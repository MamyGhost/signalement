/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.services;

/**
 *
 * @author Mamitiana
 */
import org.signalement.entities.Signalement;
import org.signalement.entities.Statut;
import org.signalement.entities.Utilisateur;
import org.signalement.repository.SignalementRepository;
import org.signalement.repository.StatutRepository;
import org.signalement.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PersonalMessageController {

  private final PersonalMessageSender pushSender;
  
  @Autowired
    private UtilisateurRepository utilisateurRepository;
  
   
     @Autowired
    private SignalementRepository signalementRepository;
     
      @Autowired
    private StatutRepository statutRepository;
     

  public PersonalMessageController(PersonalMessageSender pushSender) {
    this.pushSender = pushSender;
  }

  @CrossOrigin
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void register(@RequestParam(value ="token") String token,@RequestParam(value = "email") String email) {
    System.out.println("register: " + token);
    System.out.println("email: " + email);
    Utilisateur ut = utilisateurRepository.findByEmail(email);
    ut.setNotification(token);
    utilisateurRepository.save(ut);
    
    //this.pushSender.addToken(token);
  //  this.pushSender.sendPushMessages();
  }
  
   @PutMapping("/wb/userfront/signalement/statut/{description}/{idregion}")
    public ResponseEntity <Signalement> updateStatut(@PathVariable("description") String description,@PathVariable("idregion") int idregion){
      Signalement sData= signalementRepository.getSignalementByNomandRegion(description,idregion);
      Statut testa =statutRepository.findById(3).get();
      sData.setStatut(testa);
      Utilisateur user =  sData.getUtilisateur();
      
      pushSender.sendPushMessages(user.getNotification(), "Le signalment avec l' id "+sData.getId().toString()+"a change de statut en "+sData.getStatut().getEtat(),user.getUsername());
      System.out.println("Envoie sigalement");
       try{
            signalementRepository.save(sData);
          }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Erreur du serveur:"+ex.getMessage());
          }
     return new ResponseEntity<>(sData, HttpStatus.OK);
}

  @PostMapping("/unregister")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unregister(@RequestParam("token") String token) {
    System.out.println("unregister: " + token);
    this.pushSender.removeToken(token);
  }
  
}

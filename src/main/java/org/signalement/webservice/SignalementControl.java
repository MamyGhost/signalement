/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.webservice;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.signalement.entities.Signalement;
import org.signalement.entities.Statut;
import org.signalement.repository.SignalementRepository;
import org.signalement.repository.StatutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mamitiana
 */
@RestController
public class SignalementControl {
    
     @Autowired
    private SignalementRepository signalementRepository;
      @Autowired
    private StatutRepository statutRepository;
     
     @GetMapping("/wb/signalement/{id}")
        public ResponseEntity<Signalement> getSignalementById(@PathVariable("id") int id) {
          Optional<Signalement> sData = signalementRepository.findById(id);

          if (sData.isPresent()) {
            return new ResponseEntity<>(sData.get(), HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Signalement non trouve");
          }
        }
        
        
    @GetMapping("/wb/type/{idtype}/signalement")
        public ResponseEntity<List<Signalement>> getSignalementBytype(@PathVariable("idtype") int id) {
          List<Signalement> sData = signalementRepository.chercherpartype(id);

          if (!sData.isEmpty()) {
            return new ResponseEntity<>(sData, HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Signalement non trouve");
          }
        }
                
    
     @GetMapping("/wb/statut/{statut}/signalement")
    public ResponseEntity<List<Signalement>> listSignalementParStatut(@PathVariable("statut") int statut){
      List<Signalement> sData= signalementRepository.findSignalementByStatut(statut);
    if (!sData.isEmpty()) {
            return new ResponseEntity<>(sData, HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Signalement non trouve");
          }
}
  

  @GetMapping("/wb/userfront/signalement/type")
        public ResponseEntity<List<Signalement>> getSignalementBytypeAndRegion(@RequestParam(name="type") String type,@RequestParam(name="idregion") int idregion) {
          List<Signalement> sData = signalementRepository.findSignalementByTypeandRegion(type,idregion);

          if (!sData.isEmpty()) {
            return new ResponseEntity<>(sData, HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Signalement non trouve");
          }
        }


       @GetMapping("/wb/userfront/signalement/statut")
    public ResponseEntity<List<Signalement>> listSignalementParStatutAndRegion(@RequestParam(name="statut") String statut,@RequestParam(name="idregion") int idregion){
      List<Signalement> sData= signalementRepository.findSignalementByStatutandRegion(statut,idregion);
    if (!sData.isEmpty()) {
            return new ResponseEntity<>(sData, HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Signalement non trouve");
          }
}


    
         @GetMapping("/wb/userfront/signalement/date")          
    public ResponseEntity<List<Signalement>> listSignalementParStatut(@RequestParam(name="daty") String daty,@RequestParam(name="idregion") int idregion) throws ParseException{
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(daty);
      List<Signalement> sData= signalementRepository.findSignalementByDatyandRegion(date1,idregion);
    if (!sData.isEmpty()) {
            return new ResponseEntity<>(sData, HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Signalement non trouve");
          }
}


         @GetMapping("/wb/userfront/signalement/description")
    public ResponseEntity<List<Signalement>> getSignalementByNom(@RequestParam(name="description") String description,@RequestParam(name="idregion") int idregion) throws ParseException{
      List<Signalement> sData= signalementRepository.findSignalementByNomandRegion(description,idregion);
    if (!sData.isEmpty()) {
            return new ResponseEntity<>(sData, HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Signalement non trouve");
          }
}

     @GetMapping("/wb/userfront/signalement/description2")
    public ResponseEntity<Signalement> getSignalementByNom2(@RequestParam(name="description") String description,@RequestParam(name="idregion") int idregion) throws ParseException{
      Signalement sData= signalementRepository.getSignalementByNomandRegion(description,idregion);
        
      return new ResponseEntity<>(sData, HttpStatus.OK);
}


        @GetMapping("/wb/userfront/signalement/recherche")
    public ResponseEntity<List<Signalement>> listSignalementParRecherche(@RequestParam(name="type") String type,@RequestParam(name="daty") String daty,@RequestParam(name="statut") String statut,@RequestParam(name="idregion") int idregion) throws ParseException{
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(daty);
      List<Signalement> sData= signalementRepository.findSignalementByRecherche(type,date1,statut,idregion);
    if (!sData.isEmpty()) {
            return new ResponseEntity<>(sData, HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Signalement non trouve");
          }
}
    
    @PostMapping("/wb/userfront/signalement")
    public ResponseEntity<Signalement> save(@RequestBody Signalement signalement){
        // inserte admin
       try {
        Signalement sAdded = signalementRepository.save(signalement);
        if (sAdded == null)
            return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}

    @PutMapping("/wb/userfront/signalement/statut/{description}/{idregion}")
    public ResponseEntity <Signalement> updateStatut(@PathVariable("description") String description,@PathVariable("idregion") int idregion){
      Signalement sData= signalementRepository.getSignalementByNomandRegion(description,idregion);
      Statut testa =statutRepository.findById(3).get();
      
         sData.setStatut(testa);
      
     
       try{
            signalementRepository.save(sData);
          }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Erreur du serveur:"+ex.getMessage());
          }
     return new ResponseEntity<>(sData, HttpStatus.OK);
}

@GetMapping("/wb/userfront/region/{token}/signalement")
        public ResponseEntity<List<Signalement>> getSignalementByRegion(@PathVariable("token") String token) {
          
          List<Signalement> sData = signalementRepository.findSignalementByRegion(token);

          if (!sData.isEmpty()) {
            return new ResponseEntity<>(sData, HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Signalement non trouve");
          }
        }
    
}


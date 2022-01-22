/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.webservice;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.signalement.entities.Signalement;
import org.signalement.entities.Tokenmobile;
import org.signalement.entities.Utilisateur;
import org.signalement.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.signalement.entities.Photo;
import org.signalement.repository.PhotoRepository;
import org.signalement.repository.SignalementRepository;
import org.signalement.repository.TokenmobileRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Mamitiana
 */
@RestController
public class UtilisateurControl {
     
     @Autowired
    private UtilisateurRepository utilisateurRepository;
     
     @Autowired
    private SignalementRepository signalementRepository;
     
      
     @Autowired
    private TokenmobileRepository tokenmobileRepository;
     
      @Autowired
    private PhotoRepository photoRepository;
     
     @GetMapping("/wb/utilisateur/{id}/signalement")
        public ResponseEntity<List<Signalement>> getSignalementById(@PathVariable("id") int id) {
          Utilisateur r = utilisateurRepository.findById(id).get();
          List<Signalement> sData = r.getSignalementList();

          if (!sData.isEmpty()) {
            return new ResponseEntity<>(sData, HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Signalement non trouve");
          }
        }
        
     @PostMapping("/wb/utilisateur/login")
        public ResponseEntity<String> authentification(@RequestBody Utilisateur utilisateur)  {
            
            String sha = "";
            
            try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(utilisateur.getPassword().getBytes("utf8"));
	        sha = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		} 
            
          Utilisateur uData = utilisateurRepository.findByUsernameAndPassword(utilisateur.getUsername(),sha);
           String sha1 = "";
          if (uData==null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Utilisateur inexistant: mop de passe ou utilisateur incorrect");
          }else{
                if(tokenmobileRepository.findTokenNoexp().isEmpty()){
               Tokenmobile token = new Tokenmobile();
               token.setUtilisateur(uData);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
                Date date = new Date();
                LocalDateTime localDateTime =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                localDateTime = localDateTime.plusDays(1);
                Date currentDatePlus = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
               token.setDateexp(currentDatePlus);
               String tok = date.toString()+uData.getUsername()+uData.getId().toString();
              
		// With the java libraries
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(tok.getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}
                       
               token.setToken(sha1);
               tokenmobileRepository.save(token);
                }
                else{
                    Tokenmobile token = tokenmobileRepository.findTokenNoexp().get(0);
                    sha1= token.getToken();
                }
               
               HttpHeaders headers = new HttpHeaders();
               headers.add("Authorization", "Bearer "+sha1);
               return new ResponseEntity<>("data",
                headers, HttpStatus.OK);
          }
        }
        
        
         @PostMapping(value="/wb/utilisateur/{username}/signalement",consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<String> save(HttpServletRequest request,@PathVariable("username") String username,@RequestPart("signalement") Signalement signalement,@RequestPart(value = "file", required = false)MultipartFile[] files ){
        // inserte admin
       try {
        Utilisateur ut=utilisateurRepository.findByUsername(username);
        if(ut==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Uknowing User ");
        signalement.setUtilisateur(ut);
        Signalement sAdded = signalementRepository.save(signalement);
         // Root Directory.
        String path = request.getServletContext().getRealPath("");
        String uploadRootPath=path.split("webapp\\\\")[0]+"resources\\static\\img";
        System.out.println("uploadRootPath=" + uploadRootPath);
        File uploadRootDir = new File(uploadRootPath);
        
        System.out.println("FILEEEEEE:"+files[0].getOriginalFilename());
        if(files!=null){
                 for (MultipartFile fileData : files) {

         // Client File Name
         String name = fileData.getOriginalFilename();
         System.out.println("Client File Name = " + name);

         if (name != null && name.length() > 0) {
            try {
               // Create the file at server
               File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

               BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
               stream.write(fileData.getBytes());
               stream.close();
               Photo photo=new Photo();
               photo.setPhoto(uploadRootPath+"\\"+fileData.getOriginalFilename());
               photo.setSignalement(sAdded);
               photoRepository.save(photo);
              

               System.out.println("Write file: " + serverFile);
            } catch (Exception e) {
               System.out.println("Error Write file: " + name);
               System.out.println("Exeption"+e.getMessage());
               throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Erreur lors de l' upload des fichiers");
            }
         }
      }
        }
     
        
        if (sAdded == null)
            return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sAdded.getId())
                .toUri();
        System.out.println("STATUT: "+signalementRepository.findById(62).get().getStatut().getEtat());
        
       //return ResponseEntity.build();
         return ResponseEntity.ok()
        //.header("Custom-Header", "foo")
        .body("Signalement ajouté avec succes");
    } catch (Exception e) {
     throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Erreur du serveur");
    }
       
}
    
    
          
         @PostMapping(value="/wb/signin/utilisateur")
    public ResponseEntity<String> inscription(@RequestBody Utilisateur utilisateur){
        Utilisateur uData = utilisateurRepository.findByUsername(utilisateur.getUsername());
        if (uData!=null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"utilisateur Exsistant");
          }else{
            Utilisateur finaly=new Utilisateur();
            finaly.setUsername(utilisateur.getUsername());
            String sha1 = "";
            
            try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(utilisateur.getPassword().getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
                 finaly.setPassword(sha1);
		} catch (Exception e){
			e.printStackTrace();
		}
           
            
            utilisateurRepository.save(finaly);
            URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(utilisateur.getId())
                .toUri();
        //return ResponseEntity.created(location).build();
         return ResponseEntity.ok()
        .location(location)
        .body(finaly.getUsername());
        }
  
}
    
}

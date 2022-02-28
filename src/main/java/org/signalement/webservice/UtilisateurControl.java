/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.webservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
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
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import org.signalement.entities.Photo;
import org.signalement.entities.Type;
import org.signalement.entitiesMDB.SignalementmDB;
import org.signalement.repository.PhotoRepository;
import org.signalement.repository.SignalementRepository;
import org.signalement.repository.SignalnewRepository;
import org.signalement.repository.TokenmobileRepository;
import org.signalement.repository.TypeRepository;
import org.signalement.repositorymDB.SignalementRepositorymDB;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private SignalnewRepository signalnewRepository;
     
      
     @Autowired
    private TokenmobileRepository tokenmobileRepository;
     
       @Autowired
    private SignalementRepository signalementRepository;
       
           @Autowired
    private SignalementRepositorymDB signalementRepositorymDB;
       
          @Autowired
    private PhotoRepository photoRepository;
          
                   @Autowired
    private TypeRepository typeRepository;
          
          
           
     
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
        
        
     
     @DeleteMapping("/wb/utilisateur/token/{tok}")
        public ResponseEntity<String> logout(@PathVariable("tok") String token) {
          try{
          tokenmobileRepository.deleteByToken(token);
          JSONObject jo = new JSONObject();
          jo.put("message", "Logout reussi");
          return new ResponseEntity<>(jo.toJSONString(),HttpStatus.OK);
          }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Erreur du serveur:"+ex.getMessage());
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
            
          Utilisateur uData = utilisateurRepository.findByEmailAndPassword(utilisateur.getEmail(),sha);
           String sha1 = "";
          if (uData==null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Utilisateur inexistant: mop de passe ou utilisateur incorrect");
          }else{
               if(tokenmobileRepository.findTokenNoexp(utilisateur.getEmail()).isEmpty()){
                  if(tokenmobileRepository.findTokenexp(utilisateur.getEmail()) != null)  tokenmobileRepository.deleteTokenexp(utilisateur.getEmail());
              

               Tokenmobile token = new Tokenmobile();
               token.setUtilisateur(uData);
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
                Date date = new Date();
                LocalDateTime localDateTime =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                localDateTime = localDateTime.plusDays(1);
                Date currentDatePlus = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
               token.setDateexp(currentDatePlus);

               String tok = date.toString()+uData.getEmail()+uData.getId().toString();
              
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
                    Tokenmobile token = tokenmobileRepository.findTokenNoexp(utilisateur.getEmail()).get(0);
                    sha1= token.getToken();
                }
               
               HttpHeaders headers = new HttpHeaders();
               headers.add("Authorization", "Bearer "+sha1);
               JSONObject jo = new JSONObject();
               jo.put("message", "Login succesful");
               jo.put("email", utilisateur.getEmail());
               return new ResponseEntity<>(jo.toJSONString(),
                headers, HttpStatus.OK);
          }
        }
        
        @GetMapping("/wb/utilisateur/type")
        public ResponseEntity<List<Type>> getType() {
          List<Type> sData = typeRepository.findAll();
          if (!sData.isEmpty()) {
            return new ResponseEntity<>(sData, HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Erreur de serveur");
          }
        }
        
        

         @PostMapping(value="/wb/utilisateur/{username}/signalement",consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<String> save(HttpServletRequest request,@PathVariable("username") String username,@RequestParam(value="signalement") String s,@RequestParam(value = "file", required = false)MultipartFile[] files ) throws JsonProcessingException{
        // inserte admin
        String[] lista={"jpg","png","JPG","PNG"};
        List<String> myList = new ArrayList<String>(Arrays.asList(lista));
        ObjectMapper mapper = new ObjectMapper();
        Signalement signalement=mapper.readValue(s,Signalement.class);

        if(files != null){
        for(MultipartFile fileData : files){
            String[] fileFrags = fileData.getOriginalFilename().split("\\.");
            String extension = fileFrags[fileFrags.length-1];
            System.out.println("Extension: "+extension);
            if(myList.contains(extension) == false) 
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"les fichiers doivent etre des images jpg ou png");

        }
        }
       try {
        Utilisateur ut=utilisateurRepository.findByEmail(username);
        if(ut==null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Uknowing User ");
        signalement.setUtilisateur(ut);
        
        //signalnewRepository.save(signalement.getSignalnew().getTitre());
        Signalement sAdded = signalementRepository.save(signalement);
         List<Photo> photolist = new ArrayList(); 
         // Root Directory.
        String path = request.getServletContext().getRealPath("");
        String uploadRootPath=path.split("webapp\\\\")[0]+"resources\\static\\img";
        System.out.println("uploadRootPath=" + uploadRootPath);
        File uploadRootDir = new File(uploadRootPath);
        
        //System.out.println("FILEEEEEE:"+files[0].getOriginalFilename());
        
        URL url;
        url = new URL("https://api.imgur.com/3/image");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if(files!=null){
                 for (MultipartFile fileData : files) {

         // Client File Name
         String name = fileData.getOriginalFilename();
         System.out.println("Client File Name = " + name);
        

         if (name != null && name.length() > 0) {
            try {
               // Create the file at server
              // File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

             //  BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
               
                
                String dataImage = Base64.encode(fileData.getBytes());
                String data = URLEncoder.encode("image", "UTF-8") + "="
                + URLEncoder.encode(dataImage, "UTF-8");
                
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Authorization", "Client-ID " + "2a094b74c1e7584");
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
                
                
                
                StringBuilder stb = new StringBuilder();
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the response
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    stb.append(line).append("\n");
                }
                wr.close();
                rd.close();

                System.out.println(stb.toString());

                String val= stb.toString();
                JSONObject jobj = (JSONObject) JSONValue.parse(val); // Pass the Json formatted String

                String data1 = (String) jobj.get("data").toString(); 

                JSONObject jobj1 = (JSONObject) JSONValue.parse(data1); // Pass the Json formatted String
                String valiny="";
  
                Object value = jobj1.get("link");
                valiny=value.toString();
                
                System.out.println("PAth IMG:"+valiny);
                
                
                System.out.println(data);
            //   stream.write(fileData.getBytes());
             //  stream.close();
               Photo photo=new Photo();
              // photo.setPhoto(fileData.getOriginalFilename());
               photo.setPhoto(valiny);
               photo.setSignalement(sAdded);
               photoRepository.save(photo);
               photolist.add(new Photo(photo.getId(),photo.getPhoto()));

             //  System.out.println("Write file: " + serverFile);
            } catch (Exception e) {
               System.out.println("Error Write file: " + name);
               System.out.println("Exeption"+e.getMessage());
               throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Erreur lors de l' upload des fichiers");
            }
         }
      }
             conn.disconnect();
        }
        sAdded.setPhotoList(photolist);
        System.out.println("ID value:"+sAdded.getId());
        Utilisateur temp= new Utilisateur(sAdded.getUtilisateur().getId(),sAdded.getUtilisateur().getEmail(),sAdded.getUtilisateur().getDateinsc(),sAdded.getUtilisateur().getUsername());
        SignalementmDB signalmongo= new SignalementmDB(sAdded.getId(),sAdded.getDescription(),sAdded.getDaty(),sAdded.getLatitude().doubleValue(),sAdded.getLongitude().doubleValue(),temp,sAdded.getType(),sAdded.getRegion(),sAdded.getStatut(),sAdded.getSignalnew(),photolist);
        signalementRepositorymDB.save(signalmongo);
        //temp.getSignalementList().add(signalmongo);
        //utilisateurRepositorymDB.save(temp);
        System.out.println("Photo:"+signalmongo.getPhotoList());
        if (sAdded == null)
            return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(sAdded.getId())
                .toUri();
       // System.out.println("STATUT: "+signalementRepository.findById(62).get().getStatut().getEtat());
        
       //return ResponseEntity.build();
       JSONObject jo = new JSONObject();
       jo.put("message", "Signalement ajouté avec succes");
         return ResponseEntity.ok()
        //.header("Custom-Header", "foo")
        .body(jo.toJSONString());
    } catch (Exception e) {
     throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Erreur du serveur :"+e);
    }
       
}
    
    
          
         @PostMapping(value="/wb/utilisateur/signin")
    public ResponseEntity<String> inscription(@RequestBody Utilisateur utilisateur){
        Utilisateur uData = utilisateurRepository.findByEmail(utilisateur.getEmail());
        if (uData!=null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"utilisateur Exsistant");
          }else{
            Utilisateur finaly=new Utilisateur();
            finaly.setEmail(utilisateur.getEmail());
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
           
            finaly.setDateinsc(new Date());
            finaly.setUsername(utilisateur.getUsername());
            Utilisateur ut = utilisateurRepository.save(finaly);
            //UtilisateurmDB utmonngo=new UtilisateurmDB(ut.getId(),ut.getEmail(),ut.getPassword(),ut.getDateinsc());
            //utilisateurRepositorymDB.save(utmonngo);
             JSONObject jo = new JSONObject();
            jo.put("message", "Inscription termine");
            URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(utilisateur.getId())
                .toUri();
        //return ResponseEntity.created(location).build();
         return ResponseEntity.ok()
        .location(location)
        .body(jo.toJSONString());
        }
  
}
    

}

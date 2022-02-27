/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.webservice;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.signalement.entities.Signalement;
import org.signalement.entities.Tokenfront;
import org.signalement.entities.Userfront;
import org.signalement.entities.Region;
import org.signalement.repository.UserfrontRepository;
import org.signalement.repository.RegionRepository;
import org.signalement.repository.TokenfrontRepository;
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
import java.text.ParseException;
import net.minidev.json.JSONObject;
import org.signalement.repository.SignalementRepository;
import org.signalement.repository.TokenmobileRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author HASINA
 */
@RestController
public class UserFrontControl {
     
     @Autowired
    private UserfrontRepository userfrontRepository;
       @Autowired
    private RegionRepository regionRepository;
      
     @Autowired
    private TokenfrontRepository tokenfrontRepository;
     
        
     @Autowired
    private SignalementRepository signalementRepository;
     
        
     @PostMapping("/wb/userfront/login")
        public ResponseEntity<String> authentification(@RequestBody Userfront user)  {
             String sha1 = "";
            Region nreg= new Region();
            Userfront uData= userfrontRepository.findUserfrontlogin(user.getUsername(),user.getPassword(), user.getRegion().getId());
          if (uData==null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"diso le region ,na le username, na le password");
          }else{
              if(tokenfrontRepository.findTokenNoexp(user.getUsername()).isEmpty()){
                  if(tokenfrontRepository.findTokenexp(user.getUsername()) != null)  tokenfrontRepository.deleteTokenexp(user.getUsername());
              
               Tokenfront token = new Tokenfront();
               token.setUserfront(uData);
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
               tokenfrontRepository.save(token);
              }
               else{
                    Tokenfront token = tokenfrontRepository.findTokenNoexp(user.getUsername()).get(0);
                    sha1= token.getToken();
                }
               
               HttpHeaders headers = new HttpHeaders();
               headers.add("Authorization","Bearer " +sha1);
               //headers.add("RegionID",user.getRegion().getId().toString());
               // return new ResponseEntity<>(
               //  uData, headers, HttpStatus.OK);
               //return ResponseEntity.ok().headers(headers).body(uData);
              // JSONObject jo = new JSONObject();
               //jo.put("message", "Login succesful");
               return new ResponseEntity<>(user.getRegion().getId().toString(),headers, HttpStatus.OK);
          }
        }
        
        @GetMapping("/wb/userfront")
    public List<Userfront> list() {
    return userfrontRepository.findAll();
}

    @GetMapping("/wb/userfront/{id}")
    public ResponseEntity<Userfront> listUser(@PathVariable("id") Integer id){
      Optional<Userfront> sData = userfrontRepository.findById(id);
     if (sData.isPresent()) {
            return new ResponseEntity<>(sData.get(), HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Userfront non trouve");
          }
}
    
     @DeleteMapping("/wb/userfront/token/{tok}")
        public ResponseEntity<String> logout(@PathVariable("tok") String token) {
          try{
          tokenfrontRepository.deleteByToken(token);
          JSONObject jo = new JSONObject();
               jo.put("message", "Logout reussi");
          return new ResponseEntity<>(jo.toJSONString(),HttpStatus.OK);
          }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Erreur du serveur:"+ex.getMessage());
          }

        }
        
    
     
}


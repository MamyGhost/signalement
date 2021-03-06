/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.webservice;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.signalement.entities.Signalement;
import org.signalement.repository.SignalementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Mamitiana
 */
@RestController
public class SignalementControl {
    
     @Autowired
    private SignalementRepository signalementRepository;
     
     @GetMapping("/wb/signalement/{id}")
        public ResponseEntity<Signalement> getSignalementById(@PathVariable("id") int id) {
          Optional<Signalement> sData = signalementRepository.findById(id);

          if (sData.isPresent()) {
            return new ResponseEntity<>(sData.get(), HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Signalement non trouve");
          }
        }
        
        
    @GetMapping("wb/type/{idtype}/signalement")
        public ResponseEntity<List<Signalement>> getSignalementBytype(@PathVariable("idtype") int id) {
          List<Signalement> sData = signalementRepository.chercherpartype(id);

          if (!sData.isEmpty()) {
            return new ResponseEntity<>(sData, HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Signalement non trouve");
          }
        }
        

    @PostMapping("/wb/signalement")
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
    
}

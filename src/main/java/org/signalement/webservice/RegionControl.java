/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.webservice;

/**
 *
 * @author Mamitiana
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.signalement.entities.Region;
import org.signalement.entities.Signalement;
import org.signalement.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(value="*")
public class RegionControl {
    
     @Autowired
    private RegionRepository regionRepository;
     
      @GetMapping("/region")
         public ResponseEntity<List<Region>> getSignalementById() {
           List<Region> r = regionRepository.findAll();
           return new ResponseEntity<>(r, HttpStatus.OK);
          }
         
        
        
     
    
}


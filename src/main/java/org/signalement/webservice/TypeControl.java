/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.webservice;

import java.util.List;
import org.signalement.entities.Type;
import org.signalement.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Mamitiana
 */
@RestController
public class TypeControl {
    
      @Autowired
    private TypeRepository typeRepository;
     
        
    
    @GetMapping("/wb/userfront/listetype")
    public ResponseEntity<List<Type>> listType(){
      List<Type> sData = typeRepository.getListeType();
      System.out.print(sData.size());
     if (!sData.isEmpty()) {
            return new ResponseEntity<>(sData, HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Type non trouve");
          }

}
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.webservice;


import java.util.List;

import org.signalement.entities.Statut;

import org.signalement.repository.StatutRepository;

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
public class StatutControl {
     
     @Autowired
    private StatutRepository statutRepository;
     
        
    
    @GetMapping("/wb/userfront/listestatut")
    public ResponseEntity<List<Statut>> listStatut(){
      List<Statut> sData = statutRepository.getListeStatut();
     if (!sData.isEmpty()) {
            return new ResponseEntity<>(sData, HttpStatus.OK);
          } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Statut non trouve");
          }

}

}

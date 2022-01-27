/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.repository;

import org.signalement.entities.Tokenmobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import org.springframework.data.jpa.repository.Modifying;

/**
 *
 * @author Mamitiana
 */
public interface TokenmobileRepository extends JpaRepository<Tokenmobile, Integer> {
     public Tokenmobile findByToken(String token);

     
     @Transactional
     public void deleteByToken(String token);
     
       @Query("select t from Tokenmobile t where t.dateexp >  current_date and t.utilisateur.email= :mail")
    public List<Tokenmobile> findTokenNoexp(@Param("mail")String mail);
    
      @Query("select t from Tokenmobile t where t.dateexp <= current_date and t.utilisateur.email= :mail")
    public Tokenmobile findTokenexp(@Param("mail")String mail);
    
    @Transactional
    @Modifying
    @Query( value="delete t from Tokenmobile t join utilisateur u on t.utilisateur=u.id  where u.email= :mail",nativeQuery=true)
    public int deleteTokenexp(@Param("mail")String mail);
     
     


       @Query("select t from Tokenmobile t where t.dateexp >=  current_date")
    public List<Tokenmobile>findTokenNoexp();

}

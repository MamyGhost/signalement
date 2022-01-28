/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.repository;

import java.util.List;
import org.signalement.entities.Tokenfront;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Mamitiana
 */
@Repository
@Transactional
public interface TokenfrontRepository extends JpaRepository<Tokenfront, Integer> {
        
     public Tokenfront findByToken(String token);

     @Query("select t from Tokenfront t where t.userfront.id= :userfront")
    public Tokenfront findToken(@Param("userfront") int userfront);
    
    
    
     @Transactional
     public void deleteByToken(String token);
     
       @Query("select t from Tokenfront t where t.dateexp >  current_date and t.userfront.username= :username")
    public List<Tokenfront> findTokenNoexp(@Param("username")String username);
    
      @Query("select t from Tokenfront t where t.dateexp <= current_date and t.userfront.username= :username")
    public Tokenfront findTokenexp(@Param("username")String username);
    
    @Transactional
    @Modifying
    @Query( value="delete t from Tokenfront t join userfront u on t.userfront=u.id  where u.username= :username",nativeQuery=true)
    public int deleteTokenexp(@Param("username")String mail);
     
       @Query("select t from Tokenfront t where t.dateexp >=  current_date")
    public List<Tokenfront>findTokenNoexp();
}

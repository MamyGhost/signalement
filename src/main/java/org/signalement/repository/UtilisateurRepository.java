/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.repository;
import org.signalement.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
/**
/**
 *
 * @author Mamitiana
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Utilisateur findByEmailAndPassword(String us,String pass);
    
    Utilisateur findByEmail(String us);
    
   


    @Query(value = "select * from Utilisateur limit :inf,:sup",nativeQuery=true)
    public List<Utilisateur>findWithPagination(@Param("inf") int inf,@Param("sup") int sup);


}

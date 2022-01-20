/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.repository;
import java.util.List;
import org.signalement.entities.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author Mamitiana
 */
public interface TypeRepository extends JpaRepository<Type, Integer> {
    @Query(value="SELECT Nom FROM Type t order by t.Id ASC",nativeQuery=true)
    public List<String>getLabelType();   
}

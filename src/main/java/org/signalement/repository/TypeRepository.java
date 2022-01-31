/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.repository;
import java.util.List;
import org.signalement.entities.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
/**
 *
 * @author Mamitiana
 */
public interface TypeRepository extends JpaRepository<Type, Integer> {
    @Query(value="select nom from type",nativeQuery=true)
    public List<String>getListNom();
}

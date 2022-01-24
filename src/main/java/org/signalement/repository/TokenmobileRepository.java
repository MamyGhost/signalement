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
import java.util.List;
/**
 *
 * @author Mamitiana
 */
public interface TokenmobileRepository extends JpaRepository<Tokenmobile, Integer> {
     public Tokenmobile findByToken(String token);

       @Query("select t from Tokenmobile t where t.dateexp >=  current_date")
    public List<Tokenmobile>findTokenNoexp();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.repository;
import java.util.List;
import org.signalement.entities.Signalement;
import org.signalement.entities.Userfront;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author Mamitiana
 */
public interface UserfrontRepository extends JpaRepository<Userfront, Integer> {
    @Query("select u from Userfront u where u.username= :username and u.password= :password and u.region.id= :idregion")
    public Userfront findUserfrontlogin(@Param("username") String username, @Param("password") String password,@Param("idregion") Integer region);
    @Query("select u from Userfront u where u.region.id= :idregion")
    public List<Userfront>findUserfrontloginByRegion(@Param("idregion") Integer region);
}


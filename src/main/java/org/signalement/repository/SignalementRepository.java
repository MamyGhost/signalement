/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.repository;
import java.util.Date;
import java.util.List;
import org.signalement.entities.Signalement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author Mamitiana
 */
public interface SignalementRepository extends JpaRepository<Signalement, Integer> {
    public List<Signalement>  findByRegionIsNull();

    @Query("select s from Signalement s where s.type.id= :type")
    public List<Signalement>findSignalementByType(@Param("type") int type);
    
    @Query("select s from Signalement s where s.daty= :daty")
    public List<Signalement>findSignalementByDaty(@Param("daty") String daty);
    
    @Query("select s from Signalement s where s.statut.id= :statut")
    public List<Signalement>findSignalementByStatut(@Param("statut") int statut);
<<<<<<< Updated upstream
=======

    // @Query("select s from signalement s join userfront u on s.region.id=u.region.id join tokenfront t on u.id=t.userfront.id where t.token= :token ")
    // public List<Signalement> findSignalementByRegion(@Param("token") String token);

    @Query(value = "select * from signalement s join userfront u on s.region=u.region join tokenfront t on u.id=t.userfront where t.token= :token", nativeQuery = true)
    public List<Signalement>findSignalementByRegion(@Param("token") String token);
 

>>>>>>> Stashed changes
}

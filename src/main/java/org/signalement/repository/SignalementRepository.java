/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    @Query("SELECT s FROM Signalement s WHERE s.type.id = :idtype")
   List<Signalement>  chercherpartype(@Param("idtype") int idtype);

    @Query("select s from Signalement s where s.type.id= :type")
    public List<Signalement>findSignalementByType(@Param("type") int type);
    
    @Query("select s from Signalement s where s.daty= :daty")
    public List<Signalement>findSignalementByDaty(@Param("daty") String daty);
    
    @Query("select s from Signalement s where s.statut.id= :statut")
    public List<Signalement>findSignalementByStatut(@Param("statut") int statut);

    @Query("select s from Signalement s where s.type.id= :type")
    public List<Signalement>chercherpartype(@Param("type") int type);
}


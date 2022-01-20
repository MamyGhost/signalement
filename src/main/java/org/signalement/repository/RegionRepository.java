/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.repository;
import java.util.List;
import org.signalement.entities.Region;
import org.signalement.entities.Userfront;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
/**
 *
 * @author Mamitiana
 */
public interface RegionRepository extends JpaRepository<Region, Integer> {
    @Query("select r from Region r where r.nom= :nom")
    public Region findRegionByNom(@Param("nom") String nom);
    
    @Query("select s.region.nom,count(s) from Signalement s where s.statut.etat='Terminé' group by s.region.nom")
    public List<Object[]> findRegionup();
    
    @Query("select s.region.nom,count(s) from Signalement s where s.statut.etat='Nouveau' or s.statut.etat='En cours'  group by s.region.nom")
    public List<Object[]> findRegionlow();
    
    
}

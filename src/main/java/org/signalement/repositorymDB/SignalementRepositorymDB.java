/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.repositorymDB;

import java.util.List;
import org.signalement.entitiesMDB.SignalementmDB;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 *
 * @author Mamitiana
 */
@RepositoryRestResource(collectionResourceRel = "signalement", path = "wb")
public interface SignalementRepositorymDB extends MongoRepository<SignalementmDB,Integer> {

    @Query("{ 'utilisateur.email': :#{#mail} }")
    List<SignalementmDB> findByEmail(@Param("mail") String email,Pageable pageable);
    
    @RestResource(path = "/userfront/region")
    @Query("{ 'region.nom': :#{#region} }")
    List<SignalementmDB> findByregion(@Param("region") String trgion,Pageable pageable);
    
    @Query("{ 'signalnew': {$ne:null} }")
    List<SignalementmDB> findBysignalnew(Pageable pageable);

    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.entitiesMDB;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.signalement.entities.Photo;
import org.signalement.entities.Region;
import org.signalement.entities.Signalnew;
import org.signalement.entities.Statut;
import org.signalement.entities.Type;
import org.signalement.entities.Utilisateur;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Mamitiana
 */
@Document(collection = "signalement")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
public class SignalementmDB {
   
    @Id
    private Integer id;
   
    private String description;
   
    private Date daty;
    
    private Double latitude;
 
    private Double longitude;
  
    private Utilisateur utilisateur;
   
    private Type type;
  
    private Region region;
   
    private Statut statut;
   
    private Signalnew signalnew;

    private List<Photo> photoList=new ArrayList<>();


}

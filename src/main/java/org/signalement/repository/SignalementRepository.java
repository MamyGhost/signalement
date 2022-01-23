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

    @Query("select s from Signalement s where s.type.id= :type")
    public List<Signalement>findSignalementByType(@Param("type") int type);
    

    @Query("SELECT s FROM Signalement s WHERE s.type.id = :idtype")
   List<Signalement>  chercherpartype(@Param("idtype") int idtype);

    @Query( value = "select SUM(CASE month(s.daty)\n" +
"            WHEN 1 THEN 1\n" +
"            ELSE 0\n" +
"            END) \"Janvier\",\n" +
"            SUM(CASE month(s.daty)\n" +
"            WHEN 2 THEN 1\n" +
"            ELSE 0\n" +
"            END) \"Fevrier\", \n" +
"            SUM(CASE month(s.daty)\n" +
"            WHEN 3 THEN 1\n" +
"            ELSE 0\n" +
"            END) \"Mars\", \n" +
"            SUM(CASE month(s.daty)\n" +
"            WHEN 4 THEN 1\n" +
"            ELSE 0\n" +
"            END) \"Avril\", \n" +
"            SUM(CASE month(s.daty)\n" +
"            WHEN 5 THEN 1\n" +
"            ELSE 0\n" +
"            END) \"Mai\", \n" +
"            SUM(CASE month(s.daty)\n" +
"            WHEN 6 THEN 1\n" +
"            ELSE 0\n" +
"            END) \"Juin\", \n" +
"            SUM(CASE month(s.daty)\n" +
"            WHEN 7 THEN 1\n" +
"            ELSE 0\n" +
"            END) \"Juillet\", \n" +
"            SUM(CASE month(s.daty)\n" +
"            WHEN 8 THEN 1\n" +
"            ELSE 0\n" +
"            END) \"Aout\", \n" +
"            SUM(CASE month(s.daty)\n" +
"            WHEN 9 THEN 1\n" +
"            ELSE 0\n" +
"            END) \"Septembre\", \n" +
"            SUM(CASE month(s.daty)\n" +
"            WHEN 10 THEN 1\n" +
"            ELSE 0\n" +
"            END) \"Octobre\", \n" +
"            SUM(CASE month(s.daty)\n" +
"            WHEN 11 THEN 1\n" +
"            ELSE 0\n" +
"            END) \"Novembre\", \n" +
"            SUM(CASE month(s.daty)\n" +
"            WHEN 12 THEN 1\n" +
"            ELSE 0\n" +
"            END) \"Decembre\"\n" +
"            FROM Signalement s where year(s.daty)=year(:now)",
            nativeQuery=true)
            public List<Object[]>findstatmonthly(@Param("now") Date now);
            
            @Query(value="select\n" +
" 	CASE\n" +
" 		WHEN isa is null THEN 0\n" +
" 		ELSE isa\n" +
" 	END \"isa\"\n" +
" from type left join (select count(id) as isa,type as t from signalement group by type) as s on id=s.t;",nativeQuery=true)
    public List<Integer>findGroupByType();
            
            
    
    

    @Query("select s from Signalement s where s.daty= :daty")
    public List<Signalement>findSignalementByDaty(@Param("daty") String daty);
    
    @Query("select s from Signalement s where s.statut.id= :statut")
    public List<Signalement>findSignalementByStatut(@Param("statut") int statut);

    @Query(value = "select * from Signalement s order by s.daty DESC limit :inf,:sup",nativeQuery=true)
    public List<Signalement>findWithPagination(@Param("inf") int inf,@Param("sup") int sup);


    @Query(value = "select * from Signalement s where s.Utilisateur = :user order by s.daty DESC limit :inf,:sup",nativeQuery=true)
    public List<Signalement>findByUserWithPagination(@Param("user") int user,@Param("inf") int inf,@Param("sup") int sup);



}



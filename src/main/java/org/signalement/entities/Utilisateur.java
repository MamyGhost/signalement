
        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.signalement.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author Mamitiana
 */
@Entity
@Table(name = "utilisateur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utilisateur.findAll", query = "SELECT u FROM Utilisateur u")})


public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Email")
    private String email;
    @Column(name = "Password")
    private String password;
     @JsonIgnore
    @OneToMany(mappedBy = "utilisateur")
    private List<Signalement> signalementList;
    @OneToMany(mappedBy = "utilisateur")
    @JsonIgnore
    private List<Tokenmobile> tokenmobileList;
    @Column(name = "dateinsc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateinsc ;

    public Utilisateur(Integer id, String email, Date dateinsc) {
        this.id = id;
        this.email = email;
        this.dateinsc = dateinsc;
    }
    
    


    public Utilisateur() {
    }

    public Utilisateur(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        boolean test=isValidEmailAddress(email);
        if(test == false) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Email invalide");
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

     public void setPassword(String password) throws Exception {
         this.password = password;
         Pattern frenchPattern = Pattern.compile("(?i)[ùûüÿàâæçéèêëïîôœ]");
         if(frenchPattern.matcher(password).find()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"le Mot de passe ne doit pas contenir d' accent");
         if(password.length()<8)  throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"le Mot de passe doit contenir au moins 8 caracteres");
     }

    @XmlTransient
    public List<Signalement> getSignalementList() {
        return signalementList;
    }

    public void setSignalementList(List<Signalement> signalementList) {
        this.signalementList = signalementList;
    }

    @XmlTransient
    public List<Tokenmobile> getTokenmobileList() {
        return tokenmobileList;
    }

    public void setTokenmobileList(List<Tokenmobile> tokenmobileList) {
        this.tokenmobileList = tokenmobileList;
    }


    public Date getDateinsc() {
        return dateinsc;
    }

    public void setDateinsc(Date dateinsc) {
        this.dateinsc = dateinsc;
    }
   
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
     public boolean isValidEmailAddress(String email) {
           String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
           java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
           java.util.regex.Matcher m = p.matcher(email);
           return m.matches();
    }
    
}

 // public void setPassword(String password) throws Exception {
    //     this.password = password;
    //     Pattern frenchPattern = Pattern.compile("(?i)[ùûüÿàâæçéèêëïîôœ]");
    //     if(frenchPattern.matcher(password).find()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"le Mot de passe ne doit pas contenir d' accent");
    //     if(password.length()<8)  throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"le Mot de passe doit contenir au moins 8 caracteres");
    // }
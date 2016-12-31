/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Abdessamad
 * Edited by Bakero
 *--Done
 */
@Entity
public class Commentaire implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  
    /*@author Bakreo*/
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    private Integer evaluation;
    private String contenu;
    private String type;
    
    @ManyToOne
    private UtilisateurAide posteur;
    @ManyToOne
    private PosteCours posteCours;
    @ManyToOne
    private PosteDiscussion posteDiscussion; 
  
    public Commentaire() {
    }
    
    //Constructor
    public Commentaire(Long id, PosteCours posteCours, Date date, Integer evaluation, String contenu, UtilisateurAide posteur) {
        this.id = id;
        this.posteCours = posteCours;
        this.date = date;
        this.evaluation = evaluation;
        this.contenu = contenu;
        this.posteur = posteur;
    }
    //Getters n 
  

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public UtilisateurAide getPosteur() {
        return posteur;
    }

    public void setPosteur(UtilisateurAide posteur) {
        this.posteur = posteur;
    }

    /*----END Bakero----*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Commentaire)) {
            return false;
        }
        Commentaire other = (Commentaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Commentaire{" + "id=" + id + ", date=" + date + ", evaluation=" + evaluation + ", contenu=" + contenu + " posterId="+posteur.getId()+" posteCourId= "+posteCours.getId()+ '}';
    }


    public PosteCours getPosteCours() {
        return posteCours;
    }

    public void setPosteCours(PosteCours posteCours) {
        this.posteCours = posteCours;
    }

    public PosteDiscussion getPosteDiscussion() {
        return posteDiscussion;
    }

    public void setPosteDiscussion(PosteDiscussion posteDiscussion) {
        this.posteDiscussion = posteDiscussion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}

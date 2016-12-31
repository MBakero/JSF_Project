/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Abdessamad
 */
@Entity
@Table (name = "likee")
public class Like implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private PosteCours posteCours = new PosteCours();
    @ManyToOne
    private PosteDiscussion posteDiscussion = new PosteDiscussion();
    @ManyToOne
    private UtilisateurAide liker = new UtilisateurAide();
    
    

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
        if (!(object instanceof Like)) {
            return false;
        }
        Like other = (Like) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Like[ id=" + id + " ]";
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

    public UtilisateurAide getLiker() {
        return liker;
    }

    public void setLiker(UtilisateurAide liker) {
        this.liker = liker;
    }
    
}

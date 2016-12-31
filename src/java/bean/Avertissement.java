/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import enums.TypeAvertissement;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Abdessamad
 */
@Entity
public class Avertissement implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    @ManyToOne
    private Admin admin;
    @ManyToOne
    private Etudiant etudiant;
    @Enumerated(EnumType.STRING)
    private TypeAvertissement typeAvertissement;
    
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
        if (!(object instanceof Avertissement)) {
            return false;
        }
        Avertissement other = (Avertissement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Avertissement[ id=" + id + " ]";
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public TypeAvertissement getTypeAvertissement() {
        return typeAvertissement;
    }

    public void setTypeAvertissement(TypeAvertissement typeAvertissement) {
        this.typeAvertissement = typeAvertissement;
    }
    
}

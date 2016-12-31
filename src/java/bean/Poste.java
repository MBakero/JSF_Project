/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import enums.Filiere;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Abdessamad
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Poste implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UtilisateurAide posteur = new UtilisateurAide();
    @Enumerated(EnumType.STRING)
    private Filiere filiere;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    @Column(name = "evaluation", columnDefinition = "int DEFAULT 0")
    private Integer evaluation = Integer.valueOf("0");
    private String titre;
    @Column(name = "niveauScolaire", columnDefinition = "tinyint(1)")
    private Integer niveauScolaire;
    @OneToMany(mappedBy = "poste")
    private List<Signal> signals = new ArrayList<>();
    private String typePosteur;

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
        if (!(object instanceof Poste)) {
            return false;
        }
        Poste other = (Poste) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "filiere=" + filiere + ", evaluation=" + evaluation + ", titre=" + titre + ", niveauScolaire=" + niveauScolaire + ", typePosteur=" + typePosteur;
    }

    

    public UtilisateurAide getPosteur() {
        return posteur;
    }

    public void setPosteur(UtilisateurAide posteur) {
        this.posteur = posteur;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

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

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Integer getNiveauScolaire() {
        return niveauScolaire;
    }

    public void setNiveauScolaire(Integer niveauScolaire) {
        this.niveauScolaire = niveauScolaire;
    }

    public List<Signal> getSignals() {
        return signals;
    }

    public void setSignals(List<Signal> signals) {
        this.signals = signals;
    }
    public String getTypePosteur() {
        return typePosteur;
    }

    public void setTypePosteur(String typePosteur) {
        this.typePosteur = typePosteur;
    }
    
}

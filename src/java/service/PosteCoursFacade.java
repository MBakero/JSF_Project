/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.PosteCours;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Abdessamad
 */
@Stateless
public class PosteCoursFacade extends AbstractFacade<PosteCours> {

    @PersistenceContext(unitName = "PlateForme_ensiasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PosteCoursFacade() {
        super(PosteCours.class);
    }

    public List<PosteCours> posteByFiliere(String Filiere ) {
              System.out.println("PPPPPPPPPPPPPPPPPPPPPPP");
        Query q=em.createQuery("select p from PosteCours p where p.filiere=enums.Filiere."+Filiere);
        System.out.println("PPPPPPPPPPPPPPPPPPPPPPP");
        
            List<PosteCours> postcours=q.getResultList();
            return postcours;
    }
    
    public List<PosteCours> posteByNiveau(Long idPoste, int niveau, int limit) {
        String query = "select p from PosteCours p WHERE p.niveauScolaire = :niveau AND p.id != :idPoste ORDER BY p.id DESC";
        Query q = em.createQuery(query);
        if (limit > 0) {
            q.setMaxResults(limit);
        }
        q.setParameter("idPoste", idPoste);
        q.setParameter("niveau", niveau);
        List<PosteCours> list = q.getResultList();
        return list;
    }
    public List<PosteCours> findAllDesc(){
        Query q = em.createQuery("SELECT p FROM PosteCours p ORDER BY p.id DESC");
        return q.getResultList();
    }
    public List<PosteCours> findAllDescLimit(int limit){
        Query q = em.createQuery("SELECT p FROM PosteCours p ORDER BY p.id DESC");
        q.setMaxResults(limit);
        return q.getResultList();
    }
    
    public Long createPoste(PosteCours p){
        em.persist(p);
        em.flush();
        return p.getId();
    }
    
}

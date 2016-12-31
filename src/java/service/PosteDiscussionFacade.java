/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.PosteDiscussion;
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
public class PosteDiscussionFacade extends AbstractFacade<PosteDiscussion> {
    @PersistenceContext(unitName = "PlateForme_ensiasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PosteDiscussionFacade() {
        super(PosteDiscussion.class);
    }
    
    public List<PosteDiscussion> posteByFiliere(String Filiere ) {
        Query q=em.createQuery("select p from PosteDiscussion p where p.filiere=enums.Filiere."+Filiere);
        
            List<PosteDiscussion> postcours=q.getResultList();
            return postcours;
    }
    
    public List<PosteDiscussion> posteByNiveau(Long idPoste, int niveau, int limit) {
        String query = "select p from PosteDiscussion p WHERE p.niveauScolaire = :niveau AND p.id != :idPoste ORDER BY p.id DESC";
        Query q = em.createQuery(query);
        if (limit > 0) {
            q.setMaxResults(limit);
        }
        q.setParameter("idPoste", idPoste);
        q.setParameter("niveau", niveau);
        List<PosteDiscussion> list = q.getResultList();
        return list;
    }
    public List<PosteDiscussion> findAllDesc(){
        Query q = em.createQuery("SELECT p FROM PosteDiscussion p ORDER BY p.id DESC");
        return q.getResultList();
    }
    public List<PosteDiscussion> findAllDescLimit(int limit){
        Query q = em.createQuery("SELECT p FROM PosteDiscussion p ORDER BY p.id DESC");
        q.setMaxResults(limit);
        return q.getResultList();
    }
    
}

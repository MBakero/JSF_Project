/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Commentaire;
import java.util.ArrayList;
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
public class CommentaireFacade extends AbstractFacade<Commentaire> {

    @PersistenceContext(unitName = "PlateForme_ensiasPU")
    private EntityManager em;

    public List<Commentaire> FindById(Long idposte) {
        List<Commentaire> list = new ArrayList<>();
        Query q = em.createQuery("select cm from Commentaire cm where cm.posteCours.id=" + idposte+" AND cm.type = 'cours' ORDER BY cm.id DESC");
        list = q.getResultList();
        return list;
    }
    
    public List<Commentaire> findByIdForDiscussion(Long idposte) {
        List<Commentaire> list = new ArrayList<>();
        Query q = em.createQuery("select cm from Commentaire cm where cm.posteDiscussion.id=" + idposte+" AND cm.type = 'discussion' ORDER BY cm.id DESC");
        list = q.getResultList();
        System.out.println("list :");
        return list;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommentaireFacade() {
        super(Commentaire.class);
    }
}

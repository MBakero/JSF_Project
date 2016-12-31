/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Admin;
import bean.UtilisateurAide;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Abdessamad
 */
@Stateless
public class UtilisateurAideFacade extends AbstractFacade<UtilisateurAide> {
    @PersistenceContext(unitName = "PlateForme_ensiasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateurAideFacade() {
        super(UtilisateurAide.class);
    }
    
    public UtilisateurAide findByLogin(String login){
        UtilisateurAide user = null;
        Query query = em.createQuery("select u from UtilisateurAide u where u.login = :log");
        query.setParameter("log", login);
        try {
            user = (UtilisateurAide) query.getSingleResult();
        } catch (Exception e) {
        }
        return user;
    }
    
}

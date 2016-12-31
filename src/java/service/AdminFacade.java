/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Admin;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Abdessamad
 */
@Stateless
public class AdminFacade extends AbstractFacade<Admin> {
    @PersistenceContext(unitName = "PlateForme_ensiasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdminFacade() {
        super(Admin.class);
    }
    
    public Admin loginAuth(String login) {
        System.out.println("in logAuth");
        if (login == null || login.equals("")) {
            return null;
        }
        Admin administrateur = null;
        Query query = em.createQuery("select a from Admin a where a.login = :log");
        query.setParameter("log", login);
        try {
            administrateur = (Admin) query.getSingleResult();
        } catch (Exception e) {
        }
        return administrateur;
    }
    
}

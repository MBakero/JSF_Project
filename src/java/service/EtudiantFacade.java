/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Etudiant;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Abdessamad
 */
@Stateless
public class EtudiantFacade extends AbstractFacade<Etudiant> {
    @PersistenceContext(unitName = "PlateForme_ensiasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EtudiantFacade() {
        super(Etudiant.class);
    }
    
    public Etudiant loginAuth(String login) {
        System.out.println("in etud logAuth");
        if (login == null || login.equals("")) {
            return null;
        }
        Etudiant etudiant = null;
        Query query = em.createQuery("select e from Etudiant e where e.login = :log");
        query.setParameter("log", login);
        try {
            System.out.println("in try");
            etudiant = (Etudiant) query.getSingleResult();
            System.out.println("in try : "+etudiant);
        } catch (Exception e) {
        }
        return etudiant;
    }
    
}

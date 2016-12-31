/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;


import bean.Professeur;
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
public class ProfesseurFacade extends AbstractFacade<Professeur> {
    @PersistenceContext(unitName = "PlateForme_ensiasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfesseurFacade() {
        super(Professeur.class);
    }
    
    public Professeur loginAuth(String login) {
        System.out.println("in logAuth");
        if (login == null || login.equals("")) {
            return null;
        }
        Professeur prof = null;
        Query query = em.createQuery("select p from Professeur p where p.login = :log");
        query.setParameter("log", login);
        try {
            prof = (Professeur) query.getSingleResult();
        } catch (Exception e) {
        }
        return prof;
    }
    
    public List<Professeur> getProfByDepartement(String departement){
        
        Query q = em.createQuery("select p from Professeur p where p.departement = :depart");
        q.setParameter("depart", departement);
        return q.getResultList();
    }
    
}

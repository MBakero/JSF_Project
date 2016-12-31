/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Admin;
import bean.Etudiant;
import bean.Professeur;
import bean.Utilisateur;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Abdessamad
 */
@Stateless
public class UtilisateurFacade extends AbstractFacade<Utilisateur> {

    @PersistenceContext(unitName = "PlateForme_ensiasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateurFacade() {
        super(Utilisateur.class);
    }

    public Utilisateur findFullUserByLogin(String login, String typeUser) {
        Utilisateur user = null;
        Query query;

        try {
            switch (typeUser) {
                case "PROFESSEUR":
                    query = em.createQuery("select u from Professeur u where u.login = :log");
                    query.setParameter("log", login);
                    user = (Professeur) query.getSingleResult();
                    break;
                case "ETUDIANT":
                    query = em.createQuery("select u from Etudiant u where u.login = :log");
                    query.setParameter("log", login);
                    user = (Etudiant) query.getSingleResult();
                    break;
                default:
                    query = em.createQuery("select u from Admin u where u.login = :log");
                    query.setParameter("log", login);
                    user = (Admin) query.getSingleResult();
            }
        } catch (Exception e) {
        }
        return user;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Avertissement;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Abdessamad
 */
@Stateless
public class AvertissementFacade extends AbstractFacade<Avertissement> {
    @PersistenceContext(unitName = "PlateForme_ensiasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AvertissementFacade() {
        super(Avertissement.class);
    }
    
}

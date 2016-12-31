/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Poste;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Abdessamad
 */
@Stateless
public class PosteFacade extends AbstractFacade<Poste> {
    @PersistenceContext(unitName = "PlateForme_ensiasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PosteFacade() {
        super(Poste.class);
    }
    
}

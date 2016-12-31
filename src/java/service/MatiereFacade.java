/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Matiere;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Abdessamad
 */
@Stateless
public class MatiereFacade extends AbstractFacade<Matiere> {

    @PersistenceContext(unitName = "PlateForme_ensiasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MatiereFacade() {
        super(Matiere.class);
    }

    /* Verifier que la matiere n'est pas liée aux autres objets*/
    public int deleteMatiere(Matiere matiere) {
        super.remove(matiere);
        return 1;
    }
}

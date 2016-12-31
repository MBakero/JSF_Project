/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.PosteCours;
import bean.Tag;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Boudali
 */
@Stateless
public class TagFacade extends AbstractFacade<Tag> {

    @PersistenceContext(unitName = "PlateForme_ensiasPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TagFacade() {
        super(Tag.class);
    }
    
    
        public List<Tag> findpostbytag (String search){
                System.out.println("11111111111111111111111");

        Query q= em.createQuery("select t from Tag t where t.tag='"+search+"'");
        
        System.out.println("2222222222222222222222");

        List<Tag> Tags= (List<Tag>)q.getResultList();
            for (Tag Tag1 : Tags) {
                System.out.println("33333333333)"+Tag1.toString());
            }
        return Tags;
    }
    
}
    
    


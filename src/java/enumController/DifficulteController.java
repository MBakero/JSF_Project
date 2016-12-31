/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enumController;

import enums.Difficulte;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Abdessamad
 */
@Named(value = "difficulteController")
@RequestScoped
public class DifficulteController {

    /**
     * Creates a new instance of DifficulteController
     */
    public DifficulteController() {
    }
    
    /**
     * Méthode qui permet de retourner les differentes difficultés
     * Written By Abdessamad
     * @return
     */
    public SelectItem[] getDifficultes(){
        SelectItem[] items = new SelectItem[Difficulte.values().length];
        int i = 0;
        for (Difficulte item : Difficulte.values()) {
            items[i] = new SelectItem(item,item.toString());
            i++;
        }
        return items;
    }
}

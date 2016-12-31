/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package enumController;

import enums.Filiere;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author Abdessamad
 */
@Named(value = "filiereController")
@RequestScoped
public class FiliereController {

    /**
     * Creates a new instance of FiliereController
     */
    public FiliereController() {
    }
    /**
     * Méthode qui permet de retourner les differentes difficultés
     * Written By Abdessamad
     * @return
     */
    public SelectItem[] getFilieres(){
        SelectItem[] items = new SelectItem[Filiere.values().length];
        int i = 0;
        for (Filiere item : Filiere.values()) {
            items[i] = new SelectItem(item,item.toString());
            i++;
        }
        return items;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Professeur;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import service.ProfesseurFacade;


/**
 *
 * @author Bakero
 */
@ManagedBean(name = "professeurController")
@javax.faces.bean.SessionScoped


public class ProfesseurController {

    /**
     * Creates a new instance of ProfesseurController
     */
    
    private Professeur professeur = new Professeur();
    private List<Professeur> profsList = new ArrayList<>();
    private ProfesseurFacade professeurFacade;
    
    
    
    public String listProfsByDepart(String Departement){
         this.profsList = new ArrayList<>();
         this.profsList = professeurFacade.getProfByDepartement(Departement);
         return "/data/professeurs/list";
    }
    
    
    
    public ProfesseurController() {
    }

    public Professeur getProfesseur() {
        return professeur;
    }

    public void setProfesseur(Professeur professeur) {
        this.professeur = professeur;
    }

    public List<Professeur> getProfsList() {
        return profsList;
    }

    public void setProfsList(List<Professeur> profsList) {
        this.profsList = profsList;
    }

    public ProfesseurFacade getProfesseurFacade() {
        return professeurFacade;
    }

    public void setProfesseurFacade(ProfesseurFacade professeurFacade) {
        this.professeurFacade = professeurFacade;
    }
    
    
    
    
}

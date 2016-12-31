/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Etudiant;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import service.EtudiantFacade;

/**
 *
 * @author hp
 */
@Named(value = "etudiantController")
@SessionScoped
public class EtudiantController implements Serializable {

    /**
     * Creates a new instance of EtudiantController
     */
    private static final long serialVersionUID = 1L;
    private Etudiant etudiant = new Etudiant();
    private List<Etudiant> etudiants = new ArrayList<Etudiant>();
    private FieldsController fieldsController = new FieldsController();
    private Map<String, String> classes_validators_fields = new HashMap<String, String>();
    private Map<Long, Boolean> checked = new HashMap<Long, Boolean>();
    private Boolean checkAllInput = false;
    @Inject
    private EtudiantFacade etudiantFacade;

    /**
     * Méthode qui permet de remplir la liste des etudiants par Abdessamad
     * ABOUKDIR
     */
    @PostConstruct
    public void init() {
        etudiants = etudiantFacade.findAll();
    }

    public EtudiantController() {
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;     
    }                                 
    
    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public FieldsController getFieldsController() {
        return fieldsController;
    }

    public void setFieldsController(FieldsController fieldsController) {
        this.fieldsController = fieldsController;
    }

    public Map<String, String> getClasses_validators_fields() {
        return classes_validators_fields;
    }

    public void setClasses_validators_fields(Map<String, String> classes_validators_fields) {
        this.classes_validators_fields = classes_validators_fields;
    }

    public Map<Long, Boolean> getChecked() {
        return checked;
    }

    public void setChecked(Map<Long, Boolean> checked) {
        this.checked = checked;
    }

    public Boolean getCheckAllInput() {
        return checkAllInput;
    }

    public void setCheckAllInput(Boolean checkAllInput) {
        this.checkAllInput = checkAllInput;
    }

    
    public boolean checkFields() {
        // Map Dont on met les noms des attibutes avec leurs valeurs et et le N° de rule Ã  appliquer
        HashMap<String, Rule> fieldsWithRules = new HashMap<String, Rule>();
        boolean err = false;
        fieldsWithRules.put("nomComplet", new Rule(this.etudiant.getNom()+" "+this.etudiant.getPrenom(), 1));
        //puisque ne sont pas obligatoire
        if (!this.etudiant.getDateInscription().equals("")) {
            fieldsWithRules.put("date", new Rule(this.etudiant.getDateInscription(), 4));
        }
        if (!this.etudiant.getEmail().equals("")) {
            fieldsWithRules.put("email", new Rule(this.etudiant.getEmail(), 3));
        }
        classes_validators_fields.clear();
        classes_validators_fields.putAll(fieldsController.checkData(fieldsWithRules));
        if (!this.checkMap()) {
            err = true;
        }
        return err;
    }

    
    public boolean checkMap() {
        for (Map.Entry<String, String> entry : classes_validators_fields.entrySet()) {
            String val = entry.getValue();
            if (val.equals("has-error")) {
                return false;
            }
        }
        return true;
    }

    
    public String create() {
        Date now = new Date();
        String formatDate = new SimpleDateFormat("dd/MM/yyyy").format(now);
        this.etudiant.setDateInscription(new Date(formatDate));
        System.out.println("daaaaaaaaaaaaate : "+this.etudiant.getDateInscription());
        System.out.println("now : "+now + " format : "+formatDate);
        etudiant.setDateInscription(new Date(formatDate));
        etudiantFacade.create(etudiant);
        this.clearAllObject();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        etudiants = this.etudiantFacade.findAll();
        return "list";
    }

    public String goToDetails(Etudiant f) {
        this.etudiant = f;
        return "details";
    }

    public String goToEdit(Etudiant f) {
        this.etudiant = f;
        return "edit";
    }

    
    public String editEtudiant() {
        etudiantFacade.edit(etudiant);
        this.clearAllObject();
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        this.etudiants = etudiantFacade.findAll();
        return "list";
    }

    
    public String delete(Etudiant etudiant) {
        int res = 1;
        etudiantFacade.remove(etudiant);
        if (res == 1) {
            fieldsController.msgPersonnal("Informations", "Le etudiant a été supprimé avec succès", FieldsController.INFO_MSG);
            etudiants.remove(etudiant);
        } else {
            fieldsController.msgPersonnal("Avertissement", "Vous risuquez de perdre autres informations", FieldsController.WARN_MSG);
        }
        return "list";
    }

    /**
     * Méthode qui permet de supprimer les éléments sélectionnées Writed By
     * Mohsine
     *
     * @return
     */
    public String deleteItemChecked() {
        boolean var = true;
        for (Map.Entry<Long, Boolean> entry : checked.entrySet()) {
            Long id = entry.getKey();
            Boolean val = entry.getValue();
            if (val) {
                Etudiant f = fromlist(id);
//                int res = etudiantFacade.deleteEtudiant(f);
//                if (res == 0 || res == 2) {
//                    var = false;
//                } else {
//                    etudiants.remove(f);
//                }
                etudiantFacade.remove(f);
                etudiants.remove(f);
            }
        }
        if (!var) {
            fieldsController.msgPersonnal("Avertissment", "Problème survenu, vous risquez de perdre autre infrmations", FieldsController.WARN_MSG);
        }
        this.clearAllObject();
        return "list";
    }

    
    public Etudiant fromlist(Long id) {
        for (Iterator<Etudiant> it = etudiants.iterator(); it.hasNext();) {
            Etudiant f = it.next();
            if (f.getId().equals(id)) {
                return f;
            }
        }
        return null;
    }

    
    public void checkAll(AjaxBehaviorEvent event) {
        for (Iterator<Etudiant> it = etudiants.iterator(); it.hasNext();) {
            Etudiant f = it.next();
            //Vérification si l'article dans le map
            if (!checked.containsKey(f.getId())) {
                checked.put(f.getId(), Boolean.TRUE);
            }
        }
        for (Map.Entry<Long, Boolean> entry : checked.entrySet()) {
            //check tous les éléments selon l'etat de checkAllInput
            entry.setValue(checkAllInput);
        }

    }

    
    public void checkThis(AjaxBehaviorEvent event, Etudiant f) {
        if (!checked.containsKey(f.getId())) {
            checked.put(f.getId(), Boolean.TRUE);
        }
    }

    
    public boolean showAction() {
        for (Map.Entry<Long, Boolean> entry : checked.entrySet()) {
            Long id = entry.getKey();
            Boolean val = entry.getValue();
            if (val) {
                return true;
            }
        }
        return false;
    }

    
    public String goToList() {
        this.clearAllObject();
//        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "list";
    }

    /**
     * Méthode qui permet d'initialiser tous les objets
     */
    private void clearAllObject() {
        this.classes_validators_fields.clear();
        this.checked.clear();
        checkAllInput = false;
        etudiant = new Etudiant();
    }
}
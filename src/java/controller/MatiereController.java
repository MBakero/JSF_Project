/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Matiere;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;
import service.MatiereFacade;

/**
 *
 * @author Abdessamad
 */
@Named(value = "matiereController")
@SessionScoped
public class MatiereController implements Serializable {

    private Matiere matiere = new Matiere();
    private List<Matiere> matieres;
    private FieldsController fieldsController;
    private Part picture;
    private final String resourcesPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("").replace("build\\", "") + "/resources";
    @Inject
    private MatiereFacade matiereFacade;

    /**
     * Creates a new instance of MatiereController
     */
    public MatiereController() {
        this.fieldsController = new FieldsController();
    }

    @PostConstruct
    public void init() {
        this.matieres = this.matiereFacade.findAll();
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public List<Matiere> getMatieres() {
        return matieres;
    }

    public void setMatieres(List<Matiere> matieres) {
        this.matieres = matieres;
    }

    public String create() {
        this.matiere.setLibelle(this.matiere.getLibelle().toUpperCase());
        System.out.println("matiere : "+this.matiere);
        matiereFacade.create(this.matiere);
        this.matieres = this.matiereFacade.findAll();
        return "list?faces-redirect=true";
    }

    public String goToList() {
        return "list?faces-redirect=true";
    }

    public String goToCreate() {
        this.matiere = new Matiere();
        return "create?faces-redirect=true";
    }

    public String delete(Matiere item) {
        int res = matiereFacade.deleteMatiere(item);
        if (res == 1) {
            System.out.println("succeeeeeeeeeeeeeeeeeeeeess");
            fieldsController.msgPersonnal("Informations", "La matière a été supprimé avec succès", FieldsController.INFO_MSG);
            this.matieres.remove(item);
        } else {
            fieldsController.msgPersonnal("Avertissement", "Vous risuquez de perdre autre informations", FieldsController.WARN_MSG);
        }
        return null;
    }

    public String goToEdit(Matiere m) {
        this.matiere = m;
        return "edit?faces-redirect=true";
    }

    public String edit() {
        this.matiereFacade.edit(matiere);
        this.matieres = matiereFacade.findAll();
        this.matiere = new Matiere();
        return "list?faces-redirect=true";
    }

    public void uploadPicture(AjaxBehaviorEvent event) throws IOException {
        if (picture != null) {
            //retourne l inputstream depuis le fichier télécharger
            InputStream inputStream = picture.getInputStream();
            String fileName = getFilename(picture);
            //extension de l'image
            String ext = FilenameUtils.getExtension(fileName);
            //Vérification de l'éxtension
            String arr[] = {"jpg", "jpeg", "png", "gif", "svg"};
            boolean in = false;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].equals(ext.toLowerCase())) {
                    in = true;
                }
            }
            if (in) {
                //on edit le nom de l'image
                fileName = this.matiere.getLibelle().toUpperCase() + "." + ext;
                this.matiere.setPicture(fileName);
                //path de sortie de l'image
                String pathOut = resourcesPath + "/images/matieres/" + fileName;
                System.out.println("pathout: " + pathOut);
                try (FileOutputStream outputStream = new FileOutputStream(pathOut)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead = 0;
                    while (true) {
                        bytesRead = inputStream.read(buffer);
                        if (bytesRead > 0) {
                            outputStream.write(buffer, 0, bytesRead);
                        } else {
                            break;
                        }
                    }
                }
                inputStream.close();
                fieldsController.msgPersonnal("Info", "Image bien modifiée", FieldsController.INFO_MSG);
            } else {
                System.out.println("wrong ext");
                fieldsController.msgPersonnal("Erreur", "Vous devez choisir une format image", FieldsController.FATAL_MSG);
            }
        }
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.  
            }
        }
        return null;
    }

    public Part getPicture() {
        return picture;
    }

    public void setPicture(Part picture) {
        this.picture = picture;
    }
    
}

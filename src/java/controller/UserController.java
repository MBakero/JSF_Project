/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Admin;
import bean.Etudiant;
import bean.Professeur;
import bean.Utilisateur;
import bean.UtilisateurAide;
import enums.TypeUser;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;
import service.AdminFacade;
import service.EtudiantFacade;
import service.ProfesseurFacade;
import service.UtilisateurAideFacade;
import service.UtilisateurFacade;

/**
 *
 * @author Abdessamad
 */
@ManagedBean(name = "userController")
@javax.faces.bean.SessionScoped
public class UserController implements Serializable {

    private static final long serialVersionUID = 1L;
    private Utilisateur connectedUser = new Etudiant();
    private String type = "";
    private Part avatar;
    private final String resourcesPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("").replace("build\\", "") + "/resources";
    @EJB
    private AdminFacade adminFacade;
    @EJB
    private ProfesseurFacade professeurFacade;
    @EJB
    private EtudiantFacade etudiantFacade;
    @EJB
    private UtilisateurAideFacade utilisateurAideFacade;
    @EJB
    private UtilisateurFacade utilisateurFacade;
    private FieldsController fieldsController = new FieldsController();

    public UserController() {
    }

    public Utilisateur getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(Utilisateur user) {
        this.connectedUser = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String profileType() {
        if (this.type.equals("PROFESSEUR")) {
            return "/data/professeurs/profileProf";
        }
        return "/data/etudiants/profileEtudiant";

    }

    public Professeur getfullProfesseur() {

        if (connectedUser instanceof Professeur) {

            return (Professeur) connectedUser;
        }

        return null;
    }

    public void upload(AjaxBehaviorEvent event) throws IOException {
        if (avatar != null) {
            //retourne l inputstream depuis le fichier télécharger
            InputStream inputStream = avatar.getInputStream();
            String fileName = getFilename(avatar);
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
                System.out.println("correct ext");
                //on edit le nom de l'image
                fileName = "avatar_" + this.connectedUser.getLogin() + "." + ext;
                //path de sortie de l'image
                String pathOut = resourcesPath + "/avatars/" + fileName;
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
                //mise a jour dans la base de données
                if (this.connectedUser.getLogin() != null && !this.connectedUser.getAvatar().equals(fileName)) {
                    this.updateAvatar(fileName);
                }
                System.out.println("avat : " + connectedUser.getAvatar());
                fieldsController.msgPersonnal("Info", "Image bien modifiée", FieldsController.INFO_MSG);
                FacesContext.getCurrentInstance().getExternalContext().redirect("../../index.xhtml");
            } else {
                System.out.println("wrong ext");
                fieldsController.msgPersonnal("Erreur", "Vous devez choisir une format image", FieldsController.FATAL_MSG);
            }
        }
    }
    
    public Part getAvatar() {
        return avatar;
    }

    public void setAvatar(Part avatar) {
        this.avatar = avatar;
    }

    public Etudiant getfullEtudiant() {

        if (connectedUser instanceof Etudiant) {

            return (Etudiant) connectedUser;
        }

        return null;
    }

    public String findTypeUser(String login) {
        UtilisateurAide u = utilisateurAideFacade.findByLogin(login);
        if (u == null) {
            return "null";
        }
        return u.getType().name();
    }

    public Utilisateur findFullUserByLogin(String login, String typeUser) {
        return utilisateurFacade.findFullUserByLogin(login, typeUser);
    }

    public String login() {
        if (connectedUser.getLogin().equals("") || connectedUser.getMdps().equals("")) {
            return "";
        }
        String typeUser = findTypeUser(this.connectedUser.getLogin());
        System.out.println("typeUser : " + typeUser);
        if (typeUser.equals("null")) {
            return "";
        }
        Utilisateur loadedUser = null;
        if (typeUser.equals(TypeUser.ADMIN.name())) {
            System.out.println("iiiiiiiiiiiiiiin admin");
            loadedUser = adminFacade.loginAuth(connectedUser.getLogin());
            this.type = "ADMIN";
        } else if (typeUser.equals(TypeUser.PROFESSEUR.name())) {
            System.out.println("iiiiiiiiiiiiiiin prof");
            loadedUser = professeurFacade.loginAuth(connectedUser.getLogin());
            this.type = "PROFESSEUR";
        } else {
            System.out.println("iiiiiiiiiiiiiiin etud");
            loadedUser = etudiantFacade.loginAuth(connectedUser.getLogin());
            this.type = "ETUDIANT";
        }
        System.out.println("conn: " + this.connectedUser);
        System.out.println("load : " + loadedUser);
        if (loadedUser == null) {
            this.connectedUser = new Etudiant();
            return "";
        } //mdpass correct
        else if (loadedUser.getMdps().equals(connectedUser.getMdps())) {
            this.connectedUser = loadedUser;
            System.out.println("correeeeeeeeeeeeeeect");
            if (this.connectedUser.getAvatar() == null || this.connectedUser.getAvatar().equals("")) {
                this.connectedUser.setAvatar("default159258005521.jpg");
            }
            if (this.type.equals("ADMIN")) {
                return "/data/admin/index?faces-redirect=true";
            } else {
                return "/index?faces-redirect=true";
            }
        } //mdpass incorrect
        this.connectedUser = new Etudiant();
        return "";
    }

    public boolean isLoggedIn() {
        if (connectedUser.getLogin() == null || connectedUser.getMdps() == null) {
            return false;
        }
        return true;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
    }

    public boolean userIsAdmin() {
        if (this.type.equals("ADMIN")) {
            return true;
        } else {
            return false;
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

    /**
     * Méthode qui permet de modifier l'avatar
     *
     * @param s
     */
    private void updateAvatar(String s) {
        UtilisateurAide userAid = utilisateurAideFacade.findByLogin(connectedUser.getLogin());
        utilisateurAideFacade.edit(userAid);
        this.connectedUser.setAvatar(s);
        if (this.type.equals(TypeUser.ADMIN.name())) {
            adminFacade.edit((Admin) connectedUser);
        } else if (this.type.equals(TypeUser.PROFESSEUR.name())) {
            professeurFacade.edit((Professeur) connectedUser);
        } else {
            etudiantFacade.edit((Etudiant) connectedUser);
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Commentaire;
import bean.PosteCours;
import bean.Tag;
import bean.Utilisateur;
import bean.UtilisateurAide;
import enums.TypeUser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import service.CommentaireFacade;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;
import outils.DesktopApi;
import service.PosteCoursFacade;
import service.TagFacade;
import service.UtilisateurAideFacade;
import service.UtilisateurFacade;

/**
 *
 * @author Abdessamad
 */
@ManagedBean(name = "posteCoursController")
@javax.faces.bean.SessionScoped
public class PosteCoursController implements Serializable {

    private PosteCours posteCours = new PosteCours();
    private List<PosteCours> posteCourses = new ArrayList<>();
    private List<Commentaire> commentaires = new ArrayList<>();
    private Part pdf;
    private final String resourcesPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("").replace("build\\", "") + "\\resources";
    private List<Tag> tags = new ArrayList<>();
    @EJB
    private PosteCoursFacade posteCoursFacade;
    @EJB
    private UtilisateurAideFacade utilisateurAideFacade;
    @EJB
    private UtilisateurFacade utilisateurFacade;
    @EJB
    private CommentaireFacade commentairefacade;
    @EJB
    private CommentaireFacade commentaireFacade;
    @EJB
    private TagFacade tagFacade;
    private Tag newTag = new Tag();
    private Commentaire newCommentaire = new Commentaire();
    @ManagedProperty(value = "#{userController}")
    private UserController userController;
    private int num = 0;
    private String search;

    
    
    public String filtreByFilier(String filiere){
       this.posteCourses = new ArrayList();
        this.posteCourses=posteCoursFacade.posteByFiliere(filiere);
        return"/data/poste/list";
    }
    
    public String recherche() {

        tags = tagFacade.findpostbytag(search);
        this.posteCourses = new ArrayList<>();
        System.out.println("liste poste....");
        for (Tag tag1 : tags) {
            this.posteCourses.add(tag1.getPostCours());
            System.out.println(tag1.getPostCours());
        }

        return "";
    }

    public String FindCommmentaireByIdPost(Long idpost) {
        this.commentaires = commentairefacade.FindById(idpost);
        return "commentaire";
    }

    /**
     * Creates a new instance of PosteController
     */
    public PosteCoursController() {
        System.out.println("posteConstructor");
    }

    public void changeNum(AjaxBehaviorEvent event) {
        this.num = 7;
    }

    @PostConstruct
    public void init() {
//        this.posteCours = posteCoursFacade.find(Long.valueOf("4"));
    }

    public String createCommentaire(AjaxBehaviorEvent event) {
        System.out.println("connectedUser: " + this.userController.getConnectedUser());
        System.out.println("selectedPoste : " + this.posteCours);
        UtilisateurAide poster = utilisateurAideFacade.findByLogin(this.userController.getConnectedUser().getLogin());
        this.newCommentaire.setPosteCours(this.posteCours);
        this.newCommentaire.setPosteur(poster);
        this.newCommentaire.setDate(new Date());
        this.newCommentaire.setType("cours");
        commentaireFacade.create(newCommentaire);
        this.newCommentaire.setContenu("");
        this.commentaires = commentairefacade.FindById(this.posteCours.getId());
        return "";
    }
    
    public void uploadPdf(Long id) throws IOException {
        if (this.posteCours.getTitre() != null) {
            //retourne l inputstream depuis le fichier télécharger
            InputStream inputStream = pdf.getInputStream();
            String fileName = getFilename(pdf);
            System.out.println("pathOut: " + resourcesPath + fileName);
            //extension de l'image
            String ext = FilenameUtils.getExtension(fileName);
            //Vérification de l'éxtension
            if (ext.equals("pdf")) {
                System.out.println("correct ext");
                //on edit le nom de l'image
                fileName = "pdf_" + id + "." + ext;
                //path de sortie de l'image
                String pathOut = resourcesPath + "/pdfs/" + fileName;
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
//                fieldsController.msgPersonnal("Info", "Image bien modifiée", FieldsController.INFO_MSG);
                FacesContext.getCurrentInstance().getExternalContext().redirect("../../index.xhtml");
            } else {
                System.out.println("wrong ext");
//                fieldsController.msgPersonnal("Erreur", "Vous devez choisir une format image", FieldsController.FATAL_MSG);
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

    public String download() {
        String pathOut = resourcesPath + "\\pdfs\\pdf_"+this.posteCours.getId()+".pdf";
        System.out.println("file : " + pathOut);
        DesktopApi.open(new File(pathOut));
        return "";
    }

    public String createTag() {
        this.newTag.setPostCours(posteCours);
        System.out.println("TAAAAAAAAAAAAAAAAG: " + newTag.getTag());
        String[] tags = newTag.getTag().split(",");
        for (String tag : tags) {
            this.newTag.setTag(tag);
            System.out.println(newTag.toString());
            tagFacade.create(newTag);
        }
        newTag.setTag(null);

        return "";
    }

    public PosteCours getPosteCours() {
        return posteCours;
    }

    public void setPosteCours(PosteCours posteCours) {
        this.posteCours = posteCours;
    }

    public List<PosteCours> getPosteCourses() {
        return posteCourses;
    }

    public void setPosteCourses(List<PosteCours> posteCourses) {
        this.posteCourses = posteCourses;
    }

    public String create() {
        System.out.println("in create poste");
        String typePosteur = this.userController.getType();
        this.posteCours.setTypePosteur(typePosteur);
        this.posteCours.setDate(new Date());
        if (!typePosteur.equals(TypeUser.PROFESSEUR.name())) {
            this.posteCours.setDifficulte(null);
        }
        UtilisateurAide userAid = utilisateurAideFacade.findByLogin(this.userController.getConnectedUser().getLogin());
        this.posteCours.setPosteur(userAid);
        Long idGenerated = this.posteCoursFacade.createPoste(posteCours);
        try {
            uploadPdf(idGenerated);
        } catch (IOException ex) {
            Logger.getLogger(PosteCoursController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.posteCourses = this.posteCoursFacade.findAllDesc();
        createTag();

        return "list";
    }

    public String goToList() {
        this.posteCourses = this.posteCoursFacade.findAllDesc();
        System.out.println("taiiiiille : " + this.posteCourses.size());
        return "/data/poste/list?faces-redirect=true";
    }

    public String goToCreate() {
        this.posteCours = new PosteCours();
        return "/data/poste/create?faces-redirect=true";
    }

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }

    public CommentaireFacade getCommentairefacade() {
        return commentairefacade;
    }

    public void setCommentairefacade(CommentaireFacade commentairefacade) {
        this.commentairefacade = commentairefacade;
    }

    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Utilisateur getFullPoster() {
        String posterLogin = this.posteCours.getPosteur().getLogin();
        String posterType = this.userController.findTypeUser(posterLogin);
        return utilisateurFacade.findFullUserByLogin(posterLogin, posterType);
    }



    public String gotoDetail(PosteCours poste) {
        System.out.println("poste" + poste);
        this.posteCours = poste;
        this.commentaires = commentairefacade.FindById(poste.getId());
        return "detail?faces-redirect=true";
    }

    public List<PosteCours> findAllDesc() {
        return posteCoursFacade.findAllDesc();
    }

    public Part getPdf() {
        return pdf;
    }

    public void setPdf(Part pdf) {
        this.pdf = pdf;
    }

    public Commentaire getNewCommentaire() {
        return newCommentaire;
    }

    public void setNewCommentaire(Commentaire newCommentaire) {
        this.newCommentaire = newCommentaire;
    }

    public List<PosteCours> findAllDescLimit(int limit) {
        return this.posteCoursFacade.findAllDescLimit(limit);
    }
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public Tag getNewTag() {
        return newTag;
    }

    public void setNewTag(Tag newTag) {
        this.newTag = newTag;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
    
    public List<PosteCours> posteByNiveau(Long idPoste, int niveau, int limit){
        return this.posteCoursFacade.posteByNiveau(idPoste, niveau, limit);
    }
    
}

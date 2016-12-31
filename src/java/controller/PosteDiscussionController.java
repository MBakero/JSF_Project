/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Commentaire;
import bean.PosteDiscussion;
import bean.Tag;
import bean.Utilisateur;
import bean.UtilisateurAide;
import enums.TypeUser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;
import outils.DesktopApi;
import service.CommentaireFacade;
import service.PosteDiscussionFacade;
import service.TagFacade;
import service.UtilisateurAideFacade;
import service.UtilisateurFacade;

/**
 *
 * @author Abdessamad
 */
@ManagedBean(name = "posteDiscussionController")
@javax.faces.bean.SessionScoped
public class PosteDiscussionController implements Serializable {

    /**
     * Creates a new instance of PosteDiscussionController
     */
    public PosteDiscussionController() {
    }
    
    private PosteDiscussion posteDiscussion = new PosteDiscussion();
    private List<PosteDiscussion> posteDiscussiones = new ArrayList<>();
    private List<Commentaire> commentaires = new ArrayList<>();
    private List<Tag> tags = new ArrayList<>();
    @EJB
    private PosteDiscussionFacade posteDiscussionFacade;
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
       this.posteDiscussiones = new ArrayList();
        this.posteDiscussiones=posteDiscussionFacade.posteByFiliere(filiere);
        return"/data/discussion/list";
    }
    
    public String recherche() {

        tags = tagFacade.findpostbytag(search);
        this.posteDiscussiones = new ArrayList<>();
        System.out.println("liste poste....");
        for (Tag tag1 : tags) {
            this.posteDiscussiones.add(tag1.getPostDiscussion());
            System.out.println(tag1.getPostDiscussion());
        }

        return "";
    }

    public String FindCommmentaireByIdPost(Long idpost) {
        this.commentaires = commentairefacade.findByIdForDiscussion(idpost);
        return "commentaire";
    }

    public void changeLikeBtn(AjaxBehaviorEvent event) {
        System.out.println("clicked");
    }

    public String createCommentaire(AjaxBehaviorEvent event) {
        System.out.println("connectedUser: " + this.userController.getConnectedUser());
        System.out.println("selectedPoste : " + this.posteDiscussion);
        UtilisateurAide poster = utilisateurAideFacade.findByLogin(this.userController.getConnectedUser().getLogin());
        this.newCommentaire.setPosteDiscussion(this.posteDiscussion);
        this.newCommentaire.setPosteur(poster);
        this.newCommentaire.setDate(new Date());
        this.newCommentaire.setType("discussion");
        commentaireFacade.create(newCommentaire);
        this.newCommentaire.setContenu("");
        this.commentaires = commentairefacade.findByIdForDiscussion(this.posteDiscussion.getId());
        return "";
    }
    
    public String createTag() {
        this.newTag.setPostDiscussion(posteDiscussion);
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

    public PosteDiscussion getPosteDiscussion() {
        return posteDiscussion;
    }

    public void setPosteDiscussion(PosteDiscussion posteDiscussion) {
        this.posteDiscussion = posteDiscussion;
    }

    public List<PosteDiscussion> getPosteDiscussiones() {
        return posteDiscussiones;
    }

    public void setPosteDiscussiones(List<PosteDiscussion> posteDiscussiones) {
        this.posteDiscussiones = posteDiscussiones;
    }

    public String create() {
        System.out.println("typePoster : "+this.userController.getType());
        System.out.println("in create poste");
        String typePosteur = this.userController.getType();
        this.posteDiscussion.setTypePosteur(typePosteur);
        this.posteDiscussion.setDate(new Date());
        UtilisateurAide userAid = utilisateurAideFacade.findByLogin(this.userController.getConnectedUser().getLogin());
        this.posteDiscussion.setPosteur(userAid);
        this.posteDiscussionFacade.create(posteDiscussion);
        
        this.posteDiscussiones = this.posteDiscussionFacade.findAllDesc();
        createTag();

        return "list??faces-redirect=true";
    }

    public String goToList() {
        this.posteDiscussiones = this.posteDiscussionFacade.findAllDesc();
        return "/data/discussion/list?faces-redirect=true";
    }

    public String goToCreate() {
        this.posteDiscussion = new PosteDiscussion();
        return "/data/discussion/create?faces-redirect=true";
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
        String posterLogin = this.posteDiscussion.getPosteur().getLogin();
        String posterType = this.userController.findTypeUser(posterLogin);
        return utilisateurFacade.findFullUserByLogin(posterLogin, posterType);
    }



    public String gotoDetail(PosteDiscussion poste) {
        System.out.println("poste" + poste);
        this.posteDiscussion = poste;
        this.commentaires = commentairefacade.findByIdForDiscussion(poste.getId());
        return "detail?faces-redirect=true";
    }

    public List<PosteDiscussion> findAllDesc() {
        return posteDiscussionFacade.findAllDesc();
    }

    public Commentaire getNewCommentaire() {
        return newCommentaire;
    }

    public void setNewCommentaire(Commentaire newCommentaire) {
        this.newCommentaire = newCommentaire;
    }

    public List<PosteDiscussion> findAllDescLimit(int limit) {
        return this.posteDiscussionFacade.findAllDescLimit(limit);
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
    
    public List<PosteDiscussion> posteByNiveau(Long idPoste, int niveau, int limit){
        return this.posteDiscussionFacade.posteByNiveau(idPoste, niveau, limit);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Commentaire;
import bean.PosteCours;
import bean.Professeur;
import bean.Utilisateur;
import bean.UtilisateurAide;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import service.CommentaireFacade;

/**
 *
 * @author Boudali
 */
@Named(value = "commentaireController")
@SessionScoped
public class CommentaireController  implements Serializable {

    /**
     * Creates a new instance of CommentaireController
     */
    
        @EJB
    private CommentaireFacade commentaireFacade;
    private Commentaire commentaire=new Commentaire();
   
    
    public void CreateCommentaire(){
        PosteCours postecours = new PosteCours();
        UtilisateurAide posteur = new UtilisateurAide();
        postecours.setId(Long.valueOf(1));
        posteur.setId(Long.valueOf(1));
        System.out.println("looooooooooooooooooooool");
        this.commentaire.setPosteCours(postecours);
          System.out.println("looooooooooooooooooooool");
        this.commentaire.setPosteur(posteur);
        
        this.commentaire.setDate(new Date());
        commentaireFacade.create(commentaire);
        
        
    }
    

    public CommentaireController() {
    }

    public CommentaireFacade getCommentaireFacade() {
        return commentaireFacade;
    }

    public void setCommentaireFacade(CommentaireFacade commentaireFacade) {
        this.commentaireFacade = commentaireFacade;
    }

    public Commentaire getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(Commentaire commentaire) {
        this.commentaire = commentaire;
    }
    
}

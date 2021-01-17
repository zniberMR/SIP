package ma.ac.ehtp.sip.servlets;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import ma.ac.ehtp.sip.dao.UtilisateurDao;
import ma.ac.ehtp.sip.entities.Administrateur;
import ma.ac.ehtp.sip.entities.Professeur;
import ma.ac.ehtp.sip.entities.Secretaire;
import ma.ac.ehtp.sip.entities.Utilisateur;
import ma.ac.ehtp.sip.forms.CreationUtilisateurForm;

@WebServlet( urlPatterns = { "/creationUtilisateur" })
public class CreationUtilisateur extends HttpServlet {
    public static final String ATT_UTILISATEUR           = "utilisateur";
    public static final String ATT_PROFESSEUR            = "professeur";
    public static final String ATT_SECRETAIRE            = "secretaire";
    public static final String ATT_ADMINISTRATEUR        = "administrateur";
    public static final String ATT_UTILISATEUR_FORM      = "utilisateurForm";
    public static final String VUE_UTILISATEUR_FORM      = "/WEB-INF/template/creerUtilisateur.jsp";
    public static final String VUE_UTILISATEUR_RESULTAT  = "/WEB-INF/template/afficherUtilisateur.jsp";
    public static final String ATT_CONTEXT_UTILISATEURS  = "contextUtilisateurs";

    @EJB
    private UtilisateurDao utilisateurDao;
    @Inject
    private ValidatorFactory validatorFactory;
    private Validator validator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        validator = validatorFactory.getValidator();
    }

    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( VUE_UTILISATEUR_FORM ).forward( request, response );
    }

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        CreationUtilisateurForm utilisateurForm = new CreationUtilisateurForm(utilisateurDao, validator);

        Utilisateur                 utilisateur = utilisateurForm.creerUtilisateur( request );

        request.setAttribute( ATT_UTILISATEUR_FORM, utilisateurForm );
        request.setAttribute( ATT_UTILISATEUR, utilisateur );
        if(utilisateur instanceof Professeur){
            Professeur professeur = (Professeur)utilisateur;
            request.setAttribute( ATT_PROFESSEUR, professeur );
        } else if(utilisateur instanceof Secretaire){
            Secretaire secretaire = (Secretaire) utilisateur;
            request.setAttribute( ATT_SECRETAIRE, secretaire );
        } else if(utilisateur instanceof Administrateur){
            Administrateur administrateur = (Administrateur)utilisateur;
            request.setAttribute( ATT_ADMINISTRATEUR, administrateur );
        }

        if ( utilisateurForm.getErreurs().isEmpty() ) {
            ServletContext          servletContext = request.getServletContext();
            Map<Long, Utilisateur> mapUtilisateurs = (HashMap<Long, Utilisateur>) servletContext.getAttribute( ATT_CONTEXT_UTILISATEURS );
            mapUtilisateurs.put( utilisateur.getId(), utilisateur );
            servletContext.setAttribute( ATT_CONTEXT_UTILISATEURS, mapUtilisateurs );

            this.getServletContext().getRequestDispatcher( VUE_UTILISATEUR_RESULTAT ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( VUE_UTILISATEUR_FORM ).forward( request, response );
        }
    }
}

package ma.ac.ehtp.sip.servlets;

import ma.ac.ehtp.sip.dao.UtilisateurDao;
import ma.ac.ehtp.sip.entities.Utilisateur;
import ma.ac.ehtp.sip.forms.ConnexionForm;

import javax.ejb.EJB;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet( urlPatterns = { "/connexion" } )
public class Connexion extends HttpServlet {
    public static final String  ATT_USER                  = "utilisateur";
    public static final String  ATT_FORM                  = "form";
    public static final String  ATT_SESSION_USER          = "sessionUtilisateur";
    public static final String  VUE_CONNEXION             = "/WEB-INF/template/connexion.jsp";
    public static final String  VUE_HOME                  = "/WEB-INF/template/home.jsp";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        if(request.getSession().getAttribute(ATT_SESSION_USER) == null)
            this.getServletContext().getRequestDispatcher( VUE_CONNEXION ).forward( request, response );
        else
            this.getServletContext().getRequestDispatcher( VUE_HOME ).forward( request, response );
    }

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        ConnexionForm form = new ConnexionForm();
        /* Traitement de la requête et récupération du bean en résultant */
        Utilisateur utilisateur = form.connecterUtilisateur( request );
        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /*
         * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
         * Utilisateur à la session, sinon suppression du bean de la session.
         */
        String vue = null;
        if ( form.getErreurs().isEmpty() ) {
            session.setAttribute( ATT_SESSION_USER, utilisateur );
            vue = VUE_HOME;
        } else {
            session.setAttribute( ATT_SESSION_USER, null );
            vue = VUE_CONNEXION;
        }

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, utilisateur );

        this.getServletContext().getRequestDispatcher( vue ).forward( request, response );
    }
}
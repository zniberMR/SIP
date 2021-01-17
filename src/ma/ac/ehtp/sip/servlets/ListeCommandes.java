package ma.ac.ehtp.sip.servlets;

import ma.ac.ehtp.sip.entities.*;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet( urlPatterns = { "/listeCommandes" } )
public class ListeCommandes extends HttpServlet {
    public static final String VUE_COMMANDE_VIDE                 = "/WEB-INF/template/listerCommandesVide.jsp";
    public static final String VUE_COMMANDE_PROFESSEUR           = "/WEB-INF/template/listerCommandesProfesseur.jsp";
    public static final String VUE_COMMANDE_SECRETAIRE           = "/WEB-INF/template/listerCommandesSecretaire.jsp";
    public static final String VUE_COMMANDE_ADMINISTRATEUR       = "/WEB-INF/template/listerCommandesAdministrateur.jsp";
    public static final String ATT_SESSION_USER                  = "sessionUtilisateur";
    public static final String ATT_CONTEXT_COMMANDES             = "contextCommandes";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        HttpSession                  session = request.getSession();
        Utilisateur       sessionUtilisateur = (Utilisateur)session.getAttribute(ATT_SESSION_USER);

        ServletContext        servletContext = request.getServletContext();
        Map<Long, Commande>     mapCommandes = (Map<Long, Commande>)servletContext.getAttribute(ATT_CONTEXT_COMMANDES);

        if(mapCommandes.isEmpty())
            this.getServletContext().getRequestDispatcher( VUE_COMMANDE_VIDE ).forward( request, response );
        else if(sessionUtilisateur instanceof Administrateur)
            this.getServletContext().getRequestDispatcher( VUE_COMMANDE_ADMINISTRATEUR ).forward( request, response );
        else if(sessionUtilisateur instanceof Secretaire)
            this.getServletContext().getRequestDispatcher( VUE_COMMANDE_SECRETAIRE ).forward( request, response );
        else if(sessionUtilisateur instanceof Professeur){
            Boolean assertion = false;
            for(Map.Entry<Long,Commande> entry : mapCommandes.entrySet()) {
                if (entry.getValue().getUtilisateur().getId() == sessionUtilisateur.getId())
                    if (!entry.getValue().getEtat().equals("Annulee"))
                        assertion = true;
            }
            if(!assertion)
                this.getServletContext().getRequestDispatcher( VUE_COMMANDE_VIDE ).forward( request, response );
            else
                this.getServletContext().getRequestDispatcher( VUE_COMMANDE_PROFESSEUR ).forward( request, response );
        }
    }
}
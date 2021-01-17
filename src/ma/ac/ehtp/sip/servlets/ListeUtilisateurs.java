package ma.ac.ehtp.sip.servlets;

import ma.ac.ehtp.sip.entities.Utilisateur;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( urlPatterns = { "/listeUtilisateurs" } )
public class ListeUtilisateurs extends HttpServlet {
    public static final String VUE_UTILISATEUR                 = "/WEB-INF/template/listerUtilisateurs.jsp";
    public static final String VUE_UTILISATEUR_VIDE            = "/WEB-INF/template/listerUtilisateursVide.jsp";
    public static final String ATT_CONTEXT_UTILISATEURS        = "contextUtilisateurs";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        ServletContext          servletContext = request.getServletContext();
        Map<Long, Utilisateur> mapUtilisateurs = (Map<Long, Utilisateur>)servletContext.getAttribute(ATT_CONTEXT_UTILISATEURS);
        if(mapUtilisateurs.isEmpty())
            this.getServletContext().getRequestDispatcher( VUE_UTILISATEUR_VIDE ).forward( request, response );
        else
            this.getServletContext().getRequestDispatcher( VUE_UTILISATEUR ).forward( request, response );
    }
}
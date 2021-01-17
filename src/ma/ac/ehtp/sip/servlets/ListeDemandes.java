package ma.ac.ehtp.sip.servlets;

import ma.ac.ehtp.sip.entities.Demande;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( urlPatterns = { "/listeDemandes" } )
public class ListeDemandes extends HttpServlet {
    public static final String VUE_DEMANDE                       = "/WEB-INF/template/listerDemandes.jsp";
    public static final String VUE_DEMANDE_VIDE                  = "/WEB-INF/template/listerDemandesVide.jsp";
    public static final String ATT_CONTEXT_DEMANDES              = "contextDemandes";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        ServletContext servletContext  = request.getServletContext();
        Map<Long, Demande> mapDemandes = (Map<Long, Demande>)servletContext.getAttribute(ATT_CONTEXT_DEMANDES);
        if(mapDemandes.isEmpty())
            this.getServletContext().getRequestDispatcher( VUE_DEMANDE_VIDE ).forward( request, response );
        else
            this.getServletContext().getRequestDispatcher( VUE_DEMANDE ).forward( request, response );
    }
}
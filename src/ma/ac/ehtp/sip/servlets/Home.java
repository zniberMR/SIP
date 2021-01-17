package ma.ac.ehtp.sip.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet( urlPatterns = { "/home" } )
public class Home extends HttpServlet {
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
}
package ma.ac.ehtp.sip.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet( urlPatterns = { "/profile" } )
public class Profile extends HttpServlet {
    public static final String  VUE_PROFILE                  = "/WEB-INF/template/afficherProfile.jsp";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( VUE_PROFILE ).forward( request, response );
    }
}
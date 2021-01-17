package ma.ac.ehtp.sip.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet( urlPatterns = { "/deconnexion" } )
public class Deconnexion extends HttpServlet {
    public static final String URL_REDIRECTION = "/home";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Récupération et destruction de la session en cours */
        HttpSession session = request.getSession();
        session.invalidate();

        response.sendRedirect(request.getContextPath() + URL_REDIRECTION);
    }
}
package ma.ac.ehtp.sip.servlets;

import ma.ac.ehtp.sip.dao.UtilisateurDao;
import ma.ac.ehtp.sip.dao.DAOException;
import ma.ac.ehtp.sip.entities.Utilisateur;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet( urlPatterns = { "/suppressionUtilisateur" } )
public class SuppressionUtilisateur extends HttpServlet {
    public static final String CHAMP_ID                 = "id";
    public static final String ATT_CONTEXT_UTILISATEURS = "contextUtilisateurs";
    public static final String ATT_SESSION_USER         = "sessionUtilisateur";
    public static final String VUE_UTILISATEURS         = "/listeUtilisateurs";

    @EJB
    private UtilisateurDao utilisateurDao;

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        Long id = Long.parseLong(getValeurChamp( request, CHAMP_ID ));

        ServletContext          servletContext = request.getServletContext();
        Map<Long, Utilisateur> mapUtilisateurs = (HashMap<Long, Utilisateur>) servletContext.getAttribute( ATT_CONTEXT_UTILISATEURS );
        HttpSession                    session = request.getSession();
        Utilisateur         sessionUtilisateur = (Utilisateur) session.getAttribute(ATT_SESSION_USER);

        if ( id != null && mapUtilisateurs != null ) {
            Utilisateur utilisateur = mapUtilisateurs.get(id);
            if(utilisateur.getId() != sessionUtilisateur.getId()) {
                try {
                    utilisateurDao.supprimer(utilisateur);
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                if (utilisateur.getId() == null)
                    mapUtilisateurs.remove(id);
                servletContext.setAttribute(ATT_CONTEXT_UTILISATEURS, mapUtilisateurs);
            }
        }

        response.sendRedirect( request.getContextPath() + VUE_UTILISATEURS );
    }

    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}
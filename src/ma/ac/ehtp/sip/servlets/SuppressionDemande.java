package ma.ac.ehtp.sip.servlets;

import ma.ac.ehtp.sip.dao.DemandeDao;
import ma.ac.ehtp.sip.dao.DAOException;
import ma.ac.ehtp.sip.entities.Demande;

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

@WebServlet( urlPatterns = { "/suppressionDemande" } )
public class SuppressionDemande extends HttpServlet {
    public static final String CHAMP_ID                = "id";
    public static final String ATT_CONTEXT_DEMANDES    = "contextDemandes";
    public static final String VUE_DEMANDES            = "/listeDemandes";

    @EJB
    private DemandeDao demandeDao;

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        Long id = Long.parseLong(getValeurChamp( request, CHAMP_ID ));

        ServletContext  servletContext = request.getServletContext();
        Map<Long, Demande> mapDemandes = (HashMap<Long, Demande>) servletContext.getAttribute( ATT_CONTEXT_DEMANDES );

        if ( id != null && mapDemandes != null ) {
            Demande demande = mapDemandes.get(id);
            try{
                demandeDao.supprimer(demande);
            } catch (DAOException e){
                e.printStackTrace();
            }
            if(demande.getId() == null)
                mapDemandes.remove( id );
            servletContext.setAttribute( ATT_CONTEXT_DEMANDES, mapDemandes );
        }

        response.sendRedirect( request.getContextPath() + VUE_DEMANDES );
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
package ma.ac.ehtp.sip.servlets;

import ma.ac.ehtp.sip.config.Mailer;
import ma.ac.ehtp.sip.dao.CommandeDao;
import ma.ac.ehtp.sip.dao.DAOException;
import ma.ac.ehtp.sip.entities.Administrateur;
import ma.ac.ehtp.sip.entities.Commande;
import ma.ac.ehtp.sip.entities.Professeur;
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

@WebServlet( urlPatterns = { "/modificationCommande" } )
public class ModificationCommande extends HttpServlet {
    public static final String CHAMP_ID                = "id";
    public static final String CHAMP_CAUSE             = "cause";
    public static final String ATT_CONTEXT_COMMANDES   = "contextCommandes";
    public static final String ATT_SESSION_USER        = "sessionUtilisateur";
    public static final String VUE_COMMANDES           = "/listeCommandes";

    @EJB
    private CommandeDao commandeDao;
    @EJB
    private Mailer mailer;

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        Long      id = Long.parseLong(getValeurChamp( request, CHAMP_ID ));
        String cause = getValeurChamp(request, CHAMP_CAUSE);

        ServletContext    servletContext = request.getServletContext();
        Map<Long, Commande> mapCommandes = (HashMap<Long, Commande>) servletContext.getAttribute( ATT_CONTEXT_COMMANDES );
        HttpSession                  session = request.getSession();
        Utilisateur       sessionUtilisateur = (Utilisateur) session.getAttribute(ATT_SESSION_USER);
        if ( id != null && mapCommandes != null ) {
            Commande commande = mapCommandes.get(id);
            if(!(sessionUtilisateur instanceof Administrateur)){
                if(sessionUtilisateur instanceof Professeur && commande.getUtilisateur().getId() != sessionUtilisateur.getId()){
                    response.sendRedirect( request.getContextPath() + VUE_COMMANDES );
                    return;
                }
                commandeDao.changer(id, "Annulee");
                commande.setEtat("Annulee");

                String subject = "Modification d'une commande";
                String msg     = "La commande de l'id "+ id +" et de désignation "+ commande.getDesignation() +
                                 " est annulée par Mr/Mme " + sessionUtilisateur.getNom() + " " + sessionUtilisateur.getPrenom() +
                                 " à cause de " + cause;
                mailer.send(request, subject, msg, commande.getUtilisateur());
            } else {
                try{
                    commandeDao.supprimer(commande);
                } catch (DAOException e){
                    e.printStackTrace();
                }
                if(commande.getId() == null)
                    mapCommandes.remove( id );
            }
            session.setAttribute( ATT_CONTEXT_COMMANDES, mapCommandes );
        }

        response.sendRedirect( request.getContextPath() + VUE_COMMANDES );
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
package ma.ac.ehtp.sip.servlets;

import ma.ac.ehtp.sip.config.Mailer;
import ma.ac.ehtp.sip.dao.CommandeDao;
import ma.ac.ehtp.sip.dao.DAOException;
import ma.ac.ehtp.sip.entities.Commande;
import ma.ac.ehtp.sip.entities.Utilisateur;

import java.io.IOException;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet( urlPatterns = { "/changerEtat" } )
public class ChangerEtat extends HttpServlet {
    public static final String VUE_LISTER_COMMANDES_ADMINISTRATEUR     = "/WEB-INF/template/listerCommandesAdministrateur.jsp";
    public static final String ATT_CONTEXT_COMMANDES                   = "contextCommandes";
    public static final String ATT_SESSION_UTILISATEUR                 = "sessionUtilisateur";
    public static final String CHAMP_NOUVELLE_ETAT                     = "nouvelleEtat";
    public static final String CHAMP_ID                                = "id";
    public static final String CHAMP_CAUSE                             = "cause";

    @EJB
    private CommandeDao commandeDao;

    @EJB
    private Mailer mailer;

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        Long                          id = Long.parseLong(getValeurChamp(request, CHAMP_ID));
        String              nouvelleEtat = getValeurChamp(request, CHAMP_NOUVELLE_ETAT);
        String                     cause = getValeurChamp(request, CHAMP_CAUSE);
        ServletContext    servletContext = request.getServletContext();
        Map<Long, Commande> mapCommandes = (Map<Long, Commande>)servletContext.getAttribute(ATT_CONTEXT_COMMANDES);
        HttpSession              session = request.getSession();
        Utilisateur   sessionUtilisateur = (Utilisateur)session.getAttribute(ATT_SESSION_UTILISATEUR);
        Commande                commande = mapCommandes.get(id);
        try {
            commandeDao.changer(id, nouvelleEtat);
            mapCommandes.get(id).setEtat(nouvelleEtat);

            if(nouvelleEtat.equals("Annulee")){
                String subject = "Modification d'une commande";
                String msg     = "La commande de l'id "+ id +" et de désignation "+ commande.getDesignation() +
                                 " est annulée par Mr/Mme " + sessionUtilisateur.getNom() + " " + sessionUtilisateur.getPrenom() +
                                 " à cause de " + cause;
                mailer.send(request, subject, msg, commande.getUtilisateur());
            }
            if(nouvelleEtat.equals("En progres")){
                String subject = "Modification d'une commande";
                String msg     = "La commande de l'id "+ id +" et de désignation "+ commande.getDesignation() +
                                 " est passée à l'état en progres";
                mailer.send(request, subject, msg, commande.getUtilisateur());
            }
            if(nouvelleEtat.equals("Terminee")){
                String subject = "Modification d'une commande";
                String msg     = "La commande de l'id "+ id +" et de désignation "+ commande.getDesignation() +
                                 " est terminée";
                mailer.send(request, subject, msg, commande.getUtilisateur());
            }
        }catch (DAOException e){
            e.printStackTrace();
        }
        this.getServletContext().getRequestDispatcher( VUE_LISTER_COMMANDES_ADMINISTRATEUR ).forward( request, response );
    }

    private static String getValeurChamp(HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur.trim();
        }
    }
}
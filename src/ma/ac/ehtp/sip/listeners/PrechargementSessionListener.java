package ma.ac.ehtp.sip.listeners;

import ma.ac.ehtp.sip.dao.CommandeDao;
import ma.ac.ehtp.sip.dao.DemandeDao;
import ma.ac.ehtp.sip.dao.UtilisateurDao;
import ma.ac.ehtp.sip.entities.Commande;
import ma.ac.ehtp.sip.entities.Demande;
import ma.ac.ehtp.sip.entities.Utilisateur;

import javax.ejb.EJB;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrechargementSessionListener implements HttpSessionListener {
    public static final String ATT_SESSION_UTILISATEURS   = "sessionUtilisateurs";
    public static final String ATT_SESSION_COMMANDES      = "sessionCommandes";
    public static final String ATT_SESSION_DEMANDES       = "sessionDemandes";

    @EJB
    private UtilisateurDao utilisateurDao;
    @EJB
    private CommandeDao commandeDao;
    @EJB
    private DemandeDao demandeDao;

    @Override
    public void sessionCreated(HttpSessionEvent hse) {
        HttpSession session = hse.getSession();

        /*
         * Récupération de la liste des utilisateurs existants, et enregistrement
         * en session
         */
        List<Utilisateur> listeUtilisateurs = utilisateurDao.lister();
        Map<Long, Utilisateur> mapUtilisateurs = new HashMap<Long, Utilisateur>();
        for ( Utilisateur utilisateur : listeUtilisateurs ) {
            mapUtilisateurs.put( utilisateur.getId(), utilisateur );
        }
        session.setAttribute( ATT_SESSION_UTILISATEURS, mapUtilisateurs );

        /*
         * Récupération de la liste des commandes existantes, et
         * enregistrement en session
         */
        List<Commande> listeCommandes = commandeDao.lister();
        Map<Long, Commande> mapCommandes = new HashMap<Long, Commande>();
        for ( Commande commande : listeCommandes ) {
            mapCommandes.put( commande.getId(), commande );
        }
        session.setAttribute( ATT_SESSION_COMMANDES, mapCommandes );

        /*
         * Récupération de la liste des demandes existantes, et
         * enregistrement en session
         */
        List<Demande> listeDemandes = demandeDao.lister();
        Map<Long, Demande> mapDemandes = new HashMap<Long, Demande>();
        for ( Demande demande : listeDemandes ) {
            mapDemandes.put( demande.getId(), demande );
        }
        session.setAttribute( ATT_SESSION_DEMANDES, mapDemandes );
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent hse) {
    }
}

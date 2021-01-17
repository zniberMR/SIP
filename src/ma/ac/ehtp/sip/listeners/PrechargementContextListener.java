package ma.ac.ehtp.sip.listeners;

import ma.ac.ehtp.sip.dao.CommandeDao;
import ma.ac.ehtp.sip.dao.DemandeDao;
import ma.ac.ehtp.sip.dao.UtilisateurDao;
import ma.ac.ehtp.sip.entities.Administrateur;
import ma.ac.ehtp.sip.entities.Commande;
import ma.ac.ehtp.sip.entities.Demande;
import ma.ac.ehtp.sip.entities.Utilisateur;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebListener
public class PrechargementContextListener implements ServletContextListener {
    private static final String ATT_CONTEXT_UTILISATEURS   = "contextUtilisateurs";
    private static final String ATT_CONTEXT_COMMANDES      = "contextCommandes";
    private static final String ATT_CONTEXT_DEMANDES       = "contextDemandes";

    @EJB
    private UtilisateurDao utilisateurDao;
    @EJB
    private CommandeDao commandeDao;
    @EJB
    private DemandeDao demandeDao;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        List<Utilisateur> utilisateurs = utilisateurDao.lister();
        if(utilisateurs.isEmpty()){
            System.out.println("No users found, create an administrator.");
            Administrateur admin = new Administrateur();
            admin.setNom("admin");
            admin.setPrenom("admin");
            admin.setUsername("admin");
            admin.setPassword("admin");
            admin.setEmail("admin");
            admin.setNumeroTel("admin");
            admin.setCin("admin");
            admin.setPoste("admin");

            utilisateurDao.creer(admin);
        }

        ServletContext servletContext = servletContextEvent.getServletContext();

        List<Utilisateur> listeUtilisateurs = utilisateurDao.lister();
        Map<Long, Utilisateur> mapUtilisateurs = new HashMap<Long, Utilisateur>();
        for ( Utilisateur utilisateur : listeUtilisateurs ) {
            mapUtilisateurs.put( utilisateur.getId(), utilisateur );
        }
        servletContext.setAttribute( ATT_CONTEXT_UTILISATEURS, mapUtilisateurs );


        List<Commande> listeCommandes = commandeDao.lister();
        Map<Long, Commande> mapCommandes = new HashMap<Long, Commande>();
        for ( Commande commande : listeCommandes ) {
            mapCommandes.put( commande.getId(), commande );
        }

        servletContext.setAttribute( ATT_CONTEXT_COMMANDES, mapCommandes );
        List<Demande> listeDemandes = demandeDao.lister();
        Map<Long, Demande> mapDemandes = new HashMap<Long, Demande>();
        for ( Demande demande : listeDemandes ) {
            mapDemandes.put( demande.getId(), demande );
        }
        servletContext.setAttribute( ATT_CONTEXT_DEMANDES, mapDemandes );
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

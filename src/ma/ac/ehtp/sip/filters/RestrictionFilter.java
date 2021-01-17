package ma.ac.ehtp.sip.filters;

import ma.ac.ehtp.sip.entities.Administrateur;
import ma.ac.ehtp.sip.entities.Professeur;
import ma.ac.ehtp.sip.entities.Secretaire;
import ma.ac.ehtp.sip.entities.Utilisateur;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter( urlPatterns = { "/*" })
public class RestrictionFilter implements Filter {
    public static final String  ATT_SESSION_USER                = "sessionUtilisateur";
    public static final String  ACCES_HOME                      = "/home";
    public static final String  ACCES_CREATION_COMMANDE         = "/creationCommande";
    public static final String  ACCES_CREATION_DEMANDE          = "/creationDemande";
    public static final String  ACCES_CREATION_UTILISATEUR      = "/creationUtilisateur";
    public static final String  ACCES_LISTE_DEMANDES            = "/listeDemandes";
    public static final String  ACCES_LISTE_UTILISATEURS        = "/listeUtilisateurs";
    public static final String  ACCES_SUPPRESSION_DEMANDE       = "/suppressionDemande";
    public static final String  ACCES_SUPPRESSION_UTILISATEUR   = "/suppressionUtilisateur";
    public static final String  ACCES_CHANGER_ETAT              = "/changerEtat";
    public static final String  ACCES_MODIFICATION_UTILISATEUR  = "/modificationUtilisateur";
    public static final String  ACCES_CONNEXION                 = "/connexion";
    public static final String  ACCES_INCLUDE                   = "/inc";

    private FilterConfig config;

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.config = config;
    }

    @Override
    public void doFilter( ServletRequest req, ServletResponse resp, FilterChain chain ) throws IOException, ServletException {
        /* Cast des objets request et response */
        HttpServletRequest request   = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;

        /* Non-filtrage des ressources statiques */
        String chemin = request.getServletPath();
        if(chemin.startsWith(ACCES_INCLUDE) || chemin.startsWith(ACCES_CONNEXION)){
            chain.doFilter(request, response);
            return;
        }

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /* Récupération de l'objet utilisateur */
        Utilisateur utilisateur = (Utilisateur)session.getAttribute(ATT_SESSION_USER);

        /**
         * Si l'objet utilisateur n'existe pas dans la session en cours, alors
         * l'utilisateur n'est pas connecté
         */
        if(utilisateur == null){
            request.getRequestDispatcher(ACCES_HOME).forward(request, response);
        } else if(chemin.startsWith(ACCES_CREATION_UTILISATEUR) || chemin.startsWith(ACCES_LISTE_DEMANDES) || chemin.startsWith(ACCES_LISTE_UTILISATEURS) || chemin.startsWith(ACCES_SUPPRESSION_DEMANDE) || chemin.startsWith(ACCES_SUPPRESSION_UTILISATEUR) || chemin.startsWith(ACCES_CHANGER_ETAT) || chemin.startsWith(ACCES_MODIFICATION_UTILISATEUR)){
            if(!(utilisateur instanceof Administrateur)) {
                request.getRequestDispatcher(ACCES_HOME).forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } else if(chemin.startsWith(ACCES_CREATION_DEMANDE)){
            if(!(utilisateur instanceof Secretaire)) {
                request.getRequestDispatcher(ACCES_HOME).forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } else if(chemin.startsWith(ACCES_CREATION_COMMANDE)){
            if(!(utilisateur instanceof Professeur)) {
                request.getRequestDispatcher(ACCES_HOME).forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
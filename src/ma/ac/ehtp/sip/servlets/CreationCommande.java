package ma.ac.ehtp.sip.servlets;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;

import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import ma.ac.ehtp.sip.config.Mailer;
import ma.ac.ehtp.sip.dao.CommandeDao;
import ma.ac.ehtp.sip.dao.UtilisateurDao;
import ma.ac.ehtp.sip.entities.Commande;
import ma.ac.ehtp.sip.entities.Utilisateur;
import ma.ac.ehtp.sip.forms.CreationCommandeForm;

@WebServlet( urlPatterns = { "/creationCommande" })
public class CreationCommande extends HttpServlet {
    public static final String ATT_COMMANDE           = "commande";
    public static final String ATT_COMMANDE_FORM      = "commandeForm";
    public static final String VUE_COMMANDE_FORM      = "/WEB-INF/template/creerCommande.jsp";
    public static final String VUE_COMMANDE_RESULTAT  = "/WEB-INF/template/afficherCommande.jsp";
    public static final String ATT_CONTEXT_COMMANDES  = "contextCommandes";
    public static final String ATT_SESSION_USER       = "sessionUtilisateur";

    @EJB
    private CommandeDao commandeDao;
    @EJB
    private UtilisateurDao utilisateurDao;
    @EJB
    private Mailer mailer;
    @Inject
    private ValidatorFactory validatorFactory;
    private Validator validator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        validator = validatorFactory.getValidator();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( VUE_COMMANDE_FORM ).forward( request, response );
    }

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        CreationCommandeForm commandeForm = new CreationCommandeForm(commandeDao, utilisateurDao, validator);

        Commande                 commande = commandeForm.creerCommande( request );

        request.setAttribute( ATT_COMMANDE_FORM, commandeForm );
        request.setAttribute( ATT_COMMANDE, commande );

        if ( commandeForm.getErreurs().isEmpty() ) {
            ServletContext    servletContext = request.getServletContext();
            Map<Long, Commande> mapCommandes = (HashMap<Long, Commande>) servletContext.getAttribute( ATT_CONTEXT_COMMANDES );
            mapCommandes.put( commande.getId(), commande );
            servletContext.setAttribute( ATT_CONTEXT_COMMANDES, mapCommandes );

            HttpSession                  session = request.getSession();
            Utilisateur       sessionUtilisateur = (Utilisateur) session.getAttribute(ATT_SESSION_USER) ;
            String                       subject = "Création d'une commande";
            String                       msg     = "Une commande faite auprès de Mr/Mme " + sessionUtilisateur.getNom() + " "
                                                   + sessionUtilisateur.getPrenom();
            mailer.send(request, subject, msg);

            this.getServletContext().getRequestDispatcher( VUE_COMMANDE_RESULTAT ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( VUE_COMMANDE_FORM ).forward( request, response );
        }
    }
}

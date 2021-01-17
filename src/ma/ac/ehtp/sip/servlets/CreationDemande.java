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
import ma.ac.ehtp.sip.dao.DemandeDao;
import ma.ac.ehtp.sip.entities.Demande;
import ma.ac.ehtp.sip.entities.Utilisateur;
import ma.ac.ehtp.sip.forms.CreationDemandeForm;

@WebServlet( urlPatterns = { "/creationDemande" })
public class CreationDemande extends HttpServlet {
    public static final String ATT_DEMANDE           = "demande";
    public static final String ATT_DEMANDE_FORM      = "demandeForm";
    public static final String VUE_DEMANDE_FORM      = "/WEB-INF/template/creerDemande.jsp";
    public static final String VUE_DEMANDE_RESULTAT  = "/WEB-INF/template/afficherDemande.jsp";
    public static final String ATT_CONTEXT_DEMANDES  = "contextDemandes";
    public static final String ATT_SESSION_USER      = "sessionUtilisateur";

    @EJB
    private DemandeDao demandeDao;
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
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher( VUE_DEMANDE_FORM ).forward( request, response );
    }

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        CreationDemandeForm demandeForm = new CreationDemandeForm(demandeDao, validator);

        Demande                 demande = demandeForm.creerDemande( request );

        request.setAttribute( ATT_DEMANDE_FORM, demandeForm );
        request.setAttribute( ATT_DEMANDE, demande );

        if ( demandeForm.getErreurs().isEmpty() ) {
            ServletContext  servletContext = request.getServletContext();
            Map<Long, Demande> mapDemandes = (HashMap<Long, Demande>) servletContext.getAttribute( ATT_CONTEXT_DEMANDES );
            mapDemandes.put( demande.getId(), demande );
            servletContext.setAttribute( ATT_CONTEXT_DEMANDES, mapDemandes );

            HttpSession                session = request.getSession();
            Utilisateur      sessionUtilisateur = (Utilisateur) session.getAttribute(ATT_SESSION_USER) ;
            String                      subject = "Création d'une demande";
            String                      msg     = "Une demande faite auprès de Mr/Mme " + sessionUtilisateur.getNom() +
                                                  " " + sessionUtilisateur.getPrenom();
            mailer.send(request, subject, msg);

            this.getServletContext().getRequestDispatcher( VUE_DEMANDE_RESULTAT ).forward( request, response );
        } else {
            this.getServletContext().getRequestDispatcher( VUE_DEMANDE_FORM ).forward( request, response );
        }
    }
}

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

import ma.ac.ehtp.sip.dao.UtilisateurDao;
import ma.ac.ehtp.sip.entities.Administrateur;
import ma.ac.ehtp.sip.entities.Professeur;
import ma.ac.ehtp.sip.entities.Secretaire;
import ma.ac.ehtp.sip.entities.Utilisateur;
import ma.ac.ehtp.sip.forms.ModificationUtilisateurForm;

@WebServlet( urlPatterns = { "/modificationUtilisateur" })
public class ModificationUtilisateur extends HttpServlet {
    public static final String ATT_UTILISATEUR_FORM      = "utilisateurForm";
    public static final String VUE_UTILISATEURS          = "/listeUtilisateurs";
    public static final String ATT_CONTEXT_UTILISATEURS  = "contextUtilisateurs";

    @EJB
    private UtilisateurDao utilisateurDao;
    @Inject
    private ValidatorFactory validatorFactory;
    private Validator validator;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        validator = validatorFactory.getValidator();
    }

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        ModificationUtilisateurForm utilisateurForm = new ModificationUtilisateurForm(utilisateurDao, validator);

        Utilisateur                     utilisateur = utilisateurForm.modifierUtilisateur( request );

        request.setAttribute( ATT_UTILISATEUR_FORM, utilisateurForm );

        if ( utilisateurForm.getErreurs().isEmpty() ) {
            ServletContext         servletContext = request.getServletContext();
            Map<Long, Utilisateur> mapUtilisateur = (HashMap<Long, Utilisateur>) servletContext.getAttribute( ATT_CONTEXT_UTILISATEURS );
            Utilisateur                  utilisateur1 = mapUtilisateur.get(utilisateur.getId());
            utilisateur1.setCin(utilisateur.getCin());
            utilisateur1.setNom(utilisateur.getNom());
            utilisateur1.setPrenom(utilisateur.getPrenom());
            utilisateur1.setEmail(utilisateur.getEmail());
            utilisateur1.setUsername(utilisateur.getUsername());
            utilisateur1.setNumeroTel(utilisateur.getNumeroTel());
            if(utilisateur1 instanceof Professeur){
                ((Professeur) utilisateur1).setDepartement(((Professeur)utilisateur).getDepartement());
                ((Professeur) utilisateur1).setFiliere(((Professeur)utilisateur).getFiliere());
                ((Professeur) utilisateur1).setMatiere(((Professeur)utilisateur).getMatiere());
            } else if(utilisateur1 instanceof Secretaire){
                ((Secretaire) utilisateur1).setDepartement(((Secretaire)utilisateur).getDepartement());
                ((Secretaire) utilisateur1).setBureau(((Secretaire)utilisateur).getBureau());
            } else if(utilisateur1 instanceof Administrateur){
                ((Administrateur) utilisateur1).setPoste(((Administrateur)utilisateur).getPoste());
            }
            servletContext.setAttribute( ATT_CONTEXT_UTILISATEURS, mapUtilisateur );

            response.sendRedirect( request.getContextPath() + VUE_UTILISATEURS );
        } else {
            response.sendRedirect( request.getContextPath() + VUE_UTILISATEURS );
        }
    }
}

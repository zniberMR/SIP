package ma.ac.ehtp.sip.forms;

import ma.ac.ehtp.sip.dao.CommandeDao;
import ma.ac.ehtp.sip.dao.DAOException;
import ma.ac.ehtp.sip.dao.UtilisateurDao;
import ma.ac.ehtp.sip.entities.Commande;
import ma.ac.ehtp.sip.entities.Utilisateur;

import org.joda.time.DateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CreationCommandeForm {
    private static final String CHAMP_DESIGNATION           = "designation";
    private static final String CHAMP_QUANTITE              = "quantite";
    public static final String  ATT_SESSION_USER            = "sessionUtilisateur";

    private String              resultat;
    private Map<String, String> erreurs                     = new HashMap<String, String>();
    private CommandeDao         commandeDao;
    private UtilisateurDao      utilisateurDao;
    private Validator           validator;

    public CreationCommandeForm(CommandeDao commandeDao, UtilisateurDao utilisateurDao, Validator validator){
        this.commandeDao    = commandeDao;
        this.utilisateurDao = utilisateurDao;
        this.validator      = validator;
    }

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Commande creerCommande(HttpServletRequest request) throws IOException, ServletException {
        Utilisateur utilisateur = (Utilisateur)request.getSession().getAttribute(ATT_SESSION_USER);

        DateTime date      = new DateTime();

        String designation = getValeurChamp(request, CHAMP_DESIGNATION);
        String quantite    = getValeurChamp( request, CHAMP_QUANTITE );

        Commande commande  = new Commande();
        commande.setUtilisateur( utilisateur );
        commande.setDate(date);
        commande.setDesignation(designation);
        commande.setQuantite(quantite);

        Set<ConstraintViolation<Commande>> violations = validator.validate(commande);
        for(ConstraintViolation<Commande> violation : violations){
            setErreur(violation.getPropertyPath().toString(), violation.getMessage());
        }

        if ( erreurs.isEmpty() ) {
            try{
                commandeDao.creer(commande);
            } catch (DAOException e){
                erreurs.put(null, null);
                resultat = "Échec du création du commande : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
                e.printStackTrace();
            }
            resultat = "Succès du création du commande.";
        } else {
            resultat = "Échec du création du commande.";
        }

        return  commande;
    }

    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
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
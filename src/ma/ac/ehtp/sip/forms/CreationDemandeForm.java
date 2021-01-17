package ma.ac.ehtp.sip.forms;

import ma.ac.ehtp.sip.dao.DAOException;
import ma.ac.ehtp.sip.dao.DemandeDao;
import ma.ac.ehtp.sip.entities.Demande;

import org.joda.time.DateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CreationDemandeForm {
    private static final String CHAMP_CIN                = "cin";
    private static final String CHAMP_NOM                = "nom";
    private static final String CHAMP_PRENOM             = "prenom";
    private static final String CHAMP_EMAIL              = "email";
    private static final String CHAMP_NUMERO_TEL         = "numeroTel";

    private String              resultat;
    private Map<String, String> erreurs                  = new HashMap<String, String>();
    private DemandeDao          demandeDao;
    private Validator           validator;

    public CreationDemandeForm(DemandeDao demandeDao, Validator validator){
        this.demandeDao    = demandeDao;
        this.validator     = validator;
    }

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Demande creerDemande(HttpServletRequest request) throws IOException, ServletException {
        DateTime date       = new DateTime();

        String cin       = getValeurChamp(request, CHAMP_CIN);
        String nom       = getValeurChamp(request, CHAMP_NOM);
        String prenom    = getValeurChamp(request, CHAMP_PRENOM);
        String email     = getValeurChamp(request, CHAMP_EMAIL);
        String numeroTel = getValeurChamp(request, CHAMP_NUMERO_TEL);

        Demande demande  = new Demande();
        demande.setDate(date);
        demande.setCin(cin);
        demande.setNom(nom);
        demande.setPrenom(prenom);
        demande.setEmail(email);
        demande.setNumeroTel(numeroTel);

        Set<ConstraintViolation<Demande>> violations = validator.validate(demande);
        for(ConstraintViolation<Demande> violation : violations){
            setErreur(violation.getPropertyPath().toString(), violation.getMessage());
        }

        if ( erreurs.isEmpty() ) {
            try{
                demandeDao.creer(demande);
            } catch (DAOException e){
                erreurs.put(null, null);
                resultat = "Échec du création du demande : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
                e.printStackTrace();
            }
            resultat = "Succès du création du demande.";
        } else {
            resultat = "Échec du création du demande.";
        }

        return  demande;
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
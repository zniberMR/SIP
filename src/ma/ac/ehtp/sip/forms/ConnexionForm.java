package ma.ac.ehtp.sip.forms;

import ma.ac.ehtp.sip.entities.Utilisateur;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

public class ConnexionForm {
    private static final String ATT_CONTEXT_UTILISATEURS   = "contextUtilisateurs";
    private static final String CHAMP_USERNAME             = "username";
    private static final String CHAMP_PASSWORD             = "password";

    private static final String ALGO_CHIFFREMENT           = "SHA-256";

    private String               resultat;
    private Map<String, String>  erreurs                   = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Utilisateur connecterUtilisateur(HttpServletRequest request ) {
        /* Récupération des champs du formulaire */
        String username = getValeurChamp( request, CHAMP_USERNAME );
        String password = getValeurChamp( request, CHAMP_PASSWORD );

        ServletContext servletContext          = request.getServletContext();
        Map<Long, Utilisateur> mapUtilisateurs = (Map<Long, Utilisateur>)servletContext.getAttribute(ATT_CONTEXT_UTILISATEURS);
        Utilisateur utilisateur = null;
        for(Map.Entry<Long, Utilisateur> entry : mapUtilisateurs.entrySet()){
            if(entry.getValue().getUsername().equals(username))
                utilisateur = entry.getValue();
        }
        if(utilisateur == null) {
            setErreur(CHAMP_USERNAME, "Compte inexistant.");
            resultat = "Échec de la connexion.";
        } else {
            try {
                validationMotDePasse(password, utilisateur.getPassword());
                resultat = "Succès de la connexion.";
            } catch ( FormValidationException e ) {
                setErreur( CHAMP_PASSWORD, e.getMessage() );
                resultat = "Échec de la connexion.";
            }
        }

        return utilisateur;
    }

    private void validationMotDePasse( String motDePasse, String confirmation ) throws FormValidationException {
        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
        passwordEncryptor.setPlainDigest( false );
        if(!passwordEncryptor.checkPassword(motDePasse, confirmation))
            throw new FormValidationException("Ce mot de passe est incorrect, merci d'en saisir un autre.");
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    private void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp(HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}
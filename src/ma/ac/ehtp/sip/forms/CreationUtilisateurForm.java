package ma.ac.ehtp.sip.forms;


import ma.ac.ehtp.sip.dao.DAOException;
import ma.ac.ehtp.sip.dao.UtilisateurDao;
import ma.ac.ehtp.sip.entities.*;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CreationUtilisateurForm {
    private static final String CHAMP_CHOIX_UTILISATEUR         = "choixUtilisateur";
    private static final String CHAMP_PROFESSEUR                = "choixProfesseur";
    private static final String CHAMP_SECRETAIRE                = "choixSecretaire";
    private static final String CHAMP_ADMINISTRATEUR            = "choixAdministrateur";
    private static final String CHAMP_EMAIL                     = "email";
    private static final String CHAMP_USERNAME                  = "username";
    private static final String CHAMP_PASSWORD                  = "password";
    private static final String CHAMP_PASSWORD_CONFIRMATION     = "passwordConfirmation";
    private static final String CHAMP_CIN                       = "cin";
    private static final String CHAMP_NOM                       = "nom";
    private static final String CHAMP_PRENOM                    = "prenom";
    private static final String CHAMP_NUMERO_TEL                = "numeroTel";
    private static final String CHAMP_DEPARTEMENT               = "departement";
    private static final String CHAMP_MATIERE                   = "matiere";
    private static final String CHAMP_FILIERE                   = "filiere";
    private static final String CHAMP_POSTE                     = "poste";
    private static final String CHAMP_BUREAU                    = "bureau";

    private static final String ALGO_CHIFFREMENT                = "SHA-256";

    private String              resultat;
    private Map<String, String> erreurs                         = new HashMap<String, String>();
    private UtilisateurDao      utilisateurDao;
    private Validator           validator;

    public CreationUtilisateurForm(UtilisateurDao utilisateurDao, Validator validator){
        this.utilisateurDao    = utilisateurDao;
        this.validator         = validator;
    }

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Utilisateur creerUtilisateur(HttpServletRequest request) throws IOException, ServletException {
        String email                    = getValeurChamp(request, CHAMP_EMAIL);
        String username                 = getValeurChamp(request, CHAMP_USERNAME);
        String password                 = getValeurChamp(request, CHAMP_PASSWORD);
        String passwordConfirmation     = getValeurChamp(request, CHAMP_PASSWORD_CONFIRMATION);
        String cin                      = getValeurChamp(request, CHAMP_CIN);
        String nom                      = getValeurChamp(request, CHAMP_NOM);
        String prenom                   = getValeurChamp(request, CHAMP_PRENOM);
        String numeroTel                = getValeurChamp(request, CHAMP_NUMERO_TEL);
        String departement              = getValeurChamp(request, CHAMP_DEPARTEMENT);
        String matiere                  = getValeurChamp(request, CHAMP_MATIERE);
        String filiere                  = getValeurChamp(request, CHAMP_FILIERE);
        String poste                    = getValeurChamp(request, CHAMP_POSTE);
        String bureau                   = getValeurChamp(request, CHAMP_BUREAU);
        String choixUtilisateur         = getValeurChamp(request, CHAMP_CHOIX_UTILISATEUR);

        Utilisateur utilisateur         = new Utilisateur();
        utilisateur.setEmail(email);
        utilisateur.setUsername(username);
        utilisateur.setPassword(password);
        utilisateur.setCin(cin);
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setNumeroTel(numeroTel);

        Set<ConstraintViolation<Utilisateur>> violations = validator.validate(utilisateur);
        for(ConstraintViolation<Utilisateur> violation : violations){
            setErreur(violation.getPropertyPath().toString(), violation.getMessage());
        }

        if(password != null)
            if(!password.equals(passwordConfirmation))
                setErreur("passwordConfirmation", "Confirmation du mot de passe incorrecte.");

        if(choixUtilisateur.equals(CHAMP_PROFESSEUR)){
            Professeur professeur = new Professeur(utilisateur);
            professeur.setDepartement(departement);
            professeur.setMatiere(matiere);
            professeur.setFiliere(filiere);
            utilisateur = professeur;
        } else if(choixUtilisateur.equals(CHAMP_ADMINISTRATEUR)){
            Administrateur administrateur = new Administrateur(utilisateur);
            administrateur.setPoste(poste);
            utilisateur = administrateur;
        } else if(choixUtilisateur.equals(CHAMP_SECRETAIRE)){
            Secretaire secretaire = new Secretaire(utilisateur);
            secretaire.setDepartement(departement);
            secretaire.setBureau(bureau);
            utilisateur = secretaire;
        }

        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
        passwordEncryptor.setPlainDigest( false );
        String motDePasseChiffre = passwordEncryptor.encryptPassword( password );

        utilisateur.setPassword( motDePasseChiffre );

        if ( erreurs.isEmpty() ) {
            try{
                utilisateurDao.creer(utilisateur);
                resultat = "Succès du création de l'utilisateur.";
            } catch (DAOException e){
                erreurs.put(null, null);
                resultat = "Échec du création de l'utilisateur : une erreur imprévue est survenue, merci de réessayer dans quelques instants.";
                e.printStackTrace();
            }
        }

        return  utilisateur;
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
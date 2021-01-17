package ma.ac.ehtp.sip.forms;


import ma.ac.ehtp.sip.dao.DAOException;
import ma.ac.ehtp.sip.dao.UtilisateurDao;
import ma.ac.ehtp.sip.entities.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ModificationUtilisateurForm {
    private static final String CHAMP_ID                  = "id";
    private static final String CHAMP_EMAIL               = "email";
    private static final String CHAMP_USERNAME            = "username";
    private static final String CHAMP_CIN                 = "cin";
    private static final String CHAMP_NOM                 = "nom";
    private static final String CHAMP_PRENOM              = "prenom";
    private static final String CHAMP_NUMERO_TEL          = "numeroTel";
    private static final String CHAMP_DEPARTEMENT         = "departement";
    private static final String CHAMP_MATIERE             = "matiere";
    private static final String CHAMP_FILIERE             = "filiere";
    private static final String CHAMP_POSTE               = "poste";
    private static final String CHAMP_BUREAU              = "bureau";

    private Map<String, String> erreurs                   = new HashMap<String, String>();
    private UtilisateurDao      utilisateurDao;
    private Validator           validator;

    public ModificationUtilisateurForm(UtilisateurDao utilisateurDao, Validator validator){
        this.utilisateurDao    = utilisateurDao;
        this.validator         = validator;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public Utilisateur modifierUtilisateur(HttpServletRequest request) throws IOException, ServletException {
        Long id                 = Long.parseLong(getValeurChamp(request, CHAMP_ID));
        String email            = getValeurChamp(request, CHAMP_EMAIL);
        String username         = getValeurChamp(request, CHAMP_USERNAME);
        String cin              = getValeurChamp(request, CHAMP_CIN);
        String nom              = getValeurChamp(request, CHAMP_NOM);
        String prenom           = getValeurChamp(request, CHAMP_PRENOM);
        String numeroTel        = getValeurChamp(request, CHAMP_NUMERO_TEL);
        String departement      = getValeurChamp(request, CHAMP_DEPARTEMENT);
        String matiere          = getValeurChamp(request, CHAMP_MATIERE);
        String filiere          = getValeurChamp(request, CHAMP_FILIERE);
        String poste            = getValeurChamp(request, CHAMP_POSTE);
        String bureau           = getValeurChamp(request, CHAMP_BUREAU);

        Utilisateur utilisateur  = new Utilisateur();
        utilisateur.setId(id);
        utilisateur.setEmail(email);
        utilisateur.setUsername(username);
        utilisateur.setCin(cin);
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setNumeroTel(numeroTel);

        Utilisateur user = null;
        try{
            user = utilisateurDao.trouver(id);
        } catch (DAOException e){
            e.printStackTrace();
        }

        Set<ConstraintViolation<Utilisateur>> violations = validator.validate(utilisateur);
        for(ConstraintViolation<Utilisateur> violation : violations){
            if(violation.getPropertyPath().toString().equals("password") || (violation.getPropertyPath().toString().equals("username") && utilisateur.getUsername().equals(user.getUsername())))
                continue;
            setErreur(violation.getPropertyPath().toString(), violation.getMessage());
        }

        if(user instanceof Professeur){
            Professeur professeur = new Professeur(utilisateur);
            professeur.setDepartement(departement);
            professeur.setMatiere(matiere);
            professeur.setFiliere(filiere);
            utilisateur = professeur;
        } else if(user instanceof Administrateur){
            Administrateur administrateur = new Administrateur(utilisateur);
            administrateur.setPoste(poste);
            utilisateur = administrateur;
        } else if(user instanceof Secretaire){
            Secretaire secretaire = new Secretaire(utilisateur);
            secretaire.setDepartement(departement);
            secretaire.setBureau(bureau);
            utilisateur = secretaire;
        }

        if ( erreurs.isEmpty() ) {
            try{
                utilisateurDao.modifier(utilisateur);
            } catch (DAOException e){
                erreurs.put(null, null);
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
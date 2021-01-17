package ma.ac.ehtp.sip.entities;

import ma.ac.ehtp.sip.dao.UtilisateurDao;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username, String> {
    private static final String JNDI_NAME_UTILISATEUR_DAO = "java:global/webApp_war_exploded/UtilisateurDao";

    private UtilisateurDao utilisateurDao;

    @Override
    public void initialize(Username constraintAnnotation) {
        try {
            utilisateurDao = (UtilisateurDao)new InitialContext().lookup(JNDI_NAME_UTILISATEUR_DAO);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Utilisateur utilisateur = utilisateurDao.trouver(value);
        if(utilisateur == null)
            return true;
        else
            return false;
    }
}

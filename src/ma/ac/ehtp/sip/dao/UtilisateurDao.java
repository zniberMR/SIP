package ma.ac.ehtp.sip.dao;

import ma.ac.ehtp.sip.entities.Administrateur;
import ma.ac.ehtp.sip.entities.Professeur;
import ma.ac.ehtp.sip.entities.Secretaire;
import ma.ac.ehtp.sip.entities.Utilisateur;

import javax.ejb.Stateless;

import javax.persistence.*;

import java.util.List;

@Stateless
public class UtilisateurDao {
    private static final String JPQL_SELECT_UTILISATEUR      = "SELECT u FROM Utilisateur u ORDER BY u.id";
    private static final String JPQL_SELECT_PAR_USERNAME     = "SELECT u FROM Utilisateur u WHERE u.username=:username";
    private static final String JPQL_UPDATE_PROFESSEUR       = "UPDATE Professeur u SET u.cin = :cin, u.nom = :nom, u.prenom = :prenom," +
            " u.email = :email, u.username = :username, u.numeroTel = :numeroTel, u.departement = :departement, u.filiere = :filiere," +
            " u.matiere = :matiere WHERE u.id=:id";
    private static final String JPQL_UPDATE_SECRETAIRE       = "UPDATE Secretaire u SET u.cin = :cin, u.nom = :nom, u.prenom = :prenom," +
            " u.email = :email, u.username = :username, u.numeroTel = :numeroTel, u.departement = :departement, u.bureau = :bureau" +
            " WHERE u.id=:id";
    private static final String JPQL_UPDATE_ADMINISTRATEUR   = "UPDATE Administrateur u SET u.cin = :cin, u.nom = :nom, u.prenom = :prenom," +
            " u.email = :email, u.username = :username, u.numeroTel = :numeroTel, u.poste = :poste WHERE u.id=:id";
    private static final String PARAMETRE_ID                 = "id";
    private static final String PARAMETRE_CIN                = "cin";
    private static final String PARAMETRE_NOM                = "nom";
    private static final String PARAMETRE_PRENOM             = "prenom";
    private static final String PARAMETRE_EMAIL              = "email";
    private static final String PARAMETRE_USERNAME           = "username";
    private static final String PARAMETRE_NUMERO_TEL         = "numeroTel";
    private static final String PARAMETRE_DEPARTEMENT        = "departement";
    private static final String PARAMETRE_FILIERE            = "filiere";
    private static final String PARAMETRE_MATIERE            = "matiere";
    private static final String PARAMETRE_BUREAU             = "bureau";
    private static final String PARAMETRE_POSTE              = "poste";

    // Injection du manager, qui s'occupe de la connexion avec la BDD
    @PersistenceContext( unitName = "db_sipApp_PU" )
    private EntityManager em;

    public Utilisateur trouver(long id ) throws DAOException {
        try {
            return em.find( Utilisateur.class, id );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public Utilisateur trouver(String username ) throws DAOException {
        Utilisateur utilisateur = null;
        Query requete = em.createQuery( JPQL_SELECT_PAR_USERNAME );
        requete.setParameter( PARAMETRE_USERNAME, username );
        try {
            utilisateur = (Utilisateur) requete.getSingleResult();
        } catch ( NoResultException e ) {
            return null;
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
        return utilisateur;
    }

    public void creer( Utilisateur utilisateur ) throws DAOException {
        try {
            em.persist( utilisateur );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public List<Utilisateur> lister() throws DAOException {
        try {
            TypedQuery<Utilisateur> query = em.createQuery( JPQL_SELECT_UTILISATEUR, Utilisateur.class );
            return query.getResultList();
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void supprimer( Utilisateur utilisateur ) throws DAOException {
        try {
            em.remove( em.merge( utilisateur ) );
            utilisateur.setId(null);
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void modifier( Utilisateur utilisateur ) throws DAOException {
        try{
            if(utilisateur instanceof Professeur){
                Query query = em.createQuery(JPQL_UPDATE_PROFESSEUR);
                setParameterQuery(query, utilisateur);
                query.setParameter(PARAMETRE_DEPARTEMENT, ((Professeur) utilisateur).getDepartement());
                query.setParameter(PARAMETRE_FILIERE, ((Professeur) utilisateur).getFiliere());
                query.setParameter(PARAMETRE_MATIERE, ((Professeur) utilisateur).getMatiere());
                query.executeUpdate();
            } else if(utilisateur instanceof Secretaire){
                Query query = em.createQuery(JPQL_UPDATE_SECRETAIRE);
                setParameterQuery(query, utilisateur);
                query.setParameter(PARAMETRE_DEPARTEMENT, ((Secretaire) utilisateur).getDepartement());
                query.setParameter(PARAMETRE_BUREAU, ((Secretaire) utilisateur).getBureau());
                query.executeUpdate();
            } else if(utilisateur instanceof Administrateur){
                Query query = em.createQuery(JPQL_UPDATE_ADMINISTRATEUR);
                setParameterQuery(query, utilisateur);
                query.setParameter(PARAMETRE_POSTE, ((Administrateur) utilisateur).getPoste());
                query.executeUpdate();
            }
        } catch (Exception e){
            throw new DAOException(e);
        }
    }

    private void setParameterQuery(Query query, Utilisateur utilisateur){
        query.setParameter(PARAMETRE_ID, utilisateur.getId());
        query.setParameter(PARAMETRE_CIN, utilisateur.getCin());
        query.setParameter(PARAMETRE_NOM, utilisateur.getNom());
        query.setParameter(PARAMETRE_PRENOM, utilisateur.getPrenom());
        query.setParameter(PARAMETRE_EMAIL, utilisateur.getEmail());
        query.setParameter(PARAMETRE_USERNAME, utilisateur.getUsername());
        query.setParameter(PARAMETRE_NUMERO_TEL, utilisateur.getNumeroTel());
    }
}
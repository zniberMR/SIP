package ma.ac.ehtp.sip.dao;

import ma.ac.ehtp.sip.entities.Commande;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.util.List;

@Stateless
public class CommandeDao {
    private static final String JPQL_UPDATE_ETAT            = "UPDATE Commande c SET c.etat = :nouvelleValeur WHERE c.id=:id";
    private static final String JPQL_SELECT_COMMANDE        = "SELECT c FROM Commande c ORDER BY c.id";
    private static final String PARAMETRE_ID                = "id";
    private static final String PARAMETRE_NOUVELLE_VALEUR   = "nouvelleValeur";

    // Injection du manager, qui s'occupe de la connexion avec la BDD
    @PersistenceContext( unitName = "db_sipApp_PU" )
    private EntityManager em;

    public Commande trouver(long id ) throws DAOException {
        try {
            return em.find( Commande.class, id );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void creer( Commande commande ) throws DAOException {
        try {
            em.persist( commande );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public List<Commande> lister() throws DAOException {
        try {
            TypedQuery<Commande> query = em.createQuery( JPQL_SELECT_COMMANDE, Commande.class );
            return query.getResultList();
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void supprimer( Commande commande ) throws DAOException {
        try {
            em.remove( em.merge( commande ) );
            commande.setId(null);
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void changer(Long id, String nouvelleValeur){
        try{
            Query query = em.createQuery(JPQL_UPDATE_ETAT);
            query.setParameter(PARAMETRE_ID, id);
            query.setParameter(PARAMETRE_NOUVELLE_VALEUR, nouvelleValeur);
            query.executeUpdate();
        } catch (Exception e){
            throw new DAOException(e);
        }
    }
}

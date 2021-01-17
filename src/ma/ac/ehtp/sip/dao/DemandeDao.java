package ma.ac.ehtp.sip.dao;

import ma.ac.ehtp.sip.entities.Demande;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import java.util.List;

@Stateless
public class DemandeDao {
    private static final String JPQL_SELECT_DEMANDE        = "SELECT d FROM Demande d ORDER BY d.id";

    // Injection du manager, qui s'occupe de la connexion avec la BDD
    @PersistenceContext( unitName = "db_sipApp_PU" )
    private EntityManager em;

    public Demande trouver( long id ) throws DAOException {
        try {
            return em.find( Demande.class, id );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void creer( Demande demande ) throws DAOException {
        try {
            em.persist( demande );
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public List<Demande> lister() throws DAOException {
        try {
            TypedQuery<Demande> query = em.createQuery( JPQL_SELECT_DEMANDE, Demande.class );
            return query.getResultList();
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }

    public void supprimer( Demande demande ) throws DAOException {
        try {
            em.remove( em.merge( demande ) );
            demande.setId(null);
        } catch ( Exception e ) {
            throw new DAOException( e );
        }
    }
}

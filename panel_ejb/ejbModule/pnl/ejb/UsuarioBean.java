package pnl.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import pnl.interfaz.UsuarioBeanRemote;
import pnl.modelo.Filtro;
import pnl.modelo.Usuario;


/**
 * @generated DT_ID=none
 */
@Stateless(name = "UsuarioBean")
public class UsuarioBean
        implements  UsuarioBeanRemote,Serializable {
        	private static final long serialVersionUID = 1L;

    /**
     * @generated DT_ID=none
     */
	@Resource
	SessionContext sessionContext;

    /**
     * @generated DT_ID=none
     */
	    @PersistenceContext(unitName="Panel-EJB")
        private EntityManager em;

    /**
     * @generated DT_ID=none
     */
    public UsuarioBean() {
    }
    
    

    /**
     * @generated DT_ID=none
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Object queryByRange(String jpqlStmt, int firstResult,
                               int maxResults) {
        Query query = em.createQuery(jpqlStmt);
        if (firstResult > 0) {
            query = query.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            query = query.setMaxResults(maxResults);
        }

        return query.getResultList();
    }

    /**
     * @generated DT_ID=none
     */
    public Usuario persistUsuario(Usuario usuario) {
        em.persist(usuario);
        return usuario;
    }

    /**
     * @generated DT_ID=none
     */
    public Usuario mergeUsuario(Usuario usuario) {
        return em.merge(usuario);
    }

    /**
     * @generated DT_ID=none
     */
    public void removeUsuario(Usuario usuario) {
        usuario = em.find(Usuario.class, usuario.getIdUsuario());
        em.remove(usuario);
    }

    /**
     * @generated DT_ID=none
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Usuario> getUsuarioFindAll() {
        return em.createNamedQuery("Usuario.findAll").getResultList();
    }



	@Override
	public Usuario obtieneUsuarioPorId(String idUsuario) throws Exception {
		try {
			String queryStr = "SELECT u FROM Usuario u  WHERE u.idUsuario = :idUsuario";
					

			TypedQuery<Usuario> query = em.createQuery(queryStr, Usuario.class);

			query.setParameter("idUsuario", idUsuario);
			Usuario usuario = query.getSingleResult();
			return usuario;
		} catch (NoResultException nr) {
			return new Usuario();
		}
	}

}

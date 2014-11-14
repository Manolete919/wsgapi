package pnl.ejb;

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

import pnl.interfaz.RolBeanRemote;
import pnl.modelo.Rol;
import pnl.modelo.Usuario;


/**
 * @generated DT_ID=none
 */
@Stateless(name = "RolBean")
public class RolBean
        implements  RolBeanRemote
{

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
    public RolBean() {
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
    public Rol persistRol(Rol rol) {
        em.persist(rol);
        return rol;
    }

    /**
     * @generated DT_ID=none
     */
    public Rol mergeRol(Rol rol) {
        return em.merge(rol);
    }

    /**
     * @generated DT_ID=none
     */
    public void removeRol(Rol rol) {
        rol = em.find(Rol.class, rol.getIdRol());
        em.remove(rol);
    }

    /**
     * @generated DT_ID=none
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Rol> getRolFindAll() {
        return em.createNamedQuery("Rol.findAll").getResultList();
    }



	@Override
	public List<Rol> obtieneRolesPorUsuarioPorId(String idUsuario) throws Exception {
		try {
			String queryStr = "SELECT u.rols FROM Usuario u  WHERE u.idUsuario = :idUsuario";
					

			TypedQuery<Rol> query = em.createQuery(queryStr, Rol.class);

			query.setParameter("idUsuario", idUsuario);
			List<Rol> roles = query.getResultList();
			return roles;
		} catch (NoResultException nr) {
			return new ArrayList<Rol>();
		}
	}

}

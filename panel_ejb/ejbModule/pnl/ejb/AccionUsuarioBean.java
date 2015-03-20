package pnl.ejb;

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

import pnl.interfaz.AccionUsuarioBeanRemote;
import pnl.modelo.AccionUsuario;
import pnl.qualificadores.AuditorGeneral;

/**
 * @generated DT_ID=none
 */
@AuditorGeneral
@Stateless(name = "AccionUsuarioBean")
public class AccionUsuarioBean implements AccionUsuarioBeanRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @generated DT_ID=none
	 */
	@Resource
	SessionContext sessionContext;

	/**
	 * @generated DT_ID=none
	 */
	@PersistenceContext(unitName = "Panel-EJB")
	private EntityManager em;

	/**
	 * @generated DT_ID=none
	 */
	public AccionUsuarioBean() {
	}

	/**
	 * @generated DT_ID=none
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Object queryByRange(String jpqlStmt, int firstResult, int maxResults) {
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
	public AccionUsuario persistAccionUsuario(AccionUsuario accionUsuario) {
		em.persist(accionUsuario);
		return accionUsuario;
	}

	/**
	 * @generated DT_ID=none
	 */
	public AccionUsuario mergeAccionUsuario(AccionUsuario accionUsuario) {
		return em.merge(accionUsuario);
	}

	/**
	 * @generated DT_ID=none
	 */
	public void removeAccionUsuario(AccionUsuario accionUsuario) {
		accionUsuario = em.find(AccionUsuario.class,
				accionUsuario.getIdAccionUsuario());
		em.remove(accionUsuario);
	}

	/**
	 * @generated DT_ID=none
	 */

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<AccionUsuario> getAccionUsuarioFindAll() {
		return em.createNamedQuery("AccionUsuario.findAll").getResultList();
	}

	@Override
	public AccionUsuario obtenerAccionUsuario(long idAccionUsuario)
			throws Exception {
		try {
			String queryStr = "SELECT a " + " FROM AccionUsuario a "
					+ " WHERE a.idAccionUsuario = :idAccionUsuario ";
			TypedQuery<AccionUsuario> query = em.createQuery(queryStr,
					AccionUsuario.class);
			query.setParameter("idAccionUsuario", idAccionUsuario);
			AccionUsuario accionUsuario = query.getSingleResult();
			return accionUsuario;
		} catch (NoResultException nr) {
			return null;
		}
	}

}

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
import pnl.interfaz.FiltroBeanRemote;
import pnl.modelo.Filtro;
import pnl.qualificadores.AuditorGeneral;


/**
 * @generated DT_ID=none
 */
@AuditorGeneral
@Stateless(name = "FiltroBean")
public class FiltroBean implements FiltroBeanRemote{
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
	public FiltroBean() {
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
	public Filtro persistFiltro(Filtro filtro) {
		em.persist(filtro);
		return filtro;
	}

	/**
	 * @generated DT_ID=none
	 */
	public Filtro mergeFiltro(Filtro filtro) {
		return em.merge(filtro);
	}

	/**
	 * @generated DT_ID=none
	 */
	public void removeFiltro(Filtro filtro) {
		filtro = em.find(Filtro.class, filtro.getIdFiltro());
		em.remove(filtro);
	}

	/**
	 * @generated DT_ID=none
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<Filtro> getFiltroFindAll() {
		return em.createNamedQuery("Filtro.findAll").getResultList();
	}

	@Override
	public void persistFiltros(List<Filtro> filtros) throws Exception {
		for( Filtro filtro : filtros ){
			this.persistFiltro(filtro);
		}

	}

	@Override
	public List<Filtro> obtenerFiltrosDeIndicadorPorIndicadorNivel(long idIndicador, String aNivelIndicador)
			throws Exception {
		try {
			String queryStr = "SELECT f FROM Filtro f "
					+ " LEFT JOIN f.indicador i "
					+ " WHERE i.idIndicador = :idIndicador "
					+ " AND i.estado = 'A' "
					+ " AND f.anivelIndicador = COALESCE(:aNivelIndicador,f.anivelIndicador) ORDER BY f.indiceFiltro ";

			TypedQuery<Filtro> query = em.createQuery(queryStr, Filtro.class);


			query.setParameter("idIndicador", idIndicador);
			query.setParameter("aNivelIndicador", aNivelIndicador);

			List<Filtro> pParametrosIndicadores = query.getResultList();

			return pParametrosIndicadores;
		} catch (NoResultException nr) {
			return new ArrayList<Filtro>();
		}
	}



	@Override
	public void removeFiltros(List<Filtro> filtros) throws Exception {
		for (Filtro filtro : filtros) {
			this.removeFiltro(filtro);
		}
		
	}
	
	public Filtro obtenerFiltroPorId(long idFiltro) throws Exception {

		try {
			
			String queryStr = "SELECT f "
					+ " FROM Filtro f "
					+ " WHERE f.idFiltro = :idFiltro ";
				
			TypedQuery<Filtro> query = em.createQuery(queryStr,Filtro.class);

			query.setParameter("idFiltro", idFiltro);
			
			Filtro filtro = query.getSingleResult();

			return filtro;
			
		} catch (NoResultException nr) {
			return null;
		}

	}
	
	

}

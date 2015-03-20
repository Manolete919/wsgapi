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
import pnl.interfaz.IndicadorSerieBeanRemote;
import pnl.modelo.IndicadorSerie;
import pnl.qualificadores.AuditorGeneral;


/**
 * @generated DT_ID=none
 */
@AuditorGeneral
@Stateless(name = "IndicadorSerieBean")
public class IndicadorSerieBean
        implements IndicadorSerieBeanRemote {
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
    public IndicadorSerieBean() {
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
    public IndicadorSerie persistIndicadorSerie(IndicadorSerie indicadorSerie) {
        em.persist(indicadorSerie);
        return indicadorSerie;
    }

    /**
     * @generated DT_ID=none
     */
    public IndicadorSerie mergeIndicadorSerie(IndicadorSerie indicadorSerie) {
        return em.merge(indicadorSerie);
    }

    /**
     * @generated DT_ID=none
     */
    public void removeIndicadorSerie(IndicadorSerie indicadorSerie) {
        indicadorSerie = em.find(IndicadorSerie.class, indicadorSerie.getIdSerie());
        em.remove(indicadorSerie);
    }

    /**
     * @generated DT_ID=none
     */
	@SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<IndicadorSerie> getIndicadorSerieFindAll() {
        return em.createNamedQuery("IndicadorSerie.findAll").getResultList();
    }



	@Override
	public void persistIndicadorSeries(List<IndicadorSerie> indicadorSeries) throws Exception {
		for(IndicadorSerie indicadorSerie : indicadorSeries ){
			this.persistIndicadorSerie(indicadorSerie);
		}
		
	}



	@Override
	public List<IndicadorSerie> obtenerIndicadorSeriePorIdIndicadorEstado(
			long idIndicador, String estado) throws Exception {
	
		try{
			String queryStr = "SELECT iss FROM IndicadorSerie iss " 
					+ " LEFT JOIN iss.indicador i "
					+ " WHERE i.idIndicador = :idIndicador"
					+ " AND iss.estado = COALESCE(:estado,iss.estado) " ;
					

			TypedQuery<IndicadorSerie> query = em.createQuery(queryStr,
					IndicadorSerie.class);

			
			query.setParameter("idIndicador", idIndicador );
			query.setParameter("estado", estado );
			

			List<IndicadorSerie> IndicadorSeries = query.getResultList();

			return IndicadorSeries;
			
		}catch(NoResultException nr ){
			
			return new ArrayList<IndicadorSerie>();
			
		}
		
	
	
	}



	@Override
	public void removeIndicadorSeries(List<IndicadorSerie> indicadorSeries)
			throws Exception {
		for(IndicadorSerie indicadorSerie : indicadorSeries){
			this.removeIndicadorSerie(indicadorSerie);
		}
	}
	
	public IndicadorSerie obtenerIndicadorSeriePorId(long idSerie) throws Exception {

		try {

			String queryStr = "SELECT iss FROM IndicadorSerie iss "
					+ " WHERE iss.idSerie = :idSerie ";

			TypedQuery<IndicadorSerie> query = em.createQuery(queryStr,IndicadorSerie.class);

			query.setParameter("idSerie", idSerie);
			
			IndicadorSerie indicadorSerie = query.getSingleResult();
			
			return indicadorSerie;

		} catch (NoResultException nr) {
			return null;
		}

	}




}

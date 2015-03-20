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
import pnl.interfaz.IndicadorBeanRemote;
import pnl.modelo.Indicador;
import pnl.qualificadores.AuditorGeneral;


/**
 * @generated DT_ID=none
 */
@AuditorGeneral
@Stateless(name = "IndicadorBean")
public class IndicadorBean
        implements IndicadorBeanRemote {
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
    public IndicadorBean() {
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
    public Indicador persistIndicador(Indicador indicador) {
        em.persist(indicador);
        return indicador;
    }

    /**
     * @generated DT_ID=none
     */
    public Indicador mergeIndicador(Indicador indicador) {
        return em.merge(indicador);
    }

    /**
     * @generated DT_ID=none
     */
    public void removeIndicador(Indicador indicador) {
        indicador = em.find(Indicador.class, indicador.getIdIndicador());
        em.remove(indicador);
    }

    /**
     * @generated DT_ID=none
     */
	@SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Indicador> getIndicadorFindAll() {
        return em.createNamedQuery("Indicador.findAll").getResultList();
    }



	@Override
	public void removeIndicadores(List<Indicador> selectedIndicadores)
			throws Exception {
		// TODO Auto-generated method stub
		
		for(Indicador indicador : selectedIndicadores){
			this.removeIndicador(indicador);
		}
		
	}

	public Indicador obtenerIndicadorPorId(long idIndicador) throws Exception {

		try {

			String queryStr = "SELECT i FROM Indicador i "
					+ " WHERE i.idIndicador = :idIndicador ";

			TypedQuery<Indicador> query = em.createQuery(queryStr,
					Indicador.class);

			query.setParameter("idIndicador", idIndicador);
			Indicador indicador = query.getSingleResult();
			return indicador;

		} catch (NoResultException nr) {
			return null;
		}

	}






}

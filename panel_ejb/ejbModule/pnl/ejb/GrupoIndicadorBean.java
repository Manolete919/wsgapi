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

import pnl.interfaz.GrupoIndicadorBeanRemote;
import pnl.modelo.GrupoIndicador;
import pnl.modelo.GrupoIndicadorPK;
import pnl.modelo.Indicador;
import pnl.modelo.UsuarioGrupo;


/**
 * @generated DT_ID=none
 */
@Stateless(name = "GrupoIndicadorBean")
public class GrupoIndicadorBean
        implements  GrupoIndicadorBeanRemote, Serializable {
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
    public GrupoIndicadorBean() {
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
    public GrupoIndicador persistGrupoIndicador(GrupoIndicador grupoIndicador) {
        em.persist(grupoIndicador);
        return grupoIndicador;
    }

    /**
     * @generated DT_ID=none
     */
    public GrupoIndicador mergeGrupoIndicador(GrupoIndicador grupoIndicador) {
        return em.merge(grupoIndicador);
    }

    /**
     * @generated DT_ID=none
     */
    public void removeGrupoIndicador(GrupoIndicador grupoIndicador) {
        GrupoIndicadorPK grupoIndicadorPK = new GrupoIndicadorPK();
        grupoIndicadorPK.setIdGrupo(grupoIndicador.getId().getIdGrupo());
        grupoIndicadorPK.setIdIndicador(grupoIndicador.getId().getIdIndicador());
        grupoIndicador = em.find(GrupoIndicador.class, grupoIndicadorPK);
        em.remove(grupoIndicador);
    }

    /**
     * @generated DT_ID=none
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<GrupoIndicador> getGrupoIndicadorFindAll() {
        return em.createNamedQuery("GrupoIndicador.findAll").getResultList();
    }
    

	@Override
	public Indicador obtieneIndicadorPorIdYUsuario(long idIndicador, String idUsuario) throws Exception {
		
		try {
			String queryStr = "SELECT DISTINCT i FROM GrupoIndicador gi " 
					+ " LEFT JOIN gi.indicador i "
					+ " LEFT JOIN gi.grupo g "
					+ " LEFT JOIN g.usuarioGrupos ug "
					+ " LEFT JOIN ug.usuario u "
					+ " WHERE u.idUsuario =  :idUsuario "
					+ " AND i.idIndicador = :idIndicador ";
			

			TypedQuery<Indicador> query = em.createQuery(queryStr,
					Indicador.class);

			query.setParameter("idIndicador", idIndicador);
			query.setParameter("idUsuario", idUsuario);
			
			Indicador pIndicador = query.getSingleResult();
			return pIndicador;
			
		}catch(NoResultException nr ){
			return new Indicador();
		}
		
	}



	@Override
	public List<Indicador> obtieneIndicadoresPorIdUsuario(String idUsuario)
			throws Exception {
		try {
			String queryStr = "SELECT DISTINCT i FROM GrupoIndicador gi " 
					+ " LEFT JOIN gi.indicador i "
					+ " LEFT JOIN gi.grupo g "
					+ " LEFT JOIN g.usuarioGrupos ug "
					+ " LEFT JOIN ug.usuario u "
					+ " WHERE u.idUsuario =  :idUsuario " ;
					
	
			TypedQuery<Indicador> query = em.createQuery(queryStr,
					Indicador.class);
	
			query.setParameter("idUsuario", idUsuario);
			
			List<Indicador> indicadores = query.getResultList();
			return indicadores;
		}catch(NoResultException nr ){
			return new ArrayList<Indicador>();
		}
		
	}



	@Override
	public List<GrupoIndicador> obtieneIndicadorGruposPorIdIndicador( long idIndicador ) throws Exception {
		try {
			String queryStr = "SELECT gi FROM GrupoIndicador gi " 
					+ " LEFT JOIN gi.grupo g "
					+ " LEFT JOIN gi.indicador i "
					+ " LEFT JOIN g.usuarioGrupos ug "
					+ " LEFT JOIN ug.usuario u "
					+ " WHERE i.idIndicador =  :idIndicador ";
					
			

			TypedQuery<GrupoIndicador> query = em.createQuery(queryStr,
					GrupoIndicador.class);

			query.setParameter("idIndicador", idIndicador);
			
			List<GrupoIndicador> indicadorGrupos = query.getResultList();
			return indicadorGrupos;
			
		}catch(NoResultException nr ){
			return new ArrayList<GrupoIndicador>();
		}
	}



	@Override
	public void persistGrupoIndicadores(List<GrupoIndicador> grupoIndicadores)
			throws Exception {
		for(GrupoIndicador grupoIndicador : grupoIndicadores ){
			this.persistGrupoIndicador(grupoIndicador);
		}
		
	}



	@Override
	public void removeGrupoIndicadores(
			List<GrupoIndicador> grupoIndicadores) {
		for(GrupoIndicador grupoIndicador : grupoIndicadores ){
			this.removeGrupoIndicador(grupoIndicador);
		}
		
	}



	@Override
	public void mergeGrupoIndicadores(
			List<GrupoIndicador> grupoIndicadores) throws Exception {
		for(GrupoIndicador grupoIndicador : grupoIndicadores ){
			this.mergeGrupoIndicador(grupoIndicador);
		}
		
	}





}

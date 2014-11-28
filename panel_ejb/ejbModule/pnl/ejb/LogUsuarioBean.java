package pnl.ejb;

import java.util.ArrayList;
import java.util.Date;
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
import pnl.interfaz.LogUsuarioBeanRemote;
import pnl.modelo.LogUsuario;


/**
 * @generated DT_ID=none
 */
@Stateless(name = "LogUsuarioBean")
public class LogUsuarioBean
        implements LogUsuarioBeanRemote
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
    public LogUsuarioBean() {
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
    public LogUsuario persistLogUsuario(LogUsuario logUsuario) {
        em.persist(logUsuario);
        return logUsuario;
    }

    /**
     * @generated DT_ID=none
     */
    public LogUsuario mergeLogUsuario(LogUsuario logUsuario) {
        return em.merge(logUsuario);
    }

    /**
     * @generated DT_ID=none
     */
    public void removeLogUsuario(LogUsuario logUsuario) {
        logUsuario = em.find(LogUsuario.class, logUsuario.getIdLogUsuario());
        em.remove(logUsuario);
    }

    /**
     * @generated DT_ID=none
     */
	@SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<LogUsuario> getLogUsuarioFindAll() {
        return em.createNamedQuery("LogUsuario.findAll").getResultList();
    }



	@Override
	public List<LogUsuario> obtenerHistorial(
			String idUsuario,
			long idRecursosApp, 
			long idAccionUsuario, 
			String palabraClave,
			Date fechaInicial, 
			Date fechaFinal) throws Exception {
		try {
			
			String queryStr = "SELECT lu "
					+ " FROM LogUsuario lu "
					+ " LEFT JOIN lu.usuario u "
					+ " LEFT JOIN lu.accionUsuario au "
					+ " LEFT JOIN lu.recursosApp ra "
					+ " WHERE UPPER(TRIM(u.idUsuario)) = UPPER(TRIM(COALESCE(:idUsuario,u.idUsuario))) "
					+ " AND ra.idRecursosApp = "+ ((idRecursosApp!=-1)?":idRecursosApp ":"ra.idRecursosApp ")
					+ " AND au.idAccionUsuario = " + ((idAccionUsuario!=-1)?":idAccionUsuario ":"au.idAccionUsuario ") 				
					+ " AND UPPER(TRIM(lu.detalle)) like COALESCE(UPPER(TRIM(:palabraClave)),UPPER(TRIM(lu.detalle))) "
					+ " AND lu.fecha  BETWEEN :fechaInicial AND :fechaFinal ";
				

			TypedQuery<LogUsuario> query = em.createQuery(queryStr,LogUsuario.class);

			query.setParameter("idUsuario", idUsuario);
			if(idRecursosApp != -1 )
				query.setParameter("idRecursosApp", idRecursosApp);
			if(idAccionUsuario != -1 )
				query.setParameter("idAccionUsuario", idAccionUsuario);
			
			if(palabraClave!= null){
				if(palabraClave.equals("")){
					palabraClave = null;
				}
			}
			query.setParameter("palabraClave", palabraClave);
			query.setParameter("fechaInicial", fechaInicial);
			query.setParameter("fechaFinal", fechaFinal);
			
			List<LogUsuario> logUsuarios = query.getResultList();
			
			return logUsuarios;
			
		} catch (NoResultException nr) {
			return new ArrayList<LogUsuario>();
		}
	}

}

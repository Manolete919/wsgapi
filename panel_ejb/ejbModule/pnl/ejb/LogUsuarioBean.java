package pnl.ejb;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

}

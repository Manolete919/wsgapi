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

import pnl.interfaz.RecursosAppBeanRemote;
import pnl.modelo.RecursosApp;


/**
 * @generated DT_ID=none
 */
@Stateless(name = "RecursosAppBean")
public class RecursosAppBean implements RecursosAppBeanRemote
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
    public RecursosAppBean() {
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
    public RecursosApp persistRecursosApp(RecursosApp recursosApp) {
        em.persist(recursosApp);
        return recursosApp;
    }

    /**
     * @generated DT_ID=none
     */
    public RecursosApp mergeRecursosApp(RecursosApp recursosApp) {
        return em.merge(recursosApp);
    }

    /**
     * @generated DT_ID=none
     */
    public void removeRecursosApp(RecursosApp recursosApp) {
        recursosApp = em.find(RecursosApp.class, recursosApp.getIdRecursosApp());
        em.remove(recursosApp);
    }

    /**
     * @generated DT_ID=none
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<RecursosApp> getRecursosAppFindAll() {
        return em.createNamedQuery("RecursosApp.findAll").getResultList();
    }

}

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

import pnl.interfaz.ModeloGraficoBeanRemote;
import pnl.modelo.ModeloGrafico;


/**
 * @generated DT_ID=none
 */
@Stateless(name = "ModeloGraficoBean")
public class ModeloGraficoBean
        implements  ModeloGraficoBeanRemote,Serializable {
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
    public ModeloGraficoBean() {
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
    public ModeloGrafico persistModeloGrafico(ModeloGrafico modeloGrafico) {
        em.persist(modeloGrafico);
        return modeloGrafico;
    }

    /**
     * @generated DT_ID=none
     */
    public ModeloGrafico mergeModeloGrafico(ModeloGrafico modeloGrafico) {
        return em.merge(modeloGrafico);
    }

    /**
     * @generated DT_ID=none
     */
    public void removeModeloGrafico(ModeloGrafico modeloGrafico) {
        modeloGrafico = em.find(ModeloGrafico.class, modeloGrafico.getIdModelo());
        em.remove(modeloGrafico);
    }

    /**
     * @generated DT_ID=none
     */
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<ModeloGrafico> getModeloGraficoFindAll() throws Exception {
    	try{
    		return em.createNamedQuery("ModeloGrafico.findAll").getResultList();
    	}catch(NoResultException nr){
    		return new ArrayList<ModeloGrafico>();
    	}
    }



}

package wsg.beanAbstracto;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.Query;


/**
 *
 * @author Desarrollo
 */
public abstract class AbstractBean<T> {
	
	
    private Class<T> entityClass;

    public AbstractBean(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();
    protected abstract EntityManagerFactory getEntityManagerFactory();

    public void create(T entity){
        getEntityManager().persist(entity);
        getEntityManager().close();
        getEntityManagerFactory().close();
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
        getEntityManager().close();
        getEntityManagerFactory().close();
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
        getEntityManager().close();
        getEntityManagerFactory().close();
    }

    public T find(Object id) {
    	
        T objeto = getEntityManager().find(entityClass, id);
        getEntityManager().close();
        getEntityManagerFactory().close();
        return objeto;
        
        
        
    }
    
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
    	@SuppressWarnings("rawtypes")
		CriteriaQuery  cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        List<T> objetos = getEntityManager().createQuery(cq).getResultList();
        getEntityManager().close();
        getEntityManagerFactory().close();
        return objetos;
    }

  
    @SuppressWarnings("unchecked")
    public List<T> findRange(int[] range) {
        @SuppressWarnings("rawtypes")
		CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        List<T> objetos = q.getResultList();
        getEntityManager().close();
        getEntityManagerFactory().close();
        return objetos;
    }

    @SuppressWarnings("unchecked")
    public int count() {
    	@SuppressWarnings("rawtypes")
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        Query q = getEntityManager().createQuery(cq);
        int cant =  ((Long) q.getSingleResult()).intValue();
        getEntityManager().close();
        getEntityManagerFactory().close();
        return cant;
    }
    
    
}

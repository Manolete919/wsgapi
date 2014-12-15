package wsg.bean;


import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import wsg.beanAbstracto.AbstractBean;
import wsg.interfaz.WsgServicioBeanRemote;
import wsg.modelo.WsgServicio;
import wsg.qualificadores.AuditorGeneral;

/**
 * Session Bean implementation class WsgServicioBean
 */
@AuditorGeneral
@Stateless
public class WsgServicioBean extends AbstractBean<WsgServicio> implements WsgServicioBeanRemote , Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final Logger logger = Logger.getLogger(WsgServicio.class);
	private Properties propiedades = new Properties();

	private EntityManager em;
	
	private EntityManagerFactory entityManagerFactory = null;
	
	/**
     * Default constructor. 
     */
    public WsgServicioBean() {
        super(WsgServicio.class);
    }
       


	@Override
	protected EntityManager getEntityManager() {
		InputStream iostream = Thread.currentThread().getContextClassLoader().getResourceAsStream("properties/wsg-ejb-ear.properties");
		try {
			propiedades.load(iostream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		   
	    em = getEntityManagerFactory().createEntityManager();	
	    
		return em;
		
	}



	public EntityManagerFactory getEntityManagerFactory() {
		
		entityManagerFactory = Persistence.createEntityManagerFactory(propiedades.getProperty("wsgejb.pu"));
		
		return entityManagerFactory;
		
	}



	@Override
	public WsgServicio buscarServicioPorIdActivoYVigente(long idServicio)
			throws Exception {
		try{
			em = getEntityManager();
			
			String queryStr = "SELECT si FROM WsgServicio si "			
			+ "WHERE si.estado = 'A' "
			+ "AND CURRENT_TIMESTAMP BETWEEN COALESCE(si.fechaDesde,CURRENT_TIMESTAMP) "
			+ "AND COALESCE(si.fechaHasta,CURRENT_TIMESTAMP) "
			+ "AND si.idServicio = :idServicio ";

					
			TypedQuery<WsgServicio> query = em.createQuery(queryStr,
					WsgServicio.class);

			query.setParameter("idServicio", idServicio);
			
			WsgServicio wsgServicio = query.getSingleResult();
			
			return wsgServicio;

		}catch( NoResultException nr ){
			return null;
		}finally{
			em.close();
			entityManagerFactory.close();
		}
	}
	

}

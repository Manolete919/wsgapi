package wsg.bean;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import wsg.beanAbstracto.AbstractBean;
import wsg.interfaz.WsgServicioBeanRemote;
import wsg.modelo.WsgServicio;
import wsg.qualificadores.AuditorGeneral;

/**
 * Session Bean implementation class WsgServicioBean
 */
@AuditorGeneral
@Stateless
public class WsgServicioBean extends AbstractBean<WsgServicio> implements WsgServicioBeanRemote{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	@Inject
	private EntityManager em;
	
	
	/**
     * Default constructor. 
     */
    public WsgServicioBean() {
        super(WsgServicio.class);
    }
       


	@Override
	protected EntityManager getEntityManager() {
    
		return em;
		
	}






	@Override
	public WsgServicio buscarServicioPorIdActivoYVigente(long idServicio)
			throws Exception {
		try{
					
			TypedQuery<WsgServicio> query = em.createNamedQuery("buscarServicioPorIdActivoYVigente",WsgServicio.class);

			query.setParameter("idServicio", idServicio);
			
			WsgServicio wsgServicio = query.getSingleResult();
			
			return wsgServicio;

		}catch( NoResultException nr ){
			return null;
		}
	}
	

}

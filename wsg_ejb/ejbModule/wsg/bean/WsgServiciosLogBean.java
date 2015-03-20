package wsg.bean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import wsg.beanAbstracto.AbstractBean;
import wsg.interfaz.WsgServiciosLogBeanRemote;
import wsg.modelo.WsgServiciosLog;
import wsg.qualificadores.AuditorGeneral;

/**
 * Session Bean implementation class WsgServiciosLogBean
 */
@AuditorGeneral
@Stateless
public class WsgServiciosLogBean extends AbstractBean<WsgServiciosLog>  implements WsgServiciosLogBeanRemote{

   
   
 
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;
	


	/**
     * Default constructor. 
     */
    public WsgServiciosLogBean() {
        super(WsgServiciosLog.class);
    }




	
	@Override
	protected EntityManager getEntityManager() {
		   
		return em;
		
	}



	

}

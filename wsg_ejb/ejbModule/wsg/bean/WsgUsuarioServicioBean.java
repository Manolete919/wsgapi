package wsg.bean;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import wsg.beanAbstracto.AbstractBean;
import wsg.interfaz.WsgUsuarioServicioBeanRemote;
import wsg.modelo.WsgUsuarioServicio;
import wsg.qualificadores.AuditorGeneral;


/**
 * Session Bean implementation class UsuarioBean
 */
@AuditorGeneral
@Stateless
public class WsgUsuarioServicioBean extends AbstractBean<WsgUsuarioServicio> implements WsgUsuarioServicioBeanRemote{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final Logger logger = LogManager.getLogger(WsgJndiBean.class);

	@Inject
	private EntityManager em;
	
     
    /**
     * @see AbstractBean#AbstractBean(Class<T>)
     */
    public WsgUsuarioServicioBean() {
        super(WsgUsuarioServicio.class);
    }

	@Override
	protected EntityManager getEntityManager() {
		
		return em;
		
	}


}

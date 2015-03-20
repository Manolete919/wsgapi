package wsg.bean;


import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import wsg.beanAbstracto.AbstractBean;
import wsg.interfaz.WsgJndiBeanRemote;
import wsg.modelo.WsgJndi;
import wsg.qualificadores.AuditorGeneral;

/**
 * Session Bean implementation class EisGrupoBean
 */
@AuditorGeneral
@Stateless
public class WsgJndiBean extends AbstractBean<WsgJndi> implements WsgJndiBeanRemote{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7937999219853273371L;


	/**
     * Default constructor. 
     */

	@Inject
	private EntityManager em;
	
	
	
	
    public WsgJndiBean() {
        super(WsgJndi.class);
    }
    

    
    
    


	@Override
	protected EntityManager getEntityManager() {
    
		return em;
		
	}



}

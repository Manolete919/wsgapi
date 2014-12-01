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

import pnl.interfaz.UsuariosWsgBeanRemote;
import pnl.modelo.UsuariosWsg;
import pnl.qualificadores.AuditorGeneral;


/**
 * @generated DT_ID=none
 */
@AuditorGeneral
@Stateless(name = "UsuariosWsgBean")
public class UsuariosWsgBean implements UsuariosWsgBeanRemote
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
    public UsuariosWsgBean() {
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
    public UsuariosWsg persistUsuariosWsg(UsuariosWsg usuariosWsg) {
        em.persist(usuariosWsg);
        return usuariosWsg;
    }

    /**
     * @generated DT_ID=none
     */
    public UsuariosWsg mergeUsuariosWsg(UsuariosWsg usuariosWsg) {
        return em.merge(usuariosWsg);
    }

    /**
     * @generated DT_ID=none
     */
    public void removeUsuariosWsg(UsuariosWsg usuariosWsg) {
        usuariosWsg = em.find(UsuariosWsg.class, usuariosWsg.getIdUsuario());
        em.remove(usuariosWsg);
    }

    /**
     * @generated DT_ID=none
     */
	@SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<UsuariosWsg> getUsuariosWsgFindAll() {
        return em.createNamedQuery("UsuariosWsg.findAll").getResultList();
    }

}

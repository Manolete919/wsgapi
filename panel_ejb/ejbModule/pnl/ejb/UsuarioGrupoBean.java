package pnl.ejb;

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
import javax.persistence.TypedQuery;
import pnl.interfaz.UsuarioGrupoBeanRemote;
import pnl.modelo.UsuarioGrupo;
import pnl.modelo.UsuarioGrupoPK;
import pnl.qualificadores.AuditorGeneral;


/**
 * @generated DT_ID=none
 */
@AuditorGeneral
@Stateless(name = "UsuarioGrupoBean")
public class UsuarioGrupoBean  implements  UsuarioGrupoBeanRemote 
{

    /**
	 * 
	 */
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
    public UsuarioGrupoBean() {
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
    public UsuarioGrupo persistUsuarioGrupo(UsuarioGrupo usuarioGrupo) {
        em.persist(usuarioGrupo);
        return usuarioGrupo;
    }

    /**
     * @generated DT_ID=none
     */
    public UsuarioGrupo mergeUsuarioGrupo(UsuarioGrupo usuarioGrupo) {
        return em.merge(usuarioGrupo);
    }

    /**
     * @generated DT_ID=none
     */
    public void removeUsuarioGrupo(UsuarioGrupo usuarioGrupo) {
        UsuarioGrupoPK usuarioGrupoPK = new UsuarioGrupoPK();
        usuarioGrupoPK.setIdUsuario(usuarioGrupo.getId().getIdUsuario());
        usuarioGrupoPK.setIdGrupo(usuarioGrupo.getId().getIdGrupo());
        usuarioGrupo = em.find(UsuarioGrupo.class, usuarioGrupoPK);
        em.remove(usuarioGrupo);
    }

    /**
     * @generated DT_ID=none
     */

    @SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<UsuarioGrupo> getUsuarioGrupoFindAll() {
        return em.createNamedQuery("UsuarioGrupo.findAll").getResultList();
    }



	@Override
	public void removeUsuarioGrupos(List<UsuarioGrupo> usuarioGrupos)
			throws Exception {
		for(UsuarioGrupo usuarioGrupo : usuarioGrupos){
			
			this.removeUsuarioGrupo(usuarioGrupo);
			
		}
		
	}




	@Override
	public List<UsuarioGrupo> obtenerGruposPorIdUSuarioEstado(String idUsuario, String estado)
			throws Exception {
		try {
			String queryStr = "SELECT ug FROM UsuarioGrupo ug " 
					+ " LEFT JOIN ug.usuario u "
					+ " LEFT JOIN ug.grupo g "
					+ " WHERE u.idUsuario =  :idUsuario AND g.estado = COALESCE(:estado,g.estado) ";

			TypedQuery<UsuarioGrupo> query = em.createQuery(queryStr,
					UsuarioGrupo.class);

			query.setParameter("idUsuario", idUsuario);
			query.setParameter("estado", estado);
			
			List<UsuarioGrupo> usuarioGrupos = query.getResultList();
			return usuarioGrupos;
			
		}catch(NoResultException nr ){
			return new ArrayList<UsuarioGrupo>();
		}
	}
	
	@Override
	public List<UsuarioGrupo> obtenerGruposPorIdUSuarioNoOcupados(String idUsuario)
			throws Exception {
		try {
			String queryStr = "SELECT ug FROM UsuarioGrupo ug " 
					+ " LEFT JOIN ug.usuario u "
					+ " LEFT JOIN ug.grupo g "
					+ " WHERE u.idUsuario =  :idUsuario AND NOT EXISTS(SELECT gi FROM GrupoIndicador gi WHERE gi.grupo = g )";

			TypedQuery<UsuarioGrupo> query = em.createQuery(queryStr,
					UsuarioGrupo.class);

			query.setParameter("idUsuario", idUsuario);
			
			List<UsuarioGrupo> usuarioGrupos = query.getResultList();
			return usuarioGrupos;
			
		}catch(NoResultException nr ){
			return new ArrayList<UsuarioGrupo>();
		}
	}

}

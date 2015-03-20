package pnl.ejb;


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

import pnl.interfaz.GrupoBeanRemote;
import pnl.modelo.Grupo;
import pnl.modelo.UsuarioGrupo;
import pnl.qualificadores.AuditorGeneral;


/**
 * @generated DT_ID=none
 */
@AuditorGeneral
@Stateless(name = "GrupoBean")
public class GrupoBean implements GrupoBeanRemote
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
    public GrupoBean() {
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
    public Grupo persistGrupo(Grupo grupo) {
        em.persist(grupo);
        return grupo;
    }

    /**
     * @generated DT_ID=none
     */
    public Grupo mergeGrupo(Grupo grupo) {
        return em.merge(grupo);
    }

    /**
     * @generated DT_ID=none
     */
    public void removeGrupo(Grupo grupo) {
        grupo = em.find(Grupo.class, grupo.getIdGrupo());
        em.remove(grupo);
    }

    /**
     * @generated DT_ID=none
     */
	@SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Grupo> getGrupoFindAll() {
        return em.createNamedQuery("Grupo.findAll").getResultList();
    }



	@Override
	public void removeGrupos(List<UsuarioGrupo> selectedGrupos)
			throws Exception {
		for(UsuarioGrupo usuarioGrupo : selectedGrupos ){
			
			//si el grupo no esta siendo utilizado, eliminarlo
			if(!buscarGrupoPorId(usuarioGrupo.getGrupo().getIdGrupo())){
				this.removeGrupo(usuarioGrupo.getGrupo());
			}
			
		}
		
	}



	private boolean buscarGrupoPorId(long idGrupo) {

			try {
				
				String queryStr = "SELECT gi "
						+ " FROM GrupoIndicador gi "
						+ " LEFT JOIN gi.grupo g "
						+ " LEFT JOIN gi.indicador i "
						+ " WHERE g.idGrupo = :idGrupo ";
					

				TypedQuery<Grupo> query = em.createQuery(queryStr,Grupo.class);

				query.setParameter("idGrupo", idGrupo);
				List<Grupo> grupos = query.getResultList();
				if(grupos.isEmpty()){
					return false;
				}
				return true;
			} catch (NoResultException nr) {
				return false;
			}

	}
	
	
	public Grupo obtenerGrupoPorId(long idGrupo) throws Exception {

		try {
			
			String queryStr = "SELECT g "
					+ " FROM Grupo g "
					+ " WHERE g.idGrupo = :idGrupo ";
				
			TypedQuery<Grupo> query = em.createQuery(queryStr,Grupo.class);

			query.setParameter("idGrupo", idGrupo);
			
			Grupo grupo = query.getSingleResult();

			return grupo;
		} catch (NoResultException nr) {
			return null;
		}

	}


}

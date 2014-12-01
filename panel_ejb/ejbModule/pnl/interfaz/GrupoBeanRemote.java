package pnl.interfaz;

import java.util.List;

import javax.ejb.Remote;

import pnl.modelo.Grupo;
import pnl.modelo.UsuarioGrupo;


/**
 * @generated DT_ID=none
 */
@Remote
public interface GrupoBeanRemote
{

    /**
     * @generated DT_ID=none
     */
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    /**
     * @generated DT_ID=none
     */
    public Grupo persistGrupo(Grupo grupo);

    /**
     * @generated DT_ID=none
     */
    public Grupo mergeGrupo(Grupo grupo);

    /**
     * @generated DT_ID=none
     */
    public void removeGrupo(Grupo grupo);

    /**
     * @generated DT_ID=none
     */
    public List<Grupo> getGrupoFindAll();

	public void removeGrupos(List<UsuarioGrupo> selectedGrupos) throws Exception;
	
	public Grupo obtenerGrupoPorId(long idGrupo) throws Exception;

}

package pnl.interfaz;

import java.util.List;

import javax.ejb.Remote;

import pnl.modelo.UsuarioGrupo;



/**
 * @generated DT_ID=none
 */
@Remote
public interface UsuarioGrupoBeanRemote
{

    /**
     * @generated DT_ID=none
     */
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    /**
     * @generated DT_ID=none
     */
    public UsuarioGrupo persistUsuarioGrupo(UsuarioGrupo usuarioGrupo);

    /**
     * @generated DT_ID=none
     */
    public UsuarioGrupo mergeUsuarioGrupo(UsuarioGrupo usuarioGrupo);

    /**
     * @generated DT_ID=none
     */
    public void removeUsuarioGrupo(UsuarioGrupo usuarioGrupo);

    /**
     * @generated DT_ID=none
     */
    public List<UsuarioGrupo> getUsuarioGrupoFindAll();

	public void removeUsuarioGrupos(List<UsuarioGrupo> usuarioGrupos) throws Exception;

	public void mergeUsuarioGrupos(List<UsuarioGrupo> usuarioGrupos) throws Exception;

	public  List<UsuarioGrupo> obtenerGruposPorIdUSuarioEstado(String idUsuario, String estado) throws Exception;

}

package pnl.interfaz;

import java.util.List;

import javax.ejb.Remote;

import pnl.modelo.Usuario;


/**
 * @generated DT_ID=none
 */
@Remote
public interface UsuarioBeanRemote
{

    /**
     * @generated DT_ID=none
     */
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    /**
     * @generated DT_ID=none
     */
    public Usuario persistUsuario(Usuario usuario);

    /**
     * @generated DT_ID=none
     */
    public Usuario mergeUsuario(Usuario usuario);

    /**
     * @generated DT_ID=none
     */
    public void removeUsuario(Usuario usuario);

    /**
     * @generated DT_ID=none
     */
    public List<Usuario> getUsuarioFindAll();

	Usuario obtieneUsuarioPorId(String idUsuario) throws Exception;

}

package pnl.interfaz;

import java.util.List;
import javax.ejb.Remote;
import pnl.modelo.AccionUsuario;


/**
 * @generated DT_ID=none
 */
@Remote
public interface AccionUsuarioBeanRemote
{

    /**
     * @generated DT_ID=none
     */
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    /**
     * @generated DT_ID=none
     */
    public AccionUsuario persistAccionUsuario(AccionUsuario accionUsuario);

    /**
     * @generated DT_ID=none
     */
    public AccionUsuario mergeAccionUsuario(AccionUsuario accionUsuario);

    /**
     * @generated DT_ID=none
     */
    public void removeAccionUsuario(AccionUsuario accionUsuario);

    /**
     * @generated DT_ID=none
     */
    public List<AccionUsuario> getAccionUsuarioFindAll();
    
    public AccionUsuario obtenerAccionUsuario(long idAccionUsuario) throws Exception;

}

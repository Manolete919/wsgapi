package pnl.interfaz;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import pnl.modelo.LogUsuario;


/**
 * @generated DT_ID=none
 */
@Remote
public interface LogUsuarioBeanRemote extends Serializable
{

    /**
     * @generated DT_ID=none
     */
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    /**
     * @generated DT_ID=none
     */
    public LogUsuario persistLogUsuario(LogUsuario logUsuario);

    /**
     * @generated DT_ID=none
     */
    public LogUsuario mergeLogUsuario(LogUsuario logUsuario);

    /**
     * @generated DT_ID=none
     */
    public void removeLogUsuario(LogUsuario logUsuario);

    /**
     * @generated DT_ID=none
     */
    public List<LogUsuario> getLogUsuarioFindAll();

	public List<LogUsuario> obtenerHistorial(String idUsuario, long idRecursosApp,
			long idAccionUsuario, String palabraClave, Date fechaInicial,
			Date fechaFinal) throws Exception;

	

}

package pnl.interfaz;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import pnl.modelo.Rol;


/**
 * @generated DT_ID=none
 */
@Remote
public interface RolBeanRemote extends Serializable
{

    /**
     * @generated DT_ID=none
     */
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    /**
     * @generated DT_ID=none
     */
    public Rol persistRol(Rol rol);

    /**
     * @generated DT_ID=none
     */
    public Rol mergeRol(Rol rol);

    /**
     * @generated DT_ID=none
     */
    public void removeRol(Rol rol);

    /**
     * @generated DT_ID=none
     */
    public List<Rol> getRolFindAll();

	public List<Rol> obtieneRolesPorUsuarioPorId(String login) throws Exception;

}

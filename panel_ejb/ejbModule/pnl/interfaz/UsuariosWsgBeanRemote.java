package pnl.interfaz;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import pnl.modelo.UsuariosWsg;


/**
 * @generated DT_ID=none
 */
@Remote
public interface UsuariosWsgBeanRemote extends Serializable
{

    /**
     * @generated DT_ID=none
     */
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    /**
     * @generated DT_ID=none
     */
    public UsuariosWsg persistUsuariosWsg(UsuariosWsg usuariosWsg);

    /**
     * @generated DT_ID=none
     */
    public UsuariosWsg mergeUsuariosWsg(UsuariosWsg usuariosWsg);

    /**
     * @generated DT_ID=none
     */
    public void removeUsuariosWsg(UsuariosWsg usuariosWsg);

    /**
     * @generated DT_ID=none
     */
    public List<UsuariosWsg> getUsuariosWsgFindAll();

}

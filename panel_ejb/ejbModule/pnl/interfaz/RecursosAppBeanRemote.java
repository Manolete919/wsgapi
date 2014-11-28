package pnl.interfaz;

import java.util.List;

import javax.ejb.Remote;

import pnl.modelo.RecursosApp;


/**
 * @generated DT_ID=none
 */
@Remote
public interface RecursosAppBeanRemote
{

    /**
     * @generated DT_ID=none
     */
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    /**
     * @generated DT_ID=none
     */
    public RecursosApp persistRecursosApp(RecursosApp recursosApp);

    /**
     * @generated DT_ID=none
     */
    public RecursosApp mergeRecursosApp(RecursosApp recursosApp);

    /**
     * @generated DT_ID=none
     */
    public void removeRecursosApp(RecursosApp recursosApp);

    /**
     * @generated DT_ID=none
     */
    public List<RecursosApp> getRecursosAppFindAll();
    
    public RecursosApp obtenerRecursosAppPorId(long idRecursosApp) throws Exception;
    

}

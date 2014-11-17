package pnl.interfaz;

import java.util.List;
import javax.ejb.Remote;
import pnl.modelo.GrupoIndicador;
import pnl.modelo.Indicador;



/**
 * @generated DT_ID=none
 */
@Remote
public interface GrupoIndicadorBeanRemote
{

    /**
     * @generated DT_ID=none
     */
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    /**
     * @generated DT_ID=none
     */
    public GrupoIndicador persistGrupoIndicador(GrupoIndicador grupoIndicador);

    /**
     * @generated DT_ID=none
     */
    public GrupoIndicador mergeGrupoIndicador(GrupoIndicador grupoIndicador);

    /**
     * @generated DT_ID=none
     */
    public void removeGrupoIndicador(GrupoIndicador grupoIndicador);

    /**
     * @generated DT_ID=none
     */
    public List<GrupoIndicador> getGrupoIndicadorFindAll();

    public Indicador obtieneIndicadorPorIdYUsuario(long idIndicador, String idUsuario) throws Exception;

	public List<GrupoIndicador> obtieneIndicadorGruposPorIdYUsuario(long idIndicador,
			String idUsuario) throws Exception;

	public List<Indicador> obtieneIndicadoresPorIdUsuario(String idUsuario) throws Exception;

	public void persistGrupoIndicadores(List<GrupoIndicador> grupoIndicadores) throws Exception;

	public void removeGrupoIndicadores(List<GrupoIndicador> grupoIndicadores) throws Exception;

	public void mergeGrupoIndicadores(List<GrupoIndicador> grupoIndicadores)  throws Exception;

	

}

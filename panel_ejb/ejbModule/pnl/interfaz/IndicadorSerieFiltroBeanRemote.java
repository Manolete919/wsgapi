package pnl.interfaz;
import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import pnl.modelo.IndicadorSerieFiltro;
import pnl.modelo.IndicadorSerieFiltroPK;



/**
 * @generated DT_ID=none
 */
@Remote
public interface IndicadorSerieFiltroBeanRemote extends Serializable
{

    /**
     * @generated DT_ID=none
     */
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    /**
     * @generated DT_ID=none
     */
    public IndicadorSerieFiltro persistIndicadorSerieFiltro(IndicadorSerieFiltro indicadorSerieFiltro);

    /**
     * @generated DT_ID=none
     */
    public IndicadorSerieFiltro mergeIndicadorSerieFiltro(IndicadorSerieFiltro indicadorSerieFiltro);

    /**
     * @generated DT_ID=none
     */
    public void removeIndicadorSerieFiltro(IndicadorSerieFiltro indicadorSerieFiltro);

    /**
     * @generated DT_ID=none
     */
    public List<IndicadorSerieFiltro> getIndicadorSerieFiltroFindAll();

	public List<IndicadorSerieFiltro> obtenerSerieFiltrosPorIdIndicadorIdFiltro(long idIndicador,long idFiltro) throws Exception;

	

	public void persistIndicadorSerieFiltros(List<IndicadorSerieFiltro> indicadorSerieFiltros) throws Exception;

	public IndicadorSerieFiltro obtenerIndicadorSerieFiltroPorId(IndicadorSerieFiltroPK id) throws Exception;

}

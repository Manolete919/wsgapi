package pnl.interfaz;
import java.util.List;

import javax.ejb.Remote;

import pnl.modelo.IndicadorSerieFiltro;



/**
 * @generated DT_ID=none
 */
@Remote
public interface IndicadorSerieFiltroBeanRemote
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

	List<IndicadorSerieFiltro> obtenerSerieFiltrosPorIdIndicador(
			long idIndicador) throws Exception;

	void mergeIndicadorSerieFiltros(
			List<IndicadorSerieFiltro> indicadorSerieFiltros) throws Exception;

	void persistIndicadorSerieFiltros(
			List<IndicadorSerieFiltro> indicadorSerieFiltros) throws Exception;

}

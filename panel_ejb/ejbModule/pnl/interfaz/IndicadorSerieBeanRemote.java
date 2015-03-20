package pnl.interfaz;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import pnl.modelo.IndicadorSerie;



/**
 * @generated DT_ID=none
 */
@Remote
public interface IndicadorSerieBeanRemote extends Serializable
{

    /**
     * @generated DT_ID=none
     */
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    /**
     * @generated DT_ID=none
     */
    public IndicadorSerie persistIndicadorSerie(IndicadorSerie indicadorSerie);

    /**
     * @generated DT_ID=none
     */
    public IndicadorSerie mergeIndicadorSerie(IndicadorSerie indicadorSerie);

    /**
     * @generated DT_ID=none
     */
    public void removeIndicadorSerie(IndicadorSerie indicadorSerie);

    /**
     * @generated DT_ID=none
     */
    public List<IndicadorSerie> getIndicadorSerieFindAll();

	void persistIndicadorSeries(List<IndicadorSerie> indicadorSeries) throws Exception;

	public List<IndicadorSerie> obtenerIndicadorSeriePorIdIndicadorEstado(long idIndicador, String estado)  throws Exception;


	void removeIndicadorSeries(List<IndicadorSerie> indicadorSeries) throws Exception;
	
	public IndicadorSerie obtenerIndicadorSeriePorId(long idSerie) throws Exception;
	
	

}

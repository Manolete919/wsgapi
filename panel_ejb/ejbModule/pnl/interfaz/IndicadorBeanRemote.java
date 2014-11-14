package pnl.interfaz;

import java.util.List;

import javax.ejb.Remote;

import pnl.modelo.Indicador;


/**
 * @generated DT_ID=none
 */
@Remote
public interface IndicadorBeanRemote
{

    /**
     * @generated DT_ID=none
     */
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    /**
     * @generated DT_ID=none
     */
    public Indicador persistIndicador(Indicador indicador);

    /**
     * @generated DT_ID=none
     */
    public Indicador mergeIndicador(Indicador indicador);

    /**
     * @generated DT_ID=none
     */
    public void removeIndicador(Indicador indicador);

    /**
     * @generated DT_ID=none
     */
    public List<Indicador> getIndicadorFindAll();

	void removeIndicadores(List<Indicador> selectedIndicadores) throws Exception;

}

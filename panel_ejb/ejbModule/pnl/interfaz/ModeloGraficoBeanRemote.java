package pnl.interfaz;

import java.util.List;

import javax.ejb.Remote;

import pnl.modelo.ModeloGrafico;


/**
 * @generated DT_ID=none
 */
@Remote
public interface ModeloGraficoBeanRemote
{

    /**
     * @generated DT_ID=none
     */
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    /**
     * @generated DT_ID=none
     */
    public ModeloGrafico persistModeloGrafico(ModeloGrafico modeloGrafico);

    /**
     * @generated DT_ID=none
     */
    public ModeloGrafico mergeModeloGrafico(ModeloGrafico modeloGrafico);

    /**
     * @generated DT_ID=none
     */
    public void removeModeloGrafico(ModeloGrafico modeloGrafico);

    /**
     * @throws Exception 
     * @generated DT_ID=none
     */
    public List<ModeloGrafico> getModeloGraficoFindAll() throws Exception;

	

}

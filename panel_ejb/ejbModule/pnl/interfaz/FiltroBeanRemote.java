package pnl.interfaz;
import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import pnl.modelo.Filtro;



/**
 * @generated DT_ID=none
 */
@Remote
public interface FiltroBeanRemote extends Serializable
{

    /**
     * @generated DT_ID=none
     */
    Object queryByRange(String jpqlStmt, int firstResult, int maxResults);

    /**
     * @generated DT_ID=none
     */
    public Filtro persistFiltro(Filtro filtro);

    /**
     * @generated DT_ID=none
     */
    public Filtro mergeFiltro(Filtro filtro);

    /**
     * @generated DT_ID=none
     */
    public void removeFiltro(Filtro filtro);

    /**
     * @generated DT_ID=none
     */
    public List<Filtro> getFiltroFindAll();

	void persistFiltros(List<Filtro> filtros) throws Exception;

	List<Filtro> obtenerFiltrosDeIndicadorPorIndicadorNivel(long idIndicador, String aNivelIndicador) throws Exception;


	void removeFiltros(List<Filtro> filtros) throws Exception;
	
	public Filtro obtenerFiltroPorId(long idFiltro) throws Exception;

}

package pnl.filtro.dinamico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;
import pnl.interfaz.FiltroBeanRemote;
import pnl.interfaz.GrupoIndicadorBeanRemote;
import pnl.interfaz.IndicadorSerieBeanRemote;
import pnl.interfaz.IndicadorSerieFiltroBeanRemote;
import pnl.modelo.Indicador;
import pnl.modelo.Filtro;
import pnl.modelo.IndicadorSerie;
import pnl.modelo.IndicadorSerieFiltro;
import pnl.modelo.Usuario;
import pnl.servicio.UsuarioServicio;

@ManagedBean(name = "dinamico", eager = true)
@ViewScoped
public class Dinamico implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IndicadorSerieBeanRemote indicadorSerieBeanRemote;
	private IndicadorSerieFiltroBeanRemote indicadorSerieFiltroBeanRemote;
	private List<IndicadorSerieFiltro> indicadorSerieFiltros;
	private GrupoIndicadorBeanRemote grupoIndicadorBeanRemote;
	private Indicador indicador;
	private FiltroBeanRemote filtroBeanRemote;

	private List<Filtro> filtros;
	private Usuario usuario;
	long idIndicador = 0;
	
	@ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;

	@PostConstruct
	public void init() {

		try {

			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			indicadorSerieBeanRemote = (IndicadorSerieBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/IndicadorSerieBean");

			grupoIndicadorBeanRemote = (GrupoIndicadorBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/GrupoIndicadorBean");

			indicadorSerieFiltroBeanRemote = (IndicadorSerieFiltroBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/IndicadorSerieFiltroBean");

			filtroBeanRemote = (FiltroBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/FiltroBean");
			
			usuario = usuarioServicio.getUsuario();

		} catch (Exception e) {
			e.printStackTrace();
		}

		

	}

	public static ELContext getELContext() {
		return FacesContext.getCurrentInstance().getELContext();
	}

	public static ExpressionFactory getExpressionFactory() {
		return getApplication().getExpressionFactory();
	}

	public static Application getApplication() {
		return FacesContext.getCurrentInstance().getApplication();
	}

	// Recupera los parametros, si es que el usuario dio al boton consultar y se
	// los agrega a la serie
	public List<FiltrosIndicadorSeriesValor> obtieneParametrosDeSeriesMasGrafico() {

		List<IndicadorSerie> indicadorSeries = null;

		try {

			indicador = grupoIndicadorBeanRemote.obtieneIndicadorPorIdYUsuario(idIndicador,usuario.getIdUsuario());
		
			indicadorSerieFiltros = indicadorSerieFiltroBeanRemote.obtenerSerieFiltrosPorIdIndicadorIdFiltro(idIndicador,-1);

			indicadorSeries = indicadorSerieBeanRemote.obtenerIndicadorSeriePorIdIndicadorEstado(idIndicador,"A");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<FiltrosIndicadorSeriesValor> serieGraficoParametrosPropiedadValores = new ArrayList<FiltrosIndicadorSeriesValor>();



		for (IndicadorSerie indicadorSerie : indicadorSeries) {

			List<IndicadorSerieFiltro> pSerieBuscaParametros = devuelveFiltrosPorIdSerie(indicadorSerie
					.getIdSerie());

	
			List<FiltroValorDefault> listaDeFiltrosPorSerie1 = new ArrayList<FiltroValorDefault>();
			List<FiltroValorDefault> listaDeFiltrosPorSerie2 = new ArrayList<FiltroValorDefault>();

			// por cada serie, se deben leer todos sus parametros configurados
			for (IndicadorSerieFiltro indicadorSerieFiltro : pSerieBuscaParametros) {

				// cargar los valores inciales a nivel de serie
				FiltroValorDefault filtroValorDefault = new FiltroValorDefault(
						indicadorSerieFiltro.getFiltro(), 
						indicadorSerieFiltro.getValor());

				listaDeFiltrosPorSerie1.add(filtroValorDefault);

			}

			// leer parametros configurados por indicador, y agregarlos a la
			// serie
			
		

			for (Filtro filtro : filtros) {
				FiltroValorDefault filtroValorDefault = new FiltroValorDefault(
						filtro,
						filtro.getValorInicial());
				listaDeFiltrosPorSerie1.add(filtroValorDefault);
			}

			listaDeFiltrosPorSerie2.addAll(listaDeFiltrosPorSerie1);

			// se settea la serie, y tambien la lista de parametros por serie
			FiltrosIndicadorSeriesValor serieGraficoParametrosPropiedadValor = new FiltrosIndicadorSeriesValor(
					indicadorSerie, listaDeFiltrosPorSerie2);

			serieGraficoParametrosPropiedadValores.add(serieGraficoParametrosPropiedadValor);

		}

		return serieGraficoParametrosPropiedadValores;

	}

	// devuelve todos los parametros asociados a una serie, para la cual se
	// itera
	public List<IndicadorSerieFiltro> devuelveFiltrosPorIdSerie(long idSerie) {

		List<IndicadorSerieFiltro> pSerieParametrosEncontrado = new ArrayList<IndicadorSerieFiltro>();
		for (IndicadorSerieFiltro indicadorSerieFiltro : indicadorSerieFiltros) {

			if (indicadorSerieFiltro.getId().getIdSerie() == idSerie) {

				pSerieParametrosEncontrado.add(indicadorSerieFiltro);
			}

		}
		return pSerieParametrosEncontrado;
	}

	public Indicador getIndicador() {
		return indicador;
	}

	protected HttpSession getSession() {

		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}

	public void inicializaParametrosIndicadores(long indicadorId) {

		idIndicador = indicadorId;

		try {
			filtros = filtroBeanRemote.obtenerFiltrosDeIndicadorPorIndicadorNivel(idIndicador, "S");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}



	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}


	
	
	

}

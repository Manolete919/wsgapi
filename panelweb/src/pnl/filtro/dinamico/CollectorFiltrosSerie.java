package pnl.filtro.dinamico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import pnl.interfaz.FiltroBeanRemote;
import pnl.interfaz.GrupoIndicadorBeanRemote;
import pnl.interfaz.IndicadorSerieBeanRemote;
import pnl.interfaz.IndicadorSerieFiltroBeanRemote;
import pnl.modelo.Indicador;
import pnl.modelo.Filtro;
import pnl.modelo.IndicadorSerie;
import pnl.modelo.IndicadorSerieFiltro;
import pnl.modelo.IndicadorSerieFiltroPK;
import pnl.modelo.Usuario;
import pnl.servicio.RegistraLog;
import pnl.servicio.UsuarioServicio;
import pnl.webservice.integracion.ConsultaGenerico;
import pnl.webservice.integracion.Utileria;
import pnl.wsg.Servicio;

@ManagedBean
@ViewScoped
public class CollectorFiltrosSerie implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Filtro filtro;
	private List<Filtro> filtrosConfigurados;
	private List<FiltroValorDefault> filtroValores;
	private FiltroBeanRemote filtroBeanRemote;
	private IndicadorSerieBeanRemote indicadorSerieBeanRemote;
	private GrupoIndicadorBeanRemote grupoIndicadorBeanRemote;
	private Indicador indicador;
	private int indiceTipoDato;
	private int indiceTipoEntrada;
	private List<Filtro> filtros;
	private List<IndicadorSerieFiltro> indicadorSerieFiltros;
	private IndicadorSerieFiltroBeanRemote indicadorSerieFiltroBeanRemote;
	private List<IndicadorSerieFiltro> indicadorSerieFiltrosConfigurados;
	private Filtro selectedFiltro;
	private Usuario usuario;

	private String query;

	@ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;
	
	
	@ManagedProperty("#{registraLog}")
	private RegistraLog registraLog;

	@PostConstruct
	public void init() {

		indicador = new Indicador();
		usuario = usuarioServicio.getUsuario();
		filtro = new Filtro();
		filtro.setEstado("A");
		filtro.setAnivelIndicador("N");
		indicadorSerieFiltros = new ArrayList<IndicadorSerieFiltro>();

		try {

			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			filtroBeanRemote = (FiltroBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/FiltroBean");

			grupoIndicadorBeanRemote = (GrupoIndicadorBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/GrupoIndicadorBean");

			indicadorSerieFiltroBeanRemote = (IndicadorSerieFiltroBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/IndicadorSerieFiltroBean");

			indicadorSerieBeanRemote = (IndicadorSerieBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/IndicadorSerieBean");

		} catch (Exception e) {
			e.printStackTrace();
		}

		filtros = new ArrayList<Filtro>();

	}

	public void createNew() {

		if (filtros.contains(filtro)) {
			FacesMessage msg = new FacesMessage("Dublicado",
					"Este filtro ya ha sido agregado");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		} else {

			filtro.setEstado("A");
			filtro.setAnivelIndicador("N");
			filtros.add(filtro);
			filtro = new Filtro();
		}
	}

	public String reinit() {

		filtro = new Filtro();
		filtro.setEstado("A");
		filtro.setAnivelIndicador("N");

		return null;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public List<Filtro> getFiltros() {

		return filtros;
	}

	public void inicializar(long idIndicador) {

		try {

			indicador = grupoIndicadorBeanRemote.obtieneIndicadorPorIdYUsuario(
					idIndicador, usuario.getIdUsuario());
			filtroValores = new ArrayList<FiltroValorDefault>();
			filtroValores.add(new FiltroValorDefault(null, indicador
					.getIdServicio().toString()));
			filtrosConfigurados = filtroBeanRemote
					.obtenerFiltrosDeIndicadorPorIndicadorNivel(idIndicador,
							null);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void guardarFiltros() {

		try {

			// extraer la cantidad de series
			// consultar todas las series del indicador

			List<Filtro> filtros2 = new ArrayList<Filtro>();

			List<IndicadorSerie> indicadorSeries = indicadorSerieBeanRemote
					.obtenerIndicadorSeriePorIdIndicadorEstado(this
							.getIndicador().getIdIndicador(), null);

			for (Filtro filtro : filtros) {

				// inicializa el array de serie filt
				indicadorSerieFiltros = new ArrayList<IndicadorSerieFiltro>();
				// recorrer todas las series, con el mismo filtro
				for (IndicadorSerie indicadorSerie : indicadorSeries) {

					IndicadorSerieFiltro indicadorSerieFiltro = new IndicadorSerieFiltro();
					IndicadorSerieFiltroPK id = new IndicadorSerieFiltroPK();
					indicadorSerieFiltro.setId(id);
					indicadorSerieFiltro.setFiltro(filtro);
					indicadorSerieFiltro.setIndicadorSery(indicadorSerie);
					indicadorSerieFiltro.setIndicador(indicador);

					indicadorSerieFiltros.add(indicadorSerieFiltro);
				}

				filtro.setIndicadorSerieFiltros(indicadorSerieFiltros);
				filtro.setIndicador(indicador);
				filtros2.add(filtro);

			}

	

			// cada uno de los filtros deben agregar seccion e indicador
			filtroBeanRemote.persistFiltros(filtros2);
			registraLog.registrarLog(filtros2, RegistraLog.ACCION_CREAR, RegistraLog.RECURSO_FILTRO_SERIE);
			

			addMessage("Datos Guardados exitosamente");
			filtro = new Filtro();
			filtro.setEstado("A");
			filtro.setAnivelIndicador("N");
			filtros = new ArrayList<Filtro>();

		} catch (EJBException e) {
			Exception cause = e.getCausedByException();
			if (cause instanceof ConstraintViolationException) {
				ConstraintViolationException cve = (ConstraintViolationException) e
						.getCausedByException();
				for (Iterator<ConstraintViolation<?>> it = cve
						.getConstraintViolations().iterator(); it.hasNext();) {
					ConstraintViolation<? extends Object> v = it.next();
					System.err.println(v);
					System.err.println("==>>" + v.getMessage());
				}
				
				addMessage("Hubieron errores");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			addMessage("Hubieron errores");
			e.printStackTrace();
		}

	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public int getIndiceTipoDato() {

		return indiceTipoDato;
	}

	public int getIndiceTipoEntrada() {
		return indiceTipoEntrada;
	}

	public Indicador getIndicador() {
		return indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}

	public List<Filtro> getFiltrosConfigurados() {
		return filtrosConfigurados;
	}

	public String getQuery() {
		return query;
	}

	public void consultarSentencias() {
		ConsultaGenerico cg = new ConsultaGenerico();
		Utileria u = new Utileria();
		try {
			Servicio servicio = cg.consultarServicioWebGenerico(
					u.convertirFiltroValorEnDocument(filtroValores),
					new Long(3), usuario.getUsuariosWsg().getIdUsuario(),
					usuario.getUsuariosWsg().getClave());
			if (servicio != null) {
				if (servicio.get_any() != null) {
					query = cg.procesaDatosIdServicio(servicio.get_any());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Filtro getSelectedFiltro() {
		return selectedFiltro;
	}

	public void setSelectedFiltro(Filtro selectedFiltro) {

		try {
			indicadorSerieFiltrosConfigurados = indicadorSerieFiltroBeanRemote
					.obtenerSerieFiltrosPorIdIndicadorIdFiltro(selectedFiltro
							.getIndicador().getIdIndicador(), selectedFiltro
							.getIdFiltro());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.selectedFiltro = selectedFiltro;
	}

	public List<IndicadorSerieFiltro> getIndicadorSerieFiltrosConfigurados() {
		return indicadorSerieFiltrosConfigurados;
	}
	
	public void setRegistraLog(RegistraLog registraLog) {
		this.registraLog = registraLog;
	}

}

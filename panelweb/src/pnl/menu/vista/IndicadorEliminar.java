package pnl.menu.vista;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import pnl.filtro.dinamico.FiltroValorDefault;
import pnl.interfaz.FiltroBeanRemote;
import pnl.interfaz.GrupoIndicadorBeanRemote;
import pnl.interfaz.IndicadorBeanRemote;
import pnl.interfaz.IndicadorSerieBeanRemote;
import pnl.modelo.Filtro;
import pnl.modelo.GrupoIndicador;
import pnl.modelo.Indicador;
import pnl.modelo.IndicadorSerie;
import pnl.modelo.Usuario;
import pnl.servicio.UsuarioServicio;
import pnl.webservice.integracion.ConsultaGenerico;
import pnl.webservice.integracion.Utileria;
import pnl.wsg.Servicio;

@ManagedBean
@ViewScoped
public class IndicadorEliminar implements Serializable {
	
	//http://jsfcorner.blogspot.com/2012/11/disabled-selection-button-for.html

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Indicador> indicadores = new ArrayList<Indicador>();
	private List<Indicador> selectedIndicadores = new ArrayList<Indicador>();
	private GrupoIndicadorBeanRemote grupoIndicadorBeanRemote;
	private IndicadorBeanRemote indicadorBeanRemote;
	private Indicador selectedIndicador;
	private Usuario usuario;
	private FiltroBeanRemote filtroBeanRemote;
	private IndicadorSerieBeanRemote indicadorSerieBeanRemote;
	String query;


	@ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;

	@ManagedProperty("#{menuVista}")
	private MenuVista menuVista;

	@PostConstruct
	public void init() {

		try {

			usuario = usuarioServicio.getUsuario();

			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			grupoIndicadorBeanRemote = (GrupoIndicadorBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/GrupoIndicadorBean");
			
			indicadorBeanRemote = (IndicadorBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/IndicadorBean");

			indicadores = grupoIndicadorBeanRemote.obtieneIndicadoresPorIdUsuario(usuario.getIdUsuario());
			
			filtroBeanRemote = (FiltroBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/FiltroBean");
			
			indicadorSerieBeanRemote = (IndicadorSerieBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/IndicadorSerieBean");
			
			


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Indicador> getIndicadores() {
			
		return indicadores;
	}

	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

	public List<Indicador> getSelectedIndicadores() {
		return selectedIndicadores;
	}

	public void setSelectedIndicadores(List<Indicador> selectedIndicadores) {
		this.selectedIndicadores = selectedIndicadores;
	}

	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Indicador Seleccionado",
				Long.toString(((Indicador) event.getObject()).getIdIndicador()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowUnselect(UnselectEvent event) {
		FacesMessage msg = new FacesMessage("Indicador Deseleccionado",
				Long.toString(((Indicador) event.getObject()).getIdIndicador()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}

	public void setMenuVista(MenuVista menuVista) {
		this.menuVista = menuVista;
	}

	public void addMessage(String summary,Severity severity) {
		FacesMessage message = new FacesMessage(severity,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void eliminarIndicadoreSeleccionados() {

		if (this.hasRole("ROLE_ADMIN")) {

			try {
				

				indicadorBeanRemote.removeIndicadores(this.getSelectedIndicadores());

				addMessage("Se eliminaron exitosamente!!",FacesMessage.SEVERITY_INFO);

				menuVista.actualizarMenu();
				//recalcular
				indicadores = grupoIndicadorBeanRemote.obtieneIndicadoresPorIdUsuario(usuario.getIdUsuario());

			} catch (Exception e) {
				addMessage("Hubieron errores!",FacesMessage.SEVERITY_ERROR);
			}

		} else {
			addMessage("NO TIENE PERMISO PARA REALIZAR ESTA ACCION!!",FacesMessage.SEVERITY_INFO);
		}

	}

	protected boolean hasRole(String role) {
		// get security context from thread local
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return false;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return false;

		for (GrantedAuthority auth : authentication.getAuthorities()) {
			if (role.equals(auth.getAuthority()))
				return true;
		}

		return false;
	}

	public Indicador getSelectedIndicador() {
		return selectedIndicador;
	}

	public void setSelectedIndicador(Indicador selectedIndicador) {
		
		try {
			List<Filtro> filtros = filtroBeanRemote.obtenerFiltrosDeIndicadorPorIndicadorNivel(selectedIndicador.getIdIndicador(), null);
			selectedIndicador.setFiltros(filtros);
			List<IndicadorSerie> indicadorSeries = indicadorSerieBeanRemote.obtenerIndicadorSeriePorIdIndicadorEstado(selectedIndicador.getIdIndicador(), null);
			selectedIndicador.setIndicadorSeries(indicadorSeries);
			List<GrupoIndicador> grupoIndicadores = new ArrayList<GrupoIndicador>();
			grupoIndicadores = grupoIndicadorBeanRemote.obtieneIndicadorGruposPorIdIndicador(selectedIndicador.getIdIndicador());
			selectedIndicador.setGrupoIndicadores(grupoIndicadores);
			
			List<FiltroValorDefault> filtroValores = new ArrayList<FiltroValorDefault>();
			filtroValores.add(new FiltroValorDefault(null,selectedIndicador.getIdServicio().toString()));
			ConsultaGenerico cg = new ConsultaGenerico();
			Utileria u = new Utileria();		
			
		
			Servicio servicio = cg.consultarServicioWebGenerico(u.convertirFiltroValorEnDocument(filtroValores), new Long(3), usuario.getUsuariosWsg().getIdUsuario(), usuario.getUsuariosWsg().getClave());
			if(servicio != null ){
				if(servicio.get_any() != null ){
					query = cg.procesaDatosIdServicio(servicio.get_any());
				}
			}
			
			this.selectedIndicador = selectedIndicador;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public String getQuery() {
		return query;
	}


	
	
}

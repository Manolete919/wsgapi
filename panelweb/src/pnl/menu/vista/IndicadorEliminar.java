package pnl.menu.vista;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import pnl.interfaz.FiltroBeanRemote;
import pnl.interfaz.GrupoIndicadorBeanRemote;
import pnl.interfaz.IndicadorBeanRemote;
import pnl.interfaz.IndicadorSerieBeanRemote;
import pnl.modelo.Filtro;
import pnl.modelo.Indicador;
import pnl.modelo.IndicadorSerie;
import pnl.modelo.Usuario;
import pnl.servicio.UsuarioServicio;

@ManagedBean
public class IndicadorEliminar implements Serializable {

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
			
			indicadorBeanRemote = (IndicadorBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/IndicadorBean");

			indicadores = grupoIndicadorBeanRemote
					.obtieneIndicadoresPorIdUsuario(usuario.getIdUsuario());
			
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

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void eliminarIndicadoreSeleccionados() {

		if (this.hasRole("ROLE_ADMIN")) {

			try {
				// grupoIndicadorBeanRemote.persistUsuarioIndicador(usuarioIndicador);

				indicadorBeanRemote.removeIndicadores(this.getSelectedIndicadores());

				addMessage("Se eliminaron exitosamente!!");

				menuVista.actualizarMenu();
				//recalcular
				indicadores = grupoIndicadorBeanRemote.obtieneIndicadoresPorIdUsuario(usuario.getIdUsuario());

			} catch (Exception e) {

			}

		} else {
			addMessage("NO TIENE PERMISO PARA REALIZAR ESTA ACCION!!");
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
			this.selectedIndicador = selectedIndicador;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
	
}

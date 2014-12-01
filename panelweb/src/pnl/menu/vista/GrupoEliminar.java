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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import pnl.interfaz.GrupoBeanRemote;
import pnl.interfaz.UsuarioGrupoBeanRemote;
import pnl.modelo.Usuario;
import pnl.modelo.UsuarioGrupo;
import pnl.servicio.RegistraLog;
import pnl.servicio.UsuarioServicio;

@ManagedBean
@ViewScoped
public class GrupoEliminar implements Serializable {
	
	//http://jsfcorner.blogspot.com/2012/11/disabled-selection-button-for.html

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UsuarioGrupo> usuarioGrupos = new ArrayList<UsuarioGrupo>();
	private List<UsuarioGrupo> selectedGrupos = new ArrayList<UsuarioGrupo>();
	private UsuarioGrupoBeanRemote usuarioGrupoBeanRemote;
	private GrupoBeanRemote grupoBeanRemote;
	private Usuario usuario;
	
	


	@ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;
	
	@ManagedProperty("#{menuVista}")
	private MenuVista menuVista;
	
	@ManagedProperty("#{registraLog}")
	private RegistraLog registraLog;
	
	


	@PostConstruct
	public void init() {

		try {

			usuario = usuarioServicio.getUsuario();

			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);


			usuarioGrupoBeanRemote = (UsuarioGrupoBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/UsuarioGrupoBean");
			
			grupoBeanRemote = (GrupoBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/GrupoBean");
			
			usuarioGrupos = usuarioGrupoBeanRemote.obtenerGruposPorIdUSuarioNoOcupados(usuario.getIdUsuario());
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}





	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}




	
	public void addMessage(String summary,Severity severity) {
		FacesMessage message = new FacesMessage(severity,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void eliminarGruposSeleccionados() {

		if (this.hasRole("ROLE_ADMIN")) {

			try {
				
				
				
				usuarioGrupoBeanRemote.removeUsuarioGrupos(selectedGrupos);
				
				//eliminar todos los grupos
				grupoBeanRemote.removeGrupos(selectedGrupos);
				
				registraLog.registrarLog(selectedGrupos, RegistraLog.ACCION_BORRAR, RegistraLog.RECURSO_INDICADOR);
			
				addMessage("Se eliminaron exitosamente!!",FacesMessage.SEVERITY_INFO);
				usuarioGrupos = usuarioGrupoBeanRemote.obtenerGruposPorIdUSuarioNoOcupados(usuario.getIdUsuario());
				menuVista.actualizarMenu();
			} catch (Exception e) {
				addMessage("Ocurrio algun error!",FacesMessage.SEVERITY_ERROR);
			}

		} else {
			addMessage("NO TIENE PERMISO PARA REALIZAR ESTA ACCION!!",FacesMessage.SEVERITY_WARN);
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





	public List<UsuarioGrupo> getUsuarioGrupos() {
		return usuarioGrupos;
	}





	public void setUsuarioGrupos(List<UsuarioGrupo> usuarioGrupos) {
		this.usuarioGrupos = usuarioGrupos;
	}





	public List<UsuarioGrupo> getSelectedGrupos() {
		return selectedGrupos;
	}





	public void setSelectedGrupos(List<UsuarioGrupo> selectedGrupos) {
		this.selectedGrupos = selectedGrupos;
	}





	public void setMenuVista(MenuVista menuVista) {
		this.menuVista = menuVista;
	}

	public void setRegistraLog(RegistraLog registraLog) {
		this.registraLog = registraLog;
	}


}

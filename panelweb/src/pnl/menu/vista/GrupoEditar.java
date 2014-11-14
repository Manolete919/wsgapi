package pnl.menu.vista;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
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
import pnl.interfaz.UsuarioGrupoBeanRemote;
import pnl.modelo.Usuario;
import pnl.modelo.UsuarioGrupo;
import pnl.servicio.UsuarioServicio;


@ManagedBean
@ViewScoped
public class GrupoEditar implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<UsuarioGrupo> usuarioGrupos = new ArrayList<UsuarioGrupo>();
	private UsuarioGrupoBeanRemote usuarioGrupoBeanRemote;
	private Usuario usuario;

	@ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;



	@PostConstruct
	public void init() {

		try {

			usuario = usuarioServicio.getUsuario();

			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			usuarioGrupoBeanRemote = (UsuarioGrupoBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/UsuarioGrupoBean");
			
			

			usuarioGrupos = usuarioGrupoBeanRemote.obtenerGruposPorIdUSuarioEstado(usuario.getIdUsuario(),null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}



	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void actualizarUsuarioGrupos() {

		if (this.hasRole("ROLE_ADMIN")) {

			try {
				usuarioGrupoBeanRemote.mergeUsuarioGrupos(usuarioGrupos);
				addMessage("ACTUALIZADO CON EXITO!!");
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



	public List<UsuarioGrupo> getUsuarioGrupos() {
		return usuarioGrupos;
	}



	public void setUsuarioGrupos(List<UsuarioGrupo> usuarioGrupos) {
		this.usuarioGrupos = usuarioGrupos;
	}

	
	
}

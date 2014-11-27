package pnl.historial;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;

import pnl.interfaz.AccionUsuarioBeanRemote;
import pnl.interfaz.LogUsuarioBeanRemote;
import pnl.interfaz.RecursosAppBeanRemote;
import pnl.interfaz.UsuarioBeanRemote;
import pnl.modelo.AccionUsuario;
import pnl.modelo.LogUsuario;
import pnl.modelo.RecursosApp;
import pnl.modelo.Usuario;


@ViewScoped
@ManagedBean
public class Historial implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LogUsuarioBeanRemote logUsuarioBeanRemote;
	private List<LogUsuario> logUsuarios;
	
	private UsuarioBeanRemote usuarioBeanRemote;
	private List<Usuario> usuarios;
	private Map<String, String> usuariosSelect;
	private String idUsuario;
	
	private RecursosAppBeanRemote recursosAppBeanRemote;
	private List<RecursosApp>  recursos;
	private Map<String,Long> recursoAppSelect;
	private long idRecursosApp;
	
	
	private AccionUsuarioBeanRemote accionUsuarioBeanRemote;
	private List<AccionUsuario> acciones;
	private Map<String,Long> accionUsuarioSelect;
	private long idAccionUsuario;
	
	
	private String palabraClave;
	
	private Date fechaInicial;
	private Date fechaFinal;
	

	@PostConstruct
	public void init() {

		try {
			
			palabraClave = "";
			
			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			logUsuarioBeanRemote = (LogUsuarioBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/LogUsuarioBean");
			
			usuarioBeanRemote = (UsuarioBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/UsuarioBean");
		
			recursosAppBeanRemote = (RecursosAppBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/RecursosAppBean");
			
			accionUsuarioBeanRemote = (AccionUsuarioBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/AccionUsuarioBean");
			
			
			usuarios = usuarioBeanRemote.getUsuarioFindAll();
			
			recursos = recursosAppBeanRemote.getRecursosAppFindAll();
			
			acciones = accionUsuarioBeanRemote.getAccionUsuarioFindAll();
			
			
			usuariosSelect = new HashMap<String, String>();
			for (Usuario usuario : usuarios) {
				usuariosSelect.put(usuario.getNombre(),usuario.getIdUsuario());				
			}
			
			
			recursoAppSelect = new HashMap<String, Long>();
			for (RecursosApp recurso : recursos) {
				recursoAppSelect.put(recurso.getDescripcion(),recurso.getIdRecursosApp());				
			}
			
			accionUsuarioSelect = new HashMap<String, Long>();	
			

			for (AccionUsuario accion : acciones) {
				accionUsuarioSelect.put(accion.getDescripcion(),accion.getIdAccionUsuario());				
			}
			
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	public void consultar(){
		
		try {
			logUsuarios = logUsuarioBeanRemote.obtenerHistorial(idUsuario,
					idRecursosApp,
					idAccionUsuario,
					palabraClave,
					fechaInicial,
					fechaFinal
					);
			addMessage("Consulta exitos",FacesMessage.SEVERITY_INFO);
		} catch (Exception e) {
			addMessage("Hubieron errores en la consulta!",FacesMessage.SEVERITY_ERROR);
			e.printStackTrace();
		}
		
		
	}

	public List<LogUsuario> getLogUsuarios() {
		return logUsuarios;
	}


	public String getIdUsuario() {
		return idUsuario;
	}


	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}


	public long getIdRecursosApp() {
		return idRecursosApp;
	}


	public void setIdRecursosApp(long idRecursosApp) {
		this.idRecursosApp = idRecursosApp;
	}


	public long getIdAccionUsuario() {
		return idAccionUsuario;
	}


	public void setIdAccionUsuario(long idAccionUsuario) {
		this.idAccionUsuario = idAccionUsuario;
	}


	public String getPalabraClave() {
		return palabraClave;
	}


	public void setPalabraClave(String palabraClave) {
		this.palabraClave = palabraClave;
	}


	public Date getFechaInicial() {
		return fechaInicial;
	}


	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}


	public Date getFechaFinal() {
		return fechaFinal;
	}


	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}


	public Map<String, String> getUsuariosSelect() {
		return usuariosSelect;
	}


	public void setUsuariosSelect(Map<String, String> usuariosSelect) {
		this.usuariosSelect = usuariosSelect;
	}


	public Map<String, Long> getRecursoAppSelect() {
		return recursoAppSelect;
	}


	public void setRecursoAppSelect(Map<String, Long> recursoAppSelect) {
		this.recursoAppSelect = recursoAppSelect;
	}


	public Map<String, Long> getAccionUsuarioSelect() {
		return accionUsuarioSelect;
	}


	public void setAccionUsuarioSelect(Map<String, Long> accionUsuarioSelect) {
		this.accionUsuarioSelect = accionUsuarioSelect;
	}
	
	
	public void addMessage(String summary,Severity severity) {
		FacesMessage message = new FacesMessage(severity,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	

}

package pnl.menu.vista;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import pnl.interfaz.AccionUsuarioBeanRemote;
import pnl.interfaz.LogUsuarioBeanRemote;
import pnl.interfaz.RecursosAppBeanRemote;
import pnl.interfaz.UsuarioGrupoBeanRemote;
import pnl.modelo.AccionUsuario;
import pnl.modelo.Grupo;
import pnl.modelo.LogUsuario;
import pnl.modelo.RecursosApp;
import pnl.modelo.Usuario;
import pnl.modelo.UsuarioGrupo;
import pnl.modelo.UsuarioGrupoPK;
import pnl.servicio.UsuarioServicio;


@ManagedBean
@ViewScoped
public class GrupoNuevo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	final static Logger logger = Logger.getLogger(GrupoNuevo.class);
	
	
	private UsuarioGrupoBeanRemote usuarioGrupoBeanRemote;
	private List<UsuarioGrupo> usuarioGrupos = new ArrayList<UsuarioGrupo>();
	private LogUsuarioBeanRemote logUsuarioBeanRemote;
	private RecursosAppBeanRemote recursosAppBeanRemote;
	private RecursosApp recursosApp;
	private AccionUsuarioBeanRemote accionUsuarioBeanRemote;
	private AccionUsuario accionUsuario;
	private LogUsuario logUsuario;
    private Usuario usuario;
    private UsuarioGrupo usuarioGrupo;
    private Grupo grupo;

    public GrupoNuevo(){
    }
    
    
	@ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;
	

	
	
	
	@PostConstruct
	public void init(){
		
		
		usuarioGrupo = new UsuarioGrupo();
		grupo = new Grupo();
		grupo.setEstado("A");
		usuarioGrupo.setGrupo(grupo);
		
		usuario = usuarioServicio.getUsuario();
		
    	try {
			
			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			usuarioGrupoBeanRemote = (UsuarioGrupoBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/UsuarioGrupoBean");
			
			usuarioGrupos = usuarioGrupoBeanRemote.obtenerGruposPorIdUSuarioEstado(usuario.getIdUsuario(),null);
			
			logUsuarioBeanRemote = (LogUsuarioBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/LogUsuarioBean");
			
			recursosAppBeanRemote  = (RecursosAppBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/RecursosAppBean");

			recursosApp = recursosAppBeanRemote.obtenerRecursosAppPorId(1l);
			
			accionUsuarioBeanRemote = (AccionUsuarioBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/AccionUsuarioBean");
		
			accionUsuario = accionUsuarioBeanRemote.obtenerAccionUsuario(1l);
    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
		
	}
	
	
    public void guardarUsuarioGrupo(ActionEvent actionEvent) {
    	
    	

        	UsuarioGrupoPK id = new UsuarioGrupoPK();
        	
        	if(this.hasRole("ROLE_ADMIN")){
        		
        		usuarioGrupo.setId(id);
        		usuarioGrupo.setUsuario(usuario);
        		usuarioGrupo.setEstado("A");
        		usuarioGrupoBeanRemote.persistUsuarioGrupo(usuarioGrupo);
        		
        		
        		
        		Date d = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(d);
            	
        		
        		logUsuario = new LogUsuario();
        		logUsuario.setUsuario(usuarioServicio.getUsuario());
        		logUsuario.setFecha(c.getTime());
        		logUsuario.setAccionUsuario(accionUsuario);
        		logUsuario.setRecursosApp(recursosApp);
        		logUsuario.setIdSesion(usuarioServicio.getSession().getId());
        		logUsuario.setDetalle(usuarioGrupo.getGrupo().getDescripcion());
        		logUsuarioBeanRemote.persistLogUsuario(logUsuario);
        		
        		grupo = new Grupo();
        		grupo.setEstado("A");
        		usuarioGrupo.setGrupo(grupo);
        		
        		
        		logger.info("GRABO EXITOSAMENTE");
            	
                addMessage("Se guardo exitosamente!!",FacesMessage.SEVERITY_INFO);
                
               
              
        	
        	}else{
        		addMessage("NO TIENE PERMISO DE ADMINISTRADOR PARA REALIZAR ESTA ACCION!!",FacesMessage.SEVERITY_WARN);
        	}
    	
  	

	
    }
     

    
	public void addMessage(String summary,Severity severity) {
		FacesMessage message = new FacesMessage(severity,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
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


	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}





	public Usuario getUsuario() {
		return usuario;
	}


	public UsuarioServicio getUsuarioServicio() {
		return usuarioServicio;
	}


	public UsuarioGrupo getUsuarioGrupo() {
		return usuarioGrupo;
	}


	public void setUsuarioGrupo(UsuarioGrupo usuarioGrupo) {
		this.usuarioGrupo = usuarioGrupo;
	}


	public List<UsuarioGrupo> getUsuarioGrupos() {
		return usuarioGrupos;
	}

	

    
}

package pnl.filtro.dinamico;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pnl.interfaz.FiltroBeanRemote;
import pnl.modelo.Filtro;
import pnl.servicio.RegistraLog;
import pnl.servicio.UsuarioServicio;


@ManagedBean
@ViewScoped
public class FiltrosIndicadorConfiguracion {
	
	private List<Filtro> filtros;
	private FiltroBeanRemote filtroBeanRemote;

	@ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;
	
	@ManagedProperty("#{registraLog}")
	private RegistraLog registraLog;
	

	
	@PostConstruct
	public void init(){
		
		
    	try {
			
			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			filtroBeanRemote = (FiltroBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/FiltroBean");
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
    	
				
		
		
	}
	
	//metodo llamado por el PRERENDERVIEW
	public void inicializar(long idIndicador){
		
		
		try {
			filtros = filtroBeanRemote.obtenerFiltrosDeIndicadorPorIndicadorNivel(idIndicador,"S");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

	public List<Filtro> getFiltros() {
		return filtros;
	}

	public void setFiltros(
			List<Filtro> filtros) {
		this.filtros = filtros;
	}


	
	public void irAIndicador(){
		
		try {
			
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();         
	     	String idIndicador = (String) params.get("idIndicador"); 
	     	String idModelo = (String) params.get("idModelo"); 
	     	
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			
			String parametros = "?ind="+idIndicador;
			
			if (idModelo.equals("1")) {
				context.redirect(context.getRequestContextPath()+"/paginas/graficos/barra.xhtml"+parametros);
			} else if (idModelo.equals("2")) {
				context.redirect(context.getRequestContextPath()+"/paginas/graficos/area.xhtml"+parametros);
			} else if (idModelo.equals("3")) {
				context.redirect(context.getRequestContextPath()+"/paginas/graficos/linea.xhtml"+parametros);
			}
		
		
		
		
		} catch (Exception e) {
			addMessage("Hubo algun error",FacesMessage.SEVERITY_ERROR);
			e.printStackTrace();
		}

	
		
		
	}
	

    public void onRowEdit(RowEditEvent event) {
    	
		if (this.hasRole("ROLE_ADMIN")) {

			try {
				
				
				
	       		//registro de log de actividades de usuario
        		List<Filtro> detalles = new ArrayList<Filtro>();
         		detalles.add(((Filtro) event.getObject()));
         		
         		registraLog.registrarLog(detalles, RegistraLog.ACCION_EDITAR, RegistraLog.RECURSO_FILTRO_INDICADOR);

         		filtroBeanRemote.mergeFiltro(((Filtro) event.getObject()));
				
				addMessage("ACTUALIZADO CON EXITO!!",FacesMessage.SEVERITY_INFO);
				
			} catch (Exception e) {
				addMessage("Hubieron errores",FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}

		} else {
			addMessage("NO TIENE PERMISO PARA REALIZAR ESTA ACCION!!",FacesMessage.SEVERITY_WARN);
		}
    	
        FacesMessage msg = new FacesMessage("Se editó el registro con Id #", ""+((Filtro) event.getObject()).getIdFiltro());
        FacesContext.getCurrentInstance().addMessage(null, msg);
   
    }
    

     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Se canceló la edición del registro con Id #", ""+((Filtro) event.getObject()).getIdFiltro());
        FacesContext.getCurrentInstance().addMessage(null, msg);
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
    

     

	
	public void addMessage(String summary,Severity severity) {
		FacesMessage message = new FacesMessage(severity,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	
	


	public void setRegistraLog(RegistraLog registraLog) {
		this.registraLog = registraLog;
	}

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}


	
	
	
	
}

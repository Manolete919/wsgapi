package pnl.filtro.dinamico;

import java.io.Serializable;
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
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pnl.interfaz.FiltroBeanRemote;
import pnl.interfaz.IndicadorSerieFiltroBeanRemote;
import pnl.modelo.Filtro;
import pnl.modelo.IndicadorSerieFiltro;
import pnl.servicio.RegistraLog;
import pnl.servicio.UsuarioServicio;

@ManagedBean
@ViewScoped
public class FiltrosEditar implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Filtro> filtros;
	private List<Filtro> filtrosConfigurados;
	private FiltroBeanRemote filtroBeanRemote;
	private IndicadorSerieFiltroBeanRemote indicadorSerieFiltroBeanRemote;
	private Filtro selectedFiltro;
	private List<IndicadorSerieFiltro> indicadorSerieFiltros;

	@ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;
	
	
	@ManagedProperty("#{registraLog}")
	private RegistraLog registraLog;
	
	
	
	@PostConstruct
	public void init(){
		
	
		filtros = new ArrayList<Filtro>();
		
    	try {
			
			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			filtroBeanRemote = (FiltroBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/FiltroBean");
			
			indicadorSerieFiltroBeanRemote = (IndicadorSerieFiltroBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/IndicadorSerieFiltroBean");
			

		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	
	
	
				
			

		
	}
	
	//metodo llamado por el PRERENDERVIEW
	public void inicializar(long idIndicador){
		
		
		try {
			filtros = filtroBeanRemote.obtenerFiltrosDeIndicadorPorIndicadorNivel(idIndicador,null);
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



    
	public void addMessage(String summary,Severity severity) {
		FacesMessage message = new FacesMessage(severity,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}
	
	public void obtenerConfigurados(){
		
       	Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();       
     	String idIndicador = (String) params.get("idIndicador"); 
     	
     	
		try {
			filtrosConfigurados = filtroBeanRemote.obtenerFiltrosDeIndicadorPorIndicadorNivel(new Long(idIndicador),null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	


	public List<Filtro> getFiltrosConfigurados() {
		return filtrosConfigurados;
	}

	public Filtro getSelectedFiltro() {
		return selectedFiltro;
	}

	public void setSelectedFiltro(Filtro selectedFiltro) {
		
		try {
			indicadorSerieFiltros = indicadorSerieFiltroBeanRemote.obtenerSerieFiltrosPorIdIndicadorIdFiltro(selectedFiltro.getIndicador().getIdIndicador(), selectedFiltro.getIdFiltro());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		this.selectedFiltro = selectedFiltro;
	}

	public List<IndicadorSerieFiltro> getIndicadorSerieFiltros() {
		return indicadorSerieFiltros;
	}


	
    public void onRowEdit(RowEditEvent event) {
    	
		if (this.hasRole("ROLE_ADMIN")) {

			try {
				
			
	       		//registro de log de actividades de usuario
        		List<Filtro> detalles = new ArrayList<Filtro>();
         		detalles.add(((Filtro) event.getObject()));
         		registraLog.registrarLog(detalles, RegistraLog.ACCION_EDITAR, RegistraLog.RECURSO_FILTROS);

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

	public void setRegistraLog(RegistraLog registraLog) {
		this.registraLog = registraLog;
	}

	

}

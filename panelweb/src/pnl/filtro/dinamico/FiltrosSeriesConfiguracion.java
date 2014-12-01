package pnl.filtro.dinamico;

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

import org.primefaces.event.RowEditEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import pnl.interfaz.IndicadorSerieFiltroBeanRemote;
import pnl.modelo.Indicador;
import pnl.modelo.IndicadorSerieFiltro;
import pnl.servicio.RegistraLog;
import pnl.servicio.UsuarioServicio;



@ManagedBean
@ViewScoped
public class FiltrosSeriesConfiguracion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<IndicadorSerieFiltro> indicadorSerieFiltros;
    private Indicador indicador;
	private IndicadorSerieFiltroBeanRemote indicadorSerieFiltroBeanRemote;
	private List<IndicadorSerieFiltro> indicadorSerieFiltrosExistentes;
	
    @ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;
    
	@ManagedProperty("#{registraLog}")
	private RegistraLog registraLog;
	
	
	@PostConstruct
	public void init(){
    	try {
    		  		
    		
    		
    		
    		indicadorSerieFiltros = new ArrayList<IndicadorSerieFiltro>();
    		
			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);
			
			indicadorSerieFiltroBeanRemote = (IndicadorSerieFiltroBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/IndicadorSerieFiltroBean");
			
		
    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
  				
	}
	
	//es inicializado por el PRERENDERVIEW
	public void inicializar(long idIndicador){
		
		
		
		try {
			
			indicadorSerieFiltros = indicadorSerieFiltroBeanRemote.obtenerSerieFiltrosPorIdIndicadorIdFiltro(idIndicador,-1);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   				
		

	}








	public List<IndicadorSerieFiltro> getIndicadorSerieFiltros() {
		return indicadorSerieFiltros;
	}


	public void setIndicadorSerieFiltros(List<IndicadorSerieFiltro> indicadorSerieFiltros) {
		this.indicadorSerieFiltros = indicadorSerieFiltros;
	}


	public Indicador getIndicador() {
		return indicador;
	}


	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}



	public List<IndicadorSerieFiltro> getIndicadorSerieFiltrosExistentes() {
		return indicadorSerieFiltrosExistentes;
	}
	
    public void onRowEdit(RowEditEvent event) {
    	
		if (this.hasRole("ROLE_ADMIN")) {

			try {
				
				
				
	       		//registro de log de actividades de usuario
        		List<IndicadorSerieFiltro> detalles = new ArrayList<IndicadorSerieFiltro>();
         		detalles.add(((IndicadorSerieFiltro) event.getObject()));
         		registraLog.registrarLog(detalles, RegistraLog.ACCION_EDITAR, RegistraLog.RECURSO_FILTRO_SERIE);

         		indicadorSerieFiltroBeanRemote.mergeIndicadorSerieFiltro(((IndicadorSerieFiltro) event.getObject()));
				
				addMessage("ACTUALIZADO CON EXITO!!",FacesMessage.SEVERITY_INFO);
				
			} catch (Exception e) {
				addMessage("Hubieron errores",FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}

		} else {
			addMessage("NO TIENE PERMISO PARA REALIZAR ESTA ACCION!!",FacesMessage.SEVERITY_WARN);
		}
    	
        FacesMessage msg = new FacesMessage("Se editó el registro con Id #", ""+((IndicadorSerieFiltro) event.getObject()).getFiltro().getIdFiltro());
        FacesContext.getCurrentInstance().addMessage(null, msg);
   
    }
    

     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Se canceló la edición del registro con Id #", ""+((IndicadorSerieFiltro) event.getObject()).getFiltro().getIdFiltro());
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


	public void addMessage(String summary,Severity severity) {
		FacesMessage message = new FacesMessage(severity,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}


    

}

package pnl.filtro.dinamico;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;

import org.primefaces.event.RowEditEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import pnl.interfaz.IndicadorSerieBeanRemote;
import pnl.modelo.Filtro;
import pnl.modelo.IndicadorSerie;
import pnl.servicio.RegistraLog;
import pnl.servicio.UsuarioServicio;


@ManagedBean
@ViewScoped
public class IndicadorSerieEditar {
	
	private IndicadorSerieBeanRemote indicadorSerieBeanRemote;
	private long idIndicador = 0l;

	private List<IndicadorSerie> indicadorSeries;
	
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

			indicadorSerieBeanRemote = (IndicadorSerieBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/IndicadorSerieBean");
			
		

			

		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	
		
	}
	
	public void inicializar(long indicadorId){
		
		
		
		idIndicador = indicadorId;
		
		indicadorSeries = new ArrayList<IndicadorSerie>();
		
		try {	
			
			indicadorSeries = indicadorSerieBeanRemote.obtenerIndicadorSeriePorIdIndicadorEstado(idIndicador,null);
			
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}



	
    public void onRowEdit(RowEditEvent event) {
    	
		if (this.hasRole("ROLE_ADMIN")) {

			try {
				
				
	       		//registro de log de actividades de usuario
        		List<IndicadorSerie> detalles = new ArrayList<IndicadorSerie>();
         		detalles.add(((IndicadorSerie) event.getObject()));
         		registraLog.registrarLog(detalles, RegistraLog.ACCION_EDITAR, RegistraLog.RECURSO_SERIE);

				indicadorSerieBeanRemote.mergeIndicadorSerie(((IndicadorSerie) event.getObject()));

				
				addMessage("ACTUALIZADO CON EXITO!!",FacesMessage.SEVERITY_INFO);
				
			} catch (Exception e) {
				addMessage("Hubieron errores",FacesMessage.SEVERITY_ERROR);
				e.printStackTrace();
			}

		} else {
			addMessage("NO TIENE PERMISO PARA REALIZAR ESTA ACCION!!",FacesMessage.SEVERITY_WARN);
		}
    	
        FacesMessage msg = new FacesMessage("Se editó el registro con Id #", ""+((IndicadorSerie) event.getObject()).getIdSerie());
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
    

     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Se canceló la edición del registro con Id #", ""+((Filtro) event.getObject()).getIdFiltro());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void addMessage(String summary,Severity severity) {
		FacesMessage message = new FacesMessage(severity,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}






	public List<IndicadorSerie> getIndicadorSeries() {
		return indicadorSeries;
	}

	public void setIndicadorSeries(List<IndicadorSerie> indicadorSeries) {
		this.indicadorSeries = indicadorSeries;
	}

	protected HttpSession getSession() {

		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}
	
	
	protected void refreshPage() {
		FacesContext fc = FacesContext.getCurrentInstance();
		String refreshpage = fc.getViewRoot().getViewId();
		ViewHandler ViewH =fc.getApplication().getViewHandler();
		UIViewRoot UIV = ViewH.createView(fc,refreshpage);
		UIV.setViewId(refreshpage);
		fc.setViewRoot(UIV);
	}

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}

	public void setRegistraLog(RegistraLog registraLog) {
		this.registraLog = registraLog;
	}
	
	
	

}

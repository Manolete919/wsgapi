package pnl.filtro.dinamico;

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

import pnl.interfaz.IndicadorSerieBeanRemote;
import pnl.modelo.IndicadorSerie;
import pnl.servicio.RegistraLog;
import pnl.servicio.UsuarioServicio;


@ManagedBean
@ViewScoped
public class IndicadorSerieEliminar {
	
	private IndicadorSerieBeanRemote indicadorSerieBeanRemote;
	private long idIndicador = 0l;

	private List<IndicadorSerie> indicadorSeries;
	private List<IndicadorSerie> selectedIndicadorSeries;
	
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

			indicadorSerieBeanRemote = (IndicadorSerieBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/IndicadorSerieBean");
			
		

			

		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	
		
	}
	
	public void inicializar(long indicadorId){
		
		
		
		idIndicador = indicadorId;
		
	
		
		try {	
			
			indicadorSeries = indicadorSerieBeanRemote.obtenerIndicadorSeriePorIdIndicadorEstado(idIndicador,null);
			
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}


	
	public void eliminar(){
		
		
		try {
			
			indicadorSerieBeanRemote.removeIndicadorSeries(this.getSelectedIndicadorSeries());
			registraLog.registrarLog(this.getSelectedIndicadorSeries(), RegistraLog.ACCION_BORRAR, RegistraLog.RECURSO_SERIE);
			addMessage("Los datos fueron eliminados",FacesMessage.SEVERITY_INFO);
			
		} catch (Exception e) {
			addMessage("Hubo algun error",FacesMessage.SEVERITY_ERROR);
			e.printStackTrace();
		}
				
		
		
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

	public List<IndicadorSerie> getSelectedIndicadorSeries() {
		return selectedIndicadorSeries;
	}

	public void setSelectedIndicadorSeries(
			List<IndicadorSerie> selectedIndicadorSeries) {
		this.selectedIndicadorSeries = selectedIndicadorSeries;
	}
	
	
	public void setRegistraLog(RegistraLog registraLog) {
		this.registraLog = registraLog;
	}

}

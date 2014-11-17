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

import pnl.interfaz.FiltroBeanRemote;
import pnl.modelo.Filtro;
import pnl.servicio.UsuarioServicio;

@ManagedBean
@ViewScoped
public class FiltrosConfiguracion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Filtro> filtros;
	private List<Filtro> filtrosConfigurados;
	private FiltroBeanRemote filtroBeanRemote;

	@ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;
	
	
	
	@PostConstruct
	public void init(){
		
	
		filtros = new ArrayList<Filtro>();
		
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


	
	public void actualizarFiltros(){
		
		try {
			
			filtroBeanRemote.mergeFiltros(filtros);
			
			addMessage("Los datos fueron actualizados",FacesMessage.SEVERITY_INFO);
					
		
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

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}
	
	public void obtenerConfigurados(){
       	Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();       
     	String idIndicador = (String) params.get("idIndicador"); 
     	
     	System.out.println("INDICADOR --> " + idIndicador);
     	
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


	
	
	

}

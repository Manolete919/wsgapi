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
import pnl.interfaz.IndicadorSerieFiltroBeanRemote;
import pnl.modelo.Indicador;
import pnl.modelo.IndicadorSerieFiltro;
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






	
	
	public void guardarFiltrosSeries(){
				
		try{
			indicadorSerieFiltroBeanRemote.mergeIndicadorSerieFiltros(this.getIndicadorSerieFiltros());
			addMessage("Se grabo exitosamente",FacesMessage.SEVERITY_INFO);
		}catch (Exception e){
			addMessage("Hubo algun error",FacesMessage.SEVERITY_ERROR);
			e.printStackTrace();
		}
		
		
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
	

	public void addMessage(String summary,Severity severity) {
		FacesMessage message = new FacesMessage(severity,
				summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}


    

}

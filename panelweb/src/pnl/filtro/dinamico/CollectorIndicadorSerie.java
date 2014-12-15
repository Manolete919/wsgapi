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
import pnl.interfaz.FiltroBeanRemote;
import pnl.interfaz.GrupoIndicadorBeanRemote;
import pnl.interfaz.IndicadorSerieBeanRemote;
import pnl.modelo.Filtro;
import pnl.modelo.Indicador;
import pnl.modelo.IndicadorSerieFiltro;
import pnl.modelo.IndicadorSerieFiltroPK;
import pnl.modelo.Usuario;
import pnl.modelo.IndicadorSerie;
import pnl.servicio.UsuarioServicio;


@ManagedBean
@ViewScoped
public class CollectorIndicadorSerie implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private IndicadorSerie indicadorSerie;    
    private List<IndicadorSerie> indicadorSeries;
    private Indicador indicador;
    private Indicador selectedIndicador;
	private Usuario usuario;
	private GrupoIndicadorBeanRemote grupoIndicadorBeanRemote;
	private IndicadorSerieBeanRemote indicadorSerieBeanRemote;
	private FiltroBeanRemote filtroBeanRemote;
	  
    
    @ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;


     
    @PostConstruct
    public void init() {
   	
    	indicador = new Indicador();
    	indicadorSerie = new IndicadorSerie();
    	indicadorSerie.setEstado("A");
    	indicadorSeries = new ArrayList<IndicadorSerie>();
    	usuario = usuarioServicio.getUsuario();
     	
    	try {
    		
			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			grupoIndicadorBeanRemote = (GrupoIndicadorBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/GrupoIndicadorBean");

			
			indicadorSerieBeanRemote  = (IndicadorSerieBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/IndicadorSerieBean");
			
			filtroBeanRemote = (FiltroBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/FiltroBean");
			

		
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	
    	
         
    }
    
    
    public void createNew() {
        if(indicadorSeries.contains(indicadorSerie)) {
            FacesMessage msg = new FacesMessage("Dublicado", "Esta serie ya ha sido agregada");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else {
        	indicadorSeries.add(indicadorSerie);
        	indicadorSerie = new IndicadorSerie();
        }
    }
     
    public String reinit() {
    	indicadorSerie = new IndicadorSerie();
    	indicadorSerie.setEstado("A");
         
        return null;
    }



	public Indicador getIndicador() {
		return indicador;
	}


	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}


	public void inicializar(long idIndicador){
  		try {
			
			indicador = grupoIndicadorBeanRemote.obtieneIndicadorPorIdYUsuario(idIndicador,usuario.getIdUsuario());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
 
	public void guardarIndicadorSeries(){
		
  		try {
  			
  			List<IndicadorSerieFiltro> indicadorSerieFiltros = new ArrayList<IndicadorSerieFiltro>(); 
    		
  			List<IndicadorSerie> indicadorSeries2 =  new ArrayList<IndicadorSerie>();
    		
    		List<Filtro> filtros = filtroBeanRemote.obtenerFiltrosDeIndicadorPorIndicadorNivel(this.getIndicador().getIdIndicador(), "N");
    			
    			
    			//Series a crearse, deben registrar los filtros que ya existen	
	  			for(IndicadorSerie indicadorSerie : this.getIndicadorSeries()){        				       				
    				
	  				indicadorSerie.setIndicador(indicador);
	  				
	  				
	  				
    				for(Filtro filtro :filtros){
    					
    					filtro.setIndicador(indicador);
    					IndicadorSerieFiltro  indicadorSerieFiltro = new IndicadorSerieFiltro();
    					IndicadorSerieFiltroPK id = new IndicadorSerieFiltroPK();
    					indicadorSerieFiltro.setId(id);  					
    					indicadorSerieFiltro.setFiltro(filtro);
    					indicadorSerieFiltro.setIndicadorSery(indicadorSerie);
    					indicadorSerieFiltro.setIndicador(indicador);
    								
    					indicadorSerieFiltros.add(indicadorSerieFiltro);
    				}  	
    				
    				
    				indicadorSerie.setIndicadorSerieFiltros(indicadorSerieFiltros);
    				indicadorSeries2.add(indicadorSerie);
    				   				
    			}
	  			
	  			
    			
	  		
	  			indicadorSerieBeanRemote.persistIndicadorSeries(indicadorSeries2);
	  			
    			
    			
    			
    			indicadorSeries = new ArrayList<IndicadorSerie>();

    			
    			indicador = new Indicador();

    			addMessage("Datos Guardados exitosamente",FacesMessage.SEVERITY_INFO);
 			
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			addMessage("Hubieron errores",FacesMessage.SEVERITY_ERROR);
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


	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}


	public IndicadorSerie getIndicadorSerie() {
		return indicadorSerie;
	}


	public void setIndicadorSerie(IndicadorSerie indicadorSerie) {
		this.indicadorSerie = indicadorSerie;
	}


	public void setSelectedIndicador(Indicador selectedIndicador) {
		
		List<IndicadorSerie> indicadorSeries;
		try {
			indicadorSeries = indicadorSerieBeanRemote.obtenerIndicadorSeriePorIdIndicadorEstado(selectedIndicador.getIdIndicador(), null);
			selectedIndicador.setIndicadorSeries(indicadorSeries);
			this.selectedIndicador = selectedIndicador;
		
		} catch (Exception e) {
			addMessage("Hubieron errores",FacesMessage.SEVERITY_ERROR);
			e.printStackTrace();
		}
		
		
	}


	public Indicador getSelectedIndicador() {
		return selectedIndicador;
	}
	
	
    
}

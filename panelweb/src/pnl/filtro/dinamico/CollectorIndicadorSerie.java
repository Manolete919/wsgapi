package pnl.filtro.dinamico;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import pnl.interfaz.GrupoIndicadorBeanRemote;
import pnl.interfaz.IndicadorSerieBeanRemote;
import pnl.modelo.Indicador;
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
	private Usuario usuario;
	private GrupoIndicadorBeanRemote grupoIndicadorBeanRemote;
	private IndicadorSerieBeanRemote indicadorSerieBeanRemote;
	  
    
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

			grupoIndicadorBeanRemote = (GrupoIndicadorBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/GrupoIndicadorBean");

			
			indicadorSerieBeanRemote  = (IndicadorSerieBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/IndicadorSerieBean");
			

		
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
    			List<IndicadorSerie> indicadorSeries = this.getIndicadorSeries();
    			indicadorSerieBeanRemote.persistIndicadorSeries(indicadorSeries,indicador);
    			addMessage("Datos Guardados exitosamente");
 			
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			addMessage("Hubieron errores");
    			e.printStackTrace();
    		}	

    }
	
   public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
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
	
	
    
}

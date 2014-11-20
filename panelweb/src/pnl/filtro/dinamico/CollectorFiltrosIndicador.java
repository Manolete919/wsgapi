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
import javax.servlet.http.HttpSession;
import pnl.interfaz.FiltroBeanRemote;
import pnl.interfaz.GrupoIndicadorBeanRemote;
import pnl.modelo.Indicador;
import pnl.modelo.ModeloGrafico;
import pnl.modelo.Filtro;
import pnl.modelo.Usuario;
import pnl.servicio.UsuarioServicio;
import pnl.webservice.integracion.ConsultaGenerico;
import pnl.webservice.integracion.Utileria;
import pnl.wsg.Servicio;



@ManagedBean
@ViewScoped
public class CollectorFiltrosIndicador implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Filtro filtro;
	private List<ModeloGrafico> graficoModelos;
	private Indicador indicador;
	private Usuario usuario;
	private List<FiltroValorDefault> filtroValores;
    private List<Filtro> filtros;
    private List<Filtro> filtrosConfigurados;
    private FiltroBeanRemote filtroBeanRemote;
    private GrupoIndicadorBeanRemote grupoIndicadorBeanRemote;
      
	@ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;
    
	private String query;
 
     
    @PostConstruct
    public void init() {
    	
    	
    	
    	filtro = new Filtro();
    	filtro.setEstado("A");
    	filtro.setAnivelIndicador("S");

    	
    	try {
    		indicador = new Indicador();
    		usuario =  usuarioServicio.getUsuario();
    		
			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			filtroBeanRemote = (FiltroBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/FiltroBean");

			grupoIndicadorBeanRemote = (GrupoIndicadorBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/GrupoIndicadorBean");
	
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
    	  	
    	
    	filtros = new ArrayList<Filtro>();
         
    }
    
    
    public void createNew() {
   	
        if(filtros.contains(filtro)) {
            FacesMessage msg = new FacesMessage("Dublicado", "Este filtro ya ha sido agregado");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        else {
       	
        	filtros.add(filtro);
        	filtro = new Filtro();
        }
    }
     
    public String reinit() {
    	
    	filtro.setEstado("A");
    	filtro.setAnivelIndicador("S");   	
    	filtro = new Filtro();
         
        return null;
    }


	public Filtro getFiltro() {
		return filtro;
	}


	public List<Filtro> getFiltros() {	
		return filtros;
	}



	public void inicializar(long idIndicador){
		
		
		
		try {
			
			indicador = grupoIndicadorBeanRemote.obtieneIndicadorPorIdYUsuario(idIndicador,usuario.getIdUsuario());
			filtroValores = new ArrayList<FiltroValorDefault>();
			filtroValores.add(new FiltroValorDefault(null,indicador.getIdServicio().toString()));
			filtrosConfigurados = filtroBeanRemote.obtenerFiltrosDeIndicadorPorIndicadorNivel(idIndicador,"S");
			
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	


	public void guardarfiltros(){
				
  			
		try {
	
			filtroBeanRemote.persistFiltros(this.getFiltros(),indicador);
			filtros = new ArrayList<Filtro>();
			filtro = new Filtro();
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


	public List<ModeloGrafico> getGraficoModelos() {
		return graficoModelos;
	}


	public void setGraficoModelos(List<ModeloGrafico> graficoModelos) {
		this.graficoModelos = graficoModelos;
	}





	protected HttpSession getSession() {

		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}


	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}
	
	
	public String getQuery() {

		
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
	
	public void consultarSentencias(){
		ConsultaGenerico cg = new ConsultaGenerico();
		Utileria u = new Utileria();		
		try {
			Servicio servicio = cg.consultarServicioWebGenerico(u.convertirFiltroValorEnDocument(filtroValores), new Long(3), usuario.getIdUsuario(), usuario.getClave());
			if(servicio != null ){
				if(servicio.get_any() != null ){
					query = cg.procesaDatosIdServicio(servicio.get_any());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public List<Filtro> getFiltrosConfigurados() {
		return filtrosConfigurados;
	}
	

}

package pnl.filtro.dinamico;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import pnl.interfaz.FiltroBeanRemote;
import pnl.modelo.Filtro;
import pnl.servicio.UsuarioServicio;


@ManagedBean
@ViewScoped
public class FiltrosIndicadorConfiguracion {
	
	private List<Filtro> filtros;
	private FiltroBeanRemote filtroBeanRemote;

	@ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;
	

	
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


	
	public void guardarValoresFiltros(){
		
		try {
			
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();         
	     	String idIndicador = (String) params.get("idIndicador"); 
	     	String idModelo = (String) params.get("idModelo"); 
	     	
			filtroBeanRemote.mergeFiltros(filtros);
			addMessage("Se grabo exitosamente");
			
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			
			String parametros = "?ind="+idIndicador;
			
			if (idModelo.equals("4")) { 
				context.redirect(context.getRequestContextPath()+"/paginas/graficos/pastel.xhtml"+parametros);
			} else if (idModelo.equals("1")) {
				context.redirect(context.getRequestContextPath()+"/paginas/graficos/barra.xhtml"+parametros);
			} else if (idModelo.equals("2")) {
				context.redirect(context.getRequestContextPath()+"/paginas/graficos/area.xhtml"+parametros);
			} else if (idModelo.equals("3")) {
				context.redirect(context.getRequestContextPath()+"/paginas/graficos/linea.xhtml"+parametros);
			}
		
		
		
		
		} catch (Exception e) {
			addMessage("Hubo algun error");
			e.printStackTrace();
		}

	
		
		
	}
	
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}


	
	
	
	
}

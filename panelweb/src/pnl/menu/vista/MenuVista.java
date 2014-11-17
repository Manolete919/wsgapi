package pnl.menu.vista;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
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
import pnl.interfaz.GrupoIndicadorBeanRemote;
import pnl.modelo.Indicador;
import pnl.modelo.Usuario;
import pnl.servicio.UsuarioServicio;



@ManagedBean(name = "menuVista", eager = true)
@ViewScoped
public class MenuVista implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String opcion;		
	private Indicador indicador;	
	private GrupoIndicadorBeanRemote grupoIndicadorBeanRemote;
	private List<Indicador> indicadores;
	private Usuario usuario;
	
	@ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;
	

	@PostConstruct
	public void init() {
		
		
	     String ind = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ind");
	     
	     
	    
	     indicador = new Indicador();
	     
	     usuario = usuarioServicio.getUsuario();
	     
	     System.out.println("EL USUARIO LOGGEADO ES " + usuario.getIdUsuario() + " " + usuario.getNombre());
	     
	     
    	try {
			
			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			grupoIndicadorBeanRemote = (GrupoIndicadorBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/GrupoIndicadorBean");
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
  	
		try {
			
			indicadores = grupoIndicadorBeanRemote.obtieneIndicadoresPorIdUsuario(usuario.getIdUsuario());

			long idIndicador = -1;
			if(ind != null){
				idIndicador = new Long(ind);
			}
			
			if(idIndicador != -1) {
				this.opcion = obtenerOpcionSeleccionada(idIndicador);
			}
			
			
			System.out.println("OPCION SELECCIONADA " + this.getOpcion());
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

	}
	

	private String obtenerOpcionSeleccionada(long ind) {
		// TODO Auto-generated method stub
		String opc = null;
		int c =1;
		for(Indicador indicador : this.indicadores){
			if(indicador.getIdIndicador() == ind ){
				opc = Integer.toString(c);
				
				break;
			}
			c++;
		}
		return opc;
	}









	public String getOpcion() {
		return opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}


	public void redireccionarAGrafico() {
	

		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		
		//inicializa el el indicador, tambien debe estar en el GET del indicador, ya que la redireccion lo deja en null
		if( this.getOpcion()!= null)
			indicador = this.getIndicadores().get(Integer.parseInt(this.getOpcion())-1);

		
		String parametros = "?ind="+indicador.getIdIndicador();
		
	
		try {			
			
			if (indicador.getModeloGrafico().getIdModelo() == 1) {
				context.redirect(context.getRequestContextPath()+"/paginas/graficos/barra.xhtml"+parametros);
			} else if (indicador.getModeloGrafico().getIdModelo() == 2) {
				context.redirect(context.getRequestContextPath()+"/paginas/graficos/area.xhtml"+parametros);
			} else if (indicador.getModeloGrafico().getIdModelo() == 3) {
				context.redirect(context.getRequestContextPath()+"/paginas/graficos/linea.xhtml"+parametros);			
			}else if (indicador.getModeloGrafico().getIdModelo() == 4) { 
				context.redirect(context.getRequestContextPath()+"/paginas/graficos/pastel.xhtml"+parametros);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	

	
	
    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
    


	public Indicador getIndicador() {
		
		//inicializa el el indicador
		if( this.getOpcion()!= null)
			indicador = this.getIndicadores().get(Integer.parseInt(this.getOpcion())-1);
			
		return indicador;
	}




	public List<Indicador> getIndicadores() {
			
		return indicadores;
	}




	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}




	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}




	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}




	public Usuario getUsuario() {
		return usuario;
	}
	
	public void actualizarMenu(){		
	
		try {
			
			indicadores = grupoIndicadorBeanRemote.obtieneIndicadoresPorIdUsuario(usuario.getIdUsuario());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	
	
	
	
}

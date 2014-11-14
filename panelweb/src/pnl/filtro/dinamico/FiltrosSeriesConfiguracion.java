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

import pnl.interfaz.FiltroBeanRemote;
import pnl.interfaz.GrupoIndicadorBeanRemote;
import pnl.interfaz.IndicadorSerieBeanRemote;
import pnl.interfaz.IndicadorSerieFiltroBeanRemote;
import pnl.modelo.Indicador;
import pnl.modelo.Filtro;
import pnl.modelo.IndicadorSerieFiltroPK;
import pnl.modelo.Usuario;
import pnl.modelo.IndicadorSerie;
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
	private IndicadorSerieBeanRemote indicadorSerieBeanRemote;
	private List<IndicadorSerie> indicadorSeries;
    private Indicador indicador;
	private Usuario usuario;
	private GrupoIndicadorBeanRemote grupoIndicadorBeanRemote;
	private List<Filtro> filtros;
	private IndicadorSerieFiltroBeanRemote indicadorSerieFiltroBeanRemote;
	private List<IndicadorSerieFiltro> indicadorSerieFiltrosExistentes;
	private FiltroBeanRemote filtroBeanRemote;
	
    @ManagedProperty("#{usuarioServicio}")
	private UsuarioServicio usuarioServicio;
	
	
	@PostConstruct
	public void init(){
    	try {
    		  		
    		
    		usuario = usuarioServicio.getUsuario();
    		
    		indicadorSerieFiltros = new ArrayList<IndicadorSerieFiltro>();
    		
			Properties pr = new Properties();
			pr.put(Context.INITIAL_CONTEXT_FACTORY,
					"weblogic.jndi.WLInitialContextFactory");
			pr.put(Context.PROVIDER_URL, "t3://localhost:7001");

			InitialContext ic = new InitialContext(pr);

			grupoIndicadorBeanRemote = (GrupoIndicadorBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/GrupoIndicadorBean");
			
			
			indicadorSerieFiltroBeanRemote = (IndicadorSerieFiltroBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/IndicadorSerieFiltroBean");
			
			indicadorSerieBeanRemote = (IndicadorSerieBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/IndicadorSerieBean");
			
			filtroBeanRemote = (FiltroBeanRemote) ic
					.lookup("java:global.panel_ear.panel_ejb/FiltroBean");
			
    	
    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
  				
	}
	
	//es inicializado por el PRERENDERVIEW
	public void inicializar(long idIndicador){
		
		
		
		try {
			
			indicador = grupoIndicadorBeanRemote.obtieneIndicadorPorIdYUsuario(idIndicador,usuario.getIdUsuario());
			indicadorSeries =  indicadorSerieBeanRemote.obtenerIndicadorSeriePorIdIndicadorEstado(idIndicador,null);
			filtros = filtroBeanRemote.obtenerFiltrosDeIndicadorPorIndicadorNivel(idIndicador,"N");
			indicadorSerieFiltrosExistentes = indicadorSerieFiltroBeanRemote.obtenerSerieFiltrosPorIdIndicador(idIndicador);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(IndicadorSerie indicadorSerie : indicadorSeries ){        				       				
			
			for(Filtro filtro :filtros){
				
				IndicadorSerieFiltro  indicadorSerieFiltro = new IndicadorSerieFiltro();
				indicadorSerieFiltro.setActualizar(true);
				//para busqueda
				IndicadorSerieFiltroPK pSerieParametroPK = new IndicadorSerieFiltroPK();
				
				pSerieParametroPK.setIdIndicador(indicador.getIdIndicador());
				pSerieParametroPK.setIdSerie(indicadorSerie.getIdSerie());
				pSerieParametroPK.setIdFiltro(filtro.getIdFiltro());
				
				indicadorSerieFiltro = buscarPserieParametroIngresada(pSerieParametroPK);
			
				
				//si no existe,debidos a parametros recientes
				if(indicadorSerieFiltro == null){	
					
					indicadorSerieFiltro = new IndicadorSerieFiltro();
					indicadorSerieFiltro.setId(pSerieParametroPK);
					indicadorSerieFiltro.setActualizar(false);
					indicadorSerieFiltro.setIndicadorSery(indicadorSerie);
					indicadorSerieFiltro.setIndicador(indicador);
					indicadorSerieFiltro.setFiltro(filtro);
					
				}else{
					indicadorSerieFiltro.setActualizar(true);
				}
				
				indicadorSerieFiltros.add(indicadorSerieFiltro);
			}  	
			
			   				
		}

	}


	private IndicadorSerieFiltro buscarPserieParametroIngresada(
			IndicadorSerieFiltroPK pSerieParametroPK) {
		IndicadorSerieFiltro indicadorSerieFiltro = null;
		
		
		
		
		for(IndicadorSerieFiltro filtroExistente : getIndicadorSerieFiltrosExistentes()){
			
			if(filtroExistente.getId().equals(pSerieParametroPK)) {
				indicadorSerieFiltro = filtroExistente;
				break;
			}
			
		}
		
		// TODO Auto-generated method stub
		return indicadorSerieFiltro;
	}





	public List<IndicadorSerieFiltro> getIndicadorSerieFiltros() {
		return indicadorSerieFiltros;
	}


	public void setIndicadorSerieFiltros(List<IndicadorSerieFiltro> indicadorSerieFiltros) {
		this.indicadorSerieFiltros = indicadorSerieFiltros;
	}






	
	
	public void guardarFiltrosSeries(){
				
		try{
			indicadorSerieFiltroBeanRemote.persistIndicadorSerieFiltros(this.getIndicadorSerieFiltros());
			addMessage("Se grabo exitosamente");
		}catch (Exception e){
			addMessage("Hubo algun error");
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
	
    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary,  null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}


    

}

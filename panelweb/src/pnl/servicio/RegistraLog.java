package pnl.servicio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;

import pnl.interfaz.AccionUsuarioBeanRemote;
import pnl.interfaz.FiltroBeanRemote;
import pnl.interfaz.GrupoBeanRemote;
import pnl.interfaz.IndicadorBeanRemote;
import pnl.interfaz.IndicadorSerieBeanRemote;
import pnl.interfaz.IndicadorSerieFiltroBeanRemote;
import pnl.interfaz.LogUsuarioBeanRemote;
import pnl.interfaz.RecursosAppBeanRemote;
import pnl.modelo.AccionUsuario;
import pnl.modelo.Filtro;
import pnl.modelo.Grupo;
import pnl.modelo.Indicador;
import pnl.modelo.IndicadorSerie;
import pnl.modelo.IndicadorSerieFiltro;
import pnl.modelo.LogUsuario;
import pnl.modelo.RecursosApp;
import pnl.servicio.UsuarioServicio;

@ManagedBean(name="registraLog", eager = true)
@ViewScoped
public class RegistraLog implements Serializable {
	
	
	/**
	 * 
	 */
	
	
	
	
	private static final long serialVersionUID = 1L;
	
	public static final long ACCION_CREAR = 1L;
	public static final long ACCION_EDITAR = 2L;
	public static final long ACCION_BORRAR = 3L;
	
	public static final long  RECURSO_GRUPO = 1L;
	public static final long  RECURSO_INDICADOR = 2L;
	public static final long  RECURSO_SERIE = 3L;
	public static final long  RECURSO_FILTROS = 4L;
	public static final long  RECURSO_FILTRO_SERIE = 5L;
	public static final long  RECURSO_FILTRO_INDICADOR = 6L;
	
	private LogUsuarioBeanRemote logUsuarioBeanRemote;
	private RecursosAppBeanRemote recursosAppBeanRemote;
	private RecursosApp recursosApp;
	private AccionUsuarioBeanRemote accionUsuarioBeanRemote;
	private AccionUsuario accionUsuario;
	private LogUsuario logUsuario;
	
	private IndicadorBeanRemote indicadorBeanRemote;
	private FiltroBeanRemote filtroBeanRemote;
	private GrupoBeanRemote grupoBeanRemote;
	private IndicadorSerieBeanRemote indicadorSerieBeanRemote;
	private IndicadorSerieFiltroBeanRemote indicadorSerieFiltroBeanRemote;

	
	
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

			logUsuarioBeanRemote = (LogUsuarioBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/LogUsuarioBean");
			
			recursosAppBeanRemote  = (RecursosAppBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/RecursosAppBean");
			
			accionUsuarioBeanRemote = (AccionUsuarioBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/AccionUsuarioBean");
		
			indicadorBeanRemote = (IndicadorBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/IndicadorBean");
			
			filtroBeanRemote = (FiltroBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/FiltroBean");
			
			grupoBeanRemote = (GrupoBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/GrupoBean");
    	
			indicadorSerieBeanRemote = (IndicadorSerieBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/IndicadorSerieBean");
    	
			indicadorSerieFiltroBeanRemote = (IndicadorSerieFiltroBeanRemote) ic.lookup("java:global.panel_ear.panel_ejb/IndicadorSerieFiltroBean");
    	
    	} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public <T> void registrarLog(List<T> detalles,long idAccionUsuario,long idRecursosApp){
		
		
		try {
			
			accionUsuario = accionUsuarioBeanRemote.obtenerAccionUsuario(idAccionUsuario);
			recursosApp = recursosAppBeanRemote.obtenerRecursosAppPorId(idRecursosApp);
			
			logUsuario = new LogUsuario();
			
			logUsuario.setUsuario(usuarioServicio.getUsuario());		
			logUsuario.setAccionUsuario(accionUsuario);
			logUsuario.setRecursosApp(recursosApp);
			logUsuario.setIdSesion(usuarioServicio.getSession().getId());
			
			
			
			for(T detalle : detalles ){
				
				Date d = new Date();
		        Calendar c = Calendar.getInstance();
		        c.setTime(d);
		        logUsuario.setFecha(c.getTime());
		        

				
				
				if(idAccionUsuario == ACCION_EDITAR){
					
					if(detalle instanceof Grupo){			
						
						Grupo grupoNuevo = ((Grupo) detalle);
						
						Grupo grupoViejo = grupoBeanRemote.obtenerGrupoPorId(grupoNuevo.getIdGrupo());
						
						if( grupoViejo != null )
							logUsuario.setDetalle(grupoViejo.getDescripcion()+"->"+grupoNuevo.getDescripcion());				
					
					}
					
					if(detalle instanceof Indicador){	
						
						Indicador indicadorNuevo = ((Indicador) detalle);
						
						Indicador indicadorViejo = indicadorBeanRemote.obtenerIndicadorPorId(indicadorNuevo.getIdIndicador()); 

						if(indicadorViejo != null){
							logUsuario.setDetalle(indicadorViejo.getNombre()+"->"+indicadorNuevo.getNombre());				
						}
								
					
					}
					
					if(detalle instanceof Filtro){	
						
						Filtro filtroNuevo =  ((Filtro) detalle);
						
						Filtro filtroViejo = filtroBeanRemote.obtenerFiltroPorId(filtroNuevo.getIdFiltro());
						
						if(filtroViejo != null ){
							
							if(idRecursosApp == RECURSO_FILTROS){
								logUsuario.setDetalle(filtroViejo.getNombre()+"->"+filtroNuevo.getNombre());				
							}else if (idRecursosApp == RECURSO_FILTRO_INDICADOR){
								logUsuario.setDetalle(filtroViejo.getNombre()+":"+filtroViejo.getValorInicial() +"->"+ filtroNuevo.getValorInicial());				
							}
							
						}
						
							
					
					
					}
					
					if(detalle instanceof IndicadorSerie){	
						
						IndicadorSerie serieNuevo = ((IndicadorSerie) detalle);
						
						IndicadorSerie serieVieja = indicadorSerieBeanRemote.obtenerIndicadorSeriePorId(serieNuevo.getIdSerie());
						
						if(serieVieja != null )
							
							logUsuario.setDetalle(serieVieja.getNombre()+"->"+serieNuevo.getNombre());	
						
						
					}
					
					if(detalle instanceof IndicadorSerieFiltro){
						
						IndicadorSerieFiltro serieFiltroNuevo = ((IndicadorSerieFiltro) detalle);
						
						IndicadorSerieFiltro serieFiltroViejo = indicadorSerieFiltroBeanRemote.obtenerIndicadorSerieFiltroPorId(serieFiltroNuevo.getId());
						
						//RECURSO_FILTRO_SERIE
						if(serieFiltroViejo != null )
						
							logUsuario.setDetalle(serieFiltroViejo.getIndicadorSery().getNombre()+":"+serieFiltroViejo.getFiltro().getNombre()+":"+serieFiltroViejo.getValor()+"->"+serieFiltroNuevo.getValor());				
					
					}
				
				}else{
					
					if(detalle instanceof Grupo){			
						
						logUsuario.setDetalle(((Grupo) detalle).getDescripcion());				
					
					}
					
					if(detalle instanceof Indicador){	

						logUsuario.setDetalle(((Indicador) detalle).getNombre());				
					}
					
					if(detalle instanceof Filtro){					
						logUsuario.setDetalle(((Filtro) detalle).getNombre());				
					}
					
					if(detalle instanceof IndicadorSerie){					
						logUsuario.setDetalle(((IndicadorSerie) detalle).getNombre());				
					}
					
					if(detalle instanceof IndicadorSerieFiltro){					
						logUsuario.setDetalle(((IndicadorSerieFiltro) detalle).getFiltro().getNombre());				
					}
					
				}
				

				
				
				
				
				
			
				logUsuarioBeanRemote.persistLogUsuario(logUsuario);
				
				
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


    	
		


	
	}

	public void setUsuarioServicio(UsuarioServicio usuarioServicio) {
		this.usuarioServicio = usuarioServicio;
	}
	

	
	

}

package pnl.graficos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.HorizontalBarChartModel;
import org.primefaces.model.chart.ChartSeries;

import pnl.filtro.dinamico.Dinamico;
import pnl.filtro.dinamico.FiltroValorDefault;
import pnl.filtro.dinamico.FiltrosIndicadorSeriesValor;
import pnl.modelo.IndicadorSerie;
import pnl.webservice.integracion.ConsultaGenerico;
import pnl.webservice.integracion.Generico;
import pnl.webservice.integracion.Utileria;
import pnl.wsg.Servicio;


 
@ManagedBean
public class BarView implements Serializable {
	
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BarChartModel barModel;
    private HorizontalBarChartModel horizontalBarModel;
	private String mensajeDeAplicacion = "";
	private int codigoDeAplicacion = 0;
	private int cantidadRegistros  = 0;

 
	@ManagedProperty("#{dinamico}")
	private Dinamico dinamico;
	
	
	    
    @PostConstruct
    public void init() {
        createBarModels();
    }
 
    public BarChartModel getBarModel() {
    	
        return barModel;
    }
     
    public HorizontalBarChartModel getHorizontalBarModel() {
        return horizontalBarModel;
    }
 
    private BarChartModel initBarModel() {
        BarChartModel model = new BarChartModel();
        
		List<FiltrosIndicadorSeriesValor> serieGraficoParametrosPropiedadValores =   dinamico.obtieneParametrosDeSeriesMasGrafico();
		ConsultaGenerico cg = new ConsultaGenerico();
 
  
            try {  
    	        
   	        
    			//iterar todas las series del grafico
    			
    			if(serieGraficoParametrosPropiedadValores != null){
    
    				
    				for (FiltrosIndicadorSeriesValor serieGraficoParametrosPropiedadValor : serieGraficoParametrosPropiedadValores) {
    	
    					IndicadorSerie pSerieGrafico = serieGraficoParametrosPropiedadValor.getIndicadorSerie();
    					ChartSeries serie = new ChartSeries();
    					//serie.setFill(true);
    					serie.setLabel(pSerieGrafico.getNombre());
    					
    					//agregar los parametros del grafico
    					
    					List<FiltroValorDefault> parametrosPropiedadValores = serieGraficoParametrosPropiedadValor.getFiltroValorDefaults();
 

    					List<Generico> datos = new ArrayList<Generico>();
    					datos.add(new Generico("",0));
    					Servicio servicio = null;
						Utileria u = new Utileria();
						CatalogoError catalogo = new CatalogoError();
						try {
							
							System.out.print(Utileria.convertirDocumentToString(u.convertirFiltroValorEnDocument(parametrosPropiedadValores)));
							servicio = cg.consultarServicioWebGenerico(u.convertirFiltroValorEnDocument(parametrosPropiedadValores),dinamico.getIndicador().getIdServicio().longValue(),dinamico.getUsuario().getUsuariosWsg().getIdUsuario(), dinamico.getUsuario().getUsuariosWsg().getClave());
							if(servicio != null ){
								if(servicio.get_any() != null ){
									datos = cg.procesaDatosDeGraficos(servicio.get_any());
									cantidadRegistros = datos.size();
								}
								mensajeDeAplicacion =    catalogo.obtenerMensajeDeErrorPorNombrePropiedad(servicio.getProveedorBase(), servicio.getCodigoError());
								codigoDeAplicacion = servicio.getCodigoError();
							}else{
								mensajeDeAplicacion =    "El servicio web al que accesa la aplicacion no está disponible, intentelo mas tarde, o póngase en contacto con sistemas";
								codigoDeAplicacion = -10;
							}

						}catch(NumberFormatException nfe){
							mensajeDeAplicacion =    "Se esperaba en la segunda columna de la sentencia un valor numério y se ha obtenido caracter, revise la consulta";
							codigoDeAplicacion = -11;
						}catch (Exception e) {
							mensajeDeAplicacion =    "Ha ocurrido algun error inesperado, comuníquese con sistemas";
							codigoDeAplicacion = -12;
							e.printStackTrace();
						} 					
    					
    					
    					// setear cada serie
    					for (Generico dato : datos) {
    						serie.set(dato.getObjetoX(), dato.getObjetoY());
    					}
    	
    					model.addSeries(serie);
    			
    				
    	
    				}
    			}
    	        	        
            
            } catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}

       

         
        return model;
    }
     
    private void createBarModels() {
        createBarModel();
        
    }
     
    private void createBarModel() {
    	
   
		
		try {
			
			barModel = initBarModel();
	         
	        barModel.setTitle(dinamico.getIndicador().getNombre());
	        barModel.setLegendPosition("ne");
	         
	        Axis xAxis = barModel.getAxis(AxisType.X);
	        xAxis.setLabel(dinamico.getIndicador().getEtiquetaEjex());
	         
	        Axis yAxis = barModel.getAxis(AxisType.Y);
	        yAxis.setLabel(dinamico.getIndicador().getEtiquetaEjey());
	        yAxis.setMin(dinamico.getIndicador().getValorMiny());
	        yAxis.setMax(dinamico.getIndicador().getValorMaxy());
	        
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
        
    }
     

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	


	public void setDinamico(Dinamico dinamico) {
		this.dinamico = dinamico;
	}

	public String getMensajeDeAplicacion() {
		return mensajeDeAplicacion;
	}

	public int getCodigoDeAplicacion() {
		return codigoDeAplicacion;
	}

	public int getCantidadRegistros() {
		return cantidadRegistros;
	}

	public void setCantidadRegistros(int cantidadRegistros) {
		this.cantidadRegistros = cantidadRegistros;
	}



 
}
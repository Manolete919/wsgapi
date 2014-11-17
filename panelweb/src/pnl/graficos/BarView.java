package pnl.graficos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
    private static int contador = 0;
   
    private Date date1;
	private Date date2;
 
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
    					datos.add(new Generico(0,0));
    					Servicio servicio = null;
    					if(parametrosPropiedadValores != null ){
    						if(!parametrosPropiedadValores.isEmpty()){
    							Utileria u = new Utileria();
    							try {
    								
    								System.out.print(Utileria.convertirDocumentToString(u.convertirFiltroValorEnDocument(parametrosPropiedadValores)));
    								servicio = cg.consultarServicioWebGenerico(u.convertirFiltroValorEnDocument(parametrosPropiedadValores),dinamico.getIndicador().getIdServicio().longValue(),dinamico.getUsuario().getIdUsuario(), dinamico.getUsuario().getClave());
    								if(servicio != null ){
    									if(servicio.get_any() != null ){
    										datos = new ArrayList<Generico>();
    										datos = cg.procesaDatosDeGraficos(servicio.get_any());
    									}
    								}

    							} catch (Exception e) {
    								// TODO Auto-generated catch block
    								e.printStackTrace();
    							}
    						}
    						
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
        createHorizontalBarModel();
    }
     
    private void createBarModel() {
    	
    	/*String indiceSubmenu = TreeBean.getIndiceSubmenu();
		String indiceItem = TreeBean.getIndiceItem();
		String ordenGrafico = TreeBean.getOrdenGrafico(); */
		
		//PnlGraficoPK pnlGraficoPK = new PnlGraficoPK();
		
		/*pnlGraficoPK.setIndiceSubmenu(new Long(indiceSubmenu));
		pnlGraficoPK.setIndiceItem(new Long(indiceItem));
		pnlGraficoPK.setOrdenGrafico(new Long(ordenGrafico));*/
    	
		//PnlGrafico grafico;
		
		try {
			
			//grafico = panelGraficoBeanRemote.consultaGraficoPorIdGraficoEIdGraficoDef(pnlGraficoPK,idGraficoDef);
			barModel = initBarModel();
	         
	        barModel.setTitle(dinamico.getIndicador().getNombre());
	        barModel.setLegendPosition("ne");
	         
	        Axis xAxis = barModel.getAxis(AxisType.X);
	        xAxis.setLabel(dinamico.getIndicador().getEtiquetaEjex());
	         
	        Axis yAxis = barModel.getAxis(AxisType.Y);
	        yAxis.setLabel(dinamico.getIndicador().getEtiquetaEjey());
	        yAxis.setMin(0);
	        yAxis.setMax(200);
	        
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
        
    }
     
    private void createHorizontalBarModel() {
        horizontalBarModel = new HorizontalBarChartModel();
 
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Boys");
        boys.set("2004", 50);
        boys.set("2005", 96);
        boys.set("2006", 44);
        boys.set("2007", 55);
        boys.set("2008", 25);
 
        ChartSeries girls = new ChartSeries();
        girls.setLabel("Girls");
        girls.set("2004", 52);
        girls.set("2005", 60);
        girls.set("2006", 82);
        girls.set("2007", 35);
        girls.set("2008", 120);
 
        horizontalBarModel.addSeries(boys);
        horizontalBarModel.addSeries(girls);
         
        horizontalBarModel.setTitle("Horizontal and Stacked");
        horizontalBarModel.setLegendPosition("e");
        //horizontalBarModel.setStacked(true);
         
        Axis xAxis = horizontalBarModel.getAxis(AxisType.X);
        xAxis.setLabel("Births");
        xAxis.setMin(0);
        xAxis.setMax(200);
         
        Axis yAxis = horizontalBarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Gender");       
    }
    
	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
    public Date getDate1() {
        return date1;
    }
 
    public void setDate1(Date date1) {
        this.date1 = date1;
    }
 
    public Date getDate2() {
        return date2;
    }
 
    public void setDate2(Date date2) {
        this.date2 = date2;
    }

	public static int getContador() {
		return contador;
	}

	public static void setContador(int contador) {
		BarView.contador = contador;
	}

	public void setDinamico(Dinamico dinamico) {
		this.dinamico = dinamico;
	}



	
	
 
}
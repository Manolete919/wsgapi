package pnl.graficos;
 
import javax.annotation.PostConstruct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;

import pnl.filtro.dinamico.Dinamico;
import pnl.filtro.dinamico.FiltroValorDefault;
import pnl.filtro.dinamico.FiltrosIndicadorSeriesValor;
import pnl.modelo.IndicadorSerie;
import pnl.webservice.integracion.ConsultaGenerico;
import pnl.webservice.integracion.Generico;
import pnl.webservice.integracion.Utileria;
@ManagedBean
public class LineView implements Serializable {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LineChartModel lineModel1;
	private Date date1;
	private Date date2;
	
   // private LineChartModel lineModel2;
	
	@ManagedProperty("#{dinamico}")
	private Dinamico dinamico;
	

     
   
	@PostConstruct
    public void init() {
        createLineModels();
    }
    

 
    public LineChartModel getLineModel1() {
   
        return lineModel1;
    }
 
    /*public LineChartModel getLineModel2() {
        return lineModel2;
    } */
     
    private void createLineModels() {
    	
	
		
		
    	/*String indiceSubmenu = TreeBean.getIndiceSubmenu();
		String indiceItem = TreeBean.getIndiceItem();
		String ordenGrafico = TreeBean.getOrdenGrafico();
		
		PnlGraficoPK pnlGraficoPK = new PnlGraficoPK();
		
		pnlGraficoPK.setIndiceSubmenu(new Long(indiceSubmenu));
		pnlGraficoPK.setIndiceItem(new Long(indiceItem));
		pnlGraficoPK.setOrdenGrafico(new Long(ordenGrafico));
	   	
		PnlGrafico grafico; */
		
		try {
			
			//grafico = panelGraficoBeanRemote.consultaGraficoPorIdGraficoEIdGraficoDef(pnlGraficoPK,idGraficoDef);	  	
	        lineModel1 = initLinearModel();
	        lineModel1.setTitle(dinamico.getIndicador().getNombre());
	        lineModel1.setLegendPosition("e");
	        Axis yAxis = lineModel1.getAxis(AxisType.Y);
	        //yAxis.setLabel(dinamico.getIndicador().getEtiquetaEjex());
	        yAxis.setMin(0);
	        yAxis.setMax(10);
        
        }catch(Exception e){
        	e.printStackTrace();
        }
         
       /* lineModel2 = initCategoryModel();
        lineModel2.setTitle("Category Chart");
        lineModel2.setLegendPosition("e");
        lineModel2.setShowPointLabels(true);
        lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Years"));
        yAxis = lineModel2.getAxis(AxisType.Y); 
        yAxis.setLabel("Births");
        yAxis.setMin(0);
        yAxis.setMax(200); */
    }
     
    private LineChartModel initLinearModel() {
    	LineChartModel model = new LineChartModel();
 
		List<FiltrosIndicadorSeriesValor> serieGraficoParametrosPropiedadValores =   dinamico.obtieneParametrosDeSeriesMasGrafico();
		ConsultaGenerico cg = new ConsultaGenerico();
		
        try {
        	
        	
			//iterar todas las series del grafico
			
			if(serieGraficoParametrosPropiedadValores != null){
				
			
		
				
				for (FiltrosIndicadorSeriesValor serieGraficoParametrosPropiedadValor : serieGraficoParametrosPropiedadValores) {
	
					IndicadorSerie pSerieGrafico = serieGraficoParametrosPropiedadValor.getIndicadorSerie();
					LineChartSeries serie = new LineChartSeries();
					//serie.setFill(true);
					serie.setLabel(pSerieGrafico.getNombre());
					
					System.out.println("");
					
					//agregar los parametros del grafico
					
					
					List<FiltroValorDefault> parametrosPropiedadValores = serieGraficoParametrosPropiedadValor.getFiltroValorDefaults();
					
	
					
					List<Generico> datos = new ArrayList<Generico>();
					datos.add(new Generico(0,0));
					if(parametrosPropiedadValores != null ){
						if(!parametrosPropiedadValores.isEmpty()){
							Utileria u = new Utileria();
							try {
								datos = new ArrayList<Generico>();
								System.out.print(Utileria.convertirDocumentToString(u.convertirFiltroValorEnDocument(parametrosPropiedadValores)));
								//datos = cg.consultaDatosDelWebserice(u.convertirParametrosPropiedadValorEnDocument(parametrosPropiedadValores),dinamico.getIndicador().getIdServicio().longValue());
								datos = cg.consultaDatosWsg(u.convertirFiltroValorEnDocument(parametrosPropiedadValores),dinamico.getIndicador().getIdServicio().longValue(),dinamico.getUsuario().getIdUsuario(), dinamico.getUsuario().getClave());

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

	        
		
        }catch(Exception e){
        	e.printStackTrace();
        }

        return model;
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

	public void setDinamico(Dinamico dinamico) {
		this.dinamico = dinamico;
	}


 
    
    
}
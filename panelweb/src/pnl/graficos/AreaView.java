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
import org.primefaces.model.chart.CategoryAxis;
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
public class AreaView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LineChartModel areaModel;

	private Date date1;
	private Date date2;
	
	@ManagedProperty("#{dinamico}")
	private Dinamico dinamico;
	


	

  @PostConstruct
    public void init() {
        createAreaModel();
    }

  public LineChartModel getAreaModel() {
      return areaModel;
  }

	private void createAreaModel() {
		areaModel = new LineChartModel();
		
		
		
		List<FiltrosIndicadorSeriesValor> serieGraficoParametrosPropiedadValores =   dinamico.obtieneParametrosDeSeriesMasGrafico();
		ConsultaGenerico cg = new ConsultaGenerico();
		try {
			

			// datos del grafico
			areaModel.setTitle(dinamico.getIndicador().getNombre());
			areaModel.setLegendPosition("ne");
			areaModel.setStacked(true);
			areaModel.setShowPointLabels(true);

			Axis xAxis = new CategoryAxis(dinamico.getIndicador().getEtiquetaEjex());
			areaModel.getAxes().put(AxisType.X, xAxis);
			Axis yAxis = areaModel.getAxis(AxisType.Y);
			yAxis.setLabel(dinamico.getIndicador().getEtiquetaEjey());
			yAxis.setMin(0);
			yAxis.setMax(300);
	

			
				for (FiltrosIndicadorSeriesValor serieGraficoParametrosPropiedadValor : serieGraficoParametrosPropiedadValores) {
	
					IndicadorSerie pSerieGrafico = serieGraficoParametrosPropiedadValor.getIndicadorSerie();
					LineChartSeries serie = new LineChartSeries();
					serie.setFill(true);
					serie.setLabel(pSerieGrafico.getNombre());
					
				
		
					
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
	
					areaModel.addSeries(serie);
					
					
	
				}
		
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
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
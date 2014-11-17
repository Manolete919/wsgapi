package pnl.graficos;

import javax.annotation.PostConstruct;

import java.io.Serializable;
import java.util.ArrayList;
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
import pnl.wsg.Servicio;


@ManagedBean
public class AreaView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LineChartModel areaModel;
	private String mensajeDeAplicacion = "";
	private int codigoDeAplicacion = 0;

	
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
									mensajeDeAplicacion = servicio.getMensajeError();
									codigoDeAplicacion = servicio.getCodigoError();
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


	public void setDinamico(Dinamico dinamico) {
		this.dinamico = dinamico;
	}

	public String getMensajeDeAplicacion() {
		return mensajeDeAplicacion;
	}

	public int getCodigoDeAplicacion() {
		return codigoDeAplicacion;
	}

	
	
}
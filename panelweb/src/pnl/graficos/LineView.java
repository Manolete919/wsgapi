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
public class LineView implements Serializable {

	private static final long serialVersionUID = 1L;
	private LineChartModel lineModel2;
	@ManagedProperty("#{dinamico}")
	private Dinamico dinamico;
	private String mensajeDeAplicacion = "";
	private int codigoDeAplicacion = 0;
	private int cantidadRegistros  = 0;

	@PostConstruct
	public void init() {
		createLineModels();
	}

	public LineChartModel getLineModel2() {
		return lineModel2;
	}

	private void createLineModels() {
		try {
			lineModel2 = initCategoryModel();
			lineModel2.setTitle(dinamico.getIndicador().getNombre());
			lineModel2.setLegendPosition("e");
			lineModel2.setShowPointLabels(true);
			lineModel2.getAxes()
					.put(AxisType.X,
							new CategoryAxis(dinamico.getIndicador()
									.getEtiquetaEjey()));
			Axis yAxis = lineModel2.getAxis(AxisType.Y);
			yAxis.setLabel(dinamico.getIndicador().getEtiquetaEjex());
			yAxis.setMin(dinamico.getIndicador().getValorMiny());
			yAxis.setMax(dinamico.getIndicador().getValorMaxy());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private LineChartModel initCategoryModel() {
		LineChartModel model = new LineChartModel();
		List<FiltrosIndicadorSeriesValor> serieGraficoParametrosPropiedadValores = dinamico
				.obtieneParametrosDeSeriesMasGrafico();
		ConsultaGenerico cg = new ConsultaGenerico();
		try {
			// iterar todas las series del grafico
			if (serieGraficoParametrosPropiedadValores != null) {
				for (FiltrosIndicadorSeriesValor serieGraficoParametrosPropiedadValor : serieGraficoParametrosPropiedadValores) {
					IndicadorSerie pSerieGrafico = serieGraficoParametrosPropiedadValor
							.getIndicadorSerie();
					LineChartSeries serie = new LineChartSeries();
					// serie.setFill(true);
					serie.setLabel(pSerieGrafico.getNombre());
		
					// agregar los parametros del grafico
					List<FiltroValorDefault> parametrosPropiedadValores = serieGraficoParametrosPropiedadValor.getFiltroValorDefaults();
					List<Generico> datos = new ArrayList<Generico>();
					datos.add(new Generico("", 0));
					Servicio servicio = null;
					Utileria u = new Utileria();
					CatalogoError catalogo = new CatalogoError();
					try {
						System.out.print(Utileria.convertirDocumentToString(u.convertirFiltroValorEnDocument(parametrosPropiedadValores)));
						servicio = cg.consultarServicioWebGenerico(u.convertirFiltroValorEnDocument(parametrosPropiedadValores),dinamico.getIndicador().getIdServicio().longValue(), dinamico.getUsuario().getUsuariosWsg().getIdUsuario(),dinamico.getUsuario().getUsuariosWsg().getClave());
						if (servicio != null) {
							if (servicio.get_any() != null) {
								
								datos = new ArrayList<Generico>();
								datos = cg.procesaDatosDeGraficos(servicio.get_any());
								
								cantidadRegistros = datos.size();
								
								
							}
							mensajeDeAplicacion =    catalogo.obtenerMensajeDeErrorPorNombrePropiedad(servicio.getProveedorBase(), servicio.getCodigoError());
							codigoDeAplicacion = servicio.getCodigoError();
						}else{
							mensajeDeAplicacion =    "El servicio web al que accesa la aplicacion no est� disponible, intentelo mas tarde, o p�ngase en contacto con sistemas";
							codigoDeAplicacion = -10;
						}
					}catch(NumberFormatException nfe){
						mensajeDeAplicacion =    "Se esperaba en la segunda columna de la sentencia un valor num�rio y se ha obtenido caracter, revise la consulta";
						codigoDeAplicacion = -11;
					}catch (Exception e) {
						mensajeDeAplicacion =    "Ha ocurrido algun error inesperado, comun�quese con sistemas";
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
			e.printStackTrace();
		}
		return model;
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
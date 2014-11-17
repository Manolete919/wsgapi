package pnl.graficos;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;
import pnl.filtro.dinamico.Dinamico;
import pnl.filtro.dinamico.FiltroValorDefault;
import pnl.filtro.dinamico.FiltrosIndicadorSeriesValor;
import pnl.modelo.IndicadorSerie;
import pnl.webservice.integracion.ConsultaGenerico;
import pnl.webservice.integracion.Generico;
import pnl.webservice.integracion.Utileria;
import pnl.wsg.Servicio;


@ManagedBean
public class PieView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PieChartModel pieModel1;
	private PieChartModel pieModel2;
	@ManagedProperty("#{dinamico}")
	private Dinamico dinamico;
	

	@PostConstruct
    public void init() {
        createPieModels();
    }

	public PieChartModel getPieModel1() {

		return pieModel1;
	}

	public PieChartModel getPieModel2() {
		return pieModel2;
	}

	private void createPieModels() {
		createPieModel1();
		createPieModel2();
	}

	private void createPieModel1() {
		pieModel1 = new PieChartModel();
		

	       
		List<FiltrosIndicadorSeriesValor> filtrosIndicadorSeriesValores =   dinamico.obtieneParametrosDeSeriesMasGrafico();
		ConsultaGenerico cg = new ConsultaGenerico();
			
		try {



			
			if(!filtrosIndicadorSeriesValores.isEmpty()){
				
				FiltrosIndicadorSeriesValor filtrosIndicadorSeriesValor = filtrosIndicadorSeriesValores.get(0);
			
				IndicadorSerie pSerieGrafico = filtrosIndicadorSeriesValor.getIndicadorSerie();
				ChartSeries serie = new ChartSeries();
				//serie.setFill(true);
				serie.setLabel(pSerieGrafico.getNombre());
				
				//agregar los parametros del grafico
				List<FiltroValorDefault> parametrosPropiedadValores = filtrosIndicadorSeriesValor.getFiltroValorDefaults();
				
	
				

				List<Generico> datos = new ArrayList<Generico>();
				datos.add(new Generico("",0));
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
				
			
				pieModel1.setTitle(pSerieGrafico.getNombre()); 
				
				String objetoX = "";
				
	
				for (Generico dato : datos) {
					objetoX = "";
										
					if(dato.getObjetoX()!= null){
						objetoX = (String) dato.getObjetoX();						
					}
					pieModel1.set(objetoX, dato.getObjetoY());
				}

				
				
			
			
			}

			
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		pieModel1.setLegendPosition("w");
	}

	private void createPieModel2() {

		pieModel2 = new PieChartModel();
		pieModel2.set("Brand 1", 540);
		pieModel2.set("Brand 2", 325);
		pieModel2.set("Brand 3", 702);
		pieModel2.set("Brand 4", 421);

		pieModel2.setTitle("Custom Pie");
		pieModel2.setLegendPosition("e");
		pieModel2.setFill(false);
		pieModel2.setShowDataLabels(true);
		pieModel2.setDiameter(150);
	}



	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
				summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
    
}
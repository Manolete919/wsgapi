package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the INDICADOR database table.
 * 
 */
@Entity
@Cacheable(false)
@NamedQuery(name="Indicador.findAll", query="SELECT i FROM Indicador i")
public class Indicador implements Serializable {
	private static final long serialVersionUID = 1L;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
	@SequenceGenerator(name="INDICADOR_IDINDICADOR_GENERATOR", sequenceName="SQ_INDICADOR",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INDICADOR_IDINDICADOR_GENERATOR")
	@Column(name="ID_INDICADOR")
	private long idIndicador;
    
    @Size(max = 200)
	private String nombre;
	
	
	
	
	@Column(name="ID_SERVICIO")
	private BigDecimal idServicio;
	
	@Size(max = 1)
	private String estado;

	@Size(max = 200)
	@Column(name="ETIQUETA_EJEX")
	private String etiquetaEjex;

	@Size(max = 200)
	@Column(name="ETIQUETA_EJEY")
	private String etiquetaEjey;
	
	@Column(name="VALOR_MAXY")
	private BigDecimal valorMaxy;

	@Column(name="VALOR_MINY")
	private BigDecimal valorMiny;
	
	//bi-directional many-to-one association to ModeloGrafico
	@ManyToOne
	@JoinColumn(name="ID_MODELO_GRAFICO")
	private ModeloGrafico modeloGrafico;

	

	//bi-directional many-to-one association to Filtro
	@OneToMany(mappedBy="indicador",cascade=CascadeType.REMOVE)
	private List<Filtro> filtros;


	
	//bi-directional many-to-one association to GrupoIndicador
	@OneToMany(mappedBy="indicador",cascade=CascadeType.REMOVE)
	private List<GrupoIndicador> grupoIndicadores;

	//bi-directional many-to-one association to IndicadorSerie
	@OneToMany(mappedBy="indicador",cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	private List<IndicadorSerie> indicadorSeries;

	//bi-directional many-to-one association to IndicadorSerieFiltro
	@OneToMany(mappedBy="indicador",cascade=CascadeType.REMOVE)
	private List<IndicadorSerieFiltro> indicadorSerieFiltros;

	

	public Indicador() {
	}

	
	public long getIdIndicador() {
		return idIndicador;
	}


	public void setIdIndicador(long idIndicador) {
		this.idIndicador = idIndicador;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public BigDecimal getIdServicio() {
		return idServicio;
	}


	public void setIdServicio(BigDecimal idServicio) {
		this.idServicio = idServicio;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public String getEtiquetaEjex() {
		return etiquetaEjex;
	}


	public void setEtiquetaEjex(String etiquetaEjex) {
		this.etiquetaEjex = etiquetaEjex;
	}


	public String getEtiquetaEjey() {
		return etiquetaEjey;
	}


	public void setEtiquetaEjey(String etiquetaEjey) {
		this.etiquetaEjey = etiquetaEjey;
	}


	public BigDecimal getValorMaxy() {
		return valorMaxy;
	}


	public void setValorMaxy(BigDecimal valorMaxy) {
		this.valorMaxy = valorMaxy;
	}





	public BigDecimal getValorMiny() {
		return valorMiny;
	}


	public void setValorMiny(BigDecimal valorMiny) {
		this.valorMiny = valorMiny;
	}


	public ModeloGrafico getModeloGrafico() {
		return modeloGrafico;
	}


	public void setModeloGrafico(ModeloGrafico modeloGrafico) {
		this.modeloGrafico = modeloGrafico;
	}


	public List<Filtro> getFiltros() {
		return filtros;
	}


	public void setFiltros(List<Filtro> filtros) {
		this.filtros = filtros;
	}


	public List<GrupoIndicador> getGrupoIndicadores() {
		return grupoIndicadores;
	}


	public void setGrupoIndicadores(List<GrupoIndicador> grupoIndicadores) {
		this.grupoIndicadores = grupoIndicadores;
	}


	public List<IndicadorSerie> getIndicadorSeries() {
		return indicadorSeries;
	}


	public void setIndicadorSeries(List<IndicadorSerie> indicadorSeries) {
		this.indicadorSeries = indicadorSeries;
	}


	public List<IndicadorSerieFiltro> getIndicadorSerieFiltros() {
		return indicadorSerieFiltros;
	}


	public void setIndicadorSerieFiltros(
			List<IndicadorSerieFiltro> indicadorSerieFiltros) {
		this.indicadorSerieFiltros = indicadorSerieFiltros;
	}


	public Filtro addFiltro(Filtro filtro) {
		getFiltros().add(filtro);
		filtro.setIndicador(this);

		return filtro;
	}

	public Filtro removeFiltro(Filtro filtro) {
		getFiltros().remove(filtro);
		filtro.setIndicador(null);

		return filtro;
	}

	
	public IndicadorSerie addIndicadorSery(IndicadorSerie indicadorSery) {
		getIndicadorSeries().add(indicadorSery);
		indicadorSery.setIndicador(this);

		return indicadorSery;
	}

	public IndicadorSerie removeIndicadorSery(IndicadorSerie indicadorSery) {
		getIndicadorSeries().remove(indicadorSery);
		indicadorSery.setIndicador(null);

		return indicadorSery;
	}

	



	public IndicadorSerieFiltro addIndicadorSerieFiltro(IndicadorSerieFiltro indicadorSerieFiltro) {
		getIndicadorSerieFiltros().add(indicadorSerieFiltro);
		indicadorSerieFiltro.setIndicador(this);

		return indicadorSerieFiltro;
	}

	public IndicadorSerieFiltro removeIndicadorSerieFiltro(IndicadorSerieFiltro indicadorSerieFiltro) {
		getIndicadorSerieFiltros().remove(indicadorSerieFiltro);
		indicadorSerieFiltro.setIndicador(null);

		return indicadorSerieFiltro;
	}

	
	public GrupoIndicador addGrupoIndicadore(GrupoIndicador grupoIndicadore) {
		getGrupoIndicadores().add(grupoIndicadore);
		grupoIndicadore.setIndicador(this);

		return grupoIndicadore;
	}

	public GrupoIndicador removeGrupoIndicadore(GrupoIndicador grupoIndicadore) {
		getGrupoIndicadores().remove(grupoIndicadore);
		grupoIndicadore.setIndicador(null);

		return grupoIndicadore;
	}





}
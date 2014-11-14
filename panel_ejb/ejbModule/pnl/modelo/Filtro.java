package pnl.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the FILTROS database table.
 * 
 */
@Entity
@Table(name="FILTROS")
@Cacheable(false)
@NamedQuery(name="Filtro.findAll", query="SELECT f FROM Filtro f")
public class Filtro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FILTROS_IDFILTRO_GENERATOR", sequenceName="SQ_FILTRO", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FILTROS_IDFILTRO_GENERATOR")
	@Column(name="ID_FILTRO")
	private long idFiltro;

	@Column(name="ANIVEL_INDICADOR")
	private String anivelIndicador;

	private String estado;

	@Column(name="INDICE_FILTRO")
	private BigDecimal indiceFiltro;

	private String nombre;

	@Column(name="VALOR_INICIAL")
	private String valorInicial;

	//bi-directional many-to-one association to Indicador
	@ManyToOne
	@JoinColumn(name="ID_INDICADOR")
	private Indicador indicador;

	//bi-directional many-to-one association to IndicadorSerieFiltro
	@OneToMany(mappedBy="filtro")
	private List<IndicadorSerieFiltro> indicadorSerieFiltros;

	public Filtro() {
	}

	public long getIdFiltro() {
		return this.idFiltro;
	}

	public void setIdFiltro(long idFiltro) {
		this.idFiltro = idFiltro;
	}

	public String getAnivelIndicador() {
		return this.anivelIndicador;
	}

	public void setAnivelIndicador(String anivelIndicador) {
		this.anivelIndicador = anivelIndicador;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public BigDecimal getIndiceFiltro() {
		return this.indiceFiltro;
	}

	public void setIndiceFiltro(BigDecimal indiceFiltro) {
		this.indiceFiltro = indiceFiltro;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getValorInicial() {
		return this.valorInicial;
	}

	public void setValorInicial(String valorInicial) {
		this.valorInicial = valorInicial;
	}

	public Indicador getIndicador() {
		return this.indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public List<IndicadorSerieFiltro> getIndicadorSerieFiltros() {
		return this.indicadorSerieFiltros;
	}

	public void setIndicadorSerieFiltros(List<IndicadorSerieFiltro> indicadorSerieFiltros) {
		this.indicadorSerieFiltros = indicadorSerieFiltros;
	}

	public IndicadorSerieFiltro addIndicadorSerieFiltro(IndicadorSerieFiltro indicadorSerieFiltro) {
		getIndicadorSerieFiltros().add(indicadorSerieFiltro);
		indicadorSerieFiltro.setFiltro(this);

		return indicadorSerieFiltro;
	}

	public IndicadorSerieFiltro removeIndicadorSerieFiltro(IndicadorSerieFiltro indicadorSerieFiltro) {
		getIndicadorSerieFiltros().remove(indicadorSerieFiltro);
		indicadorSerieFiltro.setFiltro(null);

		return indicadorSerieFiltro;
	}

}
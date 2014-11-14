package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the INDICADOR_SERIE_FILTROS database table.
 * 
 */
@Entity
@Table(name="INDICADOR_SERIE_FILTROS")
@Cacheable(false)
@NamedQuery(name="IndicadorSerieFiltro.findAll", query="SELECT i FROM IndicadorSerieFiltro i")
public class IndicadorSerieFiltro implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IndicadorSerieFiltroPK id;

	private String valor;
	
	@Transient
	private boolean actualizar;
	


	//bi-directional many-to-one association to Filtro
	@ManyToOne
	@JoinColumn(name="ID_FILTRO")
	private Filtro filtro;

	//bi-directional many-to-one association to Indicador
	@ManyToOne
	@JoinColumn(name="ID_INDICADOR")
	private Indicador indicador;

	//bi-directional many-to-one association to IndicadorSerie
	//@ManyToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="ID_SERIE")
	private IndicadorSerie indicadorSery;

	public IndicadorSerieFiltro() {
	}

	public IndicadorSerieFiltroPK getId() {
		return this.id;
	}

	public void setId(IndicadorSerieFiltroPK id) {
		this.id = id;
	}

	public String getValor() {
		return this.valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Filtro getFiltro() {
		return this.filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public Indicador getIndicador() {
		return this.indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public IndicadorSerie getIndicadorSery() {
		return this.indicadorSery;
	}

	public void setIndicadorSery(IndicadorSerie indicadorSery) {
		this.indicadorSery = indicadorSery;
	}

	public boolean isActualizar() {
		return actualizar;
	}

	public void setActualizar(boolean actualizar) {
		this.actualizar = actualizar;
	}


	

}
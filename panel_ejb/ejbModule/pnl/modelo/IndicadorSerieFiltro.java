package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Size;


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
	
	//bi-directional many-to-one association to Filtro
	//@ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="ID_FILTRO")
	private Filtro filtro;

	@Size(max = 2000)
	private String valor;
	
	

	//bi-directional many-to-one association to Indicador
	@ManyToOne
	@JoinColumn(name="ID_INDICADOR")
	private Indicador indicador;

	//bi-directional many-to-one association to IndicadorSerie
	@JoinColumn(name="ID_SERIE")
	private IndicadorSerie indicadorSery;

	public IndicadorSerieFiltro() {
	}

	public IndicadorSerieFiltroPK getId() {
		return id;
	}

	public void setId(IndicadorSerieFiltroPK id) {
		this.id = id;
	}

	public Filtro getFiltro() {
		return filtro;
	}

	public void setFiltro(Filtro filtro) {
		this.filtro = filtro;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}



	public Indicador getIndicador() {
		return indicador;
	}

	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}

	public IndicadorSerie getIndicadorSery() {
		return indicadorSery;
	}

	public void setIndicadorSery(IndicadorSerie indicadorSery) {
		this.indicadorSery = indicadorSery;
	}



	

}
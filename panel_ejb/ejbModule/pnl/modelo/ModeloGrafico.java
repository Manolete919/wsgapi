package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the MODELO_GRAFICO database table.
 * 
 */
@Entity
@Table(name="MODELO_GRAFICO")
@Cacheable(false)
@NamedQuery(name="ModeloGrafico.findAll", query="SELECT m FROM ModeloGrafico m")
public class ModeloGrafico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_MODELO")
	private long idModelo;

	private String nombre;

	//bi-directional many-to-one association to Indicador
	@OneToMany(mappedBy="modeloGrafico")
	private List<Indicador> indicadors;

	public ModeloGrafico() {
	}

	public long getIdModelo() {
		return this.idModelo;
	}

	public void setIdModelo(long idModelo) {
		this.idModelo = idModelo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Indicador> getIndicadors() {
		return this.indicadors;
	}

	public void setIndicadors(List<Indicador> indicadors) {
		this.indicadors = indicadors;
	}

	public Indicador addIndicador(Indicador indicador) {
		getIndicadors().add(indicador);
		indicador.setModeloGrafico(this);

		return indicador;
	}

	public Indicador removeIndicador(Indicador indicador) {
		getIndicadors().remove(indicador);
		indicador.setModeloGrafico(null);

		return indicador;
	}

}
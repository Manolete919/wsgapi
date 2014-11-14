package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the INDICADOR_SERIES database table.
 * 
 */
@Entity
@Table(name="INDICADOR_SERIES")
@Cacheable(false)
@NamedQuery(name="IndicadorSerie.findAll", query="SELECT i FROM IndicadorSerie i")
public class IndicadorSerie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="INDICADOR_SERIES_IDSERIE_GENERATOR", sequenceName="SQ_INDICADOR_SERIE",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INDICADOR_SERIES_IDSERIE_GENERATOR")
	@Column(name="ID_SERIE")
	private long idSerie;

	private String estado;
	
	
	
	
	@Transient
	private boolean estadoBoolean;

	private String nombre;

	//bi-directional many-to-one association to Indicador
	@JoinColumn(name="ID_INDICADOR")
	private Indicador indicador;

	//bi-directional many-to-one association to IndicadorSerieFiltro
	@OneToMany(mappedBy="indicadorSery",cascade=CascadeType.REMOVE)
	private List<IndicadorSerieFiltro> indicadorSerieFiltros;

	public IndicadorSerie() {
	}

	public long getIdSerie() {
		return this.idSerie;
	}

	public void setIdSerie(long idSerie) {
		this.idSerie = idSerie;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
		indicadorSerieFiltro.setIndicadorSery(this);

		return indicadorSerieFiltro;
	}

	public IndicadorSerieFiltro removeIndicadorSerieFiltro(IndicadorSerieFiltro indicadorSerieFiltro) {
		getIndicadorSerieFiltros().remove(indicadorSerieFiltro);
		indicadorSerieFiltro.setIndicadorSery(null);

		return indicadorSerieFiltro;
	}

	public boolean isEstadoBoolean() {
		
		estadoBoolean = false;
		
		if(this.estado != null){
			estadoBoolean = (this.estado).equals("A")?true:false;
		}
		
				
		return estadoBoolean;
	}

	public void setEstadoBoolean(boolean estadoBoolean) {
		
		
		this.estado = (estadoBoolean)?"A":"I";
		
		this.estadoBoolean = estadoBoolean;
		
		
	}
	
	
	

}
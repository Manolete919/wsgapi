package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
	@SequenceGenerator(name="INDICADOR_SERIES_IDSERIE_GENERATOR", sequenceName="SQ_INDICADOR_SERIE",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="INDICADOR_SERIES_IDSERIE_GENERATOR")
	@Column(name="ID_SERIE")
	private long idSerie;
	

	@Size(max = 200)
	private String nombre;
	
	@Size(max = 1)
	private String estado;
	
	@Transient
	private String estadoVisual;
	
	
	
	@Transient
	private boolean estadoBoolean;

	
	//bi-directional many-to-one association to Indicador
	@JoinColumn(name="ID_INDICADOR")
	private Indicador indicador;


	//bi-directional many-to-one association to IndicadorSerieFiltro
	@OneToMany(mappedBy="indicadorSery",cascade={CascadeType.REMOVE,CascadeType.PERSIST})
	private List<IndicadorSerieFiltro> indicadorSerieFiltros;

	public IndicadorSerie() {
	}


	public long getIdSerie() {
		return idSerie;
	}


	public void setIdSerie(long idSerie) {
		this.idSerie = idSerie;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public Indicador getIndicador() {
		return indicador;
	}


	public void setIndicador(Indicador indicador) {
		this.indicador = indicador;
	}


	public List<IndicadorSerieFiltro> getIndicadorSerieFiltros() {
		return indicadorSerieFiltros;
	}


	public void setIndicadorSerieFiltros(
			List<IndicadorSerieFiltro> indicadorSerieFiltros) {
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


	public String getEstadoVisual() {
		if(this.getEstado()==null){
			estadoVisual = "Inactivo";
		}else{
			if(this.getEstado().equals("A")){
				estadoVisual = "Activo";
			}else{
				estadoVisual = "Inactivo";
			}
		}
		return estadoVisual;
	}
	
	
	
	
	

}
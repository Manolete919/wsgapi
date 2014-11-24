package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

/**
 * The persistent class for the FILTROS database table.
 * 
 */
@Entity
@Table(name = "FILTROS")
@Cacheable(false)
@NamedQuery(name = "Filtro.findAll", query = "SELECT f FROM Filtro f")
public class Filtro implements Serializable {
	private static final long serialVersionUID = 1L;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
	@SequenceGenerator(name = "FILTROS_IDFILTRO_GENERATOR", sequenceName = "SQ_FILTRO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILTROS_IDFILTRO_GENERATOR")
	@Column(name = "ID_FILTRO")
	private long idFiltro;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
	private String nombre;
    
    @Basic(optional = false)
    @NotNull
	@Column(name = "INDICE_FILTRO")
	private BigDecimal indiceFiltro;


    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
	@Column(name = "ANIVEL_INDICADOR")
	private String anivelIndicador;
    
    @Transient
    private String anivelVisual;

    @Size(max = 1)
    @Column(name = "ESTADO")
	private String estado;
    
    
    @Transient
    private String estadoVisual;
    

    @Size(max = 2000)
	@Column(name = "VALOR_INICIAL")
	private String valorInicial;
	
	// bi-directional many-to-one association to Indicador
    @ManyToOne(optional = false)
	@JoinColumn(name = "ID_INDICADOR")
	private Indicador indicador;

	// bi-directional many-to-one association to IndicadorSerieFiltro
	@OneToMany(mappedBy = "filtro")
	private List<IndicadorSerieFiltro> indicadorSerieFiltros;

	public Filtro() {
	}

	
	
	


	public long getIdFiltro() {
		return idFiltro;
	}






	public void setIdFiltro(long idFiltro) {
		this.idFiltro = idFiltro;
	}






	public String getNombre() {
		return nombre;
	}






	public void setNombre(String nombre) {
		this.nombre = nombre;
	}






	public BigDecimal getIndiceFiltro() {
		return indiceFiltro;
	}






	public void setIndiceFiltro(BigDecimal indiceFiltro) {
		this.indiceFiltro = indiceFiltro;
	}






	public String getAnivelIndicador() {
		return anivelIndicador;
	}






	public void setAnivelIndicador(String anivelIndicador) {
		this.anivelIndicador = anivelIndicador;
	}






	public String getEstado() {
		return estado;
	}






	public void setEstado(String estado) {
		this.estado = estado;
	}






	public String getValorInicial() {
		return valorInicial;
	}






	public void setValorInicial(String valorInicial) {
		this.valorInicial = valorInicial;
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






	public IndicadorSerieFiltro addIndicadorSerieFiltro(
			IndicadorSerieFiltro indicadorSerieFiltro) {
		getIndicadorSerieFiltros().add(indicadorSerieFiltro);
		indicadorSerieFiltro.setFiltro(this);

		return indicadorSerieFiltro;
	}

	public IndicadorSerieFiltro removeIndicadorSerieFiltro(
			IndicadorSerieFiltro indicadorSerieFiltro) {
		getIndicadorSerieFiltros().remove(indicadorSerieFiltro);
		indicadorSerieFiltro.setFiltro(null);

		return indicadorSerieFiltro;
	}






	public String getAnivelVisual() {
		
		if(this.getAnivelIndicador()==null){
			anivelVisual = "Serie";
		}else{
			if(this.getAnivelIndicador().equals("S")){
				anivelVisual = "Indicador";
			}else{
				anivelVisual = "Serie";
			}
		}
		
		return anivelVisual;
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
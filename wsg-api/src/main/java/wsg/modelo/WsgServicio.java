package wsg.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the WSG_SERVICIO database table.
 * 
 */
@Entity
@Table(name="WSG_SERVICIO")
@Cacheable(false)
@NamedQuery(name="WsgServicio.findAll", query="SELECT w FROM WsgServicio w")
public class WsgServicio implements Serializable {
	private static final long serialVersionUID = 1L;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
	@Column(name="ID_SERVICIO")
	private long idServicio;
    
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_DESDE")
	private Date fechaDesde;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="FECHA_HASTA")
	private Date fechaHasta;

    @Size(max = 200)
	private String descripcion;

    @Size(max = 1)
	private String estado;



	//bi-directional many-to-one association to WsgJndi
	@ManyToOne
	@JoinColumn(name="ID_JNDI")
	private WsgJndi wsgJndi;
	
	//bi-directional many-to-one association to WsgCategoria
	@ManyToOne
	@JoinColumn(name="ID_CATEGORIA")
	private WsgCategoria wsgCategoria;

	//bi-directional many-to-one association to WsgQuery
	@ManyToOne
	@JoinColumn(name="ID_QUERY")
	private WsgQuery wsgQuery;

	//bi-directional many-to-one association to WsgUsuarioServicio
	@OneToMany(mappedBy="wsgServicio")
	private List<WsgUsuarioServicio> wsgUsuarioServicios;

	public WsgServicio() {
	}

	public long getIdServicio() {
		return this.idServicio;
	}

	public void setIdServicio(long idServicio) {
		this.idServicio = idServicio;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaDesde() {
		return this.fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return this.fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public WsgJndi getWsgJndi() {
		return this.wsgJndi;
	}

	public void setWsgJndi(WsgJndi wsgJndi) {
		this.wsgJndi = wsgJndi;
	}

	public WsgQuery getWsgQuery() {
		return this.wsgQuery;
	}

	public void setWsgQuery(WsgQuery wsgQuery) {
		this.wsgQuery = wsgQuery;
	}

	public List<WsgUsuarioServicio> getWsgUsuarioServicios() {
		return this.wsgUsuarioServicios;
	}

	public void setWsgUsuarioServicios(List<WsgUsuarioServicio> wsgUsuarioServicios) {
		this.wsgUsuarioServicios = wsgUsuarioServicios;
	}

	public WsgUsuarioServicio addWsgUsuarioServicio(WsgUsuarioServicio wsgUsuarioServicio) {
		getWsgUsuarioServicios().add(wsgUsuarioServicio);
		wsgUsuarioServicio.setWsgServicio(this);

		return wsgUsuarioServicio;
	}

	public WsgUsuarioServicio removeWsgUsuarioServicio(WsgUsuarioServicio wsgUsuarioServicio) {
		getWsgUsuarioServicios().remove(wsgUsuarioServicio);
		wsgUsuarioServicio.setWsgServicio(null);

		return wsgUsuarioServicio;
	}

	public WsgCategoria getWsgCategoria() {
		return wsgCategoria;
	}

	public void setWsgCategoria(WsgCategoria wsgCategoria) {
		this.wsgCategoria = wsgCategoria;
	}

}
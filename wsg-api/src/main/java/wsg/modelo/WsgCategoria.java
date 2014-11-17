package wsg.modelo;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;


/**
 * The persistent class for the WSG_CATEGORIA database table.
 * 
 */
@Entity
@Table(name="WSG_CATEGORIA")
@NamedQuery(name="WsgCategoria.findAll", query="SELECT w FROM WsgCategoria w")
public class WsgCategoria implements Serializable {
	private static final long serialVersionUID = 1L;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
	@Column(name="ID_CATEGORIA")
	private long idCategoria;
    
    @Size(max = 200)
	private String descripcion;

	//bi-directional many-to-one association to WsgServicio
	@OneToMany(mappedBy="wsgCategoria")
	private List<WsgServicio> wsgServicios;

	public WsgCategoria() {
	}

	public long getIdCategoria() {
		return this.idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<WsgServicio> getWsgServicios() {
		return this.wsgServicios;
	}

	public void setWsgServicios(List<WsgServicio> wsgServicios) {
		this.wsgServicios = wsgServicios;
	}

	public WsgServicio addWsgServicio(WsgServicio wsgServicio) {
		getWsgServicios().add(wsgServicio);
		wsgServicio.setWsgCategoria(this);

		return wsgServicio;
	}

	public WsgServicio removeWsgServicio(WsgServicio wsgServicio) {
		getWsgServicios().remove(wsgServicio);
		wsgServicio.setWsgCategoria(null);

		return wsgServicio;
	}

}
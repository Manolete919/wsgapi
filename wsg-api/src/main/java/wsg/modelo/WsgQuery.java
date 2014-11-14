package wsg.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the WSG_QUERY database table.
 * 
 */
@Entity
@Table(name="WSG_QUERY")
@Cacheable(false)
@NamedQuery(name="WsgQuery.findAll", query="SELECT w FROM WsgQuery w")
public class WsgQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_QUERY")
	private long idQuery;

	private String descripcion;

	@Lob
	private String query;

	//bi-directional many-to-one association to WsgServicio
	@OneToMany(mappedBy="wsgQuery")
	private List<WsgServicio> wsgServicios;

	public WsgQuery() {
	}

	public long getIdQuery() {
		return this.idQuery;
	}

	public void setIdQuery(long idQuery) {
		this.idQuery = idQuery;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getQuery() {
		return this.query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<WsgServicio> getWsgServicios() {
		return this.wsgServicios;
	}

	public void setWsgServicios(List<WsgServicio> wsgServicios) {
		this.wsgServicios = wsgServicios;
	}

	public WsgServicio addWsgServicio(WsgServicio wsgServicio) {
		getWsgServicios().add(wsgServicio);
		wsgServicio.setWsgQuery(this);

		return wsgServicio;
	}

	public WsgServicio removeWsgServicio(WsgServicio wsgServicio) {
		getWsgServicios().remove(wsgServicio);
		wsgServicio.setWsgQuery(null);

		return wsgServicio;
	}

}
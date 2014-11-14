package wsg.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the WSG_JNDI database table.
 * 
 */
@Entity
@Table(name="WSG_JNDI")
@Cacheable(false)
@NamedQuery(name="WsgJndi.findAll", query="SELECT w FROM WsgJndi w")
public class WsgJndi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_JNDI")
	private long idJndi;

	private String descripcion;

	private String jndi;

	//bi-directional many-to-one association to WsgServicio
	@OneToMany(mappedBy="wsgJndi")
	private List<WsgServicio> wsgServicios;

	public WsgJndi() {
	}

	public long getIdJndi() {
		return this.idJndi;
	}

	public void setIdJndi(long idJndi) {
		this.idJndi = idJndi;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getJndi() {
		return this.jndi;
	}

	public void setJndi(String jndi) {
		this.jndi = jndi;
	}

	public List<WsgServicio> getWsgServicios() {
		return this.wsgServicios;
	}

	public void setWsgServicios(List<WsgServicio> wsgServicios) {
		this.wsgServicios = wsgServicios;
	}

	public WsgServicio addWsgServicio(WsgServicio wsgServicio) {
		getWsgServicios().add(wsgServicio);
		wsgServicio.setWsgJndi(this);

		return wsgServicio;
	}

	public WsgServicio removeWsgServicio(WsgServicio wsgServicio) {
		getWsgServicios().remove(wsgServicio);
		wsgServicio.setWsgJndi(null);

		return wsgServicio;
	}

}
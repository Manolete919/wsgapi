package pnl.webservice.integracion;

import java.io.Serializable;
import java.util.Date;



public class WsgServicio implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private long idServicio;

	private String descripcion;

	private String estado;


	private Date fechaDesde;


	private Date fechaHasta;



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



}
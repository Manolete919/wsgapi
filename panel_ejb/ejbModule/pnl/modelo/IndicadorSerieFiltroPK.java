package pnl.modelo;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The primary key class for the INDICADOR_SERIE_FILTROS database table.
 * 
 */
@Embeddable
public class IndicadorSerieFiltroPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	

	@Column(name="ID_INDICADOR", insertable=false, updatable=false)
	private long idIndicador;


	@Column(name="ID_SERIE", insertable=false, updatable=false)
	private long idSerie;
	

	@Column(name="ID_FILTRO", insertable=false, updatable=false)
	private long idFiltro;

	public IndicadorSerieFiltroPK() {
	}
	public long getIdIndicador() {
		return this.idIndicador;
	}
	public void setIdIndicador(long idIndicador) {
		this.idIndicador = idIndicador;
	}
	public long getIdSerie() {
		return this.idSerie;
	}
	public void setIdSerie(long idSerie) {
		this.idSerie = idSerie;
	}
	public long getIdFiltro() {
		return this.idFiltro;
	}
	public void setIdFiltro(long idFiltro) {
		this.idFiltro = idFiltro;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IndicadorSerieFiltroPK)) {
			return false;
		}
		IndicadorSerieFiltroPK castOther = (IndicadorSerieFiltroPK)other;
		return 
			(this.idIndicador == castOther.idIndicador)
			&& (this.idSerie == castOther.idSerie)
			&& (this.idFiltro == castOther.idFiltro);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idIndicador ^ (this.idIndicador >>> 32)));
		hash = hash * prime + ((int) (this.idSerie ^ (this.idSerie >>> 32)));
		hash = hash * prime + ((int) (this.idFiltro ^ (this.idFiltro >>> 32)));
		
		return hash;
	}
}
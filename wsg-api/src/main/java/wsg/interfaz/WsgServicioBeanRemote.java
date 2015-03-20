package wsg.interfaz;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import wsg.modelo.WsgServicio;

@Remote
public interface WsgServicioBeanRemote extends Serializable {
	
	void create(WsgServicio wsgServicio);

	void edit(WsgServicio wsgServicio);

	void remove(WsgServicio wsgServicio);

	public WsgServicio find(Object id);

	public List<WsgServicio> findAll();

	public List<WsgServicio> findRange(int[] range);

	int count();
	
	public WsgServicio buscarServicioPorIdActivoYVigente(long idServicio) throws Exception;
	
}

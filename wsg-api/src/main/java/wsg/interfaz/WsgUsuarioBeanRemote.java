package wsg.interfaz;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import wsg.modelo.WsgUsuario;



@Remote
public interface WsgUsuarioBeanRemote extends Serializable {
	
	void create(WsgUsuario wsgUsuario);

	void edit(WsgUsuario wsgUsuario);

	void remove(WsgUsuario wsgUsuario);

	public WsgUsuario find(Object id);
	
    public List<WsgUsuario> findAll();

    public List<WsgUsuario> findRange(int[] range);

    int count();

}

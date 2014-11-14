package wsg.interfaz;

import java.util.List;
import javax.ejb.Remote;
import wsg.modelo.WsgUsuarioServicio;

@Remote
public interface WsgUsuarioServicioBeanRemote {
	
	void create(WsgUsuarioServicio wsgUsuarioServicio);

	void edit(WsgUsuarioServicio wsgUsuarioServicio);

	void remove(WsgUsuarioServicio wsgUsuarioServicio);

	public WsgUsuarioServicio find(Object id);
	
    public List<WsgUsuarioServicio> findAll();

    public List<WsgUsuarioServicio> findRange(int[] range);

    int count();
    
}

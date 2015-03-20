package wsg.interfaz;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import wsg.modelo.WsgJndi;



@Remote
public interface WsgJndiBeanRemote extends Serializable {
	
	void create(WsgJndi wsgJndi);

	void edit(WsgJndi wsgJndi);

	void remove(WsgJndi wsgJndi);

	public WsgJndi find(Object id);
	
    public List<WsgJndi> findAll();

    public List<WsgJndi> findRange(int[] range);

    int count();
    
}

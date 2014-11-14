package wsg.interfaz;

import java.util.List;

import javax.ejb.Remote;

import wsg.modelo.WsgJndi;



@Remote
public interface WsgJndiBeanRemote {
	
	void create(WsgJndi wsgJndi);

	void edit(WsgJndi wsgJndi);

	void remove(WsgJndi wsgJndi);

	public WsgJndi find(Object id);
	
    public List<WsgJndi> findAll();

    public List<WsgJndi> findRange(int[] range);

    int count();
    
}

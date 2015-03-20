package wsg.interfaz;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import wsg.modelo.WsgQuery;



@Remote
public interface WsgQueryBeanRemote extends Serializable {
	
	void create(WsgQuery wsgQuery);

	void edit(WsgQuery wsgQuery);

	void remove(WsgQuery wsgQuery);

	public WsgQuery find(Object id);
	
    public List<WsgQuery> findAll();

    public List<WsgQuery> findRange(int[] range);

    int count();

}

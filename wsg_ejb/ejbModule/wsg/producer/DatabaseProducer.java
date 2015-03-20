package wsg.producer;




import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
//import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;



@RequestScoped 
public class DatabaseProducer {
	
	
    @PersistenceContext(unitName = "oraclePU")
    private EntityManager oraclePU;
    
    @PersistenceContext(unitName = "mySQlPU")
    private EntityManager mySQLPU;
    
    private Properties propiedades = new Properties();
    
 	//no importa el nombre del metodo, solo busca el produce
    @Produces 
    @RequestScoped    
	public EntityManager getEntityManager()
	{
		
		InputStream iostream =
		Thread.currentThread().getContextClassLoader().getResourceAsStream("properties/wsg-ejb-ear.properties");
		try {
			propiedades.load(iostream);
			if(propiedades.getProperty("wsgejb.pu").equals("oraclePU")){
				return oraclePU;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mySQLPU; 
		
		
	} 

 

	

	
	
}

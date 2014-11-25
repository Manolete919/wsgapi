package wsg.conexion;


import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.apache.log4j.Logger;




public class Conexion implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Properties propiedades = new Properties();
	public Conexion(){
	}
	

	
	
	public static Properties getPropiedades() {
		InputStream iostream =
		Thread.currentThread().getContextClassLoader().getResourceAsStream("properties/wsg-war-ear.properties");
		try {
			propiedades.load(iostream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propiedades;
	} 

	
	static final Logger logger = Logger.getLogger(Conexion.class); 

	private static DataSource dataSource = null;
	private static Context context = null;


	public static DataSource obtenerFuenteDeDatos(String jndi) throws Exception {

		if (context == null) {
			context = new InitialContext();
		}

		dataSource = (DataSource) context.lookup(jndi);
		logger.info("EXITO");
		
		return dataSource;
	}



}













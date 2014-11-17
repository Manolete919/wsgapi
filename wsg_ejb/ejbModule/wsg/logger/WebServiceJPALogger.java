package wsg.logger;
import java.io.InputStream;
import java.io.Serializable;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;




import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.PropertyConfigurator;



@Singleton
@LocalBean
public class WebServiceJPALogger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static WebServiceJPALogger instance = null;
	private static Object mutex = new Object();

	public WebServiceJPALogger() {

	}

	public static WebServiceJPALogger getInstance() {
		if (instance == null) {
			synchronized (mutex) {
				if (instance == null)

					instance = new WebServiceJPALogger();
			}
		}
		return instance;
	}

	
	
	public  void setup() {
					
		/*	URL url = EisJPALogger.class.getClassLoader().getResource(
					"resource/log4j.properties");*/

			
				PropertiesConfiguration config = new PropertiesConfiguration();
				try {
					//config = new PropertiesConfiguration(url);
					InputStream iostream =
					Thread.currentThread().getContextClassLoader().getResourceAsStream("properties/log4j.properties");							
					config.load(iostream);
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				PropertyConfigurator.configure(ConfigurationConverter
						.getProperties(config));
		
		
	}
	
}
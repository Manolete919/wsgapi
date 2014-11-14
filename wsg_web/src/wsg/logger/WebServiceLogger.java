package wsg.logger;
import java.io.InputStream;
//import java.net.URL;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.PropertyConfigurator;



@Singleton
@LocalBean
public class WebServiceLogger {

	private static WebServiceLogger instance = null;
	private static Object mutex = new Object();

	public WebServiceLogger() {

	}

	public static WebServiceLogger getInstance() {
		if (instance == null) {
			synchronized (mutex) {
				if (instance == null)

					instance = new WebServiceLogger();
			}
		}
		return instance;
	}

	
	
	public  void setup() {
					
			/*URL url = WebServiceLogger.class.getClassLoader().getResource(
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
				PropertyConfigurator.configure(ConfigurationConverter.getProperties(config));
		
		
	}
	
}
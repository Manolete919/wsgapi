package wsg.logger;
import java.io.InputStream;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
//import java.net.URL;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



@Singleton
@Startup
public class WebServiceLogger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger;

	public WebServiceLogger() {

	}

	@PostConstruct
	public void initialize() {

		inicializarLog4j();

		logger.info("***********************************************************************************");
		logger.info("*                    A p l i c a c i ó n   I n i c i a d a                        *");
		logger.info("***********************************************************************************");

	}

	public void inicializarLog4j() {

		/*
		 * URL url = EisLogger.class.getClassLoader().getResource(
		 * "resource/log4j.properties");
		 */

		PropertiesConfiguration config = new PropertiesConfiguration();
		try {
			// config = new PropertiesConfiguration(url);

			InputStream iostream = Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream("properties/log4j.properties");

			config.load(iostream);

		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PropertyConfigurator.configure(ConfigurationConverter
				.getProperties(config));

		logger = Logger.getLogger(WebServiceLogger.class);

	}

	@PreDestroy
	public void terminate() {
		logger.info("***********************************************************************************");
		logger.info("*                   D e t e n i e n d o   A p l i c a c i ó n                     *");
		logger.info("***********************************************************************************");
	}


	
}
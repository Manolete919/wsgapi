package pnl.logger;
import java.io.Serializable;

import java.net.URL;


import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.PropertyConfigurator;



public class PanelLogger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelLogger() {

	}


	
	
	public static  void setup() {
					
			
				//try {
					
					//URL url = PanelLogger.class.getClassLoader().getResource("resources/log4j.properties");
					//PropertyConfigurator.configure("log4j.properties");
					//PropertiesConfiguration config = new PropertiesConfiguration(url);
					//PropertyConfigurator.configure(ConfigurationConverter.getProperties(config));
					
					
				//} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				//}
				
		
		
	}
	
}
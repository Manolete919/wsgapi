package wsg.query;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.w3c.dom.Document;

import wsg.modelo.WsgServicio;
import wsg.qualificadores.AuditorGeneralWeb;
import wsg.resultset.Transformador;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



@Named
@RequestScoped
@AuditorGeneralWeb
public class EjecutaQuery implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject Transformador converter;


	Properties propiedades = new Properties();

	public EjecutaQuery() {
	}

	public Properties getPropiedades() {
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



	public String getResultadoTotal() {
		return resultadoTotal;
	}






	String message = "";
	static final Logger logger = LogManager.getLogger(EjecutaQuery.class);
	
	
	String resultadoTotal = null;


	public void setResultadoTotal(ResultSet rs)
			throws Exception {


		resultadoTotal = converter.resultadoConsumidoEnJSONArrayString(rs);
		
		
	}


	public Document obtenerServicioEnDocument(Connection conn,
			WsgServicio wsgServicio,
			List<String> parametros) throws Exception {

		//no olvidar cerrar el objeto connection desde la llamada
		
		PreparedStatement pstmt = null;
		int mtadtacnt;
		ParameterMetaData pmtadta;
		
			

		String sentenciaSQL = wsgServicio.getWsgQuery().getQuery();
		

		
		Document document = null;
		

		pstmt = conn.prepareStatement(sentenciaSQL,
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		pmtadta = pstmt.getParameterMetaData();
		mtadtacnt = pmtadta.getParameterCount();

		

		// SETEA LOS BINDINGS
		int cont = 1;

		for (String parametro : parametros) {
			if (cont > mtadtacnt) {
				break;
			}
			pstmt.setString(cont, parametro);
			cont++;
		}

		ResultSet rs = pstmt.executeQuery();

	
		document = converter.convertirResultSetEnDocument(rs);			
		setResultadoTotal(rs);


		pstmt.close(); // close connection
	

		return document;
	}



	
}

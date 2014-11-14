package wsg.query;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.w3c.dom.Document;

import wsg.modelo.WsgServicio;
import wsg.resultset.Transformador;

import javax.ejb.Stateless;

@Stateless
public class EjecutaQuery {
	
	//Properties propiedades = new Properties();

	public EjecutaQuery() {
		//EisLogger.getInstance().setup();		
	}

	/*public Properties getPropiedades() {
		InputStream iostream =
		Thread.currentThread().getContextClassLoader().getResourceAsStream("properties/jeis-war-ear.properties");
		try {
			propiedades.load(iostream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return propiedades;
	} */



	public String getResultadoTotal() {
		return resultadoTotal;
	}





	Transformador converter = null;
	String message = "";
	//static final Logger logger = Logger.getLogger(QueryEjecutor.class);
	
	
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
		
		converter = new Transformador();
		
		

		String sentenciaSQL = wsgServicio.getWsgQuery().getQuery();
		

		
		Document document = null;
		

		pstmt = conn.prepareStatement(sentenciaSQL,
				ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		pmtadta = pstmt.getParameterMetaData();
		mtadtacnt = pmtadta.getParameterCount();

		System.out.println(" cantidad " + mtadtacnt);

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

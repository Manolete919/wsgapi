package wsg.resultset;

import java.sql.ResultSet;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.w3c.dom.Document;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;


@Stateless
@LocalBean
public class Transformador {
	
	//Properties propiedades = new Properties();

	
	
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
	}*/



	
	
	public String resultadoConsumidoEnJSONArrayString(ResultSet rs)
			throws Exception {
		
		
		if (rs.last()) {			
			rs.beforeFirst(); // not rs.first() because the rs.next() below will
								// move on, missing the first element
		}
		

		JSONArray json = new JSONArray(); // JSON array that will be returned

		// we will need the column names, this will save the table meta-data
		// like column name.
		java.sql.ResultSetMetaData rsmd = rs.getMetaData();

		// loop through the ResultSet
		while (rs.next()) {

			// figure out how many columns there are
			int numColumns = rsmd.getColumnCount();
			// each row in the ResultSet will be converted to a JSON Object
			JSONObject obj = new JSONObject();

			// loop through all the columns and place them into the JSON
			// Object
			for (int i = 1; i < numColumns + 1; i++) {

				String column_name = rsmd.getColumnName(i);

				// read only once
				String resultadoColumna = rs.getString(column_name);

				if (resultadoColumna == null) {
					obj.put(column_name, "");
				} else {
					obj.put(column_name, resultadoColumna);
				}

			}// end foreach

			json.put(obj);

		}// end while
	
		return json.toString(); // return JSON array
	}

	public Document convertirResultSetEnDocument(ResultSet rs) throws Exception {

		

		Document document = null;
		Boolean devolverNull = true;
		
	
		

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);

		
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.newDocument();
		

		Element root = null;


		root = document.createElementNS(
					"http://axis/EISApiOnlineWS.wsdl/types/", "ns0:"
							+ "ROWSET");
		
		document.appendChild(root);

		
		// we will need the column names, this will save the table meta-data
		// like column name.
		java.sql.ResultSetMetaData rsmd = rs.getMetaData();

		// loop through the ResultSet
		while (rs.next()) {

			devolverNull = false;

			// figure out how many columns there are
			int numColumns = rsmd.getColumnCount();
			// each row in the ResultSet will be converted to an Elemet Object
			// create contact element
			Element rowNode = null;

	
			rowNode = document.createElementNS(
					"http://axis/EISApiOnlineWS.wsdl/types/", "ns0:"
							+ "ROW");
			
			

			
			for (int i = 1; i < numColumns + 1; i++) {

				String column_name = null;
				String resultadoColumna = null;
			
				column_name = rsmd.getColumnName(i);
				// solo hay que leer una vez
				resultadoColumna = rs.getString(column_name);
		

				// Element tagName = document.createElement(column_name);
				Element tagName = null;

				tagName = document.createElementNS(
						"http://axis/EISApiOnlineWS.wsdl/types/",
						"ns0:" + column_name);
			


				tagName.appendChild(document
						.createTextNode(resultadoColumna));
				

				rowNode.appendChild(tagName);

			}// end foreach

			root.appendChild(rowNode);

		}// end while

		if(devolverNull){
			return null;
		}

		return document;
	}

}

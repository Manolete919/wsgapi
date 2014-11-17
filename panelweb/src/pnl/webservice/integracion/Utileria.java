package pnl.webservice.integracion;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pnl.filtro.dinamico.FiltroValorDefault;



public class Utileria implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public Document convertirFiltroValorEnDocument(List<FiltroValorDefault> filtroValores) throws Exception {

		

		Document document = null;
		
		String aliasListaDeRegistros = "raiz";
	
		String aliasRegistro = "sentencia";
		

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);

		
		DocumentBuilder builder = factory.newDocumentBuilder();
		document = builder.newDocument();
		

		Element root = null;


		root = document.createElementNS(
					"http://axis/EISApiOnlineWS.wsdl/types/", "typ:"
							+ aliasListaDeRegistros);
	

		document.appendChild(root);

		

		Element rowNode = null;


		rowNode = document.createElementNS(
					"http://axis/EISApiOnlineWS.wsdl/types/", "typ:"
							+ aliasRegistro);
		
		
		int i = 0;
		for(FiltroValorDefault filtroValor : filtroValores){
			
			Element tagName = null;
			
			tagName = document.createElementNS(
						"http://axis/EISApiOnlineWS.wsdl/types/",
						"typ:" + "TAG"+i);
			

			tagName.appendChild(document.createTextNode(filtroValor.getValor()));
			

			rowNode.appendChild(tagName);
			
	
			i++;
		}
		


		root.appendChild(rowNode);



		

		return document;
	}
	
	public static String convertirDocumentToString(Document xml) {

		if (xml == null) {
			return "";
		}

		TransformerFactory transfac;
		Transformer trans;
		StringWriter sw;
		StreamResult result;
		DOMSource source;
		String xmlString = null;
		try {
			transfac = TransformerFactory.newInstance();
			trans = transfac.newTransformer();
			trans.setOutputProperty(OutputKeys.METHOD, "xml");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty(OutputKeys.STANDALONE, "yes");
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); // omite
																				// el
																				// encoding

			sw = new StringWriter();
			result = new StreamResult(sw);
			source = new DOMSource(xml.getDocumentElement());
			trans.transform(source, result);
			xmlString = sw.toString();
		} catch (Exception e) {
			xmlString = "Error al convertir la respuesta document to String mensaje:"
					+ e.getMessage()
					+ " localizacion:"
					+ e.getLocalizedMessage();
		} finally {

		}

		return xmlString;
	}
	
	/*private static MessageElement[] convertXMLStringtoMessageElement(
			String xmlString) throws SAXException, IOException,
			ParserConfigurationException {
		MessageElement[] m = new MessageElement[1];
		Document XMLDoc = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder()
				.parse(new InputSource(new StringReader(xmlString)));
		Element element = XMLDoc.getDocumentElement();
		m[0] = new MessageElement(element);
		return m;
	}*/
	
	
	
}

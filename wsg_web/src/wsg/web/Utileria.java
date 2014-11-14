package wsg.web;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.InputSource;

public class Utileria {

	public Document eliminaNodosDeTextosVaciosDelDocumento(Document doc)
			throws Exception {

		XPathFactory xpathFactory = XPathFactory.newInstance();
		// XPath to find empty text nodes.
		XPathExpression xpathExp = xpathFactory.newXPath().compile(
				"//text()[normalize-space(.) = '']");
		NodeList emptyTextNodes = (NodeList) xpathExp.evaluate(doc,
				XPathConstants.NODESET);
		// Remove each empty text node from document.
		for (int i = 0; i < emptyTextNodes.getLength(); i++) {
			Node emptyTextNode = emptyTextNodes.item(i);
			emptyTextNode.getParentNode().removeChild(emptyTextNode);
		}

		return doc;
	}

	public static String documentToString(Document document) {

		DOMImplementationLS domImplementationLS = (DOMImplementationLS) document
				.getImplementation();
		LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
		String string = lsSerializer.writeToString(document);
		return string;

	}

	public String convertirDocumentToString(Document xml) throws Exception {

		if (xml == null) {
			return "";
		}

		TransformerFactory transfac;
		Transformer trans;
		StringWriter sw;
		StreamResult result;
		DOMSource source;
		String xmlString = null;

		transfac = TransformerFactory.newInstance();
		trans = transfac.newTransformer();
		trans.setOutputProperty(OutputKeys.METHOD, "xml");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");
		trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
				"5");
		trans.setOutputProperty(OutputKeys.STANDALONE, "yes");
		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes"); // omite
																			// el
																			// encoding

		sw = new StringWriter();
		result = new StreamResult(sw);
		source = new DOMSource(xml.getDocumentElement());
		trans.transform(source, result);
		xmlString = sw.toString();

		return xmlString;
	}

	public String convertObjectToString(Object doc) throws Exception {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer;
		String output = "";
		transformer = tf.newTransformer();
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource((Node) doc), new StreamResult(
				writer));
		output = writer.getBuffer().toString();

		return output;
	}

	public Document convertStringToDocument(String xmlStr) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		Document doc = null;

		builder = factory.newDocumentBuilder();

		doc = builder.parse(new InputSource(new StringReader(xmlStr)));

		return doc;
	}

	public Document nodeListToDocument(NodeList nodos, String elementoRaiz) {

		// crea un nuevo documento
		Document newXmlDocument = null;
		try {
			newXmlDocument = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder().newDocument();
		} catch (ParserConfigurationException e) {
			// logger.error(e.getMessage());
		}

		// Elemento Raiz
		Element root = newXmlDocument.createElement(elementoRaiz);

		newXmlDocument.appendChild(root);

		// agrega los nodos al elemento raiz
		for (int i = 0; i < nodos.getLength(); i++) {
			Node node = nodos.item(i);
			Node copyNode = newXmlDocument.importNode(node, true);
			root.appendChild(copyNode);
		}

		return newXmlDocument;
	}

	/*
	 * public static Binds jaxbXMLToObject() { try { JAXBContext context =
	 * JAXBContext.newInstance(Binds.class); Unmarshaller un =
	 * context.createUnmarshaller(); Binds emp = (Binds) un.unmarshal(new
	 * File(FILE_NAME)); return emp; } catch (JAXBException e) {
	 * e.printStackTrace(); } return null; }
	 */

	public Document jaxbObjectToXML(Bind bind) throws Exception {

		Document document = null;

		// Create the Document
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		document = db.newDocument();

		JAXBContext context = JAXBContext.newInstance(Bind.class);
		Marshaller m = context.createMarshaller();
		// for pretty-print XML in JAXB
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Write to System.out for debugging
		// m.marshal(emp, System.out);

		// Write to File

		// StringWriter stringWriter = new StringWriter();

		m.marshal(bind, document);

		// Convert StringWriter to String
		// String xml = stringWriter.toString();
		// System.out.println(xml);

		return document;
	}

	public String jaxbObjectToXMLString(Bind bind) throws Exception {

		if (bind == null) {
			return null;
		}

		String xmlString = null;

		JAXBContext context = JAXBContext.newInstance(Bind.class);
		Marshaller m = context.createMarshaller();
		// for pretty-print XML in JAXB
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		StringWriter stringWriter = new StringWriter();

		m.marshal(bind, stringWriter);

		// Convert StringWriter to String
		xmlString = stringWriter.toString();

		return xmlString;

	}

	public List<ListaParametros> extraerListaDeParametros(Bind sentencias_binds)
			throws Exception {

		Document doc = null;
		List<ListaParametros> arrayDeListasDeParametros = new ArrayList<ListaParametros>();

		if (sentencias_binds == null) {

			return new ArrayList<ListaParametros>();
		}

		if (sentencias_binds.getAny() == null) {

			return new ArrayList<ListaParametros>();
		}

		if (!sentencias_binds.getAny().isEmpty()) {

			doc = jaxbObjectToXML(sentencias_binds);

			doc = eliminaNodosDeTextosVaciosDelDocumento(doc);

			// NOTAR QUE SOLO SE RECUPERA EL VALOR DE LA RAIZ
			NodeList sentenciasList = doc.getChildNodes().item(0)
					.getChildNodes();

			if (sentenciasList.getLength() > 0) {

				System.out.print("CANTIDAD DE SENTENCIAS SQL "
						+ sentenciasList.getLength());
				for (int c = 0; c < sentenciasList.getLength(); c++) {

					NodeList parametrosList = sentenciasList.item(c)
							.getChildNodes();

					System.out.print("CANTIDAD DE PARAMETROS BINDS "
							+ parametrosList.getLength());

					ListaParametros parametros = new ListaParametros();

					for (int d = 0; d < parametrosList.getLength(); d++) {

						String textoDelNodo = parametrosList.item(d)
								.getTextContent();

						parametros.getParametros().add(textoDelNodo);

					}

					arrayDeListasDeParametros.add(parametros);

				}
			}

		}

		return arrayDeListasDeParametros;

	}

}

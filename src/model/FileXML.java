package model;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

public class FileXML {
	
	public void generateXML() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation implementation = builder.getDOMImplementation();
			
			Document document = implementation.createDocument(null, "Hospital", null);
			document.setXmlVersion("1.0");
			
		} catch (Exception e) {

		}

	}
}

package model;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class FileXML {

	public static void main(String argv[]) {
		Room room = new Room();
		Patient patient = new Patient();
		String Id = null;
		String roomNumber = null;
		String floorNumber = null;
		String bedNumbers = null;
		Sql sql = new Sql();
		try {

			File inputFile = new File("src/resources/rooms.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("room");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					System.out.println("Room id : " + eElement.getAttribute("id"));
					Id = eElement.getAttribute("id");
					System.out.println(
							"Floor number : " + eElement.getElementsByTagName("floorNumber").item(0).getTextContent());
					floorNumber = eElement.getElementsByTagName("floorNumber").item(0).getTextContent();
					System.out.println(
							"Room number : " + eElement.getElementsByTagName("roomNumber").item(0).getTextContent());
					roomNumber = eElement.getElementsByTagName("roomNumber").item(0).getTextContent();
					NodeList patientsList = eElement.getElementsByTagName("patient");
					System.out.println("Number of patients : " + patientsList.getLength());

					for (int i = 0; i < patientsList.getLength(); i++) {
						Node patientNode = patientsList.item(i);
						if (patientNode.getNodeType() == Node.ELEMENT_NODE) {
							Element patientElement = (Element) patientNode;
							System.out.println("Patient name : "
									+ patientElement.getElementsByTagName("firstName").item(0).getTextContent()
									+ " " + patientElement.getElementsByTagName("lastName").item(0).getTextContent());
							System.out.println("Contact phone number : " + patientElement
									.getElementsByTagName("contactPhoneNumber").item(0).getTextContent());

							patient = new Patient(
									patientElement.getElementsByTagName("firstName").item(0).getTextContent(),
									patientElement.getElementsByTagName("lastName").item(0).getTextContent(),
									patientElement.getElementsByTagName("contactPhoneNumber").item(0).getTextContent());
							room.addPatient(patient);

						}
					}
				}
				room = new Room(Integer.parseInt(Id), Integer.parseInt(bedNumbers), Integer.parseInt(floorNumber),
						Short.valueOf(roomNumber), room.getListPatients());
				sql.addRoom(room);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
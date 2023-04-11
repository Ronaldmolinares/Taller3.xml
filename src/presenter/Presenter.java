package presenter;

import view.View;
import model.*;
import exceptions.*;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Presenter {
	Room room = new Room();
	Patient patient;
	FileXML fileXML;
	Sql sql = new Sql();
	private View view = new View();

	// Ejecucion del programa
	private void run() {
		boolean exit = false;
		int option;
		readXml();
		// Menu
		do {
			view.showMessage("\n******** Bienvenido a AppColsanitas ********");
			view.showMessage("1. Crear habitacion.");
			view.showMessage("2. Crear paciente.");
			view.showMessage("3. Mostrar historial de pacientes por habitacion.");
			view.showMessage("4. Generar XML.");
			view.showMessage("5. Salir.");

			option = view.readInt("Seleccione una de las opciones ");
			switch (option) {
				case 1:
					addRoom();
					break;
				case 2:
					addPatient();
					break;

				case 3:
					historyRooms();
					break;

				case 4:
					room = new Room(1, 2, 2, (short) 205);
					patient = new Patient("Lunna", "Sosa", "21321", Status.ACTIVE);
					room.addPatient(patient);
					patient = new Patient("Camila", "Espitia", "557880", Status.ACTIVE);
					room.addPatient(patient);
					sql.addRoom(room);
					room = new Room(2, 3, 3, (short) 309);
					patient = new Patient("Ana", "Espitia", "589743587", Status.ACTIVE);
					room.addPatient(patient);
					patient = new Patient("Liyen", "Merchan", "88098", Status.ACTIVE);
					room.addPatient(patient);
					sql.addRoom(room);
					saveXML();
					break;

				case 5:
					// lógica de salida de la aplicación.
					saveXML();
					exit = true;
					System.exit(0);
					break;

				default:
					view.showMessage("Solo números entre 1 y 5");
			}

		} while (!exit);
	}

	private void readXml() {
		File inputFile = new File("src/resources/rooms.xml");
		if (inputFile.exists()) {
			view.showMessage("El archivo " + inputFile.getName() + " existe y contiene :");
			Room room = new Room();
			Patient patient = new Patient();
			int Id = -1;
			short roomNumber = -1;
			int floorNumber = -1;
			int bedNumbers = -1;
			Status status = Status.INACTIVE;
			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(inputFile);
				doc.getDocumentElement().normalize();

				System.out.println("\nRoot element :" + doc.getDocumentElement().getNodeName());

				NodeList nList = doc.getElementsByTagName("room");

				for (int temp = 0; temp < nList.getLength(); temp++) {

					Node nNode = nList.item(temp);

					System.out.println("\nCurrent Element :" + nNode.getNodeName());

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;

						System.out.println("\nRoom id : " + eElement.getAttribute("id"));
						Id = Integer.parseInt(eElement.getAttribute("id"));
						System.out.println(
								"Bed Numbers : "
										+ eElement.getElementsByTagName("bedNumbers").item(0).getTextContent());
						bedNumbers = Integer
								.parseInt(eElement.getElementsByTagName("bedNumbers").item(0).getTextContent());
						System.out.println(
								"Floor number : "
										+ eElement.getElementsByTagName("floorNumber").item(0).getTextContent());
						floorNumber = Integer
								.parseInt(eElement.getElementsByTagName("floorNumber").item(0).getTextContent());
						System.out.println(
								"Room number : "
										+ eElement.getElementsByTagName("roomNumber").item(0).getTextContent());
						roomNumber = Short
								.valueOf(eElement.getElementsByTagName("roomNumber").item(0).getTextContent());
						room = new Room(Id, bedNumbers, floorNumber, roomNumber);
						NodeList patientsList = eElement.getElementsByTagName("patient");
						System.out.println("Number of patients : " + patientsList.getLength());

						for (int i = 0; i < patientsList.getLength(); i++) {
							Node patientNode = patientsList.item(i);
							if (patientNode.getNodeType() == Node.ELEMENT_NODE) {
								Element patientElement = (Element) patientNode;
								System.out.println("Patient name : "
										+ patientElement.getElementsByTagName("firstName").item(0).getTextContent()
										+ " "
										+ patientElement.getElementsByTagName("lastName").item(0).getTextContent());
								System.out.println("Contact phone number : " + patientElement
										.getElementsByTagName("contactPhoneNumber").item(0).getTextContent());
								System.out.println("Status of patient : " + patientElement
										.getElementsByTagName("status").item(0).getTextContent());
								status = Status.valueOf(patientElement
										.getElementsByTagName("status").item(0).getTextContent().toUpperCase());
								patient = new Patient(
										patientElement.getElementsByTagName("firstName").item(0).getTextContent(),
										patientElement.getElementsByTagName("lastName").item(0).getTextContent(),
										patientElement.getElementsByTagName("contactPhoneNumber").item(0)
												.getTextContent(),
										status);
								room.addPatient(patient);
							}
						}

					}
					sql.addRoom(room);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			view.showMessage("No existe un archivo previo" + "\n");
		}
	}

	// Metodo encargado de mostraar el historial de pacientes por habitacion
	private void historyRooms() {
		int id = view.readInt("Ingrese el id de la habitación: ");
		// validamos que la habitacion exista
		int posRoom = sql.findRoom(id);
		// Si es diferente a -1 quiere decir que la habitacion existe y por tanto me
		// muestra la lista de pacientes que hay en la habitacion
		if (posRoom != -1) {
			Room r = sql.getListRoom().get(posRoom);
			if (r.getId() == id) {
				for (Patient p : r.getListPatients()) {
					// Valido que los pacientes de la habitacion esten en estado activo
					if (p.getStatus().equals(Status.ACTIVE)) {
						System.out.println("Nombre: " + p.getName() + ", Apellido: " + p.getLastName() + ", Telefono: "
								+ p.getPhone());
					}
				}
			}
		} else {
			Exception e = new DuplicateException("La habitacion no existe.");
			view.showMessage(e.getMessage());
		}
	}

	private void saveXML() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation dom = builder.getDOMImplementation();
			Document document = dom.createDocument(null, "hospital", null);
			document.setXmlVersion("1.0");
			Element rooms = document.createElement("rooms");
			for (int i = 0; i < sql.getListRoom().size(); i++) {
				Element room = document.createElement("room");
				room.setAttribute("id", "" + sql.getListRoom().get(i).getId());
				Element bedNumbers = document.createElement("bedNumbers");
				Text valueBedNumbers = document.createTextNode(sql.getListRoom().get(i).getBedNumbers() + "");
				bedNumbers.appendChild(valueBedNumbers);
				room.appendChild(bedNumbers);
				Element floorNumber = document.createElement("floorNumber");
				Text valueFloorNumber = document.createTextNode(sql.getListRoom().get(i).getFloorNumber() + "");
				floorNumber.appendChild(valueFloorNumber);
				room.appendChild(floorNumber);
				Element roomNumber = document.createElement("roomNumber"); // cambiar a roomNumber
				Text valueRoomNumber = document.createTextNode(sql.getListRoom().get(i).getRoomNumber() + "");
				roomNumber.appendChild(valueRoomNumber);
				room.appendChild(roomNumber);
				Element patients = document.createElement("patients"); // crear elemento patients
				for (int j = 0; j < sql.getListRoom().get(i).getListPatients().size(); j++) {
					Element patient = document.createElement("patient");
					Element firstName = document.createElement("firstName");
					Text textFirstName = document
							.createTextNode(sql.getListRoom().get(i).getListPatients().get(j).getName() + "");
					firstName.appendChild(textFirstName);
					patient.appendChild(firstName);
					Element lastName = document.createElement("lastName");
					Text textLastName = document
							.createTextNode(sql.getListRoom().get(i).getListPatients().get(j).getLastName() + "");
					lastName.appendChild(textLastName);
					patient.appendChild(lastName);
					Element contactPhoneNumber = document.createElement("contactPhoneNumber");
					Text valueContactPhoneNumber = document
							.createTextNode(sql.getListRoom().get(i).getListPatients().get(j).getPhone() + "");
					contactPhoneNumber.appendChild(valueContactPhoneNumber);
					patient.appendChild(contactPhoneNumber);
					Element status = document.createElement("status");
					Text valuestatus = document
							.createTextNode(sql.getListRoom().get(i).getListPatients().get(j).getStatus() + "");
					status.appendChild(valuestatus);
					patient.appendChild(status);
					patients.appendChild(patient);
				}
				room.appendChild(patients);
				rooms.appendChild(room);
			}
			document.getDocumentElement().appendChild(rooms);
			Source source = new DOMSource(document);
			Result result = new StreamResult(new File("src/resources/rooms.xml"));
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, result);
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}
	}

	// Metodo encargado de añadir una habitacion
	private void addRoom() {
		try {
			int id = view.readInt("Ingrese el id de la habitacion: ");
			int posRoom = sql.findRoom(id);
			// Si entra al if significa que aún no se ha creado una habitacion con ese id,
			// por tanto la crea
			if (posRoom == -1) {

				boolean exit1 = false;
				int numFloor = 0;
				while (!exit1) {
					// validamos que cumpla con la condicion del numero de pisos (1 - 30)
					numFloor = view.readShort("Ingrese el numero de piso de la habitacion: ");
					if (numFloor >= 1 && numFloor <= 30) {
						exit1 = true;
					} else {
						view.showMessage("El número de piso solo debe ser de 1 a 30." + "\n");
					}
				}

				short numRoom = view.readShort("Ingrese el numero de la habitacion: ");
				boolean isRoomFloor = false;

				for (Room ro : sql.getListRoom()) {
					if (numRoom == ro.getRoomNumber() && numFloor == ro.getFloorNumber())
						isRoomFloor = true;
				}

				if (!isRoomFloor) {
					boolean exit = false;
					while (!exit) {
						int numBed = view.readInt("Ingrese el numero de camas de la habitación: ");
						if (numBed >= 1 && numBed <= 5) {
							Room room = new Room(id, numFloor, numRoom, numBed);
							sql.addRoom(room);
							view.showMessage("Habitación creada con exito.");
							exit = true;
						} else {
							view.showMessage("El número de camas solo debe ser de 1 a 5." + "\n");
						}
					}

				} else {
					view.showMessage(
							"La habitacion en el piso " + numFloor + " y con numero " + numRoom + " ya existe.");
				}

			} else {
				Exception e = new DuplicateException("Ya existe esta habitacion.");
				view.showMessage(e.getMessage());
			}

		} catch (Exception em) {
			Exception e = new ValueNotFoundException("Habitacion ya existente.");
			view.showMessage(e.getMessage());
		}
	}

	// Metodo encargado de añadir un paciente
	private void addPatient() {

		if (sql.getListRoom().size() == 0) {
			view.showMessage("Se debe crear primero una habitacion para poder agregar el nuevo paciente." + "\n");
		} else {

			try {
				int id = view.readInt("Ingrese el id de la habitacion en la que estara el paciente:");
				int position = sql.findRoom(id);

				if (position != -1) {

					Room roomPos = sql.getListRoom().get(position);

					if (roomPos.getListPatients().size() < roomPos.getBedNumbers()) {
						patient = new Patient(view.read("Ingrese el nombre del paciente: "),
								view.read("Ingrese el apellido del paciente: "),
								view.read("Ingrese el numero de contacto del paciente: "),
								Status.ACTIVE);
						roomPos.addPatient(patient);
						view.showMessage("Paciente agregado con exito.");
					} else {
						view.showMessage("Lista de pacientes en estado activo: ");

						int i = 0;
						for (Patient p : roomPos.getListPatients()) {
							if (p.getStatus().equals(Status.ACTIVE)) {
								view.showMessage(
										"Nombre: " + p.getName() + ", Apellido: " + p.getLastName() + ", Telefono: "
												+ p.getPhone() + " -posicion: " + i);
							}
							i++;
						}

						view.showMessage(
								"Se excede el número de camas para agregar otro paciente, sin embargo se va remplazar al paciente en la posicion que elija. "
										+ "\n");
						view.showMessage(
								"Primero ingrese los datos del paciente, despues elija una de las posiciones mostradas en pantalla");

						patient = new Patient(view.read("Ingrese el nombre del paciente: "),
								view.read("Ingrese el apellido del paciente: "),
								view.read("Ingrese el numero de contacto del paciente: "),
								Status.ACTIVE);

						int posP = view.readInt("Ingrese la posición del paciente a rempalzar: ");
						roomPos.getListPatients().get(posP).setStatus(Status.INACTIVE);
						roomPos.addPatient(patient);
						view.showMessage("Paciente agregado.");
					}
				} else {
					Exception e = new DuplicateException("La habitacion no existe.");
					view.showMessage(e.getMessage());
				}

			} catch (Exception e) {
				e = new ValueNotFoundException("Error al crear paciente (posicion no valida).");
				view.showMessage(e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		new Presenter().run();
	}
}

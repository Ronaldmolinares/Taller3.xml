package presenter;

import view.View;
import model.*;
import exceptions.*;

public class Presenter {

	Room room = new Room();
	Patient patient;
	FileXML fileXML;
	Sql sql = new Sql();
	private View view = new View();

	//Ejecucion del programa
	private void run() {
		boolean exit = false;
		int option;

		//Menu
		do {
			view.showMessage("******** Bienvenido a AppColsanitas ********");
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
				saveXML();
				break;

			case 5:
				// lógica de salida de la aplicación.
				exit = true;
				System.exit(0);
				break;

			default:
				view.showMessage("Solo números entre 1 y 5");
			}

		} while (!exit);
	}

	//Metodo encargado de mostraar el historial de pacientes por habitacion
	private void historyRooms() {
		int id = view.readInt("Ingrese el id de la habitación: ");
		//validamos que la habitacion exista 
		int posRoom = sql.findRoom(id);
		//Si es diferente a -1 quiere decir que la habitacion existe y por tanto me muestra la lista de pacientes que hay en la habitacion
		if (posRoom != -1) {
			Room r = sql.getListRoom().get(posRoom);
			if (r.getId() == id) {
				for (Patient p : r.getListPatients()) {
					//Valido que los pacientes de la habitacion esten en estado activo			
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
	}

	//Metodo encargado de añadir una habitacion
	private void addRoom() {
		try {
			int id = view.readInt("Ingrese el id de la habitacion: ");
			int posRoom = sql.findRoom(id);
			//Si entra al if significa que aún no se ha creado una habitacion con ese id, por tanto la crea
			if (posRoom == -1) {

				boolean exit1 = false;
				int numFloor = 0;
				while (!exit1) {
					//validamos que cumpla con la condicion del numero de pisos (1 - 30)
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

	//Metodo encargado de añadir un paciente
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

					} else {
						view.showMessage("Lista de pacientes en estado activo: ");

						int i = 0;
						for (Patient p : roomPos.getListPatients()) {
							if (p.getStatus().equals(Status.ACTIVE)) {
								view.showMessage("Nombre: " + p.getName() + ", Apellido: " + p.getLastName() + ", Telefono: "
										+ p.getPhone() + " -posicion: "+ i);
							}
							i++;
						}	
						
						view.showMessage("Se excede el número de camas para agregar otro paciente, sin embargo se va remplazar al paciente en la posicion que elija. " + "\n");
						view.showMessage("Primero ingrese los datos del paciente, despues elija una de las posiciones mostradas en pantalla");

						patient = new Patient(view.read("Ingrese el nombre del paciente: "),
								view.read("Ingrese el apellido del paciente: "),
								view.read("Ingrese el numero de contacto del paciente: "),
								Status.ACTIVE);

						int posP = view.readInt("Ingrese la posición del paciente a rempalzar: ");
						roomPos.getListPatients().get(posP).setStatus(Status.INACTIVE);
						roomPos.addPatient(patient);
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

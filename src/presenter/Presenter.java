package presenter;

import view.View;
import model.*;
import exceptions.*;

public class Presenter {

    Sql sql = new Sql();
    Room room = new Room();
    private View view = new View();
    
    private void run() {
        boolean exit = false;
		int option;

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
				exit = true;
				System.exit(0);
				break;

			default:
				view.showMessage("Solo números entre 1 y 5");
			}

		} while (!exit);
    }

    private void historyRooms(){}

    private void saveXML(){}

    private void addRoom() {
		try {
			int id = view.readInt("Ingrese el id de la habitacion: ");
			int posRoom = sql.findRoom(id);

			if (posRoom == -1) {
				short numRoom = view.readShort("Ingrese el numero de piso de la habitacion: ");
				int numFloor = view.readShort("Ingrese el numero de la habitacion: ");

				Room r = sql.getListRoom().get(posRoom);
				boolean isRoomFloor = false;

				for (Room ro : sql.getListRoom()) {
					if (numRoom == r.getRoomNumber() && numFloor == r.getFloorNumber())
						isRoomFloor = true;
				} if (!isRoomFloor) {
					int numBed =  view.readInt("Ingrese el numero de camas de la habitación: ");
					Room room = new Room (id,  numFloor, numRoom, numBed);
					sql.addRoom(room);
				} else {
					view.showMessage("La habitacion en el piso " + numFloor + " y con numero " + numRoom + " ya existe.");
				}
			} else {
				Exception e = new DuplicateException("Ya existe esta habitacion.");
				view.showMessage(e.getMessage());
			}

		} catch (Exception em) {
			Exception e = new ValueNotFoundException("Ya existe esta habitacionnnnn.");
			view.showMessage(e.getMessage());
		}
	}

    private void addPatient() {
        view.showMessage("Se debe crear primero una habitacion para poder agregar el nuevo paciente." + "\n");
        int id = view.readInt("Ingrese el id de la habitacion en la que estara el paciente: ");
        view.read("Ingrese el nombre del paciente: ");
        view.read("Ingrese el apellido del paciente: ");
    }

    public static void main(String[] args) {	
		new Presenter().run();
	}
}

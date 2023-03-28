package presenter;

import view.View;

public class Presenter {

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
        int id = view.readInt("Ingrese el id de la habitacion: ");
        short numRoom = view.readShort("Ingrese el numero de piso de la habitacion: ");
        int numFloor = view.readShort("Ingrese el numero de la habitacion: ");
        int numBed =  view.readInt("Ingrese el numero de camas de la habitación: ");
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

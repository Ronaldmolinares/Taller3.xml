package presenter;

import view.View;

public class Presenter {

    private View view = new View();
    
    private void run() {
        view.showMessage("******** Bienvenido a AppColsanitas ********");
        view.showMessage("1. Crear habitacion.");
		view.showMessage("2. Crear paciente.");
        view.showMessage("3. Mostrar historial de pacientes por habitacion.");
		view.showMessage("4. Generar XML.");
		view.showMessage("5. Salir.");

		int option = view.readInt("Seleccione una de las opciones ");
    }

    private void addRoom() {
        int id = view.readInt("Ingrese el id de la habitacion: ");
        short numRoom = view.readShort("Ingrese el numero de piso de la habitacion: ");
        int numFloor = view.readShort("Ingrese el numero de la habitacion: ");
        int numBed =  view.readInt("Ingrese el numero de camas de la habitación: ");
    }

    public static void main(String[] args) {	
		new Presenter().run();	
	}
}

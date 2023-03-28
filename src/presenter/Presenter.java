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

    public static void main(String[] args) {	
		new Presenter().run();	
	}
}

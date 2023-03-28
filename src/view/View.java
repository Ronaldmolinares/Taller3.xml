package view;
import java.util.Scanner;

public class View {
	private Scanner scanner = new Scanner(System.in);

	public void showMessage(String message) {
		System.out.println(message);
	}

	public double readDouble(String message) {
		showMessage(message);
		double value= 0;

		boolean continua = true;

		do {
			try {
				value= Double.parseDouble(scanner.nextLine());
				continua= false;

			} catch (NumberFormatException e) {
				showMessage("Error al ingresar el numero, intente de nuevo: " );
				continua= true;
			}
		} while (continua);
		return value;
	} 

	public int readInt(String message) {
		showMessage(message);
		int value= 0;

		boolean continua = true;

		do {
			try {
				value= Integer.parseInt(scanner.nextLine());
				continua= false;

			} catch (NumberFormatException e) {
				showMessage("Error al ingresar el numero, intente de nuevo: " );
				continua= true;
			}
		} while (continua);
		return value;
	}

	public short readShort(String message) {
		showMessage(message);
		short value= 0;

		boolean continua = true;

		do {
			try {
				value= (short) Short.parseShort(scanner.nextLine());
				continua= false;

			} catch (NumberFormatException e) {
				showMessage("Error al ingresar el numero, intente de nuevo: " );
				continua= true;
			}
		} while (continua);
		return value;
	}
	
	public String read(String message) {
		showMessage(message);	
		return scanner.nextLine();
	}

}
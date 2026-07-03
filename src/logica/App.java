package logica;
//Carlos Alberto Montenegro Perez 22154893-0 ICCI
//Daniel Alexanders Robles Valdenegro 20738244-2 ICCI

import java.io.FileNotFoundException;

import gui.VentanaPrincipal;


public class App {
	
	private static ISistema sys = SistemaImpl.getInstance();

	public static void main(String[] args) throws FileNotFoundException {
		
		sys.cargarCartas();
		VentanaPrincipal ventana = new VentanaPrincipal();
		ventana.setVisible(true);
	}

}
	
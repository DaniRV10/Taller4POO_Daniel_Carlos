package logica;

import java.io.FileNotFoundException;

import gui.VentanaPrincipal;

//Carlos Alberto Montenegro Perez 22154893-0 ICCI
//Daniel Alexanders Robles Valdenegro 20738244-2 ICCI

public class App {
	
	private static ISistema sys = SistemaImpl.getInstance();

	public static void main(String[] args) throws FileNotFoundException {
		
		sys.cargarCartas();
		//System.out.println("RUTA CORRECTA: " + new java.io.File("images/default.png").getAbsolutePath());
		VentanaPrincipal ventana = new VentanaPrincipal();
		ventana.setVisible(true);
	}

}
	
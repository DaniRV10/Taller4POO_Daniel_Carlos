package logica;

import java.io.FileNotFoundException;
import java.io.IOException;

import dominio.Carta;

public interface ISistema {
	
	void cargarCartas() throws FileNotFoundException;
	void guardarCartas() throws IOException ;
	void agregarCarta(Carta nuevaC);
	boolean eliminarCarta(int indice);
	boolean modificarCarta(int indice, String[] nuevosAtributos);
	

}

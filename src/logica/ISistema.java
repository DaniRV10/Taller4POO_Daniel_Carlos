package logica;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import dominio.Carta;
import strategy.Ordenamiento;

public interface ISistema {
	
	void cargarCartas() throws FileNotFoundException;
	void guardarCartas() throws IOException ;
	void agregarCarta(Carta nuevaC);
	boolean eliminarCarta(int indice);
	boolean modificarCarta(int indice, String[] nuevosAtributos);
	
	void ordenarColeccion(Ordenamiento estrategia);
	List<Carta> getColeccion();
	

}

package logica;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface ISistema {
	
	void cargarCartas() throws FileNotFoundException;
	void guardarCartas() throws IOException ;
	

}

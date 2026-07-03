package logica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dominio.*;
import factory.*;
import strategy.*;

public class SistemaImpl implements ISistema{
	
	private static SistemaImpl instance;
	private List<Carta> coleccion;
	
	//Singleton
	private SistemaImpl() {
		this.coleccion = new ArrayList<>();
	}
	
	public static SistemaImpl getInstance() {
		if(instance == null) {
			instance = new SistemaImpl();
		}
		return instance;
	}

	@Override
	public void cargarCartas() throws FileNotFoundException {
		
		File archivo = new File("txt/Sobres.txt");
		
        if (!archivo.exists()) {
            return; // Si no existe el archivo, parte con la lista vacía de forma segura
        }
        
        Scanner lector = new Scanner(archivo);
        while(lector.hasNextLine()) {
        	String linea = lector.nextLine();
        	Carta nuevaCarta = CartaFactory.crearCarta(linea);
        	this.coleccion.add(nuevaCarta);
        }
	}

	@Override
	public void guardarCartas(){
		try {
		FileWriter fw = new FileWriter("txt/Sobres.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        for(Carta carta: this.coleccion) {
        	bw.write(carta.FormatoString());
        	bw.newLine();
        }
        bw.close();
        fw.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// ==========================================
    // PESTAÑA 1
    // ==========================================
	
	@Override
	public void agregarCarta(Carta nuevaCarta) {
        this.coleccion.add(nuevaCarta);
        guardarCartas(); 
    }
	
	@Override
	public boolean eliminarCarta(int indice) {
	    // Validación de entrada obligatoria por pauta para evitar caídas (try-catch o rango)
	    if (indice >= 0 && indice < this.coleccion.size()) {
	        
	        // Se elimina el objeto de la lista en memoria (no la imagen del disco)
	        this.coleccion.remove(indice); 
	        
	        // Actualización automática para que los cambios persistan al cerrar la app
	        this.guardarCartas(); 
	        return true;
	    }
	    return false; // Índice fuera de rango
	}
	
	@Override
	public boolean modificarCarta(int indice, String[] nuevosAtributos) {
	    // Validación de rango para asegurar la estabilidad del programa
	    if (indice >= 0 && indice < this.coleccion.size()) {
	        try {
	            // Obtenemos la carta exacta (soluciona el problema de las repetidas)
	            Carta carta = this.coleccion.get(indice);
	            
	            //Se delega la actualizacion a las subclases
	            carta.actualizarAtributos(nuevosAtributos);
	            
	            // Sobrescribimos el archivo manteniendo estrictamente el formato original
	            this.guardarCartas(); 
	            return true;
	            
	        } catch (Exception e) {
	            System.err.println("Error de validación en los datos de modificación: " + e.getMessage());
	            return false;
	        }
	    }
	    return false; // Índice fuera de rango
	}
	
	// ==========================================
    // PESTAÑA 2
    // ==========================================
	
	
	
	@Override
    public void ordenarColeccion(Ordenamiento estrategia) {
        // Delega el ordenamiento dinámico al patrón Strategy escogido
        estrategia.ordenar(this.coleccion);
    }

    @Override
    public List<Carta> getColeccion() {
        // Retorna una copia de la lista para proteger el encapsulamiento
        return new ArrayList<>(this.coleccion);
    }
}

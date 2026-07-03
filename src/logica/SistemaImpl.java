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
	public void guardarCartas() throws IOException {
		
		FileWriter fw = new FileWriter("txt/Sobres.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        for(Carta carta: this.coleccion) {
        	bw.write(carta.FormatoString());
        	bw.newLine();
        }
        bw.close();
        fw.close();
	}
	

}

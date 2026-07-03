package factory;

import dominio.*;

public class CartaFactory {
	
	public static Carta crearCarta(String linea) {
		String[] partes = linea.split(";");
		String nombre = partes[0];
		int rareza = Integer.parseInt(partes[1]);
		String tipo = partes[2];
		
		switch(tipo.toLowerCase()) {
			case "pokemon":
				int danio = Integer.parseInt(partes[3]);
				int energias = Integer.parseInt(partes[4]);
				return new PokemonCarta(nombre, rareza, danio, energias);
			case "item":
				int bonif =  Integer.parseInt(partes[3]);
				return new ItemCarta(nombre,rareza,bonif);
			case "supporter":
				int efectos = Integer.parseInt(partes[3]);
				return new SuportCarta(nombre,rareza,efectos);
			case "energy":
				String elemento = partes[3];
				return new EnergiaCarta(nombre,rareza,elemento);
			default:
				throw new IllegalArgumentException("Tipo de carta desconocido");
		}		
	}
}

package strategy;

import java.util.List;

import dominio.Carta;

public class OrdenarNombre implements Ordenamiento {

	@Override
    public void ordenar(List<Carta> cartas) {
        // Ordena alfabéticamente por el atributo nombre
        cartas.sort((c1, c2) -> c1.getNombre().compareToIgnoreCase(c2.getNombre()));
    }
}

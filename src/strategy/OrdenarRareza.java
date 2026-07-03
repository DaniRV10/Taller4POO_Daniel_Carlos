package strategy;

import java.util.List;
import dominio.*;


public class OrdenarRareza implements Ordenamiento {

	@Override
    public void ordenar(List<Carta> cartas) {
        // Ordena de mayor rareza a menor rareza
        cartas.sort((c1, c2) -> Integer.compare(c2.getRareza(), c1.getRareza()));
    }

}

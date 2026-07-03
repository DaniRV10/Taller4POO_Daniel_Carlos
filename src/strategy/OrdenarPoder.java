package strategy;

import java.util.List;

import dominio.*;

public class OrdenarPoder implements Ordenamiento {

	@Override
    public void ordenar(List<Carta> cartas) {
        // Instanciamos el visitante que conoce las fórmulas matemáticas de cada tipo de carta
        CartaVisitor calculador = new CalcularPoderVisitor();
        
        // Ordena de mayor poder a menor poder utilizando el patrón Visitor (accept)
        cartas.sort((c1, c2) -> Double.compare(c2.accept(calculador), c1.accept(calculador)));
    }

}

package gui;

import dominio.Carta;
import dominio.EnergiaCarta;
import dominio.ItemCarta;
import dominio.PokemonCarta;
import dominio.SuportCarta;

/**
 * Utilidad de la capa de presentacion que permite conocer el tipo de una
 * carta y sus atributos adicionales sin agregar metodos nuevos a las
 * clases de {@code dominio}, que ya definen ese comportamiento a traves
 * de {@code FormatoString()} y {@code actualizarAtributos(String[])}.
 */
public final class CartaTipoUtil {

    /** Tipos de carta soportados, en el mismo orden usado en Sobres.txt. */
    public static final String[] TIPOS = {"Pokemon", "Item", "Supporter", "Energy"};

    private CartaTipoUtil() {
        // Clase de utilidad, no instanciable.
    }

    /**
     * Determina el tipo de una carta segun su clase concreta.
     *
     * @param carta carta a inspeccionar
     * @return "Pokemon", "Item", "Supporter" o "Energy"
     */
    public static String tipoDe(Carta carta) {
        if (carta instanceof PokemonCarta) {
            return "Pokemon";
        } else if (carta instanceof ItemCarta) {
            return "Item";
        } else if (carta instanceof SuportCarta) {
            return "Supporter";
        } else if (carta instanceof EnergiaCarta) {
            return "Energy";
        }
        return "Desconocido";
    }

    /**
     * Retorna los atributos adicionales de una carta, listos para mostrar
     * en la vista ampliada (una linea por atributo, con su etiqueta).
     *
     * @param carta carta a inspeccionar
     * @return arreglo de textos "Etiqueta: valor"
     */
    public static String[] atributosParaMostrar(Carta carta) {
        if (carta instanceof PokemonCarta p) {
            return new String[]{"Dano: " + p.getDanio(), "Cantidad de Energias: " + p.getEnergias()};
        } else if (carta instanceof ItemCarta i) {
            return new String[]{"Bonificacion: " + i.getBonif()};
        } else if (carta instanceof SuportCarta s) {
            return new String[]{"Efectos por Turno: " + s.getEfectos()};
        } else if (carta instanceof EnergiaCarta e) {
            return new String[]{"Elemento: " + e.getElemento()};
        }
        return new String[0];
    }

    /**
     * Retorna los valores actuales de los atributos adicionales de una
     * carta, en el mismo orden que espera {@code actualizarAtributos}.
     *
     * @param carta carta a inspeccionar
     * @return arreglo con los valores actuales, como String
     */
    public static String[] valoresAtributos(Carta carta) {
        if (carta instanceof PokemonCarta p) {
            return new String[]{String.valueOf(p.getDanio()), String.valueOf(p.getEnergias())};
        } else if (carta instanceof ItemCarta i) {
            return new String[]{String.valueOf(i.getBonif())};
        } else if (carta instanceof SuportCarta s) {
            return new String[]{String.valueOf(s.getEfectos())};
        } else if (carta instanceof EnergiaCarta e) {
            return new String[]{e.getElemento()};
        }
        return new String[0];
    }
}

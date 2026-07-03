package gui;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.File;

/**
 * Utilidad encargada de resolver y cargar la imagen de una carta a partir
 * de su nombre, probando distintas extensiones. Si no existe una imagen
 * para la carta, retorna una imagen por defecto para no romper la GUI.
 */
public final class ImagenUtil {

    private static final String CARPETA_IMAGENES = "images" + File.separator;
    private static final String[] EXTENSIONES = {"png", "jpg", "jpeg", "gif"};
    private static final String IMAGEN_DEFECTO = CARPETA_IMAGENES + "default.png";

    private ImagenUtil() {
        // Clase de utilidad, no instanciable.
    }

    /**
     * Busca y carga, escalada, la imagen correspondiente a una carta.
     * Si no se encuentra ninguna imagen (ni siquiera la por defecto),
     * retorna {@code null} y el llamador debe manejar ese caso.
     *
     * @param nombreCarta nombre de la carta (usado como nombre de archivo)
     * @param ancho       ancho deseado en pixeles
     * @param alto        alto deseado en pixeles
     * @return el icono escalado, o {@code null} si no hay imagen disponible
     */
    public static ImageIcon obtenerIcono(String nombreCarta, int ancho, int alto) {
        for (String ext : EXTENSIONES) {
            File archivo = new File(CARPETA_IMAGENES + nombreCarta + "." + ext);
            if (archivo.exists()) {
                return escalar(new ImageIcon(archivo.getPath()), ancho, alto);
            }
        }
        File defecto = new File(IMAGEN_DEFECTO);
        if (defecto.exists()) {
            return escalar(new ImageIcon(defecto.getPath()), ancho, alto);
        }
        return null;
    }

    private static ImageIcon escalar(ImageIcon original, int ancho, int alto) {
        Image escalada = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(escalada);
    }
}
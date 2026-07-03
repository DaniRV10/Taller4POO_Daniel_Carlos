package gui;
import dominio.Carta;
import dominio.CalcularPoderVisitor;
import dominio.CartaVisitor;

import javax.swing.*;
import java.awt.*;

public class VistaAmpliadaCarta extends JDialog {

    private static final CartaVisitor PODER_VISITOR = new CalcularPoderVisitor();

    public VistaAmpliadaCarta(Window owner, Carta carta) {
        super(owner, "Detalle: " + carta.getNombre(), ModalityType.APPLICATION_MODAL);
        setSize(350, 450);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        // Zona superior: Carga de imagen usando ImagenUtil 
        JLabel lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        ImageIcon icono = ImagenUtil.obtenerIcono(carta.getNombre(), 180, 180);
        if (icono != null) {
            lblImagen.setIcon(icono);
        } else {
            lblImagen.setText("[Sin Imagen]");
        }
        add(lblImagen, BorderLayout.NORTH);

        // Zona central: Datos básicos y dinámicos de utilidad
        JPanel panelDatos = new JPanel(new GridLayout(0, 1, 5, 5));
        panelDatos.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panelDatos.add(new JLabel("Nombre: " + carta.getNombre()));
        panelDatos.add(new JLabel("Tipo: " + CartaTipoUtil.tipoDe(carta)));
        panelDatos.add(new JLabel("Rareza: ★ " + carta.getRareza()));

        // Atributos dinámicos según el tipo de carta
        for (String lineaAtributo : CartaTipoUtil.atributosParaMostrar(carta)) {
            panelDatos.add(new JLabel(lineaAtributo));
        }

        double poder = carta.accept(PODER_VISITOR);
        JLabel lblPoder = new JLabel(String.format("Poder Calculado: %.2f", poder));
        lblPoder.setFont(new Font("Arial", Font.BOLD, 14));
        panelDatos.add(lblPoder);

        add(panelDatos, BorderLayout.CENTER);

        // Botón inferior para salir del diálogo modal
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        add(btnCerrar, BorderLayout.SOUTH);
    }
}
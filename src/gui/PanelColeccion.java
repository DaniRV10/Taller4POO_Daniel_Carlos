package gui;
import dominio.Carta;
import logica.SistemaImpl;
import strategy.Ordenamiento;
import strategy.OrdenarNombre;
import strategy.OrdenarPoder;
import strategy.OrdenarRareza;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelColeccion extends JPanel {

    private final DefaultListModel<Carta> modeloLista = new DefaultListModel<>();
    private final JList<Carta> listaCartas = new JList<>(modeloLista);
    private final JComboBox<String> comboOrden = new JComboBox<>(new String[]{"Rareza", "Nombre", "Poder"});

    public PanelColeccion() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

       
        listaCartas.setCellRenderer(new CartaCellRenderer());
        listaCartas.setVisibleRowCount(-1); 
        listaCartas.setLayoutOrientation(JList.HORIZONTAL_WRAP); // Hace que las cartas se organicen de izquierda a derecha

        // Panel superior para ordenamientos 
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(new JLabel("Ordenar por:"));
        panelSuperior.add(comboOrden);
        JButton btnOrdenar = new JButton("Aplicar Orden");
        panelSuperior.add(btnOrdenar);
        add(panelSuperior, BorderLayout.NORTH);

        // Lista de visualización envuelta en scroll panel
        add(new JScrollPane(listaCartas), BorderLayout.CENTER);

        // Al hacer clic abre la vista ampliada con los atributos y la imagen
        listaCartas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = listaCartas.locationToIndex(e.getPoint());
                if (index >= 0) {
                    Carta seleccionada = modeloLista.get(index);
                    Window ventanaPadre = SwingUtilities.getWindowAncestor(PanelColeccion.this);
                    new VistaAmpliadaCarta(ventanaPadre, seleccionada).setVisible(true);
                }
            }
        });

        btnOrdenar.addActionListener(e -> ordenarYRefrescar());
        refrescar();
    }

    private void ordenarYRefrescar() {
        Ordenamiento estrategia = switch ((String) comboOrden.getSelectedItem()) {
            case "Nombre" -> new OrdenarNombre();
            case "Poder" -> new OrdenarPoder();
            default -> new OrdenarRareza();
        };
        SistemaImpl.getInstance().ordenarColeccion(estrategia);
        refrescar();
    }

    public void refrescar() {
        modeloLista.clear();
        for (Carta carta : SistemaImpl.getInstance().getColeccion()) {
            modeloLista.addElement(carta);
        }
    }

    
    private static class CartaCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                       boolean isSelected, boolean cellHasFocus) {
            // Obtenemos el componente base del JList (que es un JLabel)
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof Carta carta) {
                // Configuramos el texto estructurado: Nombre arriba, rareza abajo
                label.setText("<html><center>" + carta.getNombre() + "<br><font color='gray'>★ " + carta.getRareza() + "</font></center></html>");
                
                // Alineamos el texto para que quede abajo de la imagen de forma limpia
                label.setHorizontalTextPosition(JLabel.CENTER);
                label.setVerticalTextPosition(JLabel.BOTTOM);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                
                ImageIcon icono = ImagenUtil.obtenerIcono(carta.getNombre(), 90, 110);
                if (icono != null) {
                    label.setIcon(icono);
                } else {
                    label.setIcon(null); // Fallback de seguridad
                }
                
                // Margen acolchado para evitar que las cartas se peguen entre sí en la cuadrícula
                label.setBorder(BorderFactory.createEmptyBorder(10, 12, 10, 12));
            }
            return label;
        }
    }
}
package gui;
import dominio.Carta;
import factory.CartaFactory;
import logica.SistemaImpl;

import javax.swing.*;
import java.awt.*;

public class PanelAdministracion extends JPanel {

    private final Runnable onCambio;

    private final JTextField campoNombre = new JTextField();
    private final JTextField campoRareza = new JTextField();
    private final JComboBox<String> comboTipo = new JComboBox<>(CartaTipoUtil.TIPOS);
    
    private final JTextField campoAtributo1 = new JTextField();
    private final JTextField campoAtributo2 = new JTextField();

    private final DefaultListModel<Carta> modeloExistentes = new DefaultListModel<>();
    private final JList<Carta> listaExistentes = new JList<>(modeloExistentes);

    public PanelAdministracion(Runnable onCambio) {
        this.onCambio = onCambio;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Formulario a la izquierda
        JPanel panelForm = new JPanel(new GridLayout(9, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos de la Carta"));
        
        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(campoNombre);
        panelForm.add(new JLabel("Rareza (1-5):"));
        panelForm.add(campoRareza);
        panelForm.add(new JLabel("Tipo:"));
        panelForm.add(comboTipo);
        panelForm.add(new JLabel("Atributo 1 (Daño/Bonif/Efecto/Elem):"));
        panelForm.add(campoAtributo1);
        panelForm.add(new JLabel("Atributo 2 (Solo Energías Pokémon):"));
        panelForm.add(campoAtributo2);

        // Botones CRUD básicos
        JButton btnAgregar = new JButton("Agregar Carta");
        JButton btnModificar = new JButton("Modificar Seleccionada");
        JButton btnEliminar = new JButton("Eliminar Seleccionada");
        JButton btnLimpiar = new JButton("Limpiar");

        panelForm.add(btnAgregar);
        panelForm.add(btnModificar);
        panelForm.add(btnEliminar);
        panelForm.add(btnLimpiar);

        add(panelForm, BorderLayout.WEST);

        // Lista de cartas existentes al centro
        JPanel panelLista = new JPanel(new BorderLayout(5, 5));
        panelLista.add(new JLabel("Colección en memoria (Selecciona para Modificar/Eliminar):"), BorderLayout.NORTH);
        listaExistentes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaExistentes.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarSeleccion();
        });
        panelLista.add(new JScrollPane(listaExistentes), BorderLayout.CENTER);
        
        add(panelLista, BorderLayout.CENTER);

        // Listeners 
        btnAgregar.addActionListener(e -> agregarCarta());
        btnModificar.addActionListener(e -> modificarCarta());
        btnEliminar.addActionListener(e -> eliminarCarta());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        refrescarLista();
    }

    private void cargarSeleccion() {
        Carta sel = listaExistentes.getSelectedValue();
        if (sel == null) return;

        campoNombre.setText(sel.getNombre());
        campoRareza.setText(String.valueOf(sel.getRareza()));
        String tipo = CartaTipoUtil.tipoDe(sel);
        comboTipo.setSelectedItem(tipo);

        // Deshabilitar campos base como exige el enunciado al modificar
        campoNombre.setEnabled(false);
        campoRareza.setEnabled(false);
        comboTipo.setEnabled(false);

        String[] valores = CartaTipoUtil.valoresAtributos(sel);
        campoAtributo1.setText(valores.length > 0 ? valores[0] : "");
        campoAtributo2.setText(valores.length > 1 ? valores[1] : "");
    }

    private void agregarCarta() {
        try {
            String nombre = campoNombre.getText().trim();
            int rareza = Integer.parseInt(campoRareza.getText().trim());
            String tipo = (String) comboTipo.getSelectedItem();
            
            if (nombre.isEmpty()) throw new IllegalArgumentException("El nombre es obligatorio.");

            String atributos = campoAtributo1.getText().trim();
            if (tipo.equals("Pokemon")) {
                atributos += ";" + campoAtributo2.getText().trim();
            }

            String linea = nombre + ";" + rareza + ";" + tipo + ";" + atributos;
            Carta nueva = CartaFactory.crearCarta(linea);
            SistemaImpl.getInstance().agregarCarta(nueva);

            refrescarLista();
            limpiarFormulario();
            onCambio.run();
            JOptionPane.showMessageDialog(this, "Carta agregada exitosamente.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error en los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarCarta() {
        int indice = listaExistentes.getSelectedIndex();
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una carta primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Carta sel = listaExistentes.getSelectedValue();
            String[] nuevosAtributos = comboTipo.getSelectedItem().equals("Pokemon") 
                    ? new String[]{campoAtributo1.getText().trim(), campoAtributo2.getText().trim()}
                    : new String[]{campoAtributo1.getText().trim()};

            SistemaImpl.getInstance().modificarCarta(indice, nuevosAtributos);
            refrescarLista();
            limpiarFormulario();
            onCambio.run();
            JOptionPane.showMessageDialog(this, "Carta modificada.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCarta() {
        int indice = listaExistentes.getSelectedIndex();
        if (indice < 0) return;
        
        int conf = JOptionPane.showConfirmDialog(this, "¿Eliminar seleccionada?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (conf == JOptionPane.YES_OPTION) {
            SistemaImpl.getInstance().eliminarCarta(indice);
            refrescarLista();
            limpiarFormulario();
            onCambio.run();
        }
    }

    private void limpiarFormulario() {
        listaExistentes.clearSelection();
        campoNombre.setText("");
        campoRareza.setText("");
        campoAtributo1.setText("");
        campoAtributo2.setText("");
        campoNombre.setEnabled(true);
        campoRareza.setEnabled(true);
        comboTipo.setEnabled(true);
    }

    public void refrescarLista() {
        modeloExistentes.clear();
        for (Carta c : SistemaImpl.getInstance().getColeccion()) {
            modeloExistentes.addElement(c);
        }
    }
}
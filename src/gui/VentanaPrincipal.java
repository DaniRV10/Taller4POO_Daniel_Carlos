package gui;

import javax.swing.*;



public class VentanaPrincipal extends JFrame{
	
	 public VentanaPrincipal() {
		 	setTitle("Pokemon TCG");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(1000, 700);
	        setLocationRelativeTo(null);

	        PanelColeccion panelColeccion = new PanelColeccion();
	        PanelAdministracion panelAdministracion = new PanelAdministracion(panelColeccion::refrescar);

	        JTabbedPane tabs = new JTabbedPane();
	        tabs.addTab("Administracion", panelAdministracion);
	        tabs.addTab("Ver Coleccion", panelColeccion);

	        tabs.addChangeListener(e -> {
	            if (tabs.getSelectedComponent() == panelAdministracion) {
	                panelAdministracion.refrescarLista();
	                
	            }
	        });

	        add(tabs);
	    }

}
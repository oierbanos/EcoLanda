package sistema_pantallas.panel_inicial;

import sistema_pantallas.gestion_pantallas.Gestor;
import styles.ImageFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Panel principal de la aplicación.
 */
public class PanelPrincipal extends JScrollPane {

    /**
     * Anchura de la barra que divide la pantalla en dos.
     */
    public static final int SPLIT_WIDTH = 10; // pixels

    /**
     * Panel dinamico, contendrá el panel con las funciones seleccionadas.
     */
    JScrollPane mainPanel;

    /**
     * Panel que contendra el menu de navegación y que servirá para contener las distinitas funciones
     * de la aplicación.
     * @param width Ancho de la pantalla.
     */
    public PanelPrincipal(JScrollPane mainPanel, int width) {
        super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JSplitPane panel = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            true,
            crearPanelNavegacion(),
            mainPanel
        );

        // Cambiar parametros de la barra de división
        panel.setDividerLocation(width/12); // Posición en la pantalla
        panel.setDividerSize(SPLIT_WIDTH);

        // Quitar el borde
        this.setBorder(null);

        // Añadir paneles
        this.setViewportView(panel);
    }

    /**
     * Crear el panel de navegación que servirá para pasar de una pantalla a otra.
     * @return new JPanel- El panel de navegación.
     */
    private Component crearPanelNavegacion() {
        JPanel panel = new JPanel(new GridLayout(4, 1));

        panel.add(createButton(
                ImageFactory.createImageIcon(ImageFactory.IMAGEN_HUERTO),
                Gestor.GESTIONAR_HUERTO)
        );
        panel.add(createButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_STOCK),
                Gestor.GESTIONAR_STOCK)
        );
        panel.add(createButton(
                ImageFactory.createImageIcon(ImageFactory.IMAGEN_SENSOR),
                Gestor.GESTIONAR_SENSORES)
        );
        panel.add(createButton(
                ImageFactory.createImageIcon(ImageFactory.IMAGEN_USUARIO),
                Gestor.GESTIONAR_USER)
        );
        return panel;
    }

    private JButton createButton(ImageIcon icon, String funcion) {
        JButton boton = new JButton();      // Crear boton

        boton.setIcon(icon);                // Añadir Icono
        boton.setBorder(null);              // Quitar borde

        switch(funcion) {
            case "huerto": boton.addActionListener((e)->{ changePanel(Gestor.crearPanelGestionHuerto()); });
                break;
            case "stock": boton.addActionListener((e)->{ changePanel(Gestor.crearPanelGestionStock()); });
                break;
            case "sensor": boton.addActionListener((e)->{ changePanel(Gestor.crearPanelGestionSensores()); });
                break;
            case "usuario": boton.addActionListener((e)->{ changePanel(Gestor.crearPanelGestionUser()); });
                break;
        }
        return boton;
    }

    public void changePanel(JScrollPane panel) {
        mainPanel.setViewportView(panel);
        this.repaint();
    }
}
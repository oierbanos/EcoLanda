package sistema_pantallas.panel_inicial;

import external_conexion.database.QuerySelector;
import sistema_pantallas.gestion_pantallas.Gestor;
import sistema_pantallas.login.users.Usuario;
import styles.ImageFactory;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Panel principal de la aplicación.
 */
public class PanelPrincipal extends JScrollPane implements PropertyChangeListener {

    /**
     * Anchura de la barra que divide la pantalla en dos.
     */
    public static final int SPLIT_WIDTH = 10; // pixels

    /**
     * Panel dinamico, contendrá el panel con las funciones seleccionadas.
     */
    JScrollPane mainPanel;

    /**
     * Componente donde se muestran los mensajes de error.
     */
    JFrame parentComponent;

    /**
     * El usuario con el que inicia sesion.
     */
    Usuario user;

    /**
     * Conexion con la base de datos.
     */
    QuerySelector conector;

    /**
     * Panel que contendra el menu de navegación y que servirá para contener las distinitas funciones
     * de la aplicación.
     * @param parentComponent Panel donde se muestran los mensajes de error.
     * @param mainPanel Panel principal de la aplicacion.
     * @param user Usuario que ha iniciado sesion.
     * @param conector Conexion con la base de datos.
     * @param width Ancho de la pantalla.
     */
    public PanelPrincipal(JFrame parentComponent, JScrollPane mainPanel, Usuario user, QuerySelector conector, int width) {
        super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JSplitPane panel = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            true,
            crearPanelNavegacion(),
            mainPanel
        );
        this.parentComponent = parentComponent;
        this.mainPanel = mainPanel;
        this.conector = conector;
        this.user = user;

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

    /**
     * Crear un boton a partir de una imagen.
     * @param icon Imagen del boton.
     * @param funcion Funcion que tendra el boton.
     * @return Un nuevo boton.
     */
    private JButton createButton(ImageIcon icon, String funcion) {
        JButton boton = new JButton();      // Crear boton

        boton.setIcon(icon);                // Añadir Icono
        boton.setBorder(null);              // Quitar borde

        switch(funcion) {
            case "huerto": boton.addActionListener((e)->changePanel(Gestor.crearPanelGestionHuerto(this)));
                break;
            case "stock": boton.addActionListener((e)->changePanel(Gestor.crearPanelGestionStock(parentComponent, user, conector)));
                break;
            case "sensor": boton.addActionListener((e)->changePanel(Gestor.crearPanelGestionSensores(parentComponent, user, conector)));
                break;
            case "usuario": boton.addActionListener((e)->changePanel(Gestor.crearPanelGestionUser()));
                break;
        }
        return boton;
    }

    /**
     * Realizar un cambio de panel.
     * @param panel Panel al que se quiere cambiar.
     */
    public void changePanel(JScrollPane panel) {
        mainPanel.setViewportView(panel);
        this.repaint();
    }

    /**
     * Repintar el panel cuando ocurre un evento.
     * @param evt Evento que ha ocurrido.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.repaint();
    }
}

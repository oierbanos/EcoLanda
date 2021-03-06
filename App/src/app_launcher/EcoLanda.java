package app_launcher;

import external_conexion.database.QuerySelector;
import sistema_pantallas.login.PantallaLogin;
import sistema_pantallas.login.users.Usuario;
import sistema_pantallas.pantallas_acciones.PanelInicio;
import styles.ImageFactory;
import sistema_pantallas.panel_inicial.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Clase de inicio.
 */
public class EcoLanda extends JFrame implements PropertyChangeListener {

    /**  Ancho por defecto de la pantalla */
    public final static int DEFAULT_WIDTH = 1000;
    /** Alto por defecto de la pantalla */
    public final static int DEFAULT_HEIGHT = 600;

    /**
     * Ancho de pantalla.
     */
    int width;
    /**
     * Alto de pantalla.
     */
    int height;

    /**
     * Panel donde se mostrará el contenido de la aplicación.
     */
    JScrollPane mainSubpanel;

    /**
     * Pantalla de inicio de sesión.
     */
    PantallaLogin pantallaLogin;

    /**
     * Conexión a la base de datos
     */
    QuerySelector conector;

    /**
     * Usuario que ha iniciado sesión.
     */
    Usuario usuario;

    /**
     * Clase principal
     */
    public EcoLanda() {
        // Titulo de la aplicación
        super("EcoLanda");

        // Crear un conector con la base de datos
        this.conector = new QuerySelector();

        // Determinar tamaño de la pantalla
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        width = (int) toolkit.getScreenSize().getWidth();       // Ancho
        height = (int) toolkit.getScreenSize().getHeight();     // Alto

        // Poner icono a la aplicación
        this.setIconImage(ImageFactory.createImage(ImageFactory.MAIN_ICON));

        // Colocar pantalla en el centro
        this.setLocation(width/2 - DEFAULT_WIDTH/2, height/2 - DEFAULT_HEIGHT/2);
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // Crear panel de inicio
        mainSubpanel = new PanelInicio();

        // Cambiar el color del fondo
        this.setBackground(Color.white);

        // La pantalla ocupa toda la ventana
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Si se cierra la ventana se cierra la app
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pantallaLogin = new PantallaLogin(conector, this);
        pantallaLogin.setVisible(true);
    }

    /**
     * Crear el panel que contendra la aplicacion.
     */
    public void crearPanel() {
        // Crear contenido
        PanelPrincipal panelPrincipal = new PanelPrincipal(this, mainSubpanel, usuario, conector, width);

        this.setContentPane(panelPrincipal);                // Panel
        this.setJMenuBar(new BarraMenu(panelPrincipal));    // Menu
    }

    /**
     * Evento que gestiona si se ha iniciado sesión con un usuario y de ser
     * asi arranca la aplicación.
     * @param evt Evento que ha ocurrido.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        usuario = (Usuario) evt.getNewValue();
        pantallaLogin.dispose();    // Quitar la pantalla de login.

        if (usuario != null || evt.getPropertyName().equals("Login")) {
            this.crearPanel();
            this.setVisible(true);      // Mostrar la pantalla principal.
        }
        else {
            this.dispose();     // Quitar la pantalla principal.
        }
    }

    public static void main(String[] args) {
        try {
            // Hacer que la aplicación utilice el GUI del SO.
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        // Lanzador de la aplicacion.
        EcoLanda app = new EcoLanda();
    }
}

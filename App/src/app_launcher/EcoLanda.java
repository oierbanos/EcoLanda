package app_launcher;

import sistema_pantallas.pantallas_acciones.PanelInicio;
import styles.ImageFactory;
import sistema_pantallas.panel_inicial.*;

import javax.swing.*;
import java.awt.*;

public class EcoLanda extends JFrame {

    /**  Ancho por defecto de la pantalla */
    public final static int DEFAULT_WIDTH = 1000;
    /** Alto por defecto de la pantalla */
    public final static int DEFAULT_HEIGHT = 600;

    JScrollPane mainSubpanel;

    /**
     * Clase principal
     */
    public EcoLanda() {
        // Titulo de la aplicación
        super("EcoLanda");

        // Determinar tamaño de la pantalla
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int width = (int) toolkit.getScreenSize().getWidth();       // Ancho
        int height = (int) toolkit.getScreenSize().getHeight();     // Alto

        // Poner icono a la aplicación
        this.setIconImage(ImageFactory.createImage(ImageFactory.MAIN_ICON));

        // Colocar pantalla en el centro
        this.setLocation(width/2 - DEFAULT_WIDTH/2, height/2 - DEFAULT_HEIGHT/2);
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // Crear panel de inicio
        mainSubpanel = new PanelInicio();

        // Crear contenido
        PanelPrincipal panelPrincipal = new PanelPrincipal(mainSubpanel, width);

        this.setContentPane(panelPrincipal);
        this.setJMenuBar(new BarraMenu(panelPrincipal));

        // Cambiar el color del fondo
        this.setBackground(Color.white);

        // La pantalla ocupa toda la ventana
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Si se cierra la ventana se cierra la app
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        EcoLanda app = new EcoLanda();
        app.setVisible(true);
    }
}

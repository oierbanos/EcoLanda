package sistema_pantallas.gestion_pantallas.stock;

import styles.ColorFactory;
import styles.FontFactory;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AddStock extends JDialog {

    /**
     * Ancho de pantalla predefinido.
     */
    public final static int DEFAULT_WIDTH = 300;
    /**
     * Alto de pantalla predefinido.
     */
    public final static int DEFAULT_HEIGHT = 400;

    /**
     * Gestor del stock.
     */
    GestorStock gestor;
    /**
     * Lista de tipos.
     */
    JComboBox<String> tipos;

    /**
     * Parametro para introducir cantidad.
     */
    JTextField cantidad_plantada, cantidad_recogida;
    /**
     * Parametro para introducir fecha.
     */
    JTextField fecha_plantada, fecha_recogida;

    /**
     * Crear un nuevo panel para añadir elementos al stock.
     * @param parentComponent Panel donde el dialogo se muestra.
     * @param tiutlo Titulo del dialogo.
     * @param modo Especificar si se desea que el dialogo bloquee la pantalla.
     * @param gestor Gestor del stock.
     */
    public AddStock(JFrame parentComponent, String tiutlo, boolean modo, GestorStock gestor) {
        super(parentComponent, tiutlo, modo);
        this.gestor = gestor;

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int width = (int) toolkit.getScreenSize().getWidth();
        int height = (int) toolkit.getScreenSize().getHeight();

        this.setLocation(width/2 - DEFAULT_WIDTH/2, height/2 - DEFAULT_HEIGHT/2);
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

        this.setContentPane(crearPanelVentana());
        this.setBackground(ColorFactory.BACKGROUND_COLOR);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    /**
     * Ventana principal.
     * @return El panel de la ventana principal.
     */
    private Container crearPanelVentana() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        panel.add(crearPanelSeleccionTipo(), BorderLayout.NORTH);
        panel.add(panelInput(), BorderLayout.CENTER);
        panel.add(panelBotones(), BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Zona donde se añadiran los valores del lote.
     * @return Panel de input.
     */
    private Component panelInput() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        panel.add(crearPanelCantidad());
        panel.add(crearPanelFecha());

        return panel;
    }

    /**
     * Panel que permite seleccionar el tipo de lote.
     * @return Panel de seleccion de tipo.
     */
    private Component crearPanelSeleccionTipo() {
        JPanel panel = new JPanel();
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        try {
            tipos = new JComboBox<>(gestor.getTipos());

            tipos.setFont(FontFactory.NORMAL_BUTTON);
            tipos.setPreferredSize(new Dimension(250, 40));

            panel.add(tipos);
        }
        catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return panel;
    }

    /**
     * Panel para añdir la cantidad.
     * @return Panel de cantidad.
     */
    private Component crearPanelCantidad() {
        JPanel panel = new JPanel();
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        JLabel label = new JLabel("Cantidad");
        label.setFont(FontFactory.NORMAL_BUTTON);

        panel.add(label);
        panel.add(panelCantidadPlantada());
        panel.add(panelCantidadRecogida());

        return panel;
    }

    /**
     * Panel para añadir la cantidad plantada.
     * @return Panel de cantidad plantada.
     */
    private Component panelCantidadPlantada() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Plantada");
        cantidad_plantada = new JTextField();

        crearComponentes(label, cantidad_plantada);
        panel.add(cantidad_plantada, BorderLayout.CENTER);
        panel.add(label, BorderLayout.WEST);

        return panel;
    }

    /**
     * Panel para añadir la cantidad recogida.
     * @return Panel de cantidad recogida.
     */
    private Component panelCantidadRecogida() {
        JPanel panel = new JPanel(new BorderLayout(0, 100));

        JLabel label = new JLabel("Recogida");
        cantidad_recogida = new JTextField();

        crearComponentes(label, cantidad_recogida);
        panel.add(cantidad_recogida, BorderLayout.CENTER);
        panel.add(label, BorderLayout.WEST);

        return panel;
    }

    /**
     * Panel para añadir la fecha.
     * @return Panel con las fechas.
     */
    private Component crearPanelFecha() {
        JPanel panel = new JPanel();
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        JLabel label = new JLabel("Fecha (yyyy-mm-dd)");
        label.setFont(FontFactory.NORMAL_BUTTON);

        panel.add(label);
        panel.add(panelFechaPlantada());
        panel.add(panelFechaRecogida());

        return panel;
    }

    /**
     * Panel para añadir la fecha de cuando se planto.
     * @return Panel de fecha plantar.
     */
    private Component panelFechaPlantada() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Plantada");
        fecha_plantada = new JTextField();

        crearComponentes(label, fecha_plantada);
        panel.add(fecha_plantada, BorderLayout.CENTER);
        panel.add(label, BorderLayout.WEST);

        return panel;
    }

    /**
     * Panel para añadir la fecha de cuando se recogio.
     * @return Panel fecha recoger.
     */
    private Component panelFechaRecogida() {
        JPanel panel = new JPanel(new BorderLayout(0, 100));

        JLabel label = new JLabel("Recogida");
        fecha_recogida = new JTextField();

        crearComponentes(label, fecha_recogida);
        panel.add(fecha_recogida, BorderLayout.CENTER);
        panel.add(label, BorderLayout.WEST);

        return panel;
    }

    /**
     * Modificar un JLabel y un JTextField. Cambiar la dimension, la fuente y el borde
     * de los componentes recibidos.
     * @param label JLabel que se quiere modificar.
     * @param field JTextField que se queire modificar.
     */
    private void crearComponentes(JLabel label, JTextField field) {
        label.setFont(FontFactory.NORMAL_BUTTON);
        label.setPreferredSize(new Dimension(100, 40));
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(10, 10, 5, 10)
        ));

        field.setPreferredSize(new Dimension(160, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black),
                BorderFactory.createEmptyBorder(5, 10, 10, 10)
        ));
        field.setFont(FontFactory.NORMAL_BUTTON);
    }

    /**
     * Crear el panel con los botones de aceptar y cancelar.
     * @return Panel con los botones.
     */
    private Component panelBotones() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        JButton boton = crearBoton("Aceptar");
        boton.addActionListener(e -> {
            System.out.println("Aceptar");
            AddStock.this.dispose();
        });

        JPanel pBoton = new JPanel();
        pBoton.add(boton);
        panel.add(pBoton);

        boton = crearBoton("Cancelar");
        boton.addActionListener(e -> AddStock.this.dispose());

        pBoton = new JPanel();
        pBoton.add(boton);
        panel.add(pBoton);

        return panel;
    }

    /**
     * Crear un boton.
     * @param title Texto que contendra el boton.
     * @return Un nuevo boton.
     */
    private JButton crearBoton(String title) {
        JButton boton = new JButton(title);

        boton.setPreferredSize(new Dimension(125, 40));
        boton.setFont(FontFactory.NORMAL_BUTTON);

        return boton;
    }
}
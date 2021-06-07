package sistema_pantallas.pantallas_acciones;

import Elementos.DatoSensor;
import external_conexion.database.QuerySelector;
import external_conexion.sockets.ClientSocket;
import sistema_pantallas.gestion_pantallas.sensores.GestorSensor;
import sistema_pantallas.tablas.HeaderRenderer;
import sistema_pantallas.tablas.ModeloColumnas;
import sistema_pantallas.tablas.tabla_sensor.ModeloTablaSensores;
import sistema_pantallas.tablas.tabla_sensor.RendererSensor;
import styles.ColorFactory;
import styles.FontFactory;
import styles.ImageFactory;
import styles.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Objects;

public class PanelSensores  extends JScrollPane implements PropertyChangeListener {

    /**
     * Panel que muestra los mensajes de error.
     */
    JFrame parentComponent;
    /**
     * Gestor de los sensores.
     */
    GestorSensor gestor;

    /**
     * Tabla con el contenido.
     */
    JTable tabla;
    /**
     * Modelo de la tabla, se encarga de gestionar el contenido.
     */
    ModeloTablaSensores modeloTabla;

    /**
     * Filtro.
     */
    JComboBox<String> sensor, valor, hora;
    /**
     * Muestra de datos.
     */
    JLabel temperatura, humedad;
    /**
     * Lista con los datos obtenidos.
     */
    List<DatoSensor> listaDatos;

    /**
     * Crear una nueva instancia del panel de gestion de sensores.
     * @param parentComponent Componente que muestra los mensajes de error.
     * @param conector Conexion con la base de datos.
     * @param huerto_id Numero identificador del huerto.
     */
    public PanelSensores(JFrame parentComponent, QuerySelector conector, int huerto_id) {
        super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.getVerticalScrollBar().setUnitIncrement(20);

        this.parentComponent = parentComponent;
        this.gestor = new GestorSensor(huerto_id, conector);
        this.listaDatos = gestor.getDataFromDatabase();
        this.modeloTabla = new ModeloTablaSensores(listaDatos);

        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setBackground(ColorFactory.BACKGROUND_COLOR);

        this.setViewportView(crearPanelVentana());
    }

    /**
     * Crear panel principal.
     * @return Un nuevo panel principal.
     */
    private Component crearPanelVentana() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        panel.add(crearPanelDisplay(), BorderLayout.CENTER);
        panel.add(crearPanelSensores(), BorderLayout.EAST);

        return panel;
    }

    /**
     * Crear el panel de display de datos.
     * @return Un nuevo panel de display de datos.
     */
    private Component crearPanelDisplay() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        panel.add(crearPanelFiltros(), BorderLayout.NORTH);
        panel.add(crearPanelTabla(), BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crear un nuevo panel de filtros.
     * @return Un nuevo panel con los filtros.
     */
    private Component crearPanelFiltros() {
        JPanel panel = new JPanel(new GridLayout(1, 4));
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        sensor = new JComboBox<>(gestor.getListaTipos(listaDatos));
        sensor.setFont(FontFactory.BASE_FONT);
        sensor.setSelectedIndex(0);
        panel.add(sensor);

        valor = new JComboBox<>(gestor.getRangoCantidad(listaDatos));
        valor.setFont(FontFactory.BASE_FONT);
        valor.setSelectedIndex(0);
        panel.add(valor);

        hora = new JComboBox<>(gestor.getHora(listaDatos));
        hora.setFont(FontFactory.BASE_FONT);
        hora.setSelectedIndex(0);
        panel.add(hora);

        sensor.addActionListener(e -> {
            // Filtrar al seleccionar una de las opciones.
            modeloTabla.setLista(gestor.filtrar("tipo", (String) Objects.requireNonNull(sensor.getSelectedItem())));
            tabla.repaint();
        });
        valor.addActionListener(e -> {
            // Filtrar al seleccionar una de las opciones.
            modeloTabla.setLista(gestor.filtrar("cantidad", (String) Objects.requireNonNull(valor.getSelectedItem())));
            tabla.repaint();
        });
        hora.addActionListener(e -> {
            // Filtrar al seleccionar una de las opciones.
            modeloTabla.setLista(gestor.filtrar("hora", (String) Objects.requireNonNull(hora.getSelectedItem())));
            tabla.repaint();
        });

        return panel;
    }

    /**
     * Crear un nuevo panel con la tabla.
     * @return Panel con la tabla.
     */
    private Component crearPanelTabla() {
        JScrollPane panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        tabla = new JTable(modeloTabla,
                new ModeloColumnas(
                        new RendererSensor(),
                        new String[]{ "Tipo", "Valor", "Fecha" }
                ));
        tabla.getTableHeader().setDefaultRenderer(new HeaderRenderer(tabla));

        tabla.getTableHeader().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // Detectar en que columna se ha seleccionado para filtrar mediante esa columna.
                switch (tabla.columnAtPoint(e.getPoint())) {
                    case 0: modeloTabla.filtrar("tipo"); break;
                    case 1: modeloTabla.filtrar("valor"); break;
                    case 2: modeloTabla.filtrar("hora"); break;
                }
                tabla.repaint();
            }
        });
        panel.getViewport().setBackground(Color.WHITE);
        panel.setViewportView(tabla);

        return panel;
    }

    /**
     * Crear panel de sensores.
     * @return Un nuevo panel para mostrar los sensores.
     */
    private Component crearPanelSensores() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        panel.add(crearPanelFecha(), BorderLayout.NORTH);
        panel.add(crearPanelValorSensores(), BorderLayout.CENTER);
        panel.add(crearPanelBoton(), BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crear panel de input de la fecha.
     * @return Nuevo panel de input de fecha.
     */
    private Component crearPanelFecha() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel label = new JLabel("Fecha");
        label.setFont(FontFactory.NORMAL_BUTTON);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        panel.add(label, BorderLayout.WEST);

        JTextField field = new JTextField();
        label.setFont(FontFactory.NORMAL_BUTTON);
        panel.add(field, BorderLayout.CENTER);

        JButton boton = new JButton(ImageFactory.createImageIcon(ImageFactory.IMAGEN_BUSCAR));
        boton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        boton.setPreferredSize(new Dimension(30, 30));
        boton.setBackground(Color.white);
        boton.addActionListener(e -> System.out.println("Filtrar"));
        panel.add(boton, BorderLayout.EAST);

        return panel;
    }

    /**
     * Crear panel que muestra los valores actuales de los sensores.
     * @return Un nuevo panel de muestra de datos de sensores.
     */
    private Component crearPanelValorSensores() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        JPanel pSensor = new JPanel(new BorderLayout());
        pSensor.add(crearLabel(ImageFactory.createImageIcon(ImageFactory.IMAGEN_TEMPERATURA)), BorderLayout.CENTER);
        temperatura = crearLabel(gestor.getValorSensorMasReciente("temp") + "ºC");
        pSensor.add(temperatura, BorderLayout.SOUTH);
        panel.add(pSensor);

        pSensor = new JPanel(new BorderLayout());
        pSensor.add(crearLabel(ImageFactory.createImageIcon(ImageFactory.IMAGEN_HUMEDAD)), BorderLayout.CENTER);
        humedad = crearLabel(gestor.getValorSensorMasReciente("hum") + "%");
        pSensor.add(humedad, BorderLayout.SOUTH);
        panel.add(pSensor);

        new ClientSocket("Actualizar sensores", parentComponent, this, false).start();

        return panel;
    }

    /**
     * Crear un nuevo JLabel con un icono.
     * @param icon El icono que tendra el JLabel.
     * @return Un nuevo JLabel.
     */
    private JLabel crearLabel(ImageIcon icon) {
        return new JLabel(icon);
    }

    /**
     * Crear un nuevo JLabel con un titulo.
     * @param title El titulo que tendra el JLabel.
     * @return Un nuevo JLabel.
     */
    private JLabel crearLabel(String title) {
        JLabel label = new JLabel(title);

        label.setFont(FontFactory.NORMAL_BUTTON);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 5, 10),
                new RoundedBorder(10)
        ));
        label.setSize(new Dimension(50, 30));

        return label;
    }

    /**
     * Crear el paenl qeu tendra el boton.
     * @return Un nuevo panel con el boton.
     */
    private Component crearPanelBoton() {
        JPanel panel = new JPanel();
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        JButton boton = new JButton("Actualizar Valores");
        boton.addActionListener(e -> new ClientSocket("Actualizar sensores", parentComponent, this, true).start());

        boton.setFont(FontFactory.NORMAL_BUTTON);
        boton.setPreferredSize(new Dimension(200, 40));

        panel.add(boton);

        return panel;
    }

    /**
     * Realizar una accion al ocurrir un evento.
     * @param evt El evento que ha ocurrido.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String value = ((String) evt.getNewValue());

        if (value != null) {
            String[] values = value.split("/");

            if (values.length == 2) {
                temperatura.setText(values[0].substring(0, values[0].length() - 1) + "ºC");
                humedad.setText(values[1] + "%");

                if ((boolean) evt.getOldValue()) {
                    gestor.addDataToDatabase(values[0].substring(0, values[0].length() - 1), "temp");
                    gestor.addDataToDatabase(values[1], "hum");
                }
            }
        }
    }
}

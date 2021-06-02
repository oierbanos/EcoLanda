package sistema_pantallas.pantallas_acciones;

import Elementos.DatoSensor;
import external_conexion.database.Query_Selector;
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

    JFrame parentComponent;
    GestorSensor gestor;

    JTable tabla;
    ModeloTablaSensores modeloTabla;

    JComboBox<String> sensor, valor, hora;
    JLabel temperatura, humedad;
    List<DatoSensor> listaDatos;

    public PanelSensores(JFrame parentComponent, Query_Selector conector, int huerto_id) {
        super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.getVerticalScrollBar().setUnitIncrement(20);

        this.parentComponent = parentComponent;
        this.gestor = new GestorSensor(huerto_id, conector);
        this.listaDatos = gestor.getDataFromDatabase();
        this.modeloTabla = new ModeloTablaSensores(listaDatos);
        new ClientSocket("Actualizar datos", parentComponent, this).start();

        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setBackground(ColorFactory.BACKGROUND_COLOR);

        this.setViewportView(crearPanelVentana());
    }

    private Component crearPanelVentana() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        panel.add(crearPanelDisplay(), BorderLayout.CENTER);
        panel.add(crearPanelSensores(), BorderLayout.EAST);

        return panel;
    }

    private Component crearPanelDisplay() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        panel.add(crearPanelFiltros(), BorderLayout.NORTH);
        panel.add(crearPanelTabla(), BorderLayout.CENTER);

        return panel;
    }

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
            modeloTabla.setLista(gestor.filtrar("tipo", (String) Objects.requireNonNull(sensor.getSelectedItem())));
            tabla.repaint();
        });
        valor.addActionListener(e -> {
            modeloTabla.setLista(gestor.filtrar("cantidad", (String) Objects.requireNonNull(valor.getSelectedItem())));
            tabla.repaint();
        });
        hora.addActionListener(e -> {
            modeloTabla.setLista(gestor.filtrar("hora", (String) Objects.requireNonNull(hora.getSelectedItem())));
            tabla.repaint();
        });

        return panel;
    }

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

    private Component crearPanelSensores() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        panel.add(crearPanelFecha(), BorderLayout.NORTH);
        panel.add(crearPanelValorSensores(), BorderLayout.CENTER);
        panel.add(crearPanelBoton(), BorderLayout.SOUTH);

        return panel;
    }

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

        return panel;
    }

    private Component crearLabel(ImageIcon icon) {
        return new JLabel(icon);
    }

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

    private Component crearPanelBoton() {
        JPanel panel = new JPanel();
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        JButton boton = new JButton("Actualizar Valores");
        boton.addActionListener(e -> System.out.println("Holi"));

        boton.setFont(FontFactory.NORMAL_BUTTON);
        boton.setPreferredSize(new Dimension(200, 40));

        panel.add(boton);

        return panel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String[] values = ((String) evt.getNewValue()).split("/");

        if (values.length == 2) {
            temperatura.setText(values[0] + "ºC");
            humedad.setText(values[1] + "%");
        }
    }
}

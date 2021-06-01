package sistema_pantallas.pantallas_acciones;

import sistema_pantallas.tablas.HeaderRenderer;
import sistema_pantallas.tablas.ModeloColumnas;
import sistema_pantallas.tablas.tabla_stock.ModeloTablaStock;
import sistema_pantallas.tablas.tabla_stock.RendererStock;
import styles.ColorFactory;
import styles.FontFactory;
import styles.ImageFactory;
import styles.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelSensores  extends JScrollPane {

    JFrame parentComponent;

    JTable tabla;
    ModeloTablaStock modeloTabla;

    JComboBox<String> sensor, valor, hora;

    public PanelSensores(JFrame parentComponent, int huerto_id) {
        super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.getVerticalScrollBar().setUnitIncrement(20);

        this.parentComponent = parentComponent;

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

        sensor = new JComboBox<>();
        sensor.setFont(FontFactory.BASE_FONT);
        // sensor.setSelectedIndex(0);
        panel.add(sensor);

        valor = new JComboBox<>();
        valor.setFont(FontFactory.BASE_FONT);
        // valor.setSelectedIndex(0);
        panel.add(valor);

        hora = new JComboBox<>();
        hora.setFont(FontFactory.BASE_FONT);
        // hora.setSelectedIndex(0);
        panel.add(hora);

        sensor.addActionListener(e -> System.out.println("a"));
        valor.addActionListener(e -> System.out.println("b"));
        hora.addActionListener(e -> System.out.println("c"));

        return panel;
    }

    private Component crearPanelTabla() {
        JScrollPane panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        tabla = new JTable(modeloTabla,
                new ModeloColumnas(
                        new RendererStock(),
                        new String[]{ "Tipo", "Cantidad Plantada", "Cantidad Recogida", "Fecha Plantado", "Fecha Recogido" }
                ));
        tabla.getTableHeader().setDefaultRenderer(new HeaderRenderer(tabla));

        tabla.getTableHeader().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                switch (tabla.columnAtPoint(e.getPoint())) {
                    case 0: modeloTabla.filtrar("tipo"); break;
                    case 1: modeloTabla.filtrar("cantidadP"); break;
                    case 2: modeloTabla.filtrar("cantidadR"); break;
                    case 3: modeloTabla.filtrar("fechaP"); break;
                    case 4: modeloTabla.filtrar("fechaR"); break;
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

        return panel;
    }

    private Component crearPanelValorSensores() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        JPanel pSensor = new JPanel(new BorderLayout());
        pSensor.add(crearLabel(ImageFactory.createImageIcon(ImageFactory.IMAGEN_TEMPERATURA)), BorderLayout.CENTER);
        pSensor.add(crearLabel("21ºC"), BorderLayout.SOUTH);
        panel.add(pSensor);

        pSensor = new JPanel(new BorderLayout());
        pSensor.add(crearLabel(ImageFactory.createImageIcon(ImageFactory.IMAGEN_HUMEDAD)), BorderLayout.CENTER);
        pSensor.add(crearLabel("56%"), BorderLayout.SOUTH);
        panel.add(pSensor);

        return panel;
    }

    private Component crearLabel(ImageIcon icon) {
        return new JLabel(icon);
    }

    private Component crearLabel(String title) {
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
}

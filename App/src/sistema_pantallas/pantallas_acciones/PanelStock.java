package sistema_pantallas.pantallas_acciones;

import Elementos.LoteProducto;
import external_conexion.database.QuerySelector;
import sistema_pantallas.gestion_pantallas.stock.GestorStock;
import sistema_pantallas.tablas.HeaderRenderer;
import sistema_pantallas.tablas.ModeloColumnas;
import sistema_pantallas.tablas.tabla_stock.ModeloTablaStock;
import sistema_pantallas.tablas.tabla_stock.RendererStock;
import styles.ColorFactory;
import styles.FontFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

public class PanelStock  extends JScrollPane {

    /**
     * Seleccion de filtros.
     */
    JComboBox<String> tipo, cantidadPlantado, cantidadRecogido, fechaPlantado, fechaRecogido;
    /**
     * Panel donde se muestran los mensajes de error.
     */
    JFrame parentComponent;

    /**
     * Tabla con el contenido.
     */
    JTable tabla;
    /**
     * Gestor del stock.
     */
    GestorStock gestor;
    /**
     * Modelo de la tabla que gestiona el contenido que se muestra.
     */
    ModeloTablaStock modeloTabla;

    /**
     * Una lista de los lotes de productos.
     */
    List<LoteProducto> loteProductos;

    /**
     * Una nueva instancia de un panel de stock.
     * @param parentComponent Panel donde se muestran los mensajes de error.
     * @param huerto_id Numero identificador del huerto.
     * @param conector Conector con la base de datos.
     */
    public PanelStock(JFrame parentComponent, int huerto_id, QuerySelector conector) {
        super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.getVerticalScrollBar().setUnitIncrement(20);

        this.parentComponent = parentComponent;
        gestor = new GestorStock(huerto_id, conector);
        loteProductos = gestor.getStockFromDatabase();
        modeloTabla = new ModeloTablaStock(loteProductos);

        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setBackground(ColorFactory.BACKGROUND_COLOR);

        this.setViewportView(crearPanelVentana());
    }

    /**
     * Crear un panel principal de la ventana.
     * @return Un nuevo panel principal.
     */
    private Component crearPanelVentana() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        panel.add(crearPanelDisplay(), BorderLayout.CENTER);
        panel.add(crearPanelBoton(), BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crear un nuevo panel de display.
     * @return Un nuevo panel de display.
     */
    private Component crearPanelDisplay() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        panel.add(crearPanelFiltros(), BorderLayout.NORTH);
        panel.add(crearPanelTabla(), BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crear el panel para la tabla.
     * @return Un nuevo panel para la tabla.
     */
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
                // Detectar en que columna se ha pulsado para filtrar mediante ese parametro.
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

    /**
     * Crear un nuevo panel con los filtros.
     * @return Un nuevo panel con los filtros.
     */
    private Component crearPanelFiltros() {
        JPanel panel = new JPanel(new GridLayout(1, 4));
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        tipo = new JComboBox<>(gestor.getListaTipos(loteProductos));
        tipo.setFont(FontFactory.BASE_FONT);
        tipo.setSelectedIndex(0);
        panel.add(tipo);

        cantidadPlantado = new JComboBox<>(gestor.getRangoCantidadPlantar(loteProductos));
        cantidadPlantado.setFont(FontFactory.BASE_FONT);
        cantidadPlantado.setSelectedIndex(0);
        panel.add(cantidadPlantado);

        cantidadRecogido = new JComboBox<>(gestor.getRangoCantidadRecoger(loteProductos));
        cantidadRecogido.setFont(FontFactory.BASE_FONT);
        cantidadRecogido.setSelectedIndex(0);
        panel.add(cantidadRecogido);

        fechaPlantado = new JComboBox<>(gestor.getRangoFechaPlantar(loteProductos));
        fechaPlantado.setFont(FontFactory.BASE_FONT);
        fechaPlantado.setSelectedIndex(0);
        panel.add(fechaPlantado);

        fechaRecogido = new JComboBox<>(gestor.getRangoFechaRecoger(loteProductos));
        fechaRecogido.setFont(FontFactory.BASE_FONT);
        fechaRecogido.setSelectedIndex(0);
        panel.add(fechaRecogido);

        tipo.addActionListener(e -> {
            // Filtrar al seleccionar una de las opciones.
            modeloTabla.setLista(gestor.filtrar("tipo", (String) Objects.requireNonNull(tipo.getSelectedItem())));
            tabla.repaint();
        });
        cantidadPlantado.addActionListener(e -> {
            // Filtrar al seleccionar una de las opciones.
            modeloTabla.setLista(gestor.filtrar("cantidadP", (String) Objects.requireNonNull(cantidadPlantado.getSelectedItem())));
            tabla.repaint();
        });;
        cantidadRecogido.addActionListener(e -> {
            // Filtrar al seleccionar una de las opciones.
            modeloTabla.setLista(gestor.filtrar("cantidadR", (String) Objects.requireNonNull(cantidadRecogido.getSelectedItem())));
            tabla.repaint();
        });
        fechaPlantado.addActionListener(e -> {
            // Filtrar al seleccionar una de las opciones.
            modeloTabla.setLista(gestor.filtrar("fechaP", (String) Objects.requireNonNull(fechaPlantado.getSelectedItem())));
            tabla.repaint();
        });
        fechaRecogido.addActionListener(e -> {
            // Filtrar al seleccionar una de las opciones.
            modeloTabla.setLista(gestor.filtrar("fechaR", (String) Objects.requireNonNull(fechaRecogido.getSelectedItem())));
            tabla.repaint();
        });
        return panel;
    }

    /**
     * Crear un nuevo panel con los botones.
     * @return Un nuevo panel con el boton.
     */
    private Component crearPanelBoton() {
        JPanel panel = new JPanel();
        panel.setBackground(ColorFactory.BACKGROUND_COLOR);

        JButton boton = new JButton("Añadir Stock");
        boton.addActionListener(e -> gestor.addStock(parentComponent));

        boton.setFont(FontFactory.NORMAL_BUTTON);
        boton.setPreferredSize(new Dimension(200, 40));

        panel.add(boton);

        return panel;
    }
}

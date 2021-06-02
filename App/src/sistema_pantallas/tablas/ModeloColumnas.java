package sistema_pantallas.tablas;

import sistema_pantallas.tablas.tabla_stock.RendererStock;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ModeloColumnas extends DefaultTableColumnModel {

    private static final long serialVersionUID = 1L;

    DefaultTableCellRenderer renderer;

    // Dividimos la tabla en cuatro columnas: Tipo, Cantidad, Procedencia y Fecha.
    public ModeloColumnas(DefaultTableCellRenderer renderer, String[] titles) {
        super();
        this.renderer = renderer;

        for (int i = 0; i < titles.length; i++) {
            this.addColumn(crearColumna(titles[i], i, 250));
        }
    }

    private TableColumn crearColumna(String title, int i, int j) {
        TableColumn columna = new TableColumn(i, j);

        columna.setHeaderValue(title);
        columna.setCellRenderer(renderer);

        return columna;
    }
}
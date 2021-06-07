package sistema_pantallas.tablas;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * Modelo de las columnas de una tabla.
 */
public class ModeloColumnas extends DefaultTableColumnModel {

    /**
     * Renderer de la tabla.
     */
    DefaultTableCellRenderer renderer;

    /**
     * Modelo de las columnas.
     * @param renderer Renderizador de la tabla.
     * @param titles Titulos de la tabla.
     */
    public ModeloColumnas(DefaultTableCellRenderer renderer, String[] titles) {
        super();
        this.renderer = renderer;

        // Crear las columnas.
        for (int i = 0; i < titles.length; i++) {
            this.addColumn(crearColumna(titles[i], i, 250));
        }
    }

    /**
     * Crear una nueva columna para la tabla.
     * @param title Titulo de la columna.
     * @param i Numero de columna.
     * @param j Anchura de la columna.
     * @return Una nueva columna.
     */
    private TableColumn crearColumna(String title, int i, int j) {
        TableColumn columna = new TableColumn(i, j);

        columna.setHeaderValue(title);
        columna.setCellRenderer(renderer);

        return columna;
    }
}
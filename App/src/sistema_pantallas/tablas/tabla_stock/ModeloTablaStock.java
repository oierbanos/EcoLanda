package sistema_pantallas.tablas.tabla_stock;

import Elementos.LoteProducto;

import javax.swing.table.AbstractTableModel;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ModeloTablaStock  extends AbstractTableModel {

    /**
     * Titulos de las columnas.
     */
    private static final String[] NOMBRE_COLUMNAS = {
            "Tipo", "Cantidad Plantada", "Cantidad Recogida", "Fecha Plantada", "Fecha Recogida"
    };

    /**
     * Lista con los prodcutos.
     */
    List<LoteProducto> listaProductos;

    /**
     * Una nueva instancia del modelo de la tabla de stock.
     * @param listaProductos Lista con los productos.
     */
    public ModeloTablaStock(List<LoteProducto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    /**
     * Cambiar la lista de elementos que mostramos en caso de haber cambios en estos.
     * @param listaProductos Lista con los productos.
     */
    public void setLista(List<LoteProducto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    /**
     * Filtrar los datos de la tabla mediante un parametro.
     * @param filtro Filtro que hay que realizar.
     */
    public void filtrar(String filtro) {
        switch (filtro) {
            case "tipo":
                setLista(listaProductos.stream().sorted(Comparator.comparing(LoteProducto::getNombreTipo)).collect(Collectors.toList()));
                break;
            case "cantidadP":
                setLista(listaProductos.stream().sorted(Comparator.comparing(LoteProducto::getCantidadPlantada)).collect(Collectors.toList()));
                break;
            case "cantidadR":
                setLista(listaProductos.stream().sorted(Comparator.comparing(LoteProducto::getCantidadRecogida)).collect(Collectors.toList()));
                break;
            case "fechaP":
                setLista(listaProductos.stream().sorted(Comparator.comparing(LoteProducto::getFechaPlantado)).collect(Collectors.toList()));
                break;
            case "fechaR":
                setLista(listaProductos.stream().sorted(Comparator.comparing(LoteProducto::getFechaRecogido)).collect(Collectors.toList()));
                break;
        }
    }

    @Override
    public String getColumnName(int column) {
        return NOMBRE_COLUMNAS[column];
    }

    @Override
    public int getColumnCount() {
        return NOMBRE_COLUMNAS.length;
    }

    @Override
    public int getRowCount() {
        return listaProductos.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
        LoteProducto p = listaProductos.get(row);
        return p.getFieldAt(column);
    }
}
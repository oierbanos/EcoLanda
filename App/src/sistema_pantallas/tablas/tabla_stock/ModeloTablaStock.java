package sistema_pantallas.tablas.tabla_stock;

import Elementos.LoteProducto;

import javax.swing.table.AbstractTableModel;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ModeloTablaStock  extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private static final String[] NOMBRE_COLUMNAS = {
            "Tipo", "Cantidad Plantada", "Cantidad Recogida", "Fecha Plantada", "Fecha Recogida"
    };

    List<LoteProducto> listaProductos;

    public ModeloTablaStock(List<LoteProducto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    // Cambiar la lista de elementos que mostramos en caso de haber cambios en estos.
    public void setLista(List<LoteProducto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    public void filtrar(String filtro) {
        switch (filtro) {
            case "tipo":
                setLista(listaProductos.stream().sorted(Comparator.comparing(LoteProducto::getNombreTipo)).collect(Collectors.toList()));
                break;
            case "cantidadP":
                setLista(listaProductos.stream().sorted(Comparator.comparing(LoteProducto::getCantidad_plantada)).collect(Collectors.toList()));
                break;
            case "cantidadR":
                setLista(listaProductos.stream().sorted(Comparator.comparing(LoteProducto::getCantidad_recogida)).collect(Collectors.toList()));
                break;
            case "fechaP":
                setLista(listaProductos.stream().sorted(Comparator.comparing(LoteProducto::getFecha_plantado)).collect(Collectors.toList()));
                break;
            case "fechaR":
                setLista(listaProductos.stream().sorted(Comparator.comparing(LoteProducto::getFecha_recogido)).collect(Collectors.toList()));
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
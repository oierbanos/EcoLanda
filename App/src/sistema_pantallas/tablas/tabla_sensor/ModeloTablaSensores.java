package sistema_pantallas.tablas.tabla_sensor;

import Elementos.DatoSensor;

import javax.swing.table.AbstractTableModel;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Modelo de tabla de los sensores.
 */
public class ModeloTablaSensores extends AbstractTableModel {

    /**
     * Nombre de las columnas.
     */
    private static final String[] NOMBRE_COLUMNAS = { "Tipo", "Valor", "Hora" };

    /**
     * Lista con los datos.
     */
    List<DatoSensor> listaDatos;

    /**
     * Modelo de la tabla de sensores.
     * @param listaProductos
     */
    public ModeloTablaSensores(List<DatoSensor> listaProductos) {
        this.listaDatos = listaProductos;
    }

    /**
     * Cambiar la lista de elementos que mostramos en caso de haber cambios en estos.
     * @param listaProductos La lista con los productos.
     */
    public void setLista(List<DatoSensor> listaProductos) {
        this.listaDatos = listaProductos;
    }

    /**
     * Filtrar los datos de la tabla mediante un parametro.
     * @param filtro Filtro que hay que realizar.
     */
    public void filtrar(String filtro) {
        switch (filtro) {
            case "tipo":
                setLista(listaDatos.stream().sorted(Comparator.comparing(DatoSensor::getTipo)).collect(Collectors.toList()));
                break;
            case "valor":
                setLista(listaDatos.stream().sorted(Comparator.comparing(DatoSensor::getValor)).collect(Collectors.toList()));
                break;
            case "hora":
                setLista(listaDatos.stream().sorted(Comparator.comparing(DatoSensor::getTiempoString)).collect(Collectors.toList()));
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
        return listaDatos.size();
    }

    @Override
    public Object getValueAt(int row, int column) {
        DatoSensor p = listaDatos.get(row);
        return p.getFieldAt(column);
    }
}
package sistema_pantallas.gestion_pantallas.stock;

import Elementos.LoteProducto;
import external_conexion.database.QuerySelector;
import external_conexion.database.QueryType;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gestor del stock.
 */
public class GestorStock {

    /**
     * Conector con la base de datos.
     */
    QuerySelector conector;
    /**
     * Numero identificador del huerto.
     */
    int huertoId;

    /**
     * Crear una nueva instancia de un gestor de stock.
     * @param huertoId Numero identificador del huerto.
     * @param conector Conector con la base de datos.
     */
    public GestorStock(int huertoId, QuerySelector conector) {
        this.huertoId = huertoId;
        this.conector = conector;
    }

    /**
     * Añadir stock a la base de datos.
     * @param parentComponent Panel donde se mostraran los mensajes de error.
     */
    public void addStock(JFrame parentComponent) {
        AddStock dialog = new AddStock(parentComponent, "Añadir Stock", true, this);
        dialog.setVisible(true);
    }

    /**
     * Obtener el stock de productos de la base de datos.
     * @return Una lista de lotes de productos.
     */
    public List<LoteProducto> getStockFromDatabase() {
        ResultSet set = conector.selectQuery(QueryType.GET_STOCK,
                                                conector.createArgumentList(String.valueOf(huertoId)));
        return crearListaLote(set);
    }

    /**
     * Recibir los tipos de productos que se pueden guardar en la base de datos.
     * @return Array de tipos en string.
     * @throws SQLException Excepcion generada cuando hay algun error de conexion con la base de datos.
     */
    public String[] getTipos() throws SQLException {
        List<String> values = new ArrayList<>();
        ResultSet set = conector.selectQuery(QueryType.GET_TIPOS, null);

        while (set.next()) {
            values.add(set.getString("nombre"));
        }
        return  values.toArray(new String[0]);
    }

    /**
     * Obtener la lista de tipos que hay en una lista de lotes.
     * @param productos Lista de lotes de productos.
     * @return Un array con los tipos de producto de la lista.
     */
    public String[] getListaTipos(List<LoteProducto> productos) {
        List<String> values = new ArrayList<>();

        for (LoteProducto lote : productos) {
            if (!values.contains(lote.getTipo().toString())) {
                values.add(lote.getTipo().toString());
            }
        }
        values = values.stream().sorted().collect(Collectors.toList());
        values.add(0, "Todo");

        return values.toArray(values.toArray(new String[0]));
    }

    /**
     * Obtener el rango de cantidades plantadas.
     * @param productos Lista de lotes de productos plantados.
     * @return Un array de string con los rangos.
     */
    public String[] getRangoCantidadPlantar(List<LoteProducto> productos) {
        List<String> values = new ArrayList<>();
        int tmp;

        for (LoteProducto lote : productos) {
            tmp = lote.getCantidadPlantada()/10 * 10;

            if (!values.contains("+" + tmp)) {
                values.add("+" + tmp);
            }
        }
        values = values.stream().sorted().collect(Collectors.toList());
        values.add(0, "Todo");

        return values.toArray(values.toArray(new String[0]));
    }

    /**
     * Obtener el rango de cantidades recogidas.
     * @param productos Lista de lotes de productos recogidos.
     * @return Un array de string con los rangos.
     */
    public String[] getRangoCantidadRecoger(List<LoteProducto> productos) {
        List<String> values = new ArrayList<>();
        int tmp;

        for (LoteProducto lote : productos) {
            tmp = lote.getCantidadRecogida()/100 * 100;

            if (!values.contains("+" + tmp)) {
                values.add("+" + tmp);
            }
        }
        values = values.stream().sorted().collect(Collectors.toList());
        values.add(0, "Todo");

        return values.toArray(values.toArray(new String[0]));
    }

    /**
     * Obtener las fechas de cuando se plantaron los distintos lotes.
     * @param productos Lista de lotes plantados.
     * @return Array con las distintas fechas.
     */
    public String[] getRangoFechaPlantar(List<LoteProducto> productos) {
        List<String> values = new ArrayList<>();

        for (LoteProducto lote : productos) {
            if (!values.contains(lote.getFechaPlantado().toString())) {
                values.add(lote.getFechaPlantado().toString());
            }
        }
        values = values.stream().sorted().collect(Collectors.toList());
        values.add(0, "Todo");

        return values.toArray(values.toArray(new String[0]));
    }

    /**
     * Obtener las fechas de cuando se recogieron los distintos lotes.
     * @param productos Lista de lotes recogidos.
     * @return Array con las distintas fechas.
     */
    public String[] getRangoFechaRecoger(List<LoteProducto> productos) {
        List<String> values = new ArrayList<>();

        for (LoteProducto lote : productos) {
            if (!values.contains(lote.getFechaRecogido().toString())) {
                values.add(lote.getFechaRecogido().toString());
            }
        }
        values = values.stream().sorted().collect(Collectors.toList());
        values.add(0, "Todo");

        return values.toArray(values.toArray(new String[0]));
    }

    /**
     * Obtener una lista de lotes de la base de datos aplicando un filtro a la busqueda.
     * @param tipo El tipo de parametro por el que se va a filtrar.
     * @param startIndex El parametro de referencia para el filtro.
     * @return Una lista de lotes de productos filtrada.
     */
    public List<LoteProducto> filtrar(String tipo, String startIndex) {
        ResultSet set;

        if (startIndex.equals("Todo")) { // Si desea visualizar todos los elementos no hace falta filtrar.
            set = conector.selectQuery(QueryType.GET_STOCK, conector.createArgumentList(String.valueOf(huertoId)));
        }
        else {
            switch (tipo) {
                case "tipo": // Filtrar por tipos.
                    set = conector.selectQuery(QueryType.FILTRO_TIPO,
                            conector.createArgumentList(String.valueOf(huertoId), startIndex));
                    break;
                case "cantidadP": // Filtrar por cantidad de productos plantados.
                    set = conector.selectQuery(QueryType.FILTRO_CANTIDAD_PLANTADA,
                            conector.createArgumentList(String.valueOf(huertoId), startIndex));
                    break;
                case "cantidadR": // Filtrar por cantidad de productos recogidos.
                    set = conector.selectQuery(QueryType.FILTRO_CANTIDAD_RECOGIDA,
                            conector.createArgumentList(String.valueOf(huertoId), startIndex));
                    break;
                case "fechaP": // Filtrar por la fecha en la que se plantaron.
                    set = conector.selectQuery(QueryType.FILTRO_FECHA_PLANTAR,
                            conector.createArgumentList(String.valueOf(huertoId), startIndex));
                    break;
                case "fechaR": // Filtrar por la fecha en la que se recogieron.
                    set = conector.selectQuery(QueryType.FILTRO_FECHA_RECOGER,
                            conector.createArgumentList(String.valueOf(huertoId), startIndex));
                    break;
                default: set = null;
            }
        }
        return (set != null) ? crearListaLote(set) : null;
    }

    /**
     * Crear una lista de lotes a partir del resultado de una query.
     * @param set Resultado de la query.
     * @return Una lista de lotes de porductos.
     */
    private List<LoteProducto> crearListaLote(ResultSet set) {
        List<LoteProducto> lista = new ArrayList<>();
        while (true) {
            try {
                if (!set.next()) break;
                lista.add(new LoteProducto(
                        set.getInt("cantidad_plantada"),
                        set.getInt("cantidad_recogida"),
                        set.getDate("fecha_plantar"),
                        set.getDate("fecha_recoger"),
                        set.getString("nombre")
                ));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }
}

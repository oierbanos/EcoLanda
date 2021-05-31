package sistema_pantallas.gestion_pantallas.stock;

import Elementos.LoteProducto;
import external_conexion.database.Query_Selector;
import external_conexion.database.Query_Types;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorStock {

    Query_Selector conector;
    int huerto_id;

    public GestorStock(int huerto_id, Query_Selector conector) {
        this.huerto_id = huerto_id;
        this.conector = conector;
    }

    public void addStock(JFrame parentComponent) {
        AddStock dialog = new AddStock(parentComponent, "Añadir Stock", true);
        dialog.setVisible(true);
    }

    public List<LoteProducto> getStockFromDatabase() {
        ResultSet set = conector.select_query(Query_Types.GET_STOCK,
                                                conector.createArgumentList(String.valueOf(huerto_id)));
        return crearListaLote(set);
    }

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

    public String[] getRangoCantidadPlantar(List<LoteProducto> productos) {
        List<String> values = new ArrayList<>();
        int tmp;

        for (LoteProducto lote : productos) {
            tmp = lote.getCantidad_plantada()/10 * 10;

            if (!values.contains("+" + tmp)) {
                values.add("+" + tmp);
            }
        }
        values = values.stream().sorted().collect(Collectors.toList());
        values.add(0, "Todo");

        return values.toArray(values.toArray(new String[0]));
    }

    public String[] getRangoCantidadRecoger(List<LoteProducto> productos) {
        List<String> values = new ArrayList<>();
        int tmp;

        for (LoteProducto lote : productos) {
            tmp = lote.getCantidad_recogida()/100 * 100;

            if (!values.contains("+" + tmp)) {
                values.add("+" + tmp);
            }
        }
        values = values.stream().sorted().collect(Collectors.toList());
        values.add(0, "Todo");

        return values.toArray(values.toArray(new String[0]));
    }

    public String[] getRangoFechaPlantar(List<LoteProducto> productos) {
        List<String> values = new ArrayList<>();

        for (LoteProducto lote : productos) {
            if (!values.contains(lote.getFecha_plantado().toString())) {
                values.add(lote.getFecha_plantado().toString());
            }
        }
        values = values.stream().sorted().collect(Collectors.toList());
        values.add(0, "Todo");

        return values.toArray(values.toArray(new String[0]));
    }

    public String[] getRangoFechaRecoger(List<LoteProducto> productos) {
        List<String> values = new ArrayList<>();

        for (LoteProducto lote : productos) {
            if (!values.contains(lote.getFecha_recogido().toString())) {
                values.add(lote.getFecha_recogido().toString());
            }
        }
        values = values.stream().sorted().collect(Collectors.toList());
        values.add(0, "Todo");

        return values.toArray(values.toArray(new String[0]));
    }

    public List<LoteProducto> filtrar(String tipo, String startIndex) {
        ResultSet set;

        if (startIndex.equals("Todo")) {
            set = conector.select_query(Query_Types.GET_STOCK, conector.createArgumentList(String.valueOf(huerto_id)));
        }
        else {
            switch (tipo) {
                case "tipo":
                    set = conector.select_query(Query_Types.FILTRO_TIPO,
                            conector.createArgumentList(String.valueOf(huerto_id), startIndex));
                    break;
                case "cantidadP":
                    set = conector.select_query(Query_Types.FILTRO_CANTIDAD_PLANTADA,
                            conector.createArgumentList(String.valueOf(huerto_id), startIndex));
                    break;
                case "cantidadR":
                    set = conector.select_query(Query_Types.FILTRO_CANTIDAD_RECOGIDA,
                            conector.createArgumentList(String.valueOf(huerto_id), startIndex));
                    break;
                case "fechaP":
                    set = conector.select_query(Query_Types.FILTRO_FECHA_PLANTAR,
                            conector.createArgumentList(String.valueOf(huerto_id), startIndex));
                    break;
                case "fechaR":
                    set = conector.select_query(Query_Types.FILTRO_FECHA_RECOGER,
                            conector.createArgumentList(String.valueOf(huerto_id), startIndex));
                    break;
                default: set = null;
            }
        }
        return (set != null) ? crearListaLote(set) : null;
    }

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

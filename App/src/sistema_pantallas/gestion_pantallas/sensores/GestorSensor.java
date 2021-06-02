package sistema_pantallas.gestion_pantallas.sensores;

import Elementos.DatoSensor;
import Elementos.Time;
import errors.TimeException;
import external_conexion.database.Query_Selector;
import external_conexion.database.Query_Types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GestorSensor {

    Query_Selector conector;
    int huerto_id;

    public GestorSensor(int huerto_id, Query_Selector conector) {
        this.huerto_id = huerto_id;
        this.conector = conector;
    }

    public List<DatoSensor> getDataFromDatabase() {
        ResultSet set = conector.select_query(Query_Types.GET_SENSORES,
                                                conector.createArgumentList(String.valueOf(huerto_id)));
        return crearListaValores(set);
    }

    private List<DatoSensor> crearListaValores(ResultSet set) {
        List<DatoSensor> lista = new ArrayList<>();
        while (true) {
            try {
                if (!set.next()) break;
                String[] values = set.getString("hora").split(":");

                lista.add(new DatoSensor(
                        set.getString("nombre"),
                        set.getInt("valor"),
                        new Time(
                                set.getDate("fecha"),
                                Integer.parseInt(values[0]),
                                Integer.parseInt(values[1]),
                                Integer.parseInt(values[2])
                        )
                ));
            } catch (SQLException | TimeException e) {
                e.printStackTrace();
            }
        }
        return lista;
    }

    public String[] getListaTipos(List<DatoSensor> productos) {
        List<String> values = new ArrayList<>();

        for (DatoSensor lote : productos) {
            if (!values.contains(lote.getTipo())) {
                values.add(lote.getTipo());
            }
        }
        values = values.stream().sorted().collect(Collectors.toList());
        values.add(0, "Todo");

        return values.toArray(values.toArray(new String[0]));
    }

    public String[] getRangoCantidad(List<DatoSensor> productos) {
        List<String> values = new ArrayList<>();
        double tmp;

        for (DatoSensor lote : productos) {
            tmp = lote.getValor()/10 * 10;

            if (!values.contains("+" + tmp)) {
                values.add("+" + tmp);
            }
        }
        values = values.stream().sorted().collect(Collectors.toList());
        values.add(0, "Todo");

        return values.toArray(values.toArray(new String[0]));
    }

    public String[] getHora(List<DatoSensor> productos) {
        List<String> values = new ArrayList<>();

        for (DatoSensor lote : productos) {
            if (!values.contains(lote.getHora())) {
                values.add(lote.getHora());
            }
        }
        values = values.stream().sorted().collect(Collectors.toList());
        values.add(0, "Todo");

        return values.toArray(values.toArray(new String[0]));
    }

    public List<DatoSensor> filtrar(String filtro, String startIndex) {
        ResultSet set;

        if (startIndex.equals("Todo")) {
            set = conector.select_query(Query_Types.GET_SENSORES,
                    conector.createArgumentList(String.valueOf(huerto_id)));
        }
        else {
            switch (filtro) {
                case "tipo":
                    set = conector.select_query(Query_Types.FILTRO_TIPO_SENSOR,
                            conector.createArgumentList(String.valueOf(huerto_id), startIndex));
                    break;
                case "cantidad":
                    set = conector.select_query(Query_Types.FILTRO_VALOR,
                            conector.createArgumentList(String.valueOf(huerto_id), startIndex));
                    break;
                case "hora":
                    set = conector.select_query(Query_Types.FILTRO_HORA,
                            conector.createArgumentList(String.valueOf(huerto_id), startIndex));
                    break;
                case "fecha":
                    set = conector.select_query(Query_Types.FILTRO_FECHA,
                            conector.createArgumentList(String.valueOf(huerto_id), startIndex));
                    break;
                default:
                    set = null;
            }
        }
        return (set != null) ? crearListaValores(set) : null;
    }

    public String getValorSensorMasReciente(String tipo) {
        ResultSet set = null;

        switch (tipo) {
            case "temp": set = conector.select_query(Query_Types.RECENT,
                                                conector.createArgumentList(String.valueOf(huerto_id), "temperatura")); break;
            case "hum": set = conector.select_query(Query_Types.RECENT,
                                                conector.createArgumentList(String.valueOf(huerto_id), "humedad")); break;
        }
        try {
            if (set != null && set.next()) {
                return set.getString("valor");
            }
            return "";
        }
        catch (SQLException e) {
            return "";
        }
    }
}

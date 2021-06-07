package sistema_pantallas.gestion_pantallas.sensores;

import Elementos.DatoSensor;
import Elementos.Time;
import errors.TimeException;
import external_conexion.database.QuerySelector;
import external_conexion.database.QueryType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Gestor de los datos de los sensores.
 */
public class GestorSensor {

    /**
     * Conexion con la base de datos.
     */
    QuerySelector conector;
    /**
     * Identificador del huerto en la base de datos.
     */
    int huertoId;

    /**
     * Crear una nueva instancia de un Gestor de sensores.
     * @param huertoId Numero identificador del huerto del usuario.
     * @param conector Conexion con la base de datos.
     */
    public GestorSensor(int huertoId, QuerySelector conector) {
        this.huertoId = huertoId;
        this.conector = conector;
    }

    /**
     * Obtener datos de los sensores desde la base de datos.
     * @return Una lista con los datos.
     */
    public List<DatoSensor> getDataFromDatabase() {
        ResultSet set = conector.selectQuery(QueryType.GET_SENSORES,
                                                conector.createArgumentList(String.valueOf(huertoId)));
        return crearListaValores(set);
    }

    /**
     * Transformar un ResultSet en una lista de valores de DatoSensor.
     * @param set Un ResultSet obtenido tras realizar una query en la base de datos.
     * @return Una lista de datos de sensores.
     */
    private List<DatoSensor> crearListaValores(ResultSet set) {
        List<DatoSensor> lista = new ArrayList<>();
        while (true) {
            try {
                if (!set.next()) break; // Transformar todos los valores del ResultSet.
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

    /**
     * Obtener una lista de los tipos de productos que se estan mostrando al usuario.
     * @param datos Lista de datos que se estan mostrando al usuario.
     * @return Un array de string con los nombres de los tipos.
     */
    public String[] getListaTipos(List<DatoSensor> datos) {
        List<String> values = new ArrayList<>();

        for (DatoSensor dato : datos) {
            if (!values.contains(dato.getTipo())) {
                values.add(dato.getTipo());
            }
        }
        values = values.stream().sorted().collect(Collectors.toList());
        values.add(0, "Todo");

        return values.toArray(values.toArray(new String[0]));
    }

    /**
     * Obtener el rando en las cantidades de los datos de los sensores.
     * @param datos Lista de datos que se estan mostrando al usuario.
     * @return Un array de string con los rangos de las cantidades.
     */
    public String[] getRangoCantidad(List<DatoSensor> datos) {
        List<String> values = new ArrayList<>();
        int tmp;

        for (DatoSensor dato : datos) {
            tmp = (int)dato.getValor()/10 * 10;

            if (!values.contains("+" + tmp)) {
                values.add("+" + tmp);
            }
        }
        values = values.stream().sorted().collect(Collectors.toList());
        values.add(0, "Todo");

        return values.toArray(values.toArray(new String[0]));
    }

    /**
     * Obtener las distintas horas de los datos.
     * @param datos Lista de datos que se estan mostrando al usuario.
     * @return Un array de string con las distintas horas.
     */
    public String[] getHora(List<DatoSensor> datos) {
        List<String> values = new ArrayList<>();

        for (DatoSensor dato : datos) {
            if (!values.contains(dato.getHora())) {
                values.add(dato.getHora());
            }
        }
        values = values.stream().sorted().collect(Collectors.toList());
        values.add(0, "Todo");

        return values.toArray(values.toArray(new String[0]));
    }

    /**
     * Obtener una lista de datos de la base de datos aplicando un filtro al conjunto.
     * @param filtro El tipo de filtro que se va a aplicar.
     * @param startIndex La condicion por la que se va a filtrar.
     * @return Una nueva lista de datos de sensores.
     */
    public List<DatoSensor> filtrar(String filtro, String startIndex) {
        ResultSet set;

        if (startIndex.equals("Todo")) { // Si quiere visualizar todo no hace falta filtrar.
            set = conector.selectQuery(QueryType.GET_SENSORES,
                    conector.createArgumentList(String.valueOf(huertoId)));
        }
        else {
            switch (filtro) {
                case "tipo": // Filtrar por tipo.
                    set = conector.selectQuery(QueryType.FILTRO_TIPO_SENSOR,
                            conector.createArgumentList(String.valueOf(huertoId), startIndex));
                    break;
                case "cantidad": // Filtrar por cantidad.
                    set = conector.selectQuery(QueryType.FILTRO_VALOR,
                            conector.createArgumentList(String.valueOf(huertoId), startIndex));
                    break;
                case "hora": // Filtrar por horas.
                    set = conector.selectQuery(QueryType.FILTRO_HORA,
                            conector.createArgumentList(String.valueOf(huertoId), startIndex));
                    break;
                case "fecha": // Filtrar por fecha.
                    set = conector.selectQuery(QueryType.FILTRO_FECHA,
                            conector.createArgumentList(String.valueOf(huertoId), startIndex));
                    break;
                default:
                    set = null;
            }
        }
        return (set != null) ? crearListaValores(set) : null;
    }

    /**
     * Obtener el valor mas reciente de un tipo de sensor de la base de datos..
     * @param tipo El tipo de sensor.
     * @return El valor en string.
     */
    public String getValorSensorMasReciente(String tipo) {
        ResultSet set = null;

        switch (tipo) {
            // Valor del sensor de temperatura.
            case "temp": set = conector.selectQuery(QueryType.RECENT, conector.createArgumentList(String.valueOf(huertoId), "temperatura")); break;
            // Valor del sensor de humedad.
            case "hum": set = conector.selectQuery(QueryType.RECENT, conector.createArgumentList(String.valueOf(huertoId), "humedad")); break;
        }
        try {
            // Devolver el valor mas reciente.
            if (set != null && set.next()) {
                return set.getString("valor");
            }
            return "";
        }
        catch (SQLException e) {
            return "";
        }
    }

    /**
     * Añadir nuevos datos de un sensor a la base de datos.
     * @param type Tipo de sensor.
     * @param value Valor que se quiere añadir.
     */
    public void addDataToDatabase(String type, String value) {
        conector.insertData(type, value, huertoId);
    }
}

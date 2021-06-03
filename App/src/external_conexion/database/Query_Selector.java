package external_conexion.database;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Query_Selector {

    Database_Conector conector = null;

    public void setConexion(String username, String nombre, Component parentComponent) {
        conector = new Database_Conector();
        conector.conectar(username, nombre);
        conector.setParentComponent(parentComponent);
    }

    public ResultSet select_query(Query_Types type, List<String> values) {
        return conector.makeQuery(type.GetQuery(), values);
    }

    public void insertData(String type, String value, int huerto_id) {
        List<String> values = new ArrayList<>();

        try {
            if (type.equals("temp") || type.equals("hum")) {
                values.add(select_query(
                        Query_Types.HIGHEST_ID_MEDICION,
                        createArgumentList(String.valueOf(huerto_id))).getString("id")
                );
                values.add(value);
                values.add(String.valueOf(huerto_id));
                values.add((type.equals("temp")) ? "1" : "2");

                conector.insert(
                        "INSERT INTO medicion (id, fecha, hora, valor, huerto_id, tipo_id) VALUES\n" +
                                "(?, NOW(), NOW(), ?, ?, ?),", values);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T> List<String> transformValuesToString(List<T> values) {
        List<String> transformed_values = new ArrayList<>();

        for (T value : values) {
            transformed_values.add(value.toString());
        }
        return transformed_values;
    }

    public List<String> createArgumentList(String...args) {
        List<String> lista = new ArrayList<>();

        Collections.addAll(lista, args);
        return lista;
    }

    public boolean checkConexion() {
        return conector.getConnection() != null;
    }

    public void desconectar() {
        if (conector != null) {
            conector.desconectar();
        }
    }
}
package external_conexion.database;

import java.awt.*;
import java.sql.ResultSet;
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
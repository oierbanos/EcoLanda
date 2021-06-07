package external_conexion.database;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Encargado de realizar las querys.
 */
public class QuerySelector implements DAO {

    /**
     * Conector con la base de datos.
     */
    DatabaseConector conector = null;

    /**
     * Crear una conexion con la base de datos.
     * @param username Nombre de usuario.
     * @param password Contraseña del usuario.
     * @param parentComponent Panel para mostrar los mensajes de error.
     */
    public void setConexion(String username, String password, Component parentComponent) {
        conector = new DatabaseConector();
        conector.conectar(username, password);
        conector.setParentComponent(parentComponent);
    }

    /**
     * Realizar una query.
     * @param type Querys predefinidas.
     * @param values Valores que se van a añadir a la query.
     * @return Resultado de la query.
     */
    public ResultSet selectQuery(QueryType type, List<String> values) {
        return conector.makeQuery(type.getQuery(), values);
    }

    /**
     * Insertar datos de una medicion en la base de datos.
     * @param type Tipo de medicion que se va a insertar..
     * @param value Valor que se quiere insertar.
     * @param huerto_id Identificador del huerto.
     */
    public void insertData(String type, String value, int huerto_id) {
        List<String> values = new ArrayList<>();

        try {
            // Comprobar si el tipo es de temperatura o humedad.
            if (type.equals("temp") || type.equals("hum")) {
                // Crear un nuevo ID.
                values.add(String.valueOf(selectQuery(
                        QueryType.HIGHEST_ID_MEDICION,
                        null).getInt("id") + 1
                ));
                values.add(value);
                values.add(String.valueOf(huerto_id));
                // Obtener el tipo.
                values.add((type.equals("temp")) ? "1" : "2");

                // Realizar el insert.
                conector.insert(
                        "INSERT INTO medicion (id, fecha, hora, valor, huerto_id, tipo_id) VALUES\n" +
                                "(?, NOW(), NOW(), ?, ?, ?),", values);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pasar un conjunto de argumentos en string a lista de strings.
     * @param args Argumentos que se quieren poner en lista. Cantidad indefinida.
     * @return Lista de argumentos.
     */
    public List<String> createArgumentList(String...args) {
        List<String> lista = new ArrayList<>();

        Collections.addAll(lista, args);
        return lista;
    }

    /**
     * Comprobar si esta conectado a la base de datos.
     * @return Si esta conectado devuelve un true, sino devuelve un false.
     */
    public boolean checkConexion() {
        return conector.getConnection() != null;
    }

    /**
     * Desconectar de la base de datos.
     */
    public void desconectar() {
        if (conector != null) {
            conector.desconectar();
        }
    }
}
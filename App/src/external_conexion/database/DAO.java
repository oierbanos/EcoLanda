package external_conexion.database;

import java.sql.ResultSet;
import java.util.List;

/**
 * Data Access Object.
 */
public interface DAO {

    /**
     * Obtener datos.
     * @param type Tipo de datos que se quieren obtener.
     * @param values Lista de valores para la busqueda.
     * @return Datos obtenidos.
     */
    ResultSet selectQuery(QueryType type, List<String> values);

    /**
     * Guardar datos.
     * @param type Tipo de dato que se va a guardar.
     * @param value Valor que se va a guardar.
     * @param huerto_id Identificador del huerto.
     */
    void insertData(String type, String value, int huerto_id);

    /**
     * Crear lista de argumentos.
     * @param args Argumentos para la lista.
     * @return Lista con los valores.
     */
    List<String> createArgumentList(String...args);
}

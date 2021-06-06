package external_conexion.database;

import external_conexion.file_management.FileReader;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.List;

public class DatabaseConector {

    /**
     * Driver utilizado para realizar la conexion con la base de datos.
     */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    /**
     * Direccion IP de la base de datos.
     */
    private static String DATABASE_IP;
    /**
     * Puerto por el que se conecta a la base de datos.
     */
    private static String DATABASE_PORT;
    /**
     * Nombre de la base de datos a la que se va a conectar.
     */
    private static final String DATABASE_NAME = "EcoLanda";

    /**
     * Componente que van a bloquear las ventanas de error.
     */
    private Component parentComponent;

    /**
     * Conexion con la base de datos.
     */
    private Connection connection = null;

    /**
     * Crear un objeto de conexion con la base de datos. Al crear este objeto
     * se lee la direccion IP y el puerto para conectarse al servidor.
     */
    public DatabaseConector() {
        DATABASE_IP = FileReader.getDatabaseIP();
        DATABASE_PORT = FileReader.getDatabasePort();
    }

    /**
     * Iniciar la conexion con la base de datos.
     * @param username Usuario con el que se va a iniciar sesion.
     * @param password Contraseña con la que se va a iniciar sesion.
     */
    public void conectar(String username, String password) {
        String url = "jdbc:mysql://" + DATABASE_IP + ":" + DATABASE_PORT + "/" + DATABASE_NAME;

        try {
            // Conectar a la base de datos.
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (ClassNotFoundException | SQLException e) {
            // En el caso de no poder conectarse mostrar un mensaje de error.
            JOptionPane.showMessageDialog(parentComponent, "No se ha podido conectar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            connection = null;
        }
    }

    /**
     * Recibir el elemento de conexion con la base de datos.
     * @return Conexion a la base de datos.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Desconectar de la base de datos.
     */
    public void desconectar() {
        connection = null;
    }

    /**
     * Realizar una query en la base de datos.
     * @param query Query a realizar.
     * @param values Valores que se van a añadir al PreparedStatement.
     * @return Resultado de la query.
     */
    public ResultSet makeQuery(String query, List<String> values) {
        try {
            // Crear el PreparedStatement.
            PreparedStatement ps = connection.prepareStatement(query);

            // Añadir los valores al PreparedStatement.
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    ps.setString(i+1, values.get(i));
                }
            }
            // Ejecutar la query y devolver el resultado obtenido.
            return ps.executeQuery();
        }
        catch (SQLException e) {
            // Si no se puede realizar la query mostrar un mensaje de error.
            JOptionPane.showMessageDialog(parentComponent, "No se ha podido ejecutar la busqueda.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Realizar un insert de datos.
     * @param intert Instruccion para realizar el insert.
     * @param values Valores que se van a insertar.
     */
    public void insert(String intert, List<String> values) {
        try {
            // Crear el PreparedStatement
            PreparedStatement ps = connection.prepareStatement(intert);

            // Añadir los valores al PreparedStatement
            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    ps.setString(i+1, values.get(i));
                }
            }
            // Ejecutar el insert
            ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Especificar el componente donde se van a mostrar los mensajes de error.
     * @param parentComponent Nuevo panel en el que se van a mostrar.
     */
    public void setParentComponent(Component parentComponent) {
        this.parentComponent = parentComponent;
    }
}

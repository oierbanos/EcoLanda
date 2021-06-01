package external_conexion.database;

import external_conexion.file_management.FileReader;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.sql.*;
import java.util.List;

public class Database_Conector {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static String DATABASE_IP;

    private static String DATABASE_PORT;

    private static final String DATABASE_NAME = "EcoLanda";

    private Component parentComponent;
    private Connection connection = null;

    public Database_Conector() {
        DATABASE_IP = FileReader.getDatabaseIP();
        DATABASE_PORT = FileReader.getDatabasePort();
    }

    public void conectar(String username, String password) {
        String url = "jdbc:mysql://" + DATABASE_IP + ":" + DATABASE_PORT + "/" + DATABASE_NAME;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(url, username, password);
        }
        catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(parentComponent, "No se ha podido conectar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            connection = null;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void desconectar() {
        connection = null;
    }

    public ResultSet makeQuery(String query, List<String> values) {
        try {
            PreparedStatement ps = connection.prepareStatement(query);

            if (values != null) {
                for (int i = 0; i < values.size(); i++) {
                    ps.setString(i+1, values.get(i));
                }
            }
            return ps.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setParentComponent(Component parentComponent) {
        this.parentComponent = parentComponent;
    }
}

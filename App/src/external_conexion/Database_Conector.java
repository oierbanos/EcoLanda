package external_conexion;

import javax.swing.*;
import java.sql.*;
import java.util.List;

public class Database_Conector {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String DATABASE_IP = "127.0.0.1";

    private static final String DATABASE_PORT = "3306";

    private static final String DATABASE_NAME = "EcoLanda";

    private String username;
    private String password;

    private JPanel parentComponent;
    private Connection connection = null;

    public Database_Conector(JPanel parentComponent, String username, String password) {
        this.username = username;
        this.password = password;

        this.parentComponent = parentComponent;
    }

    public boolean conectar() {
        String url = "jdbc:mysql://" + DATABASE_IP + ":" + DATABASE_PORT + "/" + DATABASE_NAME;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(url, username, password);
            return true;
        }
        catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(parentComponent, "No se ha podido conectar a la base de datos",
                                          "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void desconectar() {
        connection = null;
    }

    public ResultSet makeQuery(String query, List<String> values) {
        try {
            PreparedStatement ps = connection.prepareStatement(query);

            for (int i = 0; i < values.size(); i++) {
                ps.setString(i+1, values.get(i));
            }
            return ps.executeQuery();
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

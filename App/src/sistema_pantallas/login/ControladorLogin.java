package sistema_pantallas.login;

import errors.UserException;
import external_conexion.database.Query_Selector;
import external_conexion.database.Query_Types;
import sistema_pantallas.login.users.Direccion;
import sistema_pantallas.login.users.Usuario;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorLogin {

    PropertyChangeSupport conector;
    Query_Selector conexion_database;

    public ControladorLogin(PropertyChangeListener listener, Query_Selector database_conector) {
        // Crear una conexion con el panel principal
        conector = new PropertyChangeSupport(this);
        conector.addPropertyChangeListener(listener);

        // Iniciar el conector
        this.conexion_database = database_conector;
    }

    public void iniciarSesion(Component parent, String evt, String username, String password) {
        try {
            conexion_database.setConexion(username, password, parent);
            Usuario user = null;

            if (evt.equals("Login") && conexion_database.checkConexion()) {
                ResultSet set = conexion_database.select_query(
                        Query_Types.GET_USER, conexion_database.createArgumentList(username)
                );
                while (set.next()) {
                    user = new Usuario(
                            set.getString("username"),
                            set.getString("nombre"),
                            set.getString("apellido"),
                            set.getString("correo"),
                            set.getString("telefono"),
                            new Direccion(
                                    set.getString("calle"),
                                    set.getString("piso"),
                                    set.getString("pueblo"),
                                    set.getString("codigo_postal")
                            ),
                            set.getInt("huerto_id")
                    );
                }
                launchEvent(evt, user);
            }
        }
        catch (SQLException er) {
            JOptionPane.showMessageDialog(parent, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (UserException er) {
            JOptionPane.showMessageDialog(parent, er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void launchEvent(String name, Usuario user) throws UserException {
        if (name.equals("Login")) {
            if (user != null) {
                conector.firePropertyChange(name, null, user);
            }
            else {
                throw new UserException("No se ha encontrado el usuario.");
            }
        }
        else {
            conector.firePropertyChange(name, null, null);
        }
    }
}

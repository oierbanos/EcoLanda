package sistema_pantallas.login;

import errors.UserException;
import external_conexion.database.QuerySelector;
import external_conexion.database.QueryType;
import sistema_pantallas.login.users.Direccion;
import sistema_pantallas.login.users.Usuario;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorLogin {

    /**
     * Encargado de notificar a los listener de los distintos cambios.
     */
    PropertyChangeSupport conector;
    /**
     * Conector con la base de datos.
     */
    QuerySelector conexionDatabase;

    /**
     * Crear una nueva instancia de un controlador de login.
     * @param listener Listener de los eventos.
     * @param database_conector Conexion a la base de datos.
     */
    public ControladorLogin(PropertyChangeListener listener, QuerySelector database_conector) {
        // Crear una conexion con el panel principal
        conector = new PropertyChangeSupport(this);
        conector.addPropertyChangeListener(listener);

        // Iniciar el conector
        this.conexionDatabase = database_conector;
    }

    /**
     * Iniciar sesion con un usuario en la base de datos y obtener los datos de este.
     * @param parent Panel donde se muestran los mensajes de error.
     * @param evt Evento que se va a realizar (Login/Cancel).
     * @param username Nombre de usuario.
     * @param password Contraseña.
     */
    public void iniciarSesion(Component parent, String evt, String username, String password) {
        if (evt.equals("Login")) {
            try {
                // Conectar con el usuario y su contraseña.
                conexionDatabase.setConexion(username, password, parent);
                Usuario user = null;

                // Comprobar si se ha podido conectar.
                if (conexionDatabase.checkConexion()) {
                    ResultSet set = conexionDatabase.selectQuery(
                            QueryType.GET_USER, conexionDatabase.createArgumentList(username)
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
            } catch (SQLException er) {
                JOptionPane.showMessageDialog(parent, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (UserException er) {
                JOptionPane.showMessageDialog(parent, er.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            try { launchEvent(evt, null); } catch (UserException e) { e.printStackTrace(); }
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

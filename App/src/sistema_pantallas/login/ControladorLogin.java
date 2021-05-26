package sistema_pantallas.login;

import errors.UserException;
import external_conexion.Database_Conector;
import sistema_pantallas.login.users.Usuario;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ControladorLogin {

    PropertyChangeSupport conector;
    Database_Conector database_conector;

    public ControladorLogin(PropertyChangeListener listener, Database_Conector database_conector) {
        // Crear una conexion con el panel principal
        conector = new PropertyChangeSupport(this);
        conector.addPropertyChangeListener(listener);

        // Iniciar el conector
        this.database_conector = database_conector;
    }

    public void iniciarSesion(Component parent, String evt, String username, String password) {
        try {
            Usuario user = null;

            if (evt.equals("Login") && database_conector.conectar(username, password)) {
                ResultSet set = database_conector.makeQuery("SELECT * FROM usuario WHERE username=?",
                                                                database_conector.createArgumentList(username));
                System.out.println(set);
            }
            launchEvent(evt, user);
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

package sistema_pantallas.login;

import errors.UserException;
import sistema_pantallas.login.users.Usuario;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ControladorLogin {

    PropertyChangeSupport conector;

    public ControladorLogin(PropertyChangeListener listener) {
        // Crear una conexion con el panel principal
        conector = new PropertyChangeSupport(this);
        conector.addPropertyChangeListener(listener);
    }

    public void iniciarSesion(Component parent, String evt, Usuario user) {
        try {
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

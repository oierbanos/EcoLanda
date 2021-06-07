import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Hilo de comunicacion con el cliente de sockets.
 */
public class SocketThread extends Thread implements PropertyChangeListener {

    /**
     * Listener de eventos.
     */
    protected PropertyChangeSupport conector;
    /**
     * Socket conectado.
     */
    protected Socket socket;

    /**
     * Crear un nuevo hilo de sockets.
     * @param clientSocket Cliente de sockets que se ha conectado.
     * @param listener Listener de los ventos.
     */
    public SocketThread(Socket clientSocket, PropertyChangeListener listener) {
        this.socket = clientSocket;
        this.conector = new PropertyChangeSupport(this);
        this.conector.addPropertyChangeListener(listener);
    }

    @Override
    public void run() {
        try {
            // Lector de datos por sockets.
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            try {
                String line = in.readLine(); // Leer informacion

                // Realizar eventos.
                if (line.equals("Actualizar sensores")) {
                    conector.firePropertyChange("Actualizar", null, null);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            if (!socket.isClosed()) {
                // Transmitir datos mediante sockets.
                OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
                String value = (String) evt.getNewValue();

                out.write(value);
                out.flush();
                socket.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package external_conexion.sockets;

import external_conexion.file_management.FileReader;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket extends Thread {

    /**
     * Puerto del servidor.
     */
    private static String PUERTO;
    /**
     * IP del servidor.
     */
    private static String SERVER_IP;

    /**
     * Identificar si hay que añadir los valores a la base de datos.
     */
    private boolean add;
    /**
     * Mensaje a transmitir por sockets.
     */
    private String mensaje;
    /**
     * Componente donde se van a mostrar los mensajes de error.
     */
    private Component parentComponent;
    /**
     * Listener, pendiente de si se reciben los valores por sockets.
     */
    private PropertyChangeSupport conector;

    /**
     * Conexion por sockets a la aplicacion de la placa. Este socket funciona como cliente, por lo que se puede
     * conectar y desconectar del servidor en cualquier momento.
     * @param mensaje Mensaje que se desea transmitir.
     * @param parentComponent Panel donde se mostraran los mensajes de error.
     * @param listener Clase que solicita la informacion.
     * @param addToDatabase Identificar si la informacion obtenida en el mensaje se debe publicar en la base de datos.
     */
    public ClientSocket(String mensaje, Component parentComponent, PropertyChangeListener listener, boolean addToDatabase) {
        this.mensaje = mensaje;
        this.parentComponent = parentComponent;
        this.add = addToDatabase;

        this.conector = new PropertyChangeSupport(this);
        this.conector.addPropertyChangeListener(listener);

        // Obtener la ip y el puerto del servidor del fichero.
        SERVER_IP = FileReader.getSocketIP();
        PUERTO = FileReader.getSocketPort();
    }

    /**
     * Enviar mensaje al servidor y esperar su respuesta. Lanza el mensaje recibido mediante propertyChange.
     * @param socket Conexion al servidor.
     * @throws IOException Si el sistema no es capaz de transmitir lanza una excepcion.
     */
    private void getInformation(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        writer.println(mensaje); // Escribir mensaje.
        conector.firePropertyChange(mensaje, add, reader.readLine()); // Leer mensaje y avisar al listener.
    }

    /**
     * Cuando se crea una conexion por sockets esta se activa en un hilo separado al hilo main.
     */
    @Override
    public void run() {
        try {
            // Crear conexion por sockets al servidor.
            Socket socket = new Socket(SERVER_IP, Integer.parseInt(PUERTO));
            getInformation(socket);
            socket.close();
        }
        catch (UnknownHostException e) { // El sistema no encuentra el servidor.
            JOptionPane.showMessageDialog(parentComponent, "No se nada del host " + SERVER_IP,
                                     "Error de conexion", JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException e) { // El sistema encuentra el servidor pero no puede conectarse.
            JOptionPane.showMessageDialog(parentComponent, "No he podido conectarme a " + SERVER_IP,
                                     "Error de conexion", JOptionPane.ERROR_MESSAGE);
        }
    }
}

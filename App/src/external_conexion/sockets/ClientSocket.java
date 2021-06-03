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

    private static String PUERTO;
    private static String SERVER_IP;

    private boolean add;
    private String mensaje;
    private Component parentComponent;
    private PropertyChangeSupport conector;

    public ClientSocket(String mensaje, Component parentComponent, PropertyChangeListener listener, boolean addToDatabase) {
        this.mensaje = mensaje;
        this.parentComponent = parentComponent;
        this.add = addToDatabase;

        this.conector = new PropertyChangeSupport(this);
        this.conector.addPropertyChangeListener(listener);

        SERVER_IP = FileReader.getSocketIP();
        PUERTO = FileReader.getSocketPort();
    }

    private void getInformation(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

        writer.println(mensaje);
        conector.firePropertyChange(mensaje, add, reader.readLine());
    }

    @Override
    public void run() {
        try {
            Socket socket = new Socket(SERVER_IP, Integer.parseInt(PUERTO));
            getInformation(socket);
            socket.close();
        }
        catch (UnknownHostException e) {
            JOptionPane.showMessageDialog(parentComponent, "No se nada del host " + SERVER_IP,
                                     "Error de conexion", JOptionPane.ERROR_MESSAGE);
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(parentComponent, "No he podido conectarme a " + SERVER_IP,
                                     "Error de conexion", JOptionPane.ERROR_MESSAGE);
        }
    }
}

package external_conexion.file_management;

import java.io.*;

/**
 * Lector de ficheros.
 */
public class FileReader {

    /**
     * Fichero con la configuracion.
     */
    private static final String CONFIG_FILE = "Files/Configuracion.txt";

    /**
     * Obtener un tipo de informacion de los ficheros.
     * @param id Identificador de una linea.
     * @return Informacion solicitada.
     */
    private static String getFileInformation(String id) {
        try {
            // Crear un lector de fichero.
            BufferedReader reader = new BufferedReader(new java.io.FileReader(CONFIG_FILE));
            String linea = reader.readLine();

            // Buscar la linea con el identificador definido.
            while(linea != null) {
                if (linea.contains(id)) {
                    // Si encuentra el valor devolverlo.
                    return linea.split("=")[1];
                }
                linea = reader.readLine();
            }
            // Si no lo encuentra devuelve null.
            return null;
        }
        catch (IOException e) {
            // Si no lo encuentra el archivo devuelve null.
            System.out.println("File not found");
            return null;
        }
    }

    /**
     * Obtener la direccion ip de la base de datos.
     * @return Direccion ip.
     */
    public static String getDatabaseIP() {
        return getFileInformation("Database_ip");
    }

    /**
     * Obtener el puerto al que conectarse de la base de datos.
     * @return Puerto de la base de datos.
     */
    public static String getDatabasePort() {
        return getFileInformation("Database_port");
    }

    /**
     * Obtener la direccion ip del servidor de sockets.
     * @return Direccion ip.
     */
    public static String getSocketIP() {
        return getFileInformation("Socket_ip");
    }

    /**
     * Obtener puerto de conexion por sockets.
     * @return Puerto de conexion por sockets.
     */
    public static String getSocketPort() {
        return getFileInformation("Socket_port");
    }
}

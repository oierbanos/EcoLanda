package external_conexion.file_management;

import java.io.*;

public class FileReader {

    private static final String CONFIG_FILE = "Files/Configuracion.txt";

    private static String getFileInformation(String id) {
        try {
            BufferedReader reader = new BufferedReader(new java.io.FileReader(CONFIG_FILE));
            String linea = reader.readLine();

            while(linea != null) {
                if (linea.contains(id)) {
                    return linea.split("=")[1];
                }
                linea = reader.readLine();
            }
            return null;
        }
        catch (IOException e) {
            System.out.println("File not found");
            return null;
        }
    }

    public static String getDatabaseIP() {
        return getFileInformation("Database_ip");
    }

    public static String getDatabasePort() {
        return getFileInformation("Database_port");
    }

    public static String getSocketIP() {
        return getFileInformation("Socket_ip");
    }

    public static String getSocketPort() {
        return getFileInformation("Socket_port");
    }
}

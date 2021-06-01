import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    private static final int PUERTO = 8888;

    public void administrarConexiones() throws IOException {
        ServerSocket server;
        Socket socket;

        server = new ServerSocket(PUERTO);

        new KeyboardListener().start();
        System.out.println("############################################");
        System.out.println("               SERVER RUNNING               ");
        System.out.println("############################################");

        while (true) {
            socket = server.accept();
            new EchoThread(socket).start();
        }
    }

    public static class KeyboardListener extends Thread {

        @Override
        public void run() {
            Scanner teclado = new Scanner(System.in);

            do {
                if (teclado.nextLine().equalsIgnoreCase("quit")) {
                    System.out.println("Closing server...");
                    teclado.close();

                    System.exit(0);
                }
            } while (true);
        }
    }

    public static void main(String[] args) throws IOException {
        Main server = new Main();
        server.administrarConexiones();
    }
}

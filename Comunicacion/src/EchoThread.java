import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class EchoThread extends Thread {

    protected Socket socket;

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    @Override
    public void run() {
        try {
            InputStream inp = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inp));
            OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());

            try {
                String line = in.readLine();
                System.out.println(line);

                if (line.equals("Actualizar sensores")) {
                    out.write("Operacion aceptada\n");
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                socket.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
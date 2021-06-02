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

            String line;
            while (true) {
                try {
                    line = in.readLine();
                    if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                        socket.close();
                        return;
                    }
                    else {
                        System.out.println(line);
                        out.write("Operacion aceptada\n");
                        out.flush();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
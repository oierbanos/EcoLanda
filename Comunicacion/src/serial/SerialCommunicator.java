package serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SerialCommunicator implements SerialPortDataListener {

    SerialPort serialPort;
    BufferedReader reader;

    public boolean initCom() {
        SerialPort[] ports = SerialPort.getCommPorts();

        if (ports.length > 0) {
            serialPort = ports[0];

            serialPort.setComPortParameters(9600, 8, SerialPort.ONE_STOP_BIT, 0);
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 5000, 0);

            serialPort.addDataListener(this);

            if (serialPort.openPort()) {
                System.out.println("El puerto está abierto");
                return true;
            }
            else {
                System.out.println("No se ha podido abrir el puerto");
            }
        }
        return false;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE)  {
            reader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
            System.out.println("Si");
        }
    }

    @Override
    public int getListeningEvents() {
        return 0;
    }
}

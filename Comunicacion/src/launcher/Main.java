package launcher;

import serial.SerialCommunicator;

public class Main {

    public static void main(String[] args) {
        SerialCommunicator sc = new SerialCommunicator();
        if (!sc.initCom()) {
            System.out.println("No se ha podido conectar.");
        }
    }
}

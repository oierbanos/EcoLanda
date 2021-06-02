import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Conexion {

    //El nombre del puerto puede variar dependiendo de la entrada USB del PC inicializamos y declaramos variables
	private static final int RATIO_SERIAL = 9600;

	ComunicacionPlaca comunicacion;
    SerialPort serialport;
    Integer puerto;
  	
  	public void conectar() {
		SerialPort[] puertosDisponibles = SerialPort.getCommPorts();

		try {
			if (puertosDisponibles.length == 0) {
				System.out.println("No hay ningún puerto COM disponible.");
			}
			else {
				getPuerto(puertosDisponibles);
				if (puerto < puertosDisponibles.length && puerto >= 0) {
					try {
						serialport = puertosDisponibles[puerto];
						comunicacion = new ComunicacionPlaca(serialport);
						configurarPuerto(); // Configuramos el puerto que nos interesa
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
		} catch (Exception exc){
			System.out.println("No se ha podido conectar con la placa.");
		}
  	}
  	
  	public void getPuerto(SerialPort[] puertosDisponibles) {
		for (int i = 0; i < puertosDisponibles.length; i++) {
			if (puertosDisponibles[i].getDescriptivePortName().toLowerCase().contains("prolific")) {
				System.out.println("Conectando a...\n" + puertosDisponibles[i].getSystemPortName()
						+ " : " + puertosDisponibles[i].getDescriptivePortName());
				puerto = i;
				return;
			}
		}
	}

    private void configurarPuerto() {
 		serialport.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 5000, 0);
		serialport.setBaudRate(RATIO_SERIAL);
		serialport.setNumDataBits(8);
		serialport.setParity(SerialPort.NO_PARITY);
		serialport.setNumStopBits(SerialPort.ONE_STOP_BIT);
		serialport.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
		serialport.openPort();
		serialport.addDataListener(comunicacion);
	}

	public static class KeyboardListener extends Thread {

		@Override
		public void run() {
			Scanner teclado = new Scanner(System.in);

			do {
				if (teclado.nextLine().equalsIgnoreCase("salir")) {
					System.out.println("Cerrando servidor...");
					teclado.close();

					System.exit(-1);
				}
			} while (true);
		}
	}

	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(8888);
		Conexion conexion = new Conexion();

		conexion.conectar();
		new KeyboardListener().start();

		//noinspection InfiniteLoopStatement
		while (true) {
			SocketThread thread = new SocketThread(server.accept(), conexion.comunicacion);
			conexion.comunicacion.setPropertyChangeListener(thread);
			System.out.println("Conexión realizada.");
			thread.start();
		}
	}
}
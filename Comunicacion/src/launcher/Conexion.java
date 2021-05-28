package launcher;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

public class Conexion {
	
    //El nombre del puerto puede variar dependiendo de la entrada USB del PC inicializamos y declaramos variables	
	private static int RATIO_SERIAL = 9600;
	
    SerialPort serialport;
    ComunicacionPlaca reader;
    
    Scanner teclado;
    Integer puerto;

	public void conectar() {
		SerialPort puertosDisponibles[] = SerialPort.getCommPorts();

		if (puertosDisponibles.length == 0) {
			System.out.println("No hay ningún puerto COM disponible.");
		}
		else {
			getPuerto(puertosDisponibles);
			if (puerto < puertosDisponibles.length && puerto >= 0) {
				try {
					serialport = puertosDisponibles[puerto];
					reader = new ComunicacionPlaca(serialport);
					configurarPuerto(); // Configuramos el puerto que nos interesa
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	public void getPuerto(SerialPort[] puertosDisponibles) {

		teclado = new Scanner(System.in);

		//EN CASO DE USAR MAS DE UN COM "PROLIFIC" HABRIA QUE COMENTAR LO DE ABAJO Y DESCOMENTAR ESTO

  /*System.out.println("Puertos disponibles:");
  for (int i = 0; i < puertosDisponibles.length; i++) {
     System.out.println("[" + i + "]  " + puertosDisponibles[i].getSystemPortName()
           + " : " + puertosDisponibles[i].getDescriptivePortName());
  }

  System.out.println("Que puerto desea utilizar?");
  puerto = teclado.nextInt();*/

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
		serialport.addDataListener(reader);
	}

	public static void main(String[] args) {
    	 Conexion conexion = new Conexion();
    	 conexion.conectar();
	}
}
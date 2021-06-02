import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class ComunicacionPlaca  implements SerialPortDataListener {
	
	SerialPort serialport;
	List<Byte> bufferDeMensaje;
   	
	public ComunicacionPlaca (SerialPort serialport) {
		this.serialport = serialport;
		bufferDeMensaje= new ArrayList<>();
	}

	private void transformToString(List<Byte> bufferDeMensaje) {
		StringBuilder mensajeEnviar = new StringBuilder();
		for (Byte aByte : bufferDeMensaje) {

			int c = Integer.parseInt(aByte.toString());
			char valor = (char) c;
			mensajeEnviar.append(valor);
		}
		System.out.println(mensajeEnviar);
		bufferDeMensaje.clear();
	}

	public void clasificarMensaje() {
		Scanner teclado = new Scanner(System.in);
		String s = teclado.nextLine();

		while (!s.equalsIgnoreCase("salir")) {
			if (s.equals("t") || s.equals("p")) enviarMensaje(s);
			s = teclado.nextLine();
		}
		System.exit(-1);
	}

	private void enviarMensaje(String s) {
		byte[] bytes = s.getBytes();
		serialport.writeBytes(bytes,1);
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
			byte[] bufferDeLectura = new byte[1];
			serialport.readBytes(bufferDeLectura, bufferDeLectura.length);

			if ((bufferDeLectura[0]) == 10) transformToString(bufferDeMensaje);
			else bufferDeMensaje.add(bufferDeLectura[0]);
		}
	}

	@Override
   	public int getListeningEvents(){
   		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
   	}
}
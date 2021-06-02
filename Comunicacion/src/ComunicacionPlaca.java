import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class ComunicacionPlaca  implements SerialPortDataListener {
	
	SerialPort serialport;
	BufferedReader buffer;
	List<Byte> bufferDeMensaje;
   	
	public ComunicacionPlaca (SerialPort serialport) {
		this.serialport = serialport;
		bufferDeMensaje= new ArrayList<>();
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		if(event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) return;
		else {
			byte[] bufferDeLectura = new byte[1];
			//bufferDeLectura[0]=0;
			serialport.readBytes(bufferDeLectura, bufferDeLectura.length);
			if((bufferDeLectura[0])==10) {
				transformToString(bufferDeMensaje);
			}
			else{
				bufferDeMensaje.add(bufferDeLectura[0]);
		 	}
		}
	}

	private void transformToString(List<Byte> bufferDeMensaje) {

		String mensajeEnviar = "";
		for(int i=0; i<bufferDeMensaje.size();i++){

			int c = Integer.valueOf(bufferDeMensaje.get(i).toString());
			Character valor = Character.valueOf((char) c);
			mensajeEnviar+=valor;
		}
		System.out.println(mensajeEnviar);
		bufferDeMensaje.clear();
	}

	@Override
   	public int getListeningEvents(){
   		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
   	}

	public void clasificarMensaje() {

		Scanner teclado = new Scanner(System.in);
		String s = teclado.nextLine();

		while(!s.toLowerCase().equals("salir")){
			if(s.equals("t") || s.equals("p")) enviarMensaje(s);
			s = teclado.nextLine();
		}
		System.exit(-1);
	}

	private void enviarMensaje(String s) {
		byte[] bytes = s.getBytes();
		serialport.writeBytes(bytes,1);
	}
}
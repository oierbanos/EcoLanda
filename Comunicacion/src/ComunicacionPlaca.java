import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class ComunicacionPlaca  implements SerialPortDataListener, PropertyChangeListener {

	boolean mensajeCompleto;
	SerialPort serialport;
	List<Byte> bufferDeMensaje;
	PropertyChangeSupport conector;
   	
	public ComunicacionPlaca (SerialPort serialport) {
		this.serialport = serialport;
		this.bufferDeMensaje = new ArrayList<>();
		this.conector = new PropertyChangeSupport(this);
	}

	public void setPropertyChangeListener(PropertyChangeListener listener) {
		conector.addPropertyChangeListener(listener);
	}

	private String transformToString(List<Byte> bufferDeMensaje) {
		StringBuilder mensajeEnviar = new StringBuilder();
		for (Byte aByte : bufferDeMensaje) {

			int c = Integer.parseInt(aByte.toString());
			char valor = (char) c;
			mensajeEnviar.append(valor);
		}
		bufferDeMensaje.clear();
		return String.valueOf(mensajeEnviar);
	}

	private void enviarMensaje(String s) {
		byte[] bytes = s.getBytes();

		mensajeCompleto = false;
		serialport.writeBytes(bytes,1);
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
			byte[] bufferDeLectura = new byte[1];
			serialport.readBytes(bufferDeLectura, bufferDeLectura.length);

			if ((bufferDeLectura[0]) == 10) mensajeCompleto = true;
			else bufferDeMensaje.add(bufferDeLectura[0]);
		}
	}

	@Override
   	public int getListeningEvents(){
   		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
   	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String instruction = evt.getPropertyName();
		String mensaje = "";

		if (instruction.equals("Actualizar")) {
			enviarMensaje("t");
			while (!mensajeCompleto) ;
			mensaje += transformToString(bufferDeMensaje).substring(1);

			mensaje += "/" + (new Random().nextInt(100) - 1);

			conector.firePropertyChange(instruction, null, mensaje);
		}
	}
}
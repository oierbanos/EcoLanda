import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * Clase de comunicacion con la placa mediante serial.
 */
public class ComunicacionPlaca  implements SerialPortDataListener, PropertyChangeListener {

	/**
	 * Mensaje que se transmite.
	 */
	volatile boolean mensajeCompleto;
	/**
	 * Mensaje recibido.
	 */
	List<Byte> bufferDeMensaje;

	/**
	 * Puerto serial al que esta conectado.
	 */
	SerialPort serialport;
	/**
	 * Lanzador de eventos.
	 */
	PropertyChangeSupport conector;

	/**
	 * Crear una nueva instancia de la comunicacion con la placa.
	 * @param serialport Puerto serial por el que se conecta.
	 */
	public ComunicacionPlaca (SerialPort serialport) {
		this.serialport = serialport;
		this.bufferDeMensaje = new ArrayList<>();
		this.conector = new PropertyChangeSupport(this);
	}

	/**
	 * Añadir un listener de eventos.
	 * @param listener El listener que se va a añadir.
	 */
	public void setPropertyChangeListener(PropertyChangeListener listener) {
		conector.addPropertyChangeListener(listener);
	}

	/**
	 * Convertir un array de bits en string.
	 * @param bufferDeMensaje Mensaje a transformar.
	 * @return Mensaje transformado.
	 */
	private String transformToString(List<Byte> bufferDeMensaje) {
		StringBuilder mensajeEnviar = new StringBuilder();
		for (Byte aByte : bufferDeMensaje) {

			int c = Integer.parseInt(aByte.toString());
			mensajeEnviar.append((char) c);
		}
		bufferDeMensaje.clear();
		return String.valueOf(mensajeEnviar);
	}

	/**
	 * Enviar un mensaje mediante serial.
	 * @param s Mensaje que se va a enviar.
	 */
	private void enviarMensaje(String s) {
		byte[] bytes = s.getBytes();

		mensajeCompleto = false;
		serialport.writeBytes(bytes,1);
	}

	/**
	 * Evento de serial.
	 * @param event Evento ocurrido.
	 */
	@Override
	public void serialEvent(SerialPortEvent event) {
		if (event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
			byte[] bufferDeLectura = new byte[1];
			serialport.readBytes(bufferDeLectura, bufferDeLectura.length);

			// Si no es '\n' añadir al bufferDeMensaje
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
			while (!mensajeCompleto);
			mensaje += transformToString(bufferDeMensaje).substring(1);

			mensaje += "/" + (new Random().nextInt(100) - 1);
			conector.firePropertyChange(instruction, null, mensaje);
		}
	}
}
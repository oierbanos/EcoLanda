package launcher;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class ComunicacionPlaca  implements SerialPortDataListener {

	byte[] bufferLectura = new byte[1];
	SerialPort serialport;
	String value = "";

	public ComunicacionPlaca (SerialPort serialport) {
		this.serialport = serialport;
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		if(event.getEventType() == SerialPort.LISTENING_EVENT_DATA_AVAILABLE) {
	   		try {
	   			serialport.readBytes(bufferLectura, bufferLectura.length);
				String c = new String(bufferLectura, "UTF-8");

				if (!c.equals("-")) {
					value = "";
				}
				else {
					value += c;
				}
				System.out.println(c);
			}
	   		catch (Exception e) {
	   			e.printStackTrace();
			}
		}

	}


	@Override
   	public int getListeningEvents() {
   		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
   	}		 
  }
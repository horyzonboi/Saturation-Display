package net.horyzon;

import com.fazecast.jSerialComm.SerialPort;
import java.io.OutputStream;

public class SerialHandler {
    private static SerialPort port;
    private static OutputStream out;

    public static void connect(String portName) {
        port = SerialPort.getCommPort(portName);
        port.setBaudRate(9600);

        if (port.openPort()) {
            out = port.getOutputStream();
            System.out.println("Connected to Arduino on " + portName);
        } else {

        }
    }

    public static void sendData(String data) {
        if (out != null) {
            try {
                out.write(data.getBytes());
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void close() {
        if (port != null && port.isOpen()) {
            port.closePort();
        }
    }
}

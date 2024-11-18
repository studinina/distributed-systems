

import java.io.*;
//import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class LogClient {
    public void connectTo(String serverAddress, LogMessageOuterClass.LogMessage logMessage) {
        sendTo(serverAddress, 80, logMessage);
    }

    public void sendTo(String serverAddress, int port, LogMessageOuterClass.LogMessage logMessage) {
        Socket socket;
        try {
            socket = new Socket(serverAddress, port);

            OutputStream outputStream = socket.getOutputStream();

            // Serialize LogMessage to byte array
            byte[] logMessageBytes = logMessage.toByteArray();
    
            // Send the size of the message followed by the message itself
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeInt(logMessageBytes.length);
            dataOutputStream.write(logMessageBytes);
            dataOutputStream.flush();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
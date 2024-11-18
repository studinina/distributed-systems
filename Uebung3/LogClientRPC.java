

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class LogClientRPC {
    public void connectTo(String serverAddress, Rpcrequest.RPC_Request rpcMessage) {
        sendTo(serverAddress, 80, rpcMessage);
    }

    public void sendTo(String serverAddress, int port, Rpcrequest.RPC_Request rpcMessage) {
        Socket socket;
        try {
            socket = new Socket(serverAddress, port);

            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            // Serialize LogMessage to byte array
            byte[] rpcMessageBytes = rpcMessage.toByteArray();
    
            // Send the size of the message followed by the message itself
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            dataOutputStream.writeInt(rpcMessageBytes.length);
            dataOutputStream.write(rpcMessageBytes);
            dataOutputStream.flush();

            if (rpcMessage.getOperation() == Rpcrequest.RPC_Request.Operation.GETRECORD) {
                int msgSize = dataInputStream.readInt();

                // Read the message bytes
                byte[] messageBytes = new byte[msgSize];
                dataInputStream.readFully(messageBytes);
                Rpcrequest.RPC_AnswerRecord rpcAnswer = Rpcrequest.RPC_AnswerRecord.parseFrom(messageBytes);

                System.out.println("Ergebnis getRecord: " + rpcAnswer.getSize());
            } else if (rpcMessage.getOperation() == Rpcrequest.RPC_Request.Operation.GETSIZE) {
                int msgSize = dataInputStream.readInt();

                // Read the message bytes
                byte[] messageBytes = new byte[msgSize];
                dataInputStream.readFully(messageBytes);
                Rpcrequest.RPC_AnswerSize rpcAnswer = Rpcrequest.RPC_AnswerSize.parseFrom(messageBytes);


                System.out.println("Ergebnis GetSize: " + rpcAnswer.getSize());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
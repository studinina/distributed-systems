package Uebung3;

import com.google.protobuf.InvalidProtocolBufferException;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;
//import src.main.java.LogMessageOuterClass;


public class LogMessageHandler implements Runnable {
    private Socket socket;
    private String logFilePath;

    public LogMessageHandler(Socket socket, String logFilePath) {
        this.socket = socket;
        this.logFilePath = logFilePath;
    }
    
    @Override
    public void run() {
        InputStream inputStream = null;
        LogMessageOuterClass.LogMessage logMessage = null;
        // Read the size of the message
        int msgSize = 0;
        try {
            inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            msgSize = dataInputStream.readInt();

            // Read the message bytes
            byte[] messageBytes = new byte[msgSize];
            dataInputStream.readFully(messageBytes);
            logMessage = LogMessageOuterClass.LogMessage.parseFrom(messageBytes);
            FileWriter fileWriter = new FileWriter(logFilePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            String logMsg = "Neue Nachricht - Groesse: " + msgSize + "Bytes --- ";
            logMsg = logMsg + "Timestamp: " + logMessage.getTimeCreated() + " --- ";
            if (logMessage.hasCreator()) {
                logMsg = logMsg + "Creator: " + logMessage.getCreator() + " --- ";
            }
            logMsg = logMsg + "Severity: " + logMessage.getSeverity() + " --- ";
            logMsg = logMsg + "MessageBody: " + logMessage.getMessageBody();


            bufferedWriter.write(logMsg);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


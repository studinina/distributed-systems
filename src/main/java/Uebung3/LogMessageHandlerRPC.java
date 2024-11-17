package Uebung3;

import com.google.protobuf.InvalidProtocolBufferException;

import java.io.*;
import java.net.Socket;
//import src.main.java.LogMessageOuterClass;


public class LogMessageHandlerRPC implements Runnable {
    private Socket socket;
    private String logFilePath;

    public LogMessageHandlerRPC(Socket socket, String logFilePath) {
        this.socket = socket;
        this.logFilePath = logFilePath;
    }
    
    @Override
    public void run() {
        InputStream inputStream = null;
        Rpcrequest.RPC_Request rpcMessage = null;
        // Read the size of the message
        int msgSize = 0;
        try {
            inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            msgSize = dataInputStream.readInt();

            // Read the message bytes
            byte[] messageBytes = new byte[msgSize];
            dataInputStream.readFully(messageBytes);
            rpcMessage = Rpcrequest.RPC_Request.parseFrom(messageBytes);

            Rpcrequest.RPC_Request.Operation operation = rpcMessage.getOperation();
            OutputStream outputStream = socket.getOutputStream();

            // Send the size of the message followed by the message itself
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            byte[] answerBytes = null;

            switch (operation) {
                case GETRECORD: String record = getRecord(rpcMessage.getOp1Arg().getIndex());
                    Rpcrequest.RPC_AnswerRecord answerRecord = Rpcrequest.RPC_AnswerRecord.newBuilder().setSize(record).build();
                    // Serialize LogMessage to byte array
                    answerBytes = answerRecord.toByteArray();
                    dataOutputStream.writeInt(answerBytes.length);
                    dataOutputStream.write(answerBytes);
                    dataOutputStream.flush();
                    break;
                case ADDRECORD: addRecord(rpcMessage.getOp2Arg().getRecord(), rpcMessage.getOp2Arg().getIndex());
                    break;
                case GETSIZE: int size = getSize();
                    Rpcrequest.RPC_AnswerSize answer = Rpcrequest.RPC_AnswerSize.newBuilder().setSize(size).build();
                    // Serialize LogMessage to byte array
                    answerBytes = answer.toByteArray();
                    dataOutputStream.writeInt(answerBytes.length);
                    dataOutputStream.write(answerBytes);
                    dataOutputStream.flush();
                    break;
            }
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getRecord(int index) throws IOException  {
        String result = "";

        BufferedReader br = new BufferedReader(new FileReader(logFilePath));
            String line = "";

            while ((line = br.readLine()) != null) {
                if (line.startsWith(String.valueOf(index))) {
                    result = line.substring(String.valueOf(index).length() + 1);
                }
            }

        return result;
    }


    private void addRecord(String record,int index) throws IOException {
        FileWriter fileWriter = new FileWriter(logFilePath, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        String logRecord = index + ":" + record;

        bufferedWriter.write(logRecord);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    private int getSize() throws IOException  {
        int lineCount = 0;
        BufferedReader br = new BufferedReader(new FileReader(logFilePath));

            while ((br.readLine()) != null) {
                // Increment the line count for each line read
                lineCount++;
            }

            System.out.println("Number of lines in the file: " + lineCount);

        return lineCount;
    }
}


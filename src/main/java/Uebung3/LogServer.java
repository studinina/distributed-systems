package Uebung3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import Uebung3.LogMessage;

public class LogServer {
    private int port;

    public LogServer(int port) {
        this.port = port;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("LogServer läuft auf Port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Neue Verbindung: " + clientSocket.getInetAddress());

                try (InputStream inputStream = clientSocket.getInputStream()) {
                    // Log-Nachricht empfangen und deserialisieren
                    LogMessage logMessage = LogMessage.parseFrom(inputStream);

                    // Log-Nachricht in Datei speichern
                    saveLogMessage(logMessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLogMessage(LogMessage logMessage) {
        String logFilePath = "logs.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.write(String.format(
                "[%d] %s (%s): %s - %s%n",
                logMessage.getTimestamp(),
                logMessage.getCreator(),
                logMessage.getLocation(),
                logMessage.getSeverity(),
                logMessage.getBody()
            ));
            System.out.println("Log gespeichert: " + logMessage.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LogServer server = new LogServer(5555);
        server.startServer();
    }
}

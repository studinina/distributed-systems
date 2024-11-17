package Uebung2;

import java.io.*;
import java.net.*;

public class SocketServer implements Runnable {
    private int port;

    public SocketServer(int port) {
        this.port = port;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) { // Öffne einen ServerSocket auf dem angegebenen Port
            System.out.println("Server läuft auf Port " + port);

            while (true) {
                Socket socket = serverSocket.accept(); // Akzeptiere eingehende Verbindungen
                System.out.println("Neue Verbindung von: " + socket.getInetAddress() + ":" + socket.getPort());

                // Erstelle und starte einen neuen Thread zur Bearbeitung der Anfrage
                RequestHandlerHTTP reqHandler = new RequestHandlerHTTP(socket);
                new Thread(reqHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        startServer();
    }
}

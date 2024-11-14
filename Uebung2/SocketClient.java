package Uebung2;

import java.io.*;
import java.net.*;

public class SocketClient {
    public void connectTo(String serverAddress, int port) {
        try (Socket socket = new Socket(serverAddress, port)) { // Verbindung zu Serveradresse und Port herstellen
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Sende eine einfache HTTP GET-Anfrage
            writer.println("GET /index.html HTTP/1.1");
            writer.println("Host: " + serverAddress);
            writer.println(); // Leere Zeile, um das Ende des Headers zu signalisieren
            writer.flush();

            // Ausgabe der Antwort
            String responseLine;
            while ((responseLine = reader.readLine()) != null) {
                System.out.println("Server: '" + responseLine + "'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

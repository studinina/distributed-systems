package Uebung2;

import java.io.*;
import java.net.*;

public class SocketClient {
    public void connectTo(String serverAddress, int port) {
        try (Socket socket = new Socket(serverAddress, port)) {
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Sende eine präzise HTTP GET-Anfrage
            writer.println("GET / HTTP/1.1");              // Erste Zeile: Methode, Pfad und HTTP-Version
            writer.println("Host: " + serverAddress);        // Host-Header für den Server
            writer.println("Connection: close");           // Verbindungs-Header zum Schließen der Verbindung nach der Antwort
            writer.println();                                // Leere Zeile, um das Ende des Headers zu signalisieren
            writer.flush();
            // Das funktioniert so nicht, muss über die Kommandozeile eingegeben werden (siehe readme). 

            // Liest und gib die Serverantwort Zeile für Zeile aus
            String responseLine;
            while ((responseLine = reader.readLine()) != null) {
                System.out.println("Server: '" + responseLine + "'");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

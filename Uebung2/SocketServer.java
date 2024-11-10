package Uebung2;

import java.io.*;
import java.net.*;

public class SocketServer {
    public static void main(String[] args) {
        // Versucht, einen Server auf Port 8080 zu starten
        try (ServerSocket serverSocket = new ServerSocket(8080)) { // Erstelle einen ServerSocket auf Port 8080
            System.out.println("Server is listening on port 8080"); // Meldet im Terminal, dass der Server läuft

            while (true) { // Endlosschleife für den Server, um kontinuierlich Anfragen zu akzeptieren
                Socket socket = serverSocket.accept(); // Wartet auf eingehende Verbindungen und akzeptiert sie
                new Thread(new ClientHandler(socket)).start(); // Starte einen neuen Thread für jede eingehende Verbindung
            }
        } catch (IOException e) {
            e.printStackTrace(); // Gibt Fehler im Falle einer Ausnahme aus
        }
    }
}

// Handler-Klasse für die Bearbeitung einzelner Client-Anfragen
class ClientHandler implements Runnable {
    private Socket socket;

    // Konstruktor zur Übergabe des Sockets an den Handler
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    // Die Methode run() wird aufgerufen, wenn der Thread gestartet wird
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Eingabestream vom Client
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) { // Ausgabestream an den Client

            String requestLine = in.readLine(); // Lese die erste Zeile der Anfrage
            if (requestLine != null && requestLine.startsWith("GET")) { // Prüfe, ob es eine GET-Anfrage ist
                // Sende eine einfache HTTP-Antwort
                out.println("HTTP/1.1 200 OK"); // Statuszeile der Antwort
                out.println("Content-Type: text/plain"); // Setzt den "Content-Type"-Header auf "text/plain"
                out.println(); // Leere Zeile, die den Header von der Nachricht trennt
                out.println("Hello, this is the server response!"); // Nachricht, die an den Client gesendet wird
            }
        } catch (IOException e) {
            e.printStackTrace(); // Gibt Fehler im Falle einer Ausnahme aus
        }
    }
}

package Uebung2;

import java.io.*; // Importiert die Klassen für Eingabe- und Ausgabeoperationen
import java.net.*; // Importiert die Klassen für Netzwerkoperationen

public class SocketClient {
    public static void main(String[] args) {

        // Versucht, eine Verbindung zu einem Server herzustellen
        try (Socket socket = new Socket("stud.fh-wedel.de", 80)) { // Erstellt eine neue Socket-Verbindung zu "stud.fh-wedel.de" auf Port 80 (HTTP-Port)
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Erstellt einen PrintWriter für die Ausgabe an den Server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Erstellt einen BufferedReader für die Eingabe vom Server

            // Sendt eine HTTP GET-Anfrage an den Server
            out.println("GET / HTTP/1.1"); // HTTP GET-Anfrage für die Root-Seite "/"
            out.println("Host: stud.fh-wedel.de"); // Setzt den "Host"-Header auf "stud.fh-wedel.de"
            out.println(); // Leere Zeile, für Ende des HTTP-Headers 

            // Ließt und gibt die Antwort des Servers Zeile für Zeile aus
            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                System.out.println(responseLine); // Gibt jede Zeile der Antwort im Terminal aus
            }
        } catch (IOException e) {
            e.printStackTrace(); // Gibt wenn eine Fehlermeldung im Falle einer Ausnahme aus
        }
    }
}

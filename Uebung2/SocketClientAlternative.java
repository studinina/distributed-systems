package Uebung2;

import java.io.*; // Importiert die Klassen für Eingabe- und Ausgabeoperationen
import java.net.URL; // Importiert die URL-Klasse für einfacheren Zugriff auf URLs

public class SocketClientAlternative {
    public static void main(String[] args) {

        System.setProperty("http.proxyHost", "proxy.example.com");  // Setzt den Proxy-Host -> Muss ersetzt werden
        System.setProperty("http.proxyPort", "8080"); // Setzt den Proxy-Port -> Muss ersetzt werden
        try {
            URL url = new URL("http://stud.fh-wedel.de/index.html"); // Erstellt ein URL-Objekt für die angegebene Adresse
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream())); // Öffnet einen Eingabestream von der URL

            // Lese die Antwort vom Server und gib sie Zeile für Zeile aus
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine); // Gibt jede Zeile der Antwort im Terminal aus
            }
            in.close(); // Schliesst den Eingabestream
        } catch (IOException e) {
            e.printStackTrace(); // Gibt Fehler im Falle einer Ausnahme aus
        }
    }
}

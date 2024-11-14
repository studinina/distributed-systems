package Uebung2;

import java.io.*; // Importiert die Klassen für Eingabe- und Ausgabeoperationen
import java.net.HttpURLConnection;
import java.net.URL; // Importiert die URL-Klasse für einfacheren Zugriff auf URLs

public class SocketClientAlternative {
    public void connectTo(String urlString) {
        try {
            URL url = new URL(urlString); // Erstelle ein URL-Objekt
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // Öffne die Verbindung
            connection.setRequestMethod("GET"); // Setze die HTTP-Methode auf GET

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Lese und gebe die Antwort vom Server aus
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

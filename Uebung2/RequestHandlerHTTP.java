package Uebung2;

import java.io.*;
import java.net.Socket;

public class RequestHandlerHTTP implements Runnable{
    private Socket socket;

    public RequestHandlerHTTP(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String requestLine = in.readLine(); // Lese die erste Zeile der Anfrage
            System.out.println("Empfangene Anfrage: " + requestLine);

            if (requestLine != null && requestLine.startsWith("GET")) {
                // Sende eine einfache HTTP-Antwort zurück
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/plain");
                out.println();
                out.println("Hallo, dies ist die Serverantwort!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package Uebung2;

public class Main {

    public static void main(String[] args) {
        
        // Uncomment one of the following lines to start either the client or the server:
        Aufgabenteil01();
        Aufgabenteil02();
    }

    private static void Aufgabenteil02() {
        // Starte den Server auf Port 5555 
        SocketServer server = new SocketServer(5555);
        Thread serverThread = new Thread(server);
        serverThread.start(); // Startet den Server in einem neuen Thread

        try {
            Thread.sleep(1000); // Warte, bis der Server vollständig gestartet ist
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Optional: Falls gewünscht, kann auch der Client gestartet werden, um Anfragen an den Server zu senden
        //SocketClient client = new SocketClient();
        //client.connectTo("localhost", 4444);
    }

    private static void Aufgabenteil01() {
        SocketClient sc = new SocketClient(); // Client, der Sockets verwendet
        SocketClientAlternative scu = new SocketClientAlternative(); // Client, der die URL-Klasse verwendet

        System.out.println("\n\n ! SocketClientURL ! \n\n");
        scu.connectTo("http://stud.fh-wedel.de/index.html"); // Verbindung mit einer URL

        System.out.println("\n\n ! SocketClient ! \n\n");
        sc.connectTo("stud.fh-wedel.de", 80); // Verbindung mit einer Adresse und Port
    }
}

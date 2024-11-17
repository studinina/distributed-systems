package Uebung3;

public class Main {
    public static void main(String[] args) {
        // Uncomment one of the following lines to start either the server or the client:
        startLogServer();
        // startLogClient();
    }

    private static void startLogServer() {
        LogServer server = new LogServer(5555);
        server.startServer();
    }

    private static void startLogClient() {
        LogClient client = new LogClient();
        client.main(null); // Startet den Client und sendet eine Log-Nachricht
    }
}

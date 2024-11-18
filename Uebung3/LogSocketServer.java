

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LogSocketServer implements Runnable {
    private int port;
    private String logFilePath;

    public LogSocketServer(int port, String logFilePath) {
        this.port = port;
        this.logFilePath = logFilePath;
    }

    public void startServer() {
        boolean runServer = true;
        try {
            ServerSocket sSocket = new ServerSocket(port);
            // sSocket = new ServerSocket(port);
            while (runServer) {
                Socket socket = sSocket.accept();

                System.out.println("Neue Socketadresse: " + socket.getInetAddress() + ":" + socket.getPort());

                LogMessageHandler reqHandler = new LogMessageHandler(socket, logFilePath);
                Thread t = new Thread(reqHandler);
                t.start();
            }
            sSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
            runServer = false;
        }
    }

    @Override
    public void run() {
        startServer();
    }

    public static void main(String[] args) {
    int port = 4444; // Der Port, auf dem der Server lauschen soll
    String logFilePath = "server.log"; // Log-Datei

    LogSocketServer server = new LogSocketServer(port, logFilePath);
    System.out.println("Server läuft auf Port " + port);

    new Thread(server).start();
    }
}

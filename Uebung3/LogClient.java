import java.io.OutputStream;
import java.net.Socket;
import java.time.Instant;
import uebung3.LogMessage; 

public class LogClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             OutputStream outputStream = socket.getOutputStream()) {

            // Erstelle eine Log-Nachricht
            LogMessage logMessage = LogMessage.newBuilder()
                    .setTimestamp(Instant.now().getEpochSecond())
                    .setCreator("Client A")
                    .setLocation("localhost")
                    .setSeverity(LogMessage.SeverityLevel.INFO)
                    .setBody("Dies ist eine Log-Nachricht.")
                    .build();

            // Serialisiere und sende die Nachricht
            logMessage.writeTo(outputStream);
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

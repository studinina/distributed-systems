package Uebung4;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class DatabaseClient {

    private final DatabaseProto.DatabaseServiceGrpc.DatabaseServiceBlockingStub stub;

    public DatabaseClient(ManagedChannel channel) {
        this.stub = DatabaseProto.DatabaseServiceGrpc.newBlockingStub(channel);
    }

    public void addRecord(int index, String record) {
        DatabaseProto.AddRecordRequest request = DatabaseProto.AddRecordRequest.newBuilder()
                .setIndex(index)
                .setRecord(record)
                .build();
        DatabaseProto.AddRecordResponse response = stub.addRecord(request);
        System.out.println("Add record success: " + response.getSuccess());
    }

    public void getRecord(int index) {
        DatabaseProto.GetRecordRequest request = DatabaseProto.GetRecordRequest.newBuilder().setIndex(index).build();
        DatabaseProto.GetRecordResponse response = stub.getRecord(request);
        System.out.println("Record for index " + index + ": " + response.getRecord());
    }

    public void getSize() {
        DatabaseProto.GetSizeRequest request = DatabaseProto.GetSizeRequest.newBuilder().build();
        DatabaseProto.GetSizeResponse response = stub.getSize(request);
        System.out.println("Database size: " + response.getSize());
    }

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        DatabaseClient client = new DatabaseClient(channel);

        // Daten hinzufügen
        client.addRecord(4101, "Appen");
        client.addRecord(4102, "Ahrensburg");
        client.addRecord(4103, "Wedel");

        // Daten lesen
        client.getRecord(4103);
        client.getRecord(4107); // Dieser Index sollte nicht vorhanden sein

        // Größe der Datenbank abfragen
        client.getSize();

        channel.shutdown();
    }
}

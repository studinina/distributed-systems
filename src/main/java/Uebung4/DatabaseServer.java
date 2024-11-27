package Uebung4;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseServer {

    // Datenbank als einfache Map
    private final Map<Integer, String> records = new HashMap<>();

    // Implementierung des gRPC-Dienstes
    static class DatabaseServiceImpl extends DatabaseProto.DatabaseServiceGrpc.DatabaseServiceImplBase {
        private final Map<Integer, String> records;

        DatabaseServiceImpl(Map<Integer, String> records) {
            this.records = records;
        }

        @Override
        public void getRecord(DatabaseProto.GetRecordRequest request, StreamObserver<DatabaseProto.GetRecordResponse> responseObserver) {
            String record = records.getOrDefault(request.getIndex(), "");
            DatabaseProto.GetRecordResponse response = DatabaseProto.GetRecordResponse.newBuilder().setRecord(record).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void addRecord(DatabaseProto.AddRecordRequest request, StreamObserver<DatabaseProto.AddRecordResponse> responseObserver) {
            records.put(request.getIndex(), request.getRecord());
            DatabaseProto.AddRecordResponse response = DatabaseProto.AddRecordResponse.newBuilder().setSuccess(true).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void getSize(DatabaseProto.GetSizeRequest request, StreamObserver<DatabaseProto.GetSizeResponse> responseObserver) {
            int size = records.size();
            DatabaseProto.GetSizeResponse response = DatabaseProto.GetSizeResponse.newBuilder().setSize(size).build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    public void start() throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051)
                .addService(new DatabaseServiceImpl(records))
                .build()
                .start();

        System.out.println("Server started, listening on port 50051");
        server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        DatabaseServer server = new DatabaseServer();
        server.start();
    }
}

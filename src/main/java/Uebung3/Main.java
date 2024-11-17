package Uebung3;

import com.google.protobuf.Timestamp;
import java.time.Instant;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        //Aufgabenteil02();
        //Aufgabenteil01();
        //Thread.sleep(5000);


        Aufgabenteil03();
        //Aufgabenteil04();


    }

    private static void Aufgabenteil04() {
        LogSocketServerRPC sk = new LogSocketServerRPC(4444, "serverLog.txt");
        //sk.startServer();
        Thread t = new Thread(sk);
        t.start();

        try {
            Thread.sleep(5000);
            //t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //SimpleClient sc = new SimpleClient();
        //sc.connectTo("localhost", 4444);
    }

    private static void Aufgabenteil03() {
        LogClientRPC sc = new LogClientRPC();

        Rpcrequest.RPC_Request.ADDRECORD_request req = Rpcrequest.RPC_Request.ADDRECORD_request.newBuilder()
                .setRecord("Appen")
                .setIndex(4101)
                .build();
        Rpcrequest.RPC_Request rpcMessage = Rpcrequest.RPC_Request.newBuilder()
                .setOperation(Rpcrequest.RPC_Request.Operation.ADDRECORD)
                .setOp2Arg(req)
                .build();
        System.out.println("Sende AddRecord 01 ...");
        sc.sendTo("localhost", 4444, rpcMessage);

        req = Rpcrequest.RPC_Request.ADDRECORD_request.newBuilder()
                .setRecord("Ahrensburg")
                .setIndex(4102)
                .build();
        rpcMessage = Rpcrequest.RPC_Request.newBuilder()
                .setOperation(Rpcrequest.RPC_Request.Operation.ADDRECORD)
                .setOp2Arg(req)
                .build();
        System.out.println("Sende AddRecord 02 ...");
        sc.sendTo("localhost", 4444, rpcMessage);

        req = Rpcrequest.RPC_Request.ADDRECORD_request.newBuilder()
                .setRecord("Wedel")
                .setIndex(4103)
                .build();
        rpcMessage = Rpcrequest.RPC_Request.newBuilder()
                .setOperation(Rpcrequest.RPC_Request.Operation.ADDRECORD)
                .setOp2Arg(req)
                .build();
        System.out.println("Sende AddRecord 03 ...");
        sc.sendTo("localhost", 4444, rpcMessage);

        req = Rpcrequest.RPC_Request.ADDRECORD_request.newBuilder()
                .setRecord("Aumühle")
                .setIndex(4104)
                .build();
        rpcMessage = Rpcrequest.RPC_Request.newBuilder()
                .setOperation(Rpcrequest.RPC_Request.Operation.ADDRECORD)
                .setOp2Arg(req)
                .build();
        System.out.println("Sende AddRecord 04 ...");
        sc.sendTo("localhost", 4444, rpcMessage);

        req = Rpcrequest.RPC_Request.ADDRECORD_request.newBuilder()
                .setRecord("Seevetal")
                .setIndex(4105)
                .build();
        rpcMessage = Rpcrequest.RPC_Request.newBuilder()
                .setOperation(Rpcrequest.RPC_Request.Operation.ADDRECORD)
                .setOp2Arg(req)
                .build();
        System.out.println("Sende AddRecord 05 ...");
        sc.sendTo("localhost", 4444, rpcMessage);

        req = Rpcrequest.RPC_Request.ADDRECORD_request.newBuilder()
                .setRecord("Quickborn")
                .setIndex(4106)
                .build();
        rpcMessage = Rpcrequest.RPC_Request.newBuilder()
                .setOperation(Rpcrequest.RPC_Request.Operation.ADDRECORD)
                .setOp2Arg(req)
                .build();
        System.out.println("Sende AddRecord 06 ...");
        sc.sendTo("localhost", 4444, rpcMessage);


        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Rpcrequest.RPC_Request.GETRECORD_request reqg = Rpcrequest.RPC_Request.GETRECORD_request.newBuilder()
                .setIndex(4103)
                .build();
        rpcMessage = Rpcrequest.RPC_Request.newBuilder()
                .setOperation(Rpcrequest.RPC_Request.Operation.GETRECORD)
                .setOp1Arg(reqg)
                .build();
        System.out.println("Sende GetRecord 01 ...");
        sc.sendTo("localhost", 4444, rpcMessage);

        reqg = Rpcrequest.RPC_Request.GETRECORD_request.newBuilder()
                .setIndex(4107)
                .build();
        rpcMessage = Rpcrequest.RPC_Request.newBuilder()
                .setOperation(Rpcrequest.RPC_Request.Operation.GETRECORD)
                .setOp1Arg(reqg)
                .build();
        System.out.println("Sende GetRecord 02 ...");
        sc.sendTo("localhost", 4444, rpcMessage);

        //Das wir hier noch einen Int reinschreiben, ist unnötig.
        Rpcrequest.RPC_Request.GETSIZE_request reqs = Rpcrequest.RPC_Request.GETSIZE_request.newBuilder()
                .setSize(1)
                .build();
        rpcMessage = Rpcrequest.RPC_Request.newBuilder()
                .setOperation(Rpcrequest.RPC_Request.Operation.GETSIZE)
                .setOp3Arg(reqs)
                .build();
        System.out.println("Sende GetSize ...");
        sc.sendTo("localhost", 4444, rpcMessage);


        System.out.println("\n\n ! FERTIG ! \n\n");
    }

    private static void Aufgabenteil02() {
        LogSocketServer sk = new LogSocketServer(4444, "serverLog.txt");
        //sk.startServer();
        Thread t = new Thread(sk);
        t.start();

        try {
            Thread.sleep(5000);
            //t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //SimpleClient sc = new SimpleClient();
        //sc.connectTo("localhost", 4444);
    }

    private static void Aufgabenteil01() {
        LogClient sc = new LogClient();

        LogMessageOuterClass.LogMessage.LogCreator logCreator = LogMessageOuterClass.LogMessage.LogCreator.newBuilder()
                .setCreatorName("Timo")
                .setLocation("Hamburg")
                .build();

        // Get the current timestamp
        Instant instant = Instant.now();

        // Convert Instant to google.protobuf.Timestamp
        Timestamp timestamp = Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();

        LogMessageOuterClass.LogMessage logMessage = LogMessageOuterClass.LogMessage.newBuilder()
                .setTimeCreated(timestamp)
                .setSeverity(LogMessageOuterClass.Severity.INFO)
                .setMessageBody("Was fuer eine tolle Nachricht! :O")
                .setCreator(logCreator)
                .build();

        
        System.out.println("\n\n ! SimpleClient ! \n\n");

        sc.sendTo("localhost", 4444, logMessage);
    }
}

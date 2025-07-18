package binout.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

import binout.registry.ServiceRegistryImpl;
import binout.userprofile.UserProfileImpl;
import binout.binschedule.BinScheduleImpl;
import binout.alert.AlertServiceImpl;

public class BinOutServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;
        Server server = ServerBuilder.forPort(port)
            .addService(new ServiceRegistryImpl())
            .addService(new UserProfileImpl())
            .addService(new BinScheduleImpl())
            .addService(new AlertServiceImpl())
            .build();

        System.out.println("Starting gRPC server on port " + port + "...");
        server.start();
        System.out.println("Server started.");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server...");
            server.shutdown();
            System.out.println("Server shut down.");
        }));

        server.awaitTermination();
    }
}

package binout.server;

import binout.registry.ServiceInfo;
import binout.registry.ServiceRegistryGrpc;
import binout.userprofile.UserProfileImpl;
import binout.binschedule.BinScheduleImpl;
import binout.registry.ServiceRegistryImpl;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class BinOutServer {

    public static void main(String[] args) throws Exception {
        Server server = ServerBuilder.forPort(50051)
                .addService(new BinScheduleImpl())
                .addService(new UserProfileImpl())
                .addService(new ServiceRegistryImpl())
                .build();

        server.start();
        System.out.println("Server started on port 50051");


        // Register services
        registerService("BinScheduleService", "localhost", 50051);
        registerService("UserProfileService", "localhost", 50051);
        registerService("ServiceRegistry", "localhost", 50051);

        server.awaitTermination();
    }

    private static void registerService(String name, String host, int port) {
        try {
            ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                    .usePlaintext()
                    .build();

            ServiceRegistryGrpc.ServiceRegistryBlockingStub stub =
                    ServiceRegistryGrpc.newBlockingStub(channel);

            ServiceInfo info = ServiceInfo.newBuilder()
                    .setServiceName(name)
                    .setServiceAddress(host)
                    .setServicePort(port)
                    .build();

            stub.register(info);
            channel.shutdown();
            System.out.println("Registered service: " + name);
        } catch (Exception e) {
            System.err.println("Failed to register service: " + name);
            e.printStackTrace();
        }
    }
}

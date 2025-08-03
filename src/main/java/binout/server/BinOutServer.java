/*
Emre Ketme
Distributed Systems CA 
*/

package binout.server;

import binout.binschedule.BinScheduleImpl;
import binout.recycling.RecyclingAdvisorImpl;
import binout.userprofile.UserProfileImpl;
import binout.discovery.JmDNSServiceRegistrar;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class BinOutServer {
    public static void main(String[] args) throws Exception {
        // start gRPC server with all service implementations and interceptor

        Server server = ServerBuilder.forPort(50051)
            .addService(new BinScheduleImpl())
            .addService(new UserProfileImpl())
            .addService(new RecyclingAdvisorImpl())
            .intercept(new AuthInterceptor())
            .build();

        System.out.println("Starting BinOut gRPC server on port 50051...");
        System.out.println("Registered services:");
        System.out.println("- BinScheduleImpl");
        System.out.println("- UserProfileImpl");
        System.out.println("- RecyclingAdvisorImpl");

        server.start();//starts the grPC server
        System.out.println("Server started successfully.");
        
        //register services using jmDNS for service discovery
        JmDNSServiceRegistrar registrar = new JmDNSServiceRegistrar();

        registrar.registerService("_binschedule._tcp.local.", "BinScheduleService", 50051, "Bin schedule service");
        registrar.registerService("_userprofile._tcp.local.", "UserProfileService", 50051, "User profile service");
        registrar.registerService("_recyclingadvisor._tcp.local.", "RecyclingAdvisorService", 50051, "Recycling advisor service");

        System.out.println("All services registered with jmDNS.");
        
        //shutdown hook for cleanup
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server...");
            registrar.unregisterAll();
            server.shutdown();
            System.out.println("Server stopped.");
        }));

        server.awaitTermination(); //keeps the server running
    }
}

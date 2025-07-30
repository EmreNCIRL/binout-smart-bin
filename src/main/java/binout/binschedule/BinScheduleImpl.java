package binout.binschedule;

import binout.registry.ServiceInfo;
import binout.registry.ServiceQuery;
import binout.registry.ServiceList;
import binout.registry.ServiceRegistryGrpc;
import binout.userprofile.UserProfile;
import binout.userprofile.UserRequest;
import binout.userprofile.UserProfileServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.ConcurrentHashMap;

public class BinScheduleImpl extends BinScheduleServiceGrpc.BinScheduleServiceImplBase {

    private final ConcurrentHashMap<String, BinSchedule> scheduleMap = new ConcurrentHashMap<>();

    private final String registryHost = "localhost";
    private final int registryPort = 50051;

    @Override
    public void setSchedule(BinSchedule request, StreamObserver<Ack> responseObserver) {
        scheduleMap.put(request.getUserId(), request);

        Ack ack = Ack.newBuilder()
                .setSuccess(true)
                .setMessage("Schedule saved for user: " + request.getUserId())
                .build();

        responseObserver.onNext(ack);
        responseObserver.onCompleted();
    }

    @Override
    public void getSchedule(BinRequest request, StreamObserver<BinSchedule> responseObserver) {
        try {
            // Discover UserProfileService via ServiceRegistry
            ManagedChannel registryChannel = ManagedChannelBuilder.forAddress(registryHost, registryPort)
                    .usePlaintext()
                    .build();

            ServiceRegistryGrpc.ServiceRegistryBlockingStub registryStub = ServiceRegistryGrpc.newBlockingStub(registryChannel);

            ServiceQuery query = ServiceQuery.newBuilder()
                    .setServiceName("UserProfileService")
                    .build();

            ServiceList services = registryStub.discover(query);
            registryChannel.shutdown();

            if (services.getServicesCount() == 0) {
                responseObserver.onError(new Throwable("UserProfileService not found via registry."));
                return;
            }

            ServiceInfo profileServiceInfo = services.getServices(0);

            // Connect to UserProfileService
            ManagedChannel userProfileChannel = ManagedChannelBuilder
                    .forAddress(profileServiceInfo.getServiceAddress(), profileServiceInfo.getServicePort())
                    .usePlaintext()
                    .build();

            UserProfileServiceGrpc.UserProfileServiceBlockingStub profileStub =
                    UserProfileServiceGrpc.newBlockingStub(userProfileChannel);

            UserProfile profile = profileStub.getProfile(
                    UserRequest.newBuilder().setUserId(request.getUserId()).build()
            );

            userProfileChannel.shutdown();

            // (Optional) Use profile info here for custom logic
            System.out.println("Fetched user zone: " + profile.getZone());

            // Return existing schedule
            BinSchedule schedule = scheduleMap.get(request.getUserId());

            if (schedule == null) {
                responseObserver.onError(new Throwable("No schedule found for user: " + request.getUserId()));
                return;
            }

            responseObserver.onNext(schedule);
            responseObserver.onCompleted();

        } catch (Exception e) {
            responseObserver.onError(e);
        }
    }
}

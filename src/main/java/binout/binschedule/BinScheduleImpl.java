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

    // store all bin schedules in memory by userId
    private final ConcurrentHashMap<String, BinSchedule> scheduleMap = new ConcurrentHashMap<>();

    // registry server info for service discovery
    private final String registryHost = "localhost";
    private final int registryPort = 50051;

    @Override
    public void setSchedule(BinSchedule request, StreamObserver<Ack> responseObserver) {
        // save/update the schedule for user
        scheduleMap.put(request.getUserId(), request);

        // send back success ack with message
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
            // first find UserProfileService via the registry
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
                // error if no UserProfileService found
                responseObserver.onError(new Throwable("UserProfileService not found via registry."));
                return;
            }

            // take first found UserProfileService info
            ServiceInfo profileServiceInfo = services.getServices(0);

            // connect to UserProfileService to get user profile
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

            // just printing zone info here, can use for logic if needed
            System.out.println("Fetched user zone: " + profile.getZone());

            // get schedule from local map
            BinSchedule schedule = scheduleMap.get(request.getUserId());

            if (schedule == null) {
                // error if schedule missing for user
                responseObserver.onError(new Throwable("No schedule found for user: " + request.getUserId()));
                return;
            }

            // send schedule back to client
            responseObserver.onNext(schedule);
            responseObserver.onCompleted();

        } catch (Exception e) {
            // catch any error and send to client
            responseObserver.onError(e);
        }
    }
}

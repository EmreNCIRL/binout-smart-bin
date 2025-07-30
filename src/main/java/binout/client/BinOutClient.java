package binout.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import binout.registry.ServiceRegistryGrpc;
import binout.registry.ServiceInfo;
import binout.registry.ServiceQuery;
import binout.registry.ServiceList;

import binout.userprofile.UserProfileServiceGrpc;
import binout.userprofile.UserProfile;
import binout.userprofile.UserRequest;

import binout.binschedule.BinScheduleServiceGrpc;
import binout.binschedule.BinSchedule;
import binout.binschedule.BinRequest;

import java.util.List;

public class BinOutClient {

    private final ManagedChannel registryChannel;
    private final ServiceRegistryGrpc.ServiceRegistryBlockingStub registryStub;

    public BinOutClient(String registryHost, int registryPort) {
        registryChannel = ManagedChannelBuilder.forAddress(registryHost, registryPort)
                .usePlaintext()
                .build();
        registryStub = ServiceRegistryGrpc.newBlockingStub(registryChannel);
    }

    public void shutdown() {
        registryChannel.shutdown();
    }

    private ServiceInfo discoverService(String serviceName) throws Exception {
        ServiceQuery query = ServiceQuery.newBuilder().setServiceName(serviceName).build();
        ServiceList services = registryStub.discover(query);
        List<ServiceInfo> serviceInfos = services.getServicesList();
        if (serviceInfos.isEmpty()) {
            throw new Exception(serviceName + " not found in registry");
        }
        return serviceInfos.get(0);
    }

    private UserProfileServiceGrpc.UserProfileServiceBlockingStub getUserProfileStub() throws Exception {
        ServiceInfo info = discoverService("UserProfileService");
        ManagedChannel channel = ManagedChannelBuilder.forAddress(info.getServiceAddress(), info.getServicePort())
                .usePlaintext()
                .build();
        return UserProfileServiceGrpc.newBlockingStub(channel);
    }

    private BinScheduleServiceGrpc.BinScheduleServiceBlockingStub getBinScheduleStub() throws Exception {
        ServiceInfo info = discoverService("BinScheduleService");
        ManagedChannel channel = ManagedChannelBuilder.forAddress(info.getServiceAddress(), info.getServicePort())
                .usePlaintext()
                .build();
        return BinScheduleServiceGrpc.newBlockingStub(channel);
    }

    public void createUserProfile(String userId, String name, String email) throws Exception {
        UserProfile profile = UserProfile.newBuilder()
                .setUserId(userId)
                .setName(name)
                .setEmail(email)
                .build();

        UserProfileServiceGrpc.UserProfileServiceBlockingStub stub = getUserProfileStub();
        binout.userprofile.Ack ackUser = stub.createProfile(profile);
        System.out.println("Create profile response: " + ackUser.getMessage());
    }

    public void getUserProfile(String userId) throws Exception {
        UserRequest request = UserRequest.newBuilder().setUserId(userId).build();

        UserProfileServiceGrpc.UserProfileServiceBlockingStub stub = getUserProfileStub();
        UserProfile profile = stub.getProfile(request);
        System.out.println("User profile: " + profile.getName() + ", " + profile.getEmail());
    }

    public void setBinSchedule(String userId, String greenDate, String blueDate, String brownDate, String redDate) throws Exception {
        BinSchedule schedule = BinSchedule.newBuilder()
                .setUserId(userId)
                .setGreenDate(greenDate)
                .setBlueDate(blueDate)
                .setBrownDate(brownDate)
                .setRedDate(redDate)
                .build();

        BinScheduleServiceGrpc.BinScheduleServiceBlockingStub stub = getBinScheduleStub();
        binout.binschedule.Ack ackBin = stub.setSchedule(schedule);
        System.out.println("Set schedule response: " + ackBin.getMessage());
    }

    public BinSchedule getBinSchedule(String userId) throws Exception {
        BinRequest request = BinRequest.newBuilder().setUserId(userId).build();

        BinScheduleServiceGrpc.BinScheduleServiceBlockingStub stub = getBinScheduleStub();
        return stub.getSchedule(request);
    }

    public static void main(String[] args) {
        try {
            BinOutClient client = new BinOutClient("localhost", 50051);

            client.createUserProfile("u123", "Em User", "em@example.com");
            client.getUserProfile("u123");

            client.setBinSchedule("u123", "2025-07-20", "2025-07-21", "2025-07-22", "2025-07-23");
            BinSchedule schedule = client.getBinSchedule("u123");
            System.out.println("Bin schedule: Green=" + schedule.getGreenDate() + ", Blue=" + schedule.getBlueDate());

            client.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
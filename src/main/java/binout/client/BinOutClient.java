package binout.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import binout.registry.ServiceRegistryGrpc;
import binout.registry.ServiceInfo;

import binout.userprofile.UserProfileServiceGrpc;
import binout.userprofile.UserProfile;
import binout.userprofile.UserRequest;

import binout.binschedule.BinScheduleServiceGrpc;
import binout.binschedule.BinSchedule;

import binout.alert.AlertServiceGrpc;
import binout.alert.Alert;
import binout.alert.AlertRequest;
import binout.alert.AlertList;

public class BinOutClient {

    private final ManagedChannel channel;
    private final ServiceRegistryGrpc.ServiceRegistryBlockingStub registryStub;
    private final UserProfileServiceGrpc.UserProfileServiceBlockingStub userProfileStub;
    private final BinScheduleServiceGrpc.BinScheduleServiceBlockingStub binScheduleStub;
    private final AlertServiceGrpc.AlertServiceBlockingStub alertStub;

    public BinOutClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        registryStub = ServiceRegistryGrpc.newBlockingStub(channel);
        userProfileStub = UserProfileServiceGrpc.newBlockingStub(channel);
        binScheduleStub = BinScheduleServiceGrpc.newBlockingStub(channel);
        alertStub = AlertServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() {
        channel.shutdown();
    }

    public void registerService(String serviceName) {
        ServiceInfo serviceInfo = ServiceInfo.newBuilder()
                .setServiceName(serviceName)
                .build();

        binout.registry.Ack ack = registryStub.register(serviceInfo);
        System.out.println("Register response: " + ack.getMessage());
    }

    public void createUserProfile(String userId, String name, String email, String phone) {
        UserProfile profile = UserProfile.newBuilder()
                .setUserId(userId)
                .setName(name)
                .setEmail(email)
                .setPhone(phone)
                .build();

        binout.userprofile.Ack ack = userProfileStub.createProfile(profile);
        System.out.println("Create profile response: " + ack.getMessage());
    }

    public void getUserProfile(String userId) {
        UserRequest request = UserRequest.newBuilder()
                .setUserId(userId)
                .build();

        UserProfile profile = userProfileStub.getProfile(request);
        System.out.println("User profile: " + profile.getName() + ", " + profile.getEmail());
    }

    public void setBinSchedule(String userId, String binType, String collectionDate) {
        BinSchedule schedule = BinSchedule.newBuilder()
                .setUserId(userId)
                .setBinType(binType)
                .setCollectionDate(collectionDate)
                .build();

        binout.binschedule.Ack ack = binScheduleStub.setSchedule(schedule);
        System.out.println("Set schedule response: " + ack.getMessage());
    }

    public void getBinSchedule(String userId) {
        binout.binschedule.BinRequest request = binout.binschedule.BinRequest.newBuilder()
                .setUserId(userId)
                .build();

        BinSchedule schedule = binScheduleStub.getSchedule(request);
        System.out.println("Bin schedule: " + schedule.getBinType() + " on " + schedule.getCollectionDate());
    }

    public void sendAlert(String alertId, String userId, String message, String timestamp) {
        Alert alert = Alert.newBuilder()
                .setAlertId(alertId)
                .setUserId(userId)
                .setMessage(message)
                .setTimestamp(timestamp)
                .build();

        binout.alert.Ack ack = alertStub.sendAlert(alert);
        System.out.println("Send alert response: " + ack.getMessage());
    }

    public void getAlerts(String userId) {
        AlertRequest request = AlertRequest.newBuilder()
                .setUserId(userId)
                .build();

        AlertList alertList = alertStub.getAlerts(request);
        System.out.println("Alerts for user " + userId + ":");
        for (Alert alert : alertList.getAlertsList()) {
            System.out.println(alert.getMessage() + " at " + alert.getTimestamp());
        }
    }

    public static void main(String[] args) {
        BinOutClient client = new BinOutClient("localhost", 50051);

        client.registerService("TestService");
        client.createUserProfile("u123", "Em User", "em@example.com", "1234567890");
        client.getUserProfile("u123");

        client.setBinSchedule("u123", "recycle", "2025-07-20");
        client.getBinSchedule("u123");

        client.sendAlert("a1", "u123", "Your bin will be collected tomorrow", "2025-07-19T10:00:00Z");
        client.getAlerts("u123");

        client.shutdown();
    }
}

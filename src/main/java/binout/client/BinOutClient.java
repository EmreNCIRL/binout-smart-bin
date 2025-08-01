/*
Emre Ketme
Distributed Systems CA
*/
package binout.client;

import binout.binschedule.BinSchedule;
import binout.binschedule.BinRequest;
import binout.binschedule.BinScheduleServiceGrpc;
import binout.binschedule.Ack;
import binout.recycling.RecyclingAdvisorServiceGrpc;
import binout.recycling.WasteType;
import binout.userprofile.UserProfile;
import binout.userprofile.UserProfileServiceGrpc;
import binout.userprofile.UserRequest;
import binout.discovery.JmDNSServiceDiscovery;
import io.grpc.ClientInterceptor;
import io.grpc.ClientInterceptors;
import io.grpc.Channel;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import io.grpc.stub.StreamObserver;

import javax.jmdns.ServiceInfo;

public class BinOutClient {
    private ManagedChannel managedChannel; // actual channel for shutdown
    private Channel channel; // intercepted channel for stubs
    private BinScheduleServiceGrpc.BinScheduleServiceBlockingStub scheduleStub;
    private UserProfileServiceGrpc.UserProfileServiceBlockingStub profileBlockingStub;
    private RecyclingAdvisorServiceGrpc.RecyclingAdvisorServiceBlockingStub recyclingStub;

    // setup channel with metadata (auth token)
    public BinOutClient(String host, int port) {
        setupChannel(host, port);
    }

    private void setupChannel(String host, int port) {
        Metadata metadata = new Metadata();
        Metadata.Key<String> tokenKey = Metadata.Key.of("token", Metadata.ASCII_STRING_MARSHALLER);
        metadata.put(tokenKey, "myprojecttoken");

        managedChannel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        ClientInterceptor interceptor = MetadataUtils.newAttachHeadersInterceptor(metadata);
        channel = ClientInterceptors.intercept(managedChannel, interceptor);

        scheduleStub = BinScheduleServiceGrpc.newBlockingStub(channel);
        profileBlockingStub = UserProfileServiceGrpc.newBlockingStub(channel);
        recyclingStub = RecyclingAdvisorServiceGrpc.newBlockingStub(channel);
    }

    // get bin schedule by city name
    public String getBinScheduleByCity(String city) {
        BinRequest request = BinRequest.newBuilder().setCity(city.toLowerCase()).build();
        BinSchedule schedule = scheduleStub.getSchedule(request);
        return "Green bin: " + schedule.getGreenDate() + "\n"
             + "Blue bin: " + schedule.getBlueDate() + "\n"
             + "Brown bin: " + schedule.getBrownDate();
    }

    // get user profile by userId
    public UserProfile getUserProfile(String userId) {
        UserRequest request = UserRequest.newBuilder().setUserId(userId).build();
        return profileBlockingStub.getProfile(request);
    }

    // get recycling tips by bin type
    public String getRecyclingTips(String binType) {
        WasteType request = WasteType.newBuilder().setType(binType).build();
        return recyclingStub.getRecyclingTip(request).getTips();
    }

    // shutdown channel cleanly
    public void shutdown() {
        if (managedChannel != null) {
            managedChannel.shutdown();
        }
    }

    // discover service with jmDNS and reset channel
    public ServiceInfo discoverService(String serviceType) {
        try (JmDNSServiceDiscovery discovery = new JmDNSServiceDiscovery()) {
            ServiceInfo serviceInfo = discovery.discoverService(serviceType, 5);
            if (serviceInfo != null) {
                System.out.println("Discovered service: " + serviceInfo.getName() +
                        " at " + serviceInfo.getHostAddresses()[0] +
                        ":" + serviceInfo.getPort());
                setupChannel(serviceInfo.getHostAddresses()[0], serviceInfo.getPort());
            } else {
                System.out.println("Service not found: " + serviceType);
            }
            return serviceInfo;
        } catch (Exception e) {
            System.err.println("Error discovering service " + serviceType + ": " + e.getMessage());
            return null;
        }
    }

    // client streaming: upload schedules to server
    public void uploadSampleSchedules() {
        StreamObserver<BinSchedule> requestObserver =
            BinScheduleServiceGrpc.newStub(channel).uploadSchedules(new StreamObserver<Ack>() {
                @Override
                public void onNext(Ack ack) {
                    System.out.println("Upload complete: " + ack.getMessage());
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("Upload error: " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("Stream completed.");
                }
            });

        BinSchedule s1 = BinSchedule.newBuilder()
            .setCity("Dublin")
            .setGreenDate("Monday")
            .setBlueDate("Thursday")
            .setBrownDate("Saturday")
            .build();

        BinSchedule s2 = BinSchedule.newBuilder()
            .setCity("Cork")
            .setGreenDate("Tuesday")
            .setBlueDate("Friday")
            .setBrownDate("Sunday")
            .build();

        BinSchedule s3 = BinSchedule.newBuilder()
            .setCity("Galway")
            .setGreenDate("Wednesday")
            .setBlueDate("Saturday")
            .setBrownDate("Monday")
            .build();

        requestObserver.onNext(s1);
        requestObserver.onNext(s2);
        requestObserver.onNext(s3);
        requestObserver.onCompleted();
    }

    // bi-directional streaming example
    public void scheduleChatSample() {
        StreamObserver<BinSchedule> requestObserver =
            BinScheduleServiceGrpc.newStub(channel).scheduleChat(new StreamObserver<Ack>() {
                @Override
                public void onNext(Ack ack) {
                    System.out.println("Server ack: " + ack.getMessage());
                }

                @Override
                public void onError(Throwable t) {
                    System.err.println("ScheduleChat error: " + t.getMessage());
                }

                @Override
                public void onCompleted() {
                    System.out.println("ScheduleChat completed.");
                }
            });

        BinSchedule s1 = BinSchedule.newBuilder()
            .setCity("Dublin")
            .setGreenDate("Mon")
            .setBlueDate("Thu")
            .setBrownDate("Sat")
            .build();

        BinSchedule s2 = BinSchedule.newBuilder()
            .setCity("Cork")
            .setGreenDate("Tue")
            .setBlueDate("Fri")
            .setBrownDate("Sun")
            .build();

        BinSchedule s3 = BinSchedule.newBuilder()
            .setCity("Galway")
            .setGreenDate("Wed")
            .setBlueDate("Sat")
            .setBrownDate("Mon")
            .build();

        requestObserver.onNext(s1);
        requestObserver.onNext(s2);
        requestObserver.onNext(s3);

        requestObserver.onCompleted();
    }
}

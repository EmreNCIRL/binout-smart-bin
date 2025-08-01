/*
Emre Ketme
Distributed Systems CA
*/
package binout.userprofile;

import io.grpc.stub.StreamObserver;
import java.util.concurrent.ConcurrentHashMap;

public class UserProfileImpl extends UserProfileServiceGrpc.UserProfileServiceImplBase {
    private final ConcurrentHashMap<String, UserProfile> userProfiles = new ConcurrentHashMap<>();

    public UserProfileImpl() {
        // Initialize some fake user profiles for demo
        userProfiles.put("user1", UserProfile.newBuilder()
            .setUserId("user1")
            .setCity("Dublin")
            .setServiceProvider("Panda")
            .build());
        userProfiles.put("user2", UserProfile.newBuilder()
            .setUserId("user2")
            .setCity("Cork")
            .setServiceProvider("Country Clean")
            .build());
        userProfiles.put("user3", UserProfile.newBuilder()
            .setUserId("user3")
            .setCity("Galway")
            .setServiceProvider("Greenstar")
            .build());
    }

    // Simple unary RPC: return user profile by userId or error
    @Override
    public void getProfile(UserRequest request, StreamObserver<UserProfile> responseObserver) {
        UserProfile profile = userProfiles.get(request.getUserId());
        if (profile != null) {
            responseObserver.onNext(profile);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new Throwable("User ID not found"));
        }
    }

    // Server-side streaming RPC: repeatedly send the same profile 3 times with delay
    @Override
    public void streamProfiles(UserRequest request, StreamObserver<UserProfile> responseObserver) {
        UserProfile profile = userProfiles.get(request.getUserId());
        if (profile != null) {
            try {
                for (int i = 0; i < 3; i++) {
                    responseObserver.onNext(profile);
                    Thread.sleep(1000);
                }
                responseObserver.onCompleted();
            } catch (InterruptedException e) {
                responseObserver.onError(e);
            }
        } else {
            responseObserver.onCompleted();
        }
    }
}

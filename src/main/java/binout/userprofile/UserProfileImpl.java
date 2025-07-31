package binout.userprofile;

import io.grpc.stub.StreamObserver;
import java.util.concurrent.ConcurrentHashMap;

public class UserProfileImpl extends UserProfileServiceGrpc.UserProfileServiceImplBase {

    // store user profiles in memory with userId as key
    private final ConcurrentHashMap<String, UserProfile> userProfiles = new ConcurrentHashMap<>();

    @Override
    public void createProfile(UserProfile request, StreamObserver<Ack> responseObserver) {
        // save or update profile in map
        userProfiles.put(request.getUserId(), request);

        // send back ack with success message
        Ack ack = Ack.newBuilder()
                .setSuccess(true)
                .setMessage("Profile created for user: " + request.getUserId())
                .build();

        responseObserver.onNext(ack);
        responseObserver.onCompleted();
    }

    @Override
    public void getProfile(UserRequest request, StreamObserver<UserProfile> responseObserver) {
        // get profile from map
        UserProfile profile = userProfiles.get(request.getUserId());

        if (profile == null) {
            // send error if not found
            responseObserver.onError(new Throwable("User profile not found"));
            return;
        }

        // return found profile
        responseObserver.onNext(profile);
        responseObserver.onCompleted();
    }
}

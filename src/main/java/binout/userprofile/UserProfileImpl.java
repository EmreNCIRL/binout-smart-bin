package binout.userprofile;

import io.grpc.stub.StreamObserver;
import java.util.concurrent.ConcurrentHashMap;

public class UserProfileImpl extends UserProfileServiceGrpc.UserProfileServiceImplBase {

    private final ConcurrentHashMap<String, UserProfile> userProfiles = new ConcurrentHashMap<>();

    @Override
    public void createProfile(UserProfile request, StreamObserver<Ack> responseObserver) {
        userProfiles.put(request.getUserId(), request);

        Ack ack = Ack.newBuilder()
                .setSuccess(true)
                .setMessage("Profile created for user: " + request.getUserId())
                .build();

        responseObserver.onNext(ack);
        responseObserver.onCompleted();
    }

    @Override
    public void getProfile(UserRequest request, StreamObserver<UserProfile> responseObserver) {
        UserProfile profile = userProfiles.get(request.getUserId());

        if (profile == null) {
            responseObserver.onError(new Throwable("User profile not found"));
            return;
        }

        responseObserver.onNext(profile);
        responseObserver.onCompleted();
    }
}

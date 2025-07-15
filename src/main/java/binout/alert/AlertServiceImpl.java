/*
note to self: version 4 to fix build errors
*/
package binout.alert;

import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class AlertServiceImpl extends AlertServiceGrpc.AlertServiceImplBase {

    private final ConcurrentHashMap<String, List<Alert>> alertsByUser = new ConcurrentHashMap<>();

    @Override
    public void sendAlert(Alert request, StreamObserver<Ack> responseObserver) {
        alertsByUser.computeIfAbsent(request.getUserId(), k -> new ArrayList<>()).add(request);

        Ack ack = Ack.newBuilder()
                .setSuccess(true)
                .setMessage("Alert sent to user: " + request.getUserId())
                .build();

        responseObserver.onNext(ack);
        responseObserver.onCompleted();
    }

    @Override
    public void getAlerts(AlertRequest request, StreamObserver<AlertList> responseObserver) {
        List<Alert> userAlerts = alertsByUser.getOrDefault(request.getUserId(), new ArrayList<>());

        AlertList alertList = AlertList.newBuilder()
                .addAllAlerts(userAlerts)
                .build();

        responseObserver.onNext(alertList);
        responseObserver.onCompleted();
    }
}

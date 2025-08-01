/*
Emre Ketme
Distributed Systems CA
*/

package binout.recycling;

import io.grpc.stub.StreamObserver;
import java.util.HashMap;
import java.util.Map;

// this class gives back tips about how to recycle bins (green, blue, brown)
public class RecyclingAdvisorImpl extends RecyclingAdvisorServiceGrpc.RecyclingAdvisorServiceImplBase {
    private final Map<String, String> tipsMap = new HashMap<>();
    
    public RecyclingAdvisorImpl() {
        // just puttin default tips for each bin type
        tipsMap.put("green", "Rinse containers before recycling");
        tipsMap.put("blue", "Flatten cardboard boxes");
        tipsMap.put("brown", "No plastic bags in organic waste");
    }

    // this method handles the rpc call and sends the tip back
    @Override
    public void getRecyclingTip(WasteType request, StreamObserver<RecyclingTip> responseObserver) {
        String tips = tipsMap.getOrDefault(request.getType().toLowerCase(), 
                         "No tips available for this bin type");
        responseObserver.onNext(RecyclingTip.newBuilder().setTips(tips).build());
        responseObserver.onCompleted();
    }
}

package binout.registry;

import io.grpc.stub.StreamObserver;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;

public class ServiceRegistryImpl extends ServiceRegistryGrpc.ServiceRegistryImplBase {

    private final ConcurrentHashMap<String, ServiceInfo> serviceMap = new ConcurrentHashMap<>();

    @Override
    public void register(ServiceInfo request, StreamObserver<Ack> responseObserver) {
        serviceMap.put(request.getServiceName(), request);

        Ack ack = Ack.newBuilder()
                .setSuccess(true)
                .setMessage("Service registered: " + request.getServiceName())
                .build();

        responseObserver.onNext(ack);
        responseObserver.onCompleted();
    }

    @Override
    public void discover(ServiceQuery request, StreamObserver<ServiceList> responseObserver) {
        List<ServiceInfo> matchedServices = new ArrayList<>();

        if (request.getServiceName().isEmpty()) {
            matchedServices.addAll(serviceMap.values());
        } else {
            ServiceInfo service = serviceMap.get(request.getServiceName());
            if (service != null) {
                matchedServices.add(service);
            }
        }

        ServiceList response = ServiceList.newBuilder()
                .addAllServices(matchedServices)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

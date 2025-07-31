package binout.registry;

import io.grpc.stub.StreamObserver;
import java.util.concurrent.ConcurrentHashMap;
import java.util.List;
import java.util.ArrayList;

public class ServiceRegistryImpl extends ServiceRegistryGrpc.ServiceRegistryImplBase {

    // store registered services by name, thread-safe
    private final ConcurrentHashMap<String, ServiceInfo> serviceMap = new ConcurrentHashMap<>();

    @Override
    public void register(ServiceInfo request, StreamObserver<Ack> responseObserver) {
        // add/update service info in map
        serviceMap.put(request.getServiceName(), request);

        // send back ack with success message
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
            // if no service name given, return all registered services
            matchedServices.addAll(serviceMap.values());
        } else {
            // otherwise return only the matching service if found
            ServiceInfo service = serviceMap.get(request.getServiceName());
            if (service != null) {
                matchedServices.add(service);
            }
        }

        // build response list and send back to client
        ServiceList response = ServiceList.newBuilder()
                .addAllServices(matchedServices)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

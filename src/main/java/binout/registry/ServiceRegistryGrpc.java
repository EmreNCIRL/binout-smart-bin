package binout.registry;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * The ServiceRegistry service definition
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.73.0)",
    comments = "Source: serviceregistry.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ServiceRegistryGrpc {

  private ServiceRegistryGrpc() {}

  public static final java.lang.String SERVICE_NAME = "binout.registry.ServiceRegistry";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<binout.registry.ServiceInfo,
      binout.registry.Ack> getRegisterMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Register",
      requestType = binout.registry.ServiceInfo.class,
      responseType = binout.registry.Ack.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<binout.registry.ServiceInfo,
      binout.registry.Ack> getRegisterMethod() {
    io.grpc.MethodDescriptor<binout.registry.ServiceInfo, binout.registry.Ack> getRegisterMethod;
    if ((getRegisterMethod = ServiceRegistryGrpc.getRegisterMethod) == null) {
      synchronized (ServiceRegistryGrpc.class) {
        if ((getRegisterMethod = ServiceRegistryGrpc.getRegisterMethod) == null) {
          ServiceRegistryGrpc.getRegisterMethod = getRegisterMethod =
              io.grpc.MethodDescriptor.<binout.registry.ServiceInfo, binout.registry.Ack>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Register"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  binout.registry.ServiceInfo.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  binout.registry.Ack.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceRegistryMethodDescriptorSupplier("Register"))
              .build();
        }
      }
    }
    return getRegisterMethod;
  }

  private static volatile io.grpc.MethodDescriptor<binout.registry.ServiceQuery,
      binout.registry.ServiceList> getDiscoverMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Discover",
      requestType = binout.registry.ServiceQuery.class,
      responseType = binout.registry.ServiceList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<binout.registry.ServiceQuery,
      binout.registry.ServiceList> getDiscoverMethod() {
    io.grpc.MethodDescriptor<binout.registry.ServiceQuery, binout.registry.ServiceList> getDiscoverMethod;
    if ((getDiscoverMethod = ServiceRegistryGrpc.getDiscoverMethod) == null) {
      synchronized (ServiceRegistryGrpc.class) {
        if ((getDiscoverMethod = ServiceRegistryGrpc.getDiscoverMethod) == null) {
          ServiceRegistryGrpc.getDiscoverMethod = getDiscoverMethod =
              io.grpc.MethodDescriptor.<binout.registry.ServiceQuery, binout.registry.ServiceList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Discover"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  binout.registry.ServiceQuery.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  binout.registry.ServiceList.getDefaultInstance()))
              .setSchemaDescriptor(new ServiceRegistryMethodDescriptorSupplier("Discover"))
              .build();
        }
      }
    }
    return getDiscoverMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ServiceRegistryStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServiceRegistryStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServiceRegistryStub>() {
        @java.lang.Override
        public ServiceRegistryStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServiceRegistryStub(channel, callOptions);
        }
      };
    return ServiceRegistryStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static ServiceRegistryBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServiceRegistryBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServiceRegistryBlockingV2Stub>() {
        @java.lang.Override
        public ServiceRegistryBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServiceRegistryBlockingV2Stub(channel, callOptions);
        }
      };
    return ServiceRegistryBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ServiceRegistryBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServiceRegistryBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServiceRegistryBlockingStub>() {
        @java.lang.Override
        public ServiceRegistryBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServiceRegistryBlockingStub(channel, callOptions);
        }
      };
    return ServiceRegistryBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ServiceRegistryFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ServiceRegistryFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ServiceRegistryFutureStub>() {
        @java.lang.Override
        public ServiceRegistryFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ServiceRegistryFutureStub(channel, callOptions);
        }
      };
    return ServiceRegistryFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * The ServiceRegistry service definition
   * </pre>
   */
  public interface AsyncService {

    /**
     * <pre>
     * Register a service
     * </pre>
     */
    default void register(binout.registry.ServiceInfo request,
        io.grpc.stub.StreamObserver<binout.registry.Ack> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterMethod(), responseObserver);
    }

    /**
     * <pre>
     * Discover services by name
     * </pre>
     */
    default void discover(binout.registry.ServiceQuery request,
        io.grpc.stub.StreamObserver<binout.registry.ServiceList> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDiscoverMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ServiceRegistry.
   * <pre>
   * The ServiceRegistry service definition
   * </pre>
   */
  public static abstract class ServiceRegistryImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ServiceRegistryGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ServiceRegistry.
   * <pre>
   * The ServiceRegistry service definition
   * </pre>
   */
  public static final class ServiceRegistryStub
      extends io.grpc.stub.AbstractAsyncStub<ServiceRegistryStub> {
    private ServiceRegistryStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceRegistryStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServiceRegistryStub(channel, callOptions);
    }

    /**
     * <pre>
     * Register a service
     * </pre>
     */
    public void register(binout.registry.ServiceInfo request,
        io.grpc.stub.StreamObserver<binout.registry.Ack> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Discover services by name
     * </pre>
     */
    public void discover(binout.registry.ServiceQuery request,
        io.grpc.stub.StreamObserver<binout.registry.ServiceList> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDiscoverMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ServiceRegistry.
   * <pre>
   * The ServiceRegistry service definition
   * </pre>
   */
  public static final class ServiceRegistryBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<ServiceRegistryBlockingV2Stub> {
    private ServiceRegistryBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceRegistryBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServiceRegistryBlockingV2Stub(channel, callOptions);
    }

    /**
     * <pre>
     * Register a service
     * </pre>
     */
    public binout.registry.Ack register(binout.registry.ServiceInfo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Discover services by name
     * </pre>
     */
    public binout.registry.ServiceList discover(binout.registry.ServiceQuery request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDiscoverMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service ServiceRegistry.
   * <pre>
   * The ServiceRegistry service definition
   * </pre>
   */
  public static final class ServiceRegistryBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ServiceRegistryBlockingStub> {
    private ServiceRegistryBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceRegistryBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServiceRegistryBlockingStub(channel, callOptions);
    }

    /**
     * <pre>
     * Register a service
     * </pre>
     */
    public binout.registry.Ack register(binout.registry.ServiceInfo request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Discover services by name
     * </pre>
     */
    public binout.registry.ServiceList discover(binout.registry.ServiceQuery request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDiscoverMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ServiceRegistry.
   * <pre>
   * The ServiceRegistry service definition
   * </pre>
   */
  public static final class ServiceRegistryFutureStub
      extends io.grpc.stub.AbstractFutureStub<ServiceRegistryFutureStub> {
    private ServiceRegistryFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ServiceRegistryFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ServiceRegistryFutureStub(channel, callOptions);
    }

    /**
     * <pre>
     * Register a service
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<binout.registry.Ack> register(
        binout.registry.ServiceInfo request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Discover services by name
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<binout.registry.ServiceList> discover(
        binout.registry.ServiceQuery request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDiscoverMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER = 0;
  private static final int METHODID_DISCOVER = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER:
          serviceImpl.register((binout.registry.ServiceInfo) request,
              (io.grpc.stub.StreamObserver<binout.registry.Ack>) responseObserver);
          break;
        case METHODID_DISCOVER:
          serviceImpl.discover((binout.registry.ServiceQuery) request,
              (io.grpc.stub.StreamObserver<binout.registry.ServiceList>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getRegisterMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              binout.registry.ServiceInfo,
              binout.registry.Ack>(
                service, METHODID_REGISTER)))
        .addMethod(
          getDiscoverMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              binout.registry.ServiceQuery,
              binout.registry.ServiceList>(
                service, METHODID_DISCOVER)))
        .build();
  }

  private static abstract class ServiceRegistryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ServiceRegistryBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return binout.registry.ServiceRegistryProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ServiceRegistry");
    }
  }

  private static final class ServiceRegistryFileDescriptorSupplier
      extends ServiceRegistryBaseDescriptorSupplier {
    ServiceRegistryFileDescriptorSupplier() {}
  }

  private static final class ServiceRegistryMethodDescriptorSupplier
      extends ServiceRegistryBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ServiceRegistryMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ServiceRegistryGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ServiceRegistryFileDescriptorSupplier())
              .addMethod(getRegisterMethod())
              .addMethod(getDiscoverMethod())
              .build();
        }
      }
    }
    return result;
  }
}

/*
Emre Ketme
Distributed Systems CA
*/

package binout.binschedule;

import io.grpc.stub.StreamObserver;
import java.util.concurrent.ConcurrentHashMap;

public class BinScheduleImpl extends BinScheduleServiceGrpc.BinScheduleServiceImplBase {
    private final ConcurrentHashMap<String, BinSchedule> citySchedules = new ConcurrentHashMap<>();

    public BinScheduleImpl() {
        citySchedules.put("dublin", BinSchedule.newBuilder()
            .setCity("dublin")
            .setGreenDate("Monday")
            .setBlueDate("Wednesday")
            .setBrownDate("Friday")
            .build());
        citySchedules.put("cork", BinSchedule.newBuilder()
            .setCity("cork")
            .setGreenDate("Tuesday")
            .setBlueDate("Thursday")
            .setBrownDate("Saturday")
            .build());
        citySchedules.put("galway", BinSchedule.newBuilder()
            .setCity("galway")
            .setGreenDate("Wednesday")
            .setBlueDate("Friday")
            .setBrownDate("Sunday")
            .build());
    }

    @Override
    public void getSchedule(BinRequest request, StreamObserver<BinSchedule> responseObserver) {
        BinSchedule schedule = citySchedules.get(request.getCity().toLowerCase());
        if (schedule != null) {
            responseObserver.onNext(schedule);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new Throwable("City schedule not found"));
        }
    }

    @Override
    public void streamSchedules(BinRequest request, StreamObserver<BinSchedule> responseObserver) {
        BinSchedule schedule = citySchedules.get(request.getCity().toLowerCase());
        if (schedule != null) {
            try {
                for (int i = 0; i < 3; i++) {
                    responseObserver.onNext(schedule);
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

    @Override
    public StreamObserver<BinSchedule> uploadSchedules(StreamObserver<Ack> responseObserver) {
        return new StreamObserver<BinSchedule>() {
            int count = 0;

            @Override
            public void onNext(BinSchedule schedule) {
                citySchedules.put(schedule.getCity().toLowerCase(), schedule);
                count++;
                System.out.println("Uploaded schedule for: " + schedule.getCity());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("UploadSchedules error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                Ack ack = Ack.newBuilder()
                    .setSuccess(true)
                    .setMessage("Uploaded " + count + " schedules.")
                    .build();
                responseObserver.onNext(ack);
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<BinSchedule> scheduleChat(StreamObserver<Ack> responseObserver) {
        return new StreamObserver<BinSchedule>() {
            @Override
            public void onNext(BinSchedule schedule) {
                String city = schedule.getCity().toLowerCase();
                citySchedules.put(city, schedule);
                System.out.println("Chat updated schedule for: " + city);

                Ack ack = Ack.newBuilder()
                    .setSuccess(true)
                    .setMessage("Received schedule for " + city)
                    .build();
                responseObserver.onNext(ack);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("ScheduleChat error: " + t.getMessage());
                responseObserver.onCompleted();
            }

            @Override
            public void onCompleted() {
                System.out.println("ScheduleChat completed");
                responseObserver.onCompleted();
            }
        };
    }
}

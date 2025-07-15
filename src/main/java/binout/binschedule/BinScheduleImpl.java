/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package binout.binschedule;

/**
 *
 * @author emurekun
 */

import io.grpc.stub.StreamObserver;
import java.util.concurrent.ConcurrentHashMap;

public class BinScheduleImpl extends BinScheduleServiceGrpc.BinScheduleServiceImplBase {

    private final ConcurrentHashMap<String, BinSchedule> scheduleMap = new ConcurrentHashMap<>();

    @Override
    public void setSchedule(BinSchedule request, StreamObserver<Ack> responseObserver) {
        scheduleMap.put(request.getUserId(), request);

        Ack ack = Ack.newBuilder()
                .setSuccess(true)
                .setMessage("Schedule set for user: " + request.getUserId())
                .build();

        responseObserver.onNext(ack);
        responseObserver.onCompleted();
    }

    @Override
    public void getSchedule(BinRequest request, StreamObserver<BinSchedule> responseObserver) {
        BinSchedule schedule = scheduleMap.get(request.getUserId());

        if (schedule == null) {
            responseObserver.onError(new Throwable("No schedule found for user: " + request.getUserId()));
            return;
        }

        responseObserver.onNext(schedule);
        responseObserver.onCompleted();
    }
}


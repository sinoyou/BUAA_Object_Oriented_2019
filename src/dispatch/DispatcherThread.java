package dispatch;

import com.oocourse.elevator3.PersonRequest;
import elevator.ElevatorThread;
import tool.DebugPrint;

import java.util.ArrayList;

public class DispatcherThread extends Thread {
    private RequestList requestList;
    final private ArrayList<ElevatorThread> registers; // 考虑线程安全
    private String type = "Dispatcher";

    public DispatcherThread(RequestList list) {
        this.requestList = list;
        registers = new ArrayList<>();
    }

    // ---------- Thread funtcion ----------
    @Override
    public void run() {
        try {
            while (true) {
                PersonRequest oneRequest = requestList.getRequest();
                if (oneRequest == null) {
                    break;
                }
                DebugPrint.errPrint(type,String.format("Get a New Request '%s'",
                    oneRequest.toString()));
                dispatch(oneRequest);
            }
            // 炒鱿鱼
            squidAll();
            DebugPrint.threadStatePrint(type,"Normal ShutDown");
        } catch (InterruptedException e) {
            DebugPrint.threadStatePrint(type,"Error ShutDown");
        }
    }

    // ---------- Dispatch and Ctrl Function ----------
    private void dispatch(PersonRequest oneRequest) {
        // have only one elevator, so give it directly
        ElevatorThread elevator = registers.get(0);
        notifyElevator(elevator, oneRequest);
    }

    private void squidAll() {
        synchronized (registers){
            for (ElevatorThread elevator : registers) {
                notifyElevator(elevator, true);
            }
        }
    }

    // ---------- Message and Notify Function ----------
    public void register(ElevatorThread elevatorThread) {
        synchronized (registers){
            registers.add(elevatorThread);
        }
    }

    private void notifyElevator(ElevatorThread elevator,
                                PersonRequest personRequest) {
        elevator.getNotified(personRequest);
    }

    private void notifyElevator(ElevatorThread elevator, Boolean terminate) {
        elevator.getNotified(terminate);
    }
}

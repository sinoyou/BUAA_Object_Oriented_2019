package dispatch;

import com.oocourse.elevator2.PersonRequest;
import elevator.ElevatorThread;

import java.util.ArrayList;

public class DispatcherThread extends Thread {
    private RequestList requestList;
    private ArrayList<ElevatorThread> registers; // 后期可能考虑线程安全

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
                System.err.println(String.format("<Dispatcher>:Get a New " +
                    "Request '%s'", oneRequest.toString()));
                dispatch(oneRequest);
            }
            // 炒鱿鱼
            squidAll();
            System.err.println("@Dispatcher Shutdown With Normal State.");
        } catch (InterruptedException e) {
            System.err.println("@Dispatcher Shutdown With Error State.");
        }
    }

    // ---------- Dispatch and Ctrl Function ----------
    private void dispatch(PersonRequest oneRequest) {
        // have only one elevator, so give it directly
        ElevatorThread elevator = registers.get(0);
        notifyElevator(elevator, oneRequest);
    }

    private void squidAll() {
        for (ElevatorThread elevator : registers) {
            notifyElevator(elevator, true);
        }
    }

    // ---------- Message and Notify Function ----------
    public void register(ElevatorThread elevatorThread) {
        registers.add(elevatorThread);
    }

    private void notifyElevator(ElevatorThread elevator,
                                PersonRequest personRequest) {
        elevator.getNotified(personRequest);
    }

    private void notifyElevator(ElevatorThread elevator, Boolean terminate) {
        elevator.getNotified(terminate);
    }
}

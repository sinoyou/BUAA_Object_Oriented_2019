package dispatch;

import com.oocourse.elevator3.PersonRequest;
import elevator.ElevatorThread;
import tool.DebugPrint;
import tool.FloorTool;

import java.util.ArrayList;

public class DispatcherThread extends Thread {
    private RequestList requestList;
    private final ArrayList<ElevatorThread> registers; // 考虑线程安全
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
                DebugPrint.errPrint(type,
                    String.format("Get a New Request '%s'",
                        oneRequest.toString()));
                dispatch(oneRequest);
            }
            // 炒鱿鱼
            squidAll();
            DebugPrint.threadStatePrint(type, "Normal ShutDown");
        } catch (InterruptedException e) {
            DebugPrint.threadStatePrint(type, "Error ShutDown");
        }
    }

    // ---------- Dispatch and Ctrl Function ----------
    private void dispatch(PersonRequest oneRequest) {
        ArtificialDecision artificial =
            new ArtificialDecision(oneRequest, registers);
        notifyElevator(artificial.getTaskElevator(), artificial.getTarTask());
        /*
        int from = oneRequest.getFromFloor();
        int to = oneRequest.getToFloor();
        // can go there directly
        boolean flag = false;
        for (ElevatorThread register : registers) {
            if (FloorTool.isDirectTransport(from,
                to,
                register.getLegalFloor())) {
                flag = true;
                notifyElevator(register, oneRequest);
                break;
            }
        }
        // can not go there directly
        if (!flag) {
            PersonRequest newOne = new PersonRequest(from,
                1,
                oneRequest.getPersonId());
            for (ElevatorThread register : registers) {
                if (FloorTool.isDirectTransport(from,
                    1,
                    register.getLegalFloor())) {
                    notifyElevator(register, newOne);
                    break;
                }
            }
        }
        */
    }

    private void squidAll() {
        synchronized (registers) {
            for (ElevatorThread elevator : registers) {
                notifyElevator(elevator, true);
            }
        }
    }

    // ---------- Message and Notify Function ----------
    public void register(ElevatorThread elevatorThread) {
        synchronized (registers) {
            registers.add(elevatorThread);
        }
    }

    private void notifyElevator(ElevatorThread elevator,
                                PersonRequest personRequest) {
        DebugPrint.errPrint(type, String.format("Task <%s> dispatched to %s",
            personRequest.toString(), elevator.getname()));
        elevator.getNotified(personRequest);
    }

    private void notifyElevator(ElevatorThread elevator, Boolean terminate) {
        elevator.getNotified(terminate);
    }

    // ---------- Task Feedback Function ----------
    public void taskFeedback(int id, int floor) {
        requestList.taskFeedback(id, floor);
    }
}

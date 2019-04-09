package elevator;

import com.oocourse.TimableOutput;
import com.oocourse.elevator2.PersonRequest;
import constant.FloorTool;
import constant.TimeConst;
import dispatch.DispatcherThread;

public class ElevatorThread extends Thread {
    private final PassengerList passList;         // sub ctrl list
    private int floorIndex;                       // use Index to ignore neg num
    private int moveDirection;                    // Up, Down, Still
    private boolean isDoorOpen;                   // Door state
    private int passIn;                           // number of pass in elevator

    public ElevatorThread(DispatcherThread dispatcherThread) {
        passList = new PassengerList(this);
        floorIndex = FloorTool.floor2Index(1);
        moveDirection = FloorTool.setDirectionStill();
        isDoorOpen = false;
        passIn = 0;
        // 观察者模式构建
        dispatcherThread.register(this);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Boolean query = passList.terminateQuery();
                // if query, it mean: passList is empty and no more request
                if (query) {
                    break;
                } else {
                    actionCtrlByStep();
                }
            }
            makeSureDoorClose();
            System.err.println("@Elevator Shutdown with Normal State.");
        } catch (InterruptedException e) {
            System.err.println("@Elevator Shutdown with Error State.");
        }
    }

    // ---------- Single Elevator Action Controller(Step by Step) ----------
    private void actionCtrlByStep() throws InterruptedException {
        // basic location movement
        move();

        // check if need to open door for passenger exchange.
        // However, dispatcher can put new request after this, so door state
        // must be checked later. (this action is to save time in passList.)
        if (passList.taskNow(floorIndex, moveDirection)) {
            makeSureDoorOpen();
        }

        // check-in and check-out passenger (passenger with wrong direction can
        // be ignored to save door time.)
        passList.passengerMove(floorIndex, moveDirection);

        // check if need to change direction
        int dir = moveDirection;
        moveDirection = passList.directionCheck(moveDirection, floorIndex);
        if (dir != moveDirection) {
            System.err.println(String.format("<Elevator>:Direction Change:" +
                    "'%s' ---> '%s'", FloorTool.getDirectionName(dir),
                FloorTool.getDirectionName(moveDirection)));
        }
    }


    // ---------- Elevator Action Function ----------
    // action of elevator
    private void makeSureDoorOpen() throws InterruptedException {
        if (!isDoorOpen) {
            isDoorOpen = true;
            TimableOutput.println(String.format("OPEN-%d",
                FloorTool.index2Floor(floorIndex)));
            sleep(TimeConst.doorOpen);
        }
    }

    private void makeSureDoorClose() throws InterruptedException {
        if (isDoorOpen) {
            sleep(TimeConst.doorClose);
            TimableOutput.println(String.format("CLOSE-%d",
                FloorTool.index2Floor(floorIndex)));
            isDoorOpen = false;
        }
    }

    protected void kickOut(int id) throws InterruptedException {
        makeSureDoorOpen();
        passIn--;
        TimableOutput.println(String.format("OUT-%d-%d",
            id, FloorTool.index2Floor(floorIndex)));
    }

    protected void pullIn(int id) throws InterruptedException {
        makeSureDoorOpen();
        passIn++;
        TimableOutput.println(String.format("IN-%d-%d",
            id, FloorTool.index2Floor(floorIndex)));
    }

    // move elevator in right direction. when STILL state, no action performed.
    private void move() throws InterruptedException {
        if (FloorTool.isUp(moveDirection)) {
            makeSureDoorClose();
            sleep(TimeConst.moveOneFloor);
            floorIndex++;
            TimableOutput.println(String.format("ARRIVE-%d",
                FloorTool.index2Floor(floorIndex)));
        } else if (FloorTool.isDown(moveDirection)) {
            makeSureDoorClose();
            sleep(TimeConst.moveOneFloor);
            floorIndex--;
            TimableOutput.println(String.format("ARRIVE-%d",
                FloorTool.index2Floor(floorIndex)));
        } else if (FloorTool.isStill(moveDirection)) {
            sleep(3);
        }
    }


    // ---------- DispatcherThread Helper Function ----------

    public void getNotified(PersonRequest personRequest) {
        addNewTask(personRequest);
    }

    public void getNotified(Boolean isTerminate) {
        if (isTerminate) {
            noMoreRequest();
        }
    }

    private void addNewTask(PersonRequest personRequest) {
        passList.createNewTask(personRequest);
    }

    private void noMoreRequest() {
        passList.setNoMoreTask();
    }

    // ---------- Elevator State Get Function ----------
    public int getPassIn() {
        return passIn;
    }
}

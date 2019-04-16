package elevator;

import com.oocourse.TimableOutput;
import com.oocourse.elevator3.PersonRequest;
import tool.DebugPrint;
import tool.FloorTool;
import dispatch.DispatcherThread;

public class ElevatorThread extends Thread {
    private final PassengerList passList;         // sub ctrl list
    private int floorIndex;                       // use Index to ignore neg num
    private int moveDirection;                    // Up, Down, Still
    private boolean isDoorOpen;                   // Door state
    private int passIn;                           // number of pass in elevator
    private int moveSpeed;                    // elevator move speed
    private int[] legalFloor;                     // floors to be allowed open.
    private int doorSpeed;                        // door open/close speed.
    private String name;                          // elevator's name
    private String type = "Elevator";
    private int maxAmount;                           // max passenger
    private DispatcherThread dispatcher;
    private int taskCount;

    public ElevatorThread(DispatcherThread dispatcherThread,
                          String name,
                          int moveSpeed,
                          int doorSpeed,
                          int[] legalFloor,
                          int maxAmount) {
        passList = new PassengerList(this);
        floorIndex = FloorTool.floor2Index(1);
        moveDirection = FloorTool.setDirectionStill();
        isDoorOpen = false;
        passIn = 0;
        taskCount = 0;
        // 速度和合法楼层构建
        this.moveSpeed = moveSpeed;
        this.doorSpeed = doorSpeed;
        this.legalFloor = legalFloor;
        this.name = name;
        this.maxAmount = maxAmount;
        // 观察者模式构建
        dispatcher = dispatcherThread;
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
            DebugPrint.threadStatePrint(type, name,
                String.format("Normal ShutDown With %d Tasks", taskCount));
        } catch (InterruptedException e) {
            DebugPrint.threadStatePrint(type, name,
                String.format("Error ShutDown With %d Tasks", taskCount));
        }
    }

    // ---------- Single Elevator Action Controller(Step by Step) ----------
    private void actionCtrlByStep() throws InterruptedException {
        // basic location movement
        move();

        // update task
        passList.taskValidate();

        boolean taskNow = passList.taskNow(floorIndex, moveDirection);
        if (isFloorLegal(FloorTool.index2Floor(floorIndex))) {
            // check if need to open door for passenger exchange.
            // However, dispatcher can put new request after this, so door state
            // must be checked later. (this action is to save time in passList.)
            if (taskNow) {
                makeSureDoorOpen();
            }

            // check-in and check-out passenger
            // passenger with wrong direction can be ignored to save door time.
            passList.passengerMove(floorIndex, moveDirection);
        }

        // check if need to change direction
        int dir = moveDirection;
        moveDirection = passList.directionCheck(moveDirection, floorIndex);
        if (dir != moveDirection) {
            DebugPrint.errPrint(type, name,
                String.format("Direction Change: %s -> %s",
                    FloorTool.getDirectionName(dir),
                    FloorTool.getDirectionName(moveDirection)));
        }

    }


    // ---------- Elevator Action Function ----------
    // action of elevator
    private void makeSureDoorOpen() throws InterruptedException {
        if (!isDoorOpen) {
            isDoorOpen = true;
            TimableOutput.println(String.format("OPEN-%d-%s",
                FloorTool.index2Floor(floorIndex), name));
            sleep(this.doorSpeed);
        }
    }

    private void makeSureDoorClose() throws InterruptedException {
        if (isDoorOpen) {
            sleep(this.doorSpeed);
            TimableOutput.println(String.format("CLOSE-%d-%s",
                FloorTool.index2Floor(floorIndex), name));
            isDoorOpen = false;
        }
    }

    protected void kickOut(int id) throws InterruptedException {
        makeSureDoorOpen();
        passIn--;
        dispatcher.taskFeedback(id, FloorTool.index2Floor(floorIndex));
        TimableOutput.println(String.format("OUT-%d-%d-%s",
            id, FloorTool.index2Floor(floorIndex), name));
    }

    protected void pullIn(int id) throws InterruptedException {
        makeSureDoorOpen();
        passIn++;
        TimableOutput.println(String.format("IN-%d-%d-%s",
            id, FloorTool.index2Floor(floorIndex), name));
    }

    // move elevator in right direction. when STILL state, no action performed.
    private void move() throws InterruptedException {
        if (FloorTool.isUp(moveDirection)) {
            makeSureDoorClose();
            sleep(this.moveSpeed);
            floorIndex++;
            TimableOutput.println(String.format("ARRIVE-%d-%s",
                FloorTool.index2Floor(floorIndex), name));
        } else if (FloorTool.isDown(moveDirection)) {
            makeSureDoorClose();
            sleep(this.moveSpeed);
            floorIndex--;
            TimableOutput.println(String.format("ARRIVE-%d-%s",
                FloorTool.index2Floor(floorIndex), name));
        } else if (FloorTool.isStill(moveDirection)) {
            sleep(3);
        }
    }


    // ---------- DispatcherThread Helper Function ----------

    public void getNotified(PersonRequest personRequest) {
        taskCount++;
        passList.addNewTask(personRequest);
    }

    public void getNotified(Boolean isTerminate) {
        if (isTerminate) {
            noMoreRequest();
        }
    }

    private void noMoreRequest() {
        passList.setNoMoreTask();
    }

    public boolean isDirectTask(int from, int to) {
        return FloorTool.isDirectTransport(from, to, legalFloor);
    }


    // ---------- Elevator State Get Function ----------
    // 辨析Running task, Passin, maxAmount：电梯任务数、电梯内乘客数、电梯限载人数
    public int getTotalTask() {
        return passList.getTotalTask();
    }

    public int getPassIn() {
        return passIn;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public String getname() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int[] getLegalFloor() {
        return legalFloor.clone();
    }

    public boolean isFloorLegal(int floor) {
        return FloorTool.isLegalFloorIndex(
            FloorTool.floor2Index(floor), legalFloor);
    }

    public boolean isFull() {
        return passIn == maxAmount;
    }

    public int getMoveDirection() {
        return moveDirection;
    }

    public int getFloor() {
        return FloorTool.index2Floor(floorIndex);
    }
}

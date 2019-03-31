import com.oocourse.TimableOutput;
import com.oocourse.elevator1.PersonRequest;

public class Elevator extends Thread {
    // define thread parameter
    private Commander commander;

    // define elevator location parameter
    private int floor;
    private boolean isDoorOpen;

    Elevator(Commander commander) {
        floor = 1;
        isDoorOpen = false;
        this.commander = commander;
    }

    @Override
    public void run() {
        try {
            while (true) {
                PersonRequest oneRequest = commander.getRequest();
                if (oneRequest == null) {
                    break;
                }

                int id = oneRequest.getPersonId();
                int from = oneRequest.getFromFloor();
                int to = oneRequest.getToFloor();
                dispatch(id, from, to);
            }
            // final state must be closed.
            makeSureDoorClose();
            System.err.println("Elevator Shutdown With Normal State.");
        } catch (InterruptedException e) {
            System.err.println("Elevator Shutdown With Blocked State.");
        }
    }

    private void dispatch(int id, int start, int end)
        throws InterruptedException {
        move(this.floor, start);
        pullIn(id, start);
        move(start, end);
        kickOut(id, end);
    }

    // action of elevator
    private void makeSureDoorOpen() throws InterruptedException {
        if (!isDoorOpen) {
            isDoorOpen = true;
            TimableOutput.println(String.format("OPEN-%d", floor));
            sleep(TimeConst.doorOpen);
        }
    }

    private void makeSureDoorClose() throws InterruptedException {
        if (isDoorOpen) {
            isDoorOpen = false;
            sleep(TimeConst.doorClose);
            TimableOutput.println(String.format("CLOSE-%d", floor));
        }
    }

    private void kickOut(int id, int floor) throws InterruptedException {
        makeSureDoorOpen();
        TimableOutput.println(String.format("OUT-%d-%d", id, floor));
    }

    private void pullIn(int id, int floor) throws InterruptedException {
        makeSureDoorOpen();
        TimableOutput.println(String.format("IN-%d-%d", id, floor));
    }

    private void move(int from, int to) throws InterruptedException {
        // special occasion:same floor so don't move
        if (from != to) {
            makeSureDoorClose();
            sleep(Math.abs(from - to) * TimeConst.moveOneFloor);
            floor = to;
        }
    }
}

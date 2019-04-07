package elevator;

import com.oocourse.elevator2.PersonRequest;
import constant.FloorTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

// a passenger list is unique for an elevator.
// All methods to access passenger list must through Elevator.
// However, not only elevator thread can execute access method of the list,
// dispatcher and (servant) can too.
public class PassengerList {
    private ArrayList<ArrayList<Integer>> pickList;     // contain floor index
    private ArrayList<ArrayList<Integer>> putList;      // contain floor index
    private HashMap<Integer, Integer> toMap;            // <id,floor index>
    private HashMap<Integer, Integer> fromMap;          // <id,floor index>
    private boolean noMoreTask;
    private int runningTask;
    private ElevatorThread elevator;

    // ---------- Initial Function ----------
    public PassengerList(ElevatorThread elevator) {
        noMoreTask = false;
        runningTask = 0;
        // initial pickList and putList
        int indexLength = FloorTool.getFloorAmount();
        pickList = new ArrayList<>();
        putList = new ArrayList<>();
        for (int i = 0; i < indexLength; i++) {
            pickList.add(i, new ArrayList<>());
            putList.add(i, new ArrayList<>());
        }
        // initial target list
        toMap = new HashMap<>();
        fromMap = new HashMap<>();
        // initial thread
        this.elevator = elevator;
    }

    // ---------- Elevator Help and Advice Function ----------

    // to tell elevator if need to open door in advance.
    protected synchronized boolean taskNow(int floorIndex) {
        if (!pickList.get(floorIndex).isEmpty() ||
            !putList.get(floorIndex).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    // to tell elevator if need to continue move in the same direction.
    protected synchronized boolean hasTask(int floorIndex, int direction) {
        if (FloorTool.isDown(direction)) {
            for (int i = floorIndex - 1; i >= 0; i--) {
                if (!pickList.get(i).isEmpty() || !putList.get(i).isEmpty()) {
                    return true;
                }
            }
            return false;
        } else {
            for (int i = floorIndex + 1; i < FloorTool.getFloorAmount(); i++) {
                if (!pickList.get(i).isEmpty() || !putList.get(i).isEmpty()) {
                    return true;
                }
            }
            return false;
        }
    }

    protected synchronized int directionCheck(
        int moveDirection, int floorIndex) {
        boolean upTaskState = hasTask(floorIndex, FloorTool.setDirectionUp());
        boolean downTaskState =
            hasTask(floorIndex, FloorTool.setDirectionDown());
        // condition [上有下有，上有下无，上无下有，上无下无] * [up,down,still]
        // still 组
        if (FloorTool.isStill(moveDirection)) {
            if (upTaskState && !downTaskState) {
                return FloorTool.setDirectionUp();
            } else if (!upTaskState && downTaskState) {
                return FloorTool.setDirectionDown();
            } else if (upTaskState && downTaskState) {
                return FloorTool.setDirectionUp();
            }
        }
        // up 组
        else if (FloorTool.isUp(moveDirection)) {
            if (!upTaskState && downTaskState) {
                return FloorTool.setDirectionDown();
            } else if (!upTaskState && !downTaskState) {
                return FloorTool.setDirectionStill();
            }
        }
        // down 组
        else if (FloorTool.isDown(moveDirection)) {
            if (upTaskState && !downTaskState) {
                return FloorTool.setDirectionUp();
            } else if (!upTaskState && !downTaskState) {
                return FloorTool.setDirectionStill();
            }
        }
        return moveDirection;
    }

    // according to NoMoreRequest and Unfinished Task Amount, to give the
    // elevator a vacation(wait) or give out terminate signal.
    protected synchronized boolean terminateQuery()
        throws InterruptedException {
        while (runningTask == 0) {
            if (noMoreTask) {
                return true;
            }
            System.err.println("<Elevator>:begin to rest.");
            wait();
            System.err.println("<Elevator>:recover");
        }
        return false;
    }

    // ---------- PassengerList Maintain Function ----------
    // add to pick list and target list <--- elevator.static <--- dispatcher
    protected synchronized void createNewTask(PersonRequest personRequest) {
        System.err.println(String.format("<Elevator>: A new Task '%s' Have " +
            "Received", personRequest.toString()));
        runningTask++;
        int id = personRequest.getPersonId();
        int from = FloorTool.floor2Index(personRequest.getFromFloor());
        int to = FloorTool.floor2Index(personRequest.getToFloor());
        pickList.get(from).add(id);
        toMap.put(id, to);
        fromMap.put(id, from);
        notifyAll();
    }

    // just for unit test.
    protected synchronized void createNewTask(int id, int from, int to) {
        runningTask++;
        pickList.get(from).add(id);
        toMap.put(id, to);
        fromMap.put(id, from);
        notifyAll();
    }

    // in present floor, passList will help elevator pick and put correct pass.
    protected synchronized void passengerMove(int floorIndex)
        throws InterruptedException {
        Iterator<Integer> pickListPiece =
            pickList.get(floorIndex).iterator();
        // first pick up passenger
        while (pickListPiece.hasNext()) {
            int id = pickListPiece.next();
            elevator.pullIn(id);
            pickListPiece.remove();
            havePickedPassenger(id);
        }

        Iterator<Integer> putListPiece =
            putList.get(floorIndex).iterator();
        // then put out passenger
        while (putListPiece.hasNext()) {
            int id = putListPiece.next();
            elevator.kickOut(id);
            putListPiece.remove();
            havePutPassenger(id);
        }
    }

    // delete from pickList and add to putList <--- elevator.runMethod
    protected synchronized void havePickedPassenger(int id) {
        fromMap.remove(id);
        int toFloor = toMap.get(id);
        // pickList.get(fromFloor).remove(new Integer(id));
        putList.get(toFloor).add(id);
    }

    // delete from putList == Task Finished
    protected synchronized void havePutPassenger(int id) {
        toMap.remove(id);
        // putList.get(toFloor).remove(new Integer(id));
        runningTask--;
        System.err.println(String.format("<Elevator>:ID of Task: %d Finished."
            , id));
    }


    // ---------- Terminate State Function ----------
    protected synchronized void setNoMoreTask() {
        noMoreTask = true;
        System.err.println("<Elevator>:Have Received Input Terminate Signal.");
        notifyAll();
    }
}

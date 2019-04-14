package elevator;

import com.oocourse.elevator3.PersonRequest;
import tool.DebugPrint;
import tool.FloorTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

// a passenger list is unique for an elevator.
// All methods to access passenger list must through Elevator.
// However, not only elevator thread can execute access method of the list,
// dispatcher and (servant) can too.
public class PassengerList {
    private ArrayList<ArrayList<Integer>> upPickList;     // contain floor index
    private ArrayList<ArrayList<Integer>> downPickList;   // contain floor index
    private ArrayList<ArrayList<Integer>> putList;        // contain floor index
    private HashMap<Integer, Integer> toMap;              // <id,floor index>
    private HashMap<Integer, Integer> fromMap;            // <id,floor index>
    private LinkedList<Integer> orderList;                // id, record order
    private boolean noMoreTask;
    private int runningTask;
    private ElevatorThread elevator;
    private ArrayList<PersonRequest> taskCache;           // tasks to be added.

    // ---------- Initial Function ----------
    public PassengerList(ElevatorThread elevator) {
        noMoreTask = false;
        runningTask = 0;
        upPickList = new ArrayList<>();
        downPickList = new ArrayList<>();
        putList = new ArrayList<>();
        // initial upPickList and putList
        int indexLength = FloorTool.getFloorAmount();
        for (int i = 0; i < indexLength; i++) {
            upPickList.add(i, new ArrayList<>());
            downPickList.add(i, new ArrayList<>());
            putList.add(i, new ArrayList<>());
        }
        // initial target list
        toMap = new HashMap<>();
        fromMap = new HashMap<>();
        // initial orderList
        orderList = new LinkedList<>();
        // initial taskList
        taskCache = new ArrayList<>();
        // initial thread
        this.elevator = elevator;
    }

    // ---------- Elevator Help and Advice Function ----------

    // to tell elevator if need to open door.
    protected synchronized boolean taskNow(int floorIndex, int moveDirection) {
        boolean upPass = !upPickList.get(floorIndex).isEmpty();
        boolean downPass = !downPickList.get(floorIndex).isEmpty();
        boolean putPass = !putList.get(floorIndex).isEmpty();
        // check passenger limit
        if (!putPass && elevator.isFull()) {
            return false;
        }
        if (FloorTool.isStill(moveDirection) &&
            (upPass || downPass || putPass)) {
            return true;
        } else if (FloorTool.isDown(moveDirection) && (downPass || putPass)) {
            return true;
        } else if (FloorTool.isUp(moveDirection) && (upPass || putPass)) {
            return true;
        }
        return false;
    }

    // to tell elevator if need to continue move in the same direction.
    protected synchronized boolean hasTask(int floorIndex, int direction) {
        if (FloorTool.isDown(direction)) {
            for (int i = floorIndex - 1; i >= 0; i--) {
                if (!upPickList.get(i).isEmpty() ||
                    !downPickList.get(i).isEmpty() ||
                    !putList.get(i).isEmpty()) {
                    return true;
                }
            }
            return false;
        } else {
            for (int i = floorIndex + 1; i < FloorTool.getFloorAmount(); i++) {
                if (!upPickList.get(i).isEmpty() ||
                    !downPickList.get(i).isEmpty() ||
                    !putList.get(i).isEmpty()) {
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
                // Version 2.0 New Logic: When both dir is ok, choose priority.
                return FloorTool.setDirectionUp();
            }
        }
        // up 组
        else if (FloorTool.isUp(moveDirection)) {
            if (!upTaskState) {
                return FloorTool.setDirectionStill();
            }
        }
        // down 组
        else if (FloorTool.isDown(moveDirection)) {
            if (!downTaskState) {
                return FloorTool.setDirectionStill();
            }
        }
        return moveDirection;
    }

    // according to NoMoreRequest and Unfinished Task Amount, to give the
    // elevator a vacation(wait) or give out terminate signal.
    protected synchronized boolean terminateQuery()
        throws InterruptedException {
        while (runningTask == 0 && taskCache.isEmpty()) {
            if (noMoreTask) {
                return true;
            }
            DebugPrint.threadStatePrint(elevator.getType(), elevator.getname(), "Rest");
            wait();
            DebugPrint.threadStatePrint(elevator.getType(), elevator.getname(), "Recover");
        }
        return false;
    }

    // ---------- PassengerList Maintain Function ----------
    // add new request to taskCache (not running yet.)
    protected synchronized void addNewTask(PersonRequest personRequest) {
        taskCache.add(personRequest);
        notifyAll();
    }

    // validate requests in cache to run state.
    // 此处的策略可改变，例如可根据电梯当前运行任务数来新增任务
    protected synchronized void taskValidate() {
        Iterator<PersonRequest> it = taskCache.iterator();
        // -------- strategy can vary --------
        while (it.hasNext()) {
            runTask(it.next());
            it.remove();
        }
    }

    // add to pick list and target list
    protected synchronized void runTask(PersonRequest personRequest) {
        DebugPrint.errPrint(elevator.getType(), elevator.getname(),
            String.format("A new Task '%s' Have Received",
                personRequest.toString()));
        int id = personRequest.getPersonId();
        int from = FloorTool.floor2Index(personRequest.getFromFloor());
        int to = FloorTool.floor2Index(personRequest.getToFloor());
        runningTask++;
        orderList.addLast(id);
        // judge direction and put into correct list
        if (from - to < 0) {
            upPickList.get(from).add(id);
        } else {
            downPickList.get(from).add(id);
        }
        toMap.put(id, to);
        fromMap.put(id, from);
        notifyAll();
    }


    // in present floor, passList will help elevator pick and put correct pass.
    // 当讨论沿路接乘客时，只有同方向乘客（稳定时双向均可）才可以, 送乘客不影响。
    protected synchronized void passengerMove(int floorIndex, int moveDirection)
        throws InterruptedException {
        if (taskNow(floorIndex, moveDirection)) {

            // put
            Iterator<Integer> putIt =
                putList.get(floorIndex).iterator();
            putPassenger(putIt);

            // pick
            Iterator<Integer> upPickIt =
                upPickList.get(floorIndex).iterator();
            Iterator<Integer> downPickIt =
                downPickList.get(floorIndex).iterator();
            if (FloorTool.isUp(moveDirection)) {
                pickPassenger(upPickIt);
                pickPassenger(downPickIt);
            } else {
                pickPassenger(downPickIt);
                pickPassenger(upPickIt);
            }
        }
    }

    // put passenger action function
    private synchronized void putPassenger(Iterator<Integer> it)
        throws InterruptedException {
        while(it.hasNext()){
            int id = it.next();
            elevator.kickOut(id);
            it.remove();
            havePutPassenger(id);
        }
    }

    // pick passenger action function
    private synchronized void pickPassenger(Iterator<Integer> it)
        throws InterruptedException {
        while (it.hasNext() && !elevator.isFull()) {
            int id = it.next();
            elevator.pullIn(id);
            it.remove();
            havePickedPassenger(id);
        }
    }

    // add new sub mission to putList <--- elevator.runMethod
    protected synchronized void havePickedPassenger(int id) {
        fromMap.remove(id);
        int toFloor = toMap.get(id);
        putList.get(toFloor).add(id);
    }

    // runningTaskCount-- == Task Finished
    protected synchronized void havePutPassenger(int id) {
        toMap.remove(id);
        runningTask--;
        orderList.remove(new Integer(id));
    }


    // ---------- Terminate State Function ----------
    protected synchronized void setNoMoreTask() {
        noMoreTask = true;
        DebugPrint.errPrint(elevator.getType(), elevator.getname(),
            "Have Received Input Terminate Signal.");
        notifyAll();
    }

    // ---------- State Query Function ----------
    public synchronized int getRunningTask() {
        return runningTask;
    }
}

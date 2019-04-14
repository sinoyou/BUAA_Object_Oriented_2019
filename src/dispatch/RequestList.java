package dispatch;

import com.oocourse.elevator3.PersonRequest;
import tool.DebugPrint;

import java.util.HashMap;
import java.util.LinkedList;

public class RequestList {
    private static LinkedList<PersonRequest> list;
    private static RequestList uniInstance = new RequestList();
    private boolean noMoreRequest;
    private HashMap<Integer, Integer> destinationMap;

    private RequestList() {
        list = new LinkedList<>();
        destinationMap = new HashMap<>();
        noMoreRequest = false;
    }

    // single item mode
    public static RequestList getInstance() {
        return uniInstance;
    }

    public synchronized void noMoreRequest() {
        noMoreRequest = true;
        DebugPrint.threadStatePrint("Client", "Input Terminated");
        notifyAll();
    }

    // executed by input thread and elevator thread.
    public synchronized void putRequest(PersonRequest i) {
        list.addLast(i);
        if (!destinationMap.containsKey(i.getPersonId())) {
            destinationMap.put(i.getPersonId(), i.getToFloor());
        }
        notifyAll();
    }

    // executed by dispatcher thread
    public synchronized PersonRequest getRequest() throws InterruptedException {
        while (list.isEmpty()) {
            if(noMoreRequest && destinationMap.isEmpty()){
                return null;
            }
            wait();
        }
        /*
        if (list.isEmpty()) {
            DebugPrint.errPrint("Dispatcher", "Get None");
            return null;
        */
        PersonRequest i = list.removeFirst();
        return i;
    }

    // executed by elevator thread
    public synchronized void taskFeedback(int id, int floor) {
        int des = destinationMap.get(id);
        if (des == floor) {
            DebugPrint.errPrint("Dispatcher",
                String.format("ID %d Task Finished",id));
            destinationMap.remove(id);
        } else {
            PersonRequest subTask = new PersonRequest(floor, des, id);
            putRequest(subTask);
        }
        notifyAll();
    }
}

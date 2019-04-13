package dispatch;

import com.oocourse.elevator3.PersonRequest;
import tool.DebugPrint;

import java.util.LinkedList;

public class RequestList {
    private static LinkedList<PersonRequest> list;
    private static RequestList uniInstance = new RequestList();
    private boolean noMoreRequest;

    private RequestList() {
        list = new LinkedList<>();
        noMoreRequest = false;
    }

    // single item mode
    public static RequestList getInstance() {
        return uniInstance;
    }

    public synchronized void noMoreRequest() {
        noMoreRequest = true;
        DebugPrint.threadStatePrint("Client","Input Terminated");
        notifyAll();
    }

    public synchronized void putRequest(PersonRequest i) {
        list.addLast(i);
        notifyAll();
    }

    public synchronized PersonRequest getRequest() throws InterruptedException {
        while (list.isEmpty() && !noMoreRequest) {
            DebugPrint.threadStatePrint("Dispatcher","Rest");
            wait();
            DebugPrint.threadStatePrint("Dispatcher","Recover");
        }
        if (list.isEmpty()) {
            DebugPrint.errPrint("Dispatcher","Get None");
            return null;
        } else {
            PersonRequest i = list.removeFirst();
            return i;
        }
    }
}

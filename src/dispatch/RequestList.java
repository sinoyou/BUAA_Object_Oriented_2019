package dispatch;

import com.oocourse.elevator2.PersonRequest;

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
        System.err.println(String.format("<Client>:input has terminated."));
        notifyAll();
    }

    public synchronized void putRequest(PersonRequest i) {
        list.addLast(i);
        notifyAll();
    }

    public synchronized PersonRequest getRequest() throws InterruptedException {
        while (list.isEmpty() && !noMoreRequest) {
            System.err.println(String.format("<Dispatcher>:begin to rest."));
            wait();
            System.err.println("<Dispatcher>:recover.");
        }
        if (list.isEmpty()) {
            System.err.println(String.format("<Dispatcher>:get none."));
            return null;
        } else {
            PersonRequest i = list.removeFirst();
            return i;
        }
    }
}

import com.oocourse.elevator1.PersonRequest;

import java.util.LinkedList;

public class Commander {
    private static LinkedList<PersonRequest> list;
    private static Commander uniInstance = new Commander();
    private boolean noMoreRequest;

    private Commander() {
        list = new LinkedList<>();
        noMoreRequest = false;
    }

    // single item mode
    public static Commander getInstance() {
        return uniInstance;
    }

    public synchronized void noMoreRequest() {
        noMoreRequest = true;
        System.err.println(String.format("<Writer>:input has terminated."));
        notifyAll();
    }

    public synchronized void putRequest(PersonRequest i) {
        list.addLast(i);
        System.err.println(String.format("<Writer>:request '%s' is put.",
            i.toString()));
        notifyAll();
    }

    public synchronized PersonRequest getRequest() throws InterruptedException {
        while (list.isEmpty() && !noMoreRequest) {
            System.err.println(String.format("<Reader>:begin to rest."));
            wait();
        }
        if (list.isEmpty()) {
            System.err.println(String.format("<Reader>:Recover but get none."));
            return null;
        } else {
            PersonRequest i = list.removeFirst();
            System.err.println(String.format("<Reader>:Recover and get" +
                " request:'%s'.", i.toString()));
            return i;
        }
    }
}

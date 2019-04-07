import com.oocourse.TimableOutput;
import com.oocourse.elevator2.ElevatorInput;
import com.oocourse.elevator2.PersonRequest;
import dispatch.DispatcherThread;
import dispatch.RequestList;
import elevator.ElevatorThread;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // initial output
        TimableOutput.initStartTimestamp();
        // initial thread and class
        RequestList requestList = RequestList.getInstance();
        DispatcherThread dispatcher = new DispatcherThread(requestList);
        ElevatorThread elevator1 = new ElevatorThread(dispatcher);
        // start thread
        dispatcher.start();
        elevator1.start();

        // Main thread is used for read
        ElevatorInput input = new ElevatorInput(System.in);
        PersonRequest request;
        while (true) {
            request = input.nextPersonRequest();
            // tell dispatcher no more request.
            if (request == null) {
                input.close();
                requestList.noMoreRequest();
                break;
            } else {
                requestList.putRequest(request);
            }
        }

        // wait until elevator and dispatcher end
        try {
            dispatcher.join();
            elevator1.join();
            System.err.println("@Main Shutdown With Normal State.");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println("@Main Shutdown With Error State.");
        }
    }
}

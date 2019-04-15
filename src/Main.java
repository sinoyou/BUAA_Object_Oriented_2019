import com.oocourse.TimableOutput;
import com.oocourse.elevator3.ElevatorInput;
import com.oocourse.elevator3.PersonRequest;
import dispatch.DispatcherThread;
import dispatch.RequestList;
import elevator.ElevatorThread;
import factory.ElevatorFactory;
import tool.DebugPrint;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // initial output
        TimableOutput.initStartTimestamp();
        // initial thread and class
        RequestList requestList = RequestList.getInstance();
        DispatcherThread dispatcher = new DispatcherThread(requestList);
        ElevatorFactory factory = new ElevatorFactory(dispatcher); // factory
        // start thread
        dispatcher.start();
        ElevatorThread elevator1 = factory.create("Archer");
        elevator1.start();
        ElevatorThread elevator2 = factory.create("Berserker");
        elevator2.start();
        ElevatorThread elevator3 = factory.create("Caster");
        elevator3.start();

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
            elevator2.join();
            elevator3.join();
            DebugPrint.threadStatePrint("Main", "Normal ShutDown");
        } catch (InterruptedException e) {
            e.printStackTrace();
            DebugPrint.threadStatePrint("Main", "Error ShutDown");
        }
    }
}

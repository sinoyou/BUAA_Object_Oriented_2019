import com.oocourse.TimableOutput;
import com.oocourse.elevator1.ElevatorInput;
import com.oocourse.elevator1.PersonRequest;

public class Main {
    public static void main(String[] agrs) throws Exception{
        ElevatorInput input = new ElevatorInput(System.in);
        Commander command = Commander.getInstance();
        TimableOutput.initStartTimestamp();

        // define elevator instance and create the thread
        Thread elevator = new Elevator(command);
        elevator.start();

        PersonRequest request;
        // get request
        while (true) {
            request = input.nextPersonRequest();
            if(request==null){
                input.close();
                break;
            }else {
                command.putRequest(request);
            }
        }

        // tell commander that the input is finished.
        command.noMoreRequest();
        try {
            elevator.join();
        } catch (InterruptedException e) {
            System.err.println("Main Thread Ended With Blocked State.");
            // e.printStackTrace();
        }
    }
}

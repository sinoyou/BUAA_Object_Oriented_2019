package factory;

import dispatch.DispatcherThread;
import elevator.ElevatorThread;

public class ElevatorFactory {
    private DispatcherThread dispatcherThread;

    public ElevatorFactory(DispatcherThread dispatcherThread) {
        this.dispatcherThread = dispatcherThread;
    }

    public ElevatorThread create(String tag) {
        if (tag.equals("Archer")) {
            int[] legalList = {-3, -2, -1, 1, 15, 16, 17, 18, 19, 20};
            return new ElevatorThread(dispatcherThread,
                "A",
                400,
                200,
                legalList,
                6);
        } else if (tag.equals("Berserker")) {
            int[] legalList = {-2, -1, 1, 2, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
            return new ElevatorThread(dispatcherThread,
                "B",
                500,
                200,
                legalList,
                8);
        } else if (tag.equals("Caster")) {
            int[] legalList = {1, 3, 5, 7, 9, 11, 13, 15};
            return new ElevatorThread(dispatcherThread,
                "C",
                600,
                200,
                legalList,
                7);
        } else {
            return null;
        }
    }
}

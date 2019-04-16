package dispatch;

import com.oocourse.elevator3.PersonRequest;
import elevator.ElevatorThread;
import tool.FloorTool;

import java.util.ArrayList;

public class ArtificialDecision {
    private PersonRequest tarTask;
    private ElevatorThread taskElevator;
    private PersonRequest oriRequest;
    private ArrayList<ElevatorThread> registers;
    private ElevatorThread elevatorA;
    private ElevatorThread elevatorB;
    private ElevatorThread elevatorC;

    public ArtificialDecision(PersonRequest personRequest,
                              ArrayList<ElevatorThread> registers) {
        oriRequest = personRequest;
        this.registers = registers;
        elevatorA = null;
        elevatorB = null;
        elevatorC = null;
        // confirm each elevator's identity.
        for (ElevatorThread register : this.registers) {
            if (register.getname().equals("A")) {
                elevatorA = register;
            } else if (register.getname().equals("B")) {
                elevatorB = register;
            } else if (register.getname().equals("C")) {
                elevatorC = register;
            }
        }
        boolean isDirectTask = isDirectTask();
        // if direct task, then split as strategy.
        if (isDirectTask) {
            taskElevator = getDirectTaskElevator(oriRequest.getFromFloor(),
                oriRequest.getToFloor());
            tarTask = oriRequest;
        } else {
            setSubTask();
        }
    }


    // ---------- Artificial Decision Function ----------
    private void setSubTask() {
        // 考虑独占到达楼层的情况
        int from = oriRequest.getFromFloor();
        int to = oriRequest.getToFloor();
        int subTaskToFloor = 0;             // 子任务目的地楼层
        boolean toLegalA = elevatorA.isFloorLegal(to);
        boolean toLegalB = elevatorB.isFloorLegal(to);
        boolean toLegalC = elevatorC.isFloorLegal(to);
        // A独占目的地到达 -3 / 15-20
        if (toLegalA && !toLegalB && !toLegalC) {
            if (to == -3) {
                subTaskToFloor = 1;
            } else {
                subTaskToFloor = 15;
            }
        }
        // B独占目的地到达 2 4 6 8 10 12 14。from楼层肯定在B片区之外
        else if (!toLegalA && toLegalB && !toLegalC) {
            if (Math.abs(from - 1) <= Math.abs(from - 15)) {
                subTaskToFloor = 1;
            } else {
                subTaskToFloor = 15;
            }
        }
        // C独占目的地到达 3 + 其他情况
        else {
            if (Math.abs(from - 1) <= Math.abs(from - 15)) {
                subTaskToFloor = 1;
            } else {
                subTaskToFloor = 15;
            }
        }
        tarTask = new PersonRequest(oriRequest.getFromFloor(),
            subTaskToFloor, oriRequest.getPersonId());
        taskElevator = getDirectTaskElevator(oriRequest.getFromFloor(),
            subTaskToFloor);
    }

    private void setSubTask2() {
        // 考虑独占到达楼层的情况
        int from = oriRequest.getFromFloor();
        int to = oriRequest.getToFloor();
        int subTaskToFloor = 0;             // 子任务目的地楼层
        // B独占目的地到达 2 4 6 8 10 12 14。from楼层肯定在B片区之外
        if (Math.abs(from - 1) <= Math.abs(from - 15)) {
            subTaskToFloor = 1;
        } else {
            subTaskToFloor = 15;
        }
        tarTask = new PersonRequest(oriRequest.getFromFloor(),
            subTaskToFloor, oriRequest.getPersonId());
        taskElevator = getDirectTaskElevator(oriRequest.getFromFloor(),
            subTaskToFloor);
    }

    private boolean isDirectTask() {
        int from = oriRequest.getFromFloor();
        int to = oriRequest.getToFloor();
        // check if can transform directly
        boolean directTaskA = elevatorA.isDirectTask(from, to);
        boolean directTaskB = elevatorB.isDirectTask(from, to);
        boolean directTaskC = elevatorC.isDirectTask(from, to);
        return directTaskA | directTaskB | directTaskC;
    }

    private ElevatorThread getDirectTaskElevator(int from, int to) {
        // check if can transform directly
        boolean directTaskA = elevatorA.isDirectTask(from, to);
        boolean directTaskB = elevatorB.isDirectTask(from, to);
        boolean directTaskC = elevatorC.isDirectTask(from, to);
        // A电梯： 空间未满 + 处在5楼以下 + 运动方向顺势
        // B电梯： 空间未满 + 运动方向顺势 + 楼层间隔<=2
        if (directTaskA && !elevatorA.isFull() && elevatorA.getFloor() <= 5 &&
            FloorTool.isOnTheWay(from, to,
                elevatorA.getMoveDirection(), elevatorA.getFloor())) {
            return elevatorA;
        }
        if (directTaskB && !elevatorB.isFull() &&
            Math.abs(from - elevatorB.getFloor()) <= 2 && FloorTool.isOnTheWay(
            from, to, elevatorB.getMoveDirection(), elevatorB.getFloor())) {
            return elevatorB;
        }
        // a b c = 001, 011, 101, 111
        if (directTaskC) {
            /* if (directTaskB && !elevatorB.isFull() &&
                (elevatorB.targetFloorEstimate(from) * 5
                    <= elevatorC.targetFloorEstimate(from) * 6)) {
                return elevatorB;
            }*/
            return elevatorC;
        }
        // a b c = 010
        else if (!directTaskA && directTaskB && !directTaskC) {
            return elevatorB;
        }
        // a b c = 110
        else if (directTaskA && directTaskB && !directTaskC) {
            /*if (elevatorA.targetFloorEstimate(from) * 4 <=
                elevatorB.targetFloorEstimate(from) * 5) {
                return elevatorA;
            }*/
            return elevatorB;
        }
        // a b c = 100
        else if (directTaskA && !directTaskB && !directTaskC) {
            return elevatorA;
        }
        try {
            throw new Exception("Impossible Direct Transform");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ElevatorThread getDirectTaskElevator2(int from, int to) {
        // check if can transform directly
        boolean directTaskA = elevatorA.isDirectTask(from, to);
        boolean directTaskB = elevatorB.isDirectTask(from, to);
        boolean directTaskC = elevatorC.isDirectTask(from, to);
        ElevatorThread decision = null;
        int taskAmount = 100;
        if (directTaskA && taskAmount > elevatorA.getTotalTask()) {
            taskAmount = elevatorA.getTotalTask();
            decision = elevatorA;
        }
        if (directTaskB && taskAmount > elevatorB.getTotalTask()) {
            taskAmount = elevatorB.getTotalTask();
            decision = elevatorB;
        }
        if (directTaskC && taskAmount > elevatorC.getTotalTask()) {
            taskAmount = elevatorC.getTotalTask();
            decision = elevatorC;
        }
        return decision;
    }

    // ---------- Artificial Decision Feedback Function ----------
    public PersonRequest getTarTask() {
        return tarTask;
    }

    public ElevatorThread getTaskElevator() {
        return taskElevator;
    }
}

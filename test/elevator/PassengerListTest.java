package elevator;

import com.oocourse.elevator2.PersonRequest;
import constant.FloorTool;

import static org.junit.Assert.*;

public class PassengerListTest {
    private PassengerList passList = new PassengerList(null);

    @org.junit.Test
    public void isContinue() {
        passList.createNewTask(1,1,15);
        passList.createNewTask(2,5,15);
        passList.createNewTask(3,5,13);
        passList.createNewTask(4,6,3);
        System.out.println(passList.isContinue(1, FloorTool.setDirectionDown()));
        System.out.println(passList.isContinue(6, FloorTool.setDirectionUp()));
    }

    @org.junit.Test
    public void directionCheck() {
        assertEquals(FloorTool.setDirectionUp(),passList.directionCheck(FloorTool.setDirectionDown(),0));
        assertEquals(FloorTool.setDirectionDown(),passList.directionCheck(FloorTool.setDirectionUp(),18));
        assertFalse(passList.isContinue(0, FloorTool.setDirectionDown()));
        assertFalse(passList.isContinue(18, FloorTool.setDirectionUp()));
        passList.createNewTask(1,4,15);
        passList.createNewTask(2,5,15);
        passList.createNewTask(3,5,13);
        passList.createNewTask(4,6,3);
        assertTrue(passList.isContinue(15, FloorTool.setDirectionDown()));
        assertFalse(passList.isContinue(15, FloorTool.setDirectionUp()));
        assertTrue(passList.isContinue(3, FloorTool.setDirectionUp()));
        assertTrue(passList.isContinue(4, FloorTool.setDirectionUp()));
        assertFalse(passList.isContinue(3, FloorTool.setDirectionDown()));
        // before pick up
        assertFalse(passList.isContinue(4,FloorTool.setDirectionDown()));
        passList.havePickedPassenger(4);
        assertTrue(passList.isContinue(4,FloorTool.setDirectionDown()));
        passList.havePutPassenger(4);
        assertFalse(passList.isContinue(4,FloorTool.setDirectionDown()));
    }

    @org.junit.Test
    public void createNewTask() {
        passList.createNewTask(1,1,15);
        passList.createNewTask(2,5,15);
        passList.createNewTask(3,5,13);
        passList.createNewTask(4,6,3);
    }

    @org.junit.Test
    public void passengerMove() {
    }
}
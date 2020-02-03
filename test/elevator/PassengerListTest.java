package elevator;

import constant.FloorTool;

import static org.junit.Assert.*;

public class PassengerListTest {
    private PassengerList passList = new PassengerList(null);

    @org.junit.Test
    public void isContinue() {
        assertFalse(passList.hasTask(0, FloorTool.setDirectionDown()));
        assertFalse(passList.hasTask(18, FloorTool.setDirectionUp()));
        passList.createNewTask(1,4,15);
        passList.createNewTask(2,5,15);
        passList.createNewTask(3,5,13);
        passList.createNewTask(4,6,3);
        assertTrue(passList.hasTask(15, FloorTool.setDirectionDown()));
        assertFalse(passList.hasTask(15, FloorTool.setDirectionUp()));
        assertTrue(passList.hasTask(3, FloorTool.setDirectionUp()));
        assertTrue(passList.hasTask(4, FloorTool.setDirectionUp()));
        assertFalse(passList.hasTask(3, FloorTool.setDirectionDown()));
        // before pick up
        assertFalse(passList.hasTask(4,FloorTool.setDirectionDown()));
        passList.havePickedPassenger(4);
        assertTrue(passList.hasTask(4,FloorTool.setDirectionDown()));
        passList.havePutPassenger(4);
    }

    @org.junit.Test
    public void directionCheck() {
        int still = FloorTool.setDirectionStill();
        int down = FloorTool.setDirectionDown();
        int up = FloorTool.setDirectionUp();
        // ---> Still check
        assertEquals(still,passList.directionCheck(still,0));
        assertEquals(still,passList.directionCheck(still,7));
        assertEquals(still,passList.directionCheck(still,18));
        assertEquals(still,passList.directionCheck(down,0));
        assertEquals(still,passList.directionCheck(down,10));
        assertEquals(still,passList.directionCheck(up,18));
        assertEquals(still,passList.directionCheck(up,3));
        // special ---> Still Check
        passList.createNewTask(2,5,15);
        assertEquals(still,passList.directionCheck(still,5));
        assertEquals(still,passList.directionCheck(up,5));
        assertEquals(still,passList.directionCheck(down,5));

        // ---> Up check
        passList.createNewTask(3,16,13);
        assertEquals(up,passList.directionCheck(still,3));
        // when available in both side, choose up
        assertEquals(up,passList.directionCheck(still,7));
        assertEquals(up,passList.directionCheck(up,7));
        assertEquals(up,passList.directionCheck(up,3));
        assertEquals(up,passList.directionCheck(down,4));

        // ---> Down Check
        assertEquals(down,passList.directionCheck(still,18));
        assertEquals(down,passList.directionCheck(down,10));
        assertEquals(down,passList.directionCheck(up,18));
        assertEquals(down,passList.directionCheck(down,17));

        // Edge check
        passList.createNewTask(4,18,3);
        passList.createNewTask(9,0,13);
        assertEquals(down,passList.directionCheck(still,18));
        assertEquals(down,passList.directionCheck(up,18));
        assertEquals(down,passList.directionCheck(down,18));
        assertEquals(up,passList.directionCheck(still,0));
        assertEquals(up,passList.directionCheck(up,0));
        assertEquals(up,passList.directionCheck(down,0));
    }

    @org.junit.Test
    public void createNewTask() {
        passList.createNewTask(1,1,15);
        passList.createNewTask(2,5,15);
        passList.createNewTask(3,5,13);
        passList.createNewTask(4,6,3);
    }

}
package constant;

public class FloorTool {
    final private static int[] floor =
        {-3, -2, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    final private static int floorAmount = 19;
    final private static int down = 1;
    final private static int up = 2;
    final private static int still = 0;
    private static String downStr = "DOWN";
    private static String upStr = "UP";
    private static String stillStr = "STILL";

    public static int getFloorAmount() {
        return floorAmount;
    }

    public static int getFloor(int index) {
        return floor[index];
    }

    public static int floor2Index(int f) {
        for (int i = 0; i < floorAmount; i++) {
            if (floor[i] == f) {
                return i;
            }
        }
        return -1;
    }

    public static int index2Floor(int index) {
        return floor[index];
    }

    public static int[] getFloor() {
        return floor;
    }

    // ---------- direction set and judge ----------
    public static int setDirectionDown() {
        return down;
    }

    public static int setDirectionUp() {
        return up;
    }

    public static int setDirectionStill() {
        return still;
    }

    public static boolean isDown(int dir) {
        return dir == down;
    }

    public static boolean isUp(int dir) {
        return dir == up;
    }

    public static boolean isStill(int dir) {
        return dir == still;
    }

    public static String getDirectionName(int dir){
        switch (dir){
            case down:
                return downStr;
            case up:
                return upStr;
            case still:
                return stillStr;
            default:
                return "Unknown Direction";
        }
    }
}

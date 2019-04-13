package tool;

public class FloorTool {
    private static final int[] floor =
        {-3, -2, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
            18, 19, 20};
    private static final int floorAmount = 23;
    private static final int down = 1;
    private static final int up = 2;
    private static final int still = 0;
    private static String downStr = "DOWN";
    private static String upStr = "UP";
    private static String stillStr = "STILL";

    public static int getFloorAmount() {
        return floorAmount;
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

    public static String getDirectionName(int dir) {
        switch (dir) {
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

    // debug: legal list contains floor, must transform to index.
    public static boolean legalFloor(int floorIndex, int[] legalList) {
        for (int i = 0; i < legalList.length; i++) {
            if (floor2Index(legalList[i]) == floorIndex) {
                return true;
            }
        }
        return false;
    }
}

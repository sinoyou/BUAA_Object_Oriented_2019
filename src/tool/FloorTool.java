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
    public static boolean isLegalFloorIndex(int floorIndex, int[] legalList) {
        for (int i = 0; i < legalList.length; i++) {
            if (floor2Index(legalList[i]) == floorIndex) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDirectTransport(int from, int to, int[] legalList) {
        return (isLegalFloorIndex(floor2Index(from), legalList)
            && isLegalFloorIndex(floor2Index(to), legalList));
    }

    public static int directionMove(int direction, int floorIndex) {
        if (isUp(direction)) {
            return floorIndex + 1;
        } else if (isDown(direction)) {
            return floorIndex - 1;
        } else {
            return floorIndex;
        }
    }

    public static int getOppDirection(int direction) {
        if (isUp(direction)) {
            return setDirectionUp();
        } else if (isDown(direction)) {
            return setDirectionUp();
        } else {
            return direction;
        }
    }

    public static int getDirection(int from, int to) {
        if (from > to) {
            return setDirectionDown();
        } else if (from < to) {
            return setDirectionUp();
        } else {
            return setDirectionStill();
        }
    }

    public static boolean isOnTheWay(int from, int to, int direction) {
        if (isDown(direction) && from - to > 0) {
            return true;
        } else if (isUp(direction) && from - to < 0) {
            return true;
        }
        return false;
    }
}

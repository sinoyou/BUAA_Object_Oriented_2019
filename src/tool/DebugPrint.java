package tool;

public class DebugPrint {
    private static boolean debugMode = true;

    public static void errPrint(String type, String name, String content) {
        if (debugMode) {
            System.err.println(String.format("<%s %s>:%s", type, name, content));
        }
    }

    public static void errPrint(String type, String content) {
        if (debugMode) {
            System.err.println(String.format("<%s>:%s", type, content));
        }
    }

    public static void threadStatePrint(String type, String name, String state) {
        if (debugMode) {
            System.err.println(String.format("@<%s %s>:State -> %s"
                , type, name, state));
        }
    }

    public static void threadStatePrint(String type, String state) {
        if (debugMode) {
            System.err.println(String.format("@<%s>:State -> %s"
                , type, state));
        }
    }
}

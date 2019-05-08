package graph.tool;

public class VersionMark {
    private int versionMark;

    public VersionMark() {
        versionMark = 0;
    }

    public void versionUpdate() {
        versionMark++;
    }

    public int getVersion() {
        return versionMark;
    }

    public boolean isLatest(int curVersion) {
        return curVersion == versionMark;
    }
}

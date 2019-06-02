package tool;

import java.util.HashMap;

public class VersionCache {
    private HashMap<String, Integer> versionMap;
    private HashMap<String, Object> cache;
    private Integer version;

    public VersionCache() {
        version = 0;
        versionMap = new HashMap<>();
        cache = new HashMap<>();
    }

    /* ------- Version Check -------- */
    public void updateVersion() {
        version++;
    }

    /* ------- Cache Check -------- */
    public void updateCache(String tag, Object o) {
        versionMap.put(tag, version);
        cache.put(tag, o);
    }

    public Object getCache(String tag) {
        if (isLatest(tag)) {
            return cache.getOrDefault(tag, null);
        } else {
            return null;
        }
    }

    private boolean isLatest(String tag) {
        if (versionMap.containsKey(tag)) {
            int cacheVersion = versionMap.get(tag);
            if (cacheVersion > version) {
                System.err.println("[Version]: Unexpected cache version.");
            }
            return (cacheVersion == version);
        } else {
            return false;
        }
    }
}

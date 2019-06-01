package tool;

import java.util.HashMap;

/**
 * This class used for generate json object for unit test.
 */
public class JsonFactory {

    public static Object produceOperation(
        String id,
        String name,
        String parent,
        String visibility
    ) {
        HashMap<String, String> map = new HashMap<>();
        writeAbstract(map, id, name, parent);
        writeVisibility(map, visibility);
        map.put("_type", "UMLOperation");
        return map;
    }

    public static Object produceParameter(
        String id,
        String name,
        String parent,
        String direction,
        String type
    ) {
        HashMap<String, String> map = new HashMap<>();
        writeAbstract(map, id, name, parent);
        map.put("direction", direction);
        map.put("type", type);
        return map;
    }


    // Inner help function
    private static void writeAbstract(
        HashMap<String, String> map,
        String id,
        String name,
        String parent
    ) {
        map.put("_id", id);
        if (name != null) {
            map.put("name", name);
        }
        map.put("_parent", parent);
    }

    private static void writeVisibility(HashMap<String, String> map,
                                        String visibility) {
        map.put("visibility", visibility);
    }

}

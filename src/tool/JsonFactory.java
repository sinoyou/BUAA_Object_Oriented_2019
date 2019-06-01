package tool;

import java.util.HashMap;

/**
 * This class used for generate json object for unit test.
 */
public class JsonFactory {

    public static Object produceClass(
        String id,
        String name,
        String parent,
        String visibility
    ) {
        HashMap<String, Object> map = new HashMap<>();
        writeAbstract(map, id, name, parent);
        writeVisibility(map, visibility);
        map.put("_type", "UMLClass");
        return map;
    }

    public static Object produceInterface(
        String id,
        String name,
        String parent,
        String visibility
    ) {
        HashMap<String, Object> map = new HashMap<>();
        writeAbstract(map, id, name, parent);
        writeVisibility(map, visibility);
        map.put("_type", "UMLInterface");
        return map;
    }

    public static Object produceOperation(
        String id,
        String name,
        String parent,
        String visibility
    ) {
        HashMap<String, Object> map = new HashMap<>();
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
        HashMap<String, Object> map = new HashMap<>();
        writeAbstract(map, id, name, parent);
        map.put("_type", "UMLParameter");
        map.put("direction", direction);
        map.put("type", type);
        return map;
    }

    public static Object produceAssociation(
        String id,
        String name,
        String parent,
        String end1,
        String end2
    ) {
        HashMap<String, Object> map = new HashMap<>();
        writeAbstract(map, id, name, parent);
        map.put("_type", "UMLAssociation");
        map.putAll(new HashMap<String, Object>() {
            {
                this.put("end1", end1);
                this.put("end2", end2);
            }
        });
        return map;
    }

    public static Object produceAssociationEnd(
        String id,
        String name,
        String parent,
        String ref
    ) {
        HashMap<String, Object> map = new HashMap<>();
        writeAbstract(map, id, name, parent);
        map.put("_type", "UMLAssociationEnd");
        map.put("reference", ref);
        return map;
    }

    public static Object produceGeneralization(
        String id,
        String name,
        String parent,
        String source,
        String target
    ) {
        HashMap<String, Object> map = new HashMap<>();
        writeAbstract(map, id, name, parent);
        map.put("_type","UMLGeneralization");
        map.put("source", source);
        map.put("target", target);
        return map;
    }

    public static Object produceRealization(
        String id,
        String name,
        String parent,
        String source,
        String target
    ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("_type","UMLRealization");
        writeAbstract(map, id, name, parent);
        map.put("source", source);
        map.put("target", target);
        return map;
    }

    // Inner help function
    private static void writeAbstract(
        HashMap<String, Object> map,
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

    private static void writeVisibility(HashMap<String, Object> map,
                                        String visibility) {
        if (visibility != null) {
            map.put("visibility", visibility);
        }
    }

}

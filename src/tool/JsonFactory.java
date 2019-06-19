package tool;

import java.util.HashMap;

/**
 * This class used for generate json object for unit test.
 */
public class JsonFactory {

    /* >>>>>>>> MODEL <<<<<<<< */
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
        map.put("_type", "UMLGeneralization");
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
        map.put("_type", "UMLRealization");
        writeAbstract(map, id, name, parent);
        map.put("source", source);
        map.put("target", target);
        return map;
    }

    public static Object produceAttribute(
        String id,
        String name,
        String parent,
        String visibility,
        String type
    ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("_type", "UMLAttribute");
        writeAbstract(map, id, name, parent);
        writeVisibility(map, visibility);
        map.put("type", type);
        return map;
    }

    /* >>>>>>>> State Machine <<<<<<<< */
    public static Object produceEvent(
        String id,
        String name,
        String parent,
        String visibility,
        String value,
        String expression
    ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("_type", "UMLEvent");
        writeAbstract(map, id, name, parent);
        writeVisibility(map, visibility);
        map.put("value", value);
        map.put("expression", expression);
        return map;
    }

    public static Object produceFinalState(
        String id,
        String name,
        String parent,
        String visibility
    ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("_type", "UMLFinalState");
        writeAbstract(map, id, name, parent);
        writeVisibility(map, visibility);
        return map;
    }

    public static Object producePseudostate(
        String id,
        String name,
        String parent,
        String visibility
    ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("_type", "UMLPseudoState");
        writeAbstract(map, id, name, parent);
        writeVisibility(map, visibility);
        return map;
    }

    public static Object produceRegion(
        String id,
        String name,
        String parent,
        String visibility
    ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("_type", "UMLRegion");
        writeAbstract(map, id, name, parent);
        writeVisibility(map, visibility);
        return map;
    }

    public static Object produceState(
        String id,
        String name,
        String parent,
        String visibility
    ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("_type", "UMLState");
        writeAbstract(map, id, name, parent);
        writeVisibility(map, visibility);
        return map;
    }

    public static Object produceStateMachine(
        String id,
        String name,
        String parent
    ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("_type", "UMLStateMachine");
        writeAbstract(map, id, name, parent);
        return map;
    }

    public static Object produceTransition(
        String id,
        String name,
        String parent,
        String visibility,
        String source,
        String target,
        String guard
    ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("_type", "UMLTransition");
        writeAbstract(map, id, name, parent);
        writeVisibility(map, visibility);
        map.put("source", source);
        map.put("target", target);
        map.put("guard", guard);
        return map;
    }

    /* >>>>>>>> Collaboration <<<<<<<<*/

    public static Object produceEndPoint(
        String id,
        String name,
        String parent,
        String visibility
    ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("_type", "UMLEndPoint");
        writeAbstract(map, id, name, parent);
        writeVisibility(map, visibility);
        return map;
    }

    public static Object produceInteraction(
        String id,
        String name,
        String parent,
        String visibility
    ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("_type", "UMLInteraction");
        writeAbstract(map, id, name, parent);
        writeVisibility(map, visibility);
        return map;
    }

    public static Object produceLifeLine(
        String id,
        String name,
        String parent,
        String visibility,
        String represent,
        String isMultiInstance
    ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("_type", "UMLLifeline");
        writeAbstract(map, id, name, parent);
        writeVisibility(map, visibility);
        if (represent != null) {
            map.put("represent", represent);
        } else {
            map.put("represent", "0");
        }
        if (isMultiInstance != null) {
            map.put("isMultiInstance", isMultiInstance);

        }
        return map;
    }

    public static Object produceMessage(
        String id,
        String name,
        String parent,
        String visibility,
        String source,
        String target,
        String messageSort
    ) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("_type", "UMLEndPoint");
        writeAbstract(map, id, name, parent);
        writeVisibility(map, visibility);
        map.put("source", source);
        map.put("target", target);
        if (messageSort != null) {
            map.put("messageSort", messageSort);
        }
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

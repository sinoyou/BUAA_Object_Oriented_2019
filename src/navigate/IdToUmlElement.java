package navigate;

import com.oocourse.uml1.models.elements.UmlElement;

import java.util.HashMap;

public class IdToUmlElement {
    private HashMap<String, UmlElement> idMap;
    private static IdToUmlElement idToUmlElement = null;

    private IdToUmlElement() {
        idMap = new HashMap<>();
    }

    public static IdToUmlElement getInstance() {
        if (idToUmlElement == null) {
            idToUmlElement = new IdToUmlElement();
        }
        return idToUmlElement;
    }

    public void addUmlElement(UmlElement umlElement) {
        idMap.put(umlElement.getId(), umlElement);
    }

    public UmlElement getUmlElementById(String id) {
        return idMap.get(id);
    }
}

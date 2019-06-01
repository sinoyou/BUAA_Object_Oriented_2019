package navigate;

import com.oocourse.uml1.models.elements.UmlAssociation;
import com.oocourse.uml1.models.elements.UmlAssociationEnd;
import com.oocourse.uml1.models.elements.UmlElement;

import java.util.HashMap;

public class IdToUmlElement {
    private HashMap<String, UmlElement> idMap;
    private static IdToUmlElement idToUmlElement = null;

    /* ------- Single Instance Mode -------- */
    private IdToUmlElement() {
        idMap = new HashMap<>();
    }

    public static IdToUmlElement getInstance() {
        if (idToUmlElement == null) {
            idToUmlElement = new IdToUmlElement();
        }
        return idToUmlElement;
    }

    /* -------- General Nav -------- */
    public void addUmlElement(UmlElement umlElement) {
        idMap.put(umlElement.getId(), umlElement);
    }

    public UmlElement getUmlElementById(String id) {
        return idMap.get(id);
    }

    public boolean containsElement(String id) {
        return idMap.containsKey(id);
    }

    /* -------- Uml Association End -------- */
    public UmlAssociation getUmlAssoByUmlEnd(
        UmlAssociationEnd umlAssociationEnd) {

        String assoId = umlAssociationEnd.getParentId();
        UmlElement element = getUmlElementById(assoId);
        assert element instanceof UmlAssociation;
        return (UmlAssociation) element;
    }


}

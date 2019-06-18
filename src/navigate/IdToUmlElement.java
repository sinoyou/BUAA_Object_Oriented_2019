package navigate;


import com.oocourse.uml2.models.elements.UmlAssociation;
import com.oocourse.uml2.models.elements.UmlAssociationEnd;
import com.oocourse.uml2.models.elements.UmlElement;

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

    public UmlAssociationEnd getOppositeEndByEnd(UmlAssociationEnd umlAssociationEnd) {
        UmlAssociation umlAssociation = getUmlAssoByUmlEnd(umlAssociationEnd);
        // Set Opposite End of Association
        String oppositeEndId;
        if (umlAssociation.getEnd1().equals(umlAssociationEnd.getId())) {
            oppositeEndId = umlAssociation.getEnd2();
        } else {
            oppositeEndId = umlAssociation.getEnd1();
        }

        // Get the element of other side
        UmlAssociationEnd oppositeEnd =
            (UmlAssociationEnd) getUmlElementById(oppositeEndId);
        return oppositeEnd;
    }

    /* -------- unit test help function -------- */
    public void clearAll() {
        idMap.clear();
    }

}

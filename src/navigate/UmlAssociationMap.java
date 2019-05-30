package navigate;

import com.oocourse.uml1.models.elements.UmlAssociation;
import com.oocourse.uml1.models.elements.UmlAssociationEnd;

import java.util.HashMap;

public class UmlAssociationMap {
    private HashMap<String, UmlAssociation> assoMap;
    private static UmlAssociationMap umlAssociationMap = null;

    private UmlAssociationMap() {
        assoMap = new HashMap<>();
    }

    public static UmlAssociationMap getInstance() {
        if (umlAssociationMap == null) {
            umlAssociationMap = new UmlAssociationMap();
        }
        return umlAssociationMap;
    }

    public void addUmlAssociation(UmlAssociation umlAssociation) {
        assoMap.put(umlAssociation.getId(), umlAssociation);
    }

    private UmlAssociation getUmlAssoById(String id) {
        return assoMap.get(id);
    }

    public UmlAssociation getUmlAssoByUmlEnd(
        UmlAssociationEnd umlAssociationEnd) {

        String assoId = umlAssociationEnd.getParentId();
        return getUmlAssoById(assoId);
    }
}

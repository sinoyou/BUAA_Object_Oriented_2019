package precheck;

import com.oocourse.uml2.interact.common.AttributeClassInformation;
import com.oocourse.uml2.interact.exceptions.user.UmlRule002Exception;
import com.oocourse.uml2.models.elements.UmlAssociationEnd;
import com.oocourse.uml2.models.elements.UmlAttribute;
import compoent.model.ClassNode;
import navigate.IdToUmlElement;
import navigate.NodeNavigator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class Uml002Exception {
    // single instance mode
    private static IdToUmlElement idMap = IdToUmlElement.getInstance();
    private static NodeNavigator nodeNav = NodeNavigator.getInstance();

    public static void check() throws UmlRule002Exception {
        // set to store 002 errors of all classes
        HashSet<AttributeClassInformation> set = new HashSet<>();

        Iterator<ClassNode> classNodeIterator = nodeNav.getClassNodes();
        // loop over for each class
        while (classNodeIterator.hasNext()) {
            ClassNode node = classNodeIterator.next();
            HashMap<String, Integer> map = new HashMap<>();

            // step 1: loop over for all opposite association end first.
            Iterator<UmlAssociationEnd> endIterator = node.getSelfAssociationEnds();
            while (endIterator.hasNext()) {
                UmlAssociationEnd opEnd = idMap.getOppositeEndByEnd(endIterator.next());
                if (opEnd.getName() != null) {
                    Integer count = map.getOrDefault(opEnd.getName(), 0);
                    map.put(opEnd.getName(), count + 1);
                }
            }

            // step 2: loop over for all attributes in the class.
            Iterator<UmlAttribute> attrIterator = node.getSelfAttributes();
            while (attrIterator.hasNext()) {
                UmlAttribute umlAttribute = attrIterator.next();
                if (umlAttribute.getName() != null) {
                    Integer count = map.getOrDefault(umlAttribute.getName(), 0);
                    map.put(umlAttribute.getName(), count + 1);
                }
            }

            // check map for error 002 in the class
            for (Map.Entry<String, Integer> entry : map.entrySet()) {
                if (entry.getValue() > 1) {
                    set.add(new AttributeClassInformation(entry.getKey(), node.getKernelInstance().getName()));
                }
            }
        }

        // if set is not empty throw exception
        if (!set.isEmpty()) {
            throw new UmlRule002Exception(set);
        }
    }
}

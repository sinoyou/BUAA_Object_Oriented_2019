// package test.precheck; 

package precheck;

import com.oocourse.uml2.interact.common.AttributeClassInformation;
import com.oocourse.uml2.interact.exceptions.user.UmlRule002Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule008Exception;
import com.oocourse.uml2.interact.exceptions.user.UmlRule009Exception;
import com.oocourse.uml2.models.elements.UmlAssociation;
import com.oocourse.uml2.models.elements.UmlAssociationEnd;
import com.oocourse.uml2.models.elements.UmlAttribute;
import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlClassOrInterface;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlEndpoint;
import com.oocourse.uml2.models.elements.UmlEvent;
import com.oocourse.uml2.models.elements.UmlFinalState;
import com.oocourse.uml2.models.elements.UmlGeneralization;
import com.oocourse.uml2.models.elements.UmlInteraction;
import com.oocourse.uml2.models.elements.UmlInterface;
import com.oocourse.uml2.models.elements.UmlInterfaceRealization;
import com.oocourse.uml2.models.elements.UmlLifeline;
import com.oocourse.uml2.models.elements.UmlMessage;
import com.oocourse.uml2.models.elements.UmlOperation;
import com.oocourse.uml2.models.elements.UmlParameter;
import com.oocourse.uml2.models.elements.UmlPseudostate;
import com.oocourse.uml2.models.elements.UmlRegion;
import com.oocourse.uml2.models.elements.UmlState;
import com.oocourse.uml2.models.elements.UmlStateMachine;
import com.oocourse.uml2.models.elements.UmlTransition;
import com.oocourse.uml2.models.exceptions.UmlParseException;
import main.MyUmlGeneralInteraction;
import navigate.IdToUmlElement;
import navigate.NodeNavigator;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

import tool.JsonFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Uml002Exception Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class UmlExceptionTest {
    private static Integer idCount;
    private ArrayList<UmlElement> list;

    @Before
    public void before() throws Exception {
        idCount = 10000;
        list = new ArrayList<>();
        IdToUmlElement idMap = IdToUmlElement.getInstance();
        idMap.clearAll();
        NodeNavigator nav = NodeNavigator.getInstance();
        nav.clearAll();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: check()
     */
    @Test
    public void testCheckUml002() throws Exception {
        list.add(createClass("classA", "classA", "0", "public"));
        list.add(createClass("classB", "classB", "0", "public"));
        list.add(createClass("classC", "classC", "0", "private"));
        list.add(createClass("classD", "classD", "0", "private"));

        list.add(createAttribue("attr1", "classA", "private", "int"));
        list.add(createAttribue("attr2", "classA", "private", "int"));
        list.add(createAttribue("attr1", "classB", "private", "int"));
        list.add(createAttribue("s1", "classB", "private", "double"));
        list.add(createAttribue("s2", "classB", "private", "string"));
        list.addAll(createAssociation("classA", "classB", null, "attr1"));
        list.addAll(createAssociation("classA", "classB", "attr2", null));
        list.addAll(createAssociation("classC", "classD", null, "X"));
        list.addAll(createAssociation("classC", "classD", null, "X"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        UmlRule002Exception eSave = null;
        try {
            uml.checkForUml002();
        } catch (UmlRule002Exception e) {
            eSave = e;
        }
        HashSet<AttributeClassInformation> set = new HashSet<>();
        set.add(new AttributeClassInformation("attr1", "classA"));
        set.add(new AttributeClassInformation("X", "classC"));
        UmlRule002Exception std = new UmlRule002Exception(set);
        assertEquals(std.getMessage(), eSave.getMessage());
    }

    @Test
    public void testCheckUml0081() throws UmlParseException {
        list.add(createInterface("A", "A", "0", "public"));
        list.add(createInterface("B", "B", "0", "private"));
        list.add(createGeneralization("A", "B"));
        list.add(createGeneralization("B", "A"));
        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));
        UmlRule008Exception eSave = null;
        try {
            uml.checkForUml008();
        } catch (UmlRule008Exception e) {
            eSave = e;
        }
        HashSet<String> std = new HashSet<>();
        std.add("A");
        std.add("B");
        assertTrue(classNameCompare(std, eSave.getClasses()));
    }

    @Test
    public void testCheckUml0082() throws UmlParseException {
        list.add(createInterface("A", "A", "0", "public"));
        list.add(createInterface("B", "B", "0", null));
        list.add(createInterface("C", "C", "0", null));
        list.add(createInterface("D", "D", "0", null));
        list.add(createInterface("E", "E", "0", null));
        list.add(createInterface("F", "F", "0", null));
        list.add(createInterface("G", "G", "0", null));
        list.add(createInterface("H", "H", "0", null));
        list.add(createInterface("I", "I", "0", null));

        list.add(createGeneralization("A", "E"));
        list.add(createGeneralization("E", "D"));
        list.add(createGeneralization("D", "C"));
        list.add(createGeneralization("C", "B"));
        list.add(createGeneralization("B", "A"));
        list.add(createGeneralization("C", "I"));
        list.add(createGeneralization("C", "H"));
        list.add(createGeneralization("H", "I"));
        list.add(createGeneralization("H", "G"));
        list.add(createGeneralization("G", "F"));
        list.add(createGeneralization("F", "E"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        UmlRule008Exception eSave = null;
        try {
            uml.checkForUml008();
        } catch (UmlRule008Exception e) {
            eSave = e;
        }
        HashSet<String> std = new HashSet<>();
        std.add("A");
        std.add("B");
        std.add("C");
        std.add("D");
        std.add("E");
        std.add("H");
        std.add("G");
        std.add("F");
        assertTrue(classNameCompare(std, eSave.getClasses()));
    }

    @Test
    public void testCheckUml008() throws UmlParseException {
        list.add(createInterface("A", "A", "0", "public"));
        list.add(createInterface("B", "B", "0", null));
        list.add(createInterface("C", "C", "0", null));
        list.add(createInterface("D", "D", "0", null));
        list.add(createInterface("E", "E", "0", null));
        list.add(createInterface("F", "F", "0", null));
        list.add(createInterface("G", "G", "0", null));

        list.add(createGeneralization("A", "B"));
        list.add(createGeneralization("A", "C"));
        list.add(createGeneralization("B", "D"));
        list.add(createGeneralization("C", "D"));
        list.add(createGeneralization("D", "E"));
        list.add(createGeneralization("E", "F"));
        list.add(createGeneralization("F", "G"));
        list.add(createGeneralization("G", "A"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        HashSet<String> std = new HashSet<>();
        std.add("A");
        std.add("B");
        std.add("C");
        std.add("D");
        std.add("E");
        std.add("G");
        std.add("F");

        UmlRule008Exception eSave = null;
        try {
            uml.checkForUml008();
        } catch (UmlRule008Exception e) {
            eSave = e;
        }
        assertTrue(classNameCompare(std, eSave.getClasses()));
    }

    @Test
    public void testCheckUml0084() throws UmlParseException {
        list.add(createClass("A", "A", "0", "public"));
        list.add(createClass("B", "B", "0", null));
        list.add(createClass("C", "C", "0", null));
        list.add(createClass("D", "D", "0", null));
        list.add(createClass("E", "E", "0", null));
        list.add(createClass("F", "F", "0", null));
        list.add(createClass("G", "G", "0", null));
        list.add(createInterface("H", "H", "0", null));
        list.add(createInterface("I", "I", "0", null));
        list.add(createInterface("J", "J", "0", "public"));
        list.add(createInterface("K", "K", "0", null));
        list.add(createInterface("L", "L", "0", null));
        list.add(createInterface("M", "M", "0", null));

        list.add(createGeneralization("A", "B"));
        list.add(createGeneralization("B", "C"));
        list.add(createGeneralization("C", "D"));

        list.add(createGeneralization("E", "F"));
        list.add(createGeneralization("F", "G"));
        list.add(createGeneralization("G", "E"));

        list.add(createGeneralization("H", "K"));
        list.add(createGeneralization("K", "M"));
        list.add(createGeneralization("K", "L"));
        list.add(createGeneralization("L", "M"));
        list.add(createGeneralization("L", "J"));
        list.add(createGeneralization("J", "I"));
        list.add(createGeneralization("I", "H"));

        list.add(createRealization("A", "H"));
        list.add(createRealization("C", "M"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        HashSet<String> set = new HashSet<>();
        set.add("E");
        set.add("F");
        set.add("G");

        set.add("H");
        set.add("I");
        set.add("J");
        set.add("K");
        set.add("L");

        UmlRule008Exception eSave = null;
        try {
            uml.checkForUml008();
        }catch (UmlRule008Exception e){
            eSave = e;
        }
        assertTrue(classNameCompare(set,eSave.getClasses()));
    }

    @Test
    public void testCheckUml009() throws UmlParseException {
        list.add(createInterface("interC", "interC", "0", "public"));
        list.add(createInterface("interD", "interD", "0", "private"));
        list.add(createGeneralization("interC", "interD"));
        list.add(createGeneralization("interC", "interD"));

        list.add(createClass("classA1", "classA1", "0", "public"));
        list.add(createClass("classB1", "classB1", "0", "private"));
        list.add(createInterface("interC1", "interC1", "0", "public"));
        list.add(createInterface("interD1", "interD1", "0", "private"));
        list.add(createInterface("interE1", "interE1", "0", "public"));
        list.add(createGeneralization("classA1", "classB1"));
        list.add(createRealization("classA1", "interC1"));
        list.add(createRealization("classB1", "interE1"));
        list.add(createGeneralization("interC1", "interD1"));
        list.add(createGeneralization("interD1", "interE1"));

        list.add(createInterface("interB2", "interB2", "0", "private"));
        list.add(createInterface("interC2", "interC2", "0", "public"));
        list.add(createInterface("interD2", "interD2", "0", "protected"));
        list.add(createClass("classA2", "classA2", "0", "public"));
        list.add(createGeneralization("interB2", "interC2"));
        list.add(createGeneralization("interB2", "interD2"));
        list.add(createGeneralization("interC2", "interD2"));
        list.add(createRealization("classA2", "interC2"));
        list.add(createRealization("classA2", "interD2"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        UmlRule009Exception eSave = null;
        try {
            uml.checkForUml009();
        } catch (UmlRule009Exception e) {
            eSave = e;
        }
        Set<? extends UmlClassOrInterface> set = eSave.getClasses();
        HashSet<String> std = new HashSet<>();
        std.add("interC");
        std.add("classA1");
        std.add("interB2");
        std.add("classA2");
        assertTrue(classNameCompare(std, set));
    }

    private boolean classNameCompare(Set<String> names, Set<? extends UmlClassOrInterface> set) {
        HashSet<String> toBeCompared = new HashSet<>();
        for (UmlClassOrInterface node : set) {
            toBeCompared.add(node.getName());
        }
        return toBeCompared.equals(names);
    }

    // help function
    /* >>>>>>>> MODEL <<<<<<<< */
    private static UmlElement createClass(String id, String name, String parentId, String vis) throws UmlParseException {
        return UmlClass.loadFromExportedJson(
            JsonFactory.produceClass(id, name, parentId, vis)
        );
    }

    private static UmlElement createInterface(String id, String name, String parentId, String vis) throws UmlParseException {
        return UmlInterface.loadFromExportedJson(
            JsonFactory.produceInterface(id, name, parentId, vis)
        );
    }

    private static UmlElement createOperation(String id, String name, String parentId, String vis) throws UmlParseException {
        return UmlOperation.loadFromExportedJson(
            JsonFactory.produceOperation(id, name, parentId, vis)
        );
    }

    private static List<UmlElement> createAssociation(String id1, String id2, String end1Name, String end2Name) throws UmlParseException {
        ArrayList<UmlElement> list = new ArrayList<>();
        String assoId = idCount.toString();
        idCount++;
        String endId1 = idCount.toString();
        idCount++;
        String endId2 = idCount.toString();
        idCount++;
        list.add(UmlAssociation.loadFromExportedJson(
            JsonFactory.produceAssociation(assoId, null, id1, endId1, endId2)
        ));
        list.add(UmlAssociationEnd.loadFromExportedJson(
            JsonFactory.produceAssociationEnd(endId1, end1Name, assoId, id1)
        ));
        list.add(UmlAssociationEnd.loadFromExportedJson(
            JsonFactory.produceAssociationEnd(endId2, end2Name, assoId, id2)
        ));
        return list;
    }

    private static UmlElement createAttribue(String name, String classId, String vis, String type) throws UmlParseException {
        UmlElement e = UmlAttribute.loadFromExportedJson(
            JsonFactory.produceAttribute(idCount.toString(), name, classId, vis, type)
        );
        idCount++;
        return e;
    }

    private static UmlElement createParameter(String name, String opId, String direction, String type) throws UmlParseException {
        UmlElement e = UmlParameter.loadFromExportedJson(
            JsonFactory.produceParameter(idCount.toString(), name, opId, direction, type)
        );
        idCount++;
        return e;
    }

    private static UmlElement createGeneralization(String source, String target) throws UmlParseException {
        UmlElement e = UmlGeneralization.loadFromExportedJson(
            JsonFactory.produceGeneralization(idCount.toString(), null, source, source, target)
        );
        idCount++;
        return e;
    }

    private static UmlElement createRealization(String source, String target) throws UmlParseException {
        UmlElement e = UmlInterfaceRealization.loadFromExportedJson(
            JsonFactory.produceRealization(idCount.toString(), null, source, source, target)
        );
        idCount++;
        return e;
    }

    /* >>>>>>>> Interaction <<<<<<<< */
    private static UmlElement createEvent(String name, String parentId, String visibility) throws UmlParseException {
        UmlElement e = UmlEvent.loadFromExportedJson(
            JsonFactory.produceEvent(idCount.toString(), name, parentId, visibility, null, null)
        );
        idCount++;
        return e;
    }

    private static UmlElement createFinalState(String id, String parentId, String visibility) throws UmlParseException {
        UmlElement e = UmlFinalState.loadFromExportedJson(
            JsonFactory.produceFinalState(id, null, parentId, visibility)
        );
        return e;
    }

    private static UmlElement createPseudostate(String id, String parentId, String visibility) throws UmlParseException {
        UmlElement e = UmlPseudostate.loadFromExportedJson(
            JsonFactory.producePseudostate(id, null, parentId, visibility)
        );
        return e;
    }

    private static UmlElement createRegion(String id, String parentId, String visibility) throws UmlParseException {
        UmlElement e = UmlRegion.loadFromExportedJson(
            JsonFactory.produceRegion(id, null, parentId, visibility)
        );
        return e;
    }

    private static UmlElement createState(String id, String name, String parentId, String visibility) throws UmlParseException {
        UmlElement e = UmlState.loadFromExportedJson(
            JsonFactory.produceState(id, name, parentId, visibility)
        );
        return e;
    }

    private static UmlElement createStateMachine(String id, String name, String parentId) throws UmlParseException {
        UmlElement e = UmlStateMachine.loadFromExportedJson(
            JsonFactory.produceStateMachine(id, name, parentId)
        );
        return e;
    }

    private static UmlElement createTransition(String name, String parentId, String source, String target) throws UmlParseException {
        UmlElement e = UmlTransition.loadFromExportedJson(
            JsonFactory.produceTransition(idCount.toString(), name, parentId, "public", source, target, null)
        );
        idCount++;
        return e;
    }

    /* >>>>>>>> Collaboration <<<<<<<<  */
    private static UmlElement createInteraction(String id, String name, String parentId) throws UmlParseException {
        UmlElement e = UmlInteraction.loadFromExportedJson(
            JsonFactory.produceInteraction(id, name, parentId, "public")
        );
        return e;
    }

    private static UmlElement createEndPoint(String name, String parentId, String visibility) throws UmlParseException {
        UmlElement e = UmlEndpoint.loadFromExportedJson(
            JsonFactory.produceEndPoint(idCount.toString(), name, parentId, visibility)
        );
        idCount++;
        return e;
    }

    private static UmlElement createLifeline(String id, String name, String parentId, String visibility) throws UmlParseException {
        UmlElement e = UmlLifeline.loadFromExportedJson(
            JsonFactory.produceLifeLine(id, name, parentId, visibility, null, null)
        );
        return e;
    }

    private static UmlElement createMessage(String name, String parentId, String source, String target) throws UmlParseException {
        UmlElement e = UmlMessage.loadFromExportedJson(
            JsonFactory.produceMessage(idCount.toString(), name, parentId, "public", source, target, null)
        );
        idCount++;
        return e;
    }

    // 检查返回的迭代器内容是否正确。
    private static boolean testInterator(Iterator it,
                                         Object[] list) {
        HashMap<Object, Integer> mapStd = new HashMap<>();
        int cnt = 0;
        for (Object node : list) {
            if (!mapStd.containsKey(node)) {
                mapStd.put(node, 1);
            } else {
                int nowCnt = mapStd.get(node);
                mapStd.put(node, nowCnt + 1);
            }
        }
        HashMap<Object, Integer> map = new HashMap<>();
        while (it.hasNext()) {
            Object o = it.next();
            if (!map.containsKey(o)) {
                map.put(o, 1);
            } else {
                int nowCnt = map.get(o);
                map.put(o, nowCnt + 1);
            }
            cnt++;
        }
        return map.equals(mapStd) && list.length == cnt;
    }
} 

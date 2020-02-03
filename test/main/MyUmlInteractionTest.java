package main;// package test.;

import com.oocourse.uml2.interact.common.AttributeClassInformation;
import com.oocourse.uml2.interact.common.AttributeQueryType;
import com.oocourse.uml2.interact.common.OperationQueryType;
import com.oocourse.uml2.interact.exceptions.user.AttributeDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.AttributeNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.InteractionDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.InteractionNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.LifelineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.LifelineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.StateMachineNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.StateNotFoundException;
import com.oocourse.uml2.interact.exceptions.user.UmlRule008Exception;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlAssociation;
import com.oocourse.uml2.models.elements.UmlAssociationEnd;
import com.oocourse.uml2.models.elements.UmlAttribute;
import com.oocourse.uml2.models.elements.UmlClass;
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
import navigate.IdToUmlElement;
import navigate.NodeNavigator;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import precheck.Uml008Exception;
import tool.JsonFactory;

import javax.json.Json;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * main.MyUmlGeneralInteraction Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class MyUmlInteractionTest {
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
     * Method: getClassCount()
     */
    @Test
    public void testGetClassCount() throws Exception {
        list.add(createClass("1", "classA", "0", null));
        list.add(createClass("2", "classA", "1", null));
        list.add(createClass("3", "classB", "0", null));
        list.add(createInterface("4", "interfaceA", "0", null));
        list.add(createOperation("5", "op1", "3", null));
        list.add(createParameter("para1", "5", "in", "int"));
        list.add(createGeneralization("2", "1"));
        list.add(createRealization("3", "4"));
        list.addAll(createAssociation("3", "1"));
        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));
        assertEquals(3, uml.getClassCount());
    }

    /**
     * Method: getClassOperationCount(String s, OperationQueryType operationQueryType)
     */
    @Test
    public void testGetClassOperationCount() throws Exception {
        list.add(createClass("1", "classB", "0", null));
        list.add(createOperation("a", "ofp1", "1", "private"));
        list.add(createOperation("b", "ofp2", "1", "public"));
        list.add(createParameter("paraOfofp1", "a", "inout", "double"));
        list.add(createParameter("parapfofp2", "b", "return", "void"));

        list.add(createInterface("inter1", "classB", null, null));
        list.add(createOperation("xx`", "op", "inter1", "private"));
        list.add(createAttribue("para", "inter1", "private", "int"));

        list.add(createClass("2", "classA", "1", null));

        list.add(createOperation("c", "op1", "2", null));
        list.add(createParameter("in", "c", "in", "class"));
        list.add(createParameter("return", "c", "return", "object"));

        list.add(createOperation("d", "op1", "2", null));
        list.add(createParameter("out1", "d", "out", "int"));
        list.add(createParameter("out2", "d", "out", "double"));

        list.add(createOperation("e", "op3", "2", "protected"));
        list.add(createParameter("re", "e", "return", "long"));
        list.add(createOperation("f", "op4", "2", "package"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        assertEquals(2, uml.getClassOperationCount("classA", OperationQueryType.RETURN));
        assertEquals(2, uml.getClassOperationCount("classA", OperationQueryType.PARAM));
        assertEquals(2, uml.getClassOperationCount("classA", OperationQueryType.NON_PARAM));
        assertEquals(2, uml.getClassOperationCount("classA", OperationQueryType.NON_RETURN));
        assertEquals(4, uml.getClassOperationCount("classA", OperationQueryType.ALL));

        assertEquals(1, uml.getClassOperationCount("classB", OperationQueryType.NON_RETURN));
        assertEquals(1, uml.getClassOperationCount("classB", OperationQueryType.PARAM));
        assertEquals(2, uml.getClassOperationCount("classB", OperationQueryType.ALL));

    }

    /**
     * Method: getClassAttributeCount(String s, AttributeQueryType attributeQueryType)
     */
    @Test
    public void testGetClassAttributeCount() throws Exception {
        list.add(createClass("1", "classB", "0", null));
        list.add(createAttribue("A", "1", "private", "int"));
        list.add(createAttribue("B", "1", "public", "double"));
        list.add(createAttribue("C", "1", "protected", "float"));

        list.add(createClass("2", "classA", "0", null));
        list.add(createAttribue("A", "2", "public", "int"));
        list.add(createAttribue("B", "2", "public", "double"));

        list.add(createGeneralization("2", "1"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        assertEquals(3, uml.getClassAttributeCount("classB", AttributeQueryType.SELF_ONLY));
        assertEquals(3, uml.getClassAttributeCount("classB", AttributeQueryType.ALL));
        assertEquals(5, uml.getClassAttributeCount("classA", AttributeQueryType.ALL));
        assertEquals(2, uml.getClassAttributeCount("classA", AttributeQueryType.SELF_ONLY));
    }

    /**
     * Method: getClassAssociationCount(String s)
     */
    @Test
    public void testGetClassAssociationCount() throws Exception {
        list.add(createClass("1", "classA", null, "public"));
        list.add(createClass("2", "classB", null, "private"));
        list.add(createClass("3", "classC", "1", "protected"));
        list.add(createInterface("4", "interfaceA", "0", null));
        list.add(createInterface("5", "interfaceB", "4", "package"));
        list.add(createClass("6", "classX", "0", "public"));
        list.add(createClass("7", "classX", "0", "private"));

        list.add(createGeneralization("2", "1"));
        list.add(createRealization("1", "4"));
        list.add(createRealization("2", "5"));
        list.addAll(createAssociation("5", "2"));
        list.addAll(createAssociation("5", "4"));
        list.addAll(createAssociation("1", "2"));
        list.addAll(createAssociation("2", "1"));
        list.addAll(createAssociation("1", "1"));
        list.addAll(createAssociation("2", "2"));
        list.addAll(createAssociation("1", "3"));
        list.addAll(createAssociation("3", "2"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        assertEquals(5, uml.getClassAssociationCount("classA"));
        assertEquals(11, uml.getClassAssociationCount("classB"));
        assertEquals(2, uml.getClassAssociationCount("classC"));

        boolean flag = false;
        try {
            uml.getClassAssociationCount("classX");
        } catch (ClassDuplicatedException e) {
            flag = true;
        }
        assertTrue(flag);

        flag = false;
        try {
            uml.getClassAssociationCount("interfaceA");
        } catch (ClassNotFoundException e) {
            flag = true;
        }
        assertTrue(flag);
    }

    /**
     * Method: getClassAssociatedClassList(String s)
     */
    @Test
    public void testGetClassAssociatedClassList() throws Exception {
        list.add(createClass("1", "classA", null, "public"));
        list.add(createClass("2", "classB", null, "private"));
        list.add(createClass("3", "classC", "1", "protected"));
        list.add(createClass("6", "classX", "0", "public"));
        list.add(createClass("7", "classX", "0", "private"));
        list.add(createClass("8", "classY", "0", "public"));

        list.add(createGeneralization("2", "1"));
        list.addAll(createAssociation("1", "2"));
        list.addAll(createAssociation("2", "1"));
        list.addAll(createAssociation("1", "1"));
        list.addAll(createAssociation("2", "2"));
        list.addAll(createAssociation("1", "3"));
        list.addAll(createAssociation("3", "2"));
        list.addAll(createAssociation("6", "1"));
        list.addAll(createAssociation("7", "2"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        List list = uml.getClassAssociatedClassList("classB");
        Object[] stdList = new String[] {"classB", "classA", "classC", "classX", "classX"};
        assertTrue(testInterator(list.iterator(), stdList));

        list = uml.getClassAssociatedClassList("classA");
        stdList = new String[] {"classA", "classB", "classC", "classX"};
        assertTrue(testInterator(list.iterator(), stdList));

        list = uml.getClassAssociatedClassList("classC");
        stdList = new String[] {"classA", "classB"};
        assertTrue(testInterator(list.iterator(), stdList));

        assertTrue(uml.getClassAssociatedClassList("classY").isEmpty());
    }

    /**
     * Method: getClassOperationVisibility(String s, String s1)
     */
    @Test
    public void testGetClassOperationVisibility() throws Exception {
        list.add(createClass("A", "classA", "0", "public"));
        list.add(createOperation("1", "m1", "A", "public"));
        list.add(createOperation("2", "m1", "A", "package"));

        list.add(createClass("B", "classB", "0", "public"));
        list.add(createOperation("3", "m1", "B", "public"));
        list.add(createOperation("4", "m1", "B", "public"));
        list.add(createOperation("5", "m1", "B", "public"));
        list.add(createOperation("6", "m1", "B", "public"));

        list.add(createOperation("7", "m1", "B", "private"));
        list.add(createOperation("8", "m1", "B", "private"));
        list.add(createOperation("9", "m1", "B", "private"));

        list.add(createOperation("10", "m1", "B", "protected"));
        list.add(createOperation("11", "m1", "B", "protected"));

        list.add(createOperation("12", "m1", "B", "package"));

        list.add(createGeneralization("B", "A"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        HashMap<Visibility, Integer> stdMap = new HashMap<>();
        stdMap.put(Visibility.PUBLIC, 4);
        stdMap.put(Visibility.PRIVATE, 3);
        stdMap.put(Visibility.PROTECTED, 2);
        stdMap.put(Visibility.PACKAGE, 1);

        assertEquals(stdMap, uml.getClassOperationVisibility("classB", "m1"));

        stdMap.clear();

        stdMap.put(Visibility.PUBLIC, 0);
        stdMap.put(Visibility.PRIVATE, 0);
        stdMap.put(Visibility.PROTECTED, 0);
        stdMap.put(Visibility.PACKAGE, 0);
        assertEquals(stdMap, uml.getClassOperationVisibility("classB", "masdijo"));

        stdMap.clear();
        stdMap.put(Visibility.PUBLIC, 1);
        stdMap.put(Visibility.PRIVATE, 0);
        stdMap.put(Visibility.PROTECTED, 0);
        stdMap.put(Visibility.PACKAGE, 1);
        assertEquals(stdMap, uml.getClassOperationVisibility("classA", "m1"));
    }

    /**
     * Method: getClassAttributeVisibility(String s, String s1)
     */
    @Test
    public void testGetClassAttributeVisibility() throws Exception {
        list.add(createClass("A", "classA", null, "public"));
        list.add(createClass("B", "classB", "A", "private"));
        list.add(createGeneralization("B", "A"));

        list.add(createAttribue("b", "A", "private", "double"));
        list.add(createAttribue("d", "A", "package", "int"));
        list.add(createAttribue("a", "A", "private", "int"));
//        list.add(createAttribue("a", "A", "public", "int"));

        list.add(createAttribue("b", "B", "protected", "double"));
        list.add(createAttribue("c", "B", "package", "int"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        assertEquals(Visibility.PACKAGE, uml.getClassAttributeVisibility("classB", "c"));
        assertEquals(Visibility.PACKAGE, uml.getClassAttributeVisibility("classB", "d"));
        assertEquals(Visibility.PRIVATE, uml.getClassAttributeVisibility("classA", "b"));

        // duplicated check
        boolean flag = false;
        try {
            uml.getClassAttributeVisibility("classB", "b");
        } catch (AttributeDuplicatedException e) {
            flag = true;
        }
        assertTrue(flag);

//        flag = false;
//        try {
//            uml.getClassAttributeVisibility("classB", "a");
//        } catch (AttributeDuplicatedException e) {
//            flag = true;
//            assertEquals("classB", e.getClassName());
//        }
//        assertTrue(flag);

        // not found check
        flag = false;
        try {
            uml.getClassAttributeVisibility("classB", "e");
        } catch (AttributeNotFoundException e) {
            flag = true;
        }
        assertTrue(flag);
    }

    /**
     * Method: getTopParentClass(String s)
     */
    @Test
    public void testGetTopParentClass() throws Exception {
        // pass
    }

    /**
     * Method: getImplementInterfaceList(String s)
     */
    @Test
    public void testGetImplementInterfaceList() throws Exception {
        list.add(createClass("clsA", "classA", "0", "public"));
        list.add(createClass("clsB", "classB", "clsA", "public"));
        list.add(createClass("clsC", "classC", "clsB", "private"));
        list.add(createClass("clsD", "classD", null, "public"));

        list.add(createInterface("interA", "interfaceA", "interB", null));
        list.add(createInterface("interB", "interfaceB", "interD", null));
        list.add(createInterface("interC", "interfaceC", "interC", null));
        list.add(createInterface("interD", "interfaceD", "0", "public"));
        list.add(createInterface("interE", "interfaceE", "0", "private"));
        list.add(createInterface("interE2", "interfaceE", "0", "public"));
        list.add(createInterface("interX", "interfaceX", null, null));
        list.add(createInterface("interY", "interfaceY", "0", null));

        // add relation
        // class <-> class
        list.add(createGeneralization("clsB", "clsA"));
//        list.add(createGeneralization("clsC", "clsB"));
        list.addAll(createAssociation("clsC", "clsD"));
        // interface <-> interface
        list.add(createGeneralization("interA", "interB"));
        list.add(createGeneralization("interA", "interC"));
        list.add(createGeneralization("interB", "interD"));
//        list.add(createGeneralization("interC", "interD"));
        list.add(createGeneralization("interB", "interE"));
        list.addAll(createAssociation("interC", "interX"));
        // class <-> interface
        list.add(createRealization("clsC", "interA"));
        list.add(createRealization("clsA", "interE"));
        list.add(createRealization("clsA", "interD"));
        list.add(createRealization("clsB", "interE2"));
        list.add(createRealization("clsD", "interY"));
        list.addAll(createAssociation("clsD", "interA"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        String[] list;
        list = new String[] {"interfaceY"};
        assertTrue(testInterator(uml.getImplementInterfaceList("classD").iterator(), list));

        list = new String[] {"interfaceA", "interfaceB", "interfaceC", "interfaceD", "interfaceE"};
//        list = new String[] {"interfaceA", "interfaceB", "interfaceC", "interfaceD", "interfaceE", "interfaceE"};
        assertTrue(testInterator(uml.getImplementInterfaceList("classC").iterator(), list));

        list = new String[] {"interfaceE", "interfaceE", "interfaceD"};
        assertTrue(testInterator(uml.getImplementInterfaceList("classB").iterator(), list));

        list = new String[] {"interfaceE", "interfaceD"};
        assertTrue(testInterator(uml.getImplementInterfaceList("classA").iterator(), list));
    }

    /**
     * Method: getInformationNotHidden(String s)
     */
    @Test
    public void testGetInformationNotHidden() throws Exception {
        // case 1
        list.add(createClass("clsA", "classA", "0", null));
        list.add(createClass("clsB", "classB", "clsA", null));
        list.add(createClass("clsC", "classC", "clsB", "public"));
        list.add(createClass("clsD", "classD", null, null));
        list.add(createClass("clsE", "classE", null, null));

        list.add(createGeneralization("clsB", "clsA"));
        list.add(createGeneralization("clsC", "clsB"));
        list.add(createGeneralization("clsD", "clsC"));
        list.addAll(createAssociation("clsB", "clsE"));

        list.add(createAttribue("a", "clsB", "private", "int"));
        list.add(createAttribue("a", "clsC", "protected", "int"));
        list.add(createAttribue("b", "clsC", "private", "double"));
        list.add(createAttribue("a", "clsD", "protected", "int"));
        list.add(createAttribue("c", "clsE", "package", "float"));

        // case 2
        list.add(createClass("clsX1", "classX", null, null));
        list.add(createClass("clsX2", "classX", null, null));
        list.add(createClass("clsY", "classY", "clsX2", null));
        list.add(createGeneralization("clsX2", "clsX1"));
        list.add(createGeneralization("clsY", "clsX2"));
        list.add(createAttribue("a", "clsX1", "public", "int"));
        list.add(createAttribue("a", "clsX2", "protected", "int"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));
        assertTrue(uml.getInformationNotHidden("classA").isEmpty());
        assertTrue(uml.getInformationNotHidden("classB").isEmpty());

        AttributeClassInformation[] list;
        list = new AttributeClassInformation[] {new AttributeClassInformation("a", "classC")};
        assertTrue(testInterator(uml.getInformationNotHidden("classC").iterator(), list));

        list = new AttributeClassInformation[] {
            new AttributeClassInformation("a", "classC"),
            new AttributeClassInformation("a", "classD")
        };
        assertTrue(testInterator(uml.getInformationNotHidden("classD").iterator(), list));

        list = new AttributeClassInformation[] {
            new AttributeClassInformation("a", "classX"),
            new AttributeClassInformation("a", "classX")
        };
        assertTrue(testInterator(uml.getInformationNotHidden("classY").iterator(), list));
    }

    @Test
    public void testGetMessageCount() throws UmlParseException, InteractionNotFoundException, InteractionDuplicatedException {
        list.add(createInteraction("interA", "interA", "0"));
        list.add(createInteraction("interB1", "interB", "0"));
        list.add(createInteraction("interB2", "interB", null));

        list.add(createLifeline("lifeA", "lifeA", "interA", "public"));
        list.add(createLifeline("lifeB", "lifeB", "interA", "private"));
        list.add(createLifeline("lifeC", "lifeC", "interA", "package"));
        list.add(createLifeline("lifeD1", "lifeD", "interA", "public"));
        list.add(createLifeline("lifeD2", "lifeD", "interA", "private"));

        list.add(createEndPoint("p0", "interA", "private"));
        list.add(createEndPoint("p1", "interA", "public"));
        list.add(createEndPoint("p2", "interA", "private"));
        list.add(createEndPoint("p3", "interA", "public"));

        // message with name
        list.add(createMessage("m1", "interA", "lifeA", "lifeB"));
        list.add(createMessage("m1", "interA", "lifeA", "lifeB"));
        list.add(createMessage("m2", "interA", "lifeB", "lifeC"));
        list.add(createMessage("m3", "interA", "lifeA", "lifeB"));
        list.add(createMessage("m4", "interA", "lifeB", "lifeA"));
        list.add(createMessage("m5", "interA", "lifeD1", "lifeB"));
        // message without name
        list.add(createMessage(null, "interA", "p1", "lifeD2"));
        list.add(createMessage(null, "interA", "lifeD2", "p2"));
        list.add(createMessage(null, "interA", "lifeA", "p0"));
        list.add(createMessage(null, "interA", "p3", "lifeA"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        assertEquals(10, uml.getMessageCount("interA"));
        boolean flag = false;
        try {
            uml.getMessageCount("interB");
        } catch (InteractionDuplicatedException e) {
            flag = true;
        }
        assertTrue(flag);
        flag = false;
        try {
            uml.getMessageCount("interX");
        } catch (InteractionNotFoundException e) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void testGetParticipantCount() throws UmlParseException, InteractionNotFoundException, InteractionDuplicatedException {
        list.add(createInteraction("interA", "interA", "0"));
        list.add(createInteraction("interB1", "interB", "0"));
        list.add(createInteraction("interC", "interC", null));

        list.add(createLifeline("lifeA", "lifeA", "interA", "public"));
        list.add(createLifeline("lifeB", "lifeB", "interA", "private"));
        list.add(createLifeline("lifeC", "lifeC", "interA", "package"));
        list.add(createLifeline("lifeD1", "lifeD", "interA", "public"));
        list.add(createLifeline("lifeD2", "lifeD", "interA", "private"));

        list.add(createEndPoint("p0", "interA", "private"));
        list.add(createEndPoint("p1", "interA", "public"));
        list.add(createEndPoint("p2", "interA", "private"));
        list.add(createEndPoint("p3", "interA", "public"));

        // message with name
        list.add(createMessage("m1", "interA", "lifeA", "lifeB"));
        list.add(createMessage("m1", "interA", "lifeA", "lifeB"));
        list.add(createMessage("m2", "interA", "lifeB", "lifeC"));
        list.add(createMessage("m3", "interA", "lifeA", "lifeB"));
        list.add(createMessage("m4", "interA", "lifeB", "lifeA"));
        list.add(createMessage("m5", "interA", "lifeD1", "lifeB"));
        // message without name
        list.add(createMessage(null, "interA", "p1", "lifeD2"));
        list.add(createMessage(null, "interA", "lifeD2", "p2"));
        list.add(createMessage(null, "interA", "lifeA", "p0"));
        list.add(createMessage(null, "interA", "p3", "lifeA"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        assertEquals(5, uml.getParticipantCount("interA"));
        assertEquals(0, uml.getParticipantCount("interB"));
    }

    @Test
    public void testGetIncomingMessageCount() throws UmlParseException, LifelineNotFoundException, InteractionNotFoundException, InteractionDuplicatedException, LifelineDuplicatedException {
        list.add(createInteraction("interA", "interA", "0"));
        list.add(createInteraction("interB1", "interB", "0"));
        list.add(createInteraction("interC", "interC", null));

        list.add(createLifeline("lifeA", "lifeA", "interA", "public"));
        list.add(createLifeline("lifeB", "lifeB", "interA", "private"));
        list.add(createLifeline("lifeC", "lifeC", "interA", "package"));
        list.add(createLifeline("lifeD1", "lifeD", "interA", "public"));
        list.add(createLifeline("lifeD2", "lifeD", "interA", "private"));

        list.add(createEndPoint("p0", "interA", "private"));
        list.add(createEndPoint("p1", "interA", "public"));
        list.add(createEndPoint("p2", "interA", "private"));
        list.add(createEndPoint("p3", "interA", "public"));

        // message with name
        list.add(createMessage("m1", "interA", "lifeA", "lifeB"));
        list.add(createMessage("m1", "interA", "lifeA", "lifeB"));
        list.add(createMessage("m2", "interA", "lifeB", "lifeC"));
        list.add(createMessage("m3", "interA", "lifeA", "lifeB"));
        list.add(createMessage("m4", "interA", "lifeC", "lifeB"));
        list.add(createMessage("m5", "interA", "lifeD1", "lifeB"));
        // message without name
        list.add(createMessage(null, "interA", "p1", "lifeD2"));
        list.add(createMessage(null, "interA", "lifeD2", "p2"));
        list.add(createMessage(null, "interA", "lifeA", "p0"));
        list.add(createMessage(null, "interA", "p3", "lifeA"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        assertEquals(5, uml.getIncomingMessageCount("interA", "lifeB"));
        assertEquals(1, uml.getIncomingMessageCount("interA", "lifeA"));
        boolean flag = false;
        try {
            uml.getIncomingMessageCount("interA", "lifeD");
        } catch (LifelineDuplicatedException e) {
            flag = true;
            assertEquals("interA", e.getInteractionName());
        }
        assertTrue(flag);

        flag = false;
        try {
            uml.getIncomingMessageCount("interA", "lifeX");
        } catch (LifelineNotFoundException e) {
            flag = true;
            assertEquals("interA", e.getInteractionName());
        }
        assertTrue(flag);

    }

    @Test
    public void testStateCount() throws UmlParseException, StateMachineDuplicatedException, StateMachineNotFoundException {
        list.add(createStateMachine("1", "machine1", "0"));
        list.add(createStateMachine("2", "machine2", "0"));
        list.add(createStateMachine("3", "machine3", "0"));
        list.add(createStateMachine("4", "machiney", "0"));
        list.add(createStateMachine("5", "machiney", "0"));
        list.add(createRegion("r1", "1", "public"));
        list.add(createRegion("r2", "2", "private"));
        list.add(createRegion("r3", "3", "protected"));

        // region 1
        list.add(createPseudostate("r1ps1", "r1", "public"));
        list.add(createPseudostate("r1ps2", "r1", "private"));
        list.add(createPseudostate("r1ps3", "r1", "public"));
        list.add(createFinalState("r1fs1", "r1", "public"));
        list.add(createFinalState("r1fs2", "r1", "protected"));
        list.add(createState("r1sA", "A", "r1", "public"));
        list.add(createState("r1sB", "B", "r1", "private"));
        list.add(createState("r1sC", "C", "r1", "public"));
        list.add(createState("r1sD", "D", "r1", "protected"));
        list.add(createTransition(null, "r1", "r1ps1", "r1sA"));
        list.add(createTransition(null, "r1", "r1ps2", "r1sB"));
        list.add(createTransition(null, "r1", "r1ps3", "r1sC"));
        list.add(createTransition(null, "r1", "r1sA", "r1fs1"));
        list.add(createTransition(null, "r1", "r1sD", "r1sA"));
        list.add(createTransition(null, "r1", "r1sB", "r1sD"));
        list.add(createTransition(null, "r1", "r1sC", "r1fs2"));

        // region 2
        list.add(createState("r2sA", "A", "r2", "public"));
        list.add(createState("r2sB", "B", "r2", "private"));
        list.add(createState("r2sC", "C", "r2", "protected"));
        list.add(createTransition("t1", "r2", "r2sA", "r2sA"));
        list.add(createTransition("t2", "r2", "r2sA", "r2sB"));
        list.add(createTransition("t3", "r2", "r2sB", "r2sC"));
        list.add(createTransition("t4", "r2", "r2sC", "r2sA"));
        list.add(createTransition("t5", "r2", "r2sC", "r2sA"));
        list.add(createTransition("t6", "r2", "r2sC", "r2sB"));


        // region 3
        list.add(createState("r3x1", "X", "r3", "public"));
        list.add(createState("r3x2", "X", "r3", "public"));
        list.add(createState("r3a", "A", "r3", "private"));
        list.add(createState("r3b", "B", "r3", "protected"));
        list.add(createState("r3c", "C", "r3", "public"));

        list.add(createState("r3p", "P", "r3", "public"));
        list.add(createState("r3q", "Q", "r3", "private"));

        list.add(createState("r3m", "M", "r3", "public"));
        list.add(createState("r3n", "N", "r3", null));
        list.add(createState("r3o", "O", "r3", null));

        list.add(createPseudostate("r3ps1", "r3", "public"));
        list.add(createFinalState("r3fs1", "r3", null));
        list.add(createFinalState("r3fs2", "r3", null));
        list.add(createFinalState("r3fs3", "r3", null));

        list.add(createTransition(null, "r3", "r3a", "r3a"));
        list.add(createTransition(null, "r3", "r3a", "r3b"));
        list.add(createTransition(null, "r3", "r3b", "r3c"));
        list.add(createTransition(null, "r3", "r3c", "r3b"));
        list.add(createTransition(null, "r3", "r3c", "r3fs1"));
        list.add(createTransition(null, "r3", "r3c", "r3fs2"));
        list.add(createTransition(null, "r3", "r3c", "r3fs3"));
        list.add(createTransition(null, "r3", "r3ps1", "r3c"));
        list.add(createTransition(null, "r3", "r3ps1", "r3a"));

        list.add(createTransition("t1", "r3", "r3m", "r3n"));
        list.add(createTransition("t2", "r3", "r3n", "r3o"));
        list.add(createTransition("t3", "r3", "r3o", "r3m"));

        list.add(createTransition(null, "r3", "r3p", "r3q"));
        list.add(createTransition(null, "r3", "r3q", "r3p"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        assertEquals(6, uml.getStateCount("machine1"));
        assertEquals(3, uml.getStateCount("machine2"));
        assertEquals(12, uml.getStateCount("machine3"));
        boolean flag = false;
        try {
            uml.getStateCount("machinex");
        } catch (StateMachineNotFoundException e) {
            flag = true;
        }
        assertTrue(flag);

        flag = false;
        try {
            uml.getStateCount("machiney");
        } catch (StateMachineDuplicatedException e) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void testGetTransitionCount() throws UmlParseException, StateMachineDuplicatedException, StateMachineNotFoundException {
        list.add(createStateMachine("1", "machine1", "0"));
        list.add(createStateMachine("2", "machine2", "0"));
        list.add(createStateMachine("3", "machine3", "0"));
        list.add(createStateMachine("4", "machiney", "0"));
        list.add(createStateMachine("5", "machiney", "0"));
        list.add(createRegion("r1", "1", "public"));
        list.add(createRegion("r2", "2", "private"));
        list.add(createRegion("r3", "3", "protected"));

        // region 1
        list.add(createPseudostate("r1ps1", "r1", "public"));
        list.add(createPseudostate("r1ps2", "r1", "private"));
        list.add(createPseudostate("r1ps3", "r1", "public"));
        list.add(createFinalState("r1fs1", "r1", "public"));
        list.add(createFinalState("r1fs2", "r1", "protected"));
        list.add(createState("r1sA", "A", "r1", "public"));
        list.add(createState("r1sB", "B", "r1", "private"));
        list.add(createState("r1sC", "C", "r1", "public"));
        list.add(createState("r1sD", "D", "r1", "protected"));
        list.add(createTransition(null, "r1", "r1ps1", "r1sA"));
        list.add(createTransition(null, "r1", "r1ps2", "r1sB"));
        list.add(createTransition(null, "r1", "r1ps3", "r1sC"));
        list.add(createTransition(null, "r1", "r1sA", "r1fs1"));
        list.add(createTransition(null, "r1", "r1sD", "r1sA"));
        list.add(createTransition(null, "r1", "r1sB", "r1sD"));
        list.add(createTransition(null, "r1", "r1sC", "r1fs2"));

        // region 2
        list.add(createState("r2sA", "A", "r2", "public"));
        list.add(createState("r2sB", "B", "r2", "private"));
        list.add(createState("r2sC", "C", "r2", "protected"));
        list.add(createTransition("t1", "r2", "r2sA", "r2sA"));
        list.add(createTransition("t2", "r2", "r2sA", "r2sB"));
        list.add(createTransition("t3", "r2", "r2sB", "r2sC"));
        list.add(createTransition("t4", "r2", "r2sC", "r2sA"));
        list.add(createTransition("t5", "r2", "r2sC", "r2sA"));
        list.add(createTransition("t6", "r2", "r2sC", "r2sB"));


        // region 3
        list.add(createState("r3x1", "X", "r3", "public"));
        list.add(createState("r3x2", "X", "r3", "public"));
        list.add(createState("r3a", "A", "r3", "private"));
        list.add(createState("r3b", "B", "r3", "protected"));
        list.add(createState("r3c", "C", "r3", "public"));

        list.add(createState("r3p", "P", "r3", "public"));
        list.add(createState("r3q", "Q", "r3", "private"));

        list.add(createState("r3m", "M", "r3", "public"));
        list.add(createState("r3n", "N", "r3", null));
        list.add(createState("r3o", "O", "r3", null));

        list.add(createPseudostate("r3ps1", "r3", "public"));
        list.add(createFinalState("r3fs1", "r3", null));
        list.add(createFinalState("r3fs2", "r3", null));
        list.add(createFinalState("r3fs3", "r3", null));

        list.add(createTransition(null, "r3", "r3a", "r3a"));
        list.add(createTransition(null, "r3", "r3a", "r3b"));
        list.add(createTransition(null, "r3", "r3b", "r3c"));
        list.add(createTransition(null, "r3", "r3c", "r3b"));
        list.add(createTransition(null, "r3", "r3c", "r3fs1"));
        list.add(createTransition(null, "r3", "r3c", "r3fs2"));
        list.add(createTransition(null, "r3", "r3c", "r3fs3"));
        list.add(createTransition(null, "r3", "r3ps1", "r3c"));
        list.add(createTransition(null, "r3", "r3ps1", "r3a"));

        list.add(createTransition("t1", "r3", "r3m", "r3n"));
        list.add(createTransition("t2", "r3", "r3n", "r3o"));
        list.add(createTransition("t3", "r3", "r3o", "r3m"));

        list.add(createTransition(null, "r3", "r3p", "r3q"));
        list.add(createTransition(null, "r3", "r3q", "r3p"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        assertEquals(7, uml.getTransitionCount("machine1"));
        assertEquals(6, uml.getTransitionCount("machine2"));
        assertEquals(14, uml.getTransitionCount("machine3"));
    }

    @Test
    public void testGetSubsequentCount() throws UmlParseException, StateMachineDuplicatedException, StateDuplicatedException, StateMachineNotFoundException, StateNotFoundException {
        list.add(createStateMachine("1", "machine1", "0"));
        list.add(createStateMachine("2", "machine2", "0"));
        list.add(createStateMachine("3", "machine3", "0"));
        list.add(createStateMachine("4", "machiney", "0"));
        list.add(createStateMachine("5", "machiney", "0"));
        list.add(createRegion("r1", "1", "public"));
        list.add(createRegion("r2", "2", "private"));
        list.add(createRegion("r3", "3", "protected"));

        // region 1
        list.add(createPseudostate("r1ps1", "r1", "public"));
        list.add(createPseudostate("r1ps2", "r1", "private"));
        list.add(createPseudostate("r1ps3", "r1", "public"));
        list.add(createFinalState("r1fs1", "r1", "public"));
        list.add(createFinalState("r1fs2", "r1", "protected"));
        list.add(createState("r1sA", "A", "r1", "public"));
        list.add(createState("r1sB", "B", "r1", "private"));
        list.add(createState("r1sC", "C", "r1", "public"));
        list.add(createState("r1sD", "D", "r1", "protected"));
        list.add(createTransition(null, "r1", "r1ps1", "r1sA"));
        list.add(createTransition(null, "r1", "r1ps2", "r1sB"));
        list.add(createTransition(null, "r1", "r1ps3", "r1sC"));
        list.add(createTransition(null, "r1", "r1sA", "r1fs1"));
        list.add(createTransition(null, "r1", "r1sD", "r1sA"));
        list.add(createTransition(null, "r1", "r1sB", "r1sD"));
        list.add(createTransition(null, "r1", "r1sC", "r1fs2"));

        // region 2
        list.add(createState("r2sA", "A", "r2", "public"));
        list.add(createState("r2sB", "B", "r2", "private"));
        list.add(createState("r2sC", "C", "r2", "protected"));
        list.add(createTransition("t1", "r2", "r2sA", "r2sA"));
        list.add(createTransition("t2", "r2", "r2sA", "r2sB"));
        list.add(createTransition("t3", "r2", "r2sB", "r2sC"));
        list.add(createTransition("t4", "r2", "r2sC", "r2sA"));
        list.add(createTransition("t5", "r2", "r2sC", "r2sA"));
        list.add(createTransition("t6", "r2", "r2sC", "r2sB"));


        // region 3
        list.add(createState("r3x1", "X", "r3", "public"));
        list.add(createState("r3x2", "X", "r3", "public"));
        list.add(createState("r3a", "A", "r3", "private"));
        list.add(createState("r3b", "B", "r3", "protected"));
        list.add(createState("r3c", "C", "r3", "public"));

        list.add(createState("r3p", "P", "r3", "public"));
        list.add(createState("r3q", "Q", "r3", "private"));

        list.add(createState("r3m", "M", "r3", "public"));
        list.add(createState("r3n", "N", "r3", null));
        list.add(createState("r3o", "O", "r3", null));

        list.add(createPseudostate("r3ps1", "r3", "public"));
        list.add(createFinalState("r3fs1", "r3", null));
        list.add(createFinalState("r3fs2", "r3", null));
        list.add(createFinalState("r3fs3", "r3", null));

        list.add(createTransition(null, "r3", "r3a", "r3a"));
        list.add(createTransition(null, "r3", "r3a", "r3b"));
        list.add(createTransition(null, "r3", "r3b", "r3c"));
        list.add(createTransition(null, "r3", "r3c", "r3b"));
        list.add(createTransition(null, "r3", "r3c", "r3fs1"));
        list.add(createTransition(null, "r3", "r3c", "r3fs2"));
        list.add(createTransition(null, "r3", "r3c", "r3fs3"));
        list.add(createTransition(null, "r3", "r3ps1", "r3c"));
        list.add(createTransition(null, "r3", "r3ps1", "r3a"));

        list.add(createTransition("t1", "r3", "r3m", "r3n"));
        list.add(createTransition("t2", "r3", "r3n", "r3o"));
        list.add(createTransition("t3", "r3", "r3o", "r3m"));

        list.add(createTransition(null, "r3", "r3p", "r3q"));
        list.add(createTransition(null, "r3", "r3q", "r3p"));

        MyUmlGeneralInteraction uml = new MyUmlGeneralInteraction(list.toArray(new UmlElement[0]));

        assertEquals(1, uml.getSubsequentStateCount("machine1", "A"));
        assertEquals(3, uml.getSubsequentStateCount("machine1", "B"));
        assertEquals(1, uml.getSubsequentStateCount("machine1", "C"));

        assertEquals(3, uml.getSubsequentStateCount("machine2", "A"));
        assertEquals(3, uml.getSubsequentStateCount("machine2", "B"));
        assertEquals(3, uml.getSubsequentStateCount("machine2", "C"));

        assertEquals(4, uml.getSubsequentStateCount("machine3", "A"));
        assertEquals(3, uml.getSubsequentStateCount("machine3", "B"));
        assertEquals(3, uml.getSubsequentStateCount("machine3", "C"));
        assertEquals(2, uml.getSubsequentStateCount("machine3", "P"));
        assertEquals(2, uml.getSubsequentStateCount("machine3", "Q"));
        assertEquals(3, uml.getSubsequentStateCount("machine3", "M"));
        assertEquals(3, uml.getSubsequentStateCount("machine3", "N"));
        assertEquals(3, uml.getSubsequentStateCount("machine3", "O"));

        boolean flag = false;
        try {
            uml.getSubsequentStateCount("machine3", "X");
        } catch (StateDuplicatedException e) {
            assertEquals("machine3", e.getStateMachineName());
            flag = true;
        }
        assertTrue(flag);
        flag = false;
        try {
            uml.getSubsequentStateCount("machine3", "XX");
        } catch (StateNotFoundException e) {
            assertEquals("machine3", e.getStateMachineName());
            flag = true;
        }
        assertTrue(flag);
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

    private static List<UmlElement> createAssociation(String id1, String id2) throws UmlParseException {
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
            JsonFactory.produceAssociationEnd(endId1, null, assoId, id1)
        ));
        list.add(UmlAssociationEnd.loadFromExportedJson(
            JsonFactory.produceAssociationEnd(endId2, null, assoId, id2)
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

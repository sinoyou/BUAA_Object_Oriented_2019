// package test.navigate; 

package navigate;

import com.oocourse.uml2.interact.exceptions.user.ClassDuplicatedException;
import com.oocourse.uml2.interact.exceptions.user.ClassNotFoundException;
import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlElement;
import com.oocourse.uml2.models.elements.UmlInterface;
import com.oocourse.uml2.models.elements.UmlOperation;
import component.ClassNode;
import component.InterfaceNode;
import component.OperationNode;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import tool.JsonFactory;

import javax.json.Json;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * NodeNavigator Tester. before和after对单例模式是无效的，需要手动清零单例数据。
 *
 * @author <Authors name>
 * @version 1.0
 */
public class NodeNavigatorTest {
    private NodeNavigator nav = NodeNavigator.getInstance();
    private ClassNode classNode1, classNode2, classNode3;
    private InterfaceNode interfaceNode1, interfaceNode2;
    private OperationNode operationNode1, operationNode2, operationNode3;

    @Before
    public void before() throws Exception {
        nav.clearAll();
        UmlClass class1 = UmlClass.loadFromExportedJson(JsonFactory.produceClass("1", "class1", "0", null));
        UmlClass class2 = UmlClass.loadFromExportedJson(JsonFactory.produceClass("2", "class2", "0", null));
        UmlClass class3 = UmlClass.loadFromExportedJson(JsonFactory.produceClass("3", "class1", "0", null));
        classNode1 = new ClassNode(class1);
        classNode2 = new ClassNode(class2);
        classNode3 = new ClassNode(class3);

        UmlInterface interface1 = UmlInterface.loadFromExportedJson(
            JsonFactory.produceInterface("4", "interface", "0", null));
        UmlInterface interface2 = UmlInterface.loadFromExportedJson(
            JsonFactory.produceInterface("5", "interface", "0", null));
        interfaceNode1 = new InterfaceNode(interface1);
        interfaceNode2 = new InterfaceNode(interface2);

        UmlOperation operation1 = UmlOperation.loadFromExportedJson(
            JsonFactory.produceOperation("6", "op1", "1", null)
        );
        UmlOperation operation2 = UmlOperation.loadFromExportedJson(
            JsonFactory.produceOperation("7", "op2", "1", null)
        );
        UmlOperation operation3 = UmlOperation.loadFromExportedJson(
            JsonFactory.produceOperation("8", "op3", "4", null)
        );
        operationNode1 = new OperationNode(operation1);
        operationNode2 = new OperationNode(operation2);
        operationNode3 = new OperationNode(operation3);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getInstance()
     */
    @Test
    public void testGetInstance() throws Exception {
        NodeNavigator nav1 = NodeNavigator.getInstance();
        NodeNavigator nav2 = NodeNavigator.getInstance();
        assertSame(nav1, nav2);
    }

    /**
     * Method: addOneClassNode(ClassNode classNode)
     */
    @Test
    public void testAddOneClassNode() throws Exception {

    }

    /**
     * Method: getClassNodeByName(String name)
     */
    @Test
    public void testGetClassNodeByName() throws Exception {
        nav.addOneClassNode(classNode3);
        nav.addOneClassNode(classNode2);
        assertSame(classNode3, nav.getClassNodeByName("class1"));
        assertSame(classNode2, nav.getClassNodeByName("class2"));
        nav.addOneClassNode(classNode3);
        boolean flag = false;
        try {
            nav.getClassNodeByName("class1");
        } catch (ClassDuplicatedException e) {
            flag = true;
        }
        assertTrue(flag);
        flag = false;
        try {
            nav.getClassNodeByName("class4");
        } catch (ClassNotFoundException e) {
            flag = true;
        }
        assertTrue(flag);
    }

    /**
     * Method: getClassNodeById(String id)
     */
    @Test
    public void testGetClassNodeById() throws Exception {
        nav.addOneClassNode(classNode1);
        nav.addOneClassNode(classNode2);
        nav.addOneClassNode(classNode3);
        assertSame(classNode1, nav.getClassNodeById("1"));
        assertSame(classNode2, nav.getClassNodeById("2"));
        assertSame(classNode3, nav.getClassNodeById("3"));
    }

    /**
     * Method: containsClassNode(String id)
     */
    @Test
    public void testContainsClassNode() throws Exception {
        assertFalse(nav.containsClassNode("1"));
        assertFalse(nav.containsClassNode("2"));
        nav.addOneClassNode(classNode1);
        nav.addOneClassNode(classNode2);
        nav.addOneClassNode(classNode3);
        nav.addOneInterfaceNode(interfaceNode1);
        assertTrue(nav.containsClassNode("1"));
        assertTrue(nav.containsClassNode("2"));
        assertTrue(nav.containsClassNode("3"));
        assertFalse(nav.containsClassNode("4"));
        assertFalse(nav.containsClassNode("1asldk1j2oimd_28uda"));
    }

    /**
     * Method: addOneInterfaceNode(InterfaceNode interfaceNode)
     */
    @Test
    public void testAddOneInterfaceNode() throws Exception {

    }

    /**
     * Method: getInterfaceNodeById(String id)
     */
    @Test
    public void testGetInterfaceNodeById() throws Exception {
        nav.addOneInterfaceNode(interfaceNode1);
        nav.addOneInterfaceNode(interfaceNode2);
        assertSame(interfaceNode1, nav.getInterfaceNodeById("4"));
        assertSame(interfaceNode2, nav.getInterfaceNodeById("5"));
    }

    /**
     * Method: containsInterfaceNode(String id)
     */
    @Test
    public void testContainsInterfaceNode() throws Exception {
        assertFalse(nav.containsInterfaceNode("4"));
        assertFalse(nav.containsInterfaceNode("5"));
        nav.addOneInterfaceNode(interfaceNode1);
        nav.addOneInterfaceNode(interfaceNode2);
        nav.addOneClassNode(classNode1);
        assertTrue(nav.containsInterfaceNode("4"));
        assertTrue(nav.containsInterfaceNode("5"));
        assertFalse(nav.containsInterfaceNode("1"));
    }

    /**
     * Method: addOneOperation(OperationNode operationNode)
     */
    @Test
    public void testAddOneOperation() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: getOperationNodeById(String id)
     */
    @Test
    public void testGetOperationNodeById() throws Exception {
        nav.addOneOperation(operationNode1);
        nav.addOneOperation(operationNode2);
        nav.addOneOperation(operationNode3);
        assertSame(operationNode1,nav.getOperationNodeById("6"));
        assertSame(operationNode3,nav.getOperationNodeById("8"));
    }

    /**
     * Method: getOperationNodes()
     */
    @Test
    public void testGetOperationNodes() throws Exception {
        nav.addOneOperation(operationNode2);
        nav.addOneOperation(operationNode3);
        HashSet<OperationNode> set = new HashSet<>();
        Iterator<OperationNode> it1 = nav.getOperationNodes();
        while(it1.hasNext()){
            set.add(it1.next());
        }
        HashSet<OperationNode> stdSet = new HashSet<>();
        stdSet.add(operationNode2);
        stdSet.add(operationNode3);
        assertEquals(stdSet,set);

        nav.addOneOperation(operationNode1);
        set = new HashSet<>();
        Iterator<OperationNode> it2 = nav.getOperationNodes();
        while(it2.hasNext()){
            set.add(it2.next());
        }
        stdSet = new HashSet<>();
        stdSet.add(operationNode1);
        stdSet.add(operationNode2);
        stdSet.add(operationNode3);
        assertEquals(stdSet,set);
    }

    /**
     * Method: containsOperationNode(String id)
     */
    @Test
    public void testContainsOperationNode() throws Exception {
        assertFalse(nav.containsOperationNode("6"));
        nav.addOneClassNode(classNode1);
        nav.addOneInterfaceNode(interfaceNode1);
        assertFalse(nav.containsOperationNode("1"));
        assertFalse(nav.containsOperationNode("4"));
        nav.addOneOperation(operationNode1);
        nav.addOneOperation(operationNode2);
        assertTrue(nav.containsOperationNode("6"));
        assertTrue(nav.containsOperationNode("7"));
        assertFalse(nav.containsOperationNode("8"));
    }


} 

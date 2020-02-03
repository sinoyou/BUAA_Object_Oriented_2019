// package test.component; 

package component;

import com.oocourse.uml2.models.elements.UmlInterface;
import compoent.model.InterfaceNode;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import tool.JsonFactory;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;

/**
 * InterfaceNode Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class InterfaceNodeTest {
    private InterfaceNode nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG;

    @Before
    public void before() throws Exception {
        nodeA = new InterfaceNode(
            UmlInterface.loadFromExportedJson(
                JsonFactory.produceInterface("1", "A", "0", null)
            )
        );
        nodeB = new InterfaceNode(
            UmlInterface.loadFromExportedJson(
                JsonFactory.produceInterface("2", "B", "0", null)
            )
        );
        nodeC = new InterfaceNode(
            UmlInterface.loadFromExportedJson(
                JsonFactory.produceInterface("3", "C", "0", null)
            )
        );
        nodeD = new InterfaceNode(
            UmlInterface.loadFromExportedJson(
                JsonFactory.produceInterface("4", "D", "0", null)
            )
        );
        nodeE = new InterfaceNode(
            UmlInterface.loadFromExportedJson(
                JsonFactory.produceInterface("5", "E", "0", null)
            )
        );
        nodeF = new InterfaceNode(
            UmlInterface.loadFromExportedJson(
                JsonFactory.produceInterface("6", "F", "0", null)
            )
        );
        nodeG = new InterfaceNode(
            UmlInterface.loadFromExportedJson(
                JsonFactory.produceInterface("7", "G", "0", null)
            )
        );
        nodeA.addGenerateFrom(nodeB);
        nodeA.addGenerateFrom(nodeC);
        nodeA.addGenerateFrom(nodeG);
        nodeB.addGenerateFrom(nodeE);
        nodeB.addGenerateFrom(nodeD);
        nodeC.addGenerateFrom(nodeE);
        nodeC.addGenerateFrom(nodeF);
        nodeE.addGenerateFrom(nodeG);
        nodeF.addGenerateFrom(nodeG);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: addGenerateFrom(InterfaceNode interfaceNode)
     */
    @Test
    public void testAddGenerateFrom() throws Exception {

    }

    /**
     * Method: getGenerateListIterator()
     */
    @Test
    public void testGetGenerateListIterator() throws Exception {
        InterfaceNode[] list;
        list = new InterfaceNode[] {nodeA, nodeB, nodeC, nodeD, nodeE, nodeF, nodeG};
        assertTrue(testInterator(nodeA.getGenerateListIterator(), list, 7));
        list = new InterfaceNode[] {nodeB, nodeD, nodeG, nodeE};
        assertTrue(testInterator(nodeB.getGenerateListIterator(), list, 4));
        list = new InterfaceNode[] {nodeD};
        assertTrue(testInterator(nodeD.getGenerateListIterator(), list, 1));
        list = new InterfaceNode[] {nodeC, nodeE, nodeG, nodeF};
        assertTrue(testInterator(nodeC.getGenerateListIterator(), list, 4));
    }


    /**
     * Method: getGenerateList()
     */
    @Test
    public void testGetGenerateList() throws Exception {
/*
try { 
   Method method = InterfaceNode.getClass().getMethod("getGenerateList"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

    private boolean testInterator(Iterator<InterfaceNode> it,
                                  InterfaceNode[] list,
                                  int amount) {
        HashSet<InterfaceNode> setStd = new HashSet<>();
        int cnt = 0;
        for (InterfaceNode node : list) {
            setStd.add(node);
        }
        HashSet<InterfaceNode> set = new HashSet<>();
        while (it.hasNext()) {
            set.add(it.next());
            cnt++;
        }
        return set.equals(setStd) && amount == cnt;
    }
} 

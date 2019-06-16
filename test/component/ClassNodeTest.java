// package test.component; 

package component;

import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlGeneralization;
import com.oocourse.uml2.models.elements.UmlInterface;
import com.oocourse.uml2.models.elements.UmlInterfaceRealization;
import navigate.IdToUmlElement;
import navigate.NodeNavigator;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import tool.JsonFactory;

import static org.junit.Assert.*;

import javax.json.Json;
import java.util.HashSet;
import java.util.Iterator;

/**
 * ClassNode Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class ClassNodeTest {
    private ClassNode classNodeA, classNodeB, classNodeC, classNodeD, classNodeX;
    private InterfaceNode interfaceNodeX;
    private NodeNavigator nav = NodeNavigator.getInstance();

    @Before
    public void before() throws Exception {
        nav.clearAll();
        UmlClass classA = UmlClass.loadFromExportedJson(
            JsonFactory.produceClass("1", "classA", "0", null)
        );
        UmlClass classB = UmlClass.loadFromExportedJson(
            JsonFactory.produceClass("2", "classB", "0", null)
        );
        UmlClass classC = UmlClass.loadFromExportedJson(
            JsonFactory.produceClass("3", "classC", "0", "private")
        );
        UmlClass classD = UmlClass.loadFromExportedJson(
            JsonFactory.produceClass("4", "classD", "0", null)
        );
        UmlClass classX = UmlClass.loadFromExportedJson(
            JsonFactory.produceClass("5", "classX", "0", null)
        );
        UmlInterface interfaceX = UmlInterface.loadFromExportedJson(
            JsonFactory.produceInterface("6", "interfaceX", "0", null)
        );
        classNodeA = new ClassNode(classA);
        classNodeB = new ClassNode(classB);
        classNodeC = new ClassNode(classC);
        classNodeD = new ClassNode(classD);
        classNodeX = new ClassNode(classX);
        interfaceNodeX = new InterfaceNode(interfaceX);
        nav.addOneClassNode(classNodeA);
        nav.addOneClassNode(classNodeB);
        nav.addOneClassNode(classNodeC);
        nav.addOneClassNode(classNodeD);
        nav.addOneClassNode(classNodeX);
        nav.addOneInterfaceNode(interfaceNodeX);
        classNodeB.addGenerateFrom(classNodeA);
        classNodeC.addGenerateFrom(classNodeB);
        classNodeD.addGenerateFrom(classNodeC);
        classNodeD.addRealize(interfaceNodeX);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getKernelInstance()
     */
    @Test
    public void testGetKernelInstance() throws Exception {

    }

    /**
     * Method: addRealize(InterfaceNode interfaceNode)
     */
    @Test
    public void testAddRealize() throws Exception {

    }

    /**
     * Method: getSelfImplementInterface()
     */
    @Test
    public void testGetSelfImplementInterface() throws Exception {

    }

    /**
     * Method: addGenerateFrom(ClassNode classNode)
     */
    @Test
    public void testAddGenerateFrom() throws Exception {

    }

    /**
     * Method: getGenerateListIterator()
     */
    @Test
    public void testGetGenerateListIterator() throws Exception {
        Iterator<ClassNode> it1 = classNodeD.getGenerateListIterator();
        HashSet<ClassNode> set1 = new HashSet<>();
        while (it1.hasNext()) {
            set1.add(it1.next());
        }
        HashSet<ClassNode> setStd = new HashSet<>();
        setStd.add(classNodeD);
        setStd.add(classNodeC);
        setStd.add(classNodeB);
        setStd.add(classNodeA);
        assertEquals(set1, setStd);
        it1 = classNodeA.getGenerateListIterator();
        int cnt = 0;
        while (it1.hasNext()) {
            cnt++;
            assertSame(classNodeA, it1.next());
        }
        assertEquals(1, cnt);
    }

    /**
     * Method: getTopClass()
     */
    @Test
    public void testGetTopClass() throws Exception {
        assertSame(classNodeA, classNodeB.getTopClass());
        assertSame(classNodeA, classNodeA.getTopClass());
        assertSame(classNodeA, classNodeD.getTopClass());
        assertSame(classNodeA, classNodeC.getTopClass());
    }


    /**
     * Method: getGenerateList()
     */
    @Test
    public void testGetGenerateList() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = ClassNode.getClass().getMethod("getGenerateList"); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
    }

} 

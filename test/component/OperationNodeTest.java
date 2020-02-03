// package test.component; 

package component;

import com.oocourse.uml2.models.common.ElementType;
import com.oocourse.uml2.models.common.Visibility;
import com.oocourse.uml2.models.elements.UmlOperation;
import com.oocourse.uml2.models.elements.UmlParameter;
import compoent.model.OperationNode;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import tool.JsonFactory;

/**
 * OperationNode Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>���� 1, 2019</pre>
 */
public class OperationNodeTest {
    private static String opPublic = "public";
    private static String opPrivate = "private";
    private static String opProtected = "protected";
    private static String opPackage = "package";

    private static String paraIn = "in";
    private static String paraOut = "out";
    private static String paraInOut = "inout";
    private static String paraReturn = "return";

    private UmlOperation op1;
    private UmlOperation op2;
    private OperationNode node1;
    private OperationNode node2;
    @Before
    public void before() throws Exception {
        op1 = UmlOperation.loadFromJson(JsonFactory.produceOperation("1","op1","0",opPublic));
        op2 = UmlOperation.loadFromJson(JsonFactory.produceOperation("2","op2","0",opPackage));
        node1 = new OperationNode(op1);
        node2 = new OperationNode(op2);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getKernelInstance()
     */
    @Test
    public void testGetKernelInstance() throws Exception {
        assertEquals(op1,node1.getKernelInstance());
        assertEquals(op2, node2.getKernelInstance());
        assertEquals("op1",node1.getKernelInstance().getName());
        assertEquals(Visibility.PACKAGE,node2.getKernelInstance().getVisibility());
        assertEquals(ElementType.UML_OPERATION,node2.getKernelInstance().getElementType());
    }

    /**
     * Method: addParameter(UmlParameter umlParameter)
     */
    @Test
    public void testAddParameter() throws Exception {

    }

    /**
     * Method: hasReturn()
     */
    @Test
    public void testHasReturn() throws Exception {
        assertFalse(node1.hasReturn());
        node1.addParameter(UmlParameter.loadFromJson(JsonFactory.produceParameter("3","para1","1","out","double")));
        assertFalse(node1.hasReturn());
        node1.addParameter(UmlParameter.loadFromJson(JsonFactory.produceParameter("4","para2","1","inout","String")));
        node1.addParameter(UmlParameter.loadFromJson(JsonFactory.produceParameter("5","para3","1","in","double")));
        assertFalse(node1.hasReturn());
        node1.addParameter(UmlParameter.loadFromJson(JsonFactory.produceParameter("6","para4","1","return","A")));
        assertTrue(node1.hasReturn());
    }

    /**
     * Method: hasIn()
     */
    @Test
    public void testHasIn() throws Exception {
        node1.addParameter(UmlParameter.loadFromJson(JsonFactory.produceParameter("6","para4","1","return","double")));
        assertFalse(node1.hasIn());
        node1.addParameter(UmlParameter.loadFromJson(JsonFactory.produceParameter("3","para1","1","out","double")));
        assertTrue(node1.hasReturn());
        assertFalse(node2.hasIn());
        node2.addParameter(UmlParameter.loadFromJson(JsonFactory.produceParameter("4","para2","2","inout","double")));
        assertTrue(node2.hasIn());
    }


} 

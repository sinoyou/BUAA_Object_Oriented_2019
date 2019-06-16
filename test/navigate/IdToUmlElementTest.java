// package test.navigate; 

package navigate;

import com.oocourse.uml2.models.elements.UmlAssociation;
import com.oocourse.uml2.models.elements.UmlAssociationEnd;
import com.oocourse.uml2.models.elements.UmlClass;
import com.oocourse.uml2.models.elements.UmlElement;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import tool.JsonFactory;

import javax.json.Json;

import static org.junit.Assert.*;

/**
 * IdToUmlElement Tester.
 *
 * @author <Authors name>
 * @version 1.0
 */
public class IdToUmlElementTest {
    private UmlElement class1, op1, para1, asso1, interface1, assoEnd11, assoEnd12;

    @Before
    public void before() throws Exception {
        class1 = UmlElement.loadFromExportedJson(JsonFactory.produceClass("1", "class1", "0", null));
        op1 = UmlElement.loadFromExportedJson(JsonFactory.produceOperation("2", "op1", "1", null));
        para1 = UmlElement.loadFromExportedJson(JsonFactory.produceParameter("3", "para1", "2", "in", "hello"));
        asso1 = UmlElement.loadFromExportedJson(JsonFactory.produceAssociation("4", "asso1", "1", "6", "7"));
        interface1 = UmlElement.loadFromExportedJson(JsonFactory.produceInterface("5", "interface1", "0", null));
        assoEnd11 = UmlElement.loadFromExportedJson(JsonFactory.produceAssociationEnd("6", "asso1End1", "4", "5"));
        assoEnd12 = UmlElement.loadFromExportedJson(JsonFactory.produceAssociationEnd("7", "asso1End2", "4", "1"));

        IdToUmlElement map = IdToUmlElement.getInstance();
        map.addUmlElement(class1);
        map.addUmlElement(op1);
        map.addUmlElement(para1);
        map.addUmlElement(asso1);
        map.addUmlElement(assoEnd11);
        map.addUmlElement(assoEnd12);
        map.addUmlElement(interface1);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getInstance()
     */
    @Test
    public void testGetInstance() throws Exception {
        IdToUmlElement map1 = IdToUmlElement.getInstance();
        IdToUmlElement map2 = IdToUmlElement.getInstance();
        assertSame(map1, map2);
        assertEquals(map1, map2);
    }

    /**
     * Method: addUmlElement(UmlElement umlElement)
     */
    @Test
    public void testAddUmlElement() throws Exception {

    }

    /**
     * Method: getUmlElementById(String id)
     */
    @Test
    public void testGetUmlElementById() throws Exception {
        IdToUmlElement map = IdToUmlElement.getInstance();
        assertSame(class1, map.getUmlElementById("1"));
        assertSame(op1, map.getUmlElementById("2"));
        assertSame(para1, map.getUmlElementById("3"));
        assertSame(asso1, map.getUmlElementById("4"));
        assertSame(assoEnd12, map.getUmlElementById("7"));
    }

    /**
     * Method: containsElement(String id)
     */
    @Test
    public void testContainsElement() throws Exception {
        IdToUmlElement map = IdToUmlElement.getInstance();
        assertTrue(map.containsElement("5"));
        assertTrue(map.containsElement("6"));
        assertFalse(map.containsElement("8"));
    }

    /**
     * Method: getUmlAssoByUmlEnd(UmlAssociationEnd umlAssociationEnd)
     */
    @Test
    public void testGetUmlAssoByUmlEnd() throws Exception {
        IdToUmlElement map = IdToUmlElement.getInstance();
        assertSame(asso1, map.getUmlAssoByUmlEnd((UmlAssociationEnd) assoEnd11));
        assertSame(asso1, map.getUmlAssoByUmlEnd((UmlAssociationEnd) assoEnd12));
    }


} 

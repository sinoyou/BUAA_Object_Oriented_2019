// package test.subway.tool; 

package subway.tool;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

/**
 * ConvertMap Tester.
 *
 * @version 1.0
 * 0 -> 1(100) 2(101) 3(100) 4(103)
 * -1 -> 5(101)
 * 198213 -> 6(100) 7(106)
 */
public class ConvertMapTest {

    ConvertMap convertMap;

    @Before
    public void before() throws Exception {
        convertMap = new ConvertMap();
        convertMap.addConvert(0, 1, 100);
        convertMap.addConvert(0, 2, 101);
        convertMap.addConvert(0, 3, 100);
        convertMap.addConvert(0, 4, 103);
        convertMap.addConvert(-1, 5, 101);
        convertMap.addConvert(198213, 6, 100);
        convertMap.addConvert(198213, 7, 106);
    }

    @After
    public void after() throws Exception {

    }

    /**
     * Method: addConvert(int actual, int virtual, int pathId)
     */
    @Test
    public void testAddConvert() throws Exception {
//TODO: Test goes here... 
    }


    /**
     * Method: virtual2Actual(int virtual)
     */
    @Test
    public void testVirtual2Actual() throws Exception {
        assertEquals(0, convertMap.virtual2Actual(1));
        assertEquals(0, convertMap.virtual2Actual(2));
        assertEquals(0, convertMap.virtual2Actual(3));
        assertEquals(0, convertMap.virtual2Actual(4));
        assertEquals(-1, convertMap.virtual2Actual(5));
        assertEquals(198213, convertMap.virtual2Actual(6));
        assertEquals(198213, convertMap.virtual2Actual(7));
    }

    /**
     * Method: actual2Virtual(int actual)
     */
    @Test
    public void testActual2Virtual() throws Exception {
        assertTrue(iteratorCheck(convertMap.actual2Virtual(0), new int[] {1, 2, 3, 4}));
        assertTrue(iteratorCheck(convertMap.actual2Virtual(-1), new int[] {5}));
        assertTrue(iteratorCheck(convertMap.actual2Virtual(198213), new int[] {6, 7}));
    }

/**
 * ConvertMap Tester.
 * @version 1.0
 * 0 -> 1(100) 2(101) 3(100) 4(103)
 * -1 -> 5(101)
 * 198213 -> 6(100) 7(106)
 */


    /**
     * Method: virtual2PathId(int virtual)
     */
    @Test
    public void testVirtual2PathId() throws Exception {
        assertEquals(100, convertMap.virtual2PathId(1));
        assertEquals(101, convertMap.virtual2PathId(2));
        assertEquals(100, convertMap.virtual2PathId(3));
        assertEquals(103, convertMap.virtual2PathId(4));
        assertEquals(101, convertMap.virtual2PathId(5));
        assertEquals(100, convertMap.virtual2PathId(6));
        assertEquals(106, convertMap.virtual2PathId(7));
    }

    private boolean iteratorCheck(Iterator<Integer> it, int[] ans) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < ans.length; i++) {
            set.add(ans[i]);
        }
        HashSet<Integer> set1 = new HashSet<>();
        while (it.hasNext()) {
            set1.add(it.next());
        }
        return set.equals(set1);
    }
} 

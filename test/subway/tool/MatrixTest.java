// package test.subway.tool; 

package subway.tool;

import org.junit.Assert;
import org.junit.Assert.*;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Matrix Tester.
 *
 * @version 1.0
 */
public class MatrixTest {
    /**
     * 100   0    0    0    0
     * 1     0    0    0    0
     * 0     5    7    0    0
     * 0     0    0    0    1
     * 0     0    0    0    0
     */

    Matrix matrix;

    @Before
    public void before() throws Exception {
        matrix = new Matrix();
        matrix.addPair(0, 0, 100);
        matrix.addPair(1, 0, 1);
        matrix.addPair(2, 1, 5);
        matrix.addPair(2, 2, 7);
        matrix.addPair(3, 4, 1);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: addPair(int a, int b, int value)
     */
    @Test
    public void testAddPair() throws Exception {
        assertEquals(100, matrix.getValue(0, 0));
        matrix.addPair(4, 3, 987);
        assertTrue(matrix.isExist(4, 3));
        assertEquals(987, matrix.getValue(4, 3));
        assertFalse(matrix.isExist(3, 3));
        matrix.addPair(3, 4, 1);
        assertEquals(1, matrix.getValue(3, 4));
    }

    /**
     * Method: deletePair(int a, int b)
     */
    @Test
    public void testDeletePair() throws Exception {
        assertTrue(matrix.isFirstIndexExist(0));
        matrix.deletePair(0, 0);
        assertFalse(matrix.isExist(0, 0));
        matrix.deletePair(1, 0);
        assertFalse(matrix.isFirstIndexExist(1));
        assertFalse(matrix.isExist(1, 0));
        assertFalse(matrix.isFirstIndexExist(0));
    }

    /**
     * Method: addGroup(int node, HashMap<Integer, Integer> map)
     */
    @Test
    public void testAddGroup() throws Exception {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(1, 101);
        map.put(2, 9);
        map.put(32, 9);
        matrix.addGroup(2, map);
        matrix.addGroup(4, map);
        matrix.addPair(4, 0, 3);

        assertEquals(101, matrix.getValue(2, 1));
        assertEquals(9, matrix.getValue(2, 2));
        assertEquals(9, matrix.getValue(2, 32));
        assertEquals(3, matrix.getValue(4, 0));
    }

    /**
     * Method: isExist(int a, int b)
     */
    @Test
    public void testIsExist() throws Exception {
        assertTrue(matrix.isExist(0, 0));
        assertTrue(matrix.isExist(2, 1));
        assertFalse(matrix.isExist(2, 0));
        assertFalse(matrix.isExist(4, 4));
        matrix.deletePair(2, 1);
        assertFalse(matrix.isExist(2, 1));
        matrix.deletePair(2, 2);
        assertFalse(matrix.isExist(2, 2));
    }

    /**
     * Method: getValue(int a, int b)
     */
    @Test
    public void testGetValue() throws Exception {

    }

    /**
     * Method: getExistFirstIndexAmount()
     */
    @Test
    public void testGetExistFirstIndexAmount() throws Exception {
        assertEquals(4, matrix.getExistFirstIndexAmount());
        matrix.deletePair(1, 0);
        assertEquals(3, matrix.getExistFirstIndexAmount());
    }

    @Test
    public void testIsFirstIndexExist() throws Exception {
        assertTrue(matrix.isFirstIndexExist(0));
        assertTrue(matrix.isFirstIndexExist(1));
        assertTrue(matrix.isFirstIndexExist(2));
        assertTrue(matrix.isFirstIndexExist(3));
        assertFalse(matrix.isFirstIndexExist(4));
        matrix.deletePair(0, 0);
        assertFalse(matrix.isFirstIndexExist(0));
    }

    /**
     * Method: getExistFirstIndex()
     */
    @Test
    public void testGetExistFirstIndex() throws Exception {
        assertTrue(iteratorCheck(matrix.getExistFirstIndex(), new int[] {0, 1, 2, 3}));
        matrix.deletePair(1, 0);
        assertTrue(iteratorCheck(matrix.getExistFirstIndex(), new int[] {0, 2, 3}));
        matrix.deletePair(0, 0);
        matrix.deletePair(2, 1);
        matrix.deletePair(2, 2);
        matrix.deletePair(3, 4);
        assertTrue(iteratorCheck(matrix.getExistFirstIndex(), new int[] {}));
    }

    /**
     * Method: getExistSecondIndex(int from)
     */
    @Test
    public void testGetExistSecondIndex() throws Exception {
        iteratorCheck(matrix.getExistSecondIndex(2), new int[] {1, 2});
        matrix.deletePair(2, 1);
        iteratorCheck(matrix.getExistSecondIndex(2), new int[] {2});
        boolean flag = false;
        try {
            iteratorCheck(matrix.getExistSecondIndex(4), new int[] {2});
        } catch (Exception e) {
            flag = true;
        }
        assertTrue(flag);
    }


    /**
     * Method: add(int from, int to, int value)
     */
    @Test
    public void testAdd() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = Matrix.getClass().getMethod("add", int.class, int.class, int.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/
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

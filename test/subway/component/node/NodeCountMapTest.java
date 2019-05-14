// package test.subway.component;

package subway.component.node;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import subway.component.MyPath;
import subway.component.node.NodeCountMap;

import java.util.*;

/**
 * NodeCountMap Tester.
 *
 * @version 1.0
 */
public class NodeCountMapTest {
    NodeCountMap countMap = new NodeCountMap();

    @Before
    public void before() throws Exception {
        countMap.addOnePath(new MyPath(new int[] {1, 3, 4, 6}), 1);
        countMap.addOnePath(new MyPath(new int[] {2, 3, 7, 10, 12, 11}), 2);
        countMap.addOnePath(new MyPath(new int[] {14, 7, 4, 5}), 3);
        countMap.addOnePath(new MyPath(new int[] {8, 7, 13, 9, 8}), 4);
        countMap.addOnePath(new MyPath(new int[] {10, 10, 10}), 5);
        countMap.addOnePath(new MyPath(new int[] {10, 10}), 6);
        countMap.addOnePath(new MyPath(new int[] {100, 101}), 7);
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: addOnePath(Path path)
     */
    @Test
    public void testAddOnePath() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: removeOnePath(Path path)
     */
    @Test
    public void testRemoveOnePath() throws Exception {

    }

    /**
     * Method: size()
     */
    @Test
    public void testSize() throws Exception {
        Assert.assertEquals(16, countMap.size());
        countMap.removeOnePath(new MyPath(new int[] {1, 3, 4, 6}), 1);
        Assert.assertEquals(14, countMap.size());
    }

    /**
     * Method: containsNode(int nodeId)
     */
    @Test
    public void testContainsNode() throws Exception {
        for (int i = 1; i <= 13; i++) {
            Assert.assertTrue(countMap.containsNode(i));
        }
        Assert.assertTrue(countMap.containsNode(100));
        Assert.assertTrue(countMap.containsNode(101));
        Assert.assertFalse(countMap.containsNode(10983));
        Assert.assertFalse(countMap.containsNode(-1));

    }

    /**
     * Method: nodeSet()
     */
    @Test
    public void testNodeSet() throws Exception {
        HashSet<Integer> stdSet = new HashSet<>();
        Collections.addAll(stdSet, new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 100, 101});
        Iterator<Integer> it = countMap.nodeSet();
        HashSet<Integer> set0 = new HashSet<>();
        while (it.hasNext()) {
            set0.add(it.next());
        }
        Assert.assertEquals(set0, stdSet);

        stdSet.clear();
        set0.clear();
        Collections.addAll(stdSet, new Integer[] {1, 3, 4, 5, 6, 7, 8, 9, 10, 13, 14, 100, 101});
        countMap.removeOnePath(new MyPath(new int[] {2, 3, 7, 10, 12, 11}), 2);
        it = countMap.nodeSet();
        while (it.hasNext()) {
            set0.add(it.next());
        }
        Assert.assertEquals(stdSet, set0);
    }

    @Test
    public void testGetPathIdOnNode() {
        countMap.removeOnePath(new MyPath(new int[] {10, 10}), 6);
        Assert.assertTrue(iteratorCheck(countMap.getPathIdOnNode(10),new int[] {2,5}));
        countMap.addOnePath(new MyPath(new int[] {99,100,101,102}),100);
        Assert.assertTrue(countMap.containsNode(102));
        countMap.removeOnePath(new MyPath(new int[] {100,101}),7);
        Assert.assertTrue(iteratorCheck(countMap.getPathIdOnNode(100),new int[] {100}));
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

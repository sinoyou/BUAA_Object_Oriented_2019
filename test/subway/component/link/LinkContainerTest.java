// package test.subway.component;
package subway.component.link;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import subway.component.MyPath;
import subway.component.link.LinkContainer;

import java.util.*;

/** 
* LinkContainer Tester.
*
* @version 1.0 
*/ 
public class LinkContainerTest {
    LinkContainer container = new LinkContainer();

@Before
public void before() throws Exception {
    container.addOnePath(new MyPath(new int[] {1,3,4,6}),1);
    container.addOnePath(new MyPath(new int[] {2,3,7,10,12,11}),2);
    container.addOnePath(new MyPath(new int[] {14,7,4,5}),3);
    container.addOnePath(new MyPath(new int[] {8,7,13,9,8}),4);
    container.addOnePath(new MyPath(new int[] {10,10,10,10}),5);
    container.addOnePath(new MyPath(new int[] {10,10}),6);
    container.addOnePath(new MyPath(new int[] {100,101}),7);
} 

@After
public void after() throws Exception {

} 

/** 
* 
* Method: addOnePath(Path path) 
* 
*/ 
@Test
public void testAddOnePath() throws Exception {

} 

/** 
* 
* Method: removeOnePath(Path path) 
* 
*/ 
@Test
public void testRemoveOnePath() throws Exception {

} 

/** 
* 
* Method: getConnectNodes(int node) 
* 
*/ 
@Test
public void testGetConnectNodes() throws Exception {
    HashMap<Integer,Integer[]> list = new HashMap<>();
    list.put(7,new Integer[] {3, 8, 14, 10, 13, 4});
    list.put(10,new Integer[] {7, 10, 12});
    list.put(8,new Integer[] {7, 9}); // 请仔细参见边的定义，环形无需特殊考虑
    list.put(6,new Integer[] {4});
    list.put(100,new Integer[] {101});
    list.put(101,new Integer[] {100});

    Set<Integer> set = list.keySet();
    for(Integer i:set){
        Iterator<Integer> it = container.getConnectNodes(i);
        HashSet<Integer> testSet = new HashSet();
        while(it.hasNext()){
            testSet.add(it.next());
        }
        HashSet<Integer> stdSet = new HashSet<>();
        Collections.addAll(stdSet, list.get(i));
        Assert.assertEquals(stdSet,testSet);
    }

    container.removeOnePath(new MyPath(new int[] {1,3,4,6}),1);
    Assert.assertTrue(iteratorCheck(container.getConnectNodes(3),new int[] {2,7}));
    container.removeOnePath(new MyPath(new int[] {10,10,10,10}),5);
    Assert.assertTrue(iteratorCheck(container.getConnectNodes(10),new int[] {7,10,12}));
    container.removeOnePath(new MyPath(new int[] {10,10}),6);
    Assert.assertTrue(iteratorCheck(container.getConnectNodes(10),new int[] {12,7}));
}


/** 
* 
* Method: containsEdge(int from, int to) 
* 
*/ 
@Test
public void testContainsEdge() throws Exception {
    // special occasion
    Assert.assertFalse(container.containsEdge(8,8));
    Assert.assertTrue(container.containsEdge(10,10));
    container.addOnePath(new MyPath(new int[] {8,8}),8);
    Assert.assertTrue(container.containsEdge(8,8));
    container.removeOnePath(new MyPath(new int[] {8,8}),8);
    // medium type
    Assert.assertTrue(container.containsEdge(7,10));
    Assert.assertTrue(container.containsEdge(9,13));
    Assert.assertTrue(container.containsEdge(4,3));
    Assert.assertFalse(container.containsEdge(10,11));
    Assert.assertFalse(container.containsEdge(4,10));
    // head & tail
    Assert.assertTrue(container.containsEdge(1,3));
    Assert.assertTrue(container.containsEdge(3,1));
    Assert.assertTrue(container.containsEdge(9,8));
    Assert.assertTrue(container.containsEdge(4,6));
    Assert.assertFalse(container.containsEdge(101,5));
    // protect programming
    Assert.assertFalse(container.containsEdge(101,99));
    Assert.assertFalse(container.containsEdge(99,101));
} 

/** 
* 
* Method: containsKey(int node) 
* 
*/ 
@Test
public void testContainsKey() throws Exception {
    Assert.assertFalse(container.containsKey(102));
    Assert.assertTrue(container.containsKey(10));
    container.removeOnePath(new MyPath(new int[] {10,10}),6);
    Assert.assertTrue(container.containsKey(10));
    container.removeOnePath(new MyPath(new int[] {1,3,4,6}),1);
    Assert.assertFalse(container.containsKey(6));
    container.addOnePath(new MyPath(new int[] {102,1}),8);
    Assert.assertTrue(container.containsKey(102));
} 


@Test
public void testHasEdgeOnPath(){
    Assert.assertTrue(container.hasEdgeOnPath(4,6,1));
    Assert.assertFalse(container.hasEdgeOnPath(4,6,2));
    Assert.assertTrue(container.hasEdgeOnPath(10,10,5));
    Assert.assertTrue(container.hasEdgeOnPath(10,10,6));
    container.removeOnePath(new MyPath(new int[] {10,10}),6);
    Assert.assertFalse(container.hasEdgeOnPath(10,10,6));
    container.removeOnePath(new MyPath(new int[] {10,10,10,10}),5);
    Assert.assertFalse(container.hasEdgeOnPath(10,10,5));
}


/** 
* 
* Method: addOneEdge(int from, int to) 
* 
*/ 
@Test
public void testAddOneEdge() throws Exception {
//TODO: Test goes here... 
/* 
try { 
   Method method = LinkContainer.getClass().getMethod("addOneEdge", int.class, int.class);
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

/** 
* 
* Method: removeOneEdge(int from, int to) 
* 
*/ 
@Test
public void testRemoveOneEdge() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = LinkContainer.getClass().getMethod("removeOneEdge", int.class, int.class);
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

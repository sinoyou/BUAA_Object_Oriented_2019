// package test.subway.component.link; 
package subway.component.link; 

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.HashSet;
import java.util.Iterator;

/** 
* Link Tester.
* @version 1.0 
*/ 
public class LinkTest { 

    Link link;
    // <a,b> link -> pathId = 1 2 3 1
@Before
public void before() throws Exception {
    link = new Link();
    link.addPathId(1);
    link.addPathId(2);
    link.addPathId(3);
    link.addPathId(1);
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: addPathId(int pathId) 
* 
*/ 
@Test
public void testAddPathId() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: subPathId(int pathId) 
* 
*/ 
@Test
public void testSubPathId() throws Exception {
    link.subPathId(1);
    link.subPathId(2);
    assertTrue(link.containEdgeOnPath(1));
    assertFalse(link.containEdgeOnPath(2));
    link.subPathId(3);
    assertFalse(link.containEdgeOnPath(3));
    link.subPathId(1);
    assertFalse(link.containEdgeOnPath(1));
    assertTrue(link.isEmpty());
}

/** 
* 
* Method: isEmpty() 
* 
*/ 
@Test
public void testIsEmpty() throws Exception {
    Link link = new Link();
    assertTrue(link.isEmpty());
} 

/** 
* 
* Method: getPathIdOfLink() 
* 
*/ 
@Test
public void testGetPathIdOfLink() throws Exception {
    assertTrue(iteratorCheck(link.getPathIdOfLink(),new int[] {1,2,3}));
    link.subPathId(3);
    assertTrue(iteratorCheck(link.getPathIdOfLink(),new int[] {1,2}));
}

/** 
* 
* Method: containEdgeOnPath(int pathId) 
* 
*/ 
@Test
public void testContainEdgeOnPath() throws Exception { 
//TODO: Test goes here... 
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

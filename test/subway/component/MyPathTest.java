package subway.component;

import com.oocourse.specs3.models.Path;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.Iterator;
import java.util.zip.Adler32;

/** 
* MyPath Tester. 
*
* @version 1.0 
*/ 
public class MyPathTest {
    int[] l0 = {-1, 0, 1, 2, 3, 4, 1};
    int[] l1 = {129837};
    int[] l2 = {1, 1, 1, 1, 1, 1, 1, 1};
    int[] l3 = {0};
    Path p0 = new MyPath(l0);
    Path p1 = new MyPath(l1);
    Path p2 = new MyPath(l2);
    Path p3 = new MyPath(l3);
@Before
public void before() throws Exception {
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: size() 
* 
*/ 
@Test
public void testSize() throws Exception { 
//TODO: Test goes here...
    Assert.assertEquals(p0.size(), l0.length);
    Assert.assertEquals(p1.size(), l1.length);
    Assert.assertEquals(p2.size(), l2.length);
    Assert.assertEquals(p3.size(), l3.length);
    // invalid case
    int[] x = {};
    Path xx = new MyPath(x);
    Assert.assertEquals(xx.size(), 0);
} 

/** 
* 
* Method: getNode(int index) 
* 
*/ 
@Test
public void testGetNode() throws Exception {
    Assert.assertEquals(p0.getNode(0),l0[0]);
    Assert.assertEquals(p0.getNode(2),l0[2]);
    Assert.assertEquals(p0.getNode(6),l0[6]);
    Assert.assertEquals(p3.getNode(0),l3[0]);
} 

/** 
* 
* Method: containsNode(int node) 
* 
*/ 
@Test
public void testContainsNode() throws Exception {
    Assert.assertTrue(p0.containsNode(4));
    Assert.assertTrue(p0.containsNode(1));
    Assert.assertFalse(p0.containsNode(5));
    Assert.assertTrue(p1.containsNode(129837));
    Assert.assertFalse(p1.containsNode(129836));
    Assert.assertTrue(p2.containsNode(1));
    Assert.assertFalse(p2.containsNode(3));
    Assert.assertTrue(p3.containsNode(0));
} 

/** 
* 
* Method: getDistinctNodeCount() 
* 
*/ 
@Test
public void testGetDistinctNodeCount() throws Exception {
    Assert.assertEquals(p0.getDistinctNodeCount(),6);
    Assert.assertEquals(p0.getDistinctNodeCount(),6);
    Assert.assertEquals(p0.getDistinctNodeCount(),6);
    Assert.assertEquals(p0.getDistinctNodeCount(),6);
    Assert.assertEquals(p1.getDistinctNodeCount(),1);
    Assert.assertEquals(p2.getDistinctNodeCount(),1);
    Assert.assertEquals(p3.getDistinctNodeCount(),1);
} 

/** 
* 
* Method: equals(Object obj) 
* 
*/ 
@Test
public void testEquals() throws Exception {
    int[] x0 = {0};
    int[] x1 = {1,1,1,1,1,1,1};
    int[] x2 = {1,1,1,1,1,1,1,1,1};
    int[] x3 = {1,1,1,1,1,1,1,1};
    Path px0 = new MyPath(x0);
    Path px1 = new MyPath(x1);
    Path px2 = new MyPath(x2);
    Path px3 = new MyPath(x3);
    Assert.assertTrue(px0.equals(p3));
    Assert.assertTrue(p3.equals(px0));
    Assert.assertFalse(px2.equals(p2));
    Assert.assertFalse(p2.equals(px2));
    Assert.assertFalse(px1.equals(p2));
    Assert.assertFalse(p2.equals(px1));
    Assert.assertTrue(px3.equals(p2));
    Assert.assertTrue(p2.equals(px3));
    Assert.assertNotSame(p2,px3);
    // special same hash code
    Path px4 = new MyPath(new int[] {5,0});
    Path px5 = new MyPath(new int[] {4,31});
    Assert.assertFalse(px4.equals(px5));
    Assert.assertFalse(px5.equals(px4));
} 

/** 
* 
* Method: hashCode() 
* 
*/ 
@Test
public void testHashCode() throws Exception {
    Assert.assertNotEquals(p0.hashCode(),p1.hashCode());
    Assert.assertNotEquals(p1.hashCode(),p2.hashCode());
    Assert.assertNotEquals(p2.hashCode(),p3.hashCode());
    Assert.assertNotEquals(p3.hashCode(), p0.hashCode());
    Assert.assertEquals(p0.hashCode(),p0.hashCode());
    Assert.assertEquals(p1.hashCode(),p1.hashCode());
    Assert.assertEquals(p2.hashCode(),p2.hashCode());
    Assert.assertEquals(p3.hashCode(),p3.hashCode());
} 

/** 
* 
* Method: isValid() 
* 
*/ 
@Test
public void testIsValid() throws Exception {
    Assert.assertTrue(p0.isValid());
    Assert.assertFalse(p1.isValid());
    Assert.assertTrue(p2.isValid());
    Assert.assertFalse(p3.isValid());
    int[] x = {1198721, -128371};
    Path p = new MyPath(x);
    Assert.assertTrue(p.isValid());
} 

/** 
* 
* Method: iterator() 
* 
*/ 
@Test
public void testIterator() throws Exception {
    Iterator it1 = p1.iterator();
    Assert.assertTrue(it1.hasNext());
    it1.next();
    Assert.assertFalse(it1.hasNext());

    int[] x ={};
    Path px = new MyPath(x);
    Iterator itx = px.iterator();
    Assert.assertFalse(itx.hasNext());
} 

/** 
* 
* Method: compareTo(Path o) 
* 
*/ 
@Test
public void testCompareTo() throws Exception {
    int[] x0 = {1,2,3,4};
    int[] x1 = {1,2,3};
    int[] x2 = {1,2,3,4,5};
    int[] x3 = {0,1,2,3,4,5,6};
    int[] x4 = {5};
    Path px0 = new MyPath(x0);
    Path px1 = new MyPath(x1);
    Path px2=  new MyPath(x2);
    Path px3 = new MyPath(x3);
    Path px4 = new MyPath(x4);

    Assert.assertEquals(px0.compareTo(px0),0);
    Assert.assertEquals(px4.compareTo(px4),0);
    Assert.assertEquals(px0.compareTo(px1),1);
    Assert.assertEquals(px1.compareTo(px0),-1);
    Assert.assertEquals(px0.compareTo(px2),-1);
    Assert.assertEquals(px2.compareTo(px0),1);
    Assert.assertEquals(px0.compareTo(px3),1);
    Assert.assertEquals(px3.compareTo(px0),-1);
    Assert.assertEquals(px0.compareTo(px4),-1);
    Assert.assertEquals(px4.compareTo(px0),1);
    // Assert.assertEquals(px0.compareTo(px0),0);
}

/**
*
* Method: getHead()
*
*/
@Test
public void testGetHead() throws Exception {
    Path px0 = new MyPath(new int[] {-19873,1});
    Path px1 = new MyPath(new int[] {1,2,3,4,5,6,8,9});
    Path px2 = new MyPath(new int[] {-1});
    Path px3 = new MyPath(new int[] {});
    Assert.assertEquals(-19873,((MyPath) px0).getHead());
    Assert.assertEquals(1,((MyPath) px1).getHead());
    Assert.assertEquals(-1,((MyPath) px2).getHead());
    boolean flag = false;
    try {
        ((MyPath) px3).getHead();
    }catch (Exception e){
        flag = true;
    }
    Assert.assertTrue(flag);
}

/**
*
* Method: getTail()
*
*/
@Test
public void testGetTail() throws Exception {
    Path px0 = new MyPath(new int[] {-19873,1});
    Path px1 = new MyPath(new int[] {1,2,3,4,5,6,8,9});
    Path px2 = new MyPath(new int[] {-1});
    Path px3 = new MyPath(new int[] {});
    Assert.assertEquals(1,((MyPath) px0).getTail());
    Assert.assertEquals(9,((MyPath) px1).getTail());
    Assert.assertEquals(-1,((MyPath) px2).getTail());
    boolean flag = false;
    try {
        ((MyPath) px3).getTail();
    }catch (Exception e){
        flag = true;
    }
    Assert.assertTrue(flag);
}

@Test
    public void testGetUnpleasantValue(){
    int[] l0 = {-1, 0, 1, 2, 3, 4, 1};
    Assert.assertEquals(256,p0.getUnpleasantValue(-1));
    Assert.assertEquals(1,p0.getUnpleasantValue(0));
    Assert.assertEquals(4,p0.getUnpleasantValue(1));
    Assert.assertEquals(16,p0.getUnpleasantValue(2));
    Assert.assertEquals(0,p0.getUnpleasantValue(-2));
}

} 

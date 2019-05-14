// package test.subway.component;
package subway.component;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* DoubleDirPathMap Tester. 
*
* @version 1.0 
*/ 
public class DoubleDirPathMapTest {
    DoubleDirPathMap m0 = new DoubleDirPathMap();

@Before
public void before() throws Exception {
    m0.put(1, new MyPath(new int[] {1,2,3}));
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: put(Integer i, Path p) 
* 
*/ 
@Test
public void testPut() throws Exception {
    m0.put(2,new MyPath(new int[] {2,3,4}));
    Assert.assertEquals(2, m0.size());
    Assert.assertEquals(-1,m0.put(3,null));
} 

/** 
* 
* Method: remove(Integer i, Path p) 
* 
*/ 
@Test
public void testRemove() throws Exception {
    Assert.assertEquals(-1, m0.remove(null,new MyPath(new int[] {1,2})));
    Assert.assertEquals(-1, m0.remove(1,null));
    m0.put(1, new MyPath(new int[] {1,2,3}));
    Assert.assertEquals(-1, m0.remove(1,new MyPath(new int[] {1,2})));
    Assert.assertTrue(m0.containsPath(new MyPath(new int[] {1,2,3})));
} 

/** 
* 
* Method: size() 
* 
*/ 
@Test
public void testSize() throws Exception {
    Assert.assertEquals(1,m0.size());
} 

/** 
* 
* Method: containsId(int id) 
* 
*/ 
@Test
public void testContainsId() throws Exception {
    m0.put(100, new MyPath(new int[] {1,2,3}));
    Assert.assertTrue(m0.containsId(100));
    Assert.assertFalse(m0.containsId(101));
} 

/** 
* 
* Method: containsPath(Path path) 
* 
*/ 
@Test
public void testContainsPath() throws Exception {
    Assert.assertFalse(m0.containsPath(null));
    Assert.assertTrue(m0.containsPath(new MyPath(new int[] {1,2,3})));
    Assert.assertFalse(m0.containsPath(new MyPath(new int[] {1,2,3,4})));
    m0.put(4,new MyPath(new int[] {1,2,3,4}));
    Assert.assertTrue(m0.containsPath(new MyPath(new int[] {1,2,3,4})));
    Assert.assertFalse(m0.containsPath(new MyPath(new int[] {1,2})));
} 

/** 
* 
* Method: getPathById(int id) 
* 
*/ 
@Test
public void testGetPathById() throws Exception {
    Assert.assertEquals(new MyPath(new int[] {1,2,3}), m0.getPathById(1));
} 

/** 
* 
* Method: getIdByPath(Path path) 
* 
*/ 
@Test
public void testGetIdByPath() throws Exception {
    m0.put(5,new MyPath(new int[] {2,3,4,45}));
    Assert.assertEquals(1,m0.getIdByPath(new MyPath(new int[] {1,2,3})));
    Assert.assertEquals(5,m0.getIdByPath(new MyPath(new int[] {2,3,4,45})));
} 


} 

// package test.subway;
package subway;

import com.oocourse.specs3.models.Path;
import com.oocourse.specs3.models.PathIdNotFoundException;
import com.oocourse.specs3.models.PathNotFoundException;
import subway.component.MyPath;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After; 

/** 
* MyGraph Tester.
* @version 1.0 
*/ 
public class MyGraphTest {
    MyRailwaySystem graph = new MyRailwaySystem();

    public MyGraphTest() throws Exception {
    }

    @Before
public void before() throws Exception {
    graph.addPath(new MyPath(new int[] {1,2,3,4,5}));
    graph.addPath(new MyPath(new int[] {1,2,3,4}));
    graph.addPath(new MyPath(new int[] {1,2,3,4,5,6}));
    graph.addPath(new MyPath(new int[] {7,9}));
    graph.addPath(new MyPath(new int[] {0}));
    graph.addPath(new MyPath(new int[] {1,1,1,1,1,1}));
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
    MyRailwaySystem pcx = new MyRailwaySystem();
    Assert.assertEquals(0, pcx.size());
    pcx.addPath(new MyPath(new int[] {1,2,3}));
    Assert.assertEquals(1, pcx.size());
    pcx.addPath(new MyPath(new int[] {0,1}));
    Assert.assertEquals(2, pcx.size());
    pcx.removePath(new MyPath(new int[] {1,2,3}));
    Assert.assertEquals(pcx.size(), 1);
    pcx.removePath(new MyPath(new int[] {0,1}));
    Assert.assertEquals(0, pcx.size());
} 

/** 
* 
* Method: containsPath(Path path) 
* 
*/ 
@Test
public void testContainsPath() throws Exception {
    Assert.assertFalse(graph.containsPath(new MyPath(new int[] {0})));
    Assert.assertTrue(graph.containsPath(new MyPath(new int[] {1,1,1,1,1,1})));
    Assert.assertTrue(graph.containsPath(new MyPath(new int[] {1,2,3,4,5,6})));
    Assert.assertTrue(graph.containsPath(new MyPath(new int[] {7,9})));
    Assert.assertFalse(graph.containsPath(new MyPath(new int[] {2})));
    Assert.assertFalse(graph.containsPath(new MyPath(new int[] {1,1,1,1,1})));
    Assert.assertFalse(graph.containsPath(new MyPath(new int[] {1,1,1,1,1,1,1})));
} 

/** 
* 
* Method: containsPathId(int pathId) 
* 
*/ 
@Test
public void testContainsPathId() throws Exception {
    MyRailwaySystem pcx = new MyRailwaySystem();
    Assert.assertFalse(pcx.containsPathId(10));
    pcx.addPath(new MyPath(new int[] {1,2,3}));
    Assert.assertTrue(pcx.containsPathId(1));
    pcx.addPath(new MyPath(new int[] {0,1}));
    Assert.assertTrue(pcx.containsPathId(2));
    pcx.removePath(new MyPath(new int[] {1,2,3}));
    Assert.assertFalse(pcx.containsPathId(1));
    Assert.assertTrue(pcx.containsPathId(2));
    pcx.removePath(new MyPath(new int[] {0,1}));
    Assert.assertFalse(pcx.containsPathId(2));
} 

/** 
* 
* Method: getPathById(int pathId) 
* 
*/ 
@Test
public void testGetPathById() throws Exception {
    Assert.assertEquals(graph.getPathById(1), new MyPath(new int[] {1,2,3,4,5}));
    Assert.assertEquals(graph.getPathById(2), new MyPath(new int[] {1,2,3,4}));
    Assert.assertEquals(graph.getPathById(5), new MyPath(new int[] {1,1,1,1,1,1}));
    int cnt = 0;
    try{
        graph.getPathById(100);
    }catch (Exception e){
        cnt++;
        Assert.assertTrue(e instanceof PathIdNotFoundException);
    }
    MyRailwaySystem cpx = new MyRailwaySystem();
    cpx.addPath(new MyPath(new int[] {1,1,3}));
    Assert.assertEquals(cpx.getPathById(1),new MyPath(new int[] {1,1,3}));
    cpx.removePathById(1);
    try {
        cpx.getPathById(1);
    }catch (Exception e){
        cnt++;
        Assert.assertTrue(e instanceof PathIdNotFoundException);
    }
    Assert.assertEquals(2,cnt);
} 

/** 
* 
* Method: getPathId(Path path) 
* 
*/ 
@Test
public void testGetPathId() throws Exception {
    Assert.assertEquals(1,graph.getPathId(new MyPath(new int[] {1,2,3,4,5})));
    boolean flag = false;
    try {
        graph.getPathId(null);
    }catch (Exception e){
        flag = e instanceof PathNotFoundException;
    }
    Assert.assertTrue(flag);

    flag = false;
    try {
        graph.getPathId(new MyPath(new int[] {0}));
    }catch (Exception e){
        flag = e instanceof PathNotFoundException;
    }
    Assert.assertTrue(flag);

    flag = false;
    try {
        graph.getPathId(new MyPath(new int[] {8764,19871,1032874}));
    }catch (Exception e){
        flag = e instanceof PathNotFoundException;
    }
    Assert.assertTrue(flag);
} 

/** 
* 
* Method: getDistinctNodeCount() 
* 
*/ 
@Test
public void testGetDistinctNodeCount() throws Exception {

    Assert.assertEquals(8, graph.getDistinctNodeCount());
    graph.removePathById(4);
    Assert.assertEquals(6, graph.getDistinctNodeCount());
} 

/** 
* 
* Method: containsNode(int i) 
* 
*/ 
@Test
public void testContainsNode() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: addPath(Path path) 
* 
*/ 
@Test
public void testAddPath() throws Exception {
    MyRailwaySystem cpx = new MyRailwaySystem();
    cpx.addPath(new MyPath(new int[] {1,2,3,4}));
    cpx.addPath(new MyPath(new int[] {1}));
    cpx.addPath(new MyPath(new int[] {-1987192,1098123,132492891}));
    cpx.addPath(new MyPath(new int[] {1,1,1,1,1,1,1,1}));
    Assert.assertEquals(3,cpx.size());
    Assert.assertTrue(cpx.containsPath(new MyPath(new int[] {1,2,3,4})));
    Assert.assertFalse(cpx.containsPath(new MyPath(new int[] {1})));
    Assert.assertTrue(cpx.containsPath(new MyPath(new int[] {-1987192,1098123,132492891})));
    Assert.assertTrue(cpx.containsPath(new MyPath(new int[] {1,1,1,1,1,1,1,1})));
    Assert.assertEquals(1,cpx.addPath(new MyPath(new int[] {1,2,3,4})));
} 

/** 
* 
* Method: removePath(Path path) 
* 
*/ 
@Test
public void testRemovePath() throws Exception {
    MyRailwaySystem cpx = new MyRailwaySystem();
    cpx.addPath(new MyPath(new int[] {1,2,3,4}));
    cpx.addPath(new MyPath(new int[] {1}));
    cpx.addPath(new MyPath(new int[] {-1987192,1098123,132492891}));
    cpx.addPath(new MyPath(new int[] {1,1,1,1,1,1,1,1}));
    Assert.assertEquals(3,cpx.size());
    cpx.removePath(new MyPath(new int[] {-1987192,1098123,132492891}));
    Assert.assertEquals(2,cpx.size());
    cpx.removePath(new MyPath(new int[] {1,2,3,4}));
    cpx.removePath(new MyPath(new int[] {1,1,1,1,1,1,1,1}));
    Assert.assertEquals(0,cpx.size());
    Assert.assertFalse(cpx.containsPath(new MyPath(new int[] {1,2,3,4})));
    Assert.assertFalse(cpx.containsPath(new MyPath(new int[] {1})));
    Assert.assertFalse(cpx.containsPath(new MyPath(new int[] {-1987192,1098123,132492891})));
    Assert.assertFalse(cpx.containsPath(new MyPath(new int[] {1,1,1,1,1,1,1,1})));
    boolean flag = false;
    try{
        cpx.removePath(null);
    }catch (Exception e){
        flag = e instanceof PathNotFoundException;
    }
    Assert.assertTrue(flag);

    flag = false;
    try {
        cpx.removePath(new MyPath(new int[] {1,2,3}));
    }catch (Exception e){
        flag = e instanceof PathNotFoundException;
    }
    Assert.assertTrue(flag);

    flag = false;
    try {
        cpx.removePath(new MyPath(new int[] {1}));
    }catch (Exception e){
        flag = e instanceof PathNotFoundException;
    }
    Assert.assertTrue(flag);
} 

/** 
* 
* Method: removePathById(int pathId) 
* 
*/ 
@Test
public void testRemovePathById() throws Exception {
    MyRailwaySystem cpx = new MyRailwaySystem();
    cpx.addPath(new MyPath(new int[] {1,2,3,4}));
    cpx.addPath(new MyPath(new int[] {2}));
    cpx.addPath(new MyPath(new int[] {-1987192,1098123,132492891}));
    cpx.addPath(new MyPath(new int[] {1,1,1,1,1,1,1,1}));
    Assert.assertEquals(3,cpx.size());
    cpx.removePath(new MyPath(new int[] {-1987192,1098123,132492891}));
    Assert.assertEquals(2,cpx.size());
    cpx.removePathById(1);
    Assert.assertEquals(1,cpx.size());
    Assert.assertFalse(cpx.containsPath(new MyPath(new int[] {1,2,3,4})));
    Assert.assertFalse(cpx.containsPath(new MyPath(new int[] {1})));
    Assert.assertFalse(cpx.containsPath(new MyPath(new int[] {-1987192,1098123,132492891})));
    Assert.assertTrue(cpx.containsPath(new MyPath(new int[] {1,1,1,1,1,1,1,1})));

    boolean flag = false;
    try{
        cpx.removePathById(1);
    }catch (Exception e){
        flag = e instanceof PathIdNotFoundException;
    }
    Assert.assertTrue(flag);
} 

/** 
* 
* Method: containsEdge(int i, int i1) 
* 
*/ 
@Test
public void testContainsEdge() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: isConnected(int i, int i1) 
* 
*/ 
@Test
public void testIsConnected() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: getShortestPathLength(int i, int i1) 
* 
*/ 
@Test
public void testGetShortestPathLength() throws Exception { 
//TODO: Test goes here... 
} 


@Test
public void timeTest() throws Exception{
    MyRailwaySystem cpx = new MyRailwaySystem();
    for(int i=1;i<=100;i++){
        int[] a1 = new int[200];
        for(int j=0;j<200;j++){
            a1[j] = j;
        }
        a1[199] = i;
        MyPath mp = new MyPath(a1);
        cpx.addPath(mp);
    }

    for(int i=1;i<=100;i++){
        // generate a new path
        MyPath mp = (MyPath)cpx.getPathById(i);

        for(int j=1;j<=100;j++){
            MyPath p = (MyPath)cpx.getPathById(j);
            int x = p.compareTo(mp);
        }
    }
}

/** 
* 
* Method: addOnePath(Path path, int pathId) 
* 
*/ 
@Test
public void testAddOnePath() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = MyGraph.getClass().getMethod("addOnePath", Path.class, int.class); 
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
* Method: removeOnePath(Path path, int pathId) 
* 
*/ 
@Test
public void testRemoveOnePath() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = MyGraph.getClass().getMethod("removeOnePath", Path.class, int.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

} 

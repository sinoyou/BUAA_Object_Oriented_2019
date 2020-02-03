// package test.subway.algorithm;
package subway.algorithm;

import com.oocourse.specs3.models.Path;
import subway.component.DoubleDirPathMap;
import subway.component.link.LinkContainer;
import subway.component.MyPath;
import subway.component.node.NodeCountMap;
import subway.tool.VersionMark;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;

/** 
* InfectGraph Tester.
*
* @version 1.0 
*/ 
public class InfectGraphTest {
    LinkContainer container = new LinkContainer();
    NodeCountMap countMap = new NodeCountMap();
    VersionMark versionMark = new VersionMark();
    InfectGraph infectGraph = new InfectGraph(container,countMap,versionMark);
    DoubleDirPathMap doubleDirPathMap = new DoubleDirPathMap();
@Before
public void before() throws Exception {
    ArrayList<int[]> list = new ArrayList<>();
    list.add(new int[] {1,2});
    list.add(new int[] {1,-2,-3,-4,3});
    list.add(new int[] {1,-5,-6,-7,-8,-9,4});
    list.add(new int[] {1,-10,-11,-12,5});
    list.add(new int[] {2,-13,3});
    list.add(new int[] {3,4});
    list.add(new int[] {3,-14,-15,-16,6});
    list.add(new int[] {4,-18,7}); // 4 -17 -18 7
    list.add(new int[] {4,6});
    list.add(new int[] {5,-19,-20,-21,7});
    list.add(new int[] {6,8});
    list.add(new int[] {6,-22,-23,9});
    list.add(new int[] {7,8});
    list.add(new int[] {8,9});

    list.add(new int[] {31,32,34,35});
    list.add(new int[] {33,34,37,38});
    list.add(new int[] {36,37,39,40});
    list.add(new int[] {39,41});
    list.add(new int[] {42,41,44,45});
    list.add(new int[] {43,44,46});
    list.add(new int[] {43,45,47,48});

    int cnt = 0;
    for(int[] p : list){
        cnt++;
        System.out.println(p[p.length - 1]);
        MyPath path = new MyPath(p);
        addOnePath(path,cnt);
    }
} 

@After
public void after() throws Exception { 
} 


/** 
* 
* Method: isNodesConnected(int from, int to) 
* 
*/ 
@Test
public void testIsNodesConnected() throws Exception {
    // basic check
    Assert.assertFalse(infectGraph.isNodesConnected(1,31));
    Assert.assertFalse(infectGraph.isNodesConnected(3,48));
    for(int i=0;i<=100;i++){
        for(int j=0;j<=100;j++){
            if((i>=1 && i<=9 && j>=1 && j<=9) || (i>=31 && i<=48 && j>=31 && j<=48))
                Assert.assertTrue(infectGraph.isNodesConnected(i,j));
            else
                Assert.assertFalse(infectGraph.isNodesConnected(i,j));
        }
    }
    // check after modification
    removeOnePath(new MyPath(new int[] {39,41}),doubleDirPathMap.getIdByPath(new MyPath(new int[] {39,41})));
    Assert.assertFalse(infectGraph.isNodesConnected(43,35));
    Assert.assertTrue(infectGraph.isNodesConnected(33,39));
    Assert.assertTrue(infectGraph.isNodesConnected(48,41));
} 

/** 
* 
* Method: getShortestRoadLength(int from, int to) 
* 
*/
/*
@Test
public void testGetShortestRoadLength() throws Exception {
    Assert.assertEquals(3,(int) infectGraph.getShortestRoadLength(1,3));
    Assert.assertEquals(4,(int) infectGraph.getShortestRoadLength(1,4));
    Assert.assertEquals(5,(int) infectGraph.getShortestRoadLength(1,6));
    Assert.assertEquals(6,(int) infectGraph.getShortestRoadLength(1,8));
    Assert.assertEquals(7,(int) infectGraph.getShortestRoadLength(1,9));
    Path p = new MyPath(new int[] {2,-13,3});
    removeOnePath(p,doubleDirPathMap.getIdByPath(p));
    Assert.assertEquals(8,(int) infectGraph.getShortestRoadLength(1,9));
    p = new MyPath(new int[] {1,-10,-11,-12,5});
    removeOnePath(p,doubleDirPathMap.getIdByPath(p));
    Assert.assertEquals(11,(int) infectGraph.getShortestRoadLength(1,5));
    p = new MyPath(new int[] {6,8});
    removeOnePath(p,doubleDirPathMap.getIdByPath(p));
    Assert.assertEquals(9,(int) infectGraph.getShortestRoadLength(1,9));

    // also
    Assert.assertNull(infectGraph.getShortestRoadLength(1,48));
} 
*/

/** 
* 
* Method: stampCheck() 
* 
*/ 
@Test
public void testStampCheck() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = InfectGraph.getClass().getMethod("stampCheck");
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
* Method: spfa() 
* 
*/ 
@Test
public void testSpfa() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = InfectGraph.getClass().getMethod("spfa");
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
* Method: spfaSingle(int nodeId) 
* 
*/ 
@Test
public void testSpfaSingle() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = InfectGraph.getClass().getMethod("spfaSingle", int.class);
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

private void addOnePath(Path path, int pathId){
    versionMark.versionUpdate();
    container.addOnePath(path,pathId);
    countMap.addOnePath(path, pathId);
    doubleDirPathMap.put(pathId,path);
}

private void removeOnePath(Path path,int pathId){
    versionMark.versionUpdate();
    container.removeOnePath(path,pathId);
    countMap.removeOnePath(path,pathId);
    doubleDirPathMap.put(pathId,path);
}
} 

// package test.subway.algorithm.shortest; 
package subway.algorithm.shortest; 

import static org.junit.Assert.*;

import com.oocourse.specs3.models.Path;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import subway.component.DoubleDirPathMap;
import subway.component.MyPath;
import subway.component.link.LinkContainer;
import subway.component.node.NodeCountMap;
import subway.tool.VersionMark;

import java.util.ArrayList;
import java.util.Iterator;

/** 
* ShortestPathModel Tester.
* @version 1.0 
*/ 
public class ShortestPathModelTest {
    LinkContainer linkContainer;
    NodeCountMap nodeCountMap;
    VersionMark versionMark;
    DoubleDirPathMap doubleDirPathMap;
    ShortestPathModel model;
@Before
public void before() throws Exception {
    linkContainer = new LinkContainer();
    nodeCountMap = new NodeCountMap();
    versionMark = new VersionMark();
    doubleDirPathMap = new DoubleDirPathMap();
    model = new ShortestPath(linkContainer,nodeCountMap,versionMark);

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

    int cnt = 0;
    for(int[] p : list){
        cnt++;
        MyPath path = new MyPath(p);
        addOnePath(path,cnt);
    }
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: getLowestRoadWeight(int from, int to) 
* 
*/ 
@Test
public void testGetLowestRoadWeight() throws Exception {
    assertEquals(3,model.getLowestRoadWeight(1,3));
    assertEquals(4,model.getLowestRoadWeight(1,4));
    assertEquals(5,model.getLowestRoadWeight(1,6));
    assertEquals(6,model.getLowestRoadWeight(1,8));
    assertEquals(7,model.getLowestRoadWeight(1,9));
    assertEquals(2,model.getLowestRoadWeight(6,3));
    Path p = new MyPath(new int[] {2,-13,3});
    removeOnePath(p,doubleDirPathMap.getIdByPath(p));
    assertEquals(8,model.getLowestRoadWeight(1,9));
    p = new MyPath(new int[] {1,-10,-11,-12,5});
    removeOnePath(p,doubleDirPathMap.getIdByPath(p));
    assertEquals(11,model.getLowestRoadWeight(1,5));
    p = new MyPath(new int[] {6,8});
    removeOnePath(p,doubleDirPathMap.getIdByPath(p));
    assertEquals(9,model.getLowestRoadWeight(1,9));
    assertEquals(5,model.getLowestRoadWeight(4,1));

    // also
    /*
    boolean flag = false;
    try {
        model.getLowestRoadWeight(1,48);
    }catch (Exception e){
        flag = true;
    }
    assertTrue(flag);
    */
}

/** 
* 
* Method: getEdgeValue(int actNodeI, int actNodeJ, int pathIdI, int pathIdJ, LinkContainer linkContainer) 
* 
*/ 
@Test
public void testGetEdgeValue() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: nodeResultUpdate(int node) 
* 
*/ 
@Test
public void testNodeResultUpdate() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = ShortestPathModel.getClass().getMethod("nodeResultUpdate", int.class); 
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
* Method: graphUpdate() 
* 
*/ 
@Test
public void testGraphUpdate() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = ShortestPathModel.getClass().getMethod("graphUpdate"); 
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
* Method: singleSrcSpfa(int from, Matrix weightGraph, ConvertMap convertMap, NodeCountMap nodeCountMap) 
* 
*/ 
@Test
public void testSingleSrcSpfa() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = ShortestPathModel.getClass().getMethod("singleSrcSpfa", int.class, Matrix.class, ConvertMap.class, NodeCountMap.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 

private void addOnePath(Path path, int pathId){
    linkContainer.addOnePath(path,pathId);
    nodeCountMap.addOnePath(path,pathId);
    doubleDirPathMap.put(pathId,path);
    versionMark.versionUpdate();
}

private void removeOnePath(Path path,int pathId){
    linkContainer.removeOnePath(path,pathId);
    nodeCountMap.removeOnePath(path,pathId);
    doubleDirPathMap.remove(pathId,path);
    versionMark.versionUpdate();
}

private boolean assertError(Exception expected, Exception actual){
    return expected.getClass().getName().equals(actual.getClass().getName());
}

} 

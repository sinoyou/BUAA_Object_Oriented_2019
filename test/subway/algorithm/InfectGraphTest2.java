// package test.subway.algorithm;
package subway.algorithm;

import com.oocourse.specs3.models.Path;
import org.junit.Assert;
import subway.component.DoubleDirPathMap;
import subway.component.link.LinkContainer;
import subway.component.MyPath;
import subway.component.node.NodeCountMap;
import subway.tool.VersionMark;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;

/**
 * InfectGraph Tester.
 *
 * @version 1.0
 */
public class InfectGraphTest2 {
    LinkContainer container = new LinkContainer();
    NodeCountMap countMap = new NodeCountMap();
    VersionMark versionMark = new VersionMark();
    InfectGraph infectGraph = new InfectGraph(container,countMap,versionMark);
    DoubleDirPathMap doubleDirPathMap = new DoubleDirPathMap();
    @Before
    public void before() throws Exception {
        ArrayList<int[]> list = new ArrayList<>();
        list.add(new int[] {1,2,3,4});
        list.add(new int[] {5,2,6});
        list.add(new int[] {8,3,7});
        list.add(new int[] {10,11,12,10});
        list.add(new int[] {10,10,10});
        list.add(new int[] {6,13});
        list.add(new int[] {8,14});
        list.add(new int[] {13,14,15,16});
        list.add(new int[] {19,15,17,18});
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
     * Method: isNodesConnected(int from, int to)
     *
     */
    @Test
    public void testIsNodesConnected() throws Exception {
        assertTrue(infectGraph.isNodesConnected(1,16));
        assertTrue(infectGraph.isNodesConnected(1,8));
        assertTrue(infectGraph.isNodesConnected(10,10));
        assertTrue(infectGraph.isNodesConnected(6,6));
        assertFalse(infectGraph.isNodesConnected(18,12));
        MyPath p = new MyPath(new int[] {6,13});
        MyPath p1 = new MyPath(new int[] {8,14});
        removeOnePath(p,doubleDirPathMap.getIdByPath(p));
        assertTrue(infectGraph.isNodesConnected(5,16));
        removeOnePath(p1,doubleDirPathMap.getIdByPath(p1));
        assertFalse(infectGraph.isNodesConnected(5,16));

    }

    @Test
    public void testConnectedBlocksCount(){
        assertEquals(2,infectGraph.getConnectedBlocks());
        MyPath p = new MyPath(new int[] {6,13});
        MyPath p1 = new MyPath(new int[] {8,14});
        removeOnePath(p,doubleDirPathMap.getIdByPath(p));
        assertEquals(2,infectGraph.getConnectedBlocks());
        removeOnePath(p1,doubleDirPathMap.getIdByPath(p1));
        assertEquals(3,infectGraph.getConnectedBlocks());
        addOnePath(new MyPath(new int[] {4,12}), 100);
        assertEquals(2,infectGraph.getConnectedBlocks());
        p = new MyPath(new int[] {10,11,12,10});
        p1 = new MyPath(new int[] {10,10,10});
        removeOnePath(p,doubleDirPathMap.getIdByPath(p));
        removeOnePath(p1,doubleDirPathMap.getIdByPath(p1));
        assertEquals(2,infectGraph.getConnectedBlocks());
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

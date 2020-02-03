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
public class LowestTicketPriceTest {
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
        model = new LowestTicketPrice(linkContainer,nodeCountMap,versionMark);

        ArrayList<int[]> list = new ArrayList<>();
        list.add(new int[] {1,2,3,4,5,6,7,8,9,10});

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
        assertEquals(9,model.getLowestRoadWeight(1,10));
        addOnePath(new MyPath(new int[] {2,7}),2);
        assertEquals(9,model.getLowestRoadWeight(1,10));
        addOnePath(new MyPath(new int[] {4,10}),3);
        assertEquals(6,model.getLowestRoadWeight(1,10));
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


    /**
     *
     * Method: getEdgeValue(int actNodeI, int actNodeJ, int pathIdI, int pathIdJ, LinkContainer linkContainer)
     *
     */
    @Test
    public void testGetEdgeValue() throws Exception {
    }
}
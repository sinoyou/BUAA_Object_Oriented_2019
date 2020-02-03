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
public class LeastUnpleasantTest {
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
        model = new LeastUnpleasant(linkContainer,nodeCountMap,versionMark);

        ArrayList<int[]> list = new ArrayList<>();
        list.add(new int[] {1,2,3,4,5,6,7,8,9,10});
        list.add(new int[] {1,2,3,4,5,6,7,8,11,12});
        list.add(new int[] {58,59,18,60,7,61,62,63,28,64,65});
        list.add(new int[] {47,46,45,44,43,42,41,14,40,3,39});
        list.add(new int[] {44,48,49,58,50,51,52,53,19,54,55,8,56,57});
        list.add(new int[] {13,14,15,16,17,18,19,20,21,22,9,11,23,24,25,26,
            27,28,29,30,31,32,33,34,35,36,37,2,38,13});
        list.add(new int[] {66,3,36,67});
        list.add(new int[] {68,69,47});

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
        assertEquals((Integer) 32,model.getEdgeValue(3,3,1,2,linkContainer));
        assertEquals((Integer) 0,model.getEdgeValue(3,3,1,1,linkContainer));
        assertEquals((Integer) 64,model.getEdgeValue(2,3,1,1,linkContainer));
        assertNull(model.getEdgeValue(7,8,1,2,linkContainer));

        addOnePath(new MyPath(new int[] {32,39}),100);
        assertEquals((Integer)256,model.getEdgeValue(32,39,100,100,linkContainer));
        assertNull(model.getEdgeValue(32,39,6,6,linkContainer));
    }
}
package subway;// package test.subway;

import com.oocourse.specs3.models.NodeIdNotFoundException;
import com.oocourse.specs3.models.NodeNotConnectedException;
import com.oocourse.specs3.models.PathIdNotFoundException;
import com.oocourse.specs3.models.PathNotFoundException;
import subway.component.MyPath;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * MyGraph Tester.
 *
 * @version 1.0
 */
public class MyGraphAlgorithmTest {

    MyRailwaySystem graph = new MyRailwaySystem();

    public MyGraphAlgorithmTest() throws Exception {
    }

    @Before
    public void before() throws Exception {
        ArrayList<int[]> list = new ArrayList<>();
        list.add(new int[] {1, 2});
        list.add(new int[] {1, -2, -3, -4, 3});
        list.add(new int[] {1, -5, -6, -7, -8, -9, 4});
        list.add(new int[] {1, -10, -11, -12, 5});
        list.add(new int[] {2, -13, 3});
        list.add(new int[] {3, 4});
        list.add(new int[] {3, -14, -15, -16, 6});
        list.add(new int[] {4, -18, 7}); // 4 -17 -18 7
        list.add(new int[] {4, 6});
        list.add(new int[] {5, -19, -20, -21, 7});
        list.add(new int[] {6, 8});
        list.add(new int[] {6, -22, -23, 9});
        list.add(new int[] {7, 8});
        list.add(new int[] {8, 9});

        list.add(new int[] {31, 32, 34, 35});
        list.add(new int[] {33, 34, 37, 38});
        list.add(new int[] {36, 37, 39, 40});
        list.add(new int[] {39, 41});
        list.add(new int[] {42, 41, 44, 45});
        list.add(new int[] {43, 44, 46});
        list.add(new int[] {43, 45, 47, 48});

        for (int[] p : list) {
            MyPath path = new MyPath(p);
            graph.addPath(path);
        }
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: containsNode(int i)
     */
    @Test
    public void testContainsNode() throws Exception {
        for (int i = 0; i < 100; i++) {
            if((i>=1 && i<=9) || (i>=31 && i<=48)){
                Assert.assertTrue(graph.containsNode(i));
            }else {
                Assert.assertFalse(graph.containsNode(i));
            }
        }
    }

    /**
     * Method: containsEdge(int i, int i1)
     */
    @Test
    public void testContainsEdge() throws Exception {
        Assert.assertTrue(graph.containsEdge(2,1));
        Assert.assertTrue(graph.containsEdge(33,34));
        Assert.assertFalse(graph.containsEdge(46,45));

        graph.addPath(new MyPath(new int[] {46,47,48}));
        Assert.assertTrue(graph.containsEdge(48,47));
        graph.removePath(new MyPath(new int[] {43,45,47,48}));
        Assert.assertFalse(graph.containsEdge(45,47));
        Assert.assertTrue(graph.containsEdge(48,47));
        graph.removePath(new MyPath(new int[] {46,47,48}));
        Assert.assertFalse(graph.containsEdge(48,47));
    }

    /**
     * Method: isConnected(int i, int i1)
     */
    @Test
    public void testIsConnected() throws Exception {
        // basic check
        Assert.assertFalse(graph.isConnected(1,31));
        Assert.assertFalse(graph.isConnected(3,48));
        for(int i=0;i<=100;i++){
            for(int j=0;j<=100;j++){
                if(((i>=1 && i<=9)||(i>=31 && i<=48)) &&
                    ((j>=1 && j<=9) ||(j>=31 && j<=48))){
                    if((i>=1 && i<=9 && j>=1 && j<=9) || (i>=31 && i<=48 && j>=31 && j<=48))
                        Assert.assertTrue(graph.isConnected(i,j));
                    else{
                        Assert.assertFalse(graph.isConnected(j,i));
                    }
                }else {
                    boolean flag = false;
                    try {
                        graph.isConnected(i,j);
                    }catch (Exception e){
                        flag = e instanceof NodeIdNotFoundException;
                    }
                    Assert.assertTrue(flag);
                }
            }
        }
        // check after modification
        graph.removePath(new MyPath(new int[] {39,41}));
        Assert.assertFalse(graph.isConnected(43,35));
        Assert.assertTrue(graph.isConnected(33,39));
        Assert.assertTrue(graph.isConnected(48,41));

    }

    /**
     * Method: getShortestPathLength(int i, int i1)
     */
    @Test
    public void testGetShortestPathLength() throws Exception {
        Assert.assertEquals(3,(int)graph.getShortestPathLength(1,3));
        Assert.assertEquals(4,(int)graph.getShortestPathLength(1,4));
        Assert.assertEquals(5,(int)graph.getShortestPathLength(1,6));
        Assert.assertEquals(6,(int)graph.getShortestPathLength(1,8));
        Assert.assertEquals(7,(int)graph.getShortestPathLength(1,9));
        graph.removePath(new MyPath(new int[] {2,-13,3}));
        Assert.assertEquals(8,(int)graph.getShortestPathLength(1,9));
        graph.removePath(new MyPath(new int[] {1,-10,-11,-12,5}));
        Assert.assertEquals(11,(int)graph.getShortestPathLength(1,5));
        graph.removePath(new MyPath(new int[] {6,8}));
        Assert.assertEquals(9,(int)graph.getShortestPathLength(1,9));
        Assert.assertEquals(0, graph.getShortestPathLength(5,5));
        // exception check
        int cnt = 0;
        try{
            graph.getShortestPathLength(109823,3);
        }catch (Exception e){
            cnt++;
            Assert.assertTrue(e instanceof NodeIdNotFoundException);
        }
        try {
            graph.getShortestPathLength(3,1038123);
        }catch (Exception e){
            cnt++;
            Assert.assertTrue(e instanceof NodeIdNotFoundException);
        }
        try {
            graph.getShortestPathLength(1038123,1038123);
        }catch (Exception e){
            cnt++;
            Assert.assertTrue(e instanceof NodeIdNotFoundException);
        }
        try {
            graph.getShortestPathLength(9,48);
        }catch (Exception e){
            cnt++;
            Assert.assertTrue(e instanceof NodeNotConnectedException);
        }
        Assert.assertEquals(4,cnt);
    }


} 

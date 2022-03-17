package org.openstreetmap.josm.plugins.fastdriveway.utils;

import org.junit.Rule;
import org.junit.Test;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.Way;
import org.openstreetmap.josm.testutils.JOSMTestRules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openstreetmap.josm.plugins.fastdriveway.utils.FastDrivewayPerformUtils.getBarrierNodesFromWay;

public class BarrierNodesFromWayTest {

    @Rule
    public JOSMTestRules rules = new JOSMTestRules().main();

    @Test
    public void testWayWithoutIntersectionNodes(){
        DataSet ds = new DataSet();

        Node wayNode1 = new Node(new LatLon(0, 0));
        Node wayNode2 = new Node(new LatLon(0, 1));
        Node wayNode3 = new Node(new LatLon(0, 2));

        Way testWay = new Way();
        testWay.addNode(wayNode1);
        testWay.addNode(wayNode2);
        testWay.addNode(wayNode3);

        ds.addPrimitive(wayNode1);
        ds.addPrimitive(wayNode2);
        ds.addPrimitive(wayNode3);
        ds.addPrimitive(testWay);

        assertTrue(getBarrierNodesFromWay(testWay).isEmpty());
    }

    @Test
    public void testWayWhichIntersectOneWayWithoutBarrierTags(){
        DataSet ds = new DataSet();

        Node wayNode1 = new Node(new LatLon(0, 0));
        Node intersectionNode2 = new Node(new LatLon(0, 1));
        Node wayNode3 = new Node(new LatLon(0, 2));

        Way testWay = new Way();
        testWay.addNode(wayNode1);
        testWay.addNode(intersectionNode2);
        testWay.addNode(wayNode3);

        Node wayNode4 = new Node(new LatLon(-1, 1));
        Node wayNode6 = new Node(new LatLon(1, 1));

        Way intersectionWay = new Way();
        intersectionWay.addNode(wayNode4);
        intersectionWay.addNode(intersectionNode2);
        intersectionWay.addNode(wayNode6);
        intersectionWay.put("test", "test");

        ds.addPrimitive(wayNode1);
        ds.addPrimitive(intersectionNode2);
        ds.addPrimitive(wayNode3);
        ds.addPrimitive(wayNode4);
        ds.addPrimitive(wayNode6);

        ds.addPrimitive(testWay);
        ds.addPrimitive(intersectionWay);

        assertTrue(getBarrierNodesFromWay(testWay).isEmpty());
    }

    @Test
    public void testWayOfThreeNodesWhichIntersectOneWayWithBarrierTags(){
        DataSet ds = new DataSet();

        Node wayNode1 = new Node(new LatLon(0, 0));
        Node intersectionNode2 = new Node(new LatLon(0, 1));
        Node wayNode3 = new Node(new LatLon(0, 2));

        Way testWay = new Way();
        testWay.addNode(wayNode1);
        testWay.addNode(intersectionNode2);
        testWay.addNode(wayNode3);

        Node wayNode4 = new Node(new LatLon(-1, 1));
        Node wayNode6 = new Node(new LatLon(1, 1));

        Way intersectionWay = new Way();
        intersectionWay.addNode(wayNode4);
        intersectionWay.addNode(intersectionNode2);
        intersectionWay.addNode(wayNode6);
        intersectionWay.put("barrier", "fence");

        ds.addPrimitive(wayNode1);
        ds.addPrimitive(intersectionNode2);
        ds.addPrimitive(wayNode3);
        ds.addPrimitive(wayNode4);
        ds.addPrimitive(wayNode6);

        ds.addPrimitive(testWay);
        ds.addPrimitive(intersectionWay);

        assertEquals(getBarrierNodesFromWay(testWay).size(), 1);
        assertEquals(getBarrierNodesFromWay(testWay).get(0), intersectionNode2);
    }

    @Test
    public void testWayOfThreeNodesWhichIntersectOneWayWithBarrierTagsAndIntersectionNodeIsFirstNode(){
        DataSet ds = new DataSet();

        Node intersectionNode1 = new Node(new LatLon(0, 0));
        Node wayNode2 = new Node(new LatLon(0, 1));
        Node wayNode3 = new Node(new LatLon(0, 2));

        Way testWay = new Way();
        testWay.addNode(intersectionNode1);
        testWay.addNode(wayNode2);
        testWay.addNode(wayNode3);

        Node wayNode4 = new Node(new LatLon(-1, 0));
        Node wayNode6 = new Node(new LatLon(1, 0));

        Way intersectionWay = new Way();
        intersectionWay.addNode(wayNode4);
        intersectionWay.addNode(intersectionNode1);
        intersectionWay.addNode(wayNode6);
        intersectionWay.put("barrier", "fence");

        ds.addPrimitive(intersectionNode1);
        ds.addPrimitive(wayNode2);
        ds.addPrimitive(wayNode3);
        ds.addPrimitive(wayNode4);
        ds.addPrimitive(wayNode6);

        ds.addPrimitive(testWay);
        ds.addPrimitive(intersectionWay);

        assertTrue(getBarrierNodesFromWay(testWay).isEmpty());
    }

    @Test
    public void testWayOfThreeNodesWhichIntersectOneWayWithBarrierTagsAndIntersectionNodeIsLastNode(){
        DataSet ds = new DataSet();

        Node wayNode1 = new Node(new LatLon(0, 0));
        Node wayNode2 = new Node(new LatLon(0, 1));
        Node intersectionNode3 = new Node(new LatLon(0, 2));

        Way testWay = new Way();
        testWay.addNode(wayNode1);
        testWay.addNode(wayNode2);
        testWay.addNode(intersectionNode3);

        Node wayNode4 = new Node(new LatLon(-1, 2));
        Node wayNode6 = new Node(new LatLon(1, 2));

        Way intersectionWay = new Way();
        intersectionWay.addNode(wayNode4);
        intersectionWay.addNode(intersectionNode3);
        intersectionWay.addNode(wayNode6);
        intersectionWay.put("barrier", "fence");

        ds.addPrimitive(wayNode1);
        ds.addPrimitive(wayNode2);
        ds.addPrimitive(intersectionNode3);
        ds.addPrimitive(wayNode4);
        ds.addPrimitive(wayNode6);

        ds.addPrimitive(testWay);
        ds.addPrimitive(intersectionWay);

        assertTrue(getBarrierNodesFromWay(testWay).isEmpty());
    }

    @Test
    public void testWayOfFiveNodesWhichIntersectOneWayWithBarrierTags(){
        DataSet ds = new DataSet();

        Node wayNode1 = new Node(new LatLon(0, 0));
        Node intersectionNode2 = new Node(new LatLon(0, 1));
        Node wayNode3 = new Node(new LatLon(0, 2));
        Node wayNode4 = new Node(new LatLon(0, 3));
        Node wayNode5 = new Node(new LatLon(0, 4));

        Way testWay = new Way();
        testWay.addNode(wayNode1);
        testWay.addNode(intersectionNode2);
        testWay.addNode(wayNode3);
        testWay.addNode(wayNode4);
        testWay.addNode(wayNode5);

        Node wayNode6 = new Node(new LatLon(-1, 1));
        Node wayNode8 = new Node(new LatLon(1, 1));

        Way intersectionWay = new Way();
        intersectionWay.addNode(wayNode6);
        intersectionWay.addNode(intersectionNode2);
        intersectionWay.addNode(wayNode8);
        intersectionWay.put("barrier", "fence");

        ds.addPrimitive(wayNode1);
        ds.addPrimitive(intersectionNode2);
        ds.addPrimitive(wayNode3);
        ds.addPrimitive(wayNode4);
        ds.addPrimitive(wayNode5);
        ds.addPrimitive(wayNode6);
        ds.addPrimitive(wayNode8);

        ds.addPrimitive(testWay);
        ds.addPrimitive(intersectionWay);

        assertEquals(getBarrierNodesFromWay(testWay).size(), 1);
        assertEquals(getBarrierNodesFromWay(testWay).get(0), intersectionNode2);
    }

    @Test
    public void testWayOfFiveNodesWhichIntersectTwoWayWithBarrierTags(){
        DataSet ds = new DataSet();

        Node wayNode1 = new Node(new LatLon(0, 0));
        Node intersectionNode2 = new Node(new LatLon(0, 1));
        Node wayNode3 = new Node(new LatLon(0, 2));
        Node intersectionNode4 = new Node(new LatLon(0, 3));
        Node wayNode5 = new Node(new LatLon(0, 4));

        Way testWay = new Way();
        testWay.addNode(wayNode1);
        testWay.addNode(intersectionNode2);
        testWay.addNode(wayNode3);
        testWay.addNode(intersectionNode4);
        testWay.addNode(wayNode5);

        Node wayNode6 = new Node(new LatLon(-1, 1));
        Node wayNode8 = new Node(new LatLon(1, 1));

        Way intersectionWay = new Way();
        intersectionWay.addNode(wayNode6);
        intersectionWay.addNode(intersectionNode2);
        intersectionWay.addNode(wayNode8);
        intersectionWay.put("barrier", "fence");

        Node wayNode9 = new Node(new LatLon(-1, 3));
        Node wayNode11 = new Node(new LatLon(1, 3));
        Way intersectionWay2 = new Way();
        intersectionWay2.addNode(wayNode9);
        intersectionWay2.addNode(intersectionNode4);
        intersectionWay2.addNode(wayNode11);
        intersectionWay2.put("barrier", "wall");

        ds.addPrimitive(wayNode1);
        ds.addPrimitive(intersectionNode2);
        ds.addPrimitive(wayNode3);
        ds.addPrimitive(intersectionNode4);
        ds.addPrimitive(wayNode5);
        ds.addPrimitive(wayNode6);
        ds.addPrimitive(wayNode8);
        ds.addPrimitive(wayNode9);
        ds.addPrimitive(wayNode11);

        ds.addPrimitive(testWay);
        ds.addPrimitive(intersectionWay);
        ds.addPrimitive(intersectionWay2);

        assertEquals(getBarrierNodesFromWay(testWay).size(), 2);
        assertEquals(getBarrierNodesFromWay(testWay).get(0), intersectionNode2);
        assertEquals(getBarrierNodesFromWay(testWay).get(1), intersectionNode4);
    }
}

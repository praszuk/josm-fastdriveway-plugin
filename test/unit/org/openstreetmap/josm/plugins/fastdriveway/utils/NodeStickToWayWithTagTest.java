package org.openstreetmap.josm.plugins.fastdriveway.utils;

import org.junit.Rule;
import org.junit.Test;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.Way;
import org.openstreetmap.josm.testutils.JOSMTestRules;

import java.util.AbstractMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.openstreetmap.josm.plugins.fastdriveway.utils.FastDrivewayPerformUtils.isNodeStickToWayWithTag;

public class NodeStickToWayWithTagTest {

    @Rule
    public JOSMTestRules rules = new JOSMTestRules().main();

    @Test
    public void testFloatingNodeNotStickingToAnyWay(){
        DataSet ds = new DataSet();
        Node testNode = new Node(new LatLon(0, 0));
        ds.addPrimitive(testNode);

        assertFalse(isNodeStickToWayWithTag(testNode, new AbstractMap.SimpleEntry<>("test", "test")));
    }

    @Test
    public void testNodeSticksToEmptyWay(){
        DataSet ds = new DataSet();
        Node testNode = new Node(new LatLon(0, 0));

        Node wayNode1 = new Node(new LatLon(-1, 0));
        Node wayNode3 = new Node(new LatLon(1, 0));

        Way testWay = new Way();
        testWay.addNode(wayNode1);
        testWay.addNode(testNode);
        testWay.addNode(wayNode3);

        ds.addPrimitive(testNode);
        ds.addPrimitive(wayNode1);
        ds.addPrimitive(wayNode3);
        ds.addPrimitive(testWay);

        assertFalse(isNodeStickToWayWithTag(testNode, new AbstractMap.SimpleEntry<>("test", "test")));
    }

    @Test
    public void testNodeSticksToWayWithCorrectFullTag(){
        DataSet ds = new DataSet();
        Node testNode = new Node(new LatLon(0, 0));

        Node wayNode1 = new Node(new LatLon(-1, 0));
        Node wayNode3 = new Node(new LatLon(1, 0));

        Way testWay = new Way();
        testWay.addNode(wayNode1);
        testWay.addNode(testNode);
        testWay.addNode(wayNode3);
        testWay.put("test","test");

        ds.addPrimitive(testNode);
        ds.addPrimitive(wayNode1);
        ds.addPrimitive(wayNode3);
        ds.addPrimitive(testWay);

        assertTrue(isNodeStickToWayWithTag(testNode, new AbstractMap.SimpleEntry<>("test", "test")));
    }

    @Test
    public void testNodeSticksToWayWithCorrectTagKey(){
        DataSet ds = new DataSet();
        Node testNode = new Node(new LatLon(0, 0));

        Node wayNode1 = new Node(new LatLon(-1, 0));
        Node wayNode3 = new Node(new LatLon(1, 0));

        Way testWay = new Way();
        testWay.addNode(wayNode1);
        testWay.addNode(testNode);
        testWay.addNode(wayNode3);
        testWay.put("test","test");

        ds.addPrimitive(testNode);
        ds.addPrimitive(wayNode1);
        ds.addPrimitive(wayNode3);
        ds.addPrimitive(testWay);

        assertTrue(isNodeStickToWayWithTag(testNode, new AbstractMap.SimpleEntry<>("test", "*")));
        assertTrue(isNodeStickToWayWithTag(testNode, new AbstractMap.SimpleEntry<>("test", "")));
    }

    @Test
    public void testNodeSticksToWayWithCorrectTagKeyButIncorrectValue(){
        DataSet ds = new DataSet();
        Node testNode = new Node(new LatLon(0, 0));

        Node wayNode1 = new Node(new LatLon(-1, 0));
        Node wayNode3 = new Node(new LatLon(1, 0));

        Way testWay = new Way();
        testWay.addNode(wayNode1);
        testWay.addNode(testNode);
        testWay.addNode(wayNode3);
        testWay.put("test","test");

        ds.addPrimitive(testNode);
        ds.addPrimitive(wayNode1);
        ds.addPrimitive(wayNode3);
        ds.addPrimitive(testWay);

        assertFalse(isNodeStickToWayWithTag(testNode, new AbstractMap.SimpleEntry<>("test", "incorrect")));
    }

    @Test
    public void testNodeSticksToWayWithIncorrectTagKeyButCorrectValue(){
        DataSet ds = new DataSet();
        Node testNode = new Node(new LatLon(0, 0));

        Node wayNode1 = new Node(new LatLon(-1, 0));
        Node wayNode3 = new Node(new LatLon(1, 0));

        Way testWay = new Way();
        testWay.addNode(wayNode1);
        testWay.addNode(testNode);
        testWay.addNode(wayNode3);
        testWay.put("test","test");

        ds.addPrimitive(testNode);
        ds.addPrimitive(wayNode1);
        ds.addPrimitive(wayNode3);
        ds.addPrimitive(testWay);

        assertFalse(isNodeStickToWayWithTag(testNode, new AbstractMap.SimpleEntry<>("incorrect", "test")));
    }

    @Test
    public void testNodeSticksToWaysWithWhereOneWayHasCorrectTags(){
        DataSet ds = new DataSet();
        Node testNode = new Node(new LatLon(0, 0));

        Node wayNode1 = new Node(new LatLon(-1, 0));
        Node wayNode3 = new Node(new LatLon(1, 0));

        Node wayNode4 = new Node(new LatLon(0, -1));
        Node wayNode6 = new Node(new LatLon(0, 1));

        Way testWay = new Way();
        testWay.addNode(wayNode1);
        testWay.addNode(testNode);
        testWay.addNode(wayNode3);
        testWay.put("test","test");

        Way testWay2 = new Way();
        testWay2.addNode(wayNode4);
        testWay2.addNode(testNode);
        testWay2.addNode(wayNode6);
        testWay2.put("incorrect","incorrect");

        ds.addPrimitive(testNode);
        ds.addPrimitive(wayNode1);
        ds.addPrimitive(wayNode3);
        ds.addPrimitive(wayNode4);
        ds.addPrimitive(wayNode6);
        ds.addPrimitive(testWay);
        ds.addPrimitive(testWay2);

        assertTrue(isNodeStickToWayWithTag(testNode, new AbstractMap.SimpleEntry<>("test", "test")));
    }
}

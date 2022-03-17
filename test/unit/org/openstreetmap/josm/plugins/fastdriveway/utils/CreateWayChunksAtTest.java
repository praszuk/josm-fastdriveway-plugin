package org.openstreetmap.josm.plugins.fastdriveway.utils;

import org.junit.Test;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.Way;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.openstreetmap.josm.plugins.fastdriveway.utils.FastDrivewayPerformUtils.createWayChunksAt;

public class CreateWayChunksAtTest {

    @Test
    public void testFloatingNodeNotElementOfWay(){
        Node splitNode = new Node();

        Way way = new Way();
        way.addNode(new Node());
        way.addNode(new Node());

        assertTrue(createWayChunksAt(way, splitNode).isEmpty());
    }

    @Test
    public void testNodeElementOfSimpleWayIsFirstNode(){
        Node splitNode = new Node();
        Node node2 = new Node();

        Way way = new Way();
        way.addNode(splitNode);
        way.addNode(node2);

        List<List<Node>> chunks = createWayChunksAt(way, splitNode);

        assertEquals(chunks.size(), 1);
        assertEquals(chunks.get(0), Arrays.asList(splitNode, node2));
    }

    @Test
    public void testNodeElementOfSimpleWayIsLastNode(){
        Node node1 = new Node();
        Node splitNode = new Node();

        Way way = new Way();
        way.addNode(node1);
        way.addNode(splitNode);

        List<List<Node>> chunks = createWayChunksAt(way, splitNode);

        assertEquals(chunks.size(), 1);
        assertEquals(chunks.get(0), Arrays.asList(node1, splitNode));
    }

    @Test
    public void testNodeElementOfSimpleWayInMiddle(){
        Node node1 = new Node();
        Node splitNode = new Node();
        Node node3 = new Node();

        Way way = new Way();
        way.addNode(node1);
        way.addNode(splitNode);
        way.addNode(node3);

        List<List<Node>> chunks = createWayChunksAt(way, splitNode);

        assertEquals(chunks.size(), 2);
        assertEquals(chunks.get(0), Arrays.asList(node1, splitNode));
        assertEquals(chunks.get(1), Arrays.asList(splitNode, node3));
    }

}

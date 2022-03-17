package org.openstreetmap.josm.plugins.fastdriveway.validation;

import org.junit.Test;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.Way;

import static org.junit.Assert.assertFalse;
import static org.openstreetmap.josm.plugins.fastdriveway.validation.FastDrivewayPerformValidator.isWayValid;

public class WayIsValidTest {

    @Test
    public void testEmptyWayWithoutNodesIsIncorrect(){
        assertFalse(isWayValid(new Way()));
    }

    @Test
    public void testEmptyWayWithTagsIsIncorrect(){
        Way way = new Way();
        way.addNode(new Node());
        way.addNode(new Node());
        way.put("highway", "service");

        assertFalse(isWayValid(way));
    }

    @Test
    public void testWayClosedIsIncorrect(){
        Node node1 = new Node();
        Node node2 = new Node();
        Node node3 = new Node();

        Way way = new Way();
        way.addNode(node1);
        way.addNode(node2);
        way.addNode(node3);
        way.addNode(node1);

        assertFalse(isWayValid(new Way()));
    }
    // Rest body of isWayValid function is covered by actions tests
}

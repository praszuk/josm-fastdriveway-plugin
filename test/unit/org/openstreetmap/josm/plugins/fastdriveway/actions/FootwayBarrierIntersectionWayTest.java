package org.openstreetmap.josm.plugins.fastdriveway.actions;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openstreetmap.josm.command.Command;
import org.openstreetmap.josm.command.SequenceCommand;
import org.openstreetmap.josm.data.UndoRedoHandler;
import org.openstreetmap.josm.data.coor.LatLon;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.Way;
import org.openstreetmap.josm.plugins.fastdriveway.data.FastDrivewayObjectType;
import org.openstreetmap.josm.plugins.fastdriveway.TestUtil;
import org.openstreetmap.josm.testutils.JOSMTestRules;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class FootwayBarrierIntersectionWayTest {
    @Rule
    public JOSMTestRules rules = new JOSMTestRules().main();

    public FastDrivewayConfig cfg;

    @Before
    public void init(){
        cfg = new FastDrivewayConfig();
    }

    @Test
    public void testFootwayWithBarrierIntersectionSplitToParts(){
        DataSet ds = new DataSet();

        Node footwayStreetNode1 = new Node(new LatLon(1, 0));
        Node footwayNode2 = new Node(new LatLon(1, 1));
        Node footwayBarrierNode3 = new Node(new LatLon(1, 2));
        Node footwayNode4 = new Node(new LatLon(1, 3));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));

        Way footway = new Way();
        footway.addNode(footwayStreetNode1);
        footway.addNode(footwayNode2);
        footway.addNode(footwayBarrierNode3);
        footway.addNode(footwayNode4);

        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(footwayStreetNode1);
        street.addNode(streetNode3);
        street.put("highway", "residential");

        Node barrierNode1 = new Node(new LatLon(0, 2));
        Node barrierNode3 = new Node(new LatLon(2, 2));

        Way barrier = new Way();
        barrier.addNode(barrierNode1);
        barrier.addNode(footwayBarrierNode3);
        barrier.addNode(barrierNode3);

        barrier.put("barrier", "wall");

        ds.addPrimitive(footwayStreetNode1);
        ds.addPrimitive(footwayNode2);
        ds.addPrimitive(footwayBarrierNode3);
        ds.addPrimitive(footwayNode4);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);
        ds.addPrimitive(barrierNode1);
        ds.addPrimitive(barrierNode3);

        ds.addPrimitive(footway);
        ds.addPrimitive(street);
        ds.addPrimitive(barrier);

        ds.setSelected(footway);
        Collection<Command> commands = FastDrivewayPerform.performFootwayAction(ds, cfg);

        UndoRedoHandler.getInstance().add(new SequenceCommand("", commands));


        assertTrue(TestUtil.isPrimitiveContainsTags(
                footwayBarrierNode3,
                cfg.getMapOfTagMaps().get(FastDrivewayObjectType.FENCE_GATE))
        );
        assertEquals(footway.lastNode(), footwayBarrierNode3);

        Collection<Way> footways = TestUtil.filterFootways(ds, cfg);

        assertEquals(footways.size(), 2);
        assertEquals(footways.stream().filter(way -> way.hasTag("access", "private")).count(), 1);
    }

    @Test
    public void testFootwayWithMultipleBarrierIntersectionSplitToParts(){
        DataSet ds = new DataSet();

        Node footwayStreetNode1 = new Node(new LatLon(1, 0));
        Node footwayNode2 = new Node(new LatLon(1, 1));
        Node footwayBarrierNode3 = new Node(new LatLon(1, 2));
        Node footwayNode4 = new Node(new LatLon(1, 3));
        Node footwayBarrierNode5 = new Node(new LatLon(1, 4));
        Node footwayNode6 = new Node(new LatLon(1, 5));
        Node footwayBarrierNode7 = new Node(new LatLon(1, 6));
        Node footwayNode8 = new Node(new LatLon(1, 7));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));

        Way footway = new Way();
        footway.addNode(footwayStreetNode1);
        footway.addNode(footwayNode2);
        footway.addNode(footwayBarrierNode3);
        footway.addNode(footwayNode4);
        footway.addNode(footwayBarrierNode5);
        footway.addNode(footwayNode6);
        footway.addNode(footwayBarrierNode7);
        footway.addNode(footwayNode8);

        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(footwayStreetNode1);
        street.addNode(streetNode3);
        street.put("highway", "residential");

        Node barrierNode1 = new Node(new LatLon(0, 2));
        Node barrierNode3 = new Node(new LatLon(2, 2));

        Way barrier = new Way();
        barrier.addNode(barrierNode1);
        barrier.addNode(footwayBarrierNode3);
        barrier.addNode(barrierNode3);

        barrier.put("barrier", "wall");

        Node barrierNode4 = new Node(new LatLon(0, 4));
        Node barrierNode6 = new Node(new LatLon(2, 4));

        Way barrier2 = new Way();
        barrier2.addNode(barrierNode4);
        barrier2.addNode(footwayBarrierNode5);
        barrier2.addNode(barrierNode6);

        barrier2.put("barrier", "fence");

        Node barrierNode7 = new Node(new LatLon(0, 6));
        Node barrierNode9 = new Node(new LatLon(2, 6));

        Way barrier3 = new Way();
        barrier3.addNode(barrierNode7);
        barrier3.addNode(footwayBarrierNode7);
        barrier3.addNode(barrierNode9);

        barrier3.put("barrier", "yes");

        ds.addPrimitive(footwayStreetNode1);
        ds.addPrimitive(footwayNode2);
        ds.addPrimitive(footwayBarrierNode3);
        ds.addPrimitive(footwayNode4);
        ds.addPrimitive(footwayBarrierNode5);
        ds.addPrimitive(footwayNode6);
        ds.addPrimitive(footwayBarrierNode7);
        ds.addPrimitive(footwayNode8);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);
        ds.addPrimitive(barrierNode1);
        ds.addPrimitive(barrierNode3);
        ds.addPrimitive(barrierNode4);
        ds.addPrimitive(barrierNode6);
        ds.addPrimitive(barrierNode7);
        ds.addPrimitive(barrierNode9);

        ds.addPrimitive(footway);
        ds.addPrimitive(street);
        ds.addPrimitive(barrier);
        ds.addPrimitive(barrier2);
        ds.addPrimitive(barrier3);

        ds.setSelected(footway);
        Collection<Command> commands = FastDrivewayPerform.performFootwayAction(ds, cfg);

        UndoRedoHandler.getInstance().add(new SequenceCommand("", commands));


        Collection<Way> footways = TestUtil.filterFootways(ds, cfg);

        assertEquals(footway.lastNode(), footwayBarrierNode3);
        assertEquals(footways.size(), 2);  // no support for cycles/return to highway

        assertEquals(
            footways.stream().filter(way-> way.hasTag("access", "private")).count(),
                footways.size() - 1
        );
    }
}

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


public class DrivewayBarrierIntersectionWayTest {
    @Rule
    public JOSMTestRules rules = new JOSMTestRules().main();

    public FastDrivewayConfig cfg;

    @Before
    public void init(){
        cfg = new FastDrivewayConfig();
    }

    @Test
    public void testDrivewayWithBarrierIntersectionSplitToParts(){
        DataSet ds = new DataSet();

        Node drivewayStreetNode1 = new Node(new LatLon(1, 0));
        Node drivewayNode2 = new Node(new LatLon(1, 1));
        Node drivewayBarrierNode3 = new Node(new LatLon(1, 2));
        Node drivewayNode4 = new Node(new LatLon(1, 3));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));

        Way driveway = new Way();
        driveway.addNode(drivewayStreetNode1);
        driveway.addNode(drivewayNode2);
        driveway.addNode(drivewayBarrierNode3);
        driveway.addNode(drivewayNode4);

        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(drivewayStreetNode1);
        street.addNode(streetNode3);
        street.put("highway", "residential");

        Node barrierNode1 = new Node(new LatLon(0, 2));
        Node barrierNode3 = new Node(new LatLon(2, 2));

        Way barrier = new Way();
        barrier.addNode(barrierNode1);
        barrier.addNode(drivewayBarrierNode3);
        barrier.addNode(barrierNode3);

        barrier.put("barrier", "wall");

        ds.addPrimitive(drivewayStreetNode1);
        ds.addPrimitive(drivewayNode2);
        ds.addPrimitive(drivewayBarrierNode3);
        ds.addPrimitive(drivewayNode4);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);
        ds.addPrimitive(barrierNode1);
        ds.addPrimitive(barrierNode3);

        ds.addPrimitive(driveway);
        ds.addPrimitive(street);
        ds.addPrimitive(barrier);

        ds.setSelected(driveway);
        Collection<Command> commands = FastDrivewayPerform.performDrivewayAction(ds, cfg);

        UndoRedoHandler.getInstance().add(new SequenceCommand("", commands));


        assertTrue(TestUtil.isPrimitiveContainsTags(
                drivewayBarrierNode3,
                cfg.getMapOfTagMaps().get(FastDrivewayObjectType.FENCE_GATE))
        );
        assertEquals(driveway.lastNode(), drivewayBarrierNode3);

        Collection<Way> driveways = TestUtil.filterDriveways(ds, cfg);

        assertEquals(driveways.size(), 2);
        assertEquals(driveways.stream().filter(way -> way.hasTag("access", "private")).count(), 1);
    }

    @Test
    public void testDrivewayWithMultipleBarrierIntersectionSplitToParts(){
        DataSet ds = new DataSet();

        Node drivewayStreetNode1 = new Node(new LatLon(1, 0));
        Node drivewayNode2 = new Node(new LatLon(1, 1));
        Node drivewayBarrierNode3 = new Node(new LatLon(1, 2));
        Node drivewayNode4 = new Node(new LatLon(1, 3));
        Node drivewayBarrierNode5 = new Node(new LatLon(1, 4));
        Node drivewayNode6 = new Node(new LatLon(1, 5));
        Node drivewayBarrierNode7 = new Node(new LatLon(1, 6));
        Node drivewayNode8 = new Node(new LatLon(1, 7));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));

        Way driveway = new Way();
        driveway.addNode(drivewayStreetNode1);
        driveway.addNode(drivewayNode2);
        driveway.addNode(drivewayBarrierNode3);
        driveway.addNode(drivewayNode4);
        driveway.addNode(drivewayBarrierNode5);
        driveway.addNode(drivewayNode6);
        driveway.addNode(drivewayBarrierNode7);
        driveway.addNode(drivewayNode8);

        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(drivewayStreetNode1);
        street.addNode(streetNode3);
        street.put("highway", "residential");

        Node barrierNode1 = new Node(new LatLon(0, 2));
        Node barrierNode3 = new Node(new LatLon(2, 2));

        Way barrier = new Way();
        barrier.addNode(barrierNode1);
        barrier.addNode(drivewayBarrierNode3);
        barrier.addNode(barrierNode3);

        barrier.put("barrier", "wall");

        Node barrierNode4 = new Node(new LatLon(0, 4));
        Node barrierNode6 = new Node(new LatLon(2, 4));

        Way barrier2 = new Way();
        barrier2.addNode(barrierNode4);
        barrier2.addNode(drivewayBarrierNode5);
        barrier2.addNode(barrierNode6);

        barrier2.put("barrier", "fence");

        Node barrierNode7 = new Node(new LatLon(0, 6));
        Node barrierNode9 = new Node(new LatLon(2, 6));

        Way barrier3 = new Way();
        barrier3.addNode(barrierNode7);
        barrier3.addNode(drivewayBarrierNode7);
        barrier3.addNode(barrierNode9);

        barrier3.put("barrier", "yes");

        ds.addPrimitive(drivewayStreetNode1);
        ds.addPrimitive(drivewayNode2);
        ds.addPrimitive(drivewayBarrierNode3);
        ds.addPrimitive(drivewayNode4);
        ds.addPrimitive(drivewayBarrierNode5);
        ds.addPrimitive(drivewayNode6);
        ds.addPrimitive(drivewayBarrierNode7);
        ds.addPrimitive(drivewayNode8);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);
        ds.addPrimitive(barrierNode1);
        ds.addPrimitive(barrierNode3);
        ds.addPrimitive(barrierNode4);
        ds.addPrimitive(barrierNode6);
        ds.addPrimitive(barrierNode7);
        ds.addPrimitive(barrierNode9);

        ds.addPrimitive(driveway);
        ds.addPrimitive(street);
        ds.addPrimitive(barrier);
        ds.addPrimitive(barrier2);
        ds.addPrimitive(barrier3);

        ds.setSelected(driveway);
        Collection<Command> commands = FastDrivewayPerform.performDrivewayAction(ds, cfg);

        UndoRedoHandler.getInstance().add(new SequenceCommand("", commands));


        Collection<Way> driveways = TestUtil.filterDriveways(ds, cfg);

        assertEquals(driveway.lastNode(), drivewayBarrierNode3);
        assertEquals(driveways.size(), 2);  // no support for cycles/return to highway

        assertEquals(
            driveways.stream().filter(way-> way.hasTag("access", "private")).count(),
                driveways.size() - 1
        );
    }
}

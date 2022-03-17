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

import static org.junit.Assert.assertTrue;


public class DrivewayLastNodeTest {
    @Rule
    public JOSMTestRules rules = new JOSMTestRules().main();

    private FastDrivewayConfig cfg;

    @Before
    public void init(){
        cfg = new FastDrivewayConfig();
    }

    @Test
    public void testLastNodeNotConnectedToAnything(){
        DataSet ds = new DataSet();

        Node drivewayStreetNode1 = new Node(new LatLon(1, 0));
        Node drivewayNode2 = new Node(new LatLon(1, 1));
        Node drivewayLastNode3 = new Node(new LatLon(1, 2));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));

        Way driveway = new Way();
        driveway.addNode(drivewayStreetNode1);
        driveway.addNode(drivewayNode2);
        driveway.addNode(drivewayLastNode3);

        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(drivewayStreetNode1);
        street.addNode(streetNode3);
        street.put("highway", "residential");

        ds.addPrimitive(drivewayStreetNode1);
        ds.addPrimitive(drivewayNode2);
        ds.addPrimitive(drivewayLastNode3);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);

        ds.addPrimitive(driveway);
        ds.addPrimitive(street);

        ds.setSelected(driveway);
        Collection<Command> commands = FastDrivewayPerform.performDrivewayAction(ds, cfg);

        UndoRedoHandler.getInstance().add(new SequenceCommand("", commands));

        assertTrue(TestUtil.isPrimitiveContainsTags(
            driveway.lastNode(),
            cfg.getMapOfTagMaps().get(FastDrivewayObjectType.DRIVEWAY_NO_EXIT))
        );
    }

    @Test
    public void testLastNodeConnectedToBarrier(){
        DataSet ds = new DataSet();

        Node drivewayStreetNode1 = new Node(new LatLon(1, 0));
        Node drivewayNode2 = new Node(new LatLon(1, 1));
        Node drivewayLastNode3 = new Node(new LatLon(1, 2));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));

        Way driveway = new Way();
        driveway.addNode(drivewayStreetNode1);
        driveway.addNode(drivewayNode2);
        driveway.addNode(drivewayLastNode3);

        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(drivewayStreetNode1);
        street.addNode(streetNode3);
        street.put("highway", "residential");

        Node barrierNode1 = new Node(new LatLon(0, 2));
        Node barrierNode3 = new Node(new LatLon(2, 2));

        Way barrier = new Way();
        barrier.addNode(barrierNode1);
        barrier.addNode(drivewayLastNode3);
        barrier.addNode(barrierNode3);

        barrier.put("barrier", "wall");

        ds.addPrimitive(drivewayStreetNode1);
        ds.addPrimitive(drivewayNode2);
        ds.addPrimitive(drivewayLastNode3);
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
            driveway.lastNode(),
            cfg.getMapOfTagMaps().get(FastDrivewayObjectType.FENCE_GATE))
        );
    }

    @Test
    public void testLastNodeConnectedToBuilding(){
        DataSet ds = new DataSet();

        Node drivewayStreetNode1 = new Node(new LatLon(1, 0));
        Node drivewayNode2 = new Node(new LatLon(1, 1));
        Node drivewayLastNode3 = new Node(new LatLon(1, 2));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));

        Way driveway = new Way();
        driveway.addNode(drivewayStreetNode1);
        driveway.addNode(drivewayNode2);
        driveway.addNode(drivewayLastNode3);

        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(drivewayStreetNode1);
        street.addNode(streetNode3);
        street.put("highway", "residential");

        Node buildingNode1 = new Node(new LatLon(0, 2));
        Node buildingNode3 = new Node(new LatLon(2, 2));
        Node buildingNode4 = new Node(new LatLon(2, 3));
        Node buildingNode5 = new Node(new LatLon(0, 3));
        Way building = new Way();
        building.addNode(buildingNode1);
        building.addNode(drivewayLastNode3);
        building.addNode(buildingNode3);
        building.addNode(buildingNode4);
        building.addNode(buildingNode5);
        building.addNode(buildingNode1);
        building.put("building", "yes");

        ds.addPrimitive(drivewayStreetNode1);
        ds.addPrimitive(drivewayNode2);
        ds.addPrimitive(drivewayLastNode3);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);
        ds.addPrimitive(buildingNode1);
        ds.addPrimitive(buildingNode3);
        ds.addPrimitive(buildingNode4);
        ds.addPrimitive(buildingNode5);

        ds.addPrimitive(driveway);
        ds.addPrimitive(street);
        ds.addPrimitive(building);

        ds.setSelected(driveway);
        Collection<Command> commands = FastDrivewayPerform.performDrivewayAction(ds, cfg);

        UndoRedoHandler.getInstance().add(new SequenceCommand("", commands));

        assertTrue(TestUtil.isPrimitiveContainsTags(
            driveway.lastNode(),
            cfg.getMapOfTagMaps().get(FastDrivewayObjectType.BUILDING_GARAGE_ENTRANCE))
        );
    }


}

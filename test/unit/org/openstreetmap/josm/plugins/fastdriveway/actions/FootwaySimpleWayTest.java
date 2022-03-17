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


public class FootwaySimpleWayTest {
    @Rule
    public JOSMTestRules rules = new JOSMTestRules().main();

    public FastDrivewayConfig cfg;

    @Before
    public void init(){
        cfg = new FastDrivewayConfig();
    }

    @Test
    public void testFootwayConnectedToStreet(){
        DataSet ds = new DataSet();

        Node footwayStreetNode1 = new Node(new LatLon(1, 0));
        Node footwayNode2 = new Node(new LatLon(1, 1));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));

        Way footway = new Way();
        footway.addNode(footwayStreetNode1);
        footway.addNode(footwayNode2);

        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(footwayStreetNode1);
        street.addNode(streetNode3);
        street.put("highway", "residential");

        ds.addPrimitive(footwayStreetNode1);
        ds.addPrimitive(footwayNode2);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);

        ds.addPrimitive(footway);
        ds.addPrimitive(street);

        ds.setSelected(footway);
        Collection<Command> commands = FastDrivewayPerform.performFootwayAction(ds, cfg);

        UndoRedoHandler.getInstance().add(new SequenceCommand("", commands));

        assertTrue(TestUtil.isPrimitiveContainsTags(
            footway,
            cfg.getMapOfTagMaps().get(FastDrivewayObjectType.FOOTWAY))
        );
    }

    @Test
    public void testFootwayConnectedToFootway(){
        DataSet ds = new DataSet();

        Node footwayStreetNode1 = new Node(new LatLon(1, 0));
        Node footwayNode2 = new Node(new LatLon(1, 1));

        Node footway2Node1  = new Node(new LatLon(0, 0));
        Node footway2Node3  = new Node(new LatLon(2, 0));

        Way footway = new Way();
        footway.addNode(footwayStreetNode1);
        footway.addNode(footwayNode2);

        Way footway2 = new Way();
        footway2.addNode(footway2Node1);
        footway2.addNode(footwayStreetNode1);
        footway2.addNode(footway2Node3);
        footway2.put("highway", "footway");

        ds.addPrimitive(footwayStreetNode1);
        ds.addPrimitive(footwayNode2);
        ds.addPrimitive(footway2Node1);
        ds.addPrimitive(footway2Node3);

        ds.addPrimitive(footway);
        ds.addPrimitive(footway2);

        ds.setSelected(footway);
        Collection<Command> commands = FastDrivewayPerform.performFootwayAction(ds, cfg);

        UndoRedoHandler.getInstance().add(new SequenceCommand("", commands));

        assertTrue(TestUtil.isPrimitiveContainsTags(
            footway,
            cfg.getMapOfTagMaps().get(FastDrivewayObjectType.FOOTWAY))
        );
    }

    @Test
    public void testFootwayNotConnectedToStreet(){
        DataSet ds = new DataSet();

        Node footwayNode1 = new Node(new LatLon(1, 0));
        Node footwayNode2 = new Node(new LatLon(1, 1));

        Way footway = new Way();
        footway.addNode(footwayNode1);
        footway.addNode(footwayNode2);

        ds.addPrimitive(footwayNode1);
        ds.addPrimitive(footwayNode2);
        ds.addPrimitive(footway);

        ds.setSelected(footway);
        Collection<Command> commands = FastDrivewayPerform.performFootwayAction(ds, cfg);
        assertTrue(commands.isEmpty());
    }

    @Test
    public void testFootwayConnectedToStreetNotSelectedInDataSet(){
        DataSet ds = new DataSet();

        Node footwayStreetNode1 = new Node(new LatLon(1, 0));
        Node footwayNode2 = new Node(new LatLon(1, 1));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));

        Way footway = new Way();
        footway.addNode(footwayStreetNode1);
        footway.addNode(footwayNode2);

        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(footwayStreetNode1);
        street.addNode(streetNode3);
        street.put("highway", "residential");

        ds.addPrimitive(footwayStreetNode1);
        ds.addPrimitive(footwayNode2);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);

        ds.addPrimitive(footway);
        ds.addPrimitive(street);

        Collection<Command> commands = FastDrivewayPerform.performFootwayAction(ds, cfg);

        assertTrue(commands.isEmpty());
    }

    @Test
    public void testFootwayConnectedToStreetButFootwayIsReversed(){
        DataSet ds = new DataSet();

        Node footwayStreetNode1 = new Node(new LatLon(1, 0));
        Node footwayNode2 = new Node(new LatLon(1, 1));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));

        Way footway = new Way();
        footway.addNode(footwayNode2);
        footway.addNode(footwayStreetNode1);


        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(footwayStreetNode1);
        street.addNode(streetNode3);
        street.put("highway", "residential");

        ds.addPrimitive(footwayStreetNode1);
        ds.addPrimitive(footwayNode2);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);

        ds.addPrimitive(footway);
        ds.addPrimitive(street);

        ds.setSelected(footway);

        Collection<Command> commands = FastDrivewayPerform.performFootwayAction(ds, cfg);
        assertTrue(commands.isEmpty());
    }

    @Test
    public void testFootwayNotEmptyTags(){
        DataSet ds = new DataSet();

        Node footwayStreetNode1 = new Node(new LatLon(1, 0));
        Node footwayNode2 = new Node(new LatLon(1, 1));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));

        Way footway = new Way();
        footway.addNode(footwayStreetNode1);
        footway.addNode(footwayNode2);
        footway.put("highway", "service");

        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(footwayStreetNode1);
        street.addNode(streetNode3);
        street.put("highway", "residential");

        ds.addPrimitive(footwayStreetNode1);
        ds.addPrimitive(footwayNode2);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);

        ds.addPrimitive(footway);
        ds.addPrimitive(street);

        ds.setSelected(footway);

        Collection<Command> commands = FastDrivewayPerform.performFootwayAction(ds, cfg);
        assertTrue(commands.isEmpty());
    }

    @Test
    public void testMultipleFootwaySelectedAndConnectedToStreet(){
        DataSet ds = new DataSet();

        Node footwayStreetNode1 = new Node(new LatLon(1, 0));
        Node footwayNode2 = new Node(new LatLon(1, 1));

        Node footwayStreetNode3 = new Node(new LatLon(3, 0));
        Node footwayNode4 = new Node(new LatLon(3, 1));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));
        Node streetNode5 = new Node(new LatLon(4, 0));

        Way footway = new Way();
        footway.addNode(footwayStreetNode1);
        footway.addNode(footwayNode2);

        Way footway2 = new Way();
        footway2.addNode(footwayStreetNode3);
        footway2.addNode(footwayNode4);

        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(footwayStreetNode1);
        street.addNode(streetNode3);
        street.addNode(footwayStreetNode3);
        street.addNode(streetNode5);
        street.put("highway", "residential");

        ds.addPrimitive(footwayStreetNode1);
        ds.addPrimitive(footwayNode2);
        ds.addPrimitive(footwayStreetNode3);
        ds.addPrimitive(footwayNode4);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);
        ds.addPrimitive(streetNode5);

        ds.addPrimitive(footway);
        ds.addPrimitive(footway2);
        ds.addPrimitive(street);

        ds.setSelected(footway, footway2);
        Collection<Command> commands = FastDrivewayPerform.performFootwayAction(ds, cfg);

        UndoRedoHandler.getInstance().add(new SequenceCommand("", commands));

        Collection<Way> driveways = TestUtil.filterFootways(ds, cfg);
        assertEquals(driveways.size(), 2);
    }
}

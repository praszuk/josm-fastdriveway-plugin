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


public class DrivewaySimpleWayTest {
    @Rule
    public JOSMTestRules rules = new JOSMTestRules().main();

    public FastDrivewayConfig cfg;

    @Before
    public void init(){
        cfg = new FastDrivewayConfig();
    }

    @Test
    public void testDrivewayConnectedToStreet(){
        DataSet ds = new DataSet();

        Node drivewayStreetNode1 = new Node(new LatLon(1, 0));
        Node drivewayNode2 = new Node(new LatLon(1, 1));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));

        Way driveway = new Way();
        driveway.addNode(drivewayStreetNode1);
        driveway.addNode(drivewayNode2);

        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(drivewayStreetNode1);
        street.addNode(streetNode3);
        street.put("highway", "residential");

        ds.addPrimitive(drivewayStreetNode1);
        ds.addPrimitive(drivewayNode2);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);

        ds.addPrimitive(driveway);
        ds.addPrimitive(street);

        ds.setSelected(driveway);
        Collection<Command> commands = FastDrivewayPerform.performDrivewayAction(ds, cfg);

        UndoRedoHandler.getInstance().add(new SequenceCommand("", commands));

        assertTrue(TestUtil.isPrimitiveContainsTags(
            driveway,
            cfg.getMapOfTagMaps().get(FastDrivewayObjectType.DRIVEWAY))
        );
    }

    @Test
    public void testDrivewayNotConnectedToStreet(){
        DataSet ds = new DataSet();

        Node drivewayNode1 = new Node(new LatLon(1, 0));
        Node drivewayNode2 = new Node(new LatLon(1, 1));

        Way driveway = new Way();
        driveway.addNode(drivewayNode1);
        driveway.addNode(drivewayNode2);

        ds.addPrimitive(drivewayNode1);
        ds.addPrimitive(drivewayNode2);
        ds.addPrimitive(driveway);

        ds.setSelected(driveway);
        Collection<Command> commands = FastDrivewayPerform.performDrivewayAction(ds, cfg);

        assertTrue(commands.isEmpty());
    }

    @Test
    public void testDrivewayConnectedToStreetNotSelectedInDataSet(){
        DataSet ds = new DataSet();

        Node drivewayStreetNode1 = new Node(new LatLon(1, 0));
        Node drivewayNode2 = new Node(new LatLon(1, 1));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));

        Way driveway = new Way();
        driveway.addNode(drivewayStreetNode1);
        driveway.addNode(drivewayNode2);

        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(drivewayStreetNode1);
        street.addNode(streetNode3);
        street.put("highway", "residential");

        ds.addPrimitive(drivewayStreetNode1);
        ds.addPrimitive(drivewayNode2);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);

        ds.addPrimitive(driveway);
        ds.addPrimitive(street);

        Collection<Command> commands = FastDrivewayPerform.performDrivewayAction(ds, cfg);

        assertTrue(commands.isEmpty());
    }

    @Test
    public void testDrivewayConnectedToStreetButDrivewayIsReversed(){
        DataSet ds = new DataSet();

        Node drivewayStreetNode1 = new Node(new LatLon(1, 0));
        Node drivewayNode2 = new Node(new LatLon(1, 1));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));

        Way driveway = new Way();
        driveway.addNode(drivewayNode2);
        driveway.addNode(drivewayStreetNode1);


        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(drivewayStreetNode1);
        street.addNode(streetNode3);
        street.put("highway", "residential");

        ds.addPrimitive(drivewayStreetNode1);
        ds.addPrimitive(drivewayNode2);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);

        ds.addPrimitive(driveway);
        ds.addPrimitive(street);

        ds.setSelected(driveway);

        Collection<Command> commands = FastDrivewayPerform.performDrivewayAction(ds, cfg);
        assertTrue(commands.isEmpty());
    }

    @Test
    public void testDrivewayNotEmptyTags(){
        DataSet ds = new DataSet();

        Node drivewayStreetNode1 = new Node(new LatLon(1, 0));
        Node drivewayNode2 = new Node(new LatLon(1, 1));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));

        Way driveway = new Way();
        driveway.addNode(drivewayStreetNode1);
        driveway.addNode(drivewayNode2);
        driveway.put("highway", "service");

        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(drivewayStreetNode1);
        street.addNode(streetNode3);
        street.put("highway", "residential");

        ds.addPrimitive(drivewayStreetNode1);
        ds.addPrimitive(drivewayNode2);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);

        ds.addPrimitive(driveway);
        ds.addPrimitive(street);

        ds.setSelected(driveway);

        Collection<Command> commands = FastDrivewayPerform.performDrivewayAction(ds, cfg);
        assertTrue(commands.isEmpty());
    }

    @Test
    public void testMultipleDrivewaySelectedAndConnectedToStreet(){
        DataSet ds = new DataSet();

        Node drivewayStreetNode1 = new Node(new LatLon(1, 0));
        Node drivewayNode2 = new Node(new LatLon(1, 1));

        Node drivewayStreetNode3 = new Node(new LatLon(3, 0));
        Node drivewayNode4 = new Node(new LatLon(3, 1));

        Node streetNode1  = new Node(new LatLon(0, 0));
        Node streetNode3  = new Node(new LatLon(2, 0));
        Node streetNode5 = new Node(new LatLon(4, 0));

        Way driveway = new Way();
        driveway.addNode(drivewayStreetNode1);
        driveway.addNode(drivewayNode2);

        Way driveway2 = new Way();
        driveway2.addNode(drivewayStreetNode3);
        driveway2.addNode(drivewayNode4);

        Way street = new Way();
        street.addNode(streetNode1);
        street.addNode(drivewayStreetNode1);
        street.addNode(streetNode3);
        street.addNode(drivewayStreetNode3);
        street.addNode(streetNode5);
        street.put("highway", "residential");

        ds.addPrimitive(drivewayStreetNode1);
        ds.addPrimitive(drivewayNode2);
        ds.addPrimitive(drivewayStreetNode3);
        ds.addPrimitive(drivewayNode4);
        ds.addPrimitive(streetNode1);
        ds.addPrimitive(streetNode3);
        ds.addPrimitive(streetNode5);

        ds.addPrimitive(driveway);
        ds.addPrimitive(driveway2);
        ds.addPrimitive(street);

        ds.setSelected(driveway, driveway2);
        Collection<Command> commands = FastDrivewayPerform.performDrivewayAction(ds, cfg);

        UndoRedoHandler.getInstance().add(new SequenceCommand("", commands));

        Collection<Way> driveways = TestUtil.filterDriveways(ds, cfg);
        assertEquals(driveways.size(), 2);
    }
}

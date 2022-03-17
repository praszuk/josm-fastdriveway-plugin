package org.openstreetmap.josm.plugins.fastdriveway.actions;

import org.openstreetmap.josm.command.ChangePropertyCommand;
import org.openstreetmap.josm.command.Command;
import org.openstreetmap.josm.command.SplitWayCommand;
import org.openstreetmap.josm.data.osm.*;
import org.openstreetmap.josm.plugins.fastdriveway.data.FastDrivewayObjectType;
import org.openstreetmap.josm.plugins.fastdriveway.utils.FastDrivewayPerformUtils;
import org.openstreetmap.josm.plugins.fastdriveway.validation.FastDrivewayPerformValidator;

import java.util.*;

public class FastDrivewayPerform {

    private FastDrivewayPerform(){}

    @SuppressWarnings("Duplicates")
    static Collection<Command> performDrivewayAction(DataSet dataSet, FastDrivewayConfig config){
        Collection<Command> commands = new ArrayList<>();
        Collection<Way> selectedWays = dataSet.getSelectedWays();

        if (selectedWays.size() < 1){
            return commands;
        }

        selectedWays.stream().filter(FastDrivewayPerformValidator::isWayValid).forEach(currentWay -> {
            commands.add(new ChangePropertyCommand(
                    Arrays.asList(currentWay),
                    config.getMapOfTagMaps().get(FastDrivewayObjectType.DRIVEWAY))
            );

            Node lastNode = currentWay.lastNode();

            assert lastNode != null;

            if (FastDrivewayPerformUtils.isNodeStickToWayWithTag(lastNode, new AbstractMap.SimpleEntry<>("building", "*"))){
                commands.add(new ChangePropertyCommand(
                    Arrays.asList(lastNode),
                    config.getMapOfTagMaps().get(FastDrivewayObjectType.BUILDING_GARAGE_ENTRANCE))
                );
            }
            else if(FastDrivewayPerformUtils.isNodeStickToWayWithTag(lastNode, new AbstractMap.SimpleEntry<>("barrier", "*"))) {
                commands.add(new ChangePropertyCommand(
                    Arrays.asList(lastNode),
                    config.getMapOfTagMaps().get(FastDrivewayObjectType.FENCE_GATE))
                );
            }
            else if (!lastNode.isReferredByWays(2)) { // not referred by anything expect new driveway
                commands.add(new ChangePropertyCommand(
                        Arrays.asList(lastNode),
                        config.getMapOfTagMaps().get(FastDrivewayObjectType.DRIVEWAY_NO_EXIT))
                );
            }

            // if barrier nodes exists then divide way to 2 parts, first public, second with access=private.
            List<Node> barrierNodes = FastDrivewayPerformUtils.getBarrierNodesFromWay(currentWay);
            if (barrierNodes.size() == 0)
                return;

            commands.add(new ChangePropertyCommand(
                barrierNodes,
                config.getMapOfTagMaps().get(FastDrivewayObjectType.FENCE_GATE))
            );

            SplitWayCommand swCommand = SplitWayCommand.splitWay(
                currentWay,
                FastDrivewayPerformUtils.createWayChunksAt(currentWay, barrierNodes.get(0)),
                new ArrayList<>(),
                SplitWayCommand.Strategy.keepFirstChunk()
            );
            commands.add(swCommand);

            Way newWay = swCommand.getNewWays().get(0);

            config.getMapOfTagMaps().get(FastDrivewayObjectType.DRIVEWAY).forEach(newWay::put);
            newWay.put("access", "private");
        });

        return commands;
    }

    @SuppressWarnings("Duplicates")
    public static Collection<Command> performFootwayAction(DataSet dataSet, FastDrivewayConfig config){
        Collection<Command> commands = new ArrayList<>();
        Collection<Way> selectedWays = dataSet.getSelectedWays();

        if (selectedWays.size() < 1){
            return commands;
        }

        selectedWays.stream().filter(FastDrivewayPerformValidator::isWayValid).forEach(currentWay -> {
            commands.add(new ChangePropertyCommand(
                Arrays.asList(currentWay),
                config.getMapOfTagMaps().get(FastDrivewayObjectType.FOOTWAY))
            );
            Node lastNode = currentWay.lastNode();

            assert lastNode != null;

            if (FastDrivewayPerformUtils.isNodeStickToWayWithTag(lastNode, new AbstractMap.SimpleEntry<>("building", "*"))){
                commands.add(new ChangePropertyCommand(
                    Arrays.asList(lastNode),
                    config.getMapOfTagMaps().get(FastDrivewayObjectType.BUILDING_ENTRANCE))
                );
            }
            else if(FastDrivewayPerformUtils.isNodeStickToWayWithTag(lastNode, new AbstractMap.SimpleEntry<>("barrier", "*"))) {
                commands.add(new ChangePropertyCommand(
                    Arrays.asList(lastNode),
                    config.getMapOfTagMaps().get(FastDrivewayObjectType.FENCE_GATE))
                );
            }
            else if (!lastNode.isReferredByWays(2)) { // not referred by anything expect new driveway
                commands.add(new ChangePropertyCommand(
                        Arrays.asList(lastNode),
                        config.getMapOfTagMaps().get(FastDrivewayObjectType.FOOTWAY_NO_EXIT))
                );
            }

            // if barrier nodes exists then divide way to 2 parts, first public, second with access=private.
            List<Node> barrierNodes = FastDrivewayPerformUtils.getBarrierNodesFromWay(currentWay);
            if (barrierNodes.size() == 0)
                return;

            commands.add(new ChangePropertyCommand(
                barrierNodes,
                config.getMapOfTagMaps().get(FastDrivewayObjectType.FENCE_GATE))
            );

            SplitWayCommand swCommand = SplitWayCommand.splitWay(
                    currentWay,
                    FastDrivewayPerformUtils.createWayChunksAt(currentWay, barrierNodes.get(0)),
                    new ArrayList<>(),
                    SplitWayCommand.Strategy.keepFirstChunk()
            );
            commands.add(swCommand);

            Way newWay = swCommand.getNewWays().get(0);

            config.getMapOfTagMaps().get(FastDrivewayObjectType.FOOTWAY).forEach(newWay::put);
            newWay.put("access", "private");
        });

        return commands;
    }


}

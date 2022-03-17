package org.openstreetmap.josm.plugins.fastdriveway.utils;

import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.TagMap;
import org.openstreetmap.josm.data.osm.Way;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FastDrivewayPerformUtils {
    /**
     *
     * @param node which is element of ways to check theirs tags
     * @param entryRequired key-value pair as OSM tag which is checked if exists in any shared (referred) way.
     * To check only key, use "*" or empty string as value.
     * @return true if at least 1 way has given tag (entryRequired) else false
     */
    public static boolean isNodeStickToWayWithTag(Node node, Map.Entry<String, String> entryRequired){
        return node.isReferredByWays(1) && node.getParentWays().stream().anyMatch(parentWay -> {
            TagMap wayTags = parentWay.getKeys();

            if (wayTags.containsKey(entryRequired.getKey())){
                if (entryRequired.getValue().equals("*") || entryRequired.getValue().isEmpty()){
                    return true;
                }
                else return wayTags.get(entryRequired.getKey()).equals(entryRequired.getValue());
            }
            return false;
        });
    }

    /**
     *
     * @param way way to split to 2 parts
     * @param splitNode node which will be splitting node – ending first way part and beginning second
     * @return List with 2 list of nodes (as 2 ways). SplitNode exists in both ways.
     * If splitNode is first or last node, then one list will be returned.
     * If splitNode is not element of way then return empty list.
     */
    public static List<List<Node>> createWayChunksAt(Way way, Node splitNode) {
        List<List<Node>> wayChunks = new ArrayList<>();
        int nodesSize = way.getNodes().size();
        int splitNodeIndex = way.getNodes().indexOf(splitNode);

        if (splitNodeIndex == -1){
            return wayChunks;
        }
        else if (splitNodeIndex == 0 || splitNodeIndex + 1 == nodesSize){
            wayChunks.add(way.getNodes());
            return wayChunks;
        }

        wayChunks.add(way.getNodes().stream().limit(splitNodeIndex + 1).collect(Collectors.toList()));
        wayChunks.add(way.getNodes().stream().skip(splitNodeIndex).collect(Collectors.toList()));

        return wayChunks;
    }

    /**
     * Check if there is a node which intersect a barrier between first and last (excluding those)
     * @param way which can contain nodes which intersects way with barrier tag
     * @return list of nodes which intersects way with barrier tags excluding first and last node on the way
     */
    public static List<Node> getBarrierNodesFromWay(Way way){
        return way.getNodes()
                .stream()
                .skip(1)
                .limit(way.getNodes().size() - 2)
                .filter(node -> isNodeStickToWayWithTag(node, new AbstractMap.SimpleEntry<>("barrier", "*")))
                .collect(Collectors.toList());

    }
}

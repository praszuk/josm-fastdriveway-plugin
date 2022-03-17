package org.openstreetmap.josm.plugins.fastdriveway.validation;

import org.openstreetmap.josm.data.osm.Node;
import org.openstreetmap.josm.data.osm.TagMap;
import org.openstreetmap.josm.data.osm.Way;
import org.openstreetmap.josm.plugins.fastdriveway.utils.FastDrivewayPerformUtils;

public class FastDrivewayPerformValidator {
    private final static TagMap FIRST_NODE_WAY_REQUIRED_TAGS = new TagMap("highway","*");
    private final static TagMap LAST_NODE_WAY_REQUIRED_TAGS = new TagMap(
        "barrier", "*",
        "building", "*"
    );

    public static boolean isWayValid(Way way){
        if (!way.getKeys().isEmpty() || way.isClosed()){
            return false;
        }

        Node firstNode = way.firstNode();
        Node lastNode = way.lastNode();

        if (firstNode == null || lastNode == null){
            return false;
        }

        // Driveway must start from highway=*
        boolean is_first_node_valid = FIRST_NODE_WAY_REQUIRED_TAGS
                .entrySet()
                .stream()
                .anyMatch(entry -> FastDrivewayPerformUtils.isNodeStickToWayWithTag(firstNode, entry));

        // Driveway must end with barrier=fence, building=* or just not referred to anything
        boolean is_last_node_valid = !lastNode.isReferredByWays(2) || LAST_NODE_WAY_REQUIRED_TAGS
                .entrySet()
                .stream()
                .anyMatch(entry -> FastDrivewayPerformUtils.isNodeStickToWayWithTag(lastNode, entry));

        return is_first_node_valid && is_last_node_valid;
    }
}

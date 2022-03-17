package org.openstreetmap.josm.plugins.fastdriveway;

import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.data.osm.OsmPrimitive;
import org.openstreetmap.josm.data.osm.TagMap;
import org.openstreetmap.josm.data.osm.Way;
import org.openstreetmap.josm.plugins.fastdriveway.actions.FastDrivewayConfig;
import org.openstreetmap.josm.plugins.fastdriveway.data.FastDrivewayObjectType;

import java.util.Collection;
import java.util.stream.Collectors;

public class TestUtil {
    public static boolean isPrimitiveContainsTags(OsmPrimitive osmPrimitive, TagMap tagMap){
        return tagMap.entrySet().stream().allMatch(pair -> osmPrimitive.hasTag(pair.getKey(), pair.getValue()));
    }

    private static Collection<Way> filterWay(DataSet ds, TagMap tagMap){
        return ds.getWays()
                .stream()
                .filter(way -> TestUtil.isPrimitiveContainsTags(way, tagMap))
                .collect(Collectors.toList());
    }
    public static Collection<Way> filterDriveways(DataSet ds, FastDrivewayConfig cfg){
        return filterWay(ds, cfg.getMapOfTagMaps().get(FastDrivewayObjectType.DRIVEWAY));
    }

    public static Collection<Way> filterFootways(DataSet ds, FastDrivewayConfig cfg){
        return filterWay(ds, cfg.getMapOfTagMaps().get(FastDrivewayObjectType.FOOTWAY));
    }
}

package org.openstreetmap.josm.plugins.fastdriveway.actions;

import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.data.osm.TagMap;
import org.openstreetmap.josm.plugins.fastdriveway.data.FastDrivewayObjectType;
import org.openstreetmap.josm.plugins.fastdriveway.ui.FastDrivewayConfigDialog;
import org.openstreetmap.josm.tools.Shortcut;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import static org.openstreetmap.josm.tools.I18n.tr;


public class FastDrivewayConfig extends JosmAction {
    public static final String DESCRIPTION = tr("FastDriveway: Config");

    private final TagMap drivewayTags = new TagMap(
        "highway", "service",
        "service", "driveway"
    );

    private final TagMap drivewayNoExitTags = new TagMap(
        "noexit", "yes"
    );

    private final TagMap footwayTags = new TagMap(
        "highway", "footway"
    );

    private final TagMap footwayNoExitTags = new TagMap(
        "noexit", "yes"
    );

    private final TagMap fenceGateTags = new TagMap(
        "barrier", "gate",
        "access", "private"
    );

    private final TagMap buildingEntranceTags = new TagMap(
        "entrance", "yes",
        "access", "private"
    );

    private final TagMap buildingEntranceGarageTags = new TagMap(
        "entrance", "garage",
        "access", "private"
    );

    public FastDrivewayConfig(){
        super(
            tr("Fast Driveway config"),
            "fastdriveway",
            DESCRIPTION,
            Shortcut.registerShortcut(
                "fastdriveway:config:tags",
                DESCRIPTION,
                KeyEvent.VK_6,
                Shortcut.CTRL_SHIFT
            ),
            true
        );
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Map<FastDrivewayObjectType, TagMap> mapOfTagMaps = getMapOfTagMaps();
        FastDrivewayConfigDialog dialog = new FastDrivewayConfigDialog(mapOfTagMaps);
        dialog.showDialog();

        // Confirm tags button == 1, else discard/cancel changes
        if (dialog.getValue() == 1){
            updateTagMaps(mapOfTagMaps);
        }
    }

    public Map<FastDrivewayObjectType, TagMap> getMapOfTagMaps(){
        Map<FastDrivewayObjectType, TagMap> mapOfTagMaps =  new HashMap<>();
        mapOfTagMaps.put(FastDrivewayObjectType.DRIVEWAY, new TagMap(this.drivewayTags));
        mapOfTagMaps.put(FastDrivewayObjectType.FOOTWAY, new TagMap(this.footwayTags));
        mapOfTagMaps.put(FastDrivewayObjectType.FENCE_GATE, new TagMap(this.fenceGateTags));
        mapOfTagMaps.put(FastDrivewayObjectType.BUILDING_ENTRANCE, new TagMap(this.buildingEntranceTags));
        mapOfTagMaps.put(FastDrivewayObjectType.BUILDING_GARAGE_ENTRANCE, new TagMap(this.buildingEntranceGarageTags));
        mapOfTagMaps.put(FastDrivewayObjectType.DRIVEWAY_NO_EXIT, new TagMap(this.drivewayNoExitTags));
        mapOfTagMaps.put(FastDrivewayObjectType.FOOTWAY_NO_EXIT, new TagMap(this.footwayNoExitTags));

        return mapOfTagMaps;
    }

    public void updateTagMaps(Map<FastDrivewayObjectType, TagMap> newMapOfTagMaps){
        this.drivewayTags.clear();
        this.drivewayTags.putAll(newMapOfTagMaps.get(FastDrivewayObjectType.DRIVEWAY));

        this.footwayTags.clear();
        this.footwayTags.putAll(newMapOfTagMaps.get(FastDrivewayObjectType.FOOTWAY));

        this.fenceGateTags.clear();
        this.fenceGateTags.putAll(newMapOfTagMaps.get(FastDrivewayObjectType.FENCE_GATE));

        this.buildingEntranceTags.clear();
        this.buildingEntranceTags.putAll(newMapOfTagMaps.get(FastDrivewayObjectType.BUILDING_ENTRANCE));

        this.buildingEntranceGarageTags.clear();
        this.buildingEntranceGarageTags.putAll(newMapOfTagMaps.get(FastDrivewayObjectType.BUILDING_GARAGE_ENTRANCE));
    }
}

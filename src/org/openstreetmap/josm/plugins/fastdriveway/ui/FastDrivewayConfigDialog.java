package org.openstreetmap.josm.plugins.fastdriveway.ui;

import org.openstreetmap.josm.data.osm.TagMap;
import org.openstreetmap.josm.gui.ExtendedDialog;
import org.openstreetmap.josm.gui.MainApplication;
import org.openstreetmap.josm.plugins.fastdriveway.data.FastDrivewayObjectType;

import javax.swing.*;

import java.util.Map;

import static org.openstreetmap.josm.tools.I18n.tr;

public class FastDrivewayConfigDialog extends ExtendedDialog {
    private final Map<FastDrivewayObjectType, TagMap> mapOfTagMaps;

    public FastDrivewayConfigDialog(Map<FastDrivewayObjectType, TagMap> mapOfTagMaps) {
        super(
            MainApplication.getMainFrame(),
            tr("FastDriveway config"),
            tr("Ok"),
            tr("Cancel")
        );
        this.mapOfTagMaps = mapOfTagMaps;

        createDialog();
    }
    private void createDialog(){
        JTabbedPane objectsTagsTabbedPane = new JTabbedPane();

        objectsTagsTabbedPane.addTab(
            tr("Driveway"),
            TagTablePanelComponent.createComponent(mapOfTagMaps.get(FastDrivewayObjectType.DRIVEWAY))
        );

        objectsTagsTabbedPane.addTab(
            tr("Footway"),
            TagTablePanelComponent.createComponent(mapOfTagMaps.get(FastDrivewayObjectType.FOOTWAY))
        );

        objectsTagsTabbedPane.addTab(
            tr("Fence gate"),
            TagTablePanelComponent.createComponent(mapOfTagMaps.get(FastDrivewayObjectType.FENCE_GATE))
        );

        objectsTagsTabbedPane.addTab(
            tr("Building entrance"),
            TagTablePanelComponent.createComponent(mapOfTagMaps.get(FastDrivewayObjectType.BUILDING_ENTRANCE))
        );

        objectsTagsTabbedPane.addTab(
            tr("Building garage entrance"),
            TagTablePanelComponent.createComponent(mapOfTagMaps.get(FastDrivewayObjectType.BUILDING_GARAGE_ENTRANCE))
        );

        setContent(objectsTagsTabbedPane, false);
        setButtonIcons("ok", "cancel");
        setDefaultButton(1);
    }
}

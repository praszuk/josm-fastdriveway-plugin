package org.openstreetmap.josm.plugins.fastdriveway;

import org.openstreetmap.josm.gui.MainApplication;
import org.openstreetmap.josm.gui.MainMenu;
import org.openstreetmap.josm.gui.MapFrame;
import org.openstreetmap.josm.plugins.Plugin;
import org.openstreetmap.josm.plugins.PluginInformation;
import org.openstreetmap.josm.plugins.fastdriveway.actions.FastDrivewayConfig;
import org.openstreetmap.josm.plugins.fastdriveway.actions.FastDrivewayDrivewayAction;
import org.openstreetmap.josm.plugins.fastdriveway.actions.FastDrivewayFootwayAction;

public class FastDrivewayPlugin extends Plugin {
    private final FastDrivewayConfig config;

    public FastDrivewayPlugin(PluginInformation info){
        super(info);
        this.config = new FastDrivewayConfig();

        FastDrivewayDrivewayAction drivewayAction = new FastDrivewayDrivewayAction(config);
        FastDrivewayFootwayAction footwayAction = new FastDrivewayFootwayAction(config);
        MainMenu.add(MainApplication.getMenu().moreToolsMenu, drivewayAction);
        MainMenu.add(MainApplication.getMenu().moreToolsMenu, footwayAction);
        MainMenu.add(MainApplication.getMenu().dataMenu, config);
    }

    @Override
    public void mapFrameInitialized(MapFrame oldFrame, MapFrame newFrame) {
        config.setEnabled(newFrame != null);
        super.mapFrameInitialized(oldFrame, newFrame);
    }
}

package org.openstreetmap.josm.plugins.fastdriveway.actions;

import org.openstreetmap.josm.actions.JosmAction;
import org.openstreetmap.josm.command.Command;
import org.openstreetmap.josm.command.SequenceCommand;
import org.openstreetmap.josm.data.UndoRedoHandler;
import org.openstreetmap.josm.data.osm.DataSet;
import org.openstreetmap.josm.tools.Shortcut;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collection;

import static org.openstreetmap.josm.plugins.fastdriveway.actions.FastDrivewayPerform.performDrivewayAction;
import static org.openstreetmap.josm.tools.I18n.tr;

public class FastDrivewayDrivewayAction extends JosmAction {
    static final String DESCRIPTION = tr("FastDriveway: Create driveway.");

    private final FastDrivewayConfig config;

    public FastDrivewayDrivewayAction(FastDrivewayConfig config){
        super(
                tr("Create driveway"),
                "fastdriveway",
                DESCRIPTION,
                Shortcut.registerShortcut(
                        "fastdriveway:create:driveway",
                        tr("FastDriveway: Fill way and nodes with driveway tags and optionally split"),
                        KeyEvent.VK_4,
                        Shortcut.CTRL_SHIFT
                ),
                true
        );
        this.config = config;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        DataSet dataSet = getLayerManager().getEditDataSet();
        Collection<Command> commands = performDrivewayAction(dataSet, config);

        if (commands.size() > 0){
            UndoRedoHandler.getInstance().add(new SequenceCommand(DESCRIPTION, commands));
        }

    }
}

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

import static org.openstreetmap.josm.plugins.fastdriveway.actions.FastDrivewayPerform.performFootwayAction;
import static org.openstreetmap.josm.tools.I18n.tr;

public class FastDrivewayFootwayAction extends JosmAction {
    static final String DESCRIPTION = tr("Faster driveway: Create footway.");

    private final FastDrivewayConfig config;

    public FastDrivewayFootwayAction(FastDrivewayConfig config){
        super(
                tr("Create footway"),
                "fastdriveway",
                DESCRIPTION,
                Shortcut.registerShortcut(
                        "fastdriveway:create:footway",
                        tr("FastDriveway: Fill way and nodes with footway tags"),
                        KeyEvent.VK_5,
                        Shortcut.CTRL_SHIFT
                ),
                true
        );
        this.config = config;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        DataSet dataSet = getLayerManager().getEditDataSet();
        Collection<Command> commands = performFootwayAction(dataSet, config);

        if (commands.size() > 0){
            UndoRedoHandler.getInstance().add(new SequenceCommand(DESCRIPTION, commands));
        }

    }
}

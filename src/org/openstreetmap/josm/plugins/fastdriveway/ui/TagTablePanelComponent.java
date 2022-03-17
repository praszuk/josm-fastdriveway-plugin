package org.openstreetmap.josm.plugins.fastdriveway.ui;

import org.openstreetmap.josm.data.osm.TagMap;
import org.openstreetmap.josm.gui.tagging.TagEditorModel;
import org.openstreetmap.josm.gui.tagging.TagTable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import java.awt.*;

public class TagTablePanelComponent extends TagTable {
    private final TagMap tagsData;
    private final TagEditorModel tagEditorModel;
    private boolean dataLoaded = false;

    private TagTablePanelComponent(TagEditorModel model, int maxCharacters, TagMap tagsData) {
        super(model, maxCharacters);
        this.tagEditorModel = model;
        this.tagsData = tagsData;
    }

    public static JComponent createComponent(TagMap tableTags){
        TagTablePanelComponent tagTable = new TagTablePanelComponent(new TagEditorModel(), 128, tableTags);

        tagTable.tagsData.forEach(tagTable.tagEditorModel::add);
        tagTable.dataLoaded = true;

        JScrollPane scrollPane = new JScrollPane(tagTable);
        tagTable.setFillsViewportHeight(true);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        return panel;
    }

    @Override
    public void tableChanged(TableModelEvent tableModelEvent) {
        super.tableChanged(tableModelEvent);
        if (dataLoaded){
            tagsData.clear();
            tagsData.putAll(tagEditorModel.getTags());
        }
    }
}

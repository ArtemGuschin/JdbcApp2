package net.artem.jdbc.application.controller;

import net.artem.jdbc.application.model.Label;
import net.artem.jdbc.application.service.LabelService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LabelController {

    private final LabelService labelService;


    public LabelController() {
        this.labelService = new LabelService();
    }


    public Label createLabel(String name) {
        Label label = new Label();
        label.setName(name);
        return labelService.createLabel(label);
    }

    public Label updateLabel(Long id, String name) {
        Label updateLabel = new Label(id, name);
        updateLabel.setId(id);
        updateLabel.setName(name);
        return labelService.updateLabel(updateLabel);

    }


    public void deleteLabel(Long id) {
        labelService.deleteLabel(id);
    }


    public List<Label> getAll() {
        return labelService.getAll();
    }

    public Label getLabelById(Long id){
        return labelService.getLabelBYId(id);
    }

}




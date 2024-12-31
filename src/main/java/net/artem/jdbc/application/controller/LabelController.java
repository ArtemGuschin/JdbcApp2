package net.artem.javacore.jdbc.application.controller;

import net.artem.javacore.jdbc.application.model.Label;
import net.artem.javacore.jdbc.application.service.LabelService;

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

    public Label updateLabel( Long id,String name) {
        Label updateLabel = new Label(id,name);
        updateLabel.setId(id);
        updateLabel.setName(name);
        return labelService.updateLabel(updateLabel);

    }



    public void deleteLabel(Long id) {
        Label deleteLabel = new Label();
        labelService.deleteLabel(deleteLabel);
    }


    public List<Label> getAll() {
        return labelService.getAll();
    }
}

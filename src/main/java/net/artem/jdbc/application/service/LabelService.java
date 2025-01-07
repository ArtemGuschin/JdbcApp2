package net.artem.jdbc.application.service;

import net.artem.jdbc.application.enums.LabelStatus;
import net.artem.jdbc.application.model.Label;
import net.artem.jdbc.application.repository.LabelRepository;
import net.artem.jdbc.application.repository.jdbc.JdbcLabelRepositoryImpl;

import java.util.List;

public class LabelService {

    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {

        this.labelRepository = labelRepository;
    }

    public LabelService() {
        this.labelRepository = new JdbcLabelRepositoryImpl();
    }


    public Label createLabel(Label label) {
        label.setLabelStatus(LabelStatus.ACTIVE);
        return labelRepository.save(label);
    }

    public Label updateLabel(Label label) {
        label.setLabelStatus(LabelStatus.UNDER_REVIEW);
        return labelRepository.update(label);
    }


    public void deleteLabel(Long id) {
        labelRepository.deleteById(id);

    }


    public List<Label> getAll() {
        return labelRepository.getAll();
    }
    public Label getLabelBYId(Long id){
        return labelRepository.getById(id);
    }
}

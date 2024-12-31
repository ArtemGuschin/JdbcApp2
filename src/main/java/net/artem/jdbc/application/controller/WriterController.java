package net.artem.javacore.jdbc.application.controller;

import net.artem.javacore.jdbc.application.enums.WriterStatus;
import net.artem.javacore.jdbc.application.model.Post;
import net.artem.javacore.jdbc.application.model.Writer;
import net.artem.javacore.jdbc.application.repository.jdbc.JdbcWriterRepositoryImpl;
import net.artem.javacore.jdbc.application.repository.WriterRepository;
import net.artem.javacore.jdbc.application.service.WriterService;

import java.util.List;

public class WriterController {
    private final WriterService writerService;


    public WriterController() {

        this.writerService = new WriterService();
    }

    public Writer createWriter(Long id,String firstName, String lastName) {
        Writer updateWriter = new Writer(id,firstName,lastName);
        updateWriter.setId(id);
        updateWriter.setFirstName(firstName);
        updateWriter.setLastName(lastName);
        return writerService.updateWriter(updateWriter);
    }

    public Writer updateWriter(Long id, String firstName, String lastName, List<Post> posts, WriterStatus writerStatus) {

        return null;
    }

    public void deleteWriter(Long id) {

    }
}

package net.artem.jdbc.application.controller;

import net.artem.jdbc.application.enums.WriterStatus;
import net.artem.jdbc.application.model.Post;
import net.artem.jdbc.application.model.Writer;
import net.artem.jdbc.application.service.WriterService;

import java.util.List;

public class WriterController {
    private final WriterService writerService;

    public WriterController(WriterService writerService) {
        this.writerService = writerService;
    }


    public Writer createWriter(String firstName, String lastName, WriterStatus writerStatus) {
        Writer writer = Writer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .writerStatus(writerStatus)
                .build();
        return writerService.createWriter(writer);
    }

    public Writer updateWriter(Long id, String firstName, String lastName, List<Post> posts, WriterStatus writerStatus) {
        Writer writer = Writer.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .posts(posts)
                .writerStatus(writerStatus)
                .build();

        return writerService.updateWriter(writer);
    }

    public void deleteWriter(Long id) {
        writerService.deleteWriter(id);
    }

    public List<Writer> getAll() {
        return writerService.getAll();
    }

    public Writer getWriterBYId(Long id) {
        return writerService.getWriterById(id);

    }
}

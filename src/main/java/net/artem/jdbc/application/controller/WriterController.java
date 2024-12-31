package net.artem.jdbc.application.controller;

import net.artem.jdbc.application.enums.WriterStatus;
import net.artem.jdbc.application.model.Post;
import net.artem.jdbc.application.model.Writer;
import net.artem.jdbc.application.repository.jdbc.JdbcWriterRepositoryImpl;
import net.artem.jdbc.application.repository.WriterRepository;
import net.artem.jdbc.application.service.WriterService;

import java.util.List;

public class WriterController {

    private final WriterRepository writerRepository;

    public WriterController() {
        this.writerRepository = new JdbcWriterRepositoryImpl();

    }

    public WriterController(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }


    public Writer createWriter(String firstName, String lastName, WriterStatus writerStatus) {
        Writer writer = Writer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .writerStatus(writerStatus)
                .build();
        return writerRepository.save(writer);
    }

    public Writer updateWriter(Long id, String firstName, String lastName, List<Post> posts, WriterStatus writerStatus) {
        Writer writer = Writer.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .posts(posts)
                .writerStatus(writerStatus)
                .build();

        return writerRepository.update(writer);
    }

    public void deleteWriter(Long id) {
        writerRepository.deleteById(id);
    }

    public List<Writer> getAll() {
        return writerRepository.getAll();
    }
}

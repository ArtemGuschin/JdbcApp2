package net.artem.jdbc.application.service;

import net.artem.jdbc.application.enums.WriterStatus;
import net.artem.jdbc.application.model.Writer;
import net.artem.jdbc.application.repository.WriterRepository;

import java.util.List;

public class WriterService {
    private final WriterRepository writerRepository;

    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public Writer updateWriter(Writer updateWriter) {
        updateWriter.setWriterStatus(WriterStatus.UNDER_REVIEW);
            return writerRepository.update(updateWriter);
    }

    public Writer createWriter(Writer writer) {
        writer.setWriterStatus(WriterStatus.ACTIVE);
        return writerRepository.save(writer);
    }
    public Writer deleteWriter(Writer writer){
        writer.setWriterStatus(WriterStatus.DELETED);
        return writer;

    }
    public List<Writer> getAll(){
        return writerRepository.getAll();
    }
}

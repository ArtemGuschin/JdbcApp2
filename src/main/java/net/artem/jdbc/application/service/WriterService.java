package net.artem.jdbc.application.service;

import net.artem.jdbc.application.enums.WriterStatus;
import net.artem.jdbc.application.model.Writer;
import net.artem.jdbc.application.repository.WriterRepository;
import net.artem.jdbc.application.repository.jdbc.JdbcWriterRepositoryImpl;

import java.util.List;

public class WriterService {
    private final WriterRepository writerRepository;

    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }
    public WriterService(){
        this.writerRepository = new JdbcWriterRepositoryImpl();
    }

    public Writer updateWriter(Writer updateWriter) {
        updateWriter.setWriterStatus(WriterStatus.UNDER_REVIEW);
            return writerRepository.update(updateWriter);
    }

    public Writer createWriter(Writer writer) {
        writer.setWriterStatus(WriterStatus.ACTIVE);
        return writerRepository.save(writer);
    }
    public void deleteWriter(Long id){
        writerRepository.deleteById(id);

    }
    public List<Writer> getAll(){
        return writerRepository.getAll();
    }
    public Writer getWriterById(Long id){
         return writerRepository.getById(id);
    }
}

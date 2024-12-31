package net.artem.javacore.jdbc.application.repository.jdbc;

import net.artem.javacore.jdbc.application.enums.WriterStatus;
import net.artem.javacore.jdbc.application.model.Label;
import net.artem.javacore.jdbc.application.model.Post;
import net.artem.javacore.jdbc.application.model.Writer;
import net.artem.javacore.jdbc.application.repository.WriterRepository;
import net.artem.javacore.jdbc.application.utils.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcWriterRepositoryImpl implements WriterRepository {

    private static final String GET_WRITER_BY_ID_SQL = "SELECT * FROM writers WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM writers";
    private static final String INSERT_SQL = "INSERT INTO writers (firstname, lastname, writer_status) VALUES (?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE writers SET firstname = ?, lastname = ?, writer_status = ? WHERE id = ?";
    private static final String DELETE_BY_ID_SQL = "UPDATE writers SET writer_status = ? WHERE id = ?";

    @Override
    public Writer getById(Long id) {

        List<Writer> writers = new ArrayList<>();


        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(GET_WRITER_BY_ID_SQL) ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Long writerId = resultSet.getLong(1);
            String firstName = resultSet.getString(2);
            String lastName = resultSet.getString(3);

            WriterStatus writerStatus = WriterStatus.valueOf(resultSet.getString(5));

            return Writer.builder()
                    .id(writerId)
                    .firstName(firstName)
                    .lastName(lastName)

                    .writerStatus(writerStatus)
                    .build();

        } catch (SQLException e) {
            return null;
        }

    }

    @Override
    public List<Writer> getAll() {
        return null;
    }

    @Override
    public Writer save(Writer writer) {
        return null;
    }

    @Override
    public Writer update(Writer writer) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}

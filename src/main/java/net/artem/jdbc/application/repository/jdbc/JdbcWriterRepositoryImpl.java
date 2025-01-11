package net.artem.jdbc.application.repository.jdbc;

import lombok.SneakyThrows;
import net.artem.jdbc.application.controller.PostController;
import net.artem.jdbc.application.enums.PostStatus;
import net.artem.jdbc.application.enums.WriterStatus;
import net.artem.jdbc.application.model.Label;
import net.artem.jdbc.application.model.Post;
import net.artem.jdbc.application.model.Writer;
import net.artem.jdbc.application.repository.WriterRepository;
import net.artem.jdbc.application.utils.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class JdbcWriterRepositoryImpl implements WriterRepository {

    private static final String GET_WRITER_BY_ID_SQL = "SELECT * FROM writers WHERE id = ?";
    private static final String SELECT_ALL_SQL = "SELECT * FROM writers";
    private static final String INSERT_SQL = "INSERT INTO writers (firstname, lastname, writer_status) VALUES ( ?, ?,?)";
    private static final String UPDATE_SQL = "UPDATE writers SET firstname = ?, lastname = ?, writer_status = ? WHERE id = ?";
    private static final String DELETE_BY_ID_SQL = "UPDATE writers SET writer_status = ? WHERE id = ?";

    @SneakyThrows
    @Override
    public Writer getById(Long id) {
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(GET_WRITER_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Post> posts = new ArrayList<>();
            while (resultSet.next()) {

                Long writerId = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                WriterStatus writerStatus = WriterStatus.valueOf(resultSet.getString(4));

                return Writer.builder()
                        .id(writerId)
                        .firstName(firstName)
                        .lastName(lastName)
                        .writerStatus(writerStatus)
                        .build();
            }

        }
        return (Writer) Collections.emptyList();
    }

    @SneakyThrows
    @Override
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(SELECT_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Post> posts = new ArrayList<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                WriterStatus writerStatus = WriterStatus.valueOf(resultSet.getString(4));

                writers.add(Writer.builder()
                        .id(id)
                        .firstName(firstName)
                        .lastName(lastName)
                        .writerStatus(writerStatus)
                        .build());
            }

        }


        return writers;
    }

    @SneakyThrows
    @Override
    public Writer save(Writer writer) {

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatementWithKey(INSERT_SQL)) {
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.setString(3, writer.getWriterStatus().name());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                writer.setId(generatedKeys.getLong(1));
            } else {
                throw new RuntimeException("Создание не удалось");

            }

            return writer;
        }


    }

    @SneakyThrows
    public List<Post> getPostsForWriter(Long writerId) {
        String sql = "SELECT * FROM posts WHERE writer_id = ?";

        List<Post> posts = new ArrayList<>();

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(sql)) {
            preparedStatement.setLong(1, writerId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String content = resultSet.getString(2);
                Date created = resultSet.getDate(3);
                Date updated = resultSet.getDate(4);
                Long idW = resultSet.getLong(5);
                PostStatus postStatus = PostStatus.valueOf(resultSet.getString(6));

                posts.add(Post.builder()
                        .id(id)
                        .content(content)
                        .created(created)
                        .updated(updated)
                        .id(idW)
                        .postStatus(postStatus)
                        .build());
            }
        }
        System.out.println(posts);
        return posts;
    }

    @SneakyThrows
    @Override
    public Writer update(Writer updateWriter) {
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, updateWriter.getFirstName());
            preparedStatement.setString(2, updateWriter.getLastName());
            preparedStatement.setString(3, updateWriter.getWriterStatus().name());
            preparedStatement.setLong(4, updateWriter.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new RuntimeException("Обновление Writer неудалось");
            }
        }


        return updateWriter;
    }

    @SneakyThrows
    @Override
    public void deleteById(Long id) {

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatementWithKey(DELETE_BY_ID_SQL)) {
            preparedStatement.setString(1, WriterStatus.DELETED.name());
            preparedStatement.setLong(2, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Не удалось удалить");
            }
        }

    }


}

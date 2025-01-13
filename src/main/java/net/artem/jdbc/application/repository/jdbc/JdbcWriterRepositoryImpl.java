package net.artem.jdbc.application.repository.jdbc;

import lombok.SneakyThrows;
import net.artem.jdbc.application.enums.PostStatus;
import net.artem.jdbc.application.enums.WriterStatus;
import net.artem.jdbc.application.model.Label;
import net.artem.jdbc.application.model.Post;
import net.artem.jdbc.application.model.Writer;
import net.artem.jdbc.application.repository.WriterRepository;
import net.artem.jdbc.application.utils.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class JdbcWriterRepositoryImpl implements WriterRepository {

    private static final String GET_WRITER_BY_ID_SQL = "select *from writers w left join posts p on w.id=p.id WHERE w.id = ?";
    private static final String SELECT_ALL_SQL = "select *from writers w left join posts p on w.id=p.id";
    private static final String INSERT_SQL = "INSERT INTO writers (firstname, lastname, writer_status) VALUES (  ?,?,?)";
    private static final String UPDATE_SQL = "UPDATE writers SET firstname = ?, lastname = ?, writer_status = ? WHERE id = ?";
    private static final String DELETE_BY_ID_SQL = "UPDATE writers SET writer_status = ? WHERE id = ?";
    private static final String INSERT_WRITER_POST_SQL = "INSERT INTO writer_posts (writer_id, post_id) VALUES (?, ?)";

    @SneakyThrows
    @Override
    public Writer getById(Long id) {
        Writer result = null;
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(GET_WRITER_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Post> posts = new ArrayList<>();
            while (resultSet.next()) {

                Long writerId = resultSet.getLong(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                WriterStatus writerStatus = WriterStatus.valueOf(resultSet.getString(4));

                resultSet.getLong(5);
                Post post = new Post();

                if (post == null) {

                    post.setId(resultSet.getLong(5));
                    post.setContent(resultSet.getString(6));
                    post.setCreated(new Date(7));
                    post.setUpdated(new Date(8));
                    post.setPostStatus(PostStatus.valueOf(resultSet.getString(10)));

                    posts.add(post);
                }
                result = new Writer();
                result.setId(writerId);
                result.setFirstName(firstName);
                result.setLastName(lastName);
                result.setWriterStatus(writerStatus);


            }
            result.setPosts(posts);

        }
        return result;
    }

    @SneakyThrows
    @Override
    public List<Writer> getAll() {

        List<Writer> writers = new ArrayList<>();

        Map<Long, List<Post>> writerIdPostsListMap = new HashMap<>();
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(SELECT_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            {
                while (resultSet.next()) {

                    Long writerId = resultSet.getLong(1);
                    String firstName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);
                    WriterStatus writerStatus = WriterStatus.valueOf(resultSet.getString(4));


                    if (Objects.nonNull(resultSet.getString(5))) {
                        Post post = new Post();
                        post.setId(resultSet.getLong(5));
                        post.setContent(resultSet.getString(6));
                        post.setCreated(resultSet.getDate(7));
                        post.setUpdated(resultSet.getDate(8));
                        post.setPostStatus(PostStatus.valueOf(resultSet.getString(10)));

                        if (!writerIdPostsListMap.containsKey(writerId)) {
                            List<Post> currentPostsList = new ArrayList<>();
                            writerIdPostsListMap.put(writerId, currentPostsList);
                        }

                        writerIdPostsListMap.get(writerId).add(post);
                    }

                    if (writers.stream().noneMatch(p -> p.getId().equals(writerId))) {
                        writers.add(Writer.builder()
                                .id(writerId)
                                .firstName(firstName)
                                .lastName(lastName)
                                .writerStatus(writerStatus)
                                .build());

                    }
                }
            }
        }

        //TODO: добавить каждому посту его лейблы из нашей мапы

        writers.forEach(writer -> {
            List<Post> posts = writerIdPostsListMap.get(writer.getId());
            if (Objects.nonNull(posts)) {
                writer.setPosts(posts);
            }
        });

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

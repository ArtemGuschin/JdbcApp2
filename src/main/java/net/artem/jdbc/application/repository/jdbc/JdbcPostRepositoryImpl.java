package net.artem.jdbc.application.repository.jdbc;

import lombok.SneakyThrows;
import net.artem.jdbc.application.enums.LabelStatus;
import net.artem.jdbc.application.enums.PostStatus;
import net.artem.jdbc.application.model.Label;
import net.artem.jdbc.application.model.Post;
import net.artem.jdbc.application.model.Writer;
import net.artem.jdbc.application.repository.PostRepository;
import net.artem.jdbc.application.utils.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class JdbcPostRepositoryImpl implements PostRepository {


    private static final String GET_POST_bY_ID_SQL = """
            SELECT p.id, p.content, p.created, p.updated, p.post_status,
              w.id as writer_id, w.firstname, w.lastname, l.id as label_id, l.name
              from posts p
              left join post_labels pl on p.id = pl.post_id
              left join labels l on pl.label_id = l.id
              left join writers w on w.id = p.writer_id
              where p.id = ? """;


    private static final String SELECT_ALL_SQL = """
            SELECT p.id, p.content, p.created, p.updated, p.post_status,
            w.id as writer_id, w.firstname, w.lastname, l.id as label_id, l.name
            from posts p
            left join post_labels pl on p.id = pl.post_id left join labels l on pl.label_id = l.id
            left join writers w on w.id = p.writer_id
            """;

    private static final String INSERT_SQL = "INSERT INTO posts (content, created, updated, post_status, writer_id) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_SQL = "UPDATE posts SET content = ?, updated = ?, post_status = ? WHERE id = ?";
    private static final String DELETE_BY_ID_SQL = "UPDATE posts SET post_status = ? WHERE id = ?";

    private static final String INSERT_POST_LABEL_SQL = "INSERT INTO post_labels (post_id, label_id) VALUES (?, ?)";
    private static final String DELETE_POST_LABEL_SQL = "DELETE FROM post_labels WHERE post_id = ?";


    @Override
    public Post getById(Long id) {

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(GET_POST_bY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Label> labels = new ArrayList<>();

            while (resultSet.next()) {

                Long postId = resultSet.getLong(1);
                String content = resultSet.getString(2);
                Date created = resultSet.getDate(3);
                Date updated = resultSet.getDate(4);
                PostStatus postStatus = PostStatus.valueOf(resultSet.getString(5));

                Writer writer = new Writer();
                writer.setId(resultSet.getLong(6));
                writer.setFirstName(resultSet.getString(7));
                writer.setLastName(resultSet.getString(8));


                while (resultSet.next()) {
                    Label label = new Label();
                    label.setId(resultSet.getLong(9));
                    label.setName(resultSet.getString(10));

                    labels.add(label);
                }

                return Post.builder()
                        .id(postId)
                        .content(content)
                        .created(created)
                        .updated(updated)
                        .writer(writer)
                        .labels(labels)
                        .build();

            }

        } catch (SQLException e) {
            return null;
        }

        return null;
    }

    @SneakyThrows
    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(SELECT_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Label> labels = new ArrayList<>();
            {
                while (resultSet.next()) {

                    Long postId = resultSet.getLong(1);
                    String content = resultSet.getString(2);
                    Date created = resultSet.getDate(3);
                    Date updated = resultSet.getDate(4);
                    PostStatus postStatus = PostStatus.valueOf(resultSet.getString(5));

                    Writer writer = new Writer();
                    writer.setId(resultSet.getLong(6));
                    writer.setFirstName(resultSet.getString(7));
                    writer.setLastName(resultSet.getString(8));


                    Label label = new Label();
                    label.setId(resultSet.getLong(9));
                    label.setName(resultSet.getString(10));


                    labels.add(label);

                    return Collections.singletonList(Post.builder()
                            .id(postId)
                            .content(content)
                            .created(created)
                            .updated(updated)
                            .writer(writer)
                            .labels(labels)
                            .build());

                }
            }
        }


        return posts;
    }

    @SneakyThrows
    @Override
    //content, created, updated, post_status, writer_id
    public Post save(Post post) {
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatementWithKey(INSERT_SQL)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setTimestamp(2, new Timestamp(post.getCreated().getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(post.getUpdated().getTime()));
            preparedStatement.setString(4, post.getPostStatus().name());
            preparedStatement.setLong(5, post.getWriter().getId());


            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                post.setId(generatedKeys.getLong(1));
            } else {
                throw new RuntimeException("Создание не удалось");
            }
            addLabelToPost(post.getId(), post.getLabels());

        }

        return post;
    }

    @SneakyThrows
    private void addLabelToPost(Long postId, List<Label> labels) {
        if (labels == null) {
            return;
        }

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(INSERT_POST_LABEL_SQL)) {
            for (Label label : labels) {
                if (label.getId() != null) {
                    preparedStatement.setLong(1, postId);
                    preparedStatement.setLong(2, label.getId());

                    preparedStatement.executeUpdate();
                }
            }
        }
    }

    @SneakyThrows
    @Override
    public Post update(Post updatePost) {
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatementWithKey(UPDATE_SQL)) {
            preparedStatement.setString(1, updatePost.getContent());
            preparedStatement.setTimestamp(2, new Timestamp(updatePost.getUpdated().getTime()));
            preparedStatement.setString(3, updatePost.getPostStatus().name());
            preparedStatement.setLong(4, updatePost.getId());

            int rowCount = preparedStatement.executeUpdate();

            if (rowCount == 0) {
                throw new RuntimeException("Обновление поста не удалось, ни одна запись не была изменена.");
            }

            deleteLabelsForPost(updatePost.getId());
            addLabelToPost(updatePost.getId(), updatePost.getLabels());

        }
        return updatePost;
    }

    @SneakyThrows
    @Override
    public void deleteById(Long id) {
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(DELETE_BY_ID_SQL)) {
            preparedStatement.setString(1, PostStatus.DELETED.name());
            preparedStatement.setLong(2, id);

            int rowCount = preparedStatement.executeUpdate();

            if (rowCount == 0) {
                throw new RuntimeException("Пост с ID " + id + " не найден.");
            }
        }
    }

    @SneakyThrows
    private void deleteLabelsForPost(Long postId) {
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(DELETE_POST_LABEL_SQL)) {
            preparedStatement.setLong(1, postId);

            preparedStatement.executeUpdate();
        }
    }
}


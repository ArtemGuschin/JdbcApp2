package net.artem.jdbc.application.repository.jdbc;

import net.artem.jdbc.application.enums.LabelStatus;
import net.artem.jdbc.application.model.Label;
import net.artem.jdbc.application.repository.LabelRepository;
import net.artem.jdbc.application.utils.JdbcUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JdbcLabelRepositoryImpl implements LabelRepository {
    private static final String GET_LABEL_bY_ID_SQL = "SELECT*FROM labels WHERE id =?";
    private static final String SELECT_ALL_SQL = "SELECT* FROM labels";
    private static final String INSERT_SQL = " INSERT INTO labels (name ,label_status) VALUES(?,?)";
    private static final String UPDATE_SQL = "UPDATE   labels SET name = ? , label_status =? WHERE id =?";
    private static final String DELETE_BY_ID_SQL = "UPDATE labels SET  label_status = ? WHERE id = ?";


    @Override
    public Label getById(Long id) {
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(GET_LABEL_bY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            Long labelId = resultSet.getLong(1);
            String name = resultSet.getString(2);
            LabelStatus labelStatus = LabelStatus.valueOf(resultSet.getString(3));

            return Label.builder()
                    .id(labelId)
                    .name(name)
                    .labelStatus(labelStatus)
                    .build();

        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Label> getAll() {
        List<Label> labels = new ArrayList<>();

        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatement(SELECT_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Long labelId = resultSet.getLong(1);
                String name = resultSet.getString(2);
                LabelStatus labelStatus = LabelStatus.valueOf(resultSet.getString(3));

                labels.add(Label.builder()
                        .id(labelId)
                        .name(name)
                        .labelStatus(labelStatus)
                        .build());
            }

        } catch (SQLException e) {
            return Collections.emptyList();
        }
        return labels;
    }

    @Override
    public Label save(Label label) {
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatementWithKey(INSERT_SQL)) {
            preparedStatement.setString(1, label.getName());
            preparedStatement.setString(2, label.getLabelStatus().name());

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                label.setId(generatedKeys.getLong(1));
            } else {
                throw new RuntimeException("Создание не удалось");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return label;
    }

    @Override
    public Label update(Label label) {
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatementWithKey(UPDATE_SQL)) {
            preparedStatement.setString(1, label.getName());
            preparedStatement.setString(2, label.getLabelStatus().name());
            preparedStatement.setLong(3, label.getId());


            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Обновление ярлыка не удалось, затронуты ноль строк.");
            }
        } catch (SQLException e) {

            throw new RuntimeException("Ошибка базы данных при обновлении ярлыка.", e);
        }
        return label;
    }


    @Override
    public void deleteById(Long id) {
        try (PreparedStatement preparedStatement = JdbcUtils.getPreparedStatementWithKey(DELETE_BY_ID_SQL)) {
            preparedStatement.setString(1, LabelStatus.DELETED.name());
            preparedStatement.setLong(2, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("Обновление статуса ярлыка не удалось, затронуты ноль строк.");
            }
        } catch (SQLException e) {

            throw new RuntimeException(" Ошибка базы данных при обновлении статуса ярлыка с ID " + id, e);
        }
    }
}

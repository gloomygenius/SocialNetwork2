package com.socialnetwork.dao.h2;

import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.connection_pool.ConnectionPoolException;
import com.socialnetwork.dao.DialogDao;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.Dialog;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Vasiliy Bobkov on 15.11.2016.
 */
@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@Log4j
@RequiredArgsConstructor
public class DialogDaoImpl implements DialogDao {
    private final ConnectionPool connectionPool;

    @Override
    public Dialog getPrivateDialog(long from, long to) throws DaoException {

        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT d.id, d.creator, d.description, d.last_update, p.user_id  FROM Dialogues d, Dialog_Participants p " +
                             "WHERE d.is_private = TRUE AND d.id = p.dialog_id AND ((d.creator = ? AND p.user_id = ?) " +
                             "OR (d.creator = ? AND p.user_id = ?))"
             )) {
            preparedStatement.setLong(1, from);
            preparedStatement.setLong(2, to);
            preparedStatement.setLong(3, to);
            preparedStatement.setLong(4, from);
            @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                createPrivateDialog(from, to);
                return getPrivateDialog(from, to);
            } else {
                Set<Long> partSet = new HashSet<>();
                partSet.add(resultSet.getLong("user_id"));
                return new Dialog(
                        resultSet.getLong("id"),
                        resultSet.getLong("creator"),
                        resultSet.getString("description"),
                        partSet,
                        true,
                        resultSet.getTimestamp("last_update").toLocalDateTime()
                );
            }

        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error in getPrivateDialog() ", e);
        }
    }

    @Override
    public Set<Dialog> getLastDialogues(long participant, int limit, int offset) throws DaoException {
        Set<Dialog> dialogSet = new TreeSet<>();
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement selectStatement = connection.prepareStatement(
                     "SELECT DISTINCT d.id, d.last_update FROM Dialogues d, Dialog_Participants dp  " +
                             "WHERE  (d.id=dp.dialog_id AND dp.user_id=?) OR d.creator= ? " +
                             "ORDER BY d.last_update DESC LIMIT ? OFFSET ? ")
        ) {
            selectStatement.setLong(1, participant);
            selectStatement.setLong(2, participant);
            selectStatement.setInt(3, limit);
            selectStatement.setInt(4, offset);
            @Cleanup ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                dialogSet.add(getDialog(resultSet.getLong("id")));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error in DialogDao");
        }
        return dialogSet;
    }

    @Override
    public Dialog getDialog(long id) throws DaoException {
        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement()) {
            @Cleanup ResultSet participantRS = statement.executeQuery(
                    "SELECT user_id FROM Dialog_Participants WHERE dialog_id=" + id);
            Set<Long> userSet = new HashSet<>();
            while (participantRS.next()) userSet.add(participantRS.getLong("user_id"));
            @Cleanup ResultSet dialogRS = statement.executeQuery(
                    "SELECT creator, description, is_private, last_update FROM Dialogues WHERE id=" + id);
            if (dialogRS.next()) {
                return new Dialog(
                        id,
                        dialogRS.getLong("creator"),
                        dialogRS.getString("description"),
                        userSet,
                        dialogRS.getBoolean("is_private"),
                        dialogRS.getTimestamp("last_update").toLocalDateTime()
                );
            } else throw new DaoException("Error in DialogDao ");
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error in DialogDao ", e);
        }
    }

    @Override
    public void createPrivateDialog(long creator, long participant) throws DaoException {
        try (Connection connection = connectionPool.takeConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement prepStateForDialogues = connection.prepareStatement(
                    "INSERT INTO Dialogues(creator, last_update) VALUES (?,?);")) {
                prepStateForDialogues.setLong(1, creator);
                prepStateForDialogues.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                prepStateForDialogues.executeUpdate();
                ResultSet generatedKeys = prepStateForDialogues.getGeneratedKeys();
                if (generatedKeys.next()) {
                    @Cleanup PreparedStatement prepStateForParticipant = connection.prepareStatement(
                            "INSERT INTO Dialog_Participants(dialog_id, user_id) VALUES (?,?)");
                    prepStateForParticipant.setLong(1, generatedKeys.getLong(1));
                    prepStateForParticipant.setLong(2, participant);
                    prepStateForParticipant.executeUpdate();
                    connection.commit();
                } else
                    throw new DaoException("Error in DialogDao ");
            } catch (DaoException e) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw e;
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error in DialogDao ", e);
        }
    }

    @Override
    public void updateTime(long dialog, LocalDateTime time) throws DaoException {
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Dialogues SET last_update=? WHERE id=?;"
             )) {
            statement.setTimestamp(1, Timestamp.valueOf(time));
            statement.setLong(2, dialog);
            statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error in DialogDao ", e);
        }
    }
}
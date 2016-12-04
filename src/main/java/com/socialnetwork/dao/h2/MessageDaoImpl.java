package com.socialnetwork.dao.h2;

import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.connection_pool.ConnectionPoolException;
import com.socialnetwork.dao.MessageDao;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.Message;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Vasiliy Bobkov on 15.11.2016.
 */
@Log4j
@RequiredArgsConstructor
public class MessageDaoImpl implements MessageDao {
    private final ConnectionPool connectionPool;

    @Override
    public Set<Message> getMessages(long dialog, int limit, int offSet) throws DaoException {
        Set<Message> messageSet = new TreeSet<>();
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement selectStatement = connection.prepareStatement(
                     "SELECT id, sender, message, msg_time, is_read FROM Messages WHERE dialog=? ORDER BY msg_time DESC LIMIT ? OFFSET ? ");
        ) {
            selectStatement.setLong(1, dialog);
            selectStatement.setInt(2, limit);
            selectStatement.setInt(3, offSet);
            @Cleanup ResultSet resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                messageSet.add(new Message(
                        resultSet.getLong("id"),
                        resultSet.getLong("sender"),
                        dialog,
                        resultSet.getString("message"),
                        resultSet.getTimestamp("msg_time").toLocalDateTime(),
                        resultSet.getBoolean("is_read")
                ));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("MessageDaoImpl error", e);
        }
        return messageSet;
    }

    @Override
    public void sendMessage(long sender, long dialog, String message) throws DaoException {
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO Messages (sender, dialog, message, msg_time) VALUES (?, ?, ?, ?)"
             )) {
            preparedStatement.setLong(1, sender);
            preparedStatement.setLong(2, dialog);
            preparedStatement.setString(3, message);
            preparedStatement.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("MessageDaoImpl error", e);
        }
    }
}

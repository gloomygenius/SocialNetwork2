package com.socialnetwork.dao.h2;

import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.connection_pool.ConnectionPoolException;
import com.socialnetwork.dao.RelationDao;
import com.socialnetwork.dao.enums.RelationType;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.Relation;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.*;

import static com.socialnetwork.dao.enums.RelationType.*;

/**
 * Created by Vasiliy Bobkov on 08.11.2016.
 */
@Log4j
@RequiredArgsConstructor
public class RelationDaoImpl implements RelationDao {
    private final ConnectionPool connectionPool;

    @Override
    public Relation getFriends(long userId) throws DaoException {
        String sqlRequst = String.format(
                "SELECT recipient_id FROM relations WHERE sender_id=%d AND relation_type=%d", userId, FRIEND.ordinal());
        Set<Long> set = getIdSet(sqlRequst, "recipient_id");
        sqlRequst = String.format(
                "SELECT sender_id FROM relations WHERE recipient_id=%d AND relation_type=%d", userId, FRIEND.ordinal());
        set.addAll(getIdSet(sqlRequst, "sender_id"));
        return new Relation(
                userId,
                set,
                FRIEND.ordinal()
        );
    }

    @Override
    public Relation getIncoming(long userId) throws DaoException {
        String sqlRequest = String.format(
                "SELECT sender_id FROM relations WHERE recipient_id=%d AND relation_type=%d", userId, REQUEST.ordinal());
        Set<Long> set = getIdSet(sqlRequest, "sender_id");
        return new Relation(
                userId,
                set,
                INCOMING.ordinal()
        );
    }

    @Override
    public Relation getRequest(long userId) throws DaoException {
        String sqlRequst = String.format(
                "SELECT recipient_id FROM relations WHERE sender_id=%d AND relation_type=%d", userId, REQUEST.ordinal());
        Set<Long> set = getIdSet(sqlRequst, "recipient_id");
        return new Relation(
                userId,
                set,
                REQUEST.ordinal()
        );
    }

    @Override
    public int getRelationBetween(long fromId, long toId) throws DaoException {
        int relationType = NEUTRAL.ordinal();
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT relation_type FROM relations WHERE sender_id=? AND recipient_id=?"
             )) {
            statement.setLong(1, fromId);
            statement.setLong(2, toId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) relationType = resultSet.getInt("relation_type");
            }
            if (relationType == NEUTRAL.ordinal()) {
                statement.setLong(1, toId);
                statement.setLong(2, fromId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        relationType = resultSet.getInt("relation_type");
                        if (relationType == REQUEST.ordinal()) relationType = INCOMING.ordinal();
                    }
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("RelationDaoImpl error", e);
        }
        return relationType;
    }

    @Override
    public void add(long senderId, long recipientId, RelationType relationType) throws DaoException {
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Relations (sender_id, recipient_id, relation_type) VALUES (?,?,?)"
             )) {
            connection.setAutoCommit(false);
            if (relationType == INCOMING) {
                long buf = senderId;
                senderId = recipientId;
                recipientId = buf;
                relationType = REQUEST;
            }
            if (relationType == FRIEND) {
                if (getRelationBetween(senderId, recipientId) != INCOMING.ordinal()){
                    throw new DaoException("Add friends without right");
                }
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM Relations WHERE (sender_id=? AND recipient_id=?) " +
                            "OR (sender_id=? AND recipient_id=?)"
            )) {
                preparedStatement.setLong(1, senderId);
                preparedStatement.setLong(2, recipientId);
                preparedStatement.setLong(3, recipientId);
                preparedStatement.setLong(4, senderId);
                preparedStatement.executeUpdate();
            }
            statement.setLong(1, senderId);
            statement.setLong(2, recipientId);
            statement.setInt(3, relationType.ordinal());
            statement.execute();
            connection.commit();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("RelationDaoImpl error", e);
        }
    }

    @Override
    public void update(long senderId, long recipientId, RelationType relationType) throws DaoException {
        add(senderId, recipientId, relationType);
    }

    @Override
    public void remove(long senderId, long recipientId, RelationType relationType) throws DaoException {
        String sqlRequest;

        if (relationType == INCOMING) {
            long buf = senderId;
            senderId = recipientId;
            recipientId = buf;
            relationType = REQUEST;
        }

        if (relationType == FRIEND) {
            try (Connection connection = connectionPool.takeConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "SELECT relation_type FROM Relations WHERE (sender_id=? AND recipient_id=?) " +
                                 "OR (sender_id=? AND recipient_id=?)")) {
                preparedStatement.setLong(1, recipientId);
                preparedStatement.setLong(2, senderId);
                preparedStatement.setLong(3, senderId);
                preparedStatement.setLong(4, recipientId);
                @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int relationTypeFromRS = resultSet.getInt("relation_type");
                    if (relationTypeFromRS != FRIEND.ordinal())
                        throw new DaoException("Remove friends without right");
                } else throw new DaoException("Remove friends without right");
            } catch (SQLException | ConnectionPoolException e) {
                throw new DaoException("RelationDaoImpl error", e);
            }

            sqlRequest = String.format("DELETE FROM Relations WHERE ((sender_id=%d AND recipient_id=%d) " +
                            "OR (sender_id=%d AND recipient_id=%d)) AND relation_type=%d",
                    senderId, recipientId, recipientId, senderId, relationType.ordinal());
        } else sqlRequest = String.
                format("DELETE FROM Relations WHERE sender_id=%d AND recipient_id=%d AND relation_type=%d",
                        senderId, recipientId, relationType.ordinal());

        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate(sqlRequest);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("RelationDaoImpl error", e);
        }
    }

    private Set<Long> getIdSet(String sqlRequest, String field) throws DaoException {
        Set<Long> idSet = new HashSet<>();
        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlRequest)) {
            while (resultSet.next())
                idSet.add(resultSet.getLong(field));
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("RelationDaoImpl error", e);
        }
        return idSet;
    }
}
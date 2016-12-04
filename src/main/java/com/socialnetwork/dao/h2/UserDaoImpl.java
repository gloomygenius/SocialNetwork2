package com.socialnetwork.dao.h2;

import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.connection_pool.ConnectionPoolException;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Vasiliy Bobkov on 06.11.2016.
 */
@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
@Log4j
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final ConnectionPool connectionPool;

    @Override
    public Optional<User> getById(long id) throws DaoException {
        String sqlReqest = "SELECT id, email, password, first_name, last_name, gender, role FROM Users WHERE id='" + id + "'";
        return getByAny(sqlReqest);
    }

    @Override
    public Optional<User> getByEmail(String email) throws DaoException {
        String sqlReqest = "SELECT id, email, password, first_name, last_name,  gender, role FROM Users WHERE email = '"
                .concat(email)
                .concat("'");
        return getByAny(sqlReqest);
    }

    @Override
    public Set<Long> getByNames(String firstName, String lastName) throws DaoException {
        String sqlRequest = "SELECT id FROM Users WHERE "
                + "first_name='" + firstName + "' AND "
                + "last_name='" + lastName + "'";
        Set<Long> idSet = new HashSet<>();
        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlRequest)) {
            while (resultSet.next()) {
                idSet.add(resultSet.getLong("id"));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("UserDaoImpl error",e);
        }
        return idSet;
    }

    @Override
    public void add(User user) throws DaoException {
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Users (email, password, first_name, last_name, gender, role) VALUES (?, ?, ?, ?, ?, ?)"
             )) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setInt(5, user.getGender());
            statement.setInt(6, user.getRole());
            statement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("UserDaoImpl error",e);
        }
    }

    @Override
    public void update(User user) throws DaoException {
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Users SET email=?, password=?, first_name=?, last_name=?, gender=?, role=? WHERE id=?"
             )) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setInt(5, user.getGender());
            statement.setInt(6, user.getRole());
            statement.setLong(7, user.getId());
            statement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("UserDaoImpl error",e);
        }
    }

    @Override
    public void remove(long id) throws DaoException {
        try (Connection connection = connectionPool.takeConnection()) {
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                statement.addBatch("DELETE FROM Relations WHERE sender_id='"+id+"'");
                statement.addBatch("DELETE FROM Relations WHERE recipient_id='"+id+"'");
                statement.addBatch("DELETE FROM Messages WHERE sender='"+id+"'");
                statement.addBatch("DELETE FROM Dialogues WHERE creator='"+id+"'");
                statement.addBatch("DELETE FROM Users WHERE id='" + id + "'");
                statement.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("UserDaoImpl error",e);
        }
    }


    private User createUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("id"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getInt("gender"),
                resultSet.getInt("role")
        );
    }

    private Optional<User> getByAny(String sqlRequest) throws DaoException {
        Optional<User> user = Optional.empty();
        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlRequest)) {
            if (resultSet.next()) {
                user = Optional.of(createUserFromResultSet(resultSet));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("UserDaoImpl error",e);
        }
        return user;
    }
}

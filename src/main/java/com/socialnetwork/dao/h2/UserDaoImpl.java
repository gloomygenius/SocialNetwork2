package com.socialnetwork.dao.h2;

import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.connection_pool.ConnectionPoolException;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

/**
 * Created by Vasiliy Bobkov on 06.11.2016.
 */
@Log4j
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final ConnectionPool connectionPool;

    @Override
    public Optional<User> getById(long id) {
        Optional<User> user = Optional.empty();
        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT id, email, password, first_name, last_name, gender FROM Users WHERE id='" + id + "'"
             )) {
            if (resultSet.next()) {
                user = Optional.of(createUserFromResultSet(resultSet));
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.warn("Error requesting data from the database", e);
        }
        return user;
    }

    User createUserFromResultSet(ResultSet resultSet) {
        User user = null;
        try {
            user = new User(
                    resultSet.getLong("id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getInt("gender")
            );
        } catch (SQLException e) {
            log.error("Error of creating user from result set", e);
        }
        return user;
    }

    LocalDate convertDateToLocalDate(Date date) {
        if (date == null) return null;
        LocalDate localDate =
                Instant.ofEpochMilli(date.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
        return localDate;
    }

    @Override
    public Optional<User> getByEmail(String email) throws DaoException {
        Optional<User> user = Optional.empty();
        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT id, email, password, first_name, last_name,  gender FROM Users WHERE email = '"
                             .concat(email)
                             .concat("'")
             )) {
            if (resultSet.next()) {
                user = Optional.of(createUserFromResultSet(resultSet));
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.error("Error requesting data from the database", e);
            throw new DaoException();
        }
        return user;
    }

    @Override
    public void add(User user) throws DaoException {
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Users (email, password, first_name, last_name, gender) VALUES (?, ?, ?, ?, ?)"
             )) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setInt(5, user.getGender());
            statement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            log.error("Error add new user", e);
            throw new DaoException();
        }
    }

    @Override
    public void update(User user) throws DaoException {
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Users SET email=?, password=?, first_name=?, last_name=?, gender=? WHERE id=?"
             )) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            statement.setInt(5, user.getGender());
            statement.setLong(6, user.getId());
            statement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            log.error("Error update user", e);
            throw new DaoException();
        }
    }

    @Override
    public void remove(long id) throws DaoException {
        try (Connection connection = connectionPool.takeConnection()) {
            try (Statement statement = connection.createStatement()) {
                connection.setAutoCommit(false);
                statement.addBatch("DELETE FROM Users WHERE id='" + id + "'");
                //statement.addBatch("DELETE FROM Users WHERE id='"+id+"'");
                // TODO: 07.11.2016 удалить из других таблиц
                statement.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error("Error remove user", e);
                throw new DaoException();
            }
        } catch (SQLException | ConnectionPoolException e) {
            log.error("Error remove user", e);
            throw new DaoException();
        }
    }
}
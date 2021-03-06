package com.socialnetwork.dao.h2;

import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.connection_pool.ConnectionPoolException;
import com.socialnetwork.dao.ProfileDao;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.Profile;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

@Log4j
@RequiredArgsConstructor
public class ProfileDaoImpl implements ProfileDao {
    private final ConnectionPool connectionPool;

    @Override
    public Optional<Profile> getByUserId(long userId) throws DaoException {
        Optional<Profile> profile = Optional.empty();
        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT id, telephone, birthday, country, city, university,team, position, about " +
                             "FROM Users WHERE id='" + userId + "'"
             )) {
            if (resultSet.next()) {
                profile = Optional.of(createProfileFromResultSet(resultSet));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error in ProfileDaoImpl",e);
        }
        return profile;
    }

    private Profile createProfileFromResultSet(ResultSet resultSet) throws DaoException {
        Profile profile = null;
        try {
            Date date = resultSet.getDate("birthday");
            LocalDate birthday = null;
            if (date != null) birthday = date.toLocalDate();
            profile = new Profile(
                    resultSet.getLong("id"),
                    resultSet.getString("telephone"),
                    birthday,
                    resultSet.getString("country"),
                    resultSet.getString("city"),
                    resultSet.getString("university"),
                    resultSet.getInt("team"),
                    resultSet.getInt("position"),
                    resultSet.getString("about")
            );
        } catch (SQLException e) {
            throw new DaoException("Error in ProfileDaoImpl",e);
        }
        return profile;
    }

    @Override
    public void add(Profile profile) throws DaoException {
        try (Connection connection = connectionPool.takeConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Users SET telephone=?, birthday=?, country=?, city=?, university=?, team=?, " +
                             "position=?, about=? WHERE id=? "
             )) {
            statement.setString(1, profile.getTelephone());
            if (profile.getBirthday() == null) statement.setDate(2, null);
            else statement.setDate(2, Date.valueOf(profile.getBirthday()));
            statement.setString(3, profile.getCountry());
            statement.setString(4, profile.getCity());
            statement.setString(5, profile.getUniversity());
            statement.setInt(6, profile.getTeam());
            statement.setInt(7, profile.getPosition());
            statement.setString(8, profile.getAbout());
            statement.setLong(9, profile.getId());

            statement.execute();
        } catch (SQLException | ConnectionPoolException e) {
            throw new DaoException("Error in ProfileDaoImpl",e);
        }
    }

    @Override
    public void update(Profile profile) throws DaoException {
        add(profile);
    }

    @Override
    public void remove(long id) throws DaoException {
        Profile profile = new Profile(id, null, null, null, null, null, 0, 0, null);
        add(profile);
    }
}
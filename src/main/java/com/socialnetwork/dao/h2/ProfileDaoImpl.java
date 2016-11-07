package com.socialnetwork.dao.h2;

import com.socialnetwork.dao.ProfileDao;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.Profile;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

/**
 * Created by Vasiliy Bobkov on 07.11.2016.
 */
public class ProfileDaoImpl implements ProfileDao {

    @Override
    public Optional<Profile> getByUserId(long id) throws DaoException {
        return null;
    }

    @Override
    public void add(Profile profile) throws DaoException {

    }

    @Override
    public void update(Profile profile) throws DaoException {

    }

    @Override
    public void remove(long userId) throws DaoException {

    }

    LocalDate convertDateToLocalDate(Date date) {
        if (date == null) return null;
        LocalDate localDate =
                Instant.ofEpochMilli(date.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
        return localDate;
    }
}

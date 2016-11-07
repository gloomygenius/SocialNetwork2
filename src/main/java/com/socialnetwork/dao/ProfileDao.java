package com.socialnetwork.dao;

import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.Profile;
import com.socialnetwork.models.User;

import java.util.Optional;

/**
 * Created by Vasiliy Bobkov on 06.11.2016.
 */
public interface ProfileDao {
    Optional<Profile> getByUserId(long id) throws DaoException;
    void add(Profile profile) throws DaoException;
    void update(Profile profile) throws DaoException;
    void remove(long userId) throws DaoException;
}
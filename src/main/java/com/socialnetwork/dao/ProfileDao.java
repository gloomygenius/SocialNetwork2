package com.socialnetwork.dao;

import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.entities.Profile;

import java.util.Optional;

/**
 * Created by Vasiliy Bobkov on 06.11.2016.
 */
public interface ProfileDao {
    Optional<Profile> getByUserId(long userId) throws DaoException;
    void add(Profile profile) throws DaoException;
    void update(Profile profile) throws DaoException;
    void remove(long id) throws DaoException;
}
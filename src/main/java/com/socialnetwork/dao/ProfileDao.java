package com.socialnetwork.dao;

import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.Profile;

import java.util.Optional;

public interface ProfileDao {
    Optional<Profile> getByUserId(long userId) throws DaoException;
    void add(Profile profile) throws DaoException;
    void update(Profile profile) throws DaoException;
    void remove(long id) throws DaoException;
}
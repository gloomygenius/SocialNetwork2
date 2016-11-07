package com.socialnetwork.dao;

import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.User;

import java.util.Optional;

/**
 * Created by Vasiliy Bobkov on 06.11.2016.
 */
public interface UserDao {
    Optional<User> getById(long id);
    Optional<User> getByEmail(String email) throws DaoException;
    void add(User user) throws DaoException;
    void update(User user) throws DaoException;
    void remove(long id) throws DaoException;
}
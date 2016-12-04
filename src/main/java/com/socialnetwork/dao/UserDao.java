package com.socialnetwork.dao;

import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.User;

import java.util.Optional;
import java.util.Set;

public interface UserDao {
    Optional<User> getById(long id) throws DaoException;
    Optional<User> getByEmail(String email) throws DaoException;
    Set<Long> getByNames(String firstName, String lastName) throws DaoException;
    void add(User user) throws DaoException;
    void update(User user) throws DaoException;
    void remove(long id) throws DaoException;
}
package com.socialnetwork.dao;

import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.entities.Message;

import java.util.Set;

/**
 * Created by Vasiliy Bobkov on 15.11.2016.
 */
public interface MessageDao {
    Set<Message> getMessages(long dialog , int limit, int offSet) throws DaoException;
    void sendMessage(long sender, long dialog, String message) throws DaoException;
}

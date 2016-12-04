package com.socialnetwork.dao;

import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.Message;

import java.util.Set;

public interface MessageDao {
    Set<Message> getMessages(long dialog , int limit, int offSet) throws DaoException;
    void sendMessage(long sender, long dialog, String message) throws DaoException;
}

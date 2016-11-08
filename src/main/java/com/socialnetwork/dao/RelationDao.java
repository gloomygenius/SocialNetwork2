package com.socialnetwork.dao;

import com.socialnetwork.dao.enums.RelationType;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.Relation;

import java.util.Optional;

/**
 * Created by Vasiliy Bobkov on 08.11.2016.
 */
public interface RelationDao {

    Relation getFriends(long userId) throws DaoException;

    Relation getIncoming(long userId) throws DaoException;

    Relation getRequest(long userId) throws DaoException;

    int getRelationBetween(long fromId, long toId) throws DaoException;

    void add(long senderId, long recipientId, RelationType relationType) throws DaoException;

    void update(long senderId, long recipientId, RelationType relationType) throws DaoException;

    void remove(long senderId, long recipientId, RelationType relationType) throws DaoException;
}
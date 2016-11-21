package com.socialnetwork.dao;

import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.entities.Dialog;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by Vasiliy Bobkov on 15.11.2016.
 */
public interface DialogDao {
    Dialog getPrivateDialog(long from, long to) throws DaoException;
    Set<Dialog> getLastDialogues(long participant, int limit, int offset) throws DaoException;
    Dialog getDialog(long id) throws DaoException;
    void createPrivateDialog(long creator, long participant) throws DaoException;
    void createDialog(long creator, Set<Long> participantIdSet);
    void createDialog(long creator, Set<Long> participantIdSet, String description);
    void updateDescription();
    void updatePartiсipant();

    void updateTime(long dialog, LocalDateTime time) throws DaoException;
}
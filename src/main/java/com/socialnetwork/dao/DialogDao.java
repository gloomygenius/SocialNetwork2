package com.socialnetwork.dao;

import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.Dialog;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Dialog DAO class for work with data base.
 *
 * @author Vasiliy Bobkov
 * @since 1.8
 */
public interface DialogDao {
    /**
     * Return private dialog. Sender and recipient can be replaced.
     * @param from sender id
     * @param to recipient id
     * @return {@code Dialog}
     * @throws DaoException if error from data base
     */
    Dialog getPrivateDialog(long from, long to) throws DaoException;

    /**
     * Return {@code Set} with dialogues id, sorted by last update time.
     * @param participant of dialog
     * @param limit of set
     * @param offset start position
     * @return {@code Set} of dialogues id
     * @throws DaoException if error from data base
     */
    Set<Dialog> getLastDialogues(long participant, int limit, int offset) throws DaoException;

    /**
     * Return {@code Dialog} by id.
     * @param id of dialog
     * @return {@code Dialog}
     * @throws DaoException if error from data base
     */
    Dialog getDialog(long id) throws DaoException;

    /**
     * Create private dialog between tho users.
     * @param creator user id
     * @param participant user id
     * @throws DaoException if error from data base
     */
    void createPrivateDialog(long creator, long participant) throws DaoException;

    /**
     * Update time of dialog.
     * @param dialog id
     * @param time is new time of dialog
     * @throws DaoException if error from data base
     */
    void updateTime(long dialog, LocalDateTime time) throws DaoException;
}
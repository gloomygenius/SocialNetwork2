package com.socialnetwork.dao.exception;

/**
 * Created by Vasiliy Bobkov on 06.11.2016.
 */

public class DaoException extends Throwable {
    public DaoException(String message) {
        super(message);
    }

    public DaoException(Throwable e) {
        super(e);
    }

    public DaoException(String message, Throwable e) {
        super(message, e);
    }
}
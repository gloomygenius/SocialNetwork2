package com.socialnetwork.dao.exception;

import lombok.NoArgsConstructor;

/**
 * Created by Vasiliy Bobkov on 06.11.2016.
 */
@NoArgsConstructor
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
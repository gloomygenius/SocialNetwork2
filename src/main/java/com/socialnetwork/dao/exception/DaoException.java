package com.socialnetwork.dao.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Created by Vasiliy Bobkov on 06.11.2016.
 */
@NoArgsConstructor
@AllArgsConstructor
public class DaoException extends Throwable {
    String message;
}
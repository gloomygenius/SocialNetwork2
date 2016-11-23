package com.socialnetwork.dao;

import com.socialnetwork.common.DataScriptExecutor;
import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.connection_pool.ConnectionPoolException;
import com.socialnetwork.dao.h2.UserDaoImpl;
import lombok.SneakyThrows;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**Test for DataScriptExecutorTest
 * Created by Vasiliy Bobkov on 07.11.2016.
 */
public class DataScriptExecutorTest {
    private static ConnectionPool connectionPool;

    @BeforeClass
    public static void DBinit() throws ConnectionPoolException {
        ConnectionPool.create("src/test/resources/db.properties");
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
    }

    @Test
    @SneakyThrows
    public void initSqlData() throws Exception {
        DataScriptExecutor.initSqlData("src/test/resources/H2Init.sql");
        UserDao userDao = new UserDaoImpl(connectionPool);
        assertTrue(userDao.getById(1).isPresent());
    }
}
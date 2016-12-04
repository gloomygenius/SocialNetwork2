package com.socialnetwork.dao.h2;

import com.socialnetwork.common.DataScriptExecutor;
import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.dao.MessageDao;
import com.socialnetwork.models.Message;
import lombok.SneakyThrows;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertTrue;

/** Test for MessageDaoImpl
 * Created by Vasiliy Bobkov on 15.11.2016.
 */
public class MessageDaoImplTest {
    private static ConnectionPool connectionPool;
    private MessageDao messageDao = new MessageDaoImpl(connectionPool);

    @BeforeClass
    @SneakyThrows
    public static void initTest() {
        ConnectionPool.create("src/test/resources/db.properties");
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
        DataScriptExecutor.initSqlData("src/test/resources/H2Init.sql");
    }

    @Test
    @SneakyThrows
    public void getMessages() throws Exception {
        final int TOTAL_MESSAGES = 3;
        final int START = 0;
        final long DIALOG_ID = 1;
        Set<Message> messageSet = messageDao.getMessages(DIALOG_ID, TOTAL_MESSAGES, START);
        assertTrue(messageSet.size() == 3);
    }

    @Test
    @SneakyThrows
    public void sendMessage() throws Exception {
        final int TOTAL_MESSAGES = -1;
        final int START = 0;
        final long DIALOG_ID = 1;
        messageDao.sendMessage(1, DIALOG_ID, "My test message");
        Set<Message> messageSet = messageDao.getMessages(DIALOG_ID, TOTAL_MESSAGES, START);
        boolean check=false;
        for (Message msg :
                messageSet) {
            System.out.println(msg.getMessage());
            if (msg.getMessage().equals("My test message")) check=true;
        }
        assertTrue(check);
    }
}
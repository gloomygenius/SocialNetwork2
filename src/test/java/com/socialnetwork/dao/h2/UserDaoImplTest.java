package com.socialnetwork.dao.h2;

import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.connection_pool.ConnectionPoolException;
import com.socialnetwork.common.DataScriptExecuter;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.models.User;
import lombok.SneakyThrows;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static com.socialnetwork.dao.enums.Gender.MALE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Vasiliy Bobkov on 07.11.2016.
 */
public class UserDaoImplTest {
    private static ConnectionPool connectionPool;

    @BeforeClass
    public static void DBinit() throws ConnectionPoolException {
        ConnectionPool.create("src/test/resources/db.properties");
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
        DataScriptExecuter.initSqlData("src/test/resources/H2Init.sql");
    }

    @Test
    @SneakyThrows
    public void getById() throws Exception {
        UserDao userDao = new UserDaoImpl(connectionPool);
        assertTrue(userDao.getById(1).isPresent());
    }

    @Test
    @SneakyThrows
    public void getByEmail() throws Exception {
        UserDao userDao = new UserDaoImpl(connectionPool);
        assertTrue(userDao.getByEmail("admin@exam.com").isPresent());
    }

    @Test
    @SneakyThrows
    public void getByNames() throws Exception {
        UserDao userDao = new UserDaoImpl(connectionPool);
        assertTrue(userDao.getByNames("Василий","Бобков").isPresent());
    }

    @Test
    @SneakyThrows
    public void add() throws Exception {
        UserDao userDao = new UserDaoImpl(connectionPool);
        userDao.add(new User(0, "vasya@ya.ru", "123456", "Иван", "Иванов", MALE.ordinal()));
        assertTrue(userDao.getByEmail("vasya@ya.ru").isPresent());
    }

    @Test
    @SneakyThrows
    public void update() throws Exception {
        UserDao userDao = new UserDaoImpl(connectionPool);
        Optional<User> userOptional = userDao.getByEmail("oldmail@exam.com");
        if (!userOptional.isPresent()) throw new Exception();
        User user1 = userOptional.get();
        User user2 = new User(
                user1.getId(),
                "newmail@exam.com",
                user1.getPassword(),
                user1.getFirstName(),
                user1.getLastName(),
                user1.getGender()
        );
        userDao.update(user2);
        User user3 = userDao.getById(user2.getId()).get();
        assertTrue(user3.getEmail().equals("newmail@exam.com"));
    }

    @Test
    @SneakyThrows
    public void remove() throws Exception {
        UserDao userDao = new UserDaoImpl(connectionPool);
        userDao.add(new User(0, "example3@ya.ru", "123456", "Иван", "Иванов", MALE.ordinal()));
        Optional<User> userOptional = userDao.getByEmail("example3@ya.ru");
        assertTrue(userOptional.isPresent());
        userDao.remove(userOptional.get().getId());
        assertFalse(userDao.getByEmail("example3@ya.ru").isPresent());
    }
}
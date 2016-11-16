package com.socialnetwork.dao.h2;

import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.connection_pool.ConnectionPoolException;
import com.socialnetwork.common.DataScriptExecuter;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.entities.User;
import lombok.SneakyThrows;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static com.socialnetwork.dao.enums.Gender.MALE;
import static com.socialnetwork.dao.enums.Roles.ADMIN;
import static com.socialnetwork.dao.enums.Roles.USER;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Vasiliy Bobkov on 07.11.2016.
 */
public class UserDaoImplTest {
    private static ConnectionPool connectionPool;
    private static UserDao userDao;

    @BeforeClass
    public static void DBinit() throws ConnectionPoolException {
        ConnectionPool.create("src/test/resources/db.properties");
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
        DataScriptExecuter.initSqlData("src/test/resources/H2Init.sql");
        userDao = new UserDaoImpl(connectionPool);
    }

    @Test
    @SneakyThrows
    public void getById() throws Exception {
        assertTrue(userDao.getById(1).isPresent());
    }

    @Test
    @SneakyThrows
    public void getByEmail() throws Exception {

        assertTrue(userDao.getByEmail("admin@exam.com").isPresent());
    }

    @Test
    @SneakyThrows
    public void getByNames() throws Exception {

        assertTrue(userDao.getByNames("Василий", "Бобков").size()>0);
    }

    @Test
    @SneakyThrows
    public void add() throws Exception {

        userDao.add(new User(0, "vasya@ya.ru", "123456", "Иван", "Иванов", MALE.ordinal(), ADMIN.ordinal()));
        assertTrue(userDao.getByEmail("vasya@ya.ru").isPresent());
    }

    @Test
    @SneakyThrows
    public void update() throws Exception {

        Optional<User> userOptional = userDao.getByEmail("oldmail@exam.com");
        if (!userOptional.isPresent()) throw new Exception();
        User user1 = userOptional.get();
        User user2 = new User(
                user1.getId(),
                "newmail@exam.com",
                user1.getPassword(),
                user1.getFirstName(),
                user1.getLastName(),
                user1.getGender(),
                user1.getRole()
        );
        userDao.update(user2);
        User user3 = userDao.getById(user2.getId()).get();
        assertTrue(user3.getEmail().equals("newmail@exam.com"));
    }

    @Test
    @SneakyThrows
    public void remove() throws Exception {

        userDao.add(new User(0, "example3@ya.ru", "123456", "Иван", "Иванов", MALE.ordinal(), USER.ordinal()));
        Optional<User> userOptional = userDao.getByEmail("example3@ya.ru");
        assertTrue(userOptional.isPresent());
        userDao.remove(userOptional.get().getId());
        assertFalse(userDao.getByEmail("example3@ya.ru").isPresent());
    }
}
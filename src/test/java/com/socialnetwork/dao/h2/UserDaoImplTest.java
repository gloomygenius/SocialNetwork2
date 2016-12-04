package com.socialnetwork.dao.h2;

import com.socialnetwork.common.DataScriptExecutor;
import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.connection_pool.ConnectionPoolException;
import com.socialnetwork.dao.RelationDao;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.dao.enums.RelationType;
import com.socialnetwork.models.User;
import lombok.SneakyThrows;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static com.socialnetwork.dao.enums.Gender.MALE;
import static com.socialnetwork.dao.enums.RelationType.NEUTRAL;
import static com.socialnetwork.dao.enums.Roles.ADMIN;
import static com.socialnetwork.dao.enums.Roles.USER;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Test for UserDaoImpl
 * Created by Vasiliy Bobkov on 07.11.2016.
 */
public class UserDaoImplTest {
    private static UserDao userDao;
    private static RelationDao relationDao;

    @BeforeClass
    public static void DBinit() throws ConnectionPoolException {
        ConnectionPool.create("src/test/resources/db.properties");
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
        DataScriptExecutor.initSqlData("src/test/resources/H2Init.sql");
        userDao = new UserDaoImpl(connectionPool);
        relationDao = new RelationDaoImpl(connectionPool);
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

        assertTrue(userDao.getByNames("Василий", "Бобков").size() > 0);
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

        Optional<User> user3 = userDao.getById(user2.getId());
        if (user3.isPresent()) assertTrue(user3.get().getEmail().equals("newmail@exam.com"));
        else throw new Exception();
    }

    @Test
    @SneakyThrows
    public void remove() throws Exception {

        userDao.add(new User(0, "example3@ya.ru", "123456", "Иван", "Иванов", MALE.ordinal(), USER.ordinal()));
        Optional<User> userOptional = userDao.getByEmail("example3@ya.ru");
        assertTrue(userOptional.isPresent());
        long user_id = userOptional.get().getId();
        relationDao.add(user_id, 1, RelationType.INCOMING);
        relationDao.add(user_id, 1, RelationType.FRIEND);
        userDao.remove(user_id);
        assertFalse(userDao.getByEmail("example3@ya.ru").isPresent());
        assertThat(relationDao.getRelationBetween(1, user_id), is(NEUTRAL.ordinal()));
    }
}
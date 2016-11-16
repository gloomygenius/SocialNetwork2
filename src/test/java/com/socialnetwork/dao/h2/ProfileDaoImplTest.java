package com.socialnetwork.dao.h2;

import com.socialnetwork.common.DataScriptExecuter;
import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.connection_pool.ConnectionPoolException;
import com.socialnetwork.dao.ProfileDao;
import com.socialnetwork.entities.Profile;
import lombok.SneakyThrows;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by Vasiliy Bobkov on 07.11.2016.
 */
public class ProfileDaoImplTest {
    private static ConnectionPool connectionPool;
    ProfileDao profileDao = new ProfileDaoImpl(connectionPool);
    @BeforeClass

    public static void DBinit() throws ConnectionPoolException {
        ConnectionPool.create("src/test/resources/db.properties");
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
        DataScriptExecuter.initSqlData("src/test/resources/H2Init.sql");

    }

    @Test
    @SneakyThrows
    public void getByUserId() throws Exception {

        Profile profile = new Profile(
                1,
                "+791100299",
                LocalDate.of(1993, Month.DECEMBER, 1),
                "Россия",
                "Санкт-Петербург",
                "СПБПУ",
                0,
                0,
                "оптимист");
        profileDao.add(profile);
        Optional<Profile> profileOptional = profileDao.getByUserId(1);
        assertTrue(profileOptional.isPresent());
        assertTrue(profileOptional.get().equals(profile));
    }

    @Test
    public void add() throws Exception {
        //то же самое в getById
    }

    @Test
    public void update() throws Exception {
        //частный случай add
    }

    @Test
    @SneakyThrows
    public void remove() throws Exception {

        Profile profile = new Profile(
                2,
                "+791100299",
                LocalDate.of(1993, Month.DECEMBER, 1),
                "Россия",
                "Санкт-Петербург",
                "Оксфорд",
                0,
                0,
                "Пессимист");
        profileDao.add(profile);
        Optional<Profile> profileOptional = profileDao.getByUserId(profile.getId());
        assertTrue(profileOptional.isPresent());
        profileDao.remove(profile.getId());
        profileOptional = profileDao.getByUserId(profile.getId());
        assertTrue(profileOptional.get().equals(new Profile(profile.getId(),null,null,null,null,null,0,0,null)));
    }
}
package com.socialnetwork.dao.h2;

import com.socialnetwork.common.DataScriptExecutor;
import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.connection_pool.ConnectionPoolException;
import com.socialnetwork.dao.RelationDao;
import com.socialnetwork.dao.enums.RelationType;
import com.socialnetwork.entities.Relation;
import lombok.SneakyThrows;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Test for RelationDaoImpl
 * Created by Vasiliy Bobkov on 08.11.2016.
 */
public class RelationDaoImplTest {
    private static ConnectionPool connectionPool;
    private RelationDao relationDao = new RelationDaoImpl(connectionPool);

    @BeforeClass
    public static void DBinit() throws ConnectionPoolException {
        ConnectionPool.create("src/test/resources/db.properties");
        connectionPool = ConnectionPool.getInstance();
        connectionPool.initPoolData();
        DataScriptExecutor.initSqlData("src/test/resources/H2Init.sql");
    }

    @Test
    public void getByUserId() throws Exception {

    }

    @Test
    @SneakyThrows
    public void getFriends() throws Exception {

        Relation relation = relationDao.getFriends(1);
        Set<Long> friends = new HashSet<>();
        friends.add(2L);
        friends.add(3L);
        assertTrue(relation.getIdSet().containsAll(friends));
    }

    @Test
    @SneakyThrows
    public void getIncoming() throws Exception {

        Relation relation = relationDao.getIncoming(1);
        Set<Long> incoming = new HashSet<>();
        incoming.add(5L);
        assertTrue(relation.getIdSet().containsAll(incoming));
    }

    @Test
    @SneakyThrows
    public void getRequest() throws Exception {

        Relation relation = relationDao.getRequest(1);
        Set<Long> request = new HashSet<>();
        request.add(4L);
        assertTrue(relation.getIdSet().containsAll(request));
    }

    @Test
    @SneakyThrows
    public void getRelationBetween() throws Exception {

        relationDao.add(3, 4, RelationType.REQUEST);
        int relation = relationDao.getRelationBetween(3, 4);
        assertTrue(relation == RelationType.REQUEST.ordinal());
    }

    @Test
    @SneakyThrows
    public void add() throws Exception {

        relationDao.add(4, 5, RelationType.INCOMING);
        int relation = relationDao.getRelationBetween(4, 5);
        assertTrue(relation == RelationType.INCOMING.ordinal());
        relationDao.add(4, 5, RelationType.FRIEND);
        relation = relationDao.getRelationBetween(4, 5);
        assertFalse(relation == RelationType.INCOMING.ordinal());
        assertTrue(relation == RelationType.FRIEND.ordinal());
    }

    @Test
    public void update() throws Exception {
        // то же самое, что и add
    }

    @Test
    @SneakyThrows
    public void remove() throws Exception {

        relationDao.add(3, 4, RelationType.REQUEST);
        int relation = relationDao.getRelationBetween(3, 4);
        assertTrue(relation == RelationType.REQUEST.ordinal());
        relationDao.remove(3, 4, RelationType.REQUEST);
        relation = relationDao.getRelationBetween(3, 4);
        System.out.println(relation);
        assertTrue(relation == RelationType.NEUTRAL.ordinal());

        relationDao.add(3, 4, RelationType.FRIEND);
        relation = relationDao.getRelationBetween(3, 4);
        assertTrue(relation == RelationType.FRIEND.ordinal());
        relationDao.remove(4, 3, RelationType.FRIEND);
        relation = relationDao.getRelationBetween(3, 4);
        System.out.println(relation);
        assertTrue(relation == RelationType.NEUTRAL.ordinal());
    }
}
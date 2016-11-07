package com.socialnetwork.dao;

import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.connection_pool.ConnectionPoolException;
import lombok.extern.log4j.Log4j;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
@Log4j
public abstract class DataScriptExecuter {

    public static void initSqlData(String pathToInitSQL) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        File file = new File(pathToInitSQL);
        String currentLine;
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((currentLine = reader.readLine()) != null) {
               builder.append(currentLine);
            }
        } catch (IOException e) {
            log.fatal("Reading sql script file error", e);
        }
        String[] initState = builder.toString().split(";");

        try (Connection connection = connectionPool.takeConnection();
             Statement statement = connection.createStatement()) {
            for (String state : initState) {
                statement.executeUpdate(state);
            }
        } catch (SQLException e) {
            log.fatal("Init SQL script error", e);
        } catch (ConnectionPoolException e) {
            log.fatal("Error in ConnectionPool when sql script was executed", e);
        }
    }
}

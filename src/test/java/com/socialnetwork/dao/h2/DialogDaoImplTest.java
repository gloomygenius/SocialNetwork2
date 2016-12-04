package com.socialnetwork.dao.h2;

import com.socialnetwork.common.DataScriptExecutor;
import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.dao.DialogDao;
import com.socialnetwork.models.Dialog;
import lombok.SneakyThrows;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertNotNull;

/**Test for DialogDaoImpl
 * Created by Vasiliy Bobkov on 15.11.2016.
 */
public class DialogDaoImplTest {
    private static ConnectionPool connectionPool;
    private DialogDao dialogDao = new DialogDaoImpl(connectionPool);

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
    public void getPrivateDialog() throws Exception {
        Dialog dialog = dialogDao.getPrivateDialog(1, 2);
        System.out.println("getPrivateDialog: "+dialog.toString());
        assertNotNull(dialog);
    }

    @Test
    @SneakyThrows
    public void getLastDialogs() throws Exception {
        Set<Dialog> dialogSet = dialogDao.getLastDialogues(1,4,0);
        for (Dialog dialog:dialogSet){
            System.out.println("getLastDialogues: "+dialog.toString());
        }
    }

    @Test
    @SneakyThrows
    public void getDialog() throws Exception {
        Dialog dialog =dialogDao.getDialog(1);
        System.out.println("getDialog: "+dialog.toString());
        assertNotNull(dialog);
    }

    @Test
    @SneakyThrows
    public void createPrivateDialog() throws Exception {
        dialogDao.createPrivateDialog(3,4);
        Dialog dialog = dialogDao.getPrivateDialog(3, 4);
        System.out.println(dialog.toString());
        assertNotNull(dialog);
    }

    @Test
    public void createDialog() throws Exception {

    }

    @Test
    public void createDialog1() throws Exception {

    }

    @Test
    public void updateDescription() throws Exception {

    }

    @Test
    public void updatePartiсipant() throws Exception {

    }

}
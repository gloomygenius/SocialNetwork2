package com.socialnetwork.servlets;

import com.socialnetwork.dao.DialogDao;
import com.socialnetwork.dao.MessageDao;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.entities.Dialog;
import com.socialnetwork.entities.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import static com.socialnetwork.filters.SecurityFilter.CURRENT_USER;
import static com.socialnetwork.listeners.Initializer.DIALOG_DAO;
import static com.socialnetwork.listeners.Initializer.MESSAGE_DAO;
import static com.socialnetwork.listeners.Initializer.USER_DAO;

/**
 * Created by Vasiliy Bobkov on 10.11.2016.
 */
@WebServlet("/messages")
public class MessagesServlet extends HttpServlet {
    public static final String INCLUDED_PAGE = "includedPage";
    private static MessageDao messageDao;
    private static DialogDao dialogDao;
    private static UserDao userDao;

    @Override
    public void init(ServletConfig servletConfig) {
        ServletContext servletContext = servletConfig.getServletContext();
        messageDao = (MessageDao) servletContext.getAttribute(MESSAGE_DAO);
        dialogDao = (DialogDao) servletContext.getAttribute(DIALOG_DAO);
        userDao = (UserDao) servletContext.getAttribute(USER_DAO);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute(INCLUDED_PAGE, "messages");
        User currentUser = (User) request.getSession().getAttribute(CURRENT_USER);
        int limit = request.getAttribute("limit") != null ? (int) request.getAttribute("limit") : -1;
        int offset = request.getAttribute("offset") != null ? (int) request.getAttribute("offset") : 0;
        try {
            Set<Dialog> dialogSet = dialogDao.getLastDialogues(currentUser.getId(), limit, offset);
            Map<Dialog, User> map = new TreeMap<>();
            Optional<User> bufferUser;
            for (Dialog dialog : dialogSet) {
                int sender = (int) currentUser.getId();
                bufferUser = userDao.getById(dialog.getRecipient(sender));
                if (bufferUser.isPresent()){
                    map.put(dialog, bufferUser.get());
                }
            }
            request.setAttribute("dialogues", map);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            // TODO: 09.11.2016 Обработать
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }
}

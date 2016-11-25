package com.socialnetwork.servlets;

import com.socialnetwork.dao.DialogDao;
import com.socialnetwork.dao.MessageDao;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.entities.Message;
import com.socialnetwork.entities.User;
import lombok.SneakyThrows;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.socialnetwork.filters.SecurityFilter.CURRENT_USER;
import static com.socialnetwork.listeners.Initializer.*;

/**
 * Created by Vasiliy Bobkov on 21.11.2016.
 */
@WebServlet("/messages")
public class MessageServlet extends HttpServlet {
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
    @SneakyThrows
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        request.setCharacterEncoding("UTF-8");
        User currentUser = (User) request.getSession().getAttribute(CURRENT_USER);
        request.setAttribute(INCLUDED_PAGE, "messages");
        long dialog;
        if (request.getParameter("dialog") != null) dialog = Long.parseLong(request.getParameter("dialog"));
        else dialog = dialogDao.getPrivateDialog(currentUser.getId(), Long.parseLong(request.getParameter("recipient"))).getId();

        if (request.getParameter("message") != null) {
            messageDao.sendMessage(currentUser.getId(), dialog, request.getParameter("message"));
            dialogDao.updateTime(dialog, LocalDateTime.now());
        }
        int limit = request.getParameter("limit") != null ? Integer.parseInt(request.getParameter("limit")) : 15;
        request.setAttribute("limit", limit);
        int offset = request.getParameter("offset") != null ? Integer.parseInt(request.getParameter("offset")) : 0;
        request.setAttribute("offset", offset);

        try {
            Map<Long, String> userMap = new HashMap<>();
            Set<Message> messageSet = messageDao.getMessages(dialog, limit, offset);
            request.setAttribute("messages", messageSet);
            for (Message message : messageSet) {
                if (userMap.get(message.getSender()) != null) continue;
                Optional<User> userOptional = userDao.getById(message.getSender());
                userOptional.ifPresent((user) -> userMap.put(user.getId(), user.getFirstName()));
            }
            request.setAttribute("userMap", userMap);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            // TODO: 09.11.2016 Обработать
        }
    }
}
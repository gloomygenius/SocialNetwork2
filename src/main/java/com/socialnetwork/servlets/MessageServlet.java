package com.socialnetwork.servlets;

import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.Message;
import com.socialnetwork.models.User;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.COMMON_ERROR;

@Log4j
public class MessageServlet extends CommonHttpServlet {
    private static String MESSAGES = "messages";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute(CURRENT_USER);
        request.setAttribute(INCLUDED_PAGE, "messages");
        long dialog;
        try {
            if (request.getParameter("dialog") != null) dialog = Long.parseLong(request.getParameter("dialog"));
            else
                dialog = dialogDao.getPrivateDialog(currentUser.getId(), Long.parseLong(request.getParameter("recipient"))).getId();


            request.setAttribute("dialog", dialog);
            if (request.getParameter("message") != null) {
                messageDao.sendMessage(currentUser.getId(), dialog, request.getParameter("message"));
                dialogDao.updateTime(dialog, LocalDateTime.now());
            }
            int limit = request.getParameter("limit") != null ? Integer.parseInt(request.getParameter("limit")) : 15;
            request.setAttribute("limit", limit);
            int offset = request.getParameter("offset") != null ? Integer.parseInt(request.getParameter("offset")) : 0;
            request.setAttribute("offset", offset);
            Map<Long, String> userMap = new HashMap<>();
            Set<Message> messageSet = messageDao.getMessages(dialog, limit, offset);
            request.setAttribute(MESSAGES, messageSet);
            for (Message message : messageSet) {
                if (userMap.get(message.getSender()) != null) continue;
                Optional<User> userOptional = userDao.getById(message.getSender());
                userOptional.ifPresent((user) -> userMap.put(user.getId(), user.getFirstName()));
            }
            request.setAttribute("userMap", userMap);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (DaoException e) {
            log.error("Getting private dialog of id" + currentUser.getId() + " error", e);
            request.setAttribute(ERROR_MSG, COMMON_ERROR.getPropertyName());
            request.getRequestDispatcher("/error").forward(request, response);
        }
    }
}
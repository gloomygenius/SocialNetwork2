package com.socialnetwork.servlets;

import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.models.Dialog;
import com.socialnetwork.models.User;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

@Log4j
public class DialoguesServlet extends CommonHttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(INCLUDED_PAGE, "dialogues");
        User currentUser = (User) request.getSession().getAttribute(CURRENT_USER);
        int offset = getIntParameter("offset", request, 0);
        int limit = getIntParameter("limit", request, 10);

        boolean hasNextPage=false;
        Map<Dialog, User> map = new TreeMap<>();
        try {
            Set<Dialog> dialogSet = dialogDao.getLastDialogues(currentUser.getId(), limit, offset);

            Optional<User> bufferUser;
            for (Dialog dialog : dialogSet) {
                int sender = (int) currentUser.getId();
                bufferUser = userDao.getById(dialog.getRecipient(sender));
                bufferUser.ifPresent(user -> map.put(dialog, user));
            }
            if (dialogSet.size()==limit) hasNextPage=true;
        } catch (DaoException e) {
            log.error("Error in dialogDao when user " + currentUser.getId() + " try get dialogues list", e);
        }
        request.setAttribute("hasNextPage", hasNextPage);
        request.setAttribute("dialogues", map);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
    private int getIntParameter(String paramName, HttpServletRequest request, int defValue) {
        int value = defValue;
        if (request.getParameter(paramName) != null) {
            log.debug(paramName + " " + Integer.parseInt(request.getParameter(paramName)));
            value = Integer.parseInt(request.getParameter(paramName));
        }
        request.setAttribute(paramName, value);
        return value;
    }
}
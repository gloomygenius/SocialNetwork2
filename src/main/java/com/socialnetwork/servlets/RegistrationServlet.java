package com.socialnetwork.servlets;

import com.socialnetwork.common.NameNormalizer;
import com.socialnetwork.connection_pool.ConnectionPool;
import com.socialnetwork.dao.UserDao;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.dao.h2.UserDaoImpl;
import com.socialnetwork.models.Relation;
import com.socialnetwork.models.User;
import com.socialnetwork.services.Validator;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.socialnetwork.dao.enums.Gender.FEMALE;
import static com.socialnetwork.dao.enums.Gender.MALE;
import static com.socialnetwork.dao.enums.Roles.USER;
import static com.socialnetwork.servlets.AuthorizationServlet.NEW_FRIENDS;
import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.EMAIL_ALREADY_EXIST;
import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.REGISTRATION_FAIL;

@Log4j
public class RegistrationServlet extends CommonHttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(INCLUDED_PAGE, "regpage");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        requestProcess(request, response);
    }

    @SneakyThrows
    private void requestProcess(HttpServletRequest request, HttpServletResponse response) {
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        UserDao userDao = new UserDaoImpl(connectionPool);
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("password_confirm");
        int gender = request.getParameter("gender").equals("male") ? MALE.ordinal() : FEMALE.ordinal();
        Validator.ValidCode validCode = Validator.validateRegistration(
                firstName, lastName, email, password, passwordConfirm);

        if (validCode != Validator.ValidCode.SUCCESS) {
            request.setAttribute(ERROR_MSG, validCode.getPropertyName());
            request.setAttribute(INCLUDED_PAGE, "regpage");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            if (userDao.getByEmail(email).isPresent()) {
                request.setAttribute(ERROR_MSG, EMAIL_ALREADY_EXIST.getPropertyName());
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else {
                try {
                    password = DigestUtils.md5Hex(request.getParameter("password"));
                    userDao.add(new User(
                            0,
                            email,
                            password,
                            NameNormalizer.normalize(firstName),
                            NameNormalizer.normalize(lastName),
                            gender,
                            USER.ordinal()));
                    Optional<User> userOptional = userDao.getByEmail(email);
                    if (userOptional.isPresent()) {
                        log.info("New user registered! Login:" + email);
                        request.getSession().setAttribute(CURRENT_USER, userOptional.get());
                        request.getSession().setAttribute(NEW_FRIENDS, getCountNewFriend(userOptional.get().getId()));
                        response.sendRedirect("/");
                    } else {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    log.error("Add new user " + email + " failed");
                    request.setAttribute(ERROR_MSG, REGISTRATION_FAIL.getPropertyName());
                    request.setAttribute(ERROR_MSG, validCode.getPropertyName());
                    request.setAttribute(INCLUDED_PAGE, "regpage");
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                }
            }
        }
    }
    private int getCountNewFriend(long id) {
        Relation incoming = null;
        try {
            incoming = relationDao.getIncoming(id);
        } catch (DaoException e) {
            log.error("Error in relationDao when user " + id + " try get count of new friends", e);
        }
        return incoming != null ? incoming.getIdSet().size() : 0;
    }
}
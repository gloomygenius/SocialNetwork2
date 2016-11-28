package com.socialnetwork.servlets;

import com.socialnetwork.common.NameNormalizer;
import com.socialnetwork.dao.ProfileDao;
import com.socialnetwork.dao.exception.DaoException;
import com.socialnetwork.entities.Profile;
import com.socialnetwork.entities.User;
import com.socialnetwork.services.Validator;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import static com.socialnetwork.filters.SecurityFilter.CURRENT_USER;
import static com.socialnetwork.listeners.Initializer.PROFILE_DAO;
import static com.socialnetwork.servlets.ErrorHandler.ERROR_MSG;
import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.COMMON_ERROR;
import static com.socialnetwork.servlets.ErrorHandler.ErrorCode.USER_NOT_FOUND;
import static com.socialnetwork.servlets.ErrorHandler.SUCCESS_MSG;

/**
 * Created by Vasiliy Bobkov on 26.11.2016.
 */
@Log4j
public class ProfileEditorServlet extends HttpServlet {
    private static final String INCLUDED_PAGE = "includedPage";
    private static ProfileDao profileDao;
    private final static String PROFILE = "profile";
    private User currentUser;

    @Override
    public void init(ServletConfig servletConfig) {
        ServletContext servletContext = servletConfig.getServletContext();
        profileDao = (ProfileDao) servletContext.getAttribute(PROFILE_DAO);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameterMap().size() > 0) try {
            processParams(request);
        } catch (DaoException e) {
            request.setAttribute(ERROR_MSG, COMMON_ERROR);
        }

        HttpSession session = request.getSession();
        currentUser = (User) session.getAttribute(CURRENT_USER);
        Optional<Profile> profile = Optional.empty();
        try {
            profile = profileDao.getByUserId(currentUser.getId());
        } catch (DaoException e) {
            log.error("ProfileDao exception in IdFilter", e);
            request.setAttribute(ERROR_MSG, USER_NOT_FOUND.getPropertyName());
            request.getRequestDispatcher("/error").forward(request, response);
        }
        profile.ifPresent(profile1 -> request.setAttribute(PROFILE, profile1));
        request.setAttribute(INCLUDED_PAGE, "edit_profile");
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private void processParams(HttpServletRequest request) throws DaoException {
        String telephone = request.getParameter("telephone");
        String birthday = request.getParameter("birthday");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String university = request.getParameter("university");
        String position = request.getParameter("position");
        String about = request.getParameter("about");
        Validator.ValidCode validCode = Validator.validateProfile(telephone,birthday,country, city, university,about);
        if (validCode != Validator.ValidCode.SUCCESS)
            request.setAttribute(ERROR_MSG, validCode.getPropertyName());
        else {
            request.setAttribute(SUCCESS_MSG, validCode.getPropertyName());
            LocalDate date = LocalDate.parse(birthday);
            Profile profile=new Profile(
                    currentUser.getId(),
                    telephone.length()==0?null:telephone,
                    date,
                    country.length()==0?null:NameNormalizer.multiNormalize(country),
                    city.length()==0?null:NameNormalizer.multiNormalize(city),
                    university.length()==0?null:university,
                    0,
                    Integer.parseInt(position),
                    about.length()==0?null:about);
            profileDao.add(profile);
        }
    }
}
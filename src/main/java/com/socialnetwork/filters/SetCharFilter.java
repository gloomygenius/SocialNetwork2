package com.socialnetwork.filters;

import com.socialnetwork.common.HttpFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetCharFilter implements HttpFilter {

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain next)
            throws IOException, ServletException {

// чтение кодировки из запроса

        String encoding = request.getCharacterEncoding();

// установка UTF-8, если не установлена

        if (!"UTF-8".equals(encoding))
            response.setCharacterEncoding("UTF-8");
        next.doFilter(request, response);
    }
}
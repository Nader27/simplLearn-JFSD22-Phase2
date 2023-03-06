package com.vodafone.learner_academy.filter;

import com.vodafone.learner_academy.dao.SessionDao;
import com.vodafone.learner_academy.model.Session;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;

@WebFilter(value = "/*", filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    private final String[] noAuthRequire = new String[]{
            "/login",
            "/register",
            "/doLogin",
            "/doRegister"
    };

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession httpSession = httpRequest.getSession();
        Integer sessionId = (Integer) httpSession.getAttribute(SessionDao.SESSION_ID);
        String sessionSecret = (String) httpSession.getAttribute(SessionDao.SESSION_SECRET);
        if (sessionSecret == null || sessionId == null) {
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("Learner_" + SessionDao.SESSION_SECRET)) {
                        sessionSecret = cookie.getValue();
                    } else if (cookie.getName().equals("Learner_" + SessionDao.SESSION_ID)) {
                        sessionId = Integer.parseInt(cookie.getValue());
                    }
                }
                httpSession.setAttribute(SessionDao.SESSION_SECRET, sessionSecret);
                httpSession.setAttribute(SessionDao.SESSION_ID, sessionId);
            }
        }
        Session session = null;
        if (sessionSecret != null && sessionId != null){
            session = new SessionDao().getSession(sessionSecret, sessionId);
            if(session == null) httpSession.invalidate();
        }
        boolean loggedIn = session != null;
        boolean noAuthRequest = Arrays.asList(noAuthRequire).contains(httpRequest.getServletPath());
        if (noAuthRequest) {
            if (loggedIn) {
                httpRequest.setAttribute("mySession", session);
                RequestDispatcher requestDispatcher = httpRequest.getRequestDispatcher("index.jsp");
                requestDispatcher.include(httpRequest,httpResponse);
                httpResponse.sendRedirect("home");

            } else chain.doFilter(httpRequest, httpResponse);
        } else {
            if (!loggedIn) {
                RequestDispatcher requestDispatcher = httpRequest.getRequestDispatcher("login-form.jsp");
                requestDispatcher.include(httpRequest,httpResponse);
                httpResponse.sendRedirect("login");

            } else {
                httpRequest.setAttribute("mySession", session);
                chain.doFilter(httpRequest, httpResponse);
            }
        }
    }
}

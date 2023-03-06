package com.vodafone.learner_academy.filter;

import com.vodafone.learner_academy.dao.SessionDao;
import com.vodafone.learner_academy.model.Session;
import com.vodafone.learner_academy.model.User;
import com.vodafone.learner_academy.model.UserType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

@WebFilter(value = "/*", filterName = "AuthorizationFilter")
public class AuthorizationFilter implements Filter {

    private final String[] adminOnly = new String[]{
            "UserController",
            "RoomController"
    };
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        Session session = new SessionDao().getSession(httpRequest);
        if (session != null){
            User loggedInUser = session.getUser();
            String userType = loggedInUser.getUserType().getType();
            boolean isAdminOnly = Arrays.asList(adminOnly).contains(httpRequest.getHttpServletMapping().getServletName());
            if(!userType.equals(UserType.ADMIN) && isAdminOnly){
                throw new RuntimeException("UnAuthorized");
            }
            else chain.doFilter(httpRequest,httpResponse);
        }else chain.doFilter(httpRequest,httpResponse);
    }
}

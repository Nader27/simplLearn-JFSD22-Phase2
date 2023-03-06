package com.vodafone.learner_academy.controller;

import com.vodafone.learner_academy.dao.SessionDao;
import com.vodafone.learner_academy.dao.UserDao;
import com.vodafone.learner_academy.dao.UserTypeDao;
import com.vodafone.learner_academy.model.Session;
import com.vodafone.learner_academy.model.User;
import com.vodafone.learner_academy.model.UserType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Optional;

@WebServlet(name = "AuthController")
public class AuthController extends HttpServlet {

    private UserDao userDao;
    private SessionDao sessionDao;

    public void init() {
        userDao = new UserDao();
        sessionDao = new SessionDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/register":
                showRegister(request, response);
                break;
            case "/myAccount":
                showEditAccount(request, response);
                break;
            case "/editAccountPassword":
                showEditPassword(request, response);
                break;
            case "/logout":
                logout(request, response);
                break;
            case "/login":
            default:
                showLogin(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/doLogin":
                    login(request, response);
                    break;
                case "/doRegister":
                    register(request, response);
                    break;
                case "/editAccount":
                    UpdateAccount(request, response);
                    break;
                case "/updateAccountPassword":
                    UpdateAccountPassword(request, response);
                    break;
                default:
                    break;
            }
        } catch (SQLException | NoSuchAlgorithmException | ParseException ex) {
            throw new ServletException(ex);
        }
    }

    private void showLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("login-form.jsp");
        dispatcher.forward(request, response);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        sessionDao.delete(sessionDao.getSession(request));
        httpSession.invalidate();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Learner_" + SessionDao.SESSION_SECRET)) {
                    cookie.setMaxAge(0);
                } else if (cookie.getName().equals("Learner_" + SessionDao.SESSION_ID)) {
                    cookie.setMaxAge(0);
                }
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("login-form.jsp");
        dispatcher.include(request, response);
        response.sendRedirect("login");

    }

    private void showRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("register-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("account-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("account-password.jsp");
        dispatcher.forward(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException, ParseException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("remember_me");
        Optional<User> foundUser = userDao.getAll().stream().filter(user -> user.getEmail().equals(email.toLowerCase()) && user.getPassword().equals(userDao.encryptPassword(password))).findFirst();
        if (foundUser.isPresent()) {
            Session session =  new Session(foundUser.get(),rememberMe!= null);
            new SessionDao().save(session);
            HttpSession httpSession = request.getSession(false);
            if(httpSession != null) {
                httpSession.setAttribute(SessionDao.SESSION_SECRET, session.getSession());
                httpSession.setAttribute(SessionDao.SESSION_ID, session.getId());
            }
            if(rememberMe!=null)
            {
                Cookie secretCookie = new Cookie("Learner_"+SessionDao.SESSION_SECRET,session.getSession());
                Cookie idCookie = new Cookie("Learner_"+SessionDao.SESSION_ID, String.valueOf(session.getId()));
                response.addCookie(secretCookie);
                response.addCookie(idCookie);
            }
            response.sendRedirect("home");
        } else throw new AuthenticationException("Email and Password Not Match");
    }

    private void register(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException, ParseException {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String verifyPassword = request.getParameter("verify_password");
        if (!password.equals(verifyPassword))
            response.sendRedirect("showNewForm");
        String encryptedPassword = userDao.encryptPassword(password);
        UserType userTypeObject = new UserTypeDao().getAll().stream()
                .filter(u -> UserType.TEACHER.equals(u.getType()))
                .findFirst().orElseThrow();
        User newUser = new User(firstName, lastName, email, encryptedPassword, userTypeObject);
        userDao.save(newUser);
        response.sendRedirect("login");
    }

    private void UpdateAccount(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        User user = sessionDao.getSession(request).getUser();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        userDao.update(user);
        response.sendRedirect("home");
    }
    private void UpdateAccountPassword(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException {
        String password = request.getParameter("password");
        String verifyPassword = request.getParameter("verify_password");
        if (!password.equals(verifyPassword))
            response.sendRedirect("showNewForm");
        User user = sessionDao.getSession(request).getUser();
        String encryptedPassword = userDao.encryptPassword(password);
        user.setPassword(encryptedPassword);
        userDao.update(user);
        response.sendRedirect("home");
    }
}
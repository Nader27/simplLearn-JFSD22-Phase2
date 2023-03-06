package com.vodafone.learner_academy.controller;

import com.vodafone.learner_academy.dao.UserDao;
import com.vodafone.learner_academy.dao.UserTypeDao;
import com.vodafone.learner_academy.model.User;
import com.vodafone.learner_academy.model.UserType;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserController", value = "/user")
public class UserController extends HttpServlet {

    private UserDao userDao;

    public void init() {
        userDao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/newUser":
                    showNewForm(request, response);
                    break;
                case "/editUser":
                    showEditForm(request, response);
                    break;
                case "/editUserPassword":
                    showEditPasswordForm(request, response);
                    break;
                case "/deleteUser":
                    deleteUser(request, response);
                    break;
                case "/listUser":
                default:
                    showListUser(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/insertUser":
                    insertUser(request, response);
                    break;
                case "/updateUserPassword":
                    updateUserPassword(request, response);
                    break;
                case "/updateUser":
                    updateUser(request, response);
                    break;
                default:
                    break;
            }
        } catch (SQLException | NoSuchAlgorithmException ex) {
            throw new ServletException(ex);
        }
    }

    private void showListUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<User> listUser = userDao.getAll();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        request.setAttribute("user_types", new UserTypeDao().getAll());
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDao.get(id).orElseThrow();
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
        request.setAttribute("user", existingUser);
        request.setAttribute("user_types", new UserTypeDao().getAll());
        dispatcher.forward(request, response);

    }

    private void showEditPasswordForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDao.get(id).orElseThrow();
        RequestDispatcher dispatcher = request.getRequestDispatcher("user-password.jsp");
        request.setAttribute("user", existingUser);
        dispatcher.forward(request, response);
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException {
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String verifyPassword = request.getParameter("verify_password");
        if (!password.equals(verifyPassword))
            response.sendRedirect("showNewForm");
        int userType = Integer.parseInt(request.getParameter("user_type"));
        String encryptedPassword = userDao.encryptPassword(password);
        UserType userTypeObject = new UserTypeDao().getAll().stream()
                .filter(u -> u.getId() == userType)
                .findFirst().orElseThrow();
        User newUser = new User(firstName, lastName, email, encryptedPassword, userTypeObject);
        userDao.save(newUser);
        response.sendRedirect("listUser");
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        int userType = Integer.parseInt(request.getParameter("user_type"));
        UserType userTypeObject = new UserTypeDao().getAll().stream()
                .filter(u -> u.getId() == userType)
                .findFirst().orElseThrow();
        User user = new UserDao().get(id).orElseThrow();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUserType(userTypeObject);
        userDao.update(user);
        response.sendRedirect("listUser");
    }

    private void updateUserPassword(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException {
        int id = Integer.parseInt(request.getParameter("id"));
        String password = request.getParameter("password");
        String verifyPassword = request.getParameter("verify_password");
        if (!password.equals(verifyPassword))
            response.sendRedirect("showNewForm");
        User user = new UserDao().get(id).orElseThrow();
        String encryptedPassword = userDao.encryptPassword(password);
        user.setPassword(encryptedPassword);
        userDao.update(user);
        response.sendRedirect("listUser");
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User user = new UserDao().get(id).orElseThrow();
        userDao.delete(user);
        response.sendRedirect("listUser");
    }
}
package com.vodafone.learner_academy.controller;

import com.vodafone.learner_academy.dao.SubjectDao;
import com.vodafone.learner_academy.model.Subject;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

@WebServlet(name = "SubjectController", value = "/subject")
public class SubjectController extends HttpServlet {

    private SubjectDao subjectDao;

    public void init() {
        subjectDao = new SubjectDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/newSubject":
                    showNewForm(request, response);
                    break;
                case "/editSubject":
                    showEditForm(request, response);
                    break;
                case "/deleteSubject":
                    deleteSubject(request, response);
                    break;
                case "/listSubject":
                default:
                    showListSubject(request, response);
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
                case "/insertSubject":
                    insertSubject(request, response);
                    break;
                case "/updateSubject":
                    updateSubject(request, response);
                    break;
                default:
                    break;
            }
        } catch (SQLException | NoSuchAlgorithmException | ParseException ex) {
            throw new ServletException(ex);
        }
    }

    private void showListSubject(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Subject> listSubject = subjectDao.getAll();
        request.setAttribute("listSubject", listSubject);
        RequestDispatcher dispatcher = request.getRequestDispatcher("subject-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("subject-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Subject existingSubject = subjectDao.get(id).orElseThrow();
        RequestDispatcher dispatcher = request.getRequestDispatcher("subject-form.jsp");
        request.setAttribute("subject", existingSubject);
        dispatcher.forward(request, response);

    }

    private void insertSubject(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException, ParseException {
        String name = request.getParameter("name");

        Subject newSubject = new Subject(name);
        subjectDao.save(newSubject);
        response.sendRedirect("listSubject");
    }

    private void updateSubject(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException, ParseException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        Subject subject = new SubjectDao().get(id).orElseThrow();
        subject.setName(name);
        subjectDao.update(subject);
        response.sendRedirect("listSubject");
    }

    private void deleteSubject(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Subject subject = new SubjectDao().get(id).orElseThrow();
        subjectDao.delete(subject);
        response.sendRedirect("listSubject");

    }
}
package com.vodafone.learner_academy.controller;

import com.vodafone.learner_academy.dao.*;
import com.vodafone.learner_academy.model.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "CourseController", value = "/course")
public class CourseController extends HttpServlet {

    private CourseDao courseDao;
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm"));

    public void init() {
        courseDao = new CourseDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/newCourse":
                    showNewForm(request, response);
                    break;
                case "/editCourse":
                    showEditForm(request, response);
                    break;
                case "/courseAttendance":
                    showAttendanceForm(request, response);
                    break;
                case "/deleteCourse":
                    deleteCourse(request, response);
                    break;
                case "/listCourse":
                default:
                    showListCourse(request, response);
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
                case "/insertCourse":
                    insertCourse(request, response);
                    break;
                case "/updateCourse":
                    updateCourse(request, response);
                    break;
                case "/insertStudent":
                    insertStudent(request, response);
                    break;
                case "/deleteStudent":
                    deleteStudent(request, response);
                    break;
                case "/updateAttendance":
                    updateAttendance(request, response);
                    break;
                default:
                    break;
            }
        } catch (SQLException | NoSuchAlgorithmException | ParseException ex) {
            throw new ServletException(ex);
        }
    }

    private void showListCourse(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Course> listCourse = courseDao.getAll();
        request.setAttribute("listCourse", listCourse);
        RequestDispatcher dispatcher = request.getRequestDispatcher("course-list.jsp");
        request.setAttribute("FORMATTER", FORMATTER);
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("course-form.jsp");
        request.setAttribute("rooms", new RoomDao().getAll());
        request.setAttribute("subjects", new SubjectDao().getAll());
        List<User> teachers = new UserDao().getAll().stream().filter(user -> user.getUserType().getType().equals(UserType.TEACHER)).collect(Collectors.toList());
        request.setAttribute("teachers", teachers);
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Course existingCourse = courseDao.get(id).orElseThrow();
        RequestDispatcher dispatcher = request.getRequestDispatcher("course-form.jsp");
        request.setAttribute("course", existingCourse);
        request.setAttribute("rooms", new RoomDao().getAll());
        request.setAttribute("subjects", new SubjectDao().getAll());
        List<User> teachers = new UserDao().getAll().stream().filter(user -> user.getUserType().getType().equals(UserType.TEACHER)).collect(Collectors.toList());
        request.setAttribute("teachers", teachers);
        dispatcher.forward(request, response);

    }

    private void showAttendanceForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        RequestDispatcher dispatcher = request.getRequestDispatcher("course-attendance.jsp");
        Course existingCourse = courseDao.get(id).orElseThrow();
        request.setAttribute("course", existingCourse);
        List<Student> students = new StudentDao().getAll();
        request.setAttribute("students1", students.stream().filter(student -> student.getLevel() == 1).collect(Collectors.toList()));
        request.setAttribute("students2", students.stream().filter(student -> student.getLevel() == 2).collect(Collectors.toList()));
        request.setAttribute("students3", students.stream().filter(student -> student.getLevel() == 3).collect(Collectors.toList()));
        request.setAttribute("students4", students.stream().filter(student -> student.getLevel() == 4).collect(Collectors.toList()));
        request.setAttribute("FORMATTER", FORMATTER);
        dispatcher.forward(request, response);
    }

    private void insertCourse(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException, ParseException {
        String name = request.getParameter("name");
        Time duration = new Time(Long.parseLong(request.getParameter("duration")));
        Timestamp takePlace = new Timestamp(formatter.parse(request.getParameter("take_place")).getTime());
        int roomId = Integer.parseInt(request.getParameter("room"));
        int subjectId = Integer.parseInt(request.getParameter("subject"));
        int teacherId = Integer.parseInt(request.getParameter("teacher"));

        Room room = new RoomDao().getAll().stream()
                .filter(u -> u.getId() == roomId)
                .findFirst().orElseThrow();
        Subject subject = new SubjectDao().getAll().stream()
                .filter(u -> u.getId() == subjectId)
                .findFirst().orElseThrow();
        User teacher = new UserDao().getAll().stream()
                .filter(u -> u.getId() == teacherId)
                .findFirst().orElseThrow();
        Course newCourse = new Course(name, duration, takePlace, teacher, room, subject);
        courseDao.save(newCourse);
        response.sendRedirect("listCourse");
    }

    private void updateCourse(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException, ParseException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        Time duration = new Time(Long.parseLong(request.getParameter("duration")));
        Timestamp takePlace = new Timestamp(formatter.parse(request.getParameter("take_place")).getTime());
        int roomId = Integer.parseInt(request.getParameter("room"));
        int subjectId = Integer.parseInt(request.getParameter("subject"));
        int teacherId = Integer.parseInt(request.getParameter("teacher"));
        Room room = new RoomDao().getAll().stream()
                .filter(u -> u.getId() == roomId)
                .findFirst().orElseThrow();
        Subject subject = new SubjectDao().getAll().stream()
                .filter(u -> u.getId() == subjectId)
                .findFirst().orElseThrow();
        User teacher = new UserDao().getAll().stream()
                .filter(u -> u.getId() == teacherId)
                .findFirst().orElseThrow();
        Course course = new CourseDao().get(id).orElseThrow();
        course.setName(name);
        course.setDuration(duration);
        course.setTakePlace(takePlace);
        course.setTeacher(teacher);
        course.setSubject(subject);
        course.setRoom(room);
        courseDao.update(course);
        response.sendRedirect("listCourse");
    }

    private void updateAttendance(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException {
        int id = Integer.parseInt(request.getParameter("id"));
        String[] classStudents;
        if (request.getParameterMap().containsKey("class_students"))
            classStudents = request.getParameterValues("class_students");
        else classStudents = new String[]{};


        StudentDao studentDao = new StudentDao();
        Course course = new CourseDao().get(id).orElseThrow();
        if (classStudents.length >course.getRoom().getCapacity())
            throw new RuntimeException("Room Full");
        Collection<Student> students = course.getStudents();
        students.clear();
        for (String studentId : classStudents) {
            int stId = Integer.parseInt(studentId);
            students.add(studentDao.get(stId).orElseThrow());
        }
        courseDao.update(course);
        response.sendRedirect("listCourse");
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Course course = new CourseDao().get(id).orElseThrow();
        courseDao.delete(course);
        response.sendRedirect("listCourse");

    }

    private void insertStudent(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String fullName = request.getParameter("full_name");
        int level = Integer.parseInt(request.getParameter("level"));
        Course course = new CourseDao().get(id).orElseThrow();
        Student newStudent = new Student(fullName, level);
        newStudent.setCourses(List.of(course));
        new StudentDao().save(newStudent);
        response.sendRedirect("courseAttendance?id=" + id);
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String[] classStudents;
        if (request.getParameterMap().containsKey("class_students"))
            classStudents = request.getParameterValues("class_students");
        else classStudents = new String[]{};
        StudentDao studentDao = new StudentDao();
        for (String studentId : classStudents) {
            int stId = Integer.parseInt(studentId);
            studentDao.delete(studentDao.get(stId).orElseThrow());
        }

        response.sendRedirect("courseAttendance?id=" + id);
    }
}
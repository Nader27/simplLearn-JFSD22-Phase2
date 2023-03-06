package com.vodafone.learner_academy.controller;

import com.vodafone.learner_academy.dao.RoomDao;
import com.vodafone.learner_academy.model.Room;
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

@WebServlet(name = "RoomController", value = "/room")
public class RoomController extends HttpServlet {

    private RoomDao roomDao;

    public void init() {
        roomDao = new RoomDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/newRoom":
                    showNewForm(request, response);
                    break;
                case "/editRoom":
                    showEditForm(request, response);
                    break;
                case "/deleteRoom":
                    deleteRoom(request, response);
                    break;
                case "/listRoom":
                default:
                    showListRoom(request, response);
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
                case "/insertRoom":
                    insertRoom(request, response);
                    break;
                case "/updateRoom":
                    updateRoom(request, response);
                    break;
                default:
                    break;
            }
        } catch (SQLException | NoSuchAlgorithmException | ParseException ex) {
            throw new ServletException(ex);
        }
    }

    private void showListRoom(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Room> listRoom = roomDao.getAll();
        request.setAttribute("listRoom", listRoom);
        RequestDispatcher dispatcher = request.getRequestDispatcher("room-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("room-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Room existingRoom = roomDao.get(id).orElseThrow();
        RequestDispatcher dispatcher = request.getRequestDispatcher("room-form.jsp");
        request.setAttribute("room", existingRoom);
        dispatcher.forward(request, response);

    }

    private void insertRoom(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException, ParseException {
        String name = request.getParameter("name");
        int capacity = Integer.parseInt(request.getParameter("capacity"));

        Room newRoom = new Room(name, capacity);
        roomDao.save(newRoom);
        response.sendRedirect("listRoom");
    }

    private void updateRoom(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, NoSuchAlgorithmException, ParseException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        Room room = new RoomDao().get(id).orElseThrow();
        room.setName(name);
        room.setCapacity(capacity);
        roomDao.update(room);
        response.sendRedirect("listRoom");
    }

    private void deleteRoom(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Room room = new RoomDao().get(id).orElseThrow();
        roomDao.delete(room);
        response.sendRedirect("listRoom");

    }
}
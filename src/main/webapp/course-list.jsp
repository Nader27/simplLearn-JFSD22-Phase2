<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="listCourse" scope="request" type="java.util.List<com.vodafone.learner_academy.model.Course>"/>
<jsp:useBean id="FORMATTER" scope="request" type="java.time.format.DateTimeFormatter"/>
<jsp:useBean id="mySession" scope="request" class="com.vodafone.learner_academy.model.Session"/>

<html>

<head>
    <title>Learner Academy</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>

<body>

<header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: tomato">
        <div>
            <a href="${pageContext.request.contextPath}/" class="navbar-brand"> Learner Academy </a>
        </div>

        <ul class="navbar-nav mr-auto">
            <li><a href="<%=request.getContextPath()%>/listCourse" class="nav-link">Courses</a></li>
        </ul>
        <div class="my-2 my-lg-0">
            <ul class="navbar-nav ">
                <li><a href="<%=request.getContextPath()%>/myAccount"
                       class="navbar-brand"><%=mySession.getUser().getUserType().getType()%> <%=mySession.getUser().getFirstName()%> <%=mySession.getUser().getLastName()%>
                </a>
                </li>
                <li><a href="<%=request.getContextPath()%>/logout"
                       class="navbar-brand">logout
                </a>
                </li>
            </ul>
        </div>
    </nav>
</header>
<br>

<div class="row">
    <!-- <div class="alert alert-success" *ngIf='message'>{{message}}</div> -->

    <div class="container">
        <h3 class="text-center">List of Courses</h3>
        <hr>
        <div class="container text-left">

            <a href="<%=request.getContextPath()%>/newCourse" class="btn btn-success">Add
                New Course</a>
        </div>
        <br>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Subject</th>
                <th>Room</th>
                <th>Teacher</th>
                <th>From</th>
                <th>To</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="course" items="${listCourse}">

                <tr>
                    <td>
                        <c:out value="${course.id}"/>
                    </td>
                    <td>
                        <c:out value="${course.name}"/>
                    </td>
                    <td>
                        <c:out value="${course.subject.name}"/>
                    </td>
                    <td>
                        <c:out value="${course.room.name}"/>
                    </td>
                    <td>
                        <c:out value="${course.teacher.firstName} ${course.teacher.lastName}"/>
                    </td>
                    <td>
                        <c:out value="${course.takePlace.toLocalDateTime().format(FORMATTER)}"/>
                    </td>
                    <td>
                        <c:out value="${course.finishAt().toLocalDateTime().format(FORMATTER)}"/>
                    </td>
                    <td><a href="editCourse?id=<c:out value='${course.id}' />">Edit</a> &nbsp;&nbsp;&nbsp; <a
                            href="deleteCourse?id=<c:out value='${course.id}' />">Delete</a> &nbsp;&nbsp;&nbsp; <a
                            href="courseAttendance?id=<c:out value='${course.id}' />">Manage Attendance</a></td>
                </tr>
            </c:forEach>
            <!-- } -->
            </tbody>

        </table>
    </div>
</div>
</body>

</html>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <li><a href="<%=request.getContextPath()%>" class="nav-link">Home</a></li>
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
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">
            <h1>Welcome <%= mySession.getUser().getFirstName()%> <%= mySession.getUser().getLastName()%>
            </h1>
            <br/>
            <div class="btn-group" data-toggle="buttons-checkbox">
                <c:if test="${mySession.user.userType.type == 'Admin'}">
                    <a href="user">
                        <button type="button" class="btn btn-default">Manage User</button>
                    </a>
                </c:if>
                <a href="subject">
                    <button type="button" class="btn btn-default">Manage subject</button>
                </a>
                <c:if test="${mySession.user.userType.type == 'Admin'}">
                <a href="room">
                    <button type="button" class="btn btn-default">Manage Room</button>
                </a>
                </c:if>
                <a href="course">
                    <button type="button" class="btn btn-default">Manage Course</button>
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
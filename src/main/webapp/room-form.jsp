<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="room" scope="request" class="com.vodafone.learner_academy.model.Room"/>
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
            <li><a href="<%=request.getContextPath()%>/listRoom" class="nav-link">Rooms</a></li>
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
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">

            <c:if test="${room.id != 0}">
            <form action="updateRoom" method="post">
                </c:if>
                <c:if test="${room.id == 0}">
                <form action="insertRoom" method="post">
                    </c:if>

                    <caption>
                        <h2 class="text-center">
                            <c:if test="${room.id != 0}">
                                Edit Room
                            </c:if>
                            <c:if test="${room.id == 0}">
                                Add New Room
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${room.id != 0}">
                        <input type="hidden" name="id" value="<c:out value='${room.id}' />"/>
                    </c:if>

                    <fieldset class="form-group">
                        <label>Name <input type="text" value="<c:out value='${room.name}' />"
                                           class="form-control" name="name" required="required"></label>
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Capacity <input type="number" min="1" value="<c:out value='${room.capacity}' />"
                                               class="form-control" name="capacity" required="required"></label>
                    </fieldset>

                    <button type="submit" class="btn btn-success">Save</button>
                </form>
        </div>
    </div>
</div>
</body>

</html>
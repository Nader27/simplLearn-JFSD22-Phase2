<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="user" scope="request" class="com.vodafone.learner_academy.model.User"/>
<jsp:useBean id="user_types" scope="request" type="java.util.List<com.vodafone.learner_academy.model.UserType>"/>
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
            <li><a href="<%=request.getContextPath()%>/listUser" class="nav-link">Users</a></li>
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


            <c:if test="${user.id != 0}">
            <form action="updateUser" method="post">
                </c:if>
                <c:if test="${user.id == 0}">
                <form action="insertUser" method="post">
                    </c:if>

                    <caption>
                        <h2 class="text-center">
                            <c:if test="${user.id != 0}">
                                Edit User
                            </c:if>
                            <c:if test="${user.id == 0}">
                                Add New User
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${user.id != 0}">
                        <input type="hidden" name="id" value="<c:out value='${user.id}' />"/>
                    </c:if>

                    <fieldset class="form-group">
                        <label>First Name <input type="text" value="<c:out value='${user.firstName}' />"
                                                 class="form-control" name="first_name" required="required"></label>
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Last Name<input type="text" value="<c:out value='${user.lastName}' />"
                                               class="form-control" name="last_name" required="required"></label>
                    </fieldset>

                    <fieldset class="form-group">
                        <label>User Email <input type="text" value="<c:out value='${user.email}' />"
                                                 class="form-control" name="email"></label>
                    </fieldset>

                    <fieldset class="form-group">
                        <label>User Type
                            <select name="user_type" class="form-control">
                                <c:forEach items="${user_types}" var="user_type">
                                    <option value="${user_type.id}"
                                            <c:if test="${user_type.id eq user.userType.id}">selected="selected"</c:if>
                                    >${user_type.type}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </fieldset>

                    <c:if test="${user.id == 0}">
                        <fieldset class="form-group">
                            <label>Password <input type="password" value=""
                                                   class="form-control" name="password"></label>
                        </fieldset>

                        <fieldset class="form-group">
                            <label>Verify Password <input type="password" value=""
                                                          class="form-control" name="verify_password"></label>
                        </fieldset>
                    </c:if>

                    <button type="submit" class="btn btn-success">Save</button>
                </form>
        </div>
    </div>
</div>
</body>

</html>
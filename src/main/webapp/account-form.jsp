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
            <li><a href="<%=request.getContextPath()%>/myAccount" class="nav-link">Edit Account</a></li>
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


            <form action="editAccount" method="post">

                <h2 class="text-center">
                    Edit Account
                </h2>

                <fieldset class="form-group">
                    <label>First Name <input type="text" value="<c:out value='${mySession.user.firstName}' />"
                                             class="form-control" name="first_name" required="required"></label>
                </fieldset>

                <fieldset class="form-group">
                    <label>Last Name<input type="text" value="<c:out value='${mySession.user.lastName}' />"
                                           class="form-control" name="last_name" required="required"></label>
                </fieldset>

                <fieldset class="form-group">
                    <label>User Email <input type="text" value="<c:out value='${mySession.user.email}' />"
                                             class="form-control" name="email"></label>
                </fieldset>

                <button type="submit" class="btn btn-success">Update</button>
                <button type="submit" formaction="<%=request.getContextPath()%>/editAccountPassword"
                        formmethod="get" class="btn  btn-warning">Change Password
                </button>
            </form>
        </div>
    </div>
</div>
</body>

</html>
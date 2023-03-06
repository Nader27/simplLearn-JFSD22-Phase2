<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="course" scope="request" class="com.vodafone.learner_academy.model.Course"/>
<jsp:useBean id="teachers" scope="request" type="java.util.List<com.vodafone.learner_academy.model.User>"/>
<jsp:useBean id="subjects" scope="request" type="java.util.List<com.vodafone.learner_academy.model.Subject>"/>
<jsp:useBean id="rooms" scope="request" type="java.util.List<com.vodafone.learner_academy.model.Room>"/>
<jsp:useBean id="mySession" scope="request" class="com.vodafone.learner_academy.model.Session"/>

<html>

<head>
    <title>Learner Academy</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/jquery.durationpicker.css">
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
<div class="container col-md-5">
    <div class="card">
        <div class="card-body">

            <c:if test="${course.id != 0}">
            <form action="updateCourse" method="post">
                </c:if>
                <c:if test="${course.id == 0}">
                <form action="insertCourse" method="post">
                    </c:if>

                    <caption>
                        <h2 class="text-center">
                            <c:if test="${course.id != 0}">
                                Edit Course
                            </c:if>
                            <c:if test="${course.id == 0}">
                                Add New Course
                            </c:if>
                        </h2>
                    </caption>

                    <c:if test="${course.id != 0}">
                        <input type="hidden" name="id" value="<c:out value='${course.id}' />"/>
                    </c:if>

                    <fieldset class="form-group">
                        <label>Name <input type="text" value="<c:out value='${course.name}' />"
                                           class="form-control" name="name" required="required"></label>
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Teacher
                            <select name="teacher" class="form-control">

                                <c:forEach items="${teachers}" var="teacher">
                                    <option value="${teacher.id}"
                                            <c:if test="${teacher.id eq course.teacher.id}">selected="selected"</c:if>
                                    >${teacher.firstName} ${teacher.lastName}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Room
                            <select name="room" class="form-control">

                                <c:forEach items="${rooms}" var="room">
                                    <option value="${room.id}"
                                            <c:if test="${room.id eq course.room.id}">selected="selected"</c:if>
                                    >${room.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Subject
                            <select name="subject" class="form-control">

                                <c:forEach items="${subjects}" var="subject">
                                    <option value="${subject.id}"
                                            <c:if test="${subject.id eq course.subject.id}">selected="selected"</c:if>
                                    >${subject.name}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Start Time<input type="datetime-local" value="<c:out value='${course.takePlace}' />"
                                                class="form-control" name="take_place"></label>
                    </fieldset>

                    <fieldset class="form-group">
                        <label>Duration<input type="hidden"
                                              class="form-control" name="duration"></label>
                    </fieldset>

                    <button type="submit" class="btn btn-success">Save</button>
                </form>
        </div>
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="js/jquery.durationpicker.js"></script>
<script>
    $(document).ready(function () {
        $('input[name=duration]').durationpicker({minsJump: 15, showDays: false});
        $('input[name=duration]').durationpicker('setValue', <c:out value='${course.duration.getTime()}' />);
    });
</script>
</body>

</html>
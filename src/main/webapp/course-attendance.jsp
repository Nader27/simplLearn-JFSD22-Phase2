<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="course" scope="request" type="com.vodafone.learner_academy.model.Course"/>
<jsp:useBean id="students1" scope="request" type="java.util.List<com.vodafone.learner_academy.model.Student>"/>
<jsp:useBean id="students2" scope="request" type="java.util.List<com.vodafone.learner_academy.model.Student>"/>
<jsp:useBean id="students3" scope="request" type="java.util.List<com.vodafone.learner_academy.model.Student>"/>
<jsp:useBean id="students4" scope="request" type="java.util.List<com.vodafone.learner_academy.model.Student>"/>
<jsp:useBean id="newStudent" scope="request" class="com.vodafone.learner_academy.model.Student"/>
<jsp:useBean id="FORMATTER" scope="request" type="java.time.format.DateTimeFormatter"/>
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

            <h2 class="text-center">
                Edit Course <c:out value='${course.name}'/> Attendance <br>
            </h2>
            <h3 class="text-center">
                by <c:out value='${course.teacher.firstName}'/> <c:out value='${course.teacher.lastName}'/> <br>
            </h3>
            <h3 class="text-center">
                at <c:out value='${course.room.name}'/> <c:out
                    value="${course.takePlace.toLocalDateTime().format(FORMATTER)}"/>
            </h3>
            <h5>
                Capacity Left <%=course.getRoom().getCapacity() - course.getStudents().size()%>
            </h5>
            <form action="insertStudent" method="post">
                <fieldset class="form-inline">
                    <input type="hidden" name="id" value="<c:out value='${course.id}' />"/>
                    <label>Add new Student : &nbsp; <input placeholder="Full Name" type="text"
                                                           value="<c:out value='${newStudent.fullName}' />"
                                                           class="form-control" name="full_name">
                        &nbsp;
                        <input placeholder="level" class="form-control" type="number" max="4" min="1"
                               name="level"></label>
                    &nbsp;
                    <button type="submit" class="btn btn-info">Add</button>
                </fieldset>
            </form>
            <form action="updateAttendance" method="post">
                <input type="hidden" name="id" value="<c:out value='${course.id}' />"/>

                <br>
                <fieldset class="form-group">
                    <h6>
                        Level 1
                    </h6>
                    <c:forEach items="${students1}" var="student">
                        <div class="form-check">
                            <input class="form-check-input" id="class_students<c:out value='${student.id}' />" type="checkbox" name="class_students"
                                   value="<c:out value='${student.id}'/>"
                                   <c:if test="${course.students.contains(student)}">checked="checked"</c:if>>
                            <label for="class_students<c:out value='${student.id}' />" class="form-check-label"><c:out
                                    value='${student.fullName}'/></label>

                        </div>
                    </c:forEach>
                    <br>
                    <h6>
                        Level 2
                    </h6>
                    <c:forEach items="${students2}" var="student">
                        <div class="form-check">
                            <input class="form-check-input" id="class_students<c:out value='${student.id}' />" type="checkbox" name="class_students"
                                   value="<c:out value='${student.id}'/>"
                                   <c:if test="${course.students.contains(student)}">checked="checked"</c:if>>
                            <label for="class_students<c:out value='${student.id}' />" class="form-check-label"><c:out
                                    value='${student.fullName}'/></label>

                        </div>
                    </c:forEach>
                    <br>
                    <h6>
                        Level 3
                    </h6>
                    <c:forEach items="${students3}" var="student">
                        <div class="form-check">
                            <input class="form-check-input" id="class_students<c:out value='${student.id}' />" type="checkbox" name="class_students"
                                   value="<c:out value='${student.id}'/>"
                                   <c:if test="${course.students.contains(student)}">checked="checked"</c:if>>
                            <label for="class_students<c:out value='${student.id}' />" class="form-check-label"><c:out
                                    value='${student.fullName}'/></label>

                        </div>
                    </c:forEach>
                    <br>
                    <h6>
                        Level 4
                    </h6>
                    <c:forEach items="${students4}" var="student">
                        <div class="form-check">
                            <input id="class_students<c:out value='${student.id}' />" class="form-check-input" type="checkbox" name="class_students"
                                   value="<c:out value='${student.id}'/>"
                                   <c:if test="${course.students.contains(student)}">checked="checked"</c:if>>
                            <label for="class_students<c:out value='${student.id}' />" class="form-check-label"><c:out
                                    value='${student.fullName}'/></label>

                        </div>
                    </c:forEach>
                </fieldset>
                <br>
                <div class="d-xl-inline-block">
                    <button type="submit" class="btn btn-success">Save</button>
                    &nbsp;&nbsp;
                    <button type="submit" formaction="<%=request.getContextPath()%>/deleteStudent"
                            class="btn btn-danger" formmethod="post">Delete Selected Students
                    </button>
                </div>
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
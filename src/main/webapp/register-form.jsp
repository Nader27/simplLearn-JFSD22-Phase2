<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/login" class="nav-link">Register</a></li>
        </ul>
    </nav>
</header>
<div class="container mt-5">
    <div class="row d-flex justify-content-center">
        <div class="col-md-6">
            <div class="card px-5 py-5" id="form">
                <form action="doRegister" method="post">
                    <!-- First Name input -->
                    <div class="form-outline mb-4">
                        <label class="form-label" for="first_name">First Name</label>
                        <input type="text" id="first_name" name="first_name" required class="form-control"/>
                    </div>
                    <!-- Last Name input -->
                    <div class="form-outline mb-4">
                        <label class="form-label" for="last_name">Last Name</label>
                        <input type="text" id="last_name" name="last_name" required class="form-control"/>
                    </div>
                    <!-- Email input -->
                    <div class="form-outline mb-4">
                        <label class="form-label" for="email">Email address</label>
                        <input type="email" id="email" required name="email" class="form-control"/>
                    </div>

                    <!-- Password input -->
                    <div class="form-outline mb-4">
                        <label class="form-label" for="password">Password</label>
                        <input type="password" id="password" name="password" required class="form-control"/>
                    </div>

                    <!-- Password input -->
                    <div class="form-outline mb-4">
                        <label class="form-label" for="verify_password">Verify Password</label>
                        <input type="password" id="verify_password" name="verify_password" required
                               class="form-control"/>
                    </div>

                    <!-- Submit button -->
                    <button type="submit" class="btn btn-primary btn-block mb-4">Sign up</button>

                    <!-- Login buttons -->
                    <div class="text-center">
                        <p>Already have account? <a href="login">Login</a></p>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

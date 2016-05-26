<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>${message}</title>

<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/welcome.css" rel="stylesheet">
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>
<body ng-app="wcp" class="ng-cloak">
	<div class="generic-container" ng-controller="LoginController">
		<h1 align="center">${message}</h1>
		<div class="loginmodal-container">

			<form class="form-signin" ng-submit="login()" name="loginForm">
				<h3 class="form-signin-heading" align="center">Sign In</h3>
				
				<input type="text" ng-model="username" id="uname" class="username form-control input-sm" name="user" placeholder="Username"> 
				<input type="password" ng-model="password" id="password" name="pass" placeholder="Password">
				 <input type="submit" name="login" class="login loginmodal-submit" value="Login">
			</form>

			<div class="login-help">
				<a href="#">Register</a>
			</div>
		</div>
	</div>

	<!-- Scripts always at the end of body-->

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="resources/js/jars/bootstrap.min.js"></script>


	<script src = "https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.js"></script>
    <script src = "https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-route.js"></script>
    <script src = "https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-resource.js"></script>
    <script src = "https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular-cookies.js"></script>
    <script src="<c:url value='/resources/js/app.js' />"></script>
	<script src="<c:url value='/resources/js/service/user_service.js' />"></script>
	<script
		src="<c:url value='/resources/js/controller/user_controller.js' />"></script>
	
</body>
</html>
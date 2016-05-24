<!DOCTYPE html>
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
  <body>
  
    	 
				<div class="loginmodal-container">
					<h1>${message}</h1><br>
				  <form>
					<input type="text" name="user" placeholder="Username">
					<input type="password" name="pass" placeholder="Password">
					<input type="submit" name="login" class="login loginmodal-submit" value="Login">
				  </form>
					
				  <div class="login-help">
					<a href="#">Register</a> - <a href="#">Forgot Password</a>
				  </div>
				</div>
	
		  


    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="resources/js/bootstrap.min.js"></script>
  </body>
</html>
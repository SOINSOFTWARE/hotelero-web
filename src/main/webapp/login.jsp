<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html class="bg-black">
	<head>
		<title>Hotelero | Login</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport' />
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="css/font-awesome.min.css" rel="stylesheet" type="text/css" />
        <link href="css/ionicons.min.css" rel="stylesheet" type="text/css" />
        <link href="css/AdminLTE.css" rel="stylesheet" type="text/css" />
	</head>
    <body class="bg-black" onload="document.loginForm.username.focus();">
    	<br/>
    	<c:if test="${not empty msg}">
    		<div class="alert alert-danger alert-dismissable">
    			<i class="fa fa-ban"></i>
    			<button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
    			<b>Error!</b> ${msg}
    		</div>
    	</c:if>
        <div class="form-box" id="login-box">
            <div class="header">Iniciar sesi&oacute;n</div>
            <form name="loginForm" method='POST'>
                <div class="body bg-gray">
                    <div class="form-group">
                        <input type='text' name='username' class='form-control' required='required' placeholder='Usuario'
                        	autocomplete='off' value='${username}' ></input>
                    </div>
                    <div class="form-group">
                        <input type='password' name='password' class='form-control' required='required' placeholder='Contrase&ntilde;a'
                        	autocomplete='off' value='${password}' ></input>
                    </div>
                </div>
                <div class="footer">
                    <input name="submit" type="submit" class="btn bg-olive btn-block" value="Iniciar sesi&oacute;n" />
                </div>
            </form>
        </div>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script src="js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>
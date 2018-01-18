<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page session="true" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html class="bg-black">
    <head>
        <title>Al Tablero | Cambio de contrase&ntilde;a</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport' />
        <link href="<c:url value="/res/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/res/css/font-awesome.min.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/res/css/ionicons.min.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/res/css/AdminLTE.css" />" rel="stylesheet" type="text/css" />
        <link href="<c:url value="/res/css/jquery-ui/jquery-ui.min.css" />" rel="stylesheet" type="text/css" />
    </head>    
    <body class="bg-black">
        <div class="form-box" id="password-box">
            <div class="header">Cambio de contrase&ntilde;a</div>
            <form id="passwordForm" name="passwordForm" method="POST"
                  action="<c:url value='/contrasena' />" >
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="body bg-gray">
                    <div class="form-group">
                        <input id="username" name="username" 
                               class="form-control" required="true" 
                               placeholder="N&uacute;mero de documento" 
                               autocomplete="off"
                               <c:choose>
                                   <c:when test="${user == null}">type="text"</c:when>
                                   <c:otherwise>type="hidden" value="${user.documentNumber}"</c:otherwise>
                               </c:choose> />
                    </div>
                    <div id="divCurrentPassword" class="form-group">
                        <input type="password" id="currentPassword" name="currentPassword" 
                               class="form-control" required="true" placeholder="Actual contrase&ntilde;a"
                               autocomplete="off" maxlength="8" />
                    </div>
                    <div id="divNewPassword1" class="form-group">
                        <input type="password" id="newPassword1" name="newPassword1" 
                               class="form-control" required="true" placeholder="Nueva contrase&ntilde;a"
                               autocomplete="off" maxlength="8" />
                    </div>
                    <div id="divNewPassword2" class="form-group">
                        <input type="password" id="newPassword2" name="newPassword2" 
                               class="form-control" required="true" placeholder="Confirmar contrase&ntilde;a"
                               autocomplete="off" maxlength="8" />
                    </div>
                </div>
                <div class="footer">  
                    <input id="save-link" name="save-link" type="submit"
                           class="btn bg-olive btn-block" value="Guardar" />
                </div>
            </form>
        </div>
        <div id="fill-fields-dialog" title="Error" style="display: none">
            <p><span class="ui-icon ui-icon-cancel" style="float:left; margin:2px 7px 20px 0;"></span>Por favor complete todos los campos</p>
        </div>
        <div id="current-password-dialog" title="Error" style="display: none">
            <p><span class="ui-icon ui-icon-cancel" style="float:left; margin:2px 7px 20px 0;"></span>La contrase&ntilde;a actual es incorrecta.</p>
        </div>
        <div id="new-password-dialog" title="Error" style="display: none">
            <p><span class="ui-icon ui-icon-cancel" style="float:left; margin:2px 7px 20px 0;"></span>La nueva contrase&ntilde;a no coincide.</p>
        </div>
        <div id="same-password-dialog" title="Error" style="display: none">
            <p><span class="ui-icon ui-icon-cancel" style="float:left; margin:2px 7px 20px 0;"></span>La nueva contrase&ntilde;a es igual a la anterior.</p>
        </div>
        <div id="short-password-dialog" title="Error" style="display: none">
            <p><span class="ui-icon ui-icon-cancel" style="float:left; margin:2px 7px 20px 0;"></span>La contrase&ntilde;a debe contener al menos 4 caracteres.</p>
        </div>                
        <div id="not-reg-password-dialog" title="Error" style="display: none">
            <p><span class="ui-icon ui-icon-cancel" style="float:left; margin:2px 7px 20px 0;"></span>La contrase&ntilde;a debe comenzar por letras, tambi&eacute;n debe contener n&uacute;meros y no debe contener caracteres especiales.</p>
        </div>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
        <script src="<c:url value="/res/javascript/bootstrap.min.js" />" type="text/javascript"></script>
        <script src="<c:url value="/res/javascript/AdminLTE/app.js" />" type="text/javascript"></script>
        <script src="<c:url value="/res/javascript/altablero.js" />" type="text/javascript"></script>
        <script src="<c:url value="/res/javascript/jquery-ui.min.js" />" type="text/javascript"></script>
        <script type="text/javascript">
            $("#save-link").click(function(event) {
                var username = $.trim($("#username").val());
                var currentPass = $.trim($("#currentPassword").val());
                var newPass1 = $.trim($("#newPassword1").val());
                var newPass2 = $.trim($("#newPassword2").val());
                if (username === "" || currentPass === "" || newPass1 === "" || newPass2 === "") {
                    showFillFieldsDialog();
                    event.preventDefault();
                } else {
                    <c:if test="${user != null}">
                            if ("${user.password}" !== currentPass) {
                                showCurrentPasswordDialog();
                                event.preventDefault();
                            }
                    </c:if>
                    if (newPass1 !== newPass2) {
                        showNewPasswordDialog();
                        event.preventDefault();
                        return;
                    }
                    if (currentPass === newPass1) {
                        showSamePasswordDialog();
                        event.preventDefault();
                        return;
                    }
                    if (newPass1.length < 4) {
                        showShortPasswordDialog();
                        event.preventDefault();
                        return;
                    }
                    var pat1 = /\w+\d+\w*/;
                    var pat2 = /\W+/;
                    if (!pat1.test(newPass1) || pat2.test(newPass1)) {
                        showRegPasswordDialog();
                        event.preventDefault();
                        return;
                    }
                }
            });
            
            function showFillFieldsDialog() {
                $("#fill-fields-dialog").dialog({
                    autoOpen: false,
                    width: 400,
                    modal: true,
                    resizable: false,
                    buttons: [
                    {
                        text: "Ok",
                        click: function() {
                            $(this).dialog("close");
                        }
                    }]
                });
                $("#fill-fields-dialog").dialog("open");
            }
            
            function showCurrentPasswordDialog() {
                $("#current-password-dialog").dialog({
                    autoOpen: false,
                    width: 400,
                    modal: true,
                    resizable: false,
                    buttons: [
                    {
                        text: "Ok",
                        click: function() {
                            $(this).dialog("close");
                        }
                    }]
                });
                $("#current-password-dialog").dialog("open");
            }
            
            function showNewPasswordDialog() {
                $("#new-password-dialog").dialog({
                    autoOpen: false,
                    width: 400,
                    modal: true,
                    resizable: false,
                    buttons: [
                    {
                        text: "Ok",
                        click: function() {
                            $(this).dialog("close");
                        }
                    }]
                });
                $("#new-password-dialog").dialog("open");
            }
            
            function showSamePasswordDialog() {
                $("#same-password-dialog").dialog({
                    autoOpen: false,
                    width: 400,
                    modal: true,
                    resizable: false,
                    buttons: [
                    {
                        text: "Ok",
                        click: function() {
                            $(this).dialog("close");
                        }
                    }]
                });
                $("#same-password-dialog").dialog("open");
            }
            
            function showShortPasswordDialog() {
                $("#short-password-dialog").dialog({
                    autoOpen: false,
                    width: 400,
                    modal: true,
                    resizable: false,
                    buttons: [
                    {
                        text: "Ok",
                        click: function() {
                            $(this).dialog("close");
                        }
                    }]
                });
                $("#short-password-dialog").dialog("open");
            }
            
            function showRegPasswordDialog() {
                $("#not-reg-password-dialog").dialog({
                    autoOpen: false,
                    width: 400,
                    modal: true,
                    resizable: false,
                    buttons: [
                    {
                        text: "Ok",
                        click: function() {
                            $(this).dialog("close");
                        }
                    }]
                });
                $("#not-reg-password-dialog").dialog("open");
            }
        </script>
    </body>
</html>
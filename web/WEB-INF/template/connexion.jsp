<%--
  Created by IntelliJ IDEA.
  User: Zniber
  Date: 15/08/2020
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>EHTP - Connexion</title>
    <%@include file="inc/css.jsp"%>
</head>

<body class="bg-gradient-login">
<!-- Login Content -->
<div class="container-login">
    <div class="row justify-content-center">
        <div class="col-xl-10 col-lg-12 col-md-9">
            <div class="card shadow-sm my-5">
                <div class="card-body p-0">
                    <div class="row">
                        <div class="col-lg-12">
                            <div class="login-form">
                                <div>
                                    <div class="sidebar-brand-icon text-center">
                                        <img src="<%=request.getContextPath()%>/inc/img/logo/ehtp-logo.png" style="max-width: 500px">
                                    </div>
                                </div>
                                <br /><br />
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">Connexion</h1>
                                </div>
                                <form class="user" method="post" action="connexion">
                                    <div class="form-group">
                                        <input type="text" id="username" name="username" class="form-control" id="exampleInputEmail" aria-describedby="emailHelp"
                                               placeholder="Entrez votre nom d'utilisateur" value="${utilisateur.username}">
                                        <small style="color: #900">${form.erreurs['username']}</small>
                                    </div>
                                    <div class="form-group">
                                        <input type="password" id="password" name="password" class="form-control" id="exampleInputPassword"
                                               placeholder="Mot de passe">
                                        <small style="color: #900">${form.erreurs['password']}</small>
                                    </div>
                                    <hr>
                                    <div class="form-group">
                                        <input type="submit" id="connexion" name="connexion" value="Connexion" class="btn btn-primary btn-block" />
                                    </div>
                                </form>
                                <hr>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Login Content -->
<%@include file="inc/js.jsp"%>
</body>

</html>

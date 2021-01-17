<%--
  Created by IntelliJ IDEA.
  User: Zniber
  Date: 16/08/2020
  Time: 12:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>EHTP - Erreur</title>
    <%@include file="inc/css.jsp"%>
</head>

<body id="page-top">
<div id="wrapper">
    <!-- Sidebar -->
    <%@include file="inc/sidebar.jsp"%>
    <!-- Sidebar -->

    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <!-- TopBar -->
            <%@include file="inc/topbar.jsp"%>
            <!-- Topbar -->
        </div>

        <!-- Container Fluid-->
        <div class="container-fluid" id="container-wrapper">
            <div class="text-center">
                <img src="<%=request.getContextPath()%>/inc/img/error.svg" style="max-height: 100px;" class="mb-3">
                <h3 class="text-gray-800 font-weight-bold">Oopss!</h3>
                <p class="lead text-gray-800 mx-auto">Une erreur est survenue, veuillez essayer plus tard.</p>
                <a href="<%=request.getContextPath()%>/home">&larr; Back to Dashboard</a>
            </div>
            <br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
        </div>
        <!-- Container Fluid-->

        <!-- Footer -->
        <%@include file="inc/footer.jsp"%>
        <!-- Footer -->
    </div>
</div>

<!-- Scroll to top -->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>
<%@include file="inc/js.jsp"%>>
</body>

</html>

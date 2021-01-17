<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>EHTP - Home</title>
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
            <div class="d-sm-flex align-items-center justify-content-between mb-4">
                <h1 class="h3 mb-0 text-gray-800">Profile</h1>
            </div>
            <div class="row">
                <div class="col-lg-6">
                    <div class="card mb-4">
                        <div class="card-body">
                            <div class="alert alert-success" role="alert">
                                <output class="pt-3"><b>CIN                 : </b>${sessionScope.sessionUtilisateur.cin}</output><br />
                                <output class="pt-3"><b>Nom                 : </b>${sessionScope.sessionUtilisateur.nom}</output><br />
                                <output class="pt-3"><b>Prenom              : </b>${sessionScope.sessionUtilisateur.prenom}</output><br />
                                <output class="pt-3"><b>Email               : </b>${sessionScope.sessionUtilisateur.email}</output><br />
                                <output class="pt-3"><b>Username            : </b>${sessionScope.sessionUtilisateur.username}</output><br />
                                <output class="pt-3"><b>Numéro de téléphone : </b>${sessionScope.sessionUtilisateur.numeroTel}</output><br />
                                <%
                                    if(session.getAttribute("sessionUtilisateur") instanceof Administrateur){
                                %>
                                <output class="pt-3"><b>Poste : </b><%=((Administrateur) session.getAttribute("sessionUtilisateur")).getPoste()%></output><br />
                                <%
                                    } else if(session.getAttribute("sessionUtilisateur") instanceof Secretaire){
                                %>
                                <output class="pt-3"><b>Département : </b><%=((Secretaire) session.getAttribute("sessionUtilisateur")).getDepartement()%></output><br />
                                <output class="pt-3"><b>Bureau : </b><%=((Secretaire) session.getAttribute("sessionUtilisateur")).getBureau()%></output><br />
                                <%
                                    } else if(session.getAttribute("sessionUtilisateur") instanceof Professeur){
                                %>
                                <output class="pt-3"><b>Département : </b><%=((Professeur) session.getAttribute("sessionUtilisateur")).getDepartement()%></output><br />
                                <output class="pt-3"><b>Filière : </b><%=((Professeur) session.getAttribute("sessionUtilisateur")).getFiliere()%></output><br />
                                <output class="pt-3"><b>Matière : </b><%=((Professeur) session.getAttribute("sessionUtilisateur")).getMatiere()%></output><br />
                                <%
                                    }
                                %>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
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
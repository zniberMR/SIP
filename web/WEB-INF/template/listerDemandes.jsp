<%@ page import="ma.ac.ehtp.sip.entities.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.joda.time.format.DateTimeFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>EHTP - Liste des demandes existantes</title>
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
            <div class="row">
                <div class="col-lg-12 mb-4">
                    <!-- Simple Tables -->
                    <div class="card">
                        <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                            <h6 class="m-0 font-weight-bold text-primary">Liste des demandes existantes</h6>
                        </div>
                        <div class="table-responsive">
                            <table class="table align-items-center table-flush">
                                <thead class="thead-light">
                                <tr>
                                    <th>ID du demande</th>
                                    <th>Date</th>
                                    <th>CIN</th>
                                    <th>Nom</th>
                                    <th>Prénom</th>
                                    <th>Email</th>
                                    <th>Numéro de téléphone</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <%
                                    Map<Long, Demande> contextDemandes = (Map<Long, Demande>)config.getServletContext().getAttribute("contextDemandes");
                                    for(Map.Entry<Long, Demande> entry : contextDemandes.entrySet()){
                                        request.setAttribute("mapDemandes", entry);
                                %>
                                <tr>
                                    <td><a href="#">${ mapDemandes.value.id }</a></td>
                                    <td><%=entry.getValue().getDate().toString(DateTimeFormat.forPattern( "dd/MM/yyyy HH:mm:ss" ))%></td>
                                    <td>${ mapDemandes.value.cin }</td>
                                    <td>${ mapDemandes.value.nom }</td>
                                    <td>${ mapDemandes.value.prenom }</td>
                                    <td>${ mapDemandes.value.email }</td>
                                    <td>${ mapDemandes.value.numeroTel }</td>
                                    <td>
                                        <form id="${ mapDemandes.key }_form" method="post" action="suppressionDemande">
                                            <input hidden="hidden" id="id" name="id" value="${ mapDemandes.key }">
                                            <a id="${ mapDemandes.key }_envoyer" class="btn btn-danger">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                        </form>
                                    </td>
                                </tr>
                                <%
                                    }
                                %>
                                </tbody>
                            </table>
                        </div>
                        <div class="card-footer"></div>
                    </div>
                </div>
            </div>
            <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
            <!--Row-->
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
    <%@include file="inc/js.jsp"%>
    <script type="text/javascript">
        <%
        for(Long index : contextDemandes.keySet()){
            request.setAttribute("index", index);
        %>
        document.getElementById("${index}_envoyer").onclick = function () {
            document.getElementById("${index}_form").submit();
        }
        <%
        }
        %>
    </script>
</body>

</html>
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
    <title>EHTP - Liste des commandes existantes</title>
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
                            <h6 class="m-0 font-weight-bold text-primary">Liste des commandes existantes</h6>
                        </div>
                        <div class="table-responsive">
                            <table class="table align-items-center table-flush">
                                <thead class="thead-light">
                                <tr>
                                    <th>ID du commande</th>
                                    <th>Professeur</th>
                                    <th>Date</th>
                                    <th>Désignation</th>
                                    <th>Quantité</th>
                                    <th>Etat</th>
                                    <th>Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                <%
                                    Map<Long, Commande> contextCommandes = (Map<Long, Commande>)config.getServletContext().getAttribute("contextCommandes");
                                    for(Map.Entry<Long, Commande> entry : contextCommandes.entrySet()){
                                        request.setAttribute("mapCommandes", entry);
                                %>
                                <tr>
                                    <td><a href="#">${ mapCommandes.value.id }</a></td>
                                    <td>${ mapCommandes.value.utilisateur.nom } ${ mapCommandes.value.utilisateur.prenom }</td>
                                    <td><%=entry.getValue().getDate().toString(DateTimeFormat.forPattern( "dd/MM/yyyy HH:mm:ss" ))%></td>
                                    <td>${ mapCommandes.value.designation }</td>
                                    <td>${ mapCommandes.value.quantite }</td>
                                    <td><span class="badge
                                        <%
                                            if(entry.getValue().getEtat().equals("Annulee"))
                                                out.print("badge-danger");
                                            else if(entry.getValue().getEtat().equals("En attente"))
                                                out.print("badge-light");
                                            else if(entry.getValue().getEtat().equals("En progres"))
                                                out.print("badge-info");
                                            else if(entry.getValue().getEtat().equals("Terminee"))
                                                out.print("badge-warning");
                                            else if(entry.getValue().getEtat().equals("Livree"))
                                                out.print("badge-success");
                                        %>
                                        ">
                                        ${ mapCommandes.value.etat }</span></td>
                                    <td>
                                        <div id="${ mapCommandes.key }_envoyer2">
                                            <form action="modificationCommande" method="post">
                                                <input hidden="hidden" id="id" name="id" value="${ mapCommandes.key }">
                                                <input id="cause" name="cause" type="text" placeholder="Entrez la cause de l'annulation de la commande ici.">
                                                <input id="envoyer2" type="submit" class="btn btn-sm btn-primary" value="Envoyer">
                                            </form>
                                        </div>
                                        <input id="${ mapCommandes.key }_envoyer1" <%
                                                                    if(!entry.getValue().getEtat().equals("En attente"))
                                                                        out.print(" style=\"pointer-events: none;\" ");
                                                                 %> class="btn btn-sm btn-primary" value="Annulez la commande">
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
            <br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
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
        for(Long index : contextCommandes.keySet()){
            request.setAttribute("index", index);
        %>
        $(document).ready(function(){
            $("div#${index}_envoyer2").hide();
            jQuery('input[id=${index}_envoyer1]').click(function(){
                $("div#${index}_envoyer2").show();
                $("input#${index}_envoyer1").hide();
            });
        });
        <%
        }
        %>
    </script>
</body>

</html>
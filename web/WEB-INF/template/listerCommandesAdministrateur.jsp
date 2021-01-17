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
                                    <td>
                                        <div class="dropdown" id="${ mapCommandes.key }_envoyer">
                                            <a class="btn btn-primary dropdown-toggle" href="#" role="button" id="dropdownMenuLink"
                                               data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> ${ mapCommandes.value.etat } </a>
                                            <div class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                                                <%if(!entry.getValue().getEtat().equals("Annulee")){%>
                                                <input id="${ mapCommandes.key }_envoyer1" class="dropdown-item" value="Annulée">
                                                <%}if(!entry.getValue().getEtat().equals("En attente")){%>
                                                <form action="changerEtat" method="post">
                                                    <input hidden="hidden" id="id" name="id" value="${ mapCommandes.key }">
                                                    <input hidden="hidden" id="nouvelleEtat" name="nouvelleEtat" value="En attente">
                                                    <input class="dropdown-item" type="submit" value="En attente">
                                                </form>
                                                <%}if(!entry.getValue().getEtat().equals("En progres")){%>
                                                <form action="changerEtat" method="post">
                                                    <input hidden="hidden" id="id" name="id" value="${ mapCommandes.key }">
                                                    <input hidden="hidden" id="nouvelleEtat" name="nouvelleEtat" value="En progres">
                                                    <input class="dropdown-item" type="submit" value="En progrès">
                                                </form>
                                                <%}if(!entry.getValue().getEtat().equals("Terminee")){%>
                                                <form action="changerEtat" method="post">
                                                    <input hidden="hidden" id="id" name="id" value="${ mapCommandes.key }">
                                                    <input hidden="hidden" id="nouvelleEtat" name="nouvelleEtat" value="Terminee">
                                                    <input class="dropdown-item" type="submit" value="Terminée">
                                                </form>
                                                <%}if(!entry.getValue().getEtat().equals("Livree")){%>
                                                <form action="changerEtat" method="post">
                                                    <input hidden="hidden" id="id" name="id" value="${ mapCommandes.key }">
                                                    <input hidden="hidden" id="nouvelleEtat" name="nouvelleEtat" value="Livree">
                                                    <input class="dropdown-item" type="submit" value="Livrée">
                                                </form>
                                                <%}%>
                                            </div>
                                        </div>
                                        <div id="${ mapCommandes.key }_envoyer2">
                                            <form action="changerEtat" method="post">
                                                <input hidden="hidden" id="id" name="id" value="${ mapCommandes.key }">
                                                <input hidden="hidden" id="nouvelleEtat" name="nouvelleEtat" value="Annulee">
                                                <input id="cause" name="cause" type="text" placeholder="Entrez la cause de l'annulation de la commande ici.">
                                                <input type="submit" class="btn btn-sm btn-primary" value="Envoyer">
                                            </form>
                                        </div>
                                    </td>
                                    <td>
                                        <form id="${ mapCommandes.key }_form" action="modificationCommande" method="post">
                                            <input hidden="hidden" id="id" name="id" value="${ mapCommandes.key }">
                                            <a id="${ mapCommandes.key }_envoyerform" type="submit" class="btn btn-danger">
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
        $(document).ready(function(){
            <%
            for(Long index : contextCommandes.keySet()){
                request.setAttribute("index", index);
            %>
            $("div#${ index }_envoyer2").hide();
            jQuery('input[id=${ index }_envoyer1]').click(function(){
                $("div#${ index }_envoyer2").show();
                $("div#${ index }_envoyer").hide();
            });
            <%
                }
            %>
        });
        <%
            for(Long index : contextCommandes.keySet()){
                request.setAttribute("index", index);
        %>
        document.getElementById("${ index }_envoyerform").onclick = function () {
            document.getElementById("${ index }_form").submit();
        }
        <%
        }
        %>
    </script>
</body>

</html>
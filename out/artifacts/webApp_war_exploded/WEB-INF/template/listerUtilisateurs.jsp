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
    <title>EHTP - Liste des utilisateurs existantes</title>
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
                            <h6 class="m-0 font-weight-bold text-primary">Liste des utilisateurs existantes</h6>
                        </div>
                        <div class="table-responsive">
                            <table class="table align-items-center table-flush">
                                <thead class="thead-light">
                                <tr>
                                    <th>Rôle&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                    <th>ID de l'utilisateur&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                    <th>CIN&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                    <th>Nom&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                    <th>Prénom&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                    <th>Email&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                    <th>Nom d'utilisateur&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                    <th>Numéro de téléphone&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                    <th>Département&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                    <th>Bureau&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                    <th>Filière&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                    <th>Matière&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                    <th>Poste&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                    <th>Action&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
                                </tr>
                                </thead>
                                <tbody>
                                <%
                                    Utilisateur             sessionUtilisateur = (Utilisateur)session.getAttribute("sessionUtilisateur");
                                    Map<Long, Utilisateur> contextUtilisateurs = (Map<Long, Utilisateur>)config.getServletContext().getAttribute("contextUtilisateurs");
                                    for(Map.Entry<Long, Utilisateur> entry : contextUtilisateurs.entrySet()){
                                        request.setAttribute("mapUtilisateurs", entry);
                                %>
                                <tr id="${ mapUtilisateurs.value.id }_tr1">
                                    <td>
                                    <%
                                        if(entry.getValue() instanceof Administrateur)
                                            out.println("Administrateur");
                                        else if(entry.getValue() instanceof Professeur)
                                            out.println("Professeur");
                                        else if(entry.getValue() instanceof Secretaire)
                                            out.println("Secretaire");
                                    %>
                                    </td>
                                    <td><a href="#">${ mapUtilisateurs.value.id }</a></td>
                                    <td>${ mapUtilisateurs.value.cin }</td>
                                    <td>${ mapUtilisateurs.value.nom }</td>
                                    <td>${ mapUtilisateurs.value.prenom }</td>
                                    <td>${ mapUtilisateurs.value.email }</td>
                                    <td>${ mapUtilisateurs.value.username }</td>
                                    <td>${ mapUtilisateurs.value.numeroTel }</td>
                                    <td>
                                        <%
                                            if(entry.getValue() instanceof Professeur)
                                                out.print(((Professeur)(entry.getValue())).getDepartement());
                                            else if(entry.getValue() instanceof Secretaire)
                                                out.print(((Secretaire)(entry.getValue())).getDepartement());
                                            else
                                                out.print("-");
                                        %>
                                    </td>
                                    <td>
                                        <%
                                            if(entry.getValue() instanceof Secretaire)
                                                out.print(((Secretaire)(entry.getValue())).getBureau());
                                            else
                                                out.print("-");
                                        %>
                                    </td>
                                    <td>
                                        <%
                                            if(entry.getValue() instanceof Professeur)
                                                out.print(((Professeur)(entry.getValue())).getFiliere());
                                            else
                                                out.print("-");
                                        %>
                                    </td>
                                    <td>
                                        <%
                                            if(entry.getValue() instanceof Professeur)
                                                out.print(((Professeur)(entry.getValue())).getMatiere());
                                            else
                                                out.print("-");
                                        %>
                                    </td>
                                    <td>
                                        <%
                                            if(entry.getValue() instanceof Administrateur)
                                                out.print(((Administrateur)(entry.getValue())).getPoste());
                                            else
                                                out.print("-");
                                        %>
                                    </td>
                                    <td>
                                        <form id="${ mapUtilisateurs.value.id }_form1" method="post" action="suppressionUtilisateur">
                                            <input hidden="hidden" id="id" name="id" value="${ mapUtilisateurs.key }">
                                            <a id="${ mapUtilisateurs.value.id }_envoyer1" <%
                                                                if(entry.getValue().getId() == sessionUtilisateur.getId())
                                                                    out.print(" style=\"pointer-events: none;\" ");
                                                             %> class="btn btn-danger">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                            <a id="${ mapUtilisateurs.value.id }_envoyer" <%
                                                                if(entry.getValue().getId() == sessionUtilisateur.getId())
                                                                    out.print(" style=\"pointer-events: none;\" ");
                                                            %> class="btn btn-warning">
                                                <i class="fas fa-pen"></i>
                                            </a>
                                        </form>
                                    </td>
                                </tr>
                                <tr id="${ mapUtilisateurs.value.id }_tr2">
                                    <form method="post" action="modificationUtilisateur">
                                        <input hidden="hidden" id="id" name="id" value="${ mapUtilisateurs.value.id }">
                                        <td>
                                            <%
                                                if(entry.getValue() instanceof Administrateur)
                                                    out.println("Administrateur");
                                                else if(entry.getValue() instanceof Professeur)
                                                    out.println("Professeur");
                                                else if(entry.getValue() instanceof Secretaire)
                                                    out.println("Secretaire");
                                            %>
                                        </td>
                                        <td><a href="#">${ mapUtilisateurs.value.id }</a></td>
                                        <td>
                                            <input type="text" class="form-control" id="cin" name="cin" value="${ empty utilisateurForm.erreurs ? mapUtilisateurs.value.cin : utilisateurForm.erreurs['cin'] }">
                                        </td>
                                        <td>
                                            <input type="text" class="form-control" id="nom" name="nom" value="${ empty utilisateurForm.erreurs ? mapUtilisateurs.value.nom : utilisateurForm.erreurs['nom'] }">
                                        </td>
                                        <td>
                                            <input type="text" class="form-control" id="prenom" name="prenom" value="${ empty utilisateurForm.erreurs ? mapUtilisateurs.value.prenom : utilisateurForm.erreurs['prenom'] }">
                                        </td>
                                        <td>
                                            <input type="text" class="form-control" id="email" name="email" value="${ empty utilisateurForm.erreurs ? mapUtilisateurs.value.email : utilisateurForm.erreurs['email'] }">
                                        </td>
                                        <td>
                                            <input type="text" class="form-control" id="username" name="username" value="${ empty utilisateurForm.erreurs ? mapUtilisateurs.value.username : utilisateurForm.erreurs['username'] }">
                                        </td>
                                        <td>
                                            <input type="text" class="form-control" id="numeroTel" name="numeroTel" value="${ empty utilisateurForm.erreurs ? mapUtilisateurs.value.numeroTel : utilisateurForm.erreurs['numeroTel'] }">
                                        </td>
                                        <td>
                                            <%
                                                if(entry.getValue() instanceof Professeur){
                                            %>
                                                    <input type="text" class="form-control" id="departement" name="departement" value="<%=((Professeur)(entry.getValue())).getDepartement()%>"
                                            <%
                                                }
                                                else if(entry.getValue() instanceof Secretaire){
                                            %>
                                                    <input type="text" class="form-control" id="departement" name="departement" value="<%=((Secretaire)(entry.getValue())).getDepartement()%>"
                                            <%
                                                }
                                                else
                                                    out.print("-");
                                            %>
                                        </td>
                                        <td>
                                            <%
                                                if(entry.getValue() instanceof Secretaire){
                                            %>
                                                    <input type="text" class="form-control" id="bureau" name="bureau" value="<%=((Secretaire)(entry.getValue())).getBureau()%>"
                                            <%
                                                }
                                                else
                                                    out.print("-");
                                            %>
                                        </td>
                                        <td>
                                            <%
                                                if(entry.getValue() instanceof Professeur){
                                            %>
                                                    <input type="text" class="form-control" id="filiere" name="filiere" value="<%=((Professeur)(entry.getValue())).getFiliere()%>"
                                            <%
                                                }
                                                else
                                                    out.print("-");
                                            %>
                                        </td>
                                        <td>
                                            <%
                                                if(entry.getValue() instanceof Professeur){
                                            %>
                                                    <input type="text" class="form-control" id="matiere" name="matiere" value="<%=((Professeur)(entry.getValue())).getMatiere()%>"
                                            <%
                                                }
                                                else
                                                    out.print("-");
                                            %>
                                        </td>
                                        <td>
                                            <%
                                                if(entry.getValue() instanceof Administrateur){
                                            %>
                                                    <input type="text" class="form-control" id="poste" name="poste" value="<%=((Administrateur)(entry.getValue())).getPoste()%>"
                                            <%
                                                }
                                                else
                                                    out.print("-");
                                            %>
                                        </td>
                                        <td>
                                            <input type="submit" class="btn btn-sm btn-primary" value="Envoyer">
                                        </td>
                                    </form>
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
            for(Long index : contextUtilisateurs.keySet()){
                request.setAttribute("index", index);
            %>
            $("tr#${ index }_tr2").hide();
            jQuery('a[id=${ index }_envoyer]').click(function(){
                $("tr#${ index }_tr1").hide();
                $("tr#${ index }_tr2").show();
            });
            <%
            }
            %>
        });
        <%
        for(Long index : contextUtilisateurs.keySet()){
            request.setAttribute("index", index);
        %>
        document.getElementById("${ index }_envoyer1").onclick = function () {
            document.getElementById("${ index }_form1").submit();
        }
        <%
        }
        %>
    </script>
</body>

</html>
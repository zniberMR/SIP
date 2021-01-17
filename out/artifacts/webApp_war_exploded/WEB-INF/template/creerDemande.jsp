<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>EHTP - Création d'une demande</title>
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
            <div class="d-sm-flex align-items-center justify-content-between mb-4"></div>

            <div class="row">
                <div class="col-lg-6">
                    <!-- Form Basic -->
                    <div class="card mb-4">
                        <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                            <h6 class="m-0 font-weight-bold text-primary">Faire une demande</h6>
                        </div>
                        <div class="card-body">
                            <form method="post" action="creationDemande">
                                <div class="form-group">
                                    <label for="cin">Carte d'identité nationale <span style="color: #900">*</span></label>
                                    <input type="text" class="form-control" id="cin" name="cin"
                                           placeholder="Entrez le numéro cin de l'enseignant" value="${demande.cin}">
                                    <small style="color: #900">${demandeForm.erreurs['cin']}</small>
                                </div>
                                <div class="form-group">
                                    <label for="nom">Nom <span style="color: #900">*</span></label>
                                    <input type="text" class="form-control" id="nom" name="nom"
                                           placeholder="Entrez le nom de l'enseignant" value="${demande.nom}">
                                    <small style="color: #900">${demandeForm.erreurs['nom']}</small>
                                </div>
                                <div class="form-group">
                                    <label for="prenom">Prénom <span style="color: #900">*</span></label>
                                    <input type="text" class="form-control" id="prenom" name="prenom"
                                           placeholder="Entrez le prénom de l'enseignant" value="${demande.prenom}">
                                    <small style="color: #900">${demandeForm.erreurs['prenom']}</small>
                                </div>
                                <div class="form-group">
                                    <label for="email">Email <span style="color: #900">*</span></label>
                                    <input type="text" class="form-control" id="email" name="email"
                                           placeholder="Entrez l'adresse mail de l'enseignant" value="${demande.email}">
                                    <small style="color: #900">${demandeForm.erreurs['email']}</small>
                                </div>
                                <div class="form-group">
                                    <label for="numeroTel">Numéro de téléphone <span style="color: #900">*</span></label>
                                    <input type="text" class="form-control" id="numeroTel" name="numeroTel"
                                           placeholder="Entrez le numéro de téléphone de l'enseignant" value="${demande.numeroTel}">
                                    <small style="color: #900">${demandeForm.erreurs['numeroTel']}</small>
                                </div>
                                <button type="submit" class="btn btn-primary">Valider</button>
                            </form>
                        </div>
                    </div>

                </div>
            </div>
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
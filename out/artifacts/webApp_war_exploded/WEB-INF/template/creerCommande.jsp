<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>EHTP - Création d'une commande</title>
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
                            <h6 class="m-0 font-weight-bold text-primary">Passer une commande</h6>
                        </div>
                        <div class="card-body">
                            <form method="post" action="creationCommande">
                                <div class="form-group">
                                    <label for="designation">Désignation <span style="color: #900">*</span></label>
                                    <input type="text" class="form-control" id="designation" name="designation"
                                           placeholder="Entrez la désignation de votre commande" value="${commande.designation}">
                                    <small style="color: #900">${commandeForm.erreurs['designation']}</small>
                                </div>
                                <div class="form-group">
                                    <label for="quantite">Quantité <span style="color: #900">*</span></label>
                                    <input type="text" class="form-control" id="quantite" name="quantite"
                                           placeholder="Entrez la quantité de votre commande" value="${commande.quantite}">
                                    <small style="color: #900">${commandeForm.erreurs['quantite']}</small>
                                </div>
                                <button type="submit" class="btn btn-primary">Valider</button>
                            </form>
                        </div>
                    </div>

                    <br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
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
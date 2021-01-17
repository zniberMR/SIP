<%--
  Created by IntelliJ IDEA.
  User: Zniber
  Date: 15/08/2020
  Time: 16:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>EHTP - Inscription</title>
    <%@include file="inc/css.jsp"%>

</head>

<body class="bg-gradient-login">
<!-- Register Content -->
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
                                    <h1 class="h4 text-gray-900 mb-4">Inscription</h1>
                                </div>
                                <br />
                                <form method="post" action="creationUtilisateur">
                                    <div>
                                        <label>Genre du compte <span style="color: #900">*</span></label>
                                        <br />
                                        <input type="radio" id="choixAdministrateur" name="choixUtilisateur" value="choixAdministrateur" /> Administrateur
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        <input type="radio" id="choixSecretaire" name="choixUtilisateur" value="choixSecretaire" /> Secrétaire
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        <input type="radio" id="choixProfesseur" name="choixUtilisateur" value="choixProfesseur" checked="checked" /> Professeur
                                        <hr />
                                    </div>
                                    <br/>
                                    <div class="form-group">
                                        <label>Carte d'identité nationale <span style="color: #900">*</span></label>
                                        <input type="text" class="form-control" id="cin" name="cin" placeholder="Entrez le numéro cin de l'utilisateur" value="${utilisateur.cin}">
                                        <small style="color: #900">${utilisateurForm.erreurs['cin']}</small>
                                    </div>
                                    <div class="form-group">
                                        <label>Nom <span style="color: #900">*</span></label>
                                        <input type="text" class="form-control" id="nom" name="nom" placeholder="Entrez le nom de l'utilisateur" value="${utilisateur.nom}">
                                        <small style="color: #900">${utilisateurForm.erreurs['nom']}</small>
                                    </div>
                                    <div class="form-group">
                                        <label>Prénom <span style="color: #900">*</span></label>
                                        <input type="text" class="form-control" id="prenom" name="prenom" placeholder="Entrez le prénom de l'utilisateur" value="${utilisateur.prenom}">
                                        <small style="color: #900">${utilisateurForm.erreurs['prenom']}</small>
                                    </div>
                                    <div class="form-group">
                                        <label>Email <span style="color: #900">*</span></label>
                                        <input type="text" class="form-control" id="email" name="email"
                                               placeholder="Entrez l'adresse mail de l'utilisateur" value="${utilisateur.email}">
                                        <small style="color: #900">${utilisateurForm.erreurs['email']}</small>
                                    </div>
                                    <div class="form-group">
                                        <label>Nom d'utilisateur <span style="color: #900">*</span></label>
                                        <input type="text" class="form-control" id="username" name="username" placeholder="Entrez le nom d'utilisation" value="${utilisateur.username}">
                                        <small style="color: #900">${utilisateurForm.erreurs['username']}</small>
                                    </div>
                                    <div class="form-group">
                                        <label>Mot de passe <span style="color: #900">*</span></label>
                                        <input type="password" class="form-control" id="password" name="password" placeholder="Entrez un mot de passe pour l'utilisateur">
                                        <small style="color: #900">${utilisateurForm.erreurs['password']}</small>
                                    </div>
                                    <div class="form-group">
                                        <label>Confirmation du mot de passe <span style="color: #900">*</span></label>
                                        <input type="password" class="form-control" id="passwordConfirmation" name="passwordConfirmation"
                                               placeholder="Répétez le mot de passe">
                                        <small style="color: #900">${utilisateurForm.erreurs['passwordConfirmation']}</small>
                                    </div>
                                    <div class="form-group">
                                        <label>Numéro de téléphone <span style="color: #900">*</span></label>
                                        <input type="text" class="form-control" id="numeroTel" name="numeroTel" placeholder="Entrez le numéro de téléphone de l'utilisateur" value="${utilisateur.numeroTel}">
                                        <small style="color: #900">${utilisateurForm.erreurs['numeroTel']}</small>
                                    </div>
                                    <div class="form-group" id="choixProfesseur-choixSecretaire">
                                        <label>Département</label>
                                        <input type="text" class="form-control" id="departement" name="departement" placeholder="Entrez le département de l'utilisateur" value="${professeur.departement}${secretaire.departement}">
                                    </div>
                                    <div class="form-group" id="choixProfesseur1">
                                        <label>Matière</label>
                                        <input type="text" class="form-control" id="matiere" name="matiere" placeholder="Entrez la matière de l'utilisateur" value="${professeur.matiere}">
                                    </div>
                                    <div class="form-group" id="choixProfesseur2">
                                        <label>Filière</label>
                                        <input type="text" class="form-control" id="filiere" name="filiere" placeholder="Entrez la filière de l'utilisateur" value="${professeur.filiere}">
                                    </div>
                                    <div class="form-group" id="choixAdministrateur1">
                                        <label>Poste</label>
                                        <input type="text" class="form-control" id="poste" name="poste" placeholder="Entrez le poste de l'utilisateur" value="${administrateur.poste}">
                                    </div>
                                    <div class="form-group" id="choixSecretaire1">
                                        <label>Bureau</label>
                                        <input type="text" class="form-control" id="bureau" name="bureau" placeholder="Entrez le bureau de l'utilisateur" value="${secretaire.bureau}">
                                    </div>
                                    <hr>
                                    <div class="form-group">
                                        <button type="submit" class="btn btn-primary btn-block">Inscription</button>
                                    </div>
                                </form>
                                <hr>
                                <div class="text-center">
                                    <a class="font-weight-bold small" href="<%=request.getContextPath()%>/home">Annuler l'inscription</a>
                                </div>
                                <div class="text-center">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Register Content -->
<%@include file="inc/js.jsp"%>
<script type="text/javascript">
    $(document).ready(function(){
        $("div#choixProfesseur-choixSecretaire").show();
        $("div#choixProfesseur1").show();
        $("div#choixProfesseur2").show();
        $("div#choixAdministrateur1").hide();
        $("div#choixSecretaire1").hide();
        jQuery('input[name=choixUtilisateur]:radio').click(function(){
            $("div#choixProfesseur-choixSecretaire").hide();
            $("div#choixProfesseur1").hide();
            $("div#choixProfesseur2").hide();
            $("div#choixAdministrateur1").hide();
            $("div#choixSecretaire1").hide();
            var divId = jQuery(this).val();
            $("div#"+divId+"1").show();
            $("div#"+divId+"2").show();
            $("div#"+divId+"-choixSecretaire").show();
            $("div#choixProfesseur-"+divId).show();
        });
    });
</script>
</body>

</html>

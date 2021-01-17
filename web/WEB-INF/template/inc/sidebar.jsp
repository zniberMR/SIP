<%@ page import="ma.ac.ehtp.sip.entities.Utilisateur" %>
<%@ page import="ma.ac.ehtp.sip.entities.Professeur" %>
<%@ page import="ma.ac.ehtp.sip.entities.Secretaire" %>
<%@ page import="ma.ac.ehtp.sip.entities.Administrateur" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Sidebar -->
<ul class="navbar-nav sidebar sidebar-light accordion" id="accordionSidebar">
    <a class="sidebar-brand d-flex align-items-center justify-content-center" href="<%=request.getContextPath()%>/home">
        <div>
            <img src="<%=request.getContextPath()%>/inc/img/logo/ehtp-logo.png" style="max-width: 70px; max-height: 70px">
        </div>
        <div class="sidebar-brand-text mx-3">EHTP</div>
    </a>
    <hr class="sidebar-divider my-0">
    <li class="nav-item active">
        <a class="nav-link" href="<%=request.getContextPath()%>/home">
            <i class="fas fa-fw fa-tachometer-alt"></i>
            <span>Dashboard</span></a>
    </li>
    <hr class="sidebar-divider">
    <div class="sidebar-heading">
        Features
    </div>
    <%
        Utilisateur utilisateur = (Utilisateur)session.getAttribute("sessionUtilisateur");
        if(utilisateur instanceof Professeur){
    %>
    <li class="nav-item">
        <a class="nav-link collapsed" href="<%=request.getContextPath()%>/creationCommande">
            <i class="fab fa-fw fa-wpforms"></i>
            <span>Passer une commande</span>
        </a>
    </li>
    <%
        }
        if(utilisateur instanceof Secretaire){
    %>
    <li class="nav-item">
        <a class="nav-link collapsed" href="<%=request.getContextPath()%>/creationDemande">
            <i class="fab fa-fw fa-wpforms"></i>
            <span>Faire une demande</span>
        </a>
    </li>
    <%
        }
        if(utilisateur instanceof Administrateur){
    %>
    <li class="nav-item">
        <a class="nav-link collapsed" href="<%=request.getContextPath()%>/creationUtilisateur">
            <i class="fab fa-fw fa-wpforms"></i>
            <span>Créer un utilisateur</span>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link collapsed" href="<%=request.getContextPath()%>/listeUtilisateurs">
            <i class="fas fa-fw fa-table"></i>
            <span>Lister les utilisateurs</span>
        </a>
    </li>
    <li class="nav-item">
        <a class="nav-link collapsed" href="<%=request.getContextPath()%>/listeDemandes">
            <i class="fas fa-fw fa-table"></i>
            <span>Lister les demandes</span>
        </a>
    </li>
    <%
        }
    %>
    <li class="nav-item">
        <a class="nav-link collapsed" href="<%=request.getContextPath()%>/listeCommandes">
            <i class="fas fa-fw fa-table"></i>
            <span>Lister les commandes</span>
        </a>
    </li>
</ul>
<!-- Sidebar -->
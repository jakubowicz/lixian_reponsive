<%@ page import="accesbase.Connexion"%>
<%@ page import="Organisation.Collaborateur"%>

<%@ page language="java" import="java.sql.*" errorPage="gen_error.jsp" %>
<jsp:useBean id="mySessionBean" scope="session" class="General.sessionBean"/>
<html>
<title>Gestion des erreurs </title>    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" type="text/css" href="javascript/jqueryUI/css/tabs.css">

  <link rel="stylesheet" type="text/css" href="javascript/jqueryUI/themes/smoothness/jquery-ui.min.css">
  <link rel="stylesheet" type="text/css" href="javascript/jqueryUI/themes/smoothness/theme.css">

  
  <script type="text/javascript" src="javascript/jquery/jquery.min.js"></script>
  <script src="javascript/jqueryUI/jquery-ui.min.js"></script>
  

</head>
<%
// ---------------------------------------------- Connexion à la base ----------------------------------------------------
Connexion c1 = null ;
Statement st = c1.Connect(this.getServletContext(), mySessionBean.getClient());
// ------------------------------------- Gestion du ST -------------------------------------------//    

Collaborateur theCollaborateur = new Collaborateur(mySessionBean.getLogin());
theCollaborateur.disconnect(c1.nomBase, c1, st);
%>
<body>
 
</body>
<script type="text/javascript">
$(parent.location).attr('href', "accueil.jsp?error=yes");
</script>
</html>
<%
//------------------------------ Deconnexion à la base ----------------------------------------------------
	c1.Unconnect(st);   
//---------------------------------------------------------------------------------------------------------
%>

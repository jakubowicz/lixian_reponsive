<%@ page import="accesbase.Connexion"%>
<%@ page import="Composant.Onglet"%>
<%@ page import="Composant.Licence"%>
<%@ page import="Composant.BoiteOnglet"%>
<%@ page import="Organisation.Collaborateur"%>
<%@ page import="accesbase.ErrorSpecific"%>
<%@ page import="accesbase.transaction"%>
<%@ page import="accesbase.Config"%>
<%@ page import="Projet.Roadmap"%>
<%@ page import="General.Utils"%>
<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*,java.util.*" errorPage="gen_error.jsp" %>
<jsp:useBean id="mySessionBean" scope="session" class="General.sessionBean"/>
<%

// --------------------------------- Recuperation des parametres ----------------------------------------

String username = request.getParameter("username");
String password = request.getParameter("password");  
String codeClient = request.getParameter("codeClient");

Calendar calendar = Calendar.getInstance();
int currentYear = calendar.get(Calendar.YEAR);
int currentMonth = calendar.get(Calendar.MONTH) + 1;
int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
String sessionId= "LIXIAN@" + username + "-"+currentDay+"/"+currentMonth+"/"+currentYear;

mySessionBean.setClient(codeClient);
mySessionBean.setLogin(username);
mySessionBean.setOldLogin(username);  
mySessionBean.setSessionId(sessionId);
mySessionBean.setMachine(request.getRemoteHost());

boolean isauth= false;
boolean isCnx= false;
boolean isPeriodeOk= false;
/// --------------------------- Connexion à la base--------------------------------------------------//
Connexion c1 = null;
Statement st = c1.Connect(this.getServletContext(), mySessionBean.getClient());

if (st == null)
{
%>
    @1@KO@2@
<%   
    return;
}
// -------------------------------------------------------------------------------------------------//

// --------------------------------- Création du Collaborateur ----------------------------------------

Collaborateur theCollaborateur= new Collaborateur(username, password);
isauth=theCollaborateur.isRegistered(c1.nomBase,c1,  st);
isCnx=theCollaborateur.isConnected(c1.nomBase,c1,  st,1,session.getId());
isPeriodeOk=theCollaborateur.getPeriod(c1.nomBase,c1,  st,Config.getValueFromNom(c1.nomBase,c1,  st, "MODE"), Config.getValueFromNom(c1.nomBase,c1,  st, "NB_JOUR_EVALUATION"), Config.getValueFromNom(c1.nomBase,c1,  st, "DEBUT_EVALUATION"));
        
theCollaborateur.setAnonymous(c1.nomBase,c1,  st);
theCollaborateur.getRoles(c1.nomBase,c1,  st);

mySessionBean.setAdmin(theCollaborateur.Admin);
mySessionBean.setManager(theCollaborateur.Manager);
mySessionBean.setMOA(theCollaborateur.MOA);
mySessionBean.setMOE(theCollaborateur.MOE);
mySessionBean.setGOUVERNANCE(theCollaborateur.GOUVERNANCE);
    


//------------------------------ Lecture de la config ----------------------------------------------------
Config theConfig = new Config();
theConfig.bd_UpdatePath(c1.nomBase, c1, st, "config");

// ---------------------------------------- Initialisation des onglets en fonction du profil -----------------------------//
BoiteOnglet theBoiteOnglet = new BoiteOnglet(Config.getValueFromNom(c1.nomBase,c1,  st, "PROFIL"));
theBoiteOnglet.setProfil(c1.nomBase,c1,  st);
// -----------------------------------------------------------------------------------------------------   
// ---------------------------------------------- Verification de la licence -----------------------------//
boolean checkLicence = false;
String myKey = "";
myKey += codeClient;
myKey += ";";
myKey += Config.getValueFromNom(c1.nomBase,c1,  st, "NB_LICENCES");
myKey += ";";
myKey += Config.getValueFromNom(c1.nomBase,c1,  st, "PROFIL").substring(0,1);
myKey += ";";
myKey += Config.getValueFromNom(c1.nomBase,c1,  st, "MODE").substring(0,1);
myKey += ";";
myKey += Config.getValueFromNom(c1.nomBase,c1,  st, "DEBUT_EVALUATION");
myKey += ";";

myKey += Config.getValueFromNom(c1.nomBase,c1,  st, "NB_JOUR_EVALUATION");
myKey += ";";

myKey += java.net.InetAddress.getLocalHost().getHostAddress();

    
    Licence theLicence = new Licence();
    theLicence.genLicence(myKey);
       
    checkLicence = theLicence.checkKeys(myKey, Config.getValueFromNom(c1.nomBase,c1,  st, "KEY"),username, password);
    checkLicence = true;
// -------------------------------------------------------------------------------------------------------//
 // --------------------------------- Gestion des jours Feries ----------------------------------------
String toDay = Utils.getToDay(c1.nomBase,c1, st);
int anneeRef= Utils.theYear;

//String myAnnees = ""+anneeRef+"-"+(anneeRef+1)+"-"+(anneeRef+2);
String myAnnees = ""+anneeRef;
String lastMaj = Config.getConfigExport(c1.nomBase,c1, st, "MAJ_JOURS-FERIES");
if (!myAnnees.equals(lastMaj)) // voir si on a pas deja enregistre les jours feries
{
// 1- Obtention de collaborateur Fictif Jour Ferie
int idCollaborateurFictif = Collaborateur.get_S_IdGenerique(c1.nomBase,c1,  st );

// 2- Obtention de Projet Fictif Jour Ferie
Roadmap theProjetFictif = new Roadmap();
int idProjetFictif=theProjetFictif.getIdProjetJourFerie(c1.nomBase,c1,  st, idCollaborateurFictif );
theProjetFictif.id=idProjetFictif;


// 4- Enregisrement des Jalons
  transaction theTransaction = new transaction (this.getClass().getName());
  theTransaction.begin(c1.nomBase,c1,st);
  
  ErrorSpecific myError=new ErrorSpecific();
  
 for (int annee = anneeRef - 2; annee < (anneeRef + 2); annee++)
 {
    // 3- Creation des Jalons sur 3 annees glissantes 
    theProjetFictif.setJoursFeries(annee);

    myError= theProjetFictif.bd_InsertJoursFeries(c1.nomBase,c1, st, theTransaction.nom, annee, idCollaborateurFictif);
 }  
 
    myError =Config.bd_UpdateValueFromNom(c1.nomBase,c1, st, theTransaction.nom, "MAJ_JOURS-FERIES",myAnnees);
 
  theTransaction.commit(c1.nomBase,c1,st);
}
//------------------------------------------------------------------------------------------------------


String result="";
if (!checkLicence) 
{
    result = "KO:KEY";   
}
else if (isauth && !isPeriodeOk) 
{
    result = "KO:EVAL";   
}
else if (isauth && !isCnx) 
{
    result = "OK"; 

   // Create cookies for first and last names.      
   //Cookie login = new Cookie("username",codeClient + "@" +username);
   //Cookie psw = new Cookie("psw",password);
   //response.addCookie( login );
   //response.addCookie( psw );   
 
   Cookie C_username = new Cookie("username",codeClient + "@" +username);
   Cookie C_psw = new Cookie("psw", password);

   // Set expiry date after 24 Hrs for both the cookies.
   C_username.setMaxAge(60*60*24); 
   C_psw.setMaxAge(60*60*24); 

   // Add both the cookies in the response header.
   response.addCookie( C_username );
   response.addCookie( C_psw );
}
else if (isauth && isCnx) 
{
    result = "KO:CNX";   
}

else 
{
    result = "KO:AUTH";   
}
%>
@1@<%=result%>@2@
<%
//------------------------------ Déconnexion à la base ----------------------------------------------------
if (st != null)
{
    c1.Unconnect(st);
    st.close();
}
//---------------------------------------------------------------------------------------------------------

%>

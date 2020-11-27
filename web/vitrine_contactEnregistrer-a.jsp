<%@ page import="accesbase.Connexion"%>
<%@ page import="Organisation.Contact"%>
<%@ page import="Organisation.ExcelContact"%>
<%@ page import="accesbase.transaction"%>
<%@ page import="accesbase.ErrorSpecific"%>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html; charset=iso-8859-1" language="java" import="java.sql.*" errorPage="gen_error.jsp" %>
<jsp:useBean id="mySessionBean" scope="session" class="General.sessionBean"/>
<html>
<head>
<%

//------------------------------ Connexion à la base ----------------------------------------------------
Connexion c1 = null ;
Statement st = c1.Connect(this.getServletContext(), mySessionBean.getClient());
String base=this.getClass().getName();
/*
if (st == null)
{
        Contact theContact = new Contact(Integer.parseInt(request.getParameter("idContact")));
	theContact.nom = request.getParameter("nom");
        theContact.prenom = request.getParameter("prenom");    
    out.print("@1@"+ theContact.id + "#" + theContact.nomEtat + "@2@");
    return;
}
*/

//-------------------------------------------------------------------------------------------------------

//------------------------------ Recuperation des parametres ----------------------------------------------------

String action = request.getParameter("action"); //Creation ou Modification

//-------------------------------------------------------------------------------------------------------


//------------------------------ Création du type de techonologie ----------------------------------------------------

Contact theContact = null;
//-------------------------------------------------------------------------------------------------------
  ErrorSpecific myError=new ErrorSpecific();
  transaction theTransaction = new transaction ("EnregActeur");

    theContact = new Contact(Integer.parseInt(request.getParameter("idContact")));
	theContact.nom = request.getParameter("nom");
        theContact.prenom = request.getParameter("prenom");
        theContact.entreprise =  request.getParameter("entreprise");
        theContact.telephone = request.getParameter("telephone");
        theContact.adresse = request.getParameter("adresse");
        theContact.mail = request.getParameter("mail");
        theContact.demande = request.getParameter("demande");
        Date theDate = new Date();
        theContact.dateDemande = theDate.getDate()+"/"+(theDate.getMonth() +1)+"/"+(theDate.getYear()+1900);        
        theContact.reponse = request.getParameter("reponse");
        
if ((st == null) || (theContact.id == -1))
{  
    ExcelContact theExcelContact = new ExcelContact();
    theExcelContact.directory = "WEB-INF/";  
    theExcelContact.setBaseDirectory(); 
    //theExcelContact.filename = theExcelContact.Basedirectory +"/"+"config.xls";     
    //theExcelContact.xl_write(theContact);
    theExcelContact.filename = theExcelContact.Basedirectory +"/"+"config.xlsx";     
    theExcelContact.xl_writeXLSX(theContact);    
    out.print("@1@"+ theContact.id + "#" + theContact.nomEtat + "@2@");
    return;
}        
   
    ExcelContact theExcelContact = new ExcelContact();
    theExcelContact.directory = "WEB-INF/";  
    theExcelContact.setBaseDirectory(); 
    //theExcelContact.filename = theExcelContact.Basedirectory +"/"+"config.xls";     
    //theExcelContact.xl_write(theContact);
    theExcelContact.filename = theExcelContact.Basedirectory +"/"+"config.xlsx";     

//if (true) return;
  theTransaction.begin(base,c1,st);
  theContact.dateReponse = theDate.getDate()+"/"+(theDate.getMonth() +1)+"/"+(theDate.getYear()+1900);        

  if (action.equals("standby"))
  {
     theContact.idEtat = 3;
     if (myError.cause == 0) myError=theContact.bd_UpdateReponse(c1.nomBase,c1,st, theTransaction.nom);
  }
  if (action.equals("reponse"))
  {
     theContact.idEtat = 2;
        if (myError.cause == 0) myError=theContact.bd_UpdateReponse(c1.nomBase,c1,st, theTransaction.nom);
  }  
  if (action.equals("retablir"))
  {
     theContact.idEtat = 1;
     if (myError.cause == 0) myError=theContact.bd_UpdateReponse(c1.nomBase,c1,st, theTransaction.nom);
  }   
  else if (action.equals("demande") && (theContact.id == -1))
  {
     theContact.idEtat = 1;
     if (myError.cause == 0) myError=theContact.bd_Insert(c1.nomBase,c1,st, theTransaction.nom);
  }
  else if (action.equals("demande") && (theContact.id != -1))
  {
     theContact.idEtat = 1;
     if (myError.cause == 0) myError=theContact.bd_Update(c1.nomBase,c1,st, theTransaction.nom);
  }  
  else if (action.equals("Supprimer"))
  {
    if (myError.cause == 0) myError=theContact.bd_Delete(c1.nomBase,c1,st, theTransaction.nom);
  }
  
  
  if (myError.cause == 0)
  {
    theExcelContact.xl_writeUpdateXLSX(theContact); 
    theTransaction.commit(base,c1,st);
    theContact.getAllFromBd(c1.nomBase,c1,st);
    String Result = "@1@"+ theContact.id + "#" + theContact.nomEtat + "#" + theContact.dateReponse + "@2@";
    out.print(Result);

    }    
  else
  {
  out.print("@1@KO@2@");
  } 

//------------------------------ Déconnexion à la base ----------------------------------------------------
	c1.Unconnect(st);
        st.close();
//---------------------------------------------------------------------------------------------------------

%>
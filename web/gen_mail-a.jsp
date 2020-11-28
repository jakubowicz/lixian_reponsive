<%@ page import="accesbase.Connexion"%>
<%@ page import="java.sql.*,java.util.*" %>
<%@ page import="General.Utils"%>
<%@ page import="mail.Jmail"%>
<%@ page import="mail.AmazonSESSample"%>

<jsp:useBean id="mySessionBean" scope="session" class="General.sessionBean"/>
<%@ page contentType="text/html; charset=UTF-8" language="java" import="java.sql.*" errorPage="gen_error.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "https://www.w3.org/TR/html4/loose.dtd">

<%
//---------------------------------- Connexion à la base ---------------------------------------//
Connexion c1 = null;
Statement st = c1.Connect(this.getServletContext(), mySessionBean.getClient())  ;
String base=this.getClass().getName();
//------------------------------------------------------------------------------------------------//
String Mode="";
        String serverAddress=request.getParameter("Passerelle");
        String Login=request.getParameter("Login");
        String Password=request.getParameter("Password");
        
if (Login.equals("") && Password.equals(""))
    {
        Mode =  "Bytel";
        
    }
    else
    {
        Mode =  "AWS-Configurable";
    }    
        
        String Port=request.getParameter("Port");
        String Emetteur=request.getParameter("Emetteur");
        String Recepteur=request.getParameter("Recepteur");
        String Objet=request.getParameter("Objet");
        String Corps=request.getParameter("Corps");
        
        if (Corps != null) Corps = Utils.epure3(Corps);
/*
        c1.trace("---- test mail --------","Mode",""+Mode);
        c1.trace("---- test mail --------","Passerelle",""+serverAddress);
        c1.trace("---- test mail --------","Login",""+Login);
        c1.trace("---- test mail --------","Password",""+Password);
        c1.trace("---- test mail --------","Port",""+Port);
        c1.trace("---- test mail --------","Emetteur",""+Emetteur);
        c1.trace("---- test mail --------","Recepteur",""+Recepteur);
        c1.trace("---- test mail --------","Objet",""+Objet);
        c1.trace("---- test mail --------","Corps",""+Corps);        
*/        
        Jmail myMail = null;

 String Result = ""    ;   

            //myMail.send3();
            if (Mode.equals("Bytel"))
            {
                myMail = new Jmail (serverAddress,
                    Emetteur,
                    Recepteur,
                    Connexion.epureMail(Objet),
                    Connexion.epureMail(Corps));
                try{       
                        myMail.send();
                         Result = "OK";
                    }
                catch(Exception e)
                    {
                        System.out.print("send mail failed !!!!!!");
                        e.printStackTrace();
                        Result = e.toString();
                    }     
            }
            else if (Mode.equals("Configurable"))
            {
                myMail = new Jmail(serverAddress,  Login,  Password,  Port,  Emetteur, Recepteur, Objet, Corps);
                try{
            
                        myMail.sendByAWS();
                         Result = "OK";
                    }
                catch(Exception e)
                    {
                        System.out.print("send mail failed !!!!!!");
                        e.printStackTrace();
                        Result = e.toString();
                    }                
               
            }
            else if (Mode.equals("AWS"))
            {
                AmazonSESSample.send();    
            }
            
            else if (Mode.equals("AWS-Configurable"))
            {
                
                try{       
                        AmazonSESSample.sendConfigurable(serverAddress, Emetteur, Recepteur, Port, Login, Password, Objet, Corps); 
                         Result = "OK";
                    }
                catch(Exception e)
                    {
                        System.out.print("send mail failed !!!!!!");
                        e.printStackTrace();
                        Result = e.toString();
                    }                    
            }           

//AmazonSESSample.send();       
%>
@1@<%=Result%>@2@
<%
//---------------------------------- deConnexion à la base ---------------------------------------//
	if (st != null) c1.Unconnect(st);
        if (st != null) st.close();

//----------------------------------------------------------------------------------------//

%>

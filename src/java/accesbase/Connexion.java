package accesbase; 

import Composant.item;
import mail.Jmail;

import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.http.Cookie;
import java.io.*;
import java.awt.*;

import Composant.item;


public class Connexion {

  public static int isStandalone=0;
  public static String serverSMTP = "";
  public static String portSMTP = "";
  public static String loginSMTP = "";
  public static String passwordSMTP = "";
  
  public static String mailAdministrateur = "";
  public static String nomAdministrateur = "";
  public static String prenomAdministrateur = "";
  public static String sendMail = "";

  public static String lastError = "";
  
  public static String client="";
  public static String mail="";
  public static String produit="";
  public static String catalogue="";
  
  public static String nomBase="";
  public static String URL="";
  public static String login="";
  public static String psw="";
  public static String driver="";
  public static String debug="";
  public static String traceOn="";
  public static String traceBd="";
  public static String nomProduit="";
  public static String configuration="";
  
  public static String authentification="";
  
  public static int WebInfUnderJsp=1;
  

  public static TextArea jTextAreaTrace=null;
  
  public static Vector ListeItemsToMigrate= new Vector(10);


  public static Statement Connect(){
    ConfigXml Config = new ConfigXml ("..//..//config_Base.xml");
    return readConfig(Config);


  }

  public static Statement Connect(javax.servlet.ServletContext myContext){
      
    Date currentDate = new Date();
    long l_currentDate = currentDate.getTime();
    
    Date endDate = null;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
    String date1 = "31/12/2018";
    
    try {
    endDate = simpleDateFormat.parse(date1);
    //System.out.println(endDate);
    } catch (Exception e) {e.printStackTrace();}
    
    long l_endDate = endDate.getTime();
    //System.out.println("l_currentDate= "+l_currentDate);
    //System.out.println("l_endDate= "+l_endDate);
    
    if (l_endDate <= l_currentDate)
        return (null);
    else
        return readConfig(myContext);
  }
  
  public static Statement Connect(javax.servlet.ServletContext myContext, String codeClient){
      
    Date currentDate = new Date();
    long l_currentDate = currentDate.getTime();
    
    Date endDate = null;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
    String date1 = "31/12/2018";
    
    try {
    endDate = simpleDateFormat.parse(date1);
    //System.out.println(endDate);
    } catch (Exception e) {e.printStackTrace();}
    
    long l_endDate = endDate.getTime();
    //System.out.println("l_currentDate= "+l_currentDate);
    //System.out.println("l_endDate= "+l_endDate);
    
    if (l_endDate <= l_currentDate)
        return (null);
    else
        return readConfig(myContext, codeClient);
  }  

  public static Statement Connect(String FichierConfig){

    ConfigXml Config = new ConfigXml (FichierConfig);
    return readConfig(Config);


  }

  public static String getMailingList(String nomBase,Connexion myCnx, Statement st )
  {
    ResultSet rs;
    String req = "SELECT mail FROM  Membre WHERE (isAdmin = 1)";
    rs = myCnx.ExecReq(st, nomBase, req);
    String mailingList="";
    try { while (rs.next()) {
            mailingList+=rs.getString(1)+";";
            }
        } catch (SQLException s) { s.getMessage(); }
     mailingList = mailingList.substring(0,mailingList.length() -1);

     return mailingList;

   }

private static Statement readConfig(ConfigXml Config){
    //String Class_forName = "sun.jdbc.odbc.JdbcOdbcDriver";
  driver = Config.getInfoFromConfig("driver");

  Connection conn = null;

  //String data = "jdbc:odbc:sqlserver";
  URL = Config.getInfoFromConfig("URL");

  //String user = "sa";
  login = Config.getInfoFromConfig("login");

  //String motDePasse = "";
  psw = Config.getInfoFromConfig("psw");

  debug =Config.getInfoFromConfig("debug");
  traceOn=Config.getInfoFromConfig("trace");
  traceBd=Config.getInfoFromConfig("traceBd");

  nomBase =Config.getInfoFromConfig("nom");

  serverSMTP = Config.getInfoFromConfig("serverSMTP");
  portSMTP = Config.getInfoFromConfig("portSMTP");
  loginSMTP = Config.getInfoFromConfig("serverSMTP");
  passwordSMTP = Config.getInfoFromConfig("passwordSMTP");
  
  mailAdministrateur = Config.getInfoFromConfig("mailAdministrateur");
  nomAdministrateur = Config.getInfoFromConfig("nomAdministrateur");
  prenomAdministrateur = Config.getInfoFromConfig("prenomAdministrateur");
  sendMail = Config.getInfoFromConfig("sendMail");


  Statement st = null;
  try{
    //chargement du pilote
    Class.forName(driver);
    //initialisation de la connexion � la source de donn�es (sqlserver)
    //conn = DriverManager.getConnection(data,user,motDePasse);
    conn = DriverManager.getConnection(URL,login,psw);
    //cr�ation du statement, objet qui recevra les requ�tes
    st = conn.createStatement();
  }
  catch(SQLException s) {lastError = "SQLException on TestSqlServeur.java  : " + s.getMessage();}
  catch (Exception e) {lastError = "Undef Exception in TestSqlServeur.java " + e.getMessage();}

  return st;

}

private static Statement readConfig(javax.servlet.ServletContext myContext){
  configuration = myContext.getInitParameter("configuration");
  
  client = myContext.getInitParameter("client"+configuration);
  mail = myContext.getInitParameter("mail"+configuration);
  //produit = myContext.getInitParameter("produit"+configuration);
  //catalogue = myContext.getInitParameter("catalogue"+configuration);
  
  driver = myContext.getInitParameter("driver"+configuration);
    //System.out.println("**********************************driver="+driver);
  URL = myContext.getInitParameter("URL"+configuration);
  //System.out.println("**********************************URL="+URL);
  login = myContext.getInitParameter("login"+configuration);
  //System.out.println("**********************************login="+login);
  psw = myContext.getInitParameter("psw"+configuration);
  //System.out.println("**********************************psw="+psw);
  debug = myContext.getInitParameter("debug");
  //System.out.println("**********************************debug="+debug);
  traceOn = myContext.getInitParameter("trace");
  //System.out.println("**********************************trace="+traceOn);
  traceBd = myContext.getInitParameter("traceBd");
  //System.out.println("**********************************traceBd="+traceBd);

  //System.out.println("**********************************context="+contexte);

  nomBase = myContext.getInitParameter("nomBase"+configuration);
  serverSMTP = myContext.getInitParameter("serverSMTP");
  portSMTP = myContext.getInitParameter("portSMTP");
  loginSMTP = myContext.getInitParameter("loginSMTP");
  passwordSMTP = myContext.getInitParameter("passwordSMTP");
  
  //System.out.println("**********************************serverSMTP="+serverSMTP);
  mailAdministrateur = myContext.getInitParameter("mailAdministrateur");
  //System.out.println("**********************************mailAdministrateur="+mailAdministrateur);
  nomAdministrateur = myContext.getInitParameter("nomAdministrateur");
  //System.out.println("**********************************nomAdministrateur="+nomAdministrateur);
  prenomAdministrateur = myContext.getInitParameter("prenomAdministrateur");
  //System.out.println("**********************************prenomAdministrateur="+prenomAdministrateur);
  sendMail = myContext.getInitParameter("sendMail");

  nomProduit = myContext.getInitParameter("nomProduit");
  authentification = myContext.getInitParameter("authentification");
  //System.out.println("**********************************max_lien="+max_lien);
  //System.out.println("**********************************nomBase="+nomBase+"/"+login+"/"+psw+"/"+driver+"/"+URL+"/"+Fonctionnement);

  Connection conn = null;

  Statement st = null;
  try{
    //chargement du pilote
    Class.forName(driver);
    //initialisation de la connexion � la source de donn�es (sqlserver)
    //conn = DriverManager.getConnection(data,user,motDePasse);
    conn = DriverManager.getConnection(URL,login,psw);
    //cr�ation du statement, objet qui recevra les requ�tes
    st = conn.createStatement();
  }
  catch(SQLException s) {lastError = "SQLException on TestSqlServeur.java  : " + s.getMessage();}
  catch (Exception e) {lastError = "Undef Exception in TestSqlServeur.java " + e.getMessage();}

  return st;

}

public static void readConfigWeb(javax.servlet.ServletContext myContext, String codeClient){
  configuration = myContext.getInitParameter("configuration");
   
  mail = myContext.getInitParameter("mail"+configuration);
  produit = myContext.getInitParameter("produit"+configuration);
  catalogue = myContext.getInitParameter("catalogue"+configuration);
  
  driver = myContext.getInitParameter("driver"+configuration);
    //System.out.println("**********************************driver="+driver);
  URL = myContext.getInitParameter("URL"+configuration);
  //System.out.println("**********************************URL="+URL);
  login = myContext.getInitParameter("login"+configuration);
  //System.out.println("**********************************login="+login);
  psw = myContext.getInitParameter("psw"+configuration);
  //System.out.println("**********************************psw="+psw);
  debug = myContext.getInitParameter("debug");
  //System.out.println("**********************************debug="+debug);
  traceOn = myContext.getInitParameter("trace");
  //System.out.println("**********************************trace="+traceOn);
  traceBd = myContext.getInitParameter("traceBd");
  //System.out.println("**********************************traceBd="+traceBd);

 
  serverSMTP = myContext.getInitParameter("serverSMTP");
  portSMTP = myContext.getInitParameter("portSMTP");
  loginSMTP = myContext.getInitParameter("loginSMTP");
  passwordSMTP = myContext.getInitParameter("passwordSMTP");
  //System.out.println("**********************************serverSMTP="+serverSMTP);
  mailAdministrateur = myContext.getInitParameter("mailAdministrateur");
  //System.out.println("**********************************mailAdministrateur="+mailAdministrateur);
  nomAdministrateur = myContext.getInitParameter("nomAdministrateur");
  //System.out.println("**********************************nomAdministrateur="+nomAdministrateur);
  prenomAdministrateur = myContext.getInitParameter("prenomAdministrateur");
  //System.out.println("**********************************prenomAdministrateur="+prenomAdministrateur);
  sendMail = myContext.getInitParameter("sendMail");
  //System.out.println("**********************************sendMail="+sendMail);

  //System.out.println("**********************************max_lien="+max_lien);

  nomProduit = myContext.getInitParameter("nomProduit");
  authentification = myContext.getInitParameter("authentification");
  
  if (codeClient == null)
  {
      codeClient = myContext.getInitParameter("client"+configuration);
  }
      
  
  if (authentification.equals("yes"))
  {
      client = codeClient;
      nomBase = "Base"+codeClient;
  }
  else
  {
      client = myContext.getInitParameter("client"+configuration);
      nomBase = myContext.getInitParameter("nomBase"+configuration);
  }
  
  WebInfUnderJsp = Integer.parseInt( myContext.getInitParameter("WebInfUnderJsp"));
  //isStandalone = Integer.parseInt( myContext.getInitParameter("isStandalone"));

 
  //System.out.println("**********************************max_lien="+max_lien);
  //System.out.println("**********************************nomBase="+nomBase+"/"+login+"/"+psw+"/"+driver+"/"+URL+"/"+Fonctionnement);
  
    
  // DEBUT PATCH
  //if (nomBase.equals("BaseEsg")) nomBase = "BaseCarto";
  // FIN PATCH
  URL+=nomBase;


}

private static Statement readConfig(javax.servlet.ServletContext myContext, String codeClient){
    
  readConfigWeb( myContext,  codeClient);
  if (isStandalone == 1) return null;

  Connection conn = null;
//Connexion.trace("@0123456------","URL",""+URL);
//Connexion.trace("@0123456------","login",""+login);
//Connexion.trace("@0123456------","psw",""+psw);
//Connexion.trace("@0123456------","driver",""+driver);

  Statement st = null;
  
  
  for (int nb = 0; nb < 1; nb ++)
  {
      if (st == null)
      {
      if (nb > 0) 
      {
          try{
          Thread.sleep (2000);
          } catch (Exception e){}
          //Connexion.trace("@0123456------TRY AGAIN","nb",""+nb);
      }
  try{
    //Connexion.trace("@01234567------","driver",""+driver);
    //chargement du pilote
    Class.forName(driver);
    //initialisation de la connexion � la source de donn�es (sqlserver)
    //conn = DriverManager.getConnection(data,user,motDePasse);
    //Connexion.trace("@01234568------","driver",""+driver);
    conn = DriverManager.getConnection(URL,login,psw);
    //Connexion.trace("@01234569------","conn",""+conn);
    //cr�ation du statement, objet qui recevra les requ�tes
    st = conn.createStatement();
    //Connexion.trace("@012345610------","st",""+st);
  }
  
  catch(SQLException s) {lastError = "SQLException on TestSqlServeur.java  : " + s.getMessage();}
  catch (Exception e) {lastError = "Undef Exception in TestSqlServeur.java " + e.getMessage();}
  
      }
  }

  if (codeClient == null)
  {
      //Connexion.trace("@0123456------","codeClient",""+myContext.getInitParameter("client"+configuration));
  }  
  else
      //Connexion.trace("@0123456------","codeClient",""+codeClient);
  
  //Connexion.trace("@0123456------","nomBase",""+nomBase);
  sendMail = Config.getValueFromNom(nomBase,null,  st, "ENVOI_MAIL");
  debug = Config.getValueFromNom(nomBase,null,  st, "TRACES_SQL");
  traceOn = Config.getValueFromNom(nomBase,null,  st, "TRACES_APPLI");

  return st;

}


 public static Statement Connect( String Base, String URL, String Driver, String login, String psw){
   String Class_forName = Driver;
   Connection conn = null;
   String data = URL;
   String user =login;
   String motDePasse = psw;
   Statement st = null;
   try{
     //chargement du pilote
     Class.forName(Class_forName);
     //initialisation de la connexion � la source de donn�es (sqlserver)
     conn = DriverManager.getConnection(data,user,motDePasse);
     //cr�ation du statement, objet qui recevra les requ�tes
     st = conn.createStatement();
   }
   catch(SQLException s) {lastError = "SQLException on TestSqlServeur.java  : " + s.getMessage();}
   catch (Exception e) {lastError = "Undef Exception in TestSqlServeur.java " + e.getMessage();}
   return st;

 }
// ASTRAEA-v1, ANFR-v1, SITCOM-v1, GASPPER-v2, PCU-OAM-v1
 public static void newCnx(Statement st,String nom, String sessionId, String jspName, String Carto, String jreVersion){

   String req="";
   req = "delete from StatConnexion where sessionId = '"+sessionId+"'";
   ResultSet rs = ExecReq(st,"connect",req);

   req = "INSERT StatConnexion (UserName,sessionId, pageName, Carto, jreVersion) VALUES('"+nom.toUpperCase()+"', '"+sessionId+"',  '"+jspName+"', '"+Carto+"', '"+jreVersion+"')";
   rs = ExecReq(st,"connect",req);
   //trace("*************>>newCnx","req",req);

   req = "UPDATE StatConnexion SET jreVersion ='"+jreVersion+"' WHERE UserName='"+nom+"' AND Carto='"+Carto;
    rs = ExecReq(st, "connect", req);
 }

 public static void newCnx(Statement st,String nom, String sessionId, String jspName, String Carto){
   String req="";
   req = "delete from StatConnexion where sessionId = '"+sessionId+"'";
   ResultSet rs = ExecReq(st,"connect",req);

   req = "INSERT StatConnexion (UserName,sessionId, pageName, Carto) VALUES('"+nom+"', '"+sessionId+"',  '"+jspName+"', '"+Carto+"')";
   rs = ExecReq(st,"connect",req);
   //trace("*************>>newCnx","req",req);


 }

 public static String findCookieValue(String base,String paramName, Cookie[] ListCookie){
 String theCookie = "";
   for (int nbCokkie=0; nbCokkie <ListCookie.length;nbCokkie++)
   {
     //trace(base,"listCookie["+nbCokkie+"].getName()",""+ListCookie[nbCokkie].getName());
     //trace(base,"listCookie["+nbCokkie+"].getValue()",""+ListCookie[nbCokkie].getValue());
     if (ListCookie[nbCokkie].getName().equals(paramName))
       {
         theCookie = ListCookie[nbCokkie].getValue();
       }
   }


  return theCookie;
 }
 
  public static String findCookieValue2(String base,String paramName, Cookie[] ListCookie){

   for (int nbCokkie=0; nbCokkie <ListCookie.length;nbCokkie++)
   {
     //trace(base,"listCookie["+nbCokkie+"].getName()",""+ListCookie[nbCokkie].getName());
     //trace(base,"listCookie["+nbCokkie+"].getValue()",""+ListCookie[nbCokkie].getValue());
     if (ListCookie[nbCokkie].getName().equals(paramName))
       {
         return ListCookie[nbCokkie].getValue();
       }
   }


  return "";
 }

public static Cookie setCookie(String base, String cookieName, String cookieValue){
 Cookie c = new Cookie(cookieName,cookieValue);
 c.setMaxAge(600*3600);
 trace(base,"Cookies plac� sur",""+cookieName+" :: valeur="+cookieValue);
 return c;
}


 public static void majCnx(Statement st, String sessionId){
   String req = "UPDATE StatConnexion SET majCnx = GETDATE() WHERE sessionId='"+sessionId+"'";
   ResultSet rs = ExecReq(st, "connect", req);
   //trace("*************>>connect","req",req);
 }

 public static void majCnx(String base,Statement st, String sessionId){
   String req = "UPDATE StatConnexion SET majCnx = GETDATE(), pageName='"+base+"' WHERE sessionId='"+sessionId+"'";
   ResultSet rs = ExecReq(st, "connect", req);
   //trace("*************>>majCnx","req",req);
 }

 public static String majCnx(String base,Statement st, String sessionId, String Machine, String item, String UserName){
  //trace ("MajCnx","base",base);
  Machine=Machine.toUpperCase();
  int Index = Machine.indexOf(".");
  ResultSet rs=null;

  //if (Index > -1)  Machine = Machine.substring(0,Index);

  String req = "UPDATE StatConnexion SET UserName='"+UserName.toUpperCase()+"',majCnx = GETDATE(), pageName='"+base+"', Machine='"+Machine+"', item='"+item+"' WHERE sessionId='"+sessionId+"' AND Carto='Lixian'";
  rs = ExecReq(st, "majCnx", req);

  return Machine;
}
 
 public static String majCnx(String base,Statement st, String sessionId, String Machine, String item, String UserName, String Carto){
  //trace ("MajCnx","base",base);
  Machine=Machine.toUpperCase();
  int Index = Machine.indexOf(".");
  ResultSet rs=null;

  //if (Index > -1)  Machine = Machine.substring(0,Index);

  String req = "UPDATE StatConnexion SET UserName='"+UserName.toUpperCase()+"',majCnx = GETDATE(), pageName='"+base+"', Machine='"+Machine+"', item='"+item+"', Carto='"+Carto+"' WHERE sessionId='"+sessionId+"'";
  rs = ExecReq(st, "majCnx", req);

  return Machine;
} 

public static String majCnx(String base,Statement st, String sessionId, String Machine, String item){
 trace ("MajCnx","base",base);
 Machine=Machine.toUpperCase();
 int Index = Machine.indexOf(".");
 ResultSet rs=null;

 //if (Index > -1)  Machine = Machine.substring(0,Index);

 String req = "UPDATE StatConnexion SET majCnx = GETDATE(), pageName='"+base+"', Machine='"+Machine+"', item='"+item+"' WHERE sessionId='"+sessionId+"'";
 rs = ExecReq(st, "majCnx", req);

 return Machine;
}



  public static ResultSet ExecReq(Statement st, String jsp, String req) {

    ResultSet rec = null;
    //base = "TestCarto";
    //base = "BaseCarto";
    String base_utilisee = "use "+ nomBase;
    if (debug.equals("1"))
    {
      if (jTextAreaTrace == null)
        {
          System.out.println(getDate() + "::" + "base_utilisee=..." +
                             base_utilisee +
                             "-- jsp=" + jsp + "---Req=" + req);
        }
        else
        {
            trace(getDate() + "::" + "base_utilisee=..." +
                             base_utilisee,"---Req",req);
        }
    }
    try{
        st.execute(base_utilisee);
        rec = st.executeQuery(req);
    }
    catch(SQLException s) {lastError = "SQLException on TestSqlServeur.java  : " + s.getMessage();}
    catch (Exception e) {lastError = "Undef Exception in TestSqlServeur.java " + e.getMessage();}
    return rec;
  }

  public static ResultSet ExecReq(Statement st, String jsp, String req, boolean trace) {

  ResultSet rec = null;
  //base = "TestCarto";
  //base = "BaseCarto";
  String base_utilisee = "use "+ nomBase;
  if (trace)
  {
    if (jTextAreaTrace == null)
      {
        System.out.println(getDate() + "::" + "base_utilisee=..." +
                           base_utilisee +
                           "-- jsp=" + jsp + "---Req=" + req);
      }
      else
      {
          trace(getDate() + "::" + "base_utilisee=..." +
                           base_utilisee,"---Req",req);
      }
  }
  try{
    st.execute(base_utilisee);
    rec = st.executeQuery(req);

  }
  catch(SQLException s) {lastError = "SQLException on TestSqlServeur.java  : " + s.getMessage();}
  catch (Exception e) {lastError = "Undef Exception in TestSqlServeur.java " + e.getMessage();}
  return rec;
}

  public static ResultSet ExecReq(Statement st, String jsp, String req, boolean trace, String Thetransaction) {

  ResultSet rec = null;
  String base_utilisee = "use "+ nomBase;
  if (trace)
  {
    if (jTextAreaTrace == null)
      {
        System.out.println(getDate() + "::" + "base_utilisee=..." +
                           base_utilisee +
                           "-- jsp=" + jsp + "---Req=" + req);
      }
      else
      {
          trace(getDate() + "::" + "base_utilisee=..." +
                           base_utilisee,"---Req",req);
      }
  }

  try {
        st.execute(base_utilisee);
        rec = st.executeQuery(req);

     }

catch (Exception s)
                         {

                         req = "ROLLBACK TRANSACTION "+Thetransaction;
                         ExecReq(st, nomBase, req,trace);
                         //Unconnect(st);
                         rec=null;

                     }
return rec;

}

public static ErrorSpecific ExecUpdateTransaction(Statement st, String base, String req, boolean trace, String Thetransaction, String myClass, String myMethod, String myInstance) {
  ResultSet rs = null;
  int check=0;
  String base_utilisee = "use "+ nomBase;
  String returnCode="";

  ErrorSpecific myError= new ErrorSpecific();

  if (trace) trace(getDate()+ ":: ExecUpdateTransaction", "req", req);
   try {
         st.executeUpdate(req);
         myError.cause=0;
       }
       catch (Exception s)
       {
         myError.cause=-1;
         myError.Class=myClass;
         myError.Instance=myInstance;
         myError.Method=myMethod;

          myError.req =req.replaceAll("\r","").replaceAll("\n","");

         req = "ROLLBACK TRANSACTION "+Thetransaction;
         ExecReq(st, base, req,trace);
         //Unconnect(st);

         trace(nomBase, "*** ERREUR *** req", req);

         }
      return myError;
}

public static int ExecUpdate(Statement st, String base, String req, boolean trace, String Thetransaction) {
ResultSet rs = null;
  int check=0;
  String base_utilisee = "use "+ nomBase;
  if (trace)
  {
    if (jTextAreaTrace == null)
      {
        System.out.println(getDate() + "::" + "base_utilisee=..." +
                           base_utilisee +
                           "-- jsp=" + base + "---Req=" + req);
      }
      else
      {
          trace(getDate() + "::" + "base_utilisee=..." +
                           base_utilisee,"---Req",req);
      }
  }

  try {  check=st.executeUpdate(req);
      }
  catch (Exception s)
                           {

                           req = "ROLLBACK TRANSACTION "+Thetransaction;
                           ExecReq(st, base, req,trace);
                           //Unconnect(st);
                           check=-1;

                       }
return check;
}

public static void ExecUpdate(Statement st, String base, String req, boolean trace) {
ResultSet rs = null;
int check=0;
String base_utilisee = "use "+ nomBase;
if (trace)
{
if (jTextAreaTrace == null)
{
System.out.println(getDate() + "::" + "base_utilisee=..." +
base_utilisee +
"-- jsp=" + base + "---Req=" + req);
}
else
{
trace(getDate() + "::" + "base_utilisee=..." +
base_utilisee,"---Req",req);
}
}

try {  check=st.executeUpdate(req);
}
catch (Exception s)
{
s.printStackTrace();

}

}

  public static void getListeItemsToMigrate(String nomBase,Connexion myCnx, Statement st){

    String req = "";
    ResultSet rs=null;
    ListeItemsToMigrate.removeAllElements();
    req = "SELECT     Ordre, Nom, Etat FROM   Migration WHERE (Migration = 1) ORDER BY Ordre, Nom";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
       while (rs.next()) {
      item theitem = new item(-1);
      theitem.ordre = rs.getInt("Ordre");
      theitem.nom = rs.getString("Nom");
      theitem.idEtat = rs.getInt("Etat");

      ListeItemsToMigrate.addElement(theitem);
       }
      } catch (SQLException s) {s.getMessage(); }  

 }

  public static String getDate (){
    SimpleDateFormat formatter
           = new SimpleDateFormat ("hh:mm:ss-SS");
Date currentTime_1 = new Date();
String dateString = formatter.format(currentTime_1);
       return dateString;

  }

  public static void Unconnect(Statement st) {
    try {st.close();}
    catch (SQLException s) {lastError = "SQLException on TestSqlServeur.java  : " + s.getMessage();}
    trace(Connexion.nomBase,"***********","connexion ferm�e");
  }


  public static String truncate (String theString, int lenTruncate){
    int seuil = 50;
    int LtheString = theString.length();
    if (LtheString < seuil) return theString;
    if ((LtheString - lenTruncate) < seuil) lenTruncate = LtheString - seuil;
    String rawTrunc = theString.substring(lenTruncate);
    int Index = rawTrunc.indexOf(",");

    return "....." + rawTrunc.substring(Index);
    //return "toto";
  }

public static String getVersion(String Version){
    if ((""+Version).equals("") || Version == null)
    {
      return "";
    }
    else
    {

      return "-" + Version;
    }

  }

  public static String setVersion(String Version){
      if ((""+Version).equals("") || Version == null)
      {
        return "v0";
      }
      else if (Version.substring(0,1).equals("V"))
      {
        return "v" + Version.substring(1);
      }
      else if (Version.substring(0,1).equals("v"))
      {
        return Version;
      }

      else
      {
        return "v"+Version;
      }

    }

    public static String setNextVersion(String theVersion){
      String strVersion = theVersion.substring(theVersion.length() -1);
      int theNewVersion = Integer.parseInt(strVersion) + 1;
      strVersion = theVersion.substring(0, theVersion.length() -1) +theNewVersion ;

      return strVersion;

      }


  public static void trace (String source, String NomStr, String valStr){
        if (traceOn.equals("0")) return;

    System.out.println(getDate() + "::" + "source=" + source + "-- " + NomStr + "=" + valStr);

  }


  public static void traceBd (String page, String typeTrace,  String trace, boolean visible){
    if (traceBd.equals("no")) return;
    Statement st = Connect();
    String base_utilisee = "use "+ nomBase;
    String req = "INSERT INTO Traces  ([timeStamp], base, page, typeTrace, trace) VALUES (";
    req+="'"+getDate()+"',";
    req+="'"+nomBase+"', ";
     req+="'"+page+"',";
     req+="'"+typeTrace+"',";
      req+="'"+trace+"'";
      req+=")";
    try{
        st.execute(base_utilisee);
        st.executeQuery(req);

    }
    catch(SQLException s) {lastError = "SQLException on TestSqlServeur.java  : " + s.getMessage();}
    catch (Exception e) {lastError = "Undef Exception in TestSqlServeur.java " + e.getMessage();}



    }



public static String replaceAll (String theStr, String oldStr, String newStr){
if (theStr == null) return "";
int index = theStr.indexOf(oldStr);
String Suffixe="";
String Prefixe="";
String theNewStr="";

if (index == -1) return theStr;

      while (index !=-1){
        if (index > 0)
        {
          Prefixe+=theStr.substring(0, index)+newStr;
          Suffixe=theStr.substring(index + 1);
          theStr = Suffixe;
        }
        else
        {
          theStr = newStr +theStr.substring( 1);
        }
        index = Suffixe.indexOf(oldStr);

      }

theNewStr=Prefixe+Suffixe;
return theNewStr;
}

public static String epure (String theStr){

  String newStr=theStr;
  //newStr=replaceAll(newStr,"d","x");
  newStr=replaceAll(newStr,"\r\n","");
  newStr=replaceAll(newStr,"\"","\'\'");
  newStr=replaceAll(newStr,"'","&acute");
  //newStr=newStr.replaceAll("\r","");
  //newStr=newStr.replaceAll("\"","\'\'");
  //replaceAll("'","''");
  return newStr;
}

public static String epureMail (String theStr){

  String newStr=theStr;
  newStr=replaceAll(newStr,"�","e");
  newStr=replaceAll(newStr,"�","e");
  newStr=replaceAll(newStr,"�","e");
  newStr=replaceAll(newStr,"�","e");
  newStr=replaceAll(newStr,"�","a");
  newStr=replaceAll(newStr,"�","u");

  return newStr;
}



public static void sendmail(String emetteur,String Destinataire, String objet, String corps) throws Exception{
  //sendMail
  if (!sendMail.equals("1")) return;

Jmail myMail=null;

myMail = new Jmail (serverSMTP,
                    emetteur,
                    Destinataire,
                    epureMail(objet),
                    epureMail(corps));
//myMail.dump();

  myMail.send();


}

public static String getLogin(String base,Statement st,String Machine){

 ResultSet rs = null;
 String[] theLigne = new String[30];

if (Machine == null) return "MachineNull";
if (Machine.equals("")) return "MachineVide";

 //String req1 = "select * from St";
 String req1 = "exec searchLogin "+"["+Machine+"]";

 int nbLigne=0;

 rs = ExecReq(st, base, req1);
try {while(rs.next()) {
    theLigne[nbLigne] = rs.getString("output");
    trace(base,"theLigne["+nbLigne+"]",theLigne[nbLigne]);
    nbLigne++;
    }
   }
    catch (SQLException s) {s.getMessage(); }

    int Index=0;
    String theLogin="non connu";
    for (int i=0; i < nbLigne; i++)
    {
      if (theLigne[i] == null) continue;
      Index = theLigne[i].indexOf("<03>");
      if (Index > -1)
      {
        Index = theLigne[i].indexOf(Machine);
        if (Index > -1)
          {
           continue;
          }
        theLogin = theLigne[i].substring(0,8);
        break;
      }
    }
   return theLogin;
  }

  public static String getLogin2(String base,Statement st,String Machine){

   ResultSet rs = null;
   String[] theLigne = new String[30];

  if (Machine == null) return "MachineNull";
  if (Machine.equals("")) return "MachineVide";

   //String req1 = "select * from St";
   String req1 = "exec searchLogin "+"["+Machine+"]";

   int nbLigne=0;

   rs = ExecReq(st, base, req1);
  try {while(rs.next()) {
      theLigne[nbLigne] = rs.getString("output");
      trace(base,"theLigne["+nbLigne+"]",theLigne[nbLigne]);
      nbLigne++;
      }
     }
      catch (SQLException s) {s.getMessage(); }

      int Index=0;
      String theLogin="non connu";
      for (int i=0; i < nbLigne; i++)
      {
        if (theLigne[i] == null) continue;
        Index = theLigne[i].indexOf("MAC ");
        if (Index > -1)
        {
          theLogin = theLigne[i-2].substring(0,8);
          break;
        }
      }
     return theLogin;
    }

 

    public static void main(String[] args) {
      Connexion myCnx = null;
      Statement st= null;
      ResultSet rs = null;

      String req = "";
      String base = myCnx.getDate();
      st = myCnx.Connect();
      
       Unconnect(st);
      }


}

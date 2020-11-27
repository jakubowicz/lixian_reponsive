/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesbase;

import static accesbase.Connexion.getDate;


import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.http.Cookie;
import java.io.*;
import java.awt.*;

import Organisation.*;
import ST.*;
import OM.*;
import Composant.*;
import General.Utils;
import PO.*;
import Documentation.*;
import Graphe.*;
import Projet.*;
import Test.*;
import Processus.*;
import Anomalies.*;
import Annonces.*;
import Fichier.FichierTexte;

import java.util.Enumeration;
import java.util.Hashtable;
 
/**
 *
 * @author Joël
 */
public class db extends item{
  public Vector listeCnx = new Vector(10);
  
  public int id=-1;
  
  //
  public  String URL="";
  public  String login="";
  public  String psw="";
  public  String driver="";   
  public Statement st = null;
  
  public ResultSet rs = null;
  public String debug = "no";
  
  public String configuration="";
  public String client="";
  public String mail="";
  public String traceOn="";
  public String traceBd="";
  
  public  String serverSMTP = "";
  public  String portSMTP = "";
  public  String loginSMTP = "";
  public  String passwordSMTP = "";  
  
  public  String mailAdministrateur = "";
  public  String nomAdministrateur = "";
  public  String prenomAdministrateur = "";
  public  String sendMail = ""; 
  
  public String nomProduit="";
  public String authentification="";
  
  //
  public String backup = "";
  public int overwrite=1;
  public String langue = "French";
  
  public String transaction = "";
  
  private String req="";
  
  public Vector listeTables = new Vector(10);
  
  
  
  Hashtable ListeMembres = new Hashtable();
  Hashtable ListeServices = new Hashtable();
  Hashtable ListeSi = new Hashtable();
  Hashtable ListeTypeSi = new Hashtable();
  Hashtable ListeMacroSt = new Hashtable();
  Hashtable ListeFrequences = new Hashtable();
  Hashtable ListeMaturites = new Hashtable();
  Hashtable ListeSt = new Hashtable();
  Hashtable ListeDirections = new Hashtable();
  Hashtable ListeEquipes = new Hashtable();
  Hashtable ListeFamilles = new Hashtable();
  Hashtable ListePackages = new Hashtable();
  Hashtable ListeConstructeurs = new Hashtable();
  Hashtable ListeLogiciels = new Hashtable();
  Hashtable ListeMiddleware = new Hashtable();
  Hashtable ListeTypeAppli = new Hashtable();
  Hashtable ListeVersionSt = new Hashtable();
  Hashtable ListeTypeModule = new Hashtable();
  Hashtable ListeImplementations = new Hashtable();
  Hashtable ListeOm = new Hashtable();
  Hashtable ListeInterfaces = new Hashtable();
  Hashtable ListeBriefs = new Hashtable();
  Hashtable ListetypeProfilRoadmap = new Hashtable();
  Hashtable ListeRoadmap = new Hashtable();
  Hashtable ListeDevis = new Hashtable();
  Hashtable ListetypeJalon = new Hashtable();  
  Hashtable ListeCategories = new Hashtable();  
  Hashtable ListeTests= new Hashtable(); 
  Hashtable ListeCampagnes= new Hashtable(); 
  Hashtable ListeProcessus= new Hashtable();  
  Hashtable ListePhases= new Hashtable();  
  Hashtable ListeTaches= new Hashtable(); 
  Hashtable ListeActionSuivi= new Hashtable(); 
  
  public boolean reprise = false;
  
  

public db (String nom,String driver,  String URL, String login, String psw, String debug ){
    this.nom=nom;
    this.driver=driver;
    this.URL=URL;
    this.login=login;
    this.psw=psw;
    this.debug=debug;
}

public db(int id){
    this.id=id;
}

public db(javax.servlet.ServletContext myContext, String codeClient){
  this.configuration = myContext.getInitParameter("configuration"); 
  this.client = myContext.getInitParameter("client"+configuration);
  this.mail = myContext.getInitParameter("mail"+configuration);
  this.driver = myContext.getInitParameter("driver"+configuration);
  this.URL = myContext.getInitParameter("URL"+configuration);
  this.login = myContext.getInitParameter("login"+configuration);
  this.psw = myContext.getInitParameter("psw"+configuration);
  this.debug = myContext.getInitParameter("debug");
  this.traceOn = myContext.getInitParameter("trace");
  this.traceBd = myContext.getInitParameter("traceBd");
  this.nom = myContext.getInitParameter("nomBase"+configuration);
  this.serverSMTP = myContext.getInitParameter("serverSMTP");
  this.portSMTP = myContext.getInitParameter("portSMTP");
  this.loginSMTP = myContext.getInitParameter("loginSMTP");
  this.passwordSMTP = myContext.getInitParameter("passwordSMTP");
  this.mailAdministrateur = myContext.getInitParameter("mailAdministrateur");
  this.nomAdministrateur = myContext.getInitParameter("nomAdministrateur");
  this.prenomAdministrateur = myContext.getInitParameter("prenomAdministrateur");
  this.sendMail = myContext.getInitParameter("sendMail");
  this.nomProduit = myContext.getInitParameter("nomProduit");
  this.authentification = myContext.getInitParameter("authentification");
}

public String translate(String str, String langue){
    String new_str = str;

    if (langue.equals("French"))
    {
        new_str = new_str.replaceAll("\'Jan ","\'janv ");
        new_str = new_str.replaceAll("\'Feb ","\'févr ");
        new_str = new_str.replaceAll("\'Mar ","\'mars ");
        new_str = new_str.replaceAll("\'Apr ","\'avr ");
        new_str = new_str.replaceAll("\'May ","\'mai ");
        new_str = new_str.replaceAll("\'Jun ","\'juin ");
        new_str = new_str.replaceAll("\'Jul ","\'juil ");
        new_str = new_str.replaceAll("\'Aug ","\'août ");
        new_str = new_str.replaceAll("\'Sep ","\'sept ");
        new_str = new_str.replaceAll("\'Oct ","\'oct ");
        new_str = new_str.replaceAll("\'Nov ","\'nov ");
        new_str = new_str.replaceAll("\'Dec ","\'déc ");
    }
    else if (langue.equals("English"))
    {
        new_str = new_str.replaceAll("\'janv ","\'Jan ");
        new_str = new_str.replaceAll("\'févr ","\'Feb ");
        new_str = new_str.replaceAll("\'mars ","\'Mar ");
        new_str = new_str.replaceAll("\'avr ","\'Apr ");
        new_str = new_str.replaceAll("\'mai ","\'May ");
        new_str = new_str.replaceAll("\'juin ","\'Jun ");
        new_str = new_str.replaceAll("\'juil ","\'Jul ");
        new_str = new_str.replaceAll("\'août ","\'Aug ");
        new_str = new_str.replaceAll("\'sept ","\'Sep ");
        new_str = new_str.replaceAll("\'oct ","\'Oct ");
        new_str = new_str.replaceAll("\'nov ","\'Nov ");
        new_str = new_str.replaceAll("\'déc ","\'Dec ");
    }    
    
    return new_str;
}

  public void importTablesFromFile2(String nomBase,Connexion myCnx, Statement st, FichierTexte fichiertexte, db theBase , String langue){
    // Lire la sequence des tables dans CreateBaseVide.sql {nomTable, ordre}
      // ecrire dans ListeTable
      // ecrire la liste des tables dans Migration  
    int num=1;
    int pending = 0;
    String cmd = "";
    int pos = -1;
      this.listeTables.removeAllElements();
    try {

    BufferedReader input = new BufferedReader(new FileReader(fichiertexte.Basedirectory + "/" + fichiertexte.filename));
    try {
    String ligne = null;
    
    while (( ligne = input.readLine()) != null){
        ligne = translate(ligne, langue);
        pos = ligne.indexOf("use Base");
        if (pos > -1)
        {
            ligne = "use " + theBase.nom;           
        }
        // si la ligne ne contient pas INSERT et pending = 0
        //pos = ligne.indexOf("use BaseVide")+ ligne.indexOf("--- Fichier:") + ligne.indexOf("DEBUT dump:") + ligne.indexOf("DELETE FROM ")+ ligne.indexOf("DELETE FROM ")+ ligne.indexOf("SET IDENTITY_INSERT");
        if (
                (ligne.indexOf("use "+theBase.nom) > -1)
                ||
                (ligne.indexOf("--- Fichier:") > -1)
                ||
                (ligne.indexOf("DEBUT dump:") > -1)
                ||
                (ligne.indexOf("DELETE FROM ") > -1)
                ||
                (ligne.indexOf("SET IDENTITY_INSERT") > -1)                                
           )
        {
        //      -- executer la commande
            if (!cmd.equals(""))
            {
                this.ExecUpdate( cmd);
                cmd = "";
                pending = 0;
            }
            this.ExecUpdate( ligne);
        }
        else
        {
            
          pos = ligne.indexOf("INSERT INTO [dbo].");   
          if (pos != -1)
          {
            if (!cmd.equals(""))
            {
                this.ExecUpdate( cmd);
                
            }  
                
            cmd = ligne;
          }
            else
            {
                cmd += "\n";
                cmd += ligne;
            }            

        }
        
    }
    if (!cmd.equals(""))
        this.ExecUpdate( cmd);
    }
    finally {
    input.close();
    }
    }
    catch (IOException ex){
    ex.printStackTrace();
}

  }
  public void importTablesFromFile(String nomBase,Connexion myCnx, Statement st, FichierTexte fichiertexte, db theBase , String langue){
    // Lire la sequence des tables dans CreateBaseVide.sql {nomTable, ordre}
      // ecrire dans ListeTable
      // ecrire la liste des tables dans Migration  
    int num=1;
    int pending = 0;
    String cmd = "";
    int pos = -1;
      this.listeTables.removeAllElements();
    try {

    BufferedReader input = new BufferedReader(new FileReader(fichiertexte.Basedirectory + "/" + fichiertexte.filename));
    try {
    String ligne = null;
    
    while (( ligne = input.readLine()) != null){
        if (reprise)
        {
          pos = ligne.indexOf("@@12345@@"); 
          if (pos > -1)
            {
                reprise = false;           
            }          
          
        }
        else
        {
        
        ligne = translate(ligne, langue);
        pos = ligne.indexOf("use Base");
        if (pos > -1)
        {
            ligne = "use " + theBase.nom;           
        }
        // si la ligne ne contient pas INSERT et pending = 0
        //pos = ligne.indexOf("use BaseVide")+ ligne.indexOf("--- Fichier:") + ligne.indexOf("DEBUT dump:") + ligne.indexOf("DELETE FROM ")+ ligne.indexOf("DELETE FROM ")+ ligne.indexOf("SET IDENTITY_INSERT");
        if (
                (ligne.indexOf("use "+theBase.nom) > -1)
                ||
                (ligne.indexOf("--- Fichier:") > -1)
                ||
                (ligne.indexOf("DEBUT dump:") > -1)
                ||
                (ligne.indexOf("DELETE FROM ") > -1)
                ||
                (ligne.indexOf("SET IDENTITY_INSERT") > -1)                                
           )
        {
        //      -- executer la commande
            if (!cmd.equals(""))
            {
                this.ExecUpdate( cmd);
                cmd = "";
                pending = 0;
            }
            this.ExecUpdate( ligne);
        }
        else
        {
            
          pos = ligne.indexOf("INSERT INTO [dbo].");   
          if (pos != -1)
          {
            if (!cmd.equals(""))
            {
                this.ExecUpdate( cmd);
                
            }  
                
            cmd = ligne;
          }
            else
            {
                cmd += "\n";
                cmd += ligne;
            }            

        }
        
    }
    }
    if (!cmd.equals(""))
        this.ExecUpdate( cmd);
    }
    finally {
    input.close();
    }
    }
    catch (IOException ex){
    ex.printStackTrace();
}

  }
 
  public void getListeTablesFromSql(String nomBase,Connexion myCnx, Statement st, FichierTexte fichiertexte ){
    // Lire la sequence des tables dans CreateBaseVide.sql {nomTable, ordre}
      // ecrire dans ListeTable
      // ecrire la liste des tables dans Migration  
    int num=1;
      this.listeTables.removeAllElements();
    try {

    BufferedReader input = new BufferedReader(new FileReader(fichiertexte.Basedirectory + "/" + fichiertexte.filename));
    try {
    String ligne = null;
    
    while (( ligne = input.readLine()) != null){
      int pos = -1; //

        pos = ligne.indexOf("CREATE TABLE [dbo].[");
        
        if (pos >= 0) 
        {
            String[] ligneListeTables = ligne.split("CREATE TABLE \\[dbo\\].\\[");
            ligne = ligneListeTables[1];
            ligneListeTables = ligne.split("\\]");
            ligne = ligneListeTables[0];
          //System.out.println("@01234-------- TROUVE= " +ligne);
          table theTable = new table(ligne);
          ligne = input.readLine();                    

          pos = ligne.indexOf("IDENTITY");
          if (pos >= 0)
              theTable.identity = 1;
          else
              theTable.identity = 0;
          theTable.ordre = num;
          theTable.idEtat = 1;
          
          ligne = input.readLine();
          this.listeTables.addElement(theTable);
          num++;
        }
        else
        {
          //System.out.println("@01234-------- NON TROUVE= " +ligne);
        }
        
    }
    }
    finally {
    input.close();
    }
    }
    catch (IOException ex){
    ex.printStackTrace();
}

  }
  
  public void getListeTables(String nomBase,Connexion myCnx, Statement st ){
      
     this.listeTables.removeAllElements();
     ResultSet rs = null;

     req = "SELECT     sysobjects.name, Migration.Migration, Migration.Etat, isnull(Migration.Ordre,0) as ordre";
     req+= " FROM         sysobjects INNER JOIN";
     req+= "                       Migration ON sysobjects.name = Migration.Nom";
     req+= " WHERE     (sysobjects.xtype = 'U')";
     req+= " ORDER BY ordre";

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
       while (rs.next()) {
        table theTable = new table(rs.getString(1));
        theTable.identity = rs.getInt("Migration");
        theTable.idEtat = rs.getInt("Etat");
        theTable.ordre = rs.getInt("ordre");
       
        this.listeTables.addElement(theTable);

       } 
    }catch (SQLException s) {s.getMessage(); }


  }
  
  public void getListeTablesStandby(String nomBase,Connexion myCnx, Statement st ){
      
     this.listeTables.removeAllElements(); 
     ResultSet rs = null;

     req = "SELECT     sys.sysobjects.name, Migration.Migration, Migration.Etat, ISNULL(Migration.Ordre, 0) AS ordre";
    req+= " FROM         sys.sysobjects LEFT OUTER JOIN";
    req+= "                       Migration ON sys.sysobjects.name = Migration.Nom";
    req+= " WHERE     (sys.sysobjects.xtype = 'U') AND (Migration.Etat = 0 OR";
    req+= "                       Migration.Etat IS NULL)";
    req+= " ORDER BY sys.sysobjects.name";

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
       while (rs.next()) {
        table theTable = new table(rs.getString(1));
        theTable.idEtat = rs.getInt("Etat");
        theTable.ordre = rs.getInt("ordre");
       
        this.listeTables.addElement(theTable);

       } 
    }catch (SQLException s) {s.getMessage(); }


  }  
  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;

     req = "SELECT     ordre, nom, driver, url, login, password, langue, serverSMTP, portSMTP, loginSMTP, passwordSMTP FROM   db WHERE id ="+ this.id;

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();
       this.ordre = rs.getInt("ordre");
       this.nom = rs.getString("nom");
        this.driver=rs.getString("driver");
        this.URL=rs.getString("url");
        this.login=rs.getString("login");
        this.psw=rs.getString("password");
        this.langue=rs.getString("langue");
        
        this.serverSMTP=rs.getString("serverSMTP");
        if (this.serverSMTP == null) this.serverSMTP = "";
        
        this.portSMTP=rs.getString("portSMTP");
        if (this.portSMTP == null) this.portSMTP = "";
        
        this.loginSMTP=rs.getString("loginSMTP");
        if (this.loginSMTP == null) this.loginSMTP = "";
        
        this.passwordSMTP=rs.getString("passwordSMTP");
        if (this.passwordSMTP == null) this.passwordSMTP = "";

       } catch (SQLException s) {s.getMessage(); }


  }  
  
  
  public void getAllFromBd2(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;

     req = "SELECT     ordre, nom, driver, url, login, password FROM   db WHERE ordre ="+ this.id;

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();
       this.ordre = rs.getInt("ordre");
       this.nom = rs.getString("nom");
        this.driver=rs.getString("driver");
        this.URL=rs.getString("url");
        this.login=rs.getString("login");
        this.psw=rs.getString("password");

       } catch (SQLException s) {s.getMessage(); }


  }  

public void connect(){

  Connection conn = null;
  Statement st = null;
  try{
    Class.forName(driver);
    conn = DriverManager.getConnection(this.URL + this.nom,this.login,this.psw);
    this.st = conn.createStatement();
  }
  catch(SQLException s) {
      s.printStackTrace();
  }
  catch (Exception s) {
      s.printStackTrace();
  }


}

public void addConnection(){
Statement st = null;
  Connection conn = null;
  try{
    Class.forName(driver);
    conn = DriverManager.getConnection(this.URL + this.nom,this.login,this.psw);
    st = conn.createStatement();
    this.listeCnx.addElement(st);
  }
  catch(SQLException s) {
      s.printStackTrace();
  }
  catch (Exception s) {
      s.printStackTrace();
  }


}

public String connectDb(){
  String result = "Connexion OK";

  Connection conn = null;
  Statement st = null;
  try{
    Class.forName(driver);
    conn = DriverManager.getConnection(this.URL + this.nom,this.login,this.psw);
    this.st = conn.createStatement();
  }
  catch(SQLException s) {
      s.printStackTrace();
      result = s.toString();
  }
  catch (Exception s) {
      s.printStackTrace();
      result = s.toString();
  }

  return result;

} 

  public void disconnect() {
    try {this.st.close();}
    catch(SQLException s) {s.printStackTrace();}
    catch (Exception s) {s.printStackTrace();}
  }
  
  public void removeConnexions() {
    try {
        for (int i=0; i < this.listeCnx.size(); i++)
        {
            Statement theSt = (Statement)this.listeCnx.elementAt(i);
            theSt.close();
        }
    }
    catch(SQLException s) {s.printStackTrace();}
    catch (Exception s) {s.printStackTrace();}
    
    this.listeCnx.removeAllElements();
    
  }  
  
  
  public void trace (String source, String NomStr, String valStr){
       if (this.debug.equals("no")) return;
       System.out.println(getDate() + "::" + "source=" + source + "-- " + NomStr + "=" + valStr);

  }
  
  public void ExecReq(String req) {

    String base_utilisee = "use "+ this.nom;
    if (debug.equals("yes"))
    {
       trace(getDate() + "::" + "base_utilisee=..." + base_utilisee,"---Req",req);
    }
    try{
        this.st.execute(base_utilisee);
        this.rs=this.st.executeQuery(req);
    }
    catch(SQLException s) {
        s.printStackTrace();
    }
    catch (Exception s) {
        s.printStackTrace();
    }

  }  
  
public  void ExecUpdate(String req) {
ResultSet rs = null;
int check=0;
    String base_utilisee = "use "+ this.nom;
    if (this.debug.equals("yes"))
    {
       trace(getDate() + "::" + "base_utilisee=..." + base_utilisee,"---Req",req);
    }


try {  check=this.st.executeUpdate(req);
}
catch (Exception s)
{
System.out.println("@9999 :: sql= "+req);

}

}  
public int ExecUpdateTRansaction(String req) {
ResultSet rs = null;
  int check=0;
    String base_utilisee = "use "+ this.nom;
    if (this.debug.equals("yes"))
    {
       trace(getDate() + "::" + "base_utilisee=..." + base_utilisee,"---Req",req);
    }

  try {  check=this.st.executeUpdate(req);
      }
  catch (Exception s)
                           {
                             this.rollback();

                           check=-1;

                       }
return check;
}  


  public void begin(String transaction){
    this.transaction = transaction;
    req = "BEGIN TRANSACTION "+this.transaction;
    this.ExecReq( req);
  }
  public void commit(){
    req = "COMMIT TRANSACTION "+this.transaction;
    this.ExecReq( req);
  }
  public void rollback(){
    req = "ROLLBACK TRANSACTION "+this.transaction;
    this.ExecReq( req);
  }
  
  public void cpBulkCpFrom(db sourceBd, String table){
    req = "delete from ["+this.nom+"].[dbo].["+table+"]";
    this.ExecUpdate( req);      
      req="INSERT INTO ["+this.nom+"].[dbo].["+table+"]";
      req+=" SELECT * FROM ["+sourceBd.nom+"].[dbo].["+table+"]";
      
      this.ExecUpdate( req);
  }
  
  public void cpBulkCpWithParamFrom(db sourceBd, String table, String params){
      
    req = "delete from ["+this.nom+"].[dbo].["+table+"]";
    this.ExecUpdate( req);
    
    req="INSERT INTO ["+this.nom+"].[dbo].["+table+"] ("+params+")";
    req+=" SELECT "+params+" FROM ["+sourceBd.nom+"].[dbo].["+table+"]";
      
      this.ExecUpdate( req);
  }  
  
 public String getNomById(db sourceBd, int id, String nomTable, String champNom, String champId)
  {
      String nom="";
    req = "SELECT "+champNom+" FROM  "+nomTable+" WHERE "+champId+" =" +id;
    sourceBd.ExecReq( req);
    try {
       while (sourceBd.rs.next()) {
            nom= sourceBd.rs.getString(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return nom;
  }
 
  public Vector get2NomById(db sourceBd, int id, String nomTable1, String champNom1, String champId1, String nomTable2, String champNom2, String pk2, String fk2)
  {
      Vector myKey = new Vector(2);
      String nom="";
   
    req= "SELECT     "+nomTable1+"."+champNom1+", " +nomTable2+"."+champNom2;
    req+=" FROM         "+nomTable1+" INNER JOIN";
    req+="                       "+nomTable2+" ON "+nomTable1+"."+champId1 +" = "+nomTable2+"."+fk2;
    req+=" WHERE "+pk2+" =" +id;
    
    
    sourceBd.ExecReq( req);
    try {
       while (sourceBd.rs.next()) {
           item theKey1 = new item();
            theKey1.nom= sourceBd.rs.getString(1);
            myKey.addElement(theKey1);
            
           item theKey2 = new item();
            theKey2.nom= sourceBd.rs.getString(2);
            myKey.addElement(theKey2);            
       }
       
      } catch (SQLException s) {s.getMessage(); }          
      return myKey;
  }
  
  public int getIdByNom(String nom, String nomTable, String champNom, String champId)
  {
    int id=-1;
    req = "SELECT "+champId+" FROM  "+nomTable+" WHERE "+champNom+" ='" + Utils.clean(nom) +"'";
    this.ExecReq( req);
    try {
       while (rs.next()) {
        id= rs.getInt(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return id;
  }
  
  public int getIdBy2Nom(Vector myKey, String nomTable1, String champNom1, String champId1, String nomTable2, String champNom2, String pk2, String fk2)
  {
    int id=-1;
    String value1 = ((item)myKey.elementAt(0)).nom;
    String value2 = ((item)myKey.elementAt(1)).nom;
    
    req = "SELECT     "+nomTable2+"."+pk2;
    req+=" FROM         "+nomTable2+" INNER JOIN";
    req+="                       "+nomTable1+" ON "+nomTable2+"."+fk2 +" = "+nomTable1+"."+champId1;
    req+=" WHERE     ("+nomTable1+"."+champNom1+" = '"+value1+"') AND ("+nomTable2+"."+champNom2+" = '"+value2+"')";
    
    this.ExecReq( req);
    try {
       while (rs.next()) {
        id= rs.getInt(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return id;
  }  
  
  public int getCount(String nom, int Annee){
    int nb = 0;

    if (nom.equals("CollaborateurCompetence"))
    {
        req = "SELECT count (*) as nb FROM  CollaborateurCompetence WHERE (note <> - 1)";
    }
    else if (nom.equals("BesoinsUtilisateur"))
    {
        req = "SELECT   count(*) as nb";
        req+= "    FROM         BesoinsUtilisateur  INNER JOIN  Roadmap ON BesoinsUtilisateur.idRoadmap = Roadmap.idRoadmap";
        req+= " WHERE     (YEAR(Roadmap.dateMep) >= "+Annee+")";
    }    
    else if (nom.equals("Roadmap"))
    {
           req = "SELECT   count(*) as nb";
           req+=" FROM         Roadmap INNER JOIN";
           req+="           ST_ListeSt_All_Attribute ON Roadmap.idVersionSt = ST_ListeSt_All_Attribute.idVersionSt";
           req+=" WHERE     (Roadmap.LF_Month = -1)";
           req+=" AND     (Roadmap.LF_Year  = -1) AND (YEAR(Roadmap.dateMep) >= "+Annee+")";
    }
    else if (nom.equals("historiqueSuivi"))
    {
        req = "SELECT   count(*) as nb";
        req+=" FROM         historiqueSuivi  INNER JOIN   Roadmap ON historiqueSuivi.idRoadmap = Roadmap.idRoadmap";
        req+=" WHERE (YEAR(Roadmap.dateMep) >= "+(Annee + 1)+")";
    }
    else if (nom.equals("Devis"))
    {
        req = "SELECT   count(*) as nb";
        req+=" FROM         Devis INNER JOIN ";
        req+="                       Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN ";
        req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN ";
        req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN ";
        req+="                      typeEtatDevis ON Devis.idEtat = typeEtatDevis.id ";
        req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+")"; 
    }
    else if (nom.equals("DevisBudget"))
    {
        req = "SELECT   count(*) as nb";
        req+=" FROM         DevisBudget INNER JOIN";
        req+="                       Devis ON DevisBudget.idDevis = Devis.id INNER JOIN";
        req+="                       Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap    ";
        req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+")";  
    }  
    else if (nom.equals("Jalons"))
    {
        req = "SELECT   count(*) as nb";
        req+=" FROM         Jalons INNER JOIN";
        req+="                       Devis ON Jalons.idJalon = Devis.id INNER JOIN";
        req+="                       Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap ";
        req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+")";   
    }  
    else if (nom.equals("categorieTest"))
    {
       req = "SELECT   count(*) as nb";
        req+=" FROM         categorieTest INNER JOIN";
        req+="                       Roadmap ON categorieTest.idRoadmap = Roadmap.idRoadmap ";
        req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+")";  
    }    
    else if (nom.equals("Tests"))
    {
       req = "SELECT   count(*) as nb";
        req+=" FROM         Tests INNER JOIN";
        req+="                       categorieTest ON Tests.idCategorie = categorieTest.id INNER JOIN";
        req+="                       Roadmap ON categorieTest.idRoadmap = Roadmap.idRoadmap INNER JOIN";
        req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
        req+="                       St ON VersionSt.stVersionSt = St.idSt ";
        req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+")";   
    } 
    else if (nom.equals("Campagne"))
    {
       req = "SELECT   count(*) as nb";
        req+=" FROM         Campagne INNER JOIN";
        req+="                       Roadmap ON Campagne.idRoadmap = Roadmap.idRoadmap ";
        req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+")";     
    }    
    else if (nom.equals("resultatTest"))
    {
       req = "SELECT   count(*) as nb";
        req+=" FROM         resultatTest INNER JOIN";
        req+="                       Tests ON resultatTest.idTest = Tests.id INNER JOIN";
        req+="                       Campagne ON resultatTest.idCampagne = Campagne.id INNER JOIN";
        req+="                       Roadmap ON Campagne.idRoadmap = Roadmap.idRoadmap ";
        req+=" WHERE (YEAR(Roadmap.dateMep) >= "+(Annee+1)+")"; 
    }    
    else if (nom.equals("actionSuivi"))
    {
       req = "SELECT   count(*) as nb";
        req+=" FROM         actionSuivi INNER JOIN";
        req+="                       Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap ";
        req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+")";   
    }  
    else if (nom.equals("Consomme"))
    {
       req = "SELECT   count(*) as nb";
    req+=" FROM         Consomme INNER JOIN";
    req+="                       Membre ON Consomme.projetMembre = Membre.idMembre INNER JOIN";
    req+="                      actionSuivi ON Consomme.idAction = actionSuivi.id INNER JOIN";
    req+="                       Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap";
    req+=" WHERE     (Consomme.Charge > 0) ";
    req+=" AND (YEAR(Roadmap.dateMep) >= "+Annee+")";  
    }   
    
    else if (nom.equals("ActionAnomalie"))
    {
       req = "SELECT   count(*) as nb";
    req+=" FROM         ActionAnomalie INNER JOIN";
    req+="                       actionSuivi ON ActionAnomalie.idAction = actionSuivi.id INNER JOIN";
    req+="                      Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap ";
    req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+")";     
    }
  
    else if (nom.equals("PlanOperationnelClient"))
    {
        req = "SELECT   count(*) as nb";
    req += " FROM         PlanOperationnelClient ";
    req +=" WHERE     (Annee >= "+Annee+") AND (Service <> '')";    
    }
    
    else if (nom.equals("PlanDeCharge"))
    {
        req = "SELECT   count(*) as nb";
    req+=" FROM         PlanDeCharge INNER JOIN";
    req+="                       Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap";
    req+=" WHERE     (YEAR(Roadmap.dateMep) >= "+Annee+")";
    }        

    else
    {
        req = "SELECT   count(*) as nb FROM "+nom;
    }        
    
    
    this.ExecReq( req);

    try {
      while (rs.next()) {
        nb= rs.getInt("nb");
        }
    } catch (SQLException s) {s.getMessage();}

    return nb;
  } 
  
  public int getCount(String nom){
    if ((nom == null) || (nom.equals(""))) return 0;
    int nb = 0;
    req = "SELECT   count(*) as nb FROM "+nom;
    this.ExecReq( req);

    try {
      while (rs.next()) {
        nb= rs.getInt("nb");
        }
    } catch (SQLException s) {s.getMessage();}

    return nb;
  } 
  // -------------------------------------- Donnees de Base ------------------------------------------//
  public Vector readConfig(){
    Vector Liste = new Vector(10);
    req = "SELECT    nom, valeur, Ordre, security FROM  Config";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      item theitem = new item(-1);
      theitem.nom = rs.getString("nom");
      theitem.valeur = rs.getString("valeur");
      theitem.ordre = rs.getInt("Ordre");
      theitem.security = rs.getInt("security");
      Liste.addElement(theitem);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }   
  
  public void writeConfig(Vector Liste){
    req = "delete from Config";
    this.ExecUpdate( req);
    
    for (int i=0; i < Liste.size(); i++)
    {
        item theitem = (item)Liste.elementAt(i);
          req = "INSERT Config (nom, valeur, Ordre, security)";
          req+="  VALUES (";
          req+="'"+theitem.nom+"'";
          req+=",";
          req+="'"+theitem.valeur+"'";
          req+=",";
          req+="'"+theitem.ordre+"'";
          req+=",";
          req+="'"+theitem.security+"'";
          req+=")";

        this.ExecUpdate(req);

    
    }
  }
  
  public Vector readOnglet(){
    Vector Liste = new Vector(10);
    req = "SELECT     Ordre, nom, signet, visibility, Type, Action, Admin FROM  Onglet";
    this.ExecReq( req);
    try {
       while (rs.next()) {
        Onglet theOnglet = new Onglet();
        theOnglet.ordre = rs.getInt("Ordre");
        theOnglet.nom = rs.getString("nom");
        theOnglet.signet = rs.getString("signet");
        theOnglet.visibility = rs.getInt("visibility");
        theOnglet.Type = rs.getString("Type");
        theOnglet.Action = rs.getString("Action");
        theOnglet.Admin = rs.getInt("Admin");

      Liste.addElement(theOnglet);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
  
  public void writeOnglet(Vector Liste){
    req = "delete from Onglet";
    this.ExecUpdate( req);
    
    for (int i=0; i < Liste.size(); i++)
    {
        Onglet theOnglet = (Onglet)Liste.elementAt(i);
        if (theOnglet.Action !=  null)
            theOnglet.Action= theOnglet.Action.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
        
          req = "INSERT Onglet (Ordre, nom, signet, visibility, Type, Action, Admin)";
          req+="  VALUES (";
          req+="'"+theOnglet.ordre+"'";
          req+=",";
          req+="'"+theOnglet.nom+"'";
          req+=",";
          req+="'"+theOnglet.signet+"'";
          req+=",";
          req+="'"+theOnglet.visibility+"'";
          req+=",";
          req+="'"+theOnglet.Type+"'";
          req+=",";
          req+="'"+theOnglet.Action+"'";
          req+=",";
          req+="'"+theOnglet.Admin+"'";          
          req+=")";

        this.ExecUpdate(req);

    
    }
  }  
      
  // -------------------------------------------------------------------------------------------------//
  // -------------------------------------- Organisation ---------------------------------------------//
  public Vector readDirection(){
    Vector ListeDirection = new Vector(10);
    req = "SELECT nomDirection, libelleDirection FROM DIRECTION ORDER BY idDirection";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      Direction theDirection = new Direction(-1);
      theDirection.nom = rs.getString("nomDirection");
      theDirection.description = rs.getString("libelleDirection");
      ListeDirection.addElement(theDirection);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return ListeDirection;
  }  
  
  public void writeDirection(Vector ListeDirection){
    req = "delete from Roadmap";
    this.ExecUpdate( req);
    
    req = "delete from VersionSt";
    this.ExecUpdate( req);
    
    req = "delete from St";
    this.ExecUpdate( req);    
 
    req = "delete from EquipeMembre";
    this.ExecUpdate( req);
    
    req = "delete from CollaborateurCompetence";
    this.ExecUpdate( req);
    
    req = "delete from Membre";
    this.ExecUpdate( req);
    
    req = "delete from Service";
    this.ExecUpdate( req);
            
    req = "delete from direction";
    this.ExecUpdate( req);
    for (int i=0; i < ListeDirection.size(); i++)
    {
        Direction theDirection = (Direction)ListeDirection.elementAt(i);
        req = "EXEC ACTEUR_InsererDirection ";
        req+="'"+theDirection.nom+"'";
        req+=",";
        if (theDirection.description !=  null)
            req+="'" + theDirection.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
        else
            req+="''";

        this.ExecUpdate(req);

    
    } 
    
  }  
  
  public void deleteDirection(){
    req = "delete from CollaborateurCompetence";
    this.ExecUpdate( req);
    
    req = "delete from CollaborateurCompetence";
    this.ExecUpdate( req);
    
    req = "delete from Membre";
    this.ExecUpdate( req);
    
    req = "delete from Service";
    this.ExecUpdate( req);
            
    req = "delete from direction";
    this.ExecUpdate( req);
    
  }   
  
  public void updateKeyDirection(db sourceBd,Vector Liste){

    for (int i=0; i < Liste.size(); i++)
    {
        Service theService = (Service)Liste.elementAt(i);
        int id_Origine = theService.direction;
        String id_Destination = (String)ListeDirections.get(id_Origine);
        if (id_Destination == null)
        {
            String nomDir=getNomById(sourceBd, theService.direction, "Direction", "nomDirection", "idDirection");
            theService.direction=getIdByNom(nomDir, "Direction", "nomDirection", "idDirection");
            ListeDirections.put(id_Origine, ""+ theService.direction);
        }
        else
        {
          theService.direction = Integer.parseInt(id_Destination);
        }
    }     
  }
  
  public void updateKeyIdPere(db sourceBd,Vector ListeService){
    
    for (int i=0; i < ListeService.size(); i++)
    {
        Service theService = (Service)ListeService.elementAt(i);
        if (theService.idServicePere == -1) continue;
        String nomService=getNomById(sourceBd, theService.idServicePere, "Service", "nomService", "idService");
        theService.idServicePere=getIdByNom(nomService, "Service", "nomService", "idService");
        req = "UPDATE Service SET idServicePere="+theService.idServicePere+ " WHERE nomService='"+theService.nom+"'";
        this.ExecUpdate( req);
    }    
  }  
   
  public Vector readService(){
    Vector ListeService = new Vector(10);
    req="SELECT     nomService, descService, dirService, typeService, sendRetard, nomServicePO, nomServiceImputations, idServicePere, CentreCout, isMoe, isMoa, isGouvernance, isExploitation";
    req+="    FROM         Service ORDER BY idService";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      Service theService = new Service(-1);
      theService.nom = rs.getString(1);
      theService.description = rs.getString(2);
      theService.direction = rs.getInt(3);
      theService.type = rs.getString(4);
      theService.sendRetard = rs.getInt(5);
      theService.nomServicePO= rs.getString(6);
      theService.nomServiceImputations= rs.getString("nomServiceImputations");
      theService.idServicePere= rs.getInt("idServicePere");
      theService.CentreCout= rs.getString("CentreCout");
      if (theService.CentreCout == null) theService.CentreCout = "";
      theService.isMoe = rs.getInt("isMoe");
      theService.isMoa = rs.getInt("isMoa");
      theService.isGouvernance = rs.getInt("isGouvernance");
      theService.isExploitation = rs.getInt("isExploitation");
      ListeService.addElement(theService);
      //theService.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return ListeService;
  } 
  
  public void writeService(Vector ListeService){
    req = "delete from Roadmap";
    this.ExecUpdate( req);
    
    req = "delete from VersionSt";
    this.ExecUpdate( req);
    
    req = "delete from St";
    this.ExecUpdate( req);   
    
    req = "delete from EquipeMembre";
    this.ExecUpdate( req);
    
    req = "delete from CollaborateurCompetence";
    this.ExecUpdate( req);    
    
    req = "delete from Membre";
    this.ExecUpdate( req);
    
    req = "delete from Service";
    this.ExecUpdate( req);
    
    for (int i=0; i < ListeService.size(); i++)
    //for (int i=0; i < 1; i++)
    {
        Service theService = (Service)ListeService.elementAt(i);
        String myDesc= "";
        if (theService.description != null)
          myDesc=theService.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
        
        req = "EXEC ACTEUR_InsererService ";
        req+="'"+theService.nom+"','";
        req+=myDesc+"','";
        req+=theService.direction+"','";
        //req+="NULL,'";
        req+=theService.type+"','";
        req+=theService.idServicePere+"','";
        //req+="NULL,'";
        req+=theService.nomServiceImputations+"','";
        req+=theService.CentreCout+"','";
        req+=theService.isMoe+"','";
        req+=theService.isMoa+"','";
        req+=theService.isGouvernance+"','";
        req+=theService.isExploitation+"'";

        this.ExecUpdate(req);

    
    } 
    
  }
  
  public Vector readMembre(){
    Vector ListeMembres = new Vector(10);
    Date theDate = null;
    req="SELECT     Membre.nomMembre, Membre.prenomMembre, Service.dirService, Service.idService, Membre.fonctionMembre, Membre.mail, Membre.LoginMembre, Membre.isAdmin, Membre.isProjet, ";
    req+="                      Membre.AO, Membre.Prix, Membre.dateEntree, Membre.dateSortie, Membre.intitulePoste, Membre.niveau, Membre.Mission, Membre.societeMembre, Service_1.nomService,  ";
    req+="                     Direction.nomDirection, Membre.idMembre, Membre.Telephone_M, Membre.Telephone_B, Membre.Fax, Membre.isTest, Membre.AppletNotSupported, Membre.couleurEB, Membre.couleurDEV,  ";
    req+="                      Membre.couleurTEST, Membre.etp, Membre.profilST, Membre.listediffusionBrief, Membre.isPo, Membre.password, Service.nomService AS service, Membre.photo, Membre.isManager, Membre.couleurMES ";
    req+=" FROM         Membre INNER JOIN ";
    req+="                       Service ON Membre.serviceMembre = Service.idService INNER JOIN ";
    req+="                       Direction ON Service.dirService = Direction.idDirection LEFT OUTER JOIN ";
    req+="                      Service AS Service_1 ON Membre.societeMembre = Service_1.idService ORDER BY Membre.idMembre";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      Collaborateur theCollaborateur=new Collaborateur(-1);
      theCollaborateur.nom = rs.getString(1);
      theCollaborateur.prenom = rs.getString(2);
      theCollaborateur.direction = rs.getInt(3);
      theCollaborateur.service = rs.getInt(4);
      theCollaborateur.fonction = rs.getString(5);
      theCollaborateur.mail = rs.getString(6);
      theCollaborateur.Login = rs.getString(7);
      theCollaborateur.isAdmin = rs.getInt(8);
      theCollaborateur.isProjet = rs.getInt(9);

      theCollaborateur.AO =rs.getString(10);
      if (theCollaborateur.AO == null) theCollaborateur.AO = "";

      theCollaborateur.Prix =rs.getInt(11);
      theDate = rs.getDate(12);
      if (theDate !=null)
       {
         theCollaborateur.dateEntree = theDate.getDate() + "/" + (theDate.getMonth() + 1) +
             "/" + (theDate.getYear() + 1900);
       }
       else theCollaborateur.dateEntree = "";

       theDate = rs.getDate(13);
       if (theDate !=null)
        {
          theCollaborateur.dateSortie = theDate.getDate() + "/" + (theDate.getMonth() + 1) +
              "/" + (theDate.getYear() + 1900);
       }
       else theCollaborateur.dateSortie = "";

      theCollaborateur.intitulePoste =rs.getString(14);
      if (theCollaborateur.intitulePoste == null) theCollaborateur.intitulePoste = "";

      theCollaborateur.niveau =rs.getInt(15);
      theCollaborateur.Mission =rs.getString(16);
      if (theCollaborateur.Mission == null) theCollaborateur.Mission = "";

      theCollaborateur.societe =rs.getInt(17);
      theCollaborateur.nomSociete = rs.getString("nomService");
      theCollaborateur.nomDirection = rs.getString("nomDirection");

      theCollaborateur.id =  rs.getInt("idMembre");
      theCollaborateur.Telephone_M = rs.getString("Telephone_M");
      if (theCollaborateur.Telephone_M == null) theCollaborateur.Telephone_M = "";
      theCollaborateur.Telephone_B = rs.getString("Telephone_B");
      if (theCollaborateur.Telephone_B == null) theCollaborateur.Telephone_B = "";
      theCollaborateur.Fax = rs.getString("Fax");
      if (theCollaborateur.Fax == null) theCollaborateur.Fax = "";
      theCollaborateur.isTest = rs.getInt("isTest");
      //theCollaborateur.AppletNotSupported = rs.getInt("AppletNotSupported");
      theCollaborateur.isInterne = rs.getInt("AppletNotSupported");
      
      theCollaborateur.couleurEB = rs.getString("couleurEB");
      if (theCollaborateur.couleurEB == null ) theCollaborateur.couleurEB = "F9FF40";
      
      theCollaborateur.couleurDEV = rs.getString("couleurDEV");
      if (theCollaborateur.couleurDEV == null ) theCollaborateur.couleurDEV = "4BFF14";
      
      theCollaborateur.couleurTEST = rs.getString("couleurTEST");
      if (theCollaborateur.couleurTEST == null ) theCollaborateur.couleurTEST = "FF0303";
      
      theCollaborateur.etp = rs.getFloat("etp");
      theCollaborateur.profilST = rs.getString("profilST");
      theCollaborateur.isBrief = rs.getInt("listediffusionBrief");
      theCollaborateur.isPo = rs.getInt("isPo");
      
      theCollaborateur.Password = rs.getString("password");
      if (theCollaborateur.Password == null) theCollaborateur.Password = "";
      
      theCollaborateur.nomService = rs.getString("service");
      theCollaborateur.photo = rs.getString("photo");
      if ((theCollaborateur.photo == null) || (theCollaborateur.photo.equals("")))
          theCollaborateur.photo = "images/Direction.png";
      
      theCollaborateur.isManager = rs.getInt("isManager");
      
      theCollaborateur.couleurMES = rs.getString("couleurMES");
      if (theCollaborateur.couleurMES == null ) theCollaborateur.couleurMES = "D6E2D3";
      
      ListeMembres.addElement(theCollaborateur);
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return ListeMembres;
  } 
  
  public void updateKeyIdMembreForMembre(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Collaborateur theCollaborateur = (Collaborateur)Liste.elementAt(i);
        int id_Origine = theCollaborateur.id;
        String id_Destination = (String)ListeMembres.get(id_Origine);
        if (id_Destination == null)
        {
            String nomMembre=getNomById(sourceBd, theCollaborateur.id, "Membre ", "LoginMembre", "idMembre");
            theCollaborateur.id=getIdByNom(nomMembre, "Membre ", "LoginMembre", "idMembre");
            ListeMembres.put(id_Origine, ""+ theCollaborateur.id);
        }
        else
        {
          theCollaborateur.id= Integer.parseInt(id_Destination);
        }
    }     
  }  
  
  public void updateMembre(Vector ListeMembres){
    for (int i=0; i < ListeMembres.size(); i++)
    //for (int i=0; i < 1; i++)
    {
        Collaborateur theCollaborateur = (Collaborateur)ListeMembres.elementAt(i);      
        req = "UPDATE Membre SET ";
        req+= " couleurEB='"+theCollaborateur.couleurEB+"'";
        req+= " ,";
        req+= " couleurDEV='"+theCollaborateur.couleurDEV+"'";
        req+= " ,";
        req+= " couleurTEST='"+theCollaborateur.couleurTEST+"'";   
        req+= " WHERE idMembre="+theCollaborateur.id;   
        
        try{
                this.ExecUpdate(req);
            }
            catch (Exception e)
            {
                
                //this.trace("@@@@"+i+"/"+Liste.size(),"req",""+req);
            }        
    }
  }  
  
  public void writeMembre(Vector ListeMembres){
      
    req = "delete from Roadmap";
    this.ExecUpdate( req);
    
    req = "delete from VersionSt";
    this.ExecUpdate( req);
    
    req = "delete from St";
    this.ExecUpdate( req); 
    
    req = "delete from EquipeMembre";
    this.ExecUpdate( req);
    
    req = "delete from CollaborateurCompetence";
    this.ExecUpdate( req);    
    
    req = "delete from Membre";
    this.ExecUpdate( req);
    
    for (int i=0; i < ListeMembres.size(); i++)
    //for (int i=0; i < 1; i++)
    {
        Collaborateur theCollaborateur = (Collaborateur)ListeMembres.elementAt(i);
        
    req = "INSERT Membre";
    req+=" ( ";
    req+= " nomMembre, ";
    req+= " prenomMembre, ";
    req+= " serviceMembre, ";
    req+= " fonctionMembre,";
    req+= " mail,";
    req+= " LoginMembre,";
    req+= " isAdmin,";
    req+= " isProjet,";
    req+= " AO ,";
    req+= " Prix ,";
    req+= " dateEntree,";
    req+= " dateSortie ,";
    req+= " intitulePoste ,";
    req+= " niveau ,";
    req+= " Mission ,";
    req+= " societeMembre,";
    req+= " Telephone_M,";
    req+= " Telephone_B,";
    req+= " Fax,";
    req+= " isTest,";
    req+= " AppletNotSupported,";
    req+= " listediffusionBrief ,";
    req+= " isPo,";
    req+= " password,";
    req+= " photo,";
    req+= " isManager,";
    req+= " couleurEB,";
    req+= " couleurDEV,";
    req+= " couleurTEST,";
    req+= " couleurMES";
    req+= " )";

    req+= " VALUES";
    req+= " ( ";
      req+="'"+theCollaborateur.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"','";
      req+=theCollaborateur.prenom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"','";
      req+=theCollaborateur.service+"','";
      //req+="NULL"+",'";
      req+=theCollaborateur.fonction+"','";
      req+=theCollaborateur.mail.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"','";
      req+=theCollaborateur.Login.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"','";
      req+=theCollaborateur.isAdmin+"','";
      req+=theCollaborateur.isProjet+"','";
      req+=theCollaborateur.AO+"','";
      req+=theCollaborateur.Prix+"','";
      req+=theCollaborateur.dateEntree+"','";
      req+=theCollaborateur.dateSortie+"','";
      req+=theCollaborateur.intitulePoste.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"','";
      req+=theCollaborateur.niveau+"','";
      req+=theCollaborateur.Mission.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"','";
      req+=theCollaborateur.societe+"','";
      req+=theCollaborateur.Telephone_M+"','";
      req+=theCollaborateur.Telephone_B+"','";
      req+=theCollaborateur.Fax+"','";
      req+=theCollaborateur.isTest+"','";
      //req+=theCollaborateur.AppletNotSupported+"','";
      req+=theCollaborateur.isInterne+"','";
      req+=theCollaborateur.isBrief+"','";
      req+=theCollaborateur.isPo+"','";
      req+=theCollaborateur.Password+"','";
      req+=theCollaborateur.photo+"','";
      req+=theCollaborateur.isManager+"','"; 
      req+=theCollaborateur.couleurEB+"','"; 
      req+=theCollaborateur.couleurDEV+"','"; 
      req+=theCollaborateur.couleurTEST+"','"; 
      req+=theCollaborateur.couleurMES+"'"; 
      req+=")"; 


        this.ExecUpdate(req);

    
    }
    
  }
  
  public void updateKeyService(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Collaborateur theCollaborateur = (Collaborateur)Liste.elementAt(i);
        int id_Origine = theCollaborateur.service;
        String id_Destination = (String)ListeServices.get(id_Origine);
        if (id_Destination == null)
        {
            String nomService=getNomById(sourceBd, theCollaborateur.service, "Service", "nomService", "idService");
            theCollaborateur.service=getIdByNom(nomService, "Service", "nomService", "idService");
            ListeServices.put(id_Origine, ""+ theCollaborateur.service);
        }
        else
        {
          theCollaborateur.service = Integer.parseInt(id_Destination);
        }
    }     
  }
  
 public Vector readEquipe(){
    Vector Liste = new Vector(10);
    req = "SELECT      nom, [desc], isKpi FROM   Equipe ORDER BY id";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      Equipe theEquipe = new Equipe(-1);
      theEquipe.nom = rs.getString("nom");
      theEquipe.description = rs.getString("desc");
      if (theEquipe.description != null)
          theEquipe.description=theEquipe.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
      theEquipe.isKpi = rs.getInt("isKpi");
      Liste.addElement(theEquipe);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeEquipe(Vector Liste){
       
    req = "delete from typeGraphes";
    this.ExecUpdate( req); 
    
    req = "delete from EquipeMembre";
    this.ExecUpdate( req);
    
    req = "delete from Equipe";
    this.ExecUpdate( req);

    for (int i=0; i < Liste.size(); i++)
    {
        Equipe theEquipe = (Equipe)Liste.elementAt(i);
          req = "INSERT Equipe ( nom, [desc], isKpi)";
          req+="  VALUES (";
          req+="'"+theEquipe.nom+"'";
          req+=",";
          req+="'"+theEquipe.description+"'";
          req+=",";
          req+="'"+theEquipe.isKpi+"'";

          req+=")";

        this.ExecUpdate(req);

    
    } 
    
  } 
   
 public Vector readEquipeMembre(){
    Vector Liste = new Vector(10);
    req = "SELECT  idMembre, idMembreEquipe, type FROM   EquipeMembre ORDER BY id";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      EquipeMembre theEquipeMembre = new EquipeMembre(-1);
      theEquipeMembre.idMembre = rs.getInt("idMembre");
      theEquipeMembre.idMembreEquipe = rs.getInt("idMembreEquipe");
      theEquipeMembre.type = rs.getString("type");
      //if (Liste.size() == 0)
        Liste.addElement(theEquipeMembre);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  } 
 
   public void writeEquipeMembre(Vector Liste){
    req = "delete from EquipeMembre";
    this.ExecUpdate( req);

    for (int i=0; i < Liste.size(); i++)
    {
        EquipeMembre theEquipeMembre = (EquipeMembre)Liste.elementAt(i);
          req = "INSERT EquipeMembre (idMembre, idMembreEquipe, type)";
          req+="  VALUES (";
          req+="'"+theEquipeMembre.idMembre+"'";
          req+=",";
          req+="'"+theEquipeMembre.idMembreEquipe+"'";
          req+=",";
          req+="'"+theEquipeMembre.type+"'";

          req+=")";

        this.ExecUpdate(req);

    
    } 
    
  }
  public void updateKeyMembre(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        EquipeMembre theEquipeMembre = (EquipeMembre)Liste.elementAt(i);
        int id_Origine = theEquipeMembre.idMembre;
        String id_Destination = (String)ListeMembres.get(id_Origine);
        if (id_Destination == null)
        {
            String nomMembre=getNomById(sourceBd, theEquipeMembre.idMembre, "Membre ", "LoginMembre", "idMembre");
            theEquipeMembre.idMembre=getIdByNom(nomMembre, "Membre ", "LoginMembre", "idMembre");
            ListeMembres.put(id_Origine, ""+ theEquipeMembre.idMembre);
        }
        else
        {
          theEquipeMembre.idMembre = Integer.parseInt(id_Destination);
        }
    }     
  } 
  
  public void updateKeyEquipe(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        EquipeMembre theEquipeMembre = (EquipeMembre)Liste.elementAt(i);
        int id_Origine = theEquipeMembre.idMembreEquipe;
        String id_Destination = (String)ListeEquipes.get(id_Origine);
        if (id_Destination == null)
        {
            String nomEquipe=getNomById(sourceBd, theEquipeMembre.idMembreEquipe, "Equipe ", "nom", "id");
            theEquipeMembre.idMembreEquipe=getIdByNom(nomEquipe, "Equipe ", "nom", "id");
            ListeEquipes.put(id_Origine, ""+ theEquipeMembre.idMembreEquipe);
        }
        else
        {
          theEquipeMembre.idMembreEquipe = Integer.parseInt(id_Destination);
        }
    }     
  }  
  
  // ---------------------------------------- fin Organisation ------------------------------------------------------//
  
// ------------------------------------------- Donnees Base Produit---------------------------------//
 public Vector readSi(){
    Vector Liste = new Vector(10);
    req = "SELECT     nomSI, descSI, nomProcessus, scope, proprietaire, ordre, isEquipement, isComposant, isSt, isAppli, isActeur FROM   SI ORDER BY idSI";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      SI theSI = new SI(-1);
      theSI.nom = rs.getString("nomSI");
      theSI.descSI = rs.getString("descSI");
      if (theSI.descSI !=  null)
            theSI.descSI= theSI.descSI.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");      
      theSI.nomProcessus = rs.getString("nomProcessus");
      theSI.scope = rs.getInt("scope");
      theSI.proprietaire = rs.getString("proprietaire");
      theSI.ordre = rs.getInt("ordre");
      theSI.isEquipement = rs.getInt("isEquipement");
      theSI.isComposant = rs.getInt("isComposant");
      theSI.isSt = rs.getInt("isSt");
      theSI.isAppli = rs.getInt("isAppli");
      theSI.isActeur = rs.getInt("isActeur");

      Liste.addElement(theSI);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeSi(Vector Liste){

    req = "delete from St";
    this.ExecUpdate( req);
    
    req = "delete from VersionSt";
    this.ExecUpdate( req);    
    
    req = "delete from TypeSi";
    this.ExecUpdate( req);
    
    req = "delete from SI";
    this.ExecUpdate( req);
    

    for (int i=0; i < Liste.size(); i++)
    {
        SI theSI = (SI)Liste.elementAt(i);
    req = "INSERT INTO [SI]";
    req+= "           ([nomSI]";
    req+= "            ,[descSI]";
    req+= "            ,[nomProcessus]";
    req+= "            ,[scope]";
    req+= "            ,[proprietaire]";
    req+= "            ,[ordre]";
    req+= "            ,[isEquipement]";
    req+= "            ,[isComposant]";
    req+= "            ,[isSt]";
    req+= "            ,[isAppli]";
    req+= "            ,[isActeur])";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+theSI.nom+"'" + ",";
    req+= "'"+theSI.descSI+"'" + ",";
    req+= "'"+theSI.nomProcessus+"'" + ",";
    req+= "'"+theSI.scope+"'" + ",";
    req+= "'"+theSI.proprietaire+"'"+ ",";
    req+= "'"+theSI.ordre+"'"+ ",";
    req+= "'"+theSI.isEquipement+"'"+ ",";
    req+= "'"+theSI.isComposant+"'"+ ",";
    req+= "'"+theSI.isSt+"'"+ ",";
    req+= "'"+theSI.isAppli+"'"+ ",";
    req+= "'"+theSI.isActeur+"'";
    req+= "            )";

        this.ExecUpdate(req);

    
    } 
    
  }  
   
public Vector readTypeSi(){
    Vector Liste = new Vector(10);
    req = "SELECT    nomTypeSi, descTypeSi, siTypesi, couleurSI, couleurdefautSI FROM   TypeSi ORDER BY idTypeSi";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      TypeSi theTypeSi = new TypeSi(-1);
      theTypeSi.nom = rs.getString("nomTypeSi");
      theTypeSi.desc = rs.getString("descTypeSi");
      if (theTypeSi.desc !=  null)
            theTypeSi.desc= theTypeSi.desc.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");       
      theTypeSi.idSi = rs.getInt("siTypesi");
      theTypeSi.couleurSI = rs.getString("couleurSI"); 
      theTypeSi.couleurdefautSI = rs.getString("couleurdefautSI");

      Liste.addElement(theTypeSi);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeTypeSi(Vector Liste){

    req = "delete from St";
    this.ExecUpdate( req);
    
    req = "delete from VersionSt";
    this.ExecUpdate( req);    
      
    req = "delete from TypeSi";
    this.ExecUpdate( req);

    

    for (int i=0; i < Liste.size(); i++)
    {
        TypeSi theTypeSi = (TypeSi)Liste.elementAt(i);
    req = "INSERT INTO [typeSi]";
    req+= "           ([nomTypeSi]";
    req+= "            ,[descTypeSi]";
    req+= "            ,[siTypesi]";
    req+= "            ,[couleurSI]";
    req+= "            ,[couleurdefautSI])";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+theTypeSi.nom+"'" + ",";
    req+= "'"+theTypeSi.desc+"'" + ",";
    req+= "'"+theTypeSi.idSi+"'" + ",";
    req+= "'"+theTypeSi.couleurSI+"'" + ",";
    req+= "'"+theTypeSi.couleurdefautSI+"'";
    req+= "            )";

        this.ExecUpdate(req);

    
    } 
    
  }
   
  public void updateKeySi(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        TypeSi theTypeSi = (TypeSi)Liste.elementAt(i);
        int id_Origine = theTypeSi.idSi;
        String id_Destination = (String)ListeSi.get(id_Origine);
        if (id_Destination == null)
        {
            String nomSi=getNomById(sourceBd, theTypeSi.idSi, "SI ", "nomSI", "idSI");
            theTypeSi.idSi=getIdByNom(nomSi, "SI ", "nomSI", "idSI");
            ListeSi.put(id_Origine, ""+ theTypeSi.idSi);
        }
        else
        {
          theTypeSi.idSi = Integer.parseInt(id_Destination);
        }
    }     
  }  
  
public Vector readMacroSt(){
    Vector Liste = new Vector(10);
    req = "SELECT     nomMacrost, siMacrost, descMacrost, CartoBulle FROM   MacroSt ORDER BY idMacrost";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      MacroSt theMacroSt = new MacroSt(-1);
      theMacroSt.nom = rs.getString("nomMacrost");
      theMacroSt.idSi = rs.getInt("siMacrost");
      theMacroSt.desc = rs.getString("descMacrost");
      if (theMacroSt.desc !=  null)
            theMacroSt.desc= theMacroSt.desc.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''"); 
      
      theMacroSt.AccesRapide = rs.getString("CartoBulle");

      Liste.addElement(theMacroSt);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeMacroSt(Vector Liste){

    req = "delete from St";
    this.ExecUpdate( req);
    
    req = "delete from VersionSt";
    this.ExecUpdate( req);    
      
    req = "delete from MacroSt";
    this.ExecUpdate( req);

    for (int i=0; i < Liste.size(); i++)
    {
        MacroSt theMacroSt = (MacroSt)Liste.elementAt(i);
    req = "INSERT INTO [MacroSt]";
    req+= "           ([nomMacrost]";
    req+= "            ,[aliasMacrost]";
    req+= "            ,[siMacrost]";
    req+= "            ,[descMacrost]";
    req+= "            ,[CartoBulle]";
    req+= "            ,[nomMacrostSpinoza]";
    req+= "            ,[nomMacrostWebPo]";
    req+= "            ,[idCouche]";
    req+= "            ,[offset_X]";
    req+= "            ,[offset_Y]";
    req+= "            ,[map_X]";
    req+= "            ,[map_Y])";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+theMacroSt.nom+"'" + ",";
    req+= "'"+theMacroSt.nom+"'" + ",";
    req+= "'"+theMacroSt.idSi+"'" + ",";
    req+= "'"+theMacroSt.desc+"'" + ",";
    req+= "'"+theMacroSt.AccesRapide+"'"+ ",";
    req+= "'"+""+"'"+ ",";
    req+= "'"+""+"'"+ ",";
    req+= "'"+"0"+"'"+ ",";
    req+= "'"+"0"+"'"+ ",";
    req+= "'"+"0"+"'"+ ",";
    req+= "'"+"0"+"'"+ ",";
    req+= "'"+"0"+"'";
    req+= "            )";

        this.ExecUpdate(req);

    
    } 
    
  }
   
  public void updateMacroStKeySi(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        MacroSt theMacroSt = (MacroSt)Liste.elementAt(i);
        int id_Origine = theMacroSt.idSi;
        String id_Destination = (String)ListeSi.get(id_Origine);
        if (id_Destination == null)
        {
            String nomSi=getNomById(sourceBd, theMacroSt.idSi, "SI ", "nomSI", "idSI");
            theMacroSt.idSi=getIdByNom(nomSi, "SI ", "nomSI", "idSI");
            ListeSi.put(id_Origine, ""+ theMacroSt.idSi);
        }
        else
        {
          theMacroSt.idSi = Integer.parseInt(id_Destination);
        }
    }     
  } 
  
public Vector readTypeAppli(){
    Vector Liste = new Vector(10);
    req = "SELECT     nomTypeAppli, formeTypeAppli, formedefautTypeAppli, isEquipement, isComposant, isSt, isAppli, isActeur FROM   TypeAppli ORDER BY idTypeAppli";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      TypeAppli theTypeAppli = new TypeAppli(-1);
       theTypeAppli.nom = rs.getString("nomTypeAppli");
       if (theTypeAppli.nom == null) theTypeAppli.nom="";
       
       theTypeAppli.formeTypeAppli = rs.getString("formeTypeAppli");
       if (theTypeAppli.formeTypeAppli == null) theTypeAppli.formeTypeAppli="";
       
       theTypeAppli.formedefautTypeAppli =theTypeAppli.formeTypeAppli;
       
       theTypeAppli.isEquipement = rs.getInt("isEquipement");
       theTypeAppli.isComposant = rs.getInt("isComposant");
       theTypeAppli.isSt = rs.getInt("isSt");
       theTypeAppli.isAppli = rs.getInt("isAppli");
       theTypeAppli.isActeur = rs.getInt("isActeur");

      Liste.addElement(theTypeAppli);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeTypeAppli(Vector Liste){

    req = "delete from St";
    this.ExecUpdate( req);
    
    req = "delete from VersionSt";
    this.ExecUpdate( req);    
      
    for (int i=0; i < Liste.size(); i++)
    {
        TypeAppli theTypeAppli = (TypeAppli)Liste.elementAt(i);
    req = "INSERT INTO [TypeAppli]";
    req+= "           ([nomTypeAppli]";
    req+= "            ,[formeTypeAppli]";
    req+= "            ,[formedefautTypeAppli]";
    req+= "            ,[isEquipement]";
    req+= "            ,[isComposant]";
    req+= "            ,[isSt]";
    req+= "            ,[isAppli]";
    req+= "            ,[isActeur]";
    req+=")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+theTypeAppli.nom+"'" + ",";
    req+= "'"+theTypeAppli.formeTypeAppli+"'" + ",";
    req+= "'"+theTypeAppli.formedefautTypeAppli+"'" + ",";
    req+= "'"+theTypeAppli.isEquipement+"'" + ",";
    req+= "'"+theTypeAppli.isComposant+"'"+ ",";
    req+= "'"+theTypeAppli.isSt+"'"+ ",";
    req+= "'"+theTypeAppli.isAppli+"'"+ ",";
    req+= "'"+theTypeAppli.isActeur+"'";
    req+= "            )";

        this.ExecUpdate(req);

    
    } 
    
  }  
   
public Vector readFamilleOM(){
    Vector Liste = new Vector(10);
    req = "SELECT     nomFamilleOM, descFamilleOM FROM    FamilleOM ORDER BY idFamilleOM";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      Famille theFamille= new Famille();
       theFamille.nom = rs.getString("nomFamilleOM");
       theFamille.description = rs.getString("descFamilleOM");
       
        if (theFamille.description !=  null)
            theFamille.description= theFamille.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");       

      Liste.addElement(theFamille);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeFamilleOM(Vector Liste){
    
    req = "delete from ObjetMetier";
    this.ExecUpdate( req);  
    
    req = "delete from packageOM";
    this.ExecUpdate( req);       
      
    req = "delete from FamilleOM";
    this.ExecUpdate( req);

    for (int i=0; i < Liste.size(); i++)
    {
        Famille theFamille = (Famille)Liste.elementAt(i);
           req = "INSERT FamilleOM (";
             req+="nomFamilleOM"+",";
             req+="descFamilleOM";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+theFamille.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
             req+="'"+theFamille.description+"'";
           req+=")";

        this.ExecUpdate(req);

    
    } 
    
  }    
   
public Vector readpackageOM(){
    Vector Liste = new Vector(10);
    req = "SELECT nomPackage, descPackage, nomFamille FROM  packageOM ORDER BY idPackage";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      Paquetage thePaquetage= new Paquetage();
       thePaquetage.nom = rs.getString("nomPackage");
       thePaquetage.description = rs.getString("descPackage");
       thePaquetage.idFamille = rs.getInt("nomFamille");
   
        if (thePaquetage.nom !=  null)
            thePaquetage.nom= thePaquetage.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");   
        
        if (thePaquetage.description !=  null)
            thePaquetage.description= thePaquetage.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");       

      Liste.addElement(thePaquetage);
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writepackageOM(Vector Liste){
    
    req = "delete from ObjetMetier";
    this.ExecUpdate( req);  
    
    req = "delete from packageOM";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        Paquetage thePaquetage = (Paquetage)Liste.elementAt(i);
           req = "INSERT packageOM (";
             req+="nomPackage"+",";
             req+="descPackage"+",";
             req+="nomFamille";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+thePaquetage.nom+"'";
             req+=",";
             req+="'"+thePaquetage.description+"'";
             req+=",";
             req+=thePaquetage.idFamille;
           req+=")";

        this.ExecUpdate(req);

    
    } 
    
  }
   
  public void updateKeyFamilleOM(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Paquetage thePaquetage = (Paquetage)Liste.elementAt(i);
        int id_Origine = thePaquetage.idFamille;
        String id_Destination = (String)ListeFamilles.get(id_Origine);
        if (id_Destination == null)
        {
            String nomPackage=getNomById(sourceBd, thePaquetage.idFamille, "FamilleOM ", "nomFamilleOM", "idFamilleOM");
            thePaquetage.idFamille=getIdByNom(nomPackage, "FamilleOM ", "nomFamilleOM", "idFamilleOM");
            ListeFamilles.put(id_Origine, ""+ thePaquetage.idFamille);
        }
        else
        {
          thePaquetage.idFamille = Integer.parseInt(id_Destination);
        }
    }      
  }  
  
public Vector readBudgets(){
    Vector Liste = new Vector(10);
    req = "SELECT     Annee, nom, Description FROM   Budgets ORDER BY Annee";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      Budget theBudget= new Budget(rs.getInt("Annee"));
        theBudget.nom= rs.getString("nom");
        theBudget.desc= rs.getString("Description");
       
        if (theBudget.desc !=  null)
            theBudget.desc= theBudget.desc.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");       

      Liste.addElement(theBudget);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeBudgets(Vector Liste){  
      
    req = "delete from Budgets";
    this.ExecUpdate( req);

    for (int i=0; i < Liste.size(); i++)
    {
        Budget theBudget= (Budget)Liste.elementAt(i);
    req = "INSERT Budgets (Annee, nom, Description) ";
    req+=" VALUES (";
    req+="'"+theBudget.Annee+"'";
    req+=",";    
    req+="'"+theBudget.nom+"'";
    req+=",";
    req+="'"+theBudget.desc+"'";
    req+=")";

        this.ExecUpdate(req);

    
    } 
   }
   
public Vector readConstructeur(){
    Vector Liste = new Vector(10);
    req = "SELECT     nomConstructeur FROM         Constructeur ORDER BY idConstructeur";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      item theitem= new item();
        theitem.nom= rs.getString("nomConstructeur");
 
        Liste.addElement(theitem);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeConstructeur(Vector Liste){  
       
    req = "delete from VersionMW";
    this.ExecUpdate( req);
    
    req = "delete from Middleware";
    this.ExecUpdate( req);
    
    req = "delete from Logiciel";
    this.ExecUpdate( req); 
    
    req = "delete from Constructeur";
    this.ExecUpdate( req);    

    for (int i=0; i < Liste.size(); i++)
    {
        item theitem= (item)Liste.elementAt(i);
    req = "INSERT Constructeur (nomConstructeur) ";
    req+=" VALUES (";
    req+="'"+theitem.nom+"'";
    req+=")";

        this.ExecUpdate(req);

    } 
   }   
   
public Vector readLogiciel(){
    Vector Liste = new Vector(10);
    req = "SELECT    nomLogiciel, descLogiciel, visible FROM   Logiciel ORDER BY idLogiciel";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      TypeLogiciel theTypeLogiciel= new TypeLogiciel(-1);
         theTypeLogiciel.nom = rs.getString("nomLogiciel");
         theTypeLogiciel.desc = rs.getString("descLogiciel");
       
        if (theTypeLogiciel.desc !=  null)
            theTypeLogiciel.desc= theTypeLogiciel.desc.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");       
        theTypeLogiciel.visible = 1;
      Liste.addElement(theTypeLogiciel);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeLogiciel(Vector Liste){  
      
    req = "delete from VersionMW";
    this.ExecUpdate( req);
    
    req = "delete from Middleware";
    this.ExecUpdate( req);
    
    req = "delete from Logiciel";
    this.ExecUpdate( req);    

    for (int i=0; i < Liste.size(); i++)
    {
        TypeLogiciel theTypeLogiciel= (TypeLogiciel)Liste.elementAt(i);
    req = "INSERT Logiciel (nomLogiciel, descLogiciel, visible) ";
    req+=" VALUES (";
    req+="'"+theTypeLogiciel.nom+"'";
    req+=",";
    req+="'"+theTypeLogiciel.desc+"'";
    req+=",";
    req+="'"+theTypeLogiciel.visible+"'";    
    req+=")";

        this.ExecUpdate(req);

    
    } 
   }   
   
public Vector readMiddleware(){
    Vector Liste = new Vector(10);
    req = "SELECT  LogicielMiddleWare, nomMiddleware, constructeurMiddleware, descMiddleWare, ratioPf FROM Middleware ORDER BY idMiddleware";;
    this.ExecReq( req);
    try {
       while (rs.next()) {
      Logiciel theLogiciel= new Logiciel();
         theLogiciel.LogicielMiddleWare = rs.getInt("LogicielMiddleWare");
         theLogiciel.nom = rs.getString("nomMiddleware");
         theLogiciel.idConstructeur = rs.getInt("constructeurMiddleware");
         
         Liste.addElement(theLogiciel);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeMiddleware(Vector Liste){  
      
    req = "delete from VersionMW";
    this.ExecUpdate( req);
    
    req = "delete from Middleware";
    this.ExecUpdate( req);
      

    for (int i=0; i < Liste.size(); i++)
    {
        Logiciel theLogiciel= (Logiciel)Liste.elementAt(i);
    req = "INSERT Middleware (nomMiddleware, LogicielMiddleWare, constructeurMiddleware) ";
    req+=" VALUES (";
    req+="'"+theLogiciel.nom+"'";
    req+=",";
    req+="'"+theLogiciel.LogicielMiddleWare+"'";
    req+=",";
    req+="'"+theLogiciel.idConstructeur+"'";    
    req+=")";

        this.ExecUpdate(req);

    
    } 
   } 
   
  public void updateKeyLogiciel(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Logiciel theLogiciel= (Logiciel)Liste.elementAt(i);
        int id_Origine = theLogiciel.LogicielMiddleWare;
        String id_Destination = (String)ListeLogiciels.get(id_Origine);
        if (id_Destination == null)
        {
            String nomLogiciel=getNomById(sourceBd, theLogiciel.LogicielMiddleWare, "Logiciel ", "nomLogiciel", "idLogiciel");
            theLogiciel.LogicielMiddleWare=getIdByNom(nomLogiciel, "Logiciel ", "nomLogiciel", "idLogiciel");
            ListeLogiciels.put(id_Origine, ""+ theLogiciel.LogicielMiddleWare);
        }
        else
        {
          theLogiciel.LogicielMiddleWare = Integer.parseInt(id_Destination);
        }
    }       
  } 
  
  public void updateKeyConstructeur(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Logiciel theLogiciel= (Logiciel)Liste.elementAt(i);
        int id_Origine = theLogiciel.idConstructeur;
        String id_Destination = (String)ListeConstructeurs.get(id_Origine);
        if (id_Destination == null)
        {
            String nomConstructeur =getNomById(sourceBd, theLogiciel.idConstructeur, "Constructeur ", "nomConstructeur", "idConstructeur");
            theLogiciel.idConstructeur=getIdByNom(nomConstructeur, "Constructeur ", "nomConstructeur", "idConstructeur");
            ListeConstructeurs.put(id_Origine, ""+ theLogiciel.idConstructeur);
        }
        else
        {
          theLogiciel.idConstructeur = Integer.parseInt(id_Destination);
        }
    }    
  }
  
public Vector readVersionMW(){
    Vector Liste = new Vector(10);
    req = "SELECT    nomVersionMW, mwVersionMW FROM  VersionMW ORDER BY idVersionMW";
    this.ExecReq( req);
    try {
       while (rs.next()) {
      versionTechno theversionTechno= new versionTechno();
         theversionTechno.nom = rs.getString("nomVersionMW");
         theversionTechno.idMiddleware = rs.getInt("mwVersionMW");
 
        Liste.addElement(theversionTechno);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeVersionMW(Vector Liste){  
       
    req = "delete from VersionMW";
    this.ExecUpdate( req);
     

    for (int i=0; i < Liste.size(); i++)
    {
        versionTechno theversionTechno= (versionTechno)Liste.elementAt(i);
    req = "INSERT VersionMW ( nomVersionMW, mwVersionMW) ";
    req+=" VALUES (";
    req+="'"+theversionTechno.nom+"'";
    req+=",";
    req+="'"+theversionTechno.idMiddleware+"'";
    req+=")";

        this.ExecUpdate(req);

    } 
   }
   
  public void updateKeyMiddleware(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        versionTechno theversionTechno= (versionTechno)Liste.elementAt(i);
        int id_Origine = theversionTechno.idMiddleware;
        String id_Destination = (String)ListeMiddleware.get(id_Origine);
        if (id_Destination == null)
        {
            String nomMiddleware =getNomById(sourceBd, theversionTechno.idMiddleware, "Middleware ", "nomMiddleware", "idMiddleware");
            theversionTechno.idMiddleware=getIdByNom(nomMiddleware, "Middleware ", "nomMiddleware", "idMiddleware");
            ListeMiddleware.put(id_Origine, ""+ theversionTechno.idMiddleware);
        }
        else
        {
          theversionTechno.idMiddleware = Integer.parseInt(id_Destination);
        }
    }  
    
  } 
  
public Vector readCollaborateurCompetence(){
    Vector Liste = new Vector(10);
    req = "SELECT     idCollaborateur, idCompetence, note FROM  CollaborateurCompetence WHERE (note <> - 1) ORDER BY id";
    this.ExecReq( req);
    try {
       while (rs.next()) {
        Competence theCompetence= new Competence();
        theCompetence.theCollaborateur = new Collaborateur(rs.getInt("idCollaborateur"));
        theCompetence.theLogiciel = new Logiciel();
        theCompetence.theLogiciel.id = rs.getInt("idCompetence");
        theCompetence.note = rs.getInt("note");
 
        Liste.addElement(theCompetence);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeCollaborateurCompetence(Vector Liste){  
       
    req = "delete from CollaborateurCompetence";
    this.ExecUpdate( req);
     

    for (int i=0; i < Liste.size(); i++)
    {
        Competence theCompetence= (Competence)Liste.elementAt(i);
    req = "INSERT CollaborateurCompetence (idCollaborateur, idCompetence, note) ";
    req+=" VALUES (";
    req+="'"+ theCompetence.theCollaborateur.id+"'";
    req+=",";
    req+="'"+ theCompetence.theLogiciel.id+"'";
    req+=",";    
    req+="'"+theCompetence.note+"'";
    req+=")";

        this.ExecUpdate(req);

    } 
   } 
   
  public void updateKeyMiddlewareForCompetence(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Competence theCompetence= (Competence)Liste.elementAt(i);
        int id_Origine =theCompetence.theLogiciel.id;
        String id_Destination = (String)ListeMiddleware.get(id_Origine);
        if (id_Destination == null)
        {
            String nomMiddleware =getNomById(sourceBd, theCompetence.theLogiciel.id, "Middleware ", "nomMiddleware", "idMiddleware");
            theCompetence.theLogiciel.id=getIdByNom(nomMiddleware, "Middleware ", "nomMiddleware", "idMiddleware");
            ListeMiddleware.put(id_Origine, ""+ theCompetence.theLogiciel.id);
        }
        else
        {
          theCompetence.theLogiciel.id = Integer.parseInt(id_Destination);
        }
    }  
    
  }    
  
  public void updateKeyMembreForCompetence(db sourceBd,Vector Liste){
    for (int i=0; i < Liste.size(); i++)
    {
       Competence theCompetence= (Competence)Liste.elementAt(i);
        String nomMembre=getNomById(sourceBd, theCompetence.theCollaborateur.id, "Membre ", "LoginMembre", "idMembre");
        theCompetence.theCollaborateur.id=getIdByNom(nomMembre, "Membre ", "LoginMembre", "idMembre");
    }
  }    
  
  // ------------------------------------------- Fin Donnees Base Produit----------------------------//
  
  // ------------------------------------------ Gestion des Produits --------------------------------//
public Vector readSt(){
    Vector Liste = new Vector(10);
    req = "SELECT nomSt, typeAppliSt, Criticite, isST, isAppli, isRecurrent, isMeeting, Logo, creerProjet, isEquipement, isComposant, isActeur FROM    St ORDER BY idSt";
    
    this.ExecReq( req);
    try {
       while (rs.next()) {
        ST theSt= new ST(-1, "idVersionSt");
        theSt.nomSt = rs.getString("nomSt");
        theSt.typeAppliSt = rs.getInt("typeAppliSt");
        theSt.Criticite = rs.getString("Criticite");
        theSt.isST = rs.getInt("isST");
        theSt.isAppli = rs.getInt("isAppli");
        theSt.isRecurrent = rs.getInt("isRecurrent");
        theSt.isMeeting = rs.getInt("isMeeting");
        theSt.logo = rs.getString("logo");
        theSt.creerProjet = rs.getInt("creerProjet");
        theSt.isEquipement = rs.getInt("isEquipement");
        theSt.isComposant = rs.getInt("isComposant");
        theSt.isActeur = rs.getInt("isActeur");
 
        Liste.addElement(theSt);
      //theDirection.dump();
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeSt(Vector Liste){  
       
    req = "delete from VersionSt";
    req = "delete from St";
    
    this.ExecUpdate( req);
     

    for (int i=0; i < Liste.size(); i++)
    {
        ST theSt= (ST)Liste.elementAt(i);
    req = "INSERT St (nomSt, typeAppliSt, Criticite, isST, isAppli, isRecurrent, isMeeting, Logo, creerProjet, isEquipement, isComposant, isActeur) ";
    req+=" VALUES (";
    req+="'"+ theSt.nomSt+"'";
    req+=",";
    req+="'"+ theSt.typeAppliSt+"'";
    req+=",";    
    req+="'"+ theSt.Criticite+"'";
    req+=",";    
    req+="'"+ theSt.isST+"'";
    req+=",";    
    req+="'"+ theSt.isAppli+"'";
    req+=",";    
    req+="'"+ theSt.isRecurrent+"'";
    req+=",";    
    req+="'"+ theSt.isMeeting+"'";
    req+=",";    
    req+="'"+ theSt.logo+"'";
    req+=",";    
    req+="'"+ theSt.creerProjet+"'";
    req+=",";    
    req+="'"+ theSt.isEquipement+"'";
    req+=","; 
    req+="'"+ theSt.isComposant+"'";
    req+=",";       
    req+="'"+theSt.isActeur+"'";
    req+=")";

        this.ExecUpdate(req);

    } 
   }  
   
  public void updateKeyTypeAppliSt(db sourceBd,Vector Liste){
    
   for (int i=0; i < Liste.size(); i++)
    {
        ST theSt= (ST)Liste.elementAt(i);
        int id_Origine = theSt.typeAppliSt;
        String id_Destination = (String)ListeTypeAppli.get(id_Origine);
        if (id_Destination == null)
        {
            String nomTypeAppli=getNomById(sourceBd, theSt.typeAppliSt, "TypeAppli ", "nomTypeAppli", "idTypeAppli");
            theSt.typeAppliSt=getIdByNom(nomTypeAppli, "TypeAppli ", "nomTypeAppli", "idTypeAppli");
            ListeTypeAppli.put(id_Origine, ""+ theSt.typeAppliSt);
        }
        else
        {
          theSt.typeAppliSt = Integer.parseInt(id_Destination);
        }
    }     
  }  
  
public Vector readVersionSt(){
    Vector Liste = new Vector(10);
    req = "SELECT ";
    req+="idSt"+",";
    req+="nomSt"+",";
    req+="trigrammeSt"+",";
    req+="chronoSt"+",";
    req+="typeAppliSt"+",";
    req+="Criticite"+",";
    req+="ClefImport"+",";
    req+="diagramme"+",";
    req+="isST"+",";
    req+="isAppli"+",";
    req+="idVersionSt"+",";
    req+="stVersionSt"+",";
    req+="numVersionSt"+",";
    req+="etatFicheVersionSt"+",";
    req+="siVersionSt"+",";
    req+="typeSiVersionSt"+",";
    req+="versionRefVersionSt"+",";
    req+="creationVersionSt"+",";
    req+="derMajVersionSt"+",";
    req+="descVersionSt"+",";
    req+="deltaVersionSt"+",";
    req+="macrostVersionSt"+",";
    req+="phaseNPVersionSt"+",";
    req+="sousPhaseNPVersionSt"+",";
    req+="typeArchiVersionSt"+",";
    req+="osVersionSt"+",";
    req+="bdVersionSt"+",";
    req+="hebergVersionSt"+",";
    req+="gestionUtilVersionSt"+",";
    req+="nbLigCodeVersionSt"+",";
    req+="nbPtsFctVersionSt"+",";
    req+="iReagirVersionSt"+",";
    req+="serviceMoeVersionSt"+",";
    req+="respMoeVersionSt"+",";
    req+="serviceMoaVersionSt"+",";
    req+="serviceExploitVersionSt"+",";
    req+="mepVersionSt"+",";
    req+="etatVersionSt"+",";
    req+="coutFabrVersionSt"+",";
    req+="crenUtilVersionSt"+",";
    req+="externalVersionSt"+",";
    req+="lienManuelVersionSt"+",";
    req+="derVersionSt"+",";
    req+="auteurVersionSt"+",";
    req+="precVersionSt"+",";
    req+="suivVersionSt"+",";
    req+="nbUtilVersionSt"+",";
    req+="respMoaVersionSt"+",";
    req+="killVersionSt"+",";
    req+="okMoaVersionSt"+",";
    req+="okMoeVersionSt"+",";
    req+="STRokMoaVersionSt"+",";
    req+="mep1VersionSt"+",";
    req+="createurVersionSt"+",";
    req+="modificateurVersionSt"+",";
    req+="roadmap"+",";
    req+="Mobilite"+",";
    req+="Externalisation"+",";
    req+="dateEB"+",";
    req+="ContactOper"+",";
    req+="ModeAcq"+",";
    req+="nomTypeAppli"+",";
    req+="nomSi"+",";
    req+="nomMOA"+",";
    req+="prenomMOA"+",";
    req+="nomMOE"+",";
    req+="prenomMOE"+",";
    req+="nomTypeSi"+",";
    req+="nomMacrost"+",";
    req+="nomPhaseNP"+",";
    req+="nomSousPhaseNP"+",";
    req+="nomFrequence"+",";
    req+="icone"+",";
    req+="nomServiceMOE"+",";
    req+="nomDirectionMOE"+",";
    req+="nomServiceMOA"+",";
    req+="nomDirectionMOA"+",";
    req+="nomServicePROD"+",";
    req+="nomDirectionPROD"+",";
    req+="nomEtat"+",";
    req+="ContactMOE"+",";
    req+="isRecurrent"+",";
    req+="isMeeting"+",";
    req+="idMaturite"+",";
    req+="nomMaturite"+",";
    req+="idDomaine"+",";
    req+="nomDomaine"+",";
    req+="MOEProd1"+",";
    req+="[Backup]"+",";
    req+="ControleAcces" +",";
    req+="Complexite" +",";
    req+="Logo" +",";
    req+="idTypeClient" +",";
    req+="nomTypeClient" +",";
    req+="serviceMetier" +",";
    req+="nomServiceMetier" +",";
    req+="niveauCharge" +",";
    req+="creerProjet";
    req+=",";
    req+="isEquipement";
    req+=",";
    req+="isComposant";
    req+=",";
    req+="isActeur";
    req+=",";
    req+="nomEtatFiche";    

    req+=" FROM ST_ListeSt_All_Attribute";
    req += " where nomSt is not null  ORDER BY idVersionSt";
    
    this.ExecReq( req);
    try {
       while (rs.next()) {
        ST theSt= new ST(-1, "idVersionSt");
        theSt.idSt= rs.getInt("idSt");
        theSt.nomSt= rs.getString("nomSt");
        theSt.trigrammeSt= rs.getString("trigrammeSt");;
        theSt.chronoSt=rs.getInt("chronoSt");
        theSt.typeAppliSt=rs.getInt("typeAppliSt");
        theSt.Criticite= rs.getString("Criticite");
        theSt.ClefImport= rs.getString("ClefImport");;
        theSt.diagramme= rs.getString("diagramme");;


        theSt.isST=rs.getInt("isST");
        theSt.isAppli=rs.getInt("isAppli");
        if ((theSt.isAppli==1) && (theSt.isST==1))
        {
          theSt.typeSpinoza = "Application & ST (cf Onglet OM)";
        }
        else if ((theSt.isST==1) && (theSt.isAppli==0))
        {
          theSt.typeSpinoza = "ST (cf Onglet OM)";
        }
        else if ((theSt.isST==0) &&(theSt.isAppli==1))
        {
          theSt.typeSpinoza = "Application (cf Onglet OM)";
        }
        else
        {
          theSt.typeSpinoza = "ni Application ni ST (cf Onglet OM)";
    }

        theSt.idVersionSt=rs.getInt("idVersionSt");
        theSt.stVersionSt=rs.getInt("stVersionSt");
        theSt.numVersionSt= rs.getInt("numVersionSt");
        theSt.etatFicheVersionSt=rs.getInt("etatFicheVersionSt");
        theSt.siVersionSt=rs.getInt("siVersionSt");
        theSt.typeSiVersionSt=rs.getInt("typeSiVersionSt");
        theSt.versionRefVersionSt= rs.getString("versionRefVersionSt");if (theSt.versionRefVersionSt==null) theSt.versionRefVersionSt="";
        theSt.creationVersionSt= rs.getDate("creationVersionSt");
        theSt.derMajVersionSt= rs.getDate("derMajVersionSt");
        theSt.descVersionSt= rs.getString("descVersionSt");;
        theSt.deltaVersionSt= rs.getString("deltaVersionSt");;
        theSt.macrostVersionSt=rs.getInt("macrostVersionSt");
        theSt.phaseNPVersionSt=rs.getInt("phaseNPVersionSt");
        theSt.sousPhaseNPVersionSt=rs.getInt("sousPhaseNPVersionSt");
        theSt.typeArchiVersionSt=rs.getInt("typeArchiVersionSt");
        theSt.osVersionSt=rs.getInt("osVersionSt");
        theSt.bdVersionSt=rs.getInt("bdVersionSt");
        theSt.hebergVersionSt=rs.getInt("hebergVersionSt");
        theSt.gestionUtilVersionSt=rs.getInt("gestionUtilVersionSt");
        theSt.nbLigCodeVersionSt=rs.getInt("nbLigCodeVersionSt");
        theSt.nbPtsFctVersionSt=rs.getInt("nbPtsFctVersionSt");

        theSt.iReagirVersionSt=rs.getInt("iReagirVersionSt");
        if (theSt.iReagirVersionSt==1) theSt.str_iReagirVersionSt="Oui"; else theSt.str_iReagirVersionSt="Non";

        theSt.serviceMoeVersionSt=rs.getInt("serviceMoeVersionSt");
        theSt.respMoeVersionSt=rs.getInt("respMoeVersionSt");
        theSt.serviceMoaVersionSt=rs.getInt("serviceMoaVersionSt");
        theSt.serviceExploitVersionSt=rs.getInt("serviceExploitVersionSt");

        theSt.mepVersionSt= rs.getDate("mepVersionSt");
        if (theSt.mepVersionSt != null)
            theSt.dateMep ="convert(datetime, '"+theSt.mepVersionSt.getDate()+"/"+(theSt.mepVersionSt.getMonth()+1)+"/"+(theSt.mepVersionSt.getYear() +1900)+"', 103)";
        else
            theSt.dateMep="null";        

        theSt.etatVersionSt=rs.getInt("etatVersionSt");
        theSt.coutFabrVersionSt=rs.getInt("coutFabrVersionSt");
        theSt.crenUtilVersionSt=rs.getInt("crenUtilVersionSt");
        theSt.externalVersionSt=rs.getBoolean("externalVersionSt");

        theSt.lienManuelVersionSt= rs.getString("lienManuelVersionSt");;
        if (theSt.lienManuelVersionSt != null)
        {
          theSt.lienManuelVersionSt = theSt.lienManuelVersionSt.replaceAll(
              "\\\\\\\\", "\\\\");
          int index = theSt.lienManuelVersionSt.lastIndexOf("\\");
          theSt.titreLienManuelVersionSt = theSt.lienManuelVersionSt.substring(
              index + 1);
        }
        theSt.derVersionSt=rs.getBoolean("derVersionSt");
        theSt.auteurVersionSt=rs.getInt("auteurVersionSt");
        theSt.precVersionSt=rs.getInt("precVersionSt");
        theSt.suivVersionSt=rs.getInt("suivVersionSt");
        theSt.nbUtilVersionSt=rs.getInt("nbUtilVersionSt");
        theSt.respMoaVersionSt=rs.getInt("respMoaVersionSt");

        theSt.killVersionSt= rs.getDate("killVersionSt");
        if (theSt.killVersionSt != null)
            theSt.dateKill ="convert(datetime, '"+theSt.killVersionSt.getDate()+"/"+(theSt.killVersionSt.getMonth()+1)+"/"+(theSt.killVersionSt.getYear() +1900)+"', 103)";
        else
            theSt.dateKill="null";

        theSt.okMoaVersionSt=rs.getInt("okMoaVersionSt");
        theSt.okMoeVersionSt=rs.getInt("okMoeVersionSt");
        theSt.STRokMoaVersionSt= rs.getString("STRokMoaVersionSt");;

        theSt.mep1VersionSt= rs.getDate("mep1VersionSt");
        if (theSt.mep1VersionSt != null)
            theSt.dateMep1 ="convert(datetime, '"+theSt.mep1VersionSt.getDate()+"/"+(theSt.mep1VersionSt.getMonth()+1)+"/"+(theSt.mep1VersionSt.getYear() +1900)+"', 103)";
        else
            theSt.dateMep1="null";        

        theSt.createurVersionSt= rs.getString("createurVersionSt");;
        theSt.modificateurVersionSt= rs.getString("modificateurVersionSt");;
        theSt.roadmap= rs.getString("roadmap");;

        theSt.Mobilite= rs.getString("Mobilite");if ((theSt.Mobilite==null) || (theSt.Mobilite.equals("-1"))) theSt.Mobilite="";
        if (theSt.Mobilite.equals(""))
                {
                 theSt.displayMobilitePda = "none";
                 theSt.displayMobiliteImode = "none";
                }
                else
                {
                        if ((theSt.Mobilite .toLowerCase().equals("pda")) ) theSt.displayMobilitePda= ""; else theSt.displayMobilitePda = "none";
                        if ((theSt.Mobilite .toLowerCase().equals("i-mode"))) theSt.displayMobiliteImode= ""; else theSt.displayMobiliteImode = "none";
        }

        theSt.Externalisation= rs.getString("Externalisation");if ((theSt.Externalisation==null) || (theSt.Externalisation.equals("-1"))) theSt.Externalisation="";
        if (theSt.Externalisation.equals("")) theSt.displayExternalisation = "none";
          else
                {
                  if (theSt.Externalisation.equals("CITRIX")) theSt.displayExternalisation= ""; else theSt.displayExternalisation = "none";
                }

        theSt.dateEB= rs.getDate("dateEB");
        theSt.ContactOper= rs.getString("ContactOper");if (theSt.ContactOper==null) theSt.ContactOper="";
        theSt.ModeAcq= rs.getString("ModeAcq");if (theSt.ModeAcq==null) theSt.ModeAcq="";

        theSt.TypeApplication= rs.getString("nomTypeAppli");
        if ((theSt.TypeApplication != null) && (theSt.TypeApplication.equals("Acteur")))
                {
                  theSt.DisplayActeur="none";
                }
                else
                {
                  theSt.DisplayActeur="";
        }

        theSt.nomSi= rs.getString("nomSI");
        if ((theSt.nomSi != null) && (theSt.nomSi.endsWith("SIR"))) theSt.display_SIR="";else theSt.display_SIR="none";

        theSt.nomMOA= rs.getString("nomMOA"); if (theSt.nomMOA==null) theSt.nomMOA="";
        theSt.prenomMOA= rs.getString("prenomMOA"); if (theSt.prenomMOA==null) theSt.prenomMOA="";
        theSt.nomMOE= rs.getString("nomMOE");
        theSt.prenomMOE= rs.getString("prenomMOE");
        theSt.TypeSi= rs.getString("nomTypeSi");
        theSt.nomMacrost= rs.getString("nomMacrost");if (theSt.nomMacrost==null) theSt.nomMacrost="";
        theSt.nomPhaseNP= rs.getString("nomPhaseNP");if (theSt.nomPhaseNP==null) theSt.nomPhaseNP="";
        theSt.nomSousPhaseNP= rs.getString("nomSousPhaseNP");if (theSt.nomSousPhaseNP==null) theSt.nomSousPhaseNP="";
        theSt.nomFrequence= rs.getString("nomFrequence");if (theSt.nomFrequence==null) theSt.nomFrequence="";
        theSt.icone= rs.getString("icone");
        if (theSt.icone != null) 
        {
            theSt.icone = theSt.icone.replaceAll(" ", "");
            if (theSt.icone.indexOf("null") > -1)
                theSt.icone="images/Logos/iconeInconnu.gif";
        }
        if ((theSt.icone == null) || (theSt.icone.equals("")))
            theSt.icone="images/Logos/iconeInconnu.gif";
        theSt.nomServiceMOE= rs.getString("nomServiceMOE");
        theSt.nomDirectionMOE= rs.getString("nomDirectionMOE");
        theSt.nomServiceMOA= rs.getString("nomServiceMOA");
        theSt.nomDirectionMOA= rs.getString("nomDirectionMOA");   if (theSt.nomDirectionMOA==null) theSt.nomDirectionMOA="";
        theSt.nomServicePROD= rs.getString("nomServicePROD");if (theSt.nomServicePROD==null) theSt.nomServicePROD="";
        theSt.nomDirectionPROD= rs.getString("nomDirectionPROD");   if (theSt.nomDirectionPROD==null) theSt.nomDirectionPROD="";
        theSt.nomEtat= rs.getString("nomEtat");
        theSt.ContactMOE= rs.getString("ContactMOE");if (theSt.ContactMOE==null) theSt.ContactMOE="";
        theSt.isRecurrent=rs.getInt("isRecurrent");
        theSt.isMeeting=rs.getInt("isMeeting");

        theSt.idMaturite=rs.getInt("idMaturite");
        theSt.nomMaturite=rs.getString("nomMaturite");
        theSt.idDomaine=rs.getInt("idDomaine");
        theSt.nomDomaine=rs.getString("nomDomaine");

        theSt.MOEProd1=rs.getString("MOEProd1");if ((theSt.MOEProd1 == null) || (theSt.MOEProd1.equals("null"))) theSt.MOEProd1="";
        theSt.Backup=rs.getString("Backup");if (theSt.Backup == null) theSt.Backup="";
        theSt.ControleAcces=rs.getString("ControleAcces");if (theSt.ControleAcces == null) theSt.ControleAcces="";
        theSt.Complexite=rs.getInt("Complexite");
        theSt.logo=rs.getString("logo");
        if (theSt.logo != null) theSt.logo = theSt.logo.replaceAll(" ", "");
        if ((theSt.logo == null) || (theSt.logo.equals("")))
            theSt.logo="images/Logos/iconeInconnu.gif";
        theSt.idTypeClient=rs.getInt("idTypeClient");
        theSt.nomTypeClient=rs.getString("nomTypeClient");

        theSt.serviceMetier=rs.getInt("serviceMetier");
        theSt.nomServiceMetier=rs.getString("nomServiceMetier");

        if (theSt.siVersionSt == 14)
          theSt.niveauCharge =  rs.getString("niveauCharge");
        else
          theSt.niveauCharge = "";

        theSt.creerProjet=rs.getInt("creerProjet");
        theSt.isEquipement=rs.getInt("isEquipement");
        theSt.isComposant=rs.getInt("isComposant");
        theSt.isActeur=rs.getInt("isActeur");
        
        theSt.nomEtatFiche =  rs.getString("nomEtatFiche");
 
        //if (theSt.idSt == 113)
        //if (Liste.size() < 500)
        //if (theSt.nomSt.equals("OcreS")) 
            Liste.addElement(theSt);
      //theDirection.dump();
       }
      } catch (SQLException s) {
          s.getMessage(); 
      }    
    
    return Liste;
  }
 
   public void writeVersionSt(Vector Liste){  
       
    req = "delete from VersionSt";
    
    this.ExecUpdate( req);
     

    for (int i=0; i < Liste.size(); i++)
    {
        ST theSt= (ST)Liste.elementAt(i);
        
            if (theSt.descVersionSt == null)
              theSt.descVersionSt = "";
            else
            {
              theSt.descVersionSt=Utils.removeBR(theSt.descVersionSt);
              theSt.descVersionSt=theSt.descVersionSt.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");      
            }
            
            if (theSt.ModeAcq == null)
              theSt.ModeAcq = "";
            else
            {
              theSt.ModeAcq=theSt.ModeAcq.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");      
            } 
            
            if (theSt.ControleAcces == null)
              theSt.ControleAcces = "";
            else
            {
              theSt.ControleAcces=theSt.ControleAcces.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");      
            }  
        String str_creation="";
        if (theSt.creationVersionSt != null)
            str_creation ="convert(datetime, '"+theSt.creationVersionSt.getDate()+"/"+(theSt.creationVersionSt.getMonth()+1)+"/"+(theSt.creationVersionSt.getYear() +1900)+"', 103)";
        else
            str_creation="null";        
        
        String str_kill="";
        if (theSt.killVersionSt != null)
            str_kill ="convert(datetime, '"+theSt.killVersionSt.getDate()+"/"+(theSt.killVersionSt.getMonth()+1)+"/"+(theSt.killVersionSt.getYear() +1900)+"', 103)";
        else
            str_kill="null";          
        
        
          req = "INSERT VersionSt (";
            req+="stVersionSt"+",";
            req+="numVersionSt"+",";
            req+="etatFicheVersionSt"+",";
            req+="siVersionSt"+",";
            req+="typeSiVersionSt"+",";
            req+="versionRefVersionSt"+",";
            req+="creationVersionSt"+",";
            //req+="derMajVersionSt"+",";
            req+="descVersionSt"+",";
            req+="deltaVersionSt"+",";
            req+="macrostVersionSt"+",";
            req+="phaseNPVersionSt"+",";
            req+="sousPhaseNPVersionSt"+",";
            req+="typeArchiVersionSt"+",";
            req+="osVersionSt"+",";
            req+="bdVersionSt"+",";
            req+="hebergVersionSt"+",";
            req+="gestionUtilVersionSt"+",";
            req+="nbLigCodeVersionSt"+",";
            req+="nbPtsFctVersionSt"+",";
            req+="iReagirVersionSt"+",";
            req+="serviceMoeVersionSt"+",";
            req+="respMoeVersionSt"+",";
            req+="serviceMoaVersionSt"+",";
            req+="serviceExploitVersionSt"+",";
            req+="mepVersionSt"+",";
            req+="etatVersionSt"+",";
            req+="coutFabrVersionSt"+",";
            req+="crenUtilVersionSt"+",";
            req+="externalVersionSt"+",";
            req+="lienManuelVersionSt"+",";
            req+="derVersionSt"+",";
            req+="auteurVersionSt"+",";
            req+="precVersionSt"+",";
            req+="suivVersionSt"+",";
            req+="nbUtilVersionSt"+",";
            req+="respMoaVersionSt"+",";
            req+="killVersionSt"+",";
            req+="okMoaVersionSt"+",";
            req+="okMoeVersionSt"+",";
            req+="STRokMoaVersionSt"+",";
            req+="mep1VersionSt"+",";
            req+="createurVersionSt"+",";
            //req+="modificateurVersionSt"+",";
            req+="roadmap"+",";
            req+="Mobilite"+",";
            req+="Externalisation"+",";
            req+="dateEB"+",";
            req+="ContactOper"+",";
            req+="ModeAcq"+",";
            req+="ContactMOE"+",";
            req+="idDomaine"+",";
            req+="idMaturite"+",";
            req+="MOEProd1"+",";
            req+="[Backup]"+",";
            req+="ControleAcces"+",";
            req+="Complexite"+",";
            req+="idTypeClient"+",";
            req+="serviceMetier"+"";
          req+=")";
          req+=" VALUES ";
          req+="(";
            req+="'"+theSt.idSt+"',";
            req+="'"+theSt.numVersionSt+"',";
            req+="'"+theSt.etatFicheVersionSt+"',";
            req+="'"+theSt.siVersionSt+"',";
            req+="'"+theSt.typeSiVersionSt+"',";

            if ((theSt.versionRefVersionSt == null) || (theSt.versionRefVersionSt.equals("")))
              req+="'"+"v0"+"',";
            else
            {
              if (!theSt.versionRefVersionSt.substring(0,1).toLowerCase().equals("v"))
                theSt.versionRefVersionSt = "v" + theSt.versionRefVersionSt.substring(0);
              else
                theSt.versionRefVersionSt = "v" + theSt.versionRefVersionSt.substring(1);
              req += "'" + theSt.versionRefVersionSt + "',";
            }
            req+=""+str_creation+",";
            //req+=""+theSt.str_derMajVersionSt+",";
            if (theSt.descVersionSt == null)
              req+="null,";
            else
              req+="'"+theSt.descVersionSt.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";         

            req+="null,";

            if (theSt.macrostVersionSt <= 0)
              req+="null,";
            else
              req+="'"+theSt.macrostVersionSt+"',";

            if (true)
              req+="null,";
            else
              req+="'"+theSt.phaseNPVersionSt+"',";

            if (true)
              req+="null,";
            else
              req+="'"+theSt.sousPhaseNPVersionSt+"',";

            req+="'"+theSt.typeArchiVersionSt+"',";
            req+="'"+theSt.osVersionSt+"',";
            req+="'"+theSt.bdVersionSt+"',";
            req+="'"+theSt.hebergVersionSt+"',";
            req+="'"+theSt.gestionUtilVersionSt+"',";
            req+="'"+theSt.nbLigCodeVersionSt+"',";
            req+="'"+theSt.nbPtsFctVersionSt+"',";
            req+="'"+theSt.iReagirVersionSt+"',";

            if (theSt.serviceMoeVersionSt<= 0)
              req+="null,";
            else
              req+="'"+theSt.serviceMoeVersionSt+"',";

            if (theSt.respMoeVersionSt <= 0)
              req+="null,";
            else
              req+="'"+theSt.respMoeVersionSt+"',";


            if (theSt.serviceMoaVersionSt <= 0)
              req+="null,";
            else
              req+="'"+theSt.serviceMoaVersionSt+"',";

            req+="'"+theSt.serviceExploitVersionSt+"',";
            req+=""+theSt.dateMep+",";

            if (theSt.etatVersionSt <= 0)
              req+="3,";
            else
              req+="'"+theSt.etatVersionSt+"',";

            req+="'"+theSt.coutFabrVersionSt+"',";
            req+="'"+theSt.crenUtilVersionSt+"',";
            req+="'"+theSt.externalVersionSt+"',";
            req+="null,";
            //req+="'"+theSt.lienManuelVersionSt.replaceAll("\\\\","\\\\\\\\")+"',";
            req+="'"+theSt.derVersionSt+"',";
            req+="'"+theSt.auteurVersionSt+"',";
            req+="'"+theSt.precVersionSt+"',";
            req+="'"+theSt.suivVersionSt+"',";
            req+="'"+theSt.nbUtilVersionSt+"',";
            req+="'"+theSt.respMoaVersionSt+"',";
            req+=""+str_kill+",";

            req+="'"+theSt.okMoaVersionSt+"',";
            req+="'"+theSt.okMoeVersionSt+"',";
            req+="'"+theSt.STRokMoaVersionSt+"',";
            req+=""+theSt.dateMep1+",";
            req+="'"+theSt.createurVersionSt+"',";
            //req+="'"+theSt.modificateurVersionSt+"',";
            //req+="'"+theSt.roadmap+"',";
            req+="null,";
            req+="'"+theSt.Mobilite+"',";
            req+="'"+theSt.Externalisation+"',";
            req+=""+theSt.str_dateEB+",";
            req+="'"+theSt.ContactOper+"',";
            req+="'"+theSt.ModeAcq+"',";
            req+="'"+theSt.ContactMOE+"',";
            req+="null,";
            if (theSt.idMaturite <= 0)
              req+="null,";
            else
              req+="'"+theSt.idMaturite+"',";
            
            if (theSt.MOEProd1 == null)
              req+="null,";
            else
              req+="'"+theSt.MOEProd1.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
            
             if (theSt.Backup == null)
              req+="null,";
            else
              req+="'"+theSt.Backup.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";  
             
             if (theSt.ControleAcces == null)
              req+="null,";
            else
              req+="'"+theSt.ControleAcces.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";             

            req+="'"+theSt.Complexite+"',";
            req+="'"+theSt.idTypeClient+"',";
            req+=""+theSt.serviceMetier;

            req+=")";

            //this.trace("@@@@"+i+"/"+Liste.size(),"nomSt",""+theSt.nomSt+"/"+theSt.idVersionSt);
            try{
                this.ExecUpdate(req);
            }
            catch (Exception e)
            {
                
                //this.trace("@@@@"+i+"/"+Liste.size(),"req",""+req);
            }
            

    } 
   }
   
  public void updateKeyIdVersionForSt(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        ST theSt = (ST)Liste.elementAt(i);
        int id_Origine = theSt.idVersionSt;
        String id_Destination = (String)ListeVersionSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nomSt=getNomById(sourceBd, theSt.idVersionSt, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            theSt.idVersionSt=getIdByNom(nomSt, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            ListeVersionSt.put(id_Origine, ""+ theSt.idVersionSt);
        }
        else
        {
          theSt.idVersionSt = Integer.parseInt(id_Destination);
        }
    }        
  }   
   
   public void updateVersionSt(Vector Liste){  
           

    for (int i=0; i < Liste.size(); i++)
    {
        ST theSt= (ST)Liste.elementAt(i);
        
            if (theSt.descVersionSt == null)
              theSt.descVersionSt = "";
            else
            {
              theSt.descVersionSt=Utils.removeBR(theSt.descVersionSt);
              theSt.descVersionSt=theSt.descVersionSt.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");      
            }
            
            if (theSt.ModeAcq == null)
              theSt.ModeAcq = "";
            else
            {
              theSt.ModeAcq=theSt.ModeAcq.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");      
            } 
            
            if (theSt.ControleAcces == null)
              theSt.ControleAcces = "";
            else
            {
              theSt.ControleAcces=theSt.ControleAcces.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");      
            }  
        String str_creation="";
        if (theSt.creationVersionSt != null)
            str_creation ="convert(datetime, '"+theSt.creationVersionSt.getDate()+"/"+(theSt.creationVersionSt.getMonth()+1)+"/"+(theSt.creationVersionSt.getYear() +1900)+"', 103)";
        else
            str_creation="null";        
        
        String str_kill="";
        if (theSt.killVersionSt != null)
            str_kill ="convert(datetime, '"+theSt.killVersionSt.getDate()+"/"+(theSt.killVersionSt.getMonth()+1)+"/"+(theSt.killVersionSt.getYear() +1900)+"', 103)";
        else
            str_kill="null";          
        
        
    req = "UPDATE VersionSt ";
    req+= " SET ";
    req+= " creationVersionSt ="+ str_creation ;
    req+= ", ";
    req+= " killVersionSt ="+ str_kill ;
    req+= " WHERE idVersionSt ="+ theSt.idVersionSt;


            //this.trace("@@@@"+i+"/"+Liste.size(),"nomSt",""+theSt.nomSt+"/"+theSt.idVersionSt);
            try{
                this.ExecUpdate(req);
            }
            catch (Exception e)
            {
                
                //this.trace("@@@@"+i+"/"+Liste.size(),"req",""+req);
            }
            

    } 
   }   
   
  public void updateKeyStForSt(db sourceBd,Vector Liste){

    for (int i=0; i < Liste.size(); i++)
    {
        ST theSt= (ST)Liste.elementAt(i);
        int id_Origine = theSt.idSt;
        String id_Destination = (String)ListeSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nomSt=getNomById(sourceBd, theSt.idSt, "St", "nomSt", "idSt");
            theSt.idSt=getIdByNom(nomSt, "St", "nomSt", "idSt");
            ListeSt.put(id_Origine, ""+ theSt.idSt);
        }
        else
        {
          theSt.idSt = Integer.parseInt(id_Destination);
        }
    }       
  }    
   
  public void updateKeySiForSt(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        ST theSt= (ST)Liste.elementAt(i);
        int id_Origine = theSt.siVersionSt;
        String id_Destination = (String)ListeSi.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theSt.siVersionSt, "SI ", "nomSI", "idSI");
            theSt.siVersionSt=getIdByNom(nom,  "SI ", "nomSI", "idSI");
            ListeSi.put(id_Origine, ""+ theSt.siVersionSt);
        }
        else
        {
          theSt.siVersionSt = Integer.parseInt(id_Destination);
        }
    }     
  }   
  
 public String getNomTypeSiById(db sourceBd, int id)
  {
    String nom="";
    req = "SELECT     SI.nomSI + '-' + TypeSi.nomTypeSi AS nom";
    req+=" FROM         TypeSi INNER JOIN";
    req+="                      SI ON TypeSi.siTypesi = SI.idSI";
    req+=" WHERE TypeSi.idTypeSi ="+id;
    sourceBd.ExecReq( req);
    try {
       while (sourceBd.rs.next()) {
            nom= sourceBd.rs.getString(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return nom;
  } 
 
   public int getIdByNomTypeSi(String nom)
  {
    int id=-1;
    req = "SELECT     TypeSi.idTypeSi ";
    req+=" FROM         TypeSi INNER JOIN";
    req+="                      SI ON TypeSi.siTypesi = SI.idSI";
    req+=" WHERE     (SI.nomSI + '-' + TypeSi.nomTypeSi = '"+nom+"')";
    this.ExecReq( req);
    try {
       while (rs.next()) {
        id= rs.getInt(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return id;
  }    
  
  public void updateKeyTypeSiForSt(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        ST theSt= (ST)Liste.elementAt(i);
        int id_Origine = theSt.typeSiVersionSt;
        String id_Destination = (String)ListeTypeSi.get(id_Origine);
        if (id_Destination == null)
        {
            String nomTypeSi=getNomTypeSiById(sourceBd, theSt.typeSiVersionSt);
            theSt.typeSiVersionSt=getIdByNomTypeSi(nomTypeSi);
            ListeTypeSi.put(id_Origine, ""+ theSt.typeSiVersionSt);
        }
        else
        {
          theSt.typeSiVersionSt = Integer.parseInt(id_Destination);
        }
    }       
  }  
  
  public void updateKeyMacroStForSt(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        ST theSt= (ST)Liste.elementAt(i);
        if (theSt.nomSt.equals("OcreS"))
        {
            int xxxx = 0;
        }
        int id_Origine = theSt.macrostVersionSt;
        String id_Destination = (String)ListeMacroSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nomMacrost=getNomById(sourceBd, theSt.macrostVersionSt, "MacroSt", "nomMacrost", "idMacrost");
            theSt.macrostVersionSt=getIdByNom(nomMacrost,  "MacroSt", "nomMacrost", "idMacrost");
            ListeMacroSt.put(id_Origine, ""+ theSt.macrostVersionSt);
        }
        else
        {
          theSt.macrostVersionSt = Integer.parseInt(id_Destination);
        }
    }     
  } 
  
  public void updateKeyServiceMoeForSt(db sourceBd,Vector Liste){

    for (int i=0; i < Liste.size(); i++)
    {
        ST theSt= (ST)Liste.elementAt(i);
        int id_Origine = theSt.serviceMoeVersionSt;
        String id_Destination = (String)ListeServices.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theSt.serviceMoeVersionSt, "Service", "nomService", "idService");
            theSt.serviceMoeVersionSt=getIdByNom(nom,  "Service", "nomService", "idService");
            ListeServices.put(id_Origine, ""+ theSt.serviceMoeVersionSt);
        }
        else
        {
          theSt.serviceMoeVersionSt = Integer.parseInt(id_Destination);
        }
    }    
  }  
  
  public void updateKeyServiceMoaForSt(db sourceBd,Vector Liste){

    for (int i=0; i < Liste.size(); i++)
    {
        ST theSt= (ST)Liste.elementAt(i);
        int id_Origine = theSt.serviceMoaVersionSt;
        String id_Destination = (String)ListeServices.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theSt.serviceMoaVersionSt, "Service", "nomService", "idService");
            theSt.serviceMoaVersionSt=getIdByNom(nom,  "Service", "nomService", "idService");
            ListeServices.put(id_Origine, ""+ theSt.serviceMoaVersionSt);
        }
        else
        {
          theSt.serviceMoaVersionSt = Integer.parseInt(id_Destination);
        }
    }      
  }  
  
  public void updateKeyServiceExplForSt(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        ST theSt= (ST)Liste.elementAt(i);
        int id_Origine = theSt.serviceExploitVersionSt;
        String id_Destination = (String)ListeServices.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theSt.serviceExploitVersionSt, "Service", "nomService", "idService");
            theSt.serviceExploitVersionSt=getIdByNom(nom,  "Service", "nomService", "idService");
            ListeServices.put(id_Origine, ""+ theSt.serviceExploitVersionSt);
        }
        else
        {
          theSt.serviceExploitVersionSt = Integer.parseInt(id_Destination);
        }
    }     
  }  
  
  public void updateKeyRespMoeForSt(db sourceBd,Vector Liste){
    for (int i=0; i < Liste.size(); i++)
    {
        ST theSt= (ST)Liste.elementAt(i);
        int id_Origine = theSt.respMoeVersionSt;
        String id_Destination = (String)ListeMembres.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theSt.respMoeVersionSt, "Membre", "nomMembre", "idMembre");
            theSt.respMoeVersionSt=getIdByNom(nom,  "Membre", "nomMembre", "idMembre");
            ListeMembres.put(id_Origine, ""+ theSt.respMoeVersionSt);
        }
        else
        {
          theSt.respMoeVersionSt = Integer.parseInt(id_Destination);
        }
    }
  }  
  
  public void updateKeyRespMoaForSt(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        ST theSt= (ST)Liste.elementAt(i);
        int id_Origine = theSt.respMoaVersionSt;
        String id_Destination = (String)ListeMembres.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theSt.respMoaVersionSt, "Membre", "nomMembre", "idMembre");
            theSt.respMoaVersionSt=getIdByNom(nom,  "Membre", "nomMembre", "idMembre");
            ListeMembres.put(id_Origine, ""+ theSt.respMoaVersionSt);
        }
        else
        {
          theSt.respMoaVersionSt = Integer.parseInt(id_Destination);
        }
    }    
    
    
  }    
  
  public void updateKeyFrequenceExplForSt(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        ST theSt= (ST)Liste.elementAt(i);
        int id_Origine = theSt.crenUtilVersionSt;
        String id_Destination = (String)ListeFrequences.get(id_Origine);
        if (id_Destination == null)
        {
            String crenUtilVersionSt=getNomById(sourceBd, theSt.crenUtilVersionSt, "Frequence", "nomFrequence", "idFrequence");
            theSt.crenUtilVersionSt=getIdByNom(crenUtilVersionSt, "Frequence", "nomFrequence", "idFrequence");
            ListeFrequences.put(id_Origine, ""+ theSt.crenUtilVersionSt);
        }
        else
        {
          theSt.crenUtilVersionSt = Integer.parseInt(id_Destination);
        }
    }    
  }  
  
  public void updateKeyMaturiteForSt(db sourceBd,Vector Liste){

    for (int i=0; i < Liste.size(); i++)
    {
        ST theSt= (ST)Liste.elementAt(i);
        int id_Origine = theSt.idMaturite;
        String id_Destination = (String)ListeMaturites.get(id_Origine);
        if (id_Destination == null)
        {
            String Maturite=getNomById(sourceBd, theSt.idMaturite, "Maturite", "nom", "id");
            theSt.idMaturite=getIdByNom(Maturite, "Maturite", "nom", "id");
            ListeMaturites.put(id_Origine, ""+ theSt.idMaturite);
        }
        else
        {
          theSt.idMaturite = Integer.parseInt(id_Destination);
        }
    }     
  } 
  
public Vector readModule(){
    Vector Liste = new Vector(10);
req = "SELECT  ";
req+=" Module.nomModule, Module.descModule, Module.versionStModule, Module.Ihm, Module.InterfaceSynchrone, Module.InterfaceAsynchrone, Module.idTypeModule, TypeModule.nom AS type";
req+= " FROM         Module LEFT OUTER JOIN";
req+= "                       TypeModule ON Module.idTypeModule = TypeModule.id ORDER BY Module.idModule";
    this.ExecReq( req);
    try {
       while (rs.next()) {
           Module theModule = new Module();
          theModule.nom=rs.getString("nomModule");
          if ((theModule.nom == null) || theModule.nom.equals("null")) theModule.nom ="";
          theModule.descModule=rs.getString("descModule");
          if ((theModule.descModule == null) || theModule.descModule.equals("null")) theModule.descModule ="";
          theModule.versionStModule = rs.getInt("versionStModule");
          theModule.Ihm=rs.getInt("Ihm");
          
          theModule.InterfaceSynchrone=rs.getInt("InterfaceSynchrone");
          theModule.InterfaceAsynchrone=rs.getInt("InterfaceAsynchrone");
          theModule.idTypeModule=rs.getInt("idTypeModule");
          theModule.type=rs.getString("type");
          if (theModule.type == null || theModule.type.equals("null")) theModule.type = "";      

      Liste.addElement(theModule);
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeModule(Vector Liste){
    
    
    req = "delete from Module";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        Module theModule = (Module)Liste.elementAt(i);

       
    req = "INSERT INTO [Module]";
    req+= "           ([nomModule]";
    req+= "            ,[descModule]";
    req+= "            ,[versionStModule]";
    req+= "            ,[Ihm]";
    req+= "            ,[InterfaceSynchrone]";
    req+= "            ,[InterfaceAsynchrone]";
    req+= "            ,[idTypeModule]";
    req+= "            )";    
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+Utils.clean(theModule.nom)+"'" + ",";
    req+= "'"+ Utils.clean(theModule.descModule)+"'" + ",";
    req+= "'"+theModule.versionStModule+"'" + ",";
    req+= "'"+theModule.Ihm+"'" + ",";
    req+= "'"+theModule.InterfaceSynchrone+"'" + ",";
    req+= "'"+theModule.InterfaceAsynchrone+"'" + ",";
    req+= "'"+theModule.idTypeModule+"'" + "";    
    req+= "            )";

        this.ExecUpdate(req);

    
    } 
   
  } 
   
  public void updateKeyVersionStForModule(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Module theModule = (Module)Liste.elementAt(i);
        int id_Origine = theModule.versionStModule;
        String id_Destination = (String)ListeVersionSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nomSt=getNomById(sourceBd, theModule.versionStModule, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            theModule.versionStModule=getIdByNom(nomSt, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            ListeVersionSt.put(id_Origine, ""+ theModule.versionStModule);
        }
        else
        {
          theModule.versionStModule = Integer.parseInt(id_Destination);
        }
    }        
  } 
  
  public void updateKeyTypeModule(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Module theModule = (Module)Liste.elementAt(i);
        int id_Origine = theModule.idTypeModule;
        String id_Destination = (String)ListeTypeModule.get(id_Origine);
        if (id_Destination == null)
        {
            String nomTypeModule=getNomById(sourceBd, theModule.idTypeModule, "TypeModule", "nom", "id");
            theModule.idTypeModule=getIdByNom(nomTypeModule,"TypeModule", "nom", "id");
            ListeTypeModule.put(id_Origine, ""+ theModule.idTypeModule);
        }
        else
        {
          theModule.idTypeModule = Integer.parseInt(id_Destination);
        }
    }        
  }
  
public Vector readDocumentation(){
    Vector Liste = new Vector(10);
req = "SELECT     idDocType, nom, chemin, commentaire, idDocIcone, idDocVersionSt, extension";
req+= " FROM         Documentation ORDER BY idDoc";

    this.ExecReq( req);
    try {
       while (rs.next()) {
           doc thedoc = new doc();
           thedoc.idType=rs.getInt("idDocType");
          thedoc.nom=rs.getString("nom");
          if ((thedoc.nom == null) || thedoc.nom.equals("null")) thedoc.nom ="";
          
          thedoc.chemin=rs.getString("chemin");
          if ((thedoc.chemin == null) || thedoc.chemin.equals("null")) thedoc.chemin ="";
          
          thedoc.commentaire=rs.getString("commentaire");
          if ((thedoc.commentaire == null) || thedoc.commentaire.equals("null")) thedoc.commentaire ="";
          
          thedoc.idDocIcone=rs.getInt("idDocIcone");
          thedoc.idDocVersionSt=rs.getInt("idDocVersionSt");
          
          thedoc.extension=rs.getString("extension");
          if ((thedoc.extension == null) || thedoc.extension.equals("null")) thedoc.extension ="";   

      Liste.addElement(thedoc);
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeDocumentation(Vector Liste){
    
    
    req = "delete from Documentation";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        doc thedoc = (doc)Liste.elementAt(i);

    req = "INSERT INTO [Documentation]";
    req+= "           ([idDocType]";
    req+= "            ,[chemin]";
    req+= "            ,[commentaire]";
    req+= "            ,[idDocVersionSt]";
    req+= "            ,[extension]";
    req+= "            )";    
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+thedoc.idType+"'" + ",";
    req+= "'"+thedoc.chemin+"'" + ",";
    req+= "'"+Utils.clean(thedoc.commentaire)+"'" + ",";
    req+= "'"+thedoc.idDocVersionSt+"'" + ",";
    req+= "'"+thedoc.extension+"'" + "";
 
    req+= "            )";

        this.ExecUpdate(req);

    
    } 
   
  }
   
  public void updateKeyVersionStForDocumentation(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        doc thedoc = (doc)Liste.elementAt(i);
        int id_Origine = thedoc.idDocVersionSt;
        String id_Destination = (String)ListeVersionSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nomSt=getNomById(sourceBd, thedoc.idDocVersionSt, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            thedoc.idDocVersionSt=getIdByNom(nomSt, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            ListeVersionSt.put(id_Origine, ""+ thedoc.idDocVersionSt);
        }
        else
        {
          thedoc.idDocVersionSt = Integer.parseInt(id_Destination);
        }
    }      
  }
  
public Vector readST_MW(){
    Vector Liste = new Vector(10);
req = "SELECT  versionStST_MW, mwST_MW, nbLicencesST_MW, Archi FROM   ST_MW ORDER BY idST_MW";

    this.ExecReq( req);
    try {
       while (rs.next()) {
           ST_MW theST_MW = new ST_MW();
           theST_MW.versionStST_MW=rs.getInt("versionStST_MW");
           theST_MW.mwST_MW=rs.getInt("mwST_MW");
           theST_MW.nbLicencesST_MW=rs.getInt("nbLicencesST_MW");
           theST_MW.Archi=rs.getString("Archi");
  
//if (Liste.size() < 2)
{
      Liste.addElement(theST_MW);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeST_MW(Vector Liste){
    
    
    req = "delete from ST_MW";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        ST_MW theST_MW = (ST_MW)Liste.elementAt(i);

    req = "INSERT INTO [ST_MW]";
    req+= "           ([versionStST_MW]";
    req+= "            ,[mwST_MW]";
    req+= "            ,[nbLicencesST_MW]";
    req+= "            ,[Archi]";
    req+= "            )";    
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+theST_MW.versionStST_MW+"'" + ",";
    req+= "'"+theST_MW.mwST_MW+"'" + ",";
    req+= "'"+theST_MW.nbLicencesST_MW+"'" + ",";
    req+= "'"+theST_MW.Archi+"'" ;
 
    req+= "            )";

        this.ExecUpdate(req);

    
    } 
   
  } 
   
  public void updateKeyVersionStFortheListeST_MW(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        ST_MW theST_MW = (ST_MW)Liste.elementAt(i);
        int id_Origine = theST_MW.versionStST_MW;
        String id_Destination = (String)ListeVersionSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nomSt=getNomById(sourceBd, theST_MW.versionStST_MW, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            theST_MW.versionStST_MW=getIdByNom(nomSt, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            ListeVersionSt.put(id_Origine, ""+ theST_MW.versionStST_MW);
        }
        else
        {
          theST_MW.versionStST_MW = Integer.parseInt(id_Destination);
        }
    }     
  }
  
  public void updateKeyMiddlewareFortheListeST_MW(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
       ST_MW theST_MW = (ST_MW)Liste.elementAt(i);
        int id_Origine = theST_MW.mwST_MW;
        String id_Destination = (String)ListeMiddleware.get(id_Origine);
        if (id_Destination == null)
        {
            Vector my2names = get2NomById(sourceBd, theST_MW.mwST_MW, "Middleware","nomMiddleware", "idMiddleware","VersionMW ","nomVersionMW", "idVersionMW", "mwVersionMW ");
            theST_MW.mwST_MW=getIdBy2Nom(my2names,"Middleware","nomMiddleware", "idMiddleware","VersionMW ","nomVersionMW", "idVersionMW", "mwVersionMW ");
            ListeMiddleware.put(id_Origine, ""+ theST_MW.mwST_MW);
        }
        else
        {
          theST_MW.mwST_MW = Integer.parseInt(id_Destination);
        }
    }     
  } 
  
public Vector readInterface(){
    Vector Liste = new Vector(10);
     req = "SELECT     Interface.idInterface, Interface.origineInterface, Interface.sensInterface, Interface.extremiteInterface, Interface.typeInterface, Interface.implemInterface, ";
     req+="                  Interface.frequenceInterface, Interface.descInterface, Implementation.nomImplementation, Frequence.nomFrequence, St.nomSt AS nomStOrigine, ";
     req+="                  St_1.nomSt AS nomStDestination, Interface.dumpHtml,  Interface.idEtat, typeEtatSti.nom as nomEtat, Interface.idStValide, Interface.doc";
     req+=" FROM         Interface INNER JOIN";
     req+="                  Implementation ON Interface.implemInterface = Implementation.idImplementation INNER JOIN";
     req+="                  Frequence ON Interface.frequenceInterface = Frequence.idFrequence INNER JOIN";
     req+="                  St ON Interface.origineInterface = St.idSt INNER JOIN";
     req+="                  St AS St_1 ON Interface.extremiteInterface = St_1.idSt INNER JOIN";
     req+="                  typeEtatSti ON Interface.idEtat = typeEtatSti.id ORDER BY Interface.idInterface";

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Interface theInterface = new Interface (rs.getInt("idInterface"));
       int origineInterface = rs.getInt("origineInterface");
       theInterface.sensInterface = rs.getString("sensInterface");
       int extremiteInterface = rs.getInt("extremiteInterface");
       theInterface.typeInterface = rs.getString("typeInterface");
       theInterface.idImplementation = rs.getInt("implemInterface");
       theInterface.idFrequence = rs.getInt("frequenceInterface");
       theInterface.descInterface = rs.getString("descInterface");
       theInterface.nomImplementation = rs.getString("nomImplementation");
       theInterface.nomFrequence = rs.getString("nomFrequence");
       theInterface.StOrigine = new ST(origineInterface, "idSt");
       theInterface.StOrigine.nomSt = rs.getString("nomStOrigine");
       theInterface.StDestination = new ST(extremiteInterface, "idSt");
       theInterface.StDestination.nomSt = rs.getString("nomStDestination");
       theInterface.dumpHtml = rs.getString("dumpHtml");
       theInterface.idEtat = rs.getInt("idEtat");
       theInterface.Etat = rs.getString("nomEtat");
       theInterface.idEtat_old =theInterface.idEtat;
       theInterface.idStValide = rs.getInt("idStValide");
       theInterface.doc = rs.getString("doc");
       if (theInterface.doc == null) theInterface.doc = "";
  
//if (Liste.size() < 2)
{
      Liste.addElement(theInterface);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeInterface(Vector Liste){
    
    req = "delete from Inter_OM";
    req = "delete from Interface";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        Interface theInterface = (Interface)Liste.elementAt(i);
          
         req = "INSERT Interface ( origineInterface, sensInterface, extremiteInterface, typeInterface, ";
         req+= "implemInterface, frequenceInterface, descInterface, dumpHtml, idEtat, idStValide, doc)" ;
         req+="  VALUES (";         
         req+="'"+ theInterface.StOrigine.idSt +"'";
         req+=",";
         req+="'"+ theInterface.sensInterface +"'";
         req+=",";
         req+="'"+ theInterface.StDestination.idSt +"'";
         req+=",";
         req+="'"+ theInterface.typeInterface +"'";
         req+=",";
         req+="'"+ theInterface.idImplementation +"'";
         req+=",";
         req+="'"+ theInterface.idFrequence +"'";
         req+=",";
         req+="'"+ Utils.clean(theInterface.descInterface) +"'";
         req+=",";
         req+="'"+ Utils.clean(theInterface.dumpHtml) +"'";     
         req+=",";        
         req+="'"+ theInterface.idEtat +"'";
         req+=",";
         req+="'"+ theInterface.idStValide +"'";
         req+=",";    
         req+="'"+ Utils.clean(theInterface.doc) +"'";      
          req+=")";         

        this.ExecUpdate(req);

    
    } 
   
  }
   
  public void updateKeyStOrigForInterface(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Interface theInterface = (Interface)Liste.elementAt(i);
        int id_Origine = theInterface.StOrigine.idSt;
        String id_Destination = (String)ListeSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nomSt=getNomById(sourceBd, theInterface.StOrigine.idSt, "St", "nomSt", "idSt");
            theInterface.StOrigine.idSt=getIdByNom(nomSt, "St", "nomSt", "idSt");
            ListeSt.put(id_Origine, ""+ theInterface.StOrigine.idSt);
        }
        else
        {
          theInterface.StOrigine.idSt = Integer.parseInt(id_Destination);
        }
    }     
  }   
  
  public void updateKeyStDestForInterface(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Interface theInterface = (Interface)Liste.elementAt(i);
        int id_Origine = theInterface.StDestination.idSt;
        String id_Destination = (String)ListeSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nomSt=getNomById(sourceBd, theInterface.StDestination.idSt, "St", "nomSt", "idSt");
            theInterface.StDestination.idSt=getIdByNom(nomSt, "St", "nomSt", "idSt");
            ListeSt.put(id_Origine, ""+ theInterface.StDestination.idSt);
        }
        else
        {
          theInterface.StDestination.idSt = Integer.parseInt(id_Destination);
        }
    }     
  } 
  
  public void updateKeyFrequence(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Interface theInterface = (Interface)Liste.elementAt(i);
        int id_Origine = theInterface.idFrequence;
        String id_Destination = (String)ListeFrequences.get(id_Origine);
        if (id_Destination == null)
        {
            String nomFrequence=getNomById(sourceBd, theInterface.idFrequence, "Frequence", "nomFrequence", "idFrequence");
            theInterface.idFrequence=getIdByNom(nomFrequence,  "Frequence", "nomFrequence", "idFrequence");
            ListeFrequences.put(id_Origine, ""+ theInterface.idFrequence);
        }
        else
        {
          theInterface.idFrequence = Integer.parseInt(id_Destination);
        }
    }     
  }  
  
  public void updateKeyImplementation(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Interface theInterface = (Interface)Liste.elementAt(i);
        int id_Origine = theInterface.idImplementation;
        String id_Destination = (String)ListeImplementations.get(id_Origine);
        if (id_Destination == null)
        {
            String nomImplementation=getNomById(sourceBd, theInterface.idImplementation, "Implementation", "nomImplementation", "idImplementation");
            theInterface.idImplementation=getIdByNom(nomImplementation,  "Implementation", "nomImplementation", "idImplementation");
            ListeImplementations.put(id_Origine, ""+ theInterface.idImplementation);
        }
        else
        {
          theInterface.idImplementation = Integer.parseInt(id_Destination);
        }
    }    
  }    
  
  // -------------------------------------- Gestion des Objets Metiers -----------------------------//
  
public Vector readObjetMetier(){
    Vector Liste = new Vector(10);
    req = "SELECT     ObjetMetier.nomObjetMetier, ObjetMetier.defObjetMetier, ObjetMetier.package, ObjetMetier.famObjetMetier, FamilleOM.nomFamilleOM, ";
    req+="                   ObjetMetier.lienFichierUml, ObjetMetier.lienWsdl, packageOM.nomPackage, ObjetMetier.typeEtatObjetMetier, ObjetMetier.Criticite, ";
    req+="                   ObjetMetier.idAppliIcone, ObjetMetier.idObjetMetier, ObjetMetier.idProprietaire,  Service.nomService, ObjetMetier.typeOM, ObjetMetier.dateImport, ObjetMetier.diagnostic";
    req+=" FROM         ObjetMetier INNER JOIN";
    req+="                   FamilleOM ON ObjetMetier.famObjetMetier = FamilleOM.idFamilleOM INNER JOIN";
    req+="                   packageOM ON ObjetMetier.package = packageOM.idPackage LEFT OUTER JOIN";
    req+="                   Service ON ObjetMetier.idProprietaire = Service.idService ORDER BY ObjetMetier.idObjetMetier";

    this.ExecReq( req);
    try {
       while (rs.next()) {
       OM theOM = new OM (-1);
        theOM.nomObjetMetier= rs.getString("nomObjetMetier");
        theOM.defObjetMetier= rs.getString("defObjetMetier");
        theOM.idPackage= rs.getInt("package");
        theOM.idFamille= rs.getInt("famObjetMetier");
        theOM.Famille= rs.getString("nomFamilleOM");
        theOM.LienUml=rs.getString("lienFichierUml");
        theOM.LienWsdl=rs.getString("lienWsdl");
        theOM.Package= rs.getString("nomPackage");
        theOM.typeEtatObjetMetier= rs.getInt("typeEtatObjetMetier");
        theOM.Criticite= rs.getString("Criticite");;
        theOM.idAppliIcone= rs.getInt("idAppliIcone");
        theOM.idObjetMetier= rs.getInt("idObjetMetier");
        theOM.idProprietaire= rs.getInt("idProprietaire");
        theOM.nomProprietaire= rs.getString("nomService");
        theOM.typeOM= rs.getInt("typeOM");
        theOM.dateImport= rs.getString("dateImport");
        if (theOM.dateImport == null) theOM.dateImport = "";
        theOM.diagnostic= rs.getString("diagnostic");
        if (theOM.diagnostic == null) theOM.diagnostic = "";
  
//if (Liste.size() < 2)
{
      Liste.addElement(theOM);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeObjetMetier(Vector Liste){
    
    req = "delete from Inter_OM";
    req = "delete from ObjetMetier";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        OM theOM = (OM)Liste.elementAt(i);
          
           req = "INSERT ObjetMetier (";
             req+="nomObjetMetier"+",";
             req+="defObjetMetier"+",";
             req+="package"+",";
             req+="typeEtatObjetMetier"+",";
             req+="famObjetMetier"+",";
             req+="Criticite"+",";
             req+="idAppliIcone"+",";
             req+="idProprietaire"+",";
             req+="typeOM";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+theOM.nomObjetMetier+"',";
             req+="'"+ Utils.clean(theOM.defObjetMetier)+"',";
             req+="'"+theOM.idPackage+"',";
             req+="'"+theOM.typeEtatObjetMetier+"',";
             req+="'"+theOM.idFamille+"',";
             req+="'"+theOM.Criticite+"',";
             req+="'"+theOM.idAppliIcone+"',";
             req+="'"+theOM.idProprietaire+"',";
             req+="'"+theOM.typeOM+"'";
           req+=")";        

        this.ExecUpdate(req);

    
    } 
   
  }
  public void updateKeyfamObjetMetier(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
         OM theOM = (OM)Liste.elementAt(i);
        int id_Origine = theOM.idFamille;
        String id_Destination = (String)ListeFamilles.get(id_Origine);
        if (id_Destination == null)
        {
            String nomFamille=getNomById(sourceBd, theOM.idFamille, "FamilleOM ", "nomFamilleOM", "idFamilleOM");
            theOM.idFamille=getIdByNom(nomFamille, "FamilleOM ", "nomFamilleOM", "idFamilleOM");
            ListeFamilles.put(id_Origine, ""+ theOM.idFamille);
        }
        else
        {
          theOM.idFamille = Integer.parseInt(id_Destination);
        }
    }     
  }
  
  public void updateKeypackageObjetMetier(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
         OM theOM = (OM)Liste.elementAt(i);
        int id_Origine = theOM.idPackage;
        String id_Destination = (String)ListePackages.get(id_Origine);
        if (id_Destination == null)
        {
            String nomPackage=getNomById(sourceBd, theOM.idPackage, "packageOM ", "nomPackage", "idPackage");
            theOM.idPackage=getIdByNom(nomPackage, "packageOM ", "nomPackage", "idPackage");
            ListePackages.put(id_Origine, ""+ theOM.idPackage);
        }
        else
        {
          theOM.idPackage = Integer.parseInt(id_Destination);
        }
    }      
  }  
   
public Vector readAttribut(){
    Vector Liste = new Vector(10);
    req = "SELECT     idAttribut, nomAttribut, descAttribut, omAttribut, typeAttribut, nomTypeAttribut, ordre, sens" ;
    req+= " FROM         Attribut ORDER BY idAttribut" ;

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Attribut theAttribut = new Attribut ();
        theAttribut.nom= Utils.clean(rs.getString("nomAttribut"));
        theAttribut.descModule= Utils.clean(rs.getString("descAttribut")) ;
        theAttribut.idObjetMetier= rs.getInt("omAttribut");
        theAttribut.typeAttribut= rs.getInt("typeAttribut");
        theAttribut.nomtypeAttribut= rs.getString("nomTypeAttribut");
        theAttribut.ordre= rs.getInt("ordre");
        theAttribut.sens= rs.getInt("sens");
  
//if (Liste.size() < 2)
{
      Liste.addElement(theAttribut);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeAttribut(Vector Liste){
    
    req = "delete from Attribut";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        Attribut theAttribut= (Attribut)Liste.elementAt(i);
          
        String req = "INSERT INTO Attribut   ";
        req+=" (omAttribut,nomAttribut, descAttribut, typeAttribut, nomTypeAttribut, ordre, sens)";
        req+=" VALUES ("+theAttribut.idObjetMetier+",'"+theAttribut.nom+"','"+theAttribut.descModule+"','"+theAttribut.typeAttribut+"','"+theAttribut.nomtypeAttribut+"','"+theAttribut.ordre+"','"+theAttribut.sens+"')";     

        this.ExecUpdate(req);

    
    } 
   
  } 
  public void updateKeyomAttribut(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Attribut theAttribut= (Attribut)Liste.elementAt(i);
        int id_Origine = theAttribut.idObjetMetier;
        String id_Destination = (String)ListeOm.get(id_Origine);
        if (id_Destination == null)
        {
            String nomOM=getNomById(sourceBd, theAttribut.idObjetMetier, "ObjetMetier ", "nomObjetMetier", "idObjetMetier");
            theAttribut.idObjetMetier=getIdByNom(nomOM, "ObjetMetier ", "nomObjetMetier", "idObjetMetier");
            ListeOm.put(id_Origine, ""+ theAttribut.idObjetMetier);
        }
        else
        {
          theAttribut.idObjetMetier = Integer.parseInt(id_Destination);
        }
    }     
  } 
  
public Vector readInter_OM(){
    Vector Liste = new Vector(10);
    req = "SELECT     interInter_OM, omInter_OM FROM         Inter_OM ORDER BY idInter_OM" ;

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Inter_OM theInter_OM = new Inter_OM ();
        theInter_OM.idInterface= rs.getInt("interInter_OM");
        theInter_OM.idOM= rs.getInt("omInter_OM");
  
//if (Liste.size() < 1)
{
      Liste.addElement(theInter_OM);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeInter_OM(Vector Liste){
    
    req = "delete from Inter_OM";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        Inter_OM theInter_OM= (Inter_OM)Liste.elementAt(i);
          
           req = "INSERT Inter_OM (";
             req+="interInter_OM"+",";
             req+="omInter_OM"+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+theInter_OM.idInterface+"',";
             req+="'"+theInter_OM.idOM+"'";
           req+=")";  

        this.ExecUpdate(req);

    
    } 
   
  }
   
 public String getNomInterfaceById(db sourceBd, int id)
  {
    String nom="";
    req = "SELECT     Interface.typeInterface + '-' + St.nomSt + Interface.sensInterface + St_1.nomSt AS nomInterface";
    req+=" FROM         Interface INNER JOIN";
    req+="                       St ON Interface.origineInterface = St.idSt INNER JOIN";
    req+="                       St AS St_1 ON Interface.extremiteInterface = St_1.idSt";
    req+=" WHERE     (Interface.idInterface = '"+id+"')";
    sourceBd.ExecReq( req);
    try {
       while (sourceBd.rs.next()) {
            nom= sourceBd.rs.getString(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return nom;
  } 
 
   public int getIdByNomInterface(String nom)
  {
    int id=-1;
    req = "SELECT     Interface.idInterface";
    req+=" FROM         Interface INNER JOIN";
    req+="                       St ON Interface.origineInterface = St.idSt INNER JOIN";
    req+="                      St AS St_1 ON Interface.extremiteInterface = St_1.idSt";
    req+=" WHERE     (Interface.typeInterface + '-' + St.nomSt + Interface.sensInterface + St_1.nomSt = '"+nom+"')";
    this.ExecReq( req);
    try {
       while (rs.next()) {
        id= rs.getInt(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return id;
  }

  public void updateKeyInterfaceForInter_OM(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Inter_OM theInter_OM= (Inter_OM)Liste.elementAt(i);
        int id_Origine = theInter_OM.idInterface;
        String id_Destination = (String)ListeInterfaces.get(id_Origine);
        if (id_Destination == null)
        {
            String nomInterface=getNomInterfaceById(sourceBd,theInter_OM.idInterface);
            theInter_OM.idInterface=getIdByNomInterface(nomInterface);
            ListeInterfaces.put(id_Origine, ""+ theInter_OM.idInterface);
        }
        else
        {
          theInter_OM.idInterface = Integer.parseInt(id_Destination);
        }
    }    
  } 
  
  public void updateKeyOMForInter_OM(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Inter_OM theInter_OM= (Inter_OM)Liste.elementAt(i);
        int id_Origine = theInter_OM.idOM;
        String id_Destination = (String)ListeOm.get(id_Origine);
        if (id_Destination == null)
        {
            String nomOM=getNomById(sourceBd, theInter_OM.idOM, "ObjetMetier ", "nomObjetMetier", "idObjetMetier");
            theInter_OM.idOM=getIdByNom(nomOM, "ObjetMetier ", "nomObjetMetier", "idObjetMetier");
            ListeOm.put(id_Origine, ""+ theInter_OM.idOM);
        }
        else
        {
          theInter_OM.idOM = Integer.parseInt(id_Destination);
        }
    }      
  } 
  
public Vector readArboOM(){
    Vector Liste = new Vector(10);
    req = "SELECT    filsArboOM, pereArboOM FROM ArboOM ORDER BY idArboOM" ;

    this.ExecReq( req);
    try {
       while (rs.next()) {
       association theassociation = new association ();
        theassociation.idOrig= rs.getInt("filsArboOM");
        theassociation.idDest= rs.getInt("pereArboOM");
  
//if (Liste.size() < 1)
{
      Liste.addElement(theassociation);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeArboOM(Vector Liste){
    
    req = "delete from ArboOM";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        association theassociation= (association)Liste.elementAt(i);
          
           req = "INSERT ArboOM (";
             req+="filsArboOM"+",";
             req+="pereArboOM"+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+theassociation.idOrig+"',";
             req+="'"+theassociation.idDest+"'";
           req+=")";  

        this.ExecUpdate(req);

    
    } 
   
  }
   
  public void updateKeyfilsArboOM(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        association theassociation= (association)Liste.elementAt(i);
        int id_Origine = theassociation.idOrig;
        String id_Destination = (String)ListeOm.get(id_Origine);
        if (id_Destination == null)
        {
            String nomOM=getNomById(sourceBd, theassociation.idOrig, "ObjetMetier ", "nomObjetMetier", "idObjetMetier");
            theassociation.idOrig=getIdByNom(nomOM, "ObjetMetier ", "nomObjetMetier", "idObjetMetier");
            ListeOm.put(id_Origine, ""+ theassociation.idOrig);
        }
        else
        {
          theassociation.idOrig = Integer.parseInt(id_Destination);
        }
    }     
  }  
  
  public void updateKeypereArboOM(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        association theassociation= (association)Liste.elementAt(i);
        int id_Origine = theassociation.idDest;
        String id_Destination = (String)ListeOm.get(id_Origine);
        if (id_Destination == null)
        {
            String nomOM=getNomById(sourceBd, theassociation.idDest, "ObjetMetier ", "nomObjetMetier", "idObjetMetier");
            theassociation.idDest=getIdByNom(nomOM, "ObjetMetier ", "nomObjetMetier", "idObjetMetier");
            ListeOm.put(id_Origine, ""+ theassociation.idDest);
        }
        else
        {
          theassociation.idDest = Integer.parseInt(id_Destination);
        }
    }        
  }
  
public Vector readGraphCirculationOm(){
    Vector Liste = new Vector(10);
    req = "SELECT     idOm, idVersionSt, x, y FROM    GraphCirculationOm ORDER BY id" ;

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Node theNode = new Node ("");
        theNode.id= rs.getInt("idOm");
        theNode.idCentre= rs.getInt("idVersionSt");
        theNode.x= rs.getInt("x");
        theNode.y= rs.getInt("y");
  
//if (Liste.size() < 1)
{
      Liste.addElement(theNode);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeGraphCirculationOm(Vector Liste){
    
    req = "delete from GraphCirculationOm";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        Node theNode= (Node)Liste.elementAt(i);
          
           req = "INSERT GraphCirculationOm (";
             req+="idOm"+",";
             req+="idVersionSt"+",";
             req+="x"+",";
             req+="y"+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+theNode.id+"',";
             req+="'"+theNode.idCentre+"',";
             req+="'"+theNode.x+"',";
             req+="'"+theNode.y+"'";
           req+=")";  

        this.ExecUpdate(req);

    
    } 
   
  }
   
  public void updateKeyOMforGraphCirculationOm(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Node theNode= (Node)Liste.elementAt(i);
        int id_Origine = theNode.id;
        String id_Destination = (String)ListeOm.get(id_Origine);
        if (id_Destination == null)
        {
            String nomOM=getNomById(sourceBd, theNode.id, "ObjetMetier ", "nomObjetMetier", "idObjetMetier");
            theNode.id=getIdByNom(nomOM, "ObjetMetier ", "nomObjetMetier", "idObjetMetier");
            ListeOm.put(id_Origine, ""+ theNode.id);
        }
        else
        {
          theNode.id = Integer.parseInt(id_Destination);
        }
    }     
  }    
  
  public void updateKeyVersionStforGraphCirculationOm(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Node theNode= (Node)Liste.elementAt(i);
        int id_Origine =  theNode.idCentre;
        String id_Destination = (String)ListeVersionSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nomSt=getNomById(sourceBd, theNode.idCentre, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            theNode.idCentre=getIdByNom(nomSt, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            ListeVersionSt.put(id_Origine, ""+  theNode.idCentre);
        }
        else
        {
          theNode.idCentre = Integer.parseInt(id_Destination);
        }
    }     
  }
  
public Vector readGraphGraphOM(){
    Vector Liste = new Vector(10);
    req = "SELECT     idOMRef, idOM, x, y FROM   GraphOM ORDER BY id" ;

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Node theNode = new Node ("");
        theNode.id= rs.getInt("idOMRef");
        theNode.idCentre= rs.getInt("idOM");
        theNode.x= rs.getInt("x");
        theNode.y= rs.getInt("y");
  
//if (Liste.size() < 1)
{
      Liste.addElement(theNode);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeGraphOM(Vector Liste){
    
    req = "delete from GraphOM";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        Node theNode= (Node)Liste.elementAt(i);
          
           req = "INSERT GraphOM (";
             req+="idOMRef"+",";
             req+="idOM"+",";
             req+="x"+",";
             req+="y"+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+theNode.id+"',";
             req+="'"+theNode.idCentre+"',";
             req+="'"+theNode.x+"',";
             req+="'"+theNode.y+"'";
           req+=")";  

        this.ExecUpdate(req);

    
    } 
   
  }
   
  public void updateKeyOMOrigforGraphOM(db sourceBd,Vector Liste){

    for (int i=0; i < Liste.size(); i++)
    {
        Node theNode= (Node)Liste.elementAt(i);
        int id_Origine = theNode.id;
        String id_Destination = (String)ListeOm.get(id_Origine);
        if (id_Destination == null)
        {
            String nomOM=getNomById(sourceBd, theNode.id, "ObjetMetier ", "nomObjetMetier", "idObjetMetier");
            theNode.id=getIdByNom(nomOM, "ObjetMetier ", "nomObjetMetier", "idObjetMetier");
            ListeOm.put(id_Origine, ""+ theNode.id);
        }
        else
        {
          theNode.id = Integer.parseInt(id_Destination);
        }
    }     
  }   
  
  public void updateKeyOMDestforGraphOM(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Node theNode= (Node)Liste.elementAt(i);
        int id_Origine = theNode.idCentre;
        String id_Destination = (String)ListeOm.get(id_Origine);
        if (id_Destination == null)
        {
            String nomOM=getNomById(sourceBd, theNode.idCentre, "ObjetMetier ", "nomObjetMetier", "idObjetMetier");
            theNode.idCentre=getIdByNom(nomOM, "ObjetMetier ", "nomObjetMetier", "idObjetMetier");
            ListeOm.put(id_Origine, ""+ theNode.idCentre);
        }
        else
        {
          theNode.idCentre = Integer.parseInt(id_Destination);
        }
    }        
    
    
  }
  
public Vector readGraph(){
    Vector Liste = new Vector(10);
    req = "SELECT     centreGraph FROM   Graph ORDER BY idGraph" ;

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Node theNode = new Node ("");
        theNode.idCentre= rs.getInt("centreGraph");
  
//if (Liste.size() < 1)
{
      Liste.addElement(theNode);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeGraph(Vector Liste){
    
    req = "delete from GraphSt";
    req = "delete from Graph";
    
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        Node theNode= (Node)Liste.elementAt(i);
          
           req = "INSERT Graph (";
             req+="centreGraph "+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+theNode.idCentre+"'";
           req+=")";  

        this.ExecUpdate(req);

    
    } 
   
  }
   
  public void updateKeyVersionStorGraph(db sourceBd,Vector Liste){
    for (int i=0; i < Liste.size(); i++)
    {
        Node theNode= (Node)Liste.elementAt(i);
        int id_Origine = theNode.idCentre;
        String id_Destination = (String)ListeVersionSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nomSt=getNomById(sourceBd, theNode.idCentre, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            theNode.idCentre=getIdByNom(nomSt, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            ListeVersionSt.put(id_Origine, ""+ theNode.idCentre);
        }
        else
        {
          theNode.idCentre = Integer.parseInt(id_Destination);
        }
    }     
  }    
  
public Vector readGraphSt(){
    Vector Liste = new Vector(10);
    req = "SELECT     graphGraphSt, stGraphSt, abscisseGraphSt, ordonneeGraphSt FROM   GraphSt ORDER BY idGraphSt" ;

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Node theNode = new Node ("");
        theNode.id= rs.getInt("graphGraphSt");
        theNode.idCentre= rs.getInt("stGraphSt");
        theNode.x= rs.getInt("abscisseGraphSt");
        theNode.y= rs.getInt("ordonneeGraphSt");
  
//if (Liste.size() < 1)
{
      Liste.addElement(theNode);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeGraphSt(Vector Liste){
    
    req = "delete from GraphSt";
    
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        Node theNode= (Node)Liste.elementAt(i);
          
           req = "INSERT GraphSt (";
             req+="graphGraphSt"+",";
             req+="stGraphSt"+",";
             req+="abscisseGraphSt"+",";
             req+="ordonneeGraphSt "+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+theNode.id+"',";
             req+="'"+theNode.idCentre+"',";
             req+="'"+theNode.x+"',";
             req+="'"+theNode.y+"'";
           req+=")";  

        this.ExecUpdate(req);

    
    } 
   
  }
   
 public String getNomGraphById(db sourceBd, int id)
  {
    String nom="";
    req = "SELECT     St.nomSt AS nomGraphe";
    req+=" FROM         Graph INNER JOIN";
    req+="                       VersionSt ON Graph.centreGraph = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE  Graph.idGraph = "+id;
    sourceBd.ExecReq( req);
    try {
       while (sourceBd.rs.next()) {
            nom= sourceBd.rs.getString(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return nom;
  } 
 
   public int getIdByNomGraph(String nom)
  {
    int id=-1;
    req = "SELECT     Graph.idGraph";
    req+=" FROM         Graph INNER JOIN";
    req+="                       VersionSt ON Graph.centreGraph = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     (St.nomSt = '"+nom+"')";
    this.ExecReq( req);
    try {
       while (rs.next()) {
        id= rs.getInt(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return id;
  }   
   
  public void updateKeyGraphForGraphSt(db sourceBd,Vector Liste){
    for (int i=0; i < Liste.size(); i++)
    {
        Node theNode= (Node)Liste.elementAt(i);
        String nomGraphe=getNomGraphById(sourceBd, theNode.id);
        theNode.id=getIdByNomGraph(nomGraphe);
    } 
  }    
  
  public void updateKeySTorGraphSt(db sourceBd,Vector Liste){
    for (int i=0; i < Liste.size(); i++)
    {
        Node theNode= (Node)Liste.elementAt(i);       
        
        String nomSt=getNomById(sourceBd, theNode.idCentre, "St", "nomSt", "idSt");
        theNode.idCentre=getIdByNom(nomSt, "St", "nomSt", "idSt");        
    } 
  }
  
public Vector readGrapMacroSt(){
    Vector Liste = new Vector(10);
    req = "SELECT     idMacroSt, idSt, x, y FROM   GrapMacroSt ORDER BY id" ;

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Node theNode = new Node ("");
        theNode.id= rs.getInt("idMacroSt");
        theNode.idCentre= rs.getInt("idSt");
        theNode.x= rs.getInt("x");
        theNode.y= rs.getInt("y");
  
//if (Liste.size() < 1)
{
      Liste.addElement(theNode);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeGrapMacroSt(Vector Liste){
    
    req = "delete from GrapMacroSt";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        Node theNode= (Node)Liste.elementAt(i);
          
           req = "INSERT GrapMacroSt (";
             req+="idMacroSt"+",";
             req+="idSt"+",";
             req+="x"+",";
             req+="y"+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+theNode.id+"',";
             req+="'"+theNode.idCentre+"',";
             req+="'"+theNode.x+"',";
             req+="'"+theNode.y+"'";
           req+=")";  

        this.ExecUpdate(req);

    
    } 
   
  }
   
  public void updateKeyMacroStForGrapMacroSt(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Node theNode= (Node)Liste.elementAt(i);
        int id_Origine = theNode.id;
        String id_Destination = (String)ListeMacroSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theNode.id, "MacroSt ", "nomMacrost", "idMacrost");
            theNode.id=getIdByNom(nom,"MacroSt ", "nomMacrost", "idMacrost");
            ListeMacroSt.put(id_Origine, ""+ theNode.id);
        }
        else
        {
          theNode.id = Integer.parseInt(id_Destination);
        }
    }     
  }  
  
  public void updateKeySTorGrapMacroSt(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Node theNode= (Node)Liste.elementAt(i);
        int id_Origine = theNode.idCentre;
        String id_Destination = (String)ListeSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nomSt=getNomById(sourceBd, theNode.idCentre, "St", "nomSt", "idSt");
            theNode.idCentre=getIdByNom(nomSt, "St", "nomSt", "idSt");
            ListeSt.put(id_Origine, ""+ theNode.idCentre);
        }
        else
        {
          theNode.idCentre = Integer.parseInt(id_Destination);
        }
    }     
  }
  
  // ---------------------------------- Gestion Roadmap --------------------------------//
  
public Vector readBrief(){
    Vector Liste = new Vector(10);
    String req = "SELECT  Briefs.idRoadmap, Briefs.version, Briefs.description, Briefs.dateMep, typeEtatBrief.nom AS Etat, Briefs.idPO, Briefs.Ordre, Briefs.standby, ";
    req+="                   Briefs.LoginCreateur, Briefs.dateCreation, Briefs.Cout, Briefs.Qualite, Briefs.Fonctionnalite, Briefs.Delais, Briefs.TypeDemande, Briefs.Contexte, ";
    req+="                   Briefs.ImpactSt, Briefs.ImpactProcessus, Briefs.Gain, Briefs.Manque, Briefs.idEtat, Briefs.Reponse, Briefs.dateCOMAA, Briefs.dateReponse, Briefs.idServiceAffecte, Briefs.idStAffecte, Briefs.idUsineAffecte, Briefs.docEB, Briefs.prepaReponse";
    req+=" FROM         Briefs INNER JOIN";
    req+="                   typeEtatBrief ON Briefs.idEtat = typeEtatBrief.id ORDER BY Briefs.idRoadmap";

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Brief theBrief = new Brief (-1);
        theBrief.version = rs.getString("version");
        theBrief.description = rs.getString("description");
        if ((theBrief.description != null) &&(theBrief.description.equals("null"))) theBrief.description="";


        theBrief.Etat = rs.getString("Etat");

        theBrief.idPO = rs.getString("idPO");
        if ( (theBrief.idPO == null) || (theBrief.idPO.equals("0")))
          theBrief.idPO = "";

        theBrief.Ordre = rs.getString("Ordre");
        theBrief.standby = rs.getInt("standby");
        theBrief.LoginCreateur = rs.getString("LoginCreateur");
        theBrief.DdateCreation = rs.getDate("dateCreation");
        if (theBrief.DdateCreation != null)
          theBrief.dateCreation = ""+theBrief.DdateCreation.getDate()+"/"+(theBrief.DdateCreation.getMonth()+1) + "/"+(theBrief.DdateCreation.getYear() + 1900);
         else
          theBrief.dateCreation = "";

        theBrief.Cout = rs.getInt("Cout");
        theBrief.Qualite = rs.getInt("Qualite");
        theBrief.Fonctionnalite = rs.getInt("Fonctionnalite");
        theBrief.Delais = rs.getInt("Delais");
        theBrief.TypeDemande = rs.getInt("TypeDemande");
        theBrief.Contexte = rs.getString("Contexte");

        theBrief.ImpactSt = rs.getString("ImpactSt");
        theBrief.ImpactProcessus = rs.getString("ImpactProcessus");

        theBrief.Gain = rs.getString("Gain");
        theBrief.Manque = rs.getString("Manque");

        theBrief.idEtat = rs.getInt("idEtat");
        theBrief.Reponse = rs.getString("Reponse");
        if ((theBrief.Reponse == null) || (theBrief.Reponse.equals("null"))) theBrief.Reponse="";

        theBrief.DdateCOMAA = rs.getDate("dateCOMAA");
        if (theBrief.DdateCOMAA != null)
          theBrief.dateCOMAA = ""+theBrief.DdateCOMAA.getDate()+"/"+(theBrief.DdateCOMAA.getMonth()+1) + "/"+(theBrief.DdateCOMAA.getYear() + 1900);
         else
          theBrief.dateCOMAA = "";

        theBrief.DdateReponse = rs.getDate("dateReponse");
        if (theBrief.DdateReponse != null)
          theBrief.dateReponse = ""+theBrief.DdateReponse.getDate()+"/"+(theBrief.DdateReponse.getMonth()+1) + "/"+(theBrief.DdateReponse.getYear() + 1900);
         else
          theBrief.dateReponse = "";

        theBrief.idServiceAffecte= rs.getInt("idServiceAffecte");
        theBrief.idVersionSt= ""+rs.getInt("idStAffecte");
        theBrief.idUsineAffecte= rs.getInt("idUsineAffecte");

        theBrief.docEB = rs.getString("docEB");

        theBrief.prepaReponse = rs.getString("prepaReponse");
        if ((theBrief.prepaReponse == null) || (theBrief.prepaReponse.equals("null"))) theBrief.prepaReponse="";
  
//if (Liste.size() < 1)
{
      Liste.addElement(theBrief);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeBrief(Vector Liste){
    
    req = "delete from BriefSt";
    this.ExecUpdate( req);   
    
    req = "delete from Briefs";
    this.ExecUpdate( req);       

    for (int i=0; i < Liste.size(); i++)
    {
        Brief theBrief= (Brief)Liste.elementAt(i);
          
    if (theBrief.oldversion != null)
      theBrief.oldversion=theBrief.oldversion.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
    else
      theBrief.oldversion="";

  String version="";
  if (theBrief.version != null)
    version=theBrief.version.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
  else
    version="";

  String Contexte="";
  if (theBrief.Contexte != null)
    Contexte=theBrief.Contexte.replaceAll("\u0092","'").replaceAll("'","''");
  else
    Contexte="";

  String description="";
  if (theBrief.description != null)
    description=theBrief.description.replaceAll("\u0092","'").replaceAll("'","''");
  else
    description="";

  String Gain="";
  if (theBrief.Gain != null)
    Gain=theBrief.Gain.replaceAll("\u0092","'").replaceAll("'","''");
  else
    Gain="";

  String Manque="";
  if (theBrief.Manque != null)
    Manque=theBrief.Manque.replaceAll("\u0092","'").replaceAll("'","''");
  else
    Manque="";

  String ImpactProcessus="";
  if (theBrief.ImpactProcessus != null)
    ImpactProcessus=theBrief.ImpactProcessus.replaceAll("\u0092","'").replaceAll("'","''");
  else
    ImpactProcessus="";

  String ImpactSt="";
  if (theBrief.ImpactSt != null)
    ImpactSt=theBrief.ImpactSt.replaceAll("\u0092","'").replaceAll("'","''");
  else
    ImpactSt="";

  String Reponse="";
  if (theBrief.Reponse != null)
    Reponse=theBrief.Reponse.replaceAll("\u0092","'").replaceAll("'","''");
  else
    Reponse="";

  String prepaReponse="";
  if (theBrief.prepaReponse != null)
    prepaReponse=theBrief.prepaReponse.replaceAll("\u0092","'").replaceAll("'","''");
  else
    prepaReponse="";

    String str_dateProd ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";

    try{
      if ( (theBrief.dateProd != null) && (!theBrief.dateProd.equals(""))) {
        Utils.getDate(theBrief.dateProd);
        str_dateProd = "convert(datetime, '" + Utils.Day + "/" + Utils.Month + "/" +
            Utils.Year + "', 103)";
      }
    } catch (Exception e){}


    String str_dateCreation ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    try{
      if ( (theBrief.dateCreation != null) && (!theBrief.dateCreation.equals(""))) {
        Utils.getDate(theBrief.dateCreation);
        str_dateCreation = "convert(datetime, '" + Utils.Day + "/" +
            Utils.Month + "/" + Utils.Year + "', 103)";
      }
    }    catch (Exception e){}
    
        if (theBrief.DdateCreation != null)
            theBrief.dateCreation ="convert(datetime, '"+theBrief.DdateCreation.getDate()+"/"+(theBrief.DdateCreation.getMonth()+1)+"/"+(theBrief.DdateCreation.getYear() +1900)+"', 103)";
        else
            theBrief.dateCreation="null";  
        
        if (theBrief.DdateCOMAA != null)
            theBrief.dateCOMAA ="convert(datetime, '"+theBrief.DdateCOMAA.getDate()+"/"+(theBrief.DdateCOMAA.getMonth()+1)+"/"+(theBrief.DdateCOMAA.getYear() +1900)+"', 103)";
        else
            theBrief.dateCOMAA="null";      
        
        if (theBrief.DdateReponse != null)
            theBrief.dateReponse ="convert(datetime, '"+theBrief.DdateReponse.getDate()+"/"+(theBrief.DdateReponse.getMonth()+1)+"/"+(theBrief.DdateReponse.getYear() +1900)+"', 103)";
        else
            theBrief.dateReponse="null";        

    req = "INSERT Briefs (idServiceAffecte,idUsineAffecte,idEtat, version, description, dateMep,  idPO, Ordre, standby, LoginCreateur ";
    req+=",";
    req+="Contexte";
    req+=",";
    req+="TypeDemande";
    req+=",";
    req+="Gain";
    req+=",";
    req+="Manque";
    req+=",";
    req+="ImpactProcessus";
    req+=",";
    req+="ImpactSt";
    req+=",";
    req+="Cout";
    req+=",";
    req+="Qualite";
    req+=",";
    req+="Fonctionnalite";
    req+=",";
    req+="Delais";
    req+=",";
    req+="Reponse";
    req+=",";
    req+="docEB";
    req+=",";
    req+="prepaReponse";
    req+=",";
    req+="dateCreation";
    req+=",";
    req+="dateCOMAA";
    req+=",";
    req+="dateReponse";    
    req+="   )";
    req+=" VALUES (";
    req+="'"+theBrief.idServiceAffecte+"',";    
    req+="'"+theBrief.idUsineAffecte+"',";    
    req+="'"+theBrief.idEtat+"',";    
    req+="'"+version+"',  '"+description+"', "+str_dateProd+",  '"+theBrief.idPO+"',  '"+theBrief.Ordre+"', "+theBrief.standby+"";
    req+=",";
    req+="'"+theBrief.LoginCreateur+"'";
    //req+=",";
    //req+=""+str_dateCreation+"";

    req+=",";
    req+="'"+Contexte+"'";
    req+=",";
    req+="'"+theBrief.TypeDemande+"'";
    req+=",";
    req+="'"+Gain+"'";
    req+=",";
    req+="'"+Manque+"'";
    req+=",";
    req+="'"+ImpactProcessus+"'";
    req+=",";
    req+="'"+ImpactSt+"'";
    req+=",";
    req+="'"+theBrief.Cout+"'";
    req+=",";
    req+="'"+theBrief.Qualite+"'";
    req+=",";
    req+="'"+theBrief.Fonctionnalite+"'";
    req+=",";
    req+="'"+theBrief.Delais+"'";
    req+=",";
    req+="'"+Reponse+"'";
    req+=",";
    req+="'"+theBrief.docEB+"'";
    req+=",";
    req+="'"+prepaReponse+"'";
    req+=",";
    req+=""+theBrief.dateCreation+"";
    req+=",";
    req+=""+theBrief.dateCOMAA+"";
    req+=",";
    req+=""+theBrief.dateReponse+"";    
    req+=")";

        this.ExecUpdate(req);

    
    } 
   
  } 
   
  public void updateKeyidServiceAffecte(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Brief theBrief= (Brief)Liste.elementAt(i);
        int id_Origine = theBrief.idServiceAffecte;
        String id_Destination = (String)ListeServices.get(id_Origine);
        if (id_Destination == null)
        {
            String nomService=getNomById(sourceBd, theBrief.idServiceAffecte, "Service", "nomService", "idService");
            theBrief.idServiceAffecte=getIdByNom(nomService, "Service", "nomService", "idService");
            ListeServices.put(id_Origine, ""+ theBrief.idServiceAffecte);
        }
        else
        {
          theBrief.idServiceAffecte= Integer.parseInt(id_Destination);
        }
    }     
    
    
  }   
  
  public void updateKeyidUsineAffecte(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Brief theBrief= (Brief)Liste.elementAt(i);
        int id_Origine = theBrief.idUsineAffecte;
        String id_Destination = (String)ListeServices.get(id_Origine);
        if (id_Destination == null)
        {
            String nomService=getNomById(sourceBd, theBrief.idUsineAffecte, "Service", "nomService", "idService");
            theBrief.idUsineAffecte=getIdByNom(nomService, "Service", "nomService", "idService");
            ListeServices.put(id_Origine, ""+ theBrief.idUsineAffecte);
        }
        else
        {
          theBrief.idUsineAffecte= Integer.parseInt(id_Destination);
        }
    }        
  } 
  
public Vector readBriefSt(){
    Vector Liste = new Vector(10);
    req = "SELECT     idVersionSt, idBrief FROM BriefSt ORDER BY id" ;

    this.ExecReq( req);
    try {
       while (rs.next()) {
       association theassociation = new association();
        theassociation.idOrig= rs.getInt("idVersionSt");
        theassociation.idDest= rs.getInt("idBrief");
  
//if (Liste.size() < 1)
{
      Liste.addElement(theassociation);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeBriefSt(Vector Liste){
    
    req = "delete from BriefSt";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        association theassociation= (association)Liste.elementAt(i);
          
           req = "INSERT BriefSt (";
             req+="idVersionSt"+",";
             req+="idBrief"+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+theassociation.idOrig+"',";
             req+="'"+theassociation.idDest+"'";
           req+=")";  

        this.ExecUpdate(req);

    
    } 
   
  }
   
  public void updateKeyidVersionStForBriefSt(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        association theassociation= (association)Liste.elementAt(i);
        int id_Origine = theassociation.idOrig;
        String id_Destination = (String)ListeVersionSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nomSt=getNomById(sourceBd, theassociation.idOrig, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            theassociation.idOrig=getIdByNom(nomSt, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            ListeVersionSt.put(id_Origine, ""+ theassociation.idOrig);
        }
        else
        {
          theassociation.idOrig = Integer.parseInt(id_Destination);
        }
    }     
  }  
  
  public void updateKeyiidBriefForBriefSt(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        association theassociation= (association)Liste.elementAt(i);
        int id_Origine = theassociation.idDest;
        String id_Destination = (String)ListeBriefs.get(id_Origine);
        if (id_Destination == null)
        {
            String nomBrief=getNomById(sourceBd, theassociation.idDest, "Briefs", "version", "idRoadmap");
            theassociation.idDest=getIdByNom(nomBrief, "Briefs", "version", "idRoadmap");
            ListeBriefs.put(id_Origine, ""+ theassociation.idDest);
        }
        else
        {
          theassociation.idDest = Integer.parseInt(id_Destination);
        }
    }      
  } 
  
public Vector readRoadmapProfil(){
    Vector Liste = new Vector(10);
    req = "SELECT      nom, phase, delais, charge FROM         RoadmapProfil ORDER BY id" ;

    this.ExecReq( req);
    try {
       while (rs.next()) {
       RoadmapProfil theRoadmapProfil = new RoadmapProfil();
        theRoadmapProfil.nom= rs.getString("nom");
        theRoadmapProfil.phase= rs.getString("phase");
        theRoadmapProfil.delais= rs.getInt("delais");
        theRoadmapProfil.charge= rs.getInt("charge");

  
//if (Liste.size() < 1)
{
      Liste.addElement(theRoadmapProfil);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeRoadmapProfil(Vector Liste){
       
    req = "delete from Roadmap";
    this.ExecUpdate( req);
    
    req = "delete from RoadmapProfil";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        RoadmapProfil theRoadmapProfil= (RoadmapProfil)Liste.elementAt(i);
          
           req = "INSERT RoadmapProfil (";
             req+="nom"+",";
             req+="phase"+",";
             req+="delais"+",";             
             req+="charge"+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+theRoadmapProfil.nom+"',";
             req+="'"+theRoadmapProfil.phase+"',";
             req+="'"+theRoadmapProfil.delais+"',";
             req+="'"+theRoadmapProfil.charge+"'";
           req+=")";  

        this.ExecUpdate(req);

    
    } 
   
  }  
   
public Vector readRoadmap(int Annee){
    Vector Liste = new Vector(10);
    String req = "SELECT   Roadmap.version, Roadmap.description, Roadmap.dateEB, Roadmap.dateMep, Roadmap.idVersionSt, Roadmap.Tendance, ";
           req+="           Roadmap.dateMes, Roadmap.Etat, Roadmap.Panier, Roadmap.idPO, Roadmap.Charge, Roadmap.Ordre, Roadmap.dateT0, Roadmap.dateTest, ";
           req+="           Roadmap.docEB, Roadmap.docDevis, Roadmap.Annee, Roadmap.MotsClef, Roadmap.dateT0_init, Roadmap.dateEB_init, Roadmap.dateTest_init, ";
           req+="           Roadmap.dateMep_init, Roadmap.dateMes_init, Roadmap.dateT0MOE, Roadmap.dateSpecMOE, Roadmap.dateDevMOE, ";
           req+="           Roadmap.dateTestMOE, Roadmap.dateLivrMOE, Roadmap.dateT0MOE_init, Roadmap.dateSpecMOE_init, Roadmap.dateDevMOE_init, ";
           req+="           Roadmap.dateTestMOE_init, Roadmap.dateLivrMOE_init, ST_ListeSt_All_Attribute.nomSt, ST_ListeSt_All_Attribute.isRecurrent, Roadmap.EtatMOE, idEquipe, LF_Month, LF_Year, idMembre, Roadmap.standby, Roadmap.idBrief, Roadmap.isDevis, Roadmap.docExigences, Roadmap.dumpHtml, Roadmap.chargeConsommeForfait, Roadmap.chargeConsommeInterne, Roadmap.idProfilSpecification, Roadmap.idProfilDeveloppement, Roadmap.idProfilTest, Roadmap.nbActions,Roadmap.nbDependances,Roadmap.isFAQ ";
           req+=" FROM         Roadmap INNER JOIN";
           req+="           ST_ListeSt_All_Attribute ON Roadmap.idVersionSt = ST_ListeSt_All_Attribute.idVersionSt";
           req+=" WHERE     (Roadmap.LF_Month = -1)";
           req+=" AND (";
           req+= " ( YEAR(Roadmap.dateMep) >= "+Annee+")";
           req+=" OR (Roadmap.isFAQ = 1)";
           req+=" )";
           req+=" AND     (Roadmap.LF_Year  = -1) ORDER BY Roadmap.idRoadmap";
           //req+="ORDER BY Roadmap.Ordre";
        

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Roadmap theRoadmap = new Roadmap();
        theRoadmap.version = rs.getString(1);
        theRoadmap.description = rs.getString(2);

        theRoadmap.DdateEB = rs.getDate(3);       
        theRoadmap.dateEB=Utils.getDateFrench(theRoadmap.DdateEB);

        theRoadmap.DdateProd = rs.getDate(4);
        theRoadmap.dateProd=Utils.getDateFrench(theRoadmap.DdateProd);

        theRoadmap.idVersionSt = rs.getString("idVersionSt");

        theRoadmap.Tendance = rs.getString("Tendance");
        if (theRoadmap.Tendance == null)  theRoadmap.Tendance = "";


        theRoadmap.DdateMes = rs.getDate("dateMes");
        theRoadmap.dateMes=Utils.getDateFrench(theRoadmap.DdateMes);

        theRoadmap.Etat = rs.getString("Etat");

        theRoadmap.Panier = rs.getString("Panier");
        theRoadmap.idPO = rs.getString("idPO");
        if ( (theRoadmap.idPO == null) || (theRoadmap.idPO.equals("0")))
          theRoadmap.idPO = "";

        theRoadmap.Ordre = rs.getString("Ordre");
        if (theRoadmap.Ordre == null) theRoadmap.Ordre = "0";

        theRoadmap.DdateT0 = rs.getDate("dateT0");
        theRoadmap.dateT0=Utils.getDateFrench(theRoadmap.DdateT0);

        theRoadmap.DdateTest = rs.getDate("dateTest");
        theRoadmap.dateTest=Utils.getDateFrench(theRoadmap.DdateTest);

        theRoadmap.docEB=rs.getString("docEB");
        if (theRoadmap.docEB == null) theRoadmap.docEB="";

        theRoadmap.docDevis=rs.getString("docDevis");
        if (theRoadmap.docDevis == null) theRoadmap.docDevis="";

        theRoadmap.Annee = rs.getInt("Annee");

        theRoadmap.MotsClef = rs.getString("MotsClef");

        theRoadmap.DdateT0_init = rs.getDate("dateT0_init");
        if (theRoadmap.DdateT0_init != null)
            theRoadmap.dateT0_init =""+theRoadmap.DdateT0_init.getDate()+"/"+(theRoadmap.DdateT0_init.getMonth()+1) + "/"+(theRoadmap.DdateT0_init.getYear() + 1900);
          else
            theRoadmap.dateT0_init = "";

          theRoadmap.DdateEB_init = rs.getDate("dateEB_init");
          if (theRoadmap.DdateEB_init != null)
              theRoadmap.dateEB_init =""+theRoadmap.DdateEB_init.getDate()+"/"+(theRoadmap.DdateEB_init.getMonth()+1) + "/"+(theRoadmap.DdateEB_init.getYear() + 1900);
            else
                theRoadmap.dateEB_init = "";

          theRoadmap.DdateTest_init = rs.getDate("dateTest_init");
          if (theRoadmap.DdateTest_init != null)
              theRoadmap.dateTest_init =""+theRoadmap.DdateTest_init.getDate()+"/"+(theRoadmap.DdateTest_init.getMonth()+1) + "/"+(theRoadmap.DdateTest_init.getYear() + 1900);
            else
            theRoadmap.dateTest_init = "";

          theRoadmap.DdateProd_init = rs.getDate("dateMep_init");
          if (theRoadmap.DdateProd_init != null)
              theRoadmap.dateProd_init =""+theRoadmap.DdateProd_init.getDate()+"/"+(theRoadmap.DdateProd_init.getMonth()+1) + "/"+(theRoadmap.DdateProd_init.getYear() + 1900);
            else
                theRoadmap.dateProd_init = "";

          theRoadmap.DdateMes_init = rs.getDate("dateMes_init");
          if (theRoadmap.DdateMes_init != null)
              theRoadmap.dateMes_init =""+theRoadmap.DdateMes_init.getDate()+"/"+(theRoadmap.DdateMes_init.getMonth()+1) + "/"+(theRoadmap.DdateMes_init.getYear() + 1900);
            else
            theRoadmap.dateMes_init = "";

          theRoadmap.DdateT0MOE = rs.getDate("dateT0MOE");
          theRoadmap.dateT0MOE=Utils.getDateFrench(theRoadmap.DdateT0MOE);

        theRoadmap.nomSt = rs.getString("nomSt");
        if ((rs.getInt("isRecurrent") != 1))
          theRoadmap.isProjet = true;
        else
          theRoadmap.isProjet = false;
        String EtatMOE = rs.getString("EtatMOE");

        theRoadmap.idEquipe = rs.getInt("idEquipe");

        theRoadmap.LF_Month = rs.getInt("LF_Month");
        theRoadmap.LF_Year = rs.getInt("LF_Year");
        theRoadmap.idMembre = rs.getInt("idMembre");

        theRoadmap.standby = rs.getInt("standby");
        theRoadmap.idBrief = rs.getInt("idBrief");
        theRoadmap.isDevis = rs.getInt("isDevis");

        theRoadmap.docExigences=rs.getString("docExigences");
        if (theRoadmap.docExigences == null) theRoadmap.docExigences="";

        theRoadmap.dumpHtml=rs.getString("dumpHtml");
        if (theRoadmap.dumpHtml == null) theRoadmap.dumpHtml="";
        
        theRoadmap.chargeConsommeForfait=rs.getFloat("chargeConsommeForfait");
        theRoadmap.chargeConsommeInterne=rs.getFloat("chargeConsommeInterne");
        
        theRoadmap.idProfilSpecification = rs.getInt("idProfilSpecification");
        theRoadmap.idProfilDeveloppement = rs.getInt("idProfilDeveloppement");
        theRoadmap.idProfilTest = rs.getInt("idProfilTest");
        
        theRoadmap.nbActions = rs.getInt("nbActions");
        theRoadmap.nbDependances = rs.getInt("nbDependances");
        
        theRoadmap.isMEX = rs.getInt("isFAQ");

  
//if (Liste.size() < 1)
{
      Liste.addElement(theRoadmap);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
public Vector readRoadmapMex(){
    Vector Liste = new Vector(10);
    String req = "SELECT   Roadmap.version, Roadmap.description, Roadmap.dateEB, Roadmap.dateMep, Roadmap.idVersionSt, Roadmap.Tendance, ";
           req+="           Roadmap.dateMes, Roadmap.Etat, Roadmap.Panier, Roadmap.idPO, Roadmap.Charge, Roadmap.Ordre, Roadmap.dateT0, Roadmap.dateTest, ";
           req+="           Roadmap.docEB, Roadmap.docDevis, Roadmap.Annee, Roadmap.MotsClef, Roadmap.dateT0_init, Roadmap.dateEB_init, Roadmap.dateTest_init, ";
           req+="           Roadmap.dateMep_init, Roadmap.dateMes_init, Roadmap.dateT0MOE, Roadmap.dateSpecMOE, Roadmap.dateDevMOE, ";
           req+="           Roadmap.dateTestMOE, Roadmap.dateLivrMOE, Roadmap.dateT0MOE_init, Roadmap.dateSpecMOE_init, Roadmap.dateDevMOE_init, ";
           req+="           Roadmap.dateTestMOE_init, Roadmap.dateLivrMOE_init, ST_ListeSt_All_Attribute.nomSt, ST_ListeSt_All_Attribute.isRecurrent, Roadmap.EtatMOE, idEquipe, LF_Month, LF_Year, idMembre, Roadmap.standby, Roadmap.idBrief, Roadmap.isDevis, Roadmap.docExigences, Roadmap.dumpHtml, Roadmap.chargeConsommeForfait, Roadmap.chargeConsommeInterne, Roadmap.idProfilSpecification, Roadmap.idProfilDeveloppement, Roadmap.idProfilTest, Roadmap.nbActions,Roadmap.nbDependances,Roadmap.isFAQ  ";
           req+=" FROM         Roadmap INNER JOIN";
           req+="           ST_ListeSt_All_Attribute ON Roadmap.idVersionSt = ST_ListeSt_All_Attribute.idVersionSt";
           req+=" WHERE     (Roadmap.LF_Month = -1)";
           req+=" AND (version='MEX')";
           req+=" AND     (Roadmap.LF_Year  = -1) ORDER BY Roadmap.idRoadmap";
           //req+="ORDER BY Roadmap.Ordre";
        

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Roadmap theRoadmap = new Roadmap();
        theRoadmap.version = rs.getString(1);
        theRoadmap.description = rs.getString(2);

        theRoadmap.DdateEB = rs.getDate(3);       
        theRoadmap.dateEB=Utils.getDateFrench(theRoadmap.DdateEB);

        theRoadmap.DdateProd = rs.getDate(4);
        theRoadmap.dateProd=Utils.getDateFrench(theRoadmap.DdateProd);

        theRoadmap.idVersionSt = rs.getString("idVersionSt");

        theRoadmap.Tendance = rs.getString("Tendance");
        if (theRoadmap.Tendance == null)  theRoadmap.Tendance = "";


        theRoadmap.DdateMes = rs.getDate("dateMes");
        theRoadmap.dateMes=Utils.getDateFrench(theRoadmap.DdateMes);

        theRoadmap.Etat = rs.getString("Etat");

        theRoadmap.Panier = rs.getString("Panier");
        theRoadmap.idPO = rs.getString("idPO");
        if ( (theRoadmap.idPO == null) || (theRoadmap.idPO.equals("0")))
          theRoadmap.idPO = "";

        theRoadmap.Ordre = rs.getString("Ordre");
        if (theRoadmap.Ordre == null) theRoadmap.Ordre = "0";

        theRoadmap.DdateT0 = rs.getDate("dateT0");
        theRoadmap.dateT0=Utils.getDateFrench(theRoadmap.DdateT0);

        theRoadmap.DdateTest = rs.getDate("dateTest");
        theRoadmap.dateTest=Utils.getDateFrench(theRoadmap.DdateTest);

        theRoadmap.docEB=rs.getString("docEB");
        if (theRoadmap.docEB == null) theRoadmap.docEB="";

        theRoadmap.docDevis=rs.getString("docDevis");
        if (theRoadmap.docDevis == null) theRoadmap.docDevis="";

        theRoadmap.Annee = rs.getInt("Annee");

        theRoadmap.MotsClef = rs.getString("MotsClef");

        theRoadmap.DdateT0_init = rs.getDate("dateT0_init");
        if (theRoadmap.DdateT0_init != null)
            theRoadmap.dateT0_init =""+theRoadmap.DdateT0_init.getDate()+"/"+(theRoadmap.DdateT0_init.getMonth()+1) + "/"+(theRoadmap.DdateT0_init.getYear() + 1900);
          else
            theRoadmap.dateT0_init = "";

          theRoadmap.DdateEB_init = rs.getDate("dateEB_init");
          if (theRoadmap.DdateEB_init != null)
              theRoadmap.dateEB_init =""+theRoadmap.DdateEB_init.getDate()+"/"+(theRoadmap.DdateEB_init.getMonth()+1) + "/"+(theRoadmap.DdateEB_init.getYear() + 1900);
            else
                theRoadmap.dateEB_init = "";

          theRoadmap.DdateTest_init = rs.getDate("dateTest_init");
          if (theRoadmap.DdateTest_init != null)
              theRoadmap.dateTest_init =""+theRoadmap.DdateTest_init.getDate()+"/"+(theRoadmap.DdateTest_init.getMonth()+1) + "/"+(theRoadmap.DdateTest_init.getYear() + 1900);
            else
            theRoadmap.dateTest_init = "";

          theRoadmap.DdateProd_init = rs.getDate("dateMep_init");
          if (theRoadmap.DdateProd_init != null)
              theRoadmap.dateProd_init =""+theRoadmap.DdateProd_init.getDate()+"/"+(theRoadmap.DdateProd_init.getMonth()+1) + "/"+(theRoadmap.DdateProd_init.getYear() + 1900);
            else
                theRoadmap.dateProd_init = "";

          theRoadmap.DdateMes_init = rs.getDate("dateMes_init");
          if (theRoadmap.DdateMes_init != null)
              theRoadmap.dateMes_init =""+theRoadmap.DdateMes_init.getDate()+"/"+(theRoadmap.DdateMes_init.getMonth()+1) + "/"+(theRoadmap.DdateMes_init.getYear() + 1900);
            else
            theRoadmap.dateMes_init = "";

          theRoadmap.DdateT0MOE = rs.getDate("dateT0MOE");
          theRoadmap.dateT0MOE=Utils.getDateFrench(theRoadmap.DdateT0MOE);

        theRoadmap.nomSt = rs.getString("nomSt");
        if ((rs.getInt("isRecurrent") != 1))
          theRoadmap.isProjet = true;
        else
          theRoadmap.isProjet = false;
        String EtatMOE = rs.getString("EtatMOE");

        theRoadmap.idEquipe = rs.getInt("idEquipe");

        theRoadmap.LF_Month = rs.getInt("LF_Month");
        theRoadmap.LF_Year = rs.getInt("LF_Year");
        theRoadmap.idMembre = rs.getInt("idMembre");

        theRoadmap.standby = rs.getInt("standby");
        theRoadmap.idBrief = rs.getInt("idBrief");
        theRoadmap.isDevis = rs.getInt("isDevis");

        theRoadmap.docExigences=rs.getString("docExigences");
        if (theRoadmap.docExigences == null) theRoadmap.docExigences="";

        theRoadmap.dumpHtml=rs.getString("dumpHtml");
        if (theRoadmap.dumpHtml == null) theRoadmap.dumpHtml="";
        
        theRoadmap.chargeConsommeForfait=rs.getFloat("chargeConsommeForfait");
        theRoadmap.chargeConsommeInterne=rs.getFloat("chargeConsommeInterne");
        
        theRoadmap.idProfilSpecification = rs.getInt("idProfilSpecification");
        theRoadmap.idProfilDeveloppement = rs.getInt("idProfilDeveloppement");
        theRoadmap.idProfilTest = rs.getInt("idProfilTest");
        
        theRoadmap.nbActions = rs.getInt("nbActions");
        theRoadmap.nbDependances = rs.getInt("nbDependances");
        
        theRoadmap.isMEX = rs.getInt("isFAQ");

  
//if (Liste.size() < 1)
{
      Liste.addElement(theRoadmap);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeRoadmap(Vector Liste){
       
    req = "delete from BesoinsUtilisateur";
    this.ExecUpdate( req);          

    req = "delete from PlanDeCharge";
    this.ExecUpdate( req);   
    
    req = "delete from historiqueSuivi";
    this.ExecUpdate( req);  
    
    req = "delete from RoadmapDependances";       
    this.ExecUpdate( req);
    req = "delete from Roadmap";
    this.ExecUpdate( req);

    for (int i=0; i < Liste.size(); i++)
    {
        Roadmap theRoadmap= (Roadmap)Liste.elementAt(i);
        
   String str_dateT0 ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateT0 != null) &&(!theRoadmap.dateT0.equals("")))
    {
      //myCnx.trace("@@1234--------","dateT0",""+dateT0);
     Utils.getDate(theRoadmap.dateT0);
     //myCnx.trace("@@5678--------","dateT0",""+dateT0);
     str_dateT0 ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateEB ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateEB != null) &&(!theRoadmap.dateEB.equals("")))
    {
     Utils.getDate(theRoadmap.dateEB);
     str_dateEB ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateTest ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";

    if ((theRoadmap.dateTest != null) &&(!theRoadmap.dateTest.equals("")))
    {
      Utils.getDate(theRoadmap.dateTest);
      str_dateTest ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateProd ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateProd != null) &&(!theRoadmap.dateProd.equals("")))
    {
      Utils.getDate(theRoadmap.dateProd);
      str_dateProd ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

     String str_dateMes="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateMes != null) &&(!theRoadmap.dateMes.equals("")))
    {
      Utils.getDate(theRoadmap.dateMes);
      str_dateMes ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateT0MOE ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateT0MOE != null) &&(!theRoadmap.dateT0MOE.equals("")))
    {
      //myCnx.trace("@@1234--------","dateT0",""+dateT0);
     Utils.getDate(theRoadmap.dateT0MOE);
     //myCnx.trace("@@5678--------","dateT0",""+dateT0);
     str_dateT0MOE ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateT0_init="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateT0_init != null) &&(!theRoadmap.dateT0_init.equals("")))
    {
     Utils.getDate(theRoadmap.dateT0_init);
     str_dateT0_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateEB_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateEB_init != null) &&(!theRoadmap.dateEB_init.equals("")))
    {
     Utils.getDate(theRoadmap.dateEB_init);
     str_dateEB_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateTest_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";

    if ((theRoadmap.dateTest_init != null) &&(!theRoadmap.dateTest_init.equals("")))
    {
      Utils.getDate(theRoadmap.dateTest_init);
      str_dateTest_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateProd_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateProd_init != null) &&(!theRoadmap.dateProd_init.equals("")))
    {
      Utils.getDate(theRoadmap.dateProd_init);
      str_dateProd_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateMes_init="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateMes_init != null) &&(!theRoadmap.dateMes_init.equals("")))
    {
      Utils.getDate(theRoadmap.dateMes_init);
      str_dateMes_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateT0MOE_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateT0MOE_init != null) &&(!theRoadmap.dateT0MOE_init.equals("")))
    {
     Utils.getDate(theRoadmap.dateT0MOE_init);
     str_dateT0MOE_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    //theRoadmap.oldversion=theRoadmap.oldversion.replaceAll("\u0092","'").replaceAll("'","''");
    if (theRoadmap.version != null)
        theRoadmap.version=theRoadmap.version.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''''").replaceAll("&","et").replace('+',' ');
    else
        theRoadmap.version="";
    
    if (theRoadmap.description != null)
        theRoadmap.description=theRoadmap.description.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''''").replaceAll("&","et").replace('+',' ');
    else
        theRoadmap.description="";    

    if (theRoadmap.MotsClef != null)
        theRoadmap.MotsClef=theRoadmap.MotsClef.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''''").replaceAll("&","et").replace('+',' ');
    else
        theRoadmap.MotsClef="";
    

    try{
      int nb = Integer.parseInt(theRoadmap.idPO) ;
    }
    catch (Exception e){
      theRoadmap.idPO = "-1";
    }

    try{
      float nb = Float.parseFloat(theRoadmap.Charge) ;
    }
    catch (Exception e){
      theRoadmap.Charge = "0";
    }


    theRoadmap.MotsClef=theRoadmap.MotsClef.replaceAll("�","e").replaceAll("�","a").replaceAll("�","a").replaceAll("�","u").replaceAll("�","e").replaceAll("�","i").replaceAll("�","e");
      String str_idMembre = "";
      if (theRoadmap.idMembre >0)
          str_idMembre = ""+theRoadmap.idMembre;
      else
          str_idMembre = "NULL";        

      req = "INSERT Roadmap (version,  description, dateEB,dateMep,dateMes,idVersionSt,Tendance, Etat, Panier, idPO, Charge, Ordre,dateT0,dateTest, docEB, docDevis, Annee, MotsClef, dateT0_init, dateEB_init, dateTest_init, dateMep_init, dateMes_init, idEquipe, LF_Month, LF_Year, dateT0MOE, dateT0MOE_init, idMembre, standby, idBrief, isDevis, idProfilSpecification,idProfilDeveloppement, idProfilTest, nbActions, nbDependances, isFAQ ) ";
      req+=" VALUES ('"+theRoadmap.version+"',  '"+theRoadmap.description+"', "+str_dateEB+""+", "+str_dateProd+", "+str_dateMes+", '"+theRoadmap.idVersionSt+"',   '"+theRoadmap.Tendance+"',  '"+theRoadmap.Etat+"',  '"+theRoadmap.Panier+"',  '"+theRoadmap.idPO+"',  '"+theRoadmap.Charge+"',  '"+theRoadmap.Ordre+"',  "+str_dateT0+",  "+str_dateTest+",  '"+theRoadmap.docEB+"',  '"+theRoadmap.docDevis+"',  '"+theRoadmap.Annee+"',  '"+theRoadmap.MotsClef+"',  "+str_dateT0_init+",  "+str_dateEB_init+",  "+str_dateTest_init+",  "+str_dateProd_init+",  "+str_dateMes_init+", "+theRoadmap.idEquipe+", "+theRoadmap.LF_Month+", "+theRoadmap.LF_Year+", "+str_dateT0MOE+", "+str_dateT0MOE_init+", "+str_idMembre+", "+theRoadmap.standby+", "+theRoadmap.idBrief+", "+theRoadmap.isDevis+", "+theRoadmap.idProfilSpecification+", "+theRoadmap.idProfilDeveloppement+", "+theRoadmap.idProfilTest+", "+theRoadmap.nbActions+", "+theRoadmap.nbDependances+", "+theRoadmap.isMEX+")";

        this.ExecUpdate(req);

    
    } 
   
  }  
   public void writeRoadmapSansDelete(Vector Liste){
    req = "delete from Roadmap where isFAQ = 1";
    this.ExecUpdate( req);       

    for (int i=0; i < Liste.size(); i++)
    {
        Roadmap theRoadmap= (Roadmap)Liste.elementAt(i);
        
   String str_dateT0 ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateT0 != null) &&(!theRoadmap.dateT0.equals("")))
    {
      //myCnx.trace("@@1234--------","dateT0",""+dateT0);
     Utils.getDate(theRoadmap.dateT0);
     //myCnx.trace("@@5678--------","dateT0",""+dateT0);
     str_dateT0 ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateEB ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateEB != null) &&(!theRoadmap.dateEB.equals("")))
    {
     Utils.getDate(theRoadmap.dateEB);
     str_dateEB ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateTest ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";

    if ((theRoadmap.dateTest != null) &&(!theRoadmap.dateTest.equals("")))
    {
      Utils.getDate(theRoadmap.dateTest);
      str_dateTest ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateProd ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateProd != null) &&(!theRoadmap.dateProd.equals("")))
    {
      Utils.getDate(theRoadmap.dateProd);
      str_dateProd ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

     String str_dateMes="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateMes != null) &&(!theRoadmap.dateMes.equals("")))
    {
      Utils.getDate(theRoadmap.dateMes);
      str_dateMes ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateT0MOE ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateT0MOE != null) &&(!theRoadmap.dateT0MOE.equals("")))
    {
      //myCnx.trace("@@1234--------","dateT0",""+dateT0);
     Utils.getDate(theRoadmap.dateT0MOE);
     //myCnx.trace("@@5678--------","dateT0",""+dateT0);
     str_dateT0MOE ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateT0_init="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateT0_init != null) &&(!theRoadmap.dateT0_init.equals("")))
    {
     Utils.getDate(theRoadmap.dateT0_init);
     str_dateT0_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateEB_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateEB_init != null) &&(!theRoadmap.dateEB_init.equals("")))
    {
     Utils.getDate(theRoadmap.dateEB_init);
     str_dateEB_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateTest_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";

    if ((theRoadmap.dateTest_init != null) &&(!theRoadmap.dateTest_init.equals("")))
    {
      Utils.getDate(theRoadmap.dateTest_init);
      str_dateTest_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateProd_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateProd_init != null) &&(!theRoadmap.dateProd_init.equals("")))
    {
      Utils.getDate(theRoadmap.dateProd_init);
      str_dateProd_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateMes_init="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateMes_init != null) &&(!theRoadmap.dateMes_init.equals("")))
    {
      Utils.getDate(theRoadmap.dateMes_init);
      str_dateMes_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateT0MOE_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((theRoadmap.dateT0MOE_init != null) &&(!theRoadmap.dateT0MOE_init.equals("")))
    {
     Utils.getDate(theRoadmap.dateT0MOE_init);
     str_dateT0MOE_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    //theRoadmap.oldversion=theRoadmap.oldversion.replaceAll("\u0092","'").replaceAll("'","''");
    if (theRoadmap.version != null)
        theRoadmap.version=theRoadmap.version.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''''").replaceAll("&","et").replace('+',' ');
    else
        theRoadmap.version="";
    
    if (theRoadmap.description != null)
        theRoadmap.description=theRoadmap.description.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''''").replaceAll("&","et").replace('+',' ');
    else
        theRoadmap.description="";    

    if (theRoadmap.MotsClef != null)
        theRoadmap.MotsClef=theRoadmap.MotsClef.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''''").replaceAll("&","et").replace('+',' ');
    else
        theRoadmap.MotsClef="";
    

    try{
      int nb = Integer.parseInt(theRoadmap.idPO) ;
    }
    catch (Exception e){
      theRoadmap.idPO = "-1";
    }

    try{
      float nb = Float.parseFloat(theRoadmap.Charge) ;
    }
    catch (Exception e){
      theRoadmap.Charge = "0";
    }


    theRoadmap.MotsClef=theRoadmap.MotsClef.replaceAll("�","e").replaceAll("�","a").replaceAll("�","a").replaceAll("�","u").replaceAll("�","e").replaceAll("�","i").replaceAll("�","e");
      String str_idMembre = "";
      if (theRoadmap.idMembre >0)
          str_idMembre = ""+theRoadmap.idMembre;
      else
          str_idMembre = "NULL";        

      req = "INSERT Roadmap (version,  description, dateEB,dateMep,dateMes,idVersionSt,Tendance, Etat, Panier, idPO, Charge, Ordre,dateT0,dateTest, docEB, docDevis, Annee, MotsClef, dateT0_init, dateEB_init, dateTest_init, dateMep_init, dateMes_init, idEquipe, LF_Month, LF_Year, dateT0MOE, dateT0MOE_init, idMembre, standby, idBrief, isDevis, idProfilSpecification,idProfilDeveloppement, idProfilTest, nbActions, nbDependances, isFAQ ) ";
      req+=" VALUES ('"+theRoadmap.version+"',  '"+theRoadmap.description+"', "+str_dateEB+""+", "+str_dateProd+", "+str_dateMes+", '"+theRoadmap.idVersionSt+"',   '"+theRoadmap.Tendance+"',  '"+theRoadmap.Etat+"',  '"+theRoadmap.Panier+"',  '"+theRoadmap.idPO+"',  '"+theRoadmap.Charge+"',  '"+theRoadmap.Ordre+"',  "+str_dateT0+",  "+str_dateTest+",  '"+theRoadmap.docEB+"',  '"+theRoadmap.docDevis+"',  '"+theRoadmap.Annee+"',  '"+theRoadmap.MotsClef+"',  "+str_dateT0_init+",  "+str_dateEB_init+",  "+str_dateTest_init+",  "+str_dateProd_init+",  "+str_dateMes_init+", "+theRoadmap.idEquipe+", "+theRoadmap.LF_Month+", "+theRoadmap.LF_Year+", "+str_dateT0MOE+", "+str_dateT0MOE_init+", "+str_idMembre+", "+theRoadmap.standby+", "+theRoadmap.idBrief+", "+theRoadmap.isDevis+", "+theRoadmap.idProfilSpecification+", "+theRoadmap.idProfilDeveloppement+", "+theRoadmap.idProfilTest+", "+theRoadmap.nbActions+", "+theRoadmap.nbDependances+", "+theRoadmap.isMEX+")";
        this.ExecUpdate(req);

    
    } 
   
  }     
   
  public void updateKeyidVersionStForRoadmap(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Roadmap theRoadmap= (Roadmap)Liste.elementAt(i);
        int id_Origine = Integer.parseInt(theRoadmap.idVersionSt);
        String id_Destination = (String)ListeVersionSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nomSt=getNomById(sourceBd, Integer.parseInt(theRoadmap.idVersionSt) , "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            theRoadmap.idVersionSt= ""+ getIdByNom(nomSt, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
            ListeVersionSt.put(id_Origine, theRoadmap.idVersionSt);
        }
        else
        {
          theRoadmap.idVersionSt = id_Destination;
        }
    }    
    
    
  }  
  
  public void updateKeidMembreForRoadmap(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Roadmap theRoadmap= (Roadmap)Liste.elementAt(i);
        int id_Origine = theRoadmap.idMembre;
        String id_Destination = (String)ListeMembres.get(id_Origine);
        if (id_Destination == null)
        {
            String nomMembre=getNomById(sourceBd, theRoadmap.idMembre , "Membre", "nomMembre", "idMembre");
            theRoadmap.idMembre= getIdByNom(nomMembre,  "Membre", "nomMembre", "idMembre");
            ListeMembres.put(id_Origine, ""+ theRoadmap.idMembre);
        }
        else
        {
          theRoadmap.idMembre = Integer.parseInt(id_Destination);
        }
    }     
  }  
  
  public void updateKeidBriefForRoadmap(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Roadmap theRoadmap= (Roadmap)Liste.elementAt(i);
        int id_Origine = theRoadmap.idBrief;
        String id_Destination = (String)ListeBriefs.get(id_Origine);
        if (id_Destination == null)
        {
            String nomBrief=getNomById(sourceBd, theRoadmap.idBrief, "Briefs", "version", "idRoadmap");
            theRoadmap.idBrief=getIdByNom(nomBrief, "Briefs", "version", "idRoadmap");
            ListeBriefs.put(id_Origine, ""+ theRoadmap.idBrief);
        }
        else
        {
          theRoadmap.idBrief = Integer.parseInt(id_Destination);
        }
        
  }  
  }
  
  public void updateKeidProfilSpecificationForRoadmap(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Roadmap theRoadmap= (Roadmap)Liste.elementAt(i);
        int id_Origine = theRoadmap.idProfilSpecification;
        String id_Destination = (String)ListetypeProfilRoadmap.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theRoadmap.idProfilSpecification, "typeProfilRoadmap", "nom", "id");
            theRoadmap.idProfilSpecification=getIdByNom(nom,  "typeProfilRoadmap", "nom", "id");
            ListetypeProfilRoadmap.put(id_Origine, ""+ theRoadmap.idProfilSpecification);
        }
        else
        {
          theRoadmap.idProfilSpecification = Integer.parseInt(id_Destination);
        }
    }      
  }  
  
  public void updateKeidProfilDeveloppementForRoadmap(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Roadmap theRoadmap= (Roadmap)Liste.elementAt(i);
        int id_Origine = theRoadmap.idProfilDeveloppement;
        String id_Destination = (String)ListetypeProfilRoadmap.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theRoadmap.idProfilDeveloppement, "typeProfilRoadmap", "nom", "id");
            theRoadmap.idProfilDeveloppement=getIdByNom(nom,  "typeProfilRoadmap", "nom", "id");
            ListetypeProfilRoadmap.put(id_Origine, ""+ theRoadmap.idProfilDeveloppement);
        }
        else
        {
          theRoadmap.idProfilDeveloppement = Integer.parseInt(id_Destination);
        }
    }       
  }  
  
  public void updateKeidProfilTestForRoadmap(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Roadmap theRoadmap= (Roadmap)Liste.elementAt(i);
        int id_Origine = theRoadmap.idProfilTest;
        String id_Destination = (String)ListetypeProfilRoadmap.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theRoadmap.idProfilTest, "typeProfilRoadmap", "nom", "id");
            theRoadmap.idProfilTest=getIdByNom(nom,  "typeProfilRoadmap", "nom", "id");
            ListetypeProfilRoadmap.put(id_Origine, ""+ theRoadmap.idProfilTest);
        }
        else
        {
          theRoadmap.idProfilTest = Integer.parseInt(id_Destination);
        }
    }       
  }  
  
public Vector readhistoriqueSuivi(int Annee){
    Vector Liste = new Vector(10);
    req = "SELECT     historiqueSuivi.dateSuivi, historiqueSuivi.nom, historiqueSuivi.description,historiqueSuivi.idRoadmap,  historiqueSuivi.type, historiqueSuivi.Login, historiqueSuivi.fichierAttache";
    req+=" FROM         historiqueSuivi INNER JOIN   Roadmap ON historiqueSuivi.idRoadmap = Roadmap.idRoadmap";
    req+=" WHERE (YEAR(Roadmap.dateMep) >= "+(Annee +1) +")";
    req+=" ORDER BY id";

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Suivi theSuivi = new Suivi();
      theSuivi.dateSuivi = rs.getDate("dateSuivi");
      theSuivi.nom = rs.getString("nom");
      theSuivi.description = rs.getString("description");
      theSuivi.idRoadmap = rs.getInt("idRoadmap");
      theSuivi.type = rs.getString("type");
      theSuivi.Login = rs.getString("Login");
      if (theSuivi.Login == null) theSuivi.Login="";
      theSuivi.fichierAttache = rs.getString("fichierAttache");
      if (theSuivi.fichierAttache == null) theSuivi.fichierAttache="";      

  
//if (Liste.size() < 1)
{
      Liste.addElement(theSuivi);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writehistoriqueSuivi(Vector Liste){
       
    
    req = "delete from historiqueSuivi";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        Suivi theSuivi= (Suivi)Liste.elementAt(i);
       
    req = "INSERT historiqueSuivi (";
    req += "dateSuivi" + ",";
    req += "nom" + ",";
    req += "description" + ",";
    req += "idRoadmap" + ",";
    req += "type" + ",";
    req += "Login" + ",";
    req += "fichierAttache" + "";
    req += ")";
    req += " VALUES ";
    req += "(";
    req += "CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)"+ ",";
    req += "'" + "Migration initiale" + "',";

    req += "'" + Utils.clean(theSuivi.description)+ "',";

    req += "'" + theSuivi.idRoadmap + "',";
    req += "'" +theSuivi.type + "',";
    req += "'" +theSuivi.Login + "',";
    req += "'" +theSuivi.fichierAttache + "'";
    req += ")";

        this.ExecUpdate(req);

    
    } 
   
  }
   
 public String getNomRoadmapById(db sourceBd, int id)
  {
    String nom="";
    req = "SELECT     St.nomSt + '-' + Roadmap.version AS nomProjet";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     (Roadmap.idRoadmap = "+id+")";
    sourceBd.ExecReq( req);
    try {
       while (sourceBd.rs.next()) {
            nom= sourceBd.rs.getString(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return nom;
  } 
 
   public int getIdByNomRoadmap(String nom)
  {
    int id=-1;
    req = "SELECT     Roadmap.idRoadmap";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     (St.nomSt + '-' + Roadmap.version = '"+nom+"')";
    this.ExecReq( req);
    try {
       while (rs.next()) {
        id= rs.getInt(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return id;
  }   
   
  public void updateKeyidRoadmapForhistoriqueSuivi(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Suivi theSuivi= (Suivi)Liste.elementAt(i);
        int id_Origine = theSuivi.idRoadmap;
        String id_Destination = (String)ListeRoadmap.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomRoadmapById(sourceBd, theSuivi.idRoadmap);
            theSuivi.idRoadmap=getIdByNomRoadmap(Utils.clean(nom));
            ListeRoadmap.put(id_Origine, ""+ theSuivi.idRoadmap);
        }
        else
        {
          theSuivi.idRoadmap = Integer.parseInt(id_Destination);
        }
    }      
  }
  
public Vector readPlanOperationnelClient(int Annee){
    Vector Liste = new Vector(10);
    req = "SELECT     PlanOperationnelClient.idWebPo, PlanOperationnelClient.Enveloppe, PlanOperationnelClient.nomProjet, PlanOperationnelClient.descProjet, PlanOperationnelClient.risqueProjet, ";
    req += "                      PlanOperationnelClient.charge, PlanOperationnelClient.LF, PlanOperationnelClient.Annee, PlanOperationnelClient.gainProjet, PlanOperationnelClient.Priorite, PlanOperationnelClient.dateEF, ";
    req += "                      PlanOperationnelClient.dateMAQUETTE, PlanOperationnelClient.dateREAL, PlanOperationnelClient.dateMEP, PlanOperationnelClient.MacroSt, PlanOperationnelClient.Service, ";
    req += "                       PlanOperationnelClient.chargeConsommee, PlanOperationnelClient.depensePrevue, PlanOperationnelClient.depenseConsommee, PlanOperationnelClient.etat";
    req += " FROM         PlanOperationnelClient ";
    req +=" WHERE     (Annee >= "+Annee+") AND (Service <> '') ORDER BY idWebPo";

    this.ExecReq( req);
    try {
       while (rs.next()) {
       LignePO theLignePO = new LignePO();
        theLignePO.idWebPo= rs.getString("idWebPo"); 
        theLignePO.Enveloppe= rs.getString("Enveloppe"); 
        theLignePO.nomProjet= rs.getString("nomProjet"); 
        theLignePO.descProjet= rs.getString("descProjet"); 
        theLignePO.risqueProjet= rs.getString("risqueProjet"); // etat du ST
        theLignePO.charge= rs.getFloat("charge"); 
        theLignePO.Annee= rs.getString("Annee");
        theLignePO.gainProjet= rs.getString("gainProjet");
        theLignePO.Priorite= rs.getString("Priorite");
        theLignePO.dateEB= rs.getString("dateEF");
        theLignePO.dateMAQUETTE= rs.getString("dateMAQUETTE");
        theLignePO.dateREAL= rs.getString("dateREAL");
        theLignePO.dateMep= rs.getString("dateMEP");
        theLignePO.macrost= rs.getString("MacroSt");       
        theLignePO.service = rs.getString("Service");
        theLignePO.chargeConsommee= rs.getFloat("chargeConsommee");
        theLignePO.ChargePrevueForfait= rs.getFloat("depensePrevue");
        theLignePO.chargeConsommeForfait= rs.getFloat("depenseConsommee");
        theLignePO.etat= rs.getString("etat");

  
//if (Liste.size() < 1)
{
      Liste.addElement(theLignePO);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writePlanOperationnelClient(Vector Liste){
       
    
    req = "delete from PlanOperationnelClient";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        LignePO theLignePO= (LignePO)Liste.elementAt(i);
       
       req = "INSERT PlanOperationnelClient (idWebPo, Enveloppe, nomProjet, descProjet, risqueProjet,";
       req+= " charge, Annee, gainProjet, Priorite, dateEF, dateMAQUETTE, dateREAL, ";
       req+= " dateMep, macrost, service, chargeConsommee, depensePrevue, depenseConsommee, etat) ";
       req+=" VALUES (";
       req+= "'"+theLignePO.idWebPo+"',  ";
       req+= "'"+ theLignePO.Enveloppe+"',";
       req+= "'"+ Utils.clean(theLignePO.nomProjet)+"',";
       req+= "'"+ Utils.clean(theLignePO.descProjet)+"',";
       req+= "'"+ Utils.clean(theLignePO.risqueProjet)+"',";
       req+= "'"+ theLignePO.charge+"',";
       req+= "'"+ theLignePO.Annee+"',";
       req+= "'"+ Utils.clean(theLignePO.gainProjet)+"', ";
       req+= "'"+ theLignePO.Priorite+"', ";
       req+= "'"+ theLignePO.dateEB+"', ";
       req+= "'"+ theLignePO.dateMAQUETTE+"', ";
       req+= "'"+ theLignePO.dateREAL+"', ";
       req+= "'"+ theLignePO.dateMep+"', ";
       req+= "'"+ theLignePO.macrost+"', ";
       req+= "'"+ theLignePO.service+"', ";
       req+= "'"+ theLignePO.chargeConsommee+"', ";
       req+= "'"+ theLignePO.ChargePrevueForfait+"', ";
       req+= "'"+ theLignePO.chargeConsommeForfait+"', ";
       req+= "'"+ theLignePO.etat+"' ";
       req+=")";

        this.ExecUpdate(req);

    
    } 
   
  }  
   
public Vector readRoadmapDependances(){
    Vector Liste = new Vector(10);
    req = "SELECT     idRoadmapDestination, idRoadmapOrigine, type, nb FROM RoadmapDependances ORDER BY id";

    this.ExecReq( req);
    try {
       while (rs.next()) {
       dependanceRoadmap thedependanceRoadmap = new dependanceRoadmap();
       thedependanceRoadmap.roadmapDestination=  new Roadmap(rs.getInt("idRoadmapDestination")); 
       thedependanceRoadmap.roadmapOrigine=  new Roadmap(rs.getInt("idRoadmapOrigine")); 
       thedependanceRoadmap.type = rs.getString("type");
       thedependanceRoadmap.nb = rs.getInt("nb");

  
//if (Liste.size() < 1)
{
      Liste.addElement(thedependanceRoadmap);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeRoadmapDependances(Vector Liste){
       
    
    req = "delete from RoadmapDependances";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        dependanceRoadmap thedependanceRoadmap= (dependanceRoadmap)Liste.elementAt(i);
    req = "INSERT RoadmapDependances (";
    req += "idRoadmapDestination" + ",";
    req += "idRoadmapOrigine" + ",";
    req += "type" + ",";
    req += "nb";
    req += ")";
    req += " VALUES ";
    req += "(";

    req += "'" +thedependanceRoadmap.roadmapDestination.id+ "',";
    req += "'" +thedependanceRoadmap.roadmapOrigine.id+ "',";
    req += "'" + thedependanceRoadmap.type + "',";
    req += "'" + thedependanceRoadmap.nb + "'";

    req += ")";        
       

        this.ExecUpdate(req);

    
    } 
   
  }
   
  public void updateKeyidRoadmapDestinationForRoadmapDependances(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        dependanceRoadmap thedependanceRoadmap= (dependanceRoadmap)Liste.elementAt(i);
        int id_Origine = thedependanceRoadmap.roadmapDestination.id;
        String id_Destination = (String)ListeRoadmap.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomRoadmapById(sourceBd, thedependanceRoadmap.roadmapDestination.id);
            thedependanceRoadmap.roadmapDestination.id=getIdByNomRoadmap(Utils.clean(nom));
            ListeRoadmap.put(id_Origine, ""+ thedependanceRoadmap.roadmapDestination.id);
        }
        else
        {
          thedependanceRoadmap.roadmapDestination.id = Integer.parseInt(id_Destination);
        }
    }     
  }   
  
  public void updateKeyidRoadmapOrigineForRoadmapDependances(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        dependanceRoadmap thedependanceRoadmap= (dependanceRoadmap)Liste.elementAt(i);
        int id_Origine = thedependanceRoadmap.roadmapOrigine.id;
        String id_Destination = (String)ListeRoadmap.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomRoadmapById(sourceBd, thedependanceRoadmap.roadmapOrigine.id);
            thedependanceRoadmap.roadmapOrigine.id=getIdByNomRoadmap(Utils.clean(nom));
            ListeRoadmap.put(id_Origine, ""+ thedependanceRoadmap.roadmapOrigine.id);
        }
        else
        {
          thedependanceRoadmap.roadmapOrigine.id = Integer.parseInt(id_Destination);
        }
    }     
  }   
  
public Vector readPlanDeCharge(int Annee){
    Vector Liste = new Vector(10);
    req = "SELECT     PlanDeCharge.projetMembre, PlanDeCharge.semaine, PlanDeCharge.Charge, PlanDeCharge.anneeRef, PlanDeCharge.idRoadmap, PlanDeCharge.etat";
    req+=" FROM         PlanDeCharge INNER JOIN";
    req+="                       Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap";
    req+=" WHERE     (YEAR(Roadmap.dateMep) >= "+Annee+")";
    this.ExecReq( req);
    try {
       while (rs.next()) {
       Charge theCharge = new Charge();
       theCharge.idMembre = rs.getInt("projetMembre");
       theCharge.semaine = rs.getInt("semaine");
       theCharge.hj = rs.getInt("Charge");
       theCharge.anneeRef = rs.getInt("anneeRef");
       theCharge.idRoadmap = rs.getInt("idRoadmap");
       theCharge.idEtat = rs.getInt("etat");

  
//if (Liste.size() < 1)
{
      Liste.addElement(theCharge);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writePlanDeCharge(Vector Liste){
       
    
    req = "delete from PlanDeCharge";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        Charge theCharge= (Charge)Liste.elementAt(i);
    req = "INSERT PlanDeCharge (";
    req += "nomProjet" + ",";
    req += "projetMembre" + ",";
    req += "semaine" + ",";
    req += "Charge" + ",";
    req += "anneeRef" + ",";
    req += "idRoadmap" + ",";
    req += "etat ";
    req += ")";
    req += " VALUES ";
    req += "(";

    req += "'" +""+ "',";
    req += "'" +theCharge.idMembre+ "',";
    req += "'" +theCharge.semaine+ "',";
    req += "'" + theCharge.hj + "',";
    req += "'" + theCharge.anneeRef + "',";
    req += "'" + theCharge.idRoadmap + "',";
    req += "'" + theCharge.idEtat + "'";

    req += ")";        
       

        this.ExecUpdate(req);

    
    } 
   
  }
   
  public void updateKeyprojetMembreForPlanDeCharge(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Charge theCharge= (Charge)Liste.elementAt(i);
        int id_Origine = theCharge.idMembre;
        String id_Destination = (String)ListeMembres.get(id_Origine);
        if (id_Destination == null)
        {
            String nomMembre=getNomById(sourceBd, theCharge.idMembre, "Membre ", "LoginMembre", "idMembre");
            theCharge.idMembre=getIdByNom(nomMembre, "Membre ", "LoginMembre", "idMembre");
            ListeMembres.put(id_Origine, ""+ theCharge.idMembre);
        }
        else
        {
          theCharge.idMembre = Integer.parseInt(id_Destination);
        }
    }    
  }   
  
  public void updateKeyidRoadmapForPlanDeCharge(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Charge theCharge= (Charge)Liste.elementAt(i);
        int id_Origine = theCharge.idRoadmap;
        String id_Destination = (String)ListeRoadmap.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomRoadmapById(sourceBd, theCharge.idRoadmap);
            theCharge.idRoadmap=getIdByNomRoadmap(Utils.clean(nom));
            ListeRoadmap.put(id_Origine, ""+ theCharge.idRoadmap);
        }
        else
        {
          theCharge.idRoadmap = Integer.parseInt(id_Destination);
        }
    }      
  }
  
public Vector readBesoinsUtilisateur(int Annee){
    Vector Liste = new Vector(10);
     req = "SELECT     BesoinsUtilisateur.id, BesoinsUtilisateur.typeBesoin, BesoinsUtilisateur.nom, BesoinsUtilisateur.description, BesoinsUtilisateur.integDevis, BesoinsUtilisateur.Commentaires, BesoinsUtilisateur.idBesoin, BesoinsUtilisateur.idRoadmap, BesoinsUtilisateur.detail, BesoinsUtilisateur.isExigence, BesoinsUtilisateur.version, BesoinsUtilisateur.image, BesoinsUtilisateur.nbNiveau, BesoinsUtilisateur.niv1, BesoinsUtilisateur.niv2, BesoinsUtilisateur.niv3, BesoinsUtilisateur.niv4, BesoinsUtilisateur.chapitre, BesoinsUtilisateur.Complexite, BesoinsUtilisateur.Cout";
     req+= "    FROM         BesoinsUtilisateur  INNER JOIN  Roadmap ON BesoinsUtilisateur.idRoadmap = Roadmap.idRoadmap";
     req+= " WHERE     (YEAR(Roadmap.dateMep) >= "+Annee+") OR (Roadmap.isFAQ = 1)";

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Besoin theBesoin = new Besoin();
       theBesoin.type = rs.getInt("typeBesoin");
       theBesoin.nom = rs.getString("nom");
       theBesoin.description = rs.getString("description");
       if (theBesoin.description == null) theBesoin.description = "";
       theBesoin.IntegDevis = rs.getInt("integDevis");
       theBesoin.Commentaire = rs.getString("Commentaires");
       if (theBesoin.Commentaire == null) theBesoin.Commentaire = "";
       theBesoin.idBesoin = rs.getString("idBesoin");
       if (theBesoin.idBesoin == null) theBesoin.idBesoin = "";
       theBesoin.idRoadmap = rs.getInt("idRoadmap");
       theBesoin.detail = rs.getString("detail");
       if (theBesoin.detail == null) theBesoin.detail = "";
       theBesoin.isExigence = rs.getInt("isExigence");
       theBesoin.version = rs.getString("version");
       if (theBesoin.version == null) theBesoin.version = "";
       theBesoin.image = rs.getString("image");
       if (theBesoin.image == null) theBesoin.image = "";

       theBesoin.nbNiveau = rs.getInt("nbNiveau");
       theBesoin.niv1 = rs.getInt("niv1");
       theBesoin.niv2 = rs.getInt("niv2");
       theBesoin.niv3 = rs.getInt("niv3");
       theBesoin.niv4 = rs.getInt("niv4");

       theBesoin.niv1_old=theBesoin.niv1;
       theBesoin.niv2_old=theBesoin.niv2;
       theBesoin.niv3_old=theBesoin.niv3;
       theBesoin.niv4_old=theBesoin.niv4;

       if (theBesoin.nbNiveau == 1)
         theBesoin.chapitre = ""+ theBesoin.niv1;
       else if(theBesoin.nbNiveau == 2)
           theBesoin.chapitre = ""+ theBesoin.niv1 + "." +theBesoin.niv2;
       else if(theBesoin.nbNiveau == 3)
           theBesoin.chapitre = ""+ theBesoin.niv1 + "." +theBesoin.niv2+ "." +theBesoin.niv3;
       else if(theBesoin.nbNiveau == 4)
           theBesoin.chapitre = ""+ theBesoin.niv1 + "." +theBesoin.niv2+ "." +theBesoin.niv3+ "." +theBesoin.niv4;

       theBesoin.Complexite = rs.getInt("Complexite");
       theBesoin.Cout = rs.getFloat("Cout");

  
//if (Liste.size() < 2)
{
      Liste.addElement(theBesoin);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }

public Vector readBesoinsUtilisateurMex(){
    Vector Liste = new Vector(10);
     req = "SELECT     BesoinsUtilisateur.id, BesoinsUtilisateur.typeBesoin, BesoinsUtilisateur.nom, BesoinsUtilisateur.description, BesoinsUtilisateur.integDevis, BesoinsUtilisateur.Commentaires, BesoinsUtilisateur.idBesoin, BesoinsUtilisateur.idRoadmap, BesoinsUtilisateur.detail, BesoinsUtilisateur.isExigence, BesoinsUtilisateur.version, BesoinsUtilisateur.image, BesoinsUtilisateur.nbNiveau, BesoinsUtilisateur.niv1, BesoinsUtilisateur.niv2, BesoinsUtilisateur.niv3, BesoinsUtilisateur.niv4, BesoinsUtilisateur.chapitre, BesoinsUtilisateur.Complexite, BesoinsUtilisateur.Cout";
     req+= "    FROM         BesoinsUtilisateur  INNER JOIN  Roadmap ON BesoinsUtilisateur.idRoadmap = Roadmap.idRoadmap";
     req+= "  WHERE (Roadmap.version = 'mex')";

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Besoin theBesoin = new Besoin();
       theBesoin.type = rs.getInt("typeBesoin");
       theBesoin.nom = rs.getString("nom");
       theBesoin.description = rs.getString("description");
       if (theBesoin.description == null) theBesoin.description = "";
       theBesoin.IntegDevis = rs.getInt("integDevis");
       theBesoin.Commentaire = rs.getString("Commentaires");
       if (theBesoin.Commentaire == null) theBesoin.Commentaire = "";
       theBesoin.idBesoin = rs.getString("idBesoin");
       if (theBesoin.idBesoin == null) theBesoin.idBesoin = "";
       theBesoin.idRoadmap = rs.getInt("idRoadmap");
       theBesoin.detail = rs.getString("detail");
       if (theBesoin.detail == null) theBesoin.detail = "";
       theBesoin.isExigence = rs.getInt("isExigence");
       theBesoin.version = rs.getString("version");
       if (theBesoin.version == null) theBesoin.version = "";
       theBesoin.image = rs.getString("image");
       if (theBesoin.image == null) theBesoin.image = "";

       theBesoin.nbNiveau = rs.getInt("nbNiveau");
       theBesoin.niv1 = rs.getInt("niv1");
       theBesoin.niv2 = rs.getInt("niv2");
       theBesoin.niv3 = rs.getInt("niv3");
       theBesoin.niv4 = rs.getInt("niv4");

       theBesoin.niv1_old=theBesoin.niv1;
       theBesoin.niv2_old=theBesoin.niv2;
       theBesoin.niv3_old=theBesoin.niv3;
       theBesoin.niv4_old=theBesoin.niv4;

       if (theBesoin.nbNiveau == 1)
         theBesoin.chapitre = ""+ theBesoin.niv1;
       else if(theBesoin.nbNiveau == 2)
           theBesoin.chapitre = ""+ theBesoin.niv1 + "." +theBesoin.niv2;
       else if(theBesoin.nbNiveau == 3)
           theBesoin.chapitre = ""+ theBesoin.niv1 + "." +theBesoin.niv2+ "." +theBesoin.niv3;
       else if(theBesoin.nbNiveau == 4)
           theBesoin.chapitre = ""+ theBesoin.niv1 + "." +theBesoin.niv2+ "." +theBesoin.niv3+ "." +theBesoin.niv4;

       theBesoin.Complexite = rs.getInt("Complexite");
       theBesoin.Cout = rs.getFloat("Cout");

  
//if (Liste.size() < 2)
{
      Liste.addElement(theBesoin);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeBesoinsUtilisateur(Vector Liste){
       
    
    req = "delete from BesoinsUtilisateur";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        Besoin theBesoin= (Besoin)Liste.elementAt(i);
    String chapitre="";
    if (theBesoin.chapitre != null)
      chapitre=theBesoin.chapitre.replaceAll("\u0092","'").replaceAll("'"," ").replaceAll("&","et").replace('+',' ');
    else
    chapitre="";

  String version="";
  if (theBesoin.version != null)
    version=theBesoin.version.replaceAll("\u0092","'").replaceAll("'"," ").replaceAll("&","et").replace('+',' ');
  else
    version="";

  String description="";
  if (theBesoin.description != null)
    description=theBesoin.description.replaceAll("\u0092","'").replaceAll("'","''");
  else
    description="";

  String detail="";
  if (theBesoin.detail != null)
    detail=theBesoin.detail.replaceAll("\u0092","'").replaceAll("'","''");
  else
    detail="";

  if (theBesoin.nbNiveau == 1)
  {
    theBesoin.chapitre = "" + theBesoin.niv1;
    theBesoin.niv2 = -1;
    theBesoin.niv3 = -1;
    theBesoin.niv4 = -1;
  }
  else if(theBesoin.nbNiveau == 2)
  {
    theBesoin.chapitre = "" + theBesoin.niv1 + "." + theBesoin.niv2;
    theBesoin.niv3 = -1;
    theBesoin.niv4 = -1;
  }
  else if(theBesoin.nbNiveau == 3)
  {
    theBesoin.chapitre = "" + theBesoin.niv1 + "." + theBesoin.niv2 + "." + theBesoin.niv3;
    theBesoin.niv4 = -1;
  }
  else if(theBesoin.nbNiveau == 4)
  {
    theBesoin.chapitre = "" + theBesoin.niv1 + "." + theBesoin.niv2 + "." + theBesoin.niv3 + "." +
        theBesoin.niv4;
  }

  if (theBesoin.Complexite == -1) theBesoin.Complexite = 1;

    req = "INSERT BesoinsUtilisateur (";
    req+="typeBesoin";
    req+=",";
    req+="description";
    req+=",";
    req+="integDevis";
    req+=",";
    req+="idBesoin";
    req+=",";
    req+="idRoadmap";
    req+=",";
    req+="detail";
    req+=",";
    req+="isExigence";
    req+=",";
    req+="version";
    req+=",";
    req+="image";
    req+=",";
    req+="nbNiveau";
    req+=",";
    req+="niv1";
    req+=",";
    req+="niv2";
    req+=",";
    req+="niv3";
    req+=",";
    req+="niv4";
    req+=",";
    req+="chapitre";
    req+=",";
    req+="Complexite";
    req+="   )";
    req+=" VALUES (";
    req+="'"+theBesoin.type+"'";
    req+=",";
    req+="'"+description+"'";
    req+=",";
    req+="'"+theBesoin.IntegDevis+"'";
    req+=",";
    req+="'"+theBesoin.idBesoin+"'";
    req+=",";
    req+="'"+theBesoin.idRoadmap+"'";
    req+=",";
    req+="'"+detail+"'";
    req+=",";
    req+="'"+theBesoin.isExigence+"'";
    req+=",";
    req+="'"+version+"'";
    req+=",";
    req+="'"+theBesoin.image+"'";
    req+=",";
    req+="'"+theBesoin.nbNiveau+"'";
    req+=",";
    req+="'"+theBesoin.niv1+"'";
    req+=",";
    req+="'"+theBesoin.niv2+"'";
    req+=",";
    req+="'"+theBesoin.niv3+"'";
    req+=",";
    req+="'"+theBesoin.niv4+"'";
    req+=",";
    req+="'"+theBesoin.chapitre+"'";
    req+=",";
    req+="'"+theBesoin.Complexite+"'";
    req+=")";       
       

        this.ExecUpdate(req);

    
    } 
   
  } 
   
   public void writeBesoinsUtilisateurMex(Vector Liste){
       

    for (int i=0; i < Liste.size(); i++)
    {
        Besoin theBesoin= (Besoin)Liste.elementAt(i);
    String chapitre="";
    if (theBesoin.chapitre != null)
      chapitre=theBesoin.chapitre.replaceAll("\u0092","'").replaceAll("'"," ").replaceAll("&","et").replace('+',' ');
    else
    chapitre="";

  String version="";
  if (theBesoin.version != null)
    version=theBesoin.version.replaceAll("\u0092","'").replaceAll("'"," ").replaceAll("&","et").replace('+',' ');
  else
    version="";

  String description="";
  if (theBesoin.description != null)
    description=theBesoin.description.replaceAll("\u0092","'").replaceAll("'","''");
  else
    description="";

  String detail="";
  if (theBesoin.detail != null)
    detail=theBesoin.detail.replaceAll("\u0092","'").replaceAll("'","''");
  else
    detail="";

  if (theBesoin.nbNiveau == 1)
  {
    theBesoin.chapitre = "" + theBesoin.niv1;
    theBesoin.niv2 = -1;
    theBesoin.niv3 = -1;
    theBesoin.niv4 = -1;
  }
  else if(theBesoin.nbNiveau == 2)
  {
    theBesoin.chapitre = "" + theBesoin.niv1 + "." + theBesoin.niv2;
    theBesoin.niv3 = -1;
    theBesoin.niv4 = -1;
  }
  else if(theBesoin.nbNiveau == 3)
  {
    theBesoin.chapitre = "" + theBesoin.niv1 + "." + theBesoin.niv2 + "." + theBesoin.niv3;
    theBesoin.niv4 = -1;
  }
  else if(theBesoin.nbNiveau == 4)
  {
    theBesoin.chapitre = "" + theBesoin.niv1 + "." + theBesoin.niv2 + "." + theBesoin.niv3 + "." +
        theBesoin.niv4;
  }

  if (theBesoin.Complexite == -1) theBesoin.Complexite = 1;

    req = "INSERT BesoinsUtilisateur (";
    req+="typeBesoin";
    req+=",";
    req+="description";
    req+=",";
    req+="integDevis";
    req+=",";
    req+="idBesoin";
    req+=",";
    req+="idRoadmap";
    req+=",";
    req+="detail";
    req+=",";
    req+="isExigence";
    req+=",";
    req+="version";
    req+=",";
    req+="image";
    req+=",";
    req+="nbNiveau";
    req+=",";
    req+="niv1";
    req+=",";
    req+="niv2";
    req+=",";
    req+="niv3";
    req+=",";
    req+="niv4";
    req+=",";
    req+="chapitre";
    req+=",";
    req+="Complexite";
    req+="   )";
    req+=" VALUES (";
    req+="'"+theBesoin.type+"'";
    req+=",";
    req+="'"+description+"'";
    req+=",";
    req+="'"+theBesoin.IntegDevis+"'";
    req+=",";
    req+="'"+theBesoin.idBesoin+"'";
    req+=",";
    req+="'"+theBesoin.idRoadmap+"'";
    req+=",";
    req+="'"+detail+"'";
    req+=",";
    req+="'"+theBesoin.isExigence+"'";
    req+=",";
    req+="'"+version+"'";
    req+=",";
    req+="'"+theBesoin.image+"'";
    req+=",";
    req+="'"+theBesoin.nbNiveau+"'";
    req+=",";
    req+="'"+theBesoin.niv1+"'";
    req+=",";
    req+="'"+theBesoin.niv2+"'";
    req+=",";
    req+="'"+theBesoin.niv3+"'";
    req+=",";
    req+="'"+theBesoin.niv4+"'";
    req+=",";
    req+="'"+theBesoin.chapitre+"'";
    req+=",";
    req+="'"+theBesoin.Complexite+"'";
    req+=")";       
       

        this.ExecUpdate(req);

    
    } 
   
  }    
   
  public void updateKeyidRoadmapForBesoinsUtilisateur(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Besoin theBesoin= (Besoin)Liste.elementAt(i);
        int id_Origine = theBesoin.idRoadmap;
        String id_Destination = (String)ListeRoadmap.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomRoadmapById(sourceBd, theBesoin.idRoadmap);
            theBesoin.idRoadmap=getIdByNomRoadmap(Utils.clean(nom));
            ListeRoadmap.put(id_Origine, ""+ theBesoin.idRoadmap);
        }
        else
        {
          theBesoin.idRoadmap = Integer.parseInt(id_Destination);
        }
    }       
  }  
  
public Vector readDevis(int Annee){
    Vector Liste = new Vector(10);
    req="SELECT     Devis.id, Devis.idRoadmap, Devis.Restrictions, Devis.Risques, Devis.Reponse, Devis.idEtat, St.nomSt, Roadmap.version, Devis.description, Devis.dateSoumission, Devis.dateGO_MOA, ";
    req+="                      Devis.dateGO_GOUVERNANCE, Devis.LoginCreateur, Devis.idServiceCreateur, typeEtatDevis.nom as nomEtat ";
    req+=" FROM         Devis INNER JOIN ";
    req+="                       Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN ";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN ";
    req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN ";
    req+="                      typeEtatDevis ON Devis.idEtat = typeEtatDevis.id ";
    req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+") ORDER BY Devis.id";      
    

    this.ExecReq( req);

    try {
      while (rs.next()) {

        Devis theDevis = new Devis(-1);
        
        theDevis.idRoadmap = rs.getInt("idRoadmap");

        theDevis.Restrictions = rs.getString("Restrictions");
        theDevis.Risques = rs.getString("Risques");
        if ((theDevis.Risques == null) ||(theDevis.Risques.equals("null"))) theDevis.Risques="";
        theDevis.Reponse = rs.getString("Reponse");
        if ((theDevis.Reponse == null) ||(theDevis.Reponse.equals("null"))) theDevis.Reponse="";

        theDevis.idEtat = rs.getInt("idEtat");
        theDevis.nomProjet = rs.getString("nomSt") + "-" + rs.getString("version");

        theDevis.description = rs.getString("description");
        if ((theDevis.description == null) ||(theDevis.description.equals("null"))) theDevis.description="";

        theDevis.DdateSoumission = rs.getDate("dateSoumission");
        if (theDevis.DdateSoumission != null)
          theDevis.dateSoumission = ""+theDevis.DdateSoumission.getDate()+"/"+(theDevis.DdateSoumission.getMonth()+1) + "/"+(theDevis.DdateSoumission.getYear() + 1900);
         else
          theDevis.dateSoumission = "";

        theDevis.DdateGO_MOA = rs.getDate("dateGO_MOA");
        if (theDevis.DdateGO_MOA != null)
          theDevis.dateGO_MOA = ""+theDevis.DdateGO_MOA.getDate()+"/"+(theDevis.DdateGO_MOA.getMonth()+1) + "/"+(theDevis.DdateGO_MOA.getYear() + 1900);
         else
          theDevis.dateGO_MOA = "";

        theDevis.DdateGO_GOUVERNANCE = rs.getDate("dateGO_GOUVERNANCE");
        if (theDevis.DdateGO_GOUVERNANCE != null)
          theDevis.dateGO_GOUVERNANCE = ""+theDevis.DdateGO_GOUVERNANCE.getDate()+"/"+(theDevis.DdateGO_GOUVERNANCE.getMonth()+1) + "/"+(theDevis.DdateGO_GOUVERNANCE.getYear() + 1900);
         else
          theDevis.dateGO_GOUVERNANCE = "";

        theDevis.LoginCreateur= rs.getString("LoginCreateur");
        theDevis.idServiceCreateur= rs.getInt("idServiceCreateur");
        theDevis.Etat= rs.getString("nomEtat");

  
//if (Liste.size() < 1)
{
      Liste.addElement(theDevis);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeDevis(Vector Liste){
       
    req = "delete from DevisBudget";
    this.ExecUpdate( req);   
    
    req = "delete from RoadmapPO";
    this.ExecUpdate( req);       
    
    req = "delete from Jalons";
    this.ExecUpdate( req);
    
    req = "delete from Devis";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        Devis theDevis= (Devis)Liste.elementAt(i);
        
    if ((theDevis.DdateSoumission != null))
    {
         theDevis.dateSoumission = "convert(datetime, '"+theDevis.DdateSoumission.getDate() + "/" + (theDevis.DdateSoumission.getMonth() + 1) +"/" + (theDevis.DdateSoumission.getYear() + 1900)+"', 103)";
    }
    else
         theDevis.dateSoumission="null";    
       
    if ((theDevis.DdateGO_MOA != null))
    {
         theDevis.dateGO_MOA = "convert(datetime, '"+theDevis.DdateGO_MOA.getDate() + "/" + (theDevis.DdateGO_MOA.getMonth() + 1) +"/" + (theDevis.DdateSoumission.getYear() + 1900)+"', 103)";
    }
    else
         theDevis.dateGO_MOA="null";  
    
    if ((theDevis.DdateGO_GOUVERNANCE != null))
    {
         theDevis.dateGO_GOUVERNANCE = "convert(datetime, '"+theDevis.DdateGO_GOUVERNANCE.getDate() + "/" + (theDevis.DdateGO_GOUVERNANCE.getMonth() + 1) +"/" + (theDevis.DdateSoumission.getYear() + 1900)+"', 103)";
    }
    else
         theDevis.dateGO_GOUVERNANCE="null";      
     
    
    req = "INSERT Devis (";
    req += "idRoadmap" + ",";
    req += "Restrictions" + ",";
    req += "Risques" + ",";
    req += "Reponse" + ",";
    req += "idEtat" + ",";
    req += "description" + ",";
    req += "dateSoumission" + ",";
    req += "dateGO_MOA" + ",";
    req += "dateGO_GOUVERNANCE" + ",";
    req += "LoginCreateur" + ",";    
    req += "idServiceCreateur";
    req += ")";
    req += " VALUES ";
    req += "(";
    req += "'" +theDevis.idRoadmap+ "',";
    req += "'" +Utils.clean(theDevis.Restrictions)+ "',";
    req += "'" +Utils.clean(theDevis.Risques)+ "',";
    req += "'" +Utils.clean(theDevis.Reponse)+ "',";
    req += "'" +theDevis.idEtat+ "',";
    req += "'" +Utils.clean(theDevis.description)+ "',";
    req += "" +theDevis.dateSoumission+ ",";
    req += "" +theDevis.dateGO_MOA+ ",";
    req += "" +theDevis.dateGO_GOUVERNANCE+ ",";
    req += "'" +theDevis.LoginCreateur+ "',";
    req += "'" +theDevis.idServiceCreateur+ "'";


    req += ")";        
       

        this.ExecUpdate(req);

    
    } 
   
  }
   
  public void updateKeyidRoadmapForDevis(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
    Devis theDevis= (Devis)Liste.elementAt(i);
        int id_Origine = theDevis.idRoadmap;
        String id_Destination = (String)ListeRoadmap.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomRoadmapById(sourceBd, theDevis.idRoadmap);
            theDevis.idRoadmap=getIdByNomRoadmap(Utils.clean(nom));
            ListeRoadmap.put(id_Origine, ""+ theDevis.idRoadmap);
        }
        else
        {
          theDevis.idRoadmap = Integer.parseInt(id_Destination);
        }
    }     
  }    
  
  public void updateKeyidServiceCreateurForDevis(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Devis theDevis= (Devis)Liste.elementAt(i);
        int id_Origine = theDevis.idServiceCreateur;
        String id_Destination = (String)ListeServices.get(id_Origine);
        if (id_Destination == null)
        {
            String nomService=getNomById(sourceBd, theDevis.idServiceCreateur, "Service", "nomService", "idService");
            theDevis.idServiceCreateur=getIdByNom(nomService, "Service", "nomService", "idService");
            ListeServices.put(id_Origine, ""+ theDevis.idServiceCreateur);
        }
        else
        {
          theDevis.idServiceCreateur = Integer.parseInt(id_Destination);
        }
    }     
  }   
  
public Vector readBudgetGlobal(){
    Vector Liste = new Vector(10);
    req = "SELECT     idBudget, Annee, NomProjet, Etat, OpexPrevu, OpexConsomme, CapexPrevu, CapexConsomme FROM  BudgetGlobal ORDER BY id";

    this.ExecReq( req);
    try {
       while (rs.next()) {
       LignePO theLignePO = new LignePO();
        theLignePO.idWebPo= rs.getString("idBudget"); 
        theLignePO.Annee= rs.getString("Annee"); 
        theLignePO.nomProjet= rs.getString("NomProjet"); 
        theLignePO.etat= rs.getString("Etat"); 
        theLignePO.charge= rs.getFloat("OpexPrevu"); 
        theLignePO.chargeConsommee= rs.getFloat("OpexConsomme"); 
        theLignePO.chargeAffecteeForfait= rs.getFloat("CapexPrevu"); 
        theLignePO.chargeConsommeForfait= rs.getFloat("CapexConsomme"); 

//if (Liste.size() < 1)
{
      Liste.addElement(theLignePO);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeBudgetGlobal(Vector Liste){
       
    
    req = "delete from BudgetGlobal";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        LignePO theLignePO= (LignePO)Liste.elementAt(i);
       
       req = "INSERT BudgetGlobal (idBudget, Annee, NomProjet, Etat, OpexPrevu, OpexConsomme, CapexPrevu, CapexConsomme)";
       req+=" VALUES (";
       req+= "'"+theLignePO.idWebPo+"',  ";
       req+= "'"+ theLignePO.Annee+"',";
       req+= "'"+ theLignePO.nomProjet+"',";
       req+= "'"+ theLignePO.etat+"',";
       req+= "'"+ theLignePO.charge+"', ";
       req+= "'"+ theLignePO.chargeConsommee+"', ";
       req+= "'"+ theLignePO.chargeAffecteeForfait+"', ";
       req+= "'"+ theLignePO.chargeConsommeForfait+"' ";

       req+=")";

        this.ExecUpdate(req);

    
    } 
   
  }    
   
public Vector readRoadmapPO(){
    Vector Liste = new Vector(10);
    req = "SELECT RoadmapPO.idRoadmap,RoadmapPO.idPO,  RoadmapPO.Charge, RoadmapPO.Annee,  RoadmapPO.dateTest_devis, RoadmapPO.dateMep_devis";
    req+="     FROM         RoadmapPO ORDER BY id";

    this.ExecReq( req);
    try {
       while (rs.next()) {
       LignePO theLignePO = new LignePO();
        theLignePO.id = rs.getInt("idRoadmap");
        theLignePO.idWebPo = rs.getString("idPO");
        theLignePO.charge = rs.getFloat("Charge");
        theLignePO.Annee = rs.getString("Annee");

        theLignePO.DdateTest_devis = rs.getDate("dateTest_devis");
        theLignePO.DdateProd_devis = rs.getDate("dateMep_devis");

//if (Liste.size() < 1)
{
      Liste.addElement(theLignePO);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeRoadmapPO(Vector Liste){
       
    
    req = "delete from RoadmapPO";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        LignePO theLignePO= (LignePO)Liste.elementAt(i);
        
    if ((theLignePO.DdateTest_devis != null))
    {
         theLignePO.dateTest_devis = "convert(datetime, '"+theLignePO.DdateTest_devis.getDate() + "/" + (theLignePO.DdateTest_devis.getMonth() + 1) +"/" + (theLignePO.DdateTest_devis.getYear() + 1900)+"', 103)";
    }
    else
         theLignePO.dateTest_devis="null";  
    
    if ((theLignePO.DdateProd_devis != null))
    {
         theLignePO.dateProd_devis = "convert(datetime, '"+theLignePO.DdateProd_devis.getDate() + "/" + (theLignePO.DdateProd_devis.getMonth() + 1) +"/" + (theLignePO.DdateProd_devis.getYear() + 1900)+"', 103)";
    }
    else
         theLignePO.dateProd_devis="null";    
        
       
       req = "INSERT RoadmapPO (idRoadmap, idPO,  Charge, Annee,  dateTest_devis, dateMep_devis)";
       req+=" VALUES (";
       req+= "'"+theLignePO.id+"',  ";
       req+= "'"+theLignePO.idWebPo+"',  ";
       req+= "'"+ theLignePO.charge+"',";
       req+= "'"+ theLignePO.Annee+"',";
       req+= ""+ theLignePO.dateTest_devis+",";
       req+= ""+ theLignePO.dateProd_devis+"";

       req+=")";

        this.ExecUpdate(req);

    
    } 
   
  } 
   
  public void updateKeyidRoadmapForRoadmapPO(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        LignePO theLignePO= (LignePO)Liste.elementAt(i);
        int id_Origine = theLignePO.id;
        String id_Destination = (String)ListeRoadmap.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomRoadmapById(sourceBd, theLignePO.id);
            theLignePO.id=getIdByNomRoadmap(Utils.clean(nom));
            ListeRoadmap.put(id_Origine, ""+ theLignePO.id);
        }
        else
        {
          theLignePO.id = Integer.parseInt(id_Destination);
        }
    }    
  }    
  
public Vector readDevisBudget(int Annee){
    Vector Liste = new Vector(10);
    req = "SELECT     DevisBudget.id, DevisBudget.idDevis, DevisBudget.Type, DevisBudget.Annee, DevisBudget.ChargePrelevee, DevisBudget.ChargeDispo, DevisBudget.nomProjet, DevisBudget.Structure, ";
    req+="                      DevisBudget.idPO, DevisBudget.etat";
    req+=" FROM         DevisBudget INNER JOIN";
    req+="                       Devis ON DevisBudget.idDevis = Devis.id INNER JOIN";
    req+="                       Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap   ";
    req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+") ORDER BY DevisBudget.id ";      
    
    this.ExecReq( req);
    try {
       while (rs.next()) {
       LignePO theLignePO = new LignePO();
       theLignePO.idDevis = rs.getInt("idDevis");
       theLignePO.TypeCharge = rs.getString("Type");
       theLignePO.Annee = rs.getString("Annee");
       theLignePO.chargePrelevee = rs.getFloat("ChargePrelevee");
       theLignePO.chargeDisponible = rs.getFloat("ChargeDispo");
       theLignePO.nomProjet = rs.getString("nomProjet");
       theLignePO.service = rs.getString("Structure");
       theLignePO.idWebPo = rs.getString("idPO");
       theLignePO.etat = rs.getString("etat");

//if (Liste.size() < 1)
{
      Liste.addElement(theLignePO);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeDevisBudget(Vector Liste){
       
    
    req = "delete from DevisBudget";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        LignePO theLignePO= (LignePO)Liste.elementAt(i);
               
       
       req = "INSERT DevisBudget (idDevis, Type, Annee, ChargePrelevee, ChargeDispo, nomProjet, Structure, idPO, etat)";
       req+=" VALUES (";
       req+= "'"+theLignePO.idDevis+"',  ";
       req+= "'"+theLignePO.TypeCharge+"',  ";
       req+= "'"+ theLignePO.Annee+"',";
       req+= "'"+ theLignePO.chargePrelevee+"',";
       req+= "'"+ theLignePO.chargeDisponible+"',";
       req+= "'"+ Utils.clean(theLignePO.nomProjet)+"',";
       req+= "'"+ theLignePO.service+"',";
       req+= "'"+ theLignePO.idWebPo+"',";       
       req+= "'"+ theLignePO.etat+"'";

       req+=")";

        this.ExecUpdate(req);

    
    } 
   
  } 
   
 public String getNomDevisById(db sourceBd, int id)
  {
    String nom="";
    req = "SELECT     St.nomSt + '-' + Roadmap.version AS nomProjet";
    req+=" FROM         Devis INNER JOIN";
    req+="                      Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     (Devis.id = "+id+")";
    sourceBd.ExecReq( req);
    try {
       while (sourceBd.rs.next()) {
            nom= sourceBd.rs.getString(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return nom;
  } 
 
   public int getIdByNomDevis(String nom)
  {
    int id=-1;
    req = "SELECT     Devis.id ";
    req+=" FROM         Devis INNER JOIN";
    req+="                      Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     (St.nomSt + '-' + Roadmap.version = '"+nom+"')";
    this.ExecReq( req);
    try {
       while (rs.next()) {
        id= rs.getInt(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return id;
  }     
   
  public void updateKeyidDevisForDevisBudget(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        LignePO theLignePO= (LignePO)Liste.elementAt(i);
        int id_Origine = theLignePO.idDevis;
        String id_Destination = (String)ListeDevis.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomDevisById(sourceBd, theLignePO.idDevis);
            theLignePO.idDevis=getIdByNomDevis(Utils.clean(nom));
            ListeDevis.put(id_Origine, ""+ theLignePO.idDevis);
        }
        else
        {
          theLignePO.idDevis = Integer.parseInt(id_Destination);
        }
    }     
  } 
  
 
  
  public void updateKeyStructureForDevisBudget(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        LignePO theLignePO= (LignePO)Liste.elementAt(i);
        int id_Origine = Integer.parseInt(theLignePO.service);
        String id_Destination = (String)ListeServices.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, Integer.parseInt( theLignePO.service), "Service", "nomService", "idService");
            theLignePO.service=""+ getIdByNom(nom, "Service", "nomService", "idService");
            ListeServices.put(id_Origine, ""+ theLignePO.service);
        }
        else
        {
          theLignePO.service= id_Destination;
        }
    }        
  }  
  
   
 
 public Vector readJalons(int Annee){
    Vector Liste = new Vector(10);
    req = "SELECT     Jalons.Type, Jalons.Jalon, Jalons.Livrable, Jalons.dateJalon, Jalons.Remarques, Jalons.Acteur, Jalons.idJalon";
    req+=" FROM         Jalons INNER JOIN";
    req+="                       Devis ON Jalons.idJalon = Devis.id INNER JOIN";
    req+="                       Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap ";
    req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+") ORDER BY Jalons.id";    
    this.ExecReq( req);
    try {
       while (rs.next()) {
       Jalon theJalon = new Jalon();
       theJalon.type = rs.getInt("Type");
       theJalon.nom = rs.getString("Jalon");
       theJalon.Livrable = rs.getString("Livrable");
       theJalon.date = rs.getDate("dateJalon");
       theJalon.Commentaire = rs.getString("Remarques");
       theJalon.Acteur = rs.getString("Acteur");
       theJalon.id = rs.getInt("idJalon");


//if (Liste.size() < 1)
{
      Liste.addElement(theJalon);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeJalons(Vector Liste){
       
    
    req = "delete from Jalons";
    this.ExecUpdate( req);   

    for (int i=0; i < Liste.size(); i++)
    {
        Jalon theJalon= (Jalon)Liste.elementAt(i);
               
        if (theJalon.date != null)
            theJalon.dateCreation ="convert(datetime, '"+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1)+"/"+(theJalon.date.getYear() +1900)+"', 103)";
        else
            theJalon.dateCreation="null";   
        
       if (theJalon.id == -1) continue;
       req = "INSERT Jalons (Type, Jalon, Livrable, dateJalon, Remarques, Acteur, idJalon)";
       req+=" VALUES (";
       req+= "'"+theJalon.type+"',  ";
       req+= "'"+Utils.clean(theJalon.nom)+"',  ";
       req+= "'"+Utils.clean(theJalon.Livrable)+"',  ";
       req+= ""+ theJalon.dateCreation+",";
       req+= "'"+ Utils.clean(theJalon.Commentaire)+"',";
       req+= "'"+ theJalon.Acteur+"',";   
       req+= "'"+ theJalon.id+"'";

       req+=")";

        this.ExecUpdate(req);

    
    } 
   
  } 
   
  public void updateKeyidDevisForJalons(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Jalon theJalon= (Jalon)Liste.elementAt(i);
        int id_Origine = theJalon.id;
        String id_Destination = (String)ListeDevis.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomDevisById(sourceBd, theJalon.id);
            theJalon.id=getIdByNomDevis(Utils.clean(nom));
            ListeDevis.put(id_Origine, ""+ theJalon.id);
        }
        else
        {
          theJalon.id = Integer.parseInt(id_Destination);
        }
    }     
  }   
  
  public void updateKeyTypeForJalons(db sourceBd,Vector Liste){

    for (int i=0; i < Liste.size(); i++)
    {
        Jalon theJalon= (Jalon)Liste.elementAt(i);
        int id_Origine = theJalon.type;
        String id_Destination = (String)ListetypeJalon.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theJalon.type, "typeJalon", "nom", "id");
            theJalon.type=getIdByNom(nom, "typeJalon", "nom", "id");
            ListetypeJalon.put(id_Origine, ""+ theJalon.type);
        }
        else
        {
          theJalon.type = Integer.parseInt(id_Destination);
        }
         
  } 
  }
  
public Vector readcategorieTest(int Annee){
    Vector Liste = new Vector(10);
    req = "SELECT     categorieTest.id, categorieTest.nom, categorieTest.description, categorieTest.idRoadmap, categorieTest.Ordre, categorieTest.idBesoinUtilisateur";
    req+=" FROM         categorieTest INNER JOIN";
    req+="                       Roadmap ON categorieTest.idRoadmap = Roadmap.idRoadmap ";
    req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+") ORDER BY categorieTest.id";    

    this.ExecReq( req);
    try {
       while (rs.next()) {
       CategorieTest theCategorieTest = new CategorieTest(-1);
       theCategorieTest.nom = rs.getString("nom");
       theCategorieTest.description = rs.getString("description");
       theCategorieTest.idRoadmap = rs.getInt("idRoadmap");
        theCategorieTest.Ordre = rs.getInt("Ordre");

//if (Liste.size() < 1)
{
      Liste.addElement(theCategorieTest);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writecategorieTest(Vector Liste){
       
    
    req = "delete from resultatTest";
    this.ExecUpdate( req);
    
    req = "delete from Campagne";
    this.ExecUpdate( req);    
    
    req = "delete from Tests";
    this.ExecUpdate( req);     
    
    req = "delete from categorieTest";
    this.ExecUpdate( req);      

    for (int i=0; i < Liste.size(); i++)
    {
        CategorieTest theCategorieTest= (CategorieTest)Liste.elementAt(i);
        
           req = "INSERT categorieTest (";
             req+="nom"+",";
             req+="description"+",";
             req+="idRoadmap"+",";
             req+="Ordre"+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+Utils.clean(theCategorieTest.nom)+"',";
             req+="'"+Utils.clean(theCategorieTest.description)+"',";
             req+=""+theCategorieTest.idRoadmap+",";
             req+=""+theCategorieTest.Ordre+"";
           req+=")";

        this.ExecUpdate(req);

    
    } 
   
  } 
   
  public void updateKeyidRoadmapForcategorieTest(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        CategorieTest theCategorieTest= (CategorieTest)Liste.elementAt(i);
        int id_Origine = theCategorieTest.idRoadmap;
        String id_Destination = (String)ListeRoadmap.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomRoadmapById(sourceBd, theCategorieTest.idRoadmap);
            theCategorieTest.idRoadmap=getIdByNomRoadmap(Utils.clean(nom));
            ListeRoadmap.put(id_Origine, ""+ theCategorieTest.idRoadmap);
        }
        else
        {
          theCategorieTest.idRoadmap = Integer.parseInt(id_Destination);
        }
    }     
  }  
  
public Vector readTests(int Annee){
    Vector Liste = new Vector(10);
    req = "SELECT     Tests.id, Tests.nom, Tests.description, Tests.miseEnOeuvre, Tests.idCategorie, Tests.Ordre, Tests.FilenameMiseEnOeuvre";
    req+=" FROM         Tests INNER JOIN";
    req+="                       categorieTest ON Tests.idCategorie = categorieTest.id INNER JOIN";
    req+="                       Roadmap ON categorieTest.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt ";
    req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+") ORDER BY Tests.id";        

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Test theTest = new Test(-1);
       theTest.nom = rs.getString("nom");
       if (theTest.nom == null) theTest.nom = "";
       
       theTest.description = rs.getString("description");
       if ((theTest.description == null) || (theTest.description.equals("null"))) theTest.description = "";
       
        theTest.miseEnOeuvre = rs.getString("miseEnOeuvre");
        if (theTest.miseEnOeuvre == null || (theTest.miseEnOeuvre.equals("null"))) theTest.miseEnOeuvre = "";
        
       theTest.idCategorie = rs.getInt("idCategorie");
        theTest.Ordre = rs.getInt("Ordre");

        String chemin = rs.getString("FilenameMiseEnOeuvre");
       if (chemin != null)
       {
         theTest.FilenameMiseEnOeuvre = new doc();
         theTest.FilenameMiseEnOeuvre.chemin = chemin;
       }        

//if (Liste.size() < 1)
{
      Liste.addElement(theTest);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeTests(Vector Liste){
       
    
    req = "delete from resultatTest";
    this.ExecUpdate( req);
    
    req = "delete from Campagne";
    this.ExecUpdate( req);    
    
    req = "delete from Tests";
    this.ExecUpdate( req);     
      

    for (int i=0; i < Liste.size(); i++)
    {
        Test theTest= (Test)Liste.elementAt(i);
        
    String chemin="";
    if (theTest.FilenameMiseEnOeuvre == null) 
        chemin="";
    else
        chemin = theTest.FilenameMiseEnOeuvre.chemin;        
        
           req = "INSERT Tests (";
             req+="idCategorie"+",";
             req+="nom"+",";
             req+="description"+",";
             req+="miseEnOeuvre"+",";
             req+="Ordre"+",";
             req+="FilenameMiseEnOeuvre";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+=""+theTest.idCategorie+",'";
             req+=Utils.clean(theTest.nom)+"','";
             req+=Utils.clean(theTest.description)+"','";
             req+=Utils.clean(theTest.miseEnOeuvre)+"','";
             req+=theTest.Ordre+"','";
             req+=chemin+"'";

           req+=")";

        this.ExecUpdate(req);

    
    } 
   
  } 
   
 public String getNomCetegorieTestById(db sourceBd, int id)
  {
    String nom="";
    req = "SELECT     St.nomSt + '-' + Roadmap.version + '-' + categorieTest.nom AS nomCategorie";
    req+=" FROM         categorieTest INNER JOIN";
    req+="                       Roadmap ON categorieTest.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     categorieTest.id = "+id;
    sourceBd.ExecReq( req);
    try {
       while (sourceBd.rs.next()) {
            nom= sourceBd.rs.getString(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return nom;
  } 
 
   public int getIdByNomNomCetegorieTest(String nom)
  {
    int id=-1;
    req = "SELECT     categorieTest.id";
    req+=" FROM         categorieTest INNER JOIN";
    req+="                       Roadmap ON categorieTest.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     (St.nomSt + '-' + Roadmap.version + '-' + categorieTest.nom  = '"+nom+"')";
    this.ExecReq( req);
    try {
       while (rs.next()) {
        id= rs.getInt(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return id;
  }      
   
  public void updateKeyidCategorieForTests(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Test theTest= (Test)Liste.elementAt(i);
        int id_Origine = theTest.idCategorie;
        String id_Destination = (String)ListeCategories.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomCetegorieTestById(sourceBd, theTest.idCategorie);
            theTest.idCategorie=getIdByNomNomCetegorieTest(Utils.clean(nom));
            ListeCategories.put(id_Origine, ""+ theTest.idCategorie);
        }
        else
        {
          theTest.idCategorie= Integer.parseInt(id_Destination);
        }
    }     
  } 
  
public Vector readCampagne(int Annee){
    Vector Liste = new Vector(10);
    req = "SELECT     Campagne.date, Campagne.nb_KO, Campagne.nb_AR, Campagne.nb_AF, Campagne.nb_OK, Campagne.nb_AN, Campagne.idRoadmap, Campagne.FileName, Campagne.FileNameIn, Campagne.IA1, ";
    req+="                      Campagne.IA2, Campagne.num, Campagne.version, Campagne.idCampagneLiee, Campagne.isNonReg";
    req+=" FROM         Campagne INNER JOIN";
    req+="                       Roadmap ON Campagne.idRoadmap = Roadmap.idRoadmap ";
    req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+") ORDER BY Campagne.id";     

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Campagne theCampagne = new Campagne(-1);
       theCampagne.Ddate = rs.getDate("date");  
       theCampagne.nb_KO = rs.getInt("nb_KO");
       theCampagne.nb_AR = rs.getInt("nb_AR");
       theCampagne.nb_AF =rs.getInt("nb_AF");
       theCampagne.nb_OK = rs.getInt("nb_OK");
       theCampagne.nb_AN = rs.getInt("nb_AN");
       theCampagne.idRoadmap = rs.getInt("idRoadmap");
       theCampagne.FileName = rs.getString("FileName");
       if (theCampagne.FileName == null) theCampagne.FileName = "";
       theCampagne.FileNameIn = rs.getString("FileNameIn");
       if (theCampagne.FileNameIn == null) theCampagne.FileNameIn = "";
       theCampagne.IA1 = rs.getFloat("IA1");
       theCampagne.IA2 = rs.getFloat("IA2");
       theCampagne.num = rs.getInt("num");
       theCampagne.version = rs.getString("version");
       if (theCampagne.version == null) theCampagne.version = "";

       theCampagne.idCampagneLiee = rs.getInt("idCampagneLiee");
       theCampagne.isNonReg = rs.getInt("isNonReg");   

//if (Liste.size() < 1)
{
      Liste.addElement(theCampagne);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeCampagne(Vector Liste){
       
    
    req = "delete from resultatTest";
    this.ExecUpdate( req);
    
    req = "delete from Campagne";
    this.ExecUpdate( req);       
      

    for (int i=0; i < Liste.size(); i++)
    {
        Campagne theCampagne= (Campagne)Liste.elementAt(i);  
        
        if (theCampagne.Ddate != null)
            theCampagne.date ="convert(datetime, '"+theCampagne.Ddate.getDate()+"/"+(theCampagne.Ddate.getMonth()+1)+"/"+(theCampagne.Ddate.getYear() +1900)+"', 103)";
        else
            theCampagne.date="null";         
        
           req = "INSERT Campagne (";
             req+="date"+",";
             req+="version"+",";
             req+="nb_KO"+",";
             req+="nb_AR"+",";
             req+="nb_AF"+",";
             req+="nb_OK"+",";
             req+="nb_AN"+",";
             req+="idRoadmap"+",";
             req+="FileName"+",";
             req+="FileNameIn"+",";
             req+="IA1"+",";
             req+="IA2"+",";
             req+="num"+",";
             req+="idCampagneLiee"+",";
             req+="isNonReg"+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+=""+theCampagne.date+",";
             req+="'"+ Utils.clean(theCampagne.version)+"',";
             req+="'"+theCampagne.nb_KO+"',";
             req+="'"+theCampagne.nb_AR+"',";
             req+="'"+theCampagne.nb_AF+"',";
             req+="'"+theCampagne.nb_OK+"',";
             req+="'"+theCampagne.nb_AN+"',";
             req+="'"+theCampagne.idRoadmap+"',";
             if ((theCampagne.FileName == null) || (theCampagne.FileName.equals("")))
               req+="NULL"+",";
             else
               req+="'"+theCampagne.FileName+"',";
             if ((theCampagne.FileNameIn == null) || (theCampagne.FileNameIn.equals("")))
               req+="NULL"+",";
             else
               req+="'"+theCampagne.FileNameIn+"',";
             req+="'"+theCampagne.IA1+"',";
             req+="'"+theCampagne.IA2+"',";
             req+="'"+theCampagne.num+"',";
             req+="'"+theCampagne.idCampagneLiee+"',";
             req+="'"+theCampagne.isNonReg+"'";
           req+=")";

        this.ExecUpdate(req);

    
    } 
   
  }  
  public void updateKeyidRoadmapForCampagne(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Campagne theCampagne= (Campagne)Liste.elementAt(i);  ;
        int id_Origine = theCampagne.idRoadmap;
        String id_Destination = (String)ListeRoadmap.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomRoadmapById(sourceBd, theCampagne.idRoadmap);
            theCampagne.idRoadmap=getIdByNomRoadmap(Utils.clean(nom));
            ListeRoadmap.put(id_Origine, ""+ theCampagne.idRoadmap);
        }
        else
        {
          theCampagne.idRoadmap = Integer.parseInt(id_Destination);
        }
    }      
  }   
  
public Vector readresultatTest(int Annee){
    Vector Liste = new Vector(10);
    req = "SELECT     resultatTest.idEtat, resultatTest.commentaire, resultatTest.anomalie, resultatTest.intervenant, ";
    req+=" resultatTest.idTest, resultatTest.idCampagne, resultatTest.Filename";
    req+=" FROM         resultatTest INNER JOIN";
    req+="                       Tests ON resultatTest.idTest = Tests.id INNER JOIN";
    req+="                       Campagne ON resultatTest.idCampagne = Campagne.id INNER JOIN";
    req+="                       Roadmap ON Campagne.idRoadmap = Roadmap.idRoadmap ";
    req+=" WHERE (YEAR(Roadmap.dateMep) >= "+(Annee+1)+") ORDER BY resultatTest.id"; 
    //req+=" Where idCampagne=977";

    this.ExecReq( req);
    try {
       while (rs.next()) {
            Test theTest = new Test(-1);
            theTest.idEtat= rs.getInt("idEtat");
            theTest.commentaire= rs.getString("commentaire");
            theTest.anomalie= rs.getInt("anomalie");
            theTest.intervenant= rs.getInt("intervenant");
            theTest.id= rs.getInt("idTest");
            theTest.idCampagne= rs.getInt("idCampagne");
            theTest.Filename= rs.getString("Filename");

//if (Liste.size() < 1)
{
      Liste.addElement(theTest);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeresultatTest(Vector Liste){
       
    
    req = "delete from resultatTest";
    this.ExecUpdate( req);    
      

    for (int i=0; i < Liste.size(); i++)
    {
        Test theTest= (Test)Liste.elementAt(i);  
            
        
           req = "INSERT resultatTest (";
             req+="idEtat"+",";
             req+="commentaire"+",";
             req+="anomalie"+",";
             req+="intervenant"+",";
             req+="idTest"+",";
             req+="idCampagne"+",";
             req+="Filename"+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+theTest.idEtat+"'";
             req+=",";
             req+="'"+Utils.clean(theTest.commentaire)+"'";
             req+=",";
             req+="'"+theTest.anomalie+"'";
             req+=",";
             req+="'"+theTest.intervenant+"'";
             req+=",";
             req+="'"+theTest.id+"'";
             req+=",";
             req+="'"+theTest.idCampagne+"'";
             req+=",";
             req+="'"+theTest.Filename+"'";
           req+=")";

        this.ExecUpdate(req);

    
    } 
   
  }
   
 public String getNomTestById(db sourceBd, int id)
  {
    String nom="";
    req = "SELECT     St.nomSt + '-' + Roadmap.version + '-' + categorieTest.nom + '-' + Tests.nom AS nom";
    req+=" FROM         Tests INNER JOIN";
    req+="                       categorieTest ON Tests.idCategorie = categorieTest.id INNER JOIN";
    req+="                       Roadmap ON categorieTest.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     Tests.id = " + id;
    sourceBd.ExecReq( req);
    try {
       while (sourceBd.rs.next()) {
            nom= sourceBd.rs.getString(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return nom;
  } 
 
   public int getIdByNomTest(String nom)
  {
    int id=-1;
    req = "SELECT     Tests.id";
    req+=" FROM         Tests INNER JOIN";
    req+="                       categorieTest ON Tests.idCategorie = categorieTest.id INNER JOIN";
    req+="                       Roadmap ON categorieTest.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     (St.nomSt + '-' + Roadmap.version + '-' + categorieTest.nom + '-' + Tests.nom = '"+nom+"')";
    this.ExecReq( req);
    try {
       while (rs.next()) {
        id= rs.getInt(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return id;
  }  
   
 public String getNomCampagneById(db sourceBd, int id)
  {
    String nom="";
    req = "SELECT     St.nomSt + '-' + Roadmap.version + '-' + Campagne.version AS nom, Campagne.id";
    req+=" FROM         Campagne INNER JOIN";
    req+="                       Roadmap ON Campagne.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     Campagne.id = " + id;
    sourceBd.ExecReq( req);
    try {
       while (sourceBd.rs.next()) {
            nom= sourceBd.rs.getString(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return nom;
  } 
 
   public int getIdByNomCampagne(String nom)
  {
    int id=-1;
    req = "SELECT     Campagne.id";
    req+=" FROM         Campagne INNER JOIN";
    req+="                       Roadmap ON Campagne.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     (St.nomSt + '-' + Roadmap.version + '-' + Campagne.version = '"+nom+"')";
    this.ExecReq( req);
    try {
       while (rs.next()) {
        id= rs.getInt(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return id;
  }
   
  public void updateKeyidTestForresultatTest(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Test theTest= (Test)Liste.elementAt(i); 
        int id_Origine = theTest.id;
        String id_Destination = (String)ListeTests.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomTestById(sourceBd, theTest.id);
            theTest.id=getIdByNomTest(Utils.clean(nom));
            ListeTests.put(id_Origine, ""+ theTest.id);
        }
        else
        {
          theTest.id = Integer.parseInt(id_Destination);
        }
    }    
    
  }  
  
  
  public void updateKeyidCampagneForresultatTest(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Test theTest= (Test)Liste.elementAt(i); 
        int id_Origine = theTest.idCampagne;
        String id_Destination = (String)ListeCampagnes.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomCampagneById(sourceBd, theTest.idCampagne);
            theTest.idCampagne=getIdByNomCampagne(Utils.clean(nom));
            ListeCampagnes.put(id_Origine, ""+ theTest.idCampagne);
        }
        else
        {
          theTest.idCampagne = Integer.parseInt(id_Destination);
        }
    }      
  }   
  
  public void updateKeyidIntervenantForresultatTest(db sourceBd,Vector Liste){

    for (int i=0; i < Liste.size(); i++)
    {
        Test theTest= (Test)Liste.elementAt(i); 
        int id_Origine = theTest.intervenant;
        String id_Destination = (String)ListeMembres.get(id_Origine);
        if (id_Destination == null)
        {
            String nomMembre=getNomById(sourceBd, theTest.intervenant, "Membre ", "LoginMembre", "idMembre");
            theTest.intervenant=getIdByNom(nomMembre, "Membre ", "LoginMembre", "idMembre");
            ListeMembres.put(id_Origine, ""+ theTest.intervenant);
        }
        else
        {
          theTest.intervenant = Integer.parseInt(id_Destination);
        }
    }      
    
    
  } 
  
public Vector readProcessus(){
    Vector Liste = new Vector(10);
    req = "SELECT  nomProcessus, descProcessus, ProcessusSI, ProcessusMacroSt, ProcessusAuteur, respMOA, lienImage, disposition, ProcessusCreateur, ProcessusModificateur,  Type FROM         Processus ORDER BY idProcessus";

    this.ExecReq( req);
    try {
       while (rs.next()) {
       Processus theProcessus = new Processus(-1);
       
            theProcessus.nom = rs.getString("nomProcessus");
            theProcessus.desc = rs.getString("descProcessus");
            theProcessus.idSi = rs.getString("ProcessusSI");
            theProcessus.idMacroSt = rs.getString("ProcessusMacroSt");  
            theProcessus.idAuteur = rs.getString("ProcessusAuteur");
            String respMOA = rs.getString("respMOA");
            if ((respMOA!=null) && (!respMOA.equals("null"))) theProcessus.respMOA=respMOA;else theProcessus.respMOA="";
            theProcessus.lienImage = rs.getString("lienImage");
            theProcessus.disposition  = rs.getString("disposition");
            theProcessus.createur = rs.getString("ProcessusCreateur");
            theProcessus.modificateur = rs.getString("ProcessusModificateur");
            theProcessus.Type = rs.getString("Type");

            if ((theProcessus.lienImage!=null) && (!theProcessus.lienImage.equals("null"))) theProcessus.lienImage=theProcessus.lienImage;else theProcessus.lienImage="";


//if (Liste.size() < 1)
{
      Liste.addElement(theProcessus);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeProcessus(Vector Liste){
       
    
    req = "delete from Activite";
    this.ExecUpdate( req);
    
    req = "delete from Phase";
    this.ExecUpdate( req);     
    
    req = "delete from Processus";
    this.ExecUpdate( req);       
      

    for (int i=0; i < Liste.size(); i++)
    {
        Processus theProcessus= (Processus)Liste.elementAt(i);  
        
        if (theProcessus.createur == null) theProcessus.createur = "";
        if (theProcessus.modificateur == null) theProcessus.modificateur = "";
                 
        req = "INSERT INTO Processus ";
        req+="(nomProcessus, descProcessus, ProcessusSI, ProcessusMacroSt, ProcessusAuteur, ProcessusCreateur, ProcessusModificateur, respMOA,lienImage,disposition,Type) VALUES  (";
        req+="'"+Utils.clean(theProcessus.nom)+"'";
        req+=",'"+Utils.clean(theProcessus.desc)+"'";;
        req+=","+theProcessus.idSi+"";
        req+=","+theProcessus.idMacroSt+"";
        req+=","+theProcessus.idAuteur+"";
        req+=",'"+Utils.clean(theProcessus.createur)+"'";
        req+=",'"+Utils.clean(theProcessus.modificateur)+"'";
        req+=",'"+Utils.clean(theProcessus.respMOA)+"'";
        req+=",'"+Utils.clean(theProcessus.lienImage)+"'";
        req+=",'"+theProcessus.disposition+"'";
        req+=",'"+theProcessus.Type+"'";
        req+=")";

        this.ExecUpdate(req);

    
    } 
   
  } 
   
  public void updateKeyProcessusSIFForProcessus(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Processus theProcessus= (Processus)Liste.elementAt(i);  
        int id_Origine = Integer.parseInt(theProcessus.idSi);
        String id_Destination = (String)ListeSi.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, Integer.parseInt(theProcessus.idSi), "SI ", "nomSI", "idSI");
            theProcessus.idSi= ""+ getIdByNom(nom, "SI ", "nomSI", "idSI");
            ListeSi.put(id_Origine, ""+ theProcessus.idSi);
        }
        else
        {
          theProcessus.idSi = id_Destination;
        }
    }    
  }   
  
  public void updateKeyProcessusMacroStProcessus(db sourceBd,Vector Liste){

    
    for (int i=0; i < Liste.size(); i++)
    {
        Processus theProcessus= (Processus)Liste.elementAt(i); 
        int id_Origine = Integer.parseInt(theProcessus.idMacroSt);
        String id_Destination = (String)ListeMacroSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, Integer.parseInt(theProcessus.idMacroSt), "MacroSt ", "nomMacrost", "idMacrost");
            theProcessus.idMacroSt= ""+ getIdByNom(nom, "MacroSt ", "nomMacrost", "idMacrost");
            ListeMacroSt.put(id_Origine, ""+ theProcessus.idMacroSt);
        }
        else
        {
         theProcessus.idMacroSt = id_Destination;
        }
    }     
  }     
  
  public void updateKeyProcessusAuteurForProcessus(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
       Processus theProcessus= (Processus)Liste.elementAt(i); 
       if (theProcessus.idAuteur == null) continue;
        int id_Origine = Integer.parseInt(theProcessus.idAuteur);
        String id_Destination = (String)ListeMembres.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, Integer.parseInt(theProcessus.idAuteur), "Membre ", "LoginMembre", "idMembre");
            theProcessus.idAuteur= ""+ getIdByNom(nom, "Membre ", "LoginMembre", "idMembre");
            ListeMembres.put(id_Origine, ""+ theProcessus.idAuteur);
        }
        else
        {
          theProcessus.idAuteur = id_Destination;
        }
    }     
  }
  
public Vector readPhase(){
    Vector Liste = new Vector(10);
    req = "SELECT     nomPhaseNP, descPhaseNP, significationPhaseNP, scope, siPhaseNp, PhaseProcessus, Ordre";
    req += " FROM         Phase INNER JOIN";
    req += "                      Processus ON Phase.PhaseProcessus = Processus.idProcessus ORDER BY Phase.idPhaseNP";

    this.ExecReq( req);
    try {
       while (rs.next()) {
           /*
       Phase thePhase = new Phase(-1);
       
            thePhase.nom = rs.getString("nomPhaseNP");
            if (thePhase.nom == null) this.nom="";
            thePhase.desc = rs.getString("descPhaseNP");
            if (thePhase.desc == null) thePhase.desc="";
            thePhase.idProcessus = rs.getInt("PhaseProcessus");
            thePhase.id = rs.getInt("siPhaseNp");
            thePhase.ordre = rs.getInt("Ordre");
*/

//if (Liste.size() < 1)
{
      //Liste.addElement(thePhase);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writePhase(Vector Liste){
       
    
    req = "delete from Activite";
    this.ExecUpdate( req);
    
    req = "delete from Phase";
    this.ExecUpdate( req);     
         

    for (int i=0; i < Liste.size(); i++)
    {
        /*
        Phase thePhase= (Phase)Liste.elementAt(i);  
        
                 
        req = "INSERT INTO Phase ";
        req+=" (nomPhaseNP, descPhaseNP,  siPhaseNp, PhaseProcessus, Ordre) VALUES  (";
        req+="'"+thePhase.nom.replaceAll("'", "''")+"'";
        req+=",'"+thePhase.desc.replaceAll("'", "''")+"'";
        req+=","+thePhase.id+"";
        req+=","+thePhase.idProcessus+"";
        req+=","+thePhase.ordre+"";
        req+=")";

        this.ExecUpdate(req);

    */
    } 
   
  } 

  public void updateKeysiPhaseNpForPhase(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        /*
        Phase thePhase= (Phase)Liste.elementAt(i);
        int id_Origine = thePhase.id;
        String id_Destination = (String)ListeSi.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, thePhase.id, "SI ", "nomSI", "idSI");
            thePhase.id=  getIdByNom(nom, "SI ", "nomSI", "idSI");
            ListeSi.put(id_Origine, ""+ thePhase.id);
        }
        else
        {
          thePhase.id = Integer.parseInt(id_Destination);
        }
                */
    }    
  } 
  
  public void updateKeyPhaseProcessusForPhase(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        /*
        Phase thePhase= (Phase)Liste.elementAt(i);
        int id_Origine = thePhase.idProcessus;
        String id_Destination = (String)ListeProcessus.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, thePhase.idProcessus, "Processus ", "nomProcessus", "idProcessus");
            thePhase.idProcessus= getIdByNom(nom,  "Processus ", "nomProcessus", "idProcessus");
            ListeProcessus.put(id_Origine, ""+ thePhase.idProcessus);
        }
        else
        {
          thePhase.idProcessus = Integer.parseInt(id_Destination);
        }
                */
    }     
  } 
  
public Vector readActivite(){
    Vector Liste = new Vector(10);
    req = "SELECT     Activite.nomActivite, Activite.descActivite, Activite.donneesEntree, Activite.donneesSortie, Activite.ActivitePhase, Activite.Ligne, ";
    req+=" Activite.ActiviteActeur, Activite.numActeur, Activite.previousActivite, ";
    req+=" Activite.ListeST, Activite.Timing, Activite.Criticite, Activite.ActiviteProcessus, Activite.duree, Activite.TypeActeur, ";
    req+=" Activite.TypeActivite, Activite.TypeUml, Activite.ListeIdSt, Activite.TypeIntervenant, ";
    req+="                      Activite.duree_heures, Activite.duree_minutes";
    req+=" FROM         Activite INNER JOIN";
    req+="                       Phase ON Activite.ActivitePhase = Phase.idPhaseNP INNER JOIN";
    req+="                       Processus ON Phase.PhaseProcessus = Processus.idProcessus ORDER BY Activite.idActivite";
    this.ExecReq( req);
    try {
       while (rs.next()) {
       Activite theActivite= new Activite(-1);
       
        theActivite.nom = rs.getString("nomActivite");
        theActivite.desc = rs.getString("descActivite");
        theActivite.donneeEntree = rs.getString("donneesEntree");
        theActivite.donneeSortie = rs.getString("donneesSortie");
        theActivite.idPhase = rs.getInt("ActivitePhase");
        theActivite.numActivite = rs.getInt("Ligne");
        theActivite.idActeur = rs.getInt("ActiviteActeur");
        theActivite.numActeur = rs.getInt("numActeur");
        theActivite.previousActivite = rs.getString("previousActivite");
        theActivite.Timing = rs.getString("Timing");
        theActivite.Criticite = rs.getString("Criticite");
        theActivite.ActiviteProcessus = rs.getInt("ActiviteProcessus");
        theActivite.duree = rs.getInt("duree");
        theActivite.TypeActeur = rs.getString("TypeActeur");
        theActivite.TypeActivite = rs.getString("TypeActivite");
        theActivite.TypeUML = rs.getString("TypeUml");
        theActivite.idSt=rs.getString("ListeIdSt");
    
        theActivite.TypeIntervenant = rs.getString("TypeIntervenant");
        theActivite.duree_heures = rs.getInt("duree_heures");
        theActivite.duree_minutes = rs.getInt("duree_minutes");


//if (Liste.size() < 1)
{
      Liste.addElement(theActivite);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeActivite(Vector Liste){
       
    
    req = "delete from Activite";
    this.ExecUpdate( req);
    

    for (int i=0; i < Liste.size(); i++)
    {
        Activite theActivite= (Activite)Liste.elementAt(i);  
  
    req = "INSERT INTO Activite ";
    req+="(nomActivite, descActivite, donneesSortie, ActivitePhase, Ligne, ActiviteActeur, numActeur, previousActivite, ListeST,Timing,Criticite,duree,ActiviteProcessus,TypeActeur,TypeIntervenant,TypeActivite,TypeUml,ListeIdSt,duree_heures,duree_minutes) VALUES  (";
    req+="'"+ Utils.clean(theActivite.nom)+"'";
    req+=",'"+theActivite.desc.replaceAll("'", "''")+"'";
    req+=",'"+Utils.clean(theActivite.donneeSortie)+"'";
    req+=","+theActivite.idPhase+"";
    req+=","+theActivite.numActivite+"";
    req+=","+theActivite.idActeur+"";
    //req+=","+theActivite.numActeur+"";
    req+=","+theActivite.numActeur+"";
    req+=",'"+Utils.clean(theActivite.previousActivite)+"'";
    req+=",'"+""+"'";
    req+=",'"+Utils.clean(theActivite.Timing)+"'";
    req+=",'"+theActivite.Criticite+"'";
    req+=","+theActivite.duree+"";
    req+=","+theActivite.ActiviteProcessus+"";
     req+=",'"+theActivite.TypeActeur+"'";
     req+=",'"+theActivite.TypeIntervenant+"'";
     req+=",'"+theActivite.TypeActivite+"'";
     req+=",'"+theActivite.TypeUML+"'";
     req+=","+theActivite.idSt+"";
     req+=","+theActivite.duree_heures+"";
     req+=","+theActivite.duree_minutes+"";
     req+=")";

     this.ExecUpdate(req);

    
    } 
   
  } 

  public void updateKeyActivitePhaseForActivite(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)    
    {
        Activite theActivite= (Activite)Liste.elementAt(i);  
        int id_Origine = theActivite.idPhase;
        String id_Destination = (String)ListePhases.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theActivite.idPhase, "Phase ", "nomPhaseNP", "idPhaseNP");
            theActivite.idPhase=  getIdByNom(nom, "Phase ", "nomPhaseNP", "idPhaseNP");
            ListePhases.put(id_Origine, ""+ theActivite.idPhase);
        }
        else
        {
          theActivite.idPhase = Integer.parseInt(id_Destination);
        }
    }   
    
  }   
  public void updateKeyActiviteProcessusForActivite(db sourceBd,Vector Liste){

    
    for (int i=0; i < Liste.size(); i++)
    {
       Activite theActivite= (Activite)Liste.elementAt(i);  
        int id_Origine = theActivite.ActiviteProcessus;
        String id_Destination = (String)ListeProcessus.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theActivite.ActiviteProcessus, "Processus ", "nomProcessus", "idProcessus");
            theActivite.ActiviteProcessus=  getIdByNom(nom, "Processus ", "nomProcessus", "idProcessus");
            ListeProcessus.put(id_Origine, ""+ theActivite.ActiviteProcessus);
        }
        else
        {
          theActivite.ActiviteProcessus = Integer.parseInt(id_Destination);
        }
    }    
  }   
  public void updateKeyActiviteActeurForActivite(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Activite theActivite= (Activite)Liste.elementAt(i); 
        int id_Origine = theActivite.idActeur;
        String id_Destination = (String)ListeSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theActivite.idActeur, "St ", "nomSt", "idSt");
            theActivite.idActeur=  getIdByNom(nom,"St ", "nomSt", "idSt");
            ListeSt.put(id_Origine, ""+ theActivite.idActeur);
        }
        else
        {
          theActivite.idActeur = Integer.parseInt(id_Destination);
        }
    }    
  }   
  public void updateKeyListeStForActivite(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Activite theActivite= (Activite)Liste.elementAt(i); 
        if (theActivite.idSt == null) continue;
        int id_Origine = Integer.parseInt(theActivite.idSt);
        String id_Destination = (String)ListeSt.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, Integer.parseInt(theActivite.idSt), "St ", "nomSt", "idSt");
            theActivite.idSt=  ""+ getIdByNom(nom, "St ", "nomSt", "idSt");
            ListeSt.put(id_Origine, ""+ theActivite.idSt);
        }
        else
        {
          theActivite.idSt = id_Destination;
        }
    }    
  }
  
public Vector readactionSuivi(int Annee){
    Vector Liste = new Vector(10);
    req = "SELECT     actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, ";
    req+= " actionSuivi.dateFin, actionSuivi.type, actionSuivi.reponse, actionSuivi.isPlanning, ";
    req+="  actionSuivi.doc, actionSuivi.Code, actionSuivi.ChargePrevue, actionSuivi.ChargeDevis, actionSuivi.Semaine, ";
    req+=" actionSuivi.RAF, actionSuivi.LoginEmetteur, actionSuivi.priorite, actionSuivi.dateCloture,";
    req+=" actionSuivi.idRoadmapLie, actionSuivi.dateAction_init, actionSuivi.dateFin_init";
    req+=" FROM         actionSuivi INNER JOIN";
    req+="                       Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap ";
    req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+") ORDER BY actionSuivi.id";      
    this.ExecReq( req);
    try {
       while (rs.next()) {
       Action theAction = new Action(-1);
       
          theAction.nom = rs.getString("nom");

          theAction.acteur = rs.getString("acteur");
          theAction.idEtat = rs.getInt("idEtat");
          theAction.idRoadmap = rs.getInt("idRoadmap");        
          theAction.Ddate_start = rs.getDate("dateAction");
          theAction.Ddate_end = rs.getDate("dateFin");
          
          theAction.Type = rs.getString("type");
          theAction.reponse = rs.getString("reponse");
          if (theAction.reponse != null)
            theAction.reponse = theAction.reponse;
          else
            theAction.reponse = "";
          theAction.isPlanning = rs.getInt("isPlanning");
          theAction.doc = rs.getString("doc");
          if (theAction.doc == null) theAction.doc = "";
          theAction.Code = rs.getString("Code");
          if (theAction.Code == null) theAction.Code = "";

          theAction.ChargePrevue=rs.getFloat("ChargePrevue");
          theAction.ChargeDevis=rs.getFloat("ChargeDevis");
          theAction.Semaine=rs.getInt("Semaine");
          theAction.RAF=rs.getFloat("RAF");
          theAction.LoginEmetteur = rs.getString("LoginEmetteur");
          theAction.priorite=rs.getInt("priorite");
          theAction.DdateCloture = rs.getDate("dateCloture");
          theAction.idRoadmapLie = rs.getInt("idRoadmapLie");
          theAction.Ddate_start_init = rs.getDate("dateAction_init");
          theAction.Ddate_end_init = rs.getDate("dateFin_init");


//if (Liste.size() < 1)
{
      Liste.addElement(theAction);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeactionSuivi(Vector Liste){

    req = "delete from TachesDependances";
    this.ExecUpdate( req);
    
    req = "delete from ActionAnomalie";
    this.ExecUpdate( req);
    
    req = "delete from Consomme";
    this.ExecUpdate( req); 
    
    req = "delete from actionSuivi";
    this.ExecUpdate( req);
    

    for (int i=0; i < Liste.size(); i++)
    {
        Action theAction= (Action)Liste.elementAt(i);  
        
        if (theAction.Ddate_start != null)
            theAction.date_start ="convert(datetime, '"+theAction.Ddate_start.getDate()+"/"+(theAction.Ddate_start.getMonth()+1)+"/"+(theAction.Ddate_start.getYear() +1900)+"', 103)";
        else
            theAction.date_start="null";  
        
        if (theAction.Ddate_end != null)
            theAction.date_end ="convert(datetime, '"+theAction.Ddate_end.getDate()+"/"+(theAction.Ddate_end.getMonth()+1)+"/"+(theAction.Ddate_end.getYear() +1900)+"', 103)";
        else
            theAction.date_end="null";   
        
        if (theAction.Ddate_start_init != null)
            theAction.date_start_init ="convert(datetime, '"+theAction.Ddate_start_init.getDate()+"/"+(theAction.Ddate_start_init.getMonth()+1)+"/"+(theAction.Ddate_start_init.getYear() +1900)+"', 103)";
        else
            theAction.date_start_init="null";       
        
        if (theAction.Ddate_end_init != null)
            theAction.date_end_init ="convert(datetime, '"+theAction.Ddate_end_init.getDate()+"/"+(theAction.Ddate_end_init.getMonth()+1)+"/"+(theAction.Ddate_end_init.getYear() +1900)+"', 103)";
        else
            theAction.date_end_init="null";  
        
        if (theAction.DdateCloture!= null)
            theAction.dateCloture ="convert(datetime, '"+theAction.DdateCloture.getDate()+"/"+(theAction.DdateCloture.getMonth()+1)+"/"+(theAction.DdateCloture.getYear() +1900)+"', 103)";
        else
            theAction.dateCloture="null";        
        
  
    req = "INSERT INTO [actionSuivi]";
    req+= "           (";
    req+= "             [nom]";
    req+= "            ,[acteur]";
    req+= "            ,[idEtat]";
    req+= "            ,[idRoadmap]";
    req+= "            ,[dateAction]";
    req+= "            ,[dateFin]";
    req+= "            ,[type]";
    req+= "            ,[reponse]";
    req+= "            ,[isPlanning]";
    req+= "            ,[doc]";
    req+= "            ,[Code]";
    req+= "            ,[ChargePrevue]";
    req+= "            ,[Semaine]";
    req+= "            ,[RAF]";    
    req+= "            ,[LoginEmetteur]";   
    req+= "            ,[priorite]";   
    req+= "            ,[dateCloture]";   
    req+= "            ,[idRoadmapLie]";   
    req+= "            ,[dateAction_init]";       
    req+= "            ,[dateFin_init]";      
    req+= "            )";    
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+ Utils.clean(theAction.nom) + "',";
    req+= "'"+theAction.acteur+"'" + ",";
    req+= "'"+theAction.idEtat+"'" + ",";
    req+= "'"+theAction.idRoadmap+"'" + ",";
    req+= ""+theAction.date_start+"" + ",";
    req+= ""+theAction.date_end+"" + ",";
    req+= "'"+theAction.Type+"'" + ",";
    req+= "'"+Utils.clean(theAction.reponse)+"'" + ",";
    req+= "'"+theAction.isPlanning+"'" + ",";
    req+= "'"+theAction.doc+"'" + ",";
    req+= "'"+theAction.Code+"'" + ",";
    req+= "'"+theAction.ChargePrevue+"'" + ",";
    req+= "'"+theAction.Semaine+"'" + ",";
    req+= "'"+theAction.RAF+"'" + ",";
    req+= "'"+theAction.LoginEmetteur+"'" + ",";
    req+= "'"+theAction.priorite+"'" + ",";
    req+= ""+theAction.dateCloture+"" + ",";
    req+= "'"+theAction.idRoadmapLie+"'" + ",";
    req+= ""+theAction.date_start_init+"" + ",";
    req+= ""+theAction.date_end_init+"" + "";

    req+= "            )";

     this.ExecUpdate(req);

    
    } 
   
  } 

  public void updateKeyidRoadmapForactionSuivi(db sourceBd,Vector Liste){

    
    for (int i=0; i < Liste.size(); i++)
    {
        Action theAction= (Action)Liste.elementAt(i);
        int id_Origine = theAction.idRoadmap;
        String id_Destination = (String)ListeRoadmap.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomRoadmapById(sourceBd, theAction.idRoadmap);
            theAction.idRoadmap=getIdByNomRoadmap(Utils.clean(nom));
            ListeRoadmap.put(id_Origine, ""+ theAction.idRoadmap);
        }
        else
        {
          theAction.idRoadmap = Integer.parseInt(id_Destination);
        }
    }    
  }   
  
  public void updateKeyidRoadmapLieForactionSuivi(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Action theAction= (Action)Liste.elementAt(i);
        int id_Origine = theAction.idRoadmapLie;
        String id_Destination = (String)ListeRoadmap.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomRoadmapById(sourceBd, theAction.idRoadmapLie);
            theAction.idRoadmapLie=getIdByNomRoadmap(Utils.clean(nom));
            ListeRoadmap.put(id_Origine, ""+ theAction.idRoadmapLie);
        }
        else
        {
          theAction.idRoadmapLie = Integer.parseInt(id_Destination);
        }
    }     
  }
  
public Vector readtypeGraphes(){
    Vector Liste = new Vector(10);
    req = "SELECT     nom, ordre, idEquipe FROM  typeGraphes ORDER BY id";

    this.ExecReq( req);
    try {
       while (rs.next()) {
       item theitem = new item(-1);
       
            theitem.nom = rs.getString("nom");
            theitem.ordre = rs.getInt("ordre");
            theitem.id = rs.getInt("idEquipe");


//if (Liste.size() < 1)
{
      Liste.addElement(theitem);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writetypeGraphes(Vector Liste){
       
    
    req = "delete from typeGraphes";
    this.ExecUpdate( req); 
         

    for (int i=0; i < Liste.size(); i++)
    {
        item theitem= (item)Liste.elementAt(i);        
                 
        req = "INSERT INTO typeGraphes ";
        req+=" (nom, ordre, idEquipe) VALUES  (";
        req+="'"+theitem.nom+"'";
        req+=",'"+theitem.ordre+"'";
        req+=","+theitem.id+"";
        req+=")";

        this.ExecUpdate(req);
    
    } 
   
  } 

  public void updateKeyidEquipeFortypeGraphes(db sourceBd,Vector Liste){
    for (int i=0; i < Liste.size(); i++)
    {
        item theitem= (item)Liste.elementAt(i);
        String nom=getNomById(sourceBd, theitem.id, "Equipe ", "nom", "id");
        theitem.id=  getIdByNom(nom,"Equipe ", "nom", "id");
    }
    
    for (int i=0; i < Liste.size(); i++)
    {
        item theitem= (item)Liste.elementAt(i);
        int id_Origine = theitem.id;
        String id_Destination = (String)ListeEquipes.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theitem.id, "Equipe ", "nom", "id");
            theitem.id=  getIdByNom(nom,"Equipe ", "nom", "id");
            ListeEquipes.put(id_Origine, ""+ theitem.id);
        }
        else
        {
          theitem.id = Integer.parseInt(id_Destination);
        }
    }      
  }  
  
public Vector readConsomme(int Annee){
    Vector Liste = new Vector(10);
    req = "SELECT     Consomme.id, Consomme.projetMembre, Consomme.semaine, Consomme.Charge, Consomme.anneeRef, Consomme.idAction";
    req+=" FROM         Consomme INNER JOIN";
    req+="                       Membre ON Consomme.projetMembre = Membre.idMembre INNER JOIN";
    req+="                      actionSuivi ON Consomme.idAction = actionSuivi.id INNER JOIN";
    req+="                       Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap";
    req+=" WHERE     (Consomme.Charge > 0) ";
    req+=" AND (YEAR(Roadmap.dateMep) >= "+Annee+") ORDER BY Consomme.id";    
    this.ExecReq( req);
    try {
       while (rs.next()) {
       Charge theCharge = new Charge(-1);
       
            theCharge.idMembre = rs.getInt("projetMembre");
            theCharge.semaine = rs.getInt("semaine");
            theCharge.hj = rs.getFloat("Charge");
            theCharge.anneeRef = rs.getInt("anneeRef");
            theCharge.idObj = rs.getInt("idAction");


//if (Liste.size() < 1)
{
      Liste.addElement(theCharge);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeConsomme(Vector Liste){
       
    
    req = "delete from Consomme";
    this.ExecUpdate( req); 
         

    for (int i=0; i < Liste.size(); i++)
    {
        Charge theCharge= (Charge)Liste.elementAt(i);        
                 
        req = "INSERT INTO Consomme ";
        req+=" (projetMembre, semaine, Charge, anneeRef, idAction) VALUES  (";
        req+="'"+theCharge.idMembre+"'";
        req+=",'"+theCharge.semaine+"'";
        req+=",'"+theCharge.hj+"'";
        req+=",'"+theCharge.anneeRef+"'";
        req+=","+theCharge.idObj+"";
        req+=")";

        this.ExecUpdate(req);
    
    } 
   
  } 

  public void updateKeyprojetMembreForConsomme(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Charge theCharge= (Charge)Liste.elementAt(i);
        int id_Origine = theCharge.idMembre;
        String id_Destination = (String)ListeMembres.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theCharge.idMembre, "Membre ", "LoginMembre", "idMembre");
            theCharge.idMembre=getIdByNom(nom, "Membre ", "LoginMembre", "idMembre");
            ListeMembres.put(id_Origine, ""+ theCharge.idMembre);
        }
        else
        {
          theCharge.idMembre = Integer.parseInt(id_Destination);
        }
    }    
  }   
  
  public void updateKeyidActionForConsomme(db sourceBd,Vector Liste){
    for (int i=0; i < Liste.size(); i++)
    {
        Charge theCharge= (Charge)Liste.elementAt(i);     
        String nom=getNomById(sourceBd, theCharge.idObj, "actionSuivi ", "nom", "id");
        theCharge.idObj=  getIdByNom(nom,"actionSuivi ", "nom", "id");
    }
    
    for (int i=0; i < Liste.size(); i++)
    {
        Charge theCharge= (Charge)Liste.elementAt(i);
        int id_Origine = theCharge.idObj;
        String id_Destination = (String)ListeActionSuivi.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomById(sourceBd, theCharge.idObj, "actionSuivi ", "nom", "id");
            theCharge.idObj=  getIdByNom(nom,"actionSuivi ", "nom", "id");
            ListeActionSuivi.put(id_Origine, ""+ theCharge.idObj);
        }
        else
        {
          theCharge.idObj = Integer.parseInt(id_Destination);
        }
    }       
    
    
  } 
  
public Vector readActionAnomalie(int Annee){
    Vector Liste = new Vector(10);
    req = "SELECT     ActionAnomalie.idAction, ActionAnomalie.dateAnomalie";
    req+=" FROM         ActionAnomalie INNER JOIN";
    req+="                       actionSuivi ON ActionAnomalie.idAction = actionSuivi.id INNER JOIN";
    req+="                      Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap ";
    req+=" WHERE (YEAR(Roadmap.dateMep) >= "+Annee+") ORDER BY ActionAnomalie.id";     
    this.ExecReq( req);
    try {
       while (rs.next()) {
       item theitem = new item(-1);
       
            theitem.id = rs.getInt("idAction");
            theitem.date = rs.getDate("dateAnomalie");

//if (Liste.size() < 1)
{
      Liste.addElement(theitem);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeActionAnomalie(Vector Liste){
       
    
    req = "delete from ActionAnomalie";
    this.ExecUpdate( req); 
         

    for (int i=0; i < Liste.size(); i++)
    {
        item theitem= (item)Liste.elementAt(i); 
        String str_date="";
        
        if (theitem.date != null)
            str_date ="convert(datetime, '"+theitem.date.getDate()+"/"+(theitem.date.getMonth()+1)+"/"+(theitem.date.getYear() +1900)+"', 103)";
        else
            str_date="null";        
                 
        req = "INSERT INTO ActionAnomalie ";
        req+=" ( idAction, dateAnomalie) VALUES  (";
        req+="'"+theitem.id+"'";
        req+=","+str_date+"";
        req+=")";

        this.ExecUpdate(req);
    
    } 
   
  }
   
 public String getNomActionById(db sourceBd, int id)
  {
    String nom="";
    req = "SELECT     St.nomSt + '-' + Roadmap.version + '-' + actionSuivi.Code AS nom";
    req+= " FROM         actionSuivi INNER JOIN";
    req+= "                       Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+= "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+= "                       St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     (actionSuivi.id = "+id+")";
    sourceBd.ExecReq( req);
    try {
       while (sourceBd.rs.next()) {
            nom= sourceBd.rs.getString(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return nom;
  } 
 
   public int getIdByNomAction(String nom)
  {
    int id=-1;
    req = "SELECT     actionSuivi.id";
    req+= " FROM         actionSuivi INNER JOIN";
    req+= "                       Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+= "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+= "                       St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     (St.nomSt + '-' + Roadmap.version + '-' + actionSuivi.Code = '"+nom+"')";
    this.ExecReq( req);
    try {
       while (rs.next()) {
        id= rs.getInt(1);
       }
      } catch (SQLException s) {s.getMessage(); }          
      return id;
  }     

  public void updateKeyidActionForActionAnomalie(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        item theitem= (item)Liste.elementAt(i);  
        int id_Origine = theitem.id;
        String id_Destination = (String)ListeTaches.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomActionById(sourceBd, theitem.id);
            theitem.id=getIdByNomAction(nom);
            ListeTaches.put(id_Origine, ""+ theitem.id);
        }
        else
        {
          theitem.id = Integer.parseInt(id_Destination);
        }
    }      
  }
  
public Vector readATachesDependances(){
    Vector Liste = new Vector(10);
    req = "SELECT     idTacheDestination, idTacheOrigine, type, idRoadmap, nb FROM  TachesDependances ORDER BY id";

    this.ExecReq( req);
    try {
       while (rs.next()) {
      
        association theassociation = new association ();
        theassociation.idDest= rs.getInt("idTacheDestination");
        theassociation.idOrig= rs.getInt("idTacheOrigine");
        theassociation.type= rs.getString("type");
        theassociation.id= rs.getInt("idRoadmap");

//if (Liste.size() < 1)
{
      Liste.addElement(theassociation);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeTachesDependances(Vector Liste){
       
    
    req = "delete from TachesDependances";
    this.ExecUpdate( req); 
         

    for (int i=0; i < Liste.size(); i++)
    {
        association theassociation = (association)Liste.elementAt(i);     
                 
        req = "INSERT INTO TachesDependances ";
        req+=" (idTacheDestination, idTacheOrigine, type, idRoadmap) VALUES  (";
        req+="'"+theassociation.idDest+"'";
        req+=",'"+theassociation.idOrig+"'";
        req+=",'"+theassociation.type+"'";
        req+=",'"+theassociation.id+"'";
        req+=")";

        this.ExecUpdate(req);
    
    } 
   
  } 
   
  public void updateKeyidTacheDestinationForTachesDependances(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        association theassociation = (association)Liste.elementAt(i);  
        int id_Origine = theassociation.idDest;
        String id_Destination = (String)ListeTaches.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomActionById(sourceBd, theassociation.idDest);
            theassociation.idDest=getIdByNomAction(nom);
            ListeTaches.put(id_Origine, ""+ theassociation.idDest);
        }
        else
        {
          theassociation.idDest = Integer.parseInt(id_Destination);
        }
    }     
  }
  
  public void updateKeyidTacheOrigineForTachesDependances(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        association theassociation = (association)Liste.elementAt(i);  
        int id_Origine = theassociation.idOrig;
        String id_Destination = (String)ListeTaches.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomActionById(sourceBd, theassociation.idOrig);
            theassociation.idOrig=getIdByNomAction(nom);
            ListeTaches.put(id_Origine, ""+ theassociation.idOrig);
        }
        else
        {
          theassociation.idOrig = Integer.parseInt(id_Destination);
        }
    }       
  }  
  
  public void updateKeyidRoadmapForTachesDependances(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        association theassociation = (association)Liste.elementAt(i);  
        int id_Origine = theassociation.id;
        String id_Destination = (String)ListeRoadmap.get(id_Origine);
        if (id_Destination == null)
        {
            String nom=getNomRoadmapById(sourceBd, theassociation.id);
            theassociation.id=getIdByNomRoadmap(Utils.clean(nom));
            ListeRoadmap.put(id_Origine, ""+ theassociation.id);
        }
        else
        {
          theassociation.id = Integer.parseInt(id_Destination);
        }
    }     
  } 
  
public Vector readAnomalies(){
    Vector Liste = new Vector(10);
    req = "SELECT     objetAnomalie, AnoIdMembre, descAnomalie, versionStAnomalie, creationAnomalie, typeEtatAnomalie,";
    req+= "  criticiteAnomalie,RepAnomalie, IdcriticiteAnomalie, Cout, createurAnomalie, lienImage FROM  Anomalie ORDER BY idAnomalie";

    this.ExecReq( req);
    try {
       while (rs.next()) {
      
        Anomalie theAnomalie = new Anomalie (-1);
            theAnomalie.objet = rs.getString("objetAnomalie");
            
            theAnomalie.description = rs.getString("descAnomalie");
            if (theAnomalie.description == null) theAnomalie.description = "";
             
            theAnomalie.versionStAnomalie = rs.getInt("versionStAnomalie");
            theAnomalie.DDateCreation = rs.getDate("creationAnomalie");
            
            theAnomalie.idTypeEtat=rs.getInt("typeEtatAnomalie");
            theAnomalie.Criticite=rs.getString("criticiteAnomalie");
            
            theAnomalie.Reponse = rs.getString("RepAnomalie");
            if (theAnomalie.Reponse == null) theAnomalie.Reponse = "";
            
            theAnomalie.idTypeCriticite=rs.getInt("IdcriticiteAnomalie");
            theAnomalie.Cout=rs.getString("Cout");
            
            theAnomalie.LoginCreateur=rs.getString("createurAnomalie");
            theAnomalie.lienImage=rs.getString("lienImage");

//if (Liste.size() < 1)
{
      Liste.addElement(theAnomalie);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeAnomalie(Vector Liste){
       
    
    req = "delete from Anomalie";
    this.ExecUpdate( req); 
         

    for (int i=0; i < Liste.size(); i++)
    {
        Anomalie theAnomalie = (Anomalie)Liste.elementAt(i);     
        
        if (theAnomalie.DDateCreation != null)
            theAnomalie.DateCreation ="convert(datetime, '"+theAnomalie.DDateCreation.getDate()+"/"+(theAnomalie.DDateCreation.getMonth()+1)+"/"+(theAnomalie.DDateCreation.getYear() +1900)+"', 103)";
        else
            theAnomalie.DateCreation="null";          
         

        req = "INSERT INTO Anomalie ";
        req+=" (objetAnomalie, descAnomalie, versionStAnomalie, creationAnomalie, typeEtatAnomalie,";
        req+= " criticiteAnomalie,RepAnomalie, IdcriticiteAnomalie, Cout, createurAnomalie, lienImage) ";
        req+= " VALUES  (";
        req+="'"+Utils.clean(theAnomalie.objet)+"'";
        req+=",";
        req+="'"+ Utils.clean(theAnomalie.description)+"'";
        req+=",";
        if (theAnomalie.versionStAnomalie >0)         
            req+="'"+theAnomalie.versionStAnomalie+"'";
        else
            req+="null";
        req+=",";
        req+=""+theAnomalie.DateCreation+"";
        req+=",";
        req+="'"+theAnomalie.idTypeEtat+"'";
        req+=",";
        req+="'"+theAnomalie.Criticite+"'";
        req+=",";
        req+="'"+ Utils.clean(theAnomalie.Reponse)+"'";
        req+=",";
        req+="'"+theAnomalie.idTypeCriticite+"'";
        req+=",";
        req+="'"+theAnomalie.Cout+"'";
        req+=",";
        req+="'"+theAnomalie.LoginCreateur+"'"; 
        req+=",";
        req+="'"+theAnomalie.lienImage+"'";        
        req+=")";

        this.ExecUpdate(req);
    
    } 
   
  }  
   
  public void updateKeyiversionStAnomalieForAnomalie(db sourceBd,Vector Liste){
    for (int i=0; i < Liste.size(); i++)
    {
        Anomalie theAnomalie = (Anomalie)Liste.elementAt(i);    
        String nomSt=getNomById(sourceBd, theAnomalie.versionStAnomalie, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
        theAnomalie.versionStAnomalie=getIdByNom(nomSt, "ST_ListeSt_All_Attribute", "nomSt", "idVersionSt");
    }
  } 
  
public Vector readFaitMarquant(){
    Vector Liste = new Vector(10);
    req = "SELECT  nomFait, descFait, Param FROM  FaitMarquant ORDER BY idFait";

    this.ExecReq( req);
    try {
       while (rs.next()) {
      
        FaitMarquant theFaitMarquant= new FaitMarquant ();
        theFaitMarquant.nomFait = rs.getString("nomFait");
        theFaitMarquant.descFait = rs.getString("descFait");
        theFaitMarquant.Param = rs.getString("Param");

//if (Liste.size() < 1)
{
      Liste.addElement(theFaitMarquant);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeFaitMarquant(Vector Liste){
       
    
    req = "delete from FaitMarquant";
    this.ExecUpdate( req); 
         

    for (int i=0; i < Liste.size(); i++)
    {
        FaitMarquant theFaitMarquant = (FaitMarquant)Liste.elementAt(i);     
                 
        req = "INSERT INTO FaitMarquant ";
        req+=" (nomFait, descFait, Param) VALUES  (";
        req+="'"+Utils.clean(theFaitMarquant.nomFait)+"'";
        req+=",'"+Utils.clean(theFaitMarquant.descFait)+"'";
        req+=",'"+theFaitMarquant.Param+"'";
        req+=")";

        this.ExecUpdate(req);
    
    } 
   
  }   
  
public Vector readhistoriqueInterface(){
    Vector Liste = new Vector(10);
    req = "SELECT dateSuivi, nom, description, idInterface, type, Login FROM  historiqueInterface ORDER BY id";

    this.ExecReq( req);
    try {
       while (rs.next()) {
      
        Suivi theSuivi= new Suivi (-1);
        theSuivi.dateSuivi = rs.getDate("dateSuivi");
        theSuivi.nom = rs.getString("nom");
        theSuivi.description = rs.getString("description");
        theSuivi.id = rs.getInt("idInterface");
        theSuivi.type = rs.getString("type");
        theSuivi.Login = rs.getString("login");

//if (Liste.size() < 1)
{
      Liste.addElement(theSuivi);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writehistoriqueInterface(Vector Liste){
       
    
    req = "delete from historiqueInterface";
    this.ExecUpdate( req); 
         

    for (int i=0; i < Liste.size(); i++)
    {
        Suivi theSuivi = (Suivi)Liste.elementAt(i);   
        String str_date="";
        if (theSuivi.dateSuivi != null)
            str_date ="convert(datetime, '"+theSuivi.dateSuivi.getDate()+"/"+(theSuivi.dateSuivi.getMonth()+1)+"/"+(theSuivi.dateSuivi.getYear() +1900)+"', 103)";
        else
            str_date="null";          
                 
        req = "INSERT INTO historiqueInterface ";
        req+=" (dateSuivi, nom, description, idInterface, type, Login) VALUES  (";
        req+=""+str_date+"";
        req+=",'"+theSuivi.nom+"'";
        req+=",'"+Utils.clean(theSuivi.description)+"'";
        req+=",'"+theSuivi.id+"'";
        req+=",'"+Utils.clean(theSuivi.type)+"'";
        req+=",'"+theSuivi.Login+"'";
        req+=")";

        this.ExecUpdate(req);
    
    } 
   
  } 
  public void updateKeyidInterfaceForhistoriqueInterface(db sourceBd,Vector Liste){
    
    for (int i=0; i < Liste.size(); i++)
    {
        Suivi theSuivi = (Suivi)Liste.elementAt(i);   
        int id_Origine = theSuivi.id;
        String id_Destination = (String)ListeInterfaces.get(id_Origine);
        if (id_Destination == null)
        {
            String nomInterface=getNomInterfaceById(sourceBd,theSuivi.id);
            theSuivi.id=getIdByNomInterface(nomInterface);
            ListeInterfaces.put(id_Origine, ""+ theSuivi.id);
        }
        else
        {
          theSuivi.id = Integer.parseInt(id_Destination);
        }
    }     
  } 
  
public Vector readAppliIcone(){
    Vector Liste = new Vector(10);
    req = "SELECT   nomTypeAppli, formeTypeAppli, formedefautTypeAppli, icone, couleur FROM    AppliIcone ORDER BY id";

    this.ExecReq( req);
    try {
       while (rs.next()) {
      
        TypeAppli theTypeAppli= new TypeAppli (-1);
        theTypeAppli.nom = rs.getString("nomTypeAppli");
        theTypeAppli.formeTypeAppli = rs.getString("formeTypeAppli");
        theTypeAppli.formedefautTypeAppli = rs.getString("formedefautTypeAppli");
        theTypeAppli.display = rs.getString("icone");
        theTypeAppli.alias = rs.getString("couleur");

//if (Liste.size() < 1)
{
      Liste.addElement(theTypeAppli);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeAppliIcone(Vector Liste){
       
    
    req = "delete from AppliIcone";
    this.ExecUpdate( req); 
         

    for (int i=0; i < Liste.size(); i++)
    {
        TypeAppli theTypeAppli = (TypeAppli)Liste.elementAt(i);   
      
                 
    req = "INSERT INTO [AppliIcone]";
    req+= "           ([nomTypeAppli]";
    req+= "            ,[formeTypeAppli]";
    req+= "            ,[formedefautTypeAppli]";
    req+= "            ,[icone]";
    req+= "            ,[couleur ]";
    req+=")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+theTypeAppli.nom+"'" + ",";
    req+= "'"+theTypeAppli.formeTypeAppli+"'" + ",";
    req+= "'"+theTypeAppli.formedefautTypeAppli+"'" + ",";
    req+= "'"+theTypeAppli.display+"'" + ",";
    req+= "'"+theTypeAppli.alias+"'"+ "";
    req+= "            )";

        this.ExecUpdate(req);
    
    } 
   
  }   
   
public Vector readContacts(){
    Vector Liste = new Vector(10);
    req = "SELECT   nom, prenom, entreprise, telephone, adresse, mail, demande, dateDemande, reponse, dateReponse, idEtat FROM  Contacts ORDER BY id";

    this.ExecReq( req);
    try {
       while (rs.next()) {
      
        Contact theContact= new Contact (-1);
      theContact.nom = rs.getString("nom");
      theContact.prenom = rs.getString("prenom");
      theContact.entreprise = rs.getString("entreprise");
      theContact.telephone = rs.getString("telephone");
      theContact.adresse = rs.getString("adresse");
      theContact.mail = rs.getString("mail");
      theContact.demande = rs.getString("demande");
      
      theContact.DdateDemande = rs.getDate("dateDemande");
      
      theContact.reponse = rs.getString("reponse");
      if (theContact.reponse == null)  theContact.reponse = "";
     
      
      theContact.DdateReponse = rs.getDate("dateReponse");
      
       theContact.idEtat = rs.getInt("idEtat");

//if (Liste.size() < 1)
{
      Liste.addElement(theContact);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeContacts(Vector Liste){
       
    
    req = "delete from Contacts";
    this.ExecUpdate( req); 
         

    for (int i=0; i < Liste.size(); i++)
    {
        Contact theContact = (Contact)Liste.elementAt(i);   
        String str_dateDemande="";
        if (theContact.DdateDemande != null)
            str_dateDemande ="convert(datetime, '"+theContact.DdateDemande.getDate()+"/"+(theContact.DdateDemande.getMonth()+1)+"/"+(theContact.DdateDemande.getYear() +1900)+"', 103)";
        else
            str_dateDemande="null";  
        
        String str_dateReponse="";        
        
        if (theContact.DdateReponse != null)
            str_dateReponse ="convert(datetime, '"+theContact.DdateReponse.getDate()+"/"+(theContact.DdateReponse.getMonth()+1)+"/"+(theContact.DdateReponse.getYear() +1900)+"', 103)";
        else
            str_dateReponse="null"; 
        
    req = "INSERT Contacts (";
    req+="nom";
    req+=",";
    req+="prenom";
    req+=",";
    req+="entreprise";
    req+=",";
    req+="telephone";
    req+=",";
    req+="adresse";
    req+=",";    
    req+="mail";
    req+=",";
    req+="demande";
    req+=",";
    req+="dateDemande";
    req+=",";
    req+="reponse";
    req+=",";        
    req+="dateReponse";
    req+=",";    
    req+="idEtat";
    req+="   )";
    req+=" VALUES (";
    req+="'"+Utils.clean(theContact.nom)+"'";
    req+=",";
    req+="'"+Utils.clean(theContact.prenom)+"'";
    req+=",";
    req+="'"+Utils.clean(theContact.entreprise)+"'";
    req+=",";
    req+="'"+Utils.clean(theContact.telephone)+"'";
    req+=",";
    req+="'"+Utils.clean(theContact.adresse)+"'";
    req+=",";    
    req+="'"+Utils.clean(theContact.mail)+"'";
    req+=",";
    req+="'"+Utils.clean(theContact.demande)+"'";
    req+=",";
    req+=""+str_dateDemande+"";
    req+=",";
    req+="'"+Utils.clean(theContact.reponse)+"'"; 
    req+=",";    
    req+=""+str_dateReponse+"";
    req+=",";    
    req+="'"+theContact.idEtat+"'";

    req+=")";

        this.ExecUpdate(req);
    
    } 
   
  } 
   
public Vector readListeMotsClefs(){
    Vector Liste = new Vector(10);
    req = "SELECT     nom FROM    ListeMotsClefs ORDER BY id";

    this.ExecReq( req);
    try {
       while (rs.next()) {
      
        item theitem =new item ();
        theitem.nom = rs.getString("nom");


//if (Liste.size() < 1)
{
      Liste.addElement(theitem);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeListeMotsClefs(Vector Liste){
       
    
    req = "delete from ListeMotsClefs";
    this.ExecUpdate( req); 
         

    for (int i=0; i < Liste.size(); i++)
    {
        item theitem = (item)Liste.elementAt(i);   
      
                 
    req = "INSERT INTO [ListeMotsClefs]";
    req+= "           ([nom]";
    req+=")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+theitem.nom+"'" + "";
    req+= "            )";

        this.ExecUpdate(req);
    
    } 
   
  }   
   
public Vector readMaturite(){
    Vector Liste = new Vector(10);
    req = "SELECT    nom, num FROM  Maturite ORDER BY id";

    this.ExecReq( req);
    try {
       while (rs.next()) {
      
        item theitem =new item ();
        theitem.nom = rs.getString("nom");
        theitem.ordre = rs.getInt("num");


//if (Liste.size() < 1)
{
      Liste.addElement(theitem);
}
       }
      } catch (SQLException s) {s.getMessage(); }    
    
    return Liste;
  }
 
   public void writeMaturite(Vector Liste){
       
    
    req = "delete from Maturite";
    this.ExecUpdate( req); 
         

    for (int i=0; i < Liste.size(); i++)
    {
        item theitem = (item)Liste.elementAt(i);   
      
                 
    req = "INSERT INTO [Maturite]";
    req+= "           ( nom, num";
    req+=")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+theitem.nom+"'" + ",";
    req+= "'"+theitem.ordre+"'" + "";
    req+= "            )";

        this.ExecUpdate(req);
    
    } 
    
    
   
  }
   
 public ErrorSpecific bd_InsertListeTables(String nomBase,Connexion myCnx, Statement st, String transaction){
  ErrorSpecific myError = new ErrorSpecific();
  ResultSet rs=null;

  req = "delete  FROM    Migration";
  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeEquipe",""+this.id);
  if (myError.cause == -1) return myError;

   for (int i=0; i < this.listeTables.size(); i++)
   {
     table theTable = (table)this.listeTables.elementAt(i);
      String req = "INSERT Migration (Nom, Etat, Ordre, Migration) VALUES ('"+theTable.nom+"',"+theTable.idEtat+","+theTable.ordre+","+theTable.identity+")";
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"ErrorSpecific",""+this.id);
     if (myError.cause == -1) return myError;
   }

   return myError;

}   
   
  public void  bd_UpdateValueFromNom(String nom, String value){
    ErrorSpecific myError = new ErrorSpecific();
    String req = "SELECT     valeur FROM         Config WHERE     (nom = '"+nom+"')";

    req = "UPDATE Config SET ";
    req+="valeur ='"+value+"'";
    req+=" WHERE     (nom = '"+nom+"')";
    this.ExecUpdate(req);

  }  
  
   public void backupToS3(String base, String backup,int overwrite){
    
    req = "exec msdb.dbo.rds_backup_database ";
    req += " @source_db_name='"+base+"', ";
    req += " @s3_arn_to_backup_to='"+backup+"', "; 
    req += " @overwrite_S3_backup_file="+ overwrite +";";    

    try{
    this.ExecReq(req);
    }
    catch (Exception e)
    {
        Connexion.trace("---- Erreur execution", "req", "" + req);
    }
   
  }   
   
   public void restoreFromS3(String base, String backup){
    
    req = "exec msdb.dbo.rds_restore_database ";
    req += " @restore_db_name='"+base+"', ";
    req += " @s3_arn_to_restore_from='"+backup+"';"; 

    try{
    this.ExecReq(req);
    }
    catch (Exception e)
    {
        Connexion.trace("---- Erreur execution", "req", "" + req);
    }

    
    } 
      
   
   public String checkStatus(String base){
    req = "exec msdb.dbo.rds_task_status @db_name='"+base + "'";

    try{
    this.ExecReq(req);
    }
    catch (Exception e)
    {
        Connexion.trace("---- Erreur execution", "req", "" + req);
    }
    
    String lifecycle = "";
    int old_task_id = -1;
    int task_id = -1;
   
     
    try { while (rs.next()) {
      task_id= rs.getInt("task_id");
      if (task_id > old_task_id)
        lifecycle= rs.getString("lifecycle");
      old_task_id = task_id;
      }
    }
    catch (SQLException s) {s.getMessage();}      
    return lifecycle;
  }    
   
   
  // -----------------------------------------------------------------------------------------------//
}


  


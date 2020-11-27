package accesbase; 

import ST.ST;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import Composant.item;

/**
 * <p>Titre : </p>
 *
 * <p>Description : </p>
 *
 * <p>Copyright : Copyright (c) 2004</p>
 *
 * <p>Soci�t� : </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Config {
  String path="";
  private String req="";
  public static Vector ListeItems = new Vector(10);  
  public  Vector ListeItem = new Vector(10);

  public Config() {
    this.path = this.getClass().getResource("").getFile();
    this.path=this.path.substring(1);
    this.path = this.path.replaceAll("%20","\\ ");

    // ----------------------------- Extraction de la directory ou exporter le fichier ------//
    int pos=-1; //
    pos = this.path.indexOf("WEB-INF");
    if (pos > -1)
      this.path = this.path.substring(0,pos - 1);
  }

 public static void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs=null;
    String req;
    
    ListeItems.removeAllElements();

    req ="SELECT nom, valeur, Ordre,security  FROM   Config ORDER BY Ordre";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
       while (rs.next()) {    
        item theItem = new item();
            theItem.nom = rs.getString("nom");
            theItem.desc = rs.getString("valeur");
            theItem.ordre = rs.getInt("Ordre");
            theItem.security = rs.getInt("security");
            ListeItems.addElement(theItem);
       }
                
    } catch (SQLException s) {s.getMessage();}


  }  
 
  public String bd_UpdatePath(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;

    req = "SELECT     valeur FROM  actionSuivi WHERE (valeur = '')";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    req = "UPDATE [Config] SET [valeur]= ('"+this.path+"') WHERE nom= 'PATH'";
    //req = "UPDATE [xxxx] SET [nom]= 'xxxx'";
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     return "OK";

  }

  public static String getConfigExport(String nomBase,Connexion myCnx, Statement st, String nom){
    ResultSet rs;
    String value="";

    String req = "SELECT     valeur FROM         Config WHERE     (nom = '"+nom+"')";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        value= rs.getString("valeur");
        }
      } catch (SQLException s) {s.getMessage();}

    return value;
  }

  public static ErrorSpecific bd_UpdateValueFromNom(String nomBase,Connexion myCnx, Statement st, String transaction, String nom, String value){
    ErrorSpecific myError = new ErrorSpecific();
    String req = "";
    req = "UPDATE Config SET ";
    req+="valeur ='"+value+"'";
    req+=" WHERE     (nom = '"+nom+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, nom,"bd_UpdateConfig",""+nom);
    if (myError.cause == -1) return myError;

    return myError;
  }

  public static String getValueFromNom(String nomBase,Connexion myCnx, Statement st, String nom){
    ResultSet rs;
    String value="";

    String req = "SELECT     valeur FROM         Config WHERE     (nom = '"+nom+"')";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        value= rs.getString("valeur");
        }
    } catch (SQLException s) {s.getMessage();}

    return value;
  }
  
  public static int getCount(String nomBase,Connexion myCnx, Statement st, String nom){
    ResultSet rs;
    int nb = 0;

    String req = "SELECT   count(*) as nb FROM "+nom;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        nb= rs.getInt("nb");
        }
    } catch (SQLException s) {s.getMessage();}

    return nb;
  }
  
  public static String startExport(String nomBase,Connexion myCnx, Statement st, String export, String declenche){
      ResultSet rs = null;
      Date currentTime_1 = new Date();
      int theYear = currentTime_1.getYear()+ 1900;
      int theMonth = currentTime_1.getMonth()+1;
      int theDay = currentTime_1.getDate();
      int theHour = currentTime_1.getHours();
      int theMinute = currentTime_1.getMinutes();
      int theSecond = currentTime_1.getSeconds();
      String theDate =theYear+"-"+theMonth+"-"+theDay+"-"+theHour+"-"+theMinute+"-"+theSecond;

      String req = "update Config set valeur='"+theDate+"'";
      req+=" WHERE     (nom = '"+export+"') ";
      myCnx.ExecReq(st,nomBase,req);

      req = "update "+declenche+" set nom='GO'";

      myCnx.ExecReq(st,nomBase,req);




      return theDate;
  }
  
  public static void removeAllItems(){
        ListeItems.removeAllElements();
  }
  
  public  boolean searchItem(String nom){
      boolean trouve = false;
      
        for (int i=0; i < this.ListeItem.size(); i++)
        {
            item theItem = (item)ListeItem.elementAt(i);
            if (theItem.nom.equals(nom))
                return true;
        }
      return trouve;
  }

  public static void main(String[] args) {
    Connexion myCnx = null;
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;

    String base = myCnx.getDate();
    st = myCnx.Connect();

    Config config = new Config();
    config.bd_UpdatePath(myCnx.nomBase, myCnx, st, "config");
    System.out.print(config.path);

    myCnx.Unconnect(st);
  }
}

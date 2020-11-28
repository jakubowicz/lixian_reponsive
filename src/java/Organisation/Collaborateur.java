package Organisation; 
import Composant.item;
import java.util.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import accesbase.Connexion;
import java.sql.SQLException;
import java.util.Vector;

import General.Utils;

import java.util.*;
import accesbase.ErrorSpecific;

import java.text.SimpleDateFormat;
/**
 * <p>Titre : Description d'un collaborateur</p>
 *
 * <p>Description : </p>
 *
 * <p>Copyright : Copyright (c) 2004</p>
 *
 * <p>Soci�t� :  </p>
 *
 * @author : Joel Jakubowicz
 * @version 1.0
 */
public class Collaborateur {
  public int id;
  public int idGenerique;
  public String nom = "";
  public String prenom= "";
  public String fonction= "";
  public int service;
  public int service_old;
  public int direction;
  public String nomDirection= "";
  
  public String mail = "";
  public String Login = "";
  public String Password = "";

  
  public int isInterne = 1;
  public int isManager = 0;
  public int isAdmin = 0;
  public int isProjet =0;
  public int isTest=0;
  public int isPo=0;
  public int isBrief=0;
   
  public String TypeAdmin="";

  public String Admin="no";
  public String Manager="no";
  public String MOE="no";
  public String MOA="no";
  public String GOUVERNANCE="no";



  public int AppletNotSupported;
  public String AO= "";
  public int Prix;
  public String dateEntree= "";
  public String dateSortie= "";
  public String intitulePoste = "";
  public int niveau;
  public String Mission = "";
  public int societe;
  public String nomSociete= "";
  public String nomService= "";
  public String nomServiceImputations= "";

  public String chaine_a_ecrire= "";

  public Vector ListeEquipes = new Vector(10);
  public Vector ListeServices = new Vector(10);
  public Vector ListeProjets = new Vector(10);
  public Vector ListeCharges = new Vector(10);
  public Vector ListeST = new Vector(10);
  public Vector ListeActions = new Vector(10);
  public Vector ListeActionsNonImputables = new Vector(10);
  public Vector ListeRespMoa = new Vector(10);
  public Vector ListeFavoris = new Vector(10);
  public Vector ListeCompetences = new Vector(10);

  public String couleurEB="F9FF40";
  public String couleurDEV="4BFF14";
  public String couleurTEST="FF0303";
  public String couleurMES="D6E2D3";

  public Vector ListeProfilST = new Vector(10);
  public Vector ListeProfilsProjet = new Vector(10);
  public String profilST="";
  
  public int noteCharge=0;
  public int noteCompetence=0;


   public static Vector ListeCollaborateur = new Vector(10);
   public static Vector ListeProfil_static = new Vector(10);

  private String req="";

  String TypeCreation="";

  public float totalMembre = 0;
  public float ChargePrevue = 52 * 5;

  public int maxProjets=0;
  public String nomComplet="";

  public float totalReparti = 0;

  public String Telephone_M="";
  public String Telephone_B="";
  public String Fax="";

  public String nomCommercial;
  public String prenomCommercial;
  public String TelephoneCommercial;
  public String mailCommercial;

  public float etp = 1;
  
  public int idManager= -1;
  
  public int nbColleguesSpecification= 0;
  public int nbColleguesDeveloppement= 0;
  public int nbColleguesTest= 0;
  
  public float chargeMoyenneEb = 0;
  public float chargeMoyenneDev = 0;
  public float chargeMoyenneTest = 0;
  
  public String photo="images/Direction.png";
  
  private ResultSet rs=null;
  
  public String str_ListeCouleurPeriode = "";
  public Vector ListeCouleurPeriode = new Vector(10);  
  
  public Collaborateur(String Login) {
    this.Login = Login;
    this.TypeCreation = "byLogin";

  }
    public  void disconnect(String nomBase,Connexion myCnx, Statement st){
    req += "UPDATE StatConnexion";
    req += " SET etat= 'deconnecte'";
    req += " WHERE StatConnexion.UserName='"+this.Login+"'";
    
    myCnx.ExecReq(st, nomBase, req);

}  
public boolean isRegistered(String nomBase,Connexion myCnx, Statement st ){
    
    boolean isRegistered = false;
    int idGenerique=0;
    ResultSet rs;
    req = "SELECT     idMembre FROM   Membre WHERE     (LoginMembre = '"+this.Login+"')";
    req+= " AND     (password = '"+this.Password+"')";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
    while(rs.next())
    {
       this.id = rs.getInt(1);
       isRegistered = true;
     }
    }
   catch (SQLException s) { }

   return isRegistered;
 }     
  
  public  boolean isConnected(String nomBase,Connexion myCnx, Statement st, int minutes , String Machine){
    //Construction du tableau de valeur des services, organis�s par directions
    boolean isCnx=false;
    int nb=0;
    req = "SELECT     COUNT(*) as nb";
    req += " FROM         StatConnexion LEFT OUTER JOIN";
    req += "                       nbCnxByMembre ON StatConnexion.UserName = nbCnxByMembre.UserName";
    req += " WHERE     (GETDATE() - StatConnexion.majCnx < CONVERT(DATETIME, '1900-01-01 00:"+minutes+":00', 102))";
    req += " AND StatConnexion.UserName='"+this.Login+"'";
    req += " AND StatConnexion.Carto <>'"+Machine+"'";
    req += " AND etat = 'connecte'";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try { while(rs.next()) {
        nb =rs.getInt("nb"); // nom du ST
       
                  }	} catch (SQLException s) {s.getMessage();}
    
    if (nb > 0) isCnx = true;
    return isCnx;
}  
  public void setAnonymous(String nomBase,Connexion myCnx, Statement st ){
    int nb=0;
    ResultSet rs;
    req = "SELECT     LoginMembre FROM  Membre WHERE     (intitulePoste = '"+this.Login+"')";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
    while(rs.next())
    {
       this.Login = rs.getString("LoginMembre");
     }
    }
   catch (SQLException s) { }

 }
   public  boolean getPeriod(String nomBase,Connexion myCnx, Statement st, String mode, String str_nbJour, String start_date){
    
    if (mode.equals("NORMAL")) return true;
    int nb=0;
    try{
        nb= Integer.parseInt(str_nbJour);
    }
    catch (Exception s) {
        nb=0;
    }
    
    Date currentDate = new Date();
    long l_currentDate = currentDate.getTime();
    
    Date endDate = null;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
    
    try {
    endDate = simpleDateFormat.parse(start_date);
    System.out.println(endDate);
    } catch (Exception e) {e.printStackTrace();}    
    
    long l_endDate = endDate.getTime() ;
    System.out.println("l_startDate="+l_endDate);
    long delta = (long)nb * (long)24 * (long)3600 * (long)1000 ;
    System.out.println("delta="+delta);
    l_endDate += delta;
    
    System.out.println("l_endDate="+l_endDate);
    System.out.println("l_currentDate="+l_currentDate);
    
    
    if (l_endDate >= l_currentDate)
        return true;
    else
        return false;
    
}   
public void getRoles(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs=null;
    int nb = 0;
    req = "SELECT     COUNT(*) AS nb FROM   Membre WHERE     (LoginMembre = '"+Login+"') AND (isAdmin = 1)";
    rs = myCnx.ExecReq(st, nomBase, req);
     try { rs.next();
            nb = rs.getInt(1);
            if (nb > 0) this.Admin = "yes";else this.Admin="no";
     } catch (SQLException s) {s.getMessage();}

     
     int nbManager = 0;
    req = "SELECT COUNT(*) AS nb FROM Membre INNER JOIN Service ON Membre.serviceMembre = Service.idService WHERE (Membre.LoginMembre = '"+Login+"')  AND (Membre.isManager = 1)  ";
    rs = myCnx.ExecReq(st, nomBase, req);
     try { rs.next();
            nbManager = rs.getInt(1);
            if ( nbManager > 0) this.Manager = "yes";else this.Manager="no";
     } catch (SQLException s) {s.getMessage();}

     int nbMOA = 0;
    req = "SELECT COUNT(*) AS nb FROM Membre INNER JOIN Service ON Membre.serviceMembre = Service.idService WHERE (Membre.LoginMembre = '"+Login+"') AND (Service.isMoa=1)";
    rs = myCnx.ExecReq(st, nomBase, req);
     try { rs.next();
            nbMOA = rs.getInt(1);
            if (nbMOA > 0) this.MOA = "yes";else this.MOA="no";
 } catch (SQLException s) {s.getMessage();}

 int nbMOE = 0;
req = "SELECT COUNT(*) AS nb FROM Membre INNER JOIN Service ON Membre.serviceMembre = Service.idService WHERE (Membre.LoginMembre = '"+Login+"') AND (Service.isMoe=1)";
rs = myCnx.ExecReq(st, nomBase, req);
 try { rs.next();
        nbMOE = rs.getInt(1);
        if (nbMOE > 0) this.MOE = "yes";else this.MOE="no";
 } catch (SQLException s) {s.getMessage();}

 int nbGOUV = 0;
req = "SELECT COUNT(*) AS nb FROM Membre INNER JOIN Service ON Membre.serviceMembre = Service.idService WHERE (Membre.LoginMembre = '"+Login+"') AND (Service.isGouvernance=1)";
rs = myCnx.ExecReq(st, nomBase, req);
 try { rs.next();
        nbMOE = rs.getInt(1);
        if (nbMOE > 0) this.GOUVERNANCE = "yes";else this.GOUVERNANCE="no";
 } catch (SQLException s) {s.getMessage();}


  }  

}

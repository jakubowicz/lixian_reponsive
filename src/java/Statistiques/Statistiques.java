package Statistiques; 
import java.util.*;
import java.sql.*;
import accesbase.*;
import java.util.Date;
import accesbase.Connexion;
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
public class Statistiques {
  public int nbUtilisateurs = 0;
  public String debutStat="";
  public Vector ListeStats = new Vector(10);
  public Vector ListeStatsUserConnected = new Vector(10);
  public Vector ListeStatsUsertoDay = new Vector(10);
  public Vector ListeMembre = new Vector(10);
  public Vector ListeItems = new Vector(10);
  
  private String req="";

  public Statistiques() {
  }

  public  void getListeMembre(String nomBase,Connexion myCnx, Statement st ){
    //Construction du tableau de valeur des services, organis�s par directions
    String req = "exec UTIL_nbMembreCnx";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try { while(rs.next()) {
        String nb= rs.getString("nb");
        String Login= rs.getString("UserName");
        String prenomMembre= rs.getString("prenomMembre");
        String nomMembre= rs.getString("nomMembre");
        String fonctionMembre= rs.getString("fonctionMembre");
        String nomService= rs.getString("nomService");
        String nomDirection= rs.getString("nomDirection");
        String jreVersion= rs.getString("jreVersion");

        LigneStat theLigneStat = new LigneStat ( nb, Login, prenomMembre, nomMembre, fonctionMembre, nomService, nomDirection, jreVersion);
        this.ListeMembre.addElement(theLigneStat);
                  }	} catch (SQLException s) {s.getMessage();}
}
  
  public  void getListeItems(String nomBase,Connexion myCnx, Statement st ){
    //Construction du tableau de valeur des services, organis�s par directions
    req = "SELECT DISTINCT output  FROM     stat_trad ORDER BY output";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try { while(rs.next()) {
        item theItem = new item();
        theItem.nom = rs.getString("output");
        this.ListeItems.addElement(theItem);
                  }	} catch (SQLException s) {s.getMessage();}
}
  
  public  void getNbUtilisateurs(String nomBase,Connexion myCnx, Statement st ){

    ResultSet rs = myCnx.ExecReq(st, nomBase, "exec UTIL_nbUtilisateursWebarc");
    try {
      rs.next();
      this.nbUtilisateurs = rs.getInt(1);}
    catch (SQLException s) {s.getMessage();}
}  
  
  public  void iniItems(String nomBase,Connexion myCnx, Statement st, int annee){
 int nb=0;
 
 
 for (int mois = 1; mois <= 12; mois++)
 {
     String str_date = "convert(datetime, '"+"01"+"/"+mois+"/"+annee+"', 103)";
      for (int i=0; i < this.ListeItems.size(); i++)
      {
          item theItem = (item)this.ListeItems.elementAt(i);
          req = "SELECT     COUNT(*) AS nb";
            req+= " FROM         StatConnexion INNER JOIN";
            req+= "                       stat_trad ON StatConnexion.pageName = stat_trad.input";
            req+= " WHERE     (stat_trad.output = '"+theItem.nom+"') AND (YEAR(StatConnexion.dateCnx) = "+annee+") AND (MONTH(StatConnexion.dateCnx) = "+mois+")";
            
            ResultSet rs = myCnx.ExecReq(st, nomBase, req);
            try {
                rs.next();
                nb = rs.getInt(1);}
                catch (SQLException s) {s.getMessage();}
            
            if (nb == 0)
            {
                
    req = "INSERT INTO [StatConnexion]";
    req+= "           ([UserName]";
    req+= "            ,[sessionId]";
    req+= "            ,[dateCnx]";
    req+= "            ,[majCnx]";
    req+= "            ,[pageName]";
    req+= "            ,[etat]";
    req+= "            ,[Machine]";
    req+= "            ,[item]";
    req+= "            ,[Carto]";
    req+= "            ,[jreVersion]";
    req+= "            )";    
    req+= "      VALUES";
    req+= "            (";
    req+= "'UserName'" + ",";
    req+= "'sessionId'" + ",";
    req+= str_date + ",";
    req+= str_date + ",";
    req+= "'"+theItem.nom+"'" + ",";
    req+= "'etat'" + ",";
    req+= "'Machine'" + ",";
    req+= "'item'" + ",";
    req+= "'Carto'" + ",";
    req+= "'jreVersion'" + "";
 
    req+= "            )";
    myCnx.ExecUpdate(st,nomBase,req,true);
            }
            
            }
 }
}  
  
  public  int getnbItemByMonthByYear(String nomBase,Connexion myCnx, Statement st, int annee, int mois, String item ){
    int nb = 0;
    ResultSet rs = myCnx.ExecReq(st, nomBase, "exec UTIL_nbUtilisateursWebarc");
    try {
      rs.next();
      this.nbUtilisateurs = rs.getInt(1);}
    catch (SQLException s) {s.getMessage();}
    
    return this.nbUtilisateurs;
}

  public  void getdebutStat(String nomBase,Connexion myCnx, Statement st ){

    ResultSet rs = myCnx.ExecReq(st, nomBase, "SELECT DAY(MIN(dateCnx)), MONTH(MIN(dateCnx)), YEAR(MIN(dateCnx)) FROM StatConnexion");
    try {
      rs.next();
      this.debutStat = rs.getString(1)+"/"+rs.getString(2)+"/"+rs.getString(3); }
    catch (SQLException s) {s.getMessage();}

}

  public  void getListeUserConnected(String nomBase,Connexion myCnx, Statement st ){
    //Construction du tableau de valeur des services, organis�s par directions
    String req = "exec UTIL_SelectUserConnected";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try { while(rs.next()) {
        String UserName= rs.getString("UserName"); // nom du ST
        String dateCnx= rs.getString("dateCnx"); // nom du ST
        String dateDeCnx= rs.getString("majCnx"); // nom du ST
        String pageName= rs.getString("pageName"); // nom du ST
        String etat= rs.getString("etat"); // etat du ST
        String Machine= rs.getString("Machine"); // nom du ST
        String item= rs.getString("item"); // nom du ST
        String Carto= rs.getString("Carto"); // nom du ST
        String nb= rs.getString("nb"); // nom du ST
        String jreVersion= rs.getString("jreVersion"); // nom du ST

        LigneStat theLigneStat = new LigneStat ( UserName, dateCnx, dateDeCnx, pageName, etat, Machine, item, Carto, nb, jreVersion);
        this.ListeStatsUserConnected.addElement(theLigneStat);
                  }	} catch (SQLException s) {s.getMessage();}
}

public  void getListeUsertoDay(String nomBase,Connexion myCnx, Statement st ){
                //Construction du tableau de valeur des services, organis�s par directions
                String req = "exec UTIL_SelectUserToDay";
                ResultSet rs = myCnx.ExecReq(st, nomBase, req);

                try { while(rs.next()) {
                    String UserName= rs.getString("UserName"); // nom du ST
                    String dateCnx= rs.getString("dateCnx"); // nom du ST
                    String dateDeCnx= rs.getString("majCnx"); // nom du ST
                    String pageName= rs.getString("pageName"); // nom du ST
                    String etat= rs.getString("etat"); // etat du ST
                    String Machine= rs.getString("Machine"); // nom du ST
                    String item= rs.getString("item"); // nom du ST
                    String Carto= rs.getString("Carto"); // nom du ST
                    String nb= rs.getString("nb"); // nom du ST
                    String jreVersion= rs.getString("jreVersion"); // nom du ST

                    LigneStat theLigneStat = new LigneStat ( UserName, dateCnx, dateDeCnx, pageName, etat, Machine, item, Carto, nb, jreVersion);
                    this.ListeStatsUsertoDay.addElement(theLigneStat);
                              }	} catch (SQLException s) {s.getMessage();}
}

public  void getListeExcel(String nomBase,Connexion myCnx, Statement st ){

  String req = "exec UTIL_excelStatistiques";
  ResultSet rs = myCnx.ExecReq(st, nomBase, req);

  try { while(rs.next()) {
      String UserName = rs.getString("UserName");
      String dateCnx= rs.getDate("date").toString();
      String HeureCnx= rs.getTime("heure").toString();
      String pageName= rs.getString("pageName");
      String Machine= rs.getString("Machine");

      LigneStat theLigneStat = new LigneStat (UserName,dateCnx, HeureCnx,pageName,Machine);
      this.ListeStats.addElement(theLigneStat);
      }	} catch (SQLException s) {s.getMessage();}
}

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    for (int i=0; i < this.ListeStats.size();i++)
    {
      LigneStat theLigneStat = (LigneStat)this.ListeStats.elementAt(i);
      theLigneStat.dump();
    }
    System.out.println("==================================================");
  }

  public static void main(String[] args) {
    Statistiques statistiques = new Statistiques();
  }
}

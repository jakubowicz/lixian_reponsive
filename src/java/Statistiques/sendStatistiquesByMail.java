package Statistiques; 

import java.util.Date;
import Organisation.Service;
import java.sql.Statement;
import java.sql.ResultSet;
import accesbase.Connexion;
import Organisation.Collaborateur;
import java.sql.SQLException;
import java.util.*;
import java.util.Date;
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
public class sendStatistiquesByMail {
  public static String serveur = "";
  public static String root = "";
  public static String Login = "";
  public sendStatistiquesByMail() {
  }

  public static String getNbUtilisateurs(String nomBase, Connexion myCnx, Statement st, Statistiques theStatistiques) {
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";


          String corps ="";

          corps += "<p><font color='#003399'>";
          corps += "<span class='entete'></span>Vous &ecirc;tes <font color='#FF0000'>"+theStatistiques.nbUtilisateurs+"</font> Utilisateurs diff&eacute;rents de Lixian</font></p>";
          corps += "<font color='#003399'>   ";

    return corps;

  }

  public static String getListeUsertoDay(String nomBase, Connexion myCnx, Statement st, Statistiques theStatistiques) {
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";


          String corps ="";

          corps += "<table width='947' border='0' class='moyen' >";
          corps += "<tr bgcolor='#264C8F' >";
          corps += "<th height='43' bgcolor='#FFFFFF' scope='col'>&nbsp;</th>";
          corps += "<th class='entete' scope='col'><span class='Style2'><font color='#F1F3F6'>Login</font></span></th>";
          corps += "<th class='entete' scope='col'><span class='Style2'><font color='#F1F3F6'>nb</font></span></th>";
          corps += "<th class='entete' scope='col'><span class='Style2'><font color='#F1F3F6'>date";
          corps += "Connexion</font></span></th>";
          corps += "<th class='entete' scope='col'><span class='Style2'><font color='#F1F3F6'>Pages</font></span></th>";
          corps += "<th class='entete' scope='col'><span class='Style2'><font color='#F1F3F6'>Objet</font></span></th>";
          corps += "<th class='entete' scope='col'><span class='Style2'><font color='#F1F3F6'>Etat</font></span></th>";
          corps += "</tr>";

for (int i=0; i < theStatistiques.ListeStatsUsertoDay.size();i++)
 {
   LigneStat theLigneStatUsertoDay = (LigneStat)theStatistiques.ListeStatsUsertoDay.elementAt(i);

        corps += "<tr bgcolor='#DDE8EE' >";
        corps += "<th height='26' scope='col' bgcolor='#264C8F'><span class='Style3'><font color='#F1F3F6'>"+i+"</font></span></th>";
        corps += "<th class='Style3' scope='col'>"+theLigneStatUsertoDay.UserName+"</a></th>";
        corps += "<th class='Style3' scope='col'>"+theLigneStatUsertoDay.nb+"</th>";
        corps += "<th class='Style3' scope='col'>"+theLigneStatUsertoDay.dateCnx+"</th>";
        corps += "<th class='Style3' scope='col'>"+theLigneStatUsertoDay.pageName+"</th>";
        corps += "<th class='Style3' scope='col'>"+theLigneStatUsertoDay.item+"</th>";
        corps += "<th class='Style3' scope='col'>"+theLigneStatUsertoDay.etat+"</th>";
        corps += "</tr>";

 }

    corps += "</table>";

    return corps;

  }
  public static void main(String[] args) {
    Connexion myCnx = null;
    Statement st, st2, st3 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    st2 = myCnx.Connect();
    st3 = myCnx.Connect();

    String str_date="";
    Date theDate = new Date();

    theDate = new Date();
    str_date  = theDate.toString();

    int theDay =theDate.getDay();
    if ((theDay == 6) || (theDay == 0)) return;


    Statistiques theStatistiques = new Statistiques();
    theStatistiques.getNbUtilisateurs(myCnx.nomBase,myCnx,  st );
    theStatistiques.getdebutStat(myCnx.nomBase,myCnx,  st );
    theStatistiques.getListeUsertoDay(myCnx.nomBase,myCnx,  st );

    String emetteur = "jjakubow@"+myCnx.mail;
    String destinataire = emetteur;
    String objet = "Statisques du: "+theDate.toString();
    String corps = getNbUtilisateurs(myCnx.nomBase,myCnx,  st, theStatistiques);
    corps+=getListeUsertoDay(myCnx.nomBase,myCnx,  st, theStatistiques);


    try{
          myCnx.sendmail(emetteur, destinataire, "*** Lixian:" + objet, corps);
    } 
    catch (Exception e){
      myCnx.trace("@01234******","",e.getMessage());
      //e.printStackTrace();
    }
    myCnx.Unconnect(st2);
    myCnx.Unconnect(st);
  }
}

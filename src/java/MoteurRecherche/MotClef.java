package MoteurRecherche;

import java.util.Vector; 
import ST.ST;
import Projet.Roadmap;
import OM.OM;
import ST.Interface;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import java.util.*;
import Organisation.Collaborateur;

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
public class MotClef {
  private static Connexion myCnx;
  public String Clef;
  public String Operateur;
  public String DisplayRecherche;
  public Vector ListeSt = new Vector(6);
  public Vector ListeProjets = new Vector(6);
  public Vector ListeOm = new Vector(6);
  public Vector ListeInterface = new Vector(6);
  public Vector ListeActeurs = new Vector(6);
  public Vector ListeDevis = new Vector(6);
  private String req="";

  public MotClef(String Clef, String Operateur) {
    this.Clef = Clef;
    this.Operateur = Operateur;
  }

  public String getClauseWhereOm(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;
    String ClauseWhere = "";
    if (Clef == null || Clef.equals(""))
    {
      DisplayRecherche="none";
    }
    else
    {
      DisplayRecherche = "";

      int index = 0;
      String theTocken = "";
      String newClef = "";
      if (Clef != null)
        newClef = Clef.replaceAll("'", "' ").replaceAll(",",
            " ").replaceAll("\\.", " ").replaceAll("\\;", " ");

      for (StringTokenizer t = new StringTokenizer(newClef, " ");
           t.hasMoreTokens(); ) {
        theTocken = t.nextToken().replaceAll("'", "''");

        String reqExclusion =
            "SELECT COUNT(mot) AS nb FROM Exclusion WHERE mot = '" + theTocken +
            "'";
        rs = myCnx.ExecReq(st, nomBase, reqExclusion);
        int nbExclusion = 0;
        try {
          rs.next();
          nbExclusion = rs.getInt(1);
        }
        catch (SQLException s) {
          s.getMessage();
        }
        if (nbExclusion > 0)
          continue;

        if (index == 0) {
          //ClauseWhere+= " AND (descVersionSt LIKE '%"+theTocken+"%'";//St.nomSt
          ClauseWhere += "  WHERE((nomObjetMetier  LIKE '%" + theTocken +
              "%') OR";

          ClauseWhere += "  (Attribut.nomAttribut   LIKE '%" + theTocken + "%')  ";
          ClauseWhere += " OR ";
          ClauseWhere += "  (defObjetMetier  LIKE '%" + theTocken + "%'))  ";
        }
        else {
          //ClauseWhere+= " "+Operateur+" descVersionSt LIKE '%"+theTocken+"%'";

          ClauseWhere += " " + this.Operateur + " ((nomObjetMetier LIKE '%" +
              theTocken + "%') OR";
          ClauseWhere += "  (Attribut.nomAttribut   LIKE '%" + theTocken + "%')  ";
          ClauseWhere += " OR ";
          ClauseWhere += "  (defObjetMetier LIKE '%" + theTocken + "%'))  ";
        }
        index++;
      }
    }
    return ClauseWhere;
  }

  public String getClauseWhereInterface(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;
    String ClauseWhere = "";
    if (Clef == null || Clef.equals(""))
    {
      DisplayRecherche="none";
    }
    else
    {
      DisplayRecherche = "";

      int index = 0;
      String theTocken = "";
      String newClef = "";
      if (Clef != null)
        newClef = Clef.replaceAll("'", "' ").replaceAll(",",
            " ").replaceAll("\\.", " ").replaceAll("\\;", " ");

      for (StringTokenizer t = new StringTokenizer(newClef, " ");
           t.hasMoreTokens(); ) {
        theTocken = t.nextToken().replaceAll("'", "''");

        String reqExclusion =
            "SELECT COUNT(mot) AS nb FROM Exclusion WHERE mot = '" + theTocken +
            "'";
        rs = myCnx.ExecReq(st, nomBase, reqExclusion);
        int nbExclusion = 0;
        try {
          rs.next();
          nbExclusion = rs.getInt(1);
        }
        catch (SQLException s) {
          s.getMessage();
        }
        if (nbExclusion > 0)
          continue;

        if (index == 0) {
          //ClauseWhere+= " AND (descVersionSt LIKE '%"+theTocken+"%'";//St.nomSt
          ClauseWhere += "  WHERE((ObjetMetier.nomObjetMetier  LIKE '%" + theTocken +
              "%') OR";
          ClauseWhere += "  (Attribut.nomAttribut   LIKE '%" + theTocken + "%'))  ";
        }
        else {
          //ClauseWhere+= " "+Operateur+" descVersionSt LIKE '%"+theTocken+"%'";

          ClauseWhere += " " + this.Operateur + " ((ObjetMetier.nomObjetMetier LIKE '%" +
              theTocken + "%') OR";
          ClauseWhere += "  (Attribut.nomAttribut  LIKE '%" + theTocken + "%'))  ";
        }
        index++;
      }
    }

    ClauseWhere +=" AND     (Interface.dumpHtml IS NOT NULL) AND (Interface.dumpHtml NOT LIKE '')";
    return ClauseWhere;
  }
  public String getClauseWhereInterface2(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;
    String ClauseWhere = "";
    if (Clef == null || Clef.equals(""))
    {
      DisplayRecherche="none";
    }
    else
    {
      DisplayRecherche = "";

      int index = 0;
      String theTocken = "";
      String newClef = "";
      if (Clef != null)
        newClef = Clef.replaceAll("'", "' ").replaceAll(",",
            " ").replaceAll("\\.", " ").replaceAll("\\;", " ");

      for (StringTokenizer t = new StringTokenizer(newClef, " ");
           t.hasMoreTokens(); ) {
        theTocken = t.nextToken().replaceAll("'", "''");

        String reqExclusion =
            "SELECT COUNT(mot) AS nb FROM Exclusion WHERE mot = '" + theTocken +
            "'";
        rs = myCnx.ExecReq(st, nomBase, reqExclusion);
        int nbExclusion = 0;
        try {
          rs.next();
          nbExclusion = rs.getInt(1);
        }
        catch (SQLException s) {
          s.getMessage();
        }
        if (nbExclusion > 0)
          continue;

        if (index == 0) {
          //ClauseWhere+= " AND (descVersionSt LIKE '%"+theTocken+"%'";//St.nomSt
          ClauseWhere += "  WHERE((ObjetMetier.nomObjetMetier  LIKE '%" + theTocken +
              "%') OR";
          ClauseWhere += "  (ObjetMetier.nomObjetMetier  LIKE '%" + theTocken + "%'))  ";
        }
        else {
          //ClauseWhere+= " "+Operateur+" descVersionSt LIKE '%"+theTocken+"%'";

          ClauseWhere += " " + this.Operateur + " ((ObjetMetier.nomObjetMetier LIKE '%" +
              theTocken + "%') OR";
          ClauseWhere += "  (ObjetMetier.nomObjetMetier LIKE '%" + theTocken + "%'))  ";
        }
        index++;
      }
    }

    ClauseWhere +=" AND     (Interface.dumpHtml IS NOT NULL) AND (Interface.dumpHtml NOT LIKE '')";
    return ClauseWhere;
  }

  public String getClauseWhereSt(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;
    String ClauseWhere = "";

    if (Clef == null || Clef.equals(""))
    {
      DisplayRecherche="none";
    }
    else
    {
      DisplayRecherche = "";

      int index = 0;
      String theTocken = "";
      String newClef = "";
      if (Clef != null)
        newClef = Clef.replaceAll("'", "' ").replaceAll(",",
            " ").replaceAll("\\.", " ").replaceAll("\\;", " ");

      for (StringTokenizer t = new StringTokenizer(newClef, " ");
           t.hasMoreTokens(); ) {
        theTocken = t.nextToken().replaceAll("'", "''");

        String reqExclusion =
            "SELECT COUNT(mot) AS nb FROM Exclusion WHERE mot = '" + theTocken +
            "'";
        rs = myCnx.ExecReq(st, nomBase, reqExclusion);
        int nbExclusion = 0;
        try {
          rs.next();
          nbExclusion = rs.getInt(1);
        }
        catch (SQLException s) {
          s.getMessage();
        }
        if (nbExclusion > 0)
          continue;

        if (index == 0) {
          //ClauseWhere+= " AND (descVersionSt LIKE '%"+theTocken+"%'";//St.nomSt
          ClauseWhere += "  AND((VersionSt.descVersionSt LIKE '%" + theTocken +
              "%'))";  
          /*
          ClauseWhere += "  AND((VersionSt.descVersionSt LIKE '%" + theTocken +
              "%') OR";
          ClauseWhere += "  (St.nomSt LIKE '%" + theTocken + "%') OR";
          ClauseWhere += "  ([Module].descModule LIKE '%" + theTocken +
              "%') OR";
          ClauseWhere += "  ([Module].nomModule LIKE '%" + theTocken + "%') OR";
          ClauseWhere += "  (Processus.descProcessus LIKE '%" + theTocken +
              "%') OR";
          ClauseWhere += "  (Roadmap.version LIKE '%" + theTocken + "%') OR";
          ClauseWhere += "  (Roadmap.description LIKE '%" + theTocken +
              "%') OR";
          ClauseWhere += "  (Processus.nomProcessus LIKE '%" + theTocken +
              "%'))  ";
          */
        }
        else {
          ClauseWhere+= " "+Operateur+" descVersionSt LIKE '%"+theTocken+"%'";

          ClauseWhere += " " + this.Operateur + " ((VersionSt.descVersionSt LIKE '%" +
              theTocken + "%') OR";
          ClauseWhere += "  (St.nomSt LIKE '%" + theTocken + "%') OR";
          ClauseWhere += "  ([Module].descModule LIKE '%" + theTocken +
              "%') OR";
          ClauseWhere += "  ([Module].nomModule LIKE '%" + theTocken + "%') OR";
          ClauseWhere += "  (Roadmap.version LIKE '%" + theTocken + "%') OR";
          ClauseWhere += "  (Roadmap.description LIKE '%" + theTocken +
              "%') OR";
          ClauseWhere += "  (Processus.descProcessus LIKE '%" + theTocken +
              "%') OR";
          ClauseWhere += "  (Processus.nomProcessus LIKE '%" + theTocken +
              "%'))  ";
        }
        index++;
      }
    }
    return ClauseWhere;
  }

  public String getClauseWhereActeur(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;
    String ClauseWhere = "";

    if (Clef == null || Clef.equals(""))
    {
      DisplayRecherche="none";
    }
    else
    {
      DisplayRecherche = "";

      int index = 0;
      String theTocken = "";
      String newClef = "";
      if (Clef != null)
        newClef = Clef.replaceAll("'", "' ").replaceAll(",",
            " ").replaceAll("\\.", " ").replaceAll("\\;", " ");

      for (StringTokenizer t = new StringTokenizer(newClef, " ");
           t.hasMoreTokens(); ) {
        theTocken = t.nextToken().replaceAll("'", "''");

        String reqExclusion =
            "SELECT COUNT(mot) AS nb FROM Exclusion WHERE mot = '" + theTocken +
            "'";
        rs = myCnx.ExecReq(st, nomBase, reqExclusion);
        int nbExclusion = 0;
        try {
          rs.next();
          nbExclusion = rs.getInt(1);
        }
        catch (SQLException s) {
          s.getMessage();
        }
        if (nbExclusion > 0)
          continue;

        if (index == 0) {
          ClauseWhere += "  WHERE((nomMembre   LIKE '%" + theTocken +
              "%') OR";
          ClauseWhere += "  (prenomMembre  LIKE '%" + theTocken + "%'))  ";
        }
        else {
          ClauseWhere += " " + this.Operateur + " ((nomMembre  LIKE '%" +
              theTocken + "%') OR";
          ClauseWhere += "  (prenomMembre LIKE '%" + theTocken + "%'))  ";
        }
        index++;
      }
    }
    return ClauseWhere;
  }
  public void getListeSt(String nomBase,Connexion myCnx, Statement st){

    req = "SELECT DISTINCT idVersionSt, nomSt FROM";
    req +=" (";    
    req +=" SELECT     idVersionSt, nomSt FROM         ListeST";
    req+= " WHERE     (nomSt LIKE '%"+this.Clef +"%')";
    req+= " OR((ListeST.descVersionSt LIKE '%"+this.Clef +"%'))";

    req+= " UNION";
    
    req += " SELECT DISTINCT VersionSt.idVersionSt, St.nomSt";
    req+= " FROM         Roadmap INNER JOIN";
    req+= "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+= "                       St ON VersionSt.stVersionSt = St.idSt";
    req+= " WHERE     (Roadmap.version LIKE '%"+this.Clef +"%') AND (St.isRecurrent = 0) AND (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt = 3)";
    req +=" )"; 
    req +=" as myTable";     
    
    req+= " ORDER BY nomSt";


    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    ST theST = null;
    try {
       while (rs.next()) {
         theST = new ST(rs.getInt(1), "idVersionSt");
         theST.nomSt = rs.getString(2);
         //theST.dump();
         this.ListeSt.addElement(theST);
       }
        } catch (SQLException s) {s.getMessage();}

  }
  
public void getListeProjets(String nomBase,Connexion myCnx, Statement st, Collaborateur theCollaborateur){

if (theCollaborateur.isManager == 1)
{
   req= " SELECT   DISTINCT  nomProjet, idRoadmap, version";
   req+= " FROM         ListeProjets";
   req+= " WHERE     ((serviceMoeVersionSt = "+theCollaborateur.service+") OR (serviceMoaVersionSt = "+theCollaborateur.service+"))";
   req+= " AND     (standby = 0) AND (LF_Month = - 1) AND (LF_Year = - 1) AND (NOT (nomProjet LIKE '%NON REGRESSION%')) AND (isMeeting <> 1)";
   req+= " AND (nomProjet LIKE '%"+this.Clef +"%')";
   req+= " OR (idRoadmap LIKE '%"+this.Clef +"%')";
   req+= " order by nomProjet";
}
else
{
   req="SELECT DISTINCT * FROM";
   req+= " (";
   req+= " SELECT     nomProjet, idRoadmap, version, standby, LF_Month, LF_Year, isMeeting";
   req+= " FROM         ListeProjets";
   req+= " WHERE     (respMoeVersionSt = "+theCollaborateur.id+") OR (respMoaVersionSt = "+theCollaborateur.id+")";

   req+= " UNION";

   req+= " SELECT     ListeProjets.nomProjet, ListeProjets.idRoadmap, ListeProjets.version, standby, LF_Month, LF_Year, isMeeting";
   req+= " FROM         Membre INNER JOIN";
   req+= "                       EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
   req+= "                       Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
   req+= "                       ListeProjets ON Equipe.nom = ListeProjets.nomSt";
   req+= " WHERE     (Membre.idMembre = "+theCollaborateur.id+") AND (Membre.isProjet = 1) ";
   req+= " ) as mytable";
   req+= " WHERE     (standby = 0) AND (LF_Month = - 1) AND (LF_Year = - 1) AND (NOT (nomProjet LIKE '%NON REGRESSION%')) AND (isMeeting <> 1)";
   req+= " AND (nomProjet LIKE '%"+this.Clef +"%')";
   req+= " OR (idRoadmap LIKE '%"+this.Clef +"%')";

   req+= " order by nomProjet";
}    

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Roadmap theRoadmap = null;
    try {
       while (rs.next()) {
         theRoadmap = new Roadmap(rs.getInt("idRoadmap"));
         theRoadmap.nomProjet = rs.getString("nomProjet");
         theRoadmap.id = rs.getInt("idRoadmap");
         theRoadmap.version = rs.getString("version");
         //theST.dump();
         this.ListeProjets.addElement(theRoadmap);
       }
        } catch (SQLException s) {s.getMessage();}

  }

public void getListeDevis(String nomBase,Connexion myCnx, Statement st, Collaborateur theCollaborateur){

if (theCollaborateur.isPo == 1)
{
   req= " SELECT     Devis.idRoadmap, St.nomSt, Roadmap.version";
   req+= " FROM         Devis INNER JOIN";
   req+= "                       Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
   req+= "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req+= "                       St ON VersionSt.stVersionSt = St.idSt";
   req+= " WHERE     Devis.idRoadmap LIKE '"+this.Clef +"%'";
}
   

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Roadmap theRoadmap = null;
    try {
       while (rs.next()) {
         theRoadmap = new Roadmap(rs.getInt("idRoadmap"));
         theRoadmap.nomProjet = rs.getString("nomSt") + '-' + rs.getString("version");
         //theST.dump();
         this.ListeDevis.addElement(theRoadmap);
       }
        } catch (SQLException s) {s.getMessage();}

  }
  
  public void getListeStStrict(String nomBase,Connexion myCnx, Statement st){

    req +="SELECT     idVersionSt, nomSt FROM         ListeST";
    req+= " WHERE     (nomSt LIKE '"+this.Clef +"%')";
    req+= " ORDER BY nomSt";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    ST theST = null;
    try {
       while (rs.next()) {
         theST = new ST(rs.getInt(1), "idVersionSt");
         theST.nomSt = rs.getString(2);
         //theST.dump();
         this.ListeSt.addElement(theST);
       }
        } catch (SQLException s) {s.getMessage();}

  }  
  
    public void getListeOmStrict(String nomBase,Connexion myCnx, Statement st){

    req +="SELECT     idObjetMetier, nomObjetMetier";
    req +=" FROM         ObjetMetier";
    req+= " WHERE     (nomObjetMetier LIKE '"+this.Clef +"%')";
    req +=" ORDER BY nomObjetMetier";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    OM theOM = null;
    try {
       while (rs.next()) {
         theOM = new OM(rs.getInt("idObjetMetier"));
         theOM.nomObjetMetier = rs.getString("nomObjetMetier");
         //theST.dump();
         this.ListeOm.addElement(theOM);
       }
        } catch (SQLException s) {s.getMessage();}

  } 

  public void getListeActeurs(String nomBase,Connexion myCnx, Statement st){

    req ="SELECT    idMembre, nomMembre, prenomMembre FROM         Membre";
    req +=this.getClauseWhereActeur( nomBase, myCnx,  st) + " ORDER BY nomMembre ASC";


    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try {
       while (rs.next()) {
          Collaborateur theCollaborateur = new Collaborateur(rs.getInt("idMembre"));
          theCollaborateur.nom = rs.getString("nomMembre");
          theCollaborateur.prenom = rs.getString("prenomMembre");
         this.ListeActeurs.addElement(theCollaborateur);
       }
        } catch (SQLException s) {s.getMessage();}

  }
  public void getListeOm(String nomBase,Connexion myCnx, Statement st){

    req ="SELECT idObjetMetier,nomObjetMetier  FROM   ObjetMetier ";
    req= "SELECT DISTINCT ObjetMetier.idObjetMetier, ObjetMetier.nomObjetMetier";
    req+="    FROM         ObjetMetier LEFT OUTER JOIN";
    req+="                   Attribut ON ObjetMetier.idObjetMetier = Attribut.omAttribut";


    req +=this.getClauseWhereOm( nomBase, myCnx,  st) + " ORDER BY nomObjetMetier ASC";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    OM theOM = null;
    try {
       while (rs.next()) {
         theOM = new OM(rs.getInt(1));
         theOM.nomObjetMetier = rs.getString(2);
         //theST.dump();
         this.ListeOm.addElement(theOM);
       }
        } catch (SQLException s) {s.getMessage();}

  }

  public void getListeInterface(String nomBase,Connexion myCnx, Statement st){

    req ="SELECT   DISTINCT  Interface.idInterface, St.nomSt + Interface.sensInterface + St_1.nomSt AS interface";
    req+="    FROM         ObjetMetier INNER JOIN";
    req+="                   Interface INNER JOIN";
    req+="                   St ON Interface.origineInterface = St.idSt INNER JOIN";
    req+="                   St AS St_1 ON Interface.extremiteInterface = St_1.idSt INNER JOIN";
    req+="                   Inter_OM ON Interface.idInterface = Inter_OM.interInter_OM ON ObjetMetier.idObjetMetier = Inter_OM.omInter_OM";
    req +=this.getClauseWhereInterface( nomBase, myCnx,  st) + " ORDER BY interface ASC";

    req="SELECT DISTINCT Interface.idInterface, St.nomSt + Interface.sensInterface + St_1.nomSt AS interface";
    req+="     FROM         ObjetMetier INNER JOIN";
    req+="                   Interface INNER JOIN";
    req+="                   St ON Interface.origineInterface = St.idSt INNER JOIN";
    req+="                   St AS St_1 ON Interface.extremiteInterface = St_1.idSt INNER JOIN";
    req+="                   Inter_OM ON Interface.idInterface = Inter_OM.interInter_OM ON ObjetMetier.idObjetMetier = Inter_OM.omInter_OM LEFT OUTER JOIN";
    req+="                   Attribut ON ObjetMetier.idObjetMetier = Attribut.omAttribut";
    req +=this.getClauseWhereInterface( nomBase, myCnx,  st) + " ORDER BY interface ASC";


    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Interface theInterface = null;
    try {
       while (rs.next()) {
         theInterface = new Interface(rs.getInt(1));
         theInterface.descInterface = rs.getString(2);
         //theST.dump();
         this.ListeInterface.addElement(theInterface);
       }
        } catch (SQLException s) {s.getMessage();}

  }


  public static void main(String[] args) {

    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    int idMembre;
    String NomProjet, previousActivite= "";
    float semaine =0;

    String reqRefSt="";
    st = myCnx.Connect();
    st2 = myCnx.Connect();
    /*
    MotClef themotclef = new MotClef("Cellule 2G","AND");
    themotclef.getListeSt(myCnx.nomBase,myCnx,st);
    themotclef.getListeOm(myCnx.nomBase,myCnx,st);
    */
    MotClef themotclef = new MotClef("Jakubo","AND");
    themotclef.getListeActeurs(myCnx.nomBase,myCnx,st);

    myCnx.Unconnect(st);
    myCnx.Unconnect(st2);
  }
}

package Organisation;  

import Composant.Excel;
import java.util.Vector;
import Organisation.Collaborateur;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import Projet.Roadmap;

import Projet.Action;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Row;
import java.util.Date;
import General.Utils;
import accesbase.ErrorSpecific;
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
public class Equipe {
  public int id = -1;
  public String nom="";
  public String old_nom="";
  public String description="";
  public Vector ListeMembres = new Vector(10);
  public Vector ListeTaches = new Vector(10);
  private String req="";
  private String type="";
  public static Vector ListeEquipe = new Vector(10);
  
  public int isKpi = 0;

  public Equipe(int id) {
    this.id = id;
    this.type = "id";
  }

  public Equipe(String nom) {
    this.nom = nom;
    this.old_nom = nom;
    this.type = "nom";
  }

  public static void getListeEquipe(String nomBase,Connexion myCnx, Statement st){

    String req="SELECT id, nom FROM Equipe WHERE (nom NOT IN (SELECT nomSt  FROM St)) order by nom asc";
    ListeEquipe.removeAllElements();

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Equipe theEquipe = null;
    try {
       while (rs.next()) {
         int id = rs.getInt("id");
         String nom = rs.getString("nom");
          theEquipe = new Equipe(id, nom,"");

         //theST.dump();
         ListeEquipe.addElement(theEquipe);
       }
        } catch (SQLException s) {s.getMessage();}

  }

  public  void getListeTachesOuvertes(String nomBase,Connexion myCnx, Statement st, int idEtat){

  req = "delete from tmpTaches";
  myCnx.ExecReq(st,nomBase,req,true);


  for (int i=0; i < this.ListeMembres.size(); i++)
  {
    Collaborateur theCollaborateur = (Collaborateur)this.ListeMembres.elementAt(i);
    req = "insert into tmpTaches";
    req+="    SELECT      id,nom, acteur, idEtat, idRoadmap, dateAction, dateFin, type, reponse, isPlanning, doc, Code, ChargePrevue, ChargeDevis, Semaine, RAF, ";
    req+="                   LoginEmetteur, priorite, dateCloture, idRoadmapLie, ";
    req+="                   dateAction_init, dateFin_init";
    req+=" FROM         actionSuivi";
    req+=" WHERE     (acteur LIKE '%"+theCollaborateur.Login+"%')";
    if (idEtat > 0)
    {
      req += "  AND (idEtat = " + idEtat + ")";
    }
    myCnx.ExecReq(st,nomBase,req,true);

  }





  this.ListeTaches.removeAllElements();
  String req="SELECT DISTINCT ";
         req+="              tmpTaches.id, tmpTaches.nom, tmpTaches.acteur, tmpTaches.idEtat, tmpTaches.idRoadmap, tmpTaches.dateAction, tmpTaches.dateFin, ";
         req+="              tmpTaches.type, tmpTaches.reponse, tmpTaches.isPlanning, tmpTaches.doc, tmpTaches.Code, tmpTaches.ChargePrevue, tmpTaches.ChargeDevis, ";
         req+="              tmpTaches.Semaine, tmpTaches.RAF, tmpTaches.LoginEmetteur, tmpTaches.priorite, TypeEtatAction.nom AS nomEtat, ";
         req+="              St.nomSt + '-' + Roadmap.version AS Projet, tmpTaches.idRoadmapLie";
         req+=" FROM         tmpTaches INNER JOIN";
         req+="              TypeEtatAction ON tmpTaches.idEtat = TypeEtatAction.id INNER JOIN";
         req+="              Roadmap ON tmpTaches.idRoadmap = Roadmap.idRoadmap INNER JOIN";
         req+="              VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
         req+="              St ON VersionSt.stVersionSt = St.idSt";
         req+=" WHERE     (idEtat = 1) OR  (idEtat = 2) OR      (idEtat = 5)";
         req+=" ORDER BY Projet";



  ResultSet rs = myCnx.ExecReq(st, nomBase, req);

  try {
     while (rs.next()) {
       int id = rs.getInt("id");
       Action theAction = new Action(id);
       theAction.nom = rs.getString("nom");
       theAction.acteur = rs.getString("acteur");
       try{
         theAction.date_start = rs.getDate("dateAction").toString();
       }
       catch (Exception e)
       {
         theAction.date_start="";
       }
       try{
         theAction.date_end = rs.getDate("dateFin").toString();
       }
       catch (Exception e)
       {
         theAction.date_end="";
       }

       theAction.Code = rs.getString("Code");
       theAction.ChargePrevue = rs.getFloat("ChargePrevue");
       theAction.ChargeDevis = rs.getFloat("ChargeDevis");
       theAction.LoginEmetteur = rs.getString("LoginEmetteur");
       theAction.nomEtat = rs.getString("nomEtat");

       theAction.projet = rs.getString("Projet");
       theAction.idRoadmapLie = rs.getInt("idRoadmapLie");

        this.ListeTaches.addElement(theAction);
     }
        } catch (SQLException s) {s.getMessage();}

  }  
  
  public  void getListeTachesByIdEtat(String nomBase,Connexion myCnx, Statement st, int idEtat){

  req = "delete from tmpTaches";
  myCnx.ExecReq(st,nomBase,req,true);


  for (int i=0; i < this.ListeMembres.size(); i++)
  {
    Collaborateur theCollaborateur = (Collaborateur)this.ListeMembres.elementAt(i);
    req = "insert into tmpTaches";
    req+="    SELECT      id,nom, acteur, idEtat, idRoadmap, dateAction, dateFin, type, reponse, isPlanning, doc, Code, ChargePrevue, ChargeDevis, Semaine, RAF, ";
    req+="                   LoginEmetteur, priorite, dateCloture, idRoadmapLie, ";
    req+="                   dateAction_init, dateFin_init";
    req+=" FROM         actionSuivi";
    req+=" WHERE     (acteur LIKE '%"+theCollaborateur.Login+"%')";
    if (idEtat > 0)
    {
      req += "  AND (idEtat = " + idEtat + ")";
    }
    myCnx.ExecReq(st,nomBase,req,true);

  }





  this.ListeTaches.removeAllElements();
  String req="SELECT DISTINCT ";
         req+="              tmpTaches.id, tmpTaches.nom, tmpTaches.acteur, tmpTaches.idEtat, tmpTaches.idRoadmap, tmpTaches.dateAction, tmpTaches.dateFin, ";
         req+="              tmpTaches.type, tmpTaches.reponse, tmpTaches.isPlanning, tmpTaches.doc, tmpTaches.Code, tmpTaches.ChargePrevue, tmpTaches.ChargeDevis, ";
         req+="              tmpTaches.Semaine, tmpTaches.RAF, tmpTaches.LoginEmetteur, tmpTaches.priorite, TypeEtatAction.nom AS nomEtat, ";
         req+="              St.nomSt + '-' + Roadmap.version AS Projet, tmpTaches.idRoadmapLie";
         req+=" FROM         tmpTaches INNER JOIN";
         req+="              TypeEtatAction ON tmpTaches.idEtat = TypeEtatAction.id INNER JOIN";
         req+="              Roadmap ON tmpTaches.idRoadmap = Roadmap.idRoadmap INNER JOIN";
         req+="              VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
         req+="              St ON VersionSt.stVersionSt = St.idSt";
         req+=" ORDER BY Projet";



  ResultSet rs = myCnx.ExecReq(st, nomBase, req);

  try {
     while (rs.next()) {
       int id = rs.getInt("id");
       Action theAction = new Action(id);
       theAction.nom = rs.getString("nom");
       theAction.acteur = rs.getString("acteur");
       try{
         theAction.date_start = rs.getDate("dateAction").toString();
       }
       catch (Exception e)
       {
         theAction.date_start="";
       }
       try{
         theAction.date_end = rs.getDate("dateFin").toString();
       }
       catch (Exception e)
       {
         theAction.date_end="";
       }

       theAction.Code = rs.getString("Code");
       theAction.ChargePrevue = rs.getFloat("ChargePrevue");
       theAction.ChargeDevis = rs.getFloat("ChargeDevis");
       theAction.LoginEmetteur = rs.getString("LoginEmetteur");
       theAction.nomEtat = rs.getString("nomEtat");

       theAction.projet = rs.getString("Projet");
       theAction.idRoadmapLie = rs.getInt("idRoadmapLie");

        this.ListeTaches.addElement(theAction);
     }
        } catch (SQLException s) {s.getMessage();}

  }
  public  void getListeTachesClosesByMonth(String nomBase,Connexion myCnx, Statement st, int theYearRef, int num_mois){

  req = "delete from tmpTaches";
  myCnx.ExecReq(st,nomBase,req,true);


  for (int i=0; i < this.ListeMembres.size(); i++)
  {
    Collaborateur theCollaborateur = (Collaborateur)this.ListeMembres.elementAt(i);
    req = "insert into tmpTaches";
    req+=" SELECT     id, nom, acteur, idEtat, idRoadmap, dateAction, dateFin, type, reponse, isPlanning, doc, Code, ChargePrevue, ChargeDevis, Semaine, RAF, ";
    req+="                   LoginEmetteur, priorite, dateCloture, idRoadmapLie";
    req+=" FROM         actionSuivi";
    req+=" WHERE     (acteur LIKE '%"+theCollaborateur.Login+"%') AND (idEtat = 3) AND (MONTH(dateCloture) = "+num_mois+") AND (YEAR(dateCloture) = "+theYearRef+")";

    myCnx.ExecReq(st,nomBase,req,true);

  }

  this.ListeTaches.removeAllElements();
  String req="SELECT DISTINCT ";
         req+="              tmpTaches.id, tmpTaches.nom, tmpTaches.acteur, tmpTaches.idEtat, tmpTaches.idRoadmap, tmpTaches.dateAction, tmpTaches.dateFin, ";
         req+="              tmpTaches.type, tmpTaches.reponse, tmpTaches.isPlanning, tmpTaches.doc, tmpTaches.Code, tmpTaches.ChargePrevue, tmpTaches.ChargeDevis, ";
         req+="              tmpTaches.Semaine, tmpTaches.RAF, tmpTaches.LoginEmetteur, tmpTaches.priorite, TypeEtatAction.nom AS nomEtat, ";
         req+="              St.nomSt + '-' + Roadmap.version AS Projet, tmpTaches.idRoadmapLie";
         req+=" FROM         tmpTaches INNER JOIN";
         req+="              TypeEtatAction ON tmpTaches.idEtat = TypeEtatAction.id INNER JOIN";
         req+="              Roadmap ON tmpTaches.idRoadmap = Roadmap.idRoadmap INNER JOIN";
         req+="              VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
         req+="              St ON VersionSt.stVersionSt = St.idSt";
         req+=" ORDER BY Projet";



  ResultSet rs = myCnx.ExecReq(st, nomBase, req);

  try {
     while (rs.next()) {
       int id = rs.getInt("id");
       Action theAction = new Action(id);
       theAction.nom = rs.getString("nom");
       try{
         theAction.date_start = rs.getDate("dateAction").toString();
       }
       catch (Exception e)
       {
         theAction.date_start="";
       }
       try{
         theAction.date_end = rs.getDate("dateFin").toString();
       }
       catch (Exception e)
       {
         theAction.date_end="";
       }

       theAction.Code = rs.getString("Code");
       theAction.ChargePrevue = rs.getFloat("ChargePrevue");
       theAction.nomEtat = rs.getString("nomEtat");
       theAction.projet = rs.getString("Projet");

        this.ListeTaches.addElement(theAction);
     }
        } catch (SQLException s) {s.getMessage();}

  }

  public  void getListeTachesTraiteByMonth(String nomBase,Connexion myCnx, Statement st, int theYearRef, int num_mois){


  this.ListeTaches.removeAllElements();
  String req=" SELECT DISTINCT ";
      req+="                 actionSuivi.id, actionSuivi.Code, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, ";
      req+="                 actionSuivi.dateFin, actionSuivi.type, actionSuivi.reponse, actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.ChargePrevue, actionSuivi.ChargeDevis,";
      req+="                  actionSuivi.Semaine, actionSuivi.RAF, actionSuivi.LoginEmetteur, actionSuivi.priorite, actionSuivi.dateCloture, ";
      req+="                 St.nomSt + '-' + Roadmap.version AS Projet";
      req+=" FROM         actionSuivi INNER JOIN";
      req+="                 Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap INNER JOIN";
      req+="                 VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
      req+="                 St ON VersionSt.stVersionSt = St.idSt";


     req+=" WHERE     (MONTH(dateCloture) = "+num_mois+") AND (YEAR(dateCloture) = "+theYearRef+")";
     req+=" AND";
     req+=" (";

     for (int i=0; i < this.ListeMembres.size(); i++)
     {
       Collaborateur theCollaborateur = (Collaborateur)this.ListeMembres.elementAt(i);
       if (i > 0)  req += " OR";
       req += " (acteur LIKE '%"+theCollaborateur.Login+"%') ";

     }
     req+=" )";

     req+=" ORDER BY actionSuivi.Code";





  ResultSet rs = myCnx.ExecReq(st, nomBase, req);

  try {
     while (rs.next()) {
       int id = rs.getInt("id");
       Action theAction = new Action(id);
       theAction.Code = rs.getString("Code");
       theAction.nom = rs.getString("nom");
       try{
         theAction.date_start = rs.getDate("dateAction").toString();
       }
       catch (Exception e)
       {
         theAction.date_start="";
       }
       theAction.datePrevue= rs.getDate("dateFin");
       try{
         theAction.date_end = theAction.datePrevue.toString();
       }
       catch (Exception e)
       {
         theAction.date_end="";
       }

       theAction.ChargePrevue = rs.getFloat("ChargePrevue");
       //theAction.nomEtat = rs.getString("nomEtat");
       theAction.dateRelle= rs.getDate("dateCloture");
       try{
         theAction.dateCloture = theAction.dateRelle.toString();
       }
       catch (Exception e)
       {
         theAction.dateCloture="";
       }

       theAction.projet = rs.getString("Projet");

        this.ListeTaches.addElement(theAction);
     }
        } catch (SQLException s) {s.getMessage();}

  }

public  void getListeTachesAnomaliesByMonth(String nomBase,Connexion myCnx, Statement st, int theYearRef, int num_mois){


  this.ListeTaches.removeAllElements();
  String req="SELECT DISTINCT ";
         req+="             actionSuivi.id, actionSuivi.Code, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, ";
         req+="              actionSuivi.dateFin, actionSuivi.type, actionSuivi.reponse, actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.ChargePrevue, actionSuivi.ChargeDevis,";
         req+="               actionSuivi.Semaine, actionSuivi.RAF, actionSuivi.LoginEmetteur, actionSuivi.priorite, actionSuivi.dateCloture, ";
         req+="              St.nomSt + '-' + Roadmap.version AS Projet";
         req+=" FROM         actionSuivi INNER JOIN";
         req+="              Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap INNER JOIN";
         req+="              VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
         req+="              St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
         req+="              ActionAnomalie ON actionSuivi.id = ActionAnomalie.idAction";
         req+=" WHERE     (MONTH(ActionAnomalie.dateAnomalie) = "+num_mois+") AND (YEAR(ActionAnomalie.dateAnomalie) = "+theYearRef+")";


     req+=" AND";
     req+=" (";

     for (int i=0; i < this.ListeMembres.size(); i++)
     {
       Collaborateur theCollaborateur = (Collaborateur)this.ListeMembres.elementAt(i);
       if (i > 0)  req += " OR";
       req += " (acteur LIKE '%"+theCollaborateur.Login+"%') ";

     }
     req+=" )";

     req+=" ORDER BY actionSuivi.Code";


  ResultSet rs = myCnx.ExecReq(st, nomBase, req);

  try {
     while (rs.next()) {
       int id = rs.getInt("id");
       Action theAction = new Action(id);
       theAction.Code = rs.getString("Code");
       theAction.nom = rs.getString("nom");
       try{
         theAction.date_start = rs.getDate("dateAction").toString();
       }
       catch (Exception e)
       {
         theAction.date_start="";
       }
       theAction.datePrevue= rs.getDate("dateFin");
       try{
         theAction.date_end = theAction.datePrevue.toString();
       }
       catch (Exception e)
       {
         theAction.date_end="";
       }

       theAction.ChargePrevue = rs.getFloat("ChargePrevue");
       //theAction.nomEtat = rs.getString("nomEtat");
       theAction.dateRelle= rs.getDate("dateCloture");
       try{
         theAction.dateCloture = theAction.dateRelle.toString();
       }
       catch (Exception e)
       {
         theAction.dateCloture="";
       }

       theAction.projet = rs.getString("Projet");

        this.ListeTaches.addElement(theAction);
     }
        } catch (SQLException s) {s.getMessage();}

  }

  public  void getListeTachesStByMonth(String nomBase,Connexion myCnx, Statement st, int theYearRef){


    this.ListeTaches.removeAllElements();
    String req="SELECT DISTINCT ";
           req+="           actionSuivi.id, actionSuivi.Code, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, ";
           req+="            actionSuivi.dateFin, actionSuivi.type, actionSuivi.reponse, actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.ChargePrevue, actionSuivi.ChargeDevis,";
           req+="             actionSuivi.Semaine, actionSuivi.RAF, actionSuivi.LoginEmetteur, actionSuivi.priorite, actionSuivi.dateCloture, ";
           req+="            St.nomSt + '-' + Roadmap.version AS Projet";
           req+=" FROM         VersionSt INNER JOIN";
           req+="            Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt INNER JOIN";
           req+="            St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
           req+="            actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmapLie";
           req+=" WHERE     (YEAR(dateCloture) = "+theYearRef+")";


       req+=" AND";
       req+=" (";

       for (int i=0; i < this.ListeMembres.size(); i++)
       {
         Collaborateur theCollaborateur = (Collaborateur)this.ListeMembres.elementAt(i);
         if (i > 0)  req += " OR";
         req += " (acteur LIKE '%"+theCollaborateur.Login+"%') ";

       }
       req+=" )";

       req+=" ORDER BY Projet, actionSuivi.dateCloture";


    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try {
       while (rs.next()) {
         int id = rs.getInt("id");
         Action theAction = new Action(id);
         theAction.Code = rs.getString("Code");
         theAction.nom = rs.getString("nom");
         try{
           theAction.date_start = rs.getDate("dateAction").toString();
         }
         catch (Exception e)
         {
           theAction.date_start="";
         }
         theAction.datePrevue= rs.getDate("dateFin");
         try{
           theAction.date_end = theAction.datePrevue.toString();
         }
         catch (Exception e)
         {
           theAction.date_end="";
         }

         theAction.ChargePrevue = rs.getFloat("ChargePrevue");
         //theAction.nomEtat = rs.getString("nomEtat");
         theAction.dateRelle= rs.getDate("dateCloture");
         try{
           theAction.dateCloture = theAction.dateRelle.toString();
         }
         catch (Exception e)
         {
           theAction.dateCloture="";
         }

         theAction.projet = rs.getString("Projet");

          this.ListeTaches.addElement(theAction);
       }
          } catch (SQLException s) {s.getMessage();}

    }
  public  void getListeTachesProjetByMonth(String nomBase,Connexion myCnx, Statement st, int theYearRef){


    this.ListeTaches.removeAllElements();
    String req="SELECT DISTINCT ";
           req+="           actionSuivi.id, actionSuivi.Code, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, ";
           req+="            actionSuivi.dateFin, actionSuivi.type, actionSuivi.reponse, actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.ChargePrevue, actionSuivi.ChargeDevis,";
           req+="             actionSuivi.Semaine, actionSuivi.RAF, actionSuivi.LoginEmetteur, actionSuivi.priorite, actionSuivi.dateCloture, ";
           req+="            St.nomSt + '-' + Roadmap.version AS Projet";
           req+=" FROM         VersionSt INNER JOIN";
           req+="            Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt INNER JOIN";
           req+="            St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
           req+="            actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap";
           req+=" WHERE     (YEAR(actionSuivi.dateCloture) = "+theYearRef+")";



       req+=" AND";
       req+=" (";

       for (int i=0; i < this.ListeMembres.size(); i++)
       {
         Collaborateur theCollaborateur = (Collaborateur)this.ListeMembres.elementAt(i);
         if (i > 0)  req += " OR";
         req += " (acteur LIKE '%"+theCollaborateur.Login+"%') ";

       }
       req+=" )";

       req+=" ORDER BY Projet, actionSuivi.dateCloture";


    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try {
       while (rs.next()) {
         int id = rs.getInt("id");
         Action theAction = new Action(id);
         theAction.Code = rs.getString("Code");
         theAction.nom = rs.getString("nom");
         try{
           theAction.date_start = rs.getDate("dateAction").toString();
         }
         catch (Exception e)
         {
           theAction.date_start="";
         }
         theAction.datePrevue= rs.getDate("dateFin");
         try{
           theAction.date_end = theAction.datePrevue.toString();
         }
         catch (Exception e)
         {
           theAction.date_end="";
         }

         theAction.ChargePrevue = rs.getFloat("ChargePrevue");
         //theAction.nomEtat = rs.getString("nomEtat");
         theAction.dateRelle= rs.getDate("dateCloture");
         try{
           theAction.dateCloture = theAction.dateRelle.toString();
         }
         catch (Exception e)
         {
           theAction.dateCloture="";
         }

         theAction.projet = rs.getString("Projet");

          this.ListeTaches.addElement(theAction);
       }
          } catch (SQLException s) {s.getMessage();}

    }



  public  void getListeTachesDistinctes(String nomBase,Connexion myCnx, Statement st, Statement st2){

    ResultSet rs =null;
    ResultSet rs2 =null;

  req = "delete from tmpTaches";
  myCnx.ExecReq(st,nomBase,req,true);

  this.ListeTaches.removeAllElements();

  for (int i=0; i < this.ListeMembres.size(); i++)
  {
    Collaborateur theCollaborateur = (Collaborateur)this.ListeMembres.elementAt(i);
    req="    SELECT      id,nom, acteur, idEtat, idRoadmap, dateAction, dateFin, type, reponse, isPlanning, doc, Code, ChargePrevue, ChargeDevis, Semaine, RAF, ";
    req+="                   LoginEmetteur, priorite, dateCloture, idRoadmapLie";
    req+=" FROM         actionSuivi";
    req+=" WHERE     (acteur LIKE '%"+theCollaborateur.Login+"%')";
    rs = myCnx.ExecReq(st, nomBase, req);

    // Bouclage sur les taches lues de action suivi
    try {
       while (rs.next()) {
         int id = rs.getInt("id");
         Action theAction = new Action(id);
         theAction.nom = rs.getString("nom");
         theAction.idEtat = rs.getInt("idEtat");
         theAction.idRoadmap = rs.getInt("idRoadmap");
         Date dateAction = rs.getDate("dateAction");
         theAction.date_start="";
         try{
             theAction.date_start = "convert(datetime, '" + dateAction.getDate() + "/" + (dateAction.getMonth() + 1) + "/" +
                 (1900 +dateAction.getYear()) + "', 103)";
           }
           catch (Exception e){}

         theAction.Code = rs.getString("Code");

         req="    SELECT      count(*) as nb";
         req+=" FROM         tmpTaches";
         req+=" WHERE     id ="+theAction.id;
         rs2=myCnx.ExecReq(st2,nomBase,req,true);
         int nb = 0;
         try {
           while (rs2.next()) {
             nb = rs2.getInt("nb");
           }
         }   catch (SQLException s) {s.getMessage();}

         if (nb ==0)
         {
           this.ListeTaches.addElement(theAction);

           req = "INSERT tmpTaches (id, nom, idEtat, idRoadmap, dateAction) VALUES (" +
               theAction.id + ",'" + theAction.Code + "'," + theAction.idEtat  + "," + theAction.idRoadmap+ "," + theAction.date_start+ ")";
           myCnx.ExecReq(st2,nomBase,req,true);
         }

       }
        } catch (SQLException s) {s.getMessage();}
    // si la tache n'existe pas dans tmpTache
    // insertion dans tmpTache
    // insertion dans listeAction

  }



  }

public  int getListeAnomaliesByMonthByYear(String nomBase,Connexion myCnx, Statement st, int mois, int annee){

  req = "delete from tmpTaches";
  myCnx.ExecReq(st,nomBase,req,true);


  for (int i=0; i < this.ListeMembres.size(); i++)
  {
    Collaborateur theCollaborateur = (Collaborateur)this.ListeMembres.elementAt(i);
    req = "insert into tmpTaches";
    req+="    SELECT      id,nom, acteur, idEtat, idRoadmap, dateAction, dateFin, type, reponse, isPlanning, doc, Code, ChargePrevue, ChargeDevis, Semaine, RAF, ";
    req+="                   LoginEmetteur, priorite, dateCloture, idRoadmapLie, ";
    req+="                   dateAction_init, dateFin_init";
    req+=" FROM         actionSuivi";
    req+=" WHERE     (acteur LIKE '%"+theCollaborateur.Login+"%')";

    myCnx.ExecReq(st,nomBase,req,true);

  }



  this.ListeTaches.removeAllElements();
  String req="SELECT     COUNT(idAction) AS nb";
      req+=" FROM         ActionAnomalie";
      req+=" WHERE   idAction IN   (SELECT      id from tmpTaches) ";
      req+=" AND (MONTH(dateAnomalie) = "+mois+") AND (YEAR(dateAnomalie) = "+annee+")";


  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  int nb = 0;
  try {
     while (rs.next()) {
       nb = rs.getInt("nb");

     }
        } catch (SQLException s) {s.getMessage();}

  return nb;
  }
  public  void getListeTachesByMonthByYear(String nomBase,Connexion myCnx, Statement st, int mois, int annee){

  req = "delete from tmpTaches";
  myCnx.ExecReq(st,nomBase,req,true);


  for (int i=0; i < this.ListeMembres.size(); i++)
  {
    Collaborateur theCollaborateur = (Collaborateur)this.ListeMembres.elementAt(i);
    req = "insert into tmpTaches";
    req+="    SELECT      id,nom, acteur, idEtat, idRoadmap, dateAction, dateFin, type, reponse, isPlanning, doc, Code, ChargePrevue, ChargeDevis, Semaine, RAF, ";
    req+="                   LoginEmetteur, priorite, dateCloture, idRoadmapLie, dateAction_init, dateFin_init";
    req+=" FROM         actionSuivi";
    req+=" WHERE     (acteur LIKE '%"+theCollaborateur.Login+"%')";
    req+=" AND (MONTH(dateCloture) = "+mois+") AND (YEAR(dateCloture) = "+annee+")";

    myCnx.ExecReq(st,nomBase,req,true);

  }



  this.ListeTaches.removeAllElements();
  String req="SELECT DISTINCT ";
         req+="              tmpTaches.id, tmpTaches.nom, tmpTaches.acteur, tmpTaches.idEtat, tmpTaches.idRoadmap, tmpTaches.dateAction, tmpTaches.dateFin, ";
         req+="              tmpTaches.type, tmpTaches.reponse, tmpTaches.isPlanning, tmpTaches.doc, tmpTaches.Code, tmpTaches.ChargePrevue, tmpTaches.ChargeDevis, ";
         req+="              tmpTaches.Semaine, tmpTaches.RAF, tmpTaches.LoginEmetteur, tmpTaches.priorite, TypeEtatAction.nom AS nomEtat, ";
         req+="              St.nomSt + '-' + Roadmap.version AS Projet, tmpTaches.dateCloture,";
         req+="              tmpTaches.dateAction_init, tmpTaches.dateFin_init";
         req+=" FROM         tmpTaches INNER JOIN";
         req+="              TypeEtatAction ON tmpTaches.idEtat = TypeEtatAction.id INNER JOIN";
         req+="              Roadmap ON tmpTaches.idRoadmap = Roadmap.idRoadmap INNER JOIN";
         req+="              VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
         req+="              St ON VersionSt.stVersionSt = St.idSt";
         req+=" ORDER BY Projet";



  ResultSet rs = myCnx.ExecReq(st, nomBase, req);

  try {
     while (rs.next()) {
       int id = rs.getInt("id");
       Action theAction = new Action(id);
       theAction.nom = rs.getString("nom");
       theAction.idRoadmap = rs.getInt("idRoadmap");
       try{
         theAction.date_start = rs.getDate("dateAction").toString();
       }
       catch (Exception e)
       {
         theAction.date_start="";
       }
       try{
         theAction.date_end = rs.getDate("dateFin").toString();
       }
       catch (Exception e)
       {
         theAction.date_end="";
       }

       theAction.Code = rs.getString("Code");
       theAction.ChargePrevue = rs.getFloat("ChargePrevue");
       theAction.nomEtat = rs.getString("nomEtat");
       theAction.projet = rs.getString("Projet");

       try{
         theAction.dateCloture = rs.getDate("dateCloture").toString();
       }
       catch (Exception e)
       {
         theAction.dateCloture="";
       }

       try{
         theAction.date_start_init = rs.getDate("dateAction_init").toString();
       }
       catch (Exception e)
       {
         theAction.date_start_init="";
       }
       try{
         theAction.date_end_init = rs.getDate("dateFin_init").toString();
       }
       catch (Exception e)
       {
         theAction.date_end_init="";
       }

        this.ListeTaches.addElement(theAction);




     }
        } catch (SQLException s) {s.getMessage();}


  }

  public Collaborateur addListeCollaborateur(int id)
 {
    Collaborateur theCollaborateur = new Collaborateur(id);
    this.ListeMembres.addElement(theCollaborateur);
    return theCollaborateur;
  }

  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();

    this.nom = this.nom.replaceAll("\u0092","'");
    this.nom = this.nom.replaceAll("'","''");
    this.nom = this.nom.replaceAll("\r","");
    this.nom = this.nom.replaceAll("\\\\","\\\\\\\\");

    if (this.description != null)
    {
    this.description = this.description.replaceAll("\u0092","'");
    this.description = this.description.replaceAll("'","''");
    }
    else this.description = "";

    req = "INSERT Equipe (nom, [desc], isKpi) VALUES ('"+this.nom+"','"+this.description+"','"+this.isKpi+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    if (myError.cause == -1) return myError;

     this.old_nom = this.nom;
     this.setId( nomBase, myCnx,  st );
     
     myError = this.bd_Enreg( nomBase, myCnx,  st,  transaction);
     return myError;


  }
  
  public ErrorSpecific bd_InsertKpis(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();

    req = "DELETE FROM typeGraphes  WHERE idEquipe = '"+this.id+"'";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertKpis",""+this.id);
    if (myError.cause == -1) return myError;
    
    req = "INSERT typeGraphes  (nom, ordre, idEquipe) VALUES ('Charges','1','"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertKpis",""+this.id);
    if (myError.cause == -1) return myError;
    
    req = "INSERT typeGraphes  (nom, ordre, idEquipe) VALUES ('Delais','2','"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertKpis",""+this.id);
    if (myError.cause == -1) return myError;
    
    req = "INSERT typeGraphes  (nom, ordre, idEquipe) VALUES ('Anomalies','3','"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertKpis",""+this.id);
    if (myError.cause == -1) return myError;
    
    req = "INSERT typeGraphes  (nom, ordre, idEquipe) VALUES ('ST','4','"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertKpis",""+this.id);
    if (myError.cause == -1) return myError;
    
    req = "INSERT typeGraphes  (nom, ordre, idEquipe) VALUES ('Projet','5','"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertKpis",""+this.id);
    if (myError.cause == -1) return myError;
    
    req = "INSERT typeGraphes  (nom, ordre, idEquipe) VALUES ('Etat','6','"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertKpis",""+this.id);
    if (myError.cause == -1) return myError;
    
    req = "INSERT typeGraphes  (nom, ordre, idEquipe) VALUES ('Efficacite','7','"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertKpis",""+this.id);
    if (myError.cause == -1) return myError;    


    return myError;
  }  

  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();

    this.nom = this.nom.replaceAll("\u0092","'");
    this.nom = this.nom.replaceAll("'","''");
    this.nom = this.nom.replaceAll("\r","");
    this.nom = this.nom.replaceAll("\\\\","\\\\\\\\");
    String description="";
    if (this.description != null)
    {
      for (int j=0; j < this.description.length();j++)
      {
        int c = (int)this.description.charAt(j);
        if (c != 34)
          {
            description += this.description.charAt(j);
          }
          else
          {
            description +="'";
          }
      }
      description = description.replaceAll("'","''").replaceAll("\r","");
         }

    req = "UPDATE Equipe SET [nom]= ('"+this.nom+"') WHERE id="+this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
    if (myError.cause == -1) return myError;

    req = "UPDATE Equipe SET [desc]= ('"+description+"') WHERE id="+this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
    if (myError.cause == -1) return myError;
    
    req = "UPDATE Equipe SET [isKpi]= ('"+this.isKpi+"') WHERE id="+this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
    if (myError.cause == -1) return myError;    

     myError = this.bd_Enreg( nomBase, myCnx,  st,  transaction);
     return myError;

  }

  public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();
    //req = "delete  FROM    EquipeMembre WHERE (type = 'Collaborateur') AND (idMembreEquipe = "+this.id+")";
    req = "delete  FROM    EquipeMembre WHERE (idMembreEquipe = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    if (myError.cause == -1) return myError;

    req = "delete  FROM    Equipe WHERE (id = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    if (myError.cause == -1) return myError;

    req = "delete  FROM    typeGraphes  WHERE (idEquipe = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    if (myError.cause == -1) return myError;
    
     return myError;

  }
  
  public ErrorSpecific bd_InsertCollaborateur(String nomBase,Connexion myCnx, Statement st, int idMembre, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();
    
    // test s'il existe une structure du m�me nom
    req = "SELECT     COUNT(*) AS nb FROM         EquipeMembre WHERE     (idMembreEquipe = "+this.id+") AND (idMembre = "+idMembre+")";
    rs = myCnx.ExecReq(st, nomBase, req);
    int nb=1;
    try {
       while (rs.next()) {
         nb= rs.getInt("nb");
       }
        } catch (SQLException s) {s.getMessage();}    

    if (nb > 0) return myError;
    
    req = "INSERT EquipeMembre (idMembre, idMembreEquipe, type) VALUES ("+idMembre + "," + this.id + ",'Collaborateur'" + ")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertCollaborateur",""+this.id);
    if (myError.cause == -1) return myError;    
    
    return myError;
  }
  
  public ErrorSpecific bd_Enreg(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();
    req = "delete  FROM    EquipeMembre WHERE (type = 'Collaborateur') AND (idMembreEquipe = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    if (myError.cause == -1) return myError;
    
    // test s'il existe une structure du m�me nom
    req = "SELECT     Membre.idMembre, Membre.serviceMembre";
    req+="    FROM         Service INNER JOIN";
    req+="                   Membre ON Service.idService = Membre.serviceMembre";
    req+=" WHERE     (Service.nomService = '"+this.nom+"')";


    // si oui
    // lecture des membre de la structure
      // insertion des membres de la structure

    int idService = -1;

    rs = myCnx.ExecReq(st, nomBase, req);
    int nb=1;
    try {
       while (rs.next()) {
         int id = rs.getInt("idMembre");
         idService = rs.getInt("serviceMembre");
         Collaborateur theCollaborateur = new Collaborateur(id);
         if (nb == 1) this.ListeMembres.removeAllElements();

           this.ListeMembres.addElement(theCollaborateur);
         nb++;
       }
        } catch (SQLException s) {s.getMessage();}


      if (nb >1)
      {
          req = "UPDATE    Membre SET   isProjet = 1 WHERE     (serviceMembre  = "+idService+")";
          //if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
      }



      // si non
      // insertion des membres de l'�quipe

      for (int i = 0; i < this.ListeMembres.size(); i++) {
        Collaborateur theCollaborateur = (Collaborateur)this.ListeMembres.
            elementAt(i);

        req = "INSERT EquipeMembre (idMembre, idMembreEquipe, type) VALUES (" +
            theCollaborateur.id + "," + this.id + ",'Collaborateur'" + ")";
        myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
        if (myError.cause == -1) return myError;
      }




     return myError;

  }

  public  void excelExportTaches3(String nomBase,Connexion myCnx, Statement st, Statement st2,int theYearRef, String Type){
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    st2 = myCnx.Connect();


    String[] entete = {"Projet","Tache", "Acteurs", "Etat", "Debut", "Fin", "Prevu", "Consomme", "RAD", "RAF"};
    Excel theExport = new Excel("doc/tache/exportExcel",entete);
    String location = theExport.getClass().getResource("").getFile();
    location=location.substring(1);

    location = location.replaceAll("%20","\\ ");

    // ----------------------------- Extraction de la directory ou exporter le fichier ------//
    int pos=-1; //
    pos = location.indexOf("WEB-INF");
    if (pos > -1)
      location = location.substring(0,pos)+theExport.directory;

    theExport.xl_delete();
    //myCnx.trace("1---------- delete","location",""+location);

    // --------------------------------------------------------------------------------------------------//


    theExport.attach(location+"/ExportTaches.xls"); // nom du fichier excel horodate
    //myCnx.trace("2---------- attach","location",""+location+"/ExportTaches_"+this.id+".xls");

    //theExport.xl_open_update();
    theExport.xl_open_create("excelTaches"); // feuille de calcul

    this.getAllFromBd(myCnx.nomBase,myCnx,st);
    this.getListeMembres(myCnx.nomBase,myCnx,st);
    if (Type.equals("Toutes"))
        this.getListeTachesByIdEtat(myCnx.nomBase,myCnx,st, -1);
    else
        this.getListeTachesOuvertes(myCnx.nomBase,myCnx,st, -1);
   

    int col = 0;
    theExport.sheet.setColumnWidth(col, 256*20); // A
    theExport.sheet.setColumnWidth(++col, 256*44); // B
    theExport.sheet.setColumnWidth(++col, 256*42); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*8); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*10); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*10); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*5); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*10); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*6); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*6); // fixer la largeur de la 1ere colonne

    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet.createFreezePane(2,1) ;

    
    int numRow = 0;
    Row row = theExport.sheet.createRow(numRow);
    theExport.xl_writeEntete(row,0,theExport.entete);
    numRow++;  
    



for (int i=0; i < this.ListeTaches.size(); i++) {
  Action theTache = (Action)this.ListeTaches.elementAt(i);
  float Global_Consomme = theTache.getChargesConsommees(myCnx.nomBase,myCnx,  st);
  //String str_Global_Consomme = (""+Global_Consomme).replace('.',',');
  String str_Global_Consomme = (""+Global_Consomme);

  float RAD = theTache.ChargePrevue - Global_Consomme;
  //String str_RAD = (""+RAD).replace('.',',');
  String str_RAD = (""+RAD);

  //String str_Consomme = (""+theTache.ChargePrevue).replace('.',',');
  String str_Consomme = (""+theTache.ChargePrevue);
  
     
      int numCol = 0;
      row = theExport.sheet.createRow(numRow);
      
      if (theTache.projet != null)
          theExport.xl_write(row, numCol, "" + theTache.projet.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTache.Code != null)
          theExport.xl_write(row, ++numCol, "" + theTache.Code.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTache.acteur != null)
          theExport.xl_write(row, ++numCol, "" + theTache.acteur.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTache.nomEtat != null)
          theExport.xl_write(row, ++numCol, "" + theTache.nomEtat.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTache.date_start != null)
          theExport.xl_write(row, ++numCol, "" + theTache.date_start.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTache.date_end != null)
          theExport.xl_write(row, ++numCol, "" + theTache.date_end.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (str_Consomme != null)
          theExport.xl_write(row, ++numCol, "" + str_Consomme.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""), HSSFCell.CELL_TYPE_NUMERIC);

      if (str_Global_Consomme != null)
          theExport.xl_write(row, ++numCol, "" + str_Global_Consomme.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""), HSSFCell.CELL_TYPE_NUMERIC);

      if (str_RAD != null)
          theExport.xl_write(row, ++numCol, "" + str_RAD.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""), HSSFCell.CELL_TYPE_NUMERIC);

       numRow++;

      }


    
     

    //myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create();

    //myCnx.trace("5---------- xl_close_create","xl_close_create",""+theExport.filename);
        
    }  
  
  public  void excelExportTaches(String nomBase,Connexion myCnx, Statement st, Statement st2,int theYearRef, String Type){
      
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();

    String[] entete = {"Projet","Tache", "Acteurs", "Etat", "Debut", "Fin", "Prevu", "Consomme", "RAD", "RAF"};
    Excel theExport = new Excel("doc/tache/exportExcel",entete);


    theExport.xl_delete();
    //myCnx.trace("1---------- delete","location",""+location);

    // --------------------------------------------------------------------------------------------------//


    theExport.attach(theExport.Basedirectory+"/ExportTaches.xls"); // nom du fichier excel horodate
    //myCnx.trace("2---------- attach","location",""+location+"/ExportTaches_"+this.id+".xls");

    //theExport.xl_open_update();
    theExport.xl_open_create("excelTaches"); // feuille de calcul

    this.getAllFromBd(myCnx.nomBase,myCnx,st);
    this.getListeMembres(myCnx.nomBase,myCnx,st);
    if (Type.equals("Toutes"))
        this.getListeTachesByIdEtat(myCnx.nomBase,myCnx,st, -1);
    else
        this.getListeTachesOuvertes(myCnx.nomBase,myCnx,st, -1);
   

    int col = 0;
    theExport.sheet.setColumnWidth(col, 256*20); // A
    theExport.sheet.setColumnWidth(++col, 256*44); // B
    theExport.sheet.setColumnWidth(++col, 256*42); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*8); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*10); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*10); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*5); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*10); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*6); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*6); // fixer la largeur de la 1ere colonne

    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet.createFreezePane(2,1) ;

    
    int numRow = 0;
    Row row = theExport.sheet.createRow(numRow);
    theExport.xl_writeEntete(row,0,theExport.entete);
    numRow++;  
    



for (int i=0; i < this.ListeTaches.size(); i++) {
  Action theTache = (Action)this.ListeTaches.elementAt(i);
  float Global_Consomme = theTache.getChargesConsommees(myCnx.nomBase,myCnx,  st);
  //String str_Global_Consomme = (""+Global_Consomme).replace('.',',');
  String str_Global_Consomme = (""+Global_Consomme);

  float RAD = theTache.ChargePrevue - Global_Consomme;
  //String str_RAD = (""+RAD).replace('.',',');
  String str_RAD = (""+RAD);

  //String str_Consomme = (""+theTache.ChargePrevue).replace('.',',');
  String str_Consomme = (""+theTache.ChargePrevue);
  
     
      int numCol = 0;
      row = theExport.sheet.createRow(numRow);
      
      if (theTache.projet != null)
          theExport.xl_write(row, numCol, "" + theTache.projet.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTache.Code != null)
          theExport.xl_write(row, ++numCol, "" + theTache.Code.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTache.acteur != null)
          theExport.xl_write(row, ++numCol, "" + theTache.acteur.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTache.nomEtat != null)
          theExport.xl_write(row, ++numCol, "" + theTache.nomEtat.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTache.date_start != null)
          theExport.xl_write(row, ++numCol, "" + theTache.date_start.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTache.date_end != null)
          theExport.xl_write(row, ++numCol, "" + theTache.date_end.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (str_Consomme != null)
          theExport.xl_write(row, ++numCol, "" + str_Consomme.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""), HSSFCell.CELL_TYPE_NUMERIC);

      if (str_Global_Consomme != null)
          theExport.xl_write(row, ++numCol, "" + str_Global_Consomme.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""), HSSFCell.CELL_TYPE_NUMERIC);

      if (str_RAD != null)
          theExport.xl_write(row, ++numCol, "" + str_RAD.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""), HSSFCell.CELL_TYPE_NUMERIC);

       numRow++;

      }


    
     

    //myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create();

    //myCnx.trace("5---------- xl_close_create","xl_close_create",""+theExport.filename);
        
    }     
  
  public void getListeCharges(String nomBase,Connexion myCnx, Statement st,int anneeRef ){
    int idGenerique = Collaborateur.get_S_IdGenerique(myCnx.nomBase,myCnx,  st );

    for (int i=0; i < this.ListeMembres.size();i++)
    {
      Collaborateur theCollaborateur = (Collaborateur)this.ListeMembres.elementAt(i);
      //theCollaborateur.getListeProjets(nomBase, myCnx,  st, anneeRef);
      theCollaborateur.getTotalChargesProjets( nomBase, myCnx,  st, anneeRef, theCollaborateur.id, idGenerique);
    }

}

  public void addMOA2ListeMembres(String nomBase,Connexion myCnx, Statement st, String theLogin ){
    for (int i=0; i < this.ListeMembres.size(); i++)
    {
      Collaborateur theCollaborateur = (Collaborateur) this.ListeMembres.elementAt(i);
      if (theCollaborateur.Login.equals(theLogin)) return;
    }

    Collaborateur theCollaborateur = new Collaborateur(theLogin);
    theCollaborateur.getAllFromBd(nomBase, myCnx, st);
    this.ListeMembres.addElement(theCollaborateur);

  }

  
public int getListeActeurs(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs;

  if (this.type == "nom")
    req="exec EQUIPE_MembreByEquipe '"+this.nom+"'";
  else
    req="exec EQUIPE_MembreByIdEquipe '"+this.id+"'";

  // test s'il existe une structure du m�me nom
  String req2 = "SELECT * FROM";
    req2+=" (";
    req2+=" SELECT     Membre.idMembre, Membre.nomMembre, Membre.LoginMembre, Membre.prenomMembre, Membre.mail, Membre.photo, Service.nomService";
    req2+=" FROM         Equipe INNER JOIN";
    req2+="                       EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    req2+="                       Membre ON EquipeMembre.idMembre = Membre.idMembre  INNER JOIN";
    req2+="                        Service ON Membre.serviceMembre = Service.idService";
    req2+=" WHERE     (Equipe.nom = '"+this.nom+"')";
    req2+=" and     (EquipeMembre.type = 'collaborateur') ";

    req2+=" UNION";

    req2+=" SELECT     Membre.idMembre, Membre.nomMembre, Membre.LoginMembre, Membre.prenomMembre, Membre.mail, Membre.photo, Service.nomService";
    req2+=" FROM         St INNER JOIN";
    req2+="                       VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req2+="                       Membre ON VersionSt.respMoeVersionSt = Membre.idMembre  INNER JOIN";
    req2+="                        Service ON Membre.serviceMembre = Service.idService";    
    req2+=" WHERE     (St.nomSt = '"+this.nom+"')";

    req2+=" UNION";

    req2+=" SELECT     Membre.idMembre, Membre.nomMembre, Membre.LoginMembre, Membre.prenomMembre, Membre.mail, Membre.photo, Service.nomService";
    req2+=" FROM         St INNER JOIN";
    req2+="                       VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req2+="                       Membre ON VersionSt.respMoaVersionSt = Membre.idMembre  INNER JOIN";
    req2+="                        Service ON Membre.serviceMembre = Service.idService";       
    req2+=" WHERE     (St.nomSt = '"+this.nom+"')";
    req2+=" )";
    req2+=" as mytable order by nomMembre";
          
  rs = myCnx.ExecReq(st, nomBase, req2);
  int nb=0;
  try {
     while (rs.next()) {
       nb++;
     }
        } catch (SQLException s) {s.getMessage();}

    if (nb > 0) req = req2;

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  String nomMembre = "";
  String prenomMembre = "";
  String LoginMembre = "";
  String mail = "";
  int idMembre;
  try {
    while (rs.next()) {
      idMembre = rs.getInt("idMembre");
      nomMembre = rs.getString("nomMembre");
      LoginMembre = rs.getString("LoginMembre");
      prenomMembre = rs.getString("prenomMembre");
      mail = rs.getString("mail");
      Collaborateur theCollaborateur = new Collaborateur(idMembre);
      theCollaborateur.nom = nomMembre;
      theCollaborateur.Login = LoginMembre;
      theCollaborateur.prenom = prenomMembre;
      theCollaborateur.mail = mail;
      theCollaborateur.photo = rs.getString("photo");
      theCollaborateur.nomService = rs.getString("nomService");
      if ((theCollaborateur.photo == null) || (theCollaborateur.photo.equals("")))
        theCollaborateur.photo = "images/Direction.png";
      this.ListeMembres.addElement(theCollaborateur);
    }
  }
            catch (SQLException s) {s.getMessage();}

  return this.ListeMembres.size();

}

public int getListeMembres(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs;

  if (this.type == "nom")
    req="exec EQUIPE_MembreByEquipe '"+this.nom+"'";
  else
    req="exec EQUIPE_MembreByIdEquipe '"+this.id+"'";

  // test s'il existe une structure du m�me nom
  String req2 = "SELECT     Membre.idMembre, Membre.nomMembre, Membre.LoginMembre, Membre.prenomMembre, Membre.mail";
  req2+="    FROM         Service INNER JOIN";
  req2+="                   Membre ON Service.idService = Membre.serviceMembre";
  req2+=" WHERE     (Service.nomService = '"+this.nom+"')";

  rs = myCnx.ExecReq(st, nomBase, req2);
  int nb=0;
  try {
     while (rs.next()) {
       nb++;
     }
        } catch (SQLException s) {s.getMessage();}

    if (nb > 0) req = req2;

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  String nomMembre = "";
  String prenomMembre = "";
  String LoginMembre = "";
  String mail = "";
  int idMembre;
  try {
    while (rs.next()) {
      idMembre = rs.getInt("idMembre");
      nomMembre = rs.getString("nomMembre");
      LoginMembre = rs.getString("LoginMembre");
      prenomMembre = rs.getString("prenomMembre");
      mail = rs.getString("mail");
      Collaborateur theCollaborateur = new Collaborateur(idMembre);
      theCollaborateur.nom = nomMembre;
      theCollaborateur.Login = LoginMembre;
      theCollaborateur.prenom = prenomMembre;
      theCollaborateur.mail = mail;
      this.ListeMembres.addElement(theCollaborateur);
    }
  }
            catch (SQLException s) {s.getMessage();}

  return this.ListeMembres.size();

}

public int getListeMembres2(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs;

  if (this.type == "nom")
    req="exec EQUIPE_MembreByEquipe '"+this.nom+"'";
  else
    req="exec EQUIPE_MembreByIdEquipe '"+this.id+"'";

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  String nomMembre = "";
  String prenomMembre = "";
  String LoginMembre = "";
  String mail = "";
  int idMembre;
  try {
    while (rs.next()) {
      idMembre = rs.getInt("idMembre");
      nomMembre = rs.getString("nomMembre");
      LoginMembre = rs.getString("LoginMembre");
      prenomMembre = rs.getString("prenomMembre");
      mail = rs.getString("mail");
      Collaborateur theCollaborateur = new Collaborateur(idMembre);
      theCollaborateur.nom = nomMembre;
      theCollaborateur.Login = LoginMembre;
      theCollaborateur.prenom = prenomMembre;
      theCollaborateur.mail = mail;
      this.ListeMembres.addElement(theCollaborateur);
    }
  }
            catch (SQLException s) {s.getMessage();}

  return this.ListeMembres.size();

}
public void setId(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs = null;
  req = "SELECT     id, nom, [desc] FROM         Equipe WHERE  nom  = '"+this.old_nom+"'";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  try { rs.next();

    this.id = rs.getInt(1);

    } catch (SQLException s) {s.getMessage(); }
}

public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs = null;

  if (this.type.equals("id"))
    req = "SELECT     id, nom, [desc], isKpi FROM         Equipe WHERE  id = "+this.id;
  else if (this.type.equals("nom"))
    req = "SELECT     id, nom, [desc], isKpi FROM         Equipe WHERE  nom  = '"+this.nom+"'";

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  try { rs.next();

    this.id = rs.getInt(1);
    this.nom = rs.getString(2).replaceAll("\r","").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");;
    this.description= rs.getString(3);
    this.isKpi = rs.getInt("isKpi");

    } catch (SQLException s) {s.getMessage(); }


}



  public Equipe(int id, String nom,String description ) {
    this.id = id;
    this.nom = nom;
    this.description = description;
    this.type = "id";
  }
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    for (int i=0; i < this.ListeMembres.size();i++)
    {
      Collaborateur theCollaborateur = (Collaborateur)this.ListeMembres.elementAt(i);
      theCollaborateur.dump();
    }
    System.out.println("==================================================");
  }
  public static void main(String[] args) {
    Connexion myCnx = null;
    Statement st, st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    st2 = myCnx.Connect();
    Equipe theEquipe = new Equipe(372);
    theEquipe.excelExportTaches(myCnx.nomBase,myCnx,  st, st2, 4, "RAF");
    //theEquipe.getAllFromBd(myCnx.nomBase,myCnx,  st);
    //theEquipe.getListeMembres(myCnx.nomBase,myCnx,  st);
    //theEquipe.getListeTachesDistinctes(myCnx.nomBase,myCnx,  st, st2);
    //theEquipe.getListeTachesTraiteByMonth(myCnx.nomBase,myCnx,  st, 2013,11);


    //theEquipe.dump();
    
    //theEquipe.bd_Enreg(myCnx.nomBase,myCnx,  st ,"xxxx");


    myCnx.Unconnect(st);
  }
  public static void main2(String[] args) {
    Connexion myCnx = null;
    Statement st, st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    Equipe theEquipe = new Equipe("MOA_SIR-DED-Externe");
    int nbMembre = theEquipe.getListeMembres(myCnx.nomBase,myCnx,  st );
    //theEquipe.dump();
    theEquipe.getListeCharges(myCnx.nomBase,myCnx,  st , 2010);

    for (int i=0; i < theEquipe.ListeMembres.size(); i++) {
      Collaborateur theCollaborateur = (Collaborateur) theEquipe.ListeMembres.elementAt(i);
      for (int semaine=1; semaine <= 52; semaine++)
      {
        String theClasse = "TresPetitenteteBleu";
        String theValue = theCollaborateur.getChargeByWeek(semaine, 2010);
        if (semaine == 1)
          myCnx.trace("01234----------", "theCollaborateur",
                   theCollaborateur.nom + "::semaine=" + semaine + "charge=" +
                   theValue);
      }
    }

    myCnx.Unconnect(st);
  }
}

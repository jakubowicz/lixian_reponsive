package Organisation; 

import Composant.Excel;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import accesbase.Connexion;
import java.util.Vector;
import java.util.Date;
import General.Utils;
import Projet.Roadmap;
import Organisation.Collaborateur;
import Projet.Action;
import ST.ST;
import Composant.choiceList;
import Composant.item;
import Projet.Charge;
import accesbase.transaction;
import Test.CategorieTest;
import Test.Test;
import java.util.StringTokenizer;

import PO.Chiffrage;
import Projet.PhaseProjet;
import Projet.RoadmapProfil;
        

import org.apache.poi.xssf.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.util.Calendar;
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
public class Service {
  public int id;
  public int idServicePere;
  public String nom="";
  public String nomServiceImputations="";
  public String old_nom="";
  public String alias="";
  public String description="";
  public String mdp="";
  public int direction;
  public int old_direction;
  public String nomDirection="";
  public String type="";
  public int isMoe = 0;
  public int isMoa = 0;
  public int isGouvernance = 0;
  public int isExploitation = 0;
  public int Si;

  public String TypeSi="";
  public int nbStRouge;
  public int nbAppliRouge;
  public int nbStBleu;
  public int nbStViolet;

  public int sendRetard=0;
  public String nomServicePO="";

  private String req="";
  public String chaine_a_ecrire="";

   public Vector ListeProjets = new Vector(10);
   public Vector ListeSt = new Vector(10);
   public Vector ListeStBleu = new Vector(10);
   public Vector ListeStViolet = new Vector(10);
   public Vector ListeStRouge = new Vector(10);
   public static Vector ListeServices = new Vector(10);
   public static Vector ListeRoadmapProfil = new Vector(10);

   public Vector ListeActions = new Vector(10);

   public String TableListeProjet="";
  public Vector ListeCollaborateurs = new Vector(10);

  public int maxCollaborateurs=0;

  public float totalChargePrevue=0;
  public float totalChargeConsommee=0;
  public float TotalchargeReestimee=0;

  public static final  float nbJoursInternes = 200;
  public static final  float nbJoursExternes = 210;

  public float nb_internes=0;
  public float nb_externes=0;

  public float nb_joursMoyens=0;
  public float nb_joursTotal=0;
  
  public int nbActions=0;

  public String CentreCout="";


  public Service(int id) {
    this.id = id;
  }

  public Service(String nom) {
    this.nom = nom;
  }

  public Service(int id, String nom, int direction) {
    this.id = id;
    this.nom = nom;
    this.direction = direction;
  }

  public  void getListeSt(String nomBase,Connexion myCnx, Statement st){

    if (this.isMoe != 1)
    {
      req = "SELECT DISTINCT ListeST.idVersionSt, ListeST.nomSt";
      req += " FROM         ListeST INNER JOIN";
      req +=
          "                   Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";
      req += " WHERE     (ListeST.serviceMoaVersionSt = " + this.id + ") AND (ListeST.isMeeting <> 1) AND (ListeST.isRecurrent = 0) AND (Roadmap.dateT0 > CONVERT(DATETIME, ";
      req += "                   '2000-01-01 00:00:00', 102)) AND (Roadmap.dateEB > CONVERT(DATETIME, '2000-01-01 00:00:00', 102)) AND ";
      req += "                   (Roadmap.dateMep > CONVERT(DATETIME, '2000-01-01 00:00:00', 102)) AND (Roadmap.dateTest > CONVERT(DATETIME, '2000-01-01 00:00:00', 102))";
      req += " ORDER BY ListeST.nomSt";
    }
    else
    {
      req = "SELECT DISTINCT ListeST.idVersionSt, ListeST.nomSt";
      req += " FROM         ListeST INNER JOIN";
      req +=
          "                   Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";
      req += " WHERE     (ListeST.serviceMoeVersionSt = " + this.id + ") AND (ListeST.isMeeting <> 1) AND (ListeST.isRecurrent = 0) AND (Roadmap.dateT0 > CONVERT(DATETIME, ";
      req += "                   '2000-01-01 00:00:00', 102)) AND (Roadmap.dateEB > CONVERT(DATETIME, '2000-01-01 00:00:00', 102)) AND ";
      req += "                   (Roadmap.dateMep > CONVERT(DATETIME, '2000-01-01 00:00:00', 102)) AND (Roadmap.dateTest > CONVERT(DATETIME, '2000-01-01 00:00:00', 102))";
      req += " ORDER BY ListeST.nomSt";
    }

    this.ListeSt.removeAllElements();
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    try {
       while (rs.next()) {
         int idVersionSt = rs.getInt("idVersionSt");
         String nomSt = rs.getString("nomSt");
         if (nomSt != null)
         {
           ST theST = new ST(nomSt);
           theST.idVersionSt = idVersionSt;
           this.ListeSt.addElement(theST);
           this.ListeStRouge.addElement(theST);
         }

       }
      } catch (SQLException s) {s.getMessage();}
  }

  public static void getListeServices(String nomBase,Connexion myCnx, Statement st){

    String req="EXEC DIRECTION_SelectService MOA";
    ListeServices.removeAllElements();

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Service theService = null;
    try {
       while (rs.next()) {
         int idDirection = rs.getInt(1);
         int idService = rs.getInt(2);
         String nomDirection = rs.getString(3);
         String nomService = rs.getString(4);

         theService = new Service(idService);
         theService.direction = idDirection;
         theService.nom = nomService;
         theService.nomDirection = nomDirection;
         //theST.dump();
         ListeServices.addElement(theService);
       }
        } catch (SQLException s) {s.getMessage();}

  }
  
 

  Vector getListeCollaborateur(String theListe, String nomBase,Connexion myCnx, Statement st){
    Vector myListeCollaborateur = new Vector(10);

    if (theListe.length() ==0) return myListeCollaborateur;

    theListe =theListe + ";";

    int Index=theListe.indexOf(';');

    while (Index != -1)
    {
      String Login = theListe.substring (0,Index);
      //alert ("nomOM="+nomOM);
      theListe = theListe.substring(Index+1);
      Index = theListe.indexOf(';');

      Collaborateur theCollaborateur = new Collaborateur(Login);
      theCollaborateur.getAllFromBd( nomBase, myCnx,  st );
      if (theCollaborateur.id != 0)
      {
        this.ListeCollaborateurs.addElement(theCollaborateur);
        //theCollaborateur.dump();
         }

      myListeCollaborateur.addElement(theCollaborateur);

   }

    return myListeCollaborateur;
  }

  public static void getListeRoadmapProfil(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    String nom="";
    String dateEB="";
    String datePROD="";
    String dateMES="";
    int idVersionSt = -1;
    String version= "";

    String req="SELECT      id, nom, phase, delais, charge";
    req+=" FROM         RoadmapProfil";
    req+=" ORDER BY id";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
    while(rs.next())
    {
       RoadmapProfil theRoadmapProfil = new RoadmapProfil();
       theRoadmapProfil.id = rs.getInt("id");
       theRoadmapProfil.nom = rs.getString("nom");
       theRoadmapProfil.phase = rs.getString("phase");
       theRoadmapProfil.delais = rs.getInt("delais");
       theRoadmapProfil.charge = rs.getInt("charge");
       
       ListeRoadmapProfil.addElement(theRoadmapProfil);

     }

    }
     catch (SQLException s) { }

   
  }
  
  public void getListeCollaborateursRetardAction(String nomBase,Connexion myCnx, Statement st,  Statement st2){
    ResultSet rs;
    String nom="";
    String dateEB="";
    String datePROD="";
    String dateMES="";
    int idVersionSt = -1;
    String version= "";

    req="SELECT DISTINCT actionSuivi.acteur";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                   St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                   actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap INNER JOIN";
    req+="                   TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id INNER JOIN";
    req+="                   Service ON VersionSt.serviceMoaVersionSt = Service.idService";
    req+=" WHERE     (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)) AND ";
    req+="                   (Service.nomService = '"+this.nom+"')";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
    while(rs.next())
    {
       String ListeActeur = rs.getString("acteur");
       this.ListeCollaborateurs = getListeCollaborateur(ListeActeur,  nomBase, myCnx,  st2);

     }

    }
     catch (SQLException s) { }

   this.maxCollaborateurs = this.ListeCollaborateurs.size() + 10;
  }
  public void getListeCollaborateurs(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    String nom="";
    String dateEB="";
    String datePROD="";
    String dateMES="";
    int idVersionSt = -1;
    String version= "";

    req="SELECT     Membre.idMembre, Membre.nomMembre, Membre.prenomMembre, Membre.fonctionMembre, Membre.serviceMembre, Membre.mail, ";
    req+="                  Membre.LoginMembre, Membre.isAdmin, Membre.isProjet, Membre.AO, Membre.Prix, Membre.dateEntree, Membre.dateSortie, Membre.intitulePoste, ";
    req+="                   Membre.niveau, Membre.Mission, Membre.societeMembre, Membre.Telephone_M, Membre.Telephone_B, Membre.Fax";
    req+=" FROM         Service INNER JOIN";
    req+="                   Membre ON Service.idService = Membre.serviceMembre";
    req+=" WHERE     (Service.idService = "+this.id+")";
    req+=" ORDER BY Membre.nomMembre";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
    while(rs.next())
    {
       int idMembre = rs.getInt("idMembre");
       Collaborateur theCollaborateur = new Collaborateur(idMembre );
       theCollaborateur.nom=rs.getString("nomMembre");
       theCollaborateur.prenom=rs.getString("prenomMembre");
       this.ListeCollaborateurs.addElement(theCollaborateur);
     }

    }
     catch (SQLException s) { }

   this.maxCollaborateurs = this.ListeCollaborateurs.size() + 10;
  }

  public  void getListeActions(String nomBase,Connexion myCnx, Statement st){

    String req="";
    if (this.isMoe != 1)
    {
           req="SELECT     Roadmap.version, actionSuivi.nom, actionSuivi.acteur, actionSuivi.dateAction, Membre.idMembre, Membre.nomMembre,";
           req+="    Membre.prenomMembre";
           req+="  FROM         actionSuivi INNER JOIN ";
           req+="                        TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id INNER JOIN";
           req+="                        Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap INNER JOIN";
           req+="                        VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
           req+="                        Service ON VersionSt.serviceMoaVersionSt = Service.idService INNER JOIN";
           req+="                        Membre ON VersionSt.respMoaVersionSt = Membre.idMembre";
           req+="  WHERE     (TypeEtatAction.nom <> 'Clos') AND (Service.nomService = '"+this.nom+"') AND (actionSuivi.type = 'MOA') ";
    }
    else
    {
          req="SELECT     Roadmap.version, actionSuivi.nom, actionSuivi.acteur, actionSuivi.dateAction, Membre.idMembre, Membre.nomMembre,";
          req+="    Membre.prenomMembre";
          req+="  FROM         actionSuivi INNER JOIN ";
          req+="                        TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id INNER JOIN";
          req+="                        Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap INNER JOIN";
          req+="                        VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
          req+="                        Service ON VersionSt.serviceMoeVersionSt = Service.idService INNER JOIN";
          req+="                        Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
          req+="  WHERE     (TypeEtatAction.nom <> 'Clos') AND (Service.nomService = '"+this.nom+"') AND (actionSuivi.type = 'MOE')";
   }

  this.ListeActions.removeAllElements();

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  Service theService = null;
  try {
     while (rs.next()) {
       String projet = rs.getString("version");
       String nomAction = rs.getString("nom");
       String acteurAction = rs.getString("acteur");
       String dateAction = rs.getString("dateAction");
       int idMembre = rs.getInt("idMembre");
       String nomMembre = rs.getString("nomMembre");
       String prenomMembre = rs.getString("prenomMembre");

       Collaborateur theCollaborateur = new Collaborateur(idMembre,prenomMembre,nomMembre);
       Action theAction = new Action (-1, nomAction, acteurAction,-1,-1,"","", "");
       theAction.respMOA = theCollaborateur;
       theAction.projet = projet;

       //theST.dump();
       this.ListeActions.addElement(theAction);
     }
      } catch (SQLException s) {s.getMessage();}

}

public  String setListeActionsRetard(String nomBase,Connexion myCnx, Statement st, String transaction,String myLogin){
  req = "delete  FROM    tempListeMembres WHERE (Login = '"+myLogin+"')";
  if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

  for (int i=0; i < this.ListeActions.size();i++)
  {
    Action theAction = (Action) this.ListeActions.elementAt(i);
    Collaborateur theCollaborateur = new Collaborateur(theAction.acteur);
    theCollaborateur.getAllFromBd(myCnx.nomBase, myCnx, st);

    String req = "INSERT tempListeMembres (Login, idMembre, nomMembre, prenomMembre, LoginMembre, noteCompetence)";
    req+=" VALUES ('"+myLogin+"','"+theCollaborateur.id+"','"+theCollaborateur.nom+"','"+theCollaborateur.prenom+"','"+theCollaborateur.Login+"',"+theAction.id+")";
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

  }
   return "OK";
 }

  public  void excelExportLf(String nomBase,Connexion myCnx, Statement st, Statement st2,int theYearRef){
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();

    String[] entete = {"Code PO du Sujet","Statut de la Fiche d'impact", "ID MOE", "FicheImpact", "Annee", "Structure", "Part", "Type Charge"};
    Excel theExport = new Excel("doc/po/exportExcel",entete);

    theExport.xl_delete();
    //myCnx.trace("1---------- delete","location",""+location);

    // --------------------------------------------------------------------------------------------------//


    theExport.attach(theExport.Basedirectory+"/ExportLF_"+this.id+".xls"); // nom du fichier excel horodate
    //myCnx.trace("2---------- attach","location",""+location+"/ExportLF_"+this.id+".xls");

    //theExport.xl_open_update();
    theExport.xl_open_create("excelChiffrageLF"); // feuille de calcul

    
    /// --------------------------- Récupération de la liste des catégories de test----------------------//
    this.getListeProjetsClients(myCnx.nomBase,myCnx,  st,""+theYearRef,  1);
    // -------------------------------------------------------------------------------------------------//    

    int col = 0;
    theExport.sheet.setColumnWidth(col, 256*11); // A
    theExport.sheet.setColumnWidth(++col, 256*14); // B
    theExport.sheet.setColumnWidth(++col, 256*7); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*70); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*6); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*19); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*6); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(++col, 256*20); // fixer la largeur de la 1ere colonne

    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet.createFreezePane(2,1) ;

    
    int numRow = 0;
    Row row = theExport.sheet.createRow(numRow);
    theExport.xl_writeEntete(row,0,theExport.entete);
    numRow++;  
    



for (int i=0; i < this.ListeProjets.size(); i++)
{
  Roadmap theRoadmap = (Roadmap)this.ListeProjets.elementAt(i);
  theRoadmap.getChiffrageLF(myCnx.nomBase,myCnx,st, this.nomServiceImputations,theYearRef,1);

  
     
      int numCol = 0;
      row = theExport.sheet.createRow(numRow);
      
      if (theRoadmap.codeSujet != null)
          theExport.xl_write(row, numCol, "" + theRoadmap.codeSujet.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
      if (theRoadmap.EtatRoadmap != null)
          theExport.xl_write(row, ++numCol, "" + theRoadmap.EtatRoadmap.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
      if (theRoadmap.idPO != null)
          theExport.xl_write(row, ++numCol, "" + theRoadmap.idPO.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""), HSSFCell.CELL_TYPE_NUMERIC);
      if (theRoadmap.nomProjet != null)
          theExport.xl_write(row, ++numCol, "" + theRoadmap.nomProjet.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      theExport.xl_write(row, ++numCol, "" + theYearRef, HSSFCell.CELL_TYPE_NUMERIC);
      
      if (this.nom != null)
          theExport.xl_write(row, ++numCol, "" + this.nom.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
      if (theRoadmap.part != null)
          theExport.xl_write(row, ++numCol, "" + theRoadmap.part.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
      
      theExport.xl_write(row, ++numCol, "" + (int)theRoadmap.ChargePrevue, HSSFCell.CELL_TYPE_NUMERIC);

      numRow++;

      }


    
     

   // myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create();

    //myCnx.trace("5---------- xl_close_create","xl_close_create",""+theExport.filename);
        
    }     

  public  void excelExportProjetTechnique(String nomBase,Connexion myCnx, Statement st, Statement st2,int theYearRef){
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    st2 = myCnx.Connect();


    String[] entete = {"Code PO du Sujet","Statut de la Fiche d'impact", "ID MOE", "FicheImpact", "Annee", "Structure", "Part", "Type Charge",  "JH", "Forfait (kEuros)"};
    Excel theExport = new Excel("doc/po/exportExcel",entete);


    theExport.xl_delete();
    //myCnx.trace("1---------- delete","location",""+location);

    // --------------------------------------------------------------------------------------------------//


    theExport.attach(theExport.Basedirectory+"/ExportChiffrageProjetTechnique_"+this.id+".xls"); // nom du fichier excel horodate
    //myCnx.trace("2---------- attach","location",""+location+"/ExportChiffrageProjetTechnique_"+this.id+".xls");

    //theExport.xl_open_update();
    theExport.xl_open_create("excelChiffrageLF"); // feuille de calcul

    
    /// --------------------------- Récupération de la liste des catégories de test----------------------//
    this.getListeProjetsClients(myCnx.nomBase,myCnx,  st,""+theYearRef,  1);
    // -------------------------------------------------------------------------------------------------//    

    int col = 0;
    theExport.sheet.setColumnWidth(col, 256*11); // A
    theExport.sheet.setColumnWidth(++col, 256*14); // B
    theExport.sheet.setColumnWidth(++col, 256*7); // C
    theExport.sheet.setColumnWidth(++col, 256*70); // D
    theExport.sheet.setColumnWidth(++col, 256*6); // E
    theExport.sheet.setColumnWidth(++col, 256*11); // F
    theExport.sheet.setColumnWidth(++col, 256*6); // G
    theExport.sheet.setColumnWidth(++col, 256*14); // H
    theExport.sheet.setColumnWidth(++col, 256*8); // I
    theExport.sheet.setColumnWidth(++col, 256*15); // J

    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet.createFreezePane(2,1) ;

    
    int numRow = 0;
    Row row = theExport.sheet.createRow(numRow);
    theExport.xl_writeEntete(row,0,theExport.entete);
    numRow++;  
    



for (int i=0; i < this.ListeProjets.size(); i++)
{
  Roadmap theRoadmap = (Roadmap)this.ListeProjets.elementAt(i);
  //theRoadmap.getChiffrageLF(myCnx.nomBase,myCnx,st, this.nomServiceImputations,theYearRef,1);
  int totalCharge=theRoadmap.getChiffrage(myCnx.nomBase,myCnx,st, this.nomServiceImputations,theYearRef,1);

  
     
      int numCol = 0;
      row = theExport.sheet.createRow(numRow);
      
      if (theRoadmap.codeSujet != null)
          theExport.xl_write(row, numCol, "" + theRoadmap.codeSujet.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
      if (theRoadmap.EtatRoadmap != null)
          theExport.xl_write(row, ++numCol, "" + theRoadmap.EtatRoadmap.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
      if (theRoadmap.idPO != null)
          theExport.xl_write(row, ++numCol, "" + theRoadmap.idPO.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""), HSSFCell.CELL_TYPE_NUMERIC);
      if (theRoadmap.nomProjet != null)
          theExport.xl_write(row, ++numCol, "" + theRoadmap.nomProjet.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

        theExport.xl_write(row, ++numCol, "" + theYearRef, HSSFCell.CELL_TYPE_NUMERIC);

      if (this.nom != null)
          theExport.xl_write(row, ++numCol, "" + this.nom.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
      if (theRoadmap.part != null)
          theExport.xl_write(row, ++numCol, "" + theRoadmap.part.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theRoadmap.TypeCharge != null)
          theExport.xl_write(row, ++numCol, "" + theRoadmap.TypeCharge.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
     
      if (theRoadmap.theChiffrage != null)
          theExport.xl_write(row, ++numCol, "" + totalCharge, HSSFCell.CELL_TYPE_NUMERIC);
      
      theExport.xl_write(row, ++numCol, "" + (int)theRoadmap.theChiffrage.Forfait, HSSFCell.CELL_TYPE_NUMERIC);

      numRow++;

      }


    
     

    //myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create();

    //myCnx.trace("5---------- xl_close_create","xl_close_create",""+theExport.filename);
        
    }     
      
    public  void getListeActionsRetard(String nomBase,Connexion myCnx, Statement st, Statement st2){

      String req="";

        req="SELECT     Roadmap.version, actionSuivi.nom, actionSuivi.acteur, actionSuivi.dateAction, actionSuivi.dateFin, actionSuivi.id";
        req+="    FROM         actionSuivi INNER JOIN";
        req+="               TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id INNER JOIN";
        req+="               Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap INNER JOIN";
        req+="               VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
        req+="               Service ON VersionSt.serviceMoaVersionSt = Service.idService";
        req+="  WHERE     (TypeEtatAction.nom <> 'Clos') AND (Service.nomService = '"+this.nom+"') AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)) AND (actionSuivi.dateFin IS NOT NULL) AND (actionSuivi.acteur <> '') AND (Roadmap.standby <> 1)";

        req+=" UNION";

        req+=" SELECT     Roadmap.version, actionSuivi.nom, actionSuivi.acteur, actionSuivi.dateAction, actionSuivi.dateFin, actionSuivi.id";
        req+="    FROM         actionSuivi INNER JOIN";
        req+="               TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id INNER JOIN";
        req+="               Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap INNER JOIN";
        req+="               VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
        req+="               Service ON VersionSt.serviceMoeVersionSt = Service.idService";
        req+="  WHERE     (TypeEtatAction.nom <> 'Clos') AND (Service.nomService = '"+this.nom+"')  AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)) AND (actionSuivi.dateFin IS NOT NULL) AND (actionSuivi.acteur <> '') AND (Roadmap.standby <> 1)";
     

    this.ListeActions.removeAllElements();

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Service theService = null;
    try {
       while (rs.next()) {
         String projet = rs.getString("version");
         String nomAction = rs.getString("nom");
         String acteurAction = rs.getString("acteur");
         Date dateAction = rs.getDate("dateAction");
         Date date_end = rs.getDate("dateFin");
         int idAction = rs.getInt("id");
         Collaborateur.setListeCollaborateur(acteurAction, nomBase, myCnx,  st2);
         //int idMembre = rs.getInt("idMembre");
         //String nomMembre = rs.getString("nomMembre");
         //String prenomMembre = rs.getString("prenomMembre");

         //Collaborateur theCollaborateur = new Collaborateur(idMembre,prenomMembre,nomMembre);
         for (int i=0; i < Collaborateur.ListeCollaborateur.size();i++)
         {
           Collaborateur myCollaborateur = (Collaborateur)Collaborateur.ListeCollaborateur.elementAt(i);
           Action theAction = new Action( idAction, nomAction, myCollaborateur.Login, -1, -1,"", "", "");
           //theAction.respMOA = theCollaborateur;
           theAction.projet = projet;
           if (dateAction != null)   theAction.date_start = dateAction.toString();
           if (date_end != null)   theAction.date_end = date_end.toString();
           //theAction.dump();
           //theST.dump();
           this.ListeActions.addElement(theAction);
         }
       }
        } catch (SQLException s) {
          s.getMessage();
        }

  }
    
    public  int getnbActionsRetard(String nomBase,Connexion myCnx, Statement st){
      int nb=0;
      String req="";
      req +="        SELECT     COUNT(*) as nb";
      req +="             FROM         actionSuivi INNER JOIN";
      req +="                        TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id INNER JOIN";
      req +="                        Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap INNER JOIN";
      req +="                        VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
      req +="                        Service ON VersionSt.serviceMoeVersionSt = Service.idService";
      req +="           WHERE     (TypeEtatAction.nom <> 'Clos') AND (Service.nomService = '"+this.nom+"')  AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)) AND (actionSuivi.dateFin IS NOT NULL) AND (actionSuivi.acteur <> '') AND (Roadmap.standby <> 1)";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    try {
       while (rs.next()) {
         nb = rs.getInt("nb");
       }
        } catch (SQLException s) {
          s.getMessage();
        }
    return nb;
  }    

  public static int getnbServiceMoe(String nomBase,Connexion myCnx, Statement st){

  String req="SELECT count(*) FROM Service WHERE isMoe=1";
  int nbServiceMoe = 0;

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  Service theService = null;
  try {
     while (rs.next()) {
       nbServiceMoe = rs.getInt(1);
     }
      } catch (SQLException s) {s.getMessage();}

      return nbServiceMoe;
}

    public void getListeProjetsByMonthByJalon(String nomBase,Connexion myCnx, Statement st, String anneeRef , int num_mois, String Type, String tendance, String search){
      ResultSet rs;
if (this.isMoe != 1)
 {
      if (tendance.equals("toutes"))
      {
        if (search.equals("toutes"))
        {
          if (Type.equals("Mep"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap FROM   "+this.TableListeProjet+" WHERE     (serviceMoaVersionSt = " +
                this.id + ")  AND MONTH(dateMep) = " + (num_mois + 1) +
                " AND YEAR(dateMep) = " + anneeRef + "AND (Panier = 'checked') AND isMeeting <> 1  AND     (standby <> 1) order by dateMep asc";
          else if (Type.equals("EB"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap  FROM   "+this.TableListeProjet+" WHERE     (serviceMoaVersionSt = " +
                this.id + ")  AND MONTH(dateEB) = " + (num_mois + 1) +
                " AND YEAR(dateEB) = " + anneeRef + "AND (Panier = 'checked')  AND isMeeting <> 1  AND     (standby <> 1) order by dateEB asc";
          else if (Type.equals("Test"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap  FROM   "+this.TableListeProjet+" WHERE     (serviceMoaVersionSt = " +
                this.id + ")  AND MONTH(dateTest) = " + (num_mois + 1) +
                " AND YEAR(dateTest) = " + anneeRef + "AND (Panier = 'checked')  AND isMeeting <> 1  AND     (standby <> 1) order by dateTest asc";
          else if (Type.equals("T0"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap  FROM   "+this.TableListeProjet+" WHERE     (serviceMoaVersionSt = " +
                this.id + ")  AND MONTH(dateT0) = " + (num_mois + 1) +
                " AND YEAR(dateT0) = " + anneeRef + "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateT0 asc";
        }
        else
        {
          if (Type.equals("Mep"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap  FROM   "+this.TableListeProjet+" WHERE     (serviceMoaVersionSt = " +
                this.id + ")  AND MONTH(dateMep) = " + (num_mois + 1) +
                " AND YEAR(dateMep) = " + anneeRef +  " AND (nomProjet LIKE '%"+search+"%') " +"AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateMep asc";
          else if (Type.equals("EB"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap  FROM   "+this.TableListeProjet+" WHERE     (serviceMoaVersionSt = " +
                this.id + ")  AND MONTH(dateEB) = " + (num_mois + 1) +
                " AND YEAR(dateEB) = " + anneeRef +  " AND (nomProjet LIKE '%"+search+"%') "+ "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateEB asc";
          else if (Type.equals("Test"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap  FROM   "+this.TableListeProjet+" WHERE     (serviceMoaVersionSt = " +
                this.id + ")  AND MONTH(dateTest) = " + (num_mois + 1) +
                " AND YEAR(dateTest) = " + anneeRef +  " AND (nomProjet LIKE '%"+search+"%') "+ "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateTest asc";
          else if (Type.equals("T0"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap  FROM   "+this.TableListeProjet+" WHERE     (serviceMoaVersionSt = " +
                this.id + ")  AND MONTH(dateT0) = " + (num_mois + 1) +
                " AND YEAR(dateT0) = " + anneeRef +  " AND (nomProjet LIKE '%"+search+"%') "+ "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateT0 asc";
        }
      }
      else
      {
        if (Type.equals("Mep"))
          req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap  FROM   "+this.TableListeProjet+" WHERE     (serviceMoaVersionSt = " +
              this.id + ")  AND MONTH(dateMep) = " + (num_mois + 1) +
              " AND YEAR(dateMep) = " + anneeRef + " AND (Tendance = '"+tendance+"')" +"AND (Panier = 'checked')   AND isMeeting <> 1   AND     (standby <> 1) order by dateMep asc";
        else if (Type.equals("EB"))
          req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap  FROM   "+this.TableListeProjet+" WHERE     (serviceMoaVersionSt = " +
              this.id + ")  AND MONTH(dateEB) = " + (num_mois + 1) +
              " AND YEAR(dateEB) = " + anneeRef + " AND (Tendance = '"+tendance+"')" + "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateEB asc";
        else if (Type.equals("Test"))
          req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap  FROM   "+this.TableListeProjet+" WHERE     (serviceMoaVersionSt = " +
              this.id + ")  AND MONTH(dateTest) = " + (num_mois + 1) +
              " AND YEAR(dateTest) = " + anneeRef + " AND (Tendance = '"+tendance+"')" + "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateTest asc";
        else if (Type.equals("T0"))
          req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap  FROM   "+this.TableListeProjet+" WHERE     (serviceMoaVersionSt = " +
              this.id + ")  AND MONTH(dateT0) = " + (num_mois + 1) +
              " AND YEAR(dateT0) = " + anneeRef + " AND (Tendance = '"+tendance+"')" + "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateT0 asc";
      }
 }
 else
 {
      if (tendance.equals("toutes"))
      {
        if (search.equals("toutes"))
        {
          if (Type.equals("Mep"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap FROM   "+this.TableListeProjet+" WHERE     (serviceMoeVersionSt = " +
                this.id + ")  AND MONTH(dateMep) = " + (num_mois + 1) +
                " AND YEAR(dateMep) = " + anneeRef + "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateMep asc";
          else if (Type.equals("EB"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap FROM   "+this.TableListeProjet+" WHERE     (serviceMoeVersionSt = " +
                this.id + ")  AND MONTH(dateEB) = " + (num_mois + 1) +
                " AND YEAR(dateEB) = " + anneeRef + "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateEB asc";
          else if (Type.equals("Test"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap FROM   "+this.TableListeProjet+" WHERE     (serviceMoeVersionSt = " +
                this.id + ")  AND MONTH(dateTest) = " + (num_mois + 1) +
                " AND YEAR(dateTest) = " + anneeRef + "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateTest asc";
          else if (Type.equals("T0"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap FROM   "+this.TableListeProjet+" WHERE     (serviceMoeVersionSt = " +
                this.id + ")  AND MONTH(dateT0) = " + (num_mois + 1) +
                " AND YEAR(dateT0) = " + anneeRef + "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateT0 asc";
        }
        else
        {
          if (Type.equals("Mep"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap FROM   "+this.TableListeProjet+" WHERE     (serviceMoeVersionSt = " +
                this.id + ")  AND MONTH(dateMep) = " + (num_mois + 1) +
                " AND YEAR(dateMep) = " + anneeRef +  " AND (nomProjet LIKE '%"+search+"%') " +"AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateMep asc";
          else if (Type.equals("EB"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap FROM   "+this.TableListeProjet+" WHERE     (serviceMoeVersionSt = " +
                this.id + ")  AND MONTH(dateEB) = " + (num_mois + 1) +
                " AND YEAR(dateEB) = " + anneeRef +  " AND (nomProjet LIKE '%"+search+"%') "+ "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateEB asc";
          else if (Type.equals("Test"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap FROM   "+this.TableListeProjet+" WHERE     (serviceMoeVersionSt = " +
                this.id + ")  AND MONTH(dateTest) = " + (num_mois + 1) +
                " AND YEAR(dateTest) = " + anneeRef +  " AND (nomProjet LIKE '%"+search+"%') "+ "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateTest asc";
          else if (Type.equals("T0"))
            req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap FROM   "+this.TableListeProjet+" WHERE     (serviceMoeVersionSt = " +
                this.id + ")  AND MONTH(dateT0) = " + (num_mois + 1) +
                " AND YEAR(dateT0) = " + anneeRef +  " AND (nomProjet LIKE '%"+search+"%') "+ "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateT0 asc";
        }
      }
      else
      {
        if (Type.equals("Mep"))
          req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap FROM   "+this.TableListeProjet+" WHERE     (serviceMoeVersionSt = " +
              this.id + ")  AND MONTH(dateMep) = " + (num_mois + 1) +
              " AND YEAR(dateMep) = " + anneeRef + " AND (Tendance = '"+tendance+"')" +"AND (Panier = 'checked')   AND isMeeting <> 1   AND     (standby <> 1) order by dateMep asc";
        else if (Type.equals("EB"))
          req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap FROM   "+this.TableListeProjet+" WHERE     (serviceMoeVersionSt = " +
              this.id + ")  AND MONTH(dateEB) = " + (num_mois + 1) +
              " AND YEAR(dateEB) = " + anneeRef + " AND (Tendance = '"+tendance+"')" + "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateEB asc";
        else if (Type.equals("Test"))
          req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap FROM   "+this.TableListeProjet+" WHERE     (serviceMoeVersionSt = " +
              this.id + ")  AND MONTH(dateTest) = " + (num_mois + 1) +
              " AND YEAR(dateTest) = " + anneeRef + " AND (Tendance = '"+tendance+"')" + "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateTest asc";
        else if (Type.equals("T0"))
          req = "SELECT     nomProjet, nomSt, description, dateEB, dateMep, Suivi, Tendance, dateMes, dateT0, dateTest, docEB, docDevis, idPO, idRoadmap FROM   "+this.TableListeProjet+" WHERE     (serviceMoeVersionSt = " +
              this.id + ")  AND MONTH(dateT0) = " + (num_mois + 1) +
              " AND YEAR(dateT0) = " + anneeRef + " AND (Tendance = '"+tendance+"')" + "AND (Panier = 'checked')  AND isMeeting <> 1   AND     (standby <> 1) order by dateT0 asc";
      }
 }



      rs = myCnx.ExecReq(st, nomBase, req);
     String nomSt="";
     String nomVersion="";
     String descRoadmap = "";
     String dateT0="";
     String dateEB="";
     String dateTest="";
     String T0="";
     String dateProd = "";
     String dateMes= "";
     String Suivi = "";
     String Tendance = "";
     String docEB = "";
     String docDevis = "";
     String MotsClef = "";
     int idPo = 0;
     int i=1;

     this.ListeProjets.removeAllElements();
     try {
     while(rs.next())
     {
         nomVersion = rs.getString(1);
         if (nomVersion != null) {
           nomVersion = nomVersion.replaceAll("\n", "<br>");
         }
         else
           nomVersion = "";

         nomSt = rs.getString(2);
         nomSt = nomSt.replaceAll("\n", "<br>");
         descRoadmap = rs.getString(3);
         if (descRoadmap != null) {
           descRoadmap = descRoadmap.replaceAll("\n", "<br>");
         }
         else
           descRoadmap = "";
         java.util.Date theDateEB = rs.getDate(4);
         if (theDateEB != null) {
           dateEB = theDateEB.getDate() + "/" + (theDateEB.getMonth() + 1) + "/" +
               (theDateEB.getYear() + 1900);
           if (theDateEB.getYear() + 1900 <= 1995)
             dateEB = "";
         }
         else
           dateEB = "";

         java.util.Date thedateProd = rs.getDate(5);
         if (thedateProd != null) {
           dateProd = thedateProd.getDate() + "/" + (thedateProd.getMonth() + 1) +
               "/" + (thedateProd.getYear() + 1900);
           if (thedateProd.getYear() + 1900 <= 1995)
             dateProd = "";
         }
         else
           dateProd = "";

         /*
         String theSuivi = rs.getString(6);

         if (theSuivi != null) {
           //c1.trace("*********","Suivi",Suivi);
           Suivi = theSuivi.replaceAll("\n", "<br>");
           if (theSuivi.equals("null"))
             Suivi = "";
         }
         else {
           //c1.trace("*********","Suivi","c'est null !!!!!!!!!");
           Suivi = "";
           //c1.trace("*********","Null_Suivi",Suivi);
         }
*/
         Tendance = rs.getString(7);
         if (Tendance != null) {
           Tendance = Tendance.replaceAll("\n", "<br>");

         }
         else
           Tendance = "Inconnue";

         java.util.Date thedateMes = rs.getDate(8);
         //myCnx.trace("*********", "thedateMes", "" + thedateMes);
         if (thedateMes != null) {
           dateMes = thedateMes.getDate() + "/" + (thedateMes.getMonth() + 1) + "/" +
               (thedateMes.getYear() + 1900);
           if (thedateMes.getYear() + 1900 <= 1995)
             dateMes = "";
         }
         else
           dateMes = "";

         java.util.Date theDateT0 = rs.getDate("dateT0");
         if (theDateT0 != null) {
           dateT0 = theDateT0.getDate() + "/" + (theDateT0.getMonth() + 1) + "/" +
               (theDateT0.getYear() + 1900);
           if (theDateT0.getYear() + 1900 <= 1995)
             dateT0 = "";
         }
         else
           dateT0 = "";

         java.util.Date theDateTest = rs.getDate("dateTest");
         if (theDateTest != null) {
           dateTest = theDateTest.getDate() + "/" + (theDateTest.getMonth() + 1) +
               "/" + (theDateTest.getYear() + 1900);
           if (theDateTest.getYear() + 1900 <= 1995)
             dateTest = "";
         }
         else
           dateTest = "";

         docEB = rs.getString("docEB");
         docDevis = rs.getString("docDevis");

         //MotsClef = rs.getString("MotsClef");
         MotsClef = "";

         idPo = rs.getInt("idPO");
         int idRoadmap = rs.getInt("idRoadmap");
         Roadmap theRoadmap = new Roadmap (
                      nomSt,
                      "-1", //idVersionSt
                      "", //oldversion
                      nomVersion,
                      descRoadmap,
                      "", //EtatRoadmap
                      "", //Panier
                      ""+idPo,
                      "", //Charge
                      "", //Ordre
                      dateEB,
                      dateProd,
                      dateMes,
                      Suivi,
                      Tendance,
                      dateT0,
                      dateTest,
                      docEB,
                      docDevis,
                      Integer.parseInt(anneeRef),
                      MotsClef
                     );
         theRoadmap.id=idRoadmap;
         this.ListeProjets.addElement(theRoadmap);

         i++;
       }
     }
     catch (SQLException s) {s.getMessage(); }
}

  public void getListeProjetsByMonthByJalonByCouleur(String nomBase,Connexion myCnx, Statement st, String anneeRef , int num_mois, String Type, String SiRouge, String SiBleu){
    ResultSet rs;
    String couleur = "";
    if (SiRouge.equals("1")) couleur = "rouge";
    else
      couleur = "bleu";

if (this.isMoe != 1)
    {
        if (Type.equals("Mep"))
        {
          req = "SELECT     ListeProjets.nomProjet, ListeProjets.nomSt, ListeProjets.description, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.Suivi, ";
          req += "             ListeProjets.Tendance, ListeProjets.dateMes, ListeProjets.dateT0, ListeProjets.dateTest, ListeProjets.docEB, ListeProjets.docDevis, ListeProjets.idPO, ";
          req += "             TypeSi.couleurSI, idRoadmap, MOE,  MOA";
          req += " FROM         ListeProjets INNER JOIN";
          req +=
              "             TypeSi ON ListeProjets.typeSiVersionSt = TypeSi.idTypeSi";
          req += " WHERE     (ListeProjets.serviceMoaVersionSt = " + this.id +
              ") AND (MONTH(ListeProjets.dateMep) = " + (num_mois + 1) +
              ") AND (YEAR(ListeProjets.dateMep) = " + anneeRef + ")";
          req += "   AND  (ListeProjets.Panier = 'checked')";
          req += "   AND  (ListeProjets.standby  = 0)";
          req += "  AND isMeeting <> 1 ";
          req += " ORDER BY ListeProjets.dateMep";
        }
        else if (Type.equals("EB"))

        {
          req = "SELECT     ListeProjets.nomProjet, ListeProjets.nomSt, ListeProjets.description, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.Suivi, ";
          req += "             ListeProjets.Tendance, ListeProjets.dateMes, ListeProjets.dateT0, ListeProjets.dateTest, ListeProjets.docEB, ListeProjets.docDevis, ListeProjets.idPO, ";
          req += "             TypeSi.couleurSI, idRoadmap, MOE,  MOA";
          req += " FROM         ListeProjets INNER JOIN";
          req +=
              "             TypeSi ON ListeProjets.typeSiVersionSt = TypeSi.idTypeSi";
          req += " WHERE     (ListeProjets.serviceMoaVersionSt = " + this.id +
              ") AND (MONTH(ListeProjets.dateEB) = " + (num_mois + 1) +
              ") AND (YEAR(ListeProjets.dateEB) = " + anneeRef + ") ";
          req += " AND   (ListeProjets.Panier = 'checked')";
          req += "   AND  (ListeProjets.standby  = 0)";
          req += "  AND isMeeting <> 1 ";
          req += " ORDER BY ListeProjets.dateEB";
        }
        else if (Type.equals("Test"))
        {
          req = "SELECT     ListeProjets.nomProjet, ListeProjets.nomSt, ListeProjets.description, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.Suivi, ";
          req += "             ListeProjets.Tendance, ListeProjets.dateMes, ListeProjets.dateT0, ListeProjets.dateTest, ListeProjets.docEB, ListeProjets.docDevis, ListeProjets.idPO, ";
          req += "             TypeSi.couleurSI, idRoadmap, MOE,  MOA";
          req += " FROM         ListeProjets INNER JOIN";
          req +=
              "             TypeSi ON ListeProjets.typeSiVersionSt = TypeSi.idTypeSi";
          req += " WHERE     (ListeProjets.serviceMoaVersionSt = " + this.id +
              ") AND (MONTH(ListeProjets.dateTest) = " + (num_mois + 1) +
              ") AND (YEAR(ListeProjets.dateTest) = " + anneeRef + ") AND ";
          req += "             (ListeProjets.Panier = 'checked')";
          req += "   AND  (ListeProjets.standby  = 0)";
          req += "  AND isMeeting <> 1 ";
          req += " ORDER BY ListeProjets.dateTest";
        }
        else if (Type.equals("T0"))
        {
          req = "SELECT     ListeProjets.nomProjet, ListeProjets.nomSt, ListeProjets.description, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.Suivi, ";
          req += "             ListeProjets.Tendance, ListeProjets.dateMes, ListeProjets.dateT0, ListeProjets.dateTest, ListeProjets.docEB, ListeProjets.docDevis, ListeProjets.idPO, ";
          req += "             TypeSi.couleurSI, idRoadmap, MOE,  MOA";
          req += " FROM         ListeProjets INNER JOIN";
          req +=
              "             TypeSi ON ListeProjets.typeSiVersionSt = TypeSi.idTypeSi";
          req += " WHERE     (ListeProjets.serviceMoaVersionSt = " + this.id +
              ") AND (MONTH(ListeProjets.dateT0) = " + (num_mois + 1) +
              ") AND (YEAR(ListeProjets.dateT0) = " + anneeRef + ") AND ";
          req += "             (ListeProjets.Panier = 'checked')";
          req += "   AND  (ListeProjets.standby  = 0)";
          req += "  AND isMeeting <> 1 ";
          req += " ORDER BY ListeProjets.dateT0";
        }
    }
    else
    {
    if (Type.equals("Mep"))
    {
      req = "SELECT     ListeProjets.nomProjet, ListeProjets.nomSt, ListeProjets.description, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.Suivi, ";
      req += "             ListeProjets.Tendance, ListeProjets.dateMes, ListeProjets.dateT0, ListeProjets.dateTest, ListeProjets.docEB, ListeProjets.docDevis, ListeProjets.idPO, ";
      req += "             TypeSi.couleurSI, idRoadmap, MOE,  MOA";
      req += " FROM         ListeProjets INNER JOIN";
      req +=
          "             TypeSi ON ListeProjets.typeSiVersionSt = TypeSi.idTypeSi";
      req += " WHERE     (ListeProjets.serviceMoeVersionSt = " + this.id +
          ") AND (MONTH(ListeProjets.dateMep) = " + (num_mois + 1) +
          ") AND (YEAR(ListeProjets.dateMep) = " + anneeRef + ") AND ";
      req += "             (ListeProjets.Panier = 'checked')";
      req += "   AND  (ListeProjets.standby  = 0)";
      req += "  AND isMeeting <> 1 ";
      req += " ORDER BY ListeProjets.dateMep";
    }
    else if (Type.equals("EB"))

    {
      req = "SELECT     ListeProjets.nomProjet, ListeProjets.nomSt, ListeProjets.description, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.Suivi, ";
      req += "             ListeProjets.Tendance, ListeProjets.dateMes, ListeProjets.dateT0, ListeProjets.dateTest, ListeProjets.docEB, ListeProjets.docDevis, ListeProjets.idPO, ";
      req += "             TypeSi.couleurSI, idRoadmap, MOE,  MOA";
      req += " FROM         ListeProjets INNER JOIN";
      req +=
          "             TypeSi ON ListeProjets.typeSiVersionSt = TypeSi.idTypeSi";
      req += " WHERE     (ListeProjets.serviceMoeVersionSt = " + this.id +
          ") AND (MONTH(ListeProjets.dateEB) = " + (num_mois + 1) +
          ") AND (YEAR(ListeProjets.dateEB) = " + anneeRef + ") AND ";
      req += "             (ListeProjets.Panier = 'checked')";
      req += "   AND  (ListeProjets.standby  = 0)";
      req += "  AND isMeeting <> 1 ";
      req += " ORDER BY ListeProjets.dateEB";
    }
    else if (Type.equals("Test"))
    {
      req = "SELECT     ListeProjets.nomProjet, ListeProjets.nomSt, ListeProjets.description, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.Suivi, ";
      req += "             ListeProjets.Tendance, ListeProjets.dateMes, ListeProjets.dateT0, ListeProjets.dateTest, ListeProjets.docEB, ListeProjets.docDevis, ListeProjets.idPO, ";
      req += "             TypeSi.couleurSI, idRoadmap, MOE,  MOA";
      req += " FROM         ListeProjets INNER JOIN";
      req +=
          "             TypeSi ON ListeProjets.typeSiVersionSt = TypeSi.idTypeSi";
      req += " WHERE     (ListeProjets.serviceMoeVersionSt = " + this.id +
          ") AND (MONTH(ListeProjets.dateTest) = " + (num_mois + 1) +
          ") AND (YEAR(ListeProjets.dateTest) = " + anneeRef + ") AND ";
      req += "             (ListeProjets.Panier = 'checked')";
      req += "   AND  (ListeProjets.standby  = 0)";
      req += "  AND isMeeting <> 1 ";
      req += " ORDER BY ListeProjets.dateTest";
    }
    else if (Type.equals("T0"))
    {
      req = "SELECT     ListeProjets.nomProjet, ListeProjets.nomSt, ListeProjets.description, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.Suivi, ";
      req += "             ListeProjets.Tendance, ListeProjets.dateMes, ListeProjets.dateT0, ListeProjets.dateTest, ListeProjets.docEB, ListeProjets.docDevis, ListeProjets.idPO, ";
      req += "             TypeSi.couleurSI, idRoadmap, MOE,  MOA";
      req += " FROM         ListeProjets INNER JOIN";
      req +=
          "             TypeSi ON ListeProjets.typeSiVersionSt = TypeSi.idTypeSi";
      req += " WHERE     (ListeProjets.serviceMoeVersionSt = " + this.id +
          ") AND (MONTH(ListeProjets.dateT0) = " + (num_mois + 1) +
          ") AND (YEAR(ListeProjets.dateT0) = " + anneeRef + ") AND ";
      req += "             (ListeProjets.Panier = 'checked')";
      req += "   AND  (ListeProjets.standby  = 0)";
      req += "  AND isMeeting <> 1 ";
      req += " ORDER BY ListeProjets.dateT0";
        }
    }
    rs = myCnx.ExecReq(st, nomBase, req);
   String nomSt="";
   String nomVersion="";
   String descRoadmap = "";
   String dateT0="";
   String dateEB="";
   String dateTest="";
   String T0="";
   String dateProd = "";
   String dateMes= "";
   String Suivi = "";
   String Tendance = "";
   String docEB = "";
   String docDevis = "";
   String MotsClef = "";
   int idPo = 0;
   int i=1;

   this.ListeProjets.removeAllElements();
   try {
   while(rs.next())
   {
       nomVersion = rs.getString(1);
       if (nomVersion != null) {
         nomVersion = nomVersion.replaceAll("\n", "<br>");
       }
       else
         nomVersion = "";

       nomSt = rs.getString(2);
       nomSt = nomSt.replaceAll("\n", "<br>");
       descRoadmap = rs.getString(3);
       if (descRoadmap != null) {
         descRoadmap = descRoadmap.replaceAll("\n", "<br>");
       }
       else
         descRoadmap = "";
       java.util.Date theDateEB = rs.getDate(4);
       if (theDateEB != null) {
         dateEB = theDateEB.getDate() + "/" + (theDateEB.getMonth() + 1) + "/" +
             (theDateEB.getYear() + 1900);
         if (theDateEB.getYear() + 1900 <= 1995)
           dateEB = "";
       }
       else
         dateEB = "";

       java.util.Date thedateProd = rs.getDate(5);
       if (thedateProd != null) {
         dateProd = thedateProd.getDate() + "/" + (thedateProd.getMonth() + 1) +
             "/" + (thedateProd.getYear() + 1900);
         if (thedateProd.getYear() + 1900 <= 1995)
           dateProd = "";
       }
       else
         dateProd = "";
       /*
       String theSuivi = rs.getString(6);

       if (theSuivi != null) {
         //c1.trace("*********","Suivi",Suivi);
         Suivi = theSuivi.replaceAll("\n", "<br>");
         if (theSuivi.equals("null"))
           Suivi = "";
       }
       else {
         //c1.trace("*********","Suivi","c'est null !!!!!!!!!");
         Suivi = "";
         //c1.trace("*********","Null_Suivi",Suivi);
       }
       */

       Tendance = rs.getString(7);
       if (Tendance != null) {
         Tendance = Tendance.replaceAll("\n", "<br>");

       }
       else
         Tendance = "Inconnue";

       java.util.Date thedateMes = rs.getDate(8);
       //myCnx.trace("*********", "thedateMes", "" + thedateMes);
       if (thedateMes != null) {
         dateMes = thedateMes.getDate() + "/" + (thedateMes.getMonth() + 1) + "/" +
             (thedateMes.getYear() + 1900);
         if (thedateMes.getYear() + 1900 <= 1995)
           dateMes = "";
       }
       else
         dateMes = "";

       java.util.Date theDateT0 = rs.getDate("dateT0");
       if (theDateT0 != null) {
         dateT0 = theDateT0.getDate() + "/" + (theDateT0.getMonth() + 1) + "/" +
             (theDateT0.getYear() + 1900);
         if (theDateT0.getYear() + 1900 <= 1995)
           dateT0 = "";
       }
       else
         dateT0 = "";

       java.util.Date theDateTest = rs.getDate("dateTest");
       if (theDateTest != null) {
         dateTest = theDateTest.getDate() + "/" + (theDateTest.getMonth() + 1) +
             "/" + (theDateTest.getYear() + 1900);
         if (theDateTest.getYear() + 1900 <= 1995)
           dateTest = "";
       }
       else
         dateTest = "";

       docEB = rs.getString("docEB");
       docDevis = rs.getString("docDevis");

       //MotsClef = rs.getString("MotsClef");
       MotsClef = "";

       idPo = rs.getInt("idPO");
       int idRoadmap = rs.getInt("idRoadmap");
       Roadmap theRoadmap = new Roadmap (
                    nomSt,
                    "-1", //idVersionSt
                    "", //oldversion
                    nomVersion,
                    descRoadmap,
                    "", //EtatRoadmap
                    "", //Panier
                    ""+idPo,
                    "", //Charge
                    "", //Ordre
                    dateEB,
                    dateProd,
                    dateMes,
                    Suivi,
                    Tendance,
                    dateT0,
                    dateTest,
                    docEB,
                    docDevis,
                    Integer.parseInt(anneeRef),
                    MotsClef
                   );
       theRoadmap.id=idRoadmap;
       this.ListeProjets.addElement(theRoadmap);
       theRoadmap.MOE = rs.getString("MOE");
       if (theRoadmap.MOE == null) theRoadmap.MOE="";
       theRoadmap.MOA = rs.getString("MOA");
       if (theRoadmap.MOA == null) theRoadmap.MOA="";
       i++;
     }
   }
   catch (SQLException s) {s.getMessage(); }
}

 public void getListeProjets_Commun(String nomBase,Connexion myCnx, Statement st, int anneeRef, String theReq ){
   ResultSet rs;
   String nom="";

   String dateT0="";
   String dateEB="";
   String dateTEST="";
   String datePROD="";
   String dateMES="";

   String dateT0MOE="";
   String dateSpecMOE="";
   String dateDevMOE="";
   String dateTestMOE="";
   String dateLivrMOE="";

   int idVersionSt = -1;
   String version= "";
   Date theDate = null;

   this.ListeProjets.removeAllElements();

   rs = myCnx.ExecReq(st, myCnx.nomBase, theReq);

   try {
     while (rs.next()) {
       nom = rs.getString("nomProjet");
       dateEB = "";
       datePROD = "";
       dateMES = "";
       idVersionSt = rs.getInt("idVersionSt");
       version = rs.getString("version");
       theDate = rs.getDate("dateEB");
       dateEB = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateMep");
       datePROD = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateMes");
       dateMES = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateT0");
       dateT0 = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateTest");
       dateTEST = Utils.getDateFrench(theDate);

       theDate = rs.getDate("dateT0MOE");
       dateT0MOE = Utils.getDateFrench(theDate);
       /*       
       theDate = rs.getDate("dateSpecMOE");
       dateSpecMOE = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateDevMOE");
       dateDevMOE = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateTestMOE");
       dateTestMOE = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateLivrMOE");
       dateLivrMOE = Utils.getDateFrench(theDate);
       */

       Roadmap theRoadmap = new Roadmap(-1);
       theRoadmap.nomSt=nom;
       theRoadmap.dateT0=dateT0;
       theRoadmap.dateEB=dateEB;
       theRoadmap.dateTest=dateTEST;
       theRoadmap.dateProd=datePROD;
       theRoadmap.dateMes=dateMES;

       theRoadmap.dateT0MOE=dateT0MOE;
       theRoadmap.dateSpecMOE=dateSpecMOE;
       theRoadmap.dateDevMOE=dateDevMOE;
       theRoadmap.dateTestMOE=dateTestMOE;
       theRoadmap.dateLivrMOE=dateLivrMOE;

       theRoadmap.idVersionSt=""+idVersionSt;
       theRoadmap.version=version;

       theDate = rs.getDate("dateT0_init");
       theRoadmap.dateT0_init = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateEB_init");
       theRoadmap.dateEB_init = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateTest_init");
       theRoadmap.dateTest_init = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateMep_init");
       theRoadmap.dateProd_init = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateMes_init");
       theRoadmap.dateMes_init = Utils.getDateFrench(theDate);
       theRoadmap.id= rs.getInt("idRoadmap");
       theRoadmap.idPO= ""+rs.getInt("idPO");

       theRoadmap.standby= rs.getInt("standby");
       if (theRoadmap.standby == 1) continue;
       
       theRoadmap.nbDependances= rs.getInt("nbDependances");
       theRoadmap.nbActions= rs.getInt("nbActions");

       //theProjet.dump();

       if (dateT0MOE == null) theRoadmap.dateT0MOE="";
       if (dateSpecMOE == null) theRoadmap.dateSpecMOE="";
       if (dateDevMOE == null) theRoadmap.dateDevMOE="";
       if (dateTestMOE == null) theRoadmap.dateTestMOE="";
       if (dateLivrMOE == null) theRoadmap.dateLivrMOE="";

       if ((theRoadmap.dateT0 == null) || (theRoadmap.dateT0.equals(""))) continue;
       if ((theRoadmap.dateEB == null) || (theRoadmap.dateEB.equals(""))) continue;
       if ((theRoadmap.dateTest == null) || (theRoadmap.dateTest.equals(""))) continue;
       if ((theRoadmap.dateProd == null) || (theRoadmap.dateProd.equals(""))) continue;

       this.ListeProjets.addElement(theRoadmap);
     }
   }
catch (SQLException s) { }
}

 public void getListeProjetsOpen(String nomBase,Connexion myCnx, Statement st, Statement st2, String AnneeRef, String PeriodeDebut, String PeriodeFin){

   String ClausePeriode = "";
   if (!PeriodeDebut.equals("") && !PeriodeFin.equals("")  )
   {
   ClausePeriode +=" AND ";
   ClausePeriode +=" (";
   ClausePeriode +=" ((ListeProjets.dateT0 >= CONVERT(DATETIME, '"+PeriodeDebut+"', 103) AND ListeProjets.dateT0 <= CONVERT(DATETIME, '"+PeriodeFin+"', 103)))";
   ClausePeriode +=" OR";
   ClausePeriode +=" ((ListeProjets.dateEB >= CONVERT(DATETIME, '"+PeriodeDebut+"', 103) AND ListeProjets.dateEB <= CONVERT(DATETIME, '"+PeriodeFin+"', 103)))";
   ClausePeriode +=" OR";
   ClausePeriode +=" ((ListeProjets.dateTest >= CONVERT(DATETIME, '"+PeriodeDebut+"', 103) AND ListeProjets.dateTest <= CONVERT(DATETIME, '"+PeriodeFin+"', 103)))";
   ClausePeriode +=" OR";
   ClausePeriode +=" ((ListeProjets.dateMep >= CONVERT(DATETIME, '"+PeriodeDebut+"', 103) AND ListeProjets.dateMep <= CONVERT(DATETIME, '"+PeriodeFin+"', 103)))";
   ClausePeriode +=" )   ";
    }

   req = "select * from(";
   req += "    SELECT DISTINCT ";
   req += "                    ListeProjets.idRoadmap, ListeProjets.nomSt, ListeProjets.version, ListeProjets.Tendance, ListeProjets.Etat, ListeProjets.dateT0, ListeProjets.dateEB, ";
   req += "                    ListeProjets.dateTest, ListeProjets.dateMep, ListeProjets.docEB, ListeProjets.docDevis, ListeProjets.idPO, TypeSi.nomTypeSi, ";
   req += "                    ListeProjets.nomServiceImputations";
   req += " FROM         ListeProjets INNER JOIN";
   req +=
       "                    TypeSi ON ListeProjets.typeSiVersionSt = TypeSi.idTypeSi";

   req += " WHERE     ((ListeProjets.serviceMoaVersionSt = " + this.id + ") OR (ListeProjets.serviceMoeVersionSt = " + this.id + "))";
   req+=" AND (YEAR(ListeProjets.dateMep) >= " + AnneeRef + ") AND (ListeProjets.isMeeting <> 1)  AND (ListeProjets.etatVersionSt <> 4) AND (LF_Month = - 1) AND (LF_Year = - 1)  AND (ListeProjets.Panier = 'checked') ";
   req+=ClausePeriode;

   req += " union";

   req +=
       " SELECT     -1 as idRoadmap, '-' as nomSt, '-' as version, '-' as Tendance";
   req += "   , '-' as Etat, NULL as dateT0, NULL as dateEB, NULL as dateTest, NULL as dateMep";
   req += " , '-' as docEB, '-' as docDevis ";
   req += " , idWebPo as idPO, '-' as nomTypeSi, '-' as nomServiceMetier";

   req += " FROM         PlanOperationnelClient";
   req += " WHERE     (Annee = " + AnneeRef + ") AND (idWebPo NOT IN";
   req += "                       (SELECT DISTINCT idPO";
   req += "                       FROM          ListeProjets";
   req += "                       WHERE      (serviceMoaVersionSt = " + this.id +
       ") AND (dateMep IS NOT NULL) AND (YEAR(dateMep) = " + AnneeRef + " OR";
   req += "                                               YEAR(dateT0) = " +
       AnneeRef + ") AND (dateT0 <> CONVERT(DATETIME, '1900-01-01 00:00:00', 102)) AND (LF_Month = - 1) AND (LF_Year = - 1) AND ";
   req +=
       "                                              (idPO <> - 1))) AND (Service = '" +
       this.nomServiceImputations + "')";
   //req+=" ) AS maTable ORDER BY nomSt, version";
   req += " ) AS maTable ORDER BY idPO, nomSt";

   ResultSet rs;
   ResultSet rs2;
   String nomSt = "";
   String version = "";
   String nomProjet = "";
   String Tendance = "";
   String Etat = "";

   String dateT0 = "";
   String dateEB = "";
   String dateTEST = "";
   String datePROD = "";
   String dateMES = "";

   String dateT0MOE = "";
   String dateSpecMOE = "";
   String dateDevMOE = "";
   String dateTestMOE = "";
   String dateLivrMOE = "";

   Date theDate = null;

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
       Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap"));
       nomSt = rs.getString("nomSt");
       version = rs.getString("version");
       Tendance = rs.getString("Tendance");
       Etat = rs.getString("Etat");

       dateT0 = "";
       dateEB = "";
       dateTEST = "";
       datePROD = "";
       dateMES = "";

       theDate = rs.getDate("dateT0");
       dateT0 = Utils.getDateFrench(theDate);

       theDate = rs.getDate("dateEB");
       dateEB = Utils.getDateFrench(theDate);

       theDate = rs.getDate("dateTest");
       dateTEST = Utils.getDateFrench(theDate);

       theDate = rs.getDate("dateMep");
       datePROD = Utils.getDateFrench(theDate);

       theRoadmap.nomSt = nomSt;
       theRoadmap.version = version;
       theRoadmap.Tendance = Tendance;
       theRoadmap.EtatRoadmap = Etat;
       theRoadmap.dateT0 = dateT0;
       theRoadmap.dateEB = dateEB;
       theRoadmap.dateTest = dateTEST;
       theRoadmap.dateProd = datePROD;
       theRoadmap.dateMes = dateMES;

       theRoadmap.docEB = rs.getString("docEB");
       theRoadmap.docDevis = rs.getString("docDevis");
       theRoadmap.idPO = rs.getString("idPO");

       theRoadmap.getChargeDevis(nomBase, myCnx, st2);
       //

       //theRoadmap.ChargePO = rs.getString("MontantPo");


       if ( (theRoadmap.ChargePO != null) && (!theRoadmap.ChargePO.equals(""))) {
         int pos = theRoadmap.ChargePO.indexOf(".");
         theRoadmap.ChargePO = theRoadmap.ChargePO.substring(0, pos) + "," +
             theRoadmap.ChargePO.substring(pos + 1);
       }
       else
         theRoadmap.ChargePO = "";

       theRoadmap.nomTypeSi = rs.getString("nomTypeSi");

       //theRoadmap.nomServiceMetier = rs.getString("nomServiceImputations");
       theRoadmap.nomServiceMetier = this.nomServiceImputations;
       if (theRoadmap.nomServiceMetier == null)
         theRoadmap.nomServiceMetier = "";

       req = "SELECT     nomProjet,charge, chargeConsommee";
       req += "     FROM         PlanOperationnelClient";
       req += " WHERE     (idWebPo = " + theRoadmap.idPO + ") AND (Annee = " +
           AnneeRef + ") AND (Service = '" + theRoadmap.nomServiceMetier + "')";

       rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);

       try {
         while (rs2.next()) {

                  theRoadmap.nomProjetClient = rs2.getString("nomProjet");
                  theRoadmap.Charge = ""+rs2.getInt("charge");
                  theRoadmap.chargeConsommee = ""+rs2.getInt("chargeConsommee");

         }
         this.ListeProjets.addElement(theRoadmap);
       }
       catch (SQLException s) {}
     }

   }
   catch (SQLException s) {}
 }

 public void getListeProjetsOpenByPeriode(String nomBase,Connexion myCnx, Statement st, Statement st2, String AnneeRef, String PeriodeDebut, String PeriodeFin){

   String ClausePeriode = "";
   if (!PeriodeDebut.equals("") && !PeriodeFin.equals("")  )
   {
   ClausePeriode +=" AND ";
   ClausePeriode +=" (";
   ClausePeriode +=" ((ListeProjets.dateT0 >= CONVERT(DATETIME, '"+PeriodeDebut+"', 103) AND ListeProjets.dateT0 <= CONVERT(DATETIME, '"+PeriodeFin+"', 103)))";
   ClausePeriode +=" OR";
   ClausePeriode +=" ((ListeProjets.dateEB >= CONVERT(DATETIME, '"+PeriodeDebut+"', 103) AND ListeProjets.dateEB <= CONVERT(DATETIME, '"+PeriodeFin+"', 103)))";
   ClausePeriode +=" OR";
   ClausePeriode +=" ((ListeProjets.dateTest >= CONVERT(DATETIME, '"+PeriodeDebut+"', 103) AND ListeProjets.dateTest <= CONVERT(DATETIME, '"+PeriodeFin+"', 103)))";
   ClausePeriode +=" OR";
   ClausePeriode +=" ((ListeProjets.dateMep >= CONVERT(DATETIME, '"+PeriodeDebut+"', 103) AND ListeProjets.dateMep <= CONVERT(DATETIME, '"+PeriodeFin+"', 103)))";
   ClausePeriode +=" )   ";
    }

   req = "";
   req += "    SELECT DISTINCT ";
   req += "                    ListeProjets.idRoadmap, ListeProjets.nomSt, ListeProjets.version, ListeProjets.Tendance, ListeProjets.Etat, ListeProjets.dateT0, ListeProjets.dateEB, ";
   req += "                    ListeProjets.dateTest, ListeProjets.dateMep, ListeProjets.docEB, ListeProjets.docDevis, ListeProjets.idPO, TypeSi.nomTypeSi, ";
   req += "                    ListeProjets.nomServiceImputations, dateEB_init, dateMep_init, dateT0MOE, ListeProjets.standby, ListeProjets.idVersionSt";
   req += " FROM         ListeProjets INNER JOIN";
   req += " TypeSi ON ListeProjets.typeSiVersionSt = TypeSi.idTypeSi";


   req += " WHERE     ((ListeProjets.serviceMoaVersionSt = " + this.id + ") OR (ListeProjets.serviceMoeVersionSt = " + this.id + "))";
   req+=" AND ((YEAR(ListeProjets.dateMep) >= " + AnneeRef + ") OR (YEAR(ListeProjets.dateT0) >= " + AnneeRef + ")) AND (ListeProjets.isMeeting <> 1)  AND (ListeProjets.etatVersionSt <> 4) AND (LF_Month = - 1) AND (LF_Year = - 1)  AND (ListeProjets.Panier = 'checked') ";

   req +=ClausePeriode;
   req += "order by idPO, nomSt";

   ResultSet rs;
   ResultSet rs2;
   String nomSt = "";
   String version = "";
   String nomProjet = "";
   String Tendance = "";
   String Etat = "";

   String dateT0 = "";
   String dateEB = "";
   String dateTEST = "";
   String datePROD = "";
   String dateMES = "";

   String dateEB_init = "";
   String datePROD_init = "";


   Date theDate = null;

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
       Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap"));
       nomSt = rs.getString("nomSt");
       version = rs.getString("version");
       Tendance = rs.getString("Tendance");
       Etat = rs.getString("Etat");

       dateT0 = "";
       dateEB = "";
       dateTEST = "";
       datePROD = "";
       dateMES = "";

       theDate = rs.getDate("dateT0");
       dateT0 = Utils.getDateFrench(theDate);

       theDate = rs.getDate("dateEB");
       dateEB = Utils.getDateFrench(theDate);

       theDate = rs.getDate("dateTest");
       dateTEST = Utils.getDateFrench(theDate);

       theDate = rs.getDate("dateMep");
       datePROD = Utils.getDateFrench(theDate);

       theRoadmap.nomSt = nomSt;
       theRoadmap.version = version;
       theRoadmap.Tendance = Tendance;
       theRoadmap.EtatRoadmap = Etat;
       theRoadmap.dateT0 = dateT0;
       theRoadmap.dateEB = dateEB;
       theRoadmap.dateTest = dateTEST;
       theRoadmap.dateProd = datePROD;
       theRoadmap.dateMes = dateMES;

       theRoadmap.docEB = rs.getString("docEB");
       theRoadmap.docDevis = rs.getString("docDevis");
       theRoadmap.idPO = rs.getString("idPO");

       theRoadmap.getChargeDevis(nomBase, myCnx, st2);
       //

       //theRoadmap.ChargePO = rs.getString("MontantPo");


       if ( (theRoadmap.ChargePO != null) && (!theRoadmap.ChargePO.equals(""))) {
         int pos = theRoadmap.ChargePO.indexOf(".");
         theRoadmap.ChargePO = theRoadmap.ChargePO.substring(0, pos) + "," +
             theRoadmap.ChargePO.substring(pos + 1);
       }
       else
         theRoadmap.ChargePO = "";

       theRoadmap.nomTypeSi = rs.getString("nomTypeSi");

       //theRoadmap.nomServiceMetier = rs.getString("nomServiceImputations");
       theRoadmap.nomServiceMetier = this.nomServiceImputations;
       if (theRoadmap.nomServiceMetier == null)
         theRoadmap.nomServiceMetier = "";

       theDate = rs.getDate("dateEB_init"); //dateEB_init, dateMep_init
       theRoadmap.dateEB_init = Utils.getDateFrench(theDate);

       theDate = rs.getDate("dateMep_init"); //dateEB_init, dateMep_init
       theRoadmap.dateProd_init = Utils.getDateFrench(theDate);

       theDate = rs.getDate("dateT0MOE"); //dateEB_init, dateMep_init
       theRoadmap.dateT0MOE = Utils.getDateFrench(theDate);

       theRoadmap.standby = rs.getInt("standby");
       theRoadmap.idVersionSt = rs.getString("idVersionSt");

       req = "SELECT     nomProjet,charge, chargeConsommee";
       req += "     FROM         PlanOperationnelClient";
       req += " WHERE     (idWebPo = " + theRoadmap.idPO + ") AND (Annee = " +
           AnneeRef + ") AND (Service = '" + theRoadmap.nomServiceMetier + "')";

       rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);

       try {
         while (rs2.next()) {

                  theRoadmap.nomProjetClient = rs2.getString("nomProjet");
                  theRoadmap.Charge = ""+rs2.getInt("charge");
                  theRoadmap.chargeConsommee = ""+rs2.getInt("chargeConsommee");

         }
         this.ListeProjets.addElement(theRoadmap);
       }
       catch (SQLException s) {}
     }

   }
   catch (SQLException s) {}
 }


 public float getNbEtp(String nomBase,Connexion myCnx, Statement st){

   float nb=0;

   req="SELECT     SUM(Membre.etp) AS nbEtp";
   req+="     FROM         Service INNER JOIN";
   req+="                    Membre ON Service.idService = Membre.serviceMembre";
   req+=" WHERE     (Service.idService = "+this.id+")";

   ResultSet rs;

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
       nb= rs.getFloat("nbEtp");

     }
   }
catch (SQLException s) { }

   return nb;

 }

 public float getNbJoursEtp(String nomBase,Connexion myCnx, Statement st){

   ResultSet rs;

   req="SELECT     Membre.etp";
   req+=" FROM         Service INNER JOIN";
   req+=" Membre ON Service.idService = Membre.serviceMembre";
   req+=" WHERE     (Service.idService = "+this.id+") AND (Membre.societeMembre = 0)";



   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
       float etp = rs.getFloat("etp");
       this.nb_internes+=etp;
     }
   }
catch (SQLException s) { }

   req="SELECT     Membre.etp";
   req+=" FROM         Service INNER JOIN";
   req+=" Membre ON Service.idService = Membre.serviceMembre";
   req+=" WHERE     (Service.idService = "+this.id+") AND (Membre.societeMembre <> 0)";

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
       float etp = rs.getFloat("etp");
       this.nb_externes+=etp;
     }
   }
catch (SQLException s) { }

   this.nb_joursTotal = Service.nbJoursInternes * nb_internes + Service.nbJoursExternes * nb_externes;
   this.nb_joursMoyens = (this.nb_internes * Service.nbJoursInternes)/(this.nb_internes + this.nb_externes) + (this.nb_externes * Service.nbJoursExternes)/(this.nb_internes + this.nb_externes);

   return this.nb_joursTotal;

 }
 public float getRAFGlobal(String nomBase,Connexion myCnx, Statement st , int anneeRef, Date dateExtraction, String nomServiceImputations){
          int semaine = Utils.getWeek(nomBase,myCnx, st,dateExtraction.getDate(),dateExtraction.getMonth()+1,dateExtraction.getYear()+1900);
          ResultSet rs;
          req="SELECT     SUM(PlanDeCharge.Charge) AS total";
          req+="    FROM         Roadmap INNER JOIN";
          req+="            PlanDeCharge ON Roadmap.idRoadmap = PlanDeCharge.idRoadmap INNER JOIN";
          req+="            Membre ON PlanDeCharge.projetMembre = Membre.idMembre INNER JOIN";
          req+="            Service ON Membre.serviceMembre = Service.idService";
          req+=" WHERE     (PlanDeCharge.anneeRef = "+anneeRef+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND (Membre.etp > 0) AND ";
          req+="            (PlanDeCharge.semaine > "+semaine+")";
          req+=" AND      (Service.nomServiceImputations = '"+nomServiceImputations+"') AND   (Roadmap.idPO > 0)";

          req+="           AND  Roadmap.idPO";
          req+="  IN (SELECT DISTINCT idWebPo";
          req+=" FROM         PlanOperationnelClient";
          req+=" WHERE     (Service = '"+nomServiceImputations+"') AND (Annee = "+anneeRef+"))";
// ---------
          req="          SELECT     SUM(PlanDeCharge.Charge) AS total";
          req+="     FROM         Roadmap INNER JOIN";
          req+="             PlanDeCharge ON Roadmap.idRoadmap = PlanDeCharge.idRoadmap INNER JOIN";
          req+="             Membre ON PlanDeCharge.projetMembre = Membre.idMembre INNER JOIN";
          req+="             Service ON Membre.serviceMembre = Service.idService";
          req+="  WHERE     (PlanDeCharge.anneeRef = "+anneeRef+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND (Membre.etp > 0) AND ";
          req+="             (PlanDeCharge.semaine > "+semaine+") ";
          req+="  AND      (Service.nomServiceImputations = '"+nomServiceImputations+"') AND   (Roadmap.idPO > 0)";

          req+="  AND  Roadmap.idPO";
          req+="  IN (";
          req+="       SELECT DISTINCT idWebPo";
          req+="               FROM         PlanOperationnelClient";
          req+="       WHERE     (Service = '"+nomServiceImputations+"') AND (Annee = "+anneeRef+")";
          req+="       )";



          rs = myCnx.ExecReq(st, nomBase, req);
          float total=0;
          try {
              while (rs.next()) {
                           total = rs.getFloat(1);
                       }
                }
                                 catch (SQLException s) {s.getMessage();}
          return total;
}

 public void getTotalChargeReestimeesProjetsClients(String nomBase,Connexion myCnx, Statement st, String AnneeRef){

   req = "SELECT     SUM(charge) AS TotalchargeReestimee";
   req+="     FROM         PlanOperationnelClient_simulation INNER JOIN";
   req+="               Service ON      PlanOperationnelClient_simulation.Service = Service.nomServiceImputations";
       req+=" WHERE     (Service.idService = "+this.id+") AND (PlanOperationnelClient_simulation.Annee = "+AnneeRef+")";


   ResultSet rs;

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
       this.TotalchargeReestimee = rs.getInt("TotalchargeReestimee");

     }
   }
catch (SQLException s) { }

 }
 
  public void getTotalChargeReestimeesProjetsClients_old(String nomBase,Connexion myCnx, Statement st, String AnneeRef){

   req = "SELECT     SUM(ChiffrageLF.chargeReestimee) AS TotalchargeReestimee";
   req+="     FROM         ChiffrageLF INNER JOIN";
   req+="                    Service ON ChiffrageLF.nomService = Service.nomServiceImputations";
       req+=" WHERE     (Service.idService = "+this.id+") AND (ChiffrageLF.Annee = "+AnneeRef+")";


   ResultSet rs;

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
       this.TotalchargeReestimee = rs.getInt("TotalchargeReestimee");

     }
   }
catch (SQLException s) { }

 }
  
public float getConsomme(String nomBase,Connexion myCnx, Statement st , int anneeRef){
                //int semaine = Utils.getWeek(dateExtraction.getDate(),dateExtraction.getMonth()+1,dateExtraction.getYear()+1900);
                int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
                ResultSet rs;
                
                req="           SELECT     SUM(PlanDeCharge.Charge) AS total";
                req+="               FROM         Roadmap INNER JOIN";
                req+="                        PlanDeCharge ON Roadmap.idRoadmap = PlanDeCharge.idRoadmap INNER JOIN";
                req+="                        Membre ON PlanDeCharge.projetMembre = Membre.idMembre INNER JOIN";
                req+="                        Service ON Membre.serviceMembre = Service.idService";
                req+="             WHERE     (PlanDeCharge.anneeRef = "+anneeRef+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND (Membre.etp > 0) AND ";
                req+="                        (PlanDeCharge.semaine <= "+semaine+") ";
                req+="             AND Roadmap.idPO IN (SELECT idWebPo from PlanOperationnelClient where service = '"+this.nomServiceImputations+"')";
                req+="             GROUP BY Service.nomServiceImputations";
                req+="             HAVING      (Service.nomServiceImputations = '"+this.nomServiceImputations+"') ";  

                rs = myCnx.ExecReq(st, nomBase, req);
                float total=0;
                try {
                    while (rs.next()) {
                                 total = rs.getFloat(1);
                             }
                      }
                                       catch (SQLException s) {s.getMessage();}
                return total;
} 

public float getRAF(String nomBase,Connexion myCnx, Statement st , int anneeRef){
                //int semaine = Utils.getWeek(dateExtraction.getDate(),dateExtraction.getMonth()+1,dateExtraction.getYear()+1900);
                int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
                ResultSet rs;
                
                req="           SELECT     SUM(PlanDeCharge.Charge) AS total";
                req+="               FROM         Roadmap INNER JOIN";
                req+="                        PlanDeCharge ON Roadmap.idRoadmap = PlanDeCharge.idRoadmap INNER JOIN";
                req+="                        Membre ON PlanDeCharge.projetMembre = Membre.idMembre INNER JOIN";
                req+="                        Service ON Membre.serviceMembre = Service.idService";
                req+="             WHERE     (PlanDeCharge.anneeRef = "+anneeRef+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND (Membre.etp > 0) AND ";
                req+="                        (PlanDeCharge.semaine > "+semaine+") ";
                req+="             AND Roadmap.idPO IN (SELECT idWebPo from PlanOperationnelClient where service = '"+this.nomServiceImputations+"')";
                req+="             GROUP BY Service.nomServiceImputations";
                req+="             HAVING      (Service.nomServiceImputations = '"+this.nomServiceImputations+"') ";  

                rs = myCnx.ExecReq(st, nomBase, req);
                float total=0;
                try {
                    while (rs.next()) {
                                 total = rs.getFloat(1);
                             }
                      }
                                       catch (SQLException s) {s.getMessage();}
                return total;
} 

 public void getTotalChargeProjetsClients(String nomBase,Connexion myCnx, Statement st, String AnneeRef){

   req = "SELECT     SUM(PlanOperationnelClient.charge) AS chargesPrevues, SUM(PlanOperationnelClient.chargeConsommee) AS chargesConsommees";
   req+="    FROM         PlanOperationnelClient INNER JOIN";
   req+="                    Service ON PlanOperationnelClient.Service = Service.nomServiceImputations";
   req+=" WHERE     (Service.idService = "+this.id+") AND (PlanOperationnelClient.Annee = "+AnneeRef+")";


   ResultSet rs;

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
       this.totalChargePrevue = rs.getFloat("chargesPrevues");
       this.totalChargeConsommee = rs.getFloat("chargesConsommees");
     }
   }
catch (SQLException s) { }

 }

 public void getListeProjetsClients(String nomBase,Connexion myCnx, Statement st, String AnneeRef, int LF){
   req = "   SELECT DISTINCT ";
   req+="                    PlanOperationnelClient.idWebPo, PlanOperationnelClient.nomProjet, PlanOperationnelClient.Annee, ";
   req+="                     PlanOperationnelClient.etat,  PlanOperationnelClient.charge,";
   req+="                     PlanOperationnelClient.chargeConsommee, PlanOperationnelClient.codeSujet, PlanOperationnelClient.Part, ";
   req+="                     PlanOperationnelClient.TypeCharge, (CAST(PlanOperationnelClient.descProjet AS varchar(255))) as  descProjet, depensePrevue, depenseConsommee ";
   req+="  FROM         PlanOperationnelClient INNER JOIN";
   req+="                     Service ON PlanOperationnelClient.Service = Service.nomServiceImputations  AND ";
   req+="                     PlanOperationnelClient.Annee = PlanOperationnelClient.Annee";
   req+="  WHERE     (Service.idService = "+this.id+") AND (PlanOperationnelClient.Annee = "+AnneeRef+")";
   req+="  ORDER BY PlanOperationnelClient.idWebPo";


   ResultSet rs;
   String nomSt="";
   String version="";
   String nomProjet="";
   String Tendance = "";
   String Etat = "";

   Date theDate = null;

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
        Roadmap theRoadmap = new Roadmap(-1);
        theRoadmap.idPO = rs.getString("idWebPo");
        theRoadmap.nomProjet = rs.getString("nomProjet");
        theRoadmap.EtatRoadmap = rs.getString("etat");
        theRoadmap.Charge = ""+rs.getFloat("charge");
        theRoadmap.chargeConsommee = ""+rs.getFloat("chargeConsommee");
        theRoadmap.codeSujet = rs.getString("codeSujet");
        theRoadmap.part = rs.getString("Part");
        theRoadmap.TypeCharge = rs.getString("TypeCharge");
        theRoadmap.description=rs.getString("descProjet");
        if (theRoadmap.description == null) theRoadmap.description = "";
        
        theRoadmap.ChargePrevueForfait = rs.getFloat("depensePrevue");
        theRoadmap.chargeConsommeForfait = rs.getFloat("depenseConsommee");

        //myCnx.trace("@@99------","nomProjet/Charge/chargeConsommee",""+theRoadmap.nomProjet+"/"+theRoadmap.Charge+"/"+theRoadmap.chargeConsommee);
        this.ListeProjets.addElement(theRoadmap);
     }
   }
catch (SQLException s) { }
 }

 public void getListeProjetsClients(String nomBase,Connexion myCnx, Statement st, String AnneeRef){
   req = "SELECT DISTINCT ";
   req+="                    PlanOperationnelClient.idWebPo, PlanOperationnelClient.nomProjet, PlanOperationnelClient.Annee, PlanOperationnelClient.etat, ";
   req+="                    PlanOperationnelClient.charge, PlanOperationnelClient.chargeConsommee,";
   req+="                    PlanOperationnelClient.codeSujet, PlanOperationnelClient.Part, PlanOperationnelClient.TypeCharge,";
   req+="                    CAST(PlanOperationnelClient.descProjet AS varchar(MAX)) AS description, chefProjet as Client, dateEF, dateMEP";
   req+="                    ,";
   req+="                    CAST(PlanOperationnelClient.gainProjet AS varchar(MAX)) AS gainProjet, Priorite,  CAST(PlanOperationnelClient.risqueProjet AS varchar(MAX)) AS risqueProjet";   
   req+=" FROM         PlanOperationnelClient INNER JOIN";
   req+="                    Service ON PlanOperationnelClient.Service = Service.nomServiceImputations";

   req+=" WHERE     (Service.idService = "+this.id+") AND (PlanOperationnelClient.Annee = "+AnneeRef+")";
   req+=" ORDER BY idWebPo";


   ResultSet rs;
   String nomSt="";
   String version="";
   String nomProjet="";
   String Tendance = "";
   String Etat = "";

   Date theDate = null;

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
        Roadmap theRoadmap = new Roadmap(-1);
        theRoadmap.idPO = rs.getString("idWebPo");
        theRoadmap.nomProjet = rs.getString("nomProjet");
        theRoadmap.EtatRoadmap = rs.getString("etat");
        theRoadmap.Charge = ""+rs.getFloat("charge");
        theRoadmap.chargeConsommee = ""+rs.getFloat("chargeConsommee");
        theRoadmap.codeSujet = rs.getString("codeSujet");
        theRoadmap.part = rs.getString("Part");
        theRoadmap.TypeCharge = rs.getString("TypeCharge");
        theRoadmap.description = rs.getString("description");
        theRoadmap.nomServiceMetier = rs.getString("Client");
        theRoadmap.dateT0 = rs.getString("dateEF");
        theRoadmap.dateProd = rs.getString("dateMEP");
        
        theRoadmap.dateT0_init = theRoadmap.dateT0;
        theRoadmap.dateProd_init = theRoadmap.dateProd;
        
        theRoadmap.ROI = ""+rs.getString("gainProjet");
        theRoadmap.priorite = ""+rs.getString("Priorite");
        theRoadmap.stabilite = ""+rs.getString("risqueProjet");
        
        this.ListeProjets.addElement(theRoadmap);
     }
   }
catch (SQLException s) { }
 }


  public String Resynchro(String nomBase,Connexion myCnx, Statement st, String  transaction, int Annee){
   ResultSet rs=null;

   req = "delete from [PlanOperationnelClient_simulation]where service= '"+this.nomServiceImputations+"'";
   req+=" and Annee="+Annee;
   if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
  // myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());

   req = "insert into [PlanOperationnelClient_simulation] select * from [PlanOperationnelClient]where service= '"+this.nomServiceImputations+"'";
   req+=" and Annee="+Annee;
   //req = "insert into [PlanOperationnelClient_simulation] select idWebPo, nomProjet, charge, LF, Annee, etat, Service, chargeConsommee, dateExtraction, codeSujet, Part, TypeCharge, dateConsomme from [PlanOperationnelClient]where service= '"+this.nomServiceImputations+"'";
   if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
   //myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());

   req = "update [PlanOperationnelClient_simulation] set LF=1";
   if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
  // myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());


   return "OK";
  }
  
 public String Resynchro_old(String nomBase,Connexion myCnx, Statement st, String  transaction, int Annee){
   ResultSet rs=null;

   Vector ListeItems = new Vector(10);
   req = "SELECT     idWebPo, descProjet";
   req+="    FROM         PlanOperationnelClient_simulation";
   req+=" WHERE     (descProjet IS NOT NULL) AND (descProjet NOT LIKE '')";
   req+=" AND (Service = '"+this.nomServiceImputations+"') AND (Annee = "+Annee+")";

   // Bouclage sur toutes les entre�s ou il y a une description
        // sauvegarde idPO, description -->Items
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

        try {
          while (rs.next()) {
             item theitem = new item();
             theitem.id = rs.getInt("idWebPo");
             theitem.nom = rs.getString("descProjet");

             ListeItems.add(theitem);

          }
        }
catch (SQLException s) { }


   req = "delete from [PlanOperationnelClient_simulation]where service= '"+this.nomServiceImputations+"'";
   if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
  // myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());

   req = "insert into [PlanOperationnelClient_simulation] select * from [PlanOperationnelClient]where service= '"+this.nomServiceImputations+"'";
   //req = "insert into [PlanOperationnelClient_simulation] select idWebPo, nomProjet, charge, LF, Annee, etat, Service, chargeConsommee, dateExtraction, codeSujet, Part, TypeCharge, dateConsomme from [PlanOperationnelClient]where service= '"+this.nomServiceImputations+"'";
   if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
   //myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());

   req = "update [PlanOperationnelClient_simulation] set LF=1";
   if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
  // myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());


   req = "delete from [ChiffrageLF] where nomService= '"+this.nomServiceImputations+"'";
   if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
   //myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());

   for (int i=0; i < ListeItems.size(); i++)
   {
     item theItem = (item)ListeItems.elementAt(i);
     req = "update [PlanOperationnelClient_simulation] set descProjet='"+theItem.nom+"'";
     req+= " WHERE idWebPo = "+theItem.id;
     req+= " AND (Service = '"+this.nomServiceImputations+"') AND (Annee = "+Annee+")";

     if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
     //myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());

   }
   return "OK";
  }

  public String Resynchro2(String nomBase,Connexion myCnx, Statement st, String  transaction){
    ResultSet rs=null;

    req = "delete from [ChiffrageLF] where nomService= '"+this.nomServiceImputations+"'";
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
   // myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());

    req = "delete from [PlanOperationnelClient_simulation]where service= '"+this.nomServiceImputations+"'";
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
    //myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());

    req = "insert into [PlanOperationnelClient_simulation] select * from [PlanOperationnelClient]where service= '"+this.nomServiceImputations+"'";
    //req = "insert into [PlanOperationnelClient_simulation] select idWebPo, nomProjet, charge, LF, Annee, etat, Service, chargeConsommee, dateExtraction, codeSujet, Part, TypeCharge, dateConsomme from [PlanOperationnelClient]where service= '"+this.nomServiceImputations+"'";
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
    //myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());

    req = "update [PlanOperationnelClient_simulation] set LF=1";
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
    //myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());

    return "OK";
  }


 public float getTotalChargesActivites(String nomBase,Connexion myCnx, Statement st , int anneeRef){
 ResultSet rs;
 req="SELECT     SUM(PlanDeCharge.Charge) AS totalCharges";
 req+=" FROM         Membre INNER JOIN";
 req+="                      PlanDeCharge ON Membre.idMembre = PlanDeCharge.projetMembre INNER JOIN";
 req+="                      Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap INNER JOIN";
 req+="                      VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
 req+="                      St ON VersionSt.stVersionSt = St.idSt";
 req+=" WHERE     (Membre.serviceMembre = "+this.id+") AND (PlanDeCharge.anneeRef = "+anneeRef+") AND (St.isRecurrent = 1)";


 rs = myCnx.ExecReq(st, nomBase, req);

 float total=0;
 try {
     while (rs.next()) {
                  total = rs.getFloat(1);

              }

       }
                        catch (SQLException s) {s.getMessage();}

 return total;

}

 public float getTotalChargesProjets(String nomBase,Connexion myCnx, Statement st , int anneeRef){
 ResultSet rs;
 req="SELECT     SUM(PlanDeCharge.Charge) AS totalCharges";
 req+=" FROM         Membre INNER JOIN";
 req+="                      PlanDeCharge ON Membre.idMembre = PlanDeCharge.projetMembre INNER JOIN";
 req+="                      Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap INNER JOIN";
 req+="                      VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
 req+="                      St ON VersionSt.stVersionSt = St.idSt";
 req+=" WHERE     (Membre.serviceMembre = "+this.id+") AND (PlanDeCharge.anneeRef = "+anneeRef+") AND (St.isRecurrent <> 1)";


 rs = myCnx.ExecReq(st, nomBase, req);

 float total=0;
 try {
     while (rs.next()) {
                  total = rs.getFloat(1);

              }

       }
                        catch (SQLException s) {s.getMessage();}

 return total;

}

  public int getTotalMembres(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs;
  req="SELECT     COUNT(*) AS nb";
  req+=" FROM         Service INNER JOIN";
  req+="                    Membre ON Service.idService = Membre.serviceMembre";
  req+="  WHERE     (Service.idService = "+this.id+")";



  rs = myCnx.ExecReq(st, nomBase, req);

  int total=0;
  try {
      while (rs.next()) {
                   total = rs.getInt(1);

               }

        }
                         catch (SQLException s) {s.getMessage();}

  return total;

}
private String getClauseWhere(String ClausePeriode){

  if (ClausePeriode.equals("")) return " AND (Panier='checked')";
  String ClauseWhere = "";
  
  String[] ligneClauseWhere = ClausePeriode.split(";");
  for (int i=0;i < ligneClauseWhere.length; i++)
  {
      if (i == 0) ClauseWhere = " AND (";
      ClauseWhere += "(idJalon= "+ligneClauseWhere[i] + ")";
      if ((i != ligneClauseWhere.length -1)) ClauseWhere += " OR ";
  }
  
  ClauseWhere += ")";

  ClauseWhere+=" AND (Panier='checked')";
  return ClauseWhere;

}
private String getClauseWhere(String AD,String T0, String EB,String UAT,String PROD,String MES){

  String ClauseWhere = "";
  //if (T0.equals("0") && !EB.equals("0") && !UAT.equals("0") && !PROD.equals("0")) return "";

  if (AD.equals("1") ||T0.equals("1") || EB.equals("1") || UAT.equals("1") || PROD.equals("1") || MES.equals("1")) ClauseWhere = "AND (";

  if  (AD.equals("1"))
    {

      ClauseWhere += "((Etat = 'a demarrer') )";
    }

  if  (T0.equals("1"))
    {

      if (!ClauseWhere.equals( "AND (")) ClauseWhere +=" OR ";
        ClauseWhere += "(Etat = 'T0')";
    }

    if  (EB.equals("1"))
      {
        if (!ClauseWhere.equals( "AND (")) ClauseWhere +=" OR ";
        ClauseWhere += "(Etat = 'EB')";
    }
    if  (UAT.equals("1"))
      {
        if (!ClauseWhere.equals( "AND (")) ClauseWhere +=" OR ";
        ClauseWhere += "(Etat = 'TEST')";
    }
    if  (PROD.equals("1"))
      {
        if (!ClauseWhere.equals( "AND (")) ClauseWhere +=" OR ";
        ClauseWhere += "(Etat = 'PROD')";
    }   
    if  (MES.equals("1"))
      {
        if (!ClauseWhere.equals( "AND (")) ClauseWhere +=" OR ";
        ClauseWhere += "(Etat = 'MES')";
    }      


    if (AD.equals("1") || T0.equals("1") || EB.equals("1") || UAT.equals("1") || PROD.equals("1") || MES.equals("1")) ClauseWhere += ")";

    ClauseWhere+=" AND (Panier='checked')";
    return ClauseWhere;

}


 public int getListeProjets(String nomBase,Connexion myCnx, Statement st,  int anneeRef,  String str_ListePeriode, String Client, String PeriodeDebut, String PeriodeFin, String MotsClef, String idVersionSt, String idMembre,  String ST_Lies, int monthRef, String Projets, int page, int nbProjets ){
// @@

   ResultSet rs;


   String ClauseMembre = "";
   if ((idMembre != null) && !idMembre.equals("") && !idMembre.equals("-1") )
   {
     ClauseMembre = " and";
     ClauseMembre+=" idVersionSt in";
     ClauseMembre+="    (";
     ClauseMembre+=" SELECT distinct idVersionSt FROM (";
     ClauseMembre+=" SELECT VersionSt.idVersionSt, St.nomSt FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoaVersionSt = Membre.idMembre WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.isActeur <> 1) AND (Membre.idMembre = '"+idMembre+"') AND (St.creerProjet = 1) AND (ST.creerProjet = 1) AND (ST.isRecurrent <> 1) AND     (ST.isMeeting <> 1) ";

     ClauseMembre+=" UNION ";
     ClauseMembre+=" SELECT VersionSt.idVersionSt, St.nomSt FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoeVersionSt = Membre.idMembre WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.isActeur <> 1) AND (Membre.idMembre = '"+idMembre+"') AND (St.creerProjet = 1) AND (ST.creerProjet = 1) AND (ST.isRecurrent <> 1) AND     (ST.isMeeting <> 1) ";

     ClauseMembre+=" UNION ";
     ClauseMembre+=" SELECT ListeST.idVersionSt, ListeST.nomSt FROM Membre INNER JOIN EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ListeST ON Equipe.nom = ListeST.nomSt WHERE (Membre.idMembre = '"+idMembre+"') AND (ListeST.isActeur <> 1) AND (ListeST.creerProjet = 1) AND (ListeST.isRecurrent <> 1) AND     (ListeST.isMeeting <> 1)";
     ClauseMembre+=" ) ";
     ClauseMembre+=" AS maTable ";
     ClauseMembre+=" )";
  }

   String ClauseST="";

   if ((idVersionSt != null) && !idVersionSt.equals("") && !idVersionSt.equals("-1") && !idVersionSt.equals("-1;"))
   {
     ClauseST = " AND (";
     int nb = 0;

     for (StringTokenizer t = new StringTokenizer(idVersionSt, ";");
          t.hasMoreTokens(); ) {
       String theTocken = t.nextToken();
       if (nb == 0)
         ClauseST += "idVersionSt=" + theTocken;
       else
         ClauseST += " OR idVersionSt=" + theTocken;
       nb++;
     }

     ClauseST += ") ";
   }
   
   String ClauseProjets="";

   if (Projets != null && !Projets.equals(""))
   {
     ClauseProjets = " AND (";
     int nb = 0;

     for (StringTokenizer t = new StringTokenizer(Projets, ";");
          t.hasMoreTokens(); ) {
       String theTocken = t.nextToken();
       if (nb == 0)
         ClauseProjets += "idRoadmap =" + theTocken;
       else
         ClauseProjets += " OR idRoadmap =" + theTocken;
       nb++;
     }

     ClauseProjets += ") ";
   }
   

   String ClauseMotsClef = "";
   if (!(MotsClef == null) && !MotsClef.equals("")  )
   {
   ClauseMotsClef =" AND (MotsClef LIKE '%" + MotsClef + "%') ";

  }

   String ClausePeriode = "";
   if (!PeriodeDebut.equals("") && !PeriodeFin.equals("")  )
   {
   ClausePeriode +=" AND ";
   ClausePeriode +=" (";
   ClausePeriode +=" ((ListeProjets.dateMep >= CONVERT(DATETIME, '"+PeriodeDebut+"', 103) AND ListeProjets.dateMep <= CONVERT(DATETIME, '"+PeriodeFin+"', 103)))";
   ClausePeriode +=" )   ";
  }

   String ClauseCouleur = "";
   String couleur = "";


    String ClauseInterface = "";

    if ( (Client != null) && (!Client.equals("-1")))
    {
      ClauseInterface = " and";
      ClauseInterface += "     (";

      ClauseInterface += " idVersionSt in";

      ClauseInterface += " (";

      ClauseInterface += " SELECT     VersionSt_1.idVersionSt";
      ClauseInterface += "     FROM         Interface INNER JOIN";
      ClauseInterface += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterface += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterface += " WHERE     (VersionSt.idVersionSt = "+Client+")";

      ClauseInterface += " UNION";

      ClauseInterface += " SELECT     VersionSt.idVersionSt";
      ClauseInterface += " FROM         Interface INNER JOIN";
      ClauseInterface += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterface += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterface += " WHERE     (VersionSt_1.idVersionSt = "+Client+")";

      ClauseInterface += "     )";
      ClauseInterface += "     )";
    }
    String ClauseInterfaceST = "";

    if ( (ST_Lies != null) && (!ST_Lies.equals("-1")))
    {
      ClauseInterfaceST = " and";
      ClauseInterfaceST += "     (";

      ClauseInterfaceST += " idVersionSt in";

      ClauseInterfaceST += " (";

      ClauseInterfaceST += " SELECT     VersionSt_1.idVersionSt";
      ClauseInterfaceST += "     FROM         Interface INNER JOIN";
      ClauseInterfaceST += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterfaceST += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterfaceST += " WHERE     (VersionSt.idVersionSt = "+ST_Lies+") AND (Interface.typeInterface = 'ST')";

      ClauseInterfaceST += " UNION";

      ClauseInterfaceST += " SELECT     VersionSt.idVersionSt";
      ClauseInterfaceST += " FROM         Interface INNER JOIN";
      ClauseInterfaceST += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterfaceST += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterfaceST += " WHERE     (VersionSt_1.idVersionSt = "+ST_Lies+") AND (Interface.typeInterface = 'ST')";

      ClauseInterfaceST += " UNION";

      ClauseInterfaceST += " SELECT    "+ST_Lies;

      ClauseInterfaceST += "     )";
      ClauseInterfaceST += "     )";
    }

    String ClausePhase=getClauseWhere(str_ListePeriode);

  req = "select * from ( ";
  req+= "  select ListeProjets.nomSt,idRoadmap, nomProjet,idVersionSt,version,dateEB,dateMep,dateMes,dateT0,dateTest,dateT0MOE, nbDependances,  nbActions, dateT0_init, dateEB_init, dateTest_init, dateMep_init, dateMes_init, nomProjet as nomProjetPere, 0 as fils, standby, Panier, idRoadmapPere, serviceMoaVersionSt, serviceMoeVersionSt, etat, MotsClef, dateMep as dateMepPere,  ListeProjets.ordreJalon,  ListeProjets.idJalon  from ListeProjets ";
  req+= " where idRoadmapPere = -1";
  req+= " union";
  req+= " SELECT     ListeProjets.nomSt,ListeProjets.idRoadmap, ListeProjets.nomProjet, ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.dateMes, ListeProjets.dateT0, ";
  req+= "                       ListeProjets.dateTest, ListeProjets.dateT0MOE, ListeProjets.nbDependances, ListeProjets.nbActions, ListeProjets.dateT0_init, ListeProjets.dateEB_init, ListeProjets.dateTest_init, ";
  req+= "                       ListeProjets.dateMep_init, ListeProjets.dateMes_init, ListeProjets_1.nomProjet AS nomProjetPere, 1 as fils, ListeProjets.standby, ListeProjets.Panier, ListeProjets.idRoadmapPere, ListeProjets.serviceMoaVersionSt, ListeProjets.serviceMoeVersionSt, ListeProjets.etat, ListeProjets.MotsClef,  ListeProjets_1.dateMep as dateMepPere, ListeProjets.ordreJalon,  ListeProjets.idJalon";
  req+= " FROM         ListeProjets INNER JOIN";
  req+= "                       ListeProjets AS ListeProjets_1 ON ListeProjets.idRoadmapPere = ListeProjets_1.idRoadmap";
  req+= " WHERE    (ListeProjets.idRoadmapPere > - 1)";

  req+= " )";
  req+= " as myTableGlobale";
 
     req+=" WHERE     ((serviceMoaVersionSt = "+this.id+") OR (serviceMoeVersionSt = "+this.id+"))  ";
     req+=" AND  (";
     req+=" (";
     req+=" (dateMep IS NOT NULL)     ";
     req+=" AND (dateMep > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" AND (dateMep < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" )  ";

     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL)     ";
     req+="   AND (dateT0 > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00', 102) ) ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00', 102) ) ";     
     req+="   )  ";
     
     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL) AND (dateMep IS NOT NULL)    ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+anneeRef+"-1-1 00:00:00', 102) ) ";
     req+="   AND (dateMep >= CONVERT(DATETIME, '"+(anneeRef+1)+"-1-1 00:00:00', 102) ) ";     
     req+="   )) "; 
  
  req+=" AND (dateT0 > CONVERT(DATETIME, '1900-1-1 00:00:00', 102))  ";
  req+=" AND (dateEB > CONVERT(DATETIME, '1900-1-1 00:00:00', 102))";
  req+=" AND (dateTest > CONVERT(DATETIME, '1900-1-1 00:00:00', 102)) ";
  req+=" AND (dateMep > CONVERT(DATETIME, '1900-1-1 00:00:00', 102))    ";
  
     req+=" AND  (";
     req+=" (";
     req+=" (dateMep IS NOT NULL)     ";
     req+=" AND (dateMep > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" AND (dateMep < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" )  ";

     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL)     ";
     req+="   AND (dateT0 > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00', 102) ) ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00', 102) ) ";     
     req+="   )  ";
     
     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL) AND (dateMep IS NOT NULL)    ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+anneeRef+"-1-1 00:00:00', 102) ) ";
     req+="   AND (dateMep >= CONVERT(DATETIME, '"+(anneeRef+1)+"-1-1 00:00:00', 102) ) ";     
     req+="   )) ";       
     
  req+=" AND (standby = 0)";  
  //req+=" AND (idRoadmapPere = -1)";
  
     req+=ClauseMembre;
     req+=ClauseST;
     req+=ClauseProjets;
     req+=ClauseMotsClef;
     req+=ClausePeriode;
     req+=ClauseCouleur;
     req+=ClauseInterface;
     req+=ClausePhase;
     req+=ClauseInterfaceST;
     
    req+=" ORDER BY nomSt, dateMepPere,nomProjetPere, fils, dateMep "; 
  

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  int count = 0;
  
  Date theDate=null;
  int num = 0; //int page, int nbProjets 
  try {
  while(rs.next())
  {
   count++;
   if ((num >= (page -1) * nbProjets) && (num < page * nbProjets))
   {
    Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap") );
     nom = rs.getString("nomProjet");

     theRoadmap.idVersionSt= rs.getString("idVersionSt");
     theRoadmap.version = rs.getString("version");
     theRoadmap.DdateEB = rs.getDate("dateEB");
     theRoadmap.dateEB =  Utils.getDateFrench(theRoadmap.DdateEB);
     theRoadmap.DdateProd = rs.getDate("dateMep");
     theRoadmap.dateProd =  Utils.getDateFrench(theRoadmap.DdateProd);
     theRoadmap.DdateMes = rs.getDate("dateMes");
     theRoadmap.dateMes =  Utils.getDateFrench(theRoadmap.DdateMes);
     theRoadmap.DdateT0 = rs.getDate("dateT0");
     theRoadmap.dateT0 =  Utils.getDateFrench(theRoadmap.DdateT0);
     theRoadmap.DdateTest = rs.getDate("dateTest");
     theRoadmap.dateTest =  Utils.getDateFrench(theRoadmap.DdateTest);
     //idRoadmap= rs.getInt("idRoadmap");
     theDate = rs.getDate("dateT0MOE");
     theRoadmap.dateT0MOE =  Utils.getDateFrench(theDate);

     theRoadmap.dateDevMOE="";
     theRoadmap.dateSpecMOE="";
     theRoadmap.dateLivrMOE="";

     theRoadmap.nomSt = nom;
     
       theRoadmap.nbDependances= rs.getInt("nbDependances");
       theRoadmap.nbActions= rs.getInt("nbActions");
       
       theRoadmap.DdateT0_init = rs.getDate("dateT0_init");
       theRoadmap.dateT0_init = Utils.getDateFrench(theRoadmap.DdateT0_init);
       theRoadmap.DdateEB_init = rs.getDate("dateEB_init");
       theRoadmap.dateEB_init = Utils.getDateFrench(theRoadmap.DdateEB_init);
       theRoadmap.DdateTest_init = rs.getDate("dateTest_init");
       theRoadmap.dateTest_init = Utils.getDateFrench(theRoadmap.DdateTest_init);
       theRoadmap.DdateProd_init = rs.getDate("dateMep_init");
       theRoadmap.dateProd_init = Utils.getDateFrench(theRoadmap.DdateProd_init);
       theRoadmap.DdateMes_init = rs.getDate("dateMes_init");
       theRoadmap.dateMes_init = Utils.getDateFrench(theRoadmap.DdateMes_init);    
       
     if ((theRoadmap.dateT0 == null) || (theRoadmap.dateT0.equals(""))) continue;
     if ((theRoadmap.dateEB == null) || (theRoadmap.dateEB.equals(""))) continue;
     if ((theRoadmap.dateTest == null) || (theRoadmap.dateTest.equals(""))) continue;
        if ((theRoadmap.dateProd == null) || (theRoadmap.dateProd.equals(""))) continue;
        
        theRoadmap.idPere= rs.getInt("idRoadmapPere");
        
        theRoadmap.Etat= rs.getString("Etat");

       PhaseProjet phaseEB0 = new PhaseProjet("EB0", theRoadmap.DdateT0_init, theRoadmap.DdateEB_init);
       theRoadmap.ListePhases.addElement(phaseEB0);
       PhaseProjet phaseEB = new PhaseProjet("EB", theRoadmap.DdateT0, theRoadmap.DdateEB);
       theRoadmap.ListePhases.addElement(phaseEB);
       
       PhaseProjet phaseDEV0 = new PhaseProjet("DEV0", theRoadmap.DdateEB_init, theRoadmap.DdateTest_init);
       theRoadmap.ListePhases.addElement(phaseDEV0);     
       PhaseProjet phaseDEV = new PhaseProjet("DEV", theRoadmap.DdateEB, theRoadmap.DdateTest);
       theRoadmap.ListePhases.addElement(phaseDEV);       
       
       PhaseProjet phaseTEST0 = new PhaseProjet("TEST0", theRoadmap.DdateTest_init, theRoadmap.DdateProd_init);
       theRoadmap.ListePhases.addElement(phaseTEST0);
       PhaseProjet phaseTEST = new PhaseProjet("TEST", theRoadmap.DdateTest, theRoadmap.DdateProd);
       theRoadmap.ListePhases.addElement(phaseTEST);       
       
       PhaseProjet phaseMES0 = new PhaseProjet("MES0", theRoadmap.DdateProd_init, theRoadmap.DdateMes_init);
       theRoadmap.ListePhases.addElement(phaseMES0);
       PhaseProjet phaseMES = new PhaseProjet("MES", theRoadmap.DdateProd, theRoadmap.DdateMes);
       
       theRoadmap.ordreJalon= rs.getInt("ordreJalon");
       theRoadmap.ListePhases.addElement(phaseMES);       
               
        
     this.ListeProjets.addElement(theRoadmap);
   }
   num++;

  }
  }
   catch (SQLException s) { }

    return count;
}
 
 
 public void getListeProjets2(String nomBase,Connexion myCnx, Statement st,  int anneeRef,  String str_ListePeriode, String Client, String PeriodeDebut, String PeriodeFin, String MotsClef, String idVersionSt, String idMembre,  String ST_Lies, int monthRef, String Projets ){
// @@

   ResultSet rs;


   String ClauseMembre = "";
   if ((idMembre != null) && !idMembre.equals("") && !idMembre.equals("-1") )
   {
     ClauseMembre = " and";
     ClauseMembre+=" idVersionSt in";
     ClauseMembre+="    (";
     ClauseMembre+=" SELECT distinct idVersionSt FROM (";
     ClauseMembre+=" SELECT VersionSt.idVersionSt, St.nomSt FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoaVersionSt = Membre.idMembre WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.isActeur <> 1) AND (Membre.idMembre = '"+idMembre+"') AND (St.creerProjet = 1) AND (ST.creerProjet = 1) AND (ST.isRecurrent <> 1) AND     (ST.isMeeting <> 1) ";

     ClauseMembre+=" UNION ";
     ClauseMembre+=" SELECT VersionSt.idVersionSt, St.nomSt FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoeVersionSt = Membre.idMembre WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.isActeur <> 1) AND (Membre.idMembre = '"+idMembre+"') AND (St.creerProjet = 1) AND (ST.creerProjet = 1) AND (ST.isRecurrent <> 1) AND     (ST.isMeeting <> 1) ";

     ClauseMembre+=" UNION ";
     ClauseMembre+=" SELECT ListeST.idVersionSt, ListeST.nomSt FROM Membre INNER JOIN EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ListeST ON Equipe.nom = ListeST.nomSt WHERE (Membre.idMembre = '"+idMembre+"') AND (ListeST.isActeur <> 1) AND (ListeST.creerProjet = 1) AND (ListeST.isRecurrent <> 1) AND     (ListeST.isMeeting <> 1)";
     ClauseMembre+=" ) ";
     ClauseMembre+=" AS maTable ";
     ClauseMembre+=" )";
  }

   String ClauseST="";

   if ((idVersionSt != null) && !idVersionSt.equals("") && !idVersionSt.equals("-1") && !idVersionSt.equals("-1;"))
   {
     ClauseST = " AND (";
     int nb = 0;

     for (StringTokenizer t = new StringTokenizer(idVersionSt, ";");
          t.hasMoreTokens(); ) {
       String theTocken = t.nextToken();
       if (nb == 0)
         ClauseST += "idVersionSt=" + theTocken;
       else
         ClauseST += " OR idVersionSt=" + theTocken;
       nb++;
     }

     ClauseST += ") ";
   }
   
   String ClauseProjets="";

   if (Projets != null && !Projets.equals(""))
   {
     ClauseProjets = " AND (";
     int nb = 0;

     for (StringTokenizer t = new StringTokenizer(Projets, ";");
          t.hasMoreTokens(); ) {
       String theTocken = t.nextToken();
       if (nb == 0)
         ClauseProjets += "idRoadmap =" + theTocken;
       else
         ClauseProjets += " OR idRoadmap =" + theTocken;
       nb++;
     }

     ClauseProjets += ") ";
   }
   

   String ClauseMotsClef = "";
   if (!(MotsClef == null) && !MotsClef.equals("")  )
   {
   ClauseMotsClef =" AND (MotsClef LIKE '%" + MotsClef + "%') ";

  }

   String ClausePeriode = "";
   if (!PeriodeDebut.equals("") && !PeriodeFin.equals("")  )
   {
   ClausePeriode +=" AND ";
   ClausePeriode +=" (";
   ClausePeriode +=" ((ListeProjets.dateMep >= CONVERT(DATETIME, '"+PeriodeDebut+"', 103) AND ListeProjets.dateMep <= CONVERT(DATETIME, '"+PeriodeFin+"', 103)))";
   ClausePeriode +=" )   ";
  }

   String ClauseCouleur = "";
   String couleur = "";


    String ClauseInterface = "";

    if ( (Client != null) && (!Client.equals("-1")))
    {
      ClauseInterface = " and";
      ClauseInterface += "     (";

      ClauseInterface += " idVersionSt in";

      ClauseInterface += " (";

      ClauseInterface += " SELECT     VersionSt_1.idVersionSt";
      ClauseInterface += "     FROM         Interface INNER JOIN";
      ClauseInterface += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterface += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterface += " WHERE     (VersionSt.idVersionSt = "+Client+")";

      ClauseInterface += " UNION";

      ClauseInterface += " SELECT     VersionSt.idVersionSt";
      ClauseInterface += " FROM         Interface INNER JOIN";
      ClauseInterface += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterface += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterface += " WHERE     (VersionSt_1.idVersionSt = "+Client+")";

      ClauseInterface += "     )";
      ClauseInterface += "     )";
    }
    String ClauseInterfaceST = "";

    if ( (ST_Lies != null) && (!ST_Lies.equals("-1")))
    {
      ClauseInterfaceST = " and";
      ClauseInterfaceST += "     (";

      ClauseInterfaceST += " idVersionSt in";

      ClauseInterfaceST += " (";

      ClauseInterfaceST += " SELECT     VersionSt_1.idVersionSt";
      ClauseInterfaceST += "     FROM         Interface INNER JOIN";
      ClauseInterfaceST += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterfaceST += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterfaceST += " WHERE     (VersionSt.idVersionSt = "+ST_Lies+") AND (Interface.typeInterface = 'ST')";

      ClauseInterfaceST += " UNION";

      ClauseInterfaceST += " SELECT     VersionSt.idVersionSt";
      ClauseInterfaceST += " FROM         Interface INNER JOIN";
      ClauseInterfaceST += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterfaceST += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterfaceST += " WHERE     (VersionSt_1.idVersionSt = "+ST_Lies+") AND (Interface.typeInterface = 'ST')";

      ClauseInterfaceST += " UNION";

      ClauseInterfaceST += " SELECT    "+ST_Lies;

      ClauseInterfaceST += "     )";
      ClauseInterfaceST += "     )";
    }

    String ClausePhase=getClauseWhere(str_ListePeriode);

  req = "select * from ( ";
  req+= "  select ListeProjets.nomSt,idRoadmap, nomProjet,idVersionSt,version,dateEB,dateMep,dateMes,dateT0,dateTest,dateT0MOE, nbDependances,  nbActions, dateT0_init, dateEB_init, dateTest_init, dateMep_init, dateMes_init, nomProjet as nomProjetPere, 0 as fils, standby, Panier, idRoadmapPere, serviceMoaVersionSt, serviceMoeVersionSt, etat, MotsClef, dateMep as dateMepPere,  ListeProjets.ordreJalon,  ListeProjets.idJalon  from ListeProjets ";
  req+= " where idRoadmapPere = -1";
  req+= " union";
  req+= " SELECT     ListeProjets.nomSt,ListeProjets.idRoadmap, ListeProjets.nomProjet, ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.dateMes, ListeProjets.dateT0, ";
  req+= "                       ListeProjets.dateTest, ListeProjets.dateT0MOE, ListeProjets.nbDependances, ListeProjets.nbActions, ListeProjets.dateT0_init, ListeProjets.dateEB_init, ListeProjets.dateTest_init, ";
  req+= "                       ListeProjets.dateMep_init, ListeProjets.dateMes_init, ListeProjets_1.nomProjet AS nomProjetPere, 1 as fils, ListeProjets.standby, ListeProjets.Panier, ListeProjets.idRoadmapPere, ListeProjets.serviceMoaVersionSt, ListeProjets.serviceMoeVersionSt, ListeProjets.etat, ListeProjets.MotsClef,  ListeProjets_1.dateMep as dateMepPere, ListeProjets.ordreJalon,  ListeProjets.idJalon";
  req+= " FROM         ListeProjets INNER JOIN";
  req+= "                       ListeProjets AS ListeProjets_1 ON ListeProjets.idRoadmapPere = ListeProjets_1.idRoadmap";
  req+= " WHERE    (ListeProjets.idRoadmapPere > - 1)";

  req+= " )";
  req+= " as myTableGlobale";
 
     req+=" WHERE     ((serviceMoaVersionSt = "+this.id+") OR (serviceMoeVersionSt = "+this.id+"))  ";
     req+=" AND  (";
     req+=" (";
     req+=" (dateMep IS NOT NULL)     ";
     req+=" AND (dateMep > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" AND (dateMep < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" )  ";

     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL)     ";
     req+="   AND (dateT0 > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00', 102) ) ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00', 102) ) ";     
     req+="   )  ";
     
     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL) AND (dateMep IS NOT NULL)    ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+anneeRef+"-1-1 00:00:00', 102) ) ";
     req+="   AND (dateMep >= CONVERT(DATETIME, '"+(anneeRef+1)+"-1-1 00:00:00', 102) ) ";     
     req+="   )) "; 
  
  req+=" AND (dateT0 > CONVERT(DATETIME, '1900-1-1 00:00:00', 102))  ";
  req+=" AND (dateEB > CONVERT(DATETIME, '1900-1-1 00:00:00', 102))";
  req+=" AND (dateTest > CONVERT(DATETIME, '1900-1-1 00:00:00', 102)) ";
  req+=" AND (dateMep > CONVERT(DATETIME, '1900-1-1 00:00:00', 102))    ";
  
     req+=" AND  (";
     req+=" (";
     req+=" (dateMep IS NOT NULL)     ";
     req+=" AND (dateMep > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" AND (dateMep < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" )  ";

     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL)     ";
     req+="   AND (dateT0 > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00', 102) ) ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00', 102) ) ";     
     req+="   )  ";
     
     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL) AND (dateMep IS NOT NULL)    ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+anneeRef+"-1-1 00:00:00', 102) ) ";
     req+="   AND (dateMep >= CONVERT(DATETIME, '"+(anneeRef+1)+"-1-1 00:00:00', 102) ) ";     
     req+="   )) ";       
     
  req+=" AND (standby = 0)";  
  //req+=" AND (idRoadmapPere = -1)";
  
     req+=ClauseMembre;
     req+=ClauseST;
     req+=ClauseProjets;
     req+=ClauseMotsClef;
     req+=ClausePeriode;
     req+=ClauseCouleur;
     req+=ClauseInterface;
     req+=ClausePhase;
     req+=ClauseInterfaceST;
     
    req+=" ORDER BY nomSt, dateMepPere,nomProjetPere, fils, dateMep "; 
  

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  
  Date theDate=null;

  try {
  while(rs.next())
  {
    Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap") );
     nom = rs.getString("nomProjet");

     theRoadmap.idVersionSt= rs.getString("idVersionSt");
     theRoadmap.version = rs.getString("version");
     theRoadmap.DdateEB = rs.getDate("dateEB");
     theRoadmap.dateEB =  Utils.getDateFrench(theRoadmap.DdateEB);
     theRoadmap.DdateProd = rs.getDate("dateMep");
     theRoadmap.dateProd =  Utils.getDateFrench(theRoadmap.DdateProd);
     theRoadmap.DdateMes = rs.getDate("dateMes");
     theRoadmap.dateMes =  Utils.getDateFrench(theRoadmap.DdateMes);
     theRoadmap.DdateT0 = rs.getDate("dateT0");
     theRoadmap.dateT0 =  Utils.getDateFrench(theRoadmap.DdateT0);
     theRoadmap.DdateTest = rs.getDate("dateTest");
     theRoadmap.dateTest =  Utils.getDateFrench(theRoadmap.DdateTest);
     //idRoadmap= rs.getInt("idRoadmap");
     theDate = rs.getDate("dateT0MOE");
     theRoadmap.dateT0MOE =  Utils.getDateFrench(theDate);

     theRoadmap.dateDevMOE="";
     theRoadmap.dateSpecMOE="";
     theRoadmap.dateLivrMOE="";

     theRoadmap.nomSt = nom;
     
       theRoadmap.nbDependances= rs.getInt("nbDependances");
       theRoadmap.nbActions= rs.getInt("nbActions");
       
       theRoadmap.DdateT0_init = rs.getDate("dateT0_init");
       theRoadmap.dateT0_init = Utils.getDateFrench(theRoadmap.DdateT0_init);
       theRoadmap.DdateEB_init = rs.getDate("dateEB_init");
       theRoadmap.dateEB_init = Utils.getDateFrench(theRoadmap.DdateEB_init);
       theRoadmap.DdateTest_init = rs.getDate("dateTest_init");
       theRoadmap.dateTest_init = Utils.getDateFrench(theRoadmap.DdateTest_init);
       theRoadmap.DdateProd_init = rs.getDate("dateMep_init");
       theRoadmap.dateProd_init = Utils.getDateFrench(theRoadmap.DdateProd_init);
       theRoadmap.DdateMes_init = rs.getDate("dateMes_init");
       theRoadmap.dateMes_init = Utils.getDateFrench(theRoadmap.DdateMes_init);    
       
     if ((theRoadmap.dateT0 == null) || (theRoadmap.dateT0.equals(""))) continue;
     if ((theRoadmap.dateEB == null) || (theRoadmap.dateEB.equals(""))) continue;
     if ((theRoadmap.dateTest == null) || (theRoadmap.dateTest.equals(""))) continue;
        if ((theRoadmap.dateProd == null) || (theRoadmap.dateProd.equals(""))) continue;
        
        theRoadmap.idPere= rs.getInt("idRoadmapPere");
        
        theRoadmap.Etat= rs.getString("Etat");

       PhaseProjet phaseEB0 = new PhaseProjet("EB0", theRoadmap.DdateT0_init, theRoadmap.DdateEB_init);
       theRoadmap.ListePhases.addElement(phaseEB0);
       PhaseProjet phaseEB = new PhaseProjet("EB", theRoadmap.DdateT0, theRoadmap.DdateEB);
       theRoadmap.ListePhases.addElement(phaseEB);
       
       PhaseProjet phaseDEV0 = new PhaseProjet("DEV0", theRoadmap.DdateEB_init, theRoadmap.DdateTest_init);
       theRoadmap.ListePhases.addElement(phaseDEV0);     
       PhaseProjet phaseDEV = new PhaseProjet("DEV", theRoadmap.DdateEB, theRoadmap.DdateTest);
       theRoadmap.ListePhases.addElement(phaseDEV);       
       
       PhaseProjet phaseTEST0 = new PhaseProjet("TEST0", theRoadmap.DdateTest_init, theRoadmap.DdateProd_init);
       theRoadmap.ListePhases.addElement(phaseTEST0);
       PhaseProjet phaseTEST = new PhaseProjet("TEST", theRoadmap.DdateTest, theRoadmap.DdateProd);
       theRoadmap.ListePhases.addElement(phaseTEST);       
       
       PhaseProjet phaseMES0 = new PhaseProjet("MES0", theRoadmap.DdateProd_init, theRoadmap.DdateMes_init);
       theRoadmap.ListePhases.addElement(phaseMES0);
       PhaseProjet phaseMES = new PhaseProjet("MES", theRoadmap.DdateProd, theRoadmap.DdateMes);
       
       theRoadmap.ordreJalon= rs.getInt("ordreJalon");
       theRoadmap.ListePhases.addElement(phaseMES);       
               
        
     this.ListeProjets.addElement(theRoadmap);
   }

  }
   catch (SQLException s) { }


}
 

  public ErrorSpecific bd_Enreg(String action, String typeForm, String nomBase,Connexion myCnx, Statement st, String transaction){

    String result="";
    ErrorSpecific myError = new ErrorSpecific();
    if (typeForm.equals("Creation")) { // Cr�ation d'un nouveau ST
      myError=this.bd_Insert( nomBase, myCnx,  st,  transaction);
    }
    else { //MAJ du ST
      if (action.equals("supp"))
          { //l'utilisateur a cliqu� sur le bouton supprimer

           myError=this.bd_Delete(nomBase,myCnx,st,transaction);

          }
            else
            {
              myError=this.bd_Update( nomBase, myCnx,  st,  transaction);
            }
}
    return myError;
  }

  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  ResultSet rs=null;
  String myDesc= "";
  if (this.description != null)
          this.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");

  ErrorSpecific myError = new ErrorSpecific();
  req = "EXEC ACTEUR_InsererService ";
    req+="'"+this.nom+"','";
    req+=myDesc+"','";
    req+=this.direction+"','";
    req+=this.type+"','";
    req+=this.idServicePere+"','";
    req+=this.nomServiceImputations+"','";
    req+=this.CentreCout+"','";
    req+=this.isMoe+"','";
    req+=this.isMoa+"','";
    req+=this.isGouvernance+"','";
    req+=this.isExploitation+"'";


  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
  if (myError.cause == -1) return myError;

      req="SELECT     TOP (1) idService  FROM   Service ORDER BY idService  DESC";
      rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt(1);
        } catch (SQLException s) {s.getMessage();} 
      
   return myError;
}

 public String bd_InsertNewLF(String nomBase,Connexion myCnx, Statement st, String transaction, int Annee, int LF, Date extraction){

   ResultSet rs=null;

   String str_date ="convert(datetime, '"+extraction.getDate()+"/"+(extraction.getMonth() +1)+"/"+(extraction.getYear()+1900)+"', 103)";

   req = "DELETE FROM  PlanOperationnelClient_simulation";
   //req+=" WHERE     (Annee = "+Annee+") AND (LF = "+LF+") AND (Service = '"+this.nomServiceImputations+"') AND (etat = 'NEW')";
   req+=" WHERE     (Annee = "+Annee+") AND (LF = "+LF+") AND (Service = '"+this.nomServiceImputations+"')";

   if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
   //myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());

   for (int i=0; i < this.ListeProjets.size();i++)
   //for (int i=0; i <=0;i++)
       {
         Roadmap theRoadmap = (Roadmap)this.ListeProjets.elementAt(i);
         //if (!theRoadmap.EtatRoadmap.equals("NEW")) continue;
         //theRoadmap.dump();
     // myCnx.trace("------------------"+i,"theRoadmap.nomProjet",""+theRoadmap.nomProjet);
      req="INSERT PlanOperationnelClient_simulation ";
      req+=" (idWebPo,";
      req+=" nomProjet, ";
      req+=" charge, ";
      req+=" chargeConsommee, ";
      req+=" LF, ";
      req+=" Annee,";
      req+=" etat, ";
      req+=" Service, ";
      req+=" descProjet, ";
      req+=" dateExtraction, ";
      req+=" codeSujet, ";
      req+=" Part, ";
      req+=" TypeCharge )";

      req+=" VALUES (";
      req+=theRoadmap.idPO+",";

      if (theRoadmap.nomProjet != null)
      {
        req += "'" +
            theRoadmap.nomProjet.replaceAll("\r", "").replaceAll("\u0092",
            "'").replaceAll("'", "''") + "'";
      }
      else
        req+="''";
      req+=",";

      req+=theRoadmap.Charge+",";
      req+=theRoadmap.chargeConsommee+",";
      req+=LF+",";
      req+=Annee+",";
      req+="'"+theRoadmap.EtatRoadmap+"'"+",";

      req+="'"+this.nomServiceImputations+"'"+",";
      if (theRoadmap.description != null)
      {
        req += "'" +
            theRoadmap.description.replaceAll("\r", "").replaceAll("\u0092",
            "'").replaceAll("'", "''") + "'";
      }
      else
        req+="''";

      req+=",";
      req+=str_date+"";
      req+=",";
      req+="'"+theRoadmap.codeSujet+"'"+",";
      req+="'"+theRoadmap.part+"'"+",";
      req+="'"+theRoadmap.TypeCharge+"'"+"";

      req+=" )";

      //myCnx.trace("1---------- DELETE ----------","req",""+req);
      if (myCnx.ExecUpdate(st, nomBase, req, true, transaction) == -1) { myCnx.trace(nomBase, "*** ERREUR *** req", req);return req;}

    }

    return "OK";
  }

 public static String bd_updateProfil(String nomBase,Connexion myCnx, Statement st, String transaction){

   ResultSet rs=null;

   String req = "DELETE FROM  RoadmapProfil";

   if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
   //myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());

   for (int i=0; i < ListeRoadmapProfil.size();i++)
   //for (int i=0; i <=0;i++)
       {
         RoadmapProfil theRoadmapProfil = (RoadmapProfil)ListeRoadmapProfil.elementAt(i);
         //theRoadmap.dump();
      //myCnx.trace("------------------"+i,"theRoadmap.nomProjet",""+theRoadmap.nomProjet);
      req="INSERT RoadmapProfil ";
      req+=" (";
      req+=" nom, ";
      req+=" phase, ";
      req+=" delais,";
      req+=" charge)";

      req+=" VALUES (";

      req+="'"+theRoadmapProfil.nom+"',";
      req+=theRoadmapProfil.phase+",";
      req+=theRoadmapProfil.delais+",";
      req+=theRoadmapProfil.charge+"";

      
      req+=" )";

      //myCnx.trace("1---------- DELETE ----------","req",""+req);
      if (myCnx.ExecUpdate(st, nomBase, req, true, transaction) == -1) { myCnx.trace(nomBase, "*** ERREUR *** req", req);return req;}

    }

    return "OK";
  }
 
 public String bd_UpdateChiffrageLF(String nomBase,Connexion myCnx, Statement st, String transaction, int Annee, int LF){

   ResultSet rs=null;

   req = "DELETE FROM         ChiffrageLF";
   req+=" WHERE     (Annee = "+Annee+") AND (LF = "+LF+") AND (nomService = '"+this.nomServiceImputations+"') ";

   if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
   //myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());

   for (int i=0; i < this.ListeProjets.size();i++)
   //for (int i=0; i <=0;i++)
       {
         Roadmap theRoadmap = (Roadmap)this.ListeProjets.elementAt(i);
         //theRoadmap.dump();
      //myCnx.trace("------------------"+i,"theRoadmap.nomProjet",""+theRoadmap.nomProjet);
      req="INSERT ChiffrageLF ";
      req+=" (idService,";
      req+=" idPO, ";
      req+=" chargeReestimee, ";
      req+=" Annee,";
      req+=" LF, ";
      req+=" nomService, ";
      req+=" description)";

      req+=" VALUES (";
      req+=" -1"+",";
      req+=theRoadmap.idPO+",";
      req+=theRoadmap.ChargePrevue+",";
      req+=Annee+",";;
      req+=LF+",";
      req+="'"+this.nomServiceImputations+"'"+",";
      if (theRoadmap.description != null)
      {
        req += "'" +
            theRoadmap.description.replaceAll("\r", "").replaceAll("\u0092",
            "'").replaceAll("'", "''") + "'";
      }
      else
        req+="''";
      req+=" )";

      //myCnx.trace("1---------- DELETE ----------","req",""+req);
      if (myCnx.ExecUpdate(st, nomBase, req, true, transaction) == -1) { myCnx.trace(nomBase, "*** ERREUR *** req", req);return req;}

    }

    return "OK";
  }
 public String bd_UpdateChiffrage(String nomBase,Connexion myCnx, Statement st, String transaction, int Annee, int LF){

   ResultSet rs=null;


   //if (true) return;

   req = "DELETE FROM         ChiffragePlanOperationnelClient";
   req+=" WHERE     (Annee = "+Annee+") AND (LF = "+LF+") AND (nomService = '"+this.nomServiceImputations+"') ";

   if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
   //myCnx.trace("1--------------------","this.ListeProjets.size()",""+this.ListeProjets.size());

   for (int i=0; i < this.ListeProjets.size();i++)
       {
         Roadmap theRoadmap = (Roadmap)this.ListeProjets.elementAt(i);
         int pos = 0;
            String str_forfait = ""+theRoadmap.theChiffrage.Forfait;
            pos = str_forfait.indexOf(".");
            if (pos != -1) str_forfait = str_forfait.substring(0,pos);

         //theRoadmap.dump();
        // myCnx.trace("2------------------","theRoadmap.nomProjet",""+theRoadmap.nomProjet);
        // myCnx.trace("3------------------","str_forfait",""+str_forfait);
      req="INSERT ChiffragePlanOperationnelClient ";
      req+=" (idService,";
      req+=" idPO, ";
      req+=" idPOLie, ";

      for (int j=1; j <= 10;j++)
          {
            req += " ST"+(j)+", ";
            req += " charge"+(j)+", ";
          }
      req+=" Annee,";
      req+=" LF, ";
      req+=" nomService, ";
      req+=" description, ";
      req+=" Forfait)";

      req+=" VALUES (";
      req+=" -1"+",";
      req+=theRoadmap.idPO+",";
      req+=theRoadmap.theChiffrage.idPOLie+",";

      for (int j=0; j < 10;j++)
          {
            //myCnx.trace("3------------------","j",""+j);
            if ((theRoadmap.theChiffrage!= null) &&(theRoadmap.theChiffrage.ListeCharges != null) && (j < theRoadmap.theChiffrage.ListeCharges.size()))
            {
              Charge theCharge = (Charge) theRoadmap.theChiffrage.ListeCharges.
                  elementAt(j);
              req += theCharge.idObj + ", ";
              req += theCharge.hj + ", ";
            }
            else
            {
             req += "-1" + ", ";
             req += "0" + ", ";
           }

          }
      req+=Annee+",";;
      req+=LF+",";
      req+="'"+this.nomServiceImputations+"'"+",";
if ((theRoadmap.theChiffrage!= null) && (theRoadmap.theChiffrage.description != null))
      {
      req+="'"+theRoadmap.theChiffrage.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'"+",";
      }
      else
      {
        req+="'',";
      }
      req+="'"+str_forfait+"'";
      req+=" )";

      //myCnx.trace("4---------- ExecUpdate ----------","req",""+req);
      if (myCnx.ExecUpdate(st, nomBase, req, true, transaction) == -1) { myCnx.trace(nomBase, "*** ERREUR *** req", req);return req;}

    }

    return "OK";
  }
 public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){

   ErrorSpecific myError = new ErrorSpecific();

   String theDesc = "";
   if (this.description == null) 
       theDesc ="";
   else
       theDesc = this.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
   
   req = "EXEC [ACTEUR_MajService] ";
     req+="'"+this.id+"',";
     req+="'"+this.nom+"',";
     req+="'"+theDesc+"',";
     req+="'"+this.direction+"',";
     req+="'"+this.type+"',";
     req+="'"+this.idServicePere+"',";
     req+="'"+this.nomServiceImputations+"',";
     req+="'"+this.CentreCout+"',";
     req+="'"+this.sendRetard+"',";
     req+="'"+this.isMoe+"',";
     req+="'"+this.isMoa+"',";
     req+="'"+this.isGouvernance+"',";
     req+="'"+this.isExploitation+"'";

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
    if (myError.cause == -1) return myError;

   ST theST = new ST(this.old_nom);
   theST.getAllFromBd(nomBase, myCnx, st);

   if (theST.idSt != -1)
   {
     req = "UPDATE St SET nomSt ='" + this.nom + "' WHERE idSt =" + theST.idSt;
     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
     if (myError.cause == -1) return myError;

     req = "UPDATE VersionSt SET descVersionSt ='" + theDesc + "' WHERE stVersionSt =" + theST.idSt;
     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
     if (myError.cause == -1) return myError;

     req =  "SELECT     MacroSt.idMacrost";
     req+=" FROM         MacroSt INNER JOIN";
     req+="                       Direction ON MacroSt.nomMacrost = Direction.nomDirection";
      req+=" WHERE     (Direction.idDirection = "+this.direction+")";

     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     int idMacrost = -1;
     try {
       while (rs.next()) {

         idMacrost = rs.getInt("idMacrost");

         }
          } catch (SQLException s) {s.getMessage();}

     req = "UPDATE VersionSt SET macrostVersionSt ='" + idMacrost + "' WHERE stVersionSt =" + theST.idSt;
     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
     if (myError.cause == -1) return myError;
   }

   Equipe theEquipe = new Equipe(this.old_nom);
   theEquipe.getAllFromBd(nomBase, myCnx, st);
   if (theEquipe.id != -1)
   {
     req = "UPDATE Equipe SET nom ='" + this.nom + "' WHERE id =" + theEquipe.id;
     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
     if (myError.cause == -1) return myError;
   }

    return myError;
  }

  public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){

      ErrorSpecific myError = new ErrorSpecific();
      ResultSet rs = null;


      req = "DELETE Service WHERE idService =" + this.id;
      int check = 0;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
      if (myError.cause == -1) return myError;

      req =  "  SELECT  idSt";
      req +="     FROM         St";
      req +="     WHERE     (nomSt = '"+this.nom+"')";

      rs = myCnx.ExecReq(st, nomBase, req);
      int idSt = -1;
      try {
        while (rs.next()) {

          idSt = rs.getInt("idSt");

          }
          } catch (SQLException s) {s.getMessage();}


      req = "DELETE VersionSt WHERE stVersionSt ='" + idSt+"'";
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
      if (myError.cause == -1) return myError;

      req = "DELETE St WHERE nomSt ='" + this.nom+"'";
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
      if (myError.cause == -1) return myError;


   return myError;
}
 // ------------------------- Statistiques Projet: T0 --------------------------------------------------------------//
 public int getNbT0(String nomBase,Connexion myCnx, Statement st, String anneeRef){
   ResultSet rs = null;
   int nb_total=0;
   if (this.isMoe != 1)
   {
   req = "SELECT     COUNT(*) AS nb";
   req+="  FROM       "+this.TableListeProjet;
   req+="  WHERE     (serviceMoaVersionSt = "+this.id+") AND (YEAR(dateT0) = "+anneeRef+")";
   req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
   rs = myCnx.ExecReq(st, nomBase, req);
   try { rs.next();
   nb_total= rs.getInt(1);

       } catch (SQLException s) {s.getMessage();}
   }
   else
   {
   req = "SELECT     COUNT(*) AS nb";
   req+="  FROM       "+this.TableListeProjet;
   req+="  WHERE     (serviceMoeVersionSt = "+this.id+") AND (YEAR(dateT0) = "+anneeRef+")";
   req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
   rs = myCnx.ExecReq(st, nomBase, req);
   try { rs.next();
   nb_total= rs.getInt(1);

       } catch (SQLException s) {s.getMessage();}
   }
   return nb_total;
 }

 public int getNbT0ByMonth(String nomBase,Connexion myCnx, Statement st, String anneeRef, int theMonth){
   ResultSet rs = null;
   int nb=0;

   if (this.isMoe != 1)
   {
   req = "SELECT COUNT(*) AS nb FROM "+this.TableListeProjet+" WHERE (serviceMoaVersionSt = "+this.id+") AND (MONTH(dateT0) = "+theMonth+") AND (YEAR(dateT0) = "+anneeRef+")";
   req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
   }
   else
   {
   req = "SELECT COUNT(*) AS nb FROM "+this.TableListeProjet+" WHERE (serviceMoeVersionSt = "+this.id+") AND (MONTH(dateT0) = "+theMonth+") AND (YEAR(dateT0) = "+anneeRef+")";
   req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
   }

   rs = myCnx.ExecReq(st, nomBase, req);
   try { rs.next();
   nb= rs.getInt(1);

 } catch (SQLException s) {s.getMessage();}

   return nb;
 }

 public int getNbT0_faits(String nomBase,Connexion myCnx, Statement st, String anneeRef,String currenntYear, int currenntMonth){

   ResultSet rs = null;
   int nb_fait=0;
if (this.isMoe != 1)
 {
   if (anneeRef.equals(currenntYear))
       {
         req = "SELECT     COUNT(*) AS nb";
         req +=" FROM         "+this.TableListeProjet;
         req += "  WHERE     (serviceMoaVersionSt = " + this.id +
             ") AND (YEAR(dateT0) = " + anneeRef + ") AND (MONTH(dateT0) < " +
             currenntMonth + ")";
         req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
         rs = myCnx.ExecReq(st, nomBase, req);
         try {
           rs.next();
           nb_fait = rs.getInt(1);

         }
         catch (SQLException s) {
           s.getMessage();
         }
       }
       else if (Integer.parseInt(anneeRef) < Integer.parseInt(currenntYear))
       {
         req = "SELECT     COUNT(*) AS nb";
         req+="  FROM       "+this.TableListeProjet;
         req+="  WHERE     (serviceMoaVersionSt = "+this.id+") AND (YEAR(dateT0) = "+anneeRef+")";
         rs = myCnx.ExecReq(st, nomBase, req);
         try { rs.next();
         nb_fait= rs.getInt(1);

       } catch (SQLException s) {s.getMessage();}
       }
       else nb_fait = 0;
 }
 else
 {
   if (anneeRef.equals(currenntYear))
       {
         req = "SELECT     COUNT(*) AS nb";
         req +=" FROM         "+this.TableListeProjet;
         req += "  WHERE     (serviceMoeVersionSt = " + this.id +
             ") AND (YEAR(dateT0) = " + anneeRef + ") AND (MONTH(dateT0) < " +
             currenntMonth + ")";
         req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
         rs = myCnx.ExecReq(st, nomBase, req);
         try {
           rs.next();
           nb_fait = rs.getInt(1);

         }
         catch (SQLException s) {
           s.getMessage();
         }
       }
       else if (Integer.parseInt(anneeRef) < Integer.parseInt(currenntYear))
       {
         req = "SELECT     COUNT(*) AS nb";
         req+="  FROM       "+this.TableListeProjet;
         req+="  WHERE     (serviceMoeVersionSt = "+this.id+") AND (YEAR(dateT0) = "+anneeRef+")";
         rs = myCnx.ExecReq(st, nomBase, req);
         try { rs.next();
         nb_fait= rs.getInt(1);

       } catch (SQLException s) {s.getMessage();}
       }
       else nb_fait = 0;
 }

  return nb_fait;
 }

 public int getNbT0_encours(String nomBase,Connexion myCnx, Statement st, String anneeRef,String currenntYear, int currenntMonth){
   ResultSet rs = null;
   int nb_encours=0;

   if (this.isMoe != 1)
 {
   if (anneeRef.equals(currenntYear))
       {

         req = "SELECT     COUNT(*) AS nb";
         req +=" FROM         "+this.TableListeProjet;
         req += "  WHERE     (serviceMoaVersionSt = " + this.id +
             ") AND (YEAR(dateT0) = " + anneeRef + ") AND (MONTH(dateT0) = " +
             currenntMonth + ")";
         req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
         rs = myCnx.ExecReq(st, nomBase, req);
         try {
           rs.next();
           nb_encours = rs.getInt(1);

         }
         catch (SQLException s) {
           s.getMessage();
         }
       }
       else nb_encours = 0;
 }
 else
 {
 if (anneeRef.equals(currenntYear))
     {

       req = "SELECT     COUNT(*) AS nb";
       req +=" FROM         "+this.TableListeProjet;
       req += "  WHERE     (serviceMoeVersionSt = " + this.id +
           ") AND (YEAR(dateT0) = " + anneeRef + ") AND (MONTH(dateT0) = " +
           currenntMonth + ")";
       req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
       rs = myCnx.ExecReq(st, nomBase, req);
       try {
         rs.next();
         nb_encours = rs.getInt(1);

       }
       catch (SQLException s) {
         s.getMessage();
       }
     }
       else nb_encours = 0;
 }
   return nb_encours;
 }

 public int getNbT0_afaire(String nomBase,Connexion myCnx, Statement st, String anneeRef,String currenntYear, int currenntMonth){
   ResultSet rs = null;
   int nb_afaire = 0;

if (this.isMoe != 1)
 {
   if (anneeRef.equals(currenntYear))
       {
         req = "SELECT     COUNT(*) AS nb";
         req +=" FROM         "+this.TableListeProjet;
         req += "  WHERE     (serviceMoaVersionSt = " + this.id +
             ") AND (YEAR(dateT0) = " + anneeRef + ") AND (MONTH(dateT0) > " +
             currenntMonth + ")";
         req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
         rs = myCnx.ExecReq(st, nomBase, req);
         try {
           rs.next();
           nb_afaire = rs.getInt(1);

         }
         catch (SQLException s) {
           s.getMessage();
         }
       }
       else if (Integer.parseInt(anneeRef) > Integer.parseInt(currenntYear))
       {
         req = "SELECT     COUNT(*) AS nb";
         req+="  FROM       "+this.TableListeProjet;
         req+="  WHERE     (serviceMoaVersionSt = "+this.id+") AND (YEAR(dateT0) = "+anneeRef+")";
         rs = myCnx.ExecReq(st, nomBase, req);
         try { rs.next();
         nb_afaire= rs.getInt(1);

       } catch (SQLException s) {s.getMessage();}
       }
       else
        nb_afaire = 0;
 }
 else
 {
   if (anneeRef.equals(currenntYear))
       {
         req = "SELECT     COUNT(*) AS nb";
         req +=" FROM         "+this.TableListeProjet;
         req += "  WHERE     (serviceMoeVersionSt = " + this.id +
             ") AND (YEAR(dateT0) = " + anneeRef + ") AND (MONTH(dateT0) > " +
             currenntMonth + ")";
         req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
         rs = myCnx.ExecReq(st, nomBase, req);
         try {
           rs.next();
           nb_afaire = rs.getInt(1);

         }
         catch (SQLException s) {
           s.getMessage();
         }
       }
       else if (Integer.parseInt(anneeRef) > Integer.parseInt(currenntYear))
       {
         req = "SELECT     COUNT(*) AS nb";
         req+="  FROM       "+this.TableListeProjet;
         req+="  WHERE     (serviceMoeVersionSt = "+this.id+") AND (YEAR(dateT0) = "+anneeRef+")";
         rs = myCnx.ExecReq(st, nomBase, req);
         try { rs.next();
         nb_afaire= rs.getInt(1);

       } catch (SQLException s) {s.getMessage();}
       }
       else
        nb_afaire = 0;

 }
   return nb_afaire;
 }
// -------------------------------------------------------------------------------------------------------------------//
// ------------------------- Statistiques Projet: EB --------------------------------------------------------------//
public int getNbEB(String nomBase,Connexion myCnx, Statement st, String anneeRef){
  ResultSet rs = null;
  int nb_total=0;
  if (this.isMoe != 1)
   {
  req = "SELECT     COUNT(*) AS nb";
  req+="  FROM       "+this.TableListeProjet;
  req+="  WHERE     (serviceMoaVersionSt = "+this.id+") AND (YEAR(dateEB) = "+anneeRef+")";
  req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
  rs = myCnx.ExecReq(st, nomBase, req);
  try { rs.next();
  nb_total= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
   }
   else
   {
  req = "SELECT     COUNT(*) AS nb";
  req+="  FROM       "+this.TableListeProjet;
  req+="  WHERE     (serviceMoeVersionSt = "+this.id+") AND (YEAR(dateEB) = "+anneeRef+")";
  req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
  rs = myCnx.ExecReq(st, nomBase, req);
  try { rs.next();
  nb_total= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
   }
  return nb_total;
}

public int getNbEBByMonth(String nomBase,Connexion myCnx, Statement st, String anneeRef, int theMonth){
  ResultSet rs = null;
  int nb=0;

  if (this.isMoe != 1)
  {
   req = "SELECT COUNT(*) AS nb FROM "+this.TableListeProjet+" WHERE (serviceMoaVersionSt = "+this.id+") AND (MONTH(dateEB) = "+theMonth+") AND (YEAR(dateEB) = "+anneeRef+")";
   req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
  }
  else
  {
    req = "SELECT COUNT(*) AS nb FROM "+this.TableListeProjet+" WHERE (serviceMoeVersionSt = "+this.id+") AND (MONTH(dateEB) = "+theMonth+") AND (YEAR(dateEB) = "+anneeRef+")";
    req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
   }

  rs = myCnx.ExecReq(st, nomBase, req);
  try { rs.next();
  nb= rs.getInt(1);

} catch (SQLException s) {s.getMessage();}

  return nb;
}

public int getNbEB_faits(String nomBase,Connexion myCnx, Statement st, String anneeRef,String currenntYear, int currenntMonth){

  ResultSet rs = null;
  int nb_fait=0;

  if (this.isMoe != 1)
 {
  if (anneeRef.equals(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoaVersionSt = " + this.id +
            ") AND (YEAR(dateEB) = " + anneeRef + ") AND (MONTH(dateEB) < " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_fait = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else if (Integer.parseInt(anneeRef) < Integer.parseInt(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req+="  FROM       "+this.TableListeProjet;
        req+="  WHERE     (serviceMoaVersionSt = "+this.id+") AND (YEAR(dateEB) = "+anneeRef+")";
        rs = myCnx.ExecReq(st, nomBase, req);
        try { rs.next();
        nb_fait= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
      }
      else nb_fait = 0;
 }
 else
 {
  if (anneeRef.equals(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoeVersionSt = " + this.id +
            ") AND (YEAR(dateEB) = " + anneeRef + ") AND (MONTH(dateEB) < " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_fait = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else if (Integer.parseInt(anneeRef) < Integer.parseInt(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req+="  FROM       "+this.TableListeProjet;
        req+="  WHERE     (serviceMoeVersionSt = "+this.id+") AND (YEAR(dateEB) = "+anneeRef+")";
        rs = myCnx.ExecReq(st, nomBase, req);
        try { rs.next();
        nb_fait= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
      }
      else nb_fait = 0;
 }
  return nb_fait;
}

public int getNbEB_encours(String nomBase,Connexion myCnx, Statement st, String anneeRef,String currenntYear, int currenntMonth){
  ResultSet rs = null;
  int nb_encours=0;
  if (this.isMoe != 1)
 {
  if (anneeRef.equals(currenntYear))
      {

        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoaVersionSt = " + this.id +
            ") AND (YEAR(dateEB) = " + anneeRef + ") AND (MONTH(dateEB) = " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_encours = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else nb_encours = 0;
 }
else
 {
  if (anneeRef.equals(currenntYear))
      {

        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoeVersionSt = " + this.id +
            ") AND (YEAR(dateEB) = " + anneeRef + ") AND (MONTH(dateEB) = " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_encours = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else nb_encours = 0;
 }
  return nb_encours;
}

public int getnb_retard(String nomBase,Connexion myCnx, Statement st, String nomJalon, int ordre, int typologie1, int typologie2){

  String req = "";
  
  req="select distinct COUNT(idRoadmap) as nb from";
  req += " (";
  req += " SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet, Roadmap."+nomJalon+", Roadmap.LF_Year, Service.nomService, St.isMeeting, Roadmap.LF_Month, Roadmap.standby, St.isRecurrent, Roadmap.typologie, typeJalon.ordre";
  req += " FROM         Roadmap INNER JOIN";
  req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
  req += "                       Service ON VersionSt.serviceMoaVersionSt = Service.idService INNER JOIN";
  req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
  req += "                       typeJalon ON Roadmap.idJalon = typeJalon.id";
  req += " union";
  req += " SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet, Roadmap."+nomJalon+", Roadmap.LF_Year, Service.nomService, St.isMeeting, Roadmap.LF_Month, Roadmap.standby, St.isRecurrent, Roadmap.typologie, typeJalon.ordre";
  req += " FROM         Roadmap INNER JOIN";
  req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
  req += "                       Service ON VersionSt.serviceMoeVersionSt = Service.idService INNER JOIN";
  req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
  req += "                       typeJalon ON Roadmap.idJalon = typeJalon.id";
  req += " )  ";
  req += " as mytable ";
  req += " WHERE     ("+nomJalon+" < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)) AND ("+nomJalon+" > CONVERT(DATETIME, '1995-01-01 00:00:00', 102)) ";
  req += "  AND (nomService = '"+this.nom+"') AND (isMeeting <> 1) AND (LF_Month = - 1) ";
  req += "  AND  (LF_Year = - 1) AND (standby = 0) AND (isRecurrent <> 1) AND ((typologie = 0)OR (typologie = "+typologie1+") OR (typologie = "+typologie2+"))";
  req += "  AND (ordre < "+ordre+")";
  req += "  AND (idRoadmap not in (select idRoadmapPere from Roadmap))";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  int nb = 0;

 try {
    while (rs.next()) {
      nb = rs.getInt("nb");
    }
     } catch (SQLException s) {s.getMessage();}


 return nb;
}

public int getnb_retardT0(String nomBase,Connexion myCnx, Statement st){

  String req = "";
  req = "exec SERVICE_nb_retardT0 '"+this.nom+"'";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  int nb = 0;

 try {
    while (rs.next()) {
      String nomSt = rs.getString(2);
      nb++;
    }
     } catch (SQLException s) {s.getMessage();}


 return nb;
}


 public int getnb_Alertes(String nomBase,Connexion myCnx, Statement st){

   String req = "exec SERVICE_nbAlertes '"+this.nom+"'";

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   int nb = 0;

  try {
     while (rs.next()) {
       String nomSt = rs.getString(2);
       nb++;
     }
      } catch (SQLException s) {s.getMessage();}


  return nb;
}

 public int getnb_retardSpecMOE(String nomBase,Connexion myCnx, Statement st){

   String req = "exec SERVICE_nb_retardSpecMOE '"+this.nom+"'";
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   int nb = 0;

  try {
     while (rs.next()) {
       String nomSt = rs.getString(2);
       nb++;
     }
      } catch (SQLException s) {s.getMessage();}


  return nb;
}

public int getnb_retardDevMOE(String nomBase,Connexion myCnx, Statement st){

  String req = "exec SERVICE_nb_retardDevMOE '"+this.nom+"'";
  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  int nb = 0;

 try {
    while (rs.next()) {
      String nomSt = rs.getString(2);
      nb++;
    }
     } catch (SQLException s) {s.getMessage();}


 return nb;
}

  public int getnb_retardTestMOE(String nomBase,Connexion myCnx, Statement st){

    String req = "exec SERVICE_nb_retardTestMOE '"+this.nom+"'";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    int nb = 0;

   try {
      while (rs.next()) {
        String nomSt = rs.getString(2);
        nb++;
      }
       } catch (SQLException s) {s.getMessage();}


   return nb;
}

 public int getnb_retardLivrMOE(String nomBase,Connexion myCnx, Statement st){

   String req = "exec SERVICE_nb_retardLivrMOE '"+this.nom+"'";
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   int nb = 0;

  try {
     while (rs.next()) {
       String nomSt = rs.getString(2);
       nb++;
     }
      } catch (SQLException s) {s.getMessage();}


  return nb;
}

  public int getnb_retardEB(String nomBase,Connexion myCnx, Statement st){

    String req = "";
    req = "exec SERVICE_nb_retardEB '"+this.nom+"'";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    int nb = 0;

   try {
      while (rs.next()) {
        String nomSt = rs.getString(2);
        nb++;
      }
       } catch (SQLException s) {s.getMessage();}


   return nb;
}

 public int getListeProjets_sansCharges(String nomBase,Connexion myCnx, Statement st){

   Calendar calendar = Calendar.getInstance();
   int currentYear = calendar.get(Calendar.YEAR);

   String req = "";
   req+="SELECT     Roadmap.idRoadmap, St.nomSt, Roadmap.version, Roadmap.etat";
   req+="    FROM         Roadmap INNER JOIN";
   req+="                    VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   if (this.isMoe != 1)
   {
    req+="                    Service ON VersionSt.serviceMoaVersionSt = Service.idService INNER JOIN";
   }
   else
   {
    req+="                    Service ON VersionSt.serviceMoeVersionSt = Service.idService INNER JOIN";
  }
   req+="                    St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req+="                    SI ON VersionSt.siVersionSt = SI.idSI";
   req+=" WHERE     (Roadmap.Etat <> 'MES') AND (Roadmap.Etat <> 'a demarrer')AND (Roadmap.standby <> 1) AND (Roadmap.Etat <> 'PROD')";
   req+=" AND (Service.nomService = '"+this.nom+"') AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND ";
   req+="                    (St.isMeeting <> 1) AND (St.isRecurrent <> 1) AND (YEAR(Roadmap.dateMep) >= "+currentYear+") AND (YEAR(Roadmap.dateT0) >= "+currentYear+") ";
   req+="                    AND (YEAR(Roadmap.dateEB) >= "+currentYear+") AND (YEAR(Roadmap.dateTest) >= "+currentYear+")";
   req+=" AND          Roadmap.idRoadmap NOT IN ";
   req+=" (";
   req+=" SELECT DISTINCT PlanDeCharge.idRoadmap";
   req+=" FROM         Membre INNER JOIN";
   req+="                    Service ON Membre.serviceMembre = Service.idService INNER JOIN";
   req+="                   PlanDeCharge ON Membre.idMembre = PlanDeCharge.projetMembre";
   req+="  WHERE     (Service.nomService = '"+this.nom+"') AND (PlanDeCharge.anneeRef = "+currentYear+")";
   req+=" )  ";


   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   int nb = 0;

  try {
     while (rs.next()) {
       int idRoadmap = rs.getInt("idRoadmap");
       String nomSt = rs.getString("nomSt");
       String version = rs.getString("version");
       Roadmap theRoadmap = new Roadmap(idRoadmap);
       theRoadmap.nomProjet = nomSt+"-"+version;
       this.ListeProjets.addElement(theRoadmap);
       nb++;
     }
      } catch (SQLException s) {s.getMessage();}


  return nb;
}

public int getListeProjets_sansIdPO(String nomBase,Connexion myCnx, Statement st){

  Calendar calendar = Calendar.getInstance();
  int currentYear = calendar.get(Calendar.YEAR);

  String req = "";
    req+="SELECT DISTINCT * FROM";
    req+=" (";
    req+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS projet";
    req+="   FROM         Roadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";

    req+="                       Service ON VersionSt.serviceMoeVersionSt = Service.idService INNER JOIN";

    req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                       SI ON VersionSt.siVersionSt = SI.idSI";
    req+="    WHERE     (Roadmap.Etat <> 'MES') AND (Service.nomService = '"+this.nom+"')  AND (Roadmap.standby <> 1) AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    req+="                      (Roadmap.LF_Year = - 1) AND (Roadmap.idPO <= 0 OR";
    req+="                       Roadmap.idPO IS NULL) AND (St.isMeeting <> 1) AND (St.isRecurrent <> 1)";
    req+="    AND (YEAR(Roadmap.dateMep) >= "+currentYear+") AND  (YEAR(Roadmap.dateT0) >= "+currentYear+") AND (YEAR(Roadmap.dateEB) >= "+currentYear+") AND (YEAR(Roadmap.dateTest) >= "+currentYear+")";
    req+="    AND (Roadmap.standby <> 1)                      ";

    req+=" UNION ";
    req+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS projet";
    req+="    FROM         Roadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";

    req+="                        Service ON VersionSt.serviceMoaVersionSt = Service.idService INNER JOIN";

    req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                       SI ON VersionSt.siVersionSt = SI.idSI";
    req+="    WHERE     (Roadmap.Etat <> 'MES') AND (Service.nomService = '"+this.nom+"')  AND (Roadmap.standby <> 1) AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    req+="                       (Roadmap.LF_Year = - 1) AND (Roadmap.idPO <= 0 OR";
    req+="                      Roadmap.idPO IS NULL) AND (St.isMeeting <> 1) AND (St.isRecurrent <> 1)";
    req+="    AND (YEAR(Roadmap.dateMep) >= "+currentYear+") AND  (YEAR(Roadmap.dateT0) >= "+currentYear+") AND (YEAR(Roadmap.dateEB) >= "+currentYear+") AND (YEAR(Roadmap.dateTest) >= "+currentYear+")";
    req+="    AND (Roadmap.standby <> 1)                      ";

    req+=" UNION ";
    
    req+=" SELECT     Surconsommation.idRoadmap, St.nomSt + '-' + Surconsommation.version AS projet";
    req+=" FROM         Surconsommation INNER JOIN";
    req+="                       Roadmap ON Surconsommation.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                       Service ON VersionSt.serviceMoeVersionSt = Service.idService";
    req+=" WHERE     (Service.idService = "+this.id+")";

    req+=" UNION";
                      
    req+=" SELECT     Surconsommation.idRoadmap, St.nomSt + '-' + Surconsommation.version AS projet";
    req+=" FROM         Surconsommation INNER JOIN";
    req+="                       Roadmap ON Surconsommation.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                       Service ON VersionSt.serviceMoaVersionSt = Service.idService";
    req+=" WHERE     (Service.idService = "+this.id+")    ";

    req+="   ) as myTAble";
    req+= " ORDER BY projet";


  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  int nb = 0;

 try {
    while (rs.next()) {
      int idRoadmap = rs.getInt("idRoadmap");
      String projet = rs.getString("projet");
      Roadmap theRoadmap = new Roadmap(idRoadmap);
      theRoadmap.nomProjet = projet;
      this.ListeProjets.addElement(theRoadmap);
      nb++;
    }
     } catch (SQLException s) {s.getMessage();}


 return nb;
}

public int getListeProjets_sansIdPO2(String nomBase,Connexion myCnx, Statement st){

  Calendar calendar = Calendar.getInstance();
  int currentYear = calendar.get(Calendar.YEAR);

  String req = "";
    req+="SELECT DISTINCT * FROM";
    req+=" (";
    req+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS projet";
    req+="   FROM         Roadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";

    req+="                       Service ON VersionSt.serviceMoeVersionSt = Service.idService INNER JOIN";

    req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                       SI ON VersionSt.siVersionSt = SI.idSI";
    req+="    WHERE     (Roadmap.Etat <> 'MES') AND (Service.nomService = '"+this.nom+"')  AND (Roadmap.standby <> 1) AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    req+="                      (Roadmap.LF_Year = - 1) AND (Roadmap.idPO <= 0 OR";
    req+="                       Roadmap.idPO IS NULL) AND (St.isMeeting <> 1) AND (St.isRecurrent <> 1)";
    req+="    AND (YEAR(Roadmap.dateMep) >= "+currentYear+") AND  (YEAR(Roadmap.dateT0) >= "+currentYear+") AND (YEAR(Roadmap.dateEB) >= "+currentYear+") AND (YEAR(Roadmap.dateTest) >= "+currentYear+")";
    req+="    AND (Roadmap.standby <> 1)                      ";

    req+=" UNION ";
    req+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS projet";
    req+="    FROM         Roadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";

    req+="                        Service ON VersionSt.serviceMoaVersionSt = Service.idService INNER JOIN";

    req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                       SI ON VersionSt.siVersionSt = SI.idSI";
    req+="    WHERE     (Roadmap.Etat <> 'MES') AND (Service.nomService = '"+this.nom+"')  AND (Roadmap.standby <> 1) AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    req+="                       (Roadmap.LF_Year = - 1) AND (Roadmap.idPO <= 0 OR";
    req+="                      Roadmap.idPO IS NULL) AND (St.isMeeting <> 1) AND (St.isRecurrent <> 1)";
    req+="    AND (YEAR(Roadmap.dateMep) >= "+currentYear+") AND  (YEAR(Roadmap.dateT0) >= "+currentYear+") AND (YEAR(Roadmap.dateEB) >= "+currentYear+") AND (YEAR(Roadmap.dateTest) >= "+currentYear+")";
    req+="    AND (Roadmap.standby <> 1)                      ";

    req+="   ) as myTAble";
    req+= " ORDER BY projet";


  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  int nb = 0;

 try {
    while (rs.next()) {
      int idRoadmap = rs.getInt("idRoadmap");
      String projet = rs.getString("projet");
      Roadmap theRoadmap = new Roadmap(idRoadmap);
      theRoadmap.nomProjet = projet;
      this.ListeProjets.addElement(theRoadmap);
      nb++;
    }
     } catch (SQLException s) {s.getMessage();}


 return nb;
}
public int getnb_EBnonRattachees(String nomBase,Connexion myCnx, Statement st){

  String req = "exec SERVICE_nb_EBnonRattachees '"+this.id+"'";
  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  int nb = 0;

 try {
    while (rs.next()) {
      String nomSt = rs.getString(2);
      nb++;
    }
     } catch (SQLException s) {s.getMessage();}


 return nb;
}



 public int getnb_retardDEVIS(String nomBase,Connexion myCnx, Statement st){


   String req = "exec SERVICE_nb_retardDEVIS '"+this.id+"'";
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   int nb = 0;

  try {
     while (rs.next()) {
       String nomSt = rs.getString(2);
       nb++;
     }
      } catch (SQLException s) {s.getMessage();}


  return nb;
}

public int getnb_retardCahierTEST(String nomBase,Connexion myCnx, Statement st){

  String req = "exec [SERVICE_nb_retardCahierTEST] '"+this.id+"'";
  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  int nb = 0;

 try {
    while (rs.next()) {
      String nomSt = rs.getString(2);
      nb++;
    }
     } catch (SQLException s) {s.getMessage();}


 return nb;
}

 public int getnb_retardTEST(String nomBase,Connexion myCnx, Statement st){

   String req = "";
   req = "exec SERVICE_nb_retardTEST '"+this.nom+"'";

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   int nb = 0;

  try {
     while (rs.next()) {
       String nomSt = rs.getString(2);
       nb++;
     }
      } catch (SQLException s) {s.getMessage();}


  return nb;
}

public int getnb_retardPROD(String nomBase,Connexion myCnx, Statement st){

  String req = "";
  req = "exec SERVICE_nb_retardPROD '"+this.nom+"'";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  int nb = 0;

 try {
    while (rs.next()) {
      String nomSt = rs.getString(2);
      nb++;
    }
     } catch (SQLException s) {s.getMessage();}


 return nb;
}

  public int getnb_retardMES(String nomBase,Connexion myCnx, Statement st){

    String req = "";
    req = "exec SERVICE_nb_retardMES '"+this.nom+"'";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    int nb = 0;

   try {
      while (rs.next()) {
        String nomSt = rs.getString(2);
        nb++;
      }
       } catch (SQLException s) {s.getMessage();}


   return nb;
}

public int getNbEB_afaire(String nomBase,Connexion myCnx, Statement st, String anneeRef,String currenntYear, int currenntMonth){
  ResultSet rs = null;
  int nb_afaire=0;
  if (this.isMoe != 1)
 {
  if (anneeRef.equals(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoaVersionSt = " + this.id +
            ") AND (YEAR(dateEB) = " + anneeRef + ") AND (MONTH(dateEB) > " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_afaire = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else if (Integer.parseInt(anneeRef) > Integer.parseInt(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req+="  FROM       "+this.TableListeProjet;
        req+="  WHERE     (serviceMoaVersionSt = "+this.id+") AND (YEAR(dateEB) = "+anneeRef+")";
        rs = myCnx.ExecReq(st, nomBase, req);
        try { rs.next();
        nb_afaire= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
      }
      else
        nb_afaire = 0;
 }
 else
 {
  if (anneeRef.equals(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoeVersionSt = " + this.id +
            ") AND (YEAR(dateEB) = " + anneeRef + ") AND (MONTH(dateEB) > " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_afaire = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else if (Integer.parseInt(anneeRef) > Integer.parseInt(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req+="  FROM       "+this.TableListeProjet;
        req+="  WHERE     (serviceMoeVersionSt = "+this.id+") AND (YEAR(dateEB) = "+anneeRef+")";
        rs = myCnx.ExecReq(st, nomBase, req);
        try { rs.next();
        nb_afaire= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
      }
      else
        nb_afaire = 0;
 }
  return nb_afaire;
}
// -------------------------------------------------------------------------------------------------------------------//
// ------------------------- Statistiques Projet: Test --------------------------------------------------------------//
public int getNbTest(String nomBase,Connexion myCnx, Statement st, String anneeRef){
  ResultSet rs = null;
  int nb_total=0;
  if (this.isMoe != 1)
   {
  req = "SELECT     COUNT(*) AS nb";
  req+="  FROM       "+this.TableListeProjet;
  req+="  WHERE     (serviceMoaVersionSt = "+this.id+") AND (YEAR(dateTest) = "+anneeRef+")";
  req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
  rs = myCnx.ExecReq(st, nomBase, req);
  try { rs.next();
  nb_total= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
   }
   else
   {
  req = "SELECT     COUNT(*) AS nb";
  req+="  FROM       "+this.TableListeProjet;
  req+="  WHERE     (serviceMoeVersionSt = "+this.id+") AND (YEAR(dateTest) = "+anneeRef+")";
  req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
  rs = myCnx.ExecReq(st, nomBase, req);
  try { rs.next();
  nb_total= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
   }
  return nb_total;
}

public int getStByColorByMOA(String nomBase,Connexion myCnx, Statement st, int couleur){
int nb=0;
  req = "SELECT     COUNT(*) AS nb";
  req+="    FROM         ListeST";
  req+=" WHERE     (etatVersionSt = 3) AND (serviceMoaVersionSt = "+this.id+") AND (typeSiVersionSt = "+couleur+")";


   ResultSet rs = myCnx.ExecReq(st, nomBase, req);

   try {
      while (rs.next()) {
        nb = rs.getInt("nb");
      }
    } catch (SQLException s) {s.getMessage();}

   return nb;
}

public int getSiBleuMOA(String nomBase,Connexion myCnx, Statement st, String nomSI){
  this.nbStBleu=0;
  this.ListeStBleu.removeAllElements();
  req = "SELECT     ListeST.idSt, ListeST.nomSt, ListeST.idVersionSt";
  req+= "   FROM         ListeST INNER JOIN";
  req+= "                  SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
  req+= "                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
  req+= " WHERE     (TypeSi.nomTypeSi = 'SI Bleu') AND (ListeST.etatVersionSt = 3) AND (ListeST.serviceMoaVersionSt  = "+this.id+") AND (SI.nomSI = '"+nomSI+"') order by ListeST.nomSt asc";


   ResultSet rs = myCnx.ExecReq(st, nomBase, req);

   try {
      while (rs.next()) {
        int idSt = rs.getInt("idSt");
        String nomSt = rs.getString("nomSt");
        int idVersionSt = rs.getInt("idVersionSt");
        if (nomSt != null)
        {
          ST theST = new ST(nomSt);
          theST.idVersionSt = idVersionSt;
          theST.idSt = idSt;
          this.ListeSt.addElement(theST);
          this.ListeStBleu.addElement(theST);
        }

     this.nbStBleu++;
      }
    } catch (SQLException s) {s.getMessage();}

   return this.nbStBleu;
}

 public int getSiVioletMOA(String nomBase,Connexion myCnx, Statement st, String nomSI){
   this.nbStViolet=0;
   this.ListeStViolet.removeAllElements();
   req = "SELECT     ListeST.idSt, ListeST.nomSt, ListeST.idVersionSt";
   req+= "   FROM         ListeST INNER JOIN";
   req+= "                  SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
   req+= "                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
   req+= " WHERE     (TypeSi.nomTypeSi = 'SI Violet') AND (ListeST.etatVersionSt = 3) AND (ListeST.serviceMoaVersionSt  = "+this.id+") AND (SI.nomSI = '"+nomSI+"') order by ListeST.nomSt asc";


    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try {
       while (rs.next()) {
         int idSt = rs.getInt("idSt");
         String nomSt = rs.getString("nomSt");
         int idVersionSt = rs.getInt("idVersionSt");
         if (nomSt != null)
         {
           ST theST = new ST(nomSt);
           theST.idVersionSt = idVersionSt;
           theST.idSt = idSt;
           this.ListeSt.addElement(theST);
           this.ListeStViolet.addElement(theST);
         }

      this.nbStViolet++;
       }
     } catch (SQLException s) {s.getMessage();}

    return this.nbStViolet;
}

 public int getAppliRougeMOA(String nomBase,Connexion myCnx, Statement st, String nomSI){
   this.nbAppliRouge=0;
   req = "SELECT     ListeST.idSt, ListeST.nomSt, ListeST.idVersionSt";
   req+= "   FROM         ListeST INNER JOIN";
   req+= "                  SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
   req+= "                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
   req+= " WHERE     (TypeSi.nomTypeSi = 'SI Rouge') AND (ListeST.isAppli = 1) AND (ListeST.etatVersionSt = 3) AND (ListeST.serviceMoaVersionSt  = "+this.id+") AND (SI.nomSI = '"+nomSI+"') order by ListeST.nomSt asc";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    try {
       while (rs.next()) {
         int idSt = rs.getInt("idSt");
         String nomSt = rs.getString("nomSt");
         int idVersionSt = rs.getInt("idVersionSt");
         if (nomSt != null)
         {
           ST theST = new ST(nomSt);
           theST.idVersionSt = idVersionSt;
           theST.idSt = idSt;
           this.ListeSt.addElement(theST);
         }

      this.nbAppliRouge++;
       }
      } catch (SQLException s) {s.getMessage();}
    return this.nbAppliRouge;
}

  public void getCC(String nomBase,Connexion myCnx, Statement st, String nomSI){
    this.nbAppliRouge=0;
    req = "SELECT     nomServiceImputations, CentreCout";
    req+="    FROM         Service";
    req+=" WHERE     (nomService = '"+this.nom+"')";


     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     try {
        while (rs.next()) {
          this.nomServiceImputations = rs.getString("nomServiceImputations");
          this.CentreCout = rs.getString("CentreCout");
        }
       } catch (SQLException s) {s.getMessage();}

}
  
 public int getSiRougeMOA(String nomBase,Connexion myCnx, Statement st, String nomSI){
   this.nbStRouge=0;
   this.ListeStRouge.removeAllElements();
   req = "SELECT     ListeST.idSt, ListeST.nomSt, ListeST.idVersionSt";
   req+= "   FROM         ListeST INNER JOIN";
   req+= "                  SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
   req+= "                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
   req+= " WHERE     (TypeSi.nomTypeSi = 'SI Rouge') AND (ListeST.isST = 1) AND (ListeST.etatVersionSt = 3) AND (ListeST.serviceMoaVersionSt  = "+this.id+") AND (SI.nomSI = '"+nomSI+"') order by ListeST.nomSt asc";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    try {
       while (rs.next()) {
         int idSt = rs.getInt("idSt");
         String nomSt = rs.getString("nomSt");
         int idVersionSt = rs.getInt("idVersionSt");
         if (nomSt != null)
         {
           ST theST = new ST(nomSt);
           theST.idVersionSt = idVersionSt;
           theST.idSt = idSt;
           this.ListeSt.addElement(theST);
           this.ListeStRouge.addElement(theST);
         }

      this.nbStRouge++;
       }
      } catch (SQLException s) {s.getMessage();}
    return this.nbStRouge;
}

  public int getSiVioletMOE(String nomBase,Connexion myCnx, Statement st, String nomSI){
    this.nbStViolet=0;
    req = "SELECT     ListeST.idSt, ListeST.nomSt, ListeST.idVersionSt";
    req+= "   FROM         ListeST INNER JOIN";
    req+= "                  SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
    req+= "                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
    req+= " WHERE     (TypeSi.nomTypeSi = 'SI Violet') AND (ListeST.etatVersionSt = 3) AND (ListeST.serviceMoeVersionSt  = "+this.id+") AND (SI.nomSI = '"+nomSI+"') order by ListeST.nomSt asc";


     ResultSet rs = myCnx.ExecReq(st, nomBase, req);

     try {
        while (rs.next()) {
          int idSt = rs.getInt("idSt");
          String nomSt = rs.getString("nomSt");
          int idVersionSt = rs.getInt("idVersionSt");
          if (nomSt != null)
          {
            ST theST = new ST(nomSt);
            theST.idVersionSt = idVersionSt;
            theST.idSt = idSt;
            this.ListeSt.addElement(theST);
          }

       this.nbStViolet++;
        }
      } catch (SQLException s) {s.getMessage();}

     return this.nbStViolet;
  }
  public int getSiBleuMOE(String nomBase,Connexion myCnx, Statement st, String nomSI){
    this.nbStBleu=0;
    req = "SELECT     ListeST.idSt, ListeST.nomSt, ListeST.idVersionSt";
    req+= "   FROM         ListeST INNER JOIN";
    req+= "                  SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
    req+= "                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
    req+= " WHERE     (TypeSi.nomTypeSi = 'SI Bleu') AND (ListeST.etatVersionSt = 3) AND (ListeST.serviceMoeVersionSt  = "+this.id+") AND (SI.nomSI = '"+nomSI+"') order by ListeST.nomSt asc";


     ResultSet rs = myCnx.ExecReq(st, nomBase, req);

     try {
        while (rs.next()) {
          int idSt = rs.getInt("idSt");
          String nomSt = rs.getString("nomSt");
          int idVersionSt = rs.getInt("idVersionSt");
          if (nomSt != null)
          {
            ST theST = new ST(nomSt);
            theST.idVersionSt = idVersionSt;
            theST.idSt = idSt;
            this.ListeSt.addElement(theST);
          }

       this.nbStBleu++;
        }
      } catch (SQLException s) {s.getMessage();}

     return this.nbStBleu;
  }

  public int getAppliRougeMOE(String nomBase,Connexion myCnx, Statement st, String nomSI){
    this.nbAppliRouge=0;
    req = "SELECT     ListeST.idSt, ListeST.nomSt, ListeST.idVersionSt";
    req+= "   FROM         ListeST INNER JOIN";
    req+= "                  SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
    req+= "                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
    req+= " WHERE     (TypeSi.nomTypeSi = 'SI Rouge') AND (ListeST.isAppli = 1) AND (ListeST.etatVersionSt = 3) AND (ListeST.serviceMoeVersionSt  = "+this.id+") AND (SI.nomSI = '"+nomSI+"') order by ListeST.nomSt asc";

     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     try {
        while (rs.next()) {
          int idSt = rs.getInt("idSt");
          String nomSt = rs.getString("nomSt");
          int idVersionSt = rs.getInt("idVersionSt");
          if (nomSt != null)
          {
            ST theST = new ST(nomSt);
            theST.idVersionSt = idVersionSt;
            theST.idSt = idSt;
            this.ListeSt.addElement(theST);
          }

       this.nbAppliRouge++;
        }
       } catch (SQLException s) {s.getMessage();}
     return this.nbAppliRouge;
  }

   public int getSiRougeMOE(String nomBase,Connexion myCnx, Statement st, String nomSI){
     this.nbStRouge=0;
     req = "SELECT     ListeST.idSt, ListeST.nomSt, ListeST.idVersionSt";
     req+= "   FROM         ListeST INNER JOIN";
     req+= "                  SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
     req+= "                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
     req+= " WHERE     (TypeSi.nomTypeSi = 'SI Rouge') AND (ListeST.isST = 1) AND (ListeST.etatVersionSt = 3) AND (ListeST.serviceMoeVersionSt  = "+this.id+") AND (SI.nomSI = '"+nomSI+"') order by ListeST.nomSt asc";

      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try {
         while (rs.next()) {
           int idSt = rs.getInt("idSt");
           String nomSt = rs.getString("nomSt");
           int idVersionSt = rs.getInt("idVersionSt");
           if (nomSt != null)
           {
             ST theST = new ST(nomSt);
             theST.idVersionSt = idVersionSt;
             theST.idSt = idSt;
             this.ListeSt.addElement(theST);
           }

        this.nbStRouge++;
         }
        } catch (SQLException s) {s.getMessage();}
      return this.nbStRouge;
  }

public int getNbTestByMonth(String nomBase,Connexion myCnx, Statement st, String anneeRef, int theMonth){
  ResultSet rs = null;
  int nb=0;

  if (this.isMoe != 1)
  {
    req = "SELECT COUNT(*) AS nb FROM "+this.TableListeProjet+" WHERE (serviceMoaVersionSt = "+this.id+") AND (MONTH(dateTest) = "+theMonth+") AND (YEAR(dateTest) = "+anneeRef+")";
  req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
  }
  else
  {
    req = "SELECT COUNT(*) AS nb FROM "+this.TableListeProjet+" WHERE (serviceMoeVersionSt = "+this.id+") AND (MONTH(dateTest) = "+theMonth+") AND (YEAR(dateTest) = "+anneeRef+")";
  req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
   }

  rs = myCnx.ExecReq(st, nomBase, req);
  try { rs.next();
  nb= rs.getInt(1);

} catch (SQLException s) {s.getMessage();}

  return nb;
}

public int getNbTest_faits(String nomBase,Connexion myCnx, Statement st, String anneeRef,String currenntYear, int currenntMonth){

  ResultSet rs = null;
  int nb_fait=0;
  if (this.isMoe != 1)
 {
  if (anneeRef.equals(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoaVersionSt = " + this.id +
            ") AND (YEAR(dateTest) = " + anneeRef + ") AND (MONTH(dateTest) < " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_fait = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else if (Integer.parseInt(anneeRef) < Integer.parseInt(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req+="  FROM       "+this.TableListeProjet;
        req+="  WHERE     (serviceMoaVersionSt = "+this.id+") AND (YEAR(dateTest) = "+anneeRef+")";
        rs = myCnx.ExecReq(st, nomBase, req);
        try { rs.next();
        nb_fait= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
      }
      else nb_fait = 0;
 }
 else
 {
  if (anneeRef.equals(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoeVersionSt = " + this.id +
            ") AND (YEAR(dateTest) = " + anneeRef + ") AND (MONTH(dateTest) < " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_fait = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else if (Integer.parseInt(anneeRef) < Integer.parseInt(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req+="  FROM       "+this.TableListeProjet;
        req+="  WHERE     (serviceMoeVersionSt = "+this.id+") AND (YEAR(dateTest) = "+anneeRef+")";
        rs = myCnx.ExecReq(st, nomBase, req);
        try { rs.next();
        nb_fait= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
      }
      else nb_fait = 0;
 }
  return nb_fait;
}

public int getNbTest_encours(String nomBase,Connexion myCnx, Statement st, String anneeRef,String currenntYear, int currenntMonth){
  ResultSet rs = null;
  int nb_encours=0;
  if (this.isMoe != 1)
 {
  if (anneeRef.equals(currenntYear))
      {

        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoaVersionSt = " + this.id +
            ") AND (YEAR(dateTest) = " + anneeRef + ") AND (MONTH(dateTest) = " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_encours = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else nb_encours = 0;
 }
 else
 {
  if (anneeRef.equals(currenntYear))
      {

        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoeVersionSt = " + this.id +
            ") AND (YEAR(dateTest) = " + anneeRef + ") AND (MONTH(dateTest) = " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_encours = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else nb_encours = 0;
 }
  return nb_encours;
}

public int getNbTest_afaire(String nomBase,Connexion myCnx, Statement st, String anneeRef,String currenntYear, int currenntMonth){
  ResultSet rs = null;
  int nb_afaire=0;
  if (this.isMoe != 1)
 {
  if (anneeRef.equals(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoaVersionSt = " + this.id +
            ") AND (YEAR(dateTest) = " + anneeRef + ") AND (MONTH(dateTest) > " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_afaire = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else if (Integer.parseInt(anneeRef) > Integer.parseInt(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req+="  FROM       "+this.TableListeProjet;
        req+="  WHERE     (serviceMoaVersionSt = "+this.id+") AND (YEAR(dateTest) = "+anneeRef+")";
        rs = myCnx.ExecReq(st, nomBase, req);
        try { rs.next();
        nb_afaire= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
      }
      else
        nb_afaire = 0;
 }
 else
 {
  if (anneeRef.equals(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoeVersionSt = " + this.id +
            ") AND (YEAR(dateTest) = " + anneeRef + ") AND (MONTH(dateTest) > " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_afaire = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else if (Integer.parseInt(anneeRef) > Integer.parseInt(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req+="  FROM       "+this.TableListeProjet;
        req+="  WHERE     (serviceMoeVersionSt = "+this.id+") AND (YEAR(dateTest) = "+anneeRef+")";
        rs = myCnx.ExecReq(st, nomBase, req);
        try { rs.next();
        nb_afaire= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
      }
      else
        nb_afaire = 0;
 }
  return nb_afaire;
}
// -------------------------------------------------------------------------------------------------------------------//

// ------------------------- Statistiques Projet: Mep --------------------------------------------------------------//
public int getNbMep(String nomBase,Connexion myCnx, Statement st, String anneeRef){
  ResultSet rs = null;
  int nb_total=0;
  if (this.isMoe != 1)
   {
  req = "SELECT     COUNT(*) AS nb";
  req+="  FROM       "+this.TableListeProjet;
  req+="  WHERE     (serviceMoaVersionSt = "+this.id+") AND (YEAR(dateMep) = "+anneeRef+")";
  req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
  rs = myCnx.ExecReq(st, nomBase, req);
  try { rs.next();
  nb_total= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
   }
   else
   {
 req = "SELECT     COUNT(*) AS nb";
 req+="  FROM       "+this.TableListeProjet;
 req+="  WHERE     (serviceMoeVersionSt = "+this.id+") AND (YEAR(dateMep) = "+anneeRef+")";
 req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
 rs = myCnx.ExecReq(st, nomBase, req);
 try { rs.next();
 nb_total= rs.getInt(1);

     } catch (SQLException s) {s.getMessage();}
  }

  return nb_total;
}

public int getNbMepByMonth(String nomBase,Connexion myCnx, Statement st, String anneeRef, int theMonth){
  ResultSet rs = null;
  int nb=0;


  if (this.isMoe != 1)
  {
    req = "SELECT COUNT(*) AS nb FROM "+this.TableListeProjet+" WHERE (serviceMoaVersionSt = "+this.id+") AND (MONTH(dateMep) = "+theMonth+") AND (YEAR(dateMep) = "+anneeRef+")";
  req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
  }
  else
  {
    req = "SELECT COUNT(*) AS nb FROM "+this.TableListeProjet+" WHERE (serviceMoeVersionSt = "+this.id+") AND (MONTH(dateMep) = "+theMonth+") AND (YEAR(dateMep) = "+anneeRef+")";
  req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
   }
  rs = myCnx.ExecReq(st, nomBase, req);
  try { rs.next();
  nb= rs.getInt(1);

} catch (SQLException s) {s.getMessage();}

  return nb;
}

public int getNbMep_faits(String nomBase,Connexion myCnx, Statement st, String anneeRef,String currenntYear, int currenntMonth){

  ResultSet rs = null;
  int nb_fait=0;
  if (this.isMoe != 1)
 {
  if (anneeRef.equals(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoaVersionSt = " + this.id +
            ") AND (YEAR(dateMep) = " + anneeRef + ") AND (MONTH(dateMep) < " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_fait = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else if (Integer.parseInt(anneeRef) < Integer.parseInt(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req+="  FROM       "+this.TableListeProjet;
        req+="  WHERE     (serviceMoaVersionSt = "+this.id+") AND (YEAR(dateMep) = "+anneeRef+")";
        rs = myCnx.ExecReq(st, nomBase, req);
        try { rs.next();
        nb_fait= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
      }
      else nb_fait = 0;
 }
 else
 {
  if (anneeRef.equals(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoeVersionSt = " + this.id +
            ") AND (YEAR(dateMep) = " + anneeRef + ") AND (MONTH(dateMep) < " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_fait = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else if (Integer.parseInt(anneeRef) < Integer.parseInt(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req+="  FROM       "+this.TableListeProjet;
        req+="  WHERE     (serviceMoeVersionSt = "+this.id+") AND (YEAR(dateMep) = "+anneeRef+")";
        rs = myCnx.ExecReq(st, nomBase, req);
        try { rs.next();
        nb_fait= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
      }
      else nb_fait = 0;
 }
  return nb_fait;
}

public int getNbMep_encours(String nomBase,Connexion myCnx, Statement st, String anneeRef,String currenntYear, int currenntMonth){
  ResultSet rs = null;
  int nb_encours=0;
  if (this.isMoe != 1)
 {
  if (anneeRef.equals(currenntYear))
      {

        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoaVersionSt = " + this.id +
            ") AND (YEAR(dateMep) = " + anneeRef + ") AND (MONTH(dateMep) = " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_encours = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else nb_encours = 0;
 }
 else
 {
  if (anneeRef.equals(currenntYear))
      {

        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoeVersionSt = " + this.id +
            ") AND (YEAR(dateMep) = " + anneeRef + ") AND (MONTH(dateMep) = " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_encours = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else nb_encours = 0;
 }

  return nb_encours;
}

public int getNbMep_afaire(String nomBase,Connexion myCnx, Statement st, String anneeRef,String currenntYear, int currenntMonth){
  ResultSet rs = null;
  int nb_afaire=0;
  if (this.isMoe != 1)
 {
  if (anneeRef.equals(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoaVersionSt = " + this.id +
            ") AND (YEAR(dateMep) = " + anneeRef + ") AND (MONTH(dateMep) > " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_afaire = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else if (Integer.parseInt(anneeRef) > Integer.parseInt(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req+="  FROM       "+this.TableListeProjet;
        req+="  WHERE     (serviceMoaVersionSt = "+this.id+") AND (YEAR(dateMep) = "+anneeRef+")";
        rs = myCnx.ExecReq(st, nomBase, req);
        try { rs.next();
        nb_afaire= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
      }
      else
        nb_afaire = 0;
 }
 else
 {
  if (anneeRef.equals(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req +=" FROM         "+this.TableListeProjet;
        req += "  WHERE     (serviceMoeVersionSt = " + this.id +
            ") AND (YEAR(dateMep) = " + anneeRef + ") AND (MONTH(dateMep) > " +
            currenntMonth + ")";
        req += "  GROUP BY Panier HAVING  (Panier = 'checked')";
        rs = myCnx.ExecReq(st, nomBase, req);
        try {
          rs.next();
          nb_afaire = rs.getInt(1);

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
      else if (Integer.parseInt(anneeRef) > Integer.parseInt(currenntYear))
      {
        req = "SELECT     COUNT(*) AS nb";
        req+="  FROM       "+this.TableListeProjet;
        req+="  WHERE     (serviceMoeVersionSt = "+this.id+") AND (YEAR(dateMep) = "+anneeRef+")";
        rs = myCnx.ExecReq(st, nomBase, req);
        try { rs.next();
        nb_afaire= rs.getInt(1);

      } catch (SQLException s) {s.getMessage();}
      }
      else
        nb_afaire = 0;
 }
  return nb_afaire;
}
// -------------------------------------------------------------------------------------------------------------------//

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;
    //req = "EXEC ACTEUR_SelectUnService "+this.id;

    req="SELECT     nomService, descService, dirService, typeService, sendRetard, nomServicePO, nomServiceImputations, idServicePere, CentreCout, isMoe, isMoa, isGouvernance, isExploitation";
    req+="    FROM         Service";
    req+=" WHERE     (idService = "+this.id+")";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();

      this.nom = rs.getString(1);
      this.description = rs.getString(2);
      this.direction = rs.getInt(3);
      this.type = rs.getString(4);
      this.sendRetard = rs.getInt(5);
      this.nomServicePO= rs.getString(6);
      this.nomServiceImputations= rs.getString("nomServiceImputations");
      this.idServicePere= rs.getInt("idServicePere");
      this.CentreCout= rs.getString("CentreCout");
      if (this.CentreCout == null) this.CentreCout = "";
      this.isMoe = rs.getInt("isMoe");
      this.isMoa = rs.getInt("isMoa");
      this.isGouvernance = rs.getInt("isGouvernance");
      this.isExploitation = rs.getInt("isExploitation");
      } catch (SQLException s) {s.getMessage(); }
  }

public static void getListe(String nomBase,Connexion myCnx, Statement st ){
  //Construction du tableau de valeur des services, organis�s par directions
  String req = "EXEC LISTEACTEUR_SelectServ";
  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  ListeServices.removeAllElements();

  try { while(rs.next()) {
          int direction = rs.getInt ("dirService");
          int id = rs.getInt ("idService");
          String nom = rs.getString ("nomService");

          Service theService = new Service (id,nom,direction);
          ListeServices.addElement(theService);
                }	} catch (SQLException s) {s.getMessage();}
}


  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("nomServiceImputations="+this.nomServiceImputations);
    System.out.println("old_nom="+this.old_nom);
    System.out.println("alias="+this.alias);
    System.out.println("description="+this.description);
    System.out.println("mdp="+this.mdp);
    System.out.println("type="+this.type);
    System.out.println("Si="+this.Si);
    System.out.println("nbStRouge="+this.nbStRouge);
    System.out.println("nbStBleu="+this.nbStBleu);
    System.out.println("idServicePere="+this.idServicePere);
    System.out.println("CentreCout="+this.CentreCout);
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

    int theYearRef=2012;
    int LF = 1;

    Service theService = new Service(205);
     //theService.TableListeProjet="ListeProjets";
    theService.getAllFromBd(myCnx.nomBase,myCnx,  st);
    //theService.excelExportLf(myCnx.nomBase,myCnx,  st, st2, 2014);
    theService.excelExportProjetTechnique(myCnx.nomBase,myCnx,  st, st2, 2013);
    //theService.dump();
    //theService.getListeProjetsClients(myCnx.nomBase,myCnx,  st, "2013", 1);
    //float nb = theService.getNbJoursEtp(myCnx.nomBase,myCnx,  st);
    //System.out.println("----------- nb="+nb);
    //int nb = theService.getListeProjets_sansIdPO(myCnx.nomBase,myCnx,  st);
    //float nb = theService.getNbJoursEtp(myCnx.nomBase,myCnx,  st);
    //int nb = theService.getListeProjets_sansCharges(myCnx.nomBase,myCnx,  st);
    //getListeProjetsRespMOA
    //theService.getListeProjetsRespMOA(myCnx.nomBase,myCnx,  st, 2012, 2560);
    //getListeProjets(String nomBase,Connexion myCnx, Statement st, int anneeRef, String SiRouge, String SiBleu, String SiViolet , String AD,String T0, String EB,String UAT,String PROD, String Client, String PeriodeDebut, String PeriodeFin, String MotsClef, String idVersionSt, String idMembre,  String ST_Lies)
    //09:12:28-171::source=--------------------- SiRouge=1
    //09:12:28-171::source=--------------------- SiBleu=1
    //09:12:28-171::source=--------------------- SiViolet=1
    //09:12:28-171::source=--------------------- AD=1
    //09:12:28-171::source=--------------------- T0=1
    //09:12:28-171::source=--------------------- EB=1
    //09:12:28-171::source=--------------------- UAT=1
    //09:12:28-171::source=--------------------- PROD=1
    //09:12:28-171::source=--------------------- FiltreClient=-1
    //09:12:28-171::source=--------------------- PeriodeDebut=
    //09:12:28-171::source=--------------------- PeriodeFin=
    //09:12:28-171::source=--------------------- MotsClef=
    //09:12:28-171::source=--------------------- ListeSt=-1;
    //09:12:28-171::source=--------------------- CpMOA=-1
    //09:12:28-171::source=--------------------- ST_Lie=-1
    //theService.getListeProjets(myCnx.nomBase,myCnx,  st, 2012, "1", "1", "1", "1","1", "1", "1", "1", "-1","","", "", "-1;", "-1", "189" );
    //Vector theListe=theService.ListeProjets;
    //myCnx.trace("----------","theListe.size()",""+theListe.size());

    //theService.getListeActionsRetard(myCnx.nomBase,myCnx,  st, st2);


    //theService.getListeProjetsOpen(myCnx.nomBase,myCnx,  st, st2,"2012");
    //theService.getListeProjets(myCnx.nomBase,myCnx,  st, 2011,"1","1","1","1","0");
    //theService.getListeProjetsRespMOA(myCnx.nomBase,myCnx,  st, 2010, 2294);
    //theService.getListeProjetsST(myCnx.nomBase,myCnx,  st, 2010,""+1477);

    //theService.getListeCollaborateursRetardAction(myCnx.nomBase,myCnx,  st,   st2);
    //theService.getListeActionsRetard(myCnx.nomBase,myCnx,  st, st2);
    //System.out.println("theService.ListeActions="+theService.ListeActions.size());
    //theService.getTotalChargesProjets(myCnx.nomBase,myCnx,  st, 2011);
    //theService.getListeProjetsLies(myCnx.nomBase,myCnx,  st,2012, 189);
    //theService.getListeProjetsLiesByMonthByJalon(myCnx.nomBase,myCnx,  st,2012, 189,6,"MEP");
    //theService.getListeProjetsClients(myCnx.nomBase,myCnx,  st,""+theYearRef );
/*
    String result ="OK";
    transaction theTransaction = new transaction ("EnregChiffrage");
    theTransaction.begin(myCnx.nomBase,myCnx,  st);

  for (int i=0; i < theService.ListeProjets.size();i++)
      {
        Roadmap theRoadmap = (Roadmap)theService.ListeProjets.elementAt(i);
        theRoadmap.theChiffrage = new Chiffrage(theService.nomServiceImputations,theYearRef,LF);
        //theRoadmap.dump();
        //if (true) return;
        for (int j=0; j < 10;j++)
        {
          int versionSt=-1;
          float charge =0;
          try{
            //c1.trace("----","ST"+j,""+request.getParameter("ST"+j));
            versionSt= 12345;
            //c1.trace("----","chargeST"+j,""+request.getParameter("chargeST"+j));
            charge = (float)(j+1)*10 ;
            theRoadmap.theChiffrage.addCharges(versionSt,charge);
          }
          catch (Exception e){};

        }


    }

   result = theService.bd_UpdateChiffrage(myCnx.nomBase,myCnx,  st,theTransaction.nom, theYearRef, LF);


theTransaction.commit(myCnx.nomBase,myCnx,  st);
*/
    myCnx.Unconnect(st);
  }
}

package Test; 

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
import Composant.Excel;
import java.io.*;

import java.util.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.xssf.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import java.io.FileInputStream;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import java.util.Vector;
import PO.importPO;
import PO.LignePO;
import accesbase.Connexion;
import Projet.Devis;
import Processus.Processus;
import ST.SI;
import General.Utils;
import Projet.Roadmap;
import Organisation.Collaborateur;
import accesbase.ErrorSpecific;

public class Campagne {
  static Connexion myCnx;
  public int id = -1;
  public String date;
  public Date Ddate;
  public int num;
  public int nb_KO;
  public int nb_AR;
  public int nb_AF;
  public int nb_OK;
  public int nb_AN;

  public int nbTests;

  public float IA1;
  public float IA2;

  public int idRoadmap;
  public String FileName="";
  public String FileNameIn="";

  private String req = "";

  final static int C_Date=0;
  final static int C_KO=1;
  final static int C_AR=2;
  final static int C_AF=3;
  final static int C_OK=4;
  final static int C_AN=5;
  final static int C_nbTests=7;
  final static int C_IA1=10;
  final static int C_IA2=11;

  final static int L_Start=3;

  private String typeCampagne = "";
  public String version="";

  public Vector ListeCategories = new Vector(10);

  public int idCampagneLiee=-1;
  public int isNonReg=0;
  
  public String directory="";
  public String filename="";  

  public Campagne(int idRoadmap) {
    this.idRoadmap = idRoadmap;
    this.typeCampagne = "idRoadmap";
  }

  public Campagne(int id, String typeCampagne) {
    this.typeCampagne = typeCampagne;
    if (this.typeCampagne.equals("idCampagne"))
    {
      this.id = id;
    }
  }

 public  void excelExport(String nomBase,Connexion myCnx, Statement st, Statement st2, String Client, String directory, String filename){
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    
    this.directory = directory;
    this.filename = filename;     

    String[] entete = {"Campagne","Categorie", "Libelle", "MiseEnOeuvre", "intervenant", "Etat", "anomalie", "commentaire"};
    Excel theExport = new Excel("doc/"+Client+"/test/exportExcel",entete);

    theExport.xl_delete();
    //myCnx.trace("1---------- delete","location",""+location);

    // --------------------------------------------------------------------------------------------------//


    theExport.attach_xlsx(theExport.Basedirectory+"/" + this.filename); // nom du fichier excel horodate
    //myCnx.trace("2---------- attach","location",""+location+"/ExportCampagneTest_"+0+".xls");

    //theExport.xl_open_update();
    theExport.xl_open_create_xlsx("CAHIER_TEST"); // feuille de calcul
    theExport.setStyleDate_xlsx();

    
    /// --------------------------- Récupération de la liste des catégories de test----------------------//
    this.getListeCategories(myCnx.nomBase,myCnx,st);
    // -------------------------------------------------------------------------------------------------//    

    int col = 0;
    theExport.sheet_xlsx.setColumnWidth(col, 256*11); // A
    theExport.sheet_xlsx.setColumnWidth(++col, 256*24); // B
    theExport.sheet_xlsx.setColumnWidth(++col, 256*40); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*46); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*15); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*4); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*3); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*65); // fixer la largeur de la 1ere colonne

    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet_xlsx.createFreezePane(2,1) ;

    
    int numRow = 0;
    Row row = theExport.sheet_xlsx.createRow(numRow);
    theExport.xl_writeEntete_xlsx(row,0,theExport.entete);
    numRow++;  
    

  for (int j=0; j < this.ListeCategories.size();j++)
  {
   // myCnx.trace("@01234--------------------------","Categorie",""+j);
    CategorieTest theCategorieTest = (CategorieTest)this.ListeCategories.elementAt(j);
    theCategorieTest.getListeTests(myCnx.nomBase,myCnx,st, st2);

    for (int k=0; k < theCategorieTest.ListeTests.size();k++)
    {
     // myCnx.trace("@01234--------------------------","Test",""+k);
      Test theTest = (Test)theCategorieTest.ListeTests.elementAt(k);
      //theTest.dump();

      theTest.getResults(myCnx.nomBase,myCnx, st,this.id);
     // --------------------------------- Création du Collabotrateur ----------------------------------------
        Collaborateur theCollaborateur = new Collaborateur(theTest.intervenant);
        theCollaborateur.getAllFromBd(myCnx.nomBase,myCnx,  st2 );
     //-------------------------------------------------------------------------------------------------------      

      String str_anomalie="";
      if (theTest.anomalie == 0) str_anomalie = ""; else str_anomalie = ""+theTest.anomalie;        
     
      int numCol = 0;
      row = theExport.sheet_xlsx.createRow(numRow);
      
      if (this.version != null)
          theExport.xl_write_xlsx(row, numCol, "" + this.version.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
 
      if (theCategorieTest.nom != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + theCategorieTest.nom.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTest.nom != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + theTest.nom.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTest.miseEnOeuvre != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + theTest.miseEnOeuvre.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theCollaborateur.nom != null  && theCollaborateur.prenom != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + (theCollaborateur.nom+", "+theCollaborateur.prenom).replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
       
      if (theTest.Etat != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + theTest.Etat.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (str_anomalie != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + str_anomalie.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
      
      if (theTest.commentaire != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + theTest.commentaire.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
 
      numRow++;

      }


    }
     

   // myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create_xlsx();

    //myCnx.trace("5---------- xl_close_create","xl_close_create",""+theExport.filename);
        
    }  
 
  public void setNom(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;
    this.date = Utils.getToDay(myCnx.nomBase,myCnx,st);

    req = "SELECT     MAX(num) AS nb FROM   Campagne WHERE     (date = CONVERT(DATETIME, '"+this.date+"', 103))";
   rs = myCnx.ExecReq(st, nomBase, req);
         try {rs.next(); this.num =rs.getInt("nb") + 1;} catch (SQLException s) {s.getMessage();}
  }

  public int getNbResultTest(String nomBase,Connexion myCnx, Statement st){
    int nb = 0;
    ResultSet rs=null;
    this.date = Utils.getToDay(myCnx.nomBase,myCnx,st);

    req = "SELECT     COUNT(*) AS nb";
    req+="    FROM         categorieTest INNER JOIN";
    req+="                   Tests ON categorieTest.id = Tests.idCategorie INNER JOIN";
    req+="                   Campagne INNER JOIN";
    req+="                   Campagne AS Campagne_1 ON Campagne.idCampagneLiee = Campagne_1.id INNER JOIN";
    req+="                   Roadmap ON Campagne_1.idRoadmap = Roadmap.idRoadmap ON categorieTest.idRoadmap = Roadmap.idRoadmap";
    req+=" WHERE     (Campagne.idRoadmap = "+this.idRoadmap+")";


   rs = myCnx.ExecReq(st, nomBase, req);
         try {rs.next(); nb =rs.getInt("nb");} catch (SQLException s) {s.getMessage();}

    return nb;
  }

  public Campagne getCampagneNonReg(String nomBase,Connexion myCnx, Statement st){
    Campagne CampagneNonReg = null;
    CampagneNonReg = new Campagne(-1);
    CampagneNonReg.date = this.date;
    CampagneNonReg.num = this.num;
    CampagneNonReg.version = ""+this.version;
    CampagneNonReg.idCampagneLiee = this.id;

    return CampagneNonReg;
  }

  public void Clone(Campagne theCampagne, Roadmap theRoadmap, String nomBase,Connexion myCnx, Statement st){

    this.nb_KO = theCampagne.nb_KO;
    this.nb_AR = theCampagne.nb_AR;
    this.nb_AF = theCampagne.nb_AF;
    this.nb_OK = theCampagne.nb_KO;
    this.nb_AN = theCampagne.nb_AN;
    this.nbTests = theCampagne.nbTests;
    this.IA1 = theCampagne.IA1;
    this.IA2 = theCampagne.IA2;


  }

  public void getListeCategories(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    String req = "SELECT     categorieTest.id, categorieTest.nom";
    req+=" FROM         Campagne INNER JOIN";
    req+="                   Roadmap ON Campagne.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                   categorieTest ON Roadmap.idRoadmap = categorieTest.idRoadmap";
    req+=" WHERE     (Campagne.id = "+this.id+")";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        CategorieTest theCategorieTest = new CategorieTest(rs.getInt("id"));

        theCategorieTest.nom= rs.getString("nom");
        //theCategorieTest.dump();
        this.ListeCategories.addElement(theCategorieTest);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }

  public ErrorSpecific bd_ComputeResults(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();

    this.nb_KO = readResultByState( nomBase, myCnx,  st, "KO");
    this.nb_AR = readResultByState( nomBase, myCnx,  st, "AR");
    this.nb_AF = readResultByState( nomBase, myCnx,  st, "AF");
    this.nb_OK = readResultByState( nomBase, myCnx,  st, "OK");
    this.nb_AN = readResultByState( nomBase, myCnx,  st, "AN");

    //this.nbTests = this.nb_KO + this.nb_AR + this.nb_AF + this.nb_OK + this.nb_AN;
    this.nbTests = this.nb_KO + this.nb_AR + this.nb_AF + this.nb_OK;

    if (this.nbTests != 0)
    {
      //this.IA1 = 100 * (float) (this.nb_OK + this.nb_KO+ this.nb_AR+ this.nb_AN) / this.nbTests;
      this.IA1 = 100 * (float) (this.nb_OK + this.nb_KO) / this.nbTests;
      this.IA2 = 100 * (float) (this.nb_OK) / this.nbTests;
    }

    req = "UPDATE Campagne SET ";
    req+="nb_KO='"+this.nb_KO+"',";
    req+="nb_AR='"+this.nb_AR+"',";
    req+="nb_AF='"+this.nb_AF+"',";
    req+="nb_OK='"+this.nb_OK+"',";
    req+="IA1='"+this.IA1+"',";
    req+="IA2='"+this.IA2+"',";
    req+="nb_AN='"+this.nb_AN+"'";

    req+=" WHERE id="+this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_ComputeResults",""+this.id);

     return myError;
  }

  public int readResultNotComputed(String nomBase,Connexion myCnx, Statement st){
    int nb = 0;
    ResultSet rs;
    String req = "SELECT     COUNT(Tests.id) AS nb";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                   categorieTest ON Roadmap.idRoadmap = categorieTest.idRoadmap INNER JOIN";
    req+="                   Tests ON categorieTest.id = Tests.idCategorie";
    req+=" WHERE     (Roadmap.idRoadmap = "+this.idRoadmap+") AND (Tests.id NOT IN";
    req+="                       (SELECT     Tests_1.id AS idTest";
    req+="                         FROM          Tests AS Tests_1 INNER JOIN";
    req+="                                               resultatTest ON Tests_1.id = resultatTest.idTest RIGHT OUTER JOIN";
    req+="                                                Campagne ON resultatTest.idCampagne = Campagne.id";
    req+="                         WHERE      (Campagne.id = "+this.id+")))";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        nb = rs.getInt("nb");
      }
    }
            catch (SQLException s) {s.getMessage();}

    return nb;

  }

  public int readResultByState(String nomBase,Connexion myCnx, Statement st, String etat){
    int nb = 0;
    ResultSet rs;
    String req = "SELECT     COUNT(TypeEtatTest.nom) AS nb";
           req +=" FROM         resultatTest INNER JOIN";
           req +="            TypeEtatTest ON resultatTest.idEtat = TypeEtatTest.id";
           req +=" WHERE     (resultatTest.idCampagne = "+this.id+") AND (TypeEtatTest.nom = '"+etat+"')";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        nb = rs.getInt("nb");
      }
    }
            catch (SQLException s) {s.getMessage();}

    return nb;
  }

  public String epure (String theStr){
    if (theStr == null) return "";
    int Index = 0;
    String newStr=theStr;

    Index = theStr.indexOf(".");
    if (Index >=0)
      newStr = theStr.substring(0,Index);


    return newStr;
}


  public void majCompeurs(){

    //this.nbTests = this.nb_AF+this.nb_AR+this.nb_OK+this.nb_KO+this.nb_AN;
    this.nbTests = this.nb_AF+this.nb_AR+this.nb_OK+this.nb_KO;
    if (this.nbTests != 0)
      {
        this.IA1 = 100 * (float) (this.nb_OK + this.nb_KO) / this.nbTests;
        this.IA2 = 100 * (float) (this.nb_OK) / this.nbTests;
      }
  }

  public void xl_read_xslx() {
    FileInputStream fileIn = null;
    Row row = null;
    Cell cell = null;

    int Ligne = L_Start;
    try
    {
      // test pour svoir si c'est du xls ou du xslx/xslm
        Workbook wb = new XSSFWorkbook("..\\..\\doc\\"+this.FileName);
        Sheet sheet = wb.getSheet("Stats");
        System.out.println("wb="+wb);
        row = sheet.getRow(Ligne);
        cell = row.getCell(C_Date);
        if (cell != null)
          this.date = cell.toString();


        while((row != null) &&(cell != null) && (!this.date.equals("")))
        {
          //System.out.println("Ligne="+Ligne+"/Colonne="+C_idWebPo+"::Valeur="+cell.toString());
          //Connexion.trace("","cell.toString()",cell.toString());

          this.date = row.getCell(C_Date).toString();
          this.nb_AF = Integer.parseInt(epure(row.getCell(C_AF).toString()));
          this.nb_AR = Integer.parseInt(epure(row.getCell(C_AR).toString()));
          this.nb_OK = Integer.parseInt(epure(row.getCell(C_OK).toString()));
          this.nb_KO = Integer.parseInt(epure(row.getCell(C_KO).toString()));
          this.nb_AN = Integer.parseInt(epure(row.getCell(C_AN).toString()));

          this.majCompeurs();

            Ligne++;
            row = sheet.getRow(Ligne);
            if (row != null) cell = row.getCell(C_Date);
        }

        if (fileIn != null)
        fileIn.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    };


  }

  public void xl_read_xls() {
    FileInputStream fileIn = null;
    Cell cell = null;

    int Ligne = L_Start;
    try
    {
      // test pour svoir si c'est du xls ou du xslx/xslm
      fileIn = new FileInputStream("..\\..\\doc\\"+this.FileName);
      POIFSFileSystem fs = new POIFSFileSystem(fileIn);
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheet("Stats");
      HSSFRow row = sheet.getRow(Ligne);

      cell = row.getCell(C_Date);
        if (cell != null)
          this.date = cell.toString();


        while((row != null) &&(cell != null) && (!this.date.equals("")))
        {
          //System.out.println("Ligne="+Ligne+"/Colonne="+C_idWebPo+"::Valeur="+cell.toString());
          //Connexion.trace("","cell.toString()",cell.toString());

          this.date = row.getCell(C_Date).toString();

          this.nb_AF = Integer.parseInt(epure(row.getCell(C_AF).toString()));
          this.nb_AR = Integer.parseInt(epure(row.getCell(C_AR).toString()));
          this.nb_OK = Integer.parseInt(epure(row.getCell(C_OK).toString()));
          this.nb_KO = Integer.parseInt(epure(row.getCell(C_KO).toString()));
          this.nb_AN = Integer.parseInt(epure(row.getCell(C_AN).toString()));

          this.majCompeurs();

            Ligne++;
            row = sheet.getRow(Ligne);
            if (row != null) cell = row.getCell(C_Date);
        }

        if (fileIn != null)
        fileIn.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    };


  }
  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    String str_date ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ( (this.date != null) &&(!this.date.equals("")))
    {
     Utils.getDate(this.date);
     str_date ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

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
             req+="num"+",";
             req+="idCampagneLiee"+",";
             req+="isNonReg"+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+=""+str_date+",";
             req+="'"+this.version+"',";
             req+="'"+this.nb_KO+"',";
             req+="'"+this.nb_AR+"',";
             req+="'"+this.nb_AF+"',";
             req+="'"+this.nb_OK+"',";
             req+="'"+this.nb_AN+"',";
             req+="'"+this.idRoadmap+"',";
             if ((this.FileName == null) || (this.FileName.equals("")))
               req+="NULL"+",";
             else
               req+="'"+this.FileName+"',";
             if ((this.FileNameIn == null) || (this.FileNameIn.equals("")))
               req+="NULL"+",";
             else
               req+="'"+this.FileNameIn+"',";
             req+="'"+this.num+"',";
             req+="'"+this.idCampagneLiee+"',";
             req+="'"+this.isNonReg+"'";
           req+=")";

           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);

          //req = "SELECT     id FROM         Campagne WHERE     (idRoadmap = "+this.idRoadmap+") AND (date = CONVERT(DATETIME, '"+this.date+"', 103)) AND num = "+this.num;
          req = "SELECT     id FROM         Campagne WHERE     (idRoadmap = "+this.idRoadmap+") ORDER BY id DESC";
         rs = myCnx.ExecReq(st, nomBase, req);
         try {rs.next(); this.id =rs.getInt("id");} catch (SQLException s) {s.getMessage();}

    return myError;
  }

  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
    String str_date ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ( (this.date != null) &&(!this.date.equals("")))
    {
      //this.date = "16/03/1960";
     Utils.getDate(this.date);
     str_date ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    req = "UPDATE Campagne SET ";
    req+="date="+str_date+",";
    req+="version='"+this.version+"',";
    req+="idRoadmap='"+this.idRoadmap+"',";
    req+="idCampagneLiee='"+this.idCampagneLiee+"',";
    req+="isNonReg='"+this.isNonReg+"'";
    req+=" WHERE id="+this.id;
    int check = 0;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
    if (myError.cause == -1) return myError;

    return myError;
  }

  public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
  ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    req="DELETE FROM resultatTest   WHERE  (idCampagne  = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    if (myError.cause == -1) return myError;

    req="DELETE FROM Campagne  WHERE  (id = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    if (myError.cause == -1) return myError;

    return myError;
  }

  public ErrorSpecific bd_Enreg(String typeForm,String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    String result="";
    if (typeForm.equals("Creation")) { // Cr�ation d'un nouveau ST
      myError=this.bd_Insert( nomBase, myCnx,  st,  transaction);
    }
    else { //MAJ du ST

      myError=this.bd_Update( nomBase, myCnx,  st,  transaction);
    }

    return myError;
  }


  public Campagne getLastCampagne(String nomBase,Connexion myCnx, Statement st ){
    Campagne theCampagne = null;
    ResultSet rs = null;

    req = "SELECT     id, date, nb_KO, nb_AR, nb_AF, nb_OK, nb_AN, idRoadmap, FileName, FileNameIn, IA1, IA2, num";
    req+= " FROM         Campagne";
    req+= " WHERE     (date = CONVERT(DATETIME, '"+this.date+"', 103)) AND (id =";
    req+= "                       (SELECT     MAX(id) AS Expr1";
    req+= "                         FROM          Campagne AS Campagne_1))";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();

      theCampagne = new Campagne(rs.getInt("id"),"idCampagne");
      Date Ddate = rs.getDate("date");
      if (Ddate != null)
        theCampagne.date = ""+Ddate.getDate()+"/"+(Ddate.getMonth()+1) + "/"+(Ddate.getYear() + 1900);
       else
         theCampagne.date = "";

      this.nb_KO = rs.getInt("nb_KO");
      this.nb_AR = rs.getInt("nb_AR");
      this.nb_AF =rs.getInt("nb_AF");
      this.nb_OK = rs.getInt("nb_OK");
      this.nb_AN = rs.getInt("nb_AN");
      this.idRoadmap = rs.getInt("idRoadmap");
      this.IA1 = rs.getFloat("IA1");
      this.IA2 = rs.getFloat("IA2");

       } catch (SQLException s) {s.getMessage(); }

    return theCampagne;
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;

     if (this.typeCampagne.equals("idRoadmap"))
     {
       req = "SELECT     id, date, nb_KO, nb_AR, nb_AF, nb_OK, nb_AN, idRoadmap, FileName, FileNameIn, IA1, IA2, num, version, idCampagneLiee, isNonReg FROM Campagne WHERE (idRoadmap = " +
           this.idRoadmap + ")";
     }
     else
     {
       req = "SELECT     id, date, nb_KO, nb_AR, nb_AF, nb_OK, nb_AN, idRoadmap, FileName, FileNameIn, IA1, IA2, num, version, idCampagneLiee, isNonReg FROM Campagne WHERE (id = " +
           this.id + ")";
     }

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       this.id = rs.getInt("id");
       Date Ddate = rs.getDate("date");
       if (Ddate != null)
         this.date = ""+Ddate.getDate()+"/"+(Ddate.getMonth()+1) + "/"+(Ddate.getYear() + 1900);
        else
          this.date = "";

       this.nb_KO = rs.getInt("nb_KO");
       this.nb_AR = rs.getInt("nb_AR");
       this.nb_AF =rs.getInt("nb_AF");
       this.nb_OK = rs.getInt("nb_OK");
       this.nb_AN = rs.getInt("nb_AN");
       this.idRoadmap = rs.getInt("idRoadmap");
       this.FileName = rs.getString("FileName");
       if (this.FileName == null) this.FileName = "";
       this.FileNameIn = rs.getString("FileNameIn");
       if (this.FileNameIn == null) this.FileNameIn = "";
       this.IA1 = rs.getFloat("IA1");
       this.IA2 = rs.getFloat("IA2");
       this.num = rs.getInt("num");
       this.version = rs.getString("version");
       if (this.version == null) this.version = "";

       this.idCampagneLiee = rs.getInt("idCampagneLiee");
       this.isNonReg = rs.getInt("isNonReg");

       } catch (SQLException s) {s.getMessage(); }


       this.majCompeurs();
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("idCampagneLiee="+this.idCampagneLiee);
    System.out.println("date="+this.date);
    System.out.println("num="+this.num);
    System.out.println("version="+this.version);

    System.out.println("typeCampagne="+this.typeCampagne);
    System.out.println("idRoadmap="+this.idRoadmap);
    System.out.println("FileName="+this.FileName);
    System.out.println("FileNameIn="+this.FileNameIn);

    System.out.println("nb_KO="+this.nb_KO);
    System.out.println("nb_AR="+this.nb_AR);
    System.out.println("nb_AF="+this.nb_AF);
    System.out.println("nb_OK="+this.nb_OK);
    System.out.println("nb_AN="+this.nb_AN);
    System.out.println("nbTests="+this.nbTests);
    System.out.println("IA1="+this.IA1);
    System.out.println("IA2="+this.IA2);
    System.out.println("==================================================");
  }


  public static void main5(String[] args) {
    String fileIn= "C:\\Documents and Settings\\Admin\\Mes documents\\copyFileIn.bat";
    PrintWriter ecrivain;

    ecrivain = Utils.openTxtFile(fileIn);
    Utils.writeInTxtFile(ecrivain, "coucou");
    Utils.writeInTxtFile(ecrivain, "les papoux 2");
    Utils.CloseTxtFile(ecrivain);

  }

  public static void main2(String[] args) {
    String fileIn= "C:\\Documents and Settings\\Admin\\Mes documents\\aaaa.xlsx";
    String fileOut= "C:\\Documents and Settings\\Admin\\Mes documents\\aaaa_out.xlsx";

    try{
      Utils.copyFileBuffered(fileIn, fileOut);
    }catch (Exception e){e.printStackTrace();}
  }

  public static void main1(String[] args) {


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

    req="SELECT     idRoadmap, FileName, FileNameIn FROM   Campagne  WHERE   (FileNameIn IS NOT NULL)";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
       while (rs.next()) {
         int  idRoadmap= rs.getInt(1);
         String  FileName= rs.getString(2);
         String  FileNameIn= rs.getString(3);
         FileNameIn = FileNameIn.replace('\\','/');


         int index = FileName.lastIndexOf("/");
         FileName = FileName.substring(index+1);

         try{
           Utils.copyFileBuffered(FileNameIn, "..\\..\\doc\\"+FileName);
         }catch (Exception e){e.printStackTrace();}


         Campagne campagne = new Campagne(idRoadmap);
         campagne.FileName = FileName;
         campagne.getAllFromBd(myCnx.nomBase,myCnx,  st2);

         index = FileName.lastIndexOf(".");
         String extension = FileName.substring(index);
         if (extension.equals(".xlsx") || extension.equals(".xlsm"))
         {
           campagne.xl_read_xslx();
         }
         else
         {
           campagne.xl_read_xls();
         }

         //campagne.dump();
         campagne.bd_Update(myCnx.nomBase,myCnx, st2, "bbbb");

       }
        } catch (SQLException s) {s.getMessage();}



    myCnx.Unconnect(st);
    myCnx.Unconnect(st2);


    //campagne.xl_read("TEST-Lixian-3.0.xlsm");

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

    Roadmap roadmap = new Roadmap(19996);
    //String cr=roadmap.getListeBesoins(myCnx.nomBase, myCnx, st, false);
    roadmap.getAllFromBd(myCnx.nomBase, myCnx, st);
    //roadmap.dump();
    //roadmap.getListeActions(myCnx.nomBase, myCnx, st);
    
    Campagne campagne = new Campagne(910, "idCampagne");
    campagne.getAllFromBd(myCnx.nomBase,myCnx,  st);
    
    campagne.excelExport(myCnx.nomBase, myCnx, st,st2, "","","");

    myCnx.Unconnect(st);
    myCnx.Unconnect(st2);

    //campagne.xl_read("TEST-Lixian-3.0.xlsm");

  }

  public static void main3(String[] args) {
    Campagne campagne = new Campagne(19206);

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


    campagne.getAllFromBd(myCnx.nomBase,myCnx,  st);
    //campagne.dump();

    myCnx.Unconnect(st);
    myCnx.Unconnect(st2);


    //campagne.xl_read("TEST-Lixian-3.0.xlsm");

  }
}

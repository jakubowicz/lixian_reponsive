/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import accesbase.Connexion;
import General.Utils;
import java.io.*;
import Organisation.Service;

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
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.xssf.usermodel.*;
//import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;


import java.util.Vector;

import Organisation.Collaborateur;
import PO.LignePO;
import accesbase.ErrorSpecific;
import java.util.Date;
import java.text.SimpleDateFormat;


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
public class ExcelImport {
    static Connexion myCnx;

    public String filename="";
    public String Basedirectory="";
    public String directory="";
    
    

   
    final static int IMPORT_LigneDateExtraction=1;

    final static int IMPORT_Categorie=0;                    
    final static int IMPORT_LIBELLE_TEST=1;                    
    final static int IMPORT_DESCRIPTION_TEST=2;                   
    final static int IMPORT_MISEENOEUVRE_TEST=3;                  

    public int idRoadmap;
    String Categorie=""; // 1- IMPORT_LIBELLE_TEST=0
    String Libelle=""; // 2- IMPORT_statutImpact=4;
    String Description="";   // 3- IMPORT_MISEENOEUVRE_TEST=2;
    String MiseEnOeuvre= "";
    
    public Vector ListeLigneTEST = new Vector(10);
    public Vector ListeLigneCATEGORIE = new Vector(10);


  public ExcelImport() {
  }

  public ExcelImport(int idRoadmap) {
    this.idRoadmap = idRoadmap;
  }
  
  public void setBaseDirectory(){
    String location = this.getClass().getResource("").getFile();
    
    int pos=-1;  
    pos = location.indexOf(":");
    if (pos > -1)    
        location=location.substring(1); // on est sur du windows
    
     pos=-1;
    pos = location.indexOf("WEB-INF");
    if (pos > -1)
      location = location.substring(0,pos)+this.directory;

    location = location.replaceAll("%20","\\ ");
    Connexion.trace("xl_delete-1------------","location",""+location);
    this.Basedirectory = location;
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

  public String epure2 (String theStr){

    String newStr=theStr;

    newStr=theStr.replaceAll(".0","");

    return newStr;
}

  public String getFormatedDate(String location) {

     File myFile = new File(location);
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Date d = new Date(myFile.lastModified());

      return sdf.format(d);
  }

  public int xl_read(String nomBase,Connexion myCnx, Statement st, Statement st2) {
      int pos = -1;
      if (this.filename.indexOf("xlsx") == -1)
          return xl_readXLS(nomBase, myCnx,  st,  st2);
      else
          return xl_readXLSX(nomBase, myCnx,  st,  st2);      
      
  }
  
  public int xl_readXLS(String nomBase,Connexion myCnx, Statement st, Statement st2) {

    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();

    LignePO theLignePO =null;
    FileInputStream fileIn = null;
    Cell cell = null;

    this.ListeLigneTEST.removeAllElements();
    this.ListeLigneCATEGORIE.removeAllElements();

    //int Ligne = 26;
    int Ligne = 1;
    int Colonne = 1;
    int nb = 0;
    try
    {
      // test pour svoir si c'est du xls ou du xslx/xslm
      fileIn = new FileInputStream(this.filename);
      POIFSFileSystem fs = new POIFSFileSystem(fileIn);
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);
      HSSFRow row = sheet.getRow(Ligne);


      row = sheet.getRow(IMPORT_LigneDateExtraction);
      cell = row.getCell(IMPORT_Categorie);
      

      while((row != null) &&(cell != null) )
        {

          try{
            this.Categorie = row.getCell(IMPORT_Categorie).toString();
          }catch (Exception e){}          

          try{
            this.Libelle = row.getCell(IMPORT_LIBELLE_TEST).toString();
          }catch (Exception e){}


          try{
            this.Description =   epure(row.getCell(IMPORT_DESCRIPTION_TEST).toString());
          }catch (Exception e){}

          try{
            this.MiseEnOeuvre =   epure(row.getCell(IMPORT_MISEENOEUVRE_TEST).toString());
          }catch (Exception e){}

          CategorieTest theCategorie = this.search(this.Categorie);
          if (this.search(this.Categorie) == null)
          {
            theCategorie = new CategorieTest(this.Categorie);
          }
            
            Test theTest = new Test(this.Libelle);
            theTest.description = this.Description;
            theTest.miseEnOeuvre = this.MiseEnOeuvre;
            theCategorie.ListeTests.addElement(theTest);  
            if (this.search(this.Categorie) == null)
            {
                this.ListeLigneCATEGORIE.addElement(theCategorie);
            }            
            

            Ligne++;
            row = sheet.getRow(Ligne);
            if (row != null) cell = row.getCell(IMPORT_Categorie);
            nb++;
        }

        if (fileIn != null)
        fileIn.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    };
    
    if (this.ListeLigneCATEGORIE.size() == 0)
    {
      System.out.println("*** AUCUNE LIGNE LUES, " + this.idRoadmap);
    }    

    return nb;

  }
  
  private CategorieTest search(String nomCategorie){
      
      for (int i=0; i < this.ListeLigneCATEGORIE.size(); i++)
      {
          CategorieTest theCategorie = (CategorieTest)this.ListeLigneCATEGORIE.elementAt(i);
          if (theCategorie.nom.equals(nomCategorie)) return theCategorie;
      }
      
      return null;
  }
  
  public int xl_readXLSX(String nomBase,Connexion myCnx, Statement st, Statement st2) {

    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();

    LignePO theLignePO =null;
    FileInputStream fileIn = null;
    Cell cell = null;

    this.ListeLigneTEST.removeAllElements();
    this.ListeLigneCATEGORIE.removeAllElements();

    //int Ligne = 26;
    int Ligne = 1;
    int Colonne = 1;
    int nb = 0;

    try
    {
      // test pour svoir si c'est du xls ou du xslx/xslm
      fileIn = new FileInputStream(this.filename);
      XSSFWorkbook wb = new XSSFWorkbook (fileIn); 
      XSSFSheet sheet = null;
      
    int numOnglet = 0;
 
    sheet = wb.getSheetAt(numOnglet);  
    
    String Onglet = "";
    Onglet = sheet.getSheetName(); 
    Ligne = 1;
    XSSFRow row = sheet.getRow(IMPORT_LigneDateExtraction);  

      row = sheet.getRow(Ligne);
      cell = row.getCell(IMPORT_Categorie);

      while((row != null) &&(cell != null) )
        {

          try{
            this.Categorie = row.getCell(IMPORT_Categorie).toString();
          }catch (Exception e){}          

          try{
            this.Libelle = row.getCell(IMPORT_LIBELLE_TEST).toString();
          }catch (Exception e){}


          try{
            this.Description =   epure(row.getCell(IMPORT_DESCRIPTION_TEST).toString());
          }catch (Exception e){}

          try{
            this.MiseEnOeuvre =   epure(row.getCell(IMPORT_MISEENOEUVRE_TEST).toString());
          }catch (Exception e){}

          CategorieTest theCategorie = this.search(this.Categorie);
          if (this.search(this.Categorie) == null)
          {
            theCategorie = new CategorieTest(this.Categorie);
          }
            
            Test theTest = new Test(this.Libelle);
            theTest.description = this.Description;
            theTest.miseEnOeuvre = this.MiseEnOeuvre;
            theCategorie.ListeTests.addElement(theTest);  
            if (this.search(this.Categorie) == null)
            {
                this.ListeLigneCATEGORIE.addElement(theCategorie);
            }            
            

            Ligne++;
            row = sheet.getRow(Ligne);
            if (row != null) cell = row.getCell(IMPORT_Categorie);
            nb++;
        }

        if (fileIn != null)
        fileIn.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    };
    
    if (this.ListeLigneCATEGORIE.size() == 0)
    {
      System.out.println("*** AUCUNE LIGNE LUES, " + this.idRoadmap);
    }    

    return nb;

  }  



  public ErrorSpecific xl_write(String nomBase,Connexion myCnx, Statement st, Statement st2, String transaction) {
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    int idMembre;
    String NomProjet, previousActivite= "";
    float semaine =0;

    String reqRefSt=""; 
    ErrorSpecific myError = new ErrorSpecific();


    if (this.ListeLigneCATEGORIE.size() == 0)
    {
      System.out.println("*** AUCUNE LIGNE IMPORTEE" + this.idRoadmap);
      myError.cause=-1;
      myError.nb = 0;
      return myError;
    }

    // delete des resultats de tests lies a idRoadmap
    // delete des campagnes de tests lies a idRoadmap
    // delete des categories de tests lies a idRoadmap    
     req = "SELECT     id FROM         categorieTest WHERE     idRoadmap = " + this.idRoadmap;
     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        int idCategorie = rs.getInt("id");
        req =  "DELETE FROM Tests  WHERE     (idCategorie  = "+idCategorie+") ";
        myError = myCnx.ExecUpdateTransaction(st2,nomBase,req,true,transaction, getClass().getName(),"xl_write",""+this.idRoadmap);   
        if (myError.cause == -1) return myError;
      }
    } catch (SQLException s) {s.getMessage();}
    
        req =  "DELETE FROM categorieTest  WHERE     idRoadmap = " + this.idRoadmap;
        myError = myCnx.ExecUpdateTransaction(st2,nomBase,req,true,transaction, getClass().getName(),"xl_write",""+this.idRoadmap);      
        if (myError.cause == -1) return myError;
        
     req = "SELECT      id FROM         Campagne WHERE idRoadmap= " + this.idRoadmap;
     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        int idCampagne = rs.getInt("id");
        req =  "DELETE FROM resultatTest   WHERE     (idCampagne   = "+idCampagne+") ";
        myError = myCnx.ExecUpdateTransaction(st2,nomBase,req,true,transaction, getClass().getName(),"xl_write",""+this.idRoadmap);   
        if (myError.cause == -1) return myError;
      }
    } catch (SQLException s) {s.getMessage();}
    
        req =  "DELETE FROM Campagne  WHERE     idRoadmap = " + this.idRoadmap;
        myError = myCnx.ExecUpdateTransaction(st2,nomBase,req,true,transaction, getClass().getName(),"xl_write",""+this.idRoadmap);      
        if (myError.cause == -1) return myError;        

    
    int nb = 0;
      for (int i = 0; i < this.ListeLigneCATEGORIE.size(); i++)
      {
        CategorieTest theCategorie = (CategorieTest)this.ListeLigneCATEGORIE.elementAt(i);
        theCategorie.idRoadmap = this.idRoadmap;
        myError = theCategorie.bd_Insert(nomBase,myCnx, st,  transaction);
        if (myError.cause == -1) return myError;
        
        for (int j = 0; j < theCategorie.ListeTests.size(); j++)
        {
            Test theTest = (Test)theCategorie.ListeTests.elementAt(j);
            theTest.idCategorie = theCategorie.id;
            myError = theTest.bd_Insert(nomBase,myCnx, st,  transaction);
            if (myError.cause == -1) return myError;
            
        }
        nb++;
      }

      myError.nb=nb;

    return myError;

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

    ExcelImport theCahierTest = new ExcelImport();
    if (args.length > 0)
    {
      theCahierTest.idRoadmap = Integer.parseInt(args[0]);
    }
    else
    {
      Calendar calendar = Calendar.getInstance();
      theCahierTest.idRoadmap = calendar.get(Calendar.YEAR);
    }
    theCahierTest.xl_read(myCnx.nomBase, myCnx, st, st2);
    theCahierTest.xl_write(myCnx.nomBase, myCnx, st, st2,  "");


  }
}


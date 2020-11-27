/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package Projet; 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import OM.Attribut;
import Organisation.Service;
import PO.LignePO;
import accesbase.Connexion;
import General.Utils;
import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import org.apache.poi.hssf.usermodel.HSSFSheet; 
import org.apache.poi.xssf.usermodel.XSSFSheet;



/**
 *
 * @author JJAKUBOW
 */

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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


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

import Organisation.Collaborateur;
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
public class ExcelImportSousFiches {
    static Connexion myCnx;
    public int Annee;
    public Vector ListeLignePO = new Vector(10);
    public int nbEnreg=-1;
    public String directory = "";
    public String Basedirectory = "";      


  public ExcelImportSousFiches() {
    Statement st =null;

    st = myCnx.Connect();
    this.Annee = Integer.parseInt(Utils.getYear(myCnx.nomBase, myCnx,  st));
    this.setBaseDirectory();
  }

  public ExcelImportSousFiches(int Annee) {
    Statement st =null;

    st = myCnx.Connect();
    this.Annee = Annee;
    this.setBaseDirectory();    
  }

  public void setBaseDirectory(){
    String location = this.getClass().getResource("").getFile();
    
    this.directory="doc/po/importExcel";
    
    int pos=-1;  
    pos = location.indexOf(":");
    if (pos > -1)    
        location=location.substring(1); // on est sur du windows
    
     pos=-1;
    pos = location.indexOf("WEB-INF");
    if (pos > -1)
      location = location.substring(0,pos)+this.directory;

    location = location.replaceAll("%20","\\ ");
    //Connexion.trace("xl_delete-1------------","location",""+location);
    this.Basedirectory = location;
  }
  
  public void xl_read() {

    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();

    st = myCnx.Connect();
    st2 = myCnx.Connect();

    Attribut theAttribut =null;
    FileInputStream fileIn = null;
    Cell cell = null;

    //Connexion.trace("@01234---------------------------------","Utils.getPathProjet()",""+Utils.getPathProjet());
    //Connexion.trace("@01234---------------------------------","Utils.getPathProjet()",""+Utils.getCanonicalPath());
    
    String myDir = Utils.getCanonicalPath();
    //Connexion.trace("@01234---------------------------------","myDir",""+myDir);
    String filename ="";

    int Ligne = 1;
     filename = this.Basedirectory  + "doc/Projet/exportExcel/ImportRespireSousFiches.xlsx";
    try
    {
      // test pour svoir si c'est du xls ou du xslx/xslm
      fileIn = new FileInputStream(filename);
      /*
      POIFSFileSystem fs = new POIFSFileSystem(fileIn);
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(2);
      HSSFRow row = sheet.getRow(Ligne);
      */
    XSSFWorkbook wb = new XSSFWorkbook (filename); 
     XSSFSheet sheet = null;

      //if (cell != null) idWebPo = cell.toString();
    
    
    
    int Colonne = 1;   
    int numOnglet = 0;
    int pos=-1;
 
    sheet = wb.getSheetAt(numOnglet);  
    
    String Onglet = ""; 
    Onglet = sheet.getSheetName(); 
    Ligne = 1;
    XSSFRow row = sheet.getRow(Ligne);  
     cell = row.getCell(0); 
      while((row != null) &&(cell != null) )
        {
                     

         // Connexion.trace("","cell.toString()",cell.toString());

           int colonne=0;
           
          int idRoadmap = -1;
          try{
              String str_idRoadmap = row.getCell(colonne++).toString();
               pos = str_idRoadmap.indexOf(".");
               if (pos >= 0)
                   str_idRoadmap = str_idRoadmap.substring(0, pos);
              Connexion.trace("","str_idRoadmap",str_idRoadmap);
            idRoadmap = Integer.parseInt(str_idRoadmap);
          }catch (Exception e){}   
          
          float imputation = 0;
          try{
            String str_imputation = row.getCell(colonne++).toString();
            Connexion.trace("","str_imputation",str_imputation);
            imputation =  Float.parseFloat(str_imputation);
          }catch (Exception e){}             


        String reqRefSt =  "UPDATE Roadmap set  chargeConsommeInterne = "+imputation+" WHERE idRoadmap="+idRoadmap;
        myCnx.ExecReq(st2, base, reqRefSt);
        

  
    Ligne++;
    row = sheet.getRow(Ligne);
    if (row != null) cell = row.getCell(0);    
      }        


        }
       catch (Exception s) {s.getMessage();}
        if (fileIn != null)
        {
         try{
             fileIn.close();
            }
            catch (Exception s) {s.getMessage();}
        }
        
     this.nbEnreg = Ligne -1;
    }



  public static void main(String[] args) {

    ExcelImportSousFiches theExcelImputation = new ExcelImportSousFiches();
    theExcelImputation.xl_read();


  }
}

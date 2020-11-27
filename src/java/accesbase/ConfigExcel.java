/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package accesbase;

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

import java.io.File;
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
public class ConfigExcel {
    static Connexion myCnx;

    public String filename="";
    public String Basedirectory="";
    public String directory="";
    
    

   
    final static int IMPORT_LigneDateExtraction=1;

    final static int Col_isStandalone=0;                    
               

   
    public Vector ListeItems = new Vector(10);


  public ConfigExcel() {
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
    //Connexion.trace("xl_delete-1------------","location",""+location);
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

  
  public int xl_read() {
      
    this.ListeItems.removeAllElements();

    FileInputStream fileIn = null;
    Cell cell = null;

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
      HSSFRow row = null;
      
    int numOnglet = 0;
 
    sheet = wb.getSheetAt(numOnglet);  
    
    String Onglet = "";
    Onglet = sheet.getSheetName(); 
    Ligne = 0;
    row = sheet.getRow(IMPORT_LigneDateExtraction);  

      row = sheet.getRow(Ligne);
      cell = row.getCell(Col_isStandalone);
      String nom = "";
      while((row != null) &&(cell != null) )
        {
          item theItem = new item();
          
          if (Ligne == 0)
          {
            try{
                nom = row.getCell(Col_isStandalone).toString();
            }catch (Exception e){}
             
          }
          else
          {
            theItem.nom = nom;
            try{
                theItem.valeur= row.getCell(Col_isStandalone).toString();
            }catch (Exception e){}
            this.ListeItems.addElement(theItem);
          }

                   
            

            Ligne++;
            row = sheet.getRow(Ligne);
            if (row != null) cell = row.getCell(Col_isStandalone);
            nb++;
        }

        if (fileIn != null)
        fileIn.close();
    }
    catch (Exception e)
    {
      System.out.println("********* Impossible de lire le fichier de configuration");
      item theItem = new item();
      theItem.nom = "isStandalone";
      theItem.valeur="0";
      return 0;
    };
    
    if (this.ListeItems.size() == 0)
    {
      System.out.println("*** AUCUNE LIGNE LUES, ");
    }    

    return nb;

  }  

  public int xl_readXLSX() {
      
    this.ListeItems.removeAllElements();

    FileInputStream fileIn = null;
    Cell cell = null;

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
    Ligne = 0;
    XSSFRow row = sheet.getRow(IMPORT_LigneDateExtraction);  

      row = sheet.getRow(Ligne);
      cell = row.getCell(Col_isStandalone);
      String nom = "";
      while((row != null) &&(cell != null) )
        {
          item theItem = new item();
          
          if (Ligne == 0)
          {
            try{
                nom = row.getCell(Col_isStandalone).toString();
            }catch (Exception e){}
             
          }
          else
          {
            theItem.nom = nom;
            try{
                theItem.valeur= row.getCell(Col_isStandalone).toString();
            }catch (Exception e){}
            this.ListeItems.addElement(theItem);
          }

                   
            

            Ligne++;
            row = sheet.getRow(Ligne);
            if (row != null) cell = row.getCell(Col_isStandalone);
            nb++;
        }

        if (fileIn != null)
        fileIn.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    };
    
    if (this.ListeItems.size() == 0)
    {
      System.out.println("*** AUCUNE LIGNE LUES, ");
    }    

    return nb;

  }    


  public void xl_write(String standalone) {
      FileInputStream fileIn = null;
    try
    {
        
    
     
      fileIn = new FileInputStream(this.filename);
      POIFSFileSystem fs = new POIFSFileSystem(fileIn);
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);
      HSSFRow row = null;
		HSSFRow row1 = sheet.getRow(IMPORT_LigneDateExtraction);
		HSSFCell cell1 = row1.getCell(Col_isStandalone);
		cell1.setCellValue(standalone);
		fileIn.close();
		FileOutputStream fos =new FileOutputStream(this.filename);
	        wb.write(fos);
	        fos.close();
		System.out.println("Done");
      }
    catch (Exception e)
    {
      e.printStackTrace();
    };  
  }
  
    public void xl_writeXLSX(String standalone) {
    try
    {
     
		FileInputStream fis = new FileInputStream(new File(this.filename));
		XSSFWorkbook workbook = new XSSFWorkbook (fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFRow row1 = sheet.getRow(IMPORT_LigneDateExtraction);
		XSSFCell cell1 = row1.getCell(Col_isStandalone);
		cell1.setCellValue(standalone);
		fis.close();
		FileOutputStream fos =new FileOutputStream(new File(this.filename));
	        workbook.write(fos);
	        fos.close();
		System.out.println("Done");
      }
    catch (Exception e)
    {
      e.printStackTrace();
    };  
  }
  

  public static void main(String[] args) {

  }
}


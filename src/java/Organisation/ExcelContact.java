/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Organisation;

import Composant.Message;
import Composant.item;
import accesbase.Connexion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import accesbase.Connexion;
import General.Utils;
import java.io.*;

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
public class ExcelContact {
    static Connexion myCnx;

    public String filename="";
    public String Basedirectory="";
    public String directory="";
    
    

   // id	nom	prenom	entreprise	telephone	adresse	mail	demande	dateDemande	reponse	dateReponse	idEtat

    final static int IMPORT_LigneDateExtraction=1;

    final static int Col_id=0;
    final static int Col_nom=1;     
    final static int Col_prenom=2; 
    final static int Col_entreprise=3; 
    final static int Col_telephone=4; 
    final static int Col_adresse=5; 
    final static int Col_mail=6; 
    final static int Col_demande=7; 
    final static int Col_dateDemande=8; 
    final static int Col_reponse=9; 
    final static int Col_dateReponse=10;     
    final static int Col_etat=11; 
               

   
    public Vector ListeItems = new Vector(10);
    public Vector ListeContact = new Vector(10);
    
    private String req="";
    public CellStyle cellStyleDate=null;

  public ExcelContact() {
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

 
  public ErrorSpecific xl_readAllContacts(String nomBase,Connexion myCnx, Statement st, String transaction) {
       ErrorSpecific myError = new ErrorSpecific();
       
            req = "DELETE Contacts  ";
            myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, "ExcelContact","bd_ShiftCharges","all");
            if (myError.cause == -1) return myError;               


            // Lecture des contacts à partir d'excel
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
      XSSFRow row = null;
      
    int numOnglet = 1;
 
    sheet = wb.getSheetAt(numOnglet);  
    
    String Onglet = "";
    Onglet = sheet.getSheetName(); 
    row = sheet.getRow(IMPORT_LigneDateExtraction);  

      row = sheet.getRow(Ligne);
      cell = row.getCell(Col_nom);
      String nom = "";
      while((row != null) &&(cell != null) &&(!row.getCell(0).toString().equals("")) )
        {

          Contact theContact = new Contact(-1);  
         
          try{
            theContact.id = (int) Float.parseFloat(row.getCell(Col_id).toString()) ;
            theContact.nom = row.getCell(Col_nom).toString();
            
            try{
            theContact.prenom = row.getCell(Col_prenom).toString();
            }
            catch (Exception e)
            {
               theContact.prenom = "" ;
            }
            
            try{
            theContact.entreprise = row.getCell(Col_entreprise).toString();
            }
            catch (Exception e)
            {
               theContact.entreprise = "" ;
            }

            try{
            theContact.telephone = row.getCell(Col_telephone).toString();
            }
            catch (Exception e)
            {
               theContact.telephone = "" ;
            } 
            
            try{
            theContact.adresse = row.getCell(Col_adresse).toString();
            }
            catch (Exception e)
            {
               theContact.adresse = "" ;
            }  
            
            theContact.mail = row.getCell(Col_mail).toString();
            

            try{
            theContact.demande = row.getCell(Col_demande).toString();
            }
            catch (Exception e)
            {
               theContact.demande = "" ;
            }        
            
            String str_dateDemande = "NULL";
            try{
                Date theDate = row.getCell(Col_dateDemande).getDateCellValue();
                theContact.dateDemande = theDate.getDate()+"/"+(theDate.getMonth() +1)+"/"+(theDate.getYear()+1900);
                str_dateDemande ="convert(datetime, '"+theDate.getDate()+"/"+(theDate.getMonth() +1)+"/"+(theDate.getYear()+1900)+"', 103)";            
                       
            }
            catch (Exception e)
            {
               theContact.dateDemande = "" ;
               str_dateDemande = "NULL";
            }  
            
            
            try{
            theContact.reponse = row.getCell(Col_reponse).toString();
            }
            catch (Exception e)
            {
               theContact.reponse = "" ;
            }      
            
            String str_dateReponse = "NULL";
            try{
                Date theDate = row.getCell(Col_dateReponse).getDateCellValue();
                theContact.dateReponse = theDate.getDate()+"/"+(theDate.getMonth() +1)+"/"+(theDate.getYear()+1900);
                str_dateReponse ="convert(datetime, '"+theDate.getDate()+"/"+(theDate.getMonth() +1)+"/"+(theDate.getYear()+1900)+"', 103)";            
                       
            }
            catch (Exception e)
            {
               theContact.dateReponse = "" ;
               str_dateReponse = "NULL";
            }            
          
            theContact.idEtat = (int) Float.parseFloat(row.getCell(Col_etat).toString()) ;
            if (theContact.demande != null) theContact.demande = theContact.demande.replaceAll("'","''");
            if (theContact.reponse != null) theContact.reponse = theContact.reponse.replaceAll("'","''");
            if (theContact.reponse != null) theContact.reponse = theContact.reponse.replaceAll("'","''");
            if (theContact.nom != null) theContact.nom = theContact.nom.replaceAll("'","''");
            if (theContact.prenom != null) theContact.prenom = theContact.prenom.replaceAll("'","''");
            if (theContact.entreprise != null) theContact.entreprise = theContact.entreprise.replaceAll("'","''");
            
            // 'Jakubowicz','Joel','','0661314157','17 rue ditte','joel_jakubowicz@hotmail.fr','',NULL,'NULL',,'1'
            req = "INSERT Contacts ( id, nom, prenom, entreprise, telephone, adresse, mail, demande, dateDemande, reponse, dateReponse, idEtat)";
            req+=" VALUES (";
            req+= "'" + theContact.id + "'";
            req+=",";            
            req+= "'" + theContact.nom + "'";
            req+=",";
            req+= "'" + theContact.prenom + "'";
            req+=",";
            req+= "'" + theContact.entreprise + "'";
            req+=",";
            req+= "'" + theContact.telephone + "'";
            req+=",";
            req+= "'" + theContact.adresse + "'";
            req+=",";
            req+= "'" + theContact.mail + "'";
            req+=",";
            req+= "'" + theContact.demande + "'";
            req+=",";            
            req+= "" + str_dateDemande + "";
            req+=",";    
            req+= "'" + theContact.reponse + "'";             
            req+=",";
            req+= "" + str_dateReponse + "";             
            req+=",";         
            req+= "'" + theContact.idEtat + "'";  
            req+=")";   

            myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"xl_readAllContacts",""+this.filename);
            if (myError.cause == -1) return myError;              

          }catch (Exception e){}
          
       
            //theLignePO.dump();
            this.ListeContact.addElement(theContact);       

            Ligne++;
            row = sheet.getRow(Ligne);
            if (row != null) cell = row.getCell(Col_nom);

        }

        if (fileIn != null)
        fileIn.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    };   
        
            
            // insertion des contacts lus -> Contacts.
            
       return myError;
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
      cell = row.getCell(Col_nom);
      String nom = "";
      while((row != null) &&(cell != null) &&(!row.getCell(0).toString().equals("")) )
        {

          Contact theContact = new Contact(-1);  
         
          try{
            theContact.nom = row.getCell(0).toString();

          }catch (Exception e){}
          
       
            //theLignePO.dump();
            this.ListeContact.addElement(theContact);       

            Ligne++;
            row = sheet.getRow(Ligne);
            if (row != null) cell = row.getCell(14);

        }

        if (fileIn != null)
        fileIn.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    };   

    return nb;

  }

  private int getLigne(HSSFSheet sheet, int LigneDebut){
     int Ligne =  LigneDebut;
     HSSFRow row = null;
      HSSFCell cell = null; 
   try
    {
        row = sheet.getRow(Ligne);
        cell = row.getCell(Col_etat);
        while((row != null) &&(cell != null) &&(!row.getCell(Col_etat).toString().equals("")) )
        {
            row = sheet.getRow(Ligne);
            cell = row.getCell(Col_etat); 
            
            Ligne++;
            row = sheet.getRow(Ligne);
            if (row != null) cell = row.getCell(Col_etat);            
        }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    };   
      
     return Ligne;
  }
  
  private int getLigneXLSX(XSSFSheet sheet, int LigneDebut){
     int Ligne =  LigneDebut;
     XSSFRow row = null;
      XSSFCell cell = null; 
   try
    {
        row = sheet.getRow(Ligne);
        cell = row.getCell(Col_etat);
        while((row != null) &&(cell != null) &&(!row.getCell(Col_etat).toString().equals("")) )
        {
            row = sheet.getRow(Ligne);
            cell = row.getCell(Col_etat); 
            
            Ligne++;
            row = sheet.getRow(Ligne);
            if (row != null) cell = row.getCell(Col_etat);            
        }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    };   
      
     return Ligne;
  }  
  
  public void xl_write(Contact theContact) {
    Message m = new Message("dump",getClass().getName(), "");       
      FileInputStream fileIn = null;
    try
    {
        
    int LigneDebut = 1;
    int Ligne = 1;
     
      fileIn = new FileInputStream(this.filename);
      POIFSFileSystem fs = new POIFSFileSystem(fileIn);
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(1);
      HSSFRow row = null;
      HSSFCell cell1 = null;
      
      Ligne = getLigne(sheet, LigneDebut);
		row = sheet.createRow(Ligne);
                
		cell1 = row.createCell(Col_nom);
		cell1.setCellValue(theContact.nom);
                
		cell1 = row.createCell(Col_prenom);
		cell1.setCellValue(theContact.prenom);
                
		cell1 = row.createCell(Col_entreprise);
		cell1.setCellValue(theContact.entreprise);
                
		cell1 = row.createCell(Col_telephone);
		cell1.setCellValue(theContact.telephone);
                
		cell1 = row.createCell(Col_adresse);
		cell1.setCellValue(theContact.adresse);
                
		cell1 = row.createCell(Col_mail);
		cell1.setCellValue(theContact.mail);           
                
		cell1 = row.createCell(Col_demande);
		cell1.setCellValue(theContact.demande);       
                
		cell1 = row.createCell(Col_etat);
		cell1.setCellValue("New");                       
                
		fileIn.close();
		FileOutputStream fos =new FileOutputStream(this.filename);
	        wb.write(fos);
	        fos.close();
		m.setMessageDebug("Done");
      }
    catch (Exception e)
    {
      e.printStackTrace();
    };  
  }
 
  public void xl_writeDateStyle(Row row, int numCell, String value) {

      Cell cell = row.createCell( numCell);

    
        cell.setCellStyle(this.cellStyleDate);
        try {
        cell.setCellValue(Utils.getNewDate(value));
        }
        catch (Exception e)
        {
            cell.setCellValue(" ");
        }
    

  }  
  
  public void xl_writeUpdateDateStyle(Row row, int numCell, String value) {

      Cell cell = row.getCell( numCell);
        cell.setCellStyle(this.cellStyleDate);
        try {
        cell.setCellValue(Utils.getNewDate(value));
        }
        catch (Exception e)
        {
            cell.setCellValue(" ");
        }
    

  }  
    public void xl_writeXLSX(Contact theContact) {
    Message m = new Message("dump",getClass().getName(), "");         
    try
    {
    int LigneDebut = 1;
    int Ligne = 1;        
     
		FileInputStream fis = new FileInputStream(new File(this.filename));
		XSSFWorkbook workbook = new XSSFWorkbook (fis);
		XSSFSheet sheet = workbook.getSheetAt(1);
		XSSFRow row = null;
		XSSFCell cell1 = null;
                
    this.cellStyleDate = workbook.createCellStyle();
    CreationHelper createHelper = workbook.getCreationHelper();
    this.cellStyleDate.setDataFormat(
    createHelper.createDataFormat().getFormat("dd/MM/yyyy"));                

                Ligne = getLigneXLSX(sheet, LigneDebut);
		row = sheet.createRow(Ligne);
                theContact.id = Ligne;

		cell1 = row.createCell(Col_id);
		cell1.setCellValue(theContact.id);
                
		cell1 = row.createCell(Col_nom);
		cell1.setCellValue(theContact.nom);
                
		cell1 = row.createCell(Col_prenom);
		cell1.setCellValue(theContact.prenom);
                
		cell1 = row.createCell(Col_entreprise);
		cell1.setCellValue(theContact.entreprise);
                
		cell1 = row.createCell(Col_telephone);
		cell1.setCellValue(theContact.telephone);
                
		cell1 = row.createCell(Col_adresse);
		cell1.setCellValue(theContact.adresse);
                
		cell1 = row.createCell(Col_mail);
		cell1.setCellValue(theContact.mail);           
                
		cell1 = row.createCell(Col_demande);
		cell1.setCellValue(theContact.demande);    
                
		//cell1 = row.createCell(Col_dateDemande);
		//cell1.setCellValue(theContact.dateDemande);  
               this.xl_writeDateStyle(row, Col_dateDemande, "" + theContact.dateDemande);
                
		cell1 = row.createCell(Col_reponse);
		cell1.setCellValue(theContact.reponse);    
                
		//cell1 = row.createCell(Col_dateReponse);
		//cell1.setCellValue(theContact.dateReponse); 
                this.xl_writeDateStyle(row, Col_dateReponse, "" + theContact.dateReponse);
                
		cell1 = row.createCell(Col_etat);
		cell1.setCellValue(theContact.idEtat);                  
                
		fis.close();
		FileOutputStream fos =new FileOutputStream(this.filename);
	        workbook.write(fos);
	        fos.close();
		m.setMessageDebug("Done");
      }
    catch (Exception e)
    {
      e.printStackTrace();
    };  
  }  
    
    public void xl_writeUpdateXLSX(Contact theContact) {
    Message m = new Message("dump",getClass().getName(), "");         
    try
    {
    int LigneDebut = 1;
    int Ligne = 1;        
     
		FileInputStream fis = new FileInputStream(new File(this.filename));
		XSSFWorkbook workbook = new XSSFWorkbook (fis);
		XSSFSheet sheet = workbook.getSheetAt(1);
		XSSFRow row = null;
		XSSFCell cell1 = null;
                
    this.cellStyleDate = workbook.createCellStyle();
    CreationHelper createHelper = workbook.getCreationHelper();
    this.cellStyleDate.setDataFormat(
    createHelper.createDataFormat().getFormat("dd/MM/yyyy"));                

 		row = sheet.getRow(theContact.id);

		cell1 = row.getCell(Col_id);
		cell1.setCellValue(theContact.id);
                
		cell1 = row.getCell(Col_nom);
		cell1.setCellValue(theContact.nom);
                
		cell1 = row.getCell(Col_prenom);
		cell1.setCellValue(theContact.prenom);
                
		cell1 = row.getCell(Col_entreprise);
		cell1.setCellValue(theContact.entreprise);
                
		cell1 = row.getCell(Col_telephone);
		cell1.setCellValue(theContact.telephone);
                
		cell1 = row.getCell(Col_adresse);
		cell1.setCellValue(theContact.adresse);
                
		cell1 = row.getCell(Col_mail);
		cell1.setCellValue(theContact.mail);           
                
		cell1 = row.getCell(Col_demande);
		cell1.setCellValue(theContact.demande);    
                
		//cell1 = row.createCell(Col_dateDemande);
		//cell1.setCellValue(theContact.dateDemande);  
                //this.xl_writeDateStyle(row, Col_dateDemande, "" + theContact.dateDemande);
                
		cell1 = row.getCell(Col_reponse);
		cell1.setCellValue(theContact.reponse);    
                
		//cell1 = row.createCell(Col_dateReponse);
		//cell1.setCellValue(theContact.dateReponse); 
                this.xl_writeUpdateDateStyle(row, Col_dateReponse, "" + theContact.dateReponse);
                
		cell1 = row.getCell(Col_etat);
		cell1.setCellValue(theContact.idEtat);                  
                
		fis.close();
		FileOutputStream fos =new FileOutputStream(this.filename);
	        workbook.write(fos);
	        fos.close();
		m.setMessageDebug("Done");
      }
    catch (Exception e)
    {
      e.printStackTrace();
    };  
  }      
    
  public void setStyleDate_xlsx(XSSFWorkbook workbook){
    this.cellStyleDate = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        this.cellStyleDate.setDataFormat(
        createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
  }  
  
  
    public void xl_writeXLSX(String standalone) {
    Message m = new Message("dump",getClass().getName(), "");         
    try
    {
     
		FileInputStream fis = new FileInputStream(new File(this.filename));
		XSSFWorkbook workbook = new XSSFWorkbook (fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		XSSFRow row1 = sheet.getRow(IMPORT_LigneDateExtraction);
		XSSFCell cell1 = row1.getCell(Col_nom);
		cell1.setCellValue(standalone);
		fis.close();
		FileOutputStream fos =new FileOutputStream(this.filename);
	        workbook.write(fos);
	        fos.close();
		m.setMessageDebug("Done");
      }
    catch (Exception e)
    {
      e.printStackTrace();
    };  
  }
  

  public static void main(String[] args) {

  }
}


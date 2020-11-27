package Composant;

import accesbase.Connexion;
import ST.*;
import General.Utils;
import PO.*;

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
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.usermodel.HSSFRow;

import org.apache.poi.ss.usermodel.CreationHelper;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import java.io.FileInputStream;


import java.util.Vector;

import Organisation.Collaborateur;
import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

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
public class Excel {

    public String filename = "";
    public String directory = "";
    public String Basedirectory = "";

    public FileInputStream file = null;
    public HSSFWorkbook workbook = null;
    public HSSFSheet sheet = null;
    public HSSFCellStyle cellStyle = null;
    
    public FileOutputStream file_xlsx = null;
    public XSSFWorkbook workbook_xlsx = null;
    public XSSFSheet sheet_xlsx = null;
    public XSSFCellStyle cellStyle_xlsx = null;

    public String[] entete = null;
    
    public CellStyle cellStyleDate=null;



  public Excel(String directory,String filename, String[] entete) {
    this.entete = entete;
    this.filename = filename;
    this.directory=directory;
    this.setBaseDirectory();  
    try {
         this.file = new FileInputStream(new File(this.filename));
         this.workbook = new HSSFWorkbook(file);
         this.sheet = workbook.getSheetAt(0);
    }
    catch (FileNotFoundException e) {
    e.printStackTrace(); } catch (IOException e) {     e.printStackTrace();
    }
  }

  public Excel(String directory,String[] entete) {

    this.entete = entete;
    this.directory=directory;
    this.setBaseDirectory();     
      
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

  public void attach(String filename) {
    this.filename = filename;
    Connexion.trace("1- attach -----","Fichier a attacher: ",""+filename);
    try {
         this.file = new FileInputStream(new File(this.filename));
         this.workbook = new HSSFWorkbook(file);
         this.sheet = workbook.getSheetAt(0);
    }
    catch (FileNotFoundException e) {
      Connexion.trace("2- attach -----","Fichier inexistant: ",""+filename);
    } catch (IOException e) {
      Connexion.trace("3- attach -----","Fichier inexistant: ",""+filename);
    }
  }
  
  public void attach_xlsx(String filename) {
        this.workbook_xlsx = new XSSFWorkbook();
        this.filename = filename;
  }  


  public void xl_open_update() {
    try {
    this.file = new FileInputStream(new File(filename));
    HSSFWorkbook workbook = new HSSFWorkbook(file);
    HSSFSheet sheet = workbook.getSheetAt(0);
    }
    catch (FileNotFoundException e) {
    e.printStackTrace(); } catch (IOException e) {     e.printStackTrace();
    }
  }

  public void xl_open_create(String mySheet) {

    this.workbook = new HSSFWorkbook();
    this.sheet = workbook.createSheet(mySheet);

    HSSFCellStyle cellStyle = null;
    this.cellStyle = this.workbook.createCellStyle();

    this.cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
    this.cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
    this.cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
    this.cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyle.setTopBorderColor(HSSFColor.BLACK.index);

  }
  
  public void xl_open_create_xlsx(String mySheet) {

    this.sheet_xlsx = this.workbook_xlsx.createSheet(mySheet);
/*
    this.cellStyle_xlsx = this.workbook_xlsx.createCellStyle();

    this.cellStyle_xlsx.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyle_xlsx.setBottomBorderColor(HSSFColor.BLACK.index);
    this.cellStyle_xlsx.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyle_xlsx.setLeftBorderColor(HSSFColor.BLACK.index);
    this.cellStyle_xlsx.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyle_xlsx.setRightBorderColor(HSSFColor.BLACK.index);
    this.cellStyle_xlsx.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyle_xlsx.setTopBorderColor(HSSFColor.BLACK.index);
*/
  }  

  public void xl_close_update() {
    try {
      this.file.close();
      FileOutputStream outFile =new FileOutputStream(new File(this.filename));
      workbook.write(outFile);
      outFile.close();       }
      catch (FileNotFoundException e) {
      e.printStackTrace(); } catch (IOException e) {     e.printStackTrace();
    }

  }

  public void xl_close_create() {
    try {
      FileOutputStream outFile =new FileOutputStream(new File(this.filename));
      workbook.write(outFile);
      outFile.close();       }
      catch (FileNotFoundException e) {
      e.printStackTrace(); } catch (IOException e) {     e.printStackTrace();
    }

  }
  
  public void xl_close_create_xlsx() {
    
       FileOutputStream fileOut;
       try {
         fileOut = new FileOutputStream(this.filename);
         this.workbook_xlsx.write(fileOut);
         fileOut.close();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }    

  }  

  public void xl_delete() {

    File repertoire = new File(this.Basedirectory);
    String [] listefichiers;
    int i;

    listefichiers=repertoire.list();
Connexion.trace("xl_delete-2------------","listefichiers.length",""+listefichiers.length);    
    for(i=0;i<listefichiers.length;i++){
      boolean resultMove=false;
      try{
        boolean resultatDelete= new File(this.Basedirectory+"/"+listefichiers[i]).delete();
Connexion.trace("xl_delete-3------------","resultatDelete[i]",""+resultatDelete);         
Connexion.trace("xl_delete-3------------","listefichiers[i]",""+listefichiers[i]);         
      }
      catch (Exception e)
      {
Connexion.trace("xl_delete-4------------","Echec",""+listefichiers[i]); 
e.printStackTrace();
      }

    }

  }


  public void xl_write(Row row, int numCell, String value) {

    if (value.length() > 32000)
      value = value.substring(0,32000);
    Cell cell = row.createCell(numCell);
    cell.setCellValue(value);

    cell.setCellStyle(this.cellStyle);

  }
  
  public void xl_write_xlsx(Row row, int numCell, String value) {

    if (value.length() > 32000)
      value = value.substring(0,32000);
    Cell cell = row.createCell(numCell);
    cell.setCellValue(value);

    cell.setCellStyle(this.cellStyle_xlsx);

  }  

  public void xl_write(Row row, int numCell, String value, int type) {

    if (value.length() > 32000)
      value = value.substring(0,32000);
    Cell cell = row.createCell( numCell);

    cell.setCellValue(Float.parseFloat(value));
    cell.setCellStyle(this.cellStyle);
    cell.setCellType(type);


  }
  
  public void xl_write_xlsx(Row row, int numCell, String value, int type) {

    if (value.length() > 32000)
      value = value.substring(0,32000);
    Cell cell = row.createCell( numCell);

    cell.setCellValue(Float.parseFloat(value));
    cell.setCellStyle(this.cellStyle_xlsx);
    cell.setCellType(type);


  }  
  
  public void setStyleDate(){
    this.cellStyleDate = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        this.cellStyleDate.setDataFormat(
        createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        
    this.cellStyleDate.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyleDate.setBottomBorderColor(HSSFColor.BLACK.index);
    this.cellStyleDate.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyleDate.setLeftBorderColor(HSSFColor.BLACK.index);
    this.cellStyleDate.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyleDate.setRightBorderColor(HSSFColor.BLACK.index);
    this.cellStyleDate.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyleDate.setTopBorderColor(HSSFColor.BLACK.index);        
  }
  
  public void setStyleDate_xlsx(){
    this.cellStyleDate = workbook_xlsx.createCellStyle();
        CreationHelper createHelper = workbook_xlsx.getCreationHelper();
        this.cellStyleDate.setDataFormat(
        createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
    /*  
    this.cellStyleDate.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyleDate.setBottomBorderColor(HSSFColor.BLACK.index);
    this.cellStyleDate.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyleDate.setLeftBorderColor(HSSFColor.BLACK.index);
    this.cellStyleDate.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyleDate.setRightBorderColor(HSSFColor.BLACK.index);
    this.cellStyleDate.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
    this.cellStyleDate.setTopBorderColor(HSSFColor.BLACK.index);     
        
    */
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
  
  public void xl_writeDate(Row row, int numCell, String value) {

      Cell cell = row.createCell( numCell);
      CellStyle cellStyleDate = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyleDate.setDataFormat(
        createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
    /*
    cellStyleDate.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
    cellStyleDate.setBottomBorderColor(HSSFColor.BLACK.index);
    cellStyleDate.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
    cellStyleDate.setLeftBorderColor(HSSFColor.BLACK.index);
    cellStyleDate.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
    cellStyleDate.setRightBorderColor(HSSFColor.BLACK.index);
    cellStyleDate.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
    cellStyleDate.setTopBorderColor(HSSFColor.BLACK.index);
    */
        cell.setCellStyle(cellStyleDate);
        try {
        cell.setCellValue(Utils.getNewDate(value));
        }
        catch (Exception e)
        {
            cell.setCellValue(" ");
        }
    

  }  

  public void xl_writeEntete(Row row, int numCell, String[] value) {
    HSSFCellStyle cellStyle = null;
    for (int i=numCell; i < numCell+value.length;i++)
    {
    if (value[i].length() > 32000)
      value[i] = value[i].substring(0,32000);
    Cell cell = row.createCell(i);
    cell.setCellValue(value[i]);

    cellStyle = this.workbook.createCellStyle();

    cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
    cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
    cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
    cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
    cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
    cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
    cellStyle.setTopBorderColor(HSSFColor.BLACK.index);


    cell.setCellStyle(cellStyle);
    }

  }
  
  public void xl_writeEntete_xlsx(Row row, int numCell, String[] value) {

    XSSFCellStyle cellStyle = null;
    for (int i=numCell; i < numCell+value.length;i++)
    {
        if (value[i] == null) continue;
        if (value[i].length() > 32000)
            value[i] = value[i].substring(0,32000);
    Cell cell = row.createCell(i);
    cell.setCellValue(value[i]);

    cellStyle = this.workbook_xlsx.createCellStyle();

    cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
    cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
    cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
    cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
    cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
    cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
    cellStyle.setTopBorderColor(HSSFColor.BLACK.index);


    cell.setCellStyle(cellStyle);
    }

  }  

    public static void main(String[] args) {
   
       //1. Créer un Document vide
       XSSFWorkbook wb = new XSSFWorkbook();
       //2. Créer une Feuille de calcul vide
       Sheet feuille = wb.createSheet("new sheet");
       //3. Créer une ligne et mettre qlq chose dedans
       Row row = feuille.createRow((short)0);
       //4. Créer une Nouvelle cellule
       Cell cell = row.createCell(0);
       //5. Donner la valeur
       cell.setCellValue(1.2);
   
       //Ajouter d'autre cellule avec différents type
       /*int*/row.createCell(1).setCellValue(3);
       /*char*/row.createCell(2).setCellValue('c');
       /*String*/row.createCell(3).setCellValue("joel");
       /*boolean*/row.createCell(4).setCellValue(false);

       FileOutputStream fileOut;
       try {
         fileOut = new FileOutputStream("D:/NetBeansProjects/poi Excel/writeXLS1.xlsx");
         wb.write(fileOut);
         fileOut.close();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }
    }  
    
   
}

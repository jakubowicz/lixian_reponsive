/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package PO; 

import OM.Attribut;
import accesbase.Connexion;
import General.Utils;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

import accesbase.ErrorSpecific;

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
public class ExcelImportPo {
    static Connexion myCnx;
    public int Annee;
    public Vector ListeLignePO = new Vector(10);
    public int nbEnreg=-1;


    String codeSujet=""; // 1- PROFEECIE_Code=0
    String etat=""; // 2- PROFEECIE_statutImpact=4;
    String idWebPo="";   // 3- PROFEECIE_numFiche=2;
    String Enveloppe= "";
    String nomProjet="";// 4- PROFEECIE_Sujet=1;
    // 5- Annee
    String service=""; // 6- PROFEECIE_structure=7;
    String part=""; // 7- PROFEECIE_Financement=8;
    String TypeCharge="Depense"; // 8-
    // 8- TypeCharge = Depense
    String descProjet= "";
    String risqueProjet= "";
    float charge= 0; // PROFEECIE_chargePrevue = 10;
    float chargeConsommee= 0; // PROFEECIE_chargeConsommee = 9;
    String gainProjet="";
    String Priorite= "";
    String macrost= "";
    String chefprojet= "";

    String dateEF= "";
    String dateMAQUETTE= "";
    String dateREAL= "";
    String dateMEP= "";
    
    public String directory = "";
    public String Basedirectory = "";    


  public ExcelImportPo() {
    Statement st =null;

    st = myCnx.Connect();
    this.Annee = Integer.parseInt(Utils.getYear(myCnx.nomBase, myCnx,  st));
    this.setBaseDirectory();
  }

  public ExcelImportPo(int Annee) {
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

    
    LignePO theLignePO =null;
    FileInputStream fileIn = null;
    Cell cell = null;

   
    String myDir = Utils.getCanonicalPath();
    //Connexion.trace("@01234---------------------------------","myDir",""+myDir);
    String filename ="";


     int Ligne = 2;
     filename = this.Basedirectory  + "/PO SIR.xlsx";
    try
    {
      // test pour svoir si c'est du xls ou du xslx/xslm
      fileIn = new FileInputStream(filename);

    XSSFWorkbook wb = new XSSFWorkbook (filename); 
     XSSFSheet sheet = null;

      
    int Colonne = 1;   
    int numOnglet = 0;
 
    sheet = wb.getSheetAt(numOnglet);  
    
    String Onglet = ""; 
    Onglet = sheet.getSheetName(); 
    
    XSSFRow row = sheet.getRow(Ligne);  
     cell = row.getCell(0); 
      while((row != null) &&(cell != null) &&(!row.getCell(0).toString().equals("")) )
        {
            
          // 0: Nom Projet                              --> this.nomProjet
          // 1: Descriptif du besoin fonctionnel SI     --> this.descProjet
          // 2: Macro-Domaine                           --> this.macrost
          // 3: ROI                                     --> this.gainProjet
          // 4: Date Mep                                --> this.dateMEP
          // 5: Pendule (O/N)                           --> this.etat
          // 6: Date T0                                 --> this.dateEB
          // 7: Stabilite                               --> this.risqueProjet
          // 8: Priorite                                --> this.Priorite
          // 9: Client                                  --> this.chefprojet
            
          int AnneeLignePo = this.Annee;

          theLignePO = new LignePO();  
          
          theLignePO.Annee = ""+this.Annee;
          
          try{
            theLignePO.nomProjet = row.getCell(0).toString();
            Connexion.trace(""+Ligne+":: -----------", "this.nomProjet",""+theLignePO.nomProjet);
          }catch (Exception e){}
          
          try{
            theLignePO.descProjet = row.getCell(1).toString();
            Connexion.trace(""+Ligne+":: -----------", "this.descProjet",""+theLignePO.descProjet);
          }catch (Exception e){}    
          
          try{
            theLignePO.macrost = row.getCell(2).toString();
            Connexion.trace(""+Ligne+":: -----------", "this.macrost",""+theLignePO.macrost);
          }catch (Exception e){}      
          
          try{
            theLignePO.gainProjet = row.getCell(3).toString();
            Connexion.trace(""+Ligne+":: -----------", "this.gainProjet",""+theLignePO.gainProjet);
          }catch (Exception e){}      
          
          try{
              Date myDate = row.getCell(4).getDateCellValue();
            theLignePO.dateMep = myDate.getDate() +  "/" + (myDate.getMonth() +1)+  "/" + (myDate.getYear() + 1900);
            Connexion.trace(""+Ligne+":: -----------", "this.dateMep",""+theLignePO.dateMep);
          }catch (Exception e){}   
          
          try{
            theLignePO.etat = row.getCell(5).toString();
            Connexion.trace(""+Ligne+":: -----------", "this.etat",""+theLignePO.etat);
          }catch (Exception e){}          
          
          try{
              Date myDate = row.getCell(6).getDateCellValue();
            theLignePO.dateEB = myDate.getDate() +  "/" + (myDate.getMonth() +1)+  "/" + (myDate.getYear() + 1900);            
            Connexion.trace(""+Ligne+":: -----------", "this.dateEB",""+theLignePO.dateEB);
          }catch (Exception e){}     
          
          try{
            theLignePO.risqueProjet = row.getCell(7).toString();
            Connexion.trace(""+Ligne+":: -----------", "this.risqueProjet",""+theLignePO.risqueProjet);
          }catch (Exception e){}    
          
          try{
            theLignePO.Priorite = row.getCell(8).toString();
            Connexion.trace(""+Ligne+":: -----------", "this.Priorite",""+theLignePO.Priorite);
          }catch (Exception e){}  
          
          try{
            theLignePO.chefprojet = row.getCell(9).toString();
            Connexion.trace(""+Ligne+":: -----------", "this.chefprojet",""+theLignePO.chefprojet);
          }catch (Exception e){}          
 
          try{
            theLignePO.Annee =  epure(row.getCell(10).toString());
            Connexion.trace(""+Ligne+":: -----------", "this.chefprojet",""+theLignePO.Annee);
          }catch (Exception e){}          
          
          try{
            theLignePO.chargeDisponible = Float.parseFloat(row.getCell(11).toString());
            Connexion.trace(""+Ligne+":: -----------", "this.chefprojet",""+theLignePO.chargeDisponible);
          }catch (Exception e){}                    

          try{
            theLignePO.service = theLignePO.macrost;
            Connexion.trace(""+Ligne+":: -----------", "this.service",""+theLignePO.service);
          }catch (Exception e){}

            //theLignePO.dump();
            this.ListeLignePO.addElement(theLignePO);       

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


    }

  public void xxxx (){

     String filename = "C:\\Users\\JJAKUBOW\\Documents\\NetBeansProjects\\Lixian\\build\\web\\doc\\po\\importExcel"+ "/aaaa.xls";;
         try
    {
		FileInputStream fis = new FileInputStream(filename);
                HSSFWorkbook workbook = new HSSFWorkbook(fis);
                HSSFSheet sheet = workbook.getSheetAt(0);
		//Create First Row

		fis.close();
		FileOutputStream fos =new FileOutputStream(filename);
	    workbook.write(fos);
            
	    fos.close();
		System.out.println("Done");      
    }
         catch (Exception e){}
  }
  
  public void yyyy (){

     String filename = "C:/Users/JJAKUBOW/Documents"+ "/aaaa.xlsx";;
         try
    {
		FileInputStream fis = new FileInputStream(filename);
                XSSFWorkbook workbook = new XSSFWorkbook (filename); 
                XSSFSheet sheet = workbook.getSheetAt(0);
		//Create First Row

		fis.close();
		FileOutputStream fos =new FileOutputStream(filename);
	    workbook.write(fos);
            
	    fos.close();
		System.out.println("Done");      
    }
         catch (Exception e){e.printStackTrace();}
         
         
  }  
  
    public void xl_write() {
        // -------------------------------------------- Connexion -------------------------------------------------//
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
        // ----------------------------------------------------------------------------------------------------------//
        
        // -------------------------------------------- OUverture fichier excel -------------------------------------//
     LignePO theLignePO =null;
     
    FileInputStream fileIn = null;
    FileOutputStream fileOut = null;
    Cell cell = null;

   
    String myDir = Utils.getCanonicalPath();
    //Connexion.trace("@01234---------------------------------","myDir",""+myDir);
    String filename ="";


    int Ligne = 2;
     filename = this.Basedirectory  + "/PO SIR.xlsx";
    try
    {
      // test pour svoir si c'est du xls ou du xslx/xslm
      //fileOut = new FileOutputStream(filename);

    fileIn = new FileInputStream(filename);
    XSSFWorkbook wb = new XSSFWorkbook (filename); 
     XSSFSheet sheet = null;

      
    int Colonne = 1;   
    int numOnglet = 0;
 
    sheet = wb.getSheetAt(numOnglet);  
    
    String Onglet = ""; 
    Onglet = sheet.getSheetName(); 
    
    //XSSFRow row = sheet.getRow(Ligne);  
    // cell = row.getCell(0);      

        // -------------------------------------------- Mise à jour fichier excel ------------------------------------//
          // 11: CODE interne                               --> CODE interne
          // 12: TYPE PO                                    --> TYPE PO
          // 13: Catégories                                 --> Catégories
          // 14: ST impactés                                --> ST impactés
          // 15: Impact SI                                  --> Impact SI
          // 16: 
          // 17: MOA                                        --> MOA
          // 18: this.dateEB --> this.dateMEP               --> (X)T4 (this.dateEB > T3 2014 && this.dateEB <= T4 2014) || (this.dateMEP < T1 2014)
          // 19: this.dateEB --> this.dateMEP               --> (X)T1 (this.dateEB < T2 2015 || this.dateMEP < T2 2015)
          // 20: this.dateEB --> this.dateMEP               --> (X)T2 (this.dateEB < T3 2015 || this.dateMEP < T3 2015)
          // 20: this.dateEB --> this.dateMEP               --> (X)T3 (this.dateEB < T4 2015 || this.dateMEP < T4 2015)
          // 20: this.dateEB --> this.dateMEP               --> (X)T4 (this.dateEB < T1 2016 || this.dateMEP < T2 2016)
          // 20: this.dateEB --> this.dateMEP               --> (X)T1 (this.dateEB < T2 2015 || this.dateMEP < T2 2015)
          // 20: this.dateEB --> this.dateMEP               --> (X)T2 (this.dateEB < T2 2015 || this.dateMEP < T2 2015)
          // 20: this.dateEB --> this.dateMEP               --> (X)T1 (this.dateEB < T2 2015 || this.dateMEP < T2 2015)
        
        // ------ Calcul du trimestre/ Annee de la dateEB
        // ------ deduction du n° de colonne EB associé
        
        // ------ Calcul du trimestre/ Annee de la dateMEP
        // ------ deduction du n° de colonne MEP associé        
        
        // ------ coloriser la plage dateEB/ dateMEP 
        
        // -------------------------------------------- Fermeture fichier excel -------------------------------------//
    fileIn.close();
    fileOut = new FileOutputStream(new File(filename));
    wb.write(fileOut);
    if (fileOut != null)
        fileOut.close();
         
    
        }
            catch (Exception e)
                {
                    e.printStackTrace();
                };       
        
        // -------------------------------------------- deconnexion -------------------------------------------------//
        myCnx.Unconnect(st);        
        
        
    }
  public ErrorSpecific maj_bd(String nomBase,Connexion myCnx, Statement st, String transaction) {
    String req="";
    String base = myCnx.getDate();
    int idMembre;
    String NomProjet, previousActivite= "";
    float semaine =0;

    String reqRefSt="";
    ErrorSpecific myError = new ErrorSpecific();

    if (this.ListeLignePO.size() == 0)
    {
      System.out.println("*** AUCUNE LIGNE PO LUES, Annee" + this.Annee);
      return myError;
    }

    req =  "DELETE FROM PlanOperationnelClient WHERE     (Annee = "+this.Annee+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"maj_bd",""+this.Annee);
    if (myError.cause == -1) return myError;

      for (int i = 0; i < this.ListeLignePO.size(); i++)
      {
        LignePO theLignePO = (LignePO)this.ListeLignePO.elementAt(i);
        
          // 0: Nom Projet                              --> this.nomProjet
          // 1: Descriptif du besoin fonctionnel SI     --> this.descProjet
          // 2: Macro-Domaine                           --> this.macrost
          // 3: ROI                                     --> this.gainProjet
          // 4: Date Mep                                --> this.dateMEP
          // 5: Pendule (O/N)                           --> this.etat
          // 6: Date T0                                 --> this.dateEB
          // 7: Stabilite                               --> this.risqueProjet
          // 8: Priorite                                --> this.Priorite
          // 9: Client                                  --> this.chefprojet
        
        String nomProjet = theLignePO.nomProjet;
        if (nomProjet != null) nomProjet = nomProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''"); else nomProjet="";
        
        String descProjet = theLignePO.descProjet;
        if (descProjet != null) descProjet = descProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''"); else descProjet="";    
        
        String macrost = theLignePO.macrost;
        if (macrost != null) macrost = macrost.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''"); else macrost="";   
        
        String gainProjet = theLignePO.gainProjet;
        if (gainProjet != null) gainProjet = gainProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''"); else gainProjet="";   
        
        String dateMep = theLignePO.dateMep;
        if (dateMep != null) dateMep = dateMep.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''"); else dateMep="";         
        //dateMep = "16/08/2015";

        String etat = theLignePO.etat;
        if (etat != null) etat = etat.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''"); else etat="";
        
        String dateEB = theLignePO.dateEB;
        if (dateEB != null) dateEB = dateEB.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''"); else dateEB="";        
        //dateEB = "16/03/2015";
        String service = theLignePO.service;
        if (service != null) service = service.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''"); else service="";
        
        String risqueProjet = theLignePO.risqueProjet;
        if (risqueProjet != null) risqueProjet = risqueProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''"); else risqueProjet="";
        
        String Priorite = theLignePO.Priorite;
        if (Priorite != null) Priorite = Priorite.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''"); else Priorite="";
        
        String chefprojet = theLignePO.chefprojet;
        if (chefprojet != null) chefprojet = chefprojet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''"); else chefprojet ="";        

        Calendar calendar = Calendar.getInstance();
        Annee = calendar.get(Calendar.YEAR);


          // 0: Nom Projet                              --> this.nomProjet
          // 1: Descriptif du besoin fonctionnel SI     --> this.descProjet
          // 2: Macro-Domaine                           --> this.macrost
          // 3: ROI                                     --> this.gainProjet
          // 4: Date Mep                                --> this.dateMEP
          // 5: Pendule (O/N)                           --> this.etat
          // 6: Date T0                                 --> this.dateEB
          // 7: Stabilite                               --> this.risqueProjet
          // 8: Priorite                                --> this.Priorite
          // 9: Client                                  --> this.chefprojet

          req =  "INSERT INTO PlanOperationnelClient  ";
          req+="(idWebPo,nomProjet, descProjet,Annee,macrost, gainProjet,dateMEP,etat, dateEF,Priorite,service,  chefprojet, risqueProjet, charge ) ";
          req+= "VALUES     ('";
          req+= ""+(i+1) +"', '";
          req+= nomProjet +"', '";
          req+= descProjet +"', '";
          req+= theLignePO.Annee +"', '";
          req+= macrost +"', '";
          req+= gainProjet +"', '";
          req+= dateMep +"', '";
          req+= etat +"', '";
          req+= dateEB +"', '";
          req+= Priorite +"', '";
          req+= theLignePO.service +"', '";
          req+= theLignePO.chefprojet +"', '";
          req+= risqueProjet +"', '";
          req+= theLignePO.chargeDisponible +"'";
          req+= ")";

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"maj_bd",""+this.Annee);
    if (myError.cause == -1) return myError;
      }

    return myError;

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


  public static void main(String[] args) {

    ExcelImportPo theExcelImputation = new ExcelImportPo(2015);
    //theExcelImputation.xl_read();
    //theExcelImputation.maj_bd();
    //theExcelImputation.xl_write();
    //theExcelImputation.xxxx();
    theExcelImputation.yyyy();


  }
}

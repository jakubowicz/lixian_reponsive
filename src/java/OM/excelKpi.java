/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package OM;

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
public class excelKpi {
    static Connexion myCnx;
    public int Annee;
    public Vector ListeLignePO = new Vector(10);



  public excelKpi() {
    Statement st =null;

    st = myCnx.Connect();
    this.Annee = Integer.parseInt(Utils.getYear(myCnx.nomBase, myCnx,  st));
  }

  public excelKpi(int Annee) {
    Statement st =null;

    st = myCnx.Connect();
    this.Annee = Annee;
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
    // filename = myDir + "/web/doc/kpi/KPI2.xlsm";
    //filename = "E:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TestCarto\\doc\\kpi\\KPI2.xlsm";
    //filename = "E:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\BaseCarto\\doc\\kpi\\KPI2.xlsm";
    //Connexion.trace("@01234---------------------------------","filename",""+filename);
    filename = "E:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\BaseCarto\\doc\\kpi\\KPI ref TBP_A 30062014-V1.xlsx";
    //filename = myDir + "/web/doc/kpi/KPI ref TBP_A 30062014-V1.xlsx";
 
    //int Ligne = 26;

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
    
    req = "delete FROM         OmSt where (idOm in (SELECT     idObjetMetier FROM ObjetMetier WHERE     (typeOM = 2)))";
    myCnx.ExecReq(st2, base, req);
 
    req =  "delete FROM  GraphOM where idOM in (select idObjetMetier from ObjetMetier WHERE     (typeOM = 2))";
    myCnx.ExecReq(st, base, req);

    req =  "delete FROM  RelationsOM where origineOM in (select idObjetMetier from ObjetMetier WHERE     (typeOM = 2)) OR  destinationOM in (select idObjetMetier from ObjetMetier WHERE     (typeOM = 2))    ";
    myCnx.ExecReq(st, base, req);
    
    req =  "delete from ObjetMetier WHERE     (typeOM = 2)";
    myCnx.ExecReq(st, base, req);

    
    int Ligne = 1;
    int Colonne = 1;   
 
for (int numOnglet = 0; numOnglet <= 4; numOnglet++ )
{
    sheet = wb.getSheetAt(numOnglet);  
    
    String Onglet = ""; 
    Onglet = sheet.getSheetName(); 
    Ligne = 1;
    XSSFRow row = sheet.getRow(Ligne);  
     cell = row.getCell(0); 
      while((row != null) &&(cell != null) )
        {
                     

         // Connexion.trace("","cell.toString()",cell.toString());
/*
          1- PROFEECIE_Code=0;                        -->
          PROFEECIE_Sujet=1;                       -->
          3- PROFEECIE_numFiche=2;                    --> idWebPo
          4- PROFEECIE_Impact=3;                      --> nomProjet
          2- PROFEECIE_statutImpact=4;                --> etat
          PROFEECIE_departement=5;                 -->
          PROFEECIE_service=6;                     --> service
          6- PROFEECIE_structure=7;                   -->
          7- PROFEECIE_Financement=8;                 -->
          PROFEECIE_chargeConsommee = 9;           --> chargeConsomee
          PROFEECIE_chargePrevue = 10;             --> charge

          5- Annee
 theExcelKpi.Annee
*/
           int colonne=0;
           
          String KPI = "";
          try{
            KPI = row.getCell(colonne++).toString() ;
          }catch (Exception e){}   

          String Constructeur = "";
          try{
            Constructeur = row.getCell(colonne++).toString() ;
          }catch (Exception e){}
          
          String Formule = "";
          try{
            Formule = row.getCell(colonne++).toString() ;
          }catch (Exception e){}    

          String Techno = "";
          try{
            Techno = row.getCell(colonne++).toString() ;
          }catch (Exception e){}   

          String GranulariteTemporel = "";
          try{
            GranulariteTemporel = row.getCell(colonne++).toString() ;
          }catch (Exception e){}   
          
          String GranulariteEquipement = "";
          try{
            GranulariteEquipement = row.getCell(colonne++).toString() ;
          }catch (Exception e){}        
          
          String Commentaires = "";
          try{
            Commentaires = row.getCell(colonne++).toString() ;
          }catch (Exception e){}             
          
          
        if (KPI != null) 
        {
            KPI = KPI.replaceAll("'","''"); 
            KPI = KPI.replaceAll("à","&agrave;");
        }
 
        if (Formule != null) 
        {
            Formule = Formule.replaceAll("'","''"); 
            Formule = Formule.replaceAll("§","&sect;").replaceAll("é","&eacute;").replaceAll("à","&agrave;").replaceAll("è","&egrave;");
        }
        
        if (Constructeur != null) Constructeur = Constructeur.replaceAll("'","''").replaceAll("é","&eacute;").replaceAll("à","&agrave;").replaceAll("è","&egrave;");

        if (Techno != null) Techno = Techno.replaceAll("'","''").replaceAll("é","&eacute;").replaceAll("à","&agrave;").replaceAll("è","&egrave;");
        
        
        if (GranulariteTemporel != null) GranulariteTemporel = GranulariteTemporel.replaceAll("'","''").replaceAll("é","&eacute;").replaceAll("à","&agrave;").replaceAll("è","&egrave;");
        
        if (Onglet != null) Onglet = Onglet.replaceAll("'","''").replaceAll("é","&eacute;").replaceAll("à","&agrave;").replaceAll("è","&egrave;");
        if (GranulariteEquipement != null) GranulariteEquipement = GranulariteEquipement.replaceAll("'","''").replaceAll("é","&eacute;").replaceAll("à","&agrave;").replaceAll("è","&egrave;");   
        
        if (Commentaires != null) Commentaires = Commentaires.replaceAll("'","''").replaceAll("é","&eacute;").replaceAll("à","&agrave;").replaceAll("è","&egrave;");   
        
        String sousFamille = "";
        
        //KPI = Constructeur.substring(0, 3)+Techno+":"+KPI;
        if (numOnglet == 0)
            sousFamille = "381";
        else if (numOnglet == 1)
            sousFamille = "382";
        else if (numOnglet == 2)
            sousFamille = "383";
        else if (numOnglet == 3)
            sousFamille = "384";
        else if (numOnglet == 4)
            sousFamille = "385";        

        String reqRefSt =  "INSERT INTO ObjetMetier  ( nomObjetMetier, package,famObjetMetier, typeOM, typeEtatObjetMetier, idAppliIcone, numStOrigine) ";
        reqRefSt += " VALUES     ('"+KPI+"', '"+sousFamille+"', '27', '2', '3', '6', "+Ligne+")";
        myCnx.ExecReq(st2, base, reqRefSt);
        
        req = "SELECT idObjetMetier FROM ObjetMetier WHERE     (typeOM = 2) AND (nomObjetMetier = '"+KPI+"') AND (package = "+sousFamille+ ")";
        rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);        
        
 int idObjetMetier = -1;
    try {
      while (rs2.next()) {

        idObjetMetier = rs2.getInt("idObjetMetier");
        
        }
    }
    catch (SQLException s) {s.getMessage();}

 
    req =  "delete from OmSt WHERE     (idOm = "+idObjetMetier+")";
    myCnx.ExecReq(st2, base, req);
    
    req = "INSERT INTO OmSt   (idOm, idSt, isOrigine) VALUES ("+idObjetMetier+","+1665+","+1+")";   
    myCnx.ExecReq(st2, base, req);
 
    req =  "delete from Attribut WHERE     (omAttribut = "+idObjetMetier+")";
    myCnx.ExecReq(st2, base, req);
    
    String nomTypeAttribut = "varchar(MAX)";

    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut, nomTypeAttribut) ";
    req += " VALUES     ('Onglet', '"+Onglet+"', '"+idObjetMetier+"','"+ nomTypeAttribut+"')";
    myCnx.ExecReq(st2, base, req);      
    
    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut, nomTypeAttribut) ";
    req += " VALUES     ('Constructeur', '"+Constructeur+"', '"+idObjetMetier+"','"+ nomTypeAttribut+"')";
    myCnx.ExecReq(st2, base, req);    
      
     req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut, nomTypeAttribut) ";
    req += " VALUES     ('Formule', '"+Formule+"', '"+idObjetMetier+"','"+ nomTypeAttribut+"')";
    myCnx.ExecReq(st2, base, req);     
      
    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut, nomTypeAttribut) ";
    req += " VALUES     ('Techno', '"+Techno+"', '"+idObjetMetier+"','"+ nomTypeAttribut+"')";
    myCnx.ExecReq(st2, base, req);   

    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut, nomTypeAttribut) ";
    req += " VALUES     ('Granularite Temporelle', '"+GranulariteTemporel+"', '"+idObjetMetier+"','"+ nomTypeAttribut+"')";
    myCnx.ExecReq(st2, base, req);  
    
    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut, nomTypeAttribut) ";
    req += " VALUES     ('Granularite Equipement', '"+GranulariteEquipement+"', '"+idObjetMetier+"','"+ nomTypeAttribut+"')";
    myCnx.ExecReq(st2, base, req);        
       
    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut, nomTypeAttribut) ";
    req += " VALUES     ('Commentaires', '"+Commentaires+"', '"+idObjetMetier+"','"+ nomTypeAttribut+"')";
    myCnx.ExecReq(st2, base, req);    
  
    Ligne++;
    row = sheet.getRow(Ligne);
    if (row != null) cell = row.getCell(0);    
      }        
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
    }



  public void xl_read2() {

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
    // filename = myDir + "/web/doc/kpi/KPI2.xlsm";
    //filename = "E:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\TestCarto\\doc\\kpi\\KPI2.xlsm";
    filename = "E:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\BaseCarto\\doc\\kpi\\KPI2.xlsm";
    //Connexion.trace("@01234---------------------------------","filename",""+filename);
 
    //int Ligne = 26;
    int Ligne = 1;
    int Colonne = 1;
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
    XSSFSheet sheet = wb.getSheetAt(0); 
    XSSFRow row = sheet.getRow(Ligne);   
    cell = row.getCell(0);
      //if (cell != null) idWebPo = cell.toString();
    
    req = "delete FROM         OmSt where (idOm in (SELECT     idObjetMetier FROM ObjetMetier WHERE     (typeOM = 2)))";
    myCnx.ExecReq(st2, base, req);
    
    req =  "delete from ObjetMetier WHERE     (typeOM = 2)";
    myCnx.ExecReq(st, base, req);
 
    
      //while((row != null) &&(cell != null) )
      while((row != null) &&(cell != null) )
        {

         // Connexion.trace("","cell.toString()",cell.toString());
/*
          1- PROFEECIE_Code=0;                        -->
          PROFEECIE_Sujet=1;                       -->
          3- PROFEECIE_numFiche=2;                    --> idWebPo
          4- PROFEECIE_Impact=3;                      --> nomProjet
          2- PROFEECIE_statutImpact=4;                --> etat
          PROFEECIE_departement=5;                 -->
          PROFEECIE_service=6;                     --> service
          6- PROFEECIE_structure=7;                   -->
          7- PROFEECIE_Financement=8;                 -->
          PROFEECIE_chargeConsommee = 9;           --> chargeConsomee
          PROFEECIE_chargePrevue = 10;             --> charge

          5- Annee
 theExcelKpi.Annee
*/

          String Profil = "";
          try{
            Profil = row.getCell(0).toString() ;
          }catch (Exception e){}

          String Constructeur = "";
          try{
            Constructeur = row.getCell(1).toString() ;
          }catch (Exception e){}
          
          String Type = "";
          try{
            Type = row.getCell(2).toString() ;
          }catch (Exception e){}
          
          String Onglet = "";
          try{
            Onglet = row.getCell(3).toString() ;
          }catch (Exception e){}
          
          String Graphe = "";
          try{
            Graphe = row.getCell(4).toString() ;
          }catch (Exception e){}
          
          String NomKPI = "";
          try{
            NomKPI = row.getCell(5).toString() ;
          }catch (Exception e){}    
          
          String KPI_IPOD = "";
          try{
            KPI_IPOD = row.getCell(6).toString() ;
          }catch (Exception e){}   
          
          String KPI_constructeur = "";
          try{
            KPI_constructeur = row.getCell(7).toString() ;
          }catch (Exception e){}   
          
          String XML_LinkKey = "";
          try{
            XML_LinkKey = row.getCell(8).toString() ;
          }catch (Exception e){}       
          
          
        if (NomKPI != null) 
        {
            NomKPI = NomKPI.replaceAll("'","''"); 
            NomKPI = NomKPI.replaceAll("à","&agrave;");
        }
        if (Profil != null) Profil = Profil.replaceAll("'","''").replaceAll("é","&eacute;").replaceAll("à","&agrave;").replaceAll("è","&egrave;");
        if (Constructeur != null) Constructeur = Constructeur.replaceAll("'","''").replaceAll("é","&eacute;").replaceAll("à","&agrave;").replaceAll("è","&egrave;");
        if (Type != null) Type = Type.replaceAll("'","''").replaceAll("é","&eacute;").replaceAll("à","&agrave;").replaceAll("è","&egrave;");
        if (Onglet != null) Onglet = Onglet.replaceAll("'","''").replaceAll("é","&eacute;").replaceAll("à","&agrave;").replaceAll("è","&egrave;");
        if (Graphe != null) Graphe = Graphe.replaceAll("'","''").replaceAll("é","&eacute;").replaceAll("à","&agrave;").replaceAll("è","&egrave;");   
        if (KPI_IPOD != null) 
        {
            KPI_IPOD = KPI_IPOD.replaceAll("'","''"); 
            KPI_IPOD = KPI_IPOD.replaceAll("§","&sect;").replaceAll("é","&eacute;").replaceAll("à","&agrave;").replaceAll("è","&egrave;");
        }
        if (KPI_constructeur != null) 
        {
            KPI_constructeur = KPI_constructeur.replaceAll("'","''"); 
            KPI_constructeur = KPI_constructeur.replaceAll("§","&sect;").replaceAll("é","&eacute;").replaceAll("à","&agrave;").replaceAll("è","&egrave;");
        }
        if (XML_LinkKey != null) XML_LinkKey = XML_LinkKey.replaceAll("'","''");
        
        String sousFamille = "";
        if (Constructeur.equals("Ericsson") && Profil.equals("SuperUser.NEW"))
        {
            sousFamille = "369";
            NomKPI = "ERC"+"-"+"NEW"+":"+NomKPI;
        }
        else if (Constructeur.equals("Ericsson") && Profil.equals("SuperUser.RNC"))
        {
            sousFamille = "371";
            NomKPI = "ERC"+"-"+"RNC"+":"+NomKPI;
        } 
        else if (Constructeur.equals("Huawei") && Profil.equals("SuperUser.New"))
        {
            sousFamille = "370";
            NomKPI = "HUA"+"-"+"NEW"+":"+NomKPI;            
        }        
        else if (Constructeur.equals("Huawei") && Profil.equals("SuperUser.RNC"))
        {
            sousFamille = "372";
            NomKPI = "HUA"+"-"+"RNC"+":"+NomKPI;            
        }        
        

        String reqRefSt =  "INSERT INTO ObjetMetier  ( nomObjetMetier, package,famObjetMetier, typeOM, typeEtatObjetMetier, idAppliIcone) ";
        reqRefSt += " VALUES     ('"+NomKPI+"', '"+sousFamille+"', '27', '2', '3', '6')";
        myCnx.ExecReq(st2, base, reqRefSt);
        
        req = "SELECT idObjetMetier FROM ObjetMetier WHERE     (typeOM = 2) AND (nomObjetMetier = '"+NomKPI+"') AND (package = "+sousFamille+ ")";
        rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);        
        
 int idObjetMetier = -1;
    try {
      while (rs2.next()) {

        idObjetMetier = rs2.getInt("idObjetMetier");
        
        }
    }
    catch (SQLException s) {s.getMessage();}

 
    req =  "delete from OmSt WHERE     (idOm = "+idObjetMetier+")";
    myCnx.ExecReq(st2, base, req);
    
    req = "INSERT INTO OmSt   (idOm, idSt, isOrigine) VALUES ("+idObjetMetier+","+1665+","+1+")";   
    myCnx.ExecReq(st2, base, req);
 
    req =  "delete from Attribut WHERE     (omAttribut = "+idObjetMetier+")";
    myCnx.ExecReq(st2, base, req);
    
    String nomTypeAttribut = "varchar(MAX)";
    
    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut, nomTypeAttribut) ";
    req += " VALUES     ('Profil', '"+Profil+"', '"+idObjetMetier+"','"+ nomTypeAttribut+"')";
    myCnx.ExecReq(st2, base, req);    
      
     req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut, nomTypeAttribut) ";
    req += " VALUES     ('Constructeur', '"+Constructeur+"', '"+idObjetMetier+"','"+ nomTypeAttribut+"')";
    myCnx.ExecReq(st2, base, req);     
      
    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut, nomTypeAttribut) ";
    req += " VALUES     ('Type', '"+Type+"', '"+idObjetMetier+"','"+ nomTypeAttribut+"')";
    myCnx.ExecReq(st2, base, req);   

    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut, nomTypeAttribut) ";
    req += " VALUES     ('Graphe', '"+Graphe+"', '"+idObjetMetier+"','"+ nomTypeAttribut+"')";
    myCnx.ExecReq(st2, base, req);  
    
    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut, nomTypeAttribut) ";
    req += " VALUES     ('Onglet', '"+Onglet+"', '"+idObjetMetier+"','"+ nomTypeAttribut+"')";
    myCnx.ExecReq(st2, base, req);        
       
    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut, nomTypeAttribut) ";
    req += " VALUES     ('KPI IPOD', '"+KPI_IPOD+"', '"+idObjetMetier+"','"+ nomTypeAttribut+"')";
    myCnx.ExecReq(st2, base, req);       
      
    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut, nomTypeAttribut) ";
    req += " VALUES     ('KPI constructeur', '"+KPI_constructeur+"', '"+idObjetMetier+"','"+ nomTypeAttribut+"')";
    myCnx.ExecReq(st2, base, req);       

    req =  "INSERT INTO Attribut  (  nomAttribut, descAttribut, omAttribut, nomTypeAttribut) ";
    req += " VALUES     ('XML LinkKey', '"+XML_LinkKey+"', '"+idObjetMetier+"','"+ nomTypeAttribut+"')";
    myCnx.ExecReq(st2, base, req);  

  
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
    }



  public static void main(String[] args) {

    excelKpi theExcelKpi = new excelKpi();
    theExcelKpi.xl_read();


  }
}

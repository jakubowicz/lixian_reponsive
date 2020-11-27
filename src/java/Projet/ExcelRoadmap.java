package Projet; 

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

import accesbase.Connexion;
import General.Utils;
import ST.*;
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



import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import java.io.FileInputStream;


import java.util.Vector;

import Organisation.Collaborateur;
import Composant.Excel;

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
public class ExcelRoadmap {
    String directory = "";
    String filename = "";    

  public ExcelRoadmap() {

  }
  
  public ExcelRoadmap(String directory, String filename) {
      this.directory = directory;
      this.filename = filename;

  }  


  public  void Go(String nomBase,Connexion myCnx, Statement st, Statement st2, String CC){
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    
    
    Roadmap theListeJalon = new Roadmap(-1);
    theListeJalon.getListeTypeJalons(nomBase,myCnx,  st);
    String[] entete = new String[17 + 2 * theListeJalon.ListeTypeJalons.size()] ;
    int num=0;
    entete[num] = "Projet";
    entete[++num] = "MOA";
    entete[++num] = "MOE";
    entete[++num] = "EtatRoadmap";
    
    for (int i=1; i < theListeJalon.ListeTypeJalons.size(); i++)
    {
        typeJalon theTypeJalon = (typeJalon) theListeJalon.ListeTypeJalons.elementAt(i);
        entete[++num] = theTypeJalon.nom;
        entete[++num] = theTypeJalon.nom + "_init";
    }
    entete[++num] = "IdFicheImpact";
    entete[++num] = "ChargePrevue";
    entete[++num] = "ChargeEngagee";
    entete[++num] = "chargeConsommee";
    entete[++num] = "docEB";
    entete[++num] = "GO";
    entete[++num] = "docDevis";
    entete[++num] = "nomTypeSi";
    entete[++num] = "Leader";
    entete[++num] = "Code";
    entete[++num] = "Brief";
    entete[++num] = "Suivi";
    entete[++num] = "dateSuivi";
    
    Excel theExport = new Excel(this.directory,entete);

    theExport.xl_delete();
    //myCnx.trace("1---------- delete","location",""+location);

    // --------------------------------------------------------------------------------------------------//

    // ----------------------------- Recupertation du nom du fichier export sous forme de timestamp ------//
    req = "SELECT     valeur FROM         Config WHERE     (nom = 'EXPORT-ROADMAP')";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String myDate="";
    try { rs.next();
            myDate= rs.getString("valeur");

      } catch (SQLException s) {s.getMessage();}
    // --------------------------------------------------------------------------------------------------//

    theExport.attach_xlsx(theExport.Basedirectory+"/" + this.filename); // nom du fichier excel horodate
   // myCnx.trace("2---------- attach","location",""+location+"/ExportRoadmap_"+myDate+".xls");

    //theExport.xl_open_update();
    theExport.xl_open_create_xlsx("ROADMAP"); // feuille de calcul
    theExport.setStyleDate_xlsx();

    // ---------------------------------------------R�cup�ration de l'ann�e courante ---------------------------
          Calendar calendar = Calendar.getInstance();
          int currentYear = calendar.get(Calendar.YEAR);
          int currentMonth = calendar.get(Calendar.MONTH) + 1;
// ----------------------------------------------------------------------------------------------------------

    //---------------------------- R�cup�ration de la liste des STs--------------------------------------------//
    SI theSI =null;
    theSI = new SI(-1); //Le SIR
    theSI.getListeProjets(myCnx.nomBase,myCnx,  st, currentYear, 4,5);

// -------------------------------------------------------------------------------------------------------//


    num = 0;
    theExport.sheet_xlsx.setColumnWidth(num, 256*48); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
    theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
    theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
    
    for (int i=1; i < theListeJalon.ListeTypeJalons.size(); i++)
        {
            typeJalon theTypeJalon = (typeJalon) theListeJalon.ListeTypeJalons.elementAt(i);           
            theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
            theExport.sheet_xlsx.setColumnWidth(++num, 256*12);              
        }
    theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
    theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
    theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
    theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
    theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
    theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
    theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
    theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
    theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
    theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
    theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
    theExport.sheet_xlsx.setColumnWidth(++num, 256*48);
    theExport.sheet_xlsx.setColumnWidth(++num, 256*12);
    theExport.sheet_xlsx.createFreezePane(1,1) ;    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.


   // myCnx.trace("3---------- theExport.sheet.createFreezePane","ST.ListeExcel.size()",""+ST.ListeExcel.size());

    Row row = theExport.sheet_xlsx.createRow(0);
    theExport.xl_writeEntete_xlsx(row,0,theExport.entete);
    
    for (int numRow=0; numRow < theSI.ListeProjets.size();numRow++)
    //for (int numRow=0; numRow < 569;numRow++)
    //for (int numRow=0; numRow < 1;numRow++)
    {
      String nomAppli="";
      Roadmap theRoadmap = (Roadmap)theSI.ListeProjets.elementAt(numRow);
      ST theSt = null;

      try{
        //c1.trace("01234----------","theRoadmap.idVersionSt",""+theRoadmap.idVersionSt);
        //if (true) return;
        theSt = new ST(Integer.parseInt(theRoadmap.idVersionSt) ,"idVersionSt");
        //theSt.getAllFromBd(c1.nomBase,c1,  st );
        //theSt.dump();
        theSt.getStAncrage(myCnx.nomBase,myCnx,  st);
        //if (true) return;
        if (theSt.StAncrage != null) nomAppli = theSt.StAncrage.nomSt;
      }
      catch (Exception e){}

      theRoadmap.Annee=currentYear;
      theRoadmap.getChargesFromPOByCC(myCnx.nomBase,myCnx,  st, CC);
      //theRoadmap.getLivrables(myCnx.nomBase,myCnx,  st);
      LignePO theLignePO = new LignePO(theRoadmap.idPO);
      theLignePO.Annee = ""+currentYear;
      theLignePO.TypeCharge = "OPEX";
      float ChargesEngagees=0;
      try{
        ChargesEngagees=theLignePO.getChargesEngagees(myCnx.nomBase,myCnx,  st);
      }
      catch (Exception e){
          ChargesEngagees = 0;
      }

      int numCol = 0;

      row = theExport.sheet_xlsx.createRow(numRow+1);

        //<%=%>	<%=%>	<%=%>	<%=%>	<%=%>	<%=%>	<%=%>	<%=%>	<%=%>
        theExport.xl_write_xlsx(row, numCol, theRoadmap.nomProjet.replaceAll("--","__"));
        theExport.xl_write_xlsx(row, ++numCol, "" + theRoadmap.MOA);
        theExport.xl_write_xlsx(row, ++numCol, "" + theRoadmap.MOE);
        theExport.xl_write_xlsx(row, ++numCol, "" + theRoadmap.EtatRoadmap);
        int numEssentiel = 1;
        for (int i=1; i < theListeJalon.ListeTypeJalons.size(); i++)
        {
            typeJalon theTypeJalon = (typeJalon) theListeJalon.ListeTypeJalons.elementAt(i);
            Jalon theJalon = theRoadmap.getJalonsByType(myCnx.nomBase,myCnx,  st, theTypeJalon.id);
            
            try{
                 theExport.xl_writeDateStyle(row, ++numCol, "" + theJalon.strDate);
               } 
            catch (Exception e){
                theExport.xl_write_xlsx(row, numCol, "");
            }   
            try{
                 theExport.xl_writeDateStyle(row, ++numCol, "" + theJalon.strDate_init);
               } 
            catch (Exception e){
                theExport.xl_write_xlsx(row, numCol, "");
            }             
               
          
        }        
      
        theExport.xl_write_xlsx(row, ++numCol, ""  + "" + theRoadmap.idPO, HSSFCell.CELL_TYPE_NUMERIC);
        //theExport.xl_write(row, ++numCol, ""  + "" + 1, HSSFCell.CELL_TYPE_NUMERIC);
        theExport.xl_write_xlsx(row, ++numCol, ("" + theRoadmap.totalChargeDisponible), HSSFCell.CELL_TYPE_NUMERIC);
        try{
        theExport.xl_write_xlsx(row, ++numCol, ("" + ChargesEngagees), HSSFCell.CELL_TYPE_NUMERIC);
        }
       catch (Exception e){
            //this.Restart( nomBase, myCnx,  st,  st2,  CC,  theExport,  numRow, "ChargesEngagees", theSI);  
            return;
      }  
        
        if (theRoadmap.docDevis != null && !theRoadmap.docDevis.equals("")) theRoadmap.docDevis = "X"; else theRoadmap.docDevis = "";
        if (theRoadmap.docEB != null && !theRoadmap.docEB.equals("")) theRoadmap.docEB = "X"; else theRoadmap.docEB = "";
        
        theExport.xl_write_xlsx(row, ++numCol, ("" + theRoadmap.totalChargeConsomee), HSSFCell.CELL_TYPE_NUMERIC);
        theExport.xl_write_xlsx(row, ++numCol, "" + theRoadmap.docEB);
        try{
        theExport.xl_writeDateStyle(row, ++numCol, "" + theRoadmap.dateT0MOE);
        }
       catch (Exception e){
            //this.Restart( nomBase, myCnx,  st,  st2,  CC,  theExport,  numRow, "dateT0MOE", theSI);    
            return;
      }

        theExport.xl_write_xlsx(row, ++numCol, "" + theRoadmap.docDevis);
        theExport.xl_write_xlsx(row, ++numCol, "" + theRoadmap.nomTypeSi);
        theExport.xl_write_xlsx(row, ++numCol, "" + theRoadmap.Leader);
        theExport.xl_write_xlsx(row, ++numCol, "" + theRoadmap.id, HSSFCell.CELL_TYPE_NUMERIC);
        theExport.xl_write_xlsx(row, ++numCol, "" + theRoadmap.idBrief, HSSFCell.CELL_TYPE_NUMERIC);
        
        Suivi theSuivi = theRoadmap.getLastSuivi(myCnx.nomBase,myCnx,  st);
        if ((theSuivi != null) && (theSuivi.description != null))
        {
            theExport.xl_write_xlsx(row, ++numCol, "" + Utils.stripHtml(theSuivi.description));
            theExport.xl_writeDateStyle(row, ++numCol, "" + theSuivi.dateSuivi);
        }
      
        Connexion.trace("@1234-------------","numRow",""+numRow+ "::" + theRoadmap.nomSt.replaceAll("--","__"));
    }
   // myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create_xlsx();
  
  }  
  

    public static void main(String[] args) {
        

    }   

}

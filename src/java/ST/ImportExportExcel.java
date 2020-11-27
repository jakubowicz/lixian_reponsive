package ST; 
import accesbase.Connexion;
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
public class ImportExportExcel {
    String directory = "";
    String filename = "";

  public ImportExportExcel(String directory, String filename) {
      this.directory = directory;
      this.filename = filename;

  }

  public void Go(String nomBase,Connexion myCnx, Statement st, Statement st2){

    ResultSet rs = null;

    String req="";
    String base = myCnx.getDate();


    String[] entete = {"nom","nbProjets","Hebergement","Domaine","isST","isAppli","typeClient","GestionIncidents","Criticite","Regions","ServiceMOA","nomMOA","TypeAppli","MacroSt","nomSI","nomEtat","nomTypeSi","Version","Clients","serviceExpl","ServiceMOE","nomMOE","nbLigCode","nbPtsFct","nbUtilisateurs","Date1ereMep","Datemep","dateKill","Externalisation","nomPhaseNP","nomSousPhaseNP","Creneau-Utilisation","derMajVersionSt","dateCreation","idVersionSt","Logo","DescriptionHTML","Logiciels","DescriptionText"};
    Excel theExport = new Excel(this.directory,entete);

    theExport.xl_delete();
    myCnx.trace("1---------- delete","location",""+theExport.Basedirectory);

    // --------------------------------------------------------------------------------------------------//

    // ----------------------------- Recupertation du nom du fichier export sous forme de timestamp ------//
    req = "SELECT     valeur FROM         Config WHERE     (nom = 'EXPORT-ST')";
    //myCnx.trace("2---------- attach","st",""+myCnx.URL);
   // myCnx.trace("2---------- attach","st",""+myCnx.nomBase);
    //myCnx.trace("2---------- attach","st",""+myCnx.driver);
    //myCnx.trace("2---------- attach","rs",""+rs);
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    //myCnx.trace("2---------- attach","rs",""+rs);
    String myDate="";
    try { rs.next();
            myDate= rs.getString("valeur");

      } catch (SQLException s) {s.getMessage();}
    // --------------------------------------------------------------------------------------------------//
//myCnx.trace("2---------- attach","location",""+location+"/ExportSt_"+myDate+".xls");
    theExport.attach_xlsx(theExport.Basedirectory+"/" + this.filename); // nom du fichier excel horodate
  //  myCnx.trace("2.1---------- attach","location",""+location+"/ExportSt_"+myDate+".xls");

    //theExport.xl_open_update();
    theExport.xl_open_create_xlsx("ST"); // feuille de calcul
    theExport.setStyleDate_xlsx();

    //---------------------------- R�cup�ration de la liste des STs--------------------------------------------//
    ST.getListeExcel(myCnx.nomBase,myCnx,  st , st2);
// -------------------------------------------------------------------------------------------------------//

    String toDay = Utils.getToDay(base,myCnx, st);
    int theYearRef= Utils.theYear;


    theExport.sheet_xlsx.setColumnWidth(0, 256*25); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(25, 256*12);
    theExport.sheet_xlsx.setColumnWidth(26, 256*12);
    theExport.sheet_xlsx.setColumnWidth(27, 256*12);
    theExport.sheet_xlsx.setColumnWidth(32, 256*12);
    theExport.sheet_xlsx.setColumnWidth(33, 256*12);
    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet_xlsx.createFreezePane(1,1) ;

    //myCnx.trace("3---------- theExport.sheet.createFreezePane","ST.ListeExcel.size()",""+ST.ListeExcel.size());

      Row row = theExport.sheet_xlsx.createRow(0);
      theExport.xl_writeEntete_xlsx(row,0,theExport.entete);
        
      
    for (int numRow=0; numRow < ST.ListeExcel.size();numRow++)
    //for (int numRow=0; numRow < 432;numRow++)
    //for (int numRow=0; numRow < 2;numRow++)
    {
      int numCol = 0;
      ST theST=(ST)ST.ListeExcel.elementAt(numRow);

      theST.getListeLogiciels(myCnx.nomBase,myCnx,st);

      String ListeLogiciels=theST.getListeLogiciels_STR(myCnx.nomBase,myCnx,st);
      //int nbProjets=theST.getNbProjetsByYear(myCnx.nomBase,myCnx,st, ""+theYearRef);
      int nbProjets=theST.getNbProjetsBySlidingYear(myCnx.nomBase,myCnx,st);
      String Hebergement=theST.getListeHbergement(myCnx.nomBase,myCnx,st);
      String Domaine=theST.getDomaineHebergement(myCnx.nomBase,myCnx,st);
      String Clients=theST.getListeClients(myCnx.nomBase,myCnx,st);
      String TypeClient=theST.getTypeClient(myCnx.nomBase,myCnx,st);


           // Corps
        row = theExport.sheet_xlsx.createRow(numRow +1);
        theExport.xl_write_xlsx(row, numCol, theST.nomSt);
        theExport.xl_write_xlsx(row, ++numCol, "" + nbProjets, HSSFCell.CELL_TYPE_NUMERIC);
        theExport.xl_write_xlsx(row, ++numCol, "" + Hebergement);
        theExport.xl_write_xlsx(row, ++numCol, "" + Domaine);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.isST, HSSFCell.CELL_TYPE_NUMERIC);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.isAppli, HSSFCell.CELL_TYPE_NUMERIC);
        theExport.xl_write_xlsx(row, ++numCol, "" + TypeClient);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.nomMaturite);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.Criticite);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.Region);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.nomServiceMOA);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.nomMOA);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.TypeApplication);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.nomMacrost);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.nomSi);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.nomEtat);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.TypeSi);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.versionRefVersionSt);
        theExport.xl_write_xlsx(row, ++numCol, "" + Clients);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.serviceExpl);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.nomServiceMOE);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.nomMOE);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.nbLigCodeVersionSt, HSSFCell.CELL_TYPE_NUMERIC);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.nbPtsFctVersionSt, HSSFCell.CELL_TYPE_NUMERIC);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.nbUtilVersionSt);
        //theExport.xl_write(row, ++numCol, "" + theST.dateMep1);
        theExport.xl_writeDateStyle(row, ++numCol, theST.dateMep1);
        //theExport.xl_write(row, ++numCol, "" + theST.dateMep);
        theExport.xl_writeDateStyle(row, ++numCol, theST.dateMep);
        //theExport.xl_write(row, ++numCol, "" + theST.dateKill);
        theExport.xl_writeDateStyle(row, ++numCol, theST.dateKill);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.Externalisation);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.nomPhaseNP);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.nomSousPhaseNP);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.nomFrequence);
        //theExport.xl_write(row, ++numCol, "" + theST.str_derMajVersionSt);
        theExport.xl_writeDateStyle(row, ++numCol, theST.str_derMajVersionSt);
        //theExport.xl_write(row, ++numCol, "" + theST.str_creationVersionSt);
        theExport.xl_writeDateStyle(row, ++numCol, theST.str_creationVersionSt);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.idVersionSt, HSSFCell.CELL_TYPE_NUMERIC);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.logo);
        theExport.xl_write_xlsx(row, ++numCol, "" + theST.descVersionSt);
        theExport.xl_write_xlsx(row, ++numCol, "" + ListeLogiciels);
        theExport.xl_write_xlsx(row, ++numCol, "" + Utils.stripHtml(theST.descVersionSt));
      
    }
   // myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create_xlsx();

    //myCnx.trace("5---------- xl_close_create","xl_close_create",""+theExport.filename);


  }
  
  public static void main(String[] args) {


  }



}

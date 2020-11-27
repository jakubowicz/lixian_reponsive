package Projet; 

import Projet.Brief;

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

import General.Utils;

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
public class ExcelBrief {
    String directory = "";
    String filename = "";    

  public ExcelBrief() {

  }
  
  public ExcelBrief(String directory, String filename) {
      this.directory = directory;
      this.filename = filename;

  }  

  public  void Go(String nomBase,Connexion myCnx, Statement st, Statement st2){
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    //st = myCnx.Connect();
    //st2 = myCnx.Connect();


    String[] entete = {"Id","Libelle","Etat","Createur","Service","Service Affecte","Usine Affectee","Date Creation","Date Reponse"};
    Excel theExport = new Excel(this.directory,entete);

    theExport.xl_delete();
    //myCnx.trace("1---------- delete","location",""+location);

    // --------------------------------------------------------------------------------------------------//

    // ----------------------------- Recupertation du nom du fichier export sous forme de timestamp ------//
    req = "SELECT     valeur FROM         Config WHERE     (nom = 'EXPORT-BRIEF')";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String myDate="";
    try { rs.next();
            myDate= rs.getString("valeur");

      } catch (SQLException s) {s.getMessage();}
    // --------------------------------------------------------------------------------------------------//

    theExport.attach_xlsx(theExport.Basedirectory+"/" + this.filename); // nom du fichier excel horodate

    //theExport.xl_open_update();
    theExport.xl_open_create_xlsx("BRIEF"); // feuille de calcul
    theExport.setStyleDate_xlsx();

    //---------------------------- R�cup�ration de la liste des Briefs--------------------------------------------//
    Brief.ListeBriefs.removeAllElements();
    Brief.getListeBriefs(myCnx.nomBase,myCnx,  st );
// -------------------------------------------------------------------------------------------------------//

    theExport.sheet_xlsx.setColumnWidth(0, 256*5); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(1, 256*64); // fixer la largeur de la 1ere colonne

    for (int i=2; i <= 8; i++)
    {
      theExport.sheet_xlsx.setColumnWidth(i, 256 * 15); // fixer la largeur de la 1ere colonne
    }

    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet_xlsx.createFreezePane(2,1) ;

    //myCnx.trace("3---------- theExport.sheet.createFreezePane","Brief.ListeBriefs.size()",""+Brief.ListeBriefs.size());
    Row row = theExport.sheet_xlsx.createRow(0);
    theExport.xl_writeEntete_xlsx(row,0,theExport.entete);
    
    for (int numRow=0; numRow < Brief.ListeBriefs.size();numRow++)
    //for (int numRow=0; numRow < 432;numRow++)
    //for (int numRow=0; numRow < 2;numRow++)
    {
      Brief theBrief=(Brief)Brief.ListeBriefs.elementAt(numRow);

      int numCol = 0;

      row = theExport.sheet_xlsx.createRow(numRow+1);

         //{"Id","Libell�","Etat","Cr�ateur","Service","Service Affect�","Usine Affect�e"};
        theExport.xl_write_xlsx(row, numCol, "" + theBrief.id);
        theExport.xl_write_xlsx(row, ++numCol, "" + theBrief.version);
        theExport.xl_write_xlsx(row, ++numCol, "" + theBrief.Etat);
        theExport.xl_write_xlsx(row, ++numCol, "" +  theBrief.LoginCreateur );
        theExport.xl_write_xlsx(row, ++numCol, "" +  theBrief.ServiceCreateur );
        theExport.xl_write_xlsx(row, ++numCol, "" +  theBrief.ServiceAffecte );
        theExport.xl_write_xlsx(row, ++numCol, "" +  theBrief.UsineAffecte );
        theExport.xl_writeDateStyle(row, ++numCol, "" +  theBrief.dateCreation );
        theExport.xl_writeDateStyle(row, ++numCol, "" +  theBrief.dateReponse );

      
    }
    //myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create_xlsx(); 

    //myCnx.trace("5---------- xl_close_create","xl_close_create",""+theExport.filename);      
  }  
  
  public static void main(String[] args) {


  }



}

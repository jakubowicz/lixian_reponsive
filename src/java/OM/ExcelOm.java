package OM; 

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
public class ExcelOm {
    String directory = "";
    String filename = "";    

  public ExcelOm() {

  }
  
  public ExcelOm(String directory, String filename) {
      this.directory = directory;
      this.filename = filename;

  }  

  public void Go(String nomBase,Connexion myCnx, Statement st, Statement st2){
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    //st = myCnx.Connect();
    //st2 = myCnx.Connect();


    String[] entete = {"nom","Famille","Proprietaire","Definition"};
    Excel theExport = new Excel(this.directory,entete);


    theExport.xl_delete();
    //myCnx.trace("1---------- delete","location",""+location);

    // --------------------------------------------------------------------------------------------------//

    // ----------------------------- Recupertation du nom du fichier export sous forme de timestamp ------//
    req = "SELECT     valeur FROM         Config WHERE     (nom = 'EXPORT-OM')";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String myDate="";
    try { rs.next();
            myDate= rs.getString("valeur");

      } catch (SQLException s) {s.getMessage();}
    // --------------------------------------------------------------------------------------------------//

    theExport.attach_xlsx(theExport.Basedirectory+"/" + this.filename); // nom du fichier excel horodate
    //myCnx.trace("2---------- attach","location",""+location+"/ExportOm_"+myDate+".xls");

    //theExport.xl_open_update();
    theExport.xl_open_create_xlsx("OM"); // feuille de calcul
    theExport.setStyleDate_xlsx();

    //---------------------------- R�cup�ration de la liste des OM--------------------------------------------//
    OM.getListeOM(myCnx.nomBase,myCnx,  st );
// -------------------------------------------------------------------------------------------------------//


    theExport.sheet_xlsx.setColumnWidth(0, 256*48); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(3, 256*107); // fixer la largeur de la 1ere colonne
    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet_xlsx.createFreezePane(1,1) ;

    //myCnx.trace("3---------- theExport.sheet.createFreezePane","OM.ListeOM.size()",""+OM.ListeOM.size());

    Row row = theExport.sheet_xlsx.createRow(0);
    theExport.xl_writeEntete_xlsx(row,0,theExport.entete);
    
    for (int numRow=0; numRow < OM.ListeOM.size();numRow++)
    //for (int numRow=0; numRow < 432;numRow++)
    //for (int numRow=0; numRow < 2;numRow++)
    {
      OM theOM=(OM)OM.ListeOM.elementAt(numRow);

      int numCol = 0;

      row = theExport.sheet_xlsx.createRow(numRow+1);

              //<%=theOM.nomProprietaire%>	<%=theOM.defObjetMetier%>
        theExport.xl_write_xlsx(row, numCol, "" + theOM.nomObjetMetier);
        theExport.xl_write_xlsx(row, ++numCol, "" + theOM.Famille);
        theExport.xl_write_xlsx(row, ++numCol, "" + theOM.nomProprietaire);
        theExport.xl_write_xlsx(row, ++numCol, "" +  Utils.stripHtml(theOM.defObjetMetier) );
      
    }
    myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create_xlsx();

    myCnx.trace("5---------- xl_close_create","xl_close_create",""+theExport.filename);      
  }  
  
  public static void main(String[] args) {

 
  }



}

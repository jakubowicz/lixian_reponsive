package Projet; 

import Projet.Brief;
import PO.LignePO;

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
public class ExcelDevis {
    String directory = "";
    String filename = "";    

  public ExcelDevis() {

  }
  
  public ExcelDevis(String directory, String filename) {
      this.directory = directory;
      this.filename = filename;

  }  

  
    public  void Go(String nomBase,Connexion myCnx, Statement st, Statement st2){
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();

    Devis.ListeDevis.removeAllElements();

    String[] entete = {"Code","ST", "Projet","Etat", "Createur","MOE","MOA","dateSoumission","dateGO_MOA","dateGO_GAP","Prev Enveloppe","Prev Internes","Prev Prest UO","Prev Forfait","Conso Respire","Conso Respirare"};
    Excel theExport = new Excel(this.directory,entete);

    theExport.xl_delete();
    //myCnx.trace("1---------- delete","location",""+location);

    // --------------------------------------------------------------------------------------------------//

    // ----------------------------- Recupertation du nom du fichier export sous forme de timestamp ------//
    req = "SELECT     valeur FROM         Config WHERE     (nom = 'EXPORT-DEVIS')";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String myDate="";
    try { rs.next();
            myDate= rs.getString("valeur");

      } catch (SQLException s) {s.getMessage();}
    // --------------------------------------------------------------------------------------------------//

    theExport.attach_xlsx(theExport.Basedirectory+"/" + this.filename); // nom du fichier excel horodate

    //theExport.xl_open_update();
    theExport.xl_open_create_xlsx("DEVIS"); // feuille de calcul
    theExport.setStyleDate_xlsx();

    //---------------------------- R�cup�ration de la liste des Briefs--------------------------------------------//
    Devis.getListeDevis(myCnx.nomBase,myCnx,  st );
// -------------------------------------------------------------------------------------------------------//

    theExport.sheet_xlsx.setColumnWidth(0, 256*8); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(1, 256*15); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(2, 256*50); // fixer la largeur de la 1ere colonne

    for (int i=3; i <= 9; i++)
    {
      theExport.sheet_xlsx.setColumnWidth(i, 256 * 14); // fixer la largeur de la 1ere colonne
    }

    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet_xlsx.createFreezePane(2,1) ;

    //myCnx.trace("3---------- theExport.sheet.createFreezePane","Devis.ListeDevis.size()",""+Devis.ListeDevis.size());

        
    Row row = theExport.sheet_xlsx.createRow(0);
    theExport.xl_writeEntete_xlsx(row,0,theExport.entete);
    for (int numRow=1; numRow < Devis.ListeDevis.size();numRow++)
    //for (int numRow=0; numRow < 432;numRow++)
    //for (int numRow=0; numRow < 2;numRow++)
    {
        float PrevEnveloppe = 0;
        float PrevInternes = 0;
        float PrevPrestUO = 0;
        float PrevForfait = 0;
        float ConsoEnveloppe = 0;        
      Devis theDevis=(Devis)Devis.ListeDevis.elementAt(numRow);
      theDevis.getLignesPO(myCnx.nomBase,myCnx,  st, "OPEX");
      for (int j=0; j < theDevis.ListeLignePO_OPEX.size(); j++)
      {
          LignePO theLignePO = (LignePO)theDevis.ListeLignePO_OPEX.elementAt(j);
          PrevInternes+=theLignePO.chargePrelevee;
          PrevEnveloppe+=theLignePO.chargeDisponible;
      }
      theDevis.getLignesPO(myCnx.nomBase,myCnx,  st, "CAPEX");
      for (int j=0; j < theDevis.ListeLignePO_CAPEX.size(); j++)
      {
          LignePO theLignePO = (LignePO)theDevis.ListeLignePO_CAPEX.elementAt(j);
          PrevPrestUO+=theLignePO.chargePrelevee;
      }      
      //theDevis.getLignesPO(myCnx.nomBase,myCnx,  st, "CAPEX_EXTERNE");
      theDevis.getLignesPO(myCnx.nomBase,myCnx,  st, "CAPEX_EXTERNE");
      for (int j=0; j < theDevis.ListeLignePO_CAPEX_EXTERNE.size(); j++)
      {
          LignePO theLignePO = (LignePO)theDevis.ListeLignePO_CAPEX_EXTERNE.elementAt(j);
          PrevForfait+=theLignePO.chargePrelevee;
      }      
      
      int numCol = 0;

      row = theExport.sheet_xlsx.createRow(numRow);

      if (numRow == 0) // Entete
        theExport.xl_writeEntete_xlsx(row,numCol,theExport.entete);
      else             // Corps
      {
         //{"Code","ST", "Projet","Etat", "Cr�ateur","MOE","MOA","dateSoumission","dateGO_MOA","dateGO_GOUVERNANCE"};
        theExport.xl_write_xlsx(row, numCol, "" + theDevis.idRoadmap);
        theExport.xl_write_xlsx(row, ++numCol, "" + theDevis.nomSt);
        theExport.xl_write_xlsx(row, ++numCol, "" + theDevis.nomProjet);
        theExport.xl_write_xlsx(row, ++numCol, "" + theDevis.Etat);
        theExport.xl_write_xlsx(row, ++numCol, "" +  theDevis.LoginCreateur );
        theExport.xl_write_xlsx(row, ++numCol, "" +  theDevis.serviceMOE );
        theExport.xl_write_xlsx(row, ++numCol, "" +  theDevis.serviceMOA );
        theExport.xl_writeDateStyle(row, ++numCol, "" +  theDevis.dateSoumission );
        theExport.xl_writeDateStyle(row, ++numCol, "" +  theDevis.dateGO_MOA );
        theExport.xl_writeDateStyle(row, ++numCol, "" +  theDevis.dateGO_GOUVERNANCE );
//"Prev Enveloppe","Prev Internes","Prev Prest UO","Prev Forfait","Conso Enveloppe","Conso Respire","Conso Respirare"

        float ConsoRespire = theDevis.chargeConsommeInterne;
        float ConsoRespirare = theDevis.chargeConsommeForfait;
        theExport.xl_write_xlsx(row, ++numCol, ("" + PrevEnveloppe), HSSFCell.CELL_TYPE_NUMERIC);
        theExport.xl_write_xlsx(row, ++numCol, ("" + PrevInternes), HSSFCell.CELL_TYPE_NUMERIC);
        theExport.xl_write_xlsx(row, ++numCol, ("" + PrevPrestUO), HSSFCell.CELL_TYPE_NUMERIC);
        theExport.xl_write_xlsx(row, ++numCol, ("" + PrevForfait), HSSFCell.CELL_TYPE_NUMERIC);
        //theExport.xl_write(row, ++numCol, ("" + ConsoEnveloppe), HSSFCell.CELL_TYPE_NUMERIC);
        theExport.xl_write_xlsx(row, ++numCol, ("" + ConsoRespire), HSSFCell.CELL_TYPE_NUMERIC);
        theExport.xl_write_xlsx(row, ++numCol, ("" + ConsoRespirare), HSSFCell.CELL_TYPE_NUMERIC);
      }
    }
    //myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create_xlsx();

    //myCnx.trace("5---------- xl_close_create","xl_close_create",""+theExport.filename);
        
    }
    
  public static void main(String[] args) {


  }



}

package Projet; 

import General.Utils;

import java.util.Vector;
import PO.LignePO;
import accesbase.Connexion;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import accesbase.*;
import accesbase.ErrorSpecific;

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

import Composant.Excel;

import java.util.Date;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

import accesbase.ErrorSpecific;

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
public class Devis {
  private String req="";

  public int id;
  public int idRoadmap;
  public String nomSt;
  public String nomProjet;
  public String description;
  public String Restrictions;
  public String Risques;
  public String Reponse="";
  public int idEtat = -1;
  public String Etat ="";
  
  public int typeJalon = -1;
  public String type="";

  public String serviceMOE="";
  public String serviceMOA="";

  public String LoginCreateur="";

  public Date DdateSoumission = null;
  public String dateSoumission = "";
  public Date DdateGO_MOA = null;
  public String dateGO_MOA = "";
  public Date DdateGO_GOUVERNANCE = null;
  public String dateGO_GOUVERNANCE = "";

  public Vector ListeLignePO_OPEX = new Vector(10);
  public Vector ListeLignePO_CAPEX = new Vector(10);
  public Vector ListeLignePO_CAPEX_EXTERNE = new Vector(10);

  public static Vector ListeDevis = new Vector(10);

  public Jalon JalonUAT=null;
  public Jalon JalonPROD=null;
  public Vector ListeJalons = new Vector(10);

  public Vector ListeBesoins = new Vector(10);

  public float chargesOpexEngage = 0;
  public float chargesOpexDispo = 0;
  public float chargesOpexPreleve = 0;

  public float chargesCapexEngage = 0;
  public float chargesCapexDispo = 0;
  public float chargesCapexPreleve = 0;

  public float chargesCapexExterneEngage = 0;
  public float chargesCapexExterneDispo = 0;
  public float chargesCapexExternePreleve = 0;
  
  public float chargeConsommeForfait = 0;
  public float chargeConsommeInterne = 0;

  public int idServiceCreateur=-1;
  
  public static Vector ListeJalons_static = new Vector(10);

  public Devis(int idRoadmap) {
    this.idRoadmap=idRoadmap;
    this.type="Roadmap";

  }
  
  public Devis(int idRoadmap, String type) {
    if (type.equals("Roadmap"))
    {
        this.idRoadmap=idRoadmap;
    }
    else
    {
        this.id=idRoadmap;
    } 
    this.type = type;

  }  

  public String getAllFromBd2(String nomBase,Connexion myCnx, Statement st ){
    String oldversion ="";
    String version="";
    String description="";
    String Etat="";
    String Panier="";
    String idPO="";

    String Ordre="";

    String dateMep="";

    int Annee;
    int idEquipe=-1;


    ResultSet rs;

if (this.type.equals("Roadmap"))  
{
    req="SELECT     Devis.id, Devis.idRoadmap, Devis.Restrictions, Devis.Risques, Devis.Reponse, Devis.idEtat, St.nomSt, Roadmap.version, Devis.description, Devis.dateSoumission, Devis.dateGO_MOA, ";
    req+= "                      Devis.dateGO_GOUVERNANCE, Devis.LoginCreateur, Devis.idServiceCreateur, typeEtatDevis.nom AS nomEtat, Devis.typeJalon, Roadmap.idJalon";
    req+= " FROM         Devis INNER JOIN";
    req+= "                       Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap AND Devis.typeJalon = Roadmap.idJalon INNER JOIN";
    req+= "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+= "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+= "                       typeEtatDevis ON Devis.idEtat = typeEtatDevis.id";
    req+= " WHERE     Devis.idRoadmap = "+this.idRoadmap;
}
else if (this.type.equals("Devis")) 
{
    req="SELECT     Devis.id, Devis.idRoadmap, Devis.Restrictions, Devis.Risques, Devis.Reponse, Devis.idEtat, St.nomSt, Roadmap.version, Devis.description, Devis.dateSoumission, Devis.dateGO_MOA, ";
    req+= "                      Devis.dateGO_GOUVERNANCE, Devis.LoginCreateur, Devis.idServiceCreateur, typeEtatDevis.nom AS nomEtat, Devis.typeJalon, Roadmap.idJalon";
    req+= " FROM         Devis INNER JOIN";
    req+= "                       Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+= "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+= "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+= "                       typeEtatDevis ON Devis.idEtat = typeEtatDevis.id";
    req+= " WHERE     Devis.id = "+this.id;
}    

    rs = myCnx.ExecReq(st, nomBase, req);
    int nbRoadmap = 0;
    java.util.Date theDate;

    java.util.Date DdateMep;

    try {
      while (rs.next()) {

        this.id = rs.getInt("id");
        this.idRoadmap = rs.getInt("idRoadmap");
        this.Restrictions = rs.getString("Restrictions");
        this.Risques = rs.getString("Risques");
        if ((this.Risques == null) ||(this.Risques.equals("null"))) this.Risques="";
        this.Reponse = rs.getString("Reponse");
        if ((this.Reponse == null) ||(this.Reponse.equals("null"))) this.Reponse="";

        this.idEtat = rs.getInt("idEtat");
        this.nomProjet = rs.getString("nomSt") + "-" + rs.getString("version");

        this.description = rs.getString("description");
        if ((this.description == null) ||(this.description.equals("null"))) this.description="";

        DdateSoumission = rs.getDate("dateSoumission");
        if (DdateSoumission != null)
          dateSoumission = ""+DdateSoumission.getDate()+"/"+(DdateSoumission.getMonth()+1) + "/"+(DdateSoumission.getYear() + 1900);
         else
          dateSoumission = "";

        DdateGO_MOA = rs.getDate("dateGO_MOA");
        if (DdateGO_MOA != null)
          dateGO_MOA = ""+DdateGO_MOA.getDate()+"/"+(DdateGO_MOA.getMonth()+1) + "/"+(DdateGO_MOA.getYear() + 1900);
         else
          dateGO_MOA = "";

        DdateGO_GOUVERNANCE = rs.getDate("dateGO_GOUVERNANCE");
        if (DdateGO_GOUVERNANCE != null)
          dateGO_GOUVERNANCE = ""+DdateGO_GOUVERNANCE.getDate()+"/"+(DdateGO_GOUVERNANCE.getMonth()+1) + "/"+(DdateGO_GOUVERNANCE.getYear() + 1900);
         else
          dateGO_GOUVERNANCE = "";

        this.LoginCreateur= rs.getString("LoginCreateur");
        this.idServiceCreateur= rs.getInt("idServiceCreateur");
        this.Etat= rs.getString("nomEtat");
        
        this.typeJalon = rs.getInt("typeJalon");
      }
    }

        catch (Exception s) {
          s.getMessage();
          return req;
        }

        return "OK";

  }
  public String getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    String oldversion ="";
    String version="";
    String description="";
    String Etat="";
    String Panier="";
    String idPO="";

    String Ordre="";

    String dateMep="";

    int Annee;
    int idEquipe=-1;


    ResultSet rs;

if (this.type.equals("Roadmap"))  
{
    req="SELECT     Devis.id, Devis.idRoadmap, Devis.Restrictions, Devis.Risques, Devis.Reponse, Devis.idEtat, St.nomSt, Roadmap.version, Devis.description, Devis.dateSoumission, Devis.dateGO_MOA,";
    req+= "                       Devis.dateGO_GOUVERNANCE, Devis.LoginCreateur, Devis.idServiceCreateur, typeEtatDevis.nom AS nomEtat, Devis.typeJalon, Roadmap.idJalon";
    req+= " FROM         typeEtatDevis INNER JOIN";
    req+= "                       Devis ON typeEtatDevis.id = Devis.idEtat LEFT OUTER JOIN";
    req+= "                       VersionSt INNER JOIN";
    req+= "                       Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt INNER JOIN";
    req+= "                       St ON VersionSt.stVersionSt = St.idSt ON Devis.idRoadmap = Roadmap.idRoadmap";
    req+= " WHERE     Devis.idRoadmap = "+this.idRoadmap;
    req+= " AND     Devis.typeJalon = "+this.typeJalon;
}
else if (this.type.equals("Devis")) 
{
    req="SELECT     Devis.id, Devis.idRoadmap, Devis.Restrictions, Devis.Risques, Devis.Reponse, Devis.idEtat, St.nomSt, Roadmap.version, Devis.description, Devis.dateSoumission, Devis.dateGO_MOA,";
    req+= "                       Devis.dateGO_GOUVERNANCE, Devis.LoginCreateur, Devis.idServiceCreateur, typeEtatDevis.nom AS nomEtat, Devis.typeJalon, Roadmap.idJalon";
    req+= " FROM         typeEtatDevis INNER JOIN";
    req+= "                       Devis ON typeEtatDevis.id = Devis.idEtat LEFT OUTER JOIN";
    req+= "                       VersionSt INNER JOIN";
    req+= "                       Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt INNER JOIN";
    req+= "                       St ON VersionSt.stVersionSt = St.idSt ON Devis.idRoadmap = Roadmap.idRoadmap";
    req+= " WHERE     Devis.id = "+this.id;
}    

    rs = myCnx.ExecReq(st, nomBase, req);
    int nbRoadmap = 0;
    java.util.Date theDate;

    java.util.Date DdateMep;

    try {
      while (rs.next()) {

        this.id = rs.getInt("id");
        this.idRoadmap = rs.getInt("idRoadmap");
        this.Restrictions = rs.getString("Restrictions");
        this.Risques = rs.getString("Risques");
        if ((this.Risques == null) ||(this.Risques.equals("null"))) this.Risques="";
        this.Reponse = rs.getString("Reponse");
        if ((this.Reponse == null) ||(this.Reponse.equals("null"))) this.Reponse="";

        this.idEtat = rs.getInt("idEtat");
        this.nomProjet = rs.getString("nomSt") + "-" + rs.getString("version");

        this.description = rs.getString("description");
        if ((this.description == null) ||(this.description.equals("null"))) this.description="";

        DdateSoumission = rs.getDate("dateSoumission");
        if (DdateSoumission != null)
          dateSoumission = ""+DdateSoumission.getDate()+"/"+(DdateSoumission.getMonth()+1) + "/"+(DdateSoumission.getYear() + 1900);
         else
          dateSoumission = "";

        DdateGO_MOA = rs.getDate("dateGO_MOA");
        if (DdateGO_MOA != null)
          dateGO_MOA = ""+DdateGO_MOA.getDate()+"/"+(DdateGO_MOA.getMonth()+1) + "/"+(DdateGO_MOA.getYear() + 1900);
         else
          dateGO_MOA = "";

        DdateGO_GOUVERNANCE = rs.getDate("dateGO_GOUVERNANCE");
        if (DdateGO_GOUVERNANCE != null)
          dateGO_GOUVERNANCE = ""+DdateGO_GOUVERNANCE.getDate()+"/"+(DdateGO_GOUVERNANCE.getMonth()+1) + "/"+(DdateGO_GOUVERNANCE.getYear() + 1900);
         else
          dateGO_GOUVERNANCE = "";

        this.LoginCreateur= rs.getString("LoginCreateur");
        this.idServiceCreateur= rs.getInt("idServiceCreateur");
        this.Etat= rs.getString("nomEtat");
        
        this.typeJalon = rs.getInt("typeJalon");
      }
    }

        catch (Exception s) {
          s.getMessage();
          return req;
        }

        return "OK";

  }
  public String getCharges(String nomBase,Connexion myCnx, Statement st, String TypeCharge){
    ResultSet rs;

    req="SELECT     DevisBudget.ChargePrelevee, DevisBudget.ChargeDispo, DevisBudget.Type";
    req+=" FROM         Devis INNER JOIN";
    req+="                       DevisBudget ON Devis.id = DevisBudget.idDevis";
    req+=" WHERE     (Devis.idRoadmap = "+this.idRoadmap+") AND (DevisBudget.Type = '"+TypeCharge+"')";
    req+=" ORDER BY Devis.id DESC";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {

        if (TypeCharge.equals("OPEX"))
        {
          this.chargesOpexPreleve= rs.getFloat("ChargePrelevee");
          this.chargesOpexDispo= rs.getFloat("ChargeDispo");
        }
        else if (TypeCharge.equals("CAPEX"))
        {
          this.chargesCapexPreleve= rs.getFloat("ChargePrelevee");
          this.chargesCapexDispo= rs.getFloat("ChargeDispo");
        }
        else if (TypeCharge.equals("CAPEX_EXTERNE"))
        {
          this.chargesCapexExternePreleve= rs.getFloat("ChargePrelevee");
          this.chargesCapexExterneDispo= rs.getFloat("ChargeDispo");
        }
      }
    }
    catch (Exception s) {
      s.getMessage();
      return req;
    }
    return "OK";

  }
  
  public String getLignesPO(String nomBase,Connexion myCnx, Statement st, String TypeCharge){
    ResultSet rs;
    if (TypeCharge.equals("OPEX"))
    {
      this.chargesOpexEngage= 0;
      this.chargesOpexDispo = 0;
    }
    else if (TypeCharge.equals("CAPEX"))
    {
      this.chargesCapexEngage= 0;
      this.chargesCapexDispo = 0;
    }
    else if (TypeCharge.equals("CAPEX_EXTERNE"))
    {
      this.chargesCapexExterneEngage= 0;
      this.chargesCapexExterneDispo = 0;
    }

    req="exec [SP_DevisGetLignesPoById]";
    req+=" @id ="+this.id+",";
    req+=" @TypeCharge ='"+TypeCharge+"'";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        LignePO theLignePO = new LignePO(rs.getInt("id"));
        theLignePO.idDevis = rs.getInt("idDevis");
        theLignePO.TypeCharge= rs.getString("Type"); // nom du ST
        theLignePO.Annee= rs.getString("Annee"); // nom du ST
        theLignePO.chargePrelevee= rs.getFloat("ChargePrelevee"); // nom du ST
        theLignePO.chargeDisponible= rs.getFloat("ChargeDispo"); // nom du ST
        if (TypeCharge.equals("CAPEX"))
            theLignePO.ChargePrevueForfait = theLignePO.chargeDisponible;
        theLignePO.nomProjet= rs.getString("nomProjet"); // nom du ST
        theLignePO.idService = rs.getInt("Structure");
        theLignePO.idWebPo = rs.getString("idPO");
        theLignePO.service = rs.getString("nomService");
        theLignePO.etat = rs.getString("etat");


        if (TypeCharge.equals("OPEX"))
        {
          this.chargesOpexPreleve+=theLignePO.chargePrelevee;
          this.chargesOpexEngage+=0;
          this.chargesOpexDispo+=theLignePO.chargeDisponible;
          this.ListeLignePO_OPEX.addElement(theLignePO);
        }
        else if (TypeCharge.equals("CAPEX"))
        {
          this.chargesCapexPreleve+=theLignePO.chargePrelevee;
          this.chargesCapexEngage+=0;
          this.chargesCapexDispo+=theLignePO.chargeDisponible;
          this.ListeLignePO_CAPEX.addElement(theLignePO);
        }
        else if (TypeCharge.equals("CAPEX_EXTERNE"))
        {
          this.chargesCapexExternePreleve+=theLignePO.chargePrelevee;
          this.chargesCapexExterneEngage+=0;
          this.chargesCapexExterneDispo+=theLignePO.chargeDisponible;
          this.ListeLignePO_CAPEX_EXTERNE.addElement(theLignePO);
        }
      }
    }
    catch (Exception s) {
      s.getMessage();
      return req;
    }
    return "OK";

  }

  public float getTotalChargeExigences(String nomBase,Connexion myCnx, Statement st){

    float TotalChargeExigences = 0;
    ResultSet rs;
    req="SELECT     SUM(Cout) AS TotalChargeExigences";
    req+="    FROM         BesoinsUtilisateur";
    req+=" WHERE     (idRoadmap = "+this.idRoadmap+")";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        TotalChargeExigences = rs.getFloat("TotalChargeExigences");
      }
    }
    catch (Exception s) {
      s.getMessage();
      return TotalChargeExigences;
    }
    return TotalChargeExigences;

  }

  public void excelExport (String nomBase,Connexion myCnx, Statement st){
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    
    Roadmap theRoadmap = new Roadmap (this.idRoadmap);
    theRoadmap.getListeBesoins(nomBase,myCnx,  st, false);


    String[] entete = {"Identifiant","Description", "Intégre","Complexite", "Cout"};
    Excel theExport = new Excel("doc/devis/exportExcel",entete);

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

    theExport.attach(theExport.Basedirectory+"/ExportDevisUnitaire_"+this.idRoadmap+".xls"); // nom du fichier excel horodate
    //myCnx.trace("2---------- attach","location",""+location+"/ExportDevisUnitaire_"+this.idRoadmap+".xls");

    //theExport.xl_open_update();
    theExport.xl_open_create("DEVIS"); // feuille de calcul

    //---------------------------- R�cup�ration de la liste des Briefs--------------------------------------------//
    //Devis.getListeDevis(myCnx.nomBase,myCnx,  st );
// -------------------------------------------------------------------------------------------------------//

    theExport.sheet.setColumnWidth(0, 256*10); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(1, 256*60); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(2, 256*10); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(3, 256*10); // fixer la largeur de la 1ere colonne
    theExport.sheet.setColumnWidth(4, 256*10); // fixer la largeur de la 1ere colonne


    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet.createFreezePane(2,1) ;

    //myCnx.trace("3---------- theExport.sheet.createFreezePane","Devis.ListeDevis.size()",""+Devis.ListeDevis.size());

    int numRow = 0;
    Row row = theExport.sheet.createRow(0);
    theExport.xl_writeEntete(row,0,theExport.entete);
    numRow++;
       
    for (int i=0; i < theRoadmap.ListeBesoins.size();i++)
    //for (int numRow=0; numRow < 432;numRow++)
    //for (int numRow=0; numRow < 2;numRow++)
    {
      

      int numCol = 0;

      row = theExport.sheet.createRow(numRow);

        Besoin theBesoin=(Besoin)theRoadmap.ListeBesoins.elementAt(i);
         //{"Identifiant","Description", "Intégre","Complexite", "Cout"};
        theExport.xl_write(row, numCol, "" + theBesoin.idBesoin);
        theExport.xl_write(row, ++numCol, "" + theBesoin.description);
        theExport.xl_write(row, ++numCol, "" + theBesoin.nomTypeIntegration);
        theExport.xl_write(row, ++numCol, "" + theBesoin.nomTypeComplexite);
        theExport.xl_write(row, ++numCol, "" + theBesoin.Cout, HSSFCell.CELL_TYPE_NUMERIC);

      numRow++;
    }
    //myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create();

    //myCnx.trace("5---------- xl_close_create","xl_close_create",""+theExport.filename);
        
      
  }
  
  public String getJalons(String nomBase,Connexion myCnx, Statement st, int Type){
    ResultSet rs;

    req="exec [SP_DevisGetJalonsById]";
    req+=" @id ="+this.id+",";
    req+=" @Type ="+Type+"";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        Jalon theJalon = new Jalon(rs.getInt("id"));
        theJalon.type = rs.getInt("Type");
        theJalon.nom = rs.getString("Jalon");
        if (theJalon.nom == null) theJalon.nom="";
        theJalon.Livrable = rs.getString("Livrable");
        if (theJalon.Livrable == null) theJalon.Livrable="";

        theJalon.DdateCreation = rs.getDate("dateJalon");
        if (theJalon.DdateCreation != null)
          theJalon.dateCreation = ""+theJalon.DdateCreation.getDate()+"/"+(theJalon.DdateCreation.getMonth()+1) + "/"+(theJalon.DdateCreation.getYear() + 1900);
         else
          theJalon.dateCreation = "";

        theJalon.Commentaire = rs.getString("Remarques");
        if (theJalon.Commentaire == null) theJalon.Commentaire="";
        theJalon.Acteur = rs.getString("Acteur");
        if (theJalon.Acteur == null) theJalon.Acteur="";

        if (theJalon.type != -1)
          theJalon.nom = rs.getString("nom");

        if (Type == 2) // UAT
        {
          this.JalonUAT = theJalon;
        }
        else if (Type == 3) // PROD
        {
          this.JalonPROD = theJalon;
        }
        else // Jalon specifique
        {
          this.ListeJalons.addElement(theJalon);
        }
      }
    }
    catch (Exception s) {
      s.getMessage();
      return req;
    }
    return "OK";

  }

  public static void getListeJalonsMigration(String nomBase,Connexion myCnx, Statement st){

    int typeTEST = -1;
    int typeMEP = -1;
   
    ResultSet rs;
    String req = "";
    
    req = "SELECT     id, nom FROM  typeJalon WHERE (numIndicateur =3 )";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
            typeTEST =  rs.getInt("id");
      }}   catch (SQLException s) {s.getMessage();}   
    
    req = "SELECT     id, nom FROM  typeJalon WHERE (numIndicateur =4 )";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
            typeMEP =  rs.getInt("id");
      }}   catch (SQLException s) {s.getMessage();}      
    
    req = "SELECT     Jalons.id, Jalons.Type, Jalons.Jalon, Jalons.Livrable, Jalons.dateJalon, Jalons.Remarques,  Devis.idRoadmap";
    req += " FROM         Jalons INNER JOIN";
    req += "                       Devis ON Jalons.idJalon = Devis.id";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
            Jalon theJalon = new Jalon(rs.getInt("id"));
            int type = rs.getInt("Type");
            if (type == 2)
                theJalon.type = typeTEST;
            else if (type == 3)
                theJalon.type = typeMEP;
            
            theJalon.Livrable = rs.getString("Livrable");
            theJalon.date = rs.getDate("dateJalon");
            theJalon.Commentaire = rs.getString("Remarques");
            theJalon.idRoadmap = rs.getInt("idRoadmap");
            ListeJalons_static.addElement(theJalon);
      }}   catch (SQLException s) {s.getMessage();}  
    
  }
  
  public static void getListeJalonGoMigration(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    String req = "";
    Date dateGO_MOA = null;
    Date dateGO_GOUVERNANCE = null;
    
    ListeJalons_static.removeAllElements();
    req = "SELECT     idRoadmap, dateGO_MOA, dateGO_GOUVERNANCE FROM   Devis WHERE     (dateGO_MOA IS NOT NULL) AND (dateGO_GOUVERNANCE IS NOT NULL)";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
            Jalon theJalon = new Jalon(-1);
            theJalon.idRoadmap = rs.getInt("idRoadmap");

            dateGO_MOA = rs.getDate("dateGO_MOA");
            dateGO_GOUVERNANCE = rs.getDate("dateGO_MOA");
            
            if (dateGO_MOA.getTime() > dateGO_GOUVERNANCE.getTime())
                theJalon.date = dateGO_MOA;
            else
                theJalon.date = dateGO_MOA;                
            
            ListeJalons_static.addElement(theJalon);
      }}   catch (SQLException s) {s.getMessage();}  
    
  }  
  public String getListeBesoins(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;

    req="exec [SP_DevisGetBesoinsUtilisateurById]";
    req+=" @id ="+this.id+"";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        Besoin theBesoin = new Besoin(rs.getInt("id"));
        theBesoin.type = rs.getInt("typeBesoin");
        theBesoin.nom = rs.getString("nom");
        if (theBesoin.nom == null) theBesoin.nom="";
        theBesoin.description = rs.getString("description");
        if (theBesoin.description == null) theBesoin.description="";
        theBesoin.IntegDevis = rs.getInt("integDevis");
        theBesoin.Commentaire = rs.getString("Commentaires");
        if (theBesoin.Commentaire == null) theBesoin.Commentaire="";
        theBesoin.idBesoin = rs.getString("idBesoin");
        if (theBesoin.idBesoin == null) theBesoin.idBesoin="";
        theBesoin.nomTypeIntegration = rs.getString("nomTypeIntegration");
        this.ListeBesoins.addElement(theBesoin);
        theBesoin.Complexite = rs.getInt("Complexite");
        theBesoin.Cout = rs.getFloat("Cout");

      }
    }
    catch (Exception s) {
      s.getMessage();
      return req;
    }
    return "OK";

  }

  public static String getListeDevis(String nomBase,Connexion myCnx, Statement st){
    String req="";
    String ListNomSt="";
    ResultSet rs;

    req="SELECT     Devis.idRoadmap , Devis.id ,St.nomSt, Roadmap.version, typeEtatDevis.nom AS Etat, Devis.LoginCreateur, Service_1.nomService AS MOE, ";
    req+="                  Service.nomService AS MOA, Devis.dateSoumission, Devis.dateGO_MOA, Devis.dateGO_GOUVERNANCE, Roadmap.chargeConsommeForfait, Roadmap.chargeConsommeInterne";
    req+=" FROM         Devis INNER JOIN";
    req+="                   typeEtatDevis ON Devis.idEtat = typeEtatDevis.id INNER JOIN";
    req+="                   Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                   Service ON VersionSt.serviceMoaVersionSt = Service.idService INNER JOIN";
    req+="                   Service AS Service_1 ON VersionSt.serviceMoeVersionSt = Service_1.idService INNER JOIN";
    req+="                   St ON VersionSt.stVersionSt = St.idSt";
    req+=" ORDER BY devis.dateSoumission";




    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    int i=0;
    try {
      while (rs.next()) {
        Devis theDevis = new Devis(rs.getInt("idRoadmap"));
        theDevis.id = rs.getInt("id");

        theDevis.nomSt = rs.getString("nomSt");
        theDevis.nomProjet = rs.getString("version");
        theDevis.Etat = rs.getString("Etat");
        theDevis.LoginCreateur = rs.getString("LoginCreateur");
        theDevis.serviceMOE = rs.getString("MOE");
        theDevis.serviceMOA = rs.getString("MOA");


        try{
          theDevis.dateSoumission = rs.getString("dateSoumission").substring(0, 10);
        }catch (Exception e){}

        try{
          theDevis.dateGO_MOA = rs.getString("dateGO_MOA").substring(0, 10);
        }catch (Exception e){}

        try{
          theDevis.dateGO_GOUVERNANCE = rs.getString("dateGO_GOUVERNANCE").substring(0, 10);
        }catch (Exception e){}
        //theSt.dump();

        theDevis.chargeConsommeForfait = rs.getFloat("chargeConsommeForfait");
        theDevis.chargeConsommeInterne = rs.getFloat("chargeConsommeInterne");
        
        Devis.ListeDevis.addElement(theDevis);

        i++;
      }
    }
    catch (Exception s) {
      s.getMessage();
      return req;
    }
    return ListNomSt;

  }
  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){

    ErrorSpecific myError = new ErrorSpecific();
    String Restrictions="";
    if (this.Restrictions != null)
      Restrictions=this.Restrictions.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
    else
      Restrictions="";

    String Risques="";
    if (this.Risques != null)
      Risques=this.Risques.replaceAll("\u0092","'").replaceAll("'","''");
    else
      Risques="";


    String Reponse="";
    if (this.Reponse != null)
      Reponse=this.Reponse.replaceAll("\u0092","'").replaceAll("'","''");
    else
    Reponse="";

  String description="";
  if (this.description != null)
    description=this.description.replaceAll("\u0092","'").replaceAll("'","''");
  else
    description="";

    req="exec [SP_DevisUpdateById]";
    req+=" @description ='"+description+"', ";
    req+=" @Restrictions ='"+Restrictions+"', ";
    req+=" @Risques ='"+Risques+"', ";
    req+=" @Reponse ='"+Reponse+"',";
    req+=" @id ="+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }
  
  public String getListeDiffusion(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
  String ListeDiffusion = "";

    req = "select DISTINCT * from";
    req+=" (";
    req+=" SELECT     Membre.mail";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       Membre ON VersionSt.respMoaVersionSt = Membre.idMembre";
    req+=" WHERE     Roadmap.idRoadmap = " + this.idRoadmap;
    req+=" UNION";
    req+=" SELECT     Membre.mail";
    req+=" FROM         Service INNER JOIN";
    req+="                       Membre ON Service.idService = Membre.serviceMembre";
    req+=" WHERE     (Membre.isPo = 1)";
    req+=" )";
    req+=" as mytable order by mail";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  int nb = 0;
  try {
  while (rs.next()) {
    if (nb > 0) ListeDiffusion += ";";
    ListeDiffusion += rs.getString("mail");
    nb++;

  }
}
  catch (SQLException s) {s.getMessage();}

  return ListeDiffusion;
  }
  public ErrorSpecific bd_updateState(String nomBase,Connexion myCnx, Statement st, String transaction, int new_idEtat){

    ErrorSpecific myError = new ErrorSpecific();

    req="exec [SP_DevisUpdateStateById]";
    req+=" @idEtat ='"+new_idEtat+"', ";
    req+=" @id ="+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateState",""+this.id);
    if (myError.cause == -1) return myError;

    this.dateSoumission=Utils.getToDay(myCnx.nomBase,myCnx,st);
    String str_dateSoumission ="";
    try{
        Utils.getDate(this.dateSoumission);
        str_dateSoumission = Utils.Day + "/" + Utils.Month + "/" + Utils.Year;

    }    catch (Exception e){}

    this.dateGO_MOA =Utils.getToDay(myCnx.nomBase,myCnx,st);
    String  str_dateGO_MOA="";
    try{
        Utils.getDate(this.dateGO_MOA);
        str_dateGO_MOA = Utils.Day + "/" + Utils.Month + "/" + Utils.Year;
    }    catch (Exception e){}

    this.dateGO_GOUVERNANCE=Utils.getToDay(myCnx.nomBase,myCnx,st);
    String str_dateGO_GOUVERNANCE ="";
    try{
        Utils.getDate(this.dateGO_GOUVERNANCE);
        str_dateGO_GOUVERNANCE = Utils.Day + "/" + Utils.Month + "/" + Utils.Year;

    }    catch (Exception e){}

    String str_dateTEST = "";
    String str_datePROD = "";

    if ((new_idEtat == 2) && (new_idEtat != this.idEtat)) // etat = depose
    {
      req="exec [SP_DevisUpdateDateSoumissionById]";
      req+=" @dateSoumission ='"+str_dateSoumission+"', ";
      req+=" @id ="+this.id;

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateState",""+this.id);
      if (myError.cause == -1) return myError;
    }

    if ((new_idEtat == 4) && (new_idEtat != this.idEtat)) // etat = Accept� MOA
    {
      req="exec [SP_DevisUpdateDateGoMoaById]";
      req+=" @DateGoMoa ='"+str_dateGO_MOA+"', ";
      req+=" @id ="+this.id;

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateState",""+this.id);
      if (myError.cause == -1) return myError;
    }

    if ((new_idEtat == 5) && (new_idEtat != this.idEtat)) // etat = Accept� GAP
    {

      req="exec [SP_DevisUpdateDateGoGouvernance]";
      req+=" @dateGO_GOUVERNANCE ='"+str_dateGO_GOUVERNANCE+"', ";
      req+=" @id ="+this.id;

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateState",""+this.id);
      if (myError.cause == -1) return myError;
    }

    if ((new_idEtat == 7) && (this.idEtat == 4)) // etat = Accept�
    {
      req="exec [SP_DevisUpdateDateGoGouvernance]";
      req+=" @dateGO_GOUVERNANCE ='"+str_dateGO_GOUVERNANCE+"', ";
      req+=" @id ="+this.id;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateState",""+this.id);
      if (myError.cause == -1) return myError;
      // forcer la date de GO dans le projet
      
      /*
      req="exec [SP_RoadmapUpdateDateGoDevisById]";
      req+=" @dateGO ='"+str_dateGO_GOUVERNANCE+"', ";
      req+=" @dateTEST ='"+this.JalonUAT.dateCreation+"', ";
      req+=" @datePROD ='"+this.JalonPROD.dateCreation+"', ";
      req+=" @id ="+this.idRoadmap;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateState",""+this.id);
      if (myError.cause == -1) return myError;
      */
    }

    if ((new_idEtat == 7) && (this.idEtat == 5)) // etat = depose
    {
      req="exec [SP_DevisUpdateDateGoMoaById]";
      req+=" @DateGoMoa ='"+str_dateGO_MOA+"', ";
      req+=" @id ="+this.id;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateState",""+this.id);
      if (myError.cause == -1) return myError;
      // forcer la date de GO dans le projet
      
      /*
      req="exec [SP_RoadmapUpdateDateGoDevisById]";
      req+=" @dateGO ='"+str_dateGO_MOA+"', ";
      req+=" @dateTEST ='"+this.JalonUAT.dateCreation+"', ";
      req+=" @datePROD ='"+this.JalonPROD.dateCreation+"', ";
      req+=" @id ="+this.idRoadmap;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateState",""+this.id);
      if (myError.cause == -1) return myError;
      */
    }
    return myError;
  }

  public ErrorSpecific bd_InsertLignePO(String nomBase,Connexion myCnx, Statement st, String transaction, String Type){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

     myError = bd_DeleteLignePO( nomBase, myCnx,  st,  transaction, Type);
     if (myError.cause == -1) return myError;

     //myCnx.trace("@5678--------","req",""+req);
     Vector TheListe = null;
     if (Type.equals("OPEX"))
       TheListe = this.ListeLignePO_OPEX;
     else if (Type.equals("CAPEX"))
       TheListe = this.ListeLignePO_CAPEX;
     else if (Type.equals("CAPEX_EXTERNE"))
       TheListe = this.ListeLignePO_CAPEX_EXTERNE;

     for (int i=0; i < TheListe.size(); i++)
     {
       LignePO theLignePO = (LignePO)TheListe.elementAt(i);

       req="exec [SP_DevisInsertLignePO]";
       req+=" @idDevis ='"+this.id+"', ";
       req+=" @TypeCharge ="+theLignePO.TypeCharge+", ";
       req+=" @Annee ='"+theLignePO.Annee+"', ";
       req+=" @chargeEngagee ='"+theLignePO.chargePrelevee+"', ";
       req+=" @charge ='"+theLignePO.chargeDisponible+"', ";
       req+=" @nomProjet ='"+theLignePO.nomProjet.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ')+"',";
       req+=" @idService ='"+theLignePO.idService+"',";
       req+=" @idWebPo ='"+theLignePO.idWebPo+"',";
       req+=" @etat ='"+theLignePO.etat+"'";

       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertLignePO",""+this.id);
       if (myError.cause == -1) return myError;
     }

     return myError;

  }
  
  public ErrorSpecific bd_updateConsoPO(String nomBase,Connexion myCnx, Statement st, String transaction, String Type, String nomServiceImputations){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
    
     Vector TheListe = null;
     if (Type.equals("OPEX"))
       TheListe = this.ListeLignePO_OPEX;
     else if (Type.equals("CAPEX"))
       TheListe = this.ListeLignePO_CAPEX;
     else if (Type.equals("CAPEX_EXTERNE"))
       TheListe = this.ListeLignePO_CAPEX_EXTERNE;
     
     for (int i=0; i < TheListe.size(); i++)
     {
       LignePO theLignePO = (LignePO)TheListe.elementAt(i);

       req="SELECT     SUM(ChargePrelevee) AS ChargePrelevee";
       req+=" FROM         Devis INNER JOIN";
       req+="                       DevisBudget ON Devis.id = DevisBudget.idDevis";
       req+=" WHERE     (DevisBudget.idPO ="+theLignePO.idWebPo+") AND (DevisBudget.Type = '"+Type+"') AND (Devis.idServiceCreateur = '"+theLignePO.idService+"')";
       
        rs = myCnx.ExecReq(st, myCnx.nomBase, req);
        try {
            while (rs.next()) {
                if (Type.equals("OPEX"))
                    theLignePO.chargeConsommee = rs.getFloat("ChargePrelevee");
                else if (Type.equals("CAPEX"))
                    theLignePO.chargeConsommeForfait = rs.getFloat("ChargePrelevee");           
            }
        }
        catch (Exception e){}
      } 
     
     for (int i=0; i < TheListe.size(); i++)
     {
       LignePO theLignePO = (LignePO)TheListe.elementAt(i);
       if (Type.equals("OPEX"))
       {
        req="UPDATE PlanOperationnelClient";
        req+=" SET ";
        req+=" chargeConsommee=" +theLignePO.chargeConsommee;  
        req+=" WHERE     (idWebPO = "+theLignePO.idWebPo+") AND (Service = '"+nomServiceImputations+"')";           
       }
       else if (Type.equals("CAPEX"))
       {
        req="UPDATE PlanOperationnelClient";
        req+=" SET ";
        req+=" depenseConsommee=" +theLignePO.chargeConsommeForfait;       
        req+=" WHERE     (idWebPO = "+theLignePO.idWebPo+") AND (Service = '"+nomServiceImputations+"')";           
       }

       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertLignePO",""+this.id);
       if (myError.cause == -1) return myError;
      }      


     
     

     return myError;

  }  

  public ErrorSpecific bd_InsertJalonsPredefini(String nomBase,Connexion myCnx, Statement st, String transaction, Jalon theJalon){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    myError= bd_DeleteJalons( nomBase, myCnx,  st,  transaction, theJalon.type);
     if (myError.cause == -1) return myError;

       String nom="";
       if (theJalon.nom != null)
         nom=theJalon.nom.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
       else
         nom="";

       String Livrable="";
       if (theJalon.Livrable != null)
         Livrable=theJalon.Livrable.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
       else
         Livrable="";

       String Commentaire="";
       if (theJalon.Commentaire != null)
         Commentaire=theJalon.Commentaire.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
       else
         Commentaire="";

       String Acteur="";
       if (theJalon.Acteur != null)
         Acteur=theJalon.Acteur.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
       else
         Acteur="";


       String str_dateCreation ="01"+"/"+"01"+"/"+"1900";
       try{
         if ( (theJalon.dateCreation != null) && (!this.JalonUAT.dateCreation.equals(""))) {
           Utils.getDate(theJalon.dateCreation);
           str_dateCreation = Utils.Day + "/" + Utils.Month + "/" + Utils.Year ;
         }
    }    catch (Exception e){}

       req="exec [SP_DevisInsertJalons]";
       req+=" @idJalon ='"+this.id+"', ";
       req+=" @Type ="+theJalon.type+", ";
       req+=" @Jalon ='"+nom+"', ";
       req+=" @Livrable ='"+Livrable+"', ";
       req+=" @dateJalon ='"+str_dateCreation+"', ";
       req+=" @Remarques ='"+Commentaire+"', ";
       req+=" @Acteur ='"+Acteur+"'";

       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertJalonsPredefini",""+this.id);

     return myError;

  }
  public ErrorSpecific bd_InsertJalons(String nomBase,Connexion myCnx, Statement st, String transaction){

    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;


     for (int i=0; i < this.ListeJalons.size(); i++)
     {
       Jalon theJalon = (Jalon)this.ListeJalons.elementAt(i);

       String nom="";
       if (theJalon.nom != null)
         nom=theJalon.nom.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
       else
         nom="";

       String Livrable="";
       if (theJalon.Livrable != null)
         Livrable=theJalon.Livrable.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
       else
         Livrable="";

       String Commentaire="";
       if (theJalon.Commentaire != null)
         Commentaire=theJalon.Commentaire.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
       else
         Commentaire="";

       String Acteur="";
       if (theJalon.Acteur != null)
         Acteur=theJalon.Acteur.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
       else
         Acteur="";

       String str_dateCreation ="01"+"/"+"01"+"/"+"1900";
       try{
         if ( (theJalon.dateCreation != null) && (!theJalon.dateCreation.equals(""))) {
           Utils.getDate(theJalon.dateCreation);
           str_dateCreation = Utils.Day + "/" + Utils.Month + "/" + Utils.Year ;
         }
    }    catch (Exception e){}
       
   myError=bd_DeleteJalons( nomBase, myCnx,  st,  transaction, theJalon.type);
   if (myError.cause == -1) return myError;       

       req="exec [SP_DevisInsertJalons]";
       req+=" @idJalon ='"+this.id+"', ";
       req+=" @Type ="+theJalon.type+", ";
       req+=" @Jalon ='"+nom+"', ";
       req+=" @Livrable ='"+Livrable+"', ";
       req+=" @dateJalon ='"+str_dateCreation+"', ";
       req+=" @Remarques ='"+Commentaire+"', ";
       req+=" @Acteur ='"+Acteur+"'";

       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertJalons",""+this.id);
       if (myError.cause == -1) return myError;
     }

     return myError;

  }
  
  public ErrorSpecific bd_UpdateJalons(String nomBase,Connexion myCnx, Statement st, String transaction, int new_idEtat){

    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;


     for (int i=0; i < this.ListeJalons.size(); i++)
     {
       Jalon theJalon = (Jalon)this.ListeJalons.elementAt(i);

       String Livrable="";
       if (theJalon.Livrable != null)
         Livrable=theJalon.Livrable.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
       else
         Livrable="";

       String Commentaire="";
       if (theJalon.Commentaire != null)
         Commentaire=theJalon.Commentaire.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
       else
         Commentaire="";

       String[] ligneJalonMoe = theJalon.strDate_moe.split("/");
       String dateMoe = "convert(datetime, '"+ligneJalonMoe[0] + "/" + ligneJalonMoe[1] + "/" +ligneJalonMoe[2]+"', 103)";    
          

                    req = "UPDATE JalonsProjet SET ";
                    req +=" Livrable ="+ "'"+ Livrable + "'" + ",";
                    req +=" Remarques ="+ "'"+ Commentaire + "'" + ",";
                    req +=" date_moe ="+ dateMoe ;
                    req += " WHERE id ="+ theJalon.id;   

       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertJalons",""+this.id);
       if (myError.cause == -1) return myError;
       
       if ((new_idEtat == 7) && (theJalon.isEngagement == 1))
       {
           Date DDateGo = new Date();
           String dateGo = "convert(datetime, '"+DDateGo.getDate()+"/"+(DDateGo.getMonth()+ 1)+"/"+(DDateGo.getYear()+ 1900)+"', 103)";
                    req = "UPDATE JalonsProjet SET ";
                    req +=" date_go ="+ dateGo ;
                    req += " WHERE id ="+ theJalon.id;
                    req += " AND date_go IS NULL";
            myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertJalons",""+this.id);
            if (myError.cause == -1) return myError;                    
       }
     }

     return myError;

  }  

  public ErrorSpecific bd_updateStateByAdmin(String nomBase,Connexion myCnx, Statement st, String transaction){
    req = "UPDATE    Devis  SET ";
    req += " idEtat ='" + this.idEtat + "'";
    req += " WHERE idRoadmap =" + this.idRoadmap;

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"InsertListeSt (delete)",""+this.id);

    return myError;

  }

  public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;

    String req = "DELETE FROM Devis WHERE  idRoadmap = " + this.idRoadmap;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  }

  public ErrorSpecific bd_DeleteBesoinsUtilisateur(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;

    String req = "exec SP_DevisDeleteBesoinsUtilisateursById " + this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_DeleteBesoinsUtilisateur",""+this.id);

    return myError;
  }

  public ErrorSpecific bd_DeleteLignePO(String nomBase,Connexion myCnx, Statement st, String transaction, String Type){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;

    String req = "exec SP_DevisDeleteLignePO " + this.id + ",'"+Type+"'";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_DeleteLignePO",""+this.id);

    return myError;
  }

  public ErrorSpecific bd_DeleteJalons(String nomBase,Connexion myCnx, Statement st, String transaction, int Type){
  ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;

  String req = "exec SP_DevisDeleteJalonsById " + this.id+ ","+Type;
  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_DeleteJalons",""+this.id);

  return myError;
}


  public ErrorSpecific bd_InsertBesoins(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();


    ResultSet rs=null;

    myError = bd_DeleteBesoinsUtilisateur( nomBase, myCnx,  st,  transaction);
    if (myError.cause == -1) return myError;

     for (int i=0; i < this.ListeBesoins.size(); i++)
     {
       Besoin theBesoin = (Besoin)this.ListeBesoins.elementAt(i);

       String description="";
       if (theBesoin.description != null)
         description=theBesoin.description.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
       else
         description="";

       String Commentaire="";
       if (theBesoin.Commentaire != null)
         Commentaire=theBesoin.Commentaire.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
       else
         Commentaire="";

       String idBesoin="";
       if (theBesoin.idBesoin != null)
         idBesoin=theBesoin.idBesoin.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
       else
         idBesoin="";

       req="exec [SP_DevisInsertBesoinsUtilisateurs]";
       req+=" @idDevis ='"+this.id+"', ";
       req+=" @typeBesoin ="+theBesoin.type+", ";
       req+=" @nom ='"+theBesoin.nom+"', ";
       req+=" @description ='"+description+"', ";
       req+=" @integDevis ='"+theBesoin.IntegDevis+"', ";
       req+=" @Commentaires ='"+Commentaire+"',";
       req+=" @idBesoin ='"+idBesoin+"',";
       req+=" @Complexite ='"+theBesoin.Complexite+"',";
       req+=" @Cout ='"+theBesoin.Cout+"',";


       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertBesoins",""+this.id);
       if (myError.cause == -1) return myError;
     }

     return myError;

  }

  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  ErrorSpecific myError = new ErrorSpecific();

  String Restrictions="";
  if (this.Restrictions != null)
    Restrictions=this.Restrictions.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
  else
    Restrictions="";

  String Risques="";
  if (this.Risques != null)
    Risques=this.Risques.replaceAll("\u0092","'").replaceAll("'","''");
  else
    Risques="";


  String Reponse="";
  if (this.Reponse != null)
    Reponse=this.Reponse.replaceAll("\u0092","'").replaceAll("'","''");
  else
    Reponse="";

  String description="";
  if (this.description != null)
    description=this.description.replaceAll("\u0092","'").replaceAll("'","''");
  else
    description="";
   
       req= "    INSERT Devis (";
       req+="     description";
       req+="     ,";
       req+="     idRoadmap";
       req+="     ,";
       req+="     Restrictions";
       req+="     ,";
       req+="     Risques";
       req+="     ,";
       req+="     Reponse";
       req+="     ,";
       req+="     idEtat";
       req+="     ,";
       req+="     LoginCreateur";
       req+="     ,";
       req+="     idServiceCreateur    ";
       req+="     ,";
       req+="     typeJalon    ";       
       req+="        )";
       req+="      VALUES (";
       req+="     '"+description+"'";
       req+="     ,";
       req+="     "+this.idRoadmap;
       req+="     ,";
       req+="     '"+Restrictions+"'";
       req+="     ,";
       req+="    '"+Risques+"'";
       req+="     ,";
       req+="     '"+Reponse+"'";
       req+="     ,";
       req+="     "+idEtat;
       req+="     ,";
       req+="     '"+this.LoginCreateur+"'";
       req+="     ,";
       req+="     '"+this.idServiceCreateur+"'";
       req+="     ,";
       req+="     "+this.typeJalon;       
       req+="     )";    


    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_insert",""+this.id);
    if (myError.cause == -1) return myError;

    this.getAllFromBd(myCnx.nomBase, myCnx, st);

    return myError;
  }
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    LignePO theLignePO=null;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("idRoadmap="+this.idRoadmap);
    System.out.println("nomProjet="+this.nomProjet);
    System.out.println("description="+this.description);
    System.out.println("Risques="+this.Risques);
    System.out.println("Reponse="+this.Reponse);
    System.out.println("RepoidEtatnse="+this.idEtat);

    for (int i=0; i < this.ListeLignePO_OPEX.size(); i++){
      theLignePO = (LignePO)this.ListeLignePO_OPEX.elementAt(i);
      theLignePO.dump();

    }
    for (int i=0; i < this.ListeLignePO_CAPEX.size(); i++){
      theLignePO = (LignePO)this.ListeLignePO_CAPEX.elementAt(i);
      theLignePO.dump();
    }

    for (int i=0; i < this.ListeJalons.size(); i++){
        Jalon theJalon = (Jalon)this.ListeJalons.elementAt(i);
        theJalon.dump();
      }

    for (int i=0; i < this.ListeBesoins.size(); i++){
          Besoin theBesoin = (Besoin)this.ListeBesoins.elementAt(i);
          theBesoin.dump();
      }

    System.out.println("idServiceCreateur="+this.idServiceCreateur);
    System.out.println("==================================================");
    }

  public static void main(String[] args) {
    String nomSt = "";
    String idVersionSt="";
    String oldversion = "";
    String version = "xxxx";
    String description = "xxxxxxxxxxxxx";
    String EtatRoadmap ="";
    String Panier ="";
    String idPO = "";
    String Charge = "";
    String Ordre = "";
    String dateT0 = "";
    String dateEB = "";
    String dateTest = "";
    String dateProd = "";
    String dateMes = "";
    String Suivi = "";
    String Tendance = "";

    Connexion myCnx = null;
    Statement st, st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    st2 = myCnx.Connect();

    Devis theDevis = new Devis(34104);
    theDevis.getAllFromBd(myCnx.nomBase, myCnx, st);

    theDevis.getJalons(myCnx.nomBase, myCnx, st, -1);

    theDevis.idEtat=4;
    int next_state=7;

    transaction theTransaction = new transaction ("EnregDevis");
    theTransaction.begin(base,myCnx,st);

    //String result = theDevis.setUpdateState(myCnx.nomBase,myCnx, st, theTransaction.nom,next_state, "theCollaborateur.mail");

    theTransaction.commit(base,myCnx,st);

    theDevis.dump();

  myCnx.Unconnect(st2);
  myCnx.Unconnect(st);
  }
}

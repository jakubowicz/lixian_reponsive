package PO; 

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
public class importPO {
    static Connexion myCnx;
    static int Annee;
    public Vector ListeLignePO = new Vector(10);


    final static int L_Start=14;

    final static int C_idWebPo=11;
    final static int C_Enveloppe=2;
    //final static int Priorite="";
    final static int C_nomProjet=13;
    final static int C_descProjet=85;
    final static int C_risqueProjet=85;
    final static int C_gainProjet=85;
    final static int C_charge=38;
    final static int C_chargeEngagee = 32;
    final static int C_macrost = 14;
    final static int C_chefprojet = 17;
    final static int C_codeChronos = 23;
    //final static int nbEntree=0;
    //final static int totalChargeEngagee=0;

    final static int SPOR_idWebPo=0;
    final static int SPOR_Enveloppe=1;
    final static int SPOR_Priorite=33;
    final static int SPOR_nomProjet=2;
    final static int SPOR_descProjet=8;
    final static int SPOR_risqueProjet=27;
    final static int SPOR_gainProjet=28;
    final static int SPOR_charge=44;
    final static int SPOR_macrost = 4;
    final static int SPOR_chefprojet = 6;
    final static int SPOR_DATE_EF=18;
    final static int SPOR_DATE_MAQUETTE=19;
    final static int SPOR_DATE_REAL=20;
    final static int SPOR_MEP=21;
    final static int SPOR_MOA_DED=211;
    final static int SPOR_EOD=210;
    final static int SPOR_DTE_TRN=81;
    final static int SPOR_DTE_TRB=46;
    final static int SPOR_DTE_TPG=80;

    final static int PROFEECIE_ColonneDateExtraction=1;
    final static int PROFEECIE_LigneDateExtraction=1;

    final static int PROFEECIE_Code=0;
    final static int PROFEECIE_Sujet=1;
    final static int PROFEECIE_numFiche=2;
    final static int PROFEECIE_Impact=3;
    final static int PROFEECIE_statutImpact=4;
    final static int PROFEECIE_departement=6;
    final static int PROFEECIE_service=7;
    final static int PROFEECIE_structure=8;
    final static int PROFEECIE_Financement=9;
    final static int PROFEECIE_chargeConsommee = 11;
    final static int PROFEECIE_chargePrevue = 10;


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




  public importPO() {
    Statement st =null;

    st = myCnx.Connect();
    this.Annee = Integer.parseInt(Utils.getYear(myCnx.nomBase, myCnx,  st));
  }

  public importPO(int Annee) {
    Statement st =null;

    st = myCnx.Connect();
    this.Annee = Annee;
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

  public void xl_read_PO_Client(int Annee) {

    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();

    st = myCnx.Connect();
    st2 = myCnx.Connect();

    LignePO theLignePO =null;
    FileInputStream fileIn = null;
    Cell cell = null;

    String filename = "..\\..\\po\\PO_Client_"+(Annee)+".xls";
    int nbEcarte = 0;
    int nbTotal = 0;
    //int Ligne = 26;
    int Ligne = 33;
    int Colonne = 1;
    try
    {
      // test pour svoir si c'est du xls ou du xslx/xslm
      fileIn = new FileInputStream(filename);
      POIFSFileSystem fs = new POIFSFileSystem(fileIn);
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheet("index");
      HSSFRow row = sheet.getRow(Ligne);

      row = sheet.getRow(PROFEECIE_LigneDateExtraction);
      String str_DateExtraction = row.getCell(PROFEECIE_ColonneDateExtraction).toString();

      row = sheet.getRow(Ligne);
      cell = row.getCell(Colonne);
      //if (cell != null) idWebPo = cell.toString();

      
      while((row != null) &&(cell != null) )
        {
            nbTotal++;

          //Connexion.trace("","cell.toString()",cell.toString());
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
*/
          this.codeSujet = row.getCell(PROFEECIE_Code).toString();
          this.idWebPo =   epure(row.getCell(PROFEECIE_numFiche).toString());
          this.nomProjet = row.getCell(PROFEECIE_Impact).toString();
          this.etat = row.getCell(PROFEECIE_statutImpact).toString(); //
          //service = row.getCell(PROFEECIE_service).toString(); //
          this.service = row.getCell(PROFEECIE_structure).toString(); //
          String S_chargeConsommee = row.getCell(PROFEECIE_chargeConsommee).toString();
          String S_charge = row.getCell(PROFEECIE_chargePrevue).toString();
          this.part=row.getCell(PROFEECIE_Financement).toString();
          this.TypeCharge = "Depense";

          try{
            charge = Float.parseFloat(S_charge);
          }catch (Exception e){
            charge = 0; }

          try{
            chargeConsommee = Float.parseFloat(S_chargeConsommee);
          }catch (Exception e){
            chargeConsommee = 0; }


            theLignePO = new LignePO(idWebPo, "", nomProjet, "",
                                     "", charge, "", "");
            theLignePO.service = service;
            theLignePO.etat = etat;
            theLignePO.chargeConsommee =chargeConsommee;
            theLignePO.dateExtraction = str_DateExtraction;

            theLignePO.codeSujet = this.codeSujet;
            theLignePO.part = this.part;
            theLignePO.TypeCharge = this.TypeCharge;

            //theLignePO.dump();
            if ((this.nomProjet.indexOf("AR08") < 0) && (this.nomProjet.indexOf("REJET CONSO")< 0))
            {
              this.ListeLignePO.addElement(theLignePO);
            }
            else
            {
              nbEcarte++;
              System.out.print("@@ --> BILAN: nbLu/nbEcarte: "+this.nomProjet+"/"+nbEcarte);
            }

            Ligne++;
            row = sheet.getRow(Ligne);
            if (row != null) cell = row.getCell(Colonne);

        }

        if (fileIn != null)
        fileIn.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    };

    System.out.print("@@99999 --> Projet ecarte: "+nbEcarte+"/"+nbTotal);
  }

  
  
  public void bd_insert() {
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


    reqRefSt =  "DELETE FROM PlanOperationnel WHERE     (Annee = "+Annee+")";
    rs2 = myCnx.ExecReq(st2, base, reqRefSt);

    req = "SELECT     new_WebPo2010.[Type Financement R�seau], new_WebPo2010.[Id MOE], new_WebPo2010.Projets, new_WebPo2010.MOA, Service.idService, ";
    req+="                  new_WebPo2010.[Budget PO Courant], new_WebPo2010.Description";
    req+=" FROM         new_WebPo2010 INNER JOIN";
    req+="                  Service ON new_WebPo2010.MOA = Service.nomServicePO";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        String Enveloppe = rs.getString("Type Financement R�seau");
        int idWebPo = rs.getInt("Id MOE");

        String nomProjet = rs.getString("Projets");
        if (nomProjet != null) nomProjet = nomProjet.replaceAll("'","''"); else nomProjet="";

        int idService = rs.getInt("idService");

        String charge = rs.getString("Budget PO Courant");
        if (charge != null) charge = charge.replaceAll(",",".");


        String descProjet = rs.getString("Description");
        if (descProjet != null) descProjet = descProjet.replaceAll("'","''"); else descProjet="";

        String Priorite = "P0";

        String gainProjet = "";
        //String gainProjet ="";
        if (gainProjet != null) gainProjet = gainProjet.replaceAll("'","''"); else gainProjet="";




        //Connexion.trace("*********"," nomProjet="+nomProjet, " :: charge="+charge);

          reqRefSt =  "INSERT INTO PlanOperationnel  ";
          reqRefSt+="(idWebPo, Enveloppe,nomProjet,descProjet,Priorite,charge, Annee,gainProjet,idPoMoa) ";
          reqRefSt+= "VALUES     ('";
          reqRefSt+= idWebPo +"', '";
          reqRefSt+= Enveloppe +"', '";
          reqRefSt+= nomProjet +"', '";
          reqRefSt+= descProjet +"', '";
          reqRefSt+= Priorite +"', '";
          reqRefSt+= charge +"', '";
          reqRefSt+= Annee +"', '";
          reqRefSt+= gainProjet +"', '";
          reqRefSt+= idService +"')";
          myCnx.ExecReq(st2, myCnx.nomBase, reqRefSt);
          System.out.println("reqRefSt="+reqRefSt);
      }





    } catch (SQLException s) {s.getMessage();}

    myCnx.Unconnect(st);

  }

  public void xl_write_PO_DSI() {
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

    Date currentTime_1 = new Date();
    int theYear = currentTime_1.getYear()+ 1900;
    int theMonth = currentTime_1.getMonth()+1;
    int theDay = currentTime_1.getDate();
    int theHour = currentTime_1.getHours();
    int theMinute = currentTime_1.getMinutes();
    int theSecond = currentTime_1.getSeconds();

    // 2010-03-10 00:00:00.000
    // CONVERT(DATETIME, '1960-03-16 00:00:00', 102)
    //String theDate = theDay+"/"+theMonth+"/"+ theYear+" "+theHour+":"+theMinute+":"+theSecond+".000";
    String theDate ="CONVERT(DATETIME, '"+theYear+"-"+theMonth+"-"+theDay+" "+theHour+":"+theMinute+":"+theSecond+"', 102)";


    //reqRefSt =  "DELETE FROM PlanOperationnel WHERE     (Annee = "+this.Annee+")";
    //rs2 = myCnx.ExecReq(st2, base, reqRefSt);

      for (int i = 0; i < this.ListeLignePO.size(); i++)
      {
        LignePO theLignePO = (LignePO)this.ListeLignePO.elementAt(i);
        String Enveloppe = theLignePO.Enveloppe;
        int idWebPo = -1;
        try{
          idWebPo = Integer.parseInt(theLignePO.idWebPo);
        }
        catch (Exception e){
          //e.printStackTrace();
        }

        String nomProjet = theLignePO.nomProjet;
        if (nomProjet != null) nomProjet = nomProjet.replaceAll("'","''"); else nomProjet="";
        String chefprojet = theLignePO.chefprojet;
        if (chefprojet != null) chefprojet = chefprojet.replaceAll("'","''"); else chefprojet="";
        String macrost = theLignePO.macrost;
         if (macrost != null) macrost = macrost.replaceAll("'","''"); else macrost="";

        int idService = 74;

        String charge = ""+theLignePO.charge;
        if (charge != null) charge = charge.replaceAll(",",".");


        String descProjet =theLignePO.descProjet;
        if (descProjet != null) descProjet = descProjet.replaceAll("'","''"); else descProjet="";

        String Priorite = "P0";

        String gainProjet = theLignePO.gainProjet;
        if (gainProjet != null) gainProjet = gainProjet.replaceAll("'","''"); else gainProjet="";

        //Connexion.trace("*********"," nomProjet="+nomProjet, " :: charge="+charge);

          reqRefSt =  "INSERT INTO PlanOperationnel  ";
          reqRefSt+="(idWebPo, Enveloppe,nomProjet,descProjet,Priorite,charge, Annee,gainProjet,idPoMoa, MacroSt, chefProjet, codeChronos,dateImport) ";
          reqRefSt+= "VALUES     ('";
          reqRefSt+= idWebPo +"', '";
          reqRefSt+= Enveloppe.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          reqRefSt+= nomProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          reqRefSt+= descProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          reqRefSt+= Priorite +"', '";
          reqRefSt+= charge +"', '";
          reqRefSt+= Annee +"', '";
          reqRefSt+= gainProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          reqRefSt+= idService +"', '";
          reqRefSt+= macrost.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          reqRefSt+= chefprojet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          reqRefSt+= theLignePO.codeChronos.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"', ";;
          reqRefSt+= theDate;
          reqRefSt+=")";
          myCnx.ExecReq(st2, myCnx.nomBase, reqRefSt);
          System.out.println("reqRefSt="+reqRefSt);
      }







    myCnx.Unconnect(st);

  }

  public void xl_write_PO_Client(int Annee) {
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


    if (this.ListeLignePO.size() == 0)
    {
      System.out.println("*** AUCUNE LIGNE PO LUES, Annee" + Annee);
      return;
    }

    reqRefSt =  "DELETE FROM PlanOperationnelClient WHERE     (Annee = "+Annee+")";
    rs2 = myCnx.ExecReq(st2, base, reqRefSt);

      for (int i = 0; i < this.ListeLignePO.size(); i++)
      {
        LignePO theLignePO = (LignePO)this.ListeLignePO.elementAt(i);
        /*
                  PROFEECIE_Code=0;                        --> codeSujet
                  PROFEECIE_Sujet=1;                       -->
                  PROFEECIE_numFiche=2;                    --> idWebPo
                  PROFEECIE_Impact=3;                      --> nomProjet
                  PROFEECIE_statutImpact=4;                --> etat
                  PROFEECIE_departement=5;                 -->
                  PROFEECIE_service=6;                     --> service
                  PROFEECIE_structure=7;                   -->
                  PROFEECIE_Financement=8;                 -->
                  PROFEECIE_chargeConsommee = 9;           --> chargeConsomee
                  PROFEECIE_chargePrevue = 10;             --> charge
*/
        int idWebPo = -1;
        try{
          idWebPo = Integer.parseInt(theLignePO.idWebPo);
        }
        catch (Exception e){
          e.printStackTrace();
        }

        String nomProjet = theLignePO.nomProjet;
        if (nomProjet != null) nomProjet = nomProjet.replaceAll("'","''"); else nomProjet="";

        String etat = theLignePO.etat;
        if (etat != null) etat = etat.replaceAll("'","''"); else etat="";

        String service = theLignePO.service;
        if (service != null) service = service.replaceAll("'","''"); else service="";

        String chargeConsommee = ""+theLignePO.chargeConsommee;
        if (chargeConsommee != null) chargeConsommee = chargeConsommee.replaceAll(",",".");

        String charge = ""+theLignePO.charge;
        if (charge != null) charge = charge.replaceAll(",",".");

        String str_dateExtraction ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
        if ((theLignePO.dateExtraction != null) &&(!theLignePO.dateExtraction.equals("")))
        {
         Utils.getDate(theLignePO.dateExtraction);
         str_dateExtraction ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
       }

        //Connexion.trace("*********"," nomProjet="+nomProjet, " :: charge="+charge);

          reqRefSt =  "INSERT INTO PlanOperationnelClient  ";
          reqRefSt+="(idWebPo, nomProjet,charge, Annee,etat,service, chargeConsommee,dateExtraction, codeSujet, Part, TypeCharge ) ";
          reqRefSt+= "VALUES     ('";
          reqRefSt+= idWebPo +"', '";
          reqRefSt+= nomProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          reqRefSt+= charge +"', '";
          reqRefSt+= Annee +"', '";
          reqRefSt+= etat.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          reqRefSt+= service.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', ";
          reqRefSt+= chargeConsommee+", ";
          reqRefSt+= str_dateExtraction +", '";
          reqRefSt+= theLignePO.codeSujet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          reqRefSt+= theLignePO.part.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          reqRefSt+= theLignePO.TypeCharge.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
          reqRefSt+= ")";

          myCnx.ExecReq(st2, myCnx.nomBase, reqRefSt);
          System.out.println("reqRefSt="+reqRefSt);
      }







    myCnx.Unconnect(st);

  }



  public static void main(String[] args) {
    Calendar calendar = Calendar.getInstance();
    Annee = calendar.get(Calendar.YEAR);

    importPO thePODSI = new importPO(Annee);
    //thePODSI.xl_read_PO_DSI();
    //thePODSI.xl_write_PO_DSI();

  }



}

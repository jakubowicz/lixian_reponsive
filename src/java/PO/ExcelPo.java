package PO; 

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

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;


import java.util.Vector;

import Organisation.Collaborateur;
import accesbase.ErrorSpecific;
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
public class ExcelPo {
    static Connexion myCnx;
    public int Annee;
    public String filename="";
    public String Basedirectory="";
    public String directory="";
    
    public Vector ListeLignePO = new Vector(10);

   
    final static int IMPORT_LigneDateExtraction=1;

    final static int IMPORT_Annee=0;                    
    final static int IMPORT_Code=1;                    
    final static int IMPORT_Sujet=2;                   
    final static int IMPORT_numFiche=3;                
    final static int IMPORT_Impact=4;                  
    final static int IMPORT_statutImpact=5;
    final static int IMPORT_departement=7;
    final static int IMPORT_service=8;
    final static int IMPORT_structure=9;
    final static int IMPORT_Financement=11;
    final static int IMPORT_Opex = 12;
    final static int IMPORT_chargeConsommee = 13;
    final static int IMPORT_ColonneDateExtraction=14;
    final static int IMPORT_Capex=15;
   


    String codeSujet=""; // 1- IMPORT_Code=0
    String etat=""; // 2- IMPORT_statutImpact=4;
    String idWebPo="";   // 3- IMPORT_numFiche=2;
    String Enveloppe= "";
    String nomProjet="";// 4- IMPORT_Sujet=1;
    // 5- Annee
    String service=""; // 6- IMPORT_structure=7;
    String part=""; // 7- IMPORT_Financement=8;
    String TypeCharge="Depense"; // 8-
    // 8- TypeCharge = Depense
    String descProjet= "";
    String risqueProjet= "";
    float charge= 0; // IMPORT_Opex = 10;
    float chargeConsommee= 0; // IMPORT_chargeConsommee = 9;
    String gainProjet="";
    String Priorite= "";
    String macrost= "";
    String chefprojet= "";

    String dateEF= "";
    String dateMAQUETTE= "";
    String dateREAL= "";
    String dateMEP= "";
    
    private int nbEcarte = 0;
    private int nbTotal = 0;
    
    public causeRejet theCauseRejet=null;




  public ExcelPo() {
  }

  public ExcelPo(int Annee) {
    Statement st =null;

    st = myCnx.Connect();
    this.Annee = Annee;
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

  public String getFormatedDate(String location) {

     File myFile = new File(location);
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      Date d = new Date(myFile.lastModified());

      return sdf.format(d);
  }

  public causeRejet xl_read(String nomBase,Connexion myCnx, Statement st, Statement st2) {
      this.theCauseRejet = new causeRejet();
      int pos = -1;
      if (this.filename.indexOf("xlsx") == -1)
          return xl_readXLS(nomBase, myCnx,  st,  st2);
      else
          return xl_readXLSX(nomBase, myCnx,  st,  st2);      
      
  }
  
  public causeRejet xl_readXLS(String nomBase,Connexion myCnx, Statement st, Statement st2) {

    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();

    LignePO theLignePO =null;
    FileInputStream fileIn = null;
    Cell cell = null;

    this.ListeLignePO.removeAllElements();

    //int Ligne = 26;
    int Ligne = 1;
    int Colonne = 1;
    try
    {
      // test pour svoir si c'est du xls ou du xslx/xslm
      fileIn = new FileInputStream(this.filename);
      POIFSFileSystem fs = new POIFSFileSystem(fileIn);
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);
      HSSFRow row = sheet.getRow(Ligne);


      row = sheet.getRow(IMPORT_LigneDateExtraction);
      String str_DateExtraction = row.getCell(IMPORT_ColonneDateExtraction).toString();
      //str_DateExtraction = str_DateExtraction.substring(0,6)+ (2000 + Integer.parseInt(str_DateExtraction.substring(6,8)));
      str_DateExtraction = "";
      row = sheet.getRow(Ligne);
      cell = row.getCell(IMPORT_Annee);
      //if (cell != null) idWebPo = cell.toString();


      //while((row != null) &&(cell != null) )
      while((row != null) &&(cell != null) )
        {
            nbTotal++;
            this.theCauseRejet.nbLus++;

          //Connexion.trace("","cell.toString()",cell.toString());
/*
          1- IMPORT_Code=0;                        -->
          IMPORT_Sujet=1;                       -->
          3- IMPORT_numFiche=2;                    --> idWebPo
          4- IMPORT_Impact=3;                      --> nomProjet
          2- IMPORT_statutImpact=4;                --> etat
          IMPORT_departement=5;                 -->
          IMPORT_service=6;                     --> service
          6- IMPORT_structure=7;                   -->
          7- IMPORT_Financement=8;                 -->
          IMPORT_chargeConsommee = 9;           --> chargeConsomee
          IMPORT_Opex = 10;             --> charge

          5- Annee
 thePOClient.Annee
*/

          int AnneeLignePo = -1;
          try{
              String theAnnee = row.getCell(IMPORT_Annee).toString();
              int pos = -1;
              pos = theAnnee.indexOf(".");
              if (pos > -1)
              {
                  theAnnee = theAnnee.substring(0,pos);
              }
              AnneeLignePo = Integer.parseInt(theAnnee) ;
          }catch (Exception e){}

          
       if (AnneeLignePo == this.Annee)
       {
          try{
            this.codeSujet = row.getCell(IMPORT_Code).toString();
          }catch (Exception e){}


          try{
            this.idWebPo =   epure(row.getCell(IMPORT_numFiche).toString());
          }catch (Exception e){}


          try{
            this.nomProjet = row.getCell(IMPORT_Impact).toString();
          }catch (Exception e){}


          try{
            this.etat = row.getCell(IMPORT_statutImpact).toString(); //
          }catch (Exception e){}
          //service = row.getCell(IMPORT_service).toString(); //

          try{
            this.service = row.getCell(IMPORT_structure).toString(); //
            Service theService = new Service(this.service);
            theService.getCC(myCnx.nomBase,myCnx,st,this.service);
            this.service = theService.nomServiceImputations;
          }catch (Exception e){}


          String S_OpexConsommee = "0";
          try{
            S_OpexConsommee = row.getCell(IMPORT_chargeConsommee).toString();
          }
          catch (Exception e) {S_OpexConsommee="0";}

          String S_Opex = "";
          try{
            S_Opex = row.getCell(IMPORT_Opex).toString();
          }catch (Exception e){}

          String S_Capex = "";
          try{
            S_Capex = row.getCell(IMPORT_Capex).toString();
          }catch (Exception e){}          

          try{
            this.part=row.getCell(IMPORT_Financement).toString();
          }catch (Exception e){}

          this.TypeCharge = "Depense";

          try{
            charge = Float.parseFloat(S_Opex);
          }catch (Exception e){
            charge = 0; }

          try{
            chargeConsommee = Float.parseFloat(S_OpexConsommee);
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
            theLignePO.Annee = ""+AnneeLignePo;
            
          try{
            theLignePO.chargeAffecteeForfait = Float.parseFloat(S_Capex);
          }catch (Exception e){
            theLignePO.chargeAffecteeForfait = 0; }            

            //theLignePO.dump();
            if ((this.nomProjet.indexOf("AR08") < 0) && (this.nomProjet.indexOf("REJET CONSO")< 0) && (theLignePO.service != null) && (!theLignePO.service.equals("")))
            {
                if (theLignePO.Annee.equals(""+this.Annee))
                {
                    this.ListeLignePO.addElement(theLignePO);
                    this.theCauseRejet.nbRepris++;
                }
            }
            else
            {
                this.theCauseRejet.nbEcarte++;
                if (this.nomProjet.indexOf("AR08") >= 0)
                    this.theCauseRejet.nbActviteRecurrente++;
                else if (this.nomProjet.indexOf("REJET CONSO") >= 0)
                    this.theCauseRejet.nbRejetConso++;      
                else if ((theLignePO.service == null) || (theLignePO.service.equals("")) )
                    this.theCauseRejet.nbService++;                  

            }
       }

            Ligne++;
            row = sheet.getRow(Ligne);
            if (row != null) cell = row.getCell(IMPORT_Annee);

        }

        if (fileIn != null)
        fileIn.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    };
    
    if (this.ListeLignePO.size() == 0)
    {
      System.out.println("*** AUCUNE LIGNE PO LUES, Annee" + this.Annee);
    }   
    
        System.out.print("@@99999 --> Projet ecarte: "+nbEcarte+"/"+nbTotal);

    return this.theCauseRejet;

  }
  
  public causeRejet xl_readXLSX(String nomBase,Connexion myCnx, Statement st, Statement st2) {

    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();

    LignePO theLignePO =null;
    FileInputStream fileIn = null;
    Cell cell = null;

    this.ListeLignePO.removeAllElements();

    //int Ligne = 26;
    int Ligne = 1;
    int Colonne = 1;
    try
    {
      // test pour svoir si c'est du xls ou du xslx/xslm
      fileIn = new FileInputStream(this.filename);
      XSSFWorkbook wb = new XSSFWorkbook (fileIn); 
      XSSFSheet sheet = null;
      
    int numOnglet = 0;
 
    sheet = wb.getSheetAt(numOnglet);  
    
    
    String Onglet = "";
    Onglet = sheet.getSheetName(); 
    Ligne = 1;
    XSSFRow row = sheet.getRow(IMPORT_LigneDateExtraction);  

      String str_DateExtraction = row.getCell(IMPORT_ColonneDateExtraction).toString();
      //str_DateExtraction = str_DateExtraction.substring(0,6)+ (2000 + Integer.parseInt(str_DateExtraction.substring(6,8)));
      str_DateExtraction = "";
      row = sheet.getRow(Ligne);
      cell = row.getCell(IMPORT_Annee);

      while((row != null) &&(cell != null) )
        {
         this.theCauseRejet.nbLus++;

          int AnneeLignePo = -1;
          try{
              String theAnnee = row.getCell(IMPORT_Annee).toString();
              int pos = -1;
              pos = theAnnee.indexOf(".");
              if (pos > -1)
              {
                  theAnnee = theAnnee.substring(0,pos);
              }
              AnneeLignePo = Integer.parseInt(theAnnee) ;
          }catch (Exception e){}

          
       if (AnneeLignePo == this.Annee)
       {
          try{
            this.codeSujet = row.getCell(IMPORT_Code).toString();
          }catch (Exception e){}


          try{
            this.idWebPo =   epure(row.getCell(IMPORT_numFiche).toString());
          }catch (Exception e){}


          try{
            this.nomProjet = row.getCell(IMPORT_Impact).toString();
          }catch (Exception e){}


          try{
            this.etat = row.getCell(IMPORT_statutImpact).toString(); //
          }catch (Exception e){}
          //service = row.getCell(IMPORT_service).toString(); //

          try{
            this.service = row.getCell(IMPORT_structure).toString(); //
            Service theService = new Service(this.service);
            theService.getCC(myCnx.nomBase,myCnx,st,this.service);
            this.service = theService.nomServiceImputations;
          }catch (Exception e){}


          String S_OpexConsommee = "0";
          try{
            S_OpexConsommee = row.getCell(IMPORT_chargeConsommee).toString();
          }
          catch (Exception e) {S_OpexConsommee="0";}

          String S_Opex = "";
          try{
            S_Opex = row.getCell(IMPORT_Opex).toString();
          }catch (Exception e){}

          String S_Capex = "";
          try{
            S_Capex = row.getCell(IMPORT_Capex).toString();
          }catch (Exception e){}          

          try{
            this.part=row.getCell(IMPORT_Financement).toString();
          }catch (Exception e){}

          this.TypeCharge = "Depense";

          try{
            charge = Float.parseFloat(S_Opex);
          }catch (Exception e){
            charge = 0; }

          try{
            chargeConsommee = Float.parseFloat(S_OpexConsommee);
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
            theLignePO.Annee = ""+AnneeLignePo;
            
          try{
            theLignePO.chargeAffecteeForfait = Float.parseFloat(S_Capex);
          }catch (Exception e){
            theLignePO.chargeAffecteeForfait = 0; }            

            //theLignePO.dump();
            if ((this.nomProjet.indexOf("AR08") < 0) && (this.nomProjet.indexOf("REJET CONSO")< 0) && (theLignePO.service != null) && (!theLignePO.service.equals("")))
            {
                if (theLignePO.Annee.equals(""+this.Annee))
                {
                    this.ListeLignePO.addElement(theLignePO);
                    this.theCauseRejet.nbRepris++;
                }
            }
            else
            {
                this.theCauseRejet.nbEcarte++;
                if (this.nomProjet.indexOf("AR08") >= 0)
                    this.theCauseRejet.nbActviteRecurrente++;
                else if (this.nomProjet.indexOf("REJET CONSO") >= 0)
                    this.theCauseRejet.nbRejetConso++;      
                else if ((theLignePO.service == null) || (theLignePO.service.equals("")) )
                    this.theCauseRejet.nbService++;    
            }
       }

            Ligne++;
            row = sheet.getRow(Ligne);
            if (row != null) cell = row.getCell(IMPORT_Annee);

        }

        if (fileIn != null)
        fileIn.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    };
    
    if (this.ListeLignePO.size() == 0)
    {
      System.out.println("*** AUCUNE LIGNE PO LUES, Annee" + this.Annee);
    }    

    return this.theCauseRejet;

  }  


  public ErrorSpecific xl_write(String nomBase,Connexion myCnx, Statement st, String transaction) {
    ResultSet rs = null;
    ResultSet rs2 = null;
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
      myError.cause=-1;
      myError.nb = 0;
      return myError;
    }

    //reqRefSt =  "DELETE FROM PlanOperationnelClient WHERE     (Annee = "+this.Annee+") OR (Annee = "+(this.Annee+1)+")";
    reqRefSt =  "DELETE FROM PlanOperationnelClient WHERE     (Annee = "+this.Annee+") ";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,reqRefSt,true,transaction, getClass().getName(),"xl_write",""+this.Annee);
    if (myError.cause == -1) return myError;

      for (int i = 0; i < this.ListeLignePO.size(); i++)
      {
        LignePO theLignePO = (LignePO)this.ListeLignePO.elementAt(i);

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
        
        String chargeCapex = ""+theLignePO.chargeAffecteeForfait;
        if (chargeCapex != null) chargeCapex = chargeCapex.replaceAll(",",".");        

        Calendar calendar = Calendar.getInstance();
        //Annee = calendar.get(Calendar.YEAR);

        String str_dateExtraction ="convert(datetime, '"+calendar.get(Calendar.DATE)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR)+"', 103)";

        str_dateExtraction ="convert(datetime, '"+getFormatedDate(filename)+"', 103)";

       String str_dateConsomme ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
       if ((theLignePO.dateExtraction != null) &&(!theLignePO.dateExtraction.equals("")))
       {
        Utils.getDate(theLignePO.dateExtraction);
        str_dateConsomme ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
       }

        //Connexion.trace("*********"," nomProjet="+nomProjet, " :: charge="+charge);
          
       if (!theLignePO.Annee.equals(""+this.Annee)) continue;
       
          reqRefSt =  "INSERT INTO PlanOperationnelClient  ";
          reqRefSt+="(idWebPo, nomProjet,charge, Annee,etat,service, chargeConsommee,dateExtraction, codeSujet, Part, TypeCharge, dateConsomme, depensePrevue ) ";
          reqRefSt+= "VALUES     ('";
          reqRefSt+= idWebPo +"', '";
          reqRefSt+= nomProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          reqRefSt+= charge +"', '";
          reqRefSt+= theLignePO.Annee +"', '";
          reqRefSt+= etat.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          reqRefSt+= service.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', ";
          reqRefSt+= chargeConsommee+", ";
          reqRefSt+= str_dateExtraction +", '";
          reqRefSt+= theLignePO.codeSujet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          reqRefSt+= theLignePO.part.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          reqRefSt+= theLignePO.TypeCharge.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
          reqRefSt+= str_dateConsomme+", '";
          reqRefSt+= chargeCapex +"'";
          reqRefSt+= ")";

           myError = myCnx.ExecUpdateTransaction(st,nomBase,reqRefSt,true,transaction, getClass().getName(),"xl_write",""+this.Annee);
          if (myError.cause == -1) return myError;
      }

      myError.nb=this.ListeLignePO.size();

    return myError;

  }
  
  public ErrorSpecific xl_writeGlobal(String nomBase,Connexion myCnx, Statement st, String transaction) {
    ResultSet rs = null;
    String req="";

    ErrorSpecific myError = new ErrorSpecific();

    req =  "DELETE FROM BudgetGlobal WHERE     (Annee = "+this.Annee+") ";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"xl_writeGlobal",""+this.Annee);
    if (myError.cause == -1) return myError;
    
    req="SELECT DISTINCT idWebPo, nomProjet";
    req+=" FROM         PlanOperationnelClient";
    req+=" WHERE     (Annee = "+this.Annee+")";
    req+=" ORDER BY idWebPo";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    
    this.ListeLignePO.removeAllElements();
    try {
      while (rs.next()) {
          LignePO theLignePO = new LignePO();
          theLignePO.idWebPo = rs.getString("idWebPo");
          theLignePO.nomProjet = rs.getString("nomProjet");
          theLignePO.Annee = ""+ this.Annee;
          theLignePO.etat = "Candidat";
        this.ListeLignePO.addElement(theLignePO);

      }
    }
            catch (SQLException s) {s.getMessage();}    

      for (int i = 0; i < this.ListeLignePO.size(); i++)
      {
        LignePO theLignePO = (LignePO)this.ListeLignePO.elementAt(i);
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

          req =  "INSERT INTO BudgetGlobal  ";
          req+="(idBudget, nomProjet,Annee,etat ) ";
          req+= "VALUES     ('";
          req+= idWebPo +"', '";
          req+= nomProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          req+= theLignePO.Annee +"', '";
          req+= theLignePO.etat +"'";
          req+= ")";

          myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"xl_writeGlobal",""+this.Annee);
          if (myError.cause == -1) return myError;          
      }
      return myError;
  }  

  public static void main(String[] args) {
    Connexion myCnx = null;
    Statement st, st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    st2 = myCnx.Connect();

    ExcelPo thePOClient = new ExcelPo();
    if (args.length > 0)
    {
      thePOClient.Annee = Integer.parseInt(args[0]);
    }
    else
    {
      Calendar calendar = Calendar.getInstance();
      thePOClient.Annee = calendar.get(Calendar.YEAR);
    }
    thePOClient.xl_read(myCnx.nomBase, myCnx, st, st2);
    thePOClient.xl_write(myCnx.nomBase, myCnx, st, "");


  }
}

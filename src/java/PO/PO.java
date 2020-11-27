package PO; 

import java.util.Vector;
import ST.Module;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import java.util.Calendar;
import General.Utils;
import ST.Interface;
import Projet.Roadmap;
import accesbase.transaction;
import accesbase.ErrorSpecific;
import java.util.*;

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
public class PO {
  public String Annee;
  public int LF_Month = -1;
  public int LF_Year = -1;

  public int idSI;
  public int idMacroSt;
  public int idMOA;
  public float totalCharge=0;
  private String req="";
  public Vector ListeLignePO = new Vector(10);
  public Vector ListeLignePODelta = new Vector(10);
  public Vector ListeLignePODeltaCharge = new Vector(10);
  public String Index = "";

    public Vector ListeExcel = new Vector(10);
    public Vector ListeRoadmap = new Vector(10);
    public Vector ListeLF = new Vector(10);

  public int totalChargeEngagee =0;

  public java.util.Date DdateImport = null;
  public java.util.Date DdateImportSimule = null;
  public java.util.Date DdateImportConsomme = null;

  public String dateImport = "";
  public String dateImportSimule = "";

  public String dateImportConsomme = "";


  public PO(String Annee, int idMOA, String Index) {
    this.Annee=Annee;
    this.idMOA=idMOA;
    this.Index = Index;
  }

  public PO() {
    Calendar calendar = Calendar.getInstance();
    this.Annee = ""+calendar.get(Calendar.YEAR);

  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    String req="";

    req="SELECT DISTINCT dateExtraction, dateConsomme";
    req+="    FROM         PlanOperationnelClient";
    req+="     WHERE     (Annee = "+this.Annee+")";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        this.DdateImport= rs.getDate("dateExtraction");
        this.DdateImportConsomme= rs.getDate("dateConsomme");

      }
    }
            catch (SQLException s) {s.getMessage();}

    if (this.DdateImport != null)
        this.dateImport = ""+this.DdateImport.getDate()+"/"+(this.DdateImport.getMonth()+1) + "/"+(this.DdateImport.getYear() + 1900);
    else
        this.dateImport = "";

    if (this.DdateImportConsomme != null)
          this.dateImportConsomme = ""+this.DdateImportConsomme.getDate()+"/"+(this.DdateImportConsomme.getMonth()+1) + "/"+(this.DdateImportConsomme.getYear() + 1900);
      else
        this.dateImportConsomme = "";
  }

  public void getAllFromBdSimule(String nomBase,Connexion myCnx, Statement st, String  nomServiceImputations){
    ResultSet rs;
    String req="";

    req="SELECT DISTINCT dateExtraction";
    req+="    FROM         PlanOperationnelClient_simulation";
    req+="     WHERE     (Annee = "+this.Annee+")";
    req+=" AND service= '"+nomServiceImputations+"'";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        this.DdateImportSimule= rs.getDate("dateExtraction"); // nom du ST

      }
    }
            catch (SQLException s) {s.getMessage();}

    if (this.DdateImportSimule != null)
        this.dateImportSimule = ""+this.DdateImportSimule.getDate()+"/"+(this.DdateImportSimule.getMonth()+1) + "/"+(this.DdateImportSimule.getYear() + 1900);
    else
        this.dateImportSimule = "";
  }

  public  void getListeExcel(String nomBase,Connexion myCnx, Statement st ){

    req = "exec EXPORT_excelRoadmap";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    String old_nomProjet="";
    String idWebPo="";
    String nomServicePO="";
    String Enveloppe="";
    String nomProjet="";
    String descProjet="";
    String gainProjet="";
    String risqueProjet="";
    String Priorite="";
    String chargePrevue="";
    String ChargeEngagee="";
    String dateEB="";
    String dateMep="";
    String Annee="";

    float thechargePrevue = 0;
    float theChargeEngagee= 0;

    try { while(rs.next()) {
        idWebPo = rs.getString(1);
        nomServicePO= rs.getString(2);
        Enveloppe= rs.getString(3);

        nomProjet= rs.getString(4);
        if (nomProjet != null) nomProjet= nomProjet.replaceAll("\\r","").replaceAll("\\n","");

        descProjet= rs.getString(5);
        if (descProjet != null) descProjet= descProjet.replaceAll("\\r","").replaceAll("\\n","");

        gainProjet= rs.getString(6);
        if (gainProjet != null) gainProjet= gainProjet.replaceAll("\\r","").replaceAll("\\n","");

        risqueProjet= rs.getString(7);
        if (risqueProjet != null) risqueProjet=risqueProjet.replaceAll("\\r","").replaceAll("\\n","");

        Priorite= rs.getString(8);
        if (!old_nomProjet.equals(nomProjet))
        {
          chargePrevue= rs.getString(9);
          //if (chargePrevue != null) chargePrevue=chargePrevue.replaceAll("\\.",",");
        }
        else chargePrevue="";

        ChargeEngagee= rs.getString(10);
       //if (ChargeEngagee != null) ChargeEngagee=ChargeEngagee.replaceAll("\\.",",");
        dateEB= rs.getString(11);
        dateMep= rs.getString(12);
        Annee= rs.getString(13);

        old_nomProjet = nomProjet;

        try{
          thechargePrevue = Float.parseFloat(chargePrevue);
        }
        catch (Exception e){
          thechargePrevue = 0;
        }

        try{
          theChargeEngagee = Float.parseFloat(ChargeEngagee);
        }
        catch (Exception e){
          theChargeEngagee = 0;
        }

        LignePO theLignePO = new LignePO( idWebPo, Enveloppe,  nomProjet,  descProjet,  risqueProjet, thechargePrevue,  gainProjet,  Priorite );
        theLignePO.chargeEngagee = theChargeEngagee;
        theLignePO.str_chargeEngagee = (""+theLignePO.chargeEngagee).replaceAll("\\.",",");
        theLignePO.str_chargePrevue = (""+theLignePO.charge).replaceAll("\\.",",");

        theLignePO.dateEB = dateEB;
        theLignePO.dateMep = dateMep;
        theLignePO.Annee = Annee;
        theLignePO.nomServicePO = nomServicePO;

        this.ListeExcel.addElement(theLignePO);
        }	} catch (SQLException s) {s.getMessage();}
}

  public void setLignesPONonMappees(String nomBase,Connexion myCnx, Statement st, String idWebPo, String nomMOA ){
    ResultSet rs;
    String req =  "INSERT INTO idWebPO  (idWebPO, nomMOA) VALUES     ('"+idWebPo+"', '"+nomMOA+"')";
    rs = myCnx.ExecReq(st, nomBase, req);


  }

  public void getLignesPONonMappees(String nomBase,Connexion myCnx, Statement st, String KeyWords ){
    ResultSet rs;
    if ((KeyWords == null) || (KeyWords.equals("")))
    {
      req = "SELECT     idWebPo, nomProjet, charge, MacroSt, chefProjet, Annee, dateImport";
      req += "     FROM         PlanOperationnel";
      req += " GROUP BY idWebPo, nomProjet, charge, MacroSt, chefProjet,Annee, dateImport";
      req += " having     (idWebPo NOT IN";
      req += "                     (SELECT     idWebPO";
      req += "                   FROM          idWebPO)) AND (Annee = "+this.Annee+")  ";
      req += "                   and (PlanOperationnel.dateImport in (SELECT     MAX(dateImport) AS dateImport FROM         PlanOperationnel))         ";
      req += "                   ORDER BY MacroSt ASC";

    }
    else
    {
      req = "SELECT     idWebPo, nomProjet, charge, MacroSt, chefProjet, Annee, dateImport";
      req += "     FROM         PlanOperationnel";
      req += " GROUP BY idWebPo, nomProjet, charge, MacroSt, chefProjet,Annee, dateImport";
      req += " having     (idWebPo NOT IN";
      req += "                     (SELECT     idWebPO";
      req += "                       FROM          idWebPO)) AND (Annee = "+this.Annee+") AND (MacroSt LIKE '%"+KeyWords+"%')";
      req += "                   and (PlanOperationnel.dateImport in (SELECT     MAX(dateImport) AS dateImport FROM         PlanOperationnel))         ";
      req += " ORDER BY MacroSt";

    }

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String idWebPo= rs.getString("idWebPo"); // nom du ST
        String Enveloppe="";
        String risqueProjet="";
        String nomProjet= rs.getString("nomProjet"); // nom du ST
        String descProjet= ""; // nom du ST

        float charge= rs.getFloat("charge"); // nom du ST
        String MacroSt= rs.getString("MacroSt"); // nom du ST
        String chefProjet= rs.getString("chefProjet"); // nom du ST
        String gainProjet= "";
        String Priorite= "P1";
        totalCharge+=charge;


        LignePO theLignePO = new LignePO(idWebPo,Enveloppe, nomProjet, descProjet, risqueProjet,charge,  gainProjet,  Priorite );
        theLignePO.macrost = MacroSt;
        theLignePO.chefprojet = chefProjet;
        this.ListeLignePO.addElement(theLignePO);
      }
    }
            catch (SQLException s) {s.getMessage();}
  }

  public void getLignesPONonMappees2(String nomBase,Connexion myCnx, Statement st, String KeyWords ){
    ResultSet rs;
    if ((KeyWords == null) || (KeyWords.equals("")))
    {
      req =
          "SELECT     idWebPo, nomProjet, descProjet, charge, MacroSt, chefProjet";
      req += " FROM         PlanOperationnel";
      req += " WHERE     (idWebPo NOT IN";
      req += "                           (SELECT     idWebPO";
      req += "                         FROM          idWebPO)) AND (Annee = " +
          this.Annee + ")    ORDER BY MacroSt ASC";
    }
    else
    {
      req = " SELECT     idWebPo, nomProjet, descProjet, charge, MacroSt, chefProjet";
      req += "     FROM         PlanOperationnel";
      req += " WHERE     (idWebPo NOT IN";
      req += "                     (SELECT     idWebPO";
      req += "                       FROM          idWebPO)) AND (Annee = "+this.Annee+") AND (MacroSt LIKE '%"+KeyWords+"%')";
      req += " ORDER BY MacroSt";

    }

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String idWebPo= rs.getString("idWebPo"); // nom du ST
        String Enveloppe="";
        String risqueProjet="";
        String nomProjet= rs.getString("nomProjet"); // nom du ST
        String descProjet= rs.getString("descProjet"); // nom du ST

        float charge= rs.getFloat("charge"); // nom du ST
        String MacroSt= rs.getString("MacroSt"); // nom du ST
        String chefProjet= rs.getString("chefProjet"); // nom du ST
        String gainProjet= "";
        String Priorite= "P1";
        totalCharge+=charge;


        LignePO theLignePO = new LignePO(idWebPo,Enveloppe, nomProjet, descProjet, risqueProjet,charge,  gainProjet,  Priorite );
        theLignePO.macrost = MacroSt;
        theLignePO.chefprojet = chefProjet;
        this.ListeLignePO.addElement(theLignePO);
      }
    }
            catch (SQLException s) {s.getMessage();}
  }


  public void getLignesPO_Client(String nomBase,Connexion myCnx, Statement st, int idService ){
    ResultSet rs;

    if (this.Annee == null) this.Annee = Utils.getYear(myCnx.nomBase, myCnx,  st);


    req = "SELECT     idWebPo, Enveloppe, nomProjet, descProjet, risqueProjet, charge, LF, Annee, gainProjet, Priorite, dateEF, dateMAQUETTE, dateREAL, dateMEP, MacroSt";
      req+="     FROM         PlanOperationnelClient";
      req+=" WHERE     (Annee = '"+this.Annee+"') AND (idService = "+idService+")";
      req+=" ORDER BY nomProjet";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String idWebPo= rs.getString("idWebPo"); // nom du ST
        String Enveloppe= rs.getString("Enveloppe"); // nom du ST
        String nomProjet= rs.getString("nomProjet"); // nom du ST
        String descProjet= rs.getString("descProjet"); // nom du ST
        String risqueProjet= rs.getString("risqueProjet"); // etat du ST
        float charge= rs.getFloat("charge"); // nom du ST
        String gainProjet= rs.getString("gainProjet");
        String Priorite= rs.getString("Priorite");
        String dateEF= rs.getString("dateEF");
        String dateMAQUETTE= rs.getString("dateMAQUETTE");
        String dateREAL= rs.getString("dateREAL");
        String dateMEP= rs.getString("dateMEP");
        String MacroSt= rs.getString("MacroSt");
        totalCharge+=charge;
        LignePO theLignePO = new LignePO(idWebPo,Enveloppe, nomProjet, descProjet, risqueProjet,charge,  gainProjet,  Priorite );
        theLignePO.dateEB = dateEF;
        theLignePO.dateMAQUETTE = dateMAQUETTE;
        theLignePO.dateREAL = dateREAL;
        theLignePO.dateMep = dateMEP;
        theLignePO.macrost = MacroSt;

        this.ListeLignePO.addElement(theLignePO);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }
  
  public void getLignesPO_ClientByYear(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;

    if (this.Annee == null) this.Annee = Utils.getYear(myCnx.nomBase, myCnx,  st);


    req = "SELECT     PlanOperationnelClient.idWebPo, PlanOperationnelClient.Enveloppe, PlanOperationnelClient.nomProjet, PlanOperationnelClient.descProjet, PlanOperationnelClient.risqueProjet, ";
    req += "                      PlanOperationnelClient.charge, PlanOperationnelClient.LF, PlanOperationnelClient.Annee, PlanOperationnelClient.gainProjet, PlanOperationnelClient.Priorite, PlanOperationnelClient.dateEF, ";
    req += "                      PlanOperationnelClient.dateMAQUETTE, PlanOperationnelClient.dateREAL, PlanOperationnelClient.dateMEP, PlanOperationnelClient.MacroSt, PlanOperationnelClient.Service, ";
    req += "                       PlanOperationnelClient.chargeConsommee, PlanOperationnelClient.depensePrevue, PlanOperationnelClient.depenseConsommee, Service.idService";
    req += " FROM         PlanOperationnelClient INNER JOIN";
    req += "                       Service ON PlanOperationnelClient.Service = Service.nomServiceImputations";
    req += " WHERE     (PlanOperationnelClient.Annee = '"+this.Annee+"')";
    req += " ORDER BY PlanOperationnelClient.nomProjet";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String idWebPo= rs.getString("idWebPo"); // nom du ST
        String Enveloppe= rs.getString("Enveloppe"); // nom du ST
        String nomProjet= rs.getString("nomProjet"); // nom du ST
        String descProjet= rs.getString("descProjet"); // nom du ST
        String risqueProjet= rs.getString("risqueProjet"); // etat du ST
        float charge= rs.getFloat("charge"); // nom du ST
        String gainProjet= rs.getString("gainProjet");
        String Priorite= rs.getString("Priorite");
        String dateEF= rs.getString("dateEF");
        String dateMAQUETTE= rs.getString("dateMAQUETTE");
        String dateREAL= rs.getString("dateREAL");
        String dateMEP= rs.getString("dateMEP");
        String MacroSt= rs.getString("MacroSt");
        totalCharge+=charge;
        LignePO theLignePO = new LignePO(idWebPo,Enveloppe, nomProjet, descProjet, risqueProjet,charge,  gainProjet,  Priorite );
        theLignePO.dateEB = dateEF;
        theLignePO.dateMAQUETTE = dateMAQUETTE;
        theLignePO.dateREAL = dateREAL;
        theLignePO.dateMep = dateMEP;
        theLignePO.macrost = MacroSt;
        
        theLignePO.service = rs.getString("Service");
        theLignePO.chargeConsommee= rs.getFloat("chargeConsommee");
        theLignePO.ChargePrevueForfait= rs.getFloat("depensePrevue");
        theLignePO.chargeConsommeForfait= rs.getFloat("depenseConsommee");
        theLignePO.idService= rs.getInt("idService");

        this.ListeLignePO.addElement(theLignePO);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }  
  
  public void getLignesPO_ClientGlobalByYear(String nomBase,Connexion myCnx, Statement st, Statement st2){
    ResultSet rs;
    ResultSet rs2;

    if (this.Annee == null) this.Annee = Utils.getYear(myCnx.nomBase, myCnx,  st);


    req = "SELECT      idBudget, NomProjet, Etat, OpexPrevu, OpexConsomme, CapexPrevu, CapexConsomme, Description";
      req+="     FROM         BudgetGlobal";
      req+=" WHERE     (Annee = '"+this.Annee+"')";
      req+=" ORDER BY nomProjet";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        LignePO theLignePO = new LignePO();
        theLignePO.idWebPo= rs.getString("idBudget"); 
        theLignePO.nomProjet= rs.getString("nomProjet"); 
        theLignePO.etat= rs.getString("Etat"); 
        
        theLignePO.chargeDisponible= rs.getFloat("OpexPrevu"); 
        theLignePO.ChargePrevueForfait= rs.getFloat("CapexPrevu");
        
        theLignePO.descProjet= rs.getString("Description"); 
        
        req="SELECT     SUM(charge) AS totalChargePrevue, SUM(chargeConsommee) AS totalChargeConsommee, SUM(depensePrevue) AS totaldepensePrevuee, SUM(depenseConsommee) AS totaldepenseConsommee";
        req+=" FROM         PlanOperationnelClient";
        req+=" WHERE     (Annee = "+this.Annee+")  AND (idWebPo = "+theLignePO.idWebPo+") ";
    
        rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);
        try {
            while (rs2.next()) {
                theLignePO.chargeAffectee = rs2.getFloat("totalChargePrevue");
                theLignePO.chargeConsommee = rs2.getFloat("totalChargeConsommee");     
                theLignePO.chargeAffecteeForfait = rs2.getFloat("totaldepensePrevuee");
                theLignePO.chargeConsommeForfait = rs2.getFloat("totaldepenseConsommee"); 
                this.ListeLignePO.addElement(theLignePO);

            }
         }
        catch (SQLException s) { }        

        
      }
    }
            catch (SQLException s) {s.getMessage();}
    
    
    

  }   


  public void getLignesRoadmap(String nomBase,Connexion myCnx, Statement st){
    Interface myInterface=null;


    req = "SELECT ";
    req += " idRoadmap, nomSt, version, idVersionSt, dateT0,dateEB,dateTest, dateMep, dateMes,   serviceMoaVersionSt, respMoaVersionSt, ";
    req += " LF_Month, LF_Year";
    req += " FROM         ListeProjets";
    req += " WHERE     (dateMep IS NOT NULL) AND (YEAR(dateMep) = "+this.Annee+" OR";
    req += " YEAR(dateT0) = "+this.Annee+") AND (dateT0 <> CONVERT(DATETIME, '1900-01-01 00:00:00', 102)) AND (LF_Month = "+this.LF_Month+") AND (LF_Year = "+this.LF_Year+")";


    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    java.util.Date theDate;
    java.util.Date DdateMep;

    String dateMep="";

    try {
     while (rs.next())
      {

        int idRoadmap=rs.getInt("idRoadmap");
        Roadmap theRoadmap = new Roadmap(idRoadmap);
        theRoadmap.nomSt= rs.getString("nomSt");
        theRoadmap.version= rs.getString("version");
        theRoadmap.idVersionSt= rs.getString("idVersionSt");

        theRoadmap.DdateT0 = rs.getDate("dateT0");
        theRoadmap.dateT0 = Utils.getDateFrench(theRoadmap.DdateT0);

        theRoadmap.DdateEB = rs.getDate("dateEB");
        theRoadmap.dateEB = Utils.getDateFrench(theRoadmap.DdateEB);

        theRoadmap.DdateTest = rs.getDate("dateTest");
        theRoadmap.dateTest = Utils.getDateFrench(theRoadmap.DdateTest);

        theRoadmap.DdateProd = rs.getDate("dateMep");
        theRoadmap.dateProd = Utils.getDateFrench(theRoadmap.DdateProd);

        theRoadmap.DdateMes = rs.getDate("dateMes");
        theRoadmap.dateMes = Utils.getDateFrench(theRoadmap.DdateMes);


        this.ListeRoadmap.addElement(theRoadmap);
      }


      }
      catch (SQLException s) {s.getMessage();}
  }


  public void getListeLF(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;

    if (this.Annee == null) this.Annee = Utils.getYear(myCnx.nomBase, myCnx,  st);


    req = "SELECT DISTINCT LF_Month";
    req+=" FROM         Roadmap";
    req+=" WHERE     (LF_Year = "+this.Annee+") AND (LF_Month <> - 1)";
    req+=" ORDER BY LF_Month";



    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        int lf= rs.getInt("LF_Month"); // nom du ST
        Integer theLF = new Integer(lf);

        this.ListeLF.addElement(theLF);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }

  public void getLignesPO(String nomBase,Connexion myCnx, Statement st, String orderBy,String sens , String KeyWords){
    ResultSet rs;

    if (this.Annee == null) this.Annee = Utils.getYear(myCnx.nomBase, myCnx,  st);


    req = "SELECT     PlanOperationnel.idWebPo, PlanOperationnel.nomProjet, PlanOperationnel.charge, PlanOperationnel.LF, PlanOperationnel.Annee, Service.idService, ";
    req+="                  PlanOperationnel.codeChronos, PlanOperationnelEtat.idEtatPo";
    req+=" FROM         PlanOperationnel INNER JOIN";
    req+="                   idWebPo ON PlanOperationnel.idWebPo = idWebPo.idWebPO INNER JOIN";
    req+="                   Service ON idWebPo.nomMOA = Service.nomServicePO LEFT OUTER JOIN";
    req+="                   PlanOperationnelEtat ON PlanOperationnel.idWebPo = PlanOperationnelEtat.idWebPo";
    req+=" GROUP BY PlanOperationnel.idWebPo, PlanOperationnel.nomProjet, PlanOperationnel.charge, PlanOperationnel.idPoMoa, PlanOperationnel.LF, ";
    req+="                   PlanOperationnel.Annee, PlanOperationnel.idPoSi, PlanOperationnel.idPoMacroSt, PlanOperationnel.Priorite, PlanOperationnel.MacroSt, ";
    req+="                   PlanOperationnel.chefProjet, PlanOperationnel.codeChronos, PlanOperationnel.dateImport, PlanOperationnelEtat.idEtatPo, Service.idService";
    req+=" HAVING      (PlanOperationnel.Annee = '"+this.Annee+"') AND (Service.idService = "+idMOA+") AND (PlanOperationnel.dateImport IN";
    req+="                       (SELECT     MAX(dateImport) AS dateImport";
    req+="                         FROM          PlanOperationnel AS PlanOperationnel_1))";

    if (!KeyWords.equals(""))
      req+=" AND (PlanOperationnel.nomProjet LIKE '%"+KeyWords+"%')";

    if (!orderBy.equals("") && !sens.equals(""))
      req+=" ORDER BY PlanOperationnel."+orderBy +" "+sens;


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String idWebPo= rs.getString("idWebPo"); // nom du ST
        String Enveloppe= ""; // nom du ST
        String nomProjet= rs.getString("nomProjet"); // nom du ST
        String descProjet= ""; // nom du ST
        String risqueProjet= ""; // etat du ST
        float charge= rs.getFloat("charge"); // nom du ST
        String gainProjet= "";
        String Priorite= "P1";
        totalCharge+=charge;
        String Chronos= rs.getString("codeChronos"); // etat du ST
        int idEtatPo= rs.getInt("idEtatPo");

        LignePO theLignePO = new LignePO(idWebPo,Enveloppe, nomProjet, descProjet, risqueProjet,charge,  gainProjet,  Priorite );
        theLignePO.codeChronos = Chronos;
        theLignePO.idEtat = idEtatPo;
        this.ListeLignePO.addElement(theLignePO);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }

  public void getLignesPOByYearByService(String nomBase,Connexion myCnx, Statement st, String Annee,int idService){
    ResultSet rs;

    if (this.Annee == null) this.Annee = Utils.getYear(myCnx.nomBase, myCnx,  st);


    req = "SELECT     PlanOperationnelClient.idWebPo, PlanOperationnelClient.nomProjet, PlanOperationnelClient.charge";
    req+=" FROM         PlanOperationnelClient INNER JOIN";
    req+="                   Service ON PlanOperationnelClient.Service = Service.nomServiceImputations";
    req+=" WHERE     (PlanOperationnelClient.Annee = "+Annee+") AND (Service.idService = "+idService+") AND (PlanOperationnelClient.etat <> 'Abandonn�')";
    req+=" ORDER BY PlanOperationnelClient.idWebPo ASC";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String idWebPo= rs.getString("idWebPo"); // nom du ST
        String Enveloppe= ""; // nom du ST
        String nomProjet= rs.getString("nomProjet"); // nom du ST
        String descProjet= ""; // nom du ST
        String risqueProjet= ""; // etat du ST
        float charge= rs.getFloat("charge"); // nom du ST
        String gainProjet= "";
        String Priorite= "P1";
        totalCharge+=charge;

        LignePO theLignePO = new LignePO(idWebPo,Enveloppe, nomProjet, descProjet, risqueProjet,charge,  gainProjet,  Priorite );
        this.ListeLignePO.addElement(theLignePO);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }

  public int getId(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    int maxId = 1;

    req = "SELECT     (MAX(idBudget) ) as idBudgetMax";
    req += " FROM         BudgetGlobal";
    req += " WHERE     Annee ="+ this.Annee;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        maxId = rs.getInt("idBudgetMax") +1; 
      }
    }
            catch (SQLException s) {s.getMessage();}
    return maxId;
  }
  
  public void getLignesPOAll(String nomBase,Connexion myCnx, Statement st, String KeyWords){
    ResultSet rs;

    Utils.getDate(this.dateImport);
    String [] myDate=this.dateImport.split(" ");
    //this.dateImport ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    if (myDate[1] == null)
      this.dateImport ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    else
      this.dateImport ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+" "+myDate[1]+"', 103)";


    req = "SELECT     PlanOperationnel.idWebPo, PlanOperationnel.Enveloppe, PlanOperationnel.nomProjet, PlanOperationnel.descProjet, PlanOperationnel.risqueProjet, ";
    req+="                   PlanOperationnel.charge, PlanOperationnel.LF, PlanOperationnel.Annee, PlanOperationnel.codeChronos, ";
    req+="                    PlanOperationnel.MacroSt, PlanOperationnel.chefProjet";
    req+=" FROM         PlanOperationnel";
    req+=" WHERE     (PlanOperationnel.dateImport = "+this.dateImport+")";
    if (!KeyWords.equals(""))
    {
      req+=" AND (MacroSt LIKE '%"+KeyWords+"%')";
    }



    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String idWebPo= rs.getString("idWebPo"); // nom du ST
        String Enveloppe= rs.getString("Enveloppe"); // nom du ST
        String nomProjet= rs.getString("nomProjet"); // nom du ST
        String descProjet= rs.getString("descProjet"); // nom du ST
        String risqueProjet= rs.getString("risqueProjet"); // etat du ST
        float charge= rs.getFloat("charge"); // nom du ST
        String gainProjet= risqueProjet;
        String Priorite= "P1";
        totalCharge+=charge;
        String Chronos= rs.getString("codeChronos"); // etat du ST

        int idEtatPo= -1;

        String MacroSt= rs.getString("MacroSt");
        String chefProjet= rs.getString("chefProjet"); // etat du ST

        LignePO theLignePO = new LignePO(idWebPo,Enveloppe, nomProjet, descProjet, risqueProjet,charge,  gainProjet,  Priorite );
        theLignePO.codeChronos = Chronos;
        theLignePO.idEtat = idEtatPo;
        theLignePO.macrost = MacroSt;
        theLignePO.chefprojet = chefProjet;
        this.ListeLignePO.addElement(theLignePO);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }


  public void getLignesPODelta(String nomBase,Connexion myCnx, Statement st, PO Po2Compare, String KeyWords){
    ResultSet rs;

    req = "SELECT     PlanOperationnel.idWebPo, PlanOperationnel.Enveloppe, PlanOperationnel.nomProjet, PlanOperationnel.descProjet, PlanOperationnel.risqueProjet, ";
    req+="                   PlanOperationnel.charge, PlanOperationnel.LF, PlanOperationnel.Annee, PlanOperationnel.codeChronos, ";
    req+="                    PlanOperationnel.MacroSt, PlanOperationnel.chefProjet";
    req+="    FROM         PlanOperationnel";
    req+=" WHERE     (dateImport = "+Po2Compare.dateImport+") and (idWebPo not in (select idWebPo from PlanOperationnel WHERE     (dateImport = "+this.dateImport+")) )";

    if (!KeyWords.equals(""))
    {
      req+=" AND (PlanOperationnel.MacroSt LIKE '%"+KeyWords+"%')";
    }

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String idWebPo= rs.getString("idWebPo"); // nom du ST
        String Enveloppe= rs.getString("Enveloppe"); // nom du ST
        String nomProjet= rs.getString("nomProjet"); // nom du ST
        String descProjet= rs.getString("descProjet"); // nom du ST
        String risqueProjet= rs.getString("risqueProjet"); // etat du ST
        float charge= rs.getFloat("charge"); // nom du ST
        String gainProjet= risqueProjet;
        String Priorite= "P1";
        totalCharge+=charge;
        String Chronos= rs.getString("codeChronos"); // etat du ST

        int idEtatPo= -1;

        String MacroSt= rs.getString("MacroSt");
        String chefProjet= rs.getString("chefProjet"); // etat du ST

        LignePO theLignePO = new LignePO(idWebPo,Enveloppe, nomProjet, descProjet, risqueProjet,charge,  gainProjet,  Priorite );
        theLignePO.codeChronos = Chronos;
        theLignePO.idEtat = idEtatPo;
        theLignePO.macrost = MacroSt;
        theLignePO.chefprojet = chefProjet;
        this.ListeLignePODelta.addElement(theLignePO);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }

  public void getLignesPODelta2(String nomBase,Connexion myCnx, Statement st, String Annee2Compare){
    ResultSet rs;

    req = "SELECT     PlanOperationnel.idWebPo, PlanOperationnel.Enveloppe, PlanOperationnel.nomProjet, PlanOperationnel.descProjet, PlanOperationnel.risqueProjet, ";
    req+="                   PlanOperationnel.charge, PlanOperationnel.LF, PlanOperationnel.Annee, PlanOperationnel.codeChronos, ";
    req+="                    PlanOperationnel.MacroSt, PlanOperationnel.chefProjet";
    req+="    FROM         PlanOperationnel";
    req+=" WHERE     (Annee = "+this.Annee+") and (idWebPo not in (select idWebPo from PlanOperationnel WHERE     (Annee = "+Annee2Compare+")) )";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String idWebPo= rs.getString("idWebPo"); // nom du ST
        String Enveloppe= rs.getString("Enveloppe"); // nom du ST
        String nomProjet= rs.getString("nomProjet"); // nom du ST
        String descProjet= rs.getString("descProjet"); // nom du ST
        String risqueProjet= rs.getString("risqueProjet"); // etat du ST
        float charge= rs.getFloat("charge"); // nom du ST
        String gainProjet= risqueProjet;
        String Priorite= "P1";
        totalCharge+=charge;
        String Chronos= rs.getString("codeChronos"); // etat du ST

        int idEtatPo= -1;

        String MacroSt= rs.getString("MacroSt");
        String chefProjet= rs.getString("chefProjet"); // etat du ST

        LignePO theLignePO = new LignePO(idWebPo,Enveloppe, nomProjet, descProjet, risqueProjet,charge,  gainProjet,  Priorite );
        theLignePO.codeChronos = Chronos;
        theLignePO.idEtat = idEtatPo;
        theLignePO.macrost = MacroSt;
        theLignePO.chefprojet = chefProjet;
        this.ListeLignePODelta.addElement(theLignePO);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }

  public void getLignesPODeltaCharge(String nomBase,Connexion myCnx, Statement st, PO Po2Compare, String KeyWords){
    ResultSet rs;

    req = "SELECT     PlanOperationnel.idWebPo, PlanOperationnel.nomProjet, PlanOperationnel.charge AS charge1, PlanOperationnel.Annee AS Annee1, ";
    req+= " PlanOperationnel.MacroSt, PlanOperationnel.chefProjet, PlanOperationnel.codeChronos, PlanOperationnel_1.Annee AS Annee2, PlanOperationnel_1.charge AS charge2, ";
    req+= "                   PlanOperationnel.charge - PlanOperationnel_1.charge AS delta";
    req+= " FROM         PlanOperationnel INNER JOIN";
    req+= "                   PlanOperationnel AS PlanOperationnel_1 ON PlanOperationnel.idWebPo = PlanOperationnel_1.idWebPo";
    req+= " WHERE     (PlanOperationnel.dateImport = "+this.dateImport+")  AND     (PlanOperationnel_1.dateImport = "+Po2Compare.dateImport+") AND (PlanOperationnel.charge - PlanOperationnel_1.charge <> 0)";
    if (!KeyWords.equals(""))
    {
      req+=" AND (PlanOperationnel.MacroSt LIKE '%"+KeyWords+"%')";
    }

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String idWebPo= rs.getString("idWebPo"); // nom du ST
        String nomProjet= rs.getString("nomProjet"); // nom du ST
        float charge1= rs.getFloat("charge1"); // nom du ST

        String MacroSt= rs.getString("MacroSt");
        String chefProjet= rs.getString("chefProjet"); // etat du ST
        String codeChronos= rs.getString("codeChronos");

        float charge2= rs.getFloat("charge2");

        LignePO theLignePO = new LignePO(idWebPo,"", nomProjet, "", "",charge1,  "",  "" );
        theLignePO.codeChronos = codeChronos;
        theLignePO.macrost = MacroSt;
        theLignePO.chefprojet = chefProjet;
        theLignePO.charge2 = charge2;
        this.ListeLignePODeltaCharge.addElement(theLignePO);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }

  public void getLignesPODeltaCharge2(String nomBase,Connexion myCnx, Statement st, String Annee2Compare){
    ResultSet rs;

    req = "SELECT     PlanOperationnel.idWebPo, PlanOperationnel.nomProjet, PlanOperationnel.charge AS charge1, PlanOperationnel.Annee AS Annee1, ";
    req+= " PlanOperationnel.MacroSt, PlanOperationnel.chefProjet, PlanOperationnel.codeChronos, PlanOperationnel_1.Annee AS Annee2, PlanOperationnel_1.charge AS charge2, ";
    req+= "                   PlanOperationnel.charge - PlanOperationnel_1.charge AS delta";
    req+= " FROM         PlanOperationnel INNER JOIN";
    req+= "                   PlanOperationnel AS PlanOperationnel_1 ON PlanOperationnel.idWebPo = PlanOperationnel_1.idWebPo";
    req+= " WHERE     (PlanOperationnel.Annee = "+this.Annee+") AND (PlanOperationnel_1.Annee = "+Annee2Compare+") AND (PlanOperationnel.charge - PlanOperationnel_1.charge <> 0)";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String idWebPo= rs.getString("idWebPo"); // nom du ST
        String nomProjet= rs.getString("nomProjet"); // nom du ST
        float charge1= rs.getFloat("charge1"); // nom du ST

        String MacroSt= rs.getString("MacroSt");
        String chefProjet= rs.getString("chefProjet"); // etat du ST
        String codeChronos= rs.getString("codeChronos");

        float charge2= rs.getFloat("charge2");

        LignePO theLignePO = new LignePO(idWebPo,"", nomProjet, "", "",charge1,  "",  "" );
        theLignePO.codeChronos = codeChronos;
        theLignePO.macrost = MacroSt;
        theLignePO.chefprojet = chefProjet;
        theLignePO.charge2 = charge2;
        this.ListeLignePODeltaCharge.addElement(theLignePO);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }

  public void getLignesPOEngage2(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs;

  for (int i=0; i< this.ListeLignePO.size(); i++){
    LignePO theLignePO = (LignePO)this.ListeLignePO.elementAt(i);

    req = "SELECT     SUM(RoadmapPO.Charge) AS totalCharge, COUNT(RoadmapPO.Charge) AS nb, RoadmapPO.Annee, St.nomSt, Roadmap.version, Roadmap.idRoadmap, ";
    req+="                  Roadmap.dateEB_init, Roadmap.dateEB, Roadmap.dateMep_init, Roadmap.dateMep, VersionSt.idVersionSt";
    req+=" FROM         RoadmapPO INNER JOIN";
    req+="                   Roadmap ON RoadmapPO.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                  VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                   St ON VersionSt.stVersionSt = St.idSt";
    req+=" GROUP BY RoadmapPO.idPO, RoadmapPO.Annee, Roadmap.version, St.nomSt, Roadmap.idRoadmap, Roadmap.dateEB, Roadmap.dateMep, ";
    req+="                   Roadmap.dateEB_init, Roadmap.dateMep_init, VersionSt.idVersionSt";
    req+= " HAVING      (RoadmapPO.idPO = "+theLignePO.idWebPo+") AND (RoadmapPO.Annee = "+this.Annee+")";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    //myCnx.trace("*************","rs",""+rs);
    int nb = 0;
    try {
      while (rs.next()) {

        theLignePO.chargeEngagee= rs.getFloat("totalCharge");;
        //myCnx.trace("*************","theLignePO.chargeEngagee",""+theLignePO.chargeEngagee);

        theLignePO.nbEntree= rs.getInt("nb");
        if (theLignePO.nbEntree > 0) theLignePO.display =""; else theLignePO.display = "none";
        theLignePO.totalChargeEngagee+=theLignePO.chargeEngagee;
        if (nb > 0) {
          theLignePO.descProjet += ",";
          theLignePO.risqueProjet += ",";
        }

        String nomSt = rs.getString("nomSt");
        String version = rs.getString("version");

        String myDesc = nomSt+"-"+version ;

        int idRoadmap = rs.getInt("idRoadmap");
        theLignePO.descProjet+="<a href='DescriptionProjet.jsp?idRoadmap="+idRoadmap+"' target='_blank'>"+myDesc+"</a>";
        theLignePO.risqueProjet+=myDesc;
        Roadmap theRoadmap = new Roadmap(idRoadmap);
        theRoadmap.description = myDesc;

        if (theLignePO.chargeEngagee > 0)
           theRoadmap.ChargePO = ""+theLignePO.chargeEngagee;
         else
           theRoadmap.ChargePO = "";

        Date DdateEB_init = rs.getDate("dateEB_init");
        if (DdateEB_init != null)
          theRoadmap.dateEB_init = ""+DdateEB_init.getDate()+"/"+(DdateEB_init.getMonth()+1) + "/"+(DdateEB_init.getYear() + 1900);
        else
          theRoadmap.dateEB_init = "";

        Date DdateEB = rs.getDate("dateEB");
        if (DdateEB != null)
          theRoadmap.dateEB = ""+DdateEB.getDate()+"/"+(DdateEB.getMonth()+1) + "/"+(DdateEB.getYear() + 1900);
        else
          theRoadmap.dateEB = "";

        Date dateMep_init = rs.getDate("dateMep_init");
        if (dateMep_init != null)
          theRoadmap.dateProd_init = ""+dateMep_init.getDate()+"/"+(dateMep_init.getMonth()+1) + "/"+(dateMep_init.getYear() + 1900);
        else
          theRoadmap.dateProd_init = "";

        Date dateMep = rs.getDate("dateMep");
        if (dateMep != null)
          theRoadmap.dateProd = ""+dateMep.getDate()+"/"+(dateMep.getMonth()+1) + "/"+(dateMep.getYear() + 1900);
        else
          theRoadmap.dateProd = "";

        String idVersionSt = rs.getString("idVersionSt");
        String myPage = "CreerSuivi.jsp?typeForm=Modification&etatFicheVersionSt=1&Admin=no&Load=yes";
        myPage+="&idVersionSt="+idVersionSt;
        myPage+="&nomSt="+nomSt;
        myPage+="&version="+version;

        theRoadmap.description = "<a href='"+myPage+"' target='_blank'>"+myDesc+"</a>";

        theLignePO.ListeRoadmap.addElement(theRoadmap);
        nb++;
      }
    }
    catch (SQLException s) {
      s.getMessage();
    }
    //theLignePO.dump();
    this.totalChargeEngagee+=theLignePO.totalChargeEngagee;
  }
}
public void getLignesPOEngage(String nomBase,Connexion myCnx, Statement st ){
ResultSet rs;

for (int i=0; i< this.ListeLignePO.size(); i++){
  LignePO theLignePO = (LignePO)this.ListeLignePO.elementAt(i);

  req = "SELECT     SUM(RoadmapPO.Charge) AS totalCharge, COUNT(RoadmapPO.Charge) AS nb, RoadmapPO.Annee, St.nomSt, Roadmap.version, Roadmap.idRoadmap, Roadmap.dateEB_init, Roadmap.dateEB, Roadmap.dateMep_init, Roadmap.dateMep, Roadmap.dateT0MOE, Roadmap.Etat,RoadmapPO.dateTest_devis, RoadmapPO.dateMep_devis, Roadmap.dateTest";
  req+="    FROM         RoadmapPO INNER JOIN";
  req+="                   Roadmap ON RoadmapPO.idRoadmap = Roadmap.idRoadmap INNER JOIN";
  req+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
  req+="                   St ON VersionSt.stVersionSt = St.idSt";
  req+=" GROUP BY RoadmapPO.idPO, RoadmapPO.Annee, Roadmap.version, St.nomSt, Roadmap.idRoadmap, Roadmap.dateEB, Roadmap.dateMep, ";
  req+="                   Roadmap.dateEB_init, Roadmap.dateMep_init, Roadmap.dateT0MOE, Roadmap.Etat,RoadmapPO.dateTest_devis, RoadmapPO.dateMep_devis, Roadmap.dateTest ";
  req+= " HAVING      (RoadmapPO.idPO = "+theLignePO.idWebPo+") AND (RoadmapPO.Annee = "+this.Annee+")";

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  //myCnx.trace("*************","rs",""+rs);
  int nb = 0;
  try {
    while (rs.next()) {
      theLignePO.chargeEngagee= rs.getFloat(1);;
      //myCnx.trace("*************","theLignePO.chargeEngagee",""+theLignePO.chargeEngagee);
      theLignePO.nbEntree= rs.getInt(2);
      if (theLignePO.nbEntree > 0) theLignePO.display =""; else theLignePO.display = "none";
      theLignePO.totalChargeEngagee+=theLignePO.chargeEngagee;
      if (nb > 0) {
        theLignePO.descProjet += ",";
        theLignePO.risqueProjet += ",";
      }
      String myDesc = rs.getString("nomSt")+"-"+rs.getString("version") ;
      int idRoadmap = rs.getInt("idRoadmap");
      theLignePO.descProjet+="<a href='DescriptionProjet.jsp?idRoadmap="+idRoadmap+"' target='_blank'>"+myDesc+"</a>";
      theLignePO.risqueProjet+=myDesc;
      Roadmap theRoadmap = new Roadmap(idRoadmap);
      theRoadmap.description = myDesc;
      theRoadmap.description = "<a href='DescriptionProjet.jsp?idRoadmap="+idRoadmap+"' target='_blank'>"+myDesc+"</a>";
      if (theLignePO.chargeEngagee > 0)
         theRoadmap.ChargePO = ""+theLignePO.chargeEngagee;
       else
         theRoadmap.ChargePO = "";

      theRoadmap.DdateEB_init = rs.getDate("dateEB_init");
      if (theRoadmap.DdateEB_init != null)
        theRoadmap.dateEB_init = ""+theRoadmap.DdateEB_init.getDate()+"/"+(theRoadmap.DdateEB_init.getMonth()+1) + "/"+(theRoadmap.DdateEB_init.getYear() + 1900);
      else
        theRoadmap.dateEB_init = "";

      theRoadmap.DdateEB = rs.getDate("dateEB");
      if (theRoadmap.DdateEB != null)
        theRoadmap.dateEB = ""+theRoadmap.DdateEB.getDate()+"/"+(theRoadmap.DdateEB.getMonth()+1) + "/"+(theRoadmap.DdateEB.getYear() + 1900);
      else
        theRoadmap.dateEB = "";

      theRoadmap.DdateProd_init  = rs.getDate("dateMep_init");
      if (theRoadmap.DdateProd_init != null)
        theRoadmap.dateProd_init = ""+theRoadmap.DdateProd_init.getDate()+"/"+(theRoadmap.DdateProd_init.getMonth()+1) + "/"+(theRoadmap.DdateProd_init.getYear() + 1900);
      else
        theRoadmap.dateProd_init = "";

      theRoadmap.DdateProd = rs.getDate("dateMep");
      if (theRoadmap.DdateProd != null)
        theRoadmap.dateProd = ""+theRoadmap.DdateProd.getDate()+"/"+(theRoadmap.DdateProd.getMonth()+1) + "/"+(theRoadmap.DdateProd.getYear() + 1900);
      else
        theRoadmap.dateProd = "";

      theRoadmap.DdateT0MOE = rs.getDate("dateT0MOE");
      if (theRoadmap.DdateT0MOE != null)
        theRoadmap.dateT0MOE = ""+theRoadmap.DdateT0MOE.getDate()+"/"+(theRoadmap.DdateT0MOE.getMonth()+1) + "/"+(theRoadmap.DdateT0MOE.getYear() + 1900);
      else
        theRoadmap.dateT0MOE = "";

      String Etat = rs.getString("Etat");
      if (Etat.equals("EB") || Etat.equals("TEST") || Etat.equals("PROD") || Etat.equals("PROD") || Etat.equals("MES"))
      {
        theRoadmap.DdateDevisRef = Utils.addDays(theRoadmap.DdateEB, 21);
        theRoadmap.dateDevisRef = "" + theRoadmap.DdateDevisRef.getDate() + "/" +
            (theRoadmap.DdateDevisRef.getMonth() + 1) + "/" +
            (theRoadmap.DdateDevisRef.getYear() + 1900);
      }
      else
        theRoadmap.dateDevisRef = "";

      theRoadmap.DdateTest_devis = rs.getDate("dateTest_devis");
      if (theRoadmap.DdateTest_devis != null)
        theRoadmap.dateTest_devis = ""+theRoadmap.DdateTest_devis.getDate()+"/"+(theRoadmap.DdateTest_devis.getMonth()+1) + "/"+(theRoadmap.DdateTest_devis.getYear() + 1900);
      else
        theRoadmap.dateTest_devis = "";

      theRoadmap.DdateProd_devis = rs.getDate("dateMep_devis");
      if (theRoadmap.DdateProd_devis != null)
        theRoadmap.dateProd_devis = ""+theRoadmap.DdateProd_devis.getDate()+"/"+(theRoadmap.DdateProd_devis.getMonth()+1) + "/"+(theRoadmap.DdateProd_devis.getYear() + 1900);
      else
        theRoadmap.dateProd_devis = "";

      theRoadmap.DdateTest = rs.getDate("dateTest");
      if (theRoadmap.DdateTest != null)
        theRoadmap.dateTest = ""+theRoadmap.DdateTest.getDate()+"/"+(theRoadmap.DdateTest.getMonth()+1) + "/"+(theRoadmap.DdateTest.getYear() + 1900);
      else
        theRoadmap.dateTest = "";

      theLignePO.ListeRoadmap.addElement(theRoadmap);
      nb++;
    }
  }
  catch (SQLException s) {
    s.getMessage();
  }
  //theLignePO.dump();
  this.totalChargeEngagee+=theLignePO.totalChargeEngagee;
}
}


public LignePO addListeLignesPO(String idWebPo,String Enveloppe, String nomProjet, String descProjet, String risqueProjet,float charge , String Gains, String Priorite)
{
  LignePO theLignePO = new LignePO(idWebPo, Enveloppe,  nomProjet,  descProjet,  risqueProjet, charge,Gains,Priorite);
  this.ListeLignePO.addElement(theLignePO);
  return theLignePO;
  }


  public String bd_InsertLignesRoadmap(String nomBase,Connexion myCnx, Statement st, String transaction, int theLF_Month, int theLF_Year){
    ResultSet rs=null;

    int nb=0;
    req =  "SELECT count(*) as nb FROM Roadmap WHERE LF_Month='"+theLF_Month+"' AND      (LF_Year = "+theLF_Year+") ";
    rs = myCnx.ExecReq(st, nomBase, req);
    try { rs.next();
        nb= rs.getInt(1);
      } catch (SQLException s) {s.getMessage();}

    if (nb !=0) return "OK";

    transaction theTransaction = new transaction (transaction);

    theTransaction.begin(nomBase,myCnx,st);

    req =  "DELETE FROM Roadmap WHERE LF_Month='"+theLF_Month+"' AND      (LF_Year = "+theLF_Year+") ";
     if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}


     for (int i=0; i < this.ListeRoadmap.size(); i++)
     {
       Roadmap theRoadmap = (Roadmap)this.ListeRoadmap.elementAt(i);
       
       String str_theDateT0="NULL";
        if (theRoadmap.DdateT0 != null)
        {
          str_theDateT0 = ""+theRoadmap.DdateT0.getDate()+"/"+(theRoadmap.DdateT0.getMonth()+1) + "/"+(theRoadmap.DdateT0.getYear() + 1900);
          str_theDateT0 ="convert(datetime, '"+str_theDateT0+"', 103)";
        }

       String str_theDateEB="NULL";
        if (theRoadmap.DdateEB != null)
        {
          str_theDateEB = ""+theRoadmap.DdateEB.getDate()+"/"+(theRoadmap.DdateEB.getMonth()+1) + "/"+(theRoadmap.DdateEB.getYear() + 1900);
          str_theDateEB ="convert(datetime, '"+str_theDateEB+"', 103)";
        }
        
       String str_theDateTest="NULL";
        if (theRoadmap.DdateTest != null)
        {
          str_theDateTest = ""+theRoadmap.DdateTest.getDate()+"/"+(theRoadmap.DdateTest.getMonth()+1) + "/"+(theRoadmap.DdateTest.getYear() + 1900);
          str_theDateTest ="convert(datetime, '"+str_theDateTest+"', 103)";
        }
        
       String str_theDateProd="NULL";
        if (theRoadmap.DdateProd != null)
        {
          str_theDateProd = ""+theRoadmap.DdateProd.getDate()+"/"+(theRoadmap.DdateProd.getMonth()+1) + "/"+(theRoadmap.DdateProd.getYear() + 1900);
          str_theDateProd ="convert(datetime, '"+str_theDateProd+"', 103)";
        }
        
       String str_theDateMes="NULL";
        if (theRoadmap.DdateMes != null)
        {
          str_theDateMes = ""+theRoadmap.DdateMes.getDate()+"/"+(theRoadmap.DdateMes.getMonth()+1) + "/"+(theRoadmap.DdateMes.getYear() + 1900);
          str_theDateMes ="convert(datetime, '"+str_theDateMes+"', 103)";
        }
        
       req = "INSERT INTO Roadmap (linked_id,version, idVersionSt, dateT0,dateEB,dateTest, dateMep, dateMes,   LF_Month, LF_Year) VALUES ("
           +"'"+theRoadmap.id+"'"
           +",'"+theRoadmap.version.replaceAll("\u0092", "'").replaceAll("'","''").replaceAll("\r", "")+"'"
           +",'"+theRoadmap.idVersionSt+"'"
           +","+str_theDateT0+""
           +","+str_theDateEB+""
           +","+str_theDateTest+""
           +","+str_theDateProd+""
           +","+str_theDateMes+""
           +","+theLF_Month+""
           +","+theLF_Year+""
           +")";
       if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     }
     theTransaction.commit(nomBase,myCnx,st);
     return "OK";

  }
  public String bd_InsertLignesPO(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    req =  "DELETE FROM PlanOperationnel WHERE Annee='"+this.Annee+"' AND      (idPoMoa = "+this.idMOA+") ";
     if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}


     for (int i=0; i < this.ListeLignePO.size(); i++)
     {
       LignePO theLignePO = (LignePO)this.ListeLignePO.elementAt(i);
       req = "INSERT PlanOperationnel (idWebPo, Enveloppe, nomProjet, descProjet, risqueProjet, charge, Annee, idPoMoa, gainProjet, Priorite) VALUES ("
           +"'"+theLignePO.idWebPo+"',  '"
           +theLignePO.Enveloppe+"',  '"
           +theLignePO.nomProjet.replaceAll("\u0092", "'").replaceAll("'","''").replaceAll("\r", "")+"', '"
           +theLignePO.descProjet.replaceAll("\u0092", "'").replaceAll("'","''").replaceAll("\r", "")+"'"+", '"
           +theLignePO.risqueProjet.replaceAll("\u0092", "'").replaceAll("'","''").replaceAll("\r", "")+"', '"
           +theLignePO.charge+"', '"
           +this.Annee+"', '"
           +this.idMOA+"', '"
           +theLignePO.gainProjet.replaceAll("\u0092", "'").replaceAll("'","''").replaceAll("\r", "")+"', '"
           +theLignePO.Priorite
           +"')";
       if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     }

     return "OK";

  }
  
  public ErrorSpecific bd_InsertOneLignesPO(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();



     for (int i=0; i < this.ListeLignePO.size(); i++)
     {
       LignePO theLignePO = (LignePO)this.ListeLignePO.elementAt(i);
       
        req =  "DELETE FROM PlanOperationnelClient WHERE Annee='"+theLignePO.Annee+"' AND (idWebPo  = "+theLignePO.idWebPo+") ";
        if (i == 0)
        {
            myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertOneLignesPO","");
            if (myError.cause == -1) return myError;      
        }
        
       req = "INSERT INTO  PlanOperationnelClient (idWebPo, nomProjet,    Annee,  etat, Service,charge, chargeConsommee,   depensePrevue, depenseConsommee)";
       req+= " VALUES (";
       req+= "'"+theLignePO.idWebPo+"'";
       req+= ",";
       req+= "'"+theLignePO.nomProjet.replaceAll("\u0092", "'").replaceAll("'","''").replaceAll("\r", "")+"'";
       req+= ",";       
       req+= "'"+theLignePO.Annee+"'"; 
       req+= ",";  
       req+= "'"+theLignePO.etat+"'"; 
       req+= ",";    
       req+= "'"+theLignePO.service+"'";
       req+= ",";        
       req+= "'"+theLignePO.chargeDisponible+"'";
       req+= ",";        
       req+= "'"+theLignePO.chargeConsommee+"'";
       req+= ",";        
       req+= "'"+theLignePO.ChargePrevueForfait+"'";
       req+= ",";        
       req+= "'"+theLignePO.chargeConsommeForfait+"'";    
       req+= ")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertOneLignesPO","");
    if (myError.cause == -1) return myError;

     }

     return myError;

  }  
  
  public ErrorSpecific bd_InsertBudgetGlobal(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();

        req =  "DELETE FROM BudgetGlobal";
        myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertOneLignesPO","");
        if (myError.cause == -1) return myError;       
        
       req = "INSERT INTO BudgetGlobal (idBudget, nomProjet,Annee,etat )";
       req+= " SELECT DISTINCT PlanOperationnelClient.idWebPo, PlanOperationnelClient.nomProjet, PlanOperationnelClient.Annee,PlanOperationnelClient.etat";
       req+= " FROM PlanOperationnelClient";
       
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertOneLignesPO","");
    if (myError.cause == -1) return myError;

    return myError;

  }   

  public String bd_FreeLignesPO(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;


     for (int i=0; i < this.ListeLignePO.size(); i++)
     {
       LignePO theLignePO = (LignePO)this.ListeLignePO.elementAt(i);
       req =  "DELETE FROM idWebPO WHERE     (idWebPO = "+theLignePO.idWebPo+")";
        if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     }

     return "OK";

  }

  public String bd_Insert_tempPO(String nomBase,Connexion myCnx, Statement st, String transaction){
    //req = "delete  FROM    temp_PO WHERE (Service = '"+this.idMOA+"')  AND (Annee = '"+this.Annee+"')";
    req = "delete  FROM    temp_PO ";
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

    for (int i=0; i < this.ListeLignePO.size(); i++)
    {
      LignePO theLignePO = (LignePO)this.ListeLignePO.elementAt(i);
      req = "INSERT temp_PO (Service, Annee, idMOE, FicheMOE, ProjetClient, Charge, Engage, ChargeFloat, EngageFloat, etat, codeChronos) VALUES ("
          +this.idMOA+",  '"
          +this.Annee+"', '"
          +theLignePO.idWebPo+"', '"
          +theLignePO.nomProjet.replaceAll("\u0092", "'").replaceAll("'","''").replaceAll("\r", "")+"'"+", '"
          +theLignePO.risqueProjet.replaceAll("\u0092", "'").replaceAll("'","''").replaceAll("\r", "")+"'"+", '"
          +(""+theLignePO.charge).replace('.',',')+"', '"
          +(""+theLignePO.totalChargeEngagee).replace('.',',')+"', '"
          +theLignePO.charge+"', '"
          +theLignePO.totalChargeEngagee +"', '"
          +theLignePO.idEtat +"', '"
          +theLignePO.codeChronos
          +"')";
      if (myCnx.ExecUpdate(st,nomBase,req,false,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     }
    return "OK";
}



  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    LignePO theLignePO=null;
    System.out.println("==================================================");
    System.out.println("Annee="+this.Annee);
    System.out.println("idMacroSt="+this.idMacroSt);
    System.out.println("totalCharge="+this.totalCharge);
    System.out.println("dateImport="+this.dateImport);
    for (int i=0; i < this.ListeLignePO.size(); i++){
      theLignePO = (LignePO)this.ListeLignePO.elementAt(i);
      //theLignePO.dump();

    }
    System.out.println("==================================================");
    }

    public static void main(String[] args) {
      Connexion myCnx = null;
      Statement st, st2 = null;
      ResultSet rs = null;
      ResultSet rs2 = null;
      String req = "";
      String base = myCnx.getDate();
    st = myCnx.Connect();

      String nomProjet= "";
      String nomSt= "";
      String nomVersion= "";
      int Index = 0;

      st = Connexion.Connect();

      Calendar calendar = Calendar.getInstance();
      int currentYear = calendar.get(Calendar.YEAR);
      int currentMonth = calendar.get(Calendar.MONTH) + 1;

      PO thePO = new PO("2012",74,"1");
      thePO.LF_Month = -1;
      thePO.LF_Year = -1;
      thePO.getLignesPO(myCnx.nomBase,myCnx,st,"","" ,"");
      thePO.getLignesPOEngage(myCnx.nomBase,myCnx, st );
      //thePO.bd_Insert_tempPO(myCnx.nomBase,myCnx, st, "tempPO" );

      //thePO.getLignesRoadmap(Connexion.nomBase,myCnx,st);
      //thePO.bd_InsertLignesRoadmap(Connexion.nomBase, myCnx, st, "ManageLF", currentMonth, currentYear);
      /*
      for (int i=1; i <=7; i++)
      {
        //thePO.bd_InsertLignesRoadmap(Connexion.nomBase, myCnx, st, "ManageLF", currentMonth, currentYear);
        thePO.bd_InsertLignesRoadmap(Connexion.nomBase, myCnx, st, "ManageLF", i, currentYear);
      }
      */
      //thePO.getLignesPO(Connexion.nomBase,myCnx,st);
      //thePO.getListeExcel(Connexion.nomBase,myCnx,st);
      //System.out.println(thePO.Annee);
      Connexion.Unconnect(st);

  }
}

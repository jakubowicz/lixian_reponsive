package PO; 

import java.util.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import java.util.Vector;
import Projet.Roadmap;
import java.util.Calendar;
import General.Utils;
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
public class LignePO {
  public int id=-1;
  public int idEtat=-1;
  public int idDevis=-1;
  public String idWebPo="";
  public String Enveloppe="";
  public String Priorite="";
  public String nomProjet="";
  public String descProjet="";
  public String risqueProjet="";
  public String gainProjet="";

  public float charge=0;
  public float charge2=0;

  public float chargeDisponible = 0;
  public float chargePrelevee = 0;
  public float chargeEngagee = 0;
  public float chargeConsommee=0;
  public float chargeReestimee=0;
  
  public float chargeAffectee=0;
  
  public float ChargePrevueForfait = 0;
  public float chargeAffecteeForfait=0;
  public float chargeConsommeForfait=0;
  
  public String hypotheses="";

  public int nbEntree=0;
  public float totalChargeEngagee=0;
  public String display="";
  public String chefprojet="";
  public String macrost="";

  public String str_chargePrevue="";
  public String str_chargeEngagee="";
  public String str_chargeReestimee="";

  public String dateEB="";
  public String dateREAL="";
  public String dateMAQUETTE="";
  public String dateMep="";
  public String Annee="";
  public String nomServicePO="";
  public int idService=-1;

  public String codeChronos="";
  private String req = "";

  public java.util.Date DdateTest_devis = null;
  public String dateTest_devis = "";

  public java.util.Date DdateProd_devis = null;
  public String dateProd_devis = "";

  public java.util.Date DdateExtraction = null;
  public String dateExtraction = "";

  public Vector ListeRoadmap = new Vector(10);
  public String service="";
  public String etat="";

  public String part=""; // 7- PROFEECIE_Financement=8;
  public   String TypeCharge="Depense"; // 8-
  public   String codeSujet=""; // 1- PROFEECIE_Code=0
  public LignePO(){

  }

  public LignePO(int id){
    this.id=id;
  }

  public LignePO(String idWebPo){
    this.idWebPo=idWebPo;
  }

  public LignePO(String idWebPo,String Enveloppe, String nomProjet, String descProjet, String risqueProjet,float charge, String gainProjet, String Priorite ) {
    this.idWebPo=idWebPo;
    this.Enveloppe=Enveloppe;

    if (nomProjet !=null)
    {
      this.nomProjet = nomProjet;
      //this.nomProjet = this.nomProjet.replaceAll("\u0092", "'").replaceAll("'","''").replaceAll("\r", "");
    }

    if (descProjet !=null)
    {
      this.descProjet = descProjet;
      //this.descProjet = this.descProjet;
    }

    if (risqueProjet !=null)
    {
      this.risqueProjet = risqueProjet;
      //this.risqueProjet = this.risqueProjet.replaceAll("\u0092","'").replaceAll("'", "''").replaceAll("\r", "");
    }

    if (gainProjet !=null)
    {
      this.gainProjet = gainProjet;
      //this.gainProjet = this.gainProjet.replaceAll("\u0092","'").replaceAll("'", "''").replaceAll("\r", "");
    }

    this.charge=charge;
    this.Priorite=Priorite;

  }


  public String bd_delete(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;

       req =  "DELETE FROM idWebPO WHERE     (idWebPO = "+this.idWebPo+")";
       rs = myCnx.ExecReq(st, myCnx.nomBase, req);
       // if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}


     return "OK";
  }
  
  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();
    
        String str_dateExtraction ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
        if ((this.dateExtraction != null) &&(!this.dateExtraction.equals("")))
        {
         Utils.getDate(this.dateExtraction);
         str_dateExtraction ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
       }    

          req =  "UPDATE [PlanOperationnelClient]";
          req+= "   SET [nomProjet] = '" + this.nomProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") + "'";
          req+= "       ,[descProjet] = '" + this.descProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") + "'";
          req+= "       ,[charge] = '" + this.chargeDisponible + "'";
          req+= "       ,[depensePrevue] = '" + this.ChargePrevueForfait + "'";
          req+= "       ,[depenseConsommee] = '" + this.chargeConsommeForfait + "'";          
          req+= "       ,[Annee] = '" + this.Annee + "'";
          req+= "       ,[etat] = '" + this.etat + "'";
          
          req+= "       ,[Service] = '" + this.service.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") + "'";
          req+= "       ,[chargeConsommee] = '" + this.chargeConsommee + "'";
          req+= "       ,[dateExtraction] = " + str_dateExtraction + "";
          req+= "       ,[codeSujet] = '" + this.codeSujet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") + "'";
          req+= "       ,[Part] = '" + this.part.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") + "'";
          req+= "       ,[TypeCharge] = '" + this.TypeCharge.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") + "'";
          req+= "  WHERE idWebPo = "+ this.id;
          myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_update",""+this.id);
          if (myError.cause == -1) return myError;
          
          req =  "UPDATE [PlanOperationnelClient_simulation]";
          req+= " SET      [charge] = '" + this.chargeReestimee + "'";
          req+= "       ,[descProjet] = '" + this.hypotheses.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") + "'";          
          req+= "  WHERE idWebPo = "+ this.id;
          myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_update",""+this.id);
          if (myError.cause == -1) return myError;          

     
     return myError;

  }
  
  public ErrorSpecific bd_updateGlobal(String nomBase,Connexion myCnx, Statement st, String transaction, String old_idBudgetaire){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();
    
          req =  "UPDATE [BudgetGlobal]";
          req+= "   SET [idBudget] = '" + this.idWebPo + "'";
          req+= "       ,[Annee] = '" + this.Annee + "'";   
          req+= "       ,[NomProjet] = '" + this.nomProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") + "'";
          req+= "       ,[etat] = '" + this.etat + "'";
          req+= "       ,[OpexPrevu] = '" + this.chargeDisponible + "'";
          req+= "       ,[OpexConsomme] = '" + this.chargeConsommee + "'";
          req+= "       ,[CapexPrevu] = '" + this.ChargePrevueForfait + "'";
          req+= "       ,[CapexConsomme] = '" + this.chargeConsommeForfait + "'"; 
          req+= "       ,[Description] = '" + this.descProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") + "'";

          req+= "  WHERE id = "+ this.id;
          myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_update",""+this.id);
          if (myError.cause == -1) return myError;
          
     if (!old_idBudgetaire.equals(this.idWebPo))
     {
          req =  "UPDATE [PlanOperationnelClient]";
          req+= "   SET [idWebPo] = '" + this.idWebPo + "'";
          req+= "  WHERE idWebPo  = "+old_idBudgetaire;
          req+= "  AND Annee  = "+ this.Annee;
          
          myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_update",""+this.id);
          if (myError.cause == -1) return myError;    
     }
              
     
     return myError;

  }  
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();
    
        String str_dateExtraction ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
        if ((this.dateExtraction != null) &&(!this.dateExtraction.equals("")))
        {
         Utils.getDate(this.dateExtraction);
         str_dateExtraction ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
       }    

          req =  "INSERT INTO PlanOperationnelClient  ";
          req+="(idWebPo, nomProjet,charge, Annee,etat,service, chargeConsommee,dateExtraction, codeSujet, Part, TypeCharge, depensePrevue, depenseConsommee ) ";
          req+= "VALUES     ('";
          req+= this.id +"', '";
          req+= this.nomProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          req+= this.chargeDisponible +"', '";
          req+= this.Annee +"', '";
          req+= this.etat.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          req+= this.service.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', ";
          req+= this.chargeConsommee+", ";
          req+= str_dateExtraction +", '";
          req+= this.codeSujet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          req+= this.part.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          req+= this.TypeCharge.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
          req+= this.ChargePrevueForfait+", ";
          req+= this.chargeConsommeForfait+" ";
          req+= ")";   
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

     

     return myError;

  }
  
  public ErrorSpecific bd_insertGlobal(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();

          req =  "INSERT INTO BudgetGlobal  ";
          req+="( idBudget, Annee, NomProjet, Etat, OpexPrevu, OpexConsomme, CapexPrevu, CapexConsomme,  Description ) ";
          req+= "VALUES     ('";
          req+= this.idWebPo +"', '";
          req+= this.Annee +"', '";
          req+= this.nomProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";
          req+= this.etat.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"', '";          
          req+= this.chargeDisponible +"', '";
          req+= this.chargeConsommee+"', '";
          req+= this.ChargePrevueForfait+"', '";
          req+= this.chargeConsommeForfait+"', '";
          req+= this.descProjet.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''") +"'";   
          req+= ")";   
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

    req="SELECT     TOP (1) id FROM   BudgetGlobal ORDER BY id DESC";
      rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt(1);
        } catch (SQLException s) {s.getMessage();}
    
     return myError;

  }  
  
  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction, String serviceImputation){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();

    req =  "DELETE FROM PlanOperationnelClient WHERE     (idWebPO = "+this.id+")";
    req +=  " AND Service='"+serviceImputation+"'";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

     return myError;
  }
  
  public ErrorSpecific bd_deleteGlobal(String nomBase,Connexion myCnx, Statement st, String transaction, String serviceImputation){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();

    req = "DELETE  FROM PlanOperationnelClient WHERE     (idWebPo = "+this.idWebPo+") AND (Annee = "+this.Annee+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;
    
    req =  "DELETE FROM BudgetGlobal WHERE     (id = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

     return myError;
  }
  
  public int checkUnicity(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    int nb = 1;

    req = "SELECT     COUNT(idBudget) AS nb FROM         BudgetGlobal";
    req += " WHERE     Annee ="+ this.Annee;
    req += " AND     idBudget ="+ this.idWebPo;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        nb = rs.getInt("nb"); 
      }
    }
            catch (SQLException s) {s.getMessage();}
    return nb;
  }
  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st, int idService, int Annee ){
    ResultSet rs;
    String req="";

    req="SELECT DISTINCT";
    req+="                      PlanOperationnelClient.nomProjet, PlanOperationnelClient.Annee, ";
    req+="                         PlanOperationnelClient.etat,  PlanOperationnelClient.charge,";
    req+="                         PlanOperationnelClient.chargeConsommee, PlanOperationnelClient.codeSujet, PlanOperationnelClient.Part,";
    req+="                         PlanOperationnelClient.TypeCharge, (CAST(PlanOperationnelClient.descProjet AS varchar(255))) as  descProjet, PlanOperationnelClient.depensePrevue, PlanOperationnelClient.depenseConsommee ";
    req+="      FROM         PlanOperationnelClient INNER JOIN";
    req+="                         Service ON PlanOperationnelClient.Service = Service.nomServiceImputations  AND ";
    req+="                         PlanOperationnelClient.Annee = PlanOperationnelClient.Annee";
    req+="      WHERE     (Service.idService = "+idService+") AND (PlanOperationnelClient.Annee = "+Annee+") and idWebPo = "+this.id;


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
   try {
     while (rs.next()) {
        Roadmap theRoadmap = new Roadmap(-1);
        this.Annee = rs.getString("Annee");
        this.nomProjet = rs.getString("nomProjet");
        this.etat = rs.getString("etat");
        this.chargeDisponible = rs.getFloat("charge");
        this.chargeConsommee = rs.getFloat("chargeConsommee");
        this.codeSujet = rs.getString("codeSujet");
        this.part = rs.getString("Part");
        this.TypeCharge = rs.getString("TypeCharge");
        this.descProjet=rs.getString("descProjet");
        if (this.descProjet == null) this.descProjet = "";
        this.ChargePrevueForfait = rs.getFloat("depensePrevue");
        this.chargeConsommeForfait = rs.getFloat("depenseConsommee");        

        //myCnx.trace("@@99------","nomProjet/Charge/chargeConsommee",""+theRoadmap.nomProjet+"/"+theRoadmap.Charge+"/"+theRoadmap.chargeConsommee);

     }
   }
catch (SQLException s) { }

  }
  
  public void getAllFromBdGlobal(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    String req="";

    req="SELECT     idBudget, CodeSujet, Annee, NomProjet, Etat, OpexPrevu, OpexConsomme, CapexPrevu, CapexConsomme, Programme, Description";
    req+=" FROM         BudgetGlobal";
    req+=" WHERE     id = " + this.id;


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
   try {
     while (rs.next()) {
        Roadmap theRoadmap = new Roadmap(-1);
        this.idWebPo = rs.getString("idBudget");
        this.codeSujet = rs.getString("codeSujet");
        this.Annee = rs.getString("Annee");
        this.nomProjet = rs.getString("NomProjet");
        this.etat = rs.getString("Etat");
        this.chargeDisponible = rs.getFloat("OpexPrevu");
        //this.chargeConsommee = rs.getFloat("OpexConsomme");
        this.ChargePrevueForfait = rs.getFloat("CapexPrevu");
        //this.chargeConsommeForfait = rs.getFloat("CapexConsomme");          
        this.descProjet=rs.getString("Description");
        if (this.descProjet == null) this.descProjet = "";

     }
   }
catch (SQLException s) { }
   
   
    req="SELECT     SUM(charge) AS totalChargePrevue, SUM(chargeConsommee) AS totalChargeConsommee, SUM(depensePrevue) AS totaldepensePrevuee, SUM(depenseConsommee) AS totaldepenseConsommee";
    req+=" FROM         PlanOperationnelClient";
    req+=" WHERE     (idWebPo = "+this.idWebPo+") AND (Annee = "+this.Annee+")   ";
    
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
   try {
     while (rs.next()) {
        this.chargeAffectee = rs.getFloat("totalChargePrevue");
        //this.chargeConsommee = rs.getFloat("totalChargeConsommee");     
        this.chargeAffecteeForfait = rs.getFloat("totaldepensePrevuee");
        //this.chargeConsommeForfait = rs.getFloat("totaldepenseConsommee");             

     }
   }
catch (SQLException s) { }
    this.chargeConsommee = getChargeEngageGlobal( nomBase, myCnx,  st, Integer.parseInt(this.Annee) ,   "OPEX" );  
    this.chargeConsommeForfait = getChargeEngageGlobal( nomBase, myCnx,  st, Integer.parseInt(this.Annee) ,   "CAPEX" ); 
   
  }  
  
  public float getChargeEngage(String nomBase,Connexion myCnx, Statement st, int Annee,  int idService, String Type ){
      ResultSet rs;
      float ChargeEngage=0;
      
    req="SELECT     SUM(DevisBudget.ChargePrelevee) AS totalChargeEngagee";
    req+=" FROM         DevisBudget INNER JOIN";
    req+="                       Devis ON DevisBudget.idDevis = Devis.id INNER JOIN";
    req+="                      Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                      VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt";
    req+=" WHERE     (DevisBudget.idPO = "+this.idWebPo+") AND (DevisBudget.Annee = "+Annee+") AND (VersionSt.serviceMoeVersionSt = "+idService+") AND (DevisBudget.Type = '"+Type+"')";
      
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
   try {
     while (rs.next()) {
        ChargeEngage = rs.getFloat("totalChargeEngagee");
     }
   }
catch (SQLException s) { }
   
      return ChargeEngage;
  }  
  
  public float getChargeEngageGlobal(String nomBase,Connexion myCnx, Statement st, int Annee,   String Type ){
      ResultSet rs;
      float ChargeEngage=0;
      
    req="SELECT     SUM(DevisBudget.ChargePrelevee) AS totalChargeEngagee";
    req+=" FROM         DevisBudget INNER JOIN";
    req+="                       Devis ON DevisBudget.idDevis = Devis.id INNER JOIN";
    req+="                      Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                      VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt";
    req+=" WHERE     (DevisBudget.idPO = "+this.idWebPo+") AND (DevisBudget.Annee = "+Annee+")  AND (DevisBudget.Type = '"+Type+"')";
      
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
   try {
     while (rs.next()) {
        ChargeEngage = rs.getFloat("totalChargeEngagee");
     }
   }
catch (SQLException s) { }
   
      return ChargeEngage;
  }  
  
  public void getAllFromBdReestime(String nomBase,Connexion myCnx, Statement st, int idService, int Annee ){
    ResultSet rs;
    String req="";

    req="SELECT DISTINCT";
    req+="                      PlanOperationnelClient_simulation.nomProjet, PlanOperationnelClient_simulation.Annee, ";
    req+="                         PlanOperationnelClient_simulation.etat,  PlanOperationnelClient_simulation.charge,";
    req+="                         PlanOperationnelClient_simulation.chargeConsommee, PlanOperationnelClient_simulation.codeSujet, PlanOperationnelClient_simulation.Part,";
    req+="                         PlanOperationnelClient_simulation.TypeCharge, (CAST(PlanOperationnelClient_simulation.descProjet AS varchar(255))) as  descProjet ";
    req+="      FROM         PlanOperationnelClient_simulation INNER JOIN";
    req+="                         Service ON PlanOperationnelClient_simulation.Service = Service.nomServiceImputations  AND ";
    req+="                         PlanOperationnelClient_simulation.Annee = PlanOperationnelClient_simulation.Annee";
    req+="      WHERE     (Service.idService = "+idService+") AND (PlanOperationnelClient_simulation.Annee = "+Annee+") and idWebPo = "+this.id;


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
   try {
     while (rs.next()) {
        this.chargeReestimee = rs.getFloat("charge");
        this.hypotheses=rs.getString("descProjet");
        if (this.hypotheses == null) this.hypotheses = "";

     }
   }
catch (SQLException s) { }

  }  
  
  public void getListeRoadmap(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    int nbCnx=0;
    int totalCharge= 0;
    int totalChargeEngagee= 0;
    String req="";

    req="SELECT St.nomSt, Roadmap.version, Roadmap.description, Roadmap.Charge FROM   Roadmap INNER JOIN   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN St ON VersionSt.stVersionSt = St.idSt WHERE (Roadmap.idPO = "+this.idWebPo+")";

    req="SELECT     St.nomSt, Roadmap.version, Roadmap.description, RoadmapPO.Charge, RoadmapPO.Annee";
    req+="    FROM         Roadmap INNER JOIN";
    req+="                  VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                  St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                  RoadmapPO ON Roadmap.idRoadmap = RoadmapPO.idRoadmap";
    req+=" WHERE     (RoadmapPO.idPO = "+this.idWebPo+") AND (RoadmapPO.Annee = "+this.Annee+")";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String nom="";
    String acteur="";
    int etat=-1;
    int  id;
    int  idRoadmap;
    Date theDate;
    String str_theDate;
    try {
      while (rs.next()) {
        String nomSt= rs.getString("nomSt"); // nom du ST
        String version= rs.getString("version"); // nom du ST
        String description= rs.getString("description"); // nom du ST
        float Charge= rs.getFloat("Charge"); // nom du ST
        totalChargeEngagee+=Charge;

            //
      nbCnx++;

      Roadmap theLigneRoadmap = new Roadmap(
                 nomSt,
                 "",
                 "",
                 version,
                 description,
                 "",
                 "",
                 this.idWebPo,
                 ""+Charge,
                 "",
                 "",
                 "",
                 "",
                 "",
                 "",
                 "",
                 "",
                 "",
                 "",
                 -1,
                 ""
                 );

        //theLigneRoadmap.dump();
        this.ListeRoadmap.addElement(theLigneRoadmap);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }

  public void getListeRoadmapClient(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    int nbCnx=0;
    int totalCharge= 0;
    int totalChargeEngagee= 0;
    String req="";

    req="SELECT     Roadmap.idRoadmap, St.nomSt, Roadmap.version, Roadmap.dateT0, Roadmap.dateEB, Roadmap.dateTest, Roadmap.dateMep, Roadmap.Etat, ";
    req+="                   VersionSt.serviceMoaVersionSt, VersionSt.serviceMoeVersionSt, Roadmap.idBrief";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                   St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     (Roadmap.idPO = "+this.idWebPo+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND (YEAR(Roadmap.dateT0) = "+this.Annee+" OR";
    req+="                   YEAR(Roadmap.dateEB) = "+this.Annee+" OR";
    req+="                   YEAR(Roadmap.dateTest) = "+this.Annee+" OR";
    req+="                   YEAR(Roadmap.dateMep) = "+this.Annee+") AND (VersionSt.serviceMoaVersionSt = "+this.idService+") OR";
    req+="                   (Roadmap.idPO = "+this.idWebPo+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND (YEAR(Roadmap.dateT0) = "+this.Annee+" OR";
    req+="                   YEAR(Roadmap.dateEB) = "+this.Annee+" OR";
    req+="                   YEAR(Roadmap.dateTest) = "+this.Annee+" OR";
    req+="                   YEAR(Roadmap.dateMep) = "+this.Annee+") AND (VersionSt.serviceMoeVersionSt = "+this.idService+")";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String nom="";
    String acteur="";
    int etat=-1;
    int  id;
    int  idRoadmap;
    Date theDate;
    String str_theDate;
    try {
      while (rs.next()) {
        idRoadmap = rs.getInt("idRoadmap");
        Roadmap theRoadmap = new Roadmap(idRoadmap);
        theRoadmap.nomSt= rs.getString("nomSt"); // nom du ST
        theRoadmap.version= rs.getString("version"); // nom du ST
        theRoadmap.EtatRoadmap= rs.getString("Etat"); // nom du ST
        theRoadmap.idBrief = rs.getInt("idBrief");

        this.ListeRoadmap.addElement(theRoadmap);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }

  public int getNbRoadmapClient(String nomBase,Connexion myCnx, Statement st){
    int nb=0;
    ResultSet rs;
    int nbCnx=0;
    int totalCharge= 0;
    int totalChargeEngagee= 0;
    String req="";

    req="SELECT     COUNT(*) AS nb";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                   St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     (Roadmap.idPO = "+this.idWebPo+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND (YEAR(Roadmap.dateT0) = "+this.Annee+" OR";
    req+="                   YEAR(Roadmap.dateEB) = "+this.Annee+" OR";
    req+="                   YEAR(Roadmap.dateTest) = "+this.Annee+" OR";
    req+="                   YEAR(Roadmap.dateMep) = "+this.Annee+") AND (VersionSt.serviceMoaVersionSt = "+this.idService+") OR";
    req+="                   (Roadmap.idPO = "+this.idWebPo+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND (YEAR(Roadmap.dateT0) = "+this.Annee+" OR";
    req+="                   YEAR(Roadmap.dateEB) = "+this.Annee+" OR";
    req+="                   YEAR(Roadmap.dateTest) = "+this.Annee+" OR";
    req+="                   YEAR(Roadmap.dateMep) = "+this.Annee+") AND (VersionSt.serviceMoeVersionSt = "+this.idService+")";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String nom="";
    String acteur="";
    int etat=-1;
    int  id;
    int  idRoadmap;
    Date theDate;
    String str_theDate;
    try {
      while (rs.next()) {
        nb = rs.getInt("nb");
      }
    }
            catch (SQLException s) {s.getMessage();}

            return nb;
  }

  public float getChargesEngagees  (String nomBase,Connexion myCnx, Statement st) throws Exception{
    float total=0;
    ResultSet rs;
    String req="";

    req="SELECT     SUM(ChargePrelevee) AS total";
    req+="    FROM         DevisBudget";
    //req+=" WHERE     (Type = '"+this.TypeCharge+"') AND (Annee = "+this.Annee+") AND (idPO = "+this.idWebPo+")";
    req+=" WHERE     (Type = '"+this.TypeCharge+"')  AND (idPO = "+this.idWebPo+")";

    if (this.idWebPo.equals("31663") )
    {
        int xx = 0;
        xx = 1;
        
    }
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        total= rs.getFloat("total");
      }
    }
            catch (SQLException s) {s.getMessage();}

   return total;
  }

  public void getCharges(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    int nbCnx=0;
    int totalCharge= 0;
    int totalChargeEngagee= 0;
    String req="";

    req="SELECT    ChargePrelevee, ChargeDispo";
    req+="    FROM         DevisBudget";
    req+=" WHERE     (idDevis = "+this.idDevis+") AND (Type = '"+this.TypeCharge+"') AND (Annee = "+this.Annee+")";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String nom="";
    String acteur="";
    int etat=-1;
    int  id;
    int  idRoadmap;
    Date theDate;
    String str_theDate;
    try {
      while (rs.next()) {
        this.chargeEngagee= rs.getFloat("ChargePrelevee"); // nom du ST
        this.charge= rs.getFloat("ChargeDispo"); // nom du ST
        totalChargeEngagee+=this.chargeEngagee;

      }
    }
            catch (SQLException s) {s.getMessage();}

  }
  public int getChargesByService(String nomBase,Connexion myCnx, Statement st, String serviceImputation){
    ResultSet rs;
    int nb=0;

    req="SELECT     PlanOperationnelClient.etat AS nomEtat, PlanOperationnelClient.charge AS chargePrevue, PlanOperationnelClient.chargeConsommee, ";
    req+="                       PlanOperationnelClient.depensePrevue, PlanOperationnelClient.depenseConsommee";
    req+=" FROM         PlanOperationnelClient ";
    req+=" WHERE     (PlanOperationnelClient.Annee = "+this.Annee+") AND (PlanOperationnelClient.Service = '"+serviceImputation+"') AND (PlanOperationnelClient.idWebPo = "+this.idWebPo+")";
    req+=" ORDER BY PlanOperationnelClient.idWebPo";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        this.etat = rs.getString("nomEtat");
        try{
            this.chargeDisponible= rs.getFloat("chargePrevue"); 
        }   
        catch (Exception s) {
            this.chargeDisponible=0;
        }
        try{
            this.chargeConsommee= rs.getFloat("chargeConsommee"); 
        }   
        catch (Exception s) {
            this.chargeConsommee=0;
        }
        try{
            this.ChargePrevueForfait= rs.getFloat("depensePrevue"); 
        }   
        catch (Exception s) {
            this.ChargePrevueForfait=0;
        }
        try{
            this.chargeConsommeForfait= rs.getFloat("depenseConsommee");    
        }   
        catch (Exception s) {
            this.chargeConsommeForfait=0;
        }        
        
        nb++;
            
      }
    }
            catch (SQLException s) {s.getMessage();}
    return nb;

  }
  
  public String bd_Update2(String nomBase,Connexion myCnx, Statement st){
   // myCnx.trace("@----------","myCnx.nomBase",""+myCnx.nomBase);
    try{
      st.execute("use "+myCnx.nomBase);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    //req = "UPDATE PlanOperationnelEtat SET idEtatPo='"+this.idEtat+"' WHERE idWebPo="+this.idWebPo;
    req = "DELETE FROM  PlanOperationnelEtat WHERE (idWebPo = "+this.idWebPo+")";
    myCnx.ExecUpdate( st,nomBase,  req,true);

    req = "INSERT INTO PlanOperationnelEtat (idWebPo,idEtatPo) VALUES ("
        +"'"+this.idWebPo+"'"
        +","+this.idEtat+""
        +")";
    myCnx.ExecUpdate( st,nomBase,  req,true);

     return "OK";
  }

  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    req = "DELETE FROM  PlanOperationnelEtat WHERE (idWebPo = "+this.idWebPo+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
    if (myError.cause == -1) return myError;

    req = "INSERT INTO PlanOperationnelEtat (idWebPo,idEtatPo) VALUES ("
        +"'"+this.idWebPo+"'"
        +","+this.idEtat+""
        +")";
     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
  if (myError.cause == -1) return myError;

     return myError;
  }

  public String bd_UpdateRoadmap(String nomBase,Connexion myCnx, Statement st, String transaction){

    for (int i=0; i < this.ListeRoadmap.size();i++)
    {
      Roadmap theRoadmap = (Roadmap)this.ListeRoadmap.elementAt(i);

      String str_dateTest_devis ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
      if ((theRoadmap.dateTest_devis != null) &&(!theRoadmap.dateTest_devis.equals("")))
      {
       Utils.getDate(theRoadmap.dateTest_devis);
       str_dateTest_devis ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
       }

       String str_dateProd_devis ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
       if ((theRoadmap.dateProd_devis != null) &&(!theRoadmap.dateProd_devis.equals("")))
       {
        Utils.getDate(theRoadmap.dateProd_devis);
        str_dateProd_devis ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
       }

       req =  "DELETE FROM RoadmapPO WHERE     (idPO = "+theRoadmap.idWebPo+")";
       if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

      req =  "INSERT INTO RoadmapPO  ( idRoadmap, idPO, Charge, Annee, dateTest_devis, dateMep_devis) ";
      req +=" VALUES     ('"+theRoadmap.id+"', '"+theRoadmap.idWebPo+"', '"+theRoadmap.Charge+"', '"+theRoadmap.Annee+"',"+str_dateTest_devis+","+str_dateProd_devis+")";

      if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

    }
     return "OK";
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("idWebPo="+this.idWebPo);
    System.out.println("Priorite="+this.Priorite);
    System.out.println("Enveloppe="+this.Enveloppe);
    System.out.println("nomProjet="+this.nomProjet);
    System.out.println("descProjet="+this.descProjet);
    System.out.println("gainProjet="+this.gainProjet);
    System.out.println("risqueProjet="+this.risqueProjet);
    System.out.println("charge="+this.charge);
    System.out.println("chargeEngagee="+this.chargeEngagee);
    System.out.println("totalChargeEngagee="+this.totalChargeEngagee);
    System.out.println("nbEntree="+this.nbEntree);
    System.out.println("display="+this.display);
    System.out.println("macrost="+this.macrost);
    System.out.println("chefprojet="+this.chefprojet);
    System.out.println("codeChronos="+this.codeChronos);
    System.out.println("idEtat="+this.idEtat);
    System.out.println("dateTest_devis="+this.dateTest_devis);
    System.out.println("dateProd_devis="+this.dateProd_devis);

    System.out.println("chargeConsommee="+this.chargeConsommee);
    System.out.println("etat="+this.etat);
    System.out.println("service="+this.service);
    System.out.println("Annee="+this.Annee);
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

    st = Connexion.Connect();
    try{
      st.execute("use BaseCarto");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    LignePO theLignePO = new LignePO("43634");
    theLignePO.idEtat = 3;
    theLignePO.bd_Update(myCnx.nomBase,myCnx, st,"");
    //theLignePO.bd_delete(myCnx.nomBase,myCnx, st);


    Connexion.Unconnect(st);

  }
}

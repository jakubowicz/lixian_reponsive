package Projet; 


import Composant.Excel;
import java.sql.*;
import java.util.*;
import accesbase.*;
import java.util.Date;
import PO.LignePO;
import Test.Campagne;
import Test.Test;
import Test.CategorieTest;
import Organisation.Collaborateur;
import Organisation.Service;
import Gouvernance.Baseline;
import PO.Chiffrage;
import Graphe.Vignette;
import ST.SI;
import ST.ST;
import Test.CampagneRelecture;
import accesbase.ErrorSpecific;
import org.apache.poi.ss.usermodel.Row;
import General.Utils;
import org.apache.poi.hssf.usermodel.HSSFCell;

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
public class Roadmap {

  public float totalCharge=0;
  public float totalChargeDisponible=0;
  public float totalChargeEngagee=0;
  public float totalChargeConsomee=0;
  public int compareT0 = -1;
  public int compareEB= -1;
  public int compareTEST= -1;
  public int comparePROD= -1;

  public int old_WeekT0= 0;
  public int old_WeekEB= 0;
  public int old_WeekTEST= 0;
  public int old_WeekPROD= 0;

  public int new_WeekT0= 0;
  public int new_WeekEB= 0;
  public int new_WeekTEST= 0;
  public int new_WeekPROD= 0;


  public int id = -1;
  public int idPere = -1;
  public int linked_id = -1;
  public int new_id = -1;
  public String nomSt = "";

  public boolean isProjet;
  public boolean isGenerique;
  public boolean isSupport;

  public String idVersionSt = "";
  public String oldversion = "";
  public String version = "";
  public String description = "";
  public String EtatRoadmap ="a demarrer";
  public String EtatRoadmapMOE ="a demarrer";
  public String Panier ="";
  public String idPO = "";
  public String Charge = "";
  public String chargeConsommee = "";
  public String ChargePO  = "";
  public String detailVentilationToken  = "";
  
  public String idWebPo="";
  public String Ordre = "";

  public String nomTypeSi = "";
  public String nomProjet = "";
  public String nomProjetClient = "";

  public String MOE = "";
  public String MOA = "";

  public int nbMOE = 0;
  public int nbMOA = 0;
  public int nbTesteurs = 0;
  public int nbActions=0;
  public int nbDependances=0;

  public String anneeT0;
  public String dateT0="";
  public int weekT0 =0;
  public int A_weekT0 =0;

  public String dateT0_old;

  public String anneeEB;
  public String dateEB="";
  public int weekEB =0;
  public int A_weekEB =0;

  public String dateEB_old;


  public String anneeTEST;
  public String dateTest="";
  public int weekTEST =0;
  public int A_weekTEST =0;

  public String dateTest_old;

  public String anneePROD;
  public String dateProd="";
  public int weekPROD =0;
  public int A_weekPROD =0;

  public String dateProd_old;

  public String anneeMES;
  public String dateMes="";
  public  int weekMES =0;
  public int A_weekMES =0;

  public String dateT0MOE="";
  public String dateSpecMOE ="";
  public String dateDevMOE ="";
  public String dateTestMOE="";
  public String dateLivrMOE ="";

  public String dateT0_init = "";
  public String dateEB_init = "";
  public String dateTest_init = "";
  public String dateProd_init = "";
  public String dateMes_init = "";

  public String dateT0MOE_init= null;
  public String dateSpecMOE_init = null;
  public String dateDevMOE_init = null;
  public String dateTestMOE_init = null;
  public String dateLivrMOE_init = null;

  public String Suivi = "";
  public String SuiviMOE = "";
  
  public String fichierAttache = "";

  public String Tendance = "";

  public String docEB = "";
  public String docDevis = "";
  public String docExigences = "";

  public int Annee;
  public String MotsClef = "";


  public java.util.Date DdateT0 = null;
  public java.util.Date DdateEB = null;
  public java.util.Date DdateTest = null;
  public java.util.Date DdateProd = null;
  public java.util.Date DdateMes = null;

  public java.util.Date DdateT0_new = null;
  public java.util.Date DdateEB_new = null;
  public java.util.Date DdateTest_new = null;
  public java.util.Date DdateProd_new = null;
  public java.util.Date DdateMes_new = null;

  public java.util.Date DdateT0_init = null;
  public java.util.Date DdateEB_init = null;
  public java.util.Date DdateTest_init = null;
  public java.util.Date DdateProd_init = null;
  public java.util.Date DdateMes_init = null;

  public java.util.Date DdateT0MOE= null;
  public java.util.Date DdateSpecMOE = null;
  public java.util.Date DdateDevMOE = null;
  public java.util.Date DdateTestMOE = null;
  public java.util.Date DdateLivrMOE = null;

  public java.util.Date DdateT0MOE_init = null;
  public java.util.Date DdateSpecMOE_init = null;
  public java.util.Date DdateDevMOE_init = null;
  public java.util.Date DdateTestMOE_init = null;
  public java.util.Date DdateLivrMOE_init = null;

  public java.util.Date DdateDevisRef = null;
  public String dateDevisRef = "";

  public java.util.Date DdateTest_devis = null;
  public String dateTest_devis = "";

  public java.util.Date DdateProd_devis = null;
  public String dateProd_devis = "";

  public Vector ListePhases = new Vector(10);
  public Vector ListeActions = new Vector(10);
  public Vector ListeRoadmap = new Vector(10);
  public static Vector ListeRoadmap_static = new Vector(10);
  public Vector ListeRoadmapWithDependances = new Vector(10);
  public Vector ListeActionsWithDependances = new Vector(10);
  public Vector ListeCampagnes = new Vector(10);
  public Vector ListeCategories = new Vector(10);
  public Vector ListeSuivi = new Vector(10);
  public Vector ListeDependancesT0 = new Vector(10);
  public Vector ListeDependancesEB = new Vector(10);
  public Vector ListeDependancesTEST = new Vector(10);
  public Vector ListeDependancesMEP = new Vector(10);
  public Vector ListeDependancesVSR = new Vector(10);
  public Vector ListeTypeJalons = new Vector(10);
  public Vector ListeTypeJalonsEssentiels = new Vector(10);
  
  public Vector ListeDevis = new Vector(10);

  public String type = "MOA";

  private String req = "";

  public float ChargePrevue = 0;
  public float ChargePrevueForfait = 0;
  
  public float ChargeTotalePrevue = 0;
  public float ChargeTotalePrevueForfait = 0;
  
  public Vector ListeCollaborateurs = new Vector(10);
  public int maxCollaborateurs=0;
  public float chargeConsommeForfait = 0;
  public float chargeConsommeInterne = 0;
  
  public int idEtatChargePrevue = 1;

  public float totalReparti = 0;
  public float totalMembre = 0;

  public String InitialesMOA;
  public Vector ListeCharges = new Vector(10);

  public int idEquipe=-1;

  public int LF_Month=-1;
  public int LF_Year=-1;

  public int idMembre=-1;

  public Vector ListeLignePO = new Vector(10);
  public Vector ListeBesoins = new Vector(10);
  public static Vector ListeLignePO_static = new Vector(10);

  public static Vector ListeMotClef = new Vector(10);

  public Vector ListeVignettes = new Vector(10);

  public String nomServiceMetier="";

  public Chiffrage theChiffrage=null;
  public Chiffrage theChiffrageMOA=null;

  public String part=""; // 7- PROFEECIE_Financement=8;
  public   String TypeCharge="Depense"; // 8-
  public   String codeSujet=""; // 1- PROFEECIE_Code=0

  public int standby=0;
  public int isDevis=1;
  public int idBrief=-1;
  public boolean isDecalage=false;
  
  public int idProfilSpecification=-1;
  public int idProfilDeveloppement=-1;
  public int idProfilTest=-1;

  
  public RoadmapProfil ProfilSpecification=null;
  public RoadmapProfil ProfilDeveloppement=null;
  public RoadmapProfil ProfilTest=null;
  
  public String dumpHtml="";

  // ------------------------------------------------------------------- //
  public boolean visible = true;
  
  public int x0_T0 = 0;
  public int x1_T0 = 0;
  public int y0_T0 = 0;
  public int y1_T0 = 0;  
  
  public int x0_EB = 0;
  public int x1_EB = 0;
  public int y0_EB = 0;
  public int y1_EB = 0;   
  
  public int x0_TEST = 0;
  public int x1_TEST = 0;
  public int y0_TEST = 0;
  public int y1_TEST = 0;     
  
  public int x0_PROD = 0;
  public int x1_PROD = 0;
  public int y0_PROD = 0;
  public int y1_PROD = 0;  
  
  public int x0_MES = 0;
  public int x1_MES = 0;
  public int y0_MES = 0;
  public int y1_MES = 0;    
  
  public float X_T0=0;
  public float X_EB=0;
  public float X_TEST=0;
  public float X_PROD=0;
  public float X_MES=0;

  public float X_T0_init=0;
  public float X_EB_init=0;
  public float X_TEST_init=0;
  public float X_PROD_init=0;
  public float X_MES_init=0;

  public float X_T0MOE=-1;
  public float X_SpecMOE=0;
  public float X_DevMOE=0;
  public float X_LivrMOE=0;

  public float X_END=0;
  public String Etat="ferme";

  public boolean traversantPrecedent = false;
  public boolean traversantSuivant = false;
  
  public String ROI = "";
  public String priorite = "";
  public String stabilite = "";
  public String pendule = "";
  
  public Vector ListeJoursFeries = new Vector(10);
  public Vector ListeJalons = new Vector(10);
  
  public int isMEX = 0;
  public int typologie = 0;
  public int typeRegroupement = 0;
  
  public String directory="";
  public String filename="";
  
  public int idJalon = -1;
  public int ordreJalon= -1;
  
  public String Leader = "";
  

  // ------------------------------------------------------------------- //

  public Roadmap(){

  }

  public Roadmap(int id){
    this.id = id;

  }

  public Roadmap(String nomBase,Connexion myCnx, Statement st,String nomSt, String version){
    this.nomSt = nomSt;
    this.version = version;

    version = version.replaceAll("\u0092","'").replaceAll("'"," ").replaceAll("&","et").replace('+',' ');
    ResultSet rs = null;
    String req = "SELECT     Roadmap.idRoadmap";
           req+=" FROM         Roadmap INNER JOIN";
           req+="            ST_ListeSt_All_Attribute ON Roadmap.idVersionSt = ST_ListeSt_All_Attribute.idVersionSt";
           req+=" WHERE     (ST_ListeSt_All_Attribute.nomSt = '"+nomSt+"') AND (Roadmap.version = '"+version+"')";


    //myCnx.trace("@01234","req",""+req);

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();

      this.id= rs.getInt(1);

      } catch (SQLException s) {s.getMessage(); }

  }

  public ErrorSpecific bd_UpdateEB(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();

    req = "UPDATE Roadmap SET ";
    req+="dumpHtml ='"+this.dumpHtml.replaceAll("\r","").replaceAll("0x22","'").replaceAll("\u0092","'").replaceAll("'","''")+"'";
    req+=" WHERE     (idRoadmap = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateConfig",""+this.id);
    if (myError.cause == -1) return myError;

    return myError;
  }

  public ErrorSpecific bd_UpdateConfig(String nomBase,Connexion myCnx, Statement st, String transaction, String value){
    ErrorSpecific myError = new ErrorSpecific();

    req = "UPDATE Config SET ";
    req+="valeur ='"+value+"'";
    req+=" WHERE     (nom = 'NEW_EXPORT-ROADMAP')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateConfig",""+this.id);
    if (myError.cause == -1) return myError;

    return myError;
  }

  public ErrorSpecific bd_ShiftCharges(String nomBase,Connexion myCnx, Statement st, Statement st2, String transaction, int nbDays){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs;
    
    this.ListeCharges.removeAllElements();
    
    req = "SELECT     projetMembre, semaine, Charge, anneeRef";
    req+=" FROM         PlanDeCharge";
    req+=" WHERE     idRoadmap = " + this.id;
    
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
   while(rs.next())
   {
      Charge theCharge = new Charge();
      theCharge.idMembre=rs.getInt("projetMembre");
      theCharge.semaine=rs.getInt("semaine");
      theCharge.hj=rs.getFloat("Charge");
      theCharge.anneeRef=rs.getInt("anneeRef");
      
      if ((nbDays == 7) && (theCharge.semaine == 52))
      {
          theCharge.semaine = 1;
          theCharge.anneeRef= theCharge.anneeRef+ 1;
      }
      else if ((nbDays == -7) && (theCharge.semaine == 1))
      {
          theCharge.semaine = 52;
          theCharge.anneeRef= theCharge.anneeRef - 1;
      } 
      else if (nbDays == 7)
      {
          theCharge.semaine= theCharge.semaine+1;

      } 
      else if (nbDays == -7)
      {
          theCharge.semaine= theCharge.semaine-1;

      }
      
      this.ListeCharges.addElement(theCharge);

    }
   }
  catch (SQLException s) { }  
   
            req = "DELETE PlanDeCharge  ";
            req += " WHERE idRoadmap ="+ this.id;    
            myError = myCnx.ExecUpdateTransaction(st2,nomBase,req,true,transaction, getClass().getName(),"bd_ShiftCharges",""+this.id);
            if (myError.cause == -1) return myError;   
            
    for (int i = 0; i < this.ListeCharges.size(); i++)
    {
        Charge theCharge = (Charge)this.ListeCharges.elementAt(i);
        req = "INSERT PlanDeCharge ( nomProjet,projetMembre, semaine, Charge, anneeRef, idRoadmap, etat)";
        req+=" VALUES (";
        req+="''"+",";
        req+=theCharge.idMembre+",";
        req+=theCharge.semaine+",";
        req+=theCharge.hj+",";
        req+=theCharge.anneeRef+",";
        req+=this.id+",";
        req+= "1";
        req+=")";   

            myError = myCnx.ExecUpdateTransaction(st2,nomBase,req,true,transaction, getClass().getName(),"bd_ShiftCharges",""+this.id);
            if (myError.cause == -1) return myError;  
    }

    
    return myError;
  }
  
  public ErrorSpecific bd_updateVentilationSupport(String nomBase,Connexion myCnx, Statement st, String transaction, int idMembre, int annee, int semaine){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs;
    
   
    req = "DELETE PlanDeChargeVentilation  ";
    req += " WHERE idRoadmap ="+ this.id;   
    req+= " AND (projetMembre = "+idMembre+") AND (anneeRef = "+annee+") AND (semaine = "+semaine+")";
    
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_ShiftCharges",""+this.id);
    if (myError.cause == -1) return myError;   
            
    for (int i = 0; i < this.ListeCharges.size(); i++)
    {
        Charge theCharge = (Charge)this.ListeCharges.elementAt(i);
        req = "INSERT PlanDeChargeVentilation (  projetMembre, semaine, Charge, anneeRef, idRoadmap, idSt)";
        req+=" VALUES (";
        req+=idMembre+",";
        req+=semaine+",";
        req+=theCharge.hj+",";
        req+=annee+",";
        req+=this.id+",";
        req+= theCharge.idObj;
        req+=")";   

            myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_ShiftCharges",""+this.id);
            if (myError.cause == -1) return myError;  
    }

    
    return myError;
  }  
  
  public ErrorSpecific bd_ShiftDays(String nomBase,Connexion myCnx, Statement st, String transaction, int nbDays){
    ErrorSpecific myError = new ErrorSpecific();
    long timeT0, timeEB, timeTest, timeProd, timeMes;
    String date_T0, date_EB, date_Test, date_Prod, date_Mes;
    
   if ((this.DdateT0 != null) && (this.DdateEB != null) && (this.DdateTest != null) && (this.DdateProd != null))
    {
        timeT0 = this.DdateT0.getTime();
        timeT0 += nbDays *24 * 60 * 60 *1000;
        timeT0 += 2*60*60*1000;
        this.DdateT0 = new Date(timeT0);
        date_T0 ="convert(datetime, '"+this.DdateT0.getDate()+"/"+(this.DdateT0.getMonth()+1)+"/"+(this.DdateT0.getYear() +1900)+"', 103)";
        
        timeEB = this.DdateEB.getTime();
        timeEB += nbDays *24 * 60 * 60 *1000;
        timeEB += 2*60*60*1000;
        this.DdateEB = new Date(timeEB);
        date_EB ="convert(datetime, '"+this.DdateEB.getDate()+"/"+(this.DdateEB.getMonth()+1)+"/"+(this.DdateEB.getYear() +1900)+"', 103)";
        
        timeTest = this.DdateTest.getTime();
        timeTest += nbDays *24 * 60 * 60 *1000;
        timeTest += 2*60*60*1000;
        this.DdateTest = new Date(timeTest);
        date_Test ="convert(datetime, '"+this.DdateTest.getDate()+"/"+(this.DdateTest.getMonth()+1)+"/"+(this.DdateTest.getYear() +1900)+"', 103)";
        
        timeProd = this.DdateProd.getTime();
        timeProd += nbDays *24 * 60 * 60 *1000;
        timeProd += 2*60*60*1000;
        this.DdateProd = new Date(timeProd); 
        date_Prod ="convert(datetime, '"+this.DdateProd.getDate()+"/"+(this.DdateProd.getMonth()+1)+"/"+(this.DdateProd.getYear() +1900)+"', 103)";  
        
        if (this.DdateMes != null)
        {
            timeMes = this.DdateMes.getTime();
            timeMes += nbDays *24 * 60 * 60 *1000;
            timeMes += 2*60*60*1000;
            this.DdateMes = new Date(timeMes); 
            date_Mes ="convert(datetime, '"+this.DdateMes.getDate()+"/"+(this.DdateMes.getMonth()+1)+"/"+(this.DdateMes.getYear() +1900)+"', 103)";  
        }
        else
            date_Mes ="NULL";
        
            req = "UPDATE Roadmap SET ";
            req +=" dateT0 ="+ date_T0 + ", ";
            req +=" dateEB ="+ date_EB + ", ";
            req +=" dateTest ="+ date_Test + ", ";
            req +=" dateMep ="+ date_Prod + ", ";
            req +=" dateMes ="+ date_Mes + "";
            req += " WHERE idRoadmap ="+ this.id;         
            myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
            if (myError.cause == -1) return myError;        
       
    } 
    
    return myError;
  }
  
  public ErrorSpecific bd_UpdateBrief(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs = null;
    ErrorSpecific myError = new ErrorSpecific();

    // rechercher toutes les roadmap partageant le meme numero de brief
    req = "SELECT     idRoadmap, Etat FROM         Roadmap WHERE     (idBrief = "+this.idBrief+")";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      int nb =0;
      while (rs.next()) {

        int idRoadmap = rs.getInt("idRoadmap");
        String Etat = rs.getString("Etat");
        if (!Etat.equals("PROD") && !Etat.equals("MES"))
        {
          nb++;
        }

      }

      if (nb == 0)
        {
          // Maj du Brief � clos
          req =  "UPDATE Briefs  set idEtat=9 WHERE idRoadmap="+this.idBrief;
          myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateBrief",""+this.id);
          if (myError.cause == -1) return myError;
        }


                } catch (SQLException s) {s.getMessage();}
        // si tous les etats sont PROD ou MES
        // Forcer etat du Brief a clos (9)

    return myError;
  }

  public void setJoursFeries(int annee){
      Connexion.trace("---------", "JourDeLan", "");
      // -------------------- Date Fixes ----------------------------------//
      Jalon JourDeLan = new Jalon("JourDeLan");
      Connexion.trace("1---------", "date", ""+annee+"/01/01");
      JourDeLan.date = new Date(""+annee+"/01/01"); 
      Connexion.trace("2---------", "date", ""+annee+"/01/01");
      this.ListeJoursFeries.addElement(JourDeLan);
      Connexion.trace("3---------", "date", ""+annee+"/01/01");
      
      Connexion.trace("---------", "FeteDuTRavail", "");
      Jalon FeteDuTRavail = new Jalon("FeteDuTRavail");
      FeteDuTRavail.date = new Date(""+annee+"/05/01");   
      this.ListeJoursFeries.addElement(FeteDuTRavail);
      
      Connexion.trace("---------", "Victoire8Mai1945", "");
      Jalon Victoire8Mai1945 = new Jalon("Victoire8Mai1945");
      Victoire8Mai1945.date = new Date(""+annee+"/05/08");   
      this.ListeJoursFeries.addElement(Victoire8Mai1945);             
      
      Connexion.trace("---------", "FeteNationale", "");
      Jalon FeteNationale = new Jalon("FeteNationale");
      FeteNationale.date = new Date(""+annee+"/07/14");   
      this.ListeJoursFeries.addElement(FeteNationale);    
      
      Connexion.trace("---------", "Assomption", "");
      Jalon Assomption = new Jalon("Assomption");
      Assomption.date = new Date(""+annee+"/08/15");   
      this.ListeJoursFeries.addElement(Assomption);     
      
      Connexion.trace("---------", "Toussaint", "");
      Jalon Toussaint = new Jalon("Toussaint");
      Toussaint.date = new Date(""+annee+"/11/01");   
      this.ListeJoursFeries.addElement(Toussaint);     
  
      Connexion.trace("---------", "Armistice", "");
      Jalon Armistice = new Jalon("Armistice");
      Armistice.date = new Date(""+annee+"/11/11");   
      this.ListeJoursFeries.addElement(Armistice);    
      
      Connexion.trace("---------", "Noel", "");
      Jalon Noel = new Jalon("Noel");
      Noel.date = new Date(""+annee+"/12/25");   
      this.ListeJoursFeries.addElement(Noel);     

      // -------------------- Date Variables ----------------------------------//    
      Connexion.trace("---------", "LundiDePaques", "");
      Jalon LundiDePaques = new Jalon("LundiDePaques");
      if (annee == 2015)
        LundiDePaques.date = new Date(""+annee+"/04/06");  
      else if (annee == 2016)
        LundiDePaques.date = new Date(""+annee+"/03/28");       
      else if (annee == 2017)
        LundiDePaques.date = new Date(""+annee+"/04/17");
      else if (annee == 2018)
        LundiDePaques.date = new Date(""+annee+"/04/02");      
      else if (annee == 2019)
        LundiDePaques.date = new Date(""+annee+"/04/22");     
      else if (annee == 2020)
        LundiDePaques.date = new Date(""+annee+"/04/12");        
      else 
        LundiDePaques.date = new Date(""+annee+"/04/12");        
      this.ListeJoursFeries.addElement(LundiDePaques);  
      
      Connexion.trace("---------", "JeudiAscencion", "");
      Jalon JeudiAscencion = new Jalon("JeudiAscencion");
      if (annee == 2015)
        JeudiAscencion.date = new Date(""+annee+"/05/14");  
      else if (annee == 2016)
        JeudiAscencion.date = new Date(""+annee+"/05/05");       
      else if (annee == 2017)      
        JeudiAscencion.date = new Date(""+annee+"/05/25");   
      else if (annee == 2018)      
        JeudiAscencion.date = new Date(""+annee+"/05/10");        
      else if (annee == 2019)      
        JeudiAscencion.date = new Date(""+annee+"/05/30");    
      else if (annee == 2020)      
        JeudiAscencion.date = new Date(""+annee+"/05/21");       
      else     
        JeudiAscencion.date = new Date(""+annee+"/05/21");        
      this.ListeJoursFeries.addElement(JeudiAscencion);      
      
      Connexion.trace("---------", "LundiPentecote", "");
      Jalon LundiPentecote = new Jalon("LundiPentecote");
       if (annee == 2015)
        LundiPentecote.date = new Date(""+annee+"/05/25");  
      else if (annee == 2016)
        LundiPentecote.date = new Date(""+annee+"/05/16");       
      else if (annee == 2017)    
        LundiPentecote.date = new Date(""+annee+"/06/05");   
      else if (annee == 2018)    
        LundiPentecote.date = new Date(""+annee+"/05/21");       
      else if (annee == 2019)    
        LundiPentecote.date = new Date(""+annee+"/06/09");            
      else if (annee == 2020)    
        LundiPentecote.date = new Date(""+annee+"/05/31");   
      else   
        LundiPentecote.date = new Date(""+annee+"/05/31");        
      this.ListeJoursFeries.addElement(LundiPentecote);  
  }
  
 public int getIdProjetJourFerie(String nomBase,Connexion myCnx, Statement st, int idCollaborateur ){
   int IdProjetJourFerie =0;
   ResultSet rs;
   //req = "SELECT     idMembre FROM     Membre WHERE     (nomMembre = 'Bytel')";
   req = "SELECT     Roadmap.idRoadmap";
   req+= " FROM         Roadmap INNER JOIN";
   req+= "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt";
   req+= " WHERE     (VersionSt.respMoeVersionSt = "+idCollaborateur+") AND (Roadmap.version <> '-- NON REGRESSION')";
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
   while(rs.next())
   {
      IdProjetJourFerie = rs.getInt("idRoadmap");
    }
   }
  catch (SQLException s) { }

  return IdProjetJourFerie;
 }
  
 public ErrorSpecific bd_InsertJalons(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();

    req = "DELETE FROM JalonsProjet WHERE idRoadmap = "+this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertJalons",""+this.id);
    if (myError.cause == -1) return myError;


     for (int i=0; i < this.ListeJalons.size(); i++)
     {
         
        Jalon theJalon = (Jalon)this.ListeJalons.elementAt(i);
        theJalon.idRoadmap = this.id;
        myError =theJalon.bd_insert(nomBase, myCnx, st, transaction);
        if (myError.cause == -1) return myError;
     }
     return myError;
  } 
 
 public void bd_InsertJalons(String nomBase,Connexion myCnx, Statement st){
      ErrorSpecific myError = new ErrorSpecific();

     for (int i=0; i < this.ListeJalons.size(); i++)
     {
         
        Jalon theJalon = (Jalon)this.ListeJalons.elementAt(i);
        theJalon.bd_insert(nomBase, myCnx, st);
     }
  }  
 
 public static void bd_deleteJalons(String nomBase,Connexion myCnx, Statement st){
      ErrorSpecific myError = new ErrorSpecific();

    String req = "DELETE FROM JalonsProjet";
    myCnx.ExecUpdate(st,nomBase,req,true);

  }  
  
  public ErrorSpecific bd_InsertJoursFeries(String nomBase,Connexion myCnx, Statement st, String transaction, int anneeRef, int idCollaborateur){
      ErrorSpecific myError = new ErrorSpecific();
      int semaine=0;
    ResultSet rs=null;
Connexion.trace("---------", "bd_InsertJoursFeries", "");
    req = "DELETE FROM PlanDeCharge WHERE idRoadmap = '"+this.id+"' AND anneeRef = '"+anneeRef+"'" +" AND projetMembre  = '"+idCollaborateur+"'";
   myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertJoursFeries",""+this.id);
   if (myError.cause == -1) return myError;


     for (int i=0; i < this.ListeJoursFeries.size(); i++)
     {
       Jalon theJourFerie = (Jalon)this.ListeJoursFeries.elementAt(i);
       semaine= Utils.getWeek(nomBase,myCnx, st,theJourFerie.date.getDate(), theJourFerie.date.getMonth() +1, theJourFerie.date.getYear() + 1900);
       if ((theJourFerie.date.getYear() + 1900) == anneeRef)
       {
        int nb =0;
        req = "SELECT     Charge as nb";
        req += " FROM         PlanDeCharge";
        req += " WHERE     (projetMembre = "+idCollaborateur+") AND (idRoadmap = "+this.id+") AND (anneeRef = '"+anneeRef+"') AND (projetMembre = "+idCollaborateur+") AND (semaine = "+semaine+")";          
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {     
      while (rs.next()) {
                nb = rs.getInt("nb");
            }
        } catch (SQLException s) {s.getMessage();}
    if (nb == 0)
        req="INSERT PlanDeCharge (nomProjet,projetMembre, semaine,Charge,anneeRef, idRoadmap) VALUES ('',"+idCollaborateur+", "+semaine+""+", "+1+", "+anneeRef+", "+this.id+")";
    else
    {
        req="UPDATE PlanDeCharge set Charge="+ (nb+1);
        req += " WHERE     (projetMembre = "+idCollaborateur+") AND (idRoadmap = "+this.id+") AND (anneeRef = '"+anneeRef+"') AND (projetMembre = "+idCollaborateur+") AND (semaine = "+semaine+")";  
    }
       }
   myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertJoursFeries",""+this.id);
   if (myError.cause == -1) return myError;
     }
     return myError;
  }   
  
  public void setDates(String nomBase,Connexion myCnx, Statement st){

    String jourT0 = "";
    String moisT0 = "";
    this.anneeT0 = "";
    if ((dateT0 != null) && (!dateT0.equals("null")) &&(!dateT0.equals("")) )
    {
     Utils.getDate(dateT0);
     jourT0 = Utils.Day;
     moisT0 = Utils.Month;
     anneeT0 = Utils.Year;
     if ((jourT0 != null) && (moisT0 != null) ) this.weekT0 = Utils.getWeek(nomBase,myCnx, st,Integer.parseInt(jourT0),Integer.parseInt(moisT0),Integer.parseInt(anneeT0));
     if (this.anneeT0.equals("1900") || this.anneeT0.equals("1995")) this.weekT0=0;
     this.A_weekT0 = Integer.parseInt(this.anneeT0) * 100 + this.weekT0;

     }


    String jourEB = "";
    String moisEB = "";
    this.anneeEB = "";

    if ((dateEB != null) && (!dateEB.equals("null")) &&(!dateEB.equals("")))
    {
     Utils.getDate(dateEB);
     jourEB = Utils.Day;
     moisEB = Utils.Month;
     anneeEB = Utils.Year;
     if ((jourEB != null) && (moisEB != null) ) weekEB = Utils.getWeek(nomBase,myCnx, st,Integer.parseInt(jourEB),Integer.parseInt(moisEB),Integer.parseInt(anneeEB));
          this.A_weekEB = Integer.parseInt(this.anneeEB) * 100 + this.weekEB;
     }

     String jourTEST = "";
     String moisTEST = "";
     this.anneeTEST = "";

     if ((dateTest != null) && (!dateTest.equals("null")) &&(!dateTest.equals("")))
     {
      Utils.getDate(dateTest);
      jourTEST = Utils.Day;
      moisTEST = Utils.Month;
      anneeTEST = Utils.Year;
      if ((jourTEST != null) && (moisTEST != null) ) this.weekTEST = Utils.getWeek(nomBase,myCnx, st,Integer.parseInt(jourTEST),Integer.parseInt(moisTEST),Integer.parseInt(anneeTEST));
      if (this.anneeTEST.equals("1900") || this.anneeTEST.equals("1995")) this.weekTEST=0;
      this.A_weekTEST = Integer.parseInt(this.anneeTEST) * 100 + this.weekTEST;
     }

     String jourPROD = "";
     String moisPROD = "";
     this.anneePROD = "";

     if ((dateProd !=null) && (!dateProd.equals("null"))&& (!dateProd.equals("")))
     {
      Utils.getDate(dateProd);
      jourPROD = Utils.Day;
      moisPROD = Utils.Month;
      anneePROD = Utils.Year;

      //if ((jourPROD != null) && (moisPROD != null) ) weekPROD = Utils.getWeek(Integer.parseInt(jourPROD),Integer.parseInt(moisPROD),Integer.parseInt(anneePROD));
      if ((jourPROD != null) && (moisPROD != null) )
      {
        weekPROD = Utils.getWeek( nomBase, myCnx,  st, Integer.parseInt(jourPROD),
                                 Integer.parseInt(moisPROD),
                                 Integer.parseInt(anneePROD));
        if ((weekPROD == 1) && (weekPROD < weekTEST) && (this.anneePROD.equals(anneeTEST) )) weekPROD =52;
      }
      this.A_weekPROD = Integer.parseInt(this.anneePROD) * 100 + this.weekPROD;
    }

    String jourMES = "";
    String moisMES = "";
    this.anneeMES = "";

    if ((dateMes != null) && (!dateMes.equals("null"))&& (!dateMes.equals("")))
    {
     Utils.getDate(dateMes);
     jourMES = Utils.Day;
     moisMES = Utils.Month;
     anneeMES = Utils.Year;
     //if ((jourMES != null) && (moisMES != null) ) weekMES = Utils.getWeek(Integer.parseInt(jourMES),Integer.parseInt(moisMES),Integer.parseInt(anneeMES));
     if ((jourMES != null) && (moisMES != null) )
     {
       weekMES = Utils.getWeek( nomBase, myCnx,  st,Integer.parseInt(jourMES),
                               Integer.parseInt(moisMES),
                               Integer.parseInt(anneeMES));
       if ((weekMES == 1) && (weekMES < weekPROD) && (this.anneeMES.equals(anneePROD) )) weekMES =52;
     }
     this.A_weekMES = Integer.parseInt(this.anneeMES) * 100 + this.weekMES;
    }

/*
    if (
        this.nomSt.indexOf("**")> -1
       )
      this.isProjet = false; // c'est du r�curent
    else
      this.isProjet = true;
*/
    if ((this.weekT0 ==0) && (this.weekEB >=8)) this.weekT0 = this.weekEB - 8;
    if ((this.weekTEST ==0) && (this.weekPROD !=0)) this.weekTEST = (this.weekEB +this.weekPROD)/2 ;
    if ((this.weekMES == 0) && (this.weekPROD <=49)) this.weekMES = this.weekPROD + 3;

    if (this.weekT0 == 0) this.anneeT0 = "0";
    if (this.weekEB == 0) this.anneeEB = "0";
    if (this.weekTEST == 0) this.anneeTEST = "0";
    if (this.weekPROD == 0) this.anneePROD = "0";
    if (this.weekMES == 0) this.anneeMES = "0";
  }

  public String getLogo(String nomBase,Connexion myCnx, Statement st){
    String Logo="";
    ResultSet rs;
    int idCategorie=0;
    req = "SELECT DISTINCT St.Logo";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                   St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     (Roadmap.idVersionSt = "+this.idVersionSt+")";

    rs = myCnx.ExecReq(st, nomBase, req);
    try {rs.next(); Logo =rs.getString("Logo");} catch (SQLException s) {s.getMessage();}
    return Logo;
  }

  public int getIdCategorie(String nomBase,Connexion myCnx, Statement st, String nomCategorie){
    ResultSet rs;
    int idCategorie=0;
    req = "SELECT id FROM   categorieTest WHERE     (idRoadmap = "+this.id+") AND (nom = '"+nomCategorie.replaceAll("'","''")+"')";
    rs = myCnx.ExecReq(st, nomBase, req);
    try {rs.next(); idCategorie =rs.getInt("id");} catch (SQLException s) {s.getMessage();}
    return idCategorie;
  }
  
  public int getIdFromNom(String nomBase,Connexion myCnx, Statement st, String nomSt){
    ResultSet rs;
    int idRoadmap=0;
    req = "SELECT     idRoadmap, version";
    req+= " FROM         Roadmap";
    req+= " WHERE     (idVersionSt = "+this.idVersionSt+") AND (version LIKE '%"+nomSt+"%')";
    req+= " AND  (idPO = '"+this.idPO+"')";
    
    this.id = 0;
    rs = myCnx.ExecReq(st, nomBase, req);
    try {rs.next(); this.id =rs.getInt("idRoadmap");} catch (SQLException s) {s.getMessage();}
    return this.id;
  }
  

  public boolean isDevis(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    int nb =0;
    int idCategorie=0;
    req = "SELECT     COUNT(id) AS nb";
    req+="    FROM         Devis";
    req+="     WHERE     (idRoadmap = "+this.id+") AND (idEtat = 7)";

    rs = myCnx.ExecReq(st, nomBase, req);
    try {rs.next(); nb =rs.getInt("nb");} catch (SQLException s) {s.getMessage();}

    if (nb == 0) return false; else return true;

  }
  
  public boolean isPere(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    int nb =0;
    int idCategorie=0;
    req = "select COUNT (*) as nb from Roadmap";
    req+="     WHERE     (idRoadmapPere = "+this.id+")";
    req+=" and     (idRoadmapPere >0 )";

    rs = myCnx.ExecReq(st, nomBase, req);
    try {rs.next(); nb =rs.getInt("nb");} catch (SQLException s) {s.getMessage();}

    if (nb == 0) return false; else return true;

  }  
  
  public boolean isInListe(int id1,int id2, Vector myListe){

    for ( int i=0; i < myListe.size(); i++)
    {
       Roadmap theRoadmap =  (Roadmap)myListe.elementAt(i);
       if ((theRoadmap.id == id1) || (theRoadmap.id == id2))
           return true;
    }
        
    return false;

  } 
  
  public boolean isInListe(Vector myListe){

    for ( int i=0; i < myListe.size(); i++)
    {
       Roadmap theRoadmap =  (Roadmap)myListe.elementAt(i);
       if ((theRoadmap.id == this.id))
           return true;
    }
                        this.y0_T0 = 0;
                        this.y1_T0 = 0;   
                        this.y0_PROD = 0;
                        this.y1_PROD = 0;   
    
    return false;

  }   

  public boolean isDevisAllState(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    int nb =0;
    int idCategorie=0;
    req = "SELECT     COUNT(id) AS nb";
    req+="    FROM         Devis";
    req+="     WHERE     (idRoadmap = "+this.id+")";

    rs = myCnx.ExecReq(st, nomBase, req);
    try {rs.next(); nb =rs.getInt("nb");} catch (SQLException s) {s.getMessage();}

    if (nb == 0) return false; else return true;

  }


  public String bd_InsertCollaborateur(String nomBase,Connexion myCnx, Statement st, String transaction, int anneeRef){
    ResultSet rs=null;

    req = "DELETE FROM PlanDeCharge WHERE idRoadmap = '"+this.id+"' AND anneeRef = '"+anneeRef+"'";
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     //req = "INSERT PlanDeCharge (nomProjet,  projetMembre, semaine,Charge,anneeRef, idRoadmap) VALUES ('"+this.nomSt+"-"+this.version+"',  "+"NULL"+", "+0+""+", "+this.ChargePrevue+", "+anneeRef+", "+this.id+")";
     req = "INSERT PlanDeCharge (nomProjet, projetMembre, semaine,Charge,anneeRef, idRoadmap) VALUES ('',"+"NULL"+", "+0+""+", "+this.ChargePrevue+", "+anneeRef+", "+this.id+")";
     if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}


     for (int i=0; i < this.ListeCollaborateurs.size(); i++)
     {
       Collaborateur theCollaborateur = (Collaborateur)this.ListeCollaborateurs.elementAt(i);
       for (int j=0; j < theCollaborateur.ListeCharges.size(); j++)
       {
         Charge theCharge = (Charge)theCollaborateur.ListeCharges.elementAt(j);
         if (theCharge.anneeRef == anneeRef)
         {
           //req="INSERT PlanDeCharge (nomProjet,  projetMembre, semaine,Charge,anneeRef, idRoadmap) VALUES ('"+this.nomSt+"-"+this.version+"',  "+theCollaborateur.id+", "+theCharge.semaine+""+", "+theCharge.hj+", "+anneeRef+", "+this.id+")";
           req="INSERT PlanDeCharge (nomProjet,projetMembre, semaine,Charge,anneeRef, idRoadmap) VALUES ('',"+theCollaborateur.id+", "+theCharge.semaine+""+", "+theCharge.hj+", "+anneeRef+", "+this.id+")";
           if (myCnx.ExecUpdate(st, nomBase, req, true, transaction) == -1) {
             myCnx.trace(nomBase, "*** ERREUR *** req", req);
             return req;
           }
         }
       }
     }
     return "OK";

  }

  public static void getListeMotClefs(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;

    String req="";
    req = "SELECT DISTINCT nom FROM  ListeMotsClefs ORDER BY nom";

    ListeMotClef.removeAllElements();

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String MotClef= rs.getString("nom"); // nom du ST
        if (MotClef.length()> 1)
           ListeMotClef.addElement(MotClef);
      }
    }
            catch (SQLException s) {s.getMessage();}
  }
  
  public static void getListeMotClefsByTag(String nomBase,Connexion myCnx, Statement st, String tag){
    ResultSet rs;

    String req="";
    req = "SELECT DISTINCT nom FROM  ListeMotsClefs";
    req+= " WHERE     (nom LIKE '%"+tag+"%')";
    req+= " ORDER BY nom";

    ListeMotClef.removeAllElements();

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String MotClef= rs.getString("nom"); // nom du ST
        if (MotClef.length()> 1)
           ListeMotClef.addElement(MotClef);
      }
    }
            catch (SQLException s) {s.getMessage();}
  }  


  public String getChargeByWeek(int week, int annee){
    for (int i=0; i < this.ListeCharges.size();i++)
    {
      Charge theCharge = (Charge)this.ListeCharges.elementAt(i);
      if ((theCharge.semaine == week) && (theCharge.anneeRef == annee))
      {
        if (theCharge.hj == 0.0) return "";
        else  return ""+(float)Math.round(theCharge.hj * 10) / 10;
      }
    }
    return "";
  }
  
  public Charge getChargeByWeekByYear(int week, int annee){
    for (int i=0; i < this.ListeCharges.size();i++)
    {
      Charge theCharge = (Charge)this.ListeCharges.elementAt(i);
      if ((theCharge.semaine == week) && (theCharge.anneeRef == annee))
      {
          return theCharge;
      }
    }
    return null;
  } 
  
  public Charge getChargeByWeekByYearByCollaborateur(int week, int annee, int idMembre){
    for (int i=0; i < this.ListeCharges.size();i++)
    {
      Charge theCharge = (Charge)this.ListeCharges.elementAt(i);
      if ((theCharge.semaine == week) && (theCharge.anneeRef == annee) && (theCharge.idMembre == idMembre))
      {
          return theCharge;
      }
    }
    return null;
  }   

  public void getXY(Vector ListeRoadmap, int theYearRef, int Mois_start){
      for (int i=0; i < ListeRoadmap.size();i++)
      {
          Roadmap theRoadmap = (Roadmap)ListeRoadmap.elementAt(i);
          if (this.id == theRoadmap.id)
          {
              Jalon theJalonFirst = (Jalon)theRoadmap.ListeJalons.firstElement();
              Jalon theJalonLast = (Jalon)theRoadmap.ListeJalons.lastElement();
              
                        this.x0_T0 = theJalonFirst.x0;
                        this.x1_T0 = theJalonFirst.x1;
                        this.y0_T0 = theJalonFirst.y0;
                        this.y1_T0 = theJalonFirst.y1;   
                        
                        this.x0_PROD = theJalonLast.x0;
                        this.x1_PROD = theJalonLast.x1;
                        this.y0_PROD = theJalonLast.y0;
                        this.y1_PROD = theJalonLast.y1;                          
                         
              
              this.visible =theRoadmap.visible;
          }
      }
  }
  
    public void getXY2(Vector ListeRoadmap, int theYearRef, int Mois_start){
      for (int i=0; i < ListeRoadmap.size();i++)
      {
          Roadmap theRoadmap = (Roadmap)ListeRoadmap.elementAt(i);
          if (this.id == theRoadmap.id)
          {
              
            for ( int j=0; j < theRoadmap.ListeJalons.size(); j++)
            {
                    Jalon theJalon = (Jalon)theRoadmap.ListeJalons.elementAt(j);
                    //theJalon.setDatesPlanning(theYearRef, Mois_start );
                    if (j == 0)
                    {
                        this.x0_T0 = theJalon.x0;
                        this.x1_T0 = theJalon.x1;
                        this.y0_T0 = theJalon.y0;
                        this.y1_T0 = theJalon.y1;                        
                        
                    }
                    if (j == 3)
                    {
                        this.x0_PROD = theJalon.x0;
                        this.x1_PROD = theJalon.x1;
                        this.y0_PROD = theJalon.y0;
                        this.y1_PROD = theJalon.y1;                   
                        
                    }                    
            }              
              
              this.visible =theRoadmap.visible;
          }
      }
  }
  
    public void getXY2(Vector ListeRoadmap){
      for (int i=0; i < ListeRoadmap.size();i++)
      {
          Roadmap theRoadmap = (Roadmap)ListeRoadmap.elementAt(i);
          if (this.id == theRoadmap.id)
          {
              this.x0_T0 = theRoadmap.x0_T0;
              this.x1_T0 = theRoadmap.x1_T0;
              this.y0_T0 = theRoadmap.y0_T0;
              this.y1_T0 = theRoadmap.y1_T0;
              this.x0_PROD = theRoadmap.x0_PROD;
              this.x1_PROD = theRoadmap.x1_PROD;
              this.y0_PROD = theRoadmap.y0_PROD;
              this.y1_PROD = theRoadmap.y1_PROD;
              
              this.visible =theRoadmap.visible;
          }
      }
  }
  
  public String getListeBesoins(String nomBase,Connexion myCnx, Statement st, boolean allExigences){
    ResultSet rs;

    req="exec [SP_RoadmapGetBesoinsUtilisateurById]";
    req+=" @id ="+this.id+"";


    return getListeBesoins_commun( nomBase, myCnx,  st,  allExigences,  req);

  }

  public String bd_InsertCategorieFromBesoins(String nomBase,Connexion myCnx, Statement st, String transaction){


    // delete des cat�gories gener�es a partir des exigence
    req = "DELETE from categorieTest ";
    req+=" WHERE     (idBesoinUtilisateur > 0)";
    req+=" AND  idRoadmap ="+this.id;
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1)
      {
        myCnx.trace(nomBase,"*** ERREUR *** reqRoadmap",req);
        return req;
      }

    // Bouclage sur les besoins utilisateurs

    for (int i=0; i < this.ListeBesoins.size();i++)
    {
      Besoin theBesoin = (Besoin)this.ListeBesoins.elementAt(i);
      String description="";
      if (theBesoin.description != null)
        description=theBesoin.description.replaceAll("\u0092","'").replaceAll("'","''");
      else
        description="";

      String detail="";
      if (theBesoin.detail != null)
        detail=theBesoin.detail.replaceAll("\u0092","'").replaceAll("'","''");
      else
        detail="";

      int ordre = theBesoin.niv1 * 1000 + theBesoin.niv2 * 100+ theBesoin.niv3 * 10+ theBesoin.niv4;

      // Creation d'une categorie a partir d'une besoin

      req = "INSERT categorieTest (";
      req+="nom";
      req+=",";
      req+="description";
      req+=",";
      req+="idRoadmap";
      req+=",";
      req+="idBesoinUtilisateur";
      req+=",";
      req+="Ordre";
      req+="   )";
      req+=" VALUES (";
      req+="'"+description+"'";
      req+=",";
      req+="'"+detail+"'";
      req+=",";
      req+="'"+this.id+"'";
      req+=",";
      req+="'"+theBesoin.id+"'";
      req+=",";
      req+="'"+ordre+"'";
      req+=")";

      if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1)
        {
          myCnx.trace(nomBase,"*** ERREUR *** reqRoadmap",req);
          return req;
      }

      // Fin de bouclage
    }

    return "OK";
  }
  
  public ErrorSpecific bd_InsertFaqRadType(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();
    
     
     for (int i=0; i < SI.ListeExigences.size(); i++)
     {
        Besoin theExigence = (Besoin)SI.ListeExigences.elementAt(i);

      req = "INSERT BesoinsUtilisateur (description, nbNiveau, niv1, niv2, niv3, niv4, chapitre, idRoadmap, integDevis, Complexite )";
      req+="  VALUES (";
      req+="'"+theExigence.nom+"'";
      req+=",";
      req+="'"+theExigence.nbNiveau+"'";
      req+=",";
      req+="'"+theExigence.niv1+"'";
      req+=",";
      req+="'"+theExigence.niv2+"'";
      req+=",";
      req+="'"+theExigence.niv3+"'";
      req+=",";
      req+="'"+theExigence.niv4+"'";
      req+=",";
      req+="'"+theExigence.chapitre+"'";
      req+=",";
      req+="'"+this.id+"'";
      req+=",";
      req+="'"+"1"+"'";
      req+=",";
      req+="'"+"1"+"'";   
      req+=")";

       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertFaqRadType",""+this.id); 
     }

     return myError;

  }

  public String getListeBesoins_commun(String nomBase,Connexion myCnx, Statement st, boolean allExigences, String req){
    ResultSet rs;

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

        theBesoin.detail = rs.getString("detail");
        if (theBesoin.detail == null) theBesoin.detail="";

        theBesoin.isExigence = rs.getInt("isExigence");

        theBesoin.version = rs.getString("version");
        if (theBesoin.version == null) theBesoin.version="";

        theBesoin.image = rs.getString("image");
        if (theBesoin.image == null) theBesoin.image="";

        theBesoin.nbNiveau = rs.getInt("nbNiveau");
        theBesoin.niv1 = rs.getInt("niv1");
        theBesoin.niv2 = rs.getInt("niv2");
        theBesoin.niv3 = rs.getInt("niv3");
        theBesoin.niv4 = rs.getInt("niv4");

        if (theBesoin.nbNiveau == 1)
          theBesoin.chapitre = ""+ theBesoin.niv1;
        else if(theBesoin.nbNiveau == 2)
            theBesoin.chapitre = ""+ theBesoin.niv1 + "." +theBesoin.niv2;
        else if(theBesoin.nbNiveau == 3)
            theBesoin.chapitre = ""+ theBesoin.niv1 + "." +theBesoin.niv2+ "." +theBesoin.niv3;
        else if(theBesoin.nbNiveau == 4)
           theBesoin.chapitre = ""+ theBesoin.niv1 + "." +theBesoin.niv2+ "." +theBesoin.niv3+ "." +theBesoin.niv4;

        if (allExigences || (theBesoin.isExigence ==1))
        {
          this.ListeBesoins.addElement(theBesoin);
        }
        theBesoin.Complexite = rs.getInt("Complexite");
        theBesoin.Cout = rs.getFloat("Cout");

        theBesoin.nomTypeComplexite = rs.getString("nomTypeComplexite");
      }
    }
    catch (Exception s) {
      s.getMessage();
      return req;
    }
    return "OK";

  }


  public static String getExigences(String nomBase,Connexion myCnx, Statement st){
      
    ResultSet rs = null;

    String req = "update DeclencheProcedure set insererExigence = 'GO'";
    myCnx.ExecReq(st,nomBase,req);

    return "OK";
  }

 public  void excelExportCampagne(String nomBase,Connexion myCnx, Statement st, Statement st2, String Client, String directory, String filename){
    
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    st2 = myCnx.Connect();
    
    this.directory = directory;
    this.filename = filename;        


    String[] entete = {"Campagne","Categorie", "Libelle", "MiseEnOeuvre", "intervenant", "Etat", "anomalie", "commentaire"};
    Excel theExport = new Excel("doc/"+Client+"/test/exportExcel",entete);

    theExport.xl_delete();
    //myCnx.trace("1---------- delete","location",""+location);

    // --------------------------------------------------------------------------------------------------//


    theExport.attach_xlsx(theExport.Basedirectory+"/" + this.filename); // nom du fichier excel horodate
    //myCnx.trace("2---------- attach","location",""+location+"/ExportCampagneTest_"+0+".xls");

    //theExport.xl_open_update();
    theExport.xl_open_create_xlsx("CAHIER_TEST"); // feuille de calcul
    theExport.setStyleDate_xlsx();
    
    

    int col = 0;
    theExport.sheet_xlsx.setColumnWidth(col, 256*11); // A
    theExport.sheet_xlsx.setColumnWidth(++col, 256*24); // B
    theExport.sheet_xlsx.setColumnWidth(++col, 256*40); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*46); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*15); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*4); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*3); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*65); // fixer la largeur de la 1ere colonne

    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet_xlsx.createFreezePane(2,1) ;

    
    int numRow = 0;
    Row row = theExport.sheet_xlsx.createRow(numRow);
    theExport.xl_writeEntete_xlsx(row,0,theExport.entete);
    numRow++;  

  for (int i=0; i < this.ListeCampagnes.size();i++)
  { 
      Campagne theCampagne = (Campagne)this.ListeCampagnes.elementAt(i);
    /// --------------------------- Récupération de la liste des catégories de test----------------------//
      this.ListeCategories.removeAllElements();
    this.getListeCategories(myCnx.nomBase,myCnx,st);
    // -------------------------------------------------------------------------------------------------//
  for (int j=0; j < this.ListeCategories.size();j++)
  {
   // myCnx.trace("@01234--------------------------","Categorie",""+j);
    CategorieTest theCategorieTest = (CategorieTest)this.ListeCategories.elementAt(j);
    theCategorieTest.ListeTests.removeAllElements();
    theCategorieTest.getListeTests(myCnx.nomBase,myCnx,st, st2);

    for (int k=0; k < theCategorieTest.ListeTests.size();k++)
    {
     // myCnx.trace("@01234--------------------------","Test",""+k);
      Test theTest = (Test)theCategorieTest.ListeTests.elementAt(k);
      //theTest.dump();

      theTest.getResults(myCnx.nomBase,myCnx, st,theCampagne.id);
     // --------------------------------- Création du Collabotrateur ----------------------------------------
        Collaborateur theCollaborateur = new Collaborateur(theTest.intervenant);
        theCollaborateur.getAllFromBd(myCnx.nomBase,myCnx,  st2 );
     //-------------------------------------------------------------------------------------------------------      

      String str_anomalie="";
      if (theTest.anomalie == 0) str_anomalie = ""; else str_anomalie = ""+theTest.anomalie;        
     
      int numCol = 0;
      row = theExport.sheet_xlsx.createRow(numRow);
      
      if (theCampagne.version != null)
          theExport.xl_write_xlsx(row, numCol, "" + theCampagne.version.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
 
      if (theCategorieTest.nom != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + theCategorieTest.nom.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTest.nom != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + theTest.nom.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTest.miseEnOeuvre != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + theTest.miseEnOeuvre.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theCollaborateur.nom != null  && theCollaborateur.prenom != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + (theCollaborateur.nom+", "+theCollaborateur.prenom).replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
       
      if (theTest.Etat != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + theTest.Etat.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (str_anomalie != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + str_anomalie.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
      
      if (theTest.commentaire != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + theTest.commentaire.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
 
      numRow++;

      }


    }
     
  }
  //  myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create_xlsx();

    //myCnx.trace("5---------- xl_close_create","xl_close_create",""+theExport.filename);
       
    }  
 
  public  void excelExportTaches(String nomBase,Connexion myCnx, Statement st, String Client, String directory, String filename){
    ResultSet rs = null;
    
    this.directory = directory;
    this.filename = filename;    

    String[] entete = {"Projet","Tache", "Acteurs", "Etat", "Debut", "Fin", "Prevu", "Consomme", "RAD", "RAF"};
    Excel theExport = new Excel("doc/"+Client+"/tache/exportExcel",entete);

    theExport.xl_delete();
    //myCnx.trace("1---------- delete","location",""+location);

    // --------------------------------------------------------------------------------------------------//

    theExport.attach_xlsx(theExport.Basedirectory+"/" + this.filename);// nom du fichier excel horodate
    //myCnx.trace("2---------- attach","location",""+location+"/ExportTaches_"+this.id+".xls");

    //theExport.xl_open_update();
    theExport.xl_open_create_xlsx("Taches"); // feuille de calcul
    theExport.setStyleDate_xlsx();

    this.getAllFromBd(myCnx.nomBase,myCnx,st);
    this.getListeActions(myCnx.nomBase,myCnx,st);
   
    int col = 0;
    theExport.sheet_xlsx.setColumnWidth(col, 256*30); // A
    theExport.sheet_xlsx.setColumnWidth(++col, 256*44); // B
    theExport.sheet_xlsx.setColumnWidth(++col, 256*20); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*10); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*12); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*12); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*5); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*10); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*6); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(++col, 256*6); // fixer la largeur de la 1ere colonne

    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet_xlsx.createFreezePane(2,1) ;

    
    int numRow = 0;
    Row row = theExport.sheet_xlsx.createRow(numRow);
    theExport.xl_writeEntete_xlsx(row,0,theExport.entete);
    numRow++;  
    



for (int i=0; i < this.ListeActions.size(); i++) {
  Action theTache = (Action)this.ListeActions.elementAt(i);
  theTache.projet = this.nomSt + "-" + this.version;
  float Global_Consomme = theTache.getChargesConsommees(myCnx.nomBase,myCnx,  st);
  //String str_Global_Consomme = (""+Global_Consomme).replace('.',',');
  String str_Global_Consomme = (""+Global_Consomme);

  float RAD = theTache.ChargePrevue - Global_Consomme;
  //String str_RAD = (""+RAD).replace('.',',');
  String str_RAD = (""+RAD);

  //String str_Consomme = (""+theTache.ChargePrevue).replace('.',',');
  String str_Consomme = (""+theTache.ChargePrevue);
  
     
      int numCol = 0;
      row = theExport.sheet_xlsx.createRow(numRow);
      
      if (theTache.projet != null)
          theExport.xl_write_xlsx(row, numCol, "" + theTache.projet.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTache.Code != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + theTache.Code.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTache.acteur != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + theTache.acteur.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTache.nomEtat != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + theTache.nomEtat.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTache.date_start != null)
          theExport.xl_writeDateStyle(row, ++numCol, "" + theTache.date_start.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theTache.date_end != null)
          theExport.xl_writeDateStyle(row, ++numCol, "" + theTache.date_end.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (str_Consomme != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + str_Consomme.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""), HSSFCell.CELL_TYPE_NUMERIC);

      if (str_Global_Consomme != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + str_Global_Consomme.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""), HSSFCell.CELL_TYPE_NUMERIC);

      if (str_RAD != null)
          theExport.xl_write_xlsx(row, ++numCol, "" + str_RAD.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""), HSSFCell.CELL_TYPE_NUMERIC);

       numRow++;

      }
    //myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create_xlsx();

    //myCnx.trace("5---------- xl_close_create","xl_close_create",""+theExport.filename);
        
    }  
  
  public boolean isExigences(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;
    boolean hasExigences = false;
    req = "SELECT     COUNT(*) AS nb FROM         BesoinsUtilisateur WHERE     (idRoadmap = "+this.id+")";
    rs = myCnx.ExecReq(st, nomBase,req);
    int nb=0;
    try { rs.next();
            nb= rs.getInt("nb");
          } catch (SQLException s) {s.getMessage();}

    if (nb > 0) return true; else return false;

  }

  public String setDeleteBesoinsUtilisateur(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs = null;

    String req = "exec SP_RoadmapDeleteBesoinsUtilisateursById " + this.id;
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

    return "OK";
  }

 public ErrorSpecific bd_shiftCharges3(String nomBase,Connexion myCnx, Statement st, Statement st2, String transaction, int anneeRef, int shift){
  ErrorSpecific myError = new ErrorSpecific();
  ResultSet rs=null;
  
  Vector myListe = new Vector (10);
 
   req = "SELECT     projetMembre, semaine, Charge, anneeRef, idRoadmap";
   req="  FROM         PlanDeCharge";
   req+=" WHERE     (idRoadmap = "+this.id+") ";
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  
    try {
    while (rs.next()) {

      int semaine = rs.getInt("semaine"); 
      int annee = rs.getInt("semaine"); 
      
      if ( (semaine + shift) > 52)
      {
        semaine = semaine + shift - 52;
        annee++;
        
      }
      else  if ( (semaine + shift) < 1)
      {
        semaine = semaine + shift + 52;
        annee--;

      }  
      else 
      {        
      }
      Charge theCharge = new Charge(this.id);
      theCharge.anneeRef = annee;
      theCharge.semaine = semaine;
      myListe.addElement(theCharge);  

     }
    }
    catch (Exception e){}           

    for (int i = 0; i < myListe.size(); i++)
    {
        Charge theCharge =  (Charge)myListe.elementAt(i);
         req="  update       PlanDeCharge";
         req+=" set semaine = "+theCharge.semaine;
         req+=" , anneeRef = "+theCharge.anneeRef;
         req+=" WHERE     (idRoadmap = "+this.id+")";
         
         myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_shiftCharges",""+this.id);           
    }
    
   return myError;

}

 public ErrorSpecific bd_shiftCharges(String nomBase,Connexion myCnx, Statement st, Statement st2, String transaction, int anneeRef, int shift){
  ErrorSpecific myError = new ErrorSpecific();
  ResultSet rs=null;
 
         req="  update       PlanDeCharge";
         req+=" set semaine = semaine+"+shift;
         req+=" WHERE     (idRoadmap = "+this.id+") AND (anneeRef = "+anneeRef+")";
         
         myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_shiftCharges",""+this.id);      

            

   return myError;

} 
  
  public ErrorSpecific bd_InsertBesoins(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

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

       if (theBesoin.Complexite == -1) theBesoin.Complexite = 1;

       if (theBesoin.id <=0)
       {
         req = "exec [SP_RoadmapInsertBesoinsUtilisateurs]";
         req += " @idRoadmap ='" + this.id + "', ";
         req += " @typeBesoin =" + theBesoin.type + ", ";
         req += " @nom ='" + theBesoin.nom + "', ";
         req += " @description ='" + description + "', ";
         req += " @integDevis ='" + theBesoin.IntegDevis + "', ";
         req += " @Commentaires ='" + Commentaire + "',";
         req += " @idBesoin ='" + idBesoin + "',";
         req += " @isExigence ='" + theBesoin.isExigence + "',";
         req += " @Complexite ='" + theBesoin.Complexite + "',";
         req += " @Cout ='" + theBesoin.Cout + "'";
       }
       else if (theBesoin.idBesoin.equals("toDel"))
       {
         req="   DELETE    BesoinsUtilisateur";
         req += " WHERE BesoinsUtilisateur.id ='" + theBesoin.id + "'";
       }
       else
       {
         req="   UPDATE    BesoinsUtilisateur  SET ";
         req += " typeBesoin ='" + theBesoin.type + "', ";
         req += " nom ='" + theBesoin.type + "', ";
         req += " description ='" + description + "', ";
         req += " integDevis ='" + theBesoin.IntegDevis + "', ";
         req += " Commentaires ='" + Commentaire + "', ";
         req += " idBesoin ='" + idBesoin + "', ";
         req += " isExigence ='" + theBesoin.isExigence + "', ";
         req += " Complexite ='" + theBesoin.Complexite + "', ";
         req += " Cout ='" + theBesoin.Cout + "' ";
         req += " WHERE BesoinsUtilisateur.id ='" + theBesoin.id + "'";

       }

       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertBesoins",""+this.id);

     }

     return myError;

  }
  
  public ErrorSpecific bd_UpdateBesoins(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

     for (int i=0; i < this.ListeBesoins.size(); i++)
     {
       Besoin theBesoin = (Besoin)this.ListeBesoins.elementAt(i);

         req="   UPDATE    BesoinsUtilisateur  SET ";
          req += " integDevis ='" + theBesoin.IntegDevis + "', ";
         req += " Complexite ='" + theBesoin.Complexite + "', ";
         req += " Cout ='" + theBesoin.Cout + "' ";
         req += " WHERE BesoinsUtilisateur.id ='" + theBesoin.id + "'";

       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertBesoins",""+this.id);

     }

     return myError;

  }  

   public int getLinked(String nomBase,Connexion myCnx, Statement st , int currentYear,  int currentMonth){
    ResultSet rs=null;
    int id = -1;
    req = "SELECT idRoadmap FROM   Roadmap WHERE (linked_id = "+this.id+") AND (LF_Year = "+currentYear+") AND (LF_Month = "+currentMonth+")";
    rs = myCnx.ExecReq(st, nomBase,req);

    try { rs.next();
            id= rs.getInt("idRoadmap");
          } catch (SQLException s) {s.getMessage();}

    return id;
  }

  public void getInfoPoClient(String nomBase,Connexion myCnx, Statement st, String nomService){
    ResultSet rs;
    req = "SELECT     PlanOperationnelClient.nomProjet, PlanOperationnelClient.charge, PlanOperationnelClient.chargeConsommee, YEAR(Roadmap.dateTest) ";
    req+="                  AS anneeTest, PlanOperationnelClient.Annee";
    req+=" FROM         PlanOperationnelClient INNER JOIN";
    req+="                  Roadmap ON PlanOperationnelClient.idWebPo = Roadmap.idPO AND PlanOperationnelClient.Annee = YEAR(Roadmap.dateTest) INNER JOIN";
    req+="                  Service ON PlanOperationnelClient.Service = Service.nomServiceImputations";
    req+=" WHERE     (Roadmap.idRoadmap = "+this.id+") AND (Service.nomServiceImputations = '"+nomService+"')";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  try {
    while (rs.next()) {
      //this.nomProjetClient = rs.getString("nomProjet");
      this.Charge = ""+rs.getInt("charge");
      this.chargeConsommee = ""+rs.getInt("chargeConsommee");

    }
  }
  catch (SQLException s) {s.getMessage();}

  req = " SELECT DISTINCT PlanOperationnelClient.nomProjet";
  req+= "   FROM         PlanOperationnelClient INNER JOIN";
  req+= "                         Roadmap ON PlanOperationnelClient.idWebPo = Roadmap.idPO INNER JOIN";
  req+= "                         Service ON PlanOperationnelClient.Service = Service.nomServiceImputations";
  req+= " WHERE     (Roadmap.idRoadmap = "+this.id+")";

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
try {
  while (rs.next()) {
    this.nomProjetClient = rs.getString("nomProjet");

  }
}
  catch (SQLException s) {s.getMessage();}
  }

  public int getChargesProjetsByRangeByCollaborateur(String nomBase,Connexion myCnx, Statement st , int anneeRef, int idCollaborateur, int WeekStart, int WeekEnd){
    int nbJours=0;
    ResultSet rs;
    req="SELECT     SUM(Charge) AS nb";
    req+="    FROM         PlanDeCharge";
    req+=" WHERE     (idRoadmap = "+this.id+") AND (semaine >= "+WeekStart+") AND (semaine < "+WeekEnd+") AND (anneeRef = "+anneeRef+") AND (projetMembre = "+idCollaborateur+")";

    rs = myCnx.ExecReq(st, nomBase, req);

    int totalMembre=0;
    try {
        while (rs.next()) {
                    nbJours = rs.getInt(1);
                 }

          }
                         catch (SQLException s) {s.getMessage();}

    return nbJours;
  }

  public void getChargesFromPdc(String nomBase,Connexion myCnx, Statement st , String anneeRef){
  ResultSet rs;

    req="SELECT     Charge FROM         PlanDeCharge WHERE     (idRoadmap  = '"+this.id+"') AND (semaine = 0) AND (anneeRef = "+anneeRef+")";
    rs = myCnx.ExecReq(st, nomBase,req);
    try { rs.next();
            this.ChargePrevue= rs.getFloat(1);

} catch (SQLException s) {s.getMessage();}
}

  public void getChargesProjets(String nomBase,Connexion myCnx, Statement st , int anneeRef){
  ResultSet rs;
  for (int i=0; i < this.ListeCollaborateurs.size(); i++)
    {
    Collaborateur theCollaborateur = (Collaborateur)this.ListeCollaborateurs.elementAt(i);

    req="ACTEUR_selectChargeByMembre '"+this.id+"','"+theCollaborateur.id+"',"+anneeRef+"";
    rs = myCnx.ExecReq(st, nomBase, req);

    int totalMembre=0;
    try {
        while (rs.next()) {
                    int sem = rs.getInt(1);
                    float charge = rs.getFloat(2);
                    int idEtat = rs.getInt("etat");
                    theCollaborateur.addListeCharges(anneeRef, sem,charge, idEtat);
                    theCollaborateur.totalReparti+=charge;
                    this.totalReparti+=charge;
                    this.totalMembre+=charge;
                 }

          }
                         catch (SQLException s) {s.getMessage();}
    }

    req="SELECT     Charge FROM         PlanDeCharge WHERE     (idRoadmap  = '"+this.id+"') AND (semaine = 0) AND (anneeRef = "+anneeRef+")";
    rs = myCnx.ExecReq(st, nomBase,req);
    try { rs.next();
            ChargePrevue= rs.getFloat(1);

} catch (SQLException s) {s.getMessage();}
}
  public int getTotalChargesProjets(String nomBase,Connexion myCnx, Statement st , int anneeRef){
  ResultSet rs;
  req="SELECT     SUM(Charge) AS total";
  req+="   FROM         PlanDeCharge";
  req+=" WHERE     (idRoadmap = "+this.id+") AND (anneeRef = "+anneeRef+")";

  rs = myCnx.ExecReq(st, nomBase, req);

  int total=0;
  try {
      while (rs.next()) {
                   total = rs.getInt(1);

               }

        }
                         catch (SQLException s) {s.getMessage();}

  return total;

}
  
  public float getTotalChargesProjets(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs;
  req="SELECT     SUM(Charge) AS total";
  req+="   FROM         PlanDeCharge";
  req+=" WHERE     (idRoadmap = "+this.id+")";

  rs = myCnx.ExecReq(st, nomBase, req);

  float total=0;
  try {
      while (rs.next()) {
                   total = rs.getFloat(1);

               }

        }
                         catch (SQLException s) {s.getMessage();}

  return total;

}  
  
  public float getTotalChargesProjetsByService(String nomBase,Connexion myCnx, Statement st, int idService){
  ResultSet rs;
  req="SELECT     SUM(PlanDeCharge.Charge) AS total";
  req+= " FROM         Membre INNER JOIN";
  req+= "                       Service ON Membre.serviceMembre = Service.idService INNER JOIN";
  req+= "                       PlanDeCharge ON Membre.idMembre = PlanDeCharge.projetMembre";
  req+= " WHERE     (PlanDeCharge.idRoadmap = "+this.id+")";
  req+= " GROUP BY Service.idService";
  req+= " HAVING      (Service.idService = "+idService+")";

  rs = myCnx.ExecReq(st, nomBase, req);

  float total=0;
  try {
      while (rs.next()) {
                   total = rs.getFloat(1);

               }

        }
                         catch (SQLException s) {s.getMessage();}

  return total;

}  
  
  public Baseline getLastBaseline(String nomBase,Connexion myCnx, Statement st){
      
    Baseline theBaseline = new Baseline(this.id);
    ResultSet rs;

    req = "SELECT     TOP (1) id FROM    Baselines WHERE    idRoadmap = " + this.id + " ORDER BY id DESC";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        theBaseline.id = rs.getInt("id");
      }
    }
    catch (Exception s) {
      s.getMessage();

    }
    return theBaseline;
  }
  
  public float getTotalChargesByIdMembre(String nomBase,Connexion myCnx, Statement st , int idMembre, int anneeRef){
  ResultSet rs;
  req="SELECT     SUM( Charge) as nb ";
  req+=" FROM         PlanDeCharge";
  req+=" WHERE     (projetMembre = "+idMembre+") AND (idRoadmap = "+this.id+") AND (anneeRef = "+anneeRef+")";

  rs = myCnx.ExecReq(st, nomBase, req);

  float total=0;
  try {
      while (rs.next()) {
                   total = rs.getFloat(1);

               }

        }
                         catch (SQLException s) {s.getMessage();}

  return total;

} 
  
  public float getTotalChargesByIdMembre(String nomBase,Connexion myCnx, Statement st , int idMembre){
  ResultSet rs;
  req="SELECT     SUM( Charge) as nb ";
  req+=" FROM         PlanDeCharge";
  req+=" WHERE     (projetMembre = "+idMembre+") AND (idRoadmap = "+this.id+")";

  rs = myCnx.ExecReq(st, nomBase, req);

  float total=0;
  try {
      while (rs.next()) {
                   total = rs.getFloat(1);

               }

        }
                         catch (SQLException s) {s.getMessage();}

  return total;

}   

public int getChiffrage(String nomBase,Connexion myCnx, Statement st, String nomServiceImputations,int theYearRef,int LF){
  float total = 0;
  this.theChiffrage = new Chiffrage(nomServiceImputations,theYearRef,LF);

    ResultSet rs=null;

    req = "SELECT     id, idService, idPO, idPOLie, ST1, charge1, ST2, charge2, ST3, charge3, ST4, charge4, ST5, charge5, ST6, charge6, ST7, charge7, ST8, charge8, ST9, ";
    req+="                  charge9, ST10, charge10, Annee, LF, nomService, description, Forfait";
    req+=" FROM         ChiffragePlanOperationnelClient";
    req+=" WHERE     (nomService = '"+nomServiceImputations+"') AND (LF = "+LF+") AND (Annee = "+theYearRef+") AND (idPO = "+this.idPO+")";
    req+=" ORDER BY idPO";


    req="";
    req+=" SELECT     ChiffragePlanOperationnelClient.id, ChiffragePlanOperationnelClient.idService, ChiffragePlanOperationnelClient.idPO, ";
    req+="                       ChiffragePlanOperationnelClient.idPOLie, ChiffragePlanOperationnelClient.ST1, ChiffragePlanOperationnelClient.charge1, ";
    req+="                       ChiffragePlanOperationnelClient.ST2, ChiffragePlanOperationnelClient.charge2, ChiffragePlanOperationnelClient.ST3, ";
    req+="                       ChiffragePlanOperationnelClient.charge3, ChiffragePlanOperationnelClient.ST4, ChiffragePlanOperationnelClient.charge4, ";
    req+="                       ChiffragePlanOperationnelClient.ST5, ChiffragePlanOperationnelClient.charge5, ChiffragePlanOperationnelClient.ST6, ";
    req+="                       ChiffragePlanOperationnelClient.charge6, ChiffragePlanOperationnelClient.ST7, ChiffragePlanOperationnelClient.charge7, ";
    req+="                       ChiffragePlanOperationnelClient.ST8, ChiffragePlanOperationnelClient.charge8, ChiffragePlanOperationnelClient.ST9, ";
    req+="                       ChiffragePlanOperationnelClient.charge9, ChiffragePlanOperationnelClient.ST10, ChiffragePlanOperationnelClient.charge10, ";
    req+="                       ChiffragePlanOperationnelClient.Annee, ChiffragePlanOperationnelClient.LF, ChiffragePlanOperationnelClient.nomService, ";
    req+="                       ChiffragePlanOperationnelClient.description, ChiffragePlanOperationnelClient.Forfait, PlanOperationnelClient.Annee AS Expr1, ";
    req+="                       PlanOperationnelClient.Service, PlanOperationnelClient.charge AS chargePO";
    req+=" FROM         ChiffragePlanOperationnelClient LEFT OUTER JOIN";
    req+="                       PlanOperationnelClient ON ChiffragePlanOperationnelClient.idPO = PlanOperationnelClient.idWebPo";
    req+=" WHERE     (ChiffragePlanOperationnelClient.nomService = '"+nomServiceImputations+"') AND (ChiffragePlanOperationnelClient.LF = "+LF+") AND ";
    req+="                       (ChiffragePlanOperationnelClient.Annee = "+theYearRef+") AND (ChiffragePlanOperationnelClient.idPO = "+this.idPO+") AND (PlanOperationnelClient.Annee = "+theYearRef+") AND ";
    req+="                       (PlanOperationnelClient.Service = '"+nomServiceImputations+"')";
    req+=" ORDER BY ChiffragePlanOperationnelClient.idPO    ";

    rs = myCnx.ExecReq(st, nomBase, req);

    int idVersionSt=-1;
    float charge = 0;
    float totalCharge = 0;

       try {
            rs.next();
            this.theChiffrage.id=rs.getInt("id");
            this.theChiffrage.idPO=rs.getInt("idPO");
            this.theChiffrage.idPOLie=rs.getInt("idPOLie");

            for (int i=1; i <= 10 ; i++)
            {
              idVersionSt = rs.getInt("ST"+i);
              charge = rs.getFloat("charge"+i);
              if (idVersionSt != -1)  this.theChiffrage.addCharges(idVersionSt, charge);
              total += charge;
            }

            this.theChiffrage.nomService=rs.getString("nomService");
            this.theChiffrage.description=rs.getString("description");

            this.theChiffrage.Forfait=rs.getFloat("Forfait");
            this.ChargePrevue = rs.getFloat("chargePO");

           }

           catch (SQLException s) {
             s.getMessage();
           }

return (int)total;
}

public int getChiffrageMOA(String nomBase,Connexion myCnx, Statement st, String nomServiceImputations,int theYearRef,int LF){
           float total = 0;


             ResultSet rs=null;

             req = "SELECT     id, idService, idPO, idPOLie, ST1, charge1, ST2, charge2, ST3, charge3, ST4, charge4, ST5, charge5, ST6, charge6, ST7, charge7, ST8, charge8, ST9, ";
             req+="                  charge9, ST10, charge10, Annee, LF, nomService, description, Forfait";
             req+=" FROM         ChiffragePlanOperationnelClient";
             req+=" WHERE     (nomService <> '"+nomServiceImputations+"') AND (LF = "+LF+") AND (Annee = "+theYearRef+") AND (idPO = "+this.idPO+")";
             req+=" ORDER BY idPO";


             rs = myCnx.ExecReq(st, nomBase, req);

             int idVersionSt=-1;
             float charge = 0;
             float totalCharge = 0;

             try {
               while (rs.next()) {

                 this.theChiffrageMOA = new Chiffrage(nomServiceImputations,theYearRef,LF);

                 this.theChiffrage.id=rs.getInt("id");
                 this.theChiffrage.idPO=rs.getInt("idPO");
                 //this.theChiffrage.idPOLie=rs.getInt("idPOLie");

                 for (int i=1; i <= 10 ; i++)
                 {
                   idVersionSt = rs.getInt("ST"+i);
                   charge = rs.getFloat("charge"+i);
                   if (idVersionSt != -1)  this.theChiffrageMOA.addCharges(idVersionSt, charge);
                   total += charge;
                 }

                 this.theChiffrage.nomService=rs.getString("nomService");
                 //this.theChiffrage.description=rs.getString("description");

                 //this.theChiffrage.Forfait=rs.getFloat("Forfait");

                 this.updateChiffrageMOE(myCnx.nomBase, myCnx, st, nomServiceImputations,theYearRef,LF);

               }
             }
            catch (SQLException s) {s.getMessage();}



                    return (int)total;
}

 public void updateChiffrageMOE(String nomBase,Connexion myCnx, Statement st, String nomServiceImputations,int theYearRef,int LF){

   // bouclage sur le chiffrage MOA
   for (int i=0; i < this.theChiffrageMOA.ListeCharges.size();i++)
   {
     // pour chaque ST: de 1 � 10
     // s'il existe un chiffrage > 0, cr�er une charge = 1 � partir du premier ST Libre
     Charge theChargeMOA = (Charge)this.theChiffrageMOA.ListeCharges.elementAt(i);
     boolean trouve = false;
     for (int j=0; j < this.theChiffrage.ListeCharges.size();j++)
     {
       Charge theChargeMOE = (Charge)this.theChiffrage.ListeCharges.elementAt(j);
       if (theChargeMOE.idObj == theChargeMOA.idObj)
       {
         trouve=true;
       }
     }
     if (!trouve)
     {
       this.theChiffrage.addCharges(theChargeMOA.idObj, (float)0);
     }
   }

 }
         public void getChiffrageLF(String nomBase,Connexion myCnx, Statement st, String nomServiceImputations,int theYearRef,int LF){


             this.ChargePrevue= -1;
             ResultSet rs=null;

             req = "SELECT   charge , descProjet";
             req+=" FROM         PlanOperationnelClient_simulation";
             req+=" WHERE     (Service = '"+nomServiceImputations+"') AND (LF = "+LF+") AND (Annee = "+theYearRef+") AND (idwebpo = "+this.idPO+")";
             req+=" ORDER BY idwebpo";
              


             rs = myCnx.ExecReq(st, nomBase, req);

             int idVersionSt=-1;
             float charge = 0;
             float totalCharge = 0;

                try {
                     rs.next();
                     this.ChargePrevue=rs.getFloat("charge");
                     this.description=rs.getString("descProjet");

                    }

                    catch (SQLException s) {
                      s.getMessage();
                    }


}
         
         public void getChiffrageLF_old(String nomBase,Connexion myCnx, Statement st, String nomServiceImputations,int theYearRef,int LF){


             this.ChargePrevue= -1;
             ResultSet rs=null;

             req = "SELECT   idService, idPO, chargeReestimee, nomService, description, Annee, LF";
             req+=" FROM         ChiffrageLF";
             req+=" WHERE     (nomService = '"+nomServiceImputations+"') AND (LF = "+LF+") AND (Annee = "+theYearRef+") AND (idPO = "+this.idPO+")";
             req+=" ORDER BY idPO";


             rs = myCnx.ExecReq(st, nomBase, req);

             int idVersionSt=-1;
             float charge = 0;
             float totalCharge = 0;

                try {
                     rs.next();
                     this.ChargePrevue=rs.getFloat("chargeReestimee");
                     this.description=rs.getString("description");

                    }

                    catch (SQLException s) {
                      s.getMessage();
                    }


}

public String getLeader(String nomBase,Connexion myCnx, Statement st){
    String leader = "";
     ResultSet rs=null;

  req = "SELECT     Membre.nomMembre + ', ' + Membre.prenomMembre AS leader";
  req+= " FROM         Roadmap INNER JOIN"; 
  req+=" Membre ON Roadmap.idMembre = Membre.idMembre";
  req+=" WHERE     (idRoadmap = "+this.id+")";

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
        while (rs.next()) {
    leader=rs.getString("leader");
    }
    }
            catch (SQLException s) {s.getMessage();}

  return leader;
 }

public void getChargeDevis(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs=null;

  float totalCharge=0;
  String totalChargePO="";

  String ChargeDevis="";
  req = "SELECT     DISTINCT  RoadmapPO.Charge, RoadmapPO.Annee, RoadmapPO.idPO, PlanOperationnel.nomProjet, PlanOperationnel.charge AS chargePO";
  req+="     FROM         RoadmapPO INNER JOIN";
  req+="                     PlanOperationnel ON RoadmapPO.idPO = PlanOperationnel.idWebPo";

  req+=" WHERE     (idRoadmap = "+this.id+")";

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
try {
  while (rs.next()) {
    totalCharge+=rs.getFloat("Charge");
    this.idWebPo+=";"+rs.getString("idPO");
    this.nomProjet+=";"+rs.getString("nomProjet");
    this.ChargePO+=";"+rs.getString("chargePO");
  }
}
            catch (SQLException s) {s.getMessage();}

  this.Charge=""+totalCharge;
  if (this.idWebPo != null && this.idWebPo.length() >0) this.idWebPo = this.idWebPo.substring(1);
  if (this.nomProjet != null && this.nomProjet.length() >0) this.nomProjet = this.nomProjet.substring(1);
  if (this.ChargePO != null && this.ChargePO.length() >0) this.ChargePO = this.ChargePO.substring(1);

 }

public void getProfils(String nomBase,Connexion myCnx, Statement st){
    
    this.ProfilSpecification = new RoadmapProfil(this.idProfilSpecification);
    this.ProfilSpecification.getAllFromBd(nomBase, myCnx, st,this.idProfilSpecification,"Specification");
    
    this.ProfilDeveloppement = new RoadmapProfil(this.idProfilDeveloppement);
    this.ProfilDeveloppement.getAllFromBd(nomBase, myCnx, st,this.idProfilDeveloppement,"Developpement");
    
    this.ProfilTest = new RoadmapProfil(this.idProfilTest);
    this.ProfilTest.getAllFromBd(nomBase, myCnx, st,this.idProfilTest,"Test");
    
 }

  public void getDates(String nomBase,Connexion myCnx, Statement st){
      for (int i=0; i < this.ListeDependancesT0.size();i++)
      {
          dependanceRoadmap thedependanceRoadmap = (dependanceRoadmap)this.ListeDependancesT0.elementAt(i);
          long delta = thedependanceRoadmap.getDecalage(nomBase,myCnx, st);
          if ((delta > 0))
          {              
              for (int j=0; j < this.ListeJalons.size(); j++)
              {
                    Jalon theJalon = (Jalon)this.ListeJalons.elementAt(j);
                    if (theJalon.date != null)
                    {
                        theJalon.date = thedependanceRoadmap.setDecalage(theJalon.date, delta);
                        if (theJalon.date != null)
                            theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
                        else
                            theJalon.strDate = "";       
                    }
                    System.out.println("@999999@" + "id="+this.id+ "::" + ((Jalon)this.ListeJalons.elementAt(j)).date.toString() + "/" + ((Jalon)this.ListeJalons.elementAt(j)).strDate);
              }
              // System.out.println("-- Apres --------this.date_start="+this.date_start+"/ this.date_end="+this.date_end+"/ this.Code="+this.Code);
          }
      }
  } 
  
 public void getLivrables(String nomBase,Connexion myCnx, Statement st){

   String req = "SELECT     Roadmap.docEB, Roadmap.docDevis, Devis.id, Roadmap.Etat, VersionSt.serviceMoeVersionSt, VersionSt.serviceMoaVersionSt, ";
   req+="                    Service.nomService AS MOE, Service_1.nomService AS MOA, TypeSi.nomTypeSi";
   req+=" FROM         Roadmap INNER JOIN";
   req+="                    VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req+="                    TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi LEFT OUTER JOIN";
   req+="                    Service ON VersionSt.serviceMoeVersionSt = Service.idService LEFT OUTER JOIN";
   req+="                    Service AS Service_1 ON VersionSt.serviceMoaVersionSt = Service_1.idService LEFT OUTER JOIN";
   req+="                    Devis ON Roadmap.idRoadmap = Devis.idRoadmap";

   req+=" WHERE     (Roadmap.idRoadmap = "+this.id+")";



         ResultSet rs = myCnx.ExecReq(st, nomBase, req);
         Service theService = null;
         try {
            while (rs.next()) {
              this.docEB= rs.getString("docEB");
              this.docDevis= rs.getString("docDevis");
              String demat= rs.getString("id");
              if (demat != null)
              {
                this.docDevis = "Devis demat n�: " + demat;
              }
              else
              {
                this.docDevis ="X";
              }
              if ((this.docEB != null )&& !(this.docEB.equals("")))
              {
                this.docEB = "X";
              }
              else
              {
                this.docEB ="";
              }
              this.EtatRoadmap= rs.getString("Etat");
              this.MOE= rs.getString("MOE");
              this.MOA= rs.getString("MOA");
              this.nomTypeSi= rs.getString("nomTypeSi");
             }
             } catch (SQLException s) {s.getMessage();}

}

 public void getChargesFromPOByCC(String nomBase,Connexion myCnx, Statement st, String CC){
   ResultSet rs;
   int nbCnx=0;
   int totalCharge= 0;
   int totalChargeEngagee= 0;
   String req="";

   req="SELECT     SUM(charge) AS totalChargeDisponible, SUM(chargeConsommee) AS totalChargeConsomee";
   req+="    FROM         PlanOperationnelClient";
   req+=" WHERE     (idWebPo = "+this.idPO+") AND (Annee = "+this.Annee+") AND (Service LIKE '%"+CC+"%')";

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
       this.totalChargeDisponible= rs.getFloat("totalChargeDisponible"); // nom du ST
       this.totalChargeConsomee= rs.getFloat("totalChargeConsomee"); // nom du ST

     }
   }
           catch (SQLException s) {s.getMessage();}

  }
 
  public void getChargesFromPO(String nomBase,Connexion myCnx, Statement st,String Service,  int Annee){
   ResultSet rs;
   int nbCnx=0;
   int totalCharge= 0;
   int totalChargeEngagee= 0;
   String req="";

   req="SELECT     charge AS opex, depensePrevue AS capex";
   req+=" FROM         PlanOperationnelClient";
   req+=" WHERE     (idWebPo = "+this.idPO+") AND (Service = '"+Service+"') AND (Annee = "+Annee+")";

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);
   try {
     while (rs.next()) {
       this.ChargePrevue= rs.getFloat("opex"); // nom du ST
       this.ChargePrevueForfait= rs.getFloat("capex"); // nom du ST

     }
   }
           catch (SQLException s) {s.getMessage();}

  }
  
  public void getChargesTotalFromPO(String nomBase,Connexion myCnx, Statement st,  int Annee){
   ResultSet rs;
   int nbCnx=0;
   int totalCharge= 0;
   int totalChargeEngagee= 0;
   String req="";

   req="SELECT      SUM (charge) AS opex, SUM (depensePrevue) AS capex";
   req+=" FROM         PlanOperationnelClient";
   req+=" WHERE     (idWebPo = "+this.idPO+") AND (Annee = "+Annee+")";

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);
   try {
     while (rs.next()) {
       this.ChargeTotalePrevue= rs.getFloat("opex"); // nom du ST
       this.ChargeTotalePrevueForfait= rs.getFloat("capex"); // nom du ST

     }
   }
           catch (SQLException s) {s.getMessage();}

  }  
  
  

  public void addListeCharges(int anneeRef, int semaine, float hj, int idEtat)
  {
          if (idEtat == 0) idEtat = 1;
          Charge theCharge = new Charge(anneeRef, semaine,hj);
          theCharge.idEtat = idEtat;
          this.ListeCharges.addElement(theCharge);
  }

  public static void getLignesPO_static(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;

    String req="";
    req = "SELECT     idWebPo, nomProjet, Annee, charge";
    req+=" FROM         PlanOperationnel";
    req+=" ORDER BY Annee";

    ListeLignePO_static.removeAllElements();

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String idWebPo= rs.getString("idWebPo"); // nom du ST
        String nomProjet= rs.getString("nomProjet"); // nom du ST
        int Annee= rs.getInt("Annee"); // nom du ST
        float charge= rs.getFloat("charge");

        LignePO theLignePO = new LignePO( );
        theLignePO.idWebPo = idWebPo;
        theLignePO.Annee=""+Annee;
        theLignePO.nomProjet=nomProjet;
        theLignePO.charge=charge;
        ListeLignePO_static.addElement(theLignePO);
      }
    }
            catch (SQLException s) {s.getMessage();}
}
  public void getLignesPO(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;


    req = " SELECT     RoadmapPO.idPO, RoadmapPO.Charge,RoadmapPO.Annee,PlanOperationnel.nomProjet, PlanOperationnel.charge AS chargePO";
    req+=" FROM         RoadmapPO INNER JOIN";
    req+="                PlanOperationnel ON RoadmapPO.idPO = PlanOperationnel.idWebPo AND RoadmapPO.Annee = PlanOperationnel.Annee";

    req+=" WHERE     (idRoadmap = "+this.id+") order by idPO asc";



    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        String idWebPo= rs.getString("idPO"); // nom du ST
        String Enveloppe= ""; // nom du ST
        String descProjet= ""; // nom du ST
        String risqueProjet= ""; // etat du ST
        float charge= rs.getFloat("charge"); // nom du ST
        int Annee= rs.getInt("Annee"); // nom du ST
        String nomProjet= rs.getString("nomProjet"); // nom du ST
        String gainProjet= "";
        String Priorite= "";
        float chargePO= rs.getFloat("chargePO");

        LignePO theLignePO = new LignePO(idWebPo,Enveloppe, nomProjet, descProjet, risqueProjet,chargePO,  gainProjet,  Priorite );
        theLignePO.Annee=""+Annee;
        theLignePO.chargeEngagee=charge;
        this.ListeLignePO.addElement(theLignePO);
      }
    }
            catch (SQLException s) {s.getMessage();}
}

public void getLignesPOAll(String nomBase,Connexion myCnx, Statement st, String nomService ){
            ResultSet rs, rs2;


            req = "SELECT DISTINCT RoadmapPO.idPO, PlanOperationnelClient.charge as chargePO, RoadmapPO.Charge, RoadmapPO.Annee,  PlanOperationnelClient.nomProjet, RoadmapPO.dateTest_devis, RoadmapPO.dateMep_devis";
            req+="     FROM         RoadmapPO INNER JOIN";
            req+="           PlanOperationnelClient ON RoadmapPO.idPO = PlanOperationnelClient.idWebPo AND RoadmapPO.Annee = PlanOperationnelClient.Annee";
            req+=" WHERE     (idRoadmap = "+this.id+") ";
            req+=" AND     (PlanOperationnelClient.Service = '"+nomService+"')";
            req += " order by idPO asc";



            rs = myCnx.ExecReq(st, myCnx.nomBase, req);
            try {
              while (rs.next()) {
                String idWebPo= rs.getString("idPO"); // nom du ST
                String Enveloppe= ""; // nom du ST
                String descProjet= ""; // nom du ST
                String risqueProjet= ""; // etat du ST
                float chargePO= rs.getFloat("chargePO"); // nom du ST
                float chargeEngagee= rs.getFloat("Charge"); // nom du ST
                int Annee= rs.getInt("Annee"); // nom du ST
                String nomProjet= rs.getString("nomProjet"); // nom du ST
                if (nomProjet == null) nomProjet="non inscrit au PO";
                String gainProjet= "";
                String Priorite= "";

            LignePO theLignePO = new LignePO(idWebPo, Enveloppe,
                                             nomProjet, descProjet, risqueProjet, chargePO,
                                             gainProjet,
                                             Priorite);
            theLignePO.Annee = "" + Annee;
            theLignePO.chargeEngagee = chargeEngagee;
            theLignePO.charge = chargePO;

            theLignePO.DdateTest_devis = rs.getDate("dateTest_devis");
            if (theLignePO.DdateTest_devis != null)
              theLignePO.dateTest_devis = ""+theLignePO.DdateTest_devis.getDate()+"/"+(theLignePO.DdateTest_devis.getMonth()+1) + "/"+(theLignePO.DdateTest_devis.getYear() + 1900);
            else
              theLignePO.dateTest_devis = "";

            theLignePO.DdateProd_devis = rs.getDate("dateMep_devis");
            if (theLignePO.DdateProd_devis != null)
              theLignePO.dateProd_devis = ""+theLignePO.DdateProd_devis.getDate()+"/"+(theLignePO.DdateProd_devis.getMonth()+1) + "/"+(theLignePO.DdateProd_devis.getYear() + 1900);
            else
              theLignePO.dateProd_devis = "";

            this.ListeLignePO.addElement(theLignePO);

              }
            }
                    catch (SQLException s) {s.getMessage();}


}

  public void getLignesPOAll_old(String nomBase,Connexion myCnx, Statement st ){
              ResultSet rs, rs2;


              req = "SELECT  distinct RoadmapPO.idPO, RoadmapPO.Charge, RoadmapPO.Annee, PlanOperationnel.nomProjet, RoadmapPO.dateTest_devis,  RoadmapPO.dateMep_devis";
              req+="     FROM RoadmapPO LEFT OUTER JOIN";
              req+=" PlanOperationnel ON RoadmapPO.idPO = PlanOperationnel.idWebPo AND RoadmapPO.Annee = PlanOperationnel.Annee";
              req+=" WHERE     (idRoadmap = "+this.id+") ";
              req += " order by idPO asc";



              rs = myCnx.ExecReq(st, myCnx.nomBase, req);
              try {
                while (rs.next()) {
                  String idWebPo= rs.getString("idPO"); // nom du ST
                  String Enveloppe= ""; // nom du ST
                  String descProjet= ""; // nom du ST
                  String risqueProjet= ""; // etat du ST
                  float charge= rs.getFloat("charge"); // nom du ST
                  int Annee= rs.getInt("Annee"); // nom du ST
                  String nomProjet= rs.getString("nomProjet"); // nom du ST
                  if (nomProjet == null) nomProjet="non inscrit au PO";
                  String gainProjet= "";
                  String Priorite= "";
                  float chargePO = 0;

              LignePO theLignePO = new LignePO(idWebPo, Enveloppe,
                                               nomProjet, descProjet, risqueProjet, chargePO,
                                               gainProjet,
                                               Priorite);
              theLignePO.Annee = "" + Annee;
              theLignePO.chargeEngagee = charge;

              theLignePO.DdateTest_devis = rs.getDate("dateTest_devis");
              if (theLignePO.DdateTest_devis != null)
                theLignePO.dateTest_devis = ""+theLignePO.DdateTest_devis.getDate()+"/"+(theLignePO.DdateTest_devis.getMonth()+1) + "/"+(theLignePO.DdateTest_devis.getYear() + 1900);
              else
                theLignePO.dateTest_devis = "";

              theLignePO.DdateProd_devis = rs.getDate("dateMep_devis");
              if (theLignePO.DdateProd_devis != null)
                theLignePO.dateProd_devis = ""+theLignePO.DdateProd_devis.getDate()+"/"+(theLignePO.DdateProd_devis.getMonth()+1) + "/"+(theLignePO.DdateProd_devis.getYear() + 1900);
              else
                theLignePO.dateProd_devis = "";

              this.ListeLignePO.addElement(theLignePO);

                }
              }
                      catch (SQLException s) {s.getMessage();}


      for (int i=0; i < this.ListeLignePO.size();i++)
      {
        LignePO theLignePO = (LignePO)this.ListeLignePO.elementAt(i);
        req = "SELECT     charge";
        req+="     FROM         PlanOperationnel";
        req+=" WHERE     (idWebPo = "+theLignePO.idWebPo+")AND (Annee = "+theLignePO.Annee+")";
                      req+=" ORDER BY dateImport DESC";
        rs = myCnx.ExecReq(st, myCnx.nomBase, req);
        try {
          rs.next();

          float chargePO = rs.getFloat("charge");
          theLignePO.charge = chargePO;

        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
  }

  public void getListeCollaborateurs(String nomBase,Connexion myCnx, Statement st , int annee){
  ResultSet rs;
  String nom="";
  String dateEB="";
  String datePROD="";
  String dateMES="";
  int idVersionSt = -1;
  String version= "";

  req="exec [PROJET_selectPlanDeCharge] "+this.id+","+annee;
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     int idMembre = rs.getInt("projetMembre");
     Collaborateur theCollaborateur = new Collaborateur(idMembre );
     this.ListeCollaborateurs.addElement(theCollaborateur);
   }

  }
   catch (SQLException s) { }

   this.maxCollaborateurs = this.ListeCollaborateurs.size() + 10;
}
  
public void getListeDevis(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs;

  req=" SELECT     Devis.id, typeJalon.nom AS typeDevis, CAST(Devis.idRoadmap AS VARCHAR(50)) + '-' + Roadmap.version AS nomDevis, Devis.LoginCreateur, typeEtatDevis.nom AS Etat";
  req+="  FROM         Devis INNER JOIN";
  req+="                       typeEtatDevis ON Devis.idEtat = typeEtatDevis.id INNER JOIN";
  req+="                       Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
  req+="                       typeJalon ON Devis.typeJalon = typeJalon.id";
  req+=" WHERE     Devis.idRoadmap = " + this.id;
  req+=" ORDER BY typeJalon.ordre";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     Devis theDevis= new Devis(rs.getInt("id"), "Devis" );
     theDevis.type = rs.getString("typeDevis");
     theDevis.nomProjet = rs.getString("nomDevis");
     theDevis.LoginCreateur = rs.getString("LoginCreateur");
     theDevis.Etat = rs.getString("Etat");
     this.ListeDevis.addElement(theDevis);
   }

  }
   catch (SQLException s) { }

   this.maxCollaborateurs = this.ListeCollaborateurs.size() + 10;
}  
  
public void getListeTypeJalons(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs;
  String nom="";
  String dateEB="";
  String datePROD="";
  String dateMES="";
  int idVersionSt = -1;
  String version= "";

  req="SELECT     typeJalon.id, typeJalon.nom, typeJalon.description, typeJalon.ordre, typeJalon.alias, typeJalon.isEssentiel, typeJalon.isStLie, typeJalon.couleur, typeJalon.numIndicateur, ";
  req+= "                      typeJalon.isEngagement, typeJalon.typeDoc, TypeDocumentation.descType, TypeDocumentation.nomType";
  req+= "  FROM         typeJalon LEFT OUTER JOIN";
  req+= "                        TypeDocumentation ON typeJalon.typeDoc = TypeDocumentation.idTypeDoc";
  req+= "  ORDER BY typeJalon.ordre  ";
  
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     typeJalon theTypeJalon = new typeJalon(rs.getInt("id"));
     theTypeJalon.nom = rs.getString("nom");
     theTypeJalon.desc = rs.getString("description");
     theTypeJalon.ordre = rs.getInt("ordre");
     theTypeJalon.periode = rs.getString("alias");
     theTypeJalon.isEssentiel = rs.getInt("isEssentiel");
     theTypeJalon.isStLie = rs.getInt("isStLie");
     theTypeJalon.couleur = rs.getString("couleur");
     theTypeJalon.numIndicateur = rs.getInt("numIndicateur");
     theTypeJalon.isEngagement = rs.getInt("isEngagement");
     theTypeJalon.typeDoc = rs.getInt("typeDoc");
     theTypeJalon.Livrable = rs.getString("descType");
     theTypeJalon.nomLivrable = rs.getString("nomType");
     this.ListeTypeJalons.addElement(theTypeJalon);
     if (theTypeJalon.isEssentiel == 1) this.ListeTypeJalonsEssentiels.addElement(theTypeJalon);
   }

  }
   catch (SQLException s) { }

   this.maxCollaborateurs = this.ListeCollaborateurs.size() + 10;
}

public void getListeJalons(String nomBase,Connexion myCnx, Statement st, boolean isEssentiel){
  ResultSet rs;
    req="SELECT     JalonsProjet.id, typeJalon.nom, typeJalon.description, typeJalon.ordre, typeJalon.alias, typeJalon.isEssentiel, typeJalon.isStLie, JalonsProjet.date, JalonsProjet.date_init, JalonsProjet.type, ";
    req+="                       typeJalon.couleur, JalonsProjet.date_moe, JalonsProjet.date_go, JalonsProjet.type AS Expr1, JalonsProjet.Livrable, JalonsProjet.Remarques, typeJalon.numIndicateur, typeJalon.isEngagement, ";
    req+="                       TypeDocumentation.descType, JalonsProjet.Livrable";
    req+=" FROM         typeJalon INNER JOIN";
    req+="                       JalonsProjet ON typeJalon.id = JalonsProjet.type LEFT OUTER JOIN";
    req+="                       TypeDocumentation ON typeJalon.typeDoc = TypeDocumentation.idTypeDoc";
    req+=" WHERE     JalonsProjet.idRoadmap = "+ this.id;
    if (isEssentiel)
    {
        req+=" AND     typeJalon.isEssentiel = 1";
    }
    req+=" ORDER BY typeJalon.ordre";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  this.isDecalage = false;
  try {
  while(rs.next())
  {
     Jalon theJalon = new Jalon(rs.getInt("id"));
     theJalon.nom = rs.getString("nom");
     theJalon.ordre = rs.getInt("ordre");
     theJalon.alias = rs.getString("alias");
     theJalon.isEssentiel = rs.getInt("isEssentiel");
     theJalon.isStLie = rs.getInt("isStLie");
     
     theJalon.date = rs.getDate("date");
        if (theJalon.date != null)
          theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
         else
          theJalon.strDate = "";  
        
     theJalon.date_init = rs.getDate("date_init");
        if (theJalon.date_init != null)
          theJalon.strDate_init = ""+theJalon.date_init.getDate()+"/"+(theJalon.date_init.getMonth()+1) + "/"+(theJalon.date_init.getYear() + 1900);
         else
          theJalon.strDate_init = "";          
        
     theJalon.type = rs.getInt("type");
     theJalon.couleur = rs.getString("couleur");        
        
     theJalon.date_moe = rs.getDate("date_moe");
        if (theJalon.date_moe != null)
          theJalon.strDate_moe = ""+theJalon.date_moe.getDate()+"/"+(theJalon.date_moe.getMonth()+1) + "/"+(theJalon.date_moe.getYear() + 1900);
         else
          theJalon.strDate_moe = "";      
        
     theJalon.date_go = rs.getDate("date_go");
        if (theJalon.date_go != null)
          theJalon.strDate_go = ""+theJalon.date_go.getDate()+"/"+(theJalon.date_go.getMonth()+1) + "/"+(theJalon.date_go.getYear() + 1900);
         else
          theJalon.strDate_go = "";             
        
     theJalon.type = rs.getInt("type");

     
     theJalon.Commentaire = rs.getString("Remarques");
     if (theJalon.Commentaire == null || theJalon.Commentaire.equals("null")) theJalon.Commentaire = "";
     
     theJalon.numIndicateur = rs.getInt("numIndicateur");
     theJalon.isEngagement = rs.getInt("isEngagement");
     
     theJalon.str_typeDoc = rs.getString("descType");
     if (theJalon.str_typeDoc == null || theJalon.str_typeDoc.equals("null")) theJalon.str_typeDoc = "";     
     
     theJalon.Livrable = rs.getString("Livrable");
     if (theJalon.Livrable == null || theJalon.Livrable.equals("null")) theJalon.Livrable = "";         
 
     if (!theJalon.strDate_init.equals("") && !theJalon.strDate_init.equals("1/1/1900") && !theJalon.strDate_init.equals(theJalon.strDate) )
         this.isDecalage = true;     
     this.ListeJalons.addElement(theJalon);
   }

  }
   catch (SQLException s) { }

}

public void getListeJalonsEssentiels(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs;
    req="SELECT     typeJalon.id, typeJalon.nom, typeJalon.description, typeJalon.ordre, typeJalon.alias, typeJalon.isEssentiel, typeJalon.isStLie, JalonsProjet.date, JalonsProjet.date_init, JalonsProjet.type, typeJalon.couleur";
    req+=" FROM         typeJalon INNER JOIN";
    req+="                       JalonsProjet ON typeJalon.id = JalonsProjet.type";
    req+=" WHERE     JalonsProjet.idRoadmap = "+ this.id;
    req+=" AND YEAR(JalonsProjet.date) > 1900";
    req+=" AND     typeJalon.isEssentiel = 1";
    req+=" ORDER BY typeJalon.ordre";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  this.isDecalage = false;
  try {
  while(rs.next())
  {
     Jalon theJalon = new Jalon(rs.getInt("id"));
     theJalon.nom = rs.getString("nom");
     theJalon.ordre = rs.getInt("ordre");
     theJalon.alias = rs.getString("alias");
     theJalon.isEssentiel = rs.getInt("isEssentiel");
     theJalon.isStLie = rs.getInt("isStLie");
     theJalon.date = rs.getDate("date");
        if (theJalon.date != null)
          theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
         else
          theJalon.strDate = "";  
        
     theJalon.date_init = rs.getDate("date_init");
        if (theJalon.date_init != null)
          theJalon.strDate_init = ""+theJalon.date_init.getDate()+"/"+(theJalon.date_init.getMonth()+1) + "/"+(theJalon.date_init.getYear() + 1900);
         else
          theJalon.strDate_init = "";          
        
     theJalon.type = rs.getInt("type");
     theJalon.couleur = rs.getString("couleur");
     
     if (!theJalon.strDate_init.equals("") && !theJalon.strDate_init.equals("1/1/1900") && !theJalon.strDate_init.equals(theJalon.strDate) )
         this.isDecalage = true;
         
     this.ListeJalons.addElement(theJalon);
   }

  }
   catch (SQLException s) { }

}

public Jalon getJalonsByType(String nomBase,Connexion myCnx, Statement st, int type){
  if (this.id == -1) return null;
  ResultSet rs;
    req="SELECT    id, date, date_init, date_moe, date_go, Livrable,Remarques  FROM  JalonsProjet WHERE type = " + type + " AND  idRoadmap = " + this.id;

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  
  Jalon theJalon = null;

  try {
  while(rs.next())
  {
     theJalon = new Jalon();
     theJalon.id = rs.getInt("id");
     theJalon.date = rs.getDate("date");
        if (theJalon.date != null)
          theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
         else
          theJalon.strDate = "";   
        
     theJalon.date_init = rs.getDate("date_init");
        if (theJalon.date_init != null)
          theJalon.strDate_init = ""+theJalon.date_init.getDate()+"/"+(theJalon.date_init.getMonth()+1) + "/"+(theJalon.date_init.getYear() + 1900);
         else
          theJalon.strDate_init = "";     
        
     theJalon.date_moe = rs.getDate("date_moe");
        if (theJalon.date_moe != null)
          theJalon.strDate_moe = ""+theJalon.date_moe.getDate()+"/"+(theJalon.date_moe.getMonth()+1) + "/"+(theJalon.date_moe.getYear() + 1900);
         else
          theJalon.strDate_moe = "";  
        
     theJalon.date_go = rs.getDate("date_go");
        if (theJalon.date_go != null)
          theJalon.strDate_go = ""+theJalon.date_go.getDate()+"/"+(theJalon.date_go.getMonth()+1) + "/"+(theJalon.date_go.getYear() + 1900);
         else
          theJalon.strDate_go = "";    
        
     theJalon.Livrable = rs.getString("Livrable");
     if (theJalon.Livrable == null || theJalon.Livrable.equals("null")) theJalon.Livrable = "";
     
     theJalon.Commentaire = rs.getString("Remarques");
     if (theJalon.Commentaire == null || theJalon.Commentaire.equals("null")) theJalon.Commentaire = "";        
        
   }

  }
   catch (SQLException s) { }
    return theJalon; 
}

public Jalon getJalonsEssentielByOrdre(String nomBase,Connexion myCnx, Statement st, int ordre){
  ResultSet rs;
    req="SELECT     JalonsProjet.id, typeJalon.ordre, JalonsProjet.type, JalonsProjet.date, JalonsProjet.date_init, typeJalon.nom";
    req+=" FROM         JalonsProjet INNER JOIN    ";
    req+="                       typeJalon ON JalonsProjet.type = typeJalon.id    ";
    req+=" WHERE     JalonsProjet.idRoadmap = "+this.id+" AND (typeJalon.isEssentiel = 1)";
    req+=" ORDER BY typeJalon.ordre    ";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  
  Jalon theJalon = null;
  int nb = 1;
  try {
  while(rs.next())
  {
   if (nb == ordre)
   {
     theJalon = new Jalon();
     theJalon.id = rs.getInt("id");
     theJalon.idRoadmap = this.id;
     theJalon.ordre = rs.getInt("ordre");
     theJalon.type = rs.getInt("type");
     theJalon.date = rs.getDate("date");
        if (theJalon.date != null)
          theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
         else
          theJalon.strDate = "";   
        
     theJalon.date_init = rs.getDate("date_init");
        if (theJalon.date_init != null)
          theJalon.strDate_init = ""+theJalon.date_init.getDate()+"/"+(theJalon.date_init.getMonth()+1) + "/"+(theJalon.date_init.getYear() + 1900);
         else
          theJalon.strDate_init = "";  
        
     theJalon.nom = rs.getString("nom");
     return theJalon;
   }
   nb++;
   }

  }
   catch (SQLException s) { }
    return theJalon; 
}

public String getJalonsLie(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs;
    req="SELECT     JalonsProjet.date";
    req+= " FROM         JalonsProjet INNER JOIN";
    req+= "                       typeJalon ON JalonsProjet.type = typeJalon.id";
    req+= " WHERE     JalonsProjet.idRoadmap = "+this.id+" AND (typeJalon.isStLie = 1)";

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  
  Jalon theJalon = null;
  String theStr="";

  try {
  while(rs.next())
  {
     theJalon = new Jalon();
     theJalon.id = -1;
     theJalon.date = rs.getDate("date");
        if (theJalon.date != null)
          theStr = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
         else
          theStr = "";        
   }

  }
   catch (SQLException s) { }
    return theStr; 
}
public String getJalonsByOrdre(String nomBase,Connexion myCnx, Statement st, int ordre){
  ResultSet rs;
    req="SELECT     JalonsProjet.date";
    req+=" FROM         typeJalon INNER JOIN";
    req+="                       JalonsProjet ON typeJalon.id = JalonsProjet.type";
    req+=" WHERE     JalonsProjet.idRoadmap = "+ this.id + " AND typeJalon.ordre = " +ordre;

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  String theDate = "";

  try {
  while(rs.next())
  {
     Date date = rs.getDate("date");
        if (date != null)
          theDate = ""+date.getDate()+"/"+(date.getMonth()+1) + "/"+(date.getYear() + 1900);
         else
          theDate = "";     
   }

  }
   catch (SQLException s) { }
    return theDate; 
}
  
  public void getListeCollaborateursDispo(String nomBase,Connexion myCnx, Statement st, Statement st2, String ListeRessources){
  ResultSet rs;
  String nom="";
  String dateEB="";
  String datePROD="";
  String dateMES="";
  int idVersionSt = -1;
  String version= "";
  int semaine_debut=Utils.getWeek(nomBase,myCnx,  st,this.DdateT0.getDate(), this.DdateT0.getMonth()+1,this.DdateT0.getYear()+1900);
  int semaine_fin=Utils.getWeek(nomBase,myCnx,  st,this.DdateProd.getDate(), this.DdateProd.getMonth()+1,this.DdateProd.getYear()+1900);
  int annee=this.DdateT0.getYear()+1900;
  
  // delete tempListeMembre
    req = "DELETE FROM tempListeMembres";
    myCnx.ExecReq(st, myCnx.nomBase, req); 
  
  if (semaine_debut > semaine_fin) semaine_debut = 1;

    req="SELECT DISTINCT Membre.nomMembre, Membre.prenomMembre, Membre.LoginMembre, Membre.idMembre, Membre.photo,Service.nomService, Direction.nomDirection";
    req+=" FROM         Membre INNER JOIN";
    req+="                       CollaborateurCompetence ON Membre.idMembre = CollaborateurCompetence.idCollaborateur INNER JOIN";
    req+="                       Service ON Membre.serviceMembre = Service.idService INNER JOIN";
    req+="                      Direction ON Service.dirService = Direction.idDirection";
    req+=" WHERE     (CollaborateurCompetence.note <> - 1)";
    if (ListeRessources != null && !ListeRessources.equals(""))
    {
     req+=" AND idMembre not in ("+ListeRessources+")";   
    }
    req+=" ORDER BY Membre.nomMembre";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     int idMembre = rs.getInt("idMembre");
     Collaborateur theCollaborateur = new Collaborateur(idMembre );
     theCollaborateur.nom = rs.getString("nomMembre");
     theCollaborateur.prenom = rs.getString("prenomMembre");
     theCollaborateur.Login = rs.getString("LoginMembre");
     theCollaborateur.photo = rs.getString("photo");
     theCollaborateur.nomService = rs.getString("nomService");
     theCollaborateur.nomDirection = rs.getString("nomDirection");
     theCollaborateur.getTotalChargeRoadmap(nomBase,myCnx,st2, semaine_debut,semaine_fin,annee);
     theCollaborateur.getTotalCompetenceRoadmap(nomBase,myCnx,st2, this.id);
     //this.ListeCollaborateurs.addElement(theCollaborateur);
     // note = noteCompetence - note Dispo
     // insertion dans temp liste
     // insertoion dans tempListe
     req = "INSERT tempListeMembres (  idMembre, nomMembre, prenomMembre, LoginMembre, noteCompetence, noteDisponibilite, photo) ";
     req+=" VALUES (";
     req+="'"+theCollaborateur.id + "'";   
     req+=",";
     req+="'"+theCollaborateur.nom + "'";   
     req+=",";
     req+="'"+theCollaborateur.prenom + "'";   
     req+=",";
     req+="'"+theCollaborateur.Login + "'";   
     req+=",";
     req+="'"+theCollaborateur.noteCompetence + "'";   
     req+=",";
     req+="'"+theCollaborateur.noteCharge + "'";   
     req+=",";
     req+="'"+theCollaborateur.photo + "'";       
     req+=")";
 
     myCnx.ExecReq(st2, myCnx.nomBase, req);    
   }

  }
   catch (SQLException s) { }
  
  // Lecture du TOP 20 par note decroissante
  // insertion dans ListeCollaborateurs
    req="SELECT     TOP (10)  tempListeMembres.Login, tempListeMembres.idMembre, tempListeMembres.nomMembre, tempListeMembres.prenomMembre, tempListeMembres.LoginMembre, Membre.photo,";
    req+="                      tempListeMembres.noteCompetence, tempListeMembres.noteDisponibilite, tempListeMembres.noteCompetence - tempListeMembres.noteDisponibilite / 5 AS note, Service.nomService, ";
    req+="                       Direction.nomDirection";
    req+=" FROM         Direction INNER JOIN";
    req+="                       Service ON Direction.idDirection = Service.dirService INNER JOIN";
    req+="                       Membre ON Service.idService = Membre.serviceMembre INNER JOIN";
    req+="                       tempListeMembres ON Membre.idMembre = tempListeMembres.idMembre";
    req+=" ORDER BY note DESC";
     
    //req+=" where idRoadmap <> "+this.id;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        int idMembre = rs.getInt("idMembre");
        Collaborateur theCollaborateur = new Collaborateur(idMembre );
        theCollaborateur.nom = rs.getString("nomMembre");
        theCollaborateur.prenom = rs.getString("prenomMembre");
        theCollaborateur.Login = rs.getString("LoginMembre");
        theCollaborateur.photo = rs.getString("photo");
        theCollaborateur.noteCompetence = rs.getInt("noteCompetence");
        theCollaborateur.noteCharge = rs.getInt("noteDisponibilite");
        theCollaborateur.nomService = rs.getString("nomService");
        theCollaborateur.nomDirection = rs.getString("nomDirection");

        this.ListeCollaborateurs.addElement(theCollaborateur);
      }
    }
            catch (SQLException s) {s.getMessage();}  

   
}  
  
  public static void getListeProjets(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    String req = "SELECT     idRoadmap, nomSt + '-' + version AS nomProjet, isRecurrent, isST, isAppli";
    req+= " FROM         ListeProjets";
    req+= " WHERE     (LF_Month = - 1) AND (LF_Year = - 1) AND (YEAR(dateMep) > 2013) AND (isRecurrent = 0) AND (isST = 1) OR";
    req+= "                       (LF_Month = - 1) AND (LF_Year = - 1) AND (YEAR(dateMep) > 2013)";
    req+= " ORDER BY nomProjet";
    
    //req+=" where idRoadmap <> "+this.id;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap"));
        theRoadmap.nomProjet = rs.getString("nomProjet");
        ListeRoadmap_static.addElement(theRoadmap);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }
  public static void getListeDocsMigration(String nomBase,Connexion myCnx, Statement st){
   
    ResultSet rs;
    String req = "";
      
    req = "SELECT     idRoadmap, version, docEB FROM Roadmap WHERE     (docEB <> '')";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap"));
        //if (theRoadmap.id == 54594)
        
        theRoadmap.docEB = rs.getString("docEB");
 
        Jalon theJalon = new Jalon();
        theJalon.idRoadmap = theRoadmap.id;   
  
        ListeRoadmap_static.addElement(theRoadmap);
        
      }
    }
            catch (SQLException s) {s.getMessage();}      
  }  
  public static void getListeProjetsMigration(String nomBase,Connexion myCnx, Statement st){
    int typeAD = -1;
    int typeT0 = -1;
    int typeEB = -1;
    int typeTEST = -1;
    int typeMEP = -1;
    int typeVSR = -1;
   
    ResultSet rs;
    String req = "";

    req = "SELECT     id, nom FROM  typeJalon WHERE (ordre =-1 )";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
            typeAD =  rs.getInt("id");
      }}   catch (SQLException s) {s.getMessage();}   
    
    req = "SELECT     id, nom FROM  typeJalon WHERE (numIndicateur =1 )";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
            typeT0 =  rs.getInt("id");
      }}   catch (SQLException s) {s.getMessage();}     
    
    req = "SELECT     id, nom FROM  typeJalon WHERE (numIndicateur =2 )";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
            typeEB =  rs.getInt("id");
      }}   catch (SQLException s) {s.getMessage();}   
    
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
    
    req = "SELECT     id, nom FROM  typeJalon WHERE (numIndicateur =5 )";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
            typeVSR =  rs.getInt("id");
      }}   catch (SQLException s) {s.getMessage();}       
    
    req = "select idRoadmap, dateT0, dateT0_init, dateEB, dateEB_init, dateTest, dateTest_init, dateMep, dateMep_init, dateMes, dateMes_init from roadmap where roadmap.LF_Month = -1  and roadmap.LF_Year = -1 and (YEAR(roadmap.dateT0) > 2013 OR YEAR(roadmap.dateEB) > 2013 OR YEAR(roadmap.dateTest) > 2013 OR YEAR(roadmap.dateMep) > 2013)";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap"));
        //if (theRoadmap.id == 54594)
        {
        theRoadmap.DdateT0 = rs.getDate("dateT0");
        theRoadmap.DdateT0_init = rs.getDate("dateT0_init");
        
        theRoadmap.DdateEB = rs.getDate("dateEB");
        theRoadmap.DdateEB_init = rs.getDate("dateEB_init");
        
        theRoadmap.DdateTest = rs.getDate("dateTest");
        theRoadmap.DdateTest_init = rs.getDate("dateTest_init");
        
        theRoadmap.DdateProd = rs.getDate("dateMep");
        theRoadmap.DdateProd_init = rs.getDate("dateMep_init");
        
        theRoadmap.DdateMes = rs.getDate("dateMes");
        theRoadmap.DdateMes_init = rs.getDate("dateMes_init");
        
        if (theRoadmap.DdateT0!= null)
        {
            Jalon theJalon = new Jalon();
            theJalon.idRoadmap = theRoadmap.id;
            theJalon.type = typeT0;
            theJalon.nom = "T0";
            theJalon.alias = "T0";
            theJalon.isEssentiel = 1;
            theJalon.isStLie = 0;
            theJalon.date = theRoadmap.DdateT0;
            theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
            if (theRoadmap.DdateT0_init != null)
            {
                theJalon.date_init = theRoadmap.DdateT0_init;
                theJalon.strDate_init = ""+theJalon.date_init.getDate()+"/"+(theJalon.date_init.getMonth()+1) + "/"+(theJalon.date_init.getYear() + 1900);
            }
            theRoadmap.ListeJalons.addElement(theJalon);
        }
        
        if (theRoadmap.DdateEB!= null)
        {
            Jalon theJalon = new Jalon();
            theJalon.idRoadmap = theRoadmap.id;
            theJalon.type = typeEB;
            theJalon.nom = "EB";
            theJalon.alias = "EB";
            theJalon.isEssentiel = 1;
            theJalon.isStLie = 0;
            theJalon.date = theRoadmap.DdateEB;
            theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
            if (theRoadmap.DdateEB_init != null) 
            {
                theJalon.date_init = theRoadmap.DdateEB_init;
                theJalon.strDate_init = ""+theJalon.date_init.getDate()+"/"+(theJalon.date_init.getMonth()+1) + "/"+(theJalon.date_init.getYear() + 1900);
            }
            theRoadmap.ListeJalons.addElement(theJalon);
        }
        
        if (theRoadmap.DdateTest!= null)
        {
            Jalon theJalon = new Jalon();
            theJalon.idRoadmap = theRoadmap.id;
            theJalon.type = typeTEST;
            theJalon.nom = "TEST";
            theJalon.alias = "TEST";
            theJalon.isEssentiel = 1;
            theJalon.isStLie = 0;
            theJalon.date = theRoadmap.DdateTest;
            theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
            if (theRoadmap.DdateTest_init != null)  
            {
                theJalon.date_init = theRoadmap.DdateTest_init;
                theJalon.strDate_init = ""+theJalon.date_init.getDate()+"/"+(theJalon.date_init.getMonth()+1) + "/"+(theJalon.date_init.getYear() + 1900);
            }
            theRoadmap.ListeJalons.addElement(theJalon);
        }
        
        if (theRoadmap.DdateProd!= null)
        {
            Jalon theJalon = new Jalon();
            theJalon.idRoadmap = theRoadmap.id;
            theJalon.type = typeMEP;
            theJalon.nom = "MEP";
            theJalon.alias = "MEP";
            theJalon.isEssentiel = 1;
            theJalon.isStLie = 1;
            theJalon.date = theRoadmap.DdateProd;
            theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
            if (theRoadmap.DdateProd_init != null) 
            {
                theJalon.date_init = theRoadmap.DdateProd_init;
                theJalon.strDate_init = ""+theJalon.date_init.getDate()+"/"+(theJalon.date_init.getMonth()+1) + "/"+(theJalon.date_init.getYear() + 1900);
            }
            theRoadmap.ListeJalons.addElement(theJalon);
        }
        
        if (theRoadmap.DdateMes!= null)
        {
            Jalon theJalon = new Jalon();
            theJalon.idRoadmap = theRoadmap.id;
            theJalon.type = typeVSR;
            theJalon.nom = "VSR";
            theJalon.alias = "VSR";
            theJalon.isEssentiel = 1;
            theJalon.isStLie = 0;
            theJalon.date = theRoadmap.DdateMes; 
            theJalon.strDate = ""+theJalon.date.getDate()+"/"+(theJalon.date.getMonth()+1) + "/"+(theJalon.date.getYear() + 1900);
            if (theRoadmap.DdateMes_init != null)  
            {
                theJalon.date_init = theRoadmap.DdateMes_init;
                theJalon.strDate_init = ""+theJalon.date_init.getDate()+"/"+(theJalon.date_init.getMonth()+1) + "/"+(theJalon.date_init.getYear() + 1900);
            }
            theRoadmap.ListeJalons.addElement(theJalon);
        }        
  
        ListeRoadmap_static.addElement(theRoadmap);
        }
      }
    }
            catch (SQLException s) {s.getMessage();}

  }
  
  public void getListeRoadmap(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    String req = "select distinct idRoadmap from ";
    req+=" ( ";
    req+=" SELECT     idRoadmapDestination as idRoadmap";
    req+=" FROM         RoadmapDependances";
    //req+=" WHERE     (nb > 0)";

    req+=" union";

    req+=" SELECT     idRoadmapOrigine as idRoadmap";
    req+=" FROM         RoadmapDependances";
    //req+=" WHERE     (nb > 0)";
    req+=" ) as myTable";
    
    //req+=" where idRoadmap <> "+this.id;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap"));
        this.ListeRoadmap.addElement(theRoadmap);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }  
  
  public void getListeRoadmapFils(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    String req = "SELECT     idRoadmap, idRoadmapPere FROM         Roadmap WHERE    idRoadmapPere = "+this.id;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap"));
        this.ListeRoadmap.addElement(theRoadmap);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }   
  
  public void getListeCategories(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    String req = "SELECT     categorieTest.id, categorieTest.nom, categorieTest.description";
           req+=" FROM         Roadmap INNER JOIN";
           req+="            categorieTest ON Roadmap.idRoadmap = categorieTest.idRoadmap";
           req+=" WHERE     (Roadmap.idRoadmap = "+this.id+") ORDER by categorieTest.Ordre asc";



    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        CategorieTest theCategorieTest = new CategorieTest(rs.getInt("id"));

        theCategorieTest.nom= rs.getString("nom");
        theCategorieTest.description= rs.getString("description");
        //theCategorieTest.dump();
        this.ListeCategories.addElement(theCategorieTest);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }
  
  public void getListeDependances(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    String req = "SELECT     RoadmapDependances.idRoadmapDestination, RoadmapDependances.idRoadmapOrigine, RoadmapDependances.type, Roadmap.dateMep, St.nomSt, ";
    req+= "                      Roadmap.version";
    req+= " FROM         RoadmapDependances INNER JOIN";
    req+= "                       Roadmap ON RoadmapDependances.idRoadmapOrigine = Roadmap.idRoadmap INNER JOIN";
    req+= "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+= "                       St ON VersionSt.stVersionSt = St.idSt";
    req+= " WHERE     (RoadmapDependances.idRoadmapDestination = "+this.id+")";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
          dependanceRoadmap theDependance = new dependanceRoadmap();
          
          theDependance.roadmapDestination = this;
          theDependance.roadmapOrigine= new Roadmap(rs.getInt("idRoadmapOrigine"));                
          theDependance.type = rs.getString("type");
          theDependance.roadmapOrigine.DdateProd = rs.getDate("dateMep");
          theDependance.nomStRoadmapOrigine = rs.getString("nomSt");
          theDependance.versionRoadmapOrigine = rs.getString("version");
    
          this.ListeDependancesT0.addElement(theDependance);
      }
    }
            catch (SQLException s) {s.getMessage();}
  }  

   public ErrorSpecific bd_updateRoadmapPere(String nomBase,Connexion myCnx, Statement st, String transaction ){
    ErrorSpecific myError = new ErrorSpecific();

    ResultSet rs;
    Date date_start=null;
    Date date_end=null;

    req = "SELECT     MIN(JalonsProjet.date) as dateMin,  MAX(JalonsProjet.date) as dateMax";
    req += " FROM         JalonsProjet INNER JOIN";
    req += "                       Roadmap ON JalonsProjet.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req += "                       Roadmap AS Roadmap_1 ON Roadmap.idRoadmapPere = Roadmap_1.idRoadmap";
    req += " WHERE    Roadmap_1.idRoadmap = " + this.idPere;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    
    try {
    while (rs.next()) {
      date_start = rs.getDate("dateMin");
      date_end = rs.getDate("dateMax");    
    }
    } catch (SQLException s) {s.printStackTrace();}
 
    if ((date_start == null) || (date_end == null)) return myError;
    
    Jalon theJalonMin = new Jalon(-1);
    theJalonMin.isEssentiel = 1; 
    theJalonMin.date = date_start; 
    
    Jalon theJalonMax = new Jalon(-1);
    theJalonMax.isEssentiel = 1; 
    theJalonMax.date = date_end; 
    
    req = "SELECT     id";
    req += " FROM         typeJalon";
    req += " WHERE     (isEssentiel = 1) AND (ordre > - 1)";
    req += " ORDER BY ordre "; 
    
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    
    int num=0;
    try {
    while (rs.next()) {
        if (num == 0) theJalonMin.type = rs.getInt("id");
        else
            theJalonMax.type = rs.getInt("id");
      num++;
    }
    } catch (SQLException s) {s.printStackTrace();}  
 
    req = "DELETE JalonsProjet   WHERE idRoadmap ="+ this.idPere;    
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateRoadmapPere",""+this.id);
    if (myError.cause == -1) return myError;  

    String theDateMin = "convert(datetime, '"+theJalonMin.date.getDate() + "/" + (theJalonMin.date.getMonth() +1) + "/" +(theJalonMin.date.getYear() + 1900)+"', 103)";    
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateRoadmapPere",""+this.id);       
    req = "INSERT INTO [JalonsProjet]";
    req+= "           ([type]";
    req+= "            ,[date]";
    req+= "            ,[date_init]";
    req+= "            ,[idRoadmap]";
    req+=")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+theJalonMin.type+"'" + ",";
    req+= ""+theDateMin+"" + ",";
    req+= "NULL" + ",";
    req+= "'"+this.idPere+"'";
    req+= " )";

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateRoadmapPere",""+this.id); 
    if (myError.cause == -1) return myError; 
    
    String theDateMax = "convert(datetime, '"+theJalonMax.date.getDate() + "/" + (theJalonMax.date.getMonth() +1) + "/" +(theJalonMax.date.getYear() + 1900)+"', 103)";    
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateRoadmapPere",""+this.id);       
    req = "INSERT INTO [JalonsProjet]";
    req+= "           ([type]";
    req+= "            ,[date]";
    req+= "            ,[date_init]";
    req+= "            ,[idRoadmap]";
    req+=")";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+theJalonMax.type+"'" + ",";
    req+= ""+theDateMax+"" + ",";
    req+= "NULL" + ",";
    req+= "'"+this.idPere+"'";
    req+= " )";
    
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateRoadmapPere",""+this.id); 
    if (myError.cause == -1) return myError;     
    
    req = "SELECT   TOP(1)  JalonsProjet.type";
    req+= " FROM         JalonsProjet INNER JOIN";
    req+= "                       Roadmap ON JalonsProjet.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+= "                       typeJalon ON JalonsProjet.type = typeJalon.id";
    req+= " WHERE     (Roadmap.idRoadmapPere = "+this.idPere+")";
    req+= " ORDER BY typeJalon.ordre asc    ";
    
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    
    int idJalon = -1;
    try {
    while (rs.next()) {
        idJalon = rs.getInt("type");
    }
    } catch (SQLException s) {s.printStackTrace();}    

 
      req = "UPDATE Roadmap SET ";
      req +=" dateT0 ="+ theDateMin + ", ";
      req +=" dateEB ="+ theDateMax + ", ";
      req +=" dateTest ="+ theDateMax + ", ";
      req +=" dateMep ="+ theDateMax + ", ";
      req +=" dateMes ="+ theDateMax + ", ";
      req +=" idJalon ="+ idJalon + "";
      req += " WHERE idRoadmap ="+ this.idPere;

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
      if (myError.cause == -1) return myError;    
      
    return myError;
  }
   
   public ErrorSpecific bd_updateRoadmapPere2(String nomBase,Connexion myCnx, Statement st, String transaction ){
    ErrorSpecific myError = new ErrorSpecific();

    ResultSet rs;
    int nb = 0;

    req = "SELECT     MIN(dateT0) AS minT0, MAX(dateMep) AS maxMep, MAX(dateMes) AS maxMes";
    req+= " FROM         Roadmap";
    req+= " WHERE     idRoadmapPere = " + this.idPere;
    req+= "  AND (dateT0 > CONVERT(DATETIME, '2000-01-01 00:00:00', 102))";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    
    Date date_start=null;
    Date date_end=null;
    
    Date date_end1=null;
    Date date_end2=null;
    
    try {
    while (rs.next()) {
      date_start = rs.getDate("minT0");
      date_end1 = rs.getDate("maxMep");
      date_end2 = rs.getDate("maxMes");
      
      if (date_end1 != null)
      {
      if ((date_end2 != null) && date_end2.getTime() > date_end1.getTime())
          date_end = date_end2;
      else
          date_end = date_end1;
      }
      
      nb++;
     
        }
    }
        catch (SQLException s) {
          s.printStackTrace();
        }
    
    String str_date_start = "";
    String str_date_end = "";
    
    if (nb >0 && (date_start!= null)  && (date_end!= null))
    {
    str_date_start ="convert(datetime, '"+date_start.getDate()+"/"+(date_start.getMonth()+1)+"/"+(date_start.getYear() +1900)+"', 103)";
    str_date_end ="convert(datetime, '"+date_end.getDate()+"/"+(date_end.getMonth()+1)+"/"+(date_end.getYear() +1900)+"', 103)";
      
   
    }
    if (!str_date_start.equals(""))
    {
        req = "update Roadmap set";
        req+= " dateT0 = " + str_date_start;
        req += ",";
        req+= " dateT0_init = " + str_date_start;
        req+= " WHERE     idRoadmap = " + this.idPere;  
        myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateRoadmapPere",""+this.idPere);
        if (myError.cause == -1) return myError;  
    }
    if (!str_date_end.equals(""))
    {
        req = "update Roadmap set";
        req+= " dateEB = " + str_date_end;
        req += ",";
        req+= " dateTest = " + str_date_end;
        req += ",";
        req+= " dateMep = " + str_date_end;
        req += ",";    
        req+= " dateMes = " + str_date_end;
        req += ",";   
        req+= " dateEB_init = " + str_date_end;
        req += ",";
        req+= " dateTest_init = " + str_date_end;
        req += ",";
        req+= " dateMep_init = " + str_date_end;
        req += ",";    
        req+= " dateMes_init = " + str_date_end;        
        req+= " WHERE     idRoadmap = " + this.idPere;
        myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateRoadmapPere",""+this.idPere);
        if (myError.cause == -1) return myError;          
    }
  
 req = "DELETE FROM BaselinesPlanDeCharge";
 req+= " WHERE idBaseline IN ( ";
 req+= " SELECT     Baselines.id";
 req+= " FROM         Roadmap INNER JOIN";
 req+= "                       Baselines ON Roadmap.idRoadmapPere = Baselines.idRoadmap";
 req+= " WHERE     Roadmap.idRoadmap = " + this.id;
 req+= " ) "; 
 
 myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateRoadmapPere",""+this.idPere);
 if (myError.cause == -1) return myError;       
    return myError;
  }
  
    public void getMaxDateMep(String nomBase,Connexion myCnx, Statement st ){

    ResultSet rs;

    String req = "SELECT  TOP 1 * from";
    req+=" (";
    req+=" SELECT     MAX(dateFin) AS dateMep";
    req+=" FROM         actionSuivi";
    req+=" WHERE     (idRoadmap = "+this.id+") AND (isPlanning = 1)";

    req+=" union";

    req+=" SELECT     dateMep";
    req+=" FROM         Roadmap";
    req+=" WHERE     (idRoadmap = "+this.id+") ";
    req+=" ) ";
    req+=" as myTable order by dateMep desc";


rs = myCnx.ExecReq(st, myCnx.nomBase, req);

try {
  while (rs.next()) {
      this.DdateProd = rs.getDate("dateMep");

        if (this.DdateProd != null)
          this.dateProd = ""+this.DdateProd.getDate()+"/"+(this.DdateProd.getMonth()+1) + "/"+(this.DdateProd.getYear() + 1900);
         else
          this.dateProd = "";
  }
}
        catch (SQLException s) {
          s.printStackTrace();
        }

  

  }
  
  
  public void getListeSuivi(String nomBase,Connexion myCnx, Statement st ){

    ResultSet rs;

    String req = "SELECT     id, dateSuivi, description, Login, fichierAttache";
    req+= " FROM         historiqueSuivi";
    //req+= " WHERE     (idRoadmap = "+this.id+") AND (type = '"+this.type+"') order by datesuivi desc";
    req+= " WHERE     (idRoadmap = "+this.id+")  order by datesuivi desc";


rs = myCnx.ExecReq(st, myCnx.nomBase, req);

try {
  while (rs.next()) {
    Suivi theSuivi = new Suivi(rs.getInt(1));
    theSuivi.dateSuivi = rs.getDate(2);

    //theSuivi.nom = rs.getString("nom");

    theSuivi.description = rs.getString(3);
    theSuivi.Login = rs.getString("Login");
    if (theSuivi.Login == null) theSuivi.Login="";
    
    theSuivi.fichierAttache = rs.getString("fichierAttache");
    if (theSuivi.fichierAttache == null) theSuivi.fichierAttache="";
    //theSuivi.dump();
    //rs.getClob(3);

    //StringBuffer myBuffer = new StringBuffer(5000);
     //myBuffer.append(rs.getString(3));

     //theSuivi.description = rs.getBytes(3).toString();


    this.ListeSuivi.addElement(theSuivi);
  }
}
        catch (SQLException s) {
          s.printStackTrace();
        }

  }
  
  public Suivi getLastSuivi(String nomBase,Connexion myCnx, Statement st ){

    ResultSet rs;

    String req = "SELECT     TOP (1) description, dateSuivi";
    req+= " FROM         historiqueSuivi";
    req+= " WHERE     (idRoadmap = "+this.id+")  order by id desc";


rs = myCnx.ExecReq(st, myCnx.nomBase, req);

Suivi theSuivi = null;

try {
  while (rs.next()) {
    theSuivi = new Suivi(-1);
    theSuivi.description = rs.getString("description");
    theSuivi.dateSuivi = rs.getDate("dateSuivi");

  }
}
        catch (SQLException s) {
          s.printStackTrace();
        }
return theSuivi;
  }  

  public float getImputationGlobal(String nomBase,Connexion myCnx, Statement st , int anneeRef, Date dateExtraction, String nomServiceImputations){
           //int semaine = Utils.getWeek(nomBase,myCnx, st,dateExtraction.getDate(),dateExtraction.getMonth()+1,dateExtraction.getYear()+1900);
           int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
           ResultSet rs;
           req="SELECT     SUM(PlanDeCharge.Charge) AS total";
           req+="    FROM         Roadmap INNER JOIN";
           req+="            PlanDeCharge ON Roadmap.idRoadmap = PlanDeCharge.idRoadmap INNER JOIN";
           req+="            Membre ON PlanDeCharge.projetMembre = Membre.idMembre INNER JOIN";
           req+="            Service ON Membre.serviceMembre = Service.idService";
           req+=" WHERE     (PlanDeCharge.anneeRef = "+anneeRef+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND (Membre.etp > 0) AND ";
           req+="            (PlanDeCharge.semaine <= "+semaine+") ";
           req+=" GROUP BY Service.nomServiceImputations, Roadmap.idPO";
           req+=" HAVING      (Service.nomServiceImputations = '"+nomServiceImputations+"') AND (Roadmap.idPO = "+this.idPO+")";





           rs = myCnx.ExecReq(st, nomBase, req);
           float total=0;
           try {
               while (rs.next()) {
                            total = rs.getFloat(1);
                        }
                 }
                                  catch (SQLException s) {s.getMessage();}
           return total;
}
  public float getRAFGlobal(String nomBase,Connexion myCnx, Statement st , int anneeRef, Date dateExtraction, String nomServiceImputations){
           //int semaine = Utils.getWeek(nomBase,myCnx, st,dateExtraction.getDate(),dateExtraction.getMonth()+1,dateExtraction.getYear()+1900);
      if (this.id == -1) return 0;
           int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
           
           ResultSet rs;
           req="SELECT     SUM(PlanDeCharge.Charge) AS total";
           req+="    FROM         Roadmap INNER JOIN";
           req+="            PlanDeCharge ON Roadmap.idRoadmap = PlanDeCharge.idRoadmap INNER JOIN";
           req+="            Membre ON PlanDeCharge.projetMembre = Membre.idMembre INNER JOIN";
           req+="            Service ON Membre.serviceMembre = Service.idService";
           req+=" WHERE     (PlanDeCharge.anneeRef = "+anneeRef+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)AND (Membre.etp > 0) AND ";
           req+="            (PlanDeCharge.semaine > "+semaine+") ";
           req+=" GROUP BY Service.nomServiceImputations, Roadmap.idPO";
           req+=" HAVING      (Service.nomServiceImputations = '"+nomServiceImputations+"') AND (Roadmap.idPO = "+this.idPO+")";





           rs = myCnx.ExecReq(st, nomBase, req);
           float total=0;
           try {
               while (rs.next()) {
                            total = rs.getFloat(1);
                        }
                 }
                                  catch (SQLException s) {s.getMessage();}
           return total;
} 
  
  public float getRAFByCollaborateur(String nomBase,Connexion myCnx, Statement st , int anneeRef, int idMembre){
      if (this.id == -1) return 0;
           int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
           ResultSet rs;
           req="SELECT     SUM(PlanDeCharge.Charge) AS total";
           req+="    FROM         Roadmap INNER JOIN";
           req+="            PlanDeCharge ON Roadmap.idRoadmap = PlanDeCharge.idRoadmap INNER JOIN";
           req+="            Membre ON PlanDeCharge.projetMembre = Membre.idMembre INNER JOIN";
           req+="            Service ON Membre.serviceMembre = Service.idService";
           req+=" WHERE     ((PlanDeCharge.anneeRef = "+anneeRef+")   AND (PlanDeCharge.semaine > "+semaine+")";
           req+=" OR     (PlanDeCharge.anneeRef > "+anneeRef+") )";          
           req+=" AND (PlanDeCharge.projetMembre = "+idMembre+") AND (PlanDeCharge.idRoadmap = "+this.id+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)AND (Membre.etp > 0)";
           req+=" GROUP BY Service.nomServiceImputations, Roadmap.idPO";


           rs = myCnx.ExecReq(st, nomBase, req);
           float total=0;
           try {
               while (rs.next()) {
                            total = rs.getFloat(1);
                        }
                 }
                                  catch (SQLException s) {s.getMessage();}
           return total;
}
  
  public float getRafCollaborateurGlobal(String nomBase,Connexion myCnx, Statement st , int anneeRef, int idMembre){
      if (this.id == -1) return 0;
           int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
           ResultSet rs;
           req="SELECT     SUM(PlanDeCharge.Charge) AS total";
           req+="    FROM         Roadmap INNER JOIN";
           req+="            PlanDeCharge ON Roadmap.idRoadmap = PlanDeCharge.idRoadmap INNER JOIN";
           req+="            Membre ON PlanDeCharge.projetMembre = Membre.idMembre INNER JOIN";
           req+="            Service ON Membre.serviceMembre = Service.idService";
           req+=" WHERE     ((PlanDeCharge.anneeRef = "+anneeRef+")   AND (PlanDeCharge.semaine > "+semaine+")";
           req+=" OR     (PlanDeCharge.anneeRef > "+anneeRef+") )";          
           req+=" AND (PlanDeCharge.projetMembre = "+idMembre+") AND (PlanDeCharge.idRoadmap IN (SELECT idRoadmap from Roadmap where idRoadmapPere = "+this.id +")) AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)AND (Membre.etp > 0)";
           req+=" GROUP BY Service.nomServiceImputations, Roadmap.idPO";


           rs = myCnx.ExecReq(st, nomBase, req);
           float total=0;
           try {
               while (rs.next()) {
                            total = rs.getFloat(1);
                        }
                 }
                                  catch (SQLException s) {s.getMessage();}
           return total;
}  
  
  public float getConsommeGlobal(String nomBase,Connexion myCnx, Statement st , int anneeRef){
      if (this.id == -1) return 0;
           int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
           ResultSet rs;
           
                req="SELECT     SUM(PlanDeCharge.Charge) AS totalCharge";
                req+=" FROM         PlanDeCharge INNER JOIN";
                req+="                       Membre ON PlanDeCharge.projetMembre = Membre.idMembre";
                req+=" WHERE     ((PlanDeCharge.idRoadmap IN (SELECT idRoadmap from Roadmap where idRoadmapPere = "+this.id +")) AND (PlanDeCharge.semaine <= "+semaine+") AND (PlanDeCharge.anneeRef = "+anneeRef+") OR";
                req+="                       (PlanDeCharge.idRoadmap IN (SELECT idRoadmap from Roadmap where idRoadmapPere = "+this.id +")) AND (PlanDeCharge.anneeRef < "+anneeRef+"))";    
                req+=" GROUP BY Membre.etp";
                req+=" HAVING      (Membre.etp > 0)";           


           rs = myCnx.ExecReq(st, nomBase, req);
           float total=0;
           try {
               while (rs.next()) {
                            total = rs.getFloat(1);
                        }
                 }
                                  catch (SQLException s) {s.getMessage();}
           return total;
}  
  
  public float getRAF(String nomBase,Connexion myCnx, Statement st , int anneeRef){
      if (this.id == -1) return 0;
           int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
           ResultSet rs;
           req="SELECT     SUM(PlanDeCharge.Charge) AS total";
           req+="    FROM         Roadmap INNER JOIN";
           req+="            PlanDeCharge ON Roadmap.idRoadmap = PlanDeCharge.idRoadmap INNER JOIN";
           req+="            Membre ON PlanDeCharge.projetMembre = Membre.idMembre INNER JOIN";
           req+="            Service ON Membre.serviceMembre = Service.idService";
           req+=" WHERE     ((PlanDeCharge.anneeRef = "+anneeRef+")   AND (PlanDeCharge.semaine > "+semaine+")";
           req+=" OR     (PlanDeCharge.anneeRef > "+anneeRef+") )";          
           req+="  AND (PlanDeCharge.idRoadmap = "+this.id+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)AND (Membre.etp > 0)";

           rs = myCnx.ExecReq(st, nomBase, req);
           float total=0;
           try {
               while (rs.next()) {
                            total = rs.getFloat(1);
                        }
                 }
                                  catch (SQLException s) {s.getMessage();}
           return total;
}   

public float getRAFGlobalByCc(String nomBase,Connexion myCnx, Statement st , int anneeRef, Date dateExtraction, String nomCentreCout){
  int semaine = Utils.getWeek(nomBase,myCnx, st,dateExtraction.getDate(),dateExtraction.getMonth()+1,dateExtraction.getYear()+1900);
           ResultSet rs;
           req="SELECT     SUM(PlanDeCharge.Charge) AS total";
           req+="    FROM         Roadmap INNER JOIN";
           req+="            PlanDeCharge ON Roadmap.idRoadmap = PlanDeCharge.idRoadmap INNER JOIN";
           req+="            Membre ON PlanDeCharge.projetMembre = Membre.idMembre INNER JOIN";
           req+="            Service ON Membre.serviceMembre = Service.idService";
           req+=" WHERE     (PlanDeCharge.anneeRef = "+anneeRef+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND (Membre.etp > 0) AND ";
           req+="            (PlanDeCharge.semaine > "+semaine+") ";
           req+=" GROUP BY Service.nomServiceImputations, Roadmap.idPO";
           req+=" HAVING      (Service.nomServiceImputations Like '%"+nomCentreCout+"%') AND (Roadmap.idPO = "+this.idPO+")";

           rs = myCnx.ExecReq(st, nomBase, req);
           float total=0;
           try {
                while (rs.next()) {
                    total = rs.getFloat(1);
                }
            }
               catch (SQLException s) {s.getMessage();}
 return total;
}
public float getImputationGlobalByCc(String nomBase,Connexion myCnx, Statement st , int anneeRef, Date dateExtraction, String nomCentreCout){
  int semaine = Utils.getWeek(nomBase,myCnx, st,dateExtraction.getDate(),dateExtraction.getMonth()+1,dateExtraction.getYear()+1900);
           ResultSet rs;
           req="SELECT     SUM(PlanDeCharge.Charge) AS total";
           req+="    FROM         Roadmap INNER JOIN";
           req+="            PlanDeCharge ON Roadmap.idRoadmap = PlanDeCharge.idRoadmap INNER JOIN";
           req+="            Membre ON PlanDeCharge.projetMembre = Membre.idMembre INNER JOIN";
           req+="            Service ON Membre.serviceMembre = Service.idService";
           req+=" WHERE     (PlanDeCharge.anneeRef = "+anneeRef+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND (Membre.etp > 0) AND ";
           req+="            (PlanDeCharge.semaine <= "+semaine+")";
           req+=" GROUP BY Service.nomServiceImputations, Roadmap.idPO";
           req+=" HAVING      (Service.nomServiceImputations Like '%"+nomCentreCout+"%') AND (Roadmap.idPO = "+this.idPO+")";

           rs = myCnx.ExecReq(st, nomBase, req);
           float total=0;
           try {
                while (rs.next()) {
                    total = rs.getFloat(1);
                }
            }
               catch (SQLException s) {s.getMessage();}
 return total;
}

         public void getLignesPOByYearByServiceByIdPo(String nomBase,Connexion myCnx, Statement st, int idService){
           ResultSet rs;
           String Annee = Utils.getYear(myCnx.nomBase, myCnx,  st);
           req = "SELECT   DISTINCT  PlanOperationnelClient.idWebPo, PlanOperationnelClient.nomProjet, PlanOperationnelClient.charge, PlanOperationnelClient.Annee, PlanOperationnelClient.Service, PlanOperationnelClient.etat, PlanOperationnelClient.depensePrevue";
           req+=" FROM         PlanOperationnelClient INNER JOIN";
           req+="                   Service ON PlanOperationnelClient.Service = Service.nomServiceImputations";
           req+=" WHERE     (PlanOperationnelClient.Annee >= "+Annee+") AND (Service.idService = "+idService+") AND (PlanOperationnelClient.etat <> 'Abandonn�')";
           req+=" AND  (PlanOperationnelClient.idWebPo = "+this.idPO+")";
           req+=" ORDER BY PlanOperationnelClient.Annee ASC";

           rs = myCnx.ExecReq(st, myCnx.nomBase, req);
           try {
             while (rs.next()) {
               String idWebPo= rs.getString("idWebPo"); // nom du ST
               String Enveloppe= ""; // nom du ST
               String nomProjet= rs.getString("nomProjet"); // nom du ST
               String descProjet= ""; // nom du ST
               String risqueProjet= ""; // etat du ST
               float charge= rs.getFloat("charge"); // nom du ST
               String AnneePO= rs.getString("Annee"); // nom du ST
               String Service= rs.getString("Service"); // nom du ST
               String gainProjet= "";
               String Priorite= "P1";
               totalCharge+=charge;

               LignePO theLignePO = new LignePO(idWebPo,Enveloppe, nomProjet, descProjet, risqueProjet,charge,  gainProjet,  Priorite );
               theLignePO.chargeDisponible=charge;
               theLignePO.Annee = AnneePO;
               theLignePO.nomServicePO=Service;
               theLignePO.etat = rs.getString("etat");
               theLignePO.ChargePrevueForfait= rs.getFloat("depensePrevue"); // nom du ST
               this.ListeLignePO.addElement(theLignePO);
             }
           }
                   catch (SQLException s) {s.getMessage();}

  }
public float getRAFByService(String nomBase,Connexion myCnx, Statement st , int anneeRef, int idService){
         int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
         ResultSet rs;
                
           req="SELECT     SUM(PlanDeCharge.Charge) AS total";
           req+="    FROM         Roadmap INNER JOIN";
           req+="            PlanDeCharge ON Roadmap.idRoadmap = PlanDeCharge.idRoadmap INNER JOIN";
           req+="            Membre ON PlanDeCharge.projetMembre = Membre.idMembre INNER JOIN";
           req+="            Service ON Membre.serviceMembre = Service.idService";
           req+=" WHERE     ((PlanDeCharge.anneeRef = "+anneeRef+")   AND (PlanDeCharge.semaine > "+semaine+")";
           req+=" OR     (PlanDeCharge.anneeRef > "+anneeRef+") )";          
           req+="  AND (PlanDeCharge.idRoadmap = "+this.id+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)AND (Membre.etp > 0)";  
           req+=" AND      Membre.serviceMembre = "+idService;           

         rs = myCnx.ExecReq(st, nomBase, req);
         float total=0;
         try {
             while (rs.next()) {
                          total = rs.getFloat(1);
                      }
               }
                                catch (SQLException s) {s.getMessage();}
         return total;
}

public float getRAFGlobal(String nomBase,Connexion myCnx, Statement st , int anneeRef){
    if (this.id == -1) return 0;
         int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
         ResultSet rs;
                req="SELECT     SUM(PlanDeCharge.Charge) AS totalCharge";
                req+=" FROM         Membre INNER JOIN";
                req+="                       Service ON Membre.serviceMembre = Service.idService INNER JOIN";
                req+="                       PlanDeCharge ON Membre.idMembre = PlanDeCharge.projetMembre";
                req+=" WHERE     ((PlanDeCharge.idRoadmap IN (SELECT idRoadmap from Roadmap where idRoadmapPere = "+this.id +")) AND (PlanDeCharge.semaine > "+semaine+") AND (PlanDeCharge.anneeRef = "+anneeRef+") OR";
                req+="                       (PlanDeCharge.idRoadmap IN (SELECT idRoadmap from Roadmap where idRoadmapPere = "+this.id +")) AND (PlanDeCharge.anneeRef > "+anneeRef+"))";
                req+=" GROUP BY Membre.etp";                
                req+=" HAVING      (Membre.etp > 0)";                

         rs = myCnx.ExecReq(st, nomBase, req);
         float total=0;
         try {
             while (rs.next()) {
                          total = rs.getFloat(1);
                      }
               }
                                catch (SQLException s) {s.getMessage();}
         return total;
}

public float getRAFProjetGlobal(String nomBase,Connexion myCnx, Statement st , int anneeRef){
         int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
         ResultSet rs;
                req="SELECT     SUM(PlanDeCharge.Charge) AS totalCharge";
                req+=" FROM         Membre INNER JOIN";
                req+="                       Service ON Membre.serviceMembre = Service.idService INNER JOIN";
                req+="                       PlanDeCharge ON Membre.idMembre = PlanDeCharge.projetMembre";
                req+=" WHERE     ((PlanDeCharge.idRoadmap = "+this.id+") AND (PlanDeCharge.semaine > "+semaine+") AND (PlanDeCharge.anneeRef = "+anneeRef+") OR";
                req+="                       (PlanDeCharge.idRoadmap = "+this.id+") AND (PlanDeCharge.anneeRef > "+anneeRef+"))";
                req+=" GROUP BY Membre.etp";                
                req+=" HAVING      (Membre.etp > 0)";                

         rs = myCnx.ExecReq(st, nomBase, req);
         float total=0;
         try {
             while (rs.next()) {
                          total = rs.getFloat(1);
                      }
               }
                                catch (SQLException s) {s.getMessage();}
         return total;
}

public float getConsommeByService(String nomBase,Connexion myCnx, Statement st , int anneeRef,  int idService){
                //int semaine = Utils.getWeek(dateExtraction.getDate(),dateExtraction.getMonth()+1,dateExtraction.getYear()+1900);
                int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
                ResultSet rs;
                
                req="SELECT     SUM(PlanDeCharge.Charge) AS totalCharge";
                req+=" FROM         PlanDeCharge INNER JOIN";
                req+="                       Membre ON PlanDeCharge.projetMembre = Membre.idMembre";
                req+=" WHERE     ((PlanDeCharge.idRoadmap = "+this.id+") AND (PlanDeCharge.semaine <= "+semaine+") AND (PlanDeCharge.anneeRef = "+anneeRef+") OR";
                req+="                       (PlanDeCharge.idRoadmap = "+this.id+") AND (PlanDeCharge.anneeRef < "+anneeRef+"))";    
                req+=" AND      (Membre.etp > 0)"; 
                req+=" AND      Membre.serviceMembre = "+idService;

                rs = myCnx.ExecReq(st, nomBase, req);
                float total=0;
                try {
                    while (rs.next()) {
                                 total = rs.getFloat(1);
                             }
                      }
                                       catch (SQLException s) {s.getMessage();}
                return total;
}

public float getConsommeProjetGlobal(String nomBase,Connexion myCnx, Statement st , int anneeRef, Date dateExtraction){
                //int semaine = Utils.getWeek(dateExtraction.getDate(),dateExtraction.getMonth()+1,dateExtraction.getYear()+1900);
                int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
                ResultSet rs;
                req="SELECT     SUM(PlanDeCharge.Charge) AS totalCharge";
                req+=" FROM         PlanDeCharge INNER JOIN";
                req+="                       Membre ON PlanDeCharge.projetMembre = Membre.idMembre";
                req+=" WHERE     ((PlanDeCharge.idRoadmap = "+this.id+") AND (PlanDeCharge.semaine <= "+semaine+") AND (PlanDeCharge.anneeRef = "+anneeRef+") OR";
                req+="                       (PlanDeCharge.idRoadmap = "+this.id+") AND (PlanDeCharge.anneeRef < "+anneeRef+"))";    
                req+=" GROUP BY Membre.etp";
                req+=" HAVING      (Membre.etp > 0)";

                rs = myCnx.ExecReq(st, nomBase, req);
                float total=0;
                try {
                    while (rs.next()) {
                                 total = rs.getFloat(1);
                             }
                      }
                                       catch (SQLException s) {s.getMessage();}
                return total;
}

public float getConsommeCollaborateur(String nomBase,Connexion myCnx, Statement st , int anneeRef, int idCollaborateur){
    if (this.id == -1) return 0;
                //int semaine = Utils.getWeek(dateExtraction.getDate(),dateExtraction.getMonth()+1,dateExtraction.getYear()+1900);
                int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
                ResultSet rs;
                req="SELECT     SUM(PlanDeCharge.Charge) AS totalCharge";
                req+=" FROM         PlanDeCharge INNER JOIN";
                req+="                       Membre ON PlanDeCharge.projetMembre = Membre.idMembre";
                req+=" WHERE     ((PlanDeCharge.idRoadmap = "+this.id+") AND (PlanDeCharge.semaine <= "+semaine+") AND (PlanDeCharge.anneeRef = "+anneeRef+") OR";
                req+="                       (PlanDeCharge.idRoadmap = "+this.id+") AND (PlanDeCharge.anneeRef < "+anneeRef+"))  AND (PlanDeCharge.projetMembre = "+idCollaborateur+")";    
                req+=" GROUP BY Membre.etp";
                req+=" HAVING      (Membre.etp > 0)";

                rs = myCnx.ExecReq(st, nomBase, req);
                float total=0;
                try {
                    while (rs.next()) {
                                 total = rs.getFloat(1);
                             }
                      }
                                       catch (SQLException s) {s.getMessage();}
                return total;
}
public float getConsommeCollaborateurGlobal(String nomBase,Connexion myCnx, Statement st , int anneeRef, int idCollaborateur){
                //int semaine = Utils.getWeek(dateExtraction.getDate(),dateExtraction.getMonth()+1,dateExtraction.getYear()+1900);
    if (this.id == -1) return 0;
                int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
                ResultSet rs;
                req="SELECT     SUM(PlanDeCharge.Charge) AS totalCharge";
                req+=" FROM         PlanDeCharge INNER JOIN";
                req+="                       Membre ON PlanDeCharge.projetMembre = Membre.idMembre";
                req+=" WHERE     ((PlanDeCharge.idRoadmap IN (SELECT idRoadmap from Roadmap where idRoadmapPere = "+this.id +")" +") AND (PlanDeCharge.semaine <= "+semaine+") AND (PlanDeCharge.anneeRef = "+anneeRef+") OR";
                req+="                       (PlanDeCharge.idRoadmap IN (SELECT idRoadmap from Roadmap where idRoadmapPere = "+this.id +")" +") AND (PlanDeCharge.anneeRef < "+anneeRef+"))  AND (PlanDeCharge.projetMembre = "+idCollaborateur+")";    
                req+=" GROUP BY Membre.etp";
                req+=" HAVING      (Membre.etp > 0)";

                rs = myCnx.ExecReq(st, nomBase, req);
                float total=0;
                try {
                    while (rs.next()) {
                                 total = rs.getFloat(1);
                             }
                      }
                                       catch (SQLException s) {s.getMessage();}
                return total;
}

public float getConsomme(String nomBase,Connexion myCnx, Statement st , int anneeRef){
                //int semaine = Utils.getWeek(dateExtraction.getDate(),dateExtraction.getMonth()+1,dateExtraction.getYear()+1900);
    if (this.id == -1) return 0;
                int semaine = Utils.getCurrentWeek(nomBase,myCnx, st);
                ResultSet rs;
                
                req="SELECT     SUM(PlanDeCharge.Charge) AS totalCharge";
                req+=" FROM         PlanDeCharge INNER JOIN";
                req+="                       Membre ON PlanDeCharge.projetMembre = Membre.idMembre";
                req+=" WHERE     ((PlanDeCharge.idRoadmap = "+this.id+") AND (PlanDeCharge.semaine <= "+semaine+") AND (PlanDeCharge.anneeRef = "+anneeRef+") OR";
                req+="                       (PlanDeCharge.idRoadmap = "+this.id+") AND (PlanDeCharge.anneeRef < "+anneeRef+"))";    
                req+=" AND      (Membre.etp > 0)";

                rs = myCnx.ExecReq(st, nomBase, req);
                float total=0;
                try {
                    while (rs.next()) {
                                 total = rs.getFloat(1);
                             }
                      }
                                       catch (SQLException s) {s.getMessage();}
                return total;
}

public float getConsommeProjetByWeekByCollaborateur(String nomBase,Connexion myCnx, Statement st , int semaine, int annee, int idMembre){

   ResultSet rs;
   req="SELECT  SUM(Consomme.Charge) AS totalConsomme";
   req+="    FROM         Consomme INNER JOIN";
   req+="                    actionSuivi ON Consomme.idAction = actionSuivi.id INNER JOIN";
   req+="                    Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap";
   req+=" WHERE     (Roadmap.idRoadmap = "+this.id+") AND (Consomme.semaine = "+semaine+") AND (Consomme.anneeRef = "+annee+") AND (Consomme.projetMembre = "+idMembre+")";
   rs = myCnx.ExecReq(st, nomBase, req);
   float total=0;
   try {
   while (rs.next()) {
   total = rs.getFloat("totalConsomme");
   }
   }
   catch (SQLException s) {s.getMessage();}
   return total;
}

  public  void getListeBesoinNiv1(String nomBase,Connexion myCnx, Statement st){

    ResultSet rs;

    req= "SELECT     niv1, description";
    req+="     FROM         BesoinsUtilisateur";
    req+=" WHERE     (idRoadmap = "+this.id+") AND (nbNiveau = 1)";
    req+=" ORDER BY niv1";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        Besoin theBesoin = new Besoin(-1);
        theBesoin.niv1 = rs.getInt("niv1");
        theBesoin.description = rs.getString("description");
        if (theBesoin.description == null) theBesoin.description="";

        this.ListeBesoins.addElement(theBesoin);

      }
    }
    catch (Exception s) {
      s.getMessage();

    }

  }

  public  void getListeBesoinNiv2(String nomBase,Connexion myCnx, Statement st, int niv1){

    ResultSet rs;

    req= "SELECT     niv2, description";
    req+="     FROM         BesoinsUtilisateur";
    req+=" WHERE     (idRoadmap = "+this.id+") AND (nbNiveau = 2) AND (niv1 = "+niv1+")";
    req+=" ORDER BY niv2";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        Besoin theBesoin = new Besoin(-1);
        theBesoin.niv2 = rs.getInt("niv2");
        theBesoin.description = rs.getString("description");
        if (theBesoin.description == null) theBesoin.description="";

        this.ListeBesoins.addElement(theBesoin);

      }
    }
    catch (Exception s) {
      s.getMessage();

    }

  }

  public  void getListeBesoinNiv3(String nomBase,Connexion myCnx, Statement st, int niv1, int niv2){

    ResultSet rs;

    req= "SELECT     niv3, description";
    req+="     FROM         BesoinsUtilisateur";
    req+=" WHERE     (idRoadmap = "+this.id+") AND (nbNiveau = 3) AND (niv1 = "+niv1+") AND (niv2 = "+niv2+")";
  req+=" ORDER BY niv3";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        Besoin theBesoin = new Besoin(-1);
        theBesoin.niv3 = rs.getInt("niv3");
        theBesoin.description = rs.getString("description");
        if (theBesoin.description == null) theBesoin.description="";

        this.ListeBesoins.addElement(theBesoin);

      }
    }
    catch (Exception s) {
      s.getMessage();

    }

  }

  public  void getListeBesoinNiv4(String nomBase,Connexion myCnx, Statement st, int niv1, int niv2, int niv3){

    ResultSet rs;

    req= "SELECT     niv4, description";
    req+="     FROM         BesoinsUtilisateur";
    req+=" WHERE     (idRoadmap = "+this.id+") AND (nbNiveau = 4) AND (niv1 = "+niv1+") AND (niv2 = "+niv2+") AND (niv3 = "+niv3+")";
  req+=" ORDER BY niv4";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        Besoin theBesoin = new Besoin(-1);
        theBesoin.niv4 = rs.getInt("niv4");
        theBesoin.description = rs.getString("description");
        if (theBesoin.description == null) theBesoin.description="";

        this.ListeBesoins.addElement(theBesoin);

      }
    }
    catch (Exception s) {
      s.getMessage();

    }

  }
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    String oldversion ="";
    String version="";
    String description="";
    String Etat="";
    String Panier="";
    String idPO="";
    String Charge="";
    String Ordre="";
    String dateT0="";
    String dateEB="";
    String dateTest="";
    String dateMep="";
    String dateMes="";

    String dateT0_init="";
    String dateEB_init="";
    String dateTest_init="";
    String dateMep_init="";
    String dateMes_init="";

    String SuiviMOA="";
    String SuiviMOE="";

    String Tendance ="";

    String docEB="";
    String docDevis="";
    String docExigences="";

    int Annee;
    int idEquipe=-1;

    ResultSet rs;
    //String req = "SELECT version, description, dateEB, dateMep, idVersionSt, Suivi, Tendance, dateMes, Etat, Panier, idPO, Charge, Ordre, dateT0, dateTest, docEB, docDevis, Annee, MotsClef, dateT0_init, dateEB_init, dateTest_init, dateMep_init, dateMes_init, suiviMOE , dateT0MOE, dateSpecMOE, dateDevMOE, dateTestMOE, dateLivrMOE, dateT0MOE_init, dateSpecMOE_init, dateDevMOE_init, dateTestMOE_init,  dateLivrMOE_init FROM         Roadmap WHERE     (idRoadmap = "+this.id+") ORDER BY Ordre";
    String req = "SELECT     Roadmap.version, Roadmap.description, Roadmap.dateEB, Roadmap.dateMep, Roadmap.idVersionSt, Roadmap.Tendance, ";
           req+="           Roadmap.dateMes, Roadmap.Etat, Roadmap.Panier, Roadmap.idPO, Roadmap.Charge, Roadmap.Ordre, Roadmap.dateT0, Roadmap.dateTest, ";
           req+="           Roadmap.docEB, Roadmap.docDevis, Roadmap.Annee, Roadmap.MotsClef, Roadmap.dateT0_init, Roadmap.dateEB_init, Roadmap.dateTest_init, ";
           req+="           Roadmap.dateMep_init, Roadmap.dateMes_init, Roadmap.dateT0MOE,";
           req+="           ST_ListeSt_All_Attribute.nomSt, ST_ListeSt_All_Attribute.isRecurrent, Roadmap.EtatMOE, idEquipe, LF_Month, LF_Year, idMembre, Roadmap.standby, Roadmap.idBrief, Roadmap.isDevis, Roadmap.docExigences, Roadmap.dumpHtml, Roadmap.chargeConsommeForfait, Roadmap.chargeConsommeInterne, Roadmap.idProfilSpecification, Roadmap.idProfilDeveloppement, Roadmap.idProfilTest, Roadmap.typologie, Roadmap.idRoadmapPere, Roadmap.idJalon";
           req+=" FROM         Roadmap INNER JOIN";
           req+="           ST_ListeSt_All_Attribute ON Roadmap.idVersionSt = ST_ListeSt_All_Attribute.idVersionSt";
           req+=" WHERE     (Roadmap.idRoadmap = "+this.id+")";
           req+=" AND     (Roadmap.LF_Month = "+this.LF_Month+")";
           req+=" AND     (Roadmap.LF_Year  = "+this.LF_Year +")";
           req+="ORDER BY Roadmap.Ordre";

    rs = myCnx.ExecReq(st, nomBase, req);
    int nbRoadmap = 0;
    java.util.Date theDate;
    java.util.Date DdateEB;
    java.util.Date DdateMep;
    java.util.Date DdateMes;
    java.util.Date DdateT0;
    java.util.Date DdateTest;

    java.util.Date DdateEB_init;
    java.util.Date DdateMep_init;
    java.util.Date DdateMes_init;
    java.util.Date DdateT0_init;
    java.util.Date DdateTest_init;

    try {
      while (rs.next()) {

        version = rs.getString(1);
        oldversion = version;
        description = rs.getString(2);

        DdateEB = rs.getDate(3);       
        dateEB=Utils.getDateFrench(DdateEB);

        DdateMep = rs.getDate(4);
        dateMep=Utils.getDateFrench(DdateMep);

        this.idVersionSt = rs.getString("idVersionSt");

        Tendance = rs.getString("Tendance");
        if (Tendance == null)  Tendance = "";


        DdateMes = rs.getDate("dateMes");
        dateMes=Utils.getDateFrench(DdateMes);

        Etat = rs.getString("Etat");

        Panier = rs.getString("Panier");
        idPO = rs.getString("idPO");
        if ( (idPO == null) || (idPO.equals("0")))
          idPO = "";

          Charge = "";
        Ordre = rs.getString("Ordre");

        DdateT0 = rs.getDate("dateT0");
        dateT0=Utils.getDateFrench(DdateT0);

        DdateTest = rs.getDate("dateTest");
        dateTest=Utils.getDateFrench(DdateTest);

        docEB=rs.getString("docEB");
        if (docEB == null) docEB="";

        docDevis=rs.getString("docDevis");
        if (docDevis == null) docDevis="";

        Annee = rs.getInt("Annee");

        String MotsClef = rs.getString("MotsClef");

        DdateT0_init = rs.getDate("dateT0_init");
        if (DdateT0_init != null)
            dateT0_init =""+DdateT0_init.getDate()+"/"+(DdateT0_init.getMonth()+1) + "/"+(DdateT0_init.getYear() + 1900);
          else
            dateT0_init = "";

          DdateEB_init = rs.getDate("dateEB_init");
          if (DdateEB_init != null)
              dateEB_init =""+DdateEB_init.getDate()+"/"+(DdateEB_init.getMonth()+1) + "/"+(DdateEB_init.getYear() + 1900);
            else
            dateEB_init = "";

          DdateTest_init = rs.getDate("dateTest_init");
          if (DdateTest_init != null)
              dateTest_init =""+DdateTest_init.getDate()+"/"+(DdateTest_init.getMonth()+1) + "/"+(DdateTest_init.getYear() + 1900);
            else
            dateTest_init = "";

          DdateMep_init = rs.getDate("dateMep_init");
          if (DdateMep_init != null)
              dateMep_init =""+DdateMep_init.getDate()+"/"+(DdateMep_init.getMonth()+1) + "/"+(DdateMep_init.getYear() + 1900);
            else
            dateMep_init = "";

          DdateMes_init = rs.getDate("dateMes_init");
          if (DdateMes_init != null)
              dateMes_init =""+DdateMes_init.getDate()+"/"+(DdateMes_init.getMonth()+1) + "/"+(DdateMes_init.getYear() + 1900);
            else
            dateMes_init = "";

          this.DdateT0MOE = rs.getDate("dateT0MOE");
          this.dateT0MOE=Utils.getDateFrench(this.DdateT0MOE);


        this.nomSt = rs.getString("nomSt");
        if ((rs.getInt("isRecurrent") != 1))
          this.isProjet = true;
        else
          this.isProjet = false;
        String EtatMOE = rs.getString("EtatMOE");

        this.oldversion = oldversion;
        this.version = version;
        this.oldversion = oldversion;
        this.description = description;
        this.EtatRoadmap = Etat;
        this.EtatRoadmapMOE = EtatMOE;
        this.Panier = Panier;
        this.idPO = idPO;
        this.Charge = Charge;
        this.Ordre = Ordre;
        this.dateEB = dateEB;
        this.dateProd = dateMep;
        this.dateMes = dateMes;
        this.Suivi = SuiviMOA;
        this.Tendance = Tendance;
        this.dateT0 = dateT0;
        this.dateTest = dateTest;
        this.docEB = docEB;
        this.docDevis = docDevis;
        this.Annee = Annee;
        this.MotsClef = MotsClef;


        this.dateT0_init = dateT0_init;
        this.dateEB_init = dateEB_init;
        this.dateTest_init = dateTest_init;
        this.dateProd_init = dateMep_init;
        this.dateMes_init = dateMes_init;

        this.DdateT0 = DdateT0;
        this.DdateEB = DdateEB;
        this.DdateTest= DdateTest;
        this.DdateProd = DdateMep;
        this.DdateMes = DdateMes;

        this.DdateT0_init = DdateT0_init;
        this.DdateEB_init = DdateEB_init;
        this.DdateTest_init= DdateTest_init;
        this.DdateProd_init = DdateMep_init;
        this.DdateMes_init = DdateMes_init;

        this.SuiviMOE = SuiviMOE;
        this.idEquipe = rs.getInt("idEquipe");

        this.LF_Month = rs.getInt("LF_Month");
        this.LF_Year = rs.getInt("LF_Year");
        this.idMembre = rs.getInt("idMembre");

        this.standby = rs.getInt("standby");
        this.idBrief = rs.getInt("idBrief");
        this.isDevis = rs.getInt("isDevis");

        this.docExigences=rs.getString("docExigences");
        if (this.docExigences == null) this.docExigences="";

        this.dumpHtml=rs.getString("dumpHtml");
        if (this.dumpHtml == null) this.dumpHtml="";
        
        this.chargeConsommeForfait=rs.getFloat("chargeConsommeForfait");
        this.chargeConsommeInterne=rs.getFloat("chargeConsommeInterne");
        
        this.idProfilSpecification = rs.getInt("idProfilSpecification");
        this.idProfilDeveloppement = rs.getInt("idProfilDeveloppement");
        this.idProfilTest = rs.getInt("idProfilTest");
        
        this.typologie = rs.getInt("typologie");
        this.idPere = rs.getInt("idRoadmapPere");
        
        this.idJalon = rs.getInt("idJalon");

      }
    }

        catch (SQLException s) {
          myCnx.trace("***********", "nbRoadmap", "" + nbRoadmap);
          s.getMessage();
        }


  }

  public Roadmap(
                   String nomSt,
                   String idVersionSt,
                   String oldversion,
                   String version,
                   String description,
                   String EtatRoadmap,
                   String Panier,
                   String idPO,
                   String Charge,
                   String Ordre,
                   String dateEB,
                   String dateProd,
                   String dateMes,
                   String Suivi,
                   String Tendance,
                   String dateT0,
                   String dateTest,
                   String docEB,
                   String docDevis,
                   int Annee,
                   String MotsClef
                   )
  {
    this.nomSt=nomSt;
    this.idVersionSt=idVersionSt;
    this.oldversion=oldversion;
    this.version=version;
    this.description  =description;
    this.EtatRoadmap=EtatRoadmap;
    this.Panier=Panier;
    this.idPO=idPO;
    this.Charge=Charge;
    this.Ordre=Ordre;

    this.dateT0=dateT0;
    this.dateEB=dateEB;
    this.dateTest=dateTest;
    this.dateProd=dateProd;
    this.dateMes=dateMes;

    this.Suivi=Suivi;
    this.Tendance=Tendance;

    this.docDevis=docDevis;
    this.docEB = docEB;

    this.Annee=Annee;
    if (MotsClef != null)
      this.MotsClef = MotsClef;
    else
      this.MotsClef ="";
  }

  public void addListePO(String idWebPo,String chargeEngagee,String Annee, String TestDevis, String MepDevis)
  {
    LignePO theLignePO = new LignePO();
    theLignePO.idWebPo=idWebPo;
    theLignePO.Annee=Annee;
    theLignePO.dateTest_devis=TestDevis;
    theLignePO.dateProd_devis=MepDevis;
    try{
      theLignePO.chargeEngagee= Float.parseFloat(chargeEngagee) ;
    }
    catch (Exception e)
    {
      theLignePO.chargeEngagee=0;
    }

    this.ListeLignePO.addElement(theLignePO);
  }

  public void addListeActions(int id,String nomAction,String nomActeur,String etat, String theDate, String theDateEnd,String reponse, String str_isPlanning, String doc, String Code, float ChargePrevue, float ChargeDevis, int Semaine, float RAF, String LoginEmetteur, int priorite)
  {
    Action theAction = new Action(id,nomAction,nomActeur,Integer.parseInt(etat),this.id,theDate,theDateEnd,reponse);
    theAction.doc=doc;
    theAction.Code=Code;
    try
    {
      theAction.isPlanning = Integer.parseInt(str_isPlanning);
    }
    catch (Exception e){
      theAction.isPlanning = 0;
    }
    theAction.ChargePrevue=ChargePrevue;
    theAction.ChargeDevis=ChargeDevis;
    theAction.Semaine=Semaine;
    theAction.RAF=RAF;
    theAction.LoginEmetteur=LoginEmetteur;
    theAction.priorite=priorite;
    
    

    this.ListeActions.addElement(theAction);
  }

  public void addDependance(String ListeIdRoadmap){
      
    for (StringTokenizer t = new StringTokenizer(ListeIdRoadmap, ";");
         t.hasMoreTokens(); ) {

      String theTocken = t.nextToken();
      dependanceRoadmap theDependance = new dependanceRoadmap();
      theDependance.roadmapOrigine = new Roadmap(Integer.parseInt(theTocken) );
      this.ListeDependancesT0.addElement(theDependance); 
    }
        //System.out.println("theDependance.codeTacheDestination"+this.Code+"/"+theDependance.codeTacheDestination);
  }
  
  public void addListeActions(String nomBase,Connexion myCnx, Statement st,int id,String nomAction,String nomActeur,String etat, String theDate, String theDateEnd,String reponse, String str_isPlanning, String doc, String Code, float ChargePrevue, float ChargeDevis, int Semaine, float RAF, String LoginEmetteur, int priorite, String Login, int idRoadmapLie, String theDate_init, String theDateEnd_init, String dependance)
  {
    Action theActionOld = new Action(id);
    theActionOld.getAllFromBd(nomBase, myCnx, st);
    Action theAction = new Action(id,nomAction,nomActeur,Integer.parseInt(etat),this.id,theDate,theDateEnd,reponse);
    theAction.old_idEtat = theActionOld.idEtat;
    theAction.doc=doc;
    theAction.Code=Code;
    theAction.LoginEmetteur = Login;
    try
    {
      theAction.isPlanning = Integer.parseInt(str_isPlanning);
    }
    catch (Exception e){
      theAction.isPlanning = 0;
    }
    theAction.ChargePrevue=ChargePrevue;
    theAction.ChargeDevis=ChargeDevis;
    theAction.Semaine=Semaine;
    theAction.RAF=RAF;
    theAction.LoginEmetteur=Login;
    theAction.priorite=priorite;
    theAction.idRoadmapLie=idRoadmapLie;

    theAction.date_start_init = theDate_init;
    theAction.date_end_init = theDateEnd_init;

    if ((dependance != null) && !(dependance.equals("")))
        theAction.addDependance(dependance);
    this.ListeActions.addElement(theAction);
    System.out.println("theAction.ListeDependance.size()"+theAction.Code+"/"+theAction.ListeDependance.size());
  }


  public void getListeActionsOuvertes(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;

    String req = "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, ";
    req+="        Roadmap.idVersionSt, Roadmap.version, dateFin, actionSuivi.reponse, actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.Code";
    req +=" , actionSuivi.Code, actionSuivi.ChargePrevue, actionSuivi.ChargeDevis, actionSuivi.Semaine,   actionSuivi.RAF,actionSuivi.priorite,actionSuivi.priorite,actionSuivi.dateCloture,actionSuivi.idRoadmapLie, actionSuivi.LoginEmetteur";
    req +=" , actionSuivi.dateAction_init, actionSuivi.dateFin_init";
    req+=" FROM         actionSuivi INNER JOIN ";
    req+="                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id INNER JOIN ";
    req+="                       Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap";
    req+=" WHERE     (Roadmap.idVersionSt = "+this.idVersionSt+") AND (Roadmap.version = '"+this.version+"') AND (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.type = '"+this.type+"')";
    this.getListeActions_Commun( nomBase, myCnx,  st,  req);

  }

  public void getListeActionsOuvertesRetard(String nomBase,Connexion myCnx, Statement st, String theLogin){
    ResultSet rs;

    req = "SELECT actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, Roadmap.idVersionSt, Roadmap.version, dateFin, actionSuivi.reponse, actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.Code";
    req +=" , actionSuivi.Code, actionSuivi.ChargePrevue, actionSuivi.ChargeDevis, actionSuivi.Semaine,   actionSuivi.RAF,actionSuivi.priorite,actionSuivi.dateCloture,actionSuivi.idRoadmapLie, actionSuivi.LoginEmetteur";
    req +=" , actionSuivi.dateAction_init, actionSuivi.dateFin_init";
    req+= " FROM         actionSuivi INNER JOIN";
    req+= "                     TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id INNER JOIN";
    req+= "                     Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+= "                     VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    //req+= "                     Service ON VersionSt.serviceMoaVersionSt = Service.idService INNER JOIN";
    req+= "                     St ON VersionSt.stVersionSt = St.idSt";
    req+= " WHERE     (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.acteur LIKE '%"+theLogin+"%') AND (Roadmap.idRoadmap = '"+this.id+"') AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)) AND (actionSuivi.dateFin IS NOT NULL)";
    req+= " ORDER BY St.nomSt";

    this.getListeActions_Commun( nomBase, myCnx,  st,  req);

  }


  public void SetMotClefs(String nomDomaine){
    if (nomDomaine == null) nomDomaine="";
    if (this.MotsClef == null) this.MotsClef="";
    if (this.MotsClef.equals("")) this.MotsClef = nomDomaine;
    else
    {
      if (this.MotsClef.toUpperCase().indexOf(nomDomaine.toUpperCase()) < 0) {
        this.MotsClef += " " + nomDomaine;
      }
    }
  }

  public String SetListeMotClefs(String nomBase,Connexion myCnx, Statement st, Statement st2){

    ResultSet rs;
    ResultSet rs2;
    String theTocken = "";
    String newClef = "";


    req = "DELETE FROM ListeMotsClefs";
    if (myCnx.ExecUpdate(st,nomBase,req,true,"EnregST") == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

    req="SELECT DISTINCT Roadmap.MotsClef,Roadmap.MotsClef";
    req+=" FROM         Roadmap INNER JOIN";
    req+="               VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="               St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     (Roadmap.MotsClef IS NOT NULL) AND (Roadmap.MotsClef <> '') AND (St.isMeeting <> 1)";

    // 1- bouclage sur les mots clefs de la roadmap
    rs = myCnx.ExecReq(st, nomBase, req);

    int totalMembre=0;
    try {
        while (rs.next())
        {
          String ListeMotClefs = rs.getString("MotsClef");
          if (ListeMotClefs != null)
          {
            ListeMotClefs = ListeMotClefs.replaceAll("'", "' ").replaceAll(",", " ");
            ListeMotClefs = ListeMotClefs.replaceAll("\\.", " ").replaceAll("\\;", " ");
            ListeMotClefs = ListeMotClefs.replaceAll("\\-", " ");
          }

    // tockenisation des mots clefs

    // 2- bouclage sur les tockens
         for (StringTokenizer t = new StringTokenizer(ListeMotClefs, " ");t.hasMoreTokens(); ) {
            theTocken = t.nextToken();
            int nb = 0;
            req = "SELECT COUNT(id) AS nb FROM   ListeMotsClefs WHERE     (nom = '"+theTocken+"')";
            rs2 = myCnx.ExecReq(st2, nomBase, req);
            try {
              rs2.next();
              nb =rs2.getInt(1);
            }
            catch (SQLException s)
            {
              s.getMessage();
            }

            req = "SELECT COUNT(*) AS nb FROM   Exclusion  WHERE     (mot = '"+theTocken+"')";
            rs2 = myCnx.ExecReq(st2, nomBase, req);
            try {
              rs2.next();
              nb+=rs2.getInt(1);
            }
            catch (SQLException s)
            {
              s.getMessage();
            }

            if (nb == 0)
            {
              req = "INSERT ListeMotsClefs (nom) VALUES ('"+theTocken+"')";
              if (myCnx.ExecUpdate(st2,nomBase,req,true,"EnregST") == -1)
              {
                myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;
              }
            }

      // si le tocken fait partie de l'exclusion on ignore
      // si le tocken n'existe pas dans la liste des mots clefs, enregistrement
    }
    // 2- Finbouclage sur les tockens

    // 1- Fin bouclage sur les mots clefs de la roadmap
        }
      }
                 catch (SQLException s) {s.getMessage();}

      return "OK" ;
    }

    public ErrorSpecific bd_insertMotClefs(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();

      ResultSet rs;
      ResultSet rs2;
      String theTocken = "";
      String newClef = "";


            if (this.MotsClef != null)
            {
              this.MotsClef = this.MotsClef.replaceAll("'", "' ").replaceAll(",", " ");
              this.MotsClef = this.MotsClef.replaceAll("\\.", " ").replaceAll("\\;", " ");
              this.MotsClef = this.MotsClef.replaceAll("\\-", " ");
            }

      // tockenisation des mots clefs

      // 2- bouclage sur les tockens
           for (StringTokenizer t = new StringTokenizer(this.MotsClef, " ");t.hasMoreTokens(); ) {
              theTocken = t.nextToken();
              int nb = 0;
              req = "SELECT COUNT(id) AS nb FROM   ListeMotsClefs WHERE     (nom = '"+theTocken+"')";
              rs2 = myCnx.ExecReq(st, nomBase, req);
              try {
                rs2.next();
                nb =rs2.getInt(1);
              }
              catch (SQLException s)
              {
                s.getMessage();
              }

              req = "SELECT COUNT(*) AS nb FROM   Exclusion  WHERE     (mot = '"+theTocken+"')";
              rs2 = myCnx.ExecReq(st, nomBase, req);
              try {
                rs2.next();
                nb+=rs2.getInt(1);
              }
              catch (SQLException s)
              {
                s.getMessage();
              }

              if (nb == 0)
              {
                req = "INSERT ListeMotsClefs (nom) VALUES ('"+theTocken+"')";
                myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_insertMotClefs",""+this.id);
                if (myError.cause == -1) return myError;
              }

        // si le tocken fait partie de l'exclusion on ignore
        // si le tocken n'existe pas dans la liste des mots clefs, enregistrement
      }
      // 2- Finbouclage sur les tockens

      // 1- Fin bouclage sur les mots clefs de la roadmap



        return myError;
    }

  public String getLoginMOA(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    req="SELECT DISTINCT Membre.LoginMembre";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                  VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                  Membre ON VersionSt.respMoaVersionSt = Membre.idMembre";
    req+=" WHERE     (Roadmap.idVersionSt = "+this.idVersionSt+")";
String LoginMOA="";

    rs = myCnx.ExecReq(st, nomBase,req);
    try { rs.next();
    LoginMOA= rs.getString(1);

} catch (SQLException s) {s.getMessage();}

    return LoginMOA;
  }

  public String getLoginMOE(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs;
  req="SELECT DISTINCT Membre.LoginMembre";
  req+=" FROM         Roadmap INNER JOIN";
  req+="                  VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
  req+="                  Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
  req+=" WHERE     (Roadmap.idVersionSt = "+this.idVersionSt+")";
String LoginMOE="";

  rs = myCnx.ExecReq(st, nomBase,req);
  try { rs.next();
  LoginMOE= rs.getString(1);

} catch (SQLException s) {s.getMessage();}

  return LoginMOE;
}

public int ExisteExigence(String nomBase,Connexion myCnx, Statement st, int niv1, int niv2, int niv3, int niv4, int idExigence){
  ResultSet rs;
  int nb=0;

  req="SELECT     COUNT(*) AS nb";
  req+="    FROM         BesoinsUtilisateur";
  req+=" WHERE     (idRoadmap = "+this.id+") AND (niv1 = "+niv1+") AND (niv2 = "+niv2+") AND (niv3 = "+niv3+") AND (niv4 = "+niv4+")";

 if (idExigence != 99999999) // nouvelle exigence
 {
   req += "    AND  id <>" + idExigence;
 }
  rs = myCnx.ExecReq(st, nomBase,req);
  try { rs.next();
           nb= rs.getInt("nb");

  } catch (SQLException s) {s.getMessage();}


  return nb;
  }

  public Besoin getLastBesoin(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    Besoin LastBesoin=null;

    req="SELECT     TOP (1) id";
    req+="    FROM         BesoinsUtilisateur";
    req+=" WHERE     (idRoadmap = "+this.id+")";
    req+=" ORDER BY id DESC";

    rs = myCnx.ExecReq(st, nomBase,req);
    try { rs.next();
             LastBesoin=  new Besoin( rs.getInt("id"));

    } catch (SQLException s) {s.getMessage();}

    if (LastBesoin != null) LastBesoin.getAllFromBd(nomBase,myCnx, st);
    return LastBesoin;
  }

  public String getInitialMOA(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    this.InitialesMOA = "";

    req="SELECT     Membre.nomMembre, Membre.prenomMembre, Roadmap.idRoadmap";
    req+="     FROM         Roadmap INNER JOIN";
    req+="                   Membre ON Roadmap.idMembre = Membre.idMembre";
    req+=" WHERE     (Roadmap.idRoadmap = "+this.id+")";

    String nom = "";
    String prenom = "";
    rs = myCnx.ExecReq(st, nomBase,req);
    try { rs.next();
             nom= rs.getString(1);
             prenom= rs.getString(2);

             if ((prenom != null) && (prenom.length() > 0))
                 this.InitialesMOA +=prenom.substring(0,1).toUpperCase();
             else
                 this.InitialesMOA +=".";
             
             if ((nom != null) && (nom.length() > 0))
                 this.InitialesMOA +=nom.substring(0,1).toUpperCase();
             else
                 this.InitialesMOA +=".";             

    } catch (SQLException s) {s.getMessage();}


    if (nom.equals(""))
    {
      req = "SELECT     Membre.nomMembre, Membre.prenomMembre";
      req += " FROM         Roadmap INNER JOIN";
      req += "                      VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
      req +=
          "                 Membre ON VersionSt.respMoaVersionSt = Membre.idMembre";
      req += " WHERE     (Roadmap.idVersionSt = " + this.idVersionSt + ")";

      rs = myCnx.ExecReq(st, nomBase, req);
      try {
        rs.next();
        nom = rs.getString(1);
        prenom = rs.getString(2);
        this.InitialesMOA = "";
             if ((prenom != null) && (prenom.length() > 0))
                 this.InitialesMOA +=prenom.substring(0,1).toUpperCase();
             else
                 this.InitialesMOA +=".";
             
             if ((nom != null) && (nom.length() > 0))
                 this.InitialesMOA +=nom.substring(0,1).toUpperCase();
             else
                 this.InitialesMOA +=".";    

      }
      catch (SQLException s) {
        s.getMessage();
      }
    }
    return this.InitialesMOA;
  }

  public void getListeVignettes(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;
    Vignette theVignette = null;
    String req="SELECT     id, num, X, Y, nom, description, Login";
           req+=" FROM         Vignettes";
           req+=" WHERE     (idRoadmap = '"+this.id+"')";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      try {
         while (rs.next()) {
           int id=rs.getInt("id");
           int num=rs.getInt("num");
           int X=rs.getInt("X");
           int Y=rs.getInt("Y");
           String nom=rs.getString("nom");
           String description=rs.getString("description");
           String Login=rs.getString("Login");

           theVignette = new Vignette (id);
           theVignette.num = num;
           theVignette.X = X;
           theVignette.Y = Y;
           theVignette.nom = nom.replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");;
           theVignette.description = description.replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");;
           theVignette.Login = Login;

           //theVignette.dump();

           this.ListeVignettes.addElement(theVignette);
         }
        } catch (SQLException s) {s.getMessage();}

  }

  public void getListeActionsByIdEtat(String nomBase,Connexion myCnx, Statement st, int idState){
    ResultSet rs;

      req = "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, ";
      req +="  TypeEtatAction.nom AS nomEtat, actionSuivi.dateFin, actionSuivi.reponse, actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.Code";
      req +=" , actionSuivi.Code, actionSuivi.ChargePrevue, actionSuivi.ChargeDevis, actionSuivi.Semaine,   actionSuivi.RAF,actionSuivi.priorite,actionSuivi.dateCloture,actionSuivi.idRoadmapLie, actionSuivi.LoginEmetteur";
      req +=" , actionSuivi.dateAction_init, actionSuivi.dateFin_init, actionSuivi.dateFin_init";
      req += " FROM         actionSuivi INNER JOIN ";
      req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id";
      req += " WHERE     (actionSuivi.idRoadmap = " + this.id + ") AND (type <> 'MOE')";
      req +=" AND     (idEtat = "+idState+")";
      req +=" ORDER BY dateFin";


    this.getListeActions_Commun( nomBase, myCnx,  st,  req);
    
  }

  public void bd_UpdateJalonsDoc(String nomBase,Connexion myCnx, Statement st){
     ResultSet rs;
    String req = ""; 
    
    int typeEB = -1;
    
    req = "SELECT     id, nom FROM  typeJalon WHERE (numIndicateur =2 )";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
            typeEB =  rs.getInt("id");
      }}   catch (SQLException s) {s.getMessage();}  
    
    req = "UPDATE [JalonsProjet]";
    req+= "   SET [Livrable] = "+ "'"+this.docEB+ "'";

    req+= "  WHERE idRoadmap= "+this.id;
    req+= "  AND type= "+typeEB;

    myCnx.ExecUpdate(st,nomBase,req,true);

  }
  
  public ErrorSpecific bd_UpdateActionsWithDependancesT0(String nomBase,Connexion myCnx, Statement st, String transaction, long deltaT0){
    ResultSet rs;
    ErrorSpecific myError = new ErrorSpecific();

    this.getListeActions(nomBase, myCnx, st);
 
     for (int i=0; i < this.ListeActions.size(); i++)
     {
         Action theAction = (Action)this.ListeActions.elementAt(i);
        
        String date_start =theAction.date_start;
        String date_end =theAction.date_end;
        
        try{
        long time_start = theAction.Ddate_start.getTime();
        time_start+=deltaT0;
        theAction.Ddate_start = new Date(time_start);
        date_start ="convert(datetime, '"+theAction.Ddate_start.getDate()+"/"+(theAction.Ddate_start.getMonth()+1)+"/"+(theAction.Ddate_start.getYear() +1900)+"', 103)";
        }
        catch (Exception e)
        {
          date_start="NULL";
        }     
        
        try{        
        long time_end = theAction.Ddate_end.getTime();
        time_end+=deltaT0;
        theAction.Ddate_end = new Date(time_end);
        date_end ="convert(datetime, '"+theAction.Ddate_end.getDate()+"/"+(theAction.Ddate_end.getMonth()+1)+"/"+(theAction.Ddate_end.getYear() +1900)+"', 103)";
        }
        catch (Exception e)
        {
          date_end="NULL";
        }
           
         
            req = "UPDATE actionSuivi SET ";
            req +=" dateAction ="+ date_start + ", ";
            req +=" dateFin ="+ date_end + " ";
            req += " WHERE id ="+ theAction.id;         
            myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
            if (myError.cause == -1) return myError;
     }
     return myError;
  }  
  
  public ErrorSpecific bd_UpdateRoadmapWithDependances(String nomBase,Connexion myCnx, Statement st, Statement st2, String transaction){
    ResultSet rs;
    ErrorSpecific myError = new ErrorSpecific();
 
     req = "SELECT     id, idRoadmapDestination FROM         RoadmapDependances WHERE     (nb > 0) ORDER BY nb";
     rs = myCnx.ExecReq(st, nomBase, req);
     this.ListeRoadmapWithDependances.removeAllElements();
     try {
          while (rs.next()) {
              Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmapDestination"));       
              this.ListeRoadmapWithDependances.addElement(theRoadmap);
              }
         } 
         catch (SQLException s) {s.getMessage();}  

     for (int i=0; i < this.ListeRoadmapWithDependances.size(); i++)
     {
         long T0_old = 0;
         long T0_new = 0;
         long deltaT0 = 0;
         
         Roadmap theRoadmap = (Roadmap)this.ListeRoadmapWithDependances.elementAt(i);
         theRoadmap.getAllFromBd(myCnx.nomBase, myCnx, st);
         
         if (theRoadmap.DdateT0 != null)
            T0_old = theRoadmap.DdateT0.getTime();
         
         theRoadmap.getListeDependances(myCnx.nomBase, myCnx, st); 
         theRoadmap.getListeJalons(myCnx.nomBase, myCnx, st, true);
         theRoadmap.getDates(nomBase,myCnx, st);
         
         if (theRoadmap.DdateT0 != null)
            T0_new = theRoadmap.DdateT0.getTime();
         
         deltaT0 = T0_new - T0_old;
         if (deltaT0 >0)
              {   
                //myError =theRoadmap.bd_UpdateActionsWithDependancesT0(myCnx.nomBase, myCnx, st,transaction, deltaT0);
                if (myError.cause == -1)return myError;
              }         
         
          String dateT0 ="";
          if ((theRoadmap.dateT0 != null) &&(!theRoadmap.dateT0.equals("")))
          {
            Utils.getDate(theRoadmap.dateT0);
            dateT0 ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
         }
         else
           dateT0 = "NULL";

          String dateEB ="";
          if ((theRoadmap.dateEB != null) &&(!theRoadmap.dateEB.equals("")))
          {
            Utils.getDate(theRoadmap.dateEB);
            dateEB ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
         }
         else
           dateEB = "NULL";
          
          String dateTEST ="";
          if ((theRoadmap.dateTest != null) &&(!theRoadmap.dateTest.equals("")))
          {
            Utils.getDate(theRoadmap.dateTest);
            dateTEST ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
         }
         else
           dateTEST = "NULL";          

          String datePROD ="";
          if ((theRoadmap.dateProd != null) &&(!theRoadmap.dateProd.equals("")))
          {
            Utils.getDate(theRoadmap.dateProd);
            datePROD ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
         }
         else
           datePROD = "NULL";             

          String dateMES ="";
          if ((theRoadmap.dateMes != null) &&(!theRoadmap.dateMes.equals("")))
          {
            Utils.getDate(theRoadmap.dateMes);
            dateMES ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
         }
         else
           dateMES = "NULL";                
          
            req = "UPDATE Roadmap SET ";
            req +=" dateT0 ="+ dateT0 + ", ";
            req +=" dateEB ="+ dateEB + ", ";
            req +=" dateTest ="+ dateTEST + ", ";
            req +=" dateMep ="+ datePROD + ", ";
            req +=" dateMes ="+ dateMES + " ";
            req += " WHERE idRoadmap ="+ theRoadmap.id;         
            myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateRoadmapWithDependances",""+this.id);
            if (myError.cause == -1) return myError;
            
            for (int j=0; j < theRoadmap.ListeJalons.size(); j++)
              {
                Jalon theJalon = (Jalon)theRoadmap.ListeJalons.elementAt(j);
                String dateJalon ="";
                if ((theJalon.strDate != null) &&(!theJalon.strDate.equals("")))
                {
                    Utils.getDate(theJalon.strDate);
                    dateJalon ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
                    
                    req = "UPDATE JalonsProjet SET ";
                    req +=" date ="+ dateJalon ;
                    req += " WHERE idRoadmap ="+ theRoadmap.id;   
                    req += " AND type ="+ theJalon.type;
                    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateRoadmapWithDependances",""+this.id);
                    if (myError.cause == -1) return myError;                      
                }
 
                                     
              }            
     }
     return myError;
  }  
   
  public String getListeChargesVentilationSupport(String nomBase,Connexion myCnx, Statement st, int idMembre, int annee, int semaine){
    ResultSet rs;
    String ventilationSupport="";
    this.detailVentilationToken = "";

    req = "SELECT     PlanDeChargeVentilation.idSt, St.nomSt, PlanDeChargeVentilation.Charge";
    req+= " FROM         PlanDeChargeVentilation INNER JOIN";
    req+= "                      St ON PlanDeChargeVentilation.idSt = St.idSt";
    req+= " WHERE     (PlanDeChargeVentilation.projetMembre = "+idMembre+") AND (PlanDeChargeVentilation.anneeRef = "+annee+") AND (PlanDeChargeVentilation.semaine = "+semaine+") AND (PlanDeChargeVentilation.idRoadmap = "+this.id+")";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        Charge theCharge = new Charge();
        theCharge.idObj = rs.getInt("idSt");
        theCharge.nomObj = rs.getString("nomSt");
        theCharge.hj = rs.getFloat("Charge");
        
        if (!ventilationSupport.equals("") ) ventilationSupport+=",";
        ventilationSupport+=theCharge.nomObj + "(" + theCharge.hj + ")";

        if (!ventilationSupport.equals("") ) this.detailVentilationToken+="@";
        this.detailVentilationToken+=theCharge.idObj;
        this.detailVentilationToken+=";";
        this.detailVentilationToken+=theCharge.hj;
        
        this.ListeCharges.addElement(theCharge);

      }
    }
    catch (Exception s) {
      s.getMessage();

    }
    return ventilationSupport;

  }
  
    public void getListeActions(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;

    //myCnx.trace("@99999999999______________","this.type",""+this.type);
    if (this.type.equals("MOA") || (this.type.equals("MOE")))
    {
      //String req = "SELECT     id, nom, acteur, idetat, idRoadmap, dateAction FROM   actionSuivi WHERE idRoadmap ="+this.id;
      req = "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, ";
      req +="  TypeEtatAction.nom AS nomEtat, actionSuivi.dateFin, actionSuivi.reponse, actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.Code";
      req +=" , actionSuivi.Code, actionSuivi.ChargePrevue, actionSuivi.ChargeDevis, actionSuivi.Semaine,   actionSuivi.RAF,actionSuivi.priorite,actionSuivi.dateCloture,actionSuivi.idRoadmapLie, actionSuivi.LoginEmetteur";
      req +=" , actionSuivi.dateAction_init, actionSuivi.dateFin_init";
      req += " FROM         actionSuivi INNER JOIN ";
      req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id";
      req += " WHERE     (actionSuivi.idRoadmap = " + this.id +
          ") AND (type <> 'MOE')";
    }
    else if (this.type.equals("MOE"))
    {
      //String req = "SELECT     id, nom, acteur, idetat, idRoadmap, dateAction FROM   actionSuivi WHERE idRoadmap ="+this.id;
      req = "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, ";
      req +="  TypeEtatAction.nom AS nomEtat, actionSuivi.dateFin, actionSuivi.reponse, actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.Code";
      req +=" , actionSuivi.Code, actionSuivi.ChargePrevue, actionSuivi.ChargeDevis, actionSuivi.Semaine,   actionSuivi.RAF,actionSuivi.priorite,actionSuivi.dateCloture,actionSuivi.idRoadmapLie, actionSuivi.LoginEmetteur";
      req +=" , actionSuivi.dateAction_init, actionSuivi.dateFin_init";
      req += " FROM         actionSuivi INNER JOIN ";
      req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id";
      req += " WHERE     (actionSuivi.idRoadmap = " + this.id +
          ") AND (type = 'MOE')";
    }

    this.getListeActions_Commun( nomBase, myCnx,  st,  req);

  }

  public void getListeActionsCloses(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;

      req = "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, ";
      req +="  TypeEtatAction.nom AS nomEtat, actionSuivi.dateFin, actionSuivi.reponse, actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.Code";
      req +=" , actionSuivi.Code, actionSuivi.ChargePrevue, actionSuivi.ChargeDevis, actionSuivi.Semaine,   actionSuivi.RAF,actionSuivi.priorite,actionSuivi.dateCloture,actionSuivi.idRoadmapLie, actionSuivi.LoginEmetteur";
      req += " FROM         actionSuivi INNER JOIN ";
      req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id";
      req += " WHERE     (actionSuivi.idRoadmap = " + this.id + ")";
      req += " AND (idEtat <> 3)";


    this.getListeActions_Commun( nomBase, myCnx,  st,  req);

  }

  public void getListeActionsAnneeCourante(String nomBase,Connexion myCnx, Statement st, int Annee){
  ResultSet rs;

    req = "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, ";
    req +="  TypeEtatAction.nom AS nomEtat, actionSuivi.dateFin, actionSuivi.reponse, actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.Code";
    req +=" , actionSuivi.Code, actionSuivi.ChargePrevue, actionSuivi.ChargeDevis, actionSuivi.Semaine,   actionSuivi.RAF,actionSuivi.priorite,actionSuivi.dateCloture,actionSuivi.idRoadmapLie, actionSuivi.LoginEmetteur";
    req +=" , actionSuivi.dateAction_init, actionSuivi.dateFin_init";
    req += " FROM         actionSuivi INNER JOIN ";
    req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id";
    req += " WHERE     (actionSuivi.idRoadmap = "+this.id+") AND (actionSuivi.type <> 'MOE') AND (YEAR(actionSuivi.dateFin) = "+Annee+" OR";
    req += "                 YEAR(actionSuivi.dateAction) = "+Annee+")";
    req+=" AND (actionSuivi.isPlanning = 1)";

  this.getListeActions_Commun( nomBase, myCnx,  st,  req);

}

public void getListeActionsAnneeCouranteNonCloses(String nomBase,Connexion myCnx, Statement st, int Annee, String Login){
ResultSet rs;

  req = "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, ";
  req +="                      actionSuivi.dateFin, actionSuivi.reponse, actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.Code, actionSuivi.Code AS Expr1, ";
  req +="                      actionSuivi.ChargePrevue, actionSuivi.ChargeDevis, actionSuivi.Semaine, actionSuivi.RAF, actionSuivi.priorite, actionSuivi.dateCloture, ";
  req +="                      actionSuivi.idRoadmapLie, actionSuivi.LoginEmetteur";
  req +=" , actionSuivi.dateAction_init, actionSuivi.dateFin_init";
  req +="  FROM         actionSuivi INNER JOIN";
  req +="                      TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id";
  req +="  WHERE     (actionSuivi.idRoadmap = "+this.id+") AND (actionSuivi.type <> 'MOE') AND (YEAR(actionSuivi.dateFin) = "+Annee+" OR";
  req +="                      YEAR(actionSuivi.dateAction) = "+Annee+") AND (actionSuivi.isPlanning = 1) AND (actionSuivi.acteur LIKE '%"+Login+"%') AND (actionSuivi.idEtat <> 6) AND ";
  req +="                      (actionSuivi.idEtat <> 3)";


this.getListeActions_Commun( nomBase, myCnx,  st,  req);

}

public void getListeActionsAnneeCouranteNonAffectee(String nomBase,Connexion myCnx, Statement st, int Annee){
ResultSet rs;

  req = "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, ";
  req +="  TypeEtatAction.nom AS nomEtat, actionSuivi.dateFin, actionSuivi.reponse, actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.Code";
  req +=" , actionSuivi.Code, actionSuivi.ChargePrevue, actionSuivi.ChargeDevis, actionSuivi.Semaine,   actionSuivi.RAF,actionSuivi.priorite,actionSuivi.dateCloture,actionSuivi.idRoadmapLie, actionSuivi.LoginEmetteur";
  req +=" , actionSuivi.dateAction_init, actionSuivi.dateFin_init";
  req += " FROM         actionSuivi INNER JOIN ";
  req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id";
  req += " WHERE     (actionSuivi.idRoadmap = "+this.id+") AND (actionSuivi.type <> 'MOE') AND (YEAR(actionSuivi.dateFin) = "+Annee+" OR";
  req += "                 YEAR(actionSuivi.dateAction) = "+Annee+")";
  req+=" AND (actionSuivi.isPlanning = 1)";

this.getListeActions_Commun( nomBase, myCnx,  st,  req);

}


  private void getListeActions_Commun(String nomBase,Connexion myCnx, Statement st, String req){
    ResultSet rs;

    this.ListeActions.removeAllElements();

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String nom="";
    String acteur="";
    int etat=-1;
    int  id;
    int  idRoadmap;
    Date theDate;
    String str_theDate;
    String str_theDateFin;
    String str_theDateCloture;
    String str_etat;
    try {
      while (rs.next()) {
        id = rs.getInt("id");
        nom = rs.getString("nom");
        //nom = nom.replaceAll("\\r","\\\\r");
        //nom = nom.replaceAll("\\n","\\\\n");
        //nom = nom.replaceAll("\"","\\\\\"");

        acteur = rs.getString("acteur").replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");
        etat = rs.getInt("idEtat");
        idRoadmap = rs.getInt("idRoadmap");

        Date theDate_start = rs.getDate("dateAction");
        str_etat = rs.getString("nomEtat");
        if (theDate_start != null)
          str_theDate = ""+theDate_start.getDate()+"/"+(theDate_start.getMonth()+1) + "/"+(theDate_start.getYear() + 1900);
         else
          str_theDate = "";

        Date theDate_end = rs.getDate("dateFin");
        if (theDate_end != null)
          str_theDateFin = ""+theDate_end.getDate()+"/"+(theDate_end.getMonth()+1) + "/"+(theDate_end.getYear() + 1900);
         else
          str_theDateFin = "";

        String reponse = rs.getString("reponse");
        if (reponse != null)
          reponse = reponse;
        else
          reponse = "";

        Action theAction = new Action(id,nom,acteur,etat,idRoadmap,str_theDate,str_theDateFin,reponse);
        theAction.nomEtat = str_etat;
        theAction.isPlanning = rs.getInt("isPlanning");
        theAction.doc = rs.getString("doc");
        if (theAction.doc == null) theAction.doc = "";
        theAction.Code = rs.getString("Code");
        if (theAction.Code == null) theAction.Code = "";

        theAction.ChargePrevue = rs.getFloat("ChargePrevue");
        theAction.ChargeDevis = rs.getFloat("ChargeDevis");
        theAction.Semaine = rs.getInt("Semaine");
        theAction.RAF = rs.getFloat("RAF");
        theAction.priorite=rs.getInt("priorite");

        theDate = rs.getDate("dateCloture");
        if (theDate != null)
          str_theDateCloture = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
         else
          str_theDateCloture = "";
        theAction.dateCloture=str_theDateCloture;

        theAction.idRoadmapLie = rs.getInt("idRoadmapLie");
        theAction.LoginEmetteur = rs.getString("LoginEmetteur");

        theDate = rs.getDate("dateAction_init");
        if (theDate != null)
          theAction.date_start_init = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
         else
          theAction.date_start_init = "";

        theDate = rs.getDate("dateFin_init");
        if (theDate != null)
          theAction.date_end_init = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
         else
          theAction.date_end_init = "";

        theAction.Ddate_start = theDate_start;
        theAction.Ddate_end = theDate_end;
        
        this.ListeActions.addElement(theAction);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }

  public void getListeCampagnes(String nomBase,Connexion myCnx, Statement st, Statement st2){
    String req = "SELECT     id, idRoadmap,date, nb_KO, nb_AR, nb_AF, nb_OK, nb_AN, FileName, FileNameIn, IA1, IA2, num, version";
    req+=" FROM         Campagne";
    req+=" WHERE     (idRoadmap = "+this.id+") AND (isNonReg <> 1)";

    this.getListeCampagnes_Commun( nomBase, myCnx,  st,  st2, req);

  }

  public void getListeCampagnesNonReg(String nomBase,Connexion myCnx, Statement st, Statement st2){
    String req = "SELECT Campagne_1.id, Campagne_1.idRoadmap,Campagne_1.date, Campagne_1.nb_KO, Campagne_1.nb_AR, Campagne_1.nb_AF, Campagne_1.nb_OK, Campagne_1.nb_AN, ";
    req+="                   Campagne_1.FileName, Campagne_1.FileNameIn, Campagne_1.IA1, Campagne_1.IA2, Campagne_1.num, ";
    req+="                   Campagne_1.version";
    req+=" FROM         Campagne INNER JOIN";
    req+="                   Campagne AS Campagne_1 ON Campagne.idCampagneLiee = Campagne_1.id";
    req+=" WHERE     (Campagne.idRoadmap = "+this.id+")";



    this.getListeCampagnes_Commun( nomBase, myCnx,  st,  st2, req);

  }

  public int getNbResultTest(String nomBase,Connexion myCnx, Statement st){
    int nb = 0;
    ResultSet rs=null;

    req = "SELECT     COUNT(*) AS nb";
    req+="    FROM         categorieTest INNER JOIN";
    req+="                  Tests ON categorieTest.id = Tests.idCategorie INNER JOIN";
    req+="                  Roadmap ON categorieTest.idRoadmap = Roadmap.idRoadmap";
    req+=" WHERE     (Roadmap.idRoadmap = "+this.id+")";



   rs = myCnx.ExecReq(st, nomBase, req);
         try {rs.next(); nb =rs.getInt("nb");} catch (SQLException s) {s.getMessage();}

    return nb;
  }
  
    public void getNbActions(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;

    req = "  SELECT     COUNT(*) as nb";
    req+="   FROM         actionSuivi INNER JOIN ";
    req+="                          TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id";
    req+="   WHERE     (actionSuivi.idRoadmap = "+this.id+") AND (actionSuivi.type <> 'MOE') ";
    req+="    AND (actionSuivi.isPlanning = 1)";


   rs = myCnx.ExecReq(st, nomBase, req);
         try {rs.next(); this.nbActions =rs.getInt("nb");} catch (SQLException s) {s.getMessage();}

  }
    
    public void getNbDependances(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;

    req = "  SELECT     COUNT(*) as nb";
    req+="      FROM         RoadmapDependances INNER JOIN";
    req+="                            Roadmap ON RoadmapDependances.idRoadmapOrigine = Roadmap.idRoadmap INNER JOIN";
    req+="                            VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                            St ON VersionSt.stVersionSt = St.idSt";
    req+="      WHERE     (RoadmapDependances.idRoadmapDestination = "+this.id+")";


   rs = myCnx.ExecReq(st, nomBase, req);
         try {rs.next(); this.nbDependances =rs.getInt("nb");} catch (SQLException s) {s.getMessage();}
  }    

  private void getListeCampagnes_Commun(String nomBase,Connexion myCnx, Statement st, Statement st2, String req){
  ResultSet rs;

  this.ListeCampagnes.removeAllElements();
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
    while (rs.next()) {
      Campagne campagne = new Campagne(this.id);
      campagne.id = rs.getInt("id");
      campagne.idRoadmap=rs.getInt("idRoadmap");
      Date Ddate = rs.getDate("date");
      if (Ddate != null)
        campagne.date = ""+Ddate.getDate()+"/"+(Ddate.getMonth()+1) + "/"+(Ddate.getYear() + 1900);
       else
         campagne.date = "";

       int nb_NotComputed = campagne.readResultNotComputed( nomBase, myCnx,  st2);
       //int nb_NotComputed = 0;
      campagne.nb_KO = campagne.readResultByState( nomBase, myCnx,  st2,"KO");
      campagne.nb_AR = campagne.readResultByState( nomBase, myCnx,  st2,"AR");
      campagne.nb_AF =campagne.readResultByState( nomBase, myCnx,  st2,"AF") ;
      campagne.nb_AF +=nb_NotComputed;
      campagne.nb_OK = campagne.readResultByState( nomBase, myCnx,  st2,"OK") ;

      campagne.nb_AN = campagne.readResultByState( nomBase, myCnx,  st2,"AN") ;

      campagne.FileName = rs.getString("FileName");
      if (campagne.FileName == null) campagne.FileName = "";
      campagne.FileNameIn = rs.getString("FileNameIn");
     if (campagne.FileNameIn == null) campagne.FileNameIn = "";
     campagne.IA1 = rs.getFloat("IA1");
     campagne.IA2 = rs.getFloat("IA2");
     campagne.num = rs.getInt("num");
     campagne.version= rs.getString("version");

     //campagne.nbTests = campagne.nb_KO + campagne.nb_AR + campagne.nb_AF + campagne.nb_OK + campagne.nb_AN;
     campagne.nbTests = campagne.nb_KO + campagne.nb_AR + campagne.nb_AF + campagne.nb_OK ;
     //campagne.majCompeurs();

      //campagne.dump();
      this.ListeCampagnes.addElement(campagne);
    }
  }
          catch (SQLException s) {
            s.printStackTrace();
          }

}


  public void getListeCampagnesRelecture(String nomBase,Connexion myCnx, Statement st, Statement st2){
    ResultSet rs;
    String req = "SELECT     id, idRoadmap, Login, date";
    req+="     FROM         RelectureEB";
    req+=" WHERE     (idRoadmap = "+this.id+")";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        CampagneRelecture campagne = new CampagneRelecture(rs.getInt("id"));
        campagne.idRoadmap = this.id;
        campagne.Login= rs.getString("Login");
        Date Ddate = rs.getDate("date");
        if (Ddate != null)
          campagne.date = ""+Ddate.getDate()+"/"+(Ddate.getMonth()+1) + "/"+(Ddate.getYear() + 1900);
         else
           campagne.date = "";

        //int nb_NotComputed = campagne.readResultNotComputed( nomBase, myCnx,  st2);
         //int nb_NotComputed = 0;
        campagne.nb_KO = campagne.readResultByState( nomBase, myCnx,  st2,"KO");
        campagne.nb_AR = campagne.readResultByState( nomBase, myCnx,  st2,"AR");
        campagne.nb_AF =campagne.readResultByState( nomBase, myCnx,  st2,"AF") ;
        //campagne.nb_AF +=nb_NotComputed;
        campagne.nb_OK = campagne.readResultByState( nomBase, myCnx,  st2,"OK") ;

       //campagne.nbTests = campagne.nb_KO + campagne.nb_AR + campagne.nb_AF + campagne.nb_OK + campagne.nb_AN;
       campagne.nbTests = campagne.nb_KO + campagne.nb_AR + campagne.nb_AF + campagne.nb_OK ;

       if (campagne.nbTests != 0)
       {
         //this.IA1 = 100 * (float) (this.nb_OK + this.nb_KO+ this.nb_AR+ this.nb_AN) / this.nbTests;
         campagne.IA1 = 100 * (float) (campagne.nb_OK + campagne.nb_KO) / campagne.nbTests;
         campagne.IA2 = 100 * (float) (campagne.nb_OK) / campagne.nbTests;
       }
       //campagne.majCompeurs();

        //campagne.dump();
        this.ListeCampagnes.addElement(campagne);
      }
    }
            catch (SQLException s) {
              s.printStackTrace();
            }

  }
  public static int getidVersionSt(String nomBase,Connexion myCnx, Statement st,int id){
    int idVersionSt = -1;
    String reqRoadmap = "SELECT     idVersionSt FROM         Roadmap WHERE     (idRoadmap ="+ id+")";
    ResultSet rs = myCnx.ExecReq(st, nomBase, reqRoadmap);
    try { rs.next();
      idVersionSt = rs.getInt(1);
        } catch (SQLException s) {s.getMessage();}
    return idVersionSt;
  }

 public  void excelCahierTest(String nomBase,Connexion myCnx, Statement st, Statement st2, String Client, String directory, String filename){
    
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    
    this.directory = directory;
    this.filename = filename;    

    String[] entete = {"Categorie","Libelle", "Description", "MiseEnOeuvre"};
    Excel theExport = new Excel("doc/"+Client+"/test/exportExcel",entete);

    theExport.xl_delete();
    //myCnx.trace("1---------- delete","location",""+location);

    // --------------------------------------------------------------------------------------------------//


    theExport.attach_xlsx(theExport.Basedirectory+"/" + this.filename); // nom du fichier excel horodate
    //myCnx.trace("2---------- attach","location",""+location+"/ExportCahierTest_"+this.id+".xls");

    //theExport.xl_open_update();
    theExport.xl_open_create_xlsx("CAHIER_TEST"); // feuille de calcul
    theExport.setStyleDate_xlsx();

    
    /// --------------------------- Récupération de la liste des catégories de test----------------------//
    this.getListeCategories(myCnx.nomBase,myCnx,st);
    // -------------------------------------------------------------------------------------------------//    

    theExport.sheet_xlsx.setColumnWidth(0, 256*22); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(1, 256*66); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(2, 256*79); // fixer la largeur de la 1ere colonne
    theExport.sheet_xlsx.setColumnWidth(3, 256*79); // fixer la largeur de la 1ere colonne

    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet_xlsx.createFreezePane(2,1) ;

    //myCnx.trace("3---------- theExport.sheet.createFreezePane","Devis.ListeDevis.size()",""+Devis.ListeDevis.size());
    int numRow = 0;
    Row row = theExport.sheet_xlsx.createRow(numRow);
    theExport.xl_writeEntete_xlsx(row,0,theExport.entete);
    numRow++;    
    for (int i=0; i < this.ListeCategories.size();i++)
    {
        CategorieTest theCategorieTest = (CategorieTest)this.ListeCategories.elementAt(i);
        theCategorieTest.getListeTests(myCnx.nomBase,myCnx,st, st2);

                
            
        for (int j=0; j < theCategorieTest.ListeTests.size();j++)
        {
            Test theTest = (Test)theCategorieTest.ListeTests.elementAt(j);
      
            int numCol = 0;

            row = theExport.sheet_xlsx.createRow(numRow);

              //  myCnx.trace("---------- "+numRow," :: theTest.nom",""+theTest.nom);
            //{"Code","ST", "Projet","Etat", "Cr�ateur","MOE","MOA","dateSoumission","dateGO_MOA","dateGO_GOUVERNANCE"};
               if (theCategorieTest.nom != null)
                    theExport.xl_write_xlsx(row, numCol, "" + theCategorieTest.nom.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
            if (theTest.nom != null)
               theExport.xl_write_xlsx(row, ++numCol, "" + theTest.nom.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
            if (theTest.description != null)
                theExport.xl_write_xlsx(row, ++numCol, "" + theTest.description.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));            
            if (theTest.miseEnOeuvre != null)
                theExport.xl_write_xlsx(row, ++numCol, "" + theTest.miseEnOeuvre.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

            
            numRow++;
        }
            

    }    
    
    

    //myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create_xlsx();

    //myCnx.trace("5---------- xl_close_create","xl_close_create",""+theExport.filename);
      
    }  
   
  public ErrorSpecific UpdateDescription(String nomBase,Connexion myCnx, Statement st, String transaction){

    ErrorSpecific myError = new ErrorSpecific();
    this.description.replaceAll("\u0092","'").replaceAll("'","''");
    boolean trouve = true ;

    String reqRoadmap = "SELECT  description FROM Roadmap WHERE (version = '"+this.version+"') AND (idVersionSt = "+this.idVersionSt+")"+ "AND (description NOT LIKE '%"+this.description+"%')";
    ResultSet rs = myCnx.ExecReq(st, nomBase, reqRoadmap);
    try { rs.next();
            this.description+="\n"+ "--------------------------"+"\n"+rs.getString(1).replaceAll("\u0092","'").replaceAll("'","''");
            trouve = false;

        } catch (SQLException s) {s.getMessage();}


    reqRoadmap = "UPDATE    Roadmap SET  description = '"+this.description+"' WHERE     (version = '"+this.version+"') AND (idVersionSt = "+this.idVersionSt+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"UpdateDescription",""+this.idVersionSt);
    if (myError.cause == -1) return myError;

     return myError;
  }

  public void setDatesPlanning(int AnneeRef, int mois_start ){

      if (!this.isProjet)
      {
       this.X_T0 = 0; 
       this.X_EB = 12; 
       return;
      }
    //System.out.println("nom="+nom);
    //System.out.println("dateT0="+dateT0);
    try
    {
      Utils.getDate( dateT0);

      if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
      {
        this.X_T0 = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
        if (this.X_T0 > 12) this.X_T0 = 12;
      }
      else
      {
        X_T0 = 0;
        traversantPrecedent = true;
    }
    }
    catch (Exception e)
    {
      X_T0 = -1;
    }

    try
    {
      Utils.getDate( dateT0_init);

        if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
        {
          this.X_T0_init = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
          if (this.X_T0_init > 12) this.X_T0_init = 12;
      }
        else {
          X_T0_init = 0;
          traversantPrecedent = true;
      }
    }
    catch (Exception e)
    {
      X_T0_init = -1;
    }

    try
    {
      Utils.getDate( dateEB);

      if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
      {
        this.X_EB = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
        if (this.X_EB > 12) this.X_EB = 12;
      }
      else  if  ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) < (AnneeRef*12 + mois_start))
      {
        X_EB = 0;
      }
      else
      X_EB = 12;
    }
    catch (Exception e)
    {
      X_EB = -1;
    }
    //System.out.println("dateEB="+dateEB);

    try
    {
      Utils.getDate( dateEB_init);
      if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
      {
        this.X_EB_init = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
        if (this.X_EB_init > 12) this.X_EB_init = 12;
      }
      else  if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) < (AnneeRef*12 + mois_start))
      {
        X_EB_init = 0;
      }
      else
      X_EB_init = 12;
    }
    catch (Exception e)
    {
      X_EB_init = -1;
    }

    try
    {
      Utils.getDate( this.dateTest);

      if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
      {
        this.X_TEST = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
        if (this.X_TEST > 12) this.X_TEST = 12;
      }
      else  if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) < (AnneeRef*12 + mois_start))
      {
        this.X_TEST = 0;
      }
      else
      this.X_TEST = 12;
    }
    catch (Exception e)
    {
      X_TEST = -1;
    }

    try
    {
      Utils.getDate( this.dateTest_init);
      if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
      {
        this.X_TEST_init = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
        if (this.X_TEST_init > 12) this.X_TEST_init = 12;
      }
      else  if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) < (AnneeRef*12 + mois_start))
      {
        this.X_TEST_init = 0;
      }
      else
      this.X_TEST_init = 12;
    }
    catch (Exception e)
    {
      X_TEST_init = -1;
    }

    try
    {
      Utils.getDate( this.dateProd);

      if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
      {
        this.X_PROD = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
        if (this.X_PROD > 12) this.X_PROD = 12;
      }
      else  if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) < (AnneeRef*12 + mois_start))
      {
        this.X_PROD = 0;
      }
      else
      this.X_PROD = 12;
    }
    catch (Exception e)
    {
      X_PROD = -1;
    }

    try
    {
      Utils.getDate( this.dateProd_init);
      if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
      {
        this.X_PROD_init = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
        if (this.X_PROD_init > 12) this.X_PROD_init = 12;
      }
      else  if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) < (AnneeRef*12 + mois_start))
      {
        this.X_PROD_init = 0;
      }
      else
      this.X_PROD_init = 12;
    }
    catch (Exception e)
    {
      X_PROD_init = -1;
    }
    
    try
    {
      Utils.getDate( this.dateMes);

      if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
      {
        this.X_MES = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
        if (this.X_MES > 12) this.X_MES = 12;
      }
      else  if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) < (AnneeRef*12 + mois_start))
      {
        this.X_MES = 0;
      }
      else
        this.X_MES = 12;
    }
    catch (Exception e)
    {
      X_MES = -1;
    } 
    
    try
    {
      Utils.getDate( this.dateMes_init);
      if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
      {
        this.X_MES_init = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
        if (this.X_MES_init > 12) this.X_MES_init = 12;
      }
      else  if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) < (AnneeRef*12 + mois_start))
      {
        this.X_MES_init = 0;
      }
      else
      this.X_MES_init = 12;
    }
    catch (Exception e)
    {
      X_MES_init = -1;
    }
    

    try
    {
      Utils.getDate( dateT0MOE);


        if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
        {
          this.X_T0MOE = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
          if (this.X_T0MOE > 12) this.X_T0MOE = 12;
      }
        else
        X_T0MOE = -1;
    }
    catch (Exception e)
    {
      X_T0MOE = -1;
    }


        if (this.X_T0 < 0) this.X_T0 = 0;
        if (this.X_EB < 0) this.X_EB = 0;
        if (this.X_TEST < 0) this.X_TEST = 0;
        if (this.x0_PROD < 0) this.x0_PROD = 0;
        if (this.X_MES < 0) this.X_MES = 0;

    }

  private float getX(String theDate, int AnneeRef, int mois_start){
      float X=-1;
      
    try
    {
      Utils.getDate( theDate);

      if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
      {
        X = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
        //if (X > 12) X = 12;
      }
      else
      {
        //X = 0;
        this.traversantPrecedent = true;
    }
    }
    catch (Exception e)
    {
      X = -1;
    } 
    
    if (X < 0) X= 0;
      
      return X;
  }
  public void setDatesPlanningGlissant(int AnneeRef, int mois_start ){

      if (!this.isProjet)
      {
       this.X_T0 = 0; 
       this.X_EB = 12; 
       return;
      }

      this.X_T0 = getX(dateT0,  AnneeRef,  mois_start);
      this.X_EB = getX(dateEB,  AnneeRef,  mois_start);
      this.X_TEST = getX(dateTest,  AnneeRef,  mois_start);
      this.X_PROD = getX(dateProd,  AnneeRef,  mois_start);
      this.X_MES = getX(dateMes,  AnneeRef,  mois_start);

    }
  
  public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();

    String nomProjet="";
    //String req = "SELECT     nomProjet FROM         ListeProjets WHERE     (version = '"+this.oldversion+"') AND (idVersionSt = "+this.idVersionSt+")";
    String req = "SELECT     nomProjet FROM         ListeProjets WHERE     (idRoadmap  = '"+this.id+"')";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    try { rs.next();
      nomProjet = rs.getString(1);
        } catch (SQLException s) {s.getMessage();}

    req = " DELETE FROM         RoadmapDependances";
    req+= " WHERE     (idRoadmapDestination = '"+this.id+"') OR (idRoadmapOrigine = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    if (myError.cause == -1) return myError;
    
    req = " DELETE FROM         TachesDependances";
    req+= " WHERE     (idRoadmap = '"+this.id+"') ";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    if (myError.cause == -1) return myError;
    //req = "DELETE FROM PlanDeCharge WHERE     (nomProjet = '"+nomProjet+"')";
    req = "DELETE FROM PlanDeCharge WHERE        (idRoadmap  = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    if (myError.cause == -1) return myError;
    //myCnx.trace("@90123--------","req",""+req);
    req = "DELETE FROM actionSuivi WHERE     (idRoadmap  = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    if (myError.cause == -1) return myError;

    /*
     req = "DELETE FROM Roadmap WHERE idVersionSt = "+this.idVersionSt+" AND version='"+this.oldversion+"'";
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** reqRoadmap",req);return req;}
*/
   req = "DELETE FROM Roadmap WHERE (idRoadmap  = '"+this.id+"')";
   myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
   if (myError.cause == -1) return myError;
   
   req = "UPDATE Roadmap SET idRoadmapPere=-1  WHERE (idRoadmapPere  = '"+this.id+"')";
   myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
   if (myError.cause == -1) return myError;  
   
   req = "DELETE FROM JalonsProjet WHERE (idRoadmap  = '"+this.id+"')";
   myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
   if (myError.cause == -1) return myError; 
   
   req = "DELETE FROM Baselines WHERE (idRoadmap  = '"+this.id+"')";
   myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
   if (myError.cause == -1) return myError;   

    return myError;
  }


  public String bd_InsertPO(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;



    String req =  "DELETE FROM RoadmapPO WHERE (idRoadmap = "+this.id+ ") ";
    //myCnx.trace("@01234--------","req",""+req);

     if (myCnx.ExecUpdate(st,nomBase,req,true,"EnregST") == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     //myCnx.trace("@5678--------","req",""+req);

     for (int i=0; i < this.ListeLignePO.size(); i++)
     {
       LignePO theLignePO = (LignePO)this.ListeLignePO.elementAt(i);

       String str_dateTest_devis ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
       if ((theLignePO.dateTest_devis != null) &&(!theLignePO.dateTest_devis.equals("")))
       {
        Utils.getDate(theLignePO.dateTest_devis);
        str_dateTest_devis ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
       }

       String str_dateProd_devis ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
       if ((theLignePO.dateProd_devis != null) &&(!theLignePO.dateProd_devis.equals("")))
       {
        Utils.getDate(theLignePO.dateProd_devis);
        str_dateProd_devis ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
       }

       req = "INSERT RoadmapPO (idRoadmap, idPO, Charge, Annee, dateTest_devis, dateMep_devis )";
       req+=" VALUES (";
       req+="'"+this.id+"'" +",";
       req+=theLignePO.idWebPo+",";
       req+=theLignePO.chargeEngagee+",";
       req+=theLignePO.Annee+",";
       req+=str_dateTest_devis+",";
       req+=str_dateProd_devis;
       req+=")";
       if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     }

     return "OK";

  }

  public ErrorSpecific bd_deleteVignettes(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    String req =  "DELETE FROM Vignettes WHERE (idRoadmap = "+this.id+ ")";
    //myCnx.trace("@01234--------","req",""+req);

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    return myError;

  }

  public String bd_InsertVignettes(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;



    String req =  "DELETE FROM Vignettes WHERE (idRoadmap = "+this.id+ ")";
    //myCnx.trace("@01234--------","req",""+req);

     if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     //myCnx.trace("@5678--------","req",""+req);

     for (int i=0; i < this.ListeVignettes.size(); i++)
     {
       Vignette theVignette= (Vignette)this.ListeVignettes.elementAt(i);
       String theNom = theVignette.nom.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ');
       String theDesc = theVignette.description.replaceAll("\r","").replaceAll("'","''").replace('\u0022',' ');
       req = "INSERT Vignettes (num, X, Y, nom, description, Login, idRoadmap ) VALUES ('"+theVignette.num+"',  '"+theVignette.X+"',  '"+theVignette.Y+"', '"+theNom+"', '"+theDesc+"', '"+theVignette.Login+"', '"+this.id+"')";
       if (myCnx.ExecUpdate(st,nomBase,req,true,"EnregST") == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     }

     return "OK";

  }

  private boolean isInListe(int id){
    for (int i=0; i < this.ListeActions.size(); i++)
    {
      Action theAction = (Action)this.ListeActions.elementAt(i);
      if (theAction.id == id) return true;
    }
    return false;
  }

  
  public int getNextRoadmap(int idRoadmapDestination, String nomBase,Connexion myCnx, Statement st, int nb){
      Action nextAction = null;
      ResultSet rs = null;
      
      String req = "SELECT     idRoadmapOrigine FROM         RoadmapDependances WHERE     (idRoadmapDestination = "+idRoadmapDestination+")";
      rs=myCnx.ExecReq(st, myCnx.nomBase,req);
      
      int idRoadmapOrigine = -1;
      try {
        while (rs.next()) {
            idRoadmapOrigine = rs.getInt("idRoadmapOrigine");
            Roadmap theRoadmap = new Roadmap(idRoadmapOrigine);
            //this.ListeRoadmapWithDependances.addElement(theRoadmap);
            nb++;
        }
      } catch (SQLException s) {s.getMessage();}   
      
      if (idRoadmapOrigine != -1)
          nb = getNextRoadmap(idRoadmapOrigine,  nomBase, myCnx,  st,  nb);
      return nb;
      
  }
  
  public ErrorSpecific bd_UpdateNbDependance(String nomBase,Connexion myCnx, Statement st, String transaction, int nb){
    ErrorSpecific myError = new ErrorSpecific();
    req = "UPDATE RoadmapDependances  SET nb='"+nb+"' WHERE idRoadmapDestination ="+this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateNbDependance",""+this.id);
    
    return myError;
  }  
  
  public ErrorSpecific bd_UpdateNbDependanceActions(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    req = "UPDATE Roadmap  SET nbDependances="+this.nbDependances;
    req+=" , nbActions="+this.nbActions;
    req+="  WHERE idRoadmap ="+this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateNbDependance",""+this.id);
    
    return myError;
  }  
  
  public ErrorSpecific bd_UpdateNbActions(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    req = "UPDATE Roadmap  SET  nbActions="+this.ListeActions.size();
    req+="  WHERE idRoadmap ="+this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateNbDependance",""+this.id);
    
    return myError;
  }  
  
  
  public ErrorSpecific bd_EnregOrdreDependancesRoadmap(String nomBase,Connexion myCnx, Statement st,  String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
    
    this.getListeRoadmap(nomBase, myCnx,  st);
    for (int i=0; i < this.ListeRoadmap.size();i++)
      {
        Roadmap theRoadmap = (Roadmap)this.ListeRoadmap.elementAt(i);    
        //theRoadmap.getAllFromBd(myCnx.nomBase, myCnx,  st);
        int nb = theRoadmap.getNextRoadmap(theRoadmap.id, myCnx.nomBase, myCnx,  st, 0);
        myError =theRoadmap.bd_UpdateNbDependance( myCnx.nomBase, myCnx,  st, "",  nb);
        if (myError.cause == -1) return myError;
      }    
            
        
        return myError;
  }
  
  public ErrorSpecific bd_EnregOrdreDependancesActions(String nomBase,Connexion myCnx, Statement st, Statement st2, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
    for (int i=0; i < this.ListeActions.size();i++)
      {
        Action theAction = (Action)this.ListeActions.elementAt(i);    
        theAction.getAllFromBd(myCnx.nomBase, myCnx,  st);
        int nb = theAction.getNextAction(theAction.id, myCnx.nomBase, myCnx,  st, 0);
        myError =theAction.bd_UpdateNbDependance( myCnx.nomBase, myCnx,  st, "",  nb);
        if (myError.cause == -1) return myError;
      }    
            
        
        return myError;
  }

  public ErrorSpecific bd_EnregDependancesRoadmap(String nomBase,Connexion myCnx, Statement st, Statement st2, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

   req="DELETE FROM RoadmapDependances WHERE idRoadmapDestination="+this.id;
   myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_EnregDependancesActions",""+this.id);
   if (myError.cause == -1) return myError;
   
   for (int i=0; i < this.ListeDependancesT0.size(); i++)
   {
       dependanceRoadmap thedependanceRoadmap = (dependanceRoadmap)this.ListeDependancesT0.elementAt(i);
        req = "INSERT RoadmapDependances (idRoadmapDestination, idRoadmapOrigine, type)";
        req+="  VALUES (";
        req+="'"+this.id+"'";
        req+=",";
        req+="'"+thedependanceRoadmap.roadmapOrigine.id+"'";
        req+=",";
        req+="'"+"FaD"+"'";
        req+=")";    
                        
        myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_EnregDependancesRoadmap",""+this.id);
        if (myError.cause == -1) return myError;   
   }
   
    req = "UPDATE Roadmap SET ";
    req+="nbDependances ='"+this.ListeDependancesT0.size()+"'";
    req+=" WHERE     (idRoadmap = "+this.id+")";
    
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_EnregDependancesRoadmap",""+this.id);
    if (myError.cause == -1) return myError;   
               
        return myError;
  }
  
  public ErrorSpecific bd_EnregDependancesActions(String nomBase,Connexion myCnx, Statement st, Statement st2, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

   req="DELETE FROM TachesDependances WHERE idRoadmap="+this.id;
   myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_EnregDependancesActions",""+this.id);
   if (myError.cause == -1) return myError;
   
   // Bouclage sur toutes les taches
      // Resolution du nom en idTacheOrigine
      // insertion de la dependance

        for (int i=0; i < this.ListeActions.size(); i++)
        {
          Action theAction = (Action)this.ListeActions.elementAt(i);
 
          for (int j=0; j < theAction.ListeDependance.size(); j++)
            {
                dependanceTache theDependance = (dependanceTache)theAction.ListeDependance.elementAt(j);
                int idTacheOrigine = -1;
                req = "SELECT id from actionSuivi WHERE Code='"+theDependance.codeTacheDestination+"'";
                req+=  " AND idRoadmap="+this.id;
                rs = myCnx.ExecReq(st, nomBase, req);
                try {
                    while (rs.next()) {
                         idTacheOrigine = rs.getInt(1);
                    }
                } 
                catch (SQLException s) {s.getMessage();}
                int idTacheDestination = -1;
                req = "SELECT id from actionSuivi WHERE Code='"+theAction.Code+"'";
                req+=  " AND idRoadmap="+this.id;
                rs = myCnx.ExecReq(st, nomBase, req);
                try {
                    while (rs.next()) {
                         idTacheDestination = rs.getInt(1);
                    }
                }                 
                catch (SQLException s) {s.getMessage();}
                
                        req = "INSERT TachesDependances (idTacheDestination, idTacheOrigine, type, idRoadmap)";
                        req+="  VALUES (";
                        req+="'"+idTacheDestination+"'";
                        req+=",";
                        req+="'"+idTacheOrigine+"'";
                        req+=",";
                        req+="'"+"FaD"+"'";
                        req+=",";
                        req+="'"+this.id+"'";
                        req+=")";    
                        
                        myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_EnregDependancesActions",""+this.id);
                        if (myError.cause == -1) return myError;                        
                      
                    }
                }
               
    
            
        
        return myError;
  }
  
  public ErrorSpecific bd_EnregActions(String nomBase,Connexion myCnx, Statement st, Statement st2, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    Vector ListeAction2Delete = new Vector(10);
    // Recherche des actions � supprimer
    // Ce sont celles pr�sentes dans actionSuivi et pas dans ListeAction
    String req="SELECT id FROM actionSuivi";
    req += " WHERE idRoadmap ="+ this.id;
    rs = myCnx.ExecReq(st, nomBase, req);
    try {
       while (rs.next()) {
         int id = rs.getInt(1);
         if (!isInListe(id))
         {
           Action theAction = new Action(id);
           ListeAction2Delete.addElement(theAction);

         }
       }
        } catch (SQLException s) {s.getMessage();}

        for (int i=0; i < ListeAction2Delete.size(); i++)
        {
          Action theAction = (Action)ListeAction2Delete.elementAt(i);
           req = " DELETE FROM         TachesDependances";
           req+= " WHERE     (idTacheDestination = '"+id+"') OR (idTacheOrigine = '"+theAction.id+"')";
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_EnregActions",""+this.id);
           if (myError.cause == -1) return myError;
           
           req="DELETE FROM actionSuivi WHERE id="+theAction.id;
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_EnregActions",""+this.id);
           if (myError.cause == -1) return myError;          
        }
        
        for (int i=0; i < this.ListeActions.size(); i++)
        {
          Action theAction = (Action)this.ListeActions.elementAt(i);

          String theCode=theAction.Code.replaceAll("\u0092","'").replaceAll("'"," ").replaceAll("&","et").replace('+',' ');
          String date_start =theAction.date_start;
          if ((theAction.date_start != null) &&(!theAction.date_start.equals("")))
          {
            Utils.getDate(date_start);
            date_start ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
         }
         else
           date_start = "NULL";

         String date_end =theAction.date_end;
         if ((theAction.date_end != null) &&(!theAction.date_end.equals("")))
         {
           Utils.getDate(date_end);
           date_end ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
         }
         else
           date_end = "NULL";

         String date_start_init =theAction.date_start_init;
         if ((theAction.date_start_init != null) &&(!theAction.date_start_init.equals("")))
         {
           Utils.getDate(date_start_init);
           date_start_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
        }
        else
           date_start_init = "NULL";

         String date_end_init =theAction.date_end_init;
         if ((theAction.date_end_init != null) &&(!theAction.date_end_init.equals("")))
         {
           Utils.getDate(date_end_init);
           date_end_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
        }
        else
           date_end_init = "NULL";

         //myCnx.trace(""+i+"---------"+theAction.Code,"theAction.ChargeDevis",""+theAction.ChargeDevis);
         //myCnx.trace(""+i+"---------"+theAction.Code,"theAction.ChargePrevue",""+theAction.ChargePrevue);
         if (theAction.ChargeDevis == (float)0.0)
         {
           theAction.ChargeDevis = theAction.ChargePrevue ;
           //myCnx.trace(""+i+"---------"+theAction.Code,"theAction.ChargeDevis == (float)0.0s","");
         }

          if (theAction.id == -1)
          {
            // Recherche des actions � ins�rer
            // Ce sont celles dont id=-1
            req = "INSERT actionSuivi (nom, acteur, idEtat, idRoadmap, dateAction, dateFin, type, reponse, isPlanning, doc, Code, ChargePrevue, ChargeDevis, Semaine,  RAF, LoginEmetteur, priorite, idRoadmapLie, dateAction_init, dateFin_init )";
            req+="  VALUES ('"+theAction.nom.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ')+"',  '"+theAction.acteur.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\r","")+"',  '"+theAction.idEtat+"', "+this.id+", "+date_start+", "+date_end+", '"+this.type+"', '"+theAction.reponse.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ')+"'"+","+theAction.isPlanning+",'"+theAction.doc+"','"+theCode+"'";
            req+=",";
            req+="'"+theAction.ChargePrevue+"'";
            req+=",";
            req+="'"+theAction.ChargeDevis+"'";
            req+=",";
            req+="'"+theAction.Semaine+"'";
            req+=",";
            req+="'"+theAction.RAF+"'";
            req+=",";
            req+="'"+theAction.LoginEmetteur+"'";
            req+=",";
            req+="'"+theAction.priorite+"'";
            req+=",";
            req+="'"+theAction.idRoadmapLie+"'";
            req+=",";
            req+=""+date_start_init+"";
            req+=",";
            req+=""+date_end_init+"";
            req+=")";

          }
          else
          {
            // -------------------- a la cloture de la tache on m�morise la date de cloture ------------------//
            req = "SELECT     idEtat FROM  actionSuivi WHERE     (id = "+theAction.id+")";
            rs = myCnx.ExecReq(st, nomBase, req);
            int old_etat = -1;
            try { rs.next();
              old_etat = rs.getInt("idEtat");
             } catch (SQLException s) {s.getMessage();}



                if ((theAction.idEtat == 3) && (theAction.idEtat != old_etat)) {

                  String currentDate = Utils.getCurrentDate( myCnx.nomBase, myCnx,  st);
                  String str_date = "CONVERT(DATETIME, '"+currentDate+"',  102)";

                  req = "UPDATE actionSuivi SET  dateCloture ="+ str_date + " WHERE id="+theAction.id;
                  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_EnregActions",""+this.id);
                  if (myError.cause == -1) return myError;

                }


                // ---------------- si le traitement est incorrect on leve une anomalie (traite -> en cours)------//
/*
                      if ((theAction.idEtat == 2) && (old_etat == 3)) {

                        ActionAnomalie theActionAnomalie = new ActionAnomalie(theAction.id);
                        theActionAnomalie.bd_insert(myCnx.nomBase,myCnx, st, transaction);


                      }
*/
                // -----------------------------------------------------------------------------------------------//

              // -----------------------------------------------------------------------------------------------//


            // Recherche des actions � modifier
            // Ce sont celles dont id!= -1
            req = "UPDATE actionSuivi SET ";
            req +=" nom ='"+ theAction.nom.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ') + "', ";
            req +=" acteur ='"+ theAction.acteur.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\r","") + "', ";
            req +=" idEtat ="+ theAction.idEtat + ", ";
            req +=" idRoadmap ="+ this.id + ", ";
            req +=" dateAction ="+ date_start + ", ";
            req +=" dateFin ="+ date_end + ", ";
            req +=" type ='"+ this.type + "', ";
            if (theAction.reponse != null)
            {
                req +=" reponse ='"+ theAction.reponse.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ') + "', ";
            }         
            req +=" isPlanning ="+ theAction.isPlanning + ", ";
            req +=" doc ='"+ theAction.doc + "', ";
            req +=" Code ='"+ theCode + "', ";
            req +=" ChargePrevue ='"+ theAction.ChargePrevue + "', ";
            req +=" ChargeDevis ='"+ theAction.ChargeDevis + "', ";
            req +=" Semaine ='"+ theAction.Semaine + "', ";
            req +=" RAF ='"+ theAction.RAF + "', ";
            req +=" priorite ='"+ theAction.priorite + "', ";
            req +=" idRoadmapLie ='"+ theAction.idRoadmapLie + "', ";
            req +=" dateAction_init ="+ date_start_init + ", ";
            req +=" dateFin_init ="+ date_end_init ;

            req += " WHERE id ="+ theAction.id;
          }

          myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_EnregActions",""+this.id);
           if (myError.cause == -1) return myError;


        }

     return myError;

  }

  public String sendMail(String nomBase,Connexion myCnx, Statement st, String transaction, Collaborateur theCollaborateurRespMoe){
    ResultSet rs=null;


    String originalLoginRespMoe = theCollaborateurRespMoe.getOriginalLogin(nomBase, myCnx, st);
    String theCollaborateurRespMoeMail = theCollaborateurRespMoe.mail.replaceAll(theCollaborateurRespMoe.Login,originalLoginRespMoe);

    //myCnx.trace("0----------","this.ListeActions.size()",""+this.ListeActions.size());
    for (int i=0; i < this.ListeActions.size(); i++)
    {
       Action theAction = (Action)this.ListeActions.elementAt(i);
       //Collaborateur theCollaborateur = new Collaborateur(theAction.acteur);
       //theCollaborateur.getAllFromBd(nomBase, myCnx, st);

       //myCnx.trace(i+"----------","theAction.Code ",""+theAction.Code );
       //myCnx.trace(i+"----------","theAction.id ",""+theAction.id );
       //myCnx.trace(i+"----------","theAction.idEtat ",""+theAction.old_idEtat );
       //myCnx.trace(i+"----------","theAction.old_idEtat ",""+theAction.idEtat );

       String originalLogin="";
       if (theAction.id == -1)
       {
         String myMail = "";
         String ListeDiffusion=theAction.getListeDiffusion(myCnx);
         if ((theAction.LoginEmetteur != null) && (!theAction.LoginEmetteur.equals("")))
         {
           Collaborateur theCollaborateurEmetteur = new Collaborateur(theAction.LoginEmetteur);
           theCollaborateurEmetteur.getAllFromBd(nomBase, myCnx, st);
           originalLogin = theCollaborateurEmetteur.getOriginalLogin(nomBase, myCnx, st);
           theCollaborateurEmetteur.mail = theCollaborateurEmetteur.mail.replaceAll(theCollaborateurEmetteur.Login,originalLogin);
           theAction.sendMail(nomBase, myCnx, st, transaction, -1, "", theCollaborateurEmetteur.mail);
         }
         else
         {
           theAction.sendMail(nomBase, myCnx, st, transaction, -1, "", theCollaborateurRespMoeMail);
         }
       }
       else
       {
         String ListeDiffusion=theAction.getListeDiffusion(myCnx);
         theAction.sendMail(nomBase, myCnx, st, transaction,theAction.old_idEtat, "", "");
       }
    }


    return "OK";
  }

  public String bd_InsertActions(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;


    String req =  "DELETE FROM actionSuivi WHERE (idRoadmap = "+this.id+ ") AND (type = '"+this.type+"')";
    //myCnx.trace("@01234--------","req",""+req);

     if (myCnx.ExecUpdate(st,nomBase,req,true,"EnregST") == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     //myCnx.trace("@5678--------","req",""+req);

     for (int i=0; i < this.ListeActions.size(); i++)
     {
       Action theAction = (Action)this.ListeActions.elementAt(i);
       String theCode=theAction.Code.replaceAll("\u0092","'").replaceAll("'"," ").replaceAll("&","et").replace('+',' ');
       String date_start =theAction.date_start;
       if ((theAction.date_start != null) &&(!theAction.date_start.equals("")))
       {
         Utils.getDate(date_start);
         date_start ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
      }
      else
        date_start = "NULL";

      String date_end =theAction.date_end;
      if ((theAction.date_end != null) &&(!theAction.date_end.equals("")))
      {
        Utils.getDate(date_end);
        date_end ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
      }
      else
        date_end = "NULL";

      req = "INSERT actionSuivi (nom, acteur, idEtat, idRoadmap, dateAction, dateFin, type, reponse, isPlanning, doc, Code, ChargePrevue, ChargeDevis, Semaine,  RAF, priorite)";
      req+="  VALUES ('"+theAction.nom.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ')+"',  '"+theAction.acteur.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\r","")+"',  '"+theAction.idEtat+"', "+this.id+", "+date_start+", "+date_end+", '"+this.type+"', '"+theAction.reponse.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ')+"'"+","+theAction.isPlanning+",'"+theAction.doc+"','"+theCode+"'";
      req+=",";
      req+="'"+theAction.ChargePrevue+"'";
      req+=",";
      req+="'"+theAction.ChargeDevis+"'";
      req+=",";
      req+="'"+theAction.Semaine+"'";
      req+=",";
      req+="'"+theAction.RAF+"'";
      req+=",";
      req+="'"+theAction.priorite+"'";
      req+=")";
       if (myCnx.ExecUpdate(st,nomBase,req,true,"EnregST") == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     }

     return "OK";

  }

  public ErrorSpecific bd_InsertHistoriqueSuivi(String nomBase,Connexion myCnx, Statement st, String transaction, String Login){
    ErrorSpecific myError = new ErrorSpecific();
    if (!this.type.equals("MOE") && ( this.Suivi == null || this.Suivi.equals(""))) return myError;
    if (this.type.equals("MOE") && ( this.SuiviMOE == null || this.SuiviMOE.equals(""))) return myError;

    if ((this.Suivi.length() < 13) && ((this.fichierAttache == null) || (this.fichierAttache.equals("")))) return myError;
       
    req = "INSERT historiqueSuivi (";
    req += "dateSuivi" + ",";
    req += "nom" + ",";
    req += "description" + ",";
    req += "idRoadmap" + ",";
    req += "type" + ",";
    req += "Login" + ",";
    req += "fichierAttache" + "";
    req += ")";
    req += " VALUES ";
    req += "(";
    req += "CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)"+ ",";
    req += "'" + "Migration initiale" + "',";
    if (!this.type.equals("MOE"))
    {
      req += "'" + this.Suivi.replaceAll("\u0092", "'").replaceAll("'", "''") + "',";
    }
    else
    {
      req += "'" + this.SuiviMOE.replaceAll("\u0092", "'").replaceAll("'", "''") + "',";
    }
    req += "'" + this.id + "',";
    req += "'" +this.type + "',";
    req += "'" +Login + "',";
    req += "'" +this.fichierAttache + "'";
    req += ")";

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertHistoriqueSuivi",""+this.id);

     return myError;
  }

  public String bd_UpdateDates(String nomBase,Connexion myCnx, Statement st, String transaction){

    String str_dateT0 ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((str_dateT0 != null) &&(!this.dateT0.equals("")))
    {
     Utils.getDate(this.dateT0);
     str_dateT0 ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateEB ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((str_dateEB != null) &&(!this.dateEB.equals("")))
    {
     Utils.getDate(this.dateEB);
     str_dateEB ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateTest ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((str_dateTest != null) &&(!this.dateTest.equals("")))
    {
     Utils.getDate(this.dateTest);
     str_dateTest ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateProd ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((str_dateProd != null) &&(!this.dateProd.equals("")))
    {
      Utils.getDate(this.dateProd);
      str_dateProd ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateMes ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((str_dateMes != null) &&(!this.dateMes.equals("")))
    {
      Utils.getDate(this.dateMes);
      str_dateMes ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    req = "UPDATE Roadmap SET ";
    req +=" dateT0 ="+ str_dateT0 + ", ";
    req +=" dateEB ="+ str_dateEB + ", ";
    req +=" dateTest ="+ str_dateTest + ", ";
    req +=" dateMep ="+ str_dateProd + ", ";
    req +=" dateMes ="+ str_dateMes + "";
    req += " WHERE idRoadmap ="+ this.id;

    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     return "OK";
  }
 
  public ErrorSpecific bd_UpdateDatesInit(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    String str_init ="";

    for (int i= 0; i < this.ListeJalons.size(); i++)
    {
        Jalon theJalon = (Jalon)this.ListeJalons.elementAt(i);
        if ((theJalon.strDate != null) &&(!theJalon.strDate.equals("")))
        {
            Utils.getDate(theJalon.strDate);
            str_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
        }        
        
        req = "UPDATE JalonsProjet SET ";
        req +=" date_init ="+ str_init ;
        req += " WHERE idRoadmap ="+ this.id;
        req += " AND type ="+ theJalon.type;

     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateDatesInit",""+this.id);        
    }



      return myError;
  }   
  public ErrorSpecific bd_UpdateDatesInit2(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    String str_dateT0_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((str_dateT0_init != null) &&(!this.dateT0_init.equals("")))
    {
     Utils.getDate(this.dateT0_init);
     str_dateT0_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateEB_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((str_dateEB_init != null) &&(!this.dateEB_init.equals("")))
    {
     Utils.getDate(this.dateEB_init);
     str_dateEB_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateTest_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((str_dateTest_init != null) &&(!this.dateTest_init.equals("")))
    {
     Utils.getDate(this.dateTest_init);
     str_dateTest_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateProd_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((str_dateProd_init != null) &&(!this.dateProd_init.equals("")))
    {
      Utils.getDate(this.dateProd_init);
      str_dateProd_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateMes_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((str_dateMes_init != null) &&(!this.dateMes_init.equals("")))
    {
      Utils.getDate(this.dateMes_init);
      str_dateMes_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    req = "UPDATE Roadmap SET ";
    req +=" dateT0_init ="+ str_dateT0_init + ", ";
    req +=" dateEB_init ="+ str_dateEB_init + ", ";
    req +=" dateTest_init ="+ str_dateTest_init + ", ";
    req +=" dateMep_init ="+ str_dateProd_init + ", ";
    req +=" dateMes_init ="+ str_dateMes_init + "";
    req += " WHERE idRoadmap ="+ this.id;

     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateDatesInit",""+this.id);


      return myError;
  }  

  public String bd_UpdateDateRef(String nomBase,Connexion myCnx, Statement st, String transaction){

    String str_dateEB_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((dateEB_init != null) &&(!dateEB_init.equals("")))
    {
     Utils.getDate(dateEB_init);
     str_dateEB_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateProd_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((dateProd_init != null) &&(!dateProd_init.equals("")))
    {
      Utils.getDate(dateProd_init);
      str_dateProd_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    req = "UPDATE Roadmap SET ";
    req +=" dateEB_init ="+ str_dateEB_init + ", ";
    req +=" dateMep_init ="+ str_dateProd_init + "";
    req += " WHERE idRoadmap ="+ this.id;

    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     return "OK";
  }

  public ErrorSpecific bd_UpdateHistoriqueSuivi(String nomBase,Connexion myCnx, Statement st, String transaction, int idSuivi){
    ErrorSpecific myError = new ErrorSpecific();
    if (!this.type.equals("MOE") && ( this.Suivi == null || this.Suivi.equals(""))) return myError;
    if (this.type.equals("MOE") && ( this.SuiviMOE == null || this.SuiviMOE.equals(""))) return myError;

    if (true)
    {
      req = "UPDATE    historiqueSuivi SET  description = '"+this.Suivi.replaceAll("\u0092","'").replaceAll("'","''")+"' WHERE     (id = "+idSuivi+")";
    }
    else
    {
      req = "UPDATE    historiqueSuivi SET  description = '"+this.SuiviMOE.replaceAll("\u0092","'").replaceAll("'","''")+"' WHERE     (id = "+idSuivi+")";
    }
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateHistoriqueSuivi",""+this.id);

     return myError;
  }

  public ErrorSpecific bd_UpdateSt(String nomBase,Connexion myCnx, Statement st, ST theSt, String transaction){
    ErrorSpecific myError = new ErrorSpecific();

    if (theSt.etatVersionSt >= 3)
    {
      myError.cause = 0;
      return myError; // en production
    }
    if (this.EtatRoadmap.equals("PROD") || this.EtatRoadmap.equals("MES"))
    {
      req = "UPDATE    VersionSt SET  etatVersionSt = 3 where idVersionSt= "+ theSt.idVersionSt;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
      if (myError.cause == -1) return myError;
    }
    return myError;
  }

  public ErrorSpecific bd_deleteOldCharges(String nomBase,Connexion myCnx, Statement st, String transaction, int oldWeekStart, int oldWeekEnd, int newWeekStart, int newWeekEnd, int anneeRef, int idMembre){
    ErrorSpecific myError = new ErrorSpecific();
    req = "delete FROM  PlanDeCharge";
    req+="    WHERE     (idRoadmap = "+this.id+") AND (semaine >= "+oldWeekStart+") AND (semaine < "+oldWeekEnd+") AND (anneeRef = "+anneeRef+") AND (projetMembre = "+idMembre+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_deleteOldCharges",""+this.id);


     return myError;
  }

  public ErrorSpecific bd_UpdateNewCharges(String nomBase,Connexion myCnx, Statement st, String transaction, int oldWeekStart, int oldWeekEnd, int newWeekStart, int newWeekEnd, float ChargeMoyenne,int anneeRef, int idMembre){

    ErrorSpecific myError = new ErrorSpecific();
    for (int i=newWeekStart; i <newWeekEnd; i++)
    {
      req = "insert   PlanDeCharge ";
      req += "     (nomProjet,projetMembre, semaine, anneeRef, idRoadmap, Charge)";
      req += " VALUES";
      req += " (''," + idMembre + ","+i+"," + anneeRef + "," + this.id + "," + ChargeMoyenne + ")";

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateNewCharges",""+this.id);
      if (myError.cause == -1) return myError;
    }

     return myError;
  }

private int GetCompare(String newDate, Date newDDate, Date oldDDate)
  {
    int compare = 0;
    if (newDDate != null && oldDDate.getYear() !=0)
          compare=oldDDate.compareTo(newDDate);
        else
      compare = 999;

    return compare;
  }

public long getDeltaT0() {
    long timeT0 = 0;
    long timeT0_new = 0;
    long delta = 0;
    try{
        this.DdateT0_new = Utils.getNewDate(this.dateT0);
        timeT0 = Utils.getNewDate(this.dateT0_old).getTime();
        timeT0_new = this.DdateT0_new.getTime();
        delta = timeT0_new - timeT0;
    }
    catch (Exception e){
    }
    
    return delta;
    
}

public void setDecalageFormDeltaT0(long deltaT0) {
  for (int i=0; i < this.ListeActions.size();i++)
      {
        Action theAction = (Action)this.ListeActions.elementAt(i);
        long time_start = theAction.Ddate_start.getTime();
        time_start+=deltaT0;
        theAction.Ddate_start = new Date(time_start);
        long time_end = theAction.Ddate_end.getTime();
        time_end+=deltaT0;
        theAction.Ddate_end = new Date(time_end);        
      }
    
}

  public String checkPlages(String nomBase,Connexion myCnx, Statement st){

// ---------- T0 ------------------------------------------------------//
this.DdateT0_new = Utils.getNewDate(this.dateT0);
this.compareT0=GetCompare(this.dateT0, this.DdateT0_new, this.DdateT0);
// ---------- EB ------------------------------------------------------//
this.DdateEB_new = Utils.getNewDate(this.dateEB);
this.compareEB=GetCompare(this.dateEB, this.DdateEB_new, this.DdateEB);
// ---------- TEST ------------------------------------------------------//
this.DdateTest_new = Utils.getNewDate(this.dateTest);
this.compareTEST=GetCompare(this.dateTest, this.DdateTest_new, this.DdateTest);
// ---------- PROD ------------------------------------------------------//
this.DdateProd_new = Utils.getNewDate(this.dateProd);
this.comparePROD=GetCompare(this.dateProd, this.DdateProd_new, this.DdateProd);
// -------------------------------------------------------------------//

if (((this.compareT0 != 0) || (this.compareEB != 0)) && (this.compareT0 != 999)&& (this.compareEB != 999))
    {
      // ----------- Calcul des plage avant et apres exprim� en semaine ----------------//
      // Plage Avant = Semaine (date EB) - Semaine (date T0)
      this.old_WeekEB = Utils.getWeek(nomBase,myCnx, st,this.DdateEB.getDate(), this.DdateEB.getMonth() + 1, this.DdateEB.getYear() + 1900);
      this.old_WeekT0 = Utils.getWeek(nomBase,myCnx, st,this.DdateT0.getDate(), this.DdateT0.getMonth()+ 1, this.DdateT0.getYear()+ 1900);
      int old_plageEB = old_WeekEB - old_WeekT0;
      this.new_WeekEB = Utils.getWeek(nomBase,myCnx, st,DdateEB_new.getDate(), DdateEB_new.getMonth()+ 1, DdateEB_new.getYear()+ 1900);
      this.new_WeekT0 = Utils.getWeek(nomBase,myCnx, st,DdateT0_new.getDate(), DdateT0_new.getMonth()+ 1, DdateT0_new.getYear()+ 1900);
      int new_plageEB = new_WeekEB - new_WeekT0;

    }

    if (((this.compareEB != 0) || (this.compareTEST != 0))&& (this.compareEB != 999)&& (this.compareTEST != 999))
        {
          // ----------- Calcul des plage avant et apres exprim� en semaine ----------------//
          // Plage Avant = Semaine (date EB) - Semaine (date T0)
          this.old_WeekTEST = Utils.getWeek(nomBase,myCnx, st,this.DdateTest.getDate(), this.DdateTest.getMonth()+ 1, this.DdateTest.getYear()+ 1900);
          this.old_WeekEB = Utils.getWeek(nomBase,myCnx, st,this.DdateEB.getDate(), this.DdateEB.getMonth()+ 1, this.DdateEB.getYear()+ 1900);

          int old_plageTEST = old_WeekTEST - old_WeekEB;

          this.new_WeekTEST = Utils.getWeek(nomBase,myCnx, st,DdateTest_new.getDate(), DdateTest_new.getMonth()+ 1, DdateTest_new.getYear()+ 1900);
          this.new_WeekEB = Utils.getWeek(nomBase,myCnx, st,DdateEB_new.getDate(), DdateEB_new.getMonth()+ 1, DdateEB_new.getYear()+ 1900);
          int new_plageTEST = new_WeekTEST - new_WeekEB;

    }

    if (((this.compareTEST != 0) || (this.comparePROD != 0))&& (this.compareTEST != 999)&& (this.comparePROD != 999))
        {
          // ----------- Calcul des plage avant et apres exprim� en semaine ----------------//
          // Plage Avant = Semaine (date EB) - Semaine (date T0)
          this.old_WeekPROD = Utils.getWeek(nomBase,myCnx, st,this.DdateProd.getDate(), this.DdateProd.getMonth()+ 1, this.DdateProd.getYear()+ 1900);
          this.old_WeekTEST = Utils.getWeek(nomBase,myCnx, st,this.DdateTest.getDate(), this.DdateTest.getMonth()+ 1, this.DdateTest.getYear()+ 1900);

          int old_plagePROD = old_WeekPROD - old_WeekTEST;

          this.new_WeekPROD = Utils.getWeek(nomBase,myCnx, st,DdateProd_new.getDate(), DdateProd_new.getMonth()+ 1, DdateProd_new.getYear()+ 1900);
          this.new_WeekTEST = Utils.getWeek(nomBase,myCnx, st,DdateTest_new.getDate(), DdateTest_new.getMonth()+ 1, DdateTest_new.getYear()+ 1900);

          int new_plagePROD = new_WeekPROD - new_WeekTEST;

    }

    return "OK";
  }

  public ErrorSpecific bd_Enreg(String nomBase,Connexion myCnx, Statement st, String transaction){

    ErrorSpecific myError = new ErrorSpecific();

//System.out.println("bd_Insert");
    //myCnx.trace("@@1234--------","bd_Insert",""+transaction);
    String str_dateT0 ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((dateT0 != null) &&(!dateT0.equals("")))
    {
      //myCnx.trace("@@1234--------","dateT0",""+dateT0);
     Utils.getDate(dateT0);
     //myCnx.trace("@@5678--------","dateT0",""+dateT0);
     str_dateT0 ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateEB ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((dateEB != null) &&(!dateEB.equals("")))
    {
     Utils.getDate(dateEB);
     str_dateEB ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateTest ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";

    if ((dateTest != null) &&(!dateTest.equals("")))
    {
      Utils.getDate(dateTest);
      str_dateTest ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateProd ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((dateProd != null) &&(!dateProd.equals("")))
    {
      Utils.getDate(dateProd);
      str_dateProd ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

     String str_dateMes="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((dateMes != null) &&(!dateMes.equals("")))
    {
      Utils.getDate(dateMes);
      str_dateMes ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateT0MOE ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((this.dateT0MOE != null) &&(!this.dateT0MOE.equals("")))
    {
      //myCnx.trace("@@1234--------","dateT0",""+dateT0);
     Utils.getDate(this.dateT0MOE);
     //myCnx.trace("@@5678--------","dateT0",""+dateT0);
     str_dateT0MOE ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateT0_init="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((dateT0_init != null) &&(!dateT0_init.equals("")))
    {
     Utils.getDate(dateT0_init);
     str_dateT0_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateEB_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((dateEB_init != null) &&(!dateEB_init.equals("")))
    {
     Utils.getDate(dateEB_init);
     str_dateEB_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateTest_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";

    if ((dateTest_init != null) &&(!dateTest_init.equals("")))
    {
      Utils.getDate(dateTest_init);
      str_dateTest_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateProd_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((dateProd_init != null) &&(!dateProd_init.equals("")))
    {
      Utils.getDate(dateProd_init);
      str_dateProd_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateMes_init="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((dateMes_init != null) &&(!dateMes_init.equals("")))
    {
      Utils.getDate(dateMes_init);
      str_dateMes_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    String str_dateT0MOE_init ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    if ((this.dateT0MOE_init != null) &&(!this.dateT0MOE_init.equals("")))
    {
     Utils.getDate(this.dateT0MOE_init);
     str_dateT0MOE_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
    }

    this.oldversion=oldversion.replaceAll("\u0092","'").replaceAll("'","''");
    this.version=version.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''''").replaceAll("&","et").replace('+',' ');
    this.description=description.replaceAll("\u0092","'").replaceAll("'","''");
    this.MotsClef=MotsClef.replaceAll("\u0092","'").replaceAll("'","''");

    try{
      int nb = Integer.parseInt(this.idPO) ;
    }
    catch (Exception e){
      this.idPO = "-1";
    }

    try{
      float nb = Float.parseFloat(this.Charge) ;
    }
    catch (Exception e){
      this.Charge = "0";
    }


    this.MotsClef=this.MotsClef.replaceAll("�","e").replaceAll("�","a").replaceAll("�","a").replaceAll("�","u").replaceAll("�","e").replaceAll("�","i").replaceAll("�","e");
      String str_idMembre = "";
      if (this.idMembre >0)
          str_idMembre = ""+this.idMembre;
      else
          str_idMembre = "NULL";
      
    if (this.id == -1) // Creation
    {    
      req = "INSERT Roadmap (version,  description, dateEB,dateMep,dateMes,idVersionSt,Tendance, Etat, Panier, idPO, Charge, Ordre,dateT0,dateTest, docEB, docDevis, Annee, MotsClef, dateT0_init, dateEB_init, dateTest_init, dateMep_init, dateMes_init, idEquipe, LF_Month, LF_Year, dateT0MOE, dateT0MOE_init, idMembre, standby, idBrief, isDevis, idProfilSpecification,idProfilDeveloppement, idProfilTest, typologie, idRoadmapPere, idJalon) ";
      req+=" VALUES ('"+this.version+"',  '"+this.description+"', "+str_dateEB+""+", "+str_dateProd+", "+str_dateMes+", '"+this.idVersionSt+"',   '"+this.Tendance+"',  '"+this.EtatRoadmap+"',  '"+this.Panier+"',  '"+this.idPO+"',  '"+this.Charge+"',  '"+this.Ordre+"',  "+str_dateT0+",  "+str_dateTest+",  '"+this.docEB+"',  '"+this.docDevis+"',  '"+this.Annee+"',  '"+this.MotsClef+"',  "+str_dateT0_init+",  "+str_dateEB_init+",  "+str_dateTest_init+",  "+str_dateProd_init+",  "+str_dateMes_init+", "+this.idEquipe+", "+this.LF_Month+", "+this.LF_Year+", "+str_dateT0MOE+", "+str_dateT0MOE_init+", "+str_idMembre+", "+this.standby+", "+this.idBrief+", "+this.isDevis+", "+this.idProfilSpecification+", "+this.idProfilDeveloppement+", "+this.idProfilTest+", "+this.typologie+", "+this.idPere+", "+this.idJalon+")";

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
      if (myError.cause == -1) return myError;

        req = "SELECT idRoadmap FROM  Roadmap WHERE     (idVersionSt = "+this.idVersionSt+") AND (version = '"+this.version+"')";
        ResultSet rs = myCnx.ExecReq(st, nomBase, req);
        try { rs.next();
          this.id = rs.getInt(1);
        } catch (SQLException s) {s.getMessage();}
    }
    else // modification
    {
      req = "UPDATE Roadmap SET ";
      req +=" version ='"+ this.version + "', ";
      req +=" description ='"+ this.description + "', ";
      req +=" dateEB ="+ str_dateEB + ", ";
      req +=" dateMep ="+ str_dateProd + ", ";
      req +=" dateMes ="+ str_dateMes + ", ";
      req +=" idVersionSt ='"+ this.idVersionSt + "', ";
      req +=" Tendance ='"+ this.Tendance + "', ";
      req +=" Etat ='"+ this.EtatRoadmap + "', ";
      req +=" Panier ='"+ this.Panier + "', ";
      req +=" idPO ='"+ this.idPO + "', ";
      req +=" Charge ='"+ this.Charge + "', ";
      req +=" Ordre ='"+ this.Ordre + "', ";
      req +=" dateT0 ="+ str_dateT0 + ", ";
      req +=" dateTest ="+ str_dateTest + ", ";
      req +=" docEB ='"+ this.docEB + "', ";
      req +=" docDevis ='"+ this.docDevis + "', ";
      req +=" docExigences ='"+ this.docExigences + "', ";
      req +=" Annee ='"+ this.Annee + "', ";
      req +=" MotsClef ='"+ this.MotsClef + "', ";
      req +=" dateT0_init ="+ str_dateT0_init + ", ";
      req +=" dateEB_init ="+ str_dateEB_init + ", ";
      req +=" dateTest_init ="+ str_dateTest_init + ", ";
      req +=" dateMep_init ="+ str_dateProd_init + ", ";
      req +=" dateMes_init ="+ str_dateMes_init + ", ";
      req +=" idEquipe ="+ this.idEquipe + ", ";
      req +=" LF_Month ="+ this.LF_Month + ", ";
      req +=" LF_Year ="+ this.LF_Year + ", ";
      req +=" dateT0MOE ="+ str_dateT0MOE + ", ";
      req +=" dateT0MOE_init ="+ str_dateT0MOE_init + ", ";
      req +=" idMembre ="+ str_idMembre + ",";
      req +=" standby ="+ this.standby + ",";
      req +=" isDevis ="+ this.isDevis + ",";
      req +=" idBrief ="+ this.idBrief + ",";
      req +=" idProfilSpecification ="+ this.idProfilSpecification + ",";
      req +=" idProfilDeveloppement ="+ this.idProfilDeveloppement + ",";
      req +=" idProfilTest ="+ this.idProfilTest + ",";
      req +=" typologie ="+ this.typologie + ",";
      req +=" idRoadmapPere ="+ this.idPere + ",";
      req +=" idJalon ="+ this.idJalon + "";

      req += " WHERE idRoadmap ="+ this.id;
      req+=" AND     (Roadmap.LF_Month = "+this.LF_Month+")";
      req+=" AND     (Roadmap.LF_Year  = "+this.LF_Year +")";

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
      if (myError.cause == -1) return myError;
    }
  
    return myError;
  }

  public Collaborateur addListeCollaborateur(int id)
 {
    Collaborateur theCollaborateur = new Collaborateur(id);
    this.ListeCollaborateurs.addElement(theCollaborateur);
    return theCollaborateur; 
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nomSt="+this.nomSt);
    System.out.println("version="+this.version);
    System.out.println("description="+this.description);
    if (this.description != null)  System.out.println("lg_description="+this.description.length());

    System.out.println("anneeT0="+this.anneeT0);
    System.out.println("weekT0="+this.weekT0);
    System.out.println("dateT0="+this.dateT0);

    System.out.println("anneeEB="+this.anneeEB);
    System.out.println("weekEB="+this.weekEB);
    System.out.println("dateEB="+this.dateEB);


    System.out.println("anneeTEST="+this.anneeTEST);
    System.out.println("weekTEST="+this.weekTEST);
    System.out.println("dateTEST="+this.dateTest);

    System.out.println("anneePROD="+this.anneePROD);
    System.out.println("weekPROD="+this.weekPROD);
    System.out.println("datePROD="+this.dateProd);


    System.out.println("anneeMES="+this.anneeMES);
    System.out.println("weekMES="+this.weekMES);
    System.out.println("dateMES="+this.dateMes);


    System.out.println("idVersionSt="+this.idVersionSt);

    System.out.println("Tendance="+this.Tendance);

    System.out.println("Etat="+this.EtatRoadmap);

    System.out.println("Panier="+this.Panier);
    System.out.println("Ordre="+this.Ordre);
    System.out.println("docEB="+this.docEB);
    System.out.println("docExigences="+this.docExigences);
    System.out.println("docDevis="+this.docDevis);
    System.out.println("Annee="+this.Annee);

    System.out.println("isProjet="+this.isProjet);
    System.out.println("type="+this.type);
    System.out.println("idEquipe="+this.idEquipe);

    System.out.println("LF_Month="+this.LF_Month);
    System.out.println("LF_Year="+this.LF_Year);

    System.out.println("MotsClef="+this.MotsClef);
    System.out.println("idMembre="+this.idMembre);

    System.out.println("idPO="+this.idPO);
    System.out.println("Charge="+this.Charge);
    System.out.println("chargeConsommee="+this.chargeConsommee);
    System.out.println("ChargePrevue="+this.ChargePrevue);

    System.out.println("codeSujet="+this.codeSujet);
    System.out.println("part="+this.part);
    System.out.println("TypeCharge="+this.TypeCharge);

    System.out.println("standby="+this.standby);
    System.out.println("idBrief="+this.idBrief);

    System.out.println("==================================================");
 }

 public static int getNb(String nomBase,Connexion myCnx, Statement st,int idVersionSt){
   int nb = 0;
   String reqRoadmap = "SELECT     MAX(Ordre)  AS nb FROM   Roadmap GROUP BY idVersionSt HAVING      (idVersionSt = "+idVersionSt+")";
   ResultSet rs = myCnx.ExecReq(st, nomBase, reqRoadmap);
   try { rs.next();
     nb = rs.getInt(1) +1;
       } catch (SQLException s) {s.getMessage();}
   return nb;
  }

public float getNbJoursMoyen(String nomBase,Connexion myCnx, Statement st, int theYearRef, int idCollaborateur, Date olddateStart, Date olddateEnd, Date newdateStart, Date newdateEnd){

  int oldWeekStart = Utils.getWeek(nomBase,myCnx, st,olddateStart.getDate(),olddateStart.getMonth()+1,olddateStart.getYear()+1900);;
  int oldWeekEnd = Utils.getWeek(nomBase,myCnx, st,olddateEnd.getDate(),olddateEnd.getMonth()+1,olddateEnd.getYear()+1900);;
  int oldPlage = oldWeekEnd - oldWeekStart;
  System.out.println("oldPlage= " + oldPlage);
  int nbJours = this.getChargesProjetsByRangeByCollaborateur(nomBase, myCnx, st, theYearRef, idCollaborateur, oldWeekStart, oldWeekEnd);
  float oldChargeMoyenne = ( (float) nbJours / (float) oldPlage);
  System.out.println("oldChargeMoyenne= " + oldChargeMoyenne);

  int newWeekStart = Utils.getWeek(nomBase,myCnx, st,newdateStart.getDate(),newdateStart.getMonth()+1,newdateStart.getYear()+1900);;
  int newWeekEnd = Utils.getWeek(nomBase,myCnx, st,newdateEnd.getDate(),newdateEnd.getMonth()+1,newdateEnd.getYear()+1900);;
  int newPlage = newWeekEnd - newWeekStart;
  System.out.println("newPlage= " + newPlage);
  float newChargeMoyenne = ( (float) nbJours / (float) newPlage);
        System.out.println("newChargeMoyenne= " + newChargeMoyenne);

 return newChargeMoyenne;
}

  public static void main(String[] args) {

  }

}

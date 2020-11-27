package Projet; 

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import accesbase.Connexion;
import accesbase.ErrorSpecific;
import ST.ST;

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
import java.util.Date;
import General.Utils;
import Organisation.Service;
import java.util.Vector;
import java.util.StringTokenizer;
public class Brief
    extends Roadmap {

  public String LoginCreateur="";
  public String ServiceCreateur="";
  public Date DdateCreation = null;
  public String dateCreation = "";
  public Date DdateCOMAA = null;
  public String dateCOMAA = "";
  public Date DdateReponse = null;
  public String dateReponse = "";

  public int Cout=0;
  public int Qualite=0;
  public int Fonctionnalite=0;
  public int Delais=0;
  public int TypeDemande=0;

  public String Contexte = "";

  public String ImpactSt="";
  public String ImpactProcessus="";

  public String Gain="";
  public String Manque="";

  private String req="";

  public int idEtat = -1;
  public String Etat = "";

  public String Reponse="";
  public String prepaReponse="";

  public int idServiceAffecte = -1;
  public String ServiceAffecte = "";
  public int idUsineAffecte = -1;
  public String UsineAffecte = "";

  public Vector ListeSt = new Vector(10);
  public Vector ListeRoadmap = new Vector(10);

  public static Vector ListeBriefs = new Vector(10);

  public Brief(int id){
    this.id = id;
    this.dateProd="";

  }
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
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
    //String req = "SELECT version, description, dateEB, dateMep, idVersionSt, Suivi, Tendance, dateMes, Etat, Panier, idPO, Charge, Ordre, dateT0, dateTest, docEB, docDevis, Annee, MotsClef, dateT0_init, dateEB_init, dateTest_init, dateMep_init, dateMes_init, suiviMOE , dateT0MOE, dateSpecMOE, dateDevMOE, dateTestMOE, dateLivrMOE, dateT0MOE_init, dateSpecMOE_init, dateDevMOE_init, dateTestMOE_init,  dateLivrMOE_init FROM         Roadmap WHERE     (idRoadmap = "+this.id+") ORDER BY Ordre";
    String req = "SELECT     Briefs.idRoadmap, Briefs.version, Briefs.description, Briefs.dateMep, typeEtatBrief.nom AS Etat, Briefs.idPO, Briefs.Ordre, Briefs.standby, ";
    req+="                   Briefs.LoginCreateur, Briefs.dateCreation, Briefs.Cout, Briefs.Qualite, Briefs.Fonctionnalite, Briefs.Delais, Briefs.TypeDemande, Briefs.Contexte, ";
    req+="                   Briefs.ImpactSt, Briefs.ImpactProcessus, Briefs.Gain, Briefs.Manque, Briefs.idEtat, Briefs.Reponse, Briefs.dateCOMAA, Briefs.dateReponse, Briefs.idServiceAffecte, Briefs.idStAffecte, Briefs.idUsineAffecte, Briefs.docEB, Briefs.prepaReponse";
    req+=" FROM         Briefs INNER JOIN";
    req+="                   typeEtatBrief ON Briefs.idEtat = typeEtatBrief.id";
    req+=" WHERE     (idRoadmap = "+this.id+")";
    req+=" ORDER BY typeEtatbrief.Ordre";


    rs = myCnx.ExecReq(st, nomBase, req);
    int nbRoadmap = 0;
    java.util.Date theDate;

    java.util.Date DdateMep;

    try {
      while (rs.next()) {

        version = rs.getString("version");
        oldversion = version;
        description = rs.getString("description");
        if ((this.description != null) &&(this.description.equals("null"))) this.description="";

        DdateMep = rs.getDate("dateMep");
        if (DdateMep != null)
          dateMep = ""+DdateMep.getDate()+"/"+(DdateMep.getMonth()+1) + "/"+(DdateMep.getYear() + 1900);
         else
          dateMep = "";

        Etat = rs.getString("Etat");

        idPO = rs.getString("idPO");
        if ( (idPO == null) || (idPO.equals("0")))
          idPO = "";

        Ordre = rs.getString("Ordre");
        this.standby = rs.getInt("standby");
        this.LoginCreateur = rs.getString("LoginCreateur");
        this.DdateCreation = rs.getDate("dateCreation");
        if (DdateCreation != null)
          this.dateCreation = ""+DdateCreation.getDate()+"/"+(DdateCreation.getMonth()+1) + "/"+(DdateCreation.getYear() + 1900);
         else
          dateCreation = "";

        this.Cout = rs.getInt("Cout");
        this.Qualite = rs.getInt("Qualite");
        this.Fonctionnalite = rs.getInt("Fonctionnalite");
        this.Delais = rs.getInt("Delais");
        this.TypeDemande = rs.getInt("TypeDemande");
        this.Contexte = rs.getString("Contexte");

        this.ImpactSt = rs.getString("ImpactSt");
        this.ImpactProcessus = rs.getString("ImpactProcessus");

        this.Gain = rs.getString("Gain");
        this.Manque = rs.getString("Manque");

        this.idEtat = rs.getInt("idEtat");
        this.Reponse = rs.getString("Reponse");
        if ((this.Reponse == null) || (this.Reponse.equals("null"))) this.Reponse="";

        this.DdateCOMAA = rs.getDate("dateCOMAA");
        if (DdateCOMAA != null)
          dateCOMAA = ""+DdateCOMAA.getDate()+"/"+(DdateCOMAA.getMonth()+1) + "/"+(DdateCOMAA.getYear() + 1900);
         else
          dateCOMAA = "";

        this.DdateReponse = rs.getDate("dateReponse");
        if (DdateReponse != null)
          dateReponse = ""+DdateReponse.getDate()+"/"+(DdateReponse.getMonth()+1) + "/"+(DdateReponse.getYear() + 1900);
         else
          dateReponse = "";

        this.idServiceAffecte= rs.getInt("idServiceAffecte");
        this.idVersionSt= ""+rs.getInt("idStAffecte");
        this.idUsineAffecte= rs.getInt("idUsineAffecte");

        this.docEB = rs.getString("docEB");

        this.prepaReponse = rs.getString("prepaReponse");
        if ((this.prepaReponse == null) || (this.prepaReponse.equals("null"))) this.prepaReponse="";

        this.oldversion = oldversion;
        this.version = version;
        this.oldversion = oldversion;
        this.description = description;
        this.EtatRoadmap = Etat;
        this.idPO = idPO;
        this.dateProd = dateMep;
        
        this.Etat=Etat;


      }
    }

        catch (SQLException s) {
          myCnx.trace("***********", "nbRoadmap", "" + nbRoadmap);
          s.getMessage();
        }


  }

  public static String getListeBriefs(String nomBase,Connexion myCnx, Statement st){
    String req="";
    String ListNomSt="";
    ResultSet rs;

    req="SELECT     Briefs.idRoadmap, Briefs.version, Briefs.LoginCreateur, typeEtatBrief.nom AS Etat, Service_1.nomService AS ServiceAffecte, ";
    req+="                  Service_2.nomService AS UsineAffecte, Service.nomService AS ServiceCreateur";
    req+=" , Briefs.dateCreation, Briefs.dateCOMAA, Briefs.dateReponse";

    req+=" FROM         Briefs INNER JOIN";
    req+="                   typeEtatBrief ON Briefs.idEtat = typeEtatBrief.id INNER JOIN";
    req+="                   Membre ON Briefs.LoginCreateur = Membre.LoginMembre INNER JOIN";
    req+="                   Service ON Membre.serviceMembre = Service.idService LEFT OUTER JOIN";
    req+="                   Service AS Service_2 ON Briefs.idUsineAffecte = Service_2.idService LEFT OUTER JOIN";
    req+="                   Service AS Service_1 ON Briefs.idServiceAffecte = Service_1.idService";
    req+=" ORDER BY Briefs.idRoadmap";



    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    int i=0;
    try {
      while (rs.next()) {
        Brief theBrief = new Brief(rs.getInt("idRoadmap"));
        theBrief.version = rs.getString("version");
        theBrief.LoginCreateur = rs.getString("LoginCreateur");
        theBrief.Etat = rs.getString("Etat");
        theBrief.ServiceAffecte = rs.getString("ServiceAffecte");
        if (theBrief.ServiceAffecte == null) theBrief.ServiceAffecte="";
        theBrief.UsineAffecte = rs.getString("UsineAffecte");
        if (theBrief.UsineAffecte == null) theBrief.UsineAffecte="";

        theBrief.ServiceCreateur = rs.getString("ServiceCreateur");

        try{
          theBrief.dateCreation = rs.getString("dateCreation").substring(0, 10);
        }catch (Exception e){}

        try{
          theBrief.dateCOMAA = rs.getString("dateCOMAA").substring(0, 10);
        }catch (Exception e){}

        try{
          theBrief.dateReponse = rs.getString("dateReponse").substring(0, 10);
        }catch (Exception e){}
        //theSt.dump();


        Brief.ListeBriefs.addElement(theBrief);

        i++;
      }
    }
    catch (Exception s) {
      s.getMessage();
      return req;
    }
    return ListNomSt;

  }
  public String getListeSt(String nomBase,Connexion myCnx, Statement st){
    String ListNomSt="";
    ResultSet rs;

    req="SELECT     BriefSt.id, BriefSt.idVersionSt, BriefSt.idBrief, ListeST.nomSt";
    req+="     FROM         BriefSt INNER JOIN";
    req+="                   ListeST ON BriefSt.idVersionSt = ListeST.idVersionSt";

    req+=" WHERE     (idBrief = "+this.id+")";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    int i=0;
    try {
      while (rs.next()) {
        ST theSt = new ST(rs.getInt("idVersionSt"),"idVersionSt");
        theSt.nomSt = rs.getString("nomSt");
        //theSt.dump();
        this.ListeSt.addElement(theSt);
        if (i == 0)
        {
          ListNomSt += theSt.nomSt;
        }
        else
        {
          ListNomSt += ";" + theSt.nomSt;
        }

        i++;
      }
    }
    catch (Exception s) {
      s.getMessage();
      return req;
    }
    return ListNomSt;

  }

  public void getListeRoadmap(String nomBase,Connexion myCnx, Statement st){
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
    String req = "SELECT    Roadmap.idRoadmap, Roadmap.version, Roadmap.description, Roadmap.dateEB, Roadmap.dateMep, Roadmap.idVersionSt, Roadmap.Tendance, ";
           req+="           Roadmap.dateMes, Roadmap.Etat, Roadmap.Panier, Roadmap.idPO, Roadmap.Charge, Roadmap.Ordre, Roadmap.dateT0, Roadmap.dateTest, ";
           req+="           Roadmap.docEB, Roadmap.docDevis, Roadmap.Annee, Roadmap.MotsClef, Roadmap.dateT0_init, Roadmap.dateEB_init, Roadmap.dateTest_init, ";
           req+="           Roadmap.dateMep_init, Roadmap.dateMes_init, Roadmap.dateT0MOE, Roadmap.dateSpecMOE, Roadmap.dateDevMOE, ";
           req+="           Roadmap.dateTestMOE, Roadmap.dateLivrMOE, Roadmap.dateT0MOE_init, Roadmap.dateSpecMOE_init, Roadmap.dateDevMOE_init, ";
           req+="           Roadmap.dateTestMOE_init, Roadmap.dateLivrMOE_init, ListeST.nomSt, ListeST.isRecurrent, Roadmap.EtatMOE, idEquipe, LF_Month, LF_Year, idMembre, Roadmap.standby, Roadmap.idBrief, Roadmap.isDevis, Roadmap.docExigences";
           req+=" FROM         Roadmap INNER JOIN";
           req+="           ListeST ON Roadmap.idVersionSt = ListeST.idVersionSt";
           req+=" WHERE     (Roadmap.idBrief  = "+this.id+")";
           req+=" AND     (Roadmap.LF_Month = -1)";
           req+=" AND     (Roadmap.LF_Year  = -1)";
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

        Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap"));
        theRoadmap.version = rs.getString("version");

        DdateEB = rs.getDate("dateEB");
        if ((DdateEB != null) && (DdateEB.getYear() > 0))
          theRoadmap.dateEB = ""+DdateEB.getDate()+"/"+(DdateEB.getMonth()+1) + "/"+(DdateEB.getYear() + 1900);
        else
          theRoadmap.dateEB = "-";

        DdateMep = rs.getDate("dateMep");
        if ((DdateMep != null) && (DdateMep.getYear() > 0) )
          theRoadmap.dateProd = ""+DdateMep.getDate()+"/"+(DdateMep.getMonth()+1) + "/"+(DdateMep.getYear() + 1900);
         else
          theRoadmap.dateProd = "-";

        theRoadmap.idVersionSt = rs.getString("idVersionSt");


        DdateMes = rs.getDate("dateMes");
        if ((DdateMes != null) && (DdateMes.getYear() > 0))
            theRoadmap.dateMes =""+DdateMes.getDate()+"/"+(DdateMes.getMonth()+1) + "/"+(DdateMes.getYear() + 1900);
          else
            theRoadmap.dateMes = "-";

        DdateT0 = rs.getDate("dateT0");
        if ((DdateT0 != null) && (DdateT0.getYear() > 0))
          theRoadmap.dateT0 = ""+DdateT0.getDate()+"/"+(DdateT0.getMonth()+1) + "/"+(DdateT0.getYear() + 1900);
         else
          theRoadmap.dateT0 = "-";

        DdateTest = rs.getDate("dateTest");
        if ((DdateTest != null) && (DdateTest.getYear() > 0))
          theRoadmap.dateTest = ""+DdateTest.getDate()+"/"+(DdateTest.getMonth()+1) + "/"+(DdateTest.getYear() + 1900);
         else
          theRoadmap.dateTest = "-";

        theRoadmap.nomSt = rs.getString("nomSt");
        this.ListeRoadmap.addElement(theRoadmap);

      }
    }

        catch (SQLException s) {
          myCnx.trace("***********", "nbRoadmap", "" + nbRoadmap);
          s.getMessage();
        }

  }
  public ErrorSpecific DeleteListeSt(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs = null;

    String req = "DELETE FROM  BriefSt WHERE idBrief = "+this.id;
    //if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"InsertListeSt (delete)",""+this.id);

    return myError;
  }

  public ErrorSpecific InsertListeSt(String nomBase,Connexion myCnx, Statement st, String transaction, String ListeSt){
    ResultSet rs=null;

    ErrorSpecific myError = new ErrorSpecific();
    //this.ListeSt.removeAllElements();
    //System.out.print("--------ListeSt=" +ListeSt);

    myError = DeleteListeSt( nomBase, myCnx,  st,  transaction);
     if (myError.cause == -1) return myError;

       for (StringTokenizer t = new StringTokenizer(ListeSt, ";");
            t.hasMoreTokens(); ) {

         String theTocken = t.nextToken();
         //System.out.print("--------theTocken=" +theTocken);
         req="exec [SP_BriefInsertSt]";
         req+=" @idBrief ='"+this.id+"', ";
         req+=" @idVersionSt ="+theTocken+", ";
         req+=" @nomSt ='"+""+"'";
         //if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
         myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"InsertListeSt (insert)",""+this.id);

     }

     return myError;

  }
  public String getListeDiffusion(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
  String ListeDiffusion = "";

  req = "SELECT     TOP (50) Membre.mail, Service.nomService";
  req += " FROM         Membre INNER JOIN";
  req += "                      Service ON Membre.serviceMembre = Service.idService";
  req += " WHERE     (Membre.listediffusionBrief = 1) AND (Membre.mail <> '') AND (NOT (Service.nomService LIKE '%sorti%'))";
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

  public void getInfoPoClient(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;

  req = " SELECT DISTINCT PlanOperationnelClient.nomProjet";
  req+= "   FROM         PlanOperationnelClient ";
  req+= " WHERE     (PlanOperationnelClient.idWebPo = "+this.idPO+")";

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
try {
  while (rs.next()) {
    this.nomProjetClient = rs.getString("nomProjet");

  }
}
  catch (SQLException s) {s.getMessage();}
  }

public static String searchByVersion(String nomBase,Connexion myCnx, Statement st, String version){

String result="KO";
ResultSet rs;

version = version.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
String req="";
  req = " SELECT     COUNT(*) AS nb";
  req+="    FROM         Briefs";
  req+=" WHERE     (version = '"+version+"')";


  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  int nb = 0;
try {
  while (rs.next()) {
    nb = rs.getInt("nb");

  }
}
  catch (SQLException s) {s.getMessage();}

  if (nb > 0) return "KO";
  else  return "OK";
  }

  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

    if (this.oldversion != null)
      this.oldversion=oldversion.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
    else
      this.oldversion="";

  String version="";
  if (this.version != null)
    version=this.version.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
  else
    version="";

  String Contexte="";
  if (this.Contexte != null)
    Contexte=this.Contexte.replaceAll("\u0092","'").replaceAll("'","''");
  else
    Contexte="";

  String description="";
  if (this.description != null)
    description=this.description.replaceAll("\u0092","'").replaceAll("'","''");
  else
    description="";

  String Gain="";
  if (this.Gain != null)
    Gain=this.Gain.replaceAll("\u0092","'").replaceAll("'","''");
  else
    Gain="";

  String Manque="";
  if (this.Manque != null)
    Manque=this.Manque.replaceAll("\u0092","'").replaceAll("'","''");
  else
    Manque="";

  String ImpactProcessus="";
  if (this.ImpactProcessus != null)
    ImpactProcessus=this.ImpactProcessus.replaceAll("\u0092","'").replaceAll("'","''");
  else
    ImpactProcessus="";

  String ImpactSt="";
  if (this.ImpactSt != null)
    ImpactSt=this.ImpactSt.replaceAll("\u0092","'").replaceAll("'","''");
  else
    ImpactSt="";

  String Reponse="";
  if (this.Reponse != null)
    Reponse=this.Reponse.replaceAll("\u0092","'").replaceAll("'","''");
  else
    Reponse="";

  String prepaReponse="";
  if (this.prepaReponse != null)
    prepaReponse=this.prepaReponse.replaceAll("\u0092","'").replaceAll("'","''");
  else
    prepaReponse="";

    String str_dateProd ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";

    try{
      if ( (dateProd != null) && (!dateProd.equals(""))) {
        Utils.getDate(dateProd);
        str_dateProd = "convert(datetime, '" + Utils.Day + "/" + Utils.Month + "/" +
            Utils.Year + "', 103)";
      }
    } catch (Exception e){}


    String str_dateCreation ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";
    try{
      if ( (this.dateCreation != null) && (!this.dateCreation.equals(""))) {
        Utils.getDate(this.dateCreation);
        str_dateCreation = "convert(datetime, '" + Utils.Day + "/" +
            Utils.Month + "/" + Utils.Year + "', 103)";
      }
    }    catch (Exception e){}

    req = "INSERT Briefs (version, description, dateMep,  idPO, Ordre, standby, LoginCreateur ";
    req+=",";
    req+="Contexte";
    req+=",";
    req+="TypeDemande";
    req+=",";
    req+="Gain";
    req+=",";
    req+="Manque";
    req+=",";
    req+="ImpactProcessus";
    req+=",";
    req+="ImpactSt";
    req+=",";
    req+="Cout";
    req+=",";
    req+="Qualite";
    req+=",";
    req+="Fonctionnalite";
    req+=",";
    req+="Delais";
    req+=",";
    req+="Reponse";
    req+=",";
    req+="docEB";
    req+=",";
    req+="prepaReponse";
    req+="   )";
    req+=" VALUES (";
    req+="'"+version+"',  '"+description+"', "+str_dateProd+",  '"+this.idPO+"',  '"+this.Ordre+"', "+this.standby+"";
    req+=",";
    req+="'"+LoginCreateur+"'";
    //req+=",";
    //req+=""+str_dateCreation+"";

    req+=",";
    req+="'"+Contexte+"'";
    req+=",";
    req+="'"+TypeDemande+"'";
    req+=",";
    req+="'"+Gain+"'";
    req+=",";
    req+="'"+Manque+"'";
    req+=",";
    req+="'"+ImpactProcessus+"'";
    req+=",";
    req+="'"+ImpactSt+"'";
    req+=",";
    req+="'"+Cout+"'";
    req+=",";
    req+="'"+Qualite+"'";
    req+=",";
    req+="'"+Fonctionnalite+"'";
    req+=",";
    req+="'"+Delais+"'";
    req+=",";
    req+="'"+Reponse+"'";
    req+=",";
    req+="'"+this.docEB+"'";
    req+=",";
    req+="'"+prepaReponse+"'";
    req+=")";


    //myCnx.trace("@@456789--------","req",""+req);
      ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);

      //myCnx.trace("@@456789--------","bd_Insert",""+transaction);

      //req = "SELECT idRoadmap FROM  Briefs WHERE   (version = '"+this.version+"')";
      req="SELECT     TOP (1) idRoadmap FROM   Briefs ORDER BY idRoadmap DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt(1);
        } catch (SQLException s) {s.getMessage();}

        this.version=this.version.replaceAll("\"","''");
    return myError;
  }

  public ErrorSpecific bd_InsertProjet(String nomBase,Connexion myCnx, Statement st, String transaction, String ListeSt){

    ErrorSpecific myError = new ErrorSpecific();
    for (StringTokenizer t = new StringTokenizer(ListeSt, ";");
         t.hasMoreTokens(); ) {

      String theTocken = t.nextToken();
      int idVersionSt = Integer.parseInt(theTocken);

      ST theSt= new ST(idVersionSt,"idVersionSt");
      theSt.getAllFromBd( nomBase, myCnx,  st);

      Roadmap theRoadmap = new Roadmap(-1);
      theRoadmap.idVersionSt = ""+idVersionSt;
      theRoadmap.version = this.version;
      theRoadmap.description = this.description;
      theRoadmap.idPO = this.idPO;
      theRoadmap.dateProd = this.dateProd;
      theRoadmap.version = this.version;
      theRoadmap.idBrief = this.id;
      theRoadmap.Panier="checked";
      if (theSt.respMoaVersionSt != -1)
      {
        theRoadmap.idMembre = theSt.respMoaVersionSt;
      }
      else
      {
        theRoadmap.idMembre = theSt.respMoeVersionSt;
      }

      theRoadmap.Tendance="Inconnue";
      myError=theRoadmap.bd_Enreg( nomBase, myCnx,  st,  transaction);

      if (myError.cause == -1) return myError;

     }

    return myError;
  }

  public ErrorSpecific bd_updateStateByAdmin(String nomBase,Connexion myCnx, Statement st, String transaction){
    req = "UPDATE Briefs  SET ";
    req += " idEtat ='" + this.idEtat + "'";
    req += ",";
    req += " idServiceAffecte ='" + this.idServiceAffecte + "'";
    req += ",";
    req += " idUsineAffecte ='" + this.idUsineAffecte + "'";
    req += " WHERE idRoadmap =" + this.id;

    if (myCnx.ExecUpdate(st, nomBase, req, true, transaction) == -1) {
        myCnx.trace(nomBase, "*** ERREUR *** req", req);
    }
    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_updateStateByAdmin",""+this.id);

    return myError;

  }

  public ErrorSpecific bd_UpdateState(String nomBase,Connexion myCnx, Statement st, String transaction, int new_idEtat, String mailEmetteur){
    req = "UPDATE    Briefs  SET ";
    req +=" idEtat ='"+ new_idEtat + "'";
    req += " WHERE idRoadmap ="+ this.id;

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateState",""+this.id);
    if (myError.cause == -1) return myError;


    this.dateCreation=Utils.getToDay(myCnx.nomBase,myCnx,st);
    String str_dateCreation ="";
    try{
        Utils.getDate(this.dateCreation);
        str_dateCreation = "convert(datetime, '" + Utils.Day + "/" +
            Utils.Month + "/" + Utils.Year + "', 103)";

    }    catch (Exception e){}

    this.dateCOMAA =Utils.getToDay(myCnx.nomBase,myCnx,st);
    String  str_dateCOMAA="";
    try{
        Utils.getDate(this.dateCOMAA);
        str_dateCOMAA = "convert(datetime, '" + Utils.Day + "/" +
            Utils.Month + "/" + Utils.Year + "', 103)";
    }    catch (Exception e){}

    this.dateReponse=Utils.getToDay(myCnx.nomBase,myCnx,st);
    String str_dateReponse ="";
    try{
        Utils.getDate(this.dateReponse);
        str_dateReponse = "convert(datetime, '" + Utils.Day + "/" +
            Utils.Month + "/" + Utils.Year + "', 103)";

    }    catch (Exception e){}


// ---------------------------------------- etat = depose -----------------------------------------------//
    if ((new_idEtat == 2) && (new_idEtat != this.idEtat))
    {
      req = "UPDATE    Briefs  SET ";
      req +=" dateCreation ="+ str_dateCreation + "";
      req += " WHERE idRoadmap ="+ this.id;

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateState (-> depose)",""+this.id);
      if (myError.cause == -1) return myError;

    }

// ---------------------------------------- etat = a Completer -----------------------------------------------//
    else if ((new_idEtat == 3) && (new_idEtat != this.idEtat))
    {
      // --- Envoi d'un mail a l'autuer du Brief: Le Brief est pass� en COMAA mais n�cessite d'�tre compl�t�
      req = "UPDATE    Briefs  SET ";
      req +=" dateCOMAA ="+ str_dateCOMAA + "";
      req += " WHERE idRoadmap ="+ this.id;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateState (-> a Completer)",""+this.id);
      if (myError.cause == -1) return myError;

    }

    // ---------------------------------------- etat = Standby -----------------------------------------------//
        else if ((new_idEtat == 8) && (new_idEtat != this.idEtat))
        {
          // --- Envoi d'un mail a l'autuer du Brief: Le Brief est pass� en COMAA mais n�cessite d'�tre compl�t�
          req = "UPDATE    Briefs  SET ";
          req +=" dateCOMAA ="+ str_dateCOMAA + "";
          req += " WHERE idRoadmap ="+ this.id;

          myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateState (-> Standby)",""+this.id);
          if (myError.cause == -1) return myError;

        }
// ---------------------------------------- etat = Pris en compte -----------------------------------------------//

    else if ((new_idEtat == 4) && (new_idEtat != this.idEtat))
    {
      // --- Envoi d'un mail a l'autuer du Brief: Le Brief est pass� en COMAA mais n�cessite d'�tre compl�t�
      req = "UPDATE    Briefs  SET ";
      req +=" dateCOMAA ="+ str_dateCOMAA + "";
      req += " WHERE idRoadmap ="+ this.id;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateState (-> Pris en compte)",""+this.id);
      if (myError.cause == -1) return myError;

    }

// ---------------------------------------- etat = Refuse -----------------------------------------------//
    else if ((new_idEtat == 6) && (new_idEtat != this.idEtat))
    {
      // --- Envoi d'un mail a l'autuer du Brief: Le Brief est pass� en COMAA mais n�cessite d'�tre compl�t�
      req = "UPDATE    Briefs  SET ";
      req +=" dateCOMAA ="+ str_dateCOMAA + "";
      req += " WHERE idRoadmap ="+ this.id;

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateState (-> Refuse)",""+this.id);
      if (myError.cause == -1) return myError;
    }

// ---------------------------------------- etat = Accepte -----------------------------------------------//
    else if ((new_idEtat == 5) && (new_idEtat != this.idEtat))
    {
      req = "UPDATE    Briefs  SET ";
      req +=" dateReponse ="+ str_dateReponse + "";
      req += " WHERE idRoadmap ="+ this.id;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateState (-> Accepte)",""+this.id);
      if (myError.cause == -1) return myError;

    }

    this.idEtat = new_idEtat;
    return myError;
  }
  public ErrorSpecific bd_UpdateState2(String nomBase,Connexion myCnx, Statement st, String transaction, int new_idEtat, String mailEmetteur){
    req = "UPDATE    Briefs  SET ";
    req +=" idEtat ='"+ new_idEtat + "'";
    req += " WHERE idRoadmap ="+ this.id;

    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateState",""+this.id);
    if (myError.cause == -1) return myError;


    this.dateCreation=Utils.getToDay(myCnx.nomBase,myCnx,st);
    String str_dateCreation ="";
    try{
        Utils.getDate(this.dateCreation);
        str_dateCreation = "convert(datetime, '" + Utils.Day + "/" +
            Utils.Month + "/" + Utils.Year + "', 103)";

    }    catch (Exception e){}

    this.dateCOMAA =Utils.getToDay(myCnx.nomBase,myCnx,st);
    String  str_dateCOMAA="";
    try{
        Utils.getDate(this.dateCOMAA);
        str_dateCOMAA = "convert(datetime, '" + Utils.Day + "/" +
            Utils.Month + "/" + Utils.Year + "', 103)";
    }    catch (Exception e){}

    this.dateReponse=Utils.getToDay(myCnx.nomBase,myCnx,st);
    String str_dateReponse ="";
    try{
        Utils.getDate(this.dateReponse);
        str_dateReponse = "convert(datetime, '" + Utils.Day + "/" +
            Utils.Month + "/" + Utils.Year + "', 103)";

    }    catch (Exception e){}

    String objet="";
    String corps="";
    String destinataire="";
    String emetteur="";
    String mailingList=this.getListeDiffusion(myCnx.nomBase, myCnx,  st);

    corps+= "Bonjour";
    corps+= "<br>";

// ---------------------------------------- etat = depose -----------------------------------------------//
    if ((new_idEtat == 2) && (new_idEtat != this.idEtat))
    {
      req = "UPDATE    Briefs  SET ";
      req +=" dateCreation ="+ str_dateCreation + "";
      req += " WHERE idRoadmap ="+ this.id;

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateState (-> depose)",""+this.id);
      if (myError.cause == -1) return myError;
      //-------------- Envoi d'un mail au COMAA: soummision d'un nouveau BRIEF ------------------//
      objet= "[BRIEF][DEPOSE] "+ this.id + "-"+ this.version;
      emetteur = mailEmetteur;
      destinataire =mailingList;

      corps = "Brief Etat DEPOSE";
      corps+= "<br>";
      corps= "Bonjour";
      corps+= "<br>";

      corps += "Je viens de Soumettre le Brief: " + this.id + "-"+ this.version + "<br>"+ "<br>";
      corps += "Cordialement";

      try{
        myCnx.sendmail(emetteur,destinataire,objet,corps);
        //myCnx.trace("@01234***** mail envoye******************","emetteur",emetteur);
        //myCnx.trace("@01234***** mail envoye******************","destinataire",destinataire);
        //myCnx.trace("@01234***** mail envoye******************","objet",objet);
        //myCnx.trace("@01234***** mail envoye******************","corps",corps);
      }
      catch (Exception e)
      {
        myCnx.trace("@01234***********************","mail non envoye, sendMail",myCnx.sendMail);
      }

    }

// ---------------------------------------- etat = a Completer -----------------------------------------------//
    else if ((new_idEtat == 3) && (new_idEtat != this.idEtat))
    {
      // --- Envoi d'un mail a l'autuer du Brief: Le Brief est pass� en COMAA mais n�cessite d'�tre compl�t�
      req = "UPDATE    Briefs  SET ";
      req +=" dateCOMAA ="+ str_dateCOMAA + "";
      req += " WHERE idRoadmap ="+ this.id;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateState (-> a Completer)",""+this.id);
      if (myError.cause == -1) return myError;

      objet= "[BRIEF][A COMPLETER]  "+ this.id + "-"+ this.version;
      emetteur = mailEmetteur;
      destinataire =this.LoginCreateur+"@"+myCnx.mail+";"+"GEJABOUL@"+myCnx.mail;

      corps = "Brief etat A COMPLETER (DEMANDE INFORMATIONS) Complement/action necessaires pour passage en COMAA SIR";
      corps+= "<br>";
      corps += "Bonjour";
      corps+= "<br>";

      corps += this.Reponse+ "<br>"+ "<br>";
      corps += "-  Mail automatique envoye depuis Lixian  - ";

      try{
        myCnx.sendmail(emetteur,destinataire,objet,corps);
       // myCnx.trace("@01234***** mail envoye******************","emetteur",emetteur);
        //myCnx.trace("@01234***** mail envoye******************","destinataire",destinataire);
        //myCnx.trace("@01234***** mail envoye******************","objet",objet);
        //tmyCnx.trace("@01234***** mail envoye******************","corps",corps);
      }
      catch (Exception e)
      {
        myCnx.trace("@01234***********************","mail non envoye, sendMail",myCnx.sendMail);
      }

    }


    // ---------------------------------------- etat = Standby -----------------------------------------------//
        else if ((new_idEtat == 8) && (new_idEtat != this.idEtat))
        {
          // --- Envoi d'un mail a l'autuer du Brief: Le Brief est pass� en COMAA mais n�cessite d'�tre compl�t�
          req = "UPDATE    Briefs  SET ";
          req +=" dateCOMAA ="+ str_dateCOMAA + "";
          req += " WHERE idRoadmap ="+ this.id;

          myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateState (-> Standby)",""+this.id);
          if (myError.cause == -1) return myError;

          objet= "[BRIEF][STANDBY]  "+ this.id + "-"+ this.version;
          emetteur = mailEmetteur;
          destinataire =this.LoginCreateur+"@"+myCnx.mail+";"+"GEJABOUL@"+myCnx.mail;

          corps = "Brief Etat STANDBY";
          corps+= "<br>";
          corps += "Bonjour";
          corps+= "<br>";

          corps += this.Reponse+ "<br>"+ "<br>";
          corps += "-  Mail automatique envoye depuis Lixian  - ";

          try{
            myCnx.sendmail(emetteur,destinataire,objet,corps);
            //myCnx.trace("@01234***** mail envoye******************","emetteur",emetteur);
            //myCnx.trace("@01234***** mail envoye******************","destinataire",destinataire);
            //myCnx.trace("@01234***** mail envoye******************","objet",objet);
            //myCnx.trace("@01234***** mail envoye******************","corps",corps);
          }
          catch (Exception e)
          {
            myCnx.trace("@01234***********************","mail non envoye, sendMail",myCnx.sendMail);
          }

        }
// ---------------------------------------- etat = Pris en compte -----------------------------------------------//

    else if ((new_idEtat == 4) && (new_idEtat != this.idEtat))
    {
      // --- Envoi d'un mail a l'autuer du Brief: Le Brief est pass� en COMAA mais n�cessite d'�tre compl�t�
      req = "UPDATE    Briefs  SET ";
      req +=" dateCOMAA ="+ str_dateCOMAA + "";
      req += " WHERE idRoadmap ="+ this.id;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateState (-> Pris en compte)",""+this.id);
      if (myError.cause == -1) return myError;

      // ----  Envoi d'un mail a l'auteur du Brief: Le Brief a ete pris en compte
      objet= "[BRIEF][PRIS EN COMPTE]  "+ this.id + "-"+ this.version;
      emetteur = mailEmetteur;
      destinataire =this.LoginCreateur+"@"+myCnx.mail+";"+"GEJABOUL@"+myCnx.mail;

      corps = "Brief Etat PRIS EN COMPTE  details et consignes acceptation DPS a venir";
      corps+= "<br>";
      corps= "Bonjour";
      corps+= "<br>";

      corps += this.Reponse+ "<br>"+ "<br>";;
      corps += "-  Mail automatique envoye depuis Lixian  - ";

      try{
        //myCnx.sendmail(emetteur,destinataire,objet,corps);
        //myCnx.trace("@01234***** mail envoye******************","emetteur",emetteur);
        //myCnx.trace("@01234***** mail envoye******************","destinataire",destinataire);
        //myCnx.trace("@01234***** mail envoye******************","objet",objet);
        //myCnx.trace("@01234***** mail envoye******************","corps",corps);
      }
      catch (Exception e)
      {
        myCnx.trace("@01234***********************","mail non envoye, sendMail",myCnx.sendMail);
      }
      // ------------------------------------------------------------------------------------
    }

// ---------------------------------------- etat = Refuse -----------------------------------------------//
    else if ((new_idEtat == 6) && (new_idEtat != this.idEtat))
    {
      // --- Envoi d'un mail a l'autuer du Brief: Le Brief est pass� en COMAA mais n�cessite d'�tre compl�t�
      req = "UPDATE    Briefs  SET ";
      req +=" dateCOMAA ="+ str_dateCOMAA + "";
      req += " WHERE idRoadmap ="+ this.id;

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateState (-> Refuse)",""+this.id);
      if (myError.cause == -1) return myError;

      // ----  Envoi d'un mail a l'auteur du Brief: Le Brief a ete refuse
      objet= "[BRIEF][REFUS�]  "+ this.id + "-"+ this.version;
      emetteur = mailEmetteur;
      destinataire =this.LoginCreateur+"@"+myCnx.mail+";"+"GEJABOUL@"+myCnx.mail;

      corps = "Brief Etat REFUS�";
      corps+= "<br>";
      corps= "Bonjour";
      corps+= "<br>";
      corps += this.Reponse+ "<br>"+ "<br>";;
      corps += "-  Mail automatique envoye depuis Lixian  - ";

      try{
        myCnx.sendmail(emetteur,destinataire,objet,corps);
       // myCnx.trace("@01234***** mail envoye******************","emetteur",emetteur);
       // myCnx.trace("@01234***** mail envoye******************","destinataire",destinataire);
        //myCnx.trace("@01234***** mail envoye******************","objet",objet);
        //myCnx.trace("@01234***** mail envoye******************","corps",corps);
      }
      catch (Exception e)
      {
        myCnx.trace("@01234***********************","mail non envoye, sendMail",myCnx.sendMail);
      }
      // ------------------------------------------------------------------------------------
    }

// ---------------------------------------- etat = Accepte -----------------------------------------------//
    else if ((new_idEtat == 5) && (new_idEtat != this.idEtat))
    {
      req = "UPDATE    Briefs  SET ";
      req +=" dateReponse ="+ str_dateReponse + "";
      req += " WHERE idRoadmap ="+ this.id;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateState (-> Accepte)",""+this.id);
      if (myError.cause == -1) return myError;

      // ----  Envoi d'un mail a l'auteur du Brief: Le Brief a ete affecte � une MOE qui s'engage par une reponse
      objet= "[BRIEF][ACCEPT�]  "+ this.id + "-"+ this.version;
      emetteur = mailEmetteur;
      destinataire =this.LoginCreateur+"@"+myCnx.mail+";"+"GEJABOUL@"+myCnx.mail;

      corps = "Brief Etat ACCEPTE (DEMARRAGE)";
      corps+= "<br>";
      corps= "Bonjour";
      corps+= "<br>";
      corps += this.Reponse+ "<br>"+ "<br>";;
      corps += "-  Mail automatique envoye depuis Lixian  - ";

      try{
        myCnx.sendmail(emetteur,destinataire,objet,corps);
        //myCnx.trace("@01234***** mail envoye******************","emetteur",emetteur);
        //myCnx.trace("@01234***** mail envoye******************","destinataire",destinataire);
        //myCnx.trace("@01234***** mail envoye******************","objet",objet);
        //myCnx.trace("@01234***** mail envoye******************","corps",corps);
      }
      catch (Exception e)
      {
        myCnx.trace("@01234***********************","mail non envoye, sendMail",myCnx.sendMail);
      }
      // ------------------------------------------------------------------------------------
    }

    this.idEtat = new_idEtat;
    return myError;
  }
  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){

    if (this.oldversion != null)
      this.oldversion=oldversion.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
    else
      this.oldversion="";

  String version="";
  if (this.version != null)
    version=this.version.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
  else
    version="";

  String Contexte="";
  if (this.Contexte != null)
    Contexte=this.Contexte.replaceAll("\u0092","'").replaceAll("'","''");
  else
    Contexte="";

  String description="";
  if (this.description != null)
    description=this.description.replaceAll("\u0092","'").replaceAll("'","''");
  else
    description="";

  String Gain="";
  if (this.Gain != null)
    Gain=this.Gain.replaceAll("\u0092","'").replaceAll("'","''");
  else
    Gain="";

  String Manque="";
  if (this.Manque != null)
    Manque=this.Manque.replaceAll("\u0092","'").replaceAll("'","''");
  else
    Manque="";

  String ImpactProcessus="";
  if (this.ImpactProcessus != null)
    ImpactProcessus=this.ImpactProcessus.replaceAll("\u0092","'").replaceAll("'","''");
  else
    ImpactProcessus="";

  String ImpactSt="";
  if (this.ImpactSt != null)
    ImpactSt=this.ImpactSt.replaceAll("\u0092","'").replaceAll("'","''");
  else
    ImpactSt="";

  String Reponse="";
  if (this.Reponse != null)
    Reponse=this.Reponse.replaceAll("\u0092","'").replaceAll("'","''");
  else
    Reponse="";

  String prepaReponse="";
  if (this.prepaReponse != null)
    prepaReponse=this.prepaReponse.replaceAll("\u0092","'").replaceAll("'","''");
  else
    prepaReponse="";

    String str_dateProd ="convert(datetime, '"+"01"+"/"+"01"+"/"+"1900"+"', 103)";

    try{
      if ( (dateProd != null) && (!dateProd.equals(""))) {
        Utils.getDate(dateProd);
        str_dateProd = "convert(datetime, '" + Utils.Day + "/" + Utils.Month + "/" +
            Utils.Year + "', 103)";
      }
    } catch (Exception e){}


    req = "UPDATE    Briefs  SET ";
    req +=" version ='"+ version + "', ";
    req +=" description ='"+ description + "', ";
    req +=" dateMep ="+ str_dateProd + ", ";
    //req +=" Etat ='"+ this.EtatRoadmap + "', ";
    req +=" idPO ='"+ this.idPO + "', ";
    req +=" Ordre ='"+ this.Ordre + "', ";
    req +=" standby ="+ this.standby + ",";
    req +=" LoginCreateur ='"+ this.LoginCreateur + "', ";
    req +=" Contexte ='"+ Contexte + "', ";
    req +=" TypeDemande ='"+ this.TypeDemande + "', ";
    req +=" Gain ='"+ Gain + "', ";
    req +=" Manque ='"+ Manque + "', ";
    req +=" ImpactProcessus ='"+ ImpactProcessus + "', ";
    req +=" ImpactSt ='"+ ImpactSt + "', ";
    req +=" Cout ='"+ this.Cout + "', ";
    req +=" Qualite ='"+ this.Qualite + "', ";
    req +=" Fonctionnalite ='"+ this.Fonctionnalite + "', ";
    req +=" Delais ='"+ this.Delais + "', ";
    req +=" Reponse ='"+ Reponse + "', ";
    req +=" idServiceAffecte ="+ this.idServiceAffecte + ", ";
    req +=" idStAffecte ="+ this.idVersionSt + ", ";
    req +=" idUsineAffecte ="+ this.idUsineAffecte + ", ";
    req +=" docEB ='"+ this.docEB + "', ";
    req +=" prepaReponse ='"+ prepaReponse + "'";

    req += " WHERE idRoadmap ="+ this.id;

    this.version=this.version.replaceAll("\"","''");
    return myCnx.ExecUpdateTransaction(st, nomBase, req, true, transaction, getClass().getName(),"bd_Update",""+this.id);

  }

  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){

    req = "DELETE FROM Briefs WHERE (idRoadmap  = '"+this.id+"')";
    ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
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

    Brief theRoadmap = new Brief(56);
    theRoadmap.version = version;
    theRoadmap.description = description;

    theRoadmap.getAllFromBd(myCnx.nomBase, myCnx, st);
    //theRoadmap.dump();

    //theRoadmap.bd_Insert(myCnx.nomBase, myCnx, st, "EnregBrief");
    //theRoadmap.id=1;
    //theRoadmap.bd_Update(myCnx.nomBase, myCnx, st, "EnregBrief");

    //theRoadmap.getListeSt(myCnx.nomBase, myCnx, st);

    Brief.getListeBriefs(myCnx.nomBase, myCnx, st);


  myCnx.Unconnect(st2);
  myCnx.Unconnect(st);

  }
}

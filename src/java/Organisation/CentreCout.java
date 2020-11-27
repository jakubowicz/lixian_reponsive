package Organisation; 

import accesbase.Connexion;
import java.util.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Projet.Roadmap;
import java.util.Vector;
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
public class CentreCout {
  public int id = -1;
  public String nom = "";
  public String desc = "";
  private String req="";
  public Vector ListeProjets = new Vector(10);
  public float nb_internes=0;
  public float nb_externes=0;
  public float nb_joursMoyens=0;
  public float nb_joursTotal=0;

  public float totalChargePrevue=0;
  public float totalChargeConsommee=0;
  public float TotalchargeReestimee=0;

  public CentreCout(String nom) {
    this.nom = nom;
  }

  public float getNbEtp(String nomBase,Connexion myCnx, Statement st){

    float nb=0;

    req="SELECT     SUM(Membre.etp) AS nbEtp";
    req+="    FROM         Membre INNER JOIN";
    req+="                   Service ON Membre.serviceMembre = Service.idService";
    req+=" WHERE     (Service.nomServiceImputations LIKE '%"+this.nom+"%')";


    ResultSet rs;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        nb= rs.getFloat("nbEtp");

      }
    }
 catch (SQLException s) { }

    return nb;

  }

  public float getNbJoursEtp(String nomBase,Connexion myCnx, Statement st){

    ResultSet rs;

    req="SELECT     Membre.etp";
    req+=" FROM         Service INNER JOIN";
    req+=" Membre ON Service.idService = Membre.serviceMembre";
    req+=" WHERE     (Service.nomServiceImputations LIKE '%"+this.nom+"%') AND (Membre.societeMembre = 0)";



    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        float etp = rs.getFloat("etp");
        this.nb_internes+=etp;
      }
    }
 catch (SQLException s) { }

    req="SELECT     Membre.etp";
    req+=" FROM         Service INNER JOIN";
    req+=" Membre ON Service.idService = Membre.serviceMembre";
    req+=" WHERE     (Service.nomServiceImputations LIKE '%"+this.nom+"%') AND (Membre.societeMembre <> 0)";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        float etp = rs.getFloat("etp");
        this.nb_externes+=etp;
      }
    }
 catch (SQLException s) { }

    this.nb_joursTotal = Service.nbJoursInternes * nb_internes + Service.nbJoursExternes * nb_externes;
    this.nb_joursMoyens = (this.nb_internes * Service.nbJoursInternes)/(this.nb_internes + this.nb_externes) + (this.nb_externes * Service.nbJoursExternes)/(this.nb_internes + this.nb_externes);

    return this.nb_joursTotal;

  }
  public float getRAFGlobal(String nomBase,Connexion myCnx, Statement st , int anneeRef, Date dateExtraction, String nomServiceImputations){
           int semaine = Utils.getWeek(nomBase,myCnx, st,dateExtraction.getDate(),dateExtraction.getMonth()+1,dateExtraction.getYear()+1900);
           ResultSet rs;

           req="          SELECT     SUM(PlanDeCharge.Charge) AS total";
           req+="     FROM         Roadmap INNER JOIN";
           req+="             PlanDeCharge ON Roadmap.idRoadmap = PlanDeCharge.idRoadmap INNER JOIN";
           req+="             Membre ON PlanDeCharge.projetMembre = Membre.idMembre INNER JOIN";
           req+="             Service ON Membre.serviceMembre = Service.idService";
           req+="  WHERE     (PlanDeCharge.anneeRef = "+anneeRef+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND ";
           req+="             (PlanDeCharge.semaine > "+semaine+") AND (Membre.etp > 0)";
           req+="  AND      (Service.nomServiceImputations LIKE '%"+this.nom+"%') AND   (Roadmap.idPO > 0)";

           req+="  AND  Roadmap.idPO";
           req+="  IN (";
           req+="       SELECT DISTINCT idWebPo";
           req+="               FROM         PlanOperationnelClient";
           req+="       WHERE     (Service.nomServiceImputations LIKE '%"+this.nom+"%') AND (Annee = "+anneeRef+")";
           req+="       )";



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
  

  public void getTotalChargeProjetsClients(String nomBase,Connexion myCnx, Statement st, String AnneeRef){

    req = "SELECT     SUM(PlanOperationnelClient.charge) AS chargesPrevues, SUM(PlanOperationnelClient.chargeConsommee) AS chargesConsommees";
    req+="    FROM         PlanOperationnelClient INNER JOIN";
    req+="                    Service ON PlanOperationnelClient.Service = Service.nomServiceImputations";
    req+=" WHERE     (Service.nomServiceImputations LIKE '%"+this.nom+"%')  AND (PlanOperationnelClient.Annee = "+AnneeRef+")";


    ResultSet rs;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        this.totalChargePrevue = rs.getFloat("chargesPrevues");
        this.totalChargeConsommee = rs.getFloat("chargesConsommees");
      }
    }
 catch (SQLException s) { }

 }

 public void getTotalChargeReestimeesProjetsClients(String nomBase,Connexion myCnx, Statement st, String AnneeRef){

   req = "SELECT     SUM(ChiffrageLF.chargeReestimee) AS TotalchargeReestimee";
   req+="     FROM         ChiffrageLF INNER JOIN";
   req+="                    Service ON ChiffrageLF.nomService = Service.nomServiceImputations";
       req+=" WHERE     (Service.nomServiceImputations LIKE '%"+this.nom+"%') AND (ChiffrageLF.Annee = "+AnneeRef+")";


   ResultSet rs;

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
       this.TotalchargeReestimee = rs.getInt("TotalchargeReestimee");

     }
   }
catch (SQLException s) { }

 }
  public void getListeProjetsClients(String nomBase,Connexion myCnx, Statement st, String AnneeRef, int LF){
    req = "SELECT     PlanOperationnelClient.idWebPo, PlanOperationnelClient.nomProjet, SUM(PlanOperationnelClient.charge) AS totalCharge, ";
    req+="                  SUM(PlanOperationnelClient.chargeConsommee) AS totalChargeConso, PlanOperationnelClient.codeSujet, PlanOperationnelClient.Part, ";
    req+="                   PlanOperationnelClient.TypeCharge, PlanOperationnelClient.dateExtraction";
    req+="                  ,SUM(PlanOperationnelClient.depensePrevue) AS totaldepensePrevue, SUM(PlanOperationnelClient.depenseConsommee) AS totaldepenseConsommee";
    req+=" FROM         PlanOperationnelClient INNER JOIN";
    req+="                   Service ON PlanOperationnelClient.Service = Service.nomServiceImputations";
    req+=" WHERE     (Service.nomServiceImputations LIKE '%"+this.nom+"%')";
    req+=" GROUP BY PlanOperationnelClient.idWebPo, PlanOperationnelClient.nomProjet, PlanOperationnelClient.codeSujet, PlanOperationnelClient.Part, ";
    req+="                   PlanOperationnelClient.TypeCharge, PlanOperationnelClient.dateExtraction, PlanOperationnelClient.Annee, ";
    req+="                   PlanOperationnelClient.dateExtraction";
    req+=" HAVING      (PlanOperationnelClient.Annee = "+AnneeRef+")";


    ResultSet rs;
    String nomSt="";
    String version="";
    String nomProjet="";
    String Tendance = "";
    String Etat = "";

    Date theDate = null;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
         Roadmap theRoadmap = new Roadmap(-1);
         theRoadmap.idPO = rs.getString("idWebPo");
         theRoadmap.nomProjet = rs.getString("nomProjet");
         //theRoadmap.EtatRoadmap = rs.getString("etat");
         theRoadmap.Charge = ""+rs.getFloat("totalCharge");
         theRoadmap.chargeConsommee = ""+rs.getFloat("totalChargeConso");
         theRoadmap.codeSujet = rs.getString("codeSujet");
         theRoadmap.part = rs.getString("Part");
         theRoadmap.TypeCharge = rs.getString("TypeCharge");
         theRoadmap.ChargePrevueForfait = rs.getFloat("totaldepensePrevue");
         theRoadmap.chargeConsommeForfait = rs.getFloat("totaldepenseConsommee");         

         //myCnx.trace("@@99------","nomProjet/Charge/chargeConsommee",""+theRoadmap.nomProjet+"/"+theRoadmap.Charge+"/"+theRoadmap.chargeConsommee);
         this.ListeProjets.addElement(theRoadmap);
      }
    }
 catch (SQLException s) { }
 }

 public void getListeProjetsClientsSimule(String nomBase,Connexion myCnx, Statement st, String AnneeRef, int LF){
   req = "SELECT     PlanOperationnelClient.idWebPo, PlanOperationnelClient.nomProjet, SUM(PlanOperationnelClient.charge) AS totalCharge, ";
   req+="                  SUM(PlanOperationnelClient.chargeConsommee) AS totalChargeConso, PlanOperationnelClient.codeSujet, PlanOperationnelClient.Part, ";
   req+="                   PlanOperationnelClient.TypeCharge, PlanOperationnelClient.dateExtraction";
   req+=" FROM         PlanOperationnelClient INNER JOIN";
   req+="                   Service ON PlanOperationnelClient.Service = Service.nomServiceImputations";
   req+=" WHERE     (Service.nomServiceImputations LIKE '%"+this.nom+"%')";
   req+=" GROUP BY PlanOperationnelClient.idWebPo, PlanOperationnelClient.nomProjet, PlanOperationnelClient.codeSujet, PlanOperationnelClient.Part, ";
   req+="                   PlanOperationnelClient.TypeCharge, PlanOperationnelClient.dateExtraction, PlanOperationnelClient.Annee, ";
   req+="                   PlanOperationnelClient.dateExtraction";
   req+=" HAVING      (PlanOperationnelClient.Annee = "+AnneeRef+")";


   ResultSet rs;
   String nomSt="";
   String version="";
   String nomProjet="";
   String Tendance = "";
   String Etat = "";

   Date theDate = null;

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
        Roadmap theRoadmap = new Roadmap(-1);
        theRoadmap.idPO = rs.getString("idWebPo");
        theRoadmap.nomProjet = rs.getString("nomProjet");
        //theRoadmap.EtatRoadmap = rs.getString("etat");
        theRoadmap.Charge = ""+rs.getFloat("totalCharge");
        theRoadmap.chargeConsommee = ""+rs.getFloat("totalChargeConso");
        theRoadmap.codeSujet = rs.getString("codeSujet");
        theRoadmap.part = rs.getString("Part");
        theRoadmap.TypeCharge = rs.getString("TypeCharge");

        //myCnx.trace("@@99------","nomProjet/Charge/chargeConsommee",""+theRoadmap.nomProjet+"/"+theRoadmap.Charge+"/"+theRoadmap.chargeConsommee);
        this.ListeProjets.addElement(theRoadmap);
     }
   }
catch (SQLException s) { }
 }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("desc="+this.desc);
    System.out.println("==================================================");
  }

  public static void main(String[] args) {
    CentreCout centrecout = new CentreCout("");
  }
}

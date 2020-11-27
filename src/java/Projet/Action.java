package Projet; 

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
import Organisation.Collaborateur;
import java.sql.Statement;
import accesbase.Connexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import ST.ST;
import accesbase.ErrorSpecific;
import java.util.StringTokenizer;
import java.util.Vector;
import General.Utils;
import java.util.Calendar;

public class Action {
  public int  id;
  public String nom="";
  public String reponse="";
  public String acteur="";
  public int idEtat=-1;
  public int old_idEtat=-1;
  public int  idRoadmap;
  public int  idRoadmapLie = -1;


  public float totalReparti = 0;

  public String annee_start;
  public String  date_start="";
  public Date Ddate_start=null;
  public int week_start =0;
  public int A_week_start =0;

  public String annee_end;
  public int week_end =0;
  public int A_week_end =0;
  public String  date_end ="";
  public Date Ddate_end=null;

  public String annee_start_init="";
  public Date Ddate_start_init=null;
  public String  date_start_init="";
  public int week_start_init =0;
  public int A_week_start_init =0;

  public String annee_end_init;
  public Date Ddate_end_init=null;  
  public int week_end_init =0;
  public int A_week_end_init =0;
  public String  date_end_init="";

  public String  dateCloture;
  public Date DdateCloture=null;
  public String  nomEtat;
  public Collaborateur  respMOA;
  public String  projet;
  public int  isPlanning=0;
  public String doc = "";
  public String ListeResp = "";
  public String Code = "";

  public float ChargePrevue=0;
  public float ChargeDevis=0;
  public int Semaine=0;
  public float RAF=0;

  public String LoginEmetteur="";

  public Vector ListeCharges = new Vector(10);
  public Vector ListeDependance = new Vector(10);

  public int priorite=0;

  public Date datePrevue = null;
  public Date dateRelle = null;


  private String req;

  // ---------------------------------------------------------------------------------------- //
  public float X_T0=0;
  public float X_END=0;
  
  public int x0 = 0;
  public int x1 = 0;
  public int y0 = 0;
  public int y1 = 0;

  public String dateT0 = "";
  public String dateEND = "";

  public boolean traversantPrecedent = false;
  public boolean traversantSuivant = false;

  public String MOA="";
  public String Type="";
  public boolean visible = true;
  
  public static int chargeStandard = 3;
  public static int dureeStandard= 1000 * 3600 * 24 *5; 
  
  public int isRecurrent=0;
  // ---------------------------------------------------------------------------------------- //

  public Action(int  id, String nom, String acteur, int idEtat, int idRoadmap, String theDate, String theDateEnd, String reponse) {
    this.id = id;
    this.nom = nom;
    this.acteur = acteur;
    this.idEtat = idEtat;
    this.idRoadmap = idRoadmap;
    this.date_start = theDate;
    this.date_end = theDateEnd;
    this.reponse = reponse;

  } 

  public Action(int  id) {
    this.id = id;

  }

  public void setDates(String nomBase,Connexion myCnx, Statement st){

    String jour_start = "";
    String mois_start = "";
    this.annee_start = "";
    if ((date_start != null) && (!date_start.equals("null")) &&(!date_start.equals("")) )
    {
     Utils.getDate(date_start);
     jour_start = Utils.Day;
     mois_start = Utils.Month;
     annee_start = Utils.Year;
     if ((jour_start != null) && (mois_start != null) ) this.week_start = Utils.getWeek(nomBase,myCnx, st,Integer.parseInt(jour_start),Integer.parseInt(mois_start),Integer.parseInt(annee_start));
     if (this.annee_start.equals("1900") || this.annee_start.equals("1995")) this.week_start=0;
     this.A_week_start = Integer.parseInt(this.annee_start) * 100 + this.week_start;

     }
    if ((this.week_start ==0) && (this.week_start >=8)) this.week_start = this.week_start - 8;
    if (this.week_start == 0) this.annee_start = "0";

    String jour_end = "";
    String mois_end = "";
    this.annee_end = "";
    if ((date_end != null) && (!date_end.equals("null")) &&(!date_end.equals("")) )
    {
     Utils.getDate(date_end);
     jour_end = Utils.Day;
     mois_end = Utils.Month;
     annee_end = Utils.Year;
     if ((jour_end != null) && (mois_end != null) ) this.week_end = Utils.getWeek(nomBase,myCnx, st,Integer.parseInt(jour_end),Integer.parseInt(mois_end),Integer.parseInt(annee_end));
     if (this.annee_end.equals("1900") || this.annee_end.equals("1995")) this.week_end=0;
     this.A_week_end = Integer.parseInt(this.annee_end) * 100 + this.week_end;

     }
    if ((this.week_end ==0) && (this.week_end >=8)) this.week_end = this.week_end - 8;
    if (this.week_end == 0) this.annee_end = "0";

  }

  public void setDatesPlanning(int AnneeRef, int mois_start ){

    try
    {
      Utils.getDate( this.date_start);

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
      Utils.getDate( this.date_end);

      if ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) >= (AnneeRef*12 + mois_start))
      {
        this.X_END = (Integer.parseInt(Utils.Month) +12 * (Integer.parseInt(Utils.Year) - AnneeRef) - mois_start) + Float.parseFloat(Utils.Day) / 31;
        if (this.X_END > 12) this.X_END = 12;
      }
      else  if  ((Integer.parseInt(Utils.Year)*12 + Integer.parseInt(Utils.Month)) < (AnneeRef*12 + mois_start))
      {
        X_END = 0;
      }
      else
      X_END = 12;
    }
    catch (Exception e)
    {
      X_END = -1;
    }
    //System.out.println("dateEB="+dateEB);


    }


  public String sendMail(String nomBase,Connexion myCnx, Statement st, String transaction, int old_state, String mailEmetteur, String mailRespRoadmap){

    String objet="";
    String corps="";
    String destinataire="";
    String emetteur="";
    String mailingList="";

    Collaborateur  CollaborateurEmetteur = null;

    if ((this.LoginEmetteur != null) && (!this.LoginEmetteur.equals("")))
    {
      CollaborateurEmetteur = new Collaborateur(this.LoginEmetteur);
      CollaborateurEmetteur.getAllFromBd(nomBase,myCnx,st);
    }

    corps+= "Bonjour";
    corps+= "<br>";

    //myCnx.trace("0----------","mailEmetteur",""+mailEmetteur);
    //myCnx.trace("@@01234----------","old_state",""+old_state);
    //myCnx.trace("@@01234----------","this.idEtat",""+this.idEtat);
// ---------------------------------------- etat = inexistant -----------------------------------------------//
 if (old_state == -1)
 {
   if (this.idEtat == 1) {                 // Nouvel �tat = AF (a faire)

     //-------------- Envoi d'un mail au COMAA: soummision d'un nouveau BRIEF ------------------//
     objet = "[TACHE][a FAIRE] " + this.idRoadmap + "-" + this.id+ "-" + this.Code;
     emetteur = mailRespRoadmap;
     if ((this.LoginEmetteur != null) && (!this.LoginEmetteur.equals("")))
       emetteur = CollaborateurEmetteur.mail;
     else
        emetteur = mailRespRoadmap;
     destinataire = this.getListeDiffusion(nomBase,myCnx,st);

     corps = "Nouvelle Tache a Faire";
     corps += "<br>";
     corps = "Bonjour";
     corps += "<br>";

     corps += "Je viens de Soumettre la Tache: " + this.idRoadmap + "-" + this.id+ "-" + this.Code;
     corps +=    "<br>";
     corps += ""+this.nom;
     corps +=    "<br>" + "<br>";
     corps += "Cordialement";

     try {
       myCnx.sendmail(emetteur, destinataire, objet, corps);
      // myCnx.trace("@01234***** mail envoye******************", "emetteur",                   emetteur);
      // myCnx.trace("@01234***** mail envoye******************", "destinataire",                   destinataire);
      // myCnx.trace("@01234***** mail envoye******************", "objet", objet);
      // myCnx.trace("@01234***** mail envoye******************", "corps", corps);
     }
     catch (Exception e) {
       myCnx.trace("@01234***********************", "mail non envoy�, sendMail",
                   myCnx.sendMail);
     }

   }
 }

 // ---------------------------------------- etat = a Faire -----------------------------------------------//
  if (old_state == 1)
  {
    if (this.idEtat == 5) {                 // Nouvel �tat = Pec (Pris en Compte)

      //-------------- Envoi d'un mail au COMAA: soummision d'un nouveau BRIEF ------------------//
      objet = "[TACHE][Prise en Compte] " + this.idRoadmap + "-" + this.id+ "-" + this.Code;
      emetteur = mailEmetteur;

      if ((this.LoginEmetteur != null) && (!this.LoginEmetteur.equals("")))
        destinataire = CollaborateurEmetteur.mail;
      else
        destinataire = mailRespRoadmap;

      corps = "Tache prise en Compte";
      corps += "<br>";
      corps = "Bonjour";
      corps += "<br>";

      corps += "Je viens de Prendre en compte la Tache: " + this.idRoadmap + "-" + this.id+ "-" + this.Code;
          corps +=    "<br>";
          corps += ""+this.reponse;
          corps +=    "<br>" + "<br>";
     corps += "Cordialement";

      try {
        myCnx.sendmail(emetteur, destinataire, objet, corps);
        //myCnx.trace("@01234***** mail envoye******************", "emetteur",                    emetteur);
        //myCnx.trace("@01234***** mail envoye******************", "destinataire",                    destinataire);
        //myCnx.trace("@01234***** mail envoye******************", "objet", objet);
        //myCnx.trace("@01234***** mail envoye******************", "corps", corps);
      }
      catch (Exception e) {
        myCnx.trace("@01234***********************",
                    "mail non envoy�, sendMail",
                    myCnx.sendMail);
      }




  }
 }

 // ---------------------------------------- etat = Prise en Compte -----------------------------------------------//
  if (old_state == 5)
  {
    if (this.idEtat == 2) {                 // Nouvel �tat = En cours

      //-------------- Envoi d'un mail au COMAA: soummision d'un nouveau BRIEF ------------------//
      objet = "[TACHE][En Cours] " + this.idRoadmap + "-" + this.id+ "-" + this.Code;
      emetteur = mailEmetteur;
      if ((this.LoginEmetteur != null) && (!this.LoginEmetteur.equals("")))
        destinataire = CollaborateurEmetteur.mail;
      else
        destinataire = mailRespRoadmap;

      corps = "Tache en cours";
      corps += "<br>";
      corps = "Bonjour";
      corps += "<br>";

      corps += "Je viens de D�marrer la Tache: " + this.idRoadmap + "-" + this.id+ "-" + this.Code;
          corps +=    "<br>";
          corps += ""+this.reponse;
          corps +=    "<br>" + "<br>";

      try {
        myCnx.sendmail(emetteur, destinataire, objet, corps);
        //myCnx.trace("@01234***** mail envoye******************", "emetteur",                emetteur);
        //myCnx.trace("@01234***** mail envoye******************", "destinataire",                    destinataire);
        //myCnx.trace("@01234***** mail envoye******************", "objet", objet);
        //myCnx.trace("@01234***** mail envoye******************", "corps", corps);
      }
      catch (Exception e) {
        myCnx.trace("@01234***********************",
                    "mail non envoy�, sendMail",
                    myCnx.sendMail);
      }


  }
 }

 // ---------------------------------------- etat = En cours -----------------------------------------------//
  if (old_state == 2)
  {
    if (this.idEtat == 3) {                 // Nouvel �tat = Traite

      //-------------- Envoi d'un mail au COMAA: soummision d'un nouveau BRIEF ------------------//
      objet = "[TACHE][TRAITE] " + this.idRoadmap + "-" + this.id+ "-" + this.Code;
      emetteur = mailEmetteur;
      if ((this.LoginEmetteur != null) && (!this.LoginEmetteur.equals("")))
        destinataire = CollaborateurEmetteur.mail;
      else
        destinataire = mailRespRoadmap;

      corps = "Tache Close";
      corps += "<br>";
      corps = "Bonjour";
      corps += "<br>";

      corps += "Je viens de Traiter la Tache: " + this.idRoadmap + "-" + this.id+ "-" + this.Code;
          corps +=    "<br>";
          corps += ""+this.reponse;
          corps +=    "<br>" + "<br>";

      try {
        myCnx.sendmail(emetteur, destinataire, objet, corps);
        //myCnx.trace("@01234***** mail envoye******************", "emetteur",                    emetteur);
        //myCnx.trace("@01234***** mail envoye******************", "destinataire",                    destinataire);
        //myCnx.trace("@01234***** mail envoye******************", "objet", objet);
        //myCnx.trace("@01234***** mail envoye******************", "corps", corps);
      }
      catch (Exception e) {
        myCnx.trace("@01234***********************",
                    "mail non envoy�, sendMail",
                    myCnx.sendMail);
      }


  }
 }

 // ---------------------------------------- etat = Traite -----------------------------------------------//
  if (old_state == 3)
  {
    if (this.idEtat == 6) {                 // Nouvel �tat = Clos

      //-------------- Envoi d'un mail au COMAA: soummision d'un nouveau BRIEF ------------------//
      objet = "[TACHE][CLOSE] " + this.idRoadmap + "-" + this.id+ "-" + this.Code;

      emetteur = mailRespRoadmap;
      if ((this.LoginEmetteur != null) && (!this.LoginEmetteur.equals("")))
        emetteur = CollaborateurEmetteur.mail;
      else
         emetteur = mailRespRoadmap;
     destinataire = this.getListeDiffusion(nomBase,myCnx,st);

      corps = "Tache Close";
      corps += "<br>";
      corps = "Bonjour";
      corps += "<br>";

      corps += "Je viens de Clore la Tache: " + this.idRoadmap + "-" + this.id+ "-" + this.Code;
          corps +=    "<br>";
          corps += ""+this.reponse;
          corps +=    "<br>" + "<br>";

      try {
        myCnx.sendmail(emetteur, destinataire, objet, corps);
        //myCnx.trace("@01234***** mail envoye******************", "emetteur",                    emetteur);
        //myCnx.trace("@01234***** mail envoye******************", "destinataire",                    destinataire);
        //myCnx.trace("@01234***** mail envoye******************", "objet", objet);
        //myCnx.trace("@01234***** mail envoye******************", "corps", corps);
      }
      catch (Exception e) {
        myCnx.trace("@01234***********************",
                    "mail non envoy�, sendMail",
                    myCnx.sendMail);
      }


  }
  else if (this.idEtat == 2) {                 // Nouvel �tat = en cours

    //-------------- Envoi d'un mail au COMAA: soummision d'un nouveau BRIEF ------------------//
    objet = "[TACHE][EN COURS] " + this.idRoadmap + "-" + this.id+ "-" + this.Code;

    emetteur = this.LoginEmetteur+"@" + myCnx.mail;
    destinataire = this.getListeDiffusion(nomBase,myCnx,st);

    corps = "Tache En cours";
    corps += "<br>";
    corps = "Bonjour";
    corps += "<br>";

    corps += "Je viens de re-Ouvrir la Tache: " + this.idRoadmap + "-" + this.id+ "-" + this.Code;
        corps +=    "<br>";
        corps += ""+this.reponse;
        corps +=    "<br>" + "<br>";

        //myCnx.trace("@01234***** mail envoye******************", "emetteur",   emetteur);
        //myCnx.trace("@01234***** mail envoye******************", "destinataire", destinataire);
        //myCnx.trace("@01234***** mail envoye******************", "objet", objet);
        //myCnx.trace("@01234***** mail envoye******************", "corps", corps);
    try {
      myCnx.sendmail(emetteur, destinataire, objet, corps);
    }
    catch (Exception e) {
      myCnx.trace("@01234***********************", "mail non envoy�, sendMail", myCnx.sendMail);
    }


  }
 }

// -------------------- au traitement de la tache on m�morise la date de cloture ------------------//

   if ((this.idEtat == 3) && (this.idEtat != old_state)) {

     String currentDate = Utils.getCurrentDate( myCnx.nomBase, myCnx,  st);
     String str_date = "CONVERT(DATETIME, '"+currentDate+"',  102)";

     req = "UPDATE actionSuivi SET  dateCloture ="+ str_date + " WHERE id="+this.id;
     myCnx.ExecReq(st,myCnx.nomBase,req);

     return "OK";
   }

// -----------------------------------------------------------------------------------------------//

// ---------------- si le traitement est incorrect on leve une anomalie (traite -> en cours)------//

      if ((this.idEtat == 2) && (old_state == 3)) {

        ActionAnomalie theActionAnomalie = new ActionAnomalie(this.id);

        theActionAnomalie.bd_insert(myCnx.nomBase,myCnx, st, transaction);

        return "OK";
      }

// -----------------------------------------------------------------------------------------------//

    return "OK";
  }
  public String bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction, int newState){

    req = "UPDATE actionSuivi SET idEtat='"+newState+"' WHERE id="+this.id;
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
     return "OK";
  }
  
  public ErrorSpecific bd_UpdateNbDependance(String nomBase,Connexion myCnx, Statement st, String transaction, int nb){
    ErrorSpecific myError = new ErrorSpecific();
    req = "UPDATE TachesDependances SET nb='"+nb+"' WHERE idTacheDestination="+this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateNbDependance",""+this.id);
    
    return myError;
  } 
  
  public ErrorSpecific bd_EnregDependancesActions(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

   req="DELETE FROM TachesDependances WHERE idTacheDestination="+this.id;
   myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_EnregDependancesActions",""+this.id);
   if (myError.cause == -1) return myError;
   
          for (int j=0; j < this.ListeDependance.size(); j++)
            {
                dependanceTache theDependance = (dependanceTache)this.ListeDependance.elementAt(j);
                int idTacheOrigine = theDependance.tacheOrigine.id;
                int idTacheDestination = theDependance.tacheDestination.id;            
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

        return myError;
  }  
  
  public ErrorSpecific bd_deleteNextCharges(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    
    // recuperation de la semaine courante
        int week = Utils.getCurrentWeek(nomBase,myCnx, st);
        Calendar calendar = Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        
    // recherche de toutes les charges dans le consommé pour cette tache > date courante
    // suppression de ces charges.
    
    req = "DELETE FROM Consomme WHERE (semaine > "+week+") AND (anneeRef = "+year+") AND (idAction = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateNbDependance",""+this.id);
    
    return myError;
  }   
  
  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    
    req = "DELETE FROM actionSuivi";
    req+= " WHERE id="+this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateNbDependance",""+this.id);
    
    return myError;
  }   

  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

     this.idEtat = 1;
     String theDesc="";
    if(this.reponse != null)
      theDesc=this.nom.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
    else
      theDesc="";

    String date_start =this.date_start;
    if ((this.date_start != null) &&(!this.date_start.equals("")))
    {
      Utils.getDate(date_start);
      date_start ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
   }
   else
     date_start = "NULL";

   String date_end =this.date_end;
   if ((this.date_end != null) &&(!this.date_end.equals("")))
   {
     Utils.getDate(date_end);
     date_end ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
   }
   else
     date_end = "NULL";

   String date_start_init =this.date_start_init;
   if ((this.date_start_init != null) &&(!date_start_init.equals("")))
   {
     Utils.getDate(date_start_init);
     date_start_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
  }
  else
     date_start_init = "NULL";

   String date_end_init =this.date_end_init;
   if ((this.date_end_init != null) &&(!this.date_end_init.equals("")))
   {
     Utils.getDate(date_end_init);
     date_end_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
   }
   else
     date_end_init = "NULL"; 
   
   String date_cloture =this.dateCloture;
   if ((this.dateCloture != null) &&(!this.dateCloture.equals("")))
   {
     Utils.getDate(dateCloture);
     dateCloture ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
   }
   else
     dateCloture = "NULL";   
  
      
    req = "INSERT INTO [actionSuivi]";
    req+= "           (";
    req+= "             [nom]";
    req+= "            ,[acteur]";
    req+= "            ,[idEtat]";
    req+= "            ,[idRoadmap]";
    req+= "            ,[dateAction]";
    req+= "            ,[dateFin]";
    req+= "            ,[type]";
    req+= "            ,[reponse]";
    req+= "            ,[isPlanning]";
    req+= "            ,[doc]";
    req+= "            ,[Code]";
    req+= "            ,[ChargePrevue]";
    req+= "            ,[Semaine]";
    req+= "            ,[RAF]";    
    req+= "            ,[LoginEmetteur]";   
    req+= "            ,[priorite]";   
    req+= "            ,[dateCloture]";   
    req+= "            ,[idRoadmapLie]";   
    req+= "            ,[dateAction_init]";       
    req+= "            ,[dateFin_init]";      
    req+= "            )";    
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+ this.nom.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ')+"'" + ",";
    req+= "'"+this.acteur+"'" + ",";
    req+= "'"+this.idEtat+"'" + ",";
    req+= "'"+this.idRoadmap+"'" + ",";
    req+= ""+date_start+"" + ",";
    req+= ""+date_end+"" + ",";
    req+= "'"+this.Type+"'" + ",";
    req+= "'"+this.reponse+"'" + ",";
    req+= "'"+this.isPlanning+"'" + ",";
    req+= "'"+this.doc+"'" + ",";
    req+= "'"+this.Code+"'" + ",";
    req+= "'"+this.ChargePrevue+"'" + ",";
    req+= "'"+this.Semaine+"'" + ",";
    req+= "'"+this.RAF+"'" + ",";
    req+= "'"+this.LoginEmetteur+"'" + ",";
    req+= "'"+this.priorite+"'" + ",";
    req+= ""+dateCloture+"" + ",";
    req+= "'"+this.idRoadmapLie+"'" + ",";
    req+= ""+date_start_init+"" + ",";
    req+= ""+date_end_init+"" + "";

    req+= "            )";

      ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
      if (myError.cause ==  -1) return myError;
      
      req="SELECT  id FROM   actionSuivi ORDER BY id DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("id");
        } catch (SQLException s) {s.getMessage();}

    return myError;

  }  

  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){

     String theDesc="";
    if(this.reponse != null)
      theDesc=this.nom.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
    else
      theDesc="";

    String date_start =this.date_start;
    if ((this.date_start != null) &&(!this.date_start.equals("")))
    {
      Utils.getDate(date_start);
      date_start ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
   }
   else
     date_start = "NULL";

   String date_end =this.date_end;
   if ((this.date_end != null) &&(!this.date_end.equals("")))
   {
     Utils.getDate(date_end);
     date_end ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
   }
   else
     date_end = "NULL";

   String date_start_init =this.date_start_init;
   if ((this.date_start_init != null) &&(!date_start_init.equals("")))
   {
     Utils.getDate(date_start_init);
     date_start_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
  }
  else
     date_start_init = "NULL";

   String date_end_init =this.date_end_init;
   if ((this.date_end_init != null) &&(!this.date_end_init.equals("")))
   {
     Utils.getDate(date_end_init);
     date_end_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
   }
   else
     date_end_init = "NULL"; 
   
   String date_cloture =this.dateCloture;
   if ((this.dateCloture != null) &&(!this.dateCloture.equals("")))
   {
     Utils.getDate(dateCloture);
     dateCloture ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
   }
   else
     dateCloture = "NULL";   

    req = "UPDATE actionSuivi ";
    req+= " SET ";
    req+= " nom='"+this.nom.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ')+"'";
    req+=",";    
    req+= " acteur='"+this.acteur+"'";
    req+=",";    
    req+= " idEtat='"+this.idEtat+"'";
    req+=",";
    req+= " idRoadmap='"+this.idRoadmap+"'";
    req+=","; 
    req+= " dateAction="+date_start+"";
    req+=","; 
    req+= " dateFin="+date_end+"";
    req+=",";     
    req+= " reponse='"+this.reponse.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ')+"'";
    req+=",";
    req+= " isPlanning='"+this.isPlanning+"'";
    req+=",";
    req+= " doc='"+this.doc+"'";
    req+=",";  
    req+= " Code='"+this.Code+"'";
    req+=",";
    req+= " ChargePrevue='"+this.ChargePrevue+"'";
    req+=","; 
    req+= " ChargeDevis='"+this.ChargeDevis+"'";
    req+=",";     
    req+= " Semaine='"+this.Semaine+"'";
    req+=","; 
    req+= " RAF='"+this.RAF+"'";
    req+=",";
    req+= " LoginEmetteur='"+this.LoginEmetteur+"'";
    req+=",";
    req+= " priorite='"+this.priorite+"'";
    req+=","; 
    req+= " dateCloture="+dateCloture+"";
    req+=",";
    req+= " idRoadmapLie='"+this.idRoadmapLie+"'";
    req+=",";
    req+= " dateAction_init="+date_start_init+"";
    req+=",";
    req+= " dateFin_init="+date_end_init+"";

    req+= " WHERE id="+this.id;

      ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
      if (myError.cause ==  -1) return myError;
return myError;
  }

  public String bd_UpdateRequest(String nomBase,Connexion myCnx, Statement st, String transaction){

    String theDesc="";
    if(this.reponse != null)
      theDesc=this.nom.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("&","et").replace('+',' ');
    else
      theDesc="";

    String date_start =this.date_start;
    if ((this.date_start != null) &&(!this.date_start.equals("")))
    {
      Utils.getDate(date_start);
      date_start ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
   }
   else
     date_start = "NULL";

   String date_end =this.date_end;
   if ((this.date_end != null) &&(!this.date_end.equals("")))
   {
     Utils.getDate(date_end);
     date_end ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
   }
   else
     date_end = "NULL";

   String date_start_init =this.date_start_init;
   if ((this.date_start_init != null) &&(!date_start_init.equals("")))
   {
     Utils.getDate(date_start_init);
     date_start_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
  }
  else
     date_start_init = "NULL";

   String date_end_init =this.date_end_init;
   if ((this.date_end_init != null) &&(!this.date_end_init.equals("")))
   {
     Utils.getDate(date_end_init);
     date_end_init ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";
   }
   else
     date_end_init = "NULL";


    req = "UPDATE actionSuivi SET ";
    req +=" nom ='"+ this.nom.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ') + "', ";
    req +=" dateAction ="+ date_start + ", ";
    req +=" dateFin ="+ date_end + ", ";
    req +=" dateAction_init ="+ date_start_init + ", ";
    req +=" dateFin_init ="+ date_end_init + ", ";
    req +=" priorite ='"+ this.priorite + "'";

    req += " WHERE id ="+ this.id;

    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
     return "OK";


  }

  public void getInitialMOA(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;

    int num=0;
    for (StringTokenizer t = new StringTokenizer(this.acteur, ";");
         t.hasMoreTokens(); ) {

      String theTocken = t.nextToken();

      req="SELECT     nomMembre, prenomMembre, LoginMembre";
      req+="    FROM         Membre";
      req+=" WHERE     (LoginMembre = '"+theTocken+"')";
      rs = myCnx.ExecReq(st, nomBase,req);

      String nom = "";
      String prenom = "";

      try { rs.next();
               nom= rs.getString(1);
               prenom= rs.getString(2);
              if (num ==0)
              {
                this.ListeResp = prenom.substring(0, 1).toUpperCase() + nom.substring(0, 1).toUpperCase();
              }
              else
              {
               this.ListeResp += ";"+prenom.substring(0, 1).toUpperCase() + nom.substring(0, 1).toUpperCase();
             }

    } catch (SQLException s) {s.getMessage();}

    num++;

  }



  }

  public String getListeDiffusion(Connexion myCnx){

    String ListeDiffusion="";
    ResultSet rs;

    int num=0;
    for (StringTokenizer t = new StringTokenizer(this.acteur, ";");
         t.hasMoreTokens(); ) {

      String theTocken = t.nextToken();

      if (num == 0)
      {
        ListeDiffusion =theTocken + "@" + myCnx.mail;
      }
      else
      {
        ListeDiffusion += ";" + theTocken + "@" + myCnx.mail;
      }

    num++;

  }

  return ListeDiffusion;

  }

  public void addDependance(String nomCodeAction){
      
    for (StringTokenizer t = new StringTokenizer(nomCodeAction, ";");
         t.hasMoreTokens(); ) {

      String theTocken = t.nextToken();
      dependanceTache theDependance = new dependanceTache();
      theDependance.codeTacheDestination = theTocken;
      if (this.ListeDependance.size() == 0)
      {
        this.ListeDependance.addElement(theDependance); 
      }
    }
        //System.out.println("theDependance.codeTacheDestination"+this.Code+"/"+theDependance.codeTacheDestination);
  }
  
  public void getListeDependances(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    String req = "SELECT     TachesDependances.idTacheDestination, TachesDependances.idTacheOrigine, TachesDependances.type, actionSuivi.dateAction, actionSuivi.dateFin, actionSuivi.Code";
    req+=" FROM         TachesDependances INNER JOIN";
    req+="              actionSuivi ON TachesDependances.idTacheOrigine = actionSuivi.id";
    req+=" WHERE     (idTacheDestination = "+this.id+")";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
          dependanceTache theDependance = new dependanceTache();
          
          theDependance.tacheDestination = this;
          theDependance.tacheOrigine= new Action(rs.getInt("idTacheOrigine"));                
          theDependance.type = rs.getString("type");
          theDependance.tacheOrigine.Ddate_start = rs.getDate("dateAction");
          theDependance.tacheOrigine.Ddate_end = rs.getDate("dateFin");
          theDependance.codeTacheDestination = rs.getString("Code");
    
          this.ListeDependance.addElement(theDependance);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }  

  public void getXY(Vector ListeActions){
      for (int i=0; i < ListeActions.size();i++)
      {
          Action theAction = (Action)ListeActions.elementAt(i);
          if (this.id == theAction.id)
          {
              this.x0 = theAction.x0;
              this.x1 = theAction.x1;
              this.y0 = theAction.y0;
              this.y1 = theAction.y1;
          }
      }
  }
  public int getNextAction(int idTacheDestination, String nomBase,Connexion myCnx, Statement st, int nb){
      Action nextAction = null;
      ResultSet rs = null;
      
      String req = "SELECT     idTacheOrigine FROM         TachesDependances WHERE     (idTacheDestination = "+idTacheDestination+")";
      rs=myCnx.ExecReq(st, myCnx.nomBase,req);
      
      int idTacheOrigine = -1;
      try {
        while (rs.next()) {
            idTacheOrigine = rs.getInt("idTacheOrigine");
            nb++;
        }
      } catch (SQLException s) {s.getMessage();}   
      
      if (idTacheOrigine != -1)
          nb = getNextAction(idTacheOrigine,  nomBase, myCnx,  st,  nb);
      return nb;
      
  }
  
  public void getDates(){
      for (int i=0; i < this.ListeDependance.size();i++)
      {
          dependanceTache thedependanceTache = (dependanceTache)this.ListeDependance.elementAt(i);
          long delta = thedependanceTache.getDecalage();
          if ((delta > 0) && (this.Ddate_start != null) &&  (this.Ddate_end != null))
          {
              System.out.println("-- Avant --------this.date_start="+this.date_start+"/ this.date_end="+this.date_end+"/ this.Code="+this.Code);
              this.Ddate_start = thedependanceTache.setDecalage(this.Ddate_start, delta);
              if (this.Ddate_start != null)
                    this.date_start = ""+this.Ddate_start.getDate()+"/"+(this.Ddate_start.getMonth()+1) + "/"+(this.Ddate_start.getYear() + 1900);
              else
                    this.date_start = "";         
              this.Ddate_end = thedependanceTache.setDecalage(this.Ddate_end, delta);
              if (this.Ddate_end != null)
                    this.date_end = ""+this.Ddate_end.getDate()+"/"+(this.Ddate_end.getMonth()+1) + "/"+(this.Ddate_end.getYear() + 1900);
              else
                    this.date_end = "";  
              
               System.out.println("-- Apres --------this.date_start="+this.date_start+"/ this.date_end="+this.date_end+"/ this.Code="+this.Code);
          }
      }
  }  
  
  public String getListeDiffusion(String nomBase,Connexion myCnx, Statement st){

    String ListeDiffusion="";
    ResultSet rs;

    int num=0;
    for (StringTokenizer t = new StringTokenizer(this.acteur, ";");
         t.hasMoreTokens(); ) {

      String theTocken = t.nextToken();
      Collaborateur theCollaborateur = new Collaborateur(theTocken);
      theCollaborateur.getAllFromBd(nomBase, myCnx, st);
      theTocken=theCollaborateur.getOriginalLogin(nomBase, myCnx, st);

      if (num == 0)
      {
        ListeDiffusion =theTocken + "@" + myCnx.mail;
      }
      else
      {
        ListeDiffusion += ";" + theTocken + "@" + myCnx.mail;
      }

    num++;

  }

  return ListeDiffusion;

  }
  
 public String getisChecked(String myListe){

  int pos = myListe.indexOf(";");
  if (pos != myListe.length() -1) myListe+=";";
  
  pos = myListe.indexOf(this.Code + ";");
  if (pos >= 0) return "checked";
  
  return "";
}
 
  public float getChargeByWeek(String nomBase,Connexion myCnx, Statement st, int Annee, int semaine, int projetMembre){
    ResultSet rs;

    float ChargesPrevu=0;

    req="SELECT     Charge";
    req+="    FROM         Consomme";
    req+=" WHERE     (anneeRef = "+Annee+") AND (semaine = "+semaine+") AND (projetMembre = "+projetMembre+") AND (idAction = "+this.id+")";


      rs = myCnx.ExecReq(st, nomBase,req);

      try { rs.next();
               ChargesPrevu= rs.getFloat("Charge");

    } catch (SQLException s) {s.getMessage();}



    return ChargesPrevu;

  }

  public float getChargesConsommeesByYearByWeek(String nomBase,Connexion myCnx, Statement st, int Annee, int semaine, int projetMembre){
    ResultSet rs;

    float ChargesConsommees=0;


      req="SELECT     Charge";
      req+="    FROM         Consomme";
      req+=" WHERE     (anneeRef = "+Annee+") AND (projetMembre  = "+projetMembre+") AND (semaine = "+semaine+") AND (idAction = "+this.id+")";

      rs = myCnx.ExecReq(st, nomBase,req);

      try { rs.next();
               ChargesConsommees= rs.getFloat("Charge");

    } catch (SQLException s) {s.getMessage();}



    return ChargesConsommees;


  }

  public float getChargesConsommeesByYearByWeek_current(String nomBase,Connexion myCnx, Statement st, int Annee){
    ResultSet rs;

    float ChargesConsommees=0;

    int theWeekRef=Utils.getCurrentWeek(nomBase,myCnx, st);

    Calendar calendar = Calendar.getInstance();
    int anneeRef=calendar.get(Calendar.YEAR);

if (Annee == anneeRef)
    {
      req = "SELECT     Charge";
      req += "    FROM         Consomme";
      req += " WHERE     (anneeRef = " + Annee + ") AND (semaine <= " + theWeekRef + ") AND (idAction = " + this.id + ")";
    }
else
    {

        req = "SELECT     Charge";
        req += "    FROM         Consomme";
        req += " WHERE     (anneeRef = " + Annee + ") AND (idAction = " + this.id + ")";

    }
      rs = myCnx.ExecReq(st, nomBase,req);

      try { rs.next();
               ChargesConsommees= rs.getFloat("Charge");

    } catch (SQLException s) {s.getMessage();}



    return ChargesConsommees;


  }

  public float getChargesConsommees(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;

    float ChargesConsommees=0;
    int theWeekRef=Utils.getCurrentWeek(nomBase,myCnx, st);

    Calendar calendar = Calendar.getInstance();
    int anneeRef=calendar.get(Calendar.YEAR);

    float ChargesConsommeesAvant=0;
    float ChargesConsommeesPendant=0;


    req="      SELECT     SUM (Charge) as total";
    req+="    FROM         Consomme";
    req+="  WHERE      (idAction = "+this.id+")";
    if ((this.idEtat == 6) || (this.idEtat == 3) || (this.idEtat == 4))
    {
      req += "  AND      (semaine <= " + theWeekRef + ")";
    }
    else
    {
      req += "  AND      (semaine <= " + theWeekRef + ")";
    }
    req+=" AND anneeRef = "+anneeRef;

      rs = myCnx.ExecReq(st, nomBase,req);

      try { rs.next();
               ChargesConsommeesPendant= rs.getFloat("total");

    } catch (SQLException s) {s.getMessage();}

    req="      SELECT     SUM (Charge) as total";
    req+="    FROM         Consomme";
    req+="  WHERE      (idAction = "+this.id+")";
    req+=" AND anneeRef < "+anneeRef;

      rs = myCnx.ExecReq(st, nomBase,req);

      try { rs.next();
               ChargesConsommeesAvant= rs.getFloat("total");

    } catch (SQLException s) {s.getMessage();}

    return ChargesConsommeesAvant+ChargesConsommeesPendant;


  }

  public void getInitialMOA2(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;

    req="SELECT     nomMembre, prenomMembre, LoginMembre";
    req+="    FROM         Membre";
    req+=" WHERE     (LoginMembre = '"+this.acteur+"')";


    String nom = "";
    String prenom = "";
    rs = myCnx.ExecReq(st, nomBase,req);
    try { rs.next();
             nom= rs.getString(1);
             prenom= rs.getString(2);
            this.ListeResp  = prenom.substring(0,1).toUpperCase() + nom.substring(0,1).toUpperCase();

    } catch (SQLException s) {s.getMessage();}


  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs=null;

      //String req = "SELECT     id, nom, acteur, idetat, idRoadmap, dateAction FROM   actionSuivi WHERE idRoadmap ="+this.id;
      req = "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, ";
      req +=" TypeEtatAction.nom AS nomEtat, actionSuivi.dateFin, actionSuivi.reponse, actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.Code";
      req +=" , Code, ChargePrevue, ChargeDevis, Semaine,   RAF, LoginEmetteur,actionSuivi.priorite, actionSuivi.dateCloture, actionSuivi.idRoadmapLie";
      req +=" ,actionSuivi.dateAction_init, actionSuivi.dateFin_init, TypeEtatAction.nom AS nomEtat";
      req += " FROM         actionSuivi INNER JOIN ";
      req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id";
      req += " WHERE     actionSuivi.id = " + this.id;

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

          acteur = rs.getString("acteur").replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");
          etat = rs.getInt("idEtat");
          idRoadmap = rs.getInt("idRoadmap");

          this.Ddate_start = rs.getDate("dateAction");
          str_etat = rs.getString("nomEtat");
          if (this.Ddate_start != null)
            str_theDate = ""+this.Ddate_start.getDate()+"/"+(this.Ddate_start.getMonth()+1) + "/"+(this.Ddate_start.getYear() + 1900);
           else
            str_theDate = "";

          this.Ddate_end = rs.getDate("dateFin");
          if (this.Ddate_end != null)
            str_theDateFin = ""+this.Ddate_end.getDate()+"/"+(this.Ddate_end.getMonth()+1) + "/"+(this.Ddate_end.getYear() + 1900);
           else
            str_theDateFin = "";

          String reponse = rs.getString("reponse");
          if (reponse != null)
            reponse = reponse;
          else
            reponse = "";

          Action theAction = new Action(id,nom,acteur,etat,idRoadmap,str_theDate,str_theDateFin,reponse);
          this.nom=nom;
          this.acteur=acteur;
          this.nom=nom;
          this.nomEtat = str_etat;
          this.idRoadmap = idRoadmap;
          this.date_end=str_theDateFin;
          this.date_start=str_theDate;
          this.isPlanning = rs.getInt("isPlanning");
          this.doc = rs.getString("doc");
          if (this.doc == null) this.doc = "";
          this.Code = rs.getString("Code");
          if (this.Code == null) this.Code = "";
          this.idEtat=etat;
          this.reponse=reponse;
          this.ChargePrevue=rs.getFloat("ChargePrevue");
          this.ChargeDevis=rs.getFloat("ChargeDevis");
          this.Semaine=rs.getInt("Semaine");
          this.RAF=rs.getFloat("RAF");
          this.LoginEmetteur = rs.getString("LoginEmetteur");
          this.priorite=rs.getInt("priorite");

          theDate = rs.getDate("dateCloture");
          if (theDate != null)
            str_theDateCloture = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
           else
            str_theDateCloture = "";

          this.dateCloture = str_theDateCloture;
          this.idRoadmapLie = rs.getInt("idRoadmapLie");

          theDate = rs.getDate("dateAction_init");
          if (theDate != null)
            this.date_start_init = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
           else
            this.date_start_init = "";

          theDate = rs.getDate("dateFin_init");
          if (theDate != null)
            this.date_end_init = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
           else
            this.date_end_init = "";

        }
      }
            catch (SQLException s) {s.getMessage();}

  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("reponse="+this.reponse);
    System.out.println("acteur="+this.acteur);
    System.out.println("idEtat="+this.idEtat);
    System.out.println("nomEtat="+this.nomEtat);
    System.out.println("idRoadmap="+this.idRoadmap);
    System.out.println("idRoadmapLie="+this.idRoadmapLie);
    System.out.println("date_start="+this.date_start);
    System.out.println("date_end="+this.date_end);
    System.out.println("dateCloture="+this.dateCloture);
    System.out.println("isPlanning="+this.isPlanning);
    System.out.println("doc="+this.doc);
    System.out.println("Code="+this.Code);
    System.out.println("ChargePrevue="+this.ChargePrevue);
    System.out.println("ChargeDevis="+this.ChargeDevis);
    System.out.println("Semaine="+this.Semaine);
    System.out.println("RAF="+this.RAF);
    System.out.println("LoginEmetteur="+this.LoginEmetteur);
    System.out.println("priorite="+this.priorite);
    System.out.println("==================================================");
 }

 public void changeMembre( String nomBase,Connexion myCnx, Statement st,int id,String nom2,String Login2){

   String req = "";

   req="UPDATE Membre SET  nomMembre ='"+ nom2 + "' WHERE     (idMembre = "+id+")";
   myCnx.ExecReq(st,myCnx.nomBase,req);

   req="UPDATE Membre SET  prenomMembre ='"+ nom2 + "' WHERE     (idMembre = "+id+")";
   myCnx.ExecReq(st,myCnx.nomBase,req);

   req="UPDATE Membre SET  LoginMembre ='"+ Login2 + "' WHERE     (idMembre = "+id+")";
   myCnx.ExecReq(st,myCnx.nomBase,req);

   req="UPDATE Membre SET  mail ='"+ Login2 + "@"+myCnx.mail+"' WHERE     (idMembre = "+id+")";
   myCnx.ExecReq(st,myCnx.nomBase,req);

}

 public void rollback_changeMembre( String nomBase,Connexion myCnx, Statement st,int id,String prenom1,String nom1,String Login1){

   String req = "";

   req="UPDATE Membre SET  nomMembre ='"+ nom1 + "' WHERE     (idMembre = "+id+")";
   myCnx.ExecReq(st,myCnx.nomBase,req);

   req="UPDATE Membre SET  prenomMembre ='"+ prenom1 + "' WHERE     (idMembre = "+id+")";
   myCnx.ExecReq(st,myCnx.nomBase,req);

   req="UPDATE Membre SET  LoginMembre ='"+ Login1 + "' WHERE     (idMembre = "+id+")";
   myCnx.ExecReq(st,myCnx.nomBase,req);

   req="UPDATE Membre SET  mail ='"+ Login1 + "@"+myCnx.mail+"' WHERE     (idMembre = "+id+")";
   myCnx.ExecReq(st,myCnx.nomBase,req);

}

public void changeActionSuivi( String nomBase,Connexion myCnx, Statement st, Statement st2,String Login1,String Login2){

  String req = "";
  req+=" SELECT     acteur FROM         actionSuivi WHERE     (acteur LIKE '%"+Login1+"%')";
  ResultSet rs = myCnx.ExecReq(st, myCnx.nomBase,req);

  String acteur1="";
  String acteur2="";

  try {
     while (rs.next()) {
       acteur1 = rs.getString(1);
       acteur2 = acteur1.replaceAll(Login1,Login2);

       req = "UPDATE actionSuivi SET  acteur ='"+ acteur2 + "' WHERE     (acteur = '"+acteur1+"')";
       myCnx.ExecReq(st2,myCnx.nomBase,req);
       //theST.dump();

     }
      } catch (SQLException s) {s.getMessage();}
}

public void rollback_changeActionSuivi( String nomBase,Connexion myCnx, Statement st, Statement st2,String Login1,String Login2){

      String req = "";
      req+=" SELECT     acteur FROM         actionSuivi WHERE     (acteur LIKE '%"+Login2+"%')";
      ResultSet rs = myCnx.ExecReq(st, myCnx.nomBase,req);

      String acteur1="";
      String acteur2="";

      try {
         while (rs.next()) {
           acteur2 = rs.getString(1);
           acteur1 = acteur2.replaceAll(Login2,Login1);

           req = "UPDATE actionSuivi SET  acteur ='"+ acteur1 + "' WHERE     (acteur = '"+acteur2+"')";
           myCnx.ExecReq(st2,myCnx.nomBase,req);
           //theST.dump();

         }
          } catch (SQLException s) {s.getMessage();}
}

 public static void main3(String[] args) {
   Connexion myCnx = null;
   Statement st, st2 = null;

   st = myCnx.Connect();
   st2 = myCnx.Connect();

   Action action = new Action(1,"","",1,1,null,null,"");

   String nom1="";
   String nom2="";
   String prenom1="";
   String prenom2="";
   String Login1="";
   String Login2="";
   int id = -1;


   // ------------------------------------------------------------------------------//
   id = 3149;
   prenom1="Tiziana";
   prenom2="RAD0001";
   nom1="basile";
   nom2="RAD0001";
   Login1="tbasile";
   Login2="RRAD0001";

   if (args[0].equals("ANONYMISATION")) // ANONYMISATION
   {
     action.changeMembre(myCnx.nomBase, myCnx, st, id, nom2, Login2);
     action.changeActionSuivi(myCnx.nomBase, myCnx, st, st2, Login1, Login2);
   }
   else if (args[0].equals("ROLLBACK")) // ANONYMISATION
   {
     action.rollback_changeMembre(myCnx.nomBase, myCnx, st, id,prenom1, nom1, Login1);
     action.rollback_changeActionSuivi(myCnx.nomBase, myCnx, st, st2, Login1, Login2);
   }
   // ------------------------------------------------------------------------------//

   // ------------------------------------------------------------------------------//
   id = 3148;
   prenom1="Claudio";
   prenom2="RAD0002";
   nom1="Romano";
   nom2="RAD0002";
   Login1="cromano";
   Login2="RRAD0002";

   if (args[0].equals("ANONYMISATION")) // ANONYMISATION
   {
     action.changeMembre(myCnx.nomBase, myCnx, st, id, nom2, Login2);
     action.changeActionSuivi(myCnx.nomBase, myCnx, st, st2, Login1, Login2);
   }
   else if (args[0].equals("ROLLBACK")) // ANONYMISATION
   {
     action.rollback_changeMembre(myCnx.nomBase, myCnx, st, id,prenom1, nom1, Login1);
     action.rollback_changeActionSuivi(myCnx.nomBase, myCnx, st, st2, Login1, Login2);
   }
   // ------------------------------------------------------------------------------//

   // ------------------------------------------------------------------------------//
   id = 3147;
   prenom1="Filippo";
   prenom2="RAD0004";
   nom1="De Rogatis";
   nom2="RAD0004";
   Login1="fderogat";
   Login2="RRAD0004";

   if (args[0].equals("ANONYMISATION")) // ANONYMISATION
   {
     action.changeMembre(myCnx.nomBase, myCnx, st, id, nom2, Login2);
     action.changeActionSuivi(myCnx.nomBase, myCnx, st, st2, Login1, Login2);
   }
   else if (args[0].equals("ROLLBACK")) // ANONYMISATION
   {
     action.rollback_changeMembre(myCnx.nomBase, myCnx, st, id,prenom1, nom1, Login1);
     action.rollback_changeActionSuivi(myCnx.nomBase, myCnx, st, st2, Login1, Login2);
   }
   // ------------------------------------------------------------------------------//

   // ------------------------------------------------------------------------------//
   id = 3150;
   prenom1="Claudio";
   prenom2="RAD0003";
   nom1="SCANNAPIECO";
   nom2="RAD0003";
   Login1="cscannap";
   Login2="RRAD0003";

   if (args[0].equals("ANONYMISATION")) // ANONYMISATION
   {
     action.changeMembre(myCnx.nomBase, myCnx, st, id, nom2, Login2);
     action.changeActionSuivi(myCnx.nomBase, myCnx, st, st2, Login1, Login2);
   }
   else if (args[0].equals("ROLLBACK")) // ANONYMISATION
   {
     action.rollback_changeMembre(myCnx.nomBase, myCnx, st, id,prenom1, nom1, Login1);
     action.rollback_changeActionSuivi(myCnx.nomBase, myCnx, st, st2, Login1, Login2);
   }
   // ------------------------------------------------------------------------------//


   // ------------------------------------------------------------------------------//
   id = 3211;
   prenom1="Nicola";
   prenom2="RAD0011";
   nom1="CIRILLO";
   nom2="RAD0011";
   Login1="yyyy";
   Login2="RRAD0011";

   if (args[0].equals("ANONYMISATION")) // ANONYMISATION
   {
     action.changeMembre(myCnx.nomBase, myCnx, st, id, nom2, Login2);
     action.changeActionSuivi(myCnx.nomBase, myCnx, st, st2, Login1, Login2);
   }
   else if (args[0].equals("ROLLBACK")) // ANONYMISATION
   {
     action.rollback_changeMembre(myCnx.nomBase, myCnx, st, id,prenom1, nom1, Login1);
     action.rollback_changeActionSuivi(myCnx.nomBase, myCnx, st, st2, Login1, Login2);
   }
   // ------------------------------------------------------------------------------//

   // ------------------------------------------------------------------------------//
   id = 3229;
   prenom1="Enrico";
   prenom2="RAD0010";
   nom1="COZZUTO";
   nom2="RAD0010";
   Login1="ecozzuto";
   Login2="RRAD0010";

   if (args[0].equals("ANONYMISATION")) // ANONYMISATION
   {
     action.changeMembre(myCnx.nomBase, myCnx, st, id, nom2, Login2);
     action.changeActionSuivi(myCnx.nomBase, myCnx, st, st2, Login1, Login2);
   }
   else if (args[0].equals("ROLLBACK")) // ANONYMISATION
   {
     action.rollback_changeMembre(myCnx.nomBase, myCnx, st, id,prenom1, nom1, Login1);
     action.rollback_changeActionSuivi(myCnx.nomBase, myCnx, st, st2, Login1, Login2);
   }
   // ------------------------------------------------------------------------------//

   // ------------------------------------------------------------------------------//
   id = 3210;
   prenom1="Marco";
   prenom2="RAD0008";
   nom1="PISA";
   nom2="RAD0008";
   Login1="mpisa";
   Login2="RRAD0008";

   if (args[0].equals("ANONYMISATION")) // ANONYMISATION
   {
     action.changeMembre(myCnx.nomBase, myCnx, st, id, nom2, Login2);
     action.changeActionSuivi(myCnx.nomBase, myCnx, st, st2, Login1, Login2);
   }
   else if (args[0].equals("ROLLBACK")) // ANONYMISATION
   {
     action.rollback_changeMembre(myCnx.nomBase, myCnx, st, id,prenom1, nom1, Login1);
     action.rollback_changeActionSuivi(myCnx.nomBase, myCnx, st, st2, Login1, Login2);
   }
   // ------------------------------------------------------------------------------//


  }


  public static void main2(String[] args) {
    Connexion myCnx = null;
    Statement st, st2 = null;

    st = myCnx.Connect();
    st2 = myCnx.Connect();

    Action action = new Action(1,"","",1,1,null,null,"");

    String req = "select CONVERT(DATETIME, CURRENT_TIMESTAMP, 102) as myDate";
    ResultSet rs = myCnx.ExecReq(st, myCnx.nomBase,req);

    Date currentDate = null;
    try { rs.next();
             currentDate= rs.getDate("myDate");

   } catch (SQLException s) {s.getMessage();}

    req = "UPDATE actionSuivi SET  dateCloture ="+ currentDate + " WHERE id="+43919;
    myCnx.ExecReq(st,myCnx.nomBase,req);

    String currentDate2 = Utils.getCurrentDate( myCnx.nomBase, myCnx,  st);
    String str_date = "CONVERT(DATETIME, '"+currentDate2+"',  102)";

    req = "UPDATE actionSuivi SET  dateCloture ="+ str_date + " WHERE id="+43919;
    myCnx.ExecReq(st,myCnx.nomBase,req);

  }
  public static void main(String[] args) {
    Connexion myCnx = null;
    Statement st, st2 = null;

    st = myCnx.Connect();
    st2 = myCnx.Connect();

    
    Roadmap roadmap = new Roadmap(38324);
    //String cr=roadmap.getListeBesoins(myCnx.nomBase, myCnx, st, false);
    roadmap.getAllFromBd(myCnx.nomBase, myCnx, st);
    roadmap.getListeActions(myCnx.nomBase, myCnx, st);
    
  for (int i=0; i < roadmap.ListeActions.size();i++)
      {
        Action theAction = (Action)roadmap.ListeActions.elementAt(i);    
    //float conso = 0;
    //Action action = new Action(44807);
    theAction.getAllFromBd(myCnx.nomBase, myCnx,  st);
    //conso=action.getChargesConsommees(myCnx.nomBase, myCnx,  st);
    //System.out.print("conso="+conso);
    
    int nb = theAction.getNextAction(theAction.id, myCnx.nomBase, myCnx,  st, 0);
    System.out.println("action="+theAction.Code+ " :: nb="+nb);
    theAction.bd_UpdateNbDependance( myCnx.nomBase, myCnx,  st, "",  nb);
      }

  }
}

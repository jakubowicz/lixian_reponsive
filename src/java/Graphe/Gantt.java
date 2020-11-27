package Graphe; 

import java.util.Vector;
import java.sql.Statement;
import accesbase.Connexion;
import Favori.Favori;
import java.sql.ResultSet;
import java.sql.SQLException;

import ST.SI;
import ST.TypeSi;
import ST.ST;
import ST.MacroSt;
import Processus.Processus;
import Organisation.Service;
import Test.Campagne;
import Test.CampagneRelecture;
import Organisation.Equipe;
import Organisation.Collaborateur;
import Projet.Action;
import Projet.Jalon;
import accesbase.ErrorSpecific;

import Statistiques.Statistiques;

import General.Utils;
import java.util.Calendar;
import java.util.Date;

import Composant.item;

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
public class Gantt {
  static Connexion myCnx;

  public int id = -1;
  public String nom="";
  public int ordre = 0;
  public int idEquipe = -1;
  public String Resume = "";
  public String description = "";
  public String Graphe = "";
  public int isAnnee = 1;
  
  public int max = 0;
  public int min = 0;

  public Vector ListeBarres = new Vector(10);

  // -----------------------------------------------------------------------------------------//
  private static final int VERTICAL = 0;
  private static final int HORIZONTAL = 1;

  private static final int SOLID = 0;
  private static final int STRIPED = 1;

  private int orientation;
  private String title;
  private String tempo;
  //private Font font;
  //private FontMetrics metrics;
  private int fontHeight = 15;
  private int columns;
  //private Color colors[];
  private String labels[];
  private int styles[];
  private int scale = 10;
  private int maxLabelWidth = 150;
  private int barSpacing = 10;
  private int maxValue = 0;

  private int coeff = 0;

  private Vector ListeProjet;
  int Mois_start=0;
  int MoisRef=0;
  int AnneeRef = 0;
  int AnneeEncours = 0;
  float X_ToDay=0;

  static String Day;

  public int start_projet=0;
  public int end_projet=0;

  int max_projets_byPage = 0;

  private boolean jalon = true;

 public static String Month;
 public static String Year;

 public int num_item = 0;
 public boolean plier = false;

 public int[] rgb_EB;
 public int[] rgb_DEV;
 public int[] rgb_TEST;

 public int fontSize_projet=11;
 public int fontSize_tache=12;
 public int fontSize_legende=11;
 public int fontSize_jalon=10;
 public int fontSize_mois=12;
  public int fontSize_initiales=10;
  
  private String req="";
  
  private String[] mois = {"Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"};

  // -----------------------------------------------------------------------------------------//

  public Gantt(String nom) {
    this.nom = nom;
  }

  public Gantt(int id) {
    this.id = id;
  }

  public Gantt(int idEquipe, String nom) {
    this.idEquipe = idEquipe;
    this.nom = nom;
  }

  public void getAllFromBd(
      String nomBase,
      Connexion myCnx,
      Statement st ){
      ResultSet rs=null;

      String req = "SELECT     id, nom, ordre, idEquipe, Resume, description, isAnnee";
      req+="    FROM         typeGraphes";
      req+=" WHERE     (id = "+this.id+")";


    rs = Connexion.ExecReq(st, nomBase, req);

    //if (rs == null) return;
    try {
      while (rs.next()) {

        this.id= rs.getInt("id");
        this.nom= rs.getString("nom");
        this.ordre= rs.getInt("ordre");
        this.idEquipe= rs.getInt("idEquipe");
        this.Resume= rs.getString("Resume");
        this.description= rs.getString("description");
        this.isAnnee= rs.getInt("isAnnee");

        }

    } catch (SQLException s) {s.getMessage();}




}
  public void getSTbyMOA2(String nomBase,Connexion myCnx, Statement st, Statement st2 ){
    ResultSet rs=null;
    ResultSet rs2=null;
    Barre theBarre = null;
    String req="SELECT DISTINCT Service.idService, Service.aliasGraphe, Service.typeService";
           req+=" FROM         ListeST INNER JOIN";
           req+="            SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
           req+="            Service ON ListeST.serviceMoaVersionSt = Service.idService";
           req+=" WHERE     (SI.nomSI = 'SIR') AND (Service.typeService LIKE '%MOA%')";
           req+=" ORDER BY Service.aliasGraphe";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      try {
         while (rs.next()) {
           int idService=rs.getInt("idService");
           String nomService=rs.getString("aliasGraphe");

           theBarre = new Barre (nomService, -1, -1);

           // -- extraction des SI Rouges
           req="select";
           req+=" (";
           req+=" SELECT     COUNT(*) AS nb";
            req+=" FROM        ListeST INNER JOIN";
            req+="            TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
            req+=" WHERE     (ListeST.serviceMoaVersionSt = "+idService+") AND (TypeSi.nomTypeSi = 'SI Rouge')";
            req+=" ) as nb_Rouge";
            req+=" ,";
            req+=" (";
            req+=" SELECT     COUNT(*) AS nb";
            req+=" FROM        ListeST INNER JOIN";
            req+="            TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
            req+=" WHERE     (ListeST.serviceMoaVersionSt = "+idService+") AND (TypeSi.nomTypeSi = 'SI Bleu')";
            req+=" ) as nb_Bleu";
            req+=" ";

           rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);
             try {
                while (rs2.next()) {
                  theBarre.valeur2=rs2.getInt("nb_Rouge");
                  theBarre.valeur1=rs2.getInt("nb_Bleu");

                }
        } catch (SQLException s) {s.getMessage();}



        if ( (theBarre.valeur1 + theBarre.valeur2) > 5)
          this.ListeBarres.addElement(theBarre);

         }
        } catch (SQLException s) {s.getMessage();}

  }

  public void getSTbyMOA(String nomBase,Connexion myCnx, Statement st, Statement st2 ){
    SI theSI = new SI("SIR");
    theSI.getAllFromBd(myCnx.nomBase,myCnx,  st ) ;
    theSI.getListeMOA(myCnx.nomBase,myCnx,  st ) ;
    Barre theBarre = null;
    for (int i=0; i < theSI.ListeMOA.size(); i++)
    {
      Service theService = (Service)theSI.ListeMOA.elementAt(i);
      theService.getSiBleuMOA( nomBase, myCnx,  st, theSI.nom);

      theService.getSiRougeMOA( nomBase, myCnx,  st, theSI.nom);
      theBarre = new Barre (theService.nom, theService.nbStBleu, theService.nbStRouge);
      theBarre.ListeValeur1 = theService.ListeStBleu;
      theBarre.ListeValeur2 = theService.ListeStRouge;
      if ( (theBarre.valeur1 + theBarre.valeur2) > 1)
          this.ListeBarres.addElement(theBarre);
     }


  }

  public void getApplibyMOA(String nomBase,Connexion myCnx, Statement st, Statement st2 ){
    SI theSI = new SI("SIR");
    theSI.getAllFromBd(myCnx.nomBase,myCnx,  st ) ;
    theSI.getListeMOA(myCnx.nomBase,myCnx,  st ) ;
    Barre theBarre = null;
    for (int i=0; i < theSI.ListeMOA.size(); i++)
    {
      int nb_bleu = 0;
      int nb_rouge = 0;
      int nb_violet = 0;
      Service theService = (Service)theSI.ListeMOA.elementAt(i);
      nb_bleu=theService.getStByColorByMOA( nomBase, myCnx,  st, 2);
      nb_violet=theService.getStByColorByMOA( nomBase, myCnx,  st, 37);
      nb_rouge=theService.getAppliRougeMOA( nomBase, myCnx,  st, theSI.nom);
      theBarre = new Barre (theService.nom, nb_bleu, nb_rouge);
      theBarre.valeur3 = nb_violet;
      if ( (theBarre.valeur1 + theBarre.valeur2) > 1)
          this.ListeBarres.addElement(theBarre);
     }


  }

  public void getStatsbyMonth(String nomBase,Connexion myCnx, Statement st, int theYearRef){
    
    String toDay = Utils.getToDay(myCnx.nomBase,myCnx,  st);
    
    Barre theBarre = null;

    for (int i=1; i <= 12; i++)
    {
      int nb_bleu = 0;
      int nb_rouge = 0;
      int nb_violet = 0;
      theBarre = new Barre (mois[i-1], 0,0);
      int total=0;
      
        req = "SELECT     stat_trad.output, COUNT(stat_trad.output) AS nb, YEAR(StatConnexion.dateCnx) AS Annee, MONTH(StatConnexion.dateCnx) AS Mois";
        req+= " FROM         StatConnexion INNER JOIN";
        req+= "                       stat_trad ON StatConnexion.pageName = stat_trad.input";
        req+= " GROUP BY stat_trad.output, YEAR(StatConnexion.dateCnx), MONTH(StatConnexion.dateCnx)";
        req+= " HAVING      (YEAR(StatConnexion.dateCnx) = "+theYearRef+") AND (MONTH(StatConnexion.dateCnx) = "+i+")";
        req+= " ORDER BY stat_trad.output";
        
        /*
        req="SELECT * FROM(";
        req+= "         SELECT     stat_trad.output, COUNT(stat_trad.output) AS nb, YEAR(StatConnexion.dateCnx) AS Annee, MONTH(StatConnexion.dateCnx) AS Mois";
        req+= "         FROM         StatConnexion INNER JOIN";
        req+= "                               stat_trad ON StatConnexion.pageName = stat_trad.input";
        req+= "         GROUP BY stat_trad.output, YEAR(StatConnexion.dateCnx), MONTH(StatConnexion.dateCnx)";
        req+= "          HAVING      (YEAR(dateCnx) = "+theYearRef+") AND (MONTH(dateCnx) = "+i+")";

        req+= "          union";
        req+= "          SELECT 'Autre' as output, 0 as nb, "+theYearRef+" as Annee, "+i+" as mois";
        req+= "          union";
        req+= "          SELECT 'BRIEF' as output, 0 as nb, "+theYearRef+" as Annee, "+i+" as mois";
        req+= "          union";
        req+= "          SELECT 'DEVIS' as output, 0 as nb, "+theYearRef+" as Annee, "+i+" as mois       ";
        req+= "          union";
        req+= "          SELECT 'PROJET' as output, 0 as nb, "+theYearRef+" as Annee, "+i+" as mois   ";
        req+= "          union";
        req+= "          SELECT 'ST' as output, 0 as nb, "+theYearRef+" as Annee, "+i+" as mois   ";
        req+= "          union";        
        req+= "          SELECT 'TESTS' as output, 0 as nb, "+theYearRef+" as Annee, "+i+" as mois                                          ";
        req+= "         ) as mytable";

        
        req+= "  ORDER BY output";
        */        
        
        ResultSet rs = myCnx.ExecReq(st, nomBase, req);

      try { while(rs.next()) {
          item theItem = new item();
          theItem.id = 1;
          theItem.nom = rs.getString("output");
          theItem.nb=rs.getInt("nb");    
          
          theBarre.ListeValeur1.addElement(theItem);
          total+=theItem.nb;    
        
        
                  }	} catch (SQLException s) {s.getMessage();} 
      
      if ((total) > this.max) this.max = total;
          
       
       this.ListeBarres.addElement(theBarre);
      
      /*
      if ( (theBarre.valeur1 + theBarre.valeur2 + theBarre.valeur3) > 1)
          this.ListeBarres.addElement(theBarre);
      */
     }

  }
  
  public void getProjetsbyMonth(String nomBase,Connexion myCnx, Statement st, int theYearRef, int idSi ){
    
    String toDay = Utils.getToDay(myCnx.nomBase,myCnx,  st);
    //theYearRef=2011;
    SI theSI = new SI(idSi);
    theSI.getListeTypeSi(myCnx.nomBase,myCnx,  st ) ;

    Barre theBarre = null;

    for (int i=1; i <= 12; i++)
    {
      int nb_bleu = 0;
      int nb_rouge = 0;
      int nb_violet = 0;
      theBarre = new Barre (mois[i-1], 0,0);
      int total=0;
      for (int j=0; j < theSI.ListeTypeSi.size();j++)
      {
          TypeSi theTypeSi = (TypeSi)theSI.ListeTypeSi.elementAt(j);
          item theItem = new item();
          theItem.id = theTypeSi.id;
          theItem.nom = theTypeSi.nom;
          theItem.nb=theSI.getnbProjetsByMonthByColor(nomBase, myCnx,  st, theYearRef, i, theTypeSi.id);    
          
          theBarre.ListeValeur1.addElement(theItem);
          total+=theItem.nb;
  
          // myCnx.trace("mois="+i+"/"+j,"TypeSi="+theTypeSi.id,""+theBarre.valeur1+"/"+theBarre.valeur2+"/"+theBarre.valeur3);
      
      
      }
      if ((total) > this.max) this.max = total;
      
     
       
       this.ListeBarres.addElement(theBarre);
      
      /*
      if ( (theBarre.valeur1 + theBarre.valeur2 + theBarre.valeur3) > 1)
          this.ListeBarres.addElement(theBarre);
      */
     }

  }
  
  public void getProjetsbyMonthAll(String nomBase,Connexion myCnx, Statement st ){
    
    String toDay = Utils.getToDay(myCnx.nomBase,myCnx,  st);
    int theYearRef= Utils.theYear;
    //theYearRef=2011;
    SI theSI = new SI("SIR");
    theSI.getAllFromBd(myCnx.nomBase,myCnx,  st ) ;

    Barre theBarre = null;

    for (int i=1; i <= 12; i++)
    {
      int nb_bleu = 0;
      int nb_rouge = 0;
      int nb_violet = 0;
      nb_rouge=theSI.getnbProjetsByMonth(nomBase, myCnx,  st, theYearRef, i);
      //nb_bleu=theSI.getnbProjetsByMonthByColor(nomBase, myCnx,  st, theYearRef, i, 2);
      nb_violet=0;
      theBarre = new Barre (mois[i-1], nb_bleu,nb_rouge);
      theBarre.valeur3 = nb_violet;
      //if ( (theBarre.valeur1 + theBarre.valeur2 + theBarre.valeur3) > 1)
          this.ListeBarres.addElement(theBarre);
     }

  }  
  
  public void getProjetsbyMonth(String nomBase,Connexion myCnx, Statement st, String nomSI ){
    
    String toDay = Utils.getToDay(myCnx.nomBase,myCnx,  st);
    int theYearRef= Utils.theYear;
    //theYearRef=2011;
    SI theSI = new SI(nomSI);
    theSI.getAllFromBd(myCnx.nomBase,myCnx,  st ) ;

    Barre theBarre = null;

    for (int i=1; i <= 12; i++)
    {
      int nb_bleu = 0;
      int nb_rouge = 0;
      int nb_violet = 0;
      nb_rouge=theSI.getnbProjetsByMonthByColor(nomBase, myCnx,  st, theYearRef, i, 1);
      //nb_bleu=theSI.getnbProjetsByMonthByColor(nomBase, myCnx,  st, theYearRef, i, 2);
      nb_violet=theSI.getnbProjetsByMonthByColor(nomBase, myCnx,  st, theYearRef, i, 37);
      theBarre = new Barre (mois[i-1], nb_bleu,nb_rouge);
      theBarre.valeur3 = nb_violet;
      if ( (theBarre.valeur1 + theBarre.valeur2 + theBarre.valeur3) > 1)
          this.ListeBarres.addElement(theBarre);
     }


  }  
  
  public void getProjetsbyMonth(String nomBase,Connexion myCnx, Statement st, String nomSI, int couleur1, int couleur2 ){
    
    String toDay = Utils.getToDay(myCnx.nomBase,myCnx,  st);
    int theYearRef= Utils.theYear;
    //theYearRef=2011;
    SI theSI = new SI(nomSI);
    theSI.getAllFromBd(myCnx.nomBase,myCnx,  st ) ;

    Barre theBarre = null;

    for (int i=1; i <= 12; i++)
    {
      int nb_bleu = 0;
      int nb_rouge = 0;
      int nb_violet = 0;
      nb_rouge=theSI.getnbProjetsByMonthByColor(nomBase, myCnx,  st, theYearRef, i, couleur1);
      //nb_bleu=theSI.getnbProjetsByMonthByColor(nomBase, myCnx,  st, theYearRef, i, 2);
      nb_violet=theSI.getnbProjetsByMonthByColor(nomBase, myCnx,  st, theYearRef, i, couleur2);
      theBarre = new Barre (mois[i-1], nb_bleu,nb_rouge);
      theBarre.valeur3 = nb_violet;
      if ( (theBarre.valeur1 + theBarre.valeur2 + theBarre.valeur3) > 0)
          this.ListeBarres.addElement(theBarre);
     }


  }  

  public int getTachesChargesbyMonth(String nomBase,Connexion myCnx, Statement st, Statement st2,  Equipe theEquipe, int annee ){
    int max = 0;

    Barre theBarre = null;

    for (int i=1; i <= 12; i++)
    {
      int nbTacheOk=0;
      int nbTacheKo=0;
      theEquipe.getListeTachesByMonthByYear(nomBase,myCnx, st,i, annee);
      for (int j=0; j < theEquipe.ListeTaches.size(); j++)
      {
          Action theAction = (Action)theEquipe.ListeTaches.elementAt(j);
          float chargeConsomme = theAction.getChargesConsommees( nomBase, myCnx,  st);
          if (chargeConsomme <= theAction.ChargePrevue)
          {
            nbTacheOk++;
          }
          else
          {
            nbTacheKo++;
          }
          
          if ((nbTacheOk + nbTacheKo) > max) max = nbTacheOk+nbTacheKo;



      }
      theBarre = new Barre (mois[i-1], nbTacheOk,nbTacheKo);
      this.ListeBarres.addElement(theBarre);
      
      

     }

        return max;
  }

  public int getJoursProduitsbyMonth(String nomBase,Connexion myCnx, Statement st, Statement st2,  Equipe theEquipe, int annee ){
    
int max = 0;
    Barre theBarre = null;
    int theWeekRef=Utils.getCurrentWeek(nomBase,myCnx, st);

    Calendar calendar = Calendar.getInstance();
    int anneeRef=calendar.get(Calendar.YEAR);
    int monthRef=calendar.get(Calendar.MONTH) +1;

    for (int i=1; i <= 12; i++)
    {
      float nbTacheOk=0;
      float nbTacheKo=0;
      theEquipe.getListeTachesByMonthByYear(nomBase,myCnx, st,i, annee);
      for (int j=0; j < theEquipe.ListeTaches.size(); j++)
      {
          Action theAction = (Action)theEquipe.ListeTaches.elementAt(j);
          float chargeConsomme = theAction.getChargesConsommees( nomBase, myCnx,  st);
          if (theAction.idRoadmap != 38271) // formation
          {
            nbTacheOk += theAction.ChargePrevue;
          }
      }

      String req = "";

      if ((i > monthRef) && (anneeRef == annee))
      {
        nbTacheKo = 0;

      }
      else
      {
        req = "SELECT     SUM(Charge) AS charge";
        req += "    FROM         ConsommeMois";
        req += " GROUP BY mois, anneeRef";
        req += " HAVING      (mois = " + i + ") AND (anneeRef = " + annee + ")";
        ResultSet rs = myCnx.ExecReq(st2, myCnx.nomBase, req);
        try {
          while (rs.next()) {
            nbTacheKo = rs.getInt("charge");


          }
          } catch (SQLException s) {s.getMessage();}
        
        if ( (int)(nbTacheKo) > max) max = (int)(nbTacheKo);
        if ( (int)(nbTacheOk ) > max) max = (int)(nbTacheOk);
      }



      theBarre = new Barre (mois[i-1], (int)nbTacheOk,(int)nbTacheKo);
      this.ListeBarres.addElement(theBarre);

     }

    return max;
  }

  public void getJoursProduitsbyMonth2(String nomBase,Connexion myCnx, Statement st, Statement st2,  Equipe theEquipe, int annee ){
    

    Barre theBarre = null;

    for (int i=1; i <= 12; i++)
    {
      float nbTacheOk=0;
      float nbTacheKo=0;
      theEquipe.getListeTachesByMonthByYear(nomBase,myCnx, st,i, annee);
      for (int j=0; j < theEquipe.ListeTaches.size(); j++)
      {
          Action theAction = (Action)theEquipe.ListeTaches.elementAt(j);
          float chargeConsomme = theAction.getChargesConsommees( nomBase, myCnx,  st);
          if (chargeConsomme <= theAction.ChargePrevue)
          {
            if (theAction.idRoadmap != 38271) // formation
              nbTacheOk+=theAction.ChargePrevue;
          }
          else
          {
            if (theAction.idRoadmap != 38271) // formation
              nbTacheKo+=theAction.ChargePrevue;
          }



      }
      theBarre = new Barre (mois[i-1], (int)nbTacheOk,(int)nbTacheKo);
      this.ListeBarres.addElement(theBarre);

     }


  }
  public int getTachesAnomaliesbyMonth(String nomBase,Connexion myCnx, Statement st, Statement st2,  Equipe theEquipe, int annee ){
  int max = 0;  

    Barre theBarre = null;

    for (int i=1; i <= 12; i++)
    {
      int nbTacheOk=0;
      int nbTacheKo=0;
      theEquipe.getListeTachesByMonthByYear(nomBase,myCnx, st,i, annee);
      nbTacheOk=theEquipe.ListeTaches.size();
      nbTacheKo = theEquipe.getListeAnomaliesByMonthByYear(nomBase,myCnx, st,i, annee);

      if ((nbTacheOk + nbTacheKo) > max) max = nbTacheOk+nbTacheKo;
      
      theBarre = new Barre (mois[i-1], nbTacheOk,nbTacheKo);
      this.ListeBarres.addElement(theBarre);

     }
return max;

  }

  public int getTachesDelaisbyMonth(String nomBase,Connexion myCnx, Statement st, Statement st2,  Equipe theEquipe, int annee ){
    
    int max = 0;
    Barre theBarre = null;

    for (int i=1; i <= 12; i++)
    {
      int nbTacheOk=0;
      int nbTacheKo=0;
      theEquipe.getListeTachesByMonthByYear(nomBase,myCnx, st,i, annee);
      for (int j=0; j < theEquipe.ListeTaches.size(); j++)
      {
          Action theAction = (Action)theEquipe.ListeTaches.elementAt(j);
          long I_Cloture = 0;
          long I_End =0;

          try{
            Utils.getDateEnglish(theAction.dateCloture);
            String jourCloture = Utils.Day;
            String moisCloture = Utils.Month;
            String anneeCloture = Utils.Year;
            I_Cloture = Integer.parseInt(anneeCloture) * 365 +
                Integer.parseInt(moisCloture) * 31 + Integer.parseInt(jourCloture);
          }
          catch (Exception e){

          }

          try {
            //Utils.getDateEnglish(theAction.date_end);
            Utils.getDateEnglish(theAction.date_end_init);
            String jourEnd = Utils.Day;
            String moisEnd = Utils.Month;
            String anneeEnd = Utils.Year;
            I_End = Integer.parseInt(anneeEnd) * 365 + Integer.parseInt(moisEnd) * 31 +
                Integer.parseInt(jourEnd);
          }
          catch (Exception e){

          }

          if (I_Cloture <= I_End)
          {
            nbTacheOk++;
          }
          else
          {
            nbTacheKo++;
          }
          if ((nbTacheOk + nbTacheKo) > max) max = nbTacheOk+nbTacheKo;


      }

      theBarre = new Barre (mois[i-1], nbTacheOk,nbTacheKo);
      this.ListeBarres.addElement(theBarre);

     }
return max;

  }

  public void getStCreationKill(String nomBase,Connexion myCnx, Statement st,int anneeRef, int idSi ){
    
    Date toDay = new Date();
    Barre theBarre = null;
    ResultSet rs=null;
    int mois = -1;
    int annee = -1;
    int nbCreation = 0;
    int nbKill = 0;
    
    int bilanTotal=0;
 for ( annee=anneeRef -1; annee <= anneeRef; annee++)
  { 
    for (int i=1; i <= 12;i++)
    {
      if (i <= (toDay.getMonth() +1) || (annee == (anneeRef - 1)))
      {        
      String myMois = "";
      if (i < 10)
          myMois = "0"+i;
      else
          myMois = ""+i;
      
        theBarre = new Barre (annee +"_"+ myMois, 0,0);
        theBarre.valeur3=0;
        this.ListeBarres.addElement(theBarre);
      }
    }
  }

      req = "SELECT     COUNT(St.nomSt) AS nb, MONTH(VersionSt.creationVersionSt) AS mois, YEAR(VersionSt.creationVersionSt) AS annee";
      req+= " FROM         VersionSt INNER JOIN";
      req+= "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
      req+= "                       Etat AS EtatST ON VersionSt.etatVersionSt = EtatST.idEtat INNER JOIN";
      req+= "                       Etat AS EtatFICHE ON VersionSt.etatFicheVersionSt = EtatFICHE.idEtat INNER JOIN";
      req+= "                       TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi LEFT OUTER JOIN";
      req+= "                       Service AS Service_MOE ON VersionSt.serviceMoeVersionSt = Service_MOE.idService LEFT OUTER JOIN";
      req+= "                       Service AS Service_MOA ON VersionSt.serviceMoaVersionSt = Service_MOA.idService INNER JOIN";
      req+= "                       SI ON VersionSt.siVersionSt = SI.idSI LEFT OUTER JOIN";
      req+= "                       Membre AS Membre_MOE ON VersionSt.respMoeVersionSt = Membre_MOE.idMembre LEFT OUTER JOIN";
      req+= "                       Membre AS Membre_MOA ON VersionSt.respMoaVersionSt = Membre_MOA.idMembre";
      req+= " WHERE siVersionSt=" + idSi;
      req+= " GROUP BY MONTH(VersionSt.creationVersionSt), YEAR(VersionSt.creationVersionSt)";
      req+= " HAVING      (YEAR(VersionSt.creationVersionSt) = "+(anneeRef-1)+") OR";
      req+= "                       (YEAR(VersionSt.creationVersionSt) = "+(anneeRef)+")";
      
      req+= " ORDER BY annee, mois";
      
      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
          try {
             while (rs.next()) {
               nbCreation=rs.getInt("nb");
               mois = rs.getInt("mois");
               annee = rs.getInt("annee");
               
               int index = ((annee +1) - anneeRef) *12 + mois -1;
               if (index < this.ListeBarres.size())
               {
                theBarre = (Barre)this.ListeBarres.elementAt(index);
                theBarre.valeur1 = nbCreation;
                if (theBarre.valeur1 > this.max) this.max = theBarre.valeur1 ;
               }

             }
        } catch (SQLException s) {s.getMessage();}


      req = "SELECT     COUNT(St.nomSt) AS nb, MONTH(VersionSt.killVersionSt) AS mois, YEAR(VersionSt.killVersionSt) AS annee";
      req+= " FROM         VersionSt INNER JOIN";
      req+= "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
      req+= "                       Etat AS EtatST ON VersionSt.etatVersionSt = EtatST.idEtat INNER JOIN";
      req+= "                       Etat AS EtatFICHE ON VersionSt.etatFicheVersionSt = EtatFICHE.idEtat INNER JOIN";
      req+= "                       TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi LEFT OUTER JOIN";
      req+= "                       Service AS Service_MOE ON VersionSt.serviceMoeVersionSt = Service_MOE.idService LEFT OUTER JOIN";
      req+= "                       Service AS Service_MOA ON VersionSt.serviceMoaVersionSt = Service_MOA.idService INNER JOIN";
      req+= "                       SI ON VersionSt.siVersionSt = SI.idSI LEFT OUTER JOIN";
      req+= "                       Membre AS Membre_MOE ON VersionSt.respMoeVersionSt = Membre_MOE.idMembre LEFT OUTER JOIN";
      req+= "                       Membre AS Membre_MOA ON VersionSt.respMoaVersionSt = Membre_MOA.idMembre";
      req+= " WHERE siVersionSt=" + idSi;
      req+= " GROUP BY MONTH(VersionSt.killVersionSt), YEAR(VersionSt.killVersionSt)";
      req+= " HAVING      (YEAR(VersionSt.killVersionSt) = "+(anneeRef-1)+") OR";
      req+= "                       (YEAR(VersionSt.killVersionSt) = "+(anneeRef)+")";    
      req+= " ORDER BY annee, mois";
      
      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
          try {
             while (rs.next()) {
               nbKill=rs.getInt("nb");
               mois = rs.getInt("mois");
               annee = rs.getInt("annee");
               

               int index = ((annee +1) - anneeRef) *12 + mois -1;
               if (index < this.ListeBarres.size())
               {               
                theBarre = (Barre)this.ListeBarres.elementAt(index);
                theBarre.valeur2 = nbKill;
                if (theBarre.valeur2 > this.min) this.min = theBarre.valeur2 ;
               }
               
             }
        } catch (SQLException s) {s.getMessage();}          
          
 for ( int i = 0; i < this.ListeBarres.size();i++)
  { 
      theBarre = (Barre)this.ListeBarres.elementAt(i);
      theBarre.valeur3 = bilanTotal+theBarre.valeur1-theBarre.valeur2;
      bilanTotal += theBarre.valeur1-theBarre.valeur2;
      if (theBarre.valeur3 > this.max) this.max = theBarre.valeur3 ;
      if (theBarre.valeur3 < 0)
      {
        if ( (-1 * theBarre.valeur3) > this.min) this.min = (-1 * theBarre.valeur3);
      }
      //System.out.println(theBarre.nom+"/valeur1="+theBarre.valeur1+"/" +"valeur2="+theBarre.valeur2+"/ valeur3="+theBarre.valeur3+"/ bilanTotal="+bilanTotal);
      
  }         
      

     }
  
  public void getStCreationKillAll(String nomBase,Connexion myCnx, Statement st,int anneeRef ){
    
    Date toDay = new Date();
    Barre theBarre = null;
    ResultSet rs=null;
    int mois = -1;
    int annee = -1;
    int nbCreation = 0;
    int nbKill = 0;
    
    int bilanTotal=0;
 for ( annee=anneeRef -1; annee <= anneeRef; annee++)
  { 
    for (int i=1; i <= 12;i++)
    {
      if (i <= (toDay.getMonth() +1) || (annee == (anneeRef - 1)))
      {        
      String myMois = "";
      if (i < 10)
          myMois = "0"+i;
      else
          myMois = ""+i;
      
        theBarre = new Barre (annee +"/"+ myMois, 0,0);
        theBarre.valeur3=0;
        this.ListeBarres.addElement(theBarre);
      }
    }
  }

      req = "SELECT     COUNT(St.nomSt) AS nb, MONTH(VersionSt.creationVersionSt) AS mois, YEAR(VersionSt.creationVersionSt) AS annee";
      req+= " FROM         VersionSt INNER JOIN";
      req+= "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
      req+= "                       Etat AS EtatST ON VersionSt.etatVersionSt = EtatST.idEtat INNER JOIN";
      req+= "                       Etat AS EtatFICHE ON VersionSt.etatFicheVersionSt = EtatFICHE.idEtat INNER JOIN";
      req+= "                       TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi LEFT OUTER JOIN";
      req+= "                       Service AS Service_MOE ON VersionSt.serviceMoeVersionSt = Service_MOE.idService LEFT OUTER JOIN";
      req+= "                       Service AS Service_MOA ON VersionSt.serviceMoaVersionSt = Service_MOA.idService INNER JOIN";
      req+= "                       SI ON VersionSt.siVersionSt = SI.idSI LEFT OUTER JOIN";
      req+= "                       Membre AS Membre_MOE ON VersionSt.respMoeVersionSt = Membre_MOE.idMembre LEFT OUTER JOIN";
      req+= "                       Membre AS Membre_MOA ON VersionSt.respMoaVersionSt = Membre_MOA.idMembre";
      req+= " GROUP BY MONTH(VersionSt.creationVersionSt), YEAR(VersionSt.creationVersionSt), St.isRecurrent";
      req+= " HAVING      (YEAR(VersionSt.creationVersionSt) = "+(anneeRef-1)+") OR";
      req+= "                       (YEAR(VersionSt.creationVersionSt) = "+(anneeRef)+")";
      req+= " AND St.isRecurrent <> 1";
      req+= " ORDER BY annee, mois";
      
      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
          try {
             while (rs.next()) {
               nbCreation=rs.getInt("nb");
               mois = rs.getInt("mois");
               annee = rs.getInt("annee");
               
               int index = ((annee +1) - anneeRef) *12 + mois -1;
               if (index < this.ListeBarres.size())
               {
                theBarre = (Barre)this.ListeBarres.elementAt(index);
                theBarre.valeur1 = nbCreation;
               }

             }
        } catch (SQLException s) {s.getMessage();}

      req="SELECT     COUNT(nomSt) AS nb, MONTH(killVersionSt) AS mois, YEAR(killVersionSt) AS annee";
      req+= " FROM         ST_ListeSt_All_Attribute";
      req+= " GROUP BY MONTH(killVersionSt), YEAR(killVersionSt)";
      req+= " HAVING      (YEAR(killVersionSt) = "+(anneeRef-1)+") OR";
      req+= "                       (YEAR(killVersionSt) = "+(anneeRef)+")";
      req+= " ORDER BY annee, mois";

      req = "SELECT     COUNT(St.nomSt) AS nb, MONTH(VersionSt.killVersionSt) AS mois, YEAR(VersionSt.killVersionSt) AS annee";
      req+= " FROM         VersionSt INNER JOIN";
      req+= "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
      req+= "                       Etat AS EtatST ON VersionSt.etatVersionSt = EtatST.idEtat INNER JOIN";
      req+= "                       Etat AS EtatFICHE ON VersionSt.etatFicheVersionSt = EtatFICHE.idEtat INNER JOIN";
      req+= "                       TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi LEFT OUTER JOIN";
      req+= "                       Service AS Service_MOE ON VersionSt.serviceMoeVersionSt = Service_MOE.idService LEFT OUTER JOIN";
      req+= "                       Service AS Service_MOA ON VersionSt.serviceMoaVersionSt = Service_MOA.idService INNER JOIN";
      req+= "                       SI ON VersionSt.siVersionSt = SI.idSI LEFT OUTER JOIN";
      req+= "                       Membre AS Membre_MOE ON VersionSt.respMoeVersionSt = Membre_MOE.idMembre LEFT OUTER JOIN";
      req+= "                       Membre AS Membre_MOA ON VersionSt.respMoaVersionSt = Membre_MOA.idMembre";
      req+= " GROUP BY MONTH(VersionSt.killVersionSt), YEAR(VersionSt.killVersionSt), St.isRecurrent";
      req+= " HAVING      (YEAR(VersionSt.killVersionSt) = "+(anneeRef-1)+") OR";
      req+= "                       (YEAR(VersionSt.killVersionSt) = "+(anneeRef)+")";
      req+= " AND St.isRecurrent <> 1";
      req+= " ORDER BY annee, mois";
      
      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
          try {
             while (rs.next()) {
               nbKill=rs.getInt("nb");
               mois = rs.getInt("mois");
               annee = rs.getInt("annee");
               

               int index = ((annee +1) - anneeRef) *12 + mois -1;
               if (index < this.ListeBarres.size())
               {               
                theBarre = (Barre)this.ListeBarres.elementAt(index);
                theBarre.valeur2 = nbKill;
               }
               
             }
        } catch (SQLException s) {s.getMessage();}          
          
 for ( int i = 0; i < this.ListeBarres.size();i++)
  { 
      theBarre = (Barre)this.ListeBarres.elementAt(i);
      theBarre.valeur3 = bilanTotal+theBarre.valeur1-theBarre.valeur2;
      bilanTotal += theBarre.valeur1-theBarre.valeur2;
      //System.out.println(theBarre.nom+"/valeur1="+theBarre.valeur1+"/" +"valeur2="+theBarre.valeur2+"/ valeur3="+theBarre.valeur3+"/ bilanTotal="+bilanTotal);
      
  }         
      

     }  

  public void getStCreationKill(String nomBase,Connexion myCnx, Statement st,int anneeRef, String nomSi, String nomTypeSi1, String nomTypeSi2){
    
    Date toDay = new Date();
    Barre theBarre = null;
    ResultSet rs=null;
    int mois = -1;
    int annee = -1;
    int nbCreation = 0;
    int nbKill = 0;
    
    int bilanTotal=0;
 for ( annee=anneeRef -1; annee <= anneeRef; annee++)
  { 
    for (int i=1; i <= 12;i++)
    {
      if (i <= (toDay.getMonth() +1) || (annee == (anneeRef - 1)))
      {        
      String myMois = "";
      if (i < 10)
          myMois = "0"+i;
      else
          myMois = ""+i;
      
        theBarre = new Barre (annee +"/"+ myMois, 0,0);
        theBarre.valeur3=0;
        this.ListeBarres.addElement(theBarre);
      }
    }
  }
      
      String  req="SELECT     COUNT(nomSt) AS nb, MONTH(mepVersionSt) AS mois, YEAR(mepVersionSt) AS annee";
      req+= " FROM         ST_ListeSt_All_Attribute";
      req+= " GROUP BY MONTH(mepVersionSt), YEAR(mepVersionSt)";
      req+= " HAVING      (YEAR(mepVersionSt) = "+(anneeRef-1)+") OR";
      req+= "                       (YEAR(mepVersionSt) = "+(anneeRef)+")";
      req+= " ORDER BY annee, mois";

      req = "SELECT     COUNT(St.nomSt) AS nb, MONTH(VersionSt.creationVersionSt) AS mois, YEAR(VersionSt.creationVersionSt) AS annee";
      req+= " FROM         VersionSt INNER JOIN";
      req+= "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
      req+= "                       Etat AS EtatST ON VersionSt.etatVersionSt = EtatST.idEtat INNER JOIN";
      req+= "                       Etat AS EtatFICHE ON VersionSt.etatFicheVersionSt = EtatFICHE.idEtat INNER JOIN";
      req+= "                       TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi LEFT OUTER JOIN";
      req+= "                       Service AS Service_MOE ON VersionSt.serviceMoeVersionSt = Service_MOE.idService LEFT OUTER JOIN";
      req+= "                       Service AS Service_MOA ON VersionSt.serviceMoaVersionSt = Service_MOA.idService INNER JOIN";
      req+= "                       SI ON VersionSt.siVersionSt = SI.idSI LEFT OUTER JOIN";
      req+= "                       Membre AS Membre_MOE ON VersionSt.respMoeVersionSt = Membre_MOE.idMembre LEFT OUTER JOIN";
      req+= "                       Membre AS Membre_MOA ON VersionSt.respMoaVersionSt = Membre_MOA.idMembre";
      req+= " WHERE     (TypeSi.nomTypeSi = '"+nomTypeSi1+"') AND (SI.nomSI = '"+nomSi+"') OR";
      req+= "                       (TypeSi.nomTypeSi = '"+nomTypeSi2+"')";
      req+= " GROUP BY MONTH(VersionSt.creationVersionSt), YEAR(VersionSt.creationVersionSt)";
      req+= " HAVING      (YEAR(VersionSt.creationVersionSt) = "+(anneeRef-1)+") OR";
      req+= "                       (YEAR(VersionSt.creationVersionSt) = "+(anneeRef)+")";
      req+= " ORDER BY annee, mois";
      
      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
          try {
             while (rs.next()) {
               nbCreation=rs.getInt("nb");
               mois = rs.getInt("mois");
               annee = rs.getInt("annee");
               
               int index = ((annee +1) - anneeRef) *12 + mois -1;
               if (index < this.ListeBarres.size())
               {
                theBarre = (Barre)this.ListeBarres.elementAt(index);
                theBarre.valeur1 = nbCreation;
               }

             }
        } catch (SQLException s) {s.getMessage();}

      req="SELECT     COUNT(nomSt) AS nb, MONTH(killVersionSt) AS mois, YEAR(killVersionSt) AS annee";
      req+= " FROM         ST_ListeSt_All_Attribute";
      req+= " GROUP BY MONTH(killVersionSt), YEAR(killVersionSt)";
      req+= " HAVING      (YEAR(killVersionSt) = "+(anneeRef-1)+") OR";
      req+= "                       (YEAR(killVersionSt) = "+(anneeRef)+")";
      req+= " ORDER BY annee, mois";

      req = "SELECT     COUNT(St.nomSt) AS nb, MONTH(VersionSt.killVersionSt) AS mois, YEAR(VersionSt.killVersionSt) AS annee";
      req+= " FROM         VersionSt INNER JOIN";
      req+= "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
      req+= "                       Etat AS EtatST ON VersionSt.etatVersionSt = EtatST.idEtat INNER JOIN";
      req+= "                       Etat AS EtatFICHE ON VersionSt.etatFicheVersionSt = EtatFICHE.idEtat INNER JOIN";
      req+= "                       TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi LEFT OUTER JOIN";
      req+= "                       Service AS Service_MOE ON VersionSt.serviceMoeVersionSt = Service_MOE.idService LEFT OUTER JOIN";
      req+= "                       Service AS Service_MOA ON VersionSt.serviceMoaVersionSt = Service_MOA.idService INNER JOIN";
      req+= "                       SI ON VersionSt.siVersionSt = SI.idSI LEFT OUTER JOIN";
      req+= "                       Membre AS Membre_MOE ON VersionSt.respMoeVersionSt = Membre_MOE.idMembre LEFT OUTER JOIN";
      req+= "                       Membre AS Membre_MOA ON VersionSt.respMoaVersionSt = Membre_MOA.idMembre";
      req+= " WHERE     (TypeSi.nomTypeSi = '"+nomTypeSi1+"') AND (SI.nomSI = '"+nomSi+"') OR";
      req+= "                       (TypeSi.nomTypeSi = '"+nomTypeSi2+"')";
      req+= " GROUP BY MONTH(VersionSt.killVersionSt), YEAR(VersionSt.killVersionSt)";
      req+= " HAVING      (YEAR(VersionSt.killVersionSt) = "+(anneeRef-1)+") OR";
      req+= "                       (YEAR(VersionSt.killVersionSt) = "+(anneeRef)+")";
      req+= " ORDER BY annee, mois";
      
      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
          try {
             while (rs.next()) {
               nbKill=rs.getInt("nb");
               mois = rs.getInt("mois");
               annee = rs.getInt("annee");
               

               int index = ((annee +1) - anneeRef) *12 + mois -1;
               if (index < this.ListeBarres.size())
               {               
                theBarre = (Barre)this.ListeBarres.elementAt(index);
                theBarre.valeur2 = nbKill;
               }
               
             }
        } catch (SQLException s) {s.getMessage();}          
          
 for ( int i = 0; i < this.ListeBarres.size();i++)
  { 
      theBarre = (Barre)this.ListeBarres.elementAt(i);
      theBarre.valeur3 = bilanTotal+theBarre.valeur1-theBarre.valeur2;
      bilanTotal += theBarre.valeur1-theBarre.valeur2;
      //System.out.println(theBarre.nom+"/valeur1="+theBarre.valeur1+"/" +"valeur2="+theBarre.valeur2+"/ valeur3="+theBarre.valeur3+"/ bilanTotal="+bilanTotal);
      
  }         
      

     }   

  public void getStCreationKill2(String nomBase,Connexion myCnx, Statement st,int anneeRef ){
    
    Date toDay = new Date();
    Barre theBarre = null;
    ResultSet rs=null;
    
    int bilanTotal=0;

 for (int annee=anneeRef -1; annee <= anneeRef; annee++)
  {    
    for (int i=1; i <= 12; i++)
    {
        int nbCreation=0;
        int nbKill=0;
      /*
      if (i == 1)
        theBarre = new Barre ("*"+annee +"/"+ mois[i-1], -1,-1);
      else
        theBarre = new Barre (annee +"/"+ mois[i-1], -1,-1);
      */
      String myMois = "";
      if (i < 10)
          myMois = "0"+i;
      else
          myMois = ""+i;
      theBarre = new Barre (annee +"/"+ myMois, -1,-1);
      if (i > (toDay.getMonth() +1) && (annee == anneeRef))
      {
       theBarre.valeur1=nbCreation;   
       theBarre.valeur2=nbKill;
       theBarre.valeur3=bilanTotal;
       continue;
      }
      
      String  req="SELECT     COUNT(*) AS nb";
        req+=" FROM         ST_ListeSt_All_Attribute";
        req+=" WHERE     (YEAR(mepVersionSt) = "+annee+") AND (MONTH(mepVersionSt) = "+i+")";

        rs = myCnx.ExecReq(st, myCnx.nomBase, req);
          try {
             while (rs.next()) {
                 nbCreation=rs.getInt("nb");
               theBarre.valeur1=nbCreation;
               bilanTotal += theBarre.valeur1;

             }
        } catch (SQLException s) {s.getMessage();}
          
       req="SELECT     COUNT(*) AS nb";
        req+=" FROM         ST_ListeSt_All_Attribute";
        req+=" WHERE     (YEAR(killVersionSt) = "+annee+") AND (MONTH(killVersionSt) = "+i+")";

        
        rs = myCnx.ExecReq(st, myCnx.nomBase, req);
          try {
             while (rs.next()) {
                 nbKill=rs.getInt("nb");
               theBarre.valeur2=nbKill;
               bilanTotal -= theBarre.valeur2;
             }
        } catch (SQLException s) {s.getMessage();}        

          
        theBarre.valeur3 = bilanTotal;
          
      
      this.ListeBarres.addElement(theBarre);

     }

    }
  }  
  
   public ErrorSpecific bd_DeleteGantCache(String nomBase,Connexion myCnx, Statement st, String transaction, int Type){
  ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;

  String req = "exec SP_DevisDeleteJalonsById " + this.id+ ","+Type;
  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_DeleteJalons",""+this.id);

  return myError;
}
  
  public ErrorSpecific bd_insertGantCache(String nomBase,Connexion myCnx, Statement st,int anneeRef, String transaction ){
    
     ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

   myError=bd_DeleteGantCache( nomBase, myCnx,  st,  transaction, -1);
   if (myError.cause == -1) return myError;

   String req="";

     for (int i=0; i < this.ListeBarres.size(); i++)
     {
       Barre theBarre = (Barre)this.ListeBarres.elementAt(i);
       int annee = 2014;
       int mois = 1;


           req = "INSERT ChartSt (";
             req+="Annee"+",";
             req+="Mois"+",";
             req+="nbCreation"+",";
             req+="nbKill"+",";
             req+="cumul";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+annee+"',";
             req+="'"+mois+"',";             
             req+="'"+theBarre.valeur1+"',";
             req+="'"+theBarre.valeur2+"',";
             req+="'"+theBarre.valeur3;

           req+=")";

           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.nom);
           if (myError.cause == -1) return myError;

       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_insertGantCache",""+this.id);
       if (myError.cause == -1) return myError;
     }

     return myError;
     }  
  
  public void getStCreationKillFromCache(String nomBase,Connexion myCnx, Statement st,int anneeRef ){
    
    Date toDay = new Date();
    Barre theBarre = null;
    ResultSet rs=null;
    
    int bilanTotal=0;


      
      String  req="SELECT     Annee, Mois, nbCreation, nbKill, cumul";
      req+=" FROM         ChartSt";
      req+=" WHERE     (Annee = "+(anneeRef -1)+") OR  (Annee = "+anneeRef+")";

      
        rs = myCnx.ExecReq(st, myCnx.nomBase, req);
        boolean fini=false;
          try {
             while (rs.next()) {
                 int Annee = -1;
                 int Mois = -1;
                 
                 if (!fini)
                 {
                     theBarre = new Barre ("", -1,-1);
                     Annee = rs.getInt("Annee");
                     Mois = rs.getInt("Mois");
                     if (Mois < 10)
                        theBarre.nom=Annee+"/"+"0"+Mois;
                     else
                        theBarre.nom=Annee+"/"+Mois; 
                     
                     theBarre.valeur1=rs.getInt("nbCreation");
                     theBarre.valeur2=rs.getInt("nbKill");
                     theBarre.valeur3=rs.getInt("cumul");
                     
                     this.ListeBarres.addElement(theBarre);
                     
                     if (Mois > (toDay.getMonth() +1) && (Annee == anneeRef))
                     {
                       fini = true;
                     }
                     else
                     {
                         this.ListeBarres.addElement(theBarre);
                     }
                     
                 }

             }
        } catch (SQLException s) {s.getMessage();}
          

          
          
      this.ListeBarres.addElement(theBarre);

     }

  
  public void getCampagneTest(String nomBase,Connexion myCnx, Statement st, Statement st2, Vector ListeCampagnes ){
    Barre theBarre = null;
    for (int i=0; i < ListeCampagnes.size(); i++)
    {
      Campagne theCampagne = (Campagne)ListeCampagnes.elementAt(i);

      theBarre = new Barre (theCampagne.version, (int)theCampagne.IA1, (int)theCampagne.IA2);

      this.ListeBarres.addElement(theBarre);
     }


  }

  public void getCampagneRelecture(String nomBase,Connexion myCnx, Statement st, Statement st2, Vector ListeCampagnes ){

    Barre theBarre = null;
    for (int i=0; i < ListeCampagnes.size(); i++)
    {
      CampagneRelecture theCampagne = (CampagneRelecture)ListeCampagnes.elementAt(i);

      theBarre = new Barre (theCampagne.Login, (int)theCampagne.IA1, (int)theCampagne.IA2);

      this.ListeBarres.addElement(theBarre);
     }


  }

  public void getSTbyMOA3(String nomBase,Connexion myCnx, Statement st, Statement st2 ){
    ResultSet rs=null;
    ResultSet rs2=null;
    Barre theBarre = null;
    String req="SELECT DISTINCT Service.idService, Service.aliasGraphe, Service.typeService";
           req+=" FROM         ListeST INNER JOIN";
           req+="            SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
           req+="            Service ON ListeST.serviceMoaVersionSt = Service.idService";
           req+=" WHERE     (SI.nomSI = 'SIR') AND (Service.typeService LIKE '%MOA%')";
           req+=" ORDER BY Service.aliasGraphe";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      try {
         while (rs.next()) {
           int idService=rs.getInt("idService");
           String nomService=rs.getString("aliasGraphe");

           theBarre = new Barre (nomService, -1, -1);

           // -- extraction des SI Rouges
           req="SELECT     COUNT(*) AS nb";
           req+=" FROM        ListeST INNER JOIN";
           req+="            TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
           req+=" WHERE     (ListeST.serviceMoaVersionSt = "+idService+") AND (TypeSi.nomTypeSi = 'SI Rouge')";

           rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);
             try {
                while (rs2.next()) {
                  theBarre.valeur2=rs2.getInt("nb");

                }
        } catch (SQLException s) {s.getMessage();}

        // -- extraction des SI Bleus
        req="SELECT     COUNT(*) AS nb";
        req+=" FROM        ListeST INNER JOIN";
        req+="            TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
        req+=" WHERE     (ListeST.serviceMoaVersionSt = "+idService+") AND (TypeSi.nomTypeSi = 'SI Bleu')";

        rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);
          try {
             while (rs2.next()) {
               theBarre.valeur1=rs2.getInt("nb");

             }
        } catch (SQLException s) {s.getMessage();}

        if ( (theBarre.valeur1 + theBarre.valeur2) > 5)
          this.ListeBarres.addElement(theBarre);

         }
        } catch (SQLException s) {s.getMessage();}

  }

  public void getSTbySI(String nomBase,Connexion myCnx, Statement st, Statement st2 ){
    ResultSet rs=null;
    ResultSet rs2=null;
    Barre theBarre = null;
    String req="exec LISTEST_nbSiBleu";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      try {
         while (rs.next()) {
           String nomSI=rs.getString("nomSI");
           int nb=rs.getInt("nbST");

           theBarre = new Barre (nomSI, nb, -1);

           req="SELECT     SI.nomSI, COUNT(St.nomSt) AS nbST";
           req+="  FROM         St INNER JOIN";
           req+="            VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
           req+="            SI ON VersionSt.siVersionSt = SI.idSI INNER JOIN";
           req+="            TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi";
           req+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (VersionSt.versionRefVersionSt =";
           req+="                (SELECT     MAX(versionRefVersionSt) AS Expr1";
           req+="                  FROM          VersionSt AS VersionSt_1";
           req+="                  WHERE      (etatFicheVersionSt = 3) AND (stVersionSt = St.idSt)))";
           req+=" GROUP BY SI.nomSI, SI.scope, TypeSi.nomTypeSi, St.isST";
           req+=" HAVING      (SI.scope = 1) AND (TypeSi.nomTypeSi = 'SI Rouge') AND (St.isST = 1) AND (SI.nomSI = '"+nomSI+"')";

           rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);
             try {
                while (rs2.next()) {
                  nomSI=rs2.getString("nomSI");
                  nb=rs2.getInt("nbST");

                  theBarre.valeur2 = nb;

                  this.ListeBarres.addElement(theBarre);
                }
        } catch (SQLException s) {s.getMessage();}

         }
        } catch (SQLException s) {s.getMessage();}

  }

  public void getSTbyMacroST(String nomBase,Connexion myCnx, Statement st, Statement st2, String nomSI ){
    ResultSet rs=null;
    ResultSet rs2=null;
    Barre theBarre = null;


    // --------------------------- Creation du SI ------------------------------------------------------//
    SI theSI = new SI(nomSI);
    //theSI.dump();
    // -------------------------------------------------------------------------------------------------//

    // --------------- D�composition des SI (Rouge/Bleu et ST/Appli) par Macro-St ----------------------//
    theSI.getNbStbyMacroStByTypeSi(myCnx.nomBase,myCnx,  st, "SI Rouge" ) ;
    // -------------------------------------------------------------------------------------------------//

    String req="exec getNbStbyMacroStByTypeSi";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    for (int i=0; i< theSI.ListeMacroSt.size();i++)
    {
	MacroSt theMacroSt = (MacroSt)theSI.ListeMacroSt.elementAt(i);

           theBarre = new Barre (theMacroSt.nom, theMacroSt.nbStRouge, -1);
           this.ListeBarres.addElement(theBarre);
    }


  }

  public void getSTbyPointFonction(String nomBase,Connexion myCnx, Statement st, Statement st2, String nomSI, int  seuil){
    ResultSet rs=null;
    ResultSet rs2=null;
    Barre theBarre = null;


    // --------------------------- Creation du SI ------------------------------------------------------//
    SI theSI = new SI(nomSI);
    //theSI.dump();
    // -------------------------------------------------------------------------------------------------//

    // --------------- D�composition des SI (Rouge/Bleu et ST/Appli) par Macro-St ----------------------//
    theSI.getPF_LC(myCnx.nomBase,myCnx,  st) ;
    theSI.getBilanPF_LCByType(myCnx.nomBase,myCnx,  st, "ST") ;
    // -------------------------------------------------------------------------------------------------//
    int nbTotal = 0;

    int max = Math.min(seuil,theSI.ListeSt.size() );
    for (int i=0; i< max;i++)
    {
        ST theST = (ST)theSI.ListeSt.elementAt(i);

           theBarre = new Barre (theST.nomSt, theST.nbPtsFctVersionSt, -1);
           this.ListeBarres.addElement(theBarre);
           nbTotal +=theST.nbPtsFctVersionSt;
    }

    theBarre = new Barre ("Autres ST", theSI.totalPF - nbTotal, -1);
    this.ListeBarres.addElement(theBarre);
  }

  public void getSTbyCriticiteNbUtilisateurs(String nomBase,Connexion myCnx, Statement st, Statement st2, String nomSI, int  seuil){
    ResultSet rs=null;
    ResultSet rs2=null;
    Barre theBarre = null;


    // --------------------------- Creation du SI ------------------------------------------------------//
    SI theSI = new SI(nomSI);
    //theSI.dump();
    // -------------------------------------------------------------------------------------------------//

    // --------------- D�composition des SI (Rouge/Bleu et ST/Appli) par Macro-St ----------------------//
    theSI.getBilanCriticiteNbUtilisateur(myCnx.nomBase,myCnx,  st) ;
    // -------------------------------------------------------------------------------------------------//
    int nbTotal = 0;

    int max = Math.min(seuil,theSI.ListeSt.size() );
    for (int i=0; i< max;i++)
    {
        ST theST = (ST)theSI.ListeSt.elementAt(i);

           theBarre = new Barre (theST.nomSt, theST.nbUtilVersionSt, -1);
           this.ListeBarres.addElement(theBarre);
           nbTotal +=theST.nbLigCodeVersionSt;
    }

    theBarre = new Barre ("Autres ST", theSI.totalLC - nbTotal, -1);
    this.ListeBarres.addElement(theBarre);
  }
  public void getSTbyLigneCode(String nomBase,Connexion myCnx, Statement st, Statement st2, String nomSI, int  seuil){
    ResultSet rs=null;
    ResultSet rs2=null;
    Barre theBarre = null;


    // --------------------------- Creation du SI ------------------------------------------------------//
    SI theSI = new SI(nomSI);
    //theSI.dump();
    // -------------------------------------------------------------------------------------------------//

    // --------------- D�composition des SI (Rouge/Bleu et ST/Appli) par Macro-St ----------------------//
    theSI.getPF_LC(myCnx.nomBase,myCnx,  st) ;
    theSI.getBilanPF_LCByTypeOrderLC(myCnx.nomBase,myCnx,  st, "ST") ;
    // -------------------------------------------------------------------------------------------------//
    int nbTotal = 0;

    int max = Math.min(seuil,theSI.ListeSt.size() );
    for (int i=0; i< max;i++)
    {
        ST theST = (ST)theSI.ListeSt.elementAt(i);

           theBarre = new Barre (theST.nomSt, theST.nbLigCodeVersionSt, -1);
           this.ListeBarres.addElement(theBarre);
           nbTotal +=theST.nbLigCodeVersionSt;
    }

    theBarre = new Barre ("Autres ST", theSI.totalLC - nbTotal, -1);
    this.ListeBarres.addElement(theBarre);
  }

  public void getTachesbyState(String nomBase,Connexion myCnx, Statement st, Statement st2, Equipe theEquipe, int annee){
    ResultSet rs=null;
    ResultSet rs2=null;
    Barre theBarre = null;

    theEquipe.getListeTachesDistinctes(nomBase,myCnx, st,st2);

    String req="";
    req = "SELECT     TypeEtatAction.nom, COUNT(TypeEtatAction.nom) AS nb";
    req+="    FROM         tmpTaches INNER JOIN";
    req+="                   TypeEtatAction ON tmpTaches.idEtat = TypeEtatAction.id";
    req+=" GROUP BY TypeEtatAction.nom, YEAR(tmpTaches.dateAction)";
    req+=" HAVING      (YEAR(tmpTaches.dateAction) = "+annee+")";
    req+="     ORDER BY nb desc";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while(rs.next()) {
        String nomBarre = rs.getString("nom");
        int valeurBarre = rs.getInt("nb");

           theBarre = new Barre (nomBarre, valeurBarre, -1);
           this.ListeBarres.addElement(theBarre);

        }
      }catch (Exception e){};



  }

  public void getTachesbySt(String nomBase,Connexion myCnx, Statement st, Equipe theEquipe, int theYearRef){
    ResultSet rs=null;
    ResultSet rs2=null;
    Barre theBarre = null;
    
    float total=0;
    float top=0;

    String req="";
    // caclu de la somme globale
    
    req="    SELECT     SUM(Consomme.Charge) AS totalCharges";
    req+="         FROM         actionSuivi INNER JOIN";
    req+="                        Consomme ON actionSuivi.id = Consomme.idAction INNER JOIN";
    req+="                        Roadmap ON actionSuivi.idRoadmapLie = Roadmap.idRoadmap INNER JOIN";
    req+="                        VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt";
    req+="       WHERE (projetMembre in";
    req+="       (";
    req+="      SELECT     Membre.idMembre";
    req+="      FROM         Equipe INNER JOIN";
    req+="                         EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    req+="                         Membre ON EquipeMembre.idMembre = Membre.idMembre";
    req+="       WHERE     (EquipeMembre.type = 'Collaborateur') AND (Equipe.id = "+theEquipe.id+")";
    req+= "))";
     rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while(rs.next()) {
         total = rs.getFloat("totalCharges");

        }
      }catch (Exception e){};   
    
    
    req = "SELECT     St.nomSt, SUM(Consomme.Charge) AS totalCharges";
    req+="    FROM         actionSuivi INNER JOIN";
    req+="                   Consomme ON actionSuivi.id = Consomme.idAction INNER JOIN";
    req+="                   Roadmap ON actionSuivi.idRoadmapLie = Roadmap.idRoadmap INNER JOIN";
    req+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                   St ON VersionSt.stVersionSt = St.idSt";
    //req+=" WHERE     (Consomme.anneeRef = "+theYearRef+")";

    req+="  WHERE (projetMembre in";
    req+="  (";
    req+="  SELECT     Membre.idMembre";
    req+="  FROM         Equipe INNER JOIN";
    req+="                    EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    req+="                    Membre ON EquipeMembre.idMembre = Membre.idMembre";
    req+="  WHERE     (EquipeMembre.type = 'Collaborateur') AND (Equipe.id = "+theEquipe.id+")";
    req+="  )";
    req+="  ) ";

    req+=" GROUP BY St.nomSt";
    req+="     ORDER BY totalCharges desc";


    Barre otherBarre = new Barre ("Autres", 0, -1);

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while(rs.next()) {
        String nomBarre = rs.getString("nomSt");
        float valeurBarre = (100 * rs.getFloat("totalCharges")/ total);

        //System.out.println("nomBarre="+nomBarre + "/ valeur= "+ valeurBarre);
           if (valeurBarre > 5)
           {
           theBarre = new Barre (nomBarre, (Math.round(valeurBarre)), -1);
           this.ListeBarres.addElement(theBarre);
           top+=valeurBarre;
           }             
        }
      }catch (Exception e){};
      
      otherBarre.valeur1 = (int) Math.round(100.0 - top);

      if (otherBarre.valeur1 >0)
      {
          this.ListeBarres.addElement(otherBarre);
      }

  }
  
    public void getTachesbySt2(String nomBase,Connexion myCnx, Statement st, Equipe theEquipe, int theYearRef){
    ResultSet rs=null;
    ResultSet rs2=null;
    Barre theBarre = null;

    String req="";
    req = "SELECT     St.nomSt, SUM(Consomme.Charge) AS totalCharges";
    req+="    FROM         actionSuivi INNER JOIN";
    req+="                   Consomme ON actionSuivi.id = Consomme.idAction INNER JOIN";
    req+="                   Roadmap ON actionSuivi.idRoadmapLie = Roadmap.idRoadmap INNER JOIN";
    req+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                   St ON VersionSt.stVersionSt = St.idSt";
    //req+=" WHERE     (Consomme.anneeRef = "+theYearRef+")";

    req+="  WHERE (projetMembre in";
    req+="  (";
    req+="  SELECT     Membre.idMembre";
    req+="  FROM         Equipe INNER JOIN";
    req+="                    EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    req+="                    Membre ON EquipeMembre.idMembre = Membre.idMembre";
    req+="  WHERE     (EquipeMembre.type = 'Collaborateur') AND (Equipe.id = "+theEquipe.id+")";
    req+="  )";
    req+="  ) ";

    req+=" GROUP BY St.nomSt";
    req+="     ORDER BY totalCharges desc";




    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while(rs.next()) {
        String nomBarre = rs.getString("nomSt");
        int valeurBarre = rs.getInt("totalCharges");

           theBarre = new Barre (nomBarre, valeurBarre, -1);
           this.ListeBarres.addElement(theBarre);

        }
      }catch (Exception e){};



  }

  public void getTachesbyTypeProjet(String nomBase,Connexion myCnx, Statement st, Equipe theEquipe, int theYearRef, int theWeekRef){
    ResultSet rs=null;
    ResultSet rs2=null;
    Barre theBarre = null;
    float total=0;
    float top=0;
    String req="";
    
    req="    SELECT     SUM(PlanDeCharge.Charge) AS totalCharges";
    req+="         FROM         PlanDeCharge INNER JOIN";
    req+="                        Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                        VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                        St ON VersionSt.stVersionSt = St.idSt";
    req+="      WHERE (projetMembre in";
    req+="      (";
    req+="     SELECT     Membre.idMembre";
    req+="      FROM         Equipe INNER JOIN";
    req+="                        EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    req+="                        Membre ON EquipeMembre.idMembre = Membre.idMembre";
    req+="      WHERE     (EquipeMembre.type = 'Collaborateur') AND (Equipe.id = "+theEquipe.id+")";
    req+="      )";
    req+="      ) ";
    
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while(rs.next()) {
         total = rs.getFloat("totalCharges");

        }
      }catch (Exception e){};       
    
    
    req = "SELECT     St.nomSt + '-' + Roadmap.version AS projet, SUM(PlanDeCharge.Charge) AS totalCharges";
    req+="    FROM         PlanDeCharge INNER JOIN";
    req+="                   Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                   St ON VersionSt.stVersionSt = St.idSt";

    req+=" WHERE (projetMembre in";
    req+=" (";
    req+=" SELECT     Membre.idMembre";
    req+=" FROM         Equipe INNER JOIN";
    req+="                   EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    req+="                   Membre ON EquipeMembre.idMembre = Membre.idMembre";
    req+=" WHERE     (EquipeMembre.type = 'Collaborateur') AND (Equipe.id = "+theEquipe.id+")";
    req+=" )";
    req+=" )                ";
    req+=" GROUP BY St.nomSt + '-' + Roadmap.version";
    req+=" ORDER BY totalCharges desc";


    Barre otherBarre = new Barre ("Autres", 0, -1);

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while(rs.next()) {
        String nomBarre = rs.getString("projet");
        float valeurBarre = (100 * rs.getFloat("totalCharges")/ total);

        //System.out.println("nomBarre="+nomBarre + "/ valeur= "+ valeurBarre);
           if (valeurBarre > 5)
           {
           theBarre = new Barre (nomBarre, (Math.round(valeurBarre)), -1);
           this.ListeBarres.addElement(theBarre);
           top+=valeurBarre;
           }             
        }
      }catch (Exception e){};
      
      otherBarre.valeur1 = (int) Math.round(100.0 - top);

      if (otherBarre.valeur1 >0)
      {
          this.ListeBarres.addElement(otherBarre);
      }


  }
  
  public void getTachesbyTypeProjet2(String nomBase,Connexion myCnx, Statement st, Equipe theEquipe, int theYearRef, int theWeekRef){
    ResultSet rs=null;
    ResultSet rs2=null;
    Barre theBarre = null;

    String req="";
    req = "SELECT     St.nomSt + '-' + Roadmap.version AS projet, SUM(PlanDeCharge.Charge) AS totalCharges";
    req+="    FROM         PlanDeCharge INNER JOIN";
    req+="                   Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                   St ON VersionSt.stVersionSt = St.idSt";

    req+=" WHERE (projetMembre in";
    req+=" (";
    req+=" SELECT     Membre.idMembre";
    req+=" FROM         Equipe INNER JOIN";
    req+="                   EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    req+="                   Membre ON EquipeMembre.idMembre = Membre.idMembre";
    req+=" WHERE     (EquipeMembre.type = 'Collaborateur') AND (Equipe.id = "+theEquipe.id+")";
    req+=" )";
    req+=" )                ";
    req+=" GROUP BY St.nomSt + '-' + Roadmap.version";
    req+=" ORDER BY totalCharges desc";



    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while(rs.next()) {
        String nomBarre = rs.getString("projet");
        int valeurBarre = rs.getInt("totalCharges");

           theBarre = new Barre (nomBarre, valeurBarre, -1);
           this.ListeBarres.addElement(theBarre);

        }
      }catch (Exception e){};



  }
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("nom="+this.nom);
    System.out.println("this.ListeBarres.size()="+this.ListeBarres.size());
    for (int i=0; i < this.ListeBarres.size(); i++)
    {
      Barre theBarre = (Barre)this.ListeBarres.elementAt(i);
      theBarre.dump();
    }
    System.out.println("==================================================");
  }

  public static void main(String[] args) {
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

    /// --------------------------- Cr�ation du graphe--------------------------------------------------------//
        Gantt theGantt_StKillCreation = new Gantt("ST Kill et Creation");
        theGantt_StKillCreation.getStCreationKill(myCnx.nomBase,myCnx,st, 2014, 1);
        //theGantt_StKillCreation.getStCreationKillFromCache(myCnx.nomBase,myCnx,st, 2014);

    //theGantt.getProjetsbyMonth(myCnx.nomBase,myCnx,st);
    //theGantt.getSTbyMOA(myCnx.nomBase,myCnx,st, st2);
    //theGantt.getSTbyPointFonction(myCnx.nomBase,myCnx,st, st2, "SIR", 12);
    //Equipe theEquipe = new Equipe(372);
    //theEquipe.getAllFromBd(myCnx.nomBase,myCnx,st);
    //theEquipe.getListeMembres(myCnx.nomBase,myCnx,st);
    //theGantt.getTachesChargesbyMonth(myCnx.nomBase,myCnx,st, st2,   theEquipe, 2013);
    //theGantt.getTachesDelaisbyMonth(myCnx.nomBase,myCnx,st, st2,   theEquipe, 2013);
    //theGantt.dump();
    //myCnx.trace("@---------------------------","theGantt.ListeBarres.size()",""+theGantt.ListeBarres.size());
// ---------------------------------------------------------------------------------------------------------//

    myCnx.Unconnect(st);
    myCnx.Unconnect(st2);

  }
}

package ST; 
import General.Utils;

import java.util.*;
import java.sql.*;
import accesbase.*;
import java.util.Date;
import accesbase.Connexion;
import Graphe.*;
import Documentation.*;
import OM.OM;
import Projet.Roadmap;
import Test.Test;
import Projet.Action;
import Organisation.Collaborateur;
import Composant.item;
import Organisation.Competence;
import java.awt.*;
import Processus.Activite;
import Processus.Processus;
import OM.OM;

public class ST extends Forme{
  public ST StAncrage=null;

  public static Vector ListeExcel = new Vector(10);
// --------------- Pour le clacul de la note de remplissage de chaque ST -----------------------//
  public static int noteGlobale = 0;
  public String CouleurAvancement = "";
  public int noteAvancement = 0;
  public int noteModule=0;
  public int noteDescription=0;
  public int noteMetrologie=0;
  public int noteInterface=0;
// --------------------------------------------------------------------------------------------//

  public Node NodeSt=null;
  public String paramNode="";
  public String paramLinks="";

  public int numStOrigine;
  public String Origine="";
  public String theStyle="";


  public int idSt=-1;
  public String nomSt = "";

  public String old_nomSt = "";
  public String typeForm = "";
  public String action = "";

  public String trigrammeSt;
  public int chronoSt;
  public int typeAppliSt;
  public String Criticite = "Mineur";
  public String ClefImport;
  public String diagramme;

  public int isActeur;
  public int isAppli;
  public int isST;
  public int isComposant;
  public int isEquipement;



  public int isRecurrent;
  public int isMeeting;
  public String typeSpinoza;

  public int idVersionSt=-1;
  public int stVersionSt;
  public int numVersionSt;

  public int etatFicheVersionSt = 5;
  public String nomEtat;
  public String nomEtatFiche;

  public int siVersionSt;
  public int typeSiVersionSt;
  public String versionRefVersionSt = "";
  public String versionProd = "";
  public String versionProd1 = "";

  public Date creationVersionSt;
  public String str_creationVersionSt = "";

  public Date derMajVersionSt;
  public String str_derMajVersionSt;

  public String descVersionSt = "";
  public String deltaVersionSt;
  
  public int macrostVersionSt;
  public int macrostTecVersionSt;
  
  public int phaseNPVersionSt;
  public int sousPhaseNPVersionSt;
  public int typeArchiVersionSt;
  public int osVersionSt;
  public int bdVersionSt;
  public int hebergVersionSt;
  public int gestionUtilVersionSt;
  public int nbLigCodeVersionSt;
  public int nbPtsFctVersionSt;

  public int iReagirVersionSt;
  public String str_iReagirVersionSt;

  public int serviceMoeVersionSt;
  public int respMoeVersionSt;
  public int serviceMoaVersionSt;
  public int serviceExploitVersionSt;
  public int serviceMetier;
  public String nomServiceMetier;

  public Date mepVersionSt;
  public String dateMep = "";
  public String dateMepRoadmap = "";
  public String dateMep1Roadmap = "";

  public int etatVersionSt;
  public int coutFabrVersionSt;
  public int crenUtilVersionSt;
  public boolean externalVersionSt;
  public String lienManuelVersionSt = "";
  public String titreLienManuelVersionSt = "";
  public boolean derVersionSt;
  public int auteurVersionSt;
  public int precVersionSt;
  public int suivVersionSt;
  public int nbUtilVersionSt;
  public int respMoaVersionSt;

  public Date killVersionSt;
  public String dateKill = "";

  public int okMoaVersionSt;
  public int okMoeVersionSt;
  public String STRokMoaVersionSt;

  public Date mep1VersionSt;
  public String dateMep1 = "";

  public String createurVersionSt;
  public String modificateurVersionSt;
  public String roadmap;

  public String Mobilite = "";
  public String displayMobilitePda = "";
  public String displayMobiliteImode = "";

  public String Externalisation = "";
  public String displayExternalisation;

  public Date dateEB;
  public String str_dateEB;

  public String ContactOper = "";
  public String ContactMOE = "";
  public String ModeAcq = "";


  public String couleur;
  public String TypeApplication;
  public String DisplayActeur;

  public String Region="";

  public String nomSi;
  public String display_SIR;

  public String nomServiceMOA;
  public String nomDirectionMOA;
  public String nomMOA;
  public String prenomMOA;

  public String nomServiceMOE;
  public String nomDirectionMOE;
  public String nomMOE;
  public String prenomMOE;

  public String nomServicePROD;
  public String nomDirectionPROD;

  public String TypeSi ;
  public String nomMacrost ;
  public String nomMacrostTec ;
  public String nomPhaseNP;
  public String nomSousPhaseNP;
  public String nomFrequence;

  public String icone="images/Logos/iconeInconnu.gif";
  public String logo="images/Logos/iconeInconnu.gif";
  public String LogoBirt = "";
  public String displayClient;

  public Vector ListeVersions = new Vector(10);
  public Vector ListeRoadmap = new Vector(10);
  public Vector ListeInterfaces = new Vector(20);
  public Vector ListeProcessus = new Vector(20);
  public Vector ListeInterfacesSansActeurs = new Vector(20);
  public Vector ListeInterfacesAvecActeurs = new Vector(20);

  public Vector ListeLogiciels = new Vector(10);
  public static Vector ListeLogiciels_static = new Vector(10);

  public Vector ListeCompetences= new Vector(10);

  public Vector ListeForme = new Vector(30);
  public Vector ListeOM = new Vector(30);

  public static Vector ListeTypeSi_static = new Vector(10);
  public static Vector ListeSt = new Vector(10);
  public static Vector ListeSousTypeSt = new Vector(10);

  public static Vector RatioPf = new Vector(10);
  public static Vector ListeHebergement = new Vector(10);

  public Vector ListeDocs = new Vector(10);
  public Vector ListeServeurs = new Vector(10);
  public Vector ListeModules = new Vector(10);

  public Vector ListeRegions = new Vector(6);

  public Vector ListeTests = new Vector(6);

  String req = "";

  public final static int etat_notifie=1;
  public final static int etat_valide=3;
  public final static int etat_supprime=4;

  public String statusEnreg="";

  public int nbInterface=0;
  public int nbOm=0;

  public int nbInterfaceRouge=0;

  public int nbModules=0;
  public int nbProcessus=0;

  String TypeCreation;

  public int nb=0;
  public String nomVersionMw;
  public String serviceExpl;

  public int x=0;
  public int y=0;

  public int map_x=0;
  public int map_y=0;

  public int idMaturite=0;
  public int idTypeClient=0;

  public String nomMaturite;
  public String nomTypeClient;

  public int idDomaine=0;
  public String nomDomaine;

  public String MOEProd1;
  public String Backup;
  public String ControleAcces;

  public int Complexite=0;

  public String niveauCharge="";

  public int creerProjet=1;

  public final static String ST_Style = "Comic Sans MS";
  public final static int ST_Epaisseur = Font.PLAIN;
  public final static int ST_Taille = 10;
  public static int SEUIL_AFFICHAGE=70;
  
  public ST(String nomSt) {
    this.nomSt=nomSt;
    this.TypeCreation = "nomSt";
  }

  public ST(int id, int numStOrigine,String nomSt,int idVersionSt) {
    this.idSt = id;
    this.numStOrigine=numStOrigine;
    this.nomSt=nomSt;
    this.idVersionSt=idVersionSt;
    if (numStOrigine == 1) this.Origine = "X"; else this.Origine = "";
            if (nomSt == null)this.theStyle="display:none";;

    this.Style = ST.ST_Style;
    this.Epaisseur = ST.ST_Epaisseur;
    this.Taille = ST.ST_Taille;
  }

  public ST(int id,String nom,String couleur,String TypeApplication) {
    this.idSt=id;
    this.nomSt=nom;
    this.couleur=couleur;
    this.TypeApplication=TypeApplication;
    this.TypeCreation="nomSt";

    this.Style = ST.ST_Style;
    this.Epaisseur = ST.ST_Epaisseur;
    this.Taille = ST.ST_Taille;
  }

  public ST(int id,String nom,String old_nomSt,int idVersionSt,String typeForm,String action, int etatFicheVersionSt) {
    this.idSt=id;
    this.nomSt=nom;
    this.old_nomSt=old_nomSt;
    this.idVersionSt=idVersionSt;
    this.typeForm=typeForm;
    this.action=action;
    this.etatFicheVersionSt=etatFicheVersionSt;

    this.Style = ST.ST_Style;
    this.Epaisseur = ST.ST_Epaisseur;
    this.Taille = ST.ST_Taille;
  }

  public int getExport(String nomBase,Connexion myCnx, Statement st){
      ResultSet rs = null;
      int new_export = 0;

      req = "SELECT     valeur FROM         Config WHERE     (nom = 'NEW_EXPORT-ST')";
      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      String myDate="";
      try { rs.next();
              new_export= rs.getInt("valeur");

      } catch (SQLException s) {s.getMessage();}

      return new_export;
  }

  public String startExport(String nomBase,Connexion myCnx, Statement st){
      ResultSet rs = null;
      Date currentTime_1 = new Date();
      int theYear = currentTime_1.getYear()+ 1900;
      int theMonth = currentTime_1.getMonth()+1;
      int theDay = currentTime_1.getDate();
      int theHour = currentTime_1.getHours();
      int theMinute = currentTime_1.getMinutes();
      int theSecond = currentTime_1.getSeconds();
      String theDate =theYear+"-"+theMonth+"-"+theDay+"-"+theHour+"-"+theMinute+"-"+theSecond;

      String req = "update Config set valeur='"+theDate+"'";
      req+=" WHERE     (nom = 'EXPORT-ST') ";
      myCnx.ExecReq(st,nomBase,req);
      // 2010-03-10 00:00:00.000
      // CONVERT(DATETIME, '1960-03-16 00:00:00', 102)
      //String theDate = theDay+"/"+theMonth+"/"+ theYear+" "+theHour+":"+theMinute+":"+theSecond+".000";

      //req = "update declencheExportSt set nom='GO'";
      //myCnx.ExecReq(st,nomBase,req);




      return theDate;
  }
  public int getIdRoadmap(String nomBase,Connexion myCnx, Statement st, String version){
    ResultSet rs;
    int idRoadmap=0;
    version = version.replaceAll("'","''").replaceAll("\"","\"\"");
    req = "SELECT idRoadmap FROM  Roadmap WHERE     (idVersionSt = "+this.idVersionSt+") AND (version = '"+version+"')";
    req+=" AND (LF_Month = - 1) AND (LF_Year = - 1)";
    rs = myCnx.ExecReq(st, nomBase, req);
    try {rs.next(); idRoadmap =rs.getInt("idRoadmap");} catch (SQLException s) {s.getMessage();}
    return idRoadmap;
  }

  
  public static int searchByNom(String nomBase,Connexion myCnx, Statement st, String nomSt){
    ResultSet rs;
    int nb=0;
    String req = "SELECT     COUNT(*) AS nb FROM     St WHERE     (nomSt = '"+nomSt+"')";
    rs = myCnx.ExecReq(st, nomBase, req);
    try {rs.next(); nb =rs.getInt("nb");} catch (SQLException s) {s.getMessage();}
    return nb;
  }

  public static String searchByNomPartiel(String nomBase,Connexion myCnx, Statement st, String nomSt){
    ResultSet rs;
    String ListeSt="";
    String req = "SELECT     St.nomSt, VersionSt.idVersionSt";
    req+="    FROM         St INNER JOIN";
    req+="                   VersionSt ON St.idSt = VersionSt.stVersionSt";
    req+=" WHERE     (St.nomSt LIKE '%"+nomSt+"%')";

    rs = myCnx.ExecReq(st, nomBase, req);
    try { while (rs.next()) {
      ListeSt+=rs.getString("nomSt")+";"+ rs.getString("idVersionSt") + ",";
      }
    }
    catch (SQLException s) {s.getMessage();}

    return ListeSt;
  }

  public static String getCouleur (int note, String Taille){
    String couleur = "";
    if (note < 5) couleur = "Note_Rouge"+Taille;
    else if ((note >= 5) && (note < 7)) couleur = "Note_Orange"+Taille;
    else couleur = "Note_Verte"+Taille;

    return couleur;
  }

  public static int nbByState (String nomBase,Connexion myCnx, Statement st,String Etat){
    ResultSet rs=null;
    int nbST = 0;

    String req = "SELECT     COUNT(*) AS nbST FROM  VersionSt INNER JOIN  TypeEtat ON VersionSt.etatFicheVersionSt = TypeEtat.idTypeEtat WHERE     (TypeEtat.nom2TypeEtat = '"+Etat+"')";
    rs = myCnx.ExecReq(st, nomBase, req);
     try { rs.next();
            nbST = rs.getInt(1);
 } catch (SQLException s) {s.getMessage();}

    return nbST;
  }

  public static String getCouleurMaj (int note, String Taille){
  String couleur = "";
  if (note >= 24) couleur = "Note_Rouge"+Taille;
  else if ((note > 12) && (note < 24)) couleur = "Note_Orange"+Taille;
  else couleur = "Note_Verte"+Taille;

  return couleur;
}

public int getNbProjetsBySlidingYear(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs;
  
  Calendar calendar = Calendar.getInstance();
  int Annee = calendar.get(Calendar.YEAR) -1;
  int Month = calendar.get(Calendar.MONTH) +1;
  int Day = calendar.get(Calendar.DATE);
      
  
 String slidingDate = "CONVERT(DATETIME, '"+Day+"/"+Month+"/"+Annee+"' , 103)";

  req = "SELECT     COUNT(*) AS nb";
  req+="    FROM         ListeST INNER JOIN";
  req+="                       Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";
  req+="   WHERE     (Roadmap.dateMep > "+slidingDate+") ";
   
  req+="   AND (ListeST.nomSt = '"+this.nomSt+"') ";
  req+="   AND (Roadmap.LF_Year = - 1) AND (Roadmap.LF_Month = - 1)";
  req+="   and Roadmap.StandBy = 0";
           
  int nb = 0;

  rs = myCnx.ExecReq(st, nomBase, req);
  try { while (rs.next()) {
    nb=rs.getInt(1);
    }
  }
    catch (SQLException s) {s.getMessage();}

    return nb;
  }

public int getNbProjetsByYear(String nomBase,Connexion myCnx, Statement st,String Annee){
  ResultSet rs;
  req= "SELECT     COUNT(*) AS nb";
  req+="    FROM         ListeST INNER JOIN";
  req+="                     Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";
  req+=" WHERE     (YEAR(Roadmap.dateMep) >= "+Annee+") AND (ListeST.nomSt = '"+this.nomSt+"') AND (Roadmap.LF_Year = - 1) AND (Roadmap.LF_Month = - 1)";

  int nb = 0;

  rs = myCnx.ExecReq(st, nomBase, req);
  try { while (rs.next()) {
    nb=rs.getInt(1);
    }
  }
    catch (SQLException s) {s.getMessage();}

    return nb;
  }

 public void  getNode (String nomBase,Connexion myCnx, Statement st){
   ResultSet rs=null;
   int nb = 0;

   String req = "SELECT     TypeAppli.formeTypeAppli AS icone, TypeAppli.nomTypeAppli";
    req+= "  FROM         St INNER JOIN";
    req+= "                        TypeAppli ON St.typeAppliSt = TypeAppli.idTypeAppli INNER JOIN";
    req+= "                        VersionSt ON St.idSt = VersionSt.stVersionSt";
    req+= " WHERE     (VersionSt.idVersionSt = "+this.idVersionSt+")";

   this.NodeSt = new Node(this.nomSt);
   this.NodeSt.idNode = this.idVersionSt;
   rs = myCnx.ExecReq(st, nomBase, req);
    try { rs.next();
           
           
           this.NodeSt.icone = rs.getString("icone");
           this.NodeSt.forme = rs.getString("nomTypeAppli");
           this.NodeSt.couleur = "";
} catch (SQLException s) {s.getMessage();}


  } 
 
 public void  getNode2 (String nomBase,Connexion myCnx, Statement st){
   ResultSet rs=null;
   int nb = 0;

   String req = "SELECT DISTINCT VersionSt.idVersionSt, TypeAppli.nomTypeAppli, AppliIcone.icone, TypeSi.couleurSI";
   req+= " FROM         VersionSt INNER JOIN" ;
    req+= "  St ON VersionSt.stVersionSt = St.idSt INNER JOIN" ;
    req+= "  TypeAppli ON St.typeAppliSt = TypeAppli.idTypeAppli INNER JOIN" ;
    req+= "  TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi INNER JOIN" ;
    req+= "  AppliIcone ON TypeAppli.nomTypeAppli = AppliIcone.nomTypeAppli AND TypeSi.couleurSI = AppliIcone.couleur";
    req+= " WHERE     (VersionSt.idVersionSt = "+this.idVersionSt+")";

   this.NodeSt = new Node(this.nomSt);
   this.NodeSt.idNode = this.idVersionSt;
   rs = myCnx.ExecReq(st, nomBase, req);
    try { rs.next();
           
           this.NodeSt.forme = rs.getString("nomTypeAppli");
           this.NodeSt.icone = rs.getString("icone");
           this.NodeSt.couleur = rs.getString("couleur");
} catch (SQLException s) {s.getMessage();}


  } 

  public int getNoteMaj(){

    int Anciennete = 0;
    int nbMonth = -1;
    if (this.derMajVersionSt == null)
    {
      nbMonth = this.creationVersionSt.getYear() * 12 +
          this.creationVersionSt.getMonth();
    }
    else
    {
      nbMonth = this.derMajVersionSt.getYear() * 12 +
          this.derMajVersionSt.getMonth();
    }

    Date currentDate = new Date();
    int nbCurrentMonth =  currentDate.getYear() * 12 + currentDate.getMonth();

    Anciennete = nbCurrentMonth - nbMonth;
    this.CouleurAvancement = getCouleurMaj (Anciennete, "_petit");

    return (Anciennete);
  }

  public void getNoteAvancement(){

    if ((this.descVersionSt.length() > 0) && (this.descVersionSt.length() <= 50)) noteDescription = 1;
    else if ((this.descVersionSt.length() > 50) && (this.descVersionSt.length() <= 150)) noteDescription = 2;
    else if ((this.descVersionSt.length() > 150) ) noteDescription = 3;
    else noteDescription = 0;

    if ((this.nbLigCodeVersionSt + this.nbPtsFctVersionSt) > 0) noteMetrologie = 1;
    else noteMetrologie = 0;

    if (this.nbModules ==0) noteModule=0;
    else if ((this.nbModules >0) && (this.nbModules <4)) noteModule=1;
    else noteModule=2;

    if (this.nbInterface >0) noteInterface = 1; else noteInterface = 0;
    this.noteAvancement = noteDescription+noteMetrologie+noteModule+noteInterface;

    this.CouleurAvancement = getCouleur (this.noteAvancement, "_petit");

    noteGlobale+=this.noteAvancement;
  }

  public void getNbModules(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    rs = myCnx.ExecReq(st, nomBase, "exec ST_SelectNbModules  "+this.idVersionSt);
    try { while (rs.next()) {
      this.nbModules=rs.getInt(1);
      }
    }
      catch (SQLException s) {s.getMessage();}
    }

    public void getNbProcessus(String nomBase,Connexion myCnx, Statement st){
      ResultSet rs;
      if ((this.TypeApplication != null) && (this.TypeApplication.equals("Acteur")))
               {
                    req = "EXEC ACTEUR_SelectProcByActeur "+this.idSt+"";
               }
               else
               {
                     req = "EXEC ST_SelectProcBySt '"+this.idSt+"', 'Processus'";
          }
      rs = myCnx.ExecReq(st, nomBase, req);
      try { while (rs.next()) {
          int id = rs.getInt(1);
        this.nbProcessus++;
        }
      }
        catch (SQLException s) {s.getMessage();}
    }

  public void getNbInterface(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    rs = myCnx.ExecReq(st, nomBase, "exec ST_selectNbInterfaceSansActeur  "+this.idVersionSt+","+this.idSt);
    try { while (rs.next()) {
      this.nbInterface=rs.getInt(1);
      }
    }
      catch (SQLException s) {s.getMessage();}
    }

    public void getNbOm(String nomBase,Connexion myCnx, Statement st){
      ResultSet rs;
      req="exec ST_SelectOmByStOrigine  "+this.idSt;
      rs = myCnx.ExecReq(st, nomBase, req);
      try { while (rs.next()) {
          int id = rs.getInt(1);
        this.nbOm++;
        }
      }
        catch (SQLException s) {s.getMessage();}
    }

    public void getNbInterfaceRouge(String nomBase,Connexion myCnx, Statement st){
      ResultSet rs;
      rs = myCnx.ExecReq(st, nomBase, "exec ST_selectNbInterfaceRouge  "+this.idSt);
      try { while (rs.next()) {
        this.nbInterfaceRouge=rs.getInt(1);
        }
      }
        catch (SQLException s) {s.getMessage();}
    }
    
 public int get_IdMacroStFictif(String nomBase,Connexion myCnx, Statement st ){
   int IdMacroStFictif=0;
   ResultSet rs;
   String req = "SELECT     MacroSt.idMacrost FROM   SI INNER JOIN   MacroSt ON SI.idSI = MacroSt.siMacrost WHERE     (MacroSt.nomMacrost = 'projet') AND (SI.nomSI = 'fictif')";
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
   while(rs.next())
   {
      IdMacroStFictif = rs.getInt("idMacrost");
    }
   }
  catch (SQLException s) { }

  return IdMacroStFictif;
 }
 
  public int get_IdTypeAppliFictif(String nomBase,Connexion myCnx, Statement st ){
   int idTypeAppliFictif=0;
   ResultSet rs;
   String req = "SELECT  idTypeAppli  FROM TypeAppli where nomTypeAppli='Fictif'";
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
   while(rs.next())
   {
      idTypeAppliFictif = rs.getInt("idTypeAppli");
    }
   }
  catch (SQLException s) { }

  return idTypeAppliFictif;
 }
 
  public int get_IdSiFictif(String nomBase,Connexion myCnx, Statement st ){
   int IdMacroStFictif=0;
   ResultSet rs;
   String req = "SELECT     idSI FROM    SI WHERE     (nomSI = 'fictif')";
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
   while(rs.next())
   {
      IdMacroStFictif = rs.getInt("idSI");
    }
   }
  catch (SQLException s) { }

  return IdMacroStFictif;
 }
  
  public int get_IdTypeSiFictif(String nomBase,Connexion myCnx, Statement st ){
   int idTypeSi=0;
   ResultSet rs;
   String req = "SELECT     TOP (1) TypeSi.idTypeSi FROM         TypeSi INNER JOIN   SI ON TypeSi.siTypesi = SI.idSI WHERE     (SI.nomSI = 'fictif') AND (TypeSi.nomTypeSi = 'fictif')";
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
   while(rs.next())
   {
      idTypeSi = rs.getInt("idTypeSi");
    }
   }
  catch (SQLException s) { }

  return idTypeSi;
 }  
 
  public void getState(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
   
    req= "SELECT   DISTINCT automate.etatNext, TypeEtat_1.idTypeEtat as idEtatNext";
    req+= " FROM         automate INNER JOIN";
    req+= "                       TypeEtat ON automate.etat = TypeEtat.nom2TypeEtat INNER JOIN";
    req+= "                       TypeEtat AS TypeEtat_1 ON automate.etatNext = TypeEtat_1.nom2TypeEtat";
    req+= " WHERE     (TypeEtat.idTypeEtat = "+this.etatFicheVersionSt+") AND (automate.action = '"+this.action+"')";
    
    rs = myCnx.ExecReq(st, nomBase, req);
    try {rs.next(); 
        this.nomEtat =rs.getString("etatNext"); 
        this.etatFicheVersionSt =rs.getInt("idEtatNext");
    } catch (SQLException s) {s.getMessage();}
  }
  
  public void getState2(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    req = "SELECT     automate.etatNext, automate.[desc] FROM    automate INNER JOIN   TypeEtat ON automate.etatNext = TypeEtat.nom2TypeEtat WHERE     (TypeEtat.idTypeEtat = "+this.etatFicheVersionSt+") AND (automate.action = '"+this.action+"')";
    rs = myCnx.ExecReq(st, nomBase, req);
    try {rs.next(); this.nomEtat =rs.getString("etatNext"); this.statusEnreg =rs.getString("desc");} catch (SQLException s) {s.getMessage();}
  }  


  public void draw( int x_boite, int y_boite, int l_boite){

    //if (Activite.L < ST.SEUIL_AFFICHAGE) return;
    int  max= Math.min(38 * Activite.L /400, this.nomSt.length());
    //this.Largeur = Utils.getStringWidth(this.nomSt,ST.ST_Style, ST.ST_Taille, ST.ST_Epaisseur);
    this.Largeur = 30;
    this.Hauteur = 30;

    this.x=x_boite +l_boite -37;
    this.y=y_boite+Activite.H;

    this.x=x+8;
    this.y=y -this.Hauteur;

    this.x_vrai =  x - Utils.getStringWidth(this.nomSt,ST.ST_Style, ST.ST_Taille, ST.ST_Epaisseur) -10;
    this.y_vrai =  y + this.Hauteur - Utils.getStringHeight(this.nomSt,ST.ST_Style, ST.ST_Taille, ST.ST_Epaisseur) -10;

  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("idSt="+this.idSt);
    System.out.println("nomSt="+this.nomSt);
    System.out.println("Origine="+this.Origine);
    System.out.println("theStyle="+this.theStyle);
    System.out.println("numStOrigine="+this.numStOrigine);
    System.out.println("trigrammeSt="+this.trigrammeSt);;
    System.out.println("chronoSt="+this.chronoSt);
    System.out.println("typeAppliSt="+this.typeAppliSt);
    System.out.println("Criticite="+this.Criticite);
    System.out.println("ClefImport="+this.ClefImport);;
    System.out.println("diagramme="+this.diagramme);;
    System.out.println("isST="+this.isST);
    System.out.println("isAppli="+this.isAppli);
    System.out.println("isActeur="+this.isActeur);
    System.out.println("isComposant="+this.isComposant);
    System.out.println("isEquipement="+this.isEquipement);
    System.out.println("isRecurrent="+this.isRecurrent);
    System.out.println("isMeeting="+this.isMeeting);
    System.out.println("idVersionSt="+this.idVersionSt);
    System.out.println("stVersionSt="+this.stVersionSt);
    System.out.println("numVersionSt="+this.numVersionSt);
    System.out.println("etatFicheVersionSt="+this.etatFicheVersionSt);
    System.out.println("siVersionSt="+this.siVersionSt);
    System.out.println("typeSiVersionSt="+this.typeSiVersionSt);
    System.out.println("versionRefVersionSt="+this.versionRefVersionSt);;
    System.out.println("creationVersionSt="+this.creationVersionSt);
    System.out.println("derMajVersionSt="+this.derMajVersionSt);
    System.out.println("descVersionSt="+this.descVersionSt);;
    System.out.println("deltaVersionSt="+this.deltaVersionSt);;
    System.out.println("macrostVersionSt="+this.macrostVersionSt);
    System.out.println("phaseNPVersionSt="+this.phaseNPVersionSt);
    System.out.println("sousPhaseNPVersionSt="+this.sousPhaseNPVersionSt);
    System.out.println("typeArchiVersionSt="+this.typeArchiVersionSt);
    System.out.println("osVersionSt="+this.osVersionSt);
    System.out.println("bdVersionSt="+this.bdVersionSt);
    System.out.println("hebergVersionSt="+this.hebergVersionSt);
    System.out.println("gestionUtilVersionSt="+this.gestionUtilVersionSt);
    System.out.println("nbLigCodeVersionSt="+this.nbLigCodeVersionSt);
    System.out.println("nbPtsFctVersionSt="+this.nbPtsFctVersionSt);
    System.out.println("iReagirVersionSt="+this.iReagirVersionSt);
    System.out.println("serviceMoeVersionSt="+this.serviceMoeVersionSt);
    System.out.println("respMoeVersionSt="+this.respMoeVersionSt);
    System.out.println("serviceMoaVersionSt="+this.serviceMoaVersionSt);
    System.out.println("serviceExploitVersionSt="+this.serviceExploitVersionSt);
    System.out.println("mepVersionSt="+this.mepVersionSt);
    System.out.println("etatVersionSt="+this.etatVersionSt);
    System.out.println("coutFabrVersionSt="+this.coutFabrVersionSt);
    System.out.println("crenUtilVersionSt="+this.crenUtilVersionSt);
    System.out.println("externalVersionSt="+this.externalVersionSt);
    System.out.println("lienManuelVersionSt="+this.lienManuelVersionSt);;
    System.out.println("derVersionSt="+this.derVersionSt);
    System.out.println("auteurVersionSt="+this.auteurVersionSt);
    System.out.println("precVersionSt="+this.precVersionSt);
    System.out.println("suivVersionSt="+this.suivVersionSt);
    System.out.println("nbUtilVersionSt="+this.nbUtilVersionSt);
    System.out.println("respMoaVersionSt="+this.respMoaVersionSt);
    System.out.println("killVersionSt="+this.killVersionSt);
    System.out.println("okMoaVersionSt="+this.okMoaVersionSt);
    System.out.println("okMoeVersionSt="+this.okMoeVersionSt);
    System.out.println("STRokMoaVersionSt="+this.STRokMoaVersionSt);;
    System.out.println("mep1VersionSt="+this.mep1VersionSt);
    System.out.println("createurVersionSt="+this.createurVersionSt);;
    System.out.println("modificateurVersionSt="+this.modificateurVersionSt);;
    System.out.println("roadmap="+this.roadmap);;
    System.out.println("Mobilite="+this.Mobilite);;
   System.out.println("Externalisation="+this.Externalisation);;
    System.out.println("dateEB="+this.dateEB);
    System.out.println("ContactOper="+this.ContactOper);;
    System.out.println("ModeAcq="+this.ModeAcq);;
    System.out.println("TypeApplication="+this.TypeApplication);;
    System.out.println("nomSi="+this.nomSi);;
    System.out.println("nomMOA="+this.nomMOA);;
    System.out.println("prenomMOA="+this.prenomMOA);;
    System.out.println("nomMOE="+this.nomMOE);;
    System.out.println("prenomMOE="+this.prenomMOE);;
    System.out.println("TypeSi="+this.TypeSi);;
    System.out.println("nomMacrost="+this.nomMacrost);;
    System.out.println("nomPhaseNP="+this.nomPhaseNP);
    System.out.println("nomSousPhaseNP="+this.nomSousPhaseNP);
    System.out.println("nomFrequence="+this.nomFrequence);
    System.out.println("icone="+this.icone);
    System.out.println("nomServiceMOE="+this.nomServiceMOE);
    System.out.println("nomDirectionMOE="+this.nomDirectionMOE);
    System.out.println("nomServiceMOA="+this.nomServiceMOA);
    System.out.println("nomDirectionMOA="+this.nomDirectionMOA);
    System.out.println("nomServicePROD="+this.nomServicePROD);
    System.out.println("nomDirectionPROD="+this.nomDirectionPROD);
    System.out.println("nomEtat="+this.nomEtat);
    System.out.println("nbModules="+this.nbModules);
    System.out.println("nbInterface="+this.nbInterface);
    System.out.println("serviceExpl="+this.serviceExpl);
    System.out.println("idMaturite="+this.idMaturite);
    System.out.println("nomMaturite="+this.nomMaturite);
    System.out.println("idDomaine="+this.idDomaine);
    System.out.println("nomDomaine="+this.nomDomaine);
    System.out.println("MOEProd1="+this.MOEProd1);
    System.out.println("Backup="+this.Backup);
    System.out.println("ControleAcces="+this.ControleAcces);
    System.out.println("Complexite="+this.Complexite);
    System.out.println("logo="+this.logo);
    System.out.println("idTypeClient="+this.idTypeClient);
    System.out.println("x="+this.x);
    System.out.println("y="+this.y);
    System.out.println("map_x="+this.map_x);
    System.out.println("map_y="+this.map_y);
    System.out.println("serviceMetier="+this.macrostTecVersionSt);
    System.out.println("nomServiceMetier="+this.nomServiceMetier);
    System.out.println("creerProjet="+this.creerProjet);

    System.out.println("==================================================");
  }

  public ST(int id,String Type) {
    this.TypeCreation=Type;
    if (Type.equals("idSt"))
      this.idSt=id;

    else
      this.idVersionSt=id;


}


  public ErrorSpecific bd_Enreg(String nomBase,Connexion myCnx, Statement st, String project, String transaction){

      ErrorSpecific myError = new ErrorSpecific();
    if ((this.logo != null) && (this.logo.length() >3))
    {
      int pos = -1;
      pos = logo.indexOf("../doc");
      if (pos > -1)
      {

      }
      this.LogoBirt = this.logo.replaceAll("../doc", ".."+project+"/doc");
    }
    else
    {
      this.LogoBirt = ".."+project+"/doc/Inconnue.png";
    }

    if (this.typeForm.equals("Creation")) { // Cr�ation d'un nouveau ST
      myError=this.bd_Insert( nomBase, myCnx,  st,  transaction);
      if (myError.cause == -1) return myError;
    }
    else { //MAJ du ST
      if (action.equals("SupprimerDefinitivement"))
          { //l'utilisateur a cliqu� sur le bouton supprimer
           myError=this.supprimerDefinitivementVersion(idVersionSt,nomBase,myCnx,st,transaction);
           if (myError.cause == -1) return myError;

          }
          else if (!this.action.equals("supp"))
          {
            myError=this.bd_Update( nomBase, myCnx,  st,  transaction);
            if (myError.cause == -1) return myError;
          }
}
    return myError;
  }

  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
    
    if (this.versionRefVersionSt == null) this.versionRefVersionSt = "";

    this.descVersionSt=Utils.removeBR(this.descVersionSt);

           req = "INSERT St (";
             req+="nomSt"+",";
             req+="typeAppliSt"+",";
             req+="Criticite"+",";
             req+="isST"+",";
             req+="isAppli"+",";
             req+="isEquipement"+",";
             req+="isComposant"+",";
             req+="isActeur"+",";
             req+="isRecurrent"+",";
             req+="isMeeting"+",";
             req+="logo"+",";
             req+="LogoBirt"+",";
             req+="creerProjet"+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+this.nomSt+"',";
             req+="'"+this.typeAppliSt+"',";
             req+="'"+this.Criticite+"',";
             req+="'"+this.isST+"',";
             req+="'"+this.isAppli+"',";
             req+="'"+this.isEquipement+"',";
             req+="'"+this.isComposant+"',";
             req+="'"+this.isActeur+"',";
             req+="'"+this.isRecurrent+"',";
             req+="'"+this.isMeeting+"',";
             req+="'"+this.logo+"',";
             req+="'"+this.LogoBirt+"',";
             req+="'"+this.creerProjet+"'";
           req+=")";

           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),transaction,""+this.idSt);
           if (myError.cause == -1) return myError;

          req = "SELECT  idSt FROM   St WHERE nomSt = '"+this.nomSt+"'";
          rs = myCnx.ExecReq(st, nomBase, req);
          try {rs.next(); this.idSt =rs.getInt("idSt"); } catch (SQLException s) {s.getMessage();}


          req = "INSERT VersionSt (";
            req+="stVersionSt"+",";
            req+="numVersionSt"+",";
            req+="etatFicheVersionSt"+",";
            req+="siVersionSt"+",";
            req+="typeSiVersionSt"+",";
            req+="versionRefVersionSt"+",";
            req+="creationVersionSt"+",";
            //req+="derMajVersionSt"+",";
            req+="descVersionSt"+",";
            req+="deltaVersionSt"+",";
            req+="macrostVersionSt"+",";
            req+="phaseNPVersionSt"+",";
            req+="sousPhaseNPVersionSt"+",";
            req+="typeArchiVersionSt"+",";
            req+="osVersionSt"+",";
            req+="bdVersionSt"+",";
            req+="hebergVersionSt"+",";
            req+="gestionUtilVersionSt"+",";
            req+="nbLigCodeVersionSt"+",";
            req+="nbPtsFctVersionSt"+",";
            req+="iReagirVersionSt"+",";
            req+="serviceMoeVersionSt"+",";
            req+="respMoeVersionSt"+",";
            req+="serviceMoaVersionSt"+",";
            req+="serviceExploitVersionSt"+",";
            req+="mepVersionSt"+",";
            req+="etatVersionSt"+",";
            req+="coutFabrVersionSt"+",";
            req+="crenUtilVersionSt"+",";
            req+="externalVersionSt"+",";
            req+="lienManuelVersionSt"+",";
            req+="derVersionSt"+",";
            req+="auteurVersionSt"+",";
            req+="precVersionSt"+",";
            req+="suivVersionSt"+",";
            req+="nbUtilVersionSt"+",";
            req+="respMoaVersionSt"+",";
            req+="killVersionSt"+",";
            req+="okMoaVersionSt"+",";
            req+="okMoeVersionSt"+",";
            req+="STRokMoaVersionSt"+",";
            req+="mep1VersionSt"+",";
            req+="createurVersionSt"+",";
            //req+="modificateurVersionSt"+",";
            req+="roadmap"+",";
            req+="Mobilite"+",";
            req+="Externalisation"+",";
            req+="dateEB"+",";
            req+="ContactOper"+",";
            req+="ModeAcq"+",";
            req+="ContactMOE"+",";
            req+="idDomaine"+",";
            req+="idMaturite"+",";
            req+="MOEProd1"+",";
            req+="[Backup]"+",";
            req+="ControleAcces"+",";
            req+="Complexite"+",";
            req+="idTypeClient"+",";
            req+="serviceMetier"+"";
          req+=")";
          req+=" VALUES ";
          req+="(";
            req+="'"+this.idSt+"',";
            req+="'"+this.numVersionSt+"',";
            req+="'"+this.etatFicheVersionSt+"',";
            req+="'"+this.siVersionSt+"',";
            req+="'"+this.typeSiVersionSt+"',";

            req += "'" + this.versionRefVersionSt + "',";
            req+=""+"GETDATE()"+",";
            //req+=""+this.str_derMajVersionSt+",";
            req+="'"+this.descVersionSt.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
            req+="'"+this.deltaVersionSt+"',";

            if (this.macrostVersionSt == -1)
              req+="null,";
            else
              req+="'"+this.macrostVersionSt+"',";

            if (this.phaseNPVersionSt == -1)
              req+="null,";
            else
              req+="'"+this.phaseNPVersionSt+"',";

            if (this.sousPhaseNPVersionSt == -1)
              req+="null,";
            else
              req+="'"+this.sousPhaseNPVersionSt+"',";

            req+="'"+this.typeArchiVersionSt+"',";
            req+="'"+this.osVersionSt+"',";
            req+="'"+this.bdVersionSt+"',";
            req+="'"+this.hebergVersionSt+"',";
            req+="'"+this.gestionUtilVersionSt+"',";
            req+="'"+this.nbLigCodeVersionSt+"',";
            req+="'"+this.nbPtsFctVersionSt+"',";
            req+="'"+this.iReagirVersionSt+"',";

            if (this.serviceMoeVersionSt == -1)
              req+="null,";
            else
              req+="'"+this.serviceMoeVersionSt+"',";

            if (this.respMoeVersionSt == -1)
              req+="null,";
            else
              req+="'"+this.respMoeVersionSt+"',";


            if (this.serviceMoaVersionSt == -1)
              req+="null,";
            else
              req+="'"+this.serviceMoaVersionSt+"',";

            req+="'"+this.serviceExploitVersionSt+"',";
            req+=""+this.dateMep+",";

            if (this.etatVersionSt == -1)
              req+="3,";
            else
              req+="'"+this.etatVersionSt+"',";

            req+="'"+this.coutFabrVersionSt+"',";
            req+="'"+this.crenUtilVersionSt+"',";
            req+="'"+this.externalVersionSt+"',";
            req+="'"+this.lienManuelVersionSt.replaceAll("\\\\","\\\\\\\\")+"',";
            req+="'"+this.derVersionSt+"',";
            req+="'"+this.auteurVersionSt+"',";
            req+="'"+this.precVersionSt+"',";
            req+="'"+this.suivVersionSt+"',";
            req+="'"+this.nbUtilVersionSt+"',";
            req+="'"+this.respMoaVersionSt+"',";
            req+=""+this.dateKill+",";

            req+="'"+this.okMoaVersionSt+"',";
            req+="'"+this.okMoeVersionSt+"',";
            req+="'"+this.STRokMoaVersionSt+"',";
            req+=""+this.dateMep1+",";
            req+="'"+this.createurVersionSt+"',";
            //req+="'"+this.modificateurVersionSt+"',";
            req+="'"+this.roadmap+"',";
            req+="'"+this.Mobilite+"',";
            req+="'"+this.Externalisation+"',";
            req+=""+this.str_dateEB+",";
            req+="'"+this.ContactOper+"',";
            req+="'"+this.ModeAcq+"',";
            req+="'"+this.ContactMOE+"',";
            if (this.idDomaine == -1)
              req+="null,";
            else
              req+="'"+this.idDomaine+"',";
            if (this.idMaturite == -1)
              req+="null,";
            else
              req+="'"+this.idMaturite+"',";

            req+="'"+this.MOEProd1.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
            req+="'"+this.Backup.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
            req+="'"+this.ControleAcces.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";

            req+="'"+this.Complexite+"',";
            req+="'"+this.idTypeClient+"',";
            req+=""+this.macrostTecVersionSt;

            req+=")";

           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),transaction,""+this.idVersionSt);
           if (myError.cause == -1) return myError;

          req = "SELECT  idVersionSt FROM   VersionSt WHERE stVersionSt = '"+this.idSt+"'";
         rs = myCnx.ExecReq(st, nomBase, req);
         try {rs.next(); this.idVersionSt =rs.getInt("idVersionSt");} catch (SQLException s) {s.getMessage();}

          return myError;
  }


  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();

    this.descVersionSt=Utils.removeBR(this.descVersionSt);
    if (this.versionRefVersionSt == null) this.versionRefVersionSt = "";


    req = "UPDATE St SET ";

    req+="nomSt='"+this.nomSt+"',";
    req+="trigrammeSt='"+this.trigrammeSt+"',";
    req+="chronoSt='"+this.chronoSt+"',";
    req+="typeAppliSt='"+this.typeAppliSt+"',";
    req+="Criticite='"+this.Criticite+"',";
    req+="ClefImport='"+this.ClefImport+"',";
    req+="diagramme='"+this.diagramme+"',";
    req+="isST='"+this.isST+"',";
    req+="isAppli='"+this.isAppli+"',";
    req+="isEquipement='"+this.isEquipement+"',";
    req+="isComposant='"+this.isComposant+"',";
    req+="isActeur='"+this.isActeur+"',";
    req+="isRecurrent='"+this.isRecurrent+"',";
    req+="isMeeting='"+this.isMeeting+"',";
    req+="logo='"+this.logo+"',";
    req+="LogoBirt='"+this.LogoBirt+"',";
    req+="creerProjet='"+this.creerProjet+"'";

    req+=" WHERE idSt="+this.idSt;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),transaction,""+this.idSt);
    if (myError.cause == -1) return myError;

    // -----------------------------------------------------------------

    req = "UPDATE VersionSt SET ";

    req+="stVersionSt='"+this.stVersionSt+"',";
    req+="numVersionSt='"+this.numVersionSt+"',";
    req+="etatFicheVersionSt='"+this.etatFicheVersionSt+"',";
    req+="siVersionSt='"+this.siVersionSt+"',";
    req+="typeSiVersionSt='"+this.typeSiVersionSt+"',";

    req+="versionRefVersionSt='"+this.versionRefVersionSt+"',";
    req+="derMajVersionSt="+"GETDATE()"+",";
    req+="descVersionSt='"+this.descVersionSt.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";

    req+="deltaVersionSt='"+this.deltaVersionSt+"',";
    if (this.macrostVersionSt == -1)
      req+="macrostVersionSt=null,";
    else
      req+="macrostVersionSt='"+this.macrostVersionSt+"',";

    //Connexion.trace(":::::::::::::::::::::::::","this.phaseNPVersionSt",""+this.phaseNPVersionSt);


    if (this.phaseNPVersionSt == -1)
      req+="phaseNPVersionSt=null,";
    else
      req+="phaseNPVersionSt='"+this.phaseNPVersionSt+"',";

    if (this.sousPhaseNPVersionSt == -1)
      req+="sousPhaseNPVersionSt=null,";
    else
      req+="sousPhaseNPVersionSt='"+this.sousPhaseNPVersionSt+"',";



    req+="typeArchiVersionSt='"+this.typeArchiVersionSt+"',";
    req+="osVersionSt='"+this.osVersionSt+"',";
    req+="bdVersionSt='"+this.bdVersionSt+"',";
    req+="hebergVersionSt='"+this.hebergVersionSt+"',";
    req+="gestionUtilVersionSt='"+this.gestionUtilVersionSt+"',";
    req+="nbLigCodeVersionSt='"+this.nbLigCodeVersionSt+"',";
    req+="nbPtsFctVersionSt='"+this.nbPtsFctVersionSt+"',";
    req+="iReagirVersionSt='"+this.iReagirVersionSt+"',";
    if (this.serviceMoeVersionSt == -1)
      req+="serviceMoeVersionSt=null,";
    else
      req+="serviceMoeVersionSt='"+this.serviceMoeVersionSt+"',";

    if (this.respMoeVersionSt == -1)
      req+="respMoeVersionSt=null,";
    else
      req+="respMoeVersionSt='"+this.respMoeVersionSt+"',";


    if (this.serviceMoaVersionSt == -1)
      req+="serviceMoaVersionSt=null,";
    else
      req+="serviceMoaVersionSt='"+this.serviceMoaVersionSt+"',";

    if (this.serviceExploitVersionSt == -1)
      req+="serviceExploitVersionSt=null,";
    else
      req+="serviceExploitVersionSt='"+this.serviceExploitVersionSt+"',";

    req+="mepVersionSt="+this.dateMep+",";
    req+="etatVersionSt='"+this.etatVersionSt+"',";
    req+="coutFabrVersionSt='"+this.coutFabrVersionSt+"',";
    req+="crenUtilVersionSt='"+this.crenUtilVersionSt+"',";
    req+="externalVersionSt='"+this.externalVersionSt+"',";
    req+="lienManuelVersionSt='"+this.lienManuelVersionSt.replaceAll("\\\\","\\\\\\\\")+"',";
    req+="derVersionSt='"+this.derVersionSt+"',";
    req+="auteurVersionSt='"+this.auteurVersionSt+"',";
    req+="precVersionSt='"+this.precVersionSt+"',";
    req+="suivVersionSt='"+this.suivVersionSt+"',";
    req+="nbUtilVersionSt='"+this.nbUtilVersionSt+"',";
    req+="respMoaVersionSt='"+this.respMoaVersionSt+"',";
    req+="killVersionSt="+this.dateKill+",";

    req+="okMoaVersionSt='"+this.okMoaVersionSt+"',";
    req+="okMoeVersionSt='"+this.okMoeVersionSt+"',";
    req+="STRokMoaVersionSt='"+this.STRokMoaVersionSt+"',";
    req+="mep1VersionSt="+this.dateMep1+",";
    //req+="createurVersionSt='"+this.createurVersionSt+"',";
    req+="modificateurVersionSt='"+this.modificateurVersionSt+"',";
    req+="roadmap='"+this.roadmap+"',";
    req+="Mobilite='"+this.Mobilite+"',";
    req+="Externalisation='"+this.Externalisation+"',";
    req+="dateEB="+this.str_dateEB+",";
    req+="ContactOper='"+this.ContactOper+"',";
    req+="ModeAcq='"+this.ModeAcq.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
    req+="ContactMOE='"+this.ContactMOE+"',";
    if (this.idDomaine == -1)
      req+="idDomaine=null,";
    else
      req+="idDomaine='"+this.idDomaine+"',";

    if (this.idMaturite == -1)
      req+="idMaturite=null,";
    else
      req+="idMaturite='"+this.idMaturite+"',";

    req+="MOEProd1='"+this.MOEProd1.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
    req+="[Backup]='"+this.Backup.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
    req+="ControleAcces='"+this.ControleAcces.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";

    req+="Complexite="+this.Complexite+",";;
    req+="idTypeClient="+this.idTypeClient+",";;
    req+="serviceMetier="+this.macrostTecVersionSt;

    req+=" WHERE idVersionSt="+this.idVersionSt;


    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.idSt);
    if (myError.cause == -1) return myError;


    req = "UPDATE Equipe SET ";

    req+="nom ='"+this.nomSt+"'";
    req+=" WHERE nom='"+this.old_nomSt+"'";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.idSt);
    if (myError.cause == -1) return myError;

    return myError;
  }

  public ErrorSpecific bd_UpdateConfig(String nomBase,Connexion myCnx, Statement st, String transaction, String value){
    ErrorSpecific myError = new ErrorSpecific();
    req = "SELECT     valeur FROM         Config WHERE     (nom = 'NEW_EXPORT-ST')";

    req = "UPDATE Config SET ";
    req+="valeur ='"+value+"'";
    req+=" WHERE     (nom = 'NEW_EXPORT-ST')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateConfig",""+this.idSt);
    if (myError.cause == -1) return myError;

    return myError;
  }

  public void bd_selectByNom(String nomBase, Connexion myCnx,  Statement st ){
    //Connexion.trace(":::::::::::::::::::::::::","this.TypeCreation",this.TypeCreation);
    ResultSet rs=null;
    req ="SELECT     idSt, idVersionSt FROM   ListeST";
    req += " WHERE nomSt='"+this.nomSt+"'";
    
    rs = Connexion.ExecReq(st, nomBase, req);

    //if (rs == null) return;
    try {
      while (rs.next()) {
        this.idSt= rs.getInt("idSt");
        this.idVersionSt= rs.getInt("idVersionSt");
      }
      }
    catch (SQLException s) {s.getMessage();}
  }

  public String getListeDiffusion(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
  String ListeDiffusion = "";

  req = "SELECT mail FROM  Membre WHERE  (isAdmin = 1)";
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
  
  public void getAllFromBd(String nomBase, Connexion myCnx,  Statement st ){
    //Connexion.trace(":::::::::::::::::::::::::","this.TypeCreation",this.TypeCreation);
    ResultSet rs=null;
    req = "SELECT ";
    req+="idSt"+",";
    req+="nomSt"+",";
    req+="trigrammeSt"+",";
    req+="chronoSt"+",";
    req+="typeAppliSt"+",";
    req+="Criticite"+",";
    req+="ClefImport"+",";
    req+="diagramme"+",";
    req+="isST"+",";
    req+="isAppli"+",";
    req+="idVersionSt"+",";
    req+="stVersionSt"+",";
    req+="numVersionSt"+",";
    req+="etatFicheVersionSt"+",";
    req+="siVersionSt"+",";
    req+="typeSiVersionSt"+",";
    req+="versionRefVersionSt"+",";
    req+="creationVersionSt"+",";
    req+="derMajVersionSt"+",";
    req+="descVersionSt"+",";
    req+="deltaVersionSt"+",";
    req+="macrostVersionSt"+",";
    req+="phaseNPVersionSt"+",";
    req+="sousPhaseNPVersionSt"+",";
    req+="typeArchiVersionSt"+",";
    req+="osVersionSt"+",";
    req+="bdVersionSt"+",";
    req+="hebergVersionSt"+",";
    req+="gestionUtilVersionSt"+",";
    req+="nbLigCodeVersionSt"+",";
    req+="nbPtsFctVersionSt"+",";
    req+="iReagirVersionSt"+",";
    req+="serviceMoeVersionSt"+",";
    req+="respMoeVersionSt"+",";
    req+="serviceMoaVersionSt"+",";
    req+="serviceExploitVersionSt"+",";
    req+="mepVersionSt"+",";
    req+="etatVersionSt"+",";
    req+="coutFabrVersionSt"+",";
    req+="crenUtilVersionSt"+",";
    req+="externalVersionSt"+",";
    req+="lienManuelVersionSt"+",";
    req+="derVersionSt"+",";
    req+="auteurVersionSt"+",";
    req+="precVersionSt"+",";
    req+="suivVersionSt"+",";
    req+="nbUtilVersionSt"+",";
    req+="respMoaVersionSt"+",";
    req+="killVersionSt"+",";
    req+="okMoaVersionSt"+",";
    req+="okMoeVersionSt"+",";
    req+="STRokMoaVersionSt"+",";
    req+="mep1VersionSt"+",";
    req+="createurVersionSt"+",";
    req+="modificateurVersionSt"+",";
    req+="roadmap"+",";
    req+="Mobilite"+",";
    req+="Externalisation"+",";
    req+="dateEB"+",";
    req+="ContactOper"+",";
    req+="ModeAcq"+",";
    req+="nomTypeAppli"+",";
    req+="nomSi"+",";
    req+="nomMOA"+",";
    req+="prenomMOA"+",";
    req+="nomMOE"+",";
    req+="prenomMOE"+",";
    req+="nomTypeSi"+",";
    req+="nomMacrost"+",";
    req+="nomPhaseNP"+",";
    req+="nomSousPhaseNP"+",";
    req+="nomFrequence"+",";
    req+="icone"+",";
    req+="nomServiceMOE"+",";
    req+="nomDirectionMOE"+",";
    req+="nomServiceMOA"+",";
    req+="nomDirectionMOA"+",";
    req+="nomServicePROD"+",";
    req+="nomDirectionPROD"+",";
    req+="nomEtat"+",";
    req+="ContactMOE"+",";
    req+="isRecurrent"+",";
    req+="isMeeting"+",";
    req+="idMaturite"+",";
    req+="nomMaturite"+",";
    req+="idDomaine"+",";
    req+="nomDomaine"+",";
    req+="MOEProd1"+",";
    req+="[Backup]"+",";
    req+="ControleAcces" +",";
    req+="Complexite" +",";
    req+="Logo" +",";
    req+="idTypeClient" +",";
    req+="nomTypeClient" +",";
    req+="serviceMetier" +",";
    req+="nomMacrostTec" +",";
    req+="niveauCharge" +",";
    req+="creerProjet";
    req+=",";
    req+="isEquipement";
    req+=",";
    req+="isComposant";
    req+=",";
    req+="isActeur";
    req+=",";
    req+="nomEtatFiche";    

    req+=" FROM ST_ListeSt_All_Attribute WHERE ";
    if (this.TypeCreation.equals("idSt"))
        req += "idSt="+this.idSt;
      else if (this.TypeCreation.equals("idVersionSt"))
        req  += "idVersionSt="+this.idVersionSt;
      else
        req  += "nomSt='"+this.nomSt+"'";
      //Connexion.trace(":::::::::::::::::::::::::","req",req);
      //req = "SELECT * FROM ST";
    rs = Connexion.ExecReq(st, nomBase, req);

    //if (rs == null) return;
    try {
      while (rs.next()) {
        this.idSt= rs.getInt("idSt");
        this.nomSt= rs.getString("nomSt");
        this.trigrammeSt= rs.getString("trigrammeSt");;
        this.chronoSt=rs.getInt("chronoSt");
        this.typeAppliSt=rs.getInt("typeAppliSt");
        this.Criticite= rs.getString("Criticite");
        this.ClefImport= rs.getString("ClefImport");;
        this.diagramme= rs.getString("diagramme");;


        this.isST=rs.getInt("isST");
        this.isAppli=rs.getInt("isAppli");
        if ((this.isAppli==1) && (this.isST==1))
        {
          this.typeSpinoza = "Application & ST (cf Onglet OM)";
        }
        else if ((this.isST==1) && (this.isAppli==0))
        {
          this.typeSpinoza = "ST (cf Onglet OM)";
        }
        else if ((this.isST==0) &&(this.isAppli==1))
        {
          this.typeSpinoza = "Application (cf Onglet OM)";
        }
        else
        {
          this.typeSpinoza = "ni Application ni ST (cf Onglet OM)";
    }

        this.idVersionSt=rs.getInt("idVersionSt");
        this.stVersionSt=rs.getInt("stVersionSt");
        this.numVersionSt= rs.getInt("numVersionSt");
        this.etatFicheVersionSt=rs.getInt("etatFicheVersionSt");
        this.siVersionSt=rs.getInt("siVersionSt");
        this.typeSiVersionSt=rs.getInt("typeSiVersionSt");
        this.versionRefVersionSt= rs.getString("versionRefVersionSt");if (this.versionRefVersionSt==null) this.versionRefVersionSt="";
        this.creationVersionSt= rs.getDate("creationVersionSt");
        this.derMajVersionSt= rs.getDate("derMajVersionSt");
        this.descVersionSt= rs.getString("descVersionSt");;
        this.deltaVersionSt= rs.getString("deltaVersionSt");;
        this.macrostVersionSt=rs.getInt("macrostVersionSt");
        this.phaseNPVersionSt=rs.getInt("phaseNPVersionSt");
        this.sousPhaseNPVersionSt=rs.getInt("sousPhaseNPVersionSt");
        this.typeArchiVersionSt=rs.getInt("typeArchiVersionSt");
        this.osVersionSt=rs.getInt("osVersionSt");
        this.bdVersionSt=rs.getInt("bdVersionSt");
        this.hebergVersionSt=rs.getInt("hebergVersionSt");
        this.gestionUtilVersionSt=rs.getInt("gestionUtilVersionSt");
        this.nbLigCodeVersionSt=rs.getInt("nbLigCodeVersionSt");
        this.nbPtsFctVersionSt=rs.getInt("nbPtsFctVersionSt");

        this.iReagirVersionSt=rs.getInt("iReagirVersionSt");
        if (this.iReagirVersionSt==1) this.str_iReagirVersionSt="Oui"; else this.str_iReagirVersionSt="Non";

        this.serviceMoeVersionSt=rs.getInt("serviceMoeVersionSt");
        this.respMoeVersionSt=rs.getInt("respMoeVersionSt");
        this.serviceMoaVersionSt=rs.getInt("serviceMoaVersionSt");
        this.serviceExploitVersionSt=rs.getInt("serviceExploitVersionSt");

        this.mepVersionSt= rs.getDate("mepVersionSt");
        this.dateMep=Utils.getDateEnglish(mepVersionSt);

        this.etatVersionSt=rs.getInt("etatVersionSt");
        this.coutFabrVersionSt=rs.getInt("coutFabrVersionSt");
        this.crenUtilVersionSt=rs.getInt("crenUtilVersionSt");
        this.externalVersionSt=rs.getBoolean("externalVersionSt");

        this.lienManuelVersionSt= rs.getString("lienManuelVersionSt");;
        if (this.lienManuelVersionSt != null)
        {
          this.lienManuelVersionSt = this.lienManuelVersionSt.replaceAll(
              "\\\\\\\\", "\\\\");
          int index = this.lienManuelVersionSt.lastIndexOf("\\");
          this.titreLienManuelVersionSt = this.lienManuelVersionSt.substring(
              index + 1);
        }
        this.derVersionSt=rs.getBoolean("derVersionSt");
        this.auteurVersionSt=rs.getInt("auteurVersionSt");
        this.precVersionSt=rs.getInt("precVersionSt");
        this.suivVersionSt=rs.getInt("suivVersionSt");
        this.nbUtilVersionSt=rs.getInt("nbUtilVersionSt");
        this.respMoaVersionSt=rs.getInt("respMoaVersionSt");

        this.killVersionSt= rs.getDate("killVersionSt");
        this.dateKill=Utils.getDateEnglish(killVersionSt);

        this.okMoaVersionSt=rs.getInt("okMoaVersionSt");
        this.okMoeVersionSt=rs.getInt("okMoeVersionSt");
        this.STRokMoaVersionSt= rs.getString("STRokMoaVersionSt");;

        this.mep1VersionSt= rs.getDate("mep1VersionSt");
        this.dateMep1=Utils.getDateEnglish(mep1VersionSt);

        this.createurVersionSt= rs.getString("createurVersionSt");;
        this.modificateurVersionSt= rs.getString("modificateurVersionSt");;
        this.roadmap= rs.getString("roadmap");;

        this.Mobilite= rs.getString("Mobilite");if ((this.Mobilite==null) || (this.Mobilite.equals("-1"))) this.Mobilite="";
        if (this.Mobilite.equals(""))
                {
                 this.displayMobilitePda = "none";
                 this.displayMobiliteImode = "none";
                }
                else
                {
                        if ((this.Mobilite .toLowerCase().equals("pda")) ) this.displayMobilitePda= ""; else this.displayMobilitePda = "none";
                        if ((this.Mobilite .toLowerCase().equals("i-mode"))) this.displayMobiliteImode= ""; else this.displayMobiliteImode = "none";
        }

        this.Externalisation= rs.getString("Externalisation");if ((this.Externalisation==null) || (this.Externalisation.equals("-1"))) this.Externalisation="";
        if (this.Externalisation.equals("")) this.displayExternalisation = "none";
          else
                {
                  if (this.Externalisation.equals("CITRIX")) this.displayExternalisation= ""; else this.displayExternalisation = "none";
                }

        this.dateEB= rs.getDate("dateEB");
        this.ContactOper= rs.getString("ContactOper");if (this.ContactOper==null) this.ContactOper="";
        this.ModeAcq= rs.getString("ModeAcq");if (this.ModeAcq==null) this.ModeAcq="";

        this.TypeApplication= rs.getString("nomTypeAppli");
        if ((this.TypeApplication != null) && (this.TypeApplication.equals("Acteur")))
                {
                  this.DisplayActeur="none";
                }
                else
                {
                  this.DisplayActeur="";
        }

        this.nomSi= rs.getString("nomSI");
        if ((this.nomSi != null) && (this.nomSi.endsWith("SIR"))) this.display_SIR="";else this.display_SIR="none";

        this.nomMOA= rs.getString("nomMOA"); if (this.nomMOA==null) this.nomMOA="";
        this.prenomMOA= rs.getString("prenomMOA"); if (this.prenomMOA==null) this.prenomMOA="";
        this.nomMOE= rs.getString("nomMOE");
        this.prenomMOE= rs.getString("prenomMOE");
        this.TypeSi= rs.getString("nomTypeSi");
        this.nomMacrost= rs.getString("nomMacrost");if (this.nomMacrost==null) this.nomMacrost="";
        this.nomPhaseNP= rs.getString("nomPhaseNP");if (this.nomPhaseNP==null) this.nomPhaseNP="";
        this.nomSousPhaseNP= rs.getString("nomSousPhaseNP");if (this.nomSousPhaseNP==null) this.nomSousPhaseNP="";
        this.nomFrequence= rs.getString("nomFrequence");if (this.nomFrequence==null) this.nomFrequence="";
        this.icone= rs.getString("icone");
        if (this.icone != null) 
        {
            this.icone = this.icone.replaceAll(" ", "");
            if (this.icone.indexOf("null") > -1)
                this.icone="images/Logos/iconeInconnu.gif";
        }
        if ((this.icone == null) || (this.icone.equals("")))
            this.icone="images/Logos/iconeInconnu.gif";
        this.nomServiceMOE= rs.getString("nomServiceMOE");
        this.nomDirectionMOE= rs.getString("nomDirectionMOE");
        this.nomServiceMOA= rs.getString("nomServiceMOA");
        this.nomDirectionMOA= rs.getString("nomDirectionMOA");   if (this.nomDirectionMOA==null) this.nomDirectionMOA="";
        this.nomServicePROD= rs.getString("nomServicePROD");if (this.nomServicePROD==null) this.nomServicePROD="";
        this.nomDirectionPROD= rs.getString("nomDirectionPROD");   if (this.nomDirectionPROD==null) this.nomDirectionPROD="";
        this.nomEtat= rs.getString("nomEtat");
        this.ContactMOE= rs.getString("ContactMOE");if (this.ContactMOE==null) this.ContactMOE="";
        this.isRecurrent=rs.getInt("isRecurrent");
        this.isMeeting=rs.getInt("isMeeting");

        this.idMaturite=rs.getInt("idMaturite");
        this.nomMaturite=rs.getString("nomMaturite");
        this.idDomaine=rs.getInt("idDomaine");
        this.nomDomaine=rs.getString("nomDomaine");

        this.MOEProd1=rs.getString("MOEProd1");if ((this.MOEProd1 == null) || (this.MOEProd1.equals("null"))) this.MOEProd1="";
        this.Backup=rs.getString("Backup");if (this.Backup == null) this.Backup="";
        this.ControleAcces=rs.getString("ControleAcces");if (this.ControleAcces == null) this.ControleAcces="";
        this.Complexite=rs.getInt("Complexite");
        this.logo=rs.getString("logo");
        if (this.logo != null) this.logo = this.logo.replaceAll(" ", "");
        if ((this.logo == null) || (this.logo.equals("")))
            this.logo="images/Logos/iconeInconnu.gif";
        this.idTypeClient=rs.getInt("idTypeClient");
        this.nomTypeClient=rs.getString("nomTypeClient");

        //this.serviceMetier=rs.getInt("serviceMetier");
        this.macrostTecVersionSt=rs.getInt("serviceMetier");
        this.nomMacrostTec=rs.getString("nomMacrostTec");
        if ((this.nomMacrostTec == null) || (this.nomMacrostTec.equals("")))
            this.nomMacrostTec="";        

        if (this.siVersionSt == 14)
          this.niveauCharge =  rs.getString("niveauCharge");
        else
          this.niveauCharge = "";

        this.creerProjet=rs.getInt("creerProjet");
        this.isEquipement=rs.getInt("isEquipement");
        this.isComposant=rs.getInt("isComposant");
        this.isActeur=rs.getInt("isActeur");
        
        this.nomEtatFiche =  rs.getString("nomEtatFiche");

      }



    } catch (SQLException s) {s.getMessage();}

//st2.close();
    this.getVersionProd( nomBase,  myCnx,   st );
}


  public void getVersionProd(String nomBase,Connexion myCnx, Statement st ){

    ResultSet rs;
    req="exec [ST_SelectVersionProd] "+idVersionSt;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    String versionProd="";
    Date dateProd=null;

    try {
      while (rs.next()) {
        versionProd = rs.getString("version");
        dateProd = rs.getDate("dateMep");
      }
    }
            catch (SQLException s) {s.getMessage();}

    if (versionProd.equals(""))
    {
      this.versionProd = this.versionRefVersionSt;
      this.dateMepRoadmap = this.dateMep;
    }
    else
    {
      this.versionProd = versionProd;
      this.dateMepRoadmap = Utils.getDateFrench(dateProd);

    }

    dateProd = null;
    req="SELECT     dateMep FROM    Roadmap WHERE     (idVersionSt = "+this.idVersionSt+") AND ( (description LIKE 'Cr�ation%') OR  (version = 'Cr�ation') ) ";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        dateProd = rs.getDate("dateMep");
      }
    }
            catch (SQLException s) {s.getMessage();}

    if (dateProd==null)
    {
      this.dateMep1Roadmap= this.dateMep1;
    }
    else
    {
      this.dateMep1Roadmap = Utils.getDateFrench(dateProd);

    }
  }
  public String getLoginMOA(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    req="SELECT     Membre.LoginMembre";
    req+=" FROM         VersionSt INNER JOIN";
    req+="                       Membre ON VersionSt.respMoaVersionSt = Membre.idMembre";
    req+=" WHERE     (VersionSt.idVersionSt ="+this.idVersionSt+")";

String LoginMOA="";

    rs = myCnx.ExecReq(st, nomBase,req);
    try { rs.next();
    LoginMOA= rs.getString(1);

} catch (SQLException s) {s.getMessage();}

    return LoginMOA;
  }

  public String getLoginMOE(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs;
    req="SELECT     Membre.LoginMembre";
    req+=" FROM         VersionSt INNER JOIN";
    req+="                       Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
    req+=" WHERE     (VersionSt.idVersionSt ="+this.idVersionSt+")";
String LoginMOE="";

  rs = myCnx.ExecReq(st, nomBase,req);
  try { rs.next();
  LoginMOE= rs.getString(1);

} catch (SQLException s) {s.getMessage();}

  return LoginMOE;
}

  
  public void getListeDocs(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    req="exec ST_selectDocumentationByIdVersionSt "+idVersionSt;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String nomDoc="";
    String cheminDoc="";
    String commentaireDoc="";
    String cheminIcone="";
    String TypeDoc = "";
    int idTypeDoc;
    int Index = 0;
    try {
      while (rs.next()) {
        cheminDoc = rs.getString("chemin").replaceAll("\\\\\\\\", "\\\\"); ;
        if (cheminDoc != null) cheminDoc.replaceAll("\\\\\\\\", "\\\\"); ;
        commentaireDoc = rs.getString("commentaire");
        cheminIcone = rs.getString("CheminIcone");
        nomDoc = rs.getString("nomType");
        TypeDoc = rs.getString("descType");
        idTypeDoc = rs.getInt("idTypeDoc");
        doc theDoc = new doc(-1,cheminDoc,commentaireDoc,cheminIcone,nomDoc,TypeDoc,idTypeDoc);
        this.ListeDocs.addElement(theDoc);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }  

  public void getListeProcessus(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    if (this.TypeApplication.equals("Acteur"))
             {
                  req = "EXEC ACTEUR_SelectProcByActeur "+idSt+"";
             }
             else
             {
                   req = "EXEC ST_SelectProcBySt '"+idSt+"', 'Processus'";
             }

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
          Processus theProcessus = new Processus();
        theProcessus.id = rs.getInt("idProcessus");
        theProcessus.nom = rs.getString("nomProcessus");
        theProcessus.desc = rs.getString("descProcessus");
        
        this.ListeProcessus.addElement(theProcessus);
      }
    }
    catch (Exception e) {}

  }
  public void getListeOM(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    req = "EXEC ST_SelectOmByStOrigine "+this.idSt;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
          OM theOM = new OM(rs.getInt("idObjetMetier"));
        theOM.nom = rs.getString("nomObjetMetier");
        theOM.desc = rs.getString("defObjetMetier");
        
        this.ListeOM.addElement(theOM);
      }
    }
    catch (Exception e) {}

  }  

  public void getListeFormes(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    req="select DISTINCT * from ";
    req+="    (";

    req+=" SELECT DISTINCT TypeAppli.formeTypeAppli, AppliIcone.icone, AppliIcone.couleur";
    req+=" FROM         Interface INNER JOIN";
    req+="                   St ON Interface.origineInterface = St.idSt INNER JOIN";
    req+="                   TypeAppli ON St.typeAppliSt = TypeAppli.idTypeAppli INNER JOIN";
    req+="                   AppliIcone ON TypeAppli.formeTypeAppli = AppliIcone.formeTypeAppli INNER JOIN";
    req+="                   VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req+="                   TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi AND AppliIcone.couleur = TypeSi.couleurSI";
    req+=" WHERE     (Interface.extremiteInterface = "+this.idSt+")";

    req+=" UNION";

    req+=" SELECT DISTINCT TypeAppli.formeTypeAppli, AppliIcone.icone, AppliIcone.couleur";
    req+="     FROM         Interface INNER JOIN";
    req+="                   St ON Interface.extremiteInterface = St.idSt INNER JOIN";
    req+="                   TypeAppli ON St.typeAppliSt = TypeAppli.idTypeAppli INNER JOIN";
    req+="                   AppliIcone ON TypeAppli.formeTypeAppli = AppliIcone.formeTypeAppli INNER JOIN";
    req+="                   VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req+="                   TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi AND AppliIcone.couleur = TypeSi.couleurSI";
    req+=" WHERE     (Interface.extremiteInterface = "+this.idSt+")";

    req+=" UNION";


    req+=" SELECT DISTINCT TypeAppli.formeTypeAppli, AppliIcone.icone, AppliIcone.couleur";
    req+=" FROM         Interface INNER JOIN";
    req+="                   St ON Interface.extremiteInterface = St.idSt INNER JOIN";
    req+="                   TypeAppli ON St.typeAppliSt = TypeAppli.idTypeAppli INNER JOIN";
    req+="                   AppliIcone ON TypeAppli.formeTypeAppli = AppliIcone.formeTypeAppli INNER JOIN";
    req+="                   VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req+="                   TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi AND AppliIcone.couleur = TypeSi.couleurSI";
    req+=" WHERE     (Interface.origineInterface = "+this.idSt+")";


    req+=" UNION";

    req+=" SELECT DISTINCT TypeAppli.formeTypeAppli, AppliIcone.icone, AppliIcone.couleur";
    req+="     FROM         Interface INNER JOIN";
    req+="                   St ON Interface.origineInterface = St.idSt INNER JOIN";
    req+="                   TypeAppli ON St.typeAppliSt = TypeAppli.idTypeAppli INNER JOIN";
    req+="                  AppliIcone ON TypeAppli.formeTypeAppli = AppliIcone.formeTypeAppli INNER JOIN";
    req+="                   VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req+="                   TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi AND AppliIcone.couleur = TypeSi.couleurSI";
    req+=" WHERE     (Interface.origineInterface = "+this.idSt+")";

    req+=" UNION";
    req+=" SELECT      formeTypeAppli, icone, couleur";
    req+="     FROM         AppliIcone";
    req+=" WHERE     (nomTypeAppli = 'regroupement')";

    req+=" )";
    req+=" as mytable";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        String Forme = rs.getString(1);
        String icone = rs.getString(2);
        String couleur = rs.getString(3);
        int Index = icone.lastIndexOf("\\\\");
        icone = icone.substring(Index);
        Forme theForme = new Forme(Forme,couleur, icone );
        this.ListeForme.addElement(theForme);
      }
    }
    catch (Exception e) {}

  }  
  public void getListeModules(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    req = "SELECT     Module.idModule, Module.nomModule, Module.descModule, Module.Ihm, Module.InterfaceSynchrone, Module.InterfaceAsynchrone, Module.idTypeModule, TypeModule.nom AS nomTypeModule,";
    req += "                      Module.idTypeEnvironnement, typeEnvironnement.nom AS nomTypeEnvironnement, Module.ordre";
    req += "   FROM         Module LEFT OUTER JOIN";
    req += "                         typeEnvironnement ON Module.idTypeEnvironnement = typeEnvironnement.id LEFT OUTER JOIN";
    req += "                         TypeModule ON Module.idTypeModule = TypeModule.id";
    req+= "  WHERE     Module.versionStModule = "+this.idVersionSt;
    req+= " ORDER BY Module.ordre, nomTypeEnvironnement, nomTypeModule";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String nomModule="";
    String descModule="";
    int  Ihm;
    int  ISync;
    int  IAsync;
    try {
      while (rs.next()) {
        int idModule=rs.getInt(1);
        nomModule = rs.getString(2);
        descModule = rs.getString(3);

        Ihm=rs.getInt(4);
        ISync=rs.getInt(5);
        IAsync=rs.getInt(6);
        Module theModule = new Module(idModule,nomModule,descModule,Ihm,ISync,IAsync);
        theModule.idTypeModule = rs.getInt("idTypeModule");
        theModule.nomTypeModule = rs.getString("nomTypeModule");
        theModule.idTypeEnvironnement = rs.getInt("idTypeEnvironnement");
        theModule.nomTypeEnvironnement = rs.getString("nomTypeEnvironnement");  
        if (theModule.nomTypeEnvironnement == null)
        {
            theModule.nomTypeEnvironnement = "";
            theModule.idTypeEnvironnement = -1;
        }
        theModule.ordre=rs.getInt("ordre");
        this.ListeModules.addElement(theModule);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }
  
  public void getListeInterfaceByType(String nomBase,Connexion myCnx, Statement st, String  TypeInterface, String sens){

    String ClauseST="";
    int num_Clause=0;

    if ((this.isEquipement == 0) && (this.isComposant == 0) && (this.isST == 0) &&(this.isAppli == 0) && (this.isActeur == 0)) return;

    if (this.isEquipement == 1)
    {
      if (num_Clause == 0)
        ClauseST += "  (";
      else
        ClauseST += " OR (";

      ClauseST+= " (isEquipement_1 = "+this.isEquipement+") ";
      ClauseST += ") ";
      num_Clause++;
    }

    if (this.isComposant == 1)
    {
      if (num_Clause == 0)
        ClauseST += "  (";
      else
        ClauseST += " OR (";

      ClauseST+= " (isComposant_1 = "+this.isComposant+") ";
      ClauseST += ") ";
      num_Clause++;
    }

    if (this.isST == 1)
    {
      if (num_Clause == 0)
        ClauseST += "  (";
      else
        ClauseST += " OR (";

      ClauseST+= " (isST_1 = "+this.isST+") ";
      ClauseST += ") ";
      num_Clause++;
    }

    if (this.isAppli == 1)
    {
      if (num_Clause == 0)
        ClauseST += "  (";
      else
        ClauseST += " OR (";

      ClauseST+= " (isAppli_1 = "+this.isAppli+") ";
      ClauseST += ") ";
      num_Clause++;
    }

    if (this.isActeur == 1)
    {
      if (num_Clause == 0)
        ClauseST += "  (";
      else
        ClauseST += " OR (";

      ClauseST+= " (isActeur_1 = "+this.isActeur+") ";
      ClauseST += ") ";
      num_Clause++;
    }

    ResultSet rs;
    req = "SELECT   DISTINCT  isEquipement_2, isComposant_2, isSt_2, isAppli_2, isActeur_2, ";
    req+="                  [Type Interface], Sens";
    req+=" FROM         AutomateInterface";
    req+=" WHERE     ([Type Interface] = N'"+TypeInterface+"')  AND (Sens = N'"+sens+"')";
    if (!ClauseST.equals(""))
    {
      req+=" AND (";
      req += ClauseST;
      req+=" )";
    }
    req+=" ORDER BY [Type Interface]";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String nomModule="";
    String descModule="";
    int  Ihm;
    int  ISync;
    int  IAsync;
    try {
      while (rs.next()) {
        Interface theInterface = new Interface(-1);
        theInterface.StDestination = new ST("");
        theInterface.StDestination.isEquipement = rs.getInt("isEquipement_2");
        theInterface.StDestination.isComposant = rs.getInt("isComposant_2");
        theInterface.StDestination.isST = rs.getInt("isSt_2");
        theInterface.StDestination.isAppli = rs.getInt("isAppli_2");
        theInterface.StDestination.isActeur = rs.getInt("isActeur_2");

        theInterface.typeInterface=rs.getString("Type Interface");
        theInterface.sensInterface=rs.getString("Sens");

        this.ListeInterfaces.addElement(theInterface);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }


  public static int getListeLogiciels_static(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    int nbTotalLc=0;

    String req = "SELECT LogicielMiddleWare,idMiddleware, nomMiddleware FROM Middleware Order By nomMiddleware asc";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    ListeLogiciels_static.removeAllElements();
    try {
      while (rs.next()) {
        int LogicielMiddleWare= rs.getInt("LogicielMiddleWare");
        int idMiddleware= rs.getInt("idMiddleware");
        String nomMiddleware= rs.getString("nomMiddleware");;

        Logiciel theLogiciel = new Logiciel(LogicielMiddleWare,idMiddleware, nomMiddleware);
        ListeLogiciels_static.addElement(theLogiciel);
        }

    } catch (SQLException s) {s.getMessage();}
    return nbTotalLc;
  }

  public static int getListeTypeSi_static(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs;
  int nbTotalLc=0;

  String req = "SELECT     idTypeSi, nomTypeSi FROM  TypeSi";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  ListeTypeSi_static.removeAllElements();
  try {
    while (rs.next()) {
      int idTypeSi= rs.getInt("idTypeSi");
      String nomTypeSi= rs.getString("nomTypeSi");;

      TypeSi theTypeSi = new TypeSi(idTypeSi,nomTypeSi);
      ListeTypeSi_static.addElement(theTypeSi);
      }

  } catch (SQLException s) {s.getMessage();}
  return nbTotalLc;
}

public String getListeLogiciels_STR(String nomBase,Connexion myCnx, Statement st ){
  String str_logiciels="";
  for (int i=0; i < this.ListeLogiciels.size();i++)
  {
    Logiciel theLogiciel = (Logiciel)this.ListeLogiciels.elementAt(i);
    if (i>0) str_logiciels+=",";
    str_logiciels+=theLogiciel.typeLogiciel+":"+theLogiciel.nom;
  }

  return str_logiciels;

}

public String getListeClients(String nomBase,Connexion myCnx, Statement st ){

  String str_Clients="";
  ResultSet rs;
  int nbTotalLc=0;

  req="SELECT     St.nomSt AS nomSt1, St_1.nomSt AS nomSt2";
  req+="    FROM         Interface INNER JOIN";
  req+="                     St ON Interface.extremiteInterface = St.idSt INNER JOIN";
  req+="                     VersionSt ON Interface.origineInterface = VersionSt.idVersionSt INNER JOIN";
  req+="                     St AS St_1 ON VersionSt.stVersionSt = St_1.idSt";
  req+=" WHERE     (Interface.typeInterface = 'I') AND (Interface.extremiteInterface = "+this.idSt+") OR";
  req+="                     (Interface.typeInterface = 'I') AND (Interface.origineInterface = "+this.idVersionSt+")";


  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

int num=0;
  try {
    while (rs.next()) {
      String nomSt= rs.getString("nomSt1");
      String nomSt2= rs.getString("nomSt2");

      if (num >0) str_Clients+=",";
      if (nomSt.equals(this.nomSt))
      {
        str_Clients += nomSt2;
      }
      else
      {
        str_Clients += nomSt;
      }
      num++;
      }

  } catch (SQLException s) {s.getMessage();}
    return str_Clients;

}
  public String getDomaineHebergement(String nomBase,Connexion myCnx, Statement st ){
    String str_Hebergement="";
    ResultSet rs;

    req =  "select distinct nomMacrost  from (" ;
    req+=  " SELECT     St.nomSt, MacroSt.nomMacrost" ;
    req+=  " FROM         Interface INNER JOIN" ;
    req+=  "                      St ON Interface.origineInterface = St.idSt INNER JOIN" ;
    req+=  "                      St AS St_1 ON Interface.extremiteInterface = St_1.idSt INNER JOIN" ;
    req+=  "                      VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN" ;
    req+=  "                      MacroSt ON VersionSt.macrostVersionSt = MacroSt.idMacrost" ;
    req+=  " WHERE     (Interface.typeInterface = 'heb') AND (Interface.extremiteInterface = "+this.idSt+") AND (VersionSt.siVersionSt = 14)   " ;

    req+=  "     UNION" ;

    req+=  " SELECT     St_1.nomSt, MacroSt.nomMacrost" ;
    req+=  " FROM         Interface INNER JOIN" ;
    req+=  "                      St ON Interface.origineInterface = St.idSt INNER JOIN" ;
    req+=  "                      St AS St_1 ON Interface.extremiteInterface = St_1.idSt INNER JOIN" ;
    req+=  "                      VersionSt ON St_1.idSt = VersionSt.stVersionSt INNER JOIN" ;
    req+=  "                      MacroSt ON VersionSt.macrostVersionSt = MacroSt.idMacrost" ;
    req+=  " WHERE     (Interface.typeInterface = 'heb') AND (VersionSt.siVersionSt = 14) AND (Interface.origineInterface = "+this.idSt+")" ;
    req+=  "     ) as mytable  ";
    
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  int num=0;
    try {
      while (rs.next()) {
        String nomMacrost= rs.getString("nomMacrost");
        if (num >0)
            str_Hebergement+=";";
        str_Hebergement += nomMacrost;
        num++;
        }

    } catch (SQLException s) {s.getMessage();}
      return str_Hebergement;      
  }

  public String getListeHbergement(String nomBase,Connexion myCnx, Statement st ){

    String str_Hebergement="";
    ResultSet rs;

    req =  "select distinct nomSt from (";
    req+=" SELECT      St.nomSt";
    req+=" FROM         Interface INNER JOIN";
    req+="                       St ON Interface.origineInterface = St.idSt INNER JOIN";
    req+="                       St AS St_1 ON Interface.extremiteInterface = St_1.idSt INNER JOIN";
    req+="                       VersionSt ON St.idSt = VersionSt.stVersionSt";
    req+=" WHERE     (Interface.typeInterface = 'heb') AND (Interface.extremiteInterface = "+this.idSt+") AND (VersionSt.siVersionSt = 14)";
   
    req+=" UNION";
   
    req+=" SELECT     St_1.nomSt";
    req+=" FROM         Interface INNER JOIN";
    req+="                       St ON Interface.origineInterface = St.idSt INNER JOIN";
    req+="                       St AS St_1 ON Interface.extremiteInterface = St_1.idSt INNER JOIN";
    req+="                       VersionSt ON St_1.idSt = VersionSt.stVersionSt";
    req+=" WHERE     (Interface.typeInterface = 'heb') AND (VersionSt.siVersionSt = 14) AND (Interface.origineInterface = "+this.idSt+")";
    req+=" ) as mytable  ";
    
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  int num=0;
    try {
      while (rs.next()) {
        String nomSt= rs.getString("nomSt");
        if (num >0)
            str_Hebergement+=";";
        str_Hebergement += nomSt;
        num++;
        }

    } catch (SQLException s) {s.getMessage();}
      return str_Hebergement;

  }

  
  public void getInfrastructure(String nomBase,Connexion myCnx, Statement st, Statement st2 ){

    String str_Hebergement="";
    ResultSet rs;

    req =  "select distinct nomSt,idSt, idVersionSt from (";
    req+=" SELECT      St.nomSt as nomSt, St.idSt as idSt, VersionSt.idVersionSt as idVersionSt";
    req+=" FROM         Interface INNER JOIN";
    req+="                       St ON Interface.origineInterface = St.idSt INNER JOIN";
    req+="                       St AS St_1 ON Interface.extremiteInterface = St_1.idSt INNER JOIN";
    req+="                       VersionSt ON St.idSt = VersionSt.stVersionSt";
    req+=" WHERE     (Interface.typeInterface = 'heb') AND (Interface.extremiteInterface = "+this.idSt+") AND (St.isEquipement = 1)";
   
    req+=" UNION";
   
    req+=" SELECT     St_1.nomSt as nomSt, St_1.idSt as idSt, VersionSt.idVersionSt as idVersionSt";
    req+=" FROM         Interface INNER JOIN";
    req+="                       St ON Interface.origineInterface = St.idSt INNER JOIN";
    req+="                       St AS St_1 ON Interface.extremiteInterface = St_1.idSt INNER JOIN";
    req+="                       VersionSt ON St_1.idSt = VersionSt.stVersionSt";
    req+=" WHERE     (Interface.typeInterface = 'heb') AND (St_1.isEquipement = 1) AND (Interface.origineInterface = "+this.idSt+")";
    req+=" ) as mytable  ";
    
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  int num=0;
    try {
      while (rs.next()) {
        String nomSt= rs.getString("nomSt");
        int idSt = rs.getInt("idSt");
        int idVersionSt = rs.getInt("idVersionSt");
        ST theServeur = new ST(idSt,"idSt");
        theServeur.nomSt = nomSt;
        theServeur.idVersionSt = idVersionSt;
        theServeur.getListeModules(nomBase, myCnx, st2);
        this.ListeServeurs.addElement(theServeur);

        }

    } catch (SQLException s) {s.getMessage();}

  }
  
  public String getTypeClient(String nomBase,Connexion myCnx, Statement st ){

    String str_Hebergement="";
    ResultSet rs;

    req =  "select distinct nomImplementation from (";
    req+=" SELECT    nomImplementation";
    req+=" FROM         Interface INNER JOIN";
    req+="                       St ON Interface.origineInterface = St.idSt INNER JOIN";
    req+="                      St AS St_1 ON Interface.extremiteInterface = St_1.idSt INNER JOIN";
    req+="                       VersionSt ON St.idSt = VersionSt.stVersionSt  INNER JOIN";
    req+="                       Implementation ON Interface.implemInterface = Implementation.idImplementation";

    req+=" WHERE     (Interface.typeInterface = 'I') AND (Interface.extremiteInterface = "+this.idSt+") AND (VersionSt.siVersionSt = 7)";

    req+=" UNION";

    req+=" SELECT    nomImplementation";
    req+=" FROM         Interface INNER JOIN";
    req+="                       St ON Interface.origineInterface = St.idSt INNER JOIN";
    req+="                       St AS St_1 ON Interface.extremiteInterface = St_1.idSt INNER JOIN";
    req+="                       VersionSt ON St_1.idSt = VersionSt.stVersionSt  INNER JOIN";
    req+="                       Implementation ON Interface.implemInterface = Implementation.idImplementation";

    req+=" WHERE     (Interface.typeInterface = 'I') AND (VersionSt.siVersionSt = 7) AND (Interface.origineInterface = "+this.idSt+")";
    req+=" ) as mytable";
    
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        str_Hebergement= rs.getString("nomImplementation");

        }

    } catch (SQLException s) {s.getMessage();}
      return str_Hebergement;

  }  

  public int getListeLogiciels(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    int nbTotalLc=0;

    String req = "EXEC ST_SelectMW "+this.idVersionSt;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        int id= rs.getInt("idMiddleware");
        String nom= rs.getString("nomMiddleware");
        int idVersion= rs.getInt("idVersionMW");;
        String nomVersion=rs.getString("nomVersionMW");
        int nbLicences=rs.getInt("nbLicencesST_MW");
        nbTotalLc+=nbLicences;
        int LogicielMiddleWare= rs.getInt("LogicielMiddleWare");
        String typeLogiciel= rs.getString("nomLogiciel");;
        String Archi= rs.getString("Archi");

        Logiciel theLogiciel = new Logiciel(id,nom,idVersion,nomVersion,nbLicences,LogicielMiddleWare,typeLogiciel,Archi);
        this.ListeLogiciels.addElement(theLogiciel);
        }

    } catch (SQLException s) {s.getMessage();}
    return nbTotalLc;
  }

  public int isCompetences(String nomBase,Connexion myCnx, Statement st, int idMiddleware){
    int nb=0;
  ResultSet rs;

  String req = "SELECT     COUNT(*) AS nb";
  req+="    FROM         Middleware INNER JOIN";
  req+="                     Logiciel ON Middleware.LogicielMiddleWare = Logiciel.idLogiciel INNER JOIN";
  req+="                     VersionMW ON Middleware.idMiddleware = VersionMW.mwVersionMW INNER JOIN";
  req+="                     ST_MW ON VersionMW.idVersionMW = ST_MW.mwST_MW INNER JOIN";
  req+="                     VersionSt ON ST_MW.versionStST_MW = VersionSt.idVersionSt";
  req+=" WHERE     (VersionSt.idVersionSt = "+this.idVersionSt+") AND (Middleware.idMiddleware = "+idMiddleware+")";


  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     nb = rs.getInt("nb");
   }

  }
   catch (SQLException s) {
     s.printStackTrace();
   }
   catch (Exception s) {
     s.printStackTrace();
   }

   return nb;
  }

  public void getlisteCollaborateursByCompetence(String nomBase,Connexion myCnx, Statement st, int Type){
  ResultSet rs;

  String req = "SELECT     Middleware.idMiddleware, Middleware.nomMiddleware, Middleware.LogicielMiddleWare, Logiciel.nomLogiciel";
      req+=" FROM         Middleware INNER JOIN";
      req+="                 Logiciel ON Middleware.LogicielMiddleWare = Logiciel.idLogiciel";
      req+=" WHERE     (Middleware.LogicielMiddleWare = "+Type+")";
      req+=" ORDER BY Middleware.nomMiddleware";




  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     Logiciel theLogiciel = new Logiciel();
     theLogiciel.id = rs.getInt("idMiddleware");
     theLogiciel.nom = rs.getString("nomMiddleware");
     theLogiciel.typeLogiciel = rs.getString("nomLogiciel");

     Competence theCompetence = new Competence();
     theCompetence.theSt = this;
     theCompetence.theLogiciel = theLogiciel;
     theCompetence.note=-1; // Ma�trise

     this.ListeCompetences.addElement(theCompetence);
   }

  }
   catch (SQLException s) {
     s.printStackTrace();
   }
   catch (Exception s) {
     s.printStackTrace();
   }
}

  public void getListeCompetencesByType(String nomBase,Connexion myCnx, Statement st, int Type){
  ResultSet rs;

  String req = "SELECT     Middleware.idMiddleware, Middleware.nomMiddleware, Middleware.LogicielMiddleWare, Logiciel.nomLogiciel";
      req+=" FROM         Middleware INNER JOIN";
      req+="                 Logiciel ON Middleware.LogicielMiddleWare = Logiciel.idLogiciel";
      req+=" WHERE     (Middleware.LogicielMiddleWare = "+Type+")";
      req+=" ORDER BY Middleware.nomMiddleware";




  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     Logiciel theLogiciel = new Logiciel();
     theLogiciel.id = rs.getInt("idMiddleware");
     theLogiciel.nom = rs.getString("nomMiddleware");
     theLogiciel.typeLogiciel = rs.getString("nomLogiciel");

     Competence theCompetence = new Competence();
     theCompetence.theSt = this;
     theCompetence.theLogiciel = theLogiciel;
     theCompetence.note=-1; // Ma�trise

     this.ListeCompetences.addElement(theCompetence);
   }

  }
   catch (SQLException s) {
     s.printStackTrace();
   }
   catch (Exception s) {
     s.printStackTrace();
   }
    }

    public void getListeCompetencesByType2(String nomBase,Connexion myCnx, Statement st, int Type){
    ResultSet rs;

    String req = "SELECT     Middleware.idMiddleware, Middleware.nomMiddleware, VersionMW.idVersionMW, VersionMW.nomVersionMW, ST_MW.nbLicencesST_MW, ";
    req+="                  Middleware.LogicielMiddleWare, Logiciel.nomLogiciel, ST_MW.Archi";
    req+=" FROM         ST_MW INNER JOIN";
    req+="                   VersionMW ON ST_MW.mwST_MW = VersionMW.idVersionMW INNER JOIN";
    req+="                   Middleware ON VersionMW.mwVersionMW = Middleware.idMiddleware INNER JOIN";
    req+="                   Logiciel ON Middleware.LogicielMiddleWare = Logiciel.idLogiciel";
    req+=" WHERE     (ST_MW.versionStST_MW = "+this.idVersionSt+") AND (Middleware.LogicielMiddleWare = "+Type+")";
      req+=" ORDER BY ST_MW.Archi";



    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
    while(rs.next())
    {
       Logiciel theLogiciel = new Logiciel();
       theLogiciel.id = rs.getInt("idMiddleware");
       theLogiciel.nom = rs.getString("nomMiddleware");
       theLogiciel.typeLogiciel = rs.getString("nomLogiciel");

       Competence theCompetence = new Competence();
       theCompetence.theSt = this;
       theCompetence.theLogiciel = theLogiciel;
       theCompetence.note=2; // Ma�trise

       this.ListeCompetences.addElement(theCompetence);
     }

    }
     catch (SQLException s) {
       s.printStackTrace();
     }
     catch (Exception s) {
       s.printStackTrace();
     }
    }

  public void getListeTests(String nomBase,Connexion myCnx, Statement st, Statement st2, int idRoadmap){
    this.ListeTests.removeAllElements();
    ResultSet rs;
    String chemin = "";
    String req = "SELECT     Tests.id, Tests.nom, Tests.description, Tests.miseEnOeuvre, Tests.idCategorie,Tests.Ordre, Tests.FilenameMiseEnOeuvre,  Roadmap.version AS nomRoadmap, ";
    req+="                  categorieTest.nom AS nomCategorie";
    req+=" FROM         Tests INNER JOIN";
    req+="                   categorieTest ON Tests.idCategorie = categorieTest.id INNER JOIN";
    req+="                   Roadmap ON categorieTest.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt";
    req+=" WHERE     (VersionSt.idVersionSt = "+this.idVersionSt+")";
    req+=" AND     (Roadmap.idRoadmap <> "+idRoadmap+")";
    req+=" ORDER BY Roadmap.version, nomCategorie";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        Test theTest = new Test(rs.getInt("id"));
        theTest.nom= rs.getString("nom");
        theTest.description= rs.getString("description");
        theTest.miseEnOeuvre= rs.getString("miseEnOeuvre");
        theTest.idCategorie= rs.getInt("idCategorie");
        theTest.Ordre= rs.getInt("Ordre");
        chemin = rs.getString("FilenameMiseEnOeuvre");

        if (chemin == null) chemin = "";
        if (chemin != null)
               {
                 theTest.FilenameMiseEnOeuvre = new doc();
                 theTest.FilenameMiseEnOeuvre.chemin = chemin;
                 theTest.FilenameMiseEnOeuvre.getIcone(nomBase, myCnx, st2);
       }

        theTest.nomRoadmap= rs.getString("nomRoadmap");
        theTest.nomCategorie= rs.getString("nomCategorie");

        this.ListeTests.addElement(theTest);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }
  public void getListeInterfaces(String nomBase,Connexion myCnx, Statement st ){
    this.getListeInterfaces_Commun( nomBase, myCnx,  st, "ST_SelectInterfaceSortedByStDest","", this.ListeInterfaces);
  }

  public void getListeInterfacesAsyncOuSync(String nomBase,Connexion myCnx, Statement st, Statement st2 ){
    Interface myInterface=null;
   ResultSet rs;
   ResultSet rs2;

   req = "EXEC [ST_SelectInterfaceASyncouSyncSortedByStDest] "+this.idSt;
   rs = myCnx.ExecReq(st, nomBase, req);

   try {
    while (rs.next())
     {
       int idInterface = rs.getInt("idInterface");
       int idSt2 = rs.getInt("origineInterface");
       String sensInterface = rs.getString("sensInterface");
       String nomSt= rs.getString("nomStDestination");

       req = "INSERT INTO tempInterface (";
       req+=" idInterface,";
       req+=" idSt,";
       req+=" sensInterface,";
       req+=" nomSt";

       req+=") VALUES (";
           req +="'"+idInterface+"'";
           req +=",'"+idSt2+"'";
           req +=",'"+sensInterface+"'";
           req +=",'"+nomSt+"'";
           req +=")";
        rs2 = myCnx.ExecReq(st2, nomBase, req);
     }


     }
     catch (SQLException s) {s.getMessage();}

  }

  public void getListeIhm(String nomBase,Connexion myCnx, Statement st, Statement st2 ){
    Interface myInterface=null;
   ResultSet rs;
   ResultSet rs2;

   req = "EXEC [ST_SelectIhmSortedByStDest] "+this.idSt;
   rs = myCnx.ExecReq(st, nomBase, req);

   try {
    while (rs.next())
     {
       int idInterface = rs.getInt("idInterface");
       int idSt2 = rs.getInt("origineInterface");
       String sensInterface = rs.getString("sensInterface");
       String nomSt= rs.getString("nomStDestination");

       req = "INSERT INTO tempInterface (";
       req+=" idInterface,";
       req+=" idSt,";
       req+=" sensInterface,";
       req+=" nomSt";

       req+=") VALUES (";
           req +="'"+idInterface+"'";
           req +=",'"+idSt2+"'";
           req +=",'"+sensInterface+"'";
           req +=",'"+nomSt+"'";
           req +=")";
        rs2 = myCnx.ExecReq(st2, nomBase, req);
     }


     }
     catch (SQLException s) {s.getMessage();}

  }  
  public void getListeInterfacesSansActeurs(String nomBase,Connexion myCnx, Statement st ){
    this.getListeInterfaces_Commun( nomBase, myCnx,  st, "ST_SelectDescriptionInterfacesSansActeurs","" , this.ListeInterfacesSansActeurs);
  }

  public void getListeInterfacesAvecActeurs(String nomBase,Connexion myCnx, Statement st ){
  this.getListeInterfaces_Commun( nomBase, myCnx,  st, "ST_SelectDescriptionInterfacesAvecActeurs","", this.ListeInterfacesAvecActeurs );
}

public String bd_enregGraph(String nomBase,Connexion myCnx,Statement st, transaction  theTransaction, String nodes){
  String nomSt;
  int numVersionSt;
  String theVersion = "";
  int idVersionSt=0;
  int idGraph = 0;
  int j=0; int k=0;
  ResultSet rs;

  //Suppression des GraphSt �ventuels pour ce Graph avant Cr�ation
  req = "DELETE GraphSt WHERE graphGraphSt = "+this.NodeSt.idGraphe;

  if (myCnx.ExecUpdate(st,nomBase,req,true,theTransaction.nom) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

//out.println(nodes+"<br><br>");
  String nom = "";
//int num = 0;
  int abscisse, ordonnee;
  String str=""; String str1=""; String str2="";
  int idSt = 0;
  //myCnx.trace("@01234--------------","nodes=",""+nodes);
  for (StringTokenizer t = new StringTokenizer(nodes, "$"); t.hasMoreTokens();) {

          str = t.nextToken(); // format de str : [nomSt] v[numVersionSt]*[abscisse]*[ordonn�e]
          //c1.trace(base,"********************str=",str);
          k = str.indexOf('*');
          str1 = str.substring(0,k);
          //c1.trace(base,"********************str1=",str1);
          str2 = str.substring(k+1);
          //c1.trace(base,"********************str2=",str2);
          //Traitements sur str1
          j = str1.lastIndexOf('v');
          nom = str1;
          //num = Integer.parseInt(str1.substring(j+1));
          //Traitements sur str2 : abscisse*ordonnee
          //myCnx.trace("@01234--------------","str2=",""+str2);
          //myCnx.trace("@01234--------------","j=",""+j);
          j = str2.indexOf('*');
          abscisse = Integer.parseInt(str2.substring(0,j));
          //myCnx.trace("@01234--------------","abscisse=",""+abscisse);
          ordonnee = Integer.parseInt(str2.substring(j+1));
          //myCnx.trace("01234----------------","ordonnee=",""+ordonnee);

          //R�cup�ration de l'idVersionSt
          req = "SELECT idSt FROM St WHERE nomSt='"+nom+"'";
          rs = myCnx.ExecReq(st, nomBase, req);
          try { rs.next(); idSt = Integer.parseInt(rs.getString(1)); } catch (SQLException s) {s.getMessage();}
          //Cr�ation des GraphSt
          req = "INSERT GraphSt (graphGraphSt, stGraphSt, abscisseGraphSt, ordonneeGraphSt) VALUES ("+this.NodeSt.idGraphe+", "+idSt+", "+abscisse+", "+ordonnee+")";

          if (myCnx.ExecUpdate(st,nomBase,req,true,theTransaction.nom) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

} //end for

        return "OK";
}

public String bd_DeleteInterfaces(String nomBase,Connexion myCnx, Statement st, transaction theTransaction){
        req = "EXEC ST_DeleteInter_OM "+this.idSt;
        if (myCnx.ExecUpdate(st,nomBase,req,true,theTransaction.nom) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

        req = "EXEC ST_DeleteInterfaces "+this.idSt;
        if (myCnx.ExecUpdate(st,nomBase,req,true,theTransaction.nom) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

      return "OK";
}

public String bd_DeleteInterfacesOM(String nomBase,Connexion myCnx, Statement st, transaction theTransaction){
            req = "EXEC ST_DeleteInter_OM "+this.idSt;
            if (myCnx.ExecUpdate(st,nomBase,req,true,theTransaction.nom) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

          return "OK";
}

public String getGraph(String nomBase,Connexion myCnx,Statement st, transaction  theTransaction){
  ResultSet rs;
  // la versionSt a-t-elle d�j� un graph ?
  String req = "SELECT idGraph FROM Graph WHERE centreGraph = "+this.idVersionSt;
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  try { rs.next(); this.NodeSt.idGraphe = Integer.parseInt(rs.getString(1)); } catch (SQLException s) {s.getMessage();}
  if (this.NodeSt.idGraphe == 0) { //on cr�e le graph car il n'existe pas
          req = "INSERT Graph (centreGraph) VALUES ("+this.idVersionSt+")";
          if (myCnx.ExecUpdate(st,nomBase,req,true,theTransaction.nom) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

          // et on r�cup�re son idGraph
          req = "SELECT idGraph FROM Graph WHERE centreGraph="+this.idVersionSt;
          rs = myCnx.ExecReq(st, nomBase, req);
          try { rs.next(); this.NodeSt.idGraphe = Integer.parseInt(rs.getString(1)); } catch (SQLException s) {s.getMessage();}
} //end if
        return "OK";
  }

  private void getListeInterfaces_Commun(String nomBase,Connexion myCnx, Statement st, String req1, String req2, Vector theListe ){
    Interface myInterface=null;
    ResultSet rs;
    String centre = "";

    this.NodeSt = new Node(this.nomSt,this.idSt, this.idVersionSt,"GraphSt",this.couleur,this.TypeApplication,"centre", this.idVersionSt);
    this.NodeSt.getGraph( nomBase, myCnx,  st);

    req = "EXEC ["+req1+"] "+this.idSt;
    rs = myCnx.ExecReq(st, nomBase, req);

    try {
     while (rs.next())
      {
        int idInterface = rs.getInt("idInterface");
        int idSt = rs.getInt("origineInterface");
        ST StOrigine = new ST(idSt,"idSt");
        StOrigine.nomSt= rs.getString("nomStOrigine");
        StOrigine.versionRefVersionSt= "";
        StOrigine.TypeApplication= rs.getString("formeTypeAppliOrigine");
        StOrigine.couleur= rs.getString("couleurSIOrigine");
        if (StOrigine.nomSt.equals(this.nomSt)) centre="centre"; else centre="rien";

        String sensInterface = rs.getString("sensInterface");

        int idSt2 = rs.getInt("extremiteInterface");
        ST StDestination = new ST(idSt2,"idSt");
        StDestination.nomSt= rs.getString("nomStDestination");
        StDestination.versionRefVersionSt= "";
        StDestination.TypeApplication= rs.getString("formeTypeAppliDestination");
        StDestination.couleur= rs.getString("couleurSIDestination");
        if (StDestination.nomSt.equals(this.nomSt)) centre="centre"; else centre="rien";


        String typeInterface= rs.getString("typeInterface");
        String nomImplementation= rs.getString("nomImplementation");
        String nomFrequence= rs.getString("nomFrequence");
        String descInterface= rs.getString("descInterface");
        int idImplementation= rs.getInt("idImplementation");
        int idFrequence= rs.getInt("idFrequence");

        StOrigine.idVersionSt= rs.getInt("idVersionStOrigine");
        StDestination.idVersionSt= rs.getInt("idVersionStDestination");

        StOrigine.NodeSt = new Node(StOrigine.nomSt,StOrigine.idSt, StOrigine.idVersionSt,"GraphSt",StOrigine.couleur,StOrigine.TypeApplication,centre,StOrigine.idVersionSt);
        StOrigine.NodeSt.idGraphe=this.NodeSt.idGraphe;
        StOrigine.NodeSt.idLien = StOrigine.idVersionSt;
        //StOrigine.NodeSt.dump();

        StDestination.NodeSt = new Node(StDestination.nomSt,StDestination.idSt, StDestination.idVersionSt,"GraphSt",StDestination.couleur,StDestination.TypeApplication,centre,StDestination.idVersionSt);
        StDestination.NodeSt.idGraphe=this.NodeSt.idGraphe;
        StDestination.NodeSt.idLien = StDestination.idVersionSt;
        //StDestination.NodeSt.dump();

        myInterface=this.addInterface(
           idInterface,
           StOrigine,
           StDestination,
           sensInterface,
           typeInterface,
           nomImplementation,
           nomFrequence,
           descInterface,
           idImplementation,
           idFrequence);

        theListe.addElement(myInterface);
      }


      }
      catch (SQLException s) {s.getMessage();}
  }


  public int getNbInterfaceWithMacroSt(String nomBase,Connexion myCnx, Statement st,int idMacroSt){
    int nb=0;

    req="SELECT     COUNT(*) AS nb";
    req+="    FROM         Interface INNER JOIN";
    req+="                   VersionSt AS vst1 ON Interface.origineInterface = vst1.idVersionSt INNER JOIN";
    req+="                   St AS st1 ON vst1.stVersionSt = st1.idSt INNER JOIN";
    req+="                   TypeAppli AS t1 ON st1.typeAppliSt = t1.idTypeAppli INNER JOIN";
    req+="                   SI AS si1 ON vst1.siVersionSt = si1.idSI INNER JOIN";
    req+="                   TypeSi AS ts1 ON vst1.typeSiVersionSt = ts1.idTypeSi INNER JOIN";
    req+="                   St AS st2 ON Interface.extremiteInterface = st2.idSt INNER JOIN";
    req+="                   VersionSt AS vst2 ON st2.idSt = vst2.stVersionSt INNER JOIN";
    req+="                   TypeAppli AS t2 ON st2.typeAppliSt = t2.idTypeAppli INNER JOIN";
    req+="                   SI AS si2 ON vst2.siVersionSt = si2.idSI INNER JOIN";
    req+="                   TypeSi AS ts2 ON vst2.typeSiVersionSt = ts2.idTypeSi";
    req+=" WHERE     (Interface.origineInterface = "+this.idVersionSt+") AND (vst2.etatFicheVersionSt = 3) AND (vst2.numVersionSt =";
    req+="                       (SELECT     MAX(numVersionSt) AS Expr1";
    req+="                         FROM          VersionSt";
    req+="                         WHERE      (stVersionSt = st2.idSt) AND (etatFicheVersionSt = 3))) AND (vst2.macrostVersionSt = "+idMacroSt+") OR";
    req+="                   (Interface.extremiteInterface = "+this.idSt+") AND (vst2.idVersionSt = "+this.idVersionSt+") AND (vst1.etatFicheVersionSt = 3) AND (vst1.numVersionSt =";
    req+="                       (SELECT     MAX(numVersionSt) AS Expr1";
    req+="                         FROM          VersionSt AS VersionSt_1";
    req+="                         WHERE      (stVersionSt = st1.idSt) AND (etatFicheVersionSt = 3))) AND (vst1.macrostVersionSt = "+idMacroSt+")";


   req = "select COUNT (*) as nb from";
   req+="  (";
   req+="  SELECT    St.nomSt";
   req+="  FROM         Interface INNER JOIN";
   req+="                        St ON Interface.extremiteInterface = St.idSt INNER JOIN";
   req+="                        VersionSt ON St.idSt = VersionSt.stVersionSt";
   req+="  WHERE     (Interface.origineInterface = "+this.idSt+") AND (VersionSt.macrostVersionSt = "+idMacroSt+")";

   req+="  UNION ";

   req+="  SELECT     St.nomSt";
   req+="  FROM         VersionSt INNER JOIN";
   req+="                        St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req+="                        Interface ON St.idSt = Interface.origineInterface";
   req+="  WHERE      (Interface.extremiteInterface = "+this.idSt+") AND (VersionSt.macrostVersionSt = "+idMacroSt+")";
   req+="  ) as mytable";


    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try {rs.next();

      {
        nb=rs.getInt("nb");
      }


      }
      catch (SQLException s) {s.getMessage();}

    return nb;

  }

  public void getStAncrage(String nomBase,Connexion myCnx, Statement st){
    req = "SELECT     St.nomSt AS Origine, St_1.nomSt AS Destination, VersionSt.idVersionSt AS idVersionStOrigine, VersionSt_1.idVersionSt AS idVersionStDestination";
    req+="    FROM         Interface INNER JOIN";
    req+="                   St ON Interface.origineInterface = St.idSt INNER JOIN";
    req+="                   St AS St_1 ON Interface.extremiteInterface = St_1.idSt INNER JOIN";
    req+="                   VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req+="                   VersionSt AS VersionSt_1 ON St_1.idSt = VersionSt_1.stVersionSt";
    req+=" WHERE     ((Interface.origineInterface = "+this.idSt+") OR";
    req+="                   (Interface.extremiteInterface = "+this.idSt+"))";
    req+=" AND (Interface.typeInterface = 'ST') ";


    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try {rs.next();

      {

        String nomStOrigine= rs.getString("Origine");
        String nomStDestination= rs.getString("Destination");

        int idVersionStOrigine=rs.getInt("idVersionStOrigine");
        int idVersionStDestination=rs.getInt("idVersionStDestination");

        if (idVersionStOrigine != this.idVersionSt)
        {
          this.StAncrage = new ST(idVersionStOrigine, "idVersionSt");
          this.StAncrage.nomSt = nomStOrigine;
        }
        else
        {
          this.StAncrage = new ST(idVersionStDestination, "idVersionSt");
          this.StAncrage.nomSt = nomStDestination;
        }
      }


      }
      catch (SQLException s) {s.getMessage();}


  }

  public void getStAncrage2(String nomBase,Connexion myCnx, Statement st){
    req = "SELECT     st1.nomSt AS Origine, Interface.sensInterface, st2.nomSt AS Destination, Interface.typeInterface, vst2.idVersionSt AS idVersionStOrigine, ";
    req+= "                   vst1.idVersionSt AS idVersionStDestination";
    req+= " FROM         Interface INNER JOIN";
    req+= "                   VersionSt AS vst1 ON Interface.origineInterface = vst1.idVersionSt INNER JOIN";
    req+= "                   St AS st1 ON vst1.stVersionSt = st1.idSt INNER JOIN";
    req+= "                   TypeAppli AS t1 ON st1.typeAppliSt = t1.idTypeAppli INNER JOIN";
    req+= "                   SI AS si1 ON vst1.siVersionSt = si1.idSI INNER JOIN";
    req+= "                   TypeSi AS ts1 ON vst1.typeSiVersionSt = ts1.idTypeSi INNER JOIN";
    req+= "                   St AS st2 ON Interface.extremiteInterface = st2.idSt INNER JOIN";
    req+= "                   VersionSt AS vst2 ON st2.idSt = vst2.stVersionSt INNER JOIN";
    req+= "                   TypeAppli AS t2 ON st2.typeAppliSt = t2.idTypeAppli INNER JOIN";
    req+= "                   SI AS si2 ON vst2.siVersionSt = si2.idSI INNER JOIN";
    req+= "                   TypeSi AS ts2 ON vst2.typeSiVersionSt = ts2.idTypeSi INNER JOIN";
    req+= "                   Implementation ON Interface.implemInterface = Implementation.idImplementation INNER JOIN";
    req+= "                  Frequence ON Interface.frequenceInterface = Frequence.idFrequence";
    req+= " WHERE     (Interface.origineInterface = "+this.idVersionSt+") AND (vst2.etatFicheVersionSt = 3) AND (vst2.numVersionSt =";
    req+= "                       (SELECT     MAX(numVersionSt) AS Expr1";
    req+= "                         FROM          VersionSt";
    req+= "                         WHERE      (stVersionSt = st2.idSt) AND (etatFicheVersionSt = 3) AND (etatVersionSt <> 4))) AND (Interface.typeInterface = 'ST') AND ";
    req+= "                   (Interface.sensInterface = '<---') OR";
    req+= "                   (Interface.typeInterface = 'ST') AND (vst1.etatFicheVersionSt = 3) AND (vst1.numVersionSt =";
    req+= "                       (SELECT     MAX(numVersionSt) AS Expr1";
    req+= "                         FROM          VersionSt AS VersionSt_1";
    req+= "                         WHERE      (stVersionSt = st1.idSt) AND (etatFicheVersionSt = 3) AND (etatVersionSt <> 4))) AND (Interface.extremiteInterface = "+this.idSt+") AND ";
    req+= "                   (vst2.idVersionSt = "+this.idVersionSt+") AND (Interface.sensInterface = '--->')";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try {rs.next();

      {

        String nomStOrigine= rs.getString("Origine");
        String nomStDestination= rs.getString("Destination");

        int idVersionStOrigine=rs.getInt("idVersionStOrigine");
        int idVersionStDestination=rs.getInt("idVersionStDestination");

        if (idVersionStOrigine != this.idVersionSt)
        {
          this.StAncrage = new ST(idVersionStOrigine, "idVersionSt");
          this.StAncrage.nomSt = nomStDestination;
        }
        else
        {
          this.StAncrage = new ST(idVersionStDestination, "idVersionSt");
          this.StAncrage.nomSt = nomStOrigine;
        }
      }


      }
      catch (SQLException s) {s.getMessage();}


  }


  public void getListeRoadmapFromMySt(String nomBase,Connexion myCnx, Statement st, String Annee){
    Interface myInterface=null;

    if (this.StAncrage == null) return;

    req = "SELECT     idRoadmap, version, dateMep ";
    req+= " FROM         Roadmap";
    req+= " WHERE     (idVersionSt = "+this.StAncrage.idVersionSt+") AND (YEAR(dateMep) >= "+Annee+")";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    java.util.Date theDate;
    java.util.Date DdateMep;


    try {
     while (rs.next())
      {

        int idRoadmap=rs.getInt("idRoadmap");
        String version= rs.getString("version");
        DdateMep = rs.getDate("dateMep");
        if (DdateMep != null)
        {
          String str_day="";
          String str_month="";
          if (DdateMep.getDate() < 10)
            str_day = "0"+DdateMep.getDate();
          else
            str_day = ""+DdateMep.getDate();

          if ((DdateMep.getMonth() + 1) < 10)
            str_month = "0"+(DdateMep.getMonth() + 1);
          else
            str_month = ""+(DdateMep.getMonth() + 1);

          dateMep = ""+DdateMep.getDate() + "/" + (DdateMep.getMonth() + 1) + "/" + (DdateMep.getYear() + 1900);
        }
         else
          dateMep = "";
        Roadmap theRoadmap = new Roadmap(idRoadmap);
        theRoadmap.nomSt=this.StAncrage.nomSt;
        theRoadmap.idVersionSt=""+this.StAncrage.idVersionSt;
        theRoadmap.version = version;
        theRoadmap.dateProd = dateMep;
        theRoadmap.DdateProd = DdateMep;


        this.ListeRoadmap.addElement(theRoadmap);
      }


      }
      catch (SQLException s) {s.getMessage();}
  }

  public void getListeRoadmap(String nomBase,Connexion myCnx, Statement st){
    Interface myInterface=null;

    req = "SELECT     idRoadmap, version, dateMep, dateEB, description ";
    req+= " FROM         Roadmap";
    req+= " WHERE     (idVersionSt = "+this.idVersionSt+") AND (LF_Month = - 1) AND (LF_Year = - 1)";
    req+= " ORDER BY dateMep DESC";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    java.util.Date theDate;
    java.util.Date DdateMep;
    java.util.Date DdateEb;


    try {
     while (rs.next())
      {

        int idRoadmap=rs.getInt("idRoadmap");
        String version= rs.getString("version");
        DdateMep = rs.getDate("dateMep");
        
        Roadmap theRoadmap = new Roadmap(idRoadmap);
                
        if (DdateMep != null)
        {
          String str_day="";
          String str_month="";
          if (DdateMep.getDate() < 10)
            str_day = "0"+DdateMep.getDate();
          else
            str_day = ""+DdateMep.getDate();

          if ((DdateMep.getMonth() + 1) < 10)
            str_month = "0"+(DdateMep.getMonth() + 1);
          else
            str_month = ""+(DdateMep.getMonth() + 1);

          dateMep = ""+DdateMep.getDate() + "/" + (DdateMep.getMonth() + 1) + "/" + (DdateMep.getYear() + 1900);
        }
         else
          dateMep = "";
        
        DdateEb = rs.getDate("dateEb");
        if (DdateEb != null)
        {
          String str_day="";
          String str_month="";
          if (DdateEb.getDate() < 10)
            str_day = "0"+DdateEb.getDate();
          else
            str_day = ""+DdateEb.getDate();

          if ((DdateEb.getMonth() + 1) < 10)
            str_month = "0"+(DdateEb.getMonth() + 1);
          else
            str_month = ""+(DdateEb.getMonth() + 1);

          theRoadmap.dateEB= ""+DdateEb.getDate() + "/" + (DdateEb.getMonth() + 1) + "/" + (DdateEb.getYear() + 1900);
        }
         else
          dateMep = "";
        
        theRoadmap.description= rs.getString("description");
        
        theRoadmap.nomSt=this.nomSt;
        theRoadmap.idVersionSt=""+this.idVersionSt;
        theRoadmap.version = version;
        theRoadmap.dateProd = dateMep;
        theRoadmap.DdateProd = DdateMep;


        this.ListeRoadmap.addElement(theRoadmap);
      }


      }
      catch (SQLException s) {s.getMessage();}
  }
  public void getListeRoadmap(String nomBase,Connexion myCnx, Statement st, String Annee){
    Interface myInterface=null;

    req = "SELECT     idRoadmap, version, dateMep ";
    req+= " FROM         Roadmap";
    req+= " WHERE     (idVersionSt = "+this.idVersionSt+") AND (YEAR(dateMep) >= "+Annee+") AND (LF_Month = - 1) AND (LF_Year = - 1)";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    java.util.Date theDate;
    java.util.Date DdateMep;


    try {
     while (rs.next())
      {

        int idRoadmap=rs.getInt("idRoadmap");
        String version= rs.getString("version");
        DdateMep = rs.getDate("dateMep");
        if (DdateMep != null)
        {
          String str_day="";
          String str_month="";
          if (DdateMep.getDate() < 10)
            str_day = "0"+DdateMep.getDate();
          else
            str_day = ""+DdateMep.getDate();

          if ((DdateMep.getMonth() + 1) < 10)
            str_month = "0"+(DdateMep.getMonth() + 1);
          else
            str_month = ""+(DdateMep.getMonth() + 1);

          dateMep = ""+DdateMep.getDate() + "/" + (DdateMep.getMonth() + 1) + "/" + (DdateMep.getYear() + 1900);
        }
         else
          dateMep = "";
        Roadmap theRoadmap = new Roadmap(idRoadmap);
        theRoadmap.nomSt=this.nomSt;
        theRoadmap.idVersionSt=""+this.idVersionSt;
        theRoadmap.version = version;
        theRoadmap.dateProd = dateMep;
        theRoadmap.DdateProd = DdateMep;


        this.ListeRoadmap.addElement(theRoadmap);
      }


      }
      catch (SQLException s) {s.getMessage();}
  }


  public Interface addInterface(
      int idInterface,
      ST StOrigine,
      ST StDestination,
      String sensInterface,
      String typeInterface,
      String nomImplementation,
      String nomFrequence,
      String descInterface,
      int idImplementation,
      int idFrequence)
  {
      Interface theInterface=new Interface(
        idInterface,
        StOrigine,
        StDestination,
        sensInterface,
        typeInterface,
        nomImplementation,
        nomFrequence,
        descInterface,
        idImplementation,
        idFrequence);

    return theInterface;

  }

  public ErrorSpecific bd_InsertRegions(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
    req = "DELETE FROM StRegion WHERE idSt="+this.idSt;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertRegions",""+this.idSt);
    if (myError.cause == -1) return myError;

     for (int i=0; i < this.ListeRegions.size(); i++)
     {
       Region theRegion = (Region)this.ListeRegions.elementAt(i);

       if (theRegion.nom!=null)
             {
               req = "INSERT INTO StRegion  (idST, idRegion) VALUES     ("+this.idSt+", "+theRegion.id+")";
               myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertRegions",""+this.idSt);
               if (myError.cause == -1) return myError;
             }
     }

     return myError;

  }

  public void addNonReg(String nomBase,Connexion myCnx, Statement st){
    int nb=0;
    ResultSet rs=null;
    req = "SELECT     COUNT(*) AS nb";
    req+="    FROM         Roadmap";
    req+=" WHERE     (idVersionSt = "+this.idVersionSt+") AND (version = '-- NON REGRESSION')";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      while (rs.next()) {
        nb=rs.getInt("nb");

      }
    }
            catch (SQLException s) {s.getMessage();}

   if (nb == 0)
   {
     req = "INSERT INTO Roadmap (";
     req+=" version,";
     req+=" idVersionSt,";
     req+=" LF_Month,";
     req+=" LF_Year,";
     req+=" idRoadmapPere";

     req+=") VALUES (";
         req +="'"+"-- NON REGRESSION"+"'";
         req +=",'"+this.idVersionSt+"'";
         req +=",'"+"-1"+"'";
         req +=",'"+"-1"+"'";
         req +=",'"+"-1"+"'";
         req +=")";
    myCnx.ExecReq(st, nomBase, req);
   }

  }
  
  public ErrorSpecific bd_InsertFaqRad(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;

    ErrorSpecific myError = new ErrorSpecific();
      req =  "DELETE FROM Roadmap WHERE (isFaq = 1) AND idVersionSt="+this.idVersionSt;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateConfig",""+this.idSt);
      if (myError.cause == -1) return myError;
       
      req = "INSERT Roadmap (version, idVersionSt, Etat, Panier, Ordre, LF_Month, LF_Year, isFAQ, idRoadmapPere)";
      req+="  VALUES (";
      req+="'"+"MEX"+"'";
      req+=",";
      req+="'"+this.idVersionSt+"'";
      req+=",";
      req+="'"+"PROD"+"'";
      req+=",";
      req+="'"+0+"'";
      req+=",";
      req+="'"+"-1"+"'";     
      req+=",";
      req+="'"+"-1"+"'";   
      req+=",";
      req+="'"+"-1"+"'"; 
      req+=",";
      req+="'"+"1"+"'";
      req+=",";
      req+="'"+"-1"+"'";       
      req+=")";

     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertFaqRad",""+this.idSt);

     return myError;

  } 
  
  public Roadmap getRoadmapFaqRad(String nomBase,Connexion myCnx, Statement st ){

   req = "SELECT     idRoadmap, version FROM Roadmap WHERE     (isFAQ = 1) AND idVersionSt="+this.idVersionSt;

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   Roadmap theRoadmap = null;
   try {
      while (rs.next()) {
        theRoadmap = new Roadmap(rs.getInt(1));
      }
       } catch (SQLException s) {s.getMessage();}
   return theRoadmap;
 }
  
  public String bd_InsertOM(String nomBase,Connexion myCnx, Statement st, String transaction,Interface theInterf){
    ResultSet rs=null;

     //theInterf.dump();
     for (int i=0; i < theInterf.ListeOM.size(); i++)
     {
       OM theOM = (OM)theInterf.ListeOM.elementAt(i);
       //theOM.dump();
       req = "EXEC ST_InsererInter_OM '"+theInterf.idInterface+"', '"+theOM.idObjetMetier+"'";
       if (myCnx.ExecUpdate(st,nomBase,req,true,"EnregST") == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     }

     return "OK";
  }

  public ErrorSpecific bd_InsertModules(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
    req = "EXEC ST_DeleteModules "+this.idVersionSt;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertModules",""+this.idVersionSt);
    if (myError.cause == -1) return myError;

     for (int i=0; i < this.ListeModules.size(); i++)
     {
       Module theModule = (Module)this.ListeModules.elementAt(i);
       if (theModule.descModule == null) theModule.descModule = "";
       req = "INSERT Module (nomModule,descModule, versionStModule, idTypeModule, idTypeEnvironnement, ordre)" ;
        req+= " VALUES (";
        req+= "'"+ theModule.nom.replaceAll("'","''") + "'";
        req+= ",";
        req+= "'"+ theModule.descModule.replaceAll("'","''") + "'";
        req+= ",";
        req+= "'"+ this.idVersionSt + "'";
        req+= ",";        
        req+= "'"+ theModule.idTypeModule + "'";
        req+= ",";
        req+= "'"+ theModule.idTypeEnvironnement + "'";
        req+= ",";        
        req+= "'"+ theModule.ordre + "'";
        req+= " )";

       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertModules",""+this.idVersionSt);
       if (myError.cause == -1) return myError;
     }

     return myError;

  }

  public ErrorSpecific bd_InsertMw(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
    req =  "EXEC ST_DeleteST_MW "+this.idVersionSt;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertMw",""+this.idVersionSt);
    if (myError.cause == -1) return myError;

     for (int i=0; i < this.ListeLogiciels.size(); i++)
     {
       Logiciel theLogiciel = (Logiciel)this.ListeLogiciels.elementAt(i);
       req = "EXEC ST_InsererST_MW '"+this.idVersionSt+"', '"+theLogiciel.LogicielMiddleWare+"', '"+theLogiciel.nbLicences+"', '"+theLogiciel.Archi+"'";
       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertMw",""+this.idVersionSt);
       if (myError.cause == -1) return myError;
     }

     return myError;

  }
  
  public ErrorSpecific bd_InsertOneMw(String nomBase,Connexion myCnx, Statement st, String transaction, Logiciel theLogiciel){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

       req = "EXEC "
               + "ST_InsererST_MW '"+this.idVersionSt+"', '"+theLogiciel.LogicielMiddleWare+"', '"+theLogiciel.nbLicences+"', '"+theLogiciel.Archi+"'";
       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertMw",""+this.idVersionSt);
       if (myError.cause == -1) return myError;
       
       req = "SELECT     TOP (1) Middleware.idMiddleware";
       req+= " FROM         ST_MW INNER JOIN";
       req+= "                       VersionMW ON ST_MW.mwST_MW = VersionMW.idVersionMW INNER JOIN";
       req+= "                       Middleware ON VersionMW.mwVersionMW = Middleware.idMiddleware";
       req+= " ORDER BY ST_MW.idST_MW DESC";
       
       rs = myCnx.ExecReq(st, nomBase, req);
       try {rs.next(); this.nb =rs.getInt("idMiddleware");} catch (SQLException s) {s.getMessage();}       
     
     return myError;

  }
  
  public ErrorSpecific bd_UpdateOneMw(String nomBase,Connexion myCnx, Statement st, String transaction, Logiciel theLogiciel){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    req = "UPDATE [ST_MW]";
    req+="    SET ";
    req+="    [versionStST_MW] = '"+ this.idVersionSt+ "'";
    req+="   ,[mwST_MW] = '"+ theLogiciel.LogicielMiddleWare+ "'";
    req+="   ,[nbLicencesST_MW] = '"+ theLogiciel.nbLicences+ "'";
    req+="   ,[Archi] = '"+ theLogiciel.Archi+ "'";
    req+=" WHERE idST_MW=" + theLogiciel.idST_MW;
    
     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertMw",""+this.idVersionSt);
     if (myError.cause == -1) return myError;    
     
    req= "SELECT      Middleware.idMiddleware";
    req+=" FROM         ST_MW INNER JOIN";
    req+="                       VersionMW ON ST_MW.mwST_MW = VersionMW.idVersionMW INNER JOIN";
    req+="                       Middleware ON VersionMW.mwVersionMW = Middleware.idMiddleware";
    req+=" WHERE idST_MW=" + theLogiciel.idST_MW;

       rs = myCnx.ExecReq(st, nomBase, req);
       try {rs.next(); this.nb =rs.getInt("idMiddleware");} catch (SQLException s) {s.getMessage();}         
     
     return myError;

  } 
  
  public ErrorSpecific bd_deleteOneMw(String nomBase,Connexion myCnx, Statement st, String transaction, Logiciel theLogiciel){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    req =  "delete from ST_MW";
    req+=" WHERE idST_MW=" + theLogiciel.idST_MW;
    
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertMw",""+this.idVersionSt);
    if (myError.cause == -1) return myError;
     
     return myError;

  }  

  private boolean isInListe(int id){
    for (int i=0; i < this.ListeInterfacesSansActeurs.size(); i++)
    {
      Interface theInterface = (Interface)this.ListeInterfacesSansActeurs.elementAt(i);
      //Connexion.trace("isInListe--------","ListeInterfacesSansActeurs.idInterface",""+theInterface.idInterface);
      if (theInterface.idInterface == id)
      {
        //Connexion.trace("isInListe--------","dans la ListeInterfacesSansActeurs",""+id);
        return true;
      }

    }

    for (int i=0; i < this.ListeInterfacesAvecActeurs.size(); i++)
    {
      Interface theInterface = (Interface)this.ListeInterfacesAvecActeurs.elementAt(i);
      //Connexion.trace("isInListe--------","ListeInterfacesAvecActeurs.idInterface",""+theInterface.idInterface);
      if (theInterface.idInterface == id)
      {
        //Connexion.trace("isInListe--------","dans la ListeInterfacesAvecActeurs",""+id);
        return true;
      }
    }

    //Connexion.trace("isInListe--------","pas dans la Liste",""+id);
    return false;
  }

  public ErrorSpecific bd_EnregInterfaces(String nomBase,Connexion myCnx, Statement st, Statement st2, String transaction, Collaborateur theCollaborateur){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    // Recherche des Interfaces � supprimer
    // Ce sont celles pr�sentes dans actionSuivi et pas dans ListeAction

           req="DELETE FROM Interface WHERE origineInterface ="+this.idSt+" OR extremiteInterface ="+this.idSt;
           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"ErrorSpecific",""+this.idSt);
           if (myError.cause == -1) return myError;
         
          for (int j = 0; j < this.ListeInterfaces.size(); j++) {
            Interface theInterface = (Interface)this.ListeInterfaces. elementAt(j);
            myError=theInterface.bd_Insert(nomBase, myCnx, st, transaction, theCollaborateur);
            if (myError.cause == -1) return myError;
            myError=theInterface.bd_InsertListeOM(nomBase,myCnx, st, transaction);
            if (myError.cause == -1) return myError;
          }
        
     return myError;

  }

  public String bd_UpdateInterfaces(String nomBase,Connexion myCnx, Statement st, String transaction, Vector theListeInter){
    ResultSet rs=null;

      for (int i = 0; i < theListeInter.size(); i++) {
         Interface theInterface = (Interface)theListeInter.elementAt(i);
         String description="";
         if (theInterface.descInterface != null)
         {
           //description = theInterface.descInterface.replaceAll("\r","").replaceAll("0x22","'").replaceAll("\u0092","'").replaceAll("'","''");
           for (int j=0; j < theInterface.descInterface.length();j++)
           {
             int c = (int)theInterface.descInterface.charAt(j);
             //System.out.println("j: "+j+"---"+theInterface.descInterface.charAt(j) + " --" +(int)theInterface.descInterface.charAt(j));
             if (c != 34)
               {
                 description += theInterface.descInterface.charAt(j);
               }
               else
               {
                 description +="'";
               }
           }
           description = description.replaceAll("\r","").replaceAll("'","''");
         }
         //System.out.println("Description="+description);
         req = "UPDATE Interface ";
         req += "    set ";
         req += " origineInterface="+theInterface.StOrigine.idSt;
         req += ",";
         req += " sensInterface='"+theInterface.sensInterface+"'";
         req += ",";
         req += " extremiteInterface="+theInterface.StDestination.idSt;
         req += ",";
         req += " typeInterface='"+theInterface.typeInterface+"'";
         req += ",";
         req += " implemInterface='"+theInterface.idImplementation+"'";
         req += ",";
         req += " frequenceInterface="+theInterface.idFrequence ;
         req += ",";
         req += " descInterface='"+description+"'";


         if (myCnx.ExecUpdate(st, nomBase, req, true, "EnregST") == -1) {
           myCnx.trace(nomBase, "*** ERREUR *** req", req);
           return req;
         }

         this.bd_InsertOM(nomBase,myCnx, st,transaction,theInterface);
       }



     return "OK";

  }

  public String bd_InsertInterfaces(String nomBase,Connexion myCnx, Statement st, String transaction, Vector theListeInter){
    ResultSet rs=null;

       for (int i = 0; i < theListeInter.size(); i++) {
         Interface theInterface = (Interface)theListeInter.elementAt(i);
         String description="";
         if (theInterface.descInterface != null)
         {
           //description = theInterface.descInterface.replaceAll("\r","").replaceAll("0x22","'").replaceAll("\u0092","'").replaceAll("'","''");
           for (int j=0; j < theInterface.descInterface.length();j++)
           {
             int c = (int)theInterface.descInterface.charAt(j);
             //System.out.println("j: "+j+"---"+theInterface.descInterface.charAt(j) + " --" +(int)theInterface.descInterface.charAt(j));
             if (c != 34)
               {
                 description += theInterface.descInterface.charAt(j);
               }
               else
               {
                 description +="'";
               }
           }
           description = description.replaceAll("\r","").replaceAll("'","''");
         }
         //description="";
         //System.out.println("Description="+description);
         req = "EXEC ST_InsererInter '" + theInterface.StOrigine.idSt +
             "', '" + theInterface.sensInterface + "', '" +
             theInterface.StDestination.idSt + "', '" + theInterface.typeInterface +
             "', '" + theInterface.idImplementation + "', '" +
             theInterface.idFrequence + "', '" + description + "'";
         if (myCnx.ExecUpdate(st, nomBase, req, true, "EnregST") == -1) {
           myCnx.trace(nomBase, "*** ERREUR *** req", req);
           return req;
         }
                  theInterface.setId( nomBase, myCnx,  st );
                  this.bd_InsertOM(nomBase,myCnx, st,transaction,theInterface);
       }



     return "OK";

  }

  public String bd_InsertInterfaces2(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;

     for (int typeInter=1; typeInter <= 2; typeInter++)
     {
       Vector theListeInter = null;
       if (typeInter == 1)  theListeInter =this.ListeInterfacesSansActeurs; else theListeInter =this.ListeInterfacesAvecActeurs;
       for (int i = 0; i < theListeInter.size(); i++) {
         Interface theInterface = (Interface)theListeInter.elementAt(i);
         String description="";
         if (theInterface.descInterface != null)
         {
           //description = theInterface.descInterface.replaceAll("\r","").replaceAll("0x22","'").replaceAll("\u0092","'").replaceAll("'","''");
           for (int j=0; j < theInterface.descInterface.length();j++)
           {
             int c = (int)theInterface.descInterface.charAt(j);
             //System.out.println("j: "+j+"---"+theInterface.descInterface.charAt(j) + " --" +(int)theInterface.descInterface.charAt(j));
             if (c != 34)
               {
                 description += theInterface.descInterface.charAt(j);
               }
               else
               {
                 description +="'";
               }
           }
           description = description.replaceAll("\r","").replaceAll("'","''");
         }
         //System.out.println("Description="+description);
         req = "EXEC ST_InsererInter '" + theInterface.StOrigine.idSt +
             "', '" + theInterface.sensInterface + "', '" +
             theInterface.StDestination.idSt + "', '" + theInterface.typeInterface +
             "', '" + theInterface.idImplementation + "', '" +
             theInterface.idFrequence + "', '" + description + "'";
         if (myCnx.ExecUpdate(st, nomBase, req, true, "EnregST") == -1) {
           myCnx.trace(nomBase, "*** ERREUR *** req", req);
           return req;
         }
                  theInterface.setId( nomBase, myCnx,  st );
       }

     }

     for (int typeInter=1; typeInter <= 2; typeInter++)
     {
       Vector theListeInter = null;
       if (typeInter == 1)  theListeInter =this.ListeInterfacesSansActeurs; else theListeInter =this.ListeInterfacesAvecActeurs;
       for (int i = 0; i < theListeInter.size(); i++) {
         Interface theInterface = (Interface)theListeInter.elementAt(i);

         this.bd_InsertOM(nomBase,myCnx, st,transaction,theInterface);
         }

       }

     return "OK";

  }



  public ErrorSpecific bd_InsertDocs(String nomBase,Connexion myCnx, Statement st, String transaction){
     ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
    req =  "DELETE FROM Documentation WHERE idDocVersionSt = "+this.idVersionSt;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertDocs",""+this.idVersionSt);
    if (myError.cause == -1) return myError;

     for (int i=0; i < this.ListeDocs.size(); i++)
     {
       doc thedoc = (doc)this.ListeDocs.elementAt(i);
       String str = "";
       if (thedoc.commentaire != null)
           str = thedoc.commentaire.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
       req = "INSERT Documentation (nom,  chemin,commentaire, idDocVersionSt,idDocType,extension) VALUES ('"+thedoc.nom+"',  '"+thedoc.chemin+"',  '"+str+"', '"+this.idVersionSt+"'"+", '"+thedoc.idType+"', '"+thedoc.extension+"')";
       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertDocs",""+this.idVersionSt);
       if (myError.cause == -1) return myError;
     }

     return myError;

  }
  
  public int bd_InsertGraph(String nomBase,Connexion myCnx, Statement st, String transaction){
     ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
    int idGraph= -1;
    //req =  "SELECT      idGraph, centreGraph FROM  Graph WHERE     (centreGraph = "+this.idSt+")";
    req =  "SELECT      idGraph, centreGraph FROM  Graph WHERE     (centreGraph = "+this.idVersionSt+")";
    rs = myCnx.ExecReq(st, nomBase, req);
    try { 
        rs.next();
        idGraph =rs.getInt(1); 
    } catch (Exception s) {s.getMessage();}
    
    if (idGraph <= 0)
    {
       req = "INSERT Graph (centreGraph) VALUES ('"+this.idVersionSt+"')";
       myCnx.ExecReq(st, nomBase, req); 
    }

    req =  "SELECT      idGraph, centreGraph FROM  Graph WHERE     (centreGraph = "+this.idVersionSt+")";
    rs = myCnx.ExecReq(st, nomBase, req);
    try { 
        rs.next();
        idGraph =rs.getInt(1); 
    } catch (Exception s) {s.getMessage();}    

     return idGraph;

  }  

  public void addListeRegions(int idRegion, String Utilisation){
    Region theRegion = new Region(idRegion,Utilisation,"",0);
    this.ListeRegions.addElement(theRegion);
  }

  public void addListeDocs(int id,String chemin,String commentaire,String cheminIcone,String nom,String Type,String idType)
{
    doc thedoc = new doc(id,chemin,commentaire,cheminIcone,nom,Type,Integer.parseInt(idType));
    this.ListeDocs.addElement(thedoc);
  }

  public void addListeModules(
       int id,
       String nom,
       String descModule,
       String Ihm,
       String InterfaceSynchrone,
       String InterfaceAsynchrone,
       String idTypeModule
)
{
    Module theModule = new Module(id,nom,descModule,Ihm,InterfaceSynchrone,InterfaceAsynchrone);
    try{
    theModule.idTypeModule = Integer.parseInt(idTypeModule);
    }
    catch (Exception e){theModule.idTypeModule = -1;}

    this.ListeModules.addElement(theModule);
  }

  public void addListeMw(String nbLicences,String LogicielMiddleWare,String Archi)
  {
    try
    {
      Logiciel theLogiciel = new Logiciel(-1, "", -1, "", Integer.parseInt(nbLicences), Integer.parseInt(LogicielMiddleWare),"",Archi);

      this.ListeLogiciels.addElement(theLogiciel);
    }
    catch (Exception e){}
  }

  public void addListeInterfaces(String nomBase,Connexion myCnx, Statement st,String StSource,String StDest,String sensInter, String TypeInterf, String TypeImpl, String Freq, String descInter, String theObj, String FlagActeur, int idInterface)
  {
    ResultSet rs=null;
    int origineInter = -1;
    int extremiteInter = -1;
    int implemInter=-1;
    int frequenceInter=-1;

    ST StOrigine=null;
    ST StDestination=null;

    if (!StDest.equals(this.old_nomSt))
    {
      origineInter = this.idVersionSt; // ST Origine
      rs = myCnx.ExecReq(st, nomBase, "SELECT idSt FROM  St WHERE (nomSt = '"+StDest+"')");
      try { rs.next();extremiteInter =rs.getInt(1); } catch (SQLException s) {s.getMessage();}// ST Dest
      StOrigine = this;
      StDestination = new ST(extremiteInter,"idSt");
    }
    else
    {
      rs = myCnx.ExecReq(st, nomBase,"exec ST_SelectLastVersionStByNomSt '"+StSource+"'");
      try { rs.next();origineInter =rs.getInt(1); } catch (SQLException s) {s.getMessage();}// ST Source
      extremiteInter = this.idSt; // ST Origine
      StOrigine = new ST(origineInter,"idVersionSt");
      StDestination = this;
    }

    rs = myCnx.ExecReq(st, nomBase, "SELECT     idImplementation FROM    Implementation WHERE (nomImplementation ='"+TypeImpl+"')");
    try { rs.next();implemInter =rs.getInt(1); } catch (SQLException s) {s.getMessage();}

    rs = myCnx.ExecReq(st, nomBase, "SELECT     idFrequence FROM Frequence WHERE (nomFrequence ='"+Freq+"')");
    try { rs.next();frequenceInter =rs.getInt(1); } catch (SQLException s) {s.getMessage();}

      Interface theInterface = new Interface(
          idInterface,
          StOrigine,
          StDestination,
          sensInter,
          TypeInterf,
          TypeImpl,
          Freq,
          descInter,
          implemInter,
          frequenceInter
          );
      //theInterface.dump();
      OM theOM = theInterface.addListeOM( nomBase, myCnx,  st,theObj);
      System.out.println("********" + theInterface.ListeOM.size());

      if (FlagActeur.equals("")) this.ListeInterfacesSansActeurs.addElement(theInterface); else this.ListeInterfacesAvecActeurs.addElement(theInterface);

      this.ListeInterfaces.addElement(theInterface);

  }

  public void addListeInterfaces2(String nomBase,Connexion myCnx, Statement st,String StSource,String StDest,String sensInter, String TypeInterf, String TypeImpl, String Freq, String descInter, String theObj, String FlagActeur)
  {
    ResultSet rs=null;
    int origineInter = -1;
    int extremiteInter = -1;
    int implemInter=-1;
    int frequenceInter=-1;

    ST StOrigine=null;
    ST StDestination=null;

    if (!StDest.equals(this.old_nomSt))
    {
      origineInter = this.idVersionSt; // ST Origine
      rs = myCnx.ExecReq(st, nomBase, "SELECT idSt FROM  St WHERE (nomSt = '"+StDest+"')");
      try { rs.next();extremiteInter =rs.getInt(1); } catch (SQLException s) {s.getMessage();}// ST Dest
      StOrigine = this;
      StDestination = new ST(extremiteInter,"idSt");
    }
    else
    {
      rs = myCnx.ExecReq(st, nomBase,"exec ST_SelectLastVersionStByNomSt '"+StSource+"'");
      try { rs.next();origineInter =rs.getInt(1); } catch (SQLException s) {s.getMessage();}// ST Source
      extremiteInter = this.idSt; // ST Origine
      StOrigine = new ST(origineInter,"idVersionSt");
      StDestination = this;
    }

    rs = myCnx.ExecReq(st, nomBase, "SELECT     idImplementation FROM    Implementation WHERE (nomImplementation ='"+TypeImpl+"')");
    try { rs.next();implemInter =rs.getInt(1); } catch (SQLException s) {s.getMessage();}

    rs = myCnx.ExecReq(st, nomBase, "SELECT     idFrequence FROM Frequence WHERE (nomFrequence ='"+Freq+"')");
    try { rs.next();frequenceInter =rs.getInt(1); } catch (SQLException s) {s.getMessage();}

      Interface theInterface = new Interface(
          -1,
          StOrigine,
          StDestination,
          sensInter,
          TypeInterf,
          TypeImpl,
          Freq,
          descInter,
          implemInter,
          frequenceInter
          );
      //theInterface.dump();
      OM theOM = theInterface.addListeOM( nomBase, myCnx,  st,theObj);
      System.out.println("********" + theInterface.ListeOM.size());

      if (FlagActeur.equals("")) this.ListeInterfacesSansActeurs.addElement(theInterface); else this.ListeInterfacesAvecActeurs.addElement(theInterface);

      this.ListeInterfaces.addElement(theInterface);

  }

  public int getNumRoadmap(String nomBase,Connexion myCnx, Statement st, String Login ){
    ResultSet rs;
    int num = 0;
    String theNom="";

      req = "SELECT * FROM (SELECT VersionSt.idVersionSt, St.nomSt FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoaVersionSt = Membre.idMembre WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (Membre.LoginMembre = '"+Login+"') UNION SELECT ListeST.idVersionSt, ListeST.nomSt FROM Membre INNER JOIN EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ListeST ON Equipe.nom = ListeST.nomSt WHERE (Membre.LoginMembre = '"+Login+"')  AND  (Membre.isProjet = 1) )  AS maTable ORDER BY nomSt";
      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      try {
        while (rs.next()) {
          theNom = rs.getString("nomSt");
          if (theNom.equals(this.nomSt)) return num;
          num++;
        }
      }
      catch (SQLException s) {
        s.getMessage();
      }


    return num;
  }

  public void getListeRegions(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    req="SELECT  idRegion, nomRegion, descRegion, Ordre FROM    Region ORDER BY Ordre";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    int idRegion;
    String nomRegion="";
    String descRegion="";
    int Ordre;

    try {
      while (rs.next()) {
        idRegion = rs.getInt("idRegion");
        nomRegion = rs.getString("nomRegion");
        descRegion = rs.getString("descRegion");
        Ordre = rs.getInt("Ordre");
        Region theRegion = new Region(idRegion,nomRegion,descRegion,Ordre);
        this.ListeRegions.addElement(theRegion);
      }
    }
            catch (SQLException s) {s.getMessage();}
    int nb=0;
    for (int i=0; i < this.ListeRegions.size(); i++){
      Region theRegion = (Region)this.ListeRegions.elementAt(i);
      req = "SELECT     COUNT(*) AS nb FROM  StRegion WHERE     (idST = "+this.idSt+") AND (idRegion = "+theRegion.id+")";
      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      try {
        while (rs.next()) {
          nb=rs.getInt("nb");

        }
      }
            catch (SQLException s) {s.getMessage();}
        if (nb >0) theRegion.selected = "checked"; else theRegion.selected = "";
    }

  }

  public String existRegion(String myRegion){
    for (int i=0; i < this.ListeRegions.size();i++){
      String theRegion = (String)this.ListeRegions.elementAt(i);
      if (myRegion.equals(theRegion)) return "checked";
    }
    return "";
  }
  public void getRegion(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    req="exec ST_selectStRegion "+this.idSt;
    rs = myCnx.ExecReq(st, nomBase, req);
    try {
     while (rs.next())
      {
        String theRegion = rs.getString(1);
        this.Region+=theRegion+",";
      }
      }
      catch (SQLException s) {s.getMessage();}

if (!this.Region.equals(""))this.Region = this.Region.substring(0,this.Region.length()-1);

}



  public Roadmap addOneRoadmap( String nomBase,Connexion myCnx, Statement st , String idRoadmap){
    Roadmap roadmap = new Roadmap( Integer.parseInt(idRoadmap));
    roadmap.getAllFromBd(nomBase, myCnx, st);

    return roadmap;
  }

  public Vector addRoadmap(
                   String nomBase,
                   Connexion myCnx,
                   Statement st ,
                   String idVersionSt){
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
    String SuiviMOA="";
    String SuiviMOE="";
    String Tendance ="";
    String docEB="";
    String docDevis="";


    ResultSet rs;
    req = "SELECT   version, description, dateEB, dateMep,idVersionSt, Suivi, Tendance, dateMes, Etat, Panier, idPO, Charge, Ordre, dateT0, dateTest, docEB, docDevis, Annee, MotsClef, suiviMOE FROM  Roadmap WHERE  idVersionSt = "+idVersionSt+" ORDER BY dateMep DESC";
    rs = myCnx.ExecReq(st, nomBase, req);
    int nbRoadmap = 0;
    java.util.Date theDate;
    java.util.Date DdateEB;
    java.util.Date DdateMep;
    java.util.Date DdateMes;
    java.util.Date DdateT0;
    java.util.Date DdateTest;
    try {
      while (rs.next()) {

        version = rs.getString(1);
        oldversion = version;
        description = rs.getString(2);

        DdateEB = rs.getDate(3);
        if (DdateEB != null)
          dateEB = ""+DdateEB.getDate()+"/"+(DdateEB.getMonth()+1) + "/"+(DdateEB.getYear() + 1900);
        else
          dateEB = "";

        DdateMep = rs.getDate(4);
        if (DdateMep != null)
          dateMep = ""+DdateMep.getDate()+"/"+(DdateMep.getMonth()+1) + "/"+(DdateMep.getYear() + 1900);
         else
          dateMep = "";

        //SuiviMOA = rs.getString("Suivi");
        if (SuiviMOA == null)  SuiviMOA = "";

        Tendance = rs.getString("Tendance");
        if (Tendance == null)  Tendance = "";


        DdateMes = rs.getDate("dateMes");
        if (DdateMes != null)
            dateMes =""+DdateMes.getDate()+"/"+(DdateMes.getMonth()+1) + "/"+(DdateMes.getYear() + 1900);
          else
            dateMes = "";

        Etat = rs.getString("Etat");

        Panier = rs.getString("Panier");
        idPO = rs.getString("idPO");
        if ( (idPO == null) || (idPO.equals("0")))
          idPO = "";
        //Charge = rs.getString("Charge");
        //if ( (Charge == null) || (Charge.equals("0")))
          Charge = "";
        //tabRoadmap[nbRoadmap][11] = rs.getString("Ordre");
        Ordre = rs.getString("Ordre");

        DdateT0 = rs.getDate("dateT0");
        if (DdateT0 != null)
          dateT0 = ""+DdateT0.getDate()+"/"+(DdateT0.getMonth()+1) + "/"+(DdateT0.getYear() + 1900);
         else
          dateT0 = "";

        DdateTest = rs.getDate("dateTest");
        if (DdateTest != null)
          dateTest = ""+DdateTest.getDate()+"/"+(DdateTest.getMonth()+1) + "/"+(DdateTest.getYear() + 1900);
         else
          dateTest = "";

        docEB=rs.getString("docEB");
        if (docEB == null) docEB="";

        docDevis=rs.getString("docDevis");
        if (docDevis == null) docDevis="";

        int Annee = rs.getInt("Annee");

        String MotsClef = rs.getString("MotsClef");

        //SuiviMOE = rs.getString("SuiviMOE");
        if (SuiviMOE == null)  SuiviMOE = "";

        Roadmap roadmap = new Roadmap(
            this.nomSt,
            idVersionSt,
            oldversion,
            version,
            description,
            Etat,
            Panier,
            idPO,
            Charge,
            Ordre,
            dateEB,
            dateMep,
            dateMes,
            SuiviMOA,
            Tendance,
            dateT0,
            dateTest,
            docEB,
            docDevis,
            Annee,
            MotsClef
            );

        roadmap.DdateT0 = DdateT0;
        roadmap.DdateEB = DdateEB;
        roadmap.DdateTest= DdateTest;
        roadmap.DdateProd = DdateMep;
        roadmap.DdateMes = DdateMes;

        roadmap.SuiviMOE = SuiviMOE;

    this.ListeRoadmap.addElement(roadmap);
        nbRoadmap++;
      }
    }

        catch (SQLException s) {
          myCnx.trace("***********", "nbRoadmap", "" + nbRoadmap);
          s.getMessage();
        }



    return this.ListeRoadmap;
  }
  public VersionSt addVersion(int idVersionSt){
    VersionSt theVersionSt = new VersionSt(this.idSt,idVersionSt);
    this.ListeVersions.addElement(theVersionSt);
    return theVersionSt;
  }

  public static void getListeVersionSt(String nomBase,Connexion myCnx, Statement st){

    String req="EXEC LISTEST_SelectVersionSt";
    ListeSt.removeAllElements();

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    ST theST = null;
    try {
       while (rs.next()) {
         theST = new ST(rs.getInt(1), "idVersionSt");
         theST.nomSt = rs.getString(2);
         //theST.dump();
         ListeSt.addElement(theST);
       }
        } catch (SQLException s) {s.getMessage();}

 }



    public  void getXYonMap(String nomBase,Connexion myCnx, Statement st){

      String req="SELECT     St.idSt, St.nomSt, GrapMacroSt.x, GrapMacroSt.y, MacroSt.nomMacrost, MacroSt.offset_X, MacroSt.offset_Y, MacroSt.map_X, MacroSt.map_Y";
      req+=" FROM         St INNER JOIN";
      req+="                 GrapMacroSt ON St.idSt = GrapMacroSt.idSt INNER JOIN";
      req+="                 VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
      req+="                 MacroSt ON VersionSt.macrostVersionSt = MacroSt.idMacrost AND GrapMacroSt.idMacroSt = MacroSt.idMacrost";
      req+=" WHERE     (VersionSt.idVersionSt = "+this.idVersionSt+")";

     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     try {
        while (rs.next()) {
          int idSt = rs.getInt("idSt");
          String nomSt = rs.getString("nomSt");
          int x = rs.getInt("x");
          int y = rs.getInt("y");
          String nomMacrost = rs.getString("nomMacrost");
          int offset_X = rs.getInt("offset_X");
          int offset_Y = rs.getInt("offset_Y");
          int map_X = rs.getInt("map_X");
          int map_Y = rs.getInt("map_Y");

          this.nomSt = nomSt;
          this.x = x+offset_X;
          this.y = y+offset_Y;
          this.map_x = map_X;
          this.map_y = map_Y;
          this.nomMacrost = nomMacrost;



        }
         } catch (SQLException s) {s.getMessage();}


}

    public static void getListeStWithXY(String nomBase,Connexion myCnx, Statement st){

      String req="SELECT     St.idSt, St.nomSt, GrapMacroSt.x, GrapMacroSt.y, MacroSt.nomMacrost, MacroSt.offset_X, MacroSt.offset_Y, MacroSt.map_X, MacroSt.map_Y";
      req+=" FROM         St INNER JOIN";
      req+="                 GrapMacroSt ON St.idSt = GrapMacroSt.idSt INNER JOIN";
      req+="                 VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
      req+="                 MacroSt ON VersionSt.macrostVersionSt = MacroSt.idMacrost AND GrapMacroSt.idMacroSt = MacroSt.idMacrost";


     ListeSt.removeAllElements();

     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     ST theST = null;
     try {
        while (rs.next()) {
          int idSt = rs.getInt("idSt");
          String nomSt = rs.getString("nomSt");
          int x = rs.getInt("x");
          int y = rs.getInt("y");
          String nomMacrost = rs.getString("nomMacrost");
          int offset_X = rs.getInt("offset_X");
          int offset_Y = rs.getInt("offset_Y");
          int map_X = rs.getInt("map_X");
          int map_Y = rs.getInt("map_Y");
          theST = new ST(idSt,"idSt");
          theST.nomSt = nomSt;
          theST.x = x+offset_X;
          theST.y = y+offset_Y;
          theST.map_x = map_X;
          theST.map_y = map_Y;
          theST.nomMacrost = nomMacrost;

          //theST.dump();
          ListeSt.addElement(theST);
        }
         } catch (SQLException s) {s.getMessage();}

}

       public static void getListeStWithInterface(String nomBase,Connexion myCnx, Statement st){

         String req="SELECT     idSt, nomSt";
         req+="    FROM         ListeST";
         req+="     WHERE     (etatFicheVersionSt = 3) AND (etatVersionSt <> 4)  ";
         req+="     and idSt in (SELECT     origineInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M') UNION SELECT extremiteInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M'))";
         req+="     ORDER BY nomSt  ";

        ListeSt.removeAllElements();

        ResultSet rs = myCnx.ExecReq(st, nomBase, req);
        ST theST = null;
        try {
           while (rs.next()) {
             int idSt = rs.getInt(1);
             String nomSt = rs.getString(2);
             theST = new ST(idSt,"idSt");
             theST.nomSt = nomSt;

             //theST.dump();
             ListeSt.addElement(theST);
           }
            } catch (SQLException s) {s.getMessage();}

}

public static void getListeStWithInterfaceValidee(String nomBase,Connexion myCnx, Statement st){

  String req="         SELECT     idSt, nomSt";
         req+=" FROM         ListeST";
         req+=" WHERE     (etatFicheVersionSt = 3) AND (etatVersionSt <> 4)  ";
         req+=" and idSt in (";
         req+=" SELECT    distinct origineInterface";
         req+="              FROM         Interface";
         req+="              WHERE     (typeInterface = 'S' OR";
         req+="    typeInterface = 'A' OR";
         req+="    typeInterface = 'M') AND (dumpHtml IS NOT NULL) AND (dumpHtml NOT LIKE '') ";

         req+=" UNION ";

         req+=" SELECT   distinct  extremiteInterface";
         req+="              FROM         Interface";
         req+="              WHERE     (typeInterface = 'S' OR";
        req+="     typeInterface = 'A' OR";
        req+="     typeInterface = 'M') AND (dumpHtml IS NOT NULL) AND (dumpHtml NOT LIKE '') ";
        req+=" )";
        req+=" ORDER BY nomSt  ";


           ListeSt.removeAllElements();

           ResultSet rs = myCnx.ExecReq(st, nomBase, req);
           ST theST = null;
           try {
              while (rs.next()) {
                int idSt = rs.getInt(1);
                String nomSt = rs.getString(2);
                theST = new ST(idSt,"idSt");
                theST.nomSt = nomSt;

                //theST.dump();
                ListeSt.addElement(theST);
              }
               } catch (SQLException s) {s.getMessage();}

}

 public static void getListeSousTypeStByTypeSt(String nomBase,Connexion myCnx, Statement st, String isST, String isAppli, String isEquipement, String isComposant, String isActeur){

               String ClauseWhere="";
               if (isEquipement.equals("1"))
               {
                 if (ClauseWhere.equals(""))
                   ClauseWhere += " WHERE ";
                 else
                   ClauseWhere += " OR ";

                 ClauseWhere += " (isEquipement = 1) ";
               }

               if (isComposant.equals("1"))
               {
                 if (ClauseWhere.equals(""))
                   ClauseWhere += " WHERE ";
                 else
                   ClauseWhere += " OR ";
                 ClauseWhere += " (isComposant = 1) ";
               }

               if (isST.equals("1"))
               {
                 if (ClauseWhere.equals(""))
                   ClauseWhere += " WHERE ";
                 else
                   ClauseWhere += " OR ";
                 ClauseWhere += " (isSt = 1) ";
               }

               if (isAppli.equals("1"))
               {
                 if (ClauseWhere.equals(""))
                   ClauseWhere += " WHERE ";
                 else
                   ClauseWhere += " OR ";
                 ClauseWhere += " (isAppli = 1) ";
               }

               if (isActeur.equals("1"))
               {
                 if (ClauseWhere.equals(""))
                   ClauseWhere += " WHERE ";
                 else
                   ClauseWhere += " OR ";
                 ClauseWhere += " (isActeur = 1) ";
               }


               String req="SELECT     idTypeAppli, nomTypeAppli";
               req+=  "  FROM         TypeAppli";
               req+=ClauseWhere;
               req+=" ORDER BY nomTypeAppli";

                ST.ListeSousTypeSt.removeAllElements();

                ResultSet rs = myCnx.ExecReq(st, nomBase, req);
                item theItem = null;
                try {
                   while (rs.next()) {
                     int idTypeAppli = rs.getInt("idTypeAppli");
                     String nomTypeAppli = rs.getString("nomTypeAppli");

                     theItem = new item ();
                     theItem.id = idTypeAppli;
                     theItem.nom = nomTypeAppli;

                     ST.ListeSousTypeSt.addElement(theItem);
                   }
                    } catch (SQLException s) {s.getMessage();}

}

    public static void getListeSt(String nomBase,Connexion myCnx, Statement st){

      String req="SELECT idSt, nomSt, trigrammeSt, diagramme, idVersionSt FROM ListeST order by nomSt";
     ListeSt.removeAllElements();

     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     ST theST = null;
     try {
        while (rs.next()) {
          int idSt = rs.getInt(1);
          String nomSt = rs.getString(2);
          String trigrammeSt = rs.getString(3);
          String diagramme = rs.getString(4);
          theST = new ST(idSt,"idSt");
          theST.nomSt = nomSt;
          theST.trigrammeSt = trigrammeSt;
          theST.diagramme = diagramme;
          theST.idVersionSt = rs.getInt("idVersionSt");

          //theST.dump();
          ListeSt.addElement(theST);
        }
         } catch (SQLException s) {s.getMessage();}

}

    public  static void getListeExcel(String nomBase,Connexion myCnx, Statement st, Statement st2 ){
      String req="";
      ResultSet rs;
      ResultSet rs2;

      ListeExcel.removeAllElements();

      String nom="";
      String trig="";
      String TypeAppli="";
      String MacroSt="";
      String nomSI="";
      String nomEtat="";
      String nomTypeSi="";
      String versionRefVersionSt="";
      String STRokMoaVersionSt="";
      String MOA="";
      String respMOA="";
      String respMOE="";
      String Prod="";
      String MOE="";

      int nbLigCodeVersionSt=-1;
      int nbPtsFctVersionSt=-1;
      int nbUtilVersionSt=-1;
      String mep1VersionSt="";
      String mepVersionSt="";
      String killVersionSt="";
      String iReagirVersionSt="";
      String externalVersionSt="";
      String nomPhaseNP="";
      String nomSousPhaseNP="";
      String nomFrequence="";
      String derMajVersionSt="";
      String creationVersionSt="";

      String Description="";
      String Region="";
      int idSt;

      String Criticite;
      int isST;
       int isAppli;

       String ModeAcq;
       String serviceExpl;
       String nomTypeClient="";

       int idVersionSt=-1;
       String Logo="";

      req = "exec EXPORT_excelSt";
      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      try {while(rs.next())
      {

        nom = rs.getString(1);
        trig= rs.getString(2);
        TypeAppli= rs.getString(3);
        MacroSt= rs.getString(4);
        nomSI= rs.getString(5);
        nomEtat= rs.getString(6);
        nomTypeSi= rs.getString(7);
        versionRefVersionSt= rs.getString(8);
        STRokMoaVersionSt= rs.getString(9);
        MOA= rs.getString(10);
        respMOA= rs.getString(11);
        Prod= rs.getString(12);
        MOE= rs.getString(13);

       nbLigCodeVersionSt=rs.getInt("nbLigCodeVersionSt");
       nbPtsFctVersionSt=rs.getInt("nbPtsFctVersionSt");
       nbUtilVersionSt=rs.getInt("nbUtilVersionSt");
       mep1VersionSt=rs.getString("mep1VersionSt");
       mepVersionSt=rs.getString("mepVersionSt");
       killVersionSt=rs.getString("killVersionSt");
       externalVersionSt=rs.getString("MultiCanal");
       nomPhaseNP=rs.getString("nomPhaseNP");
       nomSousPhaseNP=rs.getString("nomSousPhaseNP");
       nomFrequence=rs.getString("nomFrequence");
       derMajVersionSt=rs.getString("derMajVersionSt");
       creationVersionSt=rs.getString("creationVersionSt");
       Description=rs.getString("Description");
       Description = Description.replaceAll("\\r","");
       Description = Description.replaceAll("\\n","");
       respMOE= rs.getString("respMOE");
       idSt= rs.getInt("idSt");


       Criticite= rs.getString("Criticite");
        isST=rs.getInt("isST");
        isAppli=rs.getInt("isAppli");

        String nomMaturite= rs.getString("nomMaturite");
        if (nomMaturite == null) nomMaturite="";
        serviceExpl= rs.getString("serviceExpl");
        nomTypeClient =  rs.getString("typeClient");

        idVersionSt= rs.getInt("idVersionSt");
        Logo =  rs.getString("Logo");

      Region="";
      /*
      req="exec ST_selectStRegion "+idSt; rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);
      while(rs2.next())
       {
         try {
                Region+=rs2.getString(1)+",";
             }

             catch (SQLException s) {s.getMessage();}

        }
      */
      if (!Region.equals(""))Region = Region.substring(0,Region.length()-1);

        ST theST = new ST(nom);
        theST.Region = Region;
        theST.trigrammeSt= trig;
        theST.TypeApplication=TypeAppli;
        theST.nomMacrost = MacroSt;
        theST.nomSi = nomSI;
        theST.nomEtat = nomEtat;
        theST.TypeSi = nomTypeSi;
        theST.versionRefVersionSt = versionRefVersionSt;
        theST.STRokMoaVersionSt = STRokMoaVersionSt;
        theST.nomServiceMOA = MOA;
        theST.nomMOA = respMOA;
        theST.nomServiceMOE = MOE;
        theST.nomMOE= respMOE;
        theST.nomServicePROD= Prod;
        theST.nbLigCodeVersionSt= nbLigCodeVersionSt;
        theST.nbPtsFctVersionSt= nbPtsFctVersionSt;
        theST.nbUtilVersionSt= nbUtilVersionSt;
        theST.dateMep1= mep1VersionSt ;
        theST.dateMep= mepVersionSt ;
        theST.dateKill= killVersionSt;
        theST.Externalisation= externalVersionSt;
        theST.nomPhaseNP= nomPhaseNP;
        theST.nomSousPhaseNP= nomSousPhaseNP;
        theST.nomFrequence= nomFrequence;
        theST.str_derMajVersionSt= derMajVersionSt;
        theST.str_creationVersionSt=creationVersionSt ;
        theST.descVersionSt= Description;
        theST.Criticite= Criticite;
        theST.isST = isST;
        theST.isAppli = isAppli;
        theST.nomMaturite = nomMaturite;
        theST.serviceExpl = serviceExpl;
        theST.nomTypeClient = nomTypeClient;
        theST.idVersionSt = idVersionSt;
        theST.idSt = idSt;
        theST.logo = Logo;

        if (theST.nomSi.equals("SI Equipements"))
          theST.niveauCharge =  rs.getString("niveauCharge");
        else
          theST.niveauCharge = "";

        ListeExcel.addElement(theST);

      }
} catch (SQLException s) {s.getMessage();}
}

    public static void getListeRatioPf(String nomBase,Connexion myCnx, Statement st){

     String req="EXEC UTIL_RatioPf";
     RatioPf.removeAllElements();

     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     Logiciel theLogiciel = null;
     try {
        while (rs.next()) {
          int idMiddleware = rs.getInt(1);
          String nomMiddleware = rs.getString(2);
          int Ratio = rs.getInt(3);

          theLogiciel = new Logiciel(idMiddleware,Ratio,nomMiddleware);
          //theST.dump();
          RatioPf.addElement(theLogiciel);
        }
         } catch (SQLException s) {s.getMessage();}

   }


  public String selectPrevious(String nomBase,Connexion myCnx, Statement st, int idVersionSt){
    ResultSet rs;
    req = "exec ST_SelectPreviousVersionSt "+this.idSt+"," + idVersionSt;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        int Previousid= rs.getInt(1);
        System.out.println("Previousid="+Previousid );
        return ""+Previousid;
        }


    } catch (SQLException s) {s.getMessage();}

    System.out.println("Previousid="+"NULL" );
    return "NULL";
  }

  public String selectNext(String nomBase,Connexion myCnx, Statement st, int idVersionSt){
    ResultSet rs;
    req = "exec ST_SelectNextVersionSt "+this.idSt+"," + idVersionSt;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        int nextid= rs.getInt(1);
        System.out.println("Nextid="+nextid );
        return ""+nextid;
        }


    } catch (SQLException s) {s.getMessage();}

    System.out.println("Nextid="+"NULL" );
    return "NULL";
  }

  public void setNiveauCharge(){
    if (this.TypeApplication.equals("Serveur"))
    {
      if (this.niveauCharge == null)
      {
        this.NodeSt.couleur = "orange";
      }
      else if (this.niveauCharge.equals("vide"))
      {
        this.NodeSt.couleur = "vert";
      }
      else if (this.niveauCharge.equals("normal"))
      {
        this.NodeSt.couleur = "vert";
      }
      else if (this.niveauCharge.equals("charg�"))
      {
        this.NodeSt.couleur = "orange";
      }
      else if (this.niveauCharge.equals("satur�"))
      {
        this.NodeSt.couleur = "rouge";
      }
      else
      {
        this.NodeSt.couleur = "orange";
      }
      }
  }


  public void MajPointeurPreviousNext(String nomBase,Connexion myCnx, Statement st, Statement st2){
    ResultSet rs;
    ResultSet rs2;
    req = "exec ST_MajPointeurVersionStInValide " + this.idSt;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    req = "exec ST_SelectVersionStValide " + this.idSt;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);


    try {
      while (rs.next()) {
        int idVersionSt= rs.getInt(2);
        String previous = this.selectPrevious(nomBase,myCnx, st2,idVersionSt);
        String Next = this.selectNext(nomBase,myCnx, st2,idVersionSt);
        System.out.println("Valide::idVersionSt="+idVersionSt+"....previous="+previous+"....next="+Next);
        req = "UPDATE VersionSt SET precVersionSt ="+ previous + ", suivVersionSt ="+ Next +" WHERE idVersionSt ="+ idVersionSt;
        rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);

        }
    } catch (SQLException s) {s.getMessage();}

  }

  public void setState(int idVersionSt,int state,String nomBase,Connexion myCnx, Statement st, Statement st2){
    req = "UPDATE VersionSt SET etatFicheVersionSt ="+ state +" WHERE idVersionSt ="+ idVersionSt;
    ResultSet rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);
  }

  public void validerVersion(int idVersionSt,String nomBase,Connexion myCnx, Statement st, Statement st2){
    this.setState(idVersionSt,VersionSt.etat_valide,nomBase,myCnx, st, st2);
    this.MajPointeurPreviousNext(nomBase,myCnx, st, st2);
  }

  public void rejeterVersion(int idVersionSt,String nomBase,Connexion myCnx, Statement st, Statement st2){
    this.setState(idVersionSt,ST.etat_notifie,nomBase,myCnx, st, st2);
    this.MajPointeurPreviousNext(nomBase,myCnx, st, st2);
  }

  public void retablirVersion(int idVersionSt,String nomBase,Connexion myCnx, Statement st, Statement st2){
    this.setState(idVersionSt,ST.etat_notifie,nomBase,myCnx, st, st2);
    this.MajPointeurPreviousNext(nomBase,myCnx, st, st2);
  }

  public void supprimerVersion(int idVersionSt,String nomBase,Connexion myCnx, Statement st, Statement st2){
    this.setState(idVersionSt,ST.etat_supprime,nomBase,myCnx, st, st2);
    this.MajPointeurPreviousNext(nomBase,myCnx, st, st2);
  }



  public ErrorSpecific supprimerDefinitivementVersion(int idVersionSt,String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    int nb = 0;
    
    req = "delete FROM  Interface WHERE     (origineInterface = "+this.idSt+") OR (extremiteInterface = "+this.idSt+")";
     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"supprimerDefinitivementVersion",""+this.idVersionSt);
     if (myError.cause == -1) return myError;    
    
    req = "DELETE VersionSt  WHERE idVersionSt ="+ idVersionSt;
    ResultSet rs2 = myCnx.ExecReq(st, myCnx.nomBase, req);

     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"supprimerDefinitivementVersion",""+this.idVersionSt);
     if (myError.cause == -1) return myError;
   
    if (myError.cause == -1) return myError;

       req = "delete ST WHERE idSt= " + this.idSt;
       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"supprimerDefinitivementVersion",""+this.idVersionSt);
       if (myError.cause == -1) return myError;
    /**@todo � supprimer les prints*/

    return myError;
  }

  public static void main3(String[] args) {
    Connexion myCnx = null;
    Statement st, st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    ST theSt = new ST(1957, "idVersionSt");
    theSt.getAllFromBd(myCnx.nomBase, myCnx, st);
    //theSt.dump();
    theSt.NodeSt = new Node(theSt.nomSt);
    String nodes = "ADA*345*140$Approvisionneur*434*74$Gateway_ECO*230*268$OCRES*462*278";

    transaction theTransaction = new transaction ("EnregGraphe");

    theTransaction.begin(base,myCnx,st);
    theSt.getGraph(myCnx.nomBase,myCnx,st, theTransaction);
    theSt.bd_enregGraph(myCnx.nomBase,myCnx,st, theTransaction,  nodes);

    theTransaction.commit(base,myCnx,st);
    //theSt.getNoteMaj();
    //theSt.addOneRoadmap(myCnx.nomBase, myCnx, st, "18778");
    myCnx.Unconnect(st);
  }

  public static void main5(String[] args) {
    Connexion myCnx=null;
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    st2 = myCnx.Connect();



  req = "SELECT     idVersionSt FROM  ST_delete"; rs = myCnx.ExecReq(st,base,req);

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
    while (rs.next()) {
       ST theSt = new ST( rs.getInt(1),"idVersionSt");
       ListeSt.addElement(theSt);
      //theSt.dump();
      }


  } catch (SQLException s) {s.getMessage();}


  for (int i=0; i < ListeSt.size();i++)
  //for (int i=0; i < 1;i++)
  {
    ST theSt = (ST)ST.ListeSt.elementAt(i);
    theSt.getAllFromBd(myCnx.nomBase,myCnx,st);
    //theSt.dump();
    //theSt.supprimerDefinitivementVersion(theSt.idVersionSt,myCnx.nomBase,myCnx,st, st2);
    }

    myCnx.Unconnect(st);
  }

  public static void main(String[] args) {
    Connexion myCnx=null;
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    ST theSt = new ST( 2457,"idVersionSt");
    theSt.getAllFromBd(myCnx.nomBase,myCnx,st);
    //theSt.bd_Update(myCnx.nomBase,myCnx,st,"");
    //theSt.dump();
    //theSt.startExport(myCnx.nomBase,myCnx,st);
    theSt.bd_InsertGraph(base, myCnx, st, "");
    //int nb = theSt.getNbInterfaceWithMacroSt(myCnx.nomBase,myCnx,st,69);
    //theSt.getXYonMap(myCnx.nomBase,myCnx,st);
    //theSt.dump();
    //theSt.getNoteAvancement();
    //theSt.getStAncrage(myCnx.nomBase,myCnx,st);
    //theSt.getListeRoadmapFromMySt(myCnx.nomBase,myCnx,st, "2011");
    //theSt.getListeLogiciels(myCnx.nomBase,myCnx,st);
    //String myListe=theSt.getListeHbergement(myCnx.nomBase,myCnx,st);
    //System.out.println(myListe);

    //theSt.getListeCompetencesByType(myCnx.nomBase,myCnx,st, 2);

    myCnx.Unconnect(st);
  }

  public static void main2(String[] args) {
  Connexion myCnx = null;
  Statement st, st2 = null;
  ResultSet rs = null;
  ResultSet rs2 = null;
  String req = "";
  String base = myCnx.getDate();
  st = myCnx.Connect();
  st2 = myCnx.Connect();
  ST theSt = new ST(447, "idSt");    //ACDC
  theSt.getAllFromBd(myCnx.nomBase, myCnx, st);
  //theSt.dump();
  //theSt.getNoteMaj();
  //theSt.addOneRoadmap(myCnx.nomBase, myCnx, st, "18778");
  //theSt.getStAncrage(myCnx.nomBase, myCnx, st);
  //ST.getListeExcel(myCnx.nomBase, myCnx, st, st2);



  transaction theTransaction = new transaction ("EnregST");
  theTransaction.begin(myCnx.nomBase,myCnx,st);

  //theSt2Enreg.bd_DeleteInterfaces(base,c1,st,theTransaction);
theSt.bd_DeleteInterfacesOM(myCnx.nomBase,myCnx,st,theTransaction);
//Cr�ation des interfaces du St
int maxInterfaces = 2;
for (int mm=1; mm <=2; mm++)
{
  String type="";
  if (mm == 1) type=""; else type="AvecActeurs";

// 1- Interface
    int idInterface = 12345;
    String StSource= "ACDC";
    String descInter = "description1";
    String typeInter = "A";
    String StDest="ADV";


    String sensInter = "-->";
    String theObj="ADM";
    String TypeImpl = "10";
    String Freq = "1";
    theSt.addListeInterfaces(myCnx.nomBase,myCnx,st,StSource,StDest, sensInter, typeInter,  TypeImpl, Freq, descInter, theObj, type, idInterface);

    // 2- Interface
         idInterface = 12345;
         StSource= "ACDC";
         descInter = "description2";
         typeInter = "A";
         StDest="OSMOSE";


         sensInter = "<--";
         theObj="ADM";
         TypeImpl = "10";
         Freq = "1";
        theSt.addListeInterfaces(myCnx.nomBase,myCnx,st,StSource,StDest, sensInter, typeInter,  TypeImpl, Freq, descInter, theObj, type, idInterface);

}
  theSt.bd_EnregInterfaces(myCnx.nomBase,myCnx,st,st2,theTransaction.nom, null);

 theTransaction.commit(myCnx.nomBase,myCnx,st);

myCnx.Unconnect(st);

}




  public static void main6(String[] args) {
    Connexion myCnx=null;
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    ST theSt = new ST(1759,"idVersionSt");
    theSt.getAllFromBd(myCnx.nomBase,myCnx,st);
    //theSt.dump();
    //theSt.getListeInterfaces(myCnx.nomBase,myCnx,st);
    theSt.getListeInterfacesAvecActeurs(myCnx.nomBase,myCnx,st);

    System.out.println("2......................nombre d'interfaces: "+theSt.ListeInterfacesAvecActeurs.size());

    for (int i=0; i < theSt.ListeInterfacesAvecActeurs.size();i++)
    {
      Interface theInterface = (Interface)theSt.ListeInterfacesAvecActeurs.elementAt(i);
      //theInterface.dump();
      theInterface.getListeOM(myCnx.nomBase,myCnx,st);
    }

    //theSt.NodeSt = new Node(theSt.idSt, theSt.idVersionSt,"GraphSt",theSt.couleur,theSt.TypeApplication,true);
    theSt.NodeSt.getGraph(myCnx.nomBase,myCnx,st);

    for (int i=0; i < theSt.ListeInterfacesAvecActeurs.size();i++)
    {
      Interface theInterface = (Interface)theSt.ListeInterfacesAvecActeurs.elementAt(i);
      ST StOrigine = theInterface.StOrigine;
      StOrigine.NodeSt.getXYFromBd(myCnx.nomBase,myCnx,st);
      ST StDestination = theInterface.StDestination;
      StDestination.NodeSt.getXYFromBd(myCnx.nomBase,myCnx,st);
    }

    for (int i=0; i < theSt.ListeInterfacesAvecActeurs.size();i++)
    {
      Interface theInterface = (Interface)theSt.ListeInterfacesAvecActeurs.elementAt(i);
      theSt.paramLinks+=theInterface.buildParamLink(i);
      ST StOrigine = theInterface.StOrigine;
      theSt.paramNode+=StOrigine.NodeSt.buildParamNode(theSt.paramNode, i);
      ST StDestination = theInterface.StDestination;
      theSt.paramNode+=StDestination.NodeSt.buildParamNode(theSt.paramNode, 1);

    }
    //theSt.NodeSt.getXYFromBd(myCnx.nomBase,myCnx,st);

    System.out.println("param_node="+theSt.paramNode);
    System.out.println("param_Link="+theSt.paramLinks);

    Bibli_Forme theBibli_Forme = new Bibli_Forme("AppliIcone",myCnx.nomBase,myCnx,st);
    for (int i=0; i < theBibli_Forme.ListeForme.size(); i++)
    {
      Forme theForme = (Forme)theBibli_Forme.ListeForme.elementAt(i);
      System.out.println(theForme.Type+"@"+theForme.couleur+"@"+theForme.icone);

      }

      System.out.println("=========================================================");
      req = "SELECT  formeTypeAppli, couleur, icone FROM   AppliIcone"; rs = myCnx.ExecReq(st,base,req);
      try { while(rs.next()) {
        String Forme = rs.getString(1);
        String couleur = rs.getString(2);
        String icone = rs.getString(3);
        int Index = icone.lastIndexOf("\\");
        icone = icone.substring(Index);
        System.out.println(Forme+"@"+couleur+"@"+icone);
    } }catch (Exception e){};

  Bibli_Forme.param_name("AppliIcone",myCnx.nomBase,myCnx,st);

  theSt.getListeLogiciels(myCnx.nomBase,myCnx,st);
  for (int i=0; i < theSt.ListeLogiciels.size(); i++)
{
  Logiciel theLogiciel = (Logiciel)theSt.ListeLogiciels.elementAt(i);
  //theLogiciel.dump();

  }

  theSt.getListeModules(myCnx.nomBase,myCnx,st);
  for (int i=0; i < theSt.ListeModules.size(); i++)
{
  Module theModule = (Module)theSt.ListeModules.elementAt(i);
  //theModule.dump();

  }

  theSt.getListeRegions(myCnx.nomBase,myCnx,st);
  for (int i=0; i < theSt.ListeRegions.size(); i++)
{
  Region theRegion = (Region)theSt.ListeRegions.elementAt(i);
  //theRegion.dump();

  }

    myCnx.Unconnect(st);

  }

  public static void main4(String[] args) {
    Connexion myCnx=null;
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    ST theSt = new ST(1759,"idVersionSt");
    theSt.getAllFromBd(myCnx.nomBase,myCnx,st);
    //theSt.dump();
    theSt.getListeInterfaces(myCnx.nomBase,myCnx,st);

    theSt.NodeSt.getGraph(myCnx.nomBase,myCnx,st);

    for ( int i=0; i < theSt.ListeInterfaces.size();i++)
    {
      Interface theInterface = (Interface)theSt.ListeInterfaces.elementAt(i);
      ST StOrigine = theInterface.StOrigine;
      StOrigine.NodeSt.getXYFromBd(myCnx.nomBase,myCnx,st);
      ST StDestination = theInterface.StDestination;
      StDestination.NodeSt.getXYFromBd(myCnx.nomBase,myCnx,st);
    }

    for ( int i=0; i < theSt.ListeInterfaces.size();i++)
    {
      Interface theInterface = (Interface)theSt.ListeInterfaces.elementAt(i);
      theSt.paramLinks+=theInterface.buildParamLink(i);

      ST StOrigine = theInterface.StOrigine;
      StOrigine.getAllFromBd(myCnx.nomBase,myCnx,st );
      //StOrigine.setNiveauCharge();
      theSt.paramNode+=StOrigine.NodeSt.buildParamNode(theSt.paramNode, i);

      ST StDestination = theInterface.StDestination;
      StDestination.getAllFromBd(myCnx.nomBase,myCnx,st );
      //StDestination.setNiveauCharge();
      theSt.paramNode+=StDestination.NodeSt.buildParamNode(theSt.paramNode, i+1);

      System.out.println(i+" ::"+theSt.paramNode);
    }


    myCnx.Unconnect(st);

  }

}

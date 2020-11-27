package ST; 
import General.Utils;
import Composant.Excel;
import java.sql.*;
import java.util.*;
import accesbase.*;
import Organisation.*;
import Processus.*;
import Projet.Action;
import Projet.Roadmap;
import Projet.Besoin;
import Projet.PhaseProjet;
import java.util.Date;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Row;
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
public class SI {
  static Connexion myCnx;
  public int id;
  public String nom="";
  public String descSI="";
  public String nomProcessus="";
  public int scope = 0;
  public String  proprietaire="";
  public int  ordre = 0;

  public int nbStCreation = 0;
  public int nbStKill = 0;
  
  public int nbSt = 0;
  public int nbSiBleu = 0;
  public int nbSiViolet = 0;
  public int nbSiRouge = 0;


  public int nbStRouge = 0;
  public int nbAppliRouge = 0;
  public int NbStAppliRouge = 0;
  public int NbniStniAppliRouge = 0;

  private String req = "";
  public String Type = "";

  public Vector ListeMOE = new Vector(6);
  public Vector ListeMOA = new Vector(6);
  public Vector ListePhasesNP = new Vector(6);
  public Vector ListeMacroSt = new Vector(6);
  public Vector ListeSt = new Vector(6);
  public static Vector ListeExigences = new Vector(6);
  public Vector ListeServices = new Vector(6);
  public Vector ListeInterfaces = new Vector(6);
  public Vector ListeTypeSi = new Vector(6);
  public static Vector ListeSI = new Vector(10);
  public static Vector ListeProcessus = new Vector(10);
  public Vector ListeModules = new Vector(10);

  public int InterfRougeRouge;
  public int InterfRougeBleu;
  public int InterfBleuRouge;
  public int InterfBleuBleu;

  public int PF_Rouge;
  public int PF_Bleu;
  public int LC_Rouge;
  public int LC_Bleu;

  public int totalPF;
  public int totalLC;

  public Vector ListeProjets = new Vector(10);
  
  public int isEquipement= 0;
  public int isComposant= 0;
  public int isSt= 0;
  public int isAppli= 1;
  public int isActeur= 0;


  public SI (String nom) {

    this.nom = nom;
    this.Type = "nom";
  }

  public SI (int id) {

    this.id = id;
    this.Type = "id";
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
     if (this.Type.equals("id"))
       req = "SELECT     idSI, nomSI, descSI, nomProcessus, scope, proprietaire, ordre, isEquipement, isComposant, isSt, isAppli, isActeur  FROM  SI WHERE idSI = '"+this.id+"'";
     else
       req = "SELECT     idSI, nomSI, descSI, nomProcessus, scope, proprietaire, ordre, isEquipement, isComposant, isSt, isAppli, isActeur  FROM  SI WHERE nomSI = '"+this.nom+"'";

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       this.id = rs.getInt(1);
       this.nom = rs.getString(2);
       if (this.nom == null) this.nom="";
       this.descSI = rs.getString(3);
       if (this.descSI == null) this.descSI="";
       this.nomProcessus = rs.getString(4);
       this.scope = rs.getInt(5);
       this.proprietaire = rs.getString(6);
       this.ordre = rs.getInt(7);
       this.isEquipement = rs.getInt("isEquipement");
       this.isComposant = rs.getInt("isComposant");       
       this.isSt = rs.getInt("isSt");
       this.isAppli = rs.getInt("isAppli");
       this.isActeur = rs.getInt("isActeur");

       } catch (SQLException s) {s.getMessage(); }
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("Type="+this.Type);
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);

    for (int i=0; i< this.ListeMOA.size();i++)
    {
      Service theMOA = (Service)this.ListeMOA.elementAt(i);
      //theMOA.dump();
    }

    System.out.println("==================================================");
 }

  public void getListeModules(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    req = "SELECT     St.nomSt, Module.nomModule , TypeModule.nom AS Type, Module.descModule , Module.Ihm , ";
    req+="                      Module.InterfaceSynchrone, Module.InterfaceAsynchrone";
    req+="  FROM         Module INNER JOIN";
    req+="                        VersionSt ON Module.versionStModule = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                        SI ON VersionSt.siVersionSt = SI.idSI LEFT OUTER JOIN";
    req+="                        TypeModule ON Module.idTypeModule = TypeModule.id";
    req+="  WHERE     (SI.nomSI = 'SI Equipements') AND (TypeModule.nom = 'Instance SQL' OR";
    req+="                        TypeModule.nom = 'Serveur Web' OR";
    req+="                        TypeModule.nom = 'Serveur Fichier')";
    
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String nomModule="";
    String descModule="";
    int  Ihm;
    int  ISync;
    int  IAsync;
    try {
      while (rs.next()) {
        String nomSt = rs.getString("nomSt");
        
        nomModule = rs.getString("nomModule");
        nomModule = nomModule.replaceAll("\\r","\\\\r");
        nomModule = nomModule.replaceAll("\\n","\\\\n");
        nomModule = nomModule.replaceAll("\"","\\\\\"");
        String type = rs.getString("Type");
        descModule = rs.getString("descModule");
        descModule = descModule.replaceAll("\\r","\\\\r");
        descModule = descModule.replaceAll("\\n","\\\\n");
        descModule = descModule.replaceAll("\"","\\\\\"");
        Ihm=rs.getInt("Ihm");
        ISync=rs.getInt("InterfaceSynchrone");
        IAsync=rs.getInt("InterfaceAsynchrone");
        Module theModule = new Module(-1,nomModule,descModule,Ihm,ISync,IAsync);
        theModule.nomSt = nomSt;
        theModule.type = type;
        this.ListeModules.addElement(theModule);
      }
    }
            catch (SQLException s) {s.getMessage();}

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
     req+=" WHERE     (nom = 'EXPORT-ROADMAP') ";
     myCnx.ExecReq(st,nomBase,req);
     // 2010-03-10 00:00:00.000
     // CONVERT(DATETIME, '1960-03-16 00:00:00', 102)
     //String theDate = theDay+"/"+theMonth+"/"+ theYear+" "+theHour+":"+theMinute+":"+theSecond+".000";

     //req = "update declencheExportRoadmap set nom='GO'";
     //myCnx.ExecReq(st,nomBase,req);




     return theDate;
  }

  public int getExport(String nomBase,Connexion myCnx, Statement st){
      ResultSet rs = null;
      int new_export = 0;

      req = "SELECT     valeur FROM         Config WHERE     (nom = 'NEW_EXPORT-ROADMAP')";
      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      String myDate="";
      try { rs.next();
              new_export= rs.getInt("valeur");

      } catch (SQLException s) {s.getMessage();}

      return new_export;
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

  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
    String description="";

    if (this.descSI != null)
    {
      //description = theInterface.descInterface.replaceAll("\r","").replaceAll("0x22","'").replaceAll("\u0092","'").replaceAll("'","''");
      for (int j=0; j < this.descSI.length();j++)
      {
        int c = (int)this.descSI.charAt(j);
        //System.out.println("j: "+j+"---"+theInterface.descInterface.charAt(j) + " --" +(int)theInterface.descInterface.charAt(j));
        if (c != 34)
          {
            description += this.descSI.charAt(j);
          }
          else
          {
            description +="'";
          }
      }
      description = description.replaceAll("\r","").replaceAll("'","''");
     }

    req = "UPDATE [SI]";
    req+= "   SET [nomSI] = "+ "'"+this.nom+ "'" + ",";
    req+= "      [descSI] = "+ "'"+description+ "'" + ",";
    req+= "       [nomProcessus] = "+ "'"+this.nomProcessus+ "'" + ",";
    req+= "       [scope] = "+ "'"+this.scope+ "'" + ",";
    req+= "       [proprietaire] = "+ "'"+this.proprietaire+ "'" + ",";
    req+= "       [ordre] = "+ "'"+this.ordre+ "'" + ",";
    req+= "       [isEquipement] = "+ "'"+this.isEquipement+ "'" + ",";
    req+= "       [isComposant] = "+ "'"+this.isComposant+ "'" + ",";
    req+= "       [isSt] = "+ "'"+this.isSt+ "'" + ",";
    req+= "       [isAppli] = "+ "'"+this.isAppli+ "'" + ",";
    req+= "       [isActeur] = "+ "'"+this.isActeur+ "'" + "";
    req+= "  WHERE idSI= "+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }

  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;
    int nb=0;
    
  req = "SELECT     St.nomSt";
  req+=  " FROM         St INNER JOIN";
  req+=  "                      VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
  req+=  "                       SI ON VersionSt.siVersionSt = SI.idSI";
  req+=  " WHERE     SI.idSI = " + this.id;
  req+=  " ORDER BY St.nomSt";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  String ListeSt = "";
  try {
    while (rs.next()) {
      if (!ListeSt.equals("")) ListeSt += ", ";else ListeSt += " // Produits: ";
      ListeSt += rs.getString(1) ;
    }
  }
  catch (SQLException s) {
    s.getMessage();
  }
  
    
  req = "SELECT     nomTypeSi";
  req+=  " FROM         TypeSi";
  req+=  " WHERE     siTypesi = "+ this.id;
  req+=  " ORDER BY siTypesi";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  nb=0;

  try {
    while (rs.next()) {
      if (nb!=0) ListeSt += ", ";else ListeSt += " // Sous-Catalogues: ";
      ListeSt += rs.getString(1) ;
      nb++;
    }
  }
  catch (SQLException s) {
    s.getMessage();
  }  

    
  req = "SELECT     nomMacrost";
  req+=  " FROM         MacroSt";
  req+=  " WHERE     siMacrost = "+ this.id;
  req+=  " ORDER BY nomMacrost";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  nb=0;

  try {
    while (rs.next()) {
      if (nb!=0) ListeSt += ", ";else ListeSt += " // Domaines: ";
      ListeSt += rs.getString(1) ;
      nb++;
    }
  }
  catch (SQLException s) {
    s.getMessage();
  } 
  
  if (!ListeSt.equals("")) {
    myError.Detail = ListeSt;
    myError.cause = -2;
    return myError;
  }
  
    req = "DELETE FROM SI WHERE (idSI  = '"+this.id+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);

    return myError;
  } 
  
  public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){

  String mydesc="";
  if (this.descSI != null)
    mydesc=this.descSI.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\"","''").replaceAll("&","et").replace('+',' ');
  else
    mydesc="";
  
    req = "INSERT INTO [SI]";
    req+= "           ([nomSI]";
    req+= "            ,[descSI]";
    req+= "            ,[nomProcessus]";
    req+= "            ,[scope]";
    req+= "            ,[proprietaire]";
    req+= "            ,[ordre]";
    req+= "            ,[isEquipement]";
    req+= "            ,[isComposant]";
    req+= "            ,[isSt]";
    req+= "            ,[isAppli]";
    req+= "            ,[isActeur])";
    req+= "      VALUES";
    req+= "            (";
    req+= "'"+this.nom+"'" + ",";
    req+= "'"+mydesc+"'" + ",";
    req+= "'"+this.nomProcessus+"'" + ",";
    req+= "'"+this.scope+"'" + ",";
    req+= "'"+this.proprietaire+"'"+ ",";
    req+= "'"+this.ordre+"'"+ ",";
    req+= "'"+this.isEquipement+"'"+ ",";
    req+= "'"+this.isComposant+"'"+ ",";
    req+= "'"+this.isSt+"'"+ ",";
    req+= "'"+this.isAppli+"'"+ ",";
    req+= "'"+this.isActeur+"'";
    req+= "            )";


    //myCnx.trace("@@456789--------","req",""+req);
      ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);

      //myCnx.trace("@@456789--------","bd_Insert",""+transaction);
      if (myError.cause == -1) return myError;
      //req = "SELECT idRoadmap FROM  Briefs WHERE   (version = '"+this.version+"')";
      req="SELECT  idSI FROM   SI ORDER BY idSI DESC";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("idSI");
        } catch (SQLException s) {s.getMessage();}

    return myError;
  }
  
public void getListeProjets(String nomBase,Connexion myCnx, Statement st, int anneeRef, int previousLastJalon, int LastJalon){

req= "SELECT     ListeProjets.idRoadmap,ListeProjets.nomProjet, Service_1.nomService AS nomServiceMOA, Service.nomService AS nomServiceMOE, typeJalon.nom AS EtatRoadmap, ListeProjets.idPO, ";
req+= "                       ListeProjets.docEB, ListeProjets.dateT0MOE, ListeProjets.docDevis, TypeSi.nomTypeSi, Membre.nomMembre + ',' + Membre.prenomMembre AS Leader,  ";
req+= "                       ListeProjets.idBrief AS Brief";
req+= " FROM         ListeProjets INNER JOIN";
req+= "                       TypeSi ON ListeProjets.typeSiVersionSt = TypeSi.idTypeSi INNER JOIN";
req+= "                       typeJalon ON ListeProjets.idJalon = typeJalon.id INNER JOIN";
req+= "                       Membre ON ListeProjets.idMembre = Membre.idMembre INNER JOIN";
req+= "                        Service ON ListeProjets.serviceMoeVersionSt = Service.idService LEFT OUTER JOIN";
req+= "                        Service AS Service_1 ON ListeProjets.serviceMoaVersionSt = Service_1.idService";
req+= "   WHERE   (ListeProjets.standby != 1) AND (ListeProjets.LF_Month = - 1) AND (ListeProjets.LF_Year = - 1) AND (ListeProjets.isRecurrent <> 1) AND (ListeProjets.isMeeting <> 1) AND ";
req+= "                        (NOT (ListeProjets.nomProjet LIKE '---%')) AND (ListeProjets.version <> '-- NON REGRESSION')";
req+= "    AND ( ";
req+= "   ((ListeProjets.idJalon <> "+LastJalon+") AND (ListeProjets.idJalon <> "+previousLastJalon+") AND  (YEAR((SELECT date  FROM JalonsProjet WHERE  type = 4 and JalonsProjet.idRoadmap = ListeProjets.idRoadmap)) < "+anneeRef+") )";
req+= "    OR ";
req+= "   (YEAR((SELECT date  FROM JalonsProjet WHERE  type = "+previousLastJalon+" and JalonsProjet.idRoadmap = ListeProjets.idRoadmap)) >= "+anneeRef+")";
req+= "   )";
req+= "     AND  (YEAR((SELECT date  FROM JalonsProjet WHERE  type = "+previousLastJalon+" and JalonsProjet.idRoadmap = ListeProjets.idRoadmap)) > "+(anneeRef -3)+")";
req+= "   ORDER BY ListeProjets.nomProjet";

ResultSet rs;
Date theDate = null;
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
       
       Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap")); 
       
       theRoadmap.nomProjet = rs.getString("nomProjet");
       theRoadmap.MOA = rs.getString("nomServiceMOA");
       theRoadmap.MOE = rs.getString("nomServiceMOE");
       theRoadmap.EtatRoadmap = rs.getString("EtatRoadmap");
       theRoadmap.idPO= ""+rs.getInt("idPO");
       theRoadmap.docEB= rs.getString("docEB");
       theDate = rs.getDate("dateT0MOE");
       theRoadmap.dateT0MOE = Utils.getDateFrench(theDate);
       theRoadmap.docDevis= rs.getString("docDevis");
       theRoadmap.Leader = rs.getString("Leader");

       this.ListeProjets.addElement(theRoadmap);
     }
   }
catch (Exception s) { }
}

public void getListeProjets2(String nomBase,Connexion myCnx, Statement st,  int anneeRef,  String str_ListePeriode, String Client, String PeriodeDebut, String PeriodeFin, String MotsClef, String idVersionSt, String idMembre,  String ST_Lies, int monthRef, String Projets  ){

// @@

   ResultSet rs;


   String ClauseMembre = "";
   if ((idMembre != null) && !idMembre.equals("") && !idMembre.equals("-1") )
   {
     ClauseMembre = " and";
     ClauseMembre+=" idVersionSt in";
     ClauseMembre+="    (";
     ClauseMembre+=" SELECT distinct idVersionSt FROM (";
     ClauseMembre+=" SELECT VersionSt.idVersionSt, St.nomSt FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoaVersionSt = Membre.idMembre WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.isActeur <> 1) AND (Membre.idMembre = '"+idMembre+"') AND (St.creerProjet = 1) AND (ST.creerProjet = 1) AND (ST.isRecurrent <> 1) AND     (ST.isMeeting <> 1) ";

     ClauseMembre+=" UNION ";
     ClauseMembre+=" SELECT VersionSt.idVersionSt, St.nomSt FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoeVersionSt = Membre.idMembre WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.isActeur <> 1) AND (Membre.idMembre = '"+idMembre+"') AND (St.creerProjet = 1) AND (ST.creerProjet = 1) AND (ST.isRecurrent <> 1) AND     (ST.isMeeting <> 1) ";

     ClauseMembre+=" UNION ";
     ClauseMembre+=" SELECT ListeST.idVersionSt, ListeST.nomSt FROM Membre INNER JOIN EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ListeST ON Equipe.nom = ListeST.nomSt WHERE (Membre.idMembre = '"+idMembre+"') AND (ListeST.isActeur <> 1) AND (ListeST.creerProjet = 1) AND (ListeST.isRecurrent <> 1) AND     (ListeST.isMeeting <> 1)";
     ClauseMembre+=" ) ";
     ClauseMembre+=" AS maTable ";
     ClauseMembre+=" )";
  }

   String ClauseST="";

   if ((idVersionSt != null) && !idVersionSt.equals("") && !idVersionSt.equals("-1") && !idVersionSt.equals("-1;"))
   {
     ClauseST = " AND (";
     int nb = 0;

     for (StringTokenizer t = new StringTokenizer(idVersionSt, ";");
          t.hasMoreTokens(); ) {
       String theTocken = t.nextToken();
       if (nb == 0)
         ClauseST += "idVersionSt=" + theTocken;
       else
         ClauseST += " OR idVersionSt=" + theTocken;
       nb++;
     }

     ClauseST += ") ";
   }
   
   String ClauseProjets="";

   if (Projets != null && !Projets.equals(""))
   {
     ClauseProjets = " AND (";
     int nb = 0;

     for (StringTokenizer t = new StringTokenizer(Projets, ";");
          t.hasMoreTokens(); ) {
       String theTocken = t.nextToken();
       if (nb == 0)
         ClauseProjets += "idRoadmap =" + theTocken;
       else
         ClauseProjets += " OR idRoadmap =" + theTocken;
       nb++;
     }

     ClauseProjets += ") ";
   }
   

   String ClauseMotsClef = "";
   if (!(MotsClef == null) && !MotsClef.equals("")  )
   {
   ClauseMotsClef =" AND (MotsClef LIKE '%" + MotsClef + "%') ";

  }

   String ClausePeriode = "";
   if (!PeriodeDebut.equals("") && !PeriodeFin.equals("")  )
   {
   ClausePeriode +=" AND ";
   ClausePeriode +=" (";
   ClausePeriode +=" ((ListeProjets.dateMep >= CONVERT(DATETIME, '"+PeriodeDebut+"', 103) AND ListeProjets.dateMep <= CONVERT(DATETIME, '"+PeriodeFin+"', 103)))";
   ClausePeriode +=" )   ";
  }

   String ClauseCouleur = "";
   String couleur = "";

    String ClauseInterface = "";

    if ( (Client != null) && (!Client.equals("-1")))
    {
      ClauseInterface = " and";
      ClauseInterface += "     (";

      ClauseInterface += " idVersionSt in";

      ClauseInterface += " (";

      ClauseInterface += " SELECT     VersionSt_1.idVersionSt";
      ClauseInterface += "     FROM         Interface INNER JOIN";
      ClauseInterface += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterface += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterface += " WHERE     (VersionSt.idVersionSt = "+Client+")";

      ClauseInterface += " UNION";

      ClauseInterface += " SELECT     VersionSt.idVersionSt";
      ClauseInterface += " FROM         Interface INNER JOIN";
      ClauseInterface += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterface += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterface += " WHERE     (VersionSt_1.idVersionSt = "+Client+")";

      ClauseInterface += "     )";
      ClauseInterface += "     )";
    }
    String ClauseInterfaceST = "";

    if ( (ST_Lies != null) && (!ST_Lies.equals("-1")))
    {
      ClauseInterfaceST = " and";
      ClauseInterfaceST += "     (";

      ClauseInterfaceST += " idVersionSt in";

      ClauseInterfaceST += " (";

      ClauseInterfaceST += " SELECT     VersionSt_1.idVersionSt";
      ClauseInterfaceST += "     FROM         Interface INNER JOIN";
      ClauseInterfaceST += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterfaceST += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterfaceST += " WHERE     (VersionSt.idVersionSt = "+ST_Lies+") AND (Interface.typeInterface = 'ST')";

      ClauseInterfaceST += " UNION";

      ClauseInterfaceST += " SELECT     VersionSt.idVersionSt";
      ClauseInterfaceST += " FROM         Interface INNER JOIN";
      ClauseInterfaceST += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterfaceST += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterfaceST += " WHERE     (VersionSt_1.idVersionSt = "+ST_Lies+") AND (Interface.typeInterface = 'ST')";

      ClauseInterfaceST += " UNION";

      ClauseInterfaceST += " SELECT    "+ST_Lies;

      ClauseInterfaceST += "     )";
      ClauseInterfaceST += "     )";
    }

    String ClausePhase=this.getClauseWhere(str_ListePeriode);

  req = "select * from ( ";
  req+= "  select ListeProjets.nomSt,idRoadmap, nomProjet,idVersionSt,version,dateEB,dateMep,dateMes,dateT0,dateTest,dateT0MOE, nbDependances,  nbActions, dateT0_init, dateEB_init, dateTest_init, dateMep_init, dateMes_init, nomProjet as nomProjetPere, 0 as fils, standby, Panier, idRoadmapPere, serviceMoaVersionSt, serviceMoeVersionSt, etat, MotsClef, idPO, dateMep as dateMepPere, isRecurrent,  ListeProjets.ordreJalon,  ListeProjets.idJalon  from ListeProjets ";
  req+= " where idRoadmapPere = -1";
  req+= " union";
  req+= " SELECT     ListeProjets.nomSt,ListeProjets.idRoadmap, ListeProjets.nomProjet, ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.dateMes, ListeProjets.dateT0, ";
  req+= "                       ListeProjets.dateTest, ListeProjets.dateT0MOE, ListeProjets.nbDependances, ListeProjets.nbActions, ListeProjets.dateT0_init, ListeProjets.dateEB_init, ListeProjets.dateTest_init, ";
  req+= "                       ListeProjets.dateMep_init, ListeProjets.dateMes_init, ListeProjets_1.nomProjet AS nomProjetPere, 1 as fils, ListeProjets.standby, ListeProjets.Panier, ListeProjets.idRoadmapPere, ListeProjets.serviceMoaVersionSt, ListeProjets.serviceMoeVersionSt, ListeProjets.etat, ListeProjets.MotsClef, ListeProjets.idPO,  ListeProjets_1.dateMep as dateMepPere, ListeProjets_1.isRecurrent,  ListeProjets.ordreJalon,  ListeProjets.idJalon";
  req+= " FROM         ListeProjets INNER JOIN";
  req+= "                       ListeProjets AS ListeProjets_1 ON ListeProjets.idRoadmapPere = ListeProjets_1.idRoadmap";
  req+= " WHERE    (ListeProjets.idRoadmapPere > - 1)";

  req+= " )";
  req+= " as myTableGlobale";
 
     //req+=" WHERE     ((serviceMoaVersionSt = "+this.id+") OR (serviceMoeVersionSt = "+this.id+"))  ";
     req+=" WHERE  (";
     req+=" (";
     req+=" (dateMep IS NOT NULL)     ";
     req+=" AND (dateMep > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" AND (dateMep < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" )  ";

     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL)     ";
     req+="   AND (dateT0 > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00', 102) ) ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00', 102) ) ";     
     req+="   )  ";
     
     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL) AND (dateMep IS NOT NULL)    ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+anneeRef+"-1-1 00:00:00', 102) ) ";
     req+="   AND (dateMep >= CONVERT(DATETIME, '"+(anneeRef+1)+"-1-1 00:00:00', 102) ) ";     
     req+="   )) "; 
  
  req+=" AND (dateT0 > CONVERT(DATETIME, '1900-1-1 00:00:00', 102))  ";
  req+=" AND (dateEB > CONVERT(DATETIME, '1900-1-1 00:00:00', 102))";
  req+=" AND (dateTest > CONVERT(DATETIME, '1900-1-1 00:00:00', 102)) ";
  req+=" AND (dateMep > CONVERT(DATETIME, '1900-1-1 00:00:00', 102))    ";
  
     req+=" AND  (";
     req+=" (";
     req+=" (dateMep IS NOT NULL)     ";
     req+=" AND (dateMep > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" AND (dateMep < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" )  ";

     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL)     ";
     req+="   AND (dateT0 > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00', 102) ) ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00', 102) ) ";     
     req+="   )  ";
     
     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL) AND (dateMep IS NOT NULL)    ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+anneeRef+"-1-1 00:00:00', 102) ) ";
     req+="   AND (dateMep >= CONVERT(DATETIME, '"+(anneeRef+1)+"-1-1 00:00:00', 102) ) ";     
     req+="   )) ";       
     
  req+=" AND (standby = 0)";
  req+=" AND (isRecurrent  = 0)";
  //req+=" AND (idRoadmapPere = -1)";
  
     req+=ClauseMembre;
     req+=ClauseST;
     req+=ClauseProjets;
     req+=ClauseMotsClef;
     req+=ClausePeriode;
     req+=ClauseCouleur;
     req+=ClauseInterface;
     req+=ClausePhase;
     req+=ClauseInterfaceST;
     
    req+=" ORDER BY nomSt, dateMepPere,nomProjetPere, fils, dateMep "; 
  

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  
  Date theDate=null;

  try {
  while(rs.next())
  {
    Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap") );
     nom = rs.getString("nomProjet");

     theRoadmap.idVersionSt= rs.getString("idVersionSt");
     theRoadmap.version = rs.getString("version");
     theRoadmap.DdateEB = rs.getDate("dateEB");
     theRoadmap.dateEB =  Utils.getDateFrench(theRoadmap.DdateEB);
     theRoadmap.DdateProd = rs.getDate("dateMep");
     theRoadmap.dateProd =  Utils.getDateFrench(theRoadmap.DdateProd);
     theRoadmap.DdateMes = rs.getDate("dateMes");
     theRoadmap.dateMes =  Utils.getDateFrench(theRoadmap.DdateMes);
     theRoadmap.DdateT0 = rs.getDate("dateT0");
     theRoadmap.dateT0 =  Utils.getDateFrench(theRoadmap.DdateT0);
     theRoadmap.DdateTest = rs.getDate("dateTest");
     theRoadmap.dateTest =  Utils.getDateFrench(theRoadmap.DdateTest);
     //idRoadmap= rs.getInt("idRoadmap");
     theDate = rs.getDate("dateT0MOE");
     theRoadmap.dateT0MOE =  Utils.getDateFrench(theDate);

     theRoadmap.dateDevMOE="";
     theRoadmap.dateSpecMOE="";
     theRoadmap.dateLivrMOE="";

     theRoadmap.nomSt = nom;
     
       theRoadmap.nbDependances= rs.getInt("nbDependances");
       theRoadmap.nbActions= rs.getInt("nbActions");
       
       theRoadmap.DdateT0_init = rs.getDate("dateT0_init");
       theRoadmap.dateT0_init = Utils.getDateFrench(theRoadmap.DdateT0_init);
       theRoadmap.DdateEB_init = rs.getDate("dateEB_init");
       theRoadmap.dateEB_init = Utils.getDateFrench(theRoadmap.DdateEB_init);
       theRoadmap.DdateTest_init = rs.getDate("dateTest_init");
       theRoadmap.dateTest_init = Utils.getDateFrench(theRoadmap.DdateTest_init);
       theRoadmap.DdateProd_init = rs.getDate("dateMep_init");
       theRoadmap.dateProd_init = Utils.getDateFrench(theRoadmap.DdateProd_init);
       theRoadmap.DdateMes_init = rs.getDate("dateMes_init");
       theRoadmap.dateMes_init = Utils.getDateFrench(theRoadmap.DdateMes_init);        
       
     if ((theRoadmap.dateT0 == null) || (theRoadmap.dateT0.equals(""))) continue;
     if ((theRoadmap.dateEB == null) || (theRoadmap.dateEB.equals(""))) continue;
     if ((theRoadmap.dateTest == null) || (theRoadmap.dateTest.equals(""))) continue;
        if ((theRoadmap.dateProd == null) || (theRoadmap.dateProd.equals(""))) continue;
        
        theRoadmap.idPere= rs.getInt("idRoadmapPere");
        
        theRoadmap.Etat= rs.getString("Etat");
        
        theRoadmap.idPO= rs.getString("idPO");
        
       
       PhaseProjet phaseEB0 = new PhaseProjet("EB0", theRoadmap.DdateT0_init, theRoadmap.DdateEB_init);
       theRoadmap.ListePhases.addElement(phaseEB0);
       PhaseProjet phaseEB = new PhaseProjet("EB", theRoadmap.DdateT0, theRoadmap.DdateEB);
       theRoadmap.ListePhases.addElement(phaseEB);
       
       PhaseProjet phaseDEV0 = new PhaseProjet("DEV0", theRoadmap.DdateEB_init, theRoadmap.DdateTest_init);
       theRoadmap.ListePhases.addElement(phaseDEV0);   
       PhaseProjet phaseDEV = new PhaseProjet("DEV", theRoadmap.DdateEB, theRoadmap.DdateTest);
       theRoadmap.ListePhases.addElement(phaseDEV);       
       
       PhaseProjet phaseTEST0 = new PhaseProjet("TEST0", theRoadmap.DdateTest_init, theRoadmap.DdateProd_init);
       theRoadmap.ListePhases.addElement(phaseTEST0);
       PhaseProjet phaseTEST = new PhaseProjet("TEST", theRoadmap.DdateTest, theRoadmap.DdateProd);
       theRoadmap.ListePhases.addElement(phaseTEST);       
       
       PhaseProjet phaseMES0 = new PhaseProjet("MES0", theRoadmap.DdateProd_init, theRoadmap.DdateMes_init);
       theRoadmap.ListePhases.addElement(phaseMES0);
       PhaseProjet phaseMES = new PhaseProjet("MES", theRoadmap.DdateProd, theRoadmap.DdateMes);
       theRoadmap.ListePhases.addElement(phaseMES);  
       
       theRoadmap.ordreJalon= rs.getInt("ordreJalon");
       //System.out.println(nom + ":: etat= "+theRoadmap.Etat);        
     this.ListeProjets.addElement(theRoadmap);
   }

  }
   catch (SQLException s) { }


}
public int getListeProjets(String nomBase,Connexion myCnx, Statement st,  int anneeRef,  String str_ListePeriode, String Client, String PeriodeDebut, String PeriodeFin, String MotsClef, String idVersionSt, String idMembre,  String ST_Lies, int monthRef, String Projets, int page, int nbProjets ){

// @@

   ResultSet rs;


   String ClauseMembre = "";
   if ((idMembre != null) && !idMembre.equals("") && !idMembre.equals("-1") )
   {
     ClauseMembre = " and";
     ClauseMembre+=" idVersionSt in";
     ClauseMembre+="    (";
     ClauseMembre+=" SELECT distinct idVersionSt FROM (";
     ClauseMembre+=" SELECT VersionSt.idVersionSt, St.nomSt FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoaVersionSt = Membre.idMembre WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.isActeur <> 1) AND (Membre.idMembre = '"+idMembre+"') AND (St.creerProjet = 1) AND (ST.creerProjet = 1) AND (ST.isRecurrent <> 1) AND     (ST.isMeeting <> 1) ";

     ClauseMembre+=" UNION ";
     ClauseMembre+=" SELECT VersionSt.idVersionSt, St.nomSt FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoeVersionSt = Membre.idMembre WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.isActeur <> 1) AND (Membre.idMembre = '"+idMembre+"') AND (St.creerProjet = 1) AND (ST.creerProjet = 1) AND (ST.isRecurrent <> 1) AND     (ST.isMeeting <> 1) ";

     ClauseMembre+=" UNION ";
     ClauseMembre+=" SELECT ListeST.idVersionSt, ListeST.nomSt FROM Membre INNER JOIN EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ListeST ON Equipe.nom = ListeST.nomSt WHERE (Membre.idMembre = '"+idMembre+"') AND (ListeST.isActeur <> 1) AND (ListeST.creerProjet = 1) AND (ListeST.isRecurrent <> 1) AND     (ListeST.isMeeting <> 1)";
     ClauseMembre+=" ) ";
     ClauseMembre+=" AS maTable ";
     ClauseMembre+=" )";
  }

   String ClauseST="";

   if ((idVersionSt != null) && !idVersionSt.equals("") && !idVersionSt.equals("-1") && !idVersionSt.equals("-1;"))
   {
     ClauseST = " AND (";
     int nb = 0;

     for (StringTokenizer t = new StringTokenizer(idVersionSt, ";");
          t.hasMoreTokens(); ) {
       String theTocken = t.nextToken();
       if (nb == 0)
         ClauseST += "idVersionSt=" + theTocken;
       else
         ClauseST += " OR idVersionSt=" + theTocken;
       nb++;
     }

     ClauseST += ") ";
   }
   
   String ClauseProjets="";

   if (Projets != null && !Projets.equals(""))
   {
     ClauseProjets = " AND (";
     int nb = 0;

     for (StringTokenizer t = new StringTokenizer(Projets, ";");
          t.hasMoreTokens(); ) {
       String theTocken = t.nextToken();
       if (nb == 0)
         ClauseProjets += "idRoadmap =" + theTocken;
       else
         ClauseProjets += " OR idRoadmap =" + theTocken;
       nb++;
     }

     ClauseProjets += ") ";
   }
   

   String ClauseMotsClef = "";
   if (!(MotsClef == null) && !MotsClef.equals("")  )
   {
   ClauseMotsClef =" AND (MotsClef LIKE '%" + MotsClef + "%') ";

  }

   String ClausePeriode = "";
   if (!PeriodeDebut.equals("") && !PeriodeFin.equals("")  )
   {
   ClausePeriode +=" AND ";
   ClausePeriode +=" (";
   ClausePeriode +=" ((ListeProjets.dateMep >= CONVERT(DATETIME, '"+PeriodeDebut+"', 103) AND ListeProjets.dateMep <= CONVERT(DATETIME, '"+PeriodeFin+"', 103)))";
   ClausePeriode +=" )   ";
  }

   String ClauseCouleur = "";
   String couleur = "";

    String ClauseInterface = "";

    if ( (Client != null) && (!Client.equals("-1")))
    {
      ClauseInterface = " and";
      ClauseInterface += "     (";

      ClauseInterface += " idVersionSt in";

      ClauseInterface += " (";

      ClauseInterface += " SELECT     VersionSt_1.idVersionSt";
      ClauseInterface += "     FROM         Interface INNER JOIN";
      ClauseInterface += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterface += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterface += " WHERE     (VersionSt.idVersionSt = "+Client+")";

      ClauseInterface += " UNION";

      ClauseInterface += " SELECT     VersionSt.idVersionSt";
      ClauseInterface += " FROM         Interface INNER JOIN";
      ClauseInterface += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterface += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterface += " WHERE     (VersionSt_1.idVersionSt = "+Client+")";

      ClauseInterface += "     )";
      ClauseInterface += "     )";
    }
    String ClauseInterfaceST = "";

    if ( (ST_Lies != null) && (!ST_Lies.equals("-1")))
    {
      ClauseInterfaceST = " and";
      ClauseInterfaceST += "     (";

      ClauseInterfaceST += " idVersionSt in";

      ClauseInterfaceST += " (";

      ClauseInterfaceST += " SELECT     VersionSt_1.idVersionSt";
      ClauseInterfaceST += "     FROM         Interface INNER JOIN";
      ClauseInterfaceST += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterfaceST += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterfaceST += " WHERE     (VersionSt.idVersionSt = "+ST_Lies+") AND (Interface.typeInterface = 'ST')";

      ClauseInterfaceST += " UNION";

      ClauseInterfaceST += " SELECT     VersionSt.idVersionSt";
      ClauseInterfaceST += " FROM         Interface INNER JOIN";
      ClauseInterfaceST += "                 VersionSt ON Interface.origineInterface = VersionSt.stVersionSt INNER JOIN";
      ClauseInterfaceST += "                 VersionSt AS VersionSt_1 ON Interface.extremiteInterface = VersionSt_1.stVersionSt";
      ClauseInterfaceST += " WHERE     (VersionSt_1.idVersionSt = "+ST_Lies+") AND (Interface.typeInterface = 'ST')";

      ClauseInterfaceST += " UNION";

      ClauseInterfaceST += " SELECT    "+ST_Lies;

      ClauseInterfaceST += "     )";
      ClauseInterfaceST += "     )";
    }

    String ClausePhase=this.getClauseWhere(str_ListePeriode);

  req = "select * from ( ";
  req+= "  select ListeProjets.nomSt,idRoadmap, nomProjet,idVersionSt,version,dateEB,dateMep,dateMes,dateT0,dateTest,dateT0MOE, nbDependances,  nbActions, dateT0_init, dateEB_init, dateTest_init, dateMep_init, dateMes_init, nomProjet as nomProjetPere, 0 as fils, standby, Panier, idRoadmapPere, serviceMoaVersionSt, serviceMoeVersionSt, etat, MotsClef, idPO, dateMep as dateMepPere, isRecurrent,  ListeProjets.ordreJalon,  ListeProjets.idJalon  from ListeProjets ";
  req+= " where idRoadmapPere = -1";
  req+= " union";
  req+= " SELECT     ListeProjets.nomSt,ListeProjets.idRoadmap, ListeProjets.nomProjet, ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.dateMes, ListeProjets.dateT0, ";
  req+= "                       ListeProjets.dateTest, ListeProjets.dateT0MOE, ListeProjets.nbDependances, ListeProjets.nbActions, ListeProjets.dateT0_init, ListeProjets.dateEB_init, ListeProjets.dateTest_init, ";
  req+= "                       ListeProjets.dateMep_init, ListeProjets.dateMes_init, ListeProjets_1.nomProjet AS nomProjetPere, 1 as fils, ListeProjets.standby, ListeProjets.Panier, ListeProjets.idRoadmapPere, ListeProjets.serviceMoaVersionSt, ListeProjets.serviceMoeVersionSt, ListeProjets.etat, ListeProjets.MotsClef, ListeProjets.idPO,  ListeProjets_1.dateMep as dateMepPere, ListeProjets_1.isRecurrent,  ListeProjets.ordreJalon,  ListeProjets.idJalon";
  req+= " FROM         ListeProjets INNER JOIN";
  req+= "                       ListeProjets AS ListeProjets_1 ON ListeProjets.idRoadmapPere = ListeProjets_1.idRoadmap";
  req+= " WHERE    (ListeProjets.idRoadmapPere > - 1)";

  req+= " )";
  req+= " as myTableGlobale";
 
     //req+=" WHERE     ((serviceMoaVersionSt = "+this.id+") OR (serviceMoeVersionSt = "+this.id+"))  ";
     req+=" WHERE  (";
     req+=" (";
     req+=" (dateMep IS NOT NULL)     ";
     req+=" AND (dateMep > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" AND (dateMep < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" )  ";

     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL)     ";
     req+="   AND (dateT0 > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00', 102) ) ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00', 102) ) ";     
     req+="   )  ";
     
     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL) AND (dateMep IS NOT NULL)    ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+anneeRef+"-1-1 00:00:00', 102) ) ";
     req+="   AND (dateMep >= CONVERT(DATETIME, '"+(anneeRef+1)+"-1-1 00:00:00', 102) ) ";     
     req+="   )) "; 
  
  req+=" AND (dateT0 > CONVERT(DATETIME, '1900-1-1 00:00:00', 102))  ";
  req+=" AND (dateEB > CONVERT(DATETIME, '1900-1-1 00:00:00', 102))";
  req+=" AND (dateTest > CONVERT(DATETIME, '1900-1-1 00:00:00', 102)) ";
  req+=" AND (dateMep > CONVERT(DATETIME, '1900-1-1 00:00:00', 102))    ";
  
     req+=" AND  (";
     req+=" (";
     req+=" (dateMep IS NOT NULL)     ";
     req+=" AND (dateMep > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" AND (dateMep < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00',102)) ";
     req+=" )  ";

     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL)     ";
     req+="   AND (dateT0 > CONVERT(DATETIME, '"+anneeRef+"-"+monthRef+"-1 00:00:00', 102) ) ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+(anneeRef+1)+"-"+monthRef+"-1 00:00:00', 102) ) ";     
     req+="   )  ";
     
     req+="  OR  ";
     req+="   (    ";
     req+="   (dateT0 IS NOT NULL) AND (dateMep IS NOT NULL)    ";
     req+="   AND (dateT0 < CONVERT(DATETIME, '"+anneeRef+"-1-1 00:00:00', 102) ) ";
     req+="   AND (dateMep >= CONVERT(DATETIME, '"+(anneeRef+1)+"-1-1 00:00:00', 102) ) ";     
     req+="   )) ";       
     
  req+=" AND (standby = 0)";
  req+=" AND (isRecurrent  = 0)";
  //req+=" AND (idRoadmapPere = -1)";
  
     req+=ClauseMembre;
     req+=ClauseST;
     req+=ClauseProjets;
     req+=ClauseMotsClef;
     req+=ClausePeriode;
     req+=ClauseCouleur;
     req+=ClauseInterface;
     req+=ClausePhase;
     req+=ClauseInterfaceST;
     
    req+=" ORDER BY nomSt, dateMepPere,nomProjetPere, fils, dateMep "; 
  

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  
  Date theDate=null;
  int num = 0;
  int count = 0;
    
  try {
  while(rs.next())
  {
   count++;
   if ((num >= (page -1) * nbProjets) && (num < page * nbProjets))
   {      
    Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap") );
     nom = rs.getString("nomProjet");

     theRoadmap.idVersionSt= rs.getString("idVersionSt");
     theRoadmap.version = rs.getString("version");
     theRoadmap.DdateEB = rs.getDate("dateEB");
     theRoadmap.dateEB =  Utils.getDateFrench(theRoadmap.DdateEB);
     theRoadmap.DdateProd = rs.getDate("dateMep");
     theRoadmap.dateProd =  Utils.getDateFrench(theRoadmap.DdateProd);
     theRoadmap.DdateMes = rs.getDate("dateMes");
     theRoadmap.dateMes =  Utils.getDateFrench(theRoadmap.DdateMes);
     theRoadmap.DdateT0 = rs.getDate("dateT0");
     theRoadmap.dateT0 =  Utils.getDateFrench(theRoadmap.DdateT0);
     theRoadmap.DdateTest = rs.getDate("dateTest");
     theRoadmap.dateTest =  Utils.getDateFrench(theRoadmap.DdateTest);
     //idRoadmap= rs.getInt("idRoadmap");
     theDate = rs.getDate("dateT0MOE");
     theRoadmap.dateT0MOE =  Utils.getDateFrench(theDate);

     theRoadmap.dateDevMOE="";
     theRoadmap.dateSpecMOE="";
     theRoadmap.dateLivrMOE="";

     theRoadmap.nomSt = nom;
     
       theRoadmap.nbDependances= rs.getInt("nbDependances");
       theRoadmap.nbActions= rs.getInt("nbActions");
       
       theRoadmap.DdateT0_init = rs.getDate("dateT0_init");
       theRoadmap.dateT0_init = Utils.getDateFrench(theRoadmap.DdateT0_init);
       theRoadmap.DdateEB_init = rs.getDate("dateEB_init");
       theRoadmap.dateEB_init = Utils.getDateFrench(theRoadmap.DdateEB_init);
       theRoadmap.DdateTest_init = rs.getDate("dateTest_init");
       theRoadmap.dateTest_init = Utils.getDateFrench(theRoadmap.DdateTest_init);
       theRoadmap.DdateProd_init = rs.getDate("dateMep_init");
       theRoadmap.dateProd_init = Utils.getDateFrench(theRoadmap.DdateProd_init);
       theRoadmap.DdateMes_init = rs.getDate("dateMes_init");
       theRoadmap.dateMes_init = Utils.getDateFrench(theRoadmap.DdateMes_init);        
       
     if ((theRoadmap.dateT0 == null) || (theRoadmap.dateT0.equals(""))) continue;
     if ((theRoadmap.dateEB == null) || (theRoadmap.dateEB.equals(""))) continue;
     if ((theRoadmap.dateTest == null) || (theRoadmap.dateTest.equals(""))) continue;
        if ((theRoadmap.dateProd == null) || (theRoadmap.dateProd.equals(""))) continue;
        
        theRoadmap.idPere= rs.getInt("idRoadmapPere");
        
        theRoadmap.Etat= rs.getString("Etat");
        
        theRoadmap.idPO= rs.getString("idPO");
        
       
       PhaseProjet phaseEB0 = new PhaseProjet("EB0", theRoadmap.DdateT0_init, theRoadmap.DdateEB_init);
       theRoadmap.ListePhases.addElement(phaseEB0);
       PhaseProjet phaseEB = new PhaseProjet("EB", theRoadmap.DdateT0, theRoadmap.DdateEB);
       theRoadmap.ListePhases.addElement(phaseEB);
       
       PhaseProjet phaseDEV0 = new PhaseProjet("DEV0", theRoadmap.DdateEB_init, theRoadmap.DdateTest_init);
       theRoadmap.ListePhases.addElement(phaseDEV0);   
       PhaseProjet phaseDEV = new PhaseProjet("DEV", theRoadmap.DdateEB, theRoadmap.DdateTest);
       theRoadmap.ListePhases.addElement(phaseDEV);       
       
       PhaseProjet phaseTEST0 = new PhaseProjet("TEST0", theRoadmap.DdateTest_init, theRoadmap.DdateProd_init);
       theRoadmap.ListePhases.addElement(phaseTEST0);
       PhaseProjet phaseTEST = new PhaseProjet("TEST", theRoadmap.DdateTest, theRoadmap.DdateProd);
       theRoadmap.ListePhases.addElement(phaseTEST);       
       
       PhaseProjet phaseMES0 = new PhaseProjet("MES0", theRoadmap.DdateProd_init, theRoadmap.DdateMes_init);
       theRoadmap.ListePhases.addElement(phaseMES0);
       PhaseProjet phaseMES = new PhaseProjet("MES", theRoadmap.DdateProd, theRoadmap.DdateMes);
       theRoadmap.ListePhases.addElement(phaseMES);  
       
       theRoadmap.ordreJalon= rs.getInt("ordreJalon");
       //System.out.println(nom + ":: etat= "+theRoadmap.Etat);        
     this.ListeProjets.addElement(theRoadmap);
   }
   num++;
  }
  }
   catch (SQLException s) { }

    return count;
}

  public void getListeProjetsByMonthByJalonByCouleur(String nomBase,Connexion myCnx, Statement st, String anneeRef , int num_mois, String Type, String SiRouge, String SiBleu){
   ResultSet rs;
   String couleur = "";
   if (SiRouge.equals("1")) couleur = "rouge";
   else
     couleur = "bleu";


       if (Type.equals("Mep"))
       {
         req = "SELECT     ListeProjets.nomProjet, ListeProjets.nomSt, ListeProjets.description, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.Suivi, ";
         req += "             ListeProjets.Tendance, ListeProjets.dateMes, ListeProjets.dateT0, ListeProjets.dateTest, ListeProjets.docEB, ListeProjets.docDevis, ListeProjets.idPO, ";
         req += "             TypeSi.couleurSI, idRoadmap, MOE,  MOA";
         req += " FROM         ListeProjets INNER JOIN";
         req +=
             "             TypeSi ON ListeProjets.typeSiVersionSt = TypeSi.idTypeSi";
         req += " WHERE     (MONTH(ListeProjets.dateMep) = " + (num_mois + 1) +
             ") AND (YEAR(ListeProjets.dateMep) = " + anneeRef + ") AND ";
         req += "             (ListeProjets.Panier = 'checked')";
         req += "   AND  (ListeProjets.standby  = 0)";
         req += " AND isMeeting <> 1 ";
         req += " ORDER BY ListeProjets.nomProjet";
       }
       else if (Type.equals("EB"))

       {
         req = "SELECT     ListeProjets.nomProjet, ListeProjets.nomSt, ListeProjets.description, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.Suivi, ";
         req += "             ListeProjets.Tendance, ListeProjets.dateMes, ListeProjets.dateT0, ListeProjets.dateTest, ListeProjets.docEB, ListeProjets.docDevis, ListeProjets.idPO, ";
         req += "             TypeSi.couleurSI, idRoadmap, MOE,  MOA";
         req += " FROM         ListeProjets INNER JOIN";
         req +=
             "             TypeSi ON ListeProjets.typeSiVersionSt = TypeSi.idTypeSi";
         req += " WHERE     (MONTH(ListeProjets.dateEB) = " + (num_mois + 1) +
             ") AND (YEAR(ListeProjets.dateEB) = " + anneeRef + ") AND ";
         req += "             (ListeProjets.Panier = 'checked')";
         req += "   AND  (ListeProjets.standby  = 0)";
         req += "  AND isMeeting <> 1 ";
         req += " ORDER BY ListeProjets.nomProjet";
       }
       else if (Type.equals("Test"))
       {
         req = "SELECT     ListeProjets.nomProjet, ListeProjets.nomSt, ListeProjets.description, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.Suivi, ";
         req += "             ListeProjets.Tendance, ListeProjets.dateMes, ListeProjets.dateT0, ListeProjets.dateTest, ListeProjets.docEB, ListeProjets.docDevis, ListeProjets.idPO, ";
         req += "             TypeSi.couleurSI, idRoadmap, MOE,  MOA";
         req += " FROM         ListeProjets INNER JOIN";
         req +=
             "             TypeSi ON ListeProjets.typeSiVersionSt = TypeSi.idTypeSi";
         req += " WHERE     (MONTH(ListeProjets.dateTest) = " + (num_mois + 1) +
             ") AND (YEAR(ListeProjets.dateTest) = " + anneeRef + ") AND ";
         req += "             (ListeProjets.Panier = 'checked')";
         req += "   AND  (ListeProjets.standby  = 0)";
         req += "  AND isMeeting <> 1 ";
         req += " ORDER BY ListeProjets.nomProjet";
       }
       else if (Type.equals("T0"))
       {
         req = "SELECT     ListeProjets.nomProjet, ListeProjets.nomSt, ListeProjets.description, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.Suivi, ";
         req += "             ListeProjets.Tendance, ListeProjets.dateMes, ListeProjets.dateT0, ListeProjets.dateTest, ListeProjets.docEB, ListeProjets.docDevis, ListeProjets.idPO, ";
         req += "             TypeSi.couleurSI, idRoadmap, MOE,  MOA";
         req += " FROM         ListeProjets INNER JOIN";
         req +=
             "             TypeSi ON ListeProjets.typeSiVersionSt = TypeSi.idTypeSi";
         req += " WHERE     (MONTH(ListeProjets.dateT0) = " + (num_mois + 1) +
             ") AND (YEAR(ListeProjets.dateT0) = " + anneeRef + ") AND ";
         req += "             (ListeProjets.Panier = 'checked')";
         req += "   AND  (ListeProjets.standby  = 0)";
         req += "  AND isMeeting <> 1 ";
         req += " ORDER BY ListeProjets.nomProjet";
       }


   rs = myCnx.ExecReq(st, nomBase, req);
  String nomSt="";
  String nomVersion="";
  String descRoadmap = "";
  String dateT0="";
  String dateEB="";
  String dateTest="";
  String T0="";
  String dateProd = "";
  String dateMes= "";
  String Suivi = "";
  String Tendance = "";
  String docEB = "";
  String docDevis = "";
  String MotsClef = "";
  int idPo = 0;
  int i=1;

  this.ListeProjets.removeAllElements();
  try {
  while(rs.next())
  {
      nomVersion = rs.getString(1);
      if (nomVersion != null) {
        nomVersion = nomVersion.replaceAll("\n", "<br>");
      }
      else
        nomVersion = "";

      nomSt = rs.getString(2);
      nomSt = nomSt.replaceAll("\n", "<br>");
      descRoadmap = rs.getString(3);
      if (descRoadmap != null) {
        descRoadmap = descRoadmap.replaceAll("\n", "<br>");
      }
      else
        descRoadmap = "";
      java.util.Date theDateEB = rs.getDate(4);
      if (theDateEB != null) {
        dateEB = theDateEB.getDate() + "/" + (theDateEB.getMonth() + 1) + "/" +
            (theDateEB.getYear() + 1900);
        if (theDateEB.getYear() + 1900 <= 1995)
          dateEB = "";
      }
      else
        dateEB = "";

      java.util.Date thedateProd = rs.getDate(5);
      if (thedateProd != null) {
        dateProd = thedateProd.getDate() + "/" + (thedateProd.getMonth() + 1) +
            "/" + (thedateProd.getYear() + 1900);
        if (thedateProd.getYear() + 1900 <= 1995)
          dateProd = "";
      }
      else
        dateProd = "";

      Tendance = rs.getString(7);
      if (Tendance != null) {
        Tendance = Tendance.replaceAll("\n", "<br>");

      }
      else
        Tendance = "Inconnue";

      java.util.Date thedateMes = rs.getDate(8);
     // myCnx.trace("*********", "thedateMes", "" + thedateMes);
      if (thedateMes != null) {
        dateMes = thedateMes.getDate() + "/" + (thedateMes.getMonth() + 1) + "/" +
            (thedateMes.getYear() + 1900);
        if (thedateMes.getYear() + 1900 <= 1995)
          dateMes = "";
      }
      else
        dateMes = "";

      java.util.Date theDateT0 = rs.getDate("dateT0");
      if (theDateT0 != null) {
        dateT0 = theDateT0.getDate() + "/" + (theDateT0.getMonth() + 1) + "/" +
            (theDateT0.getYear() + 1900);
        if (theDateT0.getYear() + 1900 <= 1995)
          dateT0 = "";
      }
      else
        dateT0 = "";

      java.util.Date theDateTest = rs.getDate("dateTest");
      if (theDateTest != null) {
        dateTest = theDateTest.getDate() + "/" + (theDateTest.getMonth() + 1) +
            "/" + (theDateTest.getYear() + 1900);
        if (theDateTest.getYear() + 1900 <= 1995)
          dateTest = "";
      }
      else
        dateTest = "";

      docEB = rs.getString("docEB");
      docDevis = rs.getString("docDevis");

      //MotsClef = rs.getString("MotsClef");
      MotsClef = "";

      idPo = rs.getInt("idPO");
      int idRoadmap = rs.getInt("idRoadmap");

      Roadmap theRoadmap = new Roadmap (
                   nomSt,
                   "-1", //idVersionSt
                   "", //oldversion
                   nomVersion,
                   descRoadmap,
                   "", //EtatRoadmap
                   "", //Panier
                   ""+idPo,
                   "", //Charge
                   "", //Ordre
                   dateEB,
                   dateProd,
                   dateMes,
                   Suivi,
                   Tendance,
                   dateT0,
                   dateTest,
                   docEB,
                   docDevis,
                   Integer.parseInt(anneeRef),
                   MotsClef
                  );
      theRoadmap.id=idRoadmap;
      theRoadmap.MOE = rs.getString("MOE");
      if (theRoadmap.MOE == null) theRoadmap.MOE="";
      theRoadmap.MOA = rs.getString("MOA");
       if (theRoadmap.MOA == null) theRoadmap.MOA="";

      this.ListeProjets.addElement(theRoadmap);

      i++;
    }
  }
  catch (SQLException s) {s.getMessage(); }
}

private String getClauseWhere(String ClausePeriode){

  if (ClausePeriode.equals("")) return " AND (Panier='checked')";
  String ClauseWhere = "";
  
  String[] ligneClauseWhere = ClausePeriode.split(";");
  for (int i=0;i < ligneClauseWhere.length; i++)
  {
      if (i == 0) ClauseWhere = " AND (";
      ClauseWhere += "(idJalon= "+ligneClauseWhere[i] + ")";
      if ((i != ligneClauseWhere.length -1)) ClauseWhere += " OR ";
  }
  
  ClauseWhere += ")";

  ClauseWhere+=" AND (Panier='checked')";
  return ClauseWhere;

}

private String getClauseWhere(String AD,String T0, String EB,String UAT,String PROD,String MES){

  String ClauseWhere = "";
  //if (T0.equals("0") && !EB.equals("0") && !UAT.equals("0") && !PROD.equals("0")) return "";

  if (AD.equals("1") ||T0.equals("1") || EB.equals("1") || UAT.equals("1") || PROD.equals("1") || MES.equals("1")) ClauseWhere = "AND (";

  if  (AD.equals("1"))
    {

      ClauseWhere += "((Etat = 'a demarrer') )";
    }

  if  (T0.equals("1"))
    {

      if (!ClauseWhere.equals( "AND (")) ClauseWhere +=" OR ";
        ClauseWhere += "(Etat = 'T0')";
    }

    if  (EB.equals("1"))
      {
        if (!ClauseWhere.equals( "AND (")) ClauseWhere +=" OR ";
        ClauseWhere += "(Etat = 'EB')";
    }
    if  (UAT.equals("1"))
      {
        if (!ClauseWhere.equals( "AND (")) ClauseWhere +=" OR ";
        ClauseWhere += "(Etat = 'TEST')";
    }
    if  (PROD.equals("1"))
      {
        if (!ClauseWhere.equals( "AND (")) ClauseWhere +=" OR ";
        ClauseWhere += "(Etat = 'PROD')";
    }   
    if  (MES.equals("1"))
      {
        if (!ClauseWhere.equals( "AND (")) ClauseWhere +=" OR ";
        ClauseWhere += "(Etat = 'MES')";
    }      


    if (AD.equals("1") || T0.equals("1") || EB.equals("1") || UAT.equals("1") || PROD.equals("1") || MES.equals("1")) ClauseWhere += ")";

    ClauseWhere+=" AND (Panier='checked')";
    return ClauseWhere;

}

   public int getnbProjetsByMonthByColor(String nomBase,Connexion myCnx, Statement st, int theYearRef, int month, int couleurSI ){

     int nbProjets = 0;
     String req = "";

     Calendar calendar = Calendar.getInstance();
     int theMonthRef = calendar.get(Calendar.MONTH) +1;

     if (month >= theMonthRef)
     {
       req = "SELECT     COUNT(typeSiVersionSt) AS nb";
       req += "    FROM         ListeProjets";
       req += "  WHERE     (Panier = 'checked') AND  (standby  = 0) AND (isMeeting <> 1) AND (MONTH(dateMep) = " +
           month + ") AND (YEAR(dateMep) = " + theYearRef +
           ") AND (typeSiVersionSt = " + couleurSI + ")";
     }
     else
     {
       req = "SELECT     COUNT(typeSiVersionSt) AS nb";
       req+="    FROM         ListeProjets";
       req+=" WHERE     (Panier = 'checked') AND (standby = 0) AND (isMeeting <> 1) AND (MONTH(dateMep) = "+month+") AND (YEAR(dateMep) = "+theYearRef+") AND (typeSiVersionSt = "+couleurSI+") AND ";
       req+="                (Etat = 'PROD' OR";
       req+="                Etat = 'MES')";


     }
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        nbProjets = rs.getInt(1);
          } catch (SQLException s) {s.getMessage();}
     return nbProjets;

}
   
   public int getnbProjetsByMonth(String nomBase,Connexion myCnx, Statement st, int theYearRef, int month ){

     int nbProjets = 0;
     String req = "";

     Calendar calendar = Calendar.getInstance();
     int theMonthRef = calendar.get(Calendar.MONTH) +1;

     if (month >= theMonthRef)
     {
       req = "SELECT     COUNT(typeSiVersionSt) AS nb";
       req += "    FROM         ListeProjets";
       req += "  WHERE     (Panier = 'checked') AND  (standby  = 0) AND (isMeeting <> 1) AND (MONTH(dateMep) = " +
           month + ") AND (YEAR(dateMep) = " + theYearRef +
           ")";
     }
     else
     {
       req = "SELECT     COUNT(typeSiVersionSt) AS nb";
       req+="    FROM         ListeProjets";
       req+=" WHERE     (Panier = 'checked') AND (standby = 0) AND (isMeeting <> 1) AND (MONTH(dateMep) = "+month+") AND (YEAR(dateMep) = "+theYearRef+")  AND ";
       req+="                (Etat = 'PROD' OR";
       req+="                Etat = 'MES')";


     }
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        nbProjets = rs.getInt(1);
          } catch (SQLException s) {s.getMessage();}
     return nbProjets;

}   

public int getnbProjetsByMonthByColor2(String nomBase,Connexion myCnx, Statement st, int theYearRef, int month, int couleurSI ){

     int nbProjets = 0;
     String req = "SELECT     COUNT(typeSiVersionSt) AS nb";
     req+="    FROM         ListeProjets";
     req+="  WHERE     (Panier = 'checked') AND  (standby  = 0) AND (isMeeting <> 1) AND (MONTH(dateMep) = "+month+") AND (YEAR(dateMep) = "+theYearRef+") AND (typeSiVersionSt = "+couleurSI+")";

      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        nbProjets = rs.getInt(1);
          } catch (SQLException s) {s.getMessage();}
     return nbProjets;

}

public int getCouleurByNom(String nomBase,Connexion myCnx, Statement st, String nom ){

     int nbProjets = 0;
     String req = "SELECT     TypeSi.idTypeSi";
     req+=" FROM         TypeSi INNER JOIN";
     req+="                       SI ON TypeSi.siTypesi = SI.idSI";
     req+=" WHERE     (TypeSi.nomTypeSi = '"+nom+"') AND (TypeSi.siTypesi = "+this.id+")";

      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        nbProjets = rs.getInt(1);
          } catch (SQLException s) {s.getMessage();}
     return nbProjets;

}


 public void getListeProjets_Commun(String nomBase,Connexion myCnx, Statement st, int anneeRef, String theReq ){
   ResultSet rs;
   String nom="";

   String dateT0="";
   String dateEB="";
   String dateTEST="";
   String datePROD="";
   String dateMES="";

   String dateT0MOE="";
   String dateSpecMOE="";
   String dateDevMOE="";
   String dateTestMOE="";
   String dateLivrMOE="";

   int idVersionSt = -1;
   String version= "";
   Date theDate = null;

   rs = myCnx.ExecReq(st, myCnx.nomBase, theReq);

   try {
     while (rs.next()) {
       nom = rs.getString("nomProjet");
       dateEB = "";
       datePROD = "";
       dateMES = "";
       idVersionSt = rs.getInt("idVersionSt");
       version = rs.getString("version");
       theDate = rs.getDate("dateEB");
       dateEB = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateMep");
       datePROD = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateMes");
       dateMES = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateT0");
       dateT0 = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateTest");
       dateTEST = Utils.getDateFrench(theDate);

       theDate = rs.getDate("dateT0MOE");
       dateT0MOE = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateSpecMOE");
       dateSpecMOE = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateDevMOE");
       dateDevMOE = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateTestMOE");
       dateTestMOE = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateLivrMOE");
       dateLivrMOE = Utils.getDateFrench(theDate);


       Roadmap theRoadmap = new Roadmap(-1);
       theRoadmap.nomSt=nom;
       theRoadmap.dateT0=dateT0;
       theRoadmap.dateEB=dateEB;
       theRoadmap.dateTest=dateTEST;
       theRoadmap.dateProd=datePROD;
       theRoadmap.dateMes=dateMES;

       theRoadmap.dateT0MOE=dateT0MOE;
       theRoadmap.dateSpecMOE=dateSpecMOE;
       theRoadmap.dateDevMOE=dateDevMOE;
       theRoadmap.dateTestMOE=dateTestMOE;
       theRoadmap.dateLivrMOE=dateLivrMOE;

       theRoadmap.idVersionSt=""+idVersionSt;
       theRoadmap.version=version;

       theDate = rs.getDate("dateT0_init");
       theRoadmap.dateT0_init = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateEB_init");
       theRoadmap.dateEB_init = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateTest_init");
       theRoadmap.dateTest_init = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateMep_init");
       theRoadmap.dateProd_init = Utils.getDateFrench(theDate);
       theDate = rs.getDate("dateMes_init");
       theRoadmap.dateMes_init = Utils.getDateFrench(theDate);
       theRoadmap.id= rs.getInt("idRoadmap");
       theRoadmap.idPO= ""+rs.getInt("idPO");
       theRoadmap.standby= rs.getInt("standby");
       theRoadmap.idBrief= rs.getInt("idBrief");

       //theProjet.dump();
       if (theRoadmap.standby == 1) continue;
       
       theRoadmap.nbDependances= rs.getInt("nbDependances");
       theRoadmap.nbActions= rs.getInt("nbActions");       

       if (dateT0MOE == null) theRoadmap.dateT0MOE="";
       if (dateSpecMOE == null) theRoadmap.dateSpecMOE="";
       if (dateDevMOE == null) theRoadmap.dateDevMOE="";
       if (dateTestMOE == null) theRoadmap.dateTestMOE="";
       if (dateLivrMOE == null) theRoadmap.dateLivrMOE="";

       if ((theRoadmap.dateT0 == null) || (theRoadmap.dateT0.equals(""))) continue;
       if ((theRoadmap.dateEB == null) || (theRoadmap.dateEB.equals(""))) continue;
       if ((theRoadmap.dateTest == null) || (theRoadmap.dateTest.equals(""))) continue;
       if ((theRoadmap.dateProd == null) || (theRoadmap.dateProd.equals(""))) continue;

       this.ListeProjets.addElement(theRoadmap);
     }
   }
catch (SQLException s) { }
}
 public void getListeStAnciennete(String nomBase,Connexion myCnx, Statement st, String orderBy, String sens , String nomTypeSi){

   if (nomTypeSi.equals("all"))
   {
     req = "SELECT     ListeST.idVersionSt, ListeST.nomSt, Membre.nomMembre, Membre.prenomMembre, Membre_1.nomMembre AS Expr1, ";
     req += "                      Membre_1.prenomMembre AS Expr2, ListeST.creationVersionSt, ListeST.derMajVersionSt, ListeST.siVersionSt, TypeSi.nomTypeSi";
     req += " FROM         ListeST INNER JOIN";
     req += "                      Membre ON ListeST.respMoeVersionSt = Membre.idMembre INNER JOIN";
     req += "                      Membre AS Membre_1 ON ListeST.respMoaVersionSt = Membre_1.idMembre INNER JOIN";
     req +=
         "                      TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
     req += " WHERE     (ListeST.siVersionSt = " + this.id + ")";
     req += "ORDER BY " + orderBy + " " + sens;
   }
   else
   {
     req = "SELECT     ListeST.idVersionSt, ListeST.nomSt, Membre.nomMembre, Membre.prenomMembre, Membre_1.nomMembre AS Expr1, ";
     req += "                      Membre_1.prenomMembre AS Expr2, ListeST.creationVersionSt, ListeST.derMajVersionSt, ListeST.siVersionSt, TypeSi.nomTypeSi";
     req += " FROM         ListeST INNER JOIN";
     req += "                      Membre ON ListeST.respMoeVersionSt = Membre.idMembre INNER JOIN";
     req += "                      Membre AS Membre_1 ON ListeST.respMoaVersionSt = Membre_1.idMembre INNER JOIN";
     req +=
         "                      TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
     req += " WHERE     (ListeST.siVersionSt = " + this.id + ") AND (TypeSi.nomTypeSi = '" + nomTypeSi + "')";
     req += "ORDER BY " + orderBy + " " + sens;
   }
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   ST theST = null;
   try {
      while (rs.next()) {
        theST = new ST(rs.getInt(1), "idVersionSt");
        theST.nomSt = rs.getString(2);
        theST.nomMOE = rs.getString(3);
        theST.prenomMOE = rs.getString(4);
        theST.nomMOA = rs.getString(5);
        theST.prenomMOA = rs.getString(6);
        theST.creationVersionSt = rs.getDate(7);
        theST.derMajVersionSt = rs.getDate(8);

        //theST.dump();
        this.ListeSt.addElement(theST);
      }
       } catch (SQLException s) {s.getMessage();}

 }

 public void getListeSt(String nomBase,Connexion myCnx, Statement st){

   req = "SELECT     idVersionSt, nomSt, descVersionSt, nomMOA, nomMOE, nomTypeSi, Logo";
   req+="    FROM         ST_ListeSt_All_Attribute";
   req+=" WHERE     (etatFicheVersionSt = 3) AND (etatVersionSt = 3) AND (siVersionSt = "+this.id+")";
   req+=" ORDER BY nomSt";

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   ST theST = null;
   try {
      while (rs.next()) {
        theST = new ST(rs.getInt("idVersionSt"), "idVersionSt");
        theST.nomSt = rs.getString("nomSt");
        theST.descVersionSt = rs.getString("descVersionSt");
        theST.nomMOA = rs.getString("nomMOA");
        theST.nomMOE = rs.getString("nomMOE");
        theST.couleur = rs.getString("nomTypeSi");
        theST.logo = rs.getString("logo");

        //theST.dump();
        this.ListeSt.addElement(theST);
      }
       } catch (SQLException s) {s.getMessage();}

 }
 
  public void getListeStAll(String nomBase,Connexion myCnx, Statement st){

   req = "SELECT     idVersionSt, nomSt, descVersionSt, nomMOA, nomMOE, nomTypeSi, Logo";
   req+="    FROM         ST_ListeSt_All_Attribute";
   req+=" WHERE     (etatFicheVersionSt = 3) AND (etatVersionSt = 3)";
   req+=" ORDER BY nomSt";

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   ST theST = null;
   try {
      while (rs.next()) {
        theST = new ST(rs.getInt("idVersionSt"), "idVersionSt");
        theST.nomSt = rs.getString("nomSt");
        theST.descVersionSt = rs.getString("descVersionSt");
        theST.nomMOA = rs.getString("nomMOA");
        theST.nomMOE = rs.getString("nomMOE");
        theST.couleur = rs.getString("nomTypeSi");
        theST.logo = rs.getString("logo");

        //theST.dump();
        this.ListeSt.addElement(theST);
      }
       } catch (SQLException s) {s.getMessage();}

 }
 public void getListeStRad(String nomBase,Connexion myCnx, Statement st, Statement st2 ){
this.ListeSt.removeAllElements();
   req = "SELECT     VersionSt.idVersionSt,St.nomSt";
   req+=" FROM         St INNER JOIN";
   req+="                      VersionSt ON St.idSt = VersionSt.stVersionSt";
   req+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND ((VersionSt.serviceMoeVersionSt = 205) OR (VersionSt.serviceMoeVersionSt = 191) OR (VersionSt.serviceMoeVersionSt = 285)  OR (VersionSt.serviceMoeVersionSt = 206) OR ( VersionSt.serviceMoeVersionSt = 207)) AND (St.isEquipement <> 1) AND (St.isComposant <> 1) AND (St.isActeur <> 1) AND ";
   req+="                       (St.isRecurrent <> 1) AND (St.isMeeting <> 1) AND (NOT (St.nomSt LIKE '---%')) AND (St.Criticite = 'Critique')";
   req+=" order by St.nomSt";
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   ST theST = null;
   try {
      while (rs.next()) {
        theST = new ST(rs.getInt(1), "idVersionSt");
        theST.nomSt = rs.getString(2);
        //theST.dump();
        if (theST.nomSt.equals("Webarc"))
        {
            int myStop=1;
        }
        Roadmap theRoadmap=theST.getRoadmapFaqRad(nomBase, myCnx, st2 );
        if (theRoadmap == null)
            this.ListeSt.addElement(theST);
      }
       } catch (SQLException s) {s.getMessage();}

 }
 
  public void getListeFaqRad(String nomBase,Connexion myCnx, Statement st ){
   this.ListeProjets.removeAllElements();
   req = "SELECT     idRoadmap, version FROM         Roadmap WHERE     (isFAQ = 1)";
   req+=" AND idRoadmap NOT IN (SELECT     BesoinsUtilisateur.idRoadmap FROM         BesoinsUtilisateur INNER JOIN    Roadmap ON BesoinsUtilisateur.idRoadmap = Roadmap.idRoadmap WHERE     (Roadmap.isFAQ = 1))";

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   Roadmap theRoadmap = null;
   try {
      while (rs.next()) {
        theRoadmap = new Roadmap(rs.getInt(1));

        this.ListeProjets.addElement(theRoadmap);
      }
       } catch (SQLException s) {s.getMessage();}

 }
  
  public static void getListePlanTypeFaq(String nomBase,Connexion myCnx, Statement st ){

   String req = "SELECT     id, nom, nbNiveau, niv1, niv2, niv3, niv4, chapitre FROM PlanTypeFAQ";
   ListeExigences.removeAllElements();
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   Besoin theExigence = null;
   try {
      while (rs.next()) {
        theExigence = new Besoin(rs.getInt(1));
        theExigence.nom = rs.getString("nom");
        theExigence.nbNiveau = rs.getInt("nbNiveau");
        theExigence.niv1 = rs.getInt("niv1");
        theExigence.niv2 = rs.getInt("niv2");
        theExigence.niv3 = rs.getInt("niv3");
        theExigence.niv4 = rs.getInt("niv4");
        theExigence.chapitre = rs.getString("chapitre");

        ListeExigences.addElement(theExigence);
      }
       } catch (SQLException s) {s.getMessage();}

 }  
  
  public ErrorSpecific bd_InsertFaqRadType(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
     ErrorSpecific myError = new ErrorSpecific();
        for (int j=0; j < this.ListeProjets.size(); j++)
        {
            Roadmap theRoadmap = (Roadmap)this.ListeProjets.elementAt(j);
            myError=theRoadmap.bd_InsertFaqRadType(nomBase, myCnx, st, transaction);
            if (myError.cause == -1) return myError;
        }
     
     return myError;

  }  
  
  
  public ErrorSpecific bd_InsertFaqRad(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();

     for (int i=0; i < this.ListeSt.size(); i++)
     {
       ST theST = (ST)this.ListeSt.elementAt(i);

        myError=theST.bd_InsertFaqRad(nomBase, myCnx, st, transaction);
        if (myError.cause == -1) return myError;
        
        myCnx.trace("bd_InsertFaqRad--------"+i,":: theST.nomSt",""+theST.nomSt);
       
     }
 
     return myError;

  } 

 public void getListeSt(String nomBase,Connexion myCnx, Statement st ,Statement st2){

   req = "exec SI_SelectVersionStSi '"+ this.nom+"'";
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   ST theST = null;
   try {
      while (rs.next()) {
        theST = new ST(rs.getInt(1), "idVersionSt");
        theST.nomSt = rs.getString(2);
        theST.idSt = rs.getInt(3);
        theST.descVersionSt = rs.getString(6);
        theST.nbLigCodeVersionSt = rs.getInt(7);
        theST.nbPtsFctVersionSt = rs.getInt(8);
        theST.getNbModules(nomBase,myCnx, st2);
        theST.getNbInterface(nomBase,myCnx, st2);
        //theST.dump();
        this.ListeSt.addElement(theST);
      }
       } catch (SQLException s) {s.getMessage();}

 }

 public void getListeServices(String nomBase,Connexion myCnx, Statement st){

   req = "SELECT     idService, nomService";
   req+=" FROM         Service";
   req+=" WHERE     (siService = "+this.id+") order by nomService";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  Service theService = null;
  try {
     while (rs.next()) {
       theService = new Service(rs.getInt("idService"));
       theService.nom = rs.getString("nomService");
       this.ListeServices.addElement(theService);
     }
      } catch (SQLException s) {s.getMessage();}

}
 
  public void getListeServicesByType(String nomBase,Connexion myCnx, Statement st, String Type){

  this.ListeServices.removeAllElements();
   req = "SELECT     idService, nomService";
   req+=" FROM         Service";
   
   if (Type.equals("MOE"))
      req+=" WHERE     (isMoe = 1)";
   else if (Type.equals("MOA"))
      req+=" WHERE     (isMoa = 1)";   
   else if (Type.equals("GOUVERNANCE"))
      req+=" WHERE     (isGouvernance = 1)";   
   else if (Type.equals("EXPLOITATION"))
      req+=" WHERE     (isExploitation = 1)";   
   
   req+=" AND (sendRetard = 1)";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  Service theService = null;
  try {
     while (rs.next()) {
       theService = new Service(rs.getInt("idService"));
       theService.nom = rs.getString("nomService");
       this.ListeServices.addElement(theService);
     }
      } catch (SQLException s) {s.getMessage();}

}
  
  public void getListeServicesRoot(String nomBase,Connexion myCnx, Statement st){

  this.ListeServices.removeAllElements();
   req = "SELECT DISTINCT Service.idServicePere, Service_1.nomService";
   req+= " FROM         Service INNER JOIN";
   req+= "                       Service AS Service_1 ON Service.idServicePere = Service_1.idService";
   req+= " WHERE     (Service.sendRetard = 1) AND (Service.isMoe=1 OR";
   req+= "                       Service.isMoa=1)";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  Service theService = null;
  try {
     while (rs.next()) {
       theService = new Service(rs.getInt("idServicePere"));
       theService.nom = rs.getString("nomService");
       this.ListeServices.addElement(theService);
     }
      } catch (SQLException s) {s.getMessage();}

}  
  
  


 public static void getListeProcessus(String nomBase,Connexion myCnx, Statement st){

   String req="SELECT nomSI, nomProcessus FROM   SI WHERE nomSI!='Acteurs'";

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   ListeProcessus.removeAllElements();
   Processus theProcessus = null;
   try {
      while (rs.next()) {
        theProcessus = new Processus();
        theProcessus.nomSi = rs.getString(1);
        theProcessus.nom = rs.getString(2);
        ListeProcessus.addElement(theProcessus);
      }
       } catch (SQLException s) {s.getMessage();}

 }
 public void getListeSt(String nomBase,Connexion myCnx, Statement st, String orderBy, String sens){

   req+="SELECT     ListeST.idVersionSt,ListeST.nomSt,  ListeST.idSt,ListeST.Criticite, ListeST.nbLigCodeVersionSt, ListeST.nbUtilVersionSt ";
   req+="FROM         ListeST INNER JOIN ";
   req+="                      TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi ";
   req+="WHERE     (TypeSi.nomTypeSi = 'SI Bleu') AND (ListeST.siVersionSt = "+this.id+") ";
   req+="ORDER BY ListeST."+orderBy+ " "+sens;

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   ST theST = null;
   try {
      while (rs.next()) {
        theST = new ST(rs.getInt(1), "idVersionSt");
        theST.nomSt = rs.getString(2);
        theST.idSt = rs.getInt(3);
        theST.Criticite = rs.getString(4);
        theST.descVersionSt = "";
        theST.nbLigCodeVersionSt = rs.getInt(5);
        theST.nbUtilVersionSt = rs.getInt(6);
        theST.nbPtsFctVersionSt = 0;
        //theST.dump();
        this.ListeSt.addElement(theST);
      }
       } catch (SQLException s) {s.getMessage();}

 }

 public MacroSt getMacroStByNom(String nom){
   MacroSt theMacroSt=null;

   for (int i=0; i < this.ListeMacroSt.size();i++){
     theMacroSt = (MacroSt)this.ListeMacroSt.elementAt(i);
     if (theMacroSt.nom.equals(nom)) return theMacroSt;
   }
   return    theMacroSt;

 }

 public Phase getPhaseNPByNom(String nom){
   Phase thePhase=null;

   for (int i=0; i < this.ListePhasesNP.size();i++){
     thePhase = (Phase)this.ListePhasesNP.elementAt(i);
     if (thePhase.nom.equals(nom)) return thePhase;
   }
   return    thePhase;

 }

 public void getListeMacroSt(String nomBase,Connexion myCnx, Statement st){

   this.ListeMacroSt.removeAllElements();
   String req="SELECT     MacroSt.idMacrost, MacroSt.nomMacrost";
   req+=" FROM         MacroSt INNER JOIN";
   req+="                       SI ON MacroSt.siMacrost = SI.idSI";
   req+=" WHERE     (SI.nomSI = '"+this.nom+"') order by nomMacrost asc";

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   MacroSt theMacroSt = null;
   try {
      while (rs.next()) {
        theMacroSt = new MacroSt(rs.getInt(1));
        theMacroSt.nom = rs.getString(2);
        this.ListeMacroSt.addElement(theMacroSt);
      }
       } catch (SQLException s) {s.getMessage();}

 }
 
  public  void getListeMacroStAccesRapide(String nomBase,Connexion myCnx, Statement st){

   this.ListeMacroSt.removeAllElements();
   String req="SELECT     MacroSt.idMacrost, MacroSt.nomMacrost";
   req+=" FROM         MacroSt INNER JOIN";
   req+="                       SI ON MacroSt.siMacrost = SI.idSI";
   req+=" WHERE     (MacroSt.CartoBulle = 'yes') order by nomMacrost asc";

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   MacroSt theMacroSt = null;
   try {
      while (rs.next()) {
        theMacroSt = new MacroSt(rs.getInt(1));
        theMacroSt.nom = rs.getString(2);
        this.ListeMacroSt.addElement(theMacroSt);
      }
       } catch (SQLException s) {s.getMessage();}

 }

 public void getListeMacroStById(String nomBase,Connexion myCnx, Statement st){

  this.ListeMacroSt.removeAllElements();
  String req="SELECT     MacroSt.idMacrost, MacroSt.nomMacrost";
  req+=" FROM         MacroSt INNER JOIN";
  req+="                       SI ON MacroSt.siMacrost = SI.idSI";
  req+=" WHERE     (SI.idSI = '"+this.id+"') order by nomMacrost asc";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  MacroSt theMacroSt = null;
  try {
     while (rs.next()) {
       theMacroSt = new MacroSt(rs.getInt(1));
       theMacroSt.nom = rs.getString(2);
       this.ListeMacroSt.addElement(theMacroSt);
     }
      } catch (SQLException s) {s.getMessage();}

}

public void getListeTypeSi(String nomBase,Connexion myCnx, Statement st){

     this.ListeTypeSi.removeAllElements();
     String req="SELECT      idTypeSi, nomTypeSi";
     req+="    FROM         TypeSi";
     req+="     WHERE     (siTypesi = "+this.id+")";


     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     TypeSi theTypeSi = null;
     try {
        while (rs.next()) {
          theTypeSi = new TypeSi(rs.getInt(1));
          theTypeSi.nom = rs.getString(2);
          this.ListeTypeSi.addElement(theTypeSi);
        }
         } catch (SQLException s) {s.getMessage();}

}


 public void getListePhasesNP(String nomBase,Connexion myCnx, Statement st){

  String req = "SELECT     PhaseNP.idPhaseNP, PhaseNP.nomPhaseNP";
  req+=" FROM         SI INNER JOIN";
  req+="                        PhaseNP ON SI.idSI = PhaseNP.siPhaseNp";
  req+=" WHERE     (SI.nomSI = '"+this.nom+"')";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  Phase thePhase = null;
  try {
     while (rs.next()) {
       thePhase = new Phase ("",rs.getInt(1),-1,rs.getString(2),"",-1);
       this.ListePhasesNP.addElement(thePhase);
     }
      } catch (SQLException s) {s.getMessage();}

}

public void getListePhasesNPById(String nomBase,Connexion myCnx, Statement st){

     String req = "SELECT     PhaseNP.idPhaseNP, PhaseNP.nomPhaseNP";
     req+=" FROM         SI INNER JOIN";
     req+="                        PhaseNP ON SI.idSI = PhaseNP.siPhaseNp";
     req+=" WHERE     (SI.idSI = '"+this.id+"')";

     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     Phase thePhase = null;
     try {
        while (rs.next()) {
          thePhase = new Phase ("",rs.getInt(1),-1,rs.getString(2),"",-1);
          this.ListePhasesNP.addElement(thePhase);
        }
         } catch (SQLException s) {s.getMessage();}

}

    public void getListeMOE(String nomBase,Connexion myCnx, Statement st){

      String req = "SELECT      Service.nomService, Service.idService,Service.typeService";
          req += " FROM         St INNER JOIN";
          req += "             VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
          req += "             SI ON VersionSt.siVersionSt = SI.idSI INNER JOIN";
          req += "             Service ON VersionSt.serviceMoeVersionSt = Service.idService";
          req += " WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (VersionSt.versionRefVersionSt =";
          req += "                 (SELECT     MAX(versionRefVersionSt) AS Expr1";
          req += "                   FROM          VersionSt AS VersionSt_1";
          req += "                   WHERE      (etatFicheVersionSt = 3) AND (stVersionSt = St.idSt))) AND (SI.nomSI = '"+this.nom+"')";
          req += " GROUP BY Service.nomService, Service.idService, Service.typeService";
          req += " ORDER BY Service.nomService";


     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     Service theService = null;
     try {
        while (rs.next()) {
          String  nomService= rs.getString("nomService");
          theService = new Service (nomService);
          theService.id = rs.getInt("idService");
          theService.type = rs.getString("typeService");

          this.ListeMOE.addElement(theService);
        }
         } catch (SQLException s) {s.getMessage();}

}


    public void getListeMOA(String nomBase,Connexion myCnx, Statement st){

      String req = "SELECT     Service.nomService, Service.idService ";
          req+=" FROM         St INNER JOIN";
          req+="              VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
          req+="              SI ON VersionSt.siVersionSt = SI.idSI INNER JOIN";
          req+="              Service ON VersionSt.serviceMoaVersionSt = Service.idService";
          req+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (VersionSt.versionRefVersionSt =";
          req+="                  (SELECT     MAX(versionRefVersionSt) AS Expr1";
          req+="                    FROM          VersionSt AS VersionSt_1";
          req+="                    WHERE      (etatFicheVersionSt = 3) AND (stVersionSt = St.idSt))) AND (SI.nomSI = '"+this.nom+"')";
          req+=" GROUP BY Service.nomService, Service.idService";
          req+=" ORDER BY Service.nomService";


     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     Service theService = null;
     try {
        while (rs.next()) {
          String  nomService= rs.getString("nomService");
          theService = new Service (nomService);
          theService.id = rs.getInt("idService");

          this.ListeMOA.addElement(theService);
        }
         } catch (SQLException s) {s.getMessage();}

}


 public static void getListeSI(String nomBase,Connexion myCnx, Statement st){

 String req="EXEC LISTETYPESI_SelectTypeSi";
 ListeSI.removeAllElements();

 ResultSet rs = myCnx.ExecReq(st, nomBase, req);
 SI theSI = null;
 try {
    while (rs.next()) {
      int idTypeSi = rs.getInt(1);
      String nomSI = rs.getString(2);
      int idSi = rs.getInt(3);

      theSI = new SI (idSi);
      theSI.nom = nomSI;
      theSI.Type = ""+idTypeSi;

      ListeSI.addElement(theSI);
    }
     } catch (SQLException s) {s.getMessage();}

}

public static void getListeSIByTypeSt(String nomBase,Connexion myCnx, Statement st, String isST, String isAppli, String isEquipement, String isComposant, String isActeur){

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


  String req="SELECT     idSI, nomSI, isEquipement, isComposant, isSt, isAppli, isActeur";
      req+=" FROM         SI";
      req+=ClauseWhere;
      req+=" ORDER BY nomSI";

   ListeSI.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   SI theSI = null;
   try {
      while (rs.next()) {
        int idSi = rs.getInt(1);
        String nomSI = rs.getString(2);

        theSI = new SI (idSi);
        theSI.nom = nomSI;

        ListeSI.addElement(theSI);
      }
       } catch (SQLException s) {s.getMessage();}

}

   public void getNbStbyMOEbyType(String nomBase,Connexion myCnx, Statement st){
     for (int i=0; i < this.ListeMOE.size(); i++)
     {
       Service theService = (Service)this.ListeMOE.elementAt(i);
       theService.getSiBleuMOE( nomBase, myCnx,  st, this.nom);
       theService.getSiRougeMOE( nomBase, myCnx,  st, this.nom);
       theService.getSiVioletMOE( nomBase, myCnx,  st, this.nom);
       theService.getAppliRougeMOE( nomBase, myCnx,  st, this.nom);
    }

 }

 public void getNbStbyMOEbyType2(String nomBase,Connexion myCnx, Statement st){
   req = "exec SI_nbStSITypeSiMOE '"+this.nom+ "'";
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   String oldMOE="";
   Service theMOE = null;
   try {
      while (rs.next()) {
        String nomMOE = rs.getString(1);
        String TypeSi = rs.getString(2);
        int nbSt = rs.getInt(3);

        if (!oldMOE.equals(nomMOE))
          {
            if (theMOE != null) this.ListeMOE.addElement(theMOE);
            theMOE = new Service(nomMOE);
          }
        if (TypeSi.equals("SI Rouge")) theMOE.nbStRouge = nbSt; else theMOE.nbStBleu = nbSt;

        oldMOE = nomMOE;
      }
       } catch (SQLException s) {s.getMessage();}

 }

 public void getNbStbyMOAbyType(String nomBase,Connexion myCnx, Statement st){
   for (int i=0; i < this.ListeMOA.size(); i++)
   {
     Service theService = (Service)this.ListeMOA.elementAt(i);
     theService.getSiBleuMOA( nomBase, myCnx,  st, this.nom);
     theService.getSiVioletMOA( nomBase, myCnx,  st, this.nom);
     theService.getSiRougeMOA( nomBase, myCnx,  st, this.nom);
     theService.getAppliRougeMOA( nomBase, myCnx,  st, this.nom);
    }

 }

 public void getNbStbyMOAbyType2(String nomBase,Connexion myCnx, Statement st){
   req = "exec SI_nbStSITypeSiMOA '"+this.nom+ "'";
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   String oldMOA="";
   Service theMOA = null;
   try {
      while (rs.next()) {
        String nomMOA = rs.getString(1);
        String TypeSi = rs.getString(2);
        int nbSt = rs.getInt(3);

        if (!oldMOA.equals(nomMOA))
          {
            if (theMOA != null) this.ListeMOA.addElement(theMOA);
            theMOA = new Service(nomMOA);
          }
        if (TypeSi.equals("SI Rouge")) theMOA.nbStRouge = nbSt; else theMOA.nbStBleu = nbSt;

        oldMOA = nomMOA;
      }
       } catch (SQLException s) {s.getMessage();}

 }

 public void getNbStbyPhaseNP(String nomBase,Connexion myCnx, Statement st){
   for (int i=0; i < this.ListePhasesNP.size(); i++)
   {
     Phase thePhasesNP = (Phase)this.ListePhasesNP.elementAt(i);
     thePhasesNP.getSiBleu( nomBase, myCnx,  st, this.nom);
     thePhasesNP.getSiRouge( nomBase, myCnx,  st, this.nom);
     thePhasesNP.getAppliRouge( nomBase, myCnx,  st, this.nom);
    }
 }

 public void getNbStbyPhaseNP2(String nomBase,Connexion myCnx, Statement st){
   req = "exec SI_nbStSITypeSiPhaseNP '"+this.nom+ "'";
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   String oldPhaseNP="";
   Service thePhaseNP = null;
   try {
      while (rs.next()) {
        String nomhaseNP = rs.getString(1);
        String TypeSi = rs.getString(2);
        int nbSt = rs.getInt(3);

        if (!oldPhaseNP.equals(nomhaseNP))
          {
            if (thePhaseNP != null) this.ListePhasesNP.addElement(thePhaseNP);
            thePhaseNP = new Service(nomhaseNP);
          }
        if (TypeSi.equals("SI Rouge")) thePhaseNP.nbStRouge = nbSt; else thePhaseNP.nbStBleu = nbSt;

        oldPhaseNP = nomhaseNP;
      }
       } catch (SQLException s) {s.getMessage();}
       if (thePhaseNP != null) this.ListePhasesNP.addElement(thePhaseNP);
 }

 public void getNbStbyMacroSt(String nomBase,Connexion myCnx, Statement st){
   for (int i=0; i < this.ListeMacroSt.size(); i++)
   {
     MacroSt theMacroSt = (MacroSt)this.ListeMacroSt.elementAt(i);
     theMacroSt.getSiBleu( nomBase, myCnx,  st);
     theMacroSt.getSiRouge( nomBase, myCnx,  st);
     theMacroSt.getAppliRouge( nomBase, myCnx,  st);
      }
}



    public void getNbStbyMacroStByTypeSi(String nomBase,Connexion myCnx, Statement st, String nomTypeSi){
     req = "exec SI_nbStSIMacroStByTypeSI '"+this.nom+ "'" + ",'"+nomTypeSi+ "'";
     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     String oldMacroSt="";
     MacroSt theMacroSt = null;
     try {
        while (rs.next()) {
          String nomMacroSt = rs.getString(1);
          String TypeSi = rs.getString(2);
          int nbSt = rs.getInt(3);

          if (!oldMacroSt.equals(nomMacroSt))
            {
              if (theMacroSt != null) this.ListeMacroSt.addElement(theMacroSt);
              theMacroSt = new MacroSt(nomMacroSt);
            }
          if (TypeSi.equals("SI Rouge")) theMacroSt.nbStRouge = nbSt; else theMacroSt.nbStBleu = nbSt;

          oldMacroSt = nomMacroSt;
        }
         } catch (SQLException s) {s.getMessage();}

}


public int getNbInterfacesByType(String nomBase,Connexion myCnx, Statement st, String thecouleurSIOrigine, String thecouleurSIDestination){
      int nb=0;

      req="SELECT     Interface.sensInterface, Interface.typeInterface, TypeSi.couleurSI  AS couleurSIOrigine, St.nomSt AS nomStOrigine, ";
      req+="                    VersionSt.etatFicheVersionSt AS etatFicheVersionStOrigine, VersionSt.etatVersionSt AS etatVersionStOrigine, ";
      req+="                     VersionSt.siVersionSt AS siVersionStOrigine, St_1.nomSt AS nomStDestination, TypeSi_1.couleurSI AS couleurSIDestination, ";
      req+="                     VersionSt_1.etatFicheVersionSt AS etatFicheVersionStDestination, VersionSt_1.etatVersionSt AS [etatVersionStDestinatio.], ";
      req+="                     VersionSt_1.siVersionSt AS siVersionStDestination";
      req+=" FROM         Interface INNER JOIN";
      req+="                     St ON Interface.origineInterface = St.idSt INNER JOIN";
      req+="                     VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
      req+="                     TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi INNER JOIN";
      req+="                     St AS St_1 ON Interface.extremiteInterface = St_1.idSt INNER JOIN";
      req+="                     VersionSt AS VersionSt_1 ON St_1.idSt = VersionSt_1.stVersionSt INNER JOIN";
      req+="                     TypeSi AS TypeSi_1 ON VersionSt_1.typeSiVersionSt = TypeSi_1.idTypeSi";
      req+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt = 3) AND (VersionSt.siVersionSt = "+this.id+") AND (Interface.typeInterface = 'A' OR";
      req+="                     Interface.typeInterface = 'S') AND (VersionSt_1.etatFicheVersionSt = 3) AND (VersionSt_1.etatVersionSt = 3) AND (VersionSt_1.siVersionSt = "+this.id+")";


     ResultSet rs = myCnx.ExecReq(st, nomBase, req);

     try {
        while (rs.next()) {

          String sensInterface = rs.getString("sensInterface");
          String couleurSIOrigine = rs.getString("couleurSIOrigine");
          ST StOrigine = new ST(rs.getString("nomStOrigine"));
          ST StDestination = new ST(rs.getString("nomStDestination"));
          String couleurSIDestination = rs.getString("couleurSIDestination");

          if (
               (couleurSIOrigine.equals(thecouleurSIOrigine) && couleurSIDestination.equals(thecouleurSIDestination) && sensInterface.equals("--->"))
              ||
               (couleurSIOrigine.equals(thecouleurSIDestination) && couleurSIDestination.equals(thecouleurSIOrigine) && sensInterface.equals("<---"))
              ||
                (couleurSIOrigine.equals(thecouleurSIOrigine) && couleurSIDestination.equals(thecouleurSIDestination) && sensInterface.equals("<--->"))
              ||
               (couleurSIOrigine.equals(thecouleurSIDestination) && couleurSIDestination.equals(thecouleurSIOrigine) && sensInterface.equals("<--->"))
              )
          {


            Interface theInterface=new Interface(
              -1,
              StOrigine,
              StDestination,
              sensInterface,
              "",
              "",
              "",
              "",
              -1,
              -1);
             this.ListeInterfaces.addElement(theInterface);

            nb++;
          }

        }
       } catch (SQLException s) {s.getMessage();}
     return nb;
}

   public int getNbInterfacesByType3(String nomBase,Connexion myCnx, Statement st, String CouleurOrig, String CouleurDest){
         int nb=0;

   if ( CouleurOrig.equals(CouleurDest))
         {
         req = "SELECT     ListeST.nomSt AS ST_orig, Interface.sensInterface, ListeST_1.nomSt AS ST_Dest, ListeST_1.*, SI.nomSI AS SI_orig, SI_1.nomSI AS SI_Dest, ";
         req+= "                 TypeSi.couleurSI AS Couleur_Orig, TypeSi_1.couleurSI ";
         req+= " FROM         Interface INNER JOIN ";
         req+= "                  ListeST ON Interface.origineInterface = ListeST.idVersionSt INNER JOIN ";
         req+= "                  ListeST AS ListeST_1 ON Interface.extremiteInterface = ListeST_1.idSt INNER JOIN ";
         req+= "                  SI ON ListeST_1.siVersionSt = SI.idSI INNER JOIN ";
         req+= "                  SI AS SI_1 ON ListeST.siVersionSt = SI_1.idSI INNER JOIN ";
         req+= "                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi INNER JOIN ";
         req+= "                  TypeSi AS TypeSi_1 ON ListeST_1.typeSiVersionSt = TypeSi_1.idTypeSi ";
         req+= " WHERE     (SI.nomSI = '"+this.nom+"') AND (SI_1.nomSI = '"+this.nom+"') AND (TypeSi.couleurSI = '"+CouleurOrig+"') AND (TypeSi_1.couleurSI = '"+CouleurDest+"') ";
         }
         else
         {
           req = "SELECT     ListeST.nomSt AS ST_orig, Interface.sensInterface, ListeST_1.nomSt AS ST_Dest, SI.nomSI AS SI_orig, SI_1.nomSI AS SI_Dest, ";
           req+= "               TypeSi.couleurSI AS Couleur_Orig, TypeSi_1.couleurSI AS Couleur_Dest";
           req+= " FROM         Interface INNER JOIN";
           req+= "               ListeST ON Interface.origineInterface = ListeST.idVersionSt INNER JOIN";
           req+= "               ListeST AS ListeST_1 ON Interface.extremiteInterface = ListeST_1.idSt INNER JOIN";
           req+= "               SI ON ListeST_1.siVersionSt = SI.idSI INNER JOIN";
           req+= "               SI AS SI_1 ON ListeST.siVersionSt = SI_1.idSI INNER JOIN";
           req+= "               TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi INNER JOIN";
           req+= "               TypeSi AS TypeSi_1 ON ListeST_1.typeSiVersionSt = TypeSi_1.idTypeSi";
           req+= " WHERE     (SI.nomSI = '"+this.nom+"') AND (SI_1.nomSI = '"+this.nom+"') AND (TypeSi.couleurSI = '"+CouleurOrig+"') AND (TypeSi_1.couleurSI = '"+CouleurDest+"') AND (Interface.sensInterface = '--->')";

           req+= " union";

           req+= " SELECT     ListeST.nomSt AS ST_orig, Interface.sensInterface, ListeST_1.nomSt AS ST_Dest, SI.nomSI AS SI_orig, SI_1.nomSI AS SI_Dest, ";
           req+= "               TypeSi.couleurSI AS Couleur_Orig, TypeSi_1.couleurSI AS Couleur_Dest";
           req+= " FROM         Interface INNER JOIN";
           req+= "               ListeST ON Interface.origineInterface = ListeST.idVersionSt INNER JOIN";
           req+= "               ListeST AS ListeST_1 ON Interface.extremiteInterface = ListeST_1.idSt INNER JOIN";
           req+= "               SI ON ListeST_1.siVersionSt = SI.idSI INNER JOIN";
           req+= "               SI AS SI_1 ON ListeST.siVersionSt = SI_1.idSI INNER JOIN";
           req+= "               TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi INNER JOIN";
           req+= "               TypeSi AS TypeSi_1 ON ListeST_1.typeSiVersionSt = TypeSi_1.idTypeSi";
           req+= " WHERE     (SI.nomSI = '"+this.nom+"') AND (SI_1.nomSI = '"+this.nom+"') AND (TypeSi.couleurSI = '"+CouleurDest+"') AND (TypeSi_1.couleurSI = '"+CouleurOrig+"') AND (Interface.sensInterface = '<---')";


         }

        ResultSet rs = myCnx.ExecReq(st, nomBase, req);

        try {
           while (rs.next()) {
             ST StOrigine = new ST(rs.getString("ST_orig"));
             String sensInterface = rs.getString("sensInterface");
             ST StDestination = new ST(rs.getString("ST_Dest"));


             Interface theInterface=new Interface(
               -1,
               StOrigine,
               StDestination,
               sensInterface,
               "",
               "",
               "",
               "",
               -1,
               -1);
             this.ListeInterfaces.addElement(theInterface);
             nb++;
           }
          } catch (SQLException s) {s.getMessage();}
        return nb;
   }


   public int getNbInterfacesByType2(String nomBase,Connexion myCnx, Statement st, String CouleurOrig, String CouleurDest){
     int nb=0;
    req = "exec SI_BilanInterfaces '"+CouleurOrig+"', '"+CouleurDest+"','"+this.nom+ "'";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try { rs.next(); nb= rs.getInt(1);  } catch (SQLException s) {s.getMessage();}
    return nb;
}

   public void getPF_LC(String nomBase,Connexion myCnx, Statement st){

    req = "EXEC SI_PFLC_NB '"+this.nom+"'";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    try {
      rs.next();
      this.totalPF = rs.getInt(1);
      this.totalLC = rs.getInt(2);
      this.nbSt = rs.getInt(3);
    } catch (SQLException s) {s.getMessage();}

}

  public  void excelExportProjetTechnique(String nomBase,Connexion myCnx, Statement st, Statement st2,int theYearRef){
      
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    st2 = myCnx.Connect();


    String[] entete = {"Code PO du Sujet","Statut de la Fiche d'impact", "ID MOE", "FicheImpact", "Annee", "Structure", "Part", "Type Charge",  "JH", "Forfait (kEuros)"};
    Excel theExport = new Excel("doc/po/exportExcel",entete);

    theExport.xl_delete();
    //myCnx.trace("1---------- delete","location",""+location);

    // --------------------------------------------------------------------------------------------------//


    theExport.attach(theExport.Basedirectory+"/ExportChiffrageProjetTechnique_toutes.xls"); // nom du fichier excel horodate
   // myCnx.trace("2---------- attach","location",""+location+"/ExportChiffrageProjetTechnique_toutes.xls");

    //theExport.xl_open_update();
    theExport.xl_open_create("excelChiffrageLF"); // feuille de calcul

    
   

    int col = 0;
    theExport.sheet.setColumnWidth(col, 256*11); // A
    theExport.sheet.setColumnWidth(++col, 256*14); // B
    theExport.sheet.setColumnWidth(++col, 256*7); // C
    theExport.sheet.setColumnWidth(++col, 256*70); // D
    theExport.sheet.setColumnWidth(++col, 256*6); // E
    theExport.sheet.setColumnWidth(++col, 256*11); // F
    theExport.sheet.setColumnWidth(++col, 256*6); // G
    theExport.sheet.setColumnWidth(++col, 256*14); // H
    theExport.sheet.setColumnWidth(++col, 256*8); // I
    theExport.sheet.setColumnWidth(++col, 256*15); // J

    // le groupe de cellules � figer est dans les 1 premi�res colonnes des 1 premi�res lignes.
    theExport.sheet.createFreezePane(2,1) ;

    
    int numRow = 0;
    Row row = theExport.sheet.createRow(numRow);
    theExport.xl_writeEntete(row,0,theExport.entete);
    numRow++;  
    

for (int numService=0; numService < this.ListeServices.size(); numService++)
{
    Service theService = (Service)this.ListeServices.elementAt(numService);
    theService.getAllFromBd(myCnx.nomBase,myCnx,  st);
    /// --------------------------- Récupération de la liste des catégories de test----------------------//
    theService.getListeProjetsClients(myCnx.nomBase,myCnx,  st,""+theYearRef,  1);
    // -------------------------------------------------------------------------------------------------// 
for (int i=0; i < theService.ListeProjets.size(); i++)
{
  Roadmap theRoadmap = (Roadmap)theService.ListeProjets.elementAt(i);
  //theRoadmap.getChiffrageLF(myCnx.nomBase,myCnx,st, this.nomServiceImputations,theYearRef,1);
  int totalCharge=theRoadmap.getChiffrage(myCnx.nomBase,myCnx,st, theService.nomServiceImputations,theYearRef,1);

  
     
      int numCol = 0;
      row = theExport.sheet.createRow(numRow);
      
      if (theRoadmap.codeSujet != null)
          theExport.xl_write(row, numCol, "" + theRoadmap.codeSujet.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
      if (theRoadmap.EtatRoadmap != null)
          theExport.xl_write(row, ++numCol, "" + theRoadmap.EtatRoadmap.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
      if (theRoadmap.idPO != null)
          theExport.xl_write(row, ++numCol, "" + theRoadmap.idPO.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""), HSSFCell.CELL_TYPE_NUMERIC);
      if (theRoadmap.nomProjet != null)
          theExport.xl_write(row, ++numCol, "" + theRoadmap.nomProjet.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

        theExport.xl_write(row, ++numCol, "" + theYearRef, HSSFCell.CELL_TYPE_NUMERIC);

      if (theService.nom != null)
          theExport.xl_write(row, ++numCol, "" + theService.nom.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
      if (theRoadmap.part != null)
          theExport.xl_write(row, ++numCol, "" + theRoadmap.part.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));

      if (theRoadmap.TypeCharge != null)
          theExport.xl_write(row, ++numCol, "" + theRoadmap.TypeCharge.replaceAll("\t"," ").replaceAll("\r","").replaceAll("\n",""));
     
      if (theRoadmap.theChiffrage != null)
          theExport.xl_write(row, ++numCol, "" + totalCharge, HSSFCell.CELL_TYPE_NUMERIC);
      
      theExport.xl_write(row, ++numCol, "" + (int)theRoadmap.theChiffrage.Forfait, HSSFCell.CELL_TYPE_NUMERIC);

      numRow++;

      }

}
    
     

    //myCnx.trace("4---------- xl_writeEntete","numRow","");
    theExport.xl_close_create();

    //myCnx.trace("5---------- xl_close_create","xl_close_create",""+theExport.filename);
     
    }     
  
  public void getBilanPF_LCByType(String nomBase,Connexion myCnx, Statement st, String TypeBilan){

   req = "EXEC SI_PFLC_"+TypeBilan+" "+"'"+this.nom+"'";
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   ST theST = null;
   try {
      while (rs.next()) {
        theST = new ST(rs.getString(1));
        theST.nbPtsFctVersionSt = rs.getInt(2);
        theST.nbLigCodeVersionSt = rs.getInt(3);
        theST.nb = rs.getInt(4);
        //theST.dump();
        this.ListeSt.addElement(theST);
      }
       } catch (SQLException s) {s.getMessage();}

}

     public void getBilanCriticiteNbUtilisateur(String nomBase,Connexion myCnx, Statement st){

       req = "SELECT     St.nomSt, VersionSt.nbUtilVersionSt, St.Criticite, (5 - TypeCriticite.Priorite) * 5000 + VersionSt.nbUtilVersionSt AS note";
       req+=" FROM         St INNER JOIN";
       req+="                VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
       req+="                SI ON VersionSt.siVersionSt = SI.idSI INNER JOIN";
       req+="                TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi INNER JOIN";
       req+="                TypeCriticite ON St.Criticite = TypeCriticite.nomTypeCriticite";
       req+="        WHERE     (SI.nomSI = '"+this.nom+"') AND (TypeSi.couleurSI = 'rouge') AND (VersionSt.etatVersionSt = 3) AND (St.isAppli = 1)";
       req+="        ORDER BY note DESC";

       ResultSet rs = myCnx.ExecReq(st, nomBase, req);
       ST theST = null;
       try {
          while (rs.next()) {
            theST = new ST(rs.getString("nomSt"));
            theST.nbUtilVersionSt = rs.getInt("note");
            //theST.dump();
            this.ListeSt.addElement(theST);
          }
           } catch (SQLException s) {s.getMessage();}

}
     public void getBilanPF_LCByTypeOrderLC(String nomBase,Connexion myCnx, Statement st, String TypeBilan){

       req = "SELECT     St.nomSt, VersionSt.nbPtsFctVersionSt, VersionSt.nbLigCodeVersionSt, 1 AS nbST, St.isAppli, St.isST";
       req+=" FROM         St INNER JOIN";
       req+="                    VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
       req+="                    SI ON VersionSt.siVersionSt = SI.idSI";
       req+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.versionRefVersionSt =";
       req+="                        (SELECT     MAX(versionRefVersionSt) AS Expr1";
       req+="                          FROM          VersionSt AS VersionSt_1";
       req+="                          WHERE      (etatFicheVersionSt = 3) AND (etatVersionSt <> 4) AND (stVersionSt = St.idSt))) AND (SI.nomSI = '"+this.nom+"') AND (St.isAppli = 1) AND ";
       req+="                    (St.isST = 0) OR";
       req+="                    (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.versionRefVersionSt =";
       req+="                        (SELECT     MAX(versionRefVersionSt) AS Expr1";
       req+="                          FROM          VersionSt AS VersionSt_1";
       req+="                          WHERE      (etatFicheVersionSt = 3) AND (etatVersionSt <> 4) AND (stVersionSt = St.idSt))) AND (SI.nomSI = '"+this.nom+"') AND (St.isAppli = 1) AND ";
       req+="                    (St.isST = 1)";
   req+=" ORDER BY VersionSt.nbLigCodeVersionSt DESC";
       ResultSet rs = myCnx.ExecReq(st, nomBase, req);
       ST theST = null;
       try {
          while (rs.next()) {
            theST = new ST(rs.getString(1));
            theST.nbPtsFctVersionSt = rs.getInt(2);
            theST.nbLigCodeVersionSt = rs.getInt(3);
            theST.nb = rs.getInt(4);
            //theST.dump();
            this.ListeSt.addElement(theST);
          }
           } catch (SQLException s) {s.getMessage();}

}

  public void getBilanPF(String nomBase,Connexion myCnx, Statement st){

   req = "exec SI_BilanPF '"+this.nom+"','SI Rouge'";
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   try { rs.next(); this.PF_Rouge= rs.getInt(1);this.LC_Rouge= rs.getInt(2);  } catch (SQLException s) {s.getMessage();}

   req = "exec SI_BilanPF '"+this.nom+"','SI Bleu'";
    rs = myCnx.ExecReq(st, nomBase, req);
   try { rs.next(); this.PF_Bleu= rs.getInt(1);this.LC_Bleu= rs.getInt(2);  } catch (SQLException s) {s.getMessage();}

}


 public int getNbStRouge(String nomBase,Connexion myCnx, Statement st){
   req = "SELECT     COUNT(*) AS nb";
   req +=" FROM         ListeST INNER JOIN";
   req +="                   SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
   req +="                   TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
   req +=" WHERE     (SI.nomSI = '"+this.nom+"') AND (TypeSi.nomTypeSi = 'SI Rouge') AND (ListeST.isSt = 1)";
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   try { rs.next();
     this.nbStRouge = rs.getInt(1);
       } catch (SQLException s) {s.getMessage();}
   return this.nbStRouge;
 }

 public int getNbAppliByMonthByYear(String nomBase,Connexion myCnx, Statement st, int mois, int annee){
   int nb=0;
   req = "      SELECT     COUNT(St.nomSt) AS nb";
   req +="        FROM         VersionSt INNER JOIN";
   req +="                              St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req +="                              Etat AS EtatST ON VersionSt.etatVersionSt = EtatST.idEtat INNER JOIN";
   req +="                              Etat AS EtatFICHE ON VersionSt.etatFicheVersionSt = EtatFICHE.idEtat INNER JOIN";
   req +="                              TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi LEFT OUTER JOIN";
   req +="                              Service AS Service_MOE ON VersionSt.serviceMoeVersionSt = Service_MOE.idService LEFT OUTER JOIN";
   req +="                              Service AS Service_MOA ON VersionSt.serviceMoaVersionSt = Service_MOA.idService INNER JOIN";
   req +="                              SI ON VersionSt.siVersionSt = SI.idSI LEFT OUTER JOIN";
   req +="                              Membre AS Membre_MOE ON VersionSt.respMoeVersionSt = Membre_MOE.idMembre LEFT OUTER JOIN";
   req +="                              Membre AS Membre_MOA ON VersionSt.respMoaVersionSt = Membre_MOA.idMembre";
   req +="        WHERE     ((TypeSi.nomTypeSi = 'SI Rouge') AND (SI.nomSI = '"+this.nom+"') OR";
   req +="                              (TypeSi.nomTypeSi = 'SI Violet')) and (YEAR(VersionSt.creationVersionSt) = "+annee+")";
   req +="                              and (MONTH(VersionSt.creationVersionSt) = "+mois+")";
  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  try { rs.next();
    nb = rs.getInt(1);
      } catch (SQLException s) {s.getMessage();}
  return nb;
}
 
 public String getCreationAppliByMonthByYearAll(String nomBase,Connexion myCnx, Statement st, int mois, int annee){
     String listeST=""; 
   this.nbStCreation=0;
   req = "      SELECT     St.nomSt";
   req +="        FROM         VersionSt INNER JOIN";
   req +="                              St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req +="                              Etat AS EtatST ON VersionSt.etatVersionSt = EtatST.idEtat INNER JOIN";
   req +="                              Etat AS EtatFICHE ON VersionSt.etatFicheVersionSt = EtatFICHE.idEtat INNER JOIN";
   req +="                              TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi LEFT OUTER JOIN";
   req +="                              Service AS Service_MOE ON VersionSt.serviceMoeVersionSt = Service_MOE.idService LEFT OUTER JOIN";
   req +="                              Service AS Service_MOA ON VersionSt.serviceMoaVersionSt = Service_MOA.idService INNER JOIN";
   req +="                              SI ON VersionSt.siVersionSt = SI.idSI LEFT OUTER JOIN";
   req +="                              Membre AS Membre_MOE ON VersionSt.respMoeVersionSt = Membre_MOE.idMembre LEFT OUTER JOIN";
   req +="                              Membre AS Membre_MOA ON VersionSt.respMoaVersionSt = Membre_MOA.idMembre";
   req +="        WHERE     (YEAR(VersionSt.creationVersionSt) = "+annee+")";
   req +="                              and (MONTH(VersionSt.creationVersionSt) = "+mois+")";
  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   try {
      while (rs.next()) {
          if (this.nbStCreation == 0)
               listeST+= rs.getString("nomSt").toLowerCase();
          else
               listeST+= ", "+rs.getString("nomSt").toLowerCase();
        this.nbStCreation++;
      }
       } catch (SQLException s) {s.getMessage();}
   if (listeST.length() > 90)
       listeST=listeST.substring(0, 89) + "...";
  return listeST;
}  
 public String getCreationAppliByMonthByYear(String nomBase,Connexion myCnx, Statement st, int mois, int annee){
     String listeST=""; 
   this.nbStCreation=0;
   req = "      SELECT     St.nomSt";
   req +="        FROM         VersionSt INNER JOIN";
   req +="                              St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req +="                              Etat AS EtatST ON VersionSt.etatVersionSt = EtatST.idEtat INNER JOIN";
   req +="                              Etat AS EtatFICHE ON VersionSt.etatFicheVersionSt = EtatFICHE.idEtat INNER JOIN";
   req +="                              TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi LEFT OUTER JOIN";
   req +="                              Service AS Service_MOE ON VersionSt.serviceMoeVersionSt = Service_MOE.idService LEFT OUTER JOIN";
   req +="                              Service AS Service_MOA ON VersionSt.serviceMoaVersionSt = Service_MOA.idService INNER JOIN";
   req +="                              SI ON VersionSt.siVersionSt = SI.idSI LEFT OUTER JOIN";
   req +="                              Membre AS Membre_MOE ON VersionSt.respMoeVersionSt = Membre_MOE.idMembre LEFT OUTER JOIN";
   req +="                              Membre AS Membre_MOA ON VersionSt.respMoaVersionSt = Membre_MOA.idMembre";
   req +="        WHERE     ((TypeSi.nomTypeSi = 'SI Rouge') AND (SI.nomSI = '"+this.nom+"') OR";
   req +="                              (TypeSi.nomTypeSi = 'SI Violet')) and (YEAR(VersionSt.creationVersionSt) = "+annee+")";
   req +="                              and (MONTH(VersionSt.creationVersionSt) = "+mois+")";
  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   try {
      while (rs.next()) {
          if (this.nbStCreation == 0)
               listeST+= rs.getString("nomSt").toLowerCase();
          else
               listeST+= ", "+rs.getString("nomSt").toLowerCase();
        this.nbStCreation++;
      }
       } catch (SQLException s) {s.getMessage();}
   if (listeST.length() > 90)
       listeST=listeST.substring(0, 89) + "...";
  return listeST;
} 
 
 
 
 public String getCreationAppliByMonthByYear(String nomBase,Connexion myCnx, Statement st, int mois, int annee, String nomTypeSi1, String nomTypeSi2){
     String listeST=""; 
   this.nbStCreation=0;
   req = "      SELECT     St.nomSt";
   req +="        FROM         VersionSt INNER JOIN";
   req +="                              St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req +="                              Etat AS EtatST ON VersionSt.etatVersionSt = EtatST.idEtat INNER JOIN";
   req +="                              Etat AS EtatFICHE ON VersionSt.etatFicheVersionSt = EtatFICHE.idEtat INNER JOIN";
   req +="                              TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi LEFT OUTER JOIN";
   req +="                              Service AS Service_MOE ON VersionSt.serviceMoeVersionSt = Service_MOE.idService LEFT OUTER JOIN";
   req +="                              Service AS Service_MOA ON VersionSt.serviceMoaVersionSt = Service_MOA.idService INNER JOIN";
   req +="                              SI ON VersionSt.siVersionSt = SI.idSI LEFT OUTER JOIN";
   req +="                              Membre AS Membre_MOE ON VersionSt.respMoeVersionSt = Membre_MOE.idMembre LEFT OUTER JOIN";
   req +="                              Membre AS Membre_MOA ON VersionSt.respMoaVersionSt = Membre_MOA.idMembre";
   req +="        WHERE     ((TypeSi.nomTypeSi = '"+nomTypeSi1+"') AND (SI.nomSI = '"+this.nom+"') OR";
   req +="                              (TypeSi.nomTypeSi = '"+nomTypeSi2+"')) and (YEAR(VersionSt.killVersionSt) = "+annee+")";
   req +="                              and (MONTH(VersionSt.killVersionSt) = "+mois+")";   

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   try {
      while (rs.next()) {
          if (this.nbStCreation == 0)
               listeST+= rs.getString("nomSt").toLowerCase();
          else
               listeST+= ", "+rs.getString("nomSt").toLowerCase();
        this.nbStCreation++;
      }
       } catch (SQLException s) {s.getMessage();}
   if (listeST.length() > 90)
       listeST=listeST.substring(0, 89) + "...";
  return listeST;
}  
 
 public String getKillAppliByMonthByYear(String nomBase,Connexion myCnx, Statement st, int mois, int annee){
     String listeST=""; 
   this.nbStKill=0;
   req = "      SELECT     St.nomSt";
   req +="        FROM         VersionSt INNER JOIN";
   req +="                              St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req +="                              Etat AS EtatST ON VersionSt.etatVersionSt = EtatST.idEtat INNER JOIN";
   req +="                              Etat AS EtatFICHE ON VersionSt.etatFicheVersionSt = EtatFICHE.idEtat INNER JOIN";
   req +="                              TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi LEFT OUTER JOIN";
   req +="                              Service AS Service_MOE ON VersionSt.serviceMoeVersionSt = Service_MOE.idService LEFT OUTER JOIN";
   req +="                              Service AS Service_MOA ON VersionSt.serviceMoaVersionSt = Service_MOA.idService INNER JOIN";
   req +="                              SI ON VersionSt.siVersionSt = SI.idSI LEFT OUTER JOIN";
   req +="                              Membre AS Membre_MOE ON VersionSt.respMoeVersionSt = Membre_MOE.idMembre LEFT OUTER JOIN";
   req +="                              Membre AS Membre_MOA ON VersionSt.respMoaVersionSt = Membre_MOA.idMembre";
   req +="        WHERE     ((TypeSi.nomTypeSi = 'SI Rouge') AND (SI.nomSI = '"+this.nom+"') OR";
   req +="                              (TypeSi.nomTypeSi = 'SI Violet')) and (YEAR(VersionSt.killVersionSt) = "+annee+")";
   req +="                              and (MONTH(VersionSt.killVersionSt) = "+mois+")";
  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   try {
      while (rs.next()) {
          if (this.nbStKill == 0)
               listeST+= rs.getString("nomSt").toLowerCase();
          else
               listeST+= ", "+rs.getString("nomSt").toLowerCase();
        this.nbStKill++;
      }
       } catch (SQLException s) {s.getMessage();}
  
   if (listeST.length() > 90)
       listeST=listeST.substring(0, 89) + "...";
  return listeST;
} 

public String getKillAppliByMonthByYearAll(String nomBase,Connexion myCnx, Statement st, int mois, int annee){
     String listeST=""; 
   this.nbStKill=0;
   req = "      SELECT     St.nomSt";
   req +="        FROM         VersionSt INNER JOIN";
   req +="                              St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req +="                              Etat AS EtatST ON VersionSt.etatVersionSt = EtatST.idEtat INNER JOIN";
   req +="                              Etat AS EtatFICHE ON VersionSt.etatFicheVersionSt = EtatFICHE.idEtat INNER JOIN";
   req +="                              TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi LEFT OUTER JOIN";
   req +="                              Service AS Service_MOE ON VersionSt.serviceMoeVersionSt = Service_MOE.idService LEFT OUTER JOIN";
   req +="                              Service AS Service_MOA ON VersionSt.serviceMoaVersionSt = Service_MOA.idService INNER JOIN";
   req +="                              SI ON VersionSt.siVersionSt = SI.idSI LEFT OUTER JOIN";
   req +="                              Membre AS Membre_MOE ON VersionSt.respMoeVersionSt = Membre_MOE.idMembre LEFT OUTER JOIN";
   req +="                              Membre AS Membre_MOA ON VersionSt.respMoaVersionSt = Membre_MOA.idMembre";
   req +="        WHERE     (YEAR(VersionSt.killVersionSt) = "+annee+")";
   req +="                              and (MONTH(VersionSt.killVersionSt) = "+mois+")";
  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   try {
      while (rs.next()) {
          if (this.nbStKill == 0)
               listeST+= rs.getString("nomSt").toLowerCase();
          else
               listeST+= ", "+rs.getString("nomSt").toLowerCase();
        this.nbStKill++;
      }
       } catch (SQLException s) {s.getMessage();}
  
   if (listeST.length() > 90)
       listeST=listeST.substring(0, 89) + "...";
  return listeST;
}  
 public String getKillAppliByMonthByYear(String nomBase,Connexion myCnx, Statement st, int mois, int annee, String nomTypeSi1, String nomTypeSi2){
     String listeST=""; 
   this.nbStKill=0;
   req = "      SELECT     St.nomSt";
   req +="        FROM         VersionSt INNER JOIN";
   req +="                              St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req +="                              Etat AS EtatST ON VersionSt.etatVersionSt = EtatST.idEtat INNER JOIN";
   req +="                              Etat AS EtatFICHE ON VersionSt.etatFicheVersionSt = EtatFICHE.idEtat INNER JOIN";
   req +="                              TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi LEFT OUTER JOIN";
   req +="                              Service AS Service_MOE ON VersionSt.serviceMoeVersionSt = Service_MOE.idService LEFT OUTER JOIN";
   req +="                              Service AS Service_MOA ON VersionSt.serviceMoaVersionSt = Service_MOA.idService INNER JOIN";
   req +="                              SI ON VersionSt.siVersionSt = SI.idSI LEFT OUTER JOIN";
   req +="                              Membre AS Membre_MOE ON VersionSt.respMoeVersionSt = Membre_MOE.idMembre LEFT OUTER JOIN";
   req +="                              Membre AS Membre_MOA ON VersionSt.respMoaVersionSt = Membre_MOA.idMembre";
   req +="        WHERE     ((TypeSi.nomTypeSi = '"+nomTypeSi1+"') AND (SI.nomSI = '"+this.nom+"') OR";
   req +="                              (TypeSi.nomTypeSi = '"+nomTypeSi2+"')) and (YEAR(VersionSt.killVersionSt) = "+annee+")";
   req +="                              and (MONTH(VersionSt.killVersionSt) = "+mois+")";  
  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   try {
      while (rs.next()) {
          if (this.nbStKill == 0)
               listeST+= rs.getString("nomSt").toLowerCase();
          else
               listeST+= ", "+rs.getString("nomSt").toLowerCase();
        this.nbStKill++;
      }
       } catch (SQLException s) {s.getMessage();}
  
   if (listeST.length() > 90)
       listeST=listeST.substring(0, 89) + "...";
  return listeST;
}  
 
 public int getNbAppliRouge(String nomBase,Connexion myCnx, Statement st){
   req = "SELECT     COUNT(*) AS nb";
   req +=" FROM         ListeST INNER JOIN";
   req +="                   SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
   req +="                   TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
   req +=" WHERE     (SI.nomSI = '"+this.nom+"') AND (TypeSi.nomTypeSi = 'SI Rouge') AND (ListeST.isAppli = 1)";
  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  try { rs.next();
    this.nbAppliRouge = rs.getInt(1);
      } catch (SQLException s) {s.getMessage();}
  return nbAppliRouge;
} 

public int getNbStAppliRouge(String nomBase,Connexion myCnx, Statement st){
  req = "SELECT     COUNT(*) AS nb";
  req +=" FROM         ListeST INNER JOIN";
  req +="                   SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
  req +="                   TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
   req +=" WHERE     (SI.nomSI = '"+this.nom+"') AND (TypeSi.nomTypeSi = 'SI Rouge') AND (ListeST.isAppli = 1) AND (ListeST.isSt = 1)";
 ResultSet rs = myCnx.ExecReq(st, nomBase, req);
 try { rs.next();
   this.NbStAppliRouge = rs.getInt(1);
     } catch (SQLException s) {s.getMessage();}
 return NbStAppliRouge;
}

public int getNbSiRouge(String nomBase,Connexion myCnx, Statement st){
  req = "SELECT     COUNT(*) AS nb";
  req +=" FROM         ListeST INNER JOIN";
  req +="                   SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
  req +="                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
    req +=" WHERE     (SI.nomSI = '"+this.nom+"') AND (TypeSi.nomTypeSi = 'SI Rouge') AND (ListeST.isST = 1) and (ListeST.etatVersionSt =3) ";
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   try { rs.next();
     this.nbSiRouge = rs.getInt(1);
       } catch (SQLException s) {s.getMessage();}
   return nbSiRouge;
}


 public int getNbSiCouleur(String nomBase,Connexion myCnx, Statement st, int Couleur){
   int nb=0;
   req = "SELECT     COUNT(*) AS nb";
   req +="     FROM         ListeST INNER JOIN";
   req +="                   SI ON ListeST.siVersionSt = SI.idSI";
   req +=" WHERE     (ListeST.etatVersionSt = 3) AND (SI.nomSI = '"+this.nom+"') AND (ListeST.isAppli = 1)";
   req +=" GROUP BY ListeST.typeSiVersionSt";
   req +=" HAVING      (ListeST.typeSiVersionSt = "+Couleur+")";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    try { rs.next();
      nb = rs.getInt(1);
        } catch (SQLException s) {s.getMessage();}
    return nb;
}

 public int getNbSiBleu(String nomBase,Connexion myCnx, Statement st){
    req = "SELECT     COUNT(*) AS nb";
    req +=" FROM         ListeST INNER JOIN";
    req +="                   SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
    req +="                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
    req +=" WHERE     (SI.nomSI = '"+this.nom+"') AND (TypeSi.nomTypeSi = 'SI Bleu') and (ListeST.etatVersionSt =3) ";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    try { rs.next();
      this.nbSiBleu = rs.getInt(1);
        } catch (SQLException s) {s.getMessage();}
    return nbSiBleu;
}

  public int getNbSiViolet(String nomBase,Connexion myCnx, Statement st){
     req = "SELECT     COUNT(*) AS nb";
     req +=" FROM         ListeST INNER JOIN";
     req +="                   SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
     req +="                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
     req +=" WHERE     (SI.nomSI = '"+this.nom+"') AND (TypeSi.nomTypeSi = 'SI Violet') and (ListeST.etatVersionSt =3) ";
     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     try { rs.next();
       this.nbSiViolet = rs.getInt(1);
         } catch (SQLException s) {s.getMessage();}
     return nbSiViolet;
}

  public int getNbSt(String nomBase,Connexion myCnx, Statement st){
    req = "SELECT     COUNT(*) AS nb";
    req +=" FROM         ListeST INNER JOIN";
    req +="                   SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
    req +="                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
    req +=" WHERE     (SI.nomSI = '"+this.nom+"') ";
     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     try { rs.next();
       this.nbSt = rs.getInt(1);
         } catch (SQLException s) {s.getMessage();}
     return nbSt;
}

 public int getNbSiniStniAppliRouge(String nomBase,Connexion myCnx, Statement st){
   req = "SELECT     COUNT(*) AS nb";
   req +=" FROM         ListeST INNER JOIN";
   req +="                   SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
   req +="                   TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
   req +=" WHERE     (SI.nomSI = '"+this.nom+"') AND (TypeSi.nomTypeSi = 'SI Rouge') AND (ListeST.isAppli != 1) AND (ListeST.isSt != 1)";
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   try { rs.next();
     this.NbniStniAppliRouge = rs.getInt(1);
       } catch (SQLException s) {s.getMessage();}
 return NbniStniAppliRouge;
}


  public static void main2(String[] args) {
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

    //SI theSI = new SI("SIR");
    //theSI.getNbStbyPhaseNP(Connexion.nomBase, myCnx, st);
    //theSI.getListeSt(Connexion.nomBase, myCnx, st,"nomSt","asc");
    //theSI.getListeSt(Connexion.nomBase, myCnx, st, st2);
    SI theSI = new SI("SIR");
    theSI.getListePhasesNP(myCnx.nomBase,myCnx,  st);
    //theSI.getListeStAnciennete(Connexion.nomBase, myCnx, st, "nomSt","asc","SI Bleu");
    //theSI.dump();
    Phase thePhase = null;
    for (int i=0; i < theSI.ListePhasesNP.size();i++)
    {
      thePhase = (Phase)theSI.ListePhasesNP.elementAt(i);
      thePhase.getListeStNP(myCnx.nomBase,myCnx,  st, theSI.nom);
    }
    thePhase = theSI.getPhaseNPByNom("Pilotage NP");
     for (int i=0; i<thePhase.ListeSt.size();i++)
           {
             ST theST = (ST)thePhase.ListeSt.elementAt(i);
          theST.getAllFromBd(myCnx.nomBase,myCnx,st);
          //theST.dump();
           }

    myCnx.Unconnect(st);
    myCnx.Unconnect(st2);

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


    SI theSI = new SI(1);
    theSI.getAllFromBd(myCnx.nomBase,myCnx,  st ); 
    theSI.getListeStRad(myCnx.nomBase, myCnx, st,st2);
    myCnx.trace("1----","theSI.ListeSt.size()",""+theSI.ListeSt.size());
   
    transaction theTransaction = new transaction ("StRAD");
    
    theTransaction.begin(myCnx.nomBase, myCnx, st);     
    theSI.bd_InsertFaqRad(myCnx.nomBase, myCnx, st,theTransaction.nom);
    
    theSI.getListeFaqRad(myCnx.nomBase, myCnx, st );
    myCnx.trace("1----","theSI.ListeProjets.size()",""+theSI.ListeProjets.size());
 
    SI.getListePlanTypeFaq(myCnx.nomBase, myCnx, st );
    theSI.bd_InsertFaqRadType(myCnx.nomBase, myCnx, st,theTransaction.nom );
    
    theTransaction.commit(myCnx.nomBase, myCnx, st);      
            
    //theSI.getListeServices(myCnx.nomBase, myCnx, st);
    //theSI.excelExportProjetTechnique(myCnx.nomBase, myCnx, st, st2, 2013);
    //theSI.getCreationAppliByMonthByYear(myCnx.nomBase, myCnx, st,6, 2014);
    //theSI.getListeProjets(myCnx.nomBase,myCnx,  st, 2012, "1", "1", "1", "1","1", "1", "1", "1", "-1","","", "", "-1;", "-1", "1207" , 1);
    //System.out.println("theSI.ListeMacroSt.size()="+theSI.ListeMacroSt.size());
    //theSI.dump();


    myCnx.Unconnect(st);
    myCnx.Unconnect(st2);

  }

  public static void main3(String[] args) {
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


    SI.getListeProcessus(myCnx.nomBase,myCnx,  st);
    //theSI.getListeStAnciennete(Connexion.nomBase, myCnx, st, "nomSt","asc","SI Bleu");
    //theSI.dump();
    Processus theProcessus = null;
    for (int i=0; i < SI.ListeProcessus.size();i++)
    {
      theProcessus = (Processus)ListeProcessus.elementAt(i);
      //theProcessus.dump();
    }

    myCnx.Unconnect(st);
    myCnx.Unconnect(st2);

  }
}

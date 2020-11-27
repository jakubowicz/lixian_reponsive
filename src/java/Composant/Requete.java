package Composant; 
import Organisation.Collaborateur;
import Organisation.Service;

/**
 * <p>Titre : </p>
 *
 * <p>Description : </p>
 *getListeStWithInterfacesByCollaborateurgetListeProjetBySt
 * <p>Copyright : Copyright (c) 2004</p>
 *
 * <p>Soci�t� : </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Requete {
  private  String req_n1="";
  private  String req_n2="";
  private  String req_n3="";

  private  String req_n1_fin="";
  private  String req_n2_fin="";
  private  String req_n3_fin="";

  public  String sql="";
  public  String sql_fin="";


  public Collaborateur theCollaborateur=null;
  public Service theService=null;
  public String anneeRef="";


  public Requete(Collaborateur theCollaborateur, Service theService, String anneeRef) {
    this.theCollaborateur=theCollaborateur;
    this.theService=theService;
    this.anneeRef=anneeRef;

  }
  
  public  void getListeProfils(int id){
    this.sql = "SELECT     id, nom FROM ProfilsProjet WHERE idMembre = " + id;
    sql += " ORDER BY  nom asc";

  }  
// ------------------------------------------ Gestion des @ST ----------------------------------------------//
  public  void getListeSt(){
    sql = "SELECT     ListeST.idVersionSt, ListeST.nomSt, ListeST.idVersionSt AS Expr1, ListeST.isST, ListeST.isAppli";
    sql += " FROM         ListeST INNER JOIN";
    sql += " SI ON ListeST.siVersionSt = SI.idSI";
    sql += " WHERE     (SI.nomSI <> 'Fictif') AND (SI.nomSI <> 'Acteurs')AND (SI.nomSI <> 'SI Equipements') AND (SI.nomSI <> 'Composants')";
    sql += " ORDER BY  ListeST.nomSt asc";

  }
  
  public  void getListeTypeOnglet(){
    this.sql = "SELECT     id, nom";
    this.sql += " FROM         Onglet";
    this.sql += " WHERE     (isManaged = 1) AND (idPere = 0)";
    this.sql += " ORDER BY Ordre";


  }
  
  public  void getListeTypeJalon(){  
    this.sql = "select * from (";
    this.sql +=" SELECT     id, nom, Ordre FROM  typeJalon WHERE Ordre <> -1";
    this.sql+=" union";
    this.sql+=" select -1, 'zzCreer', 999999 as  Ordre";
    this.sql+=" )";
    this.sql+=" as myTable";
    this.sql+=" ORDER BY Ordre asc";        
  }  
  
  public  void getListeOngletsPere(){
    this.sql = "SELECT     id, nom";
    this.sql += " FROM         Onglet";
    this.sql += " WHERE     (isManaged = 1) AND (idPere = -1)";
    this.sql += " ORDER BY Ordre";


  }  
  
  public  void getListeOngletsPerePlusCreer(){
    
    this.sql = "select * from (";
    this.sql +=" SELECT     id, nom, Ordre from Onglet WHERE     (isManaged = 1) AND  idPere = -1";
    this.sql+=" union";
    this.sql+=" select -1, 'zzCreer', 999999 as  Ordre";
    this.sql+=" )";
    this.sql+=" as myTable";
    this.sql+=" ORDER BY Ordre asc";    


  }  
  
  public  void getListeOngletsPereInternesPlusCreer(){
    
    this.sql = "select * from (";
    this.sql +=" SELECT     id, nom, Ordre from Onglet WHERE     (isManaged <> 1) AND  idPere = -1";
    this.sql+=" union";
    this.sql+=" select -1, 'zzCreer' as nom, 999999 as  Ordre";
    this.sql+=" )";
    this.sql+=" as myTable";
    this.sql+=" ORDER BY nom asc";    


  }   
  
  public  void getListeOngletsFils(){
    this.sql = "SELECT     id, nom, Ordre";
    this.sql += " FROM         Onglet";
    this.sql += " WHERE  (isManaged = 1) AND idPere = ";

    this.sql_fin += " ORDER BY Ordre";
  }
  
  public  void getListeOngletsFilsPlusCreer(){
    this.sql = "select * from (";
    this.sql +=" SELECT     id, nom, Ordre from Onglet WHERE     (isManaged = 1) AND  idPere =";
    this.sql_fin+=" union";
    this.sql_fin+=" select -1, 'zzCreer', 999999 as  Ordre";
    this.sql_fin+=" )";
    this.sql_fin+=" as myTable";
    this.sql_fin+=" WHERE id <> 90009";
    this.sql_fin+=" ORDER BY Ordre asc";
  } 
  
  public  void getListeOngletsFilsInternesPlusCreer(){
    this.sql = "select * from (";
    this.sql +=" SELECT     id, nom, Ordre from Onglet WHERE     (isManaged <> 1) AND  idPere =";
    this.sql_fin+=" union";
    this.sql_fin+=" select -1, 'zzCreer', 999999 as  Ordre";
    this.sql_fin+=" )";
    this.sql_fin+=" as myTable";
    this.sql_fin+=" ORDER BY Ordre asc";
  }    
  
  public  void getListeSousTypeOnglet(){
    this.sql = "SELECT     Type, nom";
    this.sql += " FROM         Onglet";
    this.sql += " WHERE     Type = ";
    this.sql_fin += " ORDER BY Ordre";


  }  
  
  public  void getListeContacts(){
    this.sql = "SELECT     id, nom + '-' + prenom + '-' + entreprise AS nomComplet";
    this.sql += " FROM         Contacts";
    this.sql += " ORDER BY dateDemande DESC";

  }  
  
  public  void getListeClients(){
    this.sql = "SELECT     id, nomClient FROM  db ORDER BY nomClient";

  } 
    
  public  void getListeClientsPlusCreation(){
    req_n1 = "select * from (";
    req_n1+=" SELECT     id, nomClient FROM  db  ";
    req_n1+=" union";
    req_n1+=" select -1, 'zzCreer'";
    req_n1+=" )";
    req_n1+=" as myTable";
    req_n1+=" ORDER BY nomClient ASC";

    this.sql=req_n1;
  }    
  
  public  void getListeContactsByState(){
    this.sql = "SELECT     id, nom + '-' + prenom + '-' + entreprise AS nomComplet";
    this.sql += " FROM         Contacts";
    this.sql += " WHERE    idEtat =";
    
    this.sql_fin += " ORDER BY dateDemande DESC";

  }  
  
  public  void getListeEtatContacts(){
    this.sql = "SELECT     id, Nom, Ordre FROM         TypeEtatContact ORDER BY Ordre";

  }  
  
  public  void getListeEtatImputation(){
    this.sql = "SELECT     id, nom  FROM  TypeEtatImputation ORDER BY ordre";

  }  

  public  void getListeSemainesByEtatImputation(int idMembre, int semaine, int annee){
    this.sql = "SELECT DISTINCT (anneeRef * 53 + semaine)  as id, CAST(anneeRef AS varchar(50)) + '-' + CAST(semaine AS varchar(50)) AS nom, anneeRef, semaine";
    this.sql += " FROM         PlanDeCharge";
    this.sql += " WHERE     (projetMembre = "+idMembre+")  AND     (semaine <= "+semaine+") and anneeRef <= "+annee+" AND etat =";
    this.sql_fin += " ORDER BY id";

  } 
  
  
  public  void getListeSemainesByServiceManager(int ServiceManager, String Etat){
    this.sql = "SELECT DISTINCT ";
    this.sql += "                       "+ServiceManager+" * 3000 + (CAST(PlanDeCharge.anneeRef AS varchar(50))  -2000) * 52 +  CAST(PlanDeCharge.semaine AS varchar(50)) AS id, CAST(PlanDeCharge.anneeRef AS varchar(50)) ";
    this.sql += "                       + '-' + CAST(PlanDeCharge.semaine AS varchar(50)) AS nom";
    this.sql += " FROM         PlanDeCharge INNER JOIN";
    this.sql += "                       TypeEtatImputation ON PlanDeCharge.etat = TypeEtatImputation.id INNER JOIN";
    this.sql += "                       Membre ON PlanDeCharge.projetMembre = Membre.idMembre";
    this.sql += " WHERE   Membre.isManager=0 AND  (TypeEtatImputation.nom = '"+Etat+"') AND (Membre.serviceMembre = "+ServiceManager+")";
    this.sql_fin += " ORDER BY id";

  }   
  
  public  void getListeMembreBySemainesByServiceManager(int ServiceManager, String Etat){
    this.sql = "SELECT DISTINCT ";
    this.sql += "                       Membre.idMembre, Membre.nomMembre";
    this.sql += " FROM         PlanDeCharge INNER JOIN";
    this.sql += "                       TypeEtatImputation ON PlanDeCharge.etat = TypeEtatImputation.id INNER JOIN";
    this.sql += "                       Membre ON PlanDeCharge.projetMembre = Membre.idMembre";
    this.sql += " WHERE  Membre.isManager=0 AND    (TypeEtatImputation.nom = '"+Etat+"') AND "+ServiceManager+" * 3000 + (CAST(PlanDeCharge.anneeRef AS varchar(50))  -2000) * 52 +  CAST(PlanDeCharge.semaine AS varchar(50)) =";

  }  
  
  public  void getListeStByKey(String searchKey){
    req_n1="SELECT     VersionSt.idVersionSt, St.nomSt";
    req_n1+=" FROM         St INNER JOIN";
    req_n1+="       VersionSt ON St.idSt = VersionSt.stVersionSt";
    req_n1+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt = 3) AND (St.isRecurrent <> 1) AND (St.isMeeting <> 1) AND (St.nomSt LIKE '%"+searchKey+"%')";
    req_n1+=" ORDER BY St.nomSt";

    this.sql=req_n1;

}

  public  void getListeStWithMacroStWithGraph(){
    req_n1 = "SELECT DISTINCT VersionSt.idVersionSt, St.nomSt";
    req_n1 += " FROM         GrapMacroSt INNER JOIN";
    req_n1 += " St ON GrapMacroSt.idSt = St.idSt INNER JOIN";
    req_n1 += " VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req_n1 += " SI ON VersionSt.siVersionSt = SI.idSI INNER JOIN";
    req_n1 += " MacroSt ON VersionSt.macrostVersionSt = MacroSt.idMacrost";
    req_n1 += " WHERE     (SI.nomSI = 'SIR') AND (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt = 3) AND (MacroSt.nomMacrost <> 'Autre')";
    req_n1 += " ORDER BY St.nomSt";
    this.sql=req_n1;

}

  public  void getListeStWithProjetsWithMoaOrMoeWithFaq(){
    req_n1= "SELECT * FROM ";
    req_n1+=" (";
    req_n1+=" SELECT DISTINCT VersionSt.idVersionSt, St.nomSt";
    req_n1+=" FROM         St INNER JOIN";
    req_n1+="           VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    if (theCollaborateur.MOA.equals("yes"))
    {
      req_n1+="           Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
    }
    else
    {
      req_n1+="           Membre ON VersionSt.respMoeVersionSt = Membre.idMembre INNER JOIN";
    }
    req_n1+="           Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt";
    req_n1+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (Membre.LoginMembre = '"+theCollaborateur.Login+"') AND (St.isRecurrent = 0) AND (Roadmap.Etat <> 'MES') AND   (St.isActeur <> 1) AND (Roadmap.version <> '-- NON REGRESSION') AND (LF_Month = - 1) AND (LF_Year = - 1)  AND (isFAQ = 1)";
    req_n1+=" UNION ";
    req_n1+=" SELECT DISTINCT ListeST.idVersionSt, ListeST.nomSt";
    req_n1+=" FROM         Membre INNER JOIN";
    req_n1+="           EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
    req_n1+="           Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
    req_n1+="           ListeST ON Equipe.nom = ListeST.nomSt INNER JOIN";
    req_n1+="           Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";
    req_n1+=" WHERE     ((Membre.LoginMembre = '"+theCollaborateur.Login+"') AND ((Membre.isProjet = 1) OR (Membre.isTest = 1)) AND (ListeST.isRecurrent = 0)  AND (Roadmap.Etat <> 'MES') AND   (ListeST.isActeur <> 1)) AND (Roadmap.version <> '-- NON REGRESSION') AND (LF_Month = - 1) AND (LF_Year = - 1) AND (isFAQ = 1)";
    req_n1+=" ) ";
    req_n1+=" AS maTable ORDER BY nomSt";

    this.sql=req_n1;

  }
  
  public  void getListeStWithProjetsWithMoaOrMoe(){
    req_n1= "SELECT * FROM ";
    req_n1+=" (";
    req_n1+=" SELECT DISTINCT VersionSt.idVersionSt, St.nomSt";
    req_n1+=" FROM         St INNER JOIN";
    req_n1+="           VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    if (theCollaborateur.MOA.equals("yes"))
    {
      req_n1+="           Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
    }
    else
    {
      req_n1+="           Membre ON VersionSt.respMoeVersionSt = Membre.idMembre INNER JOIN";
    }
    req_n1+="           Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt";
    req_n1+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (Membre.LoginMembre = '"+theCollaborateur.Login+"') AND (St.isRecurrent = 0) AND (Roadmap.Etat <> 'MES') AND   (St.isActeur <> 1) AND (Roadmap.version <> '-- NON REGRESSION') AND (LF_Month = - 1) AND (LF_Year = - 1)";
    req_n1+=" UNION ";
    req_n1+=" SELECT DISTINCT ListeST.idVersionSt, ListeST.nomSt";
    req_n1+=" FROM         Membre INNER JOIN";
    req_n1+="           EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
    req_n1+="           Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
    req_n1+="           ListeST ON Equipe.nom = ListeST.nomSt INNER JOIN";
    req_n1+="           Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";
    req_n1+=" WHERE     ((Membre.LoginMembre = '"+theCollaborateur.Login+"') AND ((Membre.isProjet = 1) OR (Membre.isTest = 1)) AND (ListeST.isRecurrent = 0)  AND (Roadmap.Etat <> 'MES') AND   (ListeST.isActeur <> 1)) AND (Roadmap.version <> '-- NON REGRESSION') AND (LF_Month = - 1) AND (LF_Year = - 1)";
    req_n1+=" ) ";
    req_n1+=" AS maTable ORDER BY nomSt";

    this.sql=req_n1;

  }

  public  void getListeStWithEB(){
    req_n1= "";
    req_n1+="SELECT DISTINCT VersionSt.idVersionSt, St.nomSt, VersionSt.siVersionSt";
    req_n1+=" FROM         St INNER JOIN";
    req_n1+="           VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req_n1+="           Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt INNER JOIN";
    req_n1+="           BesoinsUtilisateur ON Roadmap.idRoadmap = BesoinsUtilisateur.idRoadmap";
    req_n1+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.isRecurrent = 0) AND (Roadmap.isFAQ = 0)";
    req_n1+=" ORDER BY St.nomSt";

    this.sql=req_n1;

  }
  
  public  void getListeStWithFAQ(){
    req_n1= "";
    req_n1+="SELECT DISTINCT VersionSt.idVersionSt, St.nomSt, VersionSt.siVersionSt";
    req_n1+=" FROM         St INNER JOIN";
    req_n1+="           VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req_n1+="           Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt INNER JOIN";
    req_n1+="           BesoinsUtilisateur ON Roadmap.idRoadmap = BesoinsUtilisateur.idRoadmap";
    req_n1+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.isRecurrent = 0) ";
    //req_n1+="  AND (VersionSt.siVersionSt = 1)";
    req_n1+=" AND isFAQ=1";
    req_n1+=" ORDER BY St.nomSt";

    this.sql=req_n1;

  }  

  public  void getListeStWithInterfacesByCollaborateur(){

    req_n1= " SELECT     idSt, nomSt";
    req_n1+= "  FROM         ListeST";
    req_n1+= "  WHERE     (etatFicheVersionSt = 3) AND (etatVersionSt <> 4) AND (serviceMoeVersionSt = "+theCollaborateur.service+") ";
    req_n1+= "  and idSt in (SELECT     origineInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M') UNION SELECT extremiteInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M'))";

    req_n1+= " order by nomSt ";

    this.sql=req_n1;

  }
  
  public  void getListeStWithInterfacesByCollaborateur2(){

    req_n1="SELECT DISTINCT * FROM (";
    req_n1+= " SELECT     idSt, nomSt";
    req_n1+= "  FROM         ListeST";
    req_n1+= "  WHERE     (etatFicheVersionSt = 3) AND (etatVersionSt <> 4) AND (serviceMoeVersionSt = "+theCollaborateur.service+") ";
    req_n1+= "  and idSt in (SELECT     origineInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M') UNION SELECT extremiteInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M'))";

    req_n1+= " UNION ";

    req_n1+= "    SELECT DISTINCT ListeST.idSt, ListeST.nomSt";
    req_n1+= "    FROM         Membre INNER JOIN";
    req_n1+= "              EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
    req_n1+= "              Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
    req_n1+= "              ListeST ON Equipe.nom = ListeST.nomSt INNER JOIN";
    req_n1+= "              Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";
    req_n1+= "    WHERE     (Membre.LoginMembre = '"+theCollaborateur.Login+"') AND ((Membre.isProjet = 1) OR (Membre.isTest = 1)) AND (ListeST.isRecurrent = 0)  AND (Roadmap.Etat <> 'MES')       ";
    req_n1+= " 	and idSt in (SELECT     origineInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M') UNION SELECT extremiteInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M'))";
    req_n1+= "    ) ";
    req_n1+= " as mytable order by nomSt ";

    this.sql=req_n1;

  }  
  
    public  void getListeStWithIhmByCollaborateur(){

    req_n1="SELECT DISTINCT * FROM (";
    req_n1+= " SELECT     idSt, nomSt";
    req_n1+= "  FROM         ListeST";
    req_n1+= "  WHERE     (etatFicheVersionSt = 3) AND (etatVersionSt <> 4) AND (serviceMoeVersionSt = "+theCollaborateur.service+") ";
    req_n1+= "  and idSt in (SELECT     origineInterface FROM Interface WHERE     (typeInterface = 'I') UNION SELECT extremiteInterface FROM Interface WHERE     (typeInterface = 'I') )";

    req_n1+= " UNION ";

    req_n1+= "    SELECT DISTINCT ListeST.idSt, ListeST.nomSt";
    req_n1+= "    FROM         Membre INNER JOIN";
    req_n1+= "              EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
    req_n1+= "              Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
    req_n1+= "              ListeST ON Equipe.nom = ListeST.nomSt INNER JOIN";
    req_n1+= "              Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";
    req_n1+= "    WHERE     (Membre.LoginMembre = '"+theCollaborateur.Login+"') AND ((Membre.isProjet = 1) OR (Membre.isTest = 1)) AND (ListeST.isRecurrent = 0)  AND (Roadmap.Etat <> 'MES')       ";
    req_n1+= " 	and idSt in (SELECT     origineInterface FROM Interface WHERE     (typeInterface = 'I') UNION SELECT extremiteInterface FROM Interface WHERE     (typeInterface = 'I'))";
    req_n1+= "    ) ";
    req_n1+= " as mytable order by nomSt ";

    this.sql=req_n1;

  }

  public  void getListeStWithInterfacesValidee(){

    req_n1= "SELECT     idSt, nomSt, siVersionSt";
    req_n1+= " FROM         ListeST";
    req_n1+= " WHERE     (etatFicheVersionSt = 3) AND (etatVersionSt = 3)  AND (siVersionSt = 1)";
    req_n1+= " and idSt in (";
    req_n1+= " SELECT     origineInterface FROM Interface WHERE    ( (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M') )AND (dumpHtml NOT LIKE '')  AND (dumpHtml IS NOT NULL) ";
    req_n1+= " UNION ";
    req_n1+= " SELECT extremiteInterface FROM Interface WHERE     ((typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M')  ) AND (dumpHtml NOT LIKE '')  AND (dumpHtml IS NOT NULL) ";
    req_n1+= " )";
    req_n1+= " ORDER BY nomSt  ";

    this.sql=req_n1;

  }

  public  void getListeStFictif(){
    req_n1 ="select * from";
    req_n1 +=" (";
    req_n1 +=" SELECT     VersionSt.idVersionSt, St.nomSt";
    req_n1 +=" FROM         St INNER JOIN";
    req_n1 +="                      VersionSt INNER JOIN";
    req_n1 +="                       MacroSt ON VersionSt.macrostVersionSt = MacroSt.idMacrost ON St.idSt = VersionSt.stVersionSt";
    req_n1 +=" WHERE     (MacroSt.nomMacrost = 'Projet') AND ((VersionSt.createurVersionSt = '"+this.theCollaborateur.Login+"') OR (VersionSt.respMoaVersionSt = '"+this.theCollaborateur.id+"') OR (VersionSt.respMoeVersionSt = '"+this.theCollaborateur.id+"')) AND (St.isRecurrent = 1) AND (VersionSt.etatFicheVersionSt <> 4)";
    req_n1 +=" UNION ";
    req_n1 +=" SELECT  -1, 'zzCreer'";
    req_n1 +=" )";
    req_n1 +=" as mytable order by nomSt";

    this.sql=req_n1;

  }
  
  public  void getListeStFictif2(){
    req_n1 ="select * from";
    req_n1 +=" (";
    req_n1 +=" SELECT idVersionSt, nomSt FROM St, VersionSt INNER JOIN MacroSt ON VersionSt.macrostVersionSt = MacroSt.idMacrost WHERE stVersionSt = idSt AND (VersionSt.createurVersionSt = '"+this.theCollaborateur.Login+"') AND (MacroSt.nomMacrost = 'Projet') ";
    req_n1 +=" UNION ";
    req_n1 +=" SELECT  -1, 'zzCreer'";
    req_n1 +=" )";
    req_n1 +=" as mytable order by nomSt";

    this.sql=req_n1;

  }  

  public  void getListeStByRegions(){
    req_n2 = "SELECT St.idSt, St.nomSt FROM  Region INNER JOIN  StRegion ON Region.idRegion = StRegion.idRegion INNER JOIN  St ON StRegion.idST = St.idSt WHERE StRegion.idRegion = ";

    req_n2 = "SELECT     VersionSt.idVersionSt, St.nomSt";
    req_n2+=" FROM         Region INNER JOIN";
    req_n2+="                      StRegion ON Region.idRegion = StRegion.idRegion INNER JOIN";
    req_n2+="                      St ON StRegion.idST = St.idSt INNER JOIN";
    req_n2+="                      VersionSt ON St.idSt = VersionSt.stVersionSt";
    req_n2+=" WHERE     StRegion.idRegion = ";

    req_n2_fin = " AND (VersionSt.suivVersionSt IS NULL) ORDER BY St.nomSt asc";

  this.sql=req_n2;
  this.sql_fin=req_n2_fin;

}

public  void getListeStByState(){
  req_n2 = "SELECT idVersionSt, nomSt, versionRefVersionSt FROM St, VersionSt WHERE etatFicheVersionSt=3 AND stVersionSt = idSt AND versionRefVersionSt=(SELECT MAX(versionRefVersionSt) FROM VersionSt WHERE etatFicheversionSt=3 AND stVersionSt=idSt) AND etatVersionSt=";
  req_n2_fin = "ORDER BY nomSt asc";

this.sql=req_n2;
this.sql_fin=req_n2_fin;

}

public  void getListeStByPrefixe(String prefixe, int idSi){
   sql = "SELECT      idVersionSt, nomSt, siVersionSt";
   sql+= " FROM         ListeST";
   sql+= " WHERE     (nomSt LIKE '"+prefixe+"[_,_]%') AND (siVersionSt = "+idSi+")";


}

public  void getListeStByLogin(){

    sql = "SELECT * FROM (";
    sql+=" SELECT VersionSt.idVersionSt, St.nomSt,isRecurrent FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoaVersionSt = Membre.idMembre ";
    sql+=" WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.creerProjet = 1) AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"')";

    sql+=" UNION";

    sql+=" SELECT VersionSt.idVersionSt, St.nomSt,isRecurrent FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
    sql+=" WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4)  AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"')";

    sql+=" UNION ";

    sql+=" SELECT ListeST.idVersionSt, ListeST.nomSt,isRecurrent FROM Membre INNER JOIN EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ListeST ON Equipe.nom = ListeST.nomSt ";
    sql+=" WHERE (Membre.LoginMembre =  '"+this.theCollaborateur.Login+"')  AND  (Membre.isProjet = 1) ";

    sql+=" )";
    sql+=" AS maTable ORDER BY isRecurrent desc,nomSt ";
  
}

public  void getListeStByLoginSansActeur(){

    sql = "SELECT * FROM (";
    sql+=" SELECT VersionSt.idVersionSt, St.nomSt,isRecurrent FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoaVersionSt = Membre.idMembre ";
    sql+=" WHERE (VersionSt.etatFicheVersionSt <> 4) AND (VersionSt.etatVersionSt <> 4) AND (St.creerProjet = 1) AND (St.isActeur = 0) AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"')";

    sql+=" UNION";

    sql+=" SELECT VersionSt.idVersionSt, St.nomSt,isRecurrent FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
    sql+=" WHERE (VersionSt.etatFicheVersionSt <> 4) AND  (St.isActeur = 0) AND (VersionSt.etatVersionSt <> 4)  AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"')";

    sql+=" UNION ";

    sql+=" SELECT ListeST.idVersionSt, ListeST.nomSt,isRecurrent FROM Membre INNER JOIN EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ListeST ON Equipe.nom = ListeST.nomSt ";
    sql+=" WHERE (Membre.LoginMembre =  '"+this.theCollaborateur.Login+"')  AND (ListeST.isActeur = 0) AND  (Membre.isProjet = 1) ";

    sql+=" )";
    sql+=" AS maTable ORDER BY isRecurrent desc,nomSt ";
  
}

public  void getListeIdStByLogin(){

    sql = "SELECT * FROM (";
    sql+=" SELECT VersionSt.stVersionSt, St.nomSt,isRecurrent FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoaVersionSt = Membre.idMembre ";
    sql+=" WHERE (VersionSt.etatFicheVersionSt <> 4) AND (VersionSt.etatVersionSt <> 4) AND (St.creerProjet = 1) AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"')";

    sql+=" UNION";

    sql+=" SELECT VersionSt.stVersionSt, St.nomSt,isRecurrent FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
    sql+=" WHERE (VersionSt.etatFicheVersionSt <> 4) AND (VersionSt.etatVersionSt <> 4)  AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"')";

    sql+=" UNION ";

    sql+=" SELECT ListeST.stVersionSt, ListeST.nomSt,isRecurrent FROM Membre INNER JOIN EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ListeST ON Equipe.nom = ListeST.nomSt ";
    sql+=" WHERE (Membre.LoginMembre =  '"+this.theCollaborateur.Login+"')  AND  (Membre.isProjet = 1) ";

    sql+=" )";
    sql+=" AS maTable ORDER BY isRecurrent desc,nomSt ";
  
}
public  void getListeIdStByLogin2(){

    sql = "SELECT * FROM (";
    sql+=" SELECT VersionSt.stVersionSt, St.nomSt,isRecurrent FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoaVersionSt = Membre.idMembre ";
    sql+=" WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.creerProjet = 1) AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"')";

    sql+=" UNION";

    sql+=" SELECT VersionSt.stVersionSt, St.nomSt,isRecurrent FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
    sql+=" WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4)  AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"')";

    sql+=" UNION ";

    sql+=" SELECT ListeST.stVersionSt, ListeST.nomSt,isRecurrent FROM Membre INNER JOIN EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ListeST ON Equipe.nom = ListeST.nomSt ";
    sql+=" WHERE (Membre.LoginMembre =  '"+this.theCollaborateur.Login+"')  AND  (Membre.isProjet = 1) ";

    sql+=" )";
    sql+=" AS maTable ORDER BY isRecurrent desc,nomSt ";
  
}

public  void getListeIdBudgetByYear(){

    this.sql = "      SELECT DISTINCT ";
    this.sql += "                        PlanOperationnelClient.idWebPo, cast(PlanOperationnelClient.idWebPo as varchar(10)) + '-' +PlanOperationnelClient.nomProjet";
    this.sql += "      FROM         PlanOperationnelClient INNER JOIN";
    this.sql += "                         Service ON PlanOperationnelClient.Service = Service.nomServiceImputations  AND ";
    this.sql += "                         PlanOperationnelClient.Annee = PlanOperationnelClient.Annee";
    this.sql += "      WHERE      (PlanOperationnelClient.Annee = "+this.anneeRef+") AND Service.idService =";
    this.sql_fin += "      ORDER BY PlanOperationnelClient.idWebPo";
  
}

public  void getListeIdBudgetByYearWithCreation(){
    this.sql= "select * from (";
    this.sql += "      SELECT DISTINCT ";
    this.sql += "                        PlanOperationnelClient.idWebPo, cast(PlanOperationnelClient.idWebPo as varchar(10)) + '-' +PlanOperationnelClient.nomProjet as nomProjet";
    this.sql += "      FROM         PlanOperationnelClient INNER JOIN";
    this.sql += "                         Service ON PlanOperationnelClient.Service = Service.nomServiceImputations  AND ";
    this.sql += "                         PlanOperationnelClient.Annee = PlanOperationnelClient.Annee";
    this.sql += "      WHERE      (PlanOperationnelClient.Annee = "+this.anneeRef+") AND Service.idService =";
    
    this.sql_fin = "  UNION SELECT  9999999 as idWebPo, 'zzCreer' as nomProjet)";
    this.sql_fin+= " as mytable";    
    this.sql_fin += "      ORDER BY idWebPo";
}

public  void getListeIdBudgetByYearWithCreation2(){
    this.sql= "select * from (";
    this.sql += "      SELECT DISTINCT ";
    this.sql += "                        PlanOperationnelClient.idWebPo, cast(PlanOperationnelClient.idWebPo as varchar(10)) + '-' +PlanOperationnelClient.nomProjet as nomProjet";
    this.sql += "      FROM         PlanOperationnelClient INNER JOIN";
    this.sql += "                         Service ON PlanOperationnelClient.Service = Service.nomServiceImputations  AND ";
    this.sql += "                         PlanOperationnelClient.Annee = PlanOperationnelClient.Annee";
    this.sql += "      WHERE      (PlanOperationnelClient.Annee = "+this.anneeRef+") AND Service.idService =";
    
    this.sql_fin = "  UNION SELECT  -1 as idWebPo, 'zzCreer' as nomProjet)";
    this.sql_fin+= " as mytable";    
    this.sql_fin += "      ORDER BY idWebPo";
}

public  void getListeStByLoginByMoa(String Type){
if (Type.equals("Suivi") || Type.equals("non Suivi"))
  {
    sql = "SELECT * FROM (";
    sql+=" SELECT VersionSt.idVersionSt, St.nomSt,isRecurrent FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoaVersionSt = Membre.idMembre ";
    sql+=" WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.creerProjet = 1) AND (St.isActeur = 0) AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"')";

    sql+=" UNION";

    sql+=" SELECT VersionSt.idVersionSt, St.nomSt,isRecurrent FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
    sql+=" WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.creerProjet = 1) AND (St.isActeur = 0) AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"')";

    sql+=" UNION ";

    sql+=" SELECT ListeST.idVersionSt, ListeST.nomSt,isRecurrent FROM Membre INNER JOIN EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ListeST ON Equipe.nom = ListeST.nomSt ";
    sql+=" WHERE (Membre.LoginMembre =  '"+this.theCollaborateur.Login+"')  AND  (Membre.isProjet = 1) AND (ListeST.isActeur = 0) AND (ListeST.creerProjet = 1)AND (ListeST.isActeur <> 1)";

    sql+=" )";
    sql+=" AS maTable WHERE nomSt <> 'Jours Feries' ORDER BY isRecurrent desc,nomSt ";
  }

  else if (Type.equals("Standby"))
  {
    sql= "SELECT * FROM (";
    sql+=" SELECT DISTINCT VersionSt.idVersionSt, St.nomSt";
    sql+=" FROM         St INNER JOIN";
    sql+="                       VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    if (this.theCollaborateur.MOA.equals("yes"))
    {
      sql += "                       Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
    }
    else
    {
      sql += "                       Membre ON VersionSt.respMoeVersionSt = Membre.idMembre INNER JOIN";
    }
    sql+="                       Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt";
    sql+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.creerProjet = 1) AND (St.isActeur = 0) AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"') AND ";
    sql+="                       (Roadmap.standby = 1)";

    sql+=" UNION";

    sql+=" SELECT ListeST.idVersionSt, ListeST.nomSt ";
    sql+=" FROM Membre INNER JOIN EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ListeST ON Equipe.nom = ListeST.nomSt ";
    sql+=" WHERE (Membre.LoginMembre = '"+this.theCollaborateur.Login+"')  AND  (Membre.isProjet = 1) AND (ListeST.isActeur = 0) AND (ListeST.creerProjet = 1)AND (ListeST.isActeur <> 1)      ";
    sql+=" AND ListeST.idVersionSt in (SELECT DISTINCT idVersionSt FROM  Roadmap WHERE     (standby = 1) )";

    sql+=" )  AS maTable ORDER BY nomSt     ";

  }

}

public  void getListeStWithBaseline(){

    sql = "SELECT * FROM (";
    sql+=" SELECT VersionSt.idVersionSt, St.nomSt,isRecurrent FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoaVersionSt = Membre.idMembre ";
    sql+=" WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.creerProjet = 1) AND (St.isActeur = 0) AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"')";

    sql+=" UNION";

    sql+=" SELECT VersionSt.idVersionSt, St.nomSt,isRecurrent FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
    sql+=" WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.creerProjet = 1) AND (St.isActeur = 0) AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"')";

    sql+=" UNION ";

    sql+=" SELECT ListeST.idVersionSt, ListeST.nomSt,isRecurrent FROM Membre INNER JOIN EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ListeST ON Equipe.nom = ListeST.nomSt ";
    sql+=" WHERE (Membre.LoginMembre =  '"+this.theCollaborateur.Login+"')  AND  (Membre.isProjet = 1) AND (ListeST.isActeur = 0) AND (ListeST.creerProjet = 1)AND (ListeST.isActeur <> 1)";

    sql+=" )";
    sql+=" AS maTable WHERE nomSt <> 'Jours Feries' ";
    sql+=" AND idVersionSt IN (SELECT Roadmap.idVersionSt FROM Baselines INNER JOIN Roadmap ON Baselines.idRoadmap = Roadmap.idRoadmap)";
    sql+=" ORDER BY isRecurrent desc,nomSt ";
  

 

}

public  void getListeStNonFictifByLoginByMOA(){

    req_n1= "SELECT * FROM ";
    req_n1+=" (";
    req_n1+=" SELECT DISTINCT VersionSt.idVersionSt, St.nomSt";
    req_n1+=" FROM         St INNER JOIN";
    req_n1+="           VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    if (this.theCollaborateur.MOA.equals("yes"))
    {
      req_n1+="           Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
    }
    else
    {
      req_n1+="           Membre ON VersionSt.respMoeVersionSt = Membre.idMembre INNER JOIN";
    }
    req_n1+="           Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt";
    req_n1+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"') AND (St.isActeur = 0) AND (St.isRecurrent = 0)  AND (St.creerProjet = 1) AND (NOT (St.nomSt LIKE '**%')) AND (NOT (St.nomSt LIKE '---%')) ";

    req_n1+=" UNION ";

    req_n1+=" SELECT DISTINCT ListeST.idVersionSt, ListeST.nomSt";
    req_n1+=" FROM         Membre INNER JOIN";
    req_n1+="           EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
    req_n1+="           Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
    req_n1+="           ListeST ON Equipe.nom = ListeST.nomSt INNER JOIN";
    req_n1+="           Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";
    req_n1+=" WHERE     (Membre.LoginMembre = '"+this.theCollaborateur.Login+"') AND ((Membre.isProjet = 1) OR (Membre.isTest = 1))  AND (ListeST.isActeur = 0) AND (ListeST.isRecurrent = 0)   AND (NOT (ListeST.nomSt LIKE '**%')) AND (NOT (ListeST.nomSt LIKE '--%')) ";
    req_n1+=" ) ";
    req_n1+=" AS maTable ORDER BY nomSt";

    sql=req_n1;

}

public  void getListeStNonRecurrentByLoginByMOA(){

  req_n1=" SELECT * FROM (";
  req_n1+=" SELECT     VersionSt.idVersionSt, St.nomSt";
  req_n1+=" FROM         St INNER JOIN";
  req_n1+="               VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
  if (this.theCollaborateur.MOA.equals("yes"))
  {
    req_n1+="           Membre ON VersionSt.respMoaVersionSt = Membre.idMembre";
  }
  else
  {
    req_n1+="           Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
    }
  req_n1+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"') AND (St.creerProjet = 1) AND ";
  req_n1+="               (VersionSt.idVersionSt IN";
  req_n1+="                   (SELECT     idVersionSt";
  req_n1+="                     FROM          Roadmap where version <> '-- NON REGRESSION')) AND (St.isMeeting <> 1) ";

  req_n1+=" UNION ";

  req_n1+=" SELECT     ListeST.idVersionSt, ListeST.nomSt";
  req_n1+=" FROM         Membre INNER JOIN";
  req_n1+="               EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
  req_n1+="               Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
  req_n1+="               ListeST ON Equipe.nom = ListeST.nomSt";
  req_n1+=" WHERE     (Membre.LoginMembre = '"+this.theCollaborateur.Login+"') AND (Membre.isProjet = 1) AND (ListeST.creerProjet = 1) AND (ListeST.isActeur <> 1) AND ";
  req_n1+="               (ListeST.isMeeting <> 1) AND (ListeST.idVersionSt IN";
  req_n1+="                   (SELECT     idVersionSt";
  req_n1+="                     FROM          Roadmap where version <> '-- NON REGRESSION'))";

  req_n1+=" )  AS maTable ORDER BY nomSt";

  sql=req_n1;

}

public  void getListeStNonFictifByLoginByMOAAvecTests(){

    req_n1= "SELECT * FROM ";
    req_n1+=" (";
    req_n1+=" SELECT DISTINCT VersionSt.idVersionSt, St.nomSt";
    req_n1+=" FROM         St INNER JOIN";
    req_n1+="               VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    if (this.theCollaborateur.MOA.equals("yes"))
    {
      req_n1+="           Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
    }
    else
    {
      req_n1+="           Membre ON VersionSt.respMoeVersionSt = Membre.idMembre INNER JOIN";
    }
    req_n1+="               Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt INNER JOIN";
    req_n1+="               categorieTest ON Roadmap.idRoadmap = categorieTest.idRoadmap INNER JOIN";
    req_n1+="               Tests ON categorieTest.id = Tests.idCategorie";
    req_n1+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"') AND (St.isRecurrent = 0)  ";
    req_n1+="                AND (St.creerProjet = 1) AND (NOT (St.nomSt LIKE '**%')) AND (NOT (St.nomSt LIKE '---%')) AND ";
    req_n1+="               (Roadmap.version <> '-- NON REGRESSION')         ";

    req_n1+=" UNION ";

    req_n1+=" SELECT DISTINCT ListeST.idVersionSt, ListeST.nomSt";
    req_n1+=" FROM         Membre INNER JOIN";
    req_n1+="               EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
    req_n1+="               Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
    req_n1+="               ListeST ON Equipe.nom = ListeST.nomSt INNER JOIN";
    req_n1+="               Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt INNER JOIN";
    req_n1+="               categorieTest ON Roadmap.idRoadmap = categorieTest.idRoadmap INNER JOIN";
    req_n1+="               Tests ON categorieTest.id = Tests.idCategorie";
    req_n1+=" WHERE     (Membre.LoginMembre = '"+this.theCollaborateur.Login+"') AND (Membre.isProjet = 1) AND (ListeST.isRecurrent = 0) AND (Roadmap.Etat <> 'MES') AND ";
    req_n1+="               (NOT (ListeST.nomSt LIKE '**%')) AND (NOT (ListeST.nomSt LIKE '--%')) AND (Roadmap.version <> '-- NON REGRESSION') OR";
    req_n1+="               (Membre.LoginMembre = '"+this.theCollaborateur.Login+"') AND (ListeST.isRecurrent = 0)  AND (NOT (ListeST.nomSt LIKE '**%')) AND ";
    req_n1+="               (NOT (ListeST.nomSt LIKE '--%')) AND (Roadmap.version <> '-- NON REGRESSION') AND (Membre.isTest = 1)         ) ";
    req_n1+=" AS maTable ORDER BY nomSt";

    sql=req_n1;

}

  public  void getListeStateFicheStAvecCreation(){   
    req_n1= "select * from (";
    req_n1+= " SELECT idTypeEtat, nom2TypeEtat, Priorite FROM TypeEtat  WHERE (ST = 'ST') union select -1, 'Creer', 99999999 as priorite)";
    req_n1+= " as mytable order by Priorite asc";    
    this.sql=req_n1;
  }
  
  public  void getListeStateFicheSt(){
    req_n1 = "SELECT idTypeEtat, nom2TypeEtat FROM TypeEtat  WHERE (ST = 'ST') ORDER BY Priorite";
    this.sql=req_n1;
  }  

  public  void getListeStRetard(String Procedure, String Service){
    req_n1=  "exec "+Procedure+" '"+Service+"'";
    this.sql=req_n1;
  }
  
  
  public  void getListeProjetsRetard(  int ordre, String nomService){
  String req = "";
  req = "SELECT DISTINCT ";
  req += "                      Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS nomProjet, Roadmap.dateT0, Roadmap.Etat, Service.nomService, St.isMeeting, Roadmap.LF_Month, Roadmap.LF_Year, ";
  req += "                       Roadmap.standby, St.isRecurrent, Roadmap.typologie, Roadmap.idJalon, typeJalon.nom, typeJalon.ordre";
  req += " FROM         Roadmap INNER JOIN";
  req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
  req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
  req += "                       JalonsProjet ON Roadmap.idRoadmap = JalonsProjet.idRoadmap INNER JOIN";
  req += "                       typeJalon ON JalonsProjet.type = typeJalon.id CROSS JOIN";
  req += "                       Service";
  req += " WHERE				((VersionSt.serviceMoaVersionSt = Service.idService) OR (VersionSt.serviceMoeVersionSt = Service.idService)) AND ";
  req += " 					 ((SELECT     JalonsProjet.date FROM  JalonsProjet INNER JOIN  typeJalon ON JalonsProjet.type = typeJalon.id WHERE   JalonsProjet.idRoadmap = Roadmap.idRoadmap AND typeJalon.ordre = "+ordre+") BETWEEN CONVERT(DATETIME, '1995-01-01 00:00:00', 102) AND CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)) ";
  req += " 					 ";
  req += " 					 AND (SELECT  ordre  FROM typeJalon WHERE  id = Roadmap.idJalon)  < "+ordre+"";
  req += "                       AND (Service.nomService = '"+nomService+"') AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND (Roadmap.standby = 0) AND ";
  req += "                       (St.isRecurrent <> 1) AND (Roadmap.typologie = 0 OR";
  req += "                       Roadmap.typologie = 1) AND (typeJalon.ordre = "+ordre+") ";
  req += " ORDER BY nomProjet";
  this.sql=req;
  } 
  
  public  void getListeProjetsRetard(  int ordre, String nomService, String nomJalon, int typologie1, int typologie2){
  String req = "";
  req = "select distinct * from";
  req += " (";
  req += " SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet, Roadmap."+nomJalon+", Roadmap.LF_Year, Service.nomService, St.isMeeting, Roadmap.LF_Month, Roadmap.standby, St.isRecurrent, Roadmap.typologie, typeJalon.ordre";
  req += " FROM         Roadmap INNER JOIN";
  req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
  req += "                       Service ON VersionSt.serviceMoaVersionSt = Service.idService INNER JOIN";
  req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
  req += "                       typeJalon ON Roadmap.idJalon = typeJalon.id                     ";
  req += " union";
  req += " SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet, Roadmap."+nomJalon+", Roadmap.LF_Year, Service.nomService, St.isMeeting, Roadmap.LF_Month, Roadmap.standby, St.isRecurrent, Roadmap.typologie, typeJalon.ordre";
  req += " FROM         Roadmap INNER JOIN";
  req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
  req += "                       Service ON VersionSt.serviceMoeVersionSt = Service.idService INNER JOIN";
  req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
  req += "                       typeJalon ON Roadmap.idJalon = typeJalon.id";
  req += " )  ";
  req += " as mytable ";
  req += " WHERE     ("+nomJalon+" < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)) AND ("+nomJalon+" > CONVERT(DATETIME, '1995-01-01 00:00:00', 102)) ";
  req += "  AND (nomService = '"+nomService+"') AND (isMeeting <> 1) AND (LF_Month = - 1) ";
  req += "  AND  (LF_Year = - 1) AND (standby = 0) AND (isRecurrent <> 1) AND ((typologie = 0) OR (typologie = "+typologie1+") OR (typologie = "+typologie2+"))";
  req += "  AND (ordre < "+ordre+")";
  req += "  AND (idRoadmap not in (select idRoadmapPere from Roadmap))";
  req += " order by     nomProjet ";
  
  this.sql=req;
  } 
  
 public  void getListeProjetsRetardMembre(  int ordre, String Login, String nomJalon, int typologie1, int typologie2){
  String req = "";
  req = "select distinct * from";
  req += " (";
  req += " SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet, Roadmap.Etat, Membre.LoginMembre, Roadmap."+nomJalon+", St.isMeeting, Roadmap.LF_Month, Roadmap.LF_Year, St.isRecurrent, Roadmap.standby, Roadmap.typologie, typeJalon.ordre";
  req += " FROM         Roadmap INNER JOIN";
  req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
  req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
  req += "                       Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
  req += "                       typeJalon ON Roadmap.idJalon = typeJalon.id";
  req += " UNION";
  req += " SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet, Roadmap.Etat, Membre.LoginMembre, Roadmap."+nomJalon+", St.isMeeting, Roadmap.LF_Month, Roadmap.LF_Year, St.isRecurrent, Roadmap.standby, Roadmap.typologie, typeJalon.ordre";
  req += " FROM         Roadmap INNER JOIN";
  req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
  req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
  req += "                       Membre ON VersionSt.respMoeVersionSt = Membre.idMembre INNER JOIN";
  req += "                       typeJalon ON Roadmap.idJalon = typeJalon.id";
  req += " UNION";
  req += " SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet, Roadmap.Etat, Membre.LoginMembre, Roadmap."+nomJalon+", St.isMeeting, Roadmap.LF_Month, Roadmap.LF_Year, St.isRecurrent, Roadmap.standby, Roadmap.typologie, typeJalon.ordre";
  req += " FROM         Roadmap INNER JOIN";
  req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
  req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
  req += "                       Equipe ON St.nomSt = Equipe.nom INNER JOIN";
  req += "                       EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
  req += "                       Membre ON EquipeMembre.idMembre = Membre.idMembre INNER JOIN";
  req += "                       typeJalon ON Roadmap.idJalon = typeJalon.id";
  req += " )  ";
  req += " as mytable ";
  req += " WHERE     ("+nomJalon+" < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)) AND("+nomJalon+" > CONVERT(DATETIME, '1995-01-01 00:00:00', 102)) AND (isMeeting <> 1) AND (LF_Month = - 1) AND ";
  req += "                       (LF_Year = - 1) AND (isRecurrent <> 1) AND (standby = 0) ";
  req += "                       AND ((typologie = 0) OR (typologie = "+typologie1+") OR (typologie = "+typologie2+"))";
  req += "                       AND (LoginMembre = '"+Login+"')";
  req += "                       AND (ordre < "+ordre+")";
  req += "  AND (idRoadmap not in (select idRoadmapPere from Roadmap))";
  req += " order by     nomProjet";
  this.sql=req;
  }

  public  void getListeStateSt(){
    req_n1 = "SELECT idEtat, nomEtat FROM Etat ORDER BY idEtat";
    this.sql=req_n1;
  }

  public  void getListeStByServices(String Type){
    req_n3 = "SELECT idVersionSt, nomSt, versionRefVersionSt  FROM St, VersionSt WHERE etatFicheVersionSt=3 AND etatVersionSt != 4 AND stVersionSt = idSt AND versionRefVersionSt=(SELECT MAX(versionRefVersionSt) FROM VersionSt WHERE etatFicheversionSt=3 AND stVersionSt=idSt)";
    if (Type.equals("MOA"))
    {
      req_n3 += " AND (serviceMoaVersionSt)=";
    }
    else
    {
      req_n3 += " AND (serviceMoeVersionSt)=";
    }
    req_n3_fin = "ORDER BY nomSt asc";

    this.sql=req_n3;
    this.sql_fin=req_n3_fin;
  }

  public  void getListeStWithMoeOrMoaByState(){
    req_n2 = "SELECT     VersionSt.idVersionSt, St.nomSt FROM         St INNER JOIN   VersionSt ON St.idSt = VersionSt.stVersionSt LEFT OUTER JOIN  MacroSt ON VersionSt.macrostVersionSt = MacroSt.idMacrost WHERE   ((MacroSt.nomMacrost <> 'Projet' OR  VersionSt.macrostVersionSt is null) AND VersionSt.serviceMoaVersionSt IN (SELECT     Service_1.idService  FROM          Membre INNER JOIN   Service AS Service_1 ON Membre.serviceMembre = Service_1.idService   WHERE      ((Membre.LoginMembre = '"+theCollaborateur.Login+"'))) OR ((MacroSt.nomMacrost <> 'Projet' OR  VersionSt.macrostVersionSt is null) AND VersionSt.serviceMoeVersionSt IN (SELECT     Service_1.idService  FROM          Membre AS Membre_1 INNER JOIN  Service AS Service_1 ON Membre_1.serviceMembre = Service_1.idService  WHERE      (Membre_1.LoginMembre = '"+theCollaborateur.Login+"'))) OR     (createurVersionSt = '"+theCollaborateur.Login+"'))  AND St.nomSt <> 'Jours Feries'  AND VersionSt.etatFicheVersionSt = ";
    req_n2_fin = " ORDER BY nomSt asc";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }

  public  void getListeStByStateFiche(){
    
    req_n2 = "";
    req_n2 +="SELECT DISTINCT VersionSt.idVersionSt, St.nomSt, VersionSt.versionRefVersionSt";
    req_n2 += " FROM         St INNER JOIN";
    req_n2 += "                       VersionSt ON St.idSt = VersionSt.stVersionSt";
    req_n2 += " WHERE (St.nomSt <> 'Jours Feries')     AND     VersionSt.etatFicheVersionSt =";
    req_n2_fin = " ORDER BY St.nomSt";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }

  public  void getListeStByMembreMoe(){
    req_n2 = "SELECT idVersionSt, nomSt, versionRefVersionSt FROM St, VersionSt WHERE etatFicheVersionSt=3 AND etatVersionSt != 4 AND stVersionSt = idSt AND versionRefVersionSt=(SELECT MAX(versionRefVersionSt) FROM VersionSt WHERE etatFicheversionSt=3 AND stVersionSt=idSt) AND respMoeVersionSt="; req_n2_fin = "ORDER BY nomSt asc";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;
  }

  public  void getListeStByMacroSt(){
    req_n3 = "SELECT idVersionSt, nomSt, nomSt FROM St, VersionSt WHERE etatFicheVersionSt=3 AND etatVersionSt != 4 AND stVersionSt = idSt AND macrostVersionSt=";
    req_n3_fin = "ORDER BY St.nomSt asc";

    this.sql=req_n3;
    this.sql_fin=req_n3_fin;
  }


// --------------------------------------------------------------------------------------------------------//

  public  void getListeModulesByStAvecCreation(){

    sql = "select * from (";
    sql  += " SELECT     idModule, nomModule";
    sql += " FROM         Module";
    sql += " WHERE     versionStModule = ";
    sql_fin = " UNION";
    sql_fin  += " SELECT     -1, 'zzCreer' as nomModule";
    sql_fin+=" )";
    sql_fin+=" as myTable";       
    sql_fin += "  ORDER BY nomModule";

  }
  
    public  void getListeModulesBySt(){

    sql  = "SELECT     idModule, nomModule";
    sql += " FROM         Module";
    sql += " WHERE     versionStModule = ";

    sql_fin = "  ORDER BY nomModule";

  }
    public  void getListeTechnoBySt(){

    sql  = "SELECT     Middleware.idMiddleware, Logiciel.nomLogiciel + '-' + Middleware.nomMiddleware AS nom";
    sql += " FROM         ST_MW INNER JOIN";
    sql += "                       VersionMW ON ST_MW.mwST_MW = VersionMW.idVersionMW INNER JOIN";
    sql += "                       Middleware ON VersionMW.mwVersionMW = Middleware.idMiddleware INNER JOIN";
    sql += "                       Logiciel ON Middleware.LogicielMiddleWare = Logiciel.idLogiciel";
    sql += " WHERE     ST_MW.versionStST_MW =";
    
    sql_fin = "  ORDER BY nom";

  } 
    public  void getListeTechnoByStWithCreation(){

    sql= "select * from (";
    sql += "SELECT     Middleware.idMiddleware, Logiciel.nomLogiciel + '-' + Middleware.nomMiddleware AS nom";
    sql += " FROM         ST_MW INNER JOIN";
    sql += "                       VersionMW ON ST_MW.mwST_MW = VersionMW.idVersionMW INNER JOIN";
    sql += "                       Middleware ON VersionMW.mwVersionMW = Middleware.idMiddleware INNER JOIN";
    sql += "                       Logiciel ON Middleware.LogicielMiddleWare = Logiciel.idLogiciel";
    sql += " WHERE     ST_MW.versionStST_MW =";
    
    sql_fin = "  UNION SELECT  -1, 'zzCreer')";
    sql_fin+= " as mytable";
    sql_fin+= " order by nom";
 
  }    
// ------------------------------------------ Gestion des @Projets ----------------------------------------------//
  public  void getListeProjetByStWithTaches(){

    sql += "SELECT     Roadmap.idRoadmap, Roadmap.version, Roadmap.Ordre";
    sql += " FROM         Roadmap INNER JOIN";
    sql += "       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    sql += "       St ON VersionSt.stVersionSt = St.idSt";
    
    sql += "  WHERE (Roadmap.Etat <> 'MES')   AND (St.isMeeting <> 1) AND (Roadmap.standby = 0)";
    sql += " AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)";
    sql += " AND (Roadmap.version <> '-- NON REGRESSION')";
    sql += " AND (Roadmap.isFAQ <> 1)";
    sql += " AND Roadmap.idVersionSt = ";
    
    sql_fin += "  order by ordre";


  }
  
 public  void getListeProjetByStWithCreation(String Type){

    sql  = "select * from (";
    sql += "SELECT     Roadmap.idRoadmap, Roadmap.version, Roadmap.Ordre";
    sql += " FROM         Roadmap INNER JOIN";
    sql += "       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    sql += "       St ON VersionSt.stVersionSt = St.idSt";

    if (Type.equals("Suivi"))
    {
      sql += " WHERE     ((Roadmap.Etat <> 'TEST')   AND (St.isMeeting = 1) OR (Roadmap.Etat <> 'MES')   AND (St.isMeeting <> 1))  AND (Roadmap.standby = 0)";
    }
    else if (Type.equals("non Suivi"))
    {
      sql += " WHERE     ((Roadmap.Etat <> 'TEST')   AND (St.isMeeting = 1) OR (Roadmap.Etat = 'MES')   AND (St.isMeeting <> 1))  AND (Roadmap.standby = 0)";
    }
    else if (Type.equals("Standby"))
    {
      sql += " WHERE     ((Roadmap.Etat <> 'TEST')   AND (St.isMeeting = 1) OR (Roadmap.Etat <> 'MES')   AND (St.isMeeting <> 1))  AND (Roadmap.standby = 1)";
    }

    sql += " AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)";
    sql += " AND (Roadmap.version <> '-- NON REGRESSION')";
    sql += " AND (Roadmap.isFAQ <> 1)";
    sql += " AND Roadmap.idVersionSt = ";

    if (Type.equals("Suivi"))
    {
      sql_fin = "  UNION SELECT  -1, 'zzCreer',99999 as ordre";
      sql_fin += "  )";
      sql_fin += "  as mytable  order by ordre";
    }
    else
    {
      sql_fin += "  )";
      sql_fin += "  as mytable  order by ordre";
    }


  }  

  public  void getListeProjetBySt(String Type){
    sql  = "select * from (";
    sql += "SELECT     Roadmap.idRoadmap, Roadmap.version, Roadmap.Ordre";
    sql += " FROM         Roadmap INNER JOIN";
    sql += "       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    sql += "       St ON VersionSt.stVersionSt = St.idSt";

    if (Type.equals("Suivi"))
    {
      sql += " WHERE     (Roadmap.Etat <> 'MES')   AND (Roadmap.standby = 0)";
    }
    else if (Type.equals("non Suivi"))
    {
      sql += " WHERE     (Roadmap.Etat = 'MES')   AND (Roadmap.standby = 0)";
    }
    else if (Type.equals("Standby"))
    {
      sql += " WHERE     (Roadmap.standby = 1)";
    }

    sql += " AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)";
    sql += " AND (Roadmap.version <> '-- NON REGRESSION')";
    sql += " AND (Roadmap.version <> 'Jours Feries')";
    sql += " AND (Roadmap.isFAQ <> 1)";
    sql += " AND (Roadmap.idRoadmapPere = -1)";
    sql += " AND Roadmap.idVersionSt = ";

    if (Type.equals("Suivi"))
    {
      sql_fin = "  UNION SELECT  -1, 'zzCreer',99999999 as ordre";
      sql_fin += "  )";
      sql_fin += "  as mytable  order by ordre";
    }
    else
    {
      sql_fin += "  )";
      sql_fin += "  as mytable  order by ordre";
    }



  }
  
  public  void getListeProjetSuiviBySt(int lastJalon){
    sql  = "    select * from (";
    sql  += "     SELECT * from (";
    sql  += "     SELECT     Roadmap.idRoadmap, Roadmap.version, Roadmap.Ordre, Roadmap.idVersionSt";
    sql  += "      FROM         Roadmap INNER JOIN";
    sql  += "            VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    sql  += "            St ON VersionSt.stVersionSt = St.idSt";

    sql  += "      WHERE     (Roadmap.idJalon <>"+lastJalon+")   AND (Roadmap.standby = 0)";
    sql  += "      AND (idRoadmap not in (select idRoadmapPere from Roadmap))";

    sql  += "      AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)";
    sql  += "      AND (Roadmap.version <> '-- NON REGRESSION')";
    sql  += "      AND (Roadmap.version <> 'Jours Feries')";
    sql  += "      AND (Roadmap.isFAQ <> 1)";
    sql  += "      AND (Roadmap.idRoadmapPere = -1)";

    sql  += " UNION     ";
    sql  += "     SELECT     Roadmap.idRoadmap, Roadmap.version, Roadmap.Ordre, Roadmap.idVersionSt";
    sql  += "      FROM         Roadmap INNER JOIN";
    sql  += "            VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    sql  += "            St ON VersionSt.stVersionSt = St.idSt";

    sql  += "      WHERE     (Roadmap.standby = 0)";
    sql  += "      AND (idRoadmap  in (select idRoadmapPere from Roadmap where Roadmap.idJalon <>"+lastJalon+"))";

    sql  += "      AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)";
    sql  += "      AND (Roadmap.version <> '-- NON REGRESSION')";
    sql  += "      AND (Roadmap.version <> 'Jours Feries')";
    sql  += "      AND (Roadmap.isFAQ <> 1)   ";

    sql  += "      ) as mytable1";

    sql  += "     WHERE  idVersionSt = ";

      sql_fin = "  UNION SELECT  -1, 'zzCreer',99999999, -1 as ordre";
      sql_fin += "  )";
      sql_fin += "  as mytable  order by ordre";
    

  }  
  
  public  void getListeProjetNonSuiviBySt(int lastJalon){
    sql  = "    select * from (";
    sql  += "     SELECT * from (";
    sql  += "     SELECT     Roadmap.idRoadmap, Roadmap.version, Roadmap.Ordre, Roadmap.idVersionSt";
    sql  += "      FROM         Roadmap INNER JOIN";
    sql  += "            VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    sql  += "            St ON VersionSt.stVersionSt = St.idSt";

    sql  += "      WHERE     (Roadmap.idJalon = '"+lastJalon+"')   AND (Roadmap.standby = 0)";
    sql  += "      AND (idRoadmap not in (select idRoadmapPere from Roadmap))";

    sql  += "      AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)";
    sql  += "      AND (Roadmap.version <> '-- NON REGRESSION')";
    sql  += "      AND (Roadmap.version <> 'Jours Feries')";
    sql  += "      AND (Roadmap.isFAQ <> 1)";
    sql  += "      AND (Roadmap.idRoadmapPere = -1)";

    sql  += " UNION     ";
    sql  += "     SELECT     Roadmap.idRoadmap, Roadmap.version, Roadmap.Ordre, Roadmap.idVersionSt";
    sql  += "      FROM         Roadmap INNER JOIN";
    sql  += "            VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    sql  += "            St ON VersionSt.stVersionSt = St.idSt";

    sql  += "      WHERE     (Roadmap.standby = 0)";
    sql  += "      AND (idRoadmap  in (select idRoadmapPere from Roadmap where Roadmap.idJalon = '"+lastJalon+"'))";

    sql  += "      AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)";
    sql  += "      AND (Roadmap.version <> '-- NON REGRESSION')";
    sql  += "      AND (Roadmap.version <> 'Jours Feries')";
    sql  += "      AND (Roadmap.isFAQ <> 1)   ";

    sql  += "      ) as mytable1";

    sql  += "     WHERE  idVersionSt = ";

      sql_fin = "  UNION SELECT  -1, 'zzCreer',99999999, -1 as ordre";
      sql_fin += "  )";
      sql_fin += "  as mytable  order by ordre";
    

  }    
  
  public  void getListeProjetStandbyBySt(){
    sql  += "     SELECT * from (";
    sql  += "     SELECT     Roadmap.idRoadmap, Roadmap.version, Roadmap.Ordre, Roadmap.idVersionSt";
    sql  += "      FROM         Roadmap INNER JOIN";
    sql  += "            VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    sql  += "            St ON VersionSt.stVersionSt = St.idSt";

    sql  += "      WHERE     (Roadmap.standby = 1)";
    sql  += "      AND (idRoadmap not in (select idRoadmapPere from Roadmap))";

    sql  += "      AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)";
    sql  += "      AND (Roadmap.version <> '-- NON REGRESSION')";
    sql  += "      AND (Roadmap.version <> 'Jours Feries')";
    sql  += "      AND (Roadmap.isFAQ <> 1)";
    sql  += "      AND (Roadmap.idRoadmapPere = -1)";

    sql  += " UNION     ";
    sql  += "     SELECT     Roadmap.idRoadmap, Roadmap.version, Roadmap.Ordre, Roadmap.idVersionSt";
    sql  += "      FROM         Roadmap INNER JOIN";
    sql  += "            VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    sql  += "            St ON VersionSt.stVersionSt = St.idSt";

    sql  += "      WHERE (idRoadmap  in (select idRoadmapPere from Roadmap where Roadmap.standby = 1))";

    sql  += "      AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)";
    sql  += "      AND (Roadmap.version <> '-- NON REGRESSION')";
    sql  += "      AND (Roadmap.version <> 'Jours Feries')";
    sql  += "      AND (Roadmap.isFAQ <> 1)   ";

    sql  += "      ) as mytable1";

    sql  += "     WHERE  idVersionSt = ";

      sql_fin = "  order by ordre";
    

  }   
  
  public  void getListeProjetFilsBySt(String Type, int lastJalon){
    sql  = "select * from (";
    sql += "SELECT     Roadmap.idRoadmap, Roadmap.version, Roadmap.Ordre";
    sql += " FROM         Roadmap INNER JOIN";
    sql += "       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    sql += "       St ON VersionSt.stVersionSt = St.idSt";

    if (Type.equals("Suivi"))
    {
      sql += " WHERE     (Roadmap.idJalon <>"+lastJalon+")   AND (Roadmap.standby = 0)";
    }
    else if (Type.equals("non Suivi"))
    {
      sql += " WHERE     (Roadmap.idJalon ="+lastJalon+")   AND (Roadmap.standby = 0)";
    }
    else if (Type.equals("Standby"))
    {
      sql += " WHERE     (Roadmap.standby = 1)";
    }

    sql += " AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)";
    sql += " AND (Roadmap.version <> '-- NON REGRESSION')";
    sql += " AND (Roadmap.version <> 'Jours Feries')";
    sql += " AND (Roadmap.isFAQ <> 1)";
    sql += " AND (Roadmap.idRoadmapPere <> -1)";
    sql += " AND Roadmap.idRoadmapPere = ";

    if (Type.equals("Suivi"))
    {
      sql_fin = "  UNION SELECT  -1, 'zzCreer',99999999 as ordre";
      sql_fin += "  )";
      sql_fin += "  as mytable  order by ordre";
    }
    else
    {
      sql_fin += "  )";
      sql_fin += "  as mytable  order by ordre";
    }



  }  
  
  public  void getListeProjetByStWithBaseline(){
    sql  = "select * from (";
    sql += "SELECT     Roadmap.idRoadmap, Roadmap.version, Roadmap.Ordre";
    sql += " FROM         Roadmap INNER JOIN";
    sql += "       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    sql += "       St ON VersionSt.stVersionSt = St.idSt";
    sql += " WHERE     (Roadmap.Etat <> 'MES')   AND (Roadmap.standby = 0)";
    sql += " AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)";
    sql += " AND (Roadmap.version <> '-- NON REGRESSION')";
    sql += " AND (Roadmap.version <> 'Jours Feries')";
    sql += " AND (Roadmap.isFAQ <> 1)";
    sql += " AND Roadmap.idRoadmapPere = -1";    
    sql += " AND Roadmap.idVersionSt = ";
    sql_fin = "  UNION SELECT  -1, 'zzCreer',99999999 as ordre";
    sql_fin += "  )";
    sql_fin += "  as mytable  ";
    sql_fin += "  WHERE idRoadmap IN (SELECT Roadmap.idRoadmap FROM Baselines INNER JOIN Roadmap ON Baselines.idRoadmap = Roadmap.idRoadmap)";
    sql_fin += "  order by ordre";
    
  }  
  public  void getListeProjetBySt(){
    req_n2 = "SELECT     Roadmap.idRoadmap, Roadmap.version, Roadmap.version AS Expr1";
    req_n2 += " FROM         Roadmap INNER JOIN";
    req_n2 += "       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req_n2 += "       St ON VersionSt.stVersionSt = St.idSt";
    req_n2 += " WHERE     ((Roadmap.Etat <> 'TEST')   AND (St.isMeeting = 1) OR (Roadmap.Etat <> 'MES')   AND (St.isMeeting <> 1))";
    req_n2 += " AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)";
    req_n2 += " AND (Roadmap.version <> '-- NON REGRESSION')";
    req_n2 += " AND Roadmap.idVersionSt = ";

    this.sql=req_n2;
    this.sql_fin="";
}


  public  void getListeProjetEnCoursBySt(){
    req_n2 = "SELECT     idRoadmap, version,version";
    req_n2 += " FROM  Roadmap WHERE (LF_Month = - 1)";
    req_n2 += "  AND (Roadmap.isFAQ <> 1)";
    req_n2 += "  AND (Etat <> 'MES') AND (standby <> 1)";
    req_n2 += " AND (LF_Year = - 1) AND  idVersionSt  = ";
    req_n2_fin = " order by Ordre";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }
  public  void getListeBaselinesByProjet(){
    req_n2 = "SELECT     id, nom";
    req_n2 += " FROM         Baselines";
    req_n2 += " WHERE  idRoadmap = ";

    req_n2_fin = " order by date";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }  
  
    public  void getListeProjetnonSuiviBySt(){
    req_n2 = "SELECT     idRoadmap, version,version";
    req_n2 += " FROM  Roadmap WHERE (LF_Month = - 1)";
    req_n2 += "  AND (Roadmap.isFAQ <> 1)";
    req_n2 += "  AND (Etat = 'MES') AND (standby <> 1)";
    req_n2 += " AND (LF_Year = - 1) AND  idVersionSt  = ";
    req_n2_fin = " order by Ordre";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  } 
    
    public  void getListeProjetStandbyBySt2(){
    req_n2 = "SELECT     idRoadmap, version,version";
    req_n2 += " FROM  Roadmap WHERE (LF_Month = - 1)";
    req_n2 += "  AND (Roadmap.isFAQ <> 1)";
    req_n2 += "  AND (standby = 1)";
    req_n2 += " AND (LF_Year = - 1) AND  idVersionSt  = ";
    req_n2_fin = " order by Ordre";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }    

  public  void getListeProjetWithEb(){
    req_n2 = "SELECT     idRoadmap, version,version FROM  Roadmap WHERE  (LF_Month = - 1) AND (LF_Year = - 1) AND  idVersionSt  = ";
    req_n2="SELECT DISTINCT Roadmap.idRoadmap, Roadmap.version, Roadmap.version AS Expr1";
    req_n2+=" FROM         Roadmap INNER JOIN";
    req_n2+="          BesoinsUtilisateur ON Roadmap.idRoadmap = BesoinsUtilisateur.idRoadmap";
    req_n2+=" WHERE     (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND (Roadmap.isFAQ <> 1) AND Roadmap.idVersionSt = ";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }
  
  public  void getListeProjetWithFAQAndItem(){
    req_n2 = "SELECT     idRoadmap, version,version FROM  Roadmap WHERE  (LF_Month = - 1) AND (LF_Year = - 1) AND  idVersionSt  = ";
    req_n2="SELECT DISTINCT Roadmap.idRoadmap, Roadmap.version, Roadmap.version AS Expr1";
    req_n2+=" FROM         Roadmap INNER JOIN";
    req_n2+="          BesoinsUtilisateur ON Roadmap.idRoadmap = BesoinsUtilisateur.idRoadmap";
    req_n2+=" WHERE     (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)  AND (isFAQ=1) AND Roadmap.idVersionSt = ";
    
    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }  
  
  public  void getListeProjetWithFAQ(){
    req_n2 = "SELECT     idRoadmap, version,version FROM  Roadmap WHERE  (LF_Month = - 1) AND (LF_Year = - 1) AND  idVersionSt  = ";
    req_n2="SELECT DISTINCT Roadmap.idRoadmap, Roadmap.version, Roadmap.version AS Expr1";
    req_n2+=" FROM         Roadmap ";
    req_n2+=" WHERE     (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)  AND (isFAQ=1) AND Roadmap.idVersionSt = ";
    
    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }   

  public  void getListeProjetEnCoursNonRecurrentBySt(){
    req_n2 = "SELECT     idRoadmap, version,version FROM  Roadmap WHERE (Etat <> 'MES') AND (LF_Month = - 1) AND (LF_Year = - 1) AND (Roadmap.version <> '-- NON REGRESSION')  AND (Roadmap.isFAQ <> 1) AND  idVersionSt  = ";
    req_n2_fin = " ORDER BY Ordre";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }
  
  public  void getListeProjetNonRecurrentBySt(){
    req_n2 = "SELECT     idRoadmap, version,version FROM  Roadmap WHERE  (LF_Month = - 1) AND (LF_Year = - 1) AND (Roadmap.version <> '-- NON REGRESSION')  AND (Roadmap.isFAQ <> 1) AND  idVersionSt  = ";
    req_n2_fin = " ORDER BY Ordre";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  } 
  
    public  void getListeProjetNonRecurrentByStWithBesoinUtilisateur(){
    req_n2 = "SELECT    DISTINCT   Roadmap.idRoadmap, Roadmap.version, Roadmap.version AS Expr1, Ordre";
    req_n2 += " FROM         Roadmap INNER JOIN";
    req_n2 += "                       BesoinsUtilisateur ON Roadmap.idRoadmap = BesoinsUtilisateur.idRoadmap";
    req_n2 += " WHERE     (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND (Roadmap.version <> '-- NON REGRESSION') AND (Roadmap.isFAQ <> 1) AND Roadmap.idVersionSt =";
    req_n2_fin = " ORDER BY Ordre";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }

  public  void getListeProjetEnCoursByStAvecCategorieTest(){
    req_n2 = "SELECT DISTINCT Roadmap.idRoadmap, Roadmap.version, Roadmap.version AS Expr1";
    req_n2+=" FROM         Roadmap INNER JOIN";
    req_n2+="       categorieTest ON Roadmap.idRoadmap = categorieTest.idRoadmap";
    req_n2 += "  AND (Etat <> 'MES') AND (standby <> 1)";    
    req_n2+=" WHERE (LF_Month = - 1) AND (LF_Year = - 1) AND  Roadmap.idVersionSt  = ";

    this.sql=req_n2;

  }
  
  public  void getListeProjetNonSuiviByStAvecCategorieTest(){
    req_n2 = "SELECT DISTINCT Roadmap.idRoadmap, Roadmap.version, Roadmap.version AS Expr1";
    req_n2+=" FROM         Roadmap INNER JOIN";
    req_n2+="       categorieTest ON Roadmap.idRoadmap = categorieTest.idRoadmap";
    req_n2 += "  AND (Etat = 'MES') AND (standby <> 1)";    
    req_n2+=" WHERE (LF_Month = - 1) AND (LF_Year = - 1) AND     (Roadmap.version <> '-- NON REGRESSION') AND  Roadmap.idVersionSt  = ";

    this.sql=req_n2;

  } 
  
   public  void getListeProjetStandbyByStAvecCategorieTest(){
    req_n2 = "SELECT DISTINCT Roadmap.idRoadmap, Roadmap.version, Roadmap.version AS Expr1";
    req_n2+=" FROM         Roadmap INNER JOIN";
    req_n2+="       categorieTest ON Roadmap.idRoadmap = categorieTest.idRoadmap";
    req_n2 += "  AND (standby = 1)";    
    req_n2+=" WHERE (LF_Month = - 1) AND (LF_Year = - 1) AND     (Roadmap.version <> '-- NON REGRESSION') AND  Roadmap.idVersionSt  = ";

    this.sql=req_n2;

  }  

  public  void getListeProjetSuivi(){
    req_n1= "SELECT * FROM ";
    req_n1+=" (";
    req_n1+=" SELECT DISTINCT VersionSt.idVersionSt, St.nomSt + '-' + Roadmap.version as nomProjet";
    req_n1+=" FROM         St INNER JOIN";
    req_n1+="               VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    if (this.theCollaborateur.MOA.equals("yes"))
    {
      req_n1+="           Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
    }
    else
    {
      req_n1+="           Membre ON VersionSt.respMoeVersionSt = Membre.idMembre INNER JOIN";
    }
    req_n1+="               Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt INNER JOIN";
    req_n1+="               categorieTest ON Roadmap.idRoadmap = categorieTest.idRoadmap INNER JOIN";
    req_n1+="               Tests ON categorieTest.id = Tests.idCategorie";
    req_n1+=" WHERE     (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (Membre.LoginMembre = '"+this.theCollaborateur.Login+"') AND (St.isRecurrent = 0)  ";
    req_n1+="                AND (St.creerProjet = 1) AND (NOT (St.nomSt LIKE '**%')) AND (NOT (St.nomSt LIKE '---%')) AND ";
    req_n1+="               (Roadmap.version <> '-- NON REGRESSION')         ";

    req_n1+=" UNION ";

    req_n1+=" SELECT DISTINCT ListeST.idVersionSt, ListeST.nomSt + '-' + Roadmap.version as nomProjet";
    req_n1+=" FROM         Membre INNER JOIN";
    req_n1+="               EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
    req_n1+="               Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
    req_n1+="               ListeST ON Equipe.nom = ListeST.nomSt INNER JOIN";
    req_n1+="               Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt INNER JOIN";
    req_n1+="               categorieTest ON Roadmap.idRoadmap = categorieTest.idRoadmap INNER JOIN";
    req_n1+="               Tests ON categorieTest.id = Tests.idCategorie";
    req_n1+=" WHERE     (Membre.LoginMembre = '"+this.theCollaborateur.Login+"') AND (Membre.isProjet = 1) AND (ListeST.isRecurrent = 0) AND (Roadmap.Etat <> 'MES') AND ";
    req_n1+="               (NOT (ListeST.nomSt LIKE '**%')) AND (NOT (ListeST.nomSt LIKE '--%')) AND (Roadmap.version <> '-- NON REGRESSION') OR";
    req_n1+="               (Membre.LoginMembre = '"+this.theCollaborateur.Login+"') AND (ListeST.isRecurrent = 0)  AND (NOT (ListeST.nomSt LIKE '**%')) AND ";
    req_n1+="               (NOT (ListeST.nomSt LIKE '--%')) AND (Roadmap.version <> '-- NON REGRESSION') AND (Membre.isTest = 1)         ) ";
    req_n1+=" AS maTable ORDER BY nomProjet";

    sql=req_n1;
  }
 
  public  void getListeProjetCollaborateurSansCharge(){
   this.sql+="SELECT * FROM (";
this.sql+=" SELECT     Roadmap.idRoadmap, ListeST.nomSt + '-' + Roadmap.version as nomProjet,LoginMembre, isProjet, isActeur, creerProjet , Etat, version, dateT0, isRecurrent";
this.sql+=" FROM         Membre INNER JOIN";
this.sql+="                      EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
this.sql+="                      Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
this.sql+="                      ListeST ON Equipe.nom = ListeST.nomSt INNER JOIN";
this.sql+="                      Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";

this.sql+=" UNION";

this.sql+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version as nomProjet,LoginMembre, isProjet , isActeur, creerProjet, Etat, version, Roadmap.dateT0, isRecurrent";
this.sql+=" FROM         St INNER JOIN";
this.sql+="                      VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
this.sql+="                      Membre ON VersionSt.respMoeVersionSt = Membre.idMembre INNER JOIN";
this.sql+="                      Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt";

this.sql+=" UNION";

this.sql+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version as nomProjet,LoginMembre, isProjet , isActeur, creerProjet, Etat, version, Roadmap.dateT0, isRecurrent";
this.sql+=" FROM         St INNER JOIN";
this.sql+="                      VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
this.sql+="                      Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
this.sql+="                      Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt";

this.sql+="     )";
this.sql+="     AS maTable";
this.sql+=" WHERE     (LoginMembre = '"+this.theCollaborateur.Login+"')  AND YEAR (dateT0) = YEAR(GETDATE())  AND (isProjet = 1) AND (isActeur = 0)   AND (creerProjet = 1) AND (Etat <> 'MES') AND (Etat <> 'a demarrer') AND (version <> '-- NON REGRESSION')    AND  (isRecurrent = 0)";
this.sql+=" AND idRoadmap  not in (select PlanDeCharge.idRoadmap from PlanDeCharge where PlanDeCharge.idRoadmap is not null) ";

this.sql+="  order by nomProjet";      
  }  
  public  void getListeProjetSansCharge(){
    req_n1+="SELECT     Roadmap.idRoadmap, St.nomSt+'-'+Roadmap.version, Roadmap.etat";
    req_n1+="    FROM         Roadmap INNER JOIN";
    req_n1+="                    VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    if (theService.isMoe != 1)
    {
      req_n1+="                    Service ON VersionSt.serviceMoaVersionSt = Service.idService INNER JOIN";
    }
    else
    {
      req_n1+="                    Service ON VersionSt.serviceMoeVersionSt = Service.idService INNER JOIN";
    }
    req_n1+="                    St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req_n1+="                    SI ON VersionSt.siVersionSt = SI.idSI";
    req_n1+=" WHERE     (Roadmap.Etat <> 'MES') AND (Roadmap.Etat <> 'a demarrer') AND (Roadmap.Etat <> 'PROD') AND (Roadmap.standby <> 1)";
    req_n1+=" AND (Service.nomService = '"+theService.nom+"') AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1) AND ";
    req_n1+="                    (St.isMeeting <> 1) AND (St.isRecurrent <> 1) AND (YEAR(Roadmap.dateMep) >= "+anneeRef+") AND (YEAR(Roadmap.dateT0) >= "+anneeRef+") ";
    req_n1+="                    AND (YEAR(Roadmap.dateEB) >= "+anneeRef+") AND (YEAR(Roadmap.dateTest) >= "+anneeRef+")";
    req_n1+=" AND          Roadmap.idRoadmap NOT IN ";
    req_n1+=" (";
    req_n1+=" SELECT DISTINCT PlanDeCharge.idRoadmap";
    req_n1+=" FROM         Membre INNER JOIN";
    req_n1+="                    Service ON Membre.serviceMembre = Service.idService INNER JOIN";
    req_n1+="                   PlanDeCharge ON Membre.idMembre = PlanDeCharge.projetMembre";
    req_n1+="  WHERE     (Service.nomService = '"+theService.nom+"') AND (PlanDeCharge.anneeRef = "+anneeRef+")";
    req_n1+=" )  ";
    this.sql=req_n1;
  }

  public  void getListeProjetSansIdPo(){
    this.sql+="SELECT DISTINCT * FROM";
    this.sql+=" (";
    this.sql+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS projet";
    this.sql+="   FROM         Roadmap INNER JOIN";
    this.sql+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";

    this.sql+="                       Service ON VersionSt.serviceMoeVersionSt = Service.idService INNER JOIN";

    this.sql+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    this.sql+="                       SI ON VersionSt.siVersionSt = SI.idSI";
    this.sql+="    WHERE     (Roadmap.Etat <> 'MES') AND (Service.nomService = '"+theService.nom+"')  AND (Roadmap.standby <> 1) AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    this.sql+="                      (Roadmap.LF_Year = - 1) AND (Roadmap.idPO <= 0 OR";
    this.sql+="                       Roadmap.idPO IS NULL) AND (St.isMeeting <> 1) AND (St.isRecurrent <> 1)";
    this.sql+="    AND (YEAR(Roadmap.dateMep) >= "+anneeRef+") AND  (YEAR(Roadmap.dateT0) >= "+anneeRef+") AND (YEAR(Roadmap.dateEB) >= "+anneeRef+") AND (YEAR(Roadmap.dateTest) >= "+anneeRef+")";
    this.sql+="    AND (Roadmap.standby <> 1)                      ";

    this.sql+=" UNION ";
    this.sql+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS projet";
    this.sql+="    FROM         Roadmap INNER JOIN";
    this.sql+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";

    this.sql+="                        Service ON VersionSt.serviceMoaVersionSt = Service.idService INNER JOIN";

    this.sql+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    this.sql+="                       SI ON VersionSt.siVersionSt = SI.idSI";
    this.sql+="    WHERE     (Roadmap.Etat <> 'MES') AND (Service.nomService = '"+theService.nom+"')  AND (Roadmap.standby <> 1) AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    this.sql+="                       (Roadmap.LF_Year = - 1) AND (Roadmap.idPO <= 0 OR";
    this.sql+="                      Roadmap.idPO IS NULL) AND (St.isMeeting <> 1) AND (St.isRecurrent <> 1)";
    this.sql+="    AND (YEAR(Roadmap.dateMep) >= "+anneeRef+") AND  (YEAR(Roadmap.dateT0) >= "+anneeRef+") AND (YEAR(Roadmap.dateEB) >= "+anneeRef+") AND (YEAR(Roadmap.dateTest) >= "+anneeRef+")";
    this.sql+="    AND (Roadmap.standby <> 1)                      ";
    
    this.sql+=" UNION ";
    
    this.sql+=" SELECT     Surconsommation.idRoadmap, '@1@' + St.nomSt + '-' + Surconsommation.version AS projet";
    this.sql+=" FROM         Surconsommation INNER JOIN";
    this.sql+="                       Roadmap ON Surconsommation.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    this.sql+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    this.sql+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    this.sql+="                       Service ON VersionSt.serviceMoeVersionSt = Service.idService";
    this.sql+=" WHERE     (Service.idService = "+theService.id+")";

    this.sql+=" UNION";
                      
    this.sql+=" SELECT     Surconsommation.idRoadmap, '@1@' + St.nomSt + '-' + Surconsommation.version AS projet";
    this.sql+=" FROM         Surconsommation INNER JOIN";
    this.sql+="                       Roadmap ON Surconsommation.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    this.sql+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    this.sql+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    this.sql+="                       Service ON VersionSt.serviceMoaVersionSt = Service.idService";
    this.sql+=" WHERE     (Service.idService = "+theService.id+")    ";    

    this.sql+="   ) as myTAble";
    this.sql+= " ORDER BY projet";

  }
  
 
  public  void getListeProjetCollaborateurSansIdPo(){
    this.sql+=" select distinct * from";
    this.sql+=" (";    
    this.sql+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet";
    this.sql+=" FROM         Roadmap INNER JOIN";
    this.sql+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    this.sql+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    this.sql+="                       Membre ON VersionSt.respMoaVersionSt = Membre.idMembre";
    this.sql+=" WHERE     (Membre.LoginMembre = '"+theCollaborateur.Login+"') AND (Roadmap.Etat <> 'MES') AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    this.sql+="                       (Roadmap.LF_Year = - 1) AND (St.isRecurrent <> 1) AND (Roadmap.standby = 0) ";
    this.sql+=" 					  AND (YEAR(Roadmap.dateMep) >= YEAR(GETDATE())) AND  (YEAR(Roadmap.dateT0) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateEB) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateTest) >= YEAR(GETDATE()))";
    this.sql+=" 					  AND (Roadmap.standby <> 1)     ";
    this.sql+="                       AND (Roadmap.idPO <= 0 OR Roadmap.idPO IS NULL) ";  
    this.sql+=" UNION";
    this.sql+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet";
    this.sql+=" FROM         Roadmap INNER JOIN";
    this.sql+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    this.sql+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    this.sql+="                       Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
    this.sql+=" WHERE     (Membre.LoginMembre = '"+theCollaborateur.Login+"') AND (Roadmap.Etat <> 'MES') AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    this.sql+="                       (Roadmap.LF_Year = - 1) AND (St.isRecurrent <> 1) AND (Roadmap.standby = 0)";
    this.sql+=" 					  AND (YEAR(Roadmap.dateMep) >= YEAR(GETDATE())) AND  (YEAR(Roadmap.dateT0) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateEB) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateTest) >= YEAR(GETDATE()))";
    this.sql+=" 					  AND (Roadmap.standby <> 1)                       ";
    this.sql+="                       AND (Roadmap.idPO <= 0 OR Roadmap.idPO IS NULL) ";
    this.sql+=" UNION";
    this.sql+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet";
    this.sql+=" FROM         Roadmap INNER JOIN";
    this.sql+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    this.sql+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    this.sql+="                       Equipe ON St.nomSt = Equipe.nom INNER JOIN";
    this.sql+="                       EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    this.sql+="                       Membre ON EquipeMembre.idMembre = Membre.idMembre";
    this.sql+=" WHERE    ";
    this.sql+="                       (Roadmap.Etat <> 'MES') AND (Membre.LoginMembre = '"+theCollaborateur.Login+"') AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    this.sql+="                      (Roadmap.LF_Year = - 1) AND (Roadmap.standby = 0) ";
    this.sql+=" 					  AND (YEAR(Roadmap.dateMep) >= YEAR(GETDATE())) AND  (YEAR(Roadmap.dateT0) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateEB) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateTest) >= YEAR(GETDATE()))";
    this.sql+=" 					  AND (Roadmap.standby <> 1)                                       ";
    this.sql+="                       AND (Roadmap.idPO <= 0 OR Roadmap.idPO IS NULL)";
    
    this.sql+=" UNION";    
    
    this.sql+=" SELECT     dbo.Surconsommation.idRoadmap, '@1@' + dbo.St.nomSt + '-' + dbo.Surconsommation.version AS nomProjet";    
    this.sql+=" FROM         dbo.Surconsommation INNER JOIN";    
    this.sql+="                       dbo.Roadmap ON dbo.Surconsommation.idRoadmap = dbo.Roadmap.idRoadmap INNER JOIN";    
    this.sql+="                       dbo.VersionSt ON dbo.Roadmap.idVersionSt = dbo.VersionSt.idVersionSt INNER JOIN";    
    this.sql+="                       dbo.Membre ON dbo.VersionSt.respMoeVersionSt = dbo.Membre.idMembre INNER JOIN";    
    this.sql+="                       dbo.St ON dbo.VersionSt.stVersionSt = dbo.St.idSt";    
    this.sql+=" WHERE     (dbo.Membre.LoginMembre = '"+theCollaborateur.Login+"')";    

    this.sql+=" UNION";    

    this.sql+=" SELECT     dbo.Surconsommation.idRoadmap, '@1@' + dbo.St.nomSt + '-' + dbo.Surconsommation.version AS nomProjet";    
    this.sql+=" FROM         dbo.Surconsommation INNER JOIN";    
    this.sql+="                       dbo.Roadmap ON dbo.Surconsommation.idRoadmap = dbo.Roadmap.idRoadmap INNER JOIN";    
    this.sql+="                       dbo.VersionSt ON dbo.Roadmap.idVersionSt = dbo.VersionSt.idVersionSt INNER JOIN";    
    this.sql+="                       dbo.Membre ON dbo.VersionSt.respMoaVersionSt = dbo.Membre.idMembre INNER JOIN";    
    this.sql+="                       dbo.St ON dbo.VersionSt.stVersionSt = dbo.St.idSt";    
    this.sql+=" WHERE     (dbo.Membre.LoginMembre = '"+theCollaborateur.Login+"')";    

    this.sql+=" UNION";    

    this.sql+=" SELECT     dbo.Roadmap.idRoadmap, '@1@' + dbo.St.nomSt + '-' + dbo.Roadmap.version AS nomProjet";    
    this.sql+=" FROM         dbo.Roadmap INNER JOIN";    
    this.sql+="                       dbo.VersionSt ON dbo.Roadmap.idVersionSt = dbo.VersionSt.idVersionSt INNER JOIN";    
    this.sql+="                       dbo.St ON dbo.VersionSt.stVersionSt = dbo.St.idSt INNER JOIN";    
    this.sql+="                       dbo.Equipe ON dbo.St.nomSt = dbo.Equipe.nom INNER JOIN";    
    this.sql+="                       dbo.EquipeMembre ON dbo.Equipe.id = dbo.EquipeMembre.idMembreEquipe INNER JOIN";    
    this.sql+="                       dbo.Membre ON dbo.EquipeMembre.idMembre = dbo.Membre.idMembre INNER JOIN";    
    this.sql+="                       dbo.Surconsommation ON dbo.Roadmap.idRoadmap = dbo.Surconsommation.idRoadmap";    
    this.sql+=" WHERE     (dbo.Roadmap.Etat <> 'MES') AND (dbo.Membre.LoginMembre = '"+theCollaborateur.Login+"') AND (dbo.St.isMeeting <> 1) AND (dbo.Roadmap.LF_Month = - 1) AND (dbo.Roadmap.LF_Year = - 1) AND ";    
    this.sql+="                       (dbo.Roadmap.standby = 0) AND (dbo.Roadmap.standby <> 1)  "; 
    
    this.sql+=" )";      
    this.sql+=" as mytable ";
  
    this.sql+=" order by     nomProjet";

  }  
  
  public  void getListeProjetCollaborateurSansIdPo2(){
    this.sql+=" select distinct * from";
    this.sql+=" (";    
    this.sql+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet";
    this.sql+=" FROM         Roadmap INNER JOIN";
    this.sql+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    this.sql+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    this.sql+="                       Membre ON VersionSt.respMoaVersionSt = Membre.idMembre";
    this.sql+=" WHERE     (Membre.LoginMembre = '"+theCollaborateur.Login+"') AND (Roadmap.Etat <> 'MES') AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    this.sql+="                       (Roadmap.LF_Year = - 1) AND (St.isRecurrent <> 1) AND (Roadmap.standby = 0) ";
    this.sql+=" 					  AND (YEAR(Roadmap.dateMep) >= YEAR(GETDATE())) AND  (YEAR(Roadmap.dateT0) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateEB) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateTest) >= YEAR(GETDATE()))";
    this.sql+=" 					  AND (Roadmap.standby <> 1)     ";
    this.sql+="                       AND (Roadmap.idPO <= 0 OR Roadmap.idPO IS NULL) ";  
    this.sql+=" UNION";
    this.sql+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet";
    this.sql+=" FROM         Roadmap INNER JOIN";
    this.sql+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    this.sql+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    this.sql+="                       Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
    this.sql+=" WHERE     (Membre.LoginMembre = '"+theCollaborateur.Login+"') AND (Roadmap.Etat <> 'MES') AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    this.sql+="                       (Roadmap.LF_Year = - 1) AND (St.isRecurrent <> 1) AND (Roadmap.standby = 0)";
    this.sql+=" 					  AND (YEAR(Roadmap.dateMep) >= YEAR(GETDATE())) AND  (YEAR(Roadmap.dateT0) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateEB) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateTest) >= YEAR(GETDATE()))";
    this.sql+=" 					  AND (Roadmap.standby <> 1)                       ";
    this.sql+="                       AND (Roadmap.idPO <= 0 OR Roadmap.idPO IS NULL) ";
    this.sql+=" UNION";
    this.sql+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet";
    this.sql+=" FROM         Roadmap INNER JOIN";
    this.sql+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    this.sql+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    this.sql+="                       Equipe ON St.nomSt = Equipe.nom INNER JOIN";
    this.sql+="                       EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    this.sql+="                       Membre ON EquipeMembre.idMembre = Membre.idMembre";
    this.sql+=" WHERE    ";
    this.sql+="                       (Roadmap.Etat <> 'MES') AND (Membre.LoginMembre = '"+theCollaborateur.Login+"') AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    this.sql+="                      (Roadmap.LF_Year = - 1) AND (Roadmap.standby = 0) ";
    this.sql+=" 					  AND (YEAR(Roadmap.dateMep) >= YEAR(GETDATE())) AND  (YEAR(Roadmap.dateT0) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateEB) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateTest) >= YEAR(GETDATE()))";
    this.sql+=" 					  AND (Roadmap.standby <> 1)                                       ";
    this.sql+="                       AND (Roadmap.idPO <= 0 OR Roadmap.idPO IS NULL)";
    this.sql+=" )";      
    this.sql+=" as mytable ";
  
    this.sql+=" order by     nomProjet";

  } 
  

  public  void getListeProjetsByStateTaches(){
    req_n2= "         SELECT * FROM ( ";
    req_n2+= "    SELECT DISTINCT ";
    req_n2+= "                 Roadmap.idRoadmap + 30000 * actionSuivi.idEtat AS idRoadmapEtat, St.nomSt + '-' + Roadmap.version AS projet, actionSuivi.idEtat ";
    req_n2+= " FROM         St INNER JOIN";
    req_n2+= "                 VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req_n2+= "                 Membre ON VersionSt.respMoeVersionSt = Membre.idMembre INNER JOIN";
    req_n2+= "                 Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt INNER JOIN";
    req_n2+= "                 actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap";
    req_n2+= " WHERE     (VersionSt.etatFicheVersionSt = 3) AND (Roadmap.Etat <> 'MES') AND (VersionSt.etatVersionSt <> 4) AND (St.creerProjet = 1) AND ";
    req_n2+= "                 (VersionSt.idVersionSt IN";
    req_n2+= "                     (SELECT     idVersionSt";
    req_n2+= "                       FROM          Roadmap AS Roadmap_1";
    req_n2+= "                       WHERE      (version <> '-- NON REGRESSION'))) AND (St.isMeeting <> 1)  AND  (actionSuivi.acteur <> '') AND (Membre.idMembre = "+theCollaborateur.id+")";

    req_n2+= " UNION";

    req_n2+= " SELECT DISTINCT ";
    req_n2+= "                 Roadmap.idRoadmap + 30000 * actionSuivi.idEtat AS idRoadmapEtat, St.nomSt + '-' + Roadmap.version AS projet, actionSuivi.idEtat ";
    req_n2+= " FROM         St INNER JOIN";
    req_n2+= "                 VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req_n2+= "                 Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
    req_n2+= "                 Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt INNER JOIN";
    req_n2+= "                 actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap";
    req_n2+= " WHERE     (VersionSt.etatFicheVersionSt = 3) AND (Roadmap.Etat <> 'MES') AND (VersionSt.etatVersionSt <> 4) AND (St.creerProjet = 1) AND ";
    req_n2+= "                 (VersionSt.idVersionSt IN";
    req_n2+= "                     (SELECT     idVersionSt";
    req_n2+= "                       FROM          Roadmap AS Roadmap_1";
    req_n2+= "                       WHERE      (version <> '-- NON REGRESSION'))) AND (St.isMeeting <> 1) AND  (actionSuivi.acteur <> '') AND (Membre.idMembre = "+theCollaborateur.id+")                            ";
    req_n2+= "    )  AS maTable WHERE idEtat=";
    req_n2_fin =" ORDER BY projet";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;
  }

  public  void getListeProjetsByStateTachesByLogin(){
    req_n2= "         SELECT * FROM ( ";
    req_n2+= "    SELECT DISTINCT (Roadmap.idRoadmap + 30000*actionSuivi.idEtat) as idRoadmapEtat, (St.nomSt +'-'+roadmap.version) as projet, actionSuivi.idEtat, St.isRecurrent ";
    req_n2+= "    FROM         St INNER JOIN ";
    req_n2+= "                  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN ";

    req_n2+= "                  Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt INNER JOIN ";
    req_n2+= "                  actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap ";
    req_n2+= "    WHERE     (VersionSt.etatFicheVersionSt = 3) AND (actionSuivi.acteur LIKE '%"+theCollaborateur.Login+"%') AND (Roadmap.Etat <> 'MES') AND (VersionSt.etatVersionSt <> 4)  AND (St.creerProjet = 1) AND  ";
    req_n2+= "                  (VersionSt.idVersionSt IN ";
    req_n2+= "                      (SELECT     idVersionSt ";
    req_n2+= "                        FROM          Roadmap AS Roadmap_1 ";
    req_n2+= "                        WHERE      (version <> '-- NON REGRESSION') )) AND (St.isMeeting <> 1)  ";

    req_n2+= "    UNION  ";

    req_n2+= "    SELECT DISTINCT (Roadmap.idRoadmap+ 30000*actionSuivi.idEtat) as idRoadmapEtat, (ListeST.nomSt+'-'+Roadmap.version) as projet, actionSuivi.idEtat, ListeST.isRecurrent ";
    req_n2+= "    FROM         Membre INNER JOIN ";
    req_n2+= "                  EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN ";
    req_n2+= "                  Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ";
    req_n2+= "                  ListeST ON Equipe.nom = ListeST.nomSt INNER JOIN ";
    req_n2+= "                  Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt INNER JOIN ";
    req_n2+= "                  actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap ";
    req_n2+= "    WHERE     (Membre.LoginMembre LIKE '%"+theCollaborateur.Login+"%') AND (Roadmap.Etat <> 'MES') AND (actionSuivi.acteur LIKE '%"+theCollaborateur.Login+"%') AND (ListeST.creerProjet = 1) AND (ListeST.isActeur <> 1) AND  ";
    req_n2+= "                  (ListeST.isMeeting <> 1) AND (ListeST.idVersionSt IN ";
    req_n2+= "                      (SELECT     idVersionSt ";
    req_n2+= "                        FROM          Roadmap AS Roadmap_1 ";
    req_n2+= "                        WHERE      (version <> '-- NON REGRESSION') ))  ";

    req_n2+= "    )  AS maTable WHERE isRecurrent=0 AND idEtat=";
    req_n2_fin =" ORDER BY projet";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;
  }

// --------------------------------------------------------------------------------------------------------//

// ------------------------------------------ Gestion des @Collaborateurs ----------------------------------------------//
  public  void getListeMembresByEquipe(){
    req_n2 = "SELECT     EquipeMembre.idMembre, Membre.nomMembre, Membre.fonctionMembre FROM    EquipeMembre INNER JOIN   Membre ON EquipeMembre.idMembre = Membre.idMembre WHERE     (EquipeMembre.type = 'Collaborateur') AND EquipeMembre.idMembreEquipe = ";
    req_n2_fin = " ORDER BY nomMembre ASC";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;
  }

  public  void getListeMembreByService(){
    req_n3 = "SELECT idMembre, nomMembre FROM Membre WHERE serviceMembre=";
    req_n3_fin = " ORDER BY nomMembre asc";

    this.sql=req_n3;
    this.sql_fin=req_n3_fin;
  }
  
  public  void getListeMembreByServiceWithCreation(){
    this.sql= "select * from (";      
    this.sql += "SELECT idMembre, nomMembre FROM Membre WHERE serviceMembre=";
    this.sql_fin = "  UNION SELECT  -1, 'zzCreer')";
    this.sql_fin+= " as mytable";       
    this.sql_fin += " ORDER BY nomMembre asc";

  }  
  
  public  void getListeFields(){
    this.sql= "select * from (";      
    this.sql += "SELECT     id, nom, position FROM  Field WHERE  idDatasource =";
    this.sql_fin = "  UNION SELECT  -1, 'zzCreer', 999999 as position)";
    this.sql_fin+= " as mytable";       
    this.sql_fin += " ORDER BY position asc";

  }   

  public  void getListeMembreMoe(){
    req_n1 = "SELECT distinct idMembre, nomMembre FROM Membre,VersionSt where idMembre= respMoeVersionSt and etatFicheVersionSt=3 AND etatVersionSt != 4 and suivVersionSt is null ORDER BY nomMembre";
    this.sql=req_n1;
  }

// --------------------------------------------------------------------------------------------------------//

// ------------------------------------------ Gestion des @Services ----------------------------------------------//
  public  void getListeServicesByDirections(String Type){
    req_n2 = "SELECT     idService, nomService, descService, dirService FROM   Service WHERE (typeService LIKE '%"+Type+"%') AND     (dirService = ";
    req_n2_fin = ") ORDER BY nomService asc";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;
  }
  
  public  void getListeServicesByDirectionsWithCreation(String Type){
    this.sql= "select * from (";
    this.sql += "SELECT     idService, nomService FROM   Service WHERE (typeService LIKE '%"+Type+"%') AND     dirService = ";
    this.sql_fin = "  UNION SELECT  -1, 'zzCreer')";
    this.sql_fin+= " as mytable";    
    this.sql_fin += " ORDER BY nomService asc";

  }  
  
    public  void getListeDatasources(){
    this.sql= "select * from (";
    this.sql += "  SELECT     id, sens + '-' + nom + ' (' + type + ' )' as nom, ordre FROM datasource WHERE   idTemplate = ";
    this.sql_fin = "  UNION SELECT  -1, 'zzCreer', 999999 as ordre)";
    this.sql_fin+= " as mytable";    
    this.sql_fin += " ORDER BY ordre asc";

  }
  
    public  void getListeServicesByLogin(String Login){
    this.sql= "SELECT     Service_2.idService, Service_2.nomService";
    this.sql+= " FROM         Membre INNER JOIN";
    this.sql+= "                       Service ON Membre.serviceMembre = Service.idService INNER JOIN";
    this.sql+= "                       Service AS Service_1 ON Service.idServicePere = Service_1.idService INNER JOIN";
    this.sql+= "                       Service AS Service_2 ON Service_1.idService = Service_2.idServicePere";
    this.sql+= " WHERE     (Membre.LoginMembre = '"+Login+"')";
    this.sql+= " ORDER BY Service_2.nomService";

  }
// --------------------------------------------------------------------------------------------------------//

// ------------------------------------------ Gestion des @Directions ----------------------------------------------//
   public  void getListeLignesBudget(){
    req_n1 = "SELECT  Annee, nom FROM   Budgets ORDER BY Annee";
    this.sql=req_n1;
  }
   
   public  void getListeLignesBudgetWithCreation(){
    req_n1 = "select * from (";
    req_n1+=" SELECT Annee, nom, Annee as Ordre FROM   Budgets  ";
    req_n1+=" union";
    req_n1+=" select -1, 'zzCreer', 999999 as Ordre";
    req_n1+=" )";
    req_n1+=" as myTable";
    req_n1+=" ORDER BY Ordre ASC";

    this.sql=req_n1;       
       
  }   
   
   public  void getListeServiceByAnnee(){
    this.sql = "SELECT  DISTINCT   Service.idService, Service.nomService";
    this.sql+= " FROM         PlanOperationnelClient INNER JOIN";
    this.sql+= "                       Service ON PlanOperationnelClient.Service = Service.nomServiceImputations";
    this.sql+= " WHERE     PlanOperationnelClient.Annee = ";
    this.sql_fin = " ORDER BY Service.nomService";
  } 
   
   public  void getListeServiceByAnnee2(){
    this.sql = "SELECT     Service.idService, Service.nomService";
    this.sql+= " FROM         BudgetGlobalService INNER JOIN";
    this.sql+= "                       BudgetGlobal ON BudgetGlobalService.idBudgetGlobal = BudgetGlobal.id INNER JOIN";
    this.sql+= "                       Service ON BudgetGlobalService.idService = Service.idService INNER JOIN";
    this.sql+= "                       Budgets ON BudgetGlobal.Annee = Budgets.Annee";
    this.sql+= " WHERE     BudgetGlobal.Annee =";
    this.sql_fin = " ORDER BY Service.nomService";

  }    
   
   public  void getListeLignesBudgetByAnnee2(){
    this.sql = "select * from (";       
    this.sql+= " SELECT     idWebPo,  CAST(idWebPo AS VARCHAR(50)) + '-' + NomProjet AS ligneBudget";
    this.sql+=" FROM         PlanOperationnelClient";
    this.sql+=" WHERE  Annee = ";
    
    this.sql_fin="  union";
    this.sql_fin+=" select -1, 'zzCreer' AS ligneBudget ";
    this.sql_fin+=" )";
    this.sql_fin+=" as myTable";   
    this.sql_fin+=" ORDER BY ligneBudget";

  } 
   
   public  void getListeLignesBudgetByAnnee(){
    this.sql = "select * from (";       
    this.sql+= " SELECT     id,  CAST(idBudget AS VARCHAR(50)) + '-' + NomProjet AS ligneBudget";
    this.sql+=" FROM         BudgetGlobal";
    this.sql+=" WHERE  Annee = ";
    
    this.sql_fin="  union";
    this.sql_fin+=" select -1, 'zzCreer' AS ligneBudget ";
    this.sql_fin+=" )";
    this.sql_fin+=" as myTable";   
    this.sql_fin+=" ORDER BY ligneBudget";

  }    
   
    public  void getListeDirections(){
    req_n1 = "SELECT     idDirection, nomDirection, libelleDirection FROM Direction ORDER BY nomDirection";
    this.sql=req_n1;
  }

  public  void getListeDirectionsPlusCreation(){
    req_n1 = "select * from (";
    req_n1+=" SELECT idDirection, nomDirection FROM Direction WHERE  (nomDirection <> 'Autre')";
    req_n1+=" union";
    req_n1+=" select -1, 'zzCreer'";
    req_n1+=" )";
    req_n1+=" as myTable";
    req_n1+=" order by nomDirection";

    this.sql=req_n1;
  }
  
  public  void getListeTemplates(){
    req_n1 = "select * from (";
    req_n1+=" SELECT     id, nom, ordre FROM  template";
    req_n1+=" union";
    req_n1+=" select -1, 'zzCreer', 999999 as ordre";
    req_n1+=" )";
    req_n1+=" as myTable";
    req_n1+=" order by ordre";

    this.sql=req_n1;
  }  

// --------------------------------------------------------------------------------------------------------//

// ------------------------------------------ Gestion des @OM ----------------------------------------------//
  public  void getListeOmByKey(String searchKey){
    req_n1 = "SELECT idObjetMetier, nomObjetMetier, nomObjetMetier FROM ObjetMetier WHERE (nomObjetMetier LIKE '%"+searchKey+"%') ORDER BY nomObjetMetier";

    this.sql=req_n1;

}

  public  void getListeOmByInterfaces(int typeOM){
    req_n3 = "SELECT     ObjetMetier.idObjetMetier, ObjetMetier.nomObjetMetier, AppliIcone.formeTypeAppli";
    req_n3 += " FROM         Inter_OM INNER JOIN";
    req_n3 += "           Interface ON Inter_OM.interInter_OM = Interface.idInterface INNER JOIN";
    req_n3 += "           ObjetMetier ON Inter_OM.omInter_OM = ObjetMetier.idObjetMetier INNER JOIN";
    req_n3 += "           AppliIcone ON ObjetMetier.idAppliIcone = AppliIcone.id";
    req_n3 += " WHERE     (ObjetMetier.typeOM = "+typeOM+")";
    req_n3 += " AND     Interface.idInterface = ";

    req_n3 = "SELECT * FROM (";
    req_n3 += "SELECT     ObjetMetier.idObjetMetier, ObjetMetier.nomObjetMetier";
    req_n3 += " FROM         Inter_OM INNER JOIN";
    req_n3 += "           Interface ON Inter_OM.interInter_OM = Interface.idInterface INNER JOIN";
    req_n3 += "           ObjetMetier ON Inter_OM.omInter_OM = ObjetMetier.idObjetMetier ";
    req_n3 += " WHERE     (ObjetMetier.typeOM = "+typeOM+")";
    req_n3 += " AND     Interface.idInterface = ";

    req_n3_fin=" UNION SELECT  0, 'zz-- Cr&eacute;er'";
    req_n3_fin+=" ) as myTable order by nomObjetMetier asc";

  this.sql=req_n3;
  this.sql_fin=req_n3_fin;

  }

  public  void getListeOm(){
    req_n1 =  "SELECT idObjetMetier, nomObjetMetier,idObjetMetier FROM ObjetMetier ORDER BY nomObjetMetier";
    this.sql=req_n1;
  }
  
  public  void getListeOmWithRelations(){
    this.sql =  "select distinct idObjetMetier, nomObjetMetier from";
    this.sql +=  " SELECT     ObjetMetier.idObjetMetier, ObjetMetier.nomObjetMetier";
    this.sql +=  " FROM         ObjetMetier INNER JOIN";
    this.sql +=  "                       RelationsOM ON ObjetMetier.idObjetMetier = RelationsOM.origineOM";

    this.sql +=  " union";

    this.sql +=  " SELECT     ObjetMetier.idObjetMetier, ObjetMetier.nomObjetMetier";
    this.sql +=  " FROM         ObjetMetier INNER JOIN";
    this.sql +=  "                       RelationsOM ON ObjetMetier.idObjetMetier = RelationsOM.destinationOM";
    this.sql +=  "                       ) ";
    this.sql +=  " as mytable order by nomObjetMetier";

  }  
  
   public  void getListeOmWithCirculation(){
    this.sql =  "SELECT DISTINCT ObjetMetier.idObjetMetier, ObjetMetier.nomObjetMetier, ObjetMetier.isShortcut";
    this.sql +=  " FROM         Inter_OM INNER JOIN";
    this.sql +=  "                       Interface ON Inter_OM.interInter_OM = Interface.idInterface INNER JOIN";
    this.sql +=  "                       ObjetMetier ON Inter_OM.omInter_OM = ObjetMetier.idObjetMetier INNER JOIN";
    this.sql +=  "                       GraphCirculationOm ON ObjetMetier.idObjetMetier = GraphCirculationOm.idOm";
    this.sql +=  " ORDER BY ObjetMetier.nomObjetMetier";

  }

  public  void getListeFamilleOm(){
    req_n1 = "";
    req_n1+=" SELECT     idFamilleOM, nomFamilleOM";
    req_n1+=" FROM         FamilleOM ORDER BY nomFamilleOM";
    this.sql=req_n1;
  }
  
  public  void getListeFamilleDocumentation(){
    
    req_n1 = "select * from (";
    req_n1+=" SELECT idTypeDoc, nomType FROM TypeDocumentation  ";
    req_n1+=" union";
    req_n1+=" select -1, 'zzCreer'";
    req_n1+=" )";
    req_n1+=" as myTable";
    req_n1+=" ORDER BY nomType ASC";    
    
    this.sql=req_n1;    
  }  

  public  void getListeFamilleOmPlusCreation(){
    req_n1 = "select * from (";
    req_n1+=" SELECT     idFamilleOM, nomFamilleOM";
    req_n1+=" FROM         FamilleOM";
    req_n1+=" UNION SELECT  -1, 'zzCreer')";
    req_n1+=" as mytable order by nomFamilleOM";

    this.sql=req_n1;
  }

  public  void getListePackageOmPlusCreation(){
    this.sql =" select * from (";
    this.sql+="     SELECT     idPackage, nomPackage, nomFamille";
    this.sql+="      FROM         packageOM";
    this.sql+="      WHERE  nomFamille =";
    this.sql_fin="       UNION SELECT  -1, 'zzCreer', 'ZZZZ' as nomFamille";
    this.sql_fin+="       ) as mytable order by nomFamille";
  }

  public  void getListeOmByPackage(){
    req_n3 = "SELECT idObjetMetier, nomObjetMetier FROM ObjetMetier WHERE package=";
    req_n3_fin = "ORDER BY nomObjetMetier ASC";

    this.sql=req_n3;
    this.sql_fin=req_n3_fin;
    
  }
  
  public  void getListeOmByPackageWithCreation(){
    this.sql= "select * from ("; 
    this.sql += "SELECT idObjetMetier, nomObjetMetier FROM ObjetMetier WHERE package=";
    this.sql_fin = "  UNION SELECT  -1, 'zzCreer' as nomObjetMetier)";
    this.sql_fin+= " as mytable";    
    sql_fin += " ORDER BY nomObjetMetier ASC";
  }  

  public  void getListePackageByFamilleOm(){
  req_n2 = "SELECT DISTINCT  idPackage, nomPackage FROM         packageOM WHERE     nomFamille = ";
  req_n2_fin = " ORDER BY packageOM.nomPackage ASC";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;
  }
  
  public  void getListePackageByFamilleOm2(){
  req_n2 = "SELECT DISTINCT ObjetMetier.package, packageOM.nomPackage, ObjetMetier.famObjetMetier FROM  ObjetMetier INNER JOIN  packageOM ON ObjetMetier.package = packageOM.idPackage WHERE ObjetMetier.famObjetMetier =";
  req_n2_fin = "ORDER BY packageOM.nomPackage ASC";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;
  }  

  public  void getListeOmByState(){
    req_n2 = "SELECT idObjetMetier, nomObjetMetier FROM ObjetMetier WHERE typeEtatObjetMetier=";
    req_n2_fin = "ORDER BY nomObjetMetier asc";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }
// --------------------------------------------------------------------------------------------------------//

// ------------------------------------------ Gestion des @Processus ----------------------------------------------//
  public  void getListeProcessusByKey(String searchKey){
   req_n1 = "SELECT idProcessus, nomProcessus, nomProcessus FROM Processus WHERE     (nomProcessus LIKE '%"+searchKey+"%')  ORDER BY nomProcessus";

    this.sql=req_n1;

}

  public  void getListeProcessusByMacroSt(){
    req_n3 = "SELECT     idProcessus, nomProcessus, nomProcessus FROM   Processus WHERE     (Type = 'Processus') AND ProcessusMacroSt =";
    req_n3_fin = "ORDER BY Processus.nomProcessus asc";

    this.sql=req_n3;
    this.sql_fin=req_n3_fin;
  }
  
  public  void getListeProcessusByMacroStWithCreation(){
    
    this.sql= "select * from (";      
    this.sql += " SELECT     idProcessus, nomProcessus FROM   Processus WHERE     (Type = 'Processus') AND ProcessusMacroSt =";

    this.sql_fin = "  UNION SELECT  -1, 'zzCreer')";
    this.sql_fin+= " as mytable";
    this.sql_fin+= " order by nomProcessus";        
  }
  
  public  void getListeProcessusBySIWithCreation(){
    
    this.sql= "select * from (";      
    this.sql += " SELECT     idProcessus, nomProcessus FROM   Processus WHERE     (Type = 'Processus') AND ProcessusSI =";

    this.sql_fin = "  UNION SELECT  -1, 'zzCreer')";
    this.sql_fin+= " as mytable";
    this.sql_fin+= " order by nomProcessus";        
  }  
  
  public  void getListePhasesByMProcessusWithCreation(){
 
    this.sql= "select * from (";
    this.sql+= "SELECT     idPhaseNP, nomPhaseNP, Ordre";
    this.sql+= " FROM         Phase";
    this.sql+= " WHERE     PhaseProcessus =";

    this.sql_fin = "  UNION SELECT  -1, 'zzCreer', 999999999 as Ordre)";
    this.sql_fin+= " as mytable";    
    this.sql_fin+= " ORDER BY Ordre  ";      
  }   

  public  void getListeActiviteByPhaseWithCreation(){
 
    this.sql= "select * from (";      
    this.sql+= " SELECT     Activite.idActivite, St.nomSt + '-' + CAST(Activite.Ligne AS varchar(10)) + '-' + Activite.nomActivite AS activite, numActeur"; 
    this.sql+= " FROM         Activite INNER JOIN"; 
    this.sql+= "                       St ON Activite.ActiviteActeur = St.idSt"; 
    this.sql+= " WHERE     ActivitePhase =";   
    this.sql_fin = "  UNION SELECT  -1, 'zzCreer' as activite, 999999999 as numActeur)";
    this.sql_fin+= " as mytable";    
    this.sql_fin+= " ORDER BY numActeur, activite  ";          
  }     
  
  public  void getListeActiviteByPhaseWithCreation2(){
 
    this.sql= "select * from (";      
    this.sql+= "SELECT     idActivite, nomActivite, Ligne";
    this.sql+= " FROM         Activite";
    this.sql+= " WHERE     ActivitePhase =";   
    this.sql_fin = "  UNION SELECT  -1, 'zzCreer', -1 as Ligne)";
    this.sql_fin+= " as mytable";    
    this.sql_fin+= " ORDER BY Ligne  ";          
  }    
// --------------------------------------------------------------------------------------------------------//

// ------------------------------------------ Gestion des @Anomalies ----------------------------------------------//
  public  void getListeAnoByKey(String searchKey){
    req_n1 = "SELECT Anomalie.idAnomalie, Anomalie.objetAnomalie, creationAnomalie FROM  Anomalie WHERE (objetAnomalie LIKE '%"+searchKey+"%') AND ((typeEtatAnomalie = 1) OR    (typeEtatAnomalie = 2)) ORDER BY objetAnomalie";


    this.sql=req_n1;

}

  public  void getListeAnoByCriticite(){
    req_n3 = "SELECT Anomalie.idAnomalie, Anomalie.objetAnomalie FROM  Anomalie INNER JOIN  Criticite ON Anomalie.IdcriticiteAnomalie = Criticite.idCriticite WHERE  (Anomalie.IdcriticiteAnomalie =";
    req_n3_fin = ") ";;

    this.sql=req_n3;
    this.sql_fin=req_n3_fin;
  }

  public  void getListeAnoByCriticiteByLogin(){
    req_n3 = "SELECT Anomalie.idAnomalie, Anomalie.objetAnomalie FROM  Anomalie INNER JOIN  Criticite ON Anomalie.IdcriticiteAnomalie = Criticite.idCriticite WHERE Anomalie.createurAnomalie = '"+theCollaborateur.Login+"'AND (Anomalie.IdcriticiteAnomalie =";
    req_n3_fin = ") ";

    this.sql=req_n3;
    this.sql_fin=req_n3_fin;
  }
  
  public  void getListeAnoByState(){
    this.sql= "SELECT     Anomalie.idAnomalie, LEFT(Criticite.nomCriticite, 2) + '-' + Anomalie.objetAnomalie AS nom";
    this.sql+= " FROM         Anomalie INNER JOIN";
    this.sql+= "                       Criticite ON Anomalie.IdcriticiteAnomalie = Criticite.idCriticite INNER JOIN";
    this.sql+= "                       TypeEtat ON Criticite.EtatCriticite = TypeEtat.idTypeEtat";
    this.sql+= " WHERE     TypeEtat.idTypeEtat = ";
    this.sql_fin=" ORDER BY Anomalie.IdcriticiteAnomalie";
  }  

// --------------------------------------------------------------------------------------------------------//

// ------------------------------------------ Gestion des @Interfaces ----------------------------------------------//
  public  void getListeInterfacesBySt(){
    req_n2 = "SELECT     idInterface, sensInterface + ' ' + nomSt AS nomSt";
    req_n2 += " FROM         tempInterface";
    req_n2 += " WHERE   idSt =";

  this.sql=req_n2;
  this.sql_fin=req_n2_fin;

  }
  
  public  void getListeInterfacesByIdSt(){
    req_n2 = "exec ST_ListeInterfacesByIdSt ";

  this.sql=req_n2;


  }
  
  public  void getListeInterfacesValideeBySt(){

    req_n2 = "SELECT     tempInterface.idInterface, tempInterface.sensInterface + ' ' + tempInterface.nomSt AS nomSt, Interface.dumpHtml";
    req_n2 += " FROM         tempInterface INNER JOIN";
    req_n2 += "                 Interface ON tempInterface.idInterface = Interface.idInterface";
    req_n2 += " WHERE     (Interface.dumpHtml NOT LIKE '') AND tempInterface.idSt =";

  this.sql=req_n2;
  this.sql_fin=req_n2_fin;

  }


// --------------------------------------------------------------------------------------------------------//


  public  void getListeRelations(){
    this.sql = "SELECT * from";
    this.sql += "(";
    
    this.sql += "SELECT id, nom, id as id2 from(";
    this.sql += " SELECT    RelationsOM.id, RelationsOM.sens + ' ' + ObjetMetier_1.nomObjetMetier as nom, RelationsOM.origineOM as id_S";
    this.sql += " FROM         RelationsOM INNER JOIN";
    this.sql += "                       ObjetMetier ON RelationsOM.origineOM = ObjetMetier.idObjetMetier INNER JOIN";
    this.sql += "                       ObjetMetier AS ObjetMetier_1 ON RelationsOM.destinationOM = ObjetMetier_1.idObjetMetier";
    this.sql += " union";
    this.sql += " SELECT     RelationsOM.id, RelationsOM.sens + ' ' + ObjetMetier.nomObjetMetier as nom, RelationsOM.destinationOM as id_S";
    this.sql += " FROM         RelationsOM INNER JOIN";
    this.sql += "                       ObjetMetier ON RelationsOM.origineOM = ObjetMetier.idObjetMetier INNER JOIN";
    this.sql += "                       ObjetMetier AS ObjetMetier_1 ON RelationsOM.destinationOM = ObjetMetier_1.idObjetMetier";
    this.sql += ")  as myTable";
    this.sql += " WHERE   id_S =";
    
    this.sql_fin += " union";
    this.sql_fin += " select -1, 'zzCreer', 99999999 as id2";
    this.sql_fin += " ) as newTable order by id2";

  }
  
  public  void getListeRegions(){
    req_n1 = "SELECT     idRegion, nomRegion, descRegion FROM   Region ORDER BY idRegion";

    this.sql=req_n1;

  }  




  public  void getListeCampagneTestByProjet(){
    this.sql = " SELECT     id, (CAST(DAY(date) AS varchar(20)) + '/' + CAST(MONTH(date) AS varchar(20)) + '/' + CAST(YEAR(date)  AS varchar(20))) + '-' + CAST(num AS varchar(20)) + ' ' + CAST(version AS varchar(MAX)) as myDate, id as ordre  FROM         Campagne WHERE  isNonReg <> 1 AND idRoadmap = ";
    this.sql_fin= "  ORDER BY ORDRE";

  }
  
  public  void getListeCampagneTestByProjetWithCreation(){
    this.sql = "   SELECT * FROM ( SELECT     id, (CAST(DAY(date) AS varchar(20)) + '/' + CAST(MONTH(date) AS varchar(20)) + '/' + CAST(YEAR(date)  AS varchar(20))) + '-' + CAST(num AS varchar(20)) + ' ' + CAST(version AS varchar(MAX)) as myDate, id as ordre  FROM         Campagne WHERE  isNonReg <> 1 AND idRoadmap = ";
    this.sql_fin= "      UNION SELECT  -1, 'zzCreer', 99999999) as myTable ORDER BY ORDRE";

  }  



  public  void getListeCampagneTestByProjet2(){
    req_n3 = "SELECT     id, (CAST(DAY(date) AS varchar(20)) + '/' + CAST(MONTH(date) AS varchar(20)) + '/' + CAST(YEAR(date)  AS varchar(20))) + '-' + CAST(num AS varchar(20)) + ' ' + CAST(version AS varchar(MAX)) as myDate, ''  FROM         Campagne WHERE  isNonReg <> 1 AND idRoadmap = ";
    req_n3_fin = "  UNION SELECT  -1, 'zzCreer',''";

    this.sql=req_n3;
    this.sql_fin=req_n3_fin;

  }

  public  void getListeCampagneRelectureByProjet(){
    if ((theCollaborateur.MOA.equals("yes")) || (theCollaborateur.MOE.equals("yes")))
    {
      req_n3 = "SELECT     id, (CAST(DAY(date) AS varchar(20)) + '/' + CAST(MONTH(date) AS varchar(20)) + '/' + CAST(YEAR(date)  AS varchar(20)) + '-' + Login)  as myDate, ''  FROM         RelectureEB WHERE   idRoadmap = "; req_n3_fin = "  UNION SELECT  99999, '-- Cr&eacute;er',''";
    }
    else
    {
      req_n3 = "SELECT     id, (CAST(DAY(date) AS varchar(20)) + '/' + CAST(MONTH(date) AS varchar(20)) + '/' + CAST(YEAR(date)  AS varchar(20)) + '-' + Login)  as myDate, ''  FROM         RelectureEB WHERE   (Login = '"+theCollaborateur.Login+"') AND idRoadmap = ";
    }

    this.sql=req_n3;
    this.sql_fin=req_n3_fin;

  }

  public  void getListeCategorieTestByProjet(){
    req_n3 = "select id, nom, ordre  FROM  categorieTest WHERE   idRoadmap = ";
    req_n3_fin = "  order by ordre";
    this.sql=req_n3;
    this.sql_fin=req_n3_fin;

  }
  
  public  void getListeCategorieTestByProjetWithCreation(){
    req_n3 = "SELECT    * from ( select id, nom, ordre  FROM  categorieTest WHERE   idRoadmap = ";
    req_n3_fin = "   UNION SELECT  -1, 'zzCreer',9999999) as xx order by ordre";
    this.sql=req_n3;
    this.sql_fin=req_n3_fin;

  }  
  
  public  void getListeTestByCategorie(){
    this.sql = "SELECT     id, nom";
    this.sql += " FROM         Tests";
    this.sql += " WHERE   idCategorie =";
  }  

 public  void getListeTestByCategorieWithCreation(){ 
    
    this.sql  = "SELECT    * from (SELECT     id, nom, idCategorie, Ordre FROM         Tests  WHERE   idCategorie = ";
    this.sql_fin  = " UNION SELECT  -1, 'zzCreer', -1 as idCategorie, 9999999 as Ordre) as myTable ";   
    this.sql_fin+=" order by ordre";
  } 


  public  void getListeEquipeTypeStructure(){
    req_n1 = "SELECT DISTINCT Equipe.id, Equipe.nom";
    req_n1+=" FROM         Equipe INNER JOIN";
    req_n1+="                       EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    req_n1+="                       Membre ON EquipeMembre.idMembre = Membre.idMembre ";
    req_n1+=" WHERE     (EquipeMembre.type = 'resp') AND (Membre.LoginMembre ='"+this.theCollaborateur.Login+"')";
    this.sql=req_n1;
  }
  
  public  void getListeEquipeTypeStructure2(){
    req_n1 = "SELECT DISTINCT Equipe.id, Equipe.nom";
    req_n1+=" FROM         Equipe INNER JOIN";
    req_n1+="                       EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    req_n1+="                       Membre ON EquipeMembre.idMembre = Membre.idMembre INNER JOIN";
    req_n1+="                       typeGraphes ON Equipe.id = typeGraphes.idEquipe";
    req_n1+=" WHERE     (EquipeMembre.type = 'resp') AND (Membre.LoginMembre ='"+this.theCollaborateur.Login+"')";
    this.sql=req_n1;
  }  

  public  void getListeEquipeTypeSt(){
    req_n1= "SELECT     Equipe.id, Equipe.nom, VersionSt.serviceMoeVersionSt, VersionSt.serviceMoaVersionSt";
    req_n1+=" FROM         Equipe INNER JOIN";
    req_n1+="                 St ON Equipe.nom = St.nomSt INNER JOIN";
    req_n1+="                 VersionSt ON St.idSt = VersionSt.stVersionSt";
    req_n1+=" WHERE     (VersionSt.serviceMoeVersionSt = "+theCollaborateur.service+") OR";
    req_n1+="                 (VersionSt.serviceMoaVersionSt = "+theCollaborateur.service+")";
    req_n1+=" ORDER BY Equipe.nom";

    this.sql=req_n1;
  }

  public  void getListeEquipePlusCreation(){
    req_n1= "select * from (";
    req_n1+= " SELECT  id, nom  FROM    Equipe union select -1, 'zzCreer' as nom )";
    req_n1+= " as mytable order by nom asc";

    this.sql=req_n1;
  }

  public  void getListeMembresEquipePlusCreation(){
    this.sql= "select * from (";
    this.sql+= "SELECT     Membre.idMembre, Membre.nomMembre + ', ' + Membre.prenomMembre AS nom";
    this.sql+= " FROM         Equipe INNER JOIN";
    this.sql+= "                       EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    this.sql+= "                       Membre ON EquipeMembre.idMembre = Membre.idMembre";
    this.sql+= " WHERE     Equipe.id =";
    
    this.sql_fin=" union";
    this.sql_fin+=" select -1, 'zzCreer' as nom";
    this.sql_fin+=" )";
    this.sql_fin+=" as myTable";
    this.sql_fin+=" ORDER BY nom asc";    


  }  


  public  void getListeCouches(){
    //req_n1 = "SELECT     id, nom, [desc] FROM   Couche ORDER BY num DESC";
    req_n1 = "SELECT     ordre AS id, valeur as nom FROM         Config WHERE     (nom LIKE '%couche%') ORDER BY ordre";
    this.sql=req_n1;
  }
  
  public  void getListeCouchesByCouches(){
    //req_n1 = "SELECT     id, nom, [desc] FROM   Couche ORDER BY num DESC";
    this.sql= "select * from (";      
    this.sql += "SELECT   id , (Sens + ' ' + '('+[Type Interface]+') ' + ST_2) as nom";
    this.sql+="  FROM         AutomateInterface";
    this.sql+="  where isEquipement_1 *1 + isComposant_1 * 2  + isSt_1 * 3 + isAppli_1 * 4 + isActeur_1 * 5 =";
    this.sql_fin = "  UNION SELECT  9999999 as id, 'zzCreer' as nom)";
    this.sql_fin+= " as mytable";    
    this.sql_fin += "      ORDER BY id";
  }  

  public  void getListeDevisByServiceMoe(){
    req_n1= "SELECT     ListeProjets.idRoadmap, ListeProjets.nomProjet + ' (' + typeJalon.nom + ')', typeJalon.isEngagement";
    req_n1+= " FROM         typeJalon INNER JOIN";
    req_n1+= "                       ListeProjets ON typeJalon.id = ListeProjets.idJalon";
    req_n1+= " WHERE     (ListeProjets.LF_Month = - 1) AND (ListeProjets.LF_Year = - 1) AND (ListeProjets.isRecurrent = 0) AND (ListeProjets.isMeeting <> 1) AND (serviceMoeVersionSt = "+theService.id+")  ";
    req_n1+= "                        AND (ListeProjets.etatFicheVersionSt = 3) AND (ListeProjets.etatVersionSt = 3) AND (ListeProjets.idRoadmap NOT IN";
    req_n1+= "                           (SELECT     idRoadmap";
    req_n1+= "                             FROM          Devis WHERE Devis.typeJalon = ListeProjets.idJalon)) AND (typeJalon.isEngagement = 1)";
    req_n1+= " ORDER BY ListeProjets.nomProjet";
    this.sql=req_n1;
  }


  public  void getListeTypeLogiciel(){
    req_n1 = "SELECT distinct idLogiciel , nomLogiciel  FROM Logiciel ORDER BY nomLogiciel";
    this.sql=req_n1;
  }
  
  public  void getListeTypeLogicielPlusCreation(){
    
    this.sql= "select * from (";
    this.sql+= " SELECT distinct idLogiciel , nomLogiciel  FROM Logiciel";
    this.sql +=" union SELECT -1, 'zzCreer' as nomLogiciel)";
    this.sql+= " as mytable";
    this.sql+= " order by nomLogiciel";

  }  

  public  void getListeLogicielByType(){
    req_n2 = "SELECT idMiddleware, nomMiddleware FROM  Middleware, Logiciel WHERE idLogiciel=LogicielMiddleWare AND LogicielMiddleWare=";
    req_n2_fin = "ORDER BY nomMiddleware asc";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;
  }

  public  void getListeLogicielByTypePlusCreation(){
    req_n2= "select * from (";
    req_n2+= " SELECT idMiddleware, nomMiddleware FROM  Middleware, Logiciel WHERE idLogiciel=LogicielMiddleWare AND LogicielMiddleWare=";
    req_n2_fin =" union SELECT -1, 'zzCreer')";
    req_n2_fin+= " as mytable";
    req_n2_fin+= " order by nomMiddleware";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;
  }
  
  public  void getListeVersionLogicielPlusCreation(){
    this.sql= "select * from (";      
    this.sql+= "SELECT     VersionMW.idVersionMW, VersionMW.nomVersionMW";
    this.sql+= " FROM         Middleware INNER JOIN";
    this.sql+= "                      VersionMW ON Middleware.idMiddleware = VersionMW.mwVersionMW";
    this.sql+= " WHERE     Middleware.idMiddleware =";
    this.sql_fin =" union SELECT -1, 'zzCreer' as nomVersionMW)";
    this.sql_fin+= " as mytable";    
    this.sql_fin+=" ORDER BY nomVersionMW";
  }  


  public  void getListeVersionByLogiciel(){
    req_n3 = "(SELECT idVersionMW, nomVersionMW FROM VersionMW, Middleware WHERE  mwVersionMW=idMiddleware AND mwVersionMW=";
    req_n3_fin = ") UNION SELECT -1,'*'";

    this.sql=req_n3;
    this.sql_fin=req_n3_fin;
  }


  public  void getListeTypeInterface(){
    req_n1 = "SELECT     idImplementation, nomImplementation FROM  Implementation ORDER BY nomImplementation asc";
    this.sql=req_n1;
  }


  public  void getListeSi(){
    req_n1 = "SELECT idSI, nomSI FROM SI ORDER BY nomSI ASC";
    this.sql=req_n1;
  }
  
  public  void getListeInitialisation(){
    req_n1 = "SELECT     ordre, nom   FROM  Initialisation ORDER BY ordre";
    this.sql=req_n1;
  }  

  public  void getListeSiPlusCreation(){
    req_n1 = "select * from (";
    req_n1+=" SELECT idSI, nomSI FROM SI  ";
    req_n1+=" union";
    req_n1+=" select -1, 'zzCreer'";
    req_n1+=" )";
    req_n1+=" as myTable";
    req_n1+=" ORDER BY nomSI ASC";

    this.sql=req_n1;
  }
  
  public  void getListeImplementations(){
    req_n1 = "select * from (";
    req_n1+=" SELECT     idImplementation, nomImplementation FROM         Implementation  ";
    req_n1+=" union";
    req_n1+=" select -1, 'zzCreer' as nomImplementation";
    req_n1+=" )";
    req_n1+=" as myTable";
    req_n1+=" ORDER BY nomImplementation ASC";

    this.sql=req_n1;
  }  
  
  public  void getListeTypesInterfaces(String typeInterface){
    req_n1 = "select * from (";
    req_n1+=" SELECT     id, description FROM         typeInterfaces  ";
    req_n1+=" where type = '"+typeInterface+"'";
    req_n1+=" union";
    req_n1+=" select -1, 'zzCreer' as description";
    req_n1+=" )";
    req_n1+=" as myTable";
    
    req_n1+=" ORDER BY description ASC";

    this.sql=req_n1;
  }   
  
  
  public  void getListeTypeAppliPlusCreation(){
    req_n1 = "select * from (";
    req_n1+=" SELECT     idTypeAppli, nomTypeAppli  FROM   TypeAppli  ";
    req_n1+=" union";
    req_n1+=" select '-1', 'zzCreer'";
    req_n1+=" )";
    req_n1+=" as myTable";
    req_n1+=" ORDER BY nomTypeAppli ASC";

    this.sql=req_n1;
  }
  
  public  void getListeTypeAppli(){
    req_n1=" SELECT     idTypeAppli, nomTypeAppli  FROM   TypeAppli  ";
    req_n1+=" ORDER BY nomTypeAppli ASC";

    this.sql=req_n1;
  }  
  

  public  void getListeMacroStBySi(){
    req_n2 = "SELECT idMacrost, aliasMacrost, nomMacrost FROM MacroSt WHERE siMacrost=";
    req_n2_fin = "ORDER BY idMacrost asc";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;
  }
  
  public  void getListeMacroSt(){
    this.sql = "SELECT idMacrost, aliasMacrost, nomMacrost FROM MacroSt";
    this.sql+= " ORDER BY aliasMacrost asc";

  }  

  public  void getListeMacroStBySiPlusCreer(){
    req_n2 = "select * from (";
    req_n2 +=" SELECT idMacrost, aliasMacrost, nomMacrost FROM MacroSt WHERE siMacrost=";
    req_n2_fin+=" union";
    req_n2_fin+=" select -1, 'zzCreer', 'ZZZZZZZ' as nomMacrost";
    req_n2_fin+=" )";
    req_n2_fin+=" as myTable";
    req_n2_fin+=" ORDER BY nomMacrost asc";
    
    this.sql=req_n2;
    this.sql_fin=req_n2_fin;
  }  
  
  public  void getListeTypeSiBySiPlusCreer(){
    req_n2 = "select * from (";
    req_n2 +=" SELECT     idTypeSi, nomTypeSi, nomTypeSi AS alias FROM         TypeSi WHERE     siTypesi =";
    req_n2_fin+=" union";
    req_n2_fin+=" select -1, 'zzCreer', 'ZZZZZ' as nomTypeSi";
    req_n2_fin+=" )";
    req_n2_fin+=" as myTable";
    req_n2_fin+=" ORDER BY nomTypeSi asc";
    
    this.sql=req_n2;
    this.sql_fin=req_n2_fin;
  }    

  public  void getListeCouleurByTypeAppliPlusCreer(){
    req_n2 = "select * from (";
    req_n2 +=" SELECT DISTINCT AppliIcone.id, AppliIcone.couleur, AppliIcone.couleur AS alias FROM         AppliIcone INNER JOIN  TypeAppli ON AppliIcone.formeTypeAppli = TypeAppli.formeTypeAppli WHERE   TypeAppli.idTypeAppli =";
    req_n2_fin+=" union";
    req_n2_fin+=" select -1, 'zzCreer', ''";
    req_n2_fin+=" )";
    req_n2_fin+=" as myTable";
    req_n2_fin+=" ORDER BY couleur asc";
 
    

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;
  }  
  

  public  void getListeStateBriefs(){
    req_n1="SELECT     id, nom FROM typeEtatBrief ORDER BY ordre";
    this.sql=req_n1;
  }
  public  void getListeStateBriefsWithCreation(){
 
    req_n1= "select * from (";    
    req_n1 += "SELECT     id, nom, ordre FROM typeEtatBrief";
    req_n1 += " union select -1, 'Creer', 99999999 as ordre)";
    req_n1+= " as mytable order by ordre asc";        
    this.sql=req_n1;
  }
 
  
  
  public  void getListeStateAnoAvecCreation(){
    
    req_n1= "select * from (";
    req_n1+= " SELECT idTypeEtat, nomTypeEtat, Priorite FROM TypeEtat  WHERE (ANO = 'ANO') union select -1, 'Creer', 999999999 as Priorite)";
    req_n1+= " as mytable order by Priorite asc";    
    this.sql=req_n1;    
  }
  
  public  void getListeStateAno(){
    req_n1 = "SELECT idTypeEtat, nomTypeEtat FROM TypeEtat WHERE (ANO = 'ANO') ORDER BY Priorite";
    this.sql=req_n1;
  }  

  public  void getListeStateOm(){
    req_n1 = "SELECT idTypeEtat, nom2TypeEtat FROM TypeEtat ORDER BY idTypeEtat";
    this.sql=req_n1;
  }

  public  void getListeStateDevis(){
    req_n1="SELECT     id, nom FROM typeEtatDevis ORDER BY ordre";
    this.sql=req_n1;
  }

  public  void getListeBriefsByState(){
    req_n2= "SELECT idRoadmap, CAST ( idRoadmap AS VARCHAR)+'-'+version,dateCreation FROM  Briefs WHERE  idEtat = ";
    req_n2_fin ="ORDER BY idRoadmap";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }

  public  void getListeDevisByState(){
    req_n2= "SELECT     Devis.idRoadmap, CAST(Devis.idRoadmap AS varchar(50))+ '-' +St.nomSt + '-' + Roadmap.version AS nomProjet";
    req_n2+=" FROM         Devis INNER JOIN";
    req_n2+="                   Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req_n2+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req_n2+="                   St ON VersionSt.stVersionSt = St.idSt";
    req_n2+=" WHERE     Devis.idEtat =";
    req_n2_fin ="ORDER BY Devis.idRoadmap";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }

  public  void getListeCriticiteByStateAno(){
    req_n2 = "SELECT DISTINCT Criticite.idCriticite, TypeCriticite.nomTypeCriticite, TypeCriticite.Priorite FROM TypeEtat INNER JOIN  Criticite ON TypeEtat.idTypeEtat = Criticite.EtatCriticite INNER JOIN TypeCriticite ON Criticite.CriticiteTypeCriticite = TypeCriticite.idTypeCriticite WHERE (Criticite.EtatCriticite = ";
    req_n2_fin = ") ORDER BY TypeCriticite.Priorite asc";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }

  public  void getListeKPIByEquipe(){
    req_n2 = "SELECT     id, nom FROM    typeGraphes WHERE idEquipe =";
    req_n2_fin = " ORDER BY ordre";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }

  public  void getListeBriefsByStateByRole(){
    if (theCollaborateur.GOUVERNANCE.equals("yes")) // GAP voit tous les briefs
    {
      req_n2= "SELECT idRoadmap, CAST ( idRoadmap AS VARCHAR)+'-'+version,dateCreation FROM  Briefs WHERE  idEtat = ";
      req_n2_fin ="ORDER BY idRoadmap";
    }
    else if ((theCollaborateur.GOUVERNANCE.equals("no"))&& (theCollaborateur.isBrief == 1)) // CO-DPS ne voit que les briefs qui lui sont affectes
    {
      req_n2= "SELECT idRoadmap, CAST ( idRoadmap AS VARCHAR)+'-'+version,dateCreation FROM  Briefs  ";
      req_n2+= " WHERE (  (idEtat <> 2) AND (idServiceAffecte = "+theCollaborateur.service + ")";
      req_n2+= " OR LoginCreateur in ";
      req_n2+= " (";
      req_n2+= " SELECT     Membre_1.LoginMembre";
      req_n2+= " FROM         Service INNER JOIN";
      req_n2+= "                 Membre ON Service.idService = Membre.serviceMembre INNER JOIN";
      req_n2+= "                 Membre AS Membre_1 ON Service.idService = Membre_1.serviceMembre";
      req_n2+= " WHERE     (Membre.LoginMembre = '"+theCollaborateur.Login+"')";
      req_n2+= " ) OR (idEtat = 2))";
      req_n2+= " AND  idEtat =";
      req_n2_fin ="ORDER BY idRoadmap";


    }
    else      // hors DPS voit tous les briefs de son service
    {
      req_n2= "SELECT idRoadmap, CAST ( idRoadmap AS VARCHAR)+'-'+version,dateCreation FROM  Briefs  ";
      req_n2+= " WHERE LoginCreateur in ";
      req_n2+= " (";
      req_n2+= " SELECT     Membre_1.LoginMembre";
      req_n2+= " FROM         Service INNER JOIN";
      req_n2+= "                 Membre ON Service.idService = Membre.serviceMembre INNER JOIN";
      req_n2+= "                 Membre AS Membre_1 ON Service.idService = Membre_1.serviceMembre";
      req_n2+= " WHERE     (Membre.LoginMembre = '"+theCollaborateur.Login+"')";
      req_n2+= " )";
      req_n2+= " AND  idEtat =";
      req_n2_fin ="ORDER BY idRoadmap";
    }


    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }

  public  void getListeStByStateDevis(){
    this.sql = "SELECT DISTINCT 10000 * Devis.idEtat + ListeST.idVersionSt AS id, ListeST.nomSt";
    this.sql += " FROM         ListeST INNER JOIN";
    this.sql += "                       Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt INNER JOIN";
    this.sql += "                       Devis ON Roadmap.idRoadmap = Devis.idRoadmap";
    this.sql += " WHERE     Devis.idEtat = ";
    this.sql_fin = " ORDER BY ListeST.nomSt";
  }  
  
  public  void getListeStByStateDevis2(){
    this.sql = "SELECT DISTINCT ListeST.idVersionSt, ListeST.nomSt";
    this.sql += " FROM         ListeST INNER JOIN";
    this.sql += "                       Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt INNER JOIN";
    this.sql += "                       Devis ON Roadmap.idRoadmap = Devis.idRoadmap";
    this.sql += " WHERE     Devis.idEtat = ";
    this.sql_fin = " ORDER BY ListeST.nomSt";
  }    
  
  public  void getListeDevisBySt(){
    this.sql = "SELECT     Devis.idRoadmap, CAST(Devis.idRoadmap AS VARCHAR(50)) + '-' + Roadmap.version AS nomDevis";
    this.sql += " FROM         Devis INNER JOIN";
    this.sql += "                       Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    this.sql += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt";
    this.sql += " WHERE     10000 * Devis.idEtat + Roadmap.idVersionSt  = ";    
    this.sql_fin =  " ORDER BY Devis.idRoadmap";
  }   
  
  public  void getListeDevisByStByRole(){
    if (theCollaborateur.GOUVERNANCE.equals("yes"))
    {
    this.sql = "SELECT     Devis.id, CAST(Devis.idRoadmap AS VARCHAR(50)) + '-' + Roadmap.version + ' (' + typeJalon.nom + ')' AS nomDevis";
    this.sql += " FROM         Devis INNER JOIN";
    this.sql += "                       Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    this.sql += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN typeJalon ON Devis.typeJalon = typeJalon.id";
    this.sql += " WHERE     10000 * Devis.idEtat + Roadmap.idVersionSt  = ";    
    this.sql_fin =  " ORDER BY Devis.idRoadmap";
    }

    else if (theCollaborateur.MOE.equals("yes") && theCollaborateur.MOA.equals("yes"))
    {
    this.sql = "SELECT     Devis.id, CAST(Devis.idRoadmap AS VARCHAR(50)) + '-' + Roadmap.version + '-' + Roadmap.version + ' (' + typeJalon.nom + ')' AS nomDevis";
    this.sql += " FROM         Devis INNER JOIN";
    this.sql += "                       Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    this.sql += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN typeJalon ON Devis.typeJalon = typeJalon.id";
    this.sql+=" WHERE ((VersionSt.serviceMoeVersionSt = "+theCollaborateur.service+") OR (VersionSt.serviceMoaVersionSt = "+theCollaborateur.service+"))";    
    this.sql += " AND     10000 * Devis.idEtat + Roadmap.idVersionSt  = ";
    this.sql_fin =  " ORDER BY Devis.idRoadmap";
    }
    else if (theCollaborateur.MOE.equals("yes"))
    {
    this.sql = "SELECT     Devis.id, CAST(Devis.idRoadmap AS VARCHAR(50)) + '-' + Roadmap.version + '-' + Roadmap.version + ' (' + typeJalon.nom + ')' AS nomDevis";
    this.sql += " FROM         Devis INNER JOIN";
    this.sql += "                       Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    this.sql += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN typeJalon ON Devis.typeJalon = typeJalon.id";
    this.sql+=" WHERE ((VersionSt.serviceMoeVersionSt = "+theCollaborateur.service+") )";    
    this.sql += " AND     10000 * Devis.idEtat + Roadmap.idVersionSt  = ";    
    this.sql_fin =  " ORDER BY Devis.idRoadmap";
    }
    else if (theCollaborateur.MOA.equals("yes"))
    {
    this.sql = "SELECT     Devis.id, CAST(Devis.idRoadmap AS VARCHAR(50)) + '-' + Roadmap.version + '-' + Roadmap.version + ' (' + typeJalon.nom + ')' AS nomDevis";
    this.sql += " FROM         Devis INNER JOIN";
    this.sql += "                       Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    this.sql += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN typeJalon ON Devis.typeJalon = typeJalon.id";
    this.sql+=" WHERE ((VersionSt.serviceMoaVersionSt = "+theCollaborateur.service+"))";    
    this.sql += " AND     10000 * Devis.idEtat + Roadmap.idVersionSt  = ";   
    this.sql_fin =  " ORDER BY Devis.idRoadmap";
    }
  }   
  
  public  void getListeDevisByStateByRole(){
    if (theCollaborateur.GOUVERNANCE.equals("yes"))
    {
      req_n2= "SELECT     Devis.idRoadmap, CAST(Devis.idRoadmap AS varchar(50))+ '-' +St.nomSt + '-' + Roadmap.version AS nomProjet";
      req_n2+=" FROM         Devis INNER JOIN";
      req_n2+="                   Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
      req_n2+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
      req_n2+="                   St ON VersionSt.stVersionSt = St.idSt";
      req_n2+=" WHERE     Devis.idEtat =";  req_n2_fin ="ORDER BY Devis.idRoadmap";
    }

    else if (theCollaborateur.MOE.equals("yes") && theCollaborateur.MOA.equals("yes"))
    {
      req_n2= "SELECT     Devis.idRoadmap, CAST(Devis.idRoadmap AS varchar(50))+ '-' +St.nomSt + '-' + Roadmap.version AS nomProjet";
      req_n2+=" FROM         Devis INNER JOIN";
      req_n2+="                   Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
      req_n2+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
      req_n2+="                   St ON VersionSt.stVersionSt = St.idSt";
      req_n2+=" WHERE ((VersionSt.serviceMoeVersionSt = "+theCollaborateur.service+") OR (VersionSt.serviceMoaVersionSt = "+theCollaborateur.service+"))";
      req_n2+=" AND     Devis.idEtat =";  req_n2_fin ="ORDER BY Devis.idRoadmap";
    }
    else if (theCollaborateur.MOE.equals("yes"))
    {
      req_n2= "SELECT     Devis.idRoadmap, CAST(Devis.idRoadmap AS varchar(50))+ '-' +St.nomSt + '-' + Roadmap.version AS nomProjet";
      req_n2+=" FROM         Devis INNER JOIN";
      req_n2+="                   Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
      req_n2+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
      req_n2+="                   St ON VersionSt.stVersionSt = St.idSt";
      req_n2+=" WHERE (VersionSt.serviceMoeVersionSt = "+theCollaborateur.service+")";
      req_n2+=" AND     Devis.idEtat =";  req_n2_fin ="ORDER BY Devis.idRoadmap";
    }
    else if (theCollaborateur.MOA.equals("yes"))
    {
      req_n2= "SELECT     Devis.idRoadmap, CAST(Devis.idRoadmap AS varchar(50))+ '-' +St.nomSt + '-' + Roadmap.version AS nomProjet";
      req_n2+=" FROM         Devis INNER JOIN";
      req_n2+="                   Roadmap ON Devis.idRoadmap = Roadmap.idRoadmap INNER JOIN";
      req_n2+="                   VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
      req_n2+="                   St ON VersionSt.stVersionSt = St.idSt";
      req_n2+=" WHERE (VersionSt.serviceMoaVersionSt = "+theCollaborateur.service+")";
      req_n2+=" AND     Devis.idEtat =";  req_n2_fin ="ORDER BY Devis.idRoadmap";
    }


    this.sql=req_n2;
    this.sql_fin=req_n2_fin;

  }

  public  void getListeStateTaches(){
    req_n1="SELECT     id, nom FROM TypeEtatAction order by ordre";
    this.sql=req_n1;
  }

  public  void getListePfBySi(){
    req_n2 = "SELECT PointFonction.idPointFonction, PointFonction.TypePointFonction, PointFonction.descPointFonction FROM   PointFonction CROSS JOIN    SI WHERE  SI.idSI = ";
    req_n2_fin = "ORDER BY PointFonction.ordre";

    this.sql=req_n2;
    this.sql_fin=req_n2_fin;
  }


  public  void getListeTachesByProjet(){
    //req_n3 = "SELECT     id, nom FROM  actionSuivi WHERE     idRoadmap =";
    //req_n3_fin =" ORDER BY priorite";
  
    this.sql= "select * from (";      
    this.sql += "SELECT     id, Code ";  
    this.sql += " FROM   actionSuivi";  
    this.sql += " WHERE     idRoadmap =";

    sql_fin = "  UNION SELECT  -1, 'zzCreer')";
    sql_fin+= " as mytable";
    sql_fin+= " order by Code";          
      

  }
  
  public  void getListeTachesByProjetByState(){
    req_n3 = "SELECT     id, Code FROM         actionSuivi WHERE  (actionSuivi.acteur <> '')AND idRoadmap+ 30000*idEtat =";
    req_n3_fin =" ORDER BY priorite";

    this.sql=req_n3;
    this.sql_fin=req_n3_fin;
  }

  public  void getListeTachesServiceRetard(String Login){
    this.sql=  "SELECT     tempListeMembres.noteCompetence, actionSuivi.Code";
    this.sql+="                       FROM         tempListeMembres INNER JOIN";    
    this.sql+="                       actionSuivi ON tempListeMembres.noteCompetence = actionSuivi.id";
    this.sql+=" WHERE   Login= '"+Login+"'";
    this.sql+=" AND tempListeMembres.idMembre =";

  }
  
  public  void getListeTachesCollaborateurRetard(){
    this.sql=  "SELECT     tempListeMembres.noteCompetence, actionSuivi.Code";
    this.sql+="                       FROM         tempListeMembres INNER JOIN";    
    this.sql+="                       actionSuivi ON tempListeMembres.noteCompetence = actionSuivi.id";
    this.sql+=" order by Code";

  }
    
  
  public  void getListeMembresTachesServiceRetard(){
    req_n1=  "SELECT     DISTINCT idMembre, nomMembre, prenomMembre, LoginMembre FROM   tempListeMembres WHERE     (Login = '"+theCollaborateur.Login+"')  ORDER BY nomMembre";

    this.sql=req_n1;

  }  

  public  void getListeTachesByProjetByStateByLogin(){
    req_n3 = "SELECT     id, Code FROM         actionSuivi WHERE (actionSuivi.acteur LIKE '%"+theCollaborateur.Login+"%') AND  idRoadmap+ 30000*idEtat =";
    req_n3_fin =" ORDER BY priorite";

    this.sql=req_n3;
    this.sql_fin=req_n3_fin;
  }
  
  public  void getListeTachesByStateByLogin(){
    req_n3 = " SELECT     actionSuivi.id, St.nomSt + '-' + Roadmap.version + '-' + actionSuivi.Code AS nom";
    req_n3 += " FROM         actionSuivi INNER JOIN";
    req_n3 += "  Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req_n3 += " VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req_n3 += " St ON VersionSt.stVersionSt = St.idSt      ";
    req_n3 += " WHERE (actionSuivi.acteur LIKE '%"+theCollaborateur.Login+"%') AND idEtat =";
    req_n3_fin =" ORDER BY priorite, nom";

    this.sql=req_n3;
    this.sql_fin=req_n3_fin;
  }  
  
  public  void getListeDocBySt(){
    this.sql = "SELECT     idDoc, nom + '-' + CAST(commentaire AS varchar(50)) AS nom";
    //this.sql = "SELECT     idDoc, nom  AS nom";
    this.sql += " FROM         Documentation";
    this.sql += " WHERE     idDocVersionSt =";

    this.sql_fin=" ORDER BY nom";
  }
  
  public  void getListeDocByStWithCreation(){
    this.sql= "select * from (";      
    this.sql += "SELECT     Documentation.idDoc, TypeDocumentation.nomType  + '-' + CAST(Documentation.commentaire AS varchar(50)) AS nom";  
    this.sql += " FROM         Documentation INNER JOIN";  
    this.sql += "                      TypeDocumentation ON Documentation.idDocType = TypeDocumentation.idTypeDoc";  
    this.sql += " WHERE     idDocVersionSt =";

    sql_fin = "  UNION SELECT  -1, 'zzCreer')";
    sql_fin+= " as mytable";
    sql_fin+= " order by nom";    

  }    

  public  void getListeEbByProjetNonRecurrent(String Type){

    req_n3 = "SELECT    * from ( SELECT  id, chapitre +' '+ CAST(description AS varchar(max)) as description, (100000 * niv1 + 1000 * niv2 + 10 * niv3 + niv4) AS ordre FROM   BesoinsUtilisateur WHERE   idRoadmap = ";

    if (Type.equals("AddCreation"))
    {
      req_n3_fin = "   UNION SELECT  -1, 'zzCreer',99999999) as xx order by ordre";
    }
    else
   {
     req_n3_fin = "   ) as xx order by ordre";
   }


    this.sql=req_n3;
    this.sql_fin=req_n3_fin;
  }

  public static void main(String[] args) {
    Requete requete = new Requete(null, null, "2013");
  }
}

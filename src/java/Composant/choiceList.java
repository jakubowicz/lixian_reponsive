package Composant; 

import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import java.util.Vector;
import Organisation.Collaborateur;
import ST.SI;

/**
 * <p>Titre : Choice list</p>
 *
 * <p>Description : Cette Classe permet d'encapsuler le code d'un Choice List</p>
 *
 * <p>Copyright : Copyright (c) 2004</p>
 *
 * <p>Soci�t� : </p>
 *
 * @author Joel Jakubowicz
 * @version 1.0
 */
public class choiceList extends Html{
  Vector ListeOptions = new Vector(10);
  Vector ListeInput = new Vector(10);
  String selected = "";
  public String index = "-1";
  String Sep = "";
  int pos1 = 0;
  int pos2 = 0;
  int pos3=0;

  public String req="";

  public choiceList(String nom, String attributs, String index) {
    this.nom = nom;
    this.balise = "select";
    this.attributs = " " + attributs;
    this.index = index;

  }

  public choiceList(int pos1, int pos2,int pos3,String Sep, String index) {
    this.nom = nom;
    this.balise = "select";
    this.attributs = " " + attributs;
    this.pos1 = pos1;
    this.pos2 = pos2;
    this.pos3 = pos3;
    this.Sep = Sep;
    this.index = index;

  }
  public  void buildListFieldById(String nomBase,Connexion myCnx, Statement st, int id,  String type){

    req="SELECT     Field.id, Field.nom";
   req+=" FROM         template INNER JOIN";
   req+="                       datasource ON template.id = datasource.idTemplate INNER JOIN";
   req+="                       Field ON datasource.id = Field.idDatasource INNER JOIN";
   req+="                       datasource AS datasource_1 ON template.id = datasource_1.idTemplate INNER JOIN";
   req+="                       Field AS Field_1 ON datasource_1.id = Field_1.idDatasource";
   req+=" WHERE     (datasource.sens = 'in') AND (Field.type = '"+type+"') AND Field_1.id = "+id;
   req+=" ORDER BY Field.nom";

    this.build( nomBase, myCnx,  st,  req);
  }
  
  public  void buildListeMembreByST(String nomBase,Connexion myCnx, Statement st, String nom){

    req="SELECT * FROM ";
    req+=" (";

    req+= "SELECT    DISTINCT Membre.idMembre, Membre.nomMembre + ', ' + Membre.prenomMembre AS nom";
    req+=" FROM         Equipe INNER JOIN";
    req+="                   EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    req+="                   Membre ON EquipeMembre.idMembre = Membre.idMembre";
    req+=" WHERE     (Equipe.nom = '"+nom+"') AND (Membre.isProjet = 1)";

    req+=" UNION ";

    req+=" SELECT     DISTINCT  Membre.idMembre, Membre.nomMembre + ', ' + Membre.prenomMembre AS nom";
    req+="     FROM         ListeST INNER JOIN";
    req+="                   Membre ON ListeST.respMoaVersionSt = Membre.idMembre";
    req+="     WHERE     (ListeST.nomSt = '"+nom+"')";

    req+=" ) ";
    req+=" AS maTable ORDER BY nom";

    this.build( nomBase, myCnx,  st,  req);
  }

    public  void buildListeMembreEquipe(String nomBase,Connexion myCnx, Statement st){

    req="SELECT     Membre.idMembre, Membre.nomMembre + ', ' + Membre.prenomMembre + ', ' + Service.nomService AS nom";
    req+=" FROM         Membre INNER JOIN";
    req+="                       Service ON Membre.serviceMembre = Service.idService";
    req+=" WHERE     (Membre.isProjet = 1) OR";
    req+="                       (Membre.isTest = 1)";
    req+=" ORDER BY Membre.nomMembre";
    this.build( nomBase, myCnx,  st,  req);
  }
    
    public  void buildListeOngletsPere(String nomBase,Connexion myCnx, Statement st){

    req = "SELECT     id, nom";
    req += " FROM         Onglet";
    req += " WHERE     (isManaged = 1) AND (idPere = -1)";
    req += " ORDER BY Ordre";
    this.build( nomBase, myCnx,  st,  req);
  } 
    
    public  void buildListeOngletsPere(String nomBase,Connexion myCnx, Statement st, int isManaged){

    req = "SELECT     id, nom";
    req += " FROM         Onglet";
    req += " WHERE     (isManaged = "+isManaged+") AND (idPere = -1)";
    if (isManaged == 1)
        req += " ORDER BY Ordre";
    else
        req += " ORDER BY nom";
    this.build( nomBase, myCnx,  st,  req);
  }     
    
    public  void buildListeOm(String nomBase,Connexion myCnx, Statement st){

    req = "SELECT     idObjetMetier, nomObjetMetier FROM   ObjetMetier ORDER BY nomObjetMetier";

    this.build( nomBase, myCnx,  st,  req);
  }    
    
    public  void buildListeOm(String nomBase,Connexion myCnx, Statement st, int Type){

    req = "SELECT     idObjetMetier, nomObjetMetier FROM   ObjetMetier";
    req += " WHERE     typeOM = " + Type;
    req += "  ORDER BY nomObjetMetier";

    this.build( nomBase, myCnx,  st,  req);
  }      
    
  public  void buildListeProfils(String nomBase,Connexion myCnx, Statement st, int id){

    req = "SELECT     id, nom FROM  ProfilsProjet WHERE  idMembre = "+ id + " ORDER BY nom";

    this.build( nomBase, myCnx,  st,  req);
  }    
  
  public  void buildListeStyles(String nomBase,Connexion myCnx, Statement st){

    req = "SELECT     id, nom FROM  typeStyles ORDER BY nom";

    this.build( nomBase, myCnx,  st,  req);
  }  
    
  public  void buildListeMembreByIdST(String nomBase,Connexion myCnx, Statement st, int idVersionSt){

    req="SELECT * FROM ";
    req+="     (";
    req+=" SELECT     Membre.idMembre, Membre.nomMembre + ', ' + Membre.prenomMembre AS nom";
    req+=" FROM         VersionSt INNER JOIN";
    req+="                   Service ON VersionSt.serviceMoeVersionSt = Service.idService INNER JOIN";
    req+="                   Membre ON Service.idService = Membre.serviceMembre";
    req+=" WHERE     (VersionSt.idVersionSt = "+idVersionSt+")";

    req+=" union";

    req+=" SELECT     Membre.idMembre, Membre.nomMembre + ', ' + Membre.prenomMembre AS nom";
    req+=" FROM         VersionSt INNER JOIN";
    req+="                   Service ON VersionSt.serviceMoaVersionSt = Service.idService INNER JOIN";
    req+="                   Membre ON Service.idService = Membre.serviceMembre";
    req+=" WHERE     (VersionSt.idVersionSt = "+idVersionSt+")";

    req+=" union";

    req+=" SELECT     Membre.idMembre, Membre.nomMembre + ', ' + Membre.prenomMembre AS nom";
    req+="     FROM         ListeST INNER JOIN";
    req+="                   Membre ON ListeST.respMoeVersionSt = Membre.idMembre";
    req+=" WHERE     (ListeST.idVersionSt = "+idVersionSt+")";

    req+=" union";

    req+=" SELECT     Membre.idMembre, Membre.nomMembre + ', ' + Membre.prenomMembre AS nom";
    req+="     FROM         ListeST INNER JOIN";
    req+="                   Membre ON ListeST.serviceMoaVersionSt = Membre.idMembre";
    req+=" WHERE     (ListeST.idVersionSt = "+idVersionSt+")";

    req+=" union";

    req+=" SELECT DISTINCT Membre.idMembre, Membre.nomMembre + ', ' + Membre.prenomMembre AS nom";
    req+=" FROM         Membre INNER JOIN";
    req+="                       EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
    req+="                       Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
    req+="                       St ON Equipe.nom = St.nomSt INNER JOIN";
    req+="                       VersionSt ON St.idSt = VersionSt.stVersionSt";
    req+="     WHERE     (VersionSt.idVersionSt = "+idVersionSt+")    ";


    req+=" )";
    req+=" AS maTable ORDER BY nom";


    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeMembreEquipeByIdST(String nomBase,Connexion myCnx, Statement st, String idVersionSt){

    req="SELECT * FROM ";
    req+="     (";
    req+=" SELECT     Membre.idMembre, Membre.nomMembre";
    req+="     FROM         ListeST INNER JOIN";
    req+="                   Membre ON ListeST.respMoeVersionSt = Membre.idMembre";
    req+=" WHERE     (ListeST.idVersionSt = "+idVersionSt+")";


    req+=" union";

    req+=" SELECT     Membre.idMembre, Membre.nomMembre";
    req+="     FROM         ListeST INNER JOIN";
    req+="                   Membre ON ListeST.serviceMoaVersionSt = Membre.idMembre";
    req+=" WHERE     (ListeST.idVersionSt = "+idVersionSt+")";

    req+=" union";

    req+=" SELECT DISTINCT Membre.idMembre, Membre.nomMembre";
    req+=" FROM         Membre INNER JOIN";
    req+="                       EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
    req+="                       Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
    req+="                       St ON Equipe.nom = St.nomSt INNER JOIN";
    req+="                       VersionSt ON St.idSt = VersionSt.stVersionSt";
    req+="     WHERE     (VersionSt.idVersionSt = "+idVersionSt+")    ";

    req+=" )";
    req+=" AS maTable ORDER BY nomMembre";


    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeMembreByService(String nomBase,Connexion myCnx, Statement st, int idService){


    req= "SELECT DISTINCT Membre.idMembre, Membre.nomMembre + ', ' + Membre.prenomMembre AS nom";
    req+= "    FROM         Membre INNER JOIN";
    req+= "                   Service ON Membre.serviceMembre = Service.idService";
    req+= " WHERE     (Service.idService = "+idService+")ORDER BY nom";


    this.build( nomBase, myCnx,  st,  req);
  }
  
  public  void buildListeRoadmapProfilByService(String nomBase,Connexion myCnx, Statement st, int idService){


    req= "SELECT      id,nom";
    req+=" FROM         RoadmapProfil";
    req+=" WHERE     (idService = "+idService+") AND (type = 'delais')";

    this.build( nomBase, myCnx,  st,  req);
  } 
  
  public  void buildListeRoadmapProfil(String nomBase,Connexion myCnx, Statement st){


    req= "SELECT     id, nom, ordre FROM         typeProfilRoadmap ORDER BY ordre";
    this.build( nomBase, myCnx,  st,  req);
  }    


  public  void buildListeSTByIdService(String nomBase,Connexion myCnx, Statement st, int id, int isMoe){

    if (isMoe != 1)
    {
      req = "SELECT DISTINCT ListeST.idVersionSt, ListeST.nomSt";
      req += " FROM         ListeST INNER JOIN";
      req +=
          "                   Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";
      req += " WHERE     (ListeST.serviceMoaVersionSt = " + id + ") AND (ListeST.isMeeting <> 1) AND (ListeST.isRecurrent = 0) AND (Roadmap.dateT0 > CONVERT(DATETIME, ";
      req += "                   '2000-01-01 00:00:00', 102)) AND (Roadmap.dateEB > CONVERT(DATETIME, '2000-01-01 00:00:00', 102)) AND ";
      req += "                   (Roadmap.dateMep > CONVERT(DATETIME, '2000-01-01 00:00:00', 102)) AND (Roadmap.dateTest > CONVERT(DATETIME, '2000-01-01 00:00:00', 102))";
      req += " ORDER BY ListeST.nomSt";
    }
    else
    {
      req = "SELECT DISTINCT ListeST.idVersionSt, ListeST.nomSt";
      req += " FROM         ListeST INNER JOIN";
      req +=
          "                   Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";
      req += " WHERE     (ListeST.serviceMoeVersionSt = " + id + ") AND (ListeST.isMeeting <> 1) AND (ListeST.isRecurrent = 0) AND (Roadmap.dateT0 > CONVERT(DATETIME, ";
      req += "                   '2000-01-01 00:00:00', 102)) AND (Roadmap.dateEB > CONVERT(DATETIME, '2000-01-01 00:00:00', 102)) AND ";
      req += "                   (Roadmap.dateMep > CONVERT(DATETIME, '2000-01-01 00:00:00', 102)) AND (Roadmap.dateTest > CONVERT(DATETIME, '2000-01-01 00:00:00', 102))";
      req += " ORDER BY ListeST.nomSt";
    }
    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeST(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT DISTINCT idSt, nomSt";
    req+="     FROM         ListeST";
    req+=" WHERE     (isMeeting <> 1) AND (isRecurrent = 0)";
    req+=" ORDER BY nomSt";


    this.build( nomBase, myCnx,  st,  req);
  }
  
  public  void buildListeMyOwnST(String nomBase,Connexion myCnx, Statement st, String Login){

    req = "SELECT * FROM (";
    req+=" SELECT VersionSt.idVersionSt, St.nomSt,isRecurrent FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoaVersionSt = Membre.idMembre ";
    req+=" WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.creerProjet = 1) AND (Membre.LoginMembre = '"+Login+"')";

    req+=" UNION";

    req+=" SELECT VersionSt.idVersionSt, St.nomSt,isRecurrent FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
    req+=" WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.creerProjet = 1) AND (Membre.LoginMembre = '"+Login+"')";

    req+=" UNION ";

    req+=" SELECT ListeST.idVersionSt, ListeST.nomSt,isRecurrent FROM Membre INNER JOIN EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ListeST ON Equipe.nom = ListeST.nomSt ";
    req+=" WHERE (Membre.LoginMembre =  '"+Login+"')  AND  (Membre.isProjet = 1) AND (ListeST.creerProjet = 1)AND (ListeST.isActeur <> 1)";

    req+=" )";
    req+=" AS maTable ORDER BY isRecurrent desc,nomSt ";


    this.build( nomBase, myCnx,  st,  req);
  }
  
  public  void buildListeActeurs(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     idSt, nomSt";
    req+=" FROM         St";
    req+=" WHERE     (isActeur = 1)";
    req+=" ORDER BY nomSt";

    this.build( nomBase, myCnx,  st,  req);
  }  

  public  void buildListeSTAll(String nomBase,Connexion myCnx, Statement st){

    req= "EXEC ST_SelectListeRefSt";

    this.build( nomBase, myCnx,  st,  req);
  }
  
  public  void buildListeStOrAppli(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     idVersionSt, nomSt";
    req+=" FROM         ListeST";
    req+=" WHERE     (isST = 1) AND (isRecurrent = 0) OR";
    req+="                       (isAppli = 1) AND (isRecurrent = 0)";
    req+=" ORDER BY nomSt";

    this.build( nomBase, myCnx,  st,  req);
  }  

  public  void buildListTypeEtat(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT id, nom FROM TypeEtatAction order by ordre";

    this.build( nomBase, myCnx,  st,  req);
  }
  
  public  void buildListTypeDoc(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     idTypeDoc, nomType FROM         TypeDocumentation ORDER BY nomType";

    this.build( nomBase, myCnx,  st,  req);
  }  
  
  public  void buildListeItems(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     id, nom FROM         typeTemplate ORDER BY nom";

    this.build( nomBase, myCnx,  st,  req);
  }  
  
  public  void buildListeTemplates(String nomBase,Connexion myCnx, Statement st, String typeTempate){

    req= "SELECT     template.id, template.nom";
    req+= " FROM         typeTemplate INNER JOIN";
    req+= "                       template ON typeTemplate.id = template.idItem";
    req+= " WHERE     (typeTemplate.nom = '"+typeTempate+"')";
    req+= " ORDER BY typeTemplate.nom";

    this.build( nomBase, myCnx,  st,  req);
  }   
  
  public  void buildListeBriefs(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT DISTINCT idRoadmap as id,  '(' + CAST(Briefs.idRoadmap as varchar(50)) + '): ' + version FROM Briefs ORDER BY idRoadmap ";

    this.build( nomBase, myCnx,  st,  req);
  }  
  
  public  void buildListeDb(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     id, nom FROM   db WHERE nom <> '' ORDER BY ordre";

    this.build( nomBase, myCnx,  st,  req);
  }    
  
  public  void buildListeEnvironnement(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     id, nomClient FROM   db WHERE nom <> '' ORDER BY nomClient";

    this.build( nomBase, myCnx,  st,  req);
  }   

  public  void buildListeSTforSTI(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT DISTINCT ListeST.idVersionSt, ListeST.nomSt, ListeST.siVersionSt, SI.nomSI";
    req+="     FROM         ListeST INNER JOIN";
    req+="                   SI ON ListeST.siVersionSt = SI.idSI";
    req+=" WHERE     (ListeST.isMeeting <> 1) AND (ListeST.isRecurrent = 0) AND (SI.nomSI <> 'Fictif') AND (ListeST.isActeur <> 1) AND (ListeST.isEquipement <> 1)";
    req+=" ORDER BY ListeST.nomSt";


    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeSTforSTIAll(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT DISTINCT ListeST.idVersionSt, ListeST.nomSt, ListeST.siVersionSt, SI.nomSI";
    req+="     FROM         ListeST INNER JOIN";
    req+="                   SI ON ListeST.siVersionSt = SI.idSI";
    req+=" WHERE     (ListeST.isMeeting <> 1) AND (ListeST.isRecurrent = 0) AND (SI.nomSI <> 'Fictif') ";
    req+=" ORDER BY ListeST.nomSt";


    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeST_SI(String nomBase,Connexion myCnx, Statement st, int idSi){

  req= "SELECT DISTINCT idVersionSt, nomSt";
  req+="     FROM         ListeST";
  req+=" WHERE     ((isMeeting <> 1) AND (isRecurrent = 0) AND (siVersionSt = "+idSi+"))";
  req+=" OR (nomSt LIKE '---NEW_ST_%')";
  req+=" ORDER BY nomSt";


  this.build( nomBase, myCnx,  st,  req);
}
  
  public  void buildListeST_Chiffrage(String nomBase,Connexion myCnx, Statement st, int idSi){

  req= "SELECT DISTINCT idVersionSt, nomSt";
  req+="     FROM         ListeST";
  req+=" WHERE     ((isMeeting <> 1) AND (isRecurrent = 0) AND (siVersionSt = "+idSi+"))";
  req+=" AND (nomSt LIKE 'SIR_%')";
  req+=" ORDER BY nomSt";


  this.build( nomBase, myCnx,  st,  req);
}  

public  void buildListeSi(String nomBase,Connexion myCnx, Statement st){

req= "EXEC LISTESI_SelectSI";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeSiByTypeSt(String nomBase,Connexion myCnx, Statement st, int isST, int isAppli, int isEquipement, int isComposant, int isActeur){

  String ClauseWhere="";
  if (isEquipement ==1 )
  {
    if (ClauseWhere.equals(""))
      ClauseWhere += " WHERE ";
    else
      ClauseWhere += " OR ";

    ClauseWhere += " (isEquipement = 1) ";
  }

  if (isComposant==1)
  {
    if (ClauseWhere.equals(""))
      ClauseWhere += " WHERE ";
    else
      ClauseWhere += " OR ";
    ClauseWhere += " (isComposant = 1) ";
  }

  if (isST==1)
  {
    if (ClauseWhere.equals(""))
      ClauseWhere += " WHERE ";
    else
      ClauseWhere += " OR ";
    ClauseWhere += " (isSt = 1) ";
  }

  if (isAppli==1)
  {
    if (ClauseWhere.equals(""))
      ClauseWhere += " WHERE ";
    else
      ClauseWhere += " OR ";
    ClauseWhere += " (isAppli = 1) ";
  }

  if (isActeur==1)
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


this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeFamilleOm(String nomBase,Connexion myCnx, Statement st){

req= "SELECT      idFamilleOM, nomFamilleOM FROM  FamilleOM ORDER BY nomFamilleOM";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListePackageOm(String nomBase,Connexion myCnx, Statement st){

req= "SELECT  idPackage, nomPackage FROM  packageOM ORDER BY nomPackage";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListePhaseNp(String nomBase,Connexion myCnx, Statement st){

req= "EXEC LISTEPHASE_SelectPhaseNP";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeProcessus(String nomBase,Connexion myCnx, Statement st){

req= "SELECT idProcessus, nomProcessus FROM Processus ORDER BY nomProcessus";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeAciviteBtProcessus(String nomBase,Connexion myCnx, Statement st){

req= "SELECT idProcessus, nomProcessus FROM Processus ORDER BY nomProcessus";

this.build( nomBase, myCnx,  st,  req);
}


  public  void buildListeIdPo(String nomBase,Connexion myCnx, Statement st, String Annee, String Service){

    req= "SELECT DISTINCT idWebPo AS id, idWebPo AS nom";
    req+="     FROM         PlanOperationnelClient";
    req+=" WHERE     (Annee = "+Annee+") AND (Service = '"+Service+"')";
    req+=" ORDER BY id";

    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeIdPoByYeayByService(String nomBase,Connexion myCnx, Statement st, String Annee, String Service){

    req= "SELECT DISTINCT PlanOperationnelClient.idWebPo AS id, '(' + CAST(PlanOperationnelClient.idWebPo as varchar(50)) + ') ' + PlanOperationnelClient.nomProjet AS nom";
    req+="     FROM         PlanOperationnelClient INNER JOIN";
    req+="                   Service ON PlanOperationnelClient.Service = Service.nomServiceImputations";
    req+=" WHERE     (PlanOperationnelClient.Annee >= "+Annee+") AND (Service.idService = '"+Service+"')";
    req+=" ORDER BY id";

    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeEquipeByCollaborateur(String nomBase,Connexion myCnx, Statement st,int idCollaborateur){

    req= "SELECT     Equipe.id, Equipe.nom";
    req+="     FROM         Equipe INNER JOIN";
    req+="                   EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    req+="                   Membre ON EquipeMembre.idMembre = Membre.idMembre";
    req+=" WHERE     (EquipeMembre.type = 'resp') AND (Membre.idMembre = "+idCollaborateur+")";
    req+=" ORDER BY Equipe.nom";


    this.build( nomBase, myCnx,  st,  req);
  }
  
  public  void buildListeEquipe(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     id, nom FROM   Equipe order by nom asc";
    this.build( nomBase, myCnx,  st,  req);
  }  

  public  void buildListeImplementationByTypeInterface(String nomBase,Connexion myCnx, Statement st, String TypeInterface){

    req= "EXEC IMPLEMENTATION_SelectImplementation '"+TypeInterface+"'";

    this.build( nomBase, myCnx,  st,  req);
  }


  public  void buildListeFrequenceByTypeInterface(String nomBase,Connexion myCnx, Statement st, String TypeInterface){

    req= "EXEC FREQUENCE_SelectFrequence '"+TypeInterface+"'";

    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeDirections(String nomBase,Connexion myCnx, Statement st){
    req = "SELECT     idDirection, nomDirection, libelleDirection FROM Direction ORDER BY nomDirection";
    this.build( nomBase, myCnx,  st,  req);

  }

  public  void buildListeCollaborateurByEquipe(String nomBase,Connexion myCnx, Statement st, String Login, String Nom){

    req= "    SELECT distinct * FROM ";
    req+="  (";
    req+="  SELECT     UPPER(Membre_1.LoginMembre) as LoginMembre, UPPER(Membre_1.nomMembre) as nomMembre";
    req+="  FROM         Membre INNER JOIN";
    req+="                    Service ON Membre.serviceMembre = Service.idService INNER JOIN";
    req+="                    Membre AS Membre_1 ON Service.idService = Membre_1.serviceMembre";
    req+="  WHERE     (Membre.LoginMembre = '"+Login+"') AND (Membre.fonctionMembre = 'CDS')";

    req+="  union";

    req+="  SELECT DISTINCT UPPER(Membre.LoginMembre) as LoginMembre, UPPER(Membre.nomMembre) as nomMembre ";
    req+="  FROM         EquipeMembre INNER JOIN";
    req+="                     Membre ON EquipeMembre.idMembre = Membre.idMembre";
    req+="  WHERE     (EquipeMembre.type = 'Collaborateur') AND (EquipeMembre.idMembreEquipe IN";
    req+="                        (SELECT     Equipe.id";
    req+="                          FROM          EquipeMembre AS EquipeMembre_1 INNER JOIN";
    req+="                                                 Membre AS Membre_1 ON EquipeMembre_1.idMembre = Membre_1.idMembre INNER JOIN";
    req+="                                                 Equipe ON EquipeMembre_1.idMembreEquipe = Equipe.id";
    req+="                          WHERE      (Membre_1.LoginMembre = '"+Login+"') AND (EquipeMembre_1.type = 'resp')))";

    req+="  union";
    req+="  SELECT     UPPER(Membre.LoginMembre), UPPER(Membre.nomMembre) FROM         Membre WHERE    LoginMembre = '"+Login+"'";

    req+="  union";    
    req+="  SELECT     UPPER(Membre_1.LoginMembre) AS LoginMembre, UPPER(Membre_1.nomMembre) AS nomMembre";
    req+="  FROM         Membre INNER JOIN";
    req+="                        Service ON Membre.serviceMembre = Service.idService INNER JOIN";
    req+="                        Membre AS Membre_1 ON Service.idService = Membre_1.serviceMembre";
    req+="  WHERE     (Membre.LoginMembre = '"+Login+"')";

    req+="  )  ";
    req+="  AS maTable ORDER BY nomMembre  ";


    this.buildSansAucun( nomBase, myCnx,  st,  req);
  }

  public  void buildListeTypeJalon(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     id, nom, description, ordre FROM  typeJalon ORDER BY ordre";
    this.build( nomBase, myCnx,  st,  req);
  }
  
  public  void buildListeTypeSi(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT idTypeSi, nomTypeSi FROM   TypeSi ORDER BY nomTypeSi";
    this.build( nomBase, myCnx,  st,  req);
  }  
  
  public  void buildListeTypeSiByIdSi(String nomBase,Connexion myCnx, Statement st, int idSi){

    req= "SELECT     idTypeSi, nomTypeSi, siTypesi";
    req+=" FROM         TypeSi";
    req+=" WHERE     siTypesi = " + idSi;
    req+=" ORDER BY nomTypeSi";
    this.build( nomBase, myCnx,  st,  req);
  }   

  public  void buildListetypeBesoin(String nomBase,Connexion myCnx, Statement st){

  req= "SELECT     nom, nom FROM  typeBesoin ORDER BY ordre";
  this.build( nomBase, myCnx,  st,  req);
}

public  void buildListetypeIntegration(String nomBase,Connexion myCnx, Statement st){

req= "SELECT     id, nom, description, ordre FROM  typeIntegration ORDER BY ordre";
this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeConstructeurs(String nomBase,Connexion myCnx, Statement st){

req= "SELECT idConstructeur, nomConstructeur FROM Constructeur order by nomConstructeur";
this.build( nomBase, myCnx,  st,  req);
}

public  void buildListetypeAppli(String nomBase,Connexion myCnx, Statement st){

req= "LISTETYPEAPPLI_SelectTypeAppli";
this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeSousTypeStByTypeSt(String nomBase,Connexion myCnx, Statement st, int isST, int isAppli, int isEquipement, int isComposant, int isActeur){

  String ClauseWhere="";
  if (isEquipement ==1 )
  {
    if (ClauseWhere.equals(""))
      ClauseWhere += " WHERE ";
    else
      ClauseWhere += " OR ";

    ClauseWhere += " (isEquipement = 1) ";
  }

  if (isComposant==1)
  {
    if (ClauseWhere.equals(""))
      ClauseWhere += " WHERE ";
    else
      ClauseWhere += " OR ";
    ClauseWhere += " (isComposant = 1) ";
  }

  if (isST==1)
  {
    if (ClauseWhere.equals(""))
      ClauseWhere += " WHERE ";
    else
      ClauseWhere += " OR ";
    ClauseWhere += " (isSt = 1) ";
  }

  if (isAppli==1)
  {
    if (ClauseWhere.equals(""))
      ClauseWhere += " WHERE ";
    else
      ClauseWhere += " OR ";
    ClauseWhere += " (isAppli = 1) ";
  }

  if (isActeur==1)
  {
    if (ClauseWhere.equals(""))
      ClauseWhere += " WHERE ";
    else
      ClauseWhere += " OR ";
    ClauseWhere += " (isActeur = 1) ";
  }
  
  if (isActeur==0 && isAppli==0 && isST==0 && isComposant==0 && isEquipement==0)
  {
      ClauseWhere += "  WHERE (isActeur = 0) ";
      ClauseWhere += "  AND (isAppli = 0) ";
      ClauseWhere += "  AND (isST = 0) ";
      ClauseWhere += "  AND (isComposant = 0) ";
      ClauseWhere += "  AND (isEquipement = 0) ";
  }  
  
  

  String req="SELECT     idTypeAppli, nomTypeAppli";
  req+=  "  FROM         TypeAppli";
  req+=ClauseWhere;
  req+=" ORDER BY nomTypeAppli";

this.build( nomBase, myCnx,  st,  req);
}


public  void buildListetypeRelecture(String nomBase,Connexion myCnx, Statement st){

req= "SELECT  id, etat FROM  typeRelectureExigence ORDER BY ordre";
this.build( nomBase, myCnx,  st,  req);
}


  public  void buildListeSTLies(String nomBase,Connexion myCnx, Statement st){

    req= " select distinct * from (";
    req+="    SELECT     VersionSt.idVersionSt, St.nomSt AS Origine";
    req+="     FROM         VersionSt INNER JOIN";
    req+="                   Interface INNER JOIN";
    req+="                   St ON Interface.origineInterface = St.idSt ON VersionSt.stVersionSt = St.idSt";
    req+="     WHERE     (Interface.typeInterface = 'ST') AND (Interface.sensInterface = '--->')";

    req+="  union";

    req+="  SELECT     VersionSt_1.idVersionSt,St.nomSt AS Origine";
    req+="  FROM         Interface INNER JOIN";
    req+="                    St ON Interface.extremiteInterface = St.idSt INNER JOIN";
    req+="                    VersionSt ON Interface.origineInterface = VersionSt.idVersionSt INNER JOIN";
    req+="                    St AS St_1 ON VersionSt.stVersionSt = St_1.idSt INNER JOIN";
    req+="                    VersionSt AS VersionSt_1 ON St.idSt = VersionSt_1.stVersionSt";
    req+="  WHERE     (Interface.typeInterface = 'ST') AND (Interface.sensInterface = '<---')";
    req+="  )";
    req+=" as myTable order by Origine";




    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeSociete(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     Service.idService, Service.nomService FROM  Direction INNER JOIN   Service ON Direction.idDirection = Service.dirService WHERE     (Direction.nomDirection = 'Societe externe') order by Service.nomService";

  this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeService(String nomBase,Connexion myCnx, Statement st){

  req= "SELECT     idService, nomService, typeService";
  req+="     FROM         Service";
  req+=" ORDER BY nomService";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeSens(String nomBase,Connexion myCnx, Statement st){

  req= "SELECT     id, nom FROM   TypeSens ORDER BY ordre";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeServiceByType2(String nomBase,Connexion myCnx, Statement st, String Type){

  req= "EXEC [DIRECTION_SelectServiceOrderByDir] '"+Type+"'";

this.buildConcat( nomBase, myCnx,  st,  req);
}

public  void buildListeServiceByType(String nomBase,Connexion myCnx, Statement st, String Type){

  req= "SELECT     DISTINCT Direction.idDirection, Service.idService, Direction.nomDirection, Service.nomService";
  req+=" FROM         Service INNER JOIN";
  req+="                       Direction ON Service.dirService = Direction.idDirection";
  if (Type.equals("MOE"))
      req+=" WHERE     (Service.isMoe =1)";
  else if (Type.equals("MOA"))
      req+=" WHERE     (Service.isMoa =1)";
  else if (Type.equals("Exploitation"))
      req+=" WHERE     (Service.isExploitation =1)";  
  else if (Type.equals("Gouvernance"))
      req+=" WHERE     (Service.isGouvernance =1)";    
  
  req+=" ORDER BY Direction.nomDirection, Service.nomService";

this.buildConcat( nomBase, myCnx,  st,  req);
}

public  void buildListeServiceByDirection(String nomBase,Connexion myCnx, Statement st, int idDirection){

  req= "SELECT     idService, nomService";
  req+="     FROM         Service";
  req+=" WHERE    (dirService = "+idDirection+")";
  req+=" ORDER BY nomService";



this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeServiceByDirection2(String nomBase,Connexion myCnx, Statement st, String nomDirection){

  req= "SELECT     Service.idService, Service.nomService";
  req+="    FROM         Service INNER JOIN";
  req+="                     Direction ON Service.dirService = Direction.idDirection";
  req+=" WHERE     (Direction.nomDirection = '"+nomDirection+"') AND (NOT (Service.nomService LIKE 'DPS/%'))";
  req+=" ORDER BY Service.nomService";


this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeServiceDPSByDirection(String nomBase,Connexion myCnx, Statement st, int idDirection){

  req= "SELECT     idService, nomService";
  req+="     FROM         Service";
  req+=" WHERE     ( (nomService LIKE 'DPS/%')) AND (dirService = "+idDirection+")";
  req+=" ORDER BY nomService";


this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeServiceDPSByDirection2(String nomBase,Connexion myCnx, Statement st, String nomDirection){

  req= "SELECT     Service.idService, Service.nomService";
  req+="    FROM         Service INNER JOIN";
  req+="                     Direction ON Service.dirService = Direction.idDirection";
  req+=" WHERE     (Direction.nomDirection = '"+nomDirection+"') AND ((Service.nomService LIKE 'DPS/%'))";
  req+=" ORDER BY Service.nomService";


this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeClients(String nomBase,Connexion myCnx, Statement st){

  req= "SELECT     VersionSt.idVersionSt, St.nomSt";
  req+="     FROM         Service INNER JOIN";
  req+="                     St ON Service.nomService = St.nomSt INNER JOIN";
  req+="                     VersionSt ON St.idSt = VersionSt.stVersionSt";
  req+="   WHERE     (St.isActeur = 1) AND (St.nomSt <> '********Sorti********')";
  req+=" ORDER BY St.nomSt";


this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeDatasource(String nomBase,Connexion myCnx, Statement st, int id){

  req= "SELECT     datasource.id, template.nom + '::' + datasource.sens + '-' + datasource.nom AS nom";
  req+=" FROM         datasource INNER JOIN";
  req+="                       template ON datasource.idTemplate = template.id";
  req+=" AND datasource.id <> "+id;
  req+=" ORDER BY nom";


this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeContacts(String nomBase,Connexion myCnx, Statement st){

  req= "SELECT     id, entreprise FROM  Contacts";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeTypeModule(String nomBase,Connexion myCnx, Statement st){

  req= "SELECT     id, nom, description FROM   TypeModule ORDER BY ordre";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeEnvModule(String nomBase,Connexion myCnx, Statement st){

  req= "SELECT     id, nom FROM   typeEnvironnement ORDER BY ordre";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeServiceByNom(String nomBase,Connexion myCnx, Statement st, String nom){

  req= "SELECT     idService, nomService, typeService";
  req+="     FROM         Service";
  req+=" WHERE     (nomService LIKE '"+nom+"%')";
  req+=" ORDER BY nomService";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeServiceHavingCC(String nomBase,Connexion myCnx, Statement st){

  req= "SELECT     idService, nomService, typeService";
  req+=" FROM         Service";
  req+=" WHERE     (nomServiceImputations LIKE '% | %')";
  req+=" ORDER BY nomService";


this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeServiceByCC(String nomBase,Connexion myCnx, Statement st, String CentreCout){

  req= "SELECT     idService, nomService, typeService";
  req+=" FROM         Service";
  req+=" WHERE     (CentreCout = '"+CentreCout+"')";
  req+=" ORDER BY nomService";


this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeMaturite(String nomBase,Connexion myCnx, Statement st){

  req= "SELECT     id, nom FROM Maturite ORDER BY num";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeTypeMiddleware(String nomBase,Connexion myCnx, Statement st){

  req= "SELECT     idLogiciel, nomLogiciel FROM  Logiciel WHERE  (visible = 1)";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeMiddleware(String nomBase,Connexion myCnx, Statement st){

  req= "SELECT idMiddleware, nomMiddleware FROM Middleware   order by nomMiddleware";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeVersionMiddleware(String nomBase,Connexion myCnx, Statement st, int idMiddleware){

  req= "SELECT     idVersionMW, nomVersionMW FROM   VersionMW";
  if (idMiddleware != -1)
    req+=" WHERE     (mwVersionMW = "+idMiddleware+")";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeBesoinByNiv1(String nomBase,Connexion myCnx, Statement st, int idRoadmap){

  req= "SELECT     niv1, description";
  req+="     FROM         BesoinsUtilisateur";
  req+=" WHERE     (idRoadmap = "+idRoadmap+") AND (nbNiveau = 1)";
  req+=" ORDER BY niv1";


this.buildSansAucun( nomBase, myCnx,  st,  req);
}

public  void buildListeBesoinNiv2ByNiv1(String nomBase,Connexion myCnx, Statement st, int idRoadmap, int niv1){

  req= "SELECT     niv2, description";
  req+="     FROM         BesoinsUtilisateur";
  req+=" WHERE     (idRoadmap = "+idRoadmap+") AND (nbNiveau = 2) AND (niv1 = "+niv1+")";
  req+=" ORDER BY niv2";

this.buildSansAucun( nomBase, myCnx,  st,  req);
}

public  void buildListeBesoinNiv3ByNiv1Niv2(String nomBase,Connexion myCnx, Statement st, int idRoadmap, int niv1, int niv2){

  req= "SELECT     niv3, description";
  req+="     FROM         BesoinsUtilisateur";
  req+=" WHERE     (idRoadmap = "+idRoadmap+") AND (nbNiveau = 3) AND (niv1 = "+niv1+") AND (niv2 = "+niv2+")";
  req+=" ORDER BY niv3";

this.buildSansAucun( nomBase, myCnx,  st,  req);
}

public  void buildListeBesoinNiv4ByNiv1Niv2Niv3(String nomBase,Connexion myCnx, Statement st, int idRoadmap, int niv1, int niv2, int niv3){

  req= "SELECT     niv4, description";
  req+="     FROM         BesoinsUtilisateur";
  req+=" WHERE     (idRoadmap = "+idRoadmap+") AND (nbNiveau = 4) AND (niv1 = "+niv1+") AND (niv2 = "+niv2+") AND (niv3 = "+niv3+")";
  req+=" ORDER BY niv4";

this.buildSansAucun( nomBase, myCnx,  st,  req);
}

public  void buildListeCharge(String nomBase,Connexion myCnx, Statement st){

  req= "SELECT     id, nom FROM   typeCharge ORDER BY ordre";

this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeProjetMOE(String nomBase,Connexion myCnx, Statement st, int idService){

  req= "SELECT     idRoadmap, nomProjet, Etat, LF_Month, LF_Year, isRecurrent, isMeeting";
  req+="    FROM         ListeProjets";
  req+=" WHERE     (Etat = 'EB') AND (LF_Month = - 1) AND (LF_Year = - 1) AND (isRecurrent = 0) AND (isMeeting <> 1)";
  req+=" AND (serviceMoeVersionSt = "+idService+")";
  req+= "   and idRoadmap not in";
  req+= "  (";
  req+= "  SELECT     idRoadmap FROM         Devis";
  req+= "  )";

  req+=" ORDER BY nomProjet";

this.build( nomBase, myCnx,  st,  req);
}


  public  void buildListeSTBySI(String nomBase,Connexion myCnx, Statement st, int id){

    req= "SELECT DISTINCT ListeST.idVersionSt, ListeST.nomSt";
    req+="     FROM         ListeST INNER JOIN";
    req+="                   Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";
    req+=" WHERE     (ListeST.isMeeting <> 1) AND (ListeST.isRecurrent = 0) AND (Roadmap.dateT0 > CONVERT(DATETIME, '2000-01-01 00:00:00', 102)) AND ";
    req+="                   (Roadmap.dateEB > CONVERT(DATETIME, '2000-01-01 00:00:00', 102)) AND (Roadmap.dateMep > CONVERT(DATETIME, '2000-01-01 00:00:00', 102)) ";
    req+="                   AND (Roadmap.dateTest > CONVERT(DATETIME, '2000-01-01 00:00:00', 102))";
    req+=" ORDER BY ListeST.nomSt";


    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeReunionByIdService(String nomBase,Connexion myCnx, Statement st, int id){

    req= "SELECT DISTINCT ListeST.idVersionSt, ListeST.nomSt";
    req+= " FROM         ListeST ";
    req+= " WHERE     (ListeST.serviceMoaVersionSt = "+id+") AND (ListeST.isMeeting = 1)";
    req+= " ORDER BY ListeST.nomSt";
    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeMacroStBySi(String nomBase,Connexion myCnx, Statement st, int id){

    req= "EXEC MACROST_bySI "+id;

    this.build( nomBase, myCnx,  st,  req);
  }
  
  public  void buildListeMacroStMetierBySi(String nomBase,Connexion myCnx, Statement st, int id){

    req= "SELECT     idMacrost, nomMacrost";
    req+= " FROM         MacroSt";
    req+= " WHERE    ( siMacrost = "+id+") AND (offset_X <> 1)";

    this.build( nomBase, myCnx,  st,  req);
  }
  
  public  void buildListeMacroStTec(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     idMacrost, nomMacrost";
    req+= " FROM         MacroSt";
    req+= " WHERE    (offset_X = 1)";

    this.build( nomBase, myCnx,  st,  req);
  }  
  
  public  void buildListeMacroSt(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     MacroSt.idMacrost, SI.nomSI + '-' + MacroSt.nomMacrost AS nom";
    req+= " FROM         MacroSt INNER JOIN";
    req+= "                       SI ON MacroSt.siMacrost = SI.idSI";
    req+= " ORDER BY nom";

    this.build( nomBase, myCnx,  st,  req);
  }  

  public  void buildListeTypeEtatPO(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT      nom, nom, [desc]";
    req+= " FROM         typeEtatPO";
    req+= " ORDER BY ordre";

    this.buildSansAucun( nomBase, myCnx,  st,  req);
  }

  public  void buildListeCpMOA(String nomBase,Connexion myCnx, Statement st, int id){

    req= "SELECT DISTINCT Membre.idMembre, Membre.nomMembre + ',' + Membre.prenomMembre AS nom";
    req+= " FROM         Membre INNER JOIN";
    req+= "                   Service ON Membre.serviceMembre = Service.idService";
    req+= " WHERE     (Service.idService = "+id+")";
    req+= " ORDER BY nom";

    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeCpSI(String nomBase,Connexion myCnx, Statement st, int id){

    req= "SELECT DISTINCT Membre.idMembre, Membre.nomMembre + ',' + Membre.prenomMembre AS nom";
    req+= "     FROM         Membre INNER JOIN";
    req+= "                   Service ON Membre.serviceMembre = Service.idService";
    if (id > 0)  req+= " WHERE     (Service.siService = "+id+")";
    req+= " ORDER BY nom";


    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeRoadmapBySt(String nomBase,Connexion myCnx, Statement st, int id){

    req= "SELECT     Roadmap.idRoadmap AS idRoadmapDest, Roadmap.version";
    req+= "     FROM         VersionSt INNER JOIN";
    req+= "                   Roadmap AS Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt";
    req+= " WHERE     (VersionSt.idVersionSt = "+id+") AND (Roadmap.LF_Month = - 1) AND (Roadmap.LF_Year = - 1)";
    req+= " ORDER BY Roadmap.version";


    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeAnneePO(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT DISTINCT Annee, Annee";
    req+= "     FROM         PlanOperationnel";
    req+= " ORDER BY Annee";

    this.build( nomBase, myCnx,  st,  req);
  }
  
  public  void buildListeCouleurs(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT DISTINCT couleur AS idCouleur, couleur";
    req+= " FROM         AppliIcone";
    req+= " ORDER BY idCouleur";

    this.build( nomBase, myCnx,  st,  req);
  }
  
  public  void buildListeCouches(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     ordre AS id, valeur as nom FROM         Config WHERE     (nom LIKE '%couche%') ORDER BY ordre";

    this.build( nomBase, myCnx,  st,  req);
  }  
  

  public  void buildListeIdWebPo(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT DISTINCT idWebPo, idWebPo";
    req+=" FROM         PlanOperationnel";
    req+=" ORDER BY idWebPo";

    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeIdProfecee(String nomBase,Connexion myCnx, Statement st, String nomService){

    req= "SELECT DISTINCT LTRIM(idWebPo) + '(' + LTRIM(Annee)+ ')'  AS id, LTRIM(idWebPo) + '(' + LTRIM(Annee) + ')'  AS nom";
    req+="     FROM         PlanOperationnelClient";
    req+=" WHERE     (Service = '"+nomService+"')";
    req+=" ORDER BY id";

    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeIdProfecee2(String nomBase,Connexion myCnx, Statement st, String nomService){

  req= "SELECT DISTINCT idWebPo AS id, idWebPo AS nom";
  req+="     FROM         PlanOperationnelClient";
  req+=" WHERE     (Service = '"+nomService+"')";
  req+=" ORDER BY id";

  this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeLoginEmetteurByIdRoadmap(String nomBase,Connexion myCnx, Statement st, int idRoadmap){

  req= "SELECT DISTINCT  LoginEmetteur, Membre.nomMembre + ', ' + Membre.prenomMembre AS nom";
  req+="     FROM         actionSuivi INNER JOIN";
  req+="                     Membre ON actionSuivi.LoginEmetteur = Membre.LoginMembre";
  req+=" WHERE     (idRoadmap = "+idRoadmap+") AND (LoginEmetteur IS NOT NULL) AND (LoginEmetteur <> 'null') AND (LoginEmetteur <> '')";
  req+=" ORDER BY LoginEmetteur";

  this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeLoginEmetteur(String nomBase,Connexion myCnx, Statement st){

  req= "SELECT DISTINCT actionSuivi.LoginEmetteur, Membre.nomMembre + ', ' + Membre.prenomMembre AS nom";
  req+="     FROM         actionSuivi INNER JOIN";
  req+="                     Membre ON actionSuivi.LoginEmetteur = Membre.LoginMembre";
  req+=" WHERE     (actionSuivi.LoginEmetteur IS NOT NULL)";
  req+=" ORDER BY actionSuivi.LoginEmetteur";



  this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeLoginEmetteurByIdEquipe(String nomBase,Connexion myCnx, Statement st, int idEquipe){

  req= "SELECT DISTINCT Membre.LoginMembre, Membre.nomMembre + ', ' + Membre.prenomMembre AS nom";
  req+="     FROM         Membre INNER JOIN";
  req+="                     EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
  req+="                     Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
  req+="                     actionSuivi ON Membre.LoginMembre = actionSuivi.LoginEmetteur";
  req+=" WHERE     (EquipeMembre.type = 'resp') AND (Equipe.id = "+idEquipe+")";
  req+=" ORDER BY nom";




  this.build( nomBase, myCnx,  st,  req);
}


  public  void buildListeAnnee(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT DISTINCT Annee FROM   PlanOperationnelClient ORDER BY Annee";

    this.build( nomBase, myCnx,  st,  req);
  }
  
  public  void buildListeAnneeBudget(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT    Annee, nom FROM  Budgets ORDER BY Annee";

    this.build( nomBase, myCnx,  st,  req);
  }  

  public  void buildListeTypeInterfaces(String nomBase,Connexion myCnx, Statement st, String Type){

    req= "SELECT   nom, description, ordre FROM  typeInterfaces WHERE     (Type = '"+Type+"')ORDER BY description";

    this.build( nomBase, myCnx,  st,  req);
  }
  
  public  void buildListeTypeRelations(String nomBase,Connexion myCnx, Statement st, String Type){

    req= "SELECT   id, description, ordre FROM  typeInterfaces WHERE     (Type = '"+Type+"')ORDER BY description";

    this.build( nomBase, myCnx,  st,  req);
  }  
  
  public  void buildListeTypeInterfaces(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT   nom, description, ordre FROM  typeInterfaces ORDER BY description";

    this.build( nomBase, myCnx,  st,  req);
  }  

  public  void buildListeTypeChampTable(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT nom, nom FROM typeChampTable order by id";

    this.build( nomBase, myCnx,  st,  req);
  }

  public void buildListeTypeInterfacesByTypeSt(String nomBase,Connexion myCnx, Statement st, int isEquipement, int  isComposant, int isST, int isAppli, int isActeur){


    String ClauseST="";
    int num_Clause=0;

    if (isEquipement == 1)
    {
      if (num_Clause == 0)
        ClauseST += " WHERE (";
      else
      ClauseST += " OR (";

      ClauseST+= " (isEquipement_1 = 1) ";
      ClauseST += ") ";
      num_Clause++;
    }

    if (isComposant == 1)
    {
      if (num_Clause == 0)
        ClauseST += " WHERE (";
      else
      ClauseST += " OR (";

      ClauseST+= " (isComposant_1 = 1) ";
      ClauseST += ") ";
      num_Clause++;
    }

    if (isST == 1)
    {
      if (num_Clause == 0)
        ClauseST += " WHERE (";
      else
      ClauseST += " OR (";

      ClauseST+= " (isST_1 = 1) ";
      ClauseST += ") ";
      num_Clause++;
    }

    if (isAppli == 1)
    {
      if (num_Clause == 0)
        ClauseST += " WHERE (";
      else
      ClauseST += " OR (";

      ClauseST+= " (isAppli_1 = 1) ";
      ClauseST += ") ";
      num_Clause++;
    }

    if (isActeur == 1)
    {
      if (num_Clause == 0)
        ClauseST += " WHERE (";
      else
      ClauseST += " OR (";

      ClauseST+= " (isActeur_1 = 1) ";
      ClauseST += ") ";
      num_Clause++;
    }

    ResultSet rs;
    req = "SELECT   DISTINCT  AutomateInterface.[Type Interface], typeInterfaces.description";
    req+="    FROM         AutomateInterface INNER JOIN";
    req+="                   typeInterfaces ON AutomateInterface.[Type Interface] = typeInterfaces.nom";
    req+=ClauseST;
    req+=" ORDER BY [Type Interface]";

    this.build( nomBase, myCnx,  st,  req);
  }


  public  void buildListeIdBriefs(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     idRoadmap, idRoadmap AS Expr1 FROM Briefs ORDER BY idRoadmap";

    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeIdDevis(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     idRoadmap, idRoadmap AS Expr1 FROM Devis ORDER BY idRoadmap";

    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeEtatBrief(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     id, nom FROM  typeEtatBrief ORDER BY ordre";

    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeEtatSt(String nomBase,Connexion myCnx, Statement st){

    req= "EXEC LISTEETAT_SelectEtat";

    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeEtatTest(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT id, nom FROM   TypeEtatTest";

    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeEtatDevis(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     id, nom FROM  typeEtatDevis ORDER BY ordre";

    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeComplexite(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT     id, nom FROM  typeComplexite ORDER BY ordre";

    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListeTypeClient(String nomBase,Connexion myCnx, Statement st){

    req= "SELECT  id, nom FROM  typeClient";

    this.build( nomBase, myCnx,  st,  req);
  }


  public  void buildListeMotsClefs(String nomBase,Connexion myCnx, Statement st){

    req="SELECT DISTINCT nom, nom FROM  ListeMotsClefs ORDER BY nom";
    this.build( nomBase, myCnx,  st,  req);
  }

  public  void buildListePO(String nomBase,Connexion myCnx, Statement st){

  req="SELECT DISTINCT dateImport AS id, dateImport FROM    PlanOperationnel ORDER BY id";
  this.build( nomBase, myCnx,  st,  req);
}

public  void buildListeNotes(String nomBase,Connexion myCnx, Statement st){

req="SELECT     id, nom, ordre FROM noteCompetence ORDER BY ordre";
this.build( nomBase, myCnx,  st,  req);
}

  public void build(String nomBase,Connexion myCnx, Statement st, String req){
    int i=0;

    this.addOption("-1", "--- Aucun ---");
    ResultSet rs =null;
    rs = myCnx.ExecReq(st, nomBase, req);

    try { while(rs.next()) {
        this.addOption(rs.getString(1), rs.getString(2));
        i++;}
    } catch (SQLException s) {s.getMessage();}

  }

  public void buildSansAucun(String nomBase,Connexion myCnx, Statement st, String req){
    int i=0;

    ResultSet rs =null;
    rs = myCnx.ExecReq(st, nomBase, req);

    try { while(rs.next()) {
        this.addOption(rs.getString(1), rs.getString(2));
        i++;}
    } catch (SQLException s) {s.getMessage();}

  }

  public void build(String nomBase,Connexion myCnx, Statement st, Vector MyListe){

    this.addOption("-1", "--- Aucun ---");
    ResultSet rs =null;

    for (int i =0; i < MyListe.size(); i++)
    {
      idNom theidNom = (idNom)MyListe.elementAt(i);
      this.addOption(""+theidNom.id, theidNom.nom);
    }
  }

  public void buildConcat(String nomBase,Connexion myCnx, Statement st, String req){
    int i=0;

    this.addOption("-1", "--- Aucun ---");
    ResultSet rs =null;
    rs = myCnx.ExecReq(st, nomBase, req);

    try { while(rs.next()) {
        this.addOption(rs.getString(this.pos1), rs.getString(this.pos2) + this.Sep + rs.getString(this.pos3));
        i++;}
    } catch (SQLException s) {s.getMessage();}

  }

  public void addOption(String value,String text){
    Option theOption = new Option(value, text);
    this.ListeOptions.addElement(theOption);
    if (value.equals(this.index)) selected = "selected"; else selected = "";
    this.html +="<option value='"+value+ "' " +selected +">"+text+"</option>";
  }

  public void addOption2(String value,String text){
    Option theOption = new Option(value, text);
    this.ListeOptions.addElement(theOption);
    if (value.equals(this.index)) selected = "selected"; else selected = "";
    this.html +="<option value="+value+ " " +selected +">"+text+"</option>";
  }

  public static void main(String[] args) {
    Connexion myCnx = null;
    Statement st, st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    st2 = myCnx.Connect();

    choiceList thechoiceListePO1 = new choiceList("","","-1");
    thechoiceListePO1.buildListePO(Connexion.nomBase,myCnx, st);
   // myCnx.trace("-----------","html",""+thechoiceListePO1.html);

    myCnx.Unconnect(st);
  }
}

class Option extends Html{


  public Option(String value, String text) {
    this.value = value;
    this.text = text;

  }


}

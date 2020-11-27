package Organisation; 
import Composant.item;
import java.util.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import accesbase.Connexion;
import java.sql.SQLException;
import java.util.Vector;
import Projet.Charge;
import General.Utils;
import ST.ST;
import Projet.Roadmap;
import ST.Logiciel;
import Projet.Action;
import Projet.typeJalon;
import Favori.Favori;
import java.util.*;
import accesbase.ErrorSpecific;
import Projet.RoadmapProfil; 
import Projet.PhaseProjet;
import java.text.SimpleDateFormat;
/**
 * <p>Titre : Description d'un collaborateur</p>
 *
 * <p>Description : </p>
 *
 * <p>Copyright : Copyright (c) 2004</p>
 *
 * <p>Soci�t� :  </p>
 *
 * @author : Joel Jakubowicz
 * @version 1.0
 */
public class Collaborateur {
  public int id;
  public int idGenerique;
  public String nom = "";
  public String prenom= "";
  public String fonction= "";
  public int service;
  public int service_old;
  public int direction;
  public String nomDirection= "";
  
  public String mail = "";
  public String Login = "";
  public String Password = "";

  
  public int isInterne = 1;
  public int isManager = 0;
  public int isAdmin = 0;
  public int isProjet =0;
  public int isTest=0;
  public int isPo=0;
  public int isBrief=0;
   
  public String TypeAdmin="";

  public String Admin="no";
  public String Manager="no";
  public String MOE="no";
  public String MOA="no";
  public String GOUVERNANCE="no";



  public int AppletNotSupported;
  public String AO= "";
  public int Prix;
  public String dateEntree= "";
  public String dateSortie= "";
  public String intitulePoste = "";
  public int niveau;
  public String Mission = "";
  public int societe;
  public String nomSociete= "";
  public String nomService= "";
  public String nomServiceImputations= "";
  public RoadmapProfil profilCharge;

  public String chaine_a_ecrire= "";

  public Vector ListeEquipes = new Vector(10);
  public Vector ListeServices = new Vector(10);
  public Vector ListeProjets = new Vector(10);
  public Vector ListeCharges = new Vector(10);
  public Vector ListeST = new Vector(10);
  public Vector ListeActions = new Vector(10);
  public Vector ListeActionsNonImputables = new Vector(10);
  public Vector ListeRespMoa = new Vector(10);
  public Vector ListeFavoris = new Vector(10);
  public Vector ListeCompetences = new Vector(10);

  public String couleurEB="F9FF40";
  public String couleurDEV="4BFF14";
  public String couleurTEST="FF0303";
  public String couleurMES="D6E2D3";

  public Vector ListeProfilST = new Vector(10);
  public Vector ListeProfilsProjet = new Vector(10);
  public String profilST="";
  
  public int noteCharge=0;
  public int noteCompetence=0;


   public static Vector ListeCollaborateur = new Vector(10);
   public static Vector ListeProfil_static = new Vector(10);

  private String req="";

  String TypeCreation="";

  public float totalMembre = 0;
  public float ChargePrevue = 52 * 5;

  public int maxProjets=0;
  public String nomComplet="";

  public float totalReparti = 0;

  public String Telephone_M="";
  public String Telephone_B="";
  public String Fax="";

  public String nomCommercial;
  public String prenomCommercial;
  public String TelephoneCommercial;
  public String mailCommercial;

  public float etp = 1;
  
  public int idManager= -1;
  
  public int nbColleguesSpecification= 0;
  public int nbColleguesDeveloppement= 0;
  public int nbColleguesTest= 0;
  
  public float chargeMoyenneEb = 0;
  public float chargeMoyenneDev = 0;
  public float chargeMoyenneTest = 0;
  
  public String photo="images/Direction.png";
  
  private ResultSet rs=null;
  
  public String str_ListeCouleurPeriode = "";
  public Vector ListeCouleurPeriode = new Vector(10);  
  
  
  public Collaborateur(int id) {
    this.id = id;
    this.TypeCreation = "byId";
  }

  public Collaborateur(String Login) {
    this.Login = Login;
    this.TypeCreation = "byLogin";

  }
  
  public Collaborateur(String Login, String Password) {
    this.Login = Login;
    this.Password = Password;
    this.TypeCreation = "byLogin";

  }  

  public Collaborateur(int id, String prenom, String nom) {
    this.id = id;
    this.prenom = prenom;
    this.nom = nom;
    this.TypeCreation = "byId";
  }

  public ErrorSpecific bd_Enreg(String action, String typeForm, String nomBase,Connexion myCnx, Statement st, String transaction){

    String result="";
    ErrorSpecific myError = new ErrorSpecific();
    if (typeForm.equals("Creation")) { // Cr�ation d'un nouveau Collaborateur
      myError=this.bd_Insert( nomBase, myCnx,  st,  transaction);
    }
    else { //MAJ du ST
      if (action.equals("supp"))
          { //l'utilisateur a cliqu� sur le bouton supprimer

           myError=this.bd_Delete(nomBase,myCnx,st,transaction);

          }
            else
            {
              myError=this.bd_Update( nomBase, myCnx,  st,  transaction);
            }
          }
    return myError;
  }
  public ErrorSpecific bd_updateMigration(String nomBase,Connexion myCnx, Statement st, String transaction){

    ErrorSpecific myError = new ErrorSpecific();
    
    int typeAD = -1;
    int typeT0 = -1;
    int typeEB = -1;
    int typeTEST = -1;
    int typeMEP = -1;
    int typeVSR = -1;
   
    ResultSet rs;
    String req = "";

    req = "SELECT     id, nom FROM  typeJalon WHERE (isEssentiel = 1) ORDER BY ordre";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    int num=0;
    try {
      while (rs.next()) {
          if (num == 0)
            typeAD =  rs.getInt("id");          
          if (num == 1)
            typeT0 =  rs.getInt("id");
          else if (num == 2)
            typeEB =  rs.getInt("id"); 
          else if (num == 3)
            typeTEST =  rs.getInt("id"); 
          else if (num == 4)
            typeMEP =  rs.getInt("id"); 
          else if (num == 5)
            typeVSR =  rs.getInt("id");           
         num++;
      }}   catch (SQLException s) {s.getMessage();}      
    
    this.str_ListeCouleurPeriode = "";
    
    this.str_ListeCouleurPeriode += typeEB;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurEB;
    
    this.str_ListeCouleurPeriode += "@"; 
    
    this.str_ListeCouleurPeriode += typeTEST;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurDEV;  
    
    this.str_ListeCouleurPeriode += "@"; 
    
    this.str_ListeCouleurPeriode += typeMEP;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurTEST;
    
    this.str_ListeCouleurPeriode += "@"; 
    
    this.str_ListeCouleurPeriode += typeVSR;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurMES;
    
 
    req = "UPDATE    Membre  SET ";
    req +=" ListeCouleurPeriode ='"+ this.str_ListeCouleurPeriode + "'";    
    req += " WHERE idMembre ="+ this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }
  
  public void bd_updateMigration(String nomBase,Connexion myCnx, Statement st){
    
    int typeAD = -1;
    int typeT0 = -1;
    int typeEB = -1;
    int typeTEST = -1;
    int typeMEP = -1;
    int typeVSR = -1;
   
    ResultSet rs;
    String req = "";

    req = "SELECT     id, nom FROM  typeJalon WHERE (isEssentiel = 1) ORDER BY ordre";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    int num=0;
    try {
      while (rs.next()) {
          if (num == 0)
            typeAD =  rs.getInt("id");          
          if (num == 1)
            typeT0 =  rs.getInt("id");
          else if (num == 2)
            typeEB =  rs.getInt("id"); 
          else if (num == 3)
            typeTEST =  rs.getInt("id"); 
          else if (num == 4)
            typeMEP =  rs.getInt("id"); 
          else if (num == 5)
            typeVSR =  rs.getInt("id");           
         num++;
      }}   catch (SQLException s) {s.getMessage();}      
    
    this.str_ListeCouleurPeriode = "";
    
    this.str_ListeCouleurPeriode += typeEB;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurEB;
    
    this.str_ListeCouleurPeriode += "@"; 
    
    this.str_ListeCouleurPeriode += typeTEST;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurDEV;  
    
    this.str_ListeCouleurPeriode += "@"; 
    
    this.str_ListeCouleurPeriode += typeMEP;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurTEST;
    
    this.str_ListeCouleurPeriode += "@"; 
    
    this.str_ListeCouleurPeriode += typeVSR;
    this.str_ListeCouleurPeriode += ";";
    this.str_ListeCouleurPeriode += this.couleurMES;
    
 
    req = "UPDATE    Membre  SET ";
    req +=" ListeCouleurPeriode ='"+ this.str_ListeCouleurPeriode + "'";    
    req += " WHERE idMembre ="+ this.id;

    myCnx.ExecUpdate(st,nomBase,req,true);
  }
  
  public static void getListeCollaborateur(String nomBase,Connexion myCnx, Statement st){

    String req="EXEC LISTEACTEUR_SelectMembre";
    ListeCollaborateur.removeAllElements();

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Collaborateur theCollaborateur = null;
    try {
       while (rs.next()) {
         int serviceMembre = rs.getInt(1);
         int idMembre = rs.getInt(2);
         String nomMembre = rs.getString(3);
         String prenomMembre = rs.getString(4);

         theCollaborateur = new Collaborateur(idMembre);
         theCollaborateur.service= serviceMembre;
         theCollaborateur.nom= nomMembre.replaceAll("'", "");
         theCollaborateur.prenom= prenomMembre.replaceAll("'", "");
         //theST.dump();
         ListeCollaborateur.addElement(theCollaborateur);
       }
        } catch (SQLException s) {s.getMessage();}

  }
  
  public static void getListeProfilsMigration(String nomBase,Connexion myCnx, Statement st){

    ResultSet rs = null;
    String req = "SELECT     id, idMembre, nom, description, Annee, Mois, isJalon0, isJalon1, isJalon2, isJalon3, isJalon4, ";
    req+= " idClient, idSt, isDecalage, isTaches, couleurPeriode1, couleurPeriode2, couleurPeriode3,  ";
    req+= "                      couleurPeriode4, ListeTags, ListeApplications, ListeProjets, idCp, nbPage, ListeCouleurPeriode, ListePeriode";
    req+= " FROM         ProfilsProjet ";
    req+= " ORDER BY nom ";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
       while (rs.next()) {
      Profil theProfil = new Profil(rs.getInt("id"));
      theProfil.idMembre = rs.getInt("idMembre");
      theProfil.nom = rs.getString("nom");
      theProfil.desc = rs.getString("description");
      theProfil.Annee = rs.getInt("Annee");
      theProfil.Mois = rs.getInt("Mois");
      theProfil.isJalon0 = rs.getInt("isJalon0");
      theProfil.isJalon1 = rs.getInt("isJalon1");
      theProfil.isJalon2 = rs.getInt("isJalon2");
      theProfil.isJalon3 = rs.getInt("isJalon3");
      theProfil.isJalon4 = rs.getInt("isJalon4");     
      
      theProfil.idClient = rs.getInt("idClient");
      theProfil.idSt = rs.getInt("idSt");
      theProfil.isDecalage = rs.getInt("isDecalage");
      theProfil.isTaches = rs.getInt("isTaches");

      theProfil.couleurPeriode1 = rs.getString("couleurPeriode1");
      if ((theProfil.couleurPeriode1 == null) || (theProfil.couleurPeriode1.equals(""))) theProfil.couleurPeriode1="F9FF40";
      
      theProfil.couleurPeriode2 = rs.getString("couleurPeriode2");
      if ((theProfil.couleurPeriode2 == null) || (theProfil.couleurPeriode2.equals(""))) theProfil.couleurPeriode2="4BFF14";
      
      theProfil.couleurPeriode3 = rs.getString("couleurPeriode3");
      if ((theProfil.couleurPeriode3 == null) || (theProfil.couleurPeriode3.equals(""))) theProfil.couleurPeriode3="FF0303";
      
      theProfil.couleurPeriode4 = rs.getString("couleurPeriode4");
      if ((theProfil.couleurPeriode4 == null) || (theProfil.couleurPeriode4.equals(""))) theProfil.couleurPeriode4="D6E2D3";
      
      theProfil.ListeTags = rs.getString("ListeTags");
      theProfil.ListeApplications = rs.getString("ListeApplications");
      theProfil.ListeProjets = rs.getString("ListeProjets");
      
      theProfil.idCp = rs.getInt("idCp");
      theProfil.nbPage = rs.getInt("nbPage");
      
      ListeProfil_static.addElement(theProfil);
            

      }
    }catch (SQLException s) {s.getMessage(); } 

  }  

  public static void getListeCollaborateurWithCompetence(String nomBase,Connexion myCnx, Statement st){

    String req="SELECT DISTINCT CollaborateurCompetence.idCollaborateur, Membre.nomMembre, Membre.prenomMembre";
    req+="    FROM         CollaborateurCompetence INNER JOIN";
    req+="                  Membre ON CollaborateurCompetence.idCollaborateur = Membre.idMembre";
    req+=" WHERE     (CollaborateurCompetence.note > 0) AND (Membre.serviceMembre <> 60)";

    ListeCollaborateur.removeAllElements();

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Collaborateur theCollaborateur = null;
    try {
       while (rs.next()) {

         theCollaborateur = new Collaborateur(rs.getInt("idCollaborateur"));
         theCollaborateur.nom= rs.getString("nomMembre");
         theCollaborateur.prenom= rs.getString("prenomMembre");

         //theST.dump();
         ListeCollaborateur.addElement(theCollaborateur);
       }
        } catch (SQLException s) {s.getMessage();}

  }

  public void getListeCollaborateurByEquipe(String nomBase,Connexion myCnx, Statement st){

    String req="exec ACTEUR_SelectByManager "+this.Login;

    ListeCollaborateur.removeAllElements();

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Collaborateur theCollaborateur = null;
    try {
       while (rs.next()) {
         String prenomMembre =rs.getString("prenomMembre");
         String nomMembre =rs.getString("nomMembre");
         String LoginMembre =rs.getString("LoginMembre");

         theCollaborateur = new Collaborateur(LoginMembre);
         theCollaborateur.nom= nomMembre;
         theCollaborateur.prenom= prenomMembre;
         //theST.dump();
         ListeCollaborateur.addElement(theCollaborateur);
       }
        } catch (SQLException s) {s.getMessage();}

  }

  public void getListeStWithInterface2(String nomBase,Connexion myCnx, Statement st){


    req="SELECT DISTINCT * FROM (";
    req+= " SELECT     idSt, nomSt";
    req+= "  FROM         ST_ListeSt_All_Attribute";
    req+= "  WHERE     (etatFicheVersionSt <> 4 ) AND (etatVersionSt <> 4) AND (serviceMoeVersionSt = "+this.service+") ";
    req+= "  and idSt in (SELECT     origineInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M') UNION SELECT extremiteInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M'))";

    req+= " UNION ";

    req+= "    SELECT DISTINCT ST_ListeSt_All_Attribute.idSt, ST_ListeSt_All_Attribute.nomSt";
    req+= "    FROM         Membre INNER JOIN";
    req+= "              EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
    req+= "              Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
    req+= "              ST_ListeSt_All_Attribute ON Equipe.nom = ST_ListeSt_All_Attribute.nomSt INNER JOIN";
    req+= "              Roadmap ON ST_ListeSt_All_Attribute.idVersionSt = Roadmap.idVersionSt";
    req+= "    WHERE     (Membre.LoginMembre = '"+this.Login+"') AND ((Membre.isProjet = 1) OR (Membre.isTest = 1)) AND (ST_ListeSt_All_Attribute.isRecurrent = 0)  AND (Roadmap.Etat <> 'MES')       ";
    req+= " 	and idSt in (SELECT     origineInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M') UNION SELECT extremiteInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M'))";
    req+= "    ) ";
    req+= " as mytable order by nomSt ";

   this.ListeST.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   ST theST = null;
   try {
      while (rs.next()) {
        int idSt = rs.getInt(1);
        String nomSt = rs.getString(2);
        theST = new ST(idSt,"idSt");
        theST.nomSt = nomSt;

        //theST.dump();
        this.ListeST.addElement(theST);
      }
       } catch (SQLException s) {s.getMessage();}

}
  
  public void getListeProfilsProjet(String nomBase,Connexion myCnx, Statement st){


    req="SELECT     id, nom";
    req+=" FROM         ProfilsProjet";
    req+=" WHERE idMembre = " + this.id;
    req+=" ORDER BY nom";

   this.ListeProfilsProjet.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   ST theST = null;
   try {
      while (rs.next()) {
        Profil theProfil = new Profil(rs.getInt("id"));
        theProfil.nom = rs.getString("nom");

        //theST.dump();
        this.ListeProfilsProjet.addElement(theProfil);
      }
       } catch (SQLException s) {s.getMessage();}

}  
  
  public void getListeStWithInterface(String nomBase,Connexion myCnx, Statement st){


    req="SELECT DISTINCT * FROM (";
    req+= " SELECT     idSt, nomSt";
    req+= "  FROM         ListeST";
    req+= "  WHERE     (etatFicheVersionSt = 3) AND (etatVersionSt <> 4) AND (serviceMoeVersionSt = "+this.service+") ";
    req+= "  and idSt in (SELECT     origineInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M') UNION SELECT extremiteInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M'))";

    req+= " UNION ";

    req+= "    SELECT DISTINCT ListeST.idSt, ListeST.nomSt";
    req+= "    FROM         Membre INNER JOIN";
    req+= "              EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
    req+= "              Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
    req+= "              ListeST ON Equipe.nom = ListeST.nomSt INNER JOIN";
    req+= "              Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";
    req+= "    WHERE     (Membre.LoginMembre = '"+this.Login+"') AND ((Membre.isProjet = 1) OR (Membre.isTest = 1)) AND (ListeST.isRecurrent = 0)  AND (Roadmap.Etat <> 'MES')       ";
    req+= " 	and idSt in (SELECT     origineInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M') UNION SELECT extremiteInterface FROM Interface WHERE     (typeInterface = 'S') OR   (typeInterface = 'A') OR   (typeInterface = 'M'))";
    req+= "    ) ";
    req+= " as mytable order by nomSt ";

   this.ListeST.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   ST theST = null;
   try {
      while (rs.next()) {
        int idSt = rs.getInt(1);
        String nomSt = rs.getString(2);
        theST = new ST(idSt,"idSt");
        theST.nomSt = nomSt;

        //theST.dump();
        this.ListeST.addElement(theST);
      }
       } catch (SQLException s) {s.getMessage();}

}   

  public void getListeStWithIhm(String nomBase,Connexion myCnx, Statement st){

    String req="SELECT     idSt, nomSt";
    req+="    FROM         ListeST";
    req+="     WHERE     (etatFicheVersionSt = 3) AND (etatVersionSt <> 4)  ";
    req+="     and idSt in (SELECT     origineInterface FROM Interface WHERE     (typeInterface = 'I') UNION SELECT extremiteInterface FROM Interface WHERE     (typeInterface = 'I'))";
    req+="     ORDER BY nomSt  ";

    req="SELECT DISTINCT * FROM (";
    req+= " SELECT     idSt, nomSt";
    req+= "  FROM         ListeST";
    req+= "  WHERE     (etatFicheVersionSt = 3) AND (etatVersionSt <> 4) AND (serviceMoeVersionSt = "+this.service+") ";
    req+= "  and idSt in (SELECT     origineInterface FROM Interface WHERE     (typeInterface = 'I')  UNION SELECT extremiteInterface FROM Interface WHERE     (typeInterface = 'I'))";

    req+= " UNION ";

    req+= "    SELECT DISTINCT ListeST.idSt, ListeST.nomSt";
    req+= "    FROM         Membre INNER JOIN";
    req+= "              EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
    req+= "              Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
    req+= "              ListeST ON Equipe.nom = ListeST.nomSt INNER JOIN";
    req+= "              Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";
    req+= "    WHERE     (Membre.LoginMembre = '"+this.Login+"') AND ((Membre.isProjet = 1) OR (Membre.isTest = 1)) AND (ListeST.isRecurrent = 0)  AND (Roadmap.Etat <> 'MES')       ";
    req+= " 	and idSt in (SELECT     origineInterface FROM Interface WHERE     (typeInterface = 'I') UNION SELECT extremiteInterface FROM Interface WHERE     (typeInterface = 'I'))";
    req+= "    ) ";
    req+= " as mytable order by nomSt ";

   this.ListeST.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   ST theST = null;
   try {
      while (rs.next()) {
        int idSt = rs.getInt(1);
        String nomSt = rs.getString(2);
        theST = new ST(idSt,"idSt");
        theST.nomSt = nomSt;

        //theST.dump();
        this.ListeST.addElement(theST);
      }
       } catch (SQLException s) {s.getMessage();}

}

  public  void setListeInterfaces(String nomBase,Connexion myCnx, Statement st, Statement st2){

    String req = "DELETE FROM tempInterface";
    myCnx.ExecReq(st, myCnx.nomBase, req);

    this.getListeStWithInterface( nomBase, myCnx,  st);

    for (int i=0; i < this.ListeST.size();i++)
    {
      ST theST = (ST)this.ListeST.elementAt(i);
      theST.getListeInterfacesAsyncOuSync( nomBase, myCnx,  st,  st2 );
    }

}
  
  public  void setListeIhm(String nomBase,Connexion myCnx, Statement st, Statement st2){

    String req = "DELETE FROM tempInterface";
    myCnx.ExecReq(st, myCnx.nomBase, req);

    //this.getListeStWithInterface( nomBase, myCnx,  st);
    this.getListeStWithIhm( nomBase, myCnx,  st);
    
    for (int i=0; i < this.ListeST.size();i++)
    {
      ST theST = (ST)this.ListeST.elementAt(i);
      theST.getListeIhm( nomBase, myCnx,  st,  st2 );
    }

}  
  
  public  item getCouleurPeriode(typeJalon myTypeJalon, Vector ListeTypeJalonsEssentiels){

    item theItem = new item(-1);
    theItem.nom = "000";
    
    for (int i=0; i < this.ListeCouleurPeriode.size(); i++)
    {
        theItem = (item)this.ListeCouleurPeriode.elementAt(i);
        if (theItem.id == myTypeJalon.id)
        {
           return theItem;
        }        
    }
    
    for (int i=0; i < ListeTypeJalonsEssentiels.size(); i++)
    {
        typeJalon theTypeJalon = (typeJalon)ListeTypeJalonsEssentiels.elementAt(i);
        // 1- si Appartient au Profil
        
        // 1- si Appartient à l'ergonomie du collaborateur
        
        // 3- sinon prendre la couleur standard du Jalon
        if (theTypeJalon.id == myTypeJalon.id)
        {
           theItem = new item(theTypeJalon.id) ;
           theItem.nom = theTypeJalon.couleur;
           return theItem;
        }
    }
    return theItem;

}   

  public void getListeFavoris(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;
    Favori theFavori = null;
    String req="SELECT nom, id, Type, nom2 FROM  Favoris WHERE Login ='"+this.Login+"'";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      try {
         while (rs.next()) {
           String nom=rs.getString("nom");
           int id=rs.getInt("id");
           String Type=rs.getString("Type");
           String nom2=rs.getString("nom2");

           theFavori = new Favori (nom,id,Type,this.Login);
           theFavori.nom2 = nom2;

           this.ListeFavoris.addElement(theFavori);
         }
        } catch (SQLException s) {s.getMessage();}

  }

  public void getListeCollaborateurByCompetenceDesc(String nomBase,Connexion myCnx, Statement st){

    String result="";
    ResultSet rs=null;
    Favori theFavori = null;
    String req="SELECT     tempListeMembres.idMembre, tempListeMembres.note, tempListeMembres.nomMembre, tempListeMembres.prenomMembre, ";
    req+="                  Service.nomService";
    req+=" FROM         Membre INNER JOIN";
    req+="                   Service ON Membre.serviceMembre = Service.idService INNER JOIN";
    req+="                   tempListeMembres ON Membre.idMembre = tempListeMembres.idMembre";
    req+=" ORDER BY tempListeMembres.note DESC";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      try {
         while (rs.next()) {
           Competence theCompetence = new Competence();
           theCompetence.theCollaborateur = new Collaborateur(rs.getInt("idMembre"));
           theCompetence.note = rs.getInt("note");
           theCompetence.theCollaborateur.nom = rs.getString("nomMembre");
           theCompetence.theCollaborateur.prenom = rs.getString("prenomMembre");
           theCompetence.theCollaborateur.nomService = rs.getString("nomService");

           this.ListeCompetences.addElement(theCompetence);
         }
        } catch (SQLException s) {s.getMessage();}

  }

  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){
 ErrorSpecific myError = this.bd_Delete_NonAffecte( nomBase, myCnx,  st,  transaction);
 if (myError.cause == -1) return myError;
 
      String req="select count (*) as nb from membre where loginMembre = '"+this.Login+"'";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      int nb = 0;
    try {
       while (rs.next()) {
           nb = rs.getInt("nb");
       }
        } catch (SQLException s) {s.getMessage();}
    
    if (nb > 0)
    {
        myError.cause = -4;
        myError.Detail = "Doublon Detecte pour Login="+this.Login;
        return myError;
    }


    req = "EXEC ACTEUR_InsererMembre ";
      req+="'"+this.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"','";
      req+=this.prenom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"','";
      req+=this.service+"','";
      req+=this.fonction+"','";
      req+=this.mail.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"','";
      req+=this.Login.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"','";
      req+=this.isAdmin+"','";
      req+=this.isProjet+"','";
      req+=this.AO+"','";
      req+=this.Prix+"','";
      req+=this.dateEntree+"','";
      req+=this.dateSortie+"','";
      req+=this.intitulePoste.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"','";
      req+=this.niveau+"','";
      req+=this.Mission.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"','";
      req+=this.societe+"','";
      req+=this.Telephone_M+"','";
      req+=this.Telephone_B+"','";
      req+=this.Fax+"','";
      req+=this.isTest+"','";
      //req+=this.AppletNotSupported+"','";
      req+=this.isInterne+"','";
      req+=this.isBrief+"','";
      req+=this.isPo+"','";
      req+=this.Password+"','";
      req+=this.photo+"','";
      req+=this.isManager+"'";


    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

      req="SELECT  TOP (1) idMembre  FROM   Membre ORDER BY idMembre  DESC";
      rs = myCnx.ExecReq(st, nomBase, req);
      try { rs.next();
        this.id = rs.getInt("idMembre");
        } catch (SQLException s) {s.getMessage();} 
      
     return myError;
  }


  public static void bd_DeleteCompetences(String nomBase,Connexion myCnx, Statement st){


    String req = "delete  tempListeMembres";
    myCnx.ExecUpdate(st,nomBase,req,true);


  }

  public void bd_InsertCompetences(String nomBase,Connexion myCnx, Statement st,  int note){


      String req = "INSERT tempListeMembres (Login, idMembre, nomMembre, prenomMembre, noteCompetence)";
      req+=" VALUES ('"+this.Login+"','"+this.id+"','"+this.nom+"','"+this.prenom+"','"+note+"')";

    myCnx.ExecUpdate(st,nomBase,req,true);

  }

  public String bd_EnregProfil(String nomBase,Connexion myCnx, Statement st, String transaction){

    req = "update Membre set profilST = '"+this.profilST +"' WHERE idMembre="+this.id;
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     return "OK";
  }

  public String bd_EnregCouleurs(String nomBase,Connexion myCnx, Statement st, String transaction){

    req = "update Membre set ";
    req+=" ListeCouleurPeriode = '"+this.str_ListeCouleurPeriode +"'"; 
    req+=" WHERE idMembre="+this.id;
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     return "OK";
  }
  
  public ErrorSpecific Maj_Service(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    //update VersionSt set serviceMoeVersionSt = 204 WHERE     (respMoeVersionSt = 1961)
    //update VersionSt set serviceMoaVersionSt = 204 WHERE     (respMoaVersionSt = 1961)
    req = "update VersionSt set serviceMoeVersionSt = "+this.service+" WHERE     (respMoeVersionSt = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"Maj_Service",""+this.id);
    if (myError.cause == -1) return myError;

    req = "update VersionSt set serviceMoaVersionSt = "+this.service+" WHERE     (respMoaVersionSt = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"Maj_Service",""+this.id);
    if (myError.cause == -1) return myError;

     return myError;
  }
  
  public ErrorSpecific bd_EnregChargesProfil (String nomBase,Connexion myCnx, Statement st, String transaction, int Week_T0, int Week_EB,int Week_TEST,int Week_PROD, int anneeRef, Roadmap theRoadmap){
    ErrorSpecific myError = new ErrorSpecific();
    float theCharge=0;
    int nb_Collaborateurs=0;
    

      req = "DELETE FROM PlanDeCharge WHERE projetMembre = '"+this.id+"' AND anneeRef = '"+anneeRef+"'"+" AND idRoadmap = '"+theRoadmap.id+"'";;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"Maj_Service",""+this.id);
      if (myError.cause == -1) return myError;
      
  if (this.MOA.equals("yes"))
  {
  if (anneeRef == (theRoadmap.DdateT0.getYear() + 1900))
  {
    theCharge = (float)((float)theRoadmap.ProfilSpecification.charge/ (theRoadmap.nbMOA*(Week_EB - Week_T0 +1)));
    for (int i=Week_T0; i <= Week_EB; i++)
    {
        req = "INSERT PlanDeCharge (nomProjet,  projetMembre, semaine,Charge,anneeRef, idRoadmap) VALUES ('',  "+this.id+", "+i+""+", "+Utils.getRound(theCharge)+", "+anneeRef+", "+theRoadmap.id+")";
        myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"Maj_Service",""+this.id);
        if (myError.cause == -1) return myError;  
        this.chargeMoyenneEb = Utils.getRound(theCharge);
    }
  }
  }

  if (this.MOE.equals("yes"))
  {  
  if (anneeRef == (theRoadmap.DdateEB.getYear() + 1900))
  {  
    theCharge = (float)((float)theRoadmap.ProfilDeveloppement.charge/ (theRoadmap.nbMOE*(Week_TEST - Week_EB)));
    for (int i=Week_EB+1; i <= Week_TEST; i++)
    {
        req = "INSERT PlanDeCharge (nomProjet,  projetMembre, semaine,Charge,anneeRef, idRoadmap) VALUES ('',  "+this.id+", "+i+""+", "+Utils.getRound(theCharge)+", "+anneeRef+", "+theRoadmap.id+")";
        myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"Maj_Service",""+this.id);
        if (myError.cause == -1) return myError;   
        this.chargeMoyenneDev = Utils.getRound(theCharge);
    }
  }
  }

  if (anneeRef == (theRoadmap.DdateTest.getYear() + 1900))
  {  
      theCharge = (float)((float)theRoadmap.ProfilTest.charge/ ((theRoadmap.nbTesteurs)*(Week_PROD - Week_TEST)));
    for (int i=Week_TEST+1; i <= Week_PROD; i++)
    {
        req = "INSERT PlanDeCharge (nomProjet,  projetMembre, semaine,Charge,anneeRef, idRoadmap) VALUES ('',  "+this.id+", "+i+""+", "+Utils.getRound(theCharge)+", "+anneeRef+", "+theRoadmap.id+")";
        myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"Maj_Service",""+this.id);
        if (myError.cause == -1) return myError;    
        this.chargeMoyenneTest = Utils.getRound(theCharge);
    }
  }
 
     return myError;
  }  

  public ErrorSpecific bd_UpdateMOE(String nomBase,Connexion myCnx, Statement st, String transaction, int idManager){
     ErrorSpecific myError = null;
    // recuperation de la liste des ST dont l'acteur est MOE
    //this.getListeStMOE(nomBase, myCnx,  st);
    // Forcer la MOE au nom du manager pour tous les ST

      req = "UPDATE VersionSt SET respMoeVersionSt='"+idManager+"' WHERE respMoeVersionSt="+this.id;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateMOE",""+this.id);
      if (myError.cause == -1) return myError;
    
    
     return myError;
  }
  
  public ErrorSpecific bd_UpdateMOA(String nomBase,Connexion myCnx, Statement st, String transaction, int idManager){
     ErrorSpecific myError = null;
    // recuperation de la liste des ST dont l'acteur est MOE
    //this.getListeStMOE(nomBase, myCnx,  st);
    // Forcer la MOE au nom du manager pour tous les ST

      req = "UPDATE VersionSt SET respMoaVersionSt='"+idManager+"' WHERE respMoaVersionSt="+this.id;
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateMOA",""+this.id);
      if (myError.cause == -1) return myError;
    
    this.chaine_a_ecrire = "L'Acteur: "+this.nom+ " a bien ete reaffecte a son Manager (MOA de ST)";
     return myError;
  }  
  
  public String bd_UpdateColor(String nomBase,Connexion myCnx, Statement st, String transaction){

    req = "UPDATE Membre SET couleurEB='"+this.couleurEB+"' WHERE idMembre="+this.id;
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

    req = "UPDATE Membre SET couleurDEV='"+this.couleurDEV+"' WHERE idMembre="+this.id;
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

    req = "UPDATE Membre SET couleurTEST='"+this.couleurTEST+"' WHERE idMembre="+this.id;
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
    
    req = "UPDATE Membre SET couleurMES='"+this.couleurMES+"' WHERE idMembre="+this.id;
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}    

     return "OK";
  }

  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){

    ErrorSpecific myError = this.bd_Delete_NonAffecte( nomBase, myCnx,  st,  transaction);
    if (myError.cause == -1) return myError;

    req = "EXEC ACTEUR_MajMembre ";
      req+="'"+this.id+"',";
      req+="'"+this.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
      req+="'"+this.prenom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
      req+="'"+this.service+"',";
      req+="'"+this.fonction+"',";
      req+="'"+this.mail.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
      req+="'"+this.Login.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
      req+="'"+this.isAdmin+"',";
      req+="'"+this.isProjet+"',";
      req+="'"+this.AO+"',";
      req+="'"+this.Prix+"',";
      req+="'"+this.dateEntree+"',";
      req+="'"+this.dateSortie+"',";
      req+="'"+this.intitulePoste.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
      req+="'"+this.niveau+"',";
      req+="'"+this.Mission.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
      req+="'"+this.societe+"',";
      req+="'"+this.Telephone_M+"',";
      req+="'"+this.Telephone_B+"',";
      req+="'"+this.Fax+"',";
      req+="'"+this.isTest+"',";
      //req+="'"+this.AppletNotSupported+"',";
      req+="'"+this.isInterne+"',";
      req+="'"+this.etp+"',";
      req+="'"+this.isBrief+"',";
      req+="'"+this.isPo+"',";
      req+="'"+this.Password+"',";
      req+="'"+this.photo+"',";
      req+="'"+this.isManager+"'";

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
      if (myError.cause == -1) return myError;

     return myError;
  }

public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
   
  ErrorSpecific myError = new ErrorSpecific();
  ResultSet rs = null;
  
  String reqSuppActeur = "exec ACTEUR_selectStByMembre " + this.id;
  rs = myCnx.ExecReq(st, myCnx.nomBase, reqSuppActeur);
  String ListeSt = "";
  try {
    while (rs.next()) {
      if (!ListeSt.equals("")) ListeSt += ", ";
      ListeSt += rs.getString(1) ;
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
  
    req = "UPDATE  Roadmap SET idMembre = NULL WHERE (idMembre = " + this.id + ")";
    myError = myCnx.ExecUpdateTransaction(st, nomBase, req, true, transaction,  getClass().getName(), "bd_Delete",  "" + this.id);
    if (myError.cause == -1) return myError;
    
    req = "DELETE  FROM    EquipeMembre WHERE (idMembre = " + this.id + ")";
    myError = myCnx.ExecUpdateTransaction(st, nomBase, req, true, transaction,  getClass().getName(), "bd_Delete",  "" + this.id);
    if (myError.cause == -1) return myError;

    req = "DELETE FROM Membre WHERE idMembre =" + this.id;
    myError = myCnx.ExecUpdateTransaction(st, nomBase, req, true, transaction,  getClass().getName(), "bd_Delete", "" + this.id);
    if (myError.cause == -1) return myError;

  
  return myError;
}

  public void getIdManager(String nomBase,Connexion myCnx, Statement st){

    ResultSet rs = null;
    req = "SELECT     Membre_1.idMembre, Membre_1.nomMembre, Membre_1.fonctionMembre, Membre_1.prenomMembre, Membre_1.LoginMembre";
    req+= " FROM         Membre INNER JOIN";
    req+= "                      Service ON Membre.serviceMembre = Service.idService INNER JOIN";
    req+= "                       Membre AS Membre_1 ON Service.idService = Membre_1.serviceMembre";
    req+= " WHERE     (Membre.idMembre = "+this.id+") AND (Membre_1.isManager = 1)  ";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String ListeSt="";
    try { while(rs.next()) {
      this.idManager=rs.getInt("idMembre");
  } }catch (SQLException s) {s.getMessage();}


  }
  
  public int getIdEquipeService(String nomBase,Connexion myCnx, Statement st){
    int idEquipe = -1;

    ResultSet rs = null;
    req = "SELECT     Equipe.id, Equipe.nom";
    req += "    FROM         Membre INNER JOIN";
    req += "                   Service ON Membre.serviceMembre = Service.idService INNER JOIN";
    req += "                   Equipe ON Service.nomService = Equipe.nom";
    req += " WHERE     (Membre.LoginMembre = '"+this.Login+"')";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String ListeSt="";
    try { while(rs.next()) {
      idEquipe=rs.getInt("id");
  } }catch (SQLException s) {s.getMessage();}

    return idEquipe;

  }

  public String bd_InsertCharges(String nomBase,Connexion myCnx, Statement st, String transaction, int semaine, int Annee){


    for (int i=0; i < this.ListeActions.size(); i++)
    {
       Action theAction= (Action)this.ListeActions.elementAt(i);
       req =  "DELETE FROM Consomme WHERE (idAction = "+theAction.id+ ")"+" AND (semaine = "+semaine+ ")"+" AND (anneeRef = "+Annee+ ")"+ "AND (projetMembre = '"+this.id+"')";
       if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

       for (int j=0; j < theAction.ListeCharges.size(); j++)
       {
          Charge theCharge = (Charge)theAction.ListeCharges.elementAt(j);

          req = "INSERT Consomme (projetMembre, semaine, Charge, anneeRef, idAction)";
          req+="  VALUES (";
          req+="'"+this.id+"'";
          req+=",";
          req+="'"+theCharge.semaine+"'";
          req+=",";
          req+="'"+theCharge.hj+"'";
          req+=",";
          req+="'"+theCharge.anneeRef+"'";
          req+=",";
          req+="'"+theAction.id+"'";
          req+=")";

          if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

          String Cr = "KO";
          if (((theAction.idEtat == 1) || (theAction.idEtat == 5)) && (theCharge.hj > 0))
            Cr = theAction.bd_Update( nomBase, myCnx,  st, transaction,2);

       }

    }



    return "OK";

  }

  public String bd_updateIsProjet(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs = null;
    req = "SELECT     COUNT(Membre.idMembre) AS nb";
    req+="    FROM         Membre INNER JOIN";
    req+="                   Service ON Membre.serviceMembre = Service.idService INNER JOIN";
    req+="                   Equipe ON Service.nomService = Equipe.nom";
    req+=" WHERE     (Membre.LoginMembre = '"+this.Login+"')";

    int nb = 0;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();

      this.id = rs.getInt(1);
      nb++;

    } catch (SQLException s) {s.getMessage(); }

    if (nb >0)
    {
    req = "UPDATE    Membre SET   isProjet = 1 WHERE     (serviceMembre  = "+this.service+")";
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
    }
    return "OK";
  }

  public ErrorSpecific bd_Delete_NonAffecte(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs = null;
    int theService = -1;
    ErrorSpecific myError = new ErrorSpecific();
    req = "SELECT     idService  FROM    Service WHERE     (nomService = 'Autre')";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String ListeSt="";
    try { while(rs.next()) {
      theService=rs.getInt(1);
    } }catch (SQLException s) {s.getMessage();}

    if (theService != -1)
    {
      //req = "DELETE Membre WHERE serviceMembre ="+theService+ " AND LoginMembre = '"+this.Login+"'";
      req = "DELETE Membre WHERE serviceMembre ="+theService+ " AND LoginMembre = '"+this.Login.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete_NonAffecte",""+this.id);
      }

   return myError;
}

 public String bd_Exist(String nomBase,Connexion myCnx, Statement st, String transaction){
   ResultSet rs = null;
   String theService = "";
   String theDirection = "";
   this.chaine_a_ecrire = "";
   req = "SELECT  Service.nomService, Direction.nomDirection";
   req +="     FROM         Membre INNER JOIN";
   req +="                   Service ON Membre.serviceMembre = Service.idService INNER JOIN";
   req +="                   Direction ON Service.dirService = Direction.idDirection";
   req +="                   WHERE     (Membre.LoginMembre = '"+this.Login+"') AND (Service.nomService <> 'Autre')";
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);
   String ListeSt="";
   try { while(rs.next()) {
     theService=rs.getString(1);
     theDirection=rs.getString(2);
     this.chaine_a_ecrire = "L'Acteur: "+this.nom+ " est d�j� r�f�renc� sous: "+theDirection+"/"+theService;
   } }catch (SQLException s) {s.getMessage();}


  return this.chaine_a_ecrire;
}


public void getCommercial(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs = null;
  req = "exec ACTEUR_SelectCommercial "+this.id;

rs = myCnx.ExecReq(st, myCnx.nomBase, req);
try { rs.next();

  this.prenomCommercial = rs.getString(1);
  this.nomCommercial = rs.getString(2);
  this.TelephoneCommercial = rs.getString(3);
  this.mailCommercial = rs.getString(4);

  } catch (SQLException s) {s.getMessage(); }

}

public int idVersionStPO(String nomBase,Connexion myCnx, Statement st , int Annee){
  int idVersionSt=-1;

  Service theService = new Service(this.service);
  theService.getAllFromBd(nomBase,myCnx,st);
  String nomStPO = "zPO"+Annee+" ("+theService.nom+")";
  ST theStPO = new ST(nomStPO);
  theStPO.getAllFromBd(nomBase,myCnx,st);

  return theStPO.idVersionSt;
}

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;
    Date theDate = null;

    if (this.TypeCreation.equals("byId"))
      req = "EXEC ACTEUR_SelectUnMembre "+this.id;
    else if (this.TypeCreation.equals("byLogin"))
      req = "EXEC ACTEUR_SelectUnmembreByLogin '"+this.Login+"'";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();

      this.nom = rs.getString(1);
      this.prenom = rs.getString(2);
      this.direction = rs.getInt(3);
      this.service = rs.getInt(4);
      this.fonction = rs.getString(5);
      this.mail = rs.getString(6);
      this.Login = rs.getString(7);
      this.isAdmin = rs.getInt(8);
      this.isProjet = rs.getInt(9);

      this.AO =rs.getString(10);
      if (this.AO == null) this.AO = "";

      this.Prix =rs.getInt(11);
      theDate = rs.getDate(12);
      if (theDate !=null)
       {
         this.dateEntree = theDate.getDate() + "/" + (theDate.getMonth() + 1) +
             "/" + (theDate.getYear() + 1900);
       }
       else this.dateEntree = "";

       theDate = rs.getDate(13);
       if (theDate !=null)
        {
          this.dateSortie = theDate.getDate() + "/" + (theDate.getMonth() + 1) +
              "/" + (theDate.getYear() + 1900);
       }
       else this.dateSortie = "";

      this.intitulePoste =rs.getString(14);
      if (this.intitulePoste == null) this.intitulePoste = "";

      this.niveau =rs.getInt(15);
      this.Mission =rs.getString(16);
      if (this.Mission == null) this.Mission = "";

      this.societe =rs.getInt(17);
      this.nomSociete = rs.getString("nomService");
      this.nomDirection = rs.getString("nomDirection");

      this.id =  rs.getInt("idMembre");
      this.Telephone_M = rs.getString("Telephone_M");
      if (this.Telephone_M == null) this.Telephone_M = "";
      this.Telephone_B = rs.getString("Telephone_B");
      if (this.Telephone_B == null) this.Telephone_B = "";
      this.Fax = rs.getString("Fax");
      if (this.Fax == null) this.Fax = "";
      this.isTest = rs.getInt("isTest");
      //this.AppletNotSupported = rs.getInt("AppletNotSupported");
      this.isInterne = rs.getInt("AppletNotSupported");
      
      this.couleurEB = rs.getString("couleurEB");
      if (this.couleurEB == null ) this.couleurEB = "F9FF40";
      
      this.couleurDEV = rs.getString("couleurDEV");
      if (this.couleurDEV == null ) this.couleurDEV = "4BFF14";
      
      this.couleurTEST = rs.getString("couleurTEST");
      if (this.couleurTEST == null ) this.couleurTEST = "FF0303";
      
      this.etp = rs.getFloat("etp");
      this.profilST = rs.getString("profilST");
      this.isBrief = rs.getInt("listediffusionBrief");
      this.isPo = rs.getInt("isPo");
      
      this.Password = rs.getString("password");
      if (this.Password == null) this.Password = "";
      
      this.nomService = rs.getString("service");
      this.photo = rs.getString("photo");
      if ((this.photo == null) || (this.photo.equals("")))
          this.photo = "images/Direction.png";
      
      this.isManager = rs.getInt("isManager");
      
      this.couleurMES = rs.getString("couleurMES");
      if (this.couleurMES == null ) this.couleurMES = "D6E2D3";
      
      this.str_ListeCouleurPeriode = rs.getString("ListeCouleurPeriode");
      if (this.str_ListeCouleurPeriode != null && !this.str_ListeCouleurPeriode.equals(""))
      {
         String[] oneCouleurPeriode =  this.str_ListeCouleurPeriode.split("@");
         for (int i=0; i < oneCouleurPeriode.length; i++)
         {
             String[] CouleurPeriode =  oneCouleurPeriode[i].split(";");
             item theItem = new item();
             theItem.id = Integer.parseInt(CouleurPeriode[0]);
             theItem.nom = CouleurPeriode[1];
             this.ListeCouleurPeriode.addElement(theItem);
         }
      }
      
      } catch (SQLException s) {s.getMessage(); }
    

      nomComplet = this.prenom + " " + this.nom;;

  }
  
  public int getStateByWeekByYear (String nomBase,Connexion myCnx, Statement st,int week, int annee){
    ResultSet rs;
    int idEtat = 1;
    req="SELECT DISTINCT etat";
    req+=" FROM         PlanDeCharge";
    req+=" WHERE     (projetMembre = "+this.id+") AND (semaine = "+week+") AND (anneeRef = "+annee+")";
    
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    
     try {
        while(rs.next())
        {
            idEtat= rs.getInt("etat");
        }

    }
    catch (SQLException s) { }    
    return idEtat;
  
} 
  
  public int getStateByWeekByYear (String nomBase,Connexion myCnx, Statement st,int week, int annee, int idRoadmap){
    ResultSet rs;
    int idEtat = 1;
    req="SELECT DISTINCT etat";
    req+=" FROM         PlanDeCharge";
    req+=" WHERE     (projetMembre = "+this.id+") AND (semaine = "+week+") AND (anneeRef = "+annee+")  AND (idRoadmap = "+idRoadmap+")";
    
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    
     try {
        while(rs.next())
        {
            idEtat= rs.getInt("etat");
        }

    }
    catch (SQLException s) { }    
    return idEtat;
  
}   

  public void getListeEquipes(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs;

  req = "SELECT     Equipe.id, Equipe.nom, Equipe.[desc], EquipeMembre.type, EquipeMembre.idMembre FROM   Equipe INNER JOIN     EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe WHERE     (EquipeMembre.type = 'resp') AND (EquipeMembre.idMembre = "+this.id+")";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     int idEquipe= rs.getInt(1);
     Equipe theEquipe = new Equipe(idEquipe);
     theEquipe.nom = rs.getString(2);
     theEquipe.description = rs.getString(3);
     this.ListeEquipes.addElement(theEquipe);
        }

  }
   catch (SQLException s) { }
}

 public void getProfilSt(){
 ResultSet rs;

 String str="";
 if ((this.profilST == null ) || this.profilST.equals("")) return;
 for (StringTokenizer t = new StringTokenizer(this.profilST, ";") ; t.hasMoreTokens() ; ) {
   str = t.nextToken();
   this.ListeProfilST.addElement(str);
 }
}

 public void bd_InsertInMembre(String nomBase,Connexion myCnx, Statement st){
   ResultSet rs = null;
   int nb = 0;
   req = "SELECT     COUNT(*) AS nb FROM   Membre WHERE     (LoginMembre = '"+Login+"') ";
   rs = myCnx.ExecReq(st, nomBase, req);
    try { rs.next();
           nb = rs.getInt(1);
    } catch (SQLException s) {s.getMessage();}

    if (nb == 0)
    {
      String mail = Login+"@" + myCnx.mail;
      String prenomMembre=Login.substring(0,1);
      String nomMembre=Login.substring(1, Login.length());
      req = "INSERT INTO Membre  (nomMembre, prenomMembre,fonctionMembre,serviceMembre,mail,LoginMembre,isAdmin) VALUES     ('"+nomMembre+"', '"+prenomMembre+"', '"+"-- Autre --"+"', '"+15+"', '"+mail+"', '"+Login+"', '"+0+"')";
      rs = myCnx.ExecReq(st, nomBase, req);
    }


 }

 public String bd_InsertProjets(String nomBase,Connexion myCnx, Statement st, String transaction, int anneeRef){
   ResultSet rs=null;
   //req = "DELETE FROM PlanDeCharge WHERE projetMembre = '"+this.id+"' AND anneeRef = '"+anneeRef+"'";;
  //if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}


    for (int i=0; i < this.ListeProjets.size(); i++)
    {
      Roadmap theRoadmap = (Roadmap)this.ListeProjets.elementAt(i);
      req = "DELETE FROM PlanDeCharge WHERE projetMembre = '"+this.id+"' AND anneeRef = '"+anneeRef+"'"+" AND idRoadmap = '"+theRoadmap.id+"'";;
       if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

      for (int j=0; j < theRoadmap.ListeCharges.size(); j++)
      {
        Charge theCharge = (Charge)theRoadmap.ListeCharges.elementAt(j);
        if (theCharge.anneeRef == anneeRef)
        {
          //req = "INSERT PlanDeCharge (nomProjet,  projetMembre, semaine,Charge,anneeRef, idRoadmap) VALUES ('"+theRoadmap.nomSt + "-" + theRoadmap.version+"',  "+this.id+", "+theCharge.semaine+""+", "+theCharge.hj+", "+anneeRef+", "+theRoadmap.id+")";
          req = "INSERT PlanDeCharge (nomProjet,  projetMembre, semaine,Charge,anneeRef, idRoadmap) VALUES ('',  "+this.id+", "+theCharge.semaine+""+", "+theCharge.hj+", "+anneeRef+", "+theRoadmap.id+")";
          if (myCnx.ExecUpdate(st, nomBase, req, true, transaction) == -1) {
            myCnx.trace(nomBase, "*** ERREUR *** req", req);
            return req;
          }
        }
      }
    }
    return "OK";

  }

  public String bd_UpdateChargeAction(String nomBase,Connexion myCnx, Statement st, String transaction, int anneeRef){
    ResultSet rs=null;
    //req = "DELETE FROM PlanDeCharge WHERE projetMembre = '"+this.id+"' AND anneeRef = '"+anneeRef+"'";;
    // if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}


     for (int i=0; i < this.ListeCharges.size(); i++)
     {
       Charge theCharge = (Charge)this.ListeCharges.elementAt(i);

       req = "DELETE FROM Consomme WHERE projetMembre = '"+this.id+"' AND anneeRef = '"+anneeRef+"'";
       req+=" AND idAction = "+theCharge.idObj;
       req+=" AND semaine = "+theCharge.semaine;
       if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

        req = "INSERT Consomme (projetMembre, semaine, Charge, anneeRef, idAction)";
        req+=" VALUES ("+this.id+", "+theCharge.semaine+""+", "+theCharge.hj+", "+theCharge.anneeRef+", "+theCharge.idObj+")";
        if (myCnx.ExecUpdate(st, nomBase, req, true, transaction) == -1) {
          myCnx.trace(nomBase, "*** ERREUR *** req", req);
          return req;
           }

     }
     return "OK";

  }

  public String getChargeByWeek(int week, int annee){
    float total = 0;
    for (int i=0; i < this.ListeCharges.size();i++)
    {
      Charge theCharge = (Charge)this.ListeCharges.elementAt(i);
      if ((theCharge.semaine == week) && (theCharge.anneeRef == annee))
      {
        total+=theCharge.hj;
      }
    }
    if (total == 0) return "";
    else  return ""+total;
  }

  public int getChargeByWeekVacation(String nomBase,Connexion myCnx, Statement st, int  anneeRef,  int  semaine){
    ResultSet rs = null;
    int charge = 0;
    req = "SELECT     Charge";
    req += " FROM         PlanDeCharge";
    req += " WHERE     (projetMembre = "+this.id+") AND (anneeRef = "+anneeRef+") AND (semaine = "+semaine+") AND (nomProjet LIKE '%Absences%')";
    rs = myCnx.ExecReq(st, nomBase, req);
    try {
    while(rs.next())
    {
       charge= rs.getInt("Charge");
          }

    }
   catch (SQLException s) { }
    return charge;
  }


  public void getIdService(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;


  }


public void getRoles(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs=null;
    int nb = 0;
    req = "SELECT     COUNT(*) AS nb FROM   Membre WHERE     (LoginMembre = '"+Login+"') AND (isAdmin = 1)";
    rs = myCnx.ExecReq(st, nomBase, req);
     try { rs.next();
            nb = rs.getInt(1);
            if (nb > 0) this.Admin = "yes";else this.Admin="no";
     } catch (SQLException s) {s.getMessage();}

     
     int nbManager = 0;
    req = "SELECT COUNT(*) AS nb FROM Membre INNER JOIN Service ON Membre.serviceMembre = Service.idService WHERE (Membre.LoginMembre = '"+Login+"')  AND (Membre.isManager = 1)  ";
    rs = myCnx.ExecReq(st, nomBase, req);
     try { rs.next();
            nbManager = rs.getInt(1);
            if ( nbManager > 0) this.Manager = "yes";else this.Manager="no";
     } catch (SQLException s) {s.getMessage();}

     int nbMOA = 0;
    req = "SELECT COUNT(*) AS nb FROM Membre INNER JOIN Service ON Membre.serviceMembre = Service.idService WHERE (Membre.LoginMembre = '"+Login+"') AND (Service.isMoa=1)";
    rs = myCnx.ExecReq(st, nomBase, req);
     try { rs.next();
            nbMOA = rs.getInt(1);
            if (nbMOA > 0) this.MOA = "yes";else this.MOA="no";
 } catch (SQLException s) {s.getMessage();}

 int nbMOE = 0;
req = "SELECT COUNT(*) AS nb FROM Membre INNER JOIN Service ON Membre.serviceMembre = Service.idService WHERE (Membre.LoginMembre = '"+Login+"') AND (Service.isMoe=1)";
rs = myCnx.ExecReq(st, nomBase, req);
 try { rs.next();
        nbMOE = rs.getInt(1);
        if (nbMOE > 0) this.MOE = "yes";else this.MOE="no";
 } catch (SQLException s) {s.getMessage();}

 int nbGOUV = 0;
req = "SELECT COUNT(*) AS nb FROM Membre INNER JOIN Service ON Membre.serviceMembre = Service.idService WHERE (Membre.LoginMembre = '"+Login+"') AND (Service.isGouvernance=1)";
rs = myCnx.ExecReq(st, nomBase, req);
 try { rs.next();
        nbMOE = rs.getInt(1);
        if (nbMOE > 0) this.GOUVERNANCE = "yes";else this.GOUVERNANCE="no";
 } catch (SQLException s) {s.getMessage();}


  }

public boolean isProjet(String nomBase,Connexion myCnx, Statement st, String nomSt ){
    ResultSet rs=null;
    
    req = "  SELECT * FROM";
    req += "      (";
    req += "      SELECT     Membre.idMembre, Membre.nomMembre, Membre.LoginMembre, Membre.prenomMembre, Membre.mail, Membre.photo, Service.nomService";
    req += "      FROM         Equipe INNER JOIN";
    req += "                            EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    req += "                            Membre ON EquipeMembre.idMembre = Membre.idMembre  INNER JOIN";
    req += "                             Service ON Membre.serviceMembre = Service.idService";
    req += "      WHERE     (Equipe.nom = '"+nomSt+"')";
    req += "      and     (EquipeMembre.type = 'collaborateur') ";

    req += "      UNION";

    req += "      SELECT     Membre.idMembre, Membre.nomMembre, Membre.LoginMembre, Membre.prenomMembre, Membre.mail, Membre.photo, Service.nomService";
    req += "      FROM         St INNER JOIN";
    req += "                            VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req += "                            Membre ON VersionSt.respMoeVersionSt = Membre.idMembre  INNER JOIN";
    req += "                             Service ON Membre.serviceMembre = Service.idService    ";
    req += "      WHERE     (St.nomSt = '"+nomSt+"')";

    req += "      UNION";

    req += "      SELECT     Membre.idMembre, Membre.nomMembre, Membre.LoginMembre, Membre.prenomMembre, Membre.mail, Membre.photo, Service.nomService";
    req += "      FROM         St INNER JOIN";
    req += "                            VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
    req += "                            Membre ON VersionSt.respMoaVersionSt = Membre.idMembre  INNER JOIN";
    req += "                             Service ON Membre.serviceMembre = Service.idService       ";
    req += "      WHERE     (St.nomSt = '"+nomSt+"') ";
    req += "      )";
    req += "      as mytable WHERE (LoginMembre = '"+this.Login+"') order by nomMembre";
    
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
    while(rs.next())
    {
       return true;
     }
    }
   catch (SQLException s) { }    


return false;
  }

  public void setAnonymous(String nomBase,Connexion myCnx, Statement st ){
    int nb=0;
    ResultSet rs;
    req = "SELECT     LoginMembre FROM  Membre WHERE     (intitulePoste = '"+this.Login+"')";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
    while(rs.next())
    {
       this.Login = rs.getString("LoginMembre");
     }
    }
   catch (SQLException s) { }

 }

 public String getOriginalLogin(String nomBase,Connexion myCnx, Statement st ){
  int nb=0;
  ResultSet rs;
  req = "SELECT     intitulePoste";
  req+="    FROM         Membre";
  req+=" WHERE     (LoginMembre = '"+this.Login+"')";

  String OriginalLogin = "";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     this.intitulePoste = rs.getString("intitulePoste");
     if (intitulePoste!= null && intitulePoste.length() <= 8)
     {
       OriginalLogin = this.intitulePoste;
     }
     else
     {
       OriginalLogin = this.Login;
     }
   }
  }
 catch (SQLException s) { }

 return OriginalLogin;
}


  public int getId(String nomBase,Connexion myCnx, Statement st ){
    int idGenerique=0;
    ResultSet rs;
    req = "SELECT     idMembre FROM   Membre WHERE     (LoginMembre = '"+this.Login+"')";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
    while(rs.next())
    {
       this.id = rs.getInt(1);
     }
    }
   catch (SQLException s) { }

   return this.id;
 }
  
  public boolean isSuperAdmin(){
      boolean superAdmin = false;

      if ((this.Login.toUpperCase().equals("JJAKUBOW") && this.Password.toUpperCase().equals("JJAKUBOW")) || (this.Login.toUpperCase().equals("LEMULLER") && this.Password.toUpperCase().equals("LEMULLER")))
          superAdmin = true;

   return superAdmin;
 }  


 public static void setListeCollaborateur(String theListe,String nomBase,Connexion myCnx, Statement st){
   ListeCollaborateur.removeAllElements();
   if (theListe.length() ==0) return;

   theListe =theListe + ";";

   int Index=theListe.indexOf(';');

   while (Index != -1)
   {
     String Login = theListe.substring (0,Index);
     //alert ("nomOM="+nomOM);
     theListe = theListe.substring(Index+1);
     Index = theListe.indexOf(';');

     Collaborateur theCollaborateur = new Collaborateur(Login);
     theCollaborateur.getAllFromBd(myCnx.nomBase, myCnx, st);
     if (theCollaborateur.nom.equals("")) continue;

     ListeCollaborateur.addElement(theCollaborateur);

   }

 }

 public String getisChecked(){

  for (int i=0; i < ListeCollaborateur.size(); i++)
  {
    String myLogin = ((Collaborateur)ListeCollaborateur.elementAt(i)).Login;
    if (this.Login.equals(myLogin)) return "checked";
  }
  return "";
}

 public String getisCharged(String nomBase,Connexion myCnx, Statement st, int idRoadmap){
  if (idRoadmap == -1) return "";
   ResultSet rs;
   String req = "SELECT     SUM(Charge) AS charge";
   req+=" FROM         PlanDeCharge";
   req+=" WHERE     (projetMembre = "+this.id+") AND (idRoadmap = "+idRoadmap+")";
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
   while(rs.next())
   {
      float charge = rs.getFloat("Charge");
      if (charge > 0) return "checked";
    }
   }
  catch (SQLException s) { }
   
  return "";
}
 
 public static int get_S_IdGenerique(String nomBase,Connexion myCnx, Statement st ){
   int S_idGenerique=0;
   ResultSet rs;
   String req = "SELECT     idMembre FROM     Membre WHERE     (nomMembre = 'Admin')";
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
   while(rs.next())
   {
      S_idGenerique = rs.getInt("idMembre");
    }
   }
  catch (SQLException s) { }

  return S_idGenerique;
 }
 
  public static int get_nbUtilisateurs(String nomBase,Connexion myCnx, Statement st ){
   int nb=0;
   ResultSet rs;
   String req = "SELECT     COUNT(*) AS nb FROM         Membre";
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
   while(rs.next())
   {
      nb = rs.getInt("nb");
    }
   }
  catch (SQLException s) { }

  return nb;
 }

public void getListeProjetsCollaborateur(String nomBase,Connexion myCnx, Statement st, int idCollaborateur, int Annee, Statement st2 ){
ResultSet rs;
ResultSet rs2;
String nom="";
Date DdateT0=null;
Date DdateEB=null;
Date DdateTest=null;
Date DdateProd=null;
Date DdateMes=null;
String dateT0="";
String dateEB="";
String dateTEST="";
String datePROD="";
String dateMES="";
int idVersionSt = -1;
int idRoadmap = -1;
String version= "";
Date theDate = null;

  req = "SELECT DISTINCT ";
  req += "    ListeProjets.nomProjet,ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.dateMes, ListeProjets.dateT0, ";
  req += "                       ListeProjets.dateTest, PlanDeCharge.idRoadmap, ListeProjets.dateT0MOE, ListeProjets.dateSpecMOE, ListeProjets.dateDevMOE, ListeProjets.dateTestMOE, ListeProjets.dateLivrMOE, ListeProjets.nomSt, ListeProjets.isRecurrent";
  req += " FROM         PlanDeCharge INNER JOIN";
  req += "                       Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap INNER JOIN";
  req += "                       ListeProjets ON Roadmap.idRoadmap = ListeProjets.idRoadmap";
  req += " WHERE     (PlanDeCharge.anneeRef = "+Annee+") AND (PlanDeCharge.projetMembre = "+idCollaborateur+") AND (PlanDeCharge.Charge > 0) ORDER BY ListeProjets.nomProjet ASC";


    // ------------------------ GOOD -------------------------------------------------//
    req =" SELECT DISTINCT ";
    req += "                     ListeProjets.nomProjet, ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.dateMes, ";
    req += "                    ListeProjets.dateT0, ListeProjets.dateTest, PlanDeCharge.idRoadmap, ListeProjets.dateT0MOE, ListeProjets.dateSpecMOE, ListeProjets.dateDevMOE, ";
    req += "                    ListeProjets.dateTestMOE, ListeProjets.dateLivrMOE, ListeProjets.nomSt, ListeProjets.isRecurrent, Roadmap.idPO";
    req += "  FROM         PlanDeCharge INNER JOIN";
    req += "                    Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req += "                    ListeProjets ON Roadmap.idRoadmap = ListeProjets.idRoadmap";
    req += "  WHERE      (PlanDeCharge.projetMembre = "+idCollaborateur+")   AND (Roadmap.standby <> 1)";
    req += "  and";
    req += "  (";
    req += "  (";

     req+=" (";
     req+=" (";
     req+=" (Roadmap.dateMep IS NOT NULL)     ";
     req+=" AND (Roadmap.dateMep >= CONVERT(DATETIME, '"+Annee+"-1-1 00:00:00',102)) ";
     req+=" AND (Roadmap.dateMep < CONVERT(DATETIME, '"+(Annee+1)+"-1-1 00:00:00',102)) ";
     req+=" )  ";

     req+="  OR  ";
     req+="   (    ";
     req+="   (Roadmap.dateT0 IS NOT NULL)     ";
     req+="   AND (Roadmap.dateT0 >= CONVERT(DATETIME, '"+Annee+"-1-1 00:00:00', 102) ) ";
     req+="   AND (Roadmap.dateT0 < CONVERT(DATETIME, '"+(Annee+1)+"-1-1 00:00:00', 102) ) ";     
     req+="   ) ";
     
     req+="  OR  ";
     req+="   (    ";
     req+="   (Roadmap.dateT0 IS NOT NULL) AND (Roadmap.dateMep IS NOT NULL)    ";
     req+="   AND (Roadmap.dateT0 < CONVERT(DATETIME, '"+Annee+"-1-1 00:00:00', 102) ) ";
     req+="   AND (Roadmap.dateMep >= CONVERT(DATETIME, '"+(Annee+1)+"-1-1 00:00:00', 102) ) ";     
     req+="   ) ";     
     
     req+=" ) ";
     req+=" and ListeProjets.isRecurrent = 0";    
    req += "  )";
    req += "  or";
    req += "  (";
    req += "  ListeProjets.isRecurrent = 1";
    req += "  )";
    req += "  )      ";
    req += "  ORDER BY ListeProjets.isRecurrent desc, ListeProjets.nomProjet";  
   
rs = myCnx.ExecReq(st, myCnx.nomBase, req);

try {
  while (rs.next()) {

    dateEB = "";
    datePROD = "";
    dateMES = "";
    idVersionSt = rs.getInt("idVersionSt");
    version = rs.getString("version");
    DdateEB = rs.getDate("dateEB");
    dateEB = Utils.getDateFrench(DdateEB);
    DdateProd = rs.getDate("dateMep");
    datePROD = Utils.getDateFrench(DdateProd);
    DdateMes = rs.getDate("dateMes");
    dateMES = Utils.getDateFrench(DdateMes);
    DdateT0 = rs.getDate("dateT0");
    dateT0 = Utils.getDateFrench(DdateT0);
    DdateTest = rs.getDate("dateTest");
    dateTEST = Utils.getDateFrench(DdateTest);
    idRoadmap= rs.getInt("idRoadmap");

    theDate = rs.getDate("dateT0MOE");
    String dateT0MOE = Utils.getDateFrench(theDate);

    nom = rs.getString("nomSt");
    int isRecurrent = rs.getInt("isRecurrent");

    req = "SELECT     SUM(Charge) AS totalCharge, anneeRef";
    req += " FROM         PlanDeCharge";
    req += " WHERE     (idRoadmap = '" + idRoadmap + "') AND (projetMembre ="+idCollaborateur+")";
    req += " GROUP BY anneeRef";
    req += " HAVING      (anneeRef = " + Annee + ")";

    rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);

    try {
      while (rs2.next()) {
        float totalCharge = rs2.getFloat("totalCharge");
        if (totalCharge > 0) {

          Roadmap theRoadmap = new Roadmap(idRoadmap);
          theRoadmap.nomSt=nom;
          theRoadmap.dateT0=dateT0;
          theRoadmap.dateEB=dateEB;
          theRoadmap.dateTest=dateTEST;
          theRoadmap.dateProd=datePROD;
          theRoadmap.dateMes=dateMES;
          
          theRoadmap.DdateT0=DdateT0;
          theRoadmap.DdateEB=DdateEB;
          theRoadmap.DdateTest=DdateTest;
          theRoadmap.DdateProd=DdateProd;
          theRoadmap.DdateMes=DdateMes;          

          theRoadmap.dateT0MOE=dateT0MOE;

          theRoadmap.idVersionSt=""+idVersionSt;
          theRoadmap.version=version;
          theRoadmap.idMembre = idCollaborateur;

          if ((isRecurrent == 0)) theRoadmap.isProjet = true; else theRoadmap.isProjet = false;
          if (this.id == this.idGenerique) theRoadmap.isGenerique = true; else theRoadmap.isGenerique = false;

          this.ListeProjets.addElement(theRoadmap);
        }
      }

    }
    catch (SQLException s) {}
  }
}
  catch (SQLException s) {}

}

public void getListeProjetsCollaborateurEtat(String nomBase,Connexion myCnx, Statement st, int idCollaborateur, int Annee, Statement st2, String Etat_AD,String Etat_T0, String Etat_EB, String Etat_TEST, String Etat_MEP, String Etat_MES ){
ResultSet rs;
ResultSet rs2;
String nom="";
String dateT0="";
String dateEB="";
String dateTEST="";
String datePROD="";
String dateMES="";
int idVersionSt = -1;
int idRoadmap = -1;
String version= "";
Date theDate = null;

   String ClauseEtat = "";
   int numClause = 0;

   if (Etat_AD.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (Roadmap.Etat = 'a demarrer')";
    ClauseEtat +=" )   ";
    numClause++;
   } 
   
   if (Etat_T0.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (Roadmap.Etat = 'T0')";
    ClauseEtat +=" )   ";
    numClause++;
   } 
   if (Etat_EB.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (Roadmap.Etat = 'EB')";
    ClauseEtat +=" )   ";
    numClause++;
   } 
   if (Etat_TEST.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (Roadmap.Etat = 'TEST')";
    ClauseEtat +=" )   ";
    numClause++;
   } 
   if (Etat_MEP.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (Roadmap.Etat = 'PROD')";
    ClauseEtat +=" )   ";
    numClause++;
   }    
   if (Etat_MES.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (Roadmap.Etat = 'MES')";
    ClauseEtat +=" )   ";
    numClause++;
   }    
   
   if (numClause > 0) ClauseEtat = " AND (" + ClauseEtat +")";
   if ((Etat_AD.equals("0")) && (Etat_T0.equals("0")) && (Etat_EB.equals("0")) && (Etat_TEST.equals("0")) && (Etat_MEP.equals("0")) && (Etat_MES.equals("0"))  )
    ClauseEtat = " AND (Roadmap.Etat = 'INCONNU')";

    // ------------------------ GOOD -------------------------------------------------//
    req =" SELECT DISTINCT ";
    req += "                     ListeProjets.nomProjet, ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.dateMes, ";
    req += "                    ListeProjets.dateT0, ListeProjets.dateTest, PlanDeCharge.idRoadmap, ListeProjets.dateT0MOE, ListeProjets.dateSpecMOE, ListeProjets.dateDevMOE, ";
    req += "                    ListeProjets.dateTestMOE, ListeProjets.dateLivrMOE, ListeProjets.nomSt, ListeProjets.isRecurrent, Roadmap.idPO";
    req += "  FROM         PlanDeCharge INNER JOIN";
    req += "                    Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req += "                    ListeProjets ON Roadmap.idRoadmap = ListeProjets.idRoadmap";
    req += "  WHERE      (PlanDeCharge.projetMembre = "+idCollaborateur+")   AND (Roadmap.standby <> 1)";
    req += "  and";
    req += "  (";
    req += "  (";

     req+=" (";
     req+=" (";
     req+=" (Roadmap.dateMep IS NOT NULL)     ";
     req+=" AND (case when Roadmap.dateMes > Roadmap.dateMep then Roadmap.dateMes else Roadmap.dateMep end >= CONVERT(DATETIME, '"+Annee+"-1-1 00:00:00',102)) ";
     req+=" AND (case when Roadmap.dateMes > Roadmap.dateMep then Roadmap.dateMes else Roadmap.dateMep end < CONVERT(DATETIME, '"+(Annee+1)+"-1-1 00:00:00',102)) ";
     req+=" )  ";

     req+="  OR  ";
     req+="   (    ";
     req+="   (Roadmap.dateT0 IS NOT NULL)     ";
     req+="   AND (Roadmap.dateT0 >= CONVERT(DATETIME, '"+Annee+"-1-1 00:00:00', 102) ) ";
     req+="   AND (Roadmap.dateT0 < CONVERT(DATETIME, '"+(Annee+1)+"-1-1 00:00:00', 102) ) ";     
     req+="   ) ";
     
     req+="  OR  ";
     req+="   (    ";
     req+="   (Roadmap.dateT0 IS NOT NULL) AND (Roadmap.dateMep IS NOT NULL)    ";
     req+="   AND (Roadmap.dateT0 < CONVERT(DATETIME, '"+Annee+"-1-1 00:00:00', 102) ) ";
     req+="   AND (case when Roadmap.dateMes > Roadmap.dateMep then Roadmap.dateMes else Roadmap.dateMep end >= CONVERT(DATETIME, '"+(Annee+1)+"-1-1 00:00:00', 102) ) ";     
     req+="   ) ";     
     
     req+=" ) ";
     req+=" and ListeProjets.isRecurrent = 0";    
    req += "  )";
    req += "  or";
    req += "  (";
    req += "  ListeProjets.isRecurrent = 1";
    req += "  )";
    req += "  )      ";
    req += ClauseEtat;
    req += "  ORDER BY ListeProjets.isRecurrent desc, ListeProjets.nomProjet";  
   
rs = myCnx.ExecReq(st, myCnx.nomBase, req);

try {
  while (rs.next()) {

    Roadmap theRoadmap = new Roadmap(-1);
    dateEB = "";
    datePROD = "";
    dateMES = "";
    theRoadmap.idVersionSt = ""+ rs.getInt("idVersionSt");
    theRoadmap.version = rs.getString("version");
    theRoadmap.DdateEB = rs.getDate("dateEB");
    theRoadmap.dateEB = Utils.getDateFrench(theRoadmap.DdateEB);
    theRoadmap.DdateProd = rs.getDate("dateMep");
    theRoadmap.dateProd = Utils.getDateFrench(theRoadmap.DdateProd);    
    theRoadmap.DdateMes = rs.getDate("dateMes");
    theRoadmap.dateMes = Utils.getDateFrench(theRoadmap.DdateMes);   
    theRoadmap.DdateT0 = rs.getDate("dateT0");
    theRoadmap.dateT0 = Utils.getDateFrench(theRoadmap.DdateT0);   
    theRoadmap.DdateTest = rs.getDate("dateTest");
    theRoadmap.dateTest = Utils.getDateFrench(theRoadmap.DdateTest);   

    theRoadmap.id= rs.getInt("idRoadmap");
    
    theRoadmap.DdateT0MOE = rs.getDate("dateT0MOE");
    theRoadmap.dateT0MOE = Utils.getDateFrench(theRoadmap.DdateT0MOE);


    theRoadmap.nomSt = rs.getString("nomSt");
    int isRecurrent = rs.getInt("isRecurrent");

          theRoadmap.idMembre = idCollaborateur;

          if ((isRecurrent == 0)) theRoadmap.isProjet = true; else theRoadmap.isProjet = false;
          if (this.id == this.idGenerique) theRoadmap.isGenerique = true; else theRoadmap.isGenerique = false;
          
          theRoadmap.idPO = ""+ rs.getInt("idPO");

          this.ListeProjets.addElement(theRoadmap);
        }
      
}
  catch (SQLException s) {}

}

public void getListeProjetsCollaborateurEtatGlissant(String nomBase,Connexion myCnx, Statement st, int idCollaborateur, int Annee, Statement st2, String Etat_AD,String Etat_T0, String Etat_EB, String Etat_TEST, String Etat_MEP, String Etat_MES, int monthRef ){
ResultSet rs;
ResultSet rs2;
String nom="";
String dateT0="";
String dateEB="";
String dateTEST="";
String datePROD="";
String dateMES="";
int idVersionSt = -1;
int idRoadmap = -1;
String version= "";
Date theDate = null;

   String ClauseEtat = "";
   int numClause = 0;

   if (Etat_AD.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (Roadmap.Etat = 'a demarrer')";
    ClauseEtat +=" )   ";
    numClause++;
   } 
   
   if (Etat_T0.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (Roadmap.Etat = 'T0')";
    ClauseEtat +=" )   ";
    numClause++;
   } 
   if (Etat_EB.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (Roadmap.Etat = 'EB')";
    ClauseEtat +=" )   ";
    numClause++;
   } 
   if (Etat_TEST.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (Roadmap.Etat = 'TEST')";
    ClauseEtat +=" )   ";
    numClause++;
   } 
   if (Etat_MEP.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (Roadmap.Etat = 'PROD')";
    ClauseEtat +=" )   ";
    numClause++;
   }    
   if (Etat_MES.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (Roadmap.Etat = 'MES')";
    ClauseEtat +=" )   ";
    numClause++;
   }    
   
   if (numClause > 0) ClauseEtat = " AND (" + ClauseEtat +")";
   if ((Etat_AD.equals("0")) && (Etat_T0.equals("0")) && (Etat_EB.equals("0")) && (Etat_TEST.equals("0")) && (Etat_MEP.equals("0")) && (Etat_MES.equals("0"))  )
    ClauseEtat = " AND (Roadmap.Etat = 'INCONNU')";
   
   int monthRefEnd = (monthRef + 10) % 12 + 1;

    // ------------------------ GOOD -------------------------------------------------//
    req =" SELECT DISTINCT ";
    req += "                     ListeProjets.nomProjet, ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.dateMes, ";
    req += "                    ListeProjets.dateT0, ListeProjets.dateTest, PlanDeCharge.idRoadmap, ListeProjets.dateT0MOE, ListeProjets.dateSpecMOE, ListeProjets.dateDevMOE, ";
    req += "                    ListeProjets.dateTestMOE, ListeProjets.dateLivrMOE, ListeProjets.nomSt, ListeProjets.isRecurrent, Roadmap.idPO";
    req += "  FROM         PlanDeCharge INNER JOIN";
    req += "                    Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req += "                    ListeProjets ON Roadmap.idRoadmap = ListeProjets.idRoadmap";
    req += "  WHERE      (PlanDeCharge.projetMembre = "+idCollaborateur+")   AND (Roadmap.standby <> 1)";
    req += "  and";
    req += "  (";
    req += "  (";

     req+=" (";
     req+=" (";
     req+=" (Roadmap.dateMep IS NOT NULL)     ";
     req+=" AND (case when Roadmap.dateMes > Roadmap.dateMep then Roadmap.dateMes else Roadmap.dateMep end >= CONVERT(DATETIME, '"+Annee+"-" +monthRef + "-1 00:00:00',102)) ";
     req+=" AND (case when Roadmap.dateMes > Roadmap.dateMep then Roadmap.dateMes else Roadmap.dateMep end < CONVERT(DATETIME, '"+(Annee+1)+"-" +monthRef + "-1 00:00:00',102)) ";
     req+=" )  ";

     req+="  OR  ";
     req+="   (    ";
     req+="   (Roadmap.dateT0 IS NOT NULL)     ";
     req+="   AND (Roadmap.dateT0 >= CONVERT(DATETIME, '"+Annee+"-" +monthRef + "-1 00:00:00',102)) ";
     req+="   AND (Roadmap.dateT0 < CONVERT(DATETIME, '"+(Annee+1)+"-" +monthRef + "-1 00:00:00',102)) ";
     req+="   ) ";
     
     req+="  OR  ";
     req+="   (    ";
     req+="   (Roadmap.dateT0 IS NOT NULL) AND (Roadmap.dateMep IS NOT NULL)    ";
     req+="   AND (Roadmap.dateT0 < CONVERT(DATETIME, '"+Annee+"-" +monthRef + "-1 00:00:00',102)) ";
     req+="   AND (case when Roadmap.dateMes > Roadmap.dateMep then Roadmap.dateMes else Roadmap.dateMep end >= CONVERT(DATETIME, '"+(Annee+1)+"-" +monthRef + "-1 00:00:00',102)) ";    
     req+="   ) ";     
     
     req+=" ) ";
     req+=" and ListeProjets.isRecurrent = 0";    
    req += "  )";
    req += "  or";
    req += "  (";
    req += "  ListeProjets.isRecurrent = 1";
    req += "  )";
    req += "  )      ";
    req += ClauseEtat;
    req += "  ORDER BY ListeProjets.isRecurrent desc, ListeProjets.nomProjet";  
   
rs = myCnx.ExecReq(st, myCnx.nomBase, req);

try {
  while (rs.next()) {

    Roadmap theRoadmap = new Roadmap(-1);
    dateEB = "";
    datePROD = "";
    dateMES = "";
    theRoadmap.idVersionSt = ""+ rs.getInt("idVersionSt");
    theRoadmap.version = rs.getString("version");
    theRoadmap.DdateEB = rs.getDate("dateEB");
    theRoadmap.dateEB = Utils.getDateFrench(theRoadmap.DdateEB);
    theRoadmap.DdateProd = rs.getDate("dateMep");
    theRoadmap.dateProd = Utils.getDateFrench(theRoadmap.DdateProd);    
    theRoadmap.DdateMes = rs.getDate("dateMes");
    theRoadmap.dateMes = Utils.getDateFrench(theRoadmap.DdateMes);   
    theRoadmap.DdateT0 = rs.getDate("dateT0");
    theRoadmap.dateT0 = Utils.getDateFrench(theRoadmap.DdateT0);   
    theRoadmap.DdateTest = rs.getDate("dateTest");
    theRoadmap.dateTest = Utils.getDateFrench(theRoadmap.DdateTest);   

    theRoadmap.id= rs.getInt("idRoadmap");
    
    theRoadmap.DdateT0MOE = rs.getDate("dateT0MOE");
    theRoadmap.dateT0MOE = Utils.getDateFrench(theRoadmap.DdateT0MOE);


    theRoadmap.nomSt = rs.getString("nomSt");
    int isRecurrent = rs.getInt("isRecurrent");

          theRoadmap.idMembre = idCollaborateur;

          if ((isRecurrent == 0)) theRoadmap.isProjet = true; else theRoadmap.isProjet = false;
          if (this.id == this.idGenerique) theRoadmap.isGenerique = true; else theRoadmap.isGenerique = false;
          
          theRoadmap.idPO = ""+ rs.getInt("idPO");

          this.ListeProjets.addElement(theRoadmap);
        }
      
}
  catch (SQLException s) {}

}

public void getListeProjetsCollaborateurOuverts(String nomBase,Connexion myCnx, Statement st, int idCollaborateur, int Annee, Statement st2 ){
  ResultSet rs;
  ResultSet rs2;
  String nom="";
  String dateT0="";
  String dateEB="";
  String dateTEST="";
  String datePROD="";
  String dateMES="";
  
  Date DdateT0 = null;
  Date DdateEB = null;
  Date DdateTest = null;
  Date DdateProd = null;
  
  int idVersionSt = -1;
  int idRoadmap = -1;
  String version= "";
  Date theDate = null;

    req = "SELECT DISTINCT ";
    req += "                     PlanDeCharge.nomProjet, ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.dateMes, ";
    req += "                     ListeProjets.dateT0, ListeProjets.dateTest, PlanDeCharge.idRoadmap, ListeProjets.dateT0MOE, ListeProjets.dateSpecMOE, ListeProjets.dateDevMOE, ";
    req += "                     ListeProjets.dateTestMOE, ListeProjets.dateLivrMOE, ListeProjets.nomSt, ListeProjets.isRecurrent, PlanOperationnelClient.idWebPo, PlanOperationnelClient.charge, ";
    req += "                     PlanOperationnelClient.chargeConsommee";
    req += " FROM         PlanDeCharge INNER JOIN";
    req += "                     Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req += "                     ListeProjets ON Roadmap.idRoadmap = ListeProjets.idRoadmap LEFT OUTER JOIN";
    req += "                     PlanOperationnelClient ON Roadmap.idPO = PlanOperationnelClient.idWebPo";

    req += " WHERE     (PlanDeCharge.anneeRef = "+Annee+") AND (PlanDeCharge.projetMembre = "+idCollaborateur+") AND (PlanDeCharge.Charge > 0) AND (Roadmap.Etat <> 'MES') AND (Roadmap.standby <> 1)";
    req+=  " AND  (PlanOperationnelClient.Service = '"+this.nomServiceImputations+"' OR PlanOperationnelClient.Service IS NULL)";
    req+=  " ORDER BY PlanDeCharge.nomProjet ASC";

    req =" SELECT DISTINCT ";
    req += "                     ListeProjets.nomProjet, ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.dateMes, ";
    req += "                    ListeProjets.dateT0, ListeProjets.dateTest, PlanDeCharge.idRoadmap, ListeProjets.dateT0MOE, ListeProjets.dateSpecMOE, ListeProjets.dateDevMOE, ";
    req += "                    ListeProjets.dateTestMOE, ListeProjets.dateLivrMOE, ListeProjets.nomSt, ListeProjets.isRecurrent, Roadmap.idPO";
    req += "  FROM         PlanDeCharge INNER JOIN";
    req += "                    Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req += "                    ListeProjets ON Roadmap.idRoadmap = ListeProjets.idRoadmap";
    req += "  WHERE     (PlanDeCharge.anneeRef = "+Annee+") AND (PlanDeCharge.projetMembre = "+idCollaborateur+") AND (PlanDeCharge.Charge > 0) AND (Roadmap.Etat <> 'MES') AND (Roadmap.standby <> 1)";
    req += "  ORDER BY ListeProjets.nomProjet";

    req =" SELECT DISTINCT ";
    req += "                     ListeProjets.nomProjet, ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.dateMes, ";
    req += "                    ListeProjets.dateT0, ListeProjets.dateTest, PlanDeCharge.idRoadmap, ListeProjets.dateT0MOE, ListeProjets.dateSpecMOE, ListeProjets.dateDevMOE, ";
    req += "                    ListeProjets.dateTestMOE, ListeProjets.dateLivrMOE, ListeProjets.nomSt, ListeProjets.isRecurrent, Roadmap.idPO";
    req += "  FROM         PlanDeCharge INNER JOIN";
    req += "                    Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req += "                    ListeProjets ON Roadmap.idRoadmap = ListeProjets.idRoadmap";
    req += "  WHERE      (PlanDeCharge.projetMembre = "+idCollaborateur+")  AND (Roadmap.Etat <> 'MES') AND (Roadmap.standby <> 1)";
    req += "  and";
    req += "  (";
    req += "  (";
    req += "   (year (Roadmap.dateT0) = "+Annee+" or year (Roadmap.dateMep) = "+Annee+") and ListeProjets.isRecurrent = 0";
    req += "  )";
    req += "  or";
    req += "  (";
    req += "  ListeProjets.isRecurrent = 1";
    req += "  )";
    req += "  )      ";
    req += "  ORDER BY ListeProjets.isRecurrent desc, ListeProjets.nomProjet";
    
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
    while (rs.next()) {

      dateEB = "";
      datePROD = "";
      dateMES = "";
      idVersionSt = rs.getInt("idVersionSt");
      version = rs.getString("version");
      DdateEB = rs.getDate("dateEB");
      dateEB = Utils.getDateFrench(DdateEB);
      DdateProd = rs.getDate("dateMep");
      datePROD = Utils.getDateFrench(DdateProd);
      theDate = rs.getDate("dateMes");
      dateMES = Utils.getDateFrench(theDate);
      DdateT0 = rs.getDate("dateT0");
      dateT0 = Utils.getDateFrench(DdateT0);
      DdateTest = rs.getDate("dateTest");
      dateTEST = Utils.getDateFrench(DdateTest);
      idRoadmap= rs.getInt("idRoadmap");

      theDate = rs.getDate("dateT0MOE");
      String dateT0MOE = Utils.getDateFrench(theDate);
      theDate = rs.getDate("dateSpecMOE");
      String dateSpecMOE = Utils.getDateFrench(theDate);
      theDate = rs.getDate("dateDevMOE");
      String dateDevMOE = Utils.getDateFrench(theDate);
      theDate = rs.getDate("dateTestMOE");
      String dateTestMOE = Utils.getDateFrench(theDate);
      theDate = rs.getDate("dateLivrMOE");
      String dateLivrMOE = Utils.getDateFrench(theDate);

      nom = rs.getString("nomSt");
      int isRecurrent = rs.getInt("isRecurrent");
      String idPO = rs.getString("idPO");

      req = "SELECT     SUM(Charge) AS totalCharge, anneeRef";
      req += " FROM         PlanDeCharge";
      req += " WHERE     (idRoadmap = '" + idRoadmap + "') AND (projetMembre ="+idCollaborateur+")";
      req += " GROUP BY anneeRef";
      //req += " HAVING      (anneeRef = " + Annee + ")";

      Roadmap theRoadmap =null;
      rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);

      try {
        while (rs2.next()) {
          float totalCharge = rs2.getFloat("totalCharge");
          if (totalCharge > 0) {

            theRoadmap = new Roadmap(idRoadmap);
            theRoadmap.nomSt=nom;
            theRoadmap.dateT0=dateT0;
            theRoadmap.dateEB=dateEB;
            theRoadmap.dateTest=dateTEST;
            theRoadmap.dateProd=datePROD;
            theRoadmap.dateMes=dateMES;
            
            theRoadmap.DdateT0=DdateT0;
            theRoadmap.DdateEB=DdateEB;
            theRoadmap.DdateTest=DdateTest;
            theRoadmap.DdateProd=DdateProd;            

            theRoadmap.dateT0MOE=dateT0MOE;
            theRoadmap.dateSpecMOE=dateSpecMOE;
            theRoadmap.dateDevMOE=dateDevMOE;
            theRoadmap.dateTestMOE=dateTestMOE;
            theRoadmap.dateLivrMOE=dateLivrMOE;

            theRoadmap.idVersionSt=""+idVersionSt;
            theRoadmap.version=version;
            theRoadmap.idPO = idPO;
            theRoadmap.idMembre = idCollaborateur;
            if (theRoadmap.idPO == null || theRoadmap.idPO.equals("-1")|| theRoadmap.idPO.equals("0")) theRoadmap.idPO = "";
/*
            int i_charge = rs.getInt("Charge");
            if (i_charge <= 0) theRoadmap.Charge = "";
            else
              theRoadmap.Charge = ""+i_charge;


            int i_chargeConsommee = rs.getInt("chargeConsommee");
            if (i_chargeConsommee == 0) theRoadmap.chargeConsommee = "";
            else
              theRoadmap.chargeConsommee = ""+i_chargeConsommee;
*/
            if ((isRecurrent == 0)) theRoadmap.isProjet = true; else theRoadmap.isProjet = false;

          }
        }

      }
      catch (SQLException s) {}

if ( (theRoadmap != null) && !theRoadmap.idPO.equals(""))
      {

      // ------------ extraction du pr�vu et du consomm� ------------------------------------
      req = "SELECT     PlanOperationnelClient.charge, PlanOperationnelClient.chargeConsommee";
      req+="    FROM         Service INNER JOIN";
       req+="                 Membre ON Service.idService = Membre.serviceMembre INNER JOIN";
       req+="                 PlanOperationnelClient ON Service.nomServiceImputations = PlanOperationnelClient.Service";
        req+=" WHERE     (Membre.idMembre = "+this.id+") AND (PlanOperationnelClient.idWebPo = "+theRoadmap.idPO+") AND (PlanOperationnelClient.Annee = "+Annee+")";

      // -------------------------------------------------------------------------------------

      rs2 = myCnx.ExecReq(st2, myCnx.nomBase, req);

      try {
        while (rs2.next()) {
          int i_charge = rs2.getInt("charge");
          if (i_charge <= 0) theRoadmap.Charge = "";
          else
              theRoadmap.Charge = ""+i_charge;
            int i_chargeConsommee = rs2.getInt("chargeConsommee");
            if (i_chargeConsommee == 0) theRoadmap.chargeConsommee = "";
            else
              theRoadmap.chargeConsommee = ""+i_chargeConsommee;

        }
      }
      catch (SQLException s) {}
      this.ListeProjets.addElement(theRoadmap);
    }
    if ( (theRoadmap != null) && theRoadmap.idPO.equals(""))
      {
        this.ListeProjets.addElement(theRoadmap);
      }


    }

  }
  catch (SQLException s) {}

}


public void getListeCompetencesByType(String nomBase,Connexion myCnx, Statement st, String Type){
ResultSet rs;

  req = "SELECT * FROM ";
  req+="    (";
  req+=" SELECT     idMiddleware, Logiciel.nomLogiciel,Middleware.nomMiddleware, CollaborateurCompetence.note";
  req+=" FROM         CollaborateurCompetence INNER JOIN";
  req+="                     Middleware ON CollaborateurCompetence.idCompetence = Middleware.idMiddleware INNER JOIN";
  req+="                     Logiciel ON Middleware.LogicielMiddleWare = Logiciel.idLogiciel";
  req+=" WHERE     (CollaborateurCompetence.idCollaborateur = "+this.id;
  req+=" AND (Logiciel.nomLogiciel = '"+Type+"')";
  req+=" )";

  req+=" union";

  req+=" SELECT     idMiddleware, Logiciel.nomLogiciel,Middleware.nomMiddleware, - 1 AS note";
  req+=" FROM         Middleware INNER JOIN";
  req+="                     Logiciel ON Middleware.LogicielMiddleWare = Logiciel.idLogiciel";
  req+=" where idMiddleware not in";
  req+=" (";
  req+=" SELECT     idMiddleware";
  req+=" FROM         CollaborateurCompetence INNER JOIN";
  req+="                     Middleware ON CollaborateurCompetence.idCompetence = Middleware.idMiddleware INNER JOIN";
  req+="                     Logiciel ON Middleware.LogicielMiddleWare = Logiciel.idLogiciel";
  req+=" WHERE     (CollaborateurCompetence.idCollaborateur = "+this.id+")";
  req+=" )";
  req+=" AND (Logiciel.nomLogiciel = '"+Type+"')";
  req+=" )";
  req+=" as mytable  ORDER BY nomLogiciel, nomMiddleware";



rs = myCnx.ExecReq(st, myCnx.nomBase, req);

try {
while(rs.next())
{
   Logiciel theLogiciel = new Logiciel();
   theLogiciel.id = rs.getInt("idMiddleware");
   theLogiciel.typeLogiciel = rs.getString("nomLogiciel");
   theLogiciel.nom = rs.getString("nomMiddleware");

   Competence theCompetence = new Competence();
   theCompetence.theCollaborateur = this;
   theCompetence.theLogiciel = theLogiciel;
   theCompetence.note=rs.getInt("note");

   this.ListeCompetences.addElement(theCompetence);
 }

}
 catch (SQLException s) { }

  }

public void getListeCompetences(String nomBase,Connexion myCnx, Statement st){
ResultSet rs;

  req = "SELECT * FROM ";
  req+="    (";
  req+=" SELECT     idMiddleware, Logiciel.nomLogiciel,Middleware.nomMiddleware, CollaborateurCompetence.note";
  req+=" FROM         CollaborateurCompetence INNER JOIN";
  req+="                     Middleware ON CollaborateurCompetence.idCompetence = Middleware.idMiddleware INNER JOIN";
  req+="                     Logiciel ON Middleware.LogicielMiddleWare = Logiciel.idLogiciel";
  req+=" WHERE     (CollaborateurCompetence.idCollaborateur = "+this.id+")";

  req+=" union";

  req+=" SELECT     idMiddleware, Logiciel.nomLogiciel,Middleware.nomMiddleware, - 1 AS note";
  req+=" FROM         Middleware INNER JOIN";
  req+="                     Logiciel ON Middleware.LogicielMiddleWare = Logiciel.idLogiciel";
  req+=" where idMiddleware not in";
  req+=" (";
  req+=" SELECT     idMiddleware";
  req+=" FROM         CollaborateurCompetence INNER JOIN";
  req+="                     Middleware ON CollaborateurCompetence.idCompetence = Middleware.idMiddleware INNER JOIN";
  req+="                     Logiciel ON Middleware.LogicielMiddleWare = Logiciel.idLogiciel";
  req+=" WHERE     (CollaborateurCompetence.idCollaborateur = "+this.id+")";
  req+=" )";
  req+=" )";
  req+=" as mytable  ORDER BY nomLogiciel, nomMiddleware";



rs = myCnx.ExecReq(st, myCnx.nomBase, req);

try {
while(rs.next())
{
   Logiciel theLogiciel = new Logiciel();
   theLogiciel.id = rs.getInt("idMiddleware");
   theLogiciel.typeLogiciel = rs.getString("nomLogiciel");
   theLogiciel.nom = rs.getString("nomMiddleware");

   Competence theCompetence = new Competence();
   theCompetence.theCollaborateur = this;
   theCompetence.theLogiciel = theLogiciel;
   theCompetence.note=rs.getInt("note");

   this.ListeCompetences.addElement(theCompetence);
 }

}
 catch (SQLException s) { }

  }

public void getListeCompetences2(String nomBase,Connexion myCnx, Statement st){
ResultSet rs;

  req = "SELECT     Middleware.idMiddleware, Logiciel.nomLogiciel, Middleware.nomMiddleware, CollaborateurCompetence.note, ";
  req+="                       CollaborateurCompetence.idCollaborateur";
  req+=" FROM         Middleware INNER JOIN";
  req+="                       Logiciel ON Middleware.LogicielMiddleWare = Logiciel.idLogiciel LEFT OUTER JOIN";
  req+="                       CollaborateurCompetence ON Middleware.idMiddleware = CollaborateurCompetence.idCompetence";
  req+=" WHERE     (CollaborateurCompetence.idCollaborateur = "+this.id+")";


rs = myCnx.ExecReq(st, myCnx.nomBase, req);

try {
while(rs.next())
{
   Logiciel theLogiciel = new Logiciel();
   theLogiciel.id = rs.getInt("idMiddleware");
   theLogiciel.typeLogiciel = rs.getString("nomLogiciel");
   theLogiciel.nom = rs.getString("nomMiddleware");

   Competence theCompetence = new Competence();
   theCompetence.theCollaborateur = this;
   theCompetence.theLogiciel = theLogiciel;
   theCompetence.note=rs.getInt("note");

   this.ListeCompetences.addElement(theCompetence);
 }

}
 catch (SQLException s) { }

  }

  public void getListeSt(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs;

    req= "Select distinct * from";
    req+= "(";
    req+= "	SELECT nomSt, idVersionSt FROM   ListeST WHERE respMoaVersionSt ="+this.id+" AND (isRecurrent = 0) AND (isActeur <> 1)";
    req+= "		UNION";
    req+= "		SELECT nomSt, idVersionSt FROM   ListeST WHERE respMoeVersionSt ="+this.id+" AND (isRecurrent = 0) AND (isActeur <> 1)";

    req+= "		UNION";
    req+= "		SELECT     ListeST.nomSt, idVersionSt";
    req+= "		FROM         Membre INNER JOIN";
    req+= "	                         EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
    req+= "	                         Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
    req+= "	                         ListeST ON Equipe.nom = ListeST.nomSt";
    req+= "	    WHERE     (Membre.idMembre = "+this.id+")   AND (isRecurrent = 0) AND (ListeST.isActeur <> 1)";
    req+= "	) as mytable order by nomSt";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     ST theST = new ST(rs.getString("nomSt"));
     theST.idVersionSt  = rs.getInt("idVersionSt");
     this.ListeST.addElement(theST);
   }

  }
   catch (SQLException s) { }

  }
  
  public void getListeSt(String nomBase,Connexion myCnx, Statement st, String MOA ){
  ResultSet rs;
  String nomSt ="";
  String dateT0="";
  String dateEB="";
  String dateTEST="";
  String datePROD="";
  String dateMES="";
  int idVersionSt = -1;
  int idRoadmap = -1;
  String version= "";
  Date theDate = null;

if (MOA.equals("yes"))
  {
    req = "SELECT nomSt, idVersionSt FROM   ListeST WHERE respMoaVersionSt =" + this.id;
  }
  else
  {
        req = "SELECT nomSt, idVersionSt FROM   ListeST WHERE respMoeVersionSt =" + this.id;
  }
  req += " UNION";
  req += " SELECT     ListeST.nomSt, idVersionSt";
  req += " FROM         Membre INNER JOIN";
  req += "                       EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
  req += "                       Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
   req += "                      ListeST ON Equipe.nom = ListeST.nomSt";
   req += " WHERE     (Membre.idMembre = "+this.id+")  ";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     nomSt  = rs.getString("nomSt");

     ST theST = new ST(nomSt);
     theST.idVersionSt  = rs.getInt("idVersionSt");
     this.ListeST.addElement(theST);
   }

  }
   catch (SQLException s) { }

  }

  public void getListeStMOEorMOA(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs;
  String nomSt ="";

  req = "SELECT     VersionSt.idVersionSt, St.nomSt";
  req+=" FROM         VersionSt INNER JOIN";
  req+="                      St ON VersionSt.stVersionSt = St.idSt";
  req+=" WHERE     (VersionSt.respMoeVersionSt = "+this.id+") OR";
  req+="                      (VersionSt.respMoaVersionSt = "+this.id+")";
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     ST theST = new ST(rs.getInt("idVersionSt"), "idVersionSt");
     theST.nomSt  = rs.getString("nomSt");
     this.ListeST.addElement(theST);
   }

  }
   catch (SQLException s) { }

  }
  
  public void getListeStMOE(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs;
  String nomSt ="";

  req = "SELECT nomSt, idVersionSt FROM   ListeST WHERE respMoeVersionSt =" + this.id;
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     nomSt  = rs.getString("nomSt");

     ST theST = new ST(nomSt);
     theST.idVersionSt  = rs.getInt("idVersionSt");
     this.ListeST.addElement(theST);
   }

  }
   catch (SQLException s) { }

  }
  
  public void getListeStMOA(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs;
  String nomSt ="";

  req = "SELECT nomSt, idVersionSt FROM   ListeST WHERE respMoaVersionSt =" + this.id;
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     nomSt  = rs.getString("nomSt");

     ST theST = new ST(nomSt);
     theST.idVersionSt  = rs.getInt("idVersionSt");
     this.ListeST.addElement(theST);
   }

  }
   catch (SQLException s) { }

  }  
  
  public void getListeRoadmap(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs;
    int nbCnx=0;
    int totalCharge= 0;
    int totalChargeEngagee= 0;
    String dateT0="";
    String dateEB="";
    String dateTEST="";
    String datePROD="";
    String dateMES="";

    String dateT0MOE="";
    String dateSpecMOE = "";
    String dateDevMOE = "";
    String dateTestMOE = "";
    String dateLivrMOE = "";

    for (int i=0; i < this.ListeST.size();i++)
    {
      ST theST = (ST)this.ListeST.elementAt(i);
      String req="SELECT     St.nomSt, Roadmap.version, Roadmap.dateT0, Roadmap.dateEB, Roadmap.dateTest, Roadmap.dateMep, Roadmap.dateMes, Roadmap.Etat, Roadmap.Tendance, Roadmap.idRoadmap, dateT0MOE, dateSpecMOE, dateDevMOE, dateTestMOE, dateLivrMOE";
      req+=" FROM         Roadmap INNER JOIN";
      req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
      req+="                      St ON VersionSt.stVersionSt = St.idSt";
      req+=" WHERE     (St.nomSt = '"+theST.nomSt+"')" + " AND  (Roadmap.Etat <> 'MES') AND(LF_Month = - 1) AND (LF_Year = - 1)";
      rs = myCnx.ExecReq(st, myCnx.nomBase, req);
      String nom = "";
      String acteur = "";
      int etat = -1;
      int id;
      int idRoadmap;
      Date theDate;
      String str_theDate;
      try {
        while (rs.next()) {
          String nomSt = rs.getString("nomSt"); // nom du ST
          String version = rs.getString("version"); // nom du ST
          theDate = rs.getDate("dateT0");
          dateT0 = Utils.getDateFrench(theDate);
          theDate = rs.getDate("dateEB");
          dateEB = Utils.getDateFrench(theDate);
          theDate = rs.getDate("dateTest");
          dateTEST = Utils.getDateFrench(theDate);
          theDate = rs.getDate("dateMep");
          datePROD = Utils.getDateFrench(theDate);
          theDate = rs.getDate("dateMes");
          dateMES = Utils.getDateFrench(theDate);
          String Tendance = rs.getString("Tendance");
          idRoadmap = rs.getInt("idRoadmap");

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

          Roadmap theLigneRoadmap = new Roadmap(
              nomSt,
              "",
              "",
              version,
              "",
              "",
              "",
              "-1",
              "",
              "",
              dateEB,
              datePROD,
              dateMES,
              "",
              Tendance,
              dateT0,
              dateTEST,
              "",
              "",
              -1,
              ""
              );
          theLigneRoadmap.id = idRoadmap;
          theLigneRoadmap.dateT0MOE = dateT0MOE;
          theLigneRoadmap.dateSpecMOE = dateSpecMOE;
          theLigneRoadmap.dateDevMOE = dateDevMOE;
          theLigneRoadmap.dateTestMOE = dateTestMOE;
          theLigneRoadmap.dateLivrMOE = dateLivrMOE;

          //theLigneRoadmap.dump();
          theST.ListeRoadmap.addElement(theLigneRoadmap);
        }
      }
      catch (SQLException s) {
        s.getMessage();
      }
    }
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

  public int getListeProjetsNonReccurrent(String nomBase,Connexion myCnx, Statement st,  int anneeRef,  String str_ListePeriode, String Client, String PeriodeDebut, String PeriodeFin, String MotsClef, String idVersionSt, String idMembre,  String ST_Lies, int monthRef, String Projets, int page, int nbProjets ){
   ResultSet rs;

   String ClauseMembre = "";
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

   String ClausePeriode = getClauseWhere(str_ListePeriode);
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

    //String ClausePhase=getClauseWhere( AD, T0,  EB, UAT, PROD, MES);
    String ClausePhase="";
  
  req = "select * from ( ";
  req+= "  select ListeProjets.nomSt,idRoadmap, nomProjet,idVersionSt,version,dateEB,dateMep,dateMes,dateT0,dateTest,dateT0MOE, nbDependances,  nbActions, dateT0_init, dateEB_init, dateTest_init, dateMep_init, dateMes_init, nomProjet as nomProjetPere, 0 as fils, standby, Panier, idRoadmapPere, etat, MotsClef, dateMep as dateMepPere, ListeProjets.ordreJalon, ListeProjets.idJalon  from ListeProjets ";
  req+= " where idRoadmapPere = -1";
  req+= " union";
  req+= " SELECT     ListeProjets.nomSt,ListeProjets.idRoadmap, ListeProjets.nomProjet, ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.dateMes, ListeProjets.dateT0, ";
  req+= "                       ListeProjets.dateTest, ListeProjets.dateT0MOE, ListeProjets.nbDependances, ListeProjets.nbActions, ListeProjets.dateT0_init, ListeProjets.dateEB_init, ListeProjets.dateTest_init, ";
  req+= "                       ListeProjets.dateMep_init, ListeProjets.dateMes_init, ListeProjets_1.nomProjet AS nomProjetPere, 1 as fils, ListeProjets.standby, ListeProjets.Panier, ListeProjets.idRoadmapPere, ListeProjets.etat, ListeProjets.MotsClef,  ListeProjets_1.dateMep as dateMepPere, ListeProjets.ordreJalon, ListeProjets.idJalon";
  req+= " FROM         ListeProjets INNER JOIN";
  req+= "                       ListeProjets AS ListeProjets_1 ON ListeProjets.idRoadmapPere = ListeProjets_1.idRoadmap";
  req+= " WHERE    (ListeProjets.idRoadmapPere > - 1)";

  req+= " )";
  req+= " as myTableGlobale";
 
  req+= " where idVersionSt in";
  req+="    (";
  req+=" SELECT distinct idVersionSt FROM (";
  req+=" SELECT VersionSt.idVersionSt, St.nomSt FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoaVersionSt = Membre.idMembre WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.isActeur <> 1) AND (Membre.LoginMembre = '"+this.Login+"') AND (St.creerProjet = 1) AND (ST.creerProjet = 1) AND (ST.isRecurrent <> 1) AND     (ST.isMeeting <> 1) ";

  req+=" UNION ";
  req+=" SELECT VersionSt.idVersionSt, St.nomSt FROM St INNER JOIN  VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN Membre ON VersionSt.respMoeVersionSt = Membre.idMembre WHERE (VersionSt.etatFicheVersionSt = 3) AND (VersionSt.etatVersionSt <> 4) AND (St.isActeur <> 1) AND (Membre.LoginMembre = '"+this.Login+"') AND (St.creerProjet = 1) AND (ST.creerProjet = 1) AND (ST.isRecurrent <> 1) AND     (ST.isMeeting <> 1) ";

  req+=" UNION ";
  req+=" SELECT ListeST.idVersionSt, ListeST.nomSt FROM Membre INNER JOIN EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN ListeST ON Equipe.nom = ListeST.nomSt WHERE (Membre.LoginMembre = '"+this.Login+"') AND (ListeST.isActeur <> 1) AND (ListeST.creerProjet = 1) AND (ListeST.isRecurrent <> 1) AND     (ListeST.isMeeting <> 1)";
  req+=" ) ";
  req+=" AS maTable ";
  req+=" )";
  
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
  int count = 0;
  int num = 0;

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
       this.ListeProjets.addElement(theRoadmap);
        
   }
   num++;
  }
  }
   catch (SQLException s) { }

    return count;
}  
 
 public int getListeProjets_sansCharges(String nomBase,Connexion myCnx, Statement st){

   Calendar calendar = Calendar.getInstance();
   int currentYear = calendar.get(Calendar.YEAR);

   String req = "";
   req+="SELECT * FROM (";
req+=" SELECT     Roadmap.idRoadmap, ListeST.nomSt + '-' + Roadmap.version as nomProjet,LoginMembre, isProjet, isActeur, creerProjet , Etat, version, dateT0, isRecurrent";
req+=" FROM         Membre INNER JOIN";
req+="                      EquipeMembre ON Membre.idMembre = EquipeMembre.idMembre INNER JOIN";
req+="                      Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
req+="                      ListeST ON Equipe.nom = ListeST.nomSt INNER JOIN";
req+="                      Roadmap ON ListeST.idVersionSt = Roadmap.idVersionSt";

req+=" UNION";

req+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version as nomProjet,LoginMembre, isProjet , isActeur, creerProjet, Etat, version, Roadmap.dateT0, isRecurrent";
req+=" FROM         St INNER JOIN";
req+="                      VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
req+="                      Membre ON VersionSt.respMoeVersionSt = Membre.idMembre INNER JOIN";
req+="                      Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt";

req+=" UNION";

req+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version as nomProjet,LoginMembre, isProjet , isActeur, creerProjet, Etat, version, Roadmap.dateT0, isRecurrent";
req+=" FROM         St INNER JOIN";
req+="                      VersionSt ON St.idSt = VersionSt.stVersionSt INNER JOIN";
req+="                      Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
req+="                      Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt";

req+="     )";
req+="     AS maTable";
req+=" WHERE     (LoginMembre = '"+this.Login+"')  AND YEAR (dateT0) = YEAR(GETDATE())  AND (isProjet = 1) AND (isActeur = 0)   AND (creerProjet = 1) AND (Etat <> 'MES') AND (Etat <> 'a demarrer') AND (version <> '-- NON REGRESSION')    AND  (isRecurrent = 0)";
req+=" AND idRoadmap  not in (select PlanDeCharge.idRoadmap from PlanDeCharge where PlanDeCharge.idRoadmap is not null) ";


   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   int nb = 0;

  try {
     while (rs.next()) {
      int idRoadmap = rs.getInt("idRoadmap");
      String projet = rs.getString("nomProjet");
      Roadmap theRoadmap = new Roadmap(idRoadmap);
      theRoadmap.nomProjet = projet;
      this.ListeProjets.addElement(theRoadmap);
      nb++;
     }
      } catch (SQLException s) {s.getMessage();}


  return nb;
}  
public int getListeProjets_sansIdPO2(String nomBase,Connexion myCnx, Statement st){

  Calendar calendar = Calendar.getInstance();
  int currentYear = calendar.get(Calendar.YEAR);

  String req = "";
    req+=" select distinct * from";
    req+=" (";    
    req+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                       Membre ON VersionSt.respMoaVersionSt = Membre.idMembre";
    req+=" WHERE     (Membre.LoginMembre = '"+this.Login+"') AND (Roadmap.Etat <> 'MES') AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    req+="                       (Roadmap.LF_Year = - 1) AND (St.isRecurrent <> 1) AND (Roadmap.standby = 0) ";
    req+=" 					  AND (YEAR(Roadmap.dateMep) >= YEAR(GETDATE())) AND  (YEAR(Roadmap.dateT0) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateEB) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateTest) >= YEAR(GETDATE()))";
    req+=" 					  AND (Roadmap.standby <> 1)     ";
    req+="                       AND (Roadmap.idPO <= 0 OR Roadmap.idPO IS NULL) ";  
    req+=" UNION";
    req+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                       Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
    req+=" WHERE     (Membre.LoginMembre = '"+this.Login+"') AND (Roadmap.Etat <> 'MES') AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    req+="                       (Roadmap.LF_Year = - 1) AND (St.isRecurrent <> 1) AND (Roadmap.standby = 0)";
    req+=" 					  AND (YEAR(Roadmap.dateMep) >= YEAR(GETDATE())) AND  (YEAR(Roadmap.dateT0) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateEB) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateTest) >= YEAR(GETDATE()))";
    req+=" 					  AND (Roadmap.standby <> 1)                       ";
    req+="                       AND (Roadmap.idPO <= 0 OR Roadmap.idPO IS NULL) ";
    req+=" UNION";
    req+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                       Equipe ON St.nomSt = Equipe.nom INNER JOIN";
    req+="                       EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    req+="                       Membre ON EquipeMembre.idMembre = Membre.idMembre";
    req+=" WHERE    ";
    req+="                       (Roadmap.Etat <> 'MES') AND (Membre.LoginMembre = '"+this.Login+"') AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    req+="                      (Roadmap.LF_Year = - 1) AND (Roadmap.standby = 0) ";
    req+=" 					  AND (YEAR(Roadmap.dateMep) >= YEAR(GETDATE())) AND  (YEAR(Roadmap.dateT0) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateEB) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateTest) >= YEAR(GETDATE()))";
    req+=" 					  AND (Roadmap.standby <> 1)                                       ";
    req+="                       AND (Roadmap.idPO <= 0 OR Roadmap.idPO IS NULL)";
    req+=" )";      
    req+=" as mytable ";
  
    req+=" order by     nomProjet";


  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  int nb = 0;

 try {
    while (rs.next()) {
      int idRoadmap = rs.getInt("idRoadmap");
      String projet = rs.getString("nomProjet");
      Roadmap theRoadmap = new Roadmap(idRoadmap);
      theRoadmap.nomProjet = projet;
      this.ListeProjets.addElement(theRoadmap);
      nb++;
    }
     } catch (SQLException s) {s.getMessage();}


 return nb;
}  

public int getListeProjets_sansIdPO(String nomBase,Connexion myCnx, Statement st){

  Calendar calendar = Calendar.getInstance();
  int currentYear = calendar.get(Calendar.YEAR);

  String req = "";
    req+=" select distinct * from";
    req+=" (";    
    req+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                       Membre ON VersionSt.respMoaVersionSt = Membre.idMembre";
    req+=" WHERE     (Membre.LoginMembre = '"+this.Login+"') AND (Roadmap.Etat <> 'MES') AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    req+="                       (Roadmap.LF_Year = - 1) AND (St.isRecurrent <> 1) AND (Roadmap.standby = 0) ";
    req+=" 					  AND (YEAR(Roadmap.dateMep) >= YEAR(GETDATE())) AND  (YEAR(Roadmap.dateT0) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateEB) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateTest) >= YEAR(GETDATE()))";
    req+=" 					  AND (Roadmap.standby <> 1)     ";
    req+="                       AND (Roadmap.idPO <= 0 OR Roadmap.idPO IS NULL) ";  
    req+=" UNION";
    req+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                       Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
    req+=" WHERE     (Membre.LoginMembre = '"+this.Login+"') AND (Roadmap.Etat <> 'MES') AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    req+="                       (Roadmap.LF_Year = - 1) AND (St.isRecurrent <> 1) AND (Roadmap.standby = 0)";
    req+=" 					  AND (YEAR(Roadmap.dateMep) >= YEAR(GETDATE())) AND  (YEAR(Roadmap.dateT0) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateEB) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateTest) >= YEAR(GETDATE()))";
    req+=" 					  AND (Roadmap.standby <> 1)                       ";
    req+="                       AND (Roadmap.idPO <= 0 OR Roadmap.idPO IS NULL) ";
    req+=" UNION";
    req+=" SELECT     Roadmap.idRoadmap, St.nomSt + '-' + Roadmap.version AS  nomProjet";
    req+=" FROM         Roadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                       Equipe ON St.nomSt = Equipe.nom INNER JOIN";
    req+="                       EquipeMembre ON Equipe.id = EquipeMembre.idMembreEquipe INNER JOIN";
    req+="                       Membre ON EquipeMembre.idMembre = Membre.idMembre";
    req+=" WHERE    ";
    req+="                       (Roadmap.Etat <> 'MES') AND (Membre.LoginMembre = '"+this.Login+"') AND (St.isMeeting <> 1) AND (Roadmap.LF_Month = - 1) AND ";
    req+="                      (Roadmap.LF_Year = - 1) AND (Roadmap.standby = 0) ";
    req+=" 					  AND (YEAR(Roadmap.dateMep) >= YEAR(GETDATE())) AND  (YEAR(Roadmap.dateT0) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateEB) >= YEAR(GETDATE())) AND (YEAR(Roadmap.dateTest) >= YEAR(GETDATE()))";
    req+=" 					  AND (Roadmap.standby <> 1)                                       ";
    req+="                       AND (Roadmap.idPO <= 0 OR Roadmap.idPO IS NULL)";
    
    req+=" UNION";    
    
    req+=" SELECT     dbo.Surconsommation.idRoadmap, dbo.St.nomSt + '-' + dbo.Surconsommation.version AS nomProjet";    
    req+=" FROM         dbo.Surconsommation INNER JOIN";    
    req+="                       dbo.Roadmap ON dbo.Surconsommation.idRoadmap = dbo.Roadmap.idRoadmap INNER JOIN";    
    req+="                       dbo.VersionSt ON dbo.Roadmap.idVersionSt = dbo.VersionSt.idVersionSt INNER JOIN";    
    req+="                       dbo.Membre ON dbo.VersionSt.respMoeVersionSt = dbo.Membre.idMembre INNER JOIN";    
    req+="                       dbo.St ON dbo.VersionSt.stVersionSt = dbo.St.idSt";    
    req+=" WHERE     (dbo.Membre.LoginMembre = '"+this.Login+"')";    

    req+=" UNION";    

    req+=" SELECT     dbo.Surconsommation.idRoadmap, dbo.St.nomSt + '-' + dbo.Surconsommation.version AS nomProjet";    
    req+=" FROM         dbo.Surconsommation INNER JOIN";    
    req+="                       dbo.Roadmap ON dbo.Surconsommation.idRoadmap = dbo.Roadmap.idRoadmap INNER JOIN";    
    req+="                       dbo.VersionSt ON dbo.Roadmap.idVersionSt = dbo.VersionSt.idVersionSt INNER JOIN";    
    req+="                       dbo.Membre ON dbo.VersionSt.respMoaVersionSt = dbo.Membre.idMembre INNER JOIN";    
    req+="                       dbo.St ON dbo.VersionSt.stVersionSt = dbo.St.idSt";    
    req+=" WHERE     (dbo.Membre.LoginMembre = '"+this.Login+"')";    

    req+=" UNION";    

    req+=" SELECT     dbo.Roadmap.idRoadmap, dbo.St.nomSt + '-' + dbo.Roadmap.version AS nomProjet";    
    req+=" FROM         dbo.Roadmap INNER JOIN";    
    req+="                       dbo.VersionSt ON dbo.Roadmap.idVersionSt = dbo.VersionSt.idVersionSt INNER JOIN";    
    req+="                       dbo.St ON dbo.VersionSt.stVersionSt = dbo.St.idSt INNER JOIN";    
    req+="                       dbo.Equipe ON dbo.St.nomSt = dbo.Equipe.nom INNER JOIN";    
    req+="                       dbo.EquipeMembre ON dbo.Equipe.id = dbo.EquipeMembre.idMembreEquipe INNER JOIN";    
    req+="                       dbo.Membre ON dbo.EquipeMembre.idMembre = dbo.Membre.idMembre INNER JOIN";    
    req+="                       dbo.Surconsommation ON dbo.Roadmap.idRoadmap = dbo.Surconsommation.idRoadmap";    
    req+=" WHERE     (dbo.Roadmap.Etat <> 'MES') AND (dbo.Membre.LoginMembre = '"+this.Login+"') AND (dbo.St.isMeeting <> 1) AND (dbo.Roadmap.LF_Month = - 1) AND (dbo.Roadmap.LF_Year = - 1) AND ";    
    req+="                       (dbo.Roadmap.standby = 0) AND (dbo.Roadmap.standby <> 1)  ";      
    req+=" )";      
    req+=" as mytable ";
  
    req+=" order by     nomProjet";


  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  int nb = 0;

 try {
    while (rs.next()) {
      int idRoadmap = rs.getInt("idRoadmap");
      String projet = rs.getString("nomProjet");
      Roadmap theRoadmap = new Roadmap(idRoadmap);
      theRoadmap.nomProjet = projet;
      this.ListeProjets.addElement(theRoadmap);
      nb++;
    }
     } catch (SQLException s) {s.getMessage();}


 return nb;
} 

 public void getListeActionsAnneeCouranteNonCloses(String nomBase,Connexion myCnx, Statement st, int Annee, String etat_AF, String etat_PEC, String etat_ENCOURS, String etat_TRAITE, String etat_CLOS, String etat_STANDBY){
 ResultSet rs;
 
   String ClauseEtat = "";
   int numClause = 0;
   
   if (etat_AF.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (actionSuivi.idEtat = 1)";
    ClauseEtat +=" )   ";
    numClause++;
   } 
   if (etat_PEC.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (actionSuivi.idEtat = 5)";
    ClauseEtat +=" )   ";
    numClause++;
   } 
   if (etat_ENCOURS.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (actionSuivi.idEtat = 2)";
    ClauseEtat +=" )   ";
    numClause++;
   } 
   if (etat_TRAITE.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (actionSuivi.idEtat = 3)";
    ClauseEtat +=" )   ";
    numClause++;
   }    
   if (etat_CLOS.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (actionSuivi.idEtat = 6)";
    ClauseEtat +=" )   ";
    numClause++;
   }    
   if (etat_STANDBY.equals("1") )
   {
    if (numClause > 0) ClauseEtat +=" OR ";
    ClauseEtat +=" (";
    ClauseEtat +=" (actionSuivi.idEtat = 4)";
    ClauseEtat +=" )   ";
    numClause++;
   }    
   
   if (numClause > 0) ClauseEtat = " AND (" + ClauseEtat +")";
   if ((etat_AF.equals("0")) && (etat_PEC.equals("0")) && (etat_ENCOURS.equals("0")) && (etat_TRAITE.equals("0")) && (etat_CLOS.equals("0")) && (etat_STANDBY.equals("0")) )
    ClauseEtat = " AND (actionSuivi.idEtat = -1)";
   
   req="SELECT DISTINCT * FROM (";
   
   req += "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, ";
   req +="                      actionSuivi.dateFin, actionSuivi.reponse, actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.Code, actionSuivi.Code AS Expr1, ";
   req +="                      actionSuivi.ChargePrevue, actionSuivi.ChargeDevis, actionSuivi.Semaine, actionSuivi.RAF, actionSuivi.priorite, actionSuivi.dateCloture, ";
   req +="                      actionSuivi.idRoadmapLie, actionSuivi.LoginEmetteur, ListeProjets.nomProjet, ListeProjets.isRecurrent";
   req +="  FROM         actionSuivi INNER JOIN";
   req +="                      TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id  INNER JOIN";
   req +="                      ListeProjets ON actionSuivi.idRoadmap = ListeProjets.idRoadmap";   
   req +="  WHERE     (actionSuivi.type <> 'MOE') AND (YEAR(actionSuivi.dateFin) = "+Annee+" OR";
   req +="                      YEAR(actionSuivi.dateAction) = "+Annee+") AND (actionSuivi.acteur LIKE '%"+this.Login+"%') AND (ListeProjets.isRecurrent = 0)";
   req += ClauseEtat;
   //req +="                    AND (actionSuivi.idEtat <> 6) AND   (actionSuivi.idEtat <> 3)  AND (actionSuivi.idEtat <> 4)";
   
   req+= " UNION";
   
   req+=" SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, actionSuivi.dateFin, actionSuivi.reponse, ";
   req +="                      actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.Code, actionSuivi.Code AS Expr1, actionSuivi.ChargePrevue, actionSuivi.ChargeDevis, actionSuivi.Semaine, actionSuivi.RAF, actionSuivi.priorite, ";
   req +="                     actionSuivi.dateCloture, actionSuivi.idRoadmapLie, actionSuivi.LoginEmetteur, ListeProjets.nomProjet, ListeProjets.isRecurrent";
   req +=" FROM         actionSuivi INNER JOIN";
   req +="                      TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id INNER JOIN";
   req +="                      ListeProjets ON actionSuivi.idRoadmap = ListeProjets.idRoadmap";
   req +=" WHERE     (actionSuivi.acteur LIKE '%"+this.Login+"%') AND (ListeProjets.isRecurrent = 1)";

    req+= "    ) ";
    req+= " as mytable ";  
    req +=" ORDER BY dateAction";


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

        acteur = rs.getString("acteur").replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");
        etat = rs.getInt("idEtat");
        idRoadmap = rs.getInt("idRoadmap");

        Date Ddate_start = rs.getDate("dateAction");
        str_etat = rs.getString("nomEtat");
        if (Ddate_start != null)
          str_theDate = ""+Ddate_start.getDate()+"/"+(Ddate_start.getMonth()+1) + "/"+(Ddate_start.getYear() + 1900);
         else
          str_theDate = "";

        Date Ddate_end = rs.getDate("dateFin");
        if (Ddate_end != null)
          str_theDateFin = ""+Ddate_end.getDate()+"/"+(Ddate_end.getMonth()+1) + "/"+(Ddate_end.getYear() + 1900);
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
        
        theAction.Ddate_start = Ddate_start;
        theAction.Ddate_end = Ddate_end;
        
        theAction.projet = rs.getString("nomProjet");
        
        theAction.isRecurrent = rs.getInt("isRecurrent");

        this.ListeActions.addElement(theAction);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }
 public void getListeProjetsNonReccurrentAvecTaches(String nomBase,Connexion myCnx, Statement st, int Annee ){
 ResultSet rs;
 String nom="";
 String dateT0="";
 String dateEB="";
 String dateTEST="";
 String datePROD="";
 String dateMES="";
 int idVersionSt = -1;
 int idRoadmap = -1;
 String version= "";
 Date theDate = null;

 req = "SELECT DISTINCT ";
 req+="                      ListeProjets.idRoadmap, ListeProjets.nomProjet, ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ListeProjets.dateMep, ";
 req+="                      ListeProjets.dateMes, ListeProjets.dateT0, ListeProjets.dateTest, ListeProjets.dateT0MOE";
 req+=" FROM         ListeProjets INNER JOIN";
 req+="                      actionSuivi ON ListeProjets.idRoadmap = actionSuivi.idRoadmap";
 req+=" WHERE     (actionSuivi.acteur LIKE '%"+this.Login+"%')";
 req+=" and (YEAR(dateMep)="+Annee+" or YEAR(dateT0)="+Annee+")";
 req+=" and  LF_Month=-1";
 req+=" and  LF_Year=-1";
 req+=" order by nomProjet asc";

 rs = myCnx.ExecReq(st, myCnx.nomBase, req);

 try {
 while(rs.next())
 {
   Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap") );
    nom = rs.getString("nomProjet");
    dateEB="";
    datePROD="";
    dateMES="";
    theRoadmap.idVersionSt= rs.getString("idVersionSt");
    theRoadmap.version = rs.getString("version");
    theDate = rs.getDate("dateEB");
    theRoadmap.dateEB =  Utils.getDateFrench(theDate);
    theDate = rs.getDate("dateMep");
    theRoadmap.dateProd =  Utils.getDateFrench(theDate);
    theDate = rs.getDate("dateMes");
    theRoadmap.dateMes =  Utils.getDateFrench(theDate);
    theDate = rs.getDate("dateT0");
    theRoadmap.dateT0 =  Utils.getDateFrench(theDate);
    theDate = rs.getDate("dateTest");
    theRoadmap.dateTest =  Utils.getDateFrench(theDate);
    //idRoadmap= rs.getInt("idRoadmap");
    theDate = rs.getDate("dateT0MOE");
    theRoadmap.dateT0MOE =  Utils.getDateFrench(theDate);

    theRoadmap.dateDevMOE="";
    theRoadmap.dateSpecMOE="";
    theRoadmap.dateLivrMOE="";

    theRoadmap.nomSt = nom;
    /*
    if ((theRoadmap.dateT0 == null) || (theRoadmap.dateT0.equals(""))) continue;
    if ((theRoadmap.dateEB == null) || (theRoadmap.dateEB.equals(""))) continue;
    if ((theRoadmap.dateTest == null) || (theRoadmap.dateTest.equals(""))) continue;
       if ((theRoadmap.dateProd == null) || (theRoadmap.dateProd.equals(""))) continue;
    */
   this.ListeProjets.addElement(theRoadmap);
  }

 }
  catch (SQLException s) { }

}

public void getListeProjetsReccurrentAvecCharge(String nomBase,Connexion myCnx, Statement st, int Annee ){
ResultSet rs;
String nom="";
String dateT0="";
String dateEB="";
String dateTEST="";
String datePROD="";
String dateMES="";
int idVersionSt = -1;
int idRoadmap = -1;
String version= "";
Date theDate = null;

  req = "SELECT DISTINCT ";
  req+="                      ListeProjets.idRoadmap, PlanDeCharge.nomProjet, ListeProjets.Panier, ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ";
  req+="                       ListeProjets.dateMep, ListeProjets.dateMes, ListeProjets.dateT0, ListeProjets.dateTest, ListeProjets.LF_Month, ListeProjets.LF_Year";
  req+=" FROM         PlanDeCharge INNER JOIN";
  req+="                       ListeProjets ON PlanDeCharge.idRoadmap = ListeProjets.idRoadmap";
  req+=" WHERE     (PlanDeCharge.projetMembre = "+this.id+") AND (ListeProjets.LF_Month = - 1) AND (ListeProjets.LF_Year = - 1) AND (ListeProjets.isRecurrent = 1)";

rs = myCnx.ExecReq(st, myCnx.nomBase, req);

try {
while(rs.next())
{
  Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap") );
   nom = rs.getString("nomProjet");
   dateEB="";
   datePROD="";
   dateMES="";
   theRoadmap.idVersionSt= rs.getString("idVersionSt");
   theRoadmap.version = rs.getString("version");
   theDate = rs.getDate("dateEB");
   theRoadmap.dateEB =  Utils.getDateFrench(theDate);
   theDate = rs.getDate("dateMep");
   theRoadmap.dateProd =  Utils.getDateFrench(theDate);
   theDate = rs.getDate("dateMes");
   theRoadmap.dateMes =  Utils.getDateFrench(theDate);
   theDate = rs.getDate("dateT0");
   theRoadmap.dateT0 =  Utils.getDateFrench(theDate);
   theDate = rs.getDate("dateTest");
   theRoadmap.dateTest =  Utils.getDateFrench(theDate);
   //idRoadmap= rs.getInt("idRoadmap");
   theRoadmap.dateT0MOE="";
   theRoadmap.dateDevMOE="";
   theRoadmap.dateSpecMOE="";
   theRoadmap.dateLivrMOE="";

   theRoadmap.nomSt = nom;

   this.ListeProjets.addElement(theRoadmap);
 }

}
 catch (SQLException s) { }

}
 public void getListeProjetsNonReccurrentAvecCharge(String nomBase,Connexion myCnx, Statement st, int Annee ){
 ResultSet rs;
 String nom="";
 String dateT0="";
 String dateEB="";
 String dateTEST="";
 String datePROD="";
 String dateMES="";
 int idVersionSt = -1;
 int idRoadmap = -1;
 String version= "";
 Date theDate = null;

 req = "exec [PROJET_ListeByCollaborateurNonRecurrent] "+this.id+","+Annee;
 rs = myCnx.ExecReq(st, myCnx.nomBase, req);

 try {
 while(rs.next())
 {
   Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap") );
    nom = rs.getString("nomProjet");
    dateEB="";
    datePROD="";
    dateMES="";
    theRoadmap.idVersionSt= rs.getString("idVersionSt");
    theRoadmap.version = rs.getString("version");
    theDate = rs.getDate("dateEB");
    theRoadmap.dateEB =  Utils.getDateFrench(theDate);
    theDate = rs.getDate("dateMep");
    theRoadmap.dateProd =  Utils.getDateFrench(theDate);
    theDate = rs.getDate("dateMes");
    theRoadmap.dateMes =  Utils.getDateFrench(theDate);
    theDate = rs.getDate("dateT0");
    theRoadmap.dateT0 =  Utils.getDateFrench(theDate);
    theDate = rs.getDate("dateTest");
    theRoadmap.dateTest =  Utils.getDateFrench(theDate);
    //idRoadmap= rs.getInt("idRoadmap");
    theRoadmap.dateT0MOE="";
    theRoadmap.dateDevMOE="";
    theRoadmap.dateSpecMOE="";
    theRoadmap.dateLivrMOE="";

    theRoadmap.nomSt = nom;
    if ((theRoadmap.dateT0 == null) || (theRoadmap.dateT0.equals(""))) continue;
    if ((theRoadmap.dateEB == null) || (theRoadmap.dateEB.equals(""))) continue;
    if ((theRoadmap.dateTest == null) || (theRoadmap.dateTest.equals(""))) continue;
       if ((theRoadmap.dateProd == null) || (theRoadmap.dateProd.equals(""))) continue;
    this.ListeProjets.addElement(theRoadmap);
  }

 }
  catch (SQLException s) { }

}
 
public void getListeProjetsImputables(String nomBase,Connexion myCnx, Statement st, Statement st2, int Annee , int semaine){
 ResultSet rs;
 ResultSet rs2;

 Date theDate = null;
 
 int offsetWeek=Utils.getOffset(Annee);
 theDate = Utils.getDateByWeek(semaine, Annee, offsetWeek);
 String str_date = "";
 
 if (theDate != null)
    str_date ="convert(datetime, '"+theDate.getDate()+"/"+(theDate.getMonth()+1)+"/"+(theDate.getYear() +1900)+"', 103)";
 else
    str_date="null";  

  req="SELECT DISTINCT Roadmap.idRoadmap, Roadmap.Etat, St.nomSt + '-' + Roadmap.version AS nomProjet, St.isRecurrent, Roadmap.dateT0, Roadmap.dateMep, Roadmap.dateMes";
    req+=" FROM         VersionSt INNER JOIN";
    req+="                       Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req+="                       Membre ON VersionSt.respMoeVersionSt = Membre.idMembre INNER JOIN";
    req+="                       PlanDeCharge ON Roadmap.idRoadmap = PlanDeCharge.idRoadmap";
    req+=" WHERE    (Roadmap.Etat <> '') AND (Roadmap.standby = 0) AND (Roadmap.LF_Month = - 1) AND ";
    req+="                       (Roadmap.LF_Year = - 1) AND (((Roadmap.dateT0 <= "+str_date+") AND ((Roadmap.dateMep >= "+str_date+") OR (Roadmap.dateMes >= "+str_date+")))  OR (St.isRecurrent = 1) )";
    req+="                        AND PlanDeCharge.projetMembre = " + this.id;
    req+=" ORDER BY St.isRecurrent DESC";
  rs = myCnx.ExecReq(st, nomBase, req);

  int totalMembre=0;
  try {
      while (rs.next()) {
          Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap"));
                  theRoadmap.EtatRoadmap = rs.getString("Etat");
                  theRoadmap.nomProjet = rs.getString("nomProjet");
                  
                  if (rs.getInt("isRecurrent") == 1)
                      theRoadmap.isProjet = false;
                  else
                      theRoadmap.isProjet = true;
                  
                  theRoadmap.isGenerique= false;
                  theRoadmap.DdateT0 = rs.getDate("dateT0");
                  if (theRoadmap.DdateT0 != null)  theRoadmap.dateT0=Utils.getDateFrench(theRoadmap.DdateT0);
                  theRoadmap.DdateProd = rs.getDate("dateMep");
                  if (theRoadmap.DdateProd != null)  theRoadmap.dateProd=Utils.getDateFrench(theRoadmap.DdateProd);
                  theRoadmap.DdateMes = rs.getDate("dateMes");
                  if (theRoadmap.DdateMes != null)  theRoadmap.dateMes=Utils.getDateFrench(theRoadmap.DdateMes);
                  
                  req="SELECT     Charge, etat";
                  req+=" FROM         PlanDeCharge";
                  req+=" WHERE     (projetMembre = "+this.id+") AND (semaine = "+semaine+") AND (anneeRef = "+Annee+") AND (idRoadmap = "+theRoadmap.id+")";
                  rs2 = myCnx.ExecReq(st2, nomBase, req);
                try {
                    while (rs2.next()) {
                        theRoadmap.ChargePrevue = rs2.getFloat("Charge");
                        theRoadmap.idEtatChargePrevue = rs2.getInt("etat");
                  
                       
                    }

                }
                catch (SQLException s) {s.getMessage();}      
                 this.ListeProjets.addElement(theRoadmap);

               }

        }
                       catch (SQLException s) {s.getMessage();}
                        //theRoadmap.totalReparti = 5;

} 


 public void getListeProjetsRespMoa(String nomBase,Connexion myCnx, Statement st, int Annee ){
 ResultSet rs;
 String nom="";
 String dateT0="";
 String dateEB="";
 String dateTEST="";
 String datePROD="";
 String dateMES="";
 int idVersionSt = -1;
 int idRoadmap = -1;
 String version= "";
 Date theDate = null;

 String currentDate = Utils.getCurrentDate( nomBase, myCnx,  st);
 if (this.MOA.equals("yes"))
  {
 req = "SELECT     ListeProjets.nomProjet, ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.dateMes, ";
 req += "                     ListeProjets.dateT0, ListeProjets.dateTest, nomSt, dateT0_init, dateEB_init, dateTest_init, dateMep_init, dateMes_init";
 req += " FROM         ListeProjets INNER JOIN";
 req += "                      VersionSt ON ListeProjets.idVersionSt = VersionSt.idVersionSt INNER JOIN";
 req += "                      Membre ON VersionSt.respMoaVersionSt = Membre.idMembre";
 req += " WHERE     (Membre.idMembre = "+this.id+") AND (YEAR(ListeProjets.dateMep) = "+Annee+")";
 req += " AND (ListeProjets.dateMep > CONVERT(DATETIME, '"+currentDate+"',  102))";
 req += " ORDER BY ListeProjets.dateMep";
  }
  else
  {
 req = "SELECT     ListeProjets.nomProjet, ListeProjets.idVersionSt, ListeProjets.version, ListeProjets.dateEB, ListeProjets.dateMep, ListeProjets.dateMes, ";
 req += "                     ListeProjets.dateT0, ListeProjets.dateTest, nomSt, dateT0_init, dateEB_init, dateTest_init, dateMep_init, dateMes_init";
 req += " FROM         ListeProjets INNER JOIN";
 req += "                      VersionSt ON ListeProjets.idVersionSt = VersionSt.idVersionSt INNER JOIN";
 req += "                      Membre ON VersionSt.respMoeVersionSt = Membre.idMembre";
 req += " WHERE     (Membre.idMembre = "+this.id+") AND (YEAR(ListeProjets.dateMep) = "+Annee+")";
 req += " AND (ListeProjets.dateMep > CONVERT(DATETIME, '"+currentDate+"',  102))";
 req += " ORDER BY ListeProjets.dateMep";
  }
 rs = myCnx.ExecReq(st, myCnx.nomBase, req);

 try {
 while(rs.next())
 {
    nom = rs.getString("nomProjet");
    dateEB="";
    datePROD="";
    dateMES="";
    idVersionSt= rs.getInt("idVersionSt");
    version = rs.getString("version");
    theDate = rs.getDate("dateEB");
    dateEB =  Utils.getDateFrench(theDate);
    theDate = rs.getDate("dateMep");
    datePROD =  Utils.getDateFrench(theDate);
    theDate = rs.getDate("dateMes");
    dateMES =  Utils.getDateFrench(theDate);
    theDate = rs.getDate("dateT0");
    dateT0 =  Utils.getDateFrench(theDate);
    theDate = rs.getDate("dateTest");
    dateTEST =  Utils.getDateFrench(theDate);
    String nomSt = rs.getString("nomSt");

    Roadmap theRoadmap = new Roadmap(idRoadmap);
    theRoadmap.nomSt=nomSt;
    theRoadmap.dateT0=dateT0;
    theRoadmap.dateEB=dateEB;
    theRoadmap.dateTest=dateTEST;
    theRoadmap.dateProd=datePROD;
    theRoadmap.dateMes=dateMES;
    theRoadmap.idVersionSt=""+idVersionSt;
    theRoadmap.version=version;

    if ((theRoadmap.dateT0 == null) || (theRoadmap.dateT0.equals(""))) continue;
    if ((theRoadmap.dateEB == null) || (theRoadmap.dateEB.equals(""))) continue;
    if ((theRoadmap.dateTest == null) || (theRoadmap.dateTest.equals(""))) continue;
       if ((theRoadmap.dateProd == null) || (theRoadmap.dateProd.equals(""))) continue;
    this.ListeProjets.addElement(theRoadmap);
  }

 }
  catch (SQLException s) { }

}

public void getListeActionsByRoadmapNonImputables(String nomBase,Connexion myCnx, Statement st, int idRoadmap, int semaine, int annee){
  ResultSet rs;
  req="SELECT     id, nom, idEtat, dateAction, dateFin, type, reponse, isPlanning, doc, Code, ChargePrevue, ChargeDevis, Semaine, RAF";
  req += "     FROM         actionSuivi";
  req += " WHERE     (idRoadmap = "+idRoadmap+") AND (acteur LIKE '%"+this.Login+"%')";
  req +=" AND     (";
  req+=" (idEtat = 3) OR";
  req+=" (idEtat = 4) OR";
  req+=" (idEtat = 6)";
  req+=" )";

  req="SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.idEtat, actionSuivi.dateAction, actionSuivi.dateFin, actionSuivi.type, actionSuivi.reponse, ";
  req += "                     actionSuivi.isPlanning, actionSuivi.doc, actionSuivi.Code, actionSuivi.ChargePrevue, actionSuivi.ChargeDevis, actionSuivi.Semaine, actionSuivi.RAF, ";
  req += "                     Consomme.Charge, Consomme.semaine AS Expr1, Consomme.anneeRef, Consomme.projetMembre";
  req += " FROM         actionSuivi INNER JOIN";
  req += "                     Consomme ON actionSuivi.id = Consomme.idAction";
  req += " WHERE     (actionSuivi.idRoadmap = "+idRoadmap+") AND (actionSuivi.idEtat = 3 OR";
  req += "                     actionSuivi.idEtat = 4 OR";
  req += "                     actionSuivi.idEtat = 6) AND (Consomme.semaine = "+semaine+") AND (Consomme.anneeRef = "+annee+") AND (Consomme.projetMembre = "+this.id+") AND ";
  req += "                     (Consomme.Charge > 0)";
  req += " ORDER BY actionSuivi.Code";


  rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  String nom="";
  String acteur="";
  int etat=-1;
  int  id;
  Date theDate;
  String str_theDate;
  String str_theDateFin;
  String str_etat;

  try {
    while (rs.next()) {
      id = rs.getInt("id");
      nom = rs.getString("nom").replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");
      etat = rs.getInt("idEtat");

      theDate = rs.getDate("dateAction");

      if (theDate != null)
        str_theDate = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
       else
        str_theDate = "";

      theDate = rs.getDate("dateFin");
      if (theDate != null)
        str_theDateFin = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
       else
        str_theDateFin = "";

      Action theAction = new Action(id,nom,acteur,etat,idRoadmap,str_theDate,str_theDateFin,"");
      theAction.Code = rs.getString("Code").replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");

      //theAction.dump();
      this.ListeActionsNonImputables.addElement(theAction);
    }
  }
            catch (SQLException s) {s.getMessage();}
}

          public void getListeActionsByRoadmap(String nomBase,Connexion myCnx, Statement st, int idRoadmap){
            ResultSet rs;
            req="SELECT     id, nom, idEtat, dateAction, dateFin, type, reponse, isPlanning, doc, Code, ChargePrevue, ChargeDevis, Semaine, RAF";
            req += "     FROM         actionSuivi";
            req += " WHERE     (idRoadmap = "+idRoadmap+") AND (acteur LIKE '%"+this.Login+"%')";
            req +=" AND     ((idEtat = 1) OR";
            req +="                     (idEtat = 2) OR";
            req +="                     (idEtat = 5))";


            rs = myCnx.ExecReq(st, myCnx.nomBase, req);
            String nom="";
            String acteur="";
            int etat=-1;
            int  id;
            Date theDate;
            String str_theDate;
            String str_theDateFin;
            String str_etat;

            try {
              while (rs.next()) {
                id = rs.getInt("id");
                nom = rs.getString("nom").replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");
                etat = rs.getInt("idEtat");

                theDate = rs.getDate("dateAction");

                if (theDate != null)
                  str_theDate = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
                 else
                  str_theDate = "";

                theDate = rs.getDate("dateFin");
                if (theDate != null)
                  str_theDateFin = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
                 else
                  str_theDateFin = "";

                Action theAction = new Action(id,nom,acteur,etat,idRoadmap,str_theDate,str_theDateFin,"");
                theAction.Code = rs.getString("Code").replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");

                //theAction.dump();
                this.ListeActions.addElement(theAction);
              }
            }
                      catch (SQLException s) {s.getMessage();}
}

 public void getListeActions(String nomBase,Connexion myCnx, Statement st, String MOA, String MOE){
  ResultSet rs;

  if ((MOA.equals("no")) && (MOE.equals("yes")) )
  {
  req = "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, actionSuivi.dateFin";
   req += " FROM         Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       Membre ON VersionSt.respMoeVersionSt = Membre.idMembre INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req += "                       actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap INNER JOIN";
   req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.type = 'MOE')";

   req += " UNION";
   req += " SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, ";
   req += "                       actionSuivi.dateFin";
   req += " FROM         EquipeMembre INNER JOIN";
   req += "                       Membre ON EquipeMembre.idMembre = Membre.idMembre INNER JOIN";
   req += "                       Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
   req += "                       Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req += "                       actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap INNER JOIN";
   req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id ON Equipe.nom = St.nomSt";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND (TypeEtatAction.nom <> 'Clos')  AND (actionSuivi.type = 'MOE')";
  }
  else if ((MOA.equals("yes")))
  {
  req = "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, actionSuivi.dateFin";
   req += " FROM         Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req += "                       actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap INNER JOIN";
   req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.type = 'MOA')";

   req += " UNION";
   req += " SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, ";
   req += "                       actionSuivi.dateFin";
   req += " FROM         EquipeMembre INNER JOIN";
   req += "                       Membre ON EquipeMembre.idMembre = Membre.idMembre INNER JOIN";
   req += "                       Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
   req += "                       Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req += "                       actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap INNER JOIN";
   req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id ON Equipe.nom = St.nomSt";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.type = 'MOA') ";
  }

  else
  {
  req = "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, actionSuivi.dateFin";
   req += " FROM         Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req += "                       actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap INNER JOIN";
   req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.type = 'MOA')";

   req += " UNION";
   req += " SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, ";
   req += "                       actionSuivi.dateFin";
   req += " FROM         EquipeMembre INNER JOIN";
   req += "                       Membre ON EquipeMembre.idMembre = Membre.idMembre INNER JOIN";
   req += "                       Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
   req += "                       Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req += "                       actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap INNER JOIN";
   req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id ON Equipe.nom = St.nomSt";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.type = 'MOA') ";
  }
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);
   String nom="";
   String acteur="";
   int etat=-1;
   int  id;
   int  idRoadmap;
   Date theDate;
   String str_theDate;
   String str_theDateFin;
   String str_etat;

   try {
     while (rs.next()) {
       id = rs.getInt("id");
       nom = rs.getString("nom").replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");
       acteur = rs.getString("acteur").replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");
       etat = rs.getInt("idEtat");
       idRoadmap = rs.getInt("idRoadmap");

       theDate = rs.getDate("dateAction");
       str_etat = rs.getString("nomEtat");
       if (theDate != null)
         str_theDate = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
        else
         str_theDate = "";

       theDate = rs.getDate("dateFin");
       if (theDate != null)
         str_theDateFin = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
        else
         str_theDateFin = "";

       Action theAction = new Action(id,nom,acteur,etat,idRoadmap,str_theDate,str_theDateFin,"");
       theAction.nomEtat = str_etat;
       //theAction.dump();
       this.ListeActions.addElement(theAction);
     }
   }
            catch (SQLException s) {s.getMessage();}
 }

 public void getListeActionsByMonth(String nomBase,Connexion myCnx, Statement st, int mois, int annee){
  ResultSet rs;

  req="SELECT     id, nom, acteur, idEtat, idRoadmap, dateAction, dateFin, type, reponse, isPlanning, doc, Code, ChargePrevue, ChargeDevis, Semaine, RAF, ";
  req+="                    LoginEmetteur, priorite, MONTH(dateCloture) AS mois, dateCloture";
  req+=" FROM         actionSuivi";
  req+=" WHERE     (acteur LIKE '%"+this.Login+"%') AND (MONTH(dateCloture) = "+mois+") AND (YEAR(dateCloture) = "+annee+")";

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);
   String nom="";
   String acteur="";
   int etat=-1;
   int  id;
   int  idRoadmap;
   Date theDate;
   String str_theDate;
   String str_theDateFin;
   String str_etat;

   try {
     while (rs.next()) {
       id = rs.getInt("id");
       nom = rs.getString("nom").replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");
       acteur = rs.getString("acteur").replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");
       etat = rs.getInt("idEtat");
       idRoadmap = rs.getInt("idRoadmap");

       theDate = rs.getDate("dateAction");
       //str_etat = rs.getString("nomEtat");
       if (theDate != null)
         str_theDate = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
        else
         str_theDate = "";

       theDate = rs.getDate("dateFin");
       if (theDate != null)
         str_theDateFin = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
        else
         str_theDateFin = "";

       Action theAction = new Action(id,nom,acteur,etat,idRoadmap,str_theDate,str_theDateFin,"");
       //theAction.nomEtat = str_etat;

       theAction.ChargePrevue = rs.getFloat("ChargePrevue") ;
       //theAction.dump();
       this.ListeActions.addElement(theAction);
     }
   }
            catch (SQLException s) {s.getMessage();}
 }
 
public int getnb_retard(String nomBase,Connexion myCnx, Statement st, String nomJalon, int ordre, int typologie1, int typologie2){

  String req = "";
  req = "select distinct COUNT(idRoadmap) as nb from";
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
  req += "                       AND (LoginMembre = '"+this.Login+"')";
  req += "                       AND (ordre < "+ordre+")";
  req += "  AND (idRoadmap not in (select idRoadmapPere from Roadmap))";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  int nb = 0;

 try {
    while (rs.next()) {
      nb = rs.getInt("nb");
    }
     } catch (SQLException s) {s.getMessage();}


 return nb;
}

 public void getListeProjetsActionsRetard(String nomBase,Connexion myCnx, Statement st, int theService){
  ResultSet rs;

     this.ListeProjets.removeAllElements();

     req="SELECT   distinct  actionSuivi.idRoadmap, St.nomSt, Roadmap.version, Roadmap.idVersionSt";
     req += "     FROM         actionSuivi INNER JOIN";
     req += "                     TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id INNER JOIN";
     req += "                     Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap INNER JOIN";
     req += "                     VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
     req += "                     St ON VersionSt.stVersionSt = St.idSt";
     req += " WHERE     (actionSuivi.acteur LIKE '%" + this.Login + "%') AND (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)) ";
     req += "                     AND (actionSuivi.dateFin IS NOT NULL) AND (Roadmap.standby <> 1)";

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     int idRoadmap = rs.getInt("idRoadmap");
     String nom = rs.getString("nomSt");
     String version = rs.getString("version");
     int idVersionSt = rs.getInt("idVersionSt");

     Roadmap theRoadmap = new Roadmap(idRoadmap);
     theRoadmap.nomSt=nom;

     theRoadmap.idVersionSt=""+idVersionSt;
     theRoadmap.version=version;
     this.ListeProjets.addElement(theRoadmap);
   }

  }
  catch (SQLException s) { }
 }
 public void getListeActionsRetard(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs;

  req="SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, ";
  req += "                    actionSuivi.dateFin, Roadmap.standby";
  req += " FROM         actionSuivi INNER JOIN";
  req += "                    TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id INNER JOIN";
  req += "                    Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap";
  req += " WHERE     (actionSuivi.acteur LIKE '%"+this.Login+"%') AND (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)) ";
  req += "                    AND (actionSuivi.dateFin IS NOT NULL) AND (Roadmap.standby <> 1)";

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);
   String nom="";
   String acteur="";
   int etat=-1;
   int  id;
   int  idRoadmap;
   Date theDate;
   String str_theDate;
   String str_theDateFin;
   String str_etat;

   try {
     while (rs.next()) {
       id = rs.getInt("id");
       nom = rs.getString("nom").replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");
       acteur = rs.getString("acteur").replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");
       etat = rs.getInt("idEtat");
       idRoadmap = rs.getInt("idRoadmap");

       theDate = rs.getDate("dateAction");
       str_etat = rs.getString("nomEtat");
       if (theDate != null)
         str_theDate = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
        else
         str_theDate = "";

       theDate = rs.getDate("dateFin");
       if (theDate != null)
         str_theDateFin = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
        else
         str_theDateFin = "";

       Action theAction = new Action(id,nom,acteur,etat,idRoadmap,str_theDate,str_theDateFin,"");
       theAction.nomEtat = str_etat;
       //theAction.dump();
       this.ListeActions.addElement(theAction);
     }
   }
            catch (SQLException s) {s.getMessage();}
 }
 
public  String setListeActionsRetard(String nomBase,Connexion myCnx, Statement st, String transaction,String myLogin){
  req = "delete  FROM    tempListeMembres";
  if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

  for (int i=0; i < this.ListeActions.size();i++)
  {
    Action theAction = (Action) this.ListeActions.elementAt(i);
    Collaborateur theCollaborateur = new Collaborateur(theAction.acteur);
    theCollaborateur.getAllFromBd(myCnx.nomBase, myCnx, st);

    String req = "INSERT tempListeMembres (Login, idMembre, nomMembre, prenomMembre, LoginMembre, noteCompetence)";
    req+=" VALUES ('"+myLogin+"','"+theCollaborateur.id+"','"+theCollaborateur.nom+"','"+theCollaborateur.prenom+"','"+theCollaborateur.Login+"',"+theAction.id+")";
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

  }
   return "OK";
 } 
 
  public int getnbActionsRetard(String nomBase,Connexion myCnx, Statement st){
      int nb=0;
  ResultSet rs;

  req="SELECT COUNT(*) as nb";
  req += " FROM         actionSuivi INNER JOIN";
  req += "                    TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id INNER JOIN";
  req += "                    Roadmap ON actionSuivi.idRoadmap = Roadmap.idRoadmap";
  req += " WHERE     (actionSuivi.acteur LIKE '%"+this.Login+"%') AND (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)) ";
  req += "                    AND (actionSuivi.dateFin IS NOT NULL) AND (Roadmap.standby <> 1)";

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
       nb = rs.getInt("nb");
     }
   }
            catch (SQLException s) {s.getMessage();}
   return nb;
 }
  
  public void getTotalChargeRoadmap(String nomBase,Connexion myCnx, Statement st, int semaineDebut, int semaineFin, int Annee){
  ResultSet rs;

  req="SELECT     SUM(PlanDeCharge.Charge) AS nb";
  req+=" FROM         Membre INNER JOIN";
  req+="                       PlanDeCharge ON Membre.idMembre = PlanDeCharge.projetMembre";
  req+=" WHERE     (PlanDeCharge.semaine >= "+semaineDebut+") AND (PlanDeCharge.semaine <= "+semaineFin+") AND (PlanDeCharge.anneeRef = "+Annee+")";
  req+=" GROUP BY Membre.nomMembre, Membre.idMembre";
  req+=" HAVING      Membre.idMembre = " + this.id;

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
       this.noteCharge = rs.getInt("nb");
     }
   }
            catch (SQLException s) {s.getMessage();}

 }  
  
  public void getTotalCompetenceRoadmap(String nomBase,Connexion myCnx, Statement st, int idRoadmap){
  ResultSet rs;

    req="SELECT     SUM(CollaborateurCompetence.note) AS nb";
    req+=" FROM         Membre INNER JOIN";
    req+="                       CollaborateurCompetence ON Membre.idMembre = CollaborateurCompetence.idCollaborateur INNER JOIN";
    req+="                       Middleware ON CollaborateurCompetence.idCompetence = Middleware.idMiddleware INNER JOIN";
    req+="                       VersionMW ON Middleware.idMiddleware = VersionMW.mwVersionMW INNER JOIN";
    req+="                       ST_MW ON VersionMW.idVersionMW = ST_MW.mwST_MW INNER JOIN";
    req+="                      VersionSt ON ST_MW.versionStST_MW = VersionSt.idVersionSt INNER JOIN";
    req+="                       Roadmap ON VersionSt.idVersionSt = Roadmap.idVersionSt";
    req+=" WHERE     (CollaborateurCompetence.note <> - 1) AND (Membre.idMembre = "+this.id+") AND (Roadmap.idRoadmap = "+idRoadmap+")";

   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
     while (rs.next()) {
       this.noteCompetence = rs.getInt("nb");
     }
   }
            catch (SQLException s) {s.getMessage();}

 }  

 public void getListeActionsRetard2(String nomBase,Connexion myCnx, Statement st, String MOA, String MOE){
  ResultSet rs;

  if ((MOA.equals("no")) && (MOE.equals("yes")) )
  {
  req = "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, actionSuivi.dateFin";
   req += " FROM         Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       Membre ON VersionSt.respMoeVersionSt = Membre.idMembre INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req += "                       actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap INNER JOIN";
   req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.type = 'MOE') AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102))  AND (actionSuivi.dateFin IS NOT NULL)";

   req += " UNION";
   req += " SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, ";
   req += "                       actionSuivi.dateFin";
   req += " FROM         EquipeMembre INNER JOIN";
   req += "                       Membre ON EquipeMembre.idMembre = Membre.idMembre INNER JOIN";
   req += "                       Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
   req += "                       Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req += "                       actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap INNER JOIN";
   req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id ON Equipe.nom = St.nomSt";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND (TypeEtatAction.nom <> 'Clos')  AND (actionSuivi.type = 'MOE') AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102))  AND (actionSuivi.dateFin IS NOT NULL)";
  }
  else if ((MOA.equals("yes")))
  {
  req = "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, actionSuivi.dateFin";
   req += " FROM         Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req += "                       actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap INNER JOIN";
   req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.type = 'MOA') AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102))  AND (actionSuivi.dateFin IS NOT NULL)";

   req += " UNION";
   req += " SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, ";
   req += "                       actionSuivi.dateFin";
   req += " FROM         EquipeMembre INNER JOIN";
   req += "                       Membre ON EquipeMembre.idMembre = Membre.idMembre INNER JOIN";
   req += "                       Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
   req += "                       Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req += "                       actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap INNER JOIN";
   req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id ON Equipe.nom = St.nomSt";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.type = 'MOA') AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102))  AND (actionSuivi.dateFin IS NOT NULL)";
  }

  else
  {
  req = "SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, actionSuivi.dateFin";
   req += " FROM         Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req += "                       actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap INNER JOIN";
   req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.type = 'MOA') AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102))  AND (actionSuivi.dateFin IS NOT NULL)";

   req += " UNION";
   req += " SELECT     actionSuivi.id, actionSuivi.nom, actionSuivi.acteur, actionSuivi.idEtat, actionSuivi.idRoadmap, actionSuivi.dateAction, TypeEtatAction.nom AS nomEtat, ";
   req += "                       actionSuivi.dateFin";
   req += " FROM         EquipeMembre INNER JOIN";
   req += "                       Membre ON EquipeMembre.idMembre = Membre.idMembre INNER JOIN";
   req += "                       Equipe ON EquipeMembre.idMembreEquipe = Equipe.id INNER JOIN";
   req += "                       Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req += "                       actionSuivi ON Roadmap.idRoadmap = actionSuivi.idRoadmap INNER JOIN";
   req += "                       TypeEtatAction ON actionSuivi.idEtat = TypeEtatAction.id ON Equipe.nom = St.nomSt";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND (TypeEtatAction.nom <> 'Clos') AND (actionSuivi.type = 'MOA') AND (actionSuivi.dateFin < CONVERT(DATETIME, CURRENT_TIMESTAMP, 102))  AND (actionSuivi.dateFin IS NOT NULL)";
  }
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);
   String nom="";
   String acteur="";
   int etat=-1;
   int  id;
   int  idRoadmap;
   Date theDate;
   String str_theDate;
   String str_theDateFin;
   String str_etat;

   try {
     while (rs.next()) {
       id = rs.getInt("id");
       nom = rs.getString("nom").replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");
       acteur = rs.getString("acteur").replaceAll("\\r","\\\\r").replaceAll("\\n","\\\\n").replaceAll("\"","\\\\\"");
       etat = rs.getInt("idEtat");
       idRoadmap = rs.getInt("idRoadmap");

       theDate = rs.getDate("dateAction");
       str_etat = rs.getString("nomEtat");
       if (theDate != null)
         str_theDate = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
        else
         str_theDate = "";

       theDate = rs.getDate("dateFin");
       if (theDate != null)
         str_theDateFin = ""+theDate.getDate()+"/"+(theDate.getMonth()+1) + "/"+(theDate.getYear() + 1900);
        else
         str_theDateFin = "";

       Action theAction = new Action(id,nom,acteur,etat,idRoadmap,str_theDate,str_theDateFin,"");
       theAction.nomEtat = str_etat;
       //theAction.dump();
       this.ListeActions.addElement(theAction);
     }
   }
            catch (SQLException s) {s.getMessage();}
 }

 public int getnb_retardT0(String nomBase,Connexion myCnx, Statement st){

   String req = "";

   req = "exec SERVICE_nb_retardT0_ByLogin '"+this.Login+"'";
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   int nb = 0;

  try {
     while (rs.next()) {
       String nomSt = rs.getString(2);
       nb++;
     }
      } catch (SQLException s) {s.getMessage();}


  return nb;
 }


public int getnb_Alertes(String nomBase,Connexion myCnx, Statement st){

  String req = "exec [ACTEUR_nbAlertes] '"+this.Login+"'";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  int nb = 0;

 try {
    while (rs.next()) {
      String nomSt = rs.getString(2);
      nb++;
    }
     } catch (SQLException s) {s.getMessage();}


 return nb;
}

public int getnbConnexions(String nomBase,Connexion myCnx, Statement st){

  String req = "SELECT     COUNT(*) AS nb FROM         StatConnexion WHERE     (UserName = '"+this.Login+"')";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  int nb = 0;

 try {
    while (rs.next()) {
        nb= rs.getInt(1);
    }
     } catch (SQLException s) {s.getMessage();}


 return nb;
}

public int getnbConnexionsToDay(String nomBase,Connexion myCnx, Statement st){
Date theDate = new Date();
int theYear = theDate.getYear() + 1900;
int theMonth = theDate.getMonth() + 1;
int theDay = theDate.getDate();


  String req = "SELECT     COUNT(*) AS nb FROM         StatConnexion WHERE     (UserName = '"+this.Login+"')";
  req += " AND (YEAR(dateCnx) = "+theYear+") AND (MONTH(dateCnx) = "+theMonth+") AND (DAY(dateCnx) = "+theDay+")";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  int nb = 0;

 try {
    while (rs.next()) {
      nb= rs.getInt(1);

    }
     } catch (SQLException s) {s.getMessage();}


 return nb;
}

  public int getNoteCompetence(String nomBase,Connexion myCnx, Statement st, Competence theCompetence){

    String req = "SELECT     CollaborateurCompetence.note";
    req+="    FROM         CollaborateurCompetence INNER JOIN";
    req+="                   Middleware ON CollaborateurCompetence.idCompetence = Middleware.idMiddleware";
    req+=" WHERE     (CollaborateurCompetence.idCollaborateur = "+this.id+") AND (Middleware.idMiddleware = "+theCompetence.theLogiciel.id+")";


    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    int note = 0;

   try {
      while (rs.next()) {
        note = rs.getInt("note");

      }
       } catch (SQLException s) {s.getMessage();}


   return note;
}

public int getnb_retardTestMOE(String nomBase,Connexion myCnx, Statement st){

 String req = "exec SERVICE_nb_retardTestMOE_ByLogin '"+this.Login+"'";
 ResultSet rs = myCnx.ExecReq(st, nomBase, req);
 int nb = 0;

try {
   while (rs.next()) {
     String nomSt = rs.getString(2);
     nb++;
   }
    } catch (SQLException s) {s.getMessage();}


return nb;
}


   public int getnb_retardEB(String nomBase,Connexion myCnx, Statement st){

     String req = "";
     req = "exec SERVICE_nb_retardEB_ByLogin '"+this.Login+"'";

     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     int nb = 0;

    try {
       while (rs.next()) {
         String nomSt = rs.getString(2);
         nb++;
       }
        } catch (SQLException s) {s.getMessage();}


    return nb;
 }
   
 public int getnb_Collaborateurs(String nomBase,Connexion myCnx, Statement st, int anneeRef, int idRoadmap){

     String req = "";
     req = "SELECT     COUNT(DISTINCT PlanDeCharge.projetMembre) AS nb";
     req+=" FROM         Membre INNER JOIN";
     req+="                       Service ON Membre.serviceMembre = Service.idService INNER JOIN";
     req+="                       PlanDeCharge ON Membre.idMembre = PlanDeCharge.projetMembre";
     //req+=" WHERE     (PlanDeCharge.anneeRef = "+anneeRef+") AND (PlanDeCharge.idRoadmap = "+idRoadmap+") AND (Service.idService ="+this.service+")";
     req+=" WHERE      (PlanDeCharge.idRoadmap = "+idRoadmap+") AND (Service.idService ="+this.service+")";
     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     int nb = 0;

    try {
       while (rs.next()) {
         nb = rs.getInt("nb");
       }
        } catch (SQLException s) {s.getMessage();}
return nb;

 }

  public int getnb_retardTEST(String nomBase,Connexion myCnx, Statement st){

    String req = "";
    req = "exec SERVICE_nb_retardTest_ByLogin '"+this.Login+"'";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    int nb = 0;

   try {
      while (rs.next()) {
        String nomSt = rs.getString(2);
        nb++;
      }
       } catch (SQLException s) {s.getMessage();}


   return nb;
 }

 public int getnb_retardPROD(String nomBase,Connexion myCnx, Statement st){


   String req = "";
   req = "exec SERVICE_nb_retardPROD_ByLogin '"+this.Login+"'";
   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   int nb = 0;

  try {
     while (rs.next()) {
       String nomSt = rs.getString(2);
       nb++;
     }
      } catch (SQLException s) {s.getMessage();}


  return nb;
 }

   public int getnb_retardMES(String nomBase,Connexion myCnx, Statement st){

     String req = "";
     req = "exec SERVICE_nb_retardMES_ByLogin '"+this.Login+"'";
     ResultSet rs = myCnx.ExecReq(st, nomBase, req);
     int nb = 0;

    try {
       while (rs.next()) {
         String nomSt = rs.getString(2);
         nb++;
       }
        } catch (SQLException s) {s.getMessage();}


    return nb;
 }

 public void getListeAllProjets(String nomBase,Connexion myCnx, Statement st, String MOA){
   ResultSet rs;
   String nom="";
   String version="";
   int idRoadmap=0;
   int idVersionSt=0;

   this.ListeProjets.removeAllElements();

   if (MOA.equals("yes"))
   {
   req = "SELECT     Roadmap.idRoadmap, St.nomSt, Roadmap.version,  VersionSt.idVersionSt";
   req += " FROM         Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND(LF_Month = - 1) AND (LF_Year = - 1)";

   req += " UNION";
   req += " SELECT DISTINCT Roadmap.idRoadmap, St.nomSt, Roadmap.version, VersionSt.idVersionSt";
   req += " FROM         TypeEtatAction CROSS JOIN";
   req += "                       Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req += "                       EquipeMembre INNER JOIN";
   req += "                       Membre ON EquipeMembre.idMembre = Membre.idMembre INNER JOIN";
   req += "                       Equipe ON EquipeMembre.idMembreEquipe = Equipe.id ON St.nomSt = Equipe.nom";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND(LF_Month = - 1) AND (LF_Year = - 1)";

   }
   else
   {
   req = "SELECT     Roadmap.idRoadmap, St.nomSt, Roadmap.version,  VersionSt.idVersionSt";
   req += " FROM         Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       Membre ON VersionSt.respMoeVersionSt = Membre.idMembre INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND(LF_Month = - 1) AND (LF_Year = - 1)";

   req += " UNION";
   req += " SELECT DISTINCT Roadmap.idRoadmap, St.nomSt, Roadmap.version, VersionSt.idVersionSt";
   req += " FROM         TypeEtatAction CROSS JOIN";
   req += "                       Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
   req += "                       EquipeMembre INNER JOIN";
   req += "                       Membre ON EquipeMembre.idMembre = Membre.idMembre INNER JOIN";
   req += "                       Equipe ON EquipeMembre.idMembreEquipe = Equipe.id ON St.nomSt = Equipe.nom";
   req += " WHERE     (Membre.idMembre = "+this.id+") AND(LF_Month = - 1) AND (LF_Year = - 1)";
   }
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
   while(rs.next())
   {
      idRoadmap = rs.getInt("idRoadmap");
      nom = rs.getString("nomSt");
      version = rs.getString("version");
      idVersionSt = rs.getInt("idVersionSt");

      Roadmap theRoadmap = new Roadmap(idRoadmap);
      theRoadmap.nomSt=nom;

      theRoadmap.idVersionSt=""+idVersionSt;
      theRoadmap.version=version;
      this.ListeProjets.addElement(theRoadmap);
    }

   }
   catch (SQLException s) { }


}

 public void getListeAllProjets2(String nomBase,Connexion myCnx, Statement st, String MOA){
   ResultSet rs;
   String nom="";
   String version="";
   int idRoadmap=0;
   int idVersionSt=0;

   this.ListeProjets.removeAllElements();

   if (MOA.equals("yes"))
   {
   req = "SELECT     Roadmap.idRoadmap, St.nomSt, Roadmap.version, Roadmap.description, VersionSt.idVersionSt";
   req += " FROM         Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       Membre ON VersionSt.respMoaVersionSt = Membre.idMembre INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt";
   req += " WHERE     (Membre.idMembre = "+this.id+")";
   }
   else
   {
   req = "SELECT     Roadmap.idRoadmap, St.nomSt, Roadmap.version, Roadmap.description, VersionSt.idVersionSt";
   req += " FROM         Roadmap INNER JOIN";
   req += "                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
   req += "                       Membre ON VersionSt.respMoeVersionSt = Membre.idMembre INNER JOIN";
   req += "                       St ON VersionSt.stVersionSt = St.idSt";
   req += " WHERE     (Membre.idMembre = "+this.id+")";
   }
   rs = myCnx.ExecReq(st, myCnx.nomBase, req);

   try {
   while(rs.next())
   {
      idRoadmap = rs.getInt("idRoadmap");
      nom = rs.getString("nomSt");
      version = rs.getString("version");
      idVersionSt = rs.getInt("idVersionSt");

      Roadmap theRoadmap = new Roadmap(idRoadmap);
      theRoadmap.nomSt=nom;

      theRoadmap.idVersionSt=""+idVersionSt;
      theRoadmap.version=version;
      this.ListeProjets.addElement(theRoadmap);
    }

   }
   catch (SQLException s) { }


}

 public void getListeProjets(String nomBase,Connexion myCnx, Statement st, int Annee, Statement st2 ){

  int idGenerique = get_S_IdGenerique( nomBase, myCnx,  st );
  this.getListeProjetsCollaborateur( nomBase, myCnx,  st ,idGenerique, Annee, st2);
  if (idGenerique != this.id)
    this.getListeProjetsCollaborateur( nomBase, myCnx,  st ,this.id,  Annee, st2);

  this.maxProjets = this.ListeProjets.size() + 10;
}
 
  public void getListeServices(String nomBase,Connexion myCnx, Statement st){
    req= "SELECT     Service_2.idService, Service_2.nomService";
    req+= " FROM         Membre INNER JOIN";
    req+= "                       Service ON Membre.serviceMembre = Service.idService INNER JOIN";
    req+= "                       Service AS Service_1 ON Service.idServicePere = Service_1.idService INNER JOIN";
    req+= "                       Service AS Service_2 ON Service_1.idService = Service_2.idServicePere";
    req+= " WHERE     (Membre.LoginMembre = '"+this.Login+"')";
    req+= " ORDER BY Service_2.nomService";
    
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     Service theService = new Service (rs.getInt("idService"));
     theService.nom = rs.getString("nomService");

     this.ListeServices.addElement(theService);
   }

  }
   catch (SQLException s) { }    
}
  
  public void getListeAllServicesWithCC(String nomBase,Connexion myCnx, Statement st){
    req= "SELECT     idService, nomService";
    req+= " FROM         Service";
    req+= " WHERE     (nomServiceImputations IS NOT NULL) AND (nomServiceImputations <> '') AND (CentreCout IS NOT NULL) AND (CentreCout <> '')";
    
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     Service theService = new Service (rs.getInt("idService"));
     theService.nom = rs.getString("nomService");

     this.ListeServices.addElement(theService);
   }

  }
   catch (SQLException s) { }    
}
  
 public void getListeProjetsEtat(String nomBase,Connexion myCnx, Statement st, int Annee, Statement st2, String Etat_AD,String Etat_T0, String Etat_EB, String Etat_TEST, String Etat_MEP, String Etat_MES ){
//getListeProjetsCollaborateurEtat(String nomBase,Connexion myCnx, Statement st, int idCollaborateur, int Annee, Statement st2, String Etat_AD,String Etat_T0, String Etat_EB, String Etat_TEST, String Etat_MEP, String Etat_MES 
  int idGenerique = get_S_IdGenerique( nomBase, myCnx,  st );
  this.getListeProjetsCollaborateur( nomBase, myCnx,  st ,idGenerique, Annee, st2);
  if (idGenerique != this.id)
    this.getListeProjetsCollaborateurEtat( nomBase, myCnx,  st ,this.id,  Annee, st2,Etat_AD, Etat_T0,  Etat_EB,  Etat_TEST,  Etat_MEP,  Etat_MES);

  this.maxProjets = this.ListeProjets.size() + 10;
} 
 
 public void getListeProjetsEtatGlissant(String nomBase,Connexion myCnx, Statement st, int Annee, Statement st2, String Etat_AD,String Etat_T0, String Etat_EB, String Etat_TEST, String Etat_MEP, String Etat_MES, int monthRef){
//getListeProjetsCollaborateurEtat(String nomBase,Connexion myCnx, Statement st, int idCollaborateur, int Annee, Statement st2, String Etat_AD,String Etat_T0, String Etat_EB, String Etat_TEST, String Etat_MEP, String Etat_MES 
  int idGenerique = get_S_IdGenerique( nomBase, myCnx,  st );
  this.getListeProjetsCollaborateur( nomBase, myCnx,  st ,idGenerique, Annee, st2);
  if (idGenerique != this.id)
    this.getListeProjetsCollaborateurEtatGlissant( nomBase, myCnx,  st ,this.id,  Annee, st2,Etat_AD, Etat_T0,  Etat_EB,  Etat_TEST,  Etat_MEP,  Etat_MES, monthRef);

  this.maxProjets = this.ListeProjets.size() + 10;
}  

public void getListeProjetsOuverts(String nomBase,Connexion myCnx, Statement st, int Annee, Statement st2 ){
    this.ListeProjets.removeAllElements();
 this.idGenerique = get_S_IdGenerique( nomBase, myCnx,  st );
 this.getListeProjetsCollaborateur( nomBase, myCnx,  st ,this.idGenerique, Annee, st2);
 if (idGenerique != this.id)
   this.getListeProjetsCollaborateurOuverts( nomBase, myCnx,  st ,this.id,  Annee, st2);

 this.maxProjets = this.ListeProjets.size() + 10;
}

public void getListeRespMoa(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs=null;

  if (this.MOA.equals("yes"))
  {
  req = "SELECT DISTINCT Membre.prenomMembre, Membre.nomMembre, Membre.LoginMembre, Membre.idMembre";
  req += " FROM         ListeST INNER JOIN";
  req += "                       Membre ON ListeST.respMoaVersionSt = Membre.idMembre AND ListeST.serviceMoaVersionSt = Membre.serviceMembre";

  req+= " WHERE     (ListeST.serviceMoaVersionSt = "+this.service+")";
  }
  else
  {
  req = "SELECT DISTINCT Membre.prenomMembre, Membre.nomMembre, Membre.LoginMembre, Membre.idMembre";
  req += " FROM         ListeST INNER JOIN";
  req += "                       Membre ON ListeST.respMoeVersionSt = Membre.idMembre AND ListeST.serviceMoeVersionSt = Membre.serviceMembre";

  req+= " WHERE     (ListeST.serviceMoeVersionSt = "+this.service+")";
  }
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
     String prenomMembre = rs.getString("prenomMembre");
     String nomMembre = rs.getString("nomMembre");
     String LoginMembre = rs.getString("LoginMembre");
     int idMembre = rs.getInt("idMembre");


     Collaborateur theCollaborateur = new Collaborateur(LoginMembre);
     theCollaborateur.prenom = prenomMembre;
     theCollaborateur.nom= nomMembre;
     theCollaborateur.id= idMembre;

     this.ListeRespMoa.addElement(theCollaborateur);
   }

  }
   catch (SQLException s) { }

}


public void getChargesProjetsCollaborateur(String nomBase,Connexion myCnx, Statement st , int anneeRef, int idCollaborateur){
ResultSet rs;
for (int i=0; i < this.ListeProjets.size(); i++)
  {
  Roadmap theRoadmap = (Roadmap)this.ListeProjets.elementAt(i);

  req="ACTEUR_selectChargeByMembre '"+theRoadmap.id+"','"+idCollaborateur+"',"+anneeRef+"";
  rs = myCnx.ExecReq(st, nomBase, req);

  int totalMembre=0;
  try {
      while (rs.next()) {
                  int sem = rs.getInt(1);
                  float charge = rs.getFloat(2);
                  int idEtat = rs.getInt("etat");
                  theRoadmap.addListeCharges(anneeRef, sem,charge, idEtat);
                  theRoadmap.totalReparti+=charge;
                  this.totalMembre+=charge;
               }

        }
                       catch (SQLException s) {s.getMessage();}
                        //theRoadmap.totalReparti = 5;
  }

}

public void getChargesProjetsCollaborateurGlissant(String nomBase,Connexion myCnx, Statement st , int anneeRef, int idCollaborateur, int weekRef){
ResultSet rs;
for (int i=0; i < this.ListeProjets.size(); i++)
  {
  Roadmap theRoadmap = (Roadmap)this.ListeProjets.elementAt(i);

    req="SELECT * FROM";
    req+=" (";
    req+=" SELECT DISTINCT PlanDeCharge.semaine, PlanDeCharge.Charge, PlanDeCharge.anneeRef, PlanDeCharge.nomProjet, Roadmap.idRoadmap, PlanDeCharge.etat";
    req+=" FROM         PlanDeCharge INNER JOIN";
    req+="                       Membre ON PlanDeCharge.projetMembre = Membre.idMembre INNER JOIN";
    req+="                       Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap";
    req+=" WHERE     (Membre.idMembre = "+idCollaborateur+") AND (PlanDeCharge.anneeRef = "+anneeRef+") AND (Roadmap.idRoadmap = "+theRoadmap.id+") AND PlanDeCharge.semaine >= "+weekRef+" ";

    req+=" UNION";

    req+=" SELECT DISTINCT PlanDeCharge.semaine, PlanDeCharge.Charge, PlanDeCharge.anneeRef, PlanDeCharge.nomProjet, Roadmap.idRoadmap, PlanDeCharge.etat";
    req+=" FROM         PlanDeCharge INNER JOIN";
    req+="                       Membre ON PlanDeCharge.projetMembre = Membre.idMembre INNER JOIN";
    req+="                       Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap";
    req+=" WHERE     (Membre.idMembre = "+idCollaborateur+") AND (PlanDeCharge.anneeRef = "+(anneeRef +1)+") AND (Roadmap.idRoadmap = "+theRoadmap.id+") AND PlanDeCharge.semaine < "+weekRef+" ";
    req+=" ) ";
    req+=" as myTable order by anneeRef, semaine";
  rs = myCnx.ExecReq(st, nomBase, req);

  int totalMembre=0;
  try {
      while (rs.next()) {
                  int sem = rs.getInt("semaine");
                  float charge = rs.getFloat("Charge");
                  int idEtat = rs.getInt("etat");
                  int annee = rs.getInt("anneeRef");
                  theRoadmap.addListeCharges(annee, sem,charge, idEtat);
                  theRoadmap.totalReparti+=charge;
                  this.totalMembre+=charge;
               }

        }
                       catch (SQLException s) {s.getMessage();}
                        //theRoadmap.totalReparti = 5;
  }

}

public void getChargesProjetsCollaborateurByWeek(String nomBase,Connexion myCnx, Statement st , int anneeRef, int idCollaborateur, int semaine){
ResultSet rs;

  

  req="SELECT   DISTINCT  PlanDeCharge.idRoadmap,St.nomSt + '-' + Roadmap.version AS nomProjet, PlanDeCharge.Charge,  St.isRecurrent, PlanDeCharge.etat";
    req+=" FROM         PlanDeCharge INNER JOIN";
    req+="                      Roadmap ON PlanDeCharge.idRoadmap = Roadmap.idRoadmap INNER JOIN";
    req+="                       VersionSt ON Roadmap.idVersionSt = VersionSt.idVersionSt INNER JOIN";
    req+="                       St ON VersionSt.stVersionSt = St.idSt";
    req+=" WHERE     (PlanDeCharge.semaine = "+semaine+") AND (PlanDeCharge.anneeRef = "+anneeRef+") AND (PlanDeCharge.projetMembre = "+idCollaborateur+")";
    req+=" ORDER BY St.isRecurrent DESC";
  rs = myCnx.ExecReq(st, nomBase, req);

  int totalMembre=0;
  try {
      while (rs.next()) {
          Roadmap theRoadmap = new Roadmap(rs.getInt("idRoadmap"));
                  theRoadmap.nomProjet = rs.getString("nomProjet");
                  theRoadmap.ChargePrevue = rs.getFloat("Charge");
                  if (rs.getInt("isRecurrent") == 1)
                      theRoadmap.isProjet = false;
                  else
                      theRoadmap.isProjet = true;
                  
                  if (idCollaborateur != this.id)
                      theRoadmap.isGenerique= true;
                  else
                      theRoadmap.isGenerique= false;
                  
                  theRoadmap.idEtatChargePrevue = rs.getInt("etat");
                  
                  this.ListeProjets.addElement(theRoadmap);

               }

        }
                       catch (SQLException s) {s.getMessage();}
                        //theRoadmap.totalReparti = 5;
  

}

public void getChargesConsommeesByAction(String nomBase,Connexion myCnx, Statement st , int idAction){
ResultSet rs;


  req="SELECT     anneeRef, semaine, Charge";
  req+="    FROM         Consomme";
  req+=" WHERE     (projetMembre = "+this.id+") AND (idAction = "+idAction+") ORDER BY anneeRef, semaine";

  rs = myCnx.ExecReq(st, nomBase, req);

  int totalMembre=0;
  try {
      while (rs.next()) {
                  int anneeRef = rs.getInt("anneeRef");
                  int semaine = rs.getInt("semaine");
                  float charge = rs.getFloat("Charge");
                  Charge theCharge=new Charge(anneeRef,semaine,charge);
                  this.ListeCharges.addElement(theCharge);
                  this.totalMembre+=charge;
               }

        }
                       catch (SQLException s) {s.getMessage();}

}



public float getChargesProjetsByWeekCollaborateur(String nomBase,Connexion myCnx, Statement st , int anneeRef, int idCollaborateur, int semaine ){
ResultSet rs;
  float totalCharge= 0;
  req = "exec [ACTEUR_totalChargeByMembreByWeek] "+this.id+", "+idCollaborateur+", "+semaine+", "+anneeRef;
  rs = myCnx.ExecReq(st, nomBase, req);
  try { rs.next();
    totalCharge = rs.getFloat(1);
  }
   catch (SQLException s) {s.getMessage();}

   return totalCharge;
}

 public float getChargesTachesByWeekCollaborateur(String nomBase,Connexion myCnx, Statement st , int anneeRef, int idCollaborateur, int semaine ){
 ResultSet rs;
   float totalCharge= 0;
   
   req = "SELECT     SUM(Charge) AS total";
   req += " FROM         Consomme";
   req += " WHERE     (anneeRef = "+anneeRef+") AND (semaine = "+semaine+") AND (projetMembre = "+idCollaborateur+")";
   
   rs = myCnx.ExecReq(st, nomBase, req);
   try { rs.next();
     totalCharge += rs.getFloat(1);
   }
    catch (SQLException s) {s.getMessage();}   

    return totalCharge;
}
 
 public float getChargesByYear(String nomBase,Connexion myCnx, Statement st , int anneeRef){
 ResultSet rs;
   float totalCharge= 0;
   
   req = "SELECT     SUM(Charge) AS total";
   req += " FROM         PlanDeCharge";
   req += " WHERE     (anneeRef = "+anneeRef+") AND (projetMembre  = "+this.id+") ";
   
   rs = myCnx.ExecReq(st, nomBase, req);
   try { rs.next();
     totalCharge += rs.getFloat(1);
   }
    catch (SQLException s) {s.getMessage();}   

    return totalCharge;
} 
 
 public float getChargesTachesByWeekCollaborateur2(String nomBase,Connexion myCnx, Statement st , int anneeRef, int idCollaborateur, int semaine ){
 ResultSet rs;
   float totalCharge= 0;
   req = "SELECT     SUM(Charge) AS totalCharge";
   req+="    FROM         Consomme";
   req+=" WHERE     (anneeRef = "+anneeRef+") AND (projetMembre = "+this.id+") AND (semaine = "+semaine+")";

   rs = myCnx.ExecReq(st, nomBase, req);
   try { rs.next();
     totalCharge = rs.getFloat(1);
   }
    catch (SQLException s) {s.getMessage();}

    return totalCharge;
} 

public void getTotalChargesProjets(String nomBase,Connexion myCnx, Statement st , int anneeRef, int idCollaborateur,  int idGenerique){
ResultSet rs;
  req="exec ACTEUR_selectTotalChargeByMembre '"+this.id+"','"+anneeRef+"',"+idGenerique;
  rs = myCnx.ExecReq(st, nomBase, req);

  int totalMembre=0;
  try {
      while (rs.next()) {
                  int sem = rs.getInt(1);
                  float charge = rs.getFloat(2);
                  int idEtat = rs.getInt("etat");
                  this.addListeCharges(anneeRef, sem,charge, idEtat);
                  this.totalMembre+=charge;
               }

        }
                       catch (SQLException s) {s.getMessage();}

}

public void getChargesProjetsByWeek(String nomBase,Connexion myCnx, Statement st , int anneeRef, int semaine){
this.getChargesProjetsCollaborateurByWeek( nomBase, myCnx,  st ,  anneeRef, this.idGenerique, semaine);
  this.getChargesProjetsCollaborateurByWeek( nomBase, myCnx,  st ,  anneeRef, this.id, semaine);
}

public void getChargesProjetsByWeekForImputation(String nomBase,Connexion myCnx, Statement st, Statement st2 , int anneeRef, int semaine){
  this.getChargesProjetsCollaborateurByWeek( nomBase, myCnx,  st ,  anneeRef, this.idGenerique, semaine);
  this.getListeProjetsImputables( nomBase, myCnx,  st,  st2, anneeRef ,  semaine);
}

public void getChargesProjets(String nomBase,Connexion myCnx, Statement st , int anneeRef){
this.getChargesProjetsCollaborateur( nomBase, myCnx,  st ,  anneeRef, this.idGenerique);
  this.getChargesProjetsCollaborateur( nomBase, myCnx,  st ,  anneeRef, this.id);
}

public void getChargesProjetsGlissant(String nomBase,Connexion myCnx, Statement st , int anneeRef, int weekRef){
this.getChargesProjetsCollaborateurGlissant( nomBase, myCnx,  st ,  anneeRef, this.idGenerique, weekRef);
  this.getChargesProjetsCollaborateurGlissant( nomBase, myCnx,  st ,  anneeRef, this.id, weekRef);
}

public void getChargesTaches(String nomBase,Connexion myCnx, Statement st , int anneeRef){

ResultSet rs = null;
  for (int i=0; i < this.ListeActions.size(); i++)
    {
    Action theAction = (Action)this.ListeActions.elementAt(i);

    req="SELECT     SUM(Charge) AS totalReparti";
    req+="     FROM         Consomme";
    req+=" WHERE     (anneeRef = "+anneeRef+") AND (idAction = "+theAction.id+") AND (projetMembre = "+this.id+")";

    rs = myCnx.ExecReq(st, nomBase, req);

    int totalMembre=0;
    try {
        while (rs.next()) {
                    float charge = rs.getFloat("totalReparti");
                    theAction.totalReparti+=charge;
                    this.totalMembre+=charge;
                 }

          }
                         catch (SQLException s) {s.getMessage();}
  }
}

 public void addListeEquipe(int idMembreEquipe)
{
   Equipe theEquipe = new Equipe(idMembreEquipe);
   this.ListeEquipes.addElement(theEquipe);
  }


  public boolean isRegistered(String nomBase,Connexion myCnx, Statement st ){
    
    boolean isRegistered = false;
    int idGenerique=0;
    ResultSet rs;
    req = "SELECT     idMembre FROM   Membre WHERE     (LoginMembre = '"+this.Login+"')";
    req+= " AND     (password = '"+this.Password+"')";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
    while(rs.next())
    {
       this.id = rs.getInt(1);
       isRegistered = true;
     }
    }
   catch (SQLException s) { }

   return isRegistered;
 }     
 
  public int isInListe(String nomBase,Connexion myCnx, Statement st, String myListe)
 {
   req = "SELECT     COUNT(nom) AS nb";
   req+="    FROM         "+myListe;
   req+=" WHERE     (nom = '"+this.Login.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"')";

   ResultSet rs = myCnx.ExecReq(st, myCnx.nomBase, req);
  int nb=0;
  try { while(rs.next()) {
    nb=rs.getInt("nb");
   } }catch (SQLException s) {s.getMessage();}

    return nb;
  }

  public Roadmap addListeProjet(String nom, int idRoadmap)
 {
    Roadmap theRoadmap = new Roadmap(idRoadmap);
    theRoadmap.nomSt = nom;
    this.ListeProjets.addElement(theRoadmap);
    return theRoadmap;
  }

  public void addListeCharges(int anneeRef, int semaine, float hj, int idEtat)
 {
    if (idEtat == 0) idEtat = 1;
    Charge theCharge = new Charge(anneeRef, semaine,hj);
    theCharge.idEtat = idEtat;
    this.ListeCharges.addElement(theCharge);
  }

  public ErrorSpecific bd_InsertListeEquipe(String nomBase,Connexion myCnx, Statement st, String transaction){
  ErrorSpecific myError = new ErrorSpecific();
  ResultSet rs=null;

  req = "delete  FROM    EquipeMembre WHERE (idMembre = "+this.id+") AND (type = 'resp')";
  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeEquipe",""+this.id);
  if (myError.cause == -1) return myError;

   for (int i=0; i < this.ListeEquipes.size(); i++)
   {
     Equipe theEquipe = (Equipe)this.ListeEquipes.elementAt(i);
      String req = "INSERT EquipeMembre (idMembre, idMembreEquipe, type) VALUES ("+this.id+","+theEquipe.id+",'resp'"+")";
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeEquipe",""+this.id);
     if (myError.cause == -1) return myError;
   }

   return myError;

}

public ErrorSpecific bd_DeleteCharges(String nomBase,Connexion myCnx, Statement st, int idRoadmap, String transaction){
  ErrorSpecific myError = new ErrorSpecific();
  ResultSet rs=null;

  req = "delete  FROM     PlanDeCharge WHERE     (projetMembre = "+this.id+") AND (idRoadmap = "+idRoadmap+")";
  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeEquipe",""+this.id);

   return myError;

}
  public ErrorSpecific bd_InsertChargesIfNull(String nomBase,Connexion myCnx, Statement st, int idRoadmap, int anneeRef, int semaine, String transaction){
  ErrorSpecific myError = new ErrorSpecific();
  ResultSet rs=null;

  String isCharged = this.getisCharged(nomBase, myCnx, st, idRoadmap);
  if (isCharged.equals(""))
  {
     String req = "INSERT PlanDeCharge  ( nomProjet,projetMembre, semaine, Charge, anneeRef, idRoadmap) VALUES ('',"+this.id+","+semaine+",'0.1'"+",'"+anneeRef+"'"+",'"+idRoadmap+"'"+")";
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeEquipe",""+this.id);
     if (myError.cause == -1) return myError;      
  }

   return myError;

}
  
  public ErrorSpecific bd_InsertOneCharge(String nomBase,Connexion myCnx, Statement st, String transaction, int idRoadmap, float charge, int semaine, int anneeRef){
  ErrorSpecific myError = new ErrorSpecific();
  ResultSet rs=null;
  
 String req = "delete  FROM    PlanDeCharge WHERE (projetMembre = "+this.id+") AND   (idRoadmap = "+idRoadmap+") AND   (semaine = "+semaine+") AND (anneeRef = "+anneeRef+")";
 myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeCompetence",""+this.id);
 if (myError.cause == -1) return myError;

      req = "INSERT PlanDeCharge  ( nomProjet,projetMembre, semaine, Charge, anneeRef, idRoadmap, etat) VALUES ('',"+this.id+","+semaine+",'"+charge+"','"+anneeRef+"'"+",'"+idRoadmap+"'"+",'1'"+")";
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertOneCharge",""+this.id);
     if (myError.cause == -1) return myError; 

   return myError;

}
  
  public ErrorSpecific bd_InsertOneChargeAction(String nomBase,Connexion myCnx, Statement st, String transaction, int idAction, float charge, int semaine, int anneeRef){
  ErrorSpecific myError = new ErrorSpecific();
  ResultSet rs=null;
  //  projetMembre, semaine, Charge, anneeRef, idAction

 String req = "delete  FROM    Consomme WHERE (projetMembre = "+this.id+") AND   (idAction = "+idAction+") AND   (semaine = "+semaine+") AND (anneeRef = "+anneeRef+")";
 myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeCompetence",""+this.id);
 if (myError.cause == -1) return myError; 
 
      req = "INSERT Consomme  ( projetMembre, semaine, Charge, anneeRef, idAction) VALUES ("+this.id+","+semaine+",'"+charge+"','"+anneeRef+"'"+",'"+idAction+"'"+")";
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertOneCharge",""+this.id);
     if (myError.cause == -1) return myError; 

   return myError;

}   
  
 public ErrorSpecific bd_InsertListeCompetence(String nomBase,Connexion myCnx, Statement st, String transaction){
 ErrorSpecific myError = new ErrorSpecific();
 ResultSet rs=null;
 req = "delete  FROM    CollaborateurCompetence WHERE (idCollaborateur = "+this.id+")";

 myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeCompetence",""+this.id);
 if (myError.cause == -1) return myError;

  for (int i=0; i < this.ListeCompetences.size(); i++)
  {
    Competence theCompetence = (Competence)this.ListeCompetences.elementAt(i);
     String req = "INSERT CollaborateurCompetence (idCollaborateur, idCompetence, note) VALUES ("+this.id+","+theCompetence.theLogiciel.id+","+theCompetence.note+")";
     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeCompetence",""+this.id);
     if (myError.cause == -1) return myError;
  }

  return myError;

}

  public  boolean isConnected2(String nomBase,Connexion myCnx, Statement st, int minutes , String Machine){
    //Construction du tableau de valeur des services, organis�s par directions
    boolean isCnx=false;
    int nb=0;
    req = "SELECT     COUNT(*) as nb";
    req += " FROM         StatConnexion LEFT OUTER JOIN";
    req += "                       nbCnxByMembre ON StatConnexion.UserName = nbCnxByMembre.UserName";
    req += " WHERE     (GETDATE() - StatConnexion.majCnx < CONVERT(DATETIME, '1900-01-01 00:"+minutes+":00', 102))";
    req += " AND StatConnexion.UserName='"+this.Login+"'";
    req += " AND etat = 'connecte'";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try { while(rs.next()) {
        nb =rs.getInt("nb"); // nom du ST
       
                  }	} catch (SQLException s) {s.getMessage();}
    
    if (nb > 0) isCnx = true;
    return isCnx; 
} 
  
  public  boolean getPeriod(String nomBase,Connexion myCnx, Statement st, String mode, String str_nbJour, String start_date){
    
    if (mode.equals("NORMAL")) return true;
    int nb=0;
    try{
        nb= Integer.parseInt(str_nbJour);
    }
    catch (Exception s) {
        nb=0;
    }
    
    Date currentDate = new Date();
    long l_currentDate = currentDate.getTime();
    
    Date endDate = null;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");  
    
    try {
    endDate = simpleDateFormat.parse(start_date);
    System.out.println(endDate);
    } catch (Exception e) {e.printStackTrace();}    
    
    long l_endDate = endDate.getTime() ;
    System.out.println("l_startDate="+l_endDate);
    long delta = (long)nb * (long)24 * (long)3600 * (long)1000 ;
    System.out.println("delta="+delta);
    l_endDate += delta;
    
    System.out.println("l_endDate="+l_endDate);
    System.out.println("l_currentDate="+l_currentDate);
    
    
    if (l_endDate >= l_currentDate)
        return true;
    else
        return false;
    
}   
  public  boolean isConnected(String nomBase,Connexion myCnx, Statement st, int minutes , String Machine){
    //Construction du tableau de valeur des services, organis�s par directions
    boolean isCnx=false;
    int nb=0;
    req = "SELECT     COUNT(*) as nb";
    req += " FROM         StatConnexion LEFT OUTER JOIN";
    req += "                       nbCnxByMembre ON StatConnexion.UserName = nbCnxByMembre.UserName";
    req += " WHERE     (GETDATE() - StatConnexion.majCnx < CONVERT(DATETIME, '1900-01-01 00:"+minutes+":00', 102))";
    req += " AND StatConnexion.UserName='"+this.Login+"'";
    req += " AND StatConnexion.Carto <>'"+Machine+"'";
    req += " AND etat = 'connecte'";
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);

    try { while(rs.next()) {
        nb =rs.getInt("nb"); // nom du ST
       
                  }	} catch (SQLException s) {s.getMessage();}
    
    if (nb > 0) isCnx = true;
    return isCnx;
}  
  
  public  void disconnect(String nomBase,Connexion myCnx, Statement st){
    req += "UPDATE StatConnexion";
    req += " SET etat= 'deconnecte'";
    req += " WHERE StatConnexion.UserName='"+this.Login+"'";
    
    myCnx.ExecReq(st, nomBase, req);

}
  
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("prenom="+this.prenom);
    System.out.println("fonction="+this.fonction);
    System.out.println("service="+this.service);
    System.out.println("service_old="+this.service_old);
    System.out.println("mail="+this.mail);
    System.out.println("Login="+this.Login);
    System.out.println("isProjet="+this.isProjet);
    System.out.println("isTest="+this.isTest);
    System.out.println("isInterne="+this.isInterne);
    System.out.println("AO="+this.AO);
    System.out.println("Prix="+this.Prix);
    System.out.println("dateEntree="+this.dateEntree);
    System.out.println("dateSortie="+this.dateSortie);
    System.out.println("intitulePoste="+this.intitulePoste);
    System.out.println("niveau="+this.niveau);
    System.out.println("Mission="+this.Mission);
    System.out.println("societe="+this.societe);
    System.out.println("niveau="+this.niveau);
    System.out.println("maxProjets="+this.maxProjets);
    System.out.println("Telephone_M="+this.Telephone_M);
    System.out.println("Telephone_B="+this.Telephone_B);
    System.out.println("Fax="+this.Fax);


    System.out.println("Admin="+this.Admin);
    System.out.println("Manager="+this.Manager);
    System.out.println("MOA="+this.MOA);
    System.out.println("MOE="+this.MOE);

    System.out.println("couleurEB="+this.couleurEB);
    System.out.println("couleurDEV="+this.couleurDEV);
    System.out.println("couleurTEST="+this.couleurTEST);
    System.out.println("couleurMES="+this.couleurMES);

    System.out.println("etp="+this.etp);
    System.out.println("profilST="+this.profilST);
    System.out.println("isAdmin="+this.isAdmin);
    System.out.println("isPo="+this.isPo);
    System.out.println("isBrief="+this.isBrief);
    System.out.println("GOUVERNANCE="+this.GOUVERNANCE);

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
    st2 = myCnx.Connect();
    //Collaborateur theCollaborateur = new Collaborateur("dcazes");
    //theCollaborateur.nom = "cazes";
    //theCollaborateur.prenom = "daniel";
    //theCollaborateur.service = 93;
    //theCollaborateur.getAllFromBd(myCnx.nomBase, myCnx, st);
    //theCollaborateur.bd_Insert(myCnx.nomBase, myCnx, st, "xxx");
    //theCollaborateur.getListeProjets(myCnx.nomBase, myCnx, st,2010,st2);
    //theCollaborateur.dump();
    //theCollaborateur.getListeSt(myCnx.nomBase,myCnx,  st);
    //theCollaborateur.getListeRoadmap(myCnx.nomBase,myCnx,  st);
    //theCollaborateur.getListeActions(myCnx.nomBase,myCnx,  st);
    //theCollaborateur.getChargeByWeekVacation(myCnx.nomBase, myCnx, st,2010, 27);
    //theCollaborateur.getListeFavoris(myCnx.nomBase,myCnx,  st);
    //Collaborateur theCollaborateur = new Collaborateur(3147);
    //theCollaborateur.bd_Delete(myCnx.nomBase, myCnx, st, "xxx");
    //System.out.println(theCollaborateur.chaine_a_ecrire);
    //theCollaborateur.getRoles(myCnx.nomBase,myCnx,  st);
    //theCollaborateur.getListeProjetsRespMoa(myCnx.nomBase,myCnx,  st, 2010);
    //theCollaborateur.getListeAllProjets(myCnx.nomBase,myCnx,  st, theCollaborateur.MOA);
    //myCnx.trace("0123@"," theCollaborateur.ListeProjets.size()",""+theCollaborateur.nom+"::"+ theCollaborateur.ListeProjets.size());

    //theCollaborateur.getProfilSt();
    //theCollaborateur.getListeProjetsNonReccurrent(myCnx.nomBase,myCnx,  st, 2012);
    //theCollaborateur.getListeActionsByRoadmap(myCnx.nomBase,myCnx,  st, 41954);
    Collaborateur theCollaborateur = null;
    String originalLogin ="";
    theCollaborateur = new Collaborateur("jjakubow");
    theCollaborateur.getAllFromBd(myCnx.nomBase, myCnx, st);
    originalLogin = theCollaborateur.getOriginalLogin(myCnx.nomBase, myCnx, st);
    //myCnx.trace("---","originalLogin",""+originalLogin);

    theCollaborateur = new Collaborateur("STLAUREN");
    theCollaborateur.getAllFromBd(myCnx.nomBase, myCnx, st);
    originalLogin = theCollaborateur.getOriginalLogin(myCnx.nomBase, myCnx, st);
    //myCnx.trace("---","originalLogin",""+originalLogin);

    theCollaborateur = new Collaborateur("RRAD0001");
    theCollaborateur.getAllFromBd(myCnx.nomBase, myCnx, st);
    originalLogin = theCollaborateur.getOriginalLogin(myCnx.nomBase, myCnx, st);
    //myCnx.trace("---","originalLogin",""+originalLogin);

    theCollaborateur = new Collaborateur("leomulle");
    theCollaborateur.getAllFromBd(myCnx.nomBase, myCnx, st);
    originalLogin = theCollaborateur.getOriginalLogin(myCnx.nomBase, myCnx, st);
    //myCnx.trace("---","originalLogin",""+originalLogin);

    theCollaborateur = new Collaborateur("RRAD0002");
    theCollaborateur.getAllFromBd(myCnx.nomBase, myCnx, st);
    originalLogin = theCollaborateur.getOriginalLogin(myCnx.nomBase, myCnx, st);
    //myCnx.trace("---","originalLogin",""+originalLogin);

    //theCollaborateur.getListeProjetsOuverts(myCnx.nomBase, myCnx, st,2012,st2);
    //theCollaborateur.getListeCollaborateurByEquipe(myCnx.nomBase, myCnx, st);
    //theCollaborateur.idVersionStPO(myCnx.nomBase,myCnx,  st, 2012);
    myCnx.Unconnect(st);
  }
}

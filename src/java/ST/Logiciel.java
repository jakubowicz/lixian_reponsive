package ST; 

import General.Utils;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import java.util.Vector;
import OM.OM;
import Organisation.Collaborateur;
import Organisation.Competence;
import accesbase.ErrorSpecific;
import accesbase.transaction;

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
public class Logiciel {
 public int id;
 public int idST_MW = -1;
 public String nom="";
 public int idVersion;
 public String nomVersion ="";
 public int nbLicences;
 public int LogicielMiddleWare;
 public String typeLogiciel="";
 public String Archi="";
 public int idConstructeur;

 private static String req="";
 public Vector ListeSt = new Vector(6);
 public Vector ListeCompetences = new Vector(6);

 public static Vector ListeLogiciel = new Vector(10);
 public Vector ListeVersionTechno = new Vector(6);

 public Logiciel(){

 }

 public Logiciel (int id, int LogicielMiddleWare, String nom){
    this.id = id;
    this.LogicielMiddleWare = LogicielMiddleWare;
    this.nom = nom;
}


 public Logiciel(
      int id,
      String nom,
      int idVersion,
      String nomVersion,
      int nbLicences,
      int LogicielMiddleWare,
      String typeLogiciel,
      String Archi) {

   this.id=id;
   this.nom=nom;
   this.idVersion=idVersion;
   this.nomVersion=nomVersion;
   this.nbLicences=nbLicences;
   this.LogicielMiddleWare=LogicielMiddleWare;
   this.typeLogiciel=typeLogiciel;
   this.Archi=Archi;


  }

  public void getListeVersionTechno(String nomBase,Connexion myCnx, Statement st){

    String req= "SELECT idVersionMW, nomVersionMW FROM VersionMW WHERE mwVersionMW = "+this.id;
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    versionTechno theversionTechno = null;

    try {
       while (rs.next()) {
         theversionTechno = new versionTechno();
         theversionTechno.id = rs.getInt("idVersionMW");
         theversionTechno.nom = rs.getString("nomVersionMW");

         this.ListeVersionTechno.addElement(theversionTechno);
       }
        } catch (SQLException s) {s.getMessage();}

 }
  public void getAllFromBd(String nomBase,Connexion myCnx,Statement st ){
    String req = "SELECT      idMiddleware,LogicielMiddleWare, nomMiddleware, constructeurMiddleware, descMiddleWare, ratioPf";
    req+=" FROM         Middleware";
    req+=  " where idMiddleware ="+this.id;
    
    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    try {
       while (rs.next()) {
         int id= rs.getInt("idMiddleware");
         this.LogicielMiddleWare = rs.getInt("LogicielMiddleWare");
         this.nom = rs.getString("nomMiddleware");
         this.idConstructeur = rs.getInt("constructeurMiddleware");
       }
        } catch (SQLException s) {s.getMessage();}

}  public int getIdVersionByIdVersionSt(String nomBase,Connexion myCnx, Statement st, int idVersionSt){
    ResultSet rs;
    int idVersion = -1;
    req = "SELECT     VersionSt.idVersionSt, VersionMW.idVersionMW, VersionMW.nomVersionMW, VersionMW.mwVersionMW, ST_MW.mwST_MW, ST_MW.nbLicencesST_MW, ST_MW.Archi, ST_MW.idST_MW";
    req+=" FROM         VersionMW INNER JOIN";
    req+="                       ST_MW ON VersionMW.idVersionMW = ST_MW.mwST_MW INNER JOIN";
    req+="                       VersionSt ON ST_MW.versionStST_MW = VersionSt.idVersionSt";
    req+=" WHERE     (VersionSt.idVersionSt = "+idVersionSt+") AND (VersionMW.mwVersionMW = "+this.id+")";
    rs = myCnx.ExecReq(st, nomBase, req);
    try {rs.next(); 
        idVersion =rs.getInt("idVersionMW");
        this.nbLicences =rs.getInt("nbLicencesST_MW");
        this.Archi =rs.getString("Archi");
        this.idST_MW =rs.getInt("idST_MW");
    } catch (SQLException s) {s.getMessage();}
    return idVersion;
  }

      public String bd_InsertVersions(String nomBase,Connexion myCnx, Statement st, String transaction){
        ResultSet rs = null;
        String chaine_a_ecrire = "";
        int nb=0;

        // Bouclage sur la liste des versions du logiciel
        for (int i=0; i < this.ListeVersionTechno.size(); i++)
        {
          versionTechno theversionTechno = (versionTechno)this.ListeVersionTechno.elementAt(i);
          req="SELECT     COUNT (*) as nb ";
          req+="    FROM         Middleware INNER JOIN";
          req+="             VersionMW ON Middleware.idMiddleware = VersionMW.mwVersionMW";
          req+=" WHERE     (Middleware.nomMiddleware = '"+this.nom+"') AND (VersionMW.nomVersionMW = '"+theversionTechno.nom+"')";
          rs = myCnx.ExecReq(st,nomBase,req);
          try {
            while(rs.next()) {
              nb = rs.getInt(1);
            }
          } catch (SQLException s) {s.getMessage();}

          // Si Nouvelle
          if (nb == 0)
          {
            // l'ins�rer dans la table des versions
            req = "INSERT VersionMW (nomVersionMW, mwVersionMW) VALUES ('"+theversionTechno.nom+"', "+this.id+")";
            if (myCnx.ExecUpdate(st,myCnx.nomBase,req,true,transaction) == -1)
                {
                  myCnx.trace(nomBase,"*** ERREUR *** req",req);
                  return req;
                                  }
          }
        }
        // Fin Bouclage

        // �tablir la liste des versions supprim�es
        String liste2check="";
        for (int i=0; i < this.ListeVersionTechno.size(); i++)
        {
          versionTechno theversionTechno = (versionTechno)this.ListeVersionTechno.elementAt(i);
          if (i==0)
            liste2check+="'"+theversionTechno.nom+"'";
          else
            liste2check+=","+"'"+theversionTechno.nom+"'";
        }
        req = "SELECT     VersionMW.idVersionMW, VersionMW.nomVersionMW";
        req+="     FROM         Middleware INNER JOIN";
        req+="               VersionMW ON Middleware.idMiddleware = VersionMW.mwVersionMW";
        req+=" WHERE     (Middleware.nomMiddleware = '"+this.nom+"') ";
        if (this.ListeVersionTechno.size() > 0)
        {
          req += " and VersionMW.nomVersionMW NOT in (" + liste2check + ")";
        }
        rs = myCnx.ExecReq(st,nomBase,req);
             // Bouclage sur la liste des versions � supprimer
             Vector ListeVersion2Delete= new Vector(10);
             try {
                  while(rs.next())
                  {
                    int idVersionMW = rs.getInt("idVersionMW");
                    String nomVersionMW = rs.getString("nomVersionMW");
                    versionTechno theversionTechno = new versionTechno();
                    theversionTechno.id = idVersionMW;
                    theversionTechno.nom = nomVersionMW;
                    ListeVersion2Delete.addElement(theversionTechno);
                  }
               } catch (SQLException s) {s.getMessage();}
                // si un ST y fait r�f�rence

                Vector ListeSt2Delete= new Vector(10);
                for (int i=0; i < ListeVersion2Delete.size(); i++)
                {
                  versionTechno theversionTechno = (versionTechno)ListeVersion2Delete.elementAt(i);
                  req = "SELECT     VersionSt.idVersionSt";
                  req += "     FROM         VersionSt INNER JOIN";
                  req +=
                      "       ST_MW ON VersionSt.idVersionSt = ST_MW.versionStST_MW INNER JOIN";
                  req +=
                      "       VersionMW ON ST_MW.mwST_MW = VersionMW.idVersionMW";
                  req += " WHERE     (VersionMW.idVersionMW = "+theversionTechno.id+")";
                  rs = myCnx.ExecReq(st,nomBase,req);
                  try {
                       while(rs.next())
                       {
                         int idVersionSt = rs.getInt("idVersionSt");
                         ST theSt = new ST(idVersionSt, "idVersionSt");
                         theSt.idSt=theversionTechno.id;
                         ListeSt2Delete.addElement(theSt);
                       }
               } catch (SQLException s) {s.getMessage();}
                }

                // supprimer le lien entre ce ST et cette version
                for (int i=0; i < ListeSt2Delete.size(); i++)
                {
                  ST theST = (ST)ListeSt2Delete.elementAt(i);
                  req="DELETE";
                  req+="    FROM         ST_MW";
                  req+=" WHERE     (mwST_MW = "+theST.idSt+") AND (versionStST_MW = "+theST.idVersionSt+")";

                      if (myCnx.ExecUpdate(st,myCnx.nomBase,req,true,transaction) == -1)
                          {
                            myCnx.trace(nomBase,"*** ERREUR *** req",req);
                            return req;
                          }
                }
                // supprimer la version
                for (int i=0; i < ListeVersion2Delete.size(); i++)
                {
                  versionTechno theversionTechno = (versionTechno)ListeVersion2Delete.elementAt(i);
                  req="DELETE";
                  req+="    FROM         VersionMW";
                  req+=" WHERE     idVersionMW = "+theversionTechno.id+"";

                      if (myCnx.ExecUpdate(st,myCnx.nomBase,req,true,transaction) == -1)
                          {
                            myCnx.trace(nomBase,"*** ERREUR *** req",req);
                            return req;
                          }
                }
                // Fin Bouclage

        return "OK";
      }

      public ErrorSpecific bd_insert(String nomBase,Connexion myCnx, Statement st, String transaction){
        ResultSet rs=null;
        ErrorSpecific myError = new ErrorSpecific();

        boolean Existe=false;

       req = "INSERT Middleware (nomMiddleware, LogicielMiddleWare) VALUES ('"+this.nom+"',"+this.LogicielMiddleWare+")";
        myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_insert",""+this.id);
        if (myError.cause == -1) return myError;
          //On r�cup�re l'id du middleware qui vient d'�tre cr�� pour d�finir son constructeur et lui associer ses versions
          int idMiddleware = 0;
          String req = "EXEC MW_SelectIdMiddleware '"+this.nom+"'";
          rs = myCnx.ExecReq(st, nomBase, req);
          try {rs.next(); this.id = Integer.parseInt(rs.getString(1));} catch (SQLException s) {s.getMessage();}
          if (this.idConstructeur != -1) 
            req = "UPDATE Middleware SET constructeurMiddleware=" + this.idConstructeur + " WHERE idMiddleware=" + this.id;
          else
            req = "UPDATE Middleware SET constructeurMiddleware=NULL" + " WHERE idMiddleware=" + this.id;            
           
          myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_insert",""+this.id);

                   
         return myError;


  }

  public ErrorSpecific bd_update(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();

    req = "UPDATE Middleware SET nomMiddleware='"+this.nom+"', constructeurMiddleware="+this.idConstructeur+" WHERE idMiddleware"+""+"="+this.id;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);

    return myError;
  }

  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();

    // �tablir la liste des versions supprim�es
    String liste2check="";
    for (int i=0; i < this.ListeVersionTechno.size(); i++)
    {
      versionTechno theversionTechno = (versionTechno)this.ListeVersionTechno.elementAt(i);
      if (i==0)
        liste2check+="'"+theversionTechno.nom+"'";
      else
        liste2check+=","+"'"+theversionTechno.nom+"'";
    }
    req = "SELECT     VersionMW.idVersionMW, VersionMW.nomVersionMW";
    req+="     FROM         Middleware INNER JOIN";
    req+="               VersionMW ON Middleware.idMiddleware = VersionMW.mwVersionMW";
    req+=" WHERE     (Middleware.nomMiddleware = '"+this.nom+"') ";
    if (this.ListeVersionTechno.size() > 0)
    {
      //req += " and VersionMW.nomVersionMW NOT in (" + liste2check + ")";
    }
    rs = myCnx.ExecReq(st,nomBase,req);
         // Bouclage sur la liste des versions � supprimer
         Vector ListeVersion2Delete= new Vector(10);
         try {
              while(rs.next())
              {
                int idVersionMW = rs.getInt("idVersionMW");
                String nomVersionMW = rs.getString("nomVersionMW");
                versionTechno theversionTechno = new versionTechno();
                theversionTechno.id = idVersionMW;
                theversionTechno.nom = nomVersionMW;
                ListeVersion2Delete.addElement(theversionTechno);
              }
           } catch (SQLException s) {s.getMessage();}
            // si un ST y fait r�f�rence

            Vector ListeSt2Delete= new Vector(10);
            for (int i=0; i < ListeVersion2Delete.size(); i++)
            {
              versionTechno theversionTechno = (versionTechno)ListeVersion2Delete.elementAt(i);
              req = "SELECT     VersionSt.idVersionSt";
              req += "     FROM         VersionSt INNER JOIN";
              req +=
                  "       ST_MW ON VersionSt.idVersionSt = ST_MW.versionStST_MW INNER JOIN";
              req +=
                  "       VersionMW ON ST_MW.mwST_MW = VersionMW.idVersionMW";
              req += " WHERE     (VersionMW.idVersionMW = "+theversionTechno.id+")";
              rs = myCnx.ExecReq(st,nomBase,req);
              try {
                   while(rs.next())
                   {
                     int idVersionSt = rs.getInt("idVersionSt");
                     ST theSt = new ST(idVersionSt, "idVersionSt");
                     theSt.idSt=theversionTechno.id;
                     ListeSt2Delete.addElement(theSt);
                   }
           } catch (SQLException s) {s.getMessage();}
            }

            // supprimer le lien entre ce ST et cette version
            for (int i=0; i < ListeSt2Delete.size(); i++)
            {
              ST theST = (ST)ListeSt2Delete.elementAt(i);
              req="DELETE";
              req+="    FROM    ST_MW";
              req+=" WHERE     (mwST_MW = "+theST.idSt+") AND (versionStST_MW = "+theST.idVersionSt+")";
              myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
              if (myError.cause == -1) return myError;
            }
            // supprimer la version
            for (int i=0; i < ListeVersion2Delete.size(); i++)
            {
              versionTechno theversionTechno = (versionTechno)ListeVersion2Delete.elementAt(i);
              req="DELETE";
              req+="    FROM         VersionMW";
              req+=" WHERE     idVersionMW = "+theversionTechno.id+"";

              myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
              if (myError.cause == -1) return myError;
            }


    req="DELETE FROM         Middleware WHERE     (idMiddleware = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
    if (myError.cause == -1) return myError;

return myError;

  }

  public String bd_Delete2(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    String chaine_a_ecrire="";

    String reqMajTechno = "";
    req = "SELECT idVersionMW FROM VersionMW WHERE mwVersionMW="+this.id;
    rs = myCnx.ExecReq(st, nomBase, req);
    int[] idVersionMW = new int[100];
    int i=0;
    try { while(rs.next()) { idVersionMW[i] = Integer.parseInt(rs.getString(1)); i++; } } catch (SQLException s) {s.getMessage();}

    for (int j=0; j<i; j++) {

      req="DELETE ST_MW WHERE mwST_MW="+idVersionMW[j];
      if (myCnx.ExecUpdate(st,myCnx.nomBase,req,true,transaction) == -1)
          {
            myCnx.trace(nomBase,"*** ERREUR *** req",req);
            return req;
          }

      }

    req="DELETE VersionMW WHERE mwVersionMW="+this.id;
    if (myCnx.ExecUpdate(st,myCnx.nomBase,req,true,transaction) == -1)
        {
          myCnx.trace(nomBase,"*** ERREUR *** req",req);
          return req;
          }

          String req = "DELETE "+"Middleware"+" WHERE id"+"Middleware"+"="+this.id;

          if (myCnx.ExecUpdate(st,myCnx.nomBase,req,true,transaction) == -1)
              {
                myCnx.trace(nomBase,"*** ERREUR *** req",req);
                return req;
              }

          return "OK";

  }

  public static void getListeTypeLogiciel(String nomBase,Connexion myCnx, Statement st){

    String req="SELECT idLogiciel, nomLogiciel FROM Logiciel ORDER BY nomLogiciel";
    ListeLogiciel.removeAllElements();

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Logiciel theLogiciel = null;
    try {
       while (rs.next()) {
         theLogiciel = new Logiciel();
         theLogiciel.id = rs.getInt("idLogiciel");
         theLogiciel.nom = rs.getString("nomLogiciel");

         ListeLogiciel.addElement(theLogiciel);
       }
        } catch (SQLException s) {s.getMessage();}

  }

  public static void getListeLogiciel(String nomBase,Connexion myCnx, Statement st){

    String req="EXEC LISTEMW_SelectVersionMW";
    ListeLogiciel.removeAllElements();

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Logiciel theLogiciel = null;
    try {
       while (rs.next()) {
         int mwVersionMW = rs.getInt(1);
         int idVersionMW = rs.getInt(2);
         String nomVersionMW = rs.getString(3);
         int LogicielMiddleware = rs.getInt(4);

         theLogiciel = new Logiciel(idVersionMW,LogicielMiddleware,nomVersionMW);
         theLogiciel.idVersion= mwVersionMW;
         //theST.dump();
         ListeLogiciel.addElement(theLogiciel);
       }
        } catch (SQLException s) {s.getMessage();}

  }

  public void getListeSt(String nomBase,Connexion myCnx, Statement st){

    if (this.id == -1)
    {
    req = "SELECT DISTINCT  SI.nomSI,St.nomSt, VersionMW.nomVersionMW, VersionSt.idVersionSt";
    req +=" FROM         ST_MW INNER JOIN";
    req +="                       VersionSt ON ST_MW.versionStST_MW = VersionSt.idVersionSt INNER JOIN";
    req +="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req +="                       Etat ON VersionSt.etatVersionSt = Etat.idEtat INNER JOIN";
    req +="                       VersionMW ON ST_MW.mwST_MW = VersionMW.idVersionMW INNER JOIN";
    req +="                      Middleware ON VersionMW.mwVersionMW = Middleware.idMiddleware INNER JOIN";
    req +="                       SI ON VersionSt.siVersionSt = SI.idSI";
    req +=" WHERE     (Middleware.nomMiddleware = '"+this.nom+"')  AND (VersionSt.etatFicheVersionSt = 3) AND ";
    req +="                       (VersionSt.etatVersionSt !=4)";
    req +=" ORDER BY SI.nomSI, VersionMW.nomVersionMW  ";
    }
    else
    {
    req = "SELECT DISTINCT  SI.nomSI,St.nomSt, VersionMW.nomVersionMW, VersionSt.idVersionSt";
    req +=" FROM         ST_MW INNER JOIN";
    req +="                       VersionSt ON ST_MW.versionStST_MW = VersionSt.idVersionSt INNER JOIN";
    req +="                       St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
    req +="                       Etat ON VersionSt.etatVersionSt = Etat.idEtat INNER JOIN";
    req +="                       VersionMW ON ST_MW.mwST_MW = VersionMW.idVersionMW INNER JOIN";
    req +="                      Middleware ON VersionMW.mwVersionMW = Middleware.idMiddleware INNER JOIN";
    req +="                       SI ON VersionSt.siVersionSt = SI.idSI";
    req +=" WHERE     (Middleware.nomMiddleware = '"+this.nom+"') AND (VersionSt.etatFicheVersionSt = 3) AND ";
    req +="                       (VersionSt.etatVersionSt !=4)  AND (VersionMW.idVersionMW = '"+this.id+"')";
    req +=" ORDER BY SI.nomSI, VersionMW.nomVersionMW  ";
}

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    ST theST = null;
    try {
       while (rs.next()) {

         String nomSi = rs.getString(1);
         theST = new ST(rs.getString(2));
         theST.nomSi = nomSi;
         theST.nomVersionMw = rs.getString(3);
         theST.idVersionSt = rs.getInt(4);
         //theST.dump();
         this.ListeSt.addElement(theST);
       }
        } catch (SQLException s) {s.getMessage();}

 }

 public void getListeCollaborateurs(String nomBase,Connexion myCnx, Statement st){

   req = "SELECT     CollaborateurCompetence.idCollaborateur, Membre.nomMembre, Membre.prenomMembre, noteCompetence.nom, Service.nomService";
   req+="  FROM         CollaborateurCompetence INNER JOIN";
   req+="                       Membre ON CollaborateurCompetence.idCollaborateur = Membre.idMembre INNER JOIN";
   req+="                       Middleware ON CollaborateurCompetence.idCompetence = Middleware.idMiddleware INNER JOIN";
   req+="                       Logiciel ON Middleware.LogicielMiddleWare = Logiciel.idLogiciel INNER JOIN";
   req+="                       noteCompetence ON CollaborateurCompetence.note = noteCompetence.ordre INNER JOIN";
   req+="                       Service ON Membre.serviceMembre = Service.idService";

 req+="  WHERE     (Middleware.nomMiddleware = '"+this.nom+"') AND (Logiciel.nomLogiciel = '"+this.typeLogiciel+"') AND (CollaborateurCompetence.note > 0)";
 req+=" ORDER BY CollaborateurCompetence.note DESC";

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   ST theST = null;
   try {
      while (rs.next()) {

        int idCollaborateur = rs.getInt("idCollaborateur");
        Collaborateur theCollaborateur = new Collaborateur(idCollaborateur);
        theCollaborateur.nom= rs.getString("nomMembre");
        theCollaborateur.prenom= rs.getString("prenomMembre");

        Competence theCompetence = new Competence();
        theCompetence.theCollaborateur = theCollaborateur;
        theCompetence.theLogiciel = this;
        theCompetence.nomNote=rs.getString("nom");
        theCollaborateur.nomService = rs.getString("nomService");
        this.ListeCompetences.addElement(theCompetence);
      }
       } catch (SQLException s) {s.getMessage();}

 }
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("idVersion="+this.idVersion);
    System.out.println("nomVersion="+this.nomVersion);
    System.out.println("nbLicences="+this.nbLicences);
    System.out.println("LogicielMiddleWare="+this.LogicielMiddleWare);
    System.out.println("typeLogiciel="+this.typeLogiciel);
    System.out.println("Archi="+this.Archi);
    System.out.println("idConstructeur="+this.idConstructeur);
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
    Logiciel theLogiciel = new Logiciel();
    theLogiciel.id = 200;
    theLogiciel.nom = "aaaa";
    theLogiciel.getAllFromBd(myCnx.nomBase,myCnx,  st);
    //theLogiciel.dump();

    versionTechno theversionTechno = new versionTechno();
    theversionTechno.nom="1.0";
    theLogiciel.ListeVersionTechno.addElement(theversionTechno);
    theversionTechno = new versionTechno();
        theversionTechno.nom="2.0";
    theLogiciel.ListeVersionTechno.addElement(theversionTechno);


    transaction theTransaction = new transaction ("EnregTechno");
    theTransaction.begin(base,myCnx,st);

    int idTechno=theLogiciel.id;
    String action="enreg";

    //theLogiciel.bd_InsertVersions(myCnx.nomBase,myCnx,  st, theTransaction.nom);
    theLogiciel.bd_delete(myCnx.nomBase,myCnx,  st, theTransaction.nom);

    theTransaction.commit(base,myCnx,st);


    myCnx.Unconnect(st);
  }
}

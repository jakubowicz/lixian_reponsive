package Test; 

import java.util.Vector;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import ST.ST;
import java.util.Date;
import General.Utils;
import Documentation.doc;
import accesbase.ErrorSpecific;

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
public class CategorieTest {
  public int id=-1;
  public int Ordre=-1;
  public String nom="";
  public String description="";
  public int idRoadmap=-1;
  public Vector ListeTests = new Vector(10);

  private String req="";
  public String couleurCouvertureTotale="";
  public String couleurCouvertureOK="";
  public String couleurcoherenceOKKO ="";

  public CategorieTest(int id) {
    this.id=id;
  }
  
  public CategorieTest(String nom) {
    this.nom=nom;
  }
  

  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    String nom=this.nom;
    String description=this.description;

    if (this.nom != null) nom=this.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ');;
    if (this.description != null) description=this.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");

           req = "INSERT categorieTest (";
             req+="nom"+",";
             req+="description"+",";
             req+="idRoadmap"+",";
             req+="Ordre"+"";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+nom+"',";
             req+="'"+description+"',";
             req+=""+this.idRoadmap+",";
             req+=""+this.Ordre+"";
           req+=")";

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

          req = "SELECT id FROM categorieTest WHERE (idRoadmap = '"+this.idRoadmap+"') AND nom= '"+nom+"'";
         rs = myCnx.ExecReq(st, nomBase, req);
         try {rs.next(); this.id =rs.getInt("id");} catch (SQLException s) {s.getMessage();}

    return myError;
  }

  public ErrorSpecific bd_InsertTest(String nomBase,Connexion myCnx, Statement st, Statement st2,String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    int nb =0;
    for (int i=0; i < this.ListeTests.size();i++)
    {
      Test theTest = (Test)this.ListeTests.elementAt(i);
      nb = 0;

      int id=-1;

      int Ordre = theTest.Ordre;
      String nom=theTest.nom;
      String description=theTest.description;
      String miseEnOeuvre=theTest.miseEnOeuvre;

      if (theTest.nom != null) nom=theTest.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
      if (theTest.description != null) description=theTest.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
      if (theTest.miseEnOeuvre != null) miseEnOeuvre=theTest.miseEnOeuvre.replaceAll("\u0092","'").replaceAll("'","''").replaceAll("\r","");


      if (theTest.id == -1)
      {

           req = "INSERT Tests (";
             req+="idCategorie"+",";
             req+="nom"+",";
             req+="description"+",";
             req+="miseEnOeuvre"+",";
             req+="Ordre"+",";
             req+="FilenameMiseEnOeuvre";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+=""+this.id+",'";
             req+=nom+"','";
             req+=description+"','";
             req+=miseEnOeuvre+"','";
             req+=theTest.Ordre+"','";
             req+=theTest.FilenameMiseEnOeuvre.chemin+"'";

           req+=")";

           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertTest",""+this.id);
           if (myError.cause == -1) return myError;

           req = "SELECT id, nom FROM Tests WHERE     (idCategorie = "+this.id+") ORDER BY id DESC";
           rs = myCnx.ExecReq(st, nomBase,  req);
           try { rs.next(); theTest.id= rs.getInt("id"); } catch (SQLException s) {s.getMessage();}

      }
      else
      {
        req = "UPDATE Tests SET ";
        req+="idCategorie='"+this.id+"',";
        req+="nom='"+nom+"',";
        req+="description='"+description+"',";
        req+="miseEnOeuvre='"+miseEnOeuvre+"',";
        req+="Ordre='"+Ordre+"',";
        req+="FilenameMiseEnOeuvre='"+theTest.FilenameMiseEnOeuvre.chemin+"'";

        req+=" WHERE idCategorie="+this.id;
        req+=" AND id='"+theTest.id+"'";

        myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertTest",""+this.id);
        if (myError.cause == -1) return myError;
      }

    }

    Vector ListeTests2Delete = new Vector(10);
    String theNom="";
    int theId=-1;
    req = "SELECT id, nom FROM Tests WHERE     (idCategorie = "+this.id+")";
    rs = myCnx.ExecReq(st, nomBase, req);
    try {
      while (rs.next()) {
        theId= rs.getInt("id");
        //myCnx.trace("@5678--------------", "theId", "" + theId);
        theNom= rs.getString("nom").replaceAll("\r","").replaceAll("\u0092","'");
        if (!isInListe(theId))
        {
          ListeTests2Delete.addElement(new Integer(theId));

        }
      }
    }
            catch (SQLException s) {s.getMessage();}

    for (int i=0; i < ListeTests2Delete.size(); i++)
    {
      theId = (Integer)ListeTests2Delete.elementAt(i);

      req = "DELETE FROM resultatTest WHERE (idTest  = " + theId + ")";
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertTest",""+this.id);
      if (myError.cause == -1) return myError;

      req = "DELETE FROM Tests WHERE (id = " + theId + ") ";
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertTest",""+this.id);
      if (myError.cause == -1) return myError;

      }
    return myError;
  }


  private boolean isInListe(String Test){
    boolean trouve = false;

    for (int i=0; i < this.ListeTests.size();i++)
    {
      Test theTest = (Test)this.ListeTests.elementAt(i);
      if (Test.equals(theTest.nom))
      {
        return true;
      }
    }

    return false;
  }

  private boolean isInListe(int theId){
    boolean trouve = false;

    for (int i=0; i < this.ListeTests.size();i++)
    {
      Test theTest = (Test)this.ListeTests.elementAt(i);
      if (theTest.id == theId)
      {
        return true;
      }
    }

    return false;
  }

  public ErrorSpecific  bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    String nom=this.nom;
    String description=this.description;

    if (this.nom != null) nom=this.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ');
    if (this.description != null) description=this.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");


    req = "UPDATE categorieTest SET ";
    req+="nom='"+nom+"',";
    req+="description='"+description+"',";
    req+="idRoadmap="+this.idRoadmap+",";
    req+="Ordre="+this.Ordre+"";

    req+=" WHERE id="+this.id;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
    if (myError.cause == -1) return myError;

    return myError;
  }

  public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    req="DELETE FROM Tests   WHERE  (idCategorie = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

    req="DELETE FROM CategorieTest   WHERE  (id = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

    return myError;
  }


  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;


       req = "SELECT     nom, description, idRoadmap, id, Ordre FROM    categorieTest WHERE     (id = "+this.id+")";


     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       this.nom = rs.getString("nom");
       this.description = rs.getString("description");
       this.idRoadmap = rs.getInt("idRoadmap");
        this.Ordre = rs.getInt("Ordre");


       } catch (SQLException s) {s.getMessage(); }


  }

  public int readResultByState(String nomBase,Connexion myCnx, Statement st, String etat, int idCampagne){
    int nb = 0;
    ResultSet rs;
    String req = "SELECT     COUNT(*) AS nb";
           req+=" FROM         categorieTest INNER JOIN";
           req+="            Tests ON categorieTest.id = Tests.idCategorie INNER JOIN";
           req+="            resultatTest ON Tests.id = resultatTest.idTest INNER JOIN";
           req+="            TypeEtatTest ON resultatTest.idEtat = TypeEtatTest.id";
           req+=" WHERE     (categorieTest.id = "+this.id+") AND (resultatTest.idCampagne = "+idCampagne+")";
           req+=" GROUP BY TypeEtatTest.nom";
           req+=" HAVING      (TypeEtatTest.nom = '"+etat+"')";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        nb = rs.getInt("nb");
      }
    }
            catch (SQLException s) {s.getMessage();}

    return nb;
  }

  public void readCouleurIncoherence(String nomBase,Connexion myCnx, Statement st,  int idCampagne){

    this.couleurcoherenceOKKO = "vert";

  }

  public void readCouleurByState(String nomBase,Connexion myCnx, Statement st,  int idCampagne){
    //System.out.println("readCouleurByState");
    int nb_KO = this.readResultByState( nomBase, myCnx,  st,  "KO",  idCampagne);
    //System.out.println("**nb_KO="+nb_KO);
    int nb_AR = this.readResultByState( nomBase, myCnx,  st,  "AR",  idCampagne);
    //System.out.println("**nb_AR="+nb_AR);
    int nb_AF = this.readResultByState( nomBase, myCnx,  st,  "AF",  idCampagne);
    //System.out.println("**nb_AF="+nb_AF);
    int nb_OK = this.readResultByState( nomBase, myCnx,  st,  "OK",  idCampagne);
    //System.out.println("**nb_OK="+nb_OK);
    int nb_AN = this.readResultByState( nomBase, myCnx,  st,  "AN",  idCampagne);


    int couvTotale = 0;
    if ((nb_KO+nb_AR+nb_AF+nb_OK) == 0) couvTotale =100;
    else
    couvTotale = 100 * (nb_KO+nb_AR+nb_OK)/(nb_KO+nb_AR+nb_AF+nb_OK);
    //System.out.println("couvTotale="+couvTotale);

    int couvOk = 0;
    if ((nb_KO+nb_AR+nb_AF+nb_OK) == 0) couvOk =0;
    else
     couvOk = 100 * (nb_OK)/(nb_KO+nb_AR+nb_AF+nb_OK);
    //System.out.println("couvOk="+couvOk);

    if (couvTotale == 0) this.couleurCouvertureTotale = "rouge";
    else if (couvTotale == 100) this.couleurCouvertureTotale = "vert";
    else this.couleurCouvertureTotale = "orange";

    //System.out.println("couleurCouvertureTotale="+couleurCouvertureTotale);

    if (couvOk == 0) this.couleurCouvertureOK = "rouge";
    else if (couvOk == 100) this.couleurCouvertureOK = "vert";
    else this.couleurCouvertureOK = "orange";

    if (this.ListeTests.size() == nb_AN)
    {
      this.couleurCouvertureOK = "noir";
      this.couleurCouvertureTotale = "noir";
    }

    //System.out.println("couleurCouvertureOK="+couleurCouvertureOK);

  }

  public void getListeTests(String nomBase,Connexion myCnx, Statement st, Statement st2){
    this.ListeTests.removeAllElements();
    ResultSet rs;
    String chemin = "";
    String req = "SELECT     id, nom, description, miseEnOeuvre, idCategorie, Ordre, FilenameMiseEnOeuvre FROM    Tests";
    req+=" WHERE     (Tests.idCategorie = "+this.id+") ORDER BY Ordre ASC";


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

       theTest.description = rs.getString("description");
       if ((theTest.description == null) || (theTest.description.equals("null"))) theTest.description = "";
       
        theTest.miseEnOeuvre = rs.getString("miseEnOeuvre");
        if (theTest.miseEnOeuvre == null || (theTest.miseEnOeuvre.equals("null"))) theTest.miseEnOeuvre = "";

        //theTest.dump();
        this.ListeTests.addElement(theTest);
      }
    }
            catch (SQLException s) {s.getMessage();}

  }
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("Ordre="+this.Ordre);
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("couleurCouvertureTotale="+this.couleurCouvertureTotale);
    System.out.println("couleurCouvertureOK="+this.couleurCouvertureOK);

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
    CategorieTest theCategorieTest = new CategorieTest(96);
    theCategorieTest.readCouleurByState(myCnx.nomBase, myCnx, st, 64);
    //theCategorieTest.dump();
    //theCategorieTest.getListeTests(myCnx.nomBase, myCnx, st);
    //theSt.getNoteMaj();
    //theSt.addOneRoadmap(myCnx.nomBase, myCnx, st, "18778");
    myCnx.Unconnect(st);
  }
}

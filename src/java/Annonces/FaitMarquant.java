package Annonces; 

import java.util.Vector;
import Organisation.Collaborateur;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import Composant.simpleApplet;
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
public class FaitMarquant {
  public static Vector ListeFaitMarquants = new Vector(10);
  public static Vector ListeEnquetes = new Vector(10);

  public Vector ListeAppletParams = new Vector(10);

  public String modele;
  public String icone;
  public int idPerso;
  public int idFait;
  public String nomFait;
  public String descFait;
  public String dateFaitMarquant;
  public String ProcStock;
  public String TypeCarto;
  public int Ligne;
  public int Colonne;
  public int LigneFuture;
  public int ColonneFuture;
  public String Param;
  public int idEnquete;
  public String TypePerso;
  public String jsp;
  public static String [][][] tabWebPart =new String [4][3][2];

  public String FileNews="";

  private String Type;
  String [][] color ={ {"green","solid"},{"blue","striped"},{"red","solid"}, {"yellow","striped"}, {"black","solid"}};


  public FaitMarquant() {
    this.Type = "";
  }

  public FaitMarquant(int Ligne, int Colonne) {
    this.Ligne = Ligne;
    this.Colonne = Colonne;
    this.Type = "LigneColonne";
  }

  public FaitMarquant(int idFait) {
    this.idFait = idFait;
    this.Type = "idFait";
  }


  public void getFileNews(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
     rs = myCnx.ExecReq(st, nomBase, "SELECT     FaitMarquant.descFait, Personalisation.Type FROM  FaitMarquant INNER JOIN    Personalisation ON FaitMarquant.idFaitPerso = Personalisation.idPerso WHERE     (Personalisation.Type = 'MenuDeroulant')");
     try {rs.next(); this.FileNews = rs.getString(1); } catch (SQLException s) {s.getMessage(); }
     //c1.trace(base,"2- FaitMarquant",FaitMarquant);
     this.FileNews = this.FileNews.replaceAll("�","&egrave").replaceAll("�","&eacute").replaceAll("�","&ecirc").replaceAll("�","&agrave");

  }

  public String bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){
    String req="";
    ResultSet rs=null;
    this.descFait = this.descFait.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
    req = "INSERT INTO FaitMarquant   (nomFait, descFait, dateFaitMarquant, ProcStock,  Param, Ligne, Colonne, idFaitPerso,idEnquete)  VALUES     ";
    req+="('"+nomFait+"', '"+descFait+"', '"+dateFaitMarquant+"', '"+ProcStock+"', '"+Param+"', 0, 0, '"+modele+"',"+idEnquete+")";

    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
// si enqu�te cr�er 3 enregistremenet pour le vote
//
    String TypeAnnonce="";
    req = "SELECT libelle FROM  Personalisation WHERE (idPerso ="+ modele+")";
    rs = myCnx.ExecReq(st, nomBase, req);
    try { rs.next(); TypeAnnonce = rs.getString(1); } catch (SQLException s) {s.getMessage();}

    req = "SELECT idFait FROM FaitMarquant WHERE (nomFait = '"+ nomFait+"')";
    rs = myCnx.ExecReq(st, nomBase, req);
    try { rs.next(); idEnquete = rs.getInt(1); } catch (SQLException s) {s.getMessage();}


    if (TypeAnnonce.equals("Enqu�te"))
    {
    req = "INSERT INTO Enquetes    (nom, Reponse, idEnquetePortlet,nb) VALUES     ('"+nomFait+"', 'Oui', "+idEnquete+",0)";

    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
    req = "INSERT INTO Enquetes    (nom, Reponse, idEnquetePortlet,nb) VALUES     ('"+nomFait+"', 'Indiff�rent', "+idEnquete+",0)";

    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
    req = "INSERT INTO Enquetes    (nom, Reponse, idEnquetePortlet,nb) VALUES     ('"+nomFait+"', 'Non', "+idEnquete+",0)";

    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}
}

     return "OK";
  }

  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
    String req="";
    ErrorSpecific myError = new ErrorSpecific();

    String description="";

    if (this.descFait != null)
    {
      //description = theInterface.descInterface.replaceAll("\r","").replaceAll("0x22","'").replaceAll("\u0092","'").replaceAll("'","''");
      for (int j = 0; j < this.descFait.length(); j++) {
        int c = (int)this.descFait.charAt(j);
        //System.out.println("j: "+j+"---"+theInterface.descInterface.charAt(j) + " --" +(int)theInterface.descInterface.charAt(j));
        if (c != 34) {
          description += this.descFait.charAt(j);
        }
        else {
          description += '\"';
        }
      }
      description = description.replaceAll("\r", "").replaceAll("'", "''");
    }



    req =  "UPDATE FaitMarquant SET Ligne="+Ligne+","+"Colonne="+Colonne+" WHERE Ligne="+LigneFuture+" AND Colonne="+ColonneFuture;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.Type);
    if (myError.cause == -1) return myError;

    req =  "UPDATE FaitMarquant SET nomFait='"+nomFait+"',"+"descFait='"+description+"',"+"Ligne="+LigneFuture+","+"Colonne="+ColonneFuture+","+"ProcStock='"+ProcStock+"',"+"Param='"+Param+"',"+"idFaitPerso="+modele+","+"idEnquete="+idEnquete+""+" WHERE idFait="+idFait;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.Type);
    if (myError.cause == -1) return myError;

     return myError;
  }

  public String bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs = null;

      String req = "delete from FaitMarquant where idFait="+this.idFait;
      int check = 0;
      check=myCnx.ExecUpdate(st,nomBase,req,true,transaction);
      if (check == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}


   return "OK";
}

 public void getListeAppletParams(String nomBase,Connexion myCnx, Statement st ){
   String req ="exec "+this.ProcStock;

   this.ListeAppletParams.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   Collaborateur theCollaborateur = null;
   int nb=1;
   try {
      while (rs.next()) {
        String theLabel=rs.getString(1);
        int theLabelValue=rs.getInt(2);
        String thecolor=color[(nb-1) % color.length][0];
        String theStyle=color[(nb-1) % color.length][1];
        simpleApplet thesimpleApplet = new simpleApplet(theLabel,theLabelValue,thecolor,theStyle);
        ListeAppletParams.addElement(thesimpleApplet);
        nb++;
      }
        } catch (SQLException s) {s.getMessage();}
 }

 public int getMax_ListeAppletParams(String nomBase,Connexion myCnx, Statement st ){
   int max = -1;
  String req ="exec "+this.ProcStock;

  this.ListeAppletParams.removeAllElements();

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  Collaborateur theCollaborateur = null;
  int nb=1;
  try {
     while (rs.next()) {
       String theLabel=rs.getString(1);
       int theLabelValue=rs.getInt(2);
       String thecolor=color[(nb-1) % color.length][0];
       String theStyle=color[(nb-1) % color.length][1];
       simpleApplet thesimpleApplet = new simpleApplet();
       thesimpleApplet.label = theLabel;
       thesimpleApplet.value = theLabelValue;
       if (theLabelValue > max) max=theLabelValue;

       ListeAppletParams.addElement(thesimpleApplet);
       nb++;
     }
       } catch (SQLException s) {s.getMessage();}

       return max;
}


  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;
    String req = "";
    if (this.Type.equals("LigneColonne"))
      req = "SELECT  icone,FaitMarquant.nomFait,FaitMarquant.descFait, FaitMarquant.dateFaitMarquant, FaitMarquant.ProcStock, FaitMarquant.Ligne, FaitMarquant.Colonne,   Personalisation.Type, Personalisation.jsp, Personalisation.modele,FaitMarquant.TypeCarto,FaitMarquant.Param ,FaitMarquant.idEnquete,FaitMarquant.idFaitPerso FROM FaitMarquant INNER JOIN Personalisation ON FaitMarquant.idFaitPerso = Personalisation.idPerso WHERE (Ligne = "+this.Ligne+") AND (Colonne = "+this.Colonne+")";
    else
      req = "SELECT  icone,FaitMarquant.nomFait,FaitMarquant.descFait, FaitMarquant.dateFaitMarquant, FaitMarquant.ProcStock, FaitMarquant.Ligne, FaitMarquant.Colonne,   Personalisation.Type, Personalisation.jsp, Personalisation.modele,FaitMarquant.TypeCarto,FaitMarquant.Param ,FaitMarquant.idEnquete,FaitMarquant.idFaitPerso FROM FaitMarquant INNER JOIN Personalisation ON FaitMarquant.idFaitPerso = Personalisation.idPerso WHERE FaitMarquant.idFait="+this.idFait;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();
      this.icone = rs.getString("icone");
      this.nomFait = rs.getString("nomFait");
      this.descFait = rs.getString("descFait");
      this.dateFaitMarquant = rs.getString("dateFaitMarquant");
      this.ProcStock = rs.getString("ProcStock");
      this.Ligne = rs.getInt("Ligne");
      this.Colonne = rs.getInt("Colonne");
      this.TypePerso = rs.getString("Type");
      this.jsp = rs.getString("jsp");
      this.modele = rs.getString("modele");
      this.TypeCarto = rs.getString("TypeCarto");

      this.Param = rs.getString("Param");
      this.idEnquete = rs.getInt("idEnquete");
      this.idPerso = rs.getInt("idFaitPerso");


      } catch (SQLException s) {s.getMessage(); }
  }

  public static void getListeEnquetes(String nomBase,Connexion myCnx, Statement st){

    String req ="SELECT     FaitMarquant.idFait, FaitMarquant.nomFait FROM   FaitMarquant INNER JOIN   Personalisation ON FaitMarquant.idFaitPerso = Personalisation.idPerso WHERE     (Personalisation.modele = 'Enqu�te')";

    ListeEnquetes.removeAllElements();

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Collaborateur theCollaborateur = null;
    try {
       while (rs.next()) {
         int idFait = rs.getInt(1);
         String nomFait = rs.getString(2);

         FaitMarquant theFaitMarquant = new FaitMarquant();
         theFaitMarquant.idFait = idFait;
         theFaitMarquant.nomFait = nomFait;
         //theST.dump();
         ListeEnquetes.addElement(theFaitMarquant);
       }
        } catch (SQLException s) {s.getMessage();}

  }

  public  static void getTableWebparts(String nomBase,Connexion myCnx, Statement st){

    String req = "";
    for (int Ligne = 1; Ligne <= 3; Ligne++) {
      for (int Colonne = 1; Colonne <= 2; Colonne++) {
        req = "SELECT FaitMarquant.nomFait, Personalisation.icone FROM   FaitMarquant INNER JOIN  Personalisation ON FaitMarquant.idFaitPerso = Personalisation.idPerso WHERE     (FaitMarquant.Ligne = " +
            Ligne + ") AND (FaitMarquant.Colonne = " + Colonne +
            ")";
        ResultSet rs = myCnx.ExecReq(st, nomBase, req);
        try {
          while (rs.next()) {
            String ThenomFait = rs.getString(1);
            String Theicone = rs.getString(2);
            tabWebPart[Ligne][Colonne][0] = ThenomFait;
            tabWebPart[Ligne][Colonne][1] = Theicone;

          }
        }
        catch (SQLException s) {
          s.getMessage();
        }
      }
    }
  }

  public static void getListeFaitMarquants(String nomBase,Connexion myCnx, Statement st){

    String req = "SELECT DISTINCT modele, icone, idPerso  FROM   Personalisation";
    ListeFaitMarquants.removeAllElements();

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Collaborateur theCollaborateur = null;
    try {
       while (rs.next()) {
         String modele = rs.getString(1);
         String icone = rs.getString(2);
         int idPerso = rs.getInt(3);
         FaitMarquant theFaitMarquant = new FaitMarquant();
         theFaitMarquant.modele = modele;
         theFaitMarquant.icone = icone;
         theFaitMarquant.idPerso = idPerso;
         //theST.dump();
         ListeFaitMarquants.addElement(theFaitMarquant);
       }
        } catch (SQLException s) {s.getMessage();}

  }

  public  void getFaitMarquantsByType(String nomBase,Connexion myCnx, Statement st, String Type){

    String req = "SELECT     idFait, nomFait, descFait, dateFaitMarquant, ProcStock, TypeCarto, Param, Ligne, Colonne, idFaitPerso, idEnquete";
        req+=" FROM         FaitMarquant";
        req+=" WHERE     (nomFait = 'BRIEF')";



        ResultSet rs = null;

        rs = myCnx.ExecReq(st, myCnx.nomBase, req);
        try { rs.next();
          this.idFait = rs.getInt("idFait");
          this.nomFait = rs.getString("nomFait");
          this.descFait = rs.getString("descFait");
          this.dateFaitMarquant = rs.getString("dateFaitMarquant");
          this.ProcStock = rs.getString("ProcStock");
          this.TypeCarto = rs.getString("TypeCarto");
          this.Param = rs.getString("Param");
          this.Ligne = rs.getInt("Ligne");
          this.Colonne = rs.getInt("Colonne");
          this.idPerso = rs.getInt("idFaitPerso");
          this.idEnquete = rs.getInt("idEnquete");


      } catch (SQLException s) {s.getMessage(); }

  }
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("idFait="+this.idFait);
    System.out.println("nomFait="+this.nomFait);
    System.out.println("descFait="+this.descFait);
    System.out.println("modele="+this.modele);
    System.out.println("icone="+this.icone);
    System.out.println("idPerso="+this.idPerso);
    System.out.println("ProcStock="+this.ProcStock);
    System.out.println("Param="+this.Param);
    System.out.println("Ligne="+this.Ligne);
    System.out.println("Colonne="+this.Colonne);
    System.out.println("Type="+this.Type);
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

    FaitMarquant faitmarquant = new FaitMarquant(60);
    faitmarquant.getAllFromBd(myCnx.nomBase, myCnx, st);
    //faitmarquant.dump();
  }
}

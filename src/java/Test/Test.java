package Test; 

import java.sql.Statement;
import accesbase.Connexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import General.Utils;
import Documentation.*;
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
public class Test {
  public int id=-1;
   public String nom="";
   public String description="";
   public String miseEnOeuvre="";
   public int idCategorie=-1;
   public int idEtat=-1;
   public String Etat="";
   public String commentaire="";
   public int anomalie=-1;
   public int intervenant=-1;
   public int idCampagne=-1;
   public int Ordre=-1;
   public String Filename="";
   public doc FilenameMiseEnOeuvre=null;

   public String nomRoadmap="";
   public String nomCategorie="";

   private String req="";
  public Test(int id) {
    this.id = id;
  }
  
  public Test(String nom) {
    this.nom = nom;
  }  

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
     String chemin = "";


       req = "SELECT     id, nom, description, miseEnOeuvre, idCategorie, Ordre, FilenameMiseEnOeuvre FROM   Tests WHERE     (id = "+this.id+")";


     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       this.nom = rs.getString("nom");
       if (this.nom == null) this.nom = "";
       
       this.description = rs.getString("description");
       if ((this.description == null) || (this.description.equals("null"))) this.description = "";
       
        this.miseEnOeuvre = rs.getString("miseEnOeuvre");
        if (this.miseEnOeuvre == null || (this.miseEnOeuvre.equals("null"))) this.miseEnOeuvre = "";
        
       this.idCategorie = rs.getInt("idCategorie");
        this.Ordre = rs.getInt("Ordre");

        chemin = rs.getString("FilenameMiseEnOeuvre");


       } catch (SQLException s) {s.getMessage(); }

       if (chemin != null)
       {
         this.FilenameMiseEnOeuvre = new doc();
         this.FilenameMiseEnOeuvre.chemin = chemin;
         this.FilenameMiseEnOeuvre.getIcone(nomBase, myCnx, st);
       }


  }
  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){
        ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    String str_nom="";
    String str_description="";
    String str_miseEnOeuvre="";
    String chemin="";
    if (this.FilenameMiseEnOeuvre == null) 
        chemin="";
    else
        chemin = this.FilenameMiseEnOeuvre.chemin;

    if (this.nom != null) str_nom=this.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ');;
    if (this.description != null) str_description=this.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
    if (this.miseEnOeuvre != null) str_miseEnOeuvre=this.miseEnOeuvre.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");

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
             req+=""+this.idCategorie+",'";
             req+=str_nom+"','";
             req+=str_description+"','";
             req+=str_miseEnOeuvre+"','";
             req+=this.Ordre+"','";
             req+=chemin+"'";

           req+=")";


    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

         req = "SELECT     TOP (1) id, nom, description, miseEnOeuvre, idCategorie, Ordre, FilenameMiseEnOeuvre FROM    Tests ORDER BY id DESC";
         rs = myCnx.ExecReq(st, nomBase, req);
         try {rs.next(); this.id =rs.getInt("id");} catch (SQLException s) {s.getMessage();}

     return myError;
  }
  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    String nom=this.nom;
    String description=this.description;

    if (this.nom != null) nom=this.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ');
    if (this.description != null) this.description=this.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
    if (this.miseEnOeuvre != null) this.miseEnOeuvre=this.miseEnOeuvre.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");

        req = "UPDATE Tests SET ";
        req+="idCategorie='"+this.idCategorie+"',";
        req+="nom='"+nom+"',";
        req+="description='"+this.description+"',";
        req+="miseEnOeuvre='"+this.miseEnOeuvre+"',";
        req+="Ordre='"+Ordre+"',";
        req+="FilenameMiseEnOeuvre='"+this.FilenameMiseEnOeuvre.chemin+"'";
        req+=" WHERE id='"+this.id+"'";

    
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

     return myError;
  }
  
  public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    req="DELETE FROM Tests   WHERE  (id = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;


    return myError;
  }
  
  public void getResults(String nomBase,Connexion myCnx, Statement st,int idCampagne){
    ResultSet rs = null;
    this.idEtat= 3;
    String req = "SELECT      TypeEtatTest.nom,resultatTest.idEtat, resultatTest.commentaire, resultatTest.anomalie, resultatTest.intervenant, resultatTest.Filename";
           req+=" FROM         resultatTest INNER JOIN";
           req+="            TypeEtatTest ON resultatTest.idEtat = TypeEtatTest.id";
           req+=" WHERE     (resultatTest.idTest = "+this.id+") AND (resultatTest.idCampagne = "+idCampagne+")";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();
      this.Etat= rs.getString("nom");
      this.idEtat= rs.getInt("idEtat");
      this.commentaire= rs.getString("commentaire");
      this.anomalie= rs.getInt("anomalie");
      this.intervenant= rs.getInt("intervenant");
      this.Filename= rs.getString("Filename");


      } catch (SQLException s) {s.getMessage(); }
  }

  public String getListVersionBtState(String nomBase,Connexion myCnx, Statement st, String etat){
    String ListVersionBtState="";
    ResultSet rs = null;
    this.idEtat= 3;
    String req = "SELECT     Tests.id, Tests.nom, Tests.description, Tests.miseEnOeuvre, Tests.idCategorie, Tests.Ordre, resultatTest.idEtat, TypeEtatTest.nom AS Expr1, ";
           req+="           resultatTest.idCampagne, Campagne.version";
           req+=" FROM         Tests INNER JOIN";
           req+="            categorieTest ON Tests.idCategorie = categorieTest.id INNER JOIN";
           req+="            resultatTest ON Tests.id = resultatTest.idTest INNER JOIN";
           req+="            TypeEtatTest ON resultatTest.idEtat = TypeEtatTest.id INNER JOIN";
           req+="            Campagne ON resultatTest.idCampagne = Campagne.id";
           req+=" WHERE     (Tests.id = "+this.id+") AND (TypeEtatTest.nom = '"+etat+"')";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    int nb = 0;

      try {
        while (rs.next()) {
          int idCampagne=rs.getInt("idCampagne");
          if (nb == 0)
            ListVersionBtState+= "<a href='CommentaireTest.jsp?categorie=0&test=0&idCampagne="+idCampagne+"&idTest="+this.id+"&Filename=' title='Commentaire du test' target='_blank'>"+rs.getString("version")+"</a>";
          else
            ListVersionBtState+= " "+"<a href='CommentaireTest.jsp?categorie=0&test=0&idCampagne="+idCampagne+"&idTest="+this.id+"&Filename=' title='Commentaire du test' target='_blank'>"+rs.getString("version")+"</a>";
          nb++;

          }


    } catch (SQLException s) {s.getMessage();}

      return ListVersionBtState;
  }

  public String getListVersionBtState2(String nomBase,Connexion myCnx, Statement st, String etat){
    String ListVersionBtState="";
    ResultSet rs = null;
    this.idEtat= 3;
    String req = "SELECT     Tests.id, Tests.nom, Tests.description, Tests.miseEnOeuvre, Tests.idCategorie, Tests.Ordre, resultatTest.idEtat, TypeEtatTest.nom AS Expr1, ";
           req+="           resultatTest.idCampagne, Campagne.version";
           req+=" FROM         Tests INNER JOIN";
           req+="            categorieTest ON Tests.idCategorie = categorieTest.id INNER JOIN";
           req+="            resultatTest ON Tests.id = resultatTest.idTest INNER JOIN";
           req+="            TypeEtatTest ON resultatTest.idEtat = TypeEtatTest.id INNER JOIN";
           req+="            Campagne ON resultatTest.idCampagne = Campagne.id";
           req+=" WHERE     (Tests.id = "+this.id+") AND (TypeEtatTest.nom = '"+etat+"')";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    int nb = 0;

      try {
        while (rs.next()) {
          if (nb == 0)
            ListVersionBtState+= rs.getString("version");
          else
            ListVersionBtState+= ", "+rs.getString("version");
          nb++;

          }


    } catch (SQLException s) {s.getMessage();}

      return ListVersionBtState;
  }

  public ErrorSpecific bd_UpdateResult(String nomBase,Connexion myCnx, Statement st, String transaction, int idCampagne){
      ErrorSpecific myError = new ErrorSpecific();
     int check = 0;
    if (idEtat == -1) idEtat = 3; // AF: � faire

    req = "DELETE FROM resultatTest WHERE     (idCampagne = "+idCampagne+") AND (idTest = "+this.id+")";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateResult",""+this.id);
   if (myError.cause == -1) return myError;

    if (this.commentaire != null)
    {
      this.commentaire = this.commentaire.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ');
    }
    else this.commentaire = "";

    req = "INSERT resultatTest (idEtat, commentaire, anomalie, intervenant, idTest, idCampagne, Filename) ";
    req+=" VALUES ("+this.idEtat+",  '"+this.commentaire+"',  "+this.anomalie+", "+this.intervenant+", "+this.id+", "+idCampagne+", '"+this.Filename+"')";

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateResult",""+this.id);
    if (myError.cause == -1) return myError;

    return myError;
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("Ordre="+this.Ordre);
    System.out.println("nom="+this.nom);
    System.out.println("description="+this.description);
    System.out.println("miseEnOeuvre="+this.miseEnOeuvre);
    System.out.println("idCategorie="+this.idCategorie);
    System.out.println("idEtat="+this.idEtat);
    System.out.println("commentaire="+this.commentaire);
    System.out.println("anomalie="+this.anomalie);
    System.out.println("Filename="+this.Filename);
    System.out.println("intervenant="+this.intervenant);
    System.out.println("FilenameMiseEnOeuvre="+this.FilenameMiseEnOeuvre.chemin);
    if (this.FilenameMiseEnOeuvre != null) this.FilenameMiseEnOeuvre.dump();

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
    Test test = new Test(1444);
    test.getAllFromBd(myCnx.nomBase, myCnx, st);
   // test.dump();
  }
}

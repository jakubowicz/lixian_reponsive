package Test; 

import Documentation.doc;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import General.Utils;

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
public class RelectureTest {
  public int id;
   public String nom;
   public int idEtat;
   public String Etat;
   public String commentaire;
   public int idCampagne;


   private String req="";
  public RelectureTest(int id) {
    this.id=id;
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
     String chemin = "";


     req = "SELECT     resultatRelecture.id, resultatRelecture.idEtat, typeRelectureExigence.etat, resultatRelecture.Commentaire, resultatRelecture.idRelectureEB";
     req+="    FROM         RelectureEB INNER JOIN";
     req+="                resultatRelecture ON RelectureEB.id = resultatRelecture.id INNER JOIN";
     req+="                typeRelectureExigence ON resultatRelecture.idEtat = typeRelectureExigence.id";



     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       this.nom = rs.getString("nom");
       this.commentaire = rs.getString("description");
        this.idCampagne = rs.getInt("idCampagne");

        chemin = rs.getString("FilenameMiseEnOeuvre");



       } catch (SQLException s) {s.getMessage(); }


  }

  public String bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction, int idRelectureEB, int idBesoinUtilisateur){
    ResultSet rs=null;

    req="DELETE FROM resultatRelecture   WHERE  (idRelectureEB  = "+idRelectureEB+")" + " AND idBesoinUtilisateur="+idBesoinUtilisateur;
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

    return "OK";
  }

  public String bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction, int idRelectureEB, int idBesoinUtilisateur){
    ResultSet rs=null;

           req = "INSERT resultatRelecture (";
             req+="idEtat"+",";
             req+="Commentaire"+",";
             req+="idRelectureEB"+",";
             req+="idBesoinUtilisateur"+"";
             req+=")";

             req+=" VALUES ";
             req+="(";
             req+=""+this.idEtat+",";
             req+="'"+this.commentaire.replaceAll("\u0092","'").replaceAll("'","''")+"',";
             req+="'"+idRelectureEB+"',";
             req+="'"+idBesoinUtilisateur+"'";
             req+=")";

          if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

    return "OK";
  }

  public static void main(String[] args) {
    RelectureTest relecturetest = new RelectureTest(1);
  }
}

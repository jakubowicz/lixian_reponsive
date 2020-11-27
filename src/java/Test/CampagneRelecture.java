package Test; 

import java.util.Vector;
import accesbase.Connexion;
import java.util.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class CampagneRelecture {
  static Connexion myCnx;
  public int id = -1;
  public String date;
  public int num;
  public int nb_KO;
  public int nb_AR;
  public int nb_AF;
  public int nb_OK;
  public int nb_AN;

  public int nbTests;

  public float IA1;
  public float IA2;

  public int idRoadmap;
  public String Login="";
  public String FileName="";
  public String FileNameIn="";

  private String req = "";

  final static int C_Date=0;
  final static int C_KO=1;
  final static int C_AR=2;
  final static int C_AF=3;
  final static int C_OK=4;
  final static int C_AN=5;
  final static int C_nbTests=7;
  final static int C_IA1=10;
  final static int C_IA2=11;

  final static int L_Start=3;

  public String version="";

  public CampagneRelecture(int id) {
    this.id = id;

  }

  public String bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){


    return "OK";
  }

  public String bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;

    req="DELETE FROM RelectureEB  WHERE  (id = "+this.id+")";
    if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

    return "OK";
  }

  public String bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;

    this.date = Utils.getToDay(myCnx.nomBase,myCnx,st);
     Utils.getDate(this.date);
     String str_date ="convert(datetime, '"+Utils.Day+"/"+Utils.Month+"/"+Utils.Year+"', 103)";

           req = "INSERT RelectureEB (";
             req+="date"+",";
             req+="idRoadmap"+",";
             req+="Login"+"";
             req+=")";

             req+=" VALUES ";
             req+="(";
             req+=""+str_date+",";
             req+="'"+this.idRoadmap+"',";
             req+="'"+this.Login+"'";
             req+=")";

          if (myCnx.ExecUpdate(st,nomBase,req,true,transaction) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

          req = "SELECT id FROM  RelectureEB WHERE   (idRoadmap = "+this.idRoadmap+") AND (date = CONVERT(DATETIME, '"+this.date+"', 103)) AND Login = '"+this.Login+"'";
          rs = myCnx.ExecReq(st, nomBase, req);
          try {rs.next(); this.id =rs.getInt("id");} catch (SQLException s) {s.getMessage();}

    return "OK";
  }

  public int readResultByState(String nomBase,Connexion myCnx, Statement st, String etat){
    int nb = 0;
    ResultSet rs;
    String req = "SELECT     COUNT(typeRelectureExigence.etat) AS nb";
    req +="     FROM         RelectureEB INNER JOIN";
    req +="                   resultatRelecture ON RelectureEB.id = resultatRelecture.idRelectureEB INNER JOIN";
    req +="                   typeRelectureExigence ON resultatRelecture.idEtat = typeRelectureExigence.id";
    req +=" WHERE     (typeRelectureExigence.etat = '"+etat+"') AND (RelectureEB.id = "+this.id+")";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        nb = rs.getInt("nb");
      }
    }
            catch (SQLException s) {s.getMessage();}

    return nb;
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;


     req = "SELECT     idRoadmap, Login, date";
     req+="    FROM         RelectureEB";
     req+=" WHERE     (id = "+this.id+")";



     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       this.idRoadmap = rs.getInt("idRoadmap");
       this.Login = rs.getString("Login");
       Date Ddate = rs.getDate("date");
       if (Ddate != null)
         this.date = ""+Ddate.getDate()+"/"+(Ddate.getMonth()+1) + "/"+(Ddate.getYear() + 1900);
        else
          this.date = "";


       } catch (SQLException s) {s.getMessage(); }


       //this.majCompeurs();
  }

  public RelectureTest getResultByBesoin(String nomBase,Connexion myCnx, Statement st, int idBesoin ){
     ResultSet rs = null;


     req = "SELECT     resultatRelecture.id, resultatRelecture.idEtat, resultatRelecture.Commentaire, resultatRelecture.idRelectureEB, resultatRelecture.idBesoinUtilisateur, ";
     req+="                  typeRelectureExigence.etat";
     req+=" FROM         resultatRelecture INNER JOIN";
     req+="                  typeRelectureExigence ON resultatRelecture.idEtat = typeRelectureExigence.id";
     req+=" WHERE     (resultatRelecture.idRelectureEB = "+this.id+") AND (resultatRelecture.idBesoinUtilisateur = "+idBesoin+")";


     RelectureTest theRelectureTest = null;
     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       int id = rs.getInt("id");
       theRelectureTest = new RelectureTest(id);
       theRelectureTest.idEtat = rs.getInt("idEtat");
       theRelectureTest.commentaire = rs.getString("Commentaire");


       } catch (SQLException s) {s.getMessage(); }

       return theRelectureTest;
       //this.majCompeurs();
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("idRoadmap="+this.idRoadmap);
    System.out.println("date="+this.date);
    System.out.println("Login="+this.Login);
    System.out.println("==================================================");
  }

  public static void main(String[] args) {
    CampagneRelecture campagnerelecture = new CampagneRelecture(1);
  }
}

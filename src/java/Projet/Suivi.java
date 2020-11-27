package Projet; 
import java.util.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import accesbase.Connexion;

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
public class Suivi {
  public int id;
  public int idRoadmap;
  public Date dateSuivi;
  public String nom;
  public String Login;
  public String description;
  public String fichierAttache="";  
  public String type;
  private String req="";


  public Suivi() {
  }

  public Suivi(int id) {
    this.id = id;
  }


  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;
    req = "SELECT     dateSuivi, nom, description,idRoadmap,  type, Login";
    req+=" FROM         historiqueSuivi";
    req+=" WHERE     (id ="+ this.id+")";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();

      this.dateSuivi = rs.getDate("dateSuivi");
      this.nom = rs.getString("nom");
      this.description = rs.getString("description");
      this.idRoadmap = rs.getInt("idRoadmap");
      this.type = rs.getString("type");
      this.Login = rs.getString("Login");
      if (this.Login == null) this.Login="";
      } catch (SQLException s) {s.getMessage(); }
  }
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("dateSuivi="+this.dateSuivi);
    System.out.println("Login="+this.Login);
    System.out.println("nom="+this.nom);
    System.out.println("description="+this.description);
    System.out.println("type="+this.type);
    System.out.println("==================================================");
 }

  public static void main(String[] args) {
    Suivi suivi = new Suivi(1);
  }
}

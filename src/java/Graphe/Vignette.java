package Graphe; 

import accesbase.Connexion;
import ST.ST;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
public class Vignette {
  public int id;
  public int num;
  public int X;
  public int Y;
  public String nom;
  public String description;
  public String Login;

  private String req="";

  public Vignette(int id) {
    this.id = id;
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs=null;

    req ="SELECT id,num, X, Y, nom, description, Login FROM  Vignettes"+this.id;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String stAnomalie="";
    try {rs.next();
            this.num = rs.getInt("num");
            this.X = rs.getInt("X");
            this.Y = rs.getInt("Y");
            this.nom = rs.getString("nom");
            this.description = rs.getString("description");
            this.Login=rs.getString("Login");

    } catch (SQLException s) {s.getMessage();}


  }


  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("num="+this.num);
    System.out.println("X="+this.X);
    System.out.println("Y="+this.Y);
    System.out.println("nom="+this.nom);
    System.out.println("description="+this.description);
    System.out.println("Login="+this.Login);

    System.out.println("==================================================");
  }
  public static void main(String[] args) {
    Vignette vignette = new Vignette(1);
  }
}

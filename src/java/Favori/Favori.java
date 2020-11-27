package Favori;
 
import java.sql.*;
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
public class Favori {
  public String Action;
  public String nom;
  public int id;
  public String Type;
  public String Login;
  public String nom2;

  public Favori(String nom,int id,String Type,String Login) {
    this.nom=nom;
    this.id=id;
    this.Type=Type;
    this.Login=Login;

  }

  public void setFavoris(String Action,String nomBase,Connexion myCnx, Statement st){
    String req;
  if (Action.equals("Ajout"))
    req = "INSERT Favoris ( nom, id, Type, Login, nom2) VALUES ('"+nom+"',  '"+id+"', '"+Type+"','"+Login+"','"+nom2+"')";
  else
    req = "DELETE FROM Favoris WHERE Login ='"+Login+"'" + " AND id="+id;

myCnx.ExecReq(st,myCnx.nomBase,req);

  }

  public int getFavoris(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;
    int nbFavoris=0;
  rs = myCnx.ExecReq(st, nomBase,  "SELECT count(*) as nb FROM Favoris WHERE Login ='"+this.Login+"'" + " AND id="+this.id);
  try { rs.next(); nbFavoris = rs.getInt(1); } catch (SQLException s) {s.getMessage();}
  return nbFavoris;
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("Login="+this.Login);
    System.out.println("nom2="+this.nom2);
    System.out.println("Type="+this.Type);

    System.out.println("==================================================");
  }
}

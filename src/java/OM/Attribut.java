package OM;

import ST.*;
import Organisation.Service;
import accesbase.Connexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
public class Attribut
    extends Module {
  public int typeAttribut = -1;
  public String nomtypeAttribut = "";
  public int ordre=-1;
  public int sens=-1;
  public int idObjetMetier = -1;
  private String req="";

  public Attribut() {
  }

  public void getAllFromBd(
      String nomBase,
      Connexion myCnx,
      Statement st ){
    ResultSet rs=null;

    req = "SELECT     idAttribut, nomAttribut, descAttribut, omAttribut, typeAttribut, nomTypeAttribut, ordre, sens" ;
    req+= " FROM         Attribut" ;
    req+= " WHERE     (idAttribut = "+this.id+")";



    rs = Connexion.ExecReq(st, nomBase, req);

    //if (rs == null) return;
    try {
      while (rs.next()) {
        this.nom= rs.getString("nomAttribut");
        this.descModule= rs.getString("descAttribut");
        this.typeAttribut= rs.getInt("typeAttribut");
        this.nomtypeAttribut= rs.getString("nomTypeAttribut");
        this.ordre= rs.getInt("ordre");
        this.sens= rs.getInt("sens");

        }

    } catch (SQLException s) {s.getMessage();}


}  
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("nom="+this.descModule);
    System.out.println("nomtypeAttribut="+this.nomtypeAttribut);
    System.out.println("ordre="+this.ordre);
    System.out.println("sens="+this.sens);

    System.out.println("==================================================");
 }
}

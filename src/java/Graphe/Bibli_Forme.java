package Graphe; 

import java.util.Vector;
import java.sql.Statement;
import accesbase.Connexion;
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
public class Bibli_Forme {
  public Vector ListeForme = new Vector(30);
  public String nom;
  public Bibli_Forme(String nom, String nomBase,Connexion myCnx, Statement st ) {
    this.nom = nom;
    ResultSet rs;
    String req = "SELECT  formeTypeAppli, couleur, icone FROM   " + this.nom;
    rs = myCnx.ExecReq(st, nomBase, req);
    try {
      while (rs.next()) {
        String Forme = rs.getString(1);
        String couleur = rs.getString(2);
        String icone = rs.getString(3);
        int Index = icone.lastIndexOf("\\\\");
        icone = icone.substring(Index);
        Forme theForme = new Forme(Forme,couleur, icone );
        this.ListeForme.addElement(theForme);
      }
    }
    catch (Exception e) {}

  }
  public static String param_name(String nom, String nomBase,Connexion myCnx, Statement st ) {
    String paramName="";
    ResultSet rs;
    String req = "SELECT  formeTypeAppli, couleur, icone FROM   " + nom;
    rs = myCnx.ExecReq(st, nomBase, req);
    try {
      while (rs.next()) {
        String Forme = rs.getString(1);
        String couleur = rs.getString(2);
        String icone = rs.getString(3);
        int Index = icone.lastIndexOf("\\");
        icone = icone.substring(Index);
        paramName+="<param name="+Forme+"-"+couleur+" value='"+icone+"'>";
        paramName+="\n";
      }
    }
    catch (Exception e) {}
    return paramName;
  }
}

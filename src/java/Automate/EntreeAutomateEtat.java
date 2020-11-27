package Automate;
 
import java.util.Vector;
import java.sql.Statement;
import java.sql.SQLException;
import accesbase.Connexion;
import java.sql.ResultSet;

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
public class EntreeAutomateEtat {
  public int idEtat;
  public String Etat;
  public String Admin;
  public String display;
  public Vector ListeLigneAutomate = new Vector(10);
  String req = "" ;
  ResultSet rs = null;

  public EntreeAutomateEtat(int idEtat, String Etat, String Admin) {
    this.idEtat = idEtat;
    this.Etat = Etat;
    this.Admin = Admin;
  }

  public Vector getLigneAutomate(int idVersion, String nomBase,Connexion myCnx, Statement st, String transaction){
    if ((this.Admin==null) || (!this.Admin.equals("yes")))
      req = "SELECT     actionBouton.bouton, automate.action, automate.EtatNext, TypeEtat.idTypeEtat FROM automate INNER JOIN actionBouton ON automate.action = actionBouton.action INNER JOIN TypeEtat ON automate.EtatNext = TypeEtat.nom2TypeEtat WHERE (automate.Etat = '"+this.Etat+"') AND (automate.Utilisateur = 'yes')";
    else
      req = "SELECT     actionBouton.bouton, automate.action, automate.EtatNext, TypeEtat.idTypeEtat FROM automate INNER JOIN actionBouton ON automate.action = actionBouton.action INNER JOIN TypeEtat ON automate.EtatNext = TypeEtat.nom2TypeEtat WHERE (automate.Etat = '"+this.Etat+"')  AND (automate.Admin = 'yes')";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String bouton ="";
    String action ="";
    String EtatNext ="";
    int idEtatNext=0;

    try {
      while (rs.next()) {
        bouton= rs.getString(1);
        action= rs.getString(2);
        EtatNext= rs.getString(3);
        idEtatNext = rs.getInt(4);
        EntreeAutomate myLigneAutomate = new EntreeAutomate(bouton,action,EtatNext,idEtatNext);
        this.ListeLigneAutomate.addElement(myLigneAutomate);

        }


    } catch (SQLException s) {s.getMessage();}

    return null;
  }

  public static void main(String[] args) {
    EntreeAutomateEtat entreeautomateEtat = new EntreeAutomateEtat(1,"","");
  }
}

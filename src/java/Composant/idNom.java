package Composant;
 
import accesbase.Connexion;
import java.util.Date;

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
public class idNom {
  public int id;
  public String nom;
  public String description;
  public String LoginCreateur="";
  public Date DdateCreation = null;
  public String dateCreation = "";
  public int ordre;

  public idNom(int id, String nom) {
    this.id=id;
    this.nom=nom;
  }

  public idNom(int id) {
    this.id=id;
  }

  public idNom() {
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("description="+this.description);
    System.out.println("LoginCreateur="+this.LoginCreateur);
    System.out.println("dateCreation="+this.dateCreation);
    System.out.println("ordre="+this.ordre);
    System.out.println("==================================================");
  }

  public static void main(String[] args) {
    idNom idnom = new idNom(1,"");
  }
}

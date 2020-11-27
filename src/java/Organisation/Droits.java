package Organisation; 

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
public class Droits {
  public int id;
  public String nom;
  public String Type;
  public Droits(String nom, String Type) {
    this.nom = nom;
    this.Type = Type;
  }
  public Droits(int id,String nom, String Type) {
    this.id=id;
    this.nom = nom;
    this.Type = Type;
  }
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("Type="+this.Type);
    System.out.println("==================================================");
  }
  public static void main(String[] args) {
    Droits droits = new Droits("","");
  }
}

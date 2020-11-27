package Graphe;
 
import accesbase.Connexion;
import java.util.Vector;

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
public class Barre {
  public String nom;
  public int valeur1;
  public int valeur2;
  public int valeur3;
  public Vector ListeValeur1= new Vector(10);
  public Vector ListeValeur2= new Vector(10);

  public Barre(String nom, int valeur1, int valeur2) {
    this.nom = nom;
    this.valeur1 = valeur1;
    this.valeur2 = valeur2;
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("nom="+this.nom);
    System.out.println("valeur1="+this.valeur1);
    System.out.println("valeur2="+this.valeur2);
    System.out.println("valeur3="+this.valeur2);
    System.out.println("==================================================");
  }
}

package Graphe;
  
import java.util.Vector;
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
public class Boite {
  public String nom="";
  public int top=0;
  public int left=0;
  public int right=0;
  public int bottom=0;
  public int width = 0;
  public int height = 0;

  public int margin_left = 0;
  public int margin_top = 0;

  public Vector ListeBoites = new Vector(10);

  public Boite() {
  }

  public Boite(String nom, int top, int left) {
    this.nom=nom;
    this.top=top;
    this.left=left;
  }

  public Boite(String nom, int top, int left, int margin_top, int margin_left) {
    this.nom=nom;
    this.top=top;
    this.left=left;
    this.margin_top=margin_top;
    this.margin_left=margin_left;
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("nom="+this.nom);
    System.out.println("top="+this.top);
    System.out.println("left="+this.left);
    System.out.println("right="+this.right);
    System.out.println("width="+this.width);
    System.out.println("height="+this.height);

    System.out.println("this.ListeBoites.size()="+this.ListeBoites.size());
    for (int i=0; i < this.ListeBoites.size(); i++)
    {
      Boite theBoite = (Boite)this.ListeBoites.elementAt(i);
      theBoite.dump();
    }
    System.out.println("==================================================");
  }

  public static void main(String[] args) {
    Boite theBoite = new Boite();
  }
}

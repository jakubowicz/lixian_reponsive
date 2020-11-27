package ST; 

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
public class Region {
  public int id;
  public String nom;
  public String desc;
  public String selected;
  public int ordre;

  public Region(
  int id,
  String nom,
  String desc,
  int ordre)
  {
    this.id=id;
    this.nom=nom;
    this.desc=desc;
    this.ordre=ordre;
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("id="+this.id);
    System.out.println("nom="+this.nom);
    System.out.println("desc="+this.desc);
    System.out.println("selected="+this.selected);
    System.out.println("ordre="+this.ordre);

  }
}

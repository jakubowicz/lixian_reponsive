package Anomalies;
 
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
public class TypeCriticite {
  public int id;
  public String nom;
  public TypeCriticite(int id, String nom) {
    this.id=id;
    this.nom=nom;
  }

  public static void main(String[] args) {
    TypeCriticite typecriticite = new TypeCriticite(1,"");
  }
}

package Automate;
 
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
public class EntreeAutomate {
  public String bouton;
  public String action;
  public int idetatNext;
  public String etatNext;

  public EntreeAutomate(String bouton, String action, String etatNext, int idetatNext) {
    this.bouton =bouton;
    this.action =action;
    this.etatNext =etatNext;
    this.idetatNext=idetatNext;
  }

  public static void main(String[] args) {
    EntreeAutomate entreeautomate = new EntreeAutomate("","","",0);
  }
}

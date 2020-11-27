package Graphe;
import java.awt.*;
import Processus.Activite; 
import ST.ST;
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
public class Timing extends Forme{
  public String duree="";
  private int marge = 27;
  private int offset_Image = 2;
  private int offset_Label = 12;
  private int offset_Duree = 32;

  public int x_start=-1;
  public int y_start=-1;
  public int x_duree=-1;
  public int y_duree=-1;

  public Timing() {
  }

  public Timing(String Label,String duree){
    this.Label=Label;
    this.duree=duree;
    this.Style = "Comic Sans MS";
    this.Epaisseur = Font.PLAIN;
    this.Taille = 20;
    //this.icone = "images/xclock.png";
    //this.icone = "images/bell.png";
    this.icone = "images/history.png";
  }

  public void draw(int x_boite, int y_boite){

    this.Largeur = 20;
    this.Hauteur = 20;

    this.x = x_boite +offset_Image;
    this.y = y_boite +15;

    this.x_start= x_boite +marge -5;
    this.y_start = y_boite +offset_Label;

    this.x_duree= x_boite +marge -5;
    this.y_duree = y_boite +offset_Duree;

  }
  public static void main(String[] args) {
    Timing timing = new Timing();
  }
}

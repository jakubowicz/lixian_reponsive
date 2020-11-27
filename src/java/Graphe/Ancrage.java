package Graphe; 

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
public class Ancrage extends Forme{
  String Cardinalite="";
  String TypeFace="";
  String Face="";

  boolean isSet=false;

  public String etat = "";
  public Ancrage AncrageDest=null;

  public Ancrage() {
  }

  public Ancrage(String TypeFace,String Face,String Cardinalite,String Label,int x, int y){
    this.TypeFace=TypeFace;
    this.Face=Face;
    this.Label=Label;
    this.x = x;
    this.y=y;
    this.Cardinalite=Cardinalite;
    this.etat = "libre";

    //this.ListeCompatibilite.addElement(Cardinalite);


  }

  public void positionne(int x, int y){
    if (!this.isSet){
      this.x += x;
      this.y += y;
    }
    this.isSet = true;
}

  public static void main(String[] args) {
    Ancrage ancrage = new Ancrage();
  }
}

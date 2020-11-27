package Composant;
 
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
public class simpleApplet {
  public String label;
  public int value;
  public String color;
  public String style;

  public simpleApplet() {
  }

  public simpleApplet(String label, int value, String color, String style) {
    this.label=label;
    this.value=value;
    this.color=color;
    this.style=style;
  }

  public static void main(String[] args) {
    simpleApplet simpleapplet = new simpleApplet("",1,"","");
  }
}

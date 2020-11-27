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
public class Html {
String nom;
String balise;
String value;
String text;
String attributs;

  public int x;
  public int y;
  public int width;
  public int height;

public String html;

void start(){
  this.html = "<"+this.balise + this.attributs+">";
}

void addValue(String value){
  this.html += value;
}

void stop(){
  this.html += "</"+this.balise +">";
}

void dump(){
  System.out.print(this.html);
}
public static void main(String[] args) {

}
}

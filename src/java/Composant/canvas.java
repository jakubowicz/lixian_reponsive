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
public class canvas extends Html{
  private String font = "";
  private String color = "";
  private String textAlign = "";
  private String textBaseline = "";

  public canvas(int width, int height) {
    this.width = width;
    this.height = height;

    this.font = "10pt Verdana";
    this.color = "darkgreen";
    //this.color = "red";
    this.textAlign = "left";
    this.textBaseline = "top";
  }

  public void setFont(String font){
    this.font =font;

  }

  public void setColor(String color){
    this.color =color;

  }

  public void drawString(String text, int x, int y){

    this.html="";

    this.html += "ctx.font = ";
    this.html +="'"+ this.font+"'";
    this.html += ";";

    this.html += "ctx.textAlign = ";
    this.html +="'"+ this.textAlign+"'";
    this.html += ";";

    this.html += "ctx.textBaseline = ";
    this.html +="'"+ this.textBaseline+"'";
    this.html += ";";

    this.html += "ctx.fillStyle = ";
    this.html +="'"+ this.color+"'";
    this.html += ";";

    this.html += "ctx.fillText(";
    this.html += "'"+text+"'";
    this.html += ",";
    this.html += ""+x;
    this.html += ",";
    this.html += ""+y;
    this.html += ")";
  }

  public static void main(String[] args) {
    canvas canvas = new canvas(1,1);
  }
}

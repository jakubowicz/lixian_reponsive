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
public class Trunk extends Forme{
  public int x1=0;
  public int y1=0;
  public int x2=0;
  public int y2=0;
  boolean isFleche=false;
  public static int pas = 15;
  static int Marge_H=5;
  static int Marge_V=3;

  int a = -1;
  int b = -1;
  public boolean isHorizontal=false;
  public boolean isVertical=false;

  public int nbCollision=0;

  public Trunk(String Label,int x1,int y1,int x2,int y2,boolean isFleche){
    this.x1 = x1;
    this.y1=y1;
    this.x2 = x2;
    this.y2=y2;
    this.isFleche=isFleche;
    this.Label = Label;

    if (y2 == y1) this.isHorizontal=true;
    if (x2 != x1)
    {
      this.a = (y2 - y1) / (x2 - x1);
      this.b = y1 - this.a * x1;
    }
    else
    {
     this.isVertical = true;
    }

  }

  void drawFleche( int x1, int y1, int x2, int y2){
    double beta = 0;
    double tg_beta = 0;

   if (y1== y2){
     if ((x1 > x2)) beta = 0;
     if ((x1 < x2)) beta = (double)Math.PI;

   }
   else
   {
    if (x1 == x2) {
      if ((y1 > y2)) beta = (double)Math.PI/2;
      if ((y1 < y2)) beta = -(double)Math.PI/2;
    }
    else {
       tg_beta = (double)((double)(y2-y1)/(double)(x2-x1));
       beta = Math.atan(tg_beta);

       if ((x1 < x2) & (y1 > y2)) beta = beta + Math.PI;
       if ((x1 < x2) & (y1 < y2)) beta = beta + Math.PI;
    }
   }
    double L_fleche = (double)10;

    double x_fleche1 = 0;
    double y_fleche1= 0;

    double x_fleche2= 0;
    double y_fleche2= 0;

    double pi_sur_6 = (double)Math.PI/6;
    double alpha = pi_sur_6;

     this.drawOneFleche(x1, y1, x2, y2,beta,1, 1, 10, alpha);

  }

  void drawOneFleche (int x1, int y1, int x2, int y2,double beta,int poids1, int poids2, int L_fleche, double alpha ){


    int x0 = (poids1 *(x1 ) +  poids2*(x2 ))/(poids1+poids2);
    int y0 = (poids1 *(y1 ) +  poids2*(y2 ))/(poids1+poids2);

    double x_fleche1 = (double)x0 + L_fleche * (Math.cos(-alpha + beta)) ;
    double y_fleche1 =(double) y0 +  L_fleche *( Math.sin(-alpha + beta)) ;
    double x_fleche2 = (double)x0 + L_fleche * (Math.cos(alpha + beta)) ;
    double  y_fleche2 = (double)y0 +  L_fleche *( Math.sin(alpha + beta)) ;

    // ** DESSIN FLECHE ** /
    //screen2D.drawLine((int)x0,(int)y0,(int)x_fleche1,(int)y_fleche1);
    //screen2D.drawLine((int)x0,(int)y0,(int)x_fleche2,(int)y_fleche2);

  }
  public void draw() {

    if (this.isFleche) this.drawFleche(x1, y1, x2, y2);
    //screen2D.drawLine(x1, y1, x2, y2);
    // ** DESSIN DU TRUNK ** /

  }

  public static void main(String[] args) {
    //Trunk trunk = new Trunk();
  }
}

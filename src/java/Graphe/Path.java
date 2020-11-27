package Graphe; 

import Processus.Activite;
import java.util.Vector;
import OM.OM;
import java.awt.*;
import java.awt.image.BufferedImage;
import General.Utils;

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
public class Path {
  String Label = "";
  int note = 0;
  Ancrage AncrageOrigine=null;
  Ancrage AncrageDest=null;
  public Vector ListTrunk = new Vector(100);
  public Vector ListOtherTrunk = new Vector(100);

  String positionRelative="";
  public Activite ActiviteOrigine=null;
  public Activite ActiviteDestination=null;

  Vector ListepositionRelative=null;


  static int distance=100000;
  int nbCollision = 0;

  public OM myOM=null;
  Vector ListeAlgo = new Vector(6);
  String bestAlgo="";

  boolean ispaint = false;
  boolean isVertical = true;

  int NoteEtat = 0;
  int NoteCollision = 0;
  int NoteDistance = 0;
  int NoteLibelleOM = 0;
  int theNote = 0;

  public Path() {
  }

  public Path (Vector ListeTrunk,Activite ActiviteOrigine, Activite ActiviteDestination, String Label, boolean isVertical){
    this.ActiviteOrigine=ActiviteOrigine;
    this.ActiviteDestination=ActiviteDestination;
    this.Label=Label;
    this.ListTrunk = ListTrunk;
    this.isVertical = isVertical;

  }

  public String getListePositionRelative(Activite ActiviteOrigine,Activite ActiviteDestination){
    if (this.ActiviteOrigine.nom.equals("start") && this.isVertical)
    {
      return "vertical";
    }
    if (this.ActiviteOrigine.nom.equals("start") && !this.isVertical)
    {
      return "horizontal";
    }

    if (this.ActiviteDestination.nom.equals("stop") )
    {
      return "horizontal";
    }


    if ((ActiviteOrigine.x == ActiviteDestination.x) )
    {
      return "vertical";
    }

    if ((ActiviteOrigine.y == ActiviteDestination.y))
    {
      return "horizontal";
    }

    if ((ActiviteOrigine.x < ActiviteDestination.x)&& (ActiviteOrigine.y < ActiviteDestination.y))
    {
     return "standard";
   }
   if ((ActiviteOrigine.x > ActiviteDestination.x)&& (ActiviteOrigine.y > ActiviteDestination.y))
   {
    return "standard";
  }
    if ((ActiviteOrigine.x < ActiviteDestination.x)&& (ActiviteOrigine.y > ActiviteDestination.y))
    {
     return "standard";
   }
    if ((ActiviteOrigine.x > ActiviteDestination.x)&& (ActiviteOrigine.y < ActiviteDestination.y))
    {
     return "standard";
   }

    return "standard";
  }

  public Vector initListeAlgo(Ancrage Ancrage1, Ancrage Ancrage2){
    Vector myListeAlgo = new Vector(6);
    if (Ancrage1.Label.equals("start")) {
      if (Ancrage2.Face.equals("S"))
        myListeAlgo.addElement("trunk180B");
      else
        myListeAlgo.addElement("trunkDirect");
      return myListeAlgo;

    }

    if (Ancrage2.Label.equals("stop")) {
      if (Ancrage1.Face.equals("S"))
        myListeAlgo.addElement("trunk180B");
      else if (Ancrage1.Face.equals("N"))
        myListeAlgo.addElement("trunk180H");
      return myListeAlgo;

    }

    if (this.positionRelative.equals("vertical")) {
      if (
          (
              (Ancrage1.Face.equals("S") && Ancrage2.Face.equals("N"))
              ||
              (Ancrage1.Face.equals("N") && Ancrage2.Face.equals("S")
              )
          )
          && (Ancrage1.x == Ancrage2.x)
          )
        myListeAlgo.addElement("trunkDirect");
    }

    if (this.positionRelative.equals("horizontal")) {
      if (
          (
              (Ancrage1.Face.equals("E") && Ancrage2.Face.equals("W"))
              ||
              (Ancrage1.Face.equals("W") && Ancrage2.Face.equals("E"))
          )
          && (Ancrage1.y == Ancrage2.y)
          )

        myListeAlgo.addElement("trunkDirect")
            ;


  }
    if (this.positionRelative.equals("standard"))
    {
      myListeAlgo.addElement("trunkDirect");
    }
  if (Ancrage1.Face.equals("N") && Ancrage2.Face.equals("N"))
       {
         myListeAlgo.addElement("trunk180H");
        //myListeAlgo.addElement("trunk180H2");

       }

  if (Ancrage1.Face.equals("S") && Ancrage2.Face.equals("S"))
        {
         myListeAlgo.addElement("trunk180B")
         ;

        }

  if (Ancrage1.Face.equals("E") && Ancrage2.Face.equals("E"))
      {
       myListeAlgo.addElement("trunk180D");

      }

  if (Ancrage1.Face.equals("W") && Ancrage2.Face.equals("W"))
          {
           myListeAlgo.addElement("trunk180G");

          }

  if ((Ancrage1.TypeFace.equals("Lateral") && Ancrage2.TypeFace.equals("Base")) ||(Ancrage1.TypeFace.equals("Base") && Ancrage2.TypeFace.equals("Lateral")))
    {
      myListeAlgo.addElement("trunk90yx");
      myListeAlgo.addElement("trunk90xy");
    }

    //this.ListeAlgo.addElement("trunkDirect");
  return myListeAlgo;
  }

  public Vector Buildtrunk90xy(Ancrage Ancrage1, Ancrage Ancrage2){
    Vector myListeTrunk = new Vector (100);
    Trunk theTrunk = null;

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage1.x,Ancrage1.y,Ancrage2.x,Ancrage1.y,true);
    myListeTrunk.addElement(theTrunk);

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage2.x,Ancrage1.y,Ancrage2.x,Ancrage2.y,false);
    myListeTrunk.addElement(theTrunk);

    return myListeTrunk;
}

  public Vector Buildtrunk90yx(Ancrage Ancrage1, Ancrage Ancrage2){
    Vector myListeTrunk = new Vector (100);
    Trunk theTrunk = null;

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage1.x,Ancrage1.y,Ancrage1.x,Ancrage2.y,true);
    myListeTrunk.addElement(theTrunk);

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage1.x,Ancrage2.y,Ancrage2.x,Ancrage2.y,false);
    myListeTrunk.addElement(theTrunk);

    return myListeTrunk;
}

  public Vector Buildtrunk180B(Ancrage Ancrage1, Ancrage Ancrage2){
    int nbPas=1;
    Vector myListeTrunk = new Vector (100);
    Trunk theTrunk = null;

  theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage1.x,Math.max(Ancrage2.y,Ancrage1.y) + nbPas*Trunk.pas,Ancrage2.x,Math.max(Ancrage2.y,Ancrage1.y) + nbPas*Trunk.pas,true);
  while ((this.getnbCollision(theTrunk,this.ListOtherTrunk) >0) && (nbPas < 4))
  {
    nbPas++;
    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage1.x,Math.max(Ancrage2.y,Ancrage1.y) + nbPas*Trunk.pas,Ancrage2.x,Math.max(Ancrage2.y,Ancrage1.y) + nbPas*Trunk.pas,true);

  }

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage1.x,Ancrage1.y,Ancrage1.x,Math.max(Ancrage2.y,Ancrage1.y) + nbPas*Trunk.pas,false);
    myListeTrunk.addElement(theTrunk);

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage1.x,Math.max(Ancrage2.y,Ancrage1.y) + nbPas*Trunk.pas,Ancrage2.x,Math.max(Ancrage2.y,Ancrage1.y) + nbPas*Trunk.pas,true);
    myListeTrunk.addElement(theTrunk);

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage2.x,Math.max(Ancrage2.y,Ancrage1.y) + nbPas*Trunk.pas,Ancrage2.x,Ancrage2.y,false);
    myListeTrunk.addElement(theTrunk);

    return myListeTrunk;
}

  public Vector Buildtrunk180H(Ancrage Ancrage1, Ancrage Ancrage2){
    int nbPas=1;
    Vector myListeTrunk = new Vector (6);
    Trunk theTrunk = null;

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage1.x,Ancrage2.y - nbPas*Trunk.pas,Ancrage2.x,Ancrage2.y - nbPas*Trunk.pas,true);

    while ((this.getnbCollision(theTrunk,this.ListOtherTrunk) >0) && (nbPas < 4))
    {
      nbPas++;
      theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage1.x,Ancrage2.y - nbPas*Trunk.pas,Ancrage2.x,Ancrage2.y - nbPas*Trunk.pas,true);

    }


    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage1.x,Ancrage1.y,Ancrage1.x,Ancrage2.y - nbPas*Trunk.pas,false);
    myListeTrunk.addElement(theTrunk);

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage1.x,Ancrage2.y - nbPas*Trunk.pas,Ancrage2.x,Ancrage2.y - nbPas*Trunk.pas,true);
    myListeTrunk.addElement(theTrunk);

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage2.x,Ancrage2.y - nbPas*Trunk.pas,Ancrage2.x,Ancrage2.y,false);
    myListeTrunk.addElement(theTrunk);

    return myListeTrunk;
  }

  public Vector Buildtrunk180G(Ancrage Ancrage1, Ancrage Ancrage2){
    int nbPas=1;
    Vector myListeTrunk = new Vector (100);
    Trunk theTrunk = null;

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Math.min(Ancrage1.x - nbPas*Trunk.pas,Ancrage2.x),Ancrage1.y ,Math.min(Ancrage1.x - nbPas*Trunk.pas,Ancrage2.x),Ancrage2.y,true);
    while ((this.getnbCollision(theTrunk,this.ListOtherTrunk) >0) && (nbPas < 4))
    {
      nbPas++;
      theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Math.min(Ancrage1.x - nbPas*Trunk.pas,Ancrage2.x),Ancrage1.y ,Math.min(Ancrage1.x - nbPas*Trunk.pas,Ancrage2.x),Ancrage2.y,true);

    }


    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage1.x,Ancrage1.y,Math.min(Ancrage1.x - nbPas*Trunk.pas,Ancrage2.x),Ancrage1.y ,false);
    myListeTrunk.addElement(theTrunk);

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Math.min(Ancrage1.x - nbPas*Trunk.pas,Ancrage2.x),Ancrage1.y ,Math.min(Ancrage1.x - nbPas*Trunk.pas,Ancrage2.x),Ancrage2.y,true);
    myListeTrunk.addElement(theTrunk);

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Math.min(Ancrage1.x - nbPas*Trunk.pas,Ancrage2.x),Ancrage2.y,Ancrage2.x,Ancrage2.y,false);
    myListeTrunk.addElement(theTrunk);

    return myListeTrunk;
}

  public Vector Buildtrunk180D(Ancrage Ancrage1, Ancrage Ancrage2){
    int nbPas=1;
    Vector myListeTrunk = new Vector (100);
    Trunk theTrunk = null;

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Math.max(Ancrage1.x + nbPas*Trunk.pas,Ancrage2.x),Ancrage1.y ,Math.max(Ancrage1.x + nbPas*Trunk.pas,Ancrage2.x),Ancrage2.y,true);

    while ((this.getnbCollision(theTrunk,this.ListOtherTrunk) >0) && (nbPas < 4))
    {
      nbPas++;
      theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Math.max(Ancrage1.x + nbPas*Trunk.pas,Ancrage2.x),Ancrage1.y ,Math.max(Ancrage1.x + nbPas*Trunk.pas,Ancrage2.x),Ancrage2.y,true);

    }


    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage1.x,Ancrage1.y,Math.max(Ancrage1.x + nbPas*Trunk.pas,Ancrage2.x),Ancrage1.y ,false);
    myListeTrunk.addElement(theTrunk);

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Math.max(Ancrage1.x + nbPas*Trunk.pas,Ancrage2.x),Ancrage1.y ,Math.max(Ancrage1.x + nbPas*Trunk.pas,Ancrage2.x),Ancrage2.y,true);
    myListeTrunk.addElement(theTrunk);

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Math.max(Ancrage1.x + nbPas*Trunk.pas,Ancrage2.x),Ancrage2.y,Ancrage2.x,Ancrage2.y,false);
    myListeTrunk.addElement(theTrunk);


    return myListeTrunk;
  }

  public Vector BuildtrunkDirect(Ancrage Ancrage1, Ancrage Ancrage2){
    Vector myListeTrunk = new Vector (100);
    Trunk theTrunk = null;

    theTrunk = new Trunk (Ancrage1.Label + "=>" +Ancrage2.Label,Ancrage1.x,Ancrage1.y,Ancrage2.x,Ancrage2.y,true);
    myListeTrunk.addElement(theTrunk);

    return myListeTrunk;
}

  public boolean In(int a, int b, int c){

    if ((a > Math.min(b,c)) && (a < Math.max(b,c)) )
        return true;
    else
        return false;
  }

  public int getnbCollision(Trunk theTrunk, Vector theListTrunk){
    //System.out.println("@@ theListTrunk.size()"+theListTrunk.size());
    theTrunk.nbCollision = 0;
    /*
    if (theListTrunk.size() > 0)
    {
      System.out.println(theListTrunk.size());
    }
   */
  /*
if (this.ActiviteDestination != null && this.AncrageOrigine != null && this.AncrageDest!= null && this.AncrageOrigine.Cardinalite.equals("SSE") && this.AncrageDest.Cardinalite.equals("NW") && this.ActiviteDestination.nom.equals("Vois 4G-4G flux") && theListTrunk.size() > 0)
    {
      System.out.println(theListTrunk.size());
    }
*/
    for (int i=0; i < theListTrunk.size(); i++)
    {
      Trunk otherTrunk = (Trunk)theListTrunk.elementAt(i);

      if (theTrunk.isHorizontal)
      {
        if (otherTrunk.isHorizontal)
        {
          if (theTrunk.y1 != otherTrunk.y1) continue;
          else
          {
              if (
                    this.In(theTrunk.x1,otherTrunk.x1,otherTrunk.x2)
                    ||
                     this.In(theTrunk.x2,otherTrunk.x1,otherTrunk.x2)
                    ||
                     this.In(otherTrunk.x1,theTrunk.x1,theTrunk.x2)
                     ||
                      this.In(otherTrunk.x2,theTrunk.x1,theTrunk.x2)

                  )
                   theTrunk.nbCollision ++;

          }
        }
        else if (otherTrunk.isVertical)
        {


          if (
                this.In(otherTrunk.x1,theTrunk.x1,theTrunk.x2)
                &&
                this.In(theTrunk.y1,otherTrunk.y1,otherTrunk.y2)

              )
               theTrunk.nbCollision ++;


        }
        else
        {

        }

      }
      else if (theTrunk.isVertical)
      {
        if (otherTrunk.isHorizontal)
        {
          if (
                //( ((otherTrunk.x1 < theTrunk.x1) && (otherTrunk.x2 > theTrunk.x2)) || ((otherTrunk.x1 > theTrunk.x1) && (otherTrunk.x2 < theTrunk.x2)) ) && (( (otherTrunk.y1 > theTrunk.y1) && (otherTrunk.y1 < theTrunk.y2) )) || ( (otherTrunk.y1 < theTrunk.y1) && (otherTrunk.y2 > theTrunk.y2) ))
               this.In(theTrunk.x1,otherTrunk.x1,otherTrunk.x2)
               &&
               this.In(otherTrunk.y1,theTrunk.y1,theTrunk.y2)
             )
               theTrunk.nbCollision ++;

        }
        else if (otherTrunk.isVertical)
        {
          if (theTrunk.x1 != otherTrunk.x1) continue;
          else
          {
            if (
                  this.In(theTrunk.y1,otherTrunk.y1,otherTrunk.y2)
                  ||
                  this.In(theTrunk.y2,otherTrunk.y1,otherTrunk.y2)
                  ||
                  this.In(otherTrunk.y1,theTrunk.y1,theTrunk.y2)
                  ||
                  this.In(otherTrunk.y2,theTrunk.y1,theTrunk.y2)

                )
               theTrunk.nbCollision++;
          }

        }
        else
        {

        }

      }
      else
      {
        if (otherTrunk.isHorizontal)
        {

        }
        else if (otherTrunk.isVertical)
        {

        }
        else
        {

        }

      }

    }

    return theTrunk.nbCollision ;
  }

  public int getNoteEtat(Ancrage Ancrage1, Ancrage Ancrage2){
    int Note=0;
    if (Ancrage1.etat.equals("occupe")  ) Note+=10000;
    if (Ancrage2.etat.equals("occupe")  ) Note+=10000;

    return Note;
  }

  public int getNoteCollision(Vector myListTrunk,String Algo){
    //int Note = 1000 * this.getNbBoites(Ancrage1,Ancrage2,Algo);
    int Note = 0;
    int nb = 0;
    if (Algo.equals("trunkDirect") && this.positionRelative.equals("standard")) return 5000;

    for (int i=0; i < myListTrunk.size(); i++)
    {
      Trunk theTrunk = (Trunk)myListTrunk.elementAt(i);
      nb += this.getnbCollision(theTrunk, this.ListOtherTrunk);
    }


    return nb * 5000;
  }

  public int getNoteDistance(Vector myListTrunk,String Algo){
    //int Note = this.Computedistance(Ancrage1,Ancrage2);

    int Note=0;

    for (int i=0; i < myListTrunk.size(); i++)
    {
      Trunk theTrunk = (Trunk)myListTrunk.elementAt(i);
      Note += this.Computedistance(theTrunk.x1, theTrunk.y1, theTrunk.x2, theTrunk.y2);
    }

     //return 0;
    return Note;

  }

  public int getNoteLibelleOM(Vector myListTrunk,String Algo){
    int Note = 0;
    if ((!Algo.equals("trunkDirect")) || this.isVertical)   return 0;

    //int lgLabel=fm.stringWidth(this.Label);
    //int lgLabel=this.Label.length();
    int lgLabel=Utils.getStringWidth(this.Label, this.myOM.Style,this.myOM.Taille,this.myOM.Epaisseur);
    Trunk theTrunk = (Trunk)myListTrunk.elementAt(0);
    int lgTrunk = this.Computedistance(theTrunk.x1,theTrunk.y1,theTrunk.x2,theTrunk.y2);

    if (lgLabel >lgTrunk +10)
    {
      Note = 500;
    }
   return Note;
  }

  public int Computedistance(int x1, int y1, int x2, int y2){
    double d = Math.pow((x1 - x2),2) + Math.pow((y1 - y2),2) ;
    d = Math.sqrt(d);

    return (int)d;
  }

  public int getNote(Ancrage Ancrage1, Ancrage Ancrage2, String Algo){

    Vector myListTrunk=null;

    if (Ancrage1.Label.equals("start")&& Ancrage2.Cardinalite.equals("SW") && Algo.equals("trunk180B") && !this.isVertical) return 0;
    if (Ancrage1.Label.equals("start")&& Ancrage2.Cardinalite.equals("N") && Algo.equals("trunkDirect") && this.isVertical) return 0;

    if (Algo.equals("trunk90xy")) myListTrunk = this.Buildtrunk90xy(Ancrage1,Ancrage2);
    else if (Algo.equals("trunk90yx")) myListTrunk =this.Buildtrunk90yx(Ancrage1,Ancrage2);
    else if (Algo.equals("trunk180B")) myListTrunk =this.Buildtrunk180B(Ancrage1,Ancrage2);
    else if (Algo.equals("trunk180H")) myListTrunk =this.Buildtrunk180H(Ancrage1,Ancrage2);

    else if (Algo.equals("trunk180G")) myListTrunk = this.Buildtrunk180G(Ancrage1,Ancrage2);
    else if (Algo.equals("trunk180D")) myListTrunk = this.Buildtrunk180D(Ancrage1,Ancrage2);
    else if (Algo.equals("trunkDirect")) myListTrunk = this.BuildtrunkDirect(Ancrage1,Ancrage2);

    this.NoteEtat = getNoteEtat(Ancrage1,Ancrage2);
    this.NoteCollision = getNoteCollision(myListTrunk,Algo);
    this.NoteDistance = getNoteDistance(myListTrunk,Algo);
    this.NoteLibelleOM = getNoteLibelleOM(myListTrunk,Algo);

    this.theNote =NoteEtat+NoteCollision+NoteDistance+NoteLibelleOM;



    return this.theNote;
  }

  public void positionne(){

    int new_distance=-1;
    int bestNote = 100000;
    int theNote = 100000;

    Ancrage new_AncrageOrigine=null;
    Ancrage new_AncrageDest=null;
    int index_orig=-1;
     int index_dest=-1;
     String Algo="";
     //ElectiveChemin theElectiveChemin =null;
     //String theAlgo="";
     String ElectiveAlgo="";
     int theNbPas=0;
    this.positionRelative = this.getListePositionRelative(this.ActiviteOrigine,this.ActiviteDestination);
    for (int i=0; i < this.ActiviteOrigine.ListeAncrages.size();i++){
      new_AncrageOrigine = (Ancrage)this.ActiviteOrigine.ListeAncrages.elementAt(i);

      for (int j=0; j < this.ActiviteDestination.ListeAncrages.size();j++){
        new_AncrageDest = (Ancrage)this.ActiviteDestination.ListeAncrages.elementAt(j);

        this.ListeAlgo=this.initListeAlgo(new_AncrageOrigine,new_AncrageDest);

        for (int k = 0; k < this.ListeAlgo.size(); k++) {
          ElectiveAlgo = (String)this.ListeAlgo.elementAt(k);

          theNote = this.getNote(new_AncrageOrigine, new_AncrageDest, ElectiveAlgo);
          /*
          if (this.ActiviteDestination.nom.equals("Vois 4G-4G flux"))
          {
            System.out.println(new_AncrageOrigine.Cardinalite+ "->" + new_AncrageDest.Cardinalite+" -- ListeAlgo= "+this.ListeAlgo+" -- ElectiveAlgo= "+ElectiveAlgo +" -- bestAlgo= "+this.bestAlgo+ ":: NoteCollision = "+this.NoteCollision+ ":: note = "+theNote);
          }
          */

          if (theNote < bestNote) {
            bestNote =theNote;
            index_orig=i;
            index_dest=j;
            this.AncrageOrigine = new_AncrageOrigine;
            this.AncrageDest = new_AncrageDest;
            this.bestAlgo = ElectiveAlgo;
            theNbPas = 1;
            //break;
          }

        }
      }

    }

    this.note = bestNote;
    this.AncrageOrigine.etat = "occupe";
    this.AncrageDest.etat = "occupe";

/*
    if (this.ActiviteDestination.nom.equals("Vois 4G-4G flux"))
    {
      System.out.println("*** " +this.AncrageOrigine.Cardinalite+ "->"  +this.AncrageDest.Cardinalite +"  :: this.bestAlgo= "+this.bestAlgo+ ":: note = "+bestNote);
          }
*/
  }

  public void trunk180B(int nbPas){
    Vector myListTrunk=this.Buildtrunk180B(this.AncrageOrigine, this.AncrageDest);

int y_h=0;
    for (int i=0; i < myListTrunk.size();i++){
      Trunk theTrunk = (Trunk)myListTrunk.elementAt(i);
      this.ListTrunk.addElement(theTrunk);
      theTrunk.draw();
      //theTrunk.dump();
      if (theTrunk.isHorizontal) y_h = theTrunk.y1;

    }

//    this.myOM = new OM(this.Label,Math.min(this.AncrageDest.x,this.AncrageOrigine.x) + Trunk.Marge_H,Math.max(this.AncrageDest.y,this.AncrageOrigine.y) + nbPas*Trunk.pas - Trunk.Marge_V);
    //this.myOM = new OM(this.Label,(this.AncrageDest.x+this.AncrageOrigine.x)/2 -this.Label.length()/2,y_h+ 4*Trunk.Marge_V);
    //this.myOM = new OM(this.Label,(this.AncrageDest.x+this.AncrageOrigine.x)/2 -this.Label.length()/2,y_h+ 1*Trunk.Marge_V);
    this.myOM = new OM(this.Label,-1,-1);
    this.myOM.x = (this.AncrageDest.x+this.AncrageOrigine.x)/2 -Utils.getStringWidth(this.Label, this.myOM.Style,this.myOM.Taille,this.myOM.Epaisseur)/2;
    this.myOM.y = y_h+ 1*Trunk.Marge_V;

}
  public void trunk90yx(){

  Vector myListTrunk=this.Buildtrunk90yx(this.AncrageOrigine, this.AncrageDest);

  for (int i=0; i < myListTrunk.size();i++){
    Trunk theTrunk = (Trunk)myListTrunk.elementAt(i);
    this.ListTrunk.addElement(theTrunk);
    theTrunk.draw();
    //theTrunk.dump();

  }

//this.myOM = new OM(this.Label,Math.min(this.AncrageOrigine.x,this.AncrageDest.x)  + Trunk.Marge_H,this.AncrageOrigine.y - Trunk.Marge_V );
  if (this.AncrageOrigine.x < this.AncrageDest.x)
  {
    if (this.AncrageOrigine.y > this.AncrageDest.y)
    {
      //this.myOM = new OM(this.Label,this.AncrageDest.x- this.Label.length() - Trunk.Marge_H,this.AncrageDest.y - Trunk.Marge_V );
      this.myOM = new OM(this.Label,-1,-1);
      this.myOM.x = this.AncrageDest.x- Utils.getStringWidth(this.Label, this.myOM.Style,this.myOM.Taille,this.myOM.Epaisseur) - 4* Trunk.Marge_H;
      //this.myOM.y =this.AncrageDest.y - Trunk.Marge_V;
      this.myOM.y =this.AncrageDest.y - 5 * Trunk.Marge_V;
    }
      else
      {
      //this.myOM = new OM(this.Label,this.AncrageDest.x- this.Label.length() - Trunk.Marge_H,this.AncrageDest.y + 4*Trunk.Marge_V );
      //this.myOM = new OM(this.Label,this.AncrageDest.x- this.Label.length() - Trunk.Marge_H,this.AncrageDest.y + 1*Trunk.Marge_V );
      this.myOM = new OM(this.Label,-1,-1);
      this.myOM.x = this.AncrageDest.x- Utils.getStringWidth(this.Label, this.myOM.Style,this.myOM.Taille,this.myOM.Epaisseur) - 4 * Trunk.Marge_H;
      this.myOM.y =this.AncrageDest.y + 1*Trunk.Marge_V;
      }
    }
  else
  {
  if (this.AncrageOrigine.y > this.AncrageDest.y)
  {
    this.myOM = new OM(this.Label,this.AncrageOrigine.x+Trunk.Marge_H,this.AncrageDest.y - Trunk.Marge_V );
  }
  else
  {
    //this.myOM = new OM(this.Label,Math.max(this.AncrageDest.x + Trunk.Marge_H,this.AncrageOrigine.x-this.Label.length()),this.AncrageDest.y + 4*Trunk.Marge_V );
    //this.myOM = new OM(this.Label,Math.max(this.AncrageDest.x + Trunk.Marge_H,this.AncrageOrigine.x-this.Label.length()),this.AncrageDest.y + 1*Trunk.Marge_V );
    this.myOM = new OM(this.Label,-1,-1);
    this.myOM.x = Math.max(this.AncrageDest.x + Trunk.Marge_H,this.AncrageOrigine.x-Utils.getStringWidth(this.Label, this.myOM.Style,this.myOM.Taille,this.myOM.Epaisseur));
    this.myOM.y = this.AncrageDest.y + 1*Trunk.Marge_V;
  }
  }

}
  public void trunk90xy(){
    Vector myListTrunk = this.Buildtrunk90xy(this.AncrageOrigine,
                                             this.AncrageDest);

    for (int i = 0; i < myListTrunk.size(); i++) {
      Trunk theTrunk = (Trunk) myListTrunk.elementAt(i);
      this.ListTrunk.addElement(theTrunk);
      theTrunk.draw();
      //theTrunk.dump();

    }

//this.myOM = new OM(this.Label,Math.min(this.AncrageOrigine.x,this.AncrageDest.x)  + Trunk.Marge_H,this.AncrageOrigine.y - Trunk.Marge_V );
// ********* je remplace fm.stringWidth(this.Label) -> this.Label.length

    if (this.AncrageOrigine.x < this.AncrageDest.x)
    {
      if (this.AncrageOrigine.y < this.AncrageDest.y)
      {
        this.myOM = new OM(this.Label, this.AncrageOrigine.x + Trunk.Marge_H, this.AncrageOrigine.y - Trunk.Marge_V);
      }
      else
      {
        this.myOM = new OM(this.Label, this.AncrageOrigine.x + Trunk.Marge_H, this.AncrageOrigine.y +  Trunk.Marge_V);
      }
    }
    else
    {
      if (this.AncrageOrigine.y < this.AncrageDest.y)
      {
        //this.myOM = new OM(this.Label, this.AncrageOrigine.x - Utils.getStringWidth(this.Label, this.myOM.Style,this.myOM.Taille,this.myOM.Epaisseur)- Trunk.Marge_H,this.AncrageOrigine.y - Trunk.Marge_V);
        this.myOM = new OM(this.Label, -1,-1);
        this.myOM.x = this.AncrageOrigine.x - Utils.getStringWidth(this.Label, this.myOM.Style,this.myOM.Taille,this.myOM.Epaisseur)- Trunk.Marge_H;
        this.myOM.y = this.AncrageOrigine.y + Trunk.Marge_V;
      }
      else
      {
        //this.myOM = new OM(this.Label,this.AncrageOrigine.x - Utils.getStringWidth(this.Label, this.myOM.Style,this.myOM.Taille,this.myOM.Epaisseur) - Trunk.Marge_H,this.AncrageOrigine.y + 4 * Trunk.Marge_V);
        this.myOM = new OM(this.Label, -1,-1);
        this.myOM.x = this.AncrageOrigine.x - Utils.getStringWidth(this.Label, this.myOM.Style,this.myOM.Taille,this.myOM.Epaisseur) - Trunk.Marge_H;
        this.myOM.y = this.AncrageOrigine.y - Trunk.Marge_V;
      }
    }
}


  public void trunk180H(int nbPas){
    Vector myListTrunk=this.Buildtrunk180H(this.AncrageOrigine, this.AncrageDest);
    int y_h=0;
    for (int i=0; i < myListTrunk.size();i++){
      Trunk theTrunk = (Trunk)myListTrunk.elementAt(i);
      this.ListTrunk.addElement(theTrunk);
      theTrunk.draw();
      //theTrunk.dump();
      if (theTrunk.isHorizontal) y_h = theTrunk.y1;

    }


//    this.myOM = new OM(this.Label,Math.min(this.AncrageDest.x,this.AncrageOrigine.x) + Trunk.Marge_H,this.AncrageDest.y - nbPas*Trunk.pas- Trunk.Marge_V);
    this.myOM = new OM(this.Label,(this.AncrageDest.x+this.AncrageOrigine.x)/2,y_h- Trunk.Marge_V);


}

  public void trunk180G(int nbPas){
    Vector myListTrunk=this.Buildtrunk180G(this.AncrageOrigine, this.AncrageDest);

    for (int i=0; i < myListTrunk.size();i++){
      Trunk theTrunk = (Trunk)myListTrunk.elementAt(i);
      this.ListTrunk.addElement(theTrunk);
      theTrunk.draw();
      //theTrunk.dump();

    }

    if (
      (this.isVertical && (Math.abs(this.ActiviteOrigine.ligne - this.ActiviteDestination.ligne) == 1))
      ||
      (!this.isVertical && (Math.abs(this.ActiviteOrigine.colonne- this.ActiviteDestination.colonne) == 1))

      )
    //if (true)
      this.myOM = new OM(this.Label,this.AncrageOrigine.x- nbPas*Trunk.pas + Trunk.Marge_H,(this.AncrageOrigine.y+this.AncrageDest.y)/2);
    else
      this.myOM = new OM(this.Label,this.AncrageOrigine.x- nbPas*Trunk.pas + Trunk.Marge_H,this.AncrageOrigine.y+6*Trunk.Marge_V);



}

  public void trunk180D(int nbPas){
        int x_h=0;
    Vector myListTrunk=this.Buildtrunk180D(this.AncrageOrigine, this.AncrageDest);

    for (int i=0; i < myListTrunk.size();i++){
      Trunk theTrunk = (Trunk)myListTrunk.elementAt(i);
      this.ListTrunk.addElement(theTrunk);
      theTrunk.draw();
      //theTrunk.dump();
      if (theTrunk.isHorizontal) x_h = theTrunk.x1;
    }

    this.myOM = new OM(this.Label,x_h + Trunk.Marge_H,(this.AncrageOrigine.y+this.AncrageDest.y)/2 + nbPas*Trunk.pas);

}


  public void trunkDirect(){
    Trunk theTrunk = new Trunk (AncrageOrigine.Label + "=>" +AncrageDest.Label,this.AncrageOrigine.x,this.AncrageOrigine.y,this.AncrageDest.x,this.AncrageDest.y,true);
    this.ListTrunk.addElement(theTrunk);
    theTrunk.draw();

    this.myOM = new OM(this.Label,Math.min(this.AncrageOrigine.x,this.AncrageDest.x)  + Trunk.Marge_H,(this.AncrageOrigine.y + this.AncrageDest.y)/2 -Trunk.Marge_V);
    //this.myOM = new OM(this.Label,Math.min(this.AncrageOrigine.x,this.AncrageDest.x)  + Trunk.Marge_H,(this.AncrageOrigine.y + this.AncrageDest.y)/2);

  }

  public void build_ListeTrunk(String Algo, int nbPas){
    if ( Algo.equals("trunk90xy") ) this.trunk90xy();
    else if ( Algo.equals("trunk90yx") ) this.trunk90yx();

    else if ( Algo.equals("trunk180B") ) this.trunk180B(nbPas);
    else if ( Algo.equals("trunk180H") ) this.trunk180H(nbPas);

    else if ( Algo.equals("trunk180G") ) this.trunk180G(nbPas);
    else if ( Algo.equals("trunk180D") ) this.trunk180D(nbPas);

    else if ( Algo.equals("trunkDirect") ) this.trunkDirect();

    else this.trunkDirect();
  }

  public void draw() {

    //if (true) this.positionne(fm);
    if (!this.ispaint)
    {
      this.positionne();
      this.ispaint = true;
    }
    if ((this.AncrageDest != null) && (this.AncrageOrigine != null))
    {
      this.build_ListeTrunk(this.bestAlgo, 1);
    }

  }

  public static void main(String[] args) {
    Path path = new Path();
    int mySize = Utils.getStringWidth("Lixian", "Times new Roman",12, Font.BOLD);
    System.out.print("Size= "+mySize);
  }
}

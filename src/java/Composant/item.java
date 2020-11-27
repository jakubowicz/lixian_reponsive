package Composant; 

import accesbase.Connexion;
import java.util.Date;

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
public class item {
  public static int nbNiveaux = 0;
  public treeView itemTreeview = null;
  public int id;
  public String nom="";
  public String desc="";
  public String type="";
  
  public int ordre;
  public int security;
  public int idEtat;
  public int nbOrig;
  public int nbDest;
  
  public String valeur="";
  public Date date;
  public int nb=0;
  
  public String alias="";
  public String display="";
  public String req="";
  

  public item() {

  }

  public item(int nb) {
    nbNiveaux = nb;
  }

  public item(int id, String nom, String alias) {
    this.id=id;
    this.nom=nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''").replace('\u0092',' ');
    this.alias=alias.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
    this.display="";
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("id="+id);
    System.out.println("nom="+nom);
    System.out.println("alias="+alias);
    System.out.println("display="+display);
    System.out.println("==================================================");
  }

  public static void main(String[] args) {
    item item = new item();
  }
}

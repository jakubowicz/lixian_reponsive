package Statistiques; 
import java.util.*;
import accesbase.Connexion;

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
public class LigneStat {
  public String UserName ;
  public String dateCnx;
  public String HeureCnx;
  public String pageName;
  public String Machine;

  public String dateDeCnx;
  public String etat;
  public String item;
  public String Carto;
  public String nb;

  public String Login;
  public String prenomMembre;
  public String nomMembre;
  public String fonctionMembre;
  public String nomService;
  public String nomDirection;

  public String jreVersion;


  public LigneStat( String nb, String Login, String prenomMembre, String nomMembre, String fonctionMembre, String nomService, String nomDirection, String jreVersion) {
    this.nb = nb;
    this.Login = Login;
    this.prenomMembre = prenomMembre;
    this.nomMembre = nomMembre;
    this.fonctionMembre = fonctionMembre;
    this.nomService = nomService;
    this.nomDirection = nomDirection;
    this.jreVersion = jreVersion;
  }

  public LigneStat(String UserName,String dateCnx,String HeureCnx,String pageName,String Machine) {
    this.UserName = UserName;
    this.dateCnx = dateCnx;
    this.HeureCnx = HeureCnx;
    this.pageName = pageName;
    this.Machine = Machine;
  }

  public LigneStat(String UserName,String dateCnx,String dateDeCnx,String pageName,String etat,String Machine,String item,String Carto,String nb,String jreVersion) {
    this.UserName = UserName;
    this.dateCnx = dateCnx;
    this.dateDeCnx= dateDeCnx;
    this.pageName = pageName;
    this.etat= etat;
    this.Machine = Machine;
    this.item= item;
    this.Carto=Carto;
    this.nb= nb;
    this.jreVersion= jreVersion;
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("UserName="+this.UserName);
    System.out.println("dateCnx="+this.dateCnx);
    System.out.println("HeureCnx="+this.HeureCnx);
    System.out.println("pageName="+this.pageName);
    System.out.println("Machine="+this.Machine);
    System.out.println("==================================================");
  }
  public static void main(String[] args) {
    LigneStat lignestat = new LigneStat("","","","","");
  }
}

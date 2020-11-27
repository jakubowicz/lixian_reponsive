package Automate; 
import accesbase.*;

import java.util.Vector;
import java.sql.*;
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
public class Automate {
  int idVersion = -1;
  public Vector ListeLigneAutomate= new Vector(10);
  public Vector ListeLigneAutomateEtatAdmin= new Vector(10);
  public Vector ListeLigneAutomateEtat= new Vector(10);
  public String  Etat = "Inexistant";
  String Action = "";
  String nexEtat = "";
  String req = "" ;
  ResultSet rs = null;
  public String Admin="no";
  String TypeForm = "";

  public Automate(int idVersion, String TypeForm, String nomBase,Connexion myCnx, Statement st, String transaction, String Admin) {
    this.idVersion = idVersion;
    this.TypeForm = TypeForm;
    this.Etat = this.getEtat(idVersion, nomBase,myCnx, st, transaction);
    this.Admin=Admin;
    this.getLigneAutomate(idVersion,nomBase,myCnx, st,transaction);
  }

  public Automate() {
  }

  public Vector getLigneAutomateEtat(String nomBase,Connexion myCnx, Statement st){
    req = "SELECT DISTINCT TypeEtat.idTypeEtat, automate.etat";
    req+=" FROM         automate INNER JOIN";
    req+="                 TypeEtat ON automate.etat = TypeEtat.nom2TypeEtat";
    req+=" ORDER BY automate.etat";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    int  idTypeEtat =-1;
    String etat ="";
    String Admin ="";

    try {
      while (rs.next()) {
        idTypeEtat= rs.getInt("idTypeEtat");
        etat= rs.getString("etat");
        EntreeAutomateEtat myEntreeAutomatEtat = new EntreeAutomateEtat(idTypeEtat,etat,"");
        if (etat.equals(this.Etat) )
          myEntreeAutomatEtat.display = "";
        else
          myEntreeAutomatEtat.display = "none";
        this.ListeLigneAutomateEtat.addElement(myEntreeAutomatEtat);

        }


    } catch (SQLException s) {s.getMessage();}

    return null;
  }

  public Vector getLigneAutomateEtatAdmin(String nomBase,Connexion myCnx, Statement st){
    req = "SELECT DISTINCT TypeEtat.idTypeEtat, automate.etat, automate.Admin";
    req+=" FROM         automate INNER JOIN";
    req+="                 TypeEtat ON automate.etat = TypeEtat.nom2TypeEtat";
    req+=" ORDER BY automate.etat";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    int  idTypeEtat =-1;
    String etat ="";
    String Admin ="";

    try {
      while (rs.next()) {
        idTypeEtat= rs.getInt("idTypeEtat");
        etat= rs.getString("etat");
        Admin= rs.getString("Admin");
        EntreeAutomateEtat myEntreeAutomatEtat = new EntreeAutomateEtat(idTypeEtat,etat,Admin);
        if ((etat.equals(this.Etat)) && (Admin.equals(this.Admin)))
          myEntreeAutomatEtat.display = "";
        else
          myEntreeAutomatEtat.display = "none";
        this.ListeLigneAutomateEtatAdmin.addElement(myEntreeAutomatEtat);

        }


    } catch (SQLException s) {s.getMessage();}

    return null;
  }


  public Vector getLigneAutomate(int idVersion, String nomBase,Connexion myCnx, Statement st, String transaction){
    if ((this.Admin==null) || (!this.Admin.equals("yes")))
      req = "SELECT     actionBouton.bouton, automate.action, automate.etatNext, TypeEtat.idTypeEtat FROM automate INNER JOIN actionBouton ON automate.action = actionBouton.action INNER JOIN TypeEtat ON automate.etatNext = TypeEtat.nom2TypeEtat WHERE (automate.etat = '"+this.Etat+"') AND (automate.Utilisateur = 'yes')";
    else
      req = "SELECT     actionBouton.bouton, automate.action, automate.etatNext, TypeEtat.idTypeEtat FROM automate INNER JOIN actionBouton ON automate.action = actionBouton.action INNER JOIN TypeEtat ON automate.etatNext = TypeEtat.nom2TypeEtat WHERE (automate.etat = '"+this.Etat+"')  AND (automate.Admin = 'yes')";


    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    String bouton ="";
    String action ="";
    String etatNext ="";
    int idetatNext=0;

    try {
      while (rs.next()) {
        bouton= rs.getString(1);
        action= rs.getString(2);
        etatNext= rs.getString(3);
        idetatNext = rs.getInt(4);
        EntreeAutomate myLigneAutomate = new EntreeAutomate(bouton,action,etatNext,idetatNext);
        this.ListeLigneAutomate.addElement(myLigneAutomate);

        }


    } catch (SQLException s) {s.getMessage();}

    return null;
  }

  public String getEtat(int idVersion, String nomBase,Connexion myCnx, Statement st, String transaction){
  if ((this.TypeForm.equals("CreationVersion")) || (idVersion == -1) || (idVersion == 0)) this.Etat =  "Inexistant";
  else
  {
    req = "SELECT TypeEtat.nom2TypeEtat FROM   VersionSt INNER JOIN   TypeEtat ON VersionSt.etatFicheVersionSt = TypeEtat.idTypeEtat WHERE  (VersionSt.idVersionSt = "+idVersion+")";
    rs = myCnx.ExecReq(st, this.getClass().getName(), req);
    int nbExclusion=0;
      try { rs.next(); this.Etat = rs.getString(1);  } catch (SQLException s) {s.getMessage();}
  }

  //Connexion.trace("*********************","this.Etat",this.Etat);
  return this.Etat;
}


  public Vector getListeLigneAutomate(){

    return null;
  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("idVersion=" + this.idVersion);
    System.out.println("Etat=" + this.Etat);
    System.out.println("Admin=" + this.Admin);
    System.out.println("this.ListeLigneAutomateEtatAdmin.size()=" +
                       this.ListeLigneAutomateEtatAdmin.size());
    for (int i = 0; i < this.ListeLigneAutomateEtatAdmin.size(); i++) {
      EntreeAutomateEtat myLigneAutomateEtat = (EntreeAutomateEtat)this.ListeLigneAutomateEtatAdmin.elementAt(i);

      System.out.println("" + i + ") " + "Etat=" + myLigneAutomateEtat.Etat);
      System.out.println("" + i + ") " + "idEtat=" + myLigneAutomateEtat.idEtat);
      System.out.println("" + i + ") " + "Admin=" + myLigneAutomateEtat.Admin);
      System.out.println("" + i + ") " + "display=" + myLigneAutomateEtat.display);
      System.out.println("myLigneAutomateEtat.ListeLigneAutomate.size()=" +
                       myLigneAutomateEtat.ListeLigneAutomate.size());
      for (int j = 0; j < myLigneAutomateEtat.ListeLigneAutomate.size(); j++)
      {
        EntreeAutomate myLigneAutomate = (EntreeAutomate)myLigneAutomateEtat.ListeLigneAutomate.elementAt(j);
        System.out.println("==================================================");
        System.out.println("" + j + ") " + "action=" + myLigneAutomate.action);
        System.out.println("" + j + ") " + "bouton=" + myLigneAutomate.bouton);
        System.out.println("" + j + ") " + "etatNext=" + myLigneAutomate.etatNext);
        System.out.println("" + j + ") " + "idetatNext=" + myLigneAutomate.idetatNext);
        System.out.println("==================================================");
      }

      System.out.println("==================================================");
    }
  }
  void addLigneAutomate(EntreeAutomate uneLigneAutomate){
    ListeLigneAutomate.addElement(uneLigneAutomate);
  }

  public static void main(String[] args) {
    Connexion myCnx=null;
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    st = myCnx.Connect();

    int idVersiont=1070;
    ST theSt = new ST(idVersiont, "idVersionSt");
    theSt.getAllFromBd(myCnx.nomBase,myCnx,st);
    //theSt.dump();

    Automate myAutomate = new Automate();
    myAutomate.Etat  =myAutomate.getEtat(idVersiont,myCnx.nomBase,myCnx,  st,"");
    System.out.println("@1: Etat="+myAutomate.Etat);
    myAutomate.Admin="yes";
    System.out.println("@1: Admin="+myAutomate.Admin);
    myAutomate.getLigneAutomateEtat(myCnx.nomBase,myCnx,  st);
    System.out.println("@2:myAutomate.ListeLigneAutomateEtatAdmin.size()="+myAutomate.ListeLigneAutomateEtatAdmin.size());
    for (int i = 0; i < myAutomate.ListeLigneAutomateEtatAdmin.size(); i++) {
      EntreeAutomateEtat myLigneAutomateEtat = (EntreeAutomateEtat)myAutomate.ListeLigneAutomateEtatAdmin.elementAt(i);
      System.out.println("-------------------- i="+i+"----------------------------------");
      System.out.println(" @3: Etat="+myLigneAutomateEtat.Etat);
      System.out.println(" @4: Admin="+myLigneAutomateEtat.Admin);
      System.out.println(" @5: display="+myLigneAutomateEtat.display);
      myLigneAutomateEtat.getLigneAutomate(idVersiont,myCnx.nomBase,myCnx,  st,"");
      System.out.println(" @6: myLigneAutomateEtat.ListeLigneAutomate.size()="+myLigneAutomateEtat.ListeLigneAutomate.size());
      System.out.println("--------------------------------------------------------------");
    }
    //myAutomate.dump();
  }



}

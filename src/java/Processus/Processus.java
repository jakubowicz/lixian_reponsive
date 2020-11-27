package Processus; 
import java.util.Vector;
import java.sql.*;
import accesbase.*;
import java.util.Date;
import ST.ST;
import Graphe.Forme;
/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Soci�t� : </p>
 * @author non attribuable
 * @version 1.0
 */

public class Processus {
  public int id;
  public String nom="";
  public String desc="";
  public String idSi;
  public String nomSi="";
  public String idMacroSt;
  public String nomMacroSt="";
  public String idAuteur;
  public String respMOA="";
  public Vector ListePhases = null;
  public String lienImage="";
  public String disposition="";
  public String Type="";

  public String createur="";
  public String modificateur="";

  public static Vector ListeProcessus = new Vector(10);
  public  Vector ListeActivite = new Vector(10);
  public  Vector ListeSt = new Vector(10);

  public  Vector ListeTrunk = new Vector (100);
  public  Vector ListeOM = new Vector (100);

  public Forme Bouton_Plus=null;
  public Forme Bouton_Reset=null;
  public Forme Bouton_Moins=null;
  public Forme Bouton_V=null;
  public Forme Bouton_H=null;
  public Forme Libelle_PlusMoins=null;
  public Forme DispositionH_V=null;

  public static int Activite_L=200;
  public boolean isVertical = true;

  private String req="";


  public Processus(int id) {
    this.id=id;
    this.ListePhases = new Vector(10);
  }

  public Processus() {
  }

  public Processus(int id, String nom, String desc,String idSi,String idMacroSt,String idAuteur,String respMOA, String lienImage,String disposition,String Type) {
    this.id=id;

    this.nom=nom;
    this.desc=desc;
    this.idSi=idSi;
    this.idMacroSt=idMacroSt;
    this.idAuteur=idAuteur;
    if ((respMOA!=null) && (!respMOA.equals("null"))) this.respMOA=respMOA;else this.respMOA="";

    this.ListePhases = new Vector(10);
    if ((lienImage!=null) && (!lienImage.equals("null"))) this.lienImage=lienImage;else this.lienImage="";
    this.disposition=disposition;
    this.Type = Type;

  }
  public Processus(int id, String nom, String desc,String respMOA, String lienImage,String disposition) {
    this.id=id;
    this.nom=nom;
    this.desc=desc;
    this.lienImage=lienImage;

    this.ListePhases = new Vector(10);
    if ((respMOA!=null) && (!respMOA.equals("null"))) this.respMOA=respMOA;else this.respMOA="";

    this.lienImage=lienImage;
    this.disposition=disposition;
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){

    ResultSet rs = null;
    req = "SELECT Processus.nomProcessus, Processus.descProcessus, SI.nomSI, MacroSt.nomMacrost, Processus.respMOA, Processus.ProcessusCreateur,  Processus.ProcessusModificateur, Processus.lienImage, disposition, Processus.ProcessusSI, Processus.ProcessusMacroSt";
    req+=" FROM         SI INNER JOIN  Processus ON SI.idSI = Processus.ProcessusSI";
    req+=" INNER JOIN  MacroSt ON Processus.ProcessusMacroSt = MacroSt.idMacrost";
    req+="  WHERE Processus.idProcessus = "+this.id;
    rs = myCnx.ExecReq(st, nomBase, req);

    try { rs.next();
            this.nom = rs.getString(1);
            //c1.trace("::::::::::","nomProcessus",nomProcessus);
            this.desc = rs.getString(2).replaceAll("\r","").replaceAll("\n","<br>");;
            this.nomSi = rs.getString(3);
            this.nomMacroSt = rs.getString(4);
            String respMOA = rs.getString(5);
            if ((respMOA!=null) && (!respMOA.equals("null"))) this.respMOA=respMOA;else this.respMOA="";
            this.createur = rs.getString(6);
            this.modificateur = rs.getString(7);
            String lienImage  = rs.getString(8);
            if ((lienImage!=null) && (!lienImage.equals("null"))) this.lienImage=lienImage;else this.lienImage="";
            this.disposition  = rs.getString(9);
            this.idSi = rs.getString("ProcessusSI");
            this.idMacroSt = rs.getString("ProcessusMacroSt");
      } catch (SQLException s) {s.getMessage();}


  }

  public static void getListeProcessus(String nomBase,Connexion myCnx, Statement st){

    String req="SELECT  idProcessus, nomProcessus FROM  Processus   ORDER BY nomProcessus";
    ListeProcessus.removeAllElements();

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Processus theProcessus = null;
    try {
       while (rs.next()) {
         theProcessus = new Processus( rs.getInt(1), rs.getString(2), "","", "","") ;
         ListeProcessus.addElement(theProcessus);
       }
        } catch (SQLException s) {s.getMessage();}

 }

  public Phase addPhase(int id, int numPhase,String nom, String desc, int ordre){
    Phase thePhase = new Phase(this.nom,id,numPhase,nom,desc,ordre);
    this.ListePhases.addElement(thePhase);
    return thePhase;
  }
  public Phase getPhase(String nom){
    for (int i = 0; i < this.ListePhases.size(); i++)
    {
      Phase thePhase = (Phase)this.ListePhases.elementAt(i);
      if (thePhase.nom.equals(nom)) return thePhase;
    }
    return null;
  }

  public int getMaxActivite(){
    int maxActivite = 0;
  for (int i = 0; i < this.ListePhases.size(); i++)
  {
    Phase thePhase = (Phase)this.ListePhases.elementAt(i);
    if (thePhase.ListeActivite.size() > maxActivite) maxActivite=thePhase.ListeActivite.size();
  }
  return maxActivite;
}

public int getNbProc(String nomBase,Connexion myCnx, Statement st){
  ResultSet rs=null;
  String req = "";
  int nbProc = 0;
  req="SELECT COUNT(nomProcessus) AS nbProc FROM  Processus WHERE  (nomProcessus = '"+this.nom.replaceAll("'", "''") +"')";
  rs=myCnx.ExecReq(st,myCnx.nomBase,req);
  try { rs.next(); nbProc = rs.getInt(1);  } catch (SQLException s) {s.getMessage();}
return nbProc;
}

public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
  ResultSet rs = null;
  req = "delete FROM [Processus] where idProcessus = '"+this.id +"'";
      ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
      if (myError.cause == -1) return myError;
      
    return myError;      
}

public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){
  ResultSet rs = null;

  req = "INSERT INTO Processus ";
  //req+="(nomProcessus, descProcessus, ProcessusSI, ProcessusMacroSt, ProcessusAuteur, respMOA,lienImage,disposition) VALUES  (";
  req+="(nomProcessus, descProcessus, ProcessusSI, ProcessusMacroSt, ProcessusCreateur, respMOA,lienImage,disposition,Type) VALUES  (";
  req+="'"+this.nom.replaceAll("'", "''")+"'";
  req+=",'"+this.desc.replaceAll("'", "''")+"'";
  req+=","+this.idSi+"";
  req+=","+this.idMacroSt+"";
  req+=",'"+this.createur+"'";
  req+=",'"+this.respMOA.replaceAll("'", "''")+"'";
  req+=",'"+this.lienImage.replaceAll("'", "''")+"'";
  req+=",'"+this.disposition+"'";
  req+=",'"+this.Type+"'";
  req+=")";
//c1.trace("*********","req",""+req);
      ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
      if (myError.cause == -1) return myError;

  req="SELECT idProcessus FROM Processus WHERE  nomProcessus = '"+this.nom.replaceAll("'", "''") +"'";
  rs=myCnx.ExecReq(st,myCnx.nomBase,req,true,transaction);

  try { rs.next(); this.id = rs.getInt(1);  } catch (SQLException s) {s.getMessage();}

    return myError;
}

public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
  ResultSet rs = null;

  req = "DELETE FROM  Phase where PhaseProcessus  = '"+this.id +"'";
  ErrorSpecific myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
  if (myError.cause == -1) return myError;

  req = "UPDATE Processus SET ";
  req+=" nomProcessus='"+this.nom.replaceAll("'", "''")+"'";
  req+=",descProcessus='"+this.desc.replaceAll("'", "''")+"'";
  req+=",ProcessusSI="+this.idSi+"";
  req+=",ProcessusMacroSt="+this.idMacroSt+"";
  //req+=",ProcessusAuteur="+theProcessus.idAuteur+"";
  req+=",ProcessusModificateur='"+this.modificateur+"'";
  req+=",respMOA='"+this.respMOA.replaceAll("'", "''")+"'";
  req+=",lienImage='"+this.lienImage.replaceAll("'", "''")+"'";
  req+=",disposition='"+this.disposition+"'";
  req+=",Type='"+this.Type+"'";
           req+=" where idProcessus  = '"+this.id +"'";

  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
  if (myError.cause == -1) return myError;



  return myError;
}


public  int setFavoris(String nomBase,Connexion myCnx, Statement st, String Panier, String Login){

  int nbFavoris = 0;
  ResultSet rs=null;
  if (Panier != null)
  {
    //myCnx.trace(nomBase,"********************** Panier",Panier);
    if (Panier.equals("Ajout"))
      req = "INSERT Favoris ( nom, id, Type, Login) VALUES ('"+this.nom+"',  '"+this.id+"', '"+"PROCESSUS"+"','"+Login+"')";
    else
      req = "DELETE FROM Favoris WHERE Login ='"+Login+"'" + " AND id="+this.id;

    myCnx.ExecReq(st,nomBase,req);

    rs = myCnx.ExecReq(st, nomBase,  "SELECT count(*) as nb FROM Favoris WHERE Login ='"+Login+"'" + " AND id="+this.id);
    try { rs.next(); nbFavoris = rs.getInt(1); } catch (SQLException s) {s.getMessage();}

}
  return nbFavoris;
}

public  void getListePhases(String nomBase,Connexion myCnx, Statement st){

  String req="SELECT  Phase.idPhaseNP, Phase.nomPhaseNP, Phase.descPhaseNP, Phase.Ordre, Phase.significationPhaseNP FROM  Phase";
  req+=" INNER JOIN  Processus ON Phase.PhaseProcessus = Processus.idProcessus";
  req+=" WHERE Phase.PhaseProcessus = "+this.id;
  req+= " ORDER BY Phase.Ordre ASC";

 this.ListePhases.removeAllElements();

 ResultSet rs = myCnx.ExecReq(st, nomBase, req);
 Activite theActivite = null;
 try {
    while (rs.next()) {
      String idPhase = rs.getString(1);
      String nomPhase = rs.getString(2).replaceAll("\r","").replaceAll("\n","<br>");
      String descPhase = rs.getString(3).replaceAll("\r","").replaceAll("\n","<br>");
      int ordrePhase = rs.getInt("Ordre");

      Phase thePhase=this.addPhase(Integer.parseInt(idPhase),Integer.parseInt(idPhase),nomPhase,descPhase,ordrePhase);
    }
     } catch (SQLException s) {s.getMessage();}

}
   public  void getListePhasesNb(String nomBase,Connexion myCnx, Statement st){
     int nbPhases = 0;

     String req="SELECT  Phase.idPhaseNP, Phase.nomPhaseNP, Phase.descPhaseNP, Phase.Ordre, Phase.significationPhaseNP FROM  Phase";
     req+=" INNER JOIN  Processus ON Phase.PhaseProcessus = Processus.idProcessus";
     req+=" WHERE Phase.PhaseProcessus = "+this.id;
     req+= " ORDER BY Phase.Ordre ASC";

    this.ListePhases.removeAllElements();

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    Activite theActivite = null;
    try {
       while (rs.next()) {
         String idPhase = rs.getString(1);
         String nomPhase = rs.getString(2).replaceAll("\r","").replaceAll("\n","<br>");
         String descPhase = rs.getString(3).replaceAll("\r","").replaceAll("\n","<br>");
         int ordrePhase = rs.getInt("Ordre");

         Phase thePhase=this.addPhase(Integer.parseInt(idPhase),nbPhases,nomPhase,descPhase,ordrePhase);
         nbPhases++;
       }
        } catch (SQLException s) {s.getMessage();}

}

public  void getListeSt(String nomBase,Connexion myCnx, Statement st){

String req="exec PROC_ListeStByNomProc '"+this.nom.replaceAll("'", "''")+"'";


        this.ListeSt.removeAllElements();
        ResultSet rs = myCnx.ExecReq(st, nomBase, req);

        try {
           while (rs.next()) {
             int idVersionST=rs.getInt(2);
             String nomST=rs.getString(3);
             ST theST = new ST (-1,1,nomST,idVersionST);
             this.ListeSt.addElement(theST);
           }
            } catch (SQLException s) {s.getMessage();}

}

public  void getListeActivite(String nomBase,Connexion myCnx, Statement st){

  String req="";
  req+="SELECT     Activite.nomActivite, Activite.descActivite,Phase.nomPhaseNP, Activite.Timing, Activite.duree";
  req+=" FROM         Processus INNER JOIN";
  req+="                      Phase ON Processus.idProcessus = Phase.PhaseProcessus INNER JOIN";
  req+="                      Activite ON Phase.idPhaseNP = Activite.ActivitePhase";
  req+=" WHERE     (Processus.idProcessus = "+this.id+")";
  req+=" ORDER BY Ligne ASC";

 this.ListeActivite.removeAllElements();
 Activite theActivite = new Activite();
 theActivite.nom = "-- Début de Processus --";
 theActivite.donneeEntree="";
 this.ListeActivite.addElement(theActivite);
 

 ResultSet rs = myCnx.ExecReq(st, nomBase, req);
 theActivite = null;
 try {
    while (rs.next()) {
      theActivite = new Activite();
      theActivite.nom = rs.getString("nomActivite");
      theActivite.donneeEntree="";
      this.ListeActivite.addElement(theActivite);
    }
     } catch (SQLException s) {s.getMessage();}

}


public  void getListeActivite(String nomBase,Connexion myCnx, Statement st, String orderBy, String sens){

  String req="";
  req+="SELECT     Activite.nomActivite, Activite.descActivite,Phase.nomPhaseNP, Activite.Timing, Activite.duree";
  req+=" FROM         Processus INNER JOIN";
  req+="                      Phase ON Processus.idProcessus = Phase.PhaseProcessus INNER JOIN";
  req+="                      Activite ON Phase.idPhaseNP = Activite.ActivitePhase";
  req+=" WHERE     (Processus.idProcessus = "+this.id+")";
  req+=" ORDER BY "+orderBy+" "+sens;

 this.ListeActivite.removeAllElements();

 ResultSet rs = myCnx.ExecReq(st, nomBase, req);
 Activite theActivite = null;
 try {
    while (rs.next()) {
      String nomActivite = rs.getString(1);
      String descActivite = rs.getString(2).replaceAll("\r","").replaceAll("\n","<br>");;
      String nomPhaseNP = rs.getString(3);
      String Timing = rs.getString(4);
      int duree = rs.getInt(5);
      theActivite = new Activite(this.nom,nomPhaseNP,-1, nomActivite, descActivite, -1, -1, -1, "", "", Timing, "",duree,-1, "", "", "","");
      this.ListeActivite.addElement(theActivite);
    }
     } catch (SQLException s) {s.getMessage();}

}

 public void deleteRoadmap(String nomBase,Connexion myCnx, Statement st, String transaction){

   String req="";
   req +="delete";
   req +=" FROM         Roadmap";
   req +=" WHERE     (version IN";
   req +="                          (SELECT     nomProcessus";
   req +="                            FROM          Processus";
   req +="                         WHERE      (nomProcessus = '"+this.nom+"')))  ";

   myCnx.ExecReq(st, nomBase, req) ;
 }

 public void drawBouton_Plus() {

   this.Bouton_Plus = new Forme();
   this.Bouton_Plus.x=495;
   this.Bouton_Plus.y=3;
   this.Bouton_Plus.Largeur=32;
   this.Bouton_Plus.Hauteur=32;
   this.Bouton_Plus.icone="images/Edition/plus2.png";
  }

  public void drawBouton_Moins() {

    this.Bouton_Moins = new Forme(); // @@
    this.Bouton_Moins.x=560;
    this.Bouton_Moins.y=this.Bouton_Plus.y;
    this.Bouton_Moins.Largeur=this.Bouton_Plus.Largeur;
    this.Bouton_Moins.Hauteur=this.Bouton_Plus.Hauteur;
    this.Bouton_Moins.icone="images/Edition/minus2.png";
  }

  public void drawLibelle_PlusMoins() {

    this.Libelle_PlusMoins = new Forme();
    this.Libelle_PlusMoins.x=this.Bouton_Moins.x + 40;
    this.Libelle_PlusMoins.y=this.Bouton_Moins.y + 7;
    this.Libelle_PlusMoins.Largeur=15;
    this.Libelle_PlusMoins.Hauteur=15;
    this.Libelle_PlusMoins.CouleurTexte="black";
    this.Libelle_PlusMoins.Taille=10;
    this.Libelle_PlusMoins.Style="Verdana";
    this.Libelle_PlusMoins.Label="Adaptez la taille des couloirs du processus <<"+Processus.Activite_L+">>";
    this.Libelle_PlusMoins.Label= "";

  }

  public void drawBouton_Reset() {

    this.Bouton_Reset = new Forme(); // @@
    this.Bouton_Reset.x=530;
    this.Bouton_Reset.y=this.Bouton_Plus.y;
    this.Bouton_Reset.Largeur=this.Bouton_Plus.Largeur;
    this.Bouton_Reset.Hauteur=this.Bouton_Plus.Hauteur;
    this.Bouton_Reset.icone="images/Edition/target2.png";
  }

  public void drawBouton_V() {

    this.Bouton_V = new Forme(); // @@
    this.Bouton_V.x=15;
    this.Bouton_V.y=this.Bouton_Plus.y;
    this.Bouton_V.Largeur=this.Bouton_Plus.Largeur;
    this.Bouton_V.Hauteur=this.Bouton_Plus.Hauteur;
    this.Bouton_V.icone="images/Edition/V.png";
  }

  public void drawBouton_H() {

    this.Bouton_H = new Forme(); // @@
    this.Bouton_H.x=this.Bouton_V.x + this.Bouton_V.Largeur + 2;
    this.Bouton_H.y=this.Bouton_Plus.y;
    this.Bouton_H.Largeur=this.Bouton_Plus.Largeur;
    this.Bouton_H.Hauteur=this.Bouton_Plus.Hauteur;
    this.Bouton_H.icone="images/Edition/H.png";
  }

  public void drawDispositionH_V() {

    this.DispositionH_V = new Forme();

    this.DispositionH_V.x=this.Bouton_H.x+this.Bouton_H.Largeur+5;
    this.DispositionH_V.y=this.Bouton_Plus.y+7;
    this.DispositionH_V.Largeur=this.Bouton_Plus.Largeur;
    this.DispositionH_V.Hauteur=this.Bouton_Plus.Hauteur;
    this.DispositionH_V.CouleurTexte="black";
    this.DispositionH_V.Taille=12;
    this.DispositionH_V.Style="Arial";
    if (this.isVertical)
    {
      this.DispositionH_V.Label="Disposition Verticale";
    }
    else
    {
      this.DispositionH_V.Label="Disposition Horizontale";
    }
  }

  public String getActiviteSuivante(String nomActivite){
    for (int i = 0; i < this.ListePhases.size(); i++)
    {
      Phase thePhase = (Phase)this.ListePhases.elementAt(i);
      for (int j = 0; j < thePhase.ListeActivite.size(); j++)
      {
        Activite theActivite = (Activite)thePhase.ListeActivite.elementAt(j);
        if (theActivite.previousActivite.equals(nomActivite)) return theActivite.nom;

      }

    }
    return "-- Fin de Processus --";
  }

  public void addActeur(){

  }

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    //Connexion.trace(this.getClass().getName(),"processus.id",""+this.id);
    //Connexion.trace(this.getClass().getName(),"processus.nom",""+this.nom);
    System.out.println("==================================================");
    System.out.println("processus.id="+this.id);
    System.out.println("processus.nom="+this.nom);
    System.out.println("processus.nomSi="+this.nomSi);
    System.out.println("processus.idSi="+this.idSi);
    System.out.println("processus.respMOA="+this.respMOA);
    System.out.println("processus.idMacroSt="+this.idMacroSt);
    System.out.println("processus.idAuteur="+this.idAuteur);
    System.out.println("processus.desc="+this.desc);
    System.out.println("processus.lienImage="+this.lienImage);
     System.out.println("processus.disposition="+this.disposition);

    if ( this.ListePhases !=null)
    {
      for (int i = 0; i < this.ListePhases.size(); i++) {
        Phase thePhase = (Phase)this.ListePhases.elementAt(i);
        thePhase.dump();

      }
    }
      System.out.println("==================================================");
 }

  public static void main(String[] args) {
    Processus processus1 = new Processus(1,"","","","","");
    Phase phase1=processus1.addPhase(1,1,"phase1","ceci est la premiere phase",1);
  }

}

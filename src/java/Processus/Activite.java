package Processus; 
import java.util.*;
import accesbase.*;
import ST.*;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Graphe.Forme;
import Graphe.Ancrage;
import Graphe.Trunk;
import Graphe.Timing;
/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Soci�t� : </p>
 * @author non attribuable
 * @version 1.0
 */

public class Activite extends Forme{
  public String nomProcessus= "";
  public String nomPhase= "";
  public int idPhase;
  public int id=-1;
  public String nom= "";
  public String desc= "";
  public int idActeur;
  public int numActeur;
  public int numActivite;
  public String idSt;
  public String donneeEntree= "";
  public String donneeSortie= "";
  public String nextActivite = "";
  public Vector ListeAncrages= new Vector (20);
  public Vector ListepreviousActivite = new Vector (6);
  public String previousActivite= "";
  public Vector ListeST = null;
  public ST myST=null;
  public String Timing = "";
  public String Criticite = "Mineure";
  public int ActiviteProcessus = 0;
  public int numNote = 0;
  public int duree = 0;
  public int duree_heures = 0;
  public int duree_minutes = 0;
  public String TypeActeur= "";
  public String TypeIntervenant= "";
  public String TypeActivite = "normal";
  public String TypeUML = "activite";

  private String req;
  public ST theSt=null;

  public static int H=50;
  public static int L=140;
  public static int H_start=15;
  public static int L_start=15;

  public static int H_Synchro=20;
  public static int L_Synchro=L/2;

  public int num = 90;
  public int ligne=1;
  public int colonne=1;

  public String idActivite="";
  public boolean ispaint=false;
  public boolean isStart=false;
  public boolean isEnd=false;


  public Activite ActiviteStart=null;
  public Activite ActiviteStop=null;

  public Timing myTiming = null;

  public Activite(int id) {
    this.id = id;
    this.ListeST = new Vector(10);
    ST theST = new ST(1,"idSt");
    this.ListeST.addElement(theST);

  }

  public Activite(){

  }
  public Activite(String nomProcessus,String nomPhase,int id, String nom, String desc, int numActivite, int numActeur, int idActeur, String donneeSortie, String previousActivite, String Timing, String Criticite,int duree,int ActiviteProcessus, String TypeActeur, String TypeIntervenant, String TypeActivite,String TypeUML) {
    this.nomProcessus=nomProcessus;
    this.nomPhase=nomPhase;
    this.id=id;
    this.nom=nom;
    this.desc=desc;
    this.numActeur=numActeur;
    this.idActeur=idActeur;
    this.numActivite=numActivite;
    //if (donneeEntree.equals("")) this.donneeEntree="� d�finir"; else this.donneeEntree=donneeEntree;
    if (donneeSortie.equals("")) this.donneeSortie="a definir"; else this.donneeSortie=donneeSortie;
    this.previousActivite=previousActivite;
    if (Timing.equals("")) this.Timing="a definir"; else this.Timing=Timing;
    this.ListeST = new Vector(10);
    this.Criticite=Criticite;
    this.duree=duree;
    this.ActiviteProcessus=ActiviteProcessus;
    this.TypeActeur=TypeActeur;
    this.TypeIntervenant=TypeIntervenant;
    this.TypeActivite=TypeActivite;
    this.TypeUML=TypeUML;
    //this.ListepreviousActivite = this.getListePreviousBoite(previousActivite);

}

  public void getSt(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs = null;
    String req;
    req = "SELECT     idVersionSt,nomSt FROM    ListeST WHERE idSt  ="+ this.getST(0);
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {
      rs.next();
      int idVersionSt = rs.getInt(1);
      String nomSt = rs.getString(2);
      this.theSt = new ST(nomSt);
      theSt.idVersionSt = idVersionSt;
    }
    catch (SQLException s) {s.getMessage();}

  }

  public int getNbRoadmap(String nomBase,Connexion myCnx, Statement st, String nomProcessus){
    ResultSet rs = null;
    String req;
    int nbRoadmap=0;
    req = "SELECT   count(*) FROM  Roadmap WHERE     (idVersionSt ="+this.theSt.idVersionSt+") AND (version = '"+nomProcessus+"')";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try { rs.next();
    nbRoadmap = rs.getInt(1);
    } catch (SQLException s) {s.getMessage();}

    return nbRoadmap;

  }

  public String bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction, int idPhase, int ordre){
    ResultSet rs = null;
    String req;

    req = "INSERT INTO Activite ";
    req+="(nomActivite, descActivite, donneesSortie, ActivitePhase, Ligne, ActiviteActeur, numActeur, previousActivite, ListeST,Timing,Criticite,duree,ActiviteProcessus,TypeActeur,TypeIntervenant,TypeActivite,TypeUml,ListeIdSt,duree_heures,duree_minutes) VALUES  (";
    req+="'"+this.nom.replaceAll("'", "''")+"'";
    req+=",'"+this.desc.replaceAll("'", "''")+"'";
    //req+=",'"+theActivite.donneeEntree.replaceAll("'", "''")+"'";
    req+=",'"+this.donneeSortie.replaceAll("'", "''")+"'";
    req+=","+idPhase+"";
    req+=","+this.numActivite+"";
    req+=","+this.idActeur+"";
    //req+=","+theActivite.numActeur+"";
    req+=","+ordre+"";
    req+=",'"+this.previousActivite.replaceAll("'", "''")+"'";
    req+=",'"+this.getST(0)+"'";
    req+=",'"+this.Timing.replaceAll("'", "''")+"'";
    req+=",'"+this.Criticite+"'";
    req+=","+this.duree+"";
    req+=","+this.ActiviteProcessus+"";
     req+=",'"+this.TypeActeur+"'";
     req+=",'"+this.TypeIntervenant+"'";
     req+=",'"+this.TypeActivite+"'";
     req+=",'"+this.TypeUML+"'";
     req+=","+this.getIdST(0)+"";
     req+=","+this.duree_heures+"";
     req+=","+this.duree_minutes+"";

                          req+=")";

    if (myCnx.ExecUpdate(st,myCnx.nomBase,req,true,transaction) == -1)
        {
          myCnx.trace(nomBase,"*** ERREUR *** reqRoadmap",req);
          return req;
          }

          req="SELECT idActivite FROM   Activite WHERE nomActivite = '"+this.nom.replaceAll("'", "''") +"' AND (ActivitePhase ="+ idPhase +")";
          rs=myCnx.ExecReq(st,myCnx.nomBase,req,true,transaction);
          try { rs.next(); this.id = rs.getInt(1);  } catch (SQLException s) {s.getMessage();}



    return "OK";
}

  public String bd_Insert2(String nomBase,Connexion myCnx, Statement st, String transaction, int idPhase, int ordre){
    ResultSet rs = null;
    String req;

    req = "INSERT INTO Activite ";
    req+="(nomActivite, descActivite, donneesSortie, ActivitePhase, Ligne, ActiviteActeur, numActeur, previousActivite, ListeST,Timing,Criticite,duree,ActiviteProcessus,TypeActeur,TypeIntervenant,TypeActivite,TypeUml,ListeIdSt) VALUES  (";
    req+="'"+this.nom.replaceAll("'", "''")+"'";
    req+=",'"+this.desc.replaceAll("'", "''")+"'";
    //req+=",'"+theActivite.donneeEntree.replaceAll("'", "''")+"'";
    req+=",'"+this.donneeSortie.replaceAll("'", "''")+"'";
    req+=","+idPhase+"";
    req+=","+this.numActivite+"";
    req+=","+this.idActeur+"";
    //req+=","+theActivite.numActeur+"";
    req+=","+ordre+"";
    req+=",'"+this.previousActivite.replaceAll("'", "''")+"'";
    req+=",'"+this.getST(0)+"'";
    req+=",'"+this.Timing.replaceAll("'", "''")+"'";
    req+=",'"+this.Criticite+"'";
    req+=","+this.duree+"";
    req+=","+this.ActiviteProcessus+"";
     req+=",'"+this.TypeActeur+"'";
     req+=",'"+this.TypeIntervenant+"'";
     req+=",'"+this.TypeActivite+"'";
     req+=",'"+this.TypeUML+"'";
     req+=","+this.getIdST(0)+"";

                          req+=")";

    if (myCnx.ExecUpdate(st,myCnx.nomBase,req,true,transaction) == -1)
        {
          myCnx.trace(nomBase,"*** ERREUR *** reqRoadmap",req);
          return req;
          }

          req="SELECT idActivite FROM   Activite WHERE nomActivite = '"+this.nom.replaceAll("'", "''") +"' AND (ActivitePhase ="+ idPhase +")";
          rs=myCnx.ExecReq(st,myCnx.nomBase,req,true,transaction);
          try { rs.next(); this.id = rs.getInt(1);  } catch (SQLException s) {s.getMessage();}



    return "OK";
}

public String isPreviousActivite(String theActivite){

    for (int i=0; i < this.ListepreviousActivite.size(); i++)
       {
           Activite previousActivite = (Activite)this.ListepreviousActivite.elementAt(i);
           if (previousActivite.nom.equals(theActivite))
           {
               this.donneeEntree = previousActivite.donneeEntree;
               return "checked";
           }
       }
    
    return "";
}

  public void getListePreviousActivite2(){

    Activite thepreviousActivite = null;

      for (StringTokenizer u = new StringTokenizer(this.previousActivite, ";") ; u.hasMoreTokens() ; ) {
        String nomActivite = u.nextToken();
        thepreviousActivite=new Activite();
        thepreviousActivite.nom = nomActivite;
        this.ListepreviousActivite.addElement(thepreviousActivite);
      }

  }

  public void getListePreviousActivite(){
    Vector ListePreviousBoite = new Vector (6);
    int index = 0;
    Activite thepreviousActivite = null;
    for (StringTokenizer t = new StringTokenizer(this.previousActivite, "@") ; t.hasMoreTokens() ; ) {
      String str1 = t.nextToken();
      for (StringTokenizer u = new StringTokenizer(str1, ";") ; u.hasMoreTokens() ; ) {
        String nomActivite = u.nextToken();
        String donneeEntree = u.nextToken();
        thepreviousActivite=new Activite();
        thepreviousActivite.nom = nomActivite;
        thepreviousActivite.donneeEntree = donneeEntree;
        this.ListepreviousActivite.addElement(thepreviousActivite);
      }
      index++;
    }

    //return ListePreviousBoite;

  }
public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
  ErrorSpecific myError = new ErrorSpecific();
  ResultSet rs = null;
  String req;
  
  req = "DELETE FROM Activite ";
  req+= " WHERE idActivite ="+this.id;

  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
  if (myError.cause == -1) return myError;
  
  return myError;
}

public ErrorSpecific  bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){
  ResultSet rs = null;
  String req;
  ErrorSpecific myError = new ErrorSpecific();

    req = "INSERT INTO Activite ";
    req+="(nomActivite, descActivite, donneesSortie, ActivitePhase, Ligne, ActiviteActeur, numActeur, previousActivite, ";
    req+= " Timing,Criticite,duree,ActiviteProcessus,TypeActeur,TypeIntervenant,TypeActivite,TypeUml,ListeIdSt,duree_heures,duree_minutes) VALUES  (";
    req+="'"+this.nom.replaceAll("'", "''")+"'";
    
    if (this.desc != null)
        req+=",'"+this.desc.replaceAll("'", "''")+"'";
    else
        req+=",'"+""+"'";
    
    if (this.donneeSortie != null)
        req+=",'"+this.donneeSortie.replaceAll("'", "''")+"'";
    else
        req+=",'"+""+"'";  
    
    req+=","+idPhase+"";
    req+=","+this.ligne+"";
    req+=","+this.idActeur+"";
    req+=","+this.numActeur+"";
    
    if (this.previousActivite != null)
        req+=",'"+this.previousActivite.replaceAll("'", "''")+"'";
    else
        req+=",'"+""+"'";    

    if (this.Timing != null)
        req+=",'"+this.Timing.replaceAll("'", "''")+"'";
    else
        req+=",'"+""+"'";

    req+=",'"+this.Criticite+"'";
    req+=","+this.duree+"";
    req+=","+this.ActiviteProcessus+"";
     req+=",'"+this.TypeActeur+"'";
     req+=",'"+this.TypeIntervenant+"'";
     req+=",'"+this.TypeActivite+"'";
     req+=",'"+this.TypeUML+"'";
     req+=","+this.getIdST(0)+"";
     req+=","+this.duree_heures+"";
     req+=","+this.duree_minutes+"";

    req+=")";

  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
  if (myError.cause == -1) return myError;

req="SELECT TOP (1) idActivite FROM         Activite WHERE     (ActivitePhase = "+this.idPhase+") ORDER BY idActivite DESC";
rs=myCnx.ExecReq(st,myCnx.nomBase,req,true,transaction);
try { rs.next(); this.id = rs.getInt(1);  } catch (SQLException s){ s.getMessage(); }


  return myError;
}

public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
  ErrorSpecific myError = new ErrorSpecific();
  ResultSet rs = null;
  String req;
  
  if (this.desc == null) this.desc = "";

    req = "UPDATE Activite ";
    req+= " SET ";
    req+= " nomActivite='"+this.nom.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ')+"'";
    req+=","; 
    if (this.desc != null)
        req+= " descActivite='"+this.desc.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ')+"'";
    else
        req+= " descActivite='"+""+"'";    
    req+=",";
    
    if (this.donneeSortie != null)
        req+= " donneesSortie='"+this.donneeSortie.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ')+"'";
    else
        req+= " donneesSortie='"+""+"'";    

    req+=",";
    req+= " ActivitePhase='"+this.idPhase+"'";
    req+=",";
    req+= " Ligne='"+this.ligne+"'";
    req+=",";
    req+= " ActiviteActeur='"+this.idActeur+"'";
    req+=",";
    req+= " numActeur='"+this.numActeur+"'";
    req+=",";
    if (this.previousActivite != null)
        req+= " previousActivite='"+this.previousActivite.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ')+"'";
    else
        req+= " previousActivite='"+""+"'";    

    req+=",";
    req+= " ListeST='"+this.getST(0)+"'";
    req+=",";
    if (this.Timing != null)
        req+= " Timing='"+this.Timing.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ')+"'";
    else
        req+= " Timing='"+""+"'";      

    req+=",";
    req+= " Criticite='"+ this.Criticite+"'";
    req+=",";
    req+= " duree='"+this.duree+"'";
    req+=",";
    req+= " ActiviteProcessus='"+this.ActiviteProcessus+"'";
    req+=","; 
    req+= " TypeActeur='"+this.TypeActeur+"'";
    req+=","; 
    req+= " TypeIntervenant='"+this.TypeIntervenant+"'";
    req+=","; 
    req+= " TypeActivite='"+this.TypeActivite+"'";
    req+=","; 
    req+= " TypeUml='"+this.TypeUML+"'";
    req+=","; 
    req+= " ListeIdSt='"+this.getIdST(0)+"'";
    req+=","; 
    req+= " duree_heures='"+this.duree_heures+"'";
    req+=",";     
    req+= " duree_minutes='"+this.duree_minutes+"'";

    req+= " WHERE idActivite="+this.id;

  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
  if (myError.cause == -1) return myError;

    req = "UPDATE Activite ";
    req+= " SET ";
    req+= " numActeur='"+this.numActeur+"'";
    req+= " WHERE ActivitePhase ="+this.idPhase;
    req+= " AND ActiviteActeur  ="+this.idActeur;
    
  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
  if (myError.cause == -1) return myError;    
    
  return myError;
}

  public static Vector getListePreviousBoite(String PreviousBoite){
    if (PreviousBoite == null) return null;
    Vector ListePreviousBoite = new Vector (6);
    int index = 0;
    previousBoite thepreviousBoite = null;
    for (StringTokenizer t = new StringTokenizer(PreviousBoite, "@") ; t.hasMoreTokens() ; ) {
      String str1 = t.nextToken();
      for (StringTokenizer u = new StringTokenizer(str1, ";") ; u.hasMoreTokens() ; ) {
        String nomBoite = u.nextToken();
        String donneeEntree = u.nextToken();
        thepreviousBoite=new previousBoite(nomBoite,donneeEntree);
        ListePreviousBoite.addElement(thepreviousBoite);
      }
      index++;
    }

    return ListePreviousBoite;

  }


  public void getAllFromBd(
      String nomBase,
      Connexion myCnx,
      Statement st ){
    ResultSet rs=null;

    req = "SELECT     nomActivite, descActivite, donneesEntree, donneesSortie, ActivitePhase, Ligne, ActiviteActeur, numActeur, previousActivite, Timing, Criticite, ActiviteProcessus,  ActiviteProcessus, duree, TypeActeur,";
    req+= "                      TypeActivite, TypeUml, ListeIdSt, TypeIntervenant, duree_heures, duree_minutes";
    req+= " FROM         Activite";
    req+= " WHERE     (idActivite = "+this.id+")";

    rs = Connexion.ExecReq(st, nomBase, req);

    //if (rs == null) return;
    try {
      while (rs.next()) {
        this.nom = rs.getString(1).replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
        this.desc = rs.getString(2).replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
        this.donneeEntree = rs.getString("donneesEntree");
        this.donneeSortie = rs.getString("donneesSortie");
        this.idPhase = rs.getInt("ActivitePhase");
        this.ligne = rs.getInt("Ligne");
        this.idActeur = rs.getInt("ActiviteActeur");
        this.numActeur = rs.getInt("numActeur");
        this.previousActivite = rs.getString("previousActivite");
        this.Timing = rs.getString("Timing");
        this.Criticite = rs.getString("Criticite");
        this.ActiviteProcessus = rs.getInt("ActiviteProcessus");
        this.duree = rs.getInt("duree");
        this.TypeActeur = rs.getString("TypeActeur");
        this.TypeActivite = rs.getString("TypeActivite");
        this.TypeUML = rs.getString("TypeUml");
        String ListeIdSt=rs.getString("ListeIdSt");
        if (ListeIdSt != null)
        {
          ((ST)(this.ListeST.elementAt(0))).idSt = Integer.parseInt(ListeIdSt);
        }        
        this.TypeIntervenant = rs.getString("TypeIntervenant");
        this.duree_heures = rs.getInt("duree_heures");
        this.duree_minutes = rs.getInt("duree_minutes");
        

        }

    } catch (SQLException s) {s.getMessage();}

}
  
  public void getAllFromBd2(
      String nomBase,
      Connexion myCnx,
      Statement st ){
    ResultSet rs=null;

    req = "exec ACTIVITE_SelectActivite "+this.id;

    rs = Connexion.ExecReq(st, nomBase, req);

    //if (rs == null) return;
    try {
      while (rs.next()) {
        this.nom = rs.getString(1).replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
        this.desc = rs.getString(2).replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
        this.donneeSortie = rs.getString(3);
        this.previousActivite = rs.getString(4);
        this.nextActivite = rs.getString(5);
        this.Timing = rs.getString(6);
        this.duree = rs.getInt(7);
        this.nomProcessus = rs.getString(8);
        String nomSt=rs.getString(9);
        if (nomSt != null)
        {
          ((ST)(this.ListeST.elementAt(0))).nomSt = nomSt;
        }


        }

    } catch (SQLException s) {s.getMessage();}

}  
public void addST(int idVersionSt,String nomST, String couleur,String TypeApplication){
  ST theST = new ST(idVersionSt,nomST, couleur,TypeApplication);
  this.ListeST.addElement(theST) ;
}

public String getST(int num){
  if (this.ListeST.size() == 0) return "?";

  return ((ST)this.ListeST.elementAt(num)).nomSt;
}

public String getIdST(int num){
  if (this.ListeST.size() == 0) return "?";
  int myIdSt = ((ST)this.ListeST.elementAt(num)).idSt;
  return ""+ myIdSt;
}

public String getIdVersionSt(int num){
  if (this.ListeST.size() == 0) return "?";

  return ""+((ST)this.ListeST.elementAt(num)).idVersionSt;
}


public String getCouleurST(int num){
  if (this.ListeST.size() == 0) return "bleu";

  return ((ST)this.ListeST.elementAt(num)).couleur;
}

public String getTypeST(int num){
  if (this.ListeST.size() == 0) return "Application";

  return ((ST)this.ListeST.elementAt(num)).TypeApplication;
}

public String getNote(){
  if (this.numNote >0) return ".... cf Note("+this.numNote+")";
  else return " ";

}

public void draw(){
  if (this.TypeUML.equals("activite"))
  {
    if (this.Criticite.equals("Mineure"))
      this.icone = "images/Fonds/BoiteVerte.png";
    else if (Criticite.equals("Majeure"))
      this.icone = "images/Fonds/BoiteOrange.png";
    else if (Criticite.equals("Critique"))
      this.icone = "images/Fonds/BoiteRouge.png";
    else
      this.icone = "images/Fonds/BoiteVerte.png";
  }
  else   if (this.TypeUML.equals("test"))
  {
    if (this.Criticite.equals("Mineure"))
      this.icone = "images/Fonds/TestVert.png";
    else if (Criticite.equals("Majeure"))
      this.icone = "images/Fonds/TestOrange.png";
    else if (Criticite.equals("Critique"))
      this.icone = "images/Fonds/TestRouge.png";
    else
      this.icone = "images/Fonds/TestVert.png";
  }
  else
  {
    if (this.Criticite.equals("Mineure"))
      this.icone = "images/Fonds/SynchroNoire.png";
    else if (Criticite.equals("Majeure"))
      this.icone = "images/Fonds/SynchroNoire.png";
    else if (Criticite.equals("Critique"))
      this.icone = "images/Fonds/SynchroNoire.png";
    else
      this.icone = "images/Fonds/SynchroNoire.png";
  }
}

public void positionneAncrages(int x_a, int y_a){

  for (int i=0;i < this.ListeAncrages.size();i++){
    Ancrage theAncrage = (Ancrage)this.ListeAncrages.elementAt(i);
    theAncrage.positionne(x_a,y_a);
  }
}

public void InitAncragesTest(){


      Ancrage AncrageN = new Ancrage("Base", "N", "N", this.nom,
                             this.x + Activite.L / 2, this.y);
      this.ListeAncrages.addElement(AncrageN);

      Ancrage AncrageNE = new Ancrage("Base", "N", "NE", this.nom,
                             this.x +3* Activite.L / 4, this.y + Activite.H / 4);
      this.ListeAncrages.addElement(AncrageNE);

      Ancrage AncrageE = new Ancrage("Lateral", "E", "E", this.nom,
                             this.x + Activite.L, this.y + Activite.H / 2);
      this.ListeAncrages.addElement(AncrageE);

      Ancrage AncrageSE = new Ancrage("Base", "S", "SE", this.nom,
                             this.x +3* Activite.L / 4, this.y + 3 *Activite.H / 4);
      this.ListeAncrages.addElement(AncrageSE);


      Ancrage AncrageS = new Ancrage("Base","S","S",this.nom,this.x + Activite.L/2, this.y + Activite.H);
      this.ListeAncrages.addElement(AncrageS);


      Ancrage AncrageSW = new Ancrage("Base", "S", "SW", this.nom,
                               this.x +Activite.L / 4, this.y + 3 *Activite.H / 4);
      this.ListeAncrages.addElement(AncrageSW);


      Ancrage AncrageW = new Ancrage("Lateral", "W", "W", this.nom, this.x,
                               this.y + Activite.H / 2);
        this.ListeAncrages.addElement(AncrageW);


      Ancrage AncrageNW = new Ancrage("Base", "N", "NW", this.nom,
                                   this.x +Activite.L / 4, this.y + Activite.H / 4);
            this.ListeAncrages.addElement(AncrageNW);

}

       public    void InitAncragesSynchro(){
              Ancrage AncrageN=null;

              if (!this.nom.equals("start")&& !this.nom.equals("stop"))
              {
                  AncrageN = new Ancrage("Base", "N", "N", this.nom,
                                         this.x + Activite.L / 2, this.y);
                  this.ListeAncrages.addElement(AncrageN);
                }

              Ancrage AncrageS=null;;
              if (this.nom.equals("start") || this.nom.equals("stop"))
                AncrageS = new Ancrage("Base","S","S",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
              else
                AncrageS = new Ancrage("Base","S","S",this.nom,this.x + Activite.L/2, this.y + Activite.H_Synchro);
              this.ListeAncrages.addElement(AncrageS);


                Ancrage AncrageSE=null;;
                //if (this.nom.equals("start") || this.nom.equals("stop"))
                  //AncrageSE = new Ancrage("Base","S","SE",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                //else
                if (!this.nom.equals("start")&& !this.nom.equals("stop"))
              {
                  AncrageSE = new Ancrage("Base", "S", "SE", this.nom,
                                          this.x + 5 * Activite.L / 6,
                                          this.y + Activite.H_Synchro);
                  this.ListeAncrages.addElement(AncrageSE);
                }


                Ancrage AncrageSW=null;;
                //if (this.nom.equals("start") || this.nom.equals("stop"))
                  //AncrageSW = new Ancrage("Base","S","SW",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                //else
                if (!this.nom.equals("start")&& !this.nom.equals("stop"))
              {
                  AncrageSW = new Ancrage("Base", "S", "SW", this.nom,
                                          this.x + 1 * Activite.L / 6,
                                          this.y + Activite.H_Synchro);
                  this.ListeAncrages.addElement(AncrageSW);
                }


                Ancrage AncrageNW=null;;
                //if (this.nom.equals("start") || this.nom.equals("stop"))
                  //AncrageNW = new Ancrage("Base","N","NW",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                //else
                if (!this.nom.equals("start")&& !this.nom.equals("stop"))
              {
                  AncrageNW = new Ancrage("Base", "N", "NW", this.nom,
                                          this.x + 1 * Activite.L / 6, this.y);
                  this.ListeAncrages.addElement(AncrageNW);
                }


                 Ancrage AncrageNE=null;;
                //if (this.nom.equals("start") || this.nom.equals("stop"))
                  //AncrageNE = new Ancrage("Base","N","NE",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                //else
                if (!this.nom.equals("start")&& !this.nom.equals("stop"))
              {
                  AncrageNE = new Ancrage("Base", "N", "NE", this.nom,
                                          this.x + 5 * Activite.L / 6, this.y);
                  this.ListeAncrages.addElement(AncrageNE);
                }


}

           public    void InitAncragesActivite(){
                Ancrage AncrageN=null;
//if (this.nom.equals("start") || this.nom.equals("stop"))
                  //AncrageN = new Ancrage("Base","N","N",this.nom,this.x + proc_Boite.L_start/2, this.y + proc_Boite.H_start/2);
//else
                if (!this.nom.equals("start")&& !this.nom.equals("stop"))
                {
                    AncrageN = new Ancrage("Base", "N", "N", this.nom,
                                           this.x + Activite.L / 2, this.y);
                    this.ListeAncrages.addElement(AncrageN);
                  }

                Ancrage AncrageS=null;;
                if (this.ActiviteStart != null)
                {
                  AncrageS = new Ancrage("Base", "S", "S", this.nom,this.x + Activite.L_start / 2,this.y + Activite.H_start / 2);
                  this.ActiviteStart.ListeAncrages.addElement(AncrageS);
                }
                else if (this.ActiviteStop != null)
                {
                  AncrageS = new Ancrage("Base", "S", "S", this.nom,this.x + Activite.L_start / 2,this.y + Activite.H_start / 2);
                  this.ActiviteStop.ListeAncrages.addElement(AncrageS);
                }
                else
                  AncrageS = new Ancrage("Base","S","S",this.nom,this.x + Activite.L/2, this.y + Activite.H);

                if (this.nom.equals("start") || this.nom.equals("stop"))
                  AncrageS = new Ancrage("Base","S","S",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start);
                else
                  AncrageS = new Ancrage("Base","S","S",this.nom,this.x + Activite.L/2, this.y + Activite.H);
                this.ListeAncrages.addElement(AncrageS);

                Ancrage AncrageE=null;;
//if (this.nom.equals("start") || this.nom.equals("stop"))
                  //AncrageE = new Ancrage("Lateral","E","E",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
//else
                if (!this.nom.equals("start")&& !this.nom.equals("stop"))
                {
                    AncrageE = new Ancrage("Lateral", "E", "E", this.nom,
                                           this.x + Activite.L, this.y + Activite.H / 2);
                    this.ListeAncrages.addElement(AncrageE);
                  }

                Ancrage AncrageW=null;;
//if (this.nom.equals("start") || this.nom.equals("stop"))
                  //AncrageW = new Ancrage("Lateral","W","W",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
//else
                if (!this.nom.equals("start")&& !this.nom.equals("stop"))
                {
                    AncrageW = new Ancrage("Lateral", "W", "W", this.nom, this.x,
                                           this.y + Activite.H / 2);
                    this.ListeAncrages.addElement(AncrageW);
                  }

                  Ancrage AncrageSE=null;;
                  //if (this.nom.equals("start") || this.nom.equals("stop"))
                    //AncrageSE = new Ancrage("Base","S","SE",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                  //else
                  if (!this.nom.equals("start")&& !this.nom.equals("stop"))
                {
                    AncrageSE = new Ancrage("Base", "S", "SE", this.nom,
                                            this.x + 5 * Activite.L / 6,
                                            this.y + Activite.H);
                    this.ListeAncrages.addElement(AncrageSE);
                  }

                  Ancrage AncrageSEE=null;;
                  //if (this.nom.equals("start") || this.nom.equals("stop"))
                    //AncrageSEE = new Ancrage("Base","S","SEE",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                  //else
                  if (!this.nom.equals("start")&& !this.nom.equals("stop"))
                {
                    AncrageSEE = new Ancrage("Base", "S", "SEE", this.nom,
                                             this.x + 4 * Activite.L / 6,
                                             this.y + Activite.H);
                    this.ListeAncrages.addElement(AncrageSEE);
                  }


                  Ancrage AncrageSWW=null;;
                  //if (this.nom.equals("start") || this.nom.equals("stop"))
                    //AncrageSWW = new Ancrage("Base","S","SWW",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                  //else
                  if (!this.nom.equals("start")&& !this.nom.equals("stop"))
                {
                    AncrageSWW = new Ancrage("Base", "S", "SWW", this.nom,
                                             this.x + 2*Activite.L / 6, this.y + Activite.H);
                    this.ListeAncrages.addElement(AncrageSWW);
                  }

                  Ancrage AncrageSW=null;;
                  //if (this.nom.equals("start") || this.nom.equals("stop"))
                    //AncrageSW = new Ancrage("Base","S","SW",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                  //else
                  if (!this.nom.equals("start")&& !this.nom.equals("stop"))
                {
                    AncrageSW = new Ancrage("Base", "S", "SW", this.nom,
                                            this.x + 1 * Activite.L / 6,
                                            this.y + Activite.H);
                    this.ListeAncrages.addElement(AncrageSW);
                  }

                  Ancrage AncrageSSW=null;;
                  //if (this.nom.equals("start") || this.nom.equals("stop"))
                    //AncrageSSW = new Ancrage("Lateral","W","SSW",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                  //else
                  if (!this.nom.equals("start")&& !this.nom.equals("stop"))
                {
                    AncrageSSW = new Ancrage("Lateral", "W", "SSW", this.nom, this.x,
                                             this.y + 7 * Activite.H / 8);
                    this.ListeAncrages.addElement(AncrageSSW);
                  }

                  Ancrage AncrageNNW=null;;
                  //if (this.nom.equals("start") || this.nom.equals("stop"))
                    //AncrageNNW = new Ancrage("Lateral","W","NNW",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                  //else
                  if (!this.nom.equals("start")&& !this.nom.equals("stop"))
                {
                    AncrageNNW = new Ancrage("Lateral", "W", "NNW", this.nom, this.x,
                                             this.y + Activite.H / 8);
                    this.ListeAncrages.addElement(AncrageNNW);
                  }

                  Ancrage AncrageNW=null;;
                  //if (this.nom.equals("start") || this.nom.equals("stop"))
                    //AncrageNW = new Ancrage("Base","N","NW",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                  //else
                  if (!this.nom.equals("start")&& !this.nom.equals("stop"))
                {
                    AncrageNW = new Ancrage("Base", "N", "NW", this.nom,
                                            this.x + 1 * Activite.L / 6, this.y);
                    this.ListeAncrages.addElement(AncrageNW);
                  }

                  Ancrage AncrageNWW=null;;
                  //if (this.nom.equals("start") || this.nom.equals("stop"))
                    //AncrageNWW = new Ancrage("Base","N","NWW",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                  //else
                  if (!this.nom.equals("start")&& !this.nom.equals("stop"))
                {
                    AncrageNWW = new Ancrage("Base", "N", "NWW", this.nom,
                                             this.x + 2*Activite.L / 6, this.y);
                    this.ListeAncrages.addElement(AncrageNWW);
                  }


                   Ancrage AncrageNE=null;;
                  //if (this.nom.equals("start") || this.nom.equals("stop"))
                    //AncrageNE = new Ancrage("Base","N","NE",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                  //else
                  if (!this.nom.equals("start")&& !this.nom.equals("stop"))
                {
                    AncrageNE = new Ancrage("Base", "N", "NE", this.nom,
                                            this.x + 5 * Activite.L / 6, this.y);
                    this.ListeAncrages.addElement(AncrageNE);
                  }

                  Ancrage AncrageNEE=null;;
                 //if (this.nom.equals("start") || this.nom.equals("stop"))
                   //AncrageNEE = new Ancrage("Base","N","NEE",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                 //else
                 if (!this.nom.equals("start")&& !this.nom.equals("stop"))
                {
                   AncrageNEE = new Ancrage("Base", "N", "NEE", this.nom,
                                            this.x + 4 * Activite.L / 6, this.y);
                   this.ListeAncrages.addElement(AncrageNEE);
                 }


                  Ancrage AncrageNNE=null;;
                  //if (this.nom.equals("start") || this.nom.equals("stop"))
                    //AncrageNNE = new Ancrage("Lateral","E","NNE",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                  //else
                  if (!this.nom.equals("start")&& !this.nom.equals("stop"))
                {
                    AncrageNNE = new Ancrage("Lateral", "E", "NNE", this.nom,
                                             this.x + Activite.L, this.y + Activite.H / 8);
                    this.ListeAncrages.addElement(AncrageNNE);
                  }

                  Ancrage AncrageSSE=null;;
                 // if (this.nom.equals("start") || this.nom.equals("stop"))
                    //AncrageSSE = new Ancrage("Lateral","E","SSE",this.nom,this.x + Activite.L_start/2, this.y + Activite.H_start/2);
                  //else
                  if (!this.nom.equals("start")&& !this.nom.equals("stop"))
                {
                    AncrageSSE = new Ancrage("Lateral", "E", "SSE", this.nom,
                                             this.x + Activite.L,
                                             this.y + 7 * Activite.H / 8);
                    this.ListeAncrages.addElement(AncrageSSE);
                  }

              }


              public void InitAncrages(){
  this.ListeAncrages.removeAllElements();
  if (this.TypeUML.equals("test"))
  {
    this.InitAncragesTest();
    return;
  }

  else if (this.TypeUML.equals("synchro"))
  {
    this.InitAncragesSynchro();
    return;
  }

  else
  {
    this.InitAncragesActivite();
    return;
  }


}
public void setXY(Acteur theActeur, boolean isVertical) {

if (this.TypeUML.equals("activite") || this.TypeUML.equals("test"))
  {
    this.Largeur = Activite.L;
    this.Hauteur = Activite.H;
  }
  else if (this.TypeUML.equals("synchro"))
  {
    this.Largeur = Activite.L;
    this.Hauteur = Activite.H_Synchro;
  }

  this.ligne = this.numActivite;
  if (isVertical)
  {

      this.x = theActeur.x + Acteur.Marge_G;
      this.y = theActeur.y + Acteur.H_Titre + Acteur.Marge_H + (this.ligne - 1) * (Acteur.Marge_H + this.H);

      this.L = Activite.L;
      this.H = Activite.H;

      if (this.isStart)
      {
        this.ActiviteStart = new Activite();
        this.ActiviteStart.nom = "start";
        this.ActiviteStart.icone = "images/ledgreen.png";
        this.ActiviteStart.Largeur = 15;
        this.ActiviteStart.Hauteur = 15;
        this.ActiviteStart.x =this.x + Activite.L/2 - Activite.H_start/2;
        this.ActiviteStart.y =this.y - Activite.H +10;
      }

      if (this.isEnd)
      {
        this.ActiviteStop = new Activite();
        this.ActiviteStop.nom = "stop";
        this.ActiviteStop.icone = "images/ledred.png";
        this.ActiviteStop.Largeur = 15;
        this.ActiviteStop.Hauteur = 15;
        this.ActiviteStop.x =this.x + Activite.L +10;
        this.ActiviteStop.y =this.y +Activite.H -10;
      }


  }
  else
  {
    if (this.nom.equals("stop"))
    {
      this.x = theActeur.x +  Acteur.L_Titre + Acteur.Marge_G +(this.ligne - 2) * (Acteur.Marge_G + Activite.L+50) +  Activite.L;
      this.y = theActeur.y + Acteur.Marge_H+ Activite.H -10;

    }
    else
    {
    this.x = theActeur.x +  Acteur.L_Titre + Acteur.Marge_G + (this.ligne - 1) * (Acteur.Marge_G + Activite.L +50);
    this.y = theActeur.y + 2*Acteur.Marge_H/3;

    this.L = Activite.L;
    this.H = Activite.H;
    }
    if (this.isStart)
      {
        this.ActiviteStart.x = this.x - 30;
        this.ActiviteStart.y = this.y + Activite.H / 2;
      }
    if (this.isEnd)
      {
        this.ActiviteStop.x =this.x + Activite.L +10;
        this.ActiviteStop.y =this.y + Activite.H -10;
      }

  }

  }

  void addStart(){

    this.ActiviteStart=  new Activite();
    this.ActiviteStart.nom = "start";
    this.ActiviteStart.ligne = this.ligne;
    this.ActiviteStart.colonne = this.colonne;
  }

  void addStop(){
    this.ActiviteStop=  new Activite();
    this.ActiviteStop.nom = "stop";
    this.ActiviteStop.ligne = this.ligne;
    this.ActiviteStop.colonne = this.colonne;

}

public void avoidCollisionActivite(Vector ListeTrunk){
  Trunk theTrunk= null;
  theTrunk = new Trunk (this.Label,this.x,this.y, this.x + Activite.L, this.y, false);
  ListeTrunk.addElement(theTrunk);
  //theTrunk.dump();

  theTrunk = new Trunk (this.Label,this.x + Activite.L,this.y, this.x + Activite.L, this.y+ Activite.H, false);
  ListeTrunk.addElement(theTrunk);
  //theTrunk.dump();

  theTrunk = new Trunk (this.Label,this.x,this.y+ Activite.H, this.x + Activite.L, this.y+ Activite.H, false);
  ListeTrunk.addElement(theTrunk);
  //theTrunk.dump();

  theTrunk = new Trunk (this.Label,this.x,this.y, this.x, this.y+ Activite.H, false);
  //theTrunk.dump();
      ListeTrunk.addElement(theTrunk);
}

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("Activite.processus="+this.nomProcessus);
    System.out.println("Activite.TypeActivite="+this.TypeActivite);
    System.out.println("Activite.phase="+this.nomPhase);
    System.out.println("Activite.id="+this.id);
    System.out.println("Activite.nom="+this.nom);
    //System.out.println("Activite.donneeEntree="+this.donneeEntree);
    System.out.println("Activite.nom="+this.donneeSortie);
    System.out.println("Activite.desc="+this.desc);
    System.out.println("Activite.numNote="+this.numNote);
    System.out.println("Activite.numActivite="+this.numActivite);
    System.out.println("Activite.numActeur="+this.numActeur);
    System.out.println("Activite.idActeur="+this.idActeur);
    System.out.println("Activite.TypeActeur="+this.TypeActeur);
    System.out.println("Activite.Timing="+this.Timing);
    System.out.println("Activite.duree_jours="+this.duree);
    System.out.println("Activite.duree_heures="+this.duree_heures);
    System.out.println("Activite.duree_minutes="+this.duree_minutes);
    System.out.println("Activite.Criticite="+this.Criticite);
    System.out.println("Activite.TypeActivite="+this.TypeActivite);
    System.out.println("Activite.TypeUML="+this.TypeUML);
    System.out.println("Activite.ListeST.size()="+this.ListeST.size());
    System.out.println("Activite.ActiviteProcessus="+this.ActiviteProcessus);
    System.out.println("Activite.nomPhase="+this.nomPhase);
    System.out.println("Activite.idPhase="+this.idPhase);
    for (int i=0; i < this.ListeST.size(); i++){
      System.out.println("Activite.ST_"+i+"="+((ST)this.ListeST.elementAt(i)).nomSt);
    }
    if (this.previousActivite != null)
      System.out.println("Activite pr�c�dente="+this.previousActivite);
      else System.out.println("Activite pr�c�dente="+"aucune");
    if (this.nextActivite != null)
      System.out.println("Activite suivante="+this.nextActivite);
      else System.out.println("Activite suivante="+"aucune");
    System.out.println("==================================================");
 }

  public static void main(String[] args) {
    //Activite activite1 = new Activite("processus","phase",1,"","",1,1,1,"","","", "T0 + 3j","Majeure",0,0);
  }

}



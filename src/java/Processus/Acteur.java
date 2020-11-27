package Processus; 
import accesbase.*;
import java.sql.Statement;
import java.sql.ResultSet;
import Graphe.Forme;
import java.util.Vector;
import ST.ST;
import java.sql.SQLException;
/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Soci�t� : </p>
 * @author non attribuable
 * @version 1.0
 */

public class Acteur extends Forme{
  public int id;
  public int ordre;
  public String nom;
  public String TypeActeur;
  public String TypeIntervenant;

  public static int X0=5;
  public static int Y0=5;

  public static int H_Titre=20;
  public static int L_Titre=60;

  public static int Marge_H=60;
  public static int Marge_B=10;
  public static int Marge_G=25;
  public static int Marge_D=25;

  //static int L=proc_Boite.L + Marge_G + Marge_D;
  public static int L=Activite.L;
  public static int H=Activite.H + Marge_H + Marge_B;

  //static int Esp_interCouloir_H=L +10;
  public static int Esp_interCouloir_H=10;
  public static int Esp_interCouloir_L=10;

  public int index = -1;
  public int nbBoites=0;


  public  int x1=0;
  public  int y1=0;
  public  int x2=0;
  public  int y2=0;

  public static int largeurVisible=1100;
  public static int hauteurVisible=500;
  public static boolean AfficherActeur=true;

  public Forme iconeActeur=null;
  public Vector ListeActivite = new Vector(10);

  public Acteur(int id, String nom, int ordre, String TypeActeur,String TypeIntervenant) {
    this.id = id;
    this.ordre = ordre;
    this.nom = nom;
    this.TypeActeur = TypeActeur;
    this.TypeIntervenant = TypeIntervenant;

    iconeActeur = new Forme();
  }

  public  int getidVersionSt(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;
    int idVersionSt=-1;
    rs = myCnx.ExecReq(st, nomBase, "exec ST_SelectVersionStByIdSt "+this.id);
    try { rs.next(); idVersionSt = rs.getInt(1); } catch (Exception s) {s.getMessage();}

    return idVersionSt;
  }

  public Activite addActivite(int id, String nom, String desc, int numActivite, int numActeur, int idActeur, String donneeSortie, String previousActivite,  String Timing,  String Criticite, int duree, int ActiviteProcessus, String TypeActeur,String TypeIntervenant, String TypeActivite, String TypeUML){
    Activite theActivite = new Activite("","",id,nom,desc,numActivite,numActeur,idActeur,donneeSortie, previousActivite, Timing,Criticite,duree,ActiviteProcessus,TypeActeur,TypeIntervenant,TypeActivite,TypeUML);
    theActivite.idPhase = this.id;
    //this.ListeActivite.addElement(theActivite);
    return theActivite;
  }
  public  void getListeActivites(String nomBase,Connexion myCnx, Statement st, int idPhase){

    String req="SELECT     Activite.idActivite, Activite.nomActivite, Activite.descActivite, Activite.donneesSortie, Activite.ActivitePhase, Activite.Ligne, Activite.ActiviteActeur, ";
           req+="            Activite.previousActivite, St.nomSt, Activite.Timing, TypeSi.couleurSI, VersionSt.idVersionSt, Activite.Criticite, Activite.duree, Activite.ActiviteProcessus, ";
           req+="             Activite.TypeActeur, Activite.TypeActivite, TypeAppli.nomTypeAppli, Activite.TypeUml, Activite.ListeIdSt, Activite.TypeIntervenant, Activite.duree_heures, ";
           req+="             Activite.duree_minutes";
           req+=" FROM         Activite LEFT OUTER JOIN";
           req+="             TypeAppli RIGHT OUTER JOIN";
           req+="             VersionSt INNER JOIN";
           req+="             St ON VersionSt.stVersionSt = St.idSt INNER JOIN";
           req+="             TypeSi ON VersionSt.typeSiVersionSt = TypeSi.idTypeSi ON TypeAppli.idTypeAppli = St.typeAppliSt ON Activite.ListeIdSt = St.idSt";
           req+=" WHERE     (Activite.ActivitePhase = "+idPhase+") AND (Activite.ActiviteActeur = "+this.id+")";
           req+=" ORDER BY Activite.Ligne";

   this.ListeActivite.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);

   try {
      while (rs.next()) {
        int  idActivite = rs.getInt("idActivite");
        String nomActivite = rs.getString("nomActivite").replaceAll("\r","").replaceAll("\n","<br>");
        //String descActivite = rs.getString("descActivite").replaceAll("\r","").replaceAll("\n","<br>");
        String descActivite = rs.getString("descActivite");
        String donneesSortie = rs.getString("donneesSortie").replaceAll("\r","").replaceAll("\n","<br>");
        String ActivitePhase = rs.getString("ActivitePhase");
        int Ligne = rs.getInt("Ligne");
        int idActeur = rs.getInt("ActiviteActeur");
        String previousActivite = rs.getString("previousActivite");
        String ListeST = rs.getString("nomSt");
        String Timing = rs.getString("Timing");
        String Couleur = rs.getString("couleurSI");
        int idVersionSt = rs.getInt("idVersionSt");
        String Criticite = rs.getString("Criticite");
        int duree = rs.getInt("duree");
        int ActiviteProcessus = rs.getInt("ActiviteProcessus");
        String TypeActeur = rs.getString("TypeActeur");//$$
        String TypeActivite = rs.getString("TypeActivite");
        String TypeApplication = rs.getString("nomTypeAppli");
        String TypeUML = rs.getString("TypeUml");//$$
        int idSt = rs.getInt("ListeIdSt");
        String TypeIntervenant = rs.getString("TypeIntervenant");
        int duree_heures = rs.getInt("duree_heures");
        int duree_minutes = rs.getInt("duree_minutes");//$$

        Activite theActivite=this.addActivite(idActivite, nomActivite, descActivite,Ligne,-1,idActeur, donneesSortie, previousActivite, Timing, Criticite,duree,ActiviteProcessus,TypeActeur,TypeIntervenant,TypeActivite,TypeUML);
try{
        if (theActivite.TypeActivite.equals("start"))
        {
          theActivite.isStart = true;
        }
        if (theActivite.TypeActivite.equals("stop"))
        {
          theActivite.isEnd = true;
        }
        if (theActivite.TypeActivite.equals("start_stop"))
        {
          theActivite.isStart = true;
          theActivite.isEnd = true;
        }
}
      catch (Exception e){}
        theActivite.duree_heures=duree_heures;
        theActivite.duree_minutes=duree_minutes;
        //Connexion.trace("%%----------","theActivite.nom",""+theActivite.nom);
        //Connexion.trace("%%------","thePhase.ListeActivite.size()",""+this.ListeActivite.size());
        this.ListeActivite.addElement(theActivite);
        //theActivite.dump();
        if ((ListeST!=null) && (!ListeST.equals("")) && (!ListeST.equals("null")))
        {
          //theActivite.addST(idVersionSt, ListeST, Couleur, TypeApplication);
          ST theST = new ST(ListeST);
          theST.idSt=idSt;
          theST.idVersionSt=idVersionSt;
          theST.couleur=Couleur;
          theST.TypeApplication=TypeApplication;

          theActivite.ListeST.addElement(theST) ;

        }
      }
       } catch (SQLException s) {s.getMessage();}

       //Connexion.trace("%%999------","thePhase.ListeActivite.size()",""+this.ListeActivite.size());


}

  public void draw(boolean isVertical) {
    this.icone = "images/Fonds/couloir.png";
    if (isVertical)
    {

      this.Largeur = Acteur.Marge_G+Activite.L+Acteur.Marge_D;
      this.Hauteur = Acteur.H_Titre + Acteur.Marge_H + Activite.H_start + this.nbBoites * (Acteur.Marge_H + Activite.H) +  Activite.H_start;
      this.x = Acteur.X0 + (this.index) * (this.Largeur +Acteur.Esp_interCouloir_H);
      this.y = this.y+Acteur.Y0;
    }
    else
    {
     this.x = this.x+Acteur.X0 ;
     this.Largeur =  Acteur.L_Titre + Acteur.Marge_G  + this.nbBoites * (Acteur.Marge_G + Activite.L+50) ;
     this.Hauteur = Acteur.Marge_H + 2*Activite.H_start + 1 * ( Activite.H) ;
     this.y = this.y+Acteur.Y0 + (this.index) * (this.Esp_interCouloir_L + this.Hauteur);
 }


  }

  public void drawIcone(boolean isVertical) {

  this.iconeActeur.icone ="images/user.gif";
   if (isVertical) {

     this.iconeActeur.x=this.x + 10;
     this.iconeActeur.y=this.y;
       }
       else{ // horizontal
         this.iconeActeur.x=this.x - 10;
         this.iconeActeur.y=this.y;

       }
     this.iconeActeur.Largeur = 35;
     this.iconeActeur.Hauteur = 35;
  }


  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("Acteur.id="+this.id);
    System.out.println("Acteur.nom="+this.nom);
    System.out.println("Acteur.TypeActeur="+this.TypeActeur);
    System.out.println("Acteur.TypeActeur="+this.TypeIntervenant);
    System.out.println("Acteur.ordre="+this.ordre);
    System.out.println("==================================================");
 }

  public static void main(String[] args) {
    Acteur acteur1 = new Acteur(1,"",1,"Interne","ST");
  }

}

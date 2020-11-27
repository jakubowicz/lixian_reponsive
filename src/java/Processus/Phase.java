package Processus; 
import java.util.Vector;
import accesbase.*;
import ST.ST;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Graphe.Path;
import Graphe.Trunk;
import OM.OM;
import Graphe.Timing;
/**
 * <p>Titre : </p>
 * <p>Description : </p>
 * <p>Copyright : Copyright (c) 2004</p>
 * <p>Soci�t� : </p>
 * @author non attribuable
 * @version 1.0
 */

public class Phase {
  public String nomProcessus="";
  public int id;
  public int idProcessus;
  public int numPhase;
  public String nom="";
  public String desc="";
  public Vector ListeActivite = null;
  public Vector ListeActeur = null;
  public Vector ListeSt =  new Vector(10);
  public Vector ListeChemins =  new Vector(10);
  public Vector ListeChemins_Retour =  new Vector(10);
  public Vector ListeTrunk =  new Vector(10);
  public Vector ListeOM =  new Vector(10);

  public int nbLignesActivite;
  public int ordre;
  public static Vector ListeSousPhaseNP = new Vector(10);
  public static Vector ListePhaseNP = new Vector(10);

  public int nbStRouge;
  public int nbAppliRouge;
  public int nbStBleu;

  private String req="";

  public Phase(String nomProcessus,int id,int numPhase, String nom, String desc, int ordre) {
    this.nomProcessus=nomProcessus;
    this.id=id;
    this.numPhase=numPhase;
    this.nom=nom;
    this.desc=desc;
    this.ListeActivite = new Vector(10);
    this.ListeActeur = new Vector(10);
    this.ordre = ordre;
  }

  public Phase(int id) {
    this.id=id;
    this.ListeActivite = new Vector(10);
  }
  
  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){

    ResultSet rs = null;
    req = "SELECT     nomPhaseNP, descPhaseNP, significationPhaseNP, scope, siPhaseNp, PhaseProcessus, Ordre";
    req += " FROM         Phase";
    req += " WHERE     idPhaseNP = "+this.id;
    rs = myCnx.ExecReq(st, nomBase, req);

    try { rs.next();
            this.nom = rs.getString("nomPhaseNP");
            if (this.nom == null) this.nom="";
            this.desc = rs.getString("descPhaseNP").replaceAll("\r","").replaceAll("\n","<br>");;
            if (this.desc == null) this.desc="";
            this.idProcessus = rs.getInt("PhaseProcessus");
            this.ordre = rs.getInt("Ordre");

      } catch (SQLException s) {s.getMessage();}


  }

  public  void getListeActivite(String nomBase,Connexion myCnx, Statement st){

  String req="";
  req+="SELECT     Activite.nomActivite, Activite.descActivite,Phase.nomPhaseNP, Activite.Timing, Activite.duree";
  req+=" FROM         Processus INNER JOIN";
  req+="                      Phase ON Processus.idProcessus = Phase.PhaseProcessus INNER JOIN";
  req+="                      Activite ON Phase.idPhaseNP = Activite.ActivitePhase";
  req+=" WHERE     (Activite.ActivitePhase = "+this.id+")";
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

  public int getSiBleu(String nomBase,Connexion myCnx, Statement st, String nomSI){
    this.nbStBleu=0;
    req = "SELECT     ListeST.idSt, ListeST.nomSt, ListeST.idVersionSt";
    req+= "   FROM         ListeST INNER JOIN";
    req+= "                  SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
    req+= "                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
    req+= " WHERE     (TypeSi.nomTypeSi = 'SI Bleu') AND (ListeST.etatVersionSt = 3) AND (ListeST.phaseNPVersionSt  = "+this.id+") AND (SI.nomSI = '"+nomSI+"') order by ListeST.nomSt asc";


     ResultSet rs = myCnx.ExecReq(st, nomBase, req);

     try {
        while (rs.next()) {
          int idSt = rs.getInt("idSt");
          String nomSt = rs.getString("nomSt");
          int idVersionSt = rs.getInt("idVersionSt");
          if (nomSt != null)
          {
            ST theST = new ST(nomSt);
            theST.idVersionSt = idVersionSt;
            theST.idSt = idSt;
            this.ListeSt.addElement(theST);
          }

       this.nbStBleu++;
        }
      } catch (SQLException s) {s.getMessage();}

     return this.nbStBleu;
}

   public int getAppliRouge(String nomBase,Connexion myCnx, Statement st, String nomSI){
     this.nbAppliRouge=0;
     req = "SELECT     ListeST.idSt, ListeST.nomSt, ListeST.idVersionSt";
     req+= "   FROM         ListeST INNER JOIN";
     req+= "                  SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
     req+= "                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
     req+= " WHERE     (TypeSi.nomTypeSi = 'SI Rouge') AND (ListeST.isAppli = 1) AND (ListeST.etatVersionSt = 3) AND (ListeST.phaseNPVersionSt  = "+this.id+") AND (SI.nomSI = '"+nomSI+"') order by ListeST.nomSt asc";

      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try {
         while (rs.next()) {
           int idSt = rs.getInt("idSt");
           String nomSt = rs.getString("nomSt");
           int idVersionSt = rs.getInt("idVersionSt");
           if (nomSt != null)
           {
             ST theST = new ST(nomSt);
             theST.idVersionSt = idVersionSt;
             theST.idSt = idSt;
             this.ListeSt.addElement(theST);
           }

        this.nbAppliRouge++;
         }
        } catch (SQLException s) {s.getMessage();}
      return this.nbAppliRouge;
}
   public int getSiRouge(String nomBase,Connexion myCnx, Statement st, String nomSI){
     this.nbStRouge=0;
     req = "SELECT     ListeST.idSt, ListeST.nomSt, ListeST.idVersionSt";
     req+= "   FROM         ListeST INNER JOIN";
     req+= "                  SI ON ListeST.siVersionSt = SI.idSI INNER JOIN";
     req+= "                  TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi";
     req+= " WHERE     (TypeSi.nomTypeSi = 'SI Rouge') AND (ListeST.isST = 1) AND (ListeST.etatVersionSt = 3) AND (ListeST.phaseNPVersionSt  = "+this.id+") AND (SI.nomSI = '"+nomSI+"') order by ListeST.nomSt asc";

      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try {
         while (rs.next()) {
           int idSt = rs.getInt("idSt");
           String nomSt = rs.getString("nomSt");
           int idVersionSt = rs.getInt("idVersionSt");
           if (nomSt != null)
           {
             ST theST = new ST(nomSt);
             theST.idVersionSt = idVersionSt;
             theST.idSt = idSt;
             this.ListeSt.addElement(theST);
           }

        this.nbStRouge++;
         }
        } catch (SQLException s) {s.getMessage();}
      return this.nbStRouge;
}

    public  void getListeActivites2(String nomBase,Connexion myCnx, Statement st){
      ResultSet rs=null;
      String req="";

      req = "exec PHASE_selectActiviteByIdPhase "+ this.id;
      rs = myCnx.ExecReq(st,nomBase,req);

     try { while(rs.next())
             {
               int  idActivite = rs.getInt("idActivite");
               String nomActivite = rs.getString("nomActivite").replaceAll("\r","").replaceAll("\n","<br>");
               String descActivite = rs.getString("descActivite").replaceAll("\r","").replaceAll("\n","<br>");
               String donneesSortie = rs.getString("donneesSortie").replaceAll("\r","").replaceAll("\n","<br>");
               String ActivitePhase = rs.getString("ActivitePhase");
               int Ligne = rs.getInt("Ligne");
               int idActeur = rs.getInt("ActiviteActeur");
               String previousActivite = rs.getString("previousActivite");

               String ListeST = rs.getString("nomSt");
               String Timing = rs.getString("Timing");
               String Couleur = rs.getString("couleurSI");
               int idVersionSt = rs.getInt("idVersionSt");
               String Criticite = rs.getString("Criticite"); //$$

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
              theActivite.duree_heures=duree_heures;
              theActivite.duree_minutes=duree_minutes;

              if ((ListeST!=null) && (!ListeST.equals("")) && (!ListeST.equals("null")))
                       theActivite.addST(idVersionSt,ListeST,Couleur,TypeApplication) ;

              theActivite.addST(idSt,ListeST,"","");
              //theActivite.dump();

             }}
      catch (SQLException s) {s.getMessage();}



}
  public  void getListeActivites(String nomBase,Connexion myCnx, Statement st){

   String req="exec PHASE_selectActiviteByIdPhase "+ this.id;
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

  public Activite getIdBoiteBynom (String nomActivite){
       for (int i= 0; i < this.ListeActeur.size(); i++)
       {
         Acteur theActeur = (Acteur)this.ListeActeur.elementAt(i);
         for (int j = 0; j < theActeur.ListeActivite.size(); j++)
         {
           Activite theActivite = (Activite)theActeur.ListeActivite.elementAt(j);
           if ((theActivite.nom.equals(nomActivite)) ) return theActivite;
         }
       }

       return null;
  }

     public void getListeChemins(boolean isVertical) {
       for (int i = 0; i < this.ListeActeur.size(); i++)
       {
         Acteur theActeur = (Acteur)this.ListeActeur.elementAt(i);

         for (int j = 0; j < theActeur.ListeActivite.size(); j++)
         {
           Activite ActiviteDestination = (Activite) theActeur.ListeActivite.elementAt(j);
           //BoiteDestination.isStop=this.panel.checkIfStop(BoiteDestination.Label);

           //if (BoiteDestination.previousBoite != null)
           boolean startLien = false;
           for (int k=0; k <ActiviteDestination.ListepreviousActivite.size();k++)
           {
             if ( ( (Activite) (ActiviteDestination.ListepreviousActivite.elementAt(
                 k))).nom != null) {
               //proc_Boite BoiteOrigine = this.panel.getIdBoiteBynom(BoiteDestination.previousBoite);
               Activite ActiviteOrigine = this.getIdBoiteBynom( ( (Activite) (ActiviteDestination.ListepreviousActivite.elementAt(k))).nom);
               if (ActiviteDestination.isStart && !startLien && ActiviteOrigine == null)
               {
                 ActiviteOrigine = ActiviteDestination.ActiviteStart;
                 startLien = true;
               }

               if ( (ActiviteOrigine != null)) {
                 if (isVertical){
                   if (ActiviteDestination.ligne >= ActiviteOrigine.ligne)
                   {
                    //Chemin theChemin = new Chemin(this.ListeTrunk,ActiviteOrigine, ActiviteDestination,  ( (Activite) ( ActiviteDestination.ListepreviousActivite.elementAt(k))).donneeEntree);
                    Path theChemin = new Path(this.ListeTrunk,ActiviteOrigine, ActiviteDestination,  ( (Activite) ( ActiviteDestination.ListepreviousActivite.elementAt(k))).donneeEntree, isVertical);
                    this.ListeChemins.addElement(theChemin);
                   }
                    else
                    {
                      Path theChemin = new Path(this.ListeTrunk,ActiviteOrigine, ActiviteDestination,  ( (Activite) ( ActiviteDestination.ListepreviousActivite.elementAt(k))).donneeEntree, isVertical);
                      this.ListeChemins_Retour.addElement(theChemin);
                    }

                 }
                 else
                 {
                  if (ActiviteDestination.colonne >= ActiviteOrigine.colonne)
                  {
                   Path theChemin = new Path(this.ListeTrunk,ActiviteOrigine, ActiviteDestination,  ( (Activite) ( ActiviteDestination.ListepreviousActivite.elementAt(k))).donneeEntree, isVertical);
                   this.ListeChemins.addElement(theChemin);
                  }
                   else
                   {
                     Path theChemin = new Path(this.ListeTrunk,ActiviteOrigine, ActiviteDestination,  ( (Activite) ( ActiviteDestination.ListepreviousActivite.elementAt(k))).donneeEntree, isVertical);
                     this.ListeChemins_Retour.addElement(theChemin);
                   }
                 }

                 //theChemin.dump();
               }


             }
           }
           if (ActiviteDestination.isEnd) {
             Path theChemin = new Path(this.ListeTrunk, ActiviteDestination, ActiviteDestination.ActiviteStop, ActiviteDestination.donneeSortie, isVertical);
                 this.ListeChemins.addElement(theChemin);

           }

         }


       }
   }

  public  void getListeActeurs(String nomBase,Connexion myCnx, Statement st){

    String req="SELECT  idSt, nomSt, TypeActeur, TypeIntervenant FROM   ActeurSortedByNum";
    req+=" WHERE idPhaseNP = "+ this.id+" ORDER BY numActeur";


   this.ListeActeur.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);

   try {
      while (rs.next()) {
        int idActeur = rs.getInt(1);
        String nomActeur = rs.getString(2);
        String TypeActeur = rs.getString(3);
        String TypeIntervenant = rs.getString(4);
        this.addActeur(idActeur,nomActeur,1,TypeActeur,TypeIntervenant);
      }
       } catch (SQLException s) {s.getMessage();}

}

     public  void getListeActeursStOuStructure(String nomBase,Connexion myCnx, Statement st){
       String req="";

       req = "SELECT DISTINCT St.idSt, St.nomSt, Activite.numacteur, Activite.Typeacteur, Activite.TypeIntervenant";
       req += " FROM  Phase INNER JOIN ";
       req += "  Activite ON Phase.idPhaseNP = Activite.ActivitePhase INNER JOIN";
       req += "  St ON Activite.ActiviteActeur = St.idSt";
       req += " WHERE     ( (Activite.TypeIntervenant = 'ST') OR (Activite.TypeIntervenant = 'Structure'))";
                        req += " AND  (Phase.idPhaseNP ="+ this.id+")";



      ResultSet rs = myCnx.ExecReq(st, nomBase, req);

      try {
         while (rs.next()) {
           int idActeur = rs.getInt(1);
           String nomActeur = rs.getString(2);
           int numActeur = rs.getInt(3);
           String TypeActeur = rs.getString(4);
           String TypeIntervenant = rs.getString(5);
           this.addActeur(idActeur,nomActeur,numActeur,TypeActeur,TypeIntervenant);
         }
          } catch (SQLException s) {s.getMessage();}

}

        public  void getListeActeursPersonne(String nomBase,Connexion myCnx, Statement st){
          String req="";

          req = "SELECT     DISTINCT Membre.idMembre, nomMembre + ',' + prenomMembre, Activite.numActeur, Activite.TypeActeur, Activite.TypeIntervenant";
          req += " FROM         Activite INNER JOIN";
          req += "                       Membre ON Activite.ActiviteActeur = Membre.idMembre INNER JOIN";
          req += "                       Phase ON Activite.ActivitePhase = Phase.idPhaseNP";
          req += " WHERE     (Activite.TypeIntervenant = 'Personne')";
                        req += " AND  (Phase.idPhaseNP ="+ this.id+")";


         ResultSet rs = myCnx.ExecReq(st, nomBase, req);

         try {
            while (rs.next()) {
              int idActeur = rs.getInt(1);
              String nomActeur = rs.getString(2);
              int numActeur = rs.getInt(3);
              String TypeActeur = rs.getString(4);
              String TypeIntervenant = rs.getString(5);
              this.addActeur(idActeur,nomActeur,numActeur,TypeActeur,TypeIntervenant);

            }
             } catch (SQLException s) {s.getMessage();}

}

public  void getListeSt(String nomBase,Connexion myCnx, Statement st){

     String req="exec PHASE_ListeStByIdPhase "+this.id;


             this.ListeSt.removeAllElements();
             ResultSet rs = myCnx.ExecReq(st, nomBase, req);

             try {
                while (rs.next()) {
                  int idVersionST=rs.getInt(2);
                  String nomSt=rs.getString(3);
                  ST theST = new ST (-1,1,nomSt,idVersionST);
                  this.ListeSt.addElement(theST);
                }
                 } catch (SQLException s) {s.getMessage();}

}

 public  void getListeStNP(String nomBase,Connexion myCnx, Statement st, String nomSI){


   String req="SELECT     ListeST.idVersionSt, ListeST.nomSt, ListeST.Criticite, TypeSi.nomTypeSi, SI.nomSI";
   req+=" FROM         ListeST INNER JOIN";
   req+="                    PhaseNP ON ListeST.phaseNPVersionSt = PhaseNP.idPhaseNP INNER JOIN";
   req+="                    TypeSi ON ListeST.typeSiVersionSt = TypeSi.idTypeSi INNER JOIN";
   req+="                    SI ON TypeSi.siTypesi = SI.idSI";
   req+=" WHERE     (PhaseNP.idPhaseNP = "+this.id+") AND (TypeSi.nomTypeSi = 'SI Bleu') AND (ListeST.Criticite <> 'Mineur') AND (SI.nomSI = '"+nomSI+"') OR";
    req+="                   (PhaseNP.idPhaseNP = "+this.id+") AND (TypeSi.nomTypeSi = 'SI Rouge') AND (SI.nomSI = '"+nomSI+"')";
    req+=" ORDER BY ListeST.nomSt";



                            this.ListeSt.removeAllElements();
                            ResultSet rs = myCnx.ExecReq(st, nomBase, req);

                            try {
                               while (rs.next()) {
                                 int idVersionST=rs.getInt(1);
                                 String nomSt=rs.getString(2);
                                 ST theST = new ST(nomSt);
                                 theST.idVersionSt = idVersionST;
                                 this.ListeSt.addElement(theST);
                               }
                                } catch (SQLException s) {s.getMessage();}

}

public  void getListeActeurs2(String nomBase,Connexion myCnx, Statement st){

       String req="SELECT  idMembre, nomMembre, TypeActeur, TypeIntervenant FROM   MembreSortedByNum";
       req+=" WHERE idPhaseNP = "+ this.id+" ORDER BY numActeur";

      ResultSet rs = myCnx.ExecReq(st, nomBase, req);

      try {
         while (rs.next()) {
           int idActeur = rs.getInt(1);
           String nomActeur = rs.getString(2);
           String TypeActeur = rs.getString(3);
           String TypeIntervenant = rs.getString(4);
           this.addActeur(idActeur,nomActeur,1,TypeActeur,TypeIntervenant);
         }
          } catch (SQLException s) {s.getMessage();}

}
  public void getListeSousPhase(String nomBase,Connexion myCnx, Statement st){

    String req="SELECT     idSousPhaseNP, nomSousPhaseNP";
    req+="    FROM         SousPhaseNP";
    req+=" WHERE     (phaseSousPhaseNP = "+this.id+")";
    req+=" ORDER BY nomSousPhaseNP";

   ListeSousPhaseNP.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   Phase thePhase = null;
   try {
      while (rs.next()) {
        int idSousPhaseNp = rs.getInt("idSousPhaseNP");
        thePhase = new Phase(idSousPhaseNp);
        thePhase.nom = rs.getString("nomSousPhaseNP");

        //theST.dump();
        this.ListeSousPhaseNP.addElement(thePhase);
      }
       } catch (SQLException s) {s.getMessage();}

}

  public Activite addActivite(int id, String nom, String desc, int numActivite, int numActeur, int idActeur, String donneeSortie, String previousActivite,  String Timing,  String Criticite, int duree, int ActiviteProcessus, String TypeActeur,String TypeIntervenant, String TypeActivite, String TypeUML){
    Activite theActivite = new Activite(this.nomProcessus,this.nom,id,nom,desc,numActivite,numActeur,idActeur,donneeSortie, previousActivite, Timing,Criticite,duree,ActiviteProcessus,TypeActeur,TypeIntervenant,TypeActivite,TypeUML);
    theActivite.idPhase = this.id;
    //this.ListeActivite.addElement(theActivite);
    return theActivite;
  }
  public Phase getPhase(String nom){
    for (int i = 0; i < this.ListeActivite.size(); i++)
    {
      Phase theActivite = (Phase)this.ListeActivite.elementAt(i);
      if (theActivite.nom.equals(nom)) return theActivite;
    }
    return null;
  }

  public Activite getActiviteByActeur(int numActivite,int numActeur){
    //System.out.println("***"+System.currentTimeMillis()+"++getActiviteByActeur::this.ListeActivite.size()="+this.ListeActivite.size());
    for (int i = 0; i < this.ListeActivite.size(); i++)
    {
      Activite theActivite = (Activite)this.ListeActivite.elementAt(i);
      //System.out.println(System.currentTimeMillis()+"?????????theActivite.numActeur="+theActivite.numActeur+"//"+"numActeur="+numActeur+"?????????theActivite.numActivite="+theActivite.numActivite+"//"+"numActivite="+numActivite);
      if ((theActivite.numActeur == numActeur)&&(theActivite.numActivite == numActivite)) return theActivite;
    }
    return null;
  }

  public Activite getActiviteByIdActeur(int numActivite,int idActeur){
    //System.out.println("***"+System.currentTimeMillis()+"getActiviteByIdActeur::this.ListeActivite.size()="+this.ListeActivite.size());

    for (int i = 0; i < this.ListeActivite.size(); i++)
    {
      Activite theActivite = (Activite)this.ListeActivite.elementAt(i);
      //System.out.println(System.currentTimeMillis()+"?????????theActivite.idActeur="+theActivite.idActeur+"//"+"idActeur="+idActeur+"?????????theActivite.numActivite="+theActivite.numActivite+"//"+"numActivite="+numActivite);

      //if ((theActivite.idActeur == idActeur)&&(theActivite.numActivite <3))
      if ((theActivite.idActeur == idActeur)&&(theActivite.numActivite == numActivite))
      {
        //System.out.println("Activite trouv�e="+theActivite.nom);
        //System.out.println(System.currentTimeMillis()+"?????????theActivite.idActeur="+theActivite.idActeur+"//"+"idActeur="+idActeur+"?????????theActivite.numActivite="+theActivite.numActivite+"//"+"numActivite="+numActivite);
        return theActivite;
      }
    }
    return null;
  }



  public boolean getActiviteByLigne(int numActivite){
    for (int i = 0; i < this.ListeActivite.size(); i++)
    {
      Activite theActivite = (Activite)this.ListeActivite.elementAt(i);
      if ((theActivite.numActivite == numActivite)) return true;
    }
    return false;
  }

public int getMaxActivite(){
    int maxActivite = 0;
  for (int i = 0; i < this.ListeActivite.size(); i++)
  {
    Activite theActivite = (Activite)this.ListeActivite.elementAt(i);
    if (theActivite.numActivite > maxActivite) maxActivite=theActivite.numActivite;
  }
  return maxActivite;
}

public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction, String idSi){
  ErrorSpecific myError = new ErrorSpecific();
  ResultSet rs = null;
  String req;

  req = "INSERT INTO Phase ";
  req+=" (nomPhaseNP, descPhaseNP, siPhaseNp,  PhaseProcessus, Ordre) VALUES  (";
  req+="'"+this.nom.replaceAll("'", "''")+"'";
  req+=",'"+this.desc.replaceAll("'", "''")+"'";
  req+=",'"+idSi+"'";
  req+=","+idProcessus+"";
  req+=","+this.ordre+"";
  req+=")";

  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
  if (myError.cause == -1) return myError;
  
    req="SELECT idPhaseNP FROM    Phase WHERE nomPhaseNP = '"+this.nom.replaceAll("'", "''")+"'" +" AND (PhaseProcessus = "+idProcessus+")";
    rs=myCnx.ExecReq(st,myCnx.nomBase,req,true,transaction);
    try { rs.next(); this.id = rs.getInt(1);  } catch (SQLException s){ s.getMessage(); }


  return myError;
}

public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
  ErrorSpecific myError = new ErrorSpecific();
  ResultSet rs = null;
  String req;
  
  if (this.desc == null) this.desc = "";

    req = "UPDATE Phase ";
    req+= " SET ";
    req+= " nomPhaseNP='"+this.nom.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ')+"'";
    req+=",";    
    req+= " descPhaseNP='"+this.desc.replaceAll("\u0092","'").replaceAll("'","''").replace('\u0022',' ')+"'";
    req+=",";    
    req+= " PhaseProcessus='"+this.idProcessus+"'";
    req+=",";
    req+= " Ordre='"+this.ordre+"'";

    req+= " WHERE idPhaseNP="+this.id;

  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.id);
  if (myError.cause == -1) return myError;
  
  return myError;
}

public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
  ErrorSpecific myError = new ErrorSpecific();
  ResultSet rs = null;
  String req;
  
  req = "DELETE FROM Phase";
  req+= " WHERE idPhaseNP="+this.id;

  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.id);
  if (myError.cause == -1) return myError;
  
  return myError;
}

public String bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction, String idSi, int idProcessus){
  ResultSet rs = null;
  String req;

  req = "INSERT INTO Phase ";
  req+=" (nomPhaseNP, descPhaseNP, significationPhaseNP, scope, siPhaseNp, PhaseProcessus, Ordre) VALUES  (";
  //req+="'"+thePhase.nom.replaceAll("'", "''")+"'";
  req+="'"+this.nom.replaceAll("'", "''")+"'";
  req+=",'"+this.desc.replaceAll("'", "''")+"'";
  req+=",'"+this.desc.replaceAll("'", "''")+"'";
  req+=",'"+"xxxxx"+"'";
  req+=","+idSi+"";
  req+=","+idProcessus+"";
  req+=","+this.ordre+"";
                         req+=")";

  if (myCnx.ExecUpdate(st,myCnx.nomBase,req,true,transaction) == -1)
      {
        myCnx.trace(nomBase,"*** ERREUR *** reqRoadmap",req);
        return req;
        }

req="SELECT idPhaseNP FROM    Phase WHERE nomPhaseNP = '"+this.nom.replaceAll("'", "''")+"'" +" AND (PhaseProcessus = "+idProcessus+")";
rs=myCnx.ExecReq(st,myCnx.nomBase,req,true,transaction);
try { rs.next(); this.id = rs.getInt(1);  } catch (SQLException s){ s.getMessage(); }


  return "OK";
}

  public void addActeur(int id, String nom, int ordre,String TypeActeur,String TypeIntervenant){
    Acteur theActeur = new Acteur(id,nom,ordre,TypeActeur,TypeIntervenant);
    this.ListeActeur.addElement(theActeur);
  }
  public Acteur getActeur(String nom){
    for (int i = 0; i < this.ListeActeur.size(); i++)
    {
      Acteur theActeur = (Acteur)this.ListeActeur.elementAt(i);
      if (theActeur.nom.equals(nom)) return theActeur;
    }
    return null;
  }


public void addListeTrunk(Vector theListe)
  {
    for (int i=0; i < theListe.size(); i++)
    {
      Trunk theTrunk = (Trunk)theListe.elementAt(i);
      this.ListeTrunk.addElement(theTrunk);
    }
  }

public void addListeOM(OM theOM)
    {
      this.ListeOM.addElement(theOM);
  }

  public int getIdActeur(String nomBase,Connexion myCnx, Statement st, String nomActeur, String TypeIntervenant,  String TypeActeur,  String ordreActeur){
    int idActeur=-1;
    ResultSet rs=null;
    String req="";
    if (TypeIntervenant.equals("ST"))
        req = "SELECT idSt FROM  St WHERE (nomSt = '"+nomActeur+"')";
    else if (TypeIntervenant.equals("Structure"))
        req = "SELECT idSt FROM  St WHERE (nomSt = '"+nomActeur+"')";
    else if (TypeIntervenant.equals("Personne"))
        req = "SELECT idMembre FROM    Membre WHERE     (nomMembre + ',' + prenomMembre = '"+nomActeur+"')";

        rs = myCnx.ExecReq(st,nomBase,req);
        try { while(rs.next())
                {
                  idActeur=rs.getInt(1);

                }}
                catch (SQLException s) {s.getMessage();}

  return      idActeur;
  }


  public void addActeur(){

  }


  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    //Connexion.trace(this.getClass().getName(),"processus.id",""+this.id);
    //Connexion.trace(this.getClass().getName(),"processus.nom",""+this.nom);
    System.out.println("==================================================");
    System.out.println("phase.processus="+this.nomProcessus);
    System.out.println("phase.id="+this.id);
    System.out.println("phase.nom="+this.nom);
    System.out.println("phase.desc="+this.desc);
    System.out.println("phase.ordre="+this.ordre);
    System.out.println("phase.nbLignesActivite="+this.nbLignesActivite);

    System.out.println("nbActeurs="+this.ListeActeur.size());
    for (int i = 0; i < this.ListeActeur.size(); i++)
    {
      Acteur theActeur = (Acteur)this.ListeActeur.elementAt(i);
      theActeur.dump();

    }

    System.out.println("nbActivites="+this.ListeActivite.size());
    for (int i = 0; i < this.ListeActivite.size(); i++)
    {
      Activite theActivite = (Activite)this.ListeActivite.elementAt(i);
      theActivite.dump();

    }

      System.out.println("==================================================");
 }

 public static void main(String[] args) {
   Connexion myCnx=null;
   Statement st,st2 = null;
   ResultSet rs = null;
   ResultSet rs2 = null;
   String req="";
   String base = myCnx.getDate();
   st = myCnx.Connect();
   st2 = myCnx.Connect();

   Processus theProcessus = new Processus(344);
   theProcessus.getAllFromBd(myCnx.nomBase,myCnx,st);
   theProcessus.getListeSt(myCnx.nomBase,myCnx,st);
   theProcessus.getListePhases(myCnx.nomBase,myCnx,st);

   for (int i=0; i < theProcessus.ListePhases.size();i++)
    {
           Phase thePhase=(Phase)theProcessus.ListePhases.elementAt(i);
           thePhase.getListeActeurs(myCnx.nomBase,myCnx,st2);
           thePhase.getListeSt(myCnx.nomBase,myCnx,st2);
           //thePhase.getListeActeurs(c1.nomBase,c1,st2);
 }


 Phase thePhase = (Phase)theProcessus.ListePhases.elementAt(0);

 int nbMaxActivites = thePhase.getMaxActivite();
     for (int t=0; t < thePhase.ListeActeur.size(); t++) {
       Acteur theActeur = (Acteur)thePhase.ListeActeur.elementAt(t);
       theActeur.index = t;
       theActeur.nbBoites = nbMaxActivites;
       theActeur.draw(theProcessus.isVertical);
       //myCnx.trace("------","Hauteur",""+theActeur.Hauteur);
       //myCnx.trace("------","nbBoites",""+theActeur.nbBoites);

     }

    // myCnx.trace("","","getMaxActivite"+thePhase.getMaxActivite());

     // -------------------------- Initialisation des objets -------------------------------------------//
        theProcessus.ListeTrunk.removeAllElements();
        theProcessus.ListeOM.removeAllElements();

        // -------------------------- Positionnement des boutons de controles ------------------------------------//
             //offgraphics.drawImage(this.Bouton_Plus.myImage, this.Bouton_Plus.x, this.Bouton_Plus.y, this.Bouton_Plus.Largeur, this.Bouton_Plus.Hauteur, this);
             theProcessus.drawBouton_Plus();


             //offgraphics.drawImage(this.Bouton_Reset.myImage, this.Bouton_Reset.x, this.Bouton_Reset.y, this.Bouton_Reset.Largeur, this.Bouton_Reset.Hauteur, this);
             theProcessus.drawBouton_Reset();



             //offgraphics.drawImage(this.Bouton_Moins.myImage, this.Bouton_Moins.x, this.Bouton_Moins.y, this.Bouton_Moins.Largeur, this.Bouton_Moins.Hauteur, this);
             theProcessus.drawBouton_Moins();

             //this.Libelle_PlusMoins.drawString(offgraphics);
             theProcessus.drawLibelle_PlusMoins();


            // @@
            //offgraphics.drawImage(this.Bouton_V.myImage, this.Bouton_V.x, this.Bouton_V.y, this.Bouton_V.Largeur, this.Bouton_V.Hauteur, this);
            theProcessus.drawBouton_V();


            //offgraphics.drawImage(this.Bouton_H.myImage, this.Bouton_H.x, this.Bouton_H.y, this.Bouton_H.Largeur, this.Bouton_H.Hauteur, this);
            theProcessus.drawBouton_H();

            //this.DispositionH_V.drawString(offgraphics);

            theProcessus.drawDispositionH_V();



// -------------------------- Trace des couloirs d'Activites ------------------------------------//
        /*
            for (int i = 0 ; i < this.ListeCouloirs.size() ; i++) {
              proc_Couloir theCouloir = (proc_Couloir)this.ListeCouloirs.elementAt(i);
              theCouloir.draw(offgraphics,fm);
            }
        */

             for (int t=0; t < thePhase.ListeActeur.size(); t++) {
               Acteur theActeur = (Acteur)thePhase.ListeActeur.elementAt(t);
               theActeur.index = t;
               theActeur.nbBoites =nbMaxActivites;

// --------------------------- Affichage des couloirs -----------------------------//
               theActeur.draw(theProcessus.isVertical);

// ---------------------------- Dessin du titre du couloir (nom de l'acteur)------------------------------------------------ //
            //this.myActeur.draw(g,fm,this);

            if ((theProcessus.isVertical) ) {
              //g.drawString(this.Label, theCouloir.x + proc_Couloir.L / 2 - fm.stringWidth(this.Label) / 2 - 10,theCouloir.y + 2 + fm.getAscent());

              //g.drawLine(theCouloir.x, theCouloir.y + 20, theCouloir.x + proc_Couloir.L, theCouloir.y + 20);

              //g.drawLine(theCouloir.x, theCouloir.y + 20, theCouloir.x + theCouloir.Largeur, theCouloir.y + 20);
              //g.drawLine(theCouloir.x, theCouloir.y + theCouloir.Hauteur - 20, theCouloir.x + theCouloir.Largeur, theCouloir.y + theCouloir.Hauteur - 20);

              //g.drawString(this.Label, theCouloir.x + proc_Couloir.L / 2 - fm.stringWidth(this.Label) / 2 - 10, theCouloir.Hauteur + 25);
              //g.drawLine(theCouloir.x, theCouloir.Hauteur + 12, theCouloir.x + proc_Couloir.L,theCouloir.Hauteur + 12);

            }
            //this.drawActeur(g,theCouloir,fm);
            // ------------------------------------- Affichage de l'icone Acteur --------------------------//
            //this.drawActeurImage(g,theCouloir,fm);
            theActeur.drawIcone(theProcessus.isVertical);

// ------------------------------------------------------------------------------------------------ //


// --------------------------------------- Positionnement des Activit�s -----------------------//
            theActeur.getListeActivites(myCnx.nomBase,myCnx,st, thePhase.id);
            //c1.trace("@@--"+theActeur.nom+"----","theActeur.ListeActivite.size()",""+theActeur.ListeActivite.size());
            for (int i_activite = 0 ; i_activite < theActeur.ListeActivite.size() ; i_activite++)
            {
              Activite theActivite = (Activite)theActeur.ListeActivite.elementAt(i_activite);
              //myCnx.trace("@@--"+theActivite.nom+"----","TypeActivite",""+theActivite.TypeActivite);

              theActivite.setXY(theActeur, theProcessus.isVertical);
              theActivite.getListePreviousActivite();



              if (theActivite.isStart)
              {
                /*
                g.setColor(Color.black);
                g.drawOval(this.x ,this.y, Activite.L_start,Activite.H_start);
                g.fillOval(this.x ,this.y, Activite.L_start,Activite.H_start);
                */
              }
              if (theActivite.isEnd)
              {

                /*
                g.setColor(Color.black);
                g.drawOval(this.x ,this.y, Activite.L_start,Activite.H_start);
                g.fillOval(this.x ,this.y, Activite.L_start,Activite.H_start);
                */
              }

              if (theActivite.TypeUML.equals("activite"))
               {
                 theActivite.myST = (ST)theActivite.ListeST.elementAt(0);
                  theActivite.myST.getAllFromBd(myCnx.nomBase,myCnx,st);
                  theActivite.myST.draw(theActivite.x, theActivite.y, theActivite.Largeur);
                 //g.drawImage(this.myImage, this.x, this.y, this.L, Activite.H, this.myPanel); // @@

                 // --------------------------------- Dessin Timing & ST dans la boite -----------------------//
                 /*
                 this.myTiming.draw(g, fm, this.x, this.y);
                 this.myST.draw(g, fm, this.x, this.y, this.L);
                 if (this.myProcSupport != null)
                  this.myProcSupport.draw(g, fm, this.x, this.y);
                  this.setPolice(g);
                  g.drawString(theTexte.substring(0,Math.min(theTexte.length(),max)), this.x + 4, this.y + fm.getAscent() );
                 */
                 // ------------------------------------------------------------------------------------------//
                 if ((theActivite.Timing != null) && (!theActivite.Timing.equals("� d�finir")))
                {
                  theActivite.myTiming = new Timing(theActivite.Timing, "" + theActivite.duree);
                  theActivite.myTiming.draw(theActivite.x, theActivite.y);
                }

               }

              else if (theActivite.TypeUML.equals("test"))
              {
                 theActivite.Largeur += 0;
                 theActivite.Hauteur += 0;
                /*
                this.drawLosange(g,this.L , Activite.H);
                g.drawString(theTexte.substring(0,Math.min(theTexte.length(),max)), this.x +Math.max(this.L/2- fm.stringWidth(theTexte)/2,4),this.y + proc_Boite.H/2+ 5);
                */

              }
              else if (theActivite.TypeUML.equals("synchro"))
                {

                  /*
                this.drawSynchro(g,this.L , Activite.H_Synchro);
                */

                }
                theActivite.draw();
                theActivite.avoidCollisionActivite(thePhase.ListeTrunk);


          if (!theActivite.ispaint)
          {
             theActivite.positionneAncrages(theActivite.x, theActivite.y);
             theActivite.InitAncrages();
             theActivite.ispaint = true;
          }
          if (theActivite.isStart)
          {

            if (!theActivite.ActiviteStart.ispaint)
            {
              theActivite.ActiviteStart.positionneAncrages(theActivite.ActiviteStart.x,theActivite.ActiviteStart.y);
              theActivite.ActiviteStart.InitAncrages();
              theActivite.ActiviteStart.ispaint = true;
            }
         }

         if (theActivite.isEnd)
         {

           if (!theActivite.ActiviteStop.ispaint)
           {
             theActivite.ActiviteStop.positionneAncrages(theActivite.ActiviteStop.x,theActivite.ActiviteStop.y);
             theActivite.ActiviteStop.InitAncrages();
             theActivite.ActiviteStop.ispaint = true;
           }
         }



              if (theActivite.TypeUML.equals("activite"))
              {

              }
              else if (theActivite.TypeUML.equals("test"))
              {

              }

            }
//g.drawString(theTexte.substring(0,Math.min(theTexte.length(),max)), this.x + 4, this.y + fm.getAscent() )
// -------------------------------------------------------------------------------------------- //

            theActeur.x_vrai=theActeur.x ;
            theActeur.y_vrai=theActeur.y;
            theActeur.x =0;
            theActeur.y =0;
             }// Fin du bouclage sur les acteurs


// -------------------------- Trace des liens entre activites ------------------------------//
        thePhase.getListeChemins(theProcessus.isVertical);
        /*

            for (int i = 0 ; i < this.ListeChemins.size() ; i++) {
              Chemin theChemin = (Chemin)this.ListeChemins.elementAt(i);
                theChemin.draw(offgraphics,fm);
                this.ListeOM.addElement(theChemin.myOM);

            }
            */

            // --------------------- Chemins Aller --------------------------------------------------//
        Vector myListeChemin = null;
        for (int nbTypeChemin = 1; nbTypeChemin <= 2; nbTypeChemin ++)
        {
          if (nbTypeChemin == 1)
            myListeChemin = thePhase.ListeChemins;
          else
            myListeChemin = thePhase.ListeChemins_Retour;

          for (int i = 0 ; i < myListeChemin.size() ; i++) {
            Path theChemin = (Path)myListeChemin.elementAt(i);
            theChemin.ListOtherTrunk = thePhase.ListeTrunk;
            theChemin.draw();
            thePhase.addListeTrunk(theChemin.ListTrunk);
            //this.ListeOM.addElement(theChemin.myOM);
            thePhase.addListeOM(theChemin.myOM);
            theChemin.myOM.draw(thePhase.ListeOM, i);


            //c1.trace(""+i+"------","theChemin",""+theChemin.ActiviteOrigine.nom + "/" + theChemin.ActiviteDestination.nom);
            for (int j = 0 ; j < theChemin.ListTrunk.size() ; j++) {
              Trunk theTrunk = (Trunk)theChemin.ListTrunk.elementAt(j);
              //c1.trace(""+j+"------","theTrunk",""+theTrunk.x1+"/"+theTrunk.y1 + "->"+theTrunk.x2+"/"+theTrunk.y2 );

            }
            //this.ListeOM.addElement(theChemin.myOM);

          }
        }
// ---------------------------------------------------------------------------------------//


// ---------------------------------------------------------------------------------------//
// -------------------------- Trace des donnees sur les liens ------------------------------//
        /*
            for (int i = 0 ; i < this.ListeOM.size() ; i++) {
              OM theOM = (OM)this.ListeOM.elementAt(i);
              theOM.draw(offgraphics,fm,this.ListeOM,i );

            }
        */
// ---------------------------------------------------------------------------------------- //


    myCnx.Unconnect(st);

  }

  public static void main2(String[] args) {
    Connexion myCnx=null;
    Statement st,st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req="";
    String base = myCnx.getDate();
    st = myCnx.Connect();
    st2 = myCnx.Connect();

    //Phase phase1 = new Phase("bbb",1,1,"xx","toto",1);
    Phase phase1 = new Phase(2711);
    phase1.nom="Voisinages Flux";
    phase1.getListeActivites(myCnx.nomBase,myCnx,st);

    for (int i=0; i < phase1.ListeActivite.size();i++)
    {
      Activite theActivite = (Activite)phase1.ListeActivite.elementAt(i);
      theActivite.getAllFromBd(myCnx.nomBase,myCnx,st);
      theActivite.dump();
    }

     myCnx.Unconnect(st);

  }

}

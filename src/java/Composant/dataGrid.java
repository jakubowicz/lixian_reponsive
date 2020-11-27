package Composant; 
import accesbase.*;
import java.sql.*;
import java.util.*;


/**
 * <p>Titre : </p>
 *
 * <p>Description : Cette Classe permet d'encapsuler le code d'un DAta Grid</p>
 *
 * <p>Copyright : Copyright (c) 2004</p>
 *
 * <p>Soci�t� : </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class dataGrid extends Html{
  boolean isRoadmap=false;
  int LigneProd=0;
  public int idVersionSt=0;
  public String Table="";
  Vector ListeColonnes = new Vector(10);
  private String req="";


  public dataGrid(String nom, String attributs) {
    this.nom = nom;
    this.balise = "table";
    this.attributs = " " + attributs;

  }



  public String highlight(String Clef, String theString, String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;
    String newClef="";
    String theTocken="";
    if ((Clef == null) || (Clef.equals(""))) return theString;
          newClef=Clef.replaceAll("'","' ").replaceAll(","," ").replaceAll("\\."," ").replaceAll("\\;"," ");

    for (StringTokenizer t = new StringTokenizer(Clef, " ") ; t.hasMoreTokens() ; )
      {
         theTocken=t.nextToken().replaceAll("'","''");

        String reqExclusion = "SELECT COUNT(mot) AS nb FROM Exclusion WHERE mot = '"+theTocken+"'";
        rs = Connexion.ExecReq(st, this.getClass().getName(), reqExclusion);
        int nbExclusion=0;
        try { rs.next(); nbExclusion = rs.getInt(1);  } catch (SQLException s) {s.getMessage();}
        if (nbExclusion > 0) continue;

         String Clef1=theTocken.substring(0,1).toUpperCase() + theTocken.substring(1);
         String Clef2=theTocken.substring(0,1).toLowerCase() + theTocken.substring(1);
         String Clef3=theTocken.toUpperCase() ;
         String Clef4=theTocken.toLowerCase() ;
         //Connexion.trace("::::::::::::","Clef1",Clef1);
         theString = theString.replaceAll(Clef1,"<span class='Stabilo'>"+theTocken+"</span>").replaceAll(Clef2,"<span class='Stabilo'>"+theTocken+"</span>").replaceAll(Clef3,"<span class='Stabilo'>"+theTocken+"</span>").replaceAll(Clef4,"<span class='Stabilo'>"+theTocken+"</span>");
         //Connexion.trace("::::::::::::","theString",theString);
      }


  return theString;
  }
  int searchLigneProd(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs=null;
    java.util.Date theDate=null;

    String req = "SELECT     MAX(dateMep) AS maxDateMep FROM   Roadmap WHERE     (idVersionSt = "+idVersionSt+") AND (Etat = 'MES' OR Etat = 'PROD')";
     rs = myCnx.ExecReq(st, nomBase, req);
     try { rs.next(); theDate = rs.getDate(1);  } catch (SQLException s) {s.getMessage();}

     req = "SELECT     dateMep FROM  Roadmap WHERE     (idVersionSt = "+idVersionSt+") ORDER BY dateMep ASC";
     rs = myCnx.ExecReq(st, nomBase, req);
     int i=0;
     int numLigne = 0;
     try { while(rs.next()) {
         java.util.Date myDate = rs.getDate(1);
         if ( (myDate == null) || (theDate == null) ) return 0;
         //System.out.println("@@@@@@@@@ Date="+myDate+ "---- Ref="+theDate);
         if (myDate.equals(theDate))
           {
             numLigne = i+1;
             //System.out.println("@@@@@@@@@ Trouv� � la ligne="+numLigne);
           }
         i++;
       }
  } catch (SQLException s) {
    System.out.println("@@@@@@@@@searchLigneProd>catch: numLigne="+numLigne);
    s.getMessage();
  }

    return numLigne;
  }
  public void addColonne(String nom,String type,String nomReq,String attributsEntete, String attributsCorps){
    Colonne theColonne = new Colonne(nom, type, nomReq, attributsEntete,attributsCorps );
    this.ListeColonnes.addElement(theColonne);
    if (nom.equals("Prod"))
    {
      this.isRoadmap = true;
    }
  }

  public void buildTableModules(String nomBase,Connexion myCnx, Statement st, Statement st2,  String Clef, int idVersionSt){
    String attributsEntete = "align='center' class='TresPetitentete'" ;
    String attributsCorps = "bgcolor='#DDE8EE'" ;

   this.addColonne("Num","String","Num",attributsEntete, attributsCorps);
   this.addColonne("Nom","String","nomModule",attributsEntete, attributsCorps);
   this.addColonne("Description","String","descModule",attributsEntete, attributsCorps);
   this.addColonne("Type","String","idTypeModule",attributsEntete, attributsCorps);
   this.addColonne("Ihm","Croix","Ihm",attributsEntete, attributsCorps);
   this.addColonne("I.S","Croix","InterfaceSynchrone",attributsEntete, attributsCorps);
   this.addColonne("I.A","Croix","InterfaceAsynchrone",attributsEntete, attributsCorps);
   req = "EXEC ST_SelectDescModules "+idVersionSt;
   this.build(nomBase,myCnx,st,st2,req, Clef)  ;

  }
  
  public void buildTableModulesServeur(String nomBase,Connexion myCnx, Statement st, Statement st2,  String Clef, int idVersionSt){
    String attributsEntete = "align='center' class='TresPetitentete'" ;
    String attributsCorps = "bgcolor='#DDE8EE'" ;

   this.addColonne("Num","String","Num",attributsEntete, attributsCorps);
   this.addColonne("Nom","String","nomModule",attributsEntete, attributsCorps);
   this.addColonne("Description","String","descModule",attributsEntete, attributsCorps);
   this.addColonne("Type","String","idTypeModule",attributsEntete, attributsCorps);
   this.addColonne("Dev","Croix","Ihm",attributsEntete, attributsCorps);
   this.addColonne("Pre-Prod","Croix","InterfaceSynchrone",attributsEntete, attributsCorps);
   this.addColonne("Prod","Croix","InterfaceAsynchrone",attributsEntete, attributsCorps);
   req = "EXEC ST_SelectDescModules "+idVersionSt;
   this.build(nomBase,myCnx,st,st2,req, Clef)  ;

  }  

  public void buildTableRoadmap(String nomBase,Connexion myCnx, Statement st, Statement st2,  String Clef, int idVersionSt){
    String attributsEntete = "align='center' class='TresPetitentete'" ;
    String attributsCorps = "bgcolor='#DDE8EE'" ;

    this.addColonne("Num","String","Num",attributsEntete, attributsCorps);
    this.addColonne("Version","String","version",attributsEntete, attributsCorps);
    this.addColonne("Description","String","description",attributsEntete, attributsCorps);
    this.addColonne("EB","Date","dateEB",attributsEntete, attributsCorps);
    this.addColonne("Prod","Date","dateMep",attributsEntete, attributsCorps);
    req = "SELECT  version, description, dateEB, dateMep FROM  Roadmap WHERE  idVersionSt = "+idVersionSt+" AND (LF_Month = - 1) AND (LF_Year = - 1) AND (dateMep > CONVERT(DATETIME, '1900-01-01 00:00:00', 102))ORDER BY dateMep DESC";
    this.idVersionSt = idVersionSt;

   this.build(nomBase,myCnx,st,st2,req, Clef)  ;

  }

  public void buildTableProcessus(String nomBase,Connexion myCnx, Statement st, Statement st2,  String Clef,String TypeApplication, int idSt){
    String attributsEntete = "align='center' class='TresPetitentete'" ;
    String attributsCorps = "bgcolor='#DDE8EE'" ;

    this.addColonne("Num","String","Num",attributsEntete, attributsCorps);
    this.addColonne("Processus","String","nomProcessus",attributsEntete, attributsCorps);
    this.addColonne("Description","String","descProcessus",attributsEntete, attributsCorps);
    if (TypeApplication.equals("Acteur"))
             {
                  req = "EXEC ACTEUR_SelectProcByActeur "+idSt+"";
             }
             else
             {
                   req = "EXEC ST_SelectProcBySt '"+idSt+"', 'Processus'";
             }
    this.build(nomBase,myCnx,st,st2,req, Clef)  ;

  }

  public void buildTableOmOrigine(String nomBase,Connexion myCnx, Statement st, Statement st2,  String Clef, int idSt){
    String attributsEntete = "align='center' class='TresPetitentete'" ;
    String attributsCorps = "bgcolor='#DDE8EE'" ;

    this.addColonne("Num","String","Num",attributsEntete, attributsCorps);
    this.addColonne("Nom","String","nomObjetMetier",attributsEntete, attributsCorps);
    this.addColonne("Description","String","defObjetMetier",attributsEntete, attributsCorps);
    req = "EXEC ST_SelectOmByStOrigine "+idSt;

    this.build(nomBase,myCnx,st,st2,req, Clef)  ;

  }


  public void build(Vector myListe,  String Clef){

    Colonne theColonne = null;
    this.start();
    Ligne theLigneEntete = new Ligne("entete","");
    theLigneEntete.start();
    //theLigneEntete.dump();

    for (int numColonne =0; numColonne < this.ListeColonnes.size(); numColonne++)
    {
      theColonne = (Colonne)this.ListeColonnes.elementAt(numColonne);
      theColonne.start();
      theColonne.addValue(theColonne.nom);
      theColonne.stop();
      theLigneEntete.addValue(theColonne.html);
    }
   theLigneEntete.stop();
   this.html += theLigneEntete.html;

   int i=1;

   for (i=0; i < myListe.size(); i++)
   {
       Ligne theLigneCorps = new Ligne("Corps","");
       theLigneCorps.start();
       theColonne.attributs = theColonne.attributsEntete;
       theColonne.start();
       theColonne.addValue(""+i);
       theColonne.stop();
       theLigneCorps.addValue(theColonne.html);

       for (int numColonne =1; numColonne < this.ListeColonnes.size(); numColonne++)
       {
         theColonne = (Colonne)this.ListeColonnes.elementAt(numColonne);
         if (theColonne.type.equals("String"))
           {
             //theColonne.value= rs.getString(theColonne.nomReq);
             if (theColonne.value != null) theColonne.value= theColonne.value.replaceAll("\n","<br>");
           }
           if (theColonne.type.equals("Croix"))
           {
             //theColonne.value= rs.getString(theColonne.nomReq);
             if (theColonne.value.equals("1")) theColonne.value = "X"; else theColonne.value = "";
           }
           else if (theColonne.type.equals("Date"))
           {
            // java.util.Date theDate= rs.getDate(theColonne.nomReq);
            /*
             if (theDate != null)
             {
               theColonne.value = theDate.getDate()+"/"+(theDate.getMonth()+1)+"/"+(theDate.getYear()+1900);
               if (theDate.getYear()+1900 <= 1995) theColonne.value = "";
             }
             else theColonne.value ="";
            */

           }

           //theColonne.value = this.highlight(Clef, theColonne.value,  nomBase, myCnx,  st2);

           if ( (i==this.LigneProd) && this.isRoadmap)
             theColonne.attributs = theColonne.attributsCorpsHighLight;
           else
             theColonne.attributs = theColonne.attributsCorps;
           theColonne.start();
           theColonne.addValue(theColonne.value);
           theColonne.stop();
           theLigneCorps.addValue(theColonne.html);

     }


theLigneCorps.stop();
this.html +=theLigneCorps.html;
}


    this.stop();
  }

  public void build(String nomBase,Connexion myCnx, Statement st, Statement st2, String req,  String Clef){
    ResultSet rs =null;
    Colonne theColonne = null;
    //this.Table+="";
    //this.Table+="<table border='0'>";

    if (this.isRoadmap) this.LigneProd = this.searchLigneProd(nomBase,myCnx, st);

    this.start();
    Ligne theLigneEntete = new Ligne("entete","");
    theLigneEntete.start();
    //theLigneEntete.dump();

        for (int numColonne =0; numColonne < this.ListeColonnes.size(); numColonne++)
        {
          theColonne = (Colonne)this.ListeColonnes.elementAt(numColonne);
          theColonne.start();
          theColonne.addValue(theColonne.nom);
          theColonne.stop();
          theLigneEntete.addValue(theColonne.html);
        }
       theLigneEntete.stop();
       this.html += theLigneEntete.html;

            int i=1;
            rs = myCnx.ExecReq(st, nomBase, req);

            try { while(rs.next()) {
                Ligne theLigneCorps = new Ligne("Corps","");
                theLigneCorps.start();
                theColonne.attributs = theColonne.attributsEntete;
                theColonne.start();
                theColonne.addValue(""+i);
                theColonne.stop();
                theLigneCorps.addValue(theColonne.html);

                for (int numColonne =1; numColonne < this.ListeColonnes.size(); numColonne++)
                {
                  theColonne = (Colonne)this.ListeColonnes.elementAt(numColonne);
                  if (theColonne.type.equals("String"))
                    {
                      String myValue = rs.getString(theColonne.nomReq);
                      if (myValue == null) myValue = "";
                      theColonne.value= myValue;
                      if (theColonne.value != null) theColonne.value= theColonne.value.replaceAll("\n","<br>");
                    }
                    if (theColonne.type.equals("Croix"))
                    {
                      theColonne.value= rs.getString(theColonne.nomReq);
                      if (theColonne.value.equals("1")) theColonne.value = "X"; else theColonne.value = "";
                    }
                    else if (theColonne.type.equals("Date"))
                    {
                      java.util.Date theDate= rs.getDate(theColonne.nomReq);
                      if (theDate != null)
                      {
                        theColonne.value = theDate.getDate()+"/"+(theDate.getMonth()+1)+"/"+(theDate.getYear()+1900);
                        if (theDate.getYear()+1900 <= 1995) theColonne.value = "";
                      }
                      else theColonne.value ="";

                    }

                    theColonne.value = this.highlight(Clef, theColonne.value,  nomBase, myCnx,  st2);

                    if ( (i==this.LigneProd) && this.isRoadmap)
                      theColonne.attributs = theColonne.attributsCorpsHighLight;
                    else
                      theColonne.attributs = theColonne.attributsCorps;
                    theColonne.start();
                    theColonne.addValue(theColonne.value);
                    theColonne.stop();
                    theLigneCorps.addValue(theColonne.html);

              }


      theLigneCorps.stop();
      this.html +=theLigneCorps.html;
      i++;}
  } catch (SQLException s) {s.getMessage();}

    this.stop();

  }
}


  class Colonne extends Html{
    String nom;
    String type;
    String nomReq;
    String value;
    String attributsCorps = "";
    String attributsEntete = "";
    String attributsCorpsHighLight = " bgcolor='#00FF33'";

    public Colonne(String nom,String type,String nomReq,String attributsEntete,String attributsCorps){
      this.nom = nom;
      this.type = type;
      this.nomReq = nomReq;
      this.balise = "td";
      this.attributs = " "+attributsEntete;
      this.attributsEntete= " "+attributsEntete;
      this.attributsCorps= " "+attributsCorps;
    }

  }

  class Ligne extends Html{


  public Ligne(String nom,String attributs){
    this.nom = nom;
    this.balise = "tr";
    this.attributs = " "+attributs;
  }

}



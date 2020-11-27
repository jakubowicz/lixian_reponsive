package ST; 
import General.Utils;
import java.sql.*;
import java.util.*;

import OM.*;
import accesbase.*;
import Graphe.Node;
import Organisation.Collaborateur;
import Projet.Suivi;
import Composant.item;
import Composant.Word;

import java.util.Date;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

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
public class Interface {
  public int idInterface;
  public ST StOrigine;
  public ST StDestination;
  public String sensInterface;
  public String typeInterface;
  public String genreInterface;
  public String nomImplementation;
  public String nomFrequence;
  public String descInterface = "";
  public int idImplementation;
  public int idFrequence;

  public static Vector ListeImplementation = new Vector(10);

  public Vector ListeSuivi = new Vector(10);
  public Vector ListeOM = new Vector(20);
  public Vector ListePackages = new Vector(20);
  public String ListeStrOM = "";
  public String ListeStrOM2 = "";
  public String ListeStrOMSansLien = "";

  public String dumpHtml = "";
  public int idEtat= 1; // Modifi�e
  public int idEtat_old= 1; // Modifi�e
  public String Etat= ""; // Modifi�e
  public int idStValide=-1;

  String req;

  public String imageSensInterface;
  public Vector ListeSt = new Vector(6);

  public String doc = "";


  public Interface(int idInterface){
    this.idInterface = idInterface;
  }

  public Interface(int idImplementation, String nomImplementation){
    this.idImplementation = idImplementation;
    this.nomImplementation = nomImplementation;
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
     req = "SELECT     Interface.idInterface, Interface.origineInterface, Interface.sensInterface, Interface.extremiteInterface, Interface.typeInterface, Interface.implemInterface, ";
     req+="                  Interface.frequenceInterface, Interface.descInterface, Implementation.nomImplementation, Frequence.nomFrequence, St.nomSt AS nomStOrigine, ";
     req+="                  St_1.nomSt AS nomStDestination, Interface.dumpHtml,  Interface.idEtat, typeEtatSti.nom as nomEtat, Interface.idStValide, Interface.doc";
     req+=" FROM         Interface INNER JOIN";
     req+="                  Implementation ON Interface.implemInterface = Implementation.idImplementation INNER JOIN";
     req+="                  Frequence ON Interface.frequenceInterface = Frequence.idFrequence INNER JOIN";
     req+="                  St ON Interface.origineInterface = St.idSt INNER JOIN";
     req+="                  St AS St_1 ON Interface.extremiteInterface = St_1.idSt INNER JOIN";
     req+="                  typeEtatSti ON Interface.idEtat = typeEtatSti.id";

     req+=" WHERE     (Interface.idInterface = "+this.idInterface+")";

     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       this.idInterface = rs.getInt("idInterface");
       int origineInterface = rs.getInt("origineInterface");
       this.sensInterface = rs.getString("sensInterface");
       int extremiteInterface = rs.getInt("extremiteInterface");
       this.typeInterface = rs.getString("typeInterface");
       this.idImplementation = rs.getInt("implemInterface");
       this.idFrequence = rs.getInt("frequenceInterface");
       this.descInterface = rs.getString("descInterface");
       this.nomImplementation = rs.getString("nomImplementation");
       this.nomFrequence = rs.getString("nomFrequence");
       this.StOrigine = new ST(origineInterface, "idSt");
       this.StOrigine.nomSt = rs.getString("nomStOrigine");
       this.StDestination = new ST(extremiteInterface, "idSt");
       this.StDestination.nomSt = rs.getString("nomStDestination");
       this.dumpHtml = rs.getString("dumpHtml");
       this.idEtat = rs.getInt("idEtat");
       this.Etat = rs.getString("nomEtat");
       this.idEtat_old =this.idEtat;
       this.idStValide = rs.getInt("idStValide");
       this.doc = rs.getString("doc");
       if (this.doc == null) this.doc = "";

       } catch (SQLException s) {s.getMessage(); }

       if (this.descInterface != null)
       {
         //this.descInterface = this.descInterface.replaceAll("\\", "&#92;");
         this.descInterface = this.descInterface.replace('\\', '�');
         this.descInterface = this.descInterface.replaceAll("�", "&#92;");
         this.descInterface = this.descInterface.replaceAll("<div>?</div>", "");
         //Connexion.trace("---","this.descInterface",""+this.descInterface);
         //Connexion.trace("---","length",""+this.descInterface.length());
         //Connexion.trace("---","this.descInterface",""+Utils.stringToHexa(this.descInterface) );
         String myStr = Utils.stringToHexa(this.descInterface) ;
         if (myStr.equals("3c 64 69 76 3e 3f 3c 2f 64 69 76 3e "))
         {
             this.descInterface = "";
         }
         
       }
       else
           this.descInterface = "";

  }
  
  public void getEtat(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
     req = "SELECT      Interface.idEtat";
     req+=" FROM         Interface ";

     req+=" WHERE     (Interface.idInterface = "+this.idInterface+")";


     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       this.idEtat = rs.getInt("idEtat");
 

       } catch (SQLException s) {s.getMessage(); }

       if (this.descInterface != null)
       {
         //this.descInterface = this.descInterface.replaceAll("\\", "&#92;");
         this.descInterface = this.descInterface.replace('\\', '�');
         this.descInterface = this.descInterface.replaceAll("�", "&#92;");
         this.descInterface = this.descInterface.replaceAll("<div>?</div>", "");
         //Connexion.trace("---","this.descInterface",""+this.descInterface);
         //Connexion.trace("---","length",""+this.descInterface.length());
         //Connexion.trace("---","this.descInterface",""+Utils.stringToHexa(this.descInterface) );
         String myStr = Utils.stringToHexa(this.descInterface) ;
         if (myStr.equals("3c 64 69 76 3e 3f 3c 2f 64 69 76 3e "))
         {
             this.descInterface = "";
         }
         
       }
       else
           this.descInterface = "";

  }  

  public String bd_InsertOM(String nomBase,Connexion myCnx, Statement st, String transaction, int idObjetMetier){
    ResultSet rs=null;

    req = "EXEC ST_InsererInter_OM '"+this.idInterface+"', '"+idObjetMetier+"'";
    if (myCnx.ExecUpdate(st,nomBase,req,true,"EnregST") == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     return "OK";
  }

  public ErrorSpecific bd_InsertListeOM(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;
    for (int i=0; i < this.ListeOM.size(); i++)
      {
        OM theOM = (OM)this.ListeOM.elementAt(i);
        //theOM.dump();
        req = "EXEC ST_InsererInter_OM '"+this.idInterface+"', '"+theOM.idObjetMetier+"'";
        myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeOM",""+this.idInterface);
        if (myError.cause == -1) return myError;
        }
   return myError;
  }

  public ErrorSpecific DeleteListeOm(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;

    String req = "DELETE FROM  Inter_OM WHERE interInter_OM = "+this.idInterface;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"DeleteListeOm",""+this.idInterface);
    if (myError.cause == -1) return myError;

    return myError;
  }

  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs = null;

    myError= this.DeleteListeOm( nomBase, myCnx,  st,  transaction);
    if (myError.cause == -1) return myError;

    String req = "DELETE FROM  Interface WHERE idInterface  = "+this.idInterface;
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_delete",""+this.idInterface);
    if (myError.cause == -1) return myError;

    return myError;
  }

  public ErrorSpecific bd_InsertListeOm(String nomBase,Connexion myCnx, Statement st, String transaction, String ListeOm){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    //this.ListeSt.removeAllElements();
    //System.out.print("--------ListeSt=" +ListeSt);
    myError= this.DeleteListeOm( nomBase, myCnx,  st,  transaction);
    if (myError.cause == -1) return myError;

       for (StringTokenizer t = new StringTokenizer(ListeOm, ";");
            t.hasMoreTokens(); ) {

         String idOM = t.nextToken();
         //System.out.print("--------theTocken=" +theTocken);
         req="INSERT INTO [Inter_OM]";
         req+="  ([interInter_OM]";
         req+="  ,[omInter_OM])";
         req+=" VALUES (";
         req+=this.idInterface;
         req+=","+idOM;
         req+=")";

         myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeOm",""+this.idInterface);
         if (myError.cause == -1) return myError;
       }

     return myError;

  }

  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction, Collaborateur theCollaborateur){
      ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    //myCnx.trace("1-----bd_Insert","transaction",""+transaction);
         String description="";
         if (this.descInterface != null)
         {
           //description = theInterface.descInterface.replaceAll("\r","").replaceAll("0x22","'").replaceAll("\u0092","'").replaceAll("'","''");
           for (int j=0; j < this.descInterface.length();j++)
           {
             int c = (int)this.descInterface.charAt(j);
             //System.out.println("j: "+j+"---"+theInterface.descInterface.charAt(j) + " --" +(int)theInterface.descInterface.charAt(j));
             if (c != 34)
               {
                 description += this.descInterface.charAt(j);
               }
               else
               {
                 description +="'";
               }
           }
           description = description.replaceAll("\r","").replaceAll("'","''");
         }
    //myCnx.trace("2-----bd_Insert","this.StDestination",""+this.StDestination);
         //description="";
         //System.out.println("Description="+description);
         req = "EXEC ST_InsererInter " ;
         req+="'"+ this.StOrigine.idSt +"'";
         req+=",";
         req+="'"+ this.sensInterface +"'";
         req+=",";
         req+="'"+ this.StDestination.idSt +"'";
         req+=",";
         req+="'"+ this.typeInterface +"'";
         req+=",";
         req+="'"+ this.idImplementation +"'";
         req+=",";
         req+="'"+ this.idFrequence +"'";
         req+=",";
         req+="'"+ description +"'";

//myCnx.trace("2.1-----bd_Insert","req",""+req);
         myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.idInterface);
         if (myError.cause == -1) return myError;
//myCnx.trace("3-----bd_Insert","transaction",""+transaction);
         this.setId( nomBase, myCnx,  st );

  //       myCnx.trace("4-----bd_Insert","transaction",""+transaction);        this.idEtat = 1; // etat Modifie
        myError =this.bd_UpdateState( nomBase, myCnx,  st,  transaction, theCollaborateur);
        if (myError.cause == -1) return myError;
  //      myCnx.trace("5-----bd_Insert","transaction",""+transaction);
        myError =this.bd_InsertListeOM( nomBase, myCnx,  st,  transaction);
        if (myError.cause == -1) return myError;

     return myError;

  }

  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    String description="";

    if (this.descInterface != null)
    {
      //description = theInterface.descInterface.replaceAll("\r","").replaceAll("0x22","'").replaceAll("\u0092","'").replaceAll("'","''");
      for (int j=0; j < this.descInterface.length();j++)
      {
        int c = (int)this.descInterface.charAt(j);
        //System.out.println("j: "+j+"---"+theInterface.descInterface.charAt(j) + " --" +(int)theInterface.descInterface.charAt(j));
        if (c != 34)
          {
            description += this.descInterface.charAt(j);
          }
          else
          {
            description +="'";
          }
      }
      description = description.replaceAll("\r","").replaceAll("\n","").replaceAll("'","''");

        description = description.replace('\\', '�');
        description = description.replaceAll("�", "&#92;");

         }

    req = "UPDATE [Interface]";
    req+="    SET [origineInterface] = "+ this.StOrigine.idSt;
    req+="   ,[sensInterface] = '"+ this.sensInterface+ "'";
    req+="   ,[extremiteInterface] = "+ this.StDestination.idSt;
    req+="   ,[typeInterface] = '"+ this.typeInterface + "'";
    req+="   ,[implemInterface] = "+ this.idImplementation;
    req+="   ,[frequenceInterface] = "+ this.idFrequence;
    req+="   ,[descInterface] = '"+ description+ "'";
    req+=" WHERE idInterface=" + this.idInterface;

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.idInterface);
    if (myError.cause == -1) return myError;

    //myError=this.bd_InsertListeOM( nomBase, myCnx,  st,  transaction);

     return myError;

}

public ErrorSpecific bd_UpdateDocumentation(String nomBase,Connexion myCnx, Statement st, String transaction){
     ErrorSpecific myError = new ErrorSpecific();


     req = "UPDATE [Interface]";
     req+="    SET [doc] = '"+ this.doc + "'";

     req+=" WHERE idInterface=" + this.idInterface;

     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.idInterface);
     if (myError.cause == -1) return myError;

     //myError=this.bd_InsertListeOM( nomBase, myCnx,  st,  transaction);

      return myError;

}
   public ErrorSpecific bd_UpdateHtml(String nomBase,Connexion myCnx, Statement st, String transaction){

     ErrorSpecific myError = new ErrorSpecific();
     req = "UPDATE [Interface]";
     req+="    SET [dumpHtml] = '"+ this.dumpHtml.replaceAll("\r","").replaceAll("0x22","'").replaceAll("\u0092","'").replaceAll("'","''")+"'";

     req+=" WHERE idInterface=" + this.idInterface;

     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateHtml",""+this.idInterface);
     if (myError.cause == -1) return myError;

      return myError;

}

    public ErrorSpecific bd_UpdateState(String nomBase,Connexion myCnx, Statement st, String transaction, Collaborateur theCollaborateur){
      ErrorSpecific myError = new ErrorSpecific();

      req = "UPDATE [Interface]";
      req+="    SET [idEtat] = "+this.idEtat+"";
      req+=" WHERE idInterface=" + this.idInterface;

      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateState",""+this.idInterface);
      if (myError.cause == -1) return myError;


       ST theStOrigine = new ST(this.StOrigine.idSt,"idSt");
       theStOrigine.getAllFromBd( nomBase, myCnx,  st);

       Collaborateur theCollaborateurMoe1 = new Collaborateur(theStOrigine.respMoeVersionSt);
       theCollaborateurMoe1.getAllFromBd( nomBase, myCnx,  st);

       ST theStDestination = new ST(this.StDestination.idSt,"idSt");
       theStDestination.getAllFromBd( nomBase, myCnx,  st);
       Collaborateur theCollaborateurMoe2 = new Collaborateur(theStDestination.respMoeVersionSt);
       theCollaborateurMoe2.getAllFromBd( nomBase, myCnx,  st);

       theCollaborateur.getAllFromBd( nomBase, myCnx,  st);

       // Envoi automatique de mails sur changement d'�tat: idEtat != idEtat_old
       String objet="";
       String corps="";
       String destinataire="";
       String emetteur="";

       String mailEmetteur=theCollaborateur.mail;
       String mailDestinataire=theCollaborateurMoe1.mail+";"+theCollaborateurMoe2.mail;

       corps+= "Bonjour";
       corps+= "<br>";

       // ---------------------------------------- Changement etat -> MODIFIE -----------------------------------------------//
           if ((this.idEtat == 1) && (this.idEtat_old != this.idEtat))
           {
             objet = "[STI][MODIFIEE] " + this.idInterface;
             emetteur = mailEmetteur;
             destinataire = mailDestinataire;

             corps = "Bonjour";
             corps += "<br>";

             corps += "Je viens de Modifier la STI: " + this.StOrigine.nomSt + "-" + this.StDestination.nomSt;
             corps += "<br>";
             corps += "<br>";
             corps += "Cordialement";
           }

           // ---------------------------------------- Changement etat -> VALIDE PARTIELLEMENT----------------------------------------//
              else if ((this.idEtat == 2) && (this.idEtat_old != this.idEtat))
               {
                 objet = "[STI][VALIDEE PARTIELLEMENT] " + this.idInterface;
                 emetteur = mailEmetteur;
                 destinataire = mailDestinataire;

                 corps = "Bonjour";
                 corps += "<br>";

                 corps += "Je viens de Valider Partiellement la STI: " + this.StOrigine.nomSt + "-" + this.StDestination.nomSt;
                 corps += "<br>";
                 corps += "<br>";
                 corps += "Cordialement";
           }

           // ---------------------------------------- Changement etat -> VALIDEE -----------------------------------------------//
               else if ((this.idEtat == 3) && (this.idEtat_old != this.idEtat))
               {
                 objet = "[STI][VALIDEE] " + this.idInterface;
                 emetteur = mailEmetteur;
                 destinataire = mailDestinataire;

                 corps = "Bonjour";
                 corps += "<br>";

                 corps += "Je viens de Valider la STI: " + this.StOrigine.nomSt + "-" + this.StDestination.nomSt;
                 corps += "<br>";
                 corps += "<br>";
                 corps += "Cordialement";
           }

             try{
               myCnx.sendmail(emetteur,destinataire,objet,corps);
             }
             catch (Exception e)
             {
               myCnx.trace("@01234***********************","mail non envoy�, sendMail",myCnx.sendMail);
             }

            // myCnx.trace("@01234***** mail envoye******************","Etat",this.idEtat_old+"/"+this.idEtat);
            // myCnx.trace("@01234***** mail envoye******************","mailEmetteur",mailEmetteur);
            // myCnx.trace("@01234***** mail envoye******************","mailDestinataire",mailDestinataire);
            // myCnx.trace("@01234***** mail envoye******************","objet",objet);
            // myCnx.trace("@01234***** mail envoye******************","corps",corps);




       return myError;

}

     public ErrorSpecific bd_UpdateSt2Validate(String nomBase,Connexion myCnx, Statement st, String transaction){

       ErrorSpecific myError = new ErrorSpecific();
       req = "UPDATE [Interface]";
       req+="    SET [idStValide] = "+this.idStValide+"";

       req+=" WHERE idInterface=" + this.idInterface;

       myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_UpdateSt2Validate",""+this.idInterface);
        return myError;

}

  public void getListeSt(String nomBase,Connexion myCnx, Statement st){

  req="SELECT     idVersionSt, nomSt, versionRefVersionSt FROM   TypeInterface WHERE     idImplementation ="+this.idImplementation+"ORDER BY nomSt asc";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  ST theST = null;
  try {
     while (rs.next()) {
       theST = new ST(rs.getInt(1), "idVersionSt");
       theST.nomSt = rs.getString(2);
       //theST.dump();
       this.ListeSt.addElement(theST);
     }
      } catch (SQLException s) {s.getMessage();}

}

public void getListeStByType(String nomBase,Connexion myCnx, Statement st){

  String ClauseST="";
  int num_Clause=0;

  if ((this.StDestination.isEquipement == 0) && (this.StDestination.isComposant == 0) && (this.StDestination.isST == 0) &&(this.StDestination.isAppli == 0) && (this.StDestination.isActeur == 0)) return;
  if (this.StDestination.isEquipement == 1)
  {
    if (num_Clause == 0)
      ClauseST += " WHERE (";
    else
      ClauseST += " OR (";

    ClauseST+= " (isEquipement = 1) ";
    ClauseST += ") ";
    num_Clause++;
  }

  if (this.StDestination.isComposant == 1)
  {
    if (num_Clause == 0)
      ClauseST += " WHERE (";
    else
      ClauseST += " OR (";

    ClauseST+= " (isComposant = 1) ";
    ClauseST += ") ";
    num_Clause++;
  }

  if (this.StDestination.isST == 1)
  {
    if (num_Clause == 0)
      ClauseST += " WHERE (";
    else
      ClauseST += " OR (";

    ClauseST+= " (isST = 1) ";
    ClauseST += ") ";
    num_Clause++;
  }

  if (this.StDestination.isAppli == 1)
  {
    if (num_Clause == 0)
      ClauseST += " WHERE (";
    else
      ClauseST += " OR (";

    ClauseST+= " (isAppli = 1) ";
    ClauseST += ") ";
    num_Clause++;
  }

  if (this.StDestination.isActeur == 1)
  {
    if (num_Clause == 0)
      ClauseST += " WHERE (";
    else
      ClauseST += " OR (";

    ClauseST+= " (isActeur = 1) ";
    ClauseST += ") ";
    num_Clause++;
  }

    req="SELECT DISTINCT idSt, nomSt, idVersionSt FROM   ListeST";
    req+=ClauseST;
    req+=" ORDER by nomSt ASC";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    ST theST = null;
    try {
       while (rs.next()) {
         theST = new ST(rs.getInt("idSt"), "idSt");
         theST.nomSt = rs.getString("nomSt");
         theST.idVersionSt = rs.getInt("idVersionSt");
         //theST.dump();
         this.StDestination.ListeSt.addElement(theST);
       }
        } catch (SQLException s) {s.getMessage();}

}

public static String getTemplate(String nomBase,Connexion myCnx, Statement st, int idTemplate){

  String template = "";
  String req="SELECT     id, nom, template FROM interfaceTemplate WHERE id = "+ idTemplate;


    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    try {
       while (rs.next()) {
         template = rs.getString("template");
       }
        } catch (SQLException s) {s.getMessage();}
  return template;
}

public  String getListeOMAjoutes(String nomBase,Connexion myCnx, Statement st, String ListeOM){
  //myCnx.trace("---------","ListeOM",""+ListeOM);
  String ListeOMAjoutes="";

  // ce sont les OM qui sont dans ListeOM et pas dans Interface.listeOM

  // tokeinsation de ListeOM
  for (StringTokenizer t = new StringTokenizer(ListeOM, ";");
       t.hasMoreTokens(); ) {
  int nb=0;
    String idOM = t.nextToken();
    //myCnx.trace("---------","idOM",""+idOM);
    String req="SELECT     ObjetMetier.nomObjetMetier";
    req+="    FROM         Inter_OM INNER JOIN";
    req+="                   ObjetMetier ON Inter_OM.omInter_OM = ObjetMetier.idObjetMetier";
    req+=" WHERE     (Inter_OM.interInter_OM = "+this.idInterface+") AND (ObjetMetier.idObjetMetier = "+idOM+")";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    try {
       while (rs.next()) {
         nb++;
         String nomObjetMetier = rs.getString("nomObjetMetier");
       }
        } catch (SQLException s) {s.getMessage();}

    //myCnx.trace("---------","nb",""+nb);
    if (nb == 0) // pas dans l'interface, donc rajoute
    {
      req="SELECT     nomObjetMetier FROM  ObjetMetier WHERE     (idObjetMetier = "+idOM+")";
      rs = myCnx.ExecReq(st, nomBase, req);
      try {
         while (rs.next()) {
           ListeOMAjoutes += "/"+rs.getString("nomObjetMetier");
         }
        } catch (SQLException s) {s.getMessage();}
    }
    // token dans ListeOM ? (mieux vaut faire une requete en base)
    //   Oui: � rajouter dans listeOMAjoute
  }
  if (!ListeOMAjoutes.equals("")) ListeOMAjoutes = "OM Ajoutes: "+ListeOMAjoutes;
  return ListeOMAjoutes;
}

public  String getListeOMSupprimes(String nomBase,Connexion myCnx, Statement st, String ListeOM){

  String ListeOMSupprimes="";
  // ce sont les OM qui sont dans Interface.listeOM et pas dans ListeOM

  String req="SELECT     ObjetMetier.idObjetMetier,ObjetMetier.nomObjetMetier";
  req+="    FROM         Inter_OM INNER JOIN";
  req+="                   ObjetMetier ON Inter_OM.omInter_OM = ObjetMetier.idObjetMetier";
  req+=" WHERE     (Inter_OM.interInter_OM = "+this.idInterface+")";

  ResultSet rs = myCnx.ExecReq(st, nomBase, req);
  try {
     while (rs.next()) {
       int idObjetMetier= rs.getInt("idObjetMetier");
       String nomObjetMetier= rs.getString("nomObjetMetier");

       int pos = ListeOM.indexOf(""+idObjetMetier);
       if (pos == -1) // pas dans la liste, donc supprime
       {
         ListeOMSupprimes += "/"+nomObjetMetier;
       }

     }
        } catch (SQLException s) {s.getMessage();}


  if (!ListeOMSupprimes.equals("")) ListeOMSupprimes = "OM supprimes: "+ListeOMSupprimes;
  return ListeOMSupprimes;
}

public static void setListeInterfaces(String nomBase,Connexion myCnx, Statement st, Statement st2){

  String req = "DELETE FROM tempInterface";
  myCnx.ExecReq(st, myCnx.nomBase, req);

  ST.getListeStWithInterface( nomBase, myCnx,  st);

  for (int i=0; i < ST.ListeSt.size();i++)
  {
    ST theST = (ST)ST.ListeSt.elementAt(i);
    theST.getListeInterfacesAsyncOuSync( nomBase, myCnx,  st,  st2 );
  }

}

public static void setListeInterfacesValidees(String nomBase,Connexion myCnx, Statement st, Statement st2){

  String req = "DELETE FROM tempInterface";
  myCnx.ExecReq(st, myCnx.nomBase, req);

  ST.getListeStWithInterfaceValidee( nomBase, myCnx,  st);

  for (int i=0; i < ST.ListeSt.size();i++)
  {
    ST theST = (ST)ST.ListeSt.elementAt(i);
    theST.getListeInterfacesAsyncOuSync( nomBase, myCnx,  st,  st2 );
  }

}
  public Interface(  int idInterface,
  ST StOrigine,
  ST StDestination,
  String sensInterface,
  String typeInterface,
  String nomImplementation,
  String nomFrequence,
  String descInterface,
  int idImplementation,
  int idFrequence
) {
    this.idInterface=idInterface;
    this.StOrigine = StOrigine;
    this.StDestination = StDestination;
    this.sensInterface = sensInterface;
    this.typeInterface = typeInterface;
    this.nomImplementation = nomImplementation;
    this.nomFrequence = nomFrequence;
    this.descInterface = descInterface.replaceAll("\r","").replaceAll("\n","<br>");
    this.idImplementation = idImplementation;
    this.idFrequence = idFrequence;

    if (this.sensInterface.equals("--->"))  { this.imageSensInterface="<img src='images/Fleches/flechebleue_d.gif'>"; }
    else if (this.sensInterface.equals("<---"))  {  this.imageSensInterface="<img src='images/Fleches/flechebleue_g.gif'>"; }
    else if (this.sensInterface.equals("<--->")) {  this.imageSensInterface="<img src='images/Fleches/flechebleue_gd.gif'>"; }

  }

  public OM addListeOM(String nomBase,Connexion myCnx, Statement st,String theObj)
  {
    ResultSet rs=null;
    OM theOM=null;

    if (theObj !=null)
            {
               if (theObj.length() !=0)
                {
                  theObj =theObj + " ";
                  int Index_Blanc=theObj.indexOf(' ');
                  while (Index_Blanc != -1)
                    {
                      String nomOM = theObj.substring (0,Index_Blanc);
                      theObj = theObj.substring(Index_Blanc+1);
                      Index_Blanc = theObj.indexOf(' ');

                        int Index = nomOM.indexOf('_');
                        String theNom = nomOM.substring(Index + 1);
                        int idOM = -1;

                          rs = myCnx.ExecReq(st, nomBase,
                                             "SELECT idObjetMetier FROM  ObjetMetier WHERE (nomObjetMetier = '" +
                                             theNom + "')");

                          try {
                            rs.next();
                            idOM = rs.getInt(1);
                          }
                          catch (SQLException s) {
                            s.getMessage();
                          }
                          if (idOM != -1)
                          {
                            theOM = new OM(idOM, nomOM.substring(0, Index - 1),
                                           theNom);
                            this.ListeOM.addElement(theOM);
                          }
                     }
                  }
              }
  return theOM;
  }

  public void setId(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs = null;
    String req = "EXEC ST_SelectIdInterface "+this.StOrigine.idSt+", "+this.StDestination.idSt;
    rs = myCnx.ExecReq(st,nomBase,req);
    try { rs.next(); this.idInterface = Integer.parseInt(rs.getString(1)); } catch (SQLException s) {s.getMessage();}

  }

  public void getListeOMByType(String nomBase,Connexion myCnx, Statement st , String Type){
    OM myOM=null;
    ResultSet rs;
    req = "exec [LISTEOM_byInterfaceIdByType] "+this.idInterface + ",'"+Type+"'";
    rs = myCnx.ExecReq(st, nomBase, req);

    try {
     while (rs.next())
      {
        int idObjetMetier = rs.getInt("idObjetMetier");
        String nomFamilleOM = rs.getString("nomFamilleOM");
        String nomObjetMetier = rs.getString("nomObjetMetier");

        myOM=this.addOM(
           idObjetMetier,
           nomFamilleOM,
           nomObjetMetier
            );
        //myOM.dump();

        this.ListeOM.addElement(myOM);
        this.ListeStrOM+=myOM.LienObjetMetier + " ";
        
        this.ListeStrOMSansLien+=myOM.Package.toUpperCase()+"_"+myOM.nomObjetMetier + " ";
      }


      }
      catch (SQLException s) {s.getMessage();}
  }

  public void getListeOMByTypeByPackage(String nomBase,Connexion myCnx, Statement st , int Type, String nomPackage){
    OM myOM=null;
    ResultSet rs;
    req = "SELECT     ObjetMetier.idObjetMetier, FamilleOM.nomFamilleOM, ObjetMetier.nomObjetMetier, packageOM.nomPackage";
    req+="    FROM         Inter_OM INNER JOIN";
    req+="                   ObjetMetier ON Inter_OM.omInter_OM = ObjetMetier.idObjetMetier INNER JOIN";
    req+="                   FamilleOM ON ObjetMetier.famObjetMetier = FamilleOM.idFamilleOM INNER JOIN";
    req+="                   Interface ON Inter_OM.interInter_OM = Interface.idInterface INNER JOIN";
    req+="                   packageOM ON ObjetMetier.package = packageOM.idPackage";
    req+=" WHERE     (Interface.idInterface = "+this.idInterface+") AND (ObjetMetier.typeOM  = '"+Type+"') AND (packageOM.nomPackage = '"+nomPackage+"')";

    rs = myCnx.ExecReq(st, nomBase, req);

    try {
     while (rs.next())
      {
        int idObjetMetier = rs.getInt("idObjetMetier");
        String nomFamilleOM = rs.getString("nomFamilleOM");
        String nomObjetMetier = rs.getString("nomObjetMetier");
        String nomTypeAppli = "";

        myOM=this.addOM(
           idObjetMetier,
           nomFamilleOM,
           nomObjetMetier
            );

        Node theNode = new Node(myOM.nomObjetMetier);
        theNode.forme = nomTypeAppli;
        myOM.NodeOm = theNode;

        //myOM.dump();

        this.ListeOM.addElement(myOM);
        this.ListeStrOM+=myOM.LienObjetMetier + " ";
        this.ListeStrOMSansLien+=myOM.Package.toUpperCase()+"_"+myOM.nomObjetMetier + " ";
      }


      }
      catch (SQLException s) {s.getMessage();}
  }

  public String getisChecked(int idObj){

    for (int i=0; i < this.ListeOM.size(); i++)
    {
      OM theOM = (OM)this.ListeOM.elementAt(i);
      if (theOM.idObjetMetier == idObj) return "checked";
    }
    return "";
 }

 public ErrorSpecific bd_InsertHistoriqueInterface(String nomBase,Connexion myCnx, Statement st, String transaction, Suivi theSuivi){

   ErrorSpecific myError = new ErrorSpecific();
   req = "INSERT historiqueInterface (";
   req += "dateSuivi" + ",";
   req += "nom" + ",";
   req += "description" + ",";
   req += "idInterface" + ",";
   req += "type" + ",";
   req += "Login" + "";
   req += ")";
   req += " VALUES ";
   req += "(";
   req += "CONVERT(DATETIME, CURRENT_TIMESTAMP, 102)"+ ",";
   req += "'" + "Migration initiale" + "',";

   req += "'" + theSuivi.description.replaceAll("\u0092", "'").replaceAll("'", "''") + "',";

   req += "'" + this.idInterface + "',";
   req += "'" +theSuivi.type.replaceAll("\u0092", "'").replaceAll("'", "''") + "',";
   req += "'" +theSuivi.Login + "'";
   req += ")";

   myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertHistoriqueInterface",""+this.idInterface);
   if (myError.cause == -1) return myError;

    return myError;
  }

  public void getListePackageOM(String nomBase,Connexion myCnx, Statement st ){
    OM myOM=null;
    ResultSet rs;
    req = "SELECT DISTINCT packageOM.idPackage, packageOM.nomPackage, packageOM.descPackage, packageOM.nomFamille AS idFamille";
    req+="    FROM         Inter_OM INNER JOIN";
    req+="                   ObjetMetier ON Inter_OM.omInter_OM = ObjetMetier.idObjetMetier INNER JOIN";
    req+="                   Interface ON Inter_OM.interInter_OM = Interface.idInterface INNER JOIN";
    req+="                   packageOM ON ObjetMetier.package = packageOM.idPackage";
    req+=" WHERE     (Interface.idInterface = "+this.idInterface+")";
    req+=" ORDER BY packageOM.nomPackage";

    rs = myCnx.ExecReq(st, nomBase, req);

    try {
     while (rs.next())
      {
        Paquetage thePackage = new Paquetage();
        thePackage.id = rs.getInt("idPackage");
        thePackage.nom = rs.getString("nomPackage");
        thePackage.description = rs.getString("descPackage");

        this.ListePackages.addElement(thePackage);

      }


      }
      catch (SQLException s) {s.getMessage();}
  }

  public  static void getListeImplementationByTypeInterface(String nomBase,Connexion myCnx, Statement st, String typeInterface){

    String req= "EXEC IMPLEMENTATION_SelectImplementation '"+typeInterface+"'";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    item theItem = null;
    try {
       while (rs.next()) {
         theItem = new item();
         theItem.id = rs.getInt(1);
         theItem.nom = rs.getString(2);
         //theST.dump();
         ListeImplementation.addElement(theItem);
       }
      } catch (SQLException s) {s.getMessage();}

  }

  public void getListeSuivi(String nomBase,Connexion myCnx, Statement st ){

    ResultSet rs;

    String req = "SELECT     id, dateSuivi, description, Login";
    req+= " FROM         historiqueInterface";
    req+= " WHERE     (idInterface = "+this.idInterface+")  order by datesuivi desc";


rs = myCnx.ExecReq(st, myCnx.nomBase, req);

try {
  while (rs.next()) {
    Suivi theSuivi = new Suivi(rs.getInt("id"));
    theSuivi.dateSuivi = rs.getDate("dateSuivi");

    //theSuivi.nom = rs.getString("nom");

    theSuivi.description = rs.getString("description");
    theSuivi.Login = rs.getString("Login");
    if (theSuivi.Login == null) theSuivi.Login="";

    this.ListeSuivi.addElement(theSuivi);
  }
}
        catch (SQLException s) {
          s.printStackTrace();
        }

  }


  public void getListeOM(String nomBase,Connexion myCnx, Statement st ){
    OM myOM=null;
    ResultSet rs;
    req = "exec [LISTEOM_byInterfaceId] "+this.idInterface;
    rs = myCnx.ExecReq(st, nomBase, req);
    int nbOM = 1;

    try {
     while (rs.next())
      {
        int idObjetMetier = rs.getInt("idObjetMetier");
        String nomFamilleOM = rs.getString("nomFamilleOM");
        String nomObjetMetier = rs.getString("nomObjetMetier");

        myOM=this.addOM(
           idObjetMetier,
           nomFamilleOM,
           nomObjetMetier
            );
        //myOM.dump();

        this.ListeOM.addElement(myOM);
        this.ListeStrOM+=myOM.LienObjetMetier + " ";
        if (!this.ListeStrOM2.equals(""))
            this.ListeStrOM2=this.ListeStrOM2 + ";" + myOM.nomObjetMetier;
        else
             this.ListeStrOM2=myOM.nomObjetMetier;
        //this.ListeStrOMSansLien+=myOM.Package.toUpperCase()+"_"+myOM.nomObjetMetier + " ";
        this.ListeStrOMSansLien+=myOM.nomObjetMetier + " ";
        nbOM++;
      }


      }
      catch (SQLException s) {s.getMessage();}
  }
  
 public String stripSTI(String strDebut,String strFin){
     if (this.descInterface == null) 
     {
         this.descInterface= ""; 
         return "";
     }
     
     String str_description = "";
     str_description = Utils.stripHtml(this.descInterface);
     int pos = str_description.indexOf(strDebut);
     if (pos >= 0)
           str_description = str_description.substring(strDebut.length());
     pos = str_description.indexOf(strFin);
     if (pos >= 0)
           str_description = str_description.substring(0,pos);
   return str_description;
 }
 
 public String stripSTI(String strDebut,String strFin, int lg){
     if (this.descInterface == null) 
     {
         this.descInterface= ""; 
         return "";
     }
     
     String str_description = "";
     str_description = Utils.stripHtml(this.descInterface);
     int pos = str_description.indexOf(strDebut);
     if (pos >= 0)
           str_description = str_description.substring(strDebut.length());
     pos = str_description.indexOf(strFin);
     if (pos >= 0)
           str_description = str_description.substring(0,pos);
     
     if (str_description.length() <= lg)
         return str_description;
     else
         return str_description.substring(0, lg-1)+"...";
 
 } 
 
  public OM addOM(
    int idObjetMetier,
    String nomFamilleOM,
    String nomObjetMetier
    )
{
    OM theOM=new OM(
                    idObjetMetier,
                    nomFamilleOM,
                    nomObjetMetier
                      );

  return theOM;

}

public String buildParamLink(int end){

  String ParamLink="";

  if (end != 0) ParamLink+=",";

  ParamLink+=this.StOrigine.nomSt;
  ParamLink+="#";

  ParamLink+=this.StDestination.nomSt;
  ParamLink+="!";

  for (int i=0; i < this.ListeOM.size();i++)
  {
    if (i !=0) ParamLink += " ";
    OM theOM = (OM)this.ListeOM.elementAt(i);
    ParamLink += theOM.nomObjetMetier;

  }
  ParamLink+="!";

  ParamLink+=this.sensInterface;
  ParamLink+="!";

  if(this.typeInterface.equals("ST"))
    this.genreInterface="St2Appli";
  else if(this.typeInterface.equals("M"))
    this.genreInterface="manuel";
  else if(this.typeInterface.equals("Heb"))
    this.genreInterface="Heb";
  else if(this.typeInterface.equals("Mod"))
    this.genreInterface="Mod";
  else if(this.typeInterface.equals("F"))
    this.genreInterface="F";
  else
    this.genreInterface="normal";

  ParamLink+=this.genreInterface;

  return ParamLink;
  }

public void ExportWord(String nomBase,Connexion myCnx, Statement st){
  Date myDate = new Date();
  Word myWord = new Word();
  myWord.create();

  //myWord.attach("doc/interface/exportWord","INTERFACE_"+this.idInterface+"-"+myDate.getTime()+".docx");
  String[] entete = {"num","nomAttribut","Req/resp","typeAttribut","descAttribut"};
  myWord.attach("doc/interface/exportWord","INTERFACE_"+this.idInterface+"-"+".docx");


  for (int numPackage=0; numPackage < this.ListePackages.size();numPackage++)
  {
    Paquetage thePackage  = (Paquetage)this.ListePackages.elementAt(numPackage);
    this.ListeOM.removeAllElements();
    this.getListeOMByTypeByPackage(myCnx.nomBase,myCnx,  st, 1, thePackage.nom);

    myWord.paragraph = myWord.document.createParagraph();
    myWord.locRun=myWord.paragraph.createRun();
    myWord.locRun.setBold(true);
    myWord.locRun.setText(""+numPackage+" ---- PACKAGE: "+thePackage.nom);

    System.out.println(""+numPackage+" ---- PACKAGE: "+thePackage.nom);

    for (int numObj=0; numObj < this.ListeOM.size();numObj++)
    {
      OM theOM = (OM) this.ListeOM.elementAt(numObj);
      theOM.getAllFromBd(myCnx.nomBase, myCnx, st);
      theOM.getListeAttributs(myCnx.nomBase,myCnx,  st );

      myWord.paragraph2 = myWord.document.createParagraph();
      myWord.locRun2=myWord.paragraph2.createRun();
      myWord.locRun2.setBold(true);
      myWord.locRun2.addBreak();
      myWord.locRun2.setText(""+numObj+" ---- OBJET: "+theOM.nomObjetMetier);
      System.out.println(""+numObj+" ---- OBJET: "+theOM.nomObjetMetier);

      myWord.table =myWord.document.createTable(theOM.ListeAttributs.size()+1, 5);



      myWord.rows = myWord.table.getRow(0);

      for (int numColonne = 0; numColonne < 5; numColonne++) {
        myWord.cell = myWord.rows.getCell(numColonne);

        //System.out.println("entete["+numColonne+"]= "+entete[numColonne]);
        myWord.cell.setText(entete[numColonne]);
      }

      for(int numAttribute = 0;numAttribute < theOM.ListeAttributs.size();numAttribute++) {
        Attribut theAttribut  = (Attribut)theOM.ListeAttributs.elementAt(numAttribute);
        String nomSens="";
        if (theAttribut.sens == 1)
        {
          nomSens = "Req";
        }
        else if (theAttribut.sens == 2)
        {
          nomSens = "Resp";
        }

        myWord.rows = myWord.table.getRow(numAttribute+1);


        System.out.println(""+numAttribute+" ---- ATTRIBUT: "+theAttribut.nom);
        for (int numColonne = 0; numColonne < 5; numColonne++) {

        myWord.cell = myWord.rows.getCell(numColonne);

          if (numColonne == 0)
            myWord.cell.setText(""+(numAttribute));
          else if (numColonne == 1)
            myWord.cell.setText(""+theAttribut.nom);
          else if (numColonne == 2)
            myWord.cell.setText(""+nomSens);
          else if (numColonne == 3)
            myWord.cell.setText(""+theAttribut.nomtypeAttribut);
          else if (numColonne == 4)
            myWord.cell.setText(""+theAttribut.descModule);
        }
        //myWord.rows.setHeight(20);


      }
      myWord.table.setWidth(1000);
    }
   }


myWord.close();
}
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("idInterface="+this.idInterface);
    if (this.StOrigine != null)
    {
      System.out.println("StOrigine.idSt=" + this.StOrigine.idSt);
      System.out.println("StOrigine.idVersionSt=" + this.StOrigine.idVersionSt);
      System.out.println("StOrigine.nomSt=" + this.StOrigine.nomSt);
      System.out.println("StOrigine.versionRefVersionSt=" +
                         this.StOrigine.versionRefVersionSt);
      System.out.println("StOrigine.TypeApplication=" +
                         this.StOrigine.TypeApplication);
      System.out.println("StOrigine.couleur=" + this.StOrigine.couleur);
    }
    System.out.println("sensInterface="+this.sensInterface);
    if (this.StDestination != null)
    {
      System.out.println("StDestination.idSt=" + this.StDestination.idSt);
      System.out.println("StDestination.idVersionSt=" +
                         this.StDestination.idVersionSt);
      System.out.println("StDestination.nomSt=" + this.StDestination.nomSt);
      System.out.println("StDestination.versionRefVersionSt=" +
                         this.StDestination.versionRefVersionSt);
      System.out.println("StDestination.TypeApplication=" +
                         this.StDestination.TypeApplication);
      System.out.println("StDestination.couleur=" + this.StDestination.couleur);
    }
    System.out.println("typeInterface="+this.typeInterface);;
    System.out.println("genreInterface="+this.genreInterface);;
    System.out.println("nomImplementation="+this.nomImplementation);
    //System.out.println("descInterface="+this.descInterface);
    System.out.println("idImplementation="+this.idImplementation);;
    //System.out.println("idFrequence="+this.idFrequence);;
    //System.out.println("dumpHtml="+this.dumpHtml);
    System.out.println("idEtat="+this.idEtat);
    System.out.println("idStValide="+this.idStValide);
     System.out.println("doc="+this.doc);
    System.out.println("==================================================");
  }

  public static void main(String[] args) {
    Connexion myCnx = null;
    Statement st, st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";
    String base = myCnx.getDate();
    st = myCnx.Connect();

    String ListeOM="30;31;282";

    Interface theInterface = new Interface(65379);
    theInterface.getAllFromBd(myCnx.nomBase,myCnx,st);
    theInterface.getListePackageOM(myCnx.nomBase,myCnx,st);

    theInterface.ExportWord(myCnx.nomBase,myCnx,st);

    myCnx.Unconnect(st);
  }

  public static void main2(String[] args) {
    Connexion myCnx = null;
    Statement st, st2 = null;
    ResultSet rs = null;
    ResultSet rs2 = null;
    String req = "";
    String base = myCnx.getDate();
    st = myCnx.Connect();

    String ListeOM="30;31;282";

    Interface theInterface = new Interface(64170);
    theInterface.getAllFromBd(myCnx.nomBase,myCnx,st);

    //theInterface.getListeOM(myCnx.nomBase,myCnx,st);
    //String result=theInterface.getListeOMAjoutes(myCnx.nomBase,myCnx,  st, ListeOM);
    //result+=theInterface.getListeOMSupprimes(myCnx.nomBase,myCnx,  st, ListeOM);
    //System.out.println("result="+result);

    myCnx.Unconnect(st);
  }

}


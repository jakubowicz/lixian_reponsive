package OM; 

import General.Utils;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import java.util.Vector;
import Graphe.Node;
import ST.ST;
import ST.Interface;
import accesbase.transaction;
import java.util.StringTokenizer;
import accesbase.ErrorSpecific;
import Graphe.Forme; 

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
public class OM extends Forme{
  public int idObjetMetier;
  public String nomObjetMetier;
  public String defObjetMetier;
  public String Package;
  public int idPackage;
  public String Famille;
  public int idFamille;
  public int typeEtatObjetMetier=1;
  public boolean racineObjetMetier;
  public int idAppliIcone;
  public String LienObjetMetier;
  public String LienUml;
  public String LienWsdl;
  public String Criticite;

  public String param_edges ="";
  public String param_nodes ="";

  public String param_edges_Refst ="";
  public String param_nodes_Refst ="";

   public Vector ListeRelations = new Vector(10);
   public Vector ListeInterfaces = new Vector(20);
   public Vector ListeNodes = new Vector(10);
   public Vector ListeNodesRefSt = new Vector(10);
   public Vector ListeRefSt = new Vector(10);
   public Vector ListeAttributs = new Vector(10);
   public static Vector ListeOM = new Vector(10);
   public static Vector ListeOMInterface = new Vector(10);
   public static Vector ListeFamille = new Vector(10);
   public static Vector ListePaquetage = new Vector(10);

    public String action="";
    public String chaine_a_ecrire="";

  private String req="";
  private String TypeCreation = "";

  public int idProprietaire;
  public String nomProprietaire;

  public int typeOM=0;

  public String dateImport;
  public String diagnostic;

  public Node NodeOm=null;

  static int delta_x = 2;
  static int marge_x = 10;
  static int delta_y = 2;
   static int marge_y = 10;
   
   public int isShortCut=0;
   public int idStOrigine  = -1;
   public String nomStOrigine  = "";

   public OM(String Label, int x, int y){
     this.Label=Label;
     this.x= x;
     this.y=y;

     this.CouleurTexte="black";

     this.Style = "Verdana";
     this.Epaisseur = 1;
     this.Taille = 8;

  }

  public OM (int idObjetMetier){
    this.idObjetMetier=idObjetMetier;
    this.nomObjetMetier="";
   this.defObjetMetier="";
    this.Package="";
    this.idPackage=0;
    this.Famille="";
    this.idFamille=0;
    this.typeEtatObjetMetier=1;
    this.racineObjetMetier=false;
    this.idAppliIcone=0;
    this.LienObjetMetier="";
    this.LienUml="";
    this.LienWsdl="";
    this.Criticite="Mineur";
      this.TypeCreation = "idOm";
  }


  public OM (int idObjetMetier, String action){
  this.idObjetMetier=idObjetMetier;
  this.nomObjetMetier="";
 this.defObjetMetier="";
  this.Package="";
  this.idPackage=0;
  this.Famille="";
  this.idFamille=0;
  this.typeEtatObjetMetier=1;
  this.racineObjetMetier=false;
  this.idAppliIcone=0;
  this.LienObjetMetier="";
  this.LienUml="";
  this.LienWsdl="";
  this.Criticite="Mineur";

  this.action=action;
  this.TypeCreation = "idOm";
}

public OM (String nomObjetMetier){
this.nomObjetMetier=nomObjetMetier;
this.TypeCreation = "nomOm";

}



  public OM(int idObjetMetier, String Package, String nomObjetMetier) {
    this.idObjetMetier=idObjetMetier;
    this.Package=Package;
    this.nomObjetMetier=nomObjetMetier;
    this.LienObjetMetier="<a href='#'   onClick=\"openOm("+this.idObjetMetier+", " + "\'"+this.nomObjetMetier +"\')\">"+this.nomObjetMetier+"</a>";
      this.TypeCreation = "idOm";
  }

  public ErrorSpecific bd_Enreg(String action, String typeForm, String nomBase,Connexion myCnx, Statement st, String transaction){

    ErrorSpecific myError = new ErrorSpecific();
    String result="";
    if (typeForm.equals("Creation")) { // Cr�ation d'un nouveau ST
      myError=this.bd_Insert( nomBase, myCnx,  st,  transaction);
    }
    else { //MAJ du ST
      if (action.equals("Supprimer"))
          { //l'utilisateur a cliqu� sur le bouton supprimer

           myError=this.bd_Delete(nomBase,myCnx,st,transaction);

          }
            else
            {
              myError=this.bd_Update( nomBase, myCnx,  st,  transaction);
            }
}
    return myError;
  }

  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
           req = "INSERT ObjetMetier (";
             req+="nomObjetMetier"+",";
             req+="defObjetMetier"+",";
             req+="package"+",";
             req+="typeEtatObjetMetier"+",";
             req+="famObjetMetier"+",";
             req+="Criticite"+",";
             req+="idAppliIcone"+",";
             req+="idProprietaire"+",";
             req+="typeOM"+","; 
             req+="isShortCut"+","; 
             req+="idStOrigine";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+this.nomObjetMetier+"',";
             req+="'"+this.defObjetMetier.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
             req+="'"+this.idPackage+"',";
             req+="'"+this.typeEtatObjetMetier+"',";
             req+="'"+this.idFamille+"',";
             req+="'"+this.Criticite+"',";
             req+="'"+this.idAppliIcone+"',";
             req+="'"+this.idProprietaire+"',";
             req+="'"+this.typeOM+"',";
             req+="'"+this.isShortCut+"',";
             req+="'"+this.idStOrigine+"'";
           req+=")";

           myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.idObjetMetier);
           if (myError.cause == -1) return myError;

          req = "SELECT  idObjetMetier FROM   ObjetMetier WHERE nomObjetMetier = '"+this.nomObjetMetier+"'";
          rs = myCnx.ExecReq(st, nomBase, req);
          try {rs.next(); this.idObjetMetier =rs.getInt("idObjetMetier"); } catch (SQLException s) {s.getMessage();}

    this.chaine_a_ecrire = "L'OM: "+this.nomObjetMetier+ " a bien �t� cr�e dans la base";
    return myError;
  }

  public ErrorSpecific bd_Delete(String nomBase,Connexion myCnx, Statement st, String transaction){
    ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;

    req="DELETE FROM ObjetMetier  WHERE  (nomObjetMetier = '"+this.nomObjetMetier+"')";
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Delete",""+this.idObjetMetier);
    if (myError.cause == -1) return myError;

   return myError;
 }


 public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
   ErrorSpecific myError = new ErrorSpecific();
   req = "UPDATE ObjetMetier SET ";

   req+="nomObjetMetier='"+this.nomObjetMetier+"',";
   req+="defObjetMetier='"+this.defObjetMetier.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
   req+="package='"+this.idPackage+"',";
   req+="typeEtatObjetMetier='"+this.typeEtatObjetMetier+"',";
   req+="famObjetMetier='"+this.idFamille+"',";
   req+="Criticite='"+this.Criticite+"',";
   req+="idAppliIcone='"+this.idAppliIcone+"',";
   req+="idProprietaire='"+this.idProprietaire+"',";
   req+="typeOM='"+this.typeOM+"',";
   req+="isShortCut='"+this.isShortCut+"',";
   req+="idStOrigine='"+this.idStOrigine+"'";

   req+=" WHERE idObjetMetier="+this.idObjetMetier;
   myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Update",""+this.idObjetMetier);
   if (myError.cause == -1) return myError;

    if (this.action.equals("enreg"))
      this.chaine_a_ecrire = "L'OM: "+this.nomObjetMetier+ " a bien �t� enregistr� dans la base";
    else if (this.action.equals("valid"))
      this.chaine_a_ecrire = "L'OM: "+this.nomObjetMetier+ " a bien �t� enregistr� dans la base";

    return myError;

}

  public String bd_UpdateCrImport(String nomBase,Connexion myCnx, Statement st, String transaction){
    req = "UPDATE ObjetMetier SET ";

    req+="dateImport='"+this.dateImport+"',";
    req+="diagnostic='"+this.diagnostic+"'";

    req+=" WHERE idObjetMetier="+this.idObjetMetier;
    int check = 0;
    check=myCnx.ExecUpdate(st,nomBase,req,true,transaction);
    //myCnx.trace("*************************","check",""+check);
     if (check == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

     return "OK";

}

  public void getListeRelations(String nomBase,Connexion myCnx, Statement st, Statement st2 ){
    ResultSet rs;

    req= " SELECT     RelationsOM.id, RelationsOM.idSens,TypeSens.nom AS sens, typeInterfaces.description AS type, RelationsOM.origineOM, RelationsOM.cardinaliteOrigine, RelationsOM.destinationOM, RelationsOM.cardinaliteDestination,"; 
    req+="                       RelationsOM.description, RelationsOM.idTypeRelation, ObjetMetier.nomObjetMetier, ObjetMetier_1.nomObjetMetier AS nomdestinationOM";
    req+=" FROM         RelationsOM INNER JOIN";
    req+="                       ObjetMetier ON RelationsOM.origineOM = ObjetMetier.idObjetMetier INNER JOIN";
    req+="                       ObjetMetier AS ObjetMetier_1 ON RelationsOM.destinationOM = ObjetMetier_1.idObjetMetier INNER JOIN";
    req+="                       typeInterfaces ON RelationsOM.idTypeRelation = typeInterfaces.id INNER JOIN";
    req+="                                            TypeSens ON RelationsOM.idSens = TypeSens.id";
    req+=" WHERE     ObjetMetier.idObjetMetier =" + this.idObjetMetier;   
    req+=" UNION    ";
    req+=" SELECT     RelationsOM.id, RelationsOM.idSens, TypeSens.nom AS sens, typeInterfaces.description AS type, RelationsOM.origineOM, RelationsOM.cardinaliteOrigine, RelationsOM.destinationOM, RelationsOM.cardinaliteDestination,"; 
    req+="                       RelationsOM.description, RelationsOM.idTypeRelation, ObjetMetier.nomObjetMetier, ObjetMetier_1.nomObjetMetier AS destnomdestinationOMinationOM";
    req+=" FROM         RelationsOM INNER JOIN";
    req+="                       ObjetMetier ON RelationsOM.destinationOM = ObjetMetier.idObjetMetier INNER JOIN";
    req+="                       ObjetMetier AS ObjetMetier_1 ON RelationsOM.origineOM = ObjetMetier_1.idObjetMetier INNER JOIN";
    req+="                       typeInterfaces ON RelationsOM.idTypeRelation = typeInterfaces.id INNER JOIN";
    req+="                                            TypeSens ON RelationsOM.idSens = TypeSens.id";    
    req+=" WHERE     ObjetMetier.idObjetMetier =" + this.idObjetMetier;    
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try { while(rs.next()) {
            Relation theRelation = new Relation (rs.getInt("id"));
            theRelation.idSens = rs.getInt("idSens");
            theRelation.sens = rs.getString("sens");
            theRelation.type = rs.getString("type");
            
            
            OM theOM1 = new OM (rs.getInt("origineOM"));
            theRelation.cardinaliteOrigine = rs.getString("cardinaliteOrigine");
            OM theOM2 = new OM (rs.getInt("destinationOM"));           
            theRelation.cardinaliteDestination = rs.getString("cardinaliteDestination");
            theRelation.description = rs.getString("description");
            theRelation.idTypeRelation = rs.getInt("idTypeRelation");
            
            theRelation.origineOM = this;
            
            if (theOM1.idObjetMetier == this.idObjetMetier)
            {
                 
                theRelation.destinationOM = theOM2;
            }
            else
            {
                theRelation.destinationOM = theOM1; 
                
                if (!theRelation.sens.equals("<--->"))
                {
                if (theRelation.idSens == 1)
                    theRelation.idSens = 2;
                else if (theRelation.idSens == 2)
                    theRelation.idSens = 1;
                
                }   
                
            }
                       
            theRelation.destinationOM.nomObjetMetier = rs.getString("nomdestinationOM");         
            
            theRelation.origineOM.getNode( nomBase, myCnx,  st2);
            //myCnx.trace("1--------"+theRelation.destinationOM.nomObjetMetier,"theRelation.origineOM",""+theRelation.origineOM.NodeOm);
            theRelation.origineOM.NodeOm.idGraphe = this.idObjetMetier;    
            theRelation.destinationOM.getNode( nomBase, myCnx,  st2);
            //myCnx.trace("1--------"+theRelation.destinationOM.nomObjetMetier,"theRelation.origineOM",""+theRelation.origineOM.NodeOm);
            theRelation.destinationOM.NodeOm.idGraphe = this.idObjetMetier;             
            
            this.ListeRelations.addElement(theRelation);

        } }catch (Exception e){};  

  }

  public void getListeRelations2(String nomBase,Connexion myCnx, Statement st, Statement st2 ){
    ResultSet rs;

    String param_nodes ="";


    req = "exec LISTEOM_selectRelationsOm "+this.idObjetMetier;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try { while(rs.next()) {
            String sens = rs.getString(1);
            String type = rs.getString(2);
            //type = "normal";
            String cardinaliteOrigine = rs.getString(3);
            String origineOM = this.nomObjetMetier;
            int idOM1 = rs.getInt(4);
            String cardinaliteDestination = rs.getString(5);
            String description = rs.getString(6);
            OM destinationOM = new OM(-1);
            destinationOM.nomObjetMetier = rs.getString(7);
            int idOM2 = rs.getInt(8);
            
            int iddestinationOM;
            int idOrigineOM;
            
            if (idOM1 == this.idObjetMetier)
            {
                idOrigineOM = idOM1;
                iddestinationOM = idOM2;
            }
            else
            {
                idOrigineOM = idOM2;
                iddestinationOM = idOM1;
            }



            if (!sens.equals("<--->"))
            {
              if (((this.idObjetMetier == iddestinationOM) ) && ((sens.equals("--->")) || (sens.equals("---&gt;"))))
                 sens = "<---";
              else if (((this.idObjetMetier == iddestinationOM) ) && ((sens.equals("<---")) || (sens.equals("&lt;---"))))
                 sens = "--->";
            }
            Relation theRelation = new Relation(this, destinationOM,sens, type, cardinaliteOrigine, cardinaliteDestination, description );
            theRelation.origineOM.getNode( nomBase, myCnx,  st2);
            theRelation.origineOM.NodeOm.idGraphe = this.idObjetMetier;
            
            destinationOM.idObjetMetier = iddestinationOM;
            theRelation.destinationOM = destinationOM;
            theRelation.destinationOM.getNode( nomBase, myCnx,  st2);
            theRelation.destinationOM.NodeOm.idGraphe = this.idObjetMetier;
            
            theRelation.idTypeRelation = rs.getInt("idTypeRelation");
            
            /*
            theRelation.origineOM.NodeOm = new Node(this.idObjetMetier,this.idObjetMetier,"GraphOM", -1, -1, this.nomObjetMetier,"", "");
            
            destinationOM.idObjetMetier = iddestinationOM;
            theRelation.destinationOM = destinationOM;
            theRelation.destinationOM.NodeOm = new Node(iddestinationOM,this.idObjetMetier,"GraphOM", -1, -1, this.nomObjetMetier,"", "");
                    */
         
            
            this.ListeRelations.addElement(theRelation);

            this.param_edges+=theRelation.param_edges;

        } }catch (Exception e){};

  }

  public void getListeRelationsRefSt(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    String centre = "";
    ST StOrigine = null;
    ST StDestination = null;
    OM theOM=null;
    Interface theInterface=null;

    int i=0;


    req = "exec OM_CirculationOM "+this.idObjetMetier;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try { while(rs.next()) {
        String origineSt = rs.getString(1);
        String sens = rs.getString(2);
        String destinationSt = rs.getString(3);
        int idorigineSt = rs.getInt(4);
        int iddestinationSt = rs.getInt(5);
        String description = rs.getString("description");

       StOrigine = new ST(-1,origineSt,origineSt,idorigineSt,"","",-1);
       StDestination = new ST(-1,destinationSt,destinationSt,iddestinationSt,"","",-1);

       
        StOrigine.NodeSt = new Node(StOrigine.nomSt,StOrigine.idSt, StOrigine.idVersionSt,"GraphSt",StOrigine.couleur,StOrigine.TypeApplication,"",StOrigine.idVersionSt);
        StOrigine.NodeSt.idGraphe=this.idObjetMetier;
        StOrigine.NodeSt.idLien = StOrigine.idVersionSt;
        StOrigine.NodeSt.idNode = StOrigine.idVersionSt;
        StOrigine.NodeSt.nomNode = StOrigine.nomSt;
        //StOrigine.NodeSt.dump();

        StDestination.NodeSt = new Node(StDestination.nomSt,StDestination.idSt, StDestination.idVersionSt,"GraphSt",StDestination.couleur,StDestination.TypeApplication,"",StDestination.idVersionSt);
        StDestination.NodeSt.idGraphe=this.idObjetMetier;
        StDestination.NodeSt.idLien = StDestination.idVersionSt;  
        StDestination.NodeSt.idNode = StDestination.idVersionSt;
        StOrigine.NodeSt.nomNode = StDestination.nomSt;
       
       theInterface=new Interface(
         -1,
         StOrigine,
         StDestination,
         sens,
         "S",
         "",
         "",
         description,
         -1,
        -1);

        theOM = new OM(-1,"",this.nomObjetMetier);
        theInterface.ListeOM.addElement(theOM);
        
        theInterface.idInterface = rs.getInt("idInterface");
        this.ListeInterfaces.addElement(theInterface);

        this.param_edges_Refst+=theInterface.buildParamLink(i);
        i++;

        } }catch (Exception e){};

  }

  public void getListeNodes(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;


    req = "exec LISTEOM_selectOmByGraph "+this.idObjetMetier;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try { while(rs.next()) {
            String nomObjetMetier = rs.getString(1);
            int x = rs.getInt(2);
             int y = rs.getInt(3);
            String formeTypeAppli= rs.getString(4);
            if (formeTypeAppli == null) formeTypeAppli = "inconnu";
            String couleur= rs.getString(5);
            if (couleur == null) couleur = "rouge";
            int idOtherObj= rs.getInt(6);
            Node theNode = new Node(idOtherObj,-1,"",x,y,nomObjetMetier,couleur,formeTypeAppli);
            this.ListeNodes.addElement(theNode);
            this.param_nodes+=theNode.param_nodes;

        } }catch (Exception e){};


  }

  public void getListeNodesRefSt(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs;


  req = "exec [ST_ListeByOM] "+this.idObjetMetier;
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try { while(rs.next()) {
      String nomSt = rs.getString(1);
      int idVersionSt= rs.getInt(2);
      String formeTypeAppli= rs.getString(3);
      if (formeTypeAppli == null) formeTypeAppli = "inconnu";
      String couleur= rs.getString(4);
      if (couleur == null) couleur = "rouge";

      int x = rs.getInt(5);
      int y = rs.getInt(6);

          Node theNode = new Node(idVersionSt,-1,"",x,y,nomSt,couleur,formeTypeAppli);
          this.ListeNodesRefSt.addElement(theNode);
          this.param_nodes_Refst+=theNode.param_nodes;

      } }catch (Exception e){};


}

  public void addListeRefSt(int id,int numStOrigine,String nomSt,int idVersionSt)
 {
    ST theST = new ST(id,numStOrigine,nomSt,idVersionSt);
    this.ListeRefSt.addElement(theST);
  }

  public void addListeAttribut(String nomAttribut,String descAttribut)
 {
    Attribut theAttribut = new Attribut();
    theAttribut.nom = nomAttribut;
    //theAttribut.descModule = descAttribut.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
    theAttribut.descModule = descAttribut;
    this.ListeAttributs.addElement(theAttribut);
  }

  public void addListeRelation(String nomBase,Connexion myCnx, Statement st,String OmSource,String OmDest,String typeInter,String sensInterf,String CardOrig,String CardDest,String descInter)
 {
   ResultSet rs=null;
   OM origineOM = null;
   OM destinationOM =null;
   if (OmSource.equals(this.nomObjetMetier))
   {
     origineOM = this;
     int idOmDest=-1;
     req = "SELECT idObjetMetier FROM   ObjetMetier WHERE nomObjetMetier ='"+OmDest+"'";
     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try {rs.next(); idOmDest = rs.getInt(1);} catch (SQLException s) {s.getMessage();}
     destinationOM = new OM(idOmDest, "", OmDest);

   }
   else
   {
     destinationOM = this;
     int idOmSource=-1;
     req = "SELECT idObjetMetier FROM   ObjetMetier WHERE nomObjetMetier ='"+OmSource+"'";
     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try {rs.next(); idOmSource = rs.getInt(1);} catch (SQLException s) {s.getMessage();}
     origineOM = new OM(idOmSource, "", OmSource);
   }

    Relation theRelation = new Relation(origineOM, destinationOM, sensInterf, typeInter, CardOrig, CardDest, descInter );
    theRelation.description = descInter.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
    this.ListeRelations.addElement(theRelation);
  }

  public ErrorSpecific bd_InsertListeRelations(String nomBase,Connexion myCnx, Statement st, String transaction){
  ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
  req="DELETE FROM RelationsOM  WHERE  (origineOM = "+this.idObjetMetier+") OR (destinationOM = "+this.idObjetMetier+")";

  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeRelations",""+this.idObjetMetier);
  if (myError.cause == -1) return myError;

   for (int i=0; i < this.ListeRelations.size(); i++)
   {
     Relation theRelation = (Relation)this.ListeRelations.elementAt(i);
     OM origineOM = theRelation.origineOM;
     OM destinationOM = theRelation.destinationOM;
     String req = "INSERT INTO RelationsOm (idSens, type, origineOM, cardinaliteOrigine, destinationOM, cardinaliteDestination, description, idTypeRelation) VALUES ('"+theRelation.idSens+"','"+theRelation.type+"',"+origineOM.idObjetMetier+",'"+theRelation.cardinaliteOrigine+"',"+destinationOM.idObjetMetier+",'"+theRelation.cardinaliteDestination+"','"+theRelation.description+"','"+theRelation.idTypeRelation+"')";
     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeRelations",""+this.idObjetMetier);
     if (myError.cause == -1) return myError;
   }

   return myError;

}

  public ErrorSpecific bd_InsertListeAttributs(String nomBase,Connexion myCnx, Statement st, String transaction){
  ErrorSpecific myError = new ErrorSpecific();
    ResultSet rs=null;
  req="DELETE Attribut WHERE omAttribut = "+idObjetMetier;
  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeAttributs",""+this.idObjetMetier);
  if (myError.cause == -1) return myError;


   for (int i=0; i < this.ListeAttributs.size(); i++)
   {
     Attribut theAttribut = (Attribut)this.ListeAttributs.elementAt(i);
     theAttribut.nom=theAttribut.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''");
     if (theAttribut.descModule != null)
     {
       theAttribut.descModule = theAttribut.descModule.replaceAll("\r", "").
           replaceAll("\u0092", "'").replaceAll("'", "''");
     }
     String req = "INSERT INTO Attribut   (omAttribut,nomAttribut, descAttribut, typeAttribut, nomTypeAttribut, ordre, sens) VALUES ("+this.idObjetMetier+",'"+theAttribut.nom+"','"+theAttribut.descModule+"','"+theAttribut.typeAttribut+"','"+theAttribut.nomtypeAttribut+"','"+theAttribut.ordre+"','"+theAttribut.sens+"')";
     myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeAttributs",""+this.idObjetMetier);

   }

   return myError;

}

  public ErrorSpecific bd_InsertListeRefSt(String nomBase,Connexion myCnx, Statement st, String transaction){
  ErrorSpecific myError = new ErrorSpecific();
  ResultSet rs=null;
  req="DELETE OmSt WHERE idOm="+idObjetMetier;
  myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeRefSt",""+this.idObjetMetier);
  if (myError.cause == -1) return myError;

   for (int i=0; i < this.ListeRefSt.size(); i++)
   {
     ST theST = (ST)this.ListeRefSt.elementAt(i);
      String req = "INSERT INTO OmSt   (idOm, idSt, isOrigine) VALUES ("+idObjetMetier+","+theST.idSt+","+theST.numStOrigine+")";
      myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_InsertListeRefSt",""+this.idObjetMetier);
      if (myError.cause == -1) return myError;
   }

   return myError;

}


  public void getListeRefSt(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs;

  req = "exec OM_SelectRefSt "+this.idObjetMetier;
  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {
             int id =  rs.getInt(1);
             int numStOrigine = rs.getInt(2);
             String nomSt = rs.getString(3);
             int idVersionSt = rs.getInt(4);
             ST theST = new ST(id,numStOrigine,nomSt,idVersionSt);
             this.ListeRefSt.addElement(theST);
        }

  }
   catch (SQLException s) { }
}

 public static void getListeOMShortCut(String nomBase,Connexion myCnx, Statement st){

   String req="SELECT     idObjetMetier, nomObjetMetier FROM   ObjetMetier WHERE     (isShortcut = 1)";
   ListeOM.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   OM theOM = null;
   try {
      while (rs.next()) {

        theOM = new OM(rs.getInt("idObjetMetier"));
        theOM.nomObjetMetier = rs.getString("nomObjetMetier");
        ListeOM.addElement(theOM);
      }
       } catch (SQLException s) {s.getMessage();}

 }

 public static void getListeOM(String nomBase,Connexion myCnx, Statement st){

   String req="EXEC LISTEOM_SelectObjetMetier";
   ListeOM.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   OM theOM = null;
   try {
      while (rs.next()) {
        int idFamille = rs.getInt(1);
        int id = rs.getInt(2);
        String nom = rs.getString(3);
        int typeEtatObjetMetier = rs.getInt(4);
        String nomFamille = rs.getString(5);
        String defObjetMetier = rs.getString("defObjetMetier");
        String nomProprietaire = rs.getString("nomService");

        theOM = new OM(id);
        theOM.idFamille = idFamille;
        theOM.nomObjetMetier = nom;
        theOM.typeEtatObjetMetier = typeEtatObjetMetier;
        theOM.Famille = nomFamille;
        theOM.Famille = nomFamille;
        if (defObjetMetier != null)
            theOM.defObjetMetier = defObjetMetier.replaceAll("\\r","").replaceAll("\\n","");
        else
            theOM.defObjetMetier = "";

        theOM.nomProprietaire = nomProprietaire;
        //theST.dump();
        ListeOM.addElement(theOM);
      }
       } catch (SQLException s) {s.getMessage();}

 }

 public static void getListeOMPhysiques(String nomBase,Connexion myCnx, Statement st){

   String req="SELECT     ObjetMetier.famObjetMetier, ObjetMetier.idObjetMetier, ObjetMetier.nomObjetMetier, ObjetMetier.typeEtatObjetMetier, FamilleOM.nomFamilleOM, ";
   req+="                   ObjetMetier.defObjetMetier, Service.nomService";
   req+=" FROM         ObjetMetier INNER JOIN";
   req+="                    FamilleOM ON ObjetMetier.famObjetMetier = FamilleOM.idFamilleOM INNER JOIN";
   req+="                    AppliIcone ON ObjetMetier.idAppliIcone = AppliIcone.id LEFT OUTER JOIN";
   req+="                    Service ON ObjetMetier.idProprietaire = Service.idService";
   req+=" WHERE     (ObjetMetier.typeOM = 1)";
   req+=" ORDER BY ObjetMetier.nomObjetMetier";

   ListeOM.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   OM theOM = null;
   try {
      while (rs.next()) {
        int idFamille = rs.getInt(1);
        int id = rs.getInt(2);
        String nom = rs.getString(3);
        int typeEtatObjetMetier = rs.getInt(4);
        String nomFamille = rs.getString(5);
        String defObjetMetier = rs.getString("defObjetMetier");
        String nomProprietaire = rs.getString("nomService");

        theOM = new OM(id);
        theOM.idFamille = idFamille;
        theOM.nomObjetMetier = nom;
        theOM.typeEtatObjetMetier = typeEtatObjetMetier;
        theOM.Famille = nomFamille;
        theOM.Famille = nomFamille;
        theOM.defObjetMetier = defObjetMetier.replaceAll("\\r","").replaceAll("\\n","");

        theOM.nomProprietaire = nomProprietaire;
        //theST.dump();
        ListeOM.addElement(theOM);
      }
       } catch (SQLException s) {s.getMessage();}

 }

 public static void getListeOMValide(String nomBase,Connexion myCnx, Statement st){

   String req="SELECT     ObjetMetier.famObjetMetier, ObjetMetier.idObjetMetier, ObjetMetier.nomObjetMetier, ObjetMetier.typeEtatObjetMetier, FamilleOM.nomFamilleOM, ";
          req+="            ObjetMetier.defObjetMetier, Service.nomService";
          req+=" FROM         ObjetMetier INNER JOIN";
          req+="             FamilleOM ON ObjetMetier.famObjetMetier = FamilleOM.idFamilleOM LEFT OUTER JOIN";
          req+="             Service ON ObjetMetier.idProprietaire = Service.idService";
          req+=" WHERE     (ObjetMetier.typeEtatObjetMetier = 3)";
          req+=" ORDER BY  ObjetMetier.nomObjetMetier, ObjetMetier.famObjetMetier";
   ListeOM.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   OM theOM = null;
   try {
      while (rs.next()) {
        int idFamille = rs.getInt(1);
        int id = rs.getInt(2);
        String nom = rs.getString(3);
        int typeEtatObjetMetier = rs.getInt(4);
        String nomFamille = rs.getString(5);
        String defObjetMetier = rs.getString("defObjetMetier");
        String nomProprietaire = rs.getString("nomService");

        theOM = new OM(id);
        theOM.idFamille = idFamille;
        theOM.nomObjetMetier = nom;
        theOM.typeEtatObjetMetier = typeEtatObjetMetier;
        theOM.Famille = nomFamille;
        theOM.Famille = nomFamille;
        if (defObjetMetier != null)
          theOM.defObjetMetier = defObjetMetier.replaceAll("\\r","").replaceAll("\\n","");
        else
            theOM.defObjetMetier = "";

        theOM.nomProprietaire = nomProprietaire;
        //theST.dump();
        ListeOM.addElement(theOM);
      }
       } catch (SQLException s) {s.getMessage();}

 }

  public static void getListeOMValideByFamille(String nomBase,Connexion myCnx, Statement st, String Famille){

   String req="SELECT     ObjetMetier.famObjetMetier, ObjetMetier.idObjetMetier, ObjetMetier.nomObjetMetier, ObjetMetier.typeEtatObjetMetier, FamilleOM.nomFamilleOM, ";
          req+="            ObjetMetier.defObjetMetier, Service.nomService";
          req+=" FROM         ObjetMetier INNER JOIN";
          req+="             FamilleOM ON ObjetMetier.famObjetMetier = FamilleOM.idFamilleOM LEFT OUTER JOIN";
          req+="             Service ON ObjetMetier.idProprietaire = Service.idService";
          req+=" WHERE     (ObjetMetier.typeEtatObjetMetier = 3) AND (FamilleOM.nomFamilleOM = '"+Famille+"')";
          req+=" ORDER BY  ObjetMetier.nomObjetMetier, ObjetMetier.famObjetMetier";
   ListeOM.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   OM theOM = null;
   try {
      while (rs.next()) {
        int idFamille = rs.getInt(1);
        int id = rs.getInt(2);
        String nom = rs.getString(3);
        int typeEtatObjetMetier = rs.getInt(4);
        String nomFamille = rs.getString(5);
        String defObjetMetier = rs.getString("defObjetMetier");
        String nomProprietaire = rs.getString("nomService");

        theOM = new OM(id);
        theOM.idFamille = idFamille;
        theOM.nomObjetMetier = nom;
        theOM.typeEtatObjetMetier = typeEtatObjetMetier;
        theOM.Famille = nomFamille;
        theOM.Famille = nomFamille;
        if (defObjetMetier != null)
          theOM.defObjetMetier = defObjetMetier.replaceAll("\\r","").replaceAll("\\n","");
        else
            defObjetMetier = "";

        theOM.nomProprietaire = nomProprietaire;
        //theST.dump();
        ListeOM.addElement(theOM);
      }
       } catch (SQLException s) {s.getMessage();}

 }
  
 public static void getListeOMPhysiqueValide(String nomBase,Connexion myCnx, Statement st){

   String req="SELECT     ObjetMetier.famObjetMetier, ObjetMetier.idObjetMetier, ObjetMetier.nomObjetMetier, ObjetMetier.typeEtatObjetMetier, FamilleOM.nomFamilleOM, ";
          req+="            ObjetMetier.defObjetMetier, Service.nomService";
          req+=" FROM         ObjetMetier INNER JOIN";
          req+="             FamilleOM ON ObjetMetier.famObjetMetier = FamilleOM.idFamilleOM LEFT OUTER JOIN";
          req+="             Service ON ObjetMetier.idProprietaire = Service.idService";
          req+=" WHERE     (ObjetMetier.typeEtatObjetMetier = 3) AND (FamilleOM.nomFamilleOM = 'Objets-Physiques')";
          req+=" ORDER BY  ObjetMetier.nomObjetMetier, ObjetMetier.famObjetMetier";
   ListeOM.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   OM theOM = null;
   try {
      while (rs.next()) {
        int idFamille = rs.getInt(1);
        int id = rs.getInt(2);
        String nom = rs.getString(3);
        int typeEtatObjetMetier = rs.getInt(4);
        String nomFamille = rs.getString(5);
        String defObjetMetier = rs.getString("defObjetMetier");
        String nomProprietaire = rs.getString("nomService");

        theOM = new OM(id);
        theOM.idFamille = idFamille;
        theOM.nomObjetMetier = nom;
        theOM.typeEtatObjetMetier = typeEtatObjetMetier;
        theOM.Famille = nomFamille;
        theOM.Famille = nomFamille;
        theOM.defObjetMetier = defObjetMetier.replaceAll("\\r","").replaceAll("\\n","");

        theOM.nomProprietaire = nomProprietaire;
        //theST.dump();
        ListeOM.addElement(theOM);
      }
       } catch (SQLException s) {s.getMessage();}

 }
 public static void getListePaquetage(String nomBase,Connexion myCnx, Statement st){

   String req="SELECT  nomFamille, nomPackage, idPackage FROM  packageOM";
   ListePaquetage.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   Paquetage thePaquetage = null;
   try {
      while (rs.next()) {
        thePaquetage = new Paquetage();
        thePaquetage.idFamille = rs.getInt("nomFamille");
        thePaquetage.nom = rs.getString("nomPackage");
        thePaquetage.id = rs.getInt("idPackage");

        //theST.dump();
        ListePaquetage.addElement(thePaquetage);
      }
       } catch (SQLException s) {s.getMessage();}

 }

 public static void getListeFamilleOM(String nomBase,Connexion myCnx, Statement st){

   String req="exec LISTEFAMILLEOM_SelectFamilleOM";
   ListeFamille.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   Famille theFamille = null;
   try {
      while (rs.next()) {
        theFamille = new Famille();
        theFamille.id = rs.getInt(1);
        theFamille.nomPackage = rs.getString(2);

        //theST.dump();
        ListeFamille.addElement(theFamille);
      }
       } catch (SQLException s) {s.getMessage();}

 }

 
  public static void setListeOM(Vector theListe){
   ListeOMInterface.removeAllElements();
    ListeOMInterface = theListe;

 }
  
 public static void setListeOM2(String theListe){
   ListeOMInterface.removeAllElements();
   if (theListe.length() ==0) return;

   theListe =theListe + " ";

   int Index_Blanc=theListe.indexOf(' ');

   while (Index_Blanc != -1)
   {
     String nomOM = theListe.substring (0,Index_Blanc);
     //alert ("nomOM="+nomOM);
     theListe = theListe.substring(Index_Blanc+1);
     Index_Blanc = theListe.indexOf(' ');

     int Index = nomOM.indexOf('_');
     nomOM = nomOM.substring(Index+1);
     ListeOMInterface.addElement(nomOM);

   }

 }

 public String getisSelected(){

   for (int i=0; i < ListeOMInterface.size(); i++)
   {
     String nomOM = (String)ListeOMInterface.elementAt(i);
     if (this.nomObjetMetier.equals(nomOM)) return "selected";
   }
   return "";
 }

 public static int nextId(String nomBase,Connexion myCnx, Statement st){

   int nextId=-1;

   String req="SELECT TOP (1) idObjetMetier FROM   ObjetMetier ORDER BY idObjetMetier DESC";
   ListeFamille.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   Famille theFamille = null;
   try {
      while (rs.next()) {
        nextId= rs.getInt("idObjetMetier") + 1;
      }
       } catch (SQLException s) {s.getMessage();}

   return nextId;
 }

 public String getisChecked(){

   for (int i=0; i < ListeOMInterface.size(); i++)
   {
       OM myOM = null;
       myOM = (OM)ListeOMInterface.elementAt(i);
     //int idOM = (String)ListeOMInterface.elementAt(i);
     //Connexion.trace("getisChecked--------"+i, "", ""+nomOM+"/"+this.nomObjetMetier);
     if (this.idObjetMetier == myOM.idObjetMetier) return "checked";
   }
   return "";
 }
 
  public String getisChecked2(){

   for (int i=0; i < ListeOMInterface.size(); i++)
   {
     String nomOM = (String)ListeOMInterface.elementAt(i);
     Connexion.trace("getisChecked--------"+i, "", ""+nomOM+"/"+this.nomObjetMetier);
     if (this.nomObjetMetier.equals(nomOM)) return "checked";
   }
   return "";
 }

 public static void getListeFamille(String nomBase,Connexion myCnx, Statement st){

   String req="SELECT  nomFamille, nomPackage, idPackage FROM  packageOM";
   ListeFamille.removeAllElements();

   ResultSet rs = myCnx.ExecReq(st, nomBase, req);
   Famille theFamille = null;
   try {
      while (rs.next()) {
        theFamille = new Famille();
        theFamille.id = rs.getInt("nomFamille");
        theFamille.nomPackage = rs.getString("nomPackage");
        theFamille.idPackage = rs.getInt("idPackage");

        //theST.dump();
        ListeFamille.addElement(theFamille);
      }
       } catch (SQLException s) {s.getMessage();}

 }

 public static int nbByState (String nomBase,Connexion myCnx, Statement st,String Etat){
   ResultSet rs=null;
   int nb = 0;

   String req = "SELECT     COUNT(*) AS nbOM, TypeEtat.nom2TypeEtat FROM  TypeEtat INNER JOIN   ObjetMetier ON TypeEtat.idTypeEtat = ObjetMetier.typeEtatObjetMetier GROUP BY TypeEtat.nom2TypeEtat HAVING      (TypeEtat.nom2TypeEtat = '"+Etat+"')";

   rs = myCnx.ExecReq(st, nomBase, req);
    try { rs.next();
           nb = rs.getInt(1);
} catch (SQLException s) {s.getMessage();}

   return nb;
  }
 
 public void  getNode (String nomBase,Connexion myCnx, Statement st){
   ResultSet rs=null;
   int nb = 0;

   String req = "SELECT     TypeAppli.nomTypeAppli, TypeAppli.formeTypeAppli";
 req+= " FROM         ObjetMetier INNER JOIN";
 req+= "                       TypeAppli ON ObjetMetier.idAppliIcone = TypeAppli.idTypeAppli";
   req+= " WHERE     (ObjetMetier.idObjetMetier = "+this.idObjetMetier+")";

   this.NodeOm = new Node(this.nomObjetMetier);
   this.NodeOm.idNode = this.idObjetMetier;
   rs = myCnx.ExecReq(st, nomBase, req);
    try { rs.next();
           
           this.NodeOm.forme = rs.getString("nomTypeAppli");
           this.NodeOm.icone = rs.getString("formeTypeAppli");
           this.NodeOm.couleur = "rouge";
} catch (SQLException s) {s.getMessage();}


  } 
 
 public void  getNode2 (String nomBase,Connexion myCnx, Statement st){
   ResultSet rs=null;
   int nb = 0;

   String req = "SELECT     ObjetMetier.nomObjetMetier, AppliIcone.nomTypeAppli, AppliIcone.icone, AppliIcone.couleur" ;
   req+= " FROM         ObjetMetier INNER JOIN" ;
   req+= "                       AppliIcone ON ObjetMetier.idAppliIcone = AppliIcone.id" ;
   req+= " WHERE     (ObjetMetier.idObjetMetier = "+this.idObjetMetier+")";

   this.NodeOm = new Node(this.nomObjetMetier);
   this.NodeOm.idNode = this.idObjetMetier;
   rs = myCnx.ExecReq(st, nomBase, req);
    try { rs.next();
           
           this.NodeOm.forme = rs.getString("nomTypeAppli");
           this.NodeOm.icone = rs.getString("icone");
           this.NodeOm.couleur = rs.getString("couleur");
} catch (SQLException s) {s.getMessage();}


  } 

public static String getAttributes(String nomBase,Connexion myCnx, Statement st){
    ResultSet rs = null;

    String req = "update declenceImportOM set nom='GO'";
    myCnx.ExecReq(st,nomBase,req);

    return "OK";
  }

  public  String getListeAttributsModifies(String nomBase,Connexion myCnx, Statement st, String nomAttribut){
    //myCnx.trace("---------","ListeOM",""+ListeOM);
    String ListeAttributModifies="";
    // ce sont les Attribut qui sont dans (Vector)ListeOM et pas en base
      String req="SELECT     idAttribut, nomAttribut, omAttribut, descAttribut";
          req+=" FROM         Attribut";
          req+=" WHERE     (omAttribut = "+this.idObjetMetier+") AND (nomAttribut = '"+nomAttribut+"')";
             
      //myCnx.trace("Avant---------","nb",""+nb);
      String descAttribut ="";
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try {
         while (rs.next()) {
            descAttribut = rs.getString("descAttribut");
         }
          } catch (SQLException s) {s.getMessage();}
//myCnx.trace ("2-------------","nomAttribut",""+descAttribut);      
    // tokeinsation de ListeOM
    for (int i=0; i < this.ListeAttributs.size(); i++) {
      Attribut theAttribut = (Attribut)this.ListeAttributs.elementAt(i);
      int nb=0;
//myCnx.trace ("3-------------"+i," :: theAttribut.nom",""+theAttribut.nom);
//myCnx.trace ("3-------------"+i," :: theAttribut.descModule",""+theAttribut.descModule);
      //myCnx.trace("---------","theAttribut.id",""+theAttribut.id);
      if (theAttribut.nom.equals(nomAttribut))
      {
          if (!theAttribut.descModule.equals(descAttribut))
          {
              ListeAttributModifies+="/ "+this.nomObjetMetier + "."+theAttribut.nom;
              //myCnx.trace ("4-------------"+i," :: ListeAttributModifies",""+ListeAttributModifies);
          }
      }
      //myCnx.trace("Apres---------","nb",""+nb);

      // token dans ListeOM ? (mieux vaut faire une requete en base)
      //   Oui: � rajouter dans listeOMAjoute

    }
    if (!ListeAttributModifies.equals("")) ListeAttributModifies = " :: Attributs Modifies: "+ListeAttributModifies;
    return ListeAttributModifies;
}

  public static String searchByNomPartiel(String nomBase,Connexion myCnx, Statement st, String nomOm){
    ResultSet rs;
    String ListeOm="";
    String req = "SELECT     nomObjetMetier, idObjetMetier ";
    req+= " FROM         ObjetMetier";
    req+= " WHERE     (nomObjetMetier LIKE N'%"+nomOm+"%')" ;
    req+= " ORDER BY idObjetMetier DESC";

    rs = myCnx.ExecReq(st, nomBase, req);
    try { while (rs.next()) {
      ListeOm+=rs.getString("nomObjetMetier")+";"+ rs.getString("idObjetMetier") + ",";
      }
    }
    catch (SQLException s) {s.getMessage();}

    return ListeOm;
  }
  
public  String getListeAttributsAjoutes(String nomBase,Connexion myCnx, Statement st){
    //myCnx.trace("---------","ListeOM",""+ListeOM);
    String ListeAttributAjoutes="";

    // ce sont les Attribut qui sont dans (Vector)ListeOM et pas en base

    // tokeinsation de ListeOM
    for (int i=0; i < this.ListeAttributs.size(); i++) {
      Attribut theAttribut = (Attribut)this.ListeAttributs.elementAt(i);
      int nb=0;

      //myCnx.trace("---------","theAttribut.id",""+theAttribut.id);
      String req="SELECT     idAttribut, nomAttribut, omAttribut";
          req+=" FROM         Attribut";
          req+=" WHERE     (omAttribut = "+this.idObjetMetier+") AND (nomAttribut = '"+theAttribut.nom.replaceAll("'","''")+"')";

      //myCnx.trace("Avant---------","nb",""+nb);
      ResultSet rs = myCnx.ExecReq(st, nomBase, req);
      try {
         while (rs.next()) {
           nb++;
           String nomAttribut = rs.getString("nomAttribut");
         }
          } catch (SQLException s) {s.getMessage();}

      //myCnx.trace("Apres---------","nb",""+nb);

      // token dans ListeOM ? (mieux vaut faire une requete en base)
      //   Oui: � rajouter dans listeOMAjoute
      if (nb == 0)
      {
        ListeAttributAjoutes+="/ "+theAttribut.nom;
      }
    }
    if (!ListeAttributAjoutes.equals("")) ListeAttributAjoutes = " :: Attributs Ajoutes: "+ListeAttributAjoutes;
    return ListeAttributAjoutes;
}

  public  String getListeAttributsSupprimes(String nomBase,Connexion myCnx, Statement st){

    String ListeAttributsSupprimes="";
    // ce sont les OM qui sont dans Interface.listeOM et pas dans ListeOM

    String req="SELECT     idAttribut, nomAttribut";
           req+=" FROM         Attribut";
           req+=" WHERE     (omAttribut = "+this.idObjetMetier+")";

    ResultSet rs = myCnx.ExecReq(st, nomBase, req);
    try {
       while (rs.next()) {
         int idAttribut= rs.getInt("idAttribut");
         String nomAttribut= rs.getString("nomAttribut");

         int nb=0;
         //myCnx.trace("---------","this.ListeAttributs.size()",""+this.ListeAttributs.size());
         for (int i=0; i < this.ListeAttributs.size(); i++) {
           Attribut theAttribut = (Attribut)this.ListeAttributs.elementAt(i);
           if (theAttribut.nom.equals(nomAttribut))
           {
             nb++;
           }
         }
         //myCnx.trace("---------","nomAttribut",""+nomAttribut+ ":: nb="+nb);
         if (nb == 0)
         {
           ListeAttributsSupprimes+="/ "+nomAttribut;
         }

       }
          } catch (SQLException s) {s.getMessage();}

          if (!ListeAttributsSupprimes.equals("")) ListeAttributsSupprimes = " :: Attributs Supprimes: "+ListeAttributsSupprimes;
    return ListeAttributsSupprimes;
}
public void getListeInterfaces(String nomBase,Connexion myCnx, Statement st ){
  ResultSet rs;


  req = "SELECT     idInter_OM, interInter_OM, omInter_OM";
  req+="    FROM         Inter_OM";
  req+=" WHERE     (omInter_OM = "+this.idObjetMetier+")";

  rs = myCnx.ExecReq(st, myCnx.nomBase, req);

  try {
  while(rs.next())
  {

             Interface theInterface = new Interface(rs.getInt("interInter_OM"));
             this.ListeInterfaces.addElement(theInterface);
        }

  }
   catch (SQLException s) { }
}
public void getListeAttributs(String nomBase,Connexion myCnx, Statement st ){
ResultSet rs;


req = "EXEC OM_SelectLesAttribut "+this.idObjetMetier;
rs = myCnx.ExecReq(st, myCnx.nomBase, req);

try {
while(rs.next())
{

           Attribut theAttribut = new Attribut();
           theAttribut.id = rs.getInt(1);
           theAttribut.nom = rs.getString(2);
           theAttribut.descModule = rs.getString(3);
           theAttribut.typeAttribut = rs.getInt("typeAttribut");
           theAttribut.nomtypeAttribut = rs.getString("nomtypeAttribut");
           theAttribut.ordre = rs.getInt("ordre");
           theAttribut.sens = rs.getInt("sens");
           this.ListeAttributs.addElement(theAttribut);
      }

}
 catch (SQLException s) { }
}

  void antiChevauchement (OM theOM){
    int lg_chevauchement = 0;
    // horizontal,
    if (this.y == theOM.y)
    {
      //il faut  avancer le libell�
      if ((theOM.x >= this.x) && (theOM.x <= this.x +  Utils.getStringWidth(this.Label,this.Style,this.Taille, this.Epaisseur) ))
      {
        lg_chevauchement =  this.x + Utils.getStringWidth(this.Label,this.Style,this.Taille, this.Epaisseur) - theOM.x;
        this.y+=marge_y;
      }
      //il faut  reculer le libell�
      if ((theOM.x <= this.x) && (this.x <= theOM.x + this.Label.length()))
      {
        lg_chevauchement =  this.x + Utils.getStringWidth(this.Label,this.Style,this.Taille, this.Epaisseur) - theOM.x;
        this.y+=marge_y;
      }

    }


  }

  public void draw( Vector ListeOM, int index){

    // eviter les chevauchements horizontaux de libell�s.

    for (int i = 0 ; i < ListeOM.size() ; i++) {
      if (i == index) continue;
      OM theOM = (OM)ListeOM.elementAt(i);
      this.antiChevauchement(theOM);

    }

    //screen2D.setFont(new Font(this.Style, this.Epaisseur, this.Taille));
    //screen2D.setColor(this.CouleurTexte);
    //screen2D.drawString(this.Label,this.x,this.y);

}

  public void getAllFromBd(
      String nomBase,
      Connexion myCnx,
      Statement st ){
    ResultSet rs=null;

    req = "SELECT     ObjetMetier.idObjetMetier, ObjetMetier.nomObjetMetier, ObjetMetier.defObjetMetier, ObjetMetier.package, ObjetMetier.famObjetMetier, FamilleOM.nomFamilleOM, ";
    req+="                   ObjetMetier.lienFichierUml, ObjetMetier.lienWsdl, packageOM.nomPackage, ObjetMetier.typeEtatObjetMetier, ObjetMetier.Criticite, ";
    req+="                   ObjetMetier.idAppliIcone, ObjetMetier.idObjetMetier, ObjetMetier.idProprietaire,  Service.nomService, ObjetMetier.typeOM, ObjetMetier.dateImport, ObjetMetier.diagnostic, ObjetMetier.isShortcut, ObjetMetier.idStOrigine , ST.nomSt";
    req+=" FROM         ObjetMetier INNER JOIN";
    req+="                   FamilleOM ON ObjetMetier.famObjetMetier = FamilleOM.idFamilleOM INNER JOIN";
    req+="                   packageOM ON ObjetMetier.package = packageOM.idPackage LEFT OUTER JOIN";
    req+="                   Service ON ObjetMetier.idProprietaire = Service.idService";
    req+="                   LEFT OUTER JOIN  St ON ObjetMetier.idStOrigine = St.idSt";


    if (this.TypeCreation.equals("idOm"))
      req+="  WHERE     (ObjetMetier.idObjetMetier ="+this.idObjetMetier+")";
    else
      req+="  WHERE     (ObjetMetier.nomObjetMetier ='"+this.nomObjetMetier+"')";


    rs = Connexion.ExecReq(st, nomBase, req);

    //if (rs == null) return;
    try {
      while (rs.next()) {
        this.idObjetMetier= rs.getInt("idObjetMetier");
        this.nomObjetMetier= rs.getString("nomObjetMetier");
        this.defObjetMetier= rs.getString("defObjetMetier");
        this.idPackage= rs.getInt("package");
        this.idFamille= rs.getInt("famObjetMetier");
        this.Famille= rs.getString("nomFamilleOM");
        this.LienUml=rs.getString("lienFichierUml");
        this.LienWsdl=rs.getString("lienWsdl");
        this.Package= rs.getString("nomPackage");
        this.typeEtatObjetMetier= rs.getInt("typeEtatObjetMetier");
        this.Criticite= rs.getString("Criticite");;
        this.idAppliIcone= rs.getInt("idAppliIcone");
        this.idObjetMetier= rs.getInt("idObjetMetier");
        this.idProprietaire= rs.getInt("idProprietaire");
        this.nomProprietaire= rs.getString("nomService");
        this.typeOM= rs.getInt("typeOM");
        this.dateImport= rs.getString("dateImport");
        if (this.dateImport == null) this.dateImport = "";
        this.diagnostic= rs.getString("diagnostic");
        if (this.diagnostic == null) this.diagnostic = "";
        this.isShortCut= rs.getInt("isShortCut");
        this.idStOrigine = rs.getInt("idStOrigine");
        this.nomStOrigine= rs.getString("nomSt");
        if (this.nomStOrigine == null) this.nomStOrigine = "";        

        }

    } catch (SQLException s) {s.getMessage();}


    if (this.action.equals("valid")) this.typeEtatObjetMetier = 3;
    if (this.nomProprietaire == null) this.nomProprietaire="";

}

  public String bd_enregGraph(String nomBase,Connexion myCnx,Statement st, transaction  theTransaction, String nodes){
    int numVersionSt;
    String theVersion = "";
    int idOm=0;int idOtherOm = 0;
    int idGraph = 0;
    int j=0; int k=0;

    ResultSet rs;

    //Suppression des GraphSt �ventuels pour ce Graph avant Cr�ation
    req = "delete FROM  GraphOM  WHERE idOMRef = "+this.idObjetMetier;

    if (myCnx.ExecUpdate(st,nomBase,req,true,theTransaction.nom) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

//out.println(nodes+"<br><br>");
    String nom = "";
//int num = 0;
    int abscisse, ordonnee;
    String str=""; String str1=""; String str2="";
    int idSt = 0;

    for (StringTokenizer t = new StringTokenizer(nodes, "$"); t.hasMoreTokens();) {

            str = t.nextToken(); // format de str : [nomOm] v[numVersionSt]*[abscisse]*[ordonn�e]
            //myCnx.trace(nomBase,"********************str=",str);
            k = str.indexOf('*');
            str1 = str.substring(0,k);
            //c1.trace(base,"********************str1=",str1);
            str2 = str.substring(k+1);
            //c1.trace(base,"********************str2=",str2);
            //Traitements sur str1
            j = -1;
            //nom = str1.substring(0,j-1);

            if (j == -1)
            {
              nom = str1;
            }
            else
            {
              nom = str1.substring(0,j-1);
              //c1.trace(base,"********************nom=",nom);
            }


            //num = Integer.parseInt(str1.substring(j+1));
            //Traitements sur str2 : abscisse*ordonnee
            //myCnx.trace(nomBase,"********************str2=",""+str2);
            j = str2.indexOf('*');
            //myCnx.trace(nomBase,"********************j=",""+j);
            abscisse = Integer.parseInt(str2.substring(0,j));
            //myCnx.trace(nomBase,"********************abscisse=",""+abscisse);
            ordonnee = Integer.parseInt(str2.substring(j+1));
            //myCnx.trace(nomBase,"********************ordonnee=",""+ordonnee);

            //R�cup�ration de l'idOm
            req = "SELECT     idObjetMetier FROM  ObjetMetier WHERE nomObjetMetier ='"+nom+"'";
            rs = myCnx.ExecReq(st, nomBase, req);
            try { rs.next(); idOtherOm = Integer.parseInt(rs.getString(1)); } catch (SQLException s) {s.getMessage();}
            //Cr�ation des GraphSt
            req = "INSERT GraphOM (idOMRef, idOM, x, y) VALUES ("+this.idObjetMetier+", "+idOtherOm+", "+abscisse+", "+ordonnee+")";

            if (myCnx.ExecUpdate(st,nomBase,req,true,theTransaction.nom) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

} //end for

          return "OK";
}

 public String bd_enregGraphCirculationOM(String nomBase,Connexion myCnx,Statement st, transaction  theTransaction, String nodes){
        ResultSet rs;
          int numVersionSt;
          String theVersion = "";
          int idOtherOm = 0;
          int idGraph = 0;
          int j=0; int k=0;
          int idVersionSt = -1;

//c1.trace ("##EnregGraph","idOm",""+idOm);

// la versionSt a-t-elle d�j� un graph ?
          req = "delete FROM  GraphCirculationOM  WHERE idOm  = "+this.idObjetMetier;
          rs = myCnx.ExecReq(st, nomBase, req);

//R�cup�ration des nodes et cr�ation des GraphSt

//out.println(nodes+"<br><br>");
          String nom = "";
//int num = 0;
          int abscisse, ordonnee;
          String str=""; String str1=""; String str2="";
          int idSt = 0;
          //myCnx.trace(nomBase,"<<<<<<<<<<<<<<<<<<<<<<<nodes=",nodes);
          for (StringTokenizer t = new StringTokenizer(nodes, "$"); t.hasMoreTokens();) {

                  str = t.nextToken(); // format de str : [nomOm] v[numVersionSt]*[abscisse]*[ordonn�e]
                  //myCnx.trace(nomBase,"********************str=",str);
                  k = str.indexOf('*');
                  str1 = str.substring(0,k);
                  //c1.trace(base,"********************str1=",str1);
                  str2 = str.substring(k+1);
                  //c1.trace(base,"********************str2=",str2);
                  //Traitements sur str1
                  j = -1;
                  //nom = str1.substring(0,j-1);

                  if (j == -1)
                  {
                    nom = str1;
                  }
                  else
                  {
                    nom = str1.substring(0,j-1);
                    //c1.trace(base,"********************nom=",nom);
                  }


                  //num = Integer.parseInt(str1.substring(j+1));
                  //Traitements sur str2 : abscisse*ordonnee
                  //myCnx.trace(nomBase,"********************str2=",""+str2);
                  j = str2.indexOf('*');
                  //myCnx.trace(nomBase,"********************j=",""+j);
                  abscisse = Integer.parseInt(str2.substring(0,j));
                  //myCnx.trace(nomBase,"********************abscisse=",""+abscisse);
                  ordonnee = Integer.parseInt(str2.substring(j+1));
                  //myCnx.trace(nomBase,"********************ordonnee=",""+ordonnee);

                  //R�cup�ration de l'idOm
                  req = "SELECT    idVersionSt FROM   ListeST WHERE nomSt = '"+nom+"'";
                  rs = myCnx.ExecReq(st, nomBase, req);
                  try { rs.next(); idVersionSt = Integer.parseInt(rs.getString(1)); } catch (SQLException s) {s.getMessage();}
                  //Cr�ation des GraphSt
                  req = "INSERT GraphCirculationOM (idOM, idVersionSt, x, y) VALUES ("+this.idObjetMetier+", "+idVersionSt+", "+abscisse+", "+ordonnee+")";

                  if (myCnx.ExecUpdate(st,nomBase,req,true,theTransaction.nom) == -1){myCnx.trace(nomBase,"*** ERREUR *** req",req);return req;}

} //end for
                return "OK";
}
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("idObjetMetier="+this.idObjetMetier);
    System.out.println("nomObjetMetier="+this.nomObjetMetier);
    System.out.println("typeEtatObjetMetier="+this.typeEtatObjetMetier);
    System.out.println("Famille="+this.Famille);
    System.out.println("idFamille="+this.idFamille);
    System.out.println("Package="+this.Package);
    System.out.println("idPackage="+this.idPackage);
    System.out.println("idAppliIcone="+this.idAppliIcone);
    System.out.println("Criticite="+this.Criticite);
    System.out.println("LienObjetMetier="+this.LienObjetMetier);
    System.out.println("defObjetMetier="+this.defObjetMetier);
    System.out.println("idProprietaire="+this.idProprietaire);
    System.out.println("nomProprietaire="+this.nomProprietaire);
    System.out.println("nomProprietaire="+this.dateImport);
    System.out.println("nomProprietaire="+this.diagnostic);
    for (int i=0; i < this.ListeAttributs.size(); i++){
      Attribut theAttribut = (Attribut)this.ListeAttributs.elementAt(i);
      //theAttribut.dump();
    }

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

    //setListeOM("ECONOMIE-FINANCE_SMP");
    int idOm= 89923;
    OM theOM = new OM(idOm);
    theOM.getAllFromBd(myCnx.nomBase,myCnx,st);
    theOM.getListeAttributsModifies(myCnx.nomBase,myCnx,  st, "KPI IPOD");
    //theOM.getListeNodes(myCnx.nomBase,myCnx,st);
    //theOM.dump();
    System.out.println("param_nodes= "+theOM.param_nodes);

    //System.out.println("selected= "+theOM.getisSelected());

    //theOM.getListeRelations(myCnx.nomBase,myCnx,st);
    //theOM.getListeNodes(myCnx.nomBase,myCnx,st);

    //System.out.println(theOM.param_nodes);
    //System.out.println(theOM.param_edges);

    myCnx.Unconnect(st);
  }
}

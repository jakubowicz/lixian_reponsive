package Graphe; 

import General.Utils;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import accesbase.Connexion;
import ST.Interface;
import ST.ST;
import java.util.Vector;

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
public class Node extends Forme {
  public int idNode; //idSt
  public int idCentre; //idVersionSt
   public int idGraphe=0; //idVersionSt

  public String nomGraphe;
  public String nomNode;

  public String centre;
  public int idLien; //
  public String param_nodes;
  public int idEmplacement = -1;

  public Node(String nomNode) {
    this.nomNode=nomNode;
    this.forme="Application";
    this.couleur="rouge";
    this.icone="images/Inconnue.png";
  }

  public Node(String nomNode, int idNode,int idCentre,String nomGraphe,String couleur, String forme,String centre,int idLien) {
    this.nomNode=nomNode;
    this.idNode=idNode;
    this.idCentre=idCentre;
    this.nomGraphe=nomGraphe;
    this.couleur=couleur;
    this.forme=forme;
    this.centre=centre;
    this.idLien=idLien;
  }

  public Node(int idNode,int idGraphe,String nomGraphe, int x, int y, String nom) {
  this.idNode=idNode;
  this.idCentre=-1;
  this.idGraphe=idGraphe;
  this.nomGraphe=nomGraphe;
  this.x=x;
  this.y=y;
  this.nomNode=nom;
}

public Node(int idNode,int idGraphe,String nomGraphe, int x, int y, String nom,String couleur, String forme) {
  this.idNode=idNode;
  this.idCentre=-1;
  this.idGraphe=idGraphe;
  this.nomGraphe=nomGraphe;
  this.x=x;
  this.y=y;
  this.nomNode=nom;
  this.couleur=couleur;
  this.forme=forme;

  this.param_nodes=nom+"!"+idNode+"!"+forme+"!"+couleur+"!"+x+"!"+y+"!"+"rien"+"!"+idNode+",";
}

  public void getGraph(String nomBase,Connexion myCnx,Statement st ){
    ResultSet rs;
    String req = "SELECT idGraph FROM Graph WHERE centreGraph = "+this.idCentre;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);
    try {rs.next(); this.idGraphe = Integer.parseInt(rs.getString(1)); } catch (SQLException s) {s.getMessage(); }
  }

  public void getListeNode(String nomBase,Connexion myCnx,Statement st )
  {
    ResultSet rs;
    int x2, y2;
    int idNode2;
    Node Node2;
    String nom;

    String req = "SELECT stGraphSt,abscisseGraphSt, ordonneeGraphSt, St.nomSt  FROM "+this.nomGraphe+" INNER JOIN   St ON GraphSt.stGraphSt = St.idSt WHERE graphGraphSt = "+this.idGraphe;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        idNode2=rs.getInt("stGraphSt");
        x2= rs.getInt("abscisseGraphSt");;
        y2= rs.getInt("ordonneeGraphSt");;
        nom = rs.getString("nomSt");;
        Node2 = new Node(idNode2,this.idGraphe,this.nomGraphe,x2,y2,nom);

        }

    } catch (SQLException s) {s.getMessage();}


}
  public void getXYFromBd(String nomBase,Connexion myCnx,Statement st )
  {
    ResultSet rs;

    String req = "SELECT abscisseGraphSt, ordonneeGraphSt FROM "+this.nomGraphe+" WHERE graphGraphSt = "+this.idGraphe+" AND stGraphSt = "+this.idNode;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        this.x= rs.getInt("abscisseGraphSt");;
        this.y= rs.getInt("ordonneeGraphSt");;
        }

    } catch (SQLException s) {s.getMessage();}
    
    if (this.x == 0) this.x = 20;
    if (this.y == 0) this.y = 20;


}
  
  public void getXYFromGraphe(String nomBase,Connexion myCnx,Statement st )
  {
    ResultSet rs;

    String req = "select x,y from GrapMacroSt where idSt="+this.idNode+ " and idMacroSt="+this.idGraphe;
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        this.x= rs.getInt("x");;
        this.y= rs.getInt("y");;
        }

    } catch (SQLException s) {s.getMessage();}


}  
  
  public void getXYFromGrapheOM(String nomBase,Connexion myCnx,Statement st )
  {
    ResultSet rs;

    String req = "SELECT     GraphOM.x, GraphOM.y" ;
    req+= " FROM         ObjetMetier INNER JOIN" ;
    req+= "                       GraphOM ON ObjetMetier.idObjetMetier = GraphOM.idOM" ;
    req+= " WHERE     (GraphOM.idOMRef = "+this.idGraphe+") AND (GraphOM.idOM = "+this.idNode+")";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        this.x= rs.getInt("x");;
        this.y= rs.getInt("y");;
        }

    } catch (SQLException s) {s.getMessage();}


}  
  
  public void getXYFromGrapheOMCirculation(String nomBase,Connexion myCnx,Statement st )
  {
    ResultSet rs;

    String req = "SELECT     GraphCirculationOm.x, GraphCirculationOm.y";
    req+= " FROM         GraphCirculationOm INNER JOIN" ;
    req+= "                       VersionSt ON GraphCirculationOm.idVersionSt = VersionSt.idVersionSt INNER JOIN" ;
    req+= "                       St ON VersionSt.stVersionSt = St.idSt" ;
    req+= " WHERE     (GraphCirculationOm.idOm = "+this.idGraphe+") AND (GraphCirculationOm.idVersionSt = "+this.idNode+")";
    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        this.x= rs.getInt("x");;
        this.y= rs.getInt("y");;
        }

    } catch (SQLException s) {s.getMessage();}


}    

  public void getIcone(String nomBase,Connexion myCnx,Statement st )
  {
    ResultSet rs;

    String req = "SELECT     TypeAppli.formeTypeAppli AS icone";
    req+=" FROM         St INNER JOIN";
    req+="                       TypeAppli ON St.typeAppliSt = TypeAppli.idTypeAppli INNER JOIN";
    req+="                       VersionSt ON St.idSt = VersionSt.stVersionSt";
    req+=" WHERE    St.idSt =" +this.idNode;

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        this.icone= rs.getString("icone");;
        }
      
      if ((this.icone == null) ||  (this.icone.equals("")))
          this.icone = "images/Logos/iconeInconnu.gif";

    } catch (SQLException s) {s.getMessage();}


}
  
  public void getIcone2(String nomBase,Connexion myCnx,Statement st )
  {
    ResultSet rs;

    String req = "SELECT     icone";
    req+="    FROM         AppliIcone";
    req+=" WHERE     (formeTypeAppli = '"+this.forme+"') AND (couleur = '"+this.couleur+"')";

    rs = myCnx.ExecReq(st, myCnx.nomBase, req);

    try {
      while (rs.next()) {
        this.icone= rs.getString("icone");;
        }

    } catch (SQLException s) {s.getMessage();}


}
  
  public String buildParamNode(String Liste, int end){
    if ((Liste.indexOf(","+this.nomNode+"!") > 0) || (Liste.indexOf(this.nomNode+"!") ==0)) return "";
    String ParamNode="";

    if (end != 0) ParamNode+=",";

    ParamNode+=this.nomNode;
    ParamNode+="!";

    ParamNode+=this.idNode;
    ParamNode+="!";

    ParamNode+=this.forme;
    ParamNode+="!";

    ParamNode+=this.couleur;
    ParamNode+="!";

    ParamNode+=this.x;
    ParamNode+="!";

    ParamNode+=this.y;
    ParamNode+="!";

    ParamNode+=this.centre;
    ParamNode+="!";

    ParamNode+=this.idLien;

    return ParamNode;
  }
  public void dump(){
    if (Connexion.traceOn.equals("no")) return;

    System.out.println("==================================================");
    System.out.println("idNode="+this.idNode);
    System.out.println("idCentre="+this.idCentre);
    System.out.println("idGraphe="+this.idGraphe);
    System.out.println("x="+this.x);
    System.out.println("y="+this.y);
    System.out.println("nomGraphe="+this.nomGraphe);
    System.out.println("nomNode="+this.nomNode);
    System.out.println("couleur="+this.couleur);
    System.out.println("forme="+this.forme);
    System.out.println("x="+this.x);
    System.out.println("y="+this.y);
    System.out.println("idLien="+this.idLien);
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
    Node theNode = new Node("xxxx",1115,1388,"GraphSt","rouge","Application","centre",1);

    theNode.getXYFromBd(myCnx.nomBase,myCnx,st);
    //theNode.dump();

    myCnx.Unconnect(st);
  }
}

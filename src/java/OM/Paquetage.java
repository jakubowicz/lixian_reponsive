package OM; 

import accesbase.Connexion;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ST.ST;
import Graphe.Node;
import accesbase.ErrorSpecific;
import java.util.StringTokenizer;

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
public class Paquetage {
  public int id=-1;
  public String nom="";
  public String description="";
  public int idFamille=-1;
  public String nodes = "";

  private String req="";

  public Paquetage() {
  }

  public void getAllFromBd(String nomBase,Connexion myCnx, Statement st ){
     ResultSet rs = null;
     req= "SELECT     idPackage, nomPackage, descPackage, nomFamille AS idFamille";
     req+="    FROM         packageOM";
     req+=" WHERE     (idPackage = "+this.id+")";


     rs = myCnx.ExecReq(st, myCnx.nomBase, req);
     try { rs.next();

       this.id = rs.getInt("idPackage");
       this.nom = rs.getString("nomPackage");
       this.description = rs.getString("descPackage");
       this.idFamille = rs.getInt("idFamille");

       } catch (SQLException s) {s.getMessage(); }
  }

  public void deleteOmFromGraph(String nomBase,Connexion myCnx, Statement st ){
    // Il s'agit de detruire les objets du graphe qui ne sont pas dans le package
    req="delete";
    req+=" FROM         GraphPackageOM";
    req+=" WHERE idOM not in";
    req+=" (";

    req+=" SELECT     ObjetMetier.idObjetMetier";
    req+=" FROM         packageOM INNER JOIN";
    req+="                       ObjetMetier ON packageOM.idPackage = ObjetMetier.package";
    req+=" WHERE     (packageOM.idPackage = "+this.id+")";
    req+=" )";
    req+=" AND     (idPackage = "+this.id+")";

    myCnx.ExecReq(st, nomBase, req);

  }

  public String bd_EnregGraph(String nomBase,Connexion myCnx, Statement st, String transaction){

    String nomOm;
    int numVersionSt;
    String theVersion = "";
    int idOm=0;int idOtherOm = 0;
    int idGraph = 0;
    int j=0; int k=0;

    ResultSet rs=null;


// la versionSt a-t-elle d�j� un graph ?
    req = "delete FROM  GraphPackageOM  WHERE idPackage = "+this.id;
    rs = myCnx.ExecReq(st, nomBase, req);

//R�cup�ration des nodes et cr�ation des GraphSt

//out.println(nodes+"<br><br>");
    String nom = "";
//int num = 0;
    int abscisse, ordonnee;
    String str=""; String str1=""; String str2="";
    String idObjetMetier = "";
   // myCnx.trace(nomBase,"<<<<<<<<<<<<<<<<<<<<<<<nodes=",this.nodes);
    for (StringTokenizer t = new StringTokenizer(this.nodes, "$"); t.hasMoreTokens();) {

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
            //req = "SELECT     idSt FROM    St WHERE  nomSt = '"+nom+"'";
            //rs = myCnx.ExecReq(st, nomBase, req);
            //try { rs.next(); idSt = Integer.parseInt(rs.getString(1)); } catch (SQLException s) {s.getMessage();}

            idObjetMetier = nom;
            //Cr�ation des GraphSt
            req = "INSERT GraphPackageOM (idPackage, idOM, x, y) VALUES ("+this.id+", "+idObjetMetier+", "+abscisse+", "+ordonnee+")";
            if (idObjetMetier != null && !idObjetMetier.equals(""))
            {
              if (myCnx.ExecUpdate(st, nomBase, req, true, transaction) == -1) {
                myCnx.trace(nomBase, "*** ERREUR *** req", req);
                return req;
              }
            }
          } //end for
          return "OK";
  }

  public String getListeOmInGraph(String nomBase,Connexion myCnx, Statement st, Statement st2 ){
    ResultSet rs;
    String param_nodes = "";
    req = "SELECT     ObjetMetier.nomObjetMetier,GraphPackageOM.idOM,  GraphPackageOM.x, GraphPackageOM.y,  ";
    req+="                  AppliIcone.formeTypeAppli, AppliIcone.couleur";
    req+=" FROM         GraphPackageOM INNER JOIN";
    req+="                   ObjetMetier ON GraphPackageOM.idOM = ObjetMetier.idObjetMetier INNER JOIN";
    req+="                   AppliIcone ON ObjetMetier.idAppliIcone = AppliIcone.id";
    req+=" WHERE     (GraphPackageOM.idPackage = "+this.id+")";

    rs = myCnx.ExecReq(st, nomBase, req);
    try { while(rs.next()) {

        Node theNode = new Node(rs.getString("nomObjetMetier"));
        theNode.idNode = rs.getInt("idOM");
        theNode.x = rs.getInt("x");
        theNode.y = rs.getInt("y");
        theNode.forme = rs.getString("formeTypeAppli");
        theNode.couleur = rs.getString("couleur");

            //param_nodes += nomSt + "/" + theST.TypeApplication + "/" + theST.couleur + "/" + x + "/" + y + "/" + "rien" + "/" + theST.idVersionSt + ",";
        param_nodes += theNode.nomNode + "!" +theNode.idNode + "!" + theNode.forme + "!" + theNode.couleur + "!" + theNode.x + "!" + theNode.y + "!" + "rien" + "!" + theNode.idNode + ",";



        } }catch (Exception e){};

      return param_nodes;
  }

  public String getListeOmNotInGraph(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    String param_nodes = "";

    req=" SELECT     ObjetMetier.nomObjetMetier, ObjetMetier.idObjetMetier, AppliIcone.formeTypeAppli, AppliIcone.couleur";
    req+="     FROM         ObjetMetier INNER JOIN";
    req+="                   AppliIcone ON ObjetMetier.idAppliIcone = AppliIcone.id";
    req+=" WHERE     (ObjetMetier.package = "+this.id+")";

    req+=" AND (ObjetMetier.idObjetMetier NOT IN";
    req+="                       (SELECT     idOM";
    req+="                         FROM          GraphPackageOM";
    req+="                         WHERE      (idPackage = "+this.id+"))) ";


    rs = myCnx.ExecReq(st, nomBase, req);
    try { while(rs.next()) {

        Node theNode = new Node(rs.getString("nomObjetMetier"));
        theNode.idNode = rs.getInt("idObjetMetier");
        theNode.x = 10;
        theNode.y = 10;
        theNode.forme = rs.getString("formeTypeAppli");
        theNode.couleur = rs.getString("couleur");

            //param_nodes += nomSt + "/" + theST.TypeApplication + "/" + theST.couleur + "/" + x + "/" + y + "/" + "rien" + "/" + theST.idVersionSt + ",";
        param_nodes += theNode.nomNode + "!" +theNode.idNode + "!" + theNode.forme + "!" + theNode.couleur + "!" + theNode.x + "!" + theNode.y + "!" + "rien" + "!" + theNode.idNode + ",";



        } }catch (Exception e){};

    return param_nodes;
  }

  public String getListeOmNotInGraph2(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    String param_nodes = "";

    req=" SELECT DISTINCT ObjetMetier.nomObjetMetier, ObjetMetier.idObjetMetier, AppliIcone.formeTypeAppli, AppliIcone.couleur";
    req+="    FROM         RelationsOM INNER JOIN";
    req+="                   ObjetMetier ON RelationsOM.origineOM = ObjetMetier.idObjetMetier INNER JOIN";
    req+="                   AppliIcone ON ObjetMetier.idAppliIcone = AppliIcone.id INNER JOIN";
    req+="                   ObjetMetier AS ObjetMetier_1 ON RelationsOM.destinationOM = ObjetMetier_1.idObjetMetier";
    req+=" WHERE     (ObjetMetier.package = "+this.id+") AND (ObjetMetier.idObjetMetier NOT IN";
    req+="                       (SELECT     idOM";
    req+="                         FROM          GraphPackageOM";
    req+="                         WHERE      (idPackage = "+this.id+"))) AND (ObjetMetier_1.package = "+this.id+")";

    req+="  UNION";

    req+=" SELECT DISTINCT ObjetMetier.nomObjetMetier, ObjetMetier.idObjetMetier, AppliIcone.formeTypeAppli, AppliIcone.couleur";
    req+=" FROM         RelationsOM INNER JOIN";
    req+="                   ObjetMetier ON RelationsOM.destinationOM = ObjetMetier.idObjetMetier INNER JOIN";
    req+="                   AppliIcone ON ObjetMetier.idAppliIcone = AppliIcone.id INNER JOIN";
    req+="                   ObjetMetier AS ObjetMetier_1 ON RelationsOM.origineOM = ObjetMetier_1.idObjetMetier";
    req+=" WHERE     (ObjetMetier.package = "+this.id+") AND (ObjetMetier.idObjetMetier NOT IN";
    req+="                       (SELECT     idOM";
    req+="                         FROM          GraphPackageOM";
    req+="                         WHERE      (idPackage = "+this.id+"))) AND (ObjetMetier_1.package = "+this.id+")";


    rs = myCnx.ExecReq(st, nomBase, req);
    try { while(rs.next()) {

        Node theNode = new Node(rs.getString("nomObjetMetier"));
        theNode.idNode = rs.getInt("idObjetMetier");
        theNode.x = 10;
        theNode.y = 10;
        theNode.forme = rs.getString("formeTypeAppli");
        theNode.couleur = rs.getString("couleur");

            //param_nodes += nomSt + "/" + theST.TypeApplication + "/" + theST.couleur + "/" + x + "/" + y + "/" + "rien" + "/" + theST.idVersionSt + ",";
        param_nodes += theNode.nomNode + "!" +theNode.idNode + "!" + theNode.forme + "!" + theNode.couleur + "!" + theNode.x + "!" + theNode.y + "!" + "rien" + "!" + theNode.idNode + ",";



        } }catch (Exception e){};

    return param_nodes;
  }

  public String getListeRelations(String nomBase,Connexion myCnx, Statement st ){
    ResultSet rs;
    String param_edges = "";
    req = "SELECT     RelationsOM.sens, ObjetMetier.nomObjetMetier AS nomObjetMetierOrigine, ObjetMetier_1.nomObjetMetier AS nomObjetMetierDestination, cardinaliteOrigine,description,  cardinaliteDestination";
    req+="    FROM         RelationsOM INNER JOIN";
    req+="                   ObjetMetier ON RelationsOM.origineOM = ObjetMetier.idObjetMetier INNER JOIN";
    req+="                   ObjetMetier AS ObjetMetier_1 ON RelationsOM.destinationOM = ObjetMetier_1.idObjetMetier";
    req+=" WHERE     (ObjetMetier.package = "+this.id+") OR";
    req+="                   (ObjetMetier_1.package = "+this.id+")";

    rs = myCnx.ExecReq(st, nomBase, req);
    try { while (rs.next()) {
            String sensInterface = rs.getString("sens"); //sensInterface
            String nomOrigine = rs.getString("nomObjetMetierOrigine"); //nomExtremite-version
            String nomExtremite = rs.getString("nomObjetMetierDestination"); //nomExtremite-version
            String ListeOM = ""+rs.getString("cardinaliteOrigine")+"|"; //nom de la relation de cette interface
            ListeOM += rs.getString("description");
             ListeOM += "|"+rs.getString("cardinaliteDestination")+"";

        param_edges+=nomOrigine+"#"+nomExtremite+"!"+ListeOM+"!"+sensInterface+"!normal"+",";
}	} catch (SQLException s) {s.getMessage(); }
      return param_edges;

  }

  public ErrorSpecific bd_Insert(String nomBase,Connexion myCnx, Statement st, String transaction){
    ResultSet rs=null;
    ErrorSpecific myError = new ErrorSpecific();
           req = "INSERT packageOM (";
             req+="nomPackage"+",";
             req+="descPackage"+",";
             req+="nomFamille";
             req+=")";
             req+=" VALUES ";
             req+="(";
             req+="'"+this.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
             req+="'"+this.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
             req+=this.idFamille;
           req+=")";

    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

          req = "SELECT     TOP (1) idPackage FROM   packageOM ORDER BY idPackage DESC";
          rs = myCnx.ExecReq(st, nomBase, req);
          try {rs.next(); this.id =rs.getInt("idPackage"); } catch (SQLException s) {s.getMessage();}

    return myError;
  }

  public ErrorSpecific bd_Update(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
    req = "UPDATE packageOM SET ";

    req+="nomPackage='"+this.nom.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"',";
    req+="descPackage='"+this.description.replaceAll("\r","").replaceAll("\u0092","'").replaceAll("'","''")+"'";

    req+=" WHERE idPackage="+this.id;
    
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

     return myError;

}
  
  public ErrorSpecific bd_delete(String nomBase,Connexion myCnx, Statement st, String transaction){
      ErrorSpecific myError = new ErrorSpecific();
    req = "DELETE FROM packageOM  ";
    req+=" WHERE idPackage="+this.id;
    
    myError = myCnx.ExecUpdateTransaction(st,nomBase,req,true,transaction, getClass().getName(),"bd_Insert",""+this.id);
    if (myError.cause == -1) return myError;

     return myError;

}  

  public void dump(){
    if (Connexion.traceOn.equals("no")) return;
    System.out.println("==================================================");
    System.out.println("id=" + this.id);
    System.out.println("nom=" + this.nom);
    System.out.println("nomPackage=" + this.idFamille);
    System.out.println("==================================================");
  }

  public static void main(String[] args) {
    Paquetage paquetage = new Paquetage();
  }
}
